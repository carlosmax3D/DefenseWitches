-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = nil	-- notice: implicit variable refs by block#[0]
local r1_0 = {
  Write = function(r0_1, ...)
    -- line: [10, 15] id: 1
    if r0_0 == nil then
      return 
    end
    r0_0:write(string.format(r0_1, ...) .. "\n")
    io.flush()
  end,
  Write2 = function(r0_2)
    -- line: [17, 21] id: 2
    if r0_0 == nil then
      return 
    end
    r0_0:write(r0_2 .. "\n")
    io.flush()
  end,
  Header = function(r0_3)
    -- line: [23, 30] id: 3
    if r0_0 == nil then
      return 
    end
    assert(r0_0)
    local r1_3 = os.date("*t")
    r1_0.Write(r0_3 .. ":" .. string.format("%04d/%02d/%02d %02d:%02d:%02d", r1_3.year, r1_3.month, r1_3.day, r1_3.hour, r1_3.min, r1_3.sec))
  end,
  Init = function()
    -- line: [32, 42] id: 4
    local r0_4 = system.pathForFile("log.txt", system.TemporaryDirectory)
    r0_0 = io.open(r0_4, "a+")
    if r0_0 == nil then
      DebugPrint("cannot open log file")
      return r0_4
    end
    r0_0:setvbuf("no")
    r1_0.Header("----- ")
    return r0_4
  end,
  PrintR = function(r0_5, r1_5, r2_5)
    -- line: [45, 67] id: 5
    if type(r0_5) ~= "table" then
      r1_0.Write2(r0_5)
      return 
    end
    if not r2_5 then
      r2_5 = {}
    end
    if not r1_5 then
      r1_5 = ""
    end
    local r3_5 = nil
    for r7_5, r8_5 in pairs(r0_5) do
      if type(r8_5) == "table" and not r2_5[r8_5] then
        if not r3_5 then
          r3_5 = r1_5 .. string.rep(" ", string.len(tostring(r7_5)) + 2)
        end
        r2_5[r8_5] = true
        r1_0.Write2(r1_5 .. "[" .. tostring(r7_5) .. "] => Table {")
        r1_0.Write2(r3_5 .. "{")
        r1_0.PrintR(r8_5, r3_5 .. string.rep(" ", 2), r2_5)
        r1_0.Write2(r3_5 .. "}")
      else
        r1_0.Write2(r1_5 .. "[" .. tostring(r7_5) .. "] => " .. tostring(r8_5) .. "")
      end
    end
  end,
}
local r2_0 = {}
function r1_0.DisplayLog(r0_6)
  -- line: [71, 89] id: 6
  for r6_6, r7_6 in pairs(r2_0) do
    local r1_6 = r7_6.y - 24
    if r1_6 <= 0 then
      table.remove(r2_0, r6_6)
      display.remove(r7_6)
    else
      r7_6.y = r1_6
    end
  end
  local r2_6 = display.newText(r0_6, 1, 1, native.systemFont, 24)
  r2_6:setFillColor(0, 0, 0)
  r2_6.x = r2_6.width / 2
  r2_6.y = _G.Height - 24
  table.insert(r2_0, r2_6)
end
return r1_0
