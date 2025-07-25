-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("sqlite3")
local r1_0 = require("json")
local r2_0 = require("crypto")
local r3_0 = require("db.BaseDB")
local r4_0 = "resume.sqlite"
local function r5_0()
  -- line: [11, 13] id: 1
  return system.pathForFile(r4_0, system.DocumentsDirectory)
end
local function r6_0()
  -- line: [17, 50] id: 2
  local r1_2 = r0_0.open(r5_0())
  local r2_2 = nil
  r1_2:exec("CREATE TABLE IF NOT EXISTS resume (" .. "uid INTEGER," .. "mapid INTEGER," .. "stageid INTEGER," .. "waveid INTEGER," .. "data TEXT," .. "hex TEXT," .. "date TEXT," .. "flag BOOL DEFAULT 0," .. "PRIMARY KEY (uid, mapid, stageid, waveid))")
  r1_2:exec("CREATE TABLE IF NOT EXISTS resume_rewind (" .. "uid INTEGER," .. "mapid INTEGER," .. "stageid INTEGER," .. "waveid INTEGER," .. "date TEXT," .. "flag BOOL DEFAULT 0," .. "PRIMARY KEY (uid, mapid, stageid))")
  r1_2:close()
  if not _G.IsSimulator then
    native.setSync(r4_0, {
      iCloudBackup = false,
    })
  end
end
return {
  InitResumeData = r6_0,
  SaveResume = function(r0_3, r1_3, r2_3, r3_3, r4_3)
    -- line: [52, 66] id: 3
    local r6_3 = r0_0.open(r5_0())
    local r7_3 = r6_3:prepare("REPLACE INTO resume" .. " (uid, mapid, stageid, waveid, data, hex, date)" .. " VALUES (?, ?, ?, ?, ?, ?, ?)")
    r4_3 = r1_0.encode(r4_3)
    local r8_3, r9_3 = r3_0.calc_checksum({
      r0_3,
      r1_3,
      r2_3,
      r3_3,
      r4_3
    })
    r7_3:bind_values(r0_3, r1_3, r2_3, r3_3, r4_3, r8_3, r9_3)
    r7_3:step()
    r7_3:finalize()
    r6_3:close()
  end,
  SaveResumeRewind = function(r0_4, r1_4, r2_4, r3_4, r4_4)
    -- line: [68, 81] id: 4
    local r6_4 = r0_0.open(r5_0())
    local r7_4 = r6_4:prepare("REPLACE INTO resume_rewind" .. " (uid, mapid, stageid, waveid, date)" .. " VALUES (?, ?, ?, ?, ?)")
    if r4_4 == nil then
      r4_4 = tostring(os.time())
    end
    r7_4:bind_values(r0_4, r1_4, r2_4, r3_4, r4_4)
    r7_4:step()
    r7_4:finalize()
    r6_4:close()
  end,
  LoadResume = function(r0_5, r1_5, r2_5, r3_5)
    -- line: [83, 116] id: 5
    local r5_5 = r0_0.open(r5_0())
    local r6_5 = r5_5:prepare("SELECT data, hex, date FROM resume" .. " WHERE uid=? AND mapid=?" .. " AND stageid=? AND waveid=? AND flag=0 LIMIT 1")
    if r6_5 == nil then
      return false
    end
    r6_5:bind_values(r0_5, r1_5, r2_5, r3_5)
    local r7_5 = nil
    for r11_5 in r6_5:nrows() do
      r7_5 = {}
      r7_5.data = r11_5.data
      r7_5.hex = r11_5.hex
      r7_5.date = r11_5.date
    end
    r6_5:finalize()
    r5_5:close()
    if r7_5 then
      local r9_5, r10_5 = r3_0.calc_checksum({
        r0_5,
        r1_5,
        r2_5,
        r3_5,
        r7_5.data
      }, r7_5.date)
      if r9_5 ~= r7_5.hex then
        r3_0.checksum_error("RESUME")
        r7_5 = nil
      end
    end
    if r7_5 == nil then
      return nil
    else
      return r1_0.decode(r7_5.data)
    end
  end,
  LoadResumeRewind = function(r0_6, r1_6, r2_6)
    -- line: [118, 135] id: 6
    local r4_6 = r0_0.open(r5_0())
    local r5_6 = r4_6:prepare("SELECT waveid FROM resume_rewind" .. " WHERE uid=? AND mapid=?" .. " AND stageid=? AND flag=0 LIMIT 1")
    if r5_6 == nil then
      return 1
    end
    r5_6:bind_values(r0_6, r1_6, r2_6)
    local r6_6 = 1
    for r10_6 in r5_6:nrows() do
      r6_6 = r10_6.waveid
    end
    r5_6:finalize()
    r4_6:close()
    return r6_6
  end,
  IsInvalidResume = function(r0_7, r1_7, r2_7)
    -- line: [139, 176] id: 7
    local r4_7 = r0_0.open(r5_0())
    local r5_7 = r4_7:prepare("SELECT waveid, data, hex, date FROM resume" .. " WHERE uid=? AND mapid=?" .. " AND stageid=? AND flag=0 ORDER BY waveid ASC LIMIT 1")
    if r5_7 == nil then
      return false
    end
    r5_7:bind_values(r0_7, r1_7, r2_7)
    local r6_7 = nil
    local r7_7 = nil
    for r11_7 in r5_7:nrows() do
      r6_7 = {}
      r6_7.data = r11_7.data
      r6_7.hex = r11_7.hex
      r6_7.date = r11_7.date
      r7_7 = r11_7.waveid
    end
    r5_7:finalize()
    r4_7:close()
    if r6_7 then
      local r9_7, r10_7 = r3_0.calc_checksum({
        r0_7,
        r1_7,
        r2_7,
        r7_7,
        r6_7.data
      }, r6_7.date)
      if r9_7 ~= r6_7.hex then
        r3_0.checksum_error("RESUME")
        r6_7 = nil
        r7_7 = nil
      end
    end
    if r6_7 ~= nil then
      local r8_7 = r7_7 == 0
    else
      goto label_66	-- block#10 is visited secondly
    end
  end,
  DeleteResume = function(r0_8, r1_8, r2_8)
    -- line: [178, 189] id: 8
    local r4_8 = r0_0.open(r5_0())
    local r5_8 = r4_8:prepare("DELETE FROM resume" .. " WHERE uid=? AND mapid=? AND stageid=?")
    r5_8:bind_values(r0_8, r1_8, r2_8)
    r5_8:step()
    r5_8:finalize()
    r4_8:close()
  end,
  DeleteResumeRewind = function(r0_9, r1_9, r2_9)
    -- line: [191, 203] id: 9
    local r4_9 = r0_0.open(r5_0())
    local r5_9 = r4_9:prepare("DELETE FROM resume_rewind" .. " WHERE uid=? AND mapid=? AND stageid=?")
    r5_9:bind_values(r0_9, r1_9, r2_9)
    r5_9:step()
    r5_9:finalize()
    r4_9:close()
  end,
  CleanupResume = function()
    -- line: [206, 210] id: 10
    os.remove(r5_0())
    r6_0()
  end,
}
