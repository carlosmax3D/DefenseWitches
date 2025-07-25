-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = {}
local r1_0 = nil
local r2_0 = nil
local r3_0 = nil
local r4_0 = 0
local r5_0 = 0
local r6_0 = -1
local r7_0 = -1
local r8_0 = 0
local r9_0 = 0
local r10_0 = 0
local function r11_0()
  -- line: [23, 26] id: 1
  assert(r0_0, debug.traceback())
  return getmetatable(r0_0).__index
end
local function r12_0(r0_2)
  -- line: [31, 36] id: 2
  local r1_2 = r0_0.getScrollView()
  if r1_2 then
    r0_0.callMoveScrollListener(r1_2.x - r8_0, r1_2.y - r9_0)
  end
end
local function r13_0()
  -- line: [41, 43] id: 3
  Runtime:removeEventListener("enterFrame", r12_0)
end
local function r14_0(r0_4)
  -- line: [48, 54] id: 4
  local r2_4 = r11_0().getTransitionStash()
  if table.indexOf(r2_4, r0_4) then
    table.remove(r2_4, r0_4)
  end
end
local function r15_0(r0_5, r1_5)
  -- line: [59, 108] id: 5
  if r0_5 < 0 or #r3_0 < r0_5 then
    return 
  end
  local r2_5 = r11_0()
  local r3_5 = r0_0.getScrollView()
  local r4_5 = nil
  if r1_5 == nil then
    r1_5 = 600
  end
  if r0_0.isHorizonScroll() then
    r8_0 = r3_5.x
    r13_0()
    Runtime:addEventListener("enterFrame", r12_0)
    r4_5 = transition.to(r3_5, {
      x = -r0_5 * r6_0,
      time = r1_5,
      transition = easing.outExpo,
      onComplete = function()
        -- line: [83, 86] id: 6
        r13_0()
        r0_0.callStoppedScrollListener()
      end,
    })
  elseif r0_0.isVerticalScroll() then
    r9_0 = r3_5.y
    r13_0()
    Runtime:addEventListener("enterFrame", r12_0)
    r4_5 = transition.to(r3_5, {
      y = r0_5 * r7_0,
      time = r1_5,
      transition = easing.outExpo,
      onComplete = function()
        -- line: [97, 100] id: 7
        r13_0()
        r0_0.callStoppedScrollListener()
      end,
    })
  end
  if r4_5 then
    table.insert(r2_5.getTransitionStash(), r4_5)
  end
end
function r0_0.add(r0_20)
  -- line: [305, 343] id: 20
  local r1_20 = r11_0()
  local r2_20 = display.newGroup()
  local r3_20 = #r3_0 + 1
  r0_20.col = {}
  r0_20.col = r2_20
  r0_20.col.index = r3_20
  if r2_0 and r2_0(r0_20) == false then
    return 
  end
  if r6_0 < 0 then
    r6_0 = r2_20.width
  end
  if r7_0 < 0 then
    r7_0 = r2_20.height
  end
  r2_20.x = r10_0
  r10_0 = r10_0 + r2_20.width
  r3_0[r3_20] = r2_20
  r1_20.getScrollView():insert(r2_20)
  local r5_20, r6_20 = r1_20.getContentSize()
  r1_20.setContentSize(r5_20 + r2_20.width, r6_20)
  r1_20.update()
end
function r0_0.getListItem(r0_21)
  -- line: [348, 355] id: 21
  if r0_21 < 1 or #r3_0 < r0_21 then
    return nil
  end
  return r3_0[r0_21]
end
function r0_0.getListNum()
  -- line: [360, 362] id: 22
  return #r3_0
end
function r0_0.getScrollStage()
  -- line: [367, 369] id: 23
  return r1_0
end
function r0_0.setSelectPosition(r0_24)
  -- line: [374, 376] id: 24
  r4_0 = r0_24
end
function r0_0.movePrevious()
  -- line: [381, 388] id: 25
  if r5_0 < 1 then
    return 
  end
  r5_0 = r5_0 - 1
  r15_0(r5_0)
end
function r0_0.moveNext()
  -- line: [393, 400] id: 26
  if #r3_0 - 1 <= r5_0 then
    return 
  end
  r5_0 = r5_0 + 1
  r15_0(r5_0)
end
function r0_0.moveHome()
  -- line: [405, 412] id: 27
  if r5_0 < 1 then
    return 
  end
  r5_0 = 0
  r15_0(r5_0)
end
function r0_0.moveEnd()
  -- line: [417, 424] id: 28
  if #r3_0 - 1 <= r5_0 then
    return 
  end
  r5_0 = #r3_0 - 1
  r15_0(r5_0)
end
function r0_0.move(r0_29)
  -- line: [429, 432] id: 29
  r5_0 = r0_29
  r15_0(r0_29)
end
function r0_0.moveAtNoAnimation(r0_30)
  -- line: [437, 440] id: 30
  r5_0 = r0_30
  r15_0(r0_30, 0)
end
return {
  new = function(r0_8)
    -- line: [113, 300] id: 8
    local r1_8 = require("common.scrollView").new(r0_8)
    setmetatable(r0_0, {
      __index = r1_8,
    })
    r1_0 = nil
    r2_0 = nil
    r3_0 = nil
    r4_0 = 0
    r5_0 = 0
    r6_0 = -1
    r7_0 = -1
    r8_0 = 0
    r9_0 = 0
    r10_0 = 0
    r3_0 = {}
    if r0_8.onRender then
      r2_0 = r0_8.onRender
    end
    if r0_8.left and r0_8.width then
      r0_0.setSelectPosition(r0_8.width * 0.5 + r0_8.left)
    end
    r0_8.contentWidth = 0
    r1_0 = r1_8.getScrollStage()
    local r2_8 = r1_8.getViewTop()
    local r3_8 = r1_8.getViewLeft()
    local r4_8 = r1_8.getViewWidth()
    local r5_8 = r1_8.getViewHeight()
    local r6_8 = r1_8.getScrollView()
    local r7_8 = r1_8.getTransitionStash()
    function r1_8.callStartedScrollListener()
      -- line: [161, 165] id: 9
      if r1_8.StartedScrollListener then
        r1_8.StartedScrollListener(r0_0)
      end
    end
    function r1_8.callStoppedScrollListener()
      -- line: [170, 174] id: 10
      if r1_8.StoppedScrollListener then
        r1_8.StoppedScrollListener(r0_0)
      end
    end
    function r1_8.callMoveScrollListener(r0_11, r1_11)
      -- line: [179, 183] id: 11
      if r1_8.MoveScrollListener then
        r1_8.MoveScrollListener(r0_0, r0_11, r1_11)
      end
    end
    function r1_8.fixPositionX()
      -- line: [186, 226] id: 12
      if r6_8.x > 0 then
        r8_0 = r6_8.x
        r13_0()
        Runtime:addEventListener("enterFrame", r12_0)
        table.insert(r7_8, transition.to(r6_8, {
          x = 0,
          time = 200,
          Transition = easing.outExpo,
          onComplete = function()
            -- line: [197, 200] id: 13
            r13_0()
            r0_0.callStoppedScrollListener()
          end,
        }))
      elseif r6_8.x < r4_8 - r6_8.width then
        r8_0 = r6_8.x
        r13_0()
        Runtime:addEventListener("enterFrame", r12_0)
        table.insert(r7_8, transition.to(r6_8, {
          x = r4_8 - r6_8.width,
          time = 200,
          transition = easing.outExpo,
          onComplete = function()
            -- line: [213, 216] id: 14
            r13_0()
            r0_0.callStoppedScrollListener()
          end,
        }))
      else
        r0_0.move(math.abs(math.round(r6_8.x / r6_0)))
      end
    end
    function r1_8.fixPositionY()
      -- line: [229, 269] id: 15
      if r6_8.y > 0 then
        r9_0 = r6_8.y
        r13_0()
        Runtime:addEventListener("enterFrame", r12_0)
        table.insert(r7_8, transition.to(r6_8, {
          y = 0,
          time = 200,
          Transition = easing.outExpo,
          onComplete = function()
            -- line: [240, 243] id: 16
            r13_0()
            r0_0.callStoppedScrollListener()
          end,
        }))
      elseif r6_8.y < r5_8 - r6_8.height then
        r9_0 = r6_8.y
        r13_0()
        Runtime:addEventListener("enterFrame", r12_0)
        table.insert(r7_8, transition.to(r6_8, {
          y = r5_8 - r6_8.height,
          time = 200,
          transition = easing.outExpo,
          onComplete = function()
            -- line: [256, 259] id: 17
            r13_0()
            r0_0.callStoppedScrollListener()
          end,
        }))
      else
        r0_0.move(math.abs(math.round(r6_8.y / r7_0)))
      end
    end
    local r8_8 = r1_8.cancelAllTransitions
    function r1_8.cancelAllTransitions()
      -- line: [273, 276] id: 18
      r13_0()
      r8_8()
    end
    local r9_8 = r1_8.clean
    function r1_8.clean()
      -- line: [279, 293] id: 19
      r13_0()
      r9_8()
      r1_0 = nil
      r2_0 = nil
      r3_0 = nil
      r4_0 = 0
      r5_0 = 0
      r6_0 = -1
      r7_0 = -1
      r8_0 = 0
      r9_0 = 0
    end
    return r0_0
  end,
}
