-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("sqlite3")
local r1_0 = require("json")
local r2_0 = require("crypto")
local r3_0 = require("db.BaseDB")
local r4_0 = require("db.doc.QueueData")
local r5_0 = 2000000000
local function r6_0()
  -- line: [9, 11] id: 1
  return system.pathForFile("leaderboards.sqlite", system.DocumentsDirectory)
end
local function r15_0(r0_10, r1_10)
  -- line: [319, 352] id: 10
  local r3_10 = r0_0.open(r6_0())
  local r4_10 = r3_10:prepare("SELECT flag FROM gc" .. " WHERE uid=:uid AND category=:category AND flag=1")
  assert(r4_10, r3_10:errmsg())
  r4_10:bind_names({
    uid = r0_10,
    category = r1_10,
  })
  local r5_10 = nil
  local r6_10 = nil
  local r7_10 = 0
  for r11_10 in r4_10:nrows() do
    r7_10 = 1
  end
  r4_10:finalize()
  if r7_10 == 1 then
    r3_10:close()
    return false
  end
  r4_10 = r3_10:prepare("REPLACE INTO gc" .. " (uid, category, flag) VALUES" .. " (:uid, :category, :flag)")
  assert(r4_10, r3_10:errmsg())
  r4_10:bind_names({
    uid = r0_10,
    category = r1_10,
    flag = 1,
  })
  r4_10:step()
  r4_10:finalize()
  r3_10:close()
  return true
end
local r18_0 = nil
function r18_0(r0_13)
  -- line: [429, 515] id: 13
  local r1_13 = require("logic.gamecenter")
  local r2_13 = _G.UserID
  local r3_13 = _G.zUserToken
  if r2_13 == nil or r3_13 == nil then
    if r0_13 <= 60000 then
      timer.performWithDelay(r0_13, r18_0(r0_13 * 2))
    end
    return 
  end
  assert(r2_13 and r3_13, debug.traceback())
  local r4_13 = math.floor(os.time() / 86400)
  local r6_13 = r0_0.open(r6_0())
  local r7_13 = nil
  local r8_13 = nil
  local r9_13 = nil
  local r10_13 = nil
  local r11_13 = nil
  local r12_13 = nil
  r8_13 = r6_13:prepare("SELECT day, count, hex, date FROM gc_daily WHERE uid=:uid")
  assert(r8_13, r6_13:errmsg())
  r8_13:bind_names({
    uid = r2_13,
  })
  r9_13 = nil
  r10_13 = nil
  for r16_13 in r8_13:nrows() do
    r9_13 = r16_13.day
    r10_13 = r16_13.count
    r11_13, r12_13 = r3_0.calc_checksum2({
      r2_13,
      r9_13,
      r10_13
    }, r16_13.date)
    if r11_13 ~= r16_13.hex then
      r9_13 = nil
      r10_13 = 0
    end
  end
  r8_13:finalize()
  r8_13 = nil
  if r9_13 == nil or 1 < r4_13 - r9_13 then
    r11_13, r12_13 = r3_0.calc_checksum2({
      r2_13,
      r4_13,
      1
    })
    r8_13 = r6_13:prepare("REPLACE INTO gc_daily " .. "(uid, day, count, date, hex) VALUES " .. "(:uid, :day, 1, :date, :hex)")
    assert(r8_13, r6_13:errmsg())
    r8_13:bind_names({
      uid = r2_13,
      day = r4_13,
      date = r12_13,
      hex = r11_13,
    })
    r1_13.UnlockAchievements(21, math.round(33.333333333333336))
  elseif r4_13 - r9_13 == 1 and _G.LoginGameCenter then
    r10_13 = r10_13 + 1
    r11_13, r12_13 = r3_0.calc_checksum2({
      r2_13,
      r4_13,
      r10_13
    })
    r8_13 = r6_13:prepare("REPLACE INTO gc_daily " .. "(uid, day, count, date, hex) VALUES " .. "(:uid, :day, :count, :date, :hex)")
    assert(r8_13, r6_13:errmsg())
    r8_13:bind_names({
      uid = r2_13,
      day = r4_13,
      count = r10_13,
      date = r12_13,
      hex = r11_13,
    })
    if r10_13 >= 3 then
      if r15_0(r2_13, 21) then
        r1_13.UnlockAchievements(21, 100)
        server.UnlockNormalAchievement(r3_13, 21, function(r0_14)
          -- line: [495, 500] id: 14
          if r0_14.isError then
            r4_0.SetAchieveQueue("normal", 1, 1, 21)
          end
        end)
      end
    else
      r1_13.UnlockAchievements(21, math.round(r10_13 * 100 / 3))
    end
  end
  if r8_13 then
    r8_13:step()
    r8_13:finalize()
  end
  r6_13:close()
end
return {
  InitScore = function()
    -- line: [14, 82] id: 2
    local r1_2 = r0_0.open(r6_0())
    local r2_2 = nil
    r3_0.begin_transcation(r1_2)
    r1_2:exec("CREATE TABLE IF NOT EXISTS score (" .. "uid INTEGER," .. "map INTEGER," .. "stage INTEGER," .. "score INTEGER," .. "type INTEGER," .. "date TEXT," .. "hex TEXT," .. "cdate DATETIME DEFAULT (CURRENT_TIMESTAMP)," .. "PRIMARY KEY (uid, map, stage, type))")
    r1_2:exec("CREATE TABLE IF NOT EXISTS achievement (" .. "uid INTEGER," .. "map INTEGER," .. "stage INTEGER," .. "type INTEGER," .. "flag INTEGER DEFAULT 1," .. "date TEXT," .. "hex TEXT," .. "cdate DATETIME DEFAULT (CURRENT_TIMESTAMP)," .. "PRIMARY KEY (uid, map, stage, type))")
    r1_2:exec("CREATE TABLE IF NOT EXISTS gc (" .. "uid INTEGER," .. "category INTEGER," .. "flag INTEGER," .. "cdate DATETIME DEFAULT (CURRENT_TIMESTAMP)," .. "PRIMARY KEY (uid, category))")
    r1_2:exec("CREATE TABLE IF NOT EXISTS gc_counter (" .. "uid INTEGER," .. "type INTEGER," .. "count INTEGER," .. "date TEXT," .. "hex TEXT," .. "cdate DATETIME DEFAULT (CURRENT_TIMESTAMP)," .. "PRIMARY KEY (uid, type))")
    r1_2:exec("CREATE TABLE IF NOT EXISTS gc_daily (" .. "uid INTEGER PRIMARY KEY," .. "day INTEGER," .. "count INTEGER," .. "date TEXT," .. "hex TEXT," .. "cdate DATETIME DEFAULT (CURRENT_TIMESTAMP)" .. ")")
    r3_0.commit(r1_2)
    r1_2:close()
  end,
  AddScore = function(r0_3, r1_3, r2_3, r3_3, r4_3)
    -- line: [91, 139] id: 3
    assert(r0_3, debug.traceback())
    assert(r1_3, debug.traceback())
    assert(r2_3, debug.traceback())
    assert(r3_3 ~= nil, debug.traceback())
    assert(r4_3 ~= nil, debug.traceback())
    local r6_3 = r0_0.open(r6_0())
    local r7_3 = nil
    local r8_3 = nil
    r3_0.begin_transcation(r6_3)
    r7_3 = r6_3:prepare("SELECT score FROM score" .. " WHERE uid=:uid AND map=:map AND stage=:stage AND type=:type")
    assert(r7_3, r6_3:errmsg())
    r7_3:bind_names({
      uid = r0_3,
      map = r1_3,
      stage = r2_3,
      type = r3_3,
    })
    r8_3 = nil
    for r12_3 in r7_3:nrows() do
      r8_3 = r12_3.score
    end
    r7_3:finalize()
    local r9_3 = false
    if r8_3 == nil or r8_3 < r4_3 then
      local r10_3, r11_3 = r3_0.calc_checksum2({
        r0_3,
        r1_3,
        r2_3,
        r3_3,
        r4_3
      })
      r7_3 = r6_3:prepare("REPLACE INTO score" .. " (uid, map, stage, type, score, date, hex, cdate)" .. " VALUES" .. " (:uid, :map, :stage, :type, :score, :date, :hex, CURRENT_TIMESTAMP)")
      assert(r7_3, r6_3:errmsg())
      r7_3:bind_names({
        uid = r0_3,
        map = r1_3,
        stage = r2_3,
        type = r3_3,
        score = r4_3,
        date = r11_3,
        hex = r10_3,
      })
      r7_3:step()
      r7_3:finalize()
      r9_3 = true
    end
    r3_0.commit(r6_3)
    r6_3:close()
    return r9_3
  end,
  GetScore = function(r0_4, r1_4, r2_4)
    -- line: [142, 171] id: 4
    assert(r0_4, debug.traceback())
    assert(r1_4, debug.traceback())
    assert(r2_4 ~= nil, debug.traceback())
    local r4_4 = r0_0.open(r6_0())
    local r5_4 = r4_4:prepare("SELECT stage, score, date, hex FROM score" .. " WHERE uid=:uid AND map=:map AND type=:type")
    assert(r5_4, r4_4:errmsg())
    r5_4:bind_names({
      uid = r0_4,
      map = r1_4,
      type = r2_4,
    })
    local r6_4 = nil
    local r7_4 = nil
    local r8_4 = nil
    local r9_4 = nil
    local r10_4 = nil
    for r14_4 in r5_4:nrows() do
      if r6_4 == nil then
        r6_4 = {}
      end
      r7_4 = r14_4.stage
      r8_4 = r14_4.score
      local r15_4, r16_4 = r3_0.calc_checksum2({
        r0_4,
        r1_4,
        r7_4,
        r2_4,
        r8_4
      }, r14_4.date)
      if r15_4 ~= r14_4.hex then
        r8_4 = 0
      end
      r6_4[r7_4] = r8_4
    end
    r5_4:finalize()
    r4_4:close()
    return r6_4
  end,
  SetAchievement = function(r0_5, r1_5, r2_5, r3_5, r4_5)
    -- line: [185, 211] id: 5
    local r6_5 = r0_0.open(r6_0())
    if r4_5 == nil then
      r4_5 = true
    end
    local r7_5 = nil
    local r8_5 = nil
    local r9_5 = nil	-- notice: implicit variable refs by block#[5]
    if r4_5 then
      r9_5 = 1
      if not r9_5 then
        r9_5 = 0
      end
    else
        r9_5 = 0
    end
    local r10_5, r11_5 = r3_0.calc_checksum2({
      r0_5,
      r1_5,
      r2_5,
      r3_5,
      r9_5
    })
    r7_5 = r6_5:prepare("REPLACE INTO achievement" .. " (uid, map, stage, type, flag, date, hex, cdate)" .. " VALUES" .. " (:uid, :map, :stage, :type, :flag, :date, :hex, CURRENT_TIMESTAMP)")
    assert(r7_5, r6_5:errmsg())
    r7_5:bind_names({
      uid = r0_5,
      map = r1_5,
      stage = r2_5,
      type = r3_5,
      flag = r9_5,
      date = r11_5,
      hex = r10_5,
    })
    r7_5:step()
    r7_5:finalize()
    r6_5:close()
  end,
  SetAchievements = function(r0_6, r1_6)
    -- line: [214, 245] id: 6
    if #r1_6 < 1 then
      return 
    end
    local r3_6 = r0_0.open(r6_0())
    local r4_6 = nil
    local r5_6 = nil
    local r6_6 = nil
    local r7_6 = nil
    local r8_6 = nil
    local r9_6 = nil
    r4_6 = r3_6:prepare("REPLACE INTO achievement" .. " (uid, map, stage, type, flag, date, hex, cdate)" .. " VALUES" .. " (:uid, :map, :stage, :type, :flag, :date, :hex, CURRENT_TIMESTAMP)")
    assert(r4_6, r3_6:errmsg())
    for r13_6, r14_6 in pairs(r1_6) do
      r7_6 = r14_6.map
      r8_6 = r14_6.stage
      r9_6 = r14_6.type
      local r15_6 = r3_0.calc_checksum2
      r5_6, r6_6 = r15_6({
        r0_6,
        r7_6,
        r8_6,
        r9_6,
        1
      })
      r4_6:reset()
      r4_6:bind_names({
        uid = r0_6,
        map = r7_6,
        stage = r8_6,
        type = r9_6,
        flag = 1,
        date = r6_6,
        hex = r5_6,
      })
      r4_6:step()
    end
    r4_6:finalize()
    r3_6:close()
  end,
  LoadAchievement = function(r0_7, r1_7, r2_7)
    -- line: [248, 270] id: 7
    local r4_7 = r0_0.open(r6_0())
    local r5_7 = r4_7:prepare("SELECT stage, date, hex FROM achievement" .. " WHERE uid=:uid AND map=:map AND type=:type AND flag=1")
    assert(r5_7, r4_7:errmsg())
    r5_7:bind_names({
      uid = r0_7,
      map = r1_7,
      type = r2_7,
    })
    local r6_7 = {}
    local r7_7 = nil
    local r8_7 = nil
    local r9_7 = nil
    for r13_7 in r5_7:nrows() do
      r7_7 = r13_7.stage
      local r14_7, r15_7 = r3_0.calc_checksum2({
        r0_7,
        r1_7,
        r7_7,
        r2_7,
        1
      }, r13_7.date)
      if r14_7 == r13_7.hex then
        r6_7[r7_7] = true
      end
    end
    r5_7:finalize()
    r4_7:close()
    return r6_7
  end,
  LoadAchievementStage = function(r0_8, r1_8, r2_8)
    -- line: [273, 298] id: 8
    local r4_8 = r0_0.open(r6_0())
    local r5_8 = r4_8:prepare("SELECT type, flag, date, hex FROM achievement" .. " WHERE uid=:uid AND map=:map AND stage=:stage")
    assert(r5_8, r4_8:errmsg())
    r5_8:bind_names({
      uid = r0_8,
      map = r1_8,
      stage = r2_8,
    })
    local r6_8 = {}
    local r7_8 = nil
    local r8_8 = nil
    local r9_8 = nil
    local r10_8 = nil
    for r14_8 in r5_8:nrows() do
      r7_8 = r14_8.type
      r8_8 = r14_8.flag
      local r15_8, r16_8 = r3_0.calc_checksum2({
        r0_8,
        r1_8,
        r2_8,
        r7_8,
        r8_8
      }, r14_8.date)
      local r17_8 = r14_8.hex
      if r15_8 == r17_8 then
        if r8_8 > 0 then
          r17_8 = true
        else
          r17_8 = false
        end
        r6_8[r7_8] = r17_8
      else
        r6_8[r7_8] = false
      end
    end
    r5_8:finalize()
    r4_8:close()
    return r6_8
  end,
  GetGameCenterAchievement = function(r0_9, r1_9)
    -- line: [301, 316] id: 9
    local r3_9 = r0_0.open(r6_0())
    local r4_9 = r3_9:prepare("SELECT flag FROM gc" .. " WHERE uid=:uid AND category=:category")
    assert(r4_9, r3_9:errmsg())
    r4_9:bind_names({
      uid = r0_9,
      category = r1_9,
    })
    local r5_9 = 0
    for r9_9 in r4_9:nrows() do
      r5_9 = r9_9.flag
    end
    r4_9:finalize()
    r3_9:close()
    return r5_9 == 1
  end,
  SetGameCenterAchievement = r15_0,
  GetGameCenterAllAchievement = function(r0_11)
    -- line: [355, 369] id: 11
    local r2_11 = r0_0.open(r6_0())
    local r3_11 = r2_11:prepare("SELECT category FROM gc WHERE uid=:uid AND flag=1")
    assert(r3_11, r2_11:errmsg())
    r3_11:bind_names({
      uid = r0_11,
    })
    local r4_11 = {}
    for r8_11 in r3_11:nrows() do
      table.insert(r4_11, r8_11.category)
    end
    r3_11:finalize()
    r2_11:close()
    return r4_11
  end,
  CountAchievement = function(r0_12, r1_12, r2_12)
    -- line: [373, 426] id: 12
    assert(r0_12, debug.traceback())
    assert(r1_12, debug.traceback())
    if r2_12 == nil then
      r2_12 = 1
    end
    local r4_12 = r0_0.open(r6_0())
    local r5_12 = nil
    local r6_12 = nil
    local r7_12 = nil
    local r8_12 = nil
    r3_0.begin_transcation(r4_12)
    r5_12 = r4_12:prepare("SELECT count, hex, date FROM gc_counter" .. " WHERE uid=:uid AND type=:type")
    r5_12:bind_names({
      uid = r0_12,
      type = r1_12,
    })
    r6_12 = 0
    for r12_12 in r5_12:nrows() do
      r6_12 = r12_12.count
      r7_12, r8_12 = r3_0.calc_checksum2({
        r0_12,
        r1_12,
        r6_12
      }, r12_12.date)
      if r7_12 ~= r12_12.hex then
        r6_12 = 0
      end
    end
    r5_12:finalize()
    r6_12 = r6_12 + r2_12
    if r5_0 < r6_12 then
      r6_12 = r5_0
    end
    if _G.LoginGameCenter then
      r7_12, r8_12 = r3_0.calc_checksum2({
        r0_12,
        r1_12,
        r6_12
      })
      r5_12 = r4_12:prepare("REPLACE INTO gc_counter (uid, type, count, date, hex)" .. " VALUES (:uid, :type, :count, :date, :hex)")
      assert(r5_12, r4_12:errmsg())
      r5_12:bind_names({
        uid = r0_12,
        type = r1_12,
        count = r6_12,
        date = r8_12,
        hex = r7_12,
      })
      r5_12:step()
      r5_12:finalize()
    else
      DebugPrint("gamecenter is not login:" .. r1_12 .. "/" .. r6_12 .. "+" .. r2_12)
    end
    r3_0.commit(r4_12)
    r4_12:close()
    return r6_12
  end,
  CheckDailyBonus = r18_0,
  LeaderboardDataReplace = function(r0_15)
    -- line: [517, 625] id: 15
    if r0_15.data.leaderboard then
      local r1_15 = nil
      local r2_15 = nil
      local r3_15 = nil
      local r4_15 = nil
      local r5_15 = nil
      local r6_15 = nil
      local r7_15 = nil
      local r8_15 = nil
      local r9_15 = nil
      local r10_15 = nil
      local r11_15 = nil
      r2_15 = r0_0.open(r6_0())
      r4_15 = r0_15.data.leaderboard
      r3_15 = r2_15:prepare("REPLACE INTO achievement" .. " (uid, map, stage, type, flag, date, hex, cdate)" .. " VALUES" .. " (:uid, :map, :stage, :type, :flag, :date, :hex, CURRENT_TIMESTAMP)")
      assert(r3_15, r2_15:errmsg())
      for r15_15, r16_15 in pairs(r4_15.achievement) do
        local r17_15 = pairs
        for r20_15, r21_15 in r17_15(r16_15) do
          for r25_15, r26_15 in pairs(r21_15) do
            if type(r26_15) == "table" then
              r8_15, r9_15 = r3_0.calc_checksum2({
                r5_15,
                r15_15,
                r20_15,
                r26_15.type,
                r26_15.flag
              })
              r3_15:reset()
              r3_15:bind_names({
                uid = r5_15,
                map = r15_15,
                stage = r20_15,
                type = r26_15.type,
                flag = r26_15.flag,
                date = r9_15,
                hex = r8_15,
              })
              r3_15:step()
            end
          end
        end
      end
      r3_15:finalize()
      r3_15 = r2_15:prepare("REPLACE INTO gc (uid, category, flag)" .. " VALUES (:uid, :category, :flag)")
      assert(r3_15, r2_15:errmsg())
      for r15_15, r16_15 in pairs(r4_15.gc) do
        r3_15:reset()
        r3_15:bind_names({
          uid = r5_15,
          category = r15_15,
          flag = r16_15,
        })
        r3_15:step()
      end
      r3_15:finalize()
      r3_15 = r2_15:prepare("REPLACE INTO gc_counter (uid, type, count, date, hex)" .. " VALUES (:uid, :type, :count, :date, :hex)")
      assert(r3_15, r2_15:errmsg())
      for r15_15, r16_15 in pairs(r4_15.gc_counter) do
        local r17_15 = r3_0.calc_checksum2
        r8_15, r0_15 = r17_15({
          r5_15,
          r15_15,
          r16_15
        })
        r3_15:reset()
        r3_15:bind_names({
          uid = r5_15,
          type = r15_15,
          count = r16_15,
          date = r9_15,
          hex = r8_15,
        })
        r3_15:step()
      end
      r3_15:finalize()
      r3_15 = r2_15:prepare("REPLACE INTO gc_daily (uid, day, count, date, hex)" .. " VALUES (:uid, :day, :count, :date, :hex)")
      assert(r3_15, r2_15:errmsg())
      r6_15 = r4_15.gc_daily
      r8_15, r9_15 = r3_0.calc_checksum2({
        r5_15,
        r6_15.day,
        r6_15.count
      })
      r3_15:bind_names({
        uid = r5_15,
        day = r6_15.day,
        count = r6_15.count,
        date = r9_15,
        hex = r8_15,
      })
      r3_15:step()
      r3_15:finalize()
      r3_15 = r2_15:prepare("REPLACE INTO score " .. " (uid, map, stage, type, score, date, hex)" .. " VALUES (:uid, :map, :stage, :type, :score, :date, :hex)")
      assert(r3_15, r2_15:errmsg())
      for r15_15, r16_15 in pairs(r4_15.score) do
        local r17_15 = pairs
        for r20_15, r21_15 in r17_15(r16_15) do
          r8_15, r9_15 = r3_0.calc_checksum2({
            r5_15,
            r15_15,
            r20_15,
            r21_15.type,
            r21_15.score
          })
          r3_15:reset()
          r3_15:bind_names({
            uid = r5_15,
            map = r15_15,
            stage = r20_15,
            type = r21_15.type,
            score = r21_15.score,
            date = r9_15,
            hex = r8_15,
          })
          r3_15:step()
        end
      end
      r3_15:finalize()
      r2_15:close()
    end
  end,
  GetLeaderboardData = function()
    -- line: [627, 685] id: 16
    local r2_16 = {}
    local r4_16 = r0_0.open(r6_0())
    local r5_16 = {}
    local r1_16 = {}
    for r9_16 in r4_16:nrows("SELECT map, stage, type, flag FROM achievement ORDER BY map, stage") do
      local r10_16 = r9_16.map
      local r11_16 = r9_16.stage
      if r1_16[r10_16] == nil then
        r1_16[r10_16] = {}
      end
      if r1_16[r10_16][r11_16] == nil then
        r1_16[r10_16][r11_16] = {}
      end
      r5_16 = {
        type = r9_16.type,
        flag = r9_16.flag,
      }
      table.insert(r1_16[r10_16][r11_16], r5_16)
    end
    r2_16.achievement = r1_16
    r1_16 = {}
    for r9_16 in r4_16:nrows("SELECT category, flag FROM gc") do
      r1_16[r9_16.category] = r9_16.flag
    end
    r2_16.gc = r1_16
    r1_16 = {}
    for r9_16 in r4_16:nrows("SELECT type, count FROM gc_counter") do
      r1_16[r9_16.type] = r9_16.count
    end
    r2_16.gc_counter = r1_16
    r1_16 = {}
    for r9_16 in r4_16:nrows("SELECT day, count FROM gc_daily LIMIT 1") do
      r1_16 = {
        day = r9_16.day,
        count = r9_16.count,
      }
    end
    r2_16.gc_daily = r1_16
    r1_16 = {}
    for r9_16 in r4_16:nrows("SELECT map, stage, score, type FROM score ORDER BY map, stage") do
      local r10_16 = r9_16.map
      local r11_16 = r9_16.stage
      if r1_16[r10_16] == nil then
        r1_16[r10_16] = {}
      end
      r1_16[r10_16][r11_16] = {
        type = r9_16.type,
        score = r9_16.score,
      }
    end
    r2_16.score = r1_16
    r4_16:close()
  end,
}
