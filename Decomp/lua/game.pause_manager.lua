-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = 0
local r1_0 = nil
local r2_0 = nil
local function r3_0(r0_1, r1_1)
  -- line: [25, 37] id: 1
  if r0_1 == nil or r1_1 == nil or r1_0 == nil or r1_0[r0_1] ~= nil or type(r1_1) ~= "function" then
    return false
  end
  r1_0[r0_1] = r1_1
  return true
end
local function r4_0()
  -- line: [42, 55] id: 2
  if r1_0 == nil then
    return nil
  end
  local r0_2 = {}
  local r1_2 = nil
  local r2_2 = nil
  for r6_2, r7_2 in pairs(r1_0) do
    table.insert(r0_2, r6_2)
  end
  return r0_2
end
local function r5_0(r0_3)
  -- line: [60, 68] id: 3
  if r0_3 == nil or r1_0 == nil or r1_0[r0_3] == nil then
    return nil
  end
  return r1_0[r0_3]
end
local function r6_0(r0_4)
  -- line: [73, 82] id: 4
  if r0_4 == nil or r1_0[r0_4] == nil then
    return false
  end
  r1_0[r0_4] = nil
  return true
end
local function r7_0(r0_5)
  -- line: [87, 105] id: 5
  if r0_5 == nil then
    return 
  end
  local r1_5 = r4_0()
  if r1_5 ~= nil then
    local r2_5 = nil
    local r3_5 = nil
    for r7_5, r8_5 in pairs(r1_5) do
      local r9_5 = r5_0(r8_5)
      if r9_5 ~= nil and r0_5[r8_5] ~= nil then
        r9_5(r0_5[r8_5])
      end
    end
  end
end
local function r8_0()
  -- line: [110, 119] id: 6
  if r1_0 ~= nil then
    local r0_6 = r4_0()
    local r1_6 = nil
    for r5_6 = 1, #r0_6, 1 do
      r6_0(r0_6[r5_6])
    end
    r1_0 = nil
  end
end
local function r9_0()
  -- line: [124, 129] id: 7
  r8_0()
  r1_0 = {}
  r2_0 = r0_0
end
return {
  PauseTypeNone = r0_0,
  Init = function()
    -- line: [137, 139] id: 8
    r9_0()
  end,
  Clean = function()
    -- line: [144, 146] id: 9
    r8_0()
  end,
  SetFunction = function(r0_10, r1_10)
    -- line: [151, 153] id: 10
    return r3_0(r0_10, r1_10)
  end,
  Play = function(r0_11)
    -- line: [158, 161] id: 11
    r2_0 = r0_0
    r7_0(r0_11)
  end,
  Pause = function(r0_12, r1_12)
    -- line: [166, 169] id: 12
    r2_0 = r0_12
    r7_0(r1_12)
  end,
  GetPauseType = function()
    -- line: [174, 176] id: 13
    return r2_0
  end,
}
