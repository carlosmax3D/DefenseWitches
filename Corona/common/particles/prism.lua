-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
return {
  new = function(r0_1)
    -- line: [9, 257] id: 1
    local r1_1 = {
      position = {
        x = 0,
        y = 0,
      },
      range = {
        width = 10,
        height = 10,
      },
      generateVolume = 100,
      lifeLimit = {
        min = 10,
        max = 100,
      },
      size = {
        min = 2,
        max = 10,
      },
      alphaRange = {
        min = 0,
        max = 1,
      },
      color = nil,
    }
    local r2_1 = nil
    local r3_1 = nil
    local r4_1 = nil
    local function r5_1(r0_2, r1_2)
      -- line: [57, 59] id: 2
      r2_1 = graphics.newImageSheet(r0_2, r1_2)
    end
    local function r6_1(r0_3, r1_3)
      -- line: [63, 91] id: 3
      local r2_3 = display.newGroup()
      if r2_1 == nil then
        r2_3:insert(display.newCircle(0, 0, r0_3 * 0.5))
      elseif r3_1 ~= nil then
        local r3_3 = display.newSprite(r2_1, r3_1)
        r2_3:insert(r3_3)
        r3_3.width = r0_3
        r3_3.height = r0_3
        r3_3:play()
      else
        local r3_3 = display.newImage(r2_1, 1)
        r3_3.width = r0_3
        r3_3.height = r0_3
        r2_3:insert(r3_3)
      end
      if r1_3 ~= nil and type(r1_3) == "table" then
        r2_3[1]:setFillColor(r1_3[1], r1_3[2], r1_3[3])
      end
      return r2_3
    end
    local function r7_1(r0_4, r1_4, r2_4, r3_4, r4_4, r5_4, r6_4)
      -- line: [95, 147] id: 4
      local r7_4 = r6_1(r2_4, r3_4)
      r0_4:insert(r7_4)
      r7_4.x = r1_4.x
      r7_4.y = r1_4.y
      r7_4.xScale = 0.5
      r7_4.yScale = 0.5
      r7_4.alpha = r4_4.min
      r7_4.startTime = r6_4
      r7_4.fadeIn = {}
      r7_4.fadeIn.time = r5_4 * 0.3
      r7_4.fadeIn.alphaStep = (r4_4.max - r4_4.min) / r7_4.fadeIn.time
      r7_4.fadeIn.xScaleStep = (1.5 - r7_4.xScale) / r7_4.fadeIn.time
      r7_4.fadeIn.yScaleStep = (1.5 - r7_4.yScale) / r7_4.fadeIn.time
      r7_4.fadeOut = {}
      r7_4.fadeOut.time = r5_4 - r7_4.fadeIn.time
      r7_4.fadeOut.alphaStep = r4_4.max / r7_4.fadeOut.time
      r7_4.fadeOut.xScaleStep = r7_4.fadeOut.alphaStep
      r7_4.fadeOut.yScaleStep = r7_4.fadeOut.alphaStep
      local r8_4 = nil
      local function r9_4(r0_5, r1_5)
        -- line: [117, 127] id: 5
        if r0_5.fadeOut.time < r1_5.time - r0_5.startTime then
          display.remove(r0_5)
          Runtime:removeEventListener("enterFrame", r0_5)
          return 
        end
        r0_5.alpha = r0_5.alpha - r0_5.fadeOut.alphaStep
        r0_5.xScale = r0_5.xScale - r0_5.fadeOut.xScaleStep
        r0_5.yScale = r0_5.yScale - r0_5.fadeOut.yScaleStep
      end
      function r8_4(r0_6, r1_6)
        -- line: [129, 139] id: 6
        if r0_6.fadeIn.time < r1_6.time - r0_6.startTime then
          r0_6.startTime = r1_6.time
          r8_4 = r9_4
          return 
        end
        r0_6.alpha = r0_6.alpha + r0_6.fadeIn.alphaStep
        r0_6.xScale = r0_6.xScale + r0_6.fadeIn.xScaleStep
        r0_6.yScale = r0_6.yScale + r0_6.fadeIn.yScaleStep
      end
      function r7_4.enterFrame(r0_7, r1_7)
        -- line: [142, 144] id: 7
        r8_4(r0_7, r1_7)
      end
      Runtime:addEventListener("enterFrame", r7_4)
    end
    local function r8_1()
      -- line: [151, 174] id: 8
      local r0_8 = {
        x = r1_1.position.x + math.random(r1_1.range.width) - r1_1.range.width * 0.5,
        y = r1_1.position.y + math.random(r1_1.range.height) - r1_1.range.height * 0.5,
      }
      local r1_8 = math.random(r1_1.size.min, r1_1.size.max)
      local r2_8 = math.random(r1_1.alphaRange.min, r1_1.alphaRange.max)
      local r3_8 = math.random(r1_1.alphaRange.min, r1_1.alphaRange.max)
      r7_1(r4_1, r0_8, r1_8, r1_1.color, {
        min = math.min(r2_8, r3_8),
        max = math.max(r2_8, r3_8),
      }, math.random(r1_1.lifeLimit.min, r1_1.lifeLimit.max), system.getTimer())
    end
    local function r9_1()
      -- line: [178, 183] id: 9
      for r4_9 = 1, r1_1.generateVolume, 1 do
        r8_1()
      end
    end
    local function r10_1(r0_10)
      -- line: [187, 230] id: 10
      if r0_10 == nil then
        return 
      end
      if r0_10.parentLayer ~= nil then
        r4_1 = r0_10.parentLayer
      end
      if r0_10.position ~= nil and type(r0_10.position) == "table" then
        r1_1.position = r0_10.position
      end
      if r0_10.range ~= nil and type(r0_10.range) == "table" then
        r1_1.range = r0_10.range
      end
      if r0_10.generateVolume ~= nil then
        r1_1.generateVolume = r0_10.generateVolume
      end
      if r0_10.lifeLimit ~= nil and type(r0_10.lifeLimit) == "table" then
        r1_1.lifeLimit = r0_10.lifeLimit
      end
      if r0_10.size ~= nil and type(r0_10.size) then
        r1_1.size = r0_10.size
      end
      if r0_10.imageSheet ~= nil and r0_10.imageSheet.fileName and r0_10.imageSheet.options then
        r5_1(r0_10.imageSheet.fileName, r0_10.imageSheet.options)
      end
      if r0_10.spriteSequence ~= nil then
        r3_1 = r0_10.spriteSequence
      end
    end
    function r1_1.play()
      -- line: [247, 249] id: 12
      r9_1()
    end
    (function()
      -- line: [234, 240] id: 11
      r10_1(r0_1)
      if r4_1 == nil then
        r4_1 = display.newGroup()
      end
    end)()
    return r1_1
  end,
}
