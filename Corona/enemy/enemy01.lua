-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("enemy.anm.zako01")
local function r1_0(r0_1)
  -- line: [9, 27] id: 1
  local r1_1 = r0_1.sx
  local r2_1 = r0_1.sy
  local r3_1 = display.newGroup()
  local r4_1 = anime.Register(r0_0.GetData(), 0, 0, "data/game")
  local r5_1 = anime.GetSprite(r4_1)
  anime.Loop(r4_1, true)
  anime.Show(r4_1, true)
  r3_1:insert(r5_1)
  anime.RegisterShowHook(r4_1, function(r0_2, r1_2, r2_2, r3_2)
    -- line: [18, 21] id: 2
    r0_2.xScale = r0_2.xScale * 0.625
    r0_2.yScale = r0_2.yScale * 0.625
  end)
  r0_1.anm = r4_1
  r0_1.sprite = r5_1
  r0_1.sort_sprite = r3_1
  r0_1.sort_z = r1_1 + r2_1 * 1000
end
local r2_0 = {
  "zako01_0_0",
  "zako01_0_1",
  "zako01_0_2"
}
return {
  Pop = r1_0,
  PreLoad = function()
    -- line: [35, 37] id: 3
    preload.Load(r2_0, "data/game")
  end,
}
