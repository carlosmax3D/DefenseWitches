-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = {
  PlaneEnable = function()
    -- line: [10, 10] id: 1
    return 1
  end,
  PlaneDisable = function()
    -- line: [11, 11] id: 2
    return 2
  end,
  UnlockEnable = function()
    -- line: [12, 12] id: 3
    return 3
  end,
  UnlockDisable = function()
    -- line: [13, 13] id: 4
    return 4
  end,
  Lock = function()
    -- line: [14, 14] id: 5
    return 5
  end,
  NoSummon01 = function()
    -- line: [15, 15] id: 6
    return 6
  end,
  NoSummon02 = function()
    -- line: [16, 16] id: 7
    return 7
  end,
  EvoPlateDisable = function()
    -- line: [17, 17] id: 8
    return 8
  end,
  EvoPlateEnableNormalOnly = function()
    -- line: [18, 18] id: 9
    return 9
  end,
  EvoPlateEnable = function()
    -- line: [19, 19] id: 10
    return 10
  end,
}
return {
  new = function(r0_11)
    -- line: [25, 368] id: 11
    local r1_11 = {}
    local r2_11 = require("resource.char_define")
    local r3_11 = require("common.sprite_loader").new({
      imageSheet = "common.sprites.sprite_unit_list_parts01",
    })
    local r4_11 = "unit_info%02d%s"
    local r5_11 = "item_info%02d%s"
    local r6_11 = 40
    local r7_11 = 40
    local function r8_11(r0_12)
      -- line: [47, 47] id: 12
      return "data/char/unit_list/" .. r0_12 .. ".png"
    end
    local function r9_11(r0_13)
      -- line: [52, 54] id: 13
      return r3_11.CreateImage(r0_13, r3_11.sequenceNames.UnitListParts, r3_11.frameDefines.UnitListIconMp, 0, 0)
    end
    local function r10_11(r0_14)
      -- line: [59, 61] id: 14
      return r3_11.CreateImage(r0_14, r3_11.sequenceNames.UnitListParts, r3_11.frameDefines.UnitListIconCrystal, 0, 0)
    end
    local function r11_11(r0_15, r1_15)
      -- line: [66, 77] id: 15
      local r2_15 = display.newGroup()
      local r3_15 = display.newRect(r2_15, 0, 0, r6_11, r7_11)
      r3_15.alpha = 0
      local r4_15 = r3_11.CreateImage(r2_15, r3_11.sequenceNames.UnitListParts, r1_15, 0, 0)
      r4_15:setReferencePoint(display.CenterReferencePoint)
      r4_15.x = r3_15.width * 0.5
      r4_15.y = r3_15.height * 0.5
      r0_15:insert(r2_15)
      return r2_15
    end
    local function r12_11(r0_16)
      -- line: [82, 84] id: 16
      return r11_11(r0_16, r3_11.frameDefines.UnitListIconUpgrade02)
    end
    local function r13_11(r0_17)
      -- line: [89, 91] id: 17
      return r11_11(r0_17, r3_11.frameDefines.UnitListIconUpgrade03)
    end
    local function r14_11(r0_18)
      -- line: [96, 98] id: 18
      return r11_11(r0_18, r3_11.frameDefines.UnitListIconUpgrade04)
    end
    local function r15_11(r0_19, r1_19)
      -- line: [103, 136] id: 19
      if r0_19 == nil then
        return 
      end
      local r2_19 = display.newGroup()
      local r3_19 = display.newGroup()
      local r4_19 = r3_11.CreateImage(r2_19, r3_11.sequenceNames.UnitListParts, r3_11.frameDefines.UnitListUnlockPlateLeft, 0, 0)
      local r5_19 = r3_11.CreateImage(r3_19, r3_11.sequenceNames.UnitListParts, r3_11.frameDefines.UnitListUnlockPlateCenter, 0, 0)
      if r1_19 == true then
        local r6_19 = r3_11.CreateImage(r2_19, r3_11.sequenceNames.UnitListParts, r3_11.frameDefines.UnitListUnlockPlateRight, 76, 0)
      else
        local r6_19 = r3_11.CreateImage(r2_19, r3_11.sequenceNames.UnitListParts, r3_11.frameDefines.UnitListUnlockPlateRight, 76, 0)
      end
      r3_19.xScale = 68
      r2_19:insert(r3_19)
      r3_19:setReferencePoint(display.TopLeftReferencePoint)
      r3_19.x = 8
      r3_19.y = 0
      r0_19:insert(r2_19)
      r2_19:setReferencePoint(display.TopLeftReferencePoint)
      r2_19.x = 6
      if r1_19 == true then
        r2_19.y = 6
      else
        r2_19.y = 72
      end
      return r2_19
    end
    local function r16_11(r0_20)
      -- line: [141, 172] id: 20
      if r0_20 == nil then
        return 
      end
      local r1_20 = display.newGroup()
      local r2_20 = display.newGroup()
      local r3_20 = display.newGroup()
      local r4_20 = r3_11.CreateImage(r1_20, r3_11.sequenceNames.UnitListParts, r3_11.frameDefines.UnitListLockPlate, 0, 0)
      local r5_20 = r3_11.CreateImage(r2_20, r3_11.sequenceNames.UnitListParts, r3_11.frameDefines.UnitListLockTopLine, 0, 0)
      local r6_20 = r3_11.CreateImage(r3_20, r3_11.sequenceNames.UnitListParts, r3_11.frameDefines.UnitListLockBottomLine, 0, 0)
      r1_20:insert(r2_20)
      r2_20:setReferencePoint(display.TopLeftReferencePoint)
      r2_20.x = 6
      r2_20.y = 0
      r2_20.xScale = 72
      r1_20:insert(r3_20)
      r3_20:setReferencePoint(display.TopLeftReferencePoint)
      r3_20.x = 6
      r3_20.y = 98
      r3_20.xScale = 72
      r4_20:setReferencePoint(display.TopLeftReferencePoint)
      r4_20.x = 0
      r4_20.y = 59
      r0_20:insert(r1_20)
      r1_20:setReferencePoint(display.TopLeftReferencePoint)
      r1_20.x = 4
      r1_20.y = 2
      return r1_20
    end
    local function r17_11(r0_21)
      -- line: [177, 191] id: 21
      if r0_21 == nil then
        return 
      end
      local r1_21 = display.newGroup()
      local r2_21 = r3_11.CreateImage(r1_21, r3_11.sequenceNames.UnitListParts, r3_11.frameDefines.UnitListNoSummonPlate01, 0, 0)
      r0_21:insert(r1_21)
      r1_21:setReferencePoint(display.TopLeftReferencePoint)
      r1_21.x = 5
      r1_21.y = 63
      return r1_21
    end
    local function r18_11(r0_22)
      -- line: [196, 209] id: 22
      if r0_22 == nil then
        return 
      end
      local r1_22 = display.newGroup()
      local r2_22 = r3_11.CreateImage(r1_22, r3_11.sequenceNames.UnitListParts, r3_11.frameDefines.UnitListNoSummonPlate02, 0, 0)
      r0_22:insert(r1_22)
      r1_22.x = 5
      r1_22.y = 63
      return r1_22
    end
    local function r19_11(r0_23, r1_23, r2_23)
      -- line: [214, 277] id: 23
      local r3_23 = display.newGroup()
      if r2_23 == r0_0.PlaneEnable() then
        local r5_23 = util.LoadParts(r3_23, r8_11(string.format(r0_23, r1_23, "a")), 0, 0)
      elseif r2_23 == r0_0.PlaneDisable() then
        local r5_23 = util.LoadParts(r3_23, r8_11(string.format(r0_23, r1_23, "b")), 0, 0)
      elseif r2_23 == r0_0.UnlockEnable() then
        local r5_23 = util.LoadParts(r3_23, r8_11(string.format(r0_23, r1_23, "a")), 0, 0)
        r15_11(r3_23)
      elseif r2_23 == r0_0.UnlockDisable() then
        local r5_23 = util.LoadParts(r3_23, r8_11(string.format(r0_23, r1_23, "b")), 0, 0)
        r15_11(r3_23)
      elseif r2_23 == r0_0.Lock() then
        local r5_23 = util.LoadParts(r3_23, r8_11(string.format(r0_23, r1_23, "b")), 0, 0)
        r16_11(r3_23)
      elseif r2_23 == r0_0.NoSummon01() then
        local r5_23 = util.LoadParts(r3_23, r8_11(string.format(r0_23, r1_23, "b")), 0, 0)
        r17_11(r3_23)
      elseif r2_23 == r0_0.NoSummon02() then
        local r5_23 = util.LoadParts(r3_23, r8_11(string.format(r0_23, r1_23, "b")), 0, 0)
        r18_11(r3_23)
      elseif r2_23 == r0_0.EvoPlateDisable() then
        local r5_23 = util.LoadParts(r3_23, r8_11(string.format(r0_23, r1_23, "c")), 0, 0)
        r15_11(r3_23)
        r15_11(r3_23, true)
      elseif r2_23 == r0_0.EvoPlateEnableNormalOnly() then
        local r5_23 = util.LoadParts(r3_23, r8_11(string.format(r0_23, r1_23, "d")), 0, 0)
        r15_11(r3_23)
        r15_11(r3_23, true)
      elseif r2_23 == r0_0.EvoPlateEnable() then
        local r5_23 = util.LoadParts(r3_23, r8_11(string.format(r0_23, r1_23, "e")), 0, 0)
        r15_11(r3_23)
        r15_11(r3_23, true)
      else
        display.remove(r3_23)
        r3_23 = nil
      end
      return r3_23
    end
    local function r20_11(r0_24, r1_24)
      -- line: [282, 296] id: 24
      local r2_24 = nil	-- notice: implicit variable refs by block#[5]
      if r0_24 == r2_11.CharId.BlueMagicalFlower then
        r2_24 = r19_11(r5_11, 1, r1_24)
      elseif r0_24 == r2_11.CharId.Flare then
        r2_24 = nil
      else
        r2_24 = r19_11(r4_11, r0_24, r1_24)
      end
      return r2_24
    end
    local function r21_11()
      -- line: [301, 303] id: 25
      r3_11.Clean()
    end
    function r1_11.Clean()
      -- line: [317, 319] id: 27
      r21_11()
    end
    function r1_11.CreateUnitPlate(r0_28, r1_28)
      -- line: [324, 326] id: 28
      return r20_11(r0_28, r1_28)
    end
    function r1_11.CreateIconMp(r0_29)
      -- line: [331, 333] id: 29
      return r9_11(r0_29)
    end
    function r1_11.CreateIconCrystal(r0_30)
      -- line: [338, 340] id: 30
      return r10_11(r0_30)
    end
    function r1_11.CreateIconUpgrade2(r0_31)
      -- line: [345, 347] id: 31
      return r12_11(r0_31)
    end
    function r1_11.CreateIconUpgrade3(r0_32)
      -- line: [352, 354] id: 32
      return r13_11(r0_32)
    end
    function r1_11.CreateIconUpgrade4(r0_33)
      -- line: [359, 361] id: 33
      return r14_11(r0_33)
    end
    (function()
      -- line: [308, 309] id: 26
    end)()
    return r1_11
  end,
  PlateType = r0_0,
}
