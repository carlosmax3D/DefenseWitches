-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require
local r1_0 = pairs
local r2_0 = {}
for r25_0, r26_0 in r1_0({
  r0_0("logic.game.BaseGame"),
  r0_0("logic.game.GameClear"),
  r0_0("logic.game.GameClockStop"),
  r0_0("logic.game.GameOption"),
  r0_0("logic.game.GamePause"),
  r0_0("logic.game.GameResume"),
  r0_0("logic.game.GameRewind"),
  r0_0("logic.game.GameOrb"),
  r0_0("logic.game.GameOver"),
  r0_0("logic.game.GamePowerup"),
  r0_0("logic.game.GameRestart"),
  r0_0("logic.game.GameSpeed"),
  r0_0("logic.game.GameMap"),
  r0_0("logic.game.GamePannel"),
  r0_0("logic.game.GameHelp"),
  r0_0("logic.game.GameSidebar"),
  r0_0("logic.game.GameEnemy"),
  r0_0("logic.game.GameBom"),
  r0_0("logic.game.GameMedal"),
  nil
}) do
  local r27_0 = r1_0
  local r28_0 = r26_0
  for r30_0, r31_0 in r27_0(r28_0) do
    r2_0[r30_0] = r31_0
  end
end
return r2_0
