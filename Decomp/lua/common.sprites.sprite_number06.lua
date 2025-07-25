-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = "number01"
local r1_0 = {
  GetMP = r0_0 .. "GetMp",
  GetScore = r0_0 .. "GetScore",
  MpNumberA = r0_0 .. "MpNumberA",
  MpNumberB = r0_0 .. "MpNumberB",
  Score = r0_0 .. "Score",
}
return {
  SequenceNames = r1_0,
  FrameDefines = {
    GetMpStart = 1,
    GetScoreStart = 11,
    MpNumberAStart = 21,
    MpNumberBStart = 31,
    ScoreStart = 41,
    ScoreSlash = 52,
    ScoreHyphen = 51,
    ScoreCross = 53,
  },
  LoadImageSheet = function()
    -- line: [32, 94] id: 1
    return graphics.newImageSheet("data/sprites/numbers06.png", {
      frames = {
        {
          x = 0,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 30,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 60,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 90,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 120,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 150,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 180,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 210,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 240,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 270,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 0,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 30,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 60,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 90,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 120,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 150,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 180,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 210,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 240,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 270,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 0,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 30,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 60,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 90,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 120,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 150,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 180,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 210,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 240,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 270,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 0,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 30,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 60,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 90,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 120,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 150,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 180,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 210,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 240,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 270,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 0,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 30,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 60,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 90,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 120,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 150,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 180,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 210,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 240,
          y = 0,
          width = 30,
          height = 36,
        },
        {
          x = 270,
          y = 0,
          width = 30,
          height = 36,
        },
        nil,
        nil
      },
      sheetContentWidth = 300,
      sheetContentHeight = 36,
    })
  end,
  GetSpriteFrameInfo = function(r0_2)
    -- line: [99, 138] id: 2
    if r0_2 == r1_0.GetMP then
      return {
        name = r1_0.GetMP,
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
    elseif r0_2 == r1_0.GetScore then
      return {
        name = r1_0.GetScore,
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
          20
        },
        start = 11,
        count = 10,
      }
    elseif r0_2 == r1_0.MpNumberA then
      return {
        name = r1_0.MpNumberA,
        frames = {
          21,
          22,
          23,
          24,
          25,
          26,
          27,
          28,
          29,
          30
        },
        start = 21,
        count = 10,
      }
    elseif r0_2 == r1_0.MpNumberB then
      return {
        name = r1_0.MpNumberB,
        frames = {
          31,
          32,
          33,
          34,
          35,
          36,
          37,
          38,
          39,
          40
        },
        start = 31,
        count = 10,
      }
    elseif r0_2 == r1_0.Score then
      return {
        name = r1_0.Score,
        frames = {
          41,
          42,
          43,
          44,
          45,
          46,
          47,
          48,
          49,
          50,
          51,
          52,
          53
        },
        start = 41,
        count = 13,
      }
    end
    return nil
  end,
  IsContain = function(r0_3)
    -- line: [143, 153] id: 3
    if r0_3 == r1_0.GetMP or r0_3 == r1_0.GetScore or r0_3 == r1_0.MpNumberA or r0_3 == r1_0.MpNumberB or r0_3 == r1_0.Score then
      return true
    end
    return false
  end,
}
