-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r1_0 = require("common.sprite_loader").new({
  imageSheet = "common.sprites.sprite_number01",
})
local r2_0 = require("resource.char_define")
local r3_0 = nil
local r4_0 = 190
local r5_0 = 120
local r6_0 = 5
local r7_0 = 3
local r8_0 = nil
local r9_0 = nil
local r10_0 = nil
local r11_0 = nil
local r12_0 = 1.3
local r13_0 = 1
local r14_0 = {}
local r15_0 = {}
local r16_0 = {}
local r17_0 = {}
local r18_0 = nil
local function r19_0(r0_1)
  -- line: [33, 33] id: 1
  return "data/game/" .. r0_1 .. ".png"
end
local function r20_0(r0_2)
  -- line: [34, 34] id: 2
  return "data/map/" .. r0_2 .. ".png"
end
local function r21_0(r0_3)
  -- line: [35, 35] id: 3
  return r20_0(r0_3 .. _G.UILanguage)
end
local function r22_0(r0_4)
  -- line: [36, 36] id: 4
  return "data/map/interface/" .. r0_4 .. ".png"
end
local function r23_0(r0_5)
  -- line: [37, 37] id: 5
  return "data/evo/" .. r0_5 .. ".png"
end
local function r24_0(r0_6)
  -- line: [38, 38] id: 6
  return "data/help/witches/" .. r0_6 .. ".png"
end
local function r25_0(r0_7)
  -- line: [39, 39] id: 7
  return r24_0(r0_7 .. _G.UILanguage)
end
local function r26_0(r0_8)
  -- line: [40, 40] id: 8
  return "data/stage/" .. r0_8 .. ".png"
end
local function r27_0(r0_9)
  -- line: [41, 41] id: 9
  return r26_0(r0_9 .. _G.UILanguage)
end
local function r28_0(r0_10)
  -- line: [42, 42] id: 10
  return "data/evo/rankup/" .. r0_10 .. ".png"
end
local function r29_0(r0_11)
  -- line: [43, 43] id: 11
  return r28_0(r0_11 .. _G.UILanguage)
end
local r30_0 = {
  {
    sid = {
      r2_0.CharId.Daisy,
      r2_0.CharId.DaisyA
    },
    currentSidIndex = 1,
  },
  {
    sid = {
      r2_0.CharId.Becky
    },
    currentSidIndex = 1,
  },
  {
    sid = {
      r2_0.CharId.Chloe
    },
    currentSidIndex = 1,
  },
  {
    sid = {
      r2_0.CharId.Nicola
    },
    currentSidIndex = 1,
  },
  {
    sid = {
      r2_0.CharId.Chiara
    },
    currentSidIndex = 1,
  },
  {
    sid = {
      r2_0.CharId.Cecilia
    },
    currentSidIndex = 1,
  },
  {
    sid = {
      r2_0.CharId.Bianca
    },
    currentSidIndex = 1,
  },
  {
    sid = {
      r2_0.CharId.Lillian
    },
    currentSidIndex = 1,
  },
  {
    sid = {
      r2_0.CharId.Iris
    },
    currentSidIndex = 1,
  },
  {
    sid = {
      r2_0.CharId.Lyra
    },
    currentSidIndex = 1,
  },
  {
    sid = {
      r2_0.CharId.Tiana
    },
    currentSidIndex = 1,
  },
  {
    sid = {
      r2_0.CharId.Sarah
    },
    currentSidIndex = 1,
  },
  {
    sid = {
      r2_0.CharId.Luna
    },
    currentSidIndex = 1,
  },
  {
    sid = {
      r2_0.CharId.LiliLala
    },
    currentSidIndex = 1,
  },
  {
    sid = {
      r2_0.CharId.Kala
    },
    currentSidIndex = 1,
  },
  {
    sid = {
      r2_0.CharId.Amber
    },
    currentSidIndex = 1,
  },
  {
    sid = {
      r2_0.CharId.Nina
    },
    currentSidIndex = 1,
  },
  {
    sid = {
      r2_0.CharId.Jill
    },
    currentSidIndex = 1,
  },
  {
    sid = {
      r2_0.CharId.Yuiko
    },
    currentSidIndex = 1,
  },
  {
    sid = {
      r2_0.CharId.Bell
    },
    currentSidIndex = 1,
  },
  {
    sid = {
      r2_0.CharId.Yung
    },
    currentSidIndex = 1,
  }
}
local function r31_0(r0_12)
  -- line: [73, 99] id: 12
  if r10_0 then
    r10_0:removeSelf()
  end
  if r11_0 then
    transition.cancel(r11_0)
  end
  r10_0 = util.LoadTileParts(r8_0, -896, 0, db.LoadTileData(string.format("cutin%02d", r0_12), "chara"), string.format("data/cutin/%02d", r0_12))
  r10_0.alpha = 0
  r8_0:insert(1, r10_0)
  local r4_12 = 0.8
  if r10_0.width > 800 then
    r4_12 = 0.7
  end
  r11_0 = transition.to(r10_0, {
    x = _G.Width - r10_0.width * r4_12,
    alpha = 1,
    time = 1000,
    transition = easing.outExpo,
  })
end
local function r32_0(r0_13)
  -- line: [104, 106] id: 13
  r31_0(r0_13)
end
local function r33_0(r0_14, r1_14)
  -- line: [111, 127] id: 14
  if r1_14.phase == "began" then
    local r2_14 = r1_14.target
    function r2_14.call(r0_15)
      -- line: [114, 116] id: 15
      r33_0(r0_14, r0_15)
    end
    r9_0.setButton(r2_14)
  end
  if r1_14.phase == "ended" and r1_14.target and r1_14.target.id then
    r32_0(r1_14.target.id)
  end
  return false
end
local function r34_0(r0_16, r1_16, r2_16, r3_16)
  -- line: [133, 150] id: 16
  local r4_16 = 12
  local r5_16 = r1_16
  local r6_16 = r2_16 + string.len(tostring(r1_16)) * r4_16
  if r5_16 < 1 then
    r1_0.CreateImage(r0_16, r1_0.sequenceNames.Score, r1_0.frameDefines.ScoreStart, r6_16, r3_16)
    return 
  end
  while 0 < r5_16 do
    r5_16 = math.floor(r5_16 * 0.1)
    r1_0.CreateImage(r0_16, r1_0.sequenceNames.Score, r1_0.frameDefines.ScoreStart + r5_16 % 10, r6_16, r3_16)
    r6_16 = r6_16 - r4_16
  end
end
local function r35_0(r0_17, r1_17, r2_17, r3_17)
  -- line: [155, 172] id: 17
  local r4_17 = 16
  local r5_17 = r1_17
  local r6_17 = r2_17 + string.len(tostring(r1_17)) * r4_17
  if r5_17 < 1 then
    r1_0.CreateImage(r0_17, r1_0.sequenceNames.MpNumberA, r1_0.frameDefines.MpNumberAStart, r6_17, r3_17)
    return 
  end
  while 0 < r5_17 do
    r5_17 = math.floor(r5_17 * 0.1)
    r1_0.CreateImage(r0_17, r1_0.sequenceNames.MpNumberA, r1_0.frameDefines.MpNumberAStart + r5_17 % 10, r6_17, r3_17)
    r6_17 = r6_17 - r4_17
  end
end
local function r36_0(r0_18, r1_18, r2_18)
  -- line: [177, 180] id: 18
  r34_0(r1_18.imgGrp, r2_18, r1_18.x, r1_18.y)
  r0_18:insert(r1_18.imgGrp)
end
local function r37_0(r0_19, r1_19, r2_19, r3_19)
  -- line: [185, 275] id: 19
  if r0_19 == nil then
    return nil
  end
  local r4_19 = display.newGroup()
  local r5_19 = nil
  local r6_19 = nil
  if r3_19 then
    r6_19 = string.format("witches_chara_unlock%02d", r0_19.sid)
  else
    r6_19 = string.format("witches_chara_locked%02d", r0_19.sid)
  end
  local r7_19 = util.LoadParts(r4_19, r24_0(r6_19), 0, 0)
  r6_19 = r2_0.GetCharName(r0_19.sid)
  local r8_19 = display.newRect(r4_19, 10, r7_19.y + 14, r4_19.width - 20, 20)
  r8_19:setFillColor(0, 0, 0)
  r8_19.alpha = 0.64
  local r9_19 = display.newText(r4_19, r6_19, 64, 13, native.systemFontBold, 16)
  r9_19:setReferencePoint(display.CenterReferencePoint)
  r9_19.x = 80
  r9_19.y = 23.5
  local r10_19 = display.newRect(10, r7_19.height - 70 + 25, r4_19.width - 20, 31)
  r10_19:setFillColor(0, 0, 0)
  r10_19.alpha = 0.64
  r4_19:insert(r10_19)
  local r11_19 = r0_19.sid
  r14_0[r11_19] = db.LoadCharRank(_G.UserID, r11_19)
  if r11_19 == 1 then
    r3_0.UpdateCharInfo(r11_19, r14_0[r11_19].rank, r14_0[r11_19].exp, r3_19)
    r3_0.MessageDisp()
  end
  if r3_19 then
    local r12_19 = r10_19.x - r10_19.contentWidth / 2 + 4 + 20
    local r13_19 = r10_19.y - r10_19.contentHeight / 2 + 30 - 25 + 4
    local r14_19 = util.LoadParts(r4_19, r29_0("cha_icon_level_"), r12_19, r13_19)
    local r15_19 = 0
    if _G.UILanguage == "en" then
      r15_19 = 7
    end
    r16_0[r0_19.sid] = {
      imgGrp = display.newGroup(),
      x = r12_19 + r14_19.contentWidth - 20 + r15_19,
      y = r13_19,
    }
    r36_0(r4_19, r16_0[r0_19.sid], r14_0[r0_19.sid].rank)
  end
  return r4_19
end
local function r38_0(r0_20)
  -- line: [280, 293] id: 20
  local r1_20 = r9_0.getListItem(r0_20)
  if r1_20 then
    r1_20:setReferencePoint(display.CenterReferencePoint)
    r1_20:toFront()
    r1_20.xScale = r12_0
    r1_20.yScale = r12_0
    if r1_20[1] ~= nil then
      for r5_20 = 1, r1_20.panelGroup.numChildren, 1 do
        r1_20.panelGroup[r5_20].mask.alpha = 0
      end
    end
  end
end
local function r39_0(r0_21)
  -- line: [298, 309] id: 21
  local r1_21 = r9_0.getListItem(r0_21)
  if r1_21 then
    r1_21.xScale = r13_0
    r1_21.yScale = r13_0
    if r1_21[1] ~= nil then
      for r5_21 = 1, r1_21.panelGroup.numChildren, 1 do
        r1_21.panelGroup[r5_21].mask.alpha = 0.5
      end
    end
  end
end
local function r40_0(r0_22, r1_22)
  -- line: [314, 444] id: 22
  if r1_22.target.touchEnabled == false or r1_22.target.numChildren < 2 then
    return 
  end
  r1_22.target:removeEventListener("touch", r40_0)
  r1_22.target.touch = nil
  r1_22.target.touchEnabled = false
  local r2_22 = r30_0[r1_22.target.summonListIndex]
  local r3_22 = {
    front = {},
    back = {},
  }
  local r4_22 = {
    front = {},
    back = {},
  }
  local function r5_22()
    -- line: [336, 373] id: 23
    r18_0 = {}
    r18_0[1] = r3_22.front.target:toFront()
    transition.to(r3_22.front.target, {
      time = 200,
      x = r4_22.front.x,
      y = r4_22.front.y,
      transition = easing.inExpo,
      onComplete = function()
        -- line: [344, 364] id: 24
        r3_22.front.target.x = r4_22.front.x
        r3_22.front.target.y = r4_22.front.y
        r3_22.back.target.x = r4_22.back.x
        r3_22.back.target.y = r4_22.back.y
        if r18_0 ~= nil then
          if r18_0[1] ~= nil then
            transition.cancel(r18_0[1])
            r18_0[1] = nil
          end
          if r18_0[2] ~= nil then
            transition.cancel(r18_0[2])
            r18_0[2] = nil
          end
          r18_0 = nil
        end
        r1_22.target.touchEnabled = true
        r1_22.target.touch = r40_0
        r1_22.target:addEventListener("touch")
      end,
    })
    r18_0[2] = transition.to(r3_22.back.target, {
      time = 200,
      x = r4_22.back.x,
      y = r4_22.back.y,
      transition = easing.inExpo,
    })
  end
  local function r6_22()
    -- line: [376, 405] id: 25
    r18_0 = {}
    r18_0[1] = transition.to(r3_22.front.target, {
      time = 200,
      x = r3_22.front.x - 10,
      y = r3_22.front.y - 50,
      transition = easing.outExpo,
      onComplete = function()
        -- line: [383, 396] id: 26
        if r18_0 ~= nil then
          if r18_0[1] ~= nil then
            transition.cancel(r18_0[1])
            r18_0[1] = nil
          end
          if r18_0[2] ~= nil then
            transition.cancel(r18_0[2])
            r18_0[2] = nil
          end
          r18_0 = nil
        end
        r5_22()
      end,
    })
    r18_0[2] = transition.to(r3_22.back.target, {
      time = 200,
      x = r3_22.back.x + 10,
      y = r3_22.back.y + 50,
      transition = easing.outExpo,
    })
  end
  if r1_22.target[2].sid == r2_22.sid[r2_22.currentSidIndex] then
    r3_22.front.target = r1_22.target[1]
    r3_22.back.target = r1_22.target[2]
  else
    r3_22.front.target = r1_22.target[2]
    r3_22.back.target = r1_22.target[1]
  end
  r2_22.currentSidIndex = r2_22.currentSidIndex + 1
  if r2_22.currentSidIndex > 2 then
    r2_22.currentSidIndex = 1
  end
  if r3_22.front.target.pos.x < r3_22.back.target.pos.x then
    r3_22.front.x = r3_22.back.target.pos.x
    r3_22.front.y = r3_22.back.target.pos.y
    r4_22.back.x = r3_22.front.x
    r4_22.back.y = r3_22.front.y
    r3_22.back.x = r3_22.front.target.pos.x
    r3_22.back.y = r3_22.front.target.pos.y
    r4_22.front.x = r3_22.back.x
    r4_22.front.y = r3_22.back.y
  else
    r3_22.front.x = r3_22.front.target.pos.x
    r3_22.front.y = r3_22.front.target.pos.y
    r4_22.back.x = r3_22.front.x
    r4_22.back.y = r3_22.front.y
    r3_22.back.x = r3_22.back.target.pos.x
    r3_22.back.y = r3_22.back.target.pos.y
    r4_22.front.x = r3_22.back.x
    r4_22.front.y = r3_22.back.y
  end
  r6_22()
end
local function r41_0()
  -- line: [448, 456] id: 27
  if r18_0 ~= nil then
    transition.cancel(r18_0[1])
    transition.cancel(r18_0[2])
    r18_0[1] = nil
    r18_0[2] = nil
    r18_0 = nil
  end
end
local function r42_0(r0_28)
  -- line: [461, 484] id: 28
  r41_0()
  for r5_28 = 1, r0_28.getListNum(), 1 do
    local r6_28 = r0_28.getListItem(r5_28)
    if r6_28.panelGroup.numChildren > 1 then
      r6_28.panelGroup.touchEnabled = false
      r6_28.panelGroup:removeEventListener("touch", r40_0)
      if r6_28.panelGroup[2].pos.x < r6_28.panelGroup[1].pos.x then
        r6_28.panelGroup[1].x = r6_28.panelGroup[1].pos.x
        r6_28.panelGroup[1].y = r6_28.panelGroup[1].pos.y
        r6_28.panelGroup[2].x = r6_28.panelGroup[2].pos.x
        r6_28.panelGroup[2].y = r6_28.panelGroup[2].pos.y
      else
        r6_28.panelGroup[1].x = r6_28.panelGroup[2].pos.x
        r6_28.panelGroup[1].y = r6_28.panelGroup[2].pos.y
        r6_28.panelGroup[2].x = r6_28.panelGroup[1].pos.x
        r6_28.panelGroup[2].y = r6_28.panelGroup[1].pos.y
      end
    end
  end
end
local function r43_0(r0_29)
  -- line: [489, 556] id: 29
  local r1_29 = r0_29.getScrollView()
  local r2_29 = r0_29.getScrollStage()
  local r3_29 = math.abs(math.round((r1_29.x - r4_0) / r4_0))
  if r3_29 < 1 then
    r3_29 = 1
  end
  if #r30_0 < r3_29 then
    r3_29 = #r30_0
  end
  for r7_29 = 1, #r30_0, 1 do
    if r7_29 ~= r3_29 then
      r39_0(r7_29)
    end
  end
  local r4_29 = r0_29.getListItem(r3_29)
  if r4_29 then
    r38_0(math.abs(math.round((r1_29.x - r4_0) / r4_0)))
  end
  if 1 < #r30_0[r3_29].sid and r4_29.panelGroup.touchEnabled ~= nil then
    if r4_29.panelGroup.touch == nil and r18_0 == nil then
      r4_29.panelGroup.touch = r40_0
      r4_29.panelGroup:addEventListener("touch")
    end
    r4_29.panelGroup.touchEnabled = true
  end
  local r5_29 = r30_0[r3_29].sid[r30_0[r3_29].currentSidIndex]
  local r6_29 = r30_0[r3_29].state[r30_0[r3_29].currentSidIndex] ~= 3
  r14_0[r3_29] = db.LoadCharRank(_G.UserID, r5_29)
  r3_0.UpdateCharInfo(r5_29, r14_0[r3_29].rank, r14_0[r3_29].exp, r6_29)
  r3_0.MessageDisp()
  r3_0.DrawNeedExp()
  r3_0.DrawSkilInfo()
  r3_0.DrawStatusInfoNum()
  if r3_0.IsRankupChar() == true then
    r3_0.SetRankButtonActiveFlag(false)
    r3_0.SetEnableRankupButton(true)
  else
    r3_0.SetRankButtonActiveFlag(true)
    r3_0.SetEnableRankupButton(false)
  end
  if r3_0.IsRankUpExp() == false then
    r3_0.SetEnableRankupButton(false)
  end
end
local function r44_0(r0_30, r1_30, r2_30)
  -- line: [561, 696] id: 30
  local r3_30 = r0_30.getScrollView()
  local r4_30 = r4_0 * 0.5
  r3_0.MessageHide()
  if r3_30.x > 80 then
    r3_30.x = 80
  elseif r3_30.x < -(r3_30.width - 80) then
    r3_30.x = -(r3_30.width - 80)
  end
  local r5_30 = math.abs(r3_30.x)
  local r6_30 = r5_30 + r4_0 * 4
  local r7_30 = nil
  local r8_30 = nil
  local r9_30 = nil
  local r10_30 = nil
  local r11_30 = nil
  if r1_30 == 0 then
    return 
  elseif r1_30 < 0 then
    r7_30 = r5_30 + r4_30
    r8_30 = r7_30 + r4_0
    r9_30 = r4_0
    r10_30 = r4_0
    r11_30 = 0
  else
    r7_30 = r6_30 - r4_0 * 2 - r4_30
    r8_30 = r7_30 - r4_0
    r5_30 = r6_30 - r4_0
    r6_30 = r5_30 - r4_0
    r9_30 = -r4_0
    r10_30 = 0
    r11_30 = r4_0
  end
  r3_0.SetEnableRankupButton(false)
  local r12_30 = {}
  for r16_30 = r5_30, r6_30, r9_30 do
    table.insert(r12_30, math.abs(math.round((r16_30 - r4_30) / r4_0)))
  end
  r39_0(r12_30[1])
  r39_0(r12_30[4])
  r39_0(r12_30[1] - 1)
  r39_0(r12_30[4] + 1)
  local r13_30 = r0_30.getListItem(r12_30[2])
  if r13_30 then
    r13_30:setReferencePoint(display.CenterReferencePoint)
    if r13_30.x <= r7_30 and r7_30 <= r13_30.x + r4_0 then
      local r14_30 = math.abs((r10_30 - r7_30 - r13_30.x) / r4_0)
      local r15_30 = r13_0 + 0.5 * r14_30
      r13_30.xScale = r15_30
      r13_30.yScale = r15_30
      if r13_30.panelGroup ~= nil then
        for r19_30 = 1, r13_30.panelGroup.numChildren, 1 do
          r13_30.panelGroup[r19_30].mask.alpha = 0.5 - 0.5 * r14_30
        end
      end
      if r13_30.xScale < r13_0 or r13_30.yScale < r13_0 then
        r13_30.xScale = r13_0
        r13_30.yScale = r13_0
        if r13_30.panelGroup ~= nil then
          for r19_30 = 1, r13_30.panelGroup.numChildren, 1 do
            r13_30.panelGroup[r19_30].mask.alpha = 0.5
          end
        end
      end
    end
  end
  local r14_30 = r0_30.getListItem(r12_30[3])
  if r14_30 then
    r14_30:setReferencePoint(display.CenterReferencePoint)
    if r14_30.x < r8_30 and r8_30 < r14_30.x + r4_0 then
      local r15_30 = math.abs((r11_30 - r8_30 - r14_30.x) / r4_0)
      local r16_30 = r13_0 + 0.5 * r15_30
      r14_30:toFront()
      r14_30.xScale = r16_30
      r14_30.yScale = r16_30
      if r14_30.panelGroup ~= nil then
        for r20_30 = 1, r14_30.panelGroup.numChildren, 1 do
          r14_30.panelGroup[r20_30].mask.alpha = 0.5 - 0.5 * r15_30
        end
      end
      if r12_0 < r14_30.xScale or r12_0 < r14_30.yScale then
        r14_30.xScale = r12_0
        r14_30.yScale = r12_0
        if r14_30.panelGroup ~= nil then
          for r20_30 = 1, r14_30.panelGroup.numChildren, 1 do
            r14_30.panelGroup[r20_30].mask.alpha = 0
          end
        end
      end
    end
  end
end
local function r45_0(r0_31)
  -- line: [701, 776] id: 31
  local r1_31 = r0_31.col
  if r1_31.index == nil then
    return false
  end
  local r2_31 = r30_0[r1_31.index]
  local r3_31 = display.newGroup()
  local r4_31 = display.newRect(0, 0, r4_0, 1)
  r4_31.alpha = 0
  r1_31:insert(r4_31)
  r1_31:insert(r3_31)
  r1_31.panelGroup = r3_31
  if #r2_31.sid > 1 then
    r3_31.touch = r40_0
    r3_31:addEventListener("touch")
    r3_31.touchEnabled = false
  end
  r3_31.summonListIndex = r1_31.index
  local r5_31 = r2_31.state[1] ~= 3
  local r6_31 = 1
  if type(r2_31) == "table" and 1 < #r2_31.sid and r2_31.state[2] ~= 3 then
    r6_31 = 2
  end
  for r10_31 = 1, r6_31, 1 do
    local r12_31 = r37_0({
      sid = r2_31.sid[r10_31],
      state = r2_31.state[r10_31],
    }, 0, 0, r5_31)
    if r12_31 == nil then
      return false
    end
    if r6_31 > 1 then
      if r10_31 == 1 then
        r12_31.x = r12_31.x
        r12_31.y = r12_31.y - 10
      else
        r12_31.x = r12_31.x + 30
        r12_31.y = r12_31.y + 2.5
      end
      r12_31.pos = {
        x = r12_31.x,
        y = r12_31.y,
      }
    end
    local r13_31 = display.newRoundedRect(r12_31, 8, 8, r12_31.width - 16, r12_31.height - 16, 3)
    r13_31:setFillColor(0, 0, 0)
    r13_31.alpha = 0.5
    r12_31.mask = r13_31
    r12_31.sid = r2_31.sid[r10_31]
    r3_31:insert(r12_31)
    if r5_31 then
      r12_31.touch = r33_0
      r12_31:addEventListener("touch", r12_31)
    end
    r15_0[r12_31.sid] = r12_31
  end
  r3_31[r2_31.currentSidIndex]:toFront()
  return true
end
local function r46_0(r0_32, r1_32, r2_32, r3_32)
  -- line: [781, 832] id: 32
  local r4_32 = display.newGroup()
  local r5_32 = display.newGroup()
  r9_0 = require("common.gridView").new({
    grp = r5_32,
    top = 20,
    left = 334,
    width = r4_0,
    height = r5_0 * 3,
    contentWidth = r5_32.width,
    contentHeight = 347,
    onRender = r45_0,
  })
  for r9_32 = 1, #r30_0, 1 do
    r30_0[r9_32].currentSidIndex = 1
    for r13_32 = 1, #r1_32, 1 do
      if r30_0[r9_32].sid[1] == r13_32 then
        r30_0[r9_32].state = r1_32[r13_32]
        r9_0.add({})
        break
      end
    end
  end
  r9_0.addStartedScrollEventListener(r42_0)
  r9_0.addStoppedScrollEventListener(r43_0)
  r9_0.addMoveScrollEventListener(r44_0)
  local r6_32 = display.newGroup()
  display.newRect(r6_32, 0, 0, 848, 502):setFillColor(0, 0, 0, 0)
  r6_32:insert(r9_0.getScrollStage())
  r6_32:setMask(graphics.newMask(r23_0("squareMask")))
  r6_32.maskX = 414
  r6_32.maskY = 181
  r6_32:setReferencePoint(display.TopLeftReferencePoint)
  r6_32.x = r2_32
  r6_32.y = r3_32
  r4_32:insert(r6_32)
  r0_32:insert(r4_32)
  r8_0 = r4_32
end
return {
  UpdatePlateNumber = function(r0_33, r1_33, r2_33)
    -- line: [837, 866] id: 33
    if r16_0[r0_33].imgGrp ~= nil then
      local r3_33 = false
      for r7_33 = 1, #r30_0, 1 do
        if r30_0[r7_33].sid[r30_0[r7_33].currentSidIndex] == r0_33 then
          r3_33 = r30_0[r7_33].state[r30_0[r7_33].currentSidIndex] ~= 3
          break
        end
      end
      r3_0.UpdateCharInfo(r0_33, r1_33, r2_33, r3_33)
      display.remove(r16_0[r0_33].imgGrp)
      r16_0[r0_33].imgGrp = display.newGroup()
      r36_0(r15_0[r0_33], r16_0[r0_33], r1_33)
      r3_0.MessageDisp()
      r3_0.DrawOrb()
      r3_0.DrawExp()
      r3_0.DrawNeedExp()
      r3_0.DrawSkilInfo()
      r3_0.DrawStatusInfoNum()
    end
  end,
  new = function(r0_34)
    -- line: [871, 904] id: 34
    local r2_34 = 0
    local r3_34 = 0
    local r4_34 = nil
    local r1_34 = nil	-- notice: implicit variable refs by block#[10]
    if r0_34.rtImg then
      r1_34 = r0_34.rtImg
    else
      r1_34 = display.newGroup()
    end
    if r0_34.posX then
      r2_34 = r0_34.posX
    end
    if r0_34.posY then
      r3_34 = r0_34.posY
    end
    if r0_34.summonState then
      r4_34 = r0_34.summonState
    else
      local r5_34 = nil
      r4_34, r5_34 = db.LoadSummonData(_G.UserID)
    end
    r3_0 = r0_34.evo_view
    r46_0(r1_34, r4_34, r2_34, r3_34)
    r38_0(1)
  end,
  Cleanup = function()
    -- line: [906, 927] id: 35
    r41_0()
    if r9_0 then
      r9_0.clean()
      r9_0 = nil
    end
    if r10_0 then
      r10_0:removeSelf()
      r10_0 = nil
    end
    if r11_0 then
      transition.cancel(r11_0)
      r11_0 = nil
    end
    if r8_0 then
      r8_0:removeSelf()
      r8_0 = nil
    end
  end,
}
