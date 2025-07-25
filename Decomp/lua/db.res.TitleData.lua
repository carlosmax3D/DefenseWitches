-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("sqlite3")
local r1_0 = "data/tile.sqlite"
local function r2_0()
  -- line: [6, 8] id: 1
  return system.pathForFile(r1_0, system.ResourceDirectory)
end
return {
  LoadTileData = function(r0_2, r1_2)
    -- line: [10, 46] id: 2
    local r3_2 = r0_0.open(r2_0())
    local r4_2 = r3_2:prepare("SELECT uid, width, height, fmt, nums FROM header" .. " WHERE ident=? AND name=? LIMIT 1")
    r4_2:bind_values(r0_2, r1_2)
    local r5_2 = {}
    local r6_2 = nil
    table.insert(r5_2, r1_2)
    for r10_2 in r4_2:nrows() do
      table.insert(r5_2, r10_2.width)
      table.insert(r5_2, r10_2.height)
      table.insert(r5_2, r10_2.fmt)
      table.insert(r5_2, r10_2.nums)
      r6_2 = r10_2.uid
    end
    r4_2:finalize()
    if r6_2 then
      r4_2 = r3_2:prepare("SELECT x, y FROM layout" .. " WHERE uid=? ORDER BY oid")
      r4_2:bind_values(r6_2)
      local r7_2 = {}
      for r11_2 in r4_2:nrows() do
        table.insert(r7_2, {
          r11_2.x,
          r11_2.y
        })
      end
      r4_2:finalize()
      table.insert(r5_2, r7_2)
    else
      r5_2 = nil
    end
    r3_2:close()
    return r5_2
  end,
}
