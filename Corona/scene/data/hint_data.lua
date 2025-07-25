-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r1_0 = "hintrd.dat"
local r2_0 = {}
local r3_0 = {
  1,
  1,
  1,
  1,
  1,
  1,
  1,
  1,
  1,
  1,
  1,
  1,
  1,
  1,
  1,
  1,
  1,
  1,
  1,
  1,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0
}
local r4_0 = _G.MaxMap
local r5_0 = _G.MaxStage
local function r6_0()
  -- line: [27, 36] id: 1
  local r1_1 = io.open(system.pathForFile(r1_0, system.DocumentsDirectory), "w")
  if r1_1 then
    for r5_1, r6_1 in ipairs(r2_0) do
      r1_1:write(r6_1 .. "\n")
    end
    io.close(r1_1)
  end
end
local function r7_0()
  -- line: [39, 49] id: 2
  local r0_2 = 1
  for r4_2 = 1, r4_0, 1 do
    for r8_2 = 1, r5_0, 1 do
      r2_0[r0_2] = 0
      r0_2 = r0_2 + 1
    end
  end
  r6_0()
end
local function r8_0()
  -- line: [51, 67] id: 3
  local r1_3 = io.open(system.pathForFile(r1_0, system.DocumentsDirectory), "r")
  if r1_3 then
    local r2_3 = 1
    for r6_3 = 1, r4_0, 1 do
      for r10_3 = 1, r5_0, 1 do
        r2_0[r2_3] = tonumber(r1_3:read())
        r2_3 = r2_3 + 1
      end
    end
    io.close(r1_3)
  else
    r7_0()
  end
end
local function r9_0(r0_4, r1_4)
  -- line: [69, 71] id: 4
  return r0_4 * r4_0 - r4_0 + r1_4
end
local function r10_0(r0_5, r1_5)
  -- line: [73, 77] id: 5
  r2_0[r9_0(r0_5, r1_5)] = 1
  r6_0()
end
return {
  CheckHintRelease = function(r0_6, r1_6)
    -- line: [83, 92] id: 6
    local r4_6 = 0 < tonumber(r3_0[r9_0(r0_6, r1_6)])
  end,
  CheckHintReaded = function(r0_7, r1_7)
    -- line: [95, 104] id: 7
    local r4_7 = 0 < r2_0[r9_0(r0_7, r1_7)]
  end,
  SetHintReaded = function(r0_8, r1_8)
    -- line: [106, 108] id: 8
    r10_0(r0_8, r1_8)
  end,
  Init = function()
    -- line: [111, 113] id: 9
    r8_0()
  end,
}
