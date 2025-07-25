-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("crypto")
local r1_0 = require("lfs")
local r2_0 = require("json")
local r3_0 = "files.txt"
local r4_0 = "info"
local r5_0 = "bingo.html"
local r6_0 = _G.INFO_SERVER_URL
local r7_0 = 0
local r8_0 = 10
local r9_0 = 20
local r10_0 = 30
local r11_0 = 40
local r12_0 = 50
local r13_0 = 60
local r14_0 = 70
local r15_0 = 3
local r16_0 = 0
local r17_0 = 0
local r18_0 = false
local r19_0 = nil
local function r20_0()
  -- line: [31, 36] id: 1
  assert(r1_0.chdir(system.pathForFile("", system.CachesDirectory)), debug.traceback())
  r1_0.mkdir(r4_0)
end
local function r21_0()
  -- line: [38, 40] id: 2
  return system.pathForFile(r4_0 .. "/" .. r3_0, system.CachesDirectory)
end
local function r22_0()
  -- line: [44, 58] id: 3
  local r1_3 = io.open(r21_0(), "r")
  assert(r1_3, debug.traceback())
  local r2_3 = nil
  local r3_3 = {}
  for r7_3 in r1_3:lines() do
    r2_3 = util.StringSplit(r7_3, "\t")
    if #r2_3 == 2 then
      table.insert(r3_3, {
        filename = r2_3[1],
        hash = r2_3[2],
      })
    end
  end
  r1_3:close()
  return r3_3
end
local function r23_0()
  -- line: [61, 84] id: 4
  local r0_4 = "info/" .. _G.UILanguage .. "/"
  r20_0()
  util.CopyFile(system.pathForFile(r0_4 .. r3_0, system.ResourceDirectory), r21_0())
  db.IsInfoUpdate()
  local r3_4 = r22_0()
  for r7_4, r8_4 in pairs(r3_4) do
    local r9_4 = r8_4.filename
    util.CopyFile(system.pathForFile(r0_4 .. "html/" .. r9_4, system.ResourceDirectory), system.pathForFile(r4_0 .. "/" .. r9_4, system.CachesDirectory))
  end
  db.RegisterFileHash(r3_4)
  db.InfoUpdateDownload(true)
end
local function r24_0(r0_5)
  -- line: [86, 123] id: 5
  local r1_5 = r0_5.id
  _G.bingo[r1_5] = {}
  _G.bingo[r1_5].start_date = r0_5.start_date
  _G.bingo[r1_5].end_date = r0_5.end_date
  local r2_5 = ""
  local r3_5 = ""
  local r4_5 = util.StringSplit(r0_5.start_date, " ")
  local r5_5 = util.StringSplit(r4_5[1], "-")
  local r6_5 = util.StringSplit(r4_5[2], ":")
  r2_5 = os.time({
    year = r5_5[1],
    month = r5_5[2],
    day = r5_5[3],
    hour = r6_5[1],
    min = r6_5[2],
    sec = r6_5[3],
  })
  local r7_5 = util.StringSplit(r0_5.end_date, " ")
  local r8_5 = util.StringSplit(r7_5[1], "-")
  local r9_5 = util.StringSplit(r7_5[2], ":")
  r3_5 = os.time({
    year = r8_5[1],
    month = r8_5[2],
    day = r8_5[3],
    hour = r9_5[1],
    min = r9_5[2],
    sec = r9_5[3],
  })
  _G.bingo[r1_5].start_date_time = r2_5
  _G.bingo[r1_5].end_date_time = r3_5
end
local function r25_0()
  -- line: [126, 155] id: 6
  _G.bingo = {}
  io.input(system.pathForFile(r4_0 .. "/" .. r5_0, system.CachesDirectory))
  while true do
    local r0_6 = io.read()
    if r0_6 == nil then
      break
    end
    local r1_6 = r2_0.decode(r0_6)
    r24_0(r1_6)
    local r2_6 = ""
    server.GetServerTime(function(r0_7)
      -- line: [136, 153] id: 7
      if r0_7 ~= nil and r0_7.response ~= nil then
        local r2_7 = r2_0.decode(r0_7.response)
        if r2_7.status == 0 then
          native.showAlert("DefenseWitches", db.GetMessage(35), {
            "OK"
          })
        elseif r2_7.result ~= nil then
          local r1_7 = tonumber(r2_7.result)
          r2_6 = tonumber(r2_7.result)
        end
      end
    end)
    -- close: r0_6
  end
end
local function r26_0()
  -- line: [157, 183] id: 8
  local r0_8 = r19_0
  if r0_8 then
    local r1_8 = nil
    local r2_8 = nil
    local r3_8 = nil
    local r4_8 = nil
    for r8_8, r9_8 in pairs(r0_8.queue) do
      r1_8 = r9_8.filename
      r2_8 = r9_8.hash
      r3_8 = system.pathForFile(r1_8, system.TemporaryDirectory)
      r4_8 = system.pathForFile(r4_0 .. "/" .. r1_8, system.CachesDirectory)
      util.CopyFile(r3_8, r4_8)
      os.remove(r3_8)
      db.UpdateFileHash(r1_8, r2_8)
      if r1_8 == r5_0 then
        r25_0()
      end
    end
    db.InfoUpdateDownload(true)
  end
  if r0_8 and r0_8.ev then
    events.Delete(r0_8.ev)
  end
  r19_0 = nil
end
local function r27_0(r0_9)
  -- line: [186, 204] id: 9
  if r0_9.isError then
    if r19_0 and r19_0.ev then
      events.Delete(r19_0.ev)
      r19_0 = nil
    end
  else
    local r1_9 = r19_0
    if r1_9 == nil then
      return 
    end
    r1_9.nr = r1_9.nr + 1
    if r1_9.max < r1_9.nr then
      r26_0()
    else
      r1_9.download = false
    end
  end
end
local function r28_0(r0_10, r1_10, r2_10)
  -- line: [206, 224] id: 10
  if r1_10.download then
    return true
  end
  local r4_10 = r1_10.max
  local r5_10 = r1_10.queue[r1_10.nr]
  network.download(r5_10.url, "GET", r27_0, {
    timeout = 3,
  }, r5_10.filename, system.TemporaryDirectory)
  r1_10.download = true
  return true
end
local function r29_0()
  -- line: [226, 259] id: 11
  local r0_11 = r6_0 .. _G.UILanguage .. "/html/"
  local r1_11 = r22_0()
  local r2_11 = db.LoadFileHash()
  local r3_11 = nil
  local r4_11 = nil
  local r5_11 = {}
  for r9_11, r10_11 in pairs(r1_11) do
    r3_11 = r10_11.filename
    r4_11 = r10_11.hash
    if r2_11[r3_11] ~= r4_11 then
      table.insert(r5_11, {
        filename = r3_11,
        url = r0_11 .. r3_11,
        hash = r4_11,
      })
    end
  end
  if #r5_11 < 1 then
    r26_0()
    return 
  end
  local r6_11 = {
    queue = r5_11,
    download = false,
    max = #r5_11,
    nr = 1,
    ev = nil,
  }
  r6_11.ev = events.Register(r28_0, r6_11, 100)
  r19_0 = r6_11
end
local function r30_0(r0_12)
  -- line: [262, 339] id: 12
  local r1_12 = r21_0()
  local r2_12 = io.open(r1_12, "rb")
  if r2_12 == nil then
    r23_0()
    r2_12 = io.open(r1_12, "rb")
    if r2_12 == nil then
      return 
    end
  end
  local r3_12 = r2_12:read("*a")
  r2_12:close()
  local r4_12 = nil
  local r5_12 = nil
  if r3_12 then
    r4_12 = r0_0.digest(r0_0.sha512, r3_12)
  else
    return 
  end
  local r6_12 = r6_0 .. _G.UILanguage .. "/" .. r3_0
  DebugPrint("INFO: " .. r6_12)
  network.download(r6_12, "GET", function(r0_13)
    -- line: [291, 338] id: 13
    if r0_13.isError then
      DebugPrint("INFO: Download err.")
      if not db.IsInfoUpdate() then
        r0_12()
      end
      r16_0 = r11_0
      return 
    elseif r0_13.status == 200 then
      DebugPrint("INFO: response:" .. r0_13.status)
      r1_12 = system.pathForFile(r3_0, system.TemporaryDirectory)
      r2_12 = io.open(r1_12, "rb")
      if r2_12 == nil then
        return 
      end
      r3_12 = r2_12:read("*a")
      r2_12:close()
      if r3_12 then
        r5_12 = r0_0.digest(r0_0.sha512, r3_12)
        if r4_12 ~= r5_12 then
          util.CopyFile(r1_12, r21_0())
          db.InfoUpdateNew(false)
          db.InfoUpdateDownload(false)
          r0_12()
        elseif not db.IsInfoUpdate() then
          r0_12()
        end
        os.remove(r1_12)
        r18_0 = true
      end
    elseif r0_13.status == 404 then
      DebugPrint("INFO: 404")
      r16_0 = r7_0
      return 
    else
      DebugPrint("INFO: HTTP error(" .. r0_13.status .. ")")
      if not db.IsInfoUpdate() then
        r0_12()
      end
      r16_0 = r11_0
      return 
    end
  end, {
    timeout = 3,
  }, r3_0, system.TemporaryDirectory)
end
local function r31_0()
  -- line: [341, 342] id: 14
end
local function r32_0()
  -- line: [344, 346] id: 15
  r30_0(r31_0)
end
local function r33_0(r0_16)
  -- line: [348, 397] id: 16
  local r1_16 = false
  if r16_0 ~= r7_0 then
    if r16_0 == r8_0 then
      r18_0 = false
      r32_0()
      r16_0 = r9_0
      DebugPrint("INFO:Filelist download start.")
    elseif r16_0 == r9_0 then
      if r18_0 then
        r16_0 = r13_0
        DebugPrint("INFO:check download.")
      end
      DebugPrint("INFO:Finish Filelist download.")
    elseif r16_0 == r11_0 then
      r17_0 = r17_0 + 1
      if r15_0 < r17_0 then
        r16_0 = r7_0
      else
        timer.performWithDelay(1000, function()
          -- line: [375, 377] id: 17
          r16_0 = r8_0
        end)
        r16_0 = r12_0
        DebugPrint("INFO:Retry download.." .. r17_0)
      end
    elseif r16_0 ~= r12_0 then
      if r16_0 == r13_0 then
        r29_0()
        r16_0 = r14_0
        DebugPrint("INFO:contents download start.")
      elseif r16_0 == r14_0 and r19_0 == nil then
        r16_0 = r7_0
        DebugPrint("INFO:contents downloaded.")
      end
    end
  end
end
return {
  Init = function()
    -- line: [407, 415] id: 19
    r17_0 = 0
    r18_0 = false
    r16_0 = r8_0
    Runtime:addEventListener("enterFrame", r33_0)
  end,
  IsUpdate = r30_0,
  IsFinish = function()
    -- line: [399, 405] id: 18
    local r0_18 = r16_0 == r7_0
  end,
}
