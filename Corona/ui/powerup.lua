-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("tool.crystal")
local r1_0 = require("json")
local r2_0 = require("logic.pay_item_data")
local r3_0 = nil
local r4_0 = nil
local r5_0 = nil
local r6_0 = nil
local r7_0 = nil
local r8_0 = nil
local r9_0 = nil
local r10_0 = nil
local r11_0 = 1
local r12_0 = nil
local r13_0 = nil
local r14_0 = nil
local r15_0 = nil
local function r16_0(r0_1)
  -- line: [25, 25] id: 1
  return "data/powerup/" .. r0_1 .. ".png"
end
local function r17_0(r0_2)
  -- line: [26, 26] id: 2
  return r16_0(r0_2 .. _G.UILanguage)
end
local function r18_0(r0_3)
  -- line: [27, 27] id: 3
  return "data/option/" .. r0_3 .. ".png"
end
local function r19_0(r0_4, r1_4)
  -- line: [29, 46] id: 4
  local r2_4 = nil	-- notice: implicit variable refs by block#[3]
  if type(r1_4) == "string" then
    r2_4 = r1_4
  else
    r2_4 = util.Num2Column(r1_4)
  end
  local r3_4 = display.newGroup()
  local r4_4 = nil
  display.newText(r3_4, r2_4, 1, 1, native.systemFontBold, 40):setFillColor(0, 0, 0)
  display.newText(r3_4, r2_4, 0, 0, native.systemFontBold, 40):setFillColor(255, 225, 76)
  r0_4:insert(r3_4)
  return r3_4
end
local function r20_0(r0_5, r1_5)
  -- line: [48, 62] id: 5
  for r6_5, r7_5 in pairs(r5_0) do
    local r2_5 = r7_5.rtImg.stageBounds
    if r2_5.xMin <= r0_5 and r0_5 <= r2_5.xMax and r2_5.yMin <= r1_5 and r1_5 <= r2_5.yMax then
      if r7_5.disable then
        return nil
      else
        return r7_5.id, r7_5
      end
    end
  end
  return nil
end
local function r21_0(r0_6)
  -- line: [68, 81] id: 6
  local r1_6 = true
  for r5_6, r6_6 in pairs(r5_0) do
    if r6_6.id == r0_6 then
      r6_6.select.isVisible = true
      r6_6.msg.isVisible = true
      r1_6 = false
    else
      r6_6.select.isVisible = false
      r6_6.msg.isVisible = false
    end
  end
  r13_0.isVisible = r1_6
end
local function r22_0(r0_7)
  -- line: [83, 92] id: 7
  for r4_7, r5_7 in pairs(r5_0) do
    if r5_7.id == r0_7 and not r5_7.disable then
      r5_7.check[1].isVisible = true
      r5_7.check[2].isVisible = false
      r5_7.enable = false
      break
    end
  end
end
local function r23_0(r0_8)
  -- line: [95, 122] id: 8
  if r0_8 == 38 then
    r22_0(17)
    r22_0(18)
    r22_0(19)
    r22_0(20)
    r22_0(21)
    r22_0(22)
  elseif r0_8 == 17 then
    r22_0(18)
    r22_0(38)
  elseif r0_8 == 18 then
    r22_0(17)
    r22_0(38)
  elseif r0_8 == 19 then
    r22_0(20)
    r22_0(38)
  elseif r0_8 == 20 then
    r22_0(19)
    r22_0(38)
  elseif r0_8 == 21 then
    r22_0(22)
    r22_0(38)
  elseif r0_8 == 22 then
    r22_0(21)
    r22_0(38)
  end
end
local function r24_0(r0_9, r1_9)
  -- line: [124, 282] id: 9
  local r2_9 = display.newGroup()
  local r3_9 = r1_9.top or 0
  local r4_9 = r1_9.bottom or _G.Height
  local r5_9 = r1_9.left or 0
  local r6_9 = r1_9.right or _G.Width
  local r7_9 = r6_9 - r5_9
  local r8_9 = display.newRect(r2_9, 0, 0, r7_9, r4_9 - r3_9)
  r8_9:setFillColor(0, 0, 0)
  r8_9.alpha = 0.01
  r0_9:insert(r2_9)
  r2_9:setReferencePoint(display.TopLeftReferencePoint)
  r2_9.x = r5_9
  r2_9.y = r3_9
  r2_9.left = r5_9
  r2_9.top = r3_9
  r2_9.right = r6_9
  r2_9.bottom = r4_9
  r2_9.ev = events.Register(function(r0_11, r1_11, r2_11)
    -- line: [223, 259] id: 11
    local r4_11 = 0.9
    local r5_11 = r0_11.ev.time - r1_11.lastTime
    r1_11.lastTime = r1_11.lastTime + r5_11
    if math.abs(r1_11.velocity) < 0.01 then
      r1_11.velocity = 0
      events.Disable(r1_11.ev, true)
    end
    r1_11.velocity = r1_11.velocity * r4_11
    r1_11.x = math.floor(r1_11.x + r1_11.velocity * r5_11)
    local r6_11 = r1_11.left
    local r7_11 = _G.Width - r7_9
    if r6_11 < r1_11.x then
      r1_11.velocity = 0
      events.Disable(r1_11.ev, true)
      r1_11.tween = transition.to(r1_11, {
        time = 400,
        x = r6_11,
        transition = easing.outQuad,
      })
    elseif r1_11.x < r7_11 and r7_11 < 0 then
      r1_11.velocity = 0
      events.Disable(r1_11.ev, true)
      r1_11.tween = transition.to(r1_11, {
        time = 400,
        x = r7_11,
        transition = easing.outQuad,
      })
    elseif r1_11.x < r7_11 then
      r1_11.velocity = 0
      events.Disable(r1_11.ev, true)
      r1_11.tween = transition.to(r1_11, {
        time = 400,
        x = r6_11,
        transition = easing.outQuad,
      })
    end
    return true
  end, r2_9, 0, true)
  r2_9.vev = events.Register(function(r0_12, r1_12, r2_12)
    -- line: [261, 274] id: 12
    local r4_12 = r0_12.ev.time - r1_12.prevTime
    r1_12.prevTime = r1_12.prevTime + r4_12
    if r1_12.prevX then
      if r4_12 > 0 then
        r1_12.velocity = (r1_12.x - r1_12.prevX) / r4_12
      else
        r1_12.velocity = 0
      end
    end
    r1_12.prevX = r1_12.x
  end, r2_9, 0, true)
  function r2_9.touch(r0_10, r1_10)
    -- line: [150, 221] id: 10
    local r2_10 = r1_10.phase
    if r2_10 == "began" then
      r0_10.startPos = r1_10.x
      r0_10.prevPos = r1_10.x
      r0_10.delta = 0
      r0_10.velocity = 0
      if r0_10.tween then
        transition.cancel(r0_10.tween)
      end
      r0_10.start_btn, r0_10.select_btn = r20_0(r1_10.x, r1_10.y)
      r21_0(r0_10.start_btn)
      events.Disable(r0_10.ev, true)
      r0_10.prevTime = 0
      r0_10.prevX = 0
      events.Disable(r0_10.vev, false)
      display.getCurrentStage():setFocus(r0_10)
      r0_10.isFocus = true
    elseif r0_10.isFocus then
      if r2_10 == "moved" then
        local r3_10 = _G.Width - r7_9
        r0_10.delta = r1_10.x - r0_10.prevPos
        r0_10.prevPos = r1_10.x
        if r0_10.left < r0_10.x or r0_10.x < r3_10 then
          r0_10.x = r0_10.x + r0_10.delta / 2
        else
          r0_10.x = r0_10.x + r0_10.delta
        end
      elseif r2_10 == "ended" or r2_10 == "cancelled" then
        r0_10.lastTime = r1_10.time
        events.Disable(r0_10.ev, false)
        events.Disable(r0_10.vev, true)
        display.getCurrentStage():setFocus(nil)
        r0_10.isFocus = false
        local r3_10 = r0_10.delta
        if -1 <= r3_10 and r3_10 <= 1 then
          local r4_10, r5_10 = r20_0(r1_10.x, r1_10.y)
          if r4_10 and r4_10 == r0_10.start_btn then
            if r12_0 ~= r4_10 then
              r12_0 = r4_10
            elseif r5_10.ok then
              sound.PlaySE(1)
              r5_10.enable = not r5_10.enable
              if r5_10.enable then
                r5_10.check[1].isVisible = false
                r5_10.check[2].isVisible = true
                r23_0(r4_10)
              else
                r5_10.check[1].isVisible = true
                r5_10.check[2].isVisible = false
              end
            else
              sound.PlaySE(2)
            end
          else
            r12_0 = nil
          end
        end
      end
    end
    return true
  end
  r2_9:addEventListener("touch", r2_9)
  return r2_9
end
local function r25_0(r0_13, r1_13, r2_13, r3_13)
  -- line: [303, 320] id: 13
  if r3_13 == nil then
    r3_13 = 28
  end
  local r4_13 = display.newGroup()
  local r5_13 = nil
  local r6_13 = nil
  if type(r1_13) == "string" then
    r6_13 = r1_13
  else
    r6_13 = util.Num2Column(r1_13)
  end
  display.newText(r4_13, r6_13, 1, 1, native.systemFontBold, r3_13):setFillColor(255, 255, 255)
  display.newText(r4_13, r6_13, 0, 0, native.systemFontBold, r3_13):setFillColor(r2_13[1], r2_13[2], r2_13[3])
  r0_13:insert(r4_13)
  return r4_13
end
local function r26_0(r0_14, r1_14, r2_14, r3_14)
  -- line: [322, 355] id: 14
  local r4_14 = display.newGroup()
  local r5_14 = nil
  local r6_14 = nil
  local r7_14 = nil
  if r2_14 then
    r5_14 = 20
    r6_14 = 228
  else
    r5_14 = 20
    r6_14 = 84
  end
  util.LoadParts(r4_14, r16_0("powerup_crystal1"), r5_14, r6_14)
  if r2_14 then
    r5_14 = 56
    r6_14 = 232
  else
    r5_14 = 56
    r6_14 = 88
  end
  if r3_14 then
    r7_14 = {
      204,
      0,
      68
    }
  else
    r7_14 = {
      128,
      128,
      128
    }
  end
  local r8_14 = r25_0(r4_14, util.ConvertDisplayCrystal(r1_14), r7_14)
  r8_14:setReferencePoint(display.TopLeftReferencePoint)
  r8_14.x = r5_14
  r8_14.y = r6_14
  r0_14:insert(r4_14)
  return r4_14
end
local function r27_0(r0_15, r1_15, r2_15, r3_15, r4_15)
  -- line: [357, 363] id: 15
  r0_15:setReferencePoint(display.TopLeftReferencePoint)
  r0_15.x = r1_15 + (r3_15 - r0_15.width) / 2
  r0_15.y = r2_15 + (r4_15 - r0_15.height) / 2
end
local function r28_0(r0_16, r1_16, r2_16, r3_16, r4_16, r5_16)
  -- line: [365, 454] id: 16
  local r6_16 = _G.UILanguage == "jp"
  local r7_16 = {}
  local r8_16 = nil
  local r9_16 = nil
  local r10_16 = nil
  local r11_16 = nil
  local r12_16 = display.newGroup()
  r7_16.rtImg = r12_16
  local r13_16 = r2_0.getAllItemData()[r5_16]
  r7_16.id = r13_16[1]
  r7_16.crystal = r13_16[2]
  r7_16.msgid = r13_16[3]
  r7_16.disable = false
  r7_16.enable = false
  r1_16 = r17_0(r1_16)
  util.LoadParts(r12_16, r1_16, 0, 0)
  r7_16.parts = {}
  r8_16 = 16
  if r4_16 then
    r9_16 = 228
  else
    r9_16 = 84
  end
  r10_16 = r25_0(r12_16, "Loading", {
    255,
    255,
    255
  })
  r27_0(r10_16, r8_16, r9_16, 176, 40)
  r7_16.parts[1] = r10_16
  r10_16 = r26_0(r12_16, r7_16.crystal, r4_16, true)
  r10_16.isVisible = false
  r7_16.parts[2] = r10_16
  r10_16 = r26_0(r12_16, r7_16.crystal, r4_16, false)
  r10_16.isVisible = false
  r7_16.parts[3] = r10_16
  if r6_16 then
    r11_16 = "使用中"
  else
    r11_16 = "ENABLE"
  end
  r10_16 = r25_0(r12_16, r11_16, {
    64,
    128,
    96
  }, 24)
  r27_0(r10_16, r8_16, r9_16, 176, 40)
  r10_16.isVisible = false
  r7_16.parts[4] = r10_16
  if r6_16 then
    r10_16 = r25_0(r12_16, "上位版使用中", {
      204,
      0,
      68
    }, 20)
    r27_0(r10_16, r8_16, r9_16, 176, 40)
    r10_16.isVisible = false
    r7_16.parts[5] = r10_16
  else
    local r14_16 = display.newGroup()
    r27_0(r25_0(r14_16, "You\'ve had a", {
      204,
      0,
      68
    }, 16), 0, 0, 176, 20)
    r10_16 = r25_0(r14_16, "better equipment.", {
      204,
      0,
      68
    }, 16)
    r27_0(r10_16, 0, 20, 176, 20)
    r27_0(r14_16, r8_16, r9_16, 176, 40)
    r12_16:insert(r14_16)
    r14_16.isVisible = false
    r7_16.parts[5] = r14_16
  end
  if r4_16 then
    r10_16 = util.LoadParts(r12_16, r16_0("item_select1"), 0, 0)
  else
    r10_16 = util.LoadParts(r12_16, r16_0("item_select2"), 0, 0)
  end
  r10_16.isVisible = false
  r7_16.select = r10_16
  r8_16 = 128
  if r4_16 then
    r9_16 = 224
  else
    r9_16 = 80
  end
  r7_16.check = {}
  for r17_16 = 1, 3, 1 do
    r10_16 = util.LoadParts(r12_16, r16_0("powerup_checkmark" .. tostring(r17_16)), r8_16, r9_16)
    r10_16.isVisible = false
    r7_16.check[r17_16] = r10_16
  end
  r0_16:insert(r12_16)
  r12_16:setReferencePoint(display.TopLeftReferencePoint)
  r12_16.x = r2_16
  r12_16.y = r3_16
  return r7_16
end
local function r29_0(r0_17, r1_17)
  -- line: [456, 476] id: 17
  local r2_17 = display.newGroup()
  local r3_17 = nil
  local r4_17 = nil
  local r5_17 = 0
  for r9_17, r10_17 in pairs(r1_17) do
    r3_17 = db.GetMessage(r10_17)
    display.newText(r2_17, r3_17, 1, r5_17 + 1, native.systemFontBold, 24):setFillColor(0, 0, 0)
    r4_17 = display.newText(r2_17, r3_17, 0, r5_17, native.systemFontBold, 24)
    r4_17:setFillColor(255, 255, 255)
    r5_17 = r5_17 + 36
  end
  r0_17:insert(r2_17)
  r2_17:setReferencePoint(display.TopLeftReferencePoint)
  r2_17.x = 16
  r2_17.y = 432
  r2_17.isVisible = false
  return r2_17
end
local function r30_0(r0_18)
  -- line: [478, 484] id: 18
  local r1_18 = r0_18.param
  sound.PlaySE(2)
  r14_0()
  if r1_18 and r1_18[2] then
    r1_18[2]()
  end
  return true
end
local function r31_0()
  -- line: [486, 496] id: 19
  local r0_19 = 0
  local r1_19 = {}
  for r5_19, r6_19 in pairs(r5_0) do
    if r6_19.enable then
      r0_19 = r0_19 + r6_19.crystal
      table.insert(r1_19, r6_19.id)
    end
  end
  return r0_19, r1_19
end
local function r32_0(r0_20)
  -- line: [498, 546] id: 20
  local r1_20, r2_20 = r31_0()
  if r1_20 == 0 then
    return r30_0(r0_20)
  end
  local r3_20 = r4_0.parent
  local r4_20 = r0_20.param
  sound.PlaySE(1)
  r14_0()
  local r5_20 = nil
  local r6_20 = nil
  local r7_20 = {}
  for r11_20, r12_20 in pairs(r2_20) do
    table.insert(r7_20, {
      r12_20,
      1,
      r2_0.getItemData(r12_20)[2]
    })
  end
  require("ui.coin_module").new({
    useItemList = r7_20,
  }).Open(r3_20, db.GetMessage(119), function(r0_21)
    -- line: [527, 537] id: 21
    _G.metrics.crystal_powerup_in_stage(_G.MapSelect, _G.StageSelect, r0_21)
    local r1_21 = {}
    for r5_21, r6_21 in pairs(r0_21) do
      table.insert(r1_21, r6_21[1])
    end
    r4_20[1](r1_21)
  end, function(r0_22)
    -- line: [538, 542] id: 22
    DebugPrint("push cancel")
    r15_0(r3_20, r4_20)
  end)
  return true
end
local function r33_0()
  -- line: [549, 572] id: 23
  if r10_0 then
    if r10_0.tween then
      transition.cancel(r10_0.tween)
    end
    if r10_0.ev then
      events.Delete(r10_0.ev)
    end
    if r10_0.vev then
      events.Delete(r10_0.vev)
    end
    r10_0 = nil
  end
  if r4_0 then
    display.remove(r4_0)
    r4_0 = nil
  end
  if r3_0 then
    display.remove(r3_0)
    r3_0 = nil
  end
  r7_0 = nil
  r6_0 = nil
end
local function r34_0(r0_24)
  -- line: [574, 578] id: 24
  r15_0(r0_24[1], r0_24[2])
end
local function r35_0(r0_25)
  -- line: [580, 587] id: 25
  local r1_25 = r0_25.param
  local r2_25 = r4_0.parent
  r33_0()
  r0_0.Open(r2_25, {
    r34_0,
    {
      r2_25,
      r1_25
    }
  })
  return true
end
local function r36_0(r0_26)
  -- line: [589, 662] id: 26
  local r1_26 = display.newGroup()
  local r2_26 = util.MakeGroup(r1_26)
  local r3_26 = util.MakeGroup(r1_26)
  local r4_26 = util.MakeGroup(r1_26)
  for r8_26 = 0, 960, 16 do
    util.LoadParts(r2_26, r16_0("powerup_window01"), r8_26, 8)
    util.LoadParts(r2_26, r16_0("powerup_window02"), r8_26, 136)
    util.LoadParts(r2_26, r16_0("powerup_window03"), r8_26, 264)
    util.LoadParts(r2_26, r16_0("powerup_window04"), r8_26, 392)
    util.LoadParts(r2_26, r16_0("powerup_window05"), r8_26, 520)
  end
  util.LoadParts(r2_26, r17_0("powerup_title_"), 260, 48)
  for r8_26 = 0, 768, 256 do
    util.LoadParts(r2_26, r18_0("option_line01"), r8_26, 104)
    util.LoadParts(r2_26, r18_0("option_line02"), r8_26, 416)
    util.LoadParts(r2_26, r18_0("option_line01"), r8_26, 518)
  end
  util.LoadBtn({
    rtImg = r2_26,
    fname = r18_0("close"),
    x = 872,
    y = 0,
    func = r30_0,
    param = r0_26,
  })
  util.LoadBtn({
    rtImg = r2_26,
    fname = r17_0("ok_"),
    x = 712,
    y = 528,
    func = r32_0,
    param = r0_26,
  })
  util.LoadBtn({
    rtImg = r2_26,
    fname = r17_0("add_crystal_"),
    x = 472,
    y = 528,
    func = r35_0,
    param = r0_26,
  })
  util.LoadParts(r2_26, r16_0("pocket_crystal"), 712, 428)
  local r5_26 = r19_0(r2_26, "Loading")
  r5_26:setReferencePoint(display.TopRightReferencePoint)
  r5_26.x = 937
  r5_26.y = 448
  r7_0 = r5_26
  r10_0 = r24_0(r3_26, {
    left = 0,
    top = 104,
    right = 1248,
    bottom = 415,
  })
  r5_0 = {}
  local r6_26 = 8
  local r7_26 = 15
  table.insert(r5_0, r28_0(r10_0, "item03", r6_26, r7_26, true, 13))
  r6_26 = r6_26 + 208
  local r8_26 = nil
  for r12_26 = 3, 12, 1 do
    table.insert(r5_0, r28_0(r10_0, string.format("item%02d", r12_26 + 8), r6_26, r7_26, false, r12_26))
    if r7_26 + 144 > 159 then
      r7_26 = 15
      r6_26 = r6_26 + 208
    end
  end
  r13_0 = r29_0(r4_26, {
    119
  })
  r13_0.isVisible = true
  for r12_26, r13_26 in pairs(r5_0) do
    r13_26.msg = r29_0(r4_26, r13_26.msgid)
  end
  return r1_26
end
local function r37_0(r0_27)
  -- line: [665, 742] id: 27
  local r1_27 = db.UsingItemList(_G.UserID)
  local r2_27 = {}
  for r6_27, r7_27 in pairs(r1_27) do
    r2_27[r7_27[1]] = true
  end
  for r6_27, r7_27 in pairs(r5_0) do
    r7_27.parts[1].isVisible = false
    local r8_27 = r7_27.id
    local r9_27 = r2_27[r8_27]
    local r10_27 = true
    if r9_27 == nil then
      r9_27 = false
    end
    if (r8_27 == 1 or r8_27 == 2) and r9_27 then
      r7_27.parts[4].isVisible = true
      r7_27.disable = true
      r10_27 = false
    elseif r8_27 == 38 and r9_27 then
      r7_27.parts[4].isVisible = true
      r7_27.disable = true
      r10_27 = false
    elseif (r8_27 == 17 or r8_27 == 19 or r8_27 == 21) and r9_27 then
      r7_27.parts[4].isVisible = true
      r7_27.disable = true
      r10_27 = false
    elseif (r8_27 == 18 or r8_27 == 20 or r8_27 == 22) and r9_27 then
      r7_27.parts[4].isVisible = true
      r7_27.disable = true
      local r11_27 = r5_0[r6_27 - 1]
      r11_27.parts[2].isVisible = false
      r11_27.parts[3].isVisible = false
      r11_27.parts[4].isVisible = false
      r11_27.parts[5].isVisible = true
      r11_27.check[1].isVisible = false
      r11_27.check[2].isVisible = false
      r11_27.disable = true
      r10_27 = false
    end
    if r10_27 then
      if r7_27.crystal <= r0_27 then
        r7_27.parts[2].isVisible = true
        r7_27.check[1].isVisible = true
        r7_27.ok = true
      else
        r7_27.parts[3].isVisible = true
        r7_27.check[3].isVisible = true
        r7_27.ok = false
      end
    end
  end
  if r2_27[38] then
    local r3_27 = nil
    for r7_27 = 2, 7, 1 do
      r3_27 = r5_0[r7_27]
      r3_27.parts[2].isVisible = false
      r3_27.parts[3].isVisible = false
      r3_27.parts[4].isVisible = false
      r3_27.parts[5].isVisible = true
      r3_27.check[1].isVisible = false
      r3_27.check[2].isVisible = false
      r3_27.disable = true
      r3_27.ok = false
    end
  end
end
local function r38_0(r0_28)
  -- line: [744, 771] id: 28
  if r7_0 == nil then
    return 
  end
  if server.CheckError(r0_28) then
    server.NetworkError(35, r14_0)
  else
    local r1_28 = r1_0.decode(r0_28.response)
    if r1_28.status == 0 then
      local r2_28 = r1_28.coin
      local r3_28 = r7_0.x
      local r4_28 = r7_0.y
      local r5_28 = r7_0.parent
      if r7_0 then
        display.remove(r7_0)
      end
      r7_0 = nil
      local r6_28 = r19_0(r5_28, util.ConvertDisplayCrystal(r2_28))
      r6_28:setReferencePoint(display.TopRightReferencePoint)
      r6_28.x = r3_28
      r6_28.y = r4_28
      r7_0 = r6_28
      r37_0(r2_28)
      r0_0.ShowCoinInfo(r2_28)
    else
      server.ServerError(r1_28.status, r14_0)
    end
  end
end
local function r39_0()
  -- line: [773, 775] id: 29
  server.GetCoin(_G.UserToken, r38_0)
end
local function r40_0(r0_30)
  -- line: [777, 790] id: 30
  if db.CountQueue() > 0 then
    if r0_30.count >= 60 then
      util.setActivityIndicator(false)
      r14_0()
    end
    return 
  end
  timer.cancel(r0_30.source)
  util.setActivityIndicator(false)
  r39_0()
end
local function r41_0(r0_31)
  -- line: [792, 823] id: 31
  if _G.UserToken == nil then
    server.NetworkError(35, r14_0)
    return 
  end
  r12_0 = nil
  local r1_31 = r0_31[1]
  local r2_31 = r0_31[2]
  util.setActivityIndicator(false)
  r3_0 = util.MakeMat(r1_31)
  r4_0 = r36_0(r2_31)
  r1_31:insert(r4_0)
  if db.CountQueue() > 0 then
    util.setActivityIndicator(true)
    timer.performWithDelay(500, r40_0, 60)
  else
    r39_0()
  end
  if r9_0 then
    transition.cancel(r9_0)
  end
  r9_0 = transition.to(_G.PanelRoot, {
    time = 500,
    y = _G.HeightDiff - _G.PanelRoot.height,
    transition = easing.outExpo,
  })
  r8_0 = char.AllPause(true)
end
function r15_0(r0_32, r1_32)
  -- line: [829, 840] id: 32
  r11_0 = events.SetRepeatTime(1)
  r14_0()
  if _G.UserToken == nil then
    server.NetworkError(35, r14_0)
    return false
  else
    r41_0({
      r0_32,
      r1_32
    })
  end
end
function r14_0()
  -- line: [842, 852] id: 33
  r33_0()
  if r9_0 then
    transition.cancel(r9_0)
  end
  r9_0 = transition.to(_G.PanelRoot, {
    time = 500,
    y = 0,
    transition = easing.outExpo,
    onComplete = function()
      -- line: [848, 848] id: 34
      local r0_34 = nil	-- notice: implicit variable refs by block#[0]
      r9_0 = r0_34
    end,
  })
  char.AllPause(true)
  events.SetRepeatTime(r11_0)
end
return {
  Init = function()
    -- line: [854, 855] id: 35
  end,
  Cleanup = function()
    -- line: [857, 859] id: 36
    r14_0()
  end,
  Open = r15_0,
  Close = r14_0,
}
