-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("common.effect.h_slide_in_out")
local r1_0 = require("common.sprite_loader").new({
  imageSheet = "common.sprites.sprite_evo_combi_number01",
})
local r2_0 = require("resource.char_define")
local r3_0 = 2
local r4_0 = 2
local r5_0 = 1
local r6_0 = 2
local r7_0 = 114
local r8_0 = 296
local r9_0 = r8_0 + 30
local r10_0 = 20
local r11_0 = 684
local r12_0 = 176
local r13_0 = r12_0 + 30
local r14_0 = 20
local r15_0 = 0
local r16_0 = 1
local r17_0 = 2
local r18_0 = 3
local r19_0 = nil
local r20_0 = nil
local r21_0 = nil
local r22_0 = r15_0
local r23_0 = nil
local function r24_0(r0_1)
  -- line: [53, 53] id: 1
  return "data/help/witches/" .. r0_1 .. ".png"
end
local function r25_0(r0_2)
  -- line: [54, 54] id: 2
  return "data/evo/evo_combi/" .. r0_2 .. ".png"
end
local function r26_0(r0_3)
  -- line: [55, 55] id: 3
  return r25_0(r0_3 .. _G.UILanguage)
end
local function r27_0(r0_4)
  -- line: [60, 67] id: 4
  if r0_4.func == nil or r0_4.func.rankTable == nil then
    return nil
  end
  return r0_4.func.rankTable
end
local function r29_0(r0_6)
  -- line: [82, 102] id: 6
  if r19_0 == nil or #r19_0 < 1 then
    return 
  end
  local r1_6 = r19_0[#r19_0]
  if r0_6.type == r1_6.type then
    return true
  end
  if r0_6.type == r2_0.CharId.Daisy and r1_6.type == r2_0.CharId.DaisyA or r0_6.type == r2_0.CharId.DaisyA and r1_6.type == r2_0.CharId.Daisy then
    return true
  end
  return false
end
local function r32_0(r0_9, r1_9, r2_9)
  -- line: [155, 158] id: 9
  r0_9.AddEvoDurationTime(r1_9, r2_9)
end
local function r33_0(r0_10, r1_10, r2_10)
  -- line: [163, 166] id: 10
  r0_10.AddEvoDropRate(r1_10, r2_10)
end
local function r34_0(r0_11)
  -- line: [171, 174] id: 11
  game.AddMp(r0_11)
end
local function r35_0(r0_12)
  -- line: [179, 182] id: 12
  game.AddHp(r0_12)
end
local function r36_0(r0_13, r1_13)
  -- line: [187, 220] id: 13
  local function r2_13(r0_14)
    -- line: [189, 211] id: 14
    if r0_14[2].dtm ~= nil then
      local r1_14 = nil
      local r2_14 = nil
      for r6_14, r7_14 in pairs(r19_0) do
        r32_0(r0_13, r7_14, r0_14[2].dtm * 0.001)
      end
    elseif r0_14[2].rdrt ~= nil then
      local r1_14 = nil
      local r2_14 = nil
      for r6_14, r7_14 in pairs(r19_0) do
        r33_0(r0_13, r7_14, r0_14[2].rdrt)
      end
    elseif r0_14[2].admp ~= nil then
      r34_0(r0_14[2].admp)
    elseif r0_14[2].rchp ~= nil then
      r35_0(r0_14[2].rchp)
    end
  end
  local r3_13 = nil
  local r4_13 = nil
  for r8_13, r9_13 in pairs(r1_13) do
    r2_13(r9_13.GetCombiSkillEffectData())
  end
end
local function r37_0(r0_15)
  -- line: [225, 238] id: 15
  local r1_15 = display.newGroup()
  local r2_15 = 50
  local r3_15 = r0_15
  local r4_15 = string.len(tostring(r0_15)) * r2_15
  repeat
    r3_15 = math.floor(r3_15 * 0.1)
    r1_0.CreateImage(r1_15, r1_0.sequenceNames.evoCombiNum, r1_0.frameDefines.evoCombiNumStart + r3_15 % 10, r4_15, 0)
    r4_15 = r4_15 - r2_15
  until r3_15 <= 0
  return r1_15
end
local function r38_0(r0_16)
  -- line: [243, 296] id: 16
  local r1_16 = display.newGroup()
  display.newRect(r1_16, 0, 0, 545, 1).alpha = 0
  local r2_16 = util.LoadParts(r1_16, r0_16[1])
  if r0_16[2].dtm ~= nil then
    local r3_16 = r1_0.CreateImage(r1_16, r1_0.sequenceNames.evoCombiNum, r1_0.frameDefines.evoCombiSec, 0, 0)
    local r4_16 = r1_0.CreateImage(r1_16, r1_0.sequenceNames.evoCombiNum, r1_0.frameDefines.evoCombiPlus, 0, 0)
    local r5_16 = r37_0(math.floor(r0_16[2].dtm * 0.001))
    r1_16:insert(r5_16)
    r3_16:setReferencePoint(display.TopRightReferencePoint)
    r4_16:setReferencePoint(display.TopRightReferencePoint)
    r5_16:setReferencePoint(display.TopRightReferencePoint)
    r3_16.x = r1_16.width
    r5_16.x = r1_16.width - r3_16.width
    r4_16.x = r1_16.width - r3_16.width - r5_16.width
  elseif r0_16[2].rdrt ~= nil then
    local r3_16 = nil
    local r5_16 = display.newGroup()
    for r9_16 = 1, r0_16[2].rdrt * 10, 1 do
      local r10_16 = r1_0.CreateImage(r5_16, r1_0.sequenceNames.evoCombiNum, r1_0.frameDefines.evoCombiPlus, 0, 0)
      r10_16.x = (r9_16 - 1) * (r10_16.width - 10)
    end
    r1_16:insert(r5_16)
    r5_16:setReferencePoint(display.TopLeftReferencePoint)
    r5_16.x = r2_16.width - 10
  elseif r0_16[2].admp ~= nil then
    local r3_16 = r1_0.CreateImage(r1_16, r1_0.sequenceNames.evoCombiNum, r1_0.frameDefines.evoCombiPlus, 0, 0)
    local r4_16 = r37_0(r0_16[2].admp)
    r1_16:insert(r4_16)
    r3_16:setReferencePoint(display.TopRightReferencePoint)
    r4_16:setReferencePoint(display.TopRightReferencePoint)
    r4_16.x = r1_16.width
    r3_16.x = r1_16.width - r4_16.width
  elseif r0_16[2].rchp ~= nil then
    local r3_16 = r1_0.CreateImage(r1_16, r1_0.sequenceNames.evoCombiNum, r1_0.frameDefines.evoCombiPlus, 0, 0)
    local r4_16 = r37_0(r0_16[2].rchp)
    r1_16:insert(r4_16)
    r3_16:setReferencePoint(display.TopRightReferencePoint)
    r4_16:setReferencePoint(display.TopRightReferencePoint)
    r4_16.x = r1_16.width
    r3_16.x = r1_16.width - r4_16.width
  end
  return r1_16
end
local function r39_0(r0_17, r1_17, r2_17)
  -- line: [303, 363] id: 17
  r22_0 = r15_0
  if r23_0 ~= nil then
    r23_0.Clean()
    if r23_0.slideTarget ~= nil then
      display.remove(r23_0.slideTarget)
      r23_0.slideTarget = nil
    end
    r23_0 = nil
  end
  local r3_17 = display.newGroup()
  local r4_17 = nil
  local r5_17 = nil
  for r9_17, r10_17 in pairs(r1_17) do
    local r11_17 = r38_0(r10_17.GetCombiSkillEffectData())
    r3_17:insert(r11_17)
    r11_17.y = (r9_17 - 1) * r11_17.height
  end
  r3_17:setReferencePoint(display.CenterReferencePoint)
  r23_0 = r0_0.new({
    slideTarget = r3_17,
    startPosX = -r3_17.width * 0.5,
    stopPosX = _G.Width * 0.5,
    endPosX = _G.Width + r3_17.width,
    fixPosY = _G.Height * 0.5,
    startAlpha = 0,
    stopAlpha = 1,
    endAlpha = 0,
    start2StopTime = 13,
    stopTime = 44,
    stop2EndTime = 10,
    endListener = function()
      -- line: [339, 357] id: 18
      display.remove(r3_17)
      r3_17 = nil
      r22_0 = r15_0
      if r21_0 ~= nil then
        Runtime:removeEventListener("touch", r21_0)
        display.remove(r21_0)
        r21_0 = nil
      end
      r36_0(r0_17, r1_17)
      if r2_17 ~= nil then
        r2_17()
      end
    end,
  })
  r23_0.Play()
  r22_0 = r18_0
end
local function r40_0(r0_19, r1_19, r2_19)
  -- line: [368, 424] id: 19
  local r3_19 = display.newGroup()
  display.newRect(r3_19, 0, 0, _G.Width, 1).alpha = 0
  local r4_19 = 0
  local r5_19 = 0
  local r6_19 = 0
  local r7_19 = 0
  local r8_19 = 0
  local r9_19 = 0
  local r10_19 = nil
  if r0_19 == r5_0 then
    r4_19 = r7_0
    r6_19 = r8_0
    r8_19 = r9_0
    r9_19 = r10_0
    r10_19 = "cha_fukidasi_top"
  elseif r0_19 == r6_0 then
    r4_19 = r11_0
    r6_19 = r12_0
    r8_19 = r13_0
    r9_19 = r14_0
    r10_19 = "cha_fukidasi_bottom"
  else
    return 
  end
  local r11_19 = util.LoadParts(r3_19, r24_0(r1_19), r4_19, r5_19)
  local r12_19 = display.newGroup()
  local r13_19 = util.LoadParts(r12_19, r25_0(r10_19), 0, 0)
  r3_19:insert(r12_19)
  r12_19.x = r6_19
  r12_19.y = r7_19
  r3_19.balloonGroup = r12_19
  util.MakeText3({
    rtImg = r3_19,
    size = 20,
    color = {
      0,
      0,
      0
    },
    msg = r2_19,
    x = r8_19,
    y = r9_19,
    width = 426,
    height = 110,
    diff_x = 0,
    diff_y = 0,
  })
  return r3_19
end
local function r41_0()
  -- line: [429, 436] id: 20
  return db.LoadEvoCombiSkillTalkData({
    sid01 = r19_0[1].type,
    sid02 = r19_0[2].type,
  })
end
return {
  Init = function()
    -- line: [682, 684] id: 30
    r19_0 = {}
  end,
  Clean = function()
    -- line: [670, 677] id: 29
    if r19_0 ~= nil then
      repeat
        table.remove(r19_0)
      until #r19_0 <= 0
      r19_0 = nil
    end
  end,
  AddChar = function(r0_7)
    -- line: [110, 130] id: 7
    if r0_7.evo == nil or r0_7.evo.evoLevel == nil or r0_7.evo.evoLevel < r3_0 then
      return false
    end
    if r29_0(r0_7) == true then
      table.remove(r19_0, #r19_0)
    end
    table.insert(r19_0, r0_7)
    if r4_0 < #r19_0 then
      table.remove(r19_0, 1)
    end
    return true
  end,
  RemoveChar = function(r0_8)
    -- line: [135, 150] id: 8
    local r3_8 = 0
    for r7_8, r8_8 in pairs(r19_0) do
      if r0_8.uid == r8_8.uid then
        r3_8 = r7_8
        break
      end
    end
    if r3_8 ~= 0 then
      table.remove(r19_0, r3_8)
    end
  end,
  IsCombiSkill = function()
    -- line: [72, 77] id: 5
    if r4_0 <= #r19_0 then
      return true
    end
    return false
  end,
  PlayCombiSkillEffect = function(r0_21, r1_21)
    -- line: [441, 665] id: 21
    local r2_21 = r27_0(r19_0[1])
    local r3_21 = r27_0(r19_0[2])
    if r2_21 == nil or r3_21 == nil then
      return 
    end
    if r20_0 ~= nil then
      display.remove(r20_0)
      r20_0 = nil
    end
    local r4_21 = r41_0()
    local r5_21 = nil
    local r6_21 = nil
    local r7_21 = nil
    local r8_21 = nil
    if r4_21[1].sid == r19_0[1].type then
      r8_21 = r2_21
      r7_21 = r3_21
      r6_21 = r4_21[1]
      r5_21 = r4_21[2]
    else
      r8_21 = r3_21
      r7_21 = r2_21
      r6_21 = r4_21[1]
      r5_21 = r4_21[2]
    end
    local r9_21 = display.newGroup()
    r9_21.isVisible = false
    local r10_21 = r40_0(r6_0, r7_21.GetCombiSkillData().ipth, r5_21.msg)
    if r10_21 == nil then
      return 
    end
    local r11_21 = nil
    local function r12_21()
      -- line: [490, 532] id: 22
      local r3_22 = nil	-- notice: implicit variable refs by block#[0]
      local r0_22 = display.newGroup()
      local r1_22 = util.LoadParts(r0_22, r25_0("text_arrow"), 0, 0)
      r10_21.balloonGroup:insert(r0_22)
      r0_22.x = r10_21.balloonGroup.width - 50
      r0_22.y = 110
      local r2_22 = {
        startPosY = 0,
        endPosY = 5,
        ms = 0,
        endMs = 40,
        turnMs = 20,
      }
      function r3_22(r0_23, r1_23, r2_23)
        -- line: [508, 529] id: 23
        r1_23.ms = r1_23.ms + r2_23
        if r1_23.endMs <= r1_23.ms then
          r1_23.ms = 0
          r3_22(r0_23, r2_22, 0)
          return true
        end
        local r3_23 = nil
        if r1_23.ms < r1_23.turnMs then
          r3_23 = easing.outExpo(r1_23.ms, r1_23.turnMs, r1_23.startPosY, r1_23.endPosY)
        else
          r3_23 = easing.inExpo(r1_23.ms, r1_23.endMs, r1_23.endPosY, -r1_23.endPosY)
        end
        r1_22.y = r3_23
        return true
      end
      r11_21 = events.Register(r3_22, r2_22, 0)
    end
    r9_21:insert(r10_21)
    r9_21:setReferencePoint(display.CenterReferencePoint)
    local r13_21 = r0_0.new({
      slideTarget = r9_21,
      startPosX = _G.Width + r9_21.width * 0.5,
      stopPosX = _G.Width * 0.5,
      endPosX = -r9_21.width * 0.5,
      fixPosY = 368 + r9_21.height * 0.5,
      startAlpha = 0,
      stopAlpha = 1,
      endAlpha = 0,
      start2StopTime = 13,
      stopTime = 44,
      stop2EndTime = 10,
      pauseAtStopFlag = true,
      stopListener = function()
        -- line: [549, 552] id: 24
        r12_21()
      end,
      endListener = function()
        -- line: [553, 559] id: 25
        display.remove(r9_21)
        r9_21 = nil
        r39_0(r0_21, {
          r8_21,
          r7_21
        }, r1_21)
      end,
    })
    local r14_21 = display.newGroup()
    r14_21.isVisible = false
    local r15_21 = r40_0(r5_0, r8_21.GetCombiSkillData().ipth, r6_21.msg)
    if r15_21 == nil then
      return 
    end
    r14_21:insert(r15_21)
    r14_21:setReferencePoint(display.CenterReferencePoint)
    local r16_21 = r0_0.new({
      slideTarget = r14_21,
      startPosX = -r14_21.width * 0.5,
      stopPosX = _G.Width * 0.5,
      endPosX = _G.Width + r14_21.width,
      fixPosY = 118 + r14_21.height * 0.5,
      startAlpha = 0,
      stopAlpha = 1,
      endAlpha = 0,
      start2StopTime = 13,
      stopTime = 75,
      stop2EndTime = 10,
      pauseAtStopFlag = true,
      endListener = function()
        -- line: [585, 588] id: 26
        display.remove(r14_21)
        r14_21 = nil
      end,
    })
    r20_0 = display.newGroup()
    local r17_21 = nil
    r21_0 = display.newGroup()
    r21_0:addEventListener("touch", function(r0_27)
      -- line: [600, 638] id: 27
      if r0_27.phase == "ended" then
        if r22_0 == r16_0 or r22_0 == r17_0 then
          if r16_21.IsPlay() == false and r13_21.IsPlay() == false and r13_21.GetAnimationPhase() == r0_0.AnimationPhaseCountStop then
            if r11_21 ~= nil then
              events.Delete(r11_21)
              r11_21 = nil
            end
            r16_21.Play(r0_0.AnimationPhaseCountEnd)
            r13_21.Play(r0_0.AnimationPhaseCountEnd)
          end
          if r16_21 ~= nil and r16_21.GetAnimationPhase() == r0_0.AnimationPhaseCountStart then
            r16_21.Play(r0_0.AnimationPhaseCountStop)
          end
          if r13_21 ~= nil and r13_21.GetAnimationPhase() == r0_0.AnimationPhaseCountStart then
            r13_21.Play(r0_0.AnimationPhaseCountStop)
          end
        elseif r22_0 == r18_0 and r23_0 ~= nil and r23_0.GetAnimationPhase() <= r0_0.AnimationPhaseCountEnd then
          r23_0.CancelPlay()
        end
      end
      return true
    end)
    display.newRect(r21_0, 0, 0, _G.Width, _G.Height).alpha = 0.01
    r20_0:insert(r14_21)
    r20_0:insert(r9_21)
    r14_21.isVisible = true
    r16_21.Play()
    r22_0 = r16_0
    r17_21 = {
      ms = 0,
      targetTime = 30,
    }
    r17_21.ev = events.Register(function(r0_28, r1_28, r2_28)
      -- line: [654, 664] id: 28
      r1_28.ms = r1_28.ms + r2_28
      if r1_28.targetTime <= r1_28.ms then
        events.Delete(r1_28.ev)
        r1_28.ev = nil
        r9_21.isVisible = true
        r13_21.Play()
        r22_0 = r17_0
      end
    end, r17_21, 0)
  end,
  GetCombiCharList = function()
    -- line: [703, 705] id: 31
    return r19_0
  end,
}
