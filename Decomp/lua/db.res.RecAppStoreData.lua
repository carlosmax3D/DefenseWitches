-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("sqlite3")
local r1_0 = "data/app.sqlite"
local function r2_0()
  -- line: [7, 9] id: 1
  return system.pathForFile(r1_0, system.ResourceDirectory)
end
return {
  LoadAppData = function()
    -- line: [11, 21] id: 2
    local r1_2 = r0_0.open(r2_0())
    local r3_2 = {}
    for r7_2 in r1_2:nrows("SELECT domain, jpy, crystal FROM item WHERE flag=0") do
      table.insert(r3_2, {
        domain = r7_2.domain,
        jpy = r7_2.jpy,
        crystal = r7_2.crystal,
      })
    end
    r1_2:close()
    return r3_2
  end,
}
