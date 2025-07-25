-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = "parts04"
local r1_0 = {
  BoxDialogFrame = r0_0 .. "BoxDialogFrame",
  BorderDialogFrame = r0_0 .. "BorderDialogFrame",
  DialogLine = r0_0 .. "DialogLine",
}
local r2_0 = {
  BoxDialogFrameTopLeft = 1,
  BoxDialogFrameTopCenter = 2,
  BoxDialogFrameTopRight = 3,
  BoxDialogFrameMiddleLeft = 4,
  BoxDialogFrameMiddleCengter = 5,
  BoxDialogFrameMiddleRight = 6,
  BoxDialogFrameBottomLeft = 7,
  BoxDialogFrameBottomCenter = 8,
  BoxDialogFrameBottomRight = 9,
  BorderDialogFrameTopCenter = 10,
  BorderDialogFrameBottomCenter = 11,
  DialogLine = 12,
}
return {
  SequenceNames = r1_0,
  FrameDefines = r2_0,
  LoadImageSheet = function()
    -- line: [40, 64] id: 1
    return graphics.newImageSheet("data/sprites/parts04.png", {
      frames = {
        {
          x = 0,
          y = 0,
          width = 24,
          height = 40,
        },
        {
          x = 24,
          y = 0,
          width = 16,
          height = 40,
        },
        {
          x = 40,
          y = 0,
          width = 24,
          height = 40,
        },
        {
          x = 0,
          y = 40,
          width = 24,
          height = 16,
        },
        {
          x = 24,
          y = 40,
          width = 16,
          height = 16,
        },
        {
          x = 40,
          y = 40,
          width = 24,
          height = 16,
        },
        {
          x = 0,
          y = 56,
          width = 24,
          height = 40,
        },
        {
          x = 24,
          y = 56,
          width = 16,
          height = 40,
        },
        {
          x = 40,
          y = 56,
          width = 24,
          height = 40,
        },
        {
          x = 8,
          y = 8,
          width = 16,
          height = 24,
        },
        {
          x = 8,
          y = 64,
          width = 16,
          height = 24,
        },
        {
          x = 64,
          y = 0,
          width = 64,
          height = 3,
        }
      },
      sheetContentWidth = 128,
      sheetContentHeight = 128,
    })
  end,
  GetSpriteFrameInfo = function(r0_2)
    -- line: [69, 109] id: 2
    if r0_2 == r1_0.BoxDialogFrame then
      return {
        name = r1_0.BoxDialogFrame,
        frames = {
          r2_0.BoxDialogFrameTopLeft,
          r2_0.BoxDialogFrameTopCenter,
          r2_0.BoxDialogFrameTopRight,
          r2_0.BoxDialogFrameMiddleLeft,
          r2_0.BoxDialogFrameMiddleCengter,
          r2_0.BoxDialogFrameMiddleRight,
          r2_0.BoxDialogFrameBottomLeft,
          r2_0.BoxDialogFrameBottomCenter,
          r2_0.BoxDialogFrameBottomRight
        },
        start = 1,
        count = 9,
      }
    elseif r0_2 == r1_0.BorderDialogFrame then
      return {
        name = r1_0.BorderDialogFrame,
        frames = {
          r2_0.BorderDialogFrameTopCenter,
          r2_0.BorderDialogFrameBottomCenter
        },
        start = 10,
        count = 2,
      }
    elseif r0_2 == r1_0.DialogLine then
      return {
        name = r1_0.DialogLine,
        frames = {
          r2_0.DialogLine
        },
        start = 12,
        count = 1,
      }
    end
    return nil
  end,
  IsContain = function(r0_3)
    -- line: [114, 123] id: 3
    if r0_3 == r1_0.BoxDialogFrame or r0_3 == r1_0.BorderDialogFrame or r0_3 == r1_0.DialogLine then
      return true
    end
    return false
  end,
}
