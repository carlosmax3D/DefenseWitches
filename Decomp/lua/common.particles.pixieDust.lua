-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = {
  IMAGE = function()
    -- line: [9, 9] id: 1
    return "image"
  end,
  SPRITE = function()
    -- line: [10, 10] id: 2
    return "sprite"
  end,
}
return {
  GENERATE_SPRITE_TYPE = r0_0,
  new = function(r0_3)
    -- line: [16, 655] id: 3
    local r1_3 = 1
    local r2_3 = 2
    local r3_3 = 3
    local r4_3 = 180 / math.pi
    local r5_3 = {
      parentObj = nil,
      baseX = 0,
      baseY = 0,
      startRadius = 0,
      accelerateX = 0,
      accelerateY = 0,
      flowX = 0,
      flowY = 0,
      lifeFrame = 0,
      lifeFrameCount = 0,
      sizeStart = 0,
      sizeEnd = 0,
      moveXStart = 0,
      moveXEnd = 0,
      moveYStart = 0,
      moveYEnd = 0,
      accelerateXStart = 0,
      accelerateXEnd = 0,
      accelerateYStart = 0,
      accelerateYEnd = 0,
      lifetimeStart = 0,
      lifetimeEnd = 0,
      alphaStepStart = 0,
      alphaStepEnd = 0,
      alphaStep = 0,
      twistSpeedStart = 0,
      twistSpeedEnd = 0,
      twistRadiusStart = 0,
      twistRadiusEnd = 0,
      twistAngleStart = 0,
      twistAngleEnd = 0,
      twistAngleStepStart = 0,
      twistAngleStepEnd = 0,
      particleMax = 1,
      useSequenceName = nil,
      generateParticle = nil,
    }
    local r6_3 = nil
    local r7_3 = nil
    local r8_3 = nil
    local r9_3 = nil
    local r10_3 = nil
    local r11_3 = false
    local function r12_3(r0_4)
      -- line: [121, 142] id: 4
      if r0_4 == nil or r0_4.file == nil or r0_4.options == nil then
        return 
      end
      r8_3 = graphics.newImageSheet(r0_4.file, r0_4.options)
      if r8_3 == nil then
        DebugPrint("image sheet生成エラー")
        return 
      end
      if r0_4.sequence ~= nil then
        if type(r0_4.sequence) == "table" then
          r9_3 = r0_4.sequence
        else
          r9_3 = {
            r0_4.sequence
          }
        end
      end
    end
    local function r13_3(r0_5)
      -- line: [152, 220] id: 5
      if r0_5 == nil then
        return 
      end
      local r1_5 = 1
      if r0_5.size ~= nil then
        r1_5 = r0_5.size
      end
      local r2_5 = display.newGroup()
      local r3_5 = nil
      if r8_3 ~= nil then
        local function r4_5()
          -- line: [167, 172] id: 6
          r3_5 = display.newSprite(r2_5, r8_3, r9_3)
          r2_5.type = r2_3
          r2_5.width = r1_5
          r2_5.height = r1_5
        end
        local function r5_5()
          -- line: [174, 179] id: 7
          r3_5 = display.newImage(r2_5, r8_3, r0_5.generateParticle.frameNum)
          r2_5.type = r3_3
          r2_5.width = r1_5
          r2_5.height = r1_5
        end
        if r0_5.generateParticle == nil then
          r4_5()
        elseif r0_5.generateParticle ~= nil then
          if r0_5.generateParticle.type == r0_0.SPRITE() then
            r4_5()
          else
            r5_5()
          end
        end
      else
        r3_5 = display.newCircle(r2_5, 0, 0, r1_5)
        r2_5.type = r1_3
      end
      r2_5.isVisible = false
      if r0_5.bx ~= nil then
        r2_5.x = r0_5.bx
      end
      if r0_5.by ~= nil then
        r2_5.y = r0_5.by
      end
      if r0_5.color ~= nil and type(r0_5.color) == "table" and 2 < #r0_5.color then
        r3_5:setFillColor(r0_5.color[1], r0_5.color[2], r0_5.color[3])
        if #r0_5.color == 4 then
          r3_5.aplha = r0_5.color[4]
        end
      end
      return r2_5
    end
    local function r14_3(r0_8)
      -- line: [234, 354] id: 8
      if r0_8 == nil or r0_8.particle == nil then
        return 
      end
      local r1_8 = r13_3(r0_8.particle)
      if r5_3.parentObj ~= nil then
        r5_3.parentObj:insert(r1_8)
      end
      r1_8.bx = r1_8.x
      r1_8.by = r1_8.y
      r1_8.moveX = 1
      r1_8.moveY = 1
      if r0_8.moveX ~= nil then
        r1_8.moveX = r0_8.moveX
      end
      if r0_8.moveY ~= nil then
        r1_8.moveY = r0_8.moveY
      end
      r1_8.accelerateX = 0
      r1_8.accelerateY = 0
      if r0_8.accelerateX ~= nil then
        r1_8.accelerateX = r0_8.accelerateX
      end
      if r0_8.accelerateY ~= nil then
        r1_8.accelerateY = r0_8.accelerateY
      end
      r1_8.lifeFrameCount = 0
      if r0_8.lifeFrame ~= nil then
        r1_8.lifeFrame = r0_8.lifeFrame
      else
        r1_8.lifeFrame = r5_3.lifeFrame
      end
      if r0_8.startAlpha ~= nil then
        r1_8.alpha = r0_8.startAlpha
      else
        r1_8.alpha = 1
      end
      if r0_8.alphaStep ~= nil then
        r1_8.alphaStep = r0_8.alphaStep
      else
        r1_8.alphaStep = r5_3.alphaStep
      end
      if r0_8.twist ~= nil then
        r1_8.twist = r0_8.twist
      else
        r1_8.twist = nil
      end
      function r1_8.enterFrame(r0_9, r1_9)
        -- line: [301, 336] id: 9
        r0_9.lifeFrameCount = r0_9.lifeFrameCount + 1
        if r0_9.lifeFrame <= r0_9.lifeFrameCount then
          Runtime:removeEventListener("enterFrame", r0_9)
          r0_9:removeSelf()
          r1_8 = nil
          return 
        end
        r0_9.bx = r0_9.bx + r0_9.moveX + r5_3.flowX
        r0_9.by = r0_9.by + r0_9.moveY + r5_3.flowY
        r0_9.moveX = r0_9.moveX - r0_9.accelerateX
        r0_9.moveY = r0_9.moveY - r0_9.accelerateY
        if r0_9.twist ~= nil then
          r0_9.x = r0_9.bx + r6_3[r0_9.twist.angle] * r0_9.twist.radius
          r0_9.y = r0_9.by + r7_3[r0_9.twist.angle] * r0_9.twist.radius
          r0_9.twist.angle = (r0_9.twist.angle + r0_9.twist.angleStep) % 360 + 1
        else
          r0_9.x = r0_9.bx
          r0_9.y = r0_9.by
        end
        local r2_9 = r0_9.alpha + r0_9.alphaStep
        if 1 < r2_9 or r2_9 < 0 then
          r0_9.alphaStep = -r0_9.alphaStep
          if r2_9 > 1 then
            r2_9 = 1
          else
            r2_9 = 0
          end
        end
        r0_9.alpha = r2_9
      end
      r1_8.isVisible = true
      if r1_8.type == r2_3 then
        if r5_3.useSequenceName ~= nil then
          r1_8[1]:setSequence(r5_3.useSequenceName)
        elseif type(r9_3[1]) == "table" then
          r1_8[1]:setSequence(r9_3[1].name)
        else
          r1_8[1]:setSequence(r9_3.name)
        end
        r1_8[1]:play()
      end
      Runtime:addEventListener("enterFrame", r1_8)
      return r1_8
    end
    function r5_3.setParticleImage(r0_11)
      -- line: [551, 553] id: 11
      r12_3(r0_11)
    end
    function r5_3.enterFrame(r0_12, r1_12)
      -- line: [558, 612] id: 12
      for r6_12 = 1, r5_3.particleMax, 1 do
        local r7_12 = math.random()
        local r8_12 = r5_3.baseX
        local r9_12 = r5_3.baseY
        local r10_12 = math.random(r5_3.sizeStart, r5_3.sizeEnd)
        local r11_12 = math.random(r5_3.moveXStart, r5_3.moveXEnd) * 0.1
        local r12_12 = math.random(r5_3.moveYStart, r5_3.moveYEnd) * 0.1
        local r13_12 = math.random(r5_3.accelerateXStart, r5_3.accelerateXEnd) * 0.01
        local r14_12 = math.random(r5_3.accelerateYStart, r5_3.accelerateYEnd) * 0.01
        local r15_12 = math.random(r5_3.lifetimeStart, r5_3.lifetimeEnd)
        local r16_12 = math.random(r5_3.alphaStepStart, r5_3.alphaStepEnd) * 0.01
        local r17_12 = math.random(r5_3.twistSpeedStart, r5_3.twistSpeedEnd)
        local r18_12 = math.random(r5_3.twistRadiusStart, r5_3.twistRadiusEnd)
        local r19_12 = math.floor(math.random(r5_3.twistAngleStart, r5_3.twistAngleEnd)) + 1
        local r20_12 = math.floor(math.random(r5_3.twistAngleStepStart, r5_3.twistAngleStepEnd))
        if r5_3.startRadius > 0 then
          local r21_12 = math.floor(math.atan2(r12_12, r11_12) * r4_3)
          if r21_12 < 1 then
            r21_12 = 360 + r21_12
          elseif r21_12 > 360 then
            r21_12 = r21_12 - 360
          end
          r8_12 = r8_12 + r6_3[r21_12] * r5_3.startRadius
          r9_12 = r9_12 + r7_3[r21_12] * r5_3.startRadius
        end
        local r21_12 = table.insert
        local r22_12 = r10_3
        local r23_12 = r14_3
        local r24_12 = {}
        r24_12.particle = {
          bx = r8_12,
          by = r9_12,
          generateParticle = r5_3.generateParticle,
          size = r10_12,
          color = {
            255,
            255,
            255,
            0
          },
        }
        r24_12.name = r1_12.time
        r24_12.moveX = r11_12
        r24_12.moveY = r12_12
        r24_12.lifeFrame = r15_12
        r24_12.startAlpha = 1
        r24_12.alphaStep = r16_12
        r24_12.twist = {
          speed = r17_12,
          radius = r18_12,
          angle = r19_12,
          angleStep = r20_12,
        }
        r21_12(r22_12, r23_12(r24_12))
      end
    end
    function r5_3.play()
      -- line: [617, 620] id: 13
      r11_3 = true
      Runtime:addEventListener("enterFrame", r5_3)
    end
    function r5_3.stop()
      -- line: [625, 628] id: 14
      r11_3 = false
      Runtime:removeEventListener("enterFrame", r5_3)
    end
    function r5_3.isPlay()
      -- line: [633, 635] id: 15
      return r11_3
    end
    function r5_3.clean()
      -- line: [640, 648] id: 16
      r5_3.stop()
      local r0_16 = nil
      for r4_16 = 1, #r10_3, 1 do
        Runtime:removeEventListener("enterFrame", r10_3[r4_16])
        display.remove(r10_3[r4_16])
        r10_3[r4_16] = nil
      end
    end
    (function()
      -- line: [359, 540] id: 10
      r5_3.parentObj = nil
      if r0_3.parentObj ~= nil then
        r5_3.parentObj = r0_3.parentObj
      end
      r5_3.particleMax = 1
      if r0_3.particleMax ~= nil then
        r5_3.particleMax = r0_3.particleMax
      end
      r8_3 = nil
      r9_3 = nil
      if r0_3.particleImage ~= nil then
        r12_3(r0_3.particleImage)
      end
      r5_3.baseX = 0
      r5_3.baseY = 0
      if r0_3.baseX ~= nil then
        r5_3.baseX = r0_3.baseX
      end
      if r0_3.baseY ~= nil then
        r5_3.baseY = r0_3.baseY
      end
      r5_3.startRadius = 0
      if r0_3.startRadius ~= nil then
        r5_3.startRadius = r0_3.startRadius
      end
      r5_3.flowX = 0
      r5_3.flowY = 0
      if r0_3.flowX ~= nil then
        r5_3.flowX = r0_3.flowX
      end
      if r0_3.flowY ~= nil then
        r5_3.flowY = r0_3.flowY
      end
      r5_3.accelerateX = 1
      r5_3.accelerateY = 1
      if r0_3.accelerateX ~= nil then
        r5_3.accelerateX = params.accelerateX
      end
      if r0_3.accelerateY ~= nil then
        r5_3.accelerateY = params.accelerateY
      end
      r5_3.alphaStep = 0.1
      if r0_3.alphaStep ~= nil then
        r5_3.alphaStep = r0_3.alphaStep
      end
      r5_3.lifeFrame = 1000
      if r0_3.lifeFrame ~= nil then
        r5_3.lifeFrame = r0_3.lifeFrame
      end
      r5_3.useSequenceName = nil
      if r0_3.useSequenceName ~= nil then
        r5_3.useSequenceName = r0_3.useSequenceName
      end
      r5_3.sizeStart = 10
      if r0_3.sizeStart ~= nil then
        r5_3.sizeStart = r0_3.sizeStart
      end
      r5_3.sizeEnd = 20
      if r0_3.sizeEnd ~= nil then
        r5_3.sizeEnd = r0_3.sizeEnd
      end
      r5_3.moveXStart = -10
      if r0_3.moveXStart ~= nil then
        r5_3.moveXStart = r0_3.moveXStart
      end
      r5_3.moveXEnd = 10
      if r0_3.moveXEnd ~= nil then
        r5_3.moveXEnd = r0_3.moveXEnd
      end
      r5_3.moveYStart = -10
      if r0_3.moveYStart ~= nil then
        r5_3.moveYStart = r0_3.moveYStart
      end
      r5_3.moveYEnd = 10
      if r0_3.moveYEnd ~= nil then
        r5_3.moveYEnd = r0_3.moveYEnd
      end
      r5_3.accelerateXStart = -6
      if r0_3.accelerateXStart ~= nil then
        r5_3.accelerateXStart = r0_3.accelerateXStart
      end
      r5_3.accelerateXEnd = 6
      if r0_3.accelerateXEnd ~= nil then
        r5_3.accelerateXEnd = r0_3.accelerateXEnd
      end
      r5_3.accelerateYStart = -6
      if r0_3.accelerateYStart ~= nil then
        r5_3.accelerateYStart = r0_3.accelerateYStart
      end
      r5_3.accelerateYEnd = 6
      if r0_3.accelerateYEnd ~= nil then
        r5_3.accelerateYEnd = r0_3.accelerateYEnd
      end
      r5_3.lifetimeStart = 1
      if r0_3.lifetimeStart ~= nil then
        r5_3.lifetimeStart = r0_3.lifetimeStart
      end
      r5_3.lifetimeEnd = 50
      if r0_3.lifetimeEnd ~= nil then
        r5_3.lifetimeEnd = r0_3.lifetimeEnd
      end
      r5_3.alphaStepStart = 1
      if r0_3.alphaStepStart ~= nil then
        r5_3.alphaStepStart = r0_3.alphaStepStart
      end
      r5_3.alphaStepEnd = 10
      if r0_3.alphaStepEnd ~= nil then
        r5_3.alphaStepEnd = r0_3.alphaStepEnd
      end
      r5_3.twistSpeedStart = 0
      if r0_3.twistSpeedStart ~= nil then
        r5_3.twistSpeedStart = r0_3.twistSpeedStart
      end
      r5_3.twistSpeedEnd = 10
      if r0_3.twistSpeedEnd ~= nil then
        r5_3.twistSpeedEnd = r0_3.twistSpeedEnd
      end
      r5_3.twistRadiusStart = 1
      if r0_3.twistRadiusStart ~= nil then
        r5_3.twistRadiusStart = r0_3.twistRadiusStart
      end
      r5_3.twistRadiusEnd = 5
      if r0_3.twistRadiusEnd ~= nil then
        r5_3.twistRadiusEnd = r0_3.twistRadiusEnd
      end
      r5_3.twistAngleStart = 0
      if r0_3.twistAngleStart ~= nil then
        r5_3.twistAngleStart = r0_3.twistAngleStart
      end
      r5_3.twistAngleEnd = 359
      if r0_3.twistAngleEnd ~= nil then
        r5_3.twistAngleEnd = r0_3.twistAngleEnd
      end
      r5_3.twistAngleStepStart = 0
      if r0_3.twistAngleStepStart ~= nil then
        r5_3.twistAngleStepStart = r0_3.twistAngleStepStart
      end
      r5_3.twistAngleStepEnd = 10
      if r0_3.twistAngleStepEnd ~= nil then
        r5_3.twistAngleStepEnd = r0_3.twistAngleStepEnd
      end
      if r0_3.generateParticle ~= nil then
        r5_3.generateParticle = r0_3.generateParticle
      end
      local r0_10 = math.pi / 180
      local r1_10 = nil
      r6_3 = {}
      r7_3 = {}
      for r5_10 = 0, 360, 1 do
        table.insert(r6_3, math.cos(r0_10 * r5_10))
        table.insert(r7_3, math.sin(r0_10 * r5_10))
      end
      r10_3 = {}
      r11_3 = false
    end)()
    return r5_3
  end,
}
