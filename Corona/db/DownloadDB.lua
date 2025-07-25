-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("json")
local r1_0 = require("db.doc.GameData")
local r2_0 = require("db.doc.LeaderboardsData")
local r3_0 = "game.download"
local r4_0 = "DefenseWitchesSaveData"
local r5_0 = 1
return {
  ServerToLoad = function(r0_1, r1_1)
    -- line: [12, 14] id: 1
    server.LoadData(r0_1, r3_0, r1_1, system.TemporaryDirectory)
  end,
  MoveDownloadFile = function()
    -- line: [17, 22] id: 2
    local r0_2 = system.pathForFile(r3_0, system.TemporaryDirectory)
    util.CopyFile(r0_2, r1_0.gamedata_filename())
    os.remove(r0_2)
  end,
  DeleteDownloadFile = function()
    -- line: [25, 28] id: 3
    os.remove(system.pathForFile(r3_0, system.TemporaryDirectory))
  end,
  GetSaveDataType = function()
    -- line: [32, 45] id: 4
    local r1_4 = io.open(system.pathForFile(r3_0, system.TemporaryDirectory), "rb")
    local r3_4 = r1_4:read(string.len(r4_0))
    local r4_4 = nil
    if r3_4 == r4_0 then
      r4_4 = 1
    else
      r4_4 = 0
    end
    r1_4:close()
    return r4_4
  end,
  LoadData2 = function()
    -- line: [48, 66] id: 5
    local r0_5 = system.pathForFile(r3_0, system.TemporaryDirectory)
    local r1_5 = io.open(r0_5, "rb")
    r1_5:read(string.len(r4_0))
    local r3_5 = r1_5:read("*a")
    r1_5:close()
    if not _G.IsSimulator then
      os.remove(r0_5)
    end
    local r4_5 = r0_0.decode(r3_5)
    if r4_5.version ~= r5_0 then
      return false
    end
    r1_0.GameDataReplace(r4_5)
    r2_0.LeaderboardDataReplace(r4_5)
    return true
  end,
  SaveToServer = function(r0_6)
    -- line: [69, 80] id: 6
    if _G.SavingToServer then
      return 
    end
    _G.SavingToServer = true
    server.SaveData(r0_6, r1_0.gamedata_filename(), function(r0_7)
      -- line: [74, 79] id: 7
      if _G.IsSimulator then
        DebugPrint("save to server")
      end
      _G.SavingToServer = false
    end)
  end,
  SaveToServer2 = function(r0_8)
    -- line: [83, 105] id: 8
    if r0_8 == nil then
      return 
    end
    if _G.SavingToServer then
      return 
    end
    _G.SavingToServer = true
    server.SaveData2(r0_8, r4_0 .. r0_0.encode({
      version = r5_0,
      data = {
        GameData = r1_0.GetGameData(),
        leaderboard = r2_0.GetLeaderboardData(),
      },
    }), function(r0_9)
      -- line: [99, 104] id: 9
      if _G.IsSimulator then
        DebugPrint("save to server")
      end
      _G.SavingToServer = false
    end)
  end,
}
