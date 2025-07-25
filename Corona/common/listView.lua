-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r7_0 = nil	-- notice: implicit variable refs by block#[0]
local r0_0 = {}
local r1_0 = nil
local r2_0 = nil
local r3_0 = nil
local r4_0 = "separateLine"
local r5_0 = {}
local function r6_0()
  -- line: [18, 21] id: 1
  assert(r0_0, debug.traceback())
  return getmetatable(r0_0).__index
end
function r7_0(r0_2, r1_2)
  -- line: [26, 71] id: 2
  if #r2_0 < 1 then
    if r1_2.onComplete and type(r1_2.onComplete) == "function" then
      r1_2.onComplete()
    end
    return 
  end
  local r2_2 = r2_0[1]
  if r1_2.onBeforeRemove then
    r1_2.onBeforeRemove(r2_2.index, function()
      -- line: [39, 53] id: 3
      local r0_3 = transition.to(r2_2, {
        alpha = 0,
        time = 200,
        onComplete = function()
          -- line: [43, 51] id: 4
          r0_2 = r0_2 + 1
          r0_0.remove(r2_2, {
            speed = 50,
            onComplete = function()
              -- line: [47, 49] id: 5
              r7_0(r0_2, r1_2, listener)
            end,
          })
        end,
      })
    end)
  else
    local r3_2 = transition.to(r2_2, {
      alpha = 0,
      time = 200,
      onComplete = function()
        -- line: [58, 66] id: 6
        r0_2 = r0_2 + 1
        r0_0.remove(r2_2, {
          speed = 50,
          onComplete = function()
            -- line: [62, 64] id: 7
            r7_0(r0_2, r1_2, listener)
          end,
        })
      end,
    })
  end
  table.insert(r5_0, trans)
end
function r0_0.getScrollStage()
  -- line: [97, 99] id: 9
  return r1_0
end
function r0_0.getSeparateKey()
  -- line: [104, 106] id: 10
  return r4_0
end
function r0_0.add(r0_11)
  -- line: [111, 137] id: 11
  local r1_11 = r6_0()
  local r2_11 = display.newGroup()
  local r3_11 = #r2_0 + 1
  r0_11.row = {}
  r0_11.row = r2_11
  r0_11.row.index = r3_11
  if r3_0 then
    r3_0(r0_11)
  end
  r2_11.y = #r2_0 * r2_11.height
  r2_0[r3_11] = r2_11
  r1_11.getScrollView():insert(r2_11)
  local r5_11, r6_11 = r1_11.getContentSize()
  r1_11.setContentSize(r5_11, r6_11 + r2_11.height)
  r1_11.update()
end
function r0_0.remove(r0_12, r1_12)
  -- line: [142, 229] id: 12
  local r2_12 = r6_0()
  local r3_12 = r2_12.getScrollView()
  local r4_12 = 0
  local r5_12 = table.indexOf(r2_0, r0_12)
  if r5_12 == nil then
    return 
  end
  local r6_12 = r0_12.height
  local r7_12 = 0
  local r8_12 = display.newGroup()
  r3_12:insert(r8_12)
  for r12_12 = r5_12 + 1, #r2_0, 1 do
    r8_12:insert(r2_0[r12_12])
  end
  local r9_12 = 200
  if r1_12.speed and 0 <= r1_12.speed then
    r9_12 = r1_12.speed
  end
  table.insert(r5_0, transition.to(r8_12, {
    y = -r6_12,
    time = r9_12,
    Transition = easing.outExpo,
    onComplete = function()
      -- line: [177, 225] id: 13
      display.remove(r2_0[r5_12])
      table.remove(r2_0, r5_12)
      r3_12.isVisible = false
      if #r2_0 > 0 then
        local r1_13 = r2_0[1]
        for r5_13 = 1, r1_13.numChildren, 1 do
          local r6_13 = r1_13[r5_13]
          if r6_13[r4_0] == true then
            display.remove(r6_13)
            break
          end
        end
      end
      for r4_13 = 1, #r2_0, 1 do
        local r5_13 = r2_0[r4_13]
        local r6_13 = r5_13.y
        r5_13.y = (r4_13 - 1) * r5_13.height
        r3_12:insert(r5_13)
      end
      r3_12:remove(r8_12)
      r3_12.isVisible = true
      r2_12.update()
      if r3_12.y <= r2_12.getViewHeight() - r3_12.height then
        local r1_13 = r3_12.y + r6_12
        if r1_13 > 0 then
          r1_13 = 0
        end
        table.insert(r5_0, transition.to(r3_12, {
          y = r1_13,
          time = 250,
          Transition = easing.outExpo,
        }))
      end
      if r1_12.onComplete and type(r1_12.onComplete) == "function" then
        r1_12.onComplete()
      end
    end,
  }))
end
function r0_0.removeAllAnimation(r0_14)
  -- line: [234, 236] id: 14
  r7_0(1, r0_14)
end
function r0_0.removeAll()
  -- line: [241, 254] id: 15
  for r3_15, r4_15 in pairs(r5_0) do
    transition.cancel(r4_15)
    r3_15 = nil
    r4_15 = nil
  end
  for r3_15, r4_15 in pairs(r2_0) do
    display.remove(r4_15)
    r3_15 = nil
    r4_15 = nil
  end
end
function r0_0.getRows()
  -- line: [259, 261] id: 16
  return r2_0
end
return {
  new = function(r0_8)
    -- line: [76, 92] id: 8
    local r1_8 = require("common.scrollView").new(r0_8)
    setmetatable(r0_0, {
      __index = r1_8,
    })
    r2_0 = {}
    if r0_8.onRender then
      r3_0 = r0_8.onRender
    end
    r0_8.contentHeight = 0
    r1_0 = r1_8.getScrollStage()
    return r0_0
  end,
}
