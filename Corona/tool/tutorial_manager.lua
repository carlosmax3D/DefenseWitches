-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("ui.finger")
local r1_0 = require("ui.ticker")
local r2_0 = nil
local r3_0 = nil
local r4_0 = false
local r5_0 = false
local r6_0 = false
local r7_0 = false
local r8_0 = nil
local r9_0 = nil
local r10_0 = nil
local r11_0 = nil
local r12_0 = nil
local r13_0 = nil
local r14_0 = nil
local r15_0 = {}
local r16_0 = 1
local r17_0 = nil
local r18_0 = nil
local r19_0 = false
local r20_0 = false
local r21_0 = false
local r22_0 = false
local r23_0 = false
local r24_0 = false
local r25_0 = false
local r26_0 = false
local r27_0 = {
  {
    x = 4,
    y = 5,
  },
  {
    x = 5,
    y = 5,
  },
  {
    x = 8,
    y = 5,
  }
}
local function r28_0(r0_1)
  -- line: [94, 96] id: 1
  return "data/game/" .. r0_1 .. ".png"
end
local function r40_0(r0_13)
  -- line: [439, 439] id: 13
  return "data/ticker/" .. r0_13 .. ".png"
end
return {
  IsGameStartTutorial = r4_0,
  IsSummonTutorial = r5_0,
  IsUpgradeTutorial = r6_0,
  IsOrbTutorial = r7_0,
  PlayGameFinger = r8_0,
  TapFieldFinger = r9_0,
  SelectSummonFilger = r10_0,
  SummonFinger = r11_0,
  UpgradeFinger = r12_0,
  UpgradeCharFinger = r13_0,
  OrbBtnFinger = r14_0,
  OrbCharFinger = r15_0,
  UseOrbFingerCnt = r16_0,
  LevelUpFinger = r17_0,
  CloseBtnFinger = r18_0,
  GameStartFlag = r19_0,
  FirstTapFieldFlag = r20_0,
  FirstSelectCharFlag = r21_0,
  FirstSummonFlag = r22_0,
  FirstUpgradeCharFlag = r23_0,
  FirstUpgradeFlag = r24_0,
  FirstOrbFlag = r25_0,
  FirstOrbCharFlag = r26_0,
  TutorialFieldPattern = r27_0,
  SetIsTutorial = function()
    -- line: [108, 138] id: 2
    r4_0 = false
    r5_0 = false
    r6_0 = false
    r7_0 = false
    if _G.MapSelect == 1 then
      local r0_2 = db.LoadStageClearCount(_G.MapSelect, _G.StageSelect)
      local r1_2 = db.GetStageInfo(_G.UserID, _G.MapSelect)
      if r0_2.clearCount ~= 0 then
        return 
      end
      if _G.StageSelect == 1 and r1_2[2] == 1 then
        r4_0 = true
        r5_0 = true
        r19_0 = false
        r20_0 = false
        r21_0 = false
        r22_0 = false
      elseif _G.StageSelect == 2 then
        r6_0 = true
        r23_0 = false
        r24_0 = false
      elseif _G.StageSelect == 3 then
        r7_0 = true
        r25_0 = false
        r26_0 = false
      end
    end
  end,
  GameStartTutorial = function(r0_3, r1_3)
    -- line: [143, 165] id: 3
    if r4_0 ~= true then
      return 
    end
    if r19_0 then
      return 
    end
    if r0_3 then
      if r8_0 ~= nil then
        return 
      end
      r8_0 = r0_0.new({
        px = 80,
        py = 120,
        dy = 1,
        phase = 0,
        phase_speed = 15,
        amp = 15,
        direction = 1,
      })
      r8_0.play(r1_3)
      r1_0.SetTickerMsg(406)
    else
      r19_0 = true
      if r8_0 == nil then
        return 
      end
      r8_0.stop()
      r1_0.SetTickerMsg(407)
    end
  end,
  TapFieldTutorial = function(r0_4, r1_4, r2_4, r3_4)
    -- line: [170, 214] id: 4
    if r5_0 ~= true then
      return 
    end
    if r0_4 then
      if r9_0 ~= nil or r2_4 == nil or r3_4 == nil then
        return 
      end
      r9_0 = r0_0.new({
        px = r2_4,
        py = r3_4 - 60,
        dy = 1,
        phase = 0,
        phase_speed = 15,
        amp = 15,
        rotation = 270,
        direction = 1,
      })
      r9_0.play(r1_4)
      r2_0 = display.newImage(_G.GridRoot, r28_0("icon_tap"), r2_4, r3_4, true)
      r3_0 = require("tool.scaling").new({
        scale = 0.005,
        maxScale = 1.05,
        minScale = 1,
      })
      r3_0.Play(r2_0)
      if r20_0 then
        r1_0.SetTickerMsg(408)
      else
        r1_0.SetTickerMsg(403)
      end
    else
      if r9_0 == nil then
        return 
      end
      r9_0.stop()
      r9_0 = nil
      r3_0.Stop()
      display.remove(r2_0)
    end
  end,
  SelectSummonTutorial = function(r0_5, r1_5, r2_5, r3_5, r4_5)
    -- line: [220, 245] id: 5
    if r5_0 ~= true then
      return 
    end
    if r0_5 then
      if r10_0 ~= nil or r2_5 == nil or r3_5 == nil then
        return 
      end
      r10_0 = r0_0.new({
        px = r2_5,
        py = r3_5,
        dy = 1,
        phase = 0,
        phase_speed = 15,
        direction = 1,
        amp = 15,
        rotation = r4_5,
      })
      r10_0.play(r1_5)
      r1_0.SetTickerMsg(404)
    else
      if r10_0 == nil then
        return 
      end
      r10_0.stop()
      r10_0 = nil
    end
  end,
  SummonTutorial = function(r0_6, r1_6, r2_6, r3_6)
    -- line: [250, 273] id: 6
    if r5_0 ~= true then
      return 
    end
    if r0_6 then
      if r11_0 ~= nil or r2_6 == nil or r3_6 == nil then
        return 
      end
      r11_0 = r0_0.new({
        px = r2_6 - 30,
        py = r3_6 + 10,
        dy = 1,
        phase = 0,
        phase_speed = 15,
        amp = 15,
      })
      r11_0.play(r1_6)
      r1_0.SetTickerMsg(405)
    else
      if r11_0 == nil then
        return 
      end
      r11_0.stop()
      r11_0 = nil
    end
  end,
  UpgradeCharTutorial = function(r0_7, r1_7, r2_7, r3_7)
    -- line: [278, 304] id: 7
    if r6_0 ~= true then
      return 
    end
    if r23_0 ~= false then
      return 
    end
    if r0_7 then
      if r13_0 ~= nil or r2_7 == nil or r3_7 == nil then
        return 
      end
      r13_0 = r0_0.new({
        px = r2_7 + 10,
        py = r3_7 - 80,
        dy = 1,
        phase = 0,
        phase_speed = 15,
        amp = 15,
        rotation = 270,
        direction = 1,
      })
      r13_0.play(r1_7)
      r1_0.SetTickerMsg(409)
    else
      if r13_0 == nil then
        return 
      end
      r13_0.stop()
      r13_0 = nil
    end
  end,
  UpgradeBtnTutorial = function(r0_8, r1_8, r2_8, r3_8)
    -- line: [309, 333] id: 8
    if r6_0 ~= true then
      return 
    end
    if r0_8 then
      if r12_0 ~= nil or r2_8 == nil or r3_8 == nil then
        return 
      end
      r12_0 = r0_0.new({
        px = r2_8 - 130,
        py = r3_8 - 30,
        dy = 1,
        phase = 0,
        phase_speed = 15,
        amp = 15,
      })
      r12_0.play(r1_8)
      r1_0.SetTickerMsg(410)
    else
      if r12_0 == nil then
        return 
      end
      r12_0.stop()
      r12_0 = nil
      r1_0.SetTickerMsg(411)
    end
  end,
  OrbBtnTutorial = function(r0_9, r1_9)
    -- line: [338, 361] id: 9
    if r7_0 ~= true then
      return 
    end
    if r0_9 then
      if r25_0 then
        return 
      end
      if r14_0 ~= nil then
        return 
      end
      r14_0 = r0_0.new({
        px = 150,
        py = 160,
        dy = 1,
        phase = 0,
        phase_speed = 15,
        amp = 15,
        direction = 1,
      })
      r14_0.play(r1_9)
      r1_0.SetTickerMsg(412)
    else
      if r14_0 == nil then
        return 
      end
      r14_0.stop()
      r14_0 = nil
      if r25_0 ~= true then
      end
    end
  end,
  OrbCharTutorial = function(r0_10, r1_10)
    -- line: [366, 391] id: 10
    if r7_0 ~= true then
      return 
    end
    if r0_10 then
      r15_0[r16_0] = r0_0.new({
        px = 5,
        py = -50,
        dy = 1,
        phase = 0,
        phase_speed = 15,
        amp = 15,
        rotation = 270,
        direction = 1,
      })
      r15_0[r16_0].play(r1_10)
      r16_0 = r16_0 + 1
      r1_0.SetTickerMsg(413)
    else
      for r5_10, r6_10 in pairs(r15_0) do
        r15_0[r5_10].stop()
      end
      r1_0.SetTickerMsg(414)
      if r26_0 ~= true then
        r26_0 = true
      end
    end
  end,
  LevelUpTutorial = function(r0_11, r1_11)
    -- line: [396, 414] id: 11
    if r0_11 then
      if r17_0 ~= nil then
        return 
      end
      r17_0 = r0_0.new({
        px = -50,
        py = 25,
        dy = 1,
        phase = 0,
        phase_speed = 15,
        amp = 15,
        direction = 0,
      })
      r17_0.play(r1_11)
    else
      if r17_0 == nil then
        return 
      end
      r17_0.stop()
      r17_0 = nil
    end
  end,
  CloseBtnTutorial = function(r0_12, r1_12)
    -- line: [419, 437] id: 12
    if r0_12 then
      if r18_0 ~= nil then
        return 
      end
      r18_0 = r0_0.new({
        px = 820,
        py = 20,
        dy = 1,
        phase = 0,
        phase_speed = 15,
        amp = 15,
        direction = 0,
      })
      r18_0.play(r1_12)
    else
      if r18_0 == nil then
        return 
      end
      r18_0.stop()
      r18_0 = nil
    end
  end,
  SetTickerPlate = function()
    -- line: [444, 456] id: 14
    if r4_0 == true or r5_0 == true or r6_0 == true or r7_0 == true then
      for r3_14 = 0, 960, 16 do
        util.LoadParts(_G.TickerRoot, r40_0("plate_ticker"), r3_14, 560)
      end
      r1_0.SetTickerMsg(406)
    end
  end,
}
