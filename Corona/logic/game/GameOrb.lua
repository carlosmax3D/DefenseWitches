-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("logic.game.GameStatus")
local r1_0 = require("logic.char.CharStatus")
local r2_0 = require("logic.game.BaseGame")
local r3_0 = require("tool.tutorial_manager")
return {
  on_orb_button_func = function(r0_1, r1_1)
    -- line: [8, 23] id: 1
    char.ClearAllCircle()
    r3_0.OrbBtnTutorial(false)
    r3_0.FirstOrbFlag = true
    if char.GetUseOrbMode() == r1_0.UseOrbModeReset then
      char.SetUseOrbMode(r1_0.UseOrbModeCharSelect, r1_0.UseOrbModePlayStatusPause)
    else
      char.SetUseOrbMode(r1_0.UseOrbModeReset, r1_0.UseOrbModePlayStatusPlay)
    end
  end,
  recovery_orb = function()
    -- line: [26, 32] id: 2
    OrbManager.GetRecoveryOrb(function(r0_3, r1_3)
      -- line: [28, 31] id: 3
      r2_0.ViewPanel()
    end)
  end,
}
