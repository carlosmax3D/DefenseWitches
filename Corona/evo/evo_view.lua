-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("tool.crystal")
local r1_0 = require("evo.evo_summon_plate")
local r2_0 = require("tool.tutorial_manager")
local r3_0 = require("stage_ui.stage_help_manager")
local r4_0 = require("logic.pay_item_data")
local r5_0 = require("json")
local r6_0 = require("resource.char_define")
local r7_0 = 50
local r8_0 = require("common.sprite_loader")
local r9_0 = r8_0.new({
  imageSheet = "common.sprites.sprite_parts01",
})
local r10_0 = r8_0.new({
  imageSheet = "common.sprites.sprite_number01",
})
local r11_0 = r8_0.new({
  imageSheet = "common.sprites.sprite_number02",
})
local r12_0 = r8_0.new({
  imageSheet = "common.sprites.sprite_number03",
})
local r13_0 = r8_0.new({
  imageSheet = "common.sprites.sprite_number04",
})
local r14_0 = r8_0.new({
  imageSheet = "common.sprites.sprite_number05",
})
local r15_0 = require("evo.char_tbl.tbl_manager")
local r16_0 = nil
local r17_0 = {}
local r18_0 = {}
local r19_0 = nil
local r20_0 = nil
local r21_0 = nil
local r22_0 = nil
local r23_0 = nil
local r24_0 = {}
local r25_0 = nil
local r26_0 = nil
local r27_0 = nil
local r28_0 = nil
local r29_0 = nil
local r30_0 = 0
local r31_0 = nil
local r32_0 = nil
local r33_0 = nil
local r34_0 = nil
local r35_0 = nil
local r36_0 = false
local r37_0 = 9
local r38_0 = nil
local r39_0 = nil
local r40_0 = nil
local r41_0 = nil
local r42_0 = nil
local function r43_0(r0_1)
  -- line: [63, 63] id: 1
  return "data/option/" .. r0_1 .. ".png"
end
local function r44_0(r0_2, r1_2)
  -- line: [65, 65] id: 2
  return string.format("data/map/%s/%s.png", r0_2, r1_2)
end
local function r45_0(r0_3)
  -- line: [67, 67] id: 3
  return "data/evo/rankup/" .. r0_3 .. ".png"
end
local function r46_0(r0_4)
  -- line: [69, 69] id: 4
  return r45_0(r0_4 .. _G.UILanguage)
end
local function r47_0(r0_5)
  -- line: [71, 71] id: 5
  return "data/evo/result/" .. r0_5 .. ".png"
end
local function r48_0(r0_6)
  -- line: [73, 73] id: 6
  return r47_0(r0_6 .. _G.UILanguage)
end
local function r49_0()
  -- line: [78, 104] id: 7
  if r33_0 ~= nil then
    if r33_0.enter_frame_func ~= nil then
      Runtime:removeEventListener("enterFrame", r33_0.enter_frame_func)
    end
    r33_0.clean()
    r33_0 = nil
  end
  if r34_0 ~= nil then
    if r34_0.enter_frame_func ~= nil then
      Runtime:removeEventListener("enterFrame", r34_0.enter_frame_func)
    end
    r34_0.clean()
    r34_0 = nil
  end
  if r1_0 ~= nil then
    r1_0.Cleanup()
  end
  if r16_0 ~= nil then
    display.remove(r16_0)
    r16_0 = nil
  end
end
local function r50_0(r0_8)
  -- line: [109, 113] id: 8
  local r1_8 = require("stage_ui.stage_help")
  r1_8.Init()
  r1_8.Open(r0_8)
end
local function r51_0()
  -- line: [118, 128] id: 9
  r3_0.Init()
  if r3_0.IsFirstRankUpRun() == true and _G.ServerStatus.leveluphelp == 1 then
    r3_0.SetFirstRankUpRun()
    r50_0(8)
  end
end
local function r52_0(r0_10)
  -- line: [133, 136] id: 10
  sound.PlaySE(1)
  r50_0(9)
end
local function r53_0(r0_11)
  -- line: [141, 154] id: 11
  sound.PlaySE(2)
  r2_0.LevelUpTutorial(false)
  ExpManager.SaveExp()
  r49_0()
  db.SaveToServer2(_G.UserToken)
  util.ChangeScene({
    prev = r38_0,
    scene = r35_0,
    efx = "crossfade",
  })
end
local function r54_0(r0_12)
  -- line: [157, 169] id: 12
  local r2_12 = r15_0.GetRankupSoundId(r0_12)
  local r3_12 = sound.GetCharVoicePath(r18_0.sid, _G.GameData.language)
  if r3_12 == nil then
    return 
  end
  r3_12 = sound.GetCharVoiceFilename(r3_12, r2_12)
  sound.StopVoice(30)
  sound.PlayVoice(r3_12, 30)
end
local function r55_0(r0_13, r1_13, r2_13, r3_13)
  -- line: [174, 194] id: 13
  local r4_13 = 12
  local r5_13 = tonumber(r1_13)
  local r6_13 = "000"
  local r7_13 = string.len(r6_13)
  local r8_13 = r2_13 + string.len(r6_13) * r4_13
  while 0 < r7_13 do
    r5_13 = math.floor(r5_13 * 0.1)
    r10_0.CreateImage(r0_13, r10_0.sequenceNames.Score, r10_0.frameDefines.ScoreStart + r5_13 % 10, r8_13, r3_13)
    r8_13 = r8_13 - r4_13
    r7_13 = r7_13 - 1
  end
end
local function r56_0(r0_14, r1_14, r2_14, r3_14)
  -- line: [199, 218] id: 14
  local r4_14 = 16
  local r5_14 = tonumber(r1_14)
  local r6_14 = 5
  local r7_14 = r2_14 + string.len(tostring(r6_14)) * r4_14
  while 0 < r6_14 do
    r5_14 = math.floor(r5_14 * 0.1)
    r10_0.CreateImage(r0_14, r10_0.sequenceNames.MpNumberA, r10_0.frameDefines.MpNumberAStart + r5_14 % 10, r7_14, r3_14)
    r7_14 = r7_14 - r4_14
    r6_14 = r6_14 - 1
  end
end
local function r57_0(r0_15, r1_15, r2_15, r3_15)
  -- line: [223, 240] id: 15
  local r4_15 = 48
  local r5_15 = tonumber(r1_15)
  local r6_15 = r2_15 + string.len(tostring(r1_15)) * r4_15
  if r5_15 < 1 then
    r11_0.CreateImage(r0_15, r11_0.sequenceNames.Wave, r11_0.frameDefines.WaveStart, r6_15, r3_15)
    return 
  end
  while 0 < r5_15 do
    r5_15 = math.floor(r5_15 * 0.1)
    r11_0.CreateImage(r0_15, r11_0.sequenceNames.Wave, r11_0.frameDefines.WaveStart + r5_15 % 10, r6_15, r3_15)
    r6_15 = r6_15 - r4_15
  end
end
local function r58_0(r0_16, r1_16, r2_16, r3_16)
  -- line: [245, 265] id: 16
  local r4_16 = 24
  local r5_16 = tonumber(r1_16)
  local r6_16 = "0000000"
  local r7_16 = string.len(r6_16)
  local r8_16 = r2_16 + string.len(r6_16) * r4_16
  while 0 < r7_16 do
    r5_16 = math.floor(r5_16 * 0.1)
    r12_0.CreateImage(r0_16, r12_0.sequenceNames.TicketNum, r12_0.frameDefines.TicketNumStart + r5_16 % 10, r8_16, r3_16)
    r8_16 = r8_16 - r4_16
    r7_16 = r7_16 - 1
  end
end
local function r59_0(r0_17, r1_17, r2_17, r3_17)
  -- line: [270, 292] id: 17
  local r4_17 = 64
  local r5_17 = tonumber(r1_17)
  local r6_17 = 2
  local r7_17 = r2_17 + string.len(tostring(r6_17)) * r4_17
  if r5_17 < 1 then
    r13_0.CreateImage(r0_17, r13_0.sequenceNames.RankNum, r13_0.frameDefines.RankNumStart, r7_17, r3_17)
    return 
  end
  local r8_17 = r6_17
  while 0 < r8_17 do
    local r9_17 = r5_17 % 10
    r5_17 = math.floor(r5_17 * 0.1)
    if 0 < r9_17 or r9_17 == 0 and r8_17 == r6_17 then
      r13_0.CreateImage(r0_17, r13_0.sequenceNames.RankNum, r13_0.frameDefines.RankNumStart + r9_17, r7_17, r3_17)
    end
    r7_17 = r7_17 - r4_17
    r8_17 = r8_17 - 1
  end
end
local function r60_0(r0_18, r1_18, r2_18, r3_18)
  -- line: [294, 311] id: 18
  local r4_18 = 64
  local r5_18 = tonumber(r1_18)
  local r6_18 = r2_18 + string.len(tostring(r1_18)) * r4_18
  if r5_18 < 1 then
    r13_0.CreateImage(r0_18, r13_0.sequenceNames.RankNum, r13_0.frameDefines.RankNumStart, r6_18, r3_18)
    return 
  end
  while 0 < r5_18 do
    r5_18 = math.floor(r5_18 * 0.1)
    r13_0.CreateImage(r0_18, r13_0.sequenceNames.RankNum, r13_0.frameDefines.RankNumStart + r5_18 % 10, r6_18, r3_18)
    r6_18 = r6_18 - r4_18
  end
end
local function r61_0(r0_19, r1_19, r2_19, r3_19, r4_19)
  -- line: [316, 337] id: 19
  local r5_19 = 22
  local r6_19 = tonumber(r1_19)
  local r7_19 = r4_19
  local r8_19 = r2_19 + string.len(tostring(r7_19)) * r5_19
  while 0 < r7_19 do
    r6_19 = math.floor(r6_19 * 0.1)
    r14_0.CreateImage(r0_19, r14_0.sequenceNames.RankNum, r14_0.frameDefines.RankNumStart + r6_19 % 10, r8_19, r3_19)
    r8_19 = r8_19 - r5_19
    r7_19 = r7_19 - 1
  end
end
local function r62_0(r0_20, r1_20, r2_20, r3_20)
  -- line: [339, 356] id: 20
  local r4_20 = 22
  local r5_20 = tonumber(r1_20)
  local r6_20 = r2_20 + string.len(tostring(r1_20)) * r4_20
  if r5_20 < 1 then
    r14_0.CreateImage(r0_20, r14_0.sequenceNames.RankNum, r14_0.frameDefines.RankNumStart, r6_20, r3_20)
    return 
  end
  while 0 < r5_20 do
    r5_20 = math.floor(r5_20 * 0.1)
    r14_0.CreateImage(r0_20, r14_0.sequenceNames.RankNum, r14_0.frameDefines.RankNumStart + r5_20 % 10, r6_20, r3_20)
    r6_20 = r6_20 - r4_20
  end
end
local function r63_0()
  -- line: [361, 375] id: 21
  if r18_0.rank ~= nil then
    local r0_21 = r4_0.pay_item_data.BuyExp
    return {
      {
        r0_21,
        1,
        r4_0.getItemData(r0_21)[2]
      }
    }
  else
    return nil
  end
end
local function r64_0(r0_22, r1_22, r2_22, r3_22)
  -- line: [380, 392] id: 22
  local r5_22 = r1_22
  local r6_22 = 1
  local r7_22 = r3_22 - 40 / r6_22
  r9_0.CreateImage(r0_22, r9_0.sequenceNames.ExpFrame, r9_0.frameDefines.ExpFrameLeft, r5_22, r2_22)
  r5_22 = r5_22 + 20
  for r11_22 = 1, r7_22, 1 do
    r9_0.CreateImage(r0_22, r9_0.sequenceNames.ExpFrame, r9_0.frameDefines.ExpFrameCenter, r5_22, r2_22)
    r5_22 = r5_22 + r6_22
  end
  r9_0.CreateImage(r0_22, r9_0.sequenceNames.ExpFrame, r9_0.frameDefines.ExpFrameRight, r5_22, r2_22)
end
local function r65_0()
  -- line: [397, 408] id: 23
  local r0_23 = 340
  local r1_23 = 300
  r0_23 = r0_23 + util.LoadParts(rtImg, r44_0("01", "obj011"), r0_23, r1_23).width
  for r6_23 = 1, 5, 1 do
    r0_23 = r0_23 + util.LoadParts(rtImg, r44_0("01", "obj003"), r0_23, r1_23).width
  end
  util.LoadParts(rtImg, r44_0("01", "obj013"), r0_23, r1_23)
end
local function r66_0(r0_24, r1_24, r2_24)
  -- line: [413, 416] id: 24
  r56_0(r1_24.imgGrp, r2_24, r1_24.x, r1_24.y)
  r0_24:insert(r1_24.imgGrp)
end
local function r67_0(r0_25, r1_25, r2_25, r3_25)
  -- line: [421, 424] id: 25
  r61_0(r1_25.imgGrp, r2_25, r1_25.x, r1_25.y, r3_25)
  r0_25:insert(r1_25.imgGrp)
end
local function r68_0(r0_26, r1_26, r2_26)
  -- line: [426, 429] id: 26
  r62_0(r1_26.imgGrp, r2_26, r1_26.x, r1_26.y)
  r0_26:insert(r1_26.imgGrp)
end
local function r69_0(r0_27, r1_27, r2_27)
  -- line: [434, 437] id: 27
  r55_0(r1_27.imgGrp, r2_27, r1_27.x, r1_27.y)
  r0_27:insert(r1_27.imgGrp)
end
local function r70_0(r0_28, r1_28, r2_28)
  -- line: [442, 445] id: 28
  r58_0(r1_28.imgGrp, r2_28, r1_28.x, r1_28.y)
  r0_28:insert(r1_28.imgGrp)
end
local function r71_0(r0_29, r1_29, r2_29)
  -- line: [450, 489] id: 29
  local r3_29 = r15_0.getRankTable(r2_29.sid)
  local r4_29 = r3_29.GetRankDataMax()
  local r5_29 = 1
  local r6_29 = ""
  local r7_29 = ""
  local r8_29 = nil
  r7_29 = util.MakeText(20, {
    38,
    29,
    15
  }, {
    242,
    234,
    218
  }, db.GetMessage(367), r1_29.w + 20, 25)
  r1_29.imgGrp:insert(r7_29)
  r7_29.x = r1_29.x
  r7_29.y = r1_29.y + (r5_29 - 1) * 20
  r0_29:insert(r1_29.imgGrp)
  r5_29 = r5_29 + 1
  for r12_29 = 1, r4_29, 1 do
    local r13_29 = r3_29.GetRank2EffectString(r12_29)
    if 1 < r12_29 and r13_29 ~= nil then
      local r14_29 = ""
      if r12_29 < 10 then
        r14_29 = "Lv0" .. tostring(r12_29) .. ":"
      else
        r14_29 = "Lv" .. tostring(r12_29) .. ":"
      end
      r14_29 = r14_29 .. r13_29
      if r12_29 <= r2_29.rank then
        r8_29 = util.MakeText(20, {
          38,
          29,
          15
        }, {
          242,
          234,
          218
        }, r14_29, r1_29.w + 100, 25)
      else
        r8_29 = util.MakeText(20, {
          127,
          127,
          127
        }, {
          242,
          234,
          218
        }, r14_29, r1_29.w + 100, 25)
      end
      r1_29.imgGrp:insert(r8_29)
      r8_29:setReferencePoint(display.TopLeftReferencePoint)
      r8_29.x = r1_29.x
      r8_29.y = r1_29.y + (r5_29 - 1) * 20
      r0_29:insert(r1_29.imgGrp)
      r5_29 = r5_29 + 1
    end
  end
end
local function r72_0(r0_30, r1_30, r2_30)
  -- line: [494, 495] id: 30
end
local function r73_0()
  -- line: [497, 633] id: 31
  local r0_31 = require("common.particles.pixieDust").new
  local r1_31 = {}
  local r2_31 = {
    file = "data/sprites/twinkle01.png",
    options = {
      width = 30,
      height = 30,
      numFrames = 10,
      sheetContentWidth = 60,
      sheetContentHeight = 150,
    },
  }
  r2_31.sequence = {
    name = "prism",
    frames = {
      1,
      2
    },
    start = 1,
    count = 2,
    time = 3000,
  }
  r1_31.particleImage = r2_31
  r1_31.baseX = 0
  r1_31.baseY = 0
  r1_31.flowX = -0.5
  r1_31.flowY = 2
  r1_31.particleMax = 5
  r1_31.sizeStart = 24
  r1_31.sizeEnd = 40
  r1_31.moveXStart = -50
  r1_31.moveXEnd = 50
  r1_31.moveYStart = -100
  r1_31.moveYEnd = 60
  r1_31.accelerateXStart = -6
  r1_31.accelerateXEnd = 6
  r1_31.accelerateYStart = -6
  r1_31.accelerateYEnd = 6
  r1_31.lifetimeStart = 20
  r1_31.lifetimeEnd = 40
  r1_31.alphaStepStart = 1
  r1_31.alphaStepEnd = 10
  r1_31.twistSpeedStart = 1
  r1_31.twistSpeedEnd = 10
  r1_31.twistRadiusStart = 1
  r1_31.twistRadiusEnd = 5
  r1_31.twistAngleStart = 0
  r1_31.twistAngleEnd = 359
  r1_31.twistAngleStepStart = 1
  r1_31.twistAngleStepEnd = 10
  r33_0 = r0_31(r1_31)
  r0_31 = require("common.particles.pixieDust").new
  r1_31 = {}
  r2_31 = {
    file = "data/sprites/twinkle01.png",
    options = {
      width = 30,
      height = 30,
      numFrames = 10,
      sheetContentWidth = 60,
      sheetContentHeight = 150,
    },
  }
  r2_31.sequence = {
    name = "prism",
    frames = {
      9,
      10
    },
    start = 9,
    count = 2,
    time = 200,
  }
  r1_31.particleImage = r2_31
  r1_31.baseX = 0
  r1_31.baseY = 0
  r1_31.flowX = -0.5
  r1_31.flowY = 2
  r1_31.particleMax = 5
  r1_31.sizeStart = 24
  r1_31.sizeEnd = 40
  r1_31.moveXStart = -50
  r1_31.moveXEnd = 50
  r1_31.moveYStart = -100
  r1_31.moveYEnd = 60
  r1_31.accelerateXStart = -6
  r1_31.accelerateXEnd = 6
  r1_31.accelerateYStart = -6
  r1_31.accelerateYEnd = 6
  r1_31.lifetimeStart = 10
  r1_31.lifetimeEnd = 20
  r1_31.alphaStepStart = 1
  r1_31.alphaStepEnd = 10
  r1_31.twistSpeedStart = 1
  r1_31.twistSpeedEnd = 10
  r1_31.twistRadiusStart = 1
  r1_31.twistRadiusEnd = 5
  r1_31.twistAngleStart = 0
  r1_31.twistAngleEnd = 359
  r1_31.twistAngleStepStart = 1
  r1_31.twistAngleStepEnd = 10
  r34_0 = r0_31(r1_31)
end
local function r74_0(r0_32)
  -- line: [636, 701] id: 32
  local r7_32 = nil	-- notice: implicit variable refs by block#[2]
  if r0_32 == false then
    return 
  end
  local r1_32 = _G.Width * 0.5
  local r2_32 = _G.Height * 0.5
  local r3_32 = 100
  local r4_32 = 40
  r34_0.baseX = r1_32
  r34_0.baseY = r2_32
  r34_0.particleMax = 2
  r34_0.sizeStart = 24
  r34_0.sizeEnd = 40
  r34_0.moveXStart = -40
  r34_0.moveXEnd = 40
  r34_0.moveYStart = -40
  r34_0.moveYEnd = 40
  r34_0.lifetimeStart = 1
  r34_0.lifetimeEnd = 70
  r34_0.alphaStepStart = 1
  r34_0.alphaStepEnd = 10
  local r5_32 = 0
  local r6_32 = 40
  function r7_32(r0_33)
    -- line: [671, 696] id: 33
    r5_32 = r5_32 + 1
    if r6_32 <= r5_32 then
      Runtime:removeEventListener("enterFrame", r7_32)
      r34_0.enter_frame_func = nil
      r34_0.stop()
      r42_0()
      return false
    end
    local r2_33 = util.CalcQuadraticBezPoint({
      x = r1_32,
      y = r2_32,
    }, {
      x = r1_32,
      y = r4_32,
    }, {
      x = r3_32,
      y = r4_32,
    }, r5_32 / r6_32)
    r34_0.baseX = r2_33.x
    r34_0.baseY = r2_33.y
    return true
  end
  r34_0.play()
  Runtime:addEventListener("enterFrame", r7_32)
  r34_0.enter_frame_func = r7_32
end
local function r75_0(r0_34, r1_34, r2_34, r3_34, r4_34)
  -- line: [703, 724] id: 34
  local r5_34 = display.newGroup()
  local r6_34 = nil
  local r7_34 = nil
  local r8_34 = 0
  for r12_34, r13_34 in pairs(r3_34) do
    r6_34 = string.format(db.GetMessage(r13_34), r4_34)
    display.newText(r5_34, r6_34, 1, r8_34 + 1, native.systemFontBold, 24):setFillColor(0, 0, 0)
    r7_34 = display.newText(r5_34, r6_34, 0, r8_34, native.systemFontBold, 24)
    r7_34:setFillColor(255, 255, 255)
    r8_34 = r8_34 + 36
  end
  r0_34:insert(r5_34)
  r5_34:setReferencePoint(display.TopLeftReferencePoint)
  r5_34.x = r1_34
  r5_34.y = r2_34
  r5_34.isVisible = true
  return r5_34
end
local function r76_0(r0_35, r1_35, r2_35)
  -- line: [726, 747] id: 35
  local r3_35 = 0
  local r4_35 = nil
  local r5_35 = {}
  local r6_35 = nil
  r1_35 = string.gsub(r1_35, "(\\n)", function(r0_36)
    -- line: [733, 733] id: 36
    return "\n"
  end)
  r1_35 = util.StringSplit(r1_35, "\n")
  for r10_35, r11_35 in pairs(r1_35) do
    r6_35 = display.newText(r11_35, 0, 0, native.SystemFont, r2_35)
    r4_35 = r6_35.width
    if r3_35 < r4_35 then
      r3_35 = r4_35
    end
    table.insert(r5_35, r6_35)
  end
  return r3_35, r5_35
end
local function r77_0(r0_37, r1_37, r2_37, r3_37, r4_37)
  -- line: [749, 760] id: 37
  local r5_37 = nil	-- notice: implicit variable refs by block#[3]
  if type(r1_37) == "string" then
    r5_37 = display.newText(r0_37, r1_37, 0, 0, native.systemFont, r4_37)
  else
    r5_37 = r1_37
    r0_37:insert(r1_37)
  end
  r5_37.x = r2_37 / 2
  r5_37.y = r3_37 + r5_37.height / 2
  r5_37:setFillColor(255, 255, 255)
end
local function r78_0(r0_38, r1_38, r2_38, r3_38)
  -- line: [762, 810] id: 38
  local r4_38 = display.newGroup()
  local r5_38 = ""
  if r0_38 ~= nil then
    r5_38 = db.GetMessage(r0_38)
  end
  local r6_38 = nil
  local r7_38 = nil
  r6_38, r7_38 = r76_0(r4_38, r1_38, 24)
  local r9_38 = 64 + r6_38 + 64
  local r10_38 = 128 + 40 * 1 + 62
  util.LoadParts(r4_38, "data/cdn/download_win.png", 0, 0)
  local r11_38 = r4_38.width
  local r12_38 = r4_38.height
  r77_0(r4_38, r5_38, r11_38, 56, 32)
  for r16_38, r17_38 in pairs(r7_38) do
    r77_0(r4_38, r17_38, r11_38, 128 + 40 * (r16_38 - 1), 24)
  end
  local r13_38 = r11_38 / 2
  local r14_38 = r12_38 / 2 + 50
  if r3_38 == 1 then
    util.LoadBtn({
      rtImg = r4_38,
      fname = CD2("download_"),
      x = r13_38 - 8 - 112,
      y = r14_38,
      func = r2_38[1],
      param = r2_38[3],
    })
    util.LoadBtn({
      rtImg = r4_38,
      fname = r43_0("close"),
      x = 566,
      y = -12,
      func = r2_38[2],
      param = r2_38[4],
    })
  elseif r3_38 == 2 or r3_38 == 3 then
    util.LoadBtn({
      rtImg = r4_38,
      fname = "data/stage/ok_en.png",
      x = r13_38 - 8 - 112,
      y = r14_38,
      func = r2_38[1],
      param = r2_38[3],
    })
  end
  local r15_38 = util.LoadParts(r4_38, "data/login_bonus/receive/login_bonus_title_received.png", 0, 0)
  r15_38.x = 58
  r15_38.y = 42
  return r4_38
end
local function r79_0()
  -- line: [812, 822] id: 39
  if r31_0 then
    display.remove(r31_0)
    r31_0 = nil
  end
  if r32_0 then
    display.remove(r32_0)
    r32_0 = nil
  end
end
local function r80_0(r0_40, r1_40, r2_40, r3_40)
  -- line: [824, 839] id: 40
  sound.PlaySound("se28")
  r31_0 = util.MakeMat(r0_40)
  r32_0 = r78_0(r1_40, r2_40, r3_40, 2)
  r0_40:insert(r32_0)
  r32_0:setReferencePoint(display.CenterReferencePoint)
  r32_0.x = _G.Width / 2
  r32_0.y = _G.Height / 2
end
local function r81_0(r0_41, r1_41)
  -- line: [841, 1046] id: 41
  if r0_41 == nil then
    r0_41 = 0
  end
  if r1_41 == nil then
    r1_41 = 0
  end
  local r2_41 = r18_0.sid
  r18_0 = db.LoadCharRank(_G.UserID, r2_41)
  local r3_41 = r16_0
  local r4_41 = r18_0.rank
  local r6_41 = r4_41 + 1
  local r7_41 = r15_0.getRankTable(r2_41)
  local r8_41 = r18_0.exp + r7_41.GetRankData(r6_41).exp
  local r9_41 = r7_41.GetRank2EffectString(r6_41)
  local r10_41 = display.newGroup()
  local r11_41 = util.LoadParts(r10_41, r45_0("text_levelup"), 0, 0)
  local r12_41 = util.LoadParts(r10_41, r45_0("text_rankup_arrow"), 0, 0)
  local r13_41 = r11_41.contentHeight + 30
  r12_41.x = r11_41.contentWidth / 2 - r12_41.contentWidth / 2
  r12_41.y = r13_41 + 38 - r12_41.contentHeight / 2
  r59_0(r10_41, r4_41, 46, r13_41)
  r60_0(r10_41, r6_41, r12_41.x + r12_41.contentWidth - 40, r13_41)
  r10_41.x = -r11_41.contentWidth
  r10_41.y = _G.Height / 2 - r10_41.contentHeight / 2
  r10_41.alpha = 0
  local r14_41 = display.newRect(r3_41, 0, 0, _G.Width, _G.Height)
  r14_41:setFillColor(0, 0, 0, 128)
  r14_41:addEventListener("touch", function(r0_42)
    -- line: [877, 879] id: 42
    return true
  end)
  r3_41:insert(r10_41)
  local r16_41 = display.newGroup()
  function r16_41.enterFrame(r0_43, r1_43)
    -- line: [889, 906] id: 43
    if r0_43.startTime == 0 then
      r0_43.startTime = r1_43.time
    end
    local r2_43 = r1_43.time - r0_43.startTime
    if r0_43.endTime <= r2_43 then
      Runtime:removeEventListener("enterFrame", r16_41)
      display.remove(r16_41)
      r16_41 = nil
      r33_0.stop()
      return false
    end
    r33_0.baseX = easing.linear(r2_43, r0_43.endTime, r0_43.startPosX, r0_43.endPosX)
    return true
  end
  if _G.GameData.voice then
    if 1 < r6_41 and r9_41 ~= nil then
      r54_0(r15_0.RankupSoundTypeGetSkill)
    else
      r54_0(r15_0.RankupSoundTypeRankup)
    end
  end
  transition.from(r14_41, {
    time = 500,
    alpha = 0.5,
    onComplete = function(r0_44)
      -- line: [917, 1041] id: 44
      local r2_44 = _G.Width / 2 - r11_41.contentWidth / 2
      transition.to(r10_41, {
        time = 200,
        x = r2_44,
        y = r10_41.y,
        onComplete = function(r0_45)
          -- line: [919, 1025] id: 45
          transition.to(r10_41, {
            time = 1000,
            onComplete = function(r0_46)
              -- line: [921, 1020] id: 46
              transition.to(r10_41, {
                time = 150,
                x = _G.Width,
                onComplete = function(r0_47)
                  -- line: [923, 1014] id: 47
                  transition.to(r14_41, {
                    time = 500,
                    alpha = 0,
                    onComplete = function(r0_48)
                      -- line: [925, 1009] id: 48
                      r36_0 = false
                      r1_0.UpdatePlateNumber(r2_41, r6_41, r8_41)
                      local r1_48 = r7_41.GetRankData(r6_41).omx
                      local r2_48 = false
                      if OrbManager.GetRemainOrb() < OrbManager.GetMaxOrb() or 0 < r1_48 then
                        r2_48 = true
                      end
                      OrbManager.SetRecoveryOrb(OrbManager.GetRecoveryTypeRankup(), 0, r1_48, nil)
                      local function r3_48(r0_49)
                        -- line: [937, 941] id: 49
                        r79_0()
                        r74_0(r2_48)
                      end
                      local r4_48 = false
                      if 1 < r6_41 and r9_41 ~= nil then
                        if UILanguage == "jp" then
                          r9_41 = r9_41 .. db.GetMessage(365)
                        else
                          r9_41 = db.GetMessage(365) .. " " .. r9_41
                        end
                        r80_0(display.newGroup(), nil, r9_41, {
                          r3_48
                        })
                        r4_48 = true
                      end
                      ExpManager.LoadExp()
                      local r5_48 = tonumber(ExpManager.GetExp())
                      if r0_41 == 0 then
                        r5_48 = r5_48 - r7_41.GetRankData(r6_41).exp
                        if r5_48 < 0 then
                          r5_48 = 0
                        end
                        ExpManager.SetExp(r5_48)
                        ExpManager.SaveExp()
                        r39_0()
                        r40_0()
                        statslog.LogSend("exp_rankup", {
                          item = r2_41,
                          have_orb = OrbManager.GetRemainOrb(),
                          max_orb = OrbManager.GetMaxOrb(),
                          rank = r6_41,
                          have_exp = r5_48,
                        })
                        _G.metrics.character_level_up(r2_41, r4_41)
                      else
                        statslog.LogSend("crystal_rankup", {
                          item = r2_41,
                          count = r6_41,
                          coin = r1_41,
                          have_orb = OrbManager.GetRemainOrb(),
                          max_orb = OrbManager.GetMaxOrb(),
                          rank = r6_41,
                          have_exp = r5_48,
                        })
                      end
                      if r4_48 == false then
                        r74_0(r2_48)
                      end
                      db.SaveCharRank(_G.UserID, r2_41, r6_41, r8_41)
                      if r6_41 ~= r7_41.GetRankDataMax() and r5_48 < r7_41.GetRankData(r6_41 + 1).exp then
                        r41_0(false)
                      end
                    end,
                  })
                end,
              })
              transition.to(r10_41, {
                time = 200,
                alpha = 0,
              })
            end,
          })
        end,
      })
      transition.to(r10_41, {
        time = 300,
        alpha = 1,
      })
      r16_41.startTime = 0
      r16_41.startPosX = 0
      r16_41.endPosX = r2_44 + r10_41.width - 30
      r16_41.endTime = 300
      r33_0.baseY = r10_41.y + r10_41.height
      Runtime:addEventListener("enterFrame", r16_41)
      r33_0.play()
    end,
  })
end
local function r82_0(r0_50)
  -- line: [1051, 1096] id: 50
  local r1_50 = ExpManager.GetExp()
  ExpManager.AddExp(r0_50)
  ExpManager.SaveExp()
  local r2_50 = display.newGroup()
  function r2_50.enterFrame(r0_51, r1_51)
    -- line: [1062, 1091] id: 51
    if r0_51.startTime == 0 then
      r0_51.startTime = r1_51.time
    end
    local r2_51 = r1_51.time - r0_51.startTime
    if r0_51.endTime <= r2_51 then
      Runtime:removeEventListener("enterFrame", r2_50)
      display.remove(r2_50)
      r2_50 = nil
      r39_0()
      r36_0 = false
      r41_0(true)
      r40_0()
      return false
    end
    local r3_51 = easing.inExpo(r2_51, r0_51.endTime, r1_50, r0_50)
    display.remove(r29_0.imgGrp)
    r29_0.imgGrp = display.newGroup()
    r70_0(r16_0, r29_0, r3_51)
    return true
  end
  r2_50.startTime = 0
  r2_50.endTime = 400
  Runtime:addEventListener("enterFrame", r2_50)
end
local function r83_0(r0_52)
  -- line: [1101, 1488] id: 52
  ExpManager.LoadExp()
  local r2_52 = tonumber(ExpManager.GetExp())
  local r3_52 = 1
  local r4_52 = r15_0.getRankTable(r3_52)
  local r5_52 = db.LoadCharRank(_G.UserID, r3_52)
  for r9_52 = 0, 3, 1 do
    local r10_52 = r9_52 * 256
    util.LoadParts(r0_52, "data/help/base/00000001.jpg", r10_52, 0)
    util.LoadParts(r0_52, "data/help/base/00000006.jpg", r10_52, 512)
  end
  util.LoadParts(r0_52, r46_0("title_levelup_"), 186, 10)
  local r6_52 = util.LoadBtn({
    rtImg = r0_52,
    fname = r43_0("close"),
    x = 0,
    y = 0,
    func = r53_0,
  })
  r6_52.x = _G.Width - r6_52.width * 0.5 - 5
  r6_52.y = r6_52.height * 0.5 + 5
  local r7_52 = util.LoadBtn({
    rtImg = r0_52,
    fname = r43_0("help"),
    x = 0,
    y = 0,
    func = r52_0,
  })
  r7_52.x = _G.Width - r7_52.width * 0.5 - 110
  r7_52.y = r7_52.height * 0.5 + 5
  local r8_52 = tonumber(OrbManager.GetRemainOrb())
  local r9_52 = tonumber(OrbManager.GetMaxOrb())
  util.LoadParts(r0_52, r45_0("plate_orb"), 40, 28)
  r27_0 = {
    imgGrp = display.newGroup(),
    x = 65,
    y = 34,
  }
  r69_0(r0_52, r27_0, r8_52)
  r28_0 = {
    imgGrp = display.newGroup(),
    x = 117,
    y = 34,
  }
  r69_0(r0_52, r28_0, r9_52)
  local r10_52 = display.newGroup()
  r64_0(r10_52, 34, 66, 300)
  local r11_52 = util.LoadParts(r10_52, r48_0("exp_icon_"), 34, 66)
  r0_52:insert(r10_52)
  r29_0 = {
    imgGrp = display.newGroup(),
    x = 124,
    y = 79,
  }
  r70_0(r0_52, r29_0, r2_52)
  local r12_52 = 40
  local r13_52 = 416
  local r14_52 = util.LoadParts(r0_52, r45_0("plate_left"), r12_52, r13_52)
  r23_0 = {
    imgGrp = display.newGroup(),
    x = r12_52 + 5,
    y = r13_52 + 10,
    w = r14_52.contentWidth,
    h = r14_52.contentHeight,
  }
  r71_0(r0_52, r23_0, r5_52)
  local r17_52 = r4_52.GetRankDataMax()
  local r18_52 = r4_52.GetRankData(r5_52.rank).evlv
  local r19_52 = r4_52.GetRankData(r5_52.rank).dtm / 1000
  local r20_52 = r4_52.GetRankData(r5_52.rank).orb
  local r21_52 = r4_52.GetRankData(r5_52.rank).drt * 100
  local r22_52 = r4_52.GetTotalOmx(r5_52.rank)
  local r23_52 = 0
  if r5_52.rank < r17_52 then
    r23_52 = r4_52.GetRankData(r5_52.rank + 1).exp
  end
  local r24_52 = {}
  local r25_52 = util.LoadParts(r0_52, r45_0("plate_status"), 630, 410)
  local r26_52 = util.LoadParts(r0_52, r46_0("text_status_"), 650, 430)
  r25_0 = display.newGroup()
  local r27_52 = 654
  local r28_52 = 468
  r24_0[1] = {
    imgGrp = display.newGroup(),
    x = r27_52 + 140 + 20,
    y = r28_52 + 4,
    evox = r27_52,
    evoy = r28_52,
  }
  local r31_52 = util.LoadParts(r24_0[1].imgGrp, r46_0("text_duration_"), r27_52, r28_52)
  r67_0(r25_0, r24_0[1], r19_52, 3)
  local r32_52 = util.LoadParts(r25_0, r45_0("status_skill_sec"), 630 + r25_52.contentWidth, 472)
  r32_52.x = r32_52.x - r32_52.contentWidth - 24
  local r33_52 = util.LoadParts(r25_0, r46_0("text_evo_orb_"), 654, r31_52.y + r31_52.contentHeight + 8)
  local r34_52 = util.LoadParts(r25_0, "data/map/interface/stage_icon_orb.png", 806, r31_52.y + r31_52.contentHeight + 8)
  r24_0[2] = {
    imgGrp = display.newGroup(),
    x = 814 + r34_52.contentWidth + util.LoadParts(r25_0, r45_0("status_skill_kake"), 814 + r34_52.contentWidth, r31_52.y + r31_52.contentHeight + 8).contentWidth,
    y = r31_52.y + r31_52.contentHeight + 8,
  }
  r67_0(r25_0, r24_0[2], r20_52, 2)
  local r38_52 = util.LoadParts(r25_0, r46_0("text_drop_"), 654, r33_52.y + r33_52.contentHeight + 8)
  local r39_52 = util.LoadParts(r25_0, r45_0("status_skill_plus"), 794, r33_52.y + r33_52.contentHeight + 8)
  local r40_52 = util.LoadParts(r25_0, r45_0("status_skill_per"), 879, r33_52.y + r33_52.contentHeight + 8)
  r24_0[3] = {
    imgGrp = display.newGroup(),
    x = 814 + r39_52.contentWidth,
    y = r33_52.y + r33_52.contentHeight + 8,
  }
  r67_0(r25_0, r24_0[3], r21_52, 3)
  r0_52:insert(r25_0)
  r26_0 = display.newGroup()
  util.MakeCenterText(r26_0, 24, {
    r25_52.x,
    r25_52.y,
    r25_52.width,
    r25_52.height
  }, {
    0,
    0,
    0
  }, {
    170,
    170,
    170
  }, db.GetMessage(368))
  r26_0.isVisible = false
  r0_52:insert(r26_0)
  r36_0 = false
  local function r44_52(r0_53)
    -- line: [1300, 1341] id: 53
    if r18_0.enable_flag == false then
      r41_0(false)
      return 
    end
    if r36_0 then
      return 
    end
    ExpManager.LoadExp()
    local r1_53 = tonumber(ExpManager.GetExp())
    local r2_53 = r18_0.sid
    local r3_53 = r18_0.rank
    local r4_53 = r18_0.exp
    local r5_53 = r3_53 + 1
    local r6_53 = r15_0.getRankTable(r2_53)
    local r7_53 = r6_53.GetRankDataMax()
    local r8_53 = r6_53.GetCharRank(r2_53)
    if r7_53 < r5_53 then
      r36_0 = false
      return 
    end
    if r7_53 <= r8_53.rank + 1 then
      r36_0 = true
      r41_0(false)
    end
    if r6_53.GetRankData(r5_53).exp <= r1_53 and r3_53 <= r7_53 then
      r36_0 = true
      r81_0(0)
    end
  end
  local r45_52 = 366
  local r46_52 = 420
  r21_0 = display.newGroup()
  r21_0.image = display.newImage(r21_0, r46_0("levelup_"), 0, 0, true)
  r21_0:setReferencePoint(display.TopLeftReferencePoint)
  r21_0 = util.LoadBtnGroup({
    group = r21_0,
    cx = r45_52,
    cy = r46_52,
    func = r44_52,
    show = true,
  })
  r21_0.icon = util.LoadParts(r21_0, r45_0("cha_icon_exp"), 50, 55)
  r0_52:insert(r21_0)
  r21_0:setReferencePoint(display.CenterReferencePoint)
  r22_0 = {
    imgGrp = display.newGroup(),
    x = 130,
    y = 60,
  }
  if r5_52.rank < r17_52 then
    r66_0(r21_0, r22_0, r23_52)
  end
  r41_0(true)
  local r49_52 = r15_0.getRankTable(1)
  local r50_52 = nil
  if r49_52 ~= nil then
    if r49_52.GetRankDataMax() <= r49_52.GetCharRank(1).rank then
      r36_0 = true
      r41_0(false)
    end
    if r2_52 < r23_52 then
      r41_0(false)
    end
  end
  local function r51_52(r0_54)
    -- line: [1396, 1438] id: 54
    sound.PlaySE(1)
    local r2_54 = r4_0.getItemData(r4_0.pay_item_data.BuyExp)
    if r18_0.enable_flag == false then
      return 
    end
    if r36_0 then
      return 
    else
      r36_0 = true
      r41_0(false)
    end
    local r3_54 = r63_0()
    if r3_54 == nil then
      r36_0 = false
      r41_0(true)
      return 
    end
    local r4_54 = display.newGroup()
    display.newRect(r4_54, 0, 0, _G.Width, _G.Height).alpha = 0
    require("ui.coin_module").new({
      useItemList = r3_54,
      displayPocketCoin = {
        posX = 30,
        posY = _G.Height - 64,
      },
      displayMask = true,
    }).Open(r4_54, nil, function(r0_55)
      -- line: [1426, 1431] id: 55
      _G.metrics.crystal_buy_evo(r3_54)
      r82_0(r2_54[3])
    end, function(r0_56)
      -- line: [1432, 1437] id: 56
      r36_0 = false
      r41_0(true)
    end)
  end
  local r52_52 = 366
  local r53_52 = 552
  r19_0 = display.newGroup()
  r19_0.image = display.newImage(r19_0, r46_0("exp_cry_"), 0, 0, true)
  r19_0:setReferencePoint(display.TopLeftReferencePoint)
  r19_0 = util.LoadBtnGroup({
    group = r19_0,
    cx = r52_52,
    cy = r53_52,
    func = r51_52,
    show = true,
  })
  util.LoadParts(r19_0, "data/game/upgrade/icon_crystal.png", 60, 40)
  r0_52:insert(r19_0)
  r19_0:setReferencePoint(display.CenterReferencePoint)
  r20_0 = {
    imgGrp = display.newGroup(),
    x = 140,
    y = 43,
  }
  r66_0(r19_0, r20_0, util.ConvertDisplayCrystal(r4_0.getItemData(r4_0.pay_item_data.BuyExp)[2]))
  local r58_52 = {
    db.GetMessage(353),
    db.GetMessage(354),
    db.GetMessage(355)
  }
  for r62_52 = 1, #r58_52, 1 do
    local r63_52 = util.MakeCenterText(r0_52, 24, {
      100,
      372,
      160,
      18
    }, {
      64,
      48,
      25
    }, {
      255,
      246,
      229
    }, r58_52[r62_52])
    r63_52.isVisible = false
    r0_52:insert(r63_52)
    r63_52:setReferencePoint(display.TopLeftReferencePoint)
    r63_52.x = _G.Width / 2 - r63_52.contentWidth / 2
    r63_52.y = r63_52.y + 20
    r17_0[r62_52] = r63_52
  end
  r73_0()
end
local function r84_0()
  -- line: [1493, 1534] id: 57
  local r0_57 = db.GetMapInfo(_G.UserID)
  local r1_57 = 1
  for r5_57, r6_57 in pairs(r0_57) do
    if r6_57 ~= 0 then
      break
    else
      r1_57 = r5_57
    end
  end
  local r2_57 = db.GetStageInfo(_G.UserID, r1_57)
  local r3_57 = 1
  for r7_57, r8_57 in pairs(r2_57) do
    if r8_57 ~= 0 then
      break
    else
      r3_57 = r7_57
    end
  end
  _G.MapDB = system.pathForFile(string.format("data/map/%02d/%03d.sqlite", r1_57, r3_57), system.ResourceDirectory)
  local r4_57, r5_57 = db.LoadSummonData(_G.UserID)
  local r6_57 = nil
  for r11_57 = 1, #r4_57, 1 do
    local r12_57 = {
      r4_57[r11_57]
    }
    if r11_57 == r6_0.CharId.Daisy then
      table.insert(r12_57, r4_57[r6_0.CharId.DaisyA])
    end
    r4_57[r11_57] = r12_57
  end
  return r4_57
end
local function r85_0(r0_58)
  -- line: [1539, 1541] id: 58
  util.MakeFrame(r0_58)
end
local function r87_0()
  -- line: [1556, 1565] id: 60
  local r1_60 = r15_0.getRankTable(r18_0.sid).GetRankDataMax()
  local r2_60 = r18_0.rank + 1
  if r1_60 < r2_60 then
    return nil
  else
    return r2_60
  end
end
function r42_0()
  -- line: [1603, 1617] id: 63
  if r27_0 ~= nil then
    local r0_63 = tonumber(OrbManager.GetRemainOrb())
    display.remove(r27_0.imgGrp)
    r27_0.imgGrp = display.newGroup()
    r69_0(r16_0, r27_0, r0_63)
  end
  if r28_0 ~= nil then
    local r0_63 = tonumber(OrbManager.GetMaxOrb())
    display.remove(r28_0.imgGrp)
    r28_0.imgGrp = display.newGroup()
    r69_0(r16_0, r28_0, r0_63)
  end
end
function r39_0()
  -- line: [1622, 1633] id: 64
  if r29_0 ~= nil then
    local r0_64 = ExpManager.GetExp()
    if r0_64 == nil then
      r0_64 = 0
    end
    display.remove(r29_0.imgGrp)
    r29_0.imgGrp = display.newGroup()
    r70_0(r16_0, r29_0, r0_64)
  end
end
local function r91_0()
  -- line: [1690, 1694] id: 66
  for r3_66 = 1, #r17_0, 1 do
    r17_0[r3_66].isVisible = false
  end
end
function r40_0()
  -- line: [1699, 1730] id: 67
  r91_0()
  if r18_0.enable_flag == false then
    return 
  end
  local r0_67 = r18_0.sid
  local r1_67 = r18_0.rank
  local r2_67 = r18_0.exp
  ExpManager.LoadExp()
  local r3_67 = tonumber(ExpManager.GetExp())
  local r4_67 = r87_0()
  if r4_67 ~= nil then
    local r5_67 = r15_0.getRankTable(r0_67)
    local r6_67 = r5_67.GetRankData(r4_67).exp
    local r7_67 = r5_67.GetRankDataMax()
    if r6_67 <= r3_67 and r1_67 <= r7_67 then
      r17_0[1].isVisible = true
    elseif r3_67 < r6_67 then
      r17_0[2].isVisible = true
    else
      r17_0[3].isVisible = true
    end
  else
    r17_0[3].isVisible = true
  end
end
function r41_0(r0_72)
  -- line: [1808, 1849] id: 72
  if r21_0 == nil or r21_0.image == nil then
    return 
  end
  local function r1_72(r0_73, r1_73)
    -- line: [1816, 1829] id: 73
    if r0_73 == nil or r0_73.numChildren == nil or r0_73.numChildren < 1 then
      return 
    end
    local r2_73 = nil
    for r6_73 = 1, r0_73.numChildren, 1 do
      if r0_73[r6_73] ~= nil then
        r0_73[r6_73]:setFillColor(r1_73)
      end
    end
  end
  if r0_72 == true then
    r21_0.disable = false
    r21_0.image:setFillColor(255)
    r21_0.icon:setFillColor(255)
    r1_72(r22_0.imgGrp, 255)
    r2_0.LevelUpTutorial(true, r21_0)
  else
    r21_0.disable = true
    r21_0.image:setFillColor(190)
    r21_0.icon:setFillColor(190)
    r1_72(r22_0.imgGrp, 190)
    r2_0.LevelUpTutorial(false)
  end
end
local r96_0 = {}
function r38_0()
  -- line: [1892, 1894] id: 75
  r49_0()
end
for r102_0, r103_0 in pairs({
  DrawSkilInfo = function()
    -- line: [1546, 1554] id: 59
    if r23_0 ~= nil and r23_0.imgGrp ~= nil then
      display.remove(r23_0.imgGrp)
      r23_0.imgGrp = display.newGroup()
      r71_0(r16_0, r23_0, r18_0)
    end
  end,
  DrawBuyPrice = function()
    -- line: [1570, 1582] id: 61
    if r20_0 ~= nil then
      local r0_61 = r87_0()
      display.remove(r20_0.imgGrp)
      if r0_61 ~= nil then
        r20_0.imgGrp = display.newGroup()
        r66_0(r16_0, r20_0, r4_0.getItemData(r4_0.pay_item_data.BuyExp)[2])
      end
    end
  end,
  DrawNeedExp = function()
    -- line: [1587, 1598] id: 62
    if r22_0 ~= nil then
      local r0_62 = r87_0()
      display.remove(r22_0.imgGrp)
      if r0_62 ~= nil then
        local r1_62 = r15_0.getRankTable(r18_0.sid)
        r22_0.imgGrp = display.newGroup()
        r66_0(r21_0, r22_0, r1_62.GetRankData(r0_62).exp)
      end
    end
  end,
  DrawOrb = r42_0,
  DrawExp = r39_0,
  DrawStatusInfoNum = function()
    -- line: [1638, 1685] id: 65
    if r24_0 ~= nil then
      local r0_65 = r18_0.rank
      local r1_65 = r0_65 + 1
      local r2_65 = r15_0.getRankTable(r18_0.sid)
      if r2_65.IsEvoChar() == true then
        r25_0.isVisible = true
        r26_0.isVisible = false
      else
        r25_0.isVisible = false
        r26_0.isVisible = true
        return 
      end
      local r3_65 = r2_65.GetRankData(r0_65).evlv
      local r8_65 = {
        r2_65.GetRankData(r0_65).dtm / 1000,
        r2_65.GetRankData(r0_65).orb,
        r2_65.GetRankData(r0_65).drt * 100,
        r2_65.GetTotalOmx(r0_65)
      }
      local r9_65 = {
        3,
        2,
        3,
        3
      }
      for r13_65 = 1, #r24_0, 1 do
        if r24_0[r13_65] ~= nil then
          display.remove(r24_0[r13_65].imgGrp)
          r24_0[r13_65].imgGrp = display.newGroup()
          if r13_65 == 1 then
            util.LoadParts(r24_0[r13_65].imgGrp, r46_0("text_duration_"), r24_0[r13_65].evox, r24_0[r13_65].evoy)
          end
          r67_0(r25_0, r24_0[r13_65], r8_65[r13_65], r9_65[r13_65])
        end
      end
    end
  end,
  MessageHide = r91_0,
  MessageDisp = r40_0,
  IsRankUpExp = function()
    -- line: [1735, 1757] id: 68
    local r0_68 = r18_0.sid
    local r1_68 = r18_0.exp
    local r2_68 = r18_0.rank
    local r3_68 = r87_0()
    ExpManager.LoadExp()
    local r4_68 = tonumber(ExpManager.GetExp())
    if r3_68 == nil then
      return false
    end
    local r5_68 = r15_0.getRankTable(r0_68)
    local r7_68 = r5_68.GetRankDataMax()
    local r8_68 = r5_68.GetRankData(r3_68).exp <= r4_68
  end,
  UpdateCharInfo = function(r0_69, r1_69, r2_69, r3_69)
    -- line: [1762, 1769] id: 69
    r18_0 = {
      sid = r0_69,
      rank = r1_69,
      exp = r2_69,
      enable_flag = r3_69,
    }
  end,
  SetRankButtonActiveFlag = function(r0_70)
    -- line: [1775, 1777] id: 70
    r36_0 = r0_70
  end,
  IsRankupChar = function()
    -- line: [1782, 1803] id: 71
    if r18_0 == nil or r18_0.sid == nil or r18_0.rank == nil then
      return false
    end
    local r2_71 = r18_0.rank + 1
    local r4_71 = r15_0.getRankTable(r18_0.sid).GetRankDataMax()
    if r18_0.enable_flag == false or r4_71 < r2_71 then
      return false
    end
    return true
  end,
  SetEnableRankupButton = r41_0,
  new = function(r0_74)
    -- line: [1855, 1887] id: 74
    bgm.Play(1)
    local r1_74 = display.newGroup()
    local r2_74 = r84_0()
    if r0_74 ~= nil then
      r35_0 = r0_74.back
    end
    r83_0(r1_74)
    r85_0(r1_74)
    r1_0 = require("evo.evo_summon_plate")
    r1_0.new({
      evo_view = r96_0,
      rtImg = r1_74,
      summonState = r2_74,
      posX = 66,
      posY = 110,
    })
    r16_0 = r1_74
    r51_0()
    return r1_74
  end,
  Cleanup = r38_0,
}) do
  r96_0[r102_0] = r103_0
end
return r96_0
