-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("logic.char.CharStatus")
local r1_0 = nil
if _G.GameMode == _G.GameModeEvo then
  r1_0 = require("evo.char_tbl.tbl_c08_lillian").CreateTable()
end
local r2_0 = {
  "c08_lillian01_0_0",
  "c08_lillian01_1_1",
  "c08_lillian01_2_0",
  "c08_lillian01_2_1",
  "c08_lillian01_2_18",
  "c08_lillian01_2_19",
  "c08_lillian01_2_2",
  "c08_lillian01_2_20",
  "c08_lillian01_2_21",
  "c08_lillian01_2_25",
  "c08_lillian01_2_26",
  "c08_lillian01_2_27",
  "c08_lillian01_2_3",
  "c08_lillian01_3_0",
  "c08_lillian02_1_0",
  "c08_lillian02_1_1",
  "c08_lillian02_1_2",
  "afx08_lightning_2_0",
  "afx08_lightning_2_1",
  "afx08_lightning_2_2",
  "afx08_lightning_2_3",
  "afx08_lightning_2_4",
  "afx08_lightning_2_5",
  "afx08_lightning_5_0"
}
local r3_0 = false
local r4_0 = _G.CharaParam[8][1]
local r5_0 = require("char.c08.c08_lillian01")
local r6_0 = require("char.c08.c08_lillian02")
local r7_0 = require("char.c08.afx08_lightning")
local r8_0 = _G.CharaParam[8][2][1]
local r9_0 = _G.CharaParam[8][3][1] / 100
local r10_0 = -80
local r11_0 = -316
local function r12_0(r0_1)
  -- line: [60, 93] id: 1
  local r1_1 = r0_1.data
  local r2_1 = r1_1.shot_ev
  if r2_1 then
    table.remove(_G.ShotEvent, table.indexOf(_G.ShotEvent, r2_1))
    events.Delete(r2_1)
    r1_1.shot_ev = nil
  end
  if r1_1.cloud_move then
    table.remove(_G.ShotEvent, table.indexOf(_G.ShotEvent, r1_1.cloud_move))
    events.Delete(r1_1.cloud_move)
    r1_1.cloud_move = nil
  end
  if r1_1.cloud.anime then
    anime.Remove(r1_1.cloud.anime)
    r1_1.cloud.anime = nil
  end
  if r1_1.stan_circle then
    display.remove(r1_1.stan_circle)
    r1_1.stan_circle = nil
  end
  if r1_1.transition then
    transit.Delete(r1_1.transition)
    r1_1.transition = nil
  end
end
local function r13_0(r0_2, r1_2)
  -- line: [95, 108] id: 2
  local r2_2 = r1_2.data.scared_ev
  anime.Pause(r2_2, true)
  anime.Show(r2_2, false)
  if not r1_2.data.scared_check(r1_2) then
    r1_2.data.scared = false
    r2_2 = r1_2.anime
    anime.Pause(r2_2, true)
    anime.Show(r2_2, true)
    events.Disable(r1_2.shot_ev, false)
  end
end
local function r14_0(r0_3)
  -- line: [111, 149] id: 3
  local r2_3 = _G.UserStatus[8].range[r0_3.level][1]
  local r3_3 = r0_3.range[1]
  local r4_3 = r0_3.x
  local r5_3 = r0_3.y
  local r6_3 = nil
  local r7_3 = nil
  local r8_3 = nil
  local r9_3 = nil
  local r10_3 = nil
  local r11_3 = false
  for r15_3, r16_3 in pairs(_G.Enemys) do
    if r16_3.attack[2] == false then
      r6_3 = r16_3.sx + r16_3.sight[1]
      r7_3 = r16_3.sy + r16_3.sight[2]
      r8_3 = math.abs(r6_3 - r4_3)
      r9_3 = math.abs(r7_3 - r5_3)
      if r8_3 < r2_3 or r9_3 < r2_3 then
        r10_3 = (r6_3 - r4_3) * (r6_3 - r4_3) + (r7_3 - r5_3) * (r7_3 - r5_3)
        if r10_3 < r3_3 then
          r11_3 = true
          break
        end
      end
    end
  end
  if not r11_3 then
    return false
  end
  local r12_3 = nil
  r0_3.data.scared = true
  if r0_3.data.scared_ev ~= nil then
    r12_3 = r0_3.data.scared_ev
    anime.Pause(r12_3, false)
    anime.Show(r12_3, true)
  end
  r12_3 = r0_3.anime
  anime.Pause(r12_3, true)
  anime.Show(r12_3, false)
  return true
end
local function r15_0(r0_4, r1_4, r2_4)
  -- line: [153, 272] id: 4
  local r3_4 = r1_4.my
  local r4_4 = true
  local r5_4 = r1_4.scale
  local r6_4 = r1_4.index + r2_4
  r1_4.index = r6_4
  r6_4 = r6_4 % 16
  local r7_4 = r6_4 % 4
  if r7_4 == 0 or r7_4 == 1 then
    r1_4.sprite[1].isVisible = true
    r1_4.sprite[2].isVisible = false
  else
    r1_4.sprite[1].isVisible = false
    r1_4.sprite[2].isVisible = true
  end
  local r8_4 = r6_4 % 8
  local r9_4 = math.abs(r1_4.group.xScale)
  local r10_4 = math.abs(r1_4.group.yScale)
  if r1_4.index >= 7 then
    r9_4 = r5_4
    r10_4 = r5_4
  end
  if 0 <= r8_4 and r8_4 <= 3 then
    r1_4.group.xScale = r9_4
  else
    r1_4.group.xScale = -r9_4
  end
  if 0 <= r6_4 and r6_4 <= 7 then
    r1_4.group.yScale = r10_4
  else
    r1_4.group.yScale = -r10_4
  end
  if 0 > r1_4.index or r1_4.index >= 7 then
    if 7 <= r1_4.index and r1_4.index < 12 then
      if r3_4.data.transition then
        transit.Delete(r3_4.data.transition)
        r3_4.data.transition = nil
      end
      local r11_4 = r1_4.xy[1]
      local r12_4 = r1_4.xy[2]
      local r13_4 = nil
      local r14_4 = nil
      local r16_4 = r3_4.level
      local r19_4 = _G.UserStatus[3].range[r16_4][2] * (char.GetRangePowerup() + 100) / 100
      local r20_4 = r19_4 * r19_4
      local r21_4 = r1_4.max_stan
      local r22_4 = r3_4.data.stan.power[r16_4] * r3_4.buff_power
      local r24_4 = nil
      for r28_4, r29_4 in pairs(_G.Enemys) do
        if r21_4 <= 0 then
          break
        elseif not r29_4.stan_flag and r29_4.attack[2] == false then
          r13_4 = r29_4.sx + r29_4.sight[1]
          r14_4 = r29_4.sy + r29_4.sight[2]
          if (r13_4 - r11_4) * (r13_4 - r11_4) + (r14_4 - r12_4) * (r14_4 - r12_4) < r20_4 then
            r24_4 = r22_4
            if r29_4.attr == 2 or r29_4.attr == 4 then
              r24_4 = r24_4 * r9_0
            end
            if _G.GameMode == _G.GameModeEvo then
              r29_4.func.stan(r29_4, r24_4, r8_0, r3_4)
            else
              r29_4.func.stan(r29_4, r24_4, r8_0)
            end
            r21_4 = r21_4 - 1
          end
        end
      end
      r1_4.max_stan = r21_4
    elseif 12 <= r1_4.index and r1_4.index < 19 and r3_4.data.transition == nil then
      r3_4.data.transition = transit.Register(r1_4.group, {
        time = 266.6666666666667,
        alpha = 0,
        transition = easing.linear,
      })
    elseif r1_4.index >= 19 then
      if r3_4.data.transition then
        transit.Delete(r3_4.data.transition)
        r3_4.data.transition = nil
      end
      r3_4.data.stan_circle = nil
      display.remove(r1_4.group)
      r4_4 = false
    end
  end
  if not r4_4 then
    r12_0(r3_4)
    r3_4.shooting = false
    if r3_4.shot_ev then
      events.Disable(r3_4.shot_ev, false)
    end
  end
  return r4_4
end
local function r16_0(r0_5, r1_5)
  -- line: [275, 336] id: 5
  local r2_5 = r1_5.my
  local r3_5 = r1_5.target
  if r3_5 == nil then
    return 
  end
  local r4_5 = {}
  local r5_5 = true
  local r6_5 = display.newGroup()
  for r10_5 = 0, 1, 1 do
    local r11_5 = display.newImage(string.format("data/game/afx03_shockwave_0_%d.png", r10_5), true)
    r11_5.isVisible = r5_5
    r5_5 = false
    r6_5:insert(r11_5)
    table.insert(r4_5, r11_5)
  end
  r6_5:setReferencePoint(display.CenterReferencePoint)
  r6_5.x = r3_5.sx
  r6_5.y = r3_5.sy
  local r7_5 = 1
  local r8_5 = r2_5.level
  if r8_5 > 1 then
    local r9_5 = _G.UserStatus[8]
    r7_5 = r9_5.range[r8_5][2] * (char.GetRangePowerup() + 100) / 100 / r9_5.range[1][2]
  end
  local r9_5 = {
    group = r6_5,
    sprite = r4_5,
    index = 0,
    xy = {
      r3_5.sx,
      r3_5.sy
    },
    target = r3_5,
    hit = false,
  }
  r9_5.group.xScale = 0.001
  r9_5.group.yScale = 0.001
  r9_5.scale = r7_5
  r9_5.max_stan = r4_0[r8_5]
  r2_5.data.transition = transit.Register(r9_5.group, {
    time = 233.33333333333334,
    xScale = r7_5,
    yScale = r7_5,
    transition = easing.linear,
  })
  r9_5.my = r2_5
  _G.StanRoot:insert(r6_5)
  r2_5.data.stan_circle = r9_5.group
  r2_5.data.shot_ev = events.Register(r15_0, r9_5, 0, false)
  table.insert(_G.ShotEvent, r2_5.data.shot_ev)
  sound.PlaySE(16, 9)
  local r10_5 = r2_5.power[r2_5.level]
  if r3_5.attr == 2 or r3_5.attr == 4 then
    r10_5 = r10_5 * r9_0
  end
  r3_5.func.stan(r3_5, r10_5, r8_0)
end
local function r17_0(r0_6, r1_6, r2_6)
  -- line: [339, 353] id: 6
  local r3_6 = r1_6.my
  if r3_6.data.scared_check(r3_6) then
    r12_0(r3_6)
    r3_6.target = nil
    r3_6.shooting = false
    return false
  end
  local r4_6 = r1_6.target
  anime.Move(r1_6.anime, r4_6.sx + r10_0, r4_6.sy + r11_0)
  return true
end
local function r18_0(r0_7, r1_7, r2_7, r3_7)
  -- line: [356, 382] id: 7
  local r6_7 = anime.Register(r7_0.GetData(), r2_7.sx + r10_0, r2_7.sy + r11_0, "data/game")
  local r7_7 = anime.GetSprite(r6_7)
  local r8_7 = {
    anime = r6_7,
    group = r7_7,
    sprite = nil,
    xy = {
      r0_7,
      r1_7
    },
    target = r2_7,
    hit = false,
    my = r3_7,
  }
  r3_7.data.cloud = r8_7
  _G.MissleRoot:insert(r7_7)
  r3_7.data.cloud_move = events.Register(r17_0, r8_7, 0, false)
  table.insert(_G.ShotEvent, r3_7.data.cloud_move)
  anime.Show(r6_7, true)
  anime.RegisterTrigger(r6_7, 8, r16_0, r8_7)
  return r8_7
end
local function r19_0(r0_8)
  -- line: [384, 405] id: 8
  local r1_8 = r0_8.data
  if r1_8.cloud_move then
    events.Delete(r1_8.cloud_move)
    r1_8.cloud_move = nil
  end
  if r1_8.transition then
    transit.Delete(r1_8.transition)
    r1_8.transition = nil
  end
  if r1_8.cloud then
    local r2_8 = r1_8.cloud
    if r2_8.anime then
      anime.Remove(r2_8.anime)
      r2_8.anime = nil
    end
  end
  if r1_8.scared_ev then
    anime.Remove(r1_8.scared_ev)
    r1_8.scared_ev = nil
  end
end
local function r20_0(r0_9, r1_9)
  -- line: [408, 410] id: 9
  return 1
end
local function r21_0(r0_10, r1_10, r2_10)
  -- line: [412, 470] id: 10
  if game ~= nil and game.IsNotPauseTypeNone() then
    return true
  end
  local r3_10 = r1_10
  if r3_10.data.scared or r3_10.data.scared_check(r3_10) then
    r3_10.target = nil
    return true
  end
  if r3_10.target then
    if not r3_10.target.living then
      r3_10.target = nil
    end
    if r3_10.target_cancel then
      r3_10.target = nil
      r3_10.target_cancel = false
    end
  end
  if r3_10.target and not r3_10.shooting then
    if r3_10.func.check(r3_10, r3_10.target) then
      r3_10.vect = 1
      events.Disable(r3_10.shot_ev, true)
      r3_10.shooting = true
      local r4_10 = r3_10.anime
      anime.Pause(r4_10, false)
      anime.Show(r4_10, true, {
        scale = (r3_10.wait[r3_10.level] + r0_0.AttackSpeed) / 100,
      })
      anime.RegisterTrigger(r4_10, r3_10.shot_frame_nr, r3_10.func.shoot, r3_10)
      anime.RegisterFinish(r4_10, r3_10.func.finish, r3_10)
    elseif not r3_10.shooting then
      r3_10.target = nil
    end
  end
  if r3_10.target == nil then
    if r3_10.next_target then
      r3_10.target = r3_10.next_target
      r3_10.next_target = nil
    else
      r3_10.target = r3_10.func.search(r3_10)
    end
    if r3_10.target then
      r3_10.func.pointing(r3_10, r3_10.target)
    end
  end
  return true
end
local function r22_0(r0_11, r1_11)
  -- line: [473, 485] id: 11
  if r1_11.target and not r1_11.func.check(r1_11, r1_11.target) then
    r1_11.target = nil
  end
  anime.Pause(r0_11, true)
  anime.SetTimer(r0_11, 0)
  if r1_11.data.force_shooting then
    r1_11.data.force_shooting = false
    r1_11.shooting = false
  end
end
local function r23_0(r0_12)
  -- line: [488, 490] id: 12
  r0_12.data.force_shooting = true
end
local function r24_0(r0_13, r1_13)
  -- line: [493, 499] id: 13
  return r0_13.data.check(r0_13, r1_13) or true
end
local function r25_0(r0_14, r1_14, r2_14)
  -- line: [502, 530] id: 14
  if r1_14 then
    local r3_14 = {}
    local r4_14 = r0_14.data.transition
    if r4_14 then
      r3_14.pause = {}
      r3_14.pause.mode = transit.Pause(r4_14, true)
      r3_14.pause.obj = r4_14
    end
    r4_14 = r0_14.data.cloud
    if r4_14 and r4_14.anime then
      r3_14.cloud = {}
      r3_14.cloud.mode = events.Disable(r4_14.anime.ev, true)
      r3_14.cloud.obj = r4_14.anime.ev
    end
    return r3_14
  else
    local r3_14 = nil
    r3_14 = r2_14.pause
    if r3_14 then
      transit.Pause(r3_14.obj, r3_14.mode)
    end
    r3_14 = r2_14.cloud
    if r3_14 then
      events.Disable(r3_14.obj, r3_14.mode)
    end
    return nil
  end
end
local function r26_0(r0_15, r1_15)
  -- line: [532, 538] id: 15
  anime.ReplaceSprite(r0_15.anime, r1_15, r5_0.GetData())
  anime.ReplaceSprite(r0_15.data.scared_ev, r1_15, r6_0.GetData())
  return r0_15
end
return setmetatable({
  ChangeSprite = r26_0,
  Load = function(r0_16)
    -- line: [541, 605] id: 16
    if not r3_0 then
      preload.Load(r2_0, "data/game")
      r3_0 = true
    end
    local r1_16 = r0_16.x
    local r2_16 = r0_16.y
    local r3_16 = anime.Register(r5_0.GetData(), r1_16, r2_16, "data/game")
    local r4_16 = anime.GetSprite(r3_16)
    local r5_16 = display.newRect(_G.MyTgRoot, r1_16 - 40, r2_16 - 40, 80, 80)
    r5_16.alpha = 0.01
    r5_16.struct = r0_16
    r5_16.touch = r0_16.func.circle
    r5_16:addEventListener("touch", r5_16)
    r0_16.touch_area = r5_16
    r0_16.data = {}
    r0_16.anime = r3_16
    r0_16.spr = r4_16
    r0_16.func.load = r18_0
    r0_16.func.release = r19_0
    r0_16.func.range = r21_0
    r0_16.func.pointing = r20_0
    r0_16.func.finish = r22_0
    r0_16.func.fumble = r23_0
    r0_16.data.check = r0_16.func.check
    r0_16.func.check = r24_0
    r0_16.func.pause = r25_0
    r0_16.shot_frame_nr = 32
    r0_16.data.force_shooting = false
    r0_16.data.stan = {}
    local r6_16 = _G.UserStatus[3]
    local r7_16 = r6_16.range[1][1]
    local r8_16 = r6_16.range[1][2]
    r0_16.data.stan = {
      min_range = r7_16 * r7_16,
      max_range = r8_16 * r8_16,
      power = _G.CharaParam[8][4],
    }
    r0_16.data.scared = false
    r0_16.data.scared_check = r14_0
    local r9_16 = anime.Register(r6_0.GetData(), r1_16, r2_16, "data/game")
    anime.RegisterFinish(r9_16, r13_0, r0_16)
    r0_16.data.scared_ev = r9_16
    _G.MyRoot:insert(anime.GetSprite(r9_16))
    if _G.GameMode == _G.GameModeEvo then
      r0_16.func.changeSprite = r26_0
      r0_16.func.rankTable = r1_0
    end
    return r0_16
  end,
  Cleanup = function()
    -- line: [607, 609] id: 17
    r3_0 = false
  end,
}, {
  __index = require("char.Char"),
})
