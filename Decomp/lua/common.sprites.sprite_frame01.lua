-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r1_0 = {
  BoxFrame = "frame01" .. "BoxFrame",
}
local r2_0 = {
  BoxFrameTopLeft = 1,
  BoxFrameTopCenter = 2,
  BoxFrameTopRight = 3,
  BoxFrameMiddleLeft = 4,
  BoxFrameMiddleRight = 5,
  BoxFrameBottomLeft = 6,
  BoxFrameBottomCenter = 7,
  BoxFrameBottomRight = 8,
}
return {
  SequenceNames = r1_0,
  FrameDefines = r2_0,
  LoadImageSheet = function()
    -- line: [30, 46] id: 1
    return graphics.newImageSheet("data/sprites/frame01.png", {
      frames = {
        {
          x = 0,
          y = 0,
          width = 12,
          height = 12,
        },
        {
          x = 12,
          y = 0,
          width = 1,
          height = 12,
        },
        {
          x = 20,
          y = 0,
          width = 12,
          height = 12,
        },
        {
          x = 0,
          y = 12,
          width = 12,
          height = 1,
        },
        {
          x = 20,
          y = 12,
          width = 12,
          height = 1,
        },
        {
          x = 0,
          y = 20,
          width = 12,
          height = 12,
        },
        {
          x = 12,
          y = 20,
          width = 1,
          height = 12,
        },
        {
          x = 20,
          y = 20,
          width = 12,
          height = 12,
        }
      },
      sheetContentWidth = 32,
      sheetContentHeight = 32,
    })
  end,
  GetSpriteFrameInfo = function(r0_2)
    -- line: [51, 72] id: 2
    if r0_2 == r1_0.BoxDialogFrame then
      return {
        name = r1_0.BoxFrame,
        frames = {
          r2_0.BoxFrameTopLeft,
          r2_0.BoxFrameTopCenter,
          r2_0.BoxFrameTopRight,
          r2_0.BoxFrameMiddleLeft,
          r2_0.BoxFrameMiddleCengter,
          r2_0.BoxFrameMiddleRight,
          r2_0.BoxFrameBottomLeft,
          r2_0.BoxFrameBottomCenter,
          r2_0.BoxFrameBottomRight
        },
        start = 1,
        count = 9,
      }
    end
    return nil
  end,
  IsContain = function(r0_3)
    -- line: [77, 83] id: 3
    if r0_3 == r1_0.BoxFrame then
      return true
    end
    return false
  end,
}
