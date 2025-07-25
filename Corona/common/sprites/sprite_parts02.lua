-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = "parts02"
local r1_0 = {
  ScorePanel = r0_0 .. "ScorePanel",
  MenuBase = r0_0 .. "MenuBase",
}
return {
  SequenceNames = r1_0,
  FrameDefines = {
    ScorePanelLeft = 1,
    ScorePanelCenter = 2,
    ScorePanelRight = 3,
    MenuBaseHeaderLeft = 4,
    MenuBaseMiddleLeft = 5,
    MenuBaseFooterLeft = 6,
    MenuBaseHeaderRight = 7,
    MenuBaseMiddleRight = 8,
    MenuBaseFooterRight = 9,
  },
  LoadImageSheet = function()
    -- line: [31, 50] id: 1
    return graphics.newImageSheet("data/sprites/parts02.png", {
      frames = {
        {
          x = 0,
          y = 0,
          width = 30,
          height = 74,
        },
        {
          x = 30,
          y = 0,
          width = 10,
          height = 74,
        },
        {
          x = 40,
          y = 0,
          width = 30,
          height = 74,
        },
        {
          x = 0,
          y = 74,
          width = 80,
          height = 12,
        },
        {
          x = 0,
          y = 86,
          width = 80,
          height = 2,
        },
        {
          x = 0,
          y = 88,
          width = 80,
          height = 12,
        },
        {
          x = 0,
          y = 100,
          width = 79,
          height = 12,
        },
        {
          x = 0,
          y = 112,
          width = 79,
          height = 2,
        },
        {
          x = 0,
          y = 114,
          width = 79,
          height = 12,
        }
      },
      sheetContentWidth = 128,
      sheetContentHeight = 128,
    })
  end,
  GetSpriteFrameInfo = function(r0_2)
    -- line: [55, 73] id: 2
    if r0_2 == r1_0.ScorePanel then
      return {
        name = r1_0.ScorePanel,
        frames = {
          1,
          2,
          3
        },
        start = 1,
        count = 3,
      }
    elseif r0_2 == r1_0.MenuBase then
      return {
        name = r1_0.MenuBase,
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
    -- line: [78, 86] id: 3
    if r0_3 == r1_0.ScorePanel or r0_3 == r1_0.MenuBase then
      return true
    end
    return false
  end,
}
