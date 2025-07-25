-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("logic.char.CharStatus")
local r1_0 = "data/game/lililala"
local r2_0 = "data/game/sarah"
local r3_0 = nil
if _G.GameMode == _G.GameModeEvo then
  r3_0 = require("evo.char_tbl.tbl_c14_lililala").CreateTable()
end
local r4_0 = {
  "c14_lala_0_0",
  "c14_lala_1_0",
  "c14_lala_1_1",
  "c14_lala_1_2",
  "c14_lala_1_3",
  "c14_lala_1_4",
  "c14_lala_1_5",
  "c14_lala_1_10",
  "c14_lili_0_0",
  "c14_lili_1_0",
  "c14_lili_1_1",
  "c14_lili_1_2",
  "c14_lili_1_3",
  "c14_lili_1_4",
  "c14_lili_1_5",
  "c14_lili_1_10"
}
local r5_0 = false
local r6_0 = {
  require("char.c14.c14_lili"),
  require("char.c14.c14_lala")
}
local r7_0 = require("char.c12.afx12_buff02")
local function r8_0(r0_1)
  -- line: [42, 66] id: 1
  local r2_1 = r0_1.x
  local r3_1 = r0_1.y
  if r0_1.data.type == 1 then
    r2_1 = r2_1 - 36
    r3_1 = r3_1 - 10
  else
    r2_1 = r2_1 + 36
    r3_1 = r3_1 - 10
  end
  local r4_1 = anime.Register(r7_0.GetData(), r2_1, r3_1, r2_0)
  r0_1.data.nimbus.rt:insert(anime.GetSprite(r4_1))
  anime.RegisterShowHook(r4_1, function(r0_2, r1_2, r2_2, r3_2)
    -- line: [58, 61] id: 2
    r0_2.xScale = r0_2.xScale * 0.5
    r0_2.yScale = r0_2.yScale * 0.5
  end)
  anime.Loop(r4_1, true)
  anime.Pause(r4_1, false)
  anime.Show(r4_1, true)
  r0_1.data.nimbus.anm = r4_1
end
local function r9_0(r0_3, r1_3)
  -- line: [69, 81] id: 3
  anime.SetTimer(r0_3, r0_3.stop)
  r8_0(r1_3)
  local r2_3 = r1_3.data.pop_guardian
  if r2_3 then
    r1_3.data.guardian_data = r2_3(r1_3)
  end
end
local function r10_0(r0_4, r1_4)
  -- line: [84, 89] id: 4
  local r2_4 = r1_4.anime
  anime.Pause(r2_4, false)
  anime.RegisterFinish(r2_4, r9_0, r1_4)
  anime.Remove(r0_4)
end
local function r11_0(r0_5)
  -- line: [91, 100] id: 5
  local r1_5 = r0_5.data
  if r1_5 and r1_5.nimbus and r1_5.nimbus.anm then
    anime.Remove(r1_5.nimbus.anm)
    r0_5.data.nimbus.anm = nil
  end
end
local function r12_0(r0_6, r1_6, r2_6)
  -- line: [103, 122] id: 6
  if r1_6 then
    local r3_6 = {}
    local r4_6 = r0_6.data.nimbus
    if r4_6 and r4_6.anm then
      r3_6.nimbus = {}
      r3_6.nimbus.mode = events.Disable(r4_6.anm.ev, true)
      r3_6.nimbus.obj = r4_6.anm.ev
    end
    return r3_6
  else
    local r3_6 = r2_6.nimbus
    if r3_6 then
      events.Disable(r3_6.obj, r3_6.mode)
    end
    return nil
  end
end
local r17_0 = require("char.Char")
local function r18_0()
  -- line: [232, 257] id: 12
  local r0_12 = r0_0.SummonPos[1]
  local r1_12 = r0_0.SummonPos[2]
  local r2_12 = _G.MapLocation
  if r2_12[r1_12][r0_12] ~= 0 then
    return false
  end
  r0_0.LiliLalaPop = {
    false,
    false,
    false,
    false
  }
  local r3_12 = false
  local r4_12 = r0_0.LiliLalaVect
  local r5_12 = nil
  local r6_12 = nil
  local r7_12 = nil
  local r8_12 = nil
  for r12_12 = 1, 4, 1 do
    r5_12 = r4_12[r12_12][1]
    r6_12 = r4_12[r12_12][2]
    r7_12 = r0_12 + r5_12 * 2
    r8_12 = r1_12 + r6_12 * 2
    if 1 <= r7_12 and r7_12 <= 12 and 2 <= r8_12 and r8_12 <= 8 and r2_12[r1_12 + r6_12][r0_12 + r5_12] == 256 and r2_12[r8_12][r7_12] == 0 then
      r0_0.LiliLalaPop[r12_12] = true
      r3_12 = true
    end
  end
  return r3_12
end
local function r19_0(r0_13, r1_13, r2_13)
  -- line: [260, 296] id: 13
  local r3_13 = _G.UserStatus[14]
  local r4_13 = r0_13 * 80 - 40
  local r5_13 = r1_13 * 80 - 40
  local r6_13 = r17_0.make_character_struct({
    type = r0_0.CharDef.CharId.LiliLala,
    x = r4_13,
    y = r5_13,
    map_xy = {
      r0_13,
      r1_13
    },
    min_range = 0,
    max_range = 80,
    wait = r3_13.speed,
    attack = r3_13.attack,
    power = r3_13.power,
    level = 1,
  })
  r0_0.SummonChar[14].Load(r6_13, r2_13)
  local r7_13 = display.newGroup()
  r7_13:insert(r6_13.spr)
  r6_13.sort_sprite = r7_13
  r6_13.sort_z = r4_13 + r5_13 * 1000
  local r8_13 = display.newGroup()
  r7_13:insert(r8_13)
  assert(r6_13.data)
  r6_13.data.nimbus = {}
  r6_13.data.nimbus.rt = r8_13
  local r9_13 = display.newGroup()
  r7_13:insert(r9_13)
  r6_13.star_rt = r9_13
  return r6_13
end
local function r20_0(r0_14)
  -- line: [299, 335] id: 14
  local r3_14 = r0_14.x
  local r4_14 = r0_14.y
  local r1_14 = anime.Register(r0_0.SummonAnime[1].GetData(), r3_14, r4_14 - 40 + 63, "data/game")
  anime.RegisterFinish(r1_14, r0_14.func.finish, r0_14)
  anime.Show(r1_14, true)
  _G.SBaseRoot:insert(anime.GetSprite(r1_14))
  anime.Pause(r1_14, false)
  anime.Show(r1_14, true)
  _G.MyRoot:insert(r0_14.sort_sprite)
  r1_14 = anime.Register(r0_0.SummonAnime[2].GetData(), r3_14, r4_14 - 40, "data/game")
  anime.RegisterTrigger(r1_14, 8, function(r0_15, r1_15)
    -- line: [316, 323] id: 15
    if r0_14.type == 100 then
      r1_15.data.view_spr[1].isVisible = true
    else
      anime.Pause(r1_15.anime, true)
      anime.Show(r1_15.anime, true)
    end
  end, r0_14)
  anime.RegisterFinish(r1_14, function(r0_16, r1_16)
    -- line: [325, 327] id: 16
    anime.Remove(r0_16)
  end, nil)
  anime.Show(r1_14, true)
  _G.SFrontRoot:insert(anime.GetSprite(r1_14))
  anime.Pause(r1_14, false)
  anime.Show(r1_14, true)
  anime.RegisterTrigger(r1_14, 8, r17_0.summon_fx_trigger, r0_14)
end
local function r21_0(r0_17)
  -- line: [338, 402] id: 17
  assert(r0_17.data and r0_17.data.guardian, debug.traceback())
  local r1_17 = r0_17.data.guardian[1]
  local r2_17 = r0_17.data.guardian[2]
  local r3_17 = r0_17.data.fast
  local r4_17 = r1_17 * 80 - 40
  local r5_17 = r2_17 * 80 - 40
  local r6_17 = r17_0.make_character_struct({
    type = 100,
    x = r4_17,
    y = r5_17,
    map_xy = {
      r1_17,
      r2_17
    },
    min_range = 0,
    max_range = 80,
    wait = {
      0,
      0,
      0,
      0
    },
    attack = {
      0,
      0,
      0,
      0
    },
    power = {
      0,
      0,
      0,
      0
    },
    level = 1,
  })
  local r7_17 = r0_0.SummonChar.guardian.Load(r6_17, r0_17)
  local r8_17 = display.newGroup()
  r8_17:insert(r6_17.spr)
  for r12_17, r13_17 in pairs(r6_17.data.view_spr) do
    r8_17:insert(r13_17)
  end
  r6_17.sort_sprite = r8_17
  r6_17.sort_z = r4_17 + r5_17 * 1000
  local r9_17 = nil
  r9_17 = display.newGroup()
  r8_17:insert(r9_17)
  r6_17.hp_root = r9_17
  r9_17 = display.newGroup()
  r8_17:insert(r9_17)
  r6_17.burst_rt = r9_17
  r9_17 = display.newGroup()
  r8_17:insert(r9_17)
  r6_17.star_rt = r9_17
  _G.MapLocation[r2_17][r1_17] = 255
  if r3_17 then
    _G.MyRoot:insert(r8_17)
    r6_17.data.view_spr[1].isVisible = true
    r6_17.func.finish(nil, r6_17)
  else
    sound.PlaySE(5, 21, true)
    r20_0(r6_17)
  end
  r6_17.sound_path = sound.GetCharVoicePath(14, _G.GameData.language)
  r6_17.sound_handle = nil
  table.insert(r0_0.MyChar, r6_17)
  return r7_17
end
local function r27_0(r0_23, r1_23, r2_23, r3_23)
  -- line: [454, 558] id: 23
  r0_0.SummonPos = nil
  local r4_23 = r3_23.param
  local r5_23 = r3_23.fast_summon
  local r6_23 = r4_23[2]
  local r7_23 = r4_23[3]
  if r3_23.level == nil then
    r3_23.level = 1
  end
  local r8_23 = r3_23.level
  local r9_23 = nil
  local r10_23 = nil
  local r11_23 = nil
  local r12_23 = nil
  if r7_23 == r2_23 then
    if r6_23 < r1_23 then
      r11_23 = r6_23
      r12_23 = r7_23
      r9_23 = r1_23
      r10_23 = r2_23
    else
      r11_23 = r1_23
      r12_23 = r2_23
      r9_23 = r6_23
      r10_23 = r7_23
    end
  elseif r7_23 < r2_23 then
    r11_23 = r1_23
    r12_23 = r2_23
    r9_23 = r6_23
    r10_23 = r7_23
  else
    r11_23 = r6_23
    r12_23 = r7_23
    r9_23 = r1_23
    r10_23 = r2_23
  end
  local r13_23 = (r9_23 + r11_23) / 2
  local r14_23 = (r10_23 + r12_23) / 2
  local r15_23 = r19_0(r9_23, r10_23, 1)
  local r16_23 = r19_0(r11_23, r12_23, 2)
  assert(r15_23.data)
  r15_23.data.lililala = {
    r15_23,
    r16_23
  }
  r15_23.data.guardian = {
    r13_23,
    r14_23
  }
  r15_23.data.pop_guardian = r21_0
  r15_23.data.fast = r5_23
  r16_23.data.lililala = {
    r15_23,
    r16_23
  }
  r16_23.data.guardian = nil
  r16_23.data.fast = nil
  r16_23.data.pop_guardian = nil
  local r17_23 = _G.GameData.language
  r15_23.sound_path = sound.GetCharVoicePath(14, r17_23)
  r16_23.sound_path = sound.GetCharVoicePath(14, r17_23)
  r15_23.sound_handle = nil
  r16_23.sound_handle = nil
  r15_23.touch_sound = 7
  r16_23.touch_sound = 6
  _G.MapLocation[r10_23][r9_23] = 14
  _G.MapLocation[r12_23][r11_23] = 99
  if r5_23 then
    _G.MyRoot:insert(r15_23.sort_sprite)
    r15_23.summon = false
    anime.Pause(r15_23.anime, true)
    anime.Show(r15_23.anime, true, {
      timer = r15_23.anime.stop,
    })
    r15_23.func.nimbus(r15_23)
    r15_23.level = r8_23
    _G.MyRoot:insert(r16_23.sort_sprite)
    r16_23.summon = false
    anime.Pause(r16_23.anime, true)
    anime.Show(r16_23.anime, true, {
      timer = r16_23.anime.stop,
    })
    r16_23.func.nimbus(r16_23)
    r16_23.level = r8_23
    r15_23.data.guardian_data = r21_0(r15_23)
  else
    save.DataPush("lililala", {
      id = 14,
      lx = r9_23,
      ly = r10_23,
      rx = r11_23,
      ry = r12_23,
    })
    r20_0(r15_23)
    r20_0(r16_23)
  end
  table.insert(r0_0.MyChar, r15_23)
  table.insert(r0_0.MyChar, r16_23)
  if not r5_23 then
    if r0_0.IsUseCrystal == false then
      game.AddMp(-_G.UserStatus[14].cost[1])
    end
    game.ViewPanel()
  end
  if r8_23 > 1 then
    r17_0.set_star(r15_23)
    r17_0.set_star(r16_23)
  end
end
local function r28_0(r0_24, r1_24)
  -- line: [562, 663] id: 24
  local function r5_24(r0_25)
    -- line: [564, 564] id: 25
    return "data/game/upgrade/" .. r0_25 .. ".png"
  end
  local function r6_24(r0_26)
    -- line: [566, 566] id: 26
    return r5_24(r0_26 .. _G.UILanguage)
  end
  if r1_24.phase ~= "ended" then
    return true
  end
  r17_0.clear_guide()
  if not _G.GameData.confirm then
    return r17_0.summon_touch(r0_24, r1_24)
  end
  local r7_24 = r0_24.param[1]
  local r10_24 = r0_24.param[2] * 80 - 40
  local r11_24 = r0_24.param[3] * 80 - 40
  local r12_24 = nil
  local r13_24 = nil
  r12_24 = display.newImage(_G.MyRoot, "data/game/circle_summon.png")
  r12_24:setReferencePoint(display.CenterReferencePoint)
  r12_24.x = r10_24
  r12_24.y = r11_24
  r12_24.rotation = 0
  r13_24 = transit.Register(r12_24, {
    time = 3000,
    rotation = 360,
    transition = easing.linear,
    loop = true,
  })
  r0_0.GuideCircle = {}
  r0_0.GuideCircle.spr = r12_24
  r0_0.GuideCircle.tween = r13_24
  r17_0.display_summon_plate(14, 1, r12_24.y)
  local r2_24 = nil	-- notice: implicit variable refs by block#[7]
  if r12_24.y < 319 then
    r2_24 = r17_0.SUMMON_PLATE_POS[2]
  else
    r2_24 = r17_0.SUMMON_PLATE_POS[1]
  end
  local r3_24 = r2_24[1] + 180
  local r4_24 = r2_24[2] + 150
  local r14_24 = nil
  if r0_0.IsUseCrystal ~= true then
    r14_24 = util.LoadBtn({
      rtImg = _G.SPanelRoot,
      fname = r6_24("summon_ok_"),
      x = r3_24,
      y = r4_24,
      func = r17_0.confirm_btn,
    })
  else
    r14_24 = util.LoadBtn({
      rtImg = _G.SPanelRoot,
      fname = r6_24("summon_ok_cry_"),
      x = r3_24,
      y = r4_24,
      func = r17_0.confirm_btn,
    })
    r0_0.CrystalSpriteGrp.isVisible = true
    r0_0.CrystalSpriteGrp[2].isVisible = false
    if r12_24.y < 319 then
      r0_0.CrystalSpriteGrp.x = 640
      r0_0.CrystalSpriteGrp.y = 347
    else
      r0_0.CrystalSpriteGrp.x = 640
      r0_0.CrystalSpriteGrp.y = 305
    end
  end
  local r15_24 = util.LoadBtn({
    rtImg = _G.SPanelRoot,
    fname = r6_24("summon_cancel_"),
    x = r3_24 + 300,
    y = r4_24,
    func = r17_0.confirm_btn,
  })
  local r16_24 = nil
  local r17_24 = _G.UserStatus[r0_24.id]
  if r0_0.IsUseCrystal ~= true then
    r16_24 = r17_0.make_confirm_group_num(r3_24 + 75, r4_24 + 45, r17_24.cost[1])
  else
    r16_24 = r17_0.make_confirm_group_num(r3_24 + 75, r4_24 + 45, util.ConvertDisplayCrystal(r17_24.cost[1]))
  end
  r14_24.id = true
  r15_24.id = false
  r0_0.SummonConfirm = {
    summon = r0_24,
    spr = {
      r14_24,
      r15_24,
      r16_24
    },
  }
  return true
end
local r30_0 = {}
return setmetatable({
  Load = function(r0_7, r1_7)
    -- line: [125, 163] id: 7
    if not r5_0 then
      preload.Load(r4_0, r1_0)
      r5_0 = true
    end
    local r2_7 = r0_7.x
    local r3_7 = r0_7.y
    local r4_7 = anime.Register(r6_0[r1_7].GetData(), r2_7, r3_7, r1_0)
    local r5_7 = anime.GetSprite(r4_7)
    local r6_7 = display.newRect(_G.MyTgRoot, r2_7 - 40, r3_7 - 40, 80, 80)
    r6_7.alpha = 0.01
    r6_7.struct = r0_7
    r6_7.touch = r0_7.func.circle
    r6_7:addEventListener("touch")
    r0_7.touch_area = r6_7
    r0_7.anime = r4_7
    r0_7.spr = r5_7
    r0_7.func.release = r11_0
    r0_7.func.finish = r10_0
    r0_7.func.pause = r12_0
    r0_7.func.nimbus = r8_0
    r0_7.data = {}
    r0_7.data.light = nil
    r0_7.data.type = r1_7
    r0_7.data.upgrade_ok = false
    r0_7.data.is_released = false
    if _G.GameMode == _G.GameModeEvo then
      r0_7.func.rankTable = r3_0
    end
    return r0_7
  end,
  Cleanup = function()
    -- line: [165, 167] id: 8
    r5_0 = false
  end,
  Upgrade = function(r0_9, r1_9)
    -- line: [169, 213] id: 9
    local r2_9 = r0_9.data
    assert(r2_9, debug.traceback())
    if r2_9.is_released then
      return 
    end
    local r3_9 = r0_9.upgrade_mp
    if r1_9 == false then
      game.AddMp(-r3_9)
    end
    game.ViewPanel()
    r0_9.data.upgrade_ok = false
    save.DataPush("upgrade", {
      xy = r0_9.map_xy,
      upgrade_mp = r3_9,
    })
    local r4_9 = nil
    for r8_9, r9_9 in pairs(r2_9.lililala) do
      r4_9 = r9_9.level + 1
      r9_9.level = r4_9
      if r9_9.data.nimbus and r9_9.data.nimbus.anm then
        anime.Show(r9_9.data.nimbus.anm, false)
      end
      anime.SetTimer(r9_9.anime, 0)
      anime.Pause(r9_9.anime, false)
      anime.RegisterFinish(r9_9.anime, function(r0_10, r1_10)
        -- line: [198, 211] id: 10
        anime.SetTimer(r0_10, r0_10.stop)
        r1_10.data.upgrade_ok = true
        if r1_10.data.nimbus and r1_10.data.nimbus.anm then
          anime.Show(r1_10.data.nimbus.anm, true)
        end
        char.SetStar(r9_9, r4_9)
        local r2_10 = r1_10.data.guardian_data
        if r2_10 and r2_10.data and 0 < r2_10.data.hp then
          r2_10.func.upgrade(r2_10)
        end
      end, r9_9)
      -- close: r8_9
    end
  end,
  Release = function(r0_11)
    -- line: [215, 227] id: 11
    local r1_11 = r0_11.data
    assert(r1_11, debug.traceback())
    for r5_11, r6_11 in pairs(r1_11.lililala) do
      r6_11.func.destructor(r6_11)
    end
    save.DataPush("release", {
      xy = r0_11.map_xy,
      release_mp = 0,
    })
  end,
  coustom_anime_touch_func = function(r0_18)
    -- line: [404, 409] id: 18
    if not r0_18.data.upgrade_ok then
      return true
    end
  end,
  custom_upgrade_ok = function(r0_19, r1_19)
    -- line: [411, 414] id: 19
    r0_0.SummonChar[r0_0.CharDef.CharId.LiliLala].Upgrade(r0_19, r1_19)
    return true
  end,
  custom_release_ok = function(r0_20)
    -- line: [416, 433] id: 20
    r0_0.SummonChar[14].Release(r0_20)
    local r1_20 = r0_20.data.guardian_data
    if r1_20 == nil then
      r1_20 = r0_20.data.lililala[1].data.guardian_data
    end
    assert(r1_20)
    r0_0.SummonChar.guardian.Release(r1_20)
    if game ~= nil and game.IsPauseTypeNone() then
      game.MakeGrid(false)
    else
      game.MakeGrid(true)
    end
    game.ReleaseOK()
    return true
  end,
  custom_crystal_status = function(r0_21, r1_21, r2_21)
    -- line: [436, 443] id: 21
    if not r18_0() then
      r0_0.SummonCrystalStatus[r2_21] = 5
    else
      return r17_0.custom_crystal_status(r0_21, r1_21, r2_21)
    end
  end,
  custom_summon_status = function(r0_22, r1_22, r2_22, r3_22)
    -- line: [445, 452] id: 22
    if not r18_0() then
      r0_0.SummonStatus[r2_22] = 5
    else
      return r17_0.custom_summon_status(r0_22, r1_22, r2_22, r3_22)
    end
  end,
  custom_summon = r27_0,
  custom_confirm_summon = function(r0_27, r1_27)
    -- line: [666, 741] id: 27
    if r1_27.phase ~= "ended" then
      return true
    end
    if r0_0.SummonPlateGroup then
      return true
    end
    if r0_0.SummonPos == nil then
      return true
    end
    local r2_27 = r0_0.SummonPos[1]
    local r3_27 = r0_0.SummonPos[2]
    local r4_27 = nil
    local r5_27 = nil
    r17_0.hide_summon_circle()
    local r6_27 = nil
    for r10_27 = 1, 4, 1 do
      if r0_0.LiliLalaPop[r10_27] then
        if r6_27 then
          r6_27 = false
          break
        else
          r6_27 = true
          r0_27.twins = r10_27
          r0_27.param = {
            r0_27,
            r2_27 + r0_0.LiliLalaVect[r10_27][1] * 2,
            r3_27 + r0_0.LiliLalaVect[r10_27][2] * 2
          }
        end
      end
    end
    assert(r6_27 ~= nil)
    r17_0.view_summon_group(false)
    r17_0.clear_guide()
    r17_0.clear_guide_circle()
    local r7_27 = nil
    local r8_27 = nil
    local r9_27 = nil
    local r10_27 = nil
    local r11_27 = nil
    local r12_27 = nil
    for r16_27 = 1, 4, 1 do
      if r0_0.LiliLalaPop[r16_27] then
        r4_27 = r2_27 + r0_0.LiliLalaVect[r16_27][1] * 2
        r5_27 = r3_27 + r0_0.LiliLalaVect[r16_27][2] * 2
        r7_27 = r4_27 * 80 - 40
        r8_27 = r5_27 * 80 - 40
        r9_27 = display.newRect(_G.MyRoot, 0, 0, 64, 64)
        r9_27.strokeWidth = 1
        r9_27:setReferencePoint(display.CenterReferencePoint)
        r9_27.x = r7_27
        r9_27.y = r8_27
        r10_27 = display.newRect(_G.MyTgRoot, 0, 0, 64, 64)
        r10_27:setFillColor(0, 0, 0)
        r10_27.alpha = 0.01
        r10_27:setReferencePoint(display.CenterReferencePoint)
        r10_27.x = r7_27
        r10_27.y = r8_27
        r10_27.twins = r16_27
        r10_27.id = 14
        r10_27.param = {
          r0_27,
          r4_27,
          r5_27
        }
        r10_27.touch = r28_0
        r10_27:addEventListener("touch")
        r11_27 = anime.Register(r0_0.guide_efx.GetData(), r7_27, r8_27 - 40 - 28, "data/game/guide")
        _G.TargetRoot:insert(anime.GetSprite(r11_27))
        anime.Show(r11_27, true)
        anime.Loop(r11_27, true)
        table.insert(r0_0.GuideSprite, {
          spr = r9_27,
          anm = r11_27,
          tspr = r10_27,
        })
      end
    end
    return true
  end,
  CustomFirstSummon = function(r0_29, r1_29, r2_29, r3_29, r4_29, r5_29)
    -- line: [749, 763] id: 29
    if #r30_0 < 1 then
      r30_0 = {
        r1_29,
        r2_29
      }
    else
      r27_0(r0_29, r30_0[1], r30_0[2], {
        fast_summon = true,
        param = {
          0,
          r1_29,
          r2_29
        },
      })
      r30_0 = {}
    end
  end,
  InitFirstSummon = function()
    -- line: [745, 747] id: 28
    r30_0 = {}
  end,
}, {
  __index = r17_0,
})
