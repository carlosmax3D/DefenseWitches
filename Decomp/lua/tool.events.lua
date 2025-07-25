-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = {}
local r1_0 = {}
local r2_0 = {}
local r3_0 = _G.VsyncTime or 33.333333333333336
local r4_0 = 1
local r5_0 = nil
local r6_0 = 1
local r7_0 = nil
local function r8_0(r0_1)
  -- line: [15, 37] id: 1
  local r1_1 = true
  r0_1.time = r0_1.time + r3_0
  local r2_1 = r0_1.time
  local r3_1 = r0_1.interval
  local r4_1 = r0_1.fire
  if r3_1 < r2_1 then
    local r5_1 = nil
    if r3_1 > 0 then
      r5_1 = math.floor(r2_1 / r3_1)
    else
      r5_1 = math.round(r2_1 / 33.333333333333336)
    end
    if r0_1.debug then
      r0_1.debug(r0_1)
    end
    r1_1 = r4_1.func(r0_1, r4_1.param, r5_1)
    if r3_1 > 0 then
      r0_1.time = r2_1 - r3_1 * r5_1
    else
      r0_1.time = 0
    end
  end
  return r1_1
end
local function r9_0(r0_2)
  -- line: [39, 81] id: 2
  if r5_0 == nil then
    return 
  end
  local r1_2 = r0_0[r5_0]
  if r1_2 == nil then
    return 
  end
  for r5_2, r6_2 in pairs(r1_2) do
    if r6_2.cleanup.func then
      r6_2.cleanup.func(r6_2, r6_2.cleanup.param)
    end
    if r6_2.delete then
      table.remove(r1_2, r5_2)
    end
  end
  local r2_2 = nil
  local r3_2 = nil
  for r7_2 = 1, r4_0, 1 do
    r3_2 = r7_2 == r4_0
    for r11_2, r12_2 in pairs(r2_0) do
      r12_2()
    end
    for r11_2, r12_2 in pairs(r1_2) do
      if not r12_2.delete and not r12_2.disable and r12_2.fire.func then
        r12_2.ev = r0_2
        r12_2.uid = r11_2
        r12_2.update_frame = r3_2
        if r12_2.no_skip_frame then
          r2_2 = true
          if r3_2 then
            r2_2 = r8_0(r12_2)
          end
        else
          r2_2 = r8_0(r12_2)
        end
        if r2_2 == nil then
          r2_2 = true
        end
        if not r12_2.delete then
          r12_2.delete = not r2_2
        end
      end
    end
  end
  for r7_2, r8_2 in pairs(r1_0) do
    r8_2()
  end
end
local function r10_0(r0_3)
  -- line: [83, 88] id: 3
  r5_0 = r0_3
  r0_0[r5_0] = {}
  return r5_0
end
local function r11_0(r0_4)
  -- line: [90, 106] id: 4
  local r1_4 = r0_0[r0_4]
  if r1_4 then
    for r5_4, r6_4 in pairs(r1_4) do
      if r6_4.cleanup and r6_4.cleanup.func and not r6_4.delete then
        r6_4.cleanup.func(r6_4, r6_4.cleanup.param)
      end
    end
    r0_0[r0_4] = nil
  end
  if r0_4 == r5_0 then
    r5_0 = nil
  end
  r4_0 = 1
end
local function r12_0()
  -- line: [108, 118] id: 5
  Runtime:removeEventListener("enterFrame", r9_0)
  for r3_5, r4_5 in pairs(r0_0) do
    r11_0(r4_5)
  end
  r2_0 = {}
  r0_0 = {}
  r1_0 = {}
  r4_0 = 1
  r10_0("main")
end
local r13_0 = {
  Init = function()
    -- line: [122, 125] id: 6
    r12_0()
    Runtime:addEventListener("enterFrame", r9_0)
  end,
  Cleanup = function()
    -- line: [127, 129] id: 7
    r12_0()
  end,
  SetNamespace = function(r0_8)
    -- line: [131, 133] id: 8
    return r10_0(r0_8)
  end,
  CheckNamespace = function(r0_9)
    -- line: [135, 137] id: 9
    return r0_0[r0_9]
  end,
  DeleteNamespace = function(r0_10)
    -- line: [139, 141] id: 10
    r11_0(r0_10)
  end,
  Register = function(r0_11, r1_11, r2_11, r3_11)
    -- line: [143, 170] id: 11
    assert(r5_0, debug.traceback())
    if r3_11 == nil then
      r3_11 = false
    end
    local r4_11 = {
      guid = r6_0,
      cleanup = {
        func = nil,
        param = nil,
      },
      fire = {
        func = r0_11,
        param = r1_11,
      },
      time = 0,
      disable = r3_11,
      interval = r2_11,
      delete = false,
      ev = nil,
      update_frame = false,
      no_skip_frame = false,
    }
    r6_0 = r6_0 + 1
    table.insert(r0_0[r5_0], r4_11)
    return r4_11
  end,
  Delete = function(r0_12)
    -- line: [172, 177] id: 12
    assert(r0_12, debug.traceback())
    assert(r5_0, debug.traceback())
    r0_12.delete = true
  end,
  Disable = function(r0_13, r1_13)
    -- line: [179, 183] id: 13
    r0_13.disable = r1_13
    return r0_13.disable
  end,
  RegisterCleanup = function(r0_14, r1_14, r2_14)
    -- line: [186, 191] id: 14
    r0_14.cleanup.func = r1_14
    r0_14.cleanup.param = r2_14
    return r0_14.cleanup
  end,
  SetInterval = function(r0_15, r1_15)
    -- line: [193, 197] id: 15
    r0_15.interval = r1_15
    return r0_15.interval
  end,
  SetTimer = function(r0_16)
    -- line: [199, 201] id: 16
    r3_0 = r0_16
  end,
}
function r13_0.SetInterval(r0_17, r1_17)
  -- line: [203, 207] id: 17
  r0_17.interval = r1_17
  return r0_17.interval
end
function r13_0.GetInterval(r0_18)
  -- line: [210, 212] id: 18
  return r0_18.interval
end
function r13_0.RegisterPostProc(r0_19)
  -- line: [214, 217] id: 19
  assert(r0_19, debug.traceback())
  table.insert(r1_0, r0_19)
end
function r13_0.DeletePostProc(r0_20)
  -- line: [219, 222] id: 20
  local r1_20 = table.indexOf(r1_0, r0_20)
  if r1_20 then
    table.remove(r1_0, r1_20)
  end
end
function r13_0.RegisterFirstProc(r0_21)
  -- line: [224, 226] id: 21
  table.insert(r2_0, r0_21)
end
function r13_0.DeleteFirstProc(r0_22)
  -- line: [228, 231] id: 22
  local r1_22 = table.indexOf(r2_0, r0_22)
  if r1_22 then
    table.remove(r2_0, r1_22)
  end
end
function r13_0.SetRepeatTime(r0_23)
  -- line: [233, 239] id: 23
  local r1_23 = r4_0
  if tonumber(r0_23) > 0 then
    r4_0 = r0_23
  end
  return r1_23
end
function r13_0.GetRepeatTime()
  -- line: [241, 243] id: 24
  return r4_0
end
function r13_0.GetEventsNum()
  -- line: [245, 245] id: 25
  return #r0_0[r5_0]
end
function r13_0.ResumeInit()
  -- line: [247, 250] id: 26
  Runtime:removeEventListener("enterFrame", r9_0)
  r7_0 = {}
end
function r13_0.ResumeSet(r0_27, r1_27, r2_27)
  -- line: [252, 257] id: 27
  local r3_27 = r7_0[r0_27]
  if r3_27 == nil then
    r3_27 = {}
  end
  table.insert(r3_27, {
    func = r1_27,
    param = r2_27,
  })
  r7_0[r0_27] = r3_27
end
function r13_0.ResumeStart(r0_28)
  -- line: [259, 273] id: 28
  _G.NoUpdate = true
  local r1_28 = nil
  for r5_28 = 0, r0_28, 1 do
    if r5_28 == r0_28 then
      _G.NoUpdate = false
    end
    r9_0({
      time = r5_28,
    })
    r1_28 = r7_0[r5_28]
    if r1_28 then
      for r9_28, r10_28 in pairs(r1_28) do
        r10_28.func(r10_28.param, r5_28)
      end
    end
  end
  Runtime:addEventListener("enterFrame", r9_0)
end
function r13_0.SetSkipFrame(r0_29, r1_29)
  -- line: [275, 281] id: 29
  assert(r0_29, debug.traceback())
  if r1_29 == nil then
    r1_29 = true
  end
  r0_29.no_skip_frame = r1_29
  return r0_29.no_skip_frame
end
return r13_0
