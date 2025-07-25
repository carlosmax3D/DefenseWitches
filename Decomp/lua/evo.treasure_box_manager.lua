-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("logic.char.CharStatus")
math.randomseed(os.time())
local r1_0 = 8
local r2_0 = 0
local r3_0 = 1000
local r4_0 = 0
local r5_0 = 39
local r6_0 = 0
local r7_0 = 99
local r8_0 = 8
local r9_0 = 30
local r10_0 = "exp"
local r11_0 = "orb"
local r12_0 = "flare"
local r13_0 = "rewind"
local r14_0 = 5
local r15_0 = 8
local r16_0 = 13
local r17_0 = 12
local r18_0 = 11
local r19_0 = 10
local r20_0 = 9
local r21_0 = 14
local r22_0 = {
  {
    r10_0,
    5
  },
  {
    r10_0,
    10
  },
  {
    r10_0,
    20
  },
  {
    r10_0,
    50
  },
  {
    r10_0,
    40
  },
  {
    r10_0,
    80
  },
  {
    r10_0,
    100
  },
  {
    r10_0,
    200
  },
  {
    r11_0,
    1
  },
  {
    r11_0,
    2
  },
  {
    r11_0,
    4
  },
  {
    r11_0,
    8
  },
  {
    r12_0,
    1
  },
  {
    r13_0,
    1
  }
}
local r23_0 = 1
local r24_0 = 2
local r25_0 = {
  {
    r23_0,
    1
  },
  {
    r23_0,
    2
  },
  {
    r23_0,
    3
  },
  {
    r23_0,
    4
  },
  {
    r24_0,
    5
  },
  {
    r24_0,
    6
  },
  {
    r24_0,
    7
  },
  {
    r24_0,
    8
  },
  {
    r23_0,
    9
  },
  {
    r24_0,
    10
  },
  {
    r24_0,
    11
  },
  {
    r24_0,
    12
  },
  {
    r24_0,
    13
  },
  {
    r23_0,
    14
  }
}
local r26_0 = {
  1,
  2,
  3,
  4
}
local r27_0 = nil
local function r28_0()
  -- line: [97, 123] id: 1
  local r0_1 = r0_0.YuikoStruct
  if r0_1 == nil or r0_1.level == nil or r0_1.level < 1 then
    return 0
  end
  local r1_1 = _G.CharaParam[22][2]
  if r1_1 == nil or #r1_1 < r0_1.level then
    return 0
  end
  local r2_1 = r1_1[r0_1.level]
  if r0_1.evo ~= nil and r0_1.evo.charDropRate ~= nil and 0 < r0_1.evo.charDropRate then
    r2_1 = r2_1 + r0_1.evo.charDropRate * 10
  end
  return r2_1
end
local function r29_0(r0_2)
  -- line: [132, 138] id: 2
  if math.random(r6_0, r7_0) < r8_0 * (1 + r0_2) then
    return true
  end
  return false
end
local function r30_0()
  -- line: [145, 151] id: 3
  if math.random(r2_0, r3_0) + r1_0 - r3_0 > 0 then
    return true
  end
  return false
end
local function r31_0(r0_4, r1_4)
  -- line: [159, 161] id: 4
  return math.floor((r0_4 / r9_0 + math.random(r4_0, r5_0) + r1_4) / 10)
end
local function r32_0(r0_5)
  -- line: [169, 187] id: 5
  if r0_5 < 1 or #r25_0 < r0_5 then
    return false
  end
  local r1_5 = r25_0[r0_5]
  if r1_5[1] == r24_0 then
    table.insert(r27_0.rich, r1_5[2])
  else
    table.insert(r27_0.normal, r1_5[2])
  end
  return true
end
local function r33_0()
  -- line: [192, 196] id: 6
  if r27_0 ~= nil then
    r27_0 = nil
  end
end
local function r38_0(r0_11, r1_11)
  -- line: [294, 302] id: 11
  local r2_11 = r31_0(r0_11, r1_11) + 1
  if r15_0 < r2_11 then
    r2_11 = r15_0
  end
  return r2_11
end
local function r39_0(r0_12)
  -- line: [309, 318] id: 12
  local r1_12 = r31_0(r0_12, 0) + 1
  if #r26_0 < r1_12 then
    r1_12 = #r26_0
  end
  return r26_0[r1_12]
end
return {
  Init = function()
    -- line: [201, 208] id: 7
    r33_0()
    r27_0 = {
      rich = {},
      normal = {},
    }
  end,
  Clean = function()
    -- line: [213, 215] id: 8
    r33_0()
  end,
  IsDrop = function(r0_9, r1_9)
    -- line: [223, 233] id: 9
    if r1_9 then
      local r2_9 = r0_9.evo.charDropRate
      if r2_9 == nil then
        return false
      end
      return r29_0(r2_9)
    else
      return r30_0()
    end
  end,
  IsDropYuikoAbility = function(r0_10, r1_10)
    -- line: [241, 285] id: 10
    local r2_10 = r28_0()
    if r2_10 == 0 then
      return false
    end
    if r1_10 == false then
      r2_10 = r2_10 * 10
      local r3_10 = r3_0 - r1_0
      if math.random(r2_0, r3_10) + r2_10 - r3_10 > 0 then
        return true
      end
      return false
    else
      local r3_10 = r0_10.evo.charDropRate
      if r3_10 == nil then
        return false
      end
      if math.random(r6_0, r7_0 - r8_0 * (1 + r3_10)) < r2_10 then
        return true
      end
      return false
    end
  end,
  GetDropNormalTreasureboxType = r39_0,
  GetDropTreasureboxType = r38_0,
  GetDropTreasureboxTypeYuikoAbility = function(r0_13, r1_13, r2_13)
    -- line: [330, 413] id: 13
    local r3_13 = 0
    local r4_13 = 0
    local r5_13 = r0_0.YuikoStruct
    if r5_13 == nil or r5_13.level == nil or r5_13.level < 1 then
      return 0
    end
    local r6_13 = nil
    local r7_13 = _G.CharaParam[22][3]
    if r7_13 == nil or #r7_13 < r5_13.level then
      r6_13 = 0
    else
      r6_13 = r7_13[r5_13.level]
    end
    if r5_13.evo ~= nil and r5_13.evo.evoLevel ~= nil and 0 < r5_13.evo.evoLevel then
      local r8_13 = nil
      local r9_13 = _G.CharaParam[22][4]
      if r9_13 == nil or #r9_13 < r5_13.evo.evoLevel then
        r8_13 = 0
      else
        r8_13 = r9_13[r5_13.evo.evoLevel]
      end
      r6_13 = r6_13 + r8_13
      if r5_13.evo.rareDropRateCoefficient ~= nil and 0 < r5_13.evo.rareDropRateCoefficient then
        r4_13 = r5_13.evo.rareDropRateCoefficient * 100
      end
    end
    if math.random(99) < r6_13 + r4_13 then
      local r9_13 = math.random(999)
      if r9_13 < 50 and r2_13 <= 3 then
        r3_13 = r16_0
      elseif r9_13 < 100 then
        r3_13 = r17_0
      elseif r9_13 < 200 then
        r3_13 = r18_0
      elseif r9_13 < 700 then
        r3_13 = r19_0
      else
        r3_13 = r38_0(r0_13, r4_13)
        if r3_13 < r14_0 then
          r3_13 = r14_0
        end
      end
    else
      local r9_13 = math.random(999)
      if r9_13 < 50 and r1_13 <= 3 then
        r3_13 = r21_0
      elseif r9_13 < 150 then
        r3_13 = r20_0
      else
        r3_13 = r39_0(r0_13)
      end
    end
    return r3_13
  end,
  AddTreasurebox = function(r0_14)
    -- line: [419, 421] id: 14
    r32_0(r0_14)
  end,
  IsGetTreasurebox = function()
    -- line: [426, 432] id: 15
    if #r27_0.rich + #r27_0.normal > 0 then
      return true
    end
    return false
  end,
  GetTreasureboxFilename = function(r0_16)
    -- line: [437, 445] id: 16
    if r0_16 == r23_0 then
      return "stage_icon_treasurebox_r1"
    elseif r0_16 == r24_0 then
      return "stage_icon_treasurebox_r2"
    else
      return nil
    end
  end,
  GetTreasureboxInfo = function(r0_17)
    -- line: [450, 452] id: 17
    return r25_0[r0_17]
  end,
  GetRichNum = function()
    -- line: [457, 459] id: 18
    return #r27_0.rich
  end,
  GetNormalNum = function()
    -- line: [464, 466] id: 19
    return #r27_0.normal
  end,
  GetRichTreasurebox = function()
    -- line: [471, 473] id: 20
    return r27_0.rich
  end,
  GetNormalTreasurebox = function()
    -- line: [478, 480] id: 21
    return r27_0.normal
  end,
  GetTreasureboxData = function()
    -- line: [485, 487] id: 22
    return r27_0
  end,
  GetTreasureboxDropItemData = function(r0_23)
    -- line: [492, 498] id: 23
    if r0_23 == nil or #r22_0 < r0_23 then
      return nil
    end
    return r22_0[r0_23]
  end,
  SetTreasureboxData = function(r0_24)
    -- line: [503, 505] id: 24
    r27_0 = r0_24
  end,
  TreasureboxItemKeyExp = r10_0,
  TreasureboxItemKeyOrb = r11_0,
  TreasureboxItemKeyFlare = r12_0,
  TreasureboxItemKeyRewind = r13_0,
  TreasureboxDropItemListIndexRichMin = r14_0,
  TreasureboxDropItemListIndexMaxexp = r15_0,
  TreasureboxDropItemListIndexFlare = r16_0,
  TreasureboxDropItemListIndexOrb8 = r17_0,
  TreasureboxDropItemListIndexOrb4 = r18_0,
  TreasureboxDropItemListIndexOrb2 = r19_0,
  TreasureboxDropItemListIndexOrb1 = r20_0,
  TreasureboxDropItemListIndexRewind = r21_0,
}
