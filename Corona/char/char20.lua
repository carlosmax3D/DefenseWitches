-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = nil	-- notice: implicit variable refs by block#[5]
if _G.GameMode == _G.GameModeEvo then
  r0_0 = require("evo.char_tbl.tbl_c20_daisy").CreateTable()
end
local r1_0 = {
  "c20_daisy01_0_0",
  "c20_daisy01_1_0",
  "c20_daisy01_1_1",
  "c20_daisy01_1_2",
  "c20_daisy02_1_0",
  "c20_daisy02_1_1",
  "c20_daisy02_1_2",
  "c20_daisy03_1_0",
  "c20_daisy03_1_1",
  "c20_daisy03_1_2",
  "c20_daisy04_1_0",
  "c20_daisy04_1_1",
  "c20_daisy04_1_2",
  "c20_daisy05_1_0",
  "c20_daisy05_1_1",
  "c20_daisy05_1_2",
  "afx01_energyball"
}
local r2_0 = false
local r3_0 = "/daisy/t02"
local r4_0 = {}
for r8_0 = 1, 8, 1 do
  table.insert(r4_0, require(string.format("char.c20.t02.c20_daisy%02d", r8_0)))
end
local r5_0 = {
  {
    0,
    28
  },
  {
    -36,
    20
  },
  {
    -44,
    8
  },
  {
    -36,
    -4
  },
  {
    0,
    -44
  },
  {
    36,
    -4
  },
  {
    44,
    8
  },
  {
    36,
    20
  }
}
local function r6_0(r0_1, r1_1, r2_1)
  -- line: [74, 137] id: 1
  local r3_1 = r1_1.my
  local r4_1 = r1_1.xy[1]
  local r5_1 = r1_1.xy[2]
  local r6_1 = r1_1.target.sx + r1_1.target.sight[1]
  local r7_1 = r1_1.target.sy + r1_1.target.sight[2]
  local r10_1 = math.atan2(r7_1 - r5_1, r6_1 - r4_1)
  local r11_1 = (math.deg(r10_1) + 180) % 360
  local r12_1 = math.cos(r10_1)
  local r13_1 = math.sin(r10_1)
  local r14_1 = true
  local r15_1 = r2_1 * _G.ShotSpeed
  local r16_1 = r4_1 + r12_1 * r15_1
  local r17_1 = r5_1 + r13_1 * r15_1
  if util.GetDistance(r6_1, r7_1, r4_1, r5_1, r16_1, r17_1) < 25 then
    r1_1.hit = true
    r1_1.sprite.isVisible = false
  end
  r4_1 = r16_1
  r5_1 = r17_1
  r1_1.xy[1] = r4_1
  r1_1.xy[2] = r5_1
  if not r1_1.hit then
    r1_1.sprite.x = r4_1
    r1_1.sprite.y = r5_1
    r1_1.sprite.rotation = r11_1
  else
    local r20_1 = r1_1.target.attr
    local r21_1 = r3_1.power[r3_1.level] * r3_1.buff_power
    if r20_1 == 5 then
      r21_1 = r21_1 * 0.3
    elseif r20_1 == 6 then
      r21_1 = r21_1 * 0.6
    end
    display.remove(r1_1.sprite)
    r14_1 = false
    if _G.GameMode == _G.GameModeEvo then
      r1_1.target.func.hit(r1_1.target, r21_1, r3_1)
    else
      r1_1.target.func.hit(r1_1.target, r21_1)
    end
  end
  if not r14_1 then
    table.remove(_G.ShotEvent, table.indexOf(_G.ShotEvent, r0_1))
  end
  return r14_1
end
local function r7_0(r0_2, r1_2, r2_2, r3_2)
  -- line: [140, 164] id: 2
  local r4_2 = r5_0[r3_2.vect]
  local r5_2 = r0_2 + r4_2[1]
  local r6_2 = r1_2 + r4_2[2]
  local r7_2 = display.newImage("data/game" .. r3_0 .. "/afx01_energyball.png")
  r7_2:setReferencePoint(display.CenterReferencePoint)
  r7_2.x = r5_2
  r7_2.y = r6_2
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
  table.insert(_G.ShotEvent, events.Register(r6_0, r8_2, 0, false))
  sound.PlaySE(11, 2)
  return r8_2
end
local function r8_0(r0_3, r1_3)
  -- line: [166, 175] id: 3
  r1_3 = r1_3 .. r3_0
  for r6_3 = 1, r0_3.anime.nr, 1 do
    anime.ReplaceSprite(r0_3.anime.pack[r6_3], r1_3, r4_0[r6_3].GetData())
  end
  return r0_3
end
return setmetatable({
  ChangeSprite = r8_0,
  Load = function(r0_4)
    -- line: [178, 216] id: 4
    if not r2_0 then
      preload.Load(r1_0, "data/game" .. r3_0)
      r2_0 = true
    end
    local r1_4 = {}
    local r2_4 = display.newGroup()
    local r3_4 = r0_4.x
    local r4_4 = r0_4.y
    for r8_4 = 1, 8, 1 do
      local r9_4 = anime.Register(r4_0[r8_4].GetData(), r3_4, r4_4, "data/game" .. r3_0)
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
    r0_4.func.load = r7_0
    r0_4.shot_frame_nr = 15
    if _G.GameMode == _G.GameModeEvo then
      r0_4.func.changeSprite = r8_0
      r0_4.func.rankTable = r0_0
    end
    return r0_4
  end,
  Cleanup = function()
    -- line: [218, 220] id: 5
    r2_0 = false
  end,
}, {
  __index = require("char.Char"),
})
