-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("json")
local r1_0 = 0
local function r2_0(r0_1)
  -- line: [23, 32] id: 1
  if r1_0 == 0 then
    local r1_1 = db.LoadEvoData()
    r1_0 = r1_1.exp
    if r0_1 ~= nil then
      r0_1(r1_1.exp)
    end
  end
end
local function r3_0(r0_2)
  -- line: [37, 40] id: 2
  r2_0()
  r1_0 = r1_0 + r0_2
end
local function r4_0(r0_3)
  -- line: [45, 47] id: 3
  r1_0 = r0_3
end
local function r5_0(r0_4)
  -- line: [52, 59] id: 4
  db.SaveEvoData(nil, nil, r1_0)
  if r0_4 ~= nil then
    r0_4()
  end
end
local function r6_0(r0_5)
  -- line: [64, 80] id: 5
  server.SaveExp(r1_0, function(r0_6)
    -- line: [65, 79] id: 6
    local r1_6 = r0_0.decode(r0_6.response)
    if r1_6.status == 0 then
      native.showAlert("DefenseWitches", db.GetMessage(35), {
        "OK"
      })
      r1_6.result = nil
    end
    if r0_5 ~= nil then
      r0_5(r1_6.result)
    end
  end)
end
local function r7_0()
  -- line: [88, 90] id: 7
  r2_0()
end
return {
  Init = function()
    -- line: [100, 102] id: 8
    r7_0()
  end,
  AddExp = function(r0_9)
    -- line: [107, 109] id: 9
    r3_0(r0_9)
  end,
  SetExp = function(r0_10)
    -- line: [114, 116] id: 10
    r4_0(r0_10)
  end,
  GetExp = function()
    -- line: [121, 124] id: 11
    r2_0()
    return r1_0
  end,
  SaveExp = function(r0_12)
    -- line: [129, 132] id: 12
    r5_0(r0_12)
  end,
  LoadExp = function(r0_13)
    -- line: [137, 139] id: 13
    r2_0(r0_13)
  end,
}
