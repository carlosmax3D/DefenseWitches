-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("enemy.anm.boss02")
local function r1_0(r0_1)
  -- line: [9, 37] id: 1
  local r1_1 = r0_1.sx
  local r2_1 = r0_1.sy
  local r3_1 = display.newGroup()
  local r4_1 = anime.Register(r0_0.GetData(), 0, 0, "data/game/boss02")
  local r5_1 = anime.GetSprite(r4_1)
  anime.Loop(r4_1, true)
  anime.Show(r4_1, true)
  r3_1:insert(r5_1)
  r0_1.anm = r4_1
  r0_1.sprite = r5_1
  r0_1.attack = {
    false,
    true
  }
  r0_1.htptpos[2] = -82
  r0_1.targetpos[2] = -110
  r0_1.route = _G.SkyRoute
  r0_1.root = _G.SkyRoot
  r0_1.slip_root = _G.SSlipRoot
  r0_1.stone_damage = 10
  r0_1.sight = {
    0,
    0
  }
  r0_1.ptdiff = {
    0,
    80
  }
  r0_1.flying = true
  r0_1.sort_sprite = r3_1
  r0_1.sort_z = r1_1 + r2_1 * 1000
  r0_1.shadow = display.newImage(_G.ShadowRoot, "data/game/zako_shadow.png", r1_1, r2_1 + 80)
end
local r2_0 = {
  "boss02_0_0",
  "boss02_0_1",
  "boss02_0_2",
  "boss02_0_3"
}
return {
  Pop = r1_0,
  PreLoad = function()
    -- line: [47, 50] id: 2
    preload.Load(r2_0, "data/game/boss02")
    preload.Load({
      "zako_shadow"
    }, "data/game")
  end,
}
