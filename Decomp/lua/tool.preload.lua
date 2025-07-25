-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = {}
local r1_0 = nil
function r0_0.Init(r0_1)
  -- line: [10, 13] id: 1
  r1_0 = display.newGroup()
  r0_1:insert(r1_0)
end
function r0_0.Cleanup()
  -- line: [15, 21] id: 2
  if r1_0 then
    display.remove(r1_0)
  end
  r1_0 = nil
end
function r0_0.Load(r0_3, r1_3)
  -- line: [23, 32] id: 3
  for r7_3, r8_3 in pairs(r0_3) do
    local r3_3 = r1_3 .. "/" .. r8_3 .. ".png"
    local r2_3 = display.newImage(r3_3)
    assert(r2_3, string.format("%s is not found", r3_3))
    r2_3.isVisible = false
    r1_0:insert(r2_3)
  end
end
return r0_0
