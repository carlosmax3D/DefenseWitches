-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = _G.events or require("tool.events")
local r1_0 = require("crypto")
local r2_0 = require("json")
local r3_0 = "resume.bin"
local r4_0 = "魔法少女の宴"
local r5_0 = 0
local r6_0 = nil
local function r7_0()
  -- line: [17, 19] id: 1
  return system.pathForFile(r3_0, system.DocumentsDirectory)
end
local function r8_0()
  -- line: [21, 28] id: 2
  if game ~= nil and game.IsNotPauseTypeNone() then
    return 
  end
  r5_0 = r5_0 + 1
end
local function r9_0(r0_3)
  -- line: [31, 42] id: 3
  local r1_3 = r2_0.decode(r0_3)
  local r2_3 = r1_3.data
  local r3_3 = r1_3.hexdump
  if r2_3 == nil or r3_3 == nil then
    return nil
  end
  if r1_0.digest(r1_0.sha512, r2_3 .. r4_0) ~= r3_3 then
    return nil
  else
    return r2_0.decode(r2_3)
  end
end
local function r12_0(r0_6, r1_6)
  -- line: [52, 58] id: 6
  table.insert(r6_0, {
    time = r5_0,
    command = r0_6,
    data = r1_6,
  })
end
return {
  Init = function()
    -- line: [44, 46] id: 4
    r0_0.RegisterFirstProc(r8_0)
  end,
  Cleanup = function()
    -- line: [48, 50] id: 5
    r0_0.DeleteFirstProc(r8_0)
  end,
  DataPush = r12_0,
  DataInit = function()
    -- line: [60, 64] id: 7
    r5_0 = 0
    r6_0 = {}
    r12_0("version", {
      version = _G.Version,
    })
  end,
  DataSave = function()
    -- line: [66, 83] id: 8
    local r0_8 = r7_0()
    if _G.IsSimulator then
      DebugPrint("saving:" .. r0_8)
    end
    local r1_8 = io.open(r0_8, "w")
    local r2_8 = r2_0.encode(r6_0)
    r1_8:write(r2_0.encode({
      data = r2_8,
      hexdump = r1_0.digest(r1_0.sha512, r2_8 .. r4_0),
    }))
    r1_8:close()
    if not _G.IsSimulator then
      native.setSync(r3_0, {
        iCloudBackup = false,
      })
    end
  end,
  DataLoad = function()
    -- line: [85, 109] id: 9
    local r1_9, r2_9 = io.open(r7_0(), "r")
    if r1_9 then
      r6_0 = r9_0(r1_9:read())
      r1_9:close()
      if r6_0 then
        for r7_9, r8_9 in pairs(r6_0) do
          if r8_9.command == "version" then
            if r8_9.data.version ~= _G.Version then
              r6_0 = nil
            else
              table.remove(r6_0, r7_9)
            end
          end
        end
      end
    else
      r6_0 = nil
    end
    return r6_0
  end,
  DataResume = function()
    -- line: [111, 127] id: 10
    local r1_10, r2_10 = io.open(r7_0(), "r")
    if r1_10 then
      local r4_10 = r9_0(r1_10:read())
      r1_10:close()
      r6_0 = {}
      if r4_10 then
        for r8_10, r9_10 in pairs(r4_10) do
          if r9_10.command ~= "start" and r9_10.command ~= "version" then
            table.insert(r6_0, r9_10)
          end
        end
      end
    end
  end,
  DataClear = function()
    -- line: [129, 135] id: 11
    local r1_11, r2_11 = os.remove(r7_0())
    if not r1_11 and _G.IsSimulator then
      DebugPrint("data clear: " .. r2_11)
    end
  end,
  FileExist = function()
    -- line: [137, 165] id: 12
    local r1_12 = io.open(r7_0(), "r")
    local r2_12 = true
    if r1_12 then
      local r4_12 = r9_0(r1_12:read())
      if r4_12 then
        for r8_12, r9_12 in pairs(r4_12) do
          if r9_12.command == "version" and r9_12.data.version ~= _G.Version then
            native.showAlert("DefenseWitches", db.GetMessage(55), {
              "OK"
            })
            r2_12 = false
          end
        end
      else
        native.showAlert("DefenseWitches", db.GetMessage(55), {
          "OK"
        })
        r2_12 = false
      end
      r1_12:close()
    else
      r2_12 = false
    end
    return r2_12
  end,
}
