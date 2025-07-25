-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("sqlite3")
local r1_0 = require("json")
local r2_0 = require("crypto")
local r3_0 = require("db.BaseDB")
local r4_0 = require("db.doc.GameData")
local r5_0 = "data/ads.sqlite"
return {
  LoadAdsProbabilityTable = function()
    -- line: [11, 32] id: 1
    local r0_1 = r4_0.LoadSummonNoSaleIds(_G.UserID)
    local r2_1 = r0_0.open(system.pathForFile(r5_0, system.ResourceDirectory))
    local r3_1 = r2_1:prepare("SELECT type, tid, version, weight FROM probability" .. " WHERE language=:language AND flag=0")
    r3_1:bind_names({
      language = _G.UILanguage,
    })
    local r4_1 = {}
    for r8_1 in r3_1:nrows() do
      if (r8_1.type ~= "character" or r3_0.contains(r0_1, r8_1.tid) ~= false) and r8_1.weight > 0 then
        r4_1[#r4_1 + 1] = {
          type = r8_1.type,
          tid = r8_1.tid,
          version = r8_1.version,
          weight = r8_1.weight,
        }
      end
    end
    r3_1:finalize()
    r2_1:close()
    return r4_1
  end,
}
