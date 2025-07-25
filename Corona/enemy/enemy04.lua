-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("enemy.anm.zako04")
local function r1_0(r0_1)
  -- line: [10, 32] id: 1
  r0_1.sy = r0_1.sy - 20
  local r1_1 = r0_1.sx
  local r2_1 = r0_1.sy
  local r3_1 = display.newGroup()
  local r4_1 = anime.Register(r0_0.GetData(), 0, 0, "data/game")
  local r5_1 = anime.GetSprite(r4_1)
  anime.Loop(r4_1, true)
  anime.Show(r4_1, true)
  r3_1:insert(r5_1)
  anime.RegisterShowHook(r4_1, function(r0_2, r1_2, r2_2, r3_2)
    -- line: [20, 23] id: 2
    r0_2.xScale = r0_2.xScale * 0.625
    r0_2.yScale = r0_2.yScale * 0.619
  end)
  r0_1.anm = r4_1
  r0_1.sprite = r5_1
  r0_1.sort_sprite = r3_1
  r0_1.sort_z = r1_1 + r2_1 * 1000
  r0_1.sight = {
    0,
    20
  }
  r0_1.ptdiff = {
    0,
    20
  }
  r0_1.efxdiff = {
    0,
    16
  }
end
local r2_0 = {
  "zako04_1_0",
  "zako04_1_1",
  "zako04_1_2",
  "zako04_1_3",
  "zako04_1_4",
  "zako04_1_5",
  "zako04_1_6",
  "zako04_1_7",
  "zako04_1_8",
  "zako04_1_9",
  "zako04_1_10",
  "zako04_1_11",
  "zako04_1_12",
  "zako04_1_13",
  "zako04_1_14",
  "zako04_1_15"
}
return {
  Pop = r1_0,
  PreLoad = function()
    -- line: [53, 55] id: 3
    preload.Load(r2_0, "data/game")
  end,
}
