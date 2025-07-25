-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("logic.pay_item_data")
local r1_0 = require("logic.char.CharStatus")
local r2_0 = require("char.BaseChar")
local r3_0 = require("tool.crystal")
local r4_0 = require("json")
local r5_0 = nil
local r6_0 = nil
local r7_0 = nil
local function r8_0(r0_1, r1_1)
  -- line: [9, 28] id: 1
  local r2_1 = nil	-- notice: implicit variable refs by block#[5]
  if type(r1_1) == "string" then
    r2_1 = r1_1
  elseif r1_1 ~= nil then
    r2_1 = util.Num2Column(r1_1)
  else
    r2_1 = 0
  end
  local r3_1 = display.newGroup()
  local r4_1 = nil
  display.newText(r3_1, r2_1, 1, 1, native.systemFontBold, 40):setFillColor(0, 0, 0)
  display.newText(r3_1, r2_1, 0, 0, native.systemFontBold, 40):setFillColor(255, 225, 76)
  r0_1:insert(r3_1)
  return r3_1
end
local function r9_0()
  -- line: [31, 41] id: 2
  if r1_0.SummonPlateGroup then
    return true
  end
  r1_0.IsUseCrystal = false
  r5_0(r1_0.SummonSpr.y)
  r2_0.view_summon_group(true, r1_0.SummonSpr.y)
  game.Play()
  r2_0.unlock_use_crystal_mode()
  return true
end
local function r10_0(r0_3)
  -- line: [44, 60] id: 3
  local r1_3 = r1_0.CrystalSprite.x
  local r2_3 = r1_0.CrystalSprite.y
  local r3_3 = r1_0.CrystalSprite.parent
  if r1_0.CrystalSprite then
    display.remove(r1_0.CrystalSprite)
  end
  r1_0.CrystalSprite = nil
  local r4_3 = r8_0(r3_3, util.ConvertDisplayCrystal(r0_3))
  r4_3:setReferencePoint(display.TopRightReferencePoint)
  r4_3.x = r1_3
  r4_3.y = r2_3
  r1_0.CrystalSprite = r4_3
  r3_0.ShowCoinInfo(r0_3)
end
local function r11_0()
  -- line: [63, 74] id: 4
  if r1_0.GuideCircle == nil then
    return 
  end
  if r1_0.GuideCircle.tween then
    transit.Delete(r1_0.GuideCircle.tween)
    r1_0.GuideCircle.tween = nil
  end
  if r1_0.GuideCircle.spr then
    display.remove(r1_0.GuideCircle.spr)
    r1_0.GuideCircle.spr = nil
  end
  r1_0.GuideCircle = nil
end
local function r12_0()
  -- line: [78, 88] id: 5
  if r1_0.SummonTransition then
    transition.cancel(r1_0.SummonTransition)
    r1_0.SummonTransition = nil
    r1_0.SummonSpr.isVisible = false
    return true
  end
  return false
end
local function r13_0(r0_6, r1_6)
  -- line: [90, 111] id: 6
  local r2_6 = r1_0.CharDef.Evo1CharId[r1_6.id]
  if r2_6 == nil then
    r0_6.isVisible = false
    return true
  end
  if db.IsLockSummonData(_G.UserID, r2_6) == 1 then
    r0_6.isVisible = false
    return true
  end
  if r1_0.IsUseCrystal ~= true and _G.MP < db.GetSummonCost(r2_6, 1) then
    r0_6.isVisible = false
    return true
  end
end
local function r14_0(r0_7, r1_7)
  -- line: [113, 130] id: 7
  anime.Remove(r0_7)
  if r1_7.func.range then
    r1_7.shot_ev = events.Register(r1_7.func.range, r1_7, 0, false)
  end
  r1_7.summon = false
  r1_0.SummonChar[r1_0.CharDef.CharId.Sarah].Reload(r1_7)
  if game ~= nil and game.IsNotPauseTypeNone() then
    game.MakeGrid(false)
  else
    game.MakeGrid(true)
  end
end
local function r15_0(r0_8, r1_8)
  -- line: [132, 139] id: 8
  if r1_8.type ~= 100 then
    r1_8.func.sound(r1_8, 1)
  end
  anime.Pause(r1_8.anime, true)
  anime.Show(r1_8.anime, true)
end
local function r16_0(r0_9, r1_9)
  -- line: [142, 144] id: 9
  anime.Remove(r0_9)
end
local function r17_0()
  -- line: [148, 165] id: 10
  if #r1_0.GuideSprite < 1 then
    return 
  end
  for r3_10, r4_10 in pairs(r1_0.GuideSprite) do
    if r4_10.anm then
      anime.Remove(r4_10.anm)
      r4_10.anm = nil
    end
    if r4_10.spr then
      display.remove(r4_10.spr)
      r4_10.spr = nil
    end
    if r4_10.tspr then
      display.remove(r4_10.tspr)
      r4_10.tspr = nil
    end
  end
  r1_0.GuideSprite = {}
end
local function r18_0()
  -- line: [167, 178] id: 11
  r2_0.view_summon_group(false)
  r2_0.not_show_upgrade_btn()
  r2_0.release_release_confirm()
  r17_0()
  r11_0()
  r1_0.IsUseCrystal = false
  r2_0.remove_summon_plate()
end
local function r19_0()
  -- line: [180, 199] id: 12
  if r1_0.SummonPlateGroup then
    return true
  end
  r1_0.IsUseCrystal = true
  r2_0.lock_use_crystal_mode()
  r1_0.userCoin = game.GetPocketCrystal()
  game.UpdatePocketCrystal(function(r0_13)
    -- line: [186, 192] id: 13
    if r0_13 ~= nil then
      r1_0.userCoin = r0_13
      r10_0(r1_0.userCoin)
      r5_0(r1_0.SummonSpr.y)
    end
  end, false)
  r10_0(r1_0.userCoin)
  r5_0(r1_0.SummonSpr.y)
  r2_0.view_summon_group(true, r1_0.SummonSpr.y)
  return true
end
local function r20_0(r0_14, r1_14, r2_14, r3_14, r4_14)
  -- line: [201, 211] id: 14
  if r4_14 == "g" then
    r1_0.SpriteNumber01.CreateImage(r0_14, r1_0.SpriteNumber01.sequenceNames.Score, r1_0.SpriteNumber01.frameDefines.MpNumberBStart + r3_14, r1_14, r2_14 - 66)
  elseif r4_14 == "h" then
    r1_0.SpriteNumber01.CreateImage(r0_14, r1_0.SpriteNumber01.sequenceNames.Score, r1_0.SpriteNumber01.frameDefines.MpNumberBStart + r3_14, r1_14, r2_14 - 66)
  elseif r4_14 == "i" then
    r1_0.SpriteNumber01.CreateImage(r0_14, r1_0.SpriteNumber01.sequenceNames.Score, r1_0.SpriteNumber01.frameDefines.MpNumberAStart + r3_14, r1_14, r2_14 - 66)
  else
    r1_0.SpriteNumber01.CreateImage(r0_14, r1_0.SpriteNumber01.sequenceNames.Score, r1_0.SpriteNumber01.frameDefines.MpNumberBStart + r3_14, r1_14, r2_14 - 66)
  end
end
local function r21_0(r0_15, r1_15, r2_15, r3_15, r4_15)
  -- line: [213, 225] id: 15
  if r4_15 == "a" then
    r1_0.SpriteNumber01.CreateImage(r0_15, r1_0.SpriteNumber01.sequenceNames.Score, r1_0.SpriteNumber01.frameDefines.MpNumberAStart + r3_15, r1_15, r2_15)
  elseif r4_15 == "g" then
    r1_0.SpriteNumber01.CreateImage(r0_15, r1_0.SpriteNumber01.sequenceNames.Score, r1_0.SpriteNumber01.frameDefines.MpNumberBStart + r3_15, r1_15, r2_15)
  elseif r4_15 == "h" then
    r1_0.SpriteNumber01.CreateImage(r0_15, r1_0.SpriteNumber01.sequenceNames.Score, r1_0.SpriteNumber01.frameDefines.MpNumberAStart + r3_15, r1_15, r2_15)
  elseif r4_15 == "i" then
    r1_0.SpriteNumber01.CreateImage(r0_15, r1_0.SpriteNumber01.sequenceNames.Score, r1_0.SpriteNumber01.frameDefines.MpNumberAStart + r3_15, r1_15, r2_15)
  else
    r1_0.SpriteNumber01.CreateImage(r0_15, r1_0.SpriteNumber01.sequenceNames.Score, r1_0.SpriteNumber01.frameDefines.MpNumberBStart + r3_15, r1_15, r2_15)
  end
end
local function r22_0(r0_16, r1_16, r2_16, r3_16)
  -- line: [227, 252] id: 16
  r1_16 = r1_16 / 1000
  local r4_16 = 28
  local r5_16 = 74
  for r9_16 = 1, 4, 1 do
    r21_0(r0_16, r4_16, r5_16, math.floor((r1_16 + 0.1)) % 10, r2_16)
    r4_16 = r4_16 + 14
    r1_16 = r1_16 * 10
  end
  if r2_16 == "g" or r2_16 == "h" or r2_16 == "i" then
    r3_16 = r3_16 / 1000
    local r6_16 = 28
    local r7_16 = 74
    for r11_16 = 1, 4, 1 do
      r20_0(r0_16, r6_16, r7_16, math.floor((r3_16 + 0.1)) % 10, r2_16)
      r6_16 = r6_16 + 14
      r3_16 = r3_16 * 10
    end
  end
end
local function r23_0(r0_17, r1_17, r2_17, r3_17)
  -- line: [254, 309] id: 17
  local r4_17 = nil	-- notice: implicit variable refs by block#[16, 25, 26]
  if r1_17 == "a" then
    r4_17 = r1_0.Objects.UnitListParts.CreateUnitPlate(r0_17, r1_0.Classes.UnitListParts.PlateType.UnlockEnable())
  elseif r1_17 == "b" then
    r4_17 = r1_0.Objects.UnitListParts.CreateUnitPlate(r0_17, r1_0.Classes.UnitListParts.PlateType.UnlockDisable())
  elseif r1_17 == "c" then
    r4_17 = r1_0.Objects.UnitListParts.CreateUnitPlate(r0_17, r1_0.Classes.UnitListParts.PlateType.Lock())
  elseif r1_17 == "d" then
    r4_17 = r1_0.Objects.UnitListParts.CreateUnitPlate(r0_17, r1_0.Classes.UnitListParts.PlateType.NoSummon01())
  elseif r1_17 == "f" then
    r4_17 = r1_0.Objects.UnitListParts.CreateUnitPlate(r0_17, r1_0.Classes.UnitListParts.PlateType.NoSummon02())
  elseif r1_17 == "g" then
    r4_17 = r1_0.Objects.UnitListParts.CreateUnitPlate(r0_17, r1_0.Classes.UnitListParts.PlateType.EvoPlateDisable())
  elseif r1_17 == "h" then
    r4_17 = r1_0.Objects.UnitListParts.CreateUnitPlate(r0_17, r1_0.Classes.UnitListParts.PlateType.EvoPlateEnableNormalOnly())
  elseif r1_17 == "i" then
    r4_17 = r1_0.Objects.UnitListParts.CreateUnitPlate(r0_17, r1_0.Classes.UnitListParts.PlateType.EvoPlateEnable())
  end
  if r4_17 == nil then
    r4_17 = display.newGroup()
  end
  local r6_17 = nil
  if r1_17 == "g" or r1_17 == "h" or r1_17 == "i" then
    local r7_17 = r1_0.CharDef.Evo1CharId[r0_17]
    if r3_17 == 2 then
      r6_17 = util.ConvertDisplayCrystal(db.GetSummonCost(r7_17, 1))
    else
      r6_17 = db.GetSummonCost(r7_17, 1)
    end
  end
  if r2_17 then
    r22_0(r4_17, r2_17, r1_17, r6_17)
  end
  r4_17:setReferencePoint(display.TopLeftReferencePoint)
  r4_17.isVisible = false
  _G.SPanelRoot:insert(r4_17)
  return r4_17
end
local function r24_0(r0_18, r1_18, r2_18, r3_18)
  -- line: [312, 314] id: 18
  r1_0.SpriteNumber01.CreateImage(r0_18, r1_0.SpriteNumber01.sequenceNames.Score, r1_0.SpriteNumber01.frameDefines.MpNumberAStart + r3_18, r1_18, r2_18)
end
local function r25_0(r0_19, r1_19, r2_19)
  -- line: [316, 327] id: 19
  local r3_19 = display.newGroup()
  r2_19 = r2_19 / 1000
  for r7_19 = 1, 4, 1 do
    r24_0(r3_19, r0_19, r1_19, math.floor((r2_19 + 0.1)) % 10)
    r0_19 = r0_19 + 14
    r2_19 = r2_19 * 10
  end
  return r3_19
end
local function r26_0(r0_20)
  -- line: [331, 377] id: 20
  local r1_20 = r0_20.id
  display.remove(r1_0.SummonConfirm.spr[1])
  display.remove(r1_0.SummonConfirm.spr[2])
  display.remove(r1_0.SummonConfirm.spr[3])
  display.remove(r1_0.SummonConfirm.spr[4])
  display.remove(r1_0.SummonConfirm.spr[5])
  r1_0.TutorialManager.SummonTutorial(false)
  game.OnOrbBtnTutorial()
  if r1_20 then
    r7_0(r1_0.SummonConfirm.summon, {
      phase = "ended",
    })
    if r1_0.TutorialManager.IsSummonTutorial and r1_0.TutorialManager.FirstSummonFlag == false then
      r1_0.TutorialManager.FirstSummonFlag = true
    end
    r1_0.TutorialManager.GameStartTutorial(true, game.GetStartBtn())
  else
    if r1_0.SummonTransition == nil then
      r1_0.SummonTransition = transition.to(r1_0.SummonSpr, {
        time = 3000,
        rotation = 360,
        transition = easing.linear,
        onComplete = r6_0,
      })
      r1_0.SummonSpr.isVisible = true
    end
    r2_0.hide_upgrade_circle(true, true)
    r11_0()
    game.PossessingCrystalVisible(false)
  end
  r2_0.remove_summon_plate()
  r2_0.not_show_upgrade_btn()
  r2_0.release_release_confirm()
  r1_0.SummonConfirm = nil
  return true
end
local function r27_0(r0_21)
  -- line: [379, 384] id: 21
  r1_0.SummonConfirm.summon.id = r1_0.CharDef.Evo1CharId[r1_0.SummonConfirm.summon.id]
  r26_0(r0_21)
  return true
end
local function r28_0(r0_22)
  -- line: [388, 416] id: 22
  local r1_22 = r0_22.x
  local r2_22 = r0_22.y
  local r3_22 = display.newGroup()
  local r4_22 = anime.Register(r1_0.SummonAnime[1].GetData(), r1_22, r2_22 - 40 + 63, "data/game")
  anime.RegisterFinish(r4_22, r14_0, r0_22)
  anime.Show(r4_22, true)
  _G.SBaseRoot:insert(anime.GetSprite(r4_22))
  _G.MyRoot:insert(r0_22.sort_sprite)
  local r6_22 = anime.Register(r1_0.SummonAnime[2].GetData(), r1_22, r2_22 - 40, "data/game")
  anime.RegisterTrigger(r6_22, 8, r15_0, r0_22)
  anime.RegisterFinish(r6_22, r16_0, r3_22)
  anime.Show(r6_22, true)
  _G.SFrontRoot:insert(anime.GetSprite(r6_22))
  anime.Pause(r4_22, false)
  anime.Show(r4_22, true)
  anime.Pause(r6_22, false)
  anime.Show(r6_22, true)
  return r3_22
end
local function r29_0(r0_23, r1_23)
  -- line: [418, 427] id: 23
  if not _G.GameData.voice then
    return 
  end
  if not r0_23 then
    return 
  end
  if r0_23.sound_path == nil then
    return 
  end
  local r2_23 = sound.GetCharVoiceFilename(r0_23.sound_path, r1_23)
  local r3_23 = r0_23.sound_channel
  sound.StopVoice(r3_23)
  sound.PlayVoice(r2_23, r3_23)
end
local function r30_0(r0_24)
  -- line: [429, 519] id: 24
  local r1_24 = nil	-- notice: implicit variable refs by block#[22]
  local r2_24 = nil	-- notice: implicit variable refs by block#[22]
  if r0_24.map_xy then
    r1_24 = r0_24.map_xy[1]
    r2_24 = r0_24.map_xy[2]
  else
    r1_24 = 0
    r2_24 = 0
  end
  local r3_24 = r0_24.min_range or 0
  local r4_24 = r0_24.max_range or 0
  local r5_24 = nil
  if r0_24.wait then
    r5_24 = {
      unpack(r0_24.wait)
    }
  else
    r5_24 = {
      0,
      0,
      0,
      0
    }
  end
  local r6_24 = nil
  if r0_24.attack then
    r6_24 = {
      r0_24.attack[1],
      r0_24.attack[2]
    }
  else
    r6_24 = {
      true,
      false
    }
  end
  local r7_24 = nil
  if r0_24.power then
    r7_24 = {
      unpack(r0_24.power)
    }
  else
    r7_24 = {
      0,
      0,
      0,
      0
    }
  end
  local r8_24 = {
    uid = r1_0.CharaUID,
    type = r0_24.type or 0,
    spr = nil,
    anime = nil,
    x = r0_24.x or 0,
    y = r0_24.y or 0,
    map_xy = {
      r1_24,
      r2_24
    },
    target = nil,
    range = {
      r3_24 * r3_24,
      r4_24 * r4_24
    },
    range_circle = r4_24 * 2,
    wait = r5_24,
    vect = 1,
    shooting = false,
    pause = true,
    shot_ev = nil,
    summon = true,
    shot_frame_nr = 0,
    next_target = nil,
    target_cancel = false,
    attack = r6_24,
    power = r7_24,
    data = nil,
    star = nil,
    level = r0_24.level or 1,
    buff_type = 0,
    buff_anm = nil,
    buff_anm2 = nil,
    buff_power = 1,
    buff_target = nil,
    touch_area = nil,
    func = {
      range = r1_0.SummonChar[r0_24.type].range_func,
      pointing = r1_0.SummonChar[r0_24.type].pointing_func,
      shoot = r1_0.SummonChar[r0_24.type].shoot_func,
      load = nil,
      finish = r1_0.SummonChar[r0_24.type].finish_func,
      search = r1_0.SummonChar[r0_24.type].search_func,
      check = r1_0.SummonChar[r0_24.type].check_func,
      release = r1_0.SummonChar[r0_24.type].release_func,
      circle = r1_0.SummonChar[r0_24.type].touch_func,
      pause = nil,
      destructor = r1_0.SummonChar[r0_24.type].kill_char,
      fumble = nil,
      lockon = nil,
      sound = r29_0,
    },
    sort_z = nil,
    sort_sprite = nil,
    star_rt = nil,
    sound_path = nil,
    sound_ext = "aac",
    sound_handle = nil,
    sound_channel = 30,
    not_select = false,
  }
  r1_0.CharaUID = r1_0.CharaUID + 1
  return r8_24
end
local function r32_0(r0_26, r1_26)
  -- line: [637, 679] id: 26
  if r1_26.phase == "ended" and r1_0.SummonPos then
    if r1_0.SummonTransition and r0_26.id == r1_0.CharDef.CharId.BlueMagicalFlower then
      r12_0()
    end
    r2_0.hide_upgrade_circle(true, true)
    if not r0_26.fast_summon then
      sound.PlaySE(5, 21, true)
    end
    r2_0.view_summon_group(false)
    r2_0.remove_summon_plate()
    r2_0.not_show_upgrade_btn()
    r2_0.release_release_confirm()
    r12_0()
    r17_0()
    r11_0()
    local r3_26 = r1_0.SummonPos[1]
    local r4_26 = r1_0.SummonPos[2]
    local r5_26 = r0_26.id
    if r5_26 ~= r1_0.CharDef.CharId.BlueMagicalFlower then
      _G.BingoManager.updateUserBingoData(_G.BingoManagerClass.MISSION_TYPE.SUMMON_CHAR(), {
        charId = r5_26,
      })
      _G.BingoManager.updateUserBingoData(_G.BingoManagerClass.MISSION_TYPE.SUMMON_WITCHES(), nil)
    end
    r2_0.get_current_char({
      type = r5_26,
    }).custom_summon(r5_26, r3_26, r4_26, r0_26)
  end
  return true
end
local function r33_0(r0_27, r1_27, r2_27, r3_27, r4_27)
  -- line: [682, 726] id: 27
  if _G.UserToken == nil then
    r18_0()
    return 
  end
  if _G.NoServerCheck then
    db.UseItem(_G.UserID, _G.WaveNr, r2_27[1])
    kpi.Spend(_G.MapSelect, _G.StageSelect, r2_27[1], 1, r1_27)
    _G.metrics.crystal_set_character_in_stage(_G.MapSelect, _G.StageSelect, r2_27[1], r1_27)
    r0_27(r3_27, r4_27)
    game.SetScoreType(1)
    game.Play()
  else
    util.setActivityIndicator(true)
    server.UseCoin(_G.UserToken, r2_27[1], 1, function(r0_28)
      -- line: [697, 724] id: 28
      util.setActivityIndicator(false)
      if server.CheckError(r0_28) then
        r18_0()
        server.NetworkError()
        sound.PlaySE(2)
        dialog.Close()
      else
        local r1_28 = r4_0.decode(r0_28.response)
        if r1_28.status == 0 then
          db.UseItem(_G.UserID, _G.WaveNr, r2_27[1])
          kpi.Spend(_G.MapSelect, _G.StageSelect, r2_27[1], 1, r1_27)
          _G.metrics.crystal_set_character_in_stage(_G.MapSelect, _G.StageSelect, r2_27[1], r1_27)
          r0_27(r3_27, r4_27)
          game.SetScoreType(1)
          r2_0.unlock_use_crystal_mode()
        else
          r18_0()
          server.ServerError(r1_28.status)
        end
      end
      game.UpdatePocketCrystal()
    end)
  end
end
local function r34_0(r0_29, r1_29, r2_29)
  -- line: [728, 772] id: 29
  local r4_29 = r2_0.get_current_char({
    type = r0_29.id,
  }).custom_crystal_cost(_G.UserStatus[r0_29.id].cost[1])
  if r1_0.userCoin == nil then
    return true
  end
  if r1_0.userCoin < r4_29 then
    server.NetworkError(31)
    return true
  end
  game.PossessingCrystalVisible(false)
  local r5_29 = string.format(db.GetMessage(14), util.ConvertDisplayCrystal(r4_29))
  local r7_29 = r0_0.getItemData(r1_0.ItemList["id" .. r0_29.id])
  dialog.Open(display.newGroup(), 13, {
    r5_29,
    15
  }, {
    function(r0_30)
      -- line: [755, 762] id: 30
      sound.PlaySE(1)
      dialog.Close()
      r33_0(r2_29, r4_29, r7_29, r0_29, r1_29)
      return true
    end,
    function(r0_31)
      -- line: [763, 769] id: 31
      sound.PlaySE(2)
      dialog.Close()
      r2_0.view_summon_group(true, r1_0.SummonSpr.y)
      return true
    end
  })
  return true
end
function r7_0(r0_32, r1_32)
  -- line: [774, 786] id: 32
  if r1_0.IsUseCrystal then
    r1_32.phase = "ended"
    return r34_0(r0_32, r1_32, function(r0_33, r1_33)
      -- line: [778, 782] id: 33
      r2_0.unlock_use_crystal_mode()
      r32_0(r0_33, r1_33)
    end)
  else
    return r32_0(r0_32, r1_32)
  end
end
local function r35_0(r0_34, r1_34)
  -- line: [789, 1005] id: 34
  r1_0.TutorialManager.SelectSummonTutorial(false)
  local function r5_34(r0_35)
    -- line: [794, 794] id: 35
    return "data/game/upgrade/" .. r0_35 .. ".png"
  end
  local function r6_34(r0_36)
    -- line: [796, 796] id: 36
    return r5_34(r0_36 .. _G.UILanguage)
  end
  if not _G.GameData.confirm then
    return r7_0(r0_34, r1_34)
  end
  r1_0.CrystalSpriteGrp[2].isVisible = false
  if r1_0.SummonSpr.y < 319 then
    r1_0.CrystalSpriteGrp.x = 640
    r1_0.CrystalSpriteGrp.y = 347
  else
    r1_0.CrystalSpriteGrp.x = 640
    r1_0.CrystalSpriteGrp.y = 305
  end
  if r1_0.SummonPlateGroup then
    return true
  end
  if r1_34.phase == "ended" and r1_0.SummonPos then
    r1_0.SummonSelectPlateSpr.alpha = 0.25
    for r10_34, r11_34 in pairs(r1_0.SummonGroupSpr) do
      r11_34[1].alpha = 0.25
      r11_34[2].alpha = 0.25
      r11_34[3].alpha = 0.25
      if r11_34[4] then
        r11_34[4].alpha = 0.25
      end
      if r11_34[6] then
        r11_34[6].alpha = 0.25
      end
      if r11_34[7] then
        r11_34[7].alpha = 0.25
      end
      if r11_34[8] then
        r11_34[8].alpha = 0.25
      end
      r1_0.SummonSelectCrystalSpr[r10_34].alpha = 0.1
      r1_0.SummonSelectMpSpr[r10_34].alpha = 0.1
      r1_0.EvoSummonSelectCrystalSpr[r10_34].alpha = 0.1
      r1_0.EvoSummonSelectMpSpr[r10_34].alpha = 0.1
    end
    for r10_34, r11_34 in pairs(r1_0.SummonCrystalGroupSpr) do
      r11_34[1].alpha = 0.25
      r11_34[2].alpha = 0.25
      r11_34[3].alpha = 0.25
      if r11_34[4] then
        r11_34[4].alpha = 0.25
      end
      if r11_34[6] then
        r11_34[6].alpha = 0.25
      end
      if r11_34[7] then
        r11_34[7].alpha = 0.25
      end
      if r11_34[8] then
        r11_34[8].alpha = 0.25
      end
    end
    local r7_34 = r0_34.id
    r2_0.hide_upgrade_circle(true, true)
    r2_0.remove_summon_plate()
    r2_0.not_show_upgrade_btn()
    r2_0.release_release_confirm()
    r2_0.display_summon_plate(r7_34, 1, r1_0.SummonSpr.y)
    if r1_0.SummonConfirm then
      r1_0.SummonConfirm.summon = r0_34
    else
      local r2_34 = nil	-- notice: implicit variable refs by block#[35]
      if r1_0.SummonSpr.y < 319 then
        r2_34 = r2_0.SUMMON_PLATE_POS[2]
      else
        r2_34 = r2_0.SUMMON_PLATE_POS[1]
      end
      local r3_34 = r2_34[1] + 50
      local r4_34 = r2_34[2] + 150
      local r8_34 = nil
      local r9_34 = nil
      if r1_0.IsUseCrystal ~= true then
        r8_34 = util.LoadBtn({
          rtImg = _G.SPanelRoot,
          fname = r6_34("summon_ok_"),
          x = r3_34,
          y = r4_34,
          func = r26_0,
        })
        if r1_0.TutorialManager.IsSummonTutorial and r1_0.TutorialManager.FirstSelectCharFlag == false then
          r1_0.TutorialManager.FirstSelectCharFlag = true
        end
        r1_0.TutorialManager.SummonTutorial(true, _G.SPanelRoot, r3_34, r4_34)
        r9_34 = util.LoadBtn({
          rtImg = _G.SPanelRoot,
          fname = r6_34("supersummon_ok_"),
          x = r3_34 + 225,
          y = r4_34,
          func = r27_0,
        })
      else
        r8_34 = util.LoadBtn({
          rtImg = _G.SPanelRoot,
          fname = r6_34("summon_ok_cry_"),
          x = r3_34,
          y = r4_34,
          func = r26_0,
        })
        r9_34 = util.LoadBtn({
          rtImg = _G.SPanelRoot,
          fname = r6_34("supersummon_ok_cry_"),
          x = r3_34 + 225,
          y = r4_34,
          func = r27_0,
        })
      end
      local r10_34 = util.LoadBtn({
        rtImg = _G.SPanelRoot,
        fname = r6_34("summon_cancel_"),
        x = r3_34 + 450,
        y = r4_34,
        func = r26_0,
      })
      local r12_34 = r2_0.get_current_char({
        type = r0_34.id,
      }).custom_crystal_cost(_G.UserStatus[r7_34].cost[1])
      local r13_34 = nil
      local r14_34 = nil
      local r15_34 = nil
      local r16_34 = r1_0.CharDef.Evo1CharId[r7_34]
      if r16_34 ~= nil and db.IsLockSummonData(_G.UserID, r16_34) ~= 1 then
        r15_34 = db.GetSummonCost(r16_34, 1)
      end
      if r1_0.IsUseCrystal ~= true then
        r13_34 = r25_0(r3_34 + 75, r4_34 + 45, r12_34)
        _G.SPanelRoot:insert(r13_34)
        if r15_34 ~= nil and r15_34 <= _G.MP then
          r14_34 = r25_0(r3_34 + 300, r4_34 + 45, r15_34)
          _G.SPanelRoot:insert(r14_34)
        end
      else
        r13_34 = r25_0(r3_34 + 75, r4_34 + 45, util.ConvertDisplayCrystal(r12_34))
        if r15_34 ~= nil and util.ConvertDisplayCrystal(r15_34) <= r1_0.userCoin then
          r14_34 = r25_0(r3_34 + 300, r4_34 + 45, util.ConvertDisplayCrystal(r15_34))
        end
      end
      r8_34.id = true
      r10_34.id = false
      r9_34.id = true
      r13_0(r9_34, r0_34)
      r1_0.SummonConfirm = {
        summon = r0_34,
        spr = {
          r8_34,
          r10_34,
          r13_34,
          r9_34,
          r14_34
        },
      }
    end
    if r7_34 ~= 15 and r7_34 ~= r1_0.CharDef.CharId.Kala and r7_34 ~= r1_0.CharDef.CharId.Amber then
      r12_0()
      local r11_34 = _G.UserStatus[r7_34].range[1][2] * 2 * (r1_0.RangePower + 100) / 100
      local r12_34 = r1_0.CircleSpr
      r12_34.xScale = 0.001
      r12_34.yScale = 0.001
      r12_34.isVisible = true
      r1_0.CircleTransition = transition.to(r12_34, {
        time = 250,
        xScale = r11_34 / r12_34.width,
        yScale = r11_34 / r12_34.height,
        rotation = 360,
        transition = easing.outQuad,
        onComplete = r2_0.circle_rolling,
      })
    else
      if r1_0.SummonTransition == nil then
        r1_0.SummonTransition = transition.to(r1_0.SummonSpr, {
          time = 3000,
          rotation = 360,
          transition = easing.linear,
          onComplete = r6_0,
        })
      end
      r1_0.SummonSpr.isVisible = true
    end
  end
  return true
end
local function r37_0()
  -- line: [1012, 1179] id: 38
  local r0_38 = {}
  local r1_38 = 1
  local r2_38 = 2
  for r6_38 = 1, 2, 1 do
    for r10_38 = 1, r1_0.MAX_USER, 1 do
      local r11_38 = r1_0.CharDef.UnitPanelOrder[r10_38]
      if r11_38 then
        r0_38 = {}
        local r13_38 = _G.UserStatus[r11_38].cost[1]
        if r6_38 == r2_38 and r11_38 then
          r13_38 = r2_0.get_current_char({
            type = r11_38,
          }).custom_crystal_cost(r13_38)
        end
        local r14_38 = nil
        if r6_38 == r1_38 then
          r14_38 = r23_0(r11_38, "a", r13_38, r6_38)
        else
          r14_38 = r23_0(r11_38, "a", util.ConvertDisplayCrystal(r13_38), r6_38)
        end
        r14_38.id = r11_38
        r14_38.touch = r2_0.get_current_char({
          type = r11_38,
        }).custom_confirm_summon
        r14_38:addEventListener("touch", r14_38)
        table.insert(r0_38, r14_38)
        if r6_38 == r1_38 then
          r14_38 = r23_0(r11_38, "b", r13_38, r6_38)
        else
          r14_38 = r23_0(r11_38, "b", util.ConvertDisplayCrystal(r13_38), r6_38)
        end
        function r14_38.touch(r0_39, r1_39)
          -- line: [1054, 1054] id: 39
          return true
        end
        r14_38:addEventListener("touch", r14_38)
        table.insert(r0_38, r14_38)
        if r11_38 ~= r1_0.CharDef.CharId.BlueMagicalFlower then
          r14_38 = r23_0(r11_38, "c")
          function r14_38.touch(r0_40, r1_40)
            -- line: [1060, 1060] id: 40
            return true
          end
        else
          r14_38 = display.newRect(_G.SPanelRoot, 0, 0, 64, 64)
          r14_38:setFillColor(0, 0, 0, 0)
        end
        r14_38:addEventListener("touch", r14_38)
        table.insert(r0_38, r14_38)
        if r1_0.CharDef.CharId.Luna <= r11_38 and r11_38 <= r1_0.CharDef.CharId.Amber or r11_38 == r1_0.CharDef.CharId.Yuiko then
          r14_38 = r23_0(r11_38, "d")
          function r14_38.touch(r0_41, r1_41)
            -- line: [1073, 1073] id: 41
            return true
          end
          r14_38:addEventListener("touch", r14_38)
          table.insert(r0_38, r14_38)
        end
        if r11_38 == r1_0.CharDef.CharId.LiliLala then
          r14_38 = r23_0(r11_38, "f")
          function r14_38.touch(r0_42, r1_42)
            -- line: [1081, 1081] id: 42
            return true
          end
          r14_38:addEventListener("touch", r14_38)
          table.insert(r0_38, r14_38)
        end
        if r1_0.CharDef.Evo1CharId[r11_38] ~= nil then
          if r11_38 ~= r1_0.CharDef.CharId.Luna and r11_38 ~= r1_0.CharDef.CharId.LiliLala and r11_38 ~= r1_0.CharDef.CharId.BlueMagicalFlower and r11_38 ~= r1_0.CharDef.CharId.Kala then
            table.insert(r0_38, r14_38)
          end
          if r11_38 ~= r1_0.CharDef.CharId.LiliLala then
            table.insert(r0_38, r14_38)
          end
          if r6_38 == r1_38 then
            r14_38 = r23_0(r11_38, "g", r13_38, r6_38)
          else
            r14_38 = r23_0(r10_38, "g", util.ConvertDisplayCrystal(r13_38), r6_38)
          end
          function r14_38.touch(r0_43, r1_43)
            -- line: [1112, 1112] id: 43
            return true
          end
          r14_38:addEventListener("touch", r14_38)
          table.insert(r0_38, r14_38)
          if r6_38 == r1_38 then
            r14_38 = r23_0(r11_38, "h", r13_38, r6_38)
          else
            r14_38 = r23_0(r11_38, "h", util.ConvertDisplayCrystal(r13_38), r6_38)
          end
          r14_38.id = r11_38
          r14_38.touch = r2_0.get_current_char({
            type = r11_38,
          }).custom_confirm_summon
          r14_38:addEventListener("touch", r14_38)
          table.insert(r0_38, r14_38)
          if r6_38 == r1_38 then
            r14_38 = r23_0(r11_38, "i", r13_38, r6_38)
          else
            r14_38 = r23_0(r11_38, "i", util.ConvertDisplayCrystal(r13_38), r6_38)
          end
          r14_38.id = r11_38
          r14_38.touch = r2_0.get_current_char({
            type = r11_38,
          }).custom_confirm_summon
          r14_38:addEventListener("touch", r14_38)
          table.insert(r0_38, r14_38)
        end
        if r6_38 == r1_38 then
          local r16_38 = display.newGroup()
          local r17_38 = r1_0.Objects.UnitListParts.CreateIconMp(r16_38)
          r17_38.isVisible = false
          table.insert(r1_0.SummonSelectMpSpr, r17_38)
          _G.SPanelRoot:insert(r17_38)
          local r18_38 = r1_0.Objects.UnitListParts.CreateIconMp(r16_38)
          r18_38.isVisible = false
          table.insert(r1_0.EvoSummonSelectMpSpr, r18_38)
          _G.SPanelRoot:insert(r18_38)
        else
          local r16_38 = display.newGroup()
          local r17_38 = r1_0.Objects.UnitListParts.CreateIconCrystal(r16_38)
          r17_38.isVisible = false
          table.insert(r1_0.SummonSelectCrystalSpr, r17_38)
          _G.SPanelRoot:insert(r17_38)
          local r18_38 = r1_0.Objects.UnitListParts.CreateIconCrystal(r16_38)
          r18_38.isVisible = false
          table.insert(r1_0.EvoSummonSelectCrystalSpr, r18_38)
          _G.SPanelRoot:insert(r18_38)
        end
        if r6_38 == r1_38 then
          table.insert(r1_0.SummonGroupSpr, r0_38)
        else
          table.insert(r1_0.SummonCrystalGroupSpr, r0_38)
        end
      end
    end
  end
end
function r6_0(r0_44)
  -- line: [1182, 1193] id: 44
  if r1_0.SummonTransition then
    transition.cancel(r1_0.SummonTransition)
  end
  r0_44.rotation = 0
  r1_0.SummonTransition = transition.to(r0_44, {
    time = 3000,
    rotation = 360,
    transition = easing.linear,
    onComplete = r6_0,
  })
end
local function r38_0(r0_45)
  -- line: [1196, 1207] id: 45
  local r1_45 = r0_45[1]
  r0_45[2]()
  if r1_0.SummonSpr then
    r6_0(r1_0.SummonSpr)
    r1_0.SummonSpr.isVisible = true
  end
  game.ShowControlPanel()
end
local function r39_0(r0_46)
  -- line: [1209, 1240] id: 46
  if r1_0.SummonConfirm then
    display.remove(r1_0.SummonConfirm.spr[1])
    display.remove(r1_0.SummonConfirm.spr[2])
    display.remove(r1_0.SummonConfirm.spr[3])
    display.remove(r1_0.SummonConfirm.spr[4])
    display.remove(r1_0.SummonConfirm.spr[5])
    r1_0.SummonConfirm = nil
  end
  r2_0.release_release_confirm()
  r2_0.hide_upgrade_circle(true, true)
  r12_0()
  if r1_0.SummonGroupSpr or r1_0.SummonCrystalGroupSpr then
    r2_0.view_summon_group(false)
  end
  r2_0.remove_summon_plate()
  r2_0.not_show_upgrade_btn()
  r2_0.release_release_confirm()
  if r0_46 ~= nil and r0_46 == true then
    r2_0.unlock_use_crystal_mode()
  end
end
local function r40_0()
  -- line: [1242, 1257] id: 47
  r2_0.not_show_upgrade_btn()
  r2_0.release_release_confirm()
  r12_0()
  r2_0.hide_upgrade_circle(true, true)
  r2_0.view_summon_group(false)
  r1_0.UseCrystalMode = false
  r2_0.remove_summon_plate()
end
local function r41_0(r0_48)
  -- line: [1259, 1268] id: 48
  local r1_48 = r0_48.param
  game.HideControlPanel()
  r39_0()
  r40_0()
  r3_0.Open(_G.DialogRoot, {
    r38_0,
    {
      _G.DialogRoot,
      r1_48
    }
  })
  return true
end
local function r42_0(r0_49)
  -- line: [1270, 1287] id: 49
  if r1_0.CrystalSprite == nil then
    return 
  end
  if server.CheckError(r0_49) then
    return 
  else
    local r1_49 = r4_0.decode(r0_49.response)
    if r1_49.status == 0 then
      local r2_49 = r1_49.coin
      r1_0.userCoin = r2_49
      r5_0(r1_0.SummonSpr.y)
      r10_0(r2_49)
    else
      server.ServerError(r1_49.status, nil)
    end
    r2_0.view_summon_group(true, r1_0.SummonSpr.y)
  end
end
local function r43_0()
  -- line: [1289, 1291] id: 50
  server.GetCoin(_G.UserToken, r42_0)
end
function r5_0(r0_60)
  -- line: [1441, 1468] id: 60
  r1_0.SummonMonitoring = nil
  local r1_60 = _G.UserStatus
  local r2_60 = _G.MP
  local r3_60 = nil
  local r4_60 = nil
  r4_60 = 0
  local r5_60 = nil
  for r9_60 = 1, r1_0.MAX_USER, 1 do
    r5_60 = r1_0.CharDef.UnitPanelOrder[r9_60]
    if r5_60 then
      if r1_0.IsUseCrystal then
        r4_60 = r2_0.get_current_char({
          type = r5_60,
        }).custom_crystal_status(r4_60, r2_0.get_current_char({
          type = r5_60,
        }).custom_crystal_cost(r1_60[r5_60].cost[1]), r5_60)
      elseif r1_0.SummonStatus[r5_60] ~= 3 then
        r4_60 = r2_0.get_current_char({
          type = r5_60,
        }).custom_summon_status(r4_60, r1_60[r5_60].cost[1], r5_60, r2_60)
      end
    end
  end
  if r4_60 then
    r1_0.SummonMonitoring = {
      mp = r4_60,
      y = r0_60,
    }
  else
    r1_0.SummonMonitoring = {
      mp = 0,
      y = r0_60,
    }
  end
end
return {
  make_summon_group = function()
    -- line: [1293, 1374] id: 51
    local function r0_51(r0_52)
      -- line: [1295, 1295] id: 52
      return "data/game/crystal/" .. r0_52 .. ".png"
    end
    local function r1_51(r0_53)
      -- line: [1297, 1297] id: 53
      return r0_51(r0_53 .. _G.UILanguage)
    end
    local function r2_51(r0_54)
      -- line: [1299, 1299] id: 54
      return "data/game/upgrade/" .. r0_54 .. ".png"
    end
    local function r3_51(r0_55)
      -- line: [1301, 1301] id: 55
      return r2_51(r0_55 .. _G.UILanguage)
    end
    r1_0.SummonGroupSpr = {}
    r1_0.SummonCrystalGroupSpr = {}
    local r4_51 = nil
    r1_0.SummonSelectPlateSpr = display.newGroup()
    r1_0.SummonSelectCrystalSpr = display.newGroup()
    r1_0.SummonSelectMpSpr = display.newGroup()
    r1_0.EvoSummonSelectCrystalSpr = display.newGroup()
    r1_0.EvoSummonSelectMpSpr = display.newGroup()
    _G.UpgradeRoot:insert(r1_0.SummonSelectCrystalSpr)
    _G.UpgradeRoot:insert(r1_0.SummonSelectMpSpr)
    _G.UpgradeRoot:insert(r1_0.EvoSummonSelectCrystalSpr)
    _G.UpgradeRoot:insert(r1_0.EvoSummonSelectMpSpr)
    util.LoadParts(r1_0.SummonSelectPlateSpr, "data/game/unit/unit_plate.png", 0, 0)
    r1_0.SummonSelectPlateSpr.isVisible = false
    r1_0.SummonSelectPlateSpr.touch = function()
      -- line: [1322, 1322] id: 56
      return true
    end
    r1_0.SummonSelectPlateSpr:addEventListener("touch", spr)
    util.LoadBtn({
      rtImg = r1_0.SummonSelectPlateSpr,
      fname = r3_51("upgrade_tgl_crystal_"),
      func = r9_0,
      x = 0,
      y = 0,
    }):setReferencePoint(display.CenterReferencePoint)
    util.LoadBtn({
      rtImg = r1_0.SummonSelectPlateSpr,
      fname = r3_51("upgrade_tgl_mp_"),
      func = r19_0,
      x = 0,
      y = 0,
    }):setReferencePoint(display.CenterReferencePoint)
    _G.SPanelRoot:insert(r1_0.SummonSelectPlateSpr)
    r37_0()
    r1_0.CrystalSpriteGrp = display.newGroup()
    util.LoadParts(r1_0.CrystalSpriteGrp, "data/bom/pocket_crystal.png", 0, 0)
    r1_0.CrystalSpriteGrp.x = 408
    r1_0.CrystalSpriteGrp.y = 287
    r1_0.CrystalSprite = r8_0(r1_0.CrystalSpriteGrp, "Loading")
    r1_0.CrystalSprite.x = 40
    r1_0.CrystalSprite.y = 5
    r1_0.CrystalSprite:setReferencePoint(display.TopRightReferencePoint)
    util.LoadBtn({
      rtImg = r1_0.CrystalSpriteGrp,
      fname = r1_51("add_crystal_"),
      x = 250,
      y = 0,
      func = r41_0,
      param = r43_0,
    })
    r1_0.CrystalSpriteGrp.isVisible = false
    r1_0.CrystalSpriteGrp:setReferencePoint(display.TopLeftReferencePoint)
    _G.SPanelRoot:insert(r1_0.CrystalSpriteGrp)
  end,
  circle_summon_rolling = r6_0,
  hide_summon_circle = r12_0,
  confirm_btn = r26_0,
  register_summon = r28_0,
  clear_guide_circle = r11_0,
  clear_guide = r17_0,
  summon_fx_trigger = r15_0,
  make_character_struct = r30_0,
  clear_all_circle = r39_0,
  clear_summon_group = r40_0,
  all_clear = r18_0,
  summon_touch = r7_0,
  set_summon_status = r5_0,
  make_confirm_group_num = r25_0,
  custom_crystal_status = function(r0_57, r1_57, r2_57)
    -- line: [1376, 1400] id: 57
    if r1_0.userCoin == nil or r1_0.userCoin < r1_57 then
      r1_0.SummonCrystalStatus[r2_57] = 2
      local r3_57 = r1_0.CharDef.Evo1CharId[r2_57]
      if r3_57 ~= nil and db.IsLockSummonData(_G.UserID, r3_57) ~= 1 then
        r1_0.SummonCrystalStatus[r2_57] = 6
      end
      if r0_57 == nil or r1_57 < r0_57 then
        r0_57 = r1_57
      end
    else
      r1_0.SummonCrystalStatus[r2_57] = 1
      local r3_57 = r1_0.CharDef.Evo1CharId[r2_57]
      if r3_57 ~= nil and db.IsLockSummonData(_G.UserID, r3_57) ~= 1 then
        r1_0.SummonCrystalStatus[r2_57] = 8
      end
    end
    return r0_57
  end,
  custom_summon_status = function(r0_59, r1_59, r2_59, r3_59)
    -- line: [1406, 1438] id: 59
    if r3_59 < r1_59 then
      r1_0.SummonStatus[r2_59] = 2
      local r4_59 = r1_0.CharDef.Evo1CharId[r2_59]
      if r4_59 ~= nil and db.IsLockSummonData(_G.UserID, r4_59) ~= 1 then
        r1_0.SummonStatus[r2_59] = 6
      end
      if r0_59 == nil or r1_59 < r0_59 then
        r0_59 = r1_59
      end
    else
      r1_0.SummonStatus[r2_59] = 1
      local r4_59 = r1_0.CharDef.Evo1CharId[r2_59]
      if r4_59 ~= nil then
        if db.IsLockSummonData(_G.UserID, r4_59) ~= 1 then
          if _G.MP < db.GetSummonCost(r4_59, 1) then
            r1_0.SummonStatus[r2_59] = 7
          else
            r1_0.SummonStatus[r2_59] = 8
          end
        end
        if r0_59 == nil or r1_59 < r0_59 then
          r0_59 = r1_59
        end
      end
    end
    return r0_59
  end,
  custom_crystal_cost = function(r0_58)
    -- line: [1402, 1404] id: 58
    return r0_58
  end,
  custom_summon = function(r0_25, r1_25, r2_25, r3_25)
    -- line: [521, 635] id: 25
    if not r3_25 then
      r3_25 = {}
    end
    if r3_25.level == nil then
      r3_25.level = 1
    end
    local r4_25 = r3_25.level
    local r5_25 = r1_25 * 80 - 80 + 40
    local r6_25 = r2_25 * 80 - 80 + 40
    local r7_25 = _G.UserStatus[r0_25]
    local r9_25 = r30_0({
      type = r0_25,
      x = r5_25,
      y = r6_25,
      map_xy = {
        r1_25,
        r2_25
      },
      min_range = r7_25.range[r4_25][1],
      max_range = r7_25.range[r4_25][2] * (r1_0.RangePower + 100) / 100,
      wait = r7_25.speed,
      attack = r7_25.attack,
      power = r7_25.power,
      level = r4_25,
    })
    r1_0.SummonChar[r0_25].Load(r9_25)
    local r10_25 = display.newGroup()
    r10_25:insert(r9_25.spr)
    r9_25.sort_sprite = r10_25
    r9_25.sort_z = r5_25 + r6_25 * 1000
    local r11_25 = display.newGroup()
    r10_25:insert(r11_25)
    r9_25.star_rt = r11_25
    if r0_25 < r1_0.CharDef.CharId.LiliLala or r1_0.CharDef.CharId.Kala <= r0_25 then
      r9_25.sound_path = sound.GetCharVoicePath(r0_25, _G.GameData.language)
      r9_25.sound_handle = nil
    end
    _G.MapLocation[r2_25][r1_25] = r0_25
    if r0_25 == r1_0.CharDef.CharId.Luna then
      r1_0.LunaOnlyOne = true
    elseif r0_25 == r1_0.CharDef.CharId.Kala then
      r1_0.KalaPos = {
        r9_25.x,
        r9_25.y
      }
      r1_0.KalaOnlyOne = true
      r1_0.KalaStruct = r9_25
    elseif r0_25 == r1_0.CharDef.CharId.Amber then
      r1_0.AmberOnlyOne = true
      r1_0.AmberStruct = r9_25
    elseif r0_25 == r1_0.CharDef.CharId.Yuiko then
      r1_0.YuikoOnlyOne = true
      r1_0.YuikoStruct = r9_25
    end
    local r12_25 = r3_25.no_skip_summon
    if r12_25 == nil then
      r12_25 = false
    end
    if r3_25.fast_summon and not r12_25 then
      _G.MyRoot:insert(r9_25.sort_sprite)
      r9_25.shot_ev = events.Register(r9_25.func.range, r9_25, 0, false)
      r9_25.summon = false
      anime.Pause(r9_25.anime, true)
      anime.Show(r9_25.anime, true)
    else
      if not r3_25.fast_summon then
        save.DataPush("pop", {
          id = r0_25,
          x = r1_25,
          y = r2_25,
        })
        r2_0.get_current_char(r9_25).check_summon_achievement()
      end
      r28_0(r9_25)
    end
    table.insert(r1_0.MyChar, r9_25)
    r1_0.SummonPos = nil
    if not r3_25.fast_summon then
      if r1_0.IsUseCrystal == false then
        game.AddMp(-r7_25.cost[1])
      end
      game.ViewPanel()
    end
    if r4_25 > 1 then
      r2_0.set_star(r9_25)
    end
    game.UpdateSpecialAchievement(r0_25, 1)
    game.UpdateLevelxxMedal(r9_25.type, r1_0.EvoChar.GetCharRank(r9_25))
    r1_0.IsUseCrystal = false
    for r16_25, r17_25 in pairs(r1_0.CharDef.Evo1CharId) do
      if r17_25 == r0_25 then
        r3_25.id = r16_25
        break
      end
    end
  end,
  custom_confirm_summon = function(r0_37, r1_37)
    -- line: [1007, 1009] id: 37
    return r35_0(r0_37, r1_37)
  end,
  CustomFirstSummon = function(r0_61, r1_61, r2_61, r3_61, r4_61, r5_61)
    -- line: [1470, 1481] id: 61
    r1_0.SummonPos = {
      r1_61,
      r2_61
    }
    r7_0({
      id = r0_61,
      fast_summon = true,
      level = r3_61,
      no_skip_summon = r4_61,
    }, {
      phase = "ended",
    })
  end,
}
