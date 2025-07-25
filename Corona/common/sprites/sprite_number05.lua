-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r1_0 = {
  RackNum = "number05" .. "RankNum",
}
return {
  SequenceNames = r1_0,
  FrameDefines = {
    RankNumStart = 1,
  },
  LoadImageSheet = function()
    -- line: [20, 42] id: 1
    return graphics.newImageSheet("data/sprites/numbers05.png", {
      frames = {
        {
          x = 0,
          y = 0,
          width = 22,
          height = 24,
        },
        {
          x = 24,
          y = 0,
          width = 16,
          height = 24,
        },
        {
          x = 42,
          y = 0,
          width = 20,
          height = 24,
        },
        {
          x = 64,
          y = 0,
          width = 20,
          height = 24,
        },
        {
          x = 86,
          y = 0,
          width = 22,
          height = 24,
        },
        {
          x = 0,
          y = 26,
          width = 20,
          height = 24,
        },
        {
          x = 22,
          y = 26,
          width = 20,
          height = 24,
        },
        {
          x = 44,
          y = 26,
          width = 20,
          height = 24,
        },
        {
          x = 66,
          y = 26,
          width = 22,
          height = 24,
        },
        {
          x = 90,
          y = 26,
          width = 22,
          height = 24,
        },
        {
          x = 0,
          y = 52,
          width = 20,
          height = 20,
        },
        {
          x = 22,
          y = 52,
          width = 25,
          height = 24,
        },
        {
          x = 49,
          y = 52,
          width = 22,
          height = 20,
        },
        {
          x = 73,
          y = 52,
          width = 42,
          height = 20,
        }
      },
      sheetContentWidth = 128,
      sheetContentHeight = 128,
    })
  end,
  GetSpriteFrameInfo = function(r0_2)
    -- line: [47, 59] id: 2
    if r0_2 == r1_0.RankNum then
      return {
        name = r1_0.RankNum,
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
          11
        },
        start = 1,
        count = 11,
        time = 0,
      }
    end
    return nil
  end,
  IsContain = function(r0_3)
    -- line: [64, 70] id: 3
    if r0_3 == r1_0.RankNum then
      return true
    end
    return false
  end,
}
