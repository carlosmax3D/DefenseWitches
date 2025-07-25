-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("logic.char.CharStatus")
local r1_0 = require("logic.game.GameStatus")
local r2_0 = nil
if _G.GameMode == _G.GameModeEvo then
  r2_0 = require("evo.char_tbl.tbl_c10_lyra").CreateTable()
end
local r3_0 = {
  "c10_lyra01_1_0",
  "c10_lyra01_1_1",
  "c10_lyra01_1_2",
  "c10_lyra01_1_3",
  "c10_lyra03_0_0",
  "c10_lyra03_0_1",
  "c10_lyra05_0_0",
  "c10_lyra05_1_0",
  "afx05_windspear",
  "zako_shadow"
}
local r4_0 = false
local r5_0 = {}
for r9_0 = 1, 6, 1 do
  table.insert(r5_0, require(string.format("char.c10.c10_lyra%02d", r9_0)))
end
local r6_0 = 0.32
local r7_0 = 700
local r8_0 = {
  {
    0,
    0
  },
  {
    32,
    -32
  },
  {
    32,
    32
  },
  {
    -32,
    -32
  },
  {
    -32,
    32
  }
}
local r9_0 = 128
local r10_0 = _G.Width - 128
local function r11_0(r0_1)
  -- line: [44, 58] id: 1
  local r1_1 = r0_1.target
  if r1_1 == nil then
    return nil
  end
  if r0_1.data == nil then
    return nil
  end
  if r0_1.data.chase == nil then
    return nil
  end
  local r2_1 = r0_1.data.chase
  local r3_1 = r2_1.moveto
  local r4_1 = r2_1.vect
  assert(r4_1, debug.traceback())
  assert(r3_1, debug.traceback())
  local r5_1 = r1_1.lyra[r4_1]
  if r5_1[r3_1] > 0 then
    r5_1[r3_1] = r5_1[r3_1] - 1
  end
  return r2_1
end
local function r12_0(r0_2, r1_2, r2_2)
  -- line: [61, 87] id: 2
  if r1_2 then
    return {
      my = r0_2,
      anime = {
        anime.Pause(r0_2.anime, true),
        anime.Pause(r0_2.data.motion[1], true),
        anime.Pause(r0_2.data.motion[2], true),
        anime.Pause(r0_2.data.motion[3], true),
        anime.Pause(r0_2.data.motion[4], true),
        anime.Pause(r0_2.data.motion[5], true)
      },
    }
  else
    assert(r2_2)
    anime.Pause(r0_2.anime, r2_2.anime[1])
    anime.Pause(r0_2.data.motion[1], r2_2.anime[2])
    anime.Pause(r0_2.data.motion[2], r2_2.anime[3])
    anime.Pause(r0_2.data.motion[3], r2_2.anime[4])
    anime.Pause(r0_2.data.motion[4], r2_2.anime[5])
    anime.Pause(r0_2.data.motion[5], r2_2.anime[6])
    return nil
  end
end
local function r13_0(r0_3, r1_3, r2_3)
  -- line: [97, 143] id: 3
  if game ~= nil and game.IsNotPauseTypeNone() then
    return true
  end
  local r3_3 = r1_3.data.landing
  local r4_3 = r3_3.ms + r2_3
  if r4_3 > 999 then
    r4_3 = 999
  end
  r3_3.ms = r4_3
  local r5_3 = nil
  local r6_3 = nil
  local r7_3 = nil
  if r4_3 < 666.6667 then
    r5_3 = r3_3.sx + r3_3.lx * r4_3 / 666.6667
    r6_3 = r3_3.sy + r3_3.ly * r4_3 / 666.6667 - 80 * math.sin(math.pi * r4_3 / 666.6667)
  else
    r5_3 = r3_3.ex
    r6_3 = r3_3.ey
  end
  anime.Move(r3_3.anime, r5_3, r6_3)
  r1_3.data.x = r5_3
  r1_3.data.y = r6_3
  if r4_3 >= 999 then
    local r8_3 = anime.GetImageScale(r1_3.anime)
    r3_3.ev = nil
    r1_3.data.flying = false
    anime.Move(r1_3.anime, r3_3.ex, r3_3.ey)
    anime.Pause(r1_3.anime, true)
    anime.SetImageScale(r1_3.anime, r8_3[1], r8_3[2])
    anime.Show(r1_3.anime, true)
    anime.Show(r1_3.data.motion[1], false)
    anime.Show(r1_3.data.motion[5], false)
    anime.Pause(r1_3.data.motion[4], true)
    anime.Show(r1_3.data.motion[4], true)
    r1_3.sort_sprite:insert(r1_3.spr)
    r1_3.shooting = false
    r1_3.data.flying = false
    return false
  else
    return true
  end
end
local function r14_0(r0_4, r1_4, r2_4)
  -- line: [145, 168] id: 4
  r0_4.data.shadow.isVisible = false
  local r3_4 = r0_4.data.motion[1]
  local r4_4 = anime.GetImageScale(r0_4.anime)
  anime.Move(r3_4, r1_4, r2_4)
  anime.Pause(r3_4, false)
  anime.SetImageScale(r3_4, r4_4[1], r4_4[2])
  anime.Show(r3_4, true)
  r0_4.data.landing = {}
  local r5_4 = r0_4.data.landing
  r5_4.ms = 0
  r5_4.sx = r1_4
  r5_4.sy = r2_4
  r5_4.ex = r0_4.x
  r5_4.ey = r0_4.y
  r5_4.lx = r5_4.ex - r5_4.sx
  r5_4.ly = r5_4.ey - r5_4.sy
  r5_4.anime = r3_4
  r5_4.ev = events.Register(r13_0, r0_4, 1)
  anime.Pause(r0_4.data.motion[5], false)
  anime.Show(r0_4.data.motion[5], true)
  anime.Show(r0_4.data.motion[4], false)
end
local function r15_0(r0_5, r1_5, r2_5)
  -- line: [170, 209] id: 5
  if game ~= nil and game.IsNotPauseTypeNone() then
    return true
  end
  if r1_5.data == nil or r1_5.data.back == nil then
    return false
  end
  local r3_5 = r1_5.data.back
  local r4_5 = r3_5.sx
  local r5_5 = r3_5.sy
  local r6_5 = r3_5.ex
  local r7_5 = r3_5.ey
  if (r4_5 - r6_5) * (r4_5 - r6_5) + (r5_5 - r7_5) * (r5_5 - r7_5) < r1_5.range[2] then
    if r3_5.anime then
      anime.Show(r3_5.anime, false)
    end
    r14_0(r1_5, r4_5, r5_5)
    r3_5.ev = nil
    return false
  end
  r4_5 = r4_5 + r3_5.mx * r2_5 * r6_0
  r5_5 = r5_5 + r3_5.my * r2_5 * r6_0
  anime.Move(r3_5.anime, r4_5, r5_5)
  r3_5.sx = r4_5
  r3_5.sy = r5_5
  local r12_5 = r1_5.data.shadow
  r12_5.x = r4_5
  r12_5.y = r5_5 + 80
  return true
end
local function r16_0(r0_6)
  -- line: [212, 281] id: 6
  local r1_6 = r0_6.data.chase
  local r2_6 = r0_6.data.shooting
  local r3_6 = r0_6.data.moving
  if r1_6 and r1_6.ev then
    events.Delete(r1_6.ev)
    r1_6.ev = nil
  end
  if r2_6 and r2_6.ev then
    events.Delete(r2_6.ev)
    r2_6.ev = nil
  end
  if r3_6 and r3_6.ev then
    events.Delete(r3_6.ev)
    r3_6.ev = nil
  end
  anime.Show(r0_6.anime, false)
  local r4_6 = r0_6.data.x
  local r5_6 = r0_6.data.y
  r11_0(r0_6)
  r0_6.target = r0_6.func.search(r0_6)
  if r0_6.target then
    if r1_6 then
      r1_6.anime = nil
    end
    r0_6.func.chase_func(nil, r0_6)
    return 
  end
  if r1_6 then
    anime.Show(r1_6.anime, false)
  end
  r0_6.data.back = {}
  local r6_6 = r0_6.data.back
  r6_6.sx = r4_6
  r6_6.sy = r5_6
  r6_6.ex = r0_6.x
  r6_6.ey = r0_6.y
  local r7_6 = math.sqrt((r6_6.ex - r6_6.sx) * (r6_6.ex - r6_6.sx) + (r6_6.ey - r6_6.sy) * (r6_6.ey - r6_6.sy))
  if r7_6 - r0_6.range_circle * 0.5 < 0 then
    r14_0(r0_6, r6_6.sx, r6_6.sy)
  else
    r6_6.mx = (r6_6.ex - r6_6.sx) / r7_6
    r6_6.my = (r6_6.ey - r6_6.sy) / r7_6
    r6_6.ev = events.Register(r15_0, r0_6, 1)
    local r9_6 = nil
    if r6_6.sx < r6_6.ex then
      r9_6 = 3
    else
      r9_6 = 2
    end
    local r10_6 = r0_6.data.motion[r9_6]
    local r11_6 = anime.GetImageScale(r0_6.anime)
    r6_6.anime = r10_6
    anime.Move(r10_6, r6_6.sx, r6_6.sy)
    anime.Pause(r10_6, false)
    anime.SetImageScale(r10_6, r11_6[1], r11_6[2])
    anime.Show(r10_6, true)
    anime.Loop(r10_6, true)
    local r12_6 = r0_6.data.shadow
    r12_6.x = r6_6.sx
    r12_6.y = r6_6.sy + 80
  end
end
local function r17_0(r0_7, r1_7, r2_7)
  -- line: [283, 316] id: 7
  if game ~= nil and game.IsNotPauseTypeNone() then
    return true
  end
  local r4_7 = r1_7.data.x
  local r5_7 = r1_7.data.y
  if r1_7.data.chase.vect == 1 then
    r4_7 = r4_7 - 32
  else
    r4_7 = r4_7 + 32
  end
  local r6_7 = display.newImage("data/game/afx05_windspear.png", true)
  r6_7:setReferencePoint(display.CenterReferencePoint)
  r6_7.x = r4_7
  r6_7.y = r5_7
  r6_7.isVisible = true
  local r7_7 = {
    group = nil,
    sprite = r6_7,
    index = 0,
    xy = {
      r4_7,
      r5_7
    },
    target = r1_7.target,
    hit = false,
    my = r1_7,
  }
  _G.MissleRoot:insert(r6_7)
  table.insert(_G.ShotEvent, events.Register(require("char.char05").ShotFunc, r7_7, 0, false))
  sound.PlaySE(15, 11)
  return true
end
local function r18_0(r0_8, r1_8)
  -- line: [319, 333] id: 8
  local r2_8 = r0_8.data.chase
  local r3_8 = r0_8.data.shooting
  if r2_8 and r2_8.ev then
    events.Delete(r2_8.ev)
    r2_8.ev = nil
  end
  if r3_8 and r3_8.ev then
    events.Delete(r3_8.ev)
    r3_8.ev = nil
  end
  r11_0(r0_8)
  r0_8.func.chase_func(nil, r0_8, r1_8)
end
local function r19_0(r0_9, r1_9, r2_9)
  -- line: [336, 409] id: 9
  if game ~= nil and game.IsNotPauseTypeNone() then
    return true
  end
  local r3_9 = r1_9.data.chase
  local r4_9 = r1_9.data.moving
  if r1_9.target == nil or not r1_9.target.living then
    r16_0(r1_9)
    if r4_9 then
      r4_9.ev = nil
    end
    return false
  end
  local r5_9 = r0_0.SelectTarget
  local r6_9 = r1_9.target
  if r5_9 and r5_9 ~= r6_9 and r5_9.attack[2] and r1_9.func.check(r1_9, r5_9) then
    local r8_9 = r11_0(r1_9)
    if r8_9 and r8_9.ev then
      events.Delete(r8_9.ev)
      r8_9.ev = nil
    end
    if r1_9.data.shooting and r1_9.data.shooting.ev then
      events.Delete(r1_9.data.shooting.ev)
      r1_9.data.shooting.ev = nil
    end
    r1_9.target = r5_9
    r1_9.func.chase_func(nil, r1_9)
    return true
  end
  local r7_9 = r3_9.vect
  local r8_9 = r3_9.moveto
  local r9_9 = r1_9.target.sx
  local r10_9 = r1_9.target.sy
  if r7_9 == 1 then
    r9_9 = r9_9 + 120
  else
    r9_9 = r9_9 - 120
  end
  r9_9 = r9_9 + r8_0[r8_9][1]
  r10_9 = r10_9 + r8_0[r8_9][2]
  if r7_9 == 1 and r10_0 < r9_9 then
    r18_0(r1_9, 2)
    if r4_9 then
      r4_9.ev = nil
    end
    return false
  elseif r7_9 == 2 and r9_9 < r9_0 then
    r18_0(r1_9, 1)
    if r4_9 then
      r4_9.ev = nil
    end
    return false
  else
    anime.Move(r3_9.anime, r9_9, r10_9)
    r1_9.data.x = r9_9
    r1_9.data.y = r10_9
    local r11_9 = r1_9.data.shadow
    r11_9.x = r9_9
    r11_9.y = r10_9 + 80
    return true
  end
end
local function r20_0(r0_10, r1_10, r2_10)
  -- line: [411, 481] id: 10
  if game ~= nil and game.IsNotPauseTypeNone() then
    return true
  end
  local r3_10 = r1_10.data.chase
  local r4_10 = r1_10.target
  if r4_10 == nil then
    r16_0(r1_10)
    return false
  end
  local r5_10 = r3_10.sx
  local r6_10 = r3_10.sy
  local r7_10 = r4_10.sx
  local r8_10 = r4_10.sy
  local r9_10 = r3_10.moveto
  if r3_10.vect == 1 then
    r7_10 = r7_10 + 120
  else
    r7_10 = r7_10 - 120
  end
  r7_10 = r7_10 + r8_0[r9_10][1]
  r8_10 = r8_10 + r8_0[r9_10][2]
  local r11_10 = r7_10 - r5_10
  local r12_10 = r8_10 - r6_10
  local r13_10 = r11_10 * r11_10 + r12_10 * r12_10
  local r14_10 = r1_10.data.shadow
  if r13_10 < 400 then
    anime.Move(r3_10.anime, r7_10, r8_10)
    r1_10.data.x = r7_10
    r1_10.data.y = r8_10
    r14_10.x = r7_10
    r14_10.y = r8_10 + 80
    r1_10.data.shooting = {}
    r1_10.data.shooting.ev = events.Register(r17_0, r1_10, r7_0 * 100 / (r1_10.wait[r1_10.level] + r0_0.AttackSpeed))
    r1_10.data.moving = {}
    r1_10.data.moving.ev = events.Register(r19_0, r1_10, 1)
    r3_10.ev = nil
    return false
  else
    r13_10 = math.sqrt(r13_10)
    local r17_10 = r5_10 + r11_10 / r13_10 * r2_10 * r6_0
    local r18_10 = r6_10 + r12_10 / r13_10 * r2_10 * r6_0
    anime.Move(r3_10.anime, r17_10, r18_10)
    r1_10.data.x = r17_10
    r1_10.data.y = r18_10
    r3_10.sx = r17_10
    r3_10.sy = r18_10
    r14_10.x = r17_10
    r14_10.y = r18_10 + 80
    return true
  end
end
local function r21_0(r0_11, r1_11, r2_11)
  -- line: [484, 559] id: 11
  if r1_11.target then
    local r3_11 = r1_11.data.x
    local r4_11 = r1_11.data.y
    anime.Move(r1_11.anime, r1_11.x, r1_11.y)
    anime.Show(r1_11.anime, false)
    assert(r1_11.data.takeoff.ev == nil, debug.traceback())
    local r5_11 = r1_11.target.sx
    local r6_11 = r1_11.target.sy
    local r7_11 = r3_11
    local r8_11 = r4_11
    local r9_11 = anime.GetImageScale(r1_11.anime)
    local r10_11 = nil
    local r11_11 = nil
    if r2_11 then
      r10_11 = r2_11
    elseif r5_11 < r9_0 or r5_11 <= r7_11 then
      r10_11 = 1
    else
      r10_11 = 2
    end
    anime.Show(r1_11.data.motion[2], false)
    anime.Show(r1_11.data.motion[3], false)
    if r10_11 == 1 then
      r11_11 = r1_11.data.motion[2]
    else
      r11_11 = r1_11.data.motion[3]
    end
    anime.Move(r11_11, r7_11, r8_11)
    anime.Pause(r11_11, false)
    anime.SetImageScale(r11_11, r9_11[1], r9_11[2])
    anime.Show(r11_11, true)
    anime.Loop(r11_11, true)
    local r12_11 = r1_11.data.shadow
    r12_11.x = r7_11
    r12_11.y = r8_11 + 80
    r12_11.isVisible = true
    r1_11.data.chase = {}
    r1_11.data.chase.anime = r11_11
    r1_11.data.chase.vect = r10_11
    r1_11.data.chase.ms = 0
    r1_11.data.chase.sx = r7_11
    r1_11.data.chase.sy = r8_11
    assert(r1_11.target.lyra)
    local r13_11 = r1_11.target.lyra[r10_11]
    local r14_11 = 1
    for r18_11 = 1, 5, 1 do
      if r13_11[r18_11] == 0 then
        r14_11 = r18_11
        break
      end
    end
    if r14_11 == 1 then
      r1_11.data.sprite[2]:toFront()
      r1_11.data.sprite[3]:toFront()
    end
    r13_11[r14_11] = r13_11[r14_11] + 1
    r1_11.data.chase.moveto = r14_11
    r1_11.data.chase.ev = events.Register(r20_0, r1_11, 1)
  else
    r16_0(r1_11)
  end
end
local function r22_0(r0_12, r1_12, r2_12)
  -- line: [568, 605] id: 12
  if game ~= nil and game.IsNotPauseTypeNone() then
    return true
  end
  local r3_12 = r1_12.data.takeoff
  local r5_12 = nil
  local r4_12 = r3_12.ms + r2_12
  if r4_12 < 333.3333 then
    r5_12 = 0
  elseif r4_12 < 666.6667 then
    r5_12 = 160 * (r4_12 - 333.3333) / 333.3333
  elseif r4_12 < 1000 then
    r5_12 = 160
  else
    r4_12 = 1000
    r5_12 = 160
  end
  r1_12.data.takeoff.ms = r4_12
  r1_12.data.x = r3_12.sx
  r1_12.data.y = r3_12.sy - r5_12
  anime.Move(r1_12.anime, r3_12.sx, r3_12.sy - r5_12)
  if r4_12 >= 999 then
    r1_12.data.takeoff.ev = nil
    return false
  else
    return true
  end
end
local function r23_0(r0_13, r1_13, r2_13)
  -- line: [607, 683] id: 13
  if game ~= nil and game.IsNotPauseTypeNone() then
    return true
  end
  local r3_13 = r1_13
  local r4_13 = r3_13.target
  if r4_13 then
    if not r4_13.living then
      r11_0(r3_13)
      r3_13.target = nil
      r4_13 = nil
    end
    if r3_13.target_cancel then
      r11_0(r3_13)
      r3_13.target = nil
      r3_13.target_cancel = false
      r4_13 = nil
    end
  end
  if r4_13 and not r3_13.shooting then
    if r3_13.func.check(r3_13, r4_13) then
      r3_13.vect = 1
      r3_13.shooting = true
      r3_13.data.flying = true
      local r5_13 = r3_13.anime
      local r6_13 = anime.GetImageScale(r3_13.anime)
      anime.Pause(r3_13.anime, false)
      anime.SetImageScale(r3_13.anime, r6_13[1], r6_13[2])
      anime.Show(r3_13.anime, true)
      _G.LyraRoot:insert(r3_13.spr)
      if r3_13.data.takeoff and r3_13.data.takeoff.ev then
        events.Delete(r3_13.data.takeoff.ev)
        r3_13.data.takeoff.ev = nil
      end
      r3_13.data.takeoff = {}
      r3_13.data.takeoff.ms = 0
      r3_13.data.takeoff.sx = r3_13.x
      r3_13.data.takeoff.sy = r3_13.y
      r3_13.data.takeoff.ev = events.Register(r22_0, r3_13, 1)
      local r7_13 = r3_13.data.motion[4]
      anime.Pause(r7_13, false)
      anime.Show(r7_13, true)
      anime.RegisterFinish(r5_13, r21_0, r3_13)
    elseif not r3_13.shooting then
      r11_0(r3_13)
      r3_13.target = nil
    end
  end
  if not r3_13.data.flying and r4_13 == nil then
    local r5_13 = r0_0.SelectTarget
    if r5_13 and r5_13.attack[2] and r3_13.func.check(r3_13, r5_13) then
      r3_13.target = r5_13
    else
      r3_13.target = r3_13.func.search(r3_13)
    end
  end
  return true
end
local function r24_0(r0_14, r1_14)
  -- line: [686, 694] id: 14
  local r3_14 = r0_14.struct
  if r1_14.phase == "ended" and not r3_14.data.flying then
    return r3_14.func.circle(r0_14, r1_14)
  end
  return false
end
local function r25_0(r0_15, r1_15)
  -- line: [697, 705] id: 15
  local r3_15 = r0_15.struct
  if r1_15.phase == "ended" and r3_15.data.flying then
    return r3_15.func.circle(r0_15, r1_15)
  end
  return false
end
local function r26_0(r0_16)
  -- line: [708, 745] id: 16
  local r1_16 = r0_16.data
  for r5_16, r6_16 in pairs(r1_16.motion) do
    anime.Remove(r6_16)
  end
  r1_16.motion = nil
  local r2_16 = r1_16.chase
  local r3_16 = r1_16.shooting
  local r4_16 = r1_16.moving
  local r5_16 = r1_16.landing
  local r6_16 = r1_16.back
  r11_0(r0_16)
  if r2_16 and r2_16.ev then
    events.Delete(r2_16.ev)
    r2_16.ev = nil
  end
  if r3_16 and r3_16.ev then
    events.Delete(r3_16.ev)
    r3_16.ev = nil
  end
  if r4_16 and r4_16.ev then
    events.Delete(r4_16.ev)
    r4_16.ev = nil
  end
  if r5_16 and r5_16.ev then
    events.Delete(r5_16.ev)
    r5_16.ev = nil
  end
  if r6_16 and r6_16.ev then
    events.Delete(r6_16.ev)
    r6_16.ev = nil
  end
end
local function r27_0(r0_17, r1_17)
  -- line: [747, 755] id: 17
  anime.ReplaceSprite(r0_17.anime, r1_17, r5_0[1].GetData())
  for r6_17 = 2, 4, 1 do
    anime.ReplaceSprite(r0_17.data.motion[r6_17 - 1], r1_17, r5_0[r6_17].GetData())
  end
  return r0_17
end
return setmetatable({
  ChangeSprite = r27_0,
  Load = function(r0_18)
    -- line: [758, 862] id: 18
    if not r4_0 then
      preload.Load(r3_0, "data/game")
      r4_0 = true
    end
    local r1_18 = nil
    local r2_18 = nil
    local r3_18 = r0_18.x
    local r4_18 = r0_18.y
    r1_18 = anime.Register(r5_0[1].GetData(), r3_18, r4_18, "data/game")
    r2_18 = anime.GetSprite(r1_18)
    r0_18.anime = r1_18
    r0_18.spr = r2_18
    r0_18.data = {}
    r0_18.data.x = r3_18
    r0_18.data.y = r4_18
    r0_18.data.motion = {}
    r0_18.data.sprite = {}
    r0_18.data.shadow = display.newImage(_G.ShadowRoot, "data/game/zako_shadow.png", r3_18, r4_18 + 80)
    r0_18.data.shadow.isVisible = false
    local r5_18 = nil
    for r9_18 = 2, 6, 1 do
      r5_18 = anime.Register(r5_0[r9_18].GetData(), r3_18, r4_18, "data/game")
      r0_18.data.sprite[r9_18 - 1] = anime.GetSprite(r5_18)
      r0_18.data.motion[r9_18 - 1] = r5_18
    end
    r0_18.data.takeoff = nil
    r0_18.data.flying = false
    _G.LyraRoot:insert(r0_18.data.sprite[1])
    _G.LyraRoot:insert(r0_18.data.sprite[2])
    _G.LyraRoot:insert(r0_18.data.sprite[3])
    _G.MarkerRoot:insert(r0_18.data.sprite[4])
    _G.MarkerRoot:insert(r0_18.data.sprite[5])
    local r6_18 = display.newRect(_G.MyTgRoot, r3_18 - 40, r4_18 - 40, 80, 80)
    r6_18.alpha = 0.01
    r6_18.struct = r0_18
    r6_18.touch = r0_18.func.circle
    r6_18:addEventListener("touch", r6_18)
    r0_18.touch_area = r6_18
    r5_18 = r0_18.data.motion[4]
    anime.Pause(r5_18, true)
    anime.Show(r5_18, true)
    anime.RegisterFinish(r5_18, function(r0_19, r1_19)
      -- line: [817, 817] id: 19
      anime.SetTimer(r0_19, r0_19.stop)
    end, r0_18)
    r5_18 = r0_18.data.motion[5]
    anime.Pause(r5_18, true)
    anime.Show(r5_18, true)
    anime.RegisterFinish(r5_18, function(r0_20, r1_20)
      -- line: [824, 824] id: 20
      anime.SetTimer(r0_20, r0_20.stop)
    end, r0_18)
    anime.RegisterFinish(r0_18.data.sprite[2], r0_18.func.finish, r0_18)
    r0_18.func.range = r23_0
    function r0_18.func.pointing(r0_21, r1_21)
      -- line: [832, 832] id: 21
      return 1
    end
    r0_18.func.pause = r12_0
    r0_18.func.chase_func = r21_0
    r0_18.func.release = r26_0
    r0_18.shot_frame_nr = 1
    if _G.GameMode == _G.GameModeEvo then
      r0_18.func.changeSprite = r27_0
      r0_18.func.rankTable = r2_0
    end
    return r0_18
  end,
  Cleanup = function()
    -- line: [864, 866] id: 22
    r4_0 = false
  end,
}, {
  __index = require("char.Char"),
})
