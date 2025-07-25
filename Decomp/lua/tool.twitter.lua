-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("tool.oauth")
local r1_0 = {
  consumer_key = "ZdgfgyEa6t9gvjpE6RRBQ",
  consumer_secret = "vLZxPNrhEebutrBJy3ebW2fSmqFI8zSHi29V0FK7JL4",
  authorize_url = "https://api.twitter.com/oauth/authorize",
  request_token_url = "https://api.twitter.com/oauth/request_token",
  access_token_url = "https://api.twitter.com/oauth/access_token",
  token_ready_url = "",
}
local r2_0 = "https://api.twitter.com/1/statuses/update.json"
local r3_0 = nil
local r4_0 = nil
local r5_0 = nil
local r6_0 = nil
local r7_0 = nil
local r8_0 = 24
local r9_0 = _G.IsiPhone
local r10_0 = nil
local r11_0 = r0_0.newConsumer(r1_0)
local r12_0 = nil
local function r13_0()
  -- line: [38, 46] id: 1
  native.showAlert("DefenseWitches", "通信出来ませんでした。", {
    "OK"
  }, function(r0_2)
    -- line: [41, 45] id: 2
    if r0_2.action == "clicked" then
      r10_0()
    end
  end)
end
local function r14_0(r0_3)
  -- line: [50, 50] id: 3
  return "data/twitter/" .. r0_3 .. ".png"
end
local function r15_0(r0_4, r1_4)
  -- line: [52, 84] id: 4
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
      goto label_34	-- block#7 is visited secondly
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
  -- line: [86, 115] id: 5
  local r3_5 = display.newGroup()
  local r4_5 = nil
  local r5_5 = display.newEmbossedText(r0_5, 0, 0, native.systemFontBold, r8_0)
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
  -- line: [117, 136] id: 6
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
local function r18_0(r0_7, r1_7, r2_7, r3_7, r4_7)
  -- line: [138, 206] id: 7
  local r5_7 = r1_7[1]
  local r6_7 = r1_7[2]
  local r7_7 = display.newGroup()
  local r8_7 = nil
  local r9_7 = nil
  r8_7 = r14_0("bar_loop")
  for r13_7 = 0, _G.Width, 16 do
    util.LoadParts(r7_7, r8_7, r13_7, 0)
  end
  local r10_7 = nil
  local r11_7 = nil
  local r12_7 = 0
  local r13_7 = nil
  r9_7 = r17_0(r6_7, 0, "left2")
  r7_7:insert(r9_7)
  r12_7 = r12_7 + r9_7.width
  if r0_7 then
    r9_7 = display.newEmbossedText(r7_7, r0_7, 0, 0, native.systemFontBold, r8_0)
    r9_7:setFillColor(255, 255, 255)
    r7_7:insert(r9_7)
    r9_7:setReferencePoint(display.TopLeftReferencePoint)
    r9_7.x = r12_7
    r9_7.y = (88 - r9_7.height) / 2
    r12_7 = r12_7 + r9_7.width
  end
  r10_7 = r12_7
  r9_7 = r17_0(r5_7, _G.Width, "left")
  r7_7:insert(r9_7)
  r11_7 = _G.Width - r9_7.width
  r9_7.x = r11_7
  r10_7 = r10_7 + 8
  r11_7 = r11_7 - 8
  if r4_7 then
    r9_7 = native.newTextBox(0, 88, _G.Width, 108)
    r9_7.isEditable = true
  else
    r9_7 = native.newTextField(r10_7, 26, r11_7 - r10_7, 36)
  end
  local r14_7 = r8_0
  if r9_0 then
    r14_7 = r14_7 * 0.5
  end
  r9_7.font = native.newFont(native.systemFont, r14_7)
  r9_7:setTextColor(0, 0, 0)
  if r2_7 then
    r9_7.text = r2_7
  end
  r7_7:insert(r9_7)
  if r3_7 then
    native.setKeyboardFocus(r9_7)
  end
  r7_0 = r9_7
  if r4_0 then
    display.remove(r4_0)
  end
  r4_0 = r7_7
end
local function r19_0(r0_8)
  -- line: [208, 219] id: 8
  if r0_8.action == "clicked" then
    local r1_8 = r0_8.index
    if r1_8 == 1 then
      r11_0.getRequestToken("GET", r12_0)
    elseif r1_8 == 2 then
      r10_0()
    end
  end
end
local function r20_0(r0_9)
  -- line: [221, 248] id: 9
  local r1_9 = r0_9.response
  if r0_9.isError then
    r13_0()
  else
    local r2_9 = r0_9.status
    if r2_9 == 401 then
      native.showAlert("DefenseWitches", "認証に失敗しました。", {
        "OK",
        "Cancel"
      }, r19_0)
    elseif r2_9 ~= 200 then
      native.showAlert("DefenseWitches", string.format("Twitter サーバと正常に通信出来ませんでした(%d)", r2_9), {
        "OK"
      }, function(r0_10)
        -- line: [237, 241] id: 10
        if r0_10.action == "clicked" then
          r10_0()
        end
      end)
    else
      r5_0 = nil
      r10_0()
    end
  end
end
local function r21_0(r0_11)
  -- line: [250, 258] id: 11
  r11_0.getAccess(r1_0.oauth_token, r1_0.oauth_token_secret).call(r2_0, "POST", {
    status = r0_11,
  }, r20_0)
end
local function r22_0()
  -- line: [260, 277] id: 12
  r18_0(nil, {
    {
      "POST",
      function(r0_13)
        -- line: [261, 267] id: 13
        local r1_13 = r7_0.text
        if r1_13 then
          r21_0(r1_13)
        end
        return true
      end
    },
    {
      "Cancel",
      function(r0_14)
        -- line: [268, 271] id: 14
        r10_0()
        return true
      end
    }
  }, r5_0, true, true)
end
local function r23_0(r0_15)
  -- line: [279, 313] id: 15
  if r0_15.isError then
    r13_0()
  else
    local r1_15 = r0_15.status
    if r1_15 == 401 then
      native.showAlert("DefenseWitches", "認証に失敗しました。", {
        "OK",
        "Cancel"
      }, r19_0)
    elseif r1_15 ~= 200 then
      native.showAlert("DefenseWitches", string.format("Twitter サーバと正常に通信出来ませんでした(%d)", r1_15), {
        "OK"
      }, function(r0_16)
        -- line: [294, 298] id: 16
        if r0_16.action == "clicked" then
          r10_0()
        end
      end)
    else
      local r3_15 = r11_0.parseAccessTokenResponse(r0_15.response)
      r1_0.oauth_token = r3_15.getToken()
      r1_0.oauth_token_secret = r3_15.getTokenSecret()
      assert(r1_0.oauth_token ~= nil)
      assert(r1_0.oauth_token_secret ~= nil)
      db.SetTwitterToken(r6_0, r1_0.oauth_token, r1_0.oauth_token_secret, r3_15.getUserId(), r3_15.getScreenName())
      r22_0()
    end
  end
end
local function r24_0(r0_17)
  -- line: [315, 329] id: 17
  local r1_17 = r7_0.text
  if r1_17 then
    native.cancelWebPopup()
    if r7_0 then
      r7_0:removeSelf()
      r7_0 = nil
    end
    if r4_0 then
      display.remove(r4_0)
      r4_0 = nil
    end
    r11_0.getAccessTokenByPin(r1_17, "POST", r1_0, r23_0)
  end
end
local function r25_0(r0_18)
  -- line: [331, 334] id: 18
  native.cancelWebPopup()
  r10_0()
end
function r12_0(r0_19)
  -- line: [336, 357] id: 19
  if r0_19.isError then
    r13_0()
  else
    local r2_19 = r11_0.parseRequestTokenResponse(r0_19.response)
    r1_0.oauth_token = r2_19.getToken()
    r1_0.oauth_token_secret = r2_19.getTokenSecret()
    assert(r1_0.oauth_token ~= nil)
    assert(r1_0.oauth_token_secret ~= nil)
    r18_0("PinCode", {
      {
        "OK",
        r24_0
      },
      {
        "Cancel",
        r25_0
      }
    }, false, false)
    local r3_19 = r4_0.height
    native.showWebPopup(0, r3_19, _G.Width, _G.Height - r3_19, r2_19.getAuthorizeUrl())
  end
end
function r10_0()
  -- line: [387, 400] id: 21
  if r7_0 then
    r7_0:removeSelf()
    r7_0 = nil
  end
  if r3_0 then
    display.remove(r3_0)
    r3_0 = nil
  end
  if r4_0 then
    display.remove(r4_0)
    r4_0 = nil
  end
end
return {
  Post = function(r0_20, r1_20, r2_20, r3_20)
    -- line: [359, 385] id: 20
    if _G.IsSimulator then
      DebugPrint("(" .. r1_20 .. ")twitter: " .. r2_20)
      return 
    end
    r3_0 = util.MakeMat(r0_20)
    r6_0 = r1_20
    r5_0 = r2_20 .. " " .. _G.GooglePlayURL[_G.UILanguage]
    local r5_20 = db.GettwitterToken(r1_20)
    if r5_20 == nil then
      r11_0.getRequestToken("GET", r12_0)
    else
      r1_0.oauth_token = r5_20.token
      r1_0.oauth_token_secret = r5_20.secret
      r1_0.twitter_id = r5_20.twitterid
      r1_0.user_name = r5_20.user
      r22_0()
    end
  end,
  Close = r10_0,
}
