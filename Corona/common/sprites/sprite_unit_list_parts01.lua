-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r1_0 = {
  UnitListParts = "unitListParts01" .. "UnitListParts",
}
local r2_0 = {
  UnitListLockPlate = 1,
  UnitListNoSummonPlate01 = 2,
  UnitListNoSummonPlate02 = 3,
  UnitListUnlockPlateLeft = 4,
  UnitListUnlockPlateCenter = 5,
  UnitListUnlockPlateRight = 6,
  UnitListLockTopLine = 7,
  UnitListLockBottomLine = 8,
  UnitListIconMp = 9,
  UnitListIconCrystal = 10,
  UnitListIconUpgrade02 = 11,
  UnitListIconUpgrade03 = 12,
  UnitListIconUpgrade04 = 13,
}
return {
  SequenceNames = r1_0,
  FrameDefines = r2_0,
  LoadImageSheet = function()
    -- line: [34, 54] id: 1
    return graphics.newImageSheet("data/sprites/unitListParts01.png", {
      frames = {
        {
          x = 0,
          y = 0,
          width = 84,
          height = 36,
        },
        {
          x = 0,
          y = 36,
          width = 84,
          height = 36,
        },
        {
          x = 0,
          y = 72,
          width = 84,
          height = 36,
        },
        {
          x = 84,
          y = 0,
          width = 8,
          height = 24,
        },
        {
          x = 92,
          y = 0,
          width = 1,
          height = 24,
        },
        {
          x = 94,
          y = 0,
          width = 8,
          height = 24,
        },
        {
          x = 0,
          y = 108,
          width = 1,
          height = 2,
        },
        {
          x = 18,
          y = 108,
          width = 1,
          height = 2,
        },
        {
          x = 0,
          y = 110,
          width = 18,
          height = 18,
        },
        {
          x = 18,
          y = 110,
          width = 18,
          height = 18,
        },
        {
          x = 102,
          y = 0,
          width = 26,
          height = 24,
        },
        {
          x = 84,
          y = 24,
          width = 28,
          height = 28,
        },
        {
          x = 84,
          y = 52,
          width = 38,
          height = 37,
        }
      },
      sheetContentWidth = 128,
      sheetContentHeight = 128,
    })
  end,
  GetSpriteFrameInfo = function(r0_2)
    -- line: [59, 84] id: 2
    if r0_2 == r1_0.UnitListParts then
      return {
        name = r1_0.BoxDialogFrame,
        frames = {
          r2_0.UnitListLockPlate,
          r2_0.UnitListNoSummonPlate01,
          r2_0.UnitListNoSummonPlate02,
          r2_0.UnitListUnlockPlateLeft,
          r2_0.UnitListUnlockPlateCenter,
          r2_0.UnitListUnlockPlateRight,
          r2_0.UnitListLockTopLine,
          r2_0.UnitListLockBottomLine,
          r2_0.UnitListIconMp,
          r2_0.UnitListIconCrystal,
          r2_0.UnitListIconUpgrade02,
          r2_0.UnitListIconUpgrade03,
          r2_0.UnitListIconUpgrade04
        },
        start = 1,
        count = 13,
      }
    end
    return nil
  end,
  IsContain = function(r0_3)
    -- line: [89, 95] id: 3
    if r0_3 == r1_0.UnitListParts then
      return true
    end
    return false
  end,
}
