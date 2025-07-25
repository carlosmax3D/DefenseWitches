-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = _G.events or require("tool.events")
local function r1_0(r0_1, r1_1, r2_1)
  -- line: [8, 66] id: 1
  local r3_1 = r1_1.delay
  if r3_1 > 0 then
    r1_1.delay = r3_1 - r2_1
    return true
  end
  local r5_1 = r1_1.ed
  local r6_1 = r1_1.time
  local r7_1 = r1_1.loop
  local r4_1 = r1_1.st + r2_1
  if r5_1 < r4_1 then
    if r7_1 then
      r4_1 = r4_1 - r5_1 * math.floor(r4_1 / r5_1)
    else
      r4_1 = r5_1
    end
  end
  local r8_1 = r1_1.sprite
  local r9_1 = r1_1.func
  if r9_1 then
    r9_1(r8_1, r4_1, r5_1)
  elseif r4_1 < r5_1 then
    local r10_1 = r1_1.transition
    local r11_1 = nil
    local r12_1 = nil
    local r13_1 = nil
    local r14_1 = nil
    local r15_1 = nil
    local r16_1 = nil
    for r20_1, r21_1 in pairs(r1_1.value) do
      r12_1 = r21_1[1]
      r13_1 = r21_1[2]
      r14_1 = r21_1[4]
      r15_1 = r21_1[5]
      r16_1 = r21_1[6]
      r11_1 = r10_1(r4_1, r6_1, r12_1, r13_1)
      if r14_1 and r11_1 < r14_1 then
        r11_1 = r14_1
      end
      if r15_1 and r15_1 < r11_1 then
        r11_1 = r15_1
      end
      if r16_1 and r16_1 < r11_1 then
        r11_1 = r11_1 % r16_1
      end
      r8_1[r20_1] = r11_1
    end
  else
    for r13_1, r14_1 in pairs(r1_1.value) do
      r8_1[r13_1] = r14_1[3]
    end
  end
  if not r8_1.isVisible then
    r8_1.isVisible = true
  end
  r1_1.st = r4_1
  if r5_1 <= r4_1 and not r7_1 then
    if r1_1.finish then
      r1_1.finish(r1_1)
    end
    r1_1.ev = nil
    return false
  end
  return true
end
return {
  Register = function(r0_2, r1_2)
    -- line: [70, 120] id: 2
    assert(r0_2 and r1_2, debug.traceback())
    local r2_2 = {}
    local r3_2 = nil
    local r4_2 = nil
    local r5_2 = nil
    local r6_2 = nil
    r2_2.sprite = r0_2
    r2_2.ed = nil
    r2_2.st = 0
    r2_2.loop = false
    r2_2.delay = 0
    r2_2.value = {}
    r2_2.transition = nil
    for r10_2, r11_2 in pairs(r1_2) do
      if r10_2 == "time" then
        r2_2.ed = r11_2
      elseif r10_2 == "transition" then
        r2_2.transition = r11_2
      elseif r10_2 == "onComplete" then
        r2_2.finish = r11_2
      elseif r10_2 == "loop" then
        r2_2.loop = true
      elseif r10_2 == "delay" then
        r2_2.delay = r11_2
      elseif r10_2 == "func" then
        r2_2.func = r11_2
      else
        r3_2 = r0_2[r10_2]
        assert(r3_2, debug.traceback())
        r4_2 = nil
        r5_2 = nil
        r6_2 = nil
        if r10_2 == "xScale" or r10_2 == "yScale" then
          r4_2 = 0.001
        elseif r10_2 == "alpha" then
          r4_2 = 0
          r5_2 = 1
        elseif r10_2 == "rotation" then
          r6_2 = 360
        end
        r2_2.value[r10_2] = {
          r3_2,
          r11_2 - r3_2,
          r11_2,
          r4_2,
          r5_2,
          r6_2
        }
      end
    end
    if r2_2.transition == nil then
      r2_2.transition = easing.linear
    end
    r2_2.time = r2_2.ed - r2_2.st
    r2_2.ev = r0_0.Register(r1_0, r2_2, 1)
    return r2_2
  end,
  Delete = function(r0_3)
    -- line: [122, 127] id: 3
    assert(r0_3, debug.traceback())
    if r0_3.ev == nil then
      return 
    end
    r0_0.Delete(r0_3.ev)
    r0_3.ev = nil
  end,
  Cancel = function(r0_4)
    -- line: [129, 136] id: 4
    assert(r0_4, debug.traceback())
    if r0_4.ev == nil then
      return 
    end
    r0_4.delay = 0
    r0_4.ed = r0_4.st
  end,
  Pause = function(r0_5, r1_5)
    -- line: [138, 143] id: 5
    assert(r0_5, debug.traceback())
    if r0_5.ev == nil then
      return 
    end
    return r0_0.Disable(r0_5.ev, r1_5)
  end,
}
