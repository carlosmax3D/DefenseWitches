-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = "string01"
local r1_0 = {
  Wave = r0_0 .. "Wave",
  Final = r0_0 .. "Final",
  World = r0_0 .. "World",
  Excellent = r0_0 .. "Excellent",
  Good = r0_0 .. "Good",
  Nice = r0_0 .. "Nice",
}
return {
  SequenceNames = r1_0,
  FrameDefines = {
    Wave = 1,
    Final = 2,
    World = 3,
    Excellent = 4,
    Good = 5,
    Nice = 6,
  },
  LoadImageSheet = function()
    -- line: [31, 50] id: 1
    return graphics.newImageSheet("data/sprites/strings01.png", {
      frames = {
        {
          x = 0,
          y = 0,
          width = 152,
          height = 56,
        },
        {
          x = 0,
          y = 56,
          width = 168,
          height = 56,
        },
        {
          x = 0,
          y = 112,
          width = 128,
          height = 40,
        },
        {
          x = 0,
          y = 152,
          width = 185,
          height = 59,
        },
        {
          x = 0,
          y = 211,
          width = 94,
          height = 32,
        },
        {
          x = 94,
          y = 211,
          width = 93,
          height = 35,
        }
      },
      sheetContentWidth = 256,
      sheetContentHeight = 256,
    })
  end,
  GetSpriteFrameInfo = function(r0_2)
    -- line: [55, 101] id: 2
    if r0_2 == r1_0.Wave then
      return {
        name = r1_0.Wave,
        frames = {
          1
        },
        start = 1,
        count = 1,
      }
    elseif r0_2 == r1_0.Final then
      return {
        name = r1_0.Final,
        frames = {
          2
        },
        start = 2,
        count = 1,
      }
    elseif r0_2 == r1_0.World then
      return {
        name = r1_0.World,
        frames = {
          3
        },
        start = 3,
        count = 1,
      }
    elseif r0_2 == r1_0.Excellent then
      return {
        name = r1_0.Excellent,
        frames = {
          4
        },
        start = 4,
        count = 1,
      }
    elseif r0_2 == r1_0.Good then
      return {
        name = r1_0.Good,
        frames = {
          5
        },
        start = 5,
        count = 1,
      }
    elseif r0_2 == r1_0.Nice then
      return {
        name = r1_0.Nice,
        frames = {
          6
        },
        start = 6,
        count = 1,
      }
    end
    return nil
  end,
  IsContain = function(r0_3)
    -- line: [106, 117] id: 3
    if r0_3 == r1_0.Wave or r0_3 == r1_0.Final or r0_3 == r1_0.World or r0_3 == r1_0.Excellent or r0_3 == r1_0.Good or r0_3 == r1_0.Nice then
      return true
    end
    return false
  end,
}
