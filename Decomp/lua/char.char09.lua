-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = nil	-- notice: implicit variable refs by block#[5]
if _G.GameMode == _G.GameModeEvo then
  r0_0 = require("evo.char_tbl.tbl_c09_iris").CreateTable()
end
local r1_0 = {
  "c09_iris01_0_0",
  "c09_iris01_1_0",
  "c09_iris01_1_1",
  "c09_iris01_1_2",
  "c09_iris02_1_0",
  "c09_iris02_1_1",
  "c09_iris02_1_2",
  "c09_iris03_1_0",
  "c09_iris03_1_1",
  "c09_iris03_1_2",
  "c09_iris04_1_0",
  "c09_iris04_1_1",
  "c09_iris04_1_2",
  "c09_iris05_1_0",
  "c09_iris05_1_1",
  "c09_iris05_1_2",
  "afx09_freezingsphere",
  "afx09_icefog_0_0",
  "afx09_icefog_0_1"
}
local r2_0 = false
local r3_0 = {}
for r7_0 = 1, 8, 1 do
  table.insert(r3_0, require(string.format("char.c09.c09_iris%02d", r7_0)))
end
local r4_0 = require("char.c09.afx09_icefog")
local r5_0 = {
  {
    4,
    36
  },
  {
    -28,
    32
  },
  {
    -28,
    4
  },
  {
    -28,
    -20
  },
  {
    4,
    -36
  },
  {
    36,
    -20
  },
  {
    36,
    4
  },
  {
    36,
    32
  }
}
local r6_0 = _G.CharaParam[9][1]
local r7_0 = _G.CharaParam[9][2][1]
local r8_0 = _G.CharaParam[9][3][1] / 100
local r9_0 = _G.CharaParam[9][4]
local r10_0 = _G.CharaParam[9][5]
local function r11_0(r0_1, r1_1)
  -- line: [67, 69] id: 1
  anime.Remove(r0_1)
end
local function r12_0(r0_2, r1_2, r2_2)
  -- line: [71, 86] id: 2
  local r3_2 = anime.Register(r4_0.GetData(), r1_2, r2_2, "data/game")
  _G.StanRoot:insert(anime.GetSprite(r3_2))
  anime.RegisterFinish(r3_2, r11_0, r0_2)
  anime.Show(r3_2, true)
  anime.RegisterShowHook(r3_2, function(r0_3, r1_3, r2_3, r3_3)
    -- line: [80, 83] id: 3
    r0_3.xScale = r0_3.xScale * r3_3
    r0_3.yScale = r0_3.yScale * r3_3
  end, r10_0[r0_2.level] / r10_0[1] * 0.8)
  return r3_2
end
local function r13_0(r0_4, r1_4, r2_4)
  -- line: [89, 186] id: 4
  local r3_4 = r1_4.my
  local r4_4 = r1_4.xy[1]
  local r5_4 = r1_4.xy[2]
  local r6_4 = r1_4.target.sx + r1_4.target.sight[1]
  local r7_4 = r1_4.target.sy + r1_4.target.sight[2]
  local r10_4 = math.atan2(r7_4 - r5_4, r6_4 - r4_4)
  local r11_4 = (math.deg(r10_4) + 180) % 360
  local r12_4 = math.cos(r10_4)
  local r13_4 = math.sin(r10_4)
  local r14_4 = true
  local r15_4 = r2_4 * _G.ShotSpeed
  local r16_4 = r4_4 + r12_4 * r15_4
  local r17_4 = r5_4 + r13_4 * r15_4
  if util.GetDistance(r6_4, r7_4, r4_4, r5_4, r16_4, r17_4) < 25 then
    r1_4.hit = true
    r1_4.sprite.isVisible = false
  end
  r4_4 = r16_4
  r5_4 = r17_4
  r1_4.xy[1] = r4_4
  r1_4.xy[2] = r5_4
  if not r1_4.hit then
    r1_4.sprite.x = r4_4
    r1_4.sprite.y = r5_4
    r1_4.sprite.rotation = r11_4
  else
    local r19_4 = r3_4.level
    local r20_4 = r1_4.target
    local r21_4 = r3_4.power[r19_4]
    local r22_4 = r3_4.buff_power
    local r23_4 = r20_4.attr
    local r24_4 = r6_0[r19_4]
    local r25_4 = nil	-- notice: implicit variable refs by block#[8, 13]
    if r23_4 ~= 3 then
      r25_4 = r23_4 == 4
    else
      goto label_89	-- block#7 is visited secondly
    end
    if r25_4 then
      r21_4 = r21_4 * r8_0
    end
    r21_4 = r21_4 * r22_4
    display.remove(r1_4.sprite)
    r14_4 = false
    if _G.GameMode == _G.GameModeEvo then
      r20_4.func.hit(r20_4, r21_4, r3_4)
    else
      r20_4.func.hit(r20_4, r21_4)
    end
    if not r25_4 then
      r20_4.func.freeze(r20_4, r24_4, r7_0)
    end
    r1_4.ice_efx = r12_0(r3_4, r4_4, r5_4)
    local r26_4 = r9_0[r19_4]
    local r27_4 = r10_0[r19_4]
    local r28_4 = r27_4 * r27_4
    local r29_4 = nil
    local r30_4 = nil
    local r31_4 = nil
    local r32_4 = r20_4.attack[1]
    local r33_4 = r20_4.attack[2]
    for r37_4, r38_4 in pairs(_G.Enemys) do
      if r26_4 <= 0 then
        break
      else
        r29_4 = true
        if r38_4.attr == 3 or r38_4.attr == 4 then
          r29_4 = false
        end
        if r38_4.freeze_efx then
          r29_4 = false
        end
        if r32_4 and r38_4.attack[1] == false then
          r29_4 = false
        end
        if r33_4 and r38_4.attack[2] == false then
          r29_4 = false
        end
        if r29_4 then
          r30_4 = r38_4.sx + r38_4.sight[1]
          r31_4 = r38_4.sy + r38_4.sight[2]
          if (r30_4 - r4_4) * (r30_4 - r4_4) + (r31_4 - r5_4) * (r31_4 - r5_4) < r28_4 then
            r38_4.func.freeze(r38_4, r24_4, r7_0)
            r26_4 = r26_4 - 1
          end
        end
      end
    end
  end
  if not r14_4 then
    table.remove(_G.ShotEvent, table.indexOf(_G.ShotEvent, r0_4))
  end
  return r14_4
end
local function r14_0(r0_5, r1_5, r2_5, r3_5)
  -- line: [189, 214] id: 5
  local r4_5 = r5_0[r3_5.vect]
  local r5_5 = r0_5 + r4_5[1]
  local r6_5 = r1_5 + r4_5[2]
  local r7_5 = display.newImage("data/game/afx09_freezingsphere.png", true)
  r7_5:setReferencePoint(display.CenterReferencePoint)
  r7_5.x = r5_5
  r7_5.y = r6_5
  r7_5.isVisible = true
  local r8_5 = {
    group = nil,
    sprite = r7_5,
    index = 0,
    xy = {
      r5_5,
      r6_5
    },
    target = r2_5,
    hit = false,
    my = r3_5,
  }
  _G.MissleRoot:insert(r7_5)
  table.insert(_G.ShotEvent, events.Register(r13_0, r8_5, 0, false))
  sound.PlaySE(14, 10)
  return r8_5
end
local function r15_0(r0_6, r1_6)
  -- line: [216, 223] id: 6
  for r6_6 = 1, r0_6.anime.nr, 1 do
    anime.ReplaceSprite(r0_6.anime.pack[r6_6], r1_6, r3_0[r6_6].GetData())
  end
  return r0_6
end
return setmetatable({
  ChangeSprite = r15_0,
  Load = function(r0_7)
    -- line: [226, 263] id: 7
    if not r2_0 then
      preload.Load(r1_0, "data/game")
      r2_0 = true
    end
    local r1_7 = {}
    local r2_7 = display.newGroup()
    local r3_7 = r0_7.x
    local r4_7 = r0_7.y
    for r8_7 = 1, 8, 1 do
      local r9_7 = anime.Register(r3_0[r8_7].GetData(), r3_7, r4_7, "data/game")
      r2_7:insert(anime.GetSprite(r9_7))
      table.insert(r1_7, r9_7)
    end
    local r5_7 = display.newRect(_G.MyTgRoot, r3_7 - 40, r4_7 - 40, 80, 80)
    r5_7.alpha = 0.01
    r5_7.struct = r0_7
    r5_7.touch = r0_7.func.circle
    r5_7:addEventListener("touch", r5_7)
    r0_7.touch_area = r5_7
    r0_7.anime = anime.Pack(unpack(r1_7))
    r0_7.spr = r2_7
    r0_7.func.load = r14_0
    r0_7.shot_frame_nr = 27
    if _G.GameMode == _G.GameModeEvo then
      r0_7.func.changeSprite = r15_0
      r0_7.func.rankTable = r0_0
    end
    return r0_7
  end,
  Cleanup = function()
    -- line: [265, 267] id: 8
    r2_0 = false
  end,
}, {
  __index = require("char.Char"),
})
