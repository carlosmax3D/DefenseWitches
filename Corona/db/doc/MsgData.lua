-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("sqlite3")
local r1_0 = nil
local function r2_0()
  -- line: [6, 11] id: 1
  if r1_0 then
    return 
  end
  assert(r1_0 == nil, debug.traceback())
  r1_0 = r0_0.open(system.pathForFile("data/msg.sqlite"))
end
return {
  Init = r2_0,
  Cleanup = function()
    -- line: [13, 18] id: 2
    if r1_0 then
      r1_0:close()
      r1_0 = nil
    end
  end,
  GetMessage = function(r0_3)
    -- line: [21, 34] id: 3
    r2_0()
    assert(r1_0, debug.traceback())
    local r1_3 = r1_0:prepare("SELECT msg FROM msg" .. " WHERE msgid=? AND lang=? AND flag=0 LIMIT 1")
    r1_3:bind_values(r0_3, _G.UILanguage)
    local r2_3 = nil
    for r6_3 in r1_3:nrows() do
      r2_3 = r6_3.msg
    end
    r1_3:finalize()
    assert(r2_3, "msgid not found:" .. r0_3 .. "\n" .. debug.traceback())
    return r2_3
  end,
  GetTalkMessage = function(r0_4)
    -- line: [37, 55] id: 4
    r2_0()
    assert(r1_0, debug.traceback())
    local r1_4 = r1_0:prepare("SELECT msg FROM talk" .. " WHERE msgid=? AND lang=? AND flag=0 LIMIT 1")
    r1_4:bind_values(r0_4, _G.UILanguage)
    local r2_4 = nil
    for r6_4 in r1_4:nrows() do
      r2_4 = r6_4.msg
    end
    r1_4:finalize()
    if r2_4 == nil then
      DebugPrint("msgid not found:" .. r0_4 .. "\n" .. debug.traceback())
    end
    return r2_4
  end,
  GetBingoMessage = function(r0_5)
    -- line: [58, 75] id: 5
    r2_0()
    assert(r1_0, debug.traceback())
    local r1_5 = r1_0:prepare("SELECT msg FROM bingo" .. " WHERE msgid=? AND lang=? AND flag=0 LIMIT 1")
    r1_5:bind_values(r0_5, _G.UILanguage)
    local r2_5 = nil
    for r6_5 in r1_5:nrows() do
      r2_5 = r6_5.msg
    end
    r1_5:finalize()
    if r2_5 == nil then
      DebugPrint("msgid not found:" .. r0_5 .. "\n" .. debug.traceback())
    end
    return r2_5
  end,
}
