-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("logic.game.GameStatus")
local r1_0 = require("logic.char.CharStatus")
local r2_0 = require("logic.game.BaseGame")
local r3_0 = require("game.pause_manager")
local r4_0 = require("resource.char_define")
local r5_0 = require("tool.tutorial_manager")
local r6_0 = 87
local r7_0 = 0
local function r8_0()
  -- line: [14, 17] id: 1
  _G.GuideSpeedButton = false
  r2_0.remove_speed_effect_id()
end
return {
  SpeedCtl = function(r0_2)
    -- line: [19, 129] id: 2
    local function r1_2(r0_3)
      -- line: [20, 20] id: 3
      return "data/map/interface/" .. r0_3 .. ".png"
    end
    local function r2_2(r0_4)
      -- line: [22, 22] id: 4
      return r1_2(r0_4 .. _G.UILanguage)
    end
    local function r4_2(r0_6)
      -- line: [60, 83] id: 6
      r8_0()
      if r3_0.GetPauseType() == r0_0.PauseTypeStopClockMenu or r3_0.GetPauseType() == r0_0.PauseTypeStopClock then
        return true
      end
      sound.PlaySE(3)
      if r1_0.KalaStruct then
        r1_0.KalaStruct.func.sound(r1_0.KalaStruct, 13)
        r1_0.SummonChar[r4_0.CharId.Kala].Reload(r1_0.KalaStruct, 2)
      end
      r0_0.SpeedBtn[1].isVisible = true
      r0_0.SpeedBtn[2].isVisible = false
      r0_0.SpeedBtn[3].isVisible = false
      if _G.IsDebug and _G.TestSpeed then
        _G.SpeedType = _G.TestSpeed
        events.SetRepeatTime(_G.SpeedType)
      else
        _G.SpeedType = 2
        events.SetRepeatTime(_G.SpeedType)
      end
    end
    local function r5_2(r0_7)
      -- line: [85, 103] id: 7
      r8_0()
      if r3_0.GetPauseType() == r0_0.PauseTypeStopClockMenu or r3_0.GetPauseType() == r0_0.PauseTypeStopClock then
        return true
      end
      if r1_0.KalaStruct then
        r1_0.KalaStruct.func.sound(r1_0.KalaStruct, 11)
        r1_0.SummonChar[r4_0.CharId.Kala].Reload(r1_0.KalaStruct, 2)
      end
      sound.PlaySE(3)
      r0_0.SpeedBtn[1].isVisible = false
      r0_0.SpeedBtn[2].isVisible = true
      r0_0.SpeedBtn[3].isVisible = false
      _G.SpeedType = 1
      events.SetRepeatTime(_G.SpeedType)
    end
    table.insert(r0_0.SpeedBtn, util.LoadBtn({
      rtImg = r0_2,
      fname = r1_2("speed2"),
      x = r6_0,
      y = r7_0,
      func = function(r0_5)
        -- line: [24, 58] id: 5
        r8_0()
        if r3_0.GetPauseType() == r0_0.PauseTypeStopClockMenu or r3_0.GetPauseType() == r0_0.PauseTypeStopClock then
          return true
        end
        if r1_0.KalaStruct then
          r1_0.KalaStruct.func.sound(r1_0.KalaStruct, 12)
          r1_0.SummonChar[r4_0.CharId.Kala].Reload(r1_0.KalaStruct, 2)
        end
        sound.PlaySE(3)
        if r1_0.KalaOnlyOne then
          if r1_0.KalaStruct then
            r1_0.KalaStruct.func.sound(r1_0.KalaStruct, 13)
            r1_0.SummonChar[r4_0.CharId.Kala].Reload(r1_0.KalaStruct, 2)
          end
          r0_0.SpeedBtn[1].isVisible = false
          r0_0.SpeedBtn[2].isVisible = false
          r0_0.SpeedBtn[3].isVisible = true
          _G.SpeedType = 3
          events.SetRepeatTime(_G.SpeedType)
        else
          if r1_0.KalaStruct then
            r1_0.KalaStruct.func.sound(r1_0.KalaStruct, 11)
            r1_0.SummonChar[r4_0.CharId.Kala].Reload(r1_0.KalaStruct, 2)
          end
          r0_0.SpeedBtn[1].isVisible = false
          r0_0.SpeedBtn[2].isVisible = true
          r0_0.SpeedBtn[3].isVisible = false
          _G.SpeedType = 1
          events.SetRepeatTime(_G.SpeedType)
        end
      end,
    }))
    table.insert(r0_0.SpeedBtn, util.LoadBtn({
      rtImg = r0_2,
      fname = r1_2("speed1"),
      x = r6_0,
      y = r7_0,
      func = r4_2,
      show = false,
    }))
    table.insert(r0_0.SpeedBtn, util.LoadBtn({
      rtImg = r0_2,
      fname = r1_2("speed3"),
      x = r6_0,
      y = r7_0,
      func = r5_2,
      show = false,
    }))
  end,
  speedBtnX = r6_0,
  speedBtnY = r7_0,
}
