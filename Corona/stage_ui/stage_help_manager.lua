-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("json")
local r1_0 = require("tool.trial")
local r2_0 = "help.dat"
local r3_0 = "firstGameRun"
local r4_0 = "firstRankUpRun"
local r5_0 = "firstV400Run"
local r6_0 = "firstTreasureboxResultRun"
local r7_0 = nil
local function r8_0(r0_1, r1_1)
  -- line: [28, 46] id: 1
  if r0_1 == nil or r1_1 == nil then
    return false
  end
  local r3_1 = io.open(system.pathForFile(r1_1, system.CachesDirectory), "w")
  if r3_1 == nil then
    return false
  end
  r3_1:write(r0_0.encode(r0_1))
  io.close(r3_1)
  return true
end
local function r9_0(r0_2)
  -- line: [51, 62] id: 2
  local r1_2 = {
    [r3_0] = 0,
    [r4_0] = 0,
    [r5_0] = 0,
    [r6_0] = 0,
  }
  r8_0(r1_2, r0_2)
  return r1_2
end
local function r10_0(r0_3)
  -- line: [67, 80] id: 3
  local r2_3 = io.open(system.pathForFile(r0_3, system.CachesDirectory), "r")
  local r3_3 = nil
  if r2_3 ~= nil then
    r3_3 = r0_0.decode(r2_3:read())
    io.close(r2_3)
  else
    r3_3 = r9_0(r0_3)
  end
  return r3_3
end
local function r11_0()
  -- line: [85, 96] id: 4
  local r0_4 = nil	-- notice: implicit variable refs by block#[0]
  r7_0 = r0_4
  r7_0 = r10_0(r2_0)
  r0_4 = r1_0
  if r0_4 ~= nil then
    r0_4 = r1_0.IsFirstTrial()
    if r0_4 == false then
      r0_4 = r7_0
      r0_4[r3_0] = 1
    end
  end
end
return {
  Init = function()
    -- line: [104, 106] id: 5
    r11_0()
  end,
  IsFirstRun = function()
    -- line: [111, 122] id: 6
    if r7_0 == nil then
      r11_0()
      return true
    end
    local r0_6 = r7_0[r3_0] == 0
  end,
  IsFirstRankUpRun = function()
    -- line: [127, 138] id: 7
    if r7_0 == nil then
      r11_0()
      return true
    end
    local r0_7 = r7_0[r4_0] == 0
  end,
  IsFirstV400Run = function()
    -- line: [143, 154] id: 8
    if r7_0 == nil then
      r11_0()
      return true
    end
    local r0_8 = r7_0[r5_0] == 0
  end,
  IsFirstTreasureboxResultRun = function()
    -- line: [159, 170] id: 9
    if r7_0 == nil then
      r11_0()
      return true
    end
    local r0_9 = r7_0[r6_0] == 0
  end,
  SetFirstRun = function()
    -- line: [175, 182] id: 10
    if r7_0 == nil then
      return 
    end
    r7_0[r3_0] = 1
    r8_0(r7_0, r2_0)
  end,
  SetFirstRankUpRun = function()
    -- line: [187, 194] id: 11
    if r7_0 == nil then
      return 
    end
    r7_0[r4_0] = 1
    r8_0(r7_0, r2_0)
  end,
  SetFirstV400Run = function()
    -- line: [199, 206] id: 12
    if r7_0 == nil then
      return 
    end
    r7_0[r5_0] = 1
    r8_0(r7_0, r2_0)
  end,
  SetTreasureboxResultRun = function()
    -- line: [211, 218] id: 13
    if r7_0 == nil then
      return 
    end
    r7_0[r6_0] = 1
    r8_0(r7_0, r2_0)
  end,
}
