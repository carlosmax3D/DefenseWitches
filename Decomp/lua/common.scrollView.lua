-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
return {
  new = function(r0_1)
    -- line: [11, 618] id: 1
    local r25_1 = nil	-- notice: implicit variable refs by block#[0]
    local r24_1 = nil	-- notice: implicit variable refs by block#[0]
    local r1_1 = {
      StartedScrollListener = nil,
      StoppedScrollListener = nil,
      MoveScrollListener = nil,
    }
    local r2_1 = 0
    local r3_1 = 0
    local r4_1 = {}
    local r5_1 = nil
    local r6_1 = {}
    local r7_1 = nil
    local r8_1 = 0
    local r9_1 = 0
    local r10_1 = 0
    local r11_1 = 0
    local r12_1 = 0
    local r13_1 = 0
    local r14_1 = 0
    local r15_1 = 0
    local r16_1 = 0
    local r17_1 = 0
    local r18_1 = false
    local r19_1 = false
    local r20_1 = false
    local function r21_1()
      -- line: [48, 53] id: 2
      if r14_1 < r16_1 then
        return true
      end
      return false
    end
    local function r22_1()
      -- line: [58, 63] id: 3
      if r15_1 < r17_1 then
        return true
      end
      return false
    end
    local function r23_1()
      -- line: [65, 73] id: 4
      for r5_4, r6_4 in pairs(r4_1) do
        transition.cancel(r6_4)
        r6_4 = nil
        r5_4 = nil
      end
      r4_1 = nilf
      r4_1 = {}
    end
    function r24_1(r0_5)
      -- line: [75, 96] id: 5
      if r6_1 == nil then
        Runtime:removeEventListener("enterFrame", r24_1)
        return 
      end
      if r6_1.x > 0 then
        r10_1 = r10_1 * 0.6
      elseif r6_1.x < r14_1 - r6_1.width then
        r10_1 = r10_1 * 0.6
      else
        r10_1 = r10_1 * 0.8
      end
      r6_1.x = r6_1.x + r10_1
      r1_1.callMoveScrollListener(r10_1, r11_1)
      if math.abs(r10_1) < 1 then
        Runtime:removeEventListener("enterFrame", r24_1)
        r1_1.fixPositionX()
      end
    end
    function r25_1(r0_6)
      -- line: [98, 120] id: 6
      if r6_1 == nil then
        Runtime:removeEventListener("enterFrame", r25_1)
        return 
      end
      if r6_1.y > 0 then
        r11_1 = r11_1 * 0.6
      elseif r6_1.y < r15_1 - r6_1.height then
        r11_1 = r11_1 * 0.6
      else
        r11_1 = r11_1 * 0.8
      end
      r6_1.y = r6_1.y + r11_1
      r1_1.callMoveScrollListener(r10_1, r11_1)
      if math.abs(r11_1) < 1 then
        Runtime:removeEventListener("enterFrame", r25_1)
        r1_1.fixPositionY()
      end
    end
    local function r26_1(r0_7)
      -- line: [125, 253] id: 7
      if r0_7.phase == "began" then
        Runtime:removeEventListener("enterFrame", r24_1)
        Runtime:removeEventListener("enterFrame", r25_1)
        r1_1.cancelAllTransitions()
        display.getCurrentStage():setFocus(r6_1)
        r6_1.isFocus = true
        r20_1 = false
        if r18_1 then
          r2_1 = r6_1.x
        end
        if r19_1 then
          r3_1 = r6_1.y
        end
      elseif r6_1.isFocus then
        if r0_7.phase == "moved" then
          if r20_1 == false and (r0_7.x <= r0_7.xStart - 20 or r0_7.xStart + 20 <= r0_7.x or r0_7.y <= r0_7.yStart - 20 or r0_7.yStart + 20 <= r0_7.y) then
            r20_1 = true
            r1_1.callStartedScrollListener()
          end
          if r18_1 then
            r8_1 = r6_1.x
            r10_1 = r0_7.x - r0_7.xStart + r2_1
            if r6_1.x > 0 then
              r6_1.x = r10_1 - r10_1 * 0.6
            elseif r6_1.x < r14_1 - r6_1.width then
              r6_1.x = r10_1 - (r10_1 - r14_1 + r6_1.width) * 0.6
            else
              r6_1.x = r10_1
            end
          end
          if r19_1 then
            r9_1 = r6_1.y
            r11_1 = r0_7.y - r0_7.yStart + r3_1
            if r6_1.y > 0 then
              r6_1.y = r11_1 - r11_1 * 0.6
            elseif r6_1.y < r15_1 - r6_1.height then
              r6_1.y = r11_1 - (r11_1 - r15_1 + r6_1.height) * 0.6
            else
              r6_1.y = r11_1
            end
          end
          r1_1.callMoveScrollListener(r0_7.x - r0_7.xStart, r0_7.y - r0_7.yStart)
        elseif r0_7.phase == "ended" or r0_7.phase == "cancelled" then
          if r0_7.xStart - 20 <= r0_7.x and r0_7.x <= r0_7.xStart + 20 and r0_7.yStart - 20 <= r0_7.y and r0_7.y <= r0_7.yStart + 20 then
            if r5_1.activeButton.btn ~= nil then
              r5_1.activeButton.btn.call({
                phase = "ended",
                target = r5_1.activeButton.btn,
              })
              r5_1.activeButton = {}
            end
            r1_1.callStoppedScrollListener()
            r10_1 = 0
            r11_1 = 0
            if r18_1 then
              r1_1.fixPositionX()
            end
            if r19_1 then
              r1_1.fixPositionY()
            end
          else
            if r18_1 then
              if r6_1.x < 0 and r14_1 - r6_1.width < r6_1.x then
                r10_1 = r6_1.x - r8_1
                Runtime:addEventListener("enterFrame", r24_1)
              else
                r1_1.fixPositionX()
              end
            end
            if r19_1 then
              if r6_1.y < 0 and r15_1 - r6_1.height < r6_1.y then
                r11_1 = r6_1.y - r9_1
                Runtime:addEventListener("enterFrame", r25_1)
              else
                r1_1.fixPositionY()
              end
            end
          end
          display.getCurrentStage():setFocus(nil)
          r6_1.isFocus = false
        end
      end
      return true
    end
    function r1_1.update()
      -- line: [305, 325] id: 9
      r18_1 = r21_1()
      r19_1 = r22_1()
      r6_1:removeEventListener("touch", r26_1)
      if r14_1 < r6_1.width or r15_1 < r6_1.height then
        if r7_1 then
          display.remove(r7_1)
        end
        r7_1 = display.newRect(0, 0, r6_1.width, r6_1.height)
        r7_1:setFillColor(0, 0, 0, 0)
        r6_1:insert(1, r7_1)
        r6_1:addEventListener("touch", r26_1)
      end
    end
    function r1_1.getScrollView()
      -- line: [330, 332] id: 10
      return r6_1
    end
    function r1_1.getViewTop()
      -- line: [337, 339] id: 11
      return r12_1
    end
    function r1_1.setViewTop(r0_12)
      -- line: [344, 346] id: 12
      r12_1 = r0_12
    end
    function r1_1.getViewLeft()
      -- line: [351, 353] id: 13
      return r13_1
    end
    function r1_1.setViewLeft(r0_14)
      -- line: [358, 360] id: 14
      r13_1 = r0_14
    end
    function r1_1.getViewWidth()
      -- line: [365, 367] id: 15
      return r14_1
    end
    function r1_1.setViewWidth(r0_16)
      -- line: [372, 374] id: 16
      r14_1 = r0_16
    end
    function r1_1.getViewHeight()
      -- line: [379, 381] id: 17
      return r15_1
    end
    function r1_1.setViewHeight(r0_18)
      -- line: [386, 388] id: 18
      r15_1 = r0_18
    end
    function r1_1.getContentSize()
      -- line: [393, 395] id: 19
      return r16_1, r17_1
    end
    function r1_1.setContentSize(r0_20, r1_20)
      -- line: [400, 403] id: 20
      r16_1 = r0_20
      r17_1 = r1_20
    end
    function r1_1.getTransitionStash()
      -- line: [408, 410] id: 21
      return r4_1
    end
    function r1_1.cancelAllTransitions()
      -- line: [412, 420] id: 22
      for r5_22, r6_22 in pairs(r4_1) do
        transition.cancel(r6_22)
        r6_22 = nil
        r5_22 = nil
      end
      r4_1 = nil
      r4_1 = {}
    end
    function r1_1.isHorizonScroll()
      -- line: [425, 427] id: 23
      return r18_1
    end
    function r1_1.isVerticalScroll()
      -- line: [432, 434] id: 24
      return r19_1
    end
    function r1_1.callStartedScrollListener()
      -- line: [439, 443] id: 25
      if r1_1.StartedScrollListener then
        r1_1.StartedScrollListener()
      end
    end
    function r1_1.callStoppedScrollListener()
      -- line: [448, 452] id: 26
      if r1_1.StoppedScrollListener then
        r1_1.StoppedScrollListener()
      end
    end
    function r1_1.callMoveScrollListener(r0_27, r1_27)
      -- line: [457, 461] id: 27
      if r1_1.MoveScrollListener then
        r1_1.MoveScrollListener(r0_27, r1_27)
      end
    end
    function r1_1.fixPositionX()
      -- line: [464, 496] id: 28
      if r13_1 < r6_1.x then
        table.insert(r4_1, transition.to(r6_1, {
          x = r13_1,
          time = 400,
          Transition = easing.outExpo,
          onComplete = function()
            -- line: [471, 477] id: 29
            r1_1.callStoppedScrollListener()
            if table.indexOf(r4_1, trans) then
              table.remove(r4_1, trans)
            end
          end,
        }))
      elseif r6_1.x < r14_1 - r6_1.width then
        table.insert(r4_1, transition.to(r6_1, {
          x = r14_1 - r6_1.width,
          time = 400,
          transition = easing.outExpo,
          onComplete = function()
            -- line: [486, 492] id: 30
            r1_1.callStoppedScrollListener()
            if table.indexOf(r4_1, trans) then
              table.remove(r4_1, trans)
            end
          end,
        }))
      end
    end
    function r1_1.fixPositionY()
      -- line: [499, 531] id: 31
      if r12_1 < r6_1.y then
        table.insert(r4_1, transition.to(r6_1, {
          y = r12_1,
          time = 400,
          Transition = easing.outExpo,
          onComplete = function()
            -- line: [506, 512] id: 32
            r1_1.callStoppedScrollListener()
            if table.indexOf(r4_1, trans) then
              table.remove(r4_1, trans)
            end
          end,
        }))
      elseif r6_1.y < r15_1 - r6_1.height then
        table.insert(r4_1, transition.to(r6_1, {
          y = r15_1 - r6_1.height,
          time = 400,
          transition = easing.outExpo,
          onComplete = function()
            -- line: [521, 527] id: 33
            r1_1.callStoppedScrollListener()
            if table.indexOf(r4_1, trans) then
              table.remove(r4_1, trans)
            end
          end,
        }))
      end
    end
    function r1_1.getScrollStage()
      -- line: [536, 538] id: 34
      return r5_1
    end
    function r1_1.setButton(r0_35)
      -- line: [543, 545] id: 35
      r5_1.activeButton.btn = r0_35
    end
    function r1_1.addStartedScrollEventListener(r0_36)
      -- line: [550, 552] id: 36
      r1_1.StartedScrollListener = r0_36
    end
    function r1_1.addStoppedScrollEventListener(r0_37)
      -- line: [557, 559] id: 37
      r1_1.StoppedScrollListener = r0_37
    end
    function r1_1.addMoveScrollEventListener(r0_38)
      -- line: [565, 567] id: 38
      r1_1.MoveScrollListener = r0_38
    end
    function r1_1.clean()
      -- line: [572, 613] id: 39
      r1_1.StartedScrollListener = nil
      r1_1.StoppedScrollListener = nil
      r1_1.MoveScrollListener = nil
      r2_1 = 0
      r3_1 = 0
      r4_1 = {}
      Runtime:removeEventListener("enterFrame", r24_1)
      Runtime:removeEventListener("enterFrame", r25_1)
      if r6_1 then
        r6_1 = nil
      end
      if r5_1 then
        r5_1:removeSelf()
        r5_1 = nil
      end
      r7_1 = nil
      r8_1 = 0
      r9_1 = 0
      r10_1 = 0
      r11_1 = 0
      r12_1 = 0
      r13_1 = 0
      r14_1 = 0
      r15_1 = 0
      r16_1 = 0
      r17_1 = 0
      r18_1 = false
      r19_1 = false
      r20_1 = false
    end
    (function(r0_8)
      -- line: [258, 303] id: 8
      r1_1.clean()
      r1_1.StoppedScrollListener = nil
      r5_1 = display.newGroup()
      r6_1 = display.newGroup()
      if r0_8.grp then
        r6_1:insert(r0_8.grp)
      end
      r5_1:insert(r6_1)
      r5_1.activeButton = {}
      r5_1.x = r0_8.left
      r5_1.y = r0_8.top
      if r0_8.top then
        r12_1 = r0_8.top
      end
      if r0_8.left then
        r13_1 = r0_8.left
      end
      if r0_8.width then
        r14_1 = r0_8.width
      end
      if r0_8.height then
        r15_1 = r0_8.height
      end
      if r0_8.contentWidth then
        r16_1 = r0_8.contentWidth
      end
      if r0_8.contentHeight then
        r17_1 = r0_8.contentHeight
      end
      r1_1.update()
    end)(r0_1)
    return r1_1
  end,
}
