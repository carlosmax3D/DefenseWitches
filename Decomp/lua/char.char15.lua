-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("logic.char.CharStatus")
local r2_0 = require("common.sprite_loader").new({
  imageSheet = "common.sprites.sprite_number01",
})
local r3_0 = {
  4000,
  8000,
  16000,
  32000,
  99999
}
local r4_0 = {
  1000,
  2000,
  4000,
  8000,
  16000
}
local r5_0 = {
  -12,
  -40,
  -56,
  -68,
  0
}
local r6_0 = {
  -40,
  -60,
  -80,
  -100,
  -100
}
local r7_0 = {
  "flower_fx_0_0",
  "flower_fx_1_0",
  "flower_fx_1_1",
  "flower_fx_1_2",
  "flower_fx_1_3",
  "flower_fx_1_4",
  "flower_fx_1_5",
  "flower_fx_2_0",
  "flower_fx_3_0",
  "flower_fx_3_1",
  "flower_fx_3_2",
  "flower_fx_3_3",
  "flower_fx_3_4",
  "flower_fx_3_5",
  "flower_fx_3_6",
  "flower_fx_3_7",
  "flower_fx_3_8",
  "flower_fx_3_9",
  "flower_fx_3_10",
  "flower_fx_3_11",
  "flower_fx_3_12",
  "flower_fx_3_13",
  "flower_fx_3_14",
  "flower_fx_3_15",
  "flower01_0_0",
  "flower01_1_0",
  "flower02_1_0",
  "flower03_1_0",
  "flower04_1_0",
  "flower05_1_0"
}
local r8_0 = false
local r9_0 = {}
for r13_0 = 1, 5, 1 do
  table.insert(r9_0, require(string.format("char.c15.flower%02d", r13_0)))
end
local r10_0 = require("char.c15.flower_fx")
local r11_0 = "data/game/item/flower"
local function r12_0(r0_1, r1_1, r2_1)
  -- line: [58, 114] id: 1
  if game ~= nil and game.IsNotPauseTypeNone() then
    return true
  end
  if r1_1.levelup_anm then
    return true
  end
  local r3_1 = r1_1.score_ms + r2_1
  r1_1.score_ms = r3_1
  if r3_1 < 3000 then
    return true
  end
  r1_1.score_ms = r3_1 - 3000
  local r4_1 = r1_1.score
  local r5_1 = r1_1.x
  local r6_1 = r1_1.y
  local r7_1 = nil
  local r8_1 = nil
  local r9_1 = nil
  if r1_1.level == 5 then
    r7_1 = 60
  else
    r7_1 = 48
  end
  local r10_1 = r4_1
  local r11_1 = nil
  r9_1 = display.newGroup()
  while 0 < r10_1 do
    r11_1 = r10_1 % 10
    r10_1 = math.floor(r10_1 * 0.1)
    r2_0.CreateImage(r9_1, r2_0.sequenceNames.GetScore, r2_0.frameDefines.GetScoreStart + r11_1, r7_1, 0)
    r7_1 = r7_1 - 12
  end
  r9_1:setReferencePoint(display.CenterReferencePoint)
  r9_1.x = r5_1
  r9_1.y = r6_1 - 40
  r1_1.score_tween = transit.Register(r9_1, {
    alpha = 0.1,
    y = r6_1 - 80,
    time = 500,
    transition = easing.inExpo,
    onComplete = function(r0_2)
      -- line: [104, 110] id: 2
      display.remove(r0_2.sprite)
      transit.Delete(r0_2)
      r1_1.score_tween = nil
      game.AddScoreValue(r4_1)
      game.ViewPanel()
    end,
  })
  return true
end
local function r13_0(r0_3)
  -- line: [116, 154] id: 3
  if r0_3.score_tween then
    transit.Delete(r0_3.score_tween)
    r0_3.score_tween = nil
  end
  if r0_3.hit_anm then
    anime.Remove(r0_3.hit_anm)
    r0_3.hit_anm = nil
  end
  if r0_3.htptev then
    events.Delete(r0_3.htptev)
    r0_3.htptev = nil
  end
  if r0_3.burn_efx then
    anime.Remove(r0_3.burn_efx)
    r0_3.burn_efx = nil
  end
  if r0_3.burn_ev then
    events.Delete(r0_3.burn_ev)
    r0_3.burn_ev = nil
  end
  if r0_3.score_ev then
    events.Delete(r0_3.score_ev)
    r0_3.score_ev = nil
  end
  for r4_3, r5_3 in pairs(r0_3.flower) do
    anime.Remove(r5_3)
  end
  r0_3.anime = nil
  anime.Remove(r0_3.efx)
  r0_3.efx = nil
end
local function r14_0(r0_4)
  -- line: [156, 158] id: 4
  return r0_4.hitpoint, r0_4.score_ms
end
local function r15_0(r0_5, r1_5, r2_5, r3_5)
  -- line: [160, 180] id: 5
  if r1_5 > 1 then
    local r4_5 = nil
    anime.Show(r0_5.anime, false)
    r4_5 = r0_5.flower[r1_5]
    anime.Pause(r4_5, true)
    anime.Show(r4_5, true, {
      timer = r4_5.stop,
    })
    r0_5.maxhtpt = r3_0[r1_5]
    r0_5.hitpoint = 0
    r0_5.levelup_anm = false
    r0_5.htptpos[2] = r5_0[r1_5]
    r0_5.score = r4_0[r1_5]
    r0_5.targetpos[2] = r6_0[r1_5]
    r0_5.level = r1_5
  end
  r0_5.hitpoint = r2_5
  r0_5.score_ms = r3_5
end
local function r16_0(r0_6)
  -- line: [184, 221] id: 6
  local r1_6 = r0_6.htptspr
  if r1_6 then
    display.remove(r1_6)
  end
  r0_6.htptspr = nil
  local r4_6 = display.newGroup()
  local r5_6 = r0_6.hitpoint * 48 / r0_6.maxhtpt
  display.newRect(r4_6, 0, 0, r5_6, 4):setFillColor(0, 149, 255, 191.25)
  local r7_6 = 48 - r5_6
  if r7_6 > 0 then
    display.newRect(r4_6, r5_6, 0, r7_6, 4):setFillColor(27, 38, 38, 191.25)
  end
  r0_6.hp_root:insert(r4_6)
  r4_6.x = r0_6.x + r0_6.htptpos[1]
  r4_6.y = r0_6.y + r0_6.htptpos[2]
  r0_6.htptspr = r4_6
  if r0_6.htptev then
    events.Delete(r0_6.htptev)
    r0_6.htptev = nil
  end
  r0_6.htptev = events.Register(function(r0_7, r1_7, r2_7)
    -- line: [212, 219] id: 7
    if r1_7.htptspr then
      display.remove(r1_7.htptspr)
      r1_7.htptspr = nil
    end
    r1_7.htptev = nil
    return false
  end, r0_6, 2000)
end
local function r17_0(r0_8, r1_8)
  -- line: [224, 234] id: 8
  anime.Show(r1_8.efx, false)
  local r2_8 = r1_8.level
  r1_8.maxhtpt = r3_0[r2_8]
  r1_8.hitpoint = 0
  r1_8.levelup_anm = false
  r1_8.htptpos[2] = r5_0[r2_8]
  r1_8.score = r4_0[r2_8]
  r1_8.targetpos = {
    0,
    r6_0[r2_8]
  }
end
local function r18_0(r0_9, r1_9)
  -- line: [237, 248] id: 9
  local r2_9 = r1_9.level
  local r3_9 = nil
  for r7_9, r8_9 in pairs(r1_9.flower) do
    if r7_9 ~= r2_9 then
      anime.Show(r8_9, false)
    end
  end
  r3_9 = r1_9.flower[r2_9]
  anime.Pause(r3_9, false)
  anime.Show(r3_9, true)
end
local function r19_0(r0_10, r1_10)
  -- line: [251, 254] id: 10
  sound.PlaySE(6, 21, true)
  anime.RegisterTrigger(r0_10, 31, r18_0, r1_10)
end
local function r20_0(r0_11, r1_11)
  -- line: [257, 260] id: 11
  sound.PlaySE(5, 21, true)
  anime.RegisterTrigger(r0_11, 30, r19_0, r1_11)
end
local function r21_0(r0_12)
  -- line: [262, 295] id: 12
  if r0_12.htptev then
    events.Delete(r0_12.htptev)
    r0_12.htptev = nil
  end
  if r0_12.burn_efx then
    anime.Remove(r0_12.burn_efx)
    r0_12.burn_efx = nil
  end
  if r0_12.burn_ev then
    events.Delete(r0_12.burn_ev)
    r0_12.burn_ev = nil
  end
  if r0_12.levelup_anm then
    return 
  end
  r0_12.levelup_anm = true
  r0_12.level = r0_12.level + 1
  if r0_12.htptspr then
    display.remove(r0_12.htptspr)
    r0_12.htptspr = nil
  end
  local r2_12 = r0_12.efx
  anime.Pause(r2_12, false)
  anime.Show(r2_12, true)
  anime.RegisterTrigger(r2_12, 6, r20_0, r0_12)
  anime.RegisterFinish(r2_12, r17_0, r0_12)
end
local function r22_0(r0_13, r1_13, r2_13)
  -- line: [298, 339] id: 13
  if game ~= nil and game.IsNotPauseTypeNone() then
    return true
  end
  local r3_13 = true
  if not r1_13.levelup_anm then
    local r4_13 = r1_13.burn_time - r2_13
    r1_13.burn_time = r4_13
    local r5_13 = r1_13.hitpoint + r1_13.burn_damage
    r1_13.hitpoint = r5_13
    if r1_13.maxhtpt <= r5_13 then
      r21_0(r1_13)
      r3_13 = false
    else
      r16_0(r1_13)
    end
    if r4_13 <= 0 then
      r3_13 = false
    end
  else
    r3_13 = false
  end
  if not r3_13 then
    if r1_13.burn_efx then
      anime.Remove(r1_13.burn_efx)
      r1_13.burn_efx = nil
    end
    r1_13.burn_ev = nil
    return false
  end
  return true
end
local function r23_0(r0_14, r1_14)
  -- line: [342, 353] id: 14
  if r1_14.phase == "ended" then
    local r2_14 = r0_14.id
    if r2_14.levelup_anm then
      return true
    end
    if r2_14.level < 5 then
      char.SetNextTarget(r2_14)
      char.ClearAllCircle()
    end
  end
  return true
end
local r30_0 = require("char.Char")
local function r33_0(r0_25, r1_25, r2_25, r3_25)
  -- line: [552, 638] id: 25
  local r4_25 = r1_25 * 80 - 40
  local r5_25 = r2_25 * 80 - 40
  local r6_25 = enemy.MakeEnemyStruct(-1)
  local r7_25 = r0_0.SummonChar[15]
  assert(r7_25)
  r7_25.Load(r4_25, r5_25, r6_25)
  local r8_25 = display.newGroup()
  r8_25:insert(r6_25.spr)
  r6_25.sort_sprite = r8_25
  r6_25.sort_z = r4_25 + r5_25 * 1000
  local r9_25 = nil
  r9_25 = display.newGroup()
  r8_25:insert(r9_25)
  r6_25.slip_root = r9_25
  r9_25 = display.newGroup()
  r8_25:insert(r9_25)
  r6_25.hit_root = r9_25
  r9_25 = display.newGroup()
  r8_25:insert(r9_25)
  r6_25.hp_root = r9_25
  r6_25.sx = r4_25
  r6_25.sy = r5_25
  r6_25.ex = r4_25
  r6_25.ey = r5_25
  r6_25.func.hit = r7_25.Hit
  r6_25.func.stan = r7_25.Stan
  r6_25.func.burn = r7_25.Burn
  r6_25.func.freeze = r7_25.Slow
  r6_25.func.poison = r7_25.Poison
  r6_25.func.burst = nil
  r6_25.func.burst_finish = nil
  r6_25.x = r4_25
  r6_25.y = r5_25
  r6_25.map_xy = {
    r1_25,
    r2_25
  }
  r6_25.level = 1
  r6_25.func.pause = nil
  r6_25.func.destructor = nil
  r6_25.func.fumble = nil
  r6_25.func.lockon = nil
  function r6_25.func.sound(r0_26, r1_26)
    -- line: [602, 602] id: 26
    return true
  end
  _G.MapLocation[r2_25][r1_25] = r0_25
  if fast then
    _G.MyRoot:insert(r6_25.sort_sprite)
    r6_25.summon = false
    anime.Pause(r6_25.anime, true)
    anime.Show(r6_25.anime, true)
  else
    save.DataPush("pop", {
      id = 15,
      x = r1_25,
      y = r2_25,
    })
    r30_0.register_summon(r6_25)
  end
  table.insert(r0_0.MyChar, r6_25)
  r0_0.SummonPos = nil
  table.insert(_G.Flowers, r6_25)
  if not fast then
    if r0_0.IsUseCrystal == false then
      game.AddMp(-_G.UserStatus[15].cost[1])
    end
    game.ViewPanel()
  end
  r0_0.FlowerCount = r0_0.FlowerCount + 1
  return r6_25
end
return setmetatable({
  Load = function(r0_15, r1_15, r2_15)
    -- line: [356, 406] id: 15
    if not r8_0 then
      preload.Load(r7_0, r11_0)
      r8_0 = true
    end
    local r3_15 = {}
    local r4_15 = display.newGroup()
    local r5_15 = nil
    local r6_15 = nil
    for r10_15 = 1, 5, 1 do
      r5_15 = anime.Register(r9_0[r10_15].GetData(), r0_15, r1_15 + 28, r11_0)
      r4_15:insert(anime.GetSprite(r5_15))
      table.insert(r3_15, r5_15)
    end
    local r7_15 = nil
    r7_15 = anime.Register(r10_0.GetData(), r0_15, r1_15, r11_0)
    r4_15:insert(anime.GetSprite(r7_15))
    local r8_15 = display.newRect(_G.MyTgRoot, r0_15 - 40, r1_15 - 40, 80, 80)
    r8_15.alpha = 0.01
    r8_15.struct = r2_15
    r8_15.touch = r23_0
    r8_15.id = r2_15
    r8_15:addEventListener("touch")
    r2_15.touch_area = r8_15
    r2_15.anime = r3_15[1]
    r2_15.spr = r4_15
    r2_15.flower = r3_15
    r2_15.efx = r7_15
    r2_15.type = 15
    r2_15.maxhtpt = r3_0[1]
    r2_15.hitpoint = 0
    r2_15.levelup_anm = false
    r2_15.htptpos = {
      -24,
      r5_0[1]
    }
    r2_15.targetpos = {
      0,
      r6_0[1]
    }
    r2_15.htptev = nil
    r2_15.score = r4_0[1]
    r2_15.score_ms = 0
    r2_15.score_ev = events.Register(r12_0, r2_15, 1)
    r2_15.func.release = r13_0
    r2_15.func.get_hp = r14_0
    r2_15.func.set_first = r15_0
    return r2_15
  end,
  Cleanup = function()
    -- line: [408, 410] id: 16
    r8_0 = false
  end,
  Touch = r23_0,
  Hit = function(r0_17, r1_17)
    -- line: [414, 447] id: 17
    if r0_17.levelup_anm then
      if r0_17.target_spr then
        anime.Show(r0_17.target_spr, false)
        r0_17.target_spr = nil
      end
      char.ClearNextTarget(r0_17, true)
      return 
    end
    if r0_17.level == 5 then
      return 
    end
    local r2_17 = enemy.GetDamagePower()
    if r2_17 > 0 then
      r1_17 = r1_17 * r2_17
    end
    local r3_17 = r0_17.hitpoint + r1_17
    r0_17.hitpoint = r3_17
    local function r4_17(r0_18, r1_18)
      -- line: [431, 441] id: 18
      anime.Remove(r0_18)
      r1_18.hit_anm = nil
      if r1_18.maxhtpt <= r3_17 then
        r21_0(r1_18)
      else
        r16_0(r1_18)
        sound.PlaySE(25, 15)
      end
    end
    if r0_17.hit_anm == nil then
      r0_17.hit_anm = enemy.RegistBomb(r0_17.sx, r0_17.sy, r4_17, r0_17)
    end
  end,
  Stan = function(r0_19, r1_19, r2_19, r3_19)
    -- line: [450, 487] id: 19
    if r3_19 and r3_19.type == r0_0.CharDef.CharId.Yung then
      if r0_19.levelup_anm then
        if r0_19.target_spr then
          anime.Show(r0_19.target_spr, false)
          r0_19.target_spr = nil
        end
        char.ClearNextTarget(r0_19, true)
        return 
      end
      if r0_19.level == 5 then
        return 
      end
      local r4_19 = enemy.GetDamagePower()
      if r4_19 > 0 then
        r1_19 = r1_19 * r4_19
      end
      local r5_19 = r0_19.hitpoint + r1_19
      r0_19.hitpoint = r5_19
      local function r6_19(r0_20, r1_20)
        -- line: [468, 478] id: 20
        anime.Remove(r0_20)
        r1_20.hit_anm = nil
        if r1_20.maxhtpt <= r5_19 then
          r21_0(r1_20)
        else
          r16_0(r1_20)
          sound.PlaySE(25, 15)
        end
      end
      if r0_19.hit_anm == nil then
        r0_19.hit_anm = enemy.RegistBomb(r0_19.sx, r0_19.sy, r6_19, r0_19)
      end
      -- close: r4_19
    else
      return 
    end
  end,
  Burn = function(r0_21, r1_21, r2_21)
    -- line: [491, 525] id: 21
    if r0_21.levelup_anm then
      if r0_21.target_spr then
        anime.Show(r0_21.target_spr, false)
        r0_21.target_spr = nil
      end
      char.ClearNextTarget(r0_21, true)
      return 
    end
    if r0_21.level == 5 then
      return 
    end
    if r0_21.burn_efx == nil then
      local r6_21 = anime.Register(enemy.GetBurnEfx(1), r0_21.x, r0_21.y + 20, "data/game")
      r0_21.slip_root:insert(anime.GetSprite(r6_21))
      anime.Loop(r6_21, true)
      anime.Pause(r6_21, false)
      anime.Show(r6_21, true)
      r0_21.burn_efx = r6_21
    end
    if r0_21.burn_damage < r1_21 then
      r0_21.burn_damage = r1_21
    end
    if r0_21.burn_time < r2_21 then
      r0_21.burn_time = r2_21
    end
    if r0_21.burn_ev == nil then
      r0_21.burn_ev = events.Register(r22_0, r0_21, 1000)
    end
  end,
  Slow = function(r0_22, r1_22, r2_22)
    -- line: [528, 530] id: 22
  end,
  custom_crystal_status = function(r0_23, r1_23, r2_23)
    -- line: [534, 541] id: 23
    local r3_23 = db.isTutorial()
    if 3 <= r0_0.FlowerCount or r3_23 then
      r0_0.SummonCrystalStatus[r2_23] = 4
    else
      return r30_0.custom_crystal_status(r0_23, r1_23, r2_23)
    end
  end,
  custom_summon_status = function(r0_24, r1_24, r2_24, r3_24)
    -- line: [543, 550] id: 24
    local r4_24 = db.isTutorial()
    if 3 <= r0_0.FlowerCount or r4_24 then
      r0_0.SummonStatus[r2_24] = 4
    else
      return r30_0.custom_summon_status(r0_24, r1_24, r2_24, r3_24)
    end
  end,
  custom_summon = r33_0,
  CustomFirstSummon = function(r0_27, r1_27, r2_27, r3_27, r4_27, r5_27)
    -- line: [640, 647] id: 27
    local r8_27 = r33_0(r0_27, r1_27, r2_27, true)
    r8_27.func.set_first(r8_27, r3_27, r5_27[5], r5_27[6])
  end,
}, {
  __index = r30_0,
})
