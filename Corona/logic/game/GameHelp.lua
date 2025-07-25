-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("logic.game.GameStatus")
local r1_0 = require("logic.char.CharStatus")
local r2_0 = require("logic.game.BaseGame")
local r3_0 = require("stage_ui.stage_help_manager")
local function r4_0(r0_1)
  -- line: [8, 12] id: 1
  local r1_1 = require("stage_ui.stage_help")
  r1_1.Init()
  r1_1.Open(r0_1)
end
return {
  show_help = r4_0,
  first_help = function()
    -- line: [15, 33] id: 2
    r3_0.Init()
    if r3_0.IsFirstRun() == true and _G.ServerStatus.run2firsthelp == 1 then
      r3_0.SetFirstRun()
      r4_0(1)
    elseif r3_0.IsFirstV400Run() == true and _G.ServerStatus.orbhelp == 1 then
      r3_0.SetFirstV400Run()
      r4_0(3)
    end
  end,
}
