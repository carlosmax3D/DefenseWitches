-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
_G.module(..., _G.package.seeall)
_G.print("-----------------------------------------------")
_G.showDebug = true
local r0_0 = _G.display.contentWidth - _G.display.screenOriginX
local r1_0 = _G.display.contentHeight - _G.display.screenOriginY
_G.directorView = _G.display.newGroup()
local r2_0 = _G.display.newGroup()
local r3_0 = _G.display.newGroup()
local r4_0 = _G.display.newGroup()
local r5_0 = _G.display.newGroup()
local r6_0 = _G.display.newGroup()
local r7_0 = _G.display.newGroup()
local function r8_0()
  -- line: [82, 89] id: 1
  _G.directorView:insert(r2_0)
  _G.directorView:insert(r3_0)
  _G.directorView:insert(r4_0)
  _G.directorView:insert(r5_0)
  _G.directorView:insert(r6_0)
  _G.directorView:insert(r7_0)
end
local r9_0 = nil
local r10_0 = nil
local r11_0 = nil
local r12_0 = nil
local r13_0 = "main"
local r14_0 = "main"
local r15_0 = "main"
local r16_0 = "main"
local r17_0 = nil
local r18_0 = 200
local r19_0 = 50
local r20_0 = false
local r21_0 = nil
local r22_0 = false
local r23_0 = {}
local r24_0 = 1
local r25_0 = nil
r3_0.x = -r0_0
r3_0.y = 0
r2_0.x = 0
r2_0.y = 0
r4_0.x = r0_0
r4_0.y = 0
r6_0.x = 0
r6_0.y = 0
local function r26_0(r0_2, r1_2, r2_2)
  -- line: [120, 182] id: 2
  if _G.type(r0_2) == "nil" then
    r0_2 = "black"
  end
  local r3_2 = nil	-- notice: implicit variable refs by block#[19]
  local r4_2 = nil	-- notice: implicit variable refs by block#[19]
  local r5_2 = nil	-- notice: implicit variable refs by block#[19]
  if _G.string.lower(_G.tostring(r0_2)) == "red" then
    r3_2 = 255
    r4_2 = 0
    r5_2 = 0
  elseif _G.string.lower(_G.tostring(r0_2)) == "green" then
    r3_2 = 0
    r4_2 = 255
    r5_2 = 0
  elseif _G.string.lower(_G.tostring(r0_2)) == "blue" then
    r3_2 = 0
    r4_2 = 0
    r5_2 = 255
  elseif _G.string.lower(_G.tostring(r0_2)) == "yellow" then
    r3_2 = 255
    r4_2 = 255
    r5_2 = 0
  elseif _G.string.lower(_G.tostring(r0_2)) == "pink" then
    r3_2 = 255
    r4_2 = 0
    r5_2 = 255
  elseif _G.string.lower(_G.tostring(r0_2)) == "white" then
    r3_2 = 255
    r4_2 = 255
    r5_2 = 255
  elseif _G.type(r0_2) == "number" and _G.type(r1_2) == "number" and _G.type(r2_2) == "number" then
    r3_2 = r0_2
    r4_2 = r1_2
    r5_2 = r2_2
  else
    r3_2 = 0
    r4_2 = 0
    r5_2 = 0
  end
  return r3_2, r4_2, r5_2
end
local function r27_0(r0_3, r1_3)
  -- line: [188, 205] id: 3
  local r2_3 = "Director ERROR: " .. _G.tostring(r0_3)
  local function r3_3(r0_4)
    -- line: [190, 198] id: 4
    _G.print()
    _G.print("-----------------------")
    _G.print(r2_3)
    _G.print("-----------------------")
    _G.print(r1_3)
    _G.print("-----------------------")
    _G.error()
  end
  if _G.showDebug then
    local r4_3 = _G.native.showAlert("Director Class - ERROR", r2_3, {
      "OK"
    }, r3_3)
  else
    r3_3()
  end
end
local function r28_0(r0_5)
  -- line: [211, 213] id: 5
  _G.collectgarbage("collect")
end
local r29_0 = _G.getmetatable(_G.display.getCurrentStage())
local function r30_0(r0_6)
  -- line: [221, 223] id: 6
  local r1_6 = _G.type(r0_6)
  if r1_6 == "table" then
    r1_6 = _G.getmetatable(r0_6) == r29_0
  else
    r1_6 = r1_6 == "table"	-- block#2 is visited secondly
  end
  return r1_6
end
local r31_0 = _G.display.newRect(-r0_0, -r1_0, r0_0 * 3, r1_0 * 3)
r31_0:setFillColor(255, 255, 255)
r31_0.alpha = 0.01
r31_0.isVisible = false
r5_0:insert(r31_0)
local function r32_0(r0_7)
  -- line: [243, 245] id: 7
  return true
end
r31_0:addEventListener("touch", r32_0)
r31_0:addEventListener("tap", r32_0)
function _G.director.changeFxTime(r0_8, r1_8)
  -- line: [257, 261] id: 8
  if _G.type(r1_8) == "number" then
    r18_0 = r1_8
  end
end
function _G.director.changeSafeDelay(r0_9, r1_9)
  -- line: [267, 271] id: 9
  if _G.type(r1_9) == "number" then
    r19_0 = r1_9
  end
end
function _G.director.ChangingScene(r0_10)
  -- line: [273, 273] id: 10
  return r20_0
end
function _G.director.getPrevScene(r0_11)
  -- line: [283, 285] id: 11
  return r13_0
end
function _G.director.getCurrScene(r0_12)
  -- line: [291, 293] id: 12
  return r14_0
end
function _G.director.getNextScene(r0_13)
  -- line: [299, 301] id: 13
  return r15_0
end
local function r33_0(r0_14)
  -- line: [307, 469] id: 14
  if r0_14 == "prev" then
    r3_0:removeSelf()
    if r9_0 and r9_0.clean then
      local r1_14, r2_14 = _G.pcall(r9_0.clean)
      if not r1_14 then
        r27_0("Failed to clean object \'" .. r13_0 .. "\' - Please verify the localGroup.clean() function.", r2_14)
        return false
      end
    end
    r3_0 = _G.display.newGroup()
  elseif r0_14 == "curr" then
    r2_0:removeSelf()
    if r10_0 and r10_0.clean then
      local r1_14, r2_14 = _G.pcall(r10_0.clean)
      if not r1_14 then
        r27_0("Failed to clean object \'" .. r14_0 .. "\' - Please verify the localGroup.clean() function.", r2_14)
        return false
      end
    end
    r2_0 = _G.display.newGroup()
  elseif r0_14 == "next" then
    r4_0:removeSelf()
    if r11_0 and r11_0.clean then
      local r1_14, r2_14 = _G.pcall(r11_0.clean)
      if not r1_14 then
        r27_0("Failed to clean object \'" .. r15_0 .. "\' - Please verify the localGroup.clean() function.", r2_14)
        return false
      end
    end
    r4_0 = _G.display.newGroup()
  elseif r0_14 == "popup" then
    r6_0:removeSelf()
    if r12_0 and r12_0.clean then
      local r1_14, r2_14 = _G.pcall(r12_0.clean)
      if not r1_14 then
        r27_0("Failed to clean object \'" .. r16_0 .. "\' - Please verify the localGroup.clean() function.", r2_14)
        return false
      end
    end
    r6_0 = _G.display.newGroup()
  end
  r8_0()
end
local function r34_0(r0_15)
  -- line: [475, 582] id: 15
  if r0_15 == "prev" and r9_0 and r9_0.initVars then
    local r1_15, r2_15 = _G.pcall(r9_0.initVars)
    if not r1_15 then
      r27_0("Failed to initiate variables of object \'" .. r13_0 .. "\' - Please verify the localGroup.initVars() function.", r2_15)
      return false
    end
  elseif r0_15 == "curr" and r10_0 and r10_0.initVars then
    local r1_15, r2_15 = _G.pcall(r10_0.initVars)
    if not r1_15 then
      r27_0("Failed to initiate variables of object \'" .. r14_0 .. "\' - Please verify the localGroup.initVars() function.", r2_15)
      return false
    end
  elseif r0_15 == "next" and r11_0 and r11_0.initVars then
    local r1_15, r2_15 = _G.pcall(r11_0.initVars)
    if not r1_15 then
      r27_0("Failed to initiate variables of object \'" .. r15_0 .. "\' - Please verify the localGroup.initVars() function.", r2_15)
      return false
    end
  elseif r0_15 == "popup" and r12_0 and r12_0.initVars then
    local r1_15, r2_15 = _G.pcall(r12_0.initVars)
    if not r1_15 then
      r27_0("Failed to initiate variables of object \'" .. r16_0 .. "\' - Please verify the localGroup.initVars() function.", r2_15)
      return false
    end
  end
end
local function r35_0(r0_16)
  -- line: [588, 637] id: 16
  if r0_16 ~= "main" and _G.type(_G.package.loaded[r0_16]) ~= "nil" then
    if _G.package.loaded[r0_16].clean then
      local r1_16, r2_16 = _G.pcall(_G.package.loaded[r0_16].clean)
      if not r1_16 then
        r27_0("Failed to clean module \'" .. r0_16 .. "\' - Please verify the clean() function.", r2_16)
        return false
      end
    end
    _G.package.loaded[r0_16] = nil
    _G.timer.performWithDelay(r18_0, function(r0_17)
      -- line: [627, 629] id: 17
      r28_0()
    end)
  end
end
local function r36_0(r0_18, r1_18, r2_18)
  -- line: [643, 872] id: 18
  if _G.type(r0_18) ~= "string" then
    r27_0("Module name must be a string. moduleName = " .. _G.tostring(r0_18))
    return false
  end
  if not _G.package.loaded[r0_18] then
    local r3_18, r4_18 = _G.pcall(_G.require, r0_18)
    if not r3_18 then
      r27_0("Failed to load module \'" .. r0_18 .. "\' - Please check if the file exists and it is correct.", r4_18)
      return false
    end
  end
  if not _G.package.loaded[r0_18].new then
    r27_0("Module \'" .. _G.tostring(r0_18) .. "\' must have a new() function.")
    return false
  end
  local r3_18 = _G.package.loaded[r0_18].new
  local r4_18 = nil
  if _G.string.lower(r1_18) == "prev" then
    r33_0("prev")
    if r13_0 ~= r14_0 and r13_0 ~= r15_0 then
      r35_0(r0_18)
    end
    r4_18, r9_0 = _G.pcall(r3_18, r2_18)
    if not r4_18 then
      r27_0("Failed to execute new( params ) function on \'" .. _G.tostring(r0_18) .. "\'.", r9_0)
      return false
    end
    if not r30_0(r10_0) then
      r27_0("Module " .. r0_18 .. " must return a display.newGroup().")
      return false
    end
    local r5_18 = _G.display.newRect(0, 0, r0_0, r1_0)
    r5_18.alpha = 0.01
    r5_18:addEventListener("touch", r25_0)
    r3_0:insert(r5_18)
    r3_0:insert(r9_0)
    r13_0 = r0_18
    r34_0("prev")
  elseif _G.string.lower(r1_18) == "curr" then
    r33_0("curr")
    if r13_0 ~= r14_0 and r14_0 ~= r15_0 then
      r35_0(r0_18)
    end
    r4_18, r10_0 = _G.pcall(r3_18, r2_18)
    if not r4_18 then
      r27_0("Failed to execute new( params ) function on \'" .. _G.tostring(r0_18) .. "\'.", r10_0)
      return false
    end
    if not r30_0(r10_0) then
      r27_0("Module " .. r0_18 .. " must return a display.newGroup().")
      return false
    end
    local r5_18 = _G.display.newRect(0, 0, r0_0, r1_0)
    r5_18.alpha = 0.01
    r5_18:addEventListener("touch", r25_0)
    r2_0:insert(r5_18)
    r2_0:insert(r10_0)
    r14_0 = r0_18
    r34_0("curr")
  else
    r33_0("next")
    if r13_0 ~= r15_0 and r14_0 ~= r15_0 then
      r35_0(r0_18)
    end
    r4_18, r11_0 = _G.pcall(r3_18, r2_18)
    if not r4_18 then
      r27_0("Failed to execute new( params ) function on \'" .. _G.tostring(r0_18) .. "\'.", r11_0)
      return false
    end
    if not r30_0(r11_0) then
      r27_0("Module " .. r0_18 .. " must return a display.newGroup().")
      return false
    end
    local r5_18 = _G.display.newRect(0, 0, r0_0, r1_0)
    r5_18.alpha = 0.01
    r5_18:addEventListener("touch", r25_0)
    r4_0:insert(r5_18)
    r4_0:insert(r11_0)
    r15_0 = r0_18
    r34_0("next")
  end
  return true
end
local function r37_0(r0_19, r1_19)
  -- line: [878, 881] id: 19
  r36_0(r0_19, "prev", r1_19)
  r3_0.x = -r0_0
end
local function r38_0(r0_20, r1_20)
  -- line: [887, 890] id: 20
  r36_0(r0_20, "curr", r1_20)
  r2_0.x = 0
end
local function r39_0(r0_21, r1_21)
  -- line: [896, 899] id: 21
  r36_0(r0_21, "next", r1_21)
  r4_0.x = r0_0
end
local function r40_0(r0_22)
  -- line: [905, 962] id: 22
  r2_0.x = 0
  r2_0.y = 0
  r2_0.xScale = 1
  r2_0.yScale = 1
  r33_0("curr")
  if r14_0 ~= r15_0 then
    r35_0(r14_0)
  end
  r10_0 = r11_0
  r14_0 = r17_0
  r2_0:insert(r10_0)
  r4_0.x = r0_0
  r4_0.y = 0
  r4_0.xScale = 1
  r4_0.yScale = 1
  r15_0 = "main"
  r11_0 = nil
  r20_0 = false
  r31_0.isVisible = false
  return true
end
function _G.director.changeScene(r0_23, r1_23, r2_23, r3_23, r4_23, r5_23, r6_23)
  -- line: [968, 1314] id: 23
  _G.DebugPrint("■ changeScene param ■")
  _G.DebugPrint(r1_23)
  _G.DebugPrint("■ changeScene param ■")
  if r20_0 then
    return true
  else
    r20_0 = true
  end
  if r22_0 then
    return true
  end
  if _G.type(r1_23) ~= "table" then
    r6_23 = r5_23
    r5_23 = r4_23
    r4_23 = r3_23
    r3_23 = r2_23
    r2_23 = r1_23
    r1_23 = nil
  end
  if _G.type(r2_23) ~= "string" then
    r27_0("The scene name must be a string. scene = " .. _G.tostring(r2_23))
    return false
  end
  if r16_0 ~= "main" then
    r27_0("Could not change scene inside a popup.")
    return false
  end
  local r7_23 = _G.display.getCurrentStage()
  for r11_23 = r7_23.numChildren, 1, -1 do
    if _G.type(r7_23[r11_23].directorId) == "nil" then
      r7_23[r11_23].directorId = r14_0
    end
    if r7_23[r11_23].directorId == r14_0 and r7_23[r11_23].directorId ~= "main" then
      r10_0:insert(r7_23[r11_23])
    end
  end
  if r2_23 == "main" then
    return true
  end
  r31_0.isVisible = true
  r17_0 = r2_23
  local r8_23 = nil
  r39_0(r17_0, r1_23)
  for r12_23 = r7_23.numChildren, 1, -1 do
    if _G.type(r7_23[r12_23].directorId) == "nil" then
      r7_23[r12_23].directorId = r17_0
    end
    if r7_23[r12_23].directorId == r17_0 and r7_23[r12_23].directorId ~= "main" then
      r11_0:insert(r7_23[r12_23])
    end
  end
  local r9_23 = nil	-- notice: implicit variable refs by block#[48, 50, 51, 52]
  if r3_23 == "moveFromRight" then
    r4_0.x = r0_0
    r4_0.y = 0
    r8_23 = _G.transition.to(r4_0, {
      x = 0,
      time = r18_0,
    })
    local r11_23 = {}
    r11_23.x = -r0_0
    r11_23.time = r18_0
    r11_23.onComplete = r40_0
    r9_23 = _G.transition.to(r2_0, r11_23)
    r8_23 = r9_23
  elseif r3_23 == "overFromRight" then
    r4_0.x = r0_0
    r4_0.y = 0
    local r11_23 = {}
    r11_23.x = 0
    r11_23.time = r18_0
    r11_23.onComplete = r40_0
    r9_23 = _G.transition.to(r4_0, r11_23)
    r8_23 = r9_23
  elseif r3_23 == "moveFromLeft" then
    r4_0.x = -r0_0
    r4_0.y = 0
    r8_23 = _G.transition.to(r4_0, {
      x = 0,
      time = r18_0,
    })
    local r11_23 = {}
    r11_23.x = r0_0
    r11_23.time = r18_0
    r11_23.onComplete = r40_0
    r9_23 = _G.transition.to(r2_0, r11_23)
    r8_23 = r9_23
  elseif r3_23 == "overFromLeft" then
    r4_0.x = -r0_0
    r4_0.y = 0
    local r11_23 = {}
    r11_23.x = 0
    r11_23.time = r18_0
    r11_23.onComplete = r40_0
    r9_23 = _G.transition.to(r4_0, r11_23)
    r8_23 = r9_23
  elseif r3_23 == "moveFromTop" then
    r4_0.x = 0
    r4_0.y = -r1_0
    r8_23 = _G.transition.to(r4_0, {
      y = 0,
      time = r18_0,
    })
    local r11_23 = {}
    r11_23.y = r1_0
    r11_23.time = r18_0
    r11_23.onComplete = r40_0
    r9_23 = _G.transition.to(r2_0, r11_23)
    r8_23 = r9_23
  elseif r3_23 == "overFromTop" then
    r4_0.x = 0
    r4_0.y = -r1_0
    local r11_23 = {}
    r11_23.y = 0
    r11_23.time = r18_0
    r11_23.onComplete = r40_0
    r9_23 = _G.transition.to(r4_0, r11_23)
    r8_23 = r9_23
  elseif r3_23 == "moveFromBottom" then
    r4_0.x = 0
    r4_0.y = r1_0
    r8_23 = _G.transition.to(r4_0, {
      y = 0,
      time = r18_0,
    })
    local r11_23 = {}
    r11_23.y = -r1_0
    r11_23.time = r18_0
    r11_23.onComplete = r40_0
    r9_23 = _G.transition.to(r2_0, r11_23)
    r8_23 = r9_23
  elseif r3_23 == "overFromBottom" then
    r4_0.x = 0
    r4_0.y = r1_0
    local r11_23 = {}
    r11_23.y = 0
    r11_23.time = r18_0
    r11_23.onComplete = r40_0
    r9_23 = _G.transition.to(r4_0, r11_23)
    r8_23 = r9_23
  elseif r3_23 == "crossfade" then
    r4_0.x = 0
    r4_0.y = 0
    r4_0.alpha = 0
    r8_23 = _G.transition.to(r2_0, {
      alpha = 0,
      time = r18_0 * 2,
    })
    local r11_23 = {}
    r11_23.alpha = 1
    r11_23.time = r18_0 * 2
    r11_23.onComplete = r40_0
    r9_23 = _G.transition.to(r4_0, r11_23)
    r8_23 = r9_23
  else
    local r10_23 = nil	-- notice: implicit variable refs by block#[50, 51]
    if r3_23 == "fade" then
      r4_0.x = r0_0
      r4_0.y = 0
      r9_23 = _G.display.newRect(-r0_0, -r1_0, r0_0 * 3, r1_0 * 3)
      r9_23.alpha = 0
      r9_23:setFillColor(r26_0(r4_23, r5_23, r6_23))
      r7_0:insert(r9_23)
      r10_23 = function (r0_24)
        -- line: [1229, 1239] id: 24
        r2_0.x = r0_0
        r4_0.x = 0
        r8_23 = _G.transition.to(r0_24, {
          alpha = 0,
          time = r18_0,
          onComplete = function(r0_25)
            -- line: [1233, 1236] id: 25
            r0_25:removeSelf()
            r40_0()
          end,
        })
      end
      local r11_23 = _G.transition.to(r9_23, {
        alpha = 1,
        time = r18_0,
        onComplete = r10_23,
      })
      r8_23 = r11_23
      -- close: r9_23
    elseif r3_23 == "flip" then
      r9_23 = r4_0
      r9_23.xScale = 0.001
      r9_23 = r4_0
      r9_23.x = r0_0 / 2
      r9_23 = nil
      r10_23 = nil
      r8_23 = _G.transition.to(r2_0, {
        xScale = 0.001,
        time = r18_0,
      })
      r8_23 = _G.transition.to(r2_0, {
        x = r0_0 / 2,
        time = r18_0,
      })
      r10_23 = function (r0_27)
        -- line: [1262, 1264] id: 27
        r8_23 = _G.transition.to(r0_27, {
          xScale = 1,
          x = 0,
          time = r18_0,
          onComplete = r40_0,
        })
      end
      r9_23 = function (r0_26)
        -- line: [1258, 1260] id: 26
        r8_23 = _G.transition.to(r0_26, {
          xScale = 0.001,
          x = r0_0 / 2,
          time = r18_0,
          onComplete = r10_23,
        })
      end
      local r11_23 = _G.transition.to(r4_0, {
        time = 0,
        onComplete = r9_23,
      })
      r8_23 = r11_23
      -- close: r9_23
    elseif r3_23 == "downFlip" then
      r9_23 = r4_0
      r10_23 = r0_0
      r10_23 = r10_23 / 2
      r9_23.x = r10_23
      r9_23 = r4_0
      r10_23 = r1_0
      r10_23 = r10_23 * 0.15
      r9_23.y = r10_23
      r9_23 = r4_0
      r9_23.xScale = 0.001
      r9_23 = r4_0
      r9_23.yScale = 0.7
      r9_23 = nil
      r10_23 = nil
      local r11_23 = nil
      local r12_23 = nil
      r12_23 = function (r0_31)
        -- line: [1294, 1296] id: 31
        r8_23 = _G.transition.to(r0_31, {
          xScale = 1,
          yScale = 1,
          x = 0,
          y = 0,
          time = r18_0,
          onComplete = r40_0,
        })
      end
      r11_23 = function (r0_30)
        -- line: [1290, 1292] id: 30
        r8_23 = _G.transition.to(r0_30, {
          x = r0_0 * 0.15,
          xScale = 0.7,
          time = r18_0,
          onComplete = r12_23,
        })
      end
      r10_23 = function (r0_29)
        -- line: [1286, 1288] id: 29
        r8_23 = _G.transition.to(r0_29, {
          xScale = 0.001,
          x = r0_0 / 2,
          time = r18_0,
          onComplete = r11_23,
        })
      end
      r9_23 = function (r0_28)
        -- line: [1282, 1284] id: 28
        r8_23 = _G.transition.to(r0_28, {
          xScale = 0.7,
          yScale = 0.7,
          x = r0_0 * 0.15,
          y = r1_0 * 0.15,
          time = r18_0,
          onComplete = r10_23,
        })
      end
      r8_23 = _G.transition.to(r2_0, {
        time = 0,
        onComplete = r9_23,
      })
      -- close: r9_23
    else
      r9_23 = _G.timer
      r9_23 = r9_23.performWithDelay
      r10_23 = r19_0
      local r11_23 = r40_0
      r9_23(r10_23, r11_23)
    end
  end
  r9_23 = true
  return r9_23
end
function _G.director.openPopUp(r0_32, r1_32, r2_32, r3_32)
  -- line: [1320, 1440] id: 32
  if _G.type(r1_32) ~= "table" then
    r3_32 = r2_32
    r2_32 = r1_32
    r1_32 = nil
  end
  if _G.type(r2_32) ~= "string" then
    r27_0("Module name must be a string. moduleName = " .. _G.tostring(r2_32))
    return false
  end
  if _G.type(r3_32) == "function" then
    r21_0 = r3_32
  else
    r21_0 = nil
  end
  if r2_32 == r14_0 or r2_32 == r15_0 or r2_32 == "main" then
    return false
  end
  if r16_0 ~= "main" then
    r27_0("Could not load more then 1 popup.")
    return false
  end
  r33_0("popup")
  r35_0(r2_32)
  r16_0 = "main"
  r12_0 = nil
  local r4_32, r5_32 = _G.pcall(_G.require, r2_32)
  if not r4_32 then
    r27_0("Failed to load module \'" .. r2_32 .. "\' - Please check if the file exists and it is correct.", r5_32)
    return false
  end
  if not _G.package.loaded[r2_32].new then
    r27_0("Module \'" .. _G.tostring(r2_32) .. "\' must have a new() function.")
    return false
  end
  r4_32, r12_0 = _G.pcall(_G.package.loaded[r2_32].new, r1_32)
  if not r4_32 then
    r27_0("Failed to execute news( params ) function on \'" .. _G.tostring(_G.moduleName) .. "\'.", r12_0)
    return false
  end
  if not r30_0(r10_0) then
    r27_0("Module " .. _G.moduleName .. " must return a display.newGroup().")
    return false
  end
  r6_0:insert(r12_0)
  r16_0 = r2_32
  r34_0("popup")
  r31_0.isVisible = true
  return true
end
function _G.director.closePopUp(r0_33)
  -- line: [1446, 1490] id: 33
  if r16_0 == "main" then
    return true
  end
  r33_0("popup")
  r35_0(_G.newPopUpScene)
  r16_0 = "main"
  r12_0 = nil
  r31_0.isVisible = false
  if _G.type(r21_0) == "function" then
    r21_0()
  end
  return true
end
function _G.director.isBook(r0_34)
  -- line: [1496, 1498] id: 34
  return r22_0
end
function _G.getCurrBookPage()
  -- line: [1504, 1508] id: 35
  if r23_0[r24_0] then
    return r23_0[r24_0]
  end
end
function _G.getCurrBookPageNum()
  -- line: [1514, 1516] id: 36
  return r24_0
end
function _G.getBookPageCount()
  -- line: [1522, 1524] id: 37
  return _G.table.maxn(r23_0)
end
function _G.director.newBookPages(r0_38, r1_38, r2_38)
  -- line: [1530, 1680] id: 38
  if not r22_0 then
    return true
  end
  if _G.type(r1_38) ~= "table" then
    return true
  end
  while 0 < _G.getBookPageCount() do
    _G.table.remove(r23_0)
  end
  local r3_38 = 1
  while r1_38[r3_38] do
    local r4_38 = _G.type(r1_38[r3_38])
    if r4_38 == "table" then
      r4_38 = r23_0
      r4_38[r3_38] = r1_38[r3_38]
      r3_38 = r3_38 + 1
    end
  end
  local r4_38 = _G.display.newRect(0, 0, r0_0, r1_0)
  r4_38:setFillColor(0, 0, 0)
  r4_38.alpha = 0
  r4_38.isVisible = false
  r7_0:insert(r4_38)
  local function r5_38(r0_39)
    -- line: [1578, 1587] id: 39
    r4_38:removeSelf()
    r4_38 = nil
  end
  local function r6_38(r0_40)
    -- line: [1593, 1667] id: 40
    r33_0("prev")
    if r13_0 ~= r23_0[1] and r13_0 ~= r23_0[2] then
      r35_0(r13_0)
    end
    r13_0 = "main"
    r33_0("next")
    if r15_0 ~= r23_0[1] and r15_0 ~= r23_0[2] then
      r35_0(r15_0)
    end
    r15_0 = "main"
    if r23_0[1] then
      r38_0(r23_0[1].page, r23_0[1].params)
      r24_0 = 1
    end
    if r23_0[2] then
      r39_0(r23_0[2].page, r23_0[2].params)
    end
    if r10_0 and r10_0.start then
      local r1_40, r2_40 = _G.pcall(r10_0.start)
      if not r1_40 then
        r27_0("Failed to start page of object \'" .. r14_0 .. "\' - Please verify the localGroup.start() function.", r2_40)
        return false
      end
    end
    local r1_40 = _G.transition.to(r4_38, {
      alpha = 0,
      time = r18_0,
      onComplete = r5_38,
    })
  end
  if r2_38 then
    r4_38.isVisible = true
    local r7_38 = _G.transition.to(r4_38, {
      alpha = 1,
      time = r18_0,
      onComplete = r6_38,
    })
  else
    r6_38()
  end
end
function _G.director.changeBookPage(r0_41, r1_41)
  -- line: [1686, 1938] id: 41
  if not r22_0 then
    return true
  end
  if r20_0 then
    return true
  else
    r20_0 = true
  end
  if _G.type(r1_41) == nil then
    return true
  end
  if _G.string.lower(r1_41) == "next" then
    if _G.getCurrBookPageNum() + 1 < _G.getBookPageCount() then
      r1_41 = _G.getCurrBookPageNum() + 1
    else
      r1_41 = _G.getBookPageCount()
    end
  elseif _G.string.lower(r1_41) == "prev" then
    if _G.getCurrBookPageNum() - 1 > 1 then
      r1_41 = _G.getCurrBookPageNum() - 1
    else
      r1_41 = 1
    end
  end
  if not _G.type(r1_41) == "number" then
    return true
  end
  local r2_41 = nil
  if r1_41 < 1 or _G.getBookPageCount() < r1_41 or r1_41 == _G.getCurrBookPageNum() then
    r2_41 = _G.transition.to(r3_0, {
      x = -r0_0,
      time = r18_0,
      transition = _G.easing.outQuad,
    })
    r2_41 = _G.transition.to(r2_0, {
      x = 0,
      time = r18_0,
      transition = _G.easing.outQuad,
    })
    r2_41 = _G.transition.to(r4_0, {
      x = r0_0,
      time = r18_0,
      transition = _G.easing.outQuad,
      onComplete = function(r0_42)
        -- line: [1742, 1744] id: 42
        r20_0 = false
      end,
    })
  elseif _G.getCurrBookPageNum() < r1_41 then
    r2_41 = _G.transition.to(r3_0, {
      x = -r0_0,
      time = r18_0,
      transition = _G.easing.outQuad,
    })
    r2_41 = _G.transition.to(r2_0, {
      x = -r0_0,
      time = r18_0,
      transition = _G.easing.outQuad,
    })
    r2_41 = _G.transition.to(r4_0, {
      x = 0,
      time = r18_0,
      transition = _G.easing.outQuad,
      onComplete = function(r0_43)
        -- line: [1759, 1839] id: 43
        r33_0("prev")
        if r13_0 ~= r14_0 and r13_0 ~= r15_0 then
          r35_0(r13_0)
        end
        r9_0 = r10_0
        r13_0 = r14_0
        r3_0:insert(r9_0)
        r3_0.x = -r0_0
        r34_0("prev")
        r10_0 = r11_0
        r14_0 = r15_0
        r2_0:insert(r10_0)
        r2_0.x = 0
        r11_0 = nil
        if r23_0[r1_41 + 1] then
          r39_0(r23_0[r1_41 + 1].page, r23_0[r1_41 + 1].params)
        end
        r4_0.x = r0_0
        r24_0 = r1_41
        r20_0 = false
        if r10_0 and r10_0.start then
          local r1_43, r2_43 = _G.pcall(r10_0.start)
          if not r1_43 then
            r27_0("Failed to start page of object \'" .. r14_0 .. "\' - Please verify the localGroup.start() function.", r2_43)
            return false
          end
        end
      end,
    })
  else
    r2_41 = _G.transition.to(r3_0, {
      x = 0,
      time = r18_0,
      transition = _G.easing.outQuad,
    })
    r2_41 = _G.transition.to(r2_0, {
      x = r0_0,
      time = r18_0,
      transition = _G.easing.outQuad,
    })
    r2_41 = _G.transition.to(r4_0, {
      x = r0_0,
      time = r18_0,
      transition = _G.easing.outQuad,
      onComplete = function(r0_44)
        -- line: [1848, 1928] id: 44
        r33_0("next")
        if r13_0 ~= r15_0 and r14_0 ~= r15_0 then
          r35_0(r15_0)
        end
        r11_0 = r10_0
        r15_0 = r14_0
        r4_0:insert(r11_0)
        r4_0.x = r0_0
        r34_0("next")
        r10_0 = r9_0
        r14_0 = r13_0
        r2_0:insert(r10_0)
        r2_0.x = 0
        r9_0 = nil
        if r23_0[r1_41 - 1] then
          r37_0(r23_0[r1_41 - 1].page, r23_0[r1_41 - 1].params)
        end
        r3_0.x = -r0_0
        r24_0 = r1_41
        r20_0 = false
        if r10_0 and r10_0.start then
          local r1_44, r2_44 = _G.pcall(r10_0.start)
          if not r1_44 then
            r27_0("Failed to start page of object \'" .. r14_0 .. "\' - Please verify the localGroup.start() function.", r2_44)
            return false
          end
        end
      end,
    })
  end
end
function r25_0(r0_45)
  -- line: [1944, 2000] id: 45
  if not r22_0 then
    return true
  end
  if r20_0 then
    return true
  end
  if r0_45.phase == "moved" then
    if r0_45.x < r0_45.xStart then
      r3_0.x = -r0_0 - r0_45.xStart - r0_45.x
      r2_0.x = 0 - r0_45.xStart - r0_45.x
      r4_0.x = r0_0 - r0_45.xStart - r0_45.x
    else
      r3_0.x = -r0_0 + r0_45.x - r0_45.xStart
      r2_0.x = 0 + r0_45.x - r0_45.xStart
      r4_0.x = r0_0 + r0_45.x - r0_45.xStart
    end
  elseif r0_45.phase == "ended" then
    local r1_45 = 0.2
    if r2_0.x < -r0_0 * r1_45 then
      _G.director:changeBookPage("next")
    elseif r0_0 * r1_45 < r2_0.x then
      _G.director:changeBookPage("prev")
    else
      _G.director:changeBookPage(_G.getCurrBookPageNum())
    end
  end
end
function _G.director.goToPage(r0_46, r1_46, r2_46, r3_46)
  -- line: [2006, 2177] id: 46
  if _G.type(r1_46) ~= "table" then
    r3_46 = r2_46
    r2_46 = r1_46
    r1_46 = nil
  end
  if _G.type(r2_46) ~= "number" then
    r27_0("The page name must be a number. page = " .. _G.tostring(r2_46))
    return false
  end
  if r2_46 < 1 then
    r27_0("Cannot change to page lower then 1. page = " .. _G.tostring(r2_46))
    return false
  end
  local r4_46 = _G.display.newRect(-r0_0, -r1_0, r0_0 * 3, r1_0 * 3)
  r4_46:setFillColor(0, 0, 0)
  r4_46.alpha = 0
  r4_46.isVisible = false
  r7_0:insert(r4_46)
  local function r5_46(r0_47)
    -- line: [2038, 2047] id: 47
    r4_46:removeSelf()
    r4_46 = nil
  end
  local function r6_46(r0_48)
    -- line: [2053, 2164] id: 48
    if r2_46 == 1 then
      r33_0("prev")
      if r13_0 ~= r14_0 and r13_0 ~= r15_0 then
        r35_0(r13_0)
      end
      if r23_0[1] then
        r38_0(r23_0[1].page, r1_46 or r23_0[1].params)
        r24_0 = 1
      end
      if r23_0[2] then
        r39_0(r23_0[2].page, r23_0[2].params)
      end
    elseif r2_46 == _G.getBookPageCount() then
      if r23_0[r2_46] then
        r38_0(r23_0[r2_46].page, r1_46 or r23_0[r2_46].params)
        r24_0 = r2_46
      end
      if r23_0[r2_46 - 1] then
        r37_0(r23_0[r2_46 - 1].page, r23_0[r2_46 - 1].params)
      end
      r33_0("next")
      if r13_0 ~= r15_0 and r14_0 ~= r15_0 then
        r35_0(r15_0)
      end
    else
      if r23_0[r2_46 - 1] then
        r37_0(r23_0[r2_46 - 1].page, r23_0[r2_46 - 1].params)
      end
      if r23_0[r2_46] then
        r38_0(r23_0[r2_46].page, r1_46 or r23_0[r2_46].params)
        r24_0 = r2_46
      end
      if r23_0[r2_46 + 1] then
        r39_0(r23_0[r2_46 + 1].page, r23_0[r2_46 + 1].params)
      end
    end
    if r10_0 and r10_0.start then
      local r1_48, r2_48 = _G.pcall(r10_0.start)
      if not r1_48 then
        r27_0("Failed to start page of object \'" .. r14_0 .. "\' - Please verify the localGroup.start() function.", r2_48)
        return false
      end
    end
    local r1_48 = _G.transition.to(r4_46, {
      alpha = 0,
      time = r18_0,
      onComplete = r5_46,
    })
  end
  if r3_46 then
    r4_46.isVisible = true
    local r7_46 = _G.transition.to(r4_46, {
      alpha = 1,
      time = r18_0,
      onComplete = r6_46,
    })
  else
    r6_46()
  end
end
function _G.director.turnToBook(r0_49)
  -- line: [2183, 2185] id: 49
  r22_0 = true
end
function _G.director.turnToScenes(r0_50)
  -- line: [2187, 2232] id: 50
  r22_0 = false
  r33_0("prev")
  if r13_0 ~= r14_0 and r13_0 ~= r15_0 then
    r35_0(r13_0)
  end
  r33_0("next")
  if r13_0 ~= r15_0 and r14_0 ~= r15_0 then
    r35_0(r15_0)
  end
  r9_0 = nil
  r11_0 = nil
  r13_0 = "main"
  r15_0 = "main"
end
