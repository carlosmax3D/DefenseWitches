-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("enemy.anm.zako06")
local function r1_0(r0_1, r1_1)
  -- line: [11, 29] id: 1
  local r2_1 = r1_1.move_vect
  local r3_1 = r1_1.sort_sprite
  local r4_1 = r3_1.xScale
  local r5_1 = r1_1.htptspr
  if 0 < r4_1 and r2_1 < 0 then
    r3_1.xScale = -r4_1
    if r5_1 then
      r5_1.xScale = -1
      r5_1.x = 24
    end
  elseif r4_1 < 0 and 0 < r2_1 then
    r3_1.xScale = -r4_1
    if r5_1 then
      r5_1.xScale = 1
      r5_1.x = -24
    end
  end
end
local function r2_0(r0_2)
  -- line: [31, 51] id: 2
  local r1_2 = r0_2.sx
  local r2_2 = r0_2.sy
  local r3_2 = display.newGroup()
  local r4_2 = anime.Register(r0_0.GetData(), -16, -12, "data/game")
  local r5_2 = anime.GetSprite(r4_2)
  anime.Loop(r4_2, true)
  anime.Show(r4_2, true)
  r3_2:insert(r5_2)
  anime.RegisterShowHook(r4_2, function(r0_3, r1_3, r2_3, r3_3)
    -- line: [40, 43] id: 3
    r0_3.xScale = r0_3.xScale * 0.625
    r0_3.yScale = r0_3.yScale * 0.619
  end)
  anime.RegisterShowHook3(r4_2, r1_0, r0_2)
  r0_2.anm = r4_2
  r0_2.sprite = r5_2
  r0_2.sort_sprite = r3_2
  r0_2.sort_z = r1_2 + r2_2 * 1000
  r0_2.not_hook2 = true
end
local r3_0 = {
  "zako06_1_0",
  "zako06_1_1",
  "zako06_1_2",
  "zako06_1_3"
}
return {
  Pop = r2_0,
  PreLoad = function()
    -- line: [61, 63] id: 4
    preload.Load(r3_0, "data/game")
  end,
}
