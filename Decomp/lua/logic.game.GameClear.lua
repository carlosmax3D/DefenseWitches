-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("logic.game.GameStatus")
local r1_0 = require("logic.char.CharStatus")
local r2_0 = require("logic.game.BaseGame")
local r3_0 = require("ui.medalget")
local r4_0 = require("ui.review")
local r5_0 = require("tool.posttweet")
local r6_0 = require("tool.fbook")
local r7_0 = require("tool.twitter")
local r8_0 = require("tool.trial")
local r9_0 = require("logic.cwave")
local r10_0 = require("logic.gamecenter")
local r11_0 = require("tool.tutorial_manager")
local r12_0 = require("evo.treasure_box_manager")
local r13_0 = 1000
local r14_0 = {
  {
    24,
    26,
    {
      255,
      238,
      204
    }
  },
  {
    24,
    26,
    {
      255,
      238,
      204
    }
  },
  {
    24,
    26,
    {
      255,
      238,
      204
    }
  },
  {
    24,
    26,
    {
      255,
      238,
      204
    }
  },
  {
    24,
    26,
    {
      255,
      170,
      153
    }
  },
  {
    24,
    26,
    {
      255,
      170,
      153
    }
  },
  {
    26,
    28,
    {
      255,
      211,
      51
    }
  }
}
local function r15_0(r0_1)
  -- line: [30, 40] id: 1
  sound.PlaySE(1)
  r7_0.Post(_G.PostRoot, _G.UserID, string.format(db.GetMessage(51), db.GetMessage(40 + _G.MapSelect), _G.StageSelect, r0_0.TotalScore) .. string.format(" %d/%d", _G.HP, _G.MaxHP) .. " #dwitch", nil)
end
local function r16_0(r0_2)
  -- line: [42, 51] id: 2
  sound.PlaySE(1)
  r6_0.Post(_G.PostRoot, "DefenseWitches", string.format(db.GetMessage(51), db.GetMessage(40 + _G.MapSelect), _G.StageSelect, r0_0.TotalScore) .. string.format(" %d/%d", _G.HP, _G.MaxHP), "DefenseWitches", nil)
end
local function r17_0(r0_3, r1_3)
  -- line: [53, 60] id: 3
  local r2_3 = r0_3.struct
  if r1_3.phase == "began" then
    r2_3.skip = true
  end
  return true
end
local function r18_0(r0_4, r1_4, r2_4)
  -- line: [62, 85] id: 4
  local r3_4 = r1_4.ms + r2_4
  if r3_4 >= 1000 then
    r3_4 = r3_4 % 1000
  end
  r1_4.ms = r3_4
  local r4_4 = r1_4.r
  local r5_4 = r1_4.g
  local r6_4 = r1_4.b
  if r3_4 < 500 then
    r4_4 = 255
    r5_4 = easing.linear(r3_4, 500, 255, -152)
    r6_4 = easing.linear(r3_4, 500, 255, -166)
  else
    r4_4 = 255
    r5_4 = easing.linear(r3_4 - 500, 500, 103, 152)
    r6_4 = easing.linear(r3_4 - 500, 500, 89, 166)
  end
  r1_4.spr:setFillColor(r4_4, math.round(r5_4), math.round(r6_4))
  return true
end
local function r19_0(r0_5, r1_5, r2_5, r3_5, r4_5)
  -- line: [88, 124] id: 5
  local r6_5 = r14_0[r1_5]
  if r2_5.msg == nil then
    local r5_5 = util.MakeText3({
      rtImg = r0_5,
      size = r6_5[1],
      color = r6_5[3],
      shadow = {
        54,
        63,
        76
      },
      diff_x = 1,
      diff_y = 2,
      msg = db.GetMessage(179 + r1_5),
    })
    r5_5:setReferencePoint(display.CenterLeftReferencePoint)
    r5_5.x = 352
    r5_5.y = r4_5 + 16
    r2_5.msg = r5_5
  end
  local r7_5 = r2_5.spr
  if r7_5 then
    display.remove(r7_5)
  end
  r7_5 = util.MakeText3({
    rtImg = r0_5,
    size = r6_5[2],
    color = r6_5[3],
    shadow = {
      54,
      63,
      76
    },
    diff_x = 1,
    diff_y = 2,
    msg = util.Num2Column(r3_5),
  })
  r7_5:setReferencePoint(display.CenterRightReferencePoint)
  r7_5.x = 864
  r7_5.y = r4_5 + 16
  r2_5.spr = r7_5
end
local function r20_0(r0_6, r1_6, r2_6)
  -- line: [126, 292] id: 6
  local r3_6 = r1_6.index
  local r4_6 = r1_6.rtImg
  if r1_6.sound == nil then
    r1_6.sound = sound.LoopSEStream("se51", 29)
  end
  local r5_6 = false
  if r1_6.skip then
    local r6_6 = nil
    local r7_6 = 0
    local r8_6 = 128
    for r12_6, r13_6 in pairs(r1_6.struct) do
      r6_6 = r13_6.score
      if r6_6 >= 0 then
        r19_0(r4_6, r12_6, r13_6, r6_6, r8_6)
        r8_6 = r8_6 + 48
        r7_6 = r7_6 + r6_6
      end
    end
    r19_0(r4_6, 7, r1_6.total, r7_6, 416)
    r5_6 = true
  else
    local r6_6 = r1_6.y
    local r7_6 = r1_6.struct[r3_6]
    local r8_6 = r7_6.score
    local r9_6 = r7_6.ms + r2_6
    local r10_6 = nil
    if r13_0 <= r9_6 then
      r9_6 = r13_0
      r10_6 = r8_6
    else
      r10_6 = math.floor(easing.inExpo(r9_6, r13_0, 0, r8_6))
    end
    r7_6.ms = r9_6
    r7_6.now = r10_6
    r19_0(r4_6, r3_6, r7_6, r10_6, r6_6)
    r19_0(r4_6, 7, r1_6.total, r1_6.total.score + r10_6, 416)
    if r13_0 <= r9_6 then
      repeat
        r3_6 = r3_6 + 1
        if r3_6 > 6 then
          break
        end
        local r12_6 = r1_6.struct[r3_6].score
      until r12_6 ~= -1
      r1_6.y = r6_6 + 48
      r1_6.index = r3_6
      r1_6.total.score = r1_6.total.score + r8_6
      if r3_6 > 6 then
        r5_6 = true
      end
    end
  end
  if r5_6 then
    local r6_6 = {}
    if r1_6.high_score then
      local r8_6 = r1_6.total.spr.contentBounds
      local r9_6 = r8_6.xMin - 16
      local r10_6 = (r8_6.yMin + r8_6.yMax) / 2
      local r11_6 = util.MakeText3({
        rtImg = r4_6,
        size = 20,
        color = {
          255,
          255,
          255
        },
        shadow = {
          54,
          63,
          76
        },
        diff_x = 1,
        diff_y = 2,
        msg = "BEST SCORE‼",
      })
      r11_6:setReferencePoint(display.CenterRightReferencePoint)
      r11_6.x = r9_6
      r11_6.y = r10_6
      local r12_6 = nil
      r12_6 = {
        ms = 0,
        ed = 1000,
        r = 255,
        g = 255,
        b = 255,
        spr = r11_6[2],
      }
      r12_6.ev = events.Register(r18_0, r12_6, 1)
      table.insert(r6_6, r12_6.ev)
    end
    if #r6_6 > 0 then
      dialog.SetCloseFunc(function(r0_7)
        -- line: [219, 224] id: 7
        for r4_7, r5_7 in pairs(r0_7) do
          events.Delete(r5_7)
        end
        dialog.SetCloseFunc(nil, nil)
      end, r6_6)
    end
    local function r7_6()
      -- line: [229, 253] id: 8
      for r3_8, r4_8 in pairs(r1_6.btn) do
        r4_8.disable = false
      end
      r1_6.ev = nil
      r1_6.skip = true
      if r4_0.ShowDialog(_G.MapSelect, _G.StageSelect) == false then
        if _G.ServerStatus.invitation == 1 then
          r5_0.ShowDialog(_G.MapSelect, _G.StageSelect, r0_0.TotalScore)
        else
          server.GetStatus(function(r0_9)
            -- line: [246, 250] id: 9
            if _G.ServerStatus.invitation == 1 then
              r5_0.ShowDialog(_G.MapSelect, _G.StageSelect, r0_0.TotalScore)
            end
          end, nil)
        end
      end
    end
    _G.BingoManager.isBingoEnabled(_G.BingoManager.getBingoCardId(), function(r0_10)
      -- line: [256, 284] id: 10
      if r1_6.sound then
        sound.StopVoice(29)
        r1_6.sound = nil
      end
      if r0_10 == true then
        local r1_10 = _G.BingoManager.getUserClearedDataList()
        if r1_10 ~= nil and 0 < #r1_10 then
          local r2_10 = display.newGroup()
          r4_6:insert(r2_10)
          local r4_10 = require("bingo.bingo_card").new({
            rootLayer = r2_10,
            bingoCardId = _G.BingoManager.getBingoCardId(),
          })
          r4_10.show(true, true)
          r4_10.setMarkedByList(r1_10, function()
            -- line: [272, 275] id: 11
            _G.BingoManager.setUserClearedDataList()
            r7_6()
          end)
        else
          r7_6()
        end
        return 
      end
      r7_6()
    end)
    return false
    -- close: r6_6
  end
  return true
end
local function r21_0(r0_12)
  -- line: [296, 311] id: 12
  if r0_12 == nil then
    return 
  end
  local r1_12 = server.JsonDecode(r0_12)
  if r1_12 and r1_12.status == 0 then
    for r7_12, r8_12 in pairs(r1_12.achieves) do
      kpi.Achievement(r8_12)
    end
  end
end
local function r22_0(r0_13)
  -- line: [314, 330] id: 13
  local r1_13 = _G.UserID
  local r2_13 = nil
  local r3_13 = nil
  local r4_13 = nil
  r4_13 = {}
  for r8_13 = 1, 5, 1 do
    r2_13 = db.LoadAchievement(r1_13, r0_13, r8_13)
    r3_13 = 0
    for r12_13 = 1, 10, 1 do
      if r2_13[r12_13] then
        r3_13 = r3_13 + 10
      end
    end
    r4_13[r8_13] = r3_13
  end
  return r4_13
end
local function r23_0()
  -- line: [332, 394] id: 14
  local r0_14 = _G.UserID
  local r1_14 = _G.MapSelect
  local r2_14 = _G.StageSelect
  local r3_14 = _G.HP
  local r4_14 = {}
  for r8_14, r9_14 in pairs(r0_0.MedalClass.MedalManager.MedalId) do
    table.insert(r4_14, false)
  end
  if r0_0.SpecialAchievementFlag == true then
    db.SetAchievement(r0_14, r1_14, r2_14, r0_0.MedalClass.MedalManager.MedalId.Sp, true)
    r4_14[1] = true
  end
  if r0_0.PerfectFlag then
    db.SetAchievement(r0_14, r1_14, r2_14, r0_0.MedalClass.MedalManager.MedalId.G, true)
    db.SetAchievement(r0_14, r1_14, r2_14, r0_0.MedalClass.MedalManager.MedalId.S, true)
    db.SetAchievement(r0_14, r1_14, r2_14, r0_0.MedalClass.MedalManager.MedalId.B, true)
    r4_14[2] = true
    r4_14[3] = true
    r4_14[4] = true
  else
    if r3_14 >= 15 then
      db.SetAchievement(r0_14, r1_14, r2_14, r0_0.MedalClass.MedalManager.MedalId.S, true)
      r4_14[3] = true
    end
    if r3_14 >= 10 then
      db.SetAchievement(r0_14, r1_14, r2_14, r0_0.MedalClass.MedalManager.MedalId.B, true)
      r4_14[4] = true
    end
    if r3_14 == 1 then
      db.SetAchievement(r0_14, r1_14, r2_14, r0_0.MedalClass.MedalManager.MedalId.Hp1, true)
      r4_14[5] = true
    end
  end
  local r5_14 = r0_0.MedalObject.MedalManager.GetAcquireMedal()
  local r6_14 = nil
  for r10_14 = 1, #r5_14, 1 do
    if r5_14[r10_14] ~= nil and util.IsContainedTable(r5_14[r10_14], "ex_type") == true then
      local r11_14 = r0_0.MedalClass.MedalManager.MedalId.Ex1 + r10_14 - 1
      db.SetAchievement(r0_14, r1_14, r2_14, r11_14, true)
      r4_14[r11_14] = true
    end
  end
  return r4_14
end
local function r24_0(r0_15)
  -- line: [397, 481] id: 15
  sound.PlaySE(1)
  dialog.Close()
  save.DataClear()
  if r8_0.CheckTrialDisable() == false then
    r8_0.InitNowTrial()
  end
  local r1_15 = _G.UserID
  local r2_15 = _G.MapSelect
  local r3_15 = _G.StageSelect
  if r2_15 == 10 and r3_15 == 10 then
    _G.MapSelect = 1
    _G.StageSelect = 1
    util.ChangeScene({
      prev = r2_0.Cleanup,
      scene = "complete",
      efx = "fade",
    })
  else
    r3_15 = r3_15 + 1
    if r11_0.IsGameStartTutorial == true then
      if r2_15 <= _G.MaxMap then
        _G.MapSelect = r2_15
        _G.StageSelect = r3_15
        util.ChangeScene({
          prev = r2_0.Cleanup,
          scene = "stage",
          efx = "fade",
        })
      end
    elseif r0_15 ~= nil then
      _G.StageSelect = r3_15
      local r4_15 = "stage"
      if _G.MaxStage < r3_15 then
        _G.StageSelect = 1
        _G.MapSelect = r2_15 + 1
        r4_15 = "map"
        if 1 < _G.MapSelect and cdn.CheckFilelist() == true then
          util.ChangeScene({
            prev = r2_0.Cleanup,
            scene = "cdn",
            efx = "fade",
            param = {
              next = "stage",
              back = "map",
              scene = "map",
              val = _G.MapSelect,
            },
          })
          return 
        end
      end
      util.ChangeScene({
        prev = r2_0.Cleanup,
        scene = "stage",
        efx = "fade",
      })
    else
      _G.StageSelect = 1
      r2_15 = r2_15 + 1
      if r2_15 <= _G.MaxMap then
        _G.MapSelect = r2_15
        util.ChangeScene({
          prev = r2_0.Cleanup,
          scene = "map",
          efx = "fade",
        })
      else
        util.ChangeScene({
          prev = r2_0.Cleanup,
          scene = "title",
          efx = "fade",
        })
      end
    end
  end
  return true
end
local function r25_0(r0_16)
  -- line: [487, 541] id: 16
  enemy.DropTreasureboxEnemy = nil
  enemy.PopEnemyNum = 0
  enemy.DropFlareNum = 0
  enemy.DropRewindNum = 0
  enemy.DropRecoverNum = 0
  local r1_16 = db.LoadStageClearCount(_G.MapSelect, _G.StageSelect)
  db.AddStageClearCount(_G.MapSelect, _G.StageSelect)
  if 0 < r1_16.clearCount and r12_0.GetNormalNum() < 1 and r12_0.GetRichNum() < 1 and r0_16 ~= nil then
    r0_16()
    return 
  end
  local r2_16 = require("evo.add_up_experience_dialog")
  local r3_16 = {
    grade01 = nil,
    grade02 = nil,
  }
  if r12_0.GetNormalNum() > 0 then
    r3_16.grade01 = r12_0.GetNormalTreasurebox()
  end
  if r12_0.GetRichNum() > 0 then
    r3_16.grade02 = r12_0.GetRichTreasurebox()
  end
  local r4_16 = nil
  r4_16 = 0 < OrbManager.GetUsedOrbCount()
  ExpManager.LoadExp()
  r2_16.Open({
    stageClearCount = r1_16.clearCount,
    totalExperience = ExpManager.GetExp(),
    getTreasureboxList = r3_16,
    isUsedOrb = r4_16,
    closeCallbackFunc = r0_16,
  })
end
local function r26_0()
  -- line: [546, 804] id: 17
  local function r0_17(r0_18)
    -- line: [548, 560] id: 18
    if r8_0.CheckTrialDisable() == false then
      r8_0.InitNowTrial()
    end
    sound.PlaySE(2)
    dialog.Close()
    util.ChangeScene({
      prev = r2_0.Cleanup,
      scene = "title",
      efx = "fade",
    })
    return true
  end
  local function r1_17(r0_19)
    -- line: [562, 574] id: 19
    if r8_0.CheckTrialDisable() == false then
      r8_0.InitNowTrial()
    end
    sound.PlaySE(2)
    dialog.Close()
    util.ChangeScene({
      prev = r2_0.Cleanup,
      scene = "map",
      efx = "fade",
    })
    return true
  end
  local function r2_17(r0_20)
    -- line: [576, 577] id: 20
  end
  local function r3_17(r0_21)
    -- line: [579, 587] id: 21
    sound.PlaySE(2)
    dialog.Close()
    save.DataClear()
    r4_0.Init()
    r5_0.Init()
    util.ChangeScene({
      prev = r2_0.Cleanup,
      scene = "restart",
    })
    return true
  end
  local r4_17 = _G.UserToken
  local r5_17 = _G.UserID
  local r6_17 = _G.MapSelect
  local r7_17 = _G.StageSelect
  if r0_0.PowerupMark then
    if r0_0.PowerupMark.tween then
      transit.Delete(r0_0.PowerupMark.tween)
    end
    if r0_0.PowerupMark.spr then
      display.remove(r0_0.PowerupMark.spr)
    end
  end
  r0_0.PowerupMark = nil
  local r8_17 = _G.MP - r0_0.PurchaseMP
  if r8_17 < 0 then
    r8_17 = 0
  end
  local r9_17 = _G.HP - r0_0.PurchaseHP
  if r9_17 < 0 then
    r9_17 = 0
  end
  if r9_17 > 20 then
    r9_17 = 20
  end
  local r10_17 = r8_17 * 100
  local r11_17 = r9_17 * 10000
  local r12_17 = char.CalcUnitScore()
  local r13_17 = 0
  if not r0_0.ReleaseFlag then
    r13_17 = 300000
  end
  local r14_17 = 0
  if r0_0.PerfectFlag then
    r14_17 = 500000
  end
  r0_0.TotalScore = _G.Score + r10_17 + r11_17
  r0_0.TotalScore = r0_0.TotalScore + r12_17 + r13_17
  r0_0.TotalScore = r0_0.TotalScore + r14_17
  r0_0.MedalObject.MedalFuncs.UpdateScore(r0_0.TotalScore)
  if r9_17 == 1 then
    _G.BingoManager.updateUserBingoData(_G.BingoManagerClass.MISSION_TYPE.CLEAR_IN_HP1(), nil)
  end
  local r15_17 = db.LoadAchievementStage(r5_17, r6_17, r7_17)
  local r16_17 = r23_0()
  local r17_17 = {}
  for r21_17 = 1, r0_0.MedalClass.MedalManager.MedalId.Hp1, 1 do
    r17_17[r21_17] = r16_17[r21_17]
  end
  server.UnlockLocalAchievement(r4_17, r17_17, r6_17, r7_17, function(r0_22)
    -- line: [643, 649] id: 22
    if r0_22.isError then
      db.SetAchieveQueue("local", r6_17, r7_17, r17_17)
    end
    r21_0(r0_22.response)
  end)
  if r6_17 == 3 and (r7_17 == 3 or r7_17 == 5 or r7_17 == 6 or r7_17 == 9) then
    local r18_17 = {}
    local r19_17 = 1
    for r23_17 = r0_0.MedalClass.MedalManager.MedalId.Ex1, r0_0.MedalClass.MedalManager.MedalId.Ex3, 1 do
      r18_17[r19_17] = r16_17[r23_17]
      r19_17 = r19_17 + 1
    end
    server.UnlockExAchievement(r4_17, r18_17, r6_17, r7_17, function(r0_23)
      -- line: [661, 666] id: 23
      if r0_23.isError then
        db.SetAchieveQueue("local", r6_17, r7_17, r17_17)
      end
    end)
  end
  local r18_17 = r22_0(r6_17)
  server.UnlockWorldAchievement(r4_17, r18_17, r6_17, function(r0_24)
    -- line: [672, 677] id: 24
    if r0_24.isError then
      db.SetAchieveQueue("world", r6_17, 1, r18_17)
    end
    r21_0(r0_24.response)
  end)
  if _G.LoginGameCenter and 0 < r18_17[1] then
    local r19_17 = r18_17[1]
    local r20_17 = 26 + r6_17 - 1
    if r19_17 >= 100 then
      if db.SetGameCenterAchievement(r5_17, r20_17) then
        r10_0.UnlockAchievements(r20_17, r19_17)
        kpi.Achievement(r20_17)
      end
    else
      r10_0.UnlockAchievements(r20_17, r19_17)
    end
  end
  if _G.LoginGameCenter and not db.GetGameCenterAchievement(r5_17, 17) and r0_0.CornetPopupFlag and not r0_0.CornetKillFlag then
    r2_0.CheckTotalAchievement(17, db.CountAchievement(r5_17, 17) * 10)
  end
  if _G.LoginGameCenter and not db.GetGameCenterAchievement(r5_17, 24) and #r1_0.MyChar < 1 then
    db.CountAchievement(r5_17, 24)
    r2_0.CheckTotalAchievement(24, 100)
  end
  if r0_0.ScoreType == r0_0.ScoreTypeDef.Advance then
    kpi.Clear(r6_17, r7_17, TotalScore)
  else
    kpi.Clear(r6_17, r7_17, 0)
  end
  if r10_17 <= 0 then
    r10_17 = -1
  end
  if r12_17 <= 0 then
    r12_17 = -1
  end
  if r13_17 <= 0 then
    r13_17 = -1
  end
  if r14_17 <= 0 then
    r14_17 = -1
  end
  local r19_17 = db.AddScore(r5_17, r6_17, r7_17, r0_0.ScoreType, r0_0.TotalScore)
  local r20_17 = db.GetScore(r5_17, r6_17, r0_0.ScoreType)
  assert(r20_17, debug.traceback())
  local r21_17 = 0
  for r25_17, r26_17 in pairs(r20_17) do
    r21_17 = r21_17 + r26_17
  end
  r10_0.SetScore(r6_17, r21_17, r0_0.ScoreType)
  local r22_17 = dialog.GameClear(_G.DialogRoot, {
    _G.Score,
    r11_17,
    r10_17,
    r12_17,
    r13_17,
    r14_17
  }, {
    r1_17,
    r24_0,
    r3_17,
    r16_0,
    r15_0,
    r20_0,
    r17_0
  })
  local function r23_17(r0_25)
    -- line: [741, 744] id: 25
    events.Disable(r22_17.ev, false)
  end
  local r24_17 = r0_0.MedalObject.MedalManager.GetAcquireMedal()
  local r25_17 = {}
  local r26_17 = false
  for r30_17, r31_17 in pairs(r16_17) do
    local r32_17 = {
      acquireFlag = false,
      reward = {},
    }
    r25_17[r30_17] = r32_17
    r32_17 = r15_17[r30_17]
    if r32_17 then
      r32_17 = r25_17[r30_17]
      r32_17.acquireFlag = false
    else
      r32_17 = r0_0.MedalClass.MedalManager.MedalId.Sp
      if r32_17 <= r30_17 then
        r32_17 = r0_0.MedalClass.MedalManager.MedalId.Hp1
        if r30_17 <= r32_17 then
          r32_17 = #r0_0.MedalClass.MedalManager.AchievementReward
          if r30_17 <= r32_17 then
            r25_17[r30_17].acquireFlag = r31_17
            r32_17 = r25_17[r30_17]
            r32_17.reward = r0_0.MedalClass.MedalManager.AchievementReward[r30_17]
          end
        end
      else
        r25_17[r30_17].reward = r24_17[r30_17 - r0_0.MedalClass.MedalManager.MedalId.Ex1 + 1].reward
        r32_17 = r25_17[r30_17].reward
        if r32_17 ~= nil then
          r32_17 = r25_17[r30_17]
          r32_17.acquireFlag = true
        else
          r32_17 = r25_17[r30_17]
          r32_17.acquireFlag = false
        end
      end
      if r31_17 then
        r26_17 = true
      end
    end
  end
  local r27_17 = false
  if r26_17 then
    r27_17 = true
    r3_0.ShowDialog(r25_17, r23_17)
  end
  local r28_17 = r7_17 + 1
  if _G.MaxStage < r28_17 then
    db.UnlockMap(r5_17, r6_17 + 1)
  else
    db.UnlockStage(r5_17, r6_17, r28_17)
  end
  _G.BingoManager.updateUserBingoData(_G.BingoManagerClass.MISSION_TYPE.CLEAR_WORLD(), nil)
  db.SaveToServer2(r4_17)
  r22_17.high_score = r19_17
  events.Disable(r22_17.ev, r27_17)
  _G.IsPlayingGame = false
end
local function r27_0(r0_26)
  -- line: [808, 844] id: 26
  char.SnipeModeOff()
  r2_0.setMpObject()
  anime.Remove(r0_0.orbButtonEffect)
  statslog.LogSend("game_clear", {
    hp = _G.HP,
    mp = _G.MP,
    score = _G.Score,
  })
  _G.metrics.stage_clear(_G.MapSelect, _G.StageSelect)
  OrbManager.SaveUsedOrbTime()
  db.DeleteItemList(_G.UserID)
  if r11_0.IsGameStartTutorial == true then
    r26_0()
    return 
  end
  r25_0(r26_0)
end
local function r28_0(r0_27)
  -- line: [847, 849] id: 27
  r0_0.PerfectFlag = r0_27
end
return {
  game_clear = r27_0,
  GameClearNextButton = r24_0,
  set_perfect_flag = r28_0,
  set_none_perfect = function()
    -- line: [853, 857] id: 28
    r2_0.set_special_achievement_flag(false)
    r28_0(false)
  end,
  view_perfect = function(r0_29)
    -- line: [859, 861] id: 29
    r9_0.ViewPerfect(r27_0)
  end,
}
