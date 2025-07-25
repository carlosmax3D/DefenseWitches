-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("json")
local r1_0 = require("tool.fbook")
local r2_0 = require("tool.twitter")
local r3_0 = "posttweet.dat"
local r4_0 = 10
local r5_0 = 86400
local r6_0 = {
  "facebook",
  "twitter"
}
local r7_0 = "http://goo.gl/UmTN1I"
local r8_0 = nil
local r9_0 = nil
local r10_0 = nil
local r11_0 = nil
local r12_0 = nil
local r13_0 = {}
local r14_0 = nil
local function r15_0(r0_1)
  -- line: [24, 24] id: 1
  return "data/posttweet/" .. r0_1 .. ".png"
end
local function r16_0(r0_2)
  -- line: [25, 25] id: 2
  return r15_0(r0_2 .. _G.UILanguage)
end
local function r17_0(r0_3)
  -- line: [26, 26] id: 3
  return "data/option/" .. r0_3 .. ".png"
end
local function r18_0()
  -- line: [28, 50] id: 4
  local r0_4 = system.pathForFile(r3_0, system.DocumentsDirectory)
  local r1_4 = {}
  local r2_4 = io.open(r0_4, "r")
  if r2_4 then
    local r3_4 = 1
    while true do
      local r4_4 = r2_4:read()
      if r4_4 ~= nil then
        r1_4[r3_4] = r4_4
        r3_4 = r3_4 + 1
      else
        break
      end
    end
    io.close(r2_4)
    return r1_4
  else
    return nil
  end
end
local function r19_0()
  -- line: [53, 75] id: 5
  local r0_5 = {}
  for r4_5 = 1, #r6_0, 1 do
    r0_5[r6_0[r4_5]] = 0
  end
  local r1_5 = r18_0()
  if r1_5 ~= nil then
    for r5_5 = 1, #r1_5, 1 do
      local r6_5 = r1_5[r5_5]
      local r7_5 = r6_5:find(":")
      if r7_5 ~= nil then
        local r8_5 = r6_5:sub(1, r7_5 - 1)
        local r9_5 = r6_5:sub(r7_5 + 1, r6_5:len())
        if tonumber(r0_5[r8_5]) <= tonumber(r9_5) then
          r0_5[r8_5] = r9_5
        end
      end
    end
  end
  return r0_5
end
local function r20_0(r0_6)
  -- line: [78, 86] id: 6
  local r1_6 = r19_0()
  if r1_6[r0_6] ~= nil and os.time() <= tonumber(r1_6[r0_6]) + r5_0 then
    return false
  end
  return true
end
local function r21_0(r0_7)
  -- line: [88, 105] id: 7
  local r1_7 = r19_0()
  r1_7[r0_7] = os.time()
  local r2_7 = ""
  for r6_7 = 1, #r6_0, 1 do
    local r7_7 = r6_0[r6_7]
    r2_7 = r2_7 .. r7_7 .. ":" .. r1_7[r7_7] .. "\n"
  end
  local r4_7 = io.open(system.pathForFile(r3_0, system.DocumentsDirectory), "w")
  if r4_7 then
    r4_7:write(r2_7)
    io.close(r4_7)
  end
end
local function r22_0(r0_8, r1_8, r2_8)
  -- line: [107, 134] id: 8
  local r3_8 = 0
  local r4_8 = nil
  local r5_8 = {}
  local r6_8 = nil
  local r7_8 = r13_0.world
  if r7_8 ~= nil then
    r7_8 = r13_0.world or 1
  else
    goto label_12	-- block#2 is visited secondly
  end
  local r8_8 = r13_0.stage
  if r8_8 ~= nil then
    r8_8 = r13_0.stage or 1
  else
    goto label_21	-- block#5 is visited secondly
  end
  local r9_8 = r13_0.score
  if r9_8 ~= nil then
    r9_8 = r13_0.score or 0
  else
    goto label_30	-- block#8 is visited secondly
  end
  local r10_8 = db.GetInviteCode(_G.UserID)
  local r11_8 = _G.UILanguage
  if r11_8 == "jp" then
    r11_8 = "招待コード：" or "InviteCode:"
  else
    goto label_43	-- block#11 is visited secondly
  end
  local r12_8 = nil	-- notice: implicit variable refs by block#[16]
  if r10_8 ~= nil then
    r12_8 = r11_8 .. r10_8
    if not r12_8 then
      ::label_51::
      r12_8 = ""
    end
  else
    goto label_51	-- block#14 is visited secondly
  end
  for r16_8, r17_8 in pairs(r1_8) do
    for r22_8, r23_8 in pairs(util.StringSplit(string.gsub(string.format(r17_8, r7_8, r8_8, r9_8, r12_8), "(\\n)", function(r0_9)
      -- line: [121, 121] id: 9
      return "\n"
    end), "\n")) do
      r6_8 = display.newText(r23_8, 0, 0, native.SystemFont, r2_8)
      r4_8 = r6_8.width
      if r3_8 < r4_8 then
        r3_8 = r4_8
      end
      table.insert(r5_8, r6_8)
    end
  end
  return r3_8, r5_8
end
local function r23_0(r0_10, r1_10, r2_10, r3_10, r4_10)
  -- line: [136, 147] id: 10
  local r5_10 = nil	-- notice: implicit variable refs by block#[3]
  if type(r1_10) == "string" then
    r5_10 = display.newText(r0_10, r1_10, 0, 0, native.systemFont, r4_10)
  else
    r5_10 = r1_10
    r0_10:insert(r1_10)
  end
  r5_10.x = r2_10 / 2
  r5_10.y = r3_10 + r5_10.height / 2
  r5_10:setFillColor(255, 255, 255)
end
local function r24_0(r0_11, r1_11, r2_11)
  -- line: [149, 186] id: 11
  local r3_11 = display.newGroup()
  local r4_11 = string.format(db.GetMessage(r0_11), util.ConvertDisplayCrystal(r4_0))
  local r5_11 = {}
  for r9_11, r10_11 in pairs(r1_11) do
    if type(r10_11) == "string" then
      table.insert(r5_11, r10_11)
    else
      table.insert(r5_11, db.GetMessage(r10_11))
    end
  end
  local r6_11 = nil
  local r7_11 = nil
  r6_11, r7_11 = r22_0(r3_11, r5_11, 22)
  local r8_11 = table.maxn(r5_11)
  local r9_11 = 64 + r6_11 + 64
  local r10_11 = 128 + 40 * r8_11 + 62
  local r11_11 = -100
  util.LoadParts(r3_11, r15_0("valuation_plate"), 114 + r11_11, 110)
  local r12_11 = r3_11.width
  local r13_11 = r3_11.height
  r23_0(r3_11, r4_11, r12_11 + 25, 179, 35)
  for r17_11, r18_11 in pairs(r7_11) do
    r23_0(r3_11, r18_11, r12_11 + 25, 260 + 30 * (r17_11 - 1), 22)
  end
  util.LoadBtn({
    rtImg = r3_11,
    fname = r15_0("popup_facebook"),
    x = 266 + r11_11,
    y = 402,
    func = r2_11[1],
  })
  util.LoadBtn({
    rtImg = r3_11,
    fname = r15_0("popup_twitter"),
    x = 526 + r11_11,
    y = 402,
    func = r2_11[2],
  })
  util.LoadBtn({
    rtImg = r3_11,
    fname = r17_0("close"),
    x = 672,
    y = 100,
    func = r2_11[3],
  })
  return r3_11
end
local function r25_0()
  -- line: [188, 204] id: 12
  if r10_0 then
    transit.Delete(r10_0)
    r10_0 = nil
  end
  if r9_0 then
    display.remove(r9_0)
    r9_0 = nil
  end
  if r8_0 then
    display.remove(r8_0)
    r8_0 = nil
  end
  if r11_0 then
    r11_0(r12_0)
  end
end
local function r26_0(r0_13, r1_13, r2_13, r3_13)
  -- line: [206, 215] id: 13
  local r4_13 = nil	-- notice: implicit variable refs by block#[0]
  r11_0 = r4_13
  r25_0()
  r9_0 = util.MakeMat(r0_13)
  r8_0 = r24_0(r1_13, r2_13, r3_13)
  r0_13:insert(r8_0)
  r8_0:setReferencePoint(display.CenterReferencePoint)
  r8_0.x = _G.Width / 2
  r4_13 = r8_0
  r4_13.y = _G.Height / 2
end
return {
  ShowDialog = function(r0_14, r1_14, r2_14)
    -- line: [217, 326] id: 14
    local r3_14 = true
    for r7_14 = 1, #r6_0, 1 do
      if r20_0(r6_0[r7_14]) == false then
        r3_14 = false
      end
    end
    if r3_14 == false then
      return 
    end
    if r14_0 == true then
      return 
    end
    local function r4_14(r0_15)
      -- line: [236, 261] id: 15
      if r0_15.isError then
        return 
      end
      if r0_15.response == nil then
        return 
      end
      local r2_15 = r0_0.decode(r0_15.response)
      if r2_15 == nil or r2_15.status == 1 then
        return 
      elseif r2_15.status == 0 and r2_15.result ~= 0 and r2_15.result == 1 then
        local r3_15 = {}
        local r4_15 = r13_0.category
        if r4_15 ~= nil then
          r4_15 = r13_0.category or 0
        else
          goto label_38	-- block#13 is visited secondly
        end
        r3_15.category = r4_15
        statslog.LogSend("tweet", r3_15)
      end
    end
    local function r5_14(r0_16)
      -- line: [263, 285] id: 16
      r25_0()
      r21_0("facebook")
      _G.metrics.click_clear_fb()
      sound.PlaySE(1)
      local r1_16 = r13_0.world
      if r1_16 ~= nil then
        r1_16 = r13_0.world or 1
      else
        goto label_21	-- block#2 is visited secondly
      end
      local r2_16 = r13_0.stage
      if r2_16 ~= nil then
        r2_16 = r13_0.stage or 1
      else
        goto label_30	-- block#5 is visited secondly
      end
      local r3_16 = r13_0.score
      if r3_16 ~= nil then
        r3_16 = r13_0.score or 0
      else
        goto label_39	-- block#8 is visited secondly
      end
      local r4_16 = db.GetInviteCode(_G.UserID)
      local r5_16 = _G.UILanguage
      if r5_16 == "jp" then
        r5_16 = "招待コード：" or "InviteCode:"
      else
        goto label_52	-- block#11 is visited secondly
      end
      local r6_16 = nil	-- notice: implicit variable refs by block#[15]
      if r4_16 ~= nil then
        r6_16 = r5_16 .. r4_16
        if not r6_16 then
          ::label_60::
          r6_16 = ""
        end
      else
        goto label_60	-- block#14 is visited secondly
      end
      r1_0.Post(_G.PostRoot, "DefenseWitches", string.gsub(string.format(db.GetMessage(340), r1_16, r2_16, r3_16, r6_16), "(\\n)", function(r0_17)
        -- line: [277, 277] id: 17
        return " "
      end), "DefenseWitches", nil)
      if _G.UserInquiryID ~= nil and _G.UserToken ~= nil then
        r13_0.category = 1
        server.PostTweet(_G.UserInquiryID, "facebook", r4_14)
      end
    end
    local function r6_14(r0_18)
      -- line: [287, 308] id: 18
      r25_0()
      r21_0("twitter")
      sound.PlaySE(1)
      local r1_18 = r13_0.world
      if r1_18 ~= nil then
        r1_18 = r13_0.world or 1
      else
        goto label_17	-- block#2 is visited secondly
      end
      local r2_18 = r13_0.stage
      if r2_18 ~= nil then
        r2_18 = r13_0.stage or 1
      else
        goto label_26	-- block#5 is visited secondly
      end
      local r3_18 = r13_0.score
      if r3_18 ~= nil then
        r3_18 = r13_0.score or 0
      else
        goto label_35	-- block#8 is visited secondly
      end
      local r4_18 = db.GetInviteCode(_G.UserID)
      local r5_18 = _G.UILanguage
      if r5_18 == "jp" then
        r5_18 = " 招待コード：" or " InviteCode:"
      else
        goto label_48	-- block#11 is visited secondly
      end
      local r6_18 = nil	-- notice: implicit variable refs by block#[15]
      if r4_18 ~= nil then
        r6_18 = r5_18 .. r4_18
        if not r6_18 then
          ::label_56::
          r6_18 = ""
        end
      else
        goto label_56	-- block#14 is visited secondly
      end
      r2_0.Post(_G.PostRoot, _G.UserID, string.gsub(string.format(db.GetMessage(341), r1_18, r2_18, r3_18, r6_18), "(\\n)", function(r0_19)
        -- line: [299, 299] id: 19
        return " "
      end), nil)
      _G.metrics.click_clear_twtter()
      if _G.UserInquiryID ~= nil and _G.UserToken ~= nil then
        r13_0.category = 2
        server.PostTweet(_G.UserInquiryID, "twitter", r4_14)
      end
    end
    local function r7_14()
      -- line: [310, 313] id: 20
      r14_0 = true
      r25_0()
    end
    r13_0 = {
      world = r0_14,
      stage = r1_14,
      score = r2_14,
      category = 0,
    }
    local r8_14 = display.newGroup()
    local r9_14 = util.MakeGroup(r8_14)
    util.MakeFrame(r8_14)
    r26_0(r9_14, 338, {
      339
    }, {
      r5_14,
      r6_14,
      r7_14
    })
  end,
  Init = function()
    -- line: [328, 329] id: 21
  end,
}
