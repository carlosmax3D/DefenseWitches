-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("logic.char.CharStatus")
local r1_0 = require("logic.game.GameStatus")
local r2_0 = require("common.base_dialog")
local r3_0 = require("evo.evo_common")
local r4_0 = require("logic.pay_item_data")
return {
  new = function(r0_1)
    -- line: [15, 559] id: 1
    local r1_1 = {
      parentObj = nil,
      baseX = 0,
      baseY = 0,
    }
    local r2_1 = 575
    local r3_1 = false
    local r4_1 = false
    local r5_1 = nil
    local r6_1 = nil
    local r7_1 = nil
    local function r8_1(r0_2)
      -- line: [40, 42] id: 2
      return "data/map/interface/" .. r0_2 .. ".png"
    end
    local function r9_1(r0_3)
      -- line: [43, 45] id: 3
      return r8_1(r0_3 .. _G.UILanguage)
    end
    local function r10_1(r0_4, r1_4, r2_4, r3_4)
      -- line: [50, 54] id: 4
      local r4_4 = display.newGroup()
      r1_4:insert(r4_4)
      return r0_4.CreateNumbers(r4_4, r0_4.sequenceNames.MpNumberA, 2, r2_4, r3_4, -2)
    end
    local function r11_1(r0_5, r1_5)
      -- line: [59, 64] id: 5
      r3_0.show_num_images({
        rtImg = r0_5,
        num = util.ConvertDisplayCrystal(r1_5),
      })
    end
    local function r12_1()
      -- line: [69, 76] id: 6
      local r0_6 = r4_0.getItemData(r4_0.pay_item_data.HpFullRecovery)
      if r0_6 == nil then
        return nil
      end
      return {
        r4_0.pay_item_data.HpFullRecovery,
        r0_6[2] * (_G.DefaultHp - _G.HP)
      }
    end
    local function r13_1(r0_7)
      -- line: [81, 116] id: 7
      local r1_7 = r12_1()
      if r1_7 == nil then
        return 
      end
      local r2_7 = {
        {
          r1_7[1],
          1,
          r1_7[2]
        }
      }
      local r3_7 = display.newGroup()
      r2_0.FadeInMask(r3_7, {
        0,
        0,
        0,
        0.1
      }, 200)
      require("ui.coin_module").new({
        useItemList = r2_7,
      }).OpenWithTitle(r3_7, 366, nil, function(r0_8)
        -- line: [97, 108] id: 8
        _G.metrics.crystal_hp_recory_in_stage(_G.MapSelect, _G.StageSelect, r2_7)
        r2_0.FadeOutMask(r3_7, 100)
        if game ~= nil then
          game.RecoveryFullHealHp()
          game.ViewPanel()
        end
        r1_1.Close()
      end, function(r0_9)
        -- line: [109, 112] id: 9
        r2_0.FadeOutMask(r3_7, 100)
      end)
      return true
    end
    local function r14_1(r0_10)
      -- line: [121, 124] id: 10
      game.OpenPowerup()
      return true
    end
    local function r15_1(r0_11)
      -- line: [129, 136] id: 11
      r1_1.Close(false)
      local r1_11 = require("stage_ui.stage_help")
      r1_11.Init()
      r1_11.Open()
      return true
    end
    local function r16_1(r0_12)
      -- line: [141, 144] id: 12
      game.OpenOption()
      return true
    end
    local function r17_1()
      -- line: [149, 153] id: 13
      r1_1.Close()
      game.OpenBuyCrystalDialog()
      return true
    end
    local function r18_1()
      -- line: [158, 162] id: 14
      r1_1.Close()
      game.OpenRestartDialog()
      return true
    end
    local function r19_1()
      -- line: [167, 171] id: 15
      r1_1.Close()
      game.OpenQuitDialog()
      return true
    end
    local function r20_1()
      -- line: [176, 313] id: 16
      local r0_16 = require("common.sprite_loader").new({
        imageSheet = "common.sprites.sprite_parts02",
      })
      local function r1_16(r0_17, r1_17, r2_17)
        -- line: [180, 227] id: 17
        local r3_17 = easing.outExpo(r1_17.ms, r1_17.time, 0, r1_17.targetHeight)
        if r1_17.targetHeight <= r3_17 then
          r3_17 = r1_17.targetHeight
        end
        r1_17.middleFrame.yScale = r3_17 * 0.5
        r1_17.menuButton:setReferencePoint(display.TopLeftReferencePoint)
        r1_17.menuButton.y = r3_17
        r1_17.ms = r1_17.ms + r2_17
        if r1_17.time <= r1_17.ms then
          events.Delete(r1_17.ev)
          r6_1[1]:setReferencePoint(display.TopLeftReferencePoint)
          r6_1[2]:setReferencePoint(display.TopLeftReferencePoint)
          r6_1[1].y = r1_17.menuButton.y
          r6_1[2].y = r1_17.menuButton.y
          r6_1[1]:setReferencePoint(display.CenterReferencePoint)
          r6_1[2]:setReferencePoint(display.CenterReferencePoint)
          r3_1 = true
          r6_1[1].isVisible = not r3_1
          r6_1[2].isVisible = r3_1
          r6_1[1].disable = r3_1
          r6_1[2].disable = not r3_1
          if _G.HP < _G.DefaultHp then
            r11_1(r1_17.buttonGroup[1].numSprite, r12_1()[2])
            r1_17.buttonGroup[1].isVisible = true
            r1_17.buttonGroup[2].isVisible = false
          else
            r1_17.buttonGroup[1].isVisible = false
            r1_17.buttonGroup[2].isVisible = true
          end
          local r4_17 = nil
          for r8_17 = 3, r5_1.buttonGroup.numChildren, 1 do
            r5_1.buttonGroup[r8_17].isVisible = true
          end
        end
      end
      r6_1[1].disable = true
      local r2_16 = display.newGroup()
      r2_16:setReferencePoint(display.TopLeftReferencePoint)
      r2_16.y = 12
      local r3_16 = display.newRect(r2_16, 5, 0, 149, 2)
      r3_16:setFillColor(0, 0, 0)
      r3_16.alpha = 0.7
      r0_16.CreateImage(r2_16, r0_16.sequenceNames.MenuBase, r0_16.frameDefines.MenuBaseMiddleLeft, 0, 0)
      r0_16.CreateImage(r2_16, r0_16.sequenceNames.MenuBase, r0_16.frameDefines.MenuBaseMiddleRight, 80, 0)
      local r4_16 = display.newGroup()
      local r5_16 = display.newRect(r4_16, 5, 8, 149, 4)
      r5_16:setFillColor(0, 0, 0)
      r5_16.alpha = 0.7
      local r6_16 = r0_16.CreateImage(r4_16, r0_16.sequenceNames.MenuBase, r0_16.frameDefines.MenuBaseHeaderLeft, 0, 0)
      local r7_16 = r0_16.CreateImage(r4_16, r0_16.sequenceNames.MenuBase, r0_16.frameDefines.MenuBaseHeaderRight, 80, 0)
      r5_1:insert(1, r4_16)
      r5_1:insert(1, r2_16)
      local r8_16 = display.newRect(0, 0, _G.Width, _G.Height)
      r8_16:setFillColor(0, 0, 0)
      r8_16.alpha = 0
      r1_1.parentObj:insert(1, r8_16)
      transition.to(r8_16, {
        alpha = 0.3,
        time = 300,
      })
      r5_1.mask = r8_16
      local r9_16 = util.MakeMat(r1_1.parentObj)
      r1_1.parentObj:insert(1, r9_16)
      r5_1.mat = r9_16
      r5_1.headerFrame = r4_16
      r5_1.middleFrame = r2_16
      r4_1 = game.GetPauseType()
      char.ClearTwinsGuide()
      char.ClearAllCircle()
      if char.GetUseOrbMode() ~= r0_0.UseOrbModeReset then
        char.SetUseOrbMode(r0_0.UseOrbModeReset, r0_0.UseOrbModePlayStatusPlay)
      end
      char.SetSystemPause(true)
      if game.GetPauseType() == r1_0.PauseTypeNone then
        game.Pause(r1_0.PauseTypePulldownMenu, {
          [r1_0.PauseFuncMoveEnemy] = false,
          [r1_0.PauseFuncMoveChars] = false,
          [r1_0.PauseFuncEnablePlayPauseButton] = true,
          [r1_0.PauseFuncShowGuideBlink] = true,
          [r1_0.PauseFuncShowTicket] = true,
          [r1_0.PauseFuncEnableUseOrbButton] = false,
          [r1_0.PauseFuncClosePulldownMenu] = false,
        })
      end
      local r10_16 = {
        menuButton = r6_1[1],
        middleFrame = r2_16,
        buttonGroup = r5_1.buttonGroup,
        targetHeight = r2_1,
        ms = 0,
        time = 500,
        ev = nil,
      }
      r10_16.ev = events.Register(r1_16, r10_16, 1)
      game.PossessingCrystalVisible(false)
      return true
    end
    local function r21_1(r0_18)
      -- line: [318, 401] id: 18
      local function r1_18(r0_19, r1_19, r2_19)
        -- line: [320, 376] id: 19
        local r3_19 = r1_19.targetHeight - easing.outExpo(r1_19.ms, r1_19.time, 0, r1_19.targetHeight)
        if r3_19 < 1 then
          r3_19 = 0
        end
        r1_19.middleFrame.yScale = r3_19 * 0.5
        r1_19.menuButton:setReferencePoint(display.TopLeftReferencePoint)
        r1_19.menuButton.y = r3_19
        r1_19.ms = r1_19.ms + r2_19
        if r1_19.time <= r1_19.ms then
          events.Delete(r1_19.ev)
          r6_1[1].y = r6_1[1].height * 0.5
          r6_1[2].y = r6_1[2].height * 0.5
          r3_1 = false
          r6_1[1].isVisible = not r3_1
          r6_1[2].isVisible = r3_1
          r6_1[1].disable = r3_1
          r6_1[2].disable = not r3_1
          if r5_1 ~= nil then
            if r5_1.headerFrame ~= nil then
              display.remove(r5_1.headerFrame)
              r5_1.headerFrame = nil
            end
            if r5_1.middleFrame ~= nil then
              display.remove(r5_1.middleFrame)
              r5_1.middleFrame = nil
            end
            if r5_1.mask ~= nil then
              display.remove(r5_1.mask)
              r5_1.mask = nil
            end
            if r5_1.mat ~= nil then
              display.remove(r5_1.mat)
              r5_1.mat = nil
            end
          end
          if r4_1 == r1_0.PauseTypeNone and r0_18 ~= nil then
            char.SetSystemPause(false)
            game.Play(nil)
          end
          return false
        end
        return true
      end
      local r2_18 = nil
      for r6_18 = 1, r5_1.buttonGroup.numChildren, 1 do
        r5_1.buttonGroup[r6_18].isVisible = false
      end
      r6_1[2].disable = true
      local r3_18 = nil
      local r4_18 = {
        middleFrame = r5_1.middleFrame,
        menuButton = r6_1[2],
        targetHeight = r2_1,
        ms = 0,
        time = 500,
        ev = nil,
      }
      r4_18.ev = events.Register(r1_18, r4_18, 1)
      return true
    end
    local function r22_1(r0_20)
      -- line: [406, 486] id: 20
      local r1_20 = require("common.sprite_loader").new({
        imageSheet = "common.sprites.sprite_number01",
      })
      r6_1 = {
        util.LoadBtn(r4_20),
        {
          rtImg = r0_20,
          fname = r9_1("stage_button_menu_open_"),
          x = 0,
          y = 0,
          func = r20_1,
          show = not r3_1,
        },
        {
          rtImg = r0_20,
          fname = r9_1("stage_button_menu_close_"),
          x = 0,
          y = 0,
          func = r21_1,
          show = r3_1,
        },
        util.LoadBtn(r5_20)
      }
      local r2_20 = display.newGroup()
      local r3_20 = display.newGroup()
      display.newImage(r3_20, r9_1("stage_button_healall_"), 0, 0, true)
      util.LoadParts(r3_20, r8_1("score_crystal"), 75, 35)
      r3_20:setReferencePoint(display.TopLeftReferencePoint)
      util.LoadBtnGroup({
        group = r3_20,
        cx = 5,
        cy = 15,
        func = r13_1,
        show = false,
      })
      r2_20:insert(r3_20)
      r3_20:setReferencePoint(display.CenterReferencePoint)
      r3_20.numSprite = r10_1(r1_20, r3_20, 95, 38)
      util.LoadParts(r2_20, r9_1("stage_button_healall_0_"), 5, 15).isVisible = r3_1
      util.LoadBtn({
        rtImg = r2_20,
        fname = r9_1("stage_button_powerup_"),
        x = 5,
        y = 96,
        func = r14_1,
        show = r3_1,
      })
      util.LoadBtn({
        rtImg = r2_20,
        fname = r9_1("stage_button_help_"),
        x = 5,
        y = 177,
        func = r15_1,
        show = r3_1,
      })
      util.LoadBtn({
        rtImg = r2_20,
        fname = r9_1("stage_button_addcrystal_"),
        x = 5,
        y = 258,
        func = r17_1,
        show = r3_1,
      })
      util.LoadBtn({
        rtImg = r2_20,
        fname = r9_1("stage_button_quit_"),
        x = 5,
        y = 339,
        func = r19_1,
        show = r3_1,
      })
      util.LoadBtn({
        rtImg = r2_20,
        fname = r9_1("stage_button_restart_"),
        x = 5,
        y = 420,
        func = r18_1,
        show = r3_1,
      })
      util.LoadBtn({
        rtImg = r2_20,
        fname = r9_1("stage_button_option_"),
        x = 5,
        y = 501,
        func = r16_1,
        show = r3_1,
      })
      r0_20:insert(r2_20)
      r2_20:setReferencePoint(display.TopLeftReferncePoint)
      r2_20.x = 5
      r2_20.y = 0
      r5_1.buttonGroup = r2_20
    end
    function r1_1.Close()
      -- line: [526, 528] id: 22
      r21_1(nil)
    end
    function r1_1.IsOpen()
      -- line: [533, 535] id: 23
      return r3_1
    end
    function r1_1.SetEnableOpenButton(r0_24)
      -- line: [540, 551] id: 24
      for r5_24 = 1, #r6_1, 1 do
        if r0_24 == true then
          r6_1[r5_24].disable = false
        else
          r6_1[r5_24].disable = true
        end
      end
    end
    (function()
      -- line: [491, 518] id: 21
      r1_1.parentObj = nil
      if r0_1.parentObj ~= nil then
        r1_1.parentObj = r0_1.parentObj
      else
        r1_1.parentObj = display.newGroup()
      end
      r1_1.baseX = 0
      if r0_1.baseX ~= nil then
        r1_1.baseX = r0_1.baseX
      end
      r1_1.baseY = 0
      if r0_1.baseY ~= nil then
        r1_1.baseY = r0_1.baseY
      end
      r3_1 = false
      if r0_1.isOpen ~= nil then
        r3_1 = r0_1.isOpen
      end
      r5_1 = display.newGroup()
      r22_1(r5_1)
      r1_1.parentObj:insert(r5_1)
      r5_1:setReferencePoint(display.TopLeftReferencePoint)
      r5_1.x = r1_1.baseX
      r5_1.y = r1_1.baseY
    end)()
    return r1_1
  end,
}
