-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
module(..., package.seeall)
PerformanceOutput = {}
PerformanceOutput.mt = {}
PerformanceOutput.mt.__index = PerformanceOutput
local r0_0 = 0
local r1_0 = 0
local r2_0 = 99.9
local r3_0 = 0
local function r4_0(r0_1)
  -- line: [23, 31] id: 1
  local r1_1 = display.newGroup()
  display.newRect(r1_1, 0, 0, _G.Width, 24):setFillColor(0, 0, 0, 192)
  r0_1.msg = display.newText(r1_1, "0", 0, 0, native.systemFont, 24)
  r0_1.msg:setFillColor(255, 255, 255)
  return r1_1
end
local function r5_0(r0_2)
  -- line: [33, 72] id: 2
  local r1_2 = 0
  return function(r0_3)
    -- line: [35, 71] id: 3
    local r1_3 = system.getTimer()
    if r0_0 == 0 then
      r0_0 = r1_3
    end
    local r2_3 = r1_3 - r0_0
    r1_0 = r1_0 + 1
    if r2_3 < 1000 then
      return 
    end
    r3_0 = r3_0 + r2_3
    local r3_3 = 1000 * r1_0 / r2_3
    local r4_3 = r2_3 / r1_0
    if 2000 < r3_0 and r3_3 < r2_0 then
      r2_0 = r3_3
    end
    r0_0 = r1_3
    r1_0 = 0
    collectgarbage()
    local r5_3 = collectgarbage("count")
    if r1_2 < r5_3 then
      r1_2 = r5_3
    end
    r0_2.msg.text = string.format("%.1ffps %.1fms/f tex%dK %012.5f min%.1ffps", r3_3, r4_3, system.getInfo("textureMemoryUsed") / 1024, r1_2, r2_0)
    if r0_2.msg.width then
      r0_2.msg.x = r0_2.msg.width / 2
    end
  end
end
local r6_0 = nil
function PerformanceOutput.new()
  -- line: [76, 87] id: 4
  if r6_0 ~= nil then
    return r6_0
  end
  local r0_4 = {}
  setmetatable(r0_4, PerformanceOutput.mt)
  r0_4.group = r4_0(r0_4)
  Runtime:addEventListener("enterFrame", r5_0(r0_4))
  r6_0 = r0_4
  return r0_4
end
