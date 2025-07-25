-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = {
  UNREAD = function()
    -- line: [9, 9] id: 1
    return 0
  end,
  ALREADY_READ = function()
    -- line: [10, 10] id: 2
    return 1
  end,
}
local r1_0 = {
  BINGO_CARD_01 = function()
    -- line: [15, 15] id: 3
    return 1
  end,
}
return (function(r0_4)
  -- line: [20, 103] id: 4
  local r1_4 = {}
  local function r2_4(r0_5)
    -- line: [34, 36] id: 5
    return util.IsContainedTable(r1_0, r0_5)
  end
  local function r3_4(r0_6)
    -- line: [40, 42] id: 6
    util.IsContainedTable(r0_0, r0_6)
  end
  local function r4_4(r0_7)
    -- line: [46, 57] id: 7
    if r2_4(r0_7) == false then
      return nil
    end
    local r1_7 = db.GetAlreadyReadData(r0_7)
    if r1_7 ~= nil then
      return r1_7.state
    else
      return r0_0.UNREAD()
    end
  end
  local function r5_4(r0_8, r1_8)
    -- line: [61, 68] id: 8
    if r2_4(r0_8) == false or r3_4(r1_8) == false then
      return 
    end
    db.SetAlreadyReadData(r0_8, r1_8)
  end
  local function r6_4(r0_9)
    -- line: [72, 73] id: 9
  end
  function r1_4.getState(r0_11)
    -- line: [87, 89] id: 11
    return r4_4(r0_11)
  end
  function r1_4.setState(r0_12, r1_12)
    -- line: [93, 95] id: 12
    r5_4(r0_12, r1_12)
  end
  (function()
    -- line: [77, 80] id: 10
    r1_4.EVENT_ID = r1_0
    r1_4.STATE = r0_0
  end)()
  return r1_4
end)()
