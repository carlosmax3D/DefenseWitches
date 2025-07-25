-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("resource.char_define")
local r1_0 = {
  Sp = 1,
  G = 2,
  S = 3,
  B = 4,
  Hp1 = 5,
  Ex1 = 6,
  Ex2 = 7,
  Ex3 = 8,
}
local r2_0 = {
  {
    type = 1,
    value = 60,
  },
  {
    type = 1,
    value = 40,
  },
  {
    type = 1,
    value = 30,
  },
  {
    type = 1,
    value = 20,
  },
  {
    type = 1,
    value = 50,
  }
}
local r3_0 = {
  G = 20,
  S = 15,
  B = 10,
  Hp1 = 1,
}
local r4_0 = {
  Score = 1,
  UseOrb = 2,
  TreasureboxTotal = 3,
  TreasureboxNormal = 4,
  TreasureboxRich = 5,
  Hpxx = 6,
  Levelxx = 7,
  UnitAndEvoxx = 8,
  TwoUnitAndEvoxx = 9,
}
return {
  MedalId = r1_0,
  AchievementReward = r2_0,
  ClearHp = r3_0,
  ExType = r4_0,
  RewardType = {
    Crystal = 1,
    Exp = 2,
  },
  new = function(r0_1)
    -- line: [59, 703] id: 1
    local r1_1 = {}
    local r2_1 = 3
    local r3_1 = {
      Score = 388,
      UseOrb = 389,
      TreasureboxTotal = 390,
      TreasureboxNormal = 391,
      TreasureboxRich = 392,
      Hpxx = 393,
      Levelxx = 394,
      UnitAndEvoxx = 395,
      TwoUnitAndEvoxx = 396,
    }
    local r4_1 = nil
    local r5_1 = nil
    local function r6_1(r0_2, r1_2)
      -- line: [92, 107] id: 2
      if r5_1 == nil or #r5_1 < 1 or r1_2 == nil then
        return false
      end
      if r5_1[r0_2] ~= nil and util.IsContainedTable(r5_1[r0_2], "ex_type") == true and r5_1[r0_2].ex_type == r1_2 then
        return true
      end
      return false
    end
    local function r7_1(r0_3)
      -- line: [113, 132] id: 3
      if r4_1 == nil or r0_3 == nil then
        return nil
      end
      local r1_3 = {}
      for r5_3, r6_3 in pairs(r4_1) do
        if util.IsContainedTable(r6_3, "ex_type") == true and r6_3.ex_type == r0_3 then
          table.insert(r1_3, r6_3)
        end
      end
      if #r1_3 < 1 then
        r1_3 = nil
      end
      return r1_3
    end
    local function r8_1(r0_4)
      -- line: [137, 152] id: 4
      if r4_1 == nil or r0_4 == nil then
        return nil
      end
      local r1_4 = {}
      for r5_4, r6_4 in pairs(r4_1) do
        if util.IsContainedTable(r6_4, "ex_type") == true and r6_4.ex_type == r0_4 then
          table.insert(r1_4, r6_4)
        end
      end
      return r1_4
    end
    local function r9_1(r0_5)
      -- line: [157, 166] id: 5
      if r4_1 == nil or r0_5 == nil or r0_5 < 1 or #r4_1 < r0_5 then
        return nil
      end
      return r4_1[r0_5]
    end
    local function r10_1(r0_6, r1_6)
      -- line: [171, 180] id: 6
      if util.IsContainedTable(r0_6, "value") == false then
        return false
      end
      if r0_6.value <= r1_6 then
        return true
      end
      return false
    end
    local function r11_1(r0_7, r1_7)
      -- line: [185, 194] id: 7
      if util.IsContainedTable(r0_7, "value") == false then
        return false
      end
      if r0_7.value <= r1_7 then
        return true
      end
      return false
    end
    local function r12_1(r0_8, r1_8)
      -- line: [199, 208] id: 8
      if util.IsContainedTable(r0_8, "value") == false then
        return false
      end
      if r0_8.value <= r1_8 then
        return true
      end
      return false
    end
    local function r13_1(r0_9, r1_9)
      -- line: [213, 222] id: 9
      if util.IsContainedTable(r0_9, "value") == false then
        return false
      end
      if r1_9 == r0_9.value then
        return true
      end
      return false
    end
    local function r14_1(r0_10, r1_10, r2_10)
      -- line: [227, 237] id: 10
      if util.IsContainedTable(r0_10, "value") == false then
        return false
      end
      if r1_10 == r0_10.char_id and r0_10.value <= r2_10 then
        return true
      end
      return false
    end
    local function r15_1(r0_11, r1_11, r2_11)
      -- line: [242, 253] id: 11
      if util.IsContainedTable(r0_11, "value") == false or util.IsContainedTable(r0_11, "char_id") == false then
        return false
      end
      if r1_11 == r0_11.char_id and r0_11.value <= r2_11 then
        return true
      end
      return false
    end
    local function r16_1(r0_12, r1_12, r2_12)
      -- line: [258, 269] id: 12
      if util.IsContainedTable(r0_12, "value") == false or util.IsContainedTable(r0_12, "char_id") == false then
        return false
      end
      if r1_12 == r0_12.char_id and r0_12.value <= r2_12 then
        return true
      end
      return false
    end
    local function r17_1(r0_13, r1_13, r2_13)
      -- line: [274, 301] id: 13
      if util.IsContainedTable(r0_13, "value") == false or util.IsContainedTable(r0_13, "char_id") == false or type(r0_13.char_id) ~= "table" or type(r1_13) ~= "table" or type(r2_13) ~= "table" then
        return false
      end
      local r3_13 = {
        false,
        false
      }
      local r4_13 = nil
      local r5_13 = nil
      for r9_13 = 1, #r1_13, 1 do
        for r13_13 = 1, #r0_13.char_id, 1 do
          if r1_13[r9_13] == r0_13.char_id[r13_13] and r2_13[r9_13] == r0_13.value then
            r3_13[r9_13] = true
          end
        end
      end
      if r3_13[1] == true and r3_13[2] == true then
        return true
      end
      return false
    end
    local function r18_1(r0_14, r1_14, r2_14)
      -- line: [306, 343] id: 14
      if r0_14 == nil or r0_14 < 1 or #r5_1 < r0_14 or r1_14 == nil or r2_14 == nil or r6_1(r0_14, r1_14) == true then
        return 
      end
      local function r3_14(r0_15)
        -- line: [317, 338] id: 15
        if r0_15 == r4_0.Score then
          return "スコア"
        elseif r0_15 == r4_0.UseOrb then
          return "オーブ使用"
        elseif r0_15 == r4_0.TreasureboxTotal then
          return "木と赤の宝箱取得"
        elseif r0_15 == r4_0.TreasureboxNormal then
          return "木宝箱取得"
        elseif r0_15 == r4_0.TreasureboxRich then
          return "赤宝箱取得"
        elseif r0_15 == r4_0.Hpxx then
          return "HPxx"
        elseif r0_15 == r4_0.Levelxx then
          return "レベルxxの指定ユニット"
        elseif r0_15 == r4_0.UnitAndEvoxx then
          return "指定ユニットの覚醒xx"
        elseif r0_15 == r4_0.TwoUnitAndEvoxx then
          return "２体の指定ユニットの覚醒xx"
        end
        return "なし"
      end
      r5_1[r0_14] = {
        ex_type = r1_14,
        reward = r2_14,
      }
    end
    local function r19_1(r0_16, r1_16)
      -- line: [348, 388] id: 16
      if r0_16 == nil or r0_16 < 1 or #r5_1 < r0_16 or r1_16 == nil then
        return 
      end
      if r5_1[r0_16].ex_type == r1_16 then
        local function r3_16(r0_17)
          -- line: [361, 382] id: 17
          if r0_17 == r4_0.Score then
            return "スコア"
          elseif r0_17 == r4_0.UseOrb then
            return "オーブ使用"
          elseif r0_17 == r4_0.TreasureboxTotal then
            return "木と赤の宝箱取得"
          elseif r0_17 == r4_0.TreasureboxNormal then
            return "木宝箱取得"
          elseif r0_17 == r4_0.TreasureboxRich then
            return "赤宝箱取得"
          elseif r0_17 == r4_0.Hpxx then
            return "HPxx"
          elseif r0_17 == r4_0.Levelxx then
            return "レベルxxの指定ユニット"
          elseif r0_17 == r4_0.UnitAndEvoxx then
            return "指定ユニットの覚醒xx"
          elseif r0_17 == r4_0.TwoUnitAndEvoxx then
            return "２体の指定ユニットの覚醒xx"
          end
          return "なし"
        end
        r5_1[r0_16] = {}
      end
    end
    local function r20_1()
      -- line: [393, 407] id: 18
      if r5_1 == nil then
        return 0
      end
      local r0_18 = 0
      for r4_18 = 1, #r5_1, 1 do
        if util.IsContainedTable(r5_1[r4_18], "ex_type") == true then
          r0_18 = r0_18 + 2 ^ (r4_18 - 1)
        end
      end
      return r0_18
    end
    local function r21_1(r0_19)
      -- line: [412, 435] id: 19
      if type(r0_19) ~= "number" then
        return 
      end
      if r5_1 ~= nil then
        r5_1 = nil
      end
      local r1_19 = r0_19
      r5_1 = {}
      for r5_19 = 1, r2_1, 1 do
        table.insert(r5_1, {})
      end
      for r5_19 = 1, r2_1, 1 do
        if r1_19 % 2 == 1 then
          local r6_19 = r9_1(r5_19)
          r18_1(r6_19.exId, r6_19.ex_type, r6_19.reward)
        end
        r1_19 = math.floor(r1_19 / 2)
      end
    end
    local function r22_1(r0_20, r1_20)
      -- line: [440, 508] id: 20
      if r1_20 == nil then
        return false
      end
      local r2_20 = r8_1(r0_20)
      local r3_20 = false
      for r7_20, r8_20 in pairs(r2_20) do
        if util.IsContainedTable(r8_20, "ex_type") == false then
          break
        elseif r8_20.ex_type == r4_0.Score and r10_1(r8_20, r1_20.value) == true then
          r18_1(r8_20.exId, r0_20, r8_20.reward)
          r3_20 = true
        elseif r8_20.ex_type == r4_0.UseOrb and r11_1(r8_20, r1_20.value) == true then
          r18_1(r8_20.exId, r0_20, r8_20.reward)
          r3_20 = true
        elseif (r8_20.ex_type == r4_0.TreasureboxTotal or r8_20.ex_type == r4_0.TreasureboxNormal or r8_20.ex_type == r4_0.TreasureboxRich) and r12_1(r8_20, r1_20.value) == true then
          r18_1(r8_20.exId, r0_20, r8_20.reward)
          r3_20 = true
        elseif r8_20.ex_type == r4_0.Hpxx then
          if r13_1(r8_20, r1_20.value) == true then
            r18_1(r8_20.exId, r0_20, r8_20.reward)
            r3_20 = true
          elseif r6_1(r8_20.exId, r8_20.ex_type) == true then
            r19_1(r8_20.exId, r8_20.ex_type)
          end
        elseif r8_20.ex_type == r4_0.Levelxx and r14_1(r8_20, r1_20.char_id, r1_20.value) == true then
          r18_1(r8_20.exId, r0_20, r8_20.reward)
          r3_20 = true
        elseif r8_20.ex_type == r4_0.UnitAndEvoxx and r16_1(r8_20, r1_20.char_id, r1_20.value) == true then
          r18_1(r8_20.exId, r0_20, r8_20.reward)
          r3_20 = true
        elseif r8_20.ex_type == r4_0.TwoUnitAndEvoxx and r17_1(r8_20, r1_20.char_id, r1_20.value) == true then
          r18_1(r8_20.exId, r0_20, r8_20.reward)
          r3_20 = true
        end
      end
      return r3_20
    end
    local function r23_1(r0_21)
      -- line: [513, 574] id: 21
      if r0_21 == nil or util.IsContainedTable(r0_21, "ex_type") == false or util.IsContainedTable(r0_21, "value") == false then
        return nil
      end
      local function r1_21(r0_22)
        -- line: [521, 523] id: 22
        return r0_0.GetCharName(r0_22)
      end
      local r2_21 = nil
      if r0_21.ex_type == r4_0.Score then
        r2_21 = string.format(db.GetMessage(r3_1.Score), r0_21.value)
      elseif r0_21.ex_type == r4_0.UseOrb then
        r2_21 = string.format(db.GetMessage(r3_1.UseOrb), r0_21.value)
      elseif r0_21.ex_type == r4_0.TreasureboxTotal then
        r2_21 = string.format(db.GetMessage(r3_1.TreasureboxTotal), r0_21.value)
      elseif r0_21.ex_type == r4_0.TreasureboxNormal then
        r2_21 = string.format(db.GetMessage(r3_1.TreasureboxNormal), r0_21.value)
      elseif r0_21.ex_type == r4_0.TreasureboxRich then
        r2_21 = string.format(db.GetMessage(r3_1.TreasureboxRich), r0_21.value)
      elseif r0_21.ex_type == r4_0.Hpxx then
        r2_21 = string.format(db.GetMessage(r3_1.Hpxx), r0_21.value)
      elseif r0_21.ex_type == r4_0.Levelxx and util.IsContainedTable(r0_21, "char_id") == true then
        r2_21 = string.format(db.GetMessage(r3_1.Levelxx), r0_21.value, r1_21(r0_21.char_id))
      elseif r0_21.ex_type == r4_0.UnitAndEvoxx and util.IsContainedTable(r0_21, "char_id") == true then
        r2_21 = string.format(db.GetMessage(r3_1.UnitAndEvoxx), r1_21(r0_21.char_id), r0_21.value)
      elseif r0_21.ex_type == r4_0.TwoUnitAndEvoxx and util.IsContainedTable(r0_21, "char_id") == true and type(r0_21.char_id) == "table" and 1 < #r0_21.char_id then
        r2_21 = string.format(db.GetMessage(r3_1.TwoUnitAndEvoxx), r1_21(r0_21.char_id[1]), r1_21(r0_21.char_id[2]), r0_21.value)
      end
      return r2_21
    end
    local function r24_1(r0_23, r1_23)
      -- line: [579, 582] id: 23
      local r2_23 = nil	-- notice: implicit variable refs by block#[0]
      r4_1 = r2_23
      r2_23 = db.GetExMedalCondition(r0_23, r1_23)
      r4_1 = r2_23
    end
    local function r26_1()
      -- line: [612, 620] id: 25
      if r4_1 ~= nil then
        r4_1 = nil
      end
      if r5_1 ~= nil then
        r5_1 = nil
      end
    end
    function r1_1.GetCondition(r0_26)
      -- line: [628, 630] id: 26
      return r7_1(r0_26)
    end
    function r1_1.GetConditionByExId(r0_27)
      -- line: [635, 637] id: 27
      return r9_1(r0_27)
    end
    function r1_1.GetConditionString(r0_28)
      -- line: [642, 645] id: 28
      return r23_1(r9_1(r0_28))
    end
    function r1_1.UpdateAcquireMedal(r0_29, r1_29)
      -- line: [650, 652] id: 29
      return r22_1(r0_29, r1_29)
    end
    function r1_1.IsAcquireMedal(r0_30, r1_30)
      -- line: [657, 659] id: 30
      return r6_1(r0_30, r1_30)
    end
    function r1_1.GetAcquireMedal()
      -- line: [664, 666] id: 31
      return r5_1
    end
    function r1_1.RemoveAcquireMedal(r0_32, r1_32)
      -- line: [671, 673] id: 32
      r19_1(r0_32, r1_32)
    end
    function r1_1.EncodeAcquire()
      -- line: [678, 680] id: 33
      return r20_1()
    end
    function r1_1.DecodeAcquire(r0_34)
      -- line: [685, 687] id: 34
      r21_1(r0_34)
    end
    function r1_1.Clean()
      -- line: [692, 694] id: 35
      r26_1()
    end
    (function()
      -- line: [587, 607] id: 24
      local r0_24 = 1
      if r0_1.mapId ~= nil then
        r0_24 = r0_1.mapId
      end
      local r1_24 = 1
      if r0_1.stageId ~= nil then
        r1_24 = r0_1.stageId
      end
      r24_1(r0_24, r1_24)
      local r2_24 = nil
      r5_1 = {}
      for r6_24 = 1, r2_1, 1 do
        table.insert(r5_1, {})
      end
    end)()
    return r1_1
  end,
}
