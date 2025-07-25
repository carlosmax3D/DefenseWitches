-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("sqlite3")
local r1_0 = require("json")
local r2_0 = require("crypto")
local r3_0 = require("db.BaseDB")
local function r4_0()
  -- line: [7, 9] id: 1
  return system.pathForFile("data/gc.sqlite", system.ResourceDirectory)
end
return {
  GetAchievement = function(r0_2)
    -- line: [12, 28] id: 2
    local r2_2 = r0_0.open(r4_0())
    local r3_2 = r2_2:prepare("SELECT identifier FROM achievement" .. " WHERE category=? AND flag=?")
    r3_2:bind_values(r0_2, 0)
    local r4_2 = nil
    for r8_2 in r3_2:nrows() do
      r4_2 = r8_2.identifier
    end
    r3_2:finalize()
    r2_2:close()
    return r4_2
  end,
  GetLeaderboard = function(r0_3, r1_3)
    -- line: [30, 46] id: 3
    local r3_3 = r0_0.open(r4_0())
    local r4_3 = r3_3:prepare("SELECT identifier FROM leaderboard" .. " WHERE category=? AND type=?")
    r4_3:bind_values(r0_3, r1_3)
    local r5_3 = nil
    for r9_3 in r4_3:nrows() do
      r5_3 = r9_3.identifier
    end
    r4_3:finalize()
    r3_3:close()
    return r5_3
  end,
  GetAchievementIDs = function(r0_4)
    -- line: [49, 68] id: 4
    local r2_4 = r0_0.open(r4_0())
    local r3_4 = nil
    local r4_4 = nil
    local r5_4 = nil
    r5_4 = {}
    for r9_4, r10_4 in pairs(r0_4) do
      r3_4 = r2_4:prepare("SELECT category FROM achievement" .. " WHERE identifier=? AND flag=?")
      r3_4:bind_values(r10_4, 0)
      r4_4 = nil
      for r14_4 in r3_4:nrows() do
        r4_4 = r14_4.category
      end
      r3_4:finalize()
      if r4_4 then
        r5_4[r4_4] = r10_4
      end
    end
    r2_4:close()
    return r5_4
  end,
}
