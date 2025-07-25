-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
return {
  new = function(r0_1)
    -- line: [9, 542] id: 1
    local r1_1 = {}
    local r2_1 = require("common.base_dialog")
    local r3_1 = require("game.medal_manager")
    local r4_1 = require("resource.char_define")
    local r5_1 = {
      UnitListParts = require("game.unit_list_parts"),
    }
    local r6_1 = 19
    local r7_1 = 16
    local r8_1 = 15
    local r9_1 = {
      "normal01_special",
      "normal02_gold",
      "normal03_silver",
      "normal04_bronze",
      "normal05_hp1",
      "normal06_ex1",
      "normal07_ex2",
      "normal08_ex3"
    }
    local r10_1 = {
      typeNone = -1,
      type00 = 0,
      type03 = 1,
      type04 = 2,
    }
    local r11_1 = {
      crystal = 1,
      exp = 2,
      hp = 3,
    }
    local r12_1 = {
      "medalinfo_icon_crystal",
      "medalinfo_icon_exp",
      "medalinfo_icon_hp"
    }
    local r13_1 = nil
    local r14_1 = nil
    local r15_1 = nil
    local function r16_1(r0_2)
      -- line: [71, 71] id: 2
      return "data/medal_info/" .. r0_2 .. ".png"
    end
    local function r17_1(r0_3)
      -- line: [72, 72] id: 3
      return r16_1(r0_3 .. _G.UILanguage)
    end
    local function r18_1(r0_4)
      -- line: [73, 73] id: 4
      return "data/char/unit_list/" .. r0_4 .. ".png"
    end
    local function r19_1(r0_5)
      -- line: [74, 74] id: 5
      return "data/stage/" .. r0_5 .. ".png"
    end
    local function r20_1(r0_6)
      -- line: [75, 75] id: 6
      return "data/game/unit/" .. r0_6 .. ".png"
    end
    local function r21_1(r0_7)
      -- line: [76, 76] id: 7
      return "data/game/upgrade/" .. r0_7 .. ".png"
    end
    local function r22_1()
      -- line: [81, 86] id: 8
      if r13_1 ~= nil then
        display.remove(r13_1)
        r13_1 = nil
      end
    end
    local function r23_1(r0_9)
      -- line: [91, 98] id: 9
      if r13_1 == nil or r0_9 == nil then
        return 
      end
      r13_1.isVisible = r0_9
      r2_1.SlideInLeftEffect(r13_1, r14_1)
    end
    local function r24_1(r0_10)
      -- line: [103, 107] id: 10
      r2_1.SlideOutRightEffect(r13_1, r14_1, function()
        -- line: [104, 106] id: 11
        r22_1()
      end)
    end
    local function r25_1(r0_12, r1_12)
      -- line: [112, 125] id: 12
      local r2_12 = nil	-- notice: implicit variable refs by block#[3]
      local r3_12 = nil	-- notice: implicit variable refs by block#[3]
      if _G.UILanguage == "jp" then
        r2_12 = native.systemFontBold
        r3_12 = r6_1
      else
        r2_12 = native.systemFont
        r3_12 = r7_1
      end
      return util.MakeText3({
        rtImg = r0_12,
        size = r3_12,
        color = {
          255,
          255,
          255
        },
        shadow = {
          0,
          0,
          0
        },
        diff_x = 1,
        diff_y = 2,
        font = r2_12,
        msg = r1_12,
      })
    end
    local function r26_1(r0_13, r1_13)
      -- line: [130, 190] id: 13
      local r2_13 = {
        condition = {
          {},
          {
            text = string.format("%d%s", r3_1.ClearHp.G, db.GetMessage(400)),
            reward = r3_1.AchievementReward[r3_1.MedalId.G],
            clearFlag = false,
          },
          {
            text = string.format("%d%s", r3_1.ClearHp.S, db.GetMessage(400)),
            reward = r3_1.AchievementReward[r3_1.MedalId.S],
            clearFlag = false,
          },
          {
            text = string.format("%d%s", r3_1.ClearHp.B, db.GetMessage(400)),
            reward = r3_1.AchievementReward[r3_1.MedalId.B],
            clearFlag = false,
          },
          {
            text = string.format("%d", r3_1.ClearHp.Hp1),
            reward = r3_1.AchievementReward[r3_1.MedalId.Hp1],
            clearFlag = false,
          },
          {
            text = nil,
            reward = nil,
            clearFlag = false,
          },
          {
            text = nil,
            reward = nil,
            clearFlag = false,
          },
          {
            text = nil,
            reward = nil,
            clearFlag = false,
          }
        },
      }
      local r3_13 = db.GetSpecialAchievementByMapAndStage(r0_13, r1_13)
      for r7_13, r8_13 in pairs(r4_1.CharId) do
        local r9_13 = r3_13[r8_13]
        if r9_13 == nil then
          r9_13 = r10_1.type03
          r3_13[r8_13] = r9_13
        end
        r9_13 = table.indexOf(r4_1.PurchaseCharId, r8_13)
        if r9_13 ~= nil then
          r9_13 = r10_1.type04
          r3_13[r8_13] = r9_13
        end
        r9_13 = r4_1.CharId.BlueMagicalFlower
        if r8_13 == r9_13 then
          r9_13 = r10_1.typeNone
          r3_13[r8_13] = r9_13
        end
      end
      r2_13.condition[r3_1.MedalId.Sp] = r3_13
      for r7_13 = r3_1.MedalId.Ex1, r3_1.MedalId.Ex3, 1 do
        local r8_13 = r7_13 - r3_1.MedalId.Ex1 + 1
        local r9_13 = r15_1.GetConditionString(r8_13)
        local r10_13 = r15_1.GetConditionByExId(r8_13)
        if r9_13 ~= nil and r10_13 ~= nil then
          r2_13.condition[r7_13].text = r9_13
          r2_13.condition[r7_13].reward = r10_13.reward
        end
      end
      local r4_13 = db.LoadAchievementStage(_G.UserID, r0_13, r1_13)
      for r8_13, r9_13 in pairs(r3_1.MedalId) do
        local r10_13 = r4_13[r9_13]
        if r10_13 ~= nil then
          r10_13 = r2_13.condition[r9_13]
          local r11_13 = r4_13[r9_13]
          r10_13.clearFlag = r11_13
        end
      end
      return r2_13
    end
    local function r27_1(r0_14, r1_14, r2_14)
      -- line: [195, 205] id: 14
      local r3_14 = display.newGroup()
      local r4_14 = util.LoadParts(r3_14, r16_1("text_medalget"), 0, 0)
      r4_14:setReferencePoint(display.TopCenterReferencePoint)
      r4_14.x = r1_14
      r4_14.y = r2_14 - r4_14.height
      r0_14:insert(r3_14)
      return r3_14
    end
    local function r28_1(r0_15, r1_15)
      -- line: [210, 263] id: 15
      local r2_15 = r5_1.UnitListParts.new()
      local r3_15 = display.newGroup()
      local r4_15 = 0
      local r5_15 = 0
      local r6_15 = nil
      for r10_15, r11_15 in pairs(r4_1.UnitPanelOrder) do
        local r12_15 = nil
        if r1_15[r11_15] == r10_1.type03 then
          r12_15 = r2_15.CreateUnitPlate(r11_15, r5_1.UnitListParts.PlateType.UnlockEnable())
          if r12_15 ~= nil then
            local r13_15 = display.newGroup()
            local r14_15 = r2_15.CreateIconUpgrade3(r13_15)
            local r15_15 = r25_1(r13_15, db.GetMessage(401))
            r15_15:setReferencePoint(display.CenterLeftReferencePoint)
            r15_15.x = r14_15.width - 5
            r15_15.y = r14_15.height * 0.5
            r12_15:insert(r13_15)
            r13_15:setReferencePoint(display.CenterReferencePoint)
            r13_15.x = r12_15.width * 0.5
            r13_15.y = r12_15.height - 24
          end
        elseif r1_15[r11_15] == r10_1.type04 or r11_15 == r4_1.CharId.BlueMagicalFlower then
          r12_15 = r2_15.CreateUnitPlate(r11_15, r5_1.UnitListParts.PlateType.PlaneEnable())
        end
        if r12_15 ~= nil then
          r3_15:insert(r12_15)
          r12_15:setReferencePoint(display.TopLeftReferencePoint)
          r12_15.x = r4_15
          r12_15.y = r5_15
          r4_15 = r4_15 + 88
        end
        if r4_15 >= 880 then
          r4_15 = 0
          r5_15 = r5_15 + 104
        end
      end
      r3_15:scale(0.9, 0.9)
      r0_15:insert(r3_15)
      r2_15.Clean()
      return r3_15
    end
    local function r29_1(r0_16, r1_16)
      -- line: [268, 308] id: 16
      local r2_16 = display.newGroup()
      local r3_16 = r25_1(r2_16, db.GetMessage(398) .. db.GetMessage(397))
      r3_16:setReferencePoint(display.TopLeftReferencePoint)
      r3_16.x = 20
      r3_16.y = 0
      local r4_16 = r25_1(r2_16, db.GetMessage(399))
      r4_16:setReferencePoint(display.TopLeftReferencePoint)
      r4_16.x = 660
      r4_16.y = r3_16.y
      local r5_16 = util.LoadParts(r2_16, r16_1("medalinfo_icon_crystal"), 0, 0)
      r5_16:setReferencePoint(display.CenterLeftReferencePoint)
      r5_16.x = r4_16.x + r4_16.width - 10
      r5_16.y = r4_16.y + r4_16.height * 0.5 - 5
      local r6_16 = r25_1(r2_16, string.format("+%d", util.ConvertDisplayCrystal(r3_1.AchievementReward[1].value)))
      r6_16:setReferencePoint(display.TopLeftReferencePoint)
      r6_16.x = r5_16.x + r5_16.width
      r6_16.y = r4_16.y
      local r7_16 = util.LoadParts(r2_16, r19_1("normal01_special"), 0, 0)
      r7_16:setReferencePoint(display.TopLeftReferencePoint)
      r7_16.x = 0
      r7_16.y = 106
      local r8_16 = r28_1(r2_16, r1_16)
      r8_16:setReferencePoint(display.CenterLeftReferencePoint)
      r8_16.x = 70
      r8_16.y = r7_16.y + r7_16.height * 0.5
      if r1_16.clearFlag == true then
        r27_1(r2_16, r7_16.x + r7_16.width * 0.5, r7_16.y + r7_16.height)
      end
      r0_16:insert(r2_16)
      return r2_16
    end
    local function r30_1(r0_17, r1_17, r2_17)
      -- line: [314, 368] id: 17
      if r2_17 == nil or util.IsContainedTable(r2_17, "text") == false or util.IsContainedTable(r2_17.reward, "value") == false then
        return nil
      end
      local r3_17 = display.newGroup()
      local r4_17 = util.LoadParts(r3_17, r19_1(r9_1[r1_17]), 0, 0)
      local r5_17 = display.newGroup()
      local r6_17 = r25_1(r5_17, db.GetMessage(398))
      local r7_17 = r25_1(r5_17, r2_17.text)
      local r8_17 = util.LoadParts(r5_17, r16_1("medalinfo_icon_hp"), 0, 0)
      r6_17:setReferencePoint(display.TopLeftReferencePoint)
      r6_17.x = 0
      r6_17.y = 0
      r8_17:setReferencePoint(display.CenterLeftReferencePoint)
      r8_17.x = r6_17.x + r6_17.width - 10
      r8_17.y = r6_17.y + r6_17.height * 0.5 - 3
      r7_17:setReferencePoint(display.TopLeftReferencePoint)
      r7_17.x = r8_17.x + r8_17.width
      r7_17.y = r6_17.y
      local r9_17 = r25_1(r5_17, db.GetMessage(399))
      local r10_17 = r25_1(r5_17, string.format("+%d", util.ConvertDisplayCrystal(r2_17.reward.value)))
      local r11_17 = util.LoadParts(r5_17, r16_1("medalinfo_icon_crystal"), 0, 0)
      r9_17:setReferencePoint(display.TopLeftReferencePoint)
      r9_17.x = 0
      r9_17.y = r8_17.height - 3
      r11_17:setReferencePoint(display.CenterLeftReferencePoint)
      r11_17.x = r8_17.x
      r11_17.y = r9_17.y + r9_17.height * 0.5 - 3
      r10_17:setReferencePoint(display.TopLeftReferencePoint)
      r10_17.x = r7_17.x
      r10_17.y = r9_17.y
      r3_17:insert(r5_17)
      r5_17:setReferencePoint(display.CenterLeftReferencePoint)
      r5_17.x = r4_17.width - 5
      r5_17.y = r4_17.height * 0.5
      if r2_17.clearFlag == true then
        r27_1(r3_17, r4_17.x + r4_17.width * 0.5, r4_17.y + r4_17.height)
      end
      r0_17:insert(r3_17)
      return r3_17
    end
    local function r31_1(r0_18, r1_18, r2_18)
      -- line: [373, 434] id: 18
      if r2_18 == nil or util.IsContainedTable(r2_18, "text") == false or util.IsContainedTable(r2_18.reward, "value") == false then
        return nil
      end
      local r3_18 = display.newGroup()
      local r4_18 = util.LoadParts(r3_18, r19_1(r9_1[r1_18]), 0, 0)
      local r5_18 = display.newGroup()
      local r6_18 = r25_1(r5_18, r2_18.text)
      r6_18:setReferencePoint(display.TopLeftReferencePoint)
      r6_18.x = 0
      r6_18.y = 0
      local r7_18 = nil
      local r8_18 = nil
      if r2_18.reward.type == r11_1.crystal then
        r7_18 = util.ConvertDisplayCrystal(r2_18.reward.value)
        r8_18 = r12_1[r2_18.reward.type]
      elseif r2_18.reward.type == r11_1.exp then
        r7_18 = r2_18.reward.value
        r8_18 = r12_1[r2_18.reward.type]
      else
        r7_18 = r2_18.reward.value
        r8_18 = r12_1[r11_1.hp]
      end
      local r9_18 = r25_1(r5_18, db.GetMessage(399))
      local r10_18 = r25_1(r5_18, string.format("+%d", r7_18))
      local r11_18 = util.LoadParts(r5_18, r16_1(r8_18), 0, 0)
      r9_18:setReferencePoint(display.TopLeftReferencePoint)
      r9_18.x = 580
      r9_18.y = 0
      r11_18:setReferencePoint(display.CenterLeftReferencePoint)
      r11_18.x = r9_18.x + r9_18.width
      r11_18.y = r9_18.y + r9_18.height * 0.5
      r10_18:setReferencePoint(display.TopLeftReferencePoint)
      r10_18.x = r11_18.x + r11_18.width
      r10_18.y = 0
      r3_18:insert(r5_18)
      r5_18:setReferencePoint(display.CenterLeftReferencePoint)
      r5_18.x = r4_18.width
      r5_18.y = r4_18.height * 0.5
      if r2_18.clearFlag == true then
        r27_1(r3_18, r4_18.x + r4_18.width * 0.5, r4_18.y + r4_18.height)
      end
      r0_18:insert(r3_18)
      return r3_18
    end
    local function r32_1(r0_19, r1_19)
      -- line: [439, 495] id: 19
      local r2_19 = r2_1.Create(r13_1, 960, 650, {
        direction = "down",
        color = {
          {
            118,
            71,
            119
          },
          {
            50,
            39,
            66
          }
        },
        alpha = 1,
      }, true, false)
      r2_19.isVisible = false
      r2_19:setReferencePoint(display.CenterLeftReferencePoint)
      r2_19.x = 0
      local r3_19 = r2_19.width * 0.5
      local r4_19 = nil
      local r5_19 = nil
      local r6_19 = nil
      local r7_19 = util.LoadParts(r2_19, r17_1("title_medalinfo_"), 0, 0)
      r7_19:setReferencePoint(display.TopCenterReferencePoint)
      r7_19.x = r3_19
      r7_19.y = 40
      r2_1.CreateDialogLine(r2_19, r7_19.y + r7_19.height + 10, r7_19.width - 40)
      local r8_19 = r29_1(r2_19, r1_19.condition[r3_1.MedalId.Sp])
      r8_19:setReferencePoint(display.TopLeftReferencePoint)
      r8_19.x = 46
      r8_19.y = r7_19.y + r7_19.height + 17
      r4_19 = 46
      for r12_19 = r3_1.MedalId.G, r3_1.MedalId.Hp1, 1 do
        local r13_19 = r30_1(r2_19, r12_19, r1_19.condition[r12_19])
        if r13_19 ~= nil then
          r13_19:setReferencePoint(display.TopLeftReferencePoint)
          r13_19.x = r4_19
          r13_19.y = 354
          r4_19 = r4_19 + 220
        end
      end
      r5_19 = 430
      for r12_19 = r3_1.MedalId.Ex1, r3_1.MedalId.Ex3, 1 do
        local r13_19 = r31_1(r2_19, r12_19, r1_19.condition[r12_19])
        if r13_19 ~= nil then
          r13_19:setReferencePoint(display.TopLeftReferencePoint)
          r13_19.x = 46
          r13_19.y = r5_19
          r5_19 = r5_19 + 60
        end
      end
      r2_1.CreateCloseButton(r2_19, r2_19.width - 56, 56, r24_1)
      return r2_19
    end
    function r1_1.Show(r0_21)
      -- line: [524, 526] id: 21
      r23_1(r0_21)
    end
    function r1_1.Clean()
      -- line: [531, 533] id: 22
      r22_1()
    end
    (function()
      -- line: [500, 516] id: 20
      if util.IsContainedTable(r0_1, "mapId") == false or util.IsContainedTable(r0_1, "stageId") == false then
        DebugPrint("パラメータ異常")
        r1_1 = nil
        return 
      end
      r15_1 = r3_1.new({
        mapId = r0_1.mapId,
        stageId = r0_1.stageId,
      })
      local r0_20 = r26_1(r0_1.mapId, r0_1.stageId)
      r13_1 = display.newGroup()
      r14_1 = r32_1(r13_1, r0_20)
      r13_1:insert(r14_1)
    end)()
    return r1_1
  end,
}
