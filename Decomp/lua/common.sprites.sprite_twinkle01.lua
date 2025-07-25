-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = "twinkle01"
local r1_0 = {
  White = r0_0 .. "White",
  Silver = r0_0 .. "Silver",
  Gold = r0_0 .. "Gold",
  Red = r0_0 .. "Red",
  Purple = r0_0 .. "Purple",
}
return {
  SequenceNames = r1_0,
  FrameDefines = {
    WhiteStart = 1,
    SliverStart = 3,
    GoldStart = 5,
    RedStar = 7,
    PurpleStar = 9,
  },
  LoadImageSheet = function()
    -- line: [29, 37] id: 1
    return graphics.newImageSheet("data/sprites/twinkle01.png", {
      width = 30,
      height = 30,
      numFrames = 10,
      sheetContentWidth = 60,
      sheetContentHeight = 150,
    })
  end,
  GetSpriteFrameInfo = function(r0_2)
    -- line: [42, 81] id: 2
    if r0_2 == r1_0.White then
      return {
        name = r1_0.White,
        frames = {
          1,
          2
        },
        start = 1,
        count = 2,
      }
    elseif r0_2 == r1_0.Silver then
      return {
        name = r1_0.Silver,
        frames = {
          3,
          4
        },
        start = 3,
        count = 2,
      }
    elseif r0_2 == r1_0.Gold then
      return {
        name = r1_0.Gold,
        frames = {
          5,
          6
        },
        start = 5,
        count = 2,
      }
    elseif r0_2 == r1_0.Red then
      return {
        name = r1_0.Red,
        frames = {
          7,
          8
        },
        start = 7,
        count = 2,
      }
    elseif r0_2 == r1_0.Purple then
      return {
        name = r1_0.Purple,
        frames = {
          9,
          10
        },
        start = 9,
        count = 2,
      }
    end
    return nil
  end,
  IsContain = function(r0_3)
    -- line: [86, 97] id: 3
    if r0_3 == r1_0.White or r0_3 == r1_0.Silver or r0_3 == r1_0.Gold or r0_3 == r1_0.Red or r0_3 == r1_0.Purple then
      return true
    end
    return false
  end,
}
