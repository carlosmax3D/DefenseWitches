-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("enemy.anm.zako10")
local r1_0 = _G.RoadRoute
local function r2_0(r0_1, r1_1, r2_1)
  -- line: [14, 40] id: 1
  if game ~= nil and game.IsNotPauseTypeNone() then
    return true
  end
  local r3_1 = r1_1.index
  local r4_1 = r1_0[r3_1]
  enemy.PopEnemy(11, {
    sx = r4_1[1],
    sy = r4_1[2],
    ex = r4_1[3],
    ey = r4_1[4],
    index = r4_1[5],
  })
  _G.PopEnemyShield = false
  r1_1.num = r1_1.num - 1
  if r1_1.num <= 0 then
    return false
  end
  r3_1 = r3_1 - 20
  if r3_1 < 1 then
    r3_1 = 1
  end
  r1_1.index = r3_1
  return true
end
local function r3_0(r0_2)
  -- line: [43, 60] id: 2
  anime.Show(r0_2.burst_anm, true)
  if r0_2.isBom ~= true then
    _G.PopEnemyShield = true
    local r1_2 = r0_2.move_index
    local r2_2 = #r1_0
    if r2_2 < r1_2 then
      r1_2 = r2_2
    end
    events.Register(r2_0, {
      num = 5,
      index = r1_2,
    }, 133.33333333333334)
  end
end
local function r4_0(r0_3, r1_3, r2_3)
  -- line: [63, 70] id: 3
  local r3_3 = anime.GetTimer(r0_3.anm)
  if 10 <= r3_3 and r3_3 <= 15 then
    return {
      r1_3,
      r2_3
    }
  else
    return {
      0,
      0
    }
  end
end
local function r5_0(r0_4)
  -- line: [72, 97] id: 4
  local r1_4 = r0_4.sx
  local r2_4 = r0_4.sy
  local r3_4 = display.newGroup()
  local r4_4 = anime.Register(r0_0.GetData(), 0, 0, "data/game")
  local r5_4 = anime.GetSprite(r4_4)
  anime.Loop(r4_4, true)
  anime.Show(r4_4, true)
  r3_4:insert(r5_4)
  anime.RegisterShowHook(r4_4, function(r0_5, r1_5, r2_5, r3_5)
    -- line: [81, 84] id: 5
    r0_5.xScale = r0_5.xScale * 0.625
    r0_5.yScale = r0_5.yScale * 0.625
  end)
  r0_4.anm = r4_4
  r0_4.sprite = r5_4
  r0_4.func.move = r4_0
  r0_4.func.burst = r3_0
  r0_4.hard = false
  r0_4.move_index = 1
  r0_4.sort_sprite = r3_4
  r0_4.sort_z = r1_4 + r2_4 * 1000
  r0_4.no_snipe = true
end
local r6_0 = {
  "zako10_1_0",
  "zako10_1_1",
  "zako10_1_2",
  "zako10_1_3",
  "zako10_1_4"
}
return {
  Pop = r5_0,
  PreLoad = function()
    -- line: [108, 110] id: 6
    preload.Load(r6_0, "data/game")
  end,
}
