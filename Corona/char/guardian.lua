-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("logic.char.CharStatus")
local r1_0 = "data/game/lililala"
local r2_0 = 2000
local r4_0 = r2_0 + 500
local r5_0 = {
  960,
  1440,
  2160,
  3240
}
local r6_0 = display.newGroup()
local r7_0 = nil
local r8_0 = {
  "c14_guardian01a_0_0",
  "c14_guardian01a_1_0",
  "c14_guardian01b_1_1",
  "c14_01",
  "c14_02",
  "c14_03",
  "c14_04"
}
local r9_0 = false
local r10_0 = require("char.c14.c14_guardian01b")
local function r11_0(r0_1)
  -- line: [29, 60] id: 1
  local r1_1 = r0_1.level
  local r2_1 = r0_1.data
  local r3_1 = r2_1.view_spr
  local r4_1 = r2_1.level
  local r5_1 = r2_1.maxhp
  if r1_1 == 2 and r4_1 == 1 then
    r3_1[1].isVisible = false
    r3_1[2].isVisible = true
    r4_1 = 2
  elseif r1_1 == 3 and r4_1 < 3 then
    r3_1[1].isVisible = false
    r3_1[2].isVisible = false
    r3_1[3].isVisible = true
    r4_1 = 3
  elseif r1_1 == 4 and r4_1 < 4 then
    r3_1[1].isVisible = false
    r3_1[2].isVisible = false
    r3_1[2].isVisible = false
    r3_1[4].isVisible = true
    r4_1 = 4
  end
  r2_1.level = r4_1
end
local function r12_0(r0_2)
  -- line: [63, 106] id: 2
  if r0_2.data.progress_bar then
    events.Delete(r0_2.data.progress_bar)
    r0_2.data.progress_bar = nil
    r11_0(r0_2)
  end
  local r1_2 = r0_2.data
  assert(r1_2)
  local r2_2 = r1_2.hp
  local r3_2 = r1_2.maxhp
  assert(0 < r3_2)
  local r4_2 = r1_2.hpspr
  if r4_2 then
    display.remove(r4_2)
  end
  local r5_2 = display.newGroup()
  local r6_2 = r2_2 * 48 / r3_2
  display.newRect(r5_2, 0, 0, r6_2, 4):setFillColor(255, 127, 0, 191)
  local r7_2 = 48 - r6_2
  if r7_2 > 0 then
    display.newRect(r5_2, r6_2, 0, r7_2, 4):setFillColor(27, 38, 38, 191)
  end
  r5_2.x = r0_2.x + r1_2.htptpos[1]
  r5_2.y = r0_2.y + r1_2.htptpos[2]
  r0_2.hp_root:insert(r5_2)
  r1_2.hpspr = r5_2
  if r1_2.hpev then
    events.Delete(r1_2.hpev)
  end
  r1_2.hpev = events.Register(function(r0_3, r1_3, r2_3)
    -- line: [98, 105] id: 3
    if r1_3.data.hpspr then
      display.remove(r1_3.data.hpspr)
      r1_3.data.hpspr = nil
    end
    r1_3.data.hpev = nil
    return false
  end, r0_2, 2000)
end
local function r13_0(r0_4)
  -- line: [109, 141] id: 4
  sound.PlaySE(7, 21, true)
  local r1_4 = nil
  local r2_4 = nil
  local r3_4 = r0_4.x
  local r4_4 = r0_4.y
  local r5_4 = r0_0.SummonAnime
  r1_4 = anime.Register(r5_4[1].GetData(), r3_4, r4_4 - 40 + 63, "data/game")
  anime.RegisterFinish(r1_4, function(r0_5, r1_5)
    -- line: [117, 117] id: 5
    anime.Remove(r0_5)
  end, nil)
  anime.Show(r1_4, true)
  _G.SBaseRoot:insert(anime.GetSprite(r1_4))
  anime.Pause(r1_4, false)
  anime.Show(r1_4, true)
  _G.MyRoot:insert(r0_4.sort_sprite)
  r1_4 = anime.Register(r5_4[2].GetData(), r3_4, r4_4 - 40, "data/game")
  anime.RegisterTrigger(r1_4, 8, function(r0_6, r1_6)
    -- line: [128, 130] id: 6
    r1_6.sort_sprite.isVisible = false
  end, r0_4)
  anime.RegisterFinish(r1_4, function(r0_7, r1_7)
    -- line: [132, 135] id: 7
    anime.Remove(r0_7)
    r1_7.func.destructor(r1_7)
  end, r0_4)
  anime.Show(r1_4, true)
  _G.SFrontRoot:insert(anime.GetSprite(r1_4))
  anime.Pause(r1_4, false)
  anime.Show(r1_4, true)
end
local function r14_0(r0_8, r1_8, r2_8)
  -- line: [143, 177] id: 8
  local r3_8 = r1_8.ms + r2_8
  if r4_0 <= r3_8 then
    r3_8 = r4_0
    if game ~= nil and game.IsNotPauseTypeNone() then
      return true
    end
  end
  r1_8.ms = r3_8
  local r4_8 = r3_8 / r2_0
  local r7_8 = 1 + r4_8 / 2
  local r9_8 = r1_8.spr
  r9_8.x = r1_8.sx + r1_8.lx * r4_8
  r9_8.y = r1_8.sy + r1_8.ly * r4_8 - 120 * math.sin(math.pi * r4_8)
  r9_8.xScale = r7_8
  r9_8.yScale = r7_8
  r9_8.rotation = 270 * r4_8
  if r4_0 <= r3_8 then
    display.remove(r1_8.spr)
    local r10_8 = r1_8.struct
    r10_8.data.finish_ev = nil
    r10_8.func.destructor(r10_8)
    r13_0(r10_8.data.lililala[1])
    r13_0(r10_8.data.lililala[2])
    return false
  end
  return true
end
local function r15_0(r0_9, r1_9)
  -- line: [180, 237] id: 9
  local r2_9 = r1_9.data
  if r2_9.hp <= 0 then
    local r4_9 = table.indexOf(_G.Guardians, r1_9)
    assert(r4_9)
    table.remove(_G.Guardians, r4_9)
    anime.Show(r0_9, false)
    local r5_9 = r1_9.x
    local r6_9 = r1_9.y
    local r7_9 = display.newImage(_G.BackwardRoot, r1_0 .. "/c14_guardian01b_1_1.png", 0, 0)
    r7_9:setReferencePoint(display.CenterReferencePoint)
    r7_9.x = r5_9
    r7_9.y = r6_9
    local r8_9 = nil
    if r2_9.vect > 0 then
      r8_9 = _G.Width + 128 + math.random(50, 150)
    else
      r8_9 = -128 - math.random(50, 150)
    end
    r2_9.finish_ev = events.Register(r14_0, {
      sx = r5_9,
      sy = r6_9,
      ex = r8_9,
      ey = r6_9,
      lx = r8_9 - r5_9,
      ly = 0,
      spr = r7_9,
      struct = r1_9,
      ms = 0,
      ev = nil,
    }, 1)
    local r10_9 = r2_9.lililala
    local r11_9 = assert
    local r12_9 = type(r10_9)
    if r12_9 == "table" then
      r12_9 = #r10_9 == 2
    else
      r12_9 = r12_9 == "table"	-- block#6 is visited secondly
    end
    r11_9(r12_9)
    for r14_9, r15_9 in pairs(r10_9) do
      anime.SetTimer(r15_9.anime, 0)
      anime.Pause(r15_9.anime, true)
      if r15_9.data.nimbus and r15_9.data.nimbus.anm then
        anime.Show(r15_9.data.nimbus.anm, false)
      end
      r15_9.data.is_released = true
    end
  else
    r2_9.bend_flag = false
    anime.Show(r1_9.anime, false)
    r2_9.view_spr[1].isVisible = true
  end
end
local function r16_0(r0_10)
  -- line: [240, 255] id: 10
  local r1_10 = r0_10.data
  if r1_10.bend_flag then
    return 
  end
  r1_10.bend_flag = true
  for r5_10, r6_10 in pairs(r1_10.view_spr) do
    r6_10.isVisible = false
  end
  local r2_10 = r0_10.anime
  anime.Pause(r2_10, false)
  anime.Show(r2_10, true)
  anime.RegisterFinish(r2_10, r15_0, r0_10)
end
local function r17_0(r0_11, r1_11)
  -- line: [258, 358] id: 11
  local r2_11 = r0_11.data.hp
  local r3_11 = r1_11.hitpoint
  local r4_11 = r0_11.buff_power
  if r4_11 > 8 then
    r4_11 = 8
  end
  r3_11 = r3_11 * (9 - r4_11) / 8
  if r2_11 <= r3_11 then
    r1_11.func.hit(r1_11, r2_11)
    r0_11.data.hp = 0
    r12_0(r0_11)
    r16_0(r0_11)
    return false
  else
    r1_11.func.hit(r1_11, r1_11.hitpoint)
    r2_11 = r2_11 - r3_11
    r0_11.data.hp = r2_11
    r12_0(r0_11)
    local r5_11 = r0_11.data.maxhp
    local r6_11 = r0_11.level
    local r7_11 = r0_11.data.view_spr
    local r8_11 = r0_11.data.level
    local r9_11 = false
    if r8_11 == 1 then
      r16_0(r0_11)
    end
    if r2_11 <= r5_11 * 0.25 and r8_11 > 1 then
      r7_11[r8_11].isVisible = false
      r7_11[1].isVisible = true
      r8_11 = 1
    elseif r6_11 == 2 and r8_11 == 2 and r2_11 <= r5_11 * 0.5 then
      r7_11[1].isVisible = true
      r7_11[2].isVisible = false
      r8_11 = 1
      r9_11 = true
    elseif r6_11 == 3 and r8_11 == 3 and r2_11 <= r5_11 * 0.5 then
      r7_11[2].isVisible = true
      r7_11[3].isVisible = false
      r8_11 = 2
      r9_11 = true
    elseif r6_11 == 4 then
      if r8_11 == 3 and r2_11 <= r5_11 * 0.5 then
        r7_11[2].isVisible = true
        r7_11[3].isVisible = false
        r8_11 = 2
        r9_11 = true
      elseif r8_11 == 4 and r2_11 <= r5_11 * 0.75 then
        r7_11[3].isVisible = true
        r7_11[4].isVisible = false
        r8_11 = 3
        r9_11 = true
      end
    end
    r0_11.data.level = r8_11
    if r9_11 then
      if r0_11.data.burst_anm then
        anime.Remove(r0_11.data.burst_anm)
        r0_11.data.burst_anm = nil
      end
      local r13_11 = anime.Register(enemy.GetBurstEfx().GetData(), r0_11.x, r0_11.y, "data/game")
      r0_11.burst_rt:insert(anime.GetSprite(r13_11))
      anime.RegisterShowHook(r13_11, function(r0_12, r1_12, r2_12, r3_12)
        -- line: [343, 346] id: 12
        r0_12.xScale = r0_12.xScale * 2
        r0_12.yScale = r0_12.yScale * 2
      end, nil)
      anime.RegisterFinish(r13_11, function(r0_13, r1_13)
        -- line: [348, 351] id: 13
        anime.Remove(r0_13)
        r1_13.data.burst_anm = nil
      end, r0_11)
      r0_11.data.burst_anm = r13_11
      anime.Pause(r13_11, false)
      anime.Show(r13_11, true)
    end
    return true
  end
end
local function r18_0(r0_14, r1_14)
  -- line: [361, 371] id: 14
  if r0_14 then
    anime.Remove(r0_14)
  end
  table.insert(_G.Guardians, r1_14)
  for r5_14, r6_14 in pairs(r1_14.data.lililala) do
    r6_14.data.upgrade_ok = true
  end
end
local function r19_0(r0_15)
  -- line: [373, 405] id: 15
  local r1_15 = r0_15.data
  if r1_15 then
    for r5_15, r6_15 in pairs(r1_15.view_spr) do
      display.remove(r6_15)
    end
    if r1_15.burst_anm then
      anime.Remove(r1_15.burst_anm)
      r1_15.burst_anm = nil
    end
    if r1_15.hpspr then
      display.remove(r1_15.hpspr)
      r1_15.hpspr = nil
    end
    if r1_15.hpev then
      events.Delete(r1_15.hpev)
      r1_15.hpev = nil
    end
    if r1_15.finish_ev then
      events.Delete(r1_15.finish_ev)
      r1_15.finish_ev = nil
    end
    if r1_15.progress_bar then
      events.Delete(r1_15.progress_bar)
      r1_15.progress_bar = nil
    end
  end
  local r2_15 = table.indexOf(_G.Guardians, r0_15)
  if r2_15 then
    table.remove(_G.Guardians, r2_15)
  end
end
local function r20_0(r0_16, r1_16, r2_16)
  -- line: [408, 417] id: 16
  if r1_16 then
    return {}
  else
    return nil
  end
end
local function r21_0(r0_17, r1_17, r2_17)
  -- line: [419, 507] id: 17
  if game ~= nil and game.IsNotPauseTypeNone() then
    return true
  end
  local r3_17 = r1_17.data.progress_data
  local r5_17 = r3_17.ed
  local r4_17 = r3_17.ms + r2_17
  if r5_17 < r4_17 then
    r4_17 = r5_17
  end
  r3_17.ms = r4_17
  local r6_17 = easing.linear(r4_17, r5_17, r3_17.hp, r3_17.lhp)
  local r7_17 = r3_17.maxhp
  local r8_17 = r1_17.data.hpspr
  if r8_17 then
    display.remove(r8_17)
  end
  local r9_17 = display.newGroup()
  local r10_17 = r6_17 * 48 / r7_17
  display.newRect(r9_17, 0, 0, r10_17, 4):setFillColor(255, 127, 0, 191)
  local r11_17 = 48 - r10_17
  if r11_17 > 0 then
    display.newRect(r9_17, r10_17, 0, r11_17, 4):setFillColor(27, 38, 38, 191)
  end
  r9_17.x = r3_17.x
  r9_17.y = r3_17.y
  r3_17.rtImg:insert(r9_17)
  r1_17.data.hpspr = r9_17
  local r12_17 = r1_17.level
  local r13_17 = r1_17.data.view_spr
  local r14_17 = r1_17.data.level
  if r12_17 == 2 and r14_17 == 1 and r7_17 * 0.5 < r6_17 then
    r13_17[1].isVisible = false
    r13_17[2].isVisible = true
    r14_17 = 2
  elseif r12_17 == 3 then
    if r14_17 < 3 and r7_17 * 0.5 < r6_17 then
      r13_17[1].isVisible = false
      r13_17[2].isVisible = false
      r13_17[3].isVisible = true
      r14_17 = 3
    elseif r14_17 < 2 and r7_17 * 0.25 < r6_17 then
      r13_17[1].isVisible = false
      r13_17[2].isVisible = true
      r13_17[3].isVisible = false
      r14_17 = 2
    end
  elseif r12_17 == 4 then
    if r14_17 < 4 and r7_17 * 0.75 < r6_17 then
      r13_17[1].isVisible = false
      r13_17[2].isVisible = false
      r13_17[2].isVisible = false
      r13_17[4].isVisible = true
      r14_17 = 4
    elseif r14_17 < 3 and r7_17 * 0.5 < r6_17 then
      r13_17[1].isVisible = false
      r13_17[2].isVisible = false
      r13_17[3].isVisible = true
      r13_17[4].isVisible = false
      r14_17 = 3
    elseif r14_17 < 2 and r7_17 * 0.25 < r6_17 then
      r13_17[1].isVisible = false
      r13_17[2].isVisible = true
      r13_17[3].isVisible = false
      r13_17[4].isVisible = false
      r14_17 = 2
    end
  end
  r1_17.data.level = r14_17
  if r5_17 <= r4_17 then
    r1_17.data.progress_bar = nil
    r8_17 = r1_17.data.hpspr
    if r8_17 then
      display.remove(r8_17)
    end
    r1_17.data.hpspr = nil
    return false
  end
  return true
end
local function r22_0(r0_18, r1_18)
  -- line: [510, 540] id: 18
  local r2_18 = r0_18.data
  if r2_18.progress_bar then
    events.Delete(r2_18.progress_bar)
    r2_18.progress_bar = nil
  end
  if r2_18.hpspr then
    display.remove(r2_18.hpspr)
    r2_18.hpspr = nil
  end
  local r3_18 = {
    hp = r0_18.data.hp,
  }
  r2_18.maxhp = math.round(r2_18.maxhp * r1_18)
  r2_18.hp = r2_18.maxhp
  r3_18.maxhp = r2_18.maxhp
  r3_18.lhp = r3_18.maxhp - r3_18.hp
  if r3_18.lhp > 0 then
    r3_18.rtImg = r0_18.hp_root
    r3_18.spr = nil
    r3_18.x = r0_18.x + r2_18.htptpos[1]
    r3_18.y = r0_18.y + r2_18.htptpos[2]
    r3_18.ms = 0
    r3_18.ed = 2000
    r2_18.progress_data = r3_18
    r2_18.progress_bar = events.Register(r21_0, r0_18, 1)
  end
end
local function r23_0(r0_19)
  -- line: [542, 544] id: 19
  return r0_19.data.hp, r0_19.data.maxhp
end
local function r24_0(r0_20, r1_20, r2_20, r3_20)
  -- line: [546, 591] id: 20
  assert(r0_20, debug.traceback())
  local r4_20 = assert
  local r5_20 = r1_20
  if r5_20 then
    if r1_20 >= 1 then
      r5_20 = r1_20 <= 4
    else
      r5_20 = r1_20 >= 1	-- block#3 is visited secondly
    end
  end
  r4_20(r5_20, debug.traceback())
  assert(r2_20 ~= nil, debug.traceback())
  if r2_20 > 0 then
    if r3_20 == 0 then
      r3_20 = r5_0[1]
    end
    r0_20.data.maxhp = r3_20
    r0_20.data.hp = r2_20
    r0_20.level = r1_20
    r0_20.data.level = r1_20
    if r1_20 > 1 then
      r4_20 = r0_20.data.level
      if r1_20 == 2 and r2_20 <= r3_20 * 0.5 then
        r4_20 = 1
      elseif r1_20 == 3 and r2_20 <= r3_20 * 0.5 then
        r4_20 = 2
      elseif r1_20 == 4 then
        if r2_20 <= r3_20 * 0.5 then
          r4_20 = 2
        elseif r2_20 <= r3_20 * 0.75 then
          r4_20 = 3
        end
      end
      for r8_20, r9_20 in pairs(r0_20.data.view_spr) do
        r9_20.isVisible = r8_20 == r4_20
      end
    end
  else
    r0_20.func.destructor(r0_20)
    r4_20 = r0_20.data
    r5_20 = nil
    r5_20 = r4_20.lililala[1]
    r5_20.func.destructor(r5_20)
    r5_20 = r4_20.lililala[2]
    r5_20.func.destructor(r5_20)
  end
end
local function r25_0(r0_21)
  -- line: [593, 611] id: 21
  local r1_21 = r0_21.level + 1
  r0_21.level = r1_21
  local r2_21 = r5_0[r1_21] * _G.EnemyScale
  r0_21.data.maxhp = r2_21
  r0_21.data.hp = r2_21
  r12_0(r0_21)
  r0_21.data.level = r1_21
  for r6_21, r7_21 in pairs(r0_21.data.view_spr) do
    r7_21.isVisible = r6_21 == r1_21
  end
end
return setmetatable({
  Load = function(r0_22, r1_22)
    -- line: [613, 697] id: 22
    if not r9_0 then
      preload.Load(r8_0, r1_0)
      r9_0 = true
    end
    r0_22.data = {}
    r0_22.data.maxhp = r5_0[1]
    r0_22.data.hp = r0_22.data.maxhp
    r0_22.data.hpspr = nil
    r0_22.data.hpev = nil
    r0_22.data.htptpos = {
      -24,
      -64
    }
    r0_22.data.lililala = r1_22.data.lililala
    r0_22.data.bend_flag = false
    r0_22.data.level = 1
    r0_22.data.wave_upgrade = r22_0
    local r2_22 = r0_22.x
    local r3_22 = r0_22.y
    local r4_22 = anime.Register(r10_0.GetData(), r2_22, r3_22, r1_0)
    local r5_22 = anime.GetSprite(r4_22)
    r0_22.anime = r4_22
    r0_22.spr = r5_22
    anime.Show(r4_22, false)
    local r6_22 = 1
    local r7_22 = nil
    local r8_22 = _G.RoadRoute
    for r12_22, r13_22 in pairs(r8_22) do
      if r13_22[1] == r2_22 and r13_22[2] == r3_22 then
        r7_22 = r12_22
        break
      end
    end
    assert(r7_22)
    if r7_22 and 1 < r7_22 then
      local r9_22 = r8_22[r7_22 - 1]
      local r10_22 = r9_22[1]
      if r9_22[2] == r3_22 and r2_22 < r10_22 then
        r6_22 = -1
      end
    end
    if r6_22 == -1 then
      anime.RegisterShowHook2(r4_22, function(r0_23, r1_23, r2_23, r3_23, r4_23)
        -- line: [660, 669] id: 23
        r0_23.y = r2_23.xy[2] + r3_23.pos[r1_23][2]
        r0_23.x = r2_23.xy[1] - r3_23.pos[r1_23][1]
        r0_23.xScale = -r0_23.xScale
      end, r0_22)
    end
    local r9_22 = {}
    for r13_22 = 1, 4, 1 do
      r5_22 = display.newImage(string.format(r1_0 .. "/c14_%02d.png", r13_22), 0, 0)
      r5_22.isVisible = false
      r5_22:setReferencePoint(display.CenterReferencePoint)
      r5_22.x = r2_22
      r5_22.y = r3_22
      if r6_22 == -1 then
        r5_22.xScale = -1
      end
      table.insert(r9_22, r5_22)
    end
    r0_22.data.view_spr = r9_22
    r0_22.data.vect = r6_22
    r0_22.data.progress_bar = nil
    r0_22.data.progress_data = nil
    r0_22.func.release = r19_0
    r0_22.func.shoot = r17_0
    r0_22.func.finish = r18_0
    r0_22.func.pause = r20_0
    r0_22.func.upgrade = r25_0
    r0_22.func.get_hp = r23_0
    r0_22.func.set_first = r24_0
    return r0_22
  end,
  Cleanup = function()
    -- line: [699, 701] id: 24
    r9_0 = false
  end,
  Upgrade = r25_0,
  Release = function(r0_25)
    -- line: [704, 707] id: 25
    r0_25.func.destructor(r0_25)
  end,
  CustomFirstSummon = function(r0_26, r1_26, r2_26, r3_26, r4_26, r5_26)
    -- line: [709, 721] id: 26
    local r6_26 = r5_26[5]
    local r7_26 = r5_26[6]
    local r8_26 = nil
    for r12_26, r13_26 in pairs(r0_0.MyChar) do
      r8_26 = r13_26.map_xy
      if r8_26[1] == r1_26 and r8_26[2] == r2_26 then
        r13_26.func.set_first(r13_26, r3_26, r6_26, r7_26)
        break
      end
    end
  end,
}, {
  __index = require("char.Char"),
})
