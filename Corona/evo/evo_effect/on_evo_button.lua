-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
return {
  new = function(r0_1)
    -- line: [9, 165] id: 1
    local r1_1 = {}
    local r2_1 = nil
    local r3_1 = nil
    local r4_1 = nil
    local r5_1 = 0
    local r6_1 = 0
    local function r7_1()
      -- line: [30, 59] id: 2
      local r0_2 = {
        enterFrame = function(r0_3, r1_3)
          -- line: [34, 53] id: 3
          if r6_1 <= 0 then
            r6_1 = r1_3.time + r5_1
          end
          if r6_1 <= r1_3.time then
            Runtime:removeEventListener("enterFrame", r1_1)
            r2_1.stop()
          end
          local r2_3 = easing.linear(r1_3.time, r6_1, r3_1[1], r4_1[1])
          local r3_3 = easing.linear(r1_3.time, r6_1, r3_1[2], r4_1[2])
          r2_1.baseX = r2_3
          r2_1.baseY = r3_3
        end,
      }
      r6_1 = 0
      Runtime:addEventListener("enterFrame", r0_2, 0)
      r2_1.play()
    end
    function r1_1.Play()
      -- line: [155, 157] id: 5
      r7_1()
    end
    (function()
      -- line: [64, 147] id: 4
      r3_1 = {
        0,
        0
      }
      if r0_1.startPos ~= nil and type(r0_1.startPos) == "table" then
        r3_1 = r0_1.startPos
      end
      r4_1 = {
        0,
        0
      }
      if r0_1.startPos ~= nil and type(r0_1.startPos) == "table" then
        r4_1 = r0_1.startPos
      end
      r5_1 = 0
      if r0_1.durationTime ~= nil then
        r5_1 = r0_1.durationTime
      end
      r2_1 = require("common.particles.pixieDust").new({
        baseX = r3_1[1],
        baseY = r3_1[2],
        flowX = 1,
        flowY = -2,
        particleMax = 5,
        sizeStart = 3,
        sizeEnd = 27,
        moveXStart = -10,
        moveXEnd = 10,
        moveYStart = -10,
        moveYEnd = 10,
        accelerateXStart = -6,
        accelerateXEnd = 6,
        accelerateYStart = -6,
        accelerateYEnd = 6,
        lifetimeStart = 1,
        lifetimeEnd = 28,
        alphaStepStart = 20,
        alphaStepEnd = 100,
        twistSpeedStart = 1,
        twistSpeedEnd = 10,
        twistRadiusStart = 1,
        twistRadiusEnd = 5,
        twistAngleStart = 0,
        twistAngleEnd = 359,
        twistAngleStepStart = 1,
        twistAngleStepEnd = 10,
      })
    end)()
    return r1_1
  end,
}
