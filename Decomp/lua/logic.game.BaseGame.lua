-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("logic.game.GameStatus")
local r1_0 = require("logic.char.CharStatus")
local r2_0 = require("logic.cwave")
local r3_0 = require("logic.gamecenter")
local r4_0 = require("ui.ticker")
local r5_0 = require("logic.pay_item_data")
local r6_0 = require("resource.char_define")
local r7_0 = require("game.pause_manager")
local r8_0 = require("tool.tutorial_manager")
local r9_0 = require("evo.treasure_box_manager")
local r10_0 = {
  {
    128,
    128,
    128
  },
  {
    128,
    128,
    128
  },
  {
    128,
    128,
    128
  },
  {
    166,
    159,
    149
  },
  {
    128,
    128,
    128
  },
  {
    128,
    128,
    128
  },
  {
    128,
    128,
    128
  },
  {
    128,
    128,
    128
  },
  {
    128,
    128,
    128
  },
  {
    128,
    128,
    128
  }
}
local function r11_0()
  -- line: [31, 49] id: 1
  if r0_0.mpObject == nil then
    return 
  end
  if r7_0.GetPauseType() ~= r0_0.PauseTypeNone and r7_0.GetPauseType() ~= r0_0.PauseTypeFirstPause then
    r0_0.mpScale.Stop()
    r0_0.mpObject:setFillColor(169, 169, 169)
  elseif _G.MP < 160 then
    r0_0.mpScale.Stop()
    r0_0.mpObject:setFillColor(169, 169, 169)
  else
    r0_0.mpScale.Play(r0_0.mpObject)
    r0_0.mpObject:setFillColor(255, 255, 255)
  end
end
local function r13_0()
  -- line: [90, 104] id: 4
  if r0_0.GridParts then
    for r3_4, r4_4 in pairs(r0_0.GridParts) do
      if r4_4.spr then
        display.remove(r4_4.spr)
        r4_4.spr = nil
      end
      if r4_4.ev then
        transition.cancel(r4_4.ev)
        r4_4.ev = nil
      end
    end
  end
  r0_0.GridParts = {}
end
local function r14_0()
  -- line: [107, 112] id: 5
  if r0_0.SpeedButtonEffect ~= nil then
    anime.Remove(r0_0.SpeedButtonEffect)
    r0_0.SpeedButtonEffect = nil
  end
end
local function r15_0()
  -- line: [117, 123] id: 6
  if r0_0.SpeedEffectId then
    timer.cancel(r0_0.SpeedEffectId)
    r14_0()
    r0_0.SpeedEffectId = nil
  end
end
local function r16_0()
  -- line: [126, 229] id: 7
  OrbManager.SaveUsedOrbTime()
  r0_0.IsLoadPanelFlag = false
  r13_0()
  r7_0.Clean()
  if r0_0.GoalSprite then
    for r3_7, r4_7 in pairs(r0_0.GoalSprite) do
      anime.Remove(r4_7)
    end
    r0_0.GoalSprite = nil
  end
  if r0_0.SideBar then
    if r0_0.SideBar.ev then
      events.Delete(r0_0.SideBar.ev)
      r0_0.SideBar.ev = nil
    end
    if r0_0.SideBar.tween then
      transition.cancel(r0_0.SideBar.tween)
      r0_0.SideBar.tween = nil
    end
    r0_0.SideBar = nil
  end
  if r0_0.GuideBlink then
    local r0_7 = r0_0.GuideBlink.ev
    if r0_7 then
      events.Delete(r0_7)
    end
    r0_0.GuideBlink = nil
  end
  if r0_0.EnemyRegister then
    for r3_7, r4_7 in pairs(r0_0.EnemyRegister) do
      if r4_7.ev then
        events.Delete(r4_7.ev)
      end
      if r4_7.pev then
        events.Delete(r4_7.pev)
        r4_7.pev = nil
      end
    end
    r0_0.EnemyRegister = nil
  end
  if r0_0.PowerupMark and r0_0.PowerupMark.tween then
    transit.Delete(r0_0.PowerupMark.tween)
    r0_0.PowerupMark.tween = nil
  end
  r0_0.PowerupMark = nil
  if _G.CrashObject then
    for r3_7, r4_7 in pairs(_G.CrashObject) do
      if r4_7.aura then
        anime.Remove(r4_7.aura)
        r4_7.aura = nil
      end
    end
    _G.CrashObject = nil
  end
  r15_0()
  r2_0.Cleanup()
  save.Cleanup()
  sound.CleanupVoice()
  sound.GameSECleanup()
  dialog.Cleanup()
  enemy.Cleanup()
  char.Cleanup()
  anime.Cleanup()
  bgm.Stop()
  events.DeleteNamespace("game")
  if r0_0.GamecenterQueue then
    for r3_7, r4_7 in pairs(r0_0.GamecenterQueue) do
      if r4_7 then
        r3_0.UnlockAchievements(r3_7, r4_7)
      end
    end
    r0_0.GamecenterQueue = nil
  end
  for r3_7 = 1, 26, 1 do
    local r4_7 = string.format("enemy.enemy%02d", r3_7)
    if package.loaded[r4_7] then
      if package.loaded[r4_7].Cleanup then
        package.loaded[r4_7].Cleanup()
      end
      package.loaded[r4_7] = nil
    end
  end
  preload.Cleanup()
  if r0_0.PossessingCrystal then
    r0_0.PossessingCrystal.cleanup()
  end
  collectgarbage("collect")
end
local r17_0 = nil
function r17_0(r0_8)
  -- line: [232, 243] id: 8
  local r1_8 = r0_8.struct
  local r2_8 = r0_8.alpha
  if r2_8 < 1 then
    r2_8 = 1 or 0.5
  else
    goto label_7	-- block#2 is visited secondly
  end
  transition.cancel(r1_8.ev)
  r1_8.ev = transition.to(r1_8.spr, {
    time = 1000,
    alpha = r2_8,
    transition = easing.inExpo,
    onComplete = r17_0,
  })
end
local function r22_0(r0_15, r1_15, r2_15)
  -- line: [394, 429] id: 15
  if r0_15 == nil then
    return 
  end
  if r2_15 == nil then
    r2_15 = true
  end
  local r3_15 = nil
  local r4_15 = r0_15.numChildren
  if r4_15 > 1 then
    if r1_15 ~= nil then
      local r5_15 = math.floor(r1_15)
      for r9_15 = 1, r4_15, 1 do
        local r10_15 = r0_15[r9_15]
        if r10_15 ~= nil then
          local r11_15 = r5_15 % 10
          r5_15 = math.floor(r5_15 * 0.1)
          r10_15:setFrame(r11_15 + 1)
          if r2_15 == false then
            local r12_15 = nil	-- notice: implicit variable refs by block#[14]
            if 0 >= r11_15 and 0 >= r5_15 then
              r12_15 = r9_15 == 1
            else
              goto label_41	-- block#13 is visited secondly
            end
            r10_15.isVisible = r12_15
          end
        end
      end
    end
  else
    for r8_15 = 1, r4_15, 1 do
      local r9_15 = r0_15[r8_15]
      if r9_15 ~= nil then
        r9_15:setFrame(11)
      end
    end
  end
end
local function r23_0(r0_16, r1_16, r2_16)
  -- line: [432, 438] id: 16
  if r1_16 ~= nil then
    r22_0(r0_16, r1_16, r2_16)
  else
    r22_0(r0_16, 0, r2_16)
  end
end
local function r27_0(r0_20)
  -- line: [514, 519] id: 20
  r0_0.SpecialAchievementFlag = r0_20
  if r0_0.MedalObject.MedalDisplay ~= nil then
    r0_0.MedalObject.MedalDisplay.EnableMedal(r0_0.MedalClass.TestMedalDisplayManager.MedalTestIndex.Sp, false)
  end
end
local function r28_0(r0_21)
  -- line: [522, 524] id: 21
  r0_0.ScoreType = r0_21
end
local function r35_0(r0_32)
  -- line: [622, 626] id: 32
  _G.HP = r0_32
  r0_0.MedalObject.MedalFuncs.UpdateHpxx(_G.HP)
end
return {
  CheckTotalAchievement = function(r0_2, r1_2, r2_2)
    -- line: [52, 88] id: 2
    if r2_2 == nil then
      r2_2 = false
    end
    assert(r0_2, debug.traceback())
    assert(r1_2 ~= nil, debug.traceback())
    if r0_0.GamecenterQueue == nil then
      r0_0.GamecenterQueue = {}
    end
    local r3_2 = _G.UserID
    local r4_2 = _G.UserToken
    local r5_2 = false
    if r1_2 >= 100 and db.SetGameCenterAchievement(r3_2, r0_2) then
      r3_0.UnlockAchievements(r0_2, 100)
      r0_0.GamecenterQueue[r0_2] = nil
      r5_2 = true
    elseif not r2_2 then
      r0_0.GamecenterQueue[r0_2] = r1_2
    end
    if r5_2 then
      server.UnlockNormalAchievement(r4_2, r0_2, function(r0_3)
        -- line: [78, 86] id: 3
        if r0_3.isError then
          db.SetAchieveQueue("normal", _G.MapSelect, _G.StageSelect, r0_2)
        else
          kpi.Achievement(r0_2)
        end
      end)
    end
  end,
  setMpObject = r11_0,
  Cleanup = r16_0,
  ClearGrid = r13_0,
  remove_speed_effect_id = r15_0,
  MakeGrid = function(r0_9)
    -- line: [246, 309] id: 9
    if r0_9 == nil then
      r0_9 = false
    end
    local r1_9 = nil
    r13_0()
    local r2_9, r3_9, r4_9 = unpack(r10_0[_G.MapSelect])
    local r5_9 = 1
    local r6_9 = nil
    local r7_9 = nil
    local r8_9 = nil
    local r9_9 = nil
    for r13_9 = 2, 8, 1 do
      for r17_9 = 1, 12, 1 do
        if _G.MapLocation[r13_9][r17_9] == 0 then
          r7_9 = (r17_9 - 1) * 80
          r8_9 = (r13_9 - 1) * 80
          if r0_9 then
            r6_9 = display.newRect(_G.GridRoot, r7_9 + 2, r8_9 + 2, 76, 76)
            r6_9:setFillColor(r2_9, r3_9, r4_9, 128)
            r9_9 = transition.to(r6_9, {
              time = 1000,
              alpha = 0.5,
              transition = easing.outExpo,
              onComplete = r17_0,
            })
            if r8_0.IsSummonTutorial and r17_9 == 4 and r13_9 == 5 then
              char.SetTapFieldTutrial()
            end
          else
            r6_9 = nil
            if _G.GameData.grid then
              r6_9 = display.newRect(_G.GridRoot, r7_9 + 2, r8_9 + 2, 76, 76)
              if _G.IsSimulator and _G.IsWindows then
                r6_9:setFillColor(0, 0, 0, 16)
              else
                r6_9.strokeWidth = 1
                r6_9:setFillColor(0, 0, 0, 0)
                r6_9:setStrokeColor(r2_9, r3_9, r4_9, 192)
              end
            end
            r9_9 = nil
          end
          if r6_9 or r9_9 then
            local r18_9 = {
              spr = r6_9,
              ev = r9_9,
            }
            r6_9.struct = r18_9
            table.insert(r0_0.GridParts, r18_9)
          end
        elseif r0_9 and r17_9 == 4 and r13_9 == 5 then
          char.SetUpgradeCharTutrial()
        end
      end
    end
  end,
  PossessingCrystalVisible = function(r0_10)
    -- line: [314, 319] id: 10
    if r0_0.PossessingCrystal ~= nil then
      r0_0.PossessingCrystal.update()
      r0_0.PossessingCrystal.visible(r0_10)
    end
  end,
  is_magic = function()
    -- line: [324, 338] id: 11
    local r0_11 = false
    for r5_11, r6_11 in pairs(db.UsingItemList(_G.UserID)) do
      local r7_11 = r6_11[1]
      if r6_11[1] == r5_0.pay_item_data.SetItem01 then
        r0_11 = true
        break
      end
    end
    return r0_11
  end,
  showMagicRestartConfirm = function(r0_12)
    -- line: [344, 391] id: 12
    dialog.Close()
    local r1_12 = nil
    local r2_12 = nil
    if r0_12 then
      if r0_12.onOk then
        r1_12 = r0_12.onOk
      end
      if r0_12.onCancel then
        r2_12 = r0_12.onCancel
      end
    end
    local r3_12 = nil
    local r4_12 = nil
    local r5_12 = {
      {
        r5_0.pay_item_data.SetItem01,
        1,
        r5_0.getItemData(r5_0.pay_item_data.SetItem01)[2]
      }
    }
    require("ui.coin_module").new({
      useItemList = r5_12,
    }).Open(_G.DialogRoot, db.GetMessage(119), function(r0_13)
      -- line: [370, 385] id: 13
      _G.metrics.crystal_magic_restart(_G.MapSelect, _G.StageSelect, r5_12)
      local r1_13 = {
        magic = true,
      }
      save.DataClear()
      util.ChangeScene({
        prev = r16_0,
        scene = "restart",
        param = r1_13,
      })
      if r1_12 then
        r1_12()
      end
    end, function(r0_14)
      -- line: [386, 390] id: 14
      if r2_12 then
        r2_12()
      end
    end)
  end,
  ViewPanel = function()
    -- line: [440, 492] id: 17
    r23_0(r0_0.WaveNum, _G.WaveNr)
    r23_0(r0_0.WaveMax, _G.WaveMax)
    r23_0(r0_0.ScoreNum, _G.Score)
    r23_0(r0_0.MPNum, _G.MP)
    r23_0(r0_0.HPNum, _G.HP)
    r23_0(r0_0.HPMax, _G.MaxHP)
    r23_0(r0_0.OrbRemain, OrbManager.GetRemainOrb())
    r23_0(r0_0.OrbMax, OrbManager.GetMaxOrb())
    r23_0(r0_0.TreasureboxRich, r9_0.GetRichNum())
    r23_0(r0_0.TreasureboxNormal, r9_0.GetNormalNum())
    if 1 < #r1_0.MyChar or r0_0.isTutorialChange then
      if _G.MP <= 160 and r8_0.IsSummonTutorial then
        r4_0.SetTickerMsg(407)
      end
      r0_0.isTutorialChange = true
    end
    if _G.MP > 160 then
      char.SetTapFieldTutrial()
      char.SetUpgradeCharTutrial()
    end
    r11_0()
    local r1_17 = _G.MaxHP
    local r2_17 = _G.HP
    local r3_17 = 1
    if r2_17 <= r1_17 * 1 / 4 then
      r3_17 = 4
    elseif r2_17 <= r1_17 * 2 / 4 then
      r3_17 = 3
    elseif r2_17 <= r1_17 * 3 / 4 then
      r3_17 = 2
    else
      r3_17 = 1
    end
    if r3_17 ~= r0_0.GoalSpriteIndex then
      local r4_17 = r0_0.GoalSprite[r0_0.GoalSpriteIndex]
      local r5_17 = anime.GetTimer(r4_17)
      anime.Show(r4_17, false)
      anime.Pause(r0_0.GoalSprite[r3_17], false)
      anime.Show(r0_0.GoalSprite[r3_17], true, {
        timer = r5_17,
      })
      r0_0.GoalSpriteIndex = r3_17
    end
  end,
  UpdatePocketCrystal = function(r0_18, r1_18)
    -- line: [495, 500] id: 18
    r0_0.PokectCrystalValue = 0
    if r0_0.PktCrystal then
      r0_0.PktCrystal.update(r0_18, r1_18)
    end
  end,
  make_end_game_resume_data = function()
    -- line: [503, 511] id: 19
    db.SaveResume(_G.UserID, _G.MapSelect, _G.StageSelect, 0, {
      score = 0,
      hp = 0,
      mp = 0,
    })
  end,
  set_score_type = function(r0_22)
    -- line: [527, 537] id: 22
    if r0_22 == nil or r0_22 == r0_0.ScoreTypeDef.Advance then
      return 
    end
    r27_0(false)
    r28_0(r0_22)
  end,
  set_special_achievement_flag = r27_0,
  set_score_type_flag = r28_0,
  change_title_func = function()
    -- line: [540, 558] id: 23
    dialog.Open(_G.DialogRoot, 2, {
      7
    }, {
      function()
        -- line: [541, 549] id: 24
        sound.PlaySE(2)
        dialog.Close()
        save.DataPush("start", {
          flag = 1,
          PopEnemyNum = enemy.PopEnemyNum,
          DropTreasureboxEnemy = enemy.DropTreasureboxEnemy,
        })
        save.DataSave()
        util.ChangeScene({
          prev = r16_0,
          scene = "title",
          efx = "fade",
        })
        return true
      end,
      function(r0_25)
        -- line: [551, 555] id: 25
        sound.PlaySE(2)
        dialog.Close()
        return true
      end
    })
  end,
  back_to_map_func = function()
    -- line: [560, 578] id: 26
    dialog.Open(_G.DialogRoot, 2, {
      7
    }, {
      function()
        -- line: [561, 569] id: 27
        sound.PlaySE(2)
        dialog.Close()
        save.DataPush("start", {
          flag = 1,
          PopEnemyNum = enemy.PopEnemyNum,
          DropTreasureboxEnemy = enemy.DropTreasureboxEnemy,
        })
        save.DataSave()
        util.ChangeScene({
          prev = r16_0,
          scene = "map",
          efx = "fade",
        })
        return true
      end,
      function(r0_28)
        -- line: [571, 575] id: 28
        sound.PlaySE(2)
        dialog.Close()
        return true
      end
    })
  end,
  update_special_medal = function(r0_29, r1_29)
    -- line: [583, 604] id: 29
    if r0_0.SpecialAchievementFlag == false or r0_29 == nil or r1_29 == nil then
      return 
    end
    if char.IsPurchaseChar(r0_29) == true then
      return 
    end
    local r2_29 = r0_0.SpecialAchievement[r0_29]
    if r2_29 == r0_0.SpecialAchievementNoTarget then
      r27_0(false)
    elseif r2_29 == r0_0.SpecialAchievementTargetLevel1_3 and r0_0.SpecialAchievementBorderLevel4 <= r1_29 then
      r27_0(false)
    end
  end,
  buy_crystal_func = function(r0_30)
    -- line: [609, 612] id: 30
    crystal.Open(display.newGroup(), r0_30)
  end,
  get_hp = function()
    -- line: [616, 618] id: 31
    return _G.HP
  end,
  set_hp = r35_0,
  add_hp = function(r0_33)
    -- line: [629, 650] id: 33
    local r1_33 = _G.HP + r0_33
    if r1_33 < 0 then
      r1_33 = 0
    elseif _G.HpMaxLimit <= r1_33 then
      r1_33 = _G.HpMaxLimit
    end
    if r1_33 < 1 and 0 < r0_0.yuikoEvoLevel then
      r1_33 = 1
    end
    r35_0(r1_33)
    if r0_33 < 0 and 0 < r0_0.yuikoEvoLevel then
      r1_0.SummonChar[r6_0.CharId.Yuiko].setStarAnime()
    end
  end,
  get_mp = function(r0_36)
    -- line: [668, 670] id: 36
    return _G.MP
  end,
  set_mp = function(r0_35)
    -- line: [663, 665] id: 35
    _G.MP = r0_35
  end,
  add_mp = function(r0_34)
    -- line: [653, 660] id: 34
    _G.MP = _G.MP + r0_34
    if _G.MP < 0 then
      _G.MP = 0
    elseif _G.MpMaxLimit <= _G.MP then
      _G.MP = _G.MpMaxLimit
    end
  end,
}
