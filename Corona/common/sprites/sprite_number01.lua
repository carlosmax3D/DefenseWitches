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
    -- line: [31, 100] id: 1
    return graphics.newImageSheet("data/sprites/numbers01.png", {
      frames = {
        {
          x = 0,
          y = 0,
          width = 12,
          height = 20,
        },
        {
          x = 12,
          y = 0,
          width = 12,
          height = 20,
        },
        {
          x = 24,
          y = 0,
          width = 12,
          height = 20,
        },
        {
          x = 36,
          y = 0,
          width = 12,
          height = 20,
        },
        {
          x = 48,
          y = 0,
          width = 12,
          height = 20,
        },
        {
          x = 60,
          y = 0,
          width = 12,
          height = 20,
        },
        {
          x = 72,
          y = 0,
          width = 12,
          height = 20,
        },
        {
          x = 84,
          y = 0,
          width = 12,
          height = 20,
        },
        {
          x = 96,
          y = 0,
          width = 12,
          height = 20,
        },
        {
          x = 108,
          y = 0,
          width = 12,
          height = 20,
        },
        {
          x = 0,
          y = 20,
          width = 12,
          height = 20,
        },
        {
          x = 12,
          y = 20,
          width = 12,
          height = 20,
        },
        {
          x = 24,
          y = 20,
          width = 12,
          height = 20,
        },
        {
          x = 36,
          y = 20,
          width = 12,
          height = 20,
        },
        {
          x = 48,
          y = 20,
          width = 12,
          height = 20,
        },
        {
          x = 60,
          y = 20,
          width = 12,
          height = 20,
        },
        {
          x = 72,
          y = 20,
          width = 12,
          height = 20,
        },
        {
          x = 84,
          y = 20,
          width = 12,
          height = 20,
        },
        {
          x = 96,
          y = 20,
          width = 12,
          height = 20,
        },
        {
          x = 108,
          y = 20,
          width = 12,
          height = 20,
        },
        {
          x = 0,
          y = 40,
          width = 16,
          height = 20,
        },
        {
          x = 16,
          y = 40,
          width = 16,
          height = 20,
        },
        {
          x = 32,
          y = 40,
          width = 16,
          height = 20,
        },
        {
          x = 48,
          y = 40,
          width = 16,
          height = 20,
        },
        {
          x = 64,
          y = 40,
          width = 16,
          height = 20,
        },
        {
          x = 80,
          y = 40,
          width = 16,
          height = 20,
        },
        {
          x = 96,
          y = 40,
          width = 16,
          height = 20,
        },
        {
          x = 112,
          y = 40,
          width = 16,
          height = 20,
        },
        {
          x = 0,
          y = 60,
          width = 16,
          height = 20,
        },
        {
          x = 16,
          y = 60,
          width = 16,
          height = 20,
        },
        {
          x = 32,
          y = 60,
          width = 16,
          height = 20,
        },
        {
          x = 48,
          y = 60,
          width = 16,
          height = 20,
        },
        {
          x = 64,
          y = 60,
          width = 16,
          height = 20,
        },
        {
          x = 80,
          y = 60,
          width = 16,
          height = 20,
        },
        {
          x = 96,
          y = 60,
          width = 16,
          height = 20,
        },
        {
          x = 112,
          y = 60,
          width = 16,
          height = 20,
        },
        {
          x = 0,
          y = 80,
          width = 16,
          height = 20,
        },
        {
          x = 16,
          y = 80,
          width = 16,
          height = 20,
        },
        {
          x = 32,
          y = 80,
          width = 16,
          height = 20,
        },
        {
          x = 48,
          y = 80,
          width = 16,
          height = 20,
        },
        {
          x = 64,
          y = 80,
          width = 14,
          height = 21,
        },
        {
          x = 78,
          y = 80,
          width = 14,
          height = 21,
        },
        {
          x = 92,
          y = 80,
          width = 14,
          height = 21,
        },
        {
          x = 106,
          y = 80,
          width = 14,
          height = 21,
        },
        {
          x = 0,
          y = 101,
          width = 14,
          height = 21,
        },
        {
          x = 14,
          y = 101,
          width = 14,
          height = 21,
        },
        {
          x = 28,
          y = 101,
          width = 14,
          height = 21,
        },
        {
          x = 42,
          y = 101,
          width = 14,
          height = 21,
        },
        {
          x = 56,
          y = 101,
          width = 14,
          height = 21,
        },
        {
          x = 70,
          y = 101,
          width = 14,
          height = 21,
        },
        {
          x = 84,
          y = 101,
          width = 14,
          height = 21,
        },
        {
          x = 98,
          y = 101,
          width = 14,
          height = 21,
        },
        {
          x = 112,
          y = 101,
          width = 14,
          height = 21,
        },
        nil,
        nil,
        nil
      },
      sheetContentWidth = 128,
      sheetContentHeight = 128,
    })
  end,
  GetSpriteFrameInfo = function(r0_2)
    -- line: [105, 144] id: 2
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
    -- line: [149, 159] id: 3
    if r0_3 == r1_0.GetMP or r0_3 == r1_0.GetScore or r0_3 == r1_0.MpNumberA or r0_3 == r1_0.MpNumberB or r0_3 == r1_0.Score then
      return true
    end
    return false
  end,
}
