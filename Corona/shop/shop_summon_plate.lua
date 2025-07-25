-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("resource.char_define")
local r1_0 = nil
local r2_0 = 192
local r3_0 = 200
local r4_0 = 5
local r5_0 = 3
local r6_0 = nil
local r7_0 = nil
local r8_0 = nil
local r9_0 = nil
local r10_0 = nil
local r11_0 = nil
local r12_0 = nil
local function r13_0(r0_1)
  -- line: [23, 23] id: 1
  return "data/powerup/" .. r0_1 .. ".png"
end
local function r14_0(r0_2)
  -- line: [24, 24] id: 2
  return r13_0(r0_2 .. _G.UILanguage)
end
local function r15_0(r0_3)
  -- line: [25, 25] id: 3
  return "data/shop/" .. r0_3 .. ".png"
end
local function r16_0(r0_4)
  -- line: [26, 26] id: 4
  return r15_0(r0_4 .. _G.UILanguage)
end
local function r17_0(r0_5)
  -- line: [27, 27] id: 5
  return "data/shop/" .. r0_5 .. ".png"
end
local function r18_0(r0_6)
  -- line: [28, 28] id: 6
  return r17_0(r0_6 .. _G.UILanguage)
end
local r19_0 = nil
local r20_0 = nil
local r21_0 = nil
local r22_0 = {
  {
    sid = 24,
    img = "item_pack01",
  },
  {
    sid = 23,
    img = "item_chara09",
  },
  {
    sid = 22,
    img = "item_chara08",
  },
  {
    sid = 21,
    img = "item_chara07",
  },
  {
    sid = 11,
    img = "item_chara01",
  },
  {
    sid = 12,
    img = "item_chara02",
  },
  {
    sid = 14,
    img = "item_chara03",
  },
  {
    sid = 17,
    img = "item_chara04",
  },
  {
    sid = 18,
    img = "item_chara05",
  },
  {
    sid = 19,
    img = "item_chara06",
  },
  {
    sid = 2,
    img = "item_chara12",
  },
  {
    sid = 3,
    img = "item_chara13",
  },
  {
    sid = 4,
    img = "item_chara14",
  },
  {
    sid = 5,
    img = "item_chara15",
  },
  {
    sid = 6,
    img = "item_chara16",
  },
  {
    sid = 7,
    img = "item_chara17",
  },
  {
    sid = 8,
    img = "item_chara18",
  },
  {
    sid = 9,
    img = "item_chara19",
  },
  {
    sid = 10,
    img = "item_chara20",
  },
  {
    sid = 13,
    img = "item_chara21",
  }
}
local function r23_0(r0_7, r1_7)
  -- line: [63, 133] id: 7
  if r19_0 then
    if r19_0.buy_button then
      r19_0.buy_button.isVisible = false
    end
    if r19_0.select_window then
      r19_0.select_window.isVisible = false
    end
    if r19_0.crystal_icon then
      r19_0.crystal_icon.isVisible = true
    end
    if r19_0.crystal_num then
      r19_0.crystal_num.isVisible = true
    end
    r19_0 = nil
  end
  if r10_0 then
    display.remove(r10_0)
  end
  if r0_7 == nil then
    return 
  end
  if r1_7 == true then
    r19_0 = r0_7
    r1_0.SetDetailBtn(true, r0_7.sid)
  else
    r19_0 = nil
  end
  if r0_7.buy_button then
    r0_7.buy_button.isVisible = r1_7
  end
  if r0_7.select_window then
    r0_7.select_window.isVisible = r1_7
  end
  if r0_7.crystal_icon then
    r0_7.crystal_icon.isVisible = not r1_7
  end
  if r0_7.crystal_num then
    r0_7.crystal_num.isVisible = not r1_7
  end
  local r3_7 = db.GetMessage(r0_0.CharInformation[r0_7.sid][4])
  local r4_7 = nil
  local r5_7 = nil
  local r6_7 = nil
  if _G.UILanguage == "jp" then
    r6_7 = 386
    r4_7 = 24
    r5_7 = 36
  else
    r6_7 = 382
    r4_7 = 20
    r5_7 = 24
  end
  r10_0 = util.MakeText2({
    rtImg = r12_0,
    size = r4_7,
    line = r5_7,
    x = 16,
    y = r6_7,
    color = {
      255,
      255,
      255
    },
    shadow = {
      0,
      0,
      0
    },
    msg = r3_7,
  })
end
local function r24_0(r0_8)
  -- line: [138, 141] id: 8
  r23_0(r0_8, true)
end
local function r25_0(r0_9, r1_9)
  -- line: [146, 160] id: 9
  if r1_9.phase == "began" then
    local r2_9 = r1_9.target
    function r2_9.call(r0_10)
      -- line: [149, 151] id: 10
      r25_0(r0_9, r0_10)
    end
    r7_0.setButton(r2_9)
  end
  if r1_9.phase == "ended" then
    r24_0(r0_9)
  end
  return false
end
local function r26_0(r0_11)
  -- line: [162, 164] id: 11
  r1_0.BuyChara(r0_11)
end
local function r27_0(r0_12, r1_12, r2_12)
  -- line: [169, 225] id: 12
  if r0_12 == nil or r0_12.state == 0 then
    return nil
  end
  local r3_12 = display.newGroup()
  local r4_12 = nil
  local r5_12 = nil
  r5_12 = r16_0(r0_12.img)
  if r0_12.sid == r0_0.CharId.Yung then
    util.LoadParts(r3_12, r5_12, 0, 5)
  else
    util.LoadParts(r3_12, r5_12, 0, 0)
  end
  if r0_12.sid >= 16 then
    r5_12 = db.GetMessage(59 + r0_12.sid - 1)
  else
    r5_12 = db.GetMessage(59 + r0_12.sid)
  end
  local r6_12 = util.LoadParts(r3_12, r13_0("powerup_crystal1"), 0, 228)
  r6_12.x = r3_12.width * 0.5 - 35
  local r8_12 = util.MakeText3({
    rtImg = r3_12,
    x = 0,
    y = 228,
    size = 28,
    color = {
      204,
      0,
      68
    },
    shadow = {
      0,
      0,
      0
    },
    diff_x = 1,
    diff_y = 1,
    msg = string.format("%d", util.ConvertDisplayCrystal(r0_0.CharInformation[r0_12.sid][3])),
  })
  r8_12.x = r3_12.width * 0.5 - 1
  r3_12.crystal_icon = r6_12
  r3_12.crystal_num = r8_12
  local r9_12 = util.LoadBtn({
    rtImg = r3_12,
    fname = r18_0("cha_buy_"),
    x = 0,
    y = 224,
    func = r26_0,
    param = {
      sid = r0_12.sid,
      img = r3_12,
    },
  })
  r9_12.x = r3_12.width * 0.5 - 1
  r9_12.isVisible = false
  r3_12.id = r0_12.sid
  r3_12.buy_button = r9_12
  local r10_12 = nil
  if r0_12.sid == r0_0.CharId.Yung then
    r10_12 = util.LoadParts(r3_12, r17_0("item_select2"), 0, 5)
  else
    r10_12 = util.LoadParts(r3_12, r17_0("item_select1"), 0, 0)
  end
  r10_12.isVisible = false
  r3_12.select_window = r10_12
  return r3_12
end
local function r28_0(r0_13)
  -- line: [230, 236] id: 13
  local r1_13 = r7_0.getListItem(r0_13)
  if r1_13 then
    r1_13:setReferencePoint(display.CenterReferencePoint)
    r1_13:toFront()
  end
end
local function r29_0(r0_14)
  -- line: [241, 260] id: 14
  r1_0.SetDetailBtn(false)
  if r19_0 then
    if r19_0.buy_button then
      r19_0.buy_button.isVisible = false
    end
    if r19_0.crystal_icon then
      r19_0.crystal_icon.isVisible = true
    end
    if r19_0.crystal_num then
      r19_0.crystal_num.isVisible = true
    end
    if r19_0.select_window then
      r19_0.select_window.isVisible = false
    end
  end
  if r10_0 then
    display.remove(r10_0)
  end
end
local function r30_0(r0_15)
  -- line: [265, 267] id: 15
end
local function r31_0(r0_16, r1_16, r2_16)
  -- line: [272, 283] id: 16
  local r3_16 = r0_16.getScrollView()
  local r4_16 = r2_0 * 0.5
  if r3_16.x > 96 then
    r3_16.x = 96
  elseif r3_16.x < -(r3_16.width - 96) then
    r3_16.x = -(r3_16.width - 96)
  end
end
local function r32_0(r0_17)
  -- line: [288, 320] id: 17
  local r1_17 = r0_17.col
  if r1_17.index == nil then
    return false
  end
  local r2_17 = r22_0[r0_17.index]
  if r2_17 and r2_17.state == 0 then
    return false
  end
  local r3_17 = r27_0(r2_17, 0, 0)
  if r3_17 == nil then
    return false
  end
  r1_17:insert(r3_17)
  r1_17.id = r2_17.sid
  r3_17.sid = r2_17.sid
  r3_17.touch = r25_0
  r3_17:addEventListener("touch", r3_17)
  if r10_0 then
    r10_0:removeSelf()
    r10_0 = nil
  end
  return true
end
local function r33_0(r0_18, r1_18, r2_18)
  -- line: [325, 370] id: 18
  local r3_18 = display.newGroup()
  r7_0 = require("common.gridView").new({
    grp = r3_18,
    top = 0,
    left = 0,
    width = r2_0,
    height = r3_0 * 3,
    contentWidth = r3_18.width,
    contentHeight = 347,
    onRender = r32_0,
  })
  for r7_18 = 1, #r22_0, 1 do
    r22_0[r7_18].state = db.GetIsLockSummonData(_G.UserID, r22_0[r7_18].sid)
    r7_0.add({
      index = r7_18,
    })
  end
  r7_0.addStartedScrollEventListener(r29_0)
  r7_0.addStoppedScrollEventListener(r30_0)
  r7_0.addMoveScrollEventListener(r31_0)
  local r4_18 = display.newGroup()
  display.newRect(r4_18, 0, 0, 848, 502):setFillColor(0, 0, 0, 0)
  r4_18:insert(r7_0.getScrollStage())
  r4_18:setMask(graphics.newMask(r15_0("squareMask")))
  r4_18.maskX = 414
  r4_18.maskY = 181
  r4_18:setReferencePoint(display.TopLeftReferencePoint)
  r4_18.x = r1_18
  r4_18.y = r2_18
  r12_0:insert(r4_18)
  r0_18:insert(r12_0)
  r6_0 = r12_0
end
return {
  new = function(r0_19)
    -- line: [375, 412] id: 19
    r12_0 = display.newGroup()
    r20_0 = r0_19
    r1_0 = r0_19.shop_view
    local r1_19 = nil
    local r2_19 = 0
    local r3_19 = 0
    r10_0 = util.MakeText3({
      rtImg = r12_0,
      size = 28,
      color = {
        184,
        163,
        191
      },
      shadow = {
        0,
        0,
        0
      },
      diff_x = 1,
      diff_y = 1,
      msg = db.GetMessage(222),
    })
    r10_0:setReferencePoint(display.CenterReferencePoint)
    r10_0.x = _G.Width / 2
    r10_0.y = _G.Height / 2 - 50
    if r0_19.rtImg then
      r1_19 = r0_19.rtImg
    else
      r1_19 = display.newGroup()
    end
    if r0_19.posX then
      r2_19 = r0_19.posX
    end
    if r0_19.posY then
      r3_19 = r0_19.posY
    end
    r33_0(r1_19, r2_19, r3_19)
    r28_0(1)
  end,
  Cleanup = function()
    -- line: [414, 425] id: 20
    if r7_0 then
      r7_0.clean()
      r7_0:removeSelf()
      r7_0 = nil
    end
    if r6_0 then
      r6_0:removeSelf()
      r6_0 = nil
    end
  end,
}
