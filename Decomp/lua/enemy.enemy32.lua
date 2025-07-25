-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("enemy.anm.zako10")
local r1_0 = _G.RoadRoute
local function r2_0(r0_1, r1_1, r2_1)
  -- line: [13, 37] id: 1
  if _G.Pause then
    return true
  end
  local r3_1 = r1_1.index
  local r4_1 = r1_0[r3_1]
  enemy.PopEnemy(10, {
    sx = r4_1[1],
    sy = r4_1[2],
    ex = r4_1[3],
    ey = r4_1[4],
    index = r4_1[5],
    move_index = r3_1,
  })
  _G.PopEnemyShield = false
  r1_1.num = r1_1.num - 1
  if r1_1.num <= 0 then
    return false
  end
  r3_1 = r3_1 - 40
  if r3_1 < 1 then
    r3_1 = 1
  end
  r1_1.index = r3_1
  return true
end
local function r3_0(r0_2)
  -- line: [40, 55] id: 2
  anime.Show(r0_2.burst_anm, true)
  if r0_2.isBom ~= true then
    _G.PopEnemyShield = true
    local r1_2 = r0_2.move_index
    local r2_2 = #r1_0
    if r2_2 < r1_2 then
      r1_2 = r2_2
    end
    events.Register(r2_0, {
      num = 3,
      index = r1_2,
    }, 266.6666666666667)
  end
end
local function r4_0(r0_3, r1_3, r2_3)
  -- line: [58, 65] id: 3
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
  -- line: [67, 93] id: 4
  r0_4.sy = r0_4.sy - 8
  local r1_4 = r0_4.sx
  local r2_4 = r0_4.sy
  local r3_4 = display.newGroup()
  local r4_4 = anime.Register(r0_0.GetData(), 0, 0, "data/game")
  local r5_4 = anime.GetSprite(r4_4)
  anime.Loop(r4_4, true)
  anime.Show(r4_4, true)
  r3_4:insert(r5_4)
  r0_4.anm = r4_4
  r0_4.sprite = r5_4
  r0_4.stone_damage = 3
  r0_4.func.move = r4_0
  r0_4.func.burst = r3_0
  r0_4.hard = false
  r0_4.move_index = 1
  r0_4.sort_sprite = r3_4
  r0_4.sort_z = r1_4 + r2_4 * 1000
  r0_4.sight = {
    0,
    8
  }
  r0_4.ptdiff = {
    0,
    8
  }
  r0_4.efxdiff = {
    0,
    8
  }
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
    -- line: [104, 106] id: 5
    preload.Load(r6_0, "data/game")
  end,
}
