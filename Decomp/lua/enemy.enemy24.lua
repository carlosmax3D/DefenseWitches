-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("enemy.anm.zako02")
local function r1_0(r0_1)
  -- line: [9, 29] id: 1
  r0_1.sy = r0_1.sy - 4
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
  r0_1.hard = false
  r0_1.stone_damage = 3
  r0_1.sort_sprite = r3_1
  r0_1.sort_z = r1_1 + r2_1 * 1000
  r0_1.sight = {
    0,
    4
  }
  r0_1.ptdiff = {
    0,
    4
  }
  r0_1.efxdiff = {
    0,
    0
  }
end
local r2_0 = {
  "zako02_1_0",
  "zako02_1_1",
  "zako02_1_2"
}
return {
  Pop = r1_0,
  PreLoad = function()
    -- line: [38, 40] id: 2
    preload.Load(r2_0, "data/game")
  end,
}
