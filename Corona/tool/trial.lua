-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r1_0 = nil	-- notice: implicit variable refs by block#[0]
local r0_0 = nil	-- notice: implicit variable refs by block#[0]
local r2_0 = "trial.dat"
local r3_0 = "trialclst.dat"
local r4_0 = "trialpw.dat"
local r5_0 = "trialgo.dat"
local r6_0 = "trialtmp.dat"
local r7_0 = "trialdis.dat"
local r8_0 = {
  "trialitm1.dat",
  "trialitm2.dat",
  "trialitm3.dat",
  "trialitm4.dat"
}
local r9_0 = nil
local r10_0 = nil
local r11_0 = nil
local r12_0 = nil
local r13_0 = nil
local r14_0 = nil
local r15_0 = nil
local r16_0 = nil
local r17_0 = nil
local r18_0 = nil
local r19_0 = nil
local r20_0 = false
local r21_0 = nil
local r22_0 = _G.MaxMap
local r23_0 = _G.MaxStage
local r24_0 = 4
local r25_0 = 2
local r26_0 = 4
local r27_0 = {
  {
    38,
    "item03",
    "txt_magic_",
    {
      265,
      188,
      189
    }
  },
  {
    18,
    "item12",
    "txt_atk40_",
    {
      266,
      19,
      20
    }
  },
  {
    22,
    "item16",
    "txt_range20_",
    {
      267,
      23,
      24
    }
  },
  {
    24,
    "item18",
    "txt_mp350_",
    {
      268,
      25
    }
  }
}
local r28_0 = {
  2,
  1,
  1,
  1
}
local function r29_0(r0_1)
  -- line: [61, 63] id: 1
  return "data/option/" .. r0_1 .. ".png"
end
local function r30_0(r0_2)
  -- line: [65, 67] id: 2
  return r29_0(r0_2 .. _G.UILanguage)
end
local function r31_0(r0_3)
  -- line: [69, 71] id: 3
  return "data/powerup/" .. r0_3 .. ".png"
end
local function r32_0(r0_4)
  -- line: [73, 75] id: 4
  return r31_0(r0_4 .. _G.UILanguage)
end
local function r33_0(r0_5)
  -- line: [77, 79] id: 5
  return "data/crystal/" .. r0_5 .. ".png"
end
local function r34_0(r0_6)
  -- line: [81, 83] id: 6
  return r33_0(r0_6 .. _G.UILanguage)
end
local function r35_0(r0_7)
  -- line: [85, 87] id: 7
  return "data/trial/" .. r0_7 .. ".png"
end
local function r36_0(r0_8)
  -- line: [89, 91] id: 8
  return r35_0(r0_8 .. _G.UILanguage)
end
local function r37_0(r0_9)
  -- line: [94, 104] id: 9
  local r2_9 = io.open(system.pathForFile(r0_9, system.DocumentsDirectory), "r")
  if r2_9 ~= nil then
    io.close(r2_9)
    return true
  end
  return false
end
local function r39_0(r0_11, r1_11)
  -- line: [113, 115] id: 11
  return r0_11 * r22_0 - r22_0 + r1_11
end
local function r40_0(r0_12, r1_12)
  -- line: [118, 131] id: 12
  local r3_12 = io.open(system.pathForFile(r1_12, system.DocumentsDirectory), "w")
  if r3_12 then
    for r7_12, r8_12 in ipairs(r0_12) do
      r3_12:write(r8_12 .. "\n")
    end
    io.close(r3_12)
  end
end
local function r41_0()
  -- line: [133, 155] id: 13
  for r3_13, r4_13 in ipairs(r11_0) do
    if r3_13 == 1 and r4_13 < 2 then
      return r3_13, r4_13
    elseif r3_13 == 2 and r4_13 < 1 then
      return r3_13, r4_13
    elseif r3_13 == 3 and r4_13 < 1 then
      return r3_13, r4_13
    elseif r3_13 == 4 and r4_13 < 1 then
      return r3_13, r4_13
    end
  end
  return 0, 0
end
local function r42_0(r0_14)
  -- line: [157, 169] id: 14
  if r0_14 == 38 then
    return 1
  elseif r0_14 == 18 then
    return 2
  elseif r0_14 == 22 then
    return 3
  elseif r0_14 == 24 then
    return 4
  end
  return 0
end
local function r44_0(r0_16, r1_16, r2_16)
  -- line: [177, 192] id: 16
  local r3_16 = r39_0(r0_16, r1_16)
  if r2_16 then
    r12_0[r3_16] = r12_0[r3_16] + 1
    r40_0(r12_0, r5_0)
  end
  if r25_0 <= r12_0[r3_16] and r10_0[r3_16] == 0 then
    return true
  end
  return false
end
local function r45_0(r0_17, r1_17)
  -- line: [195, 209] id: 17
  local r2_17 = r39_0(r0_17, r1_17)
  r9_0[r2_17] = r9_0[r2_17] + 1
  r40_0(r9_0, r2_0)
  if r24_0 <= r9_0[r2_17] and r10_0[r2_17] == 0 then
    return true
  end
  return false
end
local function r46_0()
  -- line: [211, 225] id: 18
  local r0_18 = system.pathForFile(r6_0, system.DocumentsDirectory)
  local r1_18 = {}
  local r2_18 = nil
  local r3_18 = io.open(r0_18, "r")
  if r3_18 then
    r18_0 = tonumber(r3_18:read())
  else
    r18_0 = 0
  end
  return r18_0
end
local function r52_0(r0_24, r1_24)
  -- line: [324, 335] id: 24
  local r2_24 = 1
  for r6_24 = 1, r22_0, 1 do
    for r10_24 = 1, r23_0, 1 do
      r0_24[r2_24] = 0
      r2_24 = r2_24 + 1
    end
  end
  r40_0(r0_24, r1_24)
end
local function r57_0(r0_29, r1_29)
  -- line: [412, 427] id: 29
  local r3_29 = io.open(system.pathForFile(r1_29, system.DocumentsDirectory), "r")
  if r3_29 then
    for r7_29 = 1, r26_0, 1 do
      r0_29[r7_29] = tonumber(r3_29:read())
    end
    io.close(r3_29)
  else
    for r7_29 = 1, r26_0, 1 do
      r0_29[r7_29] = 0
    end
    r40_0(r0_29, r1_29)
  end
end
local function r58_0(r0_30, r1_30, r2_30, r3_30, r4_30)
  -- line: [429, 450] id: 30
  local r5_30 = display.newGroup()
  local r6_30 = nil
  local r7_30 = nil
  local r8_30 = 0
  for r12_30, r13_30 in pairs(r3_30) do
    r6_30 = string.format(db.GetMessage(r13_30), r4_30)
    display.newText(r5_30, r6_30, 1, r8_30 + 1, native.systemFontBold, 24):setFillColor(0, 0, 0)
    r7_30 = display.newText(r5_30, r6_30, 0, r8_30, native.systemFontBold, 24)
    r7_30:setFillColor(255, 255, 255)
    r8_30 = r8_30 + 36
  end
  r0_30:insert(r5_30)
  r5_30:setReferencePoint(display.TopLeftReferencePoint)
  r5_30.x = r1_30
  r5_30.y = r2_30
  r5_30.isVisible = true
  return r5_30
end
local function r63_0(r0_36, r1_36)
  -- line: [525, 542] id: 36
  local r3_36 = io.open(system.pathForFile(r1_36, system.DocumentsDirectory), "r")
  if r3_36 then
    local r4_36 = 1
    for r8_36 = 1, r22_0, 1 do
      for r12_36 = 1, r23_0, 1 do
        r0_36[r4_36] = tonumber(r3_36:read())
        r4_36 = r4_36 + 1
      end
    end
    io.close(r3_36)
  else
    r52_0(r0_36, r1_36)
  end
end
return {
  SetTrialType = function(r0_10)
    -- line: [107, 109] id: 10
    r17_0 = r0_10
  end,
  GetGameOverCount = function(r0_15, r1_15)
    -- line: [171, 174] id: 15
    return r12_0[r39_0(r0_15, r1_15)]
  end,
  CountUpGameOver = r44_0,
  CountUpTrial = r45_0,
  CountUp = function(r0_19, r1_19)
    -- line: [227, 244] id: 19
    local r2_19 = r46_0()
    local r3_19 = false
    if r17_0 == 0 then
      r3_19 = r44_0(r0_19, r1_19, false)
    else
      r3_19 = r45_0(r0_19, r1_19)
    end
    if r2_19 > 0 then
      return false
    else
      return r3_19
    end
  end,
  GetItemCount = function(r0_20, r1_20)
    -- line: [247, 270] id: 20
    local r2_20 = r39_0(r0_20, r1_20)
    local r3_20, r4_20 = r41_0()
    local r5_20 = r3_20
    local r6_20 = {}
    if r5_20 == 1 then
      r6_20 = r13_0
    elseif r5_20 == 2 then
      r6_20 = r14_0
    elseif r5_20 == 3 then
      r6_20 = r15_0
    elseif r5_20 == 4 then
      r6_20 = r16_0
    else
      r6_20 = nil
    end
    if r6_20 ~= nil then
      return r6_20[r2_20]
    end
    return 0
  end,
  CountUpItem = function(r0_21, r1_21, r2_21)
    -- line: [273, 301] id: 21
    local r3_21 = r39_0(r0_21, r1_21)
    for r7_21, r8_21 in pairs(r2_21) do
      local r9_21 = r42_0(r8_21)
      local r10_21 = {}
      if r9_21 == 1 then
        r10_21 = r13_0
      elseif r9_21 == 2 then
        r10_21 = r14_0
      elseif r9_21 == 3 then
        r10_21 = r15_0
      elseif r9_21 == 4 then
        r10_21 = r16_0
      else
        r10_21 = nil
      end
      if r10_21 ~= nil then
        r10_21[r3_21] = r10_21[r3_21] + 1
        r40_0(r10_21, r8_0[r9_21])
      end
    end
  end,
  CheckClearStage = function(r0_22, r1_22)
    -- line: [305, 313] id: 22
    local r3_22 = 1 <= r10_0[r39_0(r0_22, r1_22)]
  end,
  CountUpClearStage = function(r0_23, r1_23)
    -- line: [316, 321] id: 23
    local r2_23 = r39_0(r0_23, r1_23)
    r10_0[r2_23] = r10_0[r2_23] + 1
    r40_0(r10_0, r3_0)
  end,
  GetTrialDataIndex = r39_0,
  InitTrialData = r52_0,
  InitNowTrial = function()
    -- line: [337, 345] id: 25
    local r0_25 = system.pathForFile(r6_0, system.DocumentsDirectory)
    r40_0({
      [1] = 0,
    }, r6_0)
  end,
  ClearTrialMP = function(r0_26)
    -- line: [347, 354] id: 26
    local r1_26 = {}
    if r0_26 == 24 then
      r1_26[1] = 0
      r40_0(r1_26, r6_0)
    end
  end,
  GetNowTrial = r46_0,
  GetPowerUpItem = r41_0,
  GetItemIndex = r42_0,
  CheckTrialMP = function()
    -- line: [359, 370] id: 27
    for r3_27, r4_27 in ipairs(r11_0) do
      if r3_27 == 4 and 1 <= r4_27 then
        return true
      end
    end
    return false
  end,
  StartTrial = function()
    -- line: [373, 409] id: 28
    local r0_28 = {}
    local r1_28 = 0
    for r5_28, r6_28 in ipairs(r11_0) do
      if r5_28 == 1 and r6_28 < 2 then
        r1_28 = 38
        r11_0[r5_28] = r6_28 + 1
        break
      elseif r5_28 == 2 and r6_28 < 1 then
        r1_28 = 18
        r11_0[r5_28] = r6_28 + 1
        break
      elseif r5_28 == 3 and r6_28 < 1 then
        r1_28 = 22
        r11_0[r5_28] = r6_28 + 1
        break
      elseif r5_28 == 4 and r6_28 < 1 then
        r1_28 = 24
        r11_0[r5_28] = r6_28 + 1
        break
      end
    end
    r40_0(r11_0, r4_0)
    r0_28[1] = r1_28
    r40_0(r0_28, r6_0)
  end,
  LoadPowerUpData = r57_0,
  Close = function(r0_31)
    -- line: [452, 455] id: 31
    display.remove(r1_0)
    r1_0 = nil
  end,
  Open = function(r0_32)
    -- line: [457, 512] id: 32
    local r1_32 = _G.Width
    r0_0 = _G.GameData
    local r2_32 = display.newGroup()
    local r3_32 = display.newGroup()
    local r4_32 = display.newGroup()
    local r5_32 = display.newGroup()
    r2_32:insert(r3_32)
    r2_32:insert(r4_32)
    r2_32:insert(r5_32)
    for r9_32 = 0, 960, 16 do
      util.LoadParts(r3_32, r35_0("mighty_window01"), r9_32, 20)
      util.LoadParts(r3_32, r35_0("mighty_window02"), r9_32, 148)
      util.LoadParts(r3_32, r35_0("mighty_window03"), r9_32, 276)
      util.LoadParts(r3_32, r35_0("mighty_window04"), r9_32, 404)
      util.LoadParts(r3_32, r35_0("mighty_window05"), r9_32, 532)
    end
    local r6_32, r7_32 = r41_0()
    if r6_32 == 0 then
      return 
    end
    local r9_32 = r27_0[r6_32][3]
    local r10_32 = r27_0[r6_32][4]
    util.LoadParts(r3_32, r32_0(r27_0[r6_32][2]), 42, 206)
    util.LoadParts(r3_32, r36_0(r9_32), 240, 158)
    local r11_32 = r0_32.func
    util.LoadBtn({
      rtImg = r4_32,
      fname = r29_0("close"),
      x = 872,
      y = 68,
      func = r11_32.close,
    })
    util.LoadBtn({
      rtImg = r4_32,
      fname = r36_0("try_"),
      x = 368,
      y = 468,
      func = r11_32.try,
    })
    r21_0 = r58_0(r2_32, 246, 266, r10_32, r28_0[r6_32] - r7_32)
    function r2_32.touch(r0_33, r1_33)
      -- line: [504, 504] id: 33
      return true
    end
    r2_32:addEventListener("touch")
    if r0_32.rtImg then
      r0_32.rtImg:insert(r2_32)
    end
    r1_0 = r2_32
  end,
  CheckTrialDisable = function()
    -- line: [514, 516] id: 34
    return r19_0
  end,
  TrialDisableInit = function()
    -- line: [518, 522] id: 35
    r40_0({
      [1] = 0,
    }, r7_0)
  end,
  SaveTrialData = r40_0,
  LoadTrialData = r63_0,
  IsFirstTrial = function()
    -- line: [545, 547] id: 37
    return r20_0
  end,
  Init = function()
    -- line: [549, 585] id: 38
    local r0_38 = r37_0(r2_0) ~= true
    r17_0 = 0
    r9_0 = {}
    r10_0 = {}
    r11_0 = {}
    r12_0 = {}
    r63_0(r9_0, r2_0)
    r63_0(r10_0, r3_0)
    r57_0(r11_0, r4_0)
    r63_0(r12_0, r5_0)
    r13_0 = {}
    r14_0 = {}
    r15_0 = {}
    r16_0 = {}
    r63_0(r13_0, r8_0[1])
    r63_0(r14_0, r8_0[2])
    r63_0(r15_0, r8_0[3])
    r63_0(r16_0, r8_0[4])
    local r1_38 = io.open(system.pathForFile(r7_0, system.DocumentsDirectory), "r")
    if r1_38 then
      io.close(r1_38)
      r19_0 = true
    else
      r19_0 = false
    end
  end,
}
