-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("logic.char.CharStatus")
local r1_0 = require("logic.game.GameStatus")
local r2_0 = nil
if _G.GameMode == _G.GameModeEvo then
  r2_0 = require("evo.char_tbl.tbl_c18_amber").CreateTable()
end
local r3_0 = {
  "c18_amber01_01_0_0",
  "c18_amber01_01_0_1",
  "c18_amber02_01_0_0",
  "c18_amber02_01_0_1",
  "c18_amber02_02_0_0",
  "c18_amber02_02_0_1",
  "c18_amber02_03_0_0",
  "c18_amber02_03_0_1",
  "c18_amber02_04_0_0",
  "c18_amber02_04_0_1",
  "c18_amber02_05_0_0",
  "c18_amber02_05_0_1",
  "c18_amber02_06_0_0",
  "c18_amber02_06_0_1",
  "c18_amber02_07_0_0",
  "c18_amber02_07_0_1",
  "c18_amber02_08_0_0",
  "c18_amber02_08_0_1",
  "c18_amber03_01_0_0",
  "c18_amber03_01_0_1",
  "c18_amber03_02_0_0",
  "c18_amber03_02_0_1",
  "c18_amber03_03_0_0",
  "c18_amber03_03_0_1",
  "c18_amber03_04_0_0",
  "c18_amber03_04_0_1",
  "c18_amber03_05_0_0",
  "c18_amber03_05_0_1",
  "c18_amber03_06_0_0",
  "c18_amber03_06_0_1",
  "c18_amber03_07_0_0",
  "c18_amber03_07_0_1",
  "c18_amber03_08_0_0",
  "c18_amber03_08_0_1"
}
local r4_0 = false
local r5_0 = nil
local r6_0 = nil
local r7_0 = nil
local r8_0 = 1000
local r9_0 = {}
for r13_0 = 1, 1, 1 do
  table.insert(r9_0, require(string.format("char.c18.c18_amber01_%02d", r13_0)))
end
local r10_0 = {}
for r14_0 = 1, 8, 1 do
  table.insert(r10_0, require(string.format("char.c18.c18_amber02_%02d", r14_0)))
end
local r11_0 = {}
for r15_0 = 1, 8, 1 do
  table.insert(r11_0, require(string.format("char.c18.c18_amber03_%02d", r15_0)))
end
local r12_0 = {
  {
    0,
    28
  },
  {
    -36,
    20
  },
  {
    -44,
    8
  },
  {
    -36,
    -4
  },
  {
    0,
    -44
  },
  {
    36,
    -4
  },
  {
    44,
    8
  },
  {
    36,
    20
  }
}
local function r13_0(r0_1, r1_1, r2_1)
  -- line: [89, 119] id: 1
  local r3_1 = r1_1.ms + r2_1
  if r8_0 <= r3_1 then
    r3_1 = r8_0
    if game ~= nil and game.IsNotPauseTypeNone() then
      return true
    end
  end
  r1_1.ms = r3_1
  local r4_1 = r3_1 / r8_0
  anime.Move(r1_1.anm, r1_1.sx + r1_1.lx * r4_1, r1_1.sy + r1_1.ly * r4_1 - 120 * math.sin(math.pi * r4_1), {
    angle = 270 * r4_1,
    scale = 1 + r4_1 / 0.5,
  })
  if r8_0 <= r3_1 then
    r1_1.func(r1_1.struct)
    r1_1.ev = nil
    return false
  end
  return true
end
local function r14_0(r0_2, r1_2)
  -- line: [121, 166] id: 2
  local r2_2 = r1_2.sx
  local r3_2 = r1_2.sy
  local r4_2 = r1_2.burst_anm
  anime.RegisterShowHook(r4_2, function(r0_3, r1_3, r2_3, r3_3)
    -- line: [126, 129] id: 3
    r0_3.xScale = r0_3.xScale * 2
    r0_3.yScale = r0_3.yScale * 2
  end, nil)
  anime.Show(r4_2, true)
  local r5_2 = nil
  local r6_2 = nil
  if r2_2 < r0_0.AmberStruct.x then
    r5_2 = -112 - math.random(50, 150)
  else
    r5_2 = _G.Width + 112 + math.random(50, 150)
  end
  r6_2 = r3_2
  local r9_2 = {
    sx = r2_2,
    sy = r3_2,
    ex = r5_2,
    ey = r6_2,
    lx = r5_2 - r2_2,
    ly = r6_2 - r3_2,
    anm = r1_2.damage_anm,
    spr = anime.GetSprite(r1_2.damage_anm),
    func = r1_2.func.destructor,
    struct = r1_2,
    ms = 0,
    ev = nil,
  }
  r1_2.sort_sprite.x = 0
  r1_2.sort_sprite.y = 0
  r1_2.sort_z = 640960
  anime.Move(r9_2.anm, r2_2, r3_2)
  r9_2.ev = events.Register(r13_0, r9_2, 1)
  return false
end
local function r15_0(r0_4)
  -- line: [168, 185] id: 4
  if r0_4.isSnipe then
    return 
  end
  r0_4.isSnipe = true
  local r1_4 = r0_4.sx
  local r2_4 = r0_4.sy
  local r3_4 = r0_4.anm
  anime.Pause(r3_4, true)
  anime.Show(r3_4, false)
  r3_4 = r0_4.damage_anm
  anime.Move(r3_4, 0, 0)
  anime.Pause(r3_4, true)
  anime.Show(r3_4, true)
  events.Register(r14_0, r0_4, 0)
end
local function r16_0(r0_5, r1_5)
  -- line: [187, 192] id: 5
  if r1_5.burst_anm then
    anime.Remove(r1_5.burst_anm)
    r1_5.burst_anm = nil
  end
end
local function r17_0(r0_6, r1_6, r2_6)
  -- line: [195, 271] id: 6
  r1_6.count = r1_6.count + 10
  local r3_6 = r1_6.my
  local r4_6 = r1_6.xy[1]
  local r5_6 = r1_6.xy[2]
  local r6_6 = r1_6.target.sx + r1_6.target.sight[1]
  local r7_6 = r1_6.target.sy + r1_6.target.sight[2]
  local r10_6 = math.atan2(r7_6 - r5_6, r6_6 - r4_6)
  local r11_6 = (math.deg(r10_6) + 180) % 360
  local r12_6 = math.cos(r10_6)
  local r13_6 = math.sin(r10_6)
  local r14_6 = true
  local r15_6 = r2_6 * 100
  local r16_6 = r4_6 + r12_6 * r15_6
  local r17_6 = r5_6 + r13_6 * r15_6
  if util.GetDistance(r6_6, r7_6, r4_6, r5_6, r16_6, r17_6) < 25 then
    r1_6.hit = true
    r1_6.sprite.isVisible = false
  end
  r4_6 = r16_6
  r5_6 = r17_6
  r1_6.xy[1] = r4_6
  r1_6.xy[2] = r5_6
  if not r1_6.hit then
    r1_6.sprite:setReferencePoint(display.CenterRightReferencePoint)
    r1_6.sprite.x = r4_6
    r1_6.sprite.y = r5_6
    r1_6.sprite.xScale = r1_6.count
    r1_6.sprite.yScale = r3_6.level
    r1_6.sprite.rotation = r11_6
  else
    local r20_6 = r1_6.target.attr
    local r21_6 = r3_6.power[r3_6.level] * r3_6.buff_power
    display.remove(r1_6.sprite)
    r14_6 = false
    if r0_0.SnipeTargetSpr then
      anime.Remove(r0_0.SnipeTargetSpr)
      r0_0.SnipeTargetSpr = nil
    end
    local r23_6 = r1_6.target
    if not r23_6.no_snipe and r23_6.type ~= -1 then
      r23_6.damage_anm = r23_6.anm
      r23_6.func.burst = r15_0
      r23_6.func.burst_finish = r16_0
    end
    r23_6.func.hit(r1_6.target, r21_6)
  end
  if not r14_6 then
    table.remove(_G.ShotEvent, table.indexOf(_G.ShotEvent, r0_6))
  end
  return r14_6
end
local function r18_0(r0_7, r1_7, r2_7, r3_7)
  -- line: [274, 302] id: 7
  local r4_7 = r12_0[r3_7.vect]
  local r5_7 = r0_7 + r4_7[1]
  local r6_7 = r1_7 + r4_7[2]
  local r7_7 = display.newImage("data/game/bullet.png")
  r7_7:setReferencePoint(display.CenterReferencePoint)
  r7_7.x = r5_7
  r7_7.y = r6_7
  local r8_7 = {
    group = nil,
    sprite = r7_7,
    index = 0,
    xy = {
      r5_7,
      r6_7
    },
    target = r2_7,
    hit = false,
    my = r3_7,
    count = 1,
  }
  local r9_7 = util.GetDistance(r5_7, r6_7, 0, 0, r8_7.target.sx, r8_7.target.sy)
  _G.MissleRoot:insert(r7_7)
  table.insert(_G.ShotEvent, events.Register(r17_0, r8_7, 0, false))
  sound.PlaySE(29, 2)
  return r8_7
end
local function r19_0(r0_8, r1_8)
  -- line: [305, 370] id: 8
  if r1_8 == nil then
    r1_8 = 1
  end
  if not r4_0 then
    preload.Load(r3_0, "data/game/amber")
    r4_0 = true
  end
  local r2_8 = {}
  r7_0 = display.newGroup()
  local r3_8 = r0_8.x
  local r4_8 = r0_8.y
  if r1_8 == 1 then
    local r5_8 = anime.Register(r9_0[1].GetData(), r3_8, r4_8, "data/game/amber")
    r5_0 = anime.GetSprite(r5_8)
    r7_0:insert(r5_0)
    table.insert(r2_8, r5_8)
  elseif r1_8 == 3 then
    local r5_8 = anime.Register(r11_0[r0_8.vect].GetData(), r3_8, r4_8, "data/game/amber")
    r5_0 = anime.GetSprite(r5_8)
    r7_0:insert(r5_0)
    table.insert(r2_8, r5_8)
  else
    for r8_8 = 1, 8, 1 do
      if r1_8 == 2 then
        local r9_8 = anime.RegisterWithInterval(r10_0[r8_8].GetData(), r3_8, r4_8, "data/game/amber", 30)
        r5_0 = anime.GetSprite(r9_8)
        r7_0:insert(r5_0)
        table.insert(r2_8, r9_8)
      else
        local r9_8 = anime.Register(r11_0[r8_8].GetData(), r3_8, r4_8, "data/game/amber")
        r5_0 = anime.GetSprite(r9_8)
        r7_0:insert(r5_0)
        table.insert(r2_8, r9_8)
      end
    end
  end
  if r0_8.touch_area then
    display.remove(r0_8.touch_area)
    r0_8.touch_area = nil
  end
  r6_0 = display.newRect(_G.MyTgRoot, r3_8 - 40, r4_8 - 40, 80, 80)
  r6_0.alpha = 0.01
  r6_0.struct = r0_8
  r6_0.touch = r0_8.func.circle
  r6_0:addEventListener("touch", r6_0)
  r0_8.touch_area = r6_0
  r0_8.anime = anime.Pack(unpack(r2_8))
  if r1_8 == 1 or r1_8 == 3 then
    r0_8.non_anime_vect = true
  else
    r0_8.non_anime_vect = false
  end
  r0_8.spr = r7_0
  r0_8.func.load = r18_0
  if _G.GameMode == _G.GameModeEvo then
    r0_8.func.rankTable = r2_0
  end
  return r0_8
end
local r22_0 = require("char.Char")
local function r24_0(r0_12)
  -- line: [448, 452] id: 12
  return r0_12 * 300 / _G.UserStatus[r0_0.CharDef.CharId.Amber].speed[r0_0.AmberStruct.level] * (r0_0.AttackSpeed + 100) / 100 * 3.2
end
local function r25_0(r0_13, r1_13, r2_13, r3_13)
  -- line: [454, 478] id: 13
  local r4_13 = r0_0.SnipeChargeGauge.rtImg
  local r5_13 = r0_0.SnipeChargeGauge.rtBar
  local r6_13 = r0_0.SnipeChargeGauge.spr
  local r7_13 = r24_0(r1_13)
  local r8_13 = r24_0(r2_13)
  if r6_13 then
    display.remove(r6_13)
  end
  local r9_13 = 104
  local r10_13 = r9_13 * (1 - (r7_13 - r3_13) / r8_13)
  if r9_13 < r10_13 then
    r10_13 = r9_13
  end
  r6_13 = display.newRect(r5_13, 16, 8, r10_13, 8)
  r6_13:setFillColor(255, 127, 0)
  r0_0.SnipeChargeGauge.spr = r6_13
  r4_13.x = r0_13.x - 65
  r4_13.y = r0_13.y - 70
  r4_13.isVisible = true
end
local function r27_0()
  -- line: [574, 608] id: 15
  local function r0_15(r0_16)
    -- line: [575, 575] id: 16
    return "data/game/upgrade/" .. r0_16 .. ".png"
  end
  r0_0.PanelTrantision = transition.to(_G.PanelRoot, {
    time = 500,
    y = _G.HeightDiff - _G.PanelRoot.height,
    transition = easing.outExpo,
  })
  local r3_15 = (function(r0_17)
    -- line: [577, 577] id: 17
    return r0_15(r0_17 .. _G.UILanguage)
  end)("snipemode_window_")
  if not r0_0.SnipemodeWindowImg then
    r0_0.SnipemodeWindowImg = display.newImage(_G.SnipeRoot, r3_15, 0, -74, true)
    r0_0.SnipemodeWindowImg:setReferencePoint(display.TopLeftReferencePoint)
  end
  transition.to(r0_0.SnipemodeWindowImg, {
    time = 500,
    y = 0,
    transition = easing.outExpo,
  })
  if r0_0.SummonPlateGroup then
    r22_0.close_plate()
  end
  r22_0.hide_upgrade_circle(false, true)
  r22_0.not_show_upgrade_btn()
  r0_0.SnipeImg = display.newGroup()
  local r4_15 = display.newRect(r0_0.SnipeImg, 0, 0, _G.Width, _G.Height)
  r4_15:setFillColor(0, 0, 0)
  r4_15.alpha = 0.4
  _G.RouteRoot:insert(r0_0.SnipeImg)
  r0_0.IsSnipeMode = true
  return true
end
local function r28_0(r0_18)
  -- line: [611, 619] id: 18
  if r0_18 < 319 then
    r0_0.UpgradeBtn[15].x = 460
    r0_0.UpgradeBtn[15].y = 85
  else
    r0_0.UpgradeBtn[15].x = 460
    r0_0.UpgradeBtn[15].y = 410
  end
end
local function r29_0(r0_19)
  -- line: [621, 644] id: 19
  local function r1_19(r0_20)
    -- line: [622, 622] id: 20
    return "data/game/upgrade/" .. r0_20 .. ".png"
  end
  local r2_19 = display.newGroup()
  local r3_19 = display.newGroup()
  local r4_19 = display.newGroup()
  local r5_19 = display.newGroup()
  r2_19:insert(r3_19)
  r2_19:insert(r4_19)
  r2_19:insert(r5_19)
  util.LoadParts(r3_19, r1_19("snipecharge_base"), 0, 0)
  util.LoadParts(r5_19, r1_19("snipecharge_frame"), 0, 0)
  r2_19.isVisible = false
  r0_19:insert(r2_19)
  r2_19:setReferencePoint(display.TopLeftReferencePoint)
  r0_0.SnipeChargeGauge = {}
  r0_0.SnipeChargeGauge.rtImg = r2_19
  r0_0.SnipeChargeGauge.rtBar = r4_19
  r0_0.SnipeChargeGauge.spr = nil
end
return setmetatable({
  Load = r19_0,
  Reload = function(r0_9, r1_9)
    -- line: [372, 416] id: 9
    if r6_0 then
      r6_0:removeEventListener("touch", r6_0)
    end
    if r0_9.anime then
      anime.Remove(r0_9.anime)
    end
    r0_9.anime.isVisible = false
    r0_9 = r19_0(r0_9, r1_9)
    local r2_9 = r0_9.x
    local r3_9 = r0_9.y
    anime.Show(r0_9.anime, false)
    anime.SetTimer(r0_9.anime, 0)
    anime.Pause(r0_9.anime, false)
    if _G.GameMode == _G.GameModeEvo and r0_9.evoLevel ~= nil and 0 < r0_9.evoLevel then
      anime.Show(r0_9.anime, true, {
        scale = 1.7,
      })
    else
      anime.Show(r0_9.anime, true)
    end
    if r1_9 == 2 then
      anime.Loop(r0_9.anime, true)
    else
      anime.Loop(r0_9.anime, false)
    end
    local r4_9 = display.newGroup()
    r4_9:insert(r0_9.spr)
    r0_9.sort_sprite = r4_9
    r0_9.sort_z = r2_9 + r3_9 * 1000
    r0_9.star_rt = r4_9
    if r0_9.star then
      char.SetStar(r0_9)
    end
    r0_0.AmberStruct = r0_9
    _G.MyRoot:insert(r0_9.sort_sprite)
  end,
  Cleanup = function()
    -- line: [418, 420] id: 10
    r4_0 = false
  end,
  finish_func = function(r0_11, r1_11)
    -- line: [426, 445] id: 11
    if r1_11.target and not r1_11.func.check(r1_11, r1_11.target) then
      r1_11.target = nil
    end
    r1_11.shooting = false
    local r2_11 = r0_0.SnipeChargeGauge.spr
    if r2_11 then
      display.remove(r2_11)
    end
    r0_0.SnipeChargeGauge.spr = nil
    r0_0.SnipeChargeGauge.rtImg.isVisible = false
    r0_0.SnipeSelectTarget = nil
    r0_0.SummonChar[r0_0.CharDef.CharId.Amber].Reload(r0_0.AmberStruct, 1)
    anime.Pause(r0_11, true)
    anime.SetTimer(r0_11, 0)
  end,
  range_func = function(r0_14, r1_14, r2_14)
    -- line: [481, 571] id: 14
    if game ~= nil and game.IsNotPauseTypeNone() then
      return true
    end
    if not r0_0.SnipeSelectTarget then
      return true
    end
    local r3_14 = r1_14
    local r4_14 = r3_14.func
    local r5_14 = r0_0.SnipeSelectTarget.attack
    local r6_14 = r3_14.attack
    if (r5_14[1] and r6_14[1] or r5_14[2] and r6_14[2]) and r4_14.check(r3_14, r0_0.SnipeSelectTarget) then
      r3_14.target = r0_0.SnipeSelectTarget
    end
    r0_0.SnipeSelectTarget.frame_count = r0_0.SnipeSelectTarget.frame_count + 1
    if r3_14.target then
      r25_0(r0_0.AmberStruct, r3_14.target.hitpoint, r3_14.target.maxhtpt, r0_0.SnipeSelectTarget.frame_count)
      if not r3_14.target.living then
        r3_14.target = nil
      end
    end
    if r4_14.pointing and r3_14.target then
      local r7_14 = r4_14.pointing(r3_14, r3_14.target)
    end
    if r3_14.target then
      if r4_14.check(r3_14, r3_14.target) then
        local r7_14 = r4_14.pointing(r3_14, r3_14.target)
        local r8_14 = r24_0(r3_14.target.hitpoint)
        local r9_14 = r24_0(r3_14.target.maxhtpt)
        if r7_14 then
          if r4_14.lockon then
            r4_14.lockon(r3_14, r3_14.target)
          end
          if r8_14 - r0_0.SnipeSelectTarget.frame_count < 30 and not r0_0.SnipeSelectTarget.sound_start then
            r0_0.SnipeSelectTarget.sound_start = true
            sound.play_sound(r3_14, math.random(7, 9))
          end
          if r8_14 < r0_0.SnipeSelectTarget.frame_count then
            r3_14.vect = r7_14
            r0_0.SummonChar[r0_0.CharDef.CharId.Amber].Reload(r3_14, 3)
            local r10_14 = r3_14.anime
            anime.Pause(r10_14, false)
            anime.Show(r10_14, true)
            anime.RegisterTrigger(r10_14, r3_14.shot_frame_nr, r4_14.shoot, r3_14)
            anime.RegisterFinish(r10_14, r4_14.finish, r3_14)
            r0_0.SnipeSelectTarget = nil
          end
        end
      elseif not r3_14.shooting then
        r3_14.target = nil
      end
    end
    if r3_14.target == nil then
      anime.ChangeVect(r3_14.anime, 1)
      local r7_14 = r0_0.SnipeChargeGauge.spr
      if r7_14 then
        display.remove(r7_14)
      end
      r0_0.SnipeChargeGauge.spr = nil
      r0_0.SnipeChargeGauge.rtImg.isVisible = false
      r0_0.SummonChar[r0_0.CharDef.CharId.Amber].Reload(r0_0.AmberStruct, 1)
      r0_0.SnipeSelectTarget = nil
      if r3_14.shooting then
        r4_14.finish(r3_14.anime, r3_14)
      end
    end
    return true
  end,
  coustom_skill_touch_func = function(r0_21)
    -- line: [646, 649] id: 21
    r27_0()
    return true
  end,
  custom_popup_upgrade_btn = function(r0_23)
    -- line: [663, 671] id: 23
    if game ~= nil and game.GetPauseType() ~= r1_0.PauseTypeFirstPause then
      r28_0(r0_23.y)
      r0_0.UpgradeBtn[15].isVisible = true
      r0_0.UpgradeBtn[15].struct = r0_23
    end
  end,
  custom_make_upgrade_btn = function()
    -- line: [673, 695] id: 24
    local function r1_24(r0_25)
      -- line: [675, 675] id: 25
      return "data/game/upgrade/" .. r0_25 .. ".png"
    end
    local r0_24 = display.newGroup()
    local r3_24 = r27_0
    display.newImage(r0_24, (function(r0_26)
      -- line: [677, 677] id: 26
      return r1_24(r0_26 .. _G.UILanguage)
    end)("snipe_"), 0, 0, true)
    r0_24:setReferencePoint(display.CenterReferencePoint)
    r0_0.UpgradeBtn[15] = util.LoadBtnGroup({
      group = r0_24,
      x = 452,
      y = 176,
      func = r3_24,
      show = false,
    })
    _G.UpgradeRoot:insert(r0_24)
    r0_0.SnipeChargeGauge = nil
    r29_0(_G.ExplosionRoot)
  end,
  custom_release_ok = function(r0_27)
    -- line: [698, 714] id: 27
    local r1_27 = r0_0.SnipeChargeGauge.spr
    if r1_27 then
      display.remove(r1_27)
    end
    r0_0.SnipeChargeGauge.spr = nil
    r0_0.SnipeChargeGauge.rtImg.isVisible = false
    if r0_0.SnipeTargetSpr then
      anime.Remove(r0_0.SnipeTargetSpr)
      r0_0.SnipeTargetSpr = nil
    end
    r22_0.snipe_mode_off()
    r0_0.AmberOnlyOne = false
    r0_0.AmberStruct = nil
    r0_0.SnipeSelectTarget = nil
    return false
  end,
  custom_crystal_status = function(r0_28, r1_28, r2_28)
    -- line: [716, 722] id: 28
    if r0_0.AmberOnlyOne then
      r0_0.SummonCrystalStatus[r2_28] = 4
    else
      return r22_0.custom_crystal_status(r0_28, r1_28, r2_28)
    end
  end,
  custom_summon_status = function(r0_29, r1_29, r2_29, r3_29)
    -- line: [724, 730] id: 29
    if r0_0.AmberOnlyOne then
      r0_0.SummonStatus[r2_29] = 4
    else
      return r22_0.custom_summon_status(r0_29, r1_29, r2_29, r3_29)
    end
  end,
  coustom_circle_touch_func = function(r0_22, r1_22, r2_22)
    -- line: [651, 661] id: 22
    r0_0.CircleTransition = transition.to(r0_0.CircleSpr, {
      time = 0,
      rotation = 0,
      transition = easing.outQuad,
      onComplete = r22_0.circle_rolling,
    })
    r0_0.CircleTransition.isVisible = false
    return true
  end,
}, {
  __index = r22_0,
})
