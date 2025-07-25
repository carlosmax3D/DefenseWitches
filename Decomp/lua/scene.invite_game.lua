-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("json")
local r1_0 = _G.IsiPhone
local r2_0 = nil
local r3_0 = nil
local r4_0 = 24
local r5_0 = nil
local r6_0 = nil
local r7_0 = nil
local r8_0 = nil
local r9_0 = nil
local function r10_0(r0_1)
  -- line: [16, 16] id: 1
  return "data/twitter/" .. r0_1 .. ".png"
end
local function r11_0(r0_2)
  -- line: [17, 17] id: 2
  return "data/cont/" .. r0_2 .. ".png"
end
local function r12_0(r0_3)
  -- line: [18, 18] id: 3
  return r11_0(r0_3 .. _G.UILanguage)
end
local function r13_0()
  -- line: [20, 33] id: 4
  if r7_0 then
    display.remove(r7_0)
    r7_0 = nil
  end
  if r3_0 then
    r3_0:removeSelf()
    r3_0 = nil
  end
  if r2_0 then
    display.remove(r2_0)
    r2_0 = nil
  end
end
local function r14_0(r0_5, r1_5, r2_5)
  -- line: [35, 64] id: 5
  local r3_5 = display.newGroup()
  local r4_5 = nil
  local r5_5 = display.newText(r0_5, 0, 0, native.systemFontBold, r4_0)
  r5_5:setFillColor(255, 255, 255)
  local r6_5 = 0
  r6_5 = r6_5 + util.LoadParts(r3_5, r10_0(("button_" .. r1_5 .. "_" .. r2_5)), r6_5, 0).width
  local r7_5 = math.ceil(r5_5.width / 16)
  local r8_5 = r10_0("button_" .. r1_5 .. "_center")
  for r12_5 = r6_5, r6_5 + r7_5 * 16 - 16, 16 do
    util.LoadParts(r3_5, r8_5, r12_5, 0)
  end
  util.LoadParts(r3_5, r10_0("button_" .. r1_5 .. "_right"), r6_5 + r7_5 * 16, 0)
  r3_5:insert(r5_5)
  r5_5:setReferencePoint(display.TopLeftReferencePoint)
  r5_5.x = (r3_5.width - r5_5.width) / 2
  r5_5.y = (88 - r5_5.height) / 2
  return r3_5
end
local function r15_0(r0_6, r1_6)
  -- line: [66, 98] id: 6
  local r2_6 = r1_6.phase
  local r3_6 = r0_6[1]
  local r4_6 = r0_6[2]
  local r5_6 = r0_6.onRelease
  if r2_6 == "began" then
    r3_6.isVisible = false
    r4_6.isVisible = true
    display.getCurrentStage():setFocus(r0_6)
    r0_6.is_focus = true
  elseif r0_6.is_focus then
    local r6_6 = r1_6.x
    local r7_6 = r1_6.y
    local r8_6 = r0_6.stageBounds
    local r9_6 = r8_6.xMin
    if r9_6 <= r6_6 then
      r9_6 = r8_6.xMax
      if r6_6 <= r9_6 then
        r9_6 = r8_6.yMin
        if r9_6 <= r7_6 then
          r9_6 = r7_6 <= r8_6.yMax
        end
      end
    else
      goto label_34	-- block#7 is visited secondly
    end
    if r2_6 == "moved" and not r9_6 then
      r2_6 = "cancelled"
    end
    if r2_6 == "ended" or r2_6 == "canceled" then
      r0_6.is_focus = false
      r3_6.isVisible = true
      r4_6.isVisible = false
      display.getCurrentStage():setFocus(nil)
      if r2_6 == "ended" and r9_6 and r5_6 then
        r5_6(r0_6)
      end
    end
  end
  return true
end
local function r16_0(r0_7, r1_7, r2_7)
  -- line: [100, 119] id: 7
  local r3_7 = r0_7[1]
  local r4_7 = r0_7[2]
  local r5_7 = display.newGroup()
  local r6_7 = nil
  r5_7:insert(r14_0(r3_7, "normal", r2_7))
  r6_7 = r14_0(r3_7, "push", r2_7)
  r6_7.isVisible = false
  r5_7:insert(r6_7)
  r5_7:setReferencePoint(display.TopLeftReferencePoint)
  r5_7.x = r1_7
  r5_7.onRelease = r4_7
  r5_7.touch = r15_0
  r5_7:addEventListener("touch", r5_7)
  return r5_7
end
local function r17_0(r0_8)
  -- line: [121, 124] id: 8
  r13_0()
  return true
end
local function r18_0(r0_9)
  -- line: [126, 195] id: 9
  r7_0 = display.newGroup()
  util.MakeMat(r7_0)
  local function r1_9(r0_10)
    -- line: [130, 130] id: 10
    return "data/twitter/" .. r0_10 .. ".png"
  end
  local r2_9 = {
    "OK",
    r0_9.func
  }
  local r3_9 = {
    "Cancel",
    r17_0
  }
  local r4_9 = display.newGroup()
  local r5_9 = nil
  local r6_9 = nil
  r5_9 = r1_9("bar_loop")
  for r10_9 = 0, _G.Width, 16 do
    util.LoadParts(r4_9, r5_9, r10_9, 0)
  end
  local r7_9 = nil
  local r8_9 = nil
  local r9_9 = 0
  local r10_9 = nil
  r6_9 = r16_0(r3_9, 0, "left2")
  r4_9:insert(r6_9)
  r9_9 = r9_9 + r6_9.width
  r6_9 = display.newText(r4_9, r0_9.text, 0, 0, native.systemFontBold, r4_0)
  r6_9:setFillColor(255, 255, 255)
  r4_9:insert(r6_9)
  r6_9:setReferencePoint(display.TopLeftReferencePoint)
  r6_9.x = r9_9
  r6_9.y = (88 - r6_9.height) / 2
  r7_9 = r9_9 + r6_9.width
  r6_9 = r16_0(r2_9, _G.Width, "left")
  r4_9:insert(r6_9)
  r8_9 = _G.Width - r6_9.width
  r6_9.x = r8_9
  if not _G.IsWindows then
    r7_9 = r7_9 + 8
    r6_9 = native.newTextField(r7_9, 26, r8_9 - 8 - r7_9, 36)
    local r12_9 = 24
    if r1_0 then
      r12_9 = r12_9 * 0.5
    end
    r6_9.font = native.newFont(native.systemFont, r12_9)
    r6_9:setTextColor(0, 0, 0)
    if r0_9.get_func then
      local r13_9 = r0_9.get_func()
      if r13_9 then
        r6_9.text = r13_9
      end
    end
    r4_9:insert(r6_9)
    native.setKeyboardFocus(r6_9)
    r3_0 = r6_9
  end
  if r2_0 then
    display.remove(r2_0)
  end
  r2_0 = r4_9
end
local function r19_0(r0_11, r1_11)
  -- line: [197, 201] id: 11
  if r1_11.phase == "ended" then
    r18_0(r0_11.params)
  end
end
local function r20_0(r0_12)
  -- line: [203, 239] id: 12
  local r1_12 = display.newGroup()
  local r2_12 = display.newText(r1_12, r0_12.text, 0, 0, native.systemFontBold, 40)
  r2_12:setFillColor(51, 51, 51)
  r2_12:setReferencePoint(display.CenterLeftReferencePoint)
  r2_12.x = 0
  r2_12.y = 34
  local r3_12 = r2_12.width + 12
  local r4_12 = display.newGroup()
  r2_12 = util.LoadParts(r4_12, r11_0("input_box"), 0, 0)
  r2_12.touch = r19_0
  r2_12.params = r0_12
  r2_12:addEventListener("touch", r2_12)
  r2_12 = display.newText(r4_12, "", 16, 14, native.systemFontBold, 40)
  r2_12:setFillColor(51, 51, 51)
  r1_12:insert(r4_12)
  r4_12:setReferencePoint(display.TopLeftReferencePoint)
  r4_12.x = r3_12
  r1_12:setReferencePoint(display.TopLeftReferencePoint)
  r1_12.x = r0_12.x
  r1_12.y = r0_12.y
  r0_12.rtImg:insert(r1_12)
  return r2_12, r1_12
end
local function r21_0(r0_13)
  -- line: [241, 262] id: 13
  local r1_13 = nil	-- notice: implicit variable refs by block#[4, 5]
  if r3_0 then
    r1_13 = r3_0.text
  elseif _G.IsWindows then
    r1_13 = "wfs34e"
  end
  r5_0 = r1_13
  if r6_0 then
    local r2_13 = r6_0.x
    local r3_13 = r6_0.y
    local r4_13 = r6_0.parent
    display.remove(r6_0)
    r6_0 = util.MakeText(40, {
      0,
      0,
      0
    }, {
      255,
      255,
      255
    }, r1_13)
    r4_13:insert(r6_0)
    r6_0:setReferencePoint(display.TopLeftReferencePoint)
    r6_0.x = r2_13
    r6_0.y = r6_0.y + 10
  end
  r13_0()
end
local function r22_0(r0_14)
  -- line: [264, 301] id: 14
  util.setActivityIndicator(false)
  if server.CheckError(r0_14) then
    server.NetworkError(35)
  else
    local r2_14 = r0_0.decode(r0_14.response).status
    if r2_14 == 0 then
      r13_0()
      kpi.Invited()
      native.showAlert(db.GetMessage(336), string.format(db.GetMessage(337), util.ConvertDisplayCrystal(_G.GetCrystal)), {
        "OK"
      }, function(r0_15)
        -- line: [279, 285] id: 15
        if r0_15.action == "clicked" and r0_15.index == 1 then
          require("logic.run_to_first").startFirstGame({
            invite = true,
          })
        end
      end)
    elseif r2_14 == 6 then
      server.NetworkError(178)
    elseif r2_14 == 7 then
      server.NetworkError(179)
    elseif r2_14 == 8 then
      server.NetworkError(238)
    else
      server.ServerError(r2_14)
    end
  end
end
local function r23_0(r0_16)
  -- line: [303, 317] id: 16
  local r1_16 = r5_0
  local r2_16 = true
  if r1_16 == nil or r1_16:len() < 1 then
    r2_16 = false
  end
  if not r2_16 then
    sound.PlaySE(2)
    return 
  end
  sound.PlaySE(1)
  if _G.UserToken ~= nil then
    util.setActivityIndicator(true)
    server.InviteReceive(_G.UserToken, r1_16, r22_0)
  end
  return true
end
local function r24_0(r0_17)
  -- line: [319, 324] id: 17
  r13_0()
  sound.PlaySE(2)
  util.ChangeScene({
    scene = "cont",
  })
  return true
end
return {
  Close = r13_0,
  new = function(r0_18)
    -- line: [326, 382] id: 18
    server.MakeUserID()
    local r1_18 = display.newGroup()
    r9_0 = display.newGroup()
    r8_0 = display.newGroup()
    r1_18:insert(r9_0)
    r1_18:insert(r8_0)
    util.LoadBG(r9_0, r11_0("base"))
    local r2_18 = nil
    local r3_18 = nil
    r3_18 = display.newText(r9_0, string.format(db.GetMessage(162), util.ConvertDisplayCrystal(_G.GetCrystal)), 0, 0, 864, 72, native.systemFontBold, 30)
    r3_18:setFillColor(51, 51, 51)
    r3_18.x = 480
    r3_18.y = 236
    r2_18 = db.GetMessage(165)
    r5_0 = nil
    r6_0 = nil
    local r4_18 = nil
    r6_0, r4_18 = r20_0({
      rtImg = r9_0,
      text = r2_18,
      x = 0,
      y = 290,
      func = r21_0,
      get_func = function()
        -- line: [365, 367] id: 19
        return r5_0
      end,
    })
    r4_18:setReferencePoint(display.TopCenterReferencePoint)
    r4_18.x = _G.Width / 2
    util.LoadBtn({
      rtImg = r9_0,
      fname = r12_0("button01"),
      x = 216,
      y = 468,
      func = r24_0,
    })
    util.LoadBtn({
      rtImg = r9_0,
      fname = r12_0("button02"),
      x = 548,
      y = 468,
      func = r23_0,
    })
    util.MakeFrame(r1_18)
    return r1_18
  end,
}
