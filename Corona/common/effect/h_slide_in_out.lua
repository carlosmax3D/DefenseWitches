-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = 1
local r1_0 = 2
local r2_0 = 3
return {
  AnimationPhaseCountStart = r0_0,
  AnimationPhaseCountStop = r1_0,
  AnimationPhaseCountEnd = r2_0,
  new = function(r0_1)
    -- line: [34, 408] id: 1
    local r20_1 = nil	-- notice: implicit variable refs by block#[0]
    local r1_1 = {
      slideTarget = nil,
    }
    local r2_1 = 0
    local r3_1 = 0
    local r4_1 = 0
    local r5_1 = 0
    local r6_1 = 0
    local r7_1 = 0
    local r8_1 = 0
    local r9_1 = 0
    local r10_1 = 0
    local r11_1 = 0
    local r12_1 = false
    local r13_1 = 0
    local r14_1 = nil
    local r15_1 = nil
    local r16_1 = nil
    local function r17_1(r0_2, r1_2, r2_2)
      -- line: [86, 115] id: 2
      r1_2.ms = r1_2.ms + r2_2
      if r1_2.endMs <= r1_2.ms then
        r1_2.target.x = r1_2.endX
        r1_2.target.alpha = r1_2.endAlpha
        events.Delete(r16_1)
        r16_1 = nil
        if r15_1 ~= nil then
          r15_1()
        end
        return false
      end
      local r3_2 = easing.inExpo(r1_2.ms, r1_2.endMs, r1_2.startX, r1_2.endX - r1_2.startX)
      local r4_2 = easing.inExpo(r1_2.ms, r1_2.endMs, r1_2.startAlpha, r1_2.endAlpha - r1_2.startAlpha)
      if r4_2 > 1 then
        r4_2 = 1
      end
      r1_2.target.x = r3_2
      r1_2.target.alpha = r4_2
      return true
    end
    local function r18_1(r0_3, r1_3, r2_3)
      -- line: [120, 136] id: 3
      r1_3.ms = r1_3.ms + r2_3
      if r1_3.endMs <= r1_3.ms then
        events.Delete(r16_1)
        r16_1 = nil
        r1_3.endListener()
        return false
      end
      return true
    end
    local function r19_1(r0_4, r1_4, r2_4)
      -- line: [141, 169] id: 4
      r1_4.ms = r1_4.ms + r2_4
      if r1_4.endMs <= r1_4.ms then
        r1_4.target.x = r1_4.endX
        r1_4.target.alpha = r1_4.endAlpha
        events.Delete(r16_1)
        r16_1 = nil
        r1_4.endListener()
        return false
      end
      local r3_4 = easing.outExpo(r1_4.ms, r1_4.endMs, r1_4.startX, r1_4.endX - r1_4.startX)
      local r4_4 = easing.outExpo(r1_4.ms, r1_4.endMs, r1_4.startAlpha, r1_4.endAlpha - r1_4.startAlpha)
      if r4_4 > 1 then
        r4_4 = 1
      end
      r1_4.target.x = r3_4
      r1_4.target.alpha = r4_4
      return true
    end
    function r20_1(r0_5)
      -- line: [174, 240] id: 5
      r13_1 = r0_5
      if r0_5 == r0_0 then
        r1_1.slideTarget.x = r2_1
        r1_1.slideTarget.y = r5_1
        r1_1.slideTarget.alpha = r6_1
        r16_1 = events.Register(r19_1, {
          target = r1_1.slideTarget,
          startX = r2_1,
          endX = r3_1,
          startAlpha = r6_1,
          endAlpha = r7_1,
          ms = 0,
          endMs = r9_1,
          endListener = function()
            -- line: [192, 194] id: 6
            r20_1(r1_0)
          end,
        }, 0)
      elseif r0_5 == r1_0 then
        r16_1 = events.Register(r18_1, {
          target = r1_1.slideTarget,
          startX = r3_1,
          endX = r3_1,
          startAlpha = r7_1,
          endAlpha = r7_1,
          ms = 0,
          endMs = r10_1,
          endListener = function()
            -- line: [210, 219] id: 7
            if r14_1 ~= nil then
              r14_1()
            end
            if r12_1 == false then
              r20_1(r2_0)
            end
          end,
        }, 0)
      elseif r0_5 == r2_0 then
        r16_1 = events.Register(r17_1, {
          target = r1_1.slideTarget,
          startX = r3_1,
          endX = r4_1,
          startAlpha = r7_1,
          endAlpha = r8_1,
          ms = 0,
          endMs = r11_1,
          endListener = function()
            -- line: [234, 236] id: 8
            r20_1(r2_0 + 1)
          end,
        }, 0)
      end
    end
    local function r21_1()
      -- line: [245, 254] id: 9
      if r1_1.slideTarget == nil then
        return 
      end
      r16_1 = nil
      r20_1(r0_0)
    end
    local function r22_1()
      -- line: [259, 270] id: 10
      if r16_1 == nil then
        return 
      end
      events.Delete(r16_1)
      r16_1 = nil
      if r15_1 ~= nil then
        r15_1()
      end
    end
    local function r23_1()
      -- line: [275, 280] id: 11
      if r16_1 ~= nil then
        events.Delete(r16_1)
        r16_1 = nil
      end
    end
    function r1_1.Play(r0_13)
      -- line: [362, 368] id: 13
      if r0_13 == nil then
        r21_1()
      else
        r20_1(r0_13)
      end
    end
    function r1_1.IsPlay()
      -- line: [373, 378] id: 14
      if r16_1 ~= nil then
        return true
      end
      return false
    end
    function r1_1.GetAnimationPhase()
      -- line: [383, 385] id: 15
      return r13_1
    end
    function r1_1.CancelPlay()
      -- line: [390, 392] id: 16
      r22_1()
    end
    function r1_1.Clean()
      -- line: [397, 399] id: 17
      r23_1()
    end
    (function()
      -- line: [285, 354] id: 12
      local r0_12 = nil	-- notice: implicit variable refs by block#[0]
      r16_1 = r0_12
      r1_1.slideTarget = nil
      r0_12 = r0_1.slideTarget
      if r0_12 ~= nil then
        r0_12 = r1_1
        r0_12.slideTarget = r0_1.slideTarget
      end
      r2_1 = 0
      r0_12 = r0_1.startPosX
      if r0_12 ~= nil then
        r0_12 = r0_1.startPosX
        r2_1 = r0_12
      end
      r3_1 = 0
      r0_12 = r0_1.stopPosX
      if r0_12 ~= nil then
        r0_12 = r0_1.stopPosX
        r3_1 = r0_12
      end
      r4_1 = 0
      r0_12 = r0_1.endPosX
      if r0_12 ~= nil then
        r0_12 = r0_1.endPosX
        r4_1 = r0_12
      end
      r5_1 = 0
      r0_12 = r0_1.fixPosY
      if r0_12 ~= nil then
        r0_12 = r0_1.fixPosY
        r5_1 = r0_12
      end
      r6_1 = 0
      r0_12 = r0_1.startAlpha
      if r0_12 ~= nil then
        r0_12 = r0_1.startAlpha
        r6_1 = r0_12
      end
      r7_1 = 0
      r0_12 = r0_1.stopAlpha
      if r0_12 ~= nil then
        r0_12 = r0_1.stopAlpha
        r7_1 = r0_12
      end
      r8_1 = 0
      r0_12 = r0_1.endAlpha
      if r0_12 ~= nil then
        r0_12 = r0_1.endAlpha
        r8_1 = r0_12
      end
      r9_1 = 0
      r0_12 = r0_1.start2StopTime
      if r0_12 ~= nil then
        r0_12 = r0_1.start2StopTime
        r9_1 = r0_12
      end
      r10_1 = 0
      r0_12 = r0_1.stopTime
      if r0_12 ~= nil then
        r0_12 = r0_1.stopTime
        r10_1 = r0_12
      end
      r11_1 = 0
      r0_12 = r0_1.stop2EndTime
      if r0_12 ~= nil then
        r0_12 = r0_1.stop2EndTime
        r11_1 = r0_12
      end
      r12_1 = false
      r0_12 = r0_1.pauseAtStopFlag
      if r0_12 ~= nil then
        r0_12 = r0_1.pauseAtStopFlag
        r12_1 = r0_12
      end
      r14_1 = nil
      r0_12 = r0_1.stopListener
      if r0_12 ~= nil then
        r0_12 = r0_1.stopListener
        r14_1 = r0_12
      end
      r15_1 = nil
      r0_12 = r0_1.endListener
      if r0_12 ~= nil then
        r0_12 = r0_1.endListener
        r15_1 = r0_12
      end
    end)()
    return r1_1
  end,
}
