-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = {
  40,
  40,
  45,
  50
}
local r1_0 = {
  4,
  10,
  20,
  30
}
local r2_0 = {
  1,
  1,
  1,
  1
}
local r3_0 = {
  200,
  300,
  400,
  400
}
local r4_0 = nil
if _G.GameMode == _G.GameModeEvo then
  r4_0 = require("evo.char_tbl.tbl_c24_yung").CreateTable()
end
local r5_0 = {
  require("evo.evo_effect.evo_eff"),
  require("evo.evo_effect.evo_eff"),
  require("evo.evo_effect.evo_eff")
}
local r6_0 = "data/evo/evo_effect"
local r7_0 = require("logic.char.CharStatus")
local r8_0 = require("logic.game.GameStatus")
local r9_0 = {
  "c24_yung_atk01_0_0",
  "c24_yung_atk01_0_1",
  "c24_yung_atk01_0_2",
  "c24_yung_atk02_0_0",
  "c24_yung_atk02_0_1",
  "c24_yung_atk03_0_0",
  "c24_yung_atk03_0_1",
  "c24_yung_atk04_0_0",
  "c24_yung_atk04_0_1",
  "c24_yung_atk05_0_0",
  "c24_yung_atk05_0_1",
  "c24_yung_wait01_0_0",
  "c24_yung_wait01_0_1",
  "c24_yung_wait01_0_2",
  "c24_yung_wait01_0_3",
  "c24_yung_wait02_0_0",
  "c24_yung_wait02_0_1",
  "c24_yung_wait02_0_2",
  "c24_yung_wait03_0_0",
  "c24_yung_wait03_0_1",
  "c24_yung_wait03_0_2",
  "c24_yung_wait04_0_0",
  "c24_yung_wait04_0_1",
  "c24_yung_wait04_0_2",
  "c24_yung_wait05_0_0",
  "c24_yung_wait05_0_1",
  "c24_yung_wait05_0_2"
}
local r10_0 = {
  "yung_bullet1_0_0",
  "yung_bullet1_0_1",
  "yung_bullet1_0_2",
  "yung_bullet1_0_3",
  "yung_bullet1_0_4",
  "yung_bullet1_0_5"
}
local r11_0 = false
local r12_0 = {}
for r16_0 = 1, 8, 1 do
  table.insert(r12_0, require(string.format("char.c24.c24_yung_wait%02d", r16_0)))
end
local r13_0 = {}
for r17_0 = 1, 8, 1 do
  table.insert(r13_0, require(string.format("char.c24.c24_yung_atk%02d", r17_0)))
end
local r14_0 = require("char.c24.yung_bullet1")
local r15_0 = {
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
local r16_0 = "data/game"
local r17_0 = "data/game/lv2"
local r18_0 = "data/game/lv3"
local function r19_0(r0_1, r1_1)
  -- line: [108, 119] id: 1
  if r0_1 == nil or r1_1 == nil then
    return false
  end
  if r0_1[r1_1] ~= nil then
    return true
  end
  return false
end
local function r20_0(r0_2)
  -- line: [124, 131] id: 2
  if r0_2 == nil or r19_0(r0_2.func, "rankTable") == false then
    return nil
  end
  return r0_2.func.rankTable
end
local function r21_0(r0_3)
  -- line: [136, 158] id: 3
  local r1_3 = r20_0(r0_3)
  if r1_3 == nil or r0_3 == nil then
    return 
  end
  local r2_3 = nil
  if r0_3.evo.evoLevel == 0 then
    r2_3 = {
      scale = 1,
      offsetX = 0,
      offsetY = 0,
    }
  else
    r2_3 = r1_3.GetEvoLevelScale(r0_3.evo.evoLevel)
  end
  r0_3.evo.imageScale = r2_3.scale
  anime.SetOffset(r0_3.anime, r2_3.offsetX, r2_3.offsetY)
  anime.SetImageScale(r0_3.anime, r2_3.scale, r2_3.scale)
end
local function r22_0(r0_4)
  -- line: [163, 173] id: 4
  if r19_0(r0_4.evo, "charEffect") == false or r0_4.evo.charEffect == nil then
    return 
  end
  anime.Pause(r0_4.evo.charEffect, true)
  anime.Show(r0_4.evo.charEffect, false)
  anime.Remove(r0_4.evo.charEffect)
  r0_4.evo.charEffect = nil
end
local function r23_0(r0_5)
  -- line: [178, 206] id: 5
  if r0_5 == nil or r19_0(r0_5.evo, "evoLevel") == false then
    DebugPrint("キャラクタ構造体不正")
    return 
  end
  r22_0(r0_5)
  if r0_5.evo.evoLevel < 1 or #r5_0 < r0_5.evo.evoLevel then
    return 
  end
  local r3_5 = anime.Register(r5_0[r0_5.evo.evoLevel].GetData(), r0_5.x, r0_5.y, r6_0)
  r0_5.spr:insert(r0_5.spr.numChildren, anime.GetSprite(r3_5))
  anime.Loop(r3_5, true)
  anime.Show(r3_5, true)
  r0_5.evo.charEffect = r3_5
end
local function r24_0(r0_6, r1_6, r2_6)
  -- line: [209, 230] id: 6
  if r2_6 == 3 then
    r1_6 = r18_0
  elseif r2_6 == 2 then
    r1_6 = r17_0
  else
    r1_6 = r16_0
  end
  local r4_6 = r1_6 .. "/yung"
  local r5_6 = nil
  for r9_6 = 1, r0_6.waitAnime.nr, 1 do
    anime.ReplaceSprite(r0_6.waitAnime.pack[r9_6], r4_6, r12_0[r9_6].GetData())
  end
  for r9_6 = 1, r0_6.attAnime.nr, 1 do
    anime.ReplaceSprite(r0_6.attAnime.pack[r9_6], r4_6, r13_0[r9_6].GetData())
  end
  return r0_6
end
local function r25_0(r0_7, r1_7, r2_7)
  -- line: [232, 250] id: 7
  local r3_7 = {}
  local r4_7 = display.newGroup()
  local r5_7 = nil
  if r0_7 == 1 then
    r5_7 = r12_0
  else
    r5_7 = r13_0
  end
  for r9_7 = 1, 8, 1 do
    local r10_7 = anime.Register(r5_7[r9_7].GetData(), r1_7, r2_7, "data/game/yung")
    r4_7:insert(anime.GetSprite(r10_7))
    table.insert(r3_7, r10_7)
  end
  return anime.Pack(unpack(r3_7)), r4_7
end
local function r26_0(r0_8, r1_8)
  -- line: [252, 284] id: 8
  if r0_8.char_anime_number == r1_8 then
    return 
  end
  r0_8.char_anime_number = r1_8
  if r0_8.anime then
    if r1_8 == 1 then
      r0_8.waitCharRt.isVisible = true
      r0_8.attCharRt.isVisible = false
      r0_8.anime = r0_8.waitAnime
      r0_8.spr = r0_8.waitCharRt
      anime.Loop(r0_8.anime, true)
    else
      r0_8.waitCharRt.isVisible = false
      r0_8.attCharRt.isVisible = true
      r0_8.anime = r0_8.attAnime
      r0_8.spr = r0_8.attCharRt
      anime.Loop(r0_8.anime, false)
    end
    char.SetOrder(r0_8)
    anime.Pause(r0_8.anime, false)
    anime.Show(r0_8.anime, true, {
      scale = (r0_8.wait[r0_8.level] + r7_0.AttackSpeed) / 100,
    })
    anime.SetTimer(r0_8.anime, 0)
    if r0_8.evo then
      r23_0(r0_8)
      r21_0(r0_8)
    else
      r22_0(r0_8)
    end
  end
end
local function r27_0(r0_9)
  -- line: [286, 289] id: 9
  anime.SetTimer(r0_9.anime, 15)
  anime.Pause(r0_9.anime, true)
end
local function r28_0(r0_10)
  -- line: [291, 310] id: 10
  if r0_10.shot then
    if r0_10.in_shot_ev then
      events.Delete(r0_10.in_shot_ev)
      r0_10.in_shot_ev = nil
    end
    if r0_10.shot_ev then
      events.Delete(r0_10.shot_ev)
      r0_10.shot_ev = nil
    end
    if r0_10.shot.back_ev then
      events.Delete(r0_10.shot.back_ev)
      r0_10.shot.back_ev = nil
    end
    if r0_10.shot.anime then
      anime.Remove(r0_10.shot.anime)
      r0_10.shot.anime = nil
    end
  end
end
local function r29_0(r0_11)
  -- line: [312, 323] id: 11
  r0_11.shot_number = 0
  if r0_11.char_anime_number == 2 then
    r26_0(r0_11, 1)
  end
  if r0_11.anime then
    anime.ChangeVect(r0_11.anime, 1)
    anime.SetTimer(r0_11.anime, 1)
    anime.Pause(r0_11.anime, true)
  end
end
local function r31_0(r0_13, r1_13, r2_13)
  -- line: [336, 390] id: 13
  local r3_13 = r1_13.xy[1]
  local r4_13 = r1_13.xy[2]
  local r5_13 = r1_13.my.x
  local r6_13 = r1_13.my.y
  local r9_13 = math.atan2(r6_13 - r4_13, r5_13 - r3_13)
  local r10_13 = (math.deg(r9_13) + 180) % 360
  local r11_13 = math.cos(r9_13)
  local r12_13 = math.sin(r9_13)
  local r13_13 = true
  local r14_13 = r2_13 * r1_0[r1_13.my.level]
  local r15_13 = r3_13 + r11_13 * r14_13
  local r16_13 = r4_13 + r12_13 * r14_13
  if util.GetDistance(r5_13, r6_13, r3_13, r4_13, r15_13, r16_13) < 25 then
    anime.Remove(r1_13.anime)
    r26_0(r1_13.my, 1)
    r1_13.back = true
  end
  r3_13 = r15_13
  r4_13 = r16_13
  r1_13.xy[1] = r3_13
  r1_13.xy[2] = r4_13
  if not r1_13.back and r1_13.anime then
    anime.Move(r1_13.anime, r3_13, r4_13)
  else
    display.remove(r1_13.sprite)
    r1_13.my.shot_number = r1_13.my.shot_number - 1
    r13_13 = false
  end
  if not r13_13 then
    table.remove(_G.ShotEvent, table.indexOf(_G.ShotEvent, r0_13))
    table.remove(_G.ShotEvent, table.indexOf(_G.ShotEvent, r1_13.ev))
    r1_13.my.func.finish(r1_13.my.anime, r1_13.my)
  end
  return r13_13
end
local function r32_0(r0_14, r1_14, r2_14)
  -- line: [393, 456] id: 14
  local r3_14 = r1_14.my
  local r4_14 = r1_14.xy[1]
  local r5_14 = r1_14.xy[2]
  local r6_14 = r1_14.target.sx + r1_14.target.sight[1]
  local r7_14 = r1_14.target.sy + r1_14.target.sight[2]
  local r10_14 = math.atan2(r7_14 - r5_14, r6_14 - r4_14)
  local r11_14 = (math.deg(r10_14) + 180) % 360
  local r12_14 = math.cos(r10_14)
  local r13_14 = math.sin(r10_14)
  local r14_14 = true
  local r15_14 = r2_14 * r0_0[r3_14.level]
  local r16_14 = r4_14 + r12_14 * r15_14
  local r17_14 = r5_14 + r13_14 * r15_14
  local r18_14 = util.GetDistance(r6_14, r7_14, r4_14, r5_14, r16_14, r17_14)
  if r18_14 < 8000 then
    if not r1_14.sound_play then
      sound.PlaySE(31, 4)
    end
    r1_14.sound_play = true
  end
  if r18_14 < 25 then
    r1_14.back_ev = events.Register(r31_0, r1_14, 0, false)
    table.insert(_G.ShotEvent, r1_14.back_ev)
    r1_14.hit = true
  end
  r4_14 = r16_14
  r5_14 = r17_14
  r1_14.xy[1] = r4_14
  r1_14.xy[2] = r5_14
  if not r1_14.hit and r1_14.anime then
    anime.Move(r1_14.anime, r4_14, r5_14)
  else
    local r19_14 = r1_14.target.attr
    local r20_14 = r3_14.power[r3_14.level] * r3_14.buff_power
    r14_14 = false
    if _G.GameMode == _G.GameModeEvo then
      r1_14.target.func.stan(r1_14.target, r20_14, r3_0[r3_14.level], r3_14)
    else
      r1_14.target.func.stan(r1_14.target, r20_14, r3_0[r3_14.level])
    end
  end
  if not r14_14 then
    table.remove(_G.ShotEvent, table.indexOf(_G.ShotEvent, r0_14))
  end
  return r14_14
end
local function r33_0(r0_15, r1_15, r2_15, r3_15)
  -- line: [459, 497] id: 15
  local r4_15 = r15_0[r3_15.vect]
  local r5_15 = r0_15 + r4_15[1]
  local r6_15 = r1_15 + r4_15[2]
  local r7_15 = anime.Register(r14_0.GetData(), r5_15, r6_15, "data/game/yung")
  local r8_15 = anime.GetSprite(r7_15)
  local r9_15 = {
    anime = r7_15,
    ev = r7_15.ev,
    group = r8_15,
    sprite = nil,
    xy = {
      r5_15,
      r6_15
    },
    target = r2_15,
    hit = false,
    back = false,
    my = r3_15,
  }
  anime.Show(r7_15, true)
  anime.Loop(r7_15, true)
  anime.RegisterFinish(r7_15, r3_15.func.finish, r3_15)
  table.insert(_G.ShotEvent, r9_15.ev)
  _G.MissleRoot:insert(r8_15)
  r3_15.shot_ev = events.Register(r32_0, r9_15, 0, false)
  r3_15.shooting = true
  table.insert(_G.ShotEvent, r3_15.shot_ev)
  r3_15.shot = r9_15
  table.remove(_G.ShotEvent, table.indexOf(_G.ShotEvent, r3_15.in_shot_ev))
  return r9_15
end
return setmetatable({
  Load = function(r0_24, r1_24)
    -- line: [665, 706] id: 24
    if r1_24 == nil then
      r1_24 = 1
    end
    if not r11_0 then
      preload.Load(r10_0, "data/game/yung")
      preload.Load(r9_0, "data/game/yung")
      preload.Load(r9_0, "data/game/lv2/yung")
      preload.Load(r9_0, "data/game/lv3/yung")
      r11_0 = true
    end
    local r2_24, r3_24 = r25_0(1, r0_24.x, r0_24.y)
    local r4_24, r5_24 = r25_0(2, r0_24.x, r0_24.y)
    local r6_24 = display.newRect(_G.MyTgRoot, r0_24.x - 40, r0_24.y - 40, 80, 80)
    r6_24.alpha = 0.01
    r6_24.struct = r0_24
    r6_24.touch = r0_24.func.circle
    r6_24:addEventListener("touch", r6_24)
    r0_24.touch_area = r6_24
    r0_24.waitAnime = r2_24
    r0_24.waitCharRt = r3_24
    r0_24.attAnime = r4_24
    r0_24.attCharRt = r5_24
    r0_24.anime = r2_24
    r0_24.spr = nil
    r0_24.spr = r3_24
    r0_24.func.load = r33_0
    r0_24.shot_frame_nr = 9
    r0_24.shot_number = 0
    r0_24.char_anime_number = 1
    if _G.GameMode == _G.GameModeEvo then
      r0_24.func.changeSprite = r24_0
      r0_24.func.rankTable = r4_0
    end
    return r0_24
  end,
  Cleanup = function()
    -- line: [708, 710] id: 25
    r11_0 = false
  end,
  check_func = function(r0_17, r1_17)
    -- line: [520, 534] id: 17
    if not r1_17.living then
      return false
    end
    local r2_17 = r0_17.x
    local r3_17 = r0_17.y
    local r4_17 = r1_17.ptdiff
    local r5_17 = r1_17.sx + r4_17[1]
    local r6_17 = r1_17.sy + r4_17[2]
    local r9_17 = r0_17.range[2] >= (r2_17 - r5_17) * (r2_17 - r5_17) + (r3_17 - r6_17) * (r3_17 - r6_17)
  end,
  range_func = function(r0_21, r1_21, r2_21)
    -- line: [593, 661] id: 21
    if game ~= nil and game.IsNotPauseTypeNone() and game.GetPauseType() ~= r8_0.PauseTypeStopClock then
      return true
    end
    local r3_21 = r1_21.func
    if r7_0.SelectTarget and r7_0.SelectTarget ~= r1_21.target then
      local r4_21 = r7_0.SelectTarget.attack
      local r5_21 = r1_21.attack
      if (r4_21[1] and r5_21[1] or r4_21[2] and r5_21[2]) and r3_21.check(r1_21, r7_0.SelectTarget) then
        r1_21.target = r7_0.SelectTarget
      end
    end
    if r1_21.target then
      if not r1_21.target.living then
        r1_21.target = nil
      end
      if r1_21.target_cancel then
        r1_21.target = r1_21.func.search(r1_21)
        r1_21.target_cancel = false
      end
    end
    if r1_21.target then
      r1_21.vect = r3_21.pointing(r1_21, r1_21.target)
      if r1_21.shot_number < 1 and r1_21.anime ~= nil then
        r1_21.shot_number = r1_21.shot_number + 1
        r26_0(r1_21, 1)
        r1_21.in_shot_ev = events.Register(function()
          -- line: [636, 646] id: 22
          if r1_21.anime ~= nil then
            r26_0(r1_21, 2)
            anime.RegisterTrigger(r1_21.anime, r1_21.shot_frame_nr, r3_21.shoot, r1_21)
            anime.RegisterTrigger2(r1_21.anime, 15, function(r0_23, r1_23)
              -- line: [640, 642] id: 23
              r27_0(r1_21)
            end, r1_21)
            anime.RegisterFinish(r1_21.anime, r3_21.finish, r1_21)
          end
          return false
        end, {}, r2_0[r1_21.level])
        table.insert(_G.ShotEvent, r1_21.in_shot_ev)
      end
    end
    if r1_21.target == nil then
      r1_21.target = r3_21.search(r1_21)
      if r1_21.target == nil and not r1_21.shooting then
        r29_0(r1_21)
        r28_0(r1_21)
      end
    end
    return true
  end,
  pointing_func = function(r0_16, r1_16)
    -- line: [500, 517] id: 16
    local r4_16 = r1_16.ptdiff
    local r10_16 = (math.floor((math.atan2((r1_16.sy + r4_16[2] - r0_16.y), (r1_16.sx + r4_16[1] - r0_16.x)) * 8 / math.pi + 7) / 2) + 3) % 8 + 1
    if r0_16.anime then
      anime.ChangeVect(r0_16.anime, r10_16)
    end
    return r10_16
  end,
  shoot_func = function(r0_18, r1_18)
    -- line: [537, 553] id: 18
    local r2_18 = r1_18.func
    if r1_18.target == nil then
      if r2_18.fumble then
        r2_18.fumble(r1_18)
      end
      return 
    end
    if r1_18.next_target then
      r1_18.target = r1_18.next_target
      r1_18.next_target = nil
    end
    r2_18.pointing(r1_18, r1_18.target)
    local r3_18 = anime.GetPos(r0_18)
    r2_18.load(r3_18[1], r3_18[2], r1_18.target, r1_18)
  end,
  search_func = function(r0_19)
    -- line: [557, 590] id: 19
    local function r1_19(r0_20)
      -- line: [558, 564] id: 20
      local r1_20 = r0_20.sx
      local r2_20 = r0_20.sy
      local r3_20 = r0_19.x
      local r4_20 = r0_19.y
      return (r1_20 - r3_20) * (r1_20 - r3_20) + (r2_20 - r4_20) * (r2_20 - r4_20)
    end
    local r2_19 = nil
    local r3_19 = r0_19.x
    local r4_19 = r0_19.y
    local r5_19 = r0_19.range[1]
    local r6_19 = r0_19.range[2]
    local r7_19 = 1000000
    local r8_19 = 0
    local r9_19 = 1000000
    for r13_19, r14_19 in pairs(_G.Enemys) do
      if r14_19.living and r14_19.attack[2] == false then
        local r15_19 = r14_19.ptdiff
        local r16_19 = r14_19.sx + r15_19[1]
        local r17_19 = r14_19.sy + r15_19[2]
        local r18_19 = (r16_19 - r3_19) * (r16_19 - r3_19) + (r17_19 - r4_19) * (r17_19 - r4_19)
        if r5_19 <= r18_19 and r18_19 <= r6_19 then
          local r19_19 = r1_19(r14_19)
          if r19_19 < r7_19 then
            r7_19 = r19_19
            r2_19 = r14_19
          end
        end
      end
    end
    return r2_19
  end,
  finish_func = function(r0_12, r1_12)
    -- line: [326, 334] id: 12
    if r1_12.target then
      r1_12.target = nil
    end
    r1_12.shooting = false
    anime.Pause(r0_12, true)
    anime.SetTimer(r0_12, 0)
  end,
  custom_kill_char = function(r0_27)
    -- line: [716, 718] id: 27
    r28_0(r0_27)
  end,
  custom_release_ok = function(r0_26)
    -- line: [712, 714] id: 26
    return false
  end,
}, {
  __index = require("char.Char"),
})
