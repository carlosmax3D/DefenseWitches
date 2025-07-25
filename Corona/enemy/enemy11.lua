-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("enemy.anm.zako11")
local function r1_0(r0_1, r1_1, r2_1)
  -- line: [11, 18] id: 1
  local r3_1 = anime.GetTimer(r0_1.anm)
  if 3 <= r3_1 and r3_1 <= 11 or 16 <= r3_1 and r3_1 <= 25 then
    return {
      r1_1,
      r2_1
    }
  else
    return {
      0,
      0
    }
  end
end
local function r2_0(r0_2)
  -- line: [20, 37] id: 2
  local r1_2 = r0_2.sx
  local r2_2 = r0_2.sy
  local r3_2 = display.newGroup()
  local r4_2 = anime.Register(r0_0.GetData(), 0, 0, "data/game")
  local r5_2 = anime.GetSprite(r4_2)
  anime.Loop(r4_2, true)
  anime.Show(r4_2, true)
  r3_2:insert(r5_2)
  r0_2.anm = r4_2
  r0_2.sprite = r5_2
  r0_2.sort_sprite = r3_2
  r0_2.sort_z = r1_2 + r2_2 * 1000
  r0_2.func.move = r1_0
  r0_2.hard = false
end
local r3_0 = {
  "zako11_1_0",
  "zako11_1_2",
  "zako11_1_3",
  "zako11_1_4",
  "zako11_1_5",
  "zako11_1_6",
  "zako11_1_7",
  "zako11_1_8",
  "zako11_1_9",
  "zako11_1_10",
  "zako11_1_11",
  "zako11_1_12",
  "zako11_1_17",
  "zako11_1_18",
  "zako11_1_19",
  "zako11_1_20",
  "zako11_1_22",
  "zako11_1_23",
  "zako11_1_24"
}
return {
  Pop = r2_0,
  PreLoad = function()
    -- line: [62, 64] id: 3
    preload.Load(r3_0, "data/game")
  end,
}
