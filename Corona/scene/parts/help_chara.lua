-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("scene.help")
local r1_0 = nil
local r2_0 = nil
local r3_0 = false
local function r4_0(r0_1)
  -- line: [12, 20] id: 1
  sound.PlaySE(1)
  if r3_0 == true then
    display.remove(r2_0)
  else
    r0_0.ViewHelp("witches", r1_0)
  end
  return true
end
local r5_0 = nil
return {
  Load = function(r0_2, r1_2, r2_2)
    -- line: [23, 43] id: 2
    if r2_2 then
      r3_0 = r2_2
    else
      r3_0 = false
    end
    r2_0 = r0_2
    local r3_2 = display.newGroup()
    local r4_2 = "scene.cutin"
    if package.loaded[r4_2] == nil then
      r5_0 = require(r4_2)
    end
    r1_0 = r1_2
    util.MakeMat(r3_2)
    r5_0.View(r3_2, r1_2, r4_0)
    r0_2:insert(r3_2)
    return r3_2
  end,
  Cleanup = function()
    -- line: [45, 51] id: 3
    local r0_3 = "scene.cutin"
    if package.loaded[r0_3] then
      r5_0.ViewCleanup()
      package.loaded[r0_3] = nil
    end
  end,
}
