-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("sqlite3")
local r1_0 = require("json")
local r2_0 = require("crypto")
local r3_0 = require("resource.char_define")
local r4_0 = "game.sqlite"
local r5_0 = require("db.BaseDB")
local r6_0 = nil
local r7_0 = nil
local r8_0 = nil
local r9_0 = nil
local r10_0 = nil
local r11_0 = 3
local r12_0 = {
  {
    "user",
    1,
    nil
  },
  {
    "option",
    4,
    r7_0
  },
  {
    "summon",
    4,
    r8_0
  },
  {
    "map",
    1,
    nil
  },
  {
    "stage",
    1,
    nil
  },
  {
    "resume",
    2,
    r9_0
  },
  {
    "twitter",
    1,
    nil
  },
  {
    "item",
    1,
    nil
  },
  {
    "hexdump",
    1,
    nil
  },
  {
    "score",
    1,
    nil
  },
  {
    "info",
    1,
    nil
  },
  {
    "recovery",
    2,
    r10_0
  },
  {
    "invite",
    1,
    nil
  },
  {
    "startup",
    1,
    nil
  }
}
function r7_0(r0_1, r1_1)
  -- line: [36, 108] id: 1
  if r1_1 == 1 then
    r0_1:exec("CREATE TEMPORARY TABLE opt_backup (" .. "uid INTEGER PRIMARY KEY," .. "bgm BOOL DEFAULT 1," .. "se BOOL DEFAULT 1," .. "confirm BOOL DEFAULT 1," .. "grid BOOL DEFAULT 1," .. "flag BOOL DEFAULT 0)")
    r0_1:exec("INSERT INTO opt_backup" .. " SELECT uid, bgm, se, confirm, grid, flag FROM option")
    r0_1:exec("DROP TABLE option")
    r0_1:exec(r6_0(3))
    r0_1:exec("INSERT INTO option" .. " SELECT uid, bgm, se, confirm, grid, 1, \"jp\", flag FROM opt_backup")
    r0_1:exec("DROP TABLE opt_backup")
    local r3_1 = r0_1:prepare("UPDATE option SET voice=1,language=:language")
    assert(r3_1, debug.traceback())
    r3_1:bind_names({
      language = _G.UILanguage,
    })
    r3_1:step()
    r3_1:finalize()
  elseif r1_1 == 2 then
    r0_1:exec("CREATE TEMPORARY TABLE opt_backup (" .. "uid INTEGER PRIMARY KEY," .. "bgm BOOL DEFAULT 1," .. "se BOOL DEFAULT 1," .. "confirm BOOL DEFAULT 1," .. "grid BOOL DEFAULT 1," .. "voice BOOL DEFAULT 1," .. "language TEXT," .. "flag BOOL DEFAULT 0)")
    r0_1:exec("INSERT INTO opt_backup" .. " SELECT uid, bgm, se, confirm, grid, voice, language, flag FROM option")
    r0_1:exec("DROP TABLE option")
    r0_1:exec(r6_0(3))
    r0_1:exec("INSERT INTO option" .. " SELECT uid, bgm, se, confirm, grid, voice, language, 1, flag FROM opt_backup")
    r0_1:exec("DROP TABLE opt_backup")
  elseif r1_1 == 3 then
    r0_1:exec("CREATE TEMPORARY TABLE opt_backup (" .. "uid INTEGER PRIMARY KEY," .. "bgm BOOL DEFAULT 1," .. "se BOOL DEFAULT 1," .. "confirm BOOL DEFAULT 1," .. "grid BOOL DEFAULT 1," .. "voice BOOL DEFAULT 1," .. "language TEXT," .. "local_notification BOOL DEFAULT 1, " .. "flag BOOL DEFAULT 0)")
    r0_1:exec("INSERT INTO opt_backup" .. " SELECT uid, bgm, se, confirm, grid, voice, language, local_notification, flag FROM option")
    r0_1:exec("DROP TABLE option")
    r0_1:exec(r6_0(3))
    r0_1:exec("INSERT INTO option" .. " SELECT uid, bgm, se, confirm, grid, voice, 2, language, local_notification, flag FROM opt_backup")
    r0_1:exec("DROP TABLE opt_backup")
  end
end
local function r13_0(r0_2, r1_2, r2_2)
  -- line: [110, 120] id: 2
  local r3_2 = r0_2:prepare("REPLACE INTO summon" .. " (uid, sid, lock, four, hex, date)" .. " VALUES (?, ?, ?, ?, ?, ?)")
  local r4_2 = nil
  local r5_2 = nil
  r4_2, r5_2 = r5_0.calc_checksum({
    r1_2,
    r2_2,
    1,
    1
  })
  r3_2:reset()
  r3_2:bind_values(r1_2, r2_2, 1, 1, r4_2, r5_2)
  r3_2:step()
  r3_2:finalize()
end
function r8_0(r0_3, r1_3)
  -- line: [122, 153] id: 3
  if r1_3 < 2 then
    local r3_3 = {}
    for r7_3 in r0_3:nrows("SELECT uid FROM summon GROUP BY uid") do
      table.insert(r3_3, r7_3.uid)
    end
    for r7_3, r8_3 in pairs(r3_3) do
      for r12_3 = 13, 20, 1 do
        r13_0(r0_3, r8_3, r12_3)
      end
    end
  elseif r1_3 == 2 or r1_3 == 3 then
    local r3_3 = {}
    for r7_3 in r0_3:nrows("SELECT uid FROM summon GROUP BY uid") do
      table.insert(r3_3, r7_3.uid)
    end
    for r7_3, r8_3 in pairs(r3_3) do
      for r12_3 = 21, 50, 1 do
        r13_0(r0_3, r8_3, r12_3)
      end
    end
  end
end
function r9_0(r0_4, r1_4)
  -- line: [155, 158] id: 4
  r0_4:exec("DROP TABLE resume")
  r0_4:exec("DROP TABLE resume_rewind")
end
function r10_0(r0_5, r1_5)
  -- line: [161, 163] id: 5
  r0_5:exec("DROP TABLE recovery")
end
local function r14_0()
  -- line: [165, 167] id: 6
  return system.pathForFile(r4_0, system.DocumentsDirectory)
end
local function r15_0()
  -- line: [169, 171] id: 7
  return system.pathForFile("backup", system.DocumentsDirectory)
end
local function r16_0()
  -- line: [174, 178] id: 8
  util.CopyFile(r14_0(), r15_0())
end
local function r17_0()
  -- line: [180, 184] id: 9
  util.CopyFile(r15_0(), r14_0())
end
local function r18_0()
  -- line: [186, 189] id: 10
  os.remove(r14_0())
  os.remove(r15_0())
end
function r6_0(r0_11)
  -- line: [192, 220] id: 11
  local r1_11 = nil	-- notice: implicit variable refs by block#[4]
  if r0_11 == 2 then
    r1_11 = "CREATE TABLE IF NOT EXISTS option (" .. "uid INTEGER PRIMARY KEY," .. "bgm BOOL DEFAULT 1," .. "se BOOL DEFAULT 1," .. "confirm BOOL DEFAULT 1," .. "grid BOOL DEFAULT 1," .. "voice BOOL DEFAULT 1," .. "language TEXT," .. "local_notification BOOL DEFAULT 1, " .. "flag BOOL DEFAULT 0)"
  elseif r0_11 == 3 then
    r1_11 = "CREATE TABLE IF NOT EXISTS option (" .. "uid INTEGER PRIMARY KEY," .. "bgm BOOL DEFAULT 1," .. "se BOOL DEFAULT 1," .. "confirm BOOL DEFAULT 1," .. "grid BOOL DEFAULT 1," .. "voice BOOL DEFAULT 1," .. "voice_type INTEGER DEFAULT 1," .. "language TEXT," .. "local_notification BOOL DEFAULT 1, " .. "flag BOOL DEFAULT 0)"
  end
  assert(r1_11, debug.traceback())
  return r1_11
end
local function r19_0(r0_12)
  -- line: [223, 279] id: 12
  local r1_12 = r0_12:prepare("SELECT COUNT(*) AS count FROM version WHERE flag=0")
  if r1_12 == nil then
    return false
  end
  local r2_12 = 0
  for r6_12 in r1_12:nrows() do
    r2_12 = r6_12.count
  end
  r1_12:finalize()
  if r2_12 < 1 then
    for r6_12, r7_12 in pairs(r12_0) do
      local r8_12 = r7_12[1]
      local r9_12 = r7_12[2]
      local r10_12 = r0_12:prepare("INSERT INTO version" .. " (name, version)" .. " VALUES (?, ?)")
      r10_12:reset()
      r10_12:bind_values(r8_12, r9_12)
      r10_12:step()
      r10_12:finalize()
    end
  else
    local r4_12 = {}
    for r8_12 in r0_12:nrows("SELECT name, version FROM version") do
      r4_12[r8_12.name] = r8_12.version
    end
    for r8_12, r9_12 in pairs(r12_0) do
      local r10_12 = r9_12[1]
      local r11_12 = r9_12[2]
      local r12_12 = r9_12[3]
      local r13_12 = r4_12[r10_12]
      local r14_12 = true
      if r13_12 == nil then
        r14_12 = false
      elseif r13_12 ~= r11_12 and r12_12 then
        r12_12(r0_12, r13_12)
        r14_12 = false
      end
      if not r14_12 then
        local r15_12 = r0_12:prepare("REPLACE INTO version" .. " (name, version)" .. " VALUES (?, ?)")
        r15_12:reset()
        r15_12:bind_values(r10_12, r11_12)
        r15_12:step()
        r15_12:finalize()
      end
    end
  end
  return true
end
local function r20_0()
  -- line: [281, 286] id: 13
  native.showAlert("DefenseWitches", r5_0.GetMessage(53), {
    "OK"
  })
  r17_0()
end
local function r21_0()
  -- line: [288, 292] id: 14
  native.showAlert("DefenseWitches", r5_0.GetMessage(54), {
    "OK"
  })
  r18_0()
end
local function r25_0(r0_18, r1_18, r2_18)
  -- line: [648, 676] id: 18
  local r3_18 = false
  if r2_18 == nil then
    r2_18 = r0_0.open(r14_0())
    r3_18 = true
  end
  local r4_18 = nil
  local r5_18 = nil
  r4_18 = r2_18:prepare("SELECT four FROM summon" .. " WHERE uid=? AND sid=? AND flag=0")
  r4_18:bind_values(r0_18, r1_18)
  for r9_18 in r4_18:nrows() do
    r5_18 = r9_18.four
  end
  r4_18:finalize()
  if r3_18 then
    r2_18:close()
  end
  if _G.IsReleaseAllFlag and _G.IsReleaseAllCharacter then
    r5_18 = 0
  end
  return r5_18
end
local function r26_0(r0_19, r1_19)
  -- line: [679, 693] id: 19
  r16_0()
  local r3_19 = r0_0.open(r14_0())
  local r4_19 = nil
  local r6_19, r7_19 = r5_0.calc_checksum({
    r0_19,
    r1_19,
    0,
    r25_0(r0_19, r1_19, r3_19)
  })
  r4_19 = r3_19:prepare("UPDATE summon SET" .. " lock=0, hex=?, date=? WHERE uid=? AND sid=?")
  r4_19:bind_values(r6_19, r7_19, r0_19, r1_19)
  r4_19:step()
  r4_19:finalize()
  r3_19:close()
end
local function r27_0(r0_20)
  -- line: [696, 719] id: 20
  r16_0()
  local r2_20 = r0_0.open(r14_0())
  local r3_20 = r2_20:prepare("REPLACE INTO summon" .. " (uid, sid, lock, four, hex, date)" .. " VALUES (?, ?, ?, ?, ?, ?)")
  local r4_20 = nil
  local r5_20 = nil
  for r9_20 = 1, 50, 1 do
    r4_20, r5_20 = r5_0.calc_checksum({
      r0_20,
      r9_20,
      1,
      1
    })
    r3_20:reset()
    r3_20:bind_values(r0_20, r9_20, 1, 1, r4_20, r5_20)
    r3_20:step()
  end
  r3_20:finalize()
  r2_20:close()
  r26_0(r0_20, 1)
  r26_0(r0_20, 15)
end
local function r28_0(r0_21)
  -- line: [722, 762] id: 21
  local r2_21 = r0_0.open(r14_0())
  local r3_21 = r2_21:prepare("SELECT sid, lock, four, hex, date FROM summon" .. " WHERE uid=? AND flag=0 ORDER BY sid")
  r3_21:bind_values(r0_21)
  local r4_21 = {}
  local r5_21 = {}
  local r6_21 = nil
  local r7_21 = nil
  for r11_21 in r3_21:nrows() do
    r6_21, r7_21 = r5_0.calc_checksum({
      r0_21,
      r11_21.sid,
      r11_21.lock,
      r11_21.four
    }, r11_21.date)
    if r6_21 ~= r11_21.hex then
      r5_0.checksum_error("summon")
      r4_21 = {}
      break
    else
      local r12_21 = r11_21.sid
      local r13_21 = r11_21.lock
      if r13_21 > 0 then
        r13_21 = 3 or 1
      else
        goto label_49	-- block#5 is visited secondly
      end
      r4_21[r12_21] = r13_21
      r5_21[r11_21.sid] = 0 < r11_21.four
    end
  end
  r3_21:finalize()
  r2_21:close()
  if _G.IsReleaseAllFlag and _G.IsReleaseAllCharacter then
    local r8_21 = {}
    local r9_21 = {}
    for r13_21, r14_21 in pairs(r4_21) do
      r8_21[r13_21] = 0
      r9_21[r13_21] = false
    end
    r4_21 = nil
    r4_21 = r8_21
    r5_21 = nil
    r5_21 = r9_21
  end
  return r4_21, r5_21
end
local function r31_0(r0_24, r1_24)
  -- line: [819, 832] id: 24
  r16_0()
  local r3_24 = r0_0.open(r14_0())
  local r4_24 = nil
  local r5_24, r6_24 = r5_0.calc_checksum({
    r0_24,
    r1_24,
    0,
    0
  })
  r4_24 = r3_24:prepare("UPDATE summon SET" .. " lock=0, four=0, hex=?, date=? WHERE uid=? AND sid=?")
  r4_24:bind_values(r5_24, r6_24, r0_24, r1_24)
  r4_24:step()
  r4_24:finalize()
  r3_24:close()
end
local function r32_0(r0_25)
  -- line: [835, 872] id: 25
  local r2_25 = r0_0.open(r14_0())
  local r3_25 = r2_25:prepare("SELECT mapid, lock FROM map" .. " WHERE uid=? AND flag=0 ORDER BY mapid")
  r3_25:bind_values(r0_25)
  local r4_25 = {}
  local r5_25 = {
    r0_25
  }
  for r9_25 in r3_25:nrows() do
    r4_25[r9_25.mapid] = r9_25.lock
    table.insert(r5_25, r9_25.lock)
  end
  r3_25:finalize()
  if 0 < table.maxn(r4_25) and not r5_0.check_checksum(r2_25, "map", r5_25) then
    r5_0.checksum_error("MAP")
    r4_25 = {}
  end
  r2_25:close()
  if _G.IsReleaseAllFlag and _G.IsReleaseAllWorld then
    local r6_25 = {}
    for r10_25, r11_25 in pairs(r4_25) do
      r6_25[r10_25] = 0
    end
    r4_25 = nil
    r4_25 = r6_25
  end
  return r4_25
end
local function r35_0(r0_28, r1_28)
  -- line: [929, 967] id: 28
  local r3_28 = r0_0.open(r14_0())
  local r4_28 = r3_28:prepare("SELECT stageid, lock FROM stage" .. " WHERE uid=? AND mapid=? AND flag=0 ORDER BY stageid")
  r4_28:bind_values(r0_28, r1_28)
  local r5_28 = {}
  local r6_28 = {
    r0_28,
    r1_28
  }
  for r10_28 in r4_28:nrows() do
    r5_28[r10_28.stageid] = r10_28.lock
    table.insert(r6_28, r10_28.lock)
  end
  r4_28:finalize()
  if 0 < table.maxn(r5_28) and not r5_0.check_checksum(r3_28, string.format("map%02d", r1_28), r6_28) then
    r5_0.checksum_error("STAGE")
    r5_28 = {}
  end
  r3_28:close()
  if _G.IsReleaseAllFlag and _G.IsReleaseAllStage then
    local r7_28 = {}
    for r11_28, r12_28 in pairs(r5_28) do
      r7_28[r11_28] = 0
    end
    r5_28 = nil
    r5_28 = r7_28
  end
  return r5_28
end
local function r41_0(r0_34, r1_34, r2_34)
  -- line: [1113, 1128] id: 34
  r16_0()
  local r4_34 = r0_0.open(r14_0())
  r5_0.begin_transcation(r4_34)
  local r5_34 = r4_34:prepare("REPLACE INTO user" .. " (uid, id, key) VALUES (?, ?, ?)")
  r5_34:bind_values(r0_34, r1_34, r2_34)
  r5_34:step()
  r5_34:finalize()
  r5_0.write_checksum(r4_34, "user", {
    r0_34,
    r1_34,
    r2_34
  })
  r5_0.commit(r4_34)
  r4_34:close()
end
local function r44_0(r0_37, r1_37)
  -- line: [1158, 1173] id: 37
  r16_0()
  local r2_37 = false
  if r1_37 == nil then
    r1_37 = r0_0.open(r14_0())
    r2_37 = true
  end
  local r3_37 = r1_37:prepare("DELETE FROM item WHERE uid=?")
  r3_37:bind_values(r0_37)
  r3_37:step()
  r3_37:finalize()
  if r2_37 then
    r1_37:close()
  end
end
local function r49_0(r0_42)
  -- line: [1278, 1294] id: 42
  local r2_42 = r0_0.open(r14_0())
  local r3_42 = r2_42:prepare("SELECT count(*) AS count FROM stage" .. " WHERE uid=? AND mapid=1 AND stageid=2 AND lock = 0")
  r3_42:bind_values(r0_42)
  local r4_42 = 0
  for r8_42 in r3_42:nrows() do
    r4_42 = r8_42.count
  end
  r3_42:finalize()
  r2_42:close()
  return 0 < r4_42
end
local function r51_0(r0_44, r1_44, r2_44, r3_44)
  -- line: [1322, 1345] id: 44
  r16_0()
  local r4_44 = false
  if r3_44 == nil then
    r3_44 = r0_0.open(r14_0())
    r4_44 = true
  end
  if type(r2_44) == "boolean" then
    if r2_44 then
      r2_44 = 1
    else
      r2_44 = 0
    end
  end
  local r5_44, r6_44 = r5_0.calc_checksum({
    r0_44,
    r1_44,
    r2_44
  })
  local r7_44 = r3_44:prepare("INSERT INTO info" .. " (uid, item, hex, date, lock)" .. " VALUES" .. " (?, ?, ?, ?, ?)")
  r7_44:bind_values(r0_44, r1_44, r5_44, r6_44, r2_44)
  r7_44:step()
  r7_44:finalize()
  if r4_44 then
    r3_44:close()
  end
end
local function r64_0(r0_57)
  -- line: [1591, 1622] id: 57
  local r2_57 = r0_0.open(r14_0())
  r5_0.begin_transcation(r2_57)
  local r3_57 = r2_57:prepare("REPLACE INTO inventory_item (uid, itemid, quantity, hex, cdate)" .. " VALUES (:uid, :itemid, :quantity, :hex, :cdate)")
  for r7_57, r8_57 in pairs(r0_57) do
    if r8_57.uid ~= nil and r8_57.itemid ~= nil and r8_57.quantity ~= nil then
      local r9_57, r10_57 = r5_0.calc_checksum({
        r8_57.uid,
        r8_57.itemid,
        r8_57.quantity
      })
      r3_57:reset()
      r3_57:bind_names({
        uid = r8_57.uid,
        itemid = r8_57.itemid,
        quantity = r8_57.quantity,
        hex = r9_57,
        cdate = r10_57,
      })
      r3_57:step()
    end
  end
  r3_57:finalize()
  r5_0.commit(r2_57)
  r2_57:close()
end
local function r68_0(r0_61)
  -- line: [1754, 1788] id: 61
  local r2_61 = r0_0.open(r14_0())
  local r3_61 = r2_61:prepare("SELECT uid, bingo_id, mission_no, data, cleared, hex, date FROM bingo_progress" .. " WHERE uid=? AND bingo_id=? AND cleared=?")
  r3_61:bind_values(_G.UserID, r0_61, 1)
  local r4_61 = {}
  local r5_61 = nil
  local r6_61 = nil
  local r7_61 = nil
  local r8_61 = nil
  for r12_61 in r3_61:nrows() do
    r6_61, r7_61 = r5_0.calc_checksum({
      _G.UserID,
      r12_61.bingo_id,
      r12_61.mission_no,
      r12_61.data,
      r12_61.cleared
    }, r12_61.date)
    if r6_61 ~= r12_61.hex then
      r5_0.checksum_error("bingo progress")
      return nil
    else
      r8_61 = r12_61.cleared ~= 0
      r5_61 = {
        missionNo = r12_61.mission_no,
        cleared = r8_61,
      }
      table.insert(r4_61, r5_61)
    end
  end
  r3_61:finalize()
  r2_61:close()
  return r4_61
end
local function r78_0(r0_71, r1_71)
  -- line: [2154, 2197] id: 71
  if _G.UserID == nil or r0_71 == nil or r1_71 == nil then
    return nil
  end
  local r3_71 = r0_0.open(r14_0())
  local r4_71 = r3_71:prepare("SELECT uid, mapid, stageid, clear_count, hex, date FROM stage_clear" .. " WHERE uid=? AND mapid=? AND stageid=?")
  r4_71:bind_values(_G.UserID, r0_71, r1_71)
  local r5_71 = nil
  local r6_71 = nil
  local r7_71 = nil
  for r11_71 in r4_71:nrows() do
    r6_71, r7_71 = r5_0.calc_checksum({
      _G.UserID,
      r11_71.mapid,
      r11_71.stageid,
      r11_71.clear_count
    }, r11_71.date)
    if r6_71 ~= r11_71.hex then
      r5_0.checksum_error("stage clear")
      return nil
    else
      r5_71 = {
        mapId = r11_71.mapid,
        stageId = r11_71.stageid,
        clearCount = r11_71.clear_count,
      }
    end
  end
  r4_71:finalize()
  r3_71:close()
  if r5_71 == nil then
    local r8_71 = r11_0
    local r9_71 = 0
    r5_71 = {
      mapId = r0_71,
      stageId = r1_71,
      clearCount = 0,
    }
  end
  return r5_71
end
local function r79_0(r0_72, r1_72, r2_72)
  -- line: [2203, 2237] id: 72
  if _G.UserID == nil or r0_72 == nil or r1_72 == nil or r2_72 == nil or r2_72 < 0 then
    return false
  end
  local r4_72 = r0_0.open(r14_0())
  r5_0.begin_transcation(r4_72)
  local r5_72 = r4_72:prepare("REPLACE INTO stage_clear (uid, mapid, stageid, clear_count, hex, date)" .. " VALUES (:uid, :mapid, :stageid, :clear_count, :hex, :date)")
  local r6_72, r7_72 = r5_0.calc_checksum({
    _G.UserID,
    r0_72,
    r1_72,
    r2_72
  })
  r5_72:reset()
  r5_72:bind_names({
    uid = _G.UserID,
    mapid = r0_72,
    stageid = r1_72,
    clear_count = r2_72,
    hex = r6_72,
    date = r7_72,
  })
  r5_72:step()
  r5_72:finalize()
  r5_0.commit(r4_72)
  r4_72:close()
end
local function r82_0(r0_75, r1_75, r2_75, r3_75)
  -- line: [2326, 2359] id: 75
  if r0_75 == nil or r1_75 == nil or r2_75 == nil or r3_75 == nil then
    return false
  end
  local r5_75 = r0_0.open(r14_0())
  r5_0.begin_transcation(r5_75)
  local r6_75 = r5_75:prepare("REPLACE INTO summon_rank (uid, sid, rank, exp, hex, date)" .. " VALUES (:uid, :sid, :rank, :exp, :hex, :date)")
  local r7_75, r8_75 = r5_0.calc_checksum({
    r0_75,
    r1_75,
    r2_75,
    r3_75
  })
  r6_75:reset()
  r6_75:bind_names({
    uid = r0_75,
    sid = r1_75,
    rank = r2_75,
    exp = r3_75,
    hex = r7_75,
    date = r8_75,
  })
  r6_75:step()
  r6_75:finalize()
  r5_0.commit(r5_75)
  r5_75:close()
end
local function r84_0()
  -- line: [2420, 2469] id: 77
  if _G.UserID == nil then
    return nil
  end
  local r1_77 = r0_0.open(r14_0())
  local r2_77 = r1_77:prepare("SELECT uid, orb_remain, orb_max, exp, used_time, hex, date FROM evo_orb_exp" .. " WHERE uid=?")
  r2_77:bind_values(_G.UserID)
  local r3_77 = nil
  local r4_77 = nil
  local r5_77 = nil
  for r9_77 in r2_77:nrows() do
    r4_77, r5_77 = r5_0.calc_checksum({
      _G.UserID,
      r9_77.orb_remain,
      r9_77.orb_max,
      r9_77.exp,
      r9_77.used_time
    }, r9_77.date)
    if r4_77 ~= r9_77.hex then
      r5_0.checksum_error("evo orb exp")
      r3_77 = {
        orbRemain = 0,
        orbMax = 0,
        exp = 0,
        usedTime = 0,
      }
      return r3_77
    else
      r3_77 = {
        orbRemain = r9_77.orb_remain,
        orbMax = r9_77.orb_max,
        exp = r9_77.exp,
        usedTime = r9_77.used_time,
      }
    end
  end
  r2_77:finalize()
  r1_77:close()
  if r3_77 == nil then
    local r6_77 = r11_0
    r3_77 = {
      orbRemain = r6_77,
      orbMax = r6_77,
      exp = 0,
      usedTime = 0,
    }
  end
  return r3_77
end
return {
  InitData = function()
    -- line: [294, 582] id: 15
    local r0_15 = false
    repeat
      local r1_15 = true
      local r2_15 = r14_0()
      local r3_15 = r0_0.open(r2_15)
      local r4_15 = nil
      if _G.IsSimulator then
        DebugPrint("savedata:" .. r2_15)
      end
      local r5_15 = false
      if r5_15 then
        r4_15 = "PRAGMA fullfsync=true"
        r5_15 = r3_15:exec(r4_15)
        if r5_15 ~= r0_0.OK then
          DebugPrint(r3_15:errcode(), r3_15:errmsg())
        end
      end
      r5_0.begin_transcation(r3_15)
      r3_15:exec("CREATE TABLE IF NOT EXISTS user (" .. "uid INTEGER PRIMARY KEY," .. "id TEXT," .. "key TEXT," .. "flag BOOL DEFAULT 0)")
      r3_15:exec(r6_0(3))
      r3_15:exec("CREATE TABLE IF NOT EXISTS summon (" .. "uid INTEGER," .. "sid INTEGER," .. "lock BOOL," .. "four BOOL DEFAULT 1," .. "hex TEXT," .. "date TEXT," .. "flag BOOL DEFAULT 0," .. "PRIMARY KEY (uid, sid))")
      r3_15:exec("CREATE TABLE IF NOT EXISTS map (" .. "uid INTEGER," .. "mapid INTEGER," .. "lock BOOL," .. "flag BOOL DEFAULT 0," .. "PRIMARY KEY (uid, mapid))")
      r3_15:exec("CREATE TABLE IF NOT EXISTS stage (" .. "uid INTEGER," .. "mapid INTEGER," .. "stageid INTEGER," .. "lock BOOL," .. "flag BOOL DEFAULT 0," .. "PRIMARY KEY (uid, mapid, stageid))")
      r3_15:exec("CREATE TABLE IF NOT EXISTS twitter (" .. "uid INTEGER PRIMARY KEY," .. "token TEXT," .. "secret TEXT," .. "twitterid TEXT," .. "user TEXT," .. "flag BOOL DEFAULT 0)")
      r3_15:exec("CREATE TABLE IF NOT EXISTS item (" .. "idx INTEGER PRIMARY KEY AUTOINCREMENT," .. "uid INTEGER," .. "itemid INTEGER," .. "wave INTEGER," .. "hex TEXT," .. "date TEXT," .. "flag BOOL DEFAULT 0)")
      r3_15:exec("CREATE TABLE IF NOT EXISTS score (" .. "sid INTEGER PRIMARY KEY AUTOINCREMENT," .. "uid INTEGER," .. "mapid INTEGER," .. "stageid INTEGER," .. "hp INTEGER," .. "perfect INTEGER," .. "score INTEGER," .. "hex TEXT," .. "date TEXT," .. "cdate DATETIME DEFAULT (CURRENT_TIMESTAMP)," .. "flag BOOL DEFAULT 0)")
      r3_15:exec("CREATE TABLE IF NOT EXISTS info (" .. "lid INTEGER PRIMARY KEY AUTOINCREMENT," .. "uid INTEGER," .. "item INTEGER," .. "hex TEXT," .. "date TEXT," .. "lock BOOL DEFAULT 1," .. "cdate DATETIME DEFAULT (CURRENT_TIMESTAMP)," .. "flag BOOL DEFAULT 0)")
      r3_15:exec("CREATE TABLE IF NOT EXISTS hexdump (" .. "name TEXT PRIMARY KEY," .. "hd TEXT," .. "date TEXT," .. "flag BOOL DEFAULT 0)")
      r3_15:exec("CREATE TABLE IF NOT EXISTS version (" .. "id INTEGER PRIMARY KEY AUTOINCREMENT," .. "name TEXT," .. "version INTEGER," .. "flag BOOL DEFAULT 0)")
      r3_15:exec("CREATE TABLE IF NOT EXISTS affiliate (" .. "name TEXT PRIMARY KEY," .. "used BOOL DEFAULT 0," .. "flag BOOL DEFAULT 0)")
      r3_15:exec("CREATE TABLE IF NOT EXISTS recovery (" .. "uid INTEGER PRIMARY KEY," .. "spell TEXT," .. "flag BOOL DEFAULT 0)")
      r3_15:exec("CREATE TABLE IF NOT EXISTS invite (" .. "uid INTEGER PRIMARY KEY," .. "code TEXT," .. "flag BOOL DEFAULT 0)")
      r3_15:exec("CREATE TABLE IF NOT EXISTS startup (" .. "uid INTEGER PRIMARY KEY," .. "flag BOOL DEFAULT 0)")
      r3_15:exec("CREATE TABLE IF NOT EXISTS inventory_item (" .. "uid INTEGER," .. "itemid INTEGER," .. "quantity INTEGER," .. "hex TEXT," .. "cdate DATETIME DEFAULT (CURRENT_TIMESTAMP)," .. "flag BOOL DEFAULT 0," .. "PRIMARY KEY (uid, itemid))")
      r3_15:exec("CREATE TABLE IF NOT EXISTS play_world_stage (" .. "uid INTEGER," .. "cur_world INTEGER," .. "cur_stage INTEGER," .. "hex TEXT," .. "cdate DATETIME DEFAULT (CURRENT_TIMESTAMP)," .. "flag BOOL DEFAULT 0," .. "PRIMARY KEY (uid))")
      r3_15:exec("CREATE TABLE IF NOT EXISTS server_access_time (" .. "uid INTEGER," .. "acs_id INTEGER," .. "hex TEXT," .. "date TEXT," .. "result INTEGER DEFAULT 0," .. "PRIMARY KEY (uid, acs_id))")
      r3_15:exec("CREATE TABLE IF NOT EXISTS login_bonus_recive (" .. "uid INTEGER," .. "result INTEGER DEFAULT 0," .. "PRIMARY KEY (uid))")
      r3_15:exec("CREATE TABLE IF NOT EXISTS summon_rank (" .. "uid INTEGER," .. "sid INTEGER," .. "rank INTEGER  DEFAULT 1," .. "exp INTEGER DEFAULT 0," .. "hex TEXT," .. "date TEXT," .. "PRIMARY KEY (uid, sid))")
      r3_15:exec("CREATE TABLE IF NOT EXISTS evo_orb_exp (" .. "uid INTEGER," .. "orb_remain INTEGER  DEFAULT 0," .. "orb_max INTEGER  DEFAULT 0," .. "exp INTEGER DEFAULT 0," .. "used_time INTEGER DEFAULT 0," .. "hex TEXT," .. "date TEXT," .. "PRIMARY KEY (uid))")
      r3_15:exec("CREATE TABLE IF NOT EXISTS stage_clear (" .. "uid INTEGER," .. "mapid INTEGER," .. "stageid INTEGER," .. "clear_count INTEGER  DEFAULT 0," .. "hex TEXT," .. "date TEXT," .. "PRIMARY KEY (uid, mapid, stageid))")
      r3_15:exec("CREATE TABLE IF NOT EXISTS bingo_progress (" .. "uid INTEGER," .. "bingo_id INTEGER," .. "mission_no INTEGER," .. "data TEXT," .. "cleared BOOL DEFAULT 0," .. "hex TEXT," .. "date TEXT," .. "PRIMARY KEY (uid, bingo_id, mission_no))")
      r3_15:exec("CREATE TABLE IF NOT EXISTS already_read (" .. "uid INTEGER," .. "event_id INTEGER," .. "state INTEGER DEFAULT 0," .. "hex TEXT," .. "date TEXT," .. "PRIMARY KEY (uid, event_id))")
      r4_15 = "CREATE TABLE IF NOT EXISTS invalid_transactions (" .. "uid INTEGER," .. "token TEXT," .. "receipt TEXT," .. "signature TEXT," .. "price TEXT," .. "hex TEXT," .. "date TEXT," .. "PRIMARY KEY (uid, signature))"
      r3_15:exec(r4_15)
      r5_15 = r19_0(r3_15)
      r5_0.commit(r3_15)
      r3_15:close()
      if not r5_15 then
        if not r0_15 then
          r20_0()
          r0_15 = true
        else
          r21_0()
        end
        r1_15 = false
      end
    until r1_15
  end,
  SaveOptionData = function(r0_16)
    -- line: [584, 609] id: 16
    r16_0()
    local r2_16 = r0_0.open(r14_0())
    r5_0.begin_transcation(r2_16)
    local r3_16 = r2_16:prepare("REPLACE INTO option" .. " (uid, bgm, se, confirm, grid, voice, voice_type, language, local_notification)" .. " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")
    assert(r3_16, r2_16:errmsg())
    local r4_16 = r0_16.bgm
    if r4_16 then
      r4_16 = 1 or 0
    else
      goto label_29	-- block#2 is visited secondly
    end
    local r5_16 = r0_16.se
    if r5_16 then
      r5_16 = 1 or 0
    else
      goto label_36	-- block#5 is visited secondly
    end
    local r6_16 = r0_16.confirm
    if r6_16 then
      r6_16 = 1 or 0
    else
      goto label_43	-- block#8 is visited secondly
    end
    local r7_16 = r0_16.grid
    if r7_16 then
      r7_16 = 1 or 0
    else
      goto label_50	-- block#11 is visited secondly
    end
    local r8_16 = r0_16.voice
    if r8_16 then
      r8_16 = 1 or 0
    else
      goto label_57	-- block#14 is visited secondly
    end
    local r9_16 = 2
    if r0_16.voice_type ~= nil then
      r9_16 = r0_16.voice_type
    end
    local r10_16 = r0_16.local_notification
    if r10_16 then
      r10_16 = 1 or 0
    else
      goto label_69	-- block#19 is visited secondly
    end
    r3_16:bind_values(r0_16.uid, r4_16, r5_16, r6_16, r7_16, r8_16, r9_16, r0_16.language, r10_16)
    r3_16:step()
    r3_16:finalize()
    r5_0.commit(r2_16)
    r2_16:close()
  end,
  LoadOptionData = function(r0_17)
    -- line: [612, 645] id: 17
    local r1_17 = {}
    local r3_17 = r0_0.open(r14_0())
    local r4_17 = r3_17:prepare("SELECT bgm, se, confirm, grid, voice, voice_type, language, local_notification" .. " FROM option" .. " WHERE uid=? AND flag=0 LIMIT 1")
    assert(r4_17, r3_17:errmsg())
    r4_17:bind_values(r0_17)
    for r8_17 in r4_17:nrows() do
      local r9_17 = r8_17.bgm
      if r9_17 > 0 then
        r9_17 = true or false
      else
        goto label_30	-- block#3 is visited secondly
      end
      r1_17.bgm = r9_17
      r9_17 = r8_17.se
      if r9_17 > 0 then
        r9_17 = true or false
      else
        goto label_38	-- block#6 is visited secondly
      end
      r1_17.se = r9_17
      r9_17 = r8_17.confirm
      if r9_17 > 0 then
        r9_17 = true or false
      else
        goto label_46	-- block#9 is visited secondly
      end
      r1_17.confirm = r9_17
      r9_17 = r8_17.grid
      if r9_17 > 0 then
        r9_17 = true or false
      else
        goto label_54	-- block#12 is visited secondly
      end
      r1_17.grid = r9_17
      r9_17 = r8_17.voice
      if r9_17 > 0 then
        r9_17 = true or false
      else
        goto label_62	-- block#15 is visited secondly
      end
      r1_17.voice = r9_17
      r1_17.voice_type = 2
      if r8_17.voice_type ~= nil and 0 < r8_17.voice_type then
        r1_17.voice_type = r8_17.voice_type
      end
      r1_17.language = r8_17.language
      if r8_17.local_notification ~= nil and 0 < r8_17.local_notification then
        r1_17.local_notification = true
      else
        r1_17.local_notification = false
      end
      r1_17.uid = r0_17
    end
    r4_17:finalize()
    r3_17:close()
    return r1_17
  end,
  InitSummonData = r27_0,
  LoadSummonData = r28_0,
  GetL4SummonData = r25_0,
  UnlockSummonData = r26_0,
  IsLockSummonData = function(r0_22, r1_22)
    -- line: [765, 781] id: 22
    r16_0()
    local r3_22 = r0_0.open(r14_0())
    local r4_22 = nil
    r4_22 = r3_22:prepare("select * from summon WHERE uid=? AND sid=?")
    r4_22:bind_values(r0_22, r1_22)
    local r5_22 = 1
    for r9_22 in r4_22:nrows() do
      r5_22 = r9_22.lock
    end
    r4_22:finalize()
    r3_22:close()
    return r5_22
  end,
  GetIsLockSummonData = function(r0_23, r1_23, r2_23)
    -- line: [786, 814] id: 23
    local r3_23 = false
    if r2_23 == nil then
      r2_23 = r0_0.open(r14_0())
      r3_23 = true
    end
    local r4_23 = nil
    local r5_23 = nil
    r4_23 = r2_23:prepare("SELECT lock FROM summon" .. " WHERE uid=? AND sid=? AND flag=0")
    r4_23:bind_values(r0_23, r1_23)
    for r9_23 in r4_23:nrows() do
      r5_23 = r9_23.lock
    end
    r4_23:finalize()
    if r3_23 then
      r2_23:close()
    end
    if _G.IsReleaseAllFlag and _G.IsReleaseAllCharacter then
      r5_23 = 0
    end
    return r5_23
  end,
  UnlockL4SummonData = r31_0,
  GetMapInfo = r32_0,
  UnlockMap = function(r0_26, r1_26)
    -- line: [875, 900] id: 26
    r16_0()
    local r3_26 = r0_0.open(r14_0())
    r5_0.begin_transcation(r3_26)
    local r4_26 = r3_26:prepare("REPLACE INTO map (uid, mapid, lock, flag)" .. " values (:uid, :mapid, 0, 0)")
    r4_26:bind_names({
      uid = r0_26,
      mapid = r1_26,
    })
    r4_26:step()
    r4_26:finalize()
    r4_26 = r3_26:prepare("SELECT lock FROM map" .. " WHERE uid=? AND flag=0 ORDER BY mapid")
    r4_26:bind_values(r0_26)
    local r5_26 = {
      r0_26
    }
    local r6_26 = nil
    for r10_26 in r4_26:nrows() do
      table.insert(r5_26, r10_26.lock)
    end
    r4_26:finalize()
    r5_0.write_checksum(r3_26, "map", r5_26)
    r5_0.commit(r3_26)
    r3_26:close()
  end,
  InitStageInfo = function(r0_27, r1_27)
    -- line: [904, 926] id: 27
    r16_0()
    local r3_27 = r0_0.open(r14_0())
    r5_0.begin_transcation(r3_27)
    local r4_27 = r3_27:prepare("REPLACE INTO stage" .. " (uid, mapid, stageid, lock)" .. " VALUES (?, ?, ?, ?)")
    local r5_27 = {
      r0_27,
      r1_27
    }
    for r9_27 = 1, 100, 1 do
      r4_27:reset()
      r4_27:bind_values(r0_27, r1_27, r9_27, 1)
      r4_27:step()
      table.insert(r5_27, 1)
    end
    r4_27:finalize()
    r5_0.write_checksum(r3_27, string.format("map%02d", r1_27), r5_27)
    r5_0.commit(r3_27)
    r3_27:close()
    return r32_0()
  end,
  GetStageInfo = r35_0,
  UnlockStage = function(r0_29, r1_29, r2_29)
    -- line: [970, 996] id: 29
    r16_0()
    local r4_29 = r0_0.open(r14_0())
    r5_0.begin_transcation(r4_29)
    local r5_29 = r4_29:prepare("UPDATE stage SET" .. " lock=0 WHERE uid=? AND mapid=? AND stageid=?")
    r5_29:bind_values(r0_29, r1_29, r2_29)
    r5_29:step()
    r5_29:finalize()
    r5_29 = r4_29:prepare("SELECT lock FROM stage" .. " WHERE uid=? AND mapid=? AND flag=0 ORDER BY stageid")
    r5_29:bind_values(r0_29, r1_29)
    local r6_29 = {
      r0_29,
      r1_29
    }
    local r7_29 = nil
    for r11_29 in r5_29:nrows() do
      table.insert(r6_29, r11_29.lock)
    end
    r5_29:finalize()
    r5_0.write_checksum(r4_29, string.format("map%02d", r1_29), r6_29)
    r5_0.commit(r4_29)
    r4_29:close()
  end,
  InitMapInfo = function(r0_30)
    -- line: [1000, 1023] id: 30
    r16_0()
    local r2_30 = r0_0.open(r14_0())
    r5_0.begin_transcation(r2_30)
    local r3_30 = r2_30:prepare("REPLACE INTO map" .. " (uid, mapid, lock)" .. " VALUES (?, ?, ?)")
    r3_30:reset()
    r3_30:bind_values(r0_30, 1, 0)
    r3_30:step()
    for r7_30 = 2, 10, 1 do
      r3_30:reset()
      r3_30:bind_values(r0_30, r7_30, 1)
      r3_30:step()
    end
    r3_30:finalize()
    r5_0.write_checksum(r2_30, "map", {
      r0_30,
      0,
      1,
      1,
      1,
      1,
      1,
      1,
      1,
      1,
      1
    })
    r5_0.commit(r2_30)
    r2_30:close()
  end,
  GetTwitterToken = function(r0_31)
    -- line: [1026, 1053] id: 31
    local r2_31 = r0_0.open(r14_0())
    local r3_31 = r2_31:prepare("SELECT token, secret, twitterid, user FROM twitter" .. " WHERE uid=? AND flag=0 LIMIT 1")
    r3_31:bind_values(r0_31)
    local r4_31 = nil
    for r8_31 in r3_31:nrows() do
      r4_31 = {}
      r4_31.token = r8_31.token
      r4_31.secret = r8_31.secret
      r4_31.twitterid = r8_31.twitterid
      r4_31.user = r8_31.user
    end
    r3_31:finalize()
    if r4_31 and not r5_0.check_checksum(r2_31, "twitter", {
      r0_31,
      r4_31.token,
      r4_31.secret,
      r4_31.twitterid,
      r4_31.user
    }) then
      r5_0.checksum_error("TWITTER")
      r4_31 = nil
    end
    r2_31:close()
    return r4_31
  end,
  SetTwitterToken = function(r0_32, r1_32, r2_32, r3_32, r4_32)
    -- line: [1057, 1074] id: 32
    r16_0()
    local r6_32 = r0_0.open(r14_0())
    r5_0.begin_transcation(r6_32)
    local r7_32 = r6_32:prepare("REPLACE INTO twitter" .. " (uid, token, secret, twitterid, user)" .. " VALUES (?, ?, ?, ?, ?)")
    r7_32:bind_values(r0_32, r1_32, r2_32, r3_32, r4_32)
    r7_32:step()
    r7_32:finalize()
    r5_0.write_checksum(r6_32, "twitter", {
      r0_32,
      r1_32,
      r2_32,
      r3_32,
      r4_32
    })
    r5_0.commit(r6_32)
    r6_32:close()
  end,
  GetUserID = function(r0_33)
    -- line: [1077, 1110] id: 33
    local r2_33 = r0_0.open(r14_0())
    local r3_33 = nil
    if r0_33 then
      r3_33 = "SELECT uid, id, key FROM user WHERE" .. " uid=\"" .. r0_33 .. "\" AND flag=0 LIMIT 1"
    else
      r3_33 = "SELECT uid, id, key FROM user WHERE flag=0 LIMIT 1"
    end
    local r4_33 = nil
    local r5_33 = nil
    for r9_33 in r2_33:nrows(r3_33) do
      r4_33 = {}
      r4_33.uid = r9_33.uid
      if r5_0.is_numeric(r9_33.id) then
        r4_33.id = math.floor(r9_33.id)
        r4_33.key = r9_33.key
      else
        r4_33.id = nil
        r4_33.key = nil
      end
    end
    if r4_33 and not r5_0.check_checksum(r2_33, "user", {
      r4_33.uid,
      r4_33.id,
      r4_33.key
    }) then
      r5_0.checksum_error("USER")
      r4_33 = nil
    end
    r2_33:close()
    return r4_33
  end,
  UpdateUserID = r41_0,
  InitUserID = function()
    -- line: [1131, 1135] id: 35
    local r0_35 = math.random(1, 1000000)
    r41_0(r0_35, "", "")
    return r0_35
  end,
  CountItemList = function(r0_36)
    -- line: [1139, 1155] id: 36
    local r2_36 = r0_0.open(r14_0())
    local r3_36 = r2_36:prepare("SELECT count(*) AS count FROM item" .. " WHERE flag=0 AND uid=?")
    r3_36:bind_values(r0_36)
    local r4_36 = 0
    for r8_36 in r3_36:nrows() do
      r4_36 = r8_36.count
    end
    r3_36:finalize()
    r2_36:close()
    return r4_36
  end,
  DeleteItemList = r44_0,
  UsingItemList = function(r0_38)
    -- line: [1176, 1205] id: 38
    local r2_38 = r0_0.open(r14_0())
    local r3_38 = r2_38:prepare("SELECT idx, itemid, wave, hex, date FROM item" .. " WHERE flag=0 AND uid=?" .. " ORDER BY itemid")
    r3_38:bind_values(r0_38)
    local r4_38 = nil
    local r5_38 = nil
    local r6_38 = nil
    local r7_38 = nil
    local r8_38 = nil
    local r9_38 = {}
    for r13_38 in r3_38:nrows() do
      r6_38 = r13_38.itemid
      r7_38 = r13_38.wave
      r8_38 = r13_38.idx
      r4_38, r5_38 = r5_0.calc_checksum({
        r0_38,
        r7_38,
        r6_38
      }, r13_38.date)
      if r4_38 == r13_38.hex then
        table.insert(r9_38, {
          r6_38,
          r7_38,
          r8_38
        })
      else
        r5_0.checksum_error("ITEM")
        r9_38 = {}
        r44_0(r0_38, r2_38)
        break
      end
    end
    r3_38:finalize()
    r2_38:close()
    return r9_38
  end,
  UseItem = function(r0_39, r1_39, r2_39)
    -- line: [1208, 1232] id: 39
    r16_0()
    local r4_39 = r0_0.open(r14_0())
    if type(r2_39) ~= "table" then
      r2_39 = {}
      r2_39[1] = r2_39
    end
    local r5_39 = nil
    local r6_39 = nil
    local r7_39 = r4_39:prepare("REPLACE INTO item" .. " (uid, itemid, wave, hex, date)" .. " VALUES (?, ?, ?, ?, ?)")
    for r11_39, r12_39 in pairs(r2_39) do
      r7_39:reset()
      r5_39, r6_39 = r5_0.calc_checksum({
        r0_39,
        r1_39,
        r12_39
      })
      r7_39:bind_values(r0_39, r12_39, r1_39, r5_39, r6_39)
      r7_39:step()
    end
    r7_39:finalize()
    r4_39:close()
  end,
  UpdateItemList = function(r0_40, r1_40, r2_40)
    -- line: [1236, 1256] id: 40
    r16_0()
    local r4_40 = r0_0.open(r14_0())
    r5_0.begin_transcation(r4_40)
    local r5_40 = nil
    local r6_40 = nil
    local r7_40 = nil
    local r8_40 = nil
    local r9_40 = nil
    for r13_40, r14_40 in pairs(r1_40) do
      r6_40 = r14_40[1]
      r7_40 = r14_40[2]
      r8_40, r9_40 = r5_0.calc_checksum({
        r0_40,
        r2_40,
        r6_40
      })
      r5_40 = r4_40:prepare("UPDATE item SET wave=?," .. "hex=?, date=? WHERE idx=?")
      r5_40:bind_values(r2_40, r8_40, r9_40, r7_40)
      r5_40:step()
      r5_40:finalize()
    end
    r5_0.commit(r4_40)
    r4_40:close()
  end,
  CheckScoreData = function(r0_41, r1_41, r2_41)
    -- line: [1259, 1275] id: 41
    local r4_41 = r0_0.open(r14_0())
    local r5_41 = r4_41:prepare("SELECT count(*) AS count FROM score" .. " WHERE uid=? AND mapid=? AND stageid=?")
    r5_41:bind_values(r0_41, r1_41, r2_41)
    local r6_41 = 0
    for r10_41 in r5_41:nrows() do
      r6_41 = r10_41.count
    end
    r5_41:finalize()
    r4_41:close()
    return 0 < r6_41
  end,
  CheckSecondStageData = r49_0,
  GetPerfectData = function(r0_43, r1_43)
    -- line: [1297, 1319] id: 43
    local r3_43 = r0_0.open(r14_0())
    local r4_43 = r3_43:prepare("SELECT perfect, stageid FROM score" .. " WHERE uid=? AND mapid=? AND flag=0" .. " ORDER BY stageid DESC")
    r4_43:bind_values(r0_43, r1_43)
    local r5_43 = nil
    local r6_43 = nil
    local r7_43 = nil
    for r11_43 in r4_43:nrows() do
      if r5_43 == nil then
        r5_43 = {}
      end
      r6_43 = r11_43.stageid
      r7_43 = 0 < r11_43.perfect
      if r5_43[r6_43] == nil or not r5_43[r6_43] then
        r5_43[r6_43] = r7_43
      end
    end
    r4_43:finalize()
    r3_43:close()
    return r5_43
  end,
  SetLockInfo = r51_0,
  GetLockInfo = function(r0_45, r1_45)
    -- line: [1351, 1379] id: 45
    local r3_45 = r0_0.open(r14_0())
    local r4_45 = r3_45:prepare("SELECT lock, hex, date FROM info" .. " WHERE uid=? AND item=? AND flag=0")
    r4_45:bind_values(r0_45, r1_45)
    local r5_45 = nil
    for r9_45 in r4_45:nrows() do
      r5_45 = {}
      local r10_45, r11_45 = r5_0.calc_checksum({
        r0_45,
        r1_45,
        r9_45.lock
      }, r9_45.date)
      if r10_45 ~= r9_45.hex then
        r5_0.checksum_error("INFO")
        r5_45 = true
      else
        r5_45 = 0 < r9_45.lock
      end
    end
    r4_45:finalize()
    if r5_45 == nil then
      r51_0(r0_45, r1_45, 1, r3_45)
      r5_45 = true
    end
    r3_45:close()
    return r5_45
  end,
  GetPassword = function(r0_46)
    -- line: [1383, 1397] id: 46
    local r2_46 = r0_0.open(r14_0())
    local r3_46 = r2_46:prepare("SELECT spell FROM recovery" .. " WHERE uid=? AND flag=0 LIMIT 1")
    r3_46:bind_values(r0_46)
    local r4_46 = nil
    for r8_46 in r3_46:nrows() do
      r4_46 = r8_46.spell
    end
    r3_46:finalize()
    r2_46:close()
    return r4_46
  end,
  SetPassword = function(r0_47, r1_47)
    -- line: [1400, 1411] id: 47
    r16_0()
    local r3_47 = r0_0.open(r14_0())
    local r4_47 = r3_47:prepare("REPLACE INTO recovery" .. " (uid, spell, flag) VALUES (?, ?, 0)")
    r4_47:bind_values(r0_47, r1_47)
    r4_47:step()
    r4_47:finalize()
    r3_47:close()
  end,
  GetInviteCode = function(r0_48)
    -- line: [1415, 1429] id: 48
    local r2_48 = r0_0.open(r14_0())
    local r3_48 = r2_48:prepare("SELECT code FROM invite" .. " WHERE uid=? AND flag=0 LIMIT 1")
    r3_48:bind_values(r0_48)
    local r4_48 = nil
    for r8_48 in r3_48:nrows() do
      r4_48 = r8_48.code
    end
    r3_48:finalize()
    r2_48:close()
    return r4_48
  end,
  SetInviteCode = function(r0_49, r1_49)
    -- line: [1432, 1443] id: 49
    r16_0()
    local r3_49 = r0_0.open(r14_0())
    local r4_49 = r3_49:prepare("REPLACE INTO invite" .. " (uid, code, flag) VALUES (?, ?, 0)")
    r4_49:bind_values(r0_49, r1_49)
    r4_49:step()
    r4_49:finalize()
    r3_49:close()
  end,
  SetStartup = function(r0_50, r1_50)
    -- line: [1446, 1457] id: 50
    local r3_50 = r0_0.open(r14_0())
    local r4_50 = nil	-- notice: implicit variable refs by block#[3]
    if r1_50 then
      r4_50 = 1
      if not r4_50 then
        ::label_11::
        r4_50 = 0
      end
    else
      goto label_11	-- block#2 is visited secondly
    end
    local r5_50 = r3_50:prepare("REPLACE INTO startup" .. " (uid, flag) VALUES (?, ?)")
    r5_50:bind_values(r0_50, r4_50)
    r5_50:step()
    r5_50:finalize()
    r3_50:close()
  end,
  GetStartup = function(r0_51)
    -- line: [1460, 1474] id: 51
    local r2_51 = r0_0.open(r14_0())
    local r3_51 = r2_51:prepare("SELECT flag FROM startup" .. " WHERE uid=? LIMIT 1")
    r3_51:bind_values(r0_51)
    local r4_51 = false
    for r8_51 in r3_51:nrows() do
      if r8_51.flag > 0 then
        r4_51 = true or false
      else
        goto label_24	-- block#3 is visited secondly
      end
    end
    r3_51:finalize()
    r2_51:close()
    return r4_51
  end,
  GetAffiliate = function(r0_52)
    -- line: [1480, 1501] id: 52
    local r2_52 = r0_0.open(r14_0())
    local r3_52 = r2_52:prepare("SELECT used FROM affiliate" .. " WHERE name=? AND flag=0")
    r3_52:bind_values(r0_52)
    local r4_52 = nil
    for r8_52 in r3_52:nrows() do
      if r8_52.used then
        r4_52 = 0 < r8_52.used
      end
    end
    r3_52:finalize()
    r2_52:close()
    if r4_52 == nil then
      r4_52 = false
    end
    return r4_52
  end,
  UpdateAffiliate = function(r0_53)
    -- line: [1503, 1516] id: 53
    r16_0()
    local r2_53 = r0_0.open(r14_0())
    local r3_53 = r2_53:prepare("REPLACE INTO affiliate" .. " (name, used, flag)" .. " VALUES (?, ?, ?)")
    r3_53:bind_values(r0_53, 1, 0)
    r3_53:step()
    r3_53:finalize()
    r2_53:close()
  end,
  LoadSummonNoSaleData = function(r0_54)
    -- line: [1520, 1543] id: 54
    local r2_54 = r0_0.open(r14_0())
    local r3_54 = r2_54:prepare("SELECT sid, lock, four, hex, date FROM summon" .. " WHERE uid=? AND flag=0 AND lock=1 AND sid in (11,12,14,17,18,19) ORDER BY RANDOM() LIMIT 1")
    r3_54:bind_values(r0_54)
    local r4_54 = nil
    local r5_54 = {}
    local r6_54 = nil
    local r7_54 = nil
    for r11_54 in r3_54:nrows() do
      r6_54, r7_54 = r5_0.calc_checksum({
        r0_54,
        r11_54.sid,
        r11_54.lock,
        r11_54.four
      }, r11_54.date)
      if r6_54 ~= r11_54.hex then
        r5_0.checksum_error("summon")
        r4_54 = {}
        break
      else
        r4_54 = r11_54.sid
      end
    end
    r3_54:finalize()
    r2_54:close()
    return r4_54
  end,
  LoadSummonNoSaleIds = function(r0_55)
    -- line: [1546, 1568] id: 55
    local r2_55 = r0_0.open(r14_0())
    local r3_55 = r2_55:prepare("SELECT sid, lock, four, hex, date FROM summon" .. " WHERE uid=? AND flag=0 AND lock=1 AND sid in (11,12,14,17,18,19,21,22,23,24)")
    r3_55:bind_values(r0_55)
    local r4_55 = {}
    local r5_55 = nil
    local r6_55 = nil
    for r10_55 in r3_55:nrows() do
      r5_55, r6_55 = r5_0.calc_checksum({
        r0_55,
        r10_55.sid,
        r10_55.lock,
        r10_55.four
      }, r10_55.date)
      if r5_55 ~= r10_55.hex then
        r5_0.checksum_error("summon")
        r4_55 = {}
        break
      else
        r4_55[#r4_55 + 1] = r10_55.sid
      end
    end
    r3_55:finalize()
    r2_55:close()
    return r4_55
  end,
  GetUnlockMaxStage = function()
    -- line: [1572, 1586] id: 56
    local r1_56 = r0_0.open(r14_0())
    local r2_56 = r1_56:prepare("select mapid,stageid from stage where lock = 0 order by mapid desc, stageid desc limit 1")
    local r3_56 = 0
    local r4_56 = {}
    local r5_56 = nil
    local r6_56 = nil
    for r10_56 in r2_56:nrows() do
      r3_56 = r10_56.mapid * 1000 + r10_56.stageid
    end
    r2_56:finalize()
    r1_56:close()
    return r3_56
  end,
  SetInventoryItem = r64_0,
  SaveCurrentWorldStage = function(r0_58)
    -- line: [1628, 1657] id: 58
    local r2_58 = r0_0.open(r14_0())
    r5_0.begin_transcation(r2_58)
    local r3_58 = r2_58:prepare("REPLACE INTO play_world_stage (uid, cur_world, cur_stage, hex, cdate)" .. " VALUES (:uid, :cur_world, :cur_stage, :hex, :cdate)")
    if r0_58.uid ~= nil and r0_58.cur_world ~= nil and r0_58.cur_stage ~= nil then
      local r4_58, r5_58 = r5_0.calc_checksum({
        r0_58.uid,
        r0_58.cur_world,
        r0_58.cur_stage
      })
      r3_58:reset()
      r3_58:bind_names({
        uid = r0_58.uid,
        cur_world = r0_58.cur_world,
        cur_stage = r0_58.cur_stage,
        hex = r4_58,
        cdate = r5_58,
      })
      r3_58:step()
    end
    r3_58:finalize()
    r5_0.commit(r2_58)
    r2_58:close()
  end,
  RestoreCurrentWorldStage = function(r0_59)
    -- line: [1663, 1702] id: 59
    if r0_59 == nil then
      return nil
    end
    local r1_59 = "SELECT uid, cur_world, cur_stage, hex, cdate FROM play_world_stage WHERE uid = "
    local r2_59 = r0_59
    if type(r0_59) ~= "string" then
      r2_59 = tostring(r0_59)
    end
    local r4_59 = r0_0.open(r14_0())
    local r5_59 = r4_59:prepare(r1_59 .. r2_59)
    local r6_59 = nil
    for r10_59 in r5_59:nrows() do
      local r11_59, r12_59 = r5_0.calc_checksum({
        r10_59.uid,
        r10_59.cur_world,
        r10_59.cur_stage
      }, r10_59.cdate)
      if r11_59 ~= r10_59.hex then
        r6_59 = 0
        break
      else
        r6_59 = {
          cur_world = r10_59.cur_world,
          cur_stage = r10_59.cur_stage,
        }
        break
      end
    end
    r5_59:finalize()
    r4_59:close()
    return r6_59
  end,
  SetServerAccessTime = function(r0_60)
    -- line: [1708, 1749] id: 60
    if r0_60.uid == nil or r0_60.acs_id == nil then
      DebugPrint("データが不正です :  db.SetServerAccessTime()")
      return 
    end
    local r2_60 = r0_0.open(r14_0())
    r5_0.begin_transcation(r2_60)
    local r3_60 = "uid, acs_id, hex, date"
    local r4_60 = ":uid, :acs_id, :hex, :date"
    local r5_60, r6_60 = r5_0.calc_checksum({
      r0_60.uid,
      r0_60.acs_id
    })
    local r7_60 = {
      uid = r0_60.uid,
      acs_id = r0_60.acs_id,
      hex = r5_60,
      date = tostring(r6_60),
    }
    if r0_60.result then
      r3_60 = r3_60 .. ", result"
      r4_60 = r4_60 .. ", :result"
      r7_60.result = r0_60.result
    end
    local r8_60 = r2_60:prepare("REPLACE INTO server_access_time (" .. r3_60 .. ")" .. " VALUES (" .. r4_60 .. ")")
    r8_60:reset()
    r8_60:bind_names(r7_60)
    r8_60:step()
    r8_60:finalize()
    r5_0.commit(r2_60)
    r2_60:close()
  end,
  GetClearedBingo = r68_0,
  GetUserBingoData = function(r0_62, r1_62)
    -- line: [1793, 1827] id: 62
    local r3_62 = r0_0.open(r14_0())
    local r4_62 = r3_62:prepare("SELECT uid, bingo_id, mission_no, data, cleared, hex, date FROM bingo_progress" .. " WHERE uid=? AND bingo_id=? AND mission_no=?")
    r4_62:bind_values(_G.UserID, r0_62, r1_62)
    local r5_62 = nil
    local r6_62 = nil
    local r7_62 = nil
    local r8_62 = nil
    for r12_62 in r4_62:nrows() do
      r6_62, r7_62 = r5_0.calc_checksum({
        _G.UserID,
        r12_62.bingo_id,
        r12_62.mission_no,
        r12_62.data,
        r12_62.cleared
      }, r12_62.date)
      if r6_62 ~= r12_62.hex then
        r5_0.checksum_error("bingo progress")
        return nil
      else
        r8_62 = r12_62.cleared ~= 0
        r5_62 = {
          bingoId = r12_62.bingo_id,
          missionNo = r12_62.mission_no,
          data = r1_0.decode(r12_62.data),
          cleared = r8_62,
        }
        break
      end
    end
    r4_62:finalize()
    r3_62:close()
    return r5_62
  end,
  SetUserBingoData = function(r0_63, r1_63, r2_63, r3_63)
    -- line: [1833, 1864] id: 63
    local r5_63 = r0_0.open(r14_0())
    local r6_63 = r1_0.encode(r2_63)
    if r3_63 then
      r3_63 = 1 or 0
    else
      goto label_15	-- block#2 is visited secondly
    end
    r5_0.begin_transcation(r5_63)
    local r7_63 = r5_63:prepare("REPLACE INTO bingo_progress (uid, bingo_id, mission_no, data, cleared, hex, date)" .. " VALUES (:uid, :bingo_id, :mission_no, :data, :cleared, :hex, :date)")
    local r8_63, r9_63 = r5_0.calc_checksum({
      _G.UserID,
      r0_63,
      r1_63,
      r6_63,
      r3_63
    })
    r7_63:reset()
    r7_63:bind_names({
      uid = _G.UserID,
      bingo_id = r0_63,
      mission_no = r1_63,
      data = r6_63,
      cleared = r3_63,
      hex = r8_63,
      date = r9_63,
    })
    r7_63:step()
    r7_63:finalize()
    r5_0.commit(r5_63)
    r5_63:close()
  end,
  GetAlreadyReadData = function(r0_64)
    -- line: [1868, 1894] id: 64
    local r2_64 = r0_0.open(r14_0())
    local r3_64 = r2_64:prepare("SELECT uid, event_id, state, hex, date FROM already_read" .. " WHERE uid=? AND event_id=?")
    r3_64:bind_values(_G.UserID, r0_64)
    local r4_64 = nil
    local r5_64 = nil
    local r6_64 = nil
    for r10_64 in r3_64:nrows() do
      r5_64, r6_64 = r5_0.calc_checksum({
        _G.UserID,
        r10_64.event_id,
        r10_64.state
      }, r10_64.date)
      if r5_64 ~= r10_64.hex then
        r5_0.checksum_error("already read")
        return nil
      else
        r4_64 = {
          eventId = r10_64.event_id,
          state = r10_64.state,
        }
        break
      end
    end
    r3_64:finalize()
    r2_64:close()
    return r4_64
  end,
  SetAlreadyReadData = function(r0_65, r1_65)
    -- line: [1898, 1923] id: 65
    local r3_65 = r0_0.open(r14_0())
    r5_0.begin_transcation(r3_65)
    local r4_65 = r3_65:prepare("REPLACE INTO already_read (uid, event_id, state, hex, date)" .. " VALUES (:uid, :event_id, :state, :hex, :date)")
    local r5_65, r6_65 = r5_0.calc_checksum({
      _G.UserID,
      r0_65,
      r1_65
    })
    r4_65:reset()
    r4_65:bind_names({
      uid = _G.UserID,
      event_id = r0_65,
      state = r1_65,
      hex = r5_65,
      date = r6_65,
    })
    r4_65:step()
    r4_65:finalize()
    r5_0.commit(r3_65)
    r3_65:close()
  end,
  AddInvalidTransaction = function(r0_67, r1_67, r2_67, r3_67, r4_67)
    -- line: [1960, 1985] id: 67
    assert(r0_67, debug.traceback())
    assert(r1_67, debug.traceback())
    assert(r2_67, debug.traceback())
    assert(r3_67, debug.traceback())
    assert(r4_67, debug.traceback())
    local r6_67 = r0_0.open(r14_0())
    local r7_67 = nil
    local r8_67 = nil
    r5_0.begin_transcation(r6_67)
    local r9_67, r10_67 = r5_0.calc_checksum({
      r0_67,
      r1_67,
      r2_67,
      r3_67,
      r4_67
    })
    r7_67 = r6_67:prepare("REPLACE INTO invalid_transactions" .. " (uid, token, receipt, signature, price, hex, date)" .. " VALUES" .. " (:uid, :token, :receipt, :signature, :price, :hex, :date)")
    assert(r7_67, r6_67:errmsg())
    r7_67:bind_names({
      uid = r0_67,
      token = r1_67,
      receipt = r2_67,
      signature = r3_67,
      price = r4_67,
      hex = r9_67,
      date = r10_67,
    })
    r7_67:step()
    r7_67:finalize()
    r5_0.commit(r6_67)
    r6_67:close()
  end,
  LoadInvalidTransactionsData = function()
    -- line: [1928, 1956] id: 66
    local r1_66 = r0_0.open(r14_0())
    local r2_66 = r1_66:prepare("SELECT uid, token, receipt, signature, price, hex, date FROM invalid_transactions" .. " WHERE uid=?")
    r2_66:bind_values(_G.UserInquiryID)
    local r3_66 = {}
    local r4_66 = nil
    local r5_66 = nil
    for r9_66 in r2_66:nrows() do
      r4_66, r5_66 = r5_0.calc_checksum({
        _G.UserInquiryID,
        r9_66.token,
        r9_66.receipt,
        r9_66.signature,
        r9_66.price
      }, r9_66.date)
      if r4_66 ~= r9_66.hex then
        r5_0.checksum_error("invalid transactions")
        return nil
      else
        table.insert(r3_66, {
          token = r9_66.token,
          receipt = r9_66.receipt,
          signature = r9_66.signature,
          price = r9_66.price,
        })
      end
    end
    r2_66:finalize()
    r1_66:close()
    return r3_66
  end,
  DeleteInvalidTransaction = function(r0_68, r1_68)
    -- line: [1987, 1998] id: 68
    r16_0()
    local r3_68 = r0_0.open(r14_0())
    local r4_68 = r3_68:prepare("DELETE FROM invalid_transactions WHERE uid=? AND signature=?")
    r4_68:bind_values(r0_68, r1_68)
    r4_68:step()
    r4_68:finalize()
    r3_68:close()
  end,
  UpdateSprDaisy = function()
    -- line: [2001, 2014] id: 69
    local r0_69 = r68_0(1)
    local r1_69 = 0
    for r5_69, r6_69 in pairs(r0_69) do
      if r6_69.cleared == true then
        r1_69 = r1_69 + 1
      end
    end
    if r1_69 >= 9 then
      r26_0(_G.UserID, 20)
      r31_0(_G.UserID, 20)
    end
  end,
  UnlockUpgrade4 = function()
    -- line: [2017, 2149] id: 70
    local r0_70, r1_70 = r28_0(_G.UserID)
    for r5_70, r6_70 in pairs(r0_70) do
      if r6_70 == 1 and (r3_0.CharId.Tiana <= r5_70 and r5_70 <= r3_0.CharId.LiliLala or r3_0.CharId.Kala <= r5_70) then
        r31_0(_G.UserID, r5_70)
      end
    end
    local r2_70 = nil
    local r3_70 = nil
    local r4_70 = nil
    r2_70, r3_70 = r28_0(_G.UserID)
    if #r2_70 < 1 then
      r27_0(_G.UserID)
      r2_70, r4_70 = r28_0(_G.UserID)
    end
    local r5_70 = 0
    local r6_70 = 0
    for r10_70 = 1, 10, 1 do
      local r11_70 = r35_0(_G.UserID, r10_70)
      local r12_70 = 0
      for r16_70, r17_70 in pairs(r11_70) do
        r12_70 = r12_70 + 1
        if r12_70 >= 10 then
          break
        end
      end
      local r13_70 = 0
      for r17_70 = 1, r12_70, 1 do
        if r11_70[r17_70] == 0 then
          r13_70 = r13_70 + 1
        end
        local r18_70 = nil
        if r10_70 == 3 and r17_70 == 1 then
          r18_70 = 1
        elseif r10_70 == 3 and r17_70 == 5 then
          r18_70 = 2
        elseif r10_70 == 3 and r17_70 == 9 then
          r18_70 = 3
        elseif r10_70 == 4 and r17_70 == 5 then
          r18_70 = 4
        elseif r10_70 == 4 and r17_70 == 9 then
          r18_70 = 5
        elseif r10_70 == 5 and r17_70 == 5 then
          r18_70 = 6
        elseif r10_70 == 6 and r17_70 == 4 then
          r18_70 = 7
        elseif r10_70 == 7 and r17_70 == 3 then
          r18_70 = 8
        elseif r10_70 == 7 and r17_70 == 7 then
          r18_70 = 13
        elseif r10_70 == 8 and r17_70 == 3 then
          r18_70 = 9
        elseif r10_70 == 9 and r17_70 == 3 then
          r18_70 = 10
        end
        if r18_70 ~= nil and r25_0(_G.UserID, r18_70) and r11_70[r17_70] == 0 then
          r31_0(_G.UserID, r18_70)
        end
      end
      if r13_70 < 1 then
        break
      else
        r6_70 = r10_70
        r5_70 = r13_70 + 1
      end
    end
    local r7_70 = nil
    local r8_70 = r6_70 * 100 + r5_70
    if r8_70 < 101 and r2_70[r3_0.CharId.Daisy] == 1 then
      r31_0(_G.UserID, r3_0.CharId.Daisy)
    end
    if r8_70 < 103 and r2_70[r3_0.CharId.Becky] == 1 then
      r31_0(_G.UserID, r3_0.CharId.Becky)
    end
    if r8_70 < 106 and r2_70[r3_0.CharId.Chloe] == 1 then
      r31_0(_G.UserID, r3_0.CharId.Chloe)
    end
    if r8_70 < 201 and r2_70[r3_0.CharId.Nicola] == 1 then
      r31_0(_G.UserID, r3_0.CharId.Nicola)
    end
    if r8_70 < 206 and r2_70[r3_0.CharId.Chiara] == 1 then
      r31_0(_G.UserID, r3_0.CharId.Chiara)
    end
    if r8_70 < 401 and r2_70[r3_0.CharId.Cecilia] == 1 then
      r31_0(_G.UserID, r3_0.CharId.Cecilia)
    end
    if r8_70 < 501 and r2_70[r3_0.CharId.Bianca] == 1 then
      r31_0(_G.UserID, r3_0.CharId.Bianca)
    end
    if r8_70 < 601 and r2_70[r3_0.CharId.Lillian] == 1 then
      r31_0(_G.UserID, r3_0.CharId.Lillian)
    end
    if r8_70 < 606 and r2_70[r3_0.CharId.Luna] == 1 then
      r31_0(_G.UserID, r3_0.CharId.Luna)
    end
    if r8_70 < 701 and r2_70[r3_0.CharId.Iris] == 1 then
      r31_0(_G.UserID, r3_0.CharId.Iris)
    end
    if r8_70 < 801 and r2_70[r3_0.CharId.Lyra] == 1 then
      r31_0(_G.UserID, r3_0.CharId.Lyra)
    end
  end,
  LoadStageClearCount = r78_0,
  SaveStageClearCount = r79_0,
  LoadStageClearCountForMap = function(r0_73)
    -- line: [2243, 2304] id: 73
    if _G.UserID == nil or r0_73 == nil then
      return nil
    end
    local r2_73 = r0_0.open(r14_0())
    local r3_73 = r2_73:prepare("SELECT uid, mapid, stageid, clear_count, hex, date FROM stage_clear" .. " WHERE uid=? AND mapid=?" .. " ORDER BY stageid ASC")
    r3_73:bind_values(_G.UserID, r0_73)
    local r4_73 = {}
    local r5_73 = nil
    local r6_73 = nil
    for r10_73 in r3_73:nrows() do
      r5_73, r6_73 = r5_0.calc_checksum({
        _G.UserID,
        r10_73.mapid,
        r10_73.stageid,
        r10_73.clear_count
      }, r10_73.date)
      if r5_73 ~= r10_73.hex then
        r5_0.checksum_error("stage clear")
        return nil
      else
        table.insert(r4_73, {
          mapId = r10_73.mapid,
          stageId = r10_73.stageid,
          clearCount = r10_73.clear_count,
        })
      end
    end
    r3_73:finalize()
    r2_73:close()
    if #r4_73 < _G.MaxStage then
      local r7_73 = r35_0(_G.UserID, r0_73)
      local r8_73 = nil
      local r9_73 = nil
      local r10_73 = nil
      local r11_73 = nil
      local r12_73 = {}
      for r16_73, r17_73 in pairs(r7_73) do
        local r18_73 = 0
        for r22_73, r23_73 in pairs(r4_73) do
          if r23_73.mapId == r0_73 and r23_73.stageId == r16_73 and 0 < r23_73.clearCount then
            r18_73 = r23_73.clearCount
            break
          end
        end
        table.insert(r12_73, {
          mapId = r0_73,
          stageId = r16_73,
          clearCount = r18_73,
        })
      end
      r4_73 = r12_73
    end
    return r4_73
  end,
  AddStageClearCount = function(r0_74, r1_74)
    -- line: [2309, 2320] id: 74
    local r2_74 = r78_0(r0_74, r1_74)
    if r2_74 == nil then
      return false
    end
    return r79_0(r0_74, r1_74, r2_74.clearCount + 1)
  end,
  SaveCharRank = r82_0,
  LoadCharRank = function(r0_76, r1_76)
    -- line: [2364, 2414] id: 76
    if r0_76 == nil or r1_76 == nil then
      return nil
    end
    local r3_76 = r0_0.open(r14_0())
    local r4_76 = r3_76:prepare("SELECT uid, sid, rank, exp, hex, date FROM summon_rank" .. " WHERE uid=? AND sid=?")
    r4_76:bind_values(r0_76, r1_76)
    local r5_76 = 0
    local r6_76 = nil
    local r7_76 = nil
    local r8_76 = nil
    for r12_76 in r4_76:nrows() do
      r7_76, r8_76 = r5_0.calc_checksum({
        r0_76,
        r1_76,
        r12_76.rank,
        r12_76.exp
      }, r12_76.date)
      if r7_76 ~= r12_76.hex then
        r5_0.checksum_error("summon rank")
        r6_76 = {
          sid = 0,
          rank = 0,
          exp = 0,
        }
        return r6_76
      else
        r6_76 = {
          sid = r1_76,
          rank = r12_76.rank,
          exp = r12_76.exp,
        }
      end
      r5_76 = r5_76 + 1
    end
    r4_76:finalize()
    r3_76:close()
    if r5_76 == 0 then
      local r9_76 = 1
      local r10_76 = 0
      r82_0(r0_76, r1_76, r9_76, r10_76)
      r6_76 = {
        sid = r1_76,
        rank = r9_76,
        exp = r10_76,
      }
    end
    return r6_76
  end,
  LoadEvoData = r84_0,
  SaveEvoData = function(r0_78, r1_78, r2_78, r3_78)
    -- line: [2474, 2529] id: 78
    if _G.UserID == nil or r0_78 == nil and r1_78 == nil and r2_78 == nil and r3_78 == nil then
      return false
    end
    local r4_78 = r84_0()
    local r5_78 = r4_78.orbRemain
    local r6_78 = r4_78.orbMax
    local r7_78 = r4_78.exp
    local r8_78 = r4_78.usedTime
    if r0_78 ~= nil then
      r5_78 = r0_78
    end
    if r1_78 ~= nil then
      r6_78 = r1_78
    end
    if r2_78 ~= nil then
      r7_78 = r2_78
    end
    if r3_78 ~= nil then
      r8_78 = r3_78
    end
    local r10_78 = r0_0.open(r14_0())
    r5_0.begin_transcation(r10_78)
    local r11_78 = r10_78:prepare("REPLACE INTO evo_orb_exp (uid, orb_remain, orb_max, exp, used_time, hex, date)" .. " VALUES (:uid, :orb_remain, :orb_max, :exp, :used_time, :hex, :date)")
    local r12_78, r13_78 = r5_0.calc_checksum({
      _G.UserID,
      r5_78,
      r6_78,
      r7_78,
      r8_78
    })
    r11_78:reset()
    r11_78:bind_names({
      uid = _G.UserID,
      orb_remain = r5_78,
      orb_max = r6_78,
      exp = r7_78,
      used_time = r8_78,
      hex = r12_78,
      date = r13_78,
    })
    r11_78:step()
    r11_78:finalize()
    r5_0.commit(r10_78)
    r10_78:close()
  end,
  GetInventoryItem = function(r0_79, r1_79)
    -- line: [2534, 2592] id: 79
    if r0_79 == nil or r1_79 == nil then
      return nil
    end
    local r2_79 = "SELECT uid, itemid, quantity, hex, cdate FROM inventory_item WHERE uid = "
    local r3_79 = r0_79
    if type(r0_79) ~= "string" then
      r3_79 = tostring(r0_79)
    end
    r2_79 = r2_79 .. r3_79
    local r4_79 = r1_79
    if type(r1_79) ~= "string" then
      r4_79 = tostring(r1_79)
    end
    local r6_79 = r0_0.open(r14_0())
    local r7_79 = r6_79:prepare(r2_79 .. " AND itemid = " .. r4_79)
    local r8_79 = 0
    for r12_79 in r7_79:nrows() do
      local r13_79, r14_79 = r5_0.calc_checksum({
        r12_79.uid,
        r12_79.itemid,
        r12_79.quantity
      }, r12_79.cdate)
      if r13_79 ~= r12_79.hex then
        server.GetStockIemCount(r0_79, function(r0_80)
          -- line: [2566, 2584] id: 80
          local r1_80 = r1_0.decode(r0_80.response)
          if r1_80.status == 1 then
            for r6_80, r7_80 in pairs(r1_80.result) do
              if r6_80 == r4_79 then
                r64_0({
                  [1] = {
                    uid = r0_79,
                    itemid = tonumber(r6_80),
                    quantity = r7_80,
                  },
                })
              end
            end
          end
        end)
        break
      else
        r8_79 = r12_79.quantity
      end
    end
    r7_79:finalize()
    r6_79:close()
    return r8_79
  end,
  GetServerAccessTime = function(r0_81)
    -- line: [2598, 2635] id: 81
    if r0_81.uid == nil or r0_81.acs_id == nil then
      DebugPrint("データが不正です : db.GetServerAccessTime()")
      return nil
    end
    local r1_81 = tostring(r0_81.uid)
    local r2_81 = tostring(r0_81.acs_id)
    local r5_81 = r0_0.open(r14_0())
    local r6_81 = r5_81:prepare("SELECT uid, acs_id, hex, date, result FROM server_access_time WHERE uid = " .. r1_81 .. " AND acs_id = " .. r2_81)
    local r7_81 = 0
    if r6_81 then
      for r11_81 in r6_81:nrows() do
        local r12_81, r13_81 = r5_0.calc_checksum({
          r11_81.uid,
          r11_81.acs_id
        }, tonumber(r11_81.date))
        if r12_81 ~= r11_81.hex then
          r7_81 = 0
          break
        else
          r7_81 = {
            acs_id = r11_81.acs_id,
            date = tonumber(r11_81.date),
            result = r11_81.result,
          }
          break
        end
      end
      r6_81:finalize()
    end
    r5_81:close()
    return r7_81
  end,
  GetLoginBonusRecive = function(r0_82)
    -- line: [2638, 2667] id: 82
    if r0_82 == nil then
      return true
    end
    local r1_82 = tostring(r0_82)
    local r4_82 = r0_0.open(r14_0())
    local r5_82 = r4_82:prepare("SELECT uid, result FROM login_bonus_recive WHERE uid = " .. r1_82)
    local r6_82 = 1
    if r5_82 then
      for r10_82 in r5_82:nrows() do
        r6_82 = r10_82.result
        break
      end
      r5_82:finalize()
    end
    r4_82:close()
    local r7_82 = r6_82 == 1
  end,
  SaveLoginBonusReciveData = function(r0_83)
    -- line: [2669, 2686] id: 83
    if not r0_83 or not r0_83.uid then
      return 
    end
    local r2_83 = r0_0.open(r14_0())
    r5_0.begin_transcation(r2_83)
    local r3_83 = r2_83:prepare("REPLACE INTO login_bonus_recive" .. " (uid, result)" .. " VALUES (?, ?)")
    assert(r3_83, r2_83:errmsg())
    r3_83:bind_values(r0_83.uid, r0_83.result)
    r3_83:step()
    r3_83:finalize()
    r5_0.commit(r2_83)
    r2_83:close()
  end,
  GameDataReplace = function(r0_84)
    -- line: [2688, 2999] id: 84
    local r5_84 = _G.UserID
    local r2_84 = r0_0.open(r14_0())
    local r4_84 = r0_84.data.GameData
    local r8_84 = nil
    if type(r4_84.invite) == "table" then
      r8_84 = r4_84.invite[1]
    else
      r8_84 = r4_84.invite
    end
    if r8_84 == nil then
      r8_84 = ""
    end
    local r3_84 = r2_84:prepare("REPLACE INTO invite (uid, code, flag)" .. " VALUES (:uid, :code, 0)")
    assert(r3_84, r2_84:errmsg())
    r3_84:bind_names({
      uid = r5_84,
      code = r8_84,
    })
    r3_84:step()
    r3_84:finalize()
    r3_84 = r2_84:prepare("REPLACE INTO map (uid, mapid, lock, flag)" .. " VALUES (:uid, :map, :lock, 0)")
    assert(r3_84, r2_84:errmsg())
    local r7_84 = {
      r5_84
    }
    table.sort(r4_84.map)
    for r12_84, r13_84 in pairs(r4_84.map) do
      r3_84:reset()
      r3_84:bind_names({
        uid = r5_84,
        map = r12_84,
        lock = r13_84,
      })
      r3_84:step()
      table.insert(r7_84, r13_84)
    end
    r3_84:finalize()
    r5_0.write_checksum(r2_84, "map", r7_84)
    r3_84 = r2_84:prepare("REPLACE INTO option (uid, bgm, se, confirm, grid, voice_type, language, local_notification, flag)" .. " VALUES (:uid, :bgm, :se, :confirm, :grid, :voice_type, :language, :local_notification, 0)")
    assert(r3_84, r2_84:errmsg())
    local r6_84 = r4_84.option
    r3_84:bind_names({
      uid = r5_84,
      bgm = r6_84.bgm,
      se = r6_84.se,
      confirm = r6_84.confirm,
      grid = r6_84.grid,
      voice_type = r6_84.voice_type,
      language = _G.UILanguage,
      local_notification = r6_84.local_notification,
    })
    r3_84:step()
    r3_84:finalize()
    if r4_84.recovery then
      r3_84 = r2_84:prepare("REPLACE INTO recovery (uid, spell, flag)" .. " VALUES (:uid, :spell, 0)")
      assert(r3_84, r2_84:errmsg())
      r3_84:bind_names({
        uid = r5_84,
        spell = r4_84.recovery,
      })
      r3_84:step()
      r3_84:finalize()
    end
    r3_84 = r2_84:prepare("REPLACE INTO stage (uid, mapid, stageid, lock, flag)" .. " VALUES (:uid, :map, :stage, :lock, 0)")
    assert(r3_84, r2_84:errmsg())
    for r12_84, r13_84 in pairs(r4_84.stage) do
      for r17_84, r18_84 in pairs(r13_84) do
        r3_84:reset()
        r3_84:bind_names({
          uid = r5_84,
          map = r12_84,
          stage = r17_84,
          lock = r18_84,
        })
        r3_84:step()
      end
    end
    r3_84:finalize()
    r3_84 = r2_84:prepare("SELECT lock FROM stage" .. " WHERE uid=:uid AND mapid=:map AND flag=0 ORDER BY stageid")
    assert(r3_84, r2_84:errmsg())
    for r12_84, r13_84 in pairs(r4_84.stage) do
      r3_84:reset()
      r3_84:bind_names({
        uid = r5_84,
        map = r12_84,
      })
      r7_84 = {
        r5_84,
        r12_84
      }
      for r17_84 in r3_84:nrows() do
        table.insert(r7_84, r17_84.lock)
      end
      r5_0.write_checksum(r2_84, string.format("map%02d", r12_84), r7_84)
    end
    r3_84:finalize()
    r3_84 = r2_84:prepare("REPLACE INTO summon" .. " (uid, sid, lock, four, hex, date, flag) VALUES" .. " (:uid, :sid, :lock, :four, :hex, :date, 0)")
    assert(r3_84, r2_84:errmsg())
    local r9_84 = nil
    local r10_84 = nil
    local r11_84 = nil
    local r12_84 = nil
    local r13_84 = nil
    for r17_84, r18_84 in pairs(r4_84.summon) do
      r9_84 = r17_84
      r10_84 = r18_84.lock
      r11_84 = r18_84.four
      r12_84, r13_84 = r5_0.calc_checksum({
        r5_84,
        r9_84,
        r10_84,
        r11_84
      })
      r3_84:reset()
      r3_84:bind_names({
        uid = r5_84,
        sid = r9_84,
        lock = r10_84,
        four = r11_84,
        hex = r12_84,
        date = r13_84,
      })
      r3_84:step()
    end
    r3_84:finalize()
    r6_84 = r4_84.twitter
    if r6_84 and r6_84.token then
      r3_84 = r2_84:prepare("REPLACE INTO twitter" .. " (uid, token, secret, twitterid, user)" .. " VALUES (:uid, :token, :secret, :twitterid, :user)")
      assert(r3_84, r2_84:errmsg())
      r3_84:bind_names({
        token = r6_84.token,
        secret = r6_84.secret,
        twitterid = r6_84.twitterid,
        user = r6_84.user,
      })
      r3_84:step()
      r3_84:finalize()
    end
    r6_84 = r4_84.user
    r3_84 = r2_84:prepare("REPLACE INTO user (uid, id, key)" .. " VALUES (:uid, :id, :key)")
    assert(r3_84, r2_84:errmsg())
    r3_84:bind_names({
      uid = r5_84,
      id = r6_84.id,
      key = r6_84.key,
    })
    r3_84:step()
    r3_84:finalize()
    if r4_84.summon_rank ~= nil then
      r3_84 = r2_84:prepare("REPLACE INTO summon_rank" .. " (uid, sid, rank, exp, hex, date) VALUES" .. " (:uid, :sid, :rank, :exp, :hex, :date)")
      assert(r3_84, r2_84:errmsg())
      for r17_84, r18_84 in pairs(r4_84.summon_rank) do
        r12_84, r13_84 = r5_0.calc_checksum({
          r5_84,
          r18_84.sid,
          r18_84.rank,
          r18_84.exp
        })
        r3_84:reset()
        r3_84:bind_names({
          uid = r5_84,
          sid = r18_84.sid,
          rank = r18_84.rank,
          exp = r18_84.exp,
          hex = r12_84,
          date = r13_84,
        })
        r3_84:step()
      end
      r3_84:finalize()
    end
    if r4_84.evo_orb_exp ~= nil then
      r3_84 = r2_84:prepare("REPLACE INTO evo_orb_exp" .. " (uid, orb_remain, orb_max, exp, used_time, hex, date) VALUES" .. " (:uid, :orb_remain, :orb_max, :exp, :used_time, :hex, :date)")
      assert(r3_84, r2_84:errmsg())
      if r4_84.evo_orb_exp ~= nil and r4_84.evo_orb_exp.orb_remain ~= nil and r4_84.evo_orb_exp.orb_max ~= nil and r4_84.evo_orb_exp.exp ~= nil then
        local r14_84 = 0
        if r4_84.evo_orb_exp.used_time ~= nil then
          r14_84 = r4_84.evo_orb_exp.used_time
        end
        r12_84, r13_84 = r5_0.calc_checksum({
          r5_84,
          r4_84.evo_orb_exp.orb_remain,
          r4_84.evo_orb_exp.orb_max,
          r4_84.evo_orb_exp.exp,
          r14_84
        })
        r3_84:bind_names({
          uid = r5_84,
          orb_remain = r4_84.evo_orb_exp.orb_remain,
          orb_max = r4_84.evo_orb_exp.orb_max,
          exp = r4_84.evo_orb_exp.exp,
          used_time = r14_84,
          hex = r12_84,
          date = r13_84,
        })
        r3_84:step()
      else
        assert(true, r2_84:errmsg())
      end
      r3_84:finalize()
    end
    if r4_84.stage_clear ~= nil then
      r3_84 = r2_84:prepare("REPLACE INTO stage_clear" .. " (uid, mapid, stageid, clear_count, hex, date) VALUES" .. " (:uid, :mapid, :stageid, :clear_count, :hex, :date)")
      assert(r3_84, r2_84:errmsg())
      for r17_84, r18_84 in pairs(r4_84.stage_clear) do
        r12_84, r13_84 = r5_0.calc_checksum({
          r5_84,
          r18_84.mapid,
          r18_84.stageid,
          r18_84.clear_count
        })
        r3_84:reset()
        r3_84:bind_names({
          uid = r5_84,
          mapid = r18_84.mapid,
          stageid = r18_84.stageid,
          clear_count = r18_84.clear_count,
          hex = r12_84,
          date = r13_84,
        })
        r3_84:step()
      end
      r3_84:finalize()
    end
    if r4_84.inventory_item ~= nil then
      r3_84 = r2_84:prepare("REPLACE INTO inventory_item" .. " (uid, itemid, quantity, hex, cdate) VALUES" .. " (:uid, :itemid, :quantity, :hex, :cdate)")
      assert(r3_84, r2_84:errmsg())
      local r14_84 = _G.UserInquiryID
      for r18_84, r19_84 in pairs(r4_84.inventory_item) do
        if r14_84 ~= nil and r19_84.itemid ~= nil and r19_84.quantity ~= nil then
          local r20_84, r21_84 = r5_0.calc_checksum({
            r14_84,
            r19_84.itemid,
            r19_84.quantity
          })
          r3_84:reset()
          r3_84:bind_names({
            uid = r14_84,
            itemid = r19_84.itemid,
            quantity = r19_84.quantity,
            hex = r20_84,
            cdate = r21_84,
          })
          r3_84:step()
        end
      end
      r3_84:finalize()
    end
    if r4_84.bingo_progress ~= nil then
      r3_84 = r2_84:prepare("REPLACE INTO bingo_progress" .. " (uid, bingo_id, mission_no, data, cleared, hex, date) VALUES" .. " (:uid, :bingo_id, :mission_no, :data, :cleared, :hex, :date)")
      assert(r3_84, r2_84:errmsg())
      for r17_84, r18_84 in pairs(r4_84.bingo_progress) do
        if r5_84 ~= nil and r18_84.bingo_id ~= nil and r18_84.mission_no ~= nil and r18_84.data ~= nil and r18_84.cleared ~= nil then
          local r19_84, r20_84 = r5_0.calc_checksum({
            r5_84,
            r18_84.bingo_id,
            r18_84.mission_no,
            r18_84.data,
            r18_84.cleared
          })
          r3_84:reset()
          r3_84:bind_names({
            uid = r5_84,
            bingo_id = r18_84.bingo_id,
            mission_no = r18_84.mission_no,
            data = r18_84.data,
            cleared = r18_84.cleared,
            hex = r19_84,
            date = r20_84,
          })
          r3_84:step()
        end
      end
      r3_84:finalize()
    end
    if r4_84.already_read ~= nil then
      r3_84 = r2_84:prepare("REPLACE INTO already_read" .. " (uid, event_id, state, hex, date) VALUES" .. " (:uid, :event_id, :state, :hex, :date)")
      assert(r3_84, r2_84:errmsg())
      for r17_84, r18_84 in pairs(r4_84.already_read) do
        if r5_84 ~= nil and r18_84.event_id ~= nil and r18_84.state ~= nil then
          local r19_84, r20_84 = r5_0.calc_checksum({
            r5_84,
            r18_84.event_id,
            r18_84.state
          })
          r3_84:reset()
          r3_84:bind_names({
            uid = r5_84,
            event_id = r18_84.event_id,
            state = r18_84.state,
            hex = r19_84,
            date = r20_84,
          })
          r3_84:step()
        end
      end
      r3_84:finalize()
    end
    r2_84:close()
  end,
  GetGameData = function()
    -- line: [3001, 3163] id: 85
    local r0_85 = {}
    local r2_85 = r0_0.open(r14_0())
    local r3_85 = nil
    local r4_85 = nil
    r4_85 = {}
    for r8_85 in r2_85:nrows("SELECT code FROM invite WHERE flag=0 LIMIT 1") do
      r4_85 = r8_85.code
    end
    r0_85.invite = r4_85
    r4_85 = {}
    for r8_85 in r2_85:nrows("SELECT mapid, lock FROM map WHERE flag=0 ORDER BY mapid") do
      r4_85[r8_85.mapid] = r8_85.lock
    end
    r0_85.map = r4_85
    r4_85 = {}
    for r8_85 in r2_85:nrows("SELECT bgm, se, confirm, grid, voice_type, local_notification FROM option WHERE flag=0 LIMIT 1") do
      r4_85.bgm = r8_85.bgm
      r4_85.se = r8_85.se
      r4_85.confirm = r8_85.confirm
      r4_85.grid = r8_85.grid
      r4_85.voice_type = r8_85.voice_type
      r4_85.local_notification = r8_85.local_notification
    end
    r0_85.option = r4_85
    r4_85 = nil
    for r8_85 in r2_85:nrows("SELECT spell FROM recovery WHERE flag = 0 LIMIT 1") do
      r4_85 = r8_85.spell
    end
    r0_85.recovery = r4_85
    r4_85 = {}
    local r5_85 = nil
    local r6_85 = nil
    for r10_85 in r2_85:nrows("SELECT mapid, stageid, lock FROM stage WHERE flag = 0 ORDER BY mapid, stageid") do
      r5_85 = r10_85.mapid
      r6_85 = r10_85.stageid
      if r4_85[r5_85] == nil then
        r4_85[r5_85] = {}
      end
      r4_85[r5_85][r6_85] = r10_85.lock
    end
    r0_85.stage = r4_85
    r4_85 = {}
    for r10_85 in r2_85:nrows("SELECT sid, lock, four FROM summon WHERE flag = 0 ORDER BY sid") do
      r4_85[r10_85.sid] = {
        lock = r10_85.lock,
        four = r10_85.four,
      }
    end
    r0_85.summon = r4_85
    r4_85 = {}
    for r10_85 in r2_85:nrows("SELECT token, secret, twitterid, user FROM twitter WHERE flag=0") do
      r4_85.token = r10_85.token
      r4_85.secret = r10_85.secret
      r4_85.twitterid = r10_85.twitterid
      r4_85.user = r10_85.user
    end
    r0_85.twitter = r4_85
    r4_85 = {}
    for r10_85 in r2_85:nrows("SELECT id, key FROM user WHERE flag=0 LIMIT 1") do
      if r5_0.is_numeric(r10_85.id) then
        r4_85.id = math.floor(r10_85.id)
        r4_85.key = r10_85.key
      else
        r4_85.id = nil
        r4_85.key = nil
      end
    end
    r0_85.user = r4_85
    r4_85 = {}
    for r10_85 in r2_85:nrows("SELECT sid, rank, exp FROM summon_rank") do
      table.insert(r4_85, {
        sid = r10_85.sid,
        rank = r10_85.rank,
        exp = r10_85.exp,
      })
    end
    r0_85.summon_rank = r4_85
    r4_85 = {}
    for r10_85 in r2_85:nrows("SELECT orb_remain, orb_max, exp, used_time FROM evo_orb_exp") do
      r4_85 = {
        orb_remain = r10_85.orb_remain,
        orb_max = r10_85.orb_max,
        exp = r10_85.exp,
        used_time = r10_85.used_time,
      }
    end
    r0_85.evo_orb_exp = r4_85
    r4_85 = {}
    for r10_85 in r2_85:nrows("SELECT mapid, stageid, clear_count FROM stage_clear ORDER BY mapid, stageid") do
      table.insert(r4_85, {
        mapid = r10_85.mapid,
        stageid = r10_85.stageid,
        clear_count = r10_85.clear_count,
      })
    end
    r0_85.stage_clear = r4_85
    r4_85 = {}
    for r10_85 in r2_85:nrows("SELECT itemid, quantity FROM inventory_item") do
      table.insert(r4_85, {
        itemid = r10_85.itemid,
        quantity = r10_85.quantity,
      })
    end
    r0_85.inventory_item = r4_85
    r4_85 = {}
    for r10_85 in r2_85:nrows("SELECT bingo_id, mission_no, data, cleared FROM bingo_progress") do
      table.insert(r4_85, {
        bingo_id = r10_85.bingo_id,
        mission_no = r10_85.mission_no,
        data = r10_85.data,
        cleared = r10_85.cleared,
      })
    end
    r0_85.bingo_progress = r4_85
    r4_85 = {}
    for r10_85 in r2_85:nrows("SELECT event_id, state, hex, date FROM already_read") do
      table.insert(r4_85, {
        event_id = r10_85.event_id,
        state = r10_85.state,
      })
    end
    r0_85.already_read = r4_85
    r2_85:close()
    return r0_85
  end,
  isTutorial = function()
    -- line: [3167, 3179] id: 86
    if _G.UserID == nil then
      return false
    end
    if r49_0(_G.UserID) == false then
      return true
    end
    return false
  end,
  gamedata_filename = r14_0,
}
