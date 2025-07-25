-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("logic.char.CharStatus")
local r1_0 = require("logic.game.GameStatus")
local r2_0 = nil
local r3_0 = 0
local r4_0 = 0
local r5_0 = 0
local r6_0 = 0
local r7_0 = nil
local r8_0 = nil
if _G.GameMode == _G.GameModeEvo then
  r7_0 = require("evo.treasure_box")
  r8_0 = require("evo.treasure_box_manager")
end
local r9_0 = 1
local r10_0 = require("resource.char_define")
local r11_0 = require("efx.fx_chakudan")
local r12_0 = require("efx.fx_gekiha")
local r13_0 = {
  require("efx.burn01"),
  require("efx.burn02")
}
local r14_0 = {
  require("efx.electric_shock01"),
  require("efx.electric_shock02")
}
local r15_0 = {
  require("efx.freezing01"),
  require("efx.freezing02"),
  require("efx.freezing03"),
  require("efx.freezing04")
}
local r16_0 = require("efx.fx_bom")
local r17_0 = {
  require("efx.attack_fx.zzz_eff")
}
local r18_0 = {
  "fx_chakudan_0_0",
  "fx_chakudan_0_1",
  "fx_chakudan_0_2",
  "fx_gekiha_a_0_0",
  "fx_gekiha_a_0_1",
  "fx_gekiha_a_0_2",
  "burn01_0_0",
  "burn01_0_1",
  "burn01_0_2",
  "burn01_0_3",
  "burn02_0_0",
  "burn02_0_1",
  "burn02_0_2",
  "burn02_0_3",
  "electric_shock01_0_0",
  "electric_shock01_0_1",
  "electric_shock01_0_2",
  "electric_shock01_0_3",
  "electric_shock01_0_4",
  "electric_shock01_0_5",
  "electric_shock01_0_6",
  "electric_shock01_0_7",
  "freezing01_0_0",
  "freezing03_0_0"
}
local r19_0 = 0
local r20_0 = 2000
local function r21_0(r0_1)
  -- line: [62, 90] id: 1
  local r1_1 = 0 + 1
  if r20_0 <= r1_1 then
    r1_1 = r20_0
    if game ~= nil and game.IsNotPauseTypeNone() and game.GetPauseType() ~= r1_0.PauseTypeStopClock then
      return true
    end
  end
  r0_1.ms = r1_1
  local r2_1 = r1_1 / r20_0
  anime.Move(r0_1.anm, r0_1.sx + 200 * r2_1, r0_1.sy + 600 * r2_1 - 120 * math.sin(math.pi * r2_1), {
    angle = 270 * r2_1,
    scale = 1 + r2_1 / 2,
  })
  if r20_0 <= r1_1 then
    r0_1.func(r0_1.struct)
    anime.Remove(r0_1.anm)
    r0_1.ev = nil
    return false
  end
  return true
end
local function r22_0(r0_2)
  -- line: [93, 118] id: 2
  display.remove(r0_2.htptspr)
  r0_2.htptspr = nil
  local r1_2 = display.newGroup()
  local r2_2 = r0_2.hitpoint * 48 / r0_2.maxhtpt
  display.newRect(r1_2, 0, 0, r2_2, 4):setFillColor(255, 64, 0, 191.25)
  local r4_2 = 48 - r2_2
  if r4_2 > 0 then
    display.newRect(r1_2, r2_2, 0, r4_2, 4):setFillColor(27, 38, 38, 191.25)
  end
  r1_2.x = r0_2.htptpos[1]
  r1_2.y = r0_2.htptpos[2]
  r0_2.hp_root:insert(r1_2)
  if (r0_2.type == 31 or r0_2.type == 6) and r0_2.move_vect < 0 then
    r1_2.xScale = -1
    r1_2.x = 24
  end
  r0_2.htptspr = r1_2
end
local function r23_0(r0_3)
  -- line: [121, 136] id: 3
  if r0_3.poison_effective == nil or r0_3.poison_effective.enabled == false then
    return 
  end
  r0_3.poison_effective.enabled = false
  if r0_3.poison_effective.efx ~= nil then
    anime.Remove(r0_3.poison_effective.efx)
    r0_3.poison_effective.efx = nil
  end
  r0_3.poison_effective.type = r10_0.PoisonType.None
  r0_3.poison_effective.sleepDurationTime = 0
  r0_3.poison_effective.move_func = nil
  r0_3.poison_effective.params = nil
end
local function r24_0(r0_4, r1_4)
  -- line: [139, 246] id: 4
  if r1_4 == nil then
    r1_4 = false
  end
  r0_4.living = false
  if r0_4.anm then
    anime.Remove(r0_4.anm)
    r0_4.anm = nil
  end
  if r0_4.move then
    events.Delete(r0_4.move)
    r0_4.move = nil
  end
  if r0_4.target_spr then
    anime.Show(r0_4.target_spr, false)
    r0_4.target_spr = nil
    char.ClearNextTarget(r0_4)
  end
  if r0_4.snipe_target_spr then
    anime.Show(r0_4.snipe_target_spr, false)
    r0_4.snipe_target_spr = nil
  end
  display.remove(r0_4.htptspr)
  r0_4.htptspr = nil
  if r0_4.hit_anm then
    anime.Remove(r0_4.hit_anm)
    r0_4.hit_anm = nil
  end
  if r0_4.burst_anm then
    anime.Remove(r0_4.burst_anm)
    r0_4.burst_anm = nil
  end
  if r0_4.burn_efx then
    anime.Remove(r0_4.burn_efx)
    r0_4.burn_efx = nil
  end
  if r0_4.burn_ev then
    events.Delete(r0_4.burn_ev)
    r0_4.burn_ev = nil
  end
  if r0_4.stan_efx then
    anime.Remove(r0_4.stan_efx)
    r0_4.stan_efx = nil
  end
  if r0_4.freeze_efx then
    anime.Remove(r0_4.freeze_efx)
    r0_4.freeze_efx = nil
  end
  r23_0(r0_4)
  if r0_4.aura then
    anime.Remove(r0_4.aura)
    r0_4.aura = nil
  end
  if r0_4.crash_obj then
    display.remove(r0_4.crash_obj)
    r0_4.crash_obj = nil
  end
  if r0_4.shadow then
    display.remove(r0_4.shadow)
    r0_4.shadow = nil
  end
  if r0_4.map_x and r0_4.map_y then
    _G.MapLocation[r0_4.map_y][r0_4.map_x] = 0
    if game ~= nil and game.IsNotPauseTypeNone() then
      game.MakeGrid(true)
    else
      game.MakeGrid(false)
    end
  end
  if r0_4.dive_anm then
    events.Delete(r0_4.dive_anm)
    r0_4.dive_anm = nil
  end
  if r1_4 then
    return 
  end
  local r2_4 = table.indexOf(_G.Enemys, r0_4)
  if r2_4 then
    table.remove(_G.Enemys, r2_4)
  end
  if #_G.Enemys <= 0 and game.GetEnemyRegister() <= 0 and not _G.PopEnemyShield then
    game.NextPopEnemy()
  end
  _G.EnemyCnt = _G.EnemyCnt - 1
end
local function r25_0(r0_5, r1_5)
  -- line: [248, 250] id: 5
  r24_0(r1_5)
end
local function r27_0(r0_7, r1_7)
  -- line: [259, 493] id: 7
  if r0_7.burst_anm then
    r0_7.living = false
    return 
  end
  if r0_7.anm then
    anime.Show(r0_7.anm, false)
  end
  if r0_7.burn_efx then
    anime.Remove(r0_7.burn_efx)
    r0_7.burn_efx = nil
  end
  if r0_7.crash_obj then
    r0_7.crash_obj.isVisible = false
  end
  r0_7.living = false
  local r2_7 = r0_7.sx
  local r3_7 = r0_7.sy
  local r4_7 = anime.Register(r12_0.GetData(), r2_7, r3_7, "data/game")
  local r5_7 = r0_7.func
  if r5_7.burst_finish then
    anime.RegisterFinish(r4_7, r5_7.burst_finish, r0_7)
  end
  _G.MissleRoot:insert(anime.GetSprite(r4_7))
  r0_7.burst_anm = r4_7
  if r5_7.burst then
    r5_7.burst(r0_7)
  else
    anime.Show(r4_7, true)
  end
  local r6_7 = r0_7.score
  if _G.SpeedType >= 2 then
    r6_7 = r6_7 * 2
  end
  _G.BingoManager.updateUserBingoData(_G.BingoManagerClass.MISSION_TYPE.BEAT_ENEMY(), {
    enemyId = r0_7.type,
  })
  game.AddScoreValue(r6_7)
  game.AddMP(r0_7.addmp, r2_7, r3_7)
  local r7_7 = r0_7.type
  if 13 <= r7_7 and r7_7 <= 22 then
    local r8_7 = db.CountAchievement(_G.UserID, 18)
    local r9_7 = nil
    if r8_7 <= 3 then
      r9_7 = math.floor(r8_7 * 100 / 3)
    else
      r9_7 = 100
    end
    game.CheckTotalAchievement(18, r9_7)
  end
  if game.GetTutorialManager().IsGameStartTutorial == true then
    return 
  end
  local r8_7 = false
  local r9_7 = nil
  local r10_7 = false
  local r11_7 = nil
  if _G.UserInquiryID ~= nil and _G.LoginItems ~= nil and _G.LoginItems["2"] ~= nil and _G.LoginItems["2"].id ~= nil and r4_0 ~= nil then
    local r12_7 = db.GetInventoryItem(_G.UserInquiryID, _G.LoginItems["2"].id) + r4_0
    if r0_7.treasurebox == true then
      if r12_7 < 2 and math.random(99) < 40 then
        r9_7 = r8_0.TreasureboxDropItemListIndexFlare
        r4_0 = r4_0 + 1
        r8_7 = true
      elseif math.random(99) < 2 then
        r9_7 = r8_0.TreasureboxDropItemListIndexFlare
        r8_7 = true
      end
      if r9_7 == nil then
        r9_7 = r8_0.GetDropNormalTreasureboxType(r0_7.maxhtpt)
        r8_7 = true
      end
    end
  end
  if r8_0.IsDrop(nil, false) == true and r8_7 == false then
    if math.random(999) < 17 and r4_0 <= 3 then
      r9_7 = r8_0.TreasureboxDropItemListIndexFlare
      r4_0 = r4_0 + 1
    else
      r9_7 = r8_0.GetDropNormalTreasureboxType(r0_7.maxhtpt)
    end
    r8_7 = true
  end
  if r8_7 then
    r7_0.Pop(r0_7.move_vect, r0_7.sx, r0_7.sy, r8_0.GetTreasureboxInfo(r9_7)[1], function()
      -- line: [376, 382] id: 8
      r8_0.AddTreasurebox(r9_7)
      game.ViewPanel()
      game.UpdateTreasureboxMedal()
    end)
  end
  if r1_7 ~= nil and r1_7.evo ~= nil and 0 < r1_7.evo.evoLevel and r1_7.evo.charDropRate ~= nil then
    r10_7 = r1_7.evo.rareDropRateCoefficient ~= nil
  else
    r10_7 = r1_7 ~= nil and r1_7.evo ~= nil and 0 < r1_7.evo.evoLevel and r1_7.evo.charDropRate ~= nil 	-- block#50 is visited secondly
  end
  if r8_7 == false and r10_7 == true and r8_0.IsDrop(r1_7, true) == true then
    local r12_7 = math.random(450)
    if r12_7 < 100 and r6_0 < 3 then
      if r12_7 < 30 and r5_0 < 3 then
        r9_7 = r8_0.TreasureboxDropItemListIndexRewind
        r5_0 = r5_0 + 1
      elseif r12_7 < 75 then
        r9_7 = r8_0.TreasureboxDropItemListIndexOrb1
      elseif r12_7 < 82 then
        r9_7 = r8_0.TreasureboxDropItemListIndexOrb2
        r6_0 = r6_0 + 1
      elseif r12_7 < 86 then
        r9_7 = r8_0.TreasureboxDropItemListIndexOrb4
        r6_0 = r6_0 + 2
      elseif r12_7 < 90 then
        r9_7 = r8_0.TreasureboxDropItemListIndexOrb8
        r6_0 = r6_0 + 3
      end
    else
      r9_7 = r8_0.GetDropTreasureboxType(r0_7.maxhtpt, r1_7.evo.rareDropRateCoefficient)
    end
    local r13_7 = r8_0.GetTreasureboxInfo(r9_7)
    if r13_7 ~= nil and 0 < #r13_7 then
      r7_0.Pop(r0_7.move_vect, r0_7.sx, r0_7.sy, r13_7[1], function()
        -- line: [422, 428] id: 9
        r8_0.AddTreasurebox(r9_7)
        game.ViewPanel()
        game.UpdateTreasureboxMedal()
      end)
      r8_7 = true
    end
  end
  if r8_7 == false and r0_0.YuikoOnlyOne and r8_0.IsDropYuikoAbility(r1_7, r10_7) == true then
    r9_7 = r8_0.GetDropTreasureboxTypeYuikoAbility(r0_7.maxhtpt, r5_0, r4_0)
    if r9_7 == 0 then
      return 
    elseif r9_7 == r8_0.TreasureboxDropItemListIndexRewind then
      r5_0 = r5_0 + 1
    elseif r9_7 == r8_0.TreasureboxDropItemListIndexFlare then
      r4_0 = r4_0 + 1
    end
    local r12_7 = nil
    local r13_7 = nil
    local r14_7 = nil
    local r15_7 = r0_0.YuikoStruct
    if r15_7 ~= nil and r15_7.x ~= nil and r15_7.y ~= nil then
      r13_7 = r15_7.x
      r14_7 = r15_7.y
      if r13_7 < 80 then
        r12_7 = 1
      elseif r13_7 > 880 then
        r12_7 = -1
      elseif math.random(0, 100) < 50 then
        r12_7 = -1
      else
        r12_7 = 1	-- block#90 is visited secondly
      end
    else
      r13_7 = r0_7.sx
      r14_7 = r0_7.sy
      r12_7 = r0_7.move_vect
    end
    local r16_7 = r8_0.GetTreasureboxInfo(r9_7)
    if r16_7 ~= nil and 0 < #r16_7 then
      r7_0.Pop(r12_7, r13_7, r14_7, r16_7[1], function()
        -- line: [480, 486] id: 10
        r8_0.AddTreasurebox(r9_7)
        game.ViewPanel()
        game.UpdateTreasureboxMedal()
      end, true)
      sound.PlaySE(19, 13)
      r8_7 = true
    end
  end
end
local function r28_0(r0_11, r1_11, r2_11, r3_11)
  -- line: [495, 504] id: 11
  local r4_11 = anime.Register(r11_0.GetData(), r0_11, r1_11, "data/game")
  _G.MissleRoot:insert(anime.GetSprite(r4_11))
  if r2_11 then
    anime.RegisterFinish(r4_11, r2_11, r3_11)
  end
  anime.Show(r4_11, true)
  return r4_11
end
local function r29_0(r0_12, r1_12, r2_12)
  -- line: [510, 553] id: 12
  if not r0_12.living then
    return 
  end
  if r19_0 > 0 then
    r1_12 = r1_12 * r19_0
  end
  local function r3_12(r0_13, r1_13)
    -- line: [519, 542] id: 13
    anime.Remove(r0_13)
    r1_13.hit_anm = nil
    r22_0(r1_13)
    r1_13.htptms = 0
    if r1_13.hitpoint <= 0 then
      r1_13.living = false
      if r1_13.hard then
        sound.PlaySE(26, 16)
      else
        sound.PlaySE(27, 17)
      end
      r27_0(r1_13, r2_12)
    else
      if r1_13.hard then
        sound.PlaySE(24, 14)
      else
        sound.PlaySE(25, 15)
      end
      if r1_13.func.damage then
        r1_13.func.damage(r1_13)
      end
    end
  end
  r0_12.hitpoint = r0_12.hitpoint - r1_12
  if r0_12.hitpoint < 0 then
    r0_12.hitpoint = 0
  end
  local r4_12 = r0_12.sx
  local r5_12 = r0_12.sy
  if r0_12.hit_anm == nil then
    r0_12.hit_anm = r28_0(r4_12, r5_12, r3_12, r0_12)
  end
end
local function r30_0(r0_14, r1_14, r2_14, r3_14)
  -- line: [560, 611] id: 14
  if not r0_14.living then
    return 
  end
  if r19_0 > 0 then
    r1_14 = r1_14 * r19_0
  end
  r0_14.hitpoint = r0_14.hitpoint - r1_14
  if r0_14.hitpoint <= 0 then
    if r0_14.hard then
      sound.PlaySE(26, 16)
    else
      sound.PlaySE(27, 17)
    end
    r0_14.hitpoint = 0
    r0_14.living = false
    r22_0(r0_14)
    r27_0(r0_14, r3_14)
  else
    r22_0(r0_14)
    r0_14.htptms = 0
    if r0_14.func.damage then
      r0_14.func.damage(r0_14)
    end
  end
  if r0_14.attr == 2 or r0_14.attr == 4 then
    return 
  end
  r0_14.stan_flag = true
  r0_14.stan_time = r2_14
  if r0_14.stan_efx == nil then
    local r4_14 = r0_14.sx
    local r5_14 = r0_14.sy
    local r6_14 = nil
    if r0_14.type <= 11 then
      r6_14 = r14_0[1]
    else
      r6_14 = r14_0[2]
    end
    local r9_14 = anime.Register(r6_14.GetData(), r0_14.efxdiff[1], r0_14.efxdiff[2], "data/game")
    r0_14.slip_root:insert(anime.GetSprite(r9_14))
    anime.Loop(r9_14, true)
    anime.Show(r9_14, true)
    r0_14.stan_efx = r9_14
    sound.PlaySE(22, 19)
  end
end
local function r31_0(r0_15, r1_15, r2_15, r3_15)
  -- line: [618, 668] id: 15
  if game ~= nil and game.IsNotPauseTypeNone() and game.GetPauseType() ~= r1_0.PauseTypeStopClock then
    return true
  end
  local r4_15 = r1_15.burn_time - r2_15
  local r5_15 = r1_15.hitpoint - r1_15.burn_damage
  if r1_15.func.damage then
    r1_15.func.damage(r1_15)
  end
  if r5_15 <= 0 then
    r5_15 = 0
  end
  r1_15.htptms = 0
  r22_0(r1_15)
  r1_15.hitpoint = r5_15
  r1_15.burn_time = r4_15
  if r5_15 <= 0 then
    if r1_15.burn_efx then
      anime.Remove(r1_15.burn_efx)
      r1_15.burn_efx = nil
    end
    if r1_15.hard then
      sound.PlaySE(26, 16)
    else
      sound.PlaySE(27, 17)
    end
    r1_15.living = false
    r27_0(r1_15, r3_15)
    r1_15.burn_ev = nil
    return false
  end
  if r4_15 <= 0 then
    anime.Remove(r1_15.burn_efx)
    r1_15.burn_efx = nil
    r1_15.burn_ev = nil
    return false
  end
  return true
end
local function r32_0(r0_16, r1_16, r2_16, r3_16, r4_16)
  -- line: [671, 695] id: 16
  local r5_16 = r4_16.move_vect
  local r6_16 = r0_16.xScale
  local r7_16 = r2_16.xy[1]
  local r9_16 = r3_16.pos[r1_16][1]
  r0_16.y = r2_16.xy[2] + r3_16.pos[r1_16][2]
  if r5_16 < 0 and 0 < r6_16 then
    r0_16.x = r7_16 - r9_16
    r0_16.xScale = -r6_16
  else
    r0_16.x = r7_16 + r9_16
  end
  if r4_16.type == 15 then
    local r12_16 = r0_16.rotation
    if r5_16 < 0 then
      r0_16.rotation = 360 - r12_16
    end
  end
end
local function r33_0(r0_17, r1_17, r2_17)
  -- line: [697, 737] id: 17
  if r0_17 == nil or r0_17.attr == 1 or r0_17.attr == 4 or r0_17.living == false then
    return 
  end
  local r3_17 = r0_17.sx
  local r4_17 = r0_17.sy
  if r0_17.burn_efx == nil then
    local r5_17 = nil
    if r0_17.type <= 11 then
      r5_17 = r13_0[1]
    else
      r5_17 = r13_0[2]
    end
    local r8_17 = anime.Register(r5_17.GetData(), r0_17.efxdiff[1], r0_17.efxdiff[2], "data/game")
    r0_17.slip_root:insert(anime.GetSprite(r8_17))
    anime.Loop(r8_17, true)
    anime.Show(r8_17, true)
    r0_17.burn_efx = r8_17
    if not r0_17.not_hook2 then
      anime.RegisterShowHook2(r8_17, r32_0, r0_17)
    end
  end
  sound.PlaySE(21, 18)
  if r0_17.burn_damage < r1_17 then
    r0_17.burn_damage = r1_17
  end
  if r0_17.burn_time < r2_17 then
    r0_17.burn_time = r2_17
  end
  if r0_17.burn_ev == nil then
    r0_17.burn_ev = events.Register(r31_0, r0_17, 1000, false)
  end
end
local function r34_0(r0_18, r1_18, r2_18)
  -- line: [740, 770] id: 18
  if r0_18 == nil or r0_18.living == false then
    return 
  end
  local r3_18 = r0_18.sx
  local r4_18 = r0_18.sy
  if r0_18.burn_efx == nil then
    local r5_18 = nil
    local r6_18 = anime.Register(r13_0[1].GetData(), 40, 40, "data/game")
    r0_18.slip_root:insert(anime.GetSprite(r6_18))
    anime.Loop(r6_18, true)
    anime.Show(r6_18, true)
    r0_18.burn_efx = r6_18
  end
  sound.PlaySE(21, 18)
  if r0_18.burn_damage < r1_18 then
    r0_18.burn_damage = r1_18
  end
  if r0_18.burn_time < r2_18 then
    r0_18.burn_time = r2_18
  end
  if r0_18.burn_ev == nil then
    r0_18.burn_ev = events.Register(r31_0, r0_18, 1000, false)
  end
end
local function r35_0(r0_19, r1_19, r2_19)
  -- line: [772, 808] id: 19
  local r3_19 = r0_19.sx
  local r4_19 = r0_19.sy
  if r0_19.freeze_efx == nil and r0_19.living then
    local r5_19 = nil
    if r0_19.type <= 11 then
      r5_19 = r15_0[1]
    else
      r5_19 = r15_0[3]
    end
    local r8_19 = anime.Register(r5_19.GetData(), r0_19.efxdiff[1], r0_19.efxdiff[2], "data/game")
    r0_19.slip_root:insert(anime.GetSprite(r8_19))
    anime.RegisterFinish(r8_19, function(r0_20, r1_20)
      -- line: [788, 790] id: 20
      anime.SetTimer(r0_20, r0_20.stop)
    end, r0_19)
    anime.Show(r8_19, true)
    r0_19.freeze_efx = r8_19
    local r10_19 = r0_19.speed
    if r0_19.attr == 3 or r0_19.attr == 4 then
      r2_19 = r2_19 * 0.5
    end
    r10_19 = r10_19 * (r2_19 + 100) / 100
    if r0_19.poison_effective.efx == nil and r0_19.poison_effective.type ~= r10_0.PoisonType.Sleep then
      events.SetInterval(r0_19.move, r10_19)
    end
    r0_19.speed = r10_19
    sound.PlaySE(23, 20)
  end
  r0_19.freeze_time = r1_19
end
local function r36_0(r0_21, r1_21, r2_21)
  -- line: [812, 927] id: 21
  local function r3_21(r0_22, r1_22, r2_22, r3_22)
    -- line: [817, 916] id: 22
    if r2_22 <= 0 or r0_22.poison_effective.efx ~= nil or r0_22.poison_effective.params ~= nil or r0_22.living == false then
      return 
    end
    local r4_22 = 500
    local function r5_22(r0_23, r1_23, r2_23)
      -- line: [830, 850] id: 23
      r1_23.sleepTime = r1_23.sleepTime + r2_23
      if r1_23.sleepTime <= r1_23.sleepDurationTime then
        return true
      end
      if r1_23.struct.poison_effective.enabled == false then
        return 
      end
      events.SetInterval(r1_23.struct.move, r1_23.struct.speed)
      events.SetInterval(r1_23.struct.anm.ev, r1_23.struct.poison_effective.params.backAnimeInterval)
      r23_0(r1_23.struct)
      return false
    end
    local function r6_22(r0_24, r1_24, r2_24, r3_24)
      -- line: [854, 889] id: 24
      local r4_24 = r0_24.poison_effective.params
      local r5_24 = events.GetInterval(r0_24.move)
      local r6_24 = events.GetInterval(r0_24.anm.ev)
      r5_24 = r5_24 + r4_24.intervalStep
      r6_24 = r6_24 + r4_24.intervalStep * 3
      r4_24.intervalStep = (r4_22 - r5_24) * r4_24.decelerationCoefficient
      events.SetInterval(r0_24.move, r5_24)
      events.SetInterval(r0_24.anm.ev, r6_24)
      if r0_24.poison_effective.params.sleep == true then
        return 0, 0
      elseif r4_24.timeToSleep <= math.round(r5_24) then
        r0_24.poison_effective.params.sleep = true
        events.SetInterval(r0_24.anm.ev, r4_22 * 2)
        local r7_24 = r0_24.poison_effective.sleepDurationTime
        if r19_0 > 0 then
          r2_22 = r2_22 * r19_0
        end
        r0_24.poison_effective.params.sleepingFunc = events.Register(r5_22, {
          struct = r0_24,
          sleepTime = 0,
          sleepDurationTime = r2_22,
        }, 1)
        return 0, 0
      else
        return r1_24, r2_24
      end
    end
    local r7_22 = nil
    local r10_22 = anime.Register(r17_0[1].GetData(), r0_22.efxdiff[1] + 12, r0_22.efxdiff[2] - 2, "data/game")
    r0_22.slip_root:insert(anime.GetSprite(r10_22))
    anime.Loop(r10_22, true)
    anime.Show(r10_22, true)
    r0_22.poison_effective.enabled = true
    r0_22.poison_effective.efx = r10_22
    r0_22.poison_effective.type = r10_0.PoisonType.Sleep
    r0_22.poison_effective.sleepDurationTime = r2_22
    r0_22.poison_effective.move_func = r6_22
    r0_22.poison_effective.params = {}
    r0_22.poison_effective.params.backAnimeInterval = events.GetInterval(r0_22.anm.ev)
    r0_22.poison_effective.params.decelerationCoefficient = 1 - r1_22
    r0_22.poison_effective.params.intervalStep = (r4_22 - events.GetInterval(r0_22.move)) * r0_22.poison_effective.params.decelerationCoefficient
    r0_22.poison_effective.params.timeToSleep = r3_22
  end
  if r1_21 == r10_0.PoisonType.Sleep then
    r3_21(r0_21, r2_21.decelerationCoefficient, r2_21.sleepDurationTime, r2_21.timeToSleep)
  end
  r0_21.poison_effective.durationTime = r2_21.sleepDurationTime
end
local function r37_0(r0_25, r1_25, r2_25)
  -- line: [929, 951] id: 25
  if r0_25.snipe_target_spr then
    if r0_0.SnipeTargetSpr then
      anime.Remove(r0_0.SnipeTargetSpr)
      r0_0.SnipeTargetSpr = nil
    end
    char.PlaySound(r0_0.AmberStruct, 10)
    r0_25.snipe_target_spr = nil
  end
  game.DeleteHitpoint(r0_25.stone_damage)
  r24_0(r0_25)
  local r3_25 = anime.Register(r11_0.GetData(), r1_25, r2_25, "data/game")
  _G.MissleRoot:insert(anime.GetSprite(r3_25))
  anime.RegisterFinish(r3_25, function(r0_26, r1_26)
    -- line: [947, 949] id: 26
    anime.Remove(r1_26)
  end, r3_25)
  anime.Show(r3_25, true)
end
local function r38_0(r0_27, r1_27, r2_27)
  -- line: [953, 976] id: 27
  if game ~= nil and game.IsNotPauseTypeNone() and game.GetPauseType() ~= r1_0.PauseTypeStopClock then
    return true
  end
  local r3_27 = r1_27.struct
  local r5_27 = r1_27.ed
  local r6_27 = r3_27.anm
  local r4_27 = r1_27.st + r2_27
  r1_27.st = r4_27
  if r5_27 < r4_27 then
    r37_0(r3_27, r1_27.end_x, r1_27.end_y)
    r3_27.dive_anm = nil
    return false
  else
    anime.Move(r6_27, r1_27.sx, easing.linear(r4_27, r5_27, r1_27.sy, r1_27.ly), {
      scale = easing.linear(r4_27, r5_27, r1_27.sScale, r1_27.eScale),
    })
    return true
  end
end
local function r39_0(r0_28, r1_28, r2_28)
  -- line: [979, 995] id: 28
  local r5_28 = false
  local r6_28 = not r0_28.attack[2]
  for r10_28, r11_28 in pairs(_G.Guardians) do
    if r6_28 and 0 < r11_28.data.hp then
      local r3_28 = r11_28.x
      local r4_28 = r11_28.y
      if (r3_28 - r1_28) * (r3_28 - r1_28) + (r4_28 - r2_28) * (r4_28 - r2_28) < 1600 then
        r5_28 = r11_28.func.shoot(r11_28, r0_28)
        break
      end
    end
  end
  return r5_28
end
local function r40_0()
  -- line: [998, 1001] id: 29
  r2_0 = math.random(_G.EnemyTotalCnt)
end
local function r41_0(r0_30, r1_30, r2_30)
  -- line: [1003, 1187] id: 30
  if r2_30 < 1 then
    return true
  end
  if r1_30.hitpoint <= 0 then
    return true
  end
  local r3_30 = false
  local r4_30 = r1_30.speed
  if r1_30.stan_flag then
    local r5_30 = r1_30.stan_time - r4_30 * r2_30
    if r5_30 < 0 then
      r5_30 = 0
      r1_30.stan_flag = false
      if r1_30.stan_efx then
        anime.Remove(r1_30.stan_efx)
        r1_30.stan_efx = nil
      end
    end
    r1_30.stan_time = r5_30
    r3_30 = true
  end
  local r5_30 = r1_30.sx
  local r6_30 = r1_30.sy
  if r1_30.freeze_time > 0 then
    local r7_30 = r1_30.freeze_time - r4_30 * r2_30
    if r7_30 <= 0 then
      r7_30 = 0
      local r8_30 = r1_30.freeze_efx
      if r8_30 then
        anime.Remove(r8_30)
      end
      local r9_30 = nil
      if r1_30.type <= 11 then
        r9_30 = r15_0[2]
      else
        r9_30 = r15_0[4]
      end
      local r10_30 = anime.Register(r9_30.GetData(), 0, 0, "data/game")
      r1_30.slip_root:insert(anime.GetSprite(r10_30))
      anime.RegisterFinish(r10_30, function(r0_31, r1_31)
        -- line: [1052, 1060] id: 31
        anime.Remove(r0_31)
        r1_31.freeze_efx = nil
        if r1_30.poison_effective.efx == nil and r1_30.poison_effective.type ~= r10_0.PoisonType.Sleep then
          events.SetInterval(r1_31.move, r1_31.org_speed)
        end
      end, r1_30)
      anime.Show(r10_30, true)
      r1_30.freeze_efx = r10_30
      r1_30.speed = r1_30.org_speed
    end
    r1_30.freeze_time = r7_30
  end
  if r1_30.cantmove or r3_30 then
    return true
  end
  local function r7_30(r0_32, r1_32, r2_32, r3_32)
    -- line: [1072, 1082] id: 32
    local r4_32 = r2_32 - r0_32
    local r5_32 = r3_32 - r1_32
    if r4_32 ~= 0 then
      if r4_32 > 0 then
        r4_32 = 1
      else
        r4_32 = -1
      end
    end
    if r5_32 ~= 0 then
      if r5_32 > 0 then
        r5_32 = 1
      else
        r5_32 = -1
      end
    end
    return r4_32, r5_32
  end
  local r8_30, r9_30 = r7_30(r5_30, r6_30, r1_30.ex, r1_30.ey)
  local r10_30 = r1_30.func.move
  local r11_30 = r1_30.move_index
  local r12_30 = r1_30.sight[1]
  local r13_30 = r1_30.sight[2]
  for r17_30 = 1, r2_30, 1 do
    if r1_30.poison_effective.enabled == true and r1_30.poison_effective.move_func ~= nil and r1_30.poison_effective.type == r10_0.PoisonType.Sleep then
      r8_30, r9_30 = r1_30.poison_effective.move_func(r1_30, r8_30, r9_30, r2_30)
    end
    if r10_30 then
      local r18_30 = r10_30(r1_30, r8_30, r9_30)
      r5_30 = r5_30 + r18_30[1]
      r6_30 = r6_30 + r18_30[2]
      if r11_30 and (r18_30[1] ~= 0 or r18_30[2] ~= 0) then
        r11_30 = r11_30 + 1
      end
    else
      r5_30 = r5_30 + r8_30
      r6_30 = r6_30 + r9_30
    end
    if r39_0(r1_30, r5_30 + r12_30, r6_30 + r13_30) then
      break
    elseif r5_30 == r1_30.ex and r6_30 == r1_30.ey then
      local r18_30 = r1_30.index + 1
      local r19_30 = r1_30.route[r18_30]
      if r19_30[1] == -1 and r19_30[2] == -1 then
        if r1_30.flying and r1_30.dive_anm == nil then
          r1_30.living = false
          r1_30.dive_anm = events.Register(r38_0, {
            struct = r1_30,
            ed = 500,
            st = 0,
            sx = 0,
            sy = 0,
            ey = 80,
            ly = 80,
            sScale = 1,
            eScale = -0.5,
            end_x = r5_30,
            end_y = r6_30 + 80,
          }, 1)
        else
          r37_0(r1_30, r5_30, r6_30)
        end
        return false
      else
        r1_30.ex = r5_30 + r19_30[1]
        r1_30.ey = r6_30 + r19_30[2]
        r8_30, r9_30 = r7_30(r5_30, r6_30, r1_30.ex, r1_30.ey)
      end
      r1_30.index = r18_30
    end
  end
  if r8_30 < 0 then
    r1_30.move_vect = -1
  elseif r8_30 > 0 then
    r1_30.move_vect = 1
  end
  r1_30.sx = r5_30
  r1_30.sy = r6_30
  r1_30.sort_z = r5_30 + r6_30 * 1000
  r1_30.move_index = r11_30
  r1_30.sort_sprite.x = r5_30
  r1_30.sort_sprite.y = r6_30
  if r1_30.hit_anm then
    anime.Move(r1_30.hit_anm, r5_30, r6_30)
  end
  if r1_30.target_spr then
    anime.Move(r1_30.target_spr, r5_30 + r1_30.targetpos[1], r6_30 + r1_30.targetpos[2])
  end
  if r1_30.snipe_target_spr then
    anime.Move(r1_30.snipe_target_spr, r5_30 + r1_30.targetpos[1], r6_30 + r1_30.targetpos[2] + 60)
  end
  if r1_30.htptspr then
    if r1_30.htptms > 2000 then
      display.remove(r1_30.htptspr)
      r1_30.htptspr = nil
    else
      r1_30.htptms = r1_30.htptms + _G.VsyncTime
    end
  end
  if r1_30.shadow then
    r1_30.shadow.x = r5_30
    r1_30.shadow.y = r6_30 + 80
  end
  return true
end
local function r42_0()
  -- line: [1189, 1206] id: 33
  local r0_33 = {}
  for r4_33, r5_33 in pairs(_G.Enemys) do
    table.insert(r0_33, {
      z = r5_33.sort_z,
      spr = r5_33.sort_sprite,
    })
  end
  for r4_33, r5_33 in pairs(_G.CrashObject) do
    if 0 < r5_33.hitpoint or r5_33.htptspr then
      table.insert(r0_33, {
        z = r5_33.sort_z,
        spr = r5_33.sort_sprite,
      })
    end
  end
  char.InsertSortData(r0_33)
  game.GoalSort(r0_33)
  table.sort(r0_33, function(r0_34, r1_34)
    -- line: [1202, 1202] id: 34
    return r0_34.z < r1_34.z
  end)
  for r4_33, r5_33 in ipairs(r0_33) do
    r5_33.spr:toFront()
  end
end
local function r43_0()
  -- line: [1208, 1208] id: 35
  r9_0 = 1
end
local function r47_0(r0_39, r1_39)
  -- line: [1328, 1350] id: 39
  if r1_39.phase == "ended" then
    local r3_39 = r0_39.id
    if r0_0.IsSnipeMode then
      char.SetSnipeTarget(r3_39)
    else
      char.SetNextTarget(r3_39)
    end
    char.SnipeModeOff()
    char.ClearAllCircle(true)
    char.SetUseOrbMode(r0_0.UseOrbModeReset, r0_0.UseOrbModePlayStatusPlay)
    return true
  end
end
local function r48_0(r0_40)
  -- line: [1353, 1359] id: 40
  if r0_40 == 7 or r0_40 == 8 or r0_40 == 9 or r0_40 == 14 or r0_40 == 21 or r0_40 == 26 or r0_40 == 27 or r0_40 == 28 then
    return false
  end
  return true
end
local function r49_0(r0_41)
  -- line: [1361, 1445] id: 41
  local r1_41 = {
    uid = r9_0,
    type = r0_41,
    root = _G.MyRoot,
    slip_root = nil,
    hit_root = nil,
    hp_root = nil,
    target_spr = nil,
    sx = 0,
    sy = 0,
    ex = 0,
    ey = 0,
    speed = 0,
    attr = 0,
    sight = {
      0,
      0
    },
    ptdiff = {
      0,
      0
    },
    efxdiff = {
      0,
      0
    },
    org_speed = 0,
    anm = nil,
    sprite = nil,
    index = 1,
    move = nil,
    move_vect = 1,
    living = true,
    func = {
      hit = r29_0,
      stan = r30_0,
      burn = r33_0,
      freeze = r35_0,
      poison = r36_0,
      damage = nil,
      burst = nil,
      move = nil,
      burst_finish = r25_0,
      destructor = r24_0,
    },
    hit_anm = nil,
    stan_flag = false,
    stan_time = 0,
    stan_efx = nil,
    burst_anm = nil,
    burn_efx = nil,
    burn_ev = nil,
    burn_damage = 0,
    burn_time = 0,
    burn_cnt = 0,
    freeze_efx = nil,
    freeze_time = 0,
    poison_effective = {
      enabled = false,
      efx = nil,
      ev = nil,
      move_func = nil,
      params = nil,
      durationTime = 0,
    },
    maxhtpt = 0,
    hitpoint = 0,
    htptms = 0,
    htptspr = nil,
    attack = {
      true,
      false
    },
    addmp = 0,
    score = 0,
    route = nil,
    lyra = {
      {
        0,
        0,
        0,
        0,
        0
      },
      {
        0,
        0,
        0,
        0,
        0
      }
    },
    cantmove = false,
    htptpos = {
      -24,
      -56
    },
    targetpos = {
      0,
      -80
    },
    hard = true,
    stone_damage = 1,
    shadow = nil,
    flying = false,
    dive_anm = nil,
    sort_z = nil,
    sort_sprite = nil,
    move_index = nil,
    not_hoook2 = false,
  }
  r9_0 = r9_0 + 1
  return r1_41
end
return {
  DropTreasureboxEnemy = r2_0,
  PopEnemyNum = r3_0,
  DropFlareNum = r4_0,
  DropRewindNum = r5_0,
  DropRecoverNum = r6_0,
  GetBurstFuncFinish = function()
    -- line: [252, 254] id: 6
    return r25_0
  end,
  RegistBomb = r28_0,
  InitEnemyUID = r43_0,
  Init = function()
    -- line: [1210, 1218] id: 36
    _G.Enemys = {}
    r43_0()
    r19_0 = 0
    preload.Load(r18_0, "data/game")
    events.RegisterPostProc(r42_0)
  end,
  Cleanup = function()
    -- line: [1220, 1253] id: 37
    events.DeletePostProc(r42_0)
    if _G.Enemys then
      for r3_37, r4_37 in pairs(_G.Enemys) do
        r24_0(r4_37, true)
      end
      _G.Enemys = nil
    end
    local r0_37 = {}
    local r1_37 = nil
    for r5_37 = 1, 32, 1 do
      table.insert(r0_37, string.format("enemy.enemy%02d", r5_37))
    end
    table.insert(r0_37, "efx.fx_chakudan")
    table.insert(r0_37, "efx.fx_gekiha")
    table.insert(r0_37, "efx.burn01")
    table.insert(r0_37, "efx.burn02")
    table.insert(r0_37, "efx.electric_shock01")
    table.insert(r0_37, "efx.electric_shock02")
    table.insert(r0_37, "efx.freezing01")
    table.insert(r0_37, "efx.freezing02")
    table.insert(r0_37, "efx.freezing03")
    table.insert(r0_37, "efx.freezing04")
    for r5_37, r6_37 in pairs(r0_37) do
      if package.loaded[r6_37] then
        package.loaded[r6_37] = nil
      end
    end
  end,
  Load = function()
    -- line: [1255, 1326] id: 38
    local r0_38 = _G.MapSelect
    local r1_38 = _G.StageSelect
    local r2_38 = db.GetWaveData()
    if r2_38 == nil then
      DebugPrint(string.format("not found wave data(%d,%d)", r0_38, r1_38))
      return 
    end
    _G.EnemyPop = {}
    local r3_38 = nil
    local r4_38 = nil
    for r8_38, r9_38 in pairs(r2_38.wave) do
      local r10_38 = {}
      for r14_38, r15_38 in pairs(r9_38) do
        if false then
          r15_38[2] = 1
          r15_38[3] = 1
          r15_38[4] = 50
        end
        r3_38 = r15_38[2]
        table.insert(r10_38, {
          wait = r15_38[1],
          enemy = r3_38,
          nr = r15_38[3],
          interval = r15_38[4],
          bonus = r15_38[5],
        })
        r4_38 = string.format("enemy.enemy%02d", r3_38)
        if package.loaded[r4_38] == nil then
          require(r4_38)
          package.loaded[r4_38].PreLoad()
        end
        if r3_38 == 10 or r3_38 == 32 then
          r4_38 = "enemy.enemy11"
          if package.loaded[r4_38] == nil then
            require(r4_38)
            package.loaded[r4_38].PreLoad()
          end
          if r3_38 == 32 then
            r4_38 = "enemy.enemy10"
            if package.loaded[r4_38] == nil then
              require(r4_38)
              package.loaded[r4_38].PreLoad()
            end
          end
        end
      end
      table.insert(_G.EnemyPop, r10_38)
    end
    local r5_38 = 0
    for r9_38, r10_38 in pairs(_G.EnemyPop) do
      for r14_38, r15_38 in pairs(r10_38) do
        r5_38 = r5_38 + r15_38.nr
      end
    end
    _G.EnemyTotalCnt = r5_38
    _G.EnemyCnt = _G.EnemyTotalCnt
    if r2_0 == nil then
      r40_0()
    end
    return r2_38.wave_nr
  end,
  MakeEnemyStruct = r49_0,
  PopEnemy = function(r0_42, r1_42)
    -- line: [1447, 1528] id: 42
    local r2_42 = _G.EnemyStatus[r0_42]
    local r3_42 = nil
    local r4_42 = nil
    if r48_0(r0_42) then
      r3_42 = _G.EnemyStart[1] * 80 + 40
      r4_42 = _G.EnemyStart[2] * 80 + 40
    else
      r3_42 = _G.SkyStart[1] * 80 + 40
      r4_42 = _G.SkyStart[2] * 80 + 40 - 80
    end
    r3_0 = r3_0 + 1
    local r5_42 = false
    if r2_0 ~= nil and r2_0 == r3_0 then
      r5_42 = true
    end
    local r6_42 = r49_0(r0_42)
    r6_42.speed = r2_42.speed
    r6_42.attr = r2_42.attr
    r6_42.org_speed = r2_42.speed
    r6_42.maxhtpt = math.ceil(r2_42.hp * _G.EnemyScale)
    r6_42.addmp = r2_42.mp
    r6_42.score = r2_42.score
    r6_42.hitpoint = r6_42.maxhtpt
    r6_42.sx = r3_42
    r6_42.sy = r4_42
    r6_42.treasurebox = r5_42
    local r7_42 = string.format("enemy.enemy%02d", r0_42)
    assert(package.loaded[r7_42], r7_42)
    r6_42.route = _G.EnemyRoute
    package.loaded[r7_42].Pop(r6_42)
    if not r6_42.not_hook2 then
      anime.RegisterShowHook2(r6_42.anm, r32_0, r6_42)
    end
    r6_42.ex = r6_42.sx + r6_42.route[1][1]
    r6_42.ey = r6_42.sy + r6_42.route[1][2]
    if r1_42 then
      for r11_42, r12_42 in pairs(r1_42) do
        r6_42[r11_42] = r12_42
      end
    end
    r6_42.org_speed = r6_42.speed
    r6_42.move = events.Register(r41_0, r6_42, r6_42.speed, false)
    r6_42.sprite.touch = r47_0
    r6_42.sprite.id = r6_42
    r6_42.sprite:addEventListener("touch", r6_42.sprite)
    if r2_42.stone_damage ~= 0 then
      r6_42.stone_damage = r2_42.stone_damage
    end
    table.insert(_G.Enemys, r6_42)
    r6_42.root:insert(r6_42.sort_sprite)
    local r8_42 = nil
    local r9_42 = nil
    r9_42 = r6_42.sort_sprite
    r9_42.x = r3_42
    r9_42.y = r4_42
    r8_42 = display.newGroup()
    r9_42:insert(r8_42)
    r6_42.slip_root = r8_42
    r8_42 = display.newGroup()
    r9_42:insert(r8_42)
    r6_42.hit_root = r8_42
    r8_42 = display.newGroup()
    r9_42:insert(r8_42)
    r6_42.hp_root = r8_42
    return true
  end,
  HitObject = function(r0_43, r1_43)
    -- line: [1530, 1532] id: 43
    r29_0(r0_43, r1_43)
  end,
  BurnObject = function(r0_44, r1_44, r2_44)
    -- line: [1534, 1536] id: 44
    r34_0(r0_44, r1_44, r2_44)
  end,
  FreezeObject = function(r0_45, r1_45, r2_45)
    -- line: [1538, 1540] id: 45
  end,
  StanObject = function(r0_46, r1_46, r2_46)
    -- line: [1542, 1545] id: 46
    r29_0(r0_46, r1_46)
  end,
  AttackPowerup = function(r0_47)
    -- line: [1548, 1550] id: 47
    r19_0 = (r0_47 + 100) / 100
  end,
  GetAttackPower = function()
    -- line: [1552, 1554] id: 48
    return r19_0 * 100 - 100
  end,
  GetDamagePower = function()
    -- line: [1556, 1558] id: 49
    return r19_0
  end,
  GetBurnEfx = function(r0_50)
    -- line: [1560, 1562] id: 50
    return r13_0[r0_50].GetData()
  end,
  GetBurstEfx = function()
    -- line: [1564, 1564] id: 51
    return r12_0
  end,
  GetBomEfx = function()
    -- line: [1566, 1566] id: 52
    return r16_0
  end,
}
