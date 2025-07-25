-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("logic.game.GameStatus")
local r1_0 = require("logic.char.CharStatus")
local r2_0 = require("logic.game.BaseGame")
local r3_0 = require("logic.game.GameSpeed")
local r4_0 = require("logic.game.GamePause")
local r5_0 = require("logic.game.GameOrb")
local r6_0 = require("game.pause_manager")
local function r7_0()
  -- line: [11, 16] id: 1
  if r0_0.OrbButtonEffect ~= nil then
    anime.Remove(r0_0.OrbButtonEffect)
    r0_0.OrbButtonEffect = nil
  end
end
local function r8_0()
  -- line: [21, 23] id: 2
  r7_0()
end
local function r9_0()
  -- line: [26, 29] id: 3
  r8_0()
end
return {
  loadpanel = function(r0_4)
    -- line: [31, 213] id: 4
    local function r1_4(r0_5)
      -- line: [32, 32] id: 5
      return "data/map/interface/" .. r0_5 .. ".png"
    end
    local function r2_4(r0_6)
      -- line: [34, 34] id: 6
      return r1_4(r0_6 .. _G.UILanguage)
    end
    local function r3_4(r0_7)
      -- line: [36, 36] id: 7
      return "data/evo/evo_select_mode/" .. r0_7 .. ".png"
    end
    local function r4_4(r0_8)
      -- line: [38, 38] id: 8
      return r3_4(r0_8 .. _G.UILanguage)
    end
    local function r5_4(r0_9, r1_9, r2_9, r3_9)
      -- line: [42, 44] id: 9
      return util.LoadParts(r0_9, r1_4(r1_9), r2_9, r3_9)
    end
    local function r6_4(r0_10, r1_10, r2_10, r3_10)
      -- line: [47, 70] id: 10
      local r4_10 = display.newGroup()
      local r5_10 = true
      local r6_10 = nil
      for r10_10 = 0, 9, 1 do
        display.newImage(r4_10, r1_4(string.format("score_number%02d", r10_10)), 0, 0, true).isVisible = r5_10
        r5_10 = false
      end
      if r3_10 ~= nil and r3_10 then
        display.newImage(r4_10, r1_4("score_hyphen"), 0, 0, true).isVisible = r5_10
        r5_10 = false
      end
      r4_10:setReferencePoint(display.TopLeftReferencePoint)
      r4_10.x = r1_10
      r4_10.y = r2_10
      r0_10:insert(r4_10)
      return r4_10
    end
    display.newImage(r0_4, r1_4("game_system_bg"), 0, 0, true)
    r0_0.PlayBtn = {}
    r0_0.SpeedBtn = {}
    r0_0.PauseMenu = require("ui.pause_menu").new({
      playButton = r0_0.PlayBtn,
      speedButton = r0_0.SpeedBtn,
      backToParentGroup = r0_4,
    })
    r4_0.PlayOrPauseCtl(r0_4)
    r3_0.SpeedCtl(r0_4)
    r0_0.UseOrbBtn = display.newGroup()
    r0_0.UseOrbBtn.image = display.newImage(r0_0.UseOrbBtn, r2_4("stage_orb_"), 0, 0, true)
    r0_0.UseOrbBtn:setReferencePoint(display.TopLeftReferencePoint)
    r0_0.UseOrbBtn = util.LoadBtnGroup({
      group = r0_0.UseOrbBtn,
      cx = 722,
      cy = -28,
      func = r5_0.on_orb_button_func,
      show = true,
      disable = false,
    })
    r0_0.OrbRemain = display.newGroup()
    r0_0.UseOrbBtn.remainOrb = r0_0.SpriteNumber01.CreateNumbers(r0_0.OrbRemain, r0_0.SpriteNumber01.sequenceNames.Score, 3, 78, 70, -2)
    r0_0.UseOrbBtn.slash = r0_0.SpriteNumber01.CreateImage(r0_0.UseOrbBtn, r0_0.SpriteNumber01.sequenceNames.Score, r0_0.SpriteNumber01.frameDefines.ScoreSlash, 118, 70)
    r0_0.OrbMax = display.newGroup()
    r0_0.UseOrbBtn.maxOrb = r0_0.SpriteNumber01.CreateNumbers(r0_0.OrbMax, r0_0.SpriteNumber01.sequenceNames.Score, 3, 133, 70, -2)
    r0_0.UseOrbBtn:insert(r0_0.OrbRemain)
    r0_0.UseOrbBtn:insert(r0_0.OrbMax)
    r0_0.UseOrbBtn:setReferencePoint(display.CenterReferencePoint)
    r0_4:insert(r0_0.UseOrbBtn)
    char.SetUseOrbCharSelectModeCancelRect({
      x = r0_0.UseOrbBtn.x - r0_0.UseOrbBtn.width * 0.5,
      y = r0_0.UseOrbBtn.y - r0_0.UseOrbBtn.height * 0.5,
      width = r0_0.UseOrbBtn.width,
      height = r0_0.UseOrbBtn.height,
    })
    r0_0.UseOrbBtn.fill = display.newRoundedRect(722, -60, 310, 134, 65)
    r0_0.UseOrbBtn.fill.alpha = 0.1
    r0_0.orbBtnFlash = require("ui.flashing").new({
      speed = 0.1,
    })
    r0_0.orbBtnFlash.Play(r0_0.UseOrbBtn.image, r0_0.UseOrbBtn.fill)
    if _G.GuideSpeedButton then
      r0_0.SpeedEffectId = timer.performWithDelay(r0_0.SpeedButtonEffectDelayTime, function()
        -- line: [123, 130] id: 11
        r0_0.SpeedButtonEffect = anime.RegisterWithInterval(r0_0.stage_button_eff.GetData(), r3_0.speedBtnX + 38, r3_0.speedBtnY + 35, "data/map/interface", 60)
        anime.Show(r0_0.SpeedButtonEffect, true)
        anime.Loop(r0_0.SpeedButtonEffect, true)
        r0_4:insert(anime.GetSprite(r0_0.SpeedButtonEffect))
      end)
    end
    r0_0.orbButtonEffect = anime.RegisterWithInterval(require("efx.fullpower_eff").GetData(), r0_0.UseOrbBtn.x, r0_0.UseOrbBtn.y, "data/map/interface/sprite", 100)
    anime.Show(r0_0.orbButtonEffect, true)
    anime.Loop(r0_0.orbButtonEffect, true)
    if _G.GuideOrbButton then
      r9_0()
      r0_0.OrbButtonEffect = anime.RegisterWithInterval(require("efx.fullpower_eff").GetData(), r0_0.UseOrbBtn.x, r0_0.UseOrbBtn.y, "data/map/interface/sprite", 100)
      anime.Show(r0_0.orbButtonEffect, true)
      anime.Loop(r0_0.orbButtonEffect, true)
      r0_4:insert(anime.GetSprite(r0_0.OrbButtonEffect))
    end
    local r9_4 = display.newGroup()
    r5_4(r0_4, "score_score", 160, 5)
    r5_4(r0_4, "score_wave", 160, 30)
    r0_0.mpObject = r5_4(r0_4, "score_magicpoint", 380, 10)
    r5_4(r0_4, "text_mp", 436, 16)
    r5_4(r0_4, "stage_icon_treasurebox_r2", 648, 10)
    r5_4(r0_4, "stage_icon_treasurebox_r1", 648, 36)
    r0_0.mpObject:setReferencePoint(display.CenterReferencePoint)
    r0_0.mpScale = require("tool.scaling").new({
      scale = 0.03,
      maxScale = 1.4,
      minScale = 1,
    })
    r2_0.setMpObject()
    r0_0.ScoreNum = display.newGroup()
    r0_0.SpriteNumber01.CreateNumbers(r0_0.ScoreNum, r0_0.SpriteNumber01.sequenceNames.Score, 12, 225, 11, -2)
    r0_0.WaveNum = display.newGroup()
    r0_0.SpriteNumber01.CreateNumbers(r0_0.WaveNum, r0_0.SpriteNumber01.sequenceNames.Score, 2, 225, 37, -2)
    r0_0.SpriteNumber01.CreateImage(r9_4, r0_0.SpriteNumber01.sequenceNames.Score, r0_0.SpriteNumber01.frameDefines.ScoreSlash, 250, 37)
    r0_0.WaveMax = display.newGroup()
    r0_0.SpriteNumber01.CreateNumbers(r0_0.WaveMax, r0_0.SpriteNumber01.sequenceNames.Score, 2, 260, 37, -2)
    r9_4:insert(r0_0.ScoreNum)
    r9_4:insert(r0_0.WaveNum)
    r9_4:insert(r0_0.WaveMax)
    r0_0.MPNum = display.newGroup()
    r0_0.SpriteNumber06.CreateNumbers(r0_0.MPNum, r0_0.SpriteNumber06.sequenceNames.Score, 5, 492, 16, -2)
    r0_0.HPNum = display.newGroup()
    r0_0.SpriteNumber01.CreateNumbers(r0_0.HPNum, r0_0.SpriteNumber01.sequenceNames.Score, 2, r0_0.Goal.x + 30, r0_0.Goal.y + 6, -2)
    r0_0.SpriteNumber01.CreateImage(_G.FrontRoot, r0_0.SpriteNumber01.sequenceNames.Score, r0_0.SpriteNumber01.frameDefines.ScoreSlash, r0_0.Goal.x + 52, r0_0.Goal.y + 6)
    r0_0.HPMax = display.newGroup()
    r0_0.SpriteNumber01.CreateNumbers(r0_0.HPMax, r0_0.SpriteNumber01.sequenceNames.Score, 2, r0_0.Goal.x + 63, r0_0.Goal.y + 6, -2)
    r0_4:insert(r0_0.MPNum)
    _G.FrontRoot:insert(r0_0.HPNum)
    _G.FrontRoot:insert(r0_0.HPMax)
    r0_0.TreasureboxRich = display.newGroup()
    r0_0.SpriteNumber01.CreateNumbers(r0_0.TreasureboxRich, r0_0.SpriteNumber01.sequenceNames.Score, 3, 678, 10, -2)
    r0_0.TreasureboxNormal = display.newGroup()
    r0_0.SpriteNumber01.CreateNumbers(r0_0.TreasureboxNormal, r0_0.SpriteNumber01.sequenceNames.Score, 3, 678, 36, -2)
    r0_4:insert(r0_0.TreasureboxRich)
    r0_4:insert(r0_0.TreasureboxNormal)
    r0_4:insert(r9_4)
    r0_0.StartBtn = r0_4
  end,
  hide_control_panel = function()
    -- line: [216, 223] id: 12
    if r0_0.ControlPanelTransition then
      transition.cancel(r0_0.ControlPanelTransition)
    end
    r0_0.ControlPanelTransition = transition.to(_G.PanelRoot, {
      time = 500,
      y = _G.HeightDiff - _G.PanelRoot.height,
      transition = easing.outExpo,
    })
  end,
  show_control_panel = function()
    -- line: [226, 243] id: 13
    if r6_0.GetPauseType() == r0_0.PauseTypeStopClockMenu or r6_0.GetPauseType() == r0_0.PauseTypeStopClock then
      return true
    end
    if r0_0.ControlPanelTransition then
      transition.cancel(r0_0.ControlPanelTransition)
    end
    r0_0.ControlPanelTransition = transition.to(_G.PanelRoot, {
      time = 500,
      y = 0,
      transition = easing.outExpo,
      onComplete = function()
        -- line: [241, 241] id: 14
        r0_0.ControlPanelTransition = nil
      end,
    })
  end,
}
