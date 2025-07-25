-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("logic.char.CharStatus")
local r1_0 = nil
if _G.GameMode == _G.GameModeEvo then
  r1_0 = require("evo.char_tbl.tbl_c23_bell").CreateTable()
end
local r2_0 = require("resource.char_define")
local r3_0 = {
  "bell_0_0",
  "bell_0_1",
  "bell_0_2",
  "bell_0_3",
  "bell_0_4",
  "bell_0_5",
  "bell_0_6",
  "bell_0_7",
  "bell_0_8",
  "bell_0_9",
  "bell_0_10",
  "bell_eff_0_0",
  "bell_eff_0_1",
  "bell_eff_0_3"
}
local r4_0 = false
local r5_0 = _G.CharaParam[r2_0.CharId.Bell][1]
local r6_0 = _G.CharaParam[r2_0.CharId.Bell][2][1] * 0.01
local r7_0 = _G.CharaParam[r2_0.CharId.Bell][3][1] * 0.0001
local r8_0 = _G.CharaParam[r2_0.CharId.Bell][4][1]
local r9_0 = 3
local r10_0 = require("char.c23.c23_bell")
local r11_0 = require("char.c23.bell_eff")
local r12_0 = "/bell"
local function r13_0(r0_1, r1_1)
  -- line: [54, 95] id: 1
  local r2_1 = r1_1.max_hit
  local r5_1 = r1_1.range[2]
  local r6_1 = r1_1.xy[1]
  local r7_1 = r1_1.xy[2]
  local r8_1 = {}
  local r3_1 = r1_1.power[r1_1.level] * r1_1.buff_power
  local r9_1 = nil
  if r2_1 > 0 then
    for r13_1, r14_1 in pairs(_G.Enemys) do
      if r14_1.poison_effective.enabled == false and r14_1.attack[2] == true and 0 < r2_1 then
        r9_1 = math.pow(r14_1.sx + r14_1.sight[1] - r6_1, 2) + math.pow(r14_1.sy + r14_1.sight[2] - r7_1, 2)
        if r9_1 < r5_1 then
          if r14_1.attr == 1 then
            r3_1 = 0
          end
          r8_1 = {
            decelerationCoefficient = r7_0,
            sleepDurationTime = r3_1,
            timeToSleep = r8_0,
          }
          r14_1.func.poison(r14_1, r2_0.PoisonType.Sleep, r8_1)
          r2_1 = r2_1 - 1
        end
      end
    end
  end
  r1_1.max_hit = r2_1
end
local function r14_0(r0_2, r1_2, r2_2, r3_2)
  -- line: [98, 180] id: 2
  if r3_2.data and r3_2.data.transition then
    transit.Delete(r3_2.data.transition)
    r3_2.data.transition = nil
  end
  local function r4_2(r0_3, r1_3)
    -- line: [106, 109] id: 3
    anime.Pause(r0_3, true)
    anime.Show(r0_3, false)
  end
  local r5_2 = 1
  local r6_2 = r3_2.level
  if r6_2 > 1 then
    local r7_2 = _G.UserStatus[3]
    r5_2 = r7_2.range[r6_2][2] * 1.3 * (char.GetRangePowerup() + 100) * 0.01 / r7_2.range[1][2]
  end
  local r7_2 = {
    index = 0,
    xy = {
      r0_2,
      r1_2
    },
    target = r2_2,
    hit = false,
    max_hit = r5_0[r6_2],
    power = r3_2.power,
    range = r3_2.range,
  }
  local r8_2 = (120 - 256 * r5_2) * 0.5
  local r9_2 = r8_2
  if r6_2 == 1 then
    r8_2 = r8_2 - 10
    r9_2 = r9_2 - 15
  elseif r6_2 == 2 then
    r8_2 = r8_2 + 10
    r9_2 = r9_2 + 0
  elseif r6_2 == 3 then
    r8_2 = r8_2 + 25
    r9_2 = r9_2 + 20
  else
    r8_2 = r8_2 + 30
    r9_2 = r9_2 + 20
  end
  local r10_2 = anime.GetSprite(r3_2.anime)
  local r11_2 = anime.GetSprite(r3_2.attackEffect)
  r11_2.xScale = r5_2
  r11_2.yScale = r5_2
  r11_2.x = r0_2 + r8_2
  r11_2.y = r1_2 + r9_2
  anime.RegisterTrigger(r3_2.attackEffect, r9_0, r13_0, {
    max_hit = r5_0[r6_2],
    level = r3_2.level,
    power = r3_2.power,
    buff_power = r3_2.buff_power,
    xy = {
      r0_2,
      r1_2
    },
    range = r3_2.range,
  })
  anime.Show(r3_2.attackEffect, true)
  anime.Pause(r3_2.attackEffect, false)
  anime.RegisterFinish(r3_2.attackEffect, r4_2)
  sound.PlaySE(30, 4)
  return r7_2
end
local function r15_0(r0_4)
  -- line: [182, 187] id: 4
  if r0_4.data.transition then
    transition.cancel(r0_4.data.transition)
    r0_4.data.transition = nil
  end
end
local function r16_0(r0_5, r1_5)
  -- line: [190, 192] id: 5
  return 1
end
local function r17_0(r0_6, r1_6, r2_6)
  -- line: [196, 221] id: 6
  if game ~= nil and game.IsNotPauseTypeNone() then
    return true
  end
  local r3_6 = r1_6
  local r4_6 = r3_6.rigidity_ms + r2_6
  if r4_6 < r3_6.rigidity_es then
    r3_6.rigidity_ms = r4_6
    return true
  end
  r3_6.rigidity_ev = nil
  if r3_6.target and not r3_6.func.check(r3_6, r3_6.target) then
    r3_6.target = nil
  end
  r3_6.shooting = false
  r3_6.data.force_shooting = false
  return false
end
local function r18_0(r0_7, r1_7)
  -- line: [224, 242] id: 7
  if r1_7.target and not r1_7.func.check(r1_7, r1_7.target) then
    r1_7.target = nil
  end
  anime.Pause(r0_7, true)
  anime.SetTimer(r0_7, 0)
  local r3_7 = math.floor(r1_7.wait[r1_7.level] / (100 + r0_0.AttackSpeed) * 0.01)
  r1_7.rigidity_ms = 0
  r1_7.rigidity_es = r3_7
  r1_7.rigidity_ev = events.Register(r17_0, r1_7, 1)
end
local function r19_0(r0_8, r1_8, r2_8)
  -- line: [245, 275] id: 8
  if game ~= nil and game.IsNotPauseTypeNone() then
    return true
  end
  local r3_8 = r1_8
  if r3_8.anime.timer > 0 then
    r3_8.backTimer = r3_8.anime.timer
  end
  if not r3_8.shooting then
    r3_8.target = r3_8.func.search(r3_8)
    if r3_8.target then
      r3_8.shooting = true
      local r4_8 = r3_8.anime
      anime.Pause(r4_8, false)
      anime.Show(r4_8, true, {
        scale = (100 + r0_0.AttackSpeed) * 0.01,
      })
      anime.RegisterTrigger(r4_8, r3_8.shot_frame_nr, r3_8.func.shoot, r3_8)
      anime.RegisterFinish(r4_8, r3_8.func.finish, r3_8)
    end
  end
  return true
end
local function r20_0(r0_9)
  -- line: [279, 284] id: 9
  if r0_9.rigidity_ev then
    events.Delete(r0_9.rigidity_ev)
    r0_9.rigidity_ev = nil
  end
end
local function r21_0(r0_10, r1_10)
  -- line: [287, 291] id: 10
  local r2_10 = anime.GetPos(r0_10)
  assert(r1_10.func.load)
  r1_10.func.load(r2_10[1], r2_10[2], r1_10.target, r1_10)
end
local function r22_0(r0_11)
  -- line: [294, 296] id: 11
  r0_11.data.force_shooting = true
end
local function r23_0(r0_12, r1_12, r2_12)
  -- line: [299, 314] id: 12
  if r1_12 then
    local r3_12 = {}
    local r4_12 = r0_12.data.transition
    if r4_12 then
      r3_12.mode = transit.Pause(r4_12, true)
      r3_12.obj = r4_12
    end
    return r3_12
  else
    if r2_12.obj then
      transit.Pause(r2_12.obj, r2_12.mode)
    end
    return nil
  end
end
local function r24_0(r0_13, r1_13)
  -- line: [316, 320] id: 13
  anime.ReplaceSprite(r0_13.anime, r1_13 .. r12_0, r10_0.GetData())
  return r0_13
end
local r27_0 = setmetatable
local r28_0 = {
  Load = function(r0_14)
    -- line: [323, 378] id: 14
    if not r4_0 then
      preload.Load(r3_0, "data/game" .. r12_0)
      r4_0 = true
    end
    local r1_14 = r0_14.x
    local r2_14 = r0_14.y
    local r3_14 = anime.Register(r10_0.GetData(), r1_14, r2_14, "data/game" .. r12_0)
    local r4_14 = anime.GetSprite(r3_14)
    local r5_14 = anime.Register(r11_0.GetData(), 0, 0, "data/game" .. r12_0)
    _G.StanRoot:insert(anime.GetSprite(r5_14))
    anime.Pause(r5_14, true)
    anime.Show(r5_14, true)
    local r7_14 = display.newRect(_G.MyTgRoot, r1_14 - 40, r2_14 - 40, 80, 80)
    r7_14.alpha = 0.01
    r7_14.struct = r0_14
    r7_14.touch = r0_14.func.circle
    r7_14:addEventListener("touch", r7_14)
    r0_14.touch_area = r7_14
    r0_14.anime = r3_14
    r0_14.spr = r4_14
    r0_14.attackEffect = r5_14
    r0_14.func.load = r14_0
    r0_14.func.release = r20_0
    r0_14.func.range = r19_0
    r0_14.func.pointing = r16_0
    r0_14.func.shoot = r21_0
    r0_14.func.finish = r18_0
    r0_14.func.fumble = r22_0
    r0_14.func.pause = r23_0
    r0_14.func.release = r20_0
    r0_14.shot_frame_nr = 21
    r0_14.data = {}
    r0_14.data.force_shooting = false
    if _G.GameMode == _G.GameModeEvo then
      r0_14.func.changeSprite = r24_0
      r0_14.func.rankTable = r1_0
    end
    return r0_14
  end,
  Cleanup = function()
    -- line: [380, 382] id: 15
    r4_0 = false
  end,
  r24_0
}
return r27_0(r28_0, {
  __index = require("char.Char"),
})
