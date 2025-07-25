-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r11_0 = nil	-- notice: implicit variable refs by block#[0]
local r0_0 = require("sqlite3")
local r1_0 = require("json")
local r2_0 = require("crypto")
local r3_0 = require("db.BaseDB")
local r4_0 = "queue.sqlite"
local function r5_0()
  -- line: [8, 10] id: 1
  return system.pathForFile(r4_0, system.DocumentsDirectory)
end
local function r10_0(r0_6)
  -- line: [110, 122] id: 6
  assert(r0_6, debug.traceback())
  local r2_6 = r0_0.open(r5_0())
  local r3_6 = r2_6:prepare("DELETE FROM achievement WHERE id=:id")
  assert(r3_6, r2_6:errmsg())
  r3_6:bind_names({
    id = r0_6,
  })
  r3_6:step()
  r3_6:finalize()
  r2_6:close()
end
function r11_0(r0_7, r1_7)
  -- line: [124, 211] id: 7
  assert(r0_7 and r1_7, debug.traceback())
  assert(type(r1_7) == "table", debug.traceback())
  assert(#r1_7 == 5, debug.traceback())
  local r3_7 = r0_0.open(r5_0())
  local r4_7 = r3_7:prepare("SELECT * FROM achievement" .. " WHERE flag=:flag" .. " ORDER BY ctime LIMIT 1")
  assert(r4_7, r3_7:errmsg())
  r4_7:bind_names({
    flag = 0,
  })
  local r5_7 = nil
  local r6_7 = nil
  local r7_7 = nil
  local r8_7 = nil
  local r9_7 = nil
  for r13_7 in r4_7:nrows() do
    r6_7 = r13_7.id
    r5_7 = r13_7.cmd
    r7_7 = r13_7.data
    r9_7 = r13_7.date
    r8_7 = r13_7.hex
  end
  r4_7:finalize()
  r3_7:close()
  local r10_7 = r1_7[1]
  local r11_7 = r1_7[2]
  local r12_7 = r1_7[3]
  local r13_7 = r1_7[4]
  local r14_7 = r1_7[5]
  if r7_7 == nil then
    r10_7(r11_7, r12_7, r13_7, r14_7)
    return 
  end
  local r15_7, r16_7 = r3_0.calc_checksum2({
    r7_7
  }, r9_7)
  if r15_7 ~= r8_7 then
    r10_0(r6_7)
    r11_0(r0_7, r1_7)
  else
    local r17_7 = r1_0.decode(r7_7)
    if r5_7 == "local" then
      server.UnlockLocalAchievement(r0_7, r17_7.achievements, r17_7.map, r17_7.stage, function(r0_8)
        -- line: [175, 185] id: 8
        if r0_8.isError then
          r13_7(r0_8)
        else
          r10_0(r6_7)
          r11_0(r0_7, r1_7)
        end
      end)
    elseif r5_7 == "world" then
      server.UnlockWorldAchievement(r0_7, r17_7.achievements, r17_7.map, function(r0_9)
        -- line: [190, 197] id: 9
        if r0_9.isError then
          r13_7(r0_9)
        else
          r10_0(r6_7)
          r11_0(r0_7, r1_7)
        end
      end)
    elseif r5_7 == "normal" then
      server.UnlockNormalAchievement(r0_7, r17_7.achievements, function(r0_10)
        -- line: [201, 208] id: 10
        if r0_10.isError then
          r13_7(r0_10)
        else
          r10_0(r6_7)
          r11_0(r0_7, r1_7)
        end
      end)
    end
  end
end
return {
  InitQueue = function()
    -- line: [12, 40] id: 2
    local r1_2 = r0_0.open(r5_0())
    local r2_2 = nil
    r1_2:exec("CREATE TABLE IF NOT EXISTS queue (" .. "id INTEGER PRIMARY KEY AUTOINCREMENT," .. "cmd TEXT," .. "data TEXT," .. "wait INTEGER," .. "ctime DATETIME," .. "utime DATETIME," .. "flag BOOL DEFAULT 0)")
    r1_2:exec("CREATE TABLE IF NOT EXISTS achievement (" .. "id INTEGER PRIMARY KEY AUTOINCREMENT," .. "cmd TEXT," .. "data TEXT," .. "hex TEXT," .. "date TEXT," .. "ctime DATETIME DEFAULT (CURRENT_TIMESTAMP)," .. "flag BOOL DEFAULT 0)")
    r1_2:close()
  end,
  CountQueue = function()
    -- line: [42, 62] id: 3
    local r1_3 = r0_0.open(r5_0())
    local r3_3 = 0
    for r7_3 in r1_3:nrows("SELECT COUNT(*) AS count FROM queue WHERE flag=0") do
      r3_3 = r7_3.count
    end
    if r3_3 > 0 then
      r1_3:exec("UPDATE queue SET flag=1 WHERE flag=0")
      native.showAlert("DefenseWitches", r3_0.GetMessage(210) .. "\n" .. r3_0.GetMessage(211), {
        "OK"
      })
    end
    r1_3:close()
    return 0
  end,
  SetAchieveQueue = function(r0_4, r1_4, r2_4, r3_4)
    -- line: [65, 88] id: 4
    assert(r0_4 and r1_4 and r2_4 and r3_4, debug.traceback())
    assert(type(r3_4) == "table", debug.traceback())
    local r4_4 = r1_0.encode({
      map = r1_4,
      stage = r2_4,
      achievements = r3_4,
    })
    local r5_4, r6_4 = r3_0.calc_checksum2({
      r4_4
    })
    local r8_4 = r0_0.open(r5_0())
    local r9_4 = r8_4:prepare("INSERT INTO achievement" .. " (cmd, data, hex, date)" .. " VALUES (:cmd, :data, :hex, :date)")
    assert(r9_4, r8_4:errmsg())
    r9_4:bind_names({
      cmd = r0_4,
      data = r4_4,
      hex = r5_4,
      date = r6_4,
    })
    r9_4:step()
    r9_4:finalize()
    r8_4:close()
  end,
  CountAchieveQueue = function()
    -- line: [90, 107] id: 5
    local r1_5 = r0_0.open(r5_0())
    local r2_5 = r1_5:prepare("SELECT COUNT(*) AS count FROM achievement" .. " WHERE flag=:flag")
    assert(r2_5, r1_5:errmsg())
    r2_5:bind_names({
      flag = 0,
    })
    local r3_5 = 0
    for r7_5 in r2_5:nrows() do
      r3_5 = r7_5.count
    end
    r2_5:finalize()
    r1_5:close()
    return r3_5
  end,
  DeleteAchieveQueue = r10_0,
  DisposeAchieveQueue = r11_0,
}
