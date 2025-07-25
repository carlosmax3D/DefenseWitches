-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("logic.pay_item_data")
local r1_0 = require("json")
local r2_0 = require("logic.char.CharStatus")
local r3_0 = require("logic.game.GameStatus")
local r4_0 = require("char.BaseChar")
local r5_0 = require("char.SummonChar")
local r6_0 = require("char.UpgradeChar")
local function r7_0(r0_1)
  -- line: [17, 22] id: 1
  if table.indexOf(r2_0.CharDef.PurchaseCharId, r0_1) ~= nil then
    return true
  end
  return false
end
local function r8_0()
  -- line: [27, 29] id: 2
  return r2_0.EvoChar.GetUseOrbMode()
end
local function r9_0(r0_3, r1_3)
  -- line: [33, 165] id: 3
  r4_0.snipe_mode_off()
  if r8_0() == r2_0.UseOrbModeCharSelect and r2_0.EvoChar.CancelUseOrbModeCharSelect(r0_3, r1_3) == true then
    return true
  elseif r8_0() == r2_0.UseOrbModeEvoLevelSelect and r2_0.EvoChar.CancelUseOrbModeEvoLevelSelect(r4_0.get_map_location_x(r0_3), r4_0.get_map_location_y(r1_3)) == true then
    return true
  end
  if game.GetPauseType() == r3_0.PauseTypeStopClockMenu or game.GetPauseType() == r3_0.PauseTypeStopClock then
    return 
  end
  if r2_0.CrystalSpriteGrp then
    r2_0.CrystalSpriteGrp.isVisible = false
  end
  if r2_0.SummonConfirm then
    display.remove(r2_0.SummonConfirm.spr[1])
    display.remove(r2_0.SummonConfirm.spr[2])
    display.remove(r2_0.SummonConfirm.spr[3])
    display.remove(r2_0.SummonConfirm.spr[4])
    display.remove(r2_0.SummonConfirm.spr[5])
    r2_0.SummonConfirm = nil
  end
  r4_0.remove_summon_plate()
  r4_0.not_show_upgrade_btn()
  r4_0.release_release_confirm()
  r5_0.clear_guide()
  if r2_0.GuideCircle then
    r5_0.clear_guide_circle()
    r4_0.unlock_use_crystal_mode()
    return true
  end
  if r5_0.hide_summon_circle() then
    r4_0.view_summon_group(false)
    r4_0.unlock_use_crystal_mode()
    return true
  end
  if r4_0.hide_upgrade_circle(true, true) then
    r4_0.view_summon_group(false)
    r4_0.unlock_use_crystal_mode()
    return true
  end
  if game ~= nil and game.GetPauseType() ~= r3_0.PauseTypeNone and game.GetPauseType() ~= r3_0.PauseTypeFirstPause then
    r4_0.unlock_use_crystal_mode()
    return false
  end
  local r2_3 = r4_0.get_map_location_x(r0_3)
  local r3_3 = r4_0.get_map_location_y(r1_3)
  if 1 <= r2_3 and r2_3 <= 12 and 2 <= r3_3 and r3_3 <= 8 then
    local r4_3 = r2_3 * 80 - 80 + 40
    local r5_3 = r3_3 * 80 - 80 + 40
    if _G.MapLocation[r3_3][r2_3] == 0 then
      sound.PlaySE(4, 21)
      r2_0.SummonPos = {
        r2_3,
        r3_3
      }
      r2_0.SummonSpr.x = r4_3
      r2_0.SummonSpr.y = r5_3
      r2_0.SummonSpr.isVisible = true
      r2_0.SummonSpr.rotation = 0
      r2_0.SummonTransition = transition.to(r2_0.SummonSpr, {
        time = 3000,
        rotation = 360,
        transition = easing.linear,
        onComplete = r5_0.circle_summon_rolling,
      })
      r2_0.SummonPos2 = {
        r2_3,
        r3_3
      }
      r2_0.CircleSpr.x = r4_3
      r2_0.CircleSpr.y = r5_3
      r2_0.CircleSpr.isVisible = false
      r2_0.CircleSpr.rotation = 0
      r2_0.IsUseCrystal = false
      r5_0.set_summon_status(r5_3)
      r2_0.TutorialManager.SummonTutorial(false)
      r4_0.view_summon_group(true, r5_3)
      return false
    else
      sound.PlaySE(8, 21)
      if r2_0.BadMarkTransition then
        transit.Delete(r2_0.BadMarkTransition)
        r2_0.BadMarkTransition = nil
      end
      r2_0.BadMarkSprite.x = r4_3
      r2_0.BadMarkSprite.y = r5_3
      r2_0.BadMarkSprite.alpha = 1
      r2_0.BadMarkSprite.isVisible = true
      r2_0.BadMarkTransition = transit.Register(r2_0.BadMarkSprite, {
        time = 1000,
        alpha = 0,
        transition = easing.outExpo,
        onComplete = function(r0_4)
          -- line: [155, 158] id: 4
          r2_0.BadMarkSprite.isVisible = false
          r2_0.BadMarkTransition = nil
        end,
      })
      game.SetPlayBtnSize()
    end
  end
  return true
end
local function r10_0(r0_5)
  -- line: [167, 173] id: 5
  if r0_5.phase == "ended" then
    return r9_0(r0_5.x, r0_5.y)
  end
  return false
end
local function r11_0(r0_6)
  -- line: [175, 185] id: 6
  local r1_6 = display.newGroup()
  local r3_6 = display.newImage(r1_6, string.format("data/game/unit/icon_%s.png", r0_6), 0, 0, true)
  r1_6:setReferencePoint(display.TopLeftReferencePoint)
  r1_6.isVisible = false
  _G.SPanelRoot:insert(r1_6)
  return r1_6
end
local function r12_0(r0_7, r1_7)
  -- line: [187, 197] id: 7
  for r5_7, r6_7 in pairs(r2_0.MyChar) do
    local r8_7 = r6_7.map_xy[2]
    if r6_7.map_xy[1] == r0_7 and r8_7 == r1_7 then
      return r6_7
    end
  end
  DebugPrint("not found resume upgrade/release character")
  return nil
end
local function r13_0(r0_8)
  -- line: [200, 211] id: 8
  for r4_8, r5_8 in pairs(_G.Enemys) do
    if r5_8.uid == r0_8 then
      return r5_8
    end
  end
  for r4_8, r5_8 in pairs(_G.CrashObject) do
    if r5_8.uid == r0_8 then
      return r5_8
    end
  end
  return nil
end
local function r14_0(r0_9, r1_9)
  -- line: [213, 222] id: 9
  for r5_9, r6_9 in pairs(r2_0.MyChar) do
    if r6_9.type == r2_0.CharDef.CharId.BlueMagicalFlower and r6_9.x == r0_9 and r6_9.y == r1_9 then
      return r6_9
    end
  end
end
local function r17_0(r0_12)
  -- line: [372, 374] id: 12
  r5_0.clear_all_circle(r0_12)
end
local function r20_0(r0_15, r1_15)
  -- line: [479, 487] id: 15
  local r4_15 = r0_15[1]
  local r5_15 = r0_15[2]
  local r6_15 = r0_15[3]
  local r7_15 = r0_15[4]
  if r1_15 == nil then
    r1_15 = false
  end
  r4_0.get_current_char({
    type = r4_15,
  }).CustomFirstSummon(r4_15, r5_15, r6_15, r7_15, r1_15, r0_15)
end
local function r25_0(r0_20)
  -- line: [568, 615] id: 20
  local r1_20 = r0_20.sx
  local r2_20 = r0_20.sy
  local r3_20 = r1_20 + r0_20.targetpos[1]
  local r4_20 = r2_20 + r0_20.targetpos[2]
  if r2_0.TargetSpr == nil then
    r2_0.TargetSpr = anime.Register(r2_0.target_efx.GetData(), r3_20, r4_20, "data/game")
    _G.TargetRoot:insert(anime.GetSprite(r2_0.TargetSpr))
    anime.Show(r2_0.TargetSpr, true)
    anime.Loop(r2_0.TargetSpr, true)
  end
  if r2_0.SelectTarget and r2_0.SelectTarget == r0_20 then
    r0_20.target_spr = nil
    anime.Show(r2_0.TargetSpr, false)
    for r8_20, r9_20 in pairs(r2_0.MyChar) do
      r9_20.target_cancel = true
    end
    r2_0.SelectTarget = nil
    save.DataPush("targetcancel", {
      id = r0_20.uid,
      x = r1_20,
      y = r2_20,
    })
    return 
  end
  if r2_0.SelectTarget then
    r2_0.SelectTarget.target_spr = nil
  end
  r0_20.target_spr = r2_0.TargetSpr
  r2_0.SelectTarget = r0_20
  for r8_20, r9_20 in pairs(r2_0.MyChar) do
    if r9_20.target and not r9_20.func.check(r9_20, r2_0.SelectTarget) then
      r9_20.target = nil
    end
  end
  anime.Show(r2_0.TargetSpr, true)
  local r5_20 = anime.GetPos(r2_0.TargetSpr)
  if r5_20[1] ~= r3_20 or r5_20[2] ~= r4_20 then
    anime.Move(r2_0.TargetSpr, r3_20, r4_20)
  end
  save.DataPush("targetselect", {
    id = r0_20.uid,
    x = r1_20,
    y = r2_20,
  })
end
local function r39_0(r0_35, r1_35, r2_35)
  -- line: [866, 875] id: 35
  local r3_35 = r1_35.range[r0_35.level][1]
  local r4_35 = r1_35.range[r0_35.level][2] * r2_35
  r0_35.range = {
    r3_35 * r3_35,
    r4_35 * r4_35
  }
  r0_35.range_circle = r4_35 * 2
  if r0_35.type == r2_0.CharDef.CharId.Sarah then
    r0_35.shooting = false
    r0_35.func.range(nil, r0_35, -1)
  end
end
return {
  AllClear = function()
    -- line: [225, 227] id: 10
    r5_0.all_clear()
  end,
  Init = function()
    -- line: [229, 370] id: 11
    preload.Load({
      "summon_ok_jp",
      "summon_cancel_jp"
    }, "data/game/upgrade")
    preload.Load({
      "summon_ok_en",
      "summon_cancel_en"
    }, "data/game/upgrade")
    preload.Load({
      "supersummon_ok_jp",
      "supersummon_ok_cry_jp"
    }, "data/game/upgrade")
    preload.Load({
      "supersummon_ok_en",
      "supersummon_ok_cry_en"
    }, "data/game/upgrade")
    preload.Load({
      "guide_mark_0_0"
    }, "data/game/guide")
    r2_0.CircleTransition = nil
    r2_0.CircleUpgradeTransition = nil
    r2_0.CircleSpr = nil
    r2_0.CircleUpgradeSpr = nil
    r2_0.SummonTransition = nil
    r2_0.SummonSpr = nil
    r2_0.SummonPos = nil
    r2_0.SummonGroupSpr = nil
    r2_0.SummonCrystalGroupSpr = nil
    r2_0.SummonChar = nil
    r2_0.MyChar = nil
    r2_0.TargetSpr = nil
    r2_0.SelectTarget = nil
    r2_0.SummonStatus = nil
    r2_0.SummonCrystalStatus = nil
    r2_0.BadMarkSprite = nil
    r2_0.BadMarkTransition = nil
    r2_0.SummonConfirm = nil
    r2_0.ReleaseConfirm = nil
    r2_0.UpgradeBtn = nil
    r2_0.UpgradeBtnNum = nil
    r2_0.ReleaseBtnNum = nil
    r2_0.UpgradeMonitoring = nil
    r2_0.SummonMonitoring = nil
    r2_0.AttackSpeed = 0
    r2_0.RangePower = 0
    r2_0.Level4Lock = nil
    r2_0.LunaOnlyOne = false
    r2_0.AmberOnlyOne = false
    r2_0.FlowerCount = 0
    r2_0.GuideSprite = {}
    r2_0.GuideCircle = nil
    r2_0.CharaUID = 1
    r2_0.SnipeTargetSpr = nil
    r2_0.SnipeSelectTarget = nil
    r2_0.KalaOnlyOne = false
    r2_0.YuikoOnlyOne = false
    r2_0.SummonPlateGroup = nil
    r2_0.SummonSelectPlateSpr = nil
    r2_0.SummonSelectCrystalSpr = nil
    r2_0.SummonSelectMpSpr = nil
    r2_0.EvoSummonSelectCrystalSpr = nil
    r2_0.EvoSummonSelectMpSpr = nil
    r2_0.ShowUnitPanelFlag = false
    r2_0.IsUseCrystal = false
    r2_0.SnipemodeWindowImg = nil
    r2_0.Objects.UnitListParts = r2_0.Classes.UnitListParts.new()
    r2_0.SummonChar = {}
    for r3_11 = 1, r2_0.CharDef.CharTotal, 1 do
      if r3_11 == 16 then
        table.insert(r2_0.SummonChar, "not use")
      else
        table.insert(r2_0.SummonChar, require(string.format("char.char%02d", r3_11)))
      end
    end
    local r0_11 = require("char.guardian")
    r2_0.SummonChar[100] = r0_11
    r2_0.SummonChar.guardian = r0_11
    r2_0.MyChar = {}
    r2_0.CircleSpr = display.newImage(_G.SummonRoot, "data/game/circle_range.png", true)
    r2_0.CircleSpr:setReferencePoint(display.CenterReferencePoint)
    r2_0.CircleSpr.isVisible = false
    r2_0.CircleUpgradeSpr = display.newImage(_G.SummonRoot, "data/game/circle_upgrade_range.png", true)
    r2_0.CircleUpgradeSpr:setReferencePoint(display.CenterReferencePoint)
    r2_0.CircleUpgradeSpr.isVisible = false
    r2_0.SummonSpr = display.newImage(_G.MyRoot, "data/game/circle_summon.png", true)
    r2_0.SummonSpr:setReferencePoint(display.CenterReferencePoint)
    r2_0.SummonSpr.isVisible = false
    r2_0.SummonStatus, r2_0.Level4Lock = db.LoadSummonData(_G.UserID)
    if #r2_0.SummonStatus < 1 then
      db.InitSummonData(_G.UserID)
      r2_0.SummonStatus, r2_0.Level4Lock = db.LoadSummonData(_G.UserID)
    end
    r2_0.SummonCrystalStatus, r2_0.Level4Lock = db.LoadSummonData(_G.UserID)
    if #r2_0.SummonCrystalStatus < 1 then
      db.InitSummonData(_G.UserID)
      r2_0.SummonCrystalStatus, r2_0.Level4Lock = db.LoadSummonData(_G.UserID)
    end
    r2_0.Level4Lock[11] = false
    r2_0.Level4Lock[12] = false
    r2_0.Level4Lock[r2_0.CharDef.CharId.LiliLala] = false
    r2_0.Level4Lock[r2_0.CharDef.CharId.Amber] = false
    r2_0.Level4Lock[r2_0.CharDef.CharId.Nina] = false
    r2_0.Level4Lock[r2_0.CharDef.CharId.DaisyA] = false
    r2_0.Level4Lock[r2_0.CharDef.CharId.Jill] = false
    r2_0.Level4Lock[r2_0.CharDef.CharId.Yuiko] = false
    r5_0.make_summon_group()
    r2_0.BadMarkSprite = display.newImage(_G.BadRoot, "data/game/badmark.png", true)
    r2_0.BadMarkSprite.isVisible = false
    r6_0.make_upgrade_btn()
    for r4_11, r5_11 in pairs(r2_0.SummonChar) do
      if r4_11 ~= 16 then
        r2_0.SummonChar[r4_11].custom_make_upgrade_btn()
      end
    end
    if _G.GameMode == _G.GameModeEvo then
      r2_0.EvoChar.Init({
        myChar = r2_0.MyChar,
      })
    end
    Runtime:addEventListener("touch", r10_0)
  end,
  Cleanup = function()
    -- line: [376, 472] id: 13
    Runtime:removeEventListener("touch", r10_0)
    r17_0(true)
    if r2_0.TargetSpr then
      anime.Remove(r2_0.TargetSpr)
      r2_0.TargetSpr = nil
    end
    if r2_0.SnipeTargetSpr then
      anime.Remove(r2_0.SnipeTargetSpr)
      r2_0.SnipeTargetSpr = nil
    end
    if _G.GameMode == _G.GameModeEvo then
      r2_0.EvoChar.Clean()
    end
    if r2_0.UpgradeBtn ~= nil then
      for r3_13, r4_13 in pairs(r2_0.UpgradeBtn) do
        if r4_13 ~= nil then
          display.remove(r4_13)
        end
      end
      for r3_13 = 1, #r2_0.UpgradeBtn, 1 do
        table.remove(r2_0.UpgradeBtn)
      end
      r2_0.UpgradeBtn = nil
    end
    if r2_0.MyChar then
      for r3_13, r4_13 in pairs(r2_0.MyChar) do
        r4_0.kill_char(r4_13, true)
      end
      r2_0.MyChar = nil
    end
    if r2_0.SummonChar then
      for r3_13, r4_13 in pairs(r2_0.SummonChar) do
        if r3_13 ~= 16 then
          r4_13.Cleanup()
        end
      end
    end
    if r2_0.GuideSprite then
      for r3_13, r4_13 in pairs(r2_0.GuideSprite) do
        if r4_13.anm then
          anime.Remove(r4_13.anm)
          r4_13.anm = nil
        end
      end
    end
    if r2_0.GuideCircle and r2_0.GuideCircle.tween then
      transit.Delete(r2_0.GuideCircle.tween)
      r2_0.GuideCircle.tween = nil
    end
    local r0_13 = {}
    local r1_13 = nil
    for r5_13 = 1, r2_0.MAX_USER, 1 do
      table.insert(r0_13, string.format("char.char%02d", r5_13))
    end
    table.insert(r0_13, "char.guardian")
    table.insert(r0_13, "efx.fx_summons_base")
    table.insert(r0_13, "efx.fx_summon_overlay")
    table.insert(r0_13, "efx.target_mark")
    for r5_13, r6_13 in pairs(r0_13) do
      if package.loaded[r6_13] then
        package.loaded[r6_13] = nil
      end
    end
    r2_0.Objects.UnitListParts.Clean()
    r2_0.CircleTransition = nil
    r2_0.CircleUpgradeTransition = nil
    r2_0.CircleSpr = nil
    r2_0.CircleUpgradeSpr = nil
    r2_0.SummonTransition = nil
    r2_0.SummonSpr = nil
    r2_0.SummonPos = nil
    r2_0.SummonGroupSpr = nil
    r2_0.SummonCrystalGroupSpr = nil
    r2_0.SummonChar = nil
  end,
  InitFirstSummon = function()
    -- line: [475, 477] id: 14
    r4_0.get_current_char({
      type = r2_0.CharDef.CharId.LiliLala,
    }).InitFirstSummon()
  end,
  FirstSummon = r20_0,
  Pause = function(r0_16)
    -- line: [489, 517] id: 16
    local r2_16 = {}
    for r6_16, r7_16 in pairs(r2_0.MyChar) do
      if r7_16.anime and r7_16.anime.ev then
        table.insert(r0_16, {
          ev = r7_16.anime.ev,
          mode = events.Disable(r7_16.anime.ev, true),
        })
      end
      if r7_16.func.pause then
        table.insert(r2_16, {
          r7_16,
          r7_16.func.pause(r7_16, true)
        })
      end
      if _G.GameMode == _G.GameModeEvo then
        local r8_16 = r2_0.EvoChar.GetEvoTimerEvent(r7_16)
        if r8_16 ~= nil then
          table.insert(r0_16, {
            ev = r8_16,
            mode = events.Disable(r8_16, true),
          })
        end
      end
    end
    for r6_16, r7_16 in pairs(_G.ShotEvent) do
      table.insert(r0_16, {
        ev = r7_16,
        mode = events.Disable(r7_16, true),
      })
    end
    return r2_16
  end,
  PauseChars = function(r0_17)
    -- line: [520, 540] id: 17
    local r1_17 = not r0_17
    for r5_17, r6_17 in pairs(r2_0.MyChar) do
      if r6_17.anime and r6_17.anime.ev then
        events.Disable(r6_17.anime.ev, r1_17)
      end
      if _G.GameMode == _G.GameModeEvo then
        local r7_17 = r2_0.EvoChar.GetEvoTimerEvent(r6_17)
        if r7_17 ~= nil then
          events.Disable(r7_17, r1_17)
        end
      end
    end
    for r5_17, r6_17 in pairs(_G.ShotEvent) do
      events.Disable(r6_17, r1_17)
    end
  end,
  CheckNextTarget = function()
    -- line: [543, 550] id: 18
    if r2_0.SelectTarget == nil then
      return 
    end
    save.DataPush("targetselect", {
      id = r2_0.SelectTarget.uid,
      x = r2_0.SelectTarget.sx,
      y = r2_0.SelectTarget.sy,
    })
  end,
  ClearNextTarget = function(r0_19, r1_19)
    -- line: [553, 565] id: 19
    if r1_19 == nil then
      r1_19 = false
    end
    if r2_0.SelectTarget == nil then
      return 
    end
    if r2_0.SelectTarget and r2_0.SelectTarget ~= r0_19 then
      return 
    end
    r2_0.SelectTarget = nil
    for r5_19, r6_19 in pairs(r2_0.MyChar) do
      if r1_19 and r6_19.type == 10 then
        r6_19.target_cancel = false
      else
        r6_19.target_cancel = true
      end
    end
  end,
  SetNextTarget = r25_0,
  SetSnipeTarget = function(r0_21)
    -- line: [618, 675] id: 21
    if game ~= nil and game.GetPauseType() ~= r3_0.PauseTypeNone and game.GetPauseType() ~= r3_0.PauseTypeStopClockMenu and game.GetPauseType() ~= r3_0.PauseTypeStopClock then
      return 
    end
    if r2_0.SnipeSelectTarget then
      r0_21.snipe_target_spr = nil
      r2_0.SnipeSelectTarget = nil
      local r1_21 = r2_0.SnipeChargeGauge.spr
      if r1_21 then
        display.remove(r1_21)
      end
      r2_0.SnipeChargeGauge.spr = nil
      r2_0.SnipeChargeGauge.rtImg.isVisible = false
      if r2_0.SnipeTargetSpr then
        anime.Remove(r2_0.SnipeTargetSpr)
        r2_0.SnipeTargetSpr = nil
      end
      r2_0.SummonChar[r2_0.CharDef.CharId.Amber].Reload(r2_0.AmberStruct, 1)
    end
    local r3_21 = r0_21.sx + r0_21.targetpos[1]
    local r4_21 = r0_21.sy + r0_21.targetpos[2] + 100
    r2_0.SnipeTargetSpr = anime.Register(r2_0.snipe_target_efx.GetData(), r3_21, r4_21, "data/game")
    local r5_21 = anime.GetSprite(r2_0.SnipeTargetSpr)
    if r0_21.type == -1 then
      r5_21.y = r5_21.y - 15
    else
      r5_21.y = r5_21.y + 10
    end
    _G.TargetRoot:insert(r5_21)
    anime.Show(r2_0.SnipeTargetSpr, true)
    anime.Loop(r2_0.SnipeTargetSpr, true)
    sound.play_sound(r2_0.AmberStruct, 6)
    r2_0.SummonChar[r2_0.CharDef.CharId.Amber].Reload(r2_0.AmberStruct, 2)
    r0_21.snipe_target_spr = r2_0.SnipeTargetSpr
    r2_0.SnipeSelectTarget = r0_21
    r2_0.SnipeSelectTarget.frame_count = 0
    anime.Show(r2_0.SnipeTargetSpr, true)
    local r6_21 = anime.GetPos(r2_0.SnipeTargetSpr)
    if r6_21[1] ~= r3_21 or r6_21[2] ~= r4_21 then
      anime.Move(r2_0.SnipeTargetSpr, r3_21, r4_21)
    end
  end,
  AllPause = function(r0_22, r1_22)
    -- line: [677, 692] id: 22
    if r0_22 then
      local r2_22 = {}
      for r6_22, r7_22 in pairs(r2_0.MyChar) do
        if r7_22.func.pause then
          table.insert(r2_22, {
            r7_22,
            r7_22.func.pause(r7_22, true)
          })
        end
      end
      return r2_22
    else
      for r5_22, r6_22 in pairs(r1_22) do
        r6_22[1].func.pause(r6_22[1], false, r6_22[2])
      end
      return nil
    end
  end,
  MPMonitoring = function()
    -- line: [694, 713] id: 23
    if r2_0.UpgradeMonitoring then
      local r1_23 = r2_0.UpgradeMonitoring.struct
      if r2_0.UpgradeMonitoring.mp <= _G.MP then
        r4_0.view_upgrade_btn(r1_23)
      end
    end
    if r2_0.SummonMonitoring and r2_0.SummonPlateGroup == nil then
      local r1_23 = r2_0.SummonMonitoring.y
      if r2_0.SummonMonitoring.mp <= _G.MP then
        r4_0.view_summon_group(false)
        r5_0.set_summon_status(r1_23)
        r4_0.view_summon_group(true, r1_23)
      end
    end
  end,
  MakeResumeData = function()
    -- line: [715, 760] id: 24
    local r1_24 = {}
    for r5_24, r6_24 in pairs(r2_0.MyChar) do
      local r0_24 = r6_24.type
      if r0_24 == 100 then
        local r7_24, r8_24 = r6_24.func.get_hp(r6_24)
        table.insert(r1_24, {
          id = r6_24.type,
          xy = r6_24.map_xy,
          level = r6_24.level,
          hp = r7_24,
          maxhp = r8_24,
        })
      elseif r0_24 == 15 then
        local r7_24, r8_24 = r6_24.func.get_hp(r6_24)
        table.insert(r1_24, {
          id = r6_24.type,
          xy = r6_24.map_xy,
          level = r6_24.level,
          hp = r7_24,
          ms = r8_24,
        })
      elseif r0_24 == 13 then
        table.insert(r1_24, {
          id = r6_24.type,
          xy = r6_24.map_xy,
          level = r6_24.level,
          params = r6_24.func.get_resume_data(r6_24),
        })
      else
        table.insert(r1_24, {
          id = r6_24.type,
          xy = r6_24.map_xy,
          level = r6_24.level,
        })
      end
    end
    table.sort(r1_24, function(r0_25, r1_25)
      -- line: [757, 757] id: 25
      return r0_25.id < r1_25.id
    end)
    return r1_24
  end,
  ResumePop = function(r0_26, r1_26)
    -- line: [763, 781] id: 26
    local r2_26 = r0_26.id
    local r3_26 = r0_26.x
    local r4_26 = r0_26.y
    local r5_26 = r1_26 ~= 0
    if r2_26 == 100 then
      r20_0({
        r2_26,
        r3_26,
        r4_26,
        1,
        0,
        0
      })
    elseif r2_26 == r2_0.CharDef.CharId.BlueMagicalFlower then
      r20_0({
        r2_26,
        r3_26,
        r4_26,
        1,
        0,
        0
      })
    elseif r2_26 == r2_0.CharDef.CharId.Luna then
      r20_0({
        r2_26,
        r3_26,
        r4_26,
        1,
        nil
      }, r5_26)
    else
      r20_0({
        r2_26,
        r3_26,
        r4_26,
        1
      }, r5_26)
    end
    game.AddMp(-_G.UserStatus[r2_26].cost[1])
    save.DataPush("pop", {
      id = r2_26,
      x = r3_26,
      y = r4_26,
    })
  end,
  ResumeUpgrade = function(r0_27)
    -- line: [784, 798] id: 27
    local r1_27 = r0_27.xy[1]
    local r2_27 = r0_27.xy[2]
    local r3_27 = r12_0(r1_27, r2_27)
    if r3_27 == nil then
      return 
    end
    r3_27.upgrade_mp = r0_27.upgrade_mp
    local r4_27 = nil
    r4_27 = {
      x = r1_27,
      y = r2_27,
      struct = r3_27,
    }
    r6_0.upgrade_ok(r4_27)
  end,
  ResumeRelease = function(r0_28)
    -- line: [800, 814] id: 28
    local r1_28 = r0_28.xy[1]
    local r2_28 = r0_28.xy[2]
    local r3_28 = r12_0(r1_28, r2_28)
    if r3_28 == nil then
      return 
    end
    r3_28.release_mp = r0_28.release_mp
    local r4_28 = nil
    r4_28 = {
      x = r1_28,
      y = r2_28,
      struct = r3_28,
    }
    r6_0.release_ok(r4_28)
  end,
  ResumeTargetSet = function(r0_29)
    -- line: [816, 830] id: 29
    local r1_29 = r0_29.id
    local r2_29 = r0_29.x
    local r3_29 = r0_29.y
    local r4_29 = r13_0(r1_29)
    if r4_29 == nil then
      r4_29 = r14_0(r2_29, r3_29)
      if r4_29 == nil then
        DebugPrint(string.format("target set: %d(%d, %d): not found resume", r1_29, r2_29, r3_29))
        return 
      end
    end
    r25_0(r4_29)
  end,
  ResumeTargetCancel = function(r0_30)
    -- line: [832, 844] id: 30
    local r1_30 = r0_30.id
    local r2_30 = r13_0(r1_30)
    if r2_30 == nil then
      r2_30 = r14_0(r0_30.x, r0_30.y)
      if r2_30 == nil then
        DebugPrint(string.format("target cancel: %d: not found resume", r1_30))
        return 
      end
    end
    r25_0(r2_30)
  end,
  SummonPurchase = function(r0_31)
    -- line: [847, 849] id: 31
    r2_0.SummonStatus[r0_31] = 1
  end,
  GetSummonPurchase = function(r0_32)
    -- line: [852, 854] id: 32
    return r2_0.SummonStatus[r0_32]
  end,
  SpeedPowerup = function(r0_33)
    -- line: [857, 859] id: 33
    r2_0.AttackSpeed = r0_33
  end,
  GetSpeedPowerup = function()
    -- line: [861, 863] id: 34
    return r2_0.AttackSpeed
  end,
  SetRangePower = r39_0,
  RangePowerup = function(r0_36)
    -- line: [878, 891] id: 36
    r2_0.RangePower = r0_36
    local r1_36 = (r0_36 + 100) / 100
    local r2_36 = nil
    local r3_36 = nil
    local r4_36 = nil
    local r5_36 = nil
    local r6_36 = nil
    for r10_36, r11_36 in pairs(r2_0.MyChar) do
      r2_36 = r11_36.type
      if r2_36 ~= 100 then
        r5_36 = r11_36.level
        r6_36 = _G.UserStatus[r2_36]
        r39_0(r11_36, r6_36, r1_36)
      end
    end
  end,
  GetRangePowerup = function()
    -- line: [893, 895] id: 37
    return r2_0.RangePower
  end,
  ClearAllCircle = r17_0,
  InsertSortData = function(r0_38)
    -- line: [898, 902] id: 38
    for r4_38, r5_38 in pairs(r2_0.MyChar) do
      table.insert(r0_38, {
        z = r5_38.sort_z,
        spr = r5_38.sort_sprite,
      })
    end
  end,
  ClearVoice = function()
    -- line: [905, 913] id: 39
    if not _G.GameData.voice then
      return 
    end
    local r0_39 = #r2_0.MyChar
    if r0_39 and 1 <= r0_39 then
      local r2_39 = r2_0.MyChar[math.random(1, r0_39)]
      r2_39.func.sound(r2_39, 5)
    end
  end,
  ClearSummonGroup = function()
    -- line: [915, 917] id: 40
    r5_0.clear_summon_group()
  end,
  CalcUnitScore = function()
    -- line: [920, 943] id: 41
    local r4_41 = 0
    for r8_41, r9_41 in pairs(r2_0.MyChar) do
      local r0_41 = r9_41.type
      local r3_41 = _G.UserStatus[r0_41]
      if r0_41 == r2_0.CharDef.CharId.LiliLala or r0_41 == r2_0.CharDef.CharId.BlueMagicalFlower then
        r3_41 = nil
      end
      if r3_41 then
        local r2_41 = r3_41.sell[r9_41.level]
        if r2_41 == nil then
          r2_41 = 0
        end
        r4_41 = r4_41 + r2_41
      end
    end
    return r4_41 * 100
  end,
  ClearTwinsGuide = function()
    -- line: [946, 956] id: 42
    r5_0.clear_guide()
    r5_0.clear_guide_circle()
    r5_0.hide_summon_circle()
    r4_0.hide_upgrade_circle(true, true)
    r4_0.view_summon_group(false)
  end,
  SummonTwins = function(r0_43)
    -- line: [959, 970] id: 43
    r4_0.get_current_char({
      type = 14,
    }).custom_summon(14, r0_43.lx, r0_43.ly, {
      fast_summon = false,
      param = {
        0,
        r0_43.rx,
        r0_43.ry
      },
    })
  end,
  UpdateVoicePath = function()
    -- line: [973, 984] id: 44
    local r2_44 = _G.GameData.language
    for r6_44, r7_44 in pairs(r2_0.MyChar) do
      local r1_44 = r7_44.type
      if r1_44 < r2_0.CharDef.CharId.Luna or r1_44 == r2_0.CharDef.CharId.LiliLala or r2_0.CharDef.CharId.Kala <= r1_44 then
        r7_44.sound_path = sound.GetCharVoicePath(r1_44, r2_44)
      end
    end
  end,
  SnipeModeOff = function()
    -- line: [987, 989] id: 45
    r4_0.snipe_mode_off()
  end,
  SetOrder = function(r0_46)
    -- line: [992, 1006] id: 46
    local r1_46 = display.newGroup()
    r1_46:insert(r0_46.spr)
    r0_46.sort_sprite = r1_46
    r0_46.sort_z = r0_46.x + r0_46.y * 1000
    r0_46.star_rt = r1_46
    if r0_46.star then
      r4_0.set_star(r0_46)
    end
    _G.MyRoot:insert(r0_46.sort_sprite)
  end,
  IsShowUnitPanel = function()
    -- line: [1009, 1011] id: 47
    return r2_0.ShowUnitPanelFlag
  end,
  IsEvoChar = function()
    -- line: [1014, 1032] id: 48
    local r2_48 = false
    for r6_48, r7_48 in pairs(r2_0.MyChar) do
      local r8_48 = nil
      if r7_48.struct ~= nil then
        r8_48 = r7_48.struct
      else
        r8_48 = r7_48
      end
      if 0 < r2_0.EvoChar.GetEvoLevel(r8_48) and r2_0.EvoChar.IsStartEvo(r8_48) == false then
        r2_48 = true
        break
      end
    end
    return r2_48
  end,
  SetSystemPause = function(r0_49)
    -- line: [1037, 1039] id: 49
    r2_0.EvoChar.SetSystemPause(r0_49)
  end,
  SetUseOrbCharSelectModeCancelRect = function(r0_50)
    -- line: [1044, 1046] id: 50
    r2_0.EvoChar.SetUseOrbCharSelectModeCancelRect(r0_50)
  end,
  GetUseOrbMode = r8_0,
  SetUseOrbMode = function(r0_51, r1_51)
    -- line: [1051, 1053] id: 51
    r2_0.EvoChar.SetUseOrbMode(r0_51, r1_51)
  end,
  IsPurchaseChar = function(r0_52)
    -- line: [1058, 1060] id: 52
    return r7_0(r0_52)
  end,
  SetTapFieldTutrial = function()
    -- line: [1065, 1082] id: 53
    if r2_0.TutorialManager.SelectSummonFilger ~= nil or r2_0.TutorialManager.SummonFinger ~= nil or _G.MP <= 160 then
      return 
    end
    local r0_53 = nil
    local r1_53 = nil
    local r2_53 = 270
    for r6_53, r7_53 in ipairs(r2_0.TutorialManager.TutorialFieldPattern) do
      if _G.MapLocation[r7_53.y][r7_53.x] == 0 then
        r0_53 = (r7_53.x - 1) * 80
        r1_53 = (r7_53.y - 1) * 80
        r2_0.TutorialManager.TapFieldTutorial(true, _G.RouteRoot, r0_53, r1_53)
        break
      end
    end
  end,
  DeleteTapFieldTutrial = function()
    -- line: [1087, 1089] id: 54
    r2_0.TutorialManager.TapFieldTutorial(false)
  end,
  SetUpgradeCharTutrial = function()
    -- line: [1094, 1098] id: 55
    r2_0.TutorialManager.UpgradeCharTutorial(true, _G.RouteRoot, 240, 320)
  end,
  DeleteUpgradeCharTutrial = function()
    -- line: [1103, 1105] id: 56
    r2_0.TutorialManager.UpgradeCharTutorial(false)
  end,
  PlaySound = function(r0_57, r1_57)
    -- line: [1109, 1111] id: 57
    sound.play_sound(r0_57, r1_57)
  end,
  SetStar = function(r0_58)
    -- line: [1114, 1116] id: 58
    r4_0.set_star(r0_58)
  end,
  SetYuikoEvoLevel = function(r0_59)
    -- line: [1118, 1120] id: 59
    r2_0.SummonChar[r2_0.CharDef.CharId.Yuiko].setYuikoEvoLevel(r0_59)
  end,
}
