-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
module(..., package.seeall)
local r0_0 = nil
function Header(r0_1)
  -- line: [9, 16] id: 1
  if r0_0 == nil then
    return 
  end
  assert(r0_0)
  local r1_1 = os.date("*t")
  Write(r0_1 .. ":" .. string.format("%04d/%02d/%02d %02d:%02d:%02d", r1_1.year, r1_1.month, r1_1.day, r1_1.hour, r1_1.min, r1_1.sec))
end
function Init()
  -- line: [18, 28] id: 2
  local r0_2 = system.pathForFile("log.txt", system.TemporaryDirectory)
  r0_0 = io.open(r0_2, "a+")
  if r0_0 == nil then
    DebugPrint("cannot open log file")
    return r0_2
  end
  r0_0:setvbuf("no")
  Header("----- ")
  return r0_2
end
function Write(r0_3, ...)
  -- line: [30, 35] id: 3
  if r0_0 == nil then
    return 
  end
  r0_0:write(string.format(r0_3, ...) .. "\n")
  io.flush()
end
function Write2(r0_4)
  -- line: [37, 41] id: 4
  if r0_0 == nil then
    return 
  end
  r0_0:write(r0_4 .. "\n")
  io.flush()
end
function PrintR(r0_5, r1_5, r2_5)
  -- line: [43, 65] id: 5
  if type(r0_5) ~= "table" then
    Write2(r0_5)
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
      Write2(r1_5 .. "[" .. tostring(r7_5) .. "] => Table {")
      Write2(r3_5 .. "{")
      PrintR(r8_5, r3_5 .. string.rep(" ", 2), r2_5)
      Write2(r3_5 .. "}")
    else
      Write2(r1_5 .. "[" .. tostring(r7_5) .. "] => " .. tostring(r8_5) .. "")
    end
  end
end
local r1_0 = {}
function DisplayLog(r0_6)
  -- line: [68, 86] id: 6
  for r6_6, r7_6 in pairs(r1_0) do
    local r1_6 = r7_6.y - 24
    if r1_6 <= 0 then
      table.remove(r1_0, r6_6)
      display.remove(r7_6)
    else
      r7_6.y = r1_6
    end
  end
  local r2_6 = display.newText(r0_6, 1, 1, native.systemFont, 24)
  r2_6:setFillColor(0, 0, 0)
  r2_6.x = r2_6.width / 2
  r2_6.y = _G.Height - 24
  table.insert(r1_0, r2_6)
end
