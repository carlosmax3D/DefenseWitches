-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("logic.game.GameStatus")
local r1_0 = require("logic.char.CharStatus")
local r2_0 = require("logic.game.BaseGame")
local r3_0 = require("logic.cwave")
local r4_0 = require("game.pause_manager")
local r5_0 = require("tool.tutorial_manager")
local r6_0 = nil
local function r7_0()
  -- line: [11, 22] id: 1
  r0_0.PlayBtn[1].xScale = 1
  r0_0.PlayBtn[1].yScale = 1
  r0_0.PlayBtn[2].xScale = 1
  r0_0.PlayBtn[2].yScale = 1
  if r6_0 ~= nil then
    timer.cancel(r6_0)
  end
end
local function r8_0(r0_2, r1_2)
  -- line: [24, 71] id: 2
  if r0_0.GameOver then
    return true
  end
  if r0_0.GameClear then
    return true
  end
  if r1_2 == nil then
    r1_2 = true
  end
  r7_0()
  _G.IsPlayingGame = true
  if r0_2 == true then
    r4_0.Pause(r0_0.PauseTypePauseButton, {
      [r0_0.PauseFuncMoveEnemy] = false,
      [r0_0.PauseFuncMoveChars] = false,
      [r0_0.PauseFuncEnablePlayPauseButton] = true,
      [r0_0.PauseFuncShowGuideBlink] = false,
      [r0_0.PauseFuncShowTicket] = true,
      [r0_0.PauseFuncEnableUseOrbButton] = false,
      [r0_0.PauseFuncClosePulldownMenu] = true,
    })
  else
    r1_0.IsUseCrystal = false
    r4_0.Play({
      [r0_0.PauseFuncMoveEnemy] = true,
      [r0_0.PauseFuncMoveChars] = true,
      [r0_0.PauseFuncEnablePlayPauseButton] = false,
      [r0_0.PauseFuncShowGuideBlink] = false,
      [r0_0.PauseFuncShowTicket] = false,
      [r0_0.PauseFuncEnableUseOrbButton] = true,
      [r0_0.PauseFuncClosePulldownMenu] = true,
    })
    if r4_0.GetPauseType() == r0_0.PauseTypeFirstPause and r1_2 then
      r3_0.Start(_G.WaveNr, _G.WaveMax)
    end
    if char.GetUseOrbMode() ~= r1_0.UseOrbModeReset then
      char.SetUseOrbMode(r1_0.UseOrbModeReset, r1_0.UseOrbModePlayStatusPlay)
    end
  end
  r2_0.MakeGrid(r0_2)
end
local function r9_0(r0_3, r1_3)
  -- line: [73, 77] id: 3
  r8_0(r0_3, r1_3)
  char.ClearTwinsGuide()
  char.ClearAllCircle()
end
local function r10_0(r0_4)
  -- line: [80, 96] id: 4
  if r0_0.UseOrbBtn == nil then
    return 
  end
  r0_0.UseOrbBtn.disable = not r0_4
  if r0_4 == true then
    r0_0.UseOrbBtn.remainOrb.alpha = 1
    r0_0.UseOrbBtn.slash.alpha = 1
    r0_0.UseOrbBtn.maxOrb.alpha = 1
  else
    r0_0.UseOrbBtn.remainOrb.alpha = 0.7
    r0_0.UseOrbBtn.slash.alpha = 0.7
    r0_0.UseOrbBtn.maxOrb.alpha = 0.7
  end
end
local function r11_0(r0_5)
  -- line: [99, 139] id: 5
  if r0_5 == false then
    r5_0.GameStartTutorial(false)
    char.DeleteTapFieldTutrial()
    char.SetTapFieldTutrial()
    char.DeleteUpgradeCharTutrial()
    r5_0.OrbBtnTutorial(true, r0_0.UseOrbBtn)
    r0_0.orbBtnFlash.Play(r0_0.UseOrbBtn.image, r0_0.UseOrbBtn.fill)
    r2_0.setMpObject()
    anime.Pause(r0_0.orbButtonEffect, false)
  else
    if r5_0.FirstTapFieldFlag then
      char.DeleteTapFieldTutrial()
    end
    char.DeleteTapFieldTutrial()
    r5_0.OrbBtnTutorial(false)
    r0_0.orbBtnFlash.Stop()
    r2_0.setMpObject()
    anime.Pause(r0_0.orbButtonEffect, true)
  end
  r0_0.PlayBtn[1].isVisible = not r0_5
  r0_0.PlayBtn[2].isVisible = r0_5
end
return {
  pause_func = r9_0,
  pause_func_sub = r8_0,
  SetPlayBtnSize = r7_0,
  PlayOrPauseCtl = function(r0_6)
    -- line: [141, 210] id: 6
    local function r1_6(r0_7)
      -- line: [142, 142] id: 7
      return "data/map/interface/" .. r0_7 .. ".png"
    end
    local function r2_6(r0_8)
      -- line: [144, 144] id: 8
      return r1_6(r0_8 .. _G.UILanguage)
    end
    local function r4_6(r0_10)
      -- line: [165, 192] id: 10
      if r4_0.GetPauseType() ~= r0_0.PauseTypeFirstPause and (r4_0.GetPauseType() == r0_0.PauseTypeStopClockMenu or r4_0.GetPauseType() == r0_0.PauseTypeStopClock) then
        return true
      end
      sound.PlaySE(1)
      r11_0(false)
      r9_0(false)
      if r0_0.PauseMenu ~= nil then
        r0_0.PauseMenu.Close()
      end
      char.SetSystemPause(false)
      r2_0.PossessingCrystalVisible(false)
      if char.GetUseOrbMode() ~= r1_0.UseOrbModeReset then
        char.SetUseOrbMode(r1_0.UseOrbModeReset, r1_0.UseOrbModePlayStatusPlay)
      end
      return true
    end
    table.insert(r0_0.PlayBtn, util.LoadBtn({
      rtImg = r0_6,
      fname = r1_6("pause"),
      x = 5,
      y = 0,
      func = function(r0_9)
        -- line: [148, 163] id: 9
        if r4_0.GetPauseType() == r0_0.PauseTypeStopClockMenu or r4_0.GetPauseType() == r0_0.PauseTypeStopClock then
          return true
        end
        sound.PlaySE(1)
        r11_0(true)
        r9_0(true)
        r0_0.PauseMenu.Open()
        r2_0.setMpObject()
        char.SetSystemPause(true)
        return true
      end,
      show = false,
    }))
    table.insert(r0_0.PlayBtn, util.LoadBtn({
      rtImg = r0_6,
      fname = r1_6("play"),
      x = 5,
      y = 0,
      func = r4_6,
    }))
  end,
  enable_play_button = r11_0,
  init_pause_manager = function()
    -- line: [214, 312] id: 11
    local function r0_11(r0_12)
      -- line: [216, 256] id: 12
      local r1_12 = not r0_12
      if r0_0.EnemyRegister ~= nil then
        for r5_12, r6_12 in pairs(r0_0.EnemyRegister) do
          if r6_12.ev then
            events.Disable(r6_12.ev, r1_12)
          end
          if r6_12.pev then
            events.Disable(r6_12.pev, r1_12)
          end
        end
      end
      if _G.Enemys ~= nil then
        for r5_12, r6_12 in pairs(_G.Enemys) do
          events.Disable(r6_12.move, r1_12)
          if r6_12.anm and r6_12.anm.ev then
            events.Disable(r6_12.anm.ev, r1_12)
          end
          if r4_0.GetPauseType() == r0_0.PauseTypeStopClock then
            if r6_12.hit_anm and r6_12.hit_anm.ev then
              events.Disable(r6_12.hit_anm.ev, false)
            end
            if r6_12.burst_anm and r6_12.burst_anm.ev then
              events.Disable(r6_12.burst_anm.ev, false)
            end
          else
            if r6_12.hit_anm and r6_12.hit_anm.ev then
              events.Disable(r6_12.hit_anm.ev, r1_12)
            end
            if r6_12.burst_anm and r6_12.burst_anm.ev then
              events.Disable(r6_12.burst_anm.ev, r1_12)
            end
          end
        end
      end
    end
    local function r1_11(r0_13)
      -- line: [259, 261] id: 13
      char.PauseChars(r0_13)
    end
    local function r2_11(r0_14)
      -- line: [264, 266] id: 14
      r11_0(r0_14)
    end
    local function r3_11(r0_15)
      -- line: [269, 274] id: 15
      if r0_0.GuideBlink then
        events.Disable(r0_0.GuideBlink.ev, not r0_15)
        r0_0.GuideBlink.spr.isVisible = r0_15
      end
    end
    local function r4_11(r0_16)
      -- line: [277, 283] id: 16
      if r0_16 == true then
      end
    end
    local function r5_11(r0_17)
      -- line: [286, 288] id: 17
      r10_0(r0_17)
    end
    local function r6_11(r0_18)
      -- line: [291, 299] id: 18
    end
    r4_0.Init()
    r4_0.SetFunction(r0_0.PauseFuncMoveEnemy, r0_11)
    r4_0.SetFunction(r0_0.PauseFuncMoveChars, r1_11)
    r4_0.SetFunction(r0_0.PauseFuncEnablePlayPauseButton, r2_11)
    r4_0.SetFunction(r0_0.PauseFuncShowGuideBlink, r3_11)
    r4_0.SetFunction(r0_0.PauseFuncShowTicket, r4_11)
    r4_0.SetFunction(r0_0.PauseFuncEnableUseOrbButton, r5_11)
    r4_0.SetFunction(r0_0.PauseFuncClosePulldownMenu, r6_11)
  end,
  pause_only = function()
    -- line: [314, 325] id: 19
    r4_0.Pause(r0_0.PauseTypePauseOnly, {
      [r0_0.PauseFuncMoveEnemy] = false,
      [r0_0.PauseFuncMoveChars] = false,
      [r0_0.PauseFuncEnablePlayPauseButton] = true,
      [r0_0.PauseFuncShowGuideBlink] = false,
      [r0_0.PauseFuncShowTicket] = false,
      [r0_0.PauseFuncEnableUseOrbButton] = false,
      [r0_0.PauseFuncClosePulldownMenu] = false,
    })
  end,
}
