-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r1_0 = {
  evoCombiNum = "evoCombiNumber01" .. "EvoCombiNum",
}
return {
  SequenceNames = r1_0,
  FrameDefines = {
    evoCombiNumStart = 1,
    evoCombiPlus = 11,
    evoCombiPercent = 12,
    evoCombiSec = 13,
  },
  LoadImageSheet = function()
    -- line: [23, 44] id: 1
    return graphics.newImageSheet("data/sprites/evoCombiNumbers01.png", {
      frames = {
        {
          x = 0,
          y = 0,
          width = 52,
          height = 60,
        },
        {
          x = 52,
          y = 0,
          width = 52,
          height = 60,
        },
        {
          x = 104,
          y = 0,
          width = 52,
          height = 60,
        },
        {
          x = 156,
          y = 0,
          width = 52,
          height = 60,
        },
        {
          x = 0,
          y = 60,
          width = 52,
          height = 60,
        },
        {
          x = 52,
          y = 60,
          width = 52,
          height = 60,
        },
        {
          x = 104,
          y = 60,
          width = 52,
          height = 60,
        },
        {
          x = 156,
          y = 60,
          width = 52,
          height = 60,
        },
        {
          x = 0,
          y = 120,
          width = 52,
          height = 60,
        },
        {
          x = 52,
          y = 120,
          width = 52,
          height = 60,
        },
        {
          x = 104,
          y = 120,
          width = 52,
          height = 60,
        },
        {
          x = 156,
          y = 120,
          width = 52,
          height = 60,
        },
        {
          x = 0,
          y = 180,
          width = 84,
          height = 60,
        }
      },
      sheetContentWidth = 256,
      sheetContentHeight = 256,
    })
  end,
  GetSpriteFrameInfo = function(r0_2)
    -- line: [49, 61] id: 2
    if r0_2 == r1_0.evoCombiNum then
      return {
        name = r1_0.evoCombiNum,
        frames = {
          1,
          2,
          3,
          4,
          5,
          6,
          7,
          8,
          9,
          10,
          11,
          12,
          13
        },
        start = 1,
        count = 13,
        time = 0,
      }
    end
    return nil
  end,
  IsContain = function(r0_3)
    -- line: [66, 72] id: 3
    if r0_3 == r1_0.evoCombiNum then
      return true
    end
    return false
  end,
}
