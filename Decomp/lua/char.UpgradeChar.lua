-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("logic.pay_item_data")
local r1_0 = require("logic.char.CharStatus")
local r2_0 = require("char.BaseChar")
local r3_0 = require("logic.game.GameStatus")
local function r4_0(r0_1, r1_1)
  -- line: [6, 20] id: 1
  local r2_1 = display.newGroup()
  if r1_1 == "a" then
    r1_0.SpriteNumber01.CreateNumbers(r2_1, r1_0.SpriteNumber01.sequenceNames.MpNumberA, 4, 0, 0, -2)
  else
    r1_0.SpriteNumber01.CreateNumbers(r2_1, r1_0.SpriteNumber01.sequenceNames.MpNumberB, 4, 0, 0, -2)
  end
  r2_1:setReferencePoint(display.TopLeftReferencePoint)
  r2_1.x = 80
  r2_1.y = 44
  r2_1.isVisible = false
  r0_1:insert(r2_1)
  return r2_1
end
local function r6_0(r0_3, r1_3)
  -- line: [29, 95] id: 3
  _G.BingoManager.updateUserBingoData(_G.BingoManagerClass.MISSION_TYPE.UPGRADE_CHAR(), nil)
  if r1_3 == nil then
    r1_3 = false
  end
  if r1_0.TutorialManager.IsUpgradeTutorial and r1_0.TutorialManager.FirstUpgradeFlag == false then
    r1_0.TutorialManager.FirstUpgradeFlag = true
  end
  sound.PlaySE(6, 21, true)
  r2_0.not_show_upgrade_btn()
  local r2_3 = r0_3.struct
  if _G.MapSelect == 1 then
    statslog.LogSend("upgrade", {
      item = r2_3.type,
      level = r2_3.level,
    })
  end
  r2_3.func.sound(r2_3, 3)
  if r2_0.get_current_char(r2_3).custom_upgrade_ok(r2_3, r1_3) then
    return 
  end
  if r1_3 == false then
    game.AddMp(-r2_3.upgrade_mp)
    game.ViewPanel()
  end
  r2_3.level = r2_3.level + 1
  r2_0.set_star(r2_3)
  local r4_3 = _G.UserStatus[r2_3.type]
  local r5_3 = r4_3.range[r2_3.level][1]
  local r6_3 = r4_3.range[r2_3.level][2] * (r1_0.RangePower + 100) / 100
  r2_3.range = {
    r5_3 * r5_3,
    r6_3 * r6_3
  }
  r2_3.range_circle = r6_3 * 2
  save.DataPush("upgrade", {
    xy = r2_3.map_xy,
    upgrade_mp = r2_3.upgrade_mp,
  })
  if r2_3.level == 4 then
    game.UpdateSpecialAchievement(r2_3.type, r2_3.level)
  end
  r2_0.get_current_char(r2_3).check_upgrade_ok_achievement(r2_3)
  if _G.GameMode == _G.GameModeEvo then
    r1_0.EvoChar.UpdateEvoAbility(r2_3)
  end
end
local function r8_0(r0_5)
  -- line: [103, 140] id: 5
  sound.PlaySE(7, 21, true)
  r2_0.release_release_confirm()
  r2_0.not_show_upgrade_btn()
  local r1_5 = r0_5.struct
  if _G.GameMode == _G.GameModeEvo then
    r1_0.EvoChar.ReleaseEvo(r1_5)
  end
  r1_5.func.sound(r1_5, 4)
  if r2_0.get_current_char(r1_5).custom_release_ok(r1_5) then
    return 
  end
  game.AddMp(r1_5.release_mp)
  game.ViewPanel()
  save.DataPush("release", {
    xy = r1_5.map_xy,
    release_mp = r1_5.release_mp,
  })
  r2_0.kill_char(r1_5)
  if game ~= nil and game.IsPauseTypeNone() then
    game.MakeGrid(false)
  else
    game.MakeGrid(true)
  end
  r2_0.get_current_char(r1_5).check_release_ok_achievement(r1_5)
  game.ReleaseOK()
end
local function r9_0(r0_6)
  -- line: [146, 187] id: 6
  r2_0.close_plate(false)
  game.PossessingCrystalVisible(false)
  local r1_6 = r0_0.GetCharCrystalLevelup(r0_6.struct.type, r0_6.struct.level + 1)
  if r1_6 == nil then
    return 
  end
  require("ui.coin_module").new({
    useItemList = {
      {
        r1_6,
        1,
        r0_6.struct.upgrade_mp
      }
    },
  }).Open(_G.DialogRoot, nil, function(r0_7)
    -- line: [168, 178] id: 7
    _G.metrics.crystal_character_upgrade_in_stage(_G.MapSelect, _G.StageSelect, r0_7)
    game.UpdatePocketCrystal()
    r6_0(r0_6, true)
    r2_0.hide_upgrade_circle(true, true)
    game.SetScoreType(1)
    r2_0.unlock_use_crystal_mode()
  end, function(r0_8)
    -- line: [179, 184] id: 8
    r2_0.hide_upgrade_circle(true, true)
    r2_0.unlock_use_crystal_mode()
  end)
  return true
end
local function r10_0(r0_9)
  -- line: [190, 232] id: 9
  if not _G.GameData.confirm then
    return r8_0(r0_9)
  end
  local function r1_9(r0_10)
    -- line: [195, 195] id: 10
    return "data/game/confirm/" .. r0_10 .. ".png"
  end
  local function r2_9(r0_11)
    -- line: [197, 197] id: 11
    return r1_9(r0_11 .. _G.UILanguage)
  end
  sound.PlaySE(2)
  r2_0.not_show_upgrade_btn()
  local r3_9 = r0_9.struct
  local r4_9 = 230
  local r5_9 = 280
  if r3_9.type == r1_0.CharDef.CharId.Kala then
    r4_9 = 330
  end
  local r6_9 = util.LoadBtn({
    rtImg = _G.UpgradeRoot,
    fname = r1_9("button_ok"),
    x = r4_9,
    y = r5_9,
    func = r8_0,
  })
  r6_9.struct = r3_9
  local r7_9 = util.LoadBtn({
    rtImg = _G.UpgradeRoot,
    fname = r2_9("button_cancel_"),
    x = r4_9 + 300,
    y = r5_9,
    func = function(r0_12)
      -- line: [222, 225] id: 12
      sound.PlaySE(2)
      r2_0.release_release_confirm()
    end,
  })
  r1_0.ReleaseConfirm = {}
  table.insert(r1_0.ReleaseConfirm, r6_9)
  table.insert(r1_0.ReleaseConfirm, r7_9)
  return true
end
local function r11_0(r0_13)
  -- line: [236, 338] id: 13
  local r1_13 = r0_13.struct
  local r3_13 = r1_13.level + 1
  local r4_13 = _G.UserStatus[r1_13.type].cost[r3_13]
  if r1_0.UpgradeBtn[13].isVisible then
    r1_0.userCoin = game.GetPocketCrystal()
    game.UpdatePocketCrystal(function(r0_14)
      -- line: [245, 249] id: 14
      if r0_14 ~= nil then
        r1_0.userCoin = r0_14
      end
    end, false)
    r2_0.lock_use_crystal_mode()
    r1_0.UpgradeBtn[13].isVisible = false
    r1_0.UpgradeBtn[14].isVisible = true
    r1_0.UpgradeBtn[15].isVisible = false
    r1_0.UpgradeBtn[1].isVisible = false
    r1_0.UpgradeBtn[4].isVisible = false
    r1_0.UpgradeBtn[7].isVisible = false
    r1_0.TutorialManager.UpgradeBtnTutorial(false)
    if r3_13 > 4 then
      r1_0.UpgradeBtn[11].isVisible = false
      r1_0.UpgradeBtn[12].isVisible = false
      r1_0.UpgradeBtn[5].isVisible = true
    elseif r1_0.userCoin == nil or r1_0.userCoin < r4_13 then
      r2_0.set_upgrade_btn_num(r1_0.UpgradeBtnNum[4], util.ConvertDisplayCrystal(r4_13))
      r1_0.UpgradeBtn[11].isVisible = false
      r1_0.UpgradeBtn[12].isVisible = true
      r1_0.UpgradeBtn[12].touch = nil
    else
      local r5_13 = nil
      r2_0.set_upgrade_btn_num(r1_0.UpgradeBtnNum[3], r4_13)
      r5_13 = r1_0.UpgradeBtn[11]
      r1_13.upgrade_mp = r4_13
      r5_13.rslt = rslt
      r5_13.isVisible = false
      r5_13.struct = r1_13
      r1_0.UpgradeBtn[12].isVisible = false
      r2_0.set_upgrade_btn_num(r1_0.UpgradeBtnNum[3], util.ConvertDisplayCrystal(r4_13))
      r1_0.UpgradeBtn[11].isVisible = true
    end
  else
    r2_0.unlock_use_crystal_mode()
    r1_0.UpgradeBtn[13].isVisible = true
    r1_0.UpgradeBtn[14].isVisible = false
    if r1_13.type == r1_0.CharDef.CharId.Amber and game ~= nil and game.GetPauseType() ~= r3_0.PauseTypeFirstPause then
      r1_0.UpgradeBtn[15].isVisible = true
    end
    r1_0.UpgradeBtn[11].isVisible = false
    r1_0.UpgradeBtn[12].isVisible = false
    if r3_13 > 4 then
      r1_0.UpgradeBtn[5].isVisible = true
    elseif r3_13 == 4 and r1_0.Level4Lock[r1_13.type] then
      r1_0.UpgradeBtn[7].isVisible = true
    elseif _G.MP < r4_13 then
      r2_0.set_upgrade_btn_num(r1_0.UpgradeBtnNum[2], r4_13)
      r1_0.UpgradeBtn[4].isVisible = true
      r1_0.UpgradeMonitoring = {
        struct = r1_13,
        mp = r4_13,
      }
    else
      local r5_13 = nil
      r2_0.set_upgrade_btn_num(r1_0.UpgradeBtnNum[1], r4_13)
      r5_13 = r1_0.UpgradeBtn[1]
      r1_13.upgrade_mp = r4_13
      r5_13.rslt = rslt
      r5_13.isVisible = true
      r5_13.struct = r1_13
      r1_0.UpgradeBtn[4].isVisible = false
      r1_0.TutorialManager.UpgradeBtnTutorial(true, _G.UpgradeRoot, r5_13.x, r5_13.y)
      r1_0.TutorialManager.UpgradeCharTutorial(false)
      r2_0.set_upgrade_btn_num(r1_0.UpgradeBtnNum[1], r4_13)
      r1_0.UpgradeBtn[1].isVisible = true
    end
  end
  return true
end
return {
  make_upgrade_btn = function()
    -- line: [343, 504] id: 16
    local r0_16 = require("common.sprite_loader").new({
      imageSheet = "common.sprites.sprite_parts03",
    })
    local function r1_16(r0_17)
      -- line: [346, 346] id: 17
      return "data/game/upgrade/" .. r0_17 .. ".png"
    end
    local function r2_16(r0_18)
      -- line: [348, 348] id: 18
      return r1_16(r0_18 .. _G.UILanguage)
    end
    r1_0.UpgradeBtn = {}
    r1_0.UpgradeBtnNum = {}
    r1_0.ReleaseBtnNum = {}
    local r3_16 = nil
    local r4_16 = nil
    local r5_16 = nil
    local r6_16 = nil
    local r7_16 = nil
    local r8_16 = 45
    local r9_16 = 42
    r3_16 = display.newRect(_G.UpgradeRoot, 0, 0, _G.Width, _G.Height)
    r3_16:setFillColor(0, 0, 0)
    r3_16.alpha = 0.01
    r3_16.isVisible = false
    function r3_16.touch(r0_19, r1_19)
      -- line: [365, 381] id: 19
      if 0.8 <= (r1_19.time - r1_0.touch_time) / 1000 and r0_19.struct then
        if r0_19.struct.touch_sound then
          r0_19.struct.func.sound(r0_19.struct, r0_19.struct.touch_sound)
        else
          r0_19.struct.func.sound(r0_19.struct, 2)
        end
        r0_19.struct = nil
      end
      if r1_0.SummonPlateGroup then
        return false
      end
      r2_0.not_show_upgrade_btn()
      r2_0.release_release_confirm()
      return true
    end
    r3_16:addEventListener("touch", r3_16)
    r1_0.UpgradeBtn[3] = r3_16
    r6_16 = 110
    r7_16 = 277
    r3_16 = display.newGroup()
    display.newImage(r3_16, r2_16("upgrade_"), 0, 0, true)
    r3_16:setReferencePoint(display.TopLeftReferencePoint)
    r1_0.UpgradeBtnNum[1] = r4_0(r3_16, "a")
    r1_0.UpgradeBtn[1] = util.LoadBtnGroup({
      group = r3_16,
      cx = r6_16,
      cy = r7_16,
      func = r6_0,
      show = false,
    })
    util.LoadParts(r3_16, r1_16("icon_mp"), r8_16, r9_16)
    _G.UpgradeRoot:insert(r3_16)
    r3_16:setReferencePoint(display.CenterReferencePoint)
    r3_16 = display.newGroup()
    r4_16 = display.newImage(r3_16, r2_16("upgrade_"), 0, 0, true)
    r5_16 = util.LoadParts(r3_16, r1_16("icon_mp"), r8_16, r9_16)
    r4_16:setFillColor(164, 128, 64)
    r5_16:setFillColor(164, 128, 64)
    r3_16:setReferencePoint(display.TopLeftReferencePoint)
    r1_0.UpgradeBtnNum[2] = r4_0(r3_16, "b")
    r3_16.x = r6_16
    r3_16.y = r7_16
    r3_16.isVisible = false
    function r3_16.touch(r0_20, r1_20)
      -- line: [410, 410] id: 20
      return true
    end
    r3_16:addEventListener("touch", r3_16)
    r1_0.UpgradeBtn[4] = r3_16
    _G.UpgradeRoot:insert(r3_16)
    r3_16 = display.newGroup()
    display.newImage(r3_16, r2_16("upgrade_max_"), 0, 0, true)
    r3_16:setReferencePoint(display.TopLeftReferencePoint)
    r3_16.x = r6_16
    r3_16.y = r7_16
    r3_16.isVisible = false
    function r3_16.touch(r0_21, r1_21)
      -- line: [422, 422] id: 21
      return true
    end
    r3_16:addEventListener("touch", r3_16)
    r1_0.UpgradeBtn[5] = r3_16
    _G.UpgradeRoot:insert(r3_16)
    r3_16 = display.newGroup()
    display.newImage(r3_16, r1_16("upgrade_locked"), 0, 0, true)
    r3_16:setReferencePoint(display.TopLeftReferencePoint)
    r3_16.x = r6_16
    r3_16.y = r7_16
    r3_16.isVisible = false
    function r3_16.touch(r0_22, r1_22)
      -- line: [434, 434] id: 22
      return true
    end
    r3_16:addEventListener("touch", r3_16)
    r1_0.UpgradeBtn[7] = r3_16
    _G.UpgradeRoot:insert(r3_16)
    r3_16 = display.newGroup()
    display.newImage(r3_16, r2_16("upgrade_"), 0, 0, true)
    r3_16:setReferencePoint(display.TopLeftReferencePoint)
    r1_0.UpgradeBtnNum[3] = r4_0(r3_16, "a")
    r1_0.UpgradeBtn[11] = util.LoadBtnGroup({
      group = r3_16,
      cx = r1_0.UpgradeBtn[4].x,
      cy = r7_16,
      func = r9_0,
      show = false,
    })
    util.LoadParts(r3_16, r1_16("icon_crystal"), r8_16, r9_16)
    _G.UpgradeRoot:insert(r3_16)
    r3_16:setReferencePoint(display.CenterReferencePoint)
    r3_16 = display.newGroup()
    r4_16 = display.newImage(r3_16, r2_16("upgrade_"), 0, 0, true)
    r5_16 = util.LoadParts(r3_16, r1_16("icon_crystal"), r8_16, r9_16)
    r4_16:setFillColor(128, 128, 128)
    r5_16:setFillColor(128, 128, 128)
    r3_16:setReferencePoint(display.TopLeftReferencePoint)
    r3_16.x = r6_16
    r3_16.y = r7_16
    r3_16.isVisible = false
    function r3_16.touch(r0_23, r1_23)
      -- line: [464, 464] id: 23
      return true
    end
    r3_16:addEventListener("touch", r3_16)
    r1_0.UpgradeBtn[12] = r3_16
    r1_0.UpgradeBtnNum[4] = r4_0(r3_16, "b")
    _G.UpgradeRoot:insert(r3_16)
    r6_16 = r1_0.UpgradeBtn[12].x + r1_0.UpgradeBtn[12].width + 74
    r3_16 = display.newGroup()
    display.newImage(r3_16, r2_16("release_"), 0, 0, true)
    util.LoadParts(r3_16, r1_16("icon_mp"), r8_16, r9_16)
    r3_16:setReferencePoint(display.TopLeftReferencePoint)
    r1_0.ReleaseBtnNum = r4_0(r3_16, "a")
    r1_0.UpgradeBtn[2] = util.LoadBtnGroup({
      group = r3_16,
      cx = r6_16,
      cy = r7_16,
      func = r10_0,
      show = false,
    })
    _G.UpgradeRoot:insert(r3_16)
    r3_16:setReferencePoint(display.CenterReferencePoint)
    r6_16 = r1_0.UpgradeBtn[2].x + r1_0.UpgradeBtn[2].width * 0.5 + 74
    r3_16 = display.newGroup()
    display.newImage(r3_16, r2_16("upgrade_tgl_mp_"), 0, 0, true)
    r3_16:setReferencePoint(display.TopLeftReferencePoint)
    r1_0.UpgradeBtn[13] = util.LoadBtnGroup({
      group = r3_16,
      cx = r6_16,
      cy = r7_16,
      func = r11_0,
      show = false,
    })
    _G.UpgradeRoot:insert(r3_16)
    r3_16:setReferencePoint(display.CenterReferencePoint)
    r3_16 = display.newGroup()
    display.newImage(r3_16, r2_16("upgrade_tgl_crystal_"), 0, 0, true)
    r3_16:setReferencePoint(display.TopLeftReferencePoint)
    r1_0.UpgradeBtn[14] = util.LoadBtnGroup({
      group = r3_16,
      cx = r6_16,
      cy = r7_16,
      func = r11_0,
      show = false,
    })
    _G.UpgradeRoot:insert(r3_16)
    r3_16:setReferencePoint(display.CenterReferencePoint)
  end,
  upgrade_ok = r6_0,
  release_ok = r8_0,
  custom_make_upgrade_btn = function()
    -- line: [340, 341] id: 15
  end,
  custom_upgrade_ok = function(r0_2, r1_2)
    -- line: [24, 26] id: 2
    return false
  end,
  custom_release_ok = function(r0_4)
    -- line: [98, 100] id: 4
    return false
  end,
}
