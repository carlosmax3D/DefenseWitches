-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r1_0 = {
  EvoButton = "parts03" .. "EvoButton",
}
return {
  SequenceNames = r1_0,
  FrameDefines = {
    EvoStart = 1,
  },
  LoadImageSheet = function()
    -- line: [22, 34] id: 1
    return graphics.newImageSheet("data/sprites/parts03.png", {
      frames = {
        {
          x = 0,
          y = 0,
          width = 32,
          height = 32,
        },
        {
          x = 32,
          y = 0,
          width = 32,
          height = 32,
        },
        {
          x = 0,
          y = 32,
          width = 32,
          height = 32,
        },
        {
          x = 32,
          y = 32,
          width = 32,
          height = 32,
        }
      },
      sheetContentWidth = 64,
      sheetContentHeight = 64,
    })
  end,
  GetSpriteFrameInfo = function(r0_2)
    -- line: [39, 50] id: 2
    if r0_2 == r1_0.EvoButton then
      return {
        name = r1_0.EvoButton,
        frames = {
          1,
          2,
          3,
          4
        },
        start = 1,
        count = 4,
      }
    end
    return nil
  end,
  IsContain = function(r0_3)
    -- line: [55, 62] id: 3
    if r0_3 == r1_0.EvoButton then
      return true
    end
    return false
  end,
}
