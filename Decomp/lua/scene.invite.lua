-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("json")
local r1_0 = require("tool.fbook")
local r2_0 = require("tool.twitter")
local r3_0 = nil
local r4_0 = nil
local r5_0 = nil
local r6_0 = nil
local r7_0 = nil
local r8_0 = 20
local r9_0 = nil
local function r10_0(r0_1)
  -- line: [16, 16] id: 1
  return "data/title/" .. r0_1 .. ".png"
end
local function r11_0(r0_2)
  -- line: [17, 17] id: 2
  return "data/option/" .. r0_2 .. ".png"
end
local function r12_0(r0_3)
  -- line: [18, 18] id: 3
  return "data/invite/" .. r0_3 .. ".png"
end
local function r13_0(r0_4)
  -- line: [19, 19] id: 4
  return r12_0(r0_4 .. _G.UILanguage)
end
local function r14_0(r0_5)
  -- line: [21, 45] id: 5
  if r3_0 == nil then
    return true
  end
  if r7_0 == nil then
    sound.PlaySE(2)
    return 
  end
  sound.PlaySE(1)
  _G.metrics.click_invite_mail()
  local r1_5 = nil
  local r2_5 = nil
  local r3_5 = nil
  r1_5 = db.GetMessage(175)
  r2_5 = string.gsub(string.format(db.GetMessage(176), tostring(r7_0)), "(\\n)", function(r0_6)
    -- line: [37, 37] id: 6
    return "\n"
  end)
  local r4_5 = _G.iTunesURL[_G.UILanguage]
  if r4_5 then
    r2_5 = r2_5 .. "\n" .. r4_5
  end
  server.Mailto("", {
    subject = r1_5,
    body = r2_5,
  })
end
local function r15_0(r0_7)
  -- line: [47, 64] id: 7
  if r3_0 == nil then
    return true
  end
  if r7_0 == nil then
    sound.PlaySE(2)
    return true
  end
  sound.PlaySE(1)
  _G.metrics.click_invite_twtter()
  r2_0.Post(r3_0, _G.UserID, string.format(db.GetMessage(174), tostring(r7_0)) .. " #dwitch", nil)
  return true
end
local function r16_0(r0_8)
  -- line: [66, 83] id: 8
  if r3_0 == nil then
    return true
  end
  if r7_0 == nil then
    sound.PlaySE(2)
    return true
  end
  sound.PlaySE(1)
  _G.metrics.click_invite_fb()
  r1_0.Post(r3_0, "DefenseWitches", string.format(db.GetMessage(174), tostring(r7_0)), "", nil)
  return true
end
local function r17_0(r0_9)
  -- line: [85, 159] id: 9
  if r3_0 == nil then
    return 
  end
  if server.CheckError(r0_9) then
    server.NetworkError(35, Close)
    return 
  end
  local r1_9 = r0_0.decode(r0_9.response)
  if r1_9.status == 2 then
    r4_0.isVisible = false
    r5_0.isVisible = true
    return 
  end
  if r1_9.status ~= 0 then
    server.ServerError(r1_9.status, Close)
    return 
  end
  local r2_9 = r1_9.persons
  if r2_9 == 0 then
    return 
  end
  local r3_9 = r1_9.rewardpersons
  if r3_9 == nil then
    r3_9 = math.floor(r1_9.reward / _G.GetCrystal)
  end
  if r3_9 then
    kpi.Invitation(r3_9)
  end
  local r4_9 = display.newGroup()
  local r5_9 = 0
  local r6_9 = nil
  r6_9 = display.newImage(r4_9, r12_0("invite_star"), 0, 0)
  r6_9:setReferencePoint(display.CenterLeftReferencePoint)
  r6_9.x = r5_9
  r6_9.y = 16
  r5_9 = r5_9 + r6_9.width + 8
  r6_9 = util.MakeText(24, {
    255,
    255,
    255
  }, {
    0,
    0,
    0
  }, db.GetMessage(170))
  r4_9:insert(r6_9)
  r6_9:setReferencePoint(display.CenterLeftReferencePoint)
  r6_9.x = r5_9
  r6_9.y = 16
  r5_9 = r5_9 + r6_9.width
  r6_9 = util.MakeText(24, {
    255,
    191,
    0
  }, {
    0,
    0,
    0
  }, string.format(db.GetMessage(171), r2_9))
  r4_9:insert(r6_9)
  r6_9:setReferencePoint(display.CenterLeftReferencePoint)
  r6_9.x = r5_9
  r6_9.y = 16
  r5_9 = r5_9 + r6_9.width
  r6_9 = util.MakeText(24, {
    255,
    255,
    255
  }, {
    0,
    0,
    0
  }, db.GetMessage(172))
  r4_9:insert(r6_9)
  r6_9:setReferencePoint(display.CenterLeftReferencePoint)
  r6_9.x = r5_9
  r6_9.y = 16
  r5_9 = r5_9 + r6_9.width + 8
  r6_9 = display.newImage(r4_9, r12_0("invite_star"), 0, 0)
  r6_9:setReferencePoint(display.CenterLeftReferencePoint)
  r6_9.x = r5_9
  r6_9.y = 16
  r3_0:insert(r4_9)
  r4_9:setReferencePoint(display.CenterReferencePoint)
  r4_9.x = 480
  r4_9.y = 568
end
local function r18_0(r0_10)
  -- line: [161, 172] id: 10
  if r3_0 == nil then
    return 
  end
  if r6_0 then
    display.remove(r6_0)
    r6_0 = nil
  end
  local r1_10 = nil
  r6_0 = util.MakeCenterText(r4_0, 40, {
    88,
    328,
    784,
    40
  }, {
    255,
    225,
    76
  }, {
    0,
    0,
    0
  }, db.GetMessage(165) .. " : " .. tostring(r0_10))
end
local function r19_0(r0_11)
  -- line: [174, 187] id: 11
  if server.CheckError(r0_11) then
    server.NetworkError(35, Close)
  else
    local r1_11 = r0_0.decode(r0_11.response)
    if r1_11.status == 0 then
      r7_0 = r1_11.code
      db.SetInviteCode(_G.UserID, r7_0)
      r18_0(r7_0)
    else
      server.ServerError(r1_11.status, Close)
    end
  end
end
local function r20_0(r0_12)
  -- line: [189, 198] id: 12
  local r1_12 = nil	-- notice: implicit variable refs by block#[0]
  r3_0 = r1_12
  sound.PlaySE(2)
  r1_12 = r9_0
  if r1_12 ~= nil then
    util.ChangeScene({
      scene = r9_0,
      efx = "fade",
    })
  else
    util.ChangeScene({
      scene = "title",
      efx = "downFlip",
    })
  end
  r1_12 = true
  return r1_12
end
return {
  new = function(r0_13)
    -- line: [200, 266] id: 13
    local r1_13 = _G.UserID
    local r2_13 = _G.UserToken
    r3_0 = display.newGroup()
    r9_0 = r0_13.back
    util.LoadBG(r3_0, r12_0("invite_bg"))
    util.LoadParts(r3_0, r13_0("invite_title_"), 88, 48)
    util.MakeText2({
      rtImg = r3_0,
      size = 24,
      line = 32,
      x = 88,
      y = 232,
      w = 784,
      h = 64,
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
      msg = string.format(db.GetMessage(167), util.ConvertDisplayCrystal(_G.GetCrystal)),
    })
    r4_0 = display.newGroup()
    r3_0:insert(r4_0)
    r5_0 = display.newGroup()
    r3_0:insert(r5_0)
    local r4_13 = db.GetInviteCode(r1_13)
    if r4_13 == nil or r4_13 == "" then
      r18_0(db.GetMessage(173))
      server.InvitePublish(r2_13, r19_0)
    else
      r7_0 = r4_13
      r18_0(r7_0)
    end
    util.LoadParts(r4_0, r13_0("invite_howdo_"), 264, 400)
    util.LoadBtn({
      rtImg = r4_0,
      fname = r10_0("title_facebook"),
      x = 280,
      y = 440,
      func = r16_0,
    })
    util.LoadBtn({
      rtImg = r4_0,
      fname = r10_0("title_twitter"),
      x = 432,
      y = 440,
      func = r15_0,
    })
    util.LoadBtn({
      rtImg = r4_0,
      fname = r12_0("invite_howdo_mail"),
      x = 568,
      y = 440,
      func = r14_0,
    })
    util.MakeText2({
      rtImg = r5_0,
      size = 26,
      line = 32,
      x = 88,
      y = 368,
      w = 784,
      h = 112,
      color = {
        255,
        225,
        76
      },
      shadow = {
        0,
        0,
        0
      },
      msg = db.GetMessage(177),
    })
    r5_0.isVisible = false
    util.LoadBtn({
      rtImg = r3_0,
      fname = r11_0("close"),
      x = 872,
      y = 0,
      func = r20_0,
    })
    server.InvitePersons(r2_13, r17_0)
    return r3_0
  end,
  Close = function()
    -- line: [268, 270] id: 14
    r20_0()
  end,
  GetMaxPersons = function()
    -- line: [272, 274] id: 15
    return r8_0
  end,
}
