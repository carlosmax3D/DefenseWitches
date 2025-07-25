-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("logic.game.GameStatus")
local r1_0 = require("logic.game.BaseGame")
local r2_0 = require("logic.game.GameOver")
local r3_0 = require("logic.game.GamePause")
local r4_0 = require("logic.game.GameEnemy")
local r5_0 = require("ui.bom")
local r6_0 = require("tool.trial")
local r7_0 = require("logic.cwave")
local r8_0 = require("game.pause_manager")
local r9_0 = nil
local function r10_0()
  -- line: [15, 22] id: 1
  if not _G.GameData.voice then
    return 
  end
  local r2_1 = sound.GetCharVoiceFilename(sound.GetCharBomVoicePath(16, _G.GameData.language), math.random(1, 5))
  sound.StopVoice(30)
  sound.PlayVoice(r2_1, 30)
end
local function r11_0()
  -- line: [24, 45] id: 2
  events.DeletePostProc()
  if #_G.Enemys > 0 then
    for r3_2, r4_2 in pairs(_G.Enemys) do
      r4_2.isBom = true
      enemy.HitObject(r4_2, 10000000)
    end
  end
  if r8_0.GetPauseType() ~= r0_0.PauseTypeNone and r8_0.GetPauseType() ~= r0_0.PauseTypeStopClock and r8_0.GetPauseType() ~= r0_0.PauseTypeStopClockMenu then
    r3_0.pause_func(false)
  end
  if #_G.Enemys <= 0 and #r0_0.EnemyRegister <= 0 and not _G.PopEnemyShield then
    r4_0.NextPopEnemy()
  end
end
local function r12_0(r0_3)
  -- line: [48, 148] id: 3
  local r1_3 = display.newGroup()
  function r1_3.touch(r0_4, r1_4)
    -- line: [51, 53] id: 4
    return true
  end
  display.newRect(r1_3, -176, 0, display.contentWidth + 176, display.contentHeight):setFillColor(0, 0, 0, 0)
  r1_3:addEventListener("touch", r1_3)
  local function r2_3()
    -- line: [58, 137] id: 5
    timer.performWithDelay(500, function()
      -- line: [60, 66] id: 6
      r0_0.bomback = display.newRect(_G.CutinRoot, 0, 0, _G.Width, _G.Height)
      r0_0.bomback:setFillColor(255, 0, 0)
      r0_0.bomback.alpha = 0
      transition.to(r0_0.bomback, {
        time = 300,
        alpha = 0.2,
      })
    end)
    local r0_5 = events.GetRepeatTime()
    events.SetRepeatTime(1)
    timer.performWithDelay(2000, function()
      -- line: [72, 98] id: 7
      display.remove(r0_0.bomback)
      for r3_7 = 1, 18, 1 do
        timer.performWithDelay(100 + r3_7 * 60, function()
          -- line: [77, 96] id: 8
          local r3_8 = anime.Register(enemy.GetBomEfx().GetData(), math.random(800), math.random(600), "data/game")
          anime.RegisterShowHook(r3_8, function(r0_9, r1_9, r2_9, r3_9)
            -- line: [84, 87] id: 9
            r0_9.xScale = r0_9.xScale
            r0_9.yScale = r0_9.yScale
          end, nil)
          anime.RegisterFinish(r3_8, function(r0_10, r1_10)
            -- line: [89, 91] id: 10
            anime.Remove(r0_10)
          end, my)
          _G.SFrontRoot:insert(anime.GetSprite(r3_8))
          anime.Pause(r3_8, false)
          anime.Show(r3_8, true)
        end)
      end
    end)
    timer.performWithDelay(4000, function()
      -- line: [99, 136] id: 11
      r11_0()
      transition.to(r0_0.SpCharSpr, {
        time = 700,
        alpha = 0,
        onComplete = function()
          -- line: [105, 107] id: 12
          display.remove(r0_0.SpCharSpr)
        end,
      })
      if r0_0.PanelTrantision then
        transition.cancel(r0_0.PanelTrantision)
      end
      bgm.Play(2)
      r0_0.PanelTrantision = transition.to(_G.PanelRoot, {
        delay = 700,
        time = 500,
        y = 0,
        transition = easing.outExpo,
        onComplete = function()
          -- line: [120, 123] id: 13
          r0_0.PanelTrantision = nil
          r0_0.isUseBom = false
        end,
      })
      _G.SpeedType = r0_5
      events.SetRepeatTime(_G.SpeedType)
      r1_3:removeEventListener("touch")
      display.remove(r1_3)
      if r0_3 then
        display.remove(r0_3)
        r0_3 = nil
      end
    end)
  end
  r10_0()
  local r3_3 = 16
  r0_0.SpCharSpr = util.LoadTileParts(_G.CutinRoot, -896, 0, db.LoadTileData(string.format("cutin%02d", r3_3), "chara"), string.format("data/cutin/%02d", r3_3))
  transition.to(r0_0.SpCharSpr, {
    time = 575,
    x = 350,
    transition = easing.outQuad,
    onComplete = r2_3,
  })
end
local function r13_0()
  -- line: [150, 199] id: 14
  local r1_14 = nil	-- notice: implicit variable refs by block#[2]
  if r6_0.CheckTrialDisable() == false then
    r6_0.CountUpGameOver(_G.MapSelect, _G.StageSelect, true)
  end
  bgm.Stop()
  sound.PlaySE(10, 22, true)
  r1_0.set_hp(0)
  r1_0.ViewPanel()
  anime.Pause(r0_0.GoalSprite[4], true)
  r3_0.enable_play_button(true)
  r3_0.pause_only()
  preload.Cleanup()
  local r0_14 = nil
  function r1_14(r0_15)
    -- line: [167, 178] id: 15
    Runtime:removeEventListener("touch", r1_14)
    if r0_14 then
      sound.cancelCallbackAtPlaySound()
      timer.cancel(r0_14)
      r0_14 = nil
      r7_0.removeGameOver()
      r2_0.game_over()
    end
  end
  local function r2_14()
    -- line: [181, 184] id: 16
    Runtime:removeEventListener("touch", r1_14)
    r2_0.game_over()
  end
  Runtime:addEventListener("touch", r1_14)
  r7_0.GameEnd(_G.CutinRoot, true, 6000)
  if r6_0.CheckTrialDisable() == false then
    r6_0.GetGameOverCount(_G.MapSelect, _G.StageSelect)
  end
  if not _G.GameData.se then
    r0_14 = timer.performWithDelay(6000, r2_14)
  else
    r0_14 = timer.performWithDelay(500, function(r0_17)
      -- line: [195, 197] id: 17
      sound.PlaySound("jin04", r2_14, 7000)
    end)
  end
end
local function r14_0(r0_18)
  -- line: [201, 219] id: 18
  r0_0.GameOver = false
  _G.IsPlayingGame = true
  r1_0.set_hp(1)
  sound.PlaySE(2)
  dialog.Close()
  r12_0(util.MakeMat(display.newGroup()))
  if r0_0.MedalObject.MedalDisplay ~= nil and r1_0.get_hp() == 1 then
    r0_0.MedalObject.MedalDisplay.EnableMedal(r0_0.MedalClass.TestMedalDisplayManager.MedalTestIndex.Hp1, true)
  end
end
return {
  ShowBom = function()
    -- line: [221, 226] id: 19
    r0_0.isUseBom = true
    r5_0.Close()
    r5_0.Open(_G.DialogRoot, {
      r14_0,
      r13_0
    })
  end,
}
