-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("sqlite3")
local r1_0 = require("crypto")
local r2_0 = require("db.doc.MsgData")
local function r5_0(r0_3, r1_3)
  -- line: [37, 46] id: 3
  if r1_3 == nil then
    r1_3 = tostring(os.time())
  end
  local r2_3 = r1_3
  for r6_3, r7_3 in pairs(r0_3) do
    r2_3 = r2_3 .. tostring(r7_3)
  end
  return r1_0.hmac(r1_0.sha512, r2_3 .. "feel-.-dead-.-beef", "防衛魔女の宴"), r1_3
end
return {
  begin_transcation = function(r0_1)
    -- line: [6, 19] id: 1
    -- notice: unreachable block#1
    -- notice: unreachable block#2
    assert(r0_1, debug.traceback())
    r0_1:exec("BEGIN TRANSACTION")
  end,
  commit = function(r0_2)
    -- line: [21, 34] id: 2
    -- notice: unreachable block#1
    -- notice: unreachable block#2
    assert(r0_2, debug.traceback())
    r0_2:exec("COMMIT TRANSACTION")
  end,
  calc_checksum = r5_0,
  calc_checksum2 = function(r0_4, r1_4)
    -- line: [48, 57] id: 4
    if r1_4 == nil then
      r1_4 = tostring(os.time())
    end
    local r2_4 = r1_4
    for r6_4, r7_4 in pairs(r0_4) do
      r2_4 = r2_4 .. tostring(r7_4)
    end
    return r1_0.digest(r1_0.sha512, r2_4 .. "防衛魔法少女"), r1_4
  end,
  check_checksum = function(r0_5, r1_5, r2_5)
    -- line: [59, 74] id: 5
    local r3_5 = r0_5:prepare("SELECT hd, date FROM hexdump" .. " WHERE name=? AND flag=0 LIMIT 1")
    r3_5:bind_values(r1_5)
    local r4_5 = nil
    for r8_5 in r3_5:nrows() do
      r4_5 = {}
      r4_5.hd = r8_5.hd
      r4_5.date = r8_5.date
    end
    if r4_5 == nil then
      return false
    end
    local r5_5, r6_5 = r5_0(r2_5, r4_5.date)
    return r5_5 == r4_5.hd
  end,
  checksum_error = function(r0_6)
    -- line: [76, 80] id: 6
    native.showAlert("DefenseWitches", string.format(r2_0.GetMessage(38), r0_6), {
      "OK"
    })
  end,
  write_checksum = function(r0_7, r1_7, r2_7)
    -- line: [82, 90] id: 7
    local r3_7, r4_7 = r5_0(r2_7)
    local r5_7 = r0_7:prepare("REPLACE INTO hexdump" .. " (name, hd, date)" .. " VALUES (?, ?, ?)")
    r5_7:bind_values(r1_7, r3_7, r4_7)
    r5_7:step()
    r5_7:finalize()
  end,
  is_numeric = function(r0_8)
    -- line: [92, 110] id: 8
    local r1_8 = string.byte("0")
    local r2_8 = string.byte("9")
    local r3_8 = string.byte(".")
    local r4_8 = string.len(r0_8)
    if r4_8 < 1 then
      return false
    end
    local r5_8 = false
    local r6_8 = nil
    for r10_8 = 1, r4_8, 1 do
      r6_8 = string.byte(r0_8, r10_8)
      if r6_8 == r3_8 then
        if r5_8 then
          return false
        end
        r5_8 = true
      elseif r6_8 < r1_8 or r2_8 < r6_8 then
        return false
      end
    end
    return true
  end,
  contains = function(r0_9, r1_9)
    -- line: [113, 120] id: 9
    for r5_9, r6_9 in pairs(r0_9) do
      if tostring(r6_9) == tostring(r1_9) then
        return true
      end
    end
    return false
  end,
  GetMessage = r2_0.GetMessage,
  GetTalkMessage = r2_0.GetTalkMessage,
  GetBingoMessage = r2_0.GetBingoMessage,
}
