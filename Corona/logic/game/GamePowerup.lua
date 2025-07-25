-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("logic.game.GameStatus")
local r1_0 = require("logic.game.BaseGame")
local r2_0 = require("ui.powerup")
local r3_0 = require("logic.pay_item_data")
local function r4_0(r0_1)
  -- line: [8, 8] id: 1
  return "data/game/effect/" .. r0_1 .. ".png"
end
local function r5_0(r0_2)
  -- line: [10, 10] id: 2
  return r4_0(r0_2 .. _G.UILanguage)
end
local function r6_0(r0_3, r1_3)
  -- line: [13, 60] id: 3
  local r2_3 = r0_3.spr
  local r3_3 = nil
  local r4_3 = nil
  local r5_3 = nil
  local r6_3 = nil
  if r1_3 < 33 then
    r3_3 = 0.01
    r4_3 = 8
  elseif 33 <= r1_3 and r1_3 < 35 then
    r5_3 = r1_3 - 33
    r3_3 = easing.linear(r5_3, 2, 0, 1)
    r4_3 = easing.linear(r5_3, 2, 8, -4)
  elseif 35 <= r1_3 and r1_3 < 36 then
    r3_3 = easing.linear(r1_3 - 35, 1, 1, 3)
    r4_3 = 4
  elseif 36 <= r1_3 and r1_3 < 38 then
    r3_3 = 4
    r4_3 = easing.linear(r1_3 - 36, 2, 4, -3)
  elseif 38 <= r1_3 and r1_3 < 45 then
    r5_3 = r1_3 - 38
    r3_3 = easing.linear(r5_3, 7, 4, 4)
    r4_3 = easing.linear(r5_3, 7, 1, -1)
  else
    r3_3 = 0.01
    r4_3 = 0.01
  end
  if r1_3 < 33 then
    r6_3 = 0
  elseif 33 <= r1_3 and r1_3 < 35 then
    r6_3 = easing.linear(r1_3 - 33, 2, 0, 1)
  elseif 35 <= r1_3 and r1_3 < 43 then
    r6_3 = 1
  elseif 43 <= r1_3 and r1_3 < 45 then
    r6_3 = easing.linear(r1_3 - 43, 2, 1, -1)
  else
    r6_3 = 0
  end
  if r3_3 < 0.01 then
    r3_3 = 0.01
  end
  if r4_3 < 0.01 then
    r4_3 = 0.01
  end
  r2_3.xScale = r3_3
  r2_3.yScale = r4_3
  r2_3.alpha = r6_3
end
local function r7_0(r0_4, r1_4)
  -- line: [63, 69] id: 4
  if r0_4.flag then
    return 
  end
  if r1_4 >= 35 then
    sound.PlaySE(5, 21, true)
    r0_4.flag = true
  end
end
local function r8_0(r0_5, r1_5)
  -- line: [71, 97] id: 5
  local r2_5 = r0_5.spr
  local r3_5 = nil
  local r4_5 = nil
  local r5_5 = r0_5.params
  local r6_5 = nil
  if r1_5 < 23 then
    r3_5 = r5_5[1]
    r4_5 = 0
  elseif 23 <= r1_5 and r1_5 < 30 then
    r6_5 = r1_5 - 23
    r3_5 = easing.outExpo(r6_5, 7, r5_5[1], r5_5[2] - r5_5[1])
    r4_5 = easing.linear(r6_5, 7, 0, 1)
  elseif 30 <= r1_5 and r1_5 < 50 then
    r3_5 = easing.linear(r1_5 - 30, 20, r5_5[2], r5_5[3] - r5_5[2])
    r4_5 = 1
  elseif 50 <= r1_5 and r1_5 < 57 then
    r6_5 = r1_5 - 50
    r3_5 = easing.inExpo(r6_5, 7, r5_5[3], r5_5[4] - r5_5[3])
    r4_5 = easing.linear(r6_5, 7, 1, -1)
  else
    r3_5 = r5_5[4]
    r4_5 = 0
  end
  r2_5.x = r3_5
  r2_5.alpha = r4_5
end
local function r9_0(r0_6, r1_6)
  -- line: [101, 127] id: 6
  local r2_6 = r0_6.spr
  local r3_6 = nil
  local r4_6 = nil
  local r5_6 = r0_6.params
  local r6_6 = nil
  if r1_6 < 13 then
    r3_6 = r5_6[1]
    r4_6 = 0
  elseif 13 <= r1_6 and r1_6 < 20 then
    r6_6 = r1_6 - 13
    r3_6 = easing.outExpo(r6_6, 7, r5_6[1], r5_6[2] - r5_6[1])
    r4_6 = easing.linear(r6_6, 7, 0, 1)
  elseif 20 <= r1_6 and r1_6 < 50 then
    r3_6 = easing.linear(r1_6 - 20, 30, r5_6[2], r5_6[3] - r5_6[2])
    r4_6 = 1
  elseif 50 <= r1_6 and r1_6 < 57 then
    r6_6 = r1_6 - 50
    r3_6 = easing.inExpo(r6_6, 7, r5_6[3], r5_6[4] - r5_6[3])
    r4_6 = easing.linear(r6_6, 7, 1, -1)
  else
    r3_6 = r5_6[4]
    r4_6 = 0
  end
  r2_6.x = r3_6
  r2_6.alpha = r4_6
end
local function r10_0(r0_7, r1_7)
  -- line: [130, 143] id: 7
  local r2_7 = r0_7.spr
  local r3_7 = nil
  if r1_7 < 10 then
    r3_7 = 0
  elseif 10 <= r1_7 and r1_7 < 20 then
    r3_7 = easing.linear(r1_7 - 10, 10, 0, 1)
  elseif 20 <= r1_7 and r1_7 < 50 then
    r3_7 = 1
  elseif r1_7 >= 50 then
    r3_7 = easing.linear(r1_7 - 50, 10, 1, -1)
  end
  r2_7.alpha = r3_7
end
local function r11_0(r0_8, r1_8)
  -- line: [146, 170] id: 8
  local r2_8 = r0_8.spr
  local r3_8 = nil
  local r4_8 = nil
  local r5_8 = nil
  if r1_8 < 10 then
    r3_8 = 0.5
    r4_8 = 0
    r5_8 = 0
  elseif 10 <= r1_8 and r1_8 < 20 then
    r3_8 = easing.linear(r1_8 - 10, 10, 0.5, 0.5)
    r4_8 = easing.linear(r1_8 - 10, 50, 0, 120)
    r5_8 = easing.linear(r1_8 - 10, 10, 0, 0.33)
  elseif 20 <= r1_8 and r1_8 < 50 then
    r3_8 = 1
    r4_8 = easing.linear(r1_8 - 10, 50, 0, 120)
    r5_8 = 0.33
  else
    r3_8 = easing.linear(r1_8 - 50, 10, 1, -0.5)
    r4_8 = easing.linear(r1_8 - 10, 50, 0, 120)
    r5_8 = easing.linear(r1_8 - 50, 10, 0.33, -0.33)
  end
  r2_8.xScale = r3_8
  r2_8.yScale = r3_8
  r2_8.rotation = r4_8
  r2_8.alpha = r5_8
end
local function r12_0(r0_9, r1_9)
  -- line: [173, 184] id: 9
  local r2_9 = r0_9.spr
  local r3_9 = nil
  if r1_9 < 10 then
    r3_9 = easing.linear(r1_9, 10, 0, 0.5)
  elseif 10 <= r1_9 and r1_9 < 50 then
    r3_9 = 0.5
  elseif r1_9 >= 50 then
    r3_9 = easing.linear(r1_9 - 50, 10, 0.5, -0.5)
  end
  r2_9.alpha = r3_9
end
local function r13_0(r0_10, r1_10, r2_10, r3_10)
  -- line: [187, 196] id: 10
  local r4_10 = r0_10 / r1_10
  local r5_10 = nil
  if r4_10 < 0.5 then
    r5_10 = easing.linear(r4_10, 0.5, 0.2, 0.8)
  else
    r5_10 = easing.linear(r4_10 - 0.5, 0.5, 1, -0.8)
  end
  return r5_10
end
local function r14_0()
  -- line: [198, 216] id: 11
  if r0_0.PowerupMark then
    return 
  end
  r0_0.PowerupMark = {}
  local r0_11 = display.newImage(_G.PowerupRoot, r5_0("mighty04"), 0, 0, true)
  r0_11:setReferencePoint(display.CenterReferencePoint)
  r0_11.x = 480
  r0_11.y = 620
  r0_11.alpha = 0.2
  r0_0.PowerupMark.spr = r0_11
  r0_0.PowerupMark.tween = transit.Register(r0_11, {
    time = 2000,
    alpha = 0.2,
    transition = r13_0,
    loop = true,
  })
end
local function r15_0()
  -- line: [219, 326] id: 12
  local r0_12 = display.newGroup()
  local r1_12 = _G.UILanguage
  local r2_12 = nil
  local r3_12 = nil
  local r4_12 = nil
  r2_12 = {
    rtImg = r0_12,
    sprites = {},
    ms = 0,
    ed = 2000,
    ev = nil,
  }
  r3_12 = display.newRect(r0_12, 0, 0, _G.Width, _G.Height)
  r3_12:setFillColor(0, 0, 0)
  function r3_12.touch(r0_13)
    -- line: [234, 234] id: 13
    return true
  end
  r3_12:addEventListener("touch")
  r3_12.alpha = 0
  table.insert(r2_12.sprites, {
    spr = r3_12,
    func = r12_0,
  })
  r3_12 = display.newImage(r0_12, "data/game/circle_range.png", 0, 0, true)
  r3_12:setReferencePoint(display.CenterReferencePoint)
  r3_12.x = 480
  r3_12.y = 320
  r3_12.xScale = 0.5
  r3_12.yScale = 0.5
  r3_12.rotation = 0
  r3_12.alpha = 0
  table.insert(r2_12.sprites, {
    spr = r3_12,
    func = r11_0,
  })
  r3_12 = display.newImage(r0_12, r4_0("mighty03"), 0, 0, true)
  r3_12:setReferencePoint(display.CenterReferencePoint)
  r3_12.x = 480
  r3_12.y = 320
  r3_12.alpha = 0
  table.insert(r2_12.sprites, {
    spr = r3_12,
    func = r10_0,
  })
  r3_12 = display.newImage(r0_12, r5_0("mighty02"), 0, 0, true)
  r3_12:setReferencePoint(display.CenterReferencePoint)
  if r1_12 == "jp" then
    r3_12.x = 1063
    r3_12.y = 325
    r4_12 = {
      1063,
      426,
      396,
      -108
    }
  else
    r3_12.x = 1087
    r3_12.y = 325
    r4_12 = {
      1087,
      354,
      324,
      -130
    }
  end
  r3_12.alpha = 0
  table.insert(r2_12.sprites, {
    spr = r3_12,
    params = r4_12,
    func = r9_0,
  })
  r3_12 = display.newImage(r0_12, r5_0("mighty01"), 0, 0, true)
  r3_12:setReferencePoint(display.CenterReferencePoint)
  if r1_12 == "jp" then
    r3_12.x = -84
    r3_12.y = 325
    r4_12 = {
      -84,
      571,
      591,
      1044
    }
  else
    r3_12.x = -150
    r3_12.y = 325
    r4_12 = {
      -150,
      598,
      618,
      1110
    }
  end
  r3_12.alpha = 0
  table.insert(r2_12.sprites, {
    spr = r3_12,
    params = r4_12,
    func = r8_0,
  })
  r3_12 = display.newImage(r0_12, r4_0("mighty01"), 0, 0, true)
  r3_12:setReferencePoint(display.CenterReferencePoint)
  r3_12.x = 480
  r3_12.y = 320
  r3_12.xScale = 0.01
  r3_12.yScale = 8
  r3_12.alpha = 0
  table.insert(r2_12.sprites, {
    spr = r3_12,
    func = r6_0,
  })
  table.insert(r2_12.sprites, {
    flag = false,
    func = r7_0,
  })
  local function r5_12(r0_14, r1_14, r2_14)
    -- line: [304, 321] id: 14
    local r4_14 = r1_14.ed
    local r3_14 = r1_14.ms + r2_14
    if r4_14 < r3_14 then
      r3_14 = r4_14
    end
    local r5_14 = r3_14 / _G.VsyncTime
    for r9_14, r10_14 in pairs(r1_14.sprites) do
      r10_14.func(r10_14, r5_14)
    end
    if r4_14 <= r3_14 then
      display.remove(r1_14.rtImg)
      r14_0()
      return false
    end
    r1_14.ms = r3_14
    return true
  end
  _G.PowerupRoot:insert(r0_12)
  r2_12.ev = events.Register(r5_12, r2_12, 1)
end
local function r16_0(r0_15, r1_15)
  -- line: [330, 456] id: 15
  if r1_15 == nil then
    r1_15 = true
  end
  for r5_15, r6_15 in pairs(r0_15) do
    local r7_15 = r3_0.getItemData(r6_15)
    if r6_15 == r3_0.pay_item_data.UnlockAttackUnit01 or r6_15 == r3_0.pay_item_data.UnlockBufUnit01 then
      char.SummonPurchase(r6_15 + r7_15[5].summon)
      save.DataPush("unlock", r6_15 + r7_15[5].summon)
    elseif r6_15 == r3_0.pay_item_data.Attck20Up then
      r1_0.set_score_type(r0_0.ScoreTypeDef.Standard)
      enemy.AttackPowerup(r7_15[5].attack)
      save.DataPush("powerup", r7_15[5].attack)
    elseif r6_15 == r3_0.pay_item_data.Attack40Up then
      r1_0.set_score_type(r0_0.ScoreTypeDef.Standard)
      enemy.AttackPowerup(r7_15[5].attack)
      save.DataPush("powerup", r7_15[5].attack)
    elseif r6_15 == r3_0.pay_item_data.Speed10Up then
      r1_0.set_score_type(r0_0.ScoreTypeDef.Standard)
      char.SpeedPowerup(r7_15[5].speed)
      save.DataPush("speedup", r7_15[5].speed)
    elseif r6_15 == r3_0.pay_item_data.Speed20Up then
      r1_0.set_score_type(r0_0.ScoreTypeDef.Standard)
      char.SpeedPowerup(r7_15[5].speed)
      save.DataPush("speedup", r7_15[5].speed)
    elseif r6_15 == r3_0.pay_item_data.Range10Up then
      r1_0.set_score_type(r0_0.ScoreTypeDef.Standard)
      char.RangePowerup(r7_15[5].range)
      save.DataPush("rangeup", r7_15[5].range)
    elseif r6_15 == r3_0.pay_item_data.Range20Up then
      r1_0.set_score_type(r0_0.ScoreTypeDef.Standard)
      char.RangePowerup(r7_15[5].range)
      save.DataPush("rangeup", r7_15[5].range)
    elseif r6_15 == r3_0.pay_item_data.MpPlus150 then
      r1_0.set_score_type(r0_0.ScoreTypeDef.Standard)
      _G.MP = _G.MP + r7_15[5].mp
      save.DataPush("addmp", r7_15[5].mp)
      r0_0.PurchaseMP = r0_0.PurchaseMP + r7_15[5].mp
    elseif r6_15 == r3_0.pay_item_data.MpPlus350 then
      r1_0.set_score_type(r0_0.ScoreTypeDef.Standard)
      _G.MP = _G.MP + r7_15[5].mp
      save.DataPush("addmp", r7_15[5].mp)
      r0_0.PurchaseMP = r0_0.PurchaseMP + r7_15[5].mp
    elseif r6_15 == r3_0.pay_item_data.HpPlus5 then
      r1_0.set_score_type(r0_0.ScoreTypeDef.Standard)
      _G.HP = _G.HP + r7_15[5].hp
      save.DataPush("addhp", r7_15[5].hp)
      r0_0.PurchaseHP = r0_0.PurchaseHP + r7_15[5].hp
    elseif r6_15 == r3_0.pay_item_data.HpPlus10 then
      r1_0.set_score_type(r0_0.ScoreTypeDef.Standard)
      _G.HP = _G.HP + r7_15[5].hp
      save.DataPush("addhp", r7_15[5].hp)
      r0_0.PurchaseHP = r0_0.PurchaseHP + r7_15[5].hp
    elseif r6_15 == r3_0.pay_item_data.SetItem01 then
      r1_0.set_score_type(r0_0.ScoreTypeDef.Standard)
      enemy.AttackPowerup(r7_15[5].attack)
      save.DataPush("powerup", r7_15[5].attack)
      char.SpeedPowerup(r7_15[5].speed)
      save.DataPush("speedup", r7_15[5].speed)
      char.RangePowerup(r7_15[5].range)
      save.DataPush("rangeup", r7_15[5].range)
      if r1_15 then
        r15_0()
      else
        r14_0()
      end
      _G.BingoManager.updateUserBingoData(_G.BingoManagerClass.MISSION_TYPE.USE_BIG_MAGIC(), nil)
    elseif r3_0.pay_item_data.CharItemDaisy <= r6_15 and r6_15 <= r3_0.pay_item_data.CharItemYuiko then
      r1_0.set_score_type(r0_0.ScoreTypeDef.Standard)
      local r8_15 = r3_0.getItemData(r6_15)
      if r8_15 ~= nil and 1 < #r8_15 then
        _G.MP = _G.MP + r8_15[2]
      end
    elseif r3_0.pay_item_data.CharCostDaisyLv02 <= r6_15 and r6_15 <= r3_0.pay_item_data.CharCostYuikoLv04 then
      r1_0.set_score_type(r0_0.ScoreTypeDef.Standard)
      local r8_15 = r3_0.getItemData(r6_15)
      if r8_15 ~= nil and 1 < #r8_15 then
        _G.MP = _G.MP + r8_15[2]
      end
    end
  end
  r1_0.ViewPanel()
end
return {
  process_powerup = r16_0,
  powerup_func = function(r0_16)
    -- line: [460, 481] id: 16
    sound.PlaySE(2)
    r2_0.Open({
      function(r0_17)
        -- line: [464, 471] id: 17
        local r1_17 = r1_0.is_magic()
        r16_0(r0_17)
        r1_0.UpdatePocketCrystal()
        return true
      end,
      function()
        -- line: [473, 476] id: 18
        if _G.IsSuperMP then
          _G.MP = 90000
        end
        return true
      end
    })
    return true
  end,
}
