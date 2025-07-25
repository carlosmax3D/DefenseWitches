-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("logic.game.GameStatus")
local r1_0 = require("logic.game.BaseGame")
local r2_0 = require("logic.game.GameClear")
local r3_0 = require("logic.game.GameResume")
local r4_0 = require("logic.game.GamePause")
local r5_0 = require("game.pause_manager")
local r6_0 = require("logic.cwave")
local r7_0 = require("tool.trial")
local function r8_0(r0_1, r1_1, r2_1)
  -- line: [12, 24] id: 1
  enemy.PopEnemy(r1_1.type)
  r1_1.nr = r1_1.nr - 1
  if r1_1.nr <= 0 then
    assert(table.indexOf(r0_0.EnemyRegister, r1_1) ~= nil)
    table.remove(r0_0.EnemyRegister, r3_1)
    return false
  else
    return true
  end
end
local function r9_0(r0_2, r1_2, r2_2)
  -- line: [28, 35] id: 2
  if r8_0(nil, r1_2, 1) then
    r1_2.pev = events.Register(r8_0, r1_2, r1_2.interval, false)
  end
  r1_2.ev = nil
  return false
end
local function r10_0(r0_3)
  -- line: [38, 54] id: 3
  r0_0.WaveBonusMP = 0
  for r5_3, r6_3 in pairs(_G.EnemyPop[_G.WaveNr]) do
    local r7_3 = {
      ev = nil,
      pev = nil,
      nr = r6_3.nr,
      type = r6_3.enemy,
      interval = r6_3.interval,
    }
    r7_3.ev = events.Register(r9_0, r7_3, r6_3.wait, r0_3)
    table.insert(r0_0.EnemyRegister, r7_3)
    r0_0.WaveBonusMP = r0_0.WaveBonusMP + r6_3.bonus
  end
end
return {
  NextPopEnemy = function()
    -- line: [56, 134] id: 4
    if r0_0.GameOver then
      return 
    end
    if r5_0.GetPauseType() ~= r0_0.PauseTypeNone and r5_0.GetPauseType() ~= r0_0.PauseTypeStopClock then
      return 
    end
    local r0_4 = nil
    _G.MP = _G.MP + r0_0.WaveBonusMP
    _G.WaveNr = _G.WaveNr + 1
    if _G.MapSelect >= 3 then
      _G.EnemyScale = math.pow(_G.EnemyScaleBase, (_G.WaveNr - 1) / 1.3)
      r0_4 = math.pow(_G.EnemyScaleBase, 0.7692307692307692)
    else
      _G.EnemyScale = _G.EnemyScale * _G.EnemyScaleBase
      r0_4 = _G.EnemyScaleBase
    end
    if _G.IsQuickGameClear then
      _G.WaveMax = 1
    end
    if _G.WaveMax < _G.WaveNr then
      r1_0.make_end_game_resume_data()
      if r7_0.CheckTrialDisable() == false then
        r7_0.CountUpClearStage(_G.MapSelect, _G.StageSelect)
      end
      r0_0.UseOrbBtn.disable = true
      char.AllClear()
      r0_0.GameClear = true
      bgm.Stop()
      _G.WaveNr = _G.WaveMax
      r1_0.ViewPanel()
      r6_0.GameEnd(_G.CutinRoot, false, 4000, r0_0.PerfectFlag)
      r4_0.enable_play_button(true)
      r4_0.pause_only()
      preload.Cleanup()
      local r1_4 = r2_0.game_clear
      if r0_0.PerfectFlag then
        r1_4 = r2_0.view_perfect
      end
      timer.performWithDelay(1000, char.ClearVoice)
      if not _G.GameData.se then
        timer.performWithDelay(4000, r1_4)
      else
        timer.performWithDelay(500, function(r0_5)
          -- line: [102, 104] id: 5
          sound.PlaySound("jin03", r1_4, 4000)
        end)
      end
      -- close: r1_4
    else
      for r4_4, r5_4 in pairs(_G.Guardians) do
        r5_4.data.wave_upgrade(r5_4, r0_4)
      end
      r1_0.ViewPanel()
      r3_0.make_resume_data()
      enemy.InitEnemyUID()
      save.DataInit()
      save.DataPush("resume", {
        map = _G.MapSelect,
        stage = _G.StageSelect,
        wave = _G.WaveNr,
      })
      char.CheckNextTarget()
      local r1_4 = nil
      r10_0(r5_0.GetPauseType() == r0_0.PauseTypeStopClock)
      char.MPMonitoring()
      r6_0.Start(_G.WaveNr, _G.WaveMax)
    end
  end,
  pop_enemy_register = r10_0,
}
