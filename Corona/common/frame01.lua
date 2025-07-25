-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
return {
  new = function(r0_1)
    -- line: [28, 342] id: 1
    local r1_1 = {
      obj = nil,
    }
    local r2_1 = {
      3,
      3,
      26,
      4
    }
    local r3_1 = {
      3,
      23,
      26,
      4
    }
    local r4_1 = {
      3,
      7,
      24,
      16
    }
    local r5_1 = require("common.sprite_loader").new({
      imageSheet = "common.sprites.sprite_frame01",
    })
    local function r6_1(r0_2, r1_2)
      -- line: [55, 60] id: 2
      if r0_2 ~= nil then
        return r0_2
      end
      return r1_2
    end
    local function r7_1(r0_3, r1_3, r2_3)
      -- line: [65, 108] id: 3
      if util.IsContainedTable(r0_3, "obj") == false or util.IsContainedTable(r0_3.obj, "frame") == false or util.IsContainedTable(r0_3.obj, "background") == false or util.IsContainedTable(r0_3.obj.frame, "topLeft") == false or util.IsContainedTable(r0_3.obj.frame, "topCenter") == false or util.IsContainedTable(r0_3.obj.frame, "topRight") == false or util.IsContainedTable(r0_3.obj.frame, "middleLeft") == false or util.IsContainedTable(r0_3.obj.frame, "middleRight") == false or util.IsContainedTable(r0_3.obj.frame, "bottomLeft") == false or util.IsContainedTable(r0_3.obj.frame, "bottomCenter") == false or util.IsContainedTable(r0_3.obj.frame, "bottomRight") == false or util.IsContainedTable(r0_3.obj.background, "body") == false or util.IsContainedTable(r0_3.obj.background, "header") == false or util.IsContainedTable(r0_3.obj.background, "footer") == false or r1_3 == nil or r2_3 == nil then
        return 
      end
      local r3_3 = r1_3 - r0_3.obj.frame.topLeft.width - r0_3.obj.frame.topRight.width
      local r4_3 = r2_3 - r0_3.obj.frame.topLeft.height - r0_3.obj.frame.bottomLeft.height
      r0_3.obj.frame.topCenter.xScale = r3_3
      r0_3.obj.frame.topRight.x = r0_3.obj.frame.topLeft.width + r3_3
      r0_3.obj.frame.middleLeft.yScale = r4_3
      r0_3.obj.frame.middleRight.x = r0_3.obj.frame.topRight.x
      r0_3.obj.frame.middleRight.yScale = r4_3
      r0_3.obj.frame.bottomLeft.y = r0_3.obj.frame.topLeft.height + r4_3
      r0_3.obj.frame.bottomCenter.y = r0_3.obj.frame.bottomLeft.y
      r0_3.obj.frame.bottomCenter.xScale = r3_3
      r0_3.obj.frame.bottomRight.x = r0_3.obj.frame.topRight.x
      r0_3.obj.frame.bottomRight.y = r0_3.obj.frame.bottomLeft.y
      r0_3.obj.background.header.xScale = r1_3 - 32 - r2_1[3]
      r0_3.obj.background.body.xScale = r1_3 - 32 - r4_1[3]
      r0_3.obj.background.body.yScale = r2_3 - 32 - r4_1[4]
      r0_3.obj.background.footer.y = r2_3 - 32 - r3_1[2]
      r0_3.obj.background.footer.xScale = r1_3 - 32 - r3_1[3]
    end
    local function r8_1(r0_4, r1_4)
      -- line: [113, 121] id: 4
      if r0_4 == nil or r1_4 == nil then
        return 
      end
      r0_4:setFillColor(r1_4[1], r1_4[2], r1_4[3])
      r0_4.alpha = r1_4[4]
    end
    local function r9_1(r0_5, r1_5)
      -- line: [126, 135] id: 5
      if util.IsContainedTable(r0_5, "obj") == false or r1_5 == nil or util.IsContainedTable(r0_5.obj, "background") == nil or util.IsContainedTable(r0_5.obj.background, "header") == false then
        return 
      end
      r8_1(r0_5.obj.background.header, r1_5)
    end
    local function r10_1(r0_6, r1_6)
      -- line: [140, 149] id: 6
      if util.IsContainedTable(r0_6, "obj") == false or r1_6 == nil or util.IsContainedTable(r0_6.obj, "background") == nil or util.IsContainedTable(r0_6.obj.background, "body") == false then
        return 
      end
      r8_1(r0_6.obj.background.body, r1_6)
    end
    local function r11_1(r0_7, r1_7)
      -- line: [154, 163] id: 7
      if util.IsContainedTable(r0_7, "obj") == false or r1_7 == nil or util.IsContainedTable(r0_7.obj, "background") == nil or util.IsContainedTable(r0_7.obj.background, "footer") == false then
        return 
      end
      r8_1(r0_7.obj.background.footer, r1_7)
    end
    local function r12_1(r0_8, r1_8, r2_8, r3_8, r4_8, r5_8, r6_8)
      -- line: [168, 282] id: 8
      if r0_8 == nil or r1_8 == nil or r2_8 == nil or r3_8 == nil or r4_8 == nil or r5_8 == nil or r6_8 == nil then
        return nil
      end
      local r7_8 = display.newGroup()
      local r8_8 = display.newRect(r7_8, r4_1[1], r4_1[2], 1, 1)
      r8_8:setReferencePoint(display.TopLeftReferencePoint)
      r8_8.xScale = r2_8 - 32 - r4_1[3]
      r8_8.yScale = r3_8 - 32 - r4_1[4]
      r8_8:setFillColor(r6_8[1], r6_8[2], r6_8[3])
      r8_8.alpha = r6_8[4]
      local r9_8 = display.newRect(r7_8, r2_1[1], r2_1[2], 1, 1)
      r9_8:setReferencePoint(display.TopLeftReferencePoint)
      r9_8.xScale = r2_8 - 32 - r2_1[3]
      r9_8.yScale = r2_1[4]
      r9_8:setFillColor(r4_8[1], r4_8[2], r4_8[3])
      r9_8.alpha = r4_8[4]
      local r10_8 = display.newRect(r7_8, r3_1[1], r3_8 - 32 - r3_1[2], 1, 1)
      r10_8:setReferencePoint(display.TopLeftReferencePoint)
      r10_8.xScale = r9_8.xScale
      r10_8.yScale = r3_1[4]
      r10_8:setFillColor(r5_8[1], r5_8[2], r5_8[3])
      r10_8.alpha = r5_8[4]
      local r11_8 = r5_1.CreateImage(r7_8, r5_1.sequenceNames.BoxFrame, r5_1.frameDefines.BoxFrameTopLeft, 0, 0)
      local r12_8 = display.newGroup()
      local r13_8 = r5_1.CreateImage(r12_8, r5_1.sequenceNames.BoxFrame, r5_1.frameDefines.BoxFrameTopCenter, 0, 0)
      local r14_8 = r5_1.CreateImage(r7_8, r5_1.sequenceNames.BoxFrame, r5_1.frameDefines.BoxFrameTopRight, 0, 0)
      r7_8:insert(r12_8)
      local r15_8 = display.newGroup()
      local r16_8 = r5_1.CreateImage(r15_8, r5_1.sequenceNames.BoxFrame, r5_1.frameDefines.BoxFrameMiddleLeft, 0, 0)
      local r17_8 = display.newGroup()
      local r18_8 = nil
      r5_1.CreateImage(r17_8, r5_1.sequenceNames.BoxFrame, r5_1.frameDefines.BoxFrameMiddleRight, 0, 0)
      r7_8:insert(r15_8)
      r7_8:insert(r17_8)
      local r19_8 = r5_1.CreateImage(r7_8, r5_1.sequenceNames.BoxFrame, r5_1.frameDefines.BoxFrameBottomLeft, 0, 0)
      local r20_8 = display.newGroup()
      local r21_8 = r5_1.CreateImage(r20_8, r5_1.sequenceNames.BoxFrame, r5_1.frameDefines.BoxFrameBottomCenter, 0, 0)
      local r22_8 = r5_1.CreateImage(r7_8, r5_1.sequenceNames.BoxFrame, r5_1.frameDefines.BoxFrameBottomRight, 0, 0)
      r7_8:insert(r20_8)
      local r23_8 = r2_8 - r11_8.width - r14_8.width
      local r24_8 = r3_8 - r11_8.height - r19_8.height
      r11_8:setReferencePoint(display.TopLeftReferencePoint)
      r12_8:setReferencePoint(display.TopLeftReferencePoint)
      r14_8:setReferencePoint(display.TopLeftReferencePoint)
      r11_8.x = 0
      r11_8.y = 0
      r12_8.x = r11_8.width
      r12_8.y = 0
      r12_8.xScale = r23_8
      r14_8.x = r11_8.width + r23_8
      r14_8.y = 0
      r15_8:setReferencePoint(display.TopLeftReferencePoint)
      r17_8:setReferencePoint(display.TopLeftReferencePoint)
      r15_8.x = 0
      r15_8.y = r11_8.y + r11_8.height
      r15_8.yScale = r24_8
      r17_8.x = r14_8.x
      r17_8.y = r15_8.y
      r17_8.yScale = r24_8
      r19_8:setReferencePoint(display.TopLeftReferencePoint)
      r20_8:setReferencePoint(display.TopLeftReferencePoint)
      r22_8:setReferencePoint(display.TopLeftReferencePoint)
      r19_8.x = 0
      r19_8.y = r11_8.height + r24_8
      r20_8.x = r12_8.x
      r20_8.y = r19_8.y
      r20_8.xScale = r23_8
      r22_8.x = r14_8.x
      r22_8.y = r19_8.y
      r7_8:setReferencePoint(display.TopLeftReferencePoint)
      r7_8.x = r0_8
      r7_8.y = r1_8
      r7_8.frame = {}
      r7_8.background = {}
      r7_8.frame.topLeft = r11_8
      r7_8.frame.topCenter = r12_8
      r7_8.frame.topRight = r14_8
      r7_8.frame.middleLeft = r15_8
      r7_8.frame.middleRight = r17_8
      r7_8.frame.bottomLeft = r19_8
      r7_8.frame.bottomCenter = r20_8
      r7_8.frame.bottomRight = r22_8
      r7_8.background.body = r8_8
      r7_8.background.header = r9_8
      r7_8.background.footer = r10_8
      return r7_8
    end
    function r1_1.SetSize(r0_10, r1_10, r2_10)
      -- line: [311, 313] id: 10
      r7_1(r0_10, r1_10, r2_10)
    end
    function r1_1.SetHeaderColor(r0_11, r1_11)
      -- line: [318, 320] id: 11
      r9_1(r0_11, r1_11)
    end
    function r1_1.SetBodyColor(r0_12, r1_12)
      -- line: [325, 327] id: 12
      r10_1(r0_12, r1_12)
    end
    function r1_1.SetFooterColor(r0_13, r1_13)
      -- line: [332, 334] id: 13
      r11_1(r0_13, r1_13)
    end
    (function()
      -- line: [287, 303] id: 9
      local r0_9 = r6_1(r0_1.x, 0)
      local r1_9 = r6_1(r0_1.y, 0)
      local r2_9 = r6_1(r0_1.width, 0)
      local r3_9 = r6_1(r0_1.height, 0)
      local r4_9 = r6_1(r0_1.headerColor, {
        0,
        0,
        0,
        0
      })
      local r5_9 = r6_1(r0_1.footerColor, {
        0,
        0,
        0,
        0
      })
      local r6_9 = r6_1(r0_1.bodyColor, {
        0,
        0,
        0,
        0
      })
      r1_1.obj = nil
      local r7_9 = r12_1(r0_9, r1_9, r2_9, r3_9, r4_9, r5_9, r6_9)
      if r0_1.rootGroup ~= nil then
        r0_1.rootGroup:insert(r7_9)
      end
      r1_1.obj = r7_9
    end)()
    return r1_1
  end,
}
