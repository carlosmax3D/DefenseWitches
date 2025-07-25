-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("enemy.anm.zako04")
local function r1_0(r0_1)
  -- line: [10, 29] id: 1
  r0_1.sy = r0_1.sy - 40
  local r1_1 = r0_1.sx
  local r2_1 = r0_1.sy
  local r3_1 = display.newGroup()
  local r4_1 = anime.Register(r0_0.GetData(), 0, 0, "data/game")
  local r5_1 = anime.GetSprite(r4_1)
  anime.Loop(r4_1, true)
  anime.Show(r4_1, true)
  r3_1:insert(r5_1)
  r0_1.anm = r4_1
  r0_1.sprite = r5_1
  r0_1.stone_damage = 3
  r0_1.sort_sprite = r3_1
  r0_1.sort_z = r1_1 + r2_1 * 1000
  r0_1.sight = {
    0,
    40
  }
  r0_1.ptdiff = {
    0,
    40
  }
  r0_1.efxdiff = {
    0,
    32
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
    -- line: [51, 53] id: 2
    preload.Load(r2_0, "data/game")
  end,
}
