-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("sqlite3")
local r1_0 = require("json")
local r2_0 = require("crypto")
local r3_0 = require("db.BaseDB")
local function r4_0()
  -- line: [9, 11] id: 1
  return system.pathForFile("info.sqlite", system.DocumentsDirectory)
end
return {
  InitInformation = function()
    -- line: [13, 37] id: 2
    local r1_2 = r0_0.open(r4_0())
    local r2_2 = nil
    r3_0.begin_transcation(r1_2)
    r1_2:exec("CREATE TABLE IF NOT EXISTS tags (" .. "id INTEGER PRIMARY KEY," .. "isview INTEGER," .. "download INTEGER," .. "flag BOOL DEFAULT 0)")
    r1_2:exec("CREATE TABLE IF NOT EXISTS filehash (" .. "fname TEXT PRIMARY KEY," .. "hash TEXT," .. "flag BOOL DEFAULT 0)")
    r3_0.commit(r1_2)
    r1_2:close()
  end,
  IsInfoUpdate = function()
    -- line: [39, 66] id: 3
    local r1_3 = r0_0.open(r4_0())
    local r2_3 = r1_3:prepare("SELECT isview FROM tags" .. " WHERE id=? AND flag=?")
    r2_3:bind_values(1, 0)
    local r3_3 = nil
    for r7_3 in r2_3:nrows() do
      r3_3 = 0 < r7_3.isview
    end
    r2_3:finalize()
    if r3_3 == nil then
      r2_3 = r1_3:prepare("REPLACE INTO tags" .. " (id, isview, download, flag)" .. " VALUES (?, ?, ?, ?)")
      r2_3:bind_values(1, 0, 0, 0)
      r2_3:step()
      r2_3:finalize()
      r3_3 = false
    end
    r1_3:close()
    return r3_3
  end,
  InfoUpdateNew = function(r0_4)
    -- line: [69, 80] id: 4
    local r2_4 = r0_0.open(r4_0())
    local r3_4 = r2_4:prepare("UPDATE tags SET isview=? WHERE id=?")
    local r4_4 = nil	-- notice: implicit variable refs by block#[3]
    if r0_4 then
      r4_4 = 1
      if not r4_4 then
        r4_4 = 0
      end
    else
        r4_4 = 0
    end
    assert(r3_4, r2_4:errmsg())
    r3_4:bind_values(r4_4, 1)
    r3_4:step()
    r3_4:finalize()
    r2_4:close()
  end,
  IsInfoDownload = function()
    -- line: [82, 97] id: 5
    local r1_5 = r0_0.open(r4_0())
    local r2_5 = r1_5:prepare("SELECT download FROM tags" .. " WHERE id=? AND flag=?")
    r2_5:bind_values(1, 0)
    local r3_5 = nil
    for r7_5 in r2_5:nrows() do
      r3_5 = r7_5.download == 0
    end
    r2_5:finalize()
    r1_5:close()
    return r3_5
  end,
  InfoUpdateDownload = function(r0_6)
    -- line: [100, 110] id: 6
    local r2_6 = r0_0.open(r4_0())
    local r3_6 = r2_6:prepare("UPDATE tags SET download=? WHERE id=?")
    local r4_6 = nil	-- notice: implicit variable refs by block#[3]
    if r0_6 then
      r4_6 = 1
      if not r4_6 then
        r4_6 = 0
      end
    else
        r4_6 = 0
    end
    r3_6:bind_values(r4_6, 1)
    r3_6:step()
    r3_6:finalize()
    r2_6:close()
  end,
  RegisterFileHash = function(r0_7)
    -- line: [112, 130] id: 7
    local r2_7 = r0_0.open(r4_0())
    r3_0.begin_transcation(r2_7)
    local r3_7 = r2_7:prepare("REPLACE INTO filehash" .. " (fname, hash, flag)" .. " VALUES (?, ?, ?)")
    assert(r3_7, r2_7:errmsg())
    for r7_7, r8_7 in pairs(r0_7) do
      r3_7:reset()
      r3_7:bind_values(r8_7.filename, r8_7.hash, 0)
      r3_7:step()
    end
    r3_7:finalize()
    r3_0.commit(r2_7)
    r2_7:close()
  end,
  LoadFileHash = function()
    -- line: [132, 143] id: 8
    local r1_8 = r0_0.open(r4_0())
    local r2_8 = {}
    local r3_8 = nil
    for r7_8 in r1_8:nrows("SELECT fname, hash FROM filehash WHERE flag=0") do
      r2_8[r7_8.fname] = r7_8.hash
    end
    r1_8:close()
    return r2_8
  end,
  UpdateFileHash = function(r0_9, r1_9)
    -- line: [145, 155] id: 9
    local r3_9 = r0_0.open(r4_0())
    local r4_9 = r3_9:prepare("UPDATE filehash" .. " SET hash=? WHERE fname=?")
    r4_9:bind_values(r1_9, r0_9)
    r4_9:step()
    r4_9:finalize()
    r3_9:close()
  end,
}
