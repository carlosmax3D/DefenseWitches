-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r120_0 = nil	-- notice: implicit variable refs by block#[0]
local r118_0 = nil	-- notice: implicit variable refs by block#[0]
local r0_0 = require("common.base_dialog")
local r1_0 = require("evo.evo_common")
local r2_0 = require("common.sprite_loader")
local r3_0 = r2_0.new({
  imageSheet = "common.sprites.sprite_number01",
})
local r4_0 = r2_0.new({
  imageSheet = "common.sprites.sprite_number03",
})
local r5_0 = r2_0.new({
  imageSheet = "common.sprites.sprite_string01",
})
local r6_0 = require("anm.download_cha_anm_01")
local r7_0 = require("anm.download_cha_anm_02")
local r8_0 = require("anm.download_cha_anm_03")
local r9_0 = require("stage_ui.stage_help_manager")
local r10_0 = require("common.frame01")
local r11_0 = require("login.login_bonus_item_data")
local r12_0 = require("evo.treasure_box_manager")
local r13_0 = "twinkleWhite"
local r14_0 = "twinkleSilver"
local r15_0 = "twinkleGold"
local r17_0 = require("common.particles.fireworks").new
local r18_0 = {}
r18_0.particleImage = {
  file = "data/sprites/twinkle01.png",
  options = {
    width = 30,
    height = 30,
    numFrames = 10,
    sheetContentWidth = 60,
    sheetContentHeight = 150,
  },
  sequence = {
    {
      name = r13_0,
      start = 1,
      count = 2,
      time = 200,
    },
    {
      name = r14_0,
      start = 3,
      count = 2,
      time = 200,
    },
    {
      name = r15_0,
      start = 5,
      count = 2,
      time = 200,
    }
  },
}
r18_0.gStart = 5
r18_0.gEnd = 20
r18_0.speedRangeXStart = -10
r18_0.speedRangeXEnd = 10
r18_0.sizeStart = 10
r18_0.sizeEnd = 30
r17_0 = r17_0(r18_0)
r18_0 = nil
local r19_0 = 4
local r20_0 = 1
local r21_0 = 5
local r22_0 = 800
local r23_0 = 640
local r24_0 = 540
local r25_0 = 800
local r26_0 = 500
local r27_0 = 1600
local r28_0 = 1000
local r29_0 = "grade01"
local r30_0 = "grade02"
local r32_0 = {
  r29_0,
  r30_0,
  "grade03"
}
local r33_0 = 3
local r34_0 = 9
local r35_0 = 5
local r36_0 = 7
local r37_0 = 200
local r38_0 = 3
local r39_0 = 20
local r40_0 = 20
local r41_0 = 40
local r42_0 = 50
local r43_0 = 120
local r44_0 = 200
local r45_0 = r25_0 - 500
local r46_0 = r26_0 - 200
local r47_0 = 3
local r48_0 = 700
local r49_0 = {
  "fx_chakudan_0_0",
  "fx_chakudan_0_1",
  "fx_chakudan_0_2"
}
local r50_0 = "exp_calc"
local r51_0 = "get_item"
local r52_0 = nil
local r53_0 = nil
local r54_0 = nil
local r55_0 = nil
local r56_0 = nil
local r57_0 = nil
local r58_0 = nil
local r59_0 = nil
local r60_0 = 0
local r61_0 = 0
local r62_0 = 0
local r63_0 = 0
local r64_0 = nil
local r65_0 = nil
local r66_0 = 0
local r67_0 = nil
local r68_0 = nil
local r69_0 = nil
local r70_0 = nil
local r71_0 = nil
local r72_0 = nil
local r73_0 = nil
local r74_0 = nil
local r75_0 = nil
local r76_0 = nil
local r77_0 = nil
local function r78_0(r0_1)
  -- line: [171, 171] id: 1
  return "data/evo/result/" .. r0_1 .. ".png"
end
local function r79_0(r0_2)
  -- line: [172, 172] id: 2
  return r78_0(r0_2 .. _G.UILanguage)
end
local function r80_0(r0_3)
  -- line: [173, 173] id: 3
  return "data/evo/evo_combi/" .. r0_3 .. ".png"
end
local function r81_0(r0_4)
  -- line: [174, 174] id: 4
  return "data/evo/evo_select_mode/" .. r0_4 .. ".png"
end
local function r82_0(r0_5)
  -- line: [175, 175] id: 5
  return "data/login_bonus/receive/" .. r0_5 .. ".png"
end
local function r83_0(r0_6)
  -- line: [176, 176] id: 6
  return "data/stage/" .. r0_6 .. ".png"
end
local function r84_0(r0_7)
  -- line: [177, 177] id: 7
  return "data/download/" .. r0_7 .. ".png"
end
local function r85_0(r0_8)
  -- line: [178, 178] id: 8
  return "data/option/" .. r0_8 .. ".png"
end
local function r86_0(r0_9)
  -- line: [179, 179] id: 9
  return "data/map/interface/" .. r0_9 .. ".png"
end
local function r87_0(r0_10)
  -- line: [180, 180] id: 10
  return "data/login_bonus/receive/" .. r0_10 .. ".png"
end
local function r88_0(r0_11, r1_11, r2_11, r3_11)
  -- line: [185, 211] id: 11
  local r4_11 = display.newGroup()
  display.newRect(r4_11, 0, 0, _G.Width, _G.Height).alpha = 0
  local r5_11 = util.LoadParts(r4_11, r78_0("exp_fukidasi"), 0, 0)
  util.LoadParts(r4_11, r79_0("exp_text_"), 18, 35)
  r70_0 = anime.RegisterWithInterval(r6_0.GetData(), 128, 120, "data/download", 100)
  anime.Show(r70_0, true)
  anime.Loop(r70_0, true)
  r4_11:insert(anime.GetSprite(r70_0))
  r4_11:setReferencePoint(display.TopLeftReferencePoint)
  if r1_11 == display.TopLeftReferencePoint then
    r4_11.x = r2_11
    r4_11.y = r3_11
  else
    r4_11.x = r2_11 - r5_11.width * 0.5
    r4_11.y = r3_11
  end
  r0_11:insert(r4_11)
end
local function r89_0(r0_12, r1_12)
  -- line: [216, 284] id: 12
  local r2_12 = display.newGroup()
  local r3_12 = r0_0.Create(r2_12, 600, 370, {
    64,
    96,
    64
  }, true, true)
  r3_12.isVisible = false
  local function r4_12()
    -- line: [223, 228] id: 13
    if r2_12 ~= nil then
      display.remove(r2_12)
      r2_12 = nil
    end
  end
  local function r5_12(r0_14)
    -- line: [231, 236] id: 14
    if r1_12 ~= nil then
      r1_12(r0_14)
    end
  end
  local function r6_12()
    -- line: [239, 244] id: 15
    if r3_12 ~= nil then
      display.remove(r3_12)
      r3_12 = nil
    end
  end
  local function r7_12()
    -- line: [247, 252] id: 16
    sound.PlaySE(1)
    r6_12()
    r5_12(false)
    r4_12()
  end
  local r8_12 = db.GetMessage(356)
  r0_0.CreateDialogLine(r3_12, 104, 420)
  local r9_12 = util.LoadParts(r3_12, r82_0("login_bonus_title_received"), 0, 0)
  r9_12:setReferencePoint(display.CenterReferencePoint)
  r9_12.x = r3_12.width * 0.5
  r9_12.y = 70
  util.MakeCenterText(r3_12, 24, {
    0,
    130,
    r3_12.width,
    0
  }, {
    255,
    255,
    255
  }, {
    0,
    0,
    0
  }, r8_12)
  local r10_12 = util.LoadParts(r3_12, r79_0("exp_icon_"), 100, 150)
  util.MakeCenterText(r3_12, 42, {
    0,
    190,
    r3_12.width,
    0
  }, {
    255,
    255,
    255
  }, {
    0,
    0,
    0
  }, tostring(r0_12))
  local r11_12 = util.LoadBtn({
    rtImg = r3_12,
    fname = r83_0("ok_en"),
    cx = r3_12.width * 0.5,
    cy = r3_12.height - 95,
    func = r7_12,
  })
  r0_0.FadeInMask(r2_12, {
    0,
    0,
    0,
    0.3
  }, 300)
  r0_0.ZoomInEffect(r2_12, r3_12)
  sound.PlaySE(28)
end
local function r90_0(r0_17)
  -- line: [289, 291] id: 17
  r0_17:setFillColor(150, 150, 150, 255)
end
local function r91_0(r0_18)
  -- line: [296, 303] id: 18
  if r0_18 == nil then
    return 
  end
  r0_18:removeEventListener("touch")
  r90_0(r0_18)
end
local function r92_0()
  -- line: [306, 309] id: 19
  r57_0:removeEventListener("touch")
  r90_0(r57_0)
end
local function r93_0()
  -- line: [312, 315] id: 20
  r58_0:removeEventListener("touch")
  r90_0(r58_0)
end
local function r94_0(r0_21, r1_21)
  -- line: [320, 322] id: 21
  r1_0.show_num_images({
    rtImg = r0_21,
    num = r1_21,
  })
end
local function r95_0(r0_22, r1_22)
  -- line: [327, 329] id: 22
  r1_0.show_num_images({
    rtImg = r0_22,
    num = r1_22,
  })
end
local function r96_0(r0_23, r1_23)
  -- line: [334, 346] id: 23
  local r2_23 = r1_23
  local r3_23 = 20
  local r5_23 = tostring(r1_23):len() * r3_23
  while 0 < r2_23 do
    r2_23 = math.floor(r2_23 * 0.1)
    r4_0.CreateImage(r0_23, r4_0.sequenceNames.ExpNum, r4_0.frameDefines.ExpNumStart + r2_23 % 10, r5_23, 0)
    r5_23 = r5_23 - r3_23
  end
  r4_0.CreateImage(r0_23, r4_0.sequenceNames.ExpNum, r4_0.frameDefines.ExpNumPlus, r5_23, 0)
end
local function r97_0(r0_24, r1_24)
  -- line: [351, 363] id: 24
  local r2_24 = r1_24
  local r3_24 = 26
  local r5_24 = tostring(r1_24):len() * r3_24
  while 0 < r2_24 do
    r2_24 = math.floor(r2_24 * 0.1)
    r4_0.CreateImage(r0_24, r4_0.sequenceNames.ExpNum, r4_0.frameDefines.TicketNumStart + r2_24 % 10, r5_24, 0)
    r5_24 = r5_24 - r3_24
  end
  r4_0.CreateImage(r0_24, r4_0.sequenceNames.ExpNum, r4_0.frameDefines.TicketNumMark, r5_24, 0)
end
local function r98_0(r0_25, r1_25)
  -- line: [368, 370] id: 25
  r1_0.show_num_images({
    rtImg = r0_25,
    num = r1_25,
  })
end
local function r99_0(r0_26, r1_26)
  -- line: [375, 377] id: 26
  r1_0.show_num_images({
    rtImg = r0_26,
    num = r1_26,
  })
end
local function r100_0(r0_27)
  -- line: [382, 384] id: 27
  r4_0.CreateNumbers(r0_27, r4_0.sequenceNames.TicketNum, r34_0, 0, 0, -4)
end
local function r101_0(r0_28)
  -- line: [389, 391] id: 28
  r4_0.CreateNumbers(r0_28, r4_0.sequenceNames.ExpNum, r35_0, 0, 0, -8)
end
local function r102_0(r0_29)
  -- line: [396, 398] id: 29
  r4_0.CreateNumbers(r0_29, r4_0.sequenceNames.TicketNum, r33_0, 0, 0, -4)
end
local function r103_0(r0_30)
  -- line: [403, 405] id: 30
  r4_0.CreateNumbers(r0_30, r4_0.sequenceNames.ExpNum, r36_0, 0, 0, -8)
end
local function r104_0(r0_31, r1_31)
  -- line: [410, 415] id: 31
  if r64_0 == nil then
    return r0_31
  end
  return r1_31
end
local function r105_0(r0_32)
  -- line: [420, 441] id: 32
  local r2_32 = 0
  local r3_32 = 0
  local r4_32 = 132
  local r5_32 = 200
  local r6_32 = require("common.sprite_loader").new({
    imageSheet = "common.sprites.sprite_parts01",
  })
  r6_32.CreateImage(r0_32, r6_32.sequenceNames.ExpBaseTreasureboxFrame, r6_32.frameDefines.ExpBaseTreasureboxFrameLeftUpper, r2_32, r3_32)
  r6_32.CreateImage(r0_32, r6_32.sequenceNames.ExpBaseTreasureboxFrame, r6_32.frameDefines.ExpBaseTreasureboxFrameLeftLower, r2_32, r4_32)
  r2_32 = r2_32 + 20
  local r7_32 = r6_32.CreateImage(r0_32, r6_32.sequenceNames.ExpBaseTreasureboxFrame, r6_32.frameDefines.ExpBaseTreasureboxFrameCenterUpper, r2_32, r3_32)
  local r8_32 = r6_32.CreateImage(r0_32, r6_32.sequenceNames.ExpBaseTreasureboxFrame, r6_32.frameDefines.ExpBaseTreasureboxFrameCenterLower, r2_32, r4_32)
  r7_32.x = r2_32 + r5_32 * 0.5
  r8_32.x = r2_32 + r5_32 * 0.5
  r7_32.xScale = r5_32
  r8_32.xScale = r5_32
  r2_32 = r2_32 + r5_32
  r6_32.CreateImage(r0_32, r6_32.sequenceNames.ExpBaseTreasureboxFrame, r6_32.frameDefines.ExpBaseTreasureboxFrameRightUpper, r2_32, r3_32)
  r6_32.CreateImage(r0_32, r6_32.sequenceNames.ExpBaseTreasureboxFrame, r6_32.frameDefines.ExpBaseTreasureboxFrameRightLower, r2_32, r4_32)
end
local function r106_0(r0_33, r1_33, r2_33)
  -- line: [446, 461] id: 33
  if r64_0 == nil then
    local r4_33 = r45_0
  else
    local r4_33 = r46_0
  end
  local r5_33 = easing.outExpo(r1_33, r2_33, 0, r41_0)
  local r6_33 = easing.outExpo(r1_33, r2_33, 0, 1)
  r0_33.spr.y = r0_33.by - r5_33
  r0_33.spr.alpha = r6_33
  r0_33.spr.xScale = 1.2
  r0_33.spr.yScale = 1.2
end
local function r107_0(r0_34, r1_34, r2_34)
  -- line: [466, 477] id: 34
  if r0_34.exp == nil then
    return false
  end
  local r3_34 = math.floor(easing.inExpo(r1_34, r2_34, r62_0, r0_34.exp))
  local r4_34 = math.floor(easing.inExpo(r1_34, r2_34, r63_0, r0_34.exp))
  r95_0(r55_0, r3_34)
  r94_0(r56_0, r4_34)
end
local function r108_0(r0_35, r1_35, r2_35)
  -- line: [482, 489] id: 35
  local r3_35 = easing.outExpo(r1_35, r2_35, 0, r39_0)
  local r4_35 = easing.outExpo(r1_35, r2_35, 1, 0.3)
  r0_35.spr.y = r0_35.by - r3_35
  r0_35.spr.xScale = r4_35
  r0_35.spr.yScale = r4_35
end
local function r109_0(r0_36, r1_36)
  -- line: [494, 529] id: 36
  local r5_36 = r0_36.iconShowTime + r0_36.iconHideTime
  local r2_36 = nil	-- notice: implicit variable refs by block#[7]
  local r3_36 = nil	-- notice: implicit variable refs by block#[7]
  local r4_36 = nil	-- notice: implicit variable refs by block#[7]
  if r1_36 <= r0_36.iconShowTime then
    r2_36 = easing.outExpo(r1_36, r0_36.iconShowTime, 0, r40_0)
    r3_36 = easing.outExpo(r1_36, r0_36.iconShowTime, 0.7, 0.3)
    r4_36 = easing.linear(r1_36, r0_36.iconShowTime, 0.3, 0.7)
  elseif r1_36 <= r5_36 then
    r2_36 = r40_0
    r3_36 = 1
    r4_36 = easing.inExpo(r1_36 - r0_36.iconShowTime, r0_36.iconHideTime, 1, -1)
  else
    return true
  end
  if r0_36.particleShowTime <= r1_36 then
    r0_36.particle.stop()
  end
  r0_36.spr.y = r0_36.by - r2_36
  r0_36.spr.xScale = r3_36
  r0_36.spr.yScale = r3_36
  r0_36.spr.alpha = r4_36
  r0_36.particle.baseY = r0_36.spr.y + 30
  return false
end
local function r110_0(r0_37, r1_37, r2_37)
  -- line: [534, 544] id: 37
  local r4_37 = util.CalcQuadraticBezPoint({
    x = r0_37.startX,
    y = r0_37.startY,
  }, {
    x = r0_37.controlX,
    y = r0_37.controlY,
  }, {
    x = r0_37.endX,
    y = r0_37.endY,
  }, easing.linear(r1_37, r2_37, 0, 1))
  r0_37.particle.baseX = r4_37.x
  r0_37.particle.baseY = r4_37.y
end
local function r111_0(r0_38, r1_38, r2_38)
  -- line: [549, 606] id: 38
  if r1_38.ms <= 0 then
    sound.PlaySE(r1_38.soundId)
  end
  r1_38.ms = r1_38.ms + r2_38
  if r1_38.endTime <= r1_38.ms then
    r17_0.stop()
    r1_38.openExp.spr:removeSelf()
    r1_38.stringEffect.spr:removeSelf()
    r62_0 = r62_0 + r1_38.countup.exp
    r63_0 = r63_0 + r1_38.countup.exp
    r95_0(r55_0, r62_0)
    r94_0(r56_0, r63_0)
    events.Delete(r65_0)
    r65_0 = nil
    if r1_38.endListener ~= nil then
      r1_38.endListener()
    end
    return false
  elseif r1_38.particleEndTime <= r1_38.ms then
    r17_0.stop()
  end
  if r1_38.openExp ~= nil then
    r108_0(r1_38.openExp, r1_38.ms, r1_38.endTime)
  end
  if r1_38.countup ~= nil then
    r107_0(r1_38.countup, r1_38.ms, r1_38.endTime)
  end
  if r1_38.stringEffect ~= nil then
    r106_0(r1_38.stringEffect, r1_38.ms, r1_38.endTime)
  end
  if r1_38.ms < 100 then
    r1_38.anm:play()
  end
  return true
end
local function r112_0(r0_39, r1_39, r2_39)
  -- line: [611, 665] id: 39
  if r1_39.ms <= 0 then
    sound.PlaySE(r1_39.soundId)
  end
  r1_39.ms = r1_39.ms + r2_39
  if r1_39.endTime <= r1_39.ms then
    r1_39.openItem.spr:removeSelf()
    for r6_39, r7_39 in pairs(r18_0) do
      r7_39.stop()
    end
    r1_39.particle = nil
    events.Delete(r65_0)
    r65_0 = nil
    if r1_39.endListener ~= nil then
      r1_39.endListener()
    end
    return false
  end
  if r1_39.phase == 1 and r1_39.openItem ~= nil and r109_0(r1_39.openItem, r1_39.ms) == true then
    r1_39.phase = r1_39.phase + 1
    r1_39.phase1Endtime = r1_39.ms
  elseif r1_39.phase == 2 then
    for r6_39, r7_39 in pairs(r1_39.particle.list) do
      r110_0(r7_39, r1_39.ms - r1_39.phase1Endtime, r1_39.endTime - r1_39.phase1Endtime)
      if r7_39.particle.isPlay() == false then
        r7_39.particle.play()
      end
    end
  end
  if r1_39.ms < 100 then
    r1_39.anm:play()
  end
  return true
end
local function r113_0(r0_40, r1_40, r2_40)
  -- line: [670, 734] id: 40
  if r0_40 == nil or r0_40.frame == nil then
    return 
  end
  local r3_40 = display.newGroup()
  if r44_0 <= r2_40 then
    r5_0.CreateImage(r3_40, r5_0.sequenceNames.Excellent, r5_0.frameDefines.Excellent, 0, 0)
  elseif r43_0 <= r2_40 then
    r5_0.CreateImage(r3_40, r5_0.sequenceNames.Nice, r5_0.frameDefines.Nice, 0, 0)
  elseif r42_0 <= r2_40 then
    r5_0.CreateImage(r3_40, r5_0.sequenceNames.Good, r5_0.frameDefines.Good, 0, 0)
  end
  local r4_40 = 120
  if r3_40 ~= nil then
    if r42_0 <= r2_40 then
      r17_0.baseX = r0_40.parent.parent.x + r0_40.parent.x + r0_40.frame.width * 0.5
      r17_0.baseY = r0_40.parent.y + r0_40.y + 65
      if r44_0 <= r2_40 then
        r17_0.particleMax = 15
        r17_0.useSequenceName = r15_0
      elseif r43_0 <= r2_40 then
        r17_0.particleMax = 7
        r17_0.useSequenceName = r14_0
      else
        r17_0.particleMax = 2
        r17_0.useSequenceName = r13_0
      end
      r17_0.play()
    end
    r0_40.frame:insert(r3_40)
    r1_40.stringEffect = {
      spr = r3_40,
      by = 90,
    }
    r3_40:setReferencePoint(display.CenterReferencePoint)
    r3_40.x = r4_40
    r3_40.y = 90
    r3_40.isVisible = true
  end
  local r5_40 = display.newGroup()
  r96_0(r5_40, r2_40)
  r0_40.frame:insert(r5_40)
  r5_40:setReferencePoint(display.CenterReferencePoint)
  r5_40.x = r4_40
  r5_40.y = 120
  r1_40.openExp = {
    spr = r5_40,
    bx = r5_40.x,
    by = r5_40.y,
  }
  r1_40.countup = {
    exp = r2_40,
    getTotalExp = r62_0,
  }
end
local function r114_0(r0_41, r1_41, r2_41, r3_41, r4_41, r5_41, r6_41)
  -- line: [739, 798] id: 41
  local r7_41 = display.newGroup()
  local r8_41 = display.newGroup()
  local r9_41 = util.LoadParts(r7_41, r2_41, 0, 0)
  if r3_41 > 1 then
    local r10_41 = display.newGroup()
    r97_0(r10_41, r3_41)
    r7_41:insert(r10_41)
    r10_41:setReferencePoint(display.CenterReferencePoint)
    r10_41.x = r9_41.width + 10
    r10_41.y = r9_41.height * 0.5 + 5
  end
  r0_41.frame:insert(r8_41)
  r0_41.frame:insert(r7_41)
  r7_41:setReferencePoint(display.CenterReferencePoint)
  r7_41.x = 120
  r7_41.y = 120
  r1_41.openItem = {
    spr = r7_41,
    bx = r7_41.x,
    by = r7_41.y,
    phase = 1,
    iconShowTime = r104_0(800, 500),
    iconHideTime = r104_0(200, 100),
    particleShowTime = r104_0(500, 200),
    particle = r18_0[r4_41],
  }
  r1_41.openItem.particle.parentObj = r8_41
  r1_41.openItem.particle.baseX = r7_41.x
  r1_41.openItem.particle.baseY = r7_41.y
  local r10_41 = nil
  local r11_41 = r7_41.y - r40_0
  local r12_41 = r0_41.frame.parent.x + r0_41.frame.x
  local r13_41 = r0_41.y - r11_41
  local r14_41 = r12_41 / r3_41 + 40
  local r15_41 = r13_41 / r3_41
  r1_41.particle = {}
  r1_41.particle.list = {}
  for r19_41 = 1, r3_41, 1 do
    local r20_41 = r19_41 - 1
    r1_41.particle.list[r19_41] = {}
    r1_41.particle.list[r19_41].particle = r18_0[r4_41 + r20_41]
    r1_41.particle.list[r19_41].particle.parentObj = r8_41
    r1_41.particle.list[r19_41].startX = r7_41.x
    r1_41.particle.list[r19_41].startY = r11_41
    r1_41.particle.list[r19_41].controlX = r5_41 - r12_41 + r14_41 * r20_41
    r1_41.particle.list[r19_41].controlY = r11_41 + r13_41 + r15_41 * r20_41
    r1_41.particle.list[r19_41].endX = r5_41 - r12_41
    r1_41.particle.list[r19_41].endY = r6_41 - r13_41
  end
end
local function r115_0(r0_42, r1_42, r2_42, r3_42)
  -- line: [803, 837] id: 42
  if r3_42 == true then
    local r4_42 = db.GetInventoryItem(_G.UserInquiryID, r0_42)
    if r4_42 == nil then
      r4_42 = 0
    end
    local r5_42 = r4_42 + r1_42
    if r5_42 <= r2_42 then
      db.SetInventoryItem({
        {
          uid = _G.UserInquiryID,
          itemid = r0_42,
          quantity = r5_42,
        }
      })
      return true
    end
  else
    local r4_42 = r67_0[tostring(r0_42)]
    r67_0[tostring(r0_42)] = r67_0[tostring(r0_42)] + r1_42
    if r2_42 <= r67_0[tostring(r0_42)] then
      r67_0[tostring(r0_42)] = r2_42
    end
    if r4_42 ~= r67_0[tostring(r0_42)] then
      return true
    end
  end
  return false
end
local function r116_0(r0_43, r1_43)
  -- line: [842, 1049] id: 43
  if r59_0[r0_43.TreasureboxIndex] == nil or #r59_0[r0_43.TreasureboxIndex].data < r0_43.openIndex then
    DebugPrint("treasure box " .. tostring(r0_43.TreasureboxIndex) .. " is nil")
    return 
  end
  local function r2_43(r0_44)
    -- line: [851, 861] id: 44
    r66_0 = r66_0 + r0_44
    if _G.OrbManager.GetMaxOrb() < r66_0 then
      r66_0 = _G.OrbManager.GetMaxOrb()
    end
    r1_0.show_num_images({
      rtImg = r73_0,
      num = r66_0,
    })
  end
  local function r3_43(r0_45)
    -- line: [865, 874] id: 45
    if r115_0(_G.LoginItems["2"].id, r0_45, r11_0.getFlareMaxLimit(), false) == true then
      _G.metrics._ticket_get_flare(r0_45)
      r1_0.show_num_images({
        rtImg = r75_0,
        num = r67_0["2"],
      })
    end
  end
  local function r4_43(r0_46)
    -- line: [877, 886] id: 46
    if r115_0(_G.LoginItems["3"].id, r0_46, r11_0.getRewindMaxLimit(), false) == true then
      _G.metrics._ticket_get_rewind(r0_46)
      r1_0.show_num_images({
        rtImg = r74_0,
        num = r67_0["3"],
      })
    end
  end
  if r65_0 ~= nil then
    if r65_0.sturct ~= nil then
      if r65_0.sturct.openExp ~= nil and r65_0.sturct.openExp.spr ~= nil then
        display.remove(r65_0.sturct.openExp.spr)
        r65_0.sturct.openExp.spr = nil
      end
      if r65_0.sturct.stringEffect ~= nil and r65_0.sturct.stringEffect.spr ~= nil then
        display.remove(r65_0.sturct.stringEffect.spr)
        r65_0.sturct.stringEffect.spr = nil
      end
      if r65_0.sturct.openItem ~= nil and r65_0.sturct.openItem.spr ~= nil then
        display.remove(r65_0.sturct.openItem.spr)
        r65_0.sturct.openItem.spr = nil
        if r65_0.itemData ~= nil then
          if r65_0.itemData[1] == r12_0.TreasureboxItemKeyOrb then
            r2_43(r65_0.itemData[2])
          elseif r65_0.itemData[1] == r12_0.TreasureboxItemKeyFlare then
            r3_43(r65_0.itemData[2])
          elseif r65_0.itemData[1] == r12_0.TreasureboxItemKeyRewind then
            r4_43(r65_0.itemData[2])
          end
        end
      end
      if r65_0.sturct.countup ~= nil then
        r62_0 = r62_0 + r65_0.sturct.countup.exp
        r63_0 = r63_0 + r65_0.sturct.countup.exp
        r95_0(r55_0, r62_0)
        r94_0(r56_0, r63_0)
      end
      for r8_43, r9_43 in pairs(r18_0) do
        r9_43.stop()
      end
    end
    events.Delete(r65_0)
    r65_0 = nil
  end
  r17_0.stop()
  local r5_43 = nil
  local r6_43 = nil
  local r8_43 = r12_0.GetTreasureboxDropItemData(r59_0[r0_43.TreasureboxIndex].data[r0_43.openIndex])
  local r9_43 = r59_0[r0_43.TreasureboxIndex].treasureboxG
  r5_43 = r59_0[r0_43.TreasureboxIndex].treasureboxAnime
  r98_0(r9_43, #r59_0[r0_43.TreasureboxIndex].data - r0_43.openIndex)
  r0_43.openIndex = r0_43.openIndex + 1
  local r10_43 = {
    ms = 0,
    endTime = 0,
    particleEndTime = 0,
    anm = r5_43,
    soundId = r104_0(53, 52),
  }
  if r8_43[1] == r12_0.TreasureboxItemKeyExp then
    r10_43.endTime = r104_0(r25_0, r26_0)
    r10_43.particleEndTime = r104_0(r10_43.endTime - 500, r10_43.endTime - 100)
    r10_43.endListener = r1_43
    r113_0(r9_43, r10_43, r8_43[2])
    r65_0 = events.Register(r111_0, r10_43, 1)
    r65_0.sturct = r10_43
    r65_0.itemData = r8_43
  elseif r8_43[1] == r12_0.TreasureboxItemKeyOrb then
    r10_43.phase = 1
    r10_43.endTime = r104_0(r27_0, r28_0)
    function r10_43.endListener()
      -- line: [980, 986] id: 47
      r2_43(r8_43[2])
      if r1_43 ~= nil then
        r1_43()
      end
    end
    r114_0(r9_43, r10_43, r81_0("button_orb"), r8_43[2], r21_0, 146, 500)
    r65_0 = events.Register(r112_0, r10_43, 1)
    r65_0.sturct = r10_43
    r65_0.itemData = r8_43
    r10_43.openItem.particle.play()
  elseif r8_43[1] == r12_0.TreasureboxItemKeyFlare then
    r10_43.phase = 1
    r10_43.endTime = r104_0(r27_0, r28_0)
    function r10_43.endListener()
      -- line: [1004, 1010] id: 48
      r3_43(r8_43[2])
      if r1_43 ~= nil then
        r1_43()
      end
    end
    r114_0(r9_43, r10_43, r87_0("ticket_flare_s"), r8_43[2], r19_0, 146, 550)
    r65_0 = events.Register(r112_0, r10_43, 1)
    r65_0.sturct = r10_43
    r65_0.itemData = r8_43
    r10_43.openItem.particle.play()
  elseif r8_43[1] == r12_0.TreasureboxItemKeyRewind then
    r10_43.phase = 1
    r10_43.endTime = r104_0(r27_0, r28_0)
    function r10_43.endListener()
      -- line: [1028, 1034] id: 49
      r4_43(r8_43[2])
      if r1_43 ~= nil then
        r1_43()
      end
    end
    r114_0(r9_43, r10_43, r87_0("ticket_rewind_s"), r8_43[2], r20_0, 146, 580)
    r65_0 = events.Register(r112_0, r10_43, 1)
    r65_0.sturct = r10_43
    r65_0.itemData = r8_43
    r10_43.openItem.particle.play()
  else
    return 
  end
end
local function r117_0(r0_50, r1_50)
  -- line: [1054, 1076] id: 50
  r116_0(r0_50, r1_50)
  if r59_0[r0_50.TreasureboxIndex] == nil or #r59_0[r0_50.TreasureboxIndex].data < r0_50.openIndex then
    r91_0(r59_0[r0_50.TreasureboxIndex].openButton)
    local r2_50 = nil
    local r3_50 = nil
    local r4_50 = 0
    for r8_50, r9_50 in pairs(r59_0) do
      if #r9_50.data <= r9_50.openButton.openIndex then
        r4_50 = r4_50 + 1
      end
    end
    if #r59_0 <= r4_50 then
      r92_0()
    end
  end
end
function r118_0(r0_51, r1_51)
  -- line: [1081, 1098] id: 51
  r117_0(r0_51, function()
    -- line: [1082, 1097] id: 52
    if r0_51.openIndex <= #r59_0[r0_51.TreasureboxIndex].data and r64_0 ~= nil and r64_0 == r0_51.timerId then
      r118_0(r0_51, r1_51)
    else
      sound.PlaySE(53)
      if r1_51 ~= nil then
        r1_51()
      end
    end
  end)
end
local function r119_0(r0_53, r1_53)
  -- line: [1103, 1163] id: 53
  local r2_53 = false
  local r3_53 = display.getCurrentStage()
  local function r4_53()
    -- line: [1109, 1116] id: 54
    r0_53.xScale = 1
    r0_53.yScale = 1
    if r3_53 ~= nil then
      r3_53:setFocus(nil)
    end
  end
  if r1_53.phase == "began" then
    r0_53:setReferencePoint(display.CenterReferencePoint)
    r0_53.xScale = 0.9
    r0_53.yScale = 0.9
    r0_53.rect = r0_53.stageBounds
    if r3_53 then
      r3_53:setFocus(r0_53)
    end
    r2_53 = true
    r64_0 = timer.performWithDelay(r37_0, function(r0_55)
      -- line: [1131, 1136] id: 55
      r0_53.timerId = r64_0
      r118_0(r0_53, function()
        -- line: [1133, 1135] id: 56
        r4_53()
      end)
    end)
  elseif r1_53.phase == "moved" then
    local r5_53 = r0_53.rect
    if r1_53.x < r5_53.xMin or r5_53.xMax < r1_53.x or r1_53.y < r5_53.yMin or r5_53.yMax < r1_53.y then
      r4_53()
    end
  elseif r1_53.phase == "ended" then
    r4_53()
    sound.PlaySE(53)
    if r64_0 ~= nil then
      timer.cancel(r64_0)
      r64_0 = nil
      r117_0(r0_53)
    end
  end
  return r2_53
end
function r120_0(r0_57, r1_57)
  -- line: [1168, 1221] id: 57
  local function r2_57()
    -- line: [1170, 1176] id: 58
    sound.PlaySE(53)
    if r1_57 then
      r1_57()
    end
  end
  local r3_57 = #r59_0
  if r3_57 < r0_57 then
    r2_57()
    return 
  end
  function r3_57()
    -- line: [1185, 1216] id: 59
    if r59_0 == nil then
      return 
    end
    if r59_0[r0_57].data == nil or #r59_0[r0_57].data < r59_0[r0_57].openButton.openIndex then
      r0_57 = r0_57 + 1
      if #r59_0 < r0_57 then
        r2_57()
        return 
      end
    end
    r116_0(r59_0[r0_57].openButton, function()
      -- line: [1200, 1215] id: 60
      if r59_0 == nil then
        return 
      end
      if r59_0[r0_57].data == nil or #r59_0[r0_57].data < r59_0[r0_57].openButton.openIndex then
        r120_0(r0_57 + 1, r1_57)
        return 
      end
      r3_57()
    end)
  end
  r64_0 = 1234
  r3_57()
end
local function r121_0(r0_61)
  -- line: [1226, 1239] id: 61
  for r6_61, r7_61 in pairs(r59_0) do
    r91_0(r7_61.openButton)
  end
  r92_0()
  r120_0(1, function()
    -- line: [1236, 1238] id: 62
    local r0_62 = nil	-- notice: implicit variable refs by block#[0]
    r64_0 = r0_62
  end)
end
local function r122_0(r0_63, r1_63, r2_63)
  -- line: [1244, 1257] id: 63
  local r3_63 = r1_63.ms + r2_63
  if r48_0 <= r3_63 then
    r1_63.ev = nil
    if r1_63.endListener ~= nil then
      r1_63.endListener()
    end
    return false
  end
  r1_63.ms = r3_63
  return true
end
local function r123_0(r0_64)
  -- line: [1262, 1350] id: 64
  sound.PlaySE(1)
  r17_0.stop()
  if r65_0 ~= nil then
    events.Delete(r65_0)
    r65_0 = nil
  end
  local r1_64 = nil
  local r2_64 = nil
  local r3_64 = nil
  local r4_64 = false
  r62_0 = r61_0
  for r8_64, r9_64 in pairs(r59_0) do
    for r13_64 = 1, #r9_64.data, 1 do
      local r14_64 = r12_0.GetTreasureboxDropItemData(r9_64.data[r13_64])
      if r14_64[1] == r12_0.TreasureboxItemKeyExp then
        r62_0 = r62_0 + r14_64[2]
        r4_64 = true
      elseif r14_64[1] == r12_0.TreasureboxItemKeyOrb then
        local r15_64 = _G.OrbManager.GetRemainOrb()
        _G.OrbManager.SetRecoveryOrb(_G.OrbManager.GetRecoveryTypeTreasurebox(), r14_64[2], 0, nil)
        if r15_64 < _G.OrbManager.GetRemainOrb() then
          r1_0.show_num_images({
            rtImg = r73_0,
            num = _G.OrbManager.GetRemainOrb(),
          })
        end
      elseif r14_64[1] == r12_0.TreasureboxItemKeyFlare and r115_0(_G.LoginItems["2"].id, r14_64[2], r11_0.getFlareMaxLimit(), true) == true then
        r1_0.show_num_images({
          rtImg = r75_0,
          num = db.GetInventoryItem(_G.UserInquiryID, _G.LoginItems["2"].id),
        })
      elseif r14_64[1] == r12_0.TreasureboxItemKeyRewind and r115_0(_G.LoginItems["3"].id, r14_64[2], r11_0.getRewindMaxLimit(), true) == true then
        r1_0.show_num_images({
          rtImg = r74_0,
          num = db.GetInventoryItem(_G.UserInquiryID, _G.LoginItems["3"].id),
        })
      end
    end
  end
  r63_0 = r60_0 + r62_0
  local r5_64 = nil
  local r6_64 = nil
  for r10_64, r11_64 in pairs(r59_0) do
    r91_0(r11_64.openButton)
  end
  r92_0()
  r93_0()
  for r10_64, r11_64 in pairs(r59_0) do
    r98_0(r11_64.treasureboxG, 0)
  end
  r95_0(r55_0, r62_0)
  r94_0(r56_0, r63_0)
  if r4_64 == true then
    local r7_64 = {
      ms = 0,
      endListener = r76_0,
    }
    r7_64.ev = events.Register(r122_0, r7_64, 1)
  else
    r76_0()
  end
end
local function r124_0(r0_65, r1_65)
  -- line: [1355, 1359] id: 65
  local r2_65 = require("stage_ui.stage_help")
  r2_65.Init()
  r2_65.Open(r0_65, r1_65)
end
local function r125_0(r0_66)
  -- line: [1364, 1379] id: 66
  r9_0.Init()
  if r9_0.IsFirstTreasureboxResultRun() == true and _G.ServerStatus.boxhelp == 1 then
    r9_0.SetTreasureboxResultRun()
    r124_0(7, r0_66)
  elseif r0_66 ~= nil then
    r0_66()
  end
end
local function r126_0()
  -- line: [1384, 1387] id: 67
  sound.PlaySE(1)
  r124_0(7)
end
local function r127_0(r0_68)
  -- line: [1392, 1673] id: 68
  local r1_68 = display.newGroup()
  local r2_68 = r0_0.CreateBlueGradientBg(r1_68)
  if r2_68 == nil then
    return false
  end
  r2_68.x = _G.Width * 0.5 - r2_68.width * 0.5
  r2_68.y = _G.Height * 0.5 - r2_68.height * 0.5
  r1_68.isVisible = false
  r2_68.isVisible = false
  r52_0 = r1_68
  r53_0 = r2_68
  local r3_68 = r2_68.width * 0.5
  local r4_68 = util.LoadParts(r2_68, r79_0("exp_title_"), 0, 0)
  r4_68:setReferencePoint(display.CenterReferencePoint)
  r4_68.x = r3_68
  r4_68.y = 60
  r59_0 = {}
  local r5_68 = display.newGroup()
  local r6_68 = nil
  local r7_68 = nil
  local r8_68 = 0
  for r12_68, r13_68 in pairs(r32_0) do
    if r0_68.getTreasureboxList[r13_68] ~= nil then
      local r14_68 = display.newGroup()
      r14_68.x = r8_68 * 276
      r14_68.y = 0
      r105_0(r14_68)
      local r15_68 = nil
      if r13_68 == r29_0 then
        r15_68 = display.newSprite(graphics.newImageSheet("data/sprites/treasurebox01.png", {
          frames = {
            {
              x = 0,
              y = 0,
              width = 147,
              height = 164,
            },
            {
              x = 148,
              y = 0,
              width = 147,
              height = 164,
            }
          },
        }), {
          name = "normal",
          frames = {
            1,
            2
          },
          start = 1,
          count = 2,
          loopCount = 1,
        })
      elseif r13_68 == r30_0 then
        r15_68 = display.newSprite(graphics.newImageSheet("data/sprites/treasurebox02.png", {
          frames = {
            {
              x = 0,
              y = 0,
              width = 147,
              height = 164,
            },
            {
              x = 148,
              y = 0,
              width = 147,
              height = 164,
            }
          },
        }), {
          name = "rich",
          frames = {
            1,
            2
          },
          start = 1,
          count = 2,
          loopCount = 1,
        })
      else
        r15_68 = display.newSprite(graphics.newImageSheet("data/sprites/Treasurebox01.png", {
          frames = {
            {
              x = 0,
              y = 0,
              width = 147,
              height = 164,
            },
            {
              x = 148,
              y = 0,
              width = 147,
              height = 164,
            }
          },
        }), {
          name = "test",
          frames = {
            1,
            2
          },
          start = 1,
          count = 2,
          loopCount = 1,
        })
      end
      if r15_68 ~= nil then
        r15_68.x = r14_68.width * 0.5 - 20
        r15_68.y = r14_68.height * 0.5 - 15
        r14_68:insert(r15_68)
      end
      r4_0.CreateImage(r14_68, r4_0.sequenceNames.TicketNum, r4_0.frameDefines.TicketNumMark, r14_68.width - 130, 170)
      local r16_68 = display.newGroup()
      r102_0(r16_68)
      r16_68.x = r14_68.width - 100
      r16_68.y = 170
      r14_68:insert(r16_68)
      r5_68:insert(r14_68)
      r16_68.frame = r14_68
      r16_68.treasureboxIndex = r8_68 + 1
      r98_0(r16_68, #r0_68.getTreasureboxList[r13_68])
      local r17_68 = util.LoadParts(r14_68, r79_0("exp_button_open_"), 0, 0)
      r17_68.x = r14_68.width * 0.5 - r17_68.width * 0.5
      r17_68.y = r14_68.height - r17_68.height * 0.5
      r17_68.TreasureboxIndex = r8_68 + 1
      r17_68.openIndex = 1
      r17_68.touch = r119_0
      r17_68:addEventListener("touch", normalBtn)
      table.insert(r59_0, {
        data = r0_68.getTreasureboxList[r13_68],
        treasureboxG = r16_68,
        treasureboxAnime = r15_68,
        openButton = r17_68,
      })
      r8_68 = r8_68 + 1
    end
  end
  r2_68:insert(r5_68)
  r5_68.x = r2_68.width * 0.5 - r5_68.width * 0.5
  r5_68.y = 95
  r55_0 = display.newGroup()
  r101_0(r55_0)
  r95_0(r55_0, 0)
  r56_0 = display.newGroup()
  r100_0(r56_0)
  r94_0(r56_0, r0_68.totalExperience)
  local r9_68 = 10
  local r10_68 = 28
  local r11_68 = 20
  local r12_68 = display.newGroup()
  local r13_68 = util.LoadParts(r12_68, r79_0("exp_icon_"), 10, 0)
  local r14_68 = r12_68.x + r12_68.width + r9_68
  r56_0.x = r14_68
  r56_0.y = 15
  r14_68 = r14_68 + r56_0.width + r9_68
  local r15_68 = display.newGroup()
  r4_0.CreateImage(r15_68, r4_0.sequenceNames.TicketNum, r4_0.frameDefines.TicketNumLeft, r14_68, 18)
  r14_68 = r14_68 + r10_68
  local r16_68 = display.newGroup()
  r4_0.CreateImage(r16_68, r4_0.sequenceNames.ExpNum, r4_0.frameDefines.ExpNumPlus, r14_68, 18)
  r14_68 = r14_68 + r11_68
  r55_0.x = r14_68
  r55_0.y = 18
  r14_68 = r14_68 + r55_0.width
  local r17_68 = display.newGroup()
  r4_0.CreateImage(r17_68, r4_0.sequenceNames.TicketNum, r4_0.frameDefines.TicketNumRight, r14_68, 18)
  r14_68 = r14_68 + r10_68 + r9_68
  local r18_68 = display.newGroup()
  r54_0 = display.newGroup()
  r1_0.create_exp_frame(r54_0, 0, 0, r14_68)
  r18_68.x = r3_68 - r14_68 * 0.5
  r18_68.y = 410
  r18_68:insert(r54_0)
  r18_68:insert(r12_68)
  r18_68:insert(r56_0)
  r18_68:insert(r15_68)
  r18_68:insert(r16_68)
  r18_68:insert(r55_0)
  r18_68:insert(r17_68)
  r2_68:insert(r18_68)
  r57_0 = util.LoadBtn({
    rtImg = r2_68,
    fname = r79_0("exp_button_fullopen_"),
    cx = 0,
    cy = r2_68.height - 95,
    func = r121_0,
  })
  r57_0.x = r3_68
  r58_0 = util.LoadBtn({
    rtImg = r2_68,
    fname = r79_0("exp_button_close_"),
    cx = r57_0.x + r57_0.width + 50,
    cy = r2_68.height - 90,
    func = r123_0,
  })
  if r0_68.getTreasureboxList.grade01 == nil and r0_68.getTreasureboxList.grade02 == nil then
    r57_0.disable = true
    r90_0(r57_0)
    r88_0(r2_68, display.TopCenterReferencePoint, r3_68, 120)
  elseif r0_68.isUsedOrb == false and r0_68.getTreasureboxList.grade02 == nil then
    r5_68.x = r2_68.width * 0.5 - r5_68.width - 50
    r88_0(r2_68, display.TopLeftReferencePoint, 466, 120)
  end
  local r19_68 = util.LoadBtn({
    rtImg = r2_68,
    fname = r85_0("help"),
    cx = 0,
    cy = 0,
    func = r126_0,
  })
  r19_68.x = _G.Width - r19_68.width * 0.5 - 20
  r19_68.y = r19_68.height * 0.5 + 30
  local r20_68 = r10_0.new({
    rootGroup = r2_68,
    x = 40,
    y = 480,
    width = 170,
    height = 120,
    headerColor = {
      238,
      102,
      238,
      1
    },
    bodyColor = {
      0,
      0,
      0,
      0.2
    },
    footerColor = {
      221,
      102,
      221,
      1
    },
  })
  local r21_68 = util.LoadParts(r20_68.obj, r86_0("stage_icon_orb"), 0, 0)
  local r22_68 = util.LoadParts(r20_68.obj, r86_0("stage_ticket_rewind"), 0, 0)
  local r23_68 = util.LoadParts(r20_68.obj, r86_0("stage_ticket_flare"), 0, 0)
  r21_68:setReferencePoint(display.TopCenterReferencePoint)
  r22_68:setReferencePoint(display.TopCenterReferencePoint)
  r23_68:setReferencePoint(display.TopCenterReferencePoint)
  r21_68.x = 40
  r21_68.y = 20
  r22_68.x = 40
  r22_68.y = 50
  r23_68.x = 40
  r23_68.y = 80
  local r24_68 = r21_68.y + 1
  r73_0 = display.newGroup()
  r3_0.CreateNumbers(r73_0, r3_0.sequenceNames.Score, 3, 70, r24_68, -2)
  r3_0.CreateImage(r20_68.obj, r3_0.sequenceNames.Score, r3_0.frameDefines.ScoreSlash, 106, r24_68)
  r3_0.CreateImageNumbers(r20_68.obj, r3_0.sequenceNames.Score, r3_0.frameDefines.ScoreStart, _G.OrbManager.GetMaxOrb(), 118, r24_68, -2)
  r20_68.obj:insert(r73_0)
  r1_0.show_num_images({
    rtImg = r73_0,
    num = _G.OrbManager.GetRemainOrb(),
  })
  r24_68 = r22_68.y + 1
  r74_0 = display.newGroup()
  r3_0.CreateNumbers(r74_0, r3_0.sequenceNames.Score, 2, 82, r24_68, -2)
  r3_0.CreateImage(r20_68.obj, r3_0.sequenceNames.Score, r3_0.frameDefines.ScoreSlash, 106, r24_68)
  r3_0.CreateImageNumbers(r20_68.obj, r3_0.sequenceNames.Score, r3_0.frameDefines.ScoreStart, r11_0.getRewindMaxLimit(), 118, r24_68, -2)
  r20_68.obj:insert(r74_0)
  r1_0.show_num_images({
    rtImg = r74_0,
    num = db.GetInventoryItem(_G.UserInquiryID, _G.LoginItems["3"].id),
  })
  r24_68 = r23_68.y + 1
  r75_0 = display.newGroup()
  r3_0.CreateNumbers(r75_0, r3_0.sequenceNames.Score, 1, 94, r24_68, -2)
  r3_0.CreateImage(r20_68.obj, r3_0.sequenceNames.Score, r3_0.frameDefines.ScoreSlash, 106, r24_68)
  r3_0.CreateImageNumbers(r20_68.obj, r3_0.sequenceNames.Score, r3_0.frameDefines.ScoreStart, r11_0.getFlareMaxLimit(), 118, r24_68, -2)
  r20_68.obj:insert(r75_0)
  r1_0.show_num_images({
    rtImg = r75_0,
    num = db.GetInventoryItem(_G.UserInquiryID, _G.LoginItems["2"].id),
  })
  return true
end
local function r128_0(r0_69)
  -- line: [1678, 1732] id: 69
  local r1_69 = r0_69 * 2 - 1
  local r2_69 = require("common.particles.pixieDust").new
  local r3_69 = {}
  local r4_69 = {
    file = "data/sprites/twinkle01.png",
    options = {
      width = 30,
      height = 30,
      numFrames = 10,
      sheetContentWidth = 60,
      sheetContentHeight = 150,
    },
  }
  r4_69.sequence = {
    name = "prism",
    frames = {
      r1_69,
      r1_69 + 1
    },
    start = r1_69,
    count = 2,
    time = 100,
  }
  r3_69.particleImage = r4_69
  r3_69.baseX = 0
  r3_69.baseY = 0
  r3_69.flowX = -0.5
  r3_69.flowY = 2
  r3_69.particleMax = 2
  r3_69.sizeStart = 16
  r3_69.sizeEnd = 30
  r3_69.moveXStart = -20
  r3_69.moveXEnd = 20
  r3_69.moveYStart = -20
  r3_69.moveYEnd = 20
  r3_69.accelerateXStart = -6
  r3_69.accelerateXEnd = 6
  r3_69.accelerateYStart = -6
  r3_69.accelerateYEnd = 6
  r3_69.lifetimeStart = 3
  r3_69.lifetimeEnd = 7
  r3_69.alphaStepStart = 1
  r3_69.alphaStepEnd = 10
  r3_69.twistSpeedStart = 1
  r3_69.twistSpeedEnd = 10
  r3_69.twistRadiusStart = 1
  r3_69.twistRadiusEnd = 5
  r3_69.twistAngleStart = 0
  r3_69.twistAngleEnd = 359
  r3_69.twistAngleStepStart = 1
  r3_69.twistAngleStepEnd = 10
  return r2_69(r3_69)
end
local function r129_0()
  -- line: [1737, 1768] id: 70
  if r70_0 ~= nil then
    anime.Remove(r70_0)
  end
  if r71_0 ~= nil then
    anime.Remove(r71_0)
  end
  if r72_0 ~= nil then
    anime.Remove(r72_0)
  end
  for r3_70, r4_70 in pairs(r18_0) do
    r4_70.stop()
    r4_70.clean()
    r4_70 = nil
  end
  if r52_0 then
    display.remove(r52_0)
    r52_0 = nil
  end
  if r59_0 then
    r59_0 = nil
  end
  r62_0 = 0
  r63_0 = 0
end
local function r130_0()
  -- line: [1773, 1807] id: 71
  local r0_71 = nil	-- notice: implicit variable refs by block#[0]
  r59_0 = r0_71
  r62_0 = 0
  r63_0 = 0
  r66_0 = _G.OrbManager.GetRemainOrb()
  r0_71 = {}
  r67_0 = r0_71
  r67_0["2"] = db.GetInventoryItem(_G.UserInquiryID, _G.LoginItems["2"].id)
  r67_0["3"] = db.GetInventoryItem(_G.UserInquiryID, _G.LoginItems["3"].id)
  r0_71 = {}
  r68_0 = r0_71
  r68_0[r12_0.TreasureboxItemKeyOrb] = r66_0
  r68_0[r12_0.TreasureboxItemKeyFlare] = r67_0["2"]
  r68_0[r12_0.TreasureboxItemKeyRewind] = r67_0["3"]
  r0_71 = {}
  r18_0 = r0_71
  r0_71 = nil
  r18_0[r19_0] = r128_0(r19_0)
  r0_71 = r128_0(r20_0)
  r18_0[r20_0] = r0_71
  local r1_71 = nil
  for r5_71 = r21_0, r21_0 + 8, 1 do
    r0_71 = r128_0(r21_0)
    r18_0[r5_71] = r0_71
  end
  events.SetRepeatTime(1)
end
function r76_0()
  -- line: [1865, 1934] id: 76
  _G.ExpManager.AddExp(r62_0)
  _G.ExpManager.SaveExp()
  statslog.LogSend(r50_0, {
    item = 1,
    have_orb = _G.OrbManager.GetRemainOrb(),
    max_orb = _G.OrbManager.GetMaxOrb(),
    have_exp = r62_0,
  })
  local r1_76 = _G.OrbManager.GetRemainOrb() - r68_0[r12_0.TreasureboxItemKeyOrb]
  local r2_76 = db.GetInventoryItem(_G.UserInquiryID, _G.LoginItems["2"].id)
  local r3_76 = db.GetInventoryItem(_G.UserInquiryID, _G.LoginItems["3"].id)
  if r2_76 == nil then
    r2_76 = 0
  end
  if r3_76 == nil then
    r3_76 = 0
  end
  local r4_76 = 0
  local r5_76 = 0
  if r68_0 ~= nil then
    if r68_0[r12_0.TreasureboxItemKeyFlare] ~= nil then
      r4_76 = r68_0[r12_0.TreasureboxItemKeyFlare]
    end
    if r68_0[r12_0.TreasureboxItemKeyRewind] ~= nil then
      r5_76 = r68_0[r12_0.TreasureboxItemKeyRewind]
    end
  end
  local r6_76 = r2_76 - r4_76
  local r7_76 = r3_76 - r5_76
  local r8_76 = {
    item = r12_0.TreasureboxItemKeyOrb,
    count = r1_76,
  }
  DebugPrint("- SPSS by add up experience dialog :")
  DebugPrint(r51_0)
  statslog.LogSend(r51_0, r8_76)
  statslog.LogSend(r51_0, {
    item = r12_0.TreasureboxItemKeyFlare,
    count = r6_76,
  })
  statslog.LogSend(r51_0, {
    item = r12_0.TreasureboxItemKeyRewind,
    count = r6_76,
  })
  r0_0.SlideOutRightEffect(r52_0, r53_0, r129_0)
  if r69_0 ~= nil then
    r69_0()
    r69_0 = nil
  end
end
return {
  Open = function(r0_72)
    -- line: [1812, 1860] id: 72
    if r0_72.stageClearCount == nil or r0_72.totalExperience == nil or r0_72.getTreasureboxList == nil or r0_72.isUsedOrb == nil then
      DebugPrint("パラメータなし")
      return 
    end
    if r0_72.closeCallbackFunc ~= nil then
      r69_0 = r0_72.closeCallbackFunc
    end
    r130_0()
    if r127_0(r0_72) == false then
      return 
    end
    r63_0 = r0_72.totalExperience
    r60_0 = r0_72.totalExperience
    r61_0 = 0
    r0_0.SlideInLeftEffect(r52_0, r53_0, function()
      -- line: [1839, 1859] id: 73
      r125_0(function()
        -- line: [1841, 1858] id: 74
        if r0_72.stageClearCount < 1 then
          r89_0(r22_0, function()
            -- line: [1845, 1852] id: 75
            r61_0 = r22_0
            r63_0 = r63_0 + r22_0
            r62_0 = r62_0 + r22_0
            r95_0(r56_0, r63_0)
            r95_0(r55_0, r62_0)
          end)
        else
          r95_0(r56_0, r63_0)
          r95_0(r55_0, r62_0)
        end
      end)
    end)
  end,
  Close = r76_0,
}
