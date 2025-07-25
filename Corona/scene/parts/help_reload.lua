-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("scene.help")
local r1_0 = nil
local r2_0 = nil
return {
  Complete = function()
    -- line: [10, 12] id: 1
    r0_0.ViewHelp(r1_0, r2_0)
  end,
  Load = function(r0_2, r1_2)
    -- line: [14, 22] id: 2
    local r2_2 = display.newGroup()
    r0_2:insert(r2_2)
    r1_0 = r1_2[1]
    r2_0 = r1_2[2]
    return r2_2
  end,
}
