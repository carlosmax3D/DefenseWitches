-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("logic.char.CharStatus")
local r1_0 = nil
if _G.GameMode == _G.GameModeEvo then
  r1_0 = require("evo.char_tbl.tbl_c19_nina").CreateTable()
end
local r2_0 = {
  "c19_nina01_0_0",
  "c19_nina01_0_1",
  "c19_nina01_0_2",
  "c19_nina01_0_3",
  "c19_nina01_0_4",
  "c19_nina01_0_5",
  "c19_nina01_0_6",
  "c19_nina01_0_7",
  "c19_nina01_0_8",
  "c19_nina01_0_9",
  "c19_nina01_0_10",
  "afx19_hammer_0_0",
  "afx19_hammer_0_1",
  "afx19_hammer_0_2",
  "afx19_hammer_0_3",
  "afx19_hammer_0_4",
  "afx19_hammer_0_5"
}
local r3_0 = false
local r4_0 = _G.CharaParam[19][1]
local r5_0 = {}
for r9_0 = 1, 8, 1 do
  table.insert(r5_0, require(string.format("char.c19.c19_nina%02d", r9_0)))
end
local r6_0 = require("char.c19.afx19_hammer")
local r7_0 = -80
local r8_0 = -316
local r9_0 = 133
local r10_0 = 86
local r11_0 = 127
local function r12_0(r0_1)
  -- line: [63, 88] id: 1
  local r1_1 = r0_1.data
  local r2_1 = r1_1.shot_ev
  if r2_1 then
    table.remove(_G.ShotEvent, table.indexOf(_G.ShotEvent, r2_1))
    events.Delete(r2_1)
    r1_1.shot_ev = nil
  end
  if r1_1.eff.anime then
    anime.Remove(r1_1.eff.anime)
    r1_1.eff.anime = nil
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
local function r13_0(r0_2, r1_2, r2_2)
  -- line: [153, 266] id: 2
  local r3_2 = r1_2.my
  local r4_2 = true
  local r5_2 = r1_2.scale
  local r6_2 = r1_2.index + r2_2
  r1_2.index = r6_2
  r6_2 = r6_2 % 16
  local r7_2 = r6_2 % 4
  if r7_2 == 0 or r7_2 == 1 then
    r1_2.sprite[1].isVisible = true
    r1_2.sprite[2].isVisible = false
  else
    r1_2.sprite[1].isVisible = false
    r1_2.sprite[2].isVisible = true
  end
  local r8_2 = r6_2 % 8
  local r9_2 = math.abs(r1_2.group.xScale)
  local r10_2 = math.abs(r1_2.group.yScale)
  if r1_2.index >= 7 then
    r9_2 = r5_2
    r10_2 = r5_2
  end
  if 0 <= r8_2 and r8_2 <= 3 then
    r1_2.group.xScale = r9_2
  else
    r1_2.group.xScale = -r9_2
  end
  if 0 <= r6_2 and r6_2 <= 7 then
    r1_2.group.yScale = r10_2
  else
    r1_2.group.yScale = -r10_2
  end
  if 0 > r1_2.index or r1_2.index >= 7 then
    if 7 <= r1_2.index and r1_2.index < 12 then
      if r3_2.data.transition then
        transit.Delete(r3_2.data.transition)
        r3_2.data.transition = nil
      end
      local r11_2 = r1_2.xy[1]
      local r12_2 = r1_2.xy[2]
      local r13_2 = nil
      local r14_2 = nil
      local r16_2 = r3_2.level
      local r19_2 = _G.UserStatus[19].range[r16_2][2] * (char.GetRangePowerup() + 100) / 100
      local r20_2 = r19_2 * r19_2
      local r21_2 = r1_2.max_stan
      local r22_2 = r3_2.power[r16_2] * r3_2.buff_power
      local r24_2 = nil
      for r28_2, r29_2 in pairs(_G.Enemys) do
        if r21_2 <= 0 then
          break
        elseif not r29_2.stan_flag and r29_2.attack[2] == false then
          r13_2 = r29_2.sx + r29_2.sight[1]
          r14_2 = r29_2.sy + r29_2.sight[2]
          if (r13_2 - r11_2) * (r13_2 - r11_2) + (r14_2 - r12_2) * (r14_2 - r12_2) < r20_2 then
            r24_2 = r22_2
            r29_2.func.stan(r29_2, r24_2, 0)
            r21_2 = r21_2 - 1
          end
        end
      end
      r1_2.max_stan = r21_2
    elseif 12 <= r1_2.index and r1_2.index < 19 and r3_2.data.transition == nil then
      r3_2.data.transition = transit.Register(r1_2.group, {
        time = 266.6666666666667,
        alpha = 0,
        transition = easing.linear,
      })
    elseif r1_2.index >= 19 then
      if r3_2.data.transition then
        transit.Delete(r3_2.data.transition)
        r3_2.data.transition = nil
      end
      r3_2.data.stan_circle = nil
      display.remove(r1_2.group)
      r4_2 = false
    end
  end
  if not r4_2 then
    r12_0(r3_2)
    r3_2.shooting = false
    if r3_2.shot_ev then
      events.Disable(r3_2.shot_ev, false)
    end
  end
  return r4_2
end
local function r14_0(r0_3, r1_3)
  -- line: [269, 335] id: 3
  local r2_3 = r1_3.my
  local r3_3 = r1_3.target
  if r3_3 == nil then
    return 
  end
  local r4_3 = {}
  local r5_3 = true
  local r6_3 = display.newGroup()
  for r10_3 = 0, 1, 1 do
    local r11_3 = display.newImage(string.format("data/game/nina/afx19_hammer_eff_%d.png", r10_3), true)
    r11_3.isVisible = false
    r5_3 = false
    r6_3:insert(r11_3)
    table.insert(r4_3, r11_3)
  end
  r6_3:setReferencePoint(display.CenterReferencePoint)
  r6_3.x = r3_3.sx
  r6_3.y = r3_3.sy
  local r7_3 = 1
  local r8_3 = r2_3.level
  if r8_3 > 1 then
    local r9_3 = _G.UserStatus[19]
    r7_3 = r9_3.range[r8_3][2] * (char.GetRangePowerup() + 100) / 100 / r9_3.range[1][2]
  end
  local r9_3 = {
    group = r6_3,
    sprite = r4_3,
    index = 0,
    xy = {
      r3_3.sx,
      r3_3.sy
    },
    target = r3_3,
    hit = false,
  }
  r9_3.group.xScale = 0.001
  r9_3.group.yScale = 0.001
  r9_3.scale = r7_3
  r9_3.max_stan = r4_0[r8_3]
  r2_3.data.transition = transit.Register(r9_3.group, {
    time = 233.33333333333334,
    xScale = r7_3,
    yScale = r7_3,
    transition = easing.linear,
  })
  r9_3.my = r2_3
  _G.StanRoot:insert(r6_3)
  r2_3.data.stan_circle = r9_3.group
  r2_3.data.shot_ev = events.Register(r13_0, r9_3, 0, false)
  table.insert(_G.ShotEvent, r2_3.data.shot_ev)
  sound.PlaySE(16, 9)
  local r10_3 = r2_3.power[r2_3.level]
  if _G.GameMode == _G.GameModeEvo then
    r3_3.func.hit(r3_3, r10_3, r2_3)
  else
    r3_3.func.hit(r3_3, r10_3)
  end
end
local function r15_0(r0_4, r1_4, r2_4, r3_4)
  -- line: [338, 363] id: 4
  local r6_4 = anime.Register(r6_0.GetData(), r2_4.sx + r7_0, r2_4.sy + r8_0, "data/game/nina")
  local r7_4 = anime.GetSprite(r6_4)
  local r8_4 = {
    anime = r6_4,
    group = r7_4,
    sprite = nil,
    xy = {
      r0_4,
      r1_4
    },
    target = r2_4,
    hit = false,
    my = r3_4,
  }
  r3_4.data.eff = r8_4
  _G.MissleRoot:insert(r7_4)
  anime.Show(r6_4, true)
  anime.RegisterTrigger(r6_4, 8, r14_0, r8_4)
  return r8_4
end
local function r16_0(r0_5)
  -- line: [365, 382] id: 5
  local r1_5 = r0_5.data
  if r1_5.transition then
    transit.Delete(r1_5.transition)
    r1_5.transition = nil
  end
  if r1_5.cloud then
    local r2_5 = r1_5.cloud
    if r2_5.anime then
      anime.Remove(r2_5.anime)
      r2_5.anime = nil
    end
  end
  if r1_5.scared_ev then
    anime.Remove(r1_5.scared_ev)
    r1_5.scared_ev = nil
  end
end
local function r17_0(r0_6, r1_6, r2_6)
  -- line: [384, 451] id: 6
  if game ~= nil and game.IsNotPauseTypeNone() then
    return true
  end
  local r3_6 = r1_6
  anime.Pause(r3_6.anime, false)
  anime.Show(r3_6.anime, true)
  if r3_6.anime.timer < r11_0 then
    r3_6.target = nil
  end
  if r3_6.target then
    if not r3_6.target.living then
      r3_6.target = nil
    end
    if r3_6.target_cancel then
      r3_6.target = nil
      r3_6.target_cancel = false
    end
  end
  if r3_6.target and not r3_6.shooting then
    if r3_6.func.check(r3_6, r3_6.target) then
      events.Disable(r3_6.shot_ev, true)
      r3_6.shooting = true
      local r4_6 = r3_6.anime
      anime.Pause(r4_6, false)
      anime.Show(r4_6, true, {
        scale = (r3_6.wait[r3_6.level] + r0_0.AttackSpeed) / 100,
      })
      anime.RegisterTrigger(r4_6, r3_6.shot_frame_nr, r3_6.func.shoot, r3_6)
      anime.RegisterFinish(r4_6, r3_6.func.finish, r3_6)
    elseif not r3_6.shooting then
      r3_6.target = nil
    end
  elseif r11_0 < r3_6.anime.timer then
    r3_6.anime.timer = r10_0
  end
  if r3_6.target == nil then
    r3_6.anime.vect = 1
    if r3_6.next_target then
      r3_6.target = r3_6.next_target
      r3_6.next_target = nil
    else
      r3_6.target = r3_6.func.search(r3_6)
    end
    if r3_6.target then
      r3_6.func.pointing(r3_6, r3_6.target)
    end
  end
  return true
end
local function r18_0(r0_7, r1_7)
  -- line: [454, 466] id: 7
  if r1_7.target and not r1_7.func.check(r1_7, r1_7.target) then
    r1_7.target = nil
  end
  anime.Pause(r0_7, true)
  anime.SetTimer(r0_7, 0)
  if r1_7.data.force_shooting then
    r1_7.data.force_shooting = false
    r1_7.shooting = false
  end
end
local function r19_0(r0_8, r1_8)
  -- line: [469, 475] id: 8
  return r0_8.data.check(r0_8, r1_8) or true
end
local function r20_0(r0_9, r1_9)
  -- line: [477, 484] id: 9
  for r6_9 = 1, r0_9.anime.nr, 1 do
    anime.ReplaceSprite(r0_9.anime.pack[r6_9], r1_9 .. "/nina", r5_0[r6_9].GetData())
  end
  return r0_9
end
return setmetatable({
  ChangeSprite = r20_0,
  Load = function(r0_10)
    -- line: [486, 540] id: 10
    if not r3_0 then
      preload.Load(r2_0, "data/game/nina")
      r3_0 = true
    end
    local r1_10 = {}
    local r2_10 = display.newGroup()
    local r3_10 = r0_10.x
    local r4_10 = r0_10.y
    for r8_10 = 1, 8, 1 do
      local r9_10 = anime.Register(r5_0[r8_10].GetData(), r3_10, r4_10, "data/game/nina")
      r2_10:insert(anime.GetSprite(r9_10))
      table.insert(r1_10, r9_10)
    end
    local r5_10 = display.newRect(_G.MyTgRoot, r3_10 - 40, r4_10 - 40, 80, 80)
    r5_10.alpha = 0.01
    r5_10.struct = r0_10
    r5_10.touch = r0_10.func.circle
    r5_10:addEventListener("touch", r5_10)
    r0_10.touch_area = r5_10
    r0_10.data = {}
    r0_10.anime = anime.Pack(unpack(r1_10))
    r0_10.spr = r2_10
    r0_10.func.load = r15_0
    r0_10.func.release = r16_0
    r0_10.func.range = r17_0
    r0_10.func.finish = r18_0
    r0_10.data.check = r0_10.func.check
    r0_10.func.check = r19_0
    r0_10.shot_frame_nr = r9_0
    r0_10.data.force_shooting = false
    if _G.GameMode == _G.GameModeEvo then
      r0_10.func.changeSprite = r20_0
      r0_10.func.rankTable = r1_0
    end
    return r0_10
  end,
  Cleanup = function()
    -- line: [542, 544] id: 11
    r3_0 = false
  end,
}, {
  __index = require("char.Char"),
})
