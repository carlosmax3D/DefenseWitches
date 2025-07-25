-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("logic.char.CharStatus")
local r1_0 = nil
if _G.GameMode == _G.GameModeEvo then
  r1_0 = require("evo.char_tbl.tbl_c07_bianca").CreateTable()
end
local r2_0 = {
  "c07_bianca01_0_0",
  "c07_bianca01_1_0",
  "c07_bianca01_1_1",
  "c07_bianca01_1_2",
  "c07_bianca02_1_0",
  "c07_bianca02_1_1",
  "c07_bianca02_1_2",
  "c07_bianca03_1_0",
  "c07_bianca03_1_1",
  "c07_bianca03_1_2",
  "c07_bianca04_1_0",
  "c07_bianca04_1_1",
  "c07_bianca04_1_2",
  "c07_bianca05_1_0",
  "c07_bianca05_1_1",
  "c07_bianca05_1_2",
  "afx07_firearrow"
}
local r3_0 = false
local r4_0 = 250
local r5_0 = {}
for r9_0 = 1, 8, 1 do
  table.insert(r5_0, require(string.format("char.c07.c07_bianca%02d", r9_0)))
end
local r6_0 = _G.CharaParam[7][1]
local r7_0 = _G.CharaParam[7][2]
local r8_0 = _G.CharaParam[7][4][1] / 100
local r9_0 = _G.CharaParam[7][3]
local r10_0 = {
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
local function r11_0(r0_1, r1_1, r2_1)
  -- line: [67, 84] id: 1
  local r3_1, r4_1, r5_1, r6_1 = unpack(r2_1)
  local r7_1 = r5_1 - r3_1
  local r8_1 = r6_1 - r4_1
  if r7_1 == 0 and r8_1 == 0 then
    return (r3_1 - r0_1) * (r3_1 - r0_1) + (r4_1 - r1_1) * (r4_1 - r1_1)
  end
  local r11_1 = -(r7_1 * (r3_1 - r0_1) + r8_1 * (r4_1 - r1_1)) / (r7_1 * r7_1 + r8_1 * r8_1)
  if r11_1 < 0 then
    r11_1 = 0
  end
  if r11_1 > 1 then
    r11_1 = 1
  end
  local r12_1 = r3_1 + r7_1 * r11_1
  local r13_1 = r4_1 + r8_1 * r11_1
  return (r0_1 - r12_1) * (r0_1 - r12_1) + (r1_1 - r13_1) * (r1_1 - r13_1)
end
local function r12_0(r0_2, r1_2, r2_2)
  -- line: [88, 174] id: 2
  local r3_2 = r1_2.my
  local r4_2 = r1_2.xy[1]
  local r5_2 = r1_2.xy[2]
  local r7_2 = r4_2
  local r8_2 = r5_2
  local r9_2 = r1_2.target_mx
  local r10_2 = r1_2.target_my
  local r11_2 = true
  for r15_2 = 1, r2_2 * _G.ShotSpeed, 1 do
    r7_2 = r7_2 + r9_2
    r8_2 = r8_2 + r10_2
    if r7_2 < 0 or _G.Width < r7_2 or r8_2 < 0 or _G.Height < r8_2 then
      r7_2 = r7_2 - r9_2
      r8_2 = r8_2 - r10_2
      r11_2 = false
      break
    end
  end
  local r12_2 = r3_2.level
  local r13_2 = r3_2.power[r12_2] * r3_2.buff_power
  local r15_2 = r6_0[r12_2]
  local r16_2 = r7_0[r12_2]
  local r17_2 = {
    r4_2,
    r5_2,
    r7_2,
    r8_2
  }
  local r18_2 = false
  local r19_2 = nil
  local r20_2 = nil
  local r21_2 = nil
  local r22_2 = r1_2.uid
  local r23_2 = table.copy(_G.Enemys, _G.CrashObject, _G.Flowers)
  local r24_2 = r1_2.max_through
  for r28_2, r29_2 in pairs(r23_2) do
    r18_2 = r29_2.attack[1]
    if not r29_2.living then
      r18_2 = false
    end
    if r29_2.bianca_uid == r22_2 then
      r18_2 = false
    end
    if r18_2 and r24_2 <= 0 then
      r18_2 = false
    end
    if r18_2 then
      r19_2 = r29_2.sx + r29_2.sight[1]
      r20_2 = r29_2.sy + r29_2.sight[2]
      r21_2 = r11_0(r19_2, r20_2, r17_2)
      if r21_2 < 2500 then
        r29_2.bianca_uid = r22_2
        if r29_2.attr == 1 or r29_2.attr == 4 then
          r13_2 = r13_2 * r8_0
        end
        if _G.GameMode == _G.GameModeEvo then
          r29_2.func.hit(r29_2, r13_2, r3_2)
        else
          r29_2.func.hit(r29_2, r13_2)
        end
        r29_2.func.burn(r29_2, r15_2, r16_2)
        r24_2 = r24_2 - 1
      end
    end
    if r24_2 <= 0 then
      break
    end
  end
  r1_2.max_through = r24_2
  if r24_2 > 0 then
    r1_2.xy[1] = r7_2
    r1_2.xy[2] = r8_2
    r1_2.sprite.x = r7_2
    r1_2.sprite.y = r8_2
  else
    r11_2 = false
  end
  if not r11_2 then
    display.remove(r1_2.sprite)
    table.remove(_G.ShotEvent, table.indexOf(_G.ShotEvent, r0_2))
  end
  return r11_2
end
local r13_0 = 1
local function r14_0(r0_3, r1_3, r2_3, r3_3)
  -- line: [178, 215] id: 3
  local r4_3 = r10_0[r3_3.vect]
  local r5_3 = r0_3 + r4_3[1]
  local r6_3 = r1_3 + r4_3[2]
  local r7_3 = {}
  local r8_3 = display.newImage("data/game/afx07_firearrow.png", true)
  r8_3:setReferencePoint(display.CenterReferencePoint)
  r8_3.x = r5_3
  r8_3.y = r6_3
  r8_3.isVisible = true
  local r9_3 = r3_3.target_rad
  r8_3.rotation = (math.deg(r9_3) + 180) % 360
  local r11_3 = {
    group = nil,
    sprite = r8_3,
    index = 0,
    xy = {
      r5_3,
      r6_3
    },
    target = r2_3,
    hit = false,
    my = r3_3,
    target_mx = math.cos(r9_3),
    target_my = math.sin(r9_3),
    uid = r3_3.uid * 65536 + r13_0,
    max_through = r9_0[r3_3.level],
  }
  r13_0 = r13_0 + 1
  if r13_0 > 65535 then
    r13_0 = 1
  end
  _G.MissleRoot:insert(r8_3)
  table.insert(_G.ShotEvent, events.Register(r12_0, r11_3, 0, false))
  sound.PlaySE(12, 8)
  return r11_3
end
local function r15_0(r0_4, r1_4)
  -- line: [218, 234] id: 4
  local r4_4 = r1_4.ptdiff
  local r5_4 = r1_4.sx + r4_4[1]
  local r6_4 = r1_4.sy + r4_4[2]
  local r9_4 = math.atan2(r6_4 - r0_4.y, r5_4 - r0_4.x)
  r0_4.target_ex = r5_4
  r0_4.target_ey = r6_4
  r0_4.target_rad = r9_4
  return (math.floor((r9_4 * 8 / math.pi + 7) / 2) + 3) % 8 + 1
end
local function r16_0(r0_5, r1_5, r2_5)
  -- line: [236, 260] id: 5
  if game ~= nil and game.IsNotPauseTypeNone() then
    return true
  end
  local r3_5 = r1_5
  local r4_5 = r3_5.rigidity_ms + r2_5
  if r4_5 < r3_5.rigidity_es then
    r3_5.rigidity_ms = r4_5
    return true
  end
  r3_5.rigidity_ev = nil
  if r3_5.target and not r3_5.func.check(r3_5, r3_5.target) then
    r3_5.target = nil
  end
  r3_5.shooting = false
  return false
end
local function r17_0(r0_6, r1_6)
  -- line: [262, 277] id: 6
  anime.Pause(r0_6, true)
  anime.SetTimer(r0_6, 0)
  local r6_6 = math.floor(r4_0 / (r1_6.wait[r1_6.level] + r0_0.AttackSpeed) / 100)
  r1_6.rigidity_ms = 0
  r1_6.rigidity_es = r6_6
  r1_6.rigidity_ev = events.Register(r16_0, r1_6, 1)
end
local function r18_0(r0_7)
  -- line: [279, 284] id: 7
  if r0_7.rigidity_ev then
    events.Delete(r0_7.rigidity_ev)
    r0_7.rigidity_ev = nil
  end
end
local function r19_0(r0_8, r1_8)
  -- line: [286, 293] id: 8
  for r6_8 = 1, r0_8.anime.nr, 1 do
    anime.ReplaceSprite(r0_8.anime.pack[r6_8], r1_8, r5_0[r6_8].GetData())
  end
  return r0_8
end
return setmetatable({
  ChangeSprite = r19_0,
  Load = function(r0_9)
    -- line: [296, 336] id: 9
    if not r3_0 then
      preload.Load(r2_0, "data/game")
      r3_0 = true
    end
    local r1_9 = {}
    local r2_9 = display.newGroup()
    local r3_9 = r0_9.x
    local r4_9 = r0_9.y
    for r8_9 = 1, 8, 1 do
      local r9_9 = anime.Register(r5_0[r8_9].GetData(), r3_9, r4_9, "data/game")
      r2_9:insert(anime.GetSprite(r9_9))
      table.insert(r1_9, r9_9)
    end
    local r5_9 = display.newRect(_G.MyTgRoot, r3_9 - 40, r4_9 - 40, 80, 80)
    r5_9.alpha = 0.01
    r5_9.struct = r0_9
    r5_9.touch = r0_9.func.circle
    r5_9:addEventListener("touch", r5_9)
    r0_9.touch_area = r5_9
    r0_9.anime = anime.Pack(unpack(r1_9))
    r0_9.spr = r2_9
    r0_9.func.load = r14_0
    r0_9.func.lockon = r15_0
    r0_9.func.finish = r17_0
    r0_9.func.release = r18_0
    r0_9.shot_frame_nr = 22
    if _G.GameMode == _G.GameModeEvo then
      r0_9.func.changeSprite = r19_0
      r0_9.func.rankTable = r1_0
    end
    return r0_9
  end,
  Cleanup = function()
    -- line: [338, 340] id: 10
    r3_0 = false
  end,
}, {
  __index = require("char.Char"),
})
