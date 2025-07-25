-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = "number02"
local r1_0 = {
  Wave = r0_0 .. "Wave",
  World = r0_0 .. "World",
  PlateNumberA = r0_0 .. "PlateNumberA",
  PlateNumberB = r0_0 .. "PlateNumberB",
  Arrow = r0_0 .. "Arrow",
}
return {
  SequenceNames = r1_0,
  FrameDefines = {
    WaveStart = 1,
    WorldStart = 11,
    WorldHyphen = 21,
    PlateNumberAStart = 22,
    PlateNumberAInfinity = 32,
    PlateNumberAHyphen = 33,
    PlateNumberBStart = 34,
    PlateNumberBInfinity = 44,
    PlateNumberBHyphen = 45,
    Arrow = 46,
  },
  LoadImageSheet = function()
    -- line: [34, 96] id: 1
    return graphics.newImageSheet("data/sprites/numbers02.png", {
      frames = {
        {
          x = 0,
          y = 0,
          width = 48,
          height = 64,
        },
        {
          x = 48,
          y = 0,
          width = 48,
          height = 64,
        },
        {
          x = 96,
          y = 0,
          width = 48,
          height = 64,
        },
        {
          x = 144,
          y = 0,
          width = 48,
          height = 64,
        },
        {
          x = 192,
          y = 0,
          width = 48,
          height = 64,
        },
        {
          x = 0,
          y = 64,
          width = 48,
          height = 64,
        },
        {
          x = 48,
          y = 64,
          width = 48,
          height = 64,
        },
        {
          x = 96,
          y = 64,
          width = 48,
          height = 64,
        },
        {
          x = 144,
          y = 64,
          width = 48,
          height = 64,
        },
        {
          x = 192,
          y = 64,
          width = 48,
          height = 64,
        },
        {
          x = 0,
          y = 128,
          width = 32,
          height = 40,
        },
        {
          x = 32,
          y = 128,
          width = 32,
          height = 40,
        },
        {
          x = 64,
          y = 128,
          width = 32,
          height = 40,
        },
        {
          x = 96,
          y = 128,
          width = 32,
          height = 40,
        },
        {
          x = 128,
          y = 128,
          width = 32,
          height = 40,
        },
        {
          x = 160,
          y = 128,
          width = 32,
          height = 40,
        },
        {
          x = 192,
          y = 128,
          width = 32,
          height = 40,
        },
        {
          x = 0,
          y = 168,
          width = 32,
          height = 40,
        },
        {
          x = 32,
          y = 168,
          width = 32,
          height = 40,
        },
        {
          x = 64,
          y = 168,
          width = 32,
          height = 40,
        },
        {
          x = 96,
          y = 168,
          width = 32,
          height = 40,
        },
        {
          x = 128,
          y = 168,
          width = 16,
          height = 24,
        },
        {
          x = 144,
          y = 168,
          width = 16,
          height = 24,
        },
        {
          x = 160,
          y = 168,
          width = 16,
          height = 24,
        },
        {
          x = 176,
          y = 168,
          width = 16,
          height = 24,
        },
        {
          x = 192,
          y = 168,
          width = 16,
          height = 24,
        },
        {
          x = 208,
          y = 168,
          width = 16,
          height = 24,
        },
        {
          x = 224,
          y = 168,
          width = 16,
          height = 24,
        },
        {
          x = 240,
          y = 168,
          width = 16,
          height = 24,
        },
        {
          x = 128,
          y = 192,
          width = 16,
          height = 24,
        },
        {
          x = 144,
          y = 192,
          width = 16,
          height = 24,
        },
        {
          x = 160,
          y = 192,
          width = 34,
          height = 24,
        },
        {
          x = 222,
          y = 192,
          width = 34,
          height = 20,
        },
        {
          x = 0,
          y = 216,
          width = 18,
          height = 26,
        },
        {
          x = 18,
          y = 216,
          width = 18,
          height = 26,
        },
        {
          x = 36,
          y = 216,
          width = 18,
          height = 26,
        },
        {
          x = 54,
          y = 216,
          width = 18,
          height = 26,
        },
        {
          x = 72,
          y = 216,
          width = 18,
          height = 26,
        },
        {
          x = 90,
          y = 216,
          width = 18,
          height = 26,
        },
        {
          x = 108,
          y = 216,
          width = 18,
          height = 26,
        },
        {
          x = 126,
          y = 216,
          width = 18,
          height = 26,
        },
        {
          x = 144,
          y = 216,
          width = 18,
          height = 26,
        },
        {
          x = 162,
          y = 216,
          width = 18,
          height = 26,
        },
        {
          x = 180,
          y = 216,
          width = 36,
          height = 26,
        },
        {
          x = 222,
          y = 212,
          width = 34,
          height = 20,
        },
        {
          x = 222,
          y = 232,
          width = 34,
          height = 20,
        },
        nil,
        nil
      },
      sheetContentWidth = 256,
      sheetContentHeight = 256,
    })
  end,
  GetSpriteFrameInfo = function(r0_2)
    -- line: [101, 140] id: 2
    if r0_2 == r1_0.Wave then
      return {
        name = r1_0.Wave,
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
          10
        },
        start = 1,
        count = 10,
      }
    elseif r0_2 == r1_0.World then
      return {
        name = r1_0.World,
        frames = {
          11,
          12,
          13,
          14,
          15,
          16,
          17,
          18,
          19,
          20,
          21
        },
        start = 11,
        count = 11,
      }
    elseif r0_2 == r1_0.PlateNumberA then
      return {
        name = r1_0.PlateNumberA,
        frames = {
          22,
          23,
          24,
          25,
          26,
          27,
          28,
          29,
          30,
          31,
          32,
          33
        },
        start = 22,
        count = 12,
      }
    elseif r0_2 == r1_0.PlateNumberB then
      return {
        name = r1_0.PlateNumberB,
        frames = {
          34,
          35,
          36,
          37,
          38,
          39,
          40,
          41,
          42,
          43,
          44,
          45
        },
        start = 34,
        count = 12,
      }
    elseif r0_2 == r1_0.Arrow then
      return {
        name = r1_0.Arrow,
        frames = {
          46
        },
        start = 46,
        count = 1,
      }
    end
    return nil
  end,
  IsContain = function(r0_3)
    -- line: [145, 155] id: 3
    if r0_3 == r1_0.Wave or r0_3 == r1_0.World or r0_3 == r1_0.PlateNumberA or r0_3 == r1_0.PlateNumberB or r0_3 == r1_0.Arrow then
      return true
    end
    return false
  end,
}
