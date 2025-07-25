-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("logic.game.GameStatus")
local r1_0 = require("logic.char.CharStatus")
local r2_0 = require("logic.game.BaseGame")
local r3_0 = require("logic.game.GamePause")
local r4_0 = require("game.pause_manager")
local r5_0 = require("ui.rewind")
local function r6_0()
  -- line: [11, 15] id: 1
  if not _G.LoginGameCenter then
    return 
  end
  r2_0.CheckTotalAchievement(25, db.CountAchievement(_G.UserID, 25), true)
end
local function r7_0(r0_2)
  -- line: [18, 54] id: 2
  r6_0()
  save.DataClear()
  save.DataInit()
  save.DataPush("resume", {
    map = _G.MapSelect,
    stage = _G.StageSelect,
    wave = r0_2,
  })
  local r1_2 = 0
  for r5_2, r6_2 in pairs(_G.EnemyPop) do
    if r5_2 < r0_2 then
      for r10_2, r11_2 in pairs(r6_2) do
        r1_2 = r1_2 + r11_2.nr
      end
    end
  end
  enemy.PopEnemyNum = r1_2
  save.DataPush("start", {
    flag = 1,
    PopEnemyNum = enemy.PopEnemyNum,
    DropTreasureboxEnemy = enemy.DropTreasureboxEnemy,
  })
  save.DataSave()
  util.ChangeScene({
    prev = r2_0.Cleanup,
    param = {
      map = _G.MapSelect,
      stage = _G.StageSelect,
      wave = r0_2,
    },
    scene = "resume",
    efx = "fade",
  })
  return true
end
return {
  rewind_ok_func = r7_0,
  rewind_func = function(r0_3)
    -- line: [58, 107] id: 3
    if r0_0.GameOver then
      return true
    end
    if r0_0.GameClear then
      return true
    end
    if r4_0.GetPauseType() == r0_0.PauseTypeStopClockMenu or r4_0.GetPauseType() == r0_0.PauseTypeStopClock then
      return true
    end
    if r1_0.SummonPlateGroup then
      return true
    end
    local function r1_3(r0_4)
      -- line: [67, 74] id: 4
      sound.PlaySE(2)
      dialog.Close()
      save.DataClear()
      util.ChangeScene({
        prev = r2_0.Cleanup,
        scene = "restart",
      })
      return true
    end
    local function r2_3(r0_5)
      -- line: [76, 80] id: 5
      sound.PlaySE(2)
      dialog.Close()
      return true
    end
    sound.PlaySE(2)
    if char.GetUseOrbMode() ~= r1_0.UseOrbModeReset then
      char.SetUseOrbMode(r1_0.UseOrbModeReset, r1_0.UseOrbModePlayStatusPlay)
    end
    r2_0.PossessingCrystalVisible(false)
    if r4_0.GetPauseType() == r0_0.PauseTypeNone then
      r3_0.pause_func(true)
      char.SetSystemPause(true)
    end
    char.ClearAllCircle()
    char.ClearSummonGroup()
    if _G.WaveNr == 1 then
      dialog.Open(_G.DialogRoot, 4, {
        9
      }, {
        r1_3,
        r2_3
      })
    else
      r5_0.Open(_G.DialogRoot, {
        r7_0
      })
    end
  end,
}
