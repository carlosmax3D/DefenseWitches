-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
return {
  new = function(r0_1)
    -- line: [7, 356] id: 1
    local r1_1 = {
      baseX = 0,
      baseY = 0,
      gStart = 0,
      gEnd = 0,
      rangeXStart = 0,
      rangeXEnd = 0,
      rangeYStart = 0,
      rangeYEnd = 0,
      sizeStart = 0,
      sizeEnd = 0,
      colors = {},
      alphaStart = 0,
      alphaEnd = 0,
      particleMax = 1,
      useSequenceName = nil,
    }
    local r2_1 = nil
    local r3_1 = nil
    local r4_1 = 2
    local function r5_1(r0_2)
      -- line: [59, 80] id: 2
      if r0_2 == nil or r0_2.file == nil or r0_2.options == nil then
        return 
      end
      r2_1 = graphics.newImageSheet(r0_2.file, r0_2.options)
      if r2_1 == nil then
        DebugPrint("image sheet生成エラー")
        return 
      end
      if r0_2.sequence ~= nil then
        if type(r0_2.sequence) == "table" then
          r3_1 = r0_2.sequence
        else
          r3_1 = {
            r0_2.sequence
          }
        end
      end
    end
    local function r6_1(r0_3)
      -- line: [90, 135] id: 3
      if r0_3 == nil then
        return 
      end
      local r1_3 = 1
      if r0_3.size ~= nil then
        r1_3 = r0_3.size
      end
      local r2_3 = display.newGroup()
      local r3_3 = nil
      if r2_1 ~= nil then
        r3_3 = display.newSprite(r2_3, r2_1, r3_1)
        r2_3.type = r4_1
        r2_3.width = r1_3
        r2_3.height = r1_3
      else
        r3_3 = display.newCircle(r2_3, 0, 0, r1_3)
        r2_3.type = ParticleTypeShape
      end
      r2_3.isVisible = false
      if r0_3.bx ~= nil then
        r2_3.x = r0_3.bx
      end
      if r0_3.by ~= nil then
        r2_3.y = r0_3.by
      end
      if r0_3.color ~= nil and type(r0_3.color) == "table" and 2 < #r0_3.color then
        r3_3:setFillColor(r0_3.color[1], r0_3.color[2], r0_3.color[3])
        if #r0_3.color == 4 then
          r3_3.aplha = r0_3.color[4]
        end
      end
      return r2_3
    end
    local function r7_1(r0_4)
      -- line: [140, 211] id: 4
      local r1_4 = r6_1(r0_4.particle)
      if r0_4.g ~= nil then
        r1_4.g = r0_4.g
      else
        r1_4.g = 0.98
      end
      if r0_4.dx ~= nil then
        r1_4.dx = r0_4.dx
      else
        r1_4.dx = 0
      end
      if r0_4.dy ~= nil then
        r1_4.dy = r0_4.dy
      else
        r1_4.dy = 0
      end
      if r0_4.dalpha ~= nil then
        r1_4.dalpha = r0_4.dalpha
      else
        r1_4.dalpha = 0.1
      end
      function r1_4.enterFrame(r0_5, r1_5)
        -- line: [167, 194] id: 5
        r0_5.dy = r0_5.dy + r0_5.g
        r0_5.x = r0_5.x + r0_5.dx
        r0_5.y = r0_5.y + r0_5.dy
        local r2_5 = r0_5.alpha - r0_5.dalpha
        if 300 < r0_5.y or r2_5 < 0 then
          Runtime:removeEventListener("enterFrame", r0_5)
          r0_5:removeSelf()
        end
        if r2_5 > 0 then
          r0_5.alpha = r2_5
        end
      end
      r1_4.isVisible = true
      if r1_4.type == r4_1 then
        if r1_1.useSequenceName ~= nil then
          r1_4[1]:setSequence(r1_1.useSequenceName)
        elseif type(r3_1[1]) == "table" then
          r1_4[1]:setSequence(r3_1[1].name)
        else
          r1_4[1]:setSequence(r3_1.name)
        end
        r1_4[1]:play()
      end
      Runtime:addEventListener("enterFrame", r1_4)
    end
    function r1_1.enterFrame(r0_7)
      -- line: [300, 326] id: 7
      for r5_7 = 1, r1_1.particleMax, 1 do
        r7_1({
          particle = {
            bx = r1_1.baseX,
            by = r1_1.baseY,
            size = math.random(r1_1.sizeStart, r1_1.sizeEnd),
            color = r1_1.colors[math.floor(math.random(1, #r1_1.colors))],
          },
          dx = math.random(r1_1.rangeXStart, r1_1.rangeXEnd),
          dy = math.random(r1_1.rangeYStart, r1_1.rangeYEnd),
          g = math.random(r1_1.gStart, r1_1.gEnd) * 0.1,
          dalpha = math.random(r1_1.alphaStart, r1_1.alphaEnd) * 0.01,
        })
      end
    end
    function r1_1.setPos(r0_8, r1_8)
      -- line: [331, 334] id: 8
      r1_1.baseX = r0_8
      r1_1.baseY = r1_8
    end
    function r1_1.play()
      -- line: [339, 341] id: 9
      Runtime:addEventListener("enterFrame", r1_1)
    end
    function r1_1.stop()
      -- line: [346, 348] id: 10
      Runtime:removeEventListener("enterFrame", r1_1)
    end
    (function()
      -- line: [216, 291] id: 6
      local r0_6 = nil	-- notice: implicit variable refs by block#[0]
      r2_1 = r0_6
      r3_1 = nil
      r0_6 = r0_1.particleImage
      if r0_6 ~= nil then
        r5_1(r0_1.particleImage)
      end
      r1_1.particleMax = 1
      r0_6 = r0_1.particleMax
      if r0_6 ~= nil then
        r0_6 = r1_1
        r0_6.particleMax = r0_1.particleMax
      end
      r1_1.baseX = 0
      r0_6 = r0_1.baseX
      if r0_6 ~= nil then
        r0_6 = r1_1
        r0_6.baseX = r0_1.baseX
      end
      r1_1.baseY = 0
      r0_6 = r0_1.baseY
      if r0_6 ~= nil then
        r0_6 = r1_1
        r0_6.baseY = r0_1.baseY
      end
      r1_1.rangeXStart = -10
      r0_6 = r0_1.rangeXStart
      if r0_6 ~= nil then
        r0_6 = r1_1
        r0_6.rangeXStart = r0_1.rangeXStart
      end
      r1_1.rangeXEnd = 10
      r0_6 = r0_1.rangeXEnd
      if r0_6 ~= nil then
        r0_6 = r1_1
        r0_6.rangeXEnd = r0_1.rangeXEnd
      end
      r1_1.rangeYStart = -10
      r0_6 = r0_1.rangeYStart
      if r0_6 ~= nil then
        r0_6 = r1_1
        r0_6.rangeYStart = r0_1.rangeYStart
      end
      r1_1.rangeYEnd = 0
      r0_6 = r0_1.rangeYEnd
      if r0_6 ~= nil then
        r0_6 = r1_1
        r0_6.rangeYEnd = r0_1.rangeYEnd
      end
      r1_1.gStart = 1
      r0_6 = r0_1.gStart
      if r0_6 ~= nil then
        r0_6 = r1_1
        r0_6.gStart = r0_1.gStart
      end
      r1_1.gEnd = 10
      r0_6 = r0_1.gEnd
      if r0_6 ~= nil then
        r0_6 = r1_1
        r0_6.gEnd = r0_1.gEnd
      end
      r1_1.sizeStart = 5
      r0_6 = r0_1.sizeStart
      if r0_6 ~= nil then
        r0_6 = r1_1
        r0_6.sizeStart = r0_1.sizeStart
      end
      r1_1.sizeEnd = 100
      r0_6 = r0_1.sizeEnd
      if r0_6 ~= nil then
        r0_6 = r1_1
        r0_6.sizeEnd = r0_1.sizeEnd
      end
      r0_6 = r1_1
      r0_6.colors = {
        {
          255,
          255,
          255,
          1
        }
      }
      r0_6 = r0_1.colors
      if r0_6 ~= nil then
        r0_6 = r1_1
        r0_6.colors = r0_1.colors
      end
      r1_1.alphaStart = 1
      r0_6 = r0_1.alphaStart
      if r0_6 ~= nil then
        r0_6 = r1_1
        r0_6.alphaStart = r0_1.alphaStart
      end
      r1_1.alphaEnd = 9
      r0_6 = r0_1.alphaEnd
      if r0_6 ~= nil then
        r0_6 = r1_1
        r0_6.alphaEnd = r0_1.alphaEnd
      end
      r1_1.useSequenceName = nil
      r0_6 = r0_1.useSequenceName
      if r0_6 ~= nil then
        r0_6 = r1_1
        r0_6.useSequenceName = r0_1.useSequenceName
      end
    end)()
    return r1_1
  end,
}
