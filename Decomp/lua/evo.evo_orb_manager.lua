-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("json")
local r1_0 = require("logic.pay_item_data")
local r2_0 = require("common.base_dialog")
local r4_0 = require("common.sprite_loader").new({
  imageSheet = "common.sprites.sprite_number01",
})
local r5_0 = 1
local r6_0 = 2
local r7_0 = 3
local r8_0 = 4
local r9_0 = 5
local r10_0 = "item"
local r11_0 = "item"
local r12_0 = 30
local r36_0 = nil	-- notice: implicit variable refs by block#[2]
if _G.IsSimulator then
  r12_0 = 2
end
local r13_0 = 0
local r14_0 = 0
local r15_0 = 0
local r16_0 = false
local r17_0 = 0
local function r18_0(r0_1)
  -- line: [44, 44] id: 1
  return "data/game/upgrade/" .. r0_1 .. ".png"
end
local function r19_0(r0_2)
  -- line: [46, 46] id: 2
  return r18_0(r0_2 .. _G.UILanguage)
end
local function r20_0(r0_3)
  -- line: [48, 48] id: 3
  return "data/game/confirm/" .. r0_3 .. ".png"
end
local function r21_0(r0_4)
  -- line: [50, 50] id: 4
  return r20_0(r0_4 .. _G.UILanguage)
end
local function r22_0(r0_5)
  -- line: [52, 52] id: 5
  return "data/stage/" .. r0_5 .. ".png"
end
local function r23_0(r0_6)
  -- line: [54, 54] id: 6
  return "data/game/plate/" .. r0_6 .. ".png"
end
local function r24_0(r0_7)
  -- line: [59, 61] id: 7
  r17_0 = r0_7
end
local function r25_0(r0_8)
  -- line: [66, 68] id: 8
  r17_0 = r17_0 + r0_8
end
local function r26_0(r0_9)
  -- line: [73, 87] id: 9
  r13_0 = db.LoadEvoData().orbRemain - r0_9
  if r13_0 <= 0 then
    r13_0 = 0
  end
  r16_0 = true
  db.SaveEvoData(r13_0, nil, nil)
end
local function r27_0(r0_10, r1_10)
  -- line: [92, 115] id: 10
  server.SaveUseOrb({
    usedOrb = r0_10,
  }, function(r0_11)
    -- line: [96, 114] id: 11
    local r1_11 = r0_0.decode(r0_11.response)
    if r1_11.status == 0 then
      native.showAlert("DefenseWitches", db.GetMessage(35), {
        "OK"
      })
      r1_11.result = nil
    elseif r1_11.result ~= nil then
      r13_0 = tonumber(r1_11.result)
    end
    if r1_10 ~= nil then
      r1_10(r1_11.result)
    end
  end)
end
local function r28_0(r0_12, r1_12, r2_12, r3_12)
  -- line: [120, 175] id: 12
  local function r4_12(r0_13)
    -- line: [122, 126] id: 13
    if r3_12 ~= nil then
      r3_12(r0_13)
    end
  end
  local r5_12 = db.LoadEvoData()
  local r6_12 = 0
  local r7_12 = r5_12.orbMax
  if r0_12 == r5_0 then
    r6_12 = r5_12.orbMax
  elseif r0_12 == r6_0 then
    r6_12 = math.ceil(r5_12.orbMax * 0.5)
  elseif r0_12 == r7_0 then
    local r8_12 = 1
    if r2_12 > 0 then
      r8_12 = 2
    end
    r6_12 = r5_12.orbRemain + r8_12
    r7_12 = r5_12.orbMax + r2_12
  elseif r0_12 == r8_0 then
    r7_12 = r5_12.orbMax + r2_12
    r6_12 = r7_12
  elseif r0_12 == r9_0 then
    r6_12 = r5_12.orbRemain + r1_12
  else
    r4_12(nil)
    return 
  end
  if r7_12 <= r6_12 then
    r6_12 = r7_12
  end
  r16_0 = true
  db.SaveEvoData(r6_12, r7_12, nil)
  r5_12 = db.LoadEvoData()
  r13_0 = r5_12.orbRemain
  r14_0 = r5_12.orbMax
  r4_12(nil)
end
local function r29_0(r0_14, r1_14, r2_14)
  -- line: [180, 203] id: 14
  server.SaveRecoveryOrb({
    type = r0_14,
    addMaxOrb = r1_14,
  }, function(r0_15)
    -- line: [185, 202] id: 15
    local r1_15 = r0_0.decode(r0_15.response)
    if r1_15.status == 0 then
      native.showAlert("DefenseWitches", db.GetMessage(35), {
        "OK"
      })
      r1_15.result = nil
    else
      r14_0 = tonumber(r1_15.result[1])
      r13_0 = tonumber(r1_15.result[2])
    end
    if r2_14 ~= nil then
      r2_14(r1_15.result)
    end
  end)
end
local function r30_0()
  -- line: [208, 215] id: 16
  local r0_16 = db.LoadEvoData()
  r14_0 = r0_16.orbMax
  r13_0 = r0_16.orbRemain
  r15_0 = r0_16.usedTime
end
local function r31_0(r0_17)
  -- line: [220, 252] id: 17
  server.LoadOrbInfo(function(r0_18)
    -- line: [221, 251] id: 18
    DebugPrint("server.LoadOrbInfo")
    DebugPrint("    params : ")
    local r1_18 = nil
    if r0_18 ~= nil and r0_18.response ~= nil then
      r1_18 = r0_0.decode(r0_18.response)
      if r1_18.status == 0 then
        native.showAlert("DefenseWitches", db.GetMessage(35), {
          "OK"
        })
        r1_18.result = nil
      else
        r15_0 = tonumber(r1_18.result[3])
      end
    end
    if r0_17 ~= nil then
      if r1_18 ~= nil and r1_18.result ~= nil then
        r0_17(r1_18.result)
      else
        r0_17(nil)
      end
    end
  end)
end
local function r32_0(r0_19)
  -- line: [257, 281] id: 19
  server.GetServerTime(function(r0_20)
    -- line: [258, 280] id: 20
    local r1_20 = nil	-- notice: implicit variable refs by block#[9]
    if r0_20 ~= nil and r0_20.response ~= nil then
      local r2_20 = r0_0.decode(r0_20.response)
      if r2_20 and r2_20.status == 0 then
        native.showAlert("DefenseWitches", db.GetMessage(35), {
          "OK"
        })
      elseif r2_20 and r2_20.result ~= nil then
        r1_20 = tonumber(r2_20.result)
      end
    end
    if r0_19 ~= nil then
      r0_19(r1_20)
    end
  end)
end
local function r33_0(r0_21)
  -- line: [286, 304] id: 21
  r32_0(function(r0_22)
    -- line: [287, 303] id: 22
    local r1_22 = nil	-- notice: implicit variable refs by block#[4]
    if r0_22 ~= nil then
      r30_0()
      r1_22 = math.floor((r0_22 - r15_0) / 60 * r12_0)
      if r14_0 - r13_0 <= r1_22 then
        r1_22 = r14_0 - r13_0
      end
    end
    if r0_21 ~= nil then
      r0_21(r1_22, r0_22)
    end
  end)
end
local function r34_0(r0_23, r1_23, r2_23, r3_23, r4_23)
  -- line: [309, 329] id: 23
  local r5_23 = nil	-- notice: implicit variable refs by block#[3]
  if r4_23 == nil then
    function r5_23(r0_24, r1_24)
      -- line: [312, 312] id: 24
      return true
    end
  else
    r5_23 = r4_23
  end
  local r6_23 = display.newGroup()
  display.newImage(r6_23, r1_23, 0, 0, true)
  r6_23:setReferencePoint(display.TopLeftReferencePoint)
  r6_23.x = r2_23
  r6_23.y = r3_23
  r6_23.isVisible = true
  r6_23.touch = r5_23
  r6_23:addEventListener("touch", r6_23)
  r6_23:setReferencePoint(display.CenterReferencePoint)
  r0_23:insert(r6_23)
  return r6_23
end
local function r35_0(r0_25, r1_25, r2_25, r3_25, r4_25, r5_25, r6_25, r7_25)
  -- line: [343, 424] id: 25
  local function r8_25(r0_26)
    -- line: [345, 350] id: 26
    if r6_25 ~= nil then
      r6_25(r0_26)
    end
  end
  local r9_25 = {
    {
      r2_25,
      1,
      r4_25
    }
  }
  require("ui.coin_module").new({
    useItemList = r9_25,
  }).OpenWithTitle(r0_25, r1_25, nil, function(r0_27)
    -- line: [363, 417] id: 27
    _G.metrics.crystal_orb_upgrade(r9_25)
    local r1_27 = {
      type = r3_25,
    }
    if r5_25 ~= nil then
      r1_27.addMaxOrb = r5_25
    end
    local r2_27 = db.LoadEvoData()
    r28_0(r3_25, 0, r5_25, nil)
    if r3_25 ~= r7_0 then
      local r3_27 = nil
      local r4_27 = nil
      if r3_25 == r8_0 then
        r4_27 = r11_0
        r3_27 = {
          item = r2_25,
          have_orb = r2_27.orbRemain,
          max_orb = r2_27.orbMax,
          have_exp = r2_27.exp,
        }
        db.SaveToServer2(_G.UserToken)
      else
        r4_27 = r10_0
        r3_27 = {
          item = r2_25,
          coin = r4_25,
          have_orb = r2_27.orbRemain,
          max_orb = r2_27.orbMax,
          have_exp = r2_27.exp,
          hp = _G.HP,
          wave = _G.WaveNr,
          score = _G.Score,
        }
      end
    end
    r8_25(true)
  end, function(r0_28)
    -- line: [418, 423] id: 28
    if r7_25 ~= nil then
      r7_25(r6_25)
    end
  end)
end
function r36_0(r0_29)
  -- line: [429, 610] id: 29
  local r1_29 = display.newGroup()
  local r2_29 = r2_0.Create(r1_29, 635, 320, {
    64,
    96,
    64
  }, true, true)
  r2_29.isVisible = false
  local r3_29 = r1_0.getItemData(r1_0.pay_item_data.OrbFullRecovery)[2]
  local r4_29 = r1_0.getItemData(r1_0.pay_item_data.OrbHalfRecovery)[2]
  local r5_29 = r1_0.getItemData(r1_0.pay_item_data.OrbMaxUp)
  local function r6_29()
    -- line: [441, 446] id: 30
    if r1_29 ~= nil then
      display.remove(r1_29)
      r1_29 = nil
    end
  end
  local function r7_29(r0_31)
    -- line: [449, 455] id: 31
    r6_29()
    if r0_29 ~= nil then
      r0_29(r0_31)
    end
  end
  local function r8_29()
    -- line: [458, 468] id: 32
    if game ~= nil then
      game.PossessingCrystalVisible(false)
    end
    if r2_29 ~= nil then
      display.remove(r2_29)
      r2_29 = nil
    end
  end
  local function r9_29(r0_33)
    -- line: [471, 477] id: 33
    r2_0.FadeOutMask(r1_29, 0)
    if r0_29 ~= nil then
      r0_29(r0_33)
    end
    r6_29()
  end
  local function r10_29()
    -- line: [480, 485] id: 34
    r2_0.FadeOutMask(r1_29, 0)
    r8_29()
    r6_29()
    r36_0(r0_29)
  end
  local function r11_29()
    -- line: [488, 492] id: 35
    r8_29()
    r35_0(r1_29, nil, r1_0.pay_item_data.OrbFullRecovery, r5_0, r3_29, nil, r9_29, r10_29)
    return true
  end
  local function r12_29()
    -- line: [495, 499] id: 36
    r8_29()
    r35_0(r1_29, nil, r1_0.pay_item_data.OrbHalfRecovery, r6_0, r4_29, nil, r9_29, r10_29)
    return true
  end
  local function r13_29()
    -- line: [502, 506] id: 37
    r8_29()
    r35_0(r1_29, 360, r1_0.pay_item_data.OrbMaxUp, r8_0, r5_29[2], r5_29[3], r9_29, r10_29)
    return true
  end
  local function r14_29()
    -- line: [509, 513] id: 38
    r8_29()
    r7_29(false)
    return true
  end
  local r15_29 = db.GetMessage(358)
  local r16_29 = util.StringSplit(db.GetMessage(359), "\\n")
  r2_0.CreateDialogLine(r2_29, 104, 420)
  util.MakeCenterText(r2_29, 20, {
    0,
    60,
    r2_29.width,
    0
  }, {
    255,
    255,
    255
  }, {
    0,
    0,
    0
  }, r15_29)
  util.MakeCenterText(r2_29, 18, {
    0,
    130,
    r2_29.width,
    0
  }, {
    255,
    255,
    255
  }, {
    0,
    0,
    0
  }, r16_29[1])
  if #r16_29 > 1 then
    util.MakeCenterText(r2_29, 18, {
      0,
      154,
      r2_29.width,
      0
    }, {
      255,
      255,
      255
    }, {
      0,
      0,
      0
    }, r16_29[2])
  end
  local r18_29 = util.MakeText3({
    rtImg = r34_0(r2_29, r19_0("chargefull_"), 50, 188, r11_29),
    size = 26,
    color = {
      255,
      255,
      68
    },
    shadow = {
      0,
      0,
      0
    },
    diff_x = 1,
    diff_y = 2,
    msg = util.ConvertDisplayCrystal(r3_29),
  })
  r18_29:setReferencePoint(display.TopLeftReferencePoint)
  r18_29.x = 62
  r18_29.y = 42
  local r20_29 = util.MakeText3({
    rtImg = r34_0(r2_29, r19_0("chargehalf_"), 240, 188, r12_29),
    size = 26,
    color = {
      255,
      255,
      68
    },
    shadow = {
      0,
      0,
      0
    },
    diff_x = 1,
    diff_y = 2,
    msg = util.ConvertDisplayCrystal(r4_29),
  })
  r20_29:setReferencePoint(display.TopLeftReferencePoint)
  r20_29.x = 62
  r20_29.y = 42
  local r21_29 = display.newGroup()
  display.newImage(r21_29, r22_0("add_orb"), 0, 0, true)
  r21_29:setReferencePoint(display.TopLeftReferencePoint)
  util.LoadBtnGroup({
    group = r21_29,
    cx = 430,
    cy = 195,
    func = r13_29,
    show = true,
  })
  util.LoadParts(r21_29, r18_0("icon_crystal"), 42, 38)
  local r22_29 = util.MakeText3({
    rtImg = r21_29,
    size = 26,
    color = {
      255,
      255,
      68
    },
    shadow = {
      0,
      0,
      0
    },
    diff_x = 1,
    diff_y = 2,
    msg = util.ConvertDisplayCrystal(r5_29[2]),
  })
  r22_29:setReferencePoint(display.TopLeftReferencePoint)
  r22_29.x = 70
  r22_29.y = 34
  r21_29:setReferencePoint(display.CenterReferencePoint)
  r2_29:insert(r21_29)
  local r23_29 = util.LoadBtn({
    rtImg = r2_29,
    fname = r23_0("close"),
    x = 0,
    y = 0,
    func = r14_29,
  })
  r23_29:setReferencePoint(display.CenterReferencePoint)
  r23_29.x = r2_29.width - 30
  r23_29.y = 30
  _G.DialogRoot:insert(r1_29)
  r2_0.FadeInMask(r1_29, {
    0,
    0,
    0,
    0.3
  }, 300)
  r2_0.ZoomInEffect(r1_29, r2_29)
  if game ~= nil then
    game.PossessingCrystalVisible(true)
  end
end
local function r37_0(r0_39)
  -- line: [616, 650] id: 39
  local r1_39 = display.newGroup()
  local function r2_39()
    -- line: [620, 628] id: 40
    r2_0.FadeOutMask(r1_39, 300, function()
      -- line: [623, 627] id: 41
      if r0_39 ~= nil then
        r0_39()
      end
    end)
  end
  local function r3_39()
    -- line: [631, 639] id: 42
    r2_0.FadeOutMask(r1_39, 300, function()
      -- line: [634, 638] id: 43
      if r0_39 ~= nil then
        r0_39()
      end
    end)
  end
  local r4_39 = r1_0.getItemData(r1_0.pay_item_data.OrbMaxUp)
  local r5_39 = r4_39[2]
  local r6_39 = r4_39[3]
  r2_0.FadeInMask(r1_39, {
    0,
    0,
    0,
    0.3
  }, 300)
  r35_0(r1_39, 360, r1_0.pay_item_data.OrbMaxUp, r8_0, r5_39, r6_39, r2_39, r3_39)
end
local function r39_0(r0_45)
  -- line: [668, 670] id: 45
end
local function r42_0(r0_48)
  -- line: [689, 704] id: 48
  r33_0(function(r0_49, r1_49)
    -- line: [690, 703] id: 49
    if r0_49 ~= nil and 0 < r0_49 and r1_49 ~= nil then
      r13_0 = r13_0 + r0_49
      db.SaveEvoData(r13_0, nil, nil, r1_49)
    end
    if r0_48 ~= nil then
      r0_48(r0_49, r1_49)
    end
  end)
end
local function r49_0(r0_57)
  -- line: [779, 781] id: 57
  r31_0(r0_57)
end
local r58_0 = {
  Init = function()
    -- line: [655, 663] id: 44
    r13_0 = 0
    r14_0 = 0
    r15_0 = 0
    r17_0 = 0
    r30_0()
  end,
  GetOrbDataForServer = r49_0,
  GetRemainOrb = function()
    -- line: [675, 677] id: 46
    return r13_0
  end,
  GetMaxOrb = function()
    -- line: [682, 684] id: 47
    return r14_0
  end,
  GetRecoveryOrb = r42_0,
  GetFormatFullRecoveryTime = function(r0_50)
    -- line: [709, 735] id: 50
    r42_0(function(r0_51, r1_51)
      -- line: [710, 734] id: 51
      local r2_51 = 0
      local r3_51 = 0
      local r4_51 = 0
      if r1_51 ~= nil and r13_0 < r14_0 then
        local r5_51 = 0
        local r6_51 = 0
        r6_51 = (r14_0 - r13_0) * r12_0 * 60 + r15_0 - r1_51
        r2_51 = math.floor(r6_51 / 3600)
        r5_51 = r6_51 - r2_51 * 3600
        r3_51 = math.floor(r5_51 / 60)
        if r5_51 % 60 > 0 then
          r3_51 = r3_51 + 1
        end
        r4_51 = math.floor(r6_51 - r2_51 * 3600 - r3_51 * 60)
      end
      if r0_50 ~= nil then
        r0_50(string.format("%d:%02d", r2_51, r3_51))
      end
    end)
  end,
  SetUsedOrb = function(r0_52)
    -- line: [740, 742] id: 52
    r26_0(r0_52)
  end,
  SetRecoveryOrb = function(r0_53, r1_53, r2_53, r3_53)
    -- line: [747, 749] id: 53
    r28_0(r0_53, r1_53, r2_53, r3_53)
  end,
  IsUseOrb = function(r0_54)
    -- line: [754, 760] id: 54
    if r13_0 < r0_54 then
      return false
    end
    return true
  end,
  ShowRecoveryOrbDialog = function(r0_55)
    -- line: [765, 767] id: 55
    r36_0(r0_55)
  end,
  ShowOrbMaxUpDialog = function(r0_56)
    -- line: [772, 774] id: 56
    r37_0(r0_56)
  end,
}
r58_0.GetOrbDataForServer = r49_0
function r58_0.ResetUsedOrbFlag()
  -- line: [786, 788] id: 58
  r16_0 = false
end
function r58_0.SaveUsedOrbTime()
  -- line: [794, 809] id: 59
  if r16_0 == false or r14_0 <= r13_0 then
    DebugPrint("evo_orb_manager.SaveUsedOrbTime:サーバタイム取得無し")
    return 
  end
  r32_0(function(r0_60)
    -- line: [802, 808] id: 60
    DebugPrint("= SaveUsedOrbTime")
    if r0_60 ~= nil then
      db.SaveEvoData(nil, nil, nil, r0_60)
      r15_0 = r0_60
    end
  end)
end
function r58_0.GetRecoveryTypeRankup()
  -- line: [814, 816] id: 61
  return r7_0
end
function r58_0.GetRecoveryTypeTreasurebox()
  -- line: [821, 823] id: 62
  return r9_0
end
function r58_0.AddUsedOrbCount(r0_63)
  -- line: [828, 830] id: 63
  r25_0(r0_63)
end
function r58_0.SetUsedOrbCount(r0_64)
  -- line: [835, 837] id: 64
  r24_0(r0_64)
end
function r58_0.GetUsedOrbCount()
  -- line: [842, 844] id: 65
  return r17_0
end
function r58_0.AddMaxOrb(r0_66)
  -- line: [848, 850] id: 66
  r28_0(r8_0, 0, r0_66, nil)
end
return r58_0
