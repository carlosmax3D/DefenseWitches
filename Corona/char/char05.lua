-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = nil	-- notice: implicit variable refs by block#[5]
if _G.GameMode == _G.GameModeEvo then
  r0_0 = require("evo.char_tbl.tbl_c05_chiara").CreateTable()
end
local r1_0 = {
  "c05_chiara01_0_0",
  "c05_chiara01_1_0",
  "c05_chiara01_1_1",
  "c05_chiara01_1_2",
  "c05_chiara02_1_0",
  "c05_chiara02_1_1",
  "c05_chiara02_1_2",
  "c05_chiara03_1_0",
  "c05_chiara03_1_1",
  "c05_chiara03_1_2",
  "c05_chiara04_1_0",
  "c05_chiara04_1_1",
  "c05_chiara04_1_2",
  "c05_chiara05_1_0",
  "c05_chiara05_1_1",
  "c05_chiara05_1_2",
  "afx05_windspear"
}
local r2_0 = false
local r3_0 = {}
for r7_0 = 1, 8, 1 do
  table.insert(r3_0, require(string.format("char.c05.c05_chiara%02d", r7_0)))
end
local r4_0 = {
  {
    0,
    32
  },
  {
    -32,
    20
  },
  {
    -40,
    -4
  },
  {
    -32,
    -28
  },
  {
    0,
    -40
  },
  {
    32,
    -28
  },
  {
    40,
    -4
  },
  {
    32,
    20
  }
}
local function r5_0(r0_1, r1_1, r2_1)
  -- line: [54, 115] id: 1
  local r3_1 = false
  local r4_1 = r1_1.target
  if r4_1 then
    local r5_1 = r1_1.my
    local r6_1 = r1_1.xy[1]
    local r7_1 = r1_1.xy[2]
    local r8_1 = r4_1.sx + r4_1.sight[1]
    local r9_1 = r4_1.sy + r4_1.sight[2]
    local r12_1 = math.atan2(r9_1 - r7_1, r8_1 - r6_1)
    local r13_1 = (math.deg(r12_1) + 180) % 360
    local r14_1 = math.cos(r12_1)
    local r15_1 = math.sin(r12_1)
    r3_1 = true
    local r16_1 = r2_1 * _G.ShotSpeed
    local r17_1 = r6_1 + r14_1 * r16_1
    local r18_1 = r7_1 + r15_1 * r16_1
    if util.GetDistance(r8_1, r9_1, r6_1, r7_1, r17_1, r18_1) < 25 then
      r1_1.hit = true
      r1_1.sprite.isVisible = false
    end
    r6_1 = r17_1
    r7_1 = r18_1
    r1_1.xy[1] = r6_1
    r1_1.xy[2] = r7_1
    if not r1_1.hit then
      r1_1.sprite.x = r6_1
      r1_1.sprite.y = r7_1
      r1_1.sprite.rotation = r13_1
    else
      local r20_1 = r5_1.power[r5_1.level] * r5_1.buff_power
      r3_1 = false
      if _G.GameMode == _G.GameModeEvo then
        r1_1.target.func.hit(r1_1.target, r20_1, r5_1)
      else
        r1_1.target.func.hit(r1_1.target, r20_1)
      end
    end
  end
  if not r3_1 then
    display.remove(r1_1.sprite)
    table.remove(_G.ShotEvent, table.indexOf(_G.ShotEvent, r0_1))
  end
  return r3_1
end
local function r6_0(r0_2, r1_2, r2_2, r3_2)
  -- line: [118, 144] id: 2
  local r4_2 = r4_0[r3_2.vect]
  local r5_2 = r0_2 + r4_2[1]
  local r6_2 = r1_2 + r4_2[2]
  local r7_2 = display.newImage("data/game/afx05_windspear.png", true)
  r7_2:setReferencePoint(display.CenterReferencePoint)
  r7_2.x = r5_2
  r7_2.y = r6_2
  r7_2.isVisible = true
  local r8_2 = {
    group = nil,
    sprite = r7_2,
    index = 0,
    xy = {
      r5_2,
      r6_2
    },
    target = r2_2,
    hit = false,
    my = r3_2,
  }
  _G.MissleRoot:insert(r7_2)
  table.insert(_G.ShotEvent, events.Register(r5_0, r8_2, 0, false))
  sound.PlaySE(15, 6)
  return r8_2
end
local function r7_0(r0_3, r1_3)
  -- line: [146, 153] id: 3
  for r6_3 = 1, r0_3.anime.nr, 1 do
    anime.ReplaceSprite(r0_3.anime.pack[r6_3], r1_3, r3_0[r6_3].GetData())
  end
  return r0_3
end
return setmetatable({
  ChangeSprite = r7_0,
  Load = function(r0_4)
    -- line: [156, 193] id: 4
    if not r2_0 then
      preload.Load(r1_0, "data/game")
      r2_0 = true
    end
    local r1_4 = {}
    local r2_4 = display.newGroup()
    local r3_4 = r0_4.x
    local r4_4 = r0_4.y
    for r8_4 = 1, 8, 1 do
      local r9_4 = anime.Register(r3_0[r8_4].GetData(), r3_4, r4_4, "data/game")
      r2_4:insert(anime.GetSprite(r9_4))
      table.insert(r1_4, r9_4)
    end
    local r5_4 = display.newRect(_G.MyTgRoot, r3_4 - 40, r4_4 - 40, 80, 80)
    r5_4.alpha = 0.01
    r5_4.struct = r0_4
    r5_4.touch = r0_4.func.circle
    r5_4:addEventListener("touch", r5_4)
    r0_4.touch_area = r5_4
    r0_4.anime = anime.Pack(unpack(r1_4))
    r0_4.spr = r2_4
    r0_4.func.load = r6_0
    r0_4.shot_frame_nr = 17
    if _G.GameMode == _G.GameModeEvo then
      r0_4.func.changeSprite = r7_0
      r0_4.func.rankTable = r0_0
    end
    return r0_4
  end,
  ShotFunc = function(r0_5, r1_5, r2_5)
    -- line: [196, 196] id: 5
    return r5_0(r0_5, r1_5, r2_5)
  end,
  Cleanup = function()
    -- line: [198, 200] id: 6
    r2_0 = false
  end,
}, {
  __index = require("char.Char"),
})
