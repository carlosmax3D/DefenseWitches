-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = "number03"
local r1_0 = {
  TicketNum = r0_0 .. "TicketNum",
  ExpNum = r0_0 .. "ExpNum",
}
return {
  SequenceNames = r1_0,
  FrameDefines = {
    TicketNumStart = 1,
    TicketNumMark = 11,
    TicketNumLeft = 12,
    TicketNumRight = 13,
    ExpNumStart = 14,
    ExpNumPlus = 24,
  },
  LoadImageSheet = function()
    -- line: [26, 60] id: 1
    return graphics.newImageSheet("data/sprites/numbers03.png", {
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
          x = 0,
          y = 36,
          width = 30,
          height = 36,
        },
        {
          x = 30,
          y = 36,
          width = 30,
          height = 36,
        },
        {
          x = 60,
          y = 36,
          width = 30,
          height = 36,
        },
        {
          x = 90,
          y = 36,
          width = 28,
          height = 31,
        },
        {
          x = 118,
          y = 36,
          width = 28,
          height = 31,
        },
        {
          x = 146,
          y = 36,
          width = 28,
          height = 31,
        },
        {
          x = 174,
          y = 36,
          width = 28,
          height = 31,
        },
        {
          x = 202,
          y = 36,
          width = 28,
          height = 31,
        },
        {
          x = 0,
          y = 72,
          width = 28,
          height = 31,
        },
        {
          x = 28,
          y = 72,
          width = 28,
          height = 31,
        },
        {
          x = 56,
          y = 72,
          width = 28,
          height = 31,
        },
        {
          x = 84,
          y = 72,
          width = 28,
          height = 31,
        },
        {
          x = 112,
          y = 72,
          width = 28,
          height = 31,
        },
        {
          x = 140,
          y = 72,
          width = 28,
          height = 31,
        },
        {
          x = 168,
          y = 72,
          width = 28,
          height = 31,
        },
        {
          x = 196,
          y = 72,
          width = 28,
          height = 31,
        }
      },
      sheetContentWidth = 256,
      sheetContentHeight = 256,
    })
  end,
  GetSpriteFrameInfo = function(r0_2)
    -- line: [65, 85] id: 2
    if r0_2 == r1_0.TicketNum then
      return {
        name = r1_0.TicketNum,
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
    elseif r0_2 == r1_0.ExpNum then
      return {
        name = r1_0.ExpNum,
        frames = {
          14,
          15,
          16,
          17,
          18,
          19,
          20,
          21,
          22,
          23,
          24
        },
        start = 14,
        count = 11,
        time = 0,
      }
    end
    return nil
  end,
  IsContain = function(r0_3)
    -- line: [90, 97] id: 3
    if r0_3 == r1_0.TicketNum or r0_3 == r1_0.ExpNum then
      return true
    end
    return false
  end,
}
