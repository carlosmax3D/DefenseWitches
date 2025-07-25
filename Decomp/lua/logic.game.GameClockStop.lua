-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("logic.game.GameStatus")
local r1_0 = require("logic.char.CharStatus")
local r2_0 = require("logic.game.BaseGame")
local r3_0 = require("resource.char_define")
local r4_0 = require("game.pause_manager")
local function r5_0(r0_1, r1_1)
  -- line: [9, 99] id: 1
  local r4_1 = anime.Register(require("char.c12.afx12_buff03").GetData(), r1_0.KalaPos[1], r1_0.KalaPos[2], "data/game/sarah")
  _G.BuffRoot:insert(anime.GetSprite(r4_1))
  anime.Show(r4_1, true)
  anime.Loop(r4_1, true)
  transition.to(r0_0.bomback, {
    delay = 200,
    time = 700,
    alpha = 0,
    onComplete = function()
      -- line: [26, 29] id: 2
      display.remove(r0_0.bomback)
    end,
  })
  transition.to(r0_0.SpCharSpr, {
    delay = 200,
    time = 700,
    alpha = 0,
    onComplete = function()
      -- line: [36, 97] id: 3
      local r1_3 = nil	-- notice: implicit variable refs by block#[0]
      r1_0.SummonChar[r3_0.CharId.Kala].Reload(r1_0.KalaStruct, 3)
      display.remove(r0_0.SpCharSpr)
      anime.Remove(r0_1)
      r4_0.Pause(r0_0.PauseTypeStopClock, {
        [r0_0.PauseFuncMoveEnemy] = false,
        [r0_0.PauseFuncMoveChars] = true,
        [r0_0.PauseFuncEnablePlayPauseButton] = false,
        [r0_0.PauseFuncShowGuideBlink] = false,
        [r0_0.PauseFuncShowTicket] = false,
        [r0_0.PauseFuncEnableUseOrbButton] = false,
        [r0_0.PauseFuncClosePulldownMenu] = false,
      })
      function r1_3()
        -- line: [52, 93] id: 4
        r0_0.stopclock_frame_count = r0_0.stopclock_frame_count + 1
        if r0_0.stopclock_frame_count > 360 then
          char.SetSystemPause(false)
          r4_0.Play({
            [r0_0.PauseFuncMoveEnemy] = true,
            [r0_0.PauseFuncMoveChars] = true,
            [r0_0.PauseFuncEnablePlayPauseButton] = false,
            [r0_0.PauseFuncShowGuideBlink] = false,
            [r0_0.PauseFuncShowTicket] = false,
            [r0_0.PauseFuncEnableUseOrbButton] = true,
            [r0_0.PauseFuncClosePulldownMenu] = true,
          })
          anime.Remove(r4_1)
          if r0_0.PanelTrantision then
            transition.cancel(r0_0.PanelTrantision)
          end
          bgm.Play(2)
          _G.SpeedType = r1_1.time
          events.SetRepeatTime(_G.SpeedType)
          r0_0.PanelTrantision = transition.to(_G.PanelRoot, {
            delay = 400,
            time = 500,
            y = 0,
            transition = easing.outExpo,
            onComplete = function()
              -- line: [86, 88] id: 5
              r0_0.PanelTrantision = nil
            end,
          })
          r0_0.PauseQueue = nil
          Runtime:removeEventListener("enterFrame", r1_3)
        end
      end
      r0_0.stopclock_frame_count = 0
      Runtime:addEventListener("enterFrame", r1_3)
    end,
  })
end
local function r6_0()
  -- line: [102, 111] id: 6
  if not _G.GameData.voice then
    return 
  end
  local r2_6 = sound.GetCharVoiceFilename(sound.GetCharBomVoicePath(17, _G.GameData.language), math.random(1, 3))
  sound.StopVoice(29)
  sound.PlayVoice(r2_6, 29)
end
local r7_0 = nil
local function r8_0()
  -- line: [115, 175] id: 7
  if r0_0.GameOver then
    return true
  end
  if r0_0.GameClear then
    return true
  end
  if r7_0 == nil then
    r7_0 = true
  end
  if r4_0.GetPauseType() == r0_0.PauseTypeStopClock then
    return true
  end
  if r4_0.GetPauseType() == r0_0.PauseTypeFirstPause then
    return true
  end
  if r1_0.KalaStruct == nil then
    return true
  end
  char.AllClear()
  r2_0.set_score_type(r0_0.ScoreTypeDef.Standard)
  local r0_7 = events.GetRepeatTime()
  _G.SpeedType = 1
  events.SetRepeatTime(_G.SpeedType)
  r0_0.bomback = display.newRect(_G.CutinRoot, 0, 0, _G.Width, _G.Height)
  r0_0.bomback:setFillColor(0, 0, 0)
  r0_0.bomback.alpha = 0
  transition.to(r0_0.bomback, {
    time = 300,
    alpha = 0.7,
  })
  local r1_7 = anime.RegisterWithInterval(r0_0.afx17_stop_anm.GetData(), 400, 330, "data/game/kala", 100)
  anime.RegisterFinish(r1_7, r5_0, {
    anime = r1_7,
    time = r0_7,
  })
  _G.CutinRoot:insert(anime.GetSprite(r1_7))
  timer.performWithDelay(33, function()
    -- line: [151, 153] id: 8
    anime.Show(r1_7, true)
  end)
  local r4_7 = "data/sound/17/bom/reverseTime.aac"
  sound.StopVoice(30)
  if _G.GameData.voice then
    sound.PlayVoice(r4_7, 30)
  end
  local r5_7 = 17
  r0_0.SpCharSpr = util.LoadTileParts(_G.CutinRoot, -896, 0, db.LoadTileData(string.format("cutin%02d", r5_7), "chara"), string.format("data/cutin/%02d", r5_7))
  timer.performWithDelay(1000, function()
    -- line: [170, 174] id: 9
    r6_0()
    transition.to(r0_0.SpCharSpr, {
      time = 400,
      x = 300,
      transition = easing.outQuad,
    })
  end)
end
return {
  StopClock = function()
    -- line: [177, 236] id: 10
    r4_0.Pause(r0_0.PauseTypeStopClockMenu, {
      [r0_0.PauseFuncMoveEnemy] = false,
      [r0_0.PauseFuncMoveChars] = false,
      [r0_0.PauseFuncEnablePlayPauseButton] = true,
      [r0_0.PauseFuncShowGuideBlink] = false,
      [r0_0.PauseFuncShowTicket] = false,
      [r0_0.PauseFuncEnableUseOrbButton] = false,
      [r0_0.PauseFuncClosePulldownMenu] = false,
    })
    char.SetSystemPause(true)
    local r1_10 = string.format(db.GetMessage(14), 200)
    local r2_10 = display.newGroup()
    require("ui.stop_clock").Open(_G.DialogRoot, {
      function(r0_11)
        -- line: [198, 206] id: 11
        r0_0.PanelTrantision = transition.to(_G.PanelRoot, {
          time = 500,
          y = _G.HeightDiff - _G.PanelRoot.height,
          transition = easing.outExpo,
        })
        dialog.Close()
        r8_0()
        return true
      end,
      function(r0_12)
        -- line: [208, 229] id: 12
        r4_0.Play({
          [r0_0.PauseFuncMoveEnemy] = true,
          [r0_0.PauseFuncMoveChars] = true,
          [r0_0.PauseFuncEnablePlayPauseButton] = false,
          [r0_0.PauseFuncShowGuideBlink] = false,
          [r0_0.PauseFuncShowTicket] = false,
          [r0_0.PauseFuncEnableUseOrbButton] = true,
          [r0_0.PauseFuncClosePulldownMenu] = true,
        })
        sound.PlaySE(2)
        dialog.Close()
        char.SetSystemPause(false)
        return true
      end
    })
    return true
  end,
}
