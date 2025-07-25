-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("sqlite3")
local r1_0 = require("json")
local r2_0 = require("crypto")
local r3_0 = require("db.BaseDB")
local r4_0 = "app.sqlite"
local function r5_0()
  -- line: [12, 14] id: 1
  return system.pathForFile(r4_0, system.DocumentsDirectory)
end
return {
  InitStore = function()
    -- line: [16, 36] id: 2
    local r1_2 = r0_0.open(r5_0())
    local r2_2 = nil
    r3_0.begin_transcation(r1_2)
    r1_2:exec("CREATE TABLE IF NOT EXISTS item (" .. "domain TEXT PRIMARY KEY," .. "price TEXT," .. "flag BOOL DEFAULT 0)")
    r3_0.commit(r1_2)
    r1_2:close()
    if not _G.IsSimulator then
      native.setSync(r4_0, {
        iCloudBackup = false,
      })
    end
  end,
  RestoreStoreData = function(r0_3)
    -- line: [38, 57] id: 3
    local r2_3 = r0_0.open(r5_0())
    r3_0.begin_transcation(r2_3)
    local r3_3 = r2_3:prepare("REPLACE INTO item (domain, price, flag)" .. " VALUES (:domain, :price, 0)")
    assert(r3_3, r2_3:errmsg())
    for r7_3, r8_3 in pairs(r0_3) do
      r3_3:reset()
      r3_3:bind_names({
        domain = r8_3.domain,
        price = r8_3.localizedPrice,
      })
      r3_3:step()
    end
    r3_3:finalize()
    r3_0.commit(r2_3)
    r2_3:close()
  end,
  GetStoreLocalizedPrice = function(r0_4)
    -- line: [59, 75] id: 4
    local r2_4 = r0_0.open(r5_0())
    local r3_4 = r2_4:prepare("SELECT price FROM item WHERE domain=:domain")
    assert(r3_4, r2_4:errmsg())
    r3_4:bind_names({
      domain = r0_4,
    })
    local r4_4 = nil
    for r8_4 in r3_4:nrows() do
      r4_4 = r8_4.price
    end
    r3_4:finalize()
    r2_4:close()
    return r4_4
  end,
}
