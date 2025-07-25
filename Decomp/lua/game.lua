-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("tool.trial")
local r1_0 = require("logic.cwave")
local r2_0 = require("logic.gamecenter")
local r3_0 = require("tool.crystal")
local r4_0 = require("ui.ticker")
local r5_0 = require("game.pause_manager")
local r6_0 = require("tool.tutorial_manager")
local r7_0 = require("resource.char_define")
local r8_0 = require("logic.pay_item_data")
local r9_0 = require("logic.char.CharStatus")
local r10_0 = require("logic.game.GameStatus")
local r11_0 = require("logic.game.Game")
local r12_0 = require("evo.treasure_box_manager")
local r13_0 = {
  {
    "fx_aura_0_0",
    "fx_aura_0_1",
    "fx_aura_0_10",
    "fx_aura_0_11",
    "fx_aura_0_12",
    "fx_aura_0_13",
    "fx_aura_0_14",
    "fx_aura_0_15",
    "fx_aura_0_16",
    "fx_aura_0_17",
    "fx_aura_0_18",
    "fx_aura_0_19",
    "fx_aura_0_2",
    "fx_aura_0_20",
    "fx_aura_0_21",
    "fx_aura_0_22",
    "fx_aura_0_23",
    "fx_aura_0_24",
    "fx_aura_0_25",
    "fx_aura_0_26",
    "fx_aura_0_27",
    "fx_aura_0_28",
    "fx_aura_0_29",
    "fx_aura_0_3",
    "fx_aura_0_30",
    "fx_aura_0_31",
    "fx_aura_0_32",
    "fx_aura_0_33",
    "fx_aura_0_34",
    "fx_aura_0_35",
    "fx_aura_0_36",
    "fx_aura_0_37",
    "fx_aura_0_38",
    "fx_aura_0_39",
    "fx_aura_0_4",
    "fx_aura_0_40",
    "fx_aura_0_41",
    "fx_aura_0_42",
    "fx_aura_0_43",
    "fx_aura_0_44",
    "fx_aura_0_5",
    "fx_aura_0_6",
    "fx_aura_0_7",
    "fx_aura_0_8",
    "fx_aura_0_9",
    nil,
    nil,
    nil
  }
}
local function r14_0()
  -- line: [46, 53] id: 1
  if r10_0.TicketDisplayGroup == nil then
    return 
  end
  display.remove(r10_0.TicketDisplayGroup)
  r10_0.TicketDisplayGroup = nil
end
local function r15_0()
  -- line: [56, 58] id: 2
  preload.Load(r13_0[1], "data/game/aura")
end
local function r16_0()
  -- line: [60, 84] id: 3
  _G.MapLocation = {}
  _G.EnemyStart = {}
  _G.SkyStart = {}
  _G.EnemyRoute = {}
  _G.CrashObject = {}
  _G.SkyRoute = {}
  _G.EnemyPop = {}
  _G.Score = 0
  _G.SpeedType = 1
  _G.MP = 0
  _G.HP = _G.DefaultHp
  _G.MaxHP = 20
  _G.Enemys = nil
  _G.UserStatus = nil
  _G.EnemyStatus = nil
  _G.EnemyScaleBase = nil
  _G.EnemyScale = 1
  _G.ShotEvent = {}
  _G.ShotSpeed = 32
  _G.CharaParam = {}
  _G.PopEnemyShield = false
  _G.Guardians = {}
  _G.Flowers = {}
end
local function r17_0(r0_4)
  -- line: [88, 90] id: 4
  r10_0.PokectCrystalValue = r0_4
end
local r18_0 = nil
local function r19_0(r0_5)
  -- line: [94, 367] id: 5
  db.SetStartup(1, false)
  Runtime:removeEventListener("enterFrame", r19_0)
  r15_0()
  r16_0()
  r12_0.Init()
  r10_0.EnemyRegister = {}
  r10_0.PauseQueue = {}
  r10_0.GridParts = {}
  r10_0.GameOver = false
  r10_0.GameClear = false
  r10_0.GuideBlink = nil
  r10_0.PurchaseHP = 0
  r10_0.PurchaseMP = 0
  r10_0.ReleaseFlag = false
  r10_0.SpecialAchievement = db.GetSpecialAchievement()
  r10_0.GamecenterQueue = {}
  r10_0.CornetPopupFlag = false
  r10_0.CornetKillFlag = false
  r10_0.isUseBom = false
  r10_0.yuikoEvoLevel = 0
  r11_0.set_score_type_flag(r10_0.ScoreTypeDef.Advance)
  r11_0.set_perfect_flag(true)
  r11_0.set_special_achievement_flag(true)
  db.StatusInit()
  r8_0.Init()
  anime.Init()
  events.SetNamespace("game")
  save.Init()
  char.Init()
  enemy.Init()
  dialog.Init()
  sound.GameSELoad()
  sound.InitVoice()
  r0_0.Init()
  hint.Init()
  char.SetSystemPause(true)
  kpi.Start(_G.MapSelect, _G.StageSelect)
  OrbManager.SetUsedOrbCount(0)
  local r1_5 = nil
  local r2_5 = nil
  local r3_5 = nil
  local r4_5 = false
  if r10_0.ResumeData then
    _G.Score = r10_0.ResumeData.score
    _G.HP = r10_0.ResumeData.hp
    r11_0.set_perfect_flag(r10_0.ResumeData.perfect)
    if r10_0.PerfectFlag == nil then
      r11_0.set_perfect_flag(false)
    end
    r11_0.set_score_type_flag(r10_0.ResumeData.scoretype)
    if r10_0.ScoreType == nil then
      r11_0.set_score_type_flag(r10_0.ScoreTypeDef.Advance)
    end
    local r5_5 = r10_0.ResumeData.purchase_hpmp
    if r5_5 then
      r10_0.PurchaseHP = r5_5[1]
      r10_0.PurchaseMP = r5_5[2]
    else
      r10_0.PurchaseHP = 0
      r10_0.PurchaseMP = 0
    end
    r10_0.ReleaseFlag = r10_0.ResumeData.release_flag
    if r10_0.ReleaseFlag == nil then
      r10_0.ReleaseFlag = false
    end
    r11_0.set_special_achievement_flag(r10_0.ResumeData.special)
    if r10_0.SpecialAchievementFlag == nil then
      r11_0.set_special_achievement_flag(false)
    end
    r1_5 = {}
    for r9_5, r10_5 in pairs(r10_0.ResumeData.crash_object) do
      r1_5[r10_5.index] = {
        x = r10_5.x,
        y = r10_5.y,
        hp = r10_5.hp,
      }
    end
    r2_5 = r10_0.ResumeData.char
    r4_5 = r10_0.ResumeData.powerupmark
    if r10_0.ResumeDataWave > 1 then
      if _G.MapSelect >= 3 then
        _G.EnemyScale = math.pow(_G.EnemyScaleBase, (r10_0.ResumeDataWave - 1) / 1.3)
      else
        _G.EnemyScale = math.pow(_G.EnemyScaleBase, r10_0.ResumeDataWave - 1)
      end
    end
    OrbManager.SetUsedOrbCount(r10_0.ResumeData.use_orb_count)
    r12_0.SetTreasureboxData(r10_0.ResumeData.treasurebox)
    r10_0.MedalObject.MedalManager.DecodeAcquire(r10_0.ResumeData.mdlex_cd)
  end
  r6_0.SetTickerPlate()
  _G.BgRoot:insert(r11_0.loadmap(r1_5, r2_5))
  r11_0.loadpanel(_G.PanelRoot)
  r11_0.UpdatePocketCrystal(nil, false)
  _G.WaveMax = enemy.Load()
  _G.WaveNr = 1
  if r10_0.ResumeData then
    _G.MP = r10_0.ResumeData.mp
    _G.WaveNr = r10_0.ResumeDataWave
    local r6_5 = r10_0.ResumeData.purchase
    if r6_5 then
      for r10_5, r11_5 in pairs(r6_5) do
        if r10_5 == "tiana" then
          char.SummonPurchase(11)
        elseif r10_5 == "sara" then
          char.SummonPurchase(12)
        elseif r10_5 == "damage" then
          enemy.AttackPowerup(r11_5)
        elseif r10_5 == "speed" then
          char.SpeedPowerup(r11_5)
        elseif r10_5 == "range" then
          char.RangePowerup(r11_5)
        end
      end
    end
  end
  r11_0.ViewPanel()
  r11_0.pop_enemy_register(true)
  r11_0.init_pause_manager()
  r5_0.Pause(r10_0.PauseTypeFirstPause, {
    [r10_0.PauseFuncMoveEnemy] = false,
    [r10_0.PauseFuncMoveChars] = false,
    [r10_0.PauseFuncEnablePlayPauseButton] = true,
    [r10_0.PauseFuncShowGuideBlink] = true,
    [r10_0.PauseFuncShowTicket] = true,
    [r10_0.PauseFuncEnableUseOrbButton] = false,
    [r10_0.PauseFuncClosePulldownMenu] = false,
  })
  if _G.VIEW_FPS then
    local r7_5 = require("fps").PerformanceOutput.new()
    _G.DebugRoot:insert(r7_5.group)
    r7_5.group.x = 0
    r7_5.group.y = _G.Height - 24
  end
  if _G.WaveNr == 1 then
    db.DeleteResume(_G.UserID, _G.MapSelect, _G.StageSelect)
    db.DeleteResumeRewind(_G.UserID, _G.MapSelect, _G.StageSelect)
  end
  save.DataInit()
  save.DataPush("resume", {
    map = _G.MapSelect,
    stage = _G.StageSelect,
    wave = _G.WaveNr,
  })
  local r7_5 = false
  if r10_0.ResumeDataSave then
    r7_5 = true
    r11_0.resume_game(r10_0.ResumeDataSave)
    r10_0.ResumeDataSave = nil
  end
  local r8_5 = db.UsingItemList(_G.UserID)
  local r9_5 = {}
  local r10_5 = {}
  local r11_5 = nil
  local r12_5 = nil
  local r13_5 = nil
  local r14_5 = false
  for r18_5, r19_5 in pairs(r8_5) do
    r11_5 = r19_5[1]
    r12_5 = r19_5[2]
    r13_5 = r19_5[3]
    if r11_5 ~= nil and r12_5 ~= nil and r13_5 ~= nil then
      if r11_5 == r8_0.pay_item_data.SetItem01 then
        r14_5 = true
      end
      if _G.WaveNr <= r12_5 then
        table.insert(r9_5, r11_5)
        table.insert(r10_5, {
          r11_5,
          r13_5
        })
      end
    end
  end
  r11_0.process_powerup(r9_5, r14_5)
  if r7_5 == false then
    db.UpdateItemList(_G.UserID, r10_5, _G.WaveNr)
  end
  r10_0.ResumeData = nil
  if r4_5 then
    r11_0.draw_powerup_mark()
  end
  r1_0.Init(_G.CutinRoot, _G.MapSelect, _G.StageSelect)
  util.setActivityIndicator(false)
  display.remove(_G.LoadingRoot)
  _G.LoadingRoot = nil
  bgm.Play(2)
  if not _G.IsSimulator then
    save.DataClear()
  end
  if r0_0.CheckTrialDisable() == false then
    local r15_5 = r0_0.GetNowTrial()
    if r15_5 > 0 then
      r9_5 = {
        id = r15_5,
      }
      db.UseItem(_G.UserID, _G.WaveNr, r9_5)
      r11_0.process_powerup(r9_5)
      r0_0.ClearTrialMP(r15_5)
    end
  end
  OrbManager.ResetUsedOrbFlag()
  r10_0.IsLoadPanelFlag = true
  r11_0.first_help()
  r11_0.recovery_orb()
  r11_0.make_sidebar()
  if _G.IsDebug and _G.TestSpeed then
    _G.SpeedType = _G.TestSpeed
    events.SetRepeatTime(_G.SpeedType)
  else
    _G.SpeedType = 2
    events.SetRepeatTime(_G.SpeedType)
  end
end
local function r24_0(r0_11)
  -- line: [497, 499] id: 11
  return r10_0.ScoreType
end
local function r33_0(r0_20)
  -- line: [537, 547] id: 20
  if r10_0.GuideBlink == nil then
    return 
  end
  if r0_20 then
    events.Disable(r10_0.GuideBlink.ev, false)
    r10_0.GuideBlink.spr.isVisible = true
  else
    events.Disable(r10_0.GuideBlink.ev, true)
    r10_0.GuideBlink.spr.isVisible = false
    r10_0.GuideBlink.delay = 3000
  end
end
local function r72_0()
  -- line: [972, 974] id: 64
  return r10_0.UseOrbBtn
end
return {
  new = function(r0_6)
    -- line: [369, 472] id: 6
    r6_0.SetIsTutorial()
    if r0_6 ~= nil then
      if not r0_6.restart then
        _G.MapSelect = r0_6.map
        _G.StageSelect = r0_6.stage
        r10_0.ResumeDataWave = r0_6.wave
        r10_0.ResumeDataSave = r0_6.save
        r10_0.ResumeData = r0_6.data
      end
    else
      r10_0.ResumeData = nil
      r10_0.ResumeDataSave = nil
    end
    local r1_6 = db.RestoreCurrentWorldStage(_G.UserID)
    if type(r1_6) == "table" and (r1_6.cur_world ~= _G.MapSelect or r1_6.cur_stage ~= _G.StageSelect) then
      db.DeleteItemList(_G.UserID)
      if r0_0.CheckTrialDisable() == false then
        r0_0.InitNowTrial()
      end
    elseif type(r1_6) == "number" and r1_6 == 0 then
      db.DeleteItemList(_G.UserID)
    end
    db.SaveCurrentWorldStage({
      uid = _G.UserID,
      cur_world = _G.MapSelect,
      cur_stage = _G.StageSelect,
    })
    local r2_6 = display.newGroup()
    preload.Init(r2_6)
    _G.BgRoot = util.MakeGroup(r2_6)
    _G.GridRoot = util.MakeGroup(r2_6)
    _G.RouteRoot = util.MakeGroup(r2_6)
    _G.ShadowRoot = util.MakeGroup(r2_6)
    _G.StanRoot = util.MakeGroup(r2_6)
    _G.ObjectRoot = util.MakeGroup(r2_6)
    _G.SummonRoot = util.MakeGroup(r2_6)
    _G.SBaseRoot = util.MakeGroup(r2_6)
    _G.MarkerRoot = util.MakeGroup(r2_6)
    _G.BuffRoot = util.MakeGroup(r2_6)
    _G.MyRoot = util.MakeGroup(r2_6)
    _G.OSlipRoot = util.MakeGroup(r2_6)
    _G.SFrontRoot = util.MakeGroup(r2_6)
    _G.FrontRoot = util.MakeGroup(r2_6)
    _G.BackwardRoot = util.MakeGroup(r2_6)
    _G.SkyRoot = util.MakeGroup(r2_6)
    _G.ExplosionRoot = util.MakeGroup(r2_6)
    _G.LyraRoot = util.MakeGroup(r2_6)
    _G.TargetRoot = util.MakeGroup(r2_6)
    _G.MissleRoot = util.MakeGroup(r2_6)
    _G.MyTgRoot = util.MakeGroup(r2_6)
    _G.TickerRoot = util.MakeGroup(r2_6)
    _G.SPanelRoot = util.MakeGroup(r2_6)
    _G.BadRoot = util.MakeGroup(r2_6)
    _G.PanelRoot = util.MakeGroup(r2_6)
    _G.UpgradeRoot = util.MakeGroup(r2_6)
    _G.PauseMenuRoot = util.MakeGroup(r2_6)
    r10_0.CopyrightRoot = util.MakeGroup(r2_6)
    _G.OptionRoot = util.MakeGroup(r2_6)
    _G.DialogRoot = util.MakeGroup(r2_6)
    _G.PostRoot = util.MakeGroup(r2_6)
    _G.CutinRoot = util.MakeGroup(r2_6)
    _G.PowerupRoot = util.MakeGroup(r2_6)
    _G.LoadingRoot = util.MakeGroup(r2_6)
    _G.TrialRoot = util.MakeGroup(r2_6)
    _G.SnipeRoot = util.MakeGroup(r2_6)
    _G.DebugRoot = util.MakeGroup(r2_6)
    local r3_6 = cdn.CheckFilelist()
    r10_0.rtSide = {
      adsIconShowState = -1,
      rtImg = nil,
    }
    r10_0.rtSide.rtImg = util.MakeFrame(r2_6, r3_6, function(r0_7)
      -- line: [453, 455] id: 7
      r10_0.rtSide.adsIconShowState = r0_7
    end)
    _G.MapDB = system.pathForFile(string.format("data/map/%02d/%03d.sqlite", _G.MapSelect, _G.StageSelect), system.ResourceDirectory)
    _G.metrics.stage_start(_G.MapSelect, _G.StageSelect)
    r10_0.PossessingCrystal = r3_0.possessingCrystal.new(_G.DialogRoot, 164, _G.Height - 64 - 9)
    r10_0.PktCrystal = r3_0.PocketCrystalAccess(r17_0)
    r10_0.IsLoadPanelFlag = false
    r11_0.initMedal()
    Runtime:addEventListener("enterFrame", r19_0)
    return r2_6
  end,
  PossessingCrystalVisible = function(r0_8)
    -- line: [477, 479] id: 8
    r11_0.PossessingCrystalVisible(r0_8)
  end,
  SetPossessingCrystalPosition = function(r0_9, r1_9, r2_9)
    -- line: [484, 488] id: 9
    if r10_0.PossessingCrystal ~= nil then
      r10_0.PossessingCrystal.setPosition(r0_9, r1_9, r2_9)
    end
  end,
  ResetPossessingCrystalPosition = function()
    -- line: [493, 495] id: 10
    r10_0.PossessingCrystal.setPosition(164, _G.Height - 64 - 9)
  end,
  SetScoreType = function(r0_12)
    -- line: [501, 504] id: 12
    r11_0.set_score_type(r0_12)
  end,
  UpdateSpecialAchievement = function(r0_13, r1_13)
    -- line: [507, 509] id: 13
    r11_0.update_special_medal(r0_13, r1_13)
  end,
  ReleaseOK = function()
    -- line: [511, 511] id: 14
    r10_0.ReleaseFlag = true
  end,
  ViewPanel = function()
    -- line: [513, 515] id: 15
    r11_0.ViewPanel()
  end,
  StopClock = function()
    -- line: [518, 520] id: 16
    return r11_0.StopClock()
  end,
  ViewTicketCount = function()
    -- line: [523, 525] id: 17
    r11_0.ViewTicketCount()
  end,
  HideControlPanel = function()
    -- line: [528, 530] id: 18
    r11_0.hide_control_panel()
  end,
  ShowControlPanel = function()
    -- line: [533, 535] id: 19
    r11_0.show_control_panel()
  end,
  BlinkMode = r33_0,
  GoalSort = function(r0_21)
    -- line: [549, 557] id: 21
    if r10_0.GoalSprite == nil then
      return 
    end
    local r1_21 = nil
    local r2_21 = nil
    r2_21 = r10_0.GoalSpriteZ
    for r6_21, r7_21 in pairs(r10_0.GoalSprite) do
      r1_21 = anime.GetSprite(r7_21)
      table.insert(r0_21, {
        z = r2_21,
        spr = r1_21,
      })
    end
  end,
  GetEnemyRegister = function()
    -- line: [559, 561] id: 22
    return #r10_0.EnemyRegister
  end,
  NextPopEnemy = function()
    -- line: [563, 565] id: 23
    r11_0.NextPopEnemy()
  end,
  DeleteHitpoint = function(r0_24)
    -- line: [567, 637] id: 24
    anime.Show(r10_0.rewind_anime, true)
    r10_0.rectSpr.isVisible = true
    if r10_0.isUseBom then
      return 
    end
    if r10_0.PerfectFlag then
      local r1_24 = 1
      if _G.WaveNr >= 2 then
        r1_24 = _G.WaveNr
      end
      db.SaveResumeRewind(_G.UserID, _G.MapSelect, _G.StageSelect, r1_24)
    end
    r11_0.set_none_perfect()
    if r0_24 == nil then
      r0_24 = 1
    end
    r11_0.add_hp(-r0_24)
    local r1_24 = 0
    if _G.IsQuickGameOver then
      r1_24 = 100000
    end
    if r10_0.MedalObject.MedalDisplay ~= nil and r11_0.get_hp() < _G.DefaultHp then
      if r11_0.get_hp() == 0 then
        r10_0.MedalObject.MedalDisplay.EnableMedal(r10_0.MedalClass.TestMedalDisplayManager.MedalTestIndex.Hp1, false)
      elseif r11_0.get_hp() == 1 then
        r10_0.MedalObject.MedalDisplay.EnableMedal(r10_0.MedalClass.TestMedalDisplayManager.MedalTestIndex.Hp1, true)
      elseif r11_0.get_hp() < 10 then
        r10_0.MedalObject.MedalDisplay.EnableMedal(r10_0.MedalClass.TestMedalDisplayManager.MedalTestIndex.B, false)
      elseif r11_0.get_hp() < 15 then
        r10_0.MedalObject.MedalDisplay.EnableMedal(r10_0.MedalClass.TestMedalDisplayManager.MedalTestIndex.S, false)
      elseif r11_0.get_hp() < 20 then
        r10_0.MedalObject.MedalDisplay.EnableMedal(r10_0.MedalClass.TestMedalDisplayManager.MedalTestIndex.P, false)
      end
    end
    if r11_0.get_hp() <= r1_24 then
      char.SnipeModeOff()
      bgm.Stop()
      if r5_0.GetPauseType() == r10_0.PauseTypeNone then
        r11_0.pause_func(true)
      end
      r33_0(false)
      char.ClearAllCircle()
      char.ClearSummonGroup()
      _G.IsPlayingGame = false
      r10_0.GameOver = true
      r11_0.ShowBom()
    else
      sound.PlaySE(9, 22)
    end
    if r11_0.get_hp() >= 0 then
      r11_0.ViewPanel()
    end
  end,
  GetPocketCrystal = function()
    -- line: [642, 644] id: 25
    return r10_0.PokectCrystalValue
  end,
  UpdatePocketCrystal = function(r0_26, r1_26)
    -- line: [647, 649] id: 26
    r11_0.UpdatePocketCrystal(r0_26, r1_26)
  end,
  AddMP = function(r0_27, r1_27, r2_27, r3_27)
    -- line: [653, 707] id: 27
    if _G.LoginGameCenter and not db.GetGameCenterAchievement(_G.UserID, 19) then
      local r4_27 = db.CountAchievement(_G.UserID, 19, r0_27)
      local r5_27 = nil
      if r4_27 <= 100000 then
        r5_27 = math.floor(r4_27 * 100 / 100000)
      else
        r5_27 = 100
      end
      r11_0.CheckTotalAchievement(19, r5_27)
    end
    _G.MP = _G.MP + r0_27
    r11_0.ViewPanel()
    local r4_27 = display.newGroup()
    local r5_27 = nil
    local r6_27 = nil
    local r7_27 = nil
    r7_27 = 0
    while 0 < r0_27 do
      r5_27 = r0_27 % 10
      r0_27 = math.floor(r0_27 * 0.1)
      r10_0.SpriteNumber01.CreateImage(r4_27, r10_0.SpriteNumber01.sequenceNames.GetMP, r10_0.SpriteNumber01.frameDefines.GetMpStart + r5_27, r7_27, 0)
      r7_27 = r7_27 - 12
    end
    r4_27:setReferencePoint(display.CenterReferencePoint)
    r4_27.x = r1_27
    r4_27.y = r2_27 - 40
    if r3_27 then
      r4_27.xScale = r3_27
      r4_27.yScale = r3_27
      transition.to(r4_27, {
        alpha = 0.1,
        y = r2_27 - 120,
        time = 500,
        transition = easing.inExpo,
        onComplete = function(r0_28)
          -- line: [690, 692] id: 28
          display.remove(r0_28)
        end,
      })
    else
      transition.to(r4_27, {
        alpha = 0.1,
        y = r2_27 - 80,
        time = 300,
        transition = easing.inExpo,
        onComplete = function(r0_29)
          -- line: [700, 702] id: 29
          display.remove(r0_29)
        end,
      })
    end
    char.MPMonitoring()
  end,
  AddMPResume = function(r0_30)
    -- line: [710, 712] id: 30
    _G.MP = _G.MP + r0_30
  end,
  AddHPResume = function(r0_31)
    -- line: [714, 716] id: 31
    _G.HP = _G.HP + r0_31
  end,
  SetCornetFlag = function(r0_32)
    -- line: [719, 722] id: 32
    if r0_32 == 1 then
      r10_0.CornetPopupFlag = true
    end
    if r0_32 == 2 then
      r10_0.CornetKillFlag = true
    end
  end,
  Pause = function(r0_33, r1_33)
    -- line: [724, 735] id: 33
    if r0_33 == nil or r1_33 == nil then
      r11_0.pause_func_sub(true)
    elseif r5_0 ~= nil then
      r5_0.Pause(r0_33, r1_33)
    end
  end,
  Play = function(r0_34)
    -- line: [737, 749] id: 34
    if r0_34 == nil and r5_0.GetPauseType() ~= r10_0.PauseTypeFirstPause then
      r11_0.pause_func_sub(false)
    elseif r5_0 ~= nil then
      r5_0.Play(r0_34)
    end
  end,
  GetPauseType = function()
    -- line: [752, 754] id: 35
    return r5_0.GetPauseType()
  end,
  IsPauseTypeNone = function()
    -- line: [756, 758] id: 36
    return r5_0.GetPauseType() == r10_0.PauseTypeNone
  end,
  IsNotPauseTypeNone = function()
    -- line: [760, 762] id: 37
    return r5_0.GetPauseType() ~= r10_0.PauseTypeNone
  end,
  SetPlayBtnSize = function()
    -- line: [764, 766] id: 38
    r11_0.SetPlayBtnSize()
  end,
  ViewTicker = function(r0_39)
    -- line: [769, 781] id: 39
    local function r1_39(r0_40)
      -- line: [771, 773] id: 40
      return string.format("data/ticker/ticker%02d_", r0_40) .. _G.UILanguage .. ".png"
    end
    if r10_0.isTutorial then
      if r4_0 ~= nil then
        display.remove(r4_0)
      end
      r4_0 = util.LoadParts(_G.TickerRoot, r1_39(r0_39), 0, 560)
    end
  end,
  OpenPowerup = function()
    -- line: [786, 788] id: 41
    r11_0.powerup_func()
  end,
  OpenRewindDialog = function()
    -- line: [816, 818] id: 45
    r11_0.open_rewind_func()
  end,
  OpenPowerupDialog = function()
    -- line: [822, 824] id: 46
    r11_0.open_powerup_func()
  end,
  BackToMapDialog = function()
    -- line: [829, 831] id: 47
    r11_0.back_to_map_func()
  end,
  IsPowerup = function(r0_42)
    -- line: [793, 797] id: 42
    local r1_42 = r11_0.is_magic()
    r11_0.process_powerup(r0_42)
    r11_0.UpdatePocketCrystal()
  end,
  OpenOption = function()
    -- line: [802, 804] id: 43
    r11_0.option_func()
  end,
  OpenBuyCrystalDialog = function()
    -- line: [809, 811] id: 44
    r11_0.buy_crystal_func()
  end,
  OpenRestartDialog = function()
    -- line: [836, 876] id: 48
    local function r0_48(r0_49)
      -- line: [839, 851] id: 49
      bgm.FadeOut(500)
      sound.PlaySE(2)
      dialog.Close()
      r11_0.PossessingCrystalVisible(false)
      save.DataClear()
      util.ChangeScene({
        prev = r11_0.Cleanup,
        scene = "restart",
      })
      return true
    end
    local function r1_48(r0_50)
      -- line: [854, 863] id: 50
      sound.PlaySE(2)
      dialog.Close()
      r11_0.PossessingCrystalVisible(false)
      return true
    end
    if r0_0.GetItemCount(_G.MapSelect, _G.StageSelect) == 0 then
      if r0_0.CheckTrialDisable() == false then
        r0_0.SetTrialType(1)
        r11_0.showRestartDialog({
          onOk = r11_0.trial_game_over_func,
          onCancel = r1_48,
        })
      else
        r11_0.showRestartDialog({
          onOk = r0_48,
          onCancel = r1_48,
        })
      end
    else
      r11_0.showRestartDialog({
        onOk = r0_48,
        onCancel = r1_48,
      })
    end
  end,
  OpenQuitDialog = function()
    -- line: [881, 883] id: 51
    r11_0.change_title_func()
  end,
  AddHp = function(r0_52)
    -- line: [888, 890] id: 52
    r11_0.add_hp(r0_52)
  end,
  SetHp = function(r0_53)
    -- line: [892, 894] id: 53
    r11_0.set_hp(r0_53)
  end,
  RecoveryFullHealHp = function()
    -- line: [899, 901] id: 54
    r11_0.set_hp(_G.DefaultHp)
  end,
  AddMp = function(r0_55)
    -- line: [906, 908] id: 55
    r11_0.add_mp(r0_55)
  end,
  IsLoadPanel = function()
    -- line: [913, 915] id: 56
    return r10_0.IsLoadPanelFlag
  end,
  RecoveryOrb = function()
    -- line: [921, 923] id: 57
    r11_0.recovery_orb()
  end,
  AddScoreValue = function(r0_58)
    -- line: [928, 930] id: 58
    _G.Score = _G.Score + r0_58
  end,
  UpdateUseOrbMedal = function()
    -- line: [935, 937] id: 59
    r10_0.MedalObject.MedalFuncs.UpdateUseOrb(OrbManager.GetUsedOrbCount())
  end,
  UpdateTreasureboxMedal = function()
    -- line: [942, 949] id: 60
    local r0_60 = r12_0.GetNormalNum()
    local r1_60 = r12_0.GetRichNum()
    r10_0.MedalObject.MedalFuncs.UpdateTreasurebox(r10_0.MedalClass.MedalManager.ExType.TreasureboxTotal, r0_60 + r1_60)
    r10_0.MedalObject.MedalFuncs.UpdateTreasurebox(r10_0.MedalClass.MedalManager.ExType.TreasureboxNormal, r0_60)
    r10_0.MedalObject.MedalFuncs.UpdateTreasurebox(r10_0.MedalClass.MedalManager.ExType.TreasureboxRich, r1_60)
  end,
  UpdateLevelxxMedal = function(r0_61, r1_61)
    -- line: [954, 956] id: 61
    r10_0.MedalObject.MedalFuncs.UpdateLevelxx(r0_61, r1_61)
  end,
  UpdateUnitAndEvoxx = function(r0_62, r1_62)
    -- line: [961, 963] id: 62
    r10_0.MedalObject.MedalFuncs.UpdateUnitAndEvoxx(r0_62, r1_62)
  end,
  UpdateTwoUnitAndEvoxx = function(r0_63, r1_63)
    -- line: [968, 970] id: 63
    r10_0.MedalObject.MedalFuncs.UpdateTwoUnitAndEvoxx(r0_63, r1_63)
  end,
  GetUseOrbBtn = r72_0,
  GetStartBtn = function()
    -- line: [976, 978] id: 65
    return r10_0.StartBtn
  end,
  OnOrbBtnTutorial = function()
    -- line: [980, 984] id: 66
    if r5_0.GetPauseType() == r10_0.PauseTypeNone then
      r6_0.OrbBtnTutorial(true, r72_0())
    end
  end,
  GetTutorialManager = function()
    -- line: [988, 990] id: 67
    return r6_0
  end,
  MakeGrid = function(r0_68)
    -- line: [992, 994] id: 68
    r11_0.MakeGrid(r0_68)
  end,
  CheckTotalAchievement = function(r0_69, r1_69, r2_69)
    -- line: [996, 998] id: 69
    r11_0.CheckTotalAchievement(r0_69, r1_69, r2_69)
  end,
  Cleanup = function()
    -- line: [999, 1001] id: 70
    r11_0.Cleanup()
  end,
}
