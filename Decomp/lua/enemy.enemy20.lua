-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("enemy.anm.boss08")
local function r1_0(r0_1)
  -- line: [9, 27] id: 1
  local r1_1 = r0_1.sx
  local r2_1 = r0_1.sy
  local r3_1 = display.newGroup()
  local r4_1 = anime.Register(r0_0.GetData(), 0, 0, "data/game/boss08")
  local r5_1 = anime.GetSprite(r4_1)
  anime.Loop(r4_1, true)
  anime.Show(r4_1, true)
  r3_1:insert(r5_1)
  r0_1.anm = r4_1
  r0_1.sprite = r5_1
  r0_1.attack = {
    true,
    false
  }
  r0_1.htptpos[2] = -100
  r0_1.targetpos[2] = -110
  r0_1.stone_damage = 10
  r0_1.sort_sprite = r3_1
  r0_1.sort_z = r1_1 + r2_1 * 1000
end
local r2_0 = {
  "boss08_0_0",
  "boss08_1_0",
  "boss08_1_1",
  "boss08_1_2",
  "boss08_1_3",
  "boss08_2_0",
  "boss08_2_1",
  "boss08_2_2",
  "boss08_3_0",
  "boss08_3_1",
  "boss08_3_2"
}
return {
  Pop = r1_0,
  PreLoad = function()
    -- line: [44, 46] id: 2
    preload.Load(r2_0, "data/game/boss08")
  end,
}
