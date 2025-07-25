-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("facebook")
local r1_0 = require("json")
local r2_0 = "240189602705303"
local r3_0 = "341199119236583"
local r4_0 = nil
local r5_0 = nil
local r6_0 = nil
local r7_0 = nil
local r8_0 = nil
local r9_0 = nil
local r10_0 = 24
local r11_0 = _G.IsiPhone
local r12_0 = nil
local function r13_0()
  -- line: [23, 31] id: 1
  native.showAlert("DefenseWitches", "通信出来ませんでした。", {
    "OK"
  }, function(r0_2)
    -- line: [26, 30] id: 2
    if r0_2.action == "clicked" then
      r12_0()
    end
  end)
end
local function r14_0(r0_3)
  -- line: [34, 34] id: 3
  return "data/twitter/" .. r0_3 .. ".png"
end
local function r15_0(r0_4, r1_4)
  -- line: [36, 68] id: 4
  local r2_4 = r1_4.phase
  local r3_4 = r0_4[1]
  local r4_4 = r0_4[2]
  local r5_4 = r0_4.onRelease
  if r2_4 == "began" then
    r3_4.isVisible = false
    r4_4.isVisible = true
    display.getCurrentStage():setFocus(r0_4)
    r0_4.is_focus = true
  elseif r0_4.is_focus then
    local r6_4 = r1_4.x
    local r7_4 = r1_4.y
    local r8_4 = r0_4.stageBounds
    local r9_4 = r8_4.xMin
    if r9_4 <= r6_4 then
      r9_4 = r8_4.xMax
      if r6_4 <= r9_4 then
        r9_4 = r8_4.yMin
        if r9_4 <= r7_4 then
          r9_4 = r7_4 <= r8_4.yMax
        end
      end
    else
      r9_4 = r9_4 <= r6_4	-- block#7 is visited secondly
    end
    if r2_4 == "moved" and not r9_4 then
      r2_4 = "cancelled"
    end
    if r2_4 == "ended" or r2_4 == "canceled" then
      r0_4.is_focus = false
      r3_4.isVisible = true
      r4_4.isVisible = false
      display.getCurrentStage():setFocus(nil)
      if r2_4 == "ended" and r9_4 and r5_4 then
        r5_4(r0_4)
      end
    end
  end
  return true
end
local function r16_0(r0_5, r1_5, r2_5)
  -- line: [70, 99] id: 5
  local r3_5 = display.newGroup()
  local r4_5 = nil
  local r5_5 = display.newEmbossedText(r0_5, 0, 0, native.systemFontBold, r10_0)
  r5_5:setFillColor(255, 255, 255)
  local r6_5 = 0
  r6_5 = r6_5 + util.LoadParts(r3_5, r14_0(("button_" .. r1_5 .. "_" .. r2_5)), r6_5, 0).width
  local r7_5 = math.ceil(r5_5.width / 16)
  local r8_5 = r14_0("button_" .. r1_5 .. "_center")
  for r12_5 = r6_5, r6_5 + r7_5 * 16 - 16, 16 do
    util.LoadParts(r3_5, r8_5, r12_5, 0)
  end
  util.LoadParts(r3_5, r14_0("button_" .. r1_5 .. "_right"), r6_5 + r7_5 * 16, 0)
  r3_5:insert(r5_5)
  r5_5:setReferencePoint(display.TopLeftReferencePoint)
  r5_5.x = (r3_5.width - r5_5.width) / 2
  r5_5.y = (88 - r5_5.height) / 2
  return r3_5
end
local function r17_0(r0_6, r1_6, r2_6)
  -- line: [101, 120] id: 6
  local r3_6 = r0_6[1]
  local r4_6 = r0_6[2]
  local r5_6 = display.newGroup()
  local r6_6 = nil
  r5_6:insert(r16_0(r3_6, "normal", r2_6))
  r6_6 = r16_0(r3_6, "push", r2_6)
  r6_6.isVisible = false
  r5_6:insert(r6_6)
  r5_6:setReferencePoint(display.TopLeftReferencePoint)
  r5_6.x = r1_6
  r5_6.onRelease = r4_6
  r5_6.touch = r15_0
  r5_6:addEventListener("touch", r5_6)
  return r5_6
end
local function r18_0(r0_7, r1_7, r2_7, r3_7)
  -- line: [122, 184] id: 7
  local r4_7 = r1_7[1]
  local r5_7 = r1_7[2]
  local r6_7 = display.newGroup()
  local r7_7 = nil
  local r8_7 = nil
  r7_7 = r14_0("bar_loop")
  for r12_7 = 0, _G.Width, 16 do
    util.LoadParts(r6_7, r7_7, r12_7, 0)
  end
  local r9_7 = nil
  local r10_7 = nil
  local r11_7 = 0
  local r12_7 = nil
  r8_7 = r17_0(r5_7, 0, "left2")
  r6_7:insert(r8_7)
  r11_7 = r11_7 + r8_7.width
  if r0_7 then
    r8_7 = display.newEmbossedText(r6_7, r0_7, 0, 0, native.systemFontBold, r10_0)
    r8_7:setFillColor(255, 255, 255)
    r6_7:insert(r8_7)
    r8_7:setReferencePoint(display.TopLeftReferencePoint)
    r8_7.x = r11_7
    r8_7.y = (88 - r8_7.height) / 2
    r11_7 = r11_7 + r8_7.width
  end
  r9_7 = r11_7
  r8_7 = r17_0(r4_7, _G.Width, "left")
  r6_7:insert(r8_7)
  r8_7.x = _G.Width - r8_7.width
  r8_7 = native.newTextBox(0, 88, _G.Width, 144)
  r8_7.isEditable = true
  local r13_7 = r10_0
  if r11_0 then
    r13_7 = r13_7 * 0.5
  end
  r8_7.font = native.newFont(native.systemFont, r13_7)
  r8_7:setFillColor(0, 0, 0)
  if r2_7 then
    r8_7.text = r2_7
  end
  r6_7:insert(r8_7)
  if r3_7 then
    native.setKeyboardFocus(r8_7)
  end
  r9_0 = r8_7
  if r8_0 then
    display.remove(r8_0)
  end
  r8_0 = r6_7
end
local function r19_0(r0_8)
  -- line: [186, 205] id: 8
  r18_0(nil, {
    {
      "POST",
      function(r0_9)
        -- line: [187, 196] id: 9
        local r1_9 = r9_0.text
        if r1_9 then
          local r2_9 = r6_0.fb
          r2_9.message = r1_9
          r0_0.request("me/feed", "POST", r2_9)
          r12_0()
        end
        return true
      end
    },
    {
      "Cancel",
      function(r0_10)
        -- line: [197, 200] id: 10
        r12_0()
        return true
      end
    }
  }, r0_8.fb.message, true)
end
local function r20_0(r0_11)
  -- line: [207, 211] id: 11
  r5_0 = r0_11.callback
  r6_0 = nil
  r0_0.request("me/likes/" .. tostring(r3_0))
end
local function r21_0(r0_12)
  -- line: [213, 217] id: 12
  if r0_12.action == "clicked" then
    r12_0()
  end
end
local function r22_0(r0_13)
  -- line: [219, 259] id: 13
  if r0_13.type == "session" then
    local r1_13 = r0_13.phase
    if r1_13 == "login" then
      r4_0 = r0_13.token
      if r5_0 then
        r5_0(r6_0)
        r5_0 = nil
      end
    else
      native.showAlert("DefenseWitches", "Facebook connect fail!\n" .. "[" .. r1_13 .. "]", {
        "OK"
      }, r21_0)
    end
  elseif r0_13.type == "request" then
    local r1_13 = r0_13.response
    if not r0_13.isError then
      r1_13 = r1_0.decode(r1_13)
      if r1_13.error then
        native.showAlert("DefenseWitches", string.format("%s(%d)", r1_13.error.message, r1_13.error.code), {
          "OK"
        }, r21_0)
      else
        if r5_0 then
          r5_0(r1_13.data)
        end
        r5_0 = nil
      end
    else
      r13_0()
    end
  elseif r0_13.type == "dialog" then
  end
end
local function r25_0(r0_16)
  -- line: [277, 319] id: 16
  local function r1_16(r0_17)
    -- line: [278, 282] id: 17
    native.cancelWebPopup()
    r12_0()
    return true
  end
  r7_0 = util.MakeMat(r0_16)
  local r2_16 = display.newGroup()
  local r3_16 = nil
  local r4_16 = nil
  r3_16 = r14_0("bar_loop")
  for r8_16 = 0, _G.Width, 16 do
    util.LoadParts(r2_16, r3_16, r8_16, 0)
  end
  local r5_16 = nil
  local r6_16 = nil
  local r7_16 = 0
  local r8_16 = nil
  r4_16 = r17_0({
    "Close",
    r1_16
  }, 0, "left2")
  r2_16:insert(r4_16)
  r7_16 = r7_16 + r4_16.width
  r4_16 = display.newEmbossedText(db.GetMessage(52), 0, 0, native.systemFontBold, 24)
  r4_16:setFillColor(255, 255, 255)
  r2_16:insert(r4_16)
  r4_16:setReferencePoint(display.TopLeftReferencePoint)
  r4_16.x = r7_16
  r4_16.y = (88 - r4_16.height) / 2
  r7_16 = r7_16 + r4_16.width
  r8_0 = r2_16
  return r2_16.height
end
function r12_0()
  -- line: [349, 364] id: 21
  local r0_21 = nil	-- notice: implicit variable refs by block#[0]
  r5_0 = r0_21
  r6_0 = nil
  r0_21 = r9_0
  if r0_21 then
    r9_0:removeSelf()
    r0_21 = nil
    r9_0 = r0_21
  end
  r0_21 = r7_0
  if r0_21 then
    display.remove(r7_0)
    r0_21 = nil
    r7_0 = r0_21
  end
  r0_21 = r8_0
  if r0_21 then
    display.remove(r8_0)
    r0_21 = nil
    r8_0 = r0_21
  end
end
return {
  Post = function(r0_14, r1_14, r2_14, r3_14, r4_14)
    -- line: [261, 267] id: 14
    local r5_14 = _G.GooglePlayURL[_G.UILanguage]
    if r2_14 ~= nil and r1_14 ~= nil and r5_14 ~= nil then
      shareFB.share(r2_14, r1_14, r5_14, r12_0)
    end
  end,
  GetLike = function(r0_15)
    -- line: [269, 274] id: 15
    r6_0 = {
      callback = r0_15,
    }
    r5_0 = r20_0
    r0_0.login(r2_0, r22_0, {
      "publish_stream"
    })
  end,
  Like = function(r0_18)
    -- line: [321, 340] id: 18
    local r1_18 = r25_0(r0_18)
    native.showWebPopup(0, r1_18, _G.Width, _G.Height - r1_18, "facebook/like.html", {
      hasBackground = true,
      baseUrl = system.ResourceDirectory,
      urlRequest = function(r0_19)
        -- line: [324, 332] id: 19
        if string.find(r0_19.url, "corona:close") == 1 then
          native.cancelWebPopup()
          r12_0()
          return false
        end
        return true
      end,
    })
  end,
  Logout = function()
    -- line: [342, 347] id: 20
    if r4_0 then
      r0_0.logout()
      r4_0 = nil
    end
  end,
  Close = r12_0,
}
