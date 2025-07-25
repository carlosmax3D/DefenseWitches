-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r1_0 = {
  RackNum = "number04" .. "RankNum",
}
return {
  SequenceNames = r1_0,
  FrameDefines = {
    RankNumStart = 1,
  },
  LoadImageSheet = function()
    -- line: [20, 39] id: 1
    return graphics.newImageSheet("data/sprites/numbers04.png", {
      frames = {
        {
          x = 0,
          y = 0,
          width = 64,
          height = 76,
        },
        {
          x = 64,
          y = 0,
          width = 50,
          height = 76,
        },
        {
          x = 114,
          y = 0,
          width = 62,
          height = 76,
        },
        {
          x = 176,
          y = 0,
          width = 62,
          height = 76,
        },
        {
          x = 0,
          y = 76,
          width = 64,
          height = 76,
        },
        {
          x = 64,
          y = 76,
          width = 62,
          height = 76,
        },
        {
          x = 126,
          y = 76,
          width = 64,
          height = 76,
        },
        {
          x = 190,
          y = 76,
          width = 62,
          height = 76,
        },
        {
          x = 0,
          y = 152,
          width = 62,
          height = 76,
        },
        {
          x = 62,
          y = 152,
          width = 64,
          height = 76,
        },
        {
          x = 126,
          y = 152,
          width = 70,
          height = 60,
        }
      },
      sheetContentWidth = 256,
      sheetContentHeight = 256,
    })
  end,
  GetSpriteFrameInfo = function(r0_2)
    -- line: [44, 56] id: 2
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
    -- line: [61, 67] id: 3
    if r0_3 == r1_0.RankNum then
      return true
    end
    return false
  end,
}
