-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("game.medal_condition_dialog")
local r1_0 = require("logic.pay_item_data")
return {
  new = function(r0_1)
    -- line: [12, 557] id: 1
    local r1_1 = {}
    local r2_1 = nil
    local r3_1 = nil
    local r4_1 = nil
    local r5_1 = nil
    local r6_1 = nil
    local r7_1 = nil
    local r8_1 = nil
    local r9_1 = nil
    local function r10_1()
      -- line: [36, 49] id: 2
      local r0_2 = db.UsingItemList(_G.UserID)
      local r1_2 = {}
      for r5_2, r6_2 in pairs(r0_2) do
        r1_2[r6_2[1]] = true
      end
      if r1_2[38] then
        r5_1.isVisible = false
        r6_1.isVisible = true
      else
        r5_1.isVisible = true
        r6_1.isVisible = false
      end
    end
    local function r11_1(r0_3)
      -- line: [54, 81] id: 3
      local r1_3 = {}
      table.insert(r1_3, {
        38,
        1,
        r1_0.getItemData(38)[2]
      })
      require("ui.coin_module").new({
        useItemList = r1_3,
      }).Open(r2_1, db.GetMessage(119), function(r0_4)
        -- line: [64, 73] id: 4
        _G.metrics.crystal_magic_powerup_in_stage(_G.MapSelect, _G.StageSelect, r1_3)
        local r1_4 = {}
        for r5_4, r6_4 in pairs(r0_4) do
          table.insert(r1_4, r6_4[1])
        end
        game.IsPowerup(r1_4)
        r10_1()
        game.PossessingCrystalVisible(false)
      end, function(r0_5)
        -- line: [74, 77] id: 5
        DebugPrint("push cancel")
        game.PossessingCrystalVisible(false)
      end)
      game.PossessingCrystalVisible(true)
      return true
    end
    local function r12_1()
      -- line: [86, 89] id: 6
      game.OpenPowerupDialog()
      return true
    end
    local function r13_1()
      -- line: [94, 97] id: 7
      game.OpenRewindDialog()
      return true
    end
    local function r14_1()
      -- line: [102, 105] id: 8
      game.OpenRestartDialog()
      return true
    end
    local function r15_1()
      -- line: [110, 113] id: 9
      game.BackToMapDialog()
      return true
    end
    local function r16_1(r0_10)
      -- line: [118, 124] id: 10
      local r1_10 = require("stage_ui.stage_help")
      r1_10.Init()
      r1_10.Open()
      return true
    end
    local function r17_1(r0_11)
      -- line: [129, 132] id: 11
      game.OpenOption()
      return true
    end
    local function r18_1(r0_12)
      -- line: [137, 147] id: 12
      sound.PlaySE(2)
      DebugPrint(_G.MapSelect)
      DebugPrint(_G.StageSelect)
      r0_0.new({
        mapId = _G.MapSelect,
        stageId = _G.StageSelect,
      }).Show(true)
      return true
    end
    local function r19_1(r0_13, r1_13, r2_13, r3_13, r4_13)
      -- line: [152, 177] id: 13
      local r5_13 = 12
      local r6_13 = r2_13
      local r7_13 = r3_13 + string.len(tostring(r2_13)) * r5_13
      if r6_13 < 1 then
        r0_13.CreateImage(r1_13, r0_13.sequenceNames.Score, r0_13.frameDefines.ScoreStart, r7_13, r4_13)
        return 
      end
      while 0 < r6_13 do
        r6_13 = math.floor(r6_13 * 0.1)
        r0_13.CreateImage(r1_13, r0_13.sequenceNames.Score, r0_13.frameDefines.ScoreStart + r6_13 % 10, r7_13, r4_13)
        r7_13 = r7_13 - r5_13
      end
    end
    local function r20_1()
      -- line: [182, 190] id: 14
      if _G.HP < _G.DefaultHp then
        r4_1.isVisible = false
        r3_1.isVisible = true
      else
        r4_1.isVisible = true
        r3_1.isVisible = false
      end
    end
    local function r21_1()
      -- line: [195, 205] id: 15
      local r0_15 = r1_0.getItemData(r1_0.pay_item_data.HpFullRecovery)
      if r0_15 == nil then
        return nil
      end
      return {
        r1_0.pay_item_data.HpFullRecovery,
        r0_15[2] * (_G.DefaultHp - _G.HP)
      }
    end
    local function r22_1(r0_16)
      -- line: [210, 247] id: 16
      local r1_16 = r21_1()
      if r1_16 == nil then
        return 
      end
      local r2_16 = {
        {
          r1_16[1],
          1,
          r1_16[2]
        }
      }
      local r3_16 = require("common.base_dialog")
      local r4_16 = display.newGroup()
      r3_16.FadeInMask(r4_16, {
        0,
        0,
        0,
        0.1
      }, 200)
      require("ui.coin_module").new({
        useItemList = r2_16,
      }).OpenWithTitle(r4_16, 366, nil, function(r0_17)
        -- line: [230, 240] id: 17
        _G.metrics.crystal_hp_recory_in_stage(_G.MapSelect, _G.StageSelect, r2_16)
        r3_16.FadeOutMask(r4_16, 100)
        if game ~= nil then
          game.RecoveryFullHealHp()
          game.ViewPanel()
        end
        r20_1()
      end, function(r0_18)
        -- line: [241, 244] id: 18
        r3_16.FadeOutMask(r4_16, 100)
      end)
      return true
    end
    local function r23_1(r0_19, r1_19, r2_19, r3_19)
      -- line: [252, 263] id: 19
      local r4_19 = display.newGroup()
      r1_19:insert(r4_19)
      require("evo.evo_common").show_num_images({
        rtImg = r0_19.CreateNumbers(r4_19, r0_19.sequenceNames.MpNumberA, 2, r2_19, r3_19, -2),
        num = util.ConvertDisplayCrystal(r21_1()[2]),
      })
    end
    local function r24_1()
      -- line: [269, 490] id: 20
      local function r0_20(r0_21)
        -- line: [271, 273] id: 21
        return "data/game/plate/" .. r0_21 .. ".png"
      end
      local function r1_20(r0_22)
        -- line: [275, 277] id: 22
        return "data/map/interface/" .. r0_22 .. ".png"
      end
      local function r2_20(r0_23)
        -- line: [279, 281] id: 23
        return r1_20(r0_23 .. _G.UILanguage)
      end
      local function r3_20(r0_24)
        -- line: [283, 285] id: 24
        return "data/stage/" .. r0_24 .. ".png"
      end
      local function r4_20(r0_25)
        -- line: [287, 289] id: 25
        return r3_20(r0_25 .. _G.UILanguage)
      end
      local function r5_20(r0_26)
        -- line: [291, 293] id: 26
        return "data/powerup/" .. r0_26 .. _G.UILanguage .. ".png"
      end
      r2_1 = util.MakeGroup(_G.PauseMenuRoot)
      local r6_20 = graphics.newGradient({
        0,
        0,
        0,
        255
      }, {
        0,
        0,
        0,
        0
      }, "down")
      local r7_20 = display.newRect(r2_1, 0, 0, display.contentWidth, 150)
      r7_20:setFillColor(r6_20)
      r7_20.alpha = 0.5
      local r8_20 = display.newRect(r2_1, 0, 200, display.contentWidth, 310)
      r8_20:setFillColor(0, 0, 0)
      r8_20.alpha = 0.5
      util.MakeMat(r2_1)
      util.LoadBtn({
        rtImg = r2_1,
        fname = r0_20("close"),
        x = 858,
        y = 155,
        func = r1_1.Close,
      })
      r5_1 = display.newGroup()
      util.LoadParts(r5_1, r5_20("item03"), 64, 216, true)
      local r9_20 = util.LoadParts(r5_1, r1_20("score_crystal"), 160, 450)
      r5_1:setReferencePoint(display.TopLeftReferencePoint)
      util.LoadBtnGroup({
        group = r5_1,
        cx = 64,
        cy = 216,
        func = r11_1,
      })
      r2_1:insert(r5_1)
      display.newText(r5_1, util.ConvertDisplayCrystal(r1_0.getAllItemData()[13][2]), 190, 445, native.systemFontBold, 28):setFillColor(220, 20, 60)
      r6_1 = display.newGroup()
      util.LoadParts(r6_1, r5_20("item03"), 54, 216)
      r2_1:insert(r6_1)
      local r11_20 = nil
      if _G.UILanguage then
        r11_20 = "使用中"
      else
        r11_20 = "ENABLE"
      end
      display.newText(r6_1, r11_20, 160, 445, native.systemFontBold, 28):setFillColor(220, 20, 60)
      r10_1()
      util.LoadBtn({
        rtImg = r2_1,
        fname = r2_20("stage_button_restart_"),
        x = 288,
        y = 221,
        func = r14_1,
      })
      util.LoadBtn({
        rtImg = r2_1,
        fname = r2_20("btn_world_select2_"),
        x = 288,
        y = 320,
        func = r15_1,
      })
      util.LoadBtn({
        rtImg = r2_1,
        fname = r2_20("btn_powerup2_"),
        x = 451,
        y = 221,
        func = r12_1,
      })
      util.LoadBtn({
        rtImg = r2_1,
        fname = r2_20("btn_rewind2_"),
        x = 451,
        y = 320,
        func = r13_1,
      })
      util.LoadBtn({
        rtImg = r2_1,
        fname = r1_20("stage_button_help"),
        x = 773,
        y = 221,
        func = r16_1,
      })
      util.LoadBtn({
        rtImg = r2_1,
        fname = r1_20("stage_button_option"),
        x = 773,
        y = 320,
        func = r17_1,
      })
      r3_1 = display.newGroup()
      util.LoadParts(r3_1, r2_20("stage_button_healall_"), 613, 221, true)
      util.LoadParts(r3_1, r1_20("score_crystal"), 689, 256)
      r3_1:setReferencePoint(display.TopLeftReferencePoint)
      util.LoadBtnGroup({
        group = r3_1,
        cx = 613,
        cy = 221,
        func = r22_1,
      })
      r2_1:insert(r3_1)
      r3_1:setReferencePoint(display.CenterReferencePoint)
      r3_1.numSprite = r23_1(require("common.sprite_loader").new({
        imageSheet = "common.sprites.sprite_number01",
      }), r3_1, 709, 259)
      r4_1 = util.LoadParts(r2_1, r2_20("stage_button_healall_0_"), 613, 221)
      r20_1()
      util.LoadParts(r2_1, "data/login_bonus/receive/ticket_rewind_s.png", 318, 422)
      local r15_20 = db.GetInventoryItem(_G.UserInquiryID, _G.LoginItems["3"].id) or 0
      local r16_20 = require("common.sprite_loader").new({
        imageSheet = "common.sprites.sprite_number01",
      })
      r16_20.CreateImage(r2_1, r16_20.sequenceNames.Score, r16_20.frameDefines.ScoreCross, 358, 457)
      r19_1(r16_20, r2_1, r15_20, 360, 457)
      util.LoadParts(r2_1, "data/login_bonus/receive/ticket_flare_s.png", 398, 422)
      local r18_20 = db.GetInventoryItem(_G.UserInquiryID, _G.LoginItems["2"].id) or 0
      local r19_20 = require("common.sprite_loader").new({
        imageSheet = "common.sprites.sprite_number01",
      })
      r19_20.CreateImage(r2_1, r19_20.sequenceNames.Score, r19_20.frameDefines.ScoreCross, 438, 457)
      r19_1(r19_20, r2_1, r18_20, 440, 457)
      util.LoadBtn({
        rtImg = r2_1,
        fname = r4_20("button_medal_"),
        x = 498,
        y = 422,
        func = r18_1,
      })
      if r7_1 ~= nil then
        for r23_20, r24_20 in pairs(r7_1) do
          r2_1:insert(r24_20)
        end
      end
      if r8_1 ~= nil then
        for r23_20, r24_20 in pairs(r8_1) do
          r2_1:insert(r24_20)
        end
      end
    end
    local function r25_1()
      -- line: [495, 513] id: 27
      if r9_1 ~= nil then
        if r7_1 ~= nil then
          for r3_27, r4_27 in pairs(r7_1) do
            r9_1:insert(r4_27)
          end
        end
        if r8_1 ~= nil then
          for r3_27, r4_27 in pairs(r8_1) do
            r9_1:insert(r4_27)
          end
        end
      end
      display.remove(r2_1)
      char.SetSystemPause(false)
      game.Play(nil)
    end
    function r1_1.Open()
      -- line: [542, 544] id: 29
      r24_1()
    end
    function r1_1.Close()
      -- line: [549, 551] id: 30
      r25_1()
    end
    (function()
      -- line: [518, 532] id: 28
      if r0_1 ~= nil then
        if r0_1.playButton ~= nil then
          r7_1 = r0_1.playButton
        end
        if r0_1.speedButton ~= nil then
          r8_1 = r0_1.speedButton
        end
        if r0_1.backToParentGroup ~= nil then
          r9_1 = r0_1.backToParentGroup
        end
      end
    end)()
    return r1_1
  end,
}
