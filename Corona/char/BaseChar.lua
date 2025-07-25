-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("logic.char.CharStatus")
local r1_0 = require("logic.game.GameStatus")
local r2_0 = {
  {
    50,
    80
  },
  {
    50,
    400
  }
}
local r3_0 = {
  {
    {
      38,
      80
    },
    {
      126,
      80
    },
    {
      214,
      80
    },
    {
      302,
      80
    },
    {
      390,
      80
    },
    {
      478,
      80
    },
    {
      566,
      80
    },
    {
      654,
      80
    },
    {
      742,
      80
    },
    {
      830,
      80
    },
    {
      38,
      184
    },
    {
      126,
      184
    },
    {
      214,
      184
    },
    {
      302,
      184
    },
    {
      390,
      184
    },
    {
      478,
      184
    },
    {
      566,
      184
    },
    {
      654,
      184
    },
    {
      742,
      184
    },
    {
      830,
      184
    },
    {
      38,
      288
    },
    {
      126,
      288
    }
  },
  {
    {
      38,
      520
    },
    {
      126,
      520
    },
    {
      214,
      520
    },
    {
      302,
      520
    },
    {
      390,
      520
    },
    {
      478,
      520
    },
    {
      566,
      520
    },
    {
      654,
      520
    },
    {
      742,
      520
    },
    {
      830,
      520
    },
    {
      38,
      416
    },
    {
      126,
      416
    },
    {
      214,
      416
    },
    {
      302,
      416
    },
    {
      390,
      416
    },
    {
      478,
      416
    },
    {
      566,
      416
    },
    {
      654,
      416
    },
    {
      742,
      416
    },
    {
      830,
      416
    },
    {
      38,
      312
    },
    {
      126,
      312
    }
  }
}
local function r4_0(r0_1)
  -- line: [29, 31] id: 1
  return math.floor((r0_1 - _G.BgRoot.x) / 80) + 1
end
local function r5_0(r0_2)
  -- line: [34, 36] id: 2
  return math.floor((r0_2 - _G.BgRoot.y) / 80) + 1
end
local function r6_0(r0_3)
  -- line: [38, 40] id: 3
  return r0_0.SummonChar[r0_3.type]
end
local function r7_0(r0_4, r1_4)
  -- line: [42, 54] id: 4
  local r3_4 = r1_4
  for r7_4 = 1, r0_4.numChildren, 1 do
    r3_4 = math.floor(r3_4 * 0.1)
    r0_4[r7_4]:setFrame(r3_4 % 10 + 1)
  end
  r0_4.isVisible = true
end
local function r8_0()
  -- line: [56, 63] id: 5
  for r3_5, r4_5 in pairs(r0_0.UpgradeBtn) do
    if r3_5 ~= 6 and r3_5 ~= 8 and r3_5 ~= 10 and r3_5 ~= 15 and r4_5 then
      r4_5.isVisible = false
    end
  end
end
local function r9_0()
  -- line: [65, 82] id: 6
  if r0_0.UpgradeBtn then
    for r3_6, r4_6 in pairs(r0_0.UpgradeBtn) do
      if r4_6 then
        r4_6.isVisible = false
      end
    end
  end
  r0_0.UpgradeMonitoring = nil
  if r0_0.Cooldown then
    local r0_6 = r0_0.Cooldown.watch
    if r0_6 then
      r0_6.recovery_watch = nil
    end
    local r1_6 = r0_0.Cooldown.spr
    if r1_6 then
      display.remove(r1_6)
    end
    r0_0.Cooldown.spr = nil
    r0_0.Cooldown.rtImg.isVisible = false
  end
end
local function r10_0()
  -- line: [84, 97] id: 7
  if r0_0.ReleaseConfirm then
    for r3_7, r4_7 in pairs(r0_0.ReleaseConfirm) do
      display.remove(r4_7)
    end
    r0_0.ReleaseConfirm = nil
  end
  if _G.SPanelRoot ~= nil then
    for r3_7 = 1, _G.SPanelRoot.numChildren, 1 do
      _G.SPanelRoot[r3_7].isVisible = false
    end
  end
end
local function r11_0(r0_8, r1_8)
  -- line: [100, 120] id: 8
  local r2_8 = false
  if r0_8 and r0_0.CircleTransition then
    transition.cancel(r0_0.CircleTransition)
    r0_0.CircleTransition = nil
    r0_0.CircleSpr.isVisible = false
    r2_8 = true
  end
  if r1_8 and r0_0.CircleUpgradeTransition then
    transition.cancel(r0_0.CircleUpgradeTransition)
    r0_0.CircleUpgradeTransition = nil
    r0_0.CircleUpgradeSpr.isVisible = false
    r2_8 = true
  end
  return r2_8
end
local function r12_0(r0_9)
  -- line: [123, 134] id: 9
  if r0_0.CircleTransition then
    transition.cancel(r0_0.CircleTransition)
  end
  r0_9.rotation = 0
  r0_0.CircleTransition = transition.to(r0_9, {
    time = 3000,
    rotation = 360,
    transition = easing.linear,
    onComplete = r12_0,
  })
end
local function r13_0(r0_10)
  -- line: [137, 148] id: 10
  if r0_0.CircleUpgradeTransition then
    transition.cancel(r0_0.CircleUpgradeTransition)
  end
  r0_10.rotation = 0
  r0_0.CircleUpgradeTransition = transition.to(r0_10, {
    time = 8000,
    rotation = 360,
    transition = easing.linear,
    onComplete = r13_0,
  })
end
local function r14_0()
  -- line: [151, 157] id: 11
  if r0_0.SummonPlateGroup then
    r0_0.SummonPlateGroup:removeSelf()
    r0_0.SummonPlateGroup = nil
  end
end
local function r15_0()
  -- line: [160, 180] id: 12
  for r3_12, r4_12 in pairs(r0_0.SummonGroupSpr) do
    for r8_12, r9_12 in pairs(r4_12) do
      r9_12.isVisible = false
    end
    r0_0.SummonSelectCrystalSpr[r3_12].isVisible = false
    r0_0.SummonSelectMpSpr[r3_12].isVisible = false
    r0_0.EvoSummonSelectCrystalSpr[r3_12].isVisible = false
    r0_0.EvoSummonSelectMpSpr[r3_12].isVisible = false
  end
  for r3_12, r4_12 in pairs(r0_0.SummonCrystalGroupSpr) do
    for r8_12, r9_12 in pairs(r4_12) do
      r9_12.isVisible = false
    end
    r0_0.SummonSelectCrystalSpr[r3_12].isVisible = false
    r0_0.SummonSelectMpSpr[r3_12].isVisible = false
    r0_0.EvoSummonSelectCrystalSpr[r3_12].isVisible = false
    r0_0.EvoSummonSelectMpSpr[r3_12].isVisible = false
  end
end
local function r24_0()
  -- line: [451, 474] id: 22
  if r0_0.UseCrystalMode then
    r0_0.UseCrystalMode = false
    game.PossessingCrystalVisible(false)
    game.ResetPossessingCrystalPosition()
    r0_0.EvoChar.SetSystemPause(false)
    if game ~= nil and game.IsNotPauseTypeNone() and game.GetPauseType() ~= r1_0.PauseTypeFirstPause then
      game.Play({
        [r1_0.PauseFuncMoveEnemy] = true,
        [r1_0.PauseFuncMoveChars] = true,
        [r1_0.PauseFuncEnablePlayPauseButton] = false,
        [r1_0.PauseFuncShowGuideBlink] = false,
        [r1_0.PauseFuncShowTicket] = false,
        [r1_0.PauseFuncEnableUseOrbButton] = true,
        [r1_0.PauseFuncClosePulldownMenu] = true,
      })
    end
  end
end
local function r25_0()
  -- line: [477, 495] id: 23
  if r0_0.SummonSelectPlateSpr.isVisible == true then
    if r0_0.SummonSelectPlateSpr ~= nil and r0_0.SummonSelectPlateSpr[1].y < 296 then
      game.SetPossessingCrystalPosition(514, 300)
    else
      game.SetPossessingCrystalPosition(514, 326)
    end
  elseif r0_0.SummonPlateGroup ~= nil and r0_0.SummonPlateGroup.isPos == 1 then
    game.SetPossessingCrystalPosition(514, 360)
  else
    game.SetPossessingCrystalPosition(514, 360)
  end
  game.PossessingCrystalVisible(true)
end
local function r26_0(r0_24)
  -- line: [497, 513] id: 24
  if r0_24 == 1 then
    r0_0.SummonSelectPlateSpr[1].x = 25
    r0_0.SummonSelectPlateSpr[1].y = 63
    r0_0.SummonSelectPlateSpr[2].x = 749 + r0_0.SummonSelectPlateSpr[2].width * 0.5
    r0_0.SummonSelectPlateSpr[2].y = 304 + r0_0.SummonSelectPlateSpr[2].height * 0.5 - 5
    r0_0.SummonSelectPlateSpr[3].x = 749 + r0_0.SummonSelectPlateSpr[3].width * 0.5
    r0_0.SummonSelectPlateSpr[3].y = 304 + r0_0.SummonSelectPlateSpr[3].height * 0.5 - 5
  else
    r0_0.SummonSelectPlateSpr[1].x = 25
    r0_0.SummonSelectPlateSpr[1].y = 296
    r0_0.SummonSelectPlateSpr[2].x = 749 + r0_0.SummonSelectPlateSpr[2].width * 0.5
    r0_0.SummonSelectPlateSpr[2].y = 335 + r0_0.SummonSelectPlateSpr[2].height * 0.5 - 5
    r0_0.SummonSelectPlateSpr[3].x = 749 + r0_0.SummonSelectPlateSpr[3].width * 0.5
    r0_0.SummonSelectPlateSpr[3].y = 335 + r0_0.SummonSelectPlateSpr[3].height * 0.5 - 5
  end
end
local function r27_0(r0_25, r1_25)
  -- line: [515, 657] id: 25
  r15_0()
  if r0_0.IsUseCrystal then
    r0_0.SummonSelectPlateSpr[2].isVisible = true
    r0_0.SummonSelectPlateSpr[3].isVisible = false
  else
    r0_0.CrystalSpriteGrp.isVisible = false
    r0_0.SummonSelectPlateSpr[2].isVisible = false
    r0_0.SummonSelectPlateSpr[3].isVisible = true
  end
  if game ~= nil and game.GetPauseType() == r1_0.PauseTypeFirstPause or r0_0.CrystalSpriteGrp.isVisible then
    game.BlinkMode(not r0_25)
  end
  if r0_25 == true then
    local r2_25 = nil
    local r3_25 = nil
    local r4_25 = nil
    local r5_25 = nil
    local r6_25 = nil
    r0_0.SummonSelectPlateSpr.isVisible = true
    r0_0.SummonSelectPlateSpr.alpha = 1
    if r1_25 < 319 then
      r6_25 = r3_0[2]
      r26_0(2)
      r0_0.CrystalSpriteGrp.x = 408
      r0_0.CrystalSpriteGrp.y = 350
    else
      r6_25 = r3_0[1]
      r26_0(1)
      r0_0.CrystalSpriteGrp.x = 408
      r0_0.CrystalSpriteGrp.y = 287
    end
    local r7_25 = r0_0.SummonGroupSpr
    if r0_0.IsUseCrystal then
      r7_25 = r0_0.SummonCrystalGroupSpr
    end
    for r11_25, r12_25 in pairs(r7_25) do
      r2_25 = r0_0.CharDef.UnitPanelOrder[r11_25]
      if r2_25 ~= r0_0.CharDef.CharId.Flare then
        r4_25 = r6_25[r11_25][1]
        r5_25 = r6_25[r11_25][2]
        if r0_0.IsUseCrystal then
          r3_25 = r0_0.SummonCrystalStatus[r2_25]
        else
          r3_25 = r0_0.SummonStatus[r2_25]
        end
        r0_0.SummonSelectCrystalSpr[r11_25].isVisible = false
        r0_0.SummonSelectMpSpr[r11_25].isVisible = false
        r0_0.EvoSummonSelectCrystalSpr[r11_25].isVisible = false
        r0_0.EvoSummonSelectMpSpr[r11_25].isVisible = false
        for r16_25, r17_25 in pairs(r12_25) do
          if r16_25 == r3_25 then
            r17_25.x = r4_25
            r17_25.y = r5_25
            r17_25.isVisible = true
            if r3_25 <= 2 or r3_25 == 6 or r3_25 == 7 or r3_25 == 8 then
              if r0_0.IsUseCrystal then
                r0_0.SummonSelectCrystalSpr[r11_25].x = r4_25 + 18
                r0_0.SummonSelectCrystalSpr[r11_25].y = r5_25 + 83
                r0_0.SummonSelectCrystalSpr[r11_25].alpha = 1
                r0_0.SummonSelectCrystalSpr[r11_25].isVisible = true
                if r3_25 == 6 or r3_25 == 7 or r3_25 == 8 then
                  r0_0.EvoSummonSelectCrystalSpr[r11_25].x = r4_25 + 18
                  r0_0.EvoSummonSelectCrystalSpr[r11_25].y = r5_25 + 17
                  r0_0.EvoSummonSelectCrystalSpr[r11_25].alpha = 1
                  r0_0.EvoSummonSelectCrystalSpr[r11_25].isVisible = true
                end
              else
                r0_0.SummonSelectMpSpr[r11_25].x = r4_25 + 18
                r0_0.SummonSelectMpSpr[r11_25].y = r5_25 + 83
                r0_0.SummonSelectMpSpr[r11_25].alpha = 1
                r0_0.SummonSelectMpSpr[r11_25].isVisible = true
                if r3_25 == 6 or r3_25 == 7 or r3_25 == 8 then
                  r0_0.EvoSummonSelectMpSpr[r11_25].x = r4_25 + 18
                  r0_0.EvoSummonSelectMpSpr[r11_25].y = r5_25 + 17
                  r0_0.EvoSummonSelectMpSpr[r11_25].alpha = 1
                  r0_0.EvoSummonSelectMpSpr[r11_25].isVisible = true
                end
              end
            end
            r17_25.alpha = 1
          else
            r17_25.isVisible = false
          end
        end
      end
    end
    if r0_0.IsUseCrystal == true then
      r25_0()
      r0_0.UseCrystalMode = true
      r0_0.TutorialManager.SelectSummonTutorial(false)
    elseif r0_0.TutorialManager.IsSummonTutorial and _G.UserStatus[1].cost[1] <= _G.MP then
      local r8_25 = 115
      local r9_25 = 235
      local r10_25 = nil
      if r1_25 < 319 then
        r8_25 = 60
        r9_25 = 455
        r10_25 = 270
      end
      r0_0.TutorialManager.SelectSummonTutorial(true, r0_0.SummonSelectMpSpr, r8_25, r9_25, r10_25)
    end
    r0_0.TutorialManager.TapFieldTutorial(false)
    if r0_0.TutorialManager.IsSummonTutorial and r0_0.TutorialManager.FirstTapFieldFlag == false then
      r0_0.TutorialManager.FirstTapFieldFlag = true
    end
    r0_0.TutorialManager.UpgradeCharTutorial(false)
    r0_0.TutorialManager.OrbBtnTutorial(false)
  else
    r0_0.SummonSelectPlateSpr.isVisible = false
    r0_0.CrystalSpriteGrp.isVisible = false
    r0_0.SummonMonitoring = nil
    r0_0.TutorialManager.TapFieldTutorial(false)
    r0_0.TutorialManager.SelectSummonTutorial(false)
    r0_0.TutorialManager.SummonTutorial(false)
    r0_0.TutorialManager.UpgradeCharTutorial(false)
    r0_0.TutorialManager.UpgradeBtnTutorial(false)
  end
end
local function r28_0(r0_26)
  -- line: [662, 753] id: 26
  if r0_0.SummonPlateGroup == nil then
    return 
  end
  local r1_26 = 0
  if r0_0.SummonPlateGroup.isPos == 1 then
    _G.UpgradeRoot.y = -50
    r0_0.UpgradeBtn[3].y = 370
    r1_26 = 410
  else
    _G.UpgradeRoot.y = 270
    r0_0.UpgradeBtn[3].y = 50
    r1_26 = 90
  end
  if r0_26.type == r0_0.CharDef.CharId.Kala then
    _G.UpgradeRoot.x = -110
    r0_0.UpgradeBtn[2].x = 560 - r0_0.UpgradeBtn[2].width * 0.5
  else
    r0_0.UpgradeBtn[13].struct = r0_26
    r0_0.UpgradeBtn[13].isVisible = true
    r0_0.UpgradeBtn[14].struct = r0_26
    r0_0.UpgradeBtn[14].isVisible = false
    _G.UpgradeRoot.x = 0
    r0_0.UpgradeBtn[2].x = r0_0.UpgradeBtn[1].x + r0_0.UpgradeBtn[1].width + 74
  end
  local r3_26 = r0_26.level + 1
  local r4_26 = _G.UserStatus[r0_26.type].cost[r3_26]
  local r5_26 = true
  if r0_26.type == r0_0.CharDef.CharId.Kala then
    return 
  end
  if r3_26 > 4 then
    r0_0.UpgradeBtn[5].isVisible = true
  elseif r3_26 == 4 and r0_0.Level4Lock[r0_26.type] then
    r0_0.UpgradeBtn[7].isVisible = true
  elseif _G.MP < r4_26 then
    r7_0(r0_0.UpgradeBtnNum[2], r4_26)
    r0_0.UpgradeBtn[4].isVisible = true
    r0_0.UpgradeMonitoring = {
      struct = r0_26,
      mp = r4_26,
    }
  else
    local r6_26 = nil
    r7_0(r0_0.UpgradeBtnNum[1], r4_26)
    r6_26 = r0_0.UpgradeBtn[1]
    r0_26.upgrade_mp = r4_26
    r6_26.rslt = r5_26
    r6_26.isVisible = true
    r6_26.struct = r0_26
    r0_0.UpgradeBtn[4].isVisible = false
    r0_0.TutorialManager.UpgradeBtnTutorial(true, _G.UpgradeRoot, r6_26.x, r6_26.y)
    r0_0.TutorialManager.UpgradeCharTutorial(false)
    if r0_0.TutorialManager.IsUpgradeTutorial and r0_0.TutorialManager.FirstUpgradeCharFlag == false then
      r0_0.TutorialManager.FirstUpgradeCharFlag = true
    end
    local r7_26 = nil
    r7_0(r0_0.UpgradeBtnNum[3], r4_26)
    r7_26 = r0_0.UpgradeBtn[11]
    r0_26.upgrade_mp = r4_26
    r7_26.rslt = r5_26
    r7_26.isVisible = false
    r7_26.struct = r0_26
    r0_0.UpgradeBtn[12].isVisible = false
  end
end
local function r29_0(r0_27)
  -- line: [755, 786] id: 27
  local r4_27 = _G.UserStatus[r0_27.type].sell[r0_27.level]
  if r4_27 == nil then
    r4_27 = 0
  end
  local r5_27 = r0_0.UpgradeBtn[2]
  r0_27.release_mp = r4_27
  local r6_27 = nil
  local r7_27 = nil
  if r4_27 > 0 then
    for r11_27 = 1, r0_0.ReleaseBtnNum.numChildren, 1 do
      r4_27 = math.floor(r4_27 * 0.1)
      r0_0.ReleaseBtnNum[r11_27]:setFrame(r4_27 % 10 + 1)
    end
  else
    for r11_27 = 1, r0_0.ReleaseBtnNum.numChildren, 1 do
      r0_0.ReleaseBtnNum[r11_27]:setFrame(1)
    end
  end
  r0_0.ReleaseBtnNum.isVisible = true
  r5_27.rslt = rslt
  r5_27.isVisible = true
  r5_27.struct = r0_27
end
local function r31_0(r0_29)
  -- line: [792, 803] id: 29
  r0_0.TutorialManager.TapFieldTutorial(false)
  r28_0(r0_29)
  r29_0(r0_29)
  r0_0.UpgradeBtn[3].struct = r0_29
  r0_0.UpgradeBtn[3].isVisible = true
  r6_0(r0_29).custom_popup_upgrade_btn(r0_29)
end
local function r32_0(r0_30)
  -- line: [806, 840] id: 30
  local r1_30 = r0_30.type
  local r5_30 = _G.UserStatus[r1_30].range[(r0_30.level + 1)][2] * 2 * (r0_0.RangePower + 100) / 100
  local r6_30 = r0_0.CircleUpgradeSpr
  local r9_30 = r5_30 / r6_30.width
  local r10_30 = r5_30 / r6_30.height
  r6_30.xScale = 0.001
  r6_30.yScale = 0.001
  r6_30.alpha = 1
  r6_30.x = r0_30.x
  r6_30.y = r0_30.y
  if r1_30 == r0_0.CharDef.CharId.Yuiko then
    r6_30.x = r6_30.x + 6
  end
  r6_30.isVisible = true
  r0_0.CircleUpgradeTransition = transition.to(r6_30, {
    time = 250,
    xScale = r9_30,
    yScale = r10_30,
    rotation = 360,
    transition = easing.outQuad,
    onComplete = r13_0,
  })
end
local function r33_0(r0_31)
  -- line: [842, 869] id: 31
  r11_0(false, true)
  r8_0()
  r10_0()
  r14_0()
  if r0_0.SummonConfirm then
    display.remove(r0_0.SummonConfirm.spr[1])
    display.remove(r0_0.SummonConfirm.spr[2])
    display.remove(r0_0.SummonConfirm.spr[3])
    display.remove(r0_0.SummonConfirm.spr[4])
    display.remove(r0_0.SummonConfirm.spr[5])
    r0_0.SummonConfirm = nil
    r11_0(true, true)
  end
  r27_0(false)
  if r0_31 ~= nil and r0_31 == true then
    r24_0()
  end
end
local function r34_0(r0_32, r1_32, r2_32)
  -- line: [874, 884] id: 32
  if r1_32.phase == "ended" then
    r33_0(true)
  end
  r0_0.ShowUnitPanelFlag = false
  return true
end
local function r35_0(r0_33, r1_33, r2_33, r3_33)
  -- line: [888, 1052] id: 33
  local r4_33 = nil	-- notice: implicit variable refs by block#[54]
  if r3_33 ~= nil and r3_33.struct ~= nil then
    r4_33 = r3_33.struct
  end
  local function r6_33(r0_34)
    -- line: [899, 899] id: 34
    return "data/game/plate/" .. r0_34 .. ".png"
  end
  local function r7_33(r0_35)
    -- line: [901, 901] id: 35
    return r6_33(r0_35 .. "_" .. _G.UILanguage)
  end
  local function r8_33(r0_36)
    -- line: [903, 903] id: 36
    return "data/game/upgrade/" .. r0_36 .. ".png"
  end
  local function r9_33(r0_37)
    -- line: [905, 905] id: 37
    return r8_33(r0_37 .. _G.UILanguage)
  end
  r0_0.SummonPlateGroup = display.newGroup()
  local r10_33 = display.newImage(r0_0.SummonPlateGroup, r6_33("plate_background"), true)
  function r10_33.touch()
    -- line: [911, 920] id: 38
    r27_0(false)
    r9_0()
    r10_0()
    r14_0()
    return true
  end
  r10_33:addEventListener("touch", spr)
  local r12_33 = display.newImage(r0_0.SummonPlateGroup, r6_33(string.format("status_cha%02d", r0_33)), true)
  r12_33:setReferencePoint(display.TopRightReferencePoint)
  r12_33.x = 820
  display.newImage(r0_0.SummonPlateGroup, r7_33(string.format("plate_name_%02d", r0_33)), 10, 10, true)
  local r5_33 = nil	-- notice: implicit variable refs by block#[6]
  if r2_33 < 319 then
    r5_33 = r2_0[2]
    r0_0.SummonPlateGroup.isPos = 2
  else
    r5_33 = r2_0[1]
    r0_0.SummonPlateGroup.isPos = 1
  end
  r0_0.SummonPlateGroup.x = r5_33[1]
  r0_0.SummonPlateGroup.y = r5_33[2]
  if r0_33 == r0_0.CharDef.CharId.BlueMagicalFlower then
    display.newImage(r0_0.SummonPlateGroup, r7_33("plate_cha15_ex"), 18, 58, true)
  elseif r0_33 == r0_0.CharDef.CharId.Kala then
    display.newImage(r0_0.SummonPlateGroup, r7_33("plate_cha17_ex"), 18, 58, true)
  else
    local r14_33 = 100
    display.newImage(r0_0.SummonPlateGroup, r7_33("text_enemy"), r14_33, 58, true)
    local r15_33 = _G.UserStatus[r0_33]
    local r16_33 = r1_33 - 1
    local r17_33 = true
    if r1_33 == 1 then
      r16_33 = 1
      r17_33 = false
    end
    if r1_33 >= 5 then
      r16_33 = 4
      r17_33 = false
    end
    if r1_33 == 4 and r0_0.Level4Lock[r0_33] then
      r16_33 = 3
      r17_33 = false
    end
    local r18_33 = 130
    local r19_33 = 61
    local r20_33 = r15_33.power[r16_33]
    local r21_33 = r15_33.speed[r16_33]
    local r22_33 = r15_33.range[r16_33][2]
    if r0_33 == r0_0.CharDef.CharId.Sarah then
      r20_33 = 0
    elseif r0_33 == r0_0.CharDef.CharId.Luna then
      r20_33 = 0
      r22_33 = 0
    elseif r0_33 == r0_0.CharDef.CharId.LiliLala then
      r20_33 = 0
      r21_33 = 0
      r22_33 = 0
    elseif r0_33 == r0_0.CharDef.CharId.Amber then
      r20_33 = -1
      r22_33 = -1
    elseif r0_33 == r0_0.CharDef.CharId.Nina then
      r21_33 = 10
    end
    local r23_33 = 7
    if r0_33 == r0_0.CharDef.CharId.Daisy or r0_33 == r0_0.CharDef.CharId.Cecilia or r0_33 == r0_0.CharDef.CharId.DaisyA then
      r23_33 = 1
    elseif r0_33 == r0_0.CharDef.CharId.Becky or r0_33 == r0_0.CharDef.CharId.Bianca or r0_33 == r0_0.CharDef.CharId.Tiana then
      r23_33 = 2
    elseif r0_33 == r0_0.CharDef.CharId.Chloe or r0_33 == r0_0.CharDef.CharId.Lillian then
      r23_33 = 3
    elseif r0_33 == r0_0.CharDef.CharId.Nicola or r0_33 == r0_0.CharDef.CharId.Iris then
      r23_33 = 4
    elseif r0_33 == r0_0.CharDef.CharId.Chiara or r0_33 == r0_0.CharDef.CharId.Lyra or r0_33 == r0_0.CharDef.CharId.Bell then
      r23_33 = 5
    elseif r0_33 == r0_0.CharDef.CharId.LiliLala then
      r23_33 = 6
    elseif r0_33 == r0_0.CharDef.CharId.Amber then
      r23_33 = 8
    elseif r0_33 == r0_0.CharDef.CharId.Nina then
      r23_33 = 9
    elseif r0_33 == r0_0.CharDef.CharId.Jill then
      r23_33 = 10
    end
    display.newImage(r0_0.SummonPlateGroup, r6_33(string.format("status_enemyset_%02d", r23_33)), r14_33, 85, true)
  end
  display.newImage(r0_0.SummonPlateGroup, r6_33("plate_frame"), true)
  local r14_33 = display.newImage(r0_0.SummonPlateGroup, r6_33("close"), 760, -20, true)
  function r14_33.touch(r0_39, r1_39)
    -- line: [1042, 1045] id: 39
    r34_0(r0_39, r1_39, r4_33)
    return true
  end
  r14_33:addEventListener("touch")
  _G.SPanelRoot:insert(r0_0.SummonPlateGroup)
  r0_0.ShowUnitPanelFlag = true
end
local function r36_0()
  -- line: [1055, 1071] id: 40
  if game.GetPauseType() == r1_0.PauseTypeStopClockMenu or game.GetPauseType() == r1_0.PauseTypeStopClock then
    return true
  end
  r0_0.IsSnipeMode = false
  r0_0.PanelTrantision = transition.to(_G.PanelRoot, {
    time = 500,
    y = 0,
    transition = easing.outExpo,
  })
  if r0_0.SnipemodeWindowImg then
    r0_0.SnipemodeWindowImg:setReferencePoint(display.TopLeftReferencePoint)
    transition.to(r0_0.SnipemodeWindowImg, {
      time = 500,
      y = -74,
      transition = easing.outExpo,
    })
  end
  if r0_0.SnipeImg then
    r0_0.SnipeImg:removeSelf()
    r0_0.SnipeImg = nil
  end
end
return {
  range_func = function(r0_21, r1_21, r2_21)
    -- line: [362, 448] id: 21
    if game ~= nil and game.IsNotPauseTypeNone() and game.GetPauseType() ~= r1_0.PauseTypeStopClock then
      return true
    end
    local r3_21 = r1_21
    local r4_21 = r3_21.func
    if r0_0.SelectTarget and r0_0.SelectTarget ~= r3_21.target then
      local r5_21 = r0_0.SelectTarget.attack
      local r6_21 = r3_21.attack
      if (r5_21[1] and r6_21[1] or r5_21[2] and r6_21[2]) and r4_21.check(r3_21, r0_0.SelectTarget) then
        r3_21.target = r0_0.SelectTarget
      end
    end
    if r3_21.target then
      if not r3_21.target.living then
        r3_21.target = nil
      end
      if r3_21.target_cancel then
        r3_21.target = r3_21.func.search(r3_21)
        r3_21.target_cancel = false
      end
    end
    if r3_21.target and not r3_21.shooting then
      if r4_21.check(r3_21, r3_21.target) then
        local r5_21 = r4_21.pointing(r3_21, r3_21.target)
        if r5_21 then
          if r4_21.lockon then
            r4_21.lockon(r3_21, r3_21.target)
          end
          r3_21.vect = r5_21
          r3_21.shooting = true
          local r6_21 = r3_21.anime
          anime.Pause(r6_21, false)
          local r7_21 = nil
          if r3_21.type == r0_0.CharDef.CharId.Jill then
            r7_21 = r3_21.wait[r3_21.level] - r0_0.AttackSpeed * 0.01
          else
            r7_21 = (r3_21.wait[r3_21.level] + r0_0.AttackSpeed) / 100
          end
          anime.Show(r6_21, true, {
            scale = r7_21,
          })
          anime.RegisterTrigger(r6_21, r3_21.shot_frame_nr, r4_21.shoot, r3_21)
          anime.RegisterFinish(r6_21, r4_21.finish, r3_21)
        end
      elseif not r3_21.shooting then
        r3_21.target = nil
      end
    end
    if r3_21.target == nil then
      if r3_21.next_target then
        r3_21.target = r3_21.next_target
        r3_21.next_target = nil
      else
        r3_21.target = r4_21.search(r3_21)
      end
      if r3_21.target then
        r4_21.pointing(r3_21, r3_21.target)
      else
        anime.ChangeVect(r3_21.anime, 1)
        if r3_21.shooting then
          r4_21.finish(r3_21.anime, r3_21)
        end
      end
    end
    return true
  end,
  pointing_func = function(r0_20, r1_20)
    -- line: [340, 359] id: 20
    local r4_20 = r1_20.ptdiff
    local r10_20 = (math.floor((math.atan2((r1_20.sy + r4_20[2] - r0_20.y), (r1_20.sx + r4_20[1] - r0_20.x)) * 8 / math.pi + 7) / 2) + 3) % 8 + 1
    if not r0_20.non_anime_vect then
      anime.ChangeVect(r0_20.anime, r10_20)
    end
    return r10_20
  end,
  shoot_func = function(r0_19, r1_19)
    -- line: [321, 337] id: 19
    local r2_19 = r1_19.func
    if r1_19.target == nil then
      if r2_19.fumble then
        r2_19.fumble(r1_19)
      end
      return 
    end
    if r1_19.next_target then
      r1_19.target = r1_19.next_target
      r1_19.next_target = nil
    end
    r2_19.pointing(r1_19, r1_19.target)
    local r3_19 = anime.GetPos(r0_19)
    r2_19.load(r3_19[1], r3_19[2], r1_19.target, r1_19)
  end,
  finish_func = function(r0_18, r1_18)
    -- line: [305, 317] id: 18
    if r1_18.target and not r1_18.func.check(r1_18, r1_18.target) then
      r1_18.target = nil
    end
    r1_18.shooting = false
    anime.Pause(r0_18, true)
    anime.SetTimer(r0_18, 0)
  end,
  search_func = function(r0_16)
    -- line: [256, 302] id: 16
    local function r1_16(r0_17)
      -- line: [257, 264] id: 17
      local r1_17 = r0_17.sx
      local r2_17 = r0_17.sy
      local r3_17 = r0_17.ex
      local r4_17 = r0_17.ey
      return (r1_17 - r3_17) * (r1_17 - r3_17) + (r2_17 - r4_17) * (r2_17 - r4_17)
    end
    local r2_16 = nil
    local r3_16 = r0_16.x
    local r4_16 = r0_16.y
    local r5_16 = r0_16.range[1]
    local r6_16 = r0_16.range[2]
    local r7_16 = 1000000
    local r8_16 = 0
    local r9_16 = 1000000
    for r13_16, r14_16 in pairs(_G.Enemys) do
      if r14_16.living and (r0_16.attack[1] and r14_16.attack[1] or r0_16.attack[2] and r14_16.attack[2]) then
        local r15_16 = r14_16.ptdiff
        local r16_16 = r14_16.sx + r15_16[1]
        local r17_16 = r14_16.sy + r15_16[2]
        local r18_16 = (r16_16 - r3_16) * (r16_16 - r3_16) + (r17_16 - r4_16) * (r17_16 - r4_16)
        if r5_16 <= r18_16 and r18_16 <= r6_16 then
          local r19_16 = false
          local r20_16 = r14_16.index
          if r8_16 < r20_16 then
            r8_16 = r20_16
            r9_16 = r1_16(r14_16)
            r19_16 = true
          elseif r8_16 == r20_16 and r1_16(r14_16) < r9_16 then
            r19_16 = true
          end
          if r19_16 then
            r7_16 = r18_16
            r2_16 = r14_16
          end
        end
      end
    end
    return r2_16
  end,
  check_func = function(r0_15, r1_15)
    -- line: [238, 252] id: 15
    if not r1_15.living then
      return false
    end
    local r2_15 = r0_15.x
    local r3_15 = r0_15.y
    local r4_15 = r1_15.ptdiff
    local r5_15 = r1_15.sx + r4_15[1]
    local r6_15 = r1_15.sy + r4_15[2]
    local r9_15 = r0_15.range[2] >= (r2_15 - r5_15) * (r2_15 - r5_15) + (r3_15 - r6_15) * (r3_15 - r6_15)
  end,
  release_func = function(r0_14)
    -- line: [233, 235] id: 14
  end,
  touch_func = function(r0_44, r1_44)
    -- line: [1096, 1209] id: 44
    -- notice: unreachable block#43
    if r1_44.phase == "ended" then
      r33_0(true)
      if r0_0.EvoChar.SelectEvoChar(r0_44.struct, {
        r4_0(r0_44.struct.x),
        r5_0(r0_44.struct.y)
      }) == true then
        return true
      end
      if r0_0.EvoChar.CancelUseOrbModeEvoLevelSelect(r4_0(r1_44.x), r5_0(r1_44.y)) == true then
        return true
      end
    end
    r36_0()
    if game.GetPauseType() == r1_0.PauseTypeStopClockMenu or game.GetPauseType() == r1_0.PauseTypeStopClock then
      return 
    end
    if game ~= nil and game.IsNotPauseTypeNone() and game.GetPauseType() ~= r1_0.PauseTypeFirstPause then
      return true
    end
    local r2_44 = r0_44.struct
    if r1_44.phase == "ended" then
      if game ~= nil and game.GetPauseType() ~= r1_0.PauseTypeFirstPause then
        local r4_44 = (r1_44.time - r0_0.touch_time) / 1000
        r0_0.touch_time = r1_44.time
        local r5_44 = r0_0.touch_pos.x
        if r5_44 == r2_44.x then
          r5_44 = r0_0.touch_pos.y == r2_44.y
        else
          r5_44 = r5_44 == r2_44.x	-- block#17 is visited secondly
        end
        r0_0.touch_pos.x = r2_44.x
        r0_0.touch_pos.y = r2_44.y
        if r4_44 <= 0.8 and r5_44 and r6_0(r2_44).coustom_skill_touch_func(r0_44) then
          return true
        end
      end
      if r1_44.summon then
        return false
      end
      if r0_0.CrystalSpriteGrp then
        r0_0.CrystalSpriteGrp.isVisible = false
      end
      r14_0()
      r9_0()
      r10_0()
      if r0_0.SummonTransition then
        return false
      end
      if r11_0(true, true) then
        return true
      end
      if r2_44.not_select then
        return true
      end
      if r6_0(r2_44).coustom_anime_touch_func(r2_44) then
        return true
      end
      r35_0(r2_44.type, r2_44.level + 1, r2_44.y, {
        struct = r2_44,
      })
      r31_0(r2_44)
      sound.PlaySE(4, 21)
      local r4_44 = r0_0.CircleSpr
      local r7_44 = r2_44.range_circle / r4_44.width
      local r8_44 = r2_44.range_circle / r4_44.height
      r4_44:setReferencePoint(display.CenterReferencePoint)
      r4_44.x = r2_44.x
      r4_44.y = r2_44.y
      if r2_44.type == r0_0.CharDef.CharId.Yuiko then
        r4_44.x = r4_44.x + 6
      end
      r4_44.xScale = 0.001
      r4_44.yScale = 0.001
      r4_44.rotation = 270
      r4_44.isVisible = true
      if r6_0(r2_44).coustom_circle_touch_func(r2_44, r7_44, r8_44) then
        return true
      end
      if r2_44.level + 1 < 5 then
        local r9_44 = r2_44.level + 1
        if r9_44 == 4 then
          r9_44 = r0_0.Level4Lock[r2_44.type]
        else
          r9_44 = false
        end
        if r9_44 == false then
          r32_0(r2_44)
        end
      end
    end
    return true
  end,
  kill_char = function(r0_13, r1_13)
    -- line: [183, 230] id: 13
    if r1_13 == nil then
      r1_13 = false
    end
    if r0_13.func.release then
      r0_13.func.release(r0_13)
    end
    if r0_13.recovery_ev then
      events.Delete(r0_13.recovery_ev)
      r0_13.recovery_ev = nil
    end
    if r0_13.shot_ev then
      events.Delete(r0_13.shot_ev)
      r0_13.shot_ev = nil
    end
    if r0_13.anime then
      anime.Remove(r0_13.anime)
      r0_13.anime = nil
    end
    if r0_13.star then
      display.remove(r0_13.star)
      r0_13.star = nil
    end
    if r0_13.buff_anm then
      anime.Remove(r0_13.buff_anm)
      r0_13.buff_anm = nil
    end
    if r0_13.buff_anm2 then
      anime.Remove(r0_13.buff_anm2)
      r0_13.buff_anm2 = nil
    end
    if r0_13.touch_area then
      display.remove(r0_13.touch_area)
      r0_13.touch_area = nil
    end
    local r2_13 = r0_13.map_xy
    if r0_13.type == 100 then
      _G.MapLocation[r2_13[2]][r2_13[1]] = 256
    else
      _G.MapLocation[r2_13[2]][r2_13[1]] = 0
    end
    r6_0(r0_13).custom_kill_char(r0_13)
    if r1_13 then
      return 
    end
    local r3_13 = table.indexOf(r0_0.MyChar, r0_13)
    if r3_13 then
      table.remove(r0_0.MyChar, r3_13)
    end
  end,
  get_current_char = r6_0,
  circle_rolling = r12_0,
  not_show_upgrade_btn = r9_0,
  hide_upgrade_circle = r11_0,
  close_plate = r33_0,
  show_possessing_crystal = r25_0,
  release_release_confirm = r10_0,
  unlock_use_crystal_mode = r24_0,
  set_upgrade_btn_num = r7_0,
  view_summon_group = r27_0,
  remove_summon_plate = r14_0,
  display_summon_plate = r35_0,
  SUMMON_PLATE_POS = r2_0,
  get_map_location_x = r4_0,
  get_map_location_y = r5_0,
  lock_use_crystal_mode = function()
    -- line: [1212, 1231] id: 45
    r0_0.UseCrystalMode = true
    r25_0()
    r0_0.EvoChar.SetSystemPause(true)
    if game ~= nil and game.GetPauseType() ~= r1_0.PauseTypeFirstPause then
      game.Pause(r1_0.PauseTypeCrystalSummon, {
        [r1_0.PauseFuncMoveEnemy] = false,
        [r1_0.PauseFuncMoveChars] = false,
        [r1_0.PauseFuncEnablePlayPauseButton] = true,
        [r1_0.PauseFuncShowGuideBlink] = false,
        [r1_0.PauseFuncShowTicket] = false,
        [r1_0.PauseFuncEnableUseOrbButton] = false,
        [r1_0.PauseFuncClosePulldownMenu] = false,
      })
    end
  end,
  set_star = function(r0_46)
    -- line: [1233, 1252] id: 46
    local r1_46 = r0_46.star
    if r1_46 then
      display.remove(r1_46)
    end
    local r2_46 = r0_46.x - 40 + 52
    local r3_46 = r0_46.y - 40 - 12
    if r0_46.level == 2 then
      r1_46 = r0_0.Objects.UnitListParts.CreateIconUpgrade2(r0_46.star_rt)
    elseif r0_46.level == 3 then
      r1_46 = r0_0.Objects.UnitListParts.CreateIconUpgrade3(r0_46.star_rt)
    elseif r0_46.level == 4 then
      r1_46 = r0_0.Objects.UnitListParts.CreateIconUpgrade4(r0_46.star_rt)
    end
    r1_46:setReferencePoint(display.TopLeftReferencePoint)
    r1_46.x = r2_46
    r1_46.y = r3_46
    r0_46.star = r1_46
  end,
  snipe_mode_off = function()
    -- line: [1255, 1271] id: 47
    if game.GetPauseType() == r1_0.PauseTypeStopClockMenu or game.GetPauseType() == r1_0.PauseTypeStopClock then
      return true
    end
    r0_0.IsSnipeMode = false
    r0_0.PanelTrantision = transition.to(_G.PanelRoot, {
      time = 500,
      y = 0,
      transition = easing.outExpo,
    })
    if r0_0.SnipemodeWindowImg then
      r0_0.SnipemodeWindowImg:setReferencePoint(display.TopLeftReferencePoint)
      transition.to(r0_0.SnipemodeWindowImg, {
        time = 500,
        y = -74,
        transition = easing.outExpo,
      })
    end
    if r0_0.SnipeImg then
      r0_0.SnipeImg:removeSelf()
      r0_0.SnipeImg = nil
    end
  end,
  view_upgrade_btn = r28_0,
  coustom_skill_touch_func = function(r0_41)
    -- line: [1074, 1076] id: 41
    return false
  end,
  coustom_anime_touch_func = function(r0_42)
    -- line: [1078, 1080] id: 42
    return false
  end,
  coustom_circle_touch_func = function(r0_43, r1_43, r2_43)
    -- line: [1082, 1092] id: 43
    r0_0.CircleTransition = transition.to(r0_0.CircleSpr, {
      time = 250,
      xScale = r1_43,
      yScale = r2_43,
      rotation = 360,
      transition = easing.outQuad,
      onComplete = r12_0,
    })
    return false
  end,
  custom_popup_upgrade_btn = function(r0_28)
    -- line: [788, 790] id: 28
    r0_0.UpgradeBtn[15].isVisible = false
  end,
  custom_kill_char = function()
    -- line: [1279, 1279] id: 51
  end,
  check_upgrade_ok_achievement = function(r0_48)
    -- line: [1273, 1273] id: 48
  end,
  check_release_ok_achievement = function(r0_49)
    -- line: [1275, 1275] id: 49
  end,
  check_summon_achievement = function()
    -- line: [1277, 1277] id: 50
  end,
}
