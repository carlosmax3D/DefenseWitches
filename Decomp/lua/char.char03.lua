-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("logic.char.CharStatus")
local r1_0 = nil
if _G.GameMode == _G.GameModeEvo then
  r1_0 = require("evo.char_tbl.tbl_c03_chloe").CreateTable()
end
local r2_0 = {
  "c03_chloe01_0_0",
  "c03_chloe01_1_0",
  "c03_chloe01_1_1",
  "c03_chloe01_1_2",
  "c03_chloe01_1_3",
  "c03_chloe01_1_4",
  "c03_chloe01_1_5",
  "afx03_shockwave_0_0",
  "afx03_shockwave_0_1"
}
local r3_0 = false
local r4_0 = _G.CharaParam[3][1]
local r5_0 = require("char.c03.c03_chloe01")
local r6_0 = _G.CharaParam[3][2][1]
local r7_0 = _G.CharaParam[3][3][1] / 100
local function r8_0(r0_1)
  -- line: [40, 68] id: 1
  if not _G.LoginGameCenter then
    return 
  end
  local r1_1 = _G.UserID
  local r2_1 = _G.UserToken
  local r3_1 = db.CountAchievement(r1_1, 7, r0_1)
  local r4_1 = nil
  local r5_1 = nil
  local r6_1 = nil
  if r3_1 <= 1000 then
    r4_1 = math.floor(r3_1 * 100 / 1000)
    r5_1 = 7
  elseif r3_1 <= 10000 then
    if not db.GetGameCenterAchievement(r1_1, 7) then
      game.CheckTotalAchievement(7, 100)
    end
    r4_1 = math.floor(r3_1 * 100 / 10000)
    r5_1 = 8
  elseif r3_1 <= 100000 then
    if not db.GetGameCenterAchievement(r1_1, 8) then
      game.CheckTotalAchievement(8, 100)
    end
    r4_1 = math.floor(r3_1 * 100 / 100000)
    r5_1 = 9
  else
    r4_1 = 100
    r5_1 = 9
  end
  game.CheckTotalAchievement(r5_1, r4_1)
end
local function r9_0(r0_2, r1_2, r2_2)
  -- line: [71, 194] id: 2
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
  if r1_2 ~= nil and r1_2.my ~= nil and r1_2.my.anime ~= nil then
    local r11_2 = anime.GetImageScale(r1_2.my.anime)
    if r11_2 ~= nil then
      r9_2 = r9_2 * r11_2[1]
      r10_2 = r10_2 * r11_2[2]
    end
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
      local r14_2 = r1_2.max_stan
      local r15_2 = r1_2.time
      local r18_2 = r3_2.range[2]
      local r16_2 = r3_2.power[r3_2.level] * r3_2.buff_power
      local r19_2 = nil
      local r20_2 = nil
      local r21_2 = nil
      for r25_2, r26_2 in pairs(_G.Enemys) do
        if r14_2 <= 0 then
          break
        elseif not r26_2.stan_flag and r26_2.attack[2] == false then
          r20_2 = r26_2.sx + r26_2.sight[1]
          r21_2 = r26_2.sy + r26_2.sight[2]
          if (r20_2 - r11_2) * (r20_2 - r11_2) + (r21_2 - r12_2) * (r21_2 - r12_2) < r18_2 then
            r19_2 = r16_2
            if r26_2.attr == 2 or r26_2.attr == 4 then
              r19_2 = r19_2 * r7_0
            end
            if _G.GameMode == _G.GameModeEvo then
              r26_2.func.stan(r26_2, r19_2, r15_2, r3_2)
            else
              r26_2.func.stan(r26_2, r19_2, r15_2)
            end
            r14_2 = r14_2 - 1
          end
        end
      end
      r1_2.max_stan = r14_2
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
      display.remove(r1_2.group)
      r3_2.shooting = false
      r4_2 = false
    end
  end
  if not r4_2 then
    table.remove(_G.ShotEvent, table.indexOf(_G.ShotEvent, r0_2))
    r3_2.shooting = false
  end
  return r4_2
end
local function r10_0(r0_3, r1_3, r2_3, r3_3)
  -- line: [197, 257] id: 3
  if r3_3.data and r3_3.data.transition then
    transit.Delete(r3_3.data.transition)
    r3_3.data.transition = nil
  end
  local r4_3 = {}
  local r5_3 = true
  local r6_3 = display.newGroup()
  for r10_3 = 0, 1, 1 do
    local r11_3 = display.newImage(string.format("data/game/afx03_shockwave_0_%d.png", r10_3), true)
    r11_3.isVisible = r5_3
    r5_3 = false
    r6_3:insert(r11_3)
    table.insert(r4_3, r11_3)
  end
  r6_3:setReferencePoint(display.CenterReferencePoint)
  r6_3.x = r0_3
  r6_3.y = r1_3
  local r7_3 = 1
  local r8_3 = r3_3.level
  if r8_3 > 1 then
    local r9_3 = _G.UserStatus[3]
    r7_3 = r9_3.range[r8_3][2] * (char.GetRangePowerup() + 100) / 100 / r9_3.range[1][2]
  end
  local r9_3 = {
    group = r6_3,
    sprite = r4_3,
    index = 0,
    xy = {
      r0_3,
      r1_3
    },
    target = r2_3,
    hit = false,
    time = r6_0,
  }
  r9_3.group.xScale = 0.001
  r9_3.group.yScale = 0.001
  r9_3.scale = r7_3
  r9_3.max_stan = r4_0[r8_3]
  r3_3.data = {}
  r3_3.data.transition = transit.Register(r9_3.group, {
    time = 233.33333333333334,
    xScale = r7_3,
    yScale = r7_3,
    transition = easing.linear,
  })
  r9_3.my = r3_3
  _G.StanRoot:insert(r6_3)
  table.insert(_G.ShotEvent, events.Register(r9_0, r9_3, 0, false))
  sound.PlaySE(13, 4)
  r8_0(1)
  return r9_3
end
local function r11_0(r0_4)
  -- line: [259, 264] id: 4
  if r0_4.data.transition then
    transition.cancel(r0_4.data.transition)
    r0_4.data.transition = nil
  end
end
local function r12_0(r0_5, r1_5)
  -- line: [267, 269] id: 5
  return 1
end
local function r13_0(r0_6, r1_6)
  -- line: [272, 285] id: 6
  if r1_6.target and not r1_6.func.check(r1_6, r1_6.target) then
    r1_6.target = nil
  end
  anime.Pause(r0_6, true)
  anime.SetTimer(r0_6, 0)
  if r1_6.data.force_shooting then
    r1_6.data.force_shooting = false
    r1_6.shooting = false
  end
end
local function r14_0(r0_7, r1_7, r2_7)
  -- line: [288, 314] id: 7
  if game ~= nil and game.IsNotPauseTypeNone() then
    return true
  end
  local r3_7 = r1_7
  if not r3_7.shooting then
    r3_7.target = r3_7.func.search(r3_7)
    if r3_7.target then
      r3_7.shooting = true
      local r4_7 = r3_7.anime
      anime.Pause(r4_7, false)
      anime.Show(r4_7, true, {
        scale = (r3_7.wait[r3_7.level] + r0_0.AttackSpeed) / 100,
      })
      anime.RegisterTrigger(r4_7, r3_7.shot_frame_nr, r3_7.func.shoot, r3_7)
      anime.RegisterFinish(r4_7, r3_7.func.finish, r3_7)
    end
  end
  return true
end
local function r15_0(r0_8, r1_8)
  -- line: [317, 321] id: 8
  local r2_8 = anime.GetPos(r0_8)
  assert(r1_8.func.load)
  r1_8.func.load(r2_8[1], r2_8[2], r1_8.target, r1_8)
end
local function r16_0(r0_9)
  -- line: [324, 326] id: 9
  r0_9.data.force_shooting = true
end
local function r17_0(r0_10, r1_10, r2_10)
  -- line: [329, 344] id: 10
  if r1_10 then
    local r3_10 = {}
    local r4_10 = r0_10.data.transition
    if r4_10 then
      r3_10.mode = transit.Pause(r4_10, true)
      r3_10.obj = r4_10
    end
    return r3_10
  else
    if r2_10.obj then
      transit.Pause(r2_10.obj, r2_10.mode)
    end
    return nil
  end
end
local function r18_0(r0_11, r1_11)
  -- line: [346, 350] id: 11
  anime.ReplaceSprite(r0_11.anime, r1_11, r5_0.GetData())
  return r0_11
end
return setmetatable({
  ChangeSprite = r18_0,
  Load = function(r0_12)
    -- line: [353, 395] id: 12
    if not r3_0 then
      preload.Load(r2_0, "data/game")
      r3_0 = true
    end
    local r1_12 = r0_12.x
    local r2_12 = r0_12.y
    local r3_12 = anime.Register(r5_0.GetData(), r1_12, r2_12, "data/game")
    local r4_12 = anime.GetSprite(r3_12)
    local r5_12 = display.newRect(_G.MyTgRoot, r1_12 - 40, r2_12 - 40, 80, 80)
    r5_12.alpha = 0.01
    r5_12.struct = r0_12
    r5_12.touch = r0_12.func.circle
    r5_12:addEventListener("touch", r5_12)
    r0_12.touch_area = r5_12
    r0_12.anime = r3_12
    r0_12.spr = r4_12
    r0_12.func.load = r10_0
    r0_12.func.release = r11_0
    r0_12.func.range = r14_0
    r0_12.func.pointing = r12_0
    r0_12.func.shoot = r15_0
    r0_12.func.finish = r13_0
    r0_12.func.fumble = r16_0
    r0_12.func.pause = r17_0
    r0_12.shot_frame_nr = 32
    r0_12.data = {}
    r0_12.data.force_shooting = false
    if _G.GameMode == _G.GameModeEvo then
      r0_12.func.changeSprite = r18_0
      r0_12.func.rankTable = r1_0
    end
    return r0_12
  end,
  Cleanup = function()
    -- line: [397, 399] id: 13
    r3_0 = false
  end,
}, {
  __index = require("char.Char"),
})
