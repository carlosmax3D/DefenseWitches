-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
module(..., package.seeall)
SequenceNames = {
  Medals = "test" .. "Medals",
}
FrameDefines = {
  Sp = 1,
  P = 2,
  S = 3,
  B = 4,
  Hp1 = 5,
  Ex3 = 6,
  Ex2 = 7,
  Ex1 = 8,
  Disable = 9,
}
function LoadImageSheet()
  -- line: [32, 49] id: 1
  return graphics.newImageSheet("test/data/mdl_set.png", {
    frames = {
      {
        x = 0,
        y = 0,
        width = 18,
        height = 18,
      },
      {
        x = 18,
        y = 0,
        width = 18,
        height = 18,
      },
      {
        x = 36,
        y = 0,
        width = 18,
        height = 18,
      },
      {
        x = 0,
        y = 18,
        width = 18,
        height = 18,
      },
      {
        x = 18,
        y = 18,
        width = 18,
        height = 18,
      },
      {
        x = 36,
        y = 18,
        width = 18,
        height = 18,
      },
      {
        x = 0,
        y = 36,
        width = 18,
        height = 18,
      },
      {
        x = 18,
        y = 36,
        width = 18,
        height = 18,
      },
      {
        x = 36,
        y = 36,
        width = 18,
        height = 18,
      }
    },
    sheetContentWidth = 64,
    sheetContentHeight = 64,
  })
end
function GetSpriteFrameInfo(r0_2)
  -- line: [54, 73] id: 2
  if r0_2 == SequenceNames.Medals then
    return {
      name = SequenceNames.Medals,
      frames = {
        FrameDefines.Sp,
        FrameDefines.P,
        FrameDefines.S,
        FrameDefines.B,
        FrameDefines.Hp1,
        FrameDefines.Ex3,
        FrameDefines.Ex2,
        FrameDefines.Ex1,
        FrameDefines.Disable
      },
      start = 1,
      count = 6,
    }
  end
end
function IsContain(r0_3)
  -- line: [78, 85] id: 3
  if r0_3 == SequenceNames.Medals then
    return true
  end
  return false
end
