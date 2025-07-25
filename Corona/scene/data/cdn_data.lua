-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("sqlite3")
local r1_0 = require("lfs")
local r2_0 = require("crypto")
local r3_0 = _G.CDN_VERSION
local r4_0 = _G.CDN_VERSION_URLSTR
local r5_0 = "gdata"
local r6_0 = "_tmpfile"
local r7_0 = "files.txt"
local r8_0 = "gdata.sqlite"
local r9_0 = 0
local r10_0 = 0
local r11_0 = 0
local r12_0 = 10
local r13_0 = 20
local r14_0 = 30
local r15_0 = 40
local r16_0 = 50
local r17_0 = 10
local r18_0 = {
  "hint"
}
local r19_0 = false
local r20_0 = nil
local r21_0 = {}
local function r22_0()
  -- line: [36, 44] id: 1
  local r0_1 = -1
--  if nil and not nil then
--    go to label_5	-- block#1 is visited secondly
--  end
  return r0_1
end
local function r23_0(r0_2)
  -- line: [46, 59] id: 2
  -- notice: unreachable block#1
  -- notice: unreachable block#2
  assert(r0_2, debug.traceback())
  r0_2:exec("BEGIN TRANSACTION")
end
local function r24_0(r0_3)
  -- line: [61, 74] id: 3
  -- notice: unreachable block#2
  -- notice: unreachable block#1
  assert(r0_3, debug.traceback())
  r0_3:exec("COMMIT TRANSACTION")
end
local function r25_0()
  -- line: [77, 94] id: 4
  local r1_4 = io.open(system.pathForFile(r5_0 .. "/" .. r7_0, system.DocumentsDirectory), "r")
  if r1_4 then
    local r2_4 = nil
    local r3_4 = {}
    for r7_4 in r1_4:lines() do
      r2_4 = util.StringSplit(r7_4, "\t")
      if #r2_4 == 2 then
        table.insert(r3_4, {
          filename = r2_4[1],
          hash = r2_4[2],
        })
      end
    end
    r1_4:close()
    return r3_4
  else
    return nil
  end
end
local function r26_0()
  -- line: [96, 98] id: 5
  return system.pathForFile(r8_0, system.DocumentsDirectory)
end
local function r27_0(r0_6)
  -- line: [100, 116] id: 6
  local r2_6 = r0_0.open(r26_0())
  r23_0(r2_6)
  local r3_6 = r2_6:prepare("REPLACE INTO filehash" .. " (fname, hash, flag)" .. " VALUES (?, ?, ?)")
  assert(r3_6, r2_6:errmsg())
  for r7_6, r8_6 in pairs(r0_6) do
    r3_6:reset()
    r3_6:bind_values(r8_6.filename, r8_6.hash, 0)
    r3_6:step()
  end
  r3_6:finalize()
  r24_0(r2_6)
  r2_6:close()
end
local function r28_0(r0_7, r1_7)
  -- line: [118, 127] id: 7
  local r3_7 = r0_0.open(r26_0())
  local r4_7 = r3_7:prepare("UPDATE filehash" .. " SET flag=? WHERE fname=?")
  r4_7:bind_values(r1_7, r0_7)
  r4_7:step()
  r4_7:finalize()
  r3_7:close()
end
local function r29_0(r0_8, r1_8)
  -- line: [129, 138] id: 8
  local r3_8 = r0_0.open(r26_0())
  local r4_8 = r3_8:prepare("UPDATE filehash" .. " SET hash=? WHERE fname=?")
  r4_8:bind_values(r1_8, r0_8)
  r4_8:step()
  r4_8:finalize()
  r3_8:close()
end
local function r30_0()
  -- line: [141, 161] id: 9
  local r1_9 = r0_0.open(r26_0())
  if r1_9 then
    local r2_9 = {}
    local r3_9 = nil
    local r4_9 = 0
    for r8_9 in r1_9:nrows("SELECT fname, hash FROM filehash") do
      r2_9[r8_9.fname] = r8_9.hash
      r4_9 = r4_9 + 1
    end
    r1_9:close()
    if r4_9 == 0 then
      return nil
    else
      return r2_9
    end
  end
end
local function r31_0(r0_10)
  -- line: [164, 182] id: 10
  local r2_10 = r0_0.open(r26_0())
  local r3_10 = 0
  if r2_10 then
    local r5_10 = r2_10:prepare("SELECT flag FROM filehash WHERE fname=?")
    r5_10:bind_values(r0_10)
    for r9_10 in r5_10:nrows() do
      r3_10 = r9_10.flag
    end
    r5_10:finalize()
    r2_10:close()
  end
  return r3_10
end
local function r32_0()
  -- line: [184, 200] id: 11
  local r1_11 = r0_0.open(r26_0())
  local r2_11 = nil
  r23_0(r1_11)
  r1_11:exec("CREATE TABLE IF NOT EXISTS filehash (" .. "fname TEXT PRIMARY KEY," .. "hash TEXT," .. "flag BOOL DEFAULT 0)")
  r24_0(r1_11)
  r1_11:close()
end
local function r33_0(r0_12)
  -- line: [203, 241] id: 12
  local r1_12 = system.pathForFile(r0_12, system.TemporaryDirectory)
  local r3_12 = util.StringSplit(r0_12, "_")[2]
  local r4_12, r5_12 = string.find(r3_12, ".", 0, true)
  local r8_12 = require("cdn.size_" .. string.sub(r3_12, 1, r5_12 - 1)).GetData()
  local r9_12 = io.open(r1_12, "rb")
  if r9_12 then
    for r13_12 = 1, #r8_12, 1 do
      local r14_12 = r9_12:read(r8_12[r13_12][1])
      local r15_12 = nil
      if r8_12[r13_12][3] == true then
        r15_12 = system.pathForFile(r8_12[r13_12][2], system.DocumentsDirectory)
        util.Mkdir(util.Dirname(r15_12))
      else
        r15_12 = system.pathForFile(r5_0 .. "/" .. r2_0.digest(r2_0.md5, r8_12[r13_12][2]), system.DocumentsDirectory)
      end
      util.ResDebug("ExtractPackFile :" .. r15_12)
      local r16_12 = io.open(r15_12, "wb")
      if r16_12 then
        r16_12:write(r14_12)
        r16_12:close()
      else
        util.ResDebug("file2 err.")
      end
    end
    r9_12:close()
    return true
  else
    return false
  end
end
local function r34_0(r0_13)
  -- line: [244, 261] id: 13
  if r0_13.isError then
    util.Debug("Network error - download failed")
  elseif r0_13.status == 200 then
    local r2_13 = r1_0.chdir(system.pathForFile("", system.DocumentsDirectory))
    r1_0.mkdir(r5_0)
    util.CopyFile(system.pathForFile(r6_0, system.TemporaryDirectory), system.pathForFile(r5_0 .. "/" .. r7_0, system.DocumentsDirectory))
  else
    util.ResDebug("Download NG. " .. r0_13.status)
  end
  r19_0 = true
end
local function r35_0()
  -- line: [263, 266] id: 14
  network.download(r4_0 .. r3_0 .. "/" .. r7_0, "GET", r34_0, r6_0, system.TemporaryDirectory)
end
local function r36_0()
  -- line: [268, 275] id: 15
  if r20_0 and r20_0.ev then
    events.Delete(r20_0.ev)
  end
  r20_0 = nil
  r21_0 = {}
end
local function r37_0(r0_16, r1_16, r2_16)
  -- line: [281, 382] id: 16
  local function r3_16(r0_17, r1_17)
    -- line: [283, 292] id: 17
    local r2_17 = false
    for r6_17, r7_17 in ipairs(r1_17) do
      if "pack_" .. r7_17 .. ".bin" == r0_17 then
        r2_17 = true
      end
    end
    return r2_17
  end
  local function r4_16(r0_18, r1_18, r2_18)
    -- line: [295, 320] id: 18
    local r3_18 = {}
    if r2_18 == nil then
      r3_18 = {
        unpack(r18_0)
      }
    else
      r3_18 = {
        unpack(r2_18)
      }
      for r7_18, r8_18 in ipairs(r18_0) do
        r3_18[#r3_18 + 1] = r8_18
      end
    end
    if r1_18 == nil then
      if r3_16(r0_18, r3_18) == false then
        return true
      end
      return false
    else
      if r3_16(r0_18, r1_18) == true then
        return true
      end
      return false
    end
  end
  r36_0()
  local r5_16 = r4_0 .. r3_0 .. "/"
  local r6_16 = r25_0()
  local r7_16 = r30_0()
  local r8_16 = nil
  local r9_16 = nil
  local r10_16 = 0
  if r6_16 == nil then
    r9_0 = r12_0
    return true
  end
  if r7_16 then
    for r14_16, r15_16 in pairs(r6_16) do
      r8_16 = r15_16.filename
      r9_16 = r15_16.hash
      if r2_16 == true or r4_16(r8_16, r0_16, r1_16) then
        if r7_16[r8_16] == nil then
          table.insert(r21_0, {
            filename = r8_16,
            url = r5_16 .. r8_16,
            hash = r9_16,
          })
          r10_16 = r10_16 + 1
        elseif r7_16[r8_16] ~= r9_16 then
          table.insert(r21_0, {
            filename = r8_16,
            url = r5_16 .. r8_16,
            hash = r9_16,
          })
          r10_16 = r10_16 + 1
        else
          if r31_0(r8_16) == 0 then
            table.insert(r21_0, {
              filename = r8_16,
              url = r5_16 .. r8_16,
              hash = r9_16,
            })
          end
          r10_16 = r10_16 + 1
        end
      end
    end
  else
    for r14_16, r15_16 in pairs(r6_16) do
      r8_16 = r15_16.filename
      r9_16 = r15_16.hash
      table.insert(r21_0, {
        filename = r8_16,
        url = r5_16 .. r8_16,
        hash = r9_16,
      })
    end
    r27_0(r6_16)
  end
  if #r21_0 > 0 then
    r20_0 = {
      queue = r21_0,
      download = false,
      max = #r21_0,
      nr = 1,
      ev = nil,
    }
    return true
  else
    return false
  end
end
local function r38_0(r0_19)
  -- line: [384, 419] id: 19
  if r0_19.isError then
    events.Delete(r20_0.ev)
    r20_0 = nil
    r10_0 = r17_0
    r9_0 = r11_0
  elseif r0_19.status == 200 then
    if r20_0 == nil then
      return 
    end
    local r1_19 = r20_0.queue[r20_0.nr].filename
    local r2_19 = r20_0.queue[r20_0.nr].hash
    r33_0(r1_19)
    r27_0({
      {
        filename = r1_19,
        hash = r2_19,
      }
    })
    r28_0(r1_19, 1)
    os.remove(system.pathForFile(r1_19, system.TemporaryDirectory))
    r20_0.nr = r20_0.nr + 1
    if r20_0.max < r20_0.nr then
      r36_0()
      _G.metrics.cdn_download_complie()
    else
      r20_0.download = false
    end
  else
    util.Debug("Download NG. " .. r0_19.status)
  end
end
local function r39_0(r0_20, r1_20, r2_20)
  -- line: [421, 440] id: 20
  if r1_20.download then
    return true
  end
  local r4_20 = r1_20.max
  local r5_20 = r1_20.queue[r1_20.nr]
  local r6_20 = r5_20.filename
  local r7_20 = r5_20.url
  local r8_20 = _G.UserToken
  if r8_20 == nil then
    r8_20 = "none"
  end
  network.download(r7_20 .. "?key=" .. r8_20, "GET", r38_0, r6_20, system.TemporaryDirectory)
  r1_20.download = true
  return true
end
local function r40_0()
  -- line: [442, 446] id: 21
  if r20_0 ~= nil then
    r20_0.ev = events.Register(r39_0, r20_0, 100)
  end
end
local function r41_0(r0_22)
  -- line: [448, 473] id: 22
  local r1_22 = false
  if r9_0 ~= r11_0 then
    if r9_0 == r12_0 then
      r35_0()
      r9_0 = r13_0
    elseif r9_0 == r13_0 and r19_0 then
      r9_0 = r11_0
    elseif r9_0 == r14_0 then
      if r37_0(nil, nil, true) == true then
        r9_0 = r15_0
      else
        r9_0 = r11_0
      end
    elseif r9_0 == r15_0 then
      r40_0()
      r9_0 = r16_0
    end
  end
end
local r42_0 = {
  InitCDN = function()
    -- line: [477, 489] id: 23
    local r0_23 = nil	-- notice: implicit variable refs by block#[0]
    r20_0 = r0_23
    r0_23 = {}
    r21_0 = r0_23
    r9_0 = r12_0
    r19_0 = false
    r32_0()
    r0_23 = system.pathForFile("", system.DocumentsDirectory)
    local r1_23 = r1_0.chdir(r0_23)
    r1_0.mkdir(r5_0)
    Runtime:addEventListener("enterFrame", r41_0)
  end,
  DownloadFilelist = function()
    -- line: [491, 493] id: 24
    r35_0()
  end,
  DownloadRequest = function()
    -- line: [495, 498] id: 25
    r9_0 = r14_0
    _G.metrics.cdn_download_start()
  end,
  DownloadStart = function()
    -- line: [500, 502] id: 26
    r40_0()
  end,
  DownloadState = function()
    -- line: [504, 506] id: 27
    return r9_0
  end,
  GetDownloadEvent = function()
    -- line: [508, 510] id: 28
    return r20_0
  end,
  GetDownloadQueue = function()
    -- line: [512, 514] id: 29
    return r21_0
  end,
  Close = function()
    -- line: [516, 523] id: 30
    if r20_0 then
      if r20_0.ev then
        events.Delete(r20_0.ev)
      end
      r20_0 = nil
    end
  end,
  CheckFilelistForce = function()
    -- line: [527, 529] id: 31
    return r37_0(nil, nil, true)
  end,
  CheckFilelist = function(r0_32, r1_32, r2_32)
    -- line: [531, 533] id: 32
    return r37_0(r0_32, r1_32, r2_32)
  end,
  GetImage = function(r0_33)
    -- line: [535, 538] id: 33
    return r5_0 .. "/" .. r2_0.digest(r2_0.md5, r0_33)
  end,
}
local function r43_0(r0_34)
  -- line: [540, 548] id: 34
  local r1_34 = false
  local r2_34 = io.open(r0_34, "r")
  if r2_34 then
    io.close(r2_34)
    r1_34 = true
  end
  return r1_34
end
function r42_0.NewImage(r0_35, r1_35, r2_35, r3_35, r4_35)
  -- line: [550, 589] id: 35
  local r5_35 = r42_0.GetImage(r1_35)
  local r6_35 = system.pathForFile(r1_35, system.ResourceDirectory)
  local r7_35 = system.pathForFile(r5_35, system.DocumentsDirectory)
  local r8_35 = true
  if r7_35 ~= nil then
    r8_35 = r43_0(r7_35)
  else
    r8_35 = false
  end
  if r8_35 then
    if r7_35 ~= nil then
      util.ResDebug("FROM CDN:" .. r7_35)
    end
    if r2_35 == nil then
      if r4_35 == nil then
        return display.newImage(r0_35, r5_35, system.DocumentsDirectory)
      else
        return display.newImage(r0_35, r5_35, system.DocumentsDirectory, r4_35)
      end
    else
      return display.newImage(r0_35, r5_35, system.DocumentsDirectory, r2_35, r3_35, r4_35)
    end
  else
    if r6_35 ~= nil then
      util.ResDebug("FROM RES:" .. r6_35)
    end
    if r2_35 == nil then
      if r4_35 == nil then
        return display.newImage(r0_35, r1_35)
      else
        return display.newImage(r0_35, r1_35, r4_35)
      end
    else
      return display.newImage(r0_35, r1_35, r2_35, r3_35, r4_35)
    end
  end
end
function r42_0.NewImageNG(r0_36, r1_36, r2_36, r3_36)
  -- line: [591, 630] id: 36
  local r4_36 = r42_0.GetImage(r0_36)
  local r5_36 = system.pathForFile(r0_36, system.ResourceDirectory)
  local r6_36 = system.pathForFile(r4_36, system.DocumentsDirectory)
  local r7_36 = true
  if r6_36 ~= nil then
    r7_36 = r43_0(r6_36)
  else
    r7_36 = false
  end
  if r7_36 then
    if r6_36 ~= nil then
      util.ResDebug("FROM CDN:" .. r6_36)
    end
    if r1_36 == nil then
      if r3_36 == nil then
        return display.newImage(r4_36, system.DocumentsDirectory)
      else
        return display.newImage(r4_36, system.DocumentsDirectory, r3_36)
      end
    else
      return display.newImage(r4_36, system.DocumentsDirectory, r1_36, r2_36, r3_36)
    end
  else
    if r5_36 ~= nil then
      util.ResDebug("FROM RES:" .. r5_36)
    end
    if r1_36 == nil then
      if r3_36 == nil then
        return display.newImage(r0_36)
      else
        return display.newImage(r0_36, r3_36)
      end
    else
      return display.newImage(r0_36, r1_36, r2_36, r3_36)
    end
  end
end
function r42_0.NewMask(r0_37)
  -- line: [632, 655] id: 37
  local r1_37 = r42_0.GetImage(r0_37)
  local r2_37 = system.pathForFile(r0_37, system.ResourceDirectory)
  local r3_37 = system.pathForFile(r1_37, system.DocumentsDirectory)
  local r4_37 = true
  if r3_37 ~= nil then
    r4_37 = r43_0(r3_37)
  else
    r4_37 = false
  end
  if r4_37 then
    if r3_37 ~= nil then
      util.ResDebug("FROM CDN:" .. r3_37)
    end
    return graphics.newMask(r1_37, system.DocumentsDirectory)
  else
    if r2_37 ~= nil then
      util.ResDebug("FROM RES:" .. r2_37)
    end
    return graphics.newMask(r0_37)
  end
end
return r42_0
