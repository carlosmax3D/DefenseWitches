-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = {
  BEAT_ENEMY = function()
    -- line: [10, 10] id: 1
    return 1
  end,
  CLEAR_WORLD = function()
    -- line: [11, 11] id: 2
    return 2
  end,
  UNLOCK_CHAR = function()
    -- line: [12, 12] id: 3
    return 3
  end,
  SUMMON_CHAR = function()
    -- line: [13, 13] id: 4
    return 4
  end,
  UPGRADE_CHAR = function()
    -- line: [14, 14] id: 5
    return 101
  end,
  USE_FULL_BOCKO = function()
    -- line: [15, 15] id: 6
    return 102
  end,
  SUMMON_WITCHES = function()
    -- line: [16, 16] id: 7
    return 103
  end,
  USE_BIG_MAGIC = function()
    -- line: [17, 17] id: 8
    return 104
  end,
  CLEAR_IN_HP1 = function()
    -- line: [18, 18] id: 9
    return 105
  end,
}
return {
  MISSION_TYPE = r0_0,
  new = function(r0_10)
    -- line: [23, 1298] id: 10
    local r1_10 = {}
    local r2_10 = {
      BEAT_ENEMY = function()
        -- line: [31, 31] id: 11
        return 6
      end,
      CLEAR_WORLD = function()
        -- line: [32, 32] id: 12
        return 7
      end,
      UNLOCK_CHAR = function()
        -- line: [33, 33] id: 13
        return 8
      end,
      SUMMON_CHAR = function()
        -- line: [34, 34] id: 14
        return 9
      end,
      UPGRADE_CHAR = function()
        -- line: [35, 35] id: 15
        return 2
      end,
      USE_FULL_BOCKO = function()
        -- line: [36, 36] id: 16
        return 3
      end,
      SUMMON_WITCHES = function()
        -- line: [37, 37] id: 17
        return 1
      end,
      USE_BIG_MAGIC = function()
        -- line: [38, 38] id: 18
        return 4
      end,
      CLEAR_IN_HP1 = function()
        -- line: [39, 39] id: 19
        return 5
      end,
    }
    local r3_10 = {
      BEAT_ENEMY = function()
        -- line: [44, 44] id: 20
        return 13
      end,
      CLEAR_WORLD = function()
        -- line: [45, 45] id: 21
        return 15
      end,
      UNLOCK_CHAR = function()
        -- line: [46, 46] id: 22
        return 16
      end,
      SUMMON_CHAR = function()
        -- line: [47, 47] id: 23
        return 18
      end,
      UPGRADE_CHAR = function()
        -- line: [48, 48] id: 24
        return 11
      end,
      USE_FULL_BOCKO = function()
        -- line: [49, 49] id: 25
        return 12
      end,
      SUMMON_WITCHES = function()
        -- line: [50, 50] id: 26
        return 10
      end,
      USE_BIG_MAGIC = function()
        -- line: [51, 51] id: 27
        return 17
      end,
      CLEAR_IN_HP1 = function()
        -- line: [52, 52] id: 28
        return 14
      end,
    }
    local r4_10 = {
      BEAT_ENEMY = function()
        -- line: [57, 57] id: 29
        return "eid%03d"
      end,
      CLEAR_WORLD = function()
        -- line: [58, 58] id: 30
        return "wid%03d"
      end,
      UNLOCK_CHAR = function()
        -- line: [59, 59] id: 31
        return "cid%03d"
      end,
      SUMMON_CHAR = function()
        -- line: [60, 60] id: 32
        return "cid%03d"
      end,
      UPGRADE_CHAR = function()
        -- line: [61, 61] id: 33
        return "upgrade"
      end,
      USE_FULL_BOCKO = function()
        -- line: [62, 62] id: 34
        return "fbocko"
      end,
      SUMMON_WITCHES = function()
        -- line: [63, 63] id: 35
        return "switches"
      end,
      USE_BIG_MAGIC = function()
        -- line: [64, 64] id: 36
        return "bmagic"
      end,
      CLEAR_IN_HP1 = function()
        -- line: [65, 65] id: 37
        return "chp1"
      end,
    }
    local r5_10 = {
      CHAR = function()
        -- line: [70, 70] id: 38
        return 1
      end,
    }
    local r6_10 = require("resource.char_define")
    local r7_10 = require("resource.enemy_define")
    local r8_10 = require("resource.world_define")
    local r9_10 = nil
    local function r10_10(r0_39, r1_39)
      -- line: [87, 89] id: 39
      util.IsBingoEnabled(r1_39, r0_39)
    end
    local function r11_10()
      -- line: [93, 99] id: 40
      if r9_10 == nil then
        return false
      end
      return true
    end
    local function r12_10(r0_41)
      -- line: [103, 108] id: 41
      if r0_41 == 1 then
        return require("bingo.bingo_card_data.tbl_b01_card").new()
      end
    end
    local function r13_10(r0_42)
      -- line: [112, 124] id: 42
      if r11_10() == false then
        return nil
      end
      for r4_42, r5_42 in pairs(r9_10.mission_data) do
        if r0_42 == r5_42.mission_type then
          return r4_42, r5_42
        end
      end
      return nil
    end
    local function r14_10(r0_43, r1_43)
      -- line: [128, 177] id: 43
      local r2_43, r3_43 = r13_10(r1_43.mission_type)
      if r2_43 == nil then
        DebugPrint("ビンゴミッションデータ取得失敗")
        return nil
      end
      local r4_43 = db.GetUserBingoData(r0_43, r2_43)
      if r1_43.mission_type == r0_0.BEAT_ENEMY() then
        local r5_43 = string.format(r4_10.BEAT_ENEMY(), r3_43.enemy_id)
        local r6_43 = 0
        if r4_43 ~= nil then
          r6_43 = r4_43.data[r5_43]
        end
        return string.format(db.GetBingoMessage(r2_10.BEAT_ENEMY()), r7_10.GetEnemyName(r1_43.enemy_id), r1_43.value, r6_43, r1_43.value)
      elseif r1_43.mission_type == r0_0.CLEAR_WORLD() then
        return string.format(db.GetBingoMessage(r2_10.CLEAR_WORLD()), r1_43.value)
      elseif r1_43.mission_type == r0_0.UNLOCK_CHAR() then
        return string.format(db.GetBingoMessage(r2_10.UNLOCK_CHAR()), r6_10.GetCharName(r1_43.char_id))
      elseif r1_43.mission_type == r0_0.SUMMON_CHAR() then
        local r5_43 = string.format(r4_10.SUMMON_CHAR(), r3_43.char_id)
        local r6_43 = 0
        if r4_43 ~= nil then
          r6_43 = r4_43.data[r5_43]
        end
        return string.format(db.GetBingoMessage(r2_10.SUMMON_CHAR()), r6_10.GetCharName(r1_43.char_id), r1_43.value, r6_43, r1_43.value)
      elseif r1_43.mission_type == r0_0.UPGRADE_CHAR() then
        return db.GetBingoMessage(r2_10.UPGRADE_CHAR())
      elseif r1_43.mission_type == r0_0.USE_FULL_BOCKO() then
        return db.GetBingoMessage(r2_10.USE_FULL_BOCKO())
      elseif r1_43.mission_type == r0_0.SUMMON_WITCHES() then
        return db.GetBingoMessage(r2_10.SUMMON_WITCHES())
      elseif r1_43.mission_type == r0_0.USE_BIG_MAGIC() then
        return db.GetBingoMessage(r2_10.USE_BIG_MAGIC())
      elseif r1_43.mission_type == r0_0.CLEAR_IN_HP1() then
        return db.GetBingoMessage(r2_10.CLEAR_IN_HP1())
      end
      return ""
    end
    local function r15_10(r0_44)
      -- line: [181, 219] id: 44
      if r0_44.mission_type == r0_0.BEAT_ENEMY() then
        if _G.UILanguage == "jp" then
          return string.format(db.GetBingoMessage(r3_10.BEAT_ENEMY()), r7_10.GetEnemyName(r0_44.enemy_id), r0_44.value)
        else
          return string.format(db.GetBingoMessage(r3_10.BEAT_ENEMY()), r0_44.value, r7_10.GetEnemyName(r0_44.enemy_id))
        end
      elseif r0_44.mission_type == r0_0.CLEAR_WORLD() then
        return string.format(db.GetBingoMessage(r3_10.CLEAR_WORLD()), r8_10.GetWorldName(r0_44.value))
      elseif r0_44.mission_type == r0_0.UNLOCK_CHAR() then
        return string.format(db.GetBingoMessage(r3_10.UNLOCK_CHAR()), r6_10.GetCharName(r0_44.char_id))
      elseif r0_44.mission_type == r0_0.SUMMON_CHAR() then
        if _G.UILanguage == "jp" then
          return string.format(db.GetBingoMessage(r3_10.SUMMON_CHAR()), r6_10.GetCharName(r0_44.char_id), r0_44.value)
        else
          return string.format(db.GetBingoMessage(r3_10.SUMMON_CHAR()), r0_44.value, r6_10.GetCharName(r0_44.char_id))
        end
      elseif r0_44.mission_type == r0_0.UPGRADE_CHAR() then
        return db.GetBingoMessage(r3_10.UPGRADE_CHAR())
      elseif r0_44.mission_type == r0_0.USE_FULL_BOCKO() then
        return db.GetBingoMessage(r3_10.USE_FULL_BOCKO())
      elseif r0_44.mission_type == r0_0.SUMMON_WITCHES() then
        return db.GetBingoMessage(r3_10.SUMMON_WITCHES())
      elseif r0_44.mission_type == r0_0.USE_BIG_MAGIC() then
        return db.GetBingoMessage(r3_10.USE_BIG_MAGIC())
      elseif r0_44.mission_type == r0_0.CLEAR_IN_HP1() then
        return db.GetBingoMessage(r3_10.CLEAR_IN_HP1())
      end
      return ""
    end
    local function r16_10()
      -- line: [223, 230] id: 45
      if r11_10() == false then
        return 1
      end
      return r9_10.cardId
    end
    local function r17_10(r0_46)
      -- line: [234, 246] id: 46
      if r0_46.all_clear.image == nil or r0_46.all_clear.char_id == nil then
        return nil
      end
      return {
        prizesType = r0_46.all_clear.prizes_type,
        charId = r0_46.all_clear.char_id,
        image = r0_46.all_clear.image,
        charName = r6_10.GetCharName(r0_46.all_clear.char_id),
      }
    end
    local function r18_10(r0_47)
      -- line: [250, 261] id: 47
      if r11_10() == false then
        return nil
      end
      if r12_10(r0_47) == nil then
        return nil
      end
      return r17_10(r9_10.prizes_data)
    end
    local function r19_10()
      -- line: [265, 271] id: 48
      if r11_10() == false then
        return nil
      end
      return r17_10(r9_10.prizes_data)
    end
    local function r20_10(r0_49)
      -- line: [275, 308] id: 49
      local r1_49, r2_49 = r13_10(r0_0.BEAT_ENEMY())
      if r1_49 == nil then
        return 
      end
      if r0_49 ~= r2_49.enemy_id then
        return 
      end
      local r3_49 = string.format(r4_10.BEAT_ENEMY(), r2_49.enemy_id)
      local r4_49 = db.GetUserBingoData(r9_10.cardId, r1_49)
      local r5_49 = 1
      if util.IsContainedTable(r4_49, "data") == false or r4_49.data ~= nil and r4_49.data[r3_49] == nil then
        r4_49 = {}
        r4_49.data = {}
      else
        r5_49 = r4_49.data[r3_49] + 1
      end
      if r2_49.value < r5_49 or r4_49.cleared == true then
        return 
      end
      r4_49.data[r3_49] = r5_49
      db.SetUserBingoData(r9_10.cardId, r1_49, r4_49.data, false)
    end
    local function r21_10()
      -- line: [312, 350] id: 50
      if _G.UserID == nil then
        DebugPrint(" ** ユーザIDが存在しません **")
        return 
      end
      local r0_50, r1_50 = r13_10(r0_0.CLEAR_WORLD())
      if r0_50 == nil then
        return 
      end
      local r2_50 = db.GetUserBingoData(r9_10.cardId, r0_50)
      local r3_50 = db.GetMapInfo(_G.UserID)
      local r4_50 = r1_50.value + 1
      if r4_50 > 10 then
        r4_50 = 10
      end
      if r3_50[r4_50] == 1 then
        return 
      end
      local r5_50 = string.format(r4_10.CLEAR_WORLD(), r1_50.value)
      if util.IsContainedTable(r2_50, "data") == false or r2_50.data ~= nil and r2_50.data[r5_50] == nil then
        r2_50 = {}
        r2_50.data = {}
      end
      if r2_50.cleared == true then
        return 
      end
      r2_50.data[r5_50] = 1
      db.SetUserBingoData(r9_10.cardId, r0_50, r2_50.data, false)
    end
    local function r22_10()
      -- line: [354, 388] id: 51
      if _G.UserID == nil then
        DebugPrint(" ** ユーザIDが存在しません **")
        return 
      end
      local r0_51, r1_51 = r13_10(r0_0.UNLOCK_CHAR())
      if r0_51 == nil then
        return 
      end
      local r2_51 = db.GetUserBingoData(r9_10.cardId, r0_51)
      if db.LoadSummonData(_G.UserID)[r1_51.char_id] ~= 1 then
        return 
      end
      local r4_51 = string.format(r4_10.UNLOCK_CHAR(), r1_51.char_id)
      if util.IsContainedTable(r2_51, "data") == false or r2_51.data ~= nil and r2_51.data[r4_51] == nil then
        r2_51 = {}
        r2_51.data = {}
      end
      if r2_51.cleared == true then
        return 
      end
      r2_51.data[r4_51] = 1
      db.SetUserBingoData(r9_10.cardId, r0_51, r2_51.data, false)
    end
    local function r23_10(r0_52)
      -- line: [392, 424] id: 52
      local r1_52, r2_52 = r13_10(r0_0.SUMMON_CHAR())
      if r1_52 == nil then
        return 
      end
      if r0_52 ~= r2_52.char_id then
        return 
      end
      local r3_52 = db.GetUserBingoData(r9_10.cardId, r1_52)
      local r4_52 = string.format(r4_10.SUMMON_CHAR(), r2_52.char_id)
      local r5_52 = 1
      if util.IsContainedTable(r3_52, "data") == false or r3_52.data ~= nil and r3_52.data[r4_52] == nil then
        r3_52 = {}
        r3_52.data = {}
      else
        r5_52 = r3_52.data[r4_52] + 1
      end
      if r2_52.value < r5_52 or r3_52.cleared == true then
        return 
      end
      r3_52.data[r4_52] = r5_52
      db.SetUserBingoData(r9_10.cardId, r1_52, r3_52.data, false)
    end
    local function r24_10()
      -- line: [428, 451] id: 53
      local r0_53, r1_53 = r13_10(r0_0.UPGRADE_CHAR())
      if r0_53 == nil then
        return 
      end
      local r2_53 = db.GetUserBingoData(r9_10.cardId, r0_53)
      local r3_53 = r4_10.UPGRADE_CHAR()
      if util.IsContainedTable(r2_53, "data") == false or r2_53.data ~= nil and r2_53.data[r3_53] == nil then
        r2_53 = {}
        r2_53.data = {}
      end
      if r2_53.cleared == true then
        return 
      end
      r2_53.data[r3_53] = 1
      db.SetUserBingoData(r9_10.cardId, r0_53, r2_53.data, false)
    end
    local function r25_10()
      -- line: [455, 478] id: 54
      local r0_54, r1_54 = r13_10(r0_0.USE_FULL_BOCKO())
      if r0_54 == nil then
        return 
      end
      local r2_54 = db.GetUserBingoData(r9_10.cardId, r0_54)
      local r3_54 = r4_10.USE_FULL_BOCKO()
      if util.IsContainedTable(r2_54, "data") == false or r2_54.data ~= nil and r2_54.data[r3_54] == nil then
        r2_54 = {}
        r2_54.data = {}
      end
      if r2_54.cleared == true then
        return 
      end
      r2_54.data[r3_54] = 1
      db.SetUserBingoData(r9_10.cardId, r0_54, r2_54.data, false)
    end
    local function r26_10()
      -- line: [482, 505] id: 55
      local r0_55, r1_55 = r13_10(r0_0.SUMMON_WITCHES())
      if r0_55 == nil then
        return 
      end
      local r2_55 = db.GetUserBingoData(r9_10.cardId, r0_55)
      local r3_55 = r4_10.SUMMON_WITCHES()
      if util.IsContainedTable(r2_55, "data") == false or r2_55.data ~= nil and r2_55.data[r3_55] == nil then
        r2_55 = {}
        r2_55.data = {}
      end
      if r2_55.cleared == true then
        return 
      end
      r2_55.data[r3_55] = 1
      db.SetUserBingoData(r9_10.cardId, r0_55, r2_55.data, false)
    end
    local function r27_10()
      -- line: [509, 532] id: 56
      local r0_56, r1_56 = r13_10(r0_0.USE_BIG_MAGIC())
      if r0_56 == nil then
        return 
      end
      local r2_56 = db.GetUserBingoData(r9_10.cardId, r0_56)
      local r3_56 = r4_10.USE_BIG_MAGIC()
      if util.IsContainedTable(r2_56, "data") == false or r2_56.data ~= nil and r2_56.data[r3_56] == nil then
        r2_56 = {}
        r2_56.data = {}
      end
      if r2_56.cleared == true then
        return 
      end
      r2_56.data[r3_56] = 1
      db.SetUserBingoData(r9_10.cardId, r0_56, r2_56.data, false)
    end
    local function r28_10()
      -- line: [536, 559] id: 57
      local r0_57, r1_57 = r13_10(r0_0.CLEAR_IN_HP1())
      if r0_57 == nil then
        return 
      end
      local r2_57 = db.GetUserBingoData(r9_10.cardId, r0_57)
      local r3_57 = r4_10.CLEAR_IN_HP1()
      if util.IsContainedTable(r2_57, "data") == false or r2_57.data ~= nil and r2_57.data[r3_57] == nil then
        r2_57 = {}
        r2_57.data = {}
      end
      if r2_57.cleared == true then
        return 
      end
      r2_57.data[r3_57] = 1
      db.SetUserBingoData(r9_10.cardId, r0_57, r2_57.data, false)
    end
    local function r29_10(r0_58, r1_58)
      -- line: [563, 591] id: 58
      if r0_58 == r0_0.BEAT_ENEMY() then
        r20_10(r1_58.enemyId)
      elseif r0_58 == r0_0.CLEAR_WORLD() then
        r21_10()
      elseif r0_58 == r0_0.UNLOCK_CHAR() then
        r22_10()
      elseif r0_58 == r0_0.SUMMON_CHAR() then
        r23_10(r1_58.charId)
      elseif r0_58 == r0_0.UPGRADE_CHAR() then
        r24_10()
      elseif r0_58 == r0_0.USE_FULL_BOCKO() then
        r25_10()
      elseif r0_58 == r0_0.SUMMON_WITCHES() then
        r26_10()
      elseif r0_58 == r0_0.USE_BIG_MAGIC() then
        r27_10()
      elseif r0_58 == r0_0.CLEAR_IN_HP1() then
        r28_10()
      end
    end
    local function r30_10()
      -- line: [595, 611] id: 59
      local r0_59, r1_59 = r13_10(r0_0.BEAT_ENEMY())
      if r0_59 == nil then
        return nil
      end
      local r2_59 = string.format(r4_10.BEAT_ENEMY(), r1_59.enemy_id)
      local r3_59 = db.GetUserBingoData(r9_10.cardId, r0_59)
      if r3_59 == nil or r3_59.data[r2_59] < r1_59.value or r3_59.cleared == true then
        return nil
      end
      return r0_59, r3_59.data
    end
    local function r31_10()
      -- line: [615, 632] id: 60
      local r0_60, r1_60 = r13_10(r0_0.CLEAR_WORLD())
      if r0_60 == nil then
        return nil
      end
      local r2_60 = db.GetUserBingoData(r9_10.cardId, r0_60)
      local r3_60 = string.format(r4_10.CLEAR_WORLD(), r1_60.value)
      if r2_60 == nil or r2_60.data[r3_60] ~= 1 or r2_60.cleared == true then
        return nil
      end
      return r0_60, r2_60.data
    end
    local function r32_10()
      -- line: [636, 652] id: 61
      local r0_61, r1_61 = r13_10(r0_0.UNLOCK_CHAR())
      if r0_61 == nil then
        return nil
      end
      local r2_61 = db.GetUserBingoData(r9_10.cardId, r0_61)
      local r3_61 = string.format(r4_10.UNLOCK_CHAR(), r1_61.char_id)
      if r2_61 == nil or r2_61.data[r3_61] ~= 1 or r2_61.cleared == true then
        return nil
      end
      return r0_61, r2_61.data
    end
    local function r33_10()
      -- line: [656, 674] id: 62
      local r0_62, r1_62 = r13_10(r0_0.SUMMON_CHAR())
      if r0_62 == nil then
        return nil
      end
      local r2_62 = db.GetUserBingoData(r9_10.cardId, r0_62)
      local r3_62 = string.format(r4_10.SUMMON_CHAR(), r1_62.char_id)
      if r2_62 == nil or r2_62.data[r3_62] < r1_62.value or r2_62.cleared == true then
        return nil
      end
      return r0_62, r2_62.data
    end
    local function r34_10()
      -- line: [678, 695] id: 63
      local r0_63, r1_63 = r13_10(r0_0.UPGRADE_CHAR())
      if r0_63 == nil then
        return nil
      end
      local r2_63 = db.GetUserBingoData(r9_10.cardId, r0_63)
      local r3_63 = r4_10.UPGRADE_CHAR()
      if r2_63 == nil or r2_63.data[r3_63] ~= 1 or r2_63.cleared == true then
        return nil
      end
      return r0_63, r2_63.data
    end
    local function r35_10()
      -- line: [699, 716] id: 64
      local r0_64, r1_64 = r13_10(r0_0.USE_FULL_BOCKO())
      if r0_64 == nil then
        return nil
      end
      local r2_64 = db.GetUserBingoData(r9_10.cardId, r0_64)
      local r3_64 = r4_10.USE_FULL_BOCKO()
      if r2_64 == nil or r2_64.data[r3_64] ~= 1 or r2_64.cleared == true then
        return nil
      end
      return r0_64, r2_64.data
    end
    local function r36_10()
      -- line: [720, 737] id: 65
      local r0_65, r1_65 = r13_10(r0_0.SUMMON_WITCHES())
      if r0_65 == nil then
        return nil
      end
      local r2_65 = db.GetUserBingoData(r9_10.cardId, r0_65)
      local r3_65 = r4_10.SUMMON_WITCHES()
      if r2_65 == nil or r2_65.data[r3_65] ~= 1 or r2_65.cleared == true then
        return nil
      end
      return r0_65, r2_65.data
    end
    local function r37_10()
      -- line: [741, 758] id: 66
      local r0_66, r1_66 = r13_10(r0_0.USE_BIG_MAGIC())
      if r0_66 == nil then
        return nil
      end
      local r2_66 = db.GetUserBingoData(r9_10.cardId, r0_66)
      local r3_66 = r4_10.USE_BIG_MAGIC()
      if r2_66 == nil or r2_66.data[r3_66] ~= 1 or r2_66.cleared == true then
        return nil
      end
      return r0_66, r2_66.data
    end
    local function r38_10()
      -- line: [762, 779] id: 67
      local r0_67, r1_67 = r13_10(r0_0.CLEAR_IN_HP1())
      if r0_67 == nil then
        return nil
      end
      local r2_67 = db.GetUserBingoData(r9_10.cardId, r0_67)
      local r3_67 = r4_10.CLEAR_IN_HP1()
      if r2_67 == nil or r2_67.data[r3_67] ~= 1 or r2_67.cleared == true then
        return nil
      end
      return r0_67, r2_67.data
    end
    local function r39_10(r0_68)
      -- line: [783, 831] id: 68
      local r1_68 = nil	-- notice: implicit variable refs by block#[18, 24]
      local r2_68 = nil	-- notice: implicit variable refs by block#[24]
      if r0_68 == r0_0.BEAT_ENEMY() then
        r1_68, r2_68 = r30_10()
      elseif r0_68 == r0_0.CLEAR_WORLD() then
        r1_68, r2_68 = r31_10()
      elseif r0_68 == r0_0.UNLOCK_CHAR() then
        r1_68, r2_68 = r32_10()
      elseif r0_68 == r0_0.SUMMON_CHAR() then
        r1_68, r2_68 = r33_10()
      elseif r0_68 == r0_0.UPGRADE_CHAR() then
        r1_68, r2_68 = r34_10()
      elseif r0_68 == r0_0.USE_FULL_BOCKO() then
        r1_68, r2_68 = r35_10()
      elseif r0_68 == r0_0.SUMMON_WITCHES() then
        r1_68, r2_68 = r36_10()
      elseif r0_68 == r0_0.USE_BIG_MAGIC() then
        r1_68, r2_68 = r37_10()
      elseif r0_68 == r0_0.CLEAR_IN_HP1() then
        r1_68, r2_68 = r38_10()
      end
      if r1_68 ~= nil then
        local r3_68 = nil
        for r7_68, r8_68 in pairs(r9_10.mission_data) do
          if r8_68.mission_type == r0_68 then
            r3_68 = r8_68.spss_item_master_id
            break
          end
        end
        statslog.LogSend("unlock", {
          item = r3_68,
        })
      end
      return r1_68, r2_68
    end
    local function r40_10()
      -- line: [835, 842] id: 69
      local r0_69, r1_69 = r30_10()
      if r0_69 == nil then
        return 
      end
      db.SetUserBingoData(r9_10.cardId, r0_69, r1_69, true)
    end
    local function r41_10()
      -- line: [846, 853] id: 70
      local r0_70, r1_70 = r31_10()
      if r0_70 == nil then
        return 
      end
      db.SetUserBingoData(r9_10.cardId, r0_70, r1_70, true)
    end
    local function r42_10()
      -- line: [857, 864] id: 71
      local r0_71, r1_71 = r32_10()
      if r0_71 == nil then
        return 
      end
      db.SetUserBingoData(r9_10.cardId, r0_71, r1_71, true)
    end
    local function r43_10()
      -- line: [868, 875] id: 72
      local r0_72, r1_72 = r33_10()
      if r0_72 == nil then
        return 
      end
      db.SetUserBingoData(r9_10.cardId, r0_72, r1_72, true)
    end
    local function r44_10()
      -- line: [879, 886] id: 73
      local r0_73, r1_73 = r34_10()
      if r0_73 == nil then
        return 
      end
      db.SetUserBingoData(r9_10.cardId, r0_73, r1_73, true)
    end
    local function r45_10()
      -- line: [890, 897] id: 74
      local r0_74, r1_74 = r35_10()
      if r0_74 == nil then
        return 
      end
      db.SetUserBingoData(r9_10.cardId, r0_74, r1_74, true)
    end
    local function r46_10()
      -- line: [901, 908] id: 75
      local r0_75, r1_75 = r36_10()
      if r0_75 == nil then
        return 
      end
      db.SetUserBingoData(r9_10.cardId, r0_75, r1_75, true)
    end
    local function r47_10()
      -- line: [912, 919] id: 76
      local r0_76, r1_76 = r37_10()
      if r0_76 == nil then
        return 
      end
      db.SetUserBingoData(r9_10.cardId, r0_76, r1_76, true)
    end
    local function r48_10()
      -- line: [923, 930] id: 77
      local r0_77, r1_77 = r38_10()
      if r0_77 == nil then
        return 
      end
      db.SetUserBingoData(r9_10.cardId, r0_77, r1_77, true)
    end
    local function r49_10(r0_78)
      -- line: [934, 964] id: 78
      if r0_78 == r0_0.BEAT_ENEMY() then
        return r40_10()
      elseif r0_78 == r0_0.CLEAR_WORLD() then
        return r41_10()
      elseif r0_78 == r0_0.UNLOCK_CHAR() then
        return r42_10()
      elseif r0_78 == r0_0.SUMMON_CHAR() then
        return r43_10()
      elseif r0_78 == r0_0.UPGRADE_CHAR() then
        return r44_10()
      elseif r0_78 == r0_0.USE_FULL_BOCKO() then
        return r45_10()
      elseif r0_78 == r0_0.SUMMON_WITCHES() then
        return r46_10()
      elseif r0_78 == r0_0.USE_BIG_MAGIC() then
        return r47_10()
      elseif r0_78 == r0_0.CLEAR_IN_HP1() then
        return r48_10()
      end
      return false
    end
    local function r50_10(r0_79, r1_79)
      -- line: [968, 975] id: 79
      local r2_79 = {}
      for r6_79, r7_79 in pairs(r1_79) do
        table.insert(r2_79, {
          missionType = r7_79.mission_type,
          message = r14_10(r0_79, r7_79),
        })
      end
      return r2_79
    end
    local function r51_10(r0_80)
      -- line: [979, 985] id: 80
      local r1_80 = r12_10(r0_80)
      if r1_80 == nil then
        return nil
      end
      return r50_10(r0_80, r1_80.mission_data)
    end
    local function r52_10()
      -- line: [989, 995] id: 81
      if r11_10() == false then
        return nil
      end
      return r50_10(bingoCardId, r9_10.mission_data)
    end
    local function r53_10()
      -- line: [999, 1011] id: 82
      if r11_10() == false then
        return nil
      end
      local r0_82 = {}
      for r4_82, r5_82 in pairs(r9_10.mission_data) do
        if r39_10(r5_82.mission_type) ~= nil then
          table.insert(r0_82, {
            missionNo = r4_82,
            marked = true,
          })
        end
      end
      return r0_82
    end
    local function r54_10()
      -- line: [1015, 1023] id: 83
      if r11_10() == false then
        return 
      end
      for r3_83, r4_83 in pairs(r9_10.mission_data) do
        r49_10(r4_83.mission_type)
      end
    end
    local function r55_10(r0_84, r1_84)
      -- line: [1027, 1039] id: 84
      local r2_84 = {}
      for r6_84 = 1, #r1_84, 1 do
        table.insert(r2_84, {
          marked = false,
        })
      end
      for r6_84, r7_84 in pairs(r0_84) do
        r2_84[r7_84.missionNo].marked = true
      end
      return r2_84
    end
    local function r56_10(r0_85)
      -- line: [1043, 1048] id: 85
      return r55_10(db.GetClearedBingo(r0_85), r12_10(r0_85).mission_data)
    end
    local function r57_10()
      -- line: [1052, 1059] id: 86
      if r11_10() == false then
        return nil
      end
      return r55_10(db.GetClearedBingo(r9_10.cardId), r9_10.mission_data)
    end
    local function r58_10(r0_87)
      -- line: [1063, 1074] id: 87
      if r11_10() == false then
        return false
      end
      if #db.GetClearedBingo(r0_87) == #r9_10.mission_data then
        return true
      end
      return false
    end
    local function r59_10()
      -- line: [1078, 1093] id: 88
      local r0_88 = r19_10()
      if r0_88 ~= nil and r0_88.prizesType == r5_10.CHAR() then
        db.UnlockSummonData(_G.UserID, r0_88.charId)
        db.UnlockL4SummonData(_G.UserID, r0_88.charId)
      end
      statslog.LogSend("unlock", {
        item = r9_10.prizes_data.all_clear.spss_item_master_id,
      })
    end
    local function r60_10()
      -- line: [1096, 1100] id: 89
      if r9_10 ~= nil then
        r9_10 = nil
      end
    end
    local function r61_10(r0_90)
      -- line: [1104, 1107] id: 90
      r60_10()
      r9_10 = r12_10(r0_90)
    end
    local function r62_10(r0_91)
      -- line: [1111, 1119] id: 91
      if r0_91 == nil then
        return 
      end
      if r0_91.bingoCardId ~= nil then
        r61_10(r0_91.bingoCardId)
      end
    end
    function r1_10.setBingoCardData(r0_93)
      -- line: [1132, 1134] id: 93
      r61_10(r0_93)
    end
    function r1_10.getBingoCardId()
      -- line: [1138, 1140] id: 94
      return r16_10()
    end
    function r1_10.getPrizesData(r0_95)
      -- line: [1144, 1152] id: 95
      if r0_95 ~= nil then
        return r18_10(r0_95)
      else
        return r19_10()
      end
    end
    function r1_10.setPrizesData()
      -- line: [1156, 1159] id: 96
      _G.metrics.bingo_clear()
      r59_10()
    end
    function r1_10.getBingoCardData(r0_97)
      -- line: [1163, 1171] id: 97
      if r0_97 ~= nil then
        return r51_10(r0_97)
      else
        return r52_10()
      end
    end
    function r1_10.getUserBingoData(r0_98)
      -- line: [1175, 1183] id: 98
      if r0_98 ~= nil then
        return r56_10(r0_98)
      else
        return r57_10()
      end
    end
    function r1_10.updateUserBingoData(r0_99, r1_99)
      -- line: [1187, 1189] id: 99
      r29_10(r0_99, r1_99)
    end
    function r1_10.getUserClearedDataList()
      -- line: [1194, 1196] id: 100
      return r53_10()
    end
    function r1_10.setUserClearedDataList()
      -- line: [1200, 1202] id: 101
      r54_10()
    end
    function r1_10.getMissionMessage(r0_102, r1_102)
      -- line: [1206, 1213] id: 102
      local r2_102, r3_102 = r13_10(r1_102)
      if r2_102 == nil then
        return nil
      end
      return r14_10(r0_102, r3_102)
    end
    function r1_10.getMissionDetailMessage(r0_103)
      -- line: [1217, 1224] id: 103
      local r1_103, r2_103 = r13_10(r0_103)
      if r1_103 == nil then
        return nil
      end
      return r15_10(r2_103)
    end
    function r1_10.isBingoEnabled(r0_104, r1_104)
      -- line: [1228, 1230] id: 104
      r10_10(r0_104, r1_104)
    end
    function r1_10.clearAll()
      -- line: [1234, 1293] id: 105
      for r3_105, r4_105 in pairs(r9_10.mission_data) do
        local r5_105, r6_105 = r13_10(r4_105.mission_type)
        if r4_105.mission_type == r0_0.BEAT_ENEMY() then
          db.SetUserBingoData(r9_10.cardId, r5_105, {
            [string.format(r4_10.BEAT_ENEMY(), r6_105.enemy_id)] = 3,
          }, true)
        elseif r4_105.mission_type == r0_0.CLEAR_WORLD() then
          db.SetUserBingoData(r9_10.cardId, r5_105, {
            [string.format(r4_10.CLEAR_WORLD(), r6_105.value)] = 1,
          }, true)
        elseif r4_105.mission_type == r0_0.UNLOCK_CHAR() then
          db.SetUserBingoData(r9_10.cardId, r5_105, {
            [string.format(r4_10.UNLOCK_CHAR(), r6_105.char_id)] = 1,
          }, true)
        elseif r4_105.mission_type == r0_0.SUMMON_CHAR() then
          db.SetUserBingoData(r9_10.cardId, r5_105, {
            [string.format(r4_10.SUMMON_CHAR(), r6_105.char_id)] = 50,
          }, true)
        elseif r4_105.mission_type == r0_0.UPGRADE_CHAR() then
          db.SetUserBingoData(r9_10.cardId, r5_105, {
            [r4_10.UPGRADE_CHAR()] = 1,
          }, true)
        elseif r4_105.mission_type == r0_0.USE_FULL_BOCKO() then
          db.SetUserBingoData(r9_10.cardId, r5_105, {
            [r4_10.USE_FULL_BOCKO()] = 1,
          }, true)
        elseif r4_105.mission_type == r0_0.SUMMON_WITCHES() then
          db.SetUserBingoData(r9_10.cardId, r5_105, {
            [r4_10.SUMMON_WITCHES()] = 1,
          }, true)
        elseif r4_105.mission_type == r0_0.USE_BIG_MAGIC() then
          db.SetUserBingoData(r9_10.cardId, r5_105, {
            [r4_10.USE_BIG_MAGIC()] = 1,
          }, false)
        elseif r4_105.mission_type == r0_0.CLEAR_IN_HP1() then
          db.SetUserBingoData(r9_10.cardId, r5_105, {
            [r4_10.CLEAR_IN_HP1()] = 1,
          }, true)
        end
      end
    end
    (function()
      -- line: [1123, 1125] id: 92
      r62_10(r0_10)
    end)()
    return r1_10
  end,
}
