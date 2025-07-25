-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = "parts01"
local r1_0 = {
  ExpFrame = r0_0 .. "ExpFrame",
  ExpBaseTreasureboxFrame = r0_0 .. "ExpBaseTreasureboxFrame",
}
return {
  SequenceNames = r1_0,
  FrameDefines = {
    ExpFrameLeft = 1,
    ExpFrameCenter = 2,
    ExpFrameRight = 3,
    ExpBaseTreasureboxFrameLeftUpper = 4,
    ExpBaseTreasureboxFrameCenterUpper = 5,
    ExpBaseTreasureboxFrameRightUpper = 6,
    ExpBaseTreasureboxFrameLeftLower = 7,
    ExpBaseTreasureboxFrameCenterLower = 8,
    ExpBaseTreasureboxFrameRightLower = 9,
  },
  LoadImageSheet = function()
    -- line: [30, 49] id: 1
    return graphics.newImageSheet("data/sprites/parts01.png", {
      frames = {
        {
          x = 0,
          y = 0,
          width = 20,
          height = 74,
        },
        {
          x = 20,
          y = 0,
          width = 1,
          height = 74,
        },
        {
          x = 21,
          y = 0,
          width = 20,
          height = 74,
        },
        {
          x = 41,
          y = 0,
          width = 20,
          height = 132,
        },
        {
          x = 61,
          y = 0,
          width = 1,
          height = 132,
        },
        {
          x = 62,
          y = 0,
          width = 20,
          height = 132,
        },
        {
          x = 82,
          y = 0,
          width = 20,
          height = 132,
        },
        {
          x = 102,
          y = 0,
          width = 1,
          height = 132,
        },
        {
          x = 103,
          y = 0,
          width = 20,
          height = 132,
        }
      },
      sheetContentWidth = 256,
      sheetContentHeight = 256,
    })
  end,
  GetSpriteFrameInfo = function(r0_2)
    -- line: [54, 72] id: 2
    if r0_2 == r1_0.ExpFrame then
      return {
        name = r1_0.ExpFrame,
        frames = {
          1,
          2,
          3
        },
        start = 1,
        count = 3,
      }
    elseif r0_2 == r1_0.ExpBaseTreasureboxFrame then
      return {
        name = r1_0.ExpBaseTreasureboxFrame,
        frames = {
          4,
          5,
          6,
          7,
          8,
          9
        },
        start = 4,
        count = 6,
      }
    end
    return nil
  end,
  IsContain = function(r0_3)
    -- line: [77, 84] id: 3
    if r0_3 == r1_0.ExpFrame or r0_3 == r1_0.ExpBaseTreasureboxFrame then
      return true
    end
    return false
  end,
}
