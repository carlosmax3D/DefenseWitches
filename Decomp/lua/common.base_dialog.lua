-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local function r0_0(r0_1)
  -- line: [6, 6] id: 1
  return "data/dialog/" .. r0_1 .. ".png"
end
local function r1_0(r0_2)
  -- line: [8, 8] id: 2
  return "data/option/" .. r0_2 .. ".png"
end
local r2_0 = require("common.sprite_loader").new({
  imageSheet = "common.sprites.sprite_parts04",
})
local r3_0 = nil
local r4_0 = nil
local function r5_0(r0_3)
  -- line: [20, 22] id: 3
  r3_0 = util.MakeMat(r0_3)
end
local function r10_0(r0_8, r1_8, r2_8, r3_8)
  -- line: [202, 221] id: 8
  local r4_8 = display.newRect(r0_8, 0, 0, _G.Width, _G.Height)
  r4_8:setFillColor(r1_8[1], r1_8[2], r1_8[3])
  r4_8.alpha = 0
  r0_8.mask = r4_8
  r0_8:insert(1, r4_8)
  transition.to(r4_8, {
    alpha = r1_8[4],
    time = r2_8,
    transition = easing.linear,
    onComplete = function()
      -- line: [215, 219] id: 9
      if r3_8 ~= nil then
        r3_8()
      end
    end,
  })
end
return {
  Create = function(r0_4, r1_4, r2_4, r3_4, r4_4, r5_4)
    -- line: [36, 130] id: 4
    if r0_4 == nil then
      return nil
    end
    r5_0(r0_4)
    local r6_4 = display.newGroup()
    local r7_4 = 24
    local r8_4 = 40
    local r9_4 = 16
    local r10_4 = 16
    local r11_4 = 32
    local r12_4 = 0
    if r4_4 == false then
      r8_4 = 0
      r11_4 = 0
    end
    if r5_4 == false then
      r7_4 = 0
      r10_4 = 0
    end
    local r13_4 = math.ceil((r1_4 - r7_4 * 2) / r9_4)
    local r14_4 = math.ceil((r2_4 - r8_4 * 2) / r9_4)
    local r15_4 = r7_4 * 2 + r13_4 * r9_4
    local r16_4 = r8_4 * 2 + r14_4 * r9_4
    local r17_4 = display.newRect(r6_4, r10_4, r11_4, r15_4 - r10_4 * 2, r16_4 - r11_4 * 2)
    if util.IsContainedTable(r3_4, "direction") == true and util.IsContainedTable(r3_4, "color") == true then
      r17_4:setFillColor(graphics.newGradient(r3_4.color[1], r3_4.color[2], r3_4.direction))
      if util.IsContainedTable(r3_4, "alpha") == true then
        r17_4.alpha = r3_4.alpha
      end
    else
      r17_4:setFillColor(r3_4[1], r3_4[2], r3_4[3])
      if #r3_4 > 3 then
        r17_4.alpha = r3_4[4]
      end
    end
    if r4_4 == true and r5_4 == true then
      r2_0.CreateImage(r6_4, r2_0.sequenceNames.BoxDialogFrame, r2_0.frameDefines.BoxDialogFrameTopLeft, 0, 0)
      r2_0.CreateImage(r6_4, r2_0.sequenceNames.BoxDialogFrame, r2_0.frameDefines.BoxDialogFrameTopRight, r15_4 - r7_4, 0)
      r2_0.CreateImage(r6_4, r2_0.sequenceNames.BoxDialogFrame, r2_0.frameDefines.BoxDialogFrameBottomLeft, 0, r16_4 - r8_4)
      r2_0.CreateImage(r6_4, r2_0.sequenceNames.BoxDialogFrame, r2_0.frameDefines.BoxDialogFrameBottomRight, r15_4 - r7_4, r16_4 - r8_4)
    end
    local r18_4 = nil
    if r4_4 == true then
      local r19_4 = r7_4
      for r23_4 = 1, r13_4, 1 do
        r2_0.CreateImage(r6_4, r2_0.sequenceNames.BoxDialogFrame, r2_0.frameDefines.BoxDialogFrameTopCenter, r19_4, 0)
        r2_0.CreateImage(r6_4, r2_0.sequenceNames.BoxDialogFrame, r2_0.frameDefines.BoxDialogFrameBottomCenter, r19_4, r16_4 - r8_4)
        r19_4 = r19_4 + r9_4
      end
    end
    if r5_4 == true then
      local r19_4 = r8_4
      for r23_4 = 1, r14_4, 1 do
        r2_0.CreateImage(r6_4, r2_0.sequenceNames.BoxDialogFrame, r2_0.frameDefines.BoxDialogFrameMiddleLeft, 0, r19_4)
        r2_0.CreateImage(r6_4, r2_0.sequenceNames.BoxDialogFrame, r2_0.frameDefines.BoxDialogFrameMiddleRight, r15_4 - r7_4, r19_4)
        r19_4 = r19_4 + r9_4
      end
    end
    r0_4:insert(r6_4)
    r6_4:setReferencePoint(display.CenterReferencePoint)
    r6_4.x = _G.Width * 0.5
    r6_4.y = _G.Height * 0.5
    return r6_4
  end,
  CreateBlueGradientBg = function(r0_5)
    -- line: [135, 153] id: 5
    if r0_5 == nil then
      return nil
    end
    r5_0(r0_5)
    local r1_5 = display.newGroup()
    for r5_5 = 0, 960, 16 do
      util.LoadParts(r1_5, r0_0("clear_window01"), r5_5, 0)
      util.LoadParts(r1_5, r0_0("clear_window02"), r5_5, 128)
      util.LoadParts(r1_5, r0_0("clear_window03"), r5_5, 256)
      util.LoadParts(r1_5, r0_0("clear_window04"), r5_5, 384)
      util.LoadParts(r1_5, r0_0("clear_window05"), r5_5, 512)
    end
    return r1_5
  end,
  CreateCloseButton = function(r0_6, r1_6, r2_6, r3_6)
    -- line: [158, 170] id: 6
    if r0_6 == nil then
      return nil
    end
    return util.LoadBtn({
      rtImg = r0_6,
      fname = r1_0("close"),
      cx = r1_6,
      cy = r2_6,
      func = r3_6,
    })
  end,
  CreateDialogLine = function(r0_7, r1_7, r2_7)
    -- line: [179, 196] id: 7
    if r0_7 == nil then
      return nil
    end
    local r3_7 = display.newGroup()
    local r4_7 = nil
    for r9_7 = 0, math.ceil(r2_7 / 64) - 1, 1 do
      r2_0.CreateImage(r3_7, r2_0.sequenceNames.DialogLine, r2_0.frameDefines.DialogLine, r9_7 * 64, 0)
    end
    r0_7:insert(r3_7)
    r3_7:setReferencePoint(display.CenterReferencePoint)
    r3_7.x = r0_7.width * 0.5
    r3_7.y = r1_7
    return r3_7
  end,
  FadeInMask = r10_0,
  FadeOutMask = function(r0_10, r1_10, r2_10)
    -- line: [226, 242] id: 10
    if r0_10 == nil or r0_10.mask == nil then
      return 
    end
    transition.to(r0_10.mask, {
      alpha = 0,
      time = r1_10,
      transition = easing.linear,
      onComplete = function()
        -- line: [236, 240] id: 11
        if r2_10 ~= nil then
          r2_10()
        end
      end,
    })
  end,
  ZoomInEffect = function(r0_12, r1_12, r2_12)
    -- line: [247, 288] id: 12
    if r0_12 == nil or r1_12 == nil then
      return nil
    end
    r0_12.isVisible = true
    local r3_12 = {
      xScale = 1,
      yScale = 1,
      time = 300,
      delay = 200,
      transition = easing.outExpo,
    }
    if r2_12 ~= nil then
      function r3_12.onComplete()
        -- line: [265, 267] id: 13
        r2_12()
      end
    end
    r1_12:setReferencePoint(display.CenterReferencePoint)
    r1_12.alpha = 0
    r1_12.xScale = 0.1
    r1_12.yScale = 0.1
    r1_12.isVisible = true
    r10_0(r0_12, {
      0,
      0,
      0,
      0.5
    }, 500)
    transition.to(r1_12, r3_12)
    transition.to(r1_12, {
      alpha = 1,
      time = 200,
      delay = 300,
      transition = easing.outExpo,
    })
  end,
  SlideInLeftEffect = function(r0_14, r1_14, r2_14)
    -- line: [293, 330] id: 14
    if r0_14 == nil or r1_14 == nil then
      return nil
    end
    local function r3_14()
      -- line: [300, 304] id: 15
      if r2_14 ~= nil then
        r2_14()
      end
    end
    r0_14.isVisible = true
    r1_14.isVisible = true
    r1_14.alpha = 0
    r1_14.x = -r1_14.width
    r10_0(r0_14, {
      0,
      0,
      0,
      0.5
    }, 500)
    transition.to(r1_14, {
      x = 0,
      time = 200,
      delay = 200,
      transition = easing.outExpo,
      onComplete = function()
        -- line: [320, 322] id: 16
        r3_14()
      end,
    })
    transition.to(r1_14, {
      alpha = 1,
      time = 150,
      delay = 220,
      transition = easing.linear,
    })
  end,
  SlideOutRightEffect = function(r0_17, r1_17, r2_17)
    -- line: [335, 371] id: 17
    if r0_17 == nil or r1_17 == nil then
      return nil
    end
    local r3_17 = r0_17.mask
    transition.to(r1_17, {
      x = r1_17.width,
      alpha = 0,
      time = 200,
      transition = easing.inExpo,
      onComplete = function()
        -- line: [349, 354] id: 18
        if r3_17 ~= nil and r2_17 ~= nil then
          r2_17()
        end
      end,
    })
    if r3_17 == nil then
      return 
    end
    transition.to(r3_17, {
      alpha = 0,
      time = 200,
      delay = 150,
      onComplete = function()
        -- line: [365, 369] id: 19
        if r2_17 ~= nil then
          r2_17()
        end
      end,
    })
  end,
  SlideInUpEffect = function(r0_20, r1_20, r2_20, r3_20)
    -- line: [375, 391] id: 20
    r0_20:setReferencePoint(display.CenterReferencePoint)
    r0_20.alpha = 0
    r0_20.y = r1_20.y
    transition.to(r0_20, {
      time = 400,
      alpha = 1,
      y = r2_20.y,
      transition = easing.outBack,
      onComplete = function()
        -- line: [385, 389] id: 21
        if r3_20 ~= nil then
          r3_20()
        end
      end,
    })
  end,
  SlideOutDownEffect = function(r0_22, r1_22, r2_22)
    -- line: [395, 411] id: 22
    transition.to(r0_22, {
      time = 400,
      y = r1_22.y,
      alpha = 0,
      transition = easing.inBack,
      onComplete = function()
        -- line: [401, 409] id: 23
        if r3_0 ~= nil then
          display.remove(r3_0)
        end
        if r2_22 ~= nil then
          r2_22()
        end
      end,
    })
  end,
}
