-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = {}
local r1_0 = require("common.effect.h_slide_in_out")
local r2_0 = require("common.base_dialog")
local r3_0 = require("resource.char_define")
local r4_0 = require("evo.time_bar")
local r5_0 = require("evo.evo_combi_manager")
local r6_0 = require("logic.game.GameStatus")
local r7_0 = require("common.sprite_loader")
local r8_0 = r7_0.new({
  imageSheet = "common.sprites.sprite_evo_combi_number01",
})
local r9_0 = r7_0.new({
  imageSheet = "common.sprites.sprite_number01",
})
local r10_0 = r7_0.new({
  imageSheet = "common.sprites.sprite_number03",
})
local r11_0 = {
  require("evo.evo_effect.evo_eff"),
  require("evo.evo_effect.evo_eff"),
  require("evo.evo_effect.evo_eff")
}
local r12_0 = require("tool.tutorial_manager")
local r13_0 = 0
local r14_0 = 1
local r15_0 = 2
local r16_0 = 1
local r17_0 = 2
local r18_0 = 200
local r19_0 = 320
local r20_0 = "data/game"
local r21_0 = "data/game/lv2"
local r22_0 = "data/evo/evo_effect"
local r23_0 = "evol_up"
local r24_0 = 1
local r25_0 = 0
local r26_0 = 1
local r27_0 = 2
local r28_0 = nil
local r29_0 = false
local r30_0 = 0
local r31_0 = nil
local r32_0 = nil
local r33_0 = r25_0
local r34_0 = nil
local r35_0 = nil
local r36_0 = nil
local r37_0 = nil
local r38_0 = nil
local r39_0 = nil
local r40_0 = nil
local r41_0 = nil
local r42_0 = nil
local r43_0 = nil
local r44_0 = nil
local r45_0 = nil
local r46_0 = nil
local r47_0 = nil
local function r48_0(r0_1)
  -- line: [103, 103] id: 1
  return "data/evo/evo_str_effect/" .. r0_1 .. ".png"
end
local function r49_0(r0_2)
  -- line: [104, 104] id: 2
  return r48_0(r0_2 .. _G.UILanguage)
end
local function r50_0(r0_3)
  -- line: [105, 105] id: 3
  return "data/evo/evo_combi/" .. r0_3 .. ".png"
end
local function r51_0(r0_4)
  -- line: [106, 106] id: 4
  return r50_0(r0_4 .. _G.UILanguage)
end
local function r52_0(r0_5)
  -- line: [107, 107] id: 5
  return "data/evo/evo_select_mode/" .. r0_5 .. ".png"
end
local function r53_0(r0_6)
  -- line: [108, 108] id: 6
  return r52_0(r0_6 .. _G.UILanguage)
end
local function r54_0(r0_7)
  -- line: [109, 109] id: 7
  return "data/map/interface/" .. r0_7 .. ".png"
end
local function r55_0(r0_8)
  -- line: [110, 110] id: 8
  return "data/game/upgrade/" .. r0_8 .. ".png"
end
local function r56_0(r0_9, r1_9)
  -- line: [115, 127] id: 9
  if r0_9 == nil or r1_9 == nil then
    DebugPrint("チェックする対象が nil です")
    return false
  end
  if r0_9[r1_9] ~= nil then
    return true
  end
  return false
end
local function r57_0(r0_10)
  -- line: [132, 139] id: 10
  if r0_10 == nil or r56_0(r0_10.func, "rankTable") == false then
    return nil
  end
  return r0_10.func.rankTable
end
local function r58_0(r0_11)
  -- line: [144, 154] id: 11
  if r56_0(r0_11.evo, "charEffect") == false or r0_11.evo.charEffect == nil then
    return 
  end
  anime.Pause(r0_11.evo.charEffect, true)
  anime.Show(r0_11.evo.charEffect, false)
  anime.Remove(r0_11.evo.charEffect)
  r0_11.evo.charEffect = nil
end
local function r59_0(r0_12)
  -- line: [159, 193] id: 12
  if r0_12 == nil or r56_0(r0_12.evo, "evoLevel") == false then
    DebugPrint("キャラクタ構造体不正")
    return 
  end
  r58_0(r0_12)
  if r0_12.evo.evoLevel < 1 or #r11_0 < r0_12.evo.evoLevel then
    DebugPrint("evoLevel値不正")
    return 
  end
  local r1_12 = r0_12.x
  local r2_12 = r0_12.y
  if r0_12.type == r3_0.CharId.Yuiko then
    r1_12 = r1_12 + 6
    r2_12 = r2_12 + 5
  end
  local r3_12 = anime.Register(r11_0[r0_12.evo.evoLevel].GetData(), r1_12, r2_12, r22_0)
  r0_12.spr:insert(r0_12.spr.numChildren, anime.GetSprite(r3_12))
  anime.Loop(r3_12, true)
  anime.Show(r3_12, true)
  r0_12.evo.charEffect = r3_12
end
local function r60_0(r0_13)
  -- line: [198, 205] id: 13
  if r56_0(r0_13.evo, "timeGauge") == false then
    return 
  end
  r0_13.evo.timeGauge.dispose()
  r0_13.evo.timeGauge = nil
end
local function r61_0(r0_14, r1_14)
  -- line: [210, 238] id: 14
  if r56_0(r0_14, "evo") == false then
    return 
  end
  r60_0(r0_14)
  local r2_14 = display.newGroup()
  local r3_14 = r4_0.new({
    rtImg = r2_14,
    x = 0,
    y = 0,
    w = 60,
    h = 8,
    time = r1_14,
  })
  local r4_14 = 50
  if r0_14.evo.evoLevel == 3 then
    r4_14 = 80
  end
  r2_14:setReferencePoint(display.CenterReferencePoint)
  r2_14.x = r0_14.x
  r2_14.y = r0_14.y - r4_14
  _G.TickerRoot:insert(r0_14.spr.numChildren, r2_14)
  r0_14.evo.timeGauge = r3_14
end
local function r62_0(r0_15, r1_15)
  -- line: [243, 250] id: 15
  if r56_0(r0_15, "evo") == false or r0_15.evo.endFunc ~= nil then
    return 
  end
  r0_15.evo.endFunc = r1_15
end
local function r63_0(r0_16, r1_16)
  -- line: [255, 275] id: 16
  local r2_16 = r57_0(r0_16)
  if r2_16 == nil then
    return -1
  end
  local r3_16 = 0
  local r4_16 = 1
  if r56_0(r0_16.evo, "before") == true and 0 < r0_16.evo.before.evoLevel then
    r4_16 = r0_16.evo.before.evoLevel + 1
  end
  local r5_16 = r2_16.GetCharRank(r0_16.type)
  if r5_16 ~= nil then
    r3_16 = r2_16.GetRankData(r5_16.rank).orb
  end
  return r3_16 * r1_16
end
local function r64_0(r0_17, r1_17)
  -- line: [281, 307] id: 17
  if r56_0(r0_17.func, "changeSprite") == false then
    DebugPrint("------ none change sprite")
    return 
  end
  if r0_17.type == r3_0.CharId.Yuiko then
    r0_17.func.changeSprite(r0_17, r1_17)
  else
    local r2_17 = nil
    if r1_17 == 3 then
      r2_17 = r21_0
    elseif r1_17 == 2 then
      r2_17 = r21_0
    else
      r2_17 = r20_0
    end
    r0_17.func.changeSprite(r0_17, r2_17, r1_17)
  end
  char.SetOrder(r0_17)
end
local function r65_0(r0_18)
  -- line: [312, 338] id: 18
  local r1_18 = r57_0(r0_18)
  if r1_18 == nil or r0_18 == nil then
    return 
  end
  local r2_18 = nil
  if r0_18.evo.evoLevel == 0 then
    r2_18 = {
      scale = 1,
      offsetX = 0,
      offsetY = 0,
    }
  else
    r2_18 = r1_18.GetEvoLevelScale(r0_18.evo.evoLevel)
  end
  r0_18.evo.imageScale = r2_18.scale
  anime.SetOffset(r0_18.anime, r2_18.offsetX, r2_18.offsetY)
  anime.SetImageScale(r0_18.anime, r2_18.scale, r2_18.scale)
end
local function r66_0(r0_19)
  -- line: [343, 387] id: 19
  if r56_0(r0_19, "evo") == true then
    return false
  end
  r0_19.evo = {
    evoLevel = 0,
    previousTime = 0,
    durationTime = 0,
    elapseTime = 0,
    timeGauge = nil,
    imageScale = 1,
    endFunc = nil,
    charDropRate = 0,
    rareDropRateCoefficient = 0,
    evIsStart = false,
    ev = nil,
    charEffect = nil,
    before = {
      evoLevel = 0,
      imageScale = 1,
      charDropRate = 0,
      rareDropRateCoefficient = 0,
    },
  }
  return true
end
local function r67_0(r0_20, r1_20)
  -- line: [392, 426] id: 20
  local r2_20 = r57_0(r0_20)
  if r2_20 == nil then
    return 
  end
  local r3_20 = _G.UserStatus[r0_20.type]
  if r1_20 > 0 then
    r0_20.wait[r0_20.level] = r3_20.speed[r0_20.level] * r2_20.GetEvoData(r1_20).spd
    r0_20.power[r0_20.level] = r3_20.power[r0_20.level] * r2_20.GetEvoData(r1_20).atk
    char.SetRangePower(r0_20, r3_20, (char.GetRangePowerup() + 100) / 100 * r2_20.GetEvoData(r1_20).rng)
  else
    r0_20.wait[r0_20.level] = r3_20.speed[r0_20.level]
    r0_20.power[r0_20.level] = r3_20.power[r0_20.level]
    char.SetRangePower(r0_20, r3_20, (char.GetRangePowerup() + 100) / 100)
  end
  if r0_20.type == r3_0.CharId.Yuiko then
    char.SetYuikoEvoLevel(r1_20)
  end
end
local function r68_0(r0_21, r1_21)
  -- line: [431, 444] id: 21
  if r56_0(r0_21.evo, "ev") == false or r56_0(r0_21.evo, "timeGauge") == false or r56_0(r0_21.evo, "elapseTime") == false or r56_0(r0_21.evo, "durationTime") == false then
    return 
  end
  r0_21.evo.durationTime = r0_21.evo.durationTime + r1_21
  r0_21.evo.timeGauge.setTotalTime(r0_21.evo.durationTime)
end
local function r69_0(r0_22, r1_22)
  -- line: [449, 456] id: 22
  if r56_0(r0_22.evo, "rareDropRateCoefficient") == false then
  end
  r0_22.evo.rareDropRateCoefficient = r1_22
end
local function r70_0(r0_23)
  -- line: [461, 467] id: 23
  if r0_23 == nil or r56_0(r0_23.evo, "evIsStart") == false then
    return false
  end
  return r0_23.evo.evIsStart
end
local function r71_0(r0_24)
  -- line: [472, 478] id: 24
  if r56_0(r0_24.evo, "ev") == true then
    events.Delete(r0_24.evo.ev)
    r0_24.evo.ev = nil
    r0_24.evo.evIsStart = false
  end
end
local function r72_0(r0_25)
  -- line: [483, 520] id: 25
  if r0_25 == nil or r56_0(r0_25, "evo") == false then
    return 
  end
  r60_0(r0_25)
  r0_25.evo.evoLevel = 0
  r0_25.evo.imageScale = 1
  r0_25.evo.charDropRate = 0
  r0_25.evo.rareDropRateCoefficient = 0
  if r56_0(r0_25.evo, "before") == true then
    r0_25.evo.before.evoLevel = 0
    r0_25.evo.before.imageScale = 1
    r0_25.evo.before.charDropRate = 0
    r0_25.evo.before.rareDropRateCoefficient = 0
  end
  r5_0.RemoveChar(r0_25)
  r65_0(r0_25)
  r64_0(r0_25, 0)
  r58_0(r0_25)
  r67_0(r0_25, 0)
  if r0_25.evo.endFunc ~= nil then
    r0_25.evo.endFunc(r0_25)
  end
end
local function r73_0(r0_26, r1_26)
  -- line: [525, 531] id: 26
  if r56_0(r0_26.evo, "previousTime") == false then
    return 
  end
  r0_26.evo.previousTime = r1_26
end
local function r74_0(r0_27)
  -- line: [536, 550] id: 27
  for r4_27, r5_27 in pairs(r28_0) do
    local r6_27 = nil
    if r5_27.struct ~= nil then
      r6_27 = r5_27.struct
    else
      r6_27 = r5_27
    end
    if r40_0(r6_27) == true then
      r73_0(r6_27, r0_27)
    end
  end
end
local function r75_0(r0_28, r1_28, r2_28)
  -- line: [555, 575] id: 28
  local r3_28 = os.time()
  r1_28.evo.elapseTime = r1_28.evo.elapseTime + os.difftime(r3_28, r1_28.evo.previousTime) * events.GetRepeatTime()
  if r1_28.evo.durationTime <= r1_28.evo.elapseTime then
    r71_0(r1_28)
    r72_0(r1_28)
    return false
  end
  r73_0(r1_28, r3_28)
  r1_28.evo.timeGauge.updateFunc(r1_28.evo.elapseTime)
  return true
end
local function r76_0(r0_29)
  -- line: [580, 609] id: 29
  local r1_29 = r57_0(r0_29)
  if r1_29 == nil or r56_0(r0_29.evo, "evoLevel") == false or r0_29.evo.evoLevel < 1 then
    return 
  end
  local r2_29 = 0
  local r3_29 = r1_29.GetCharRank(r0_29.type)
  if r3_29 == nil then
    return 
  end
  r2_29 = r1_29.GetRankData(r3_29.rank).dtm
  r71_0(r0_29)
  r0_29.evo.durationTime = r2_29 * 0.001
  r0_29.evo.elapseTime = 0
  r0_29.evo.ev = events.Register(r75_0, r0_29, 0)
  r61_0(r0_29, r0_29.evo.durationTime)
  events.Disable(r0_29.evo.ev, true)
end
local function r77_0(r0_30)
  -- line: [614, 636] id: 30
  if r0_30 == nil or r56_0(r0_30.evo, "before") == false then
    return 
  end
  r0_30.evo.evoLevel = r0_30.evo.before.evoLevel
  r0_30.evo.imageScale = r0_30.evo.before.imageScale
  r0_30.evo.charDropRate = r0_30.evo.before.charDropRate
  r0_30.evo.rareDropRateCoefficient = r0_30.evo.before.rareDropRateCoefficient
  if r56_0(r0_30.evo, "ev") == true then
    events.Delete(r0_30.evo.ev)
    r0_30.evo.ev = nil
  end
  r64_0(r0_30, r0_30.evo.evoLevel)
  r65_0(r0_30)
  r67_0(r0_30, r0_30.evo.evoLevel)
end
local function r78_0(r0_31)
  -- line: [641, 653] id: 31
  game.SetScoreType(1)
  game.Play()
  r73_0(r0_31, os.time())
  r74_0(os.time())
  events.Disable(r0_31.evo.ev, false)
end
local function r79_0(r0_32)
  -- line: [658, 671] id: 32
  local r1_32 = display.newGroup()
  local r2_32 = 50
  local r3_32 = r0_32
  local r4_32 = string.len(tostring(r0_32)) * r2_32
  repeat
    r3_32 = math.floor(r3_32 * 0.1)
    r8_0.CreateImage(r1_32, r8_0.sequenceNames.evoCombiNum, r8_0.frameDefines.evoCombiNumStart + r3_32 % 10, r4_32, 0)
    r4_32 = r4_32 - r2_32
  until r3_32 <= 0
  return r1_32
end
local function r80_0(r0_33, r1_33)
  -- line: [676, 692] id: 33
  if r1_33 ~= nil then
    r1_33()
  end
  r33_0 = r25_0
  if r37_0 ~= nil then
    r2_0.FadeOutMask(r37_0, 100, function()
      -- line: [686, 689] id: 34
      display.remove(r37_0)
      r37_0 = nil
    end)
  end
end
local function r81_0(r0_35, r1_35)
  -- line: [697, 798] id: 35
  if r0_35.type == r3_0.CharId.Yuiko then
    r80_0(r0_35, r1_35)
    return 
  end
  local r2_35 = r57_0(r0_35)
  if r2_35 == nil or r37_0 == nil then
    return 
  end
  if r36_0 ~= nil then
    r36_0.Clean()
    if r36_0.slideTarget ~= nil then
      display.remove(r36_0.slideTarget)
    end
    r36_0 = nil
  end
  local r3_35 = display.newGroup()
  local r4_35 = r2_35.GetEvoData(r0_35.evo.evoLevel)
  local r5_35 = {
    "text_attack_",
    "text_speed_",
    "text_range_"
  }
  local r6_35 = nil
  local r7_35 = nil
  for r11_35, r12_35 in pairs({
    "atk",
    "spd",
    "rng"
  }) do
    local r13_35 = display.newGroup()
    display.newRect(r13_35, 0, 0, 500, 68).alpha = 0
    local r14_35 = util.LoadParts(r13_35, r49_0(r5_35[r11_35]), 0, 0)
    local r15_35 = r8_0.CreateImage(r13_35, r8_0.sequenceNames.evoCombiNum, r8_0.frameDefines.evoCombiPercent, 0, 0)
    r15_35:setReferencePoint(display.TopRightReferencePoint)
    r15_35.x = r13_35.width
    local r16_35 = nil
    if r0_35.type == r3_0.CharId.Jill then
      if r12_35 == "spd" then
        r16_35 = r4_35[r12_35] * 200
      else
        r16_35 = (r4_35[r12_35] - 1) * 100
      end
    else
      r16_35 = (r4_35[r12_35] - 1) * 100
    end
    local r17_35 = r79_0(r16_35)
    r13_35:insert(r17_35)
    r17_35:setReferencePoint(display.TopRightReferencePoint)
    r17_35.x = r15_35.x - r15_35.width
    local r18_35 = r8_0.CreateImage(r13_35, r8_0.sequenceNames.evoCombiNum, r8_0.frameDefines.evoCombiPlus, 0, 0)
    r18_35:setReferencePoint(display.TopRightReferencePoint)
    r18_35.x = r17_35.x - r17_35.width
    r3_35:insert(r13_35)
    r13_35.y = (r11_35 - 1) * r13_35.height
  end
  r3_35:setReferencePoint(display.CenterReferencePoint)
  r36_0 = r1_0.new({
    slideTarget = r3_35,
    startPosX = -r3_35.width * 0.5,
    stopPosX = _G.Width * 0.5,
    endPosX = _G.Width + r3_35.width,
    fixPosY = _G.Height * 0.5,
    startAlpha = 0,
    stopAlpha = 1,
    endAlpha = 0,
    start2StopTime = 13,
    stopTime = 44,
    stop2EndTime = 10,
    endListener = function()
      -- line: [774, 791] id: 36
      display.remove(r3_35)
      r3_35 = nil
      r33_0 = r25_0
      if r37_0 ~= nil then
        r2_0.FadeOutMask(r37_0, 100, function()
          -- line: [781, 784] id: 37
          display.remove(r37_0)
          r37_0 = nil
        end)
      end
      if r1_35 ~= nil then
        r1_35()
      end
    end,
  })
  r37_0:insert(r3_35)
  r36_0.Play()
  r33_0 = r27_0
end
local function r82_0(r0_38, r1_38)
  -- line: [803, 908] id: 38
  local r2_38 = r57_0(r0_38)
  if r2_38 == nil then
    return 
  end
  if r34_0 ~= nil then
    r34_0.Clean()
    if r34_0.slideTarget ~= nil then
      display.remove(r34_0.slideTarget)
    end
    r34_0 = nil
  end
  if r35_0 ~= nil then
    r35_0.Clean()
    if r35_0.slideTarget ~= nil then
      display.remove(r35_0.slideTarget)
    end
    r35_0 = nil
  end
  if r37_0 ~= nil then
    display.remove(r37_0)
    r37_0 = nil
  end
  local r3_38 = display.newGroup()
  local r4_38 = util.LoadParts(r3_38, r49_0("text_evo_title01_"), 0, 0)
  local r5_38 = util.LoadParts(r3_38, r48_0(string.format("text_evo_num_%d", r0_38.evo.evoLevel)), 0, 0)
  local r6_38 = util.LoadParts(r3_38, r49_0("text_evo_title02_"), 0, 0)
  r5_38.y = (r4_38.height - r5_38.height) * 0.5
  if _G.UILanguage == "en" then
    r5_38.x = r4_38.width - r5_38.width - 30
    r6_38.x = r4_38.x + r4_38.width
  else
    r5_38.x = r4_38.width * 0.5 + r5_38.width + 70
    r6_38.x = r5_38.x + r5_38.width
  end
  r3_38:setReferencePoint(display.CenterReferencePoint)
  r34_0 = r1_0.new({
    slideTarget = r3_38,
    startPosX = -r3_38.width * 0.5,
    stopPosX = _G.Width * 0.5,
    endPosX = _G.Width + r3_38.width,
    fixPosY = _G.Height * 0.5 - r3_38.height * 0.5,
    startAlpha = 0,
    stopAlpha = 1,
    endAlpha = 0,
    start2StopTime = 13,
    stopTime = 44,
    stop2EndTime = 10,
    endListener = function()
      -- line: [859, 865] id: 39
      display.remove(r3_38)
      r3_38 = nil
      r81_0(r0_38, r1_38)
    end,
  })
  local r7_38 = display.newGroup()
  local r8_38 = r2_38.GetCharRank(r0_38.type)
  if r8_38 == nil then
    return 
  end
  local r10_38 = r79_0(math.floor(r2_38.GetRankData(r8_38.rank).dtm * 0.001))
  r7_38:insert(r10_38)
  local r11_38 = r8_0.CreateImage(r7_38, r8_0.sequenceNames.evoCombiNum, r8_0.frameDefines.evoCombiSec, 0, 0)
  r11_38.x = r10_38.width + r11_38.width
  r7_38:setReferencePoint(display.CenterReferencePoint)
  r35_0 = r1_0.new({
    slideTarget = r7_38,
    startPosX = _G.Width + r7_38.width,
    stopPosX = _G.Width * 0.5,
    endPosX = -r7_38.width,
    fixPosY = _G.Height * 0.5 + r7_38.height * 0.5,
    startAlpha = 0,
    stopAlpha = 1,
    endAlpha = 0,
    start2StopTime = 13,
    stopTime = 44,
    stop2EndTime = 10,
    endListener = function()
      -- line: [893, 896] id: 40
      display.remove(r7_38)
      r7_38 = nil
    end,
  })
  r37_0 = display.newGroup()
  r2_0.FadeInMask(r37_0, {
    0,
    0,
    0,
    0.3
  }, 100)
  r37_0:insert(r3_38)
  r37_0:insert(r7_38)
  r34_0.Play()
  r35_0.Play()
  r33_0 = r26_0
end
local function r83_0(r0_41, r1_41)
  -- line: [913, 1037] id: 41
  local r2_41 = r57_0(r0_41)
  if r56_0(r0_41.evo, "ev") == false or r2_41 == nil then
    return 
  end
  local function r3_41()
    -- line: [922, 926] id: 42
    if r1_41 ~= nil then
      r1_41()
    end
  end
  if r42_0() == true then
    return 
  end
  local r4_41 = 1
  local r5_41 = display.newGroup()
  r5_41.isVisible = true
  display.newRect(r5_41, 0, 0, _G.Width, _G.Height).alpha = 0.01
  r5_41:addEventListener("touch", function(r0_43)
    -- line: [938, 961] id: 43
    if r0_43.phase == "ended" then
      if r33_0 == r26_0 then
        if r34_0 ~= nil and r34_0.GetAnimationPhase() <= r1_0.AnimationPhaseCountEnd then
          r34_0.CancelPlay()
        end
        if r35_0 ~= nil and r35_0.GetAnimationPhase() <= r1_0.AnimationPhaseCountEnd then
          r35_0.CancelPlay()
        end
      elseif r33_0 == r27_0 and r36_0 ~= nil and r36_0.GetAnimationPhase() <= r1_0.AnimationPhaseCountEnd then
        r36_0.CancelPlay()
      end
    end
    return true
  end)
  local function r6_41()
    -- line: [964, 969] id: 44
    Runtime:removeEventListener("touch", r5_41)
    display.remove(r5_41)
    r5_41 = nil
  end
  local r7_41 = r5_0.AddChar(r0_41)
  local function r8_41()
    -- line: [976, 982] id: 45
    local r0_45 = r39_0(r0_41)
    if r0_45 ~= nil then
      r0_41.func.sound(r0_41, r0_45)
    end
  end
  r33_0 = r25_0
  r82_0(r0_41, function()
    -- line: [986, 1014] id: 46
    if r7_41 == true and r5_0.IsCombiSkill() == true then
      r6_41()
      r5_0.PlayCombiSkillEffect(r0_0, function()
        -- line: [994, 1005] id: 47
        r78_0(r0_41)
        r8_41()
        local r0_47 = r5_0.GetCombiCharList()
        game.UpdateTwoUnitAndEvoxx({
          r0_47[1].type,
          r0_47[2].type
        }, {
          r0_47[1].evo.evoLevel,
          r0_47[2].evo.evoLevel
        })
        r3_41()
      end)
    else
      r8_41()
      r78_0(r0_41)
      r6_41()
      r3_41()
    end
  end)
  r0_41.evo.before = nil
  r0_41.evo.evIsStart = true
  statslog.LogSend(r23_0, {
    item = r0_41.type,
    evo_no = r0_41.evo.evoLevel,
    have_orb = _G.OrbManager.GetRemainOrb(),
    max_orb = _G.OrbManager.GetMaxOrb(),
    hp = _G.HP,
    wave = _G.WaveNr,
  })
end
local function r84_0(r0_48, r1_48, r2_48)
  -- line: [1042, 1145] id: 48
  local r3_48 = r57_0(r0_48)
  if r0_48 == nil or r56_0(r0_48.evo, "evoLevel") == true and r3_48 ~= nil and r3_48.GetEvoDataMax() <= r0_48.evo.evoLevel then
    return 
  end
  local r4_48 = 0
  local function r5_48(r0_49, r1_49, r2_49)
    -- line: [1057, 1098] id: 49
    _G.OrbManager.SetUsedOrb(r2_49)
    _G.OrbManager.AddUsedOrbCount(r4_48)
    game.ViewPanel()
    game.UpdateUseOrbMedal()
    game.UpdateUnitAndEvoxx(r0_49.type, r1_49)
    r0_49.evo.evoLevel = r1_49
    local r3_49 = r3_48.GetCharRank(r0_48.type)
    if r3_49 == nil then
      return 
    end
    r0_49.evo.charDropRate = r3_48.GetRankData(r3_49.rank).drt
    r64_0(r0_49, r0_49.evo.evoLevel)
    if r0_49.evo.evoLevel == 3 then
      r65_0(r0_49)
    end
    r59_0(r0_49)
    r67_0(r0_49, r0_49.evo.evoLevel)
    r76_0(r0_49)
    _G.BingoManager.updateUserBingoData(_G.BingoManagerClass.MISSION_TYPE.USE_FULL_BOCKO(), nil)
    if r2_48 ~= nil then
      r2_48()
    end
  end
  r66_0(r0_48)
  r0_48.evo.before = {
    evoLevel = r0_48.evo.evoLevel,
    imageScale = r0_48.evo.imageScale,
    charDropRate = r0_48.evo.charDropRate,
    rareDropRateCoefficient = r0_48.evo.rareDropRateCoefficient,
  }
  local r6_48 = r63_0(r0_48, r1_48)
  if r6_48 < 0 then
    DebugPrint("使用オーブ数エラー : " .. tostring(r6_48))
    return 
  end
  r4_48 = r6_48
  if _G.OrbManager.IsUseOrb(r6_48) == true then
    r5_48(r0_48, r1_48, r6_48)
  else
    local r7_48 = r6_48 - _G.OrbManager.GetRemainOrb()
    _G.OrbManager.ShowRecoveryOrbDialog(function(r0_50)
      -- line: [1129, 1143] id: 50
      if r0_50 == true then
        game.ViewPanel()
        r32_0 = nil
        r43_0(r14_0, r17_0)
      else
        r77_0(r0_48)
        r43_0(r13_0, r16_0)
      end
    end)
  end
end
local function r85_0(r0_51, r1_51)
  -- line: [1150, 1194] id: 51
  local function r2_51(r0_52, r1_52)
    -- line: [1152, 1162] id: 52
    if r0_52 == nil then
      return 
    end
    if r1_52 == true then
      r0_52:setFillColor(180)
    else
      r0_52:setFillColor(255)
    end
  end
  if r0_51 == nil or r0_51.spr == nil then
    return 
  end
  if r1_51 == true then
    local r3_51 = display.newGroup()
    r0_51.spr.badmarkImage = util.LoadParts(r3_51, "data/game/badmark.png", r0_51.x - 32, r0_51.y - 32)
    r0_51.spr.badmarkImage:setReferencePoint(display.CenterReferencePoint)
    r0_51.spr.badmarkImage.xScale = -1
    r0_51.spr.badmarkImage.yScale = -1
    _G.DialogRoot:insert(r3_51)
  elseif r0_51.spr.badmarkImage ~= nil then
    display.remove(r0_51.spr.badmarkImage)
    r0_51.spr.badmarkImage = nil
  end
  local r3_51 = nil
  local r4_51 = nil
  for r8_51 = 1, r0_51.spr.numChildren, 1 do
    if r0_51.spr[r8_51].numChildren == nil then
      r2_51(r0_51.spr[r8_51], r1_51)
    else
      for r12_51 = 1, r0_51.spr[r8_51].numChildren, 1 do
        r2_51(r0_51.spr[r8_51][r12_51], r1_51)
      end
    end
  end
end
local function r86_0(r0_53, r1_53)
  -- line: [1199, 1207] id: 53
  if r1_53 == nil then
    return 
  end
  local r2_53 = util.LoadParts(r0_53, r54_0("stage_icon_orb"), 8, 3)
  r9_0.CreateImage(r0_53, r9_0.sequenceNames.Score, r9_0.frameDefines.ScoreCross, 31, 5)
  r9_0.CreateImage(r0_53, r9_0.sequenceNames.Score, r9_0.frameDefines.ScoreStart + r1_53, 45, 4)
end
local function r87_0(r0_54, r1_54)
  -- line: [1212, 1222] id: 54
  if r0_54 == nil then
    return 
  end
  local r2_54 = r44_0(r1_54)
  if r2_54 == nil then
    return 
  end
  r86_0(r0_54, r2_54)
end
local function r88_0(r0_55, r1_55)
  -- line: [1227, 1256] id: 55
  if r57_0(r0_55) == nil then
    return 
  end
  if r1_55 == true then
    local r3_55 = display.newGroup()
    local r4_55 = util.LoadParts(r3_55, r52_0("stage_plate_orb"), 0, 0)
    r87_0(r3_55, r0_55)
    r3_55:setReferencePoint(display.CenterReferencePoint)
    r3_55.x = r0_55.x
    r3_55.y = r0_55.y - 50
    _G.DialogRoot:insert(r3_55)
    r0_55.spr.orbImage = r3_55
    r12_0.OrbCharTutorial(true, r3_55)
  elseif r0_55.spr.orbImage ~= nil then
    r12_0.OrbCharTutorial(false)
    display.remove(r0_55.spr.orbImage)
    r0_55.spr.orbImage = nil
  end
end
local function r89_0(r0_56)
  -- line: [1261, 1286] id: 56
  for r6_56, r7_56 in pairs(r28_0) do
    local r8_56 = nil
    if r7_56.struct ~= nil then
      r8_56 = r7_56.struct
    else
      r8_56 = r7_56
    end
    local r9_56 = true
    if r0_56 == r13_0 then
      r9_56 = false
    end
    if r41_0(r8_56) == true and r40_0(r8_56) == false then
      r88_0(r8_56, r9_56)
    else
      r85_0(r8_56, r9_56)
    end
  end
end
local function r90_0()
  -- line: [1291, 1297] id: 57
  if r38_0 == nil then
    return 
  end
  display.remove(r38_0)
  r38_0 = nil
end
local function r91_0()
  -- line: [1302, 1307] id: 58
  local r0_58 = nil	-- notice: implicit variable refs by block#[0]
  r32_0 = r0_58
  r30_0 = r13_0
  r89_0(r13_0)
end
local function r92_0(r0_59, r1_59)
  -- line: [1312, 1318] id: 59
  r84_0(r0_59, r1_59, function()
    -- line: [1314, 1317] id: 60
    r83_0(r0_59)
  end)
end
local function r93_0(r0_61)
  -- line: [1323, 1340] id: 61
  if r56_0(r0_61.param, "targetEvoLevel") == false or r56_0(r0_61.param, "charStruct") == false then
    return 
  end
  r45_0(nil, false)
  r91_0()
  r92_0(r0_61.param.charStruct, r0_61.param.targetEvoLevel)
end
local function r94_0(r0_62, r1_62)
  -- line: [1345, 1405] id: 62
  local r2_62 = r46_0(r1_62)
  if r2_62 == nil then
    return 
  end
  local r3_62 = r47_0(r1_62)
  if r3_62 == nil then
    return 
  end
  local r4_62 = r44_0(r1_62)
  if r4_62 == nil then
    return 
  end
  local r5_62 = display.newGroup()
  local r6_62 = nil
  for r10_62 = 1, r3_62, 1 do
    local r11_62 = display.newGroup()
    local r12_62 = display.newGroup()
    local r13_62 = util.LoadParts(r12_62, r52_0("button_orb"), 0, 0)
    local r14_62 = r10_0.CreateImage(r12_62, r10_0.sequenceNames.TicketNum, r10_0.frameDefines.TicketNumMark, 31, 0)
    local r15_62 = r10_0.CreateImage(r12_62, r10_0.sequenceNames.TicketNum, r10_0.frameDefines.TicketNumStart + r10_62 * r4_62, 55, 0)
    r14_62:setReferencePoint(display.TopLeftReferencePoint)
    r15_62:setReferencePoint(display.TopLeftReferencePoint)
    r14_62.y = r13_62.height - r14_62.height - 5
    r15_62.y = r13_62.height - r15_62.height - 5
    r11_62:insert(r12_62)
    local r16_62 = display.newRoundedRect(0, 0, r12_62.width + 20, r12_62.height + 20, 10)
    r16_62:setFillColor(0, 0, 0)
    r16_62.alpha = 0.2
    r11_62:insert(1, r16_62)
    r12_62.x = r11_62.width * 0.5 - r12_62.width * 0.5
    r12_62.y = r11_62.height * 0.5 - r12_62.height * 0.5 + 2
    util.LoadBtnGroup({
      group = r11_62,
      cx = 0,
      cy = 0,
      func = r93_0,
      param = {
        targetEvoLevel = r10_62,
        charStruct = r1_62,
      },
      show = true,
    })
    r5_62:insert(r11_62)
    r11_62.x = (r10_62 - 1) * (r11_62.width + 10)
    r11_62:setReferencePoint(display.CenterReferencePoint)
    if r2_62 < r10_62 then
      r11_62.disable = true
      r13_62:setFillColor(128)
      r14_62:setFillColor(128)
      r15_62:setFillColor(128)
    end
  end
  r0_62:insert(r5_62)
  r0_62.balloonContent = r5_62
end
local function r95_0(r0_63, r1_63, r2_63)
  -- line: [1410, 1431] id: 63
  local r3_63 = util.LoadParts(r0_63, r52_0("orb_fukidasi_01"), 0, 0)
  local r4_63 = util.LoadParts(r0_63, r52_0("orb_fukidasi_02"), 0, 0)
  local r5_63 = r3_63.width * 0.5
  local r6_63 = r4_63.width * 0.5
  r3_63:setReferencePoint(display.TopLeftReferencePoint)
  r4_63:setReferencePoint(display.TopLeftReferencePoint)
  r4_63.x = r5_63 - r6_63
  if r1_63 == true then
    r3_63.y = r4_63.y + r4_63.height - 8
  else
    r4_63.yScale = -1
    r3_63.y = r4_63.y - r4_63.height - r3_63.height + 10
  end
  r0_63.balloon = r3_63
  r0_63.balloonTail = r4_63
end
local function r96_0(r0_64)
  -- line: [1436, 1474] id: 64
  r90_0()
  local r1_64 = false
  if r0_64.y < r19_0 then
    r1_64 = true
  end
  r38_0 = display.newGroup()
  r95_0(r38_0, r1_64, r0_64)
  local r2_64 = r38_0.width * 0.5
  if r1_64 == true then
    r38_0.x = r0_64.x - r38_0.width * 0.5
    r38_0.y = r0_64.y + 20
  else
    r38_0.x = r0_64.x - r38_0.width * 0.5
    r38_0.y = r0_64.y - 30
  end
  if r0_64.x - r2_64 < 0 then
    r38_0.x = 0
    r38_0.balloonTail.x = r0_64.x - r38_0.balloonTail.width * 0.5
  elseif _G.Width < r0_64.x + r2_64 then
    r38_0.x = _G.Width - r38_0.width
    r38_0.balloonTail.x = r38_0.width - r38_0.balloonTail.width * 0.5 - _G.Width - r0_64.x
  end
  r94_0(r38_0, r0_64)
  r38_0.balloonContent.x = r38_0.balloon.x + 25
  r38_0.balloonContent.y = r38_0.balloon.y + 18
end
function r40_0(r0_70)
  -- line: [1535, 1542] id: 70
  if r0_70 ~= nil and r56_0(r0_70.evo, "evIsStart") == true and r0_70.evo.evIsStart == true then
    return true
  end
  return false
end
function r47_0(r0_73)
  -- line: [1572, 1579] id: 73
  local r1_73 = r57_0(r0_73)
  if r1_73 == nil then
    return nil
  end
  return r1_73.GetEvoDataMax()
end
function r39_0(r0_74)
  -- line: [1584, 1591] id: 74
  local r1_74 = r57_0(r0_74)
  if r1_74 == nil then
    return nil
  end
  return r1_74.GetEvoSoundId(r0_74)
end
function r44_0(r0_75)
  -- line: [1596, 1622] id: 75
  local r1_75 = r57_0(r0_75)
  if r1_75 == nil then
    return nil
  end
  local r2_75 = 0
  if r56_0(r0_75.evo, "evoLevel") == true then
    r2_75 = r0_75.evo.evoLevel
  end
  if r2_75 == nil or r2_75 < 1 or r1_75.GetEvoDataMax() < r2_75 then
    r2_75 = 1
  end
  local r3_75 = r1_75.GetCharRank(r0_75.type)
  if r3_75 == nil then
    return nil
  end
  local r4_75 = r1_75.GetRankData(r3_75.rank)
  if r4_75 == nil then
    return nil
  end
  return r4_75.orb
end
function r46_0(r0_77)
  -- line: [1640, 1662] id: 77
  local r1_77 = r57_0(r0_77)
  if r1_77 == nil then
    return nil
  end
  local r2_77 = r1_77.GetCharRank(r0_77.type)
  if r2_77 == nil then
    return nil
  end
  local r3_77 = r1_77.GetRankData(r2_77.rank)
  if r3_77 == nil then
    return nil
  end
  if _G.IsDebug == true and _G.IsReleaseAllEvoLevel == true then
    return r47_0(r0_77)
  else
    return r3_77.evlv
  end
end
function r41_0(r0_78)
  -- line: [1667, 1674] id: 78
  local r1_78 = r57_0(r0_78)
  if r1_78 == nil then
    return nil
  end
  return r1_78.IsEvoChar()
end
function r42_0()
  -- line: [1741, 1747] id: 84
  if r36_0 == nil then
    return false
  end
  return r36_0.IsPlay()
end
function r45_0(r0_85, r1_85)
  -- line: [1752, 1759] id: 85
  if r1_85 == true and r0_85 ~= nil then
    r96_0(r0_85)
  else
    r90_0()
  end
end
function r43_0(r0_89, r1_89)
  -- line: [1796, 1857] id: 89
  if r29_0 == true or r0_89 == nil or r1_89 == nil or r28_0 == nil then
    return 
  end
  r30_0 = r0_89
  if r0_89 == r13_0 then
    r45_0(nil, false)
    r32_0 = nil
    game.Play()
    r89_0(r0_89)
    r74_0(os.time())
  else
    local r2_89 = game.GetPauseType()
    if r1_89 == r16_0 then
      game.Play()
    elseif r1_89 == r17_0 and game.IsPauseTypeNone() then
      game.Pause(r6_0.PauseTypeStopClockMenu, {
        [r6_0.PauseFuncMoveEnemy] = false,
        [r6_0.PauseFuncMoveChars] = false,
        [r6_0.PauseFuncEnablePlayPauseButton] = true,
        [r6_0.PauseFuncShowGuideBlink] = false,
        [r6_0.PauseFuncShowTicket] = false,
        [r6_0.PauseFuncEnableUseOrbButton] = true,
        [r6_0.PauseFuncClosePulldownMenu] = false,
      })
    end
    if _G.OrbManager.GetRemainOrb() < 1 then
      _G.OrbManager.ShowRecoveryOrbDialog(function(r0_90)
        -- line: [1840, 1851] id: 90
        if r0_90 == true then
          game.ViewPanel()
          r89_0(r0_89)
        else
          r43_0(r13_0, r16_0)
        end
      end)
    else
      r89_0(r0_89)
    end
  end
end
for r123_0, r124_0 in pairs({
  UseOrbModeReset = r13_0,
  UseOrbModeCharSelect = r14_0,
  UseOrbModeEvoLevelSelect = r15_0,
  UseOrbModePlayStatusPlay = r16_0,
  UseOrbModePlayStatusPause = r17_0,
  Init = function(r0_65)
    -- line: [1479, 1489] id: 65
    local r1_65 = nil	-- notice: implicit variable refs by block#[0]
    r38_0 = r1_65
    r28_0 = nil
    r1_65 = r0_65.myChar
    if r1_65 ~= nil then
      r1_65 = r0_65.myChar
      r28_0 = r1_65
    end
    r29_0 = false
    r5_0.Init()
  end,
  SetEvo = function(r0_66, r1_66)
    -- line: [1494, 1496] id: 66
  end,
  SetEvoEndFunc = function(r0_67, r1_67)
    -- line: [1501, 1503] id: 67
    r62_0(r0_67, r1_67)
  end,
  RemoveEvoEndFunc = function(r0_68)
    -- line: [1508, 1515] id: 68
    if r0_68 == nil or r56_0(r0_68.evo, "endFunc") == false then
      return 
    end
    r0_68.evo.endFunc = nil
  end,
  IsEvoLevelMax = function(r0_69)
    -- line: [1520, 1530] id: 69
    local r1_69 = r57_0(r0_69)
    if r56_0(r0_69, "evo") == true and r0_69.evo.evoLevel ~= nil and r1_69 ~= nil and r1_69.GetEvoDataMax() <= r0_69.evo.evoLevel then
      return true
    end
    return false
  end,
  IsStartEvo = r40_0,
  GetEvoTimerEvent = function(r0_71)
    -- line: [1547, 1555] id: 71
    if r0_71 == nil or r0_71.evo == nil or r0_71.evo.ev == nil then
      return nil
    end
    return r0_71.evo.ev
  end,
  GetEvoLevel = function(r0_72)
    -- line: [1560, 1567] id: 72
    if r0_72 == nil or r56_0(r0_72.evo, "evoLevel") == false then
      return 0
    end
    return r0_72.evo.evoLevel
  end,
  GetEvoLevelMax = r47_0,
  GetEvoSound = r39_0,
  GetEvoCost = r44_0,
  GetCharRank = function(r0_76)
    -- line: [1627, 1635] id: 76
    local r1_76 = r57_0(r0_76)
    if r1_76 == nil then
      return nil
    end
    return r1_76.GetCharRank(r0_76.type).rank
  end,
  GetCharRank2EvoLevel = r46_0,
  IsEvoChar = r41_0,
  UpdateEvoAbility = function(r0_79)
    -- line: [1679, 1686] id: 79
    if r56_0(r0_79, "evo") == false or r56_0(r0_79.evo, "evoLevel") == false then
      return 
    end
    r67_0(r0_79, r0_79.evo.evoLevel)
  end,
  AddEvoDurationTime = function(r0_80, r1_80)
    -- line: [1691, 1693] id: 80
    r68_0(r0_80, r1_80)
  end,
  AddEvoDropRate = function(r0_81, r1_81)
    -- line: [1698, 1700] id: 81
    r69_0(r0_81, r1_81)
  end,
  CreateCharStruct = function(r0_82)
    -- line: [1706, 1714] id: 82
    local r1_82 = {
      type = r0_82,
    }
    r66_0(r1_82)
    return r1_82
  end,
  ReleaseEvo = function(r0_83)
    -- line: [1719, 1736] id: 83
    r58_0(r0_83)
    r71_0(r0_83)
    r60_0(r0_83)
    r0_83.evo = nil
    r5_0.RemoveChar(r0_83)
  end,
  IsStringEffectPlay = r42_0,
  ShowEvoBalloon = r45_0,
  SetSystemPause = function(r0_86)
    -- line: [1764, 1776] id: 86
    r29_0 = r0_86
    if r0_86 == false then
      r30_0 = r13_0
      r89_0(r13_0)
      r90_0()
      r74_0(os.time())
    end
  end,
  SetUseOrbCharSelectModeCancelRect = function(r0_87)
    -- line: [1781, 1783] id: 87
    r31_0 = r0_87
  end,
  GetUseOrbMode = function()
    -- line: [1788, 1790] id: 88
    return r30_0
  end,
  SetUseOrbMode = r43_0,
  SelectEvoChar = function(r0_91, r1_91)
    -- line: [1862, 1884] id: 91
    if r30_0 ~= r14_0 or r41_0(r0_91) == false or r40_0(r0_91) == true then
      return false
    end
    if r46_0(r0_91) < 2 then
      r91_0()
      r92_0(r0_91, 1)
    else
      r30_0 = r15_0
      r32_0 = r1_91
      r45_0(r0_91, true)
    end
    return true
  end,
  CancelUseOrbModeCharSelect = function(r0_92, r1_92)
    -- line: [1889, 1906] id: 92
    if r30_0 ~= r14_0 then
      return false
    end
    if r31_0.x <= r0_92 and r0_92 <= r31_0.x + r31_0.width and r31_0.y <= r1_92 and r1_92 <= r31_0.y + r31_0.height then
      return false
    end
    r43_0(r13_0, r16_0)
    return true
  end,
  CancelUseOrbModeEvoLevelSelect = function(r0_93, r1_93)
    -- line: [1911, 1928] id: 93
    if r30_0 ~= r15_0 or r32_0 == nil or r0_93 < 1 or r1_93 < 1 then
      return false
    end
    if r32_0[1] == r0_93 and r32_0[2] == r1_93 then
      return false
    end
    r43_0(r13_0, r16_0)
    return true
  end,
  CreateUseOrbIconNumber = function(r0_94, r1_94)
    -- line: [1933, 1935] id: 94
    return r87_0(r0_94, r1_94)
  end,
  CreateCostUseOrbIconNumber = function(r0_95, r1_95)
    -- line: [1940, 1942] id: 95
    return r86_0(r0_95, r1_95)
  end,
  Clean = function()
    -- line: [1947, 1949] id: 96
    r5_0.Clean()
  end,
}) do
  r0_0[r123_0] = r124_0
end
return r0_0
