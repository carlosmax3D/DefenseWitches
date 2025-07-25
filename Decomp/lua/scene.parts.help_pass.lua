-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("json")
local r1_0 = require("scene.help")
local r2_0 = 790
local r3_0 = 470
local r4_0 = {
  85,
  85
}
local r5_0 = 48
local r6_0 = nil
local r7_0 = nil
local r8_0 = true
local function r9_0(r0_1, r1_1)
  -- line: [18, 68] id: 1
  local r2_1 = db.GetMessage(r1_1)
  local r3_1 = nil
  local r4_1 = 8
  r3_1 = util.MakeText(32, {
    255,
    255,
    255
  }, {
    0,
    0,
    0
  }, r2_1, r2_0 - 16, r3_0 - 16)
  r3_1:setReferencePoint(display.TopLeftReferencePoint)
  r3_1.x = 8
  r3_1.y = r4_1
  r4_1 = r4_1 + 96
  r0_1:insert(r3_1)
  local r5_1 = display.newGroup()
  r3_1 = util.MakeText3({
    rtImg = r5_1,
    size = r5_0,
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
    msg = "Password: " .. r6_0,
  })
  r3_1:setReferencePoint(display.TopLeftReferencePoint)
  r3_1.x = 0
  r3_1.y = 52
  local r6_1 = r3_1.width
  r3_1 = util.MakeText3({
    rtImg = r5_1,
    size = r5_0,
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
    msg = "ID: " .. _G.UserInquiryID,
  })
  r3_1:setReferencePoint(display.TopRightReferencePoint)
  r3_1.x = r6_1
  r3_1.y = 0
  r0_1:insert(r5_1)
  r5_1:setReferencePoint(display.CenterReferencePoint)
  r5_1.x = r2_0 * 0.5
  r5_1.y = r3_0 * 0.5 - 32
  r4_1 = r4_1 + r5_1.height + 16
  r3_1 = util.MakeText3({
    rtImg = r0_1,
    size = 18,
    x = 8,
    y = r3_0 - 64 - 52,
    width = r2_0 - 16,
    height = 52,
    color = {
      255,
      0,
      0
    },
    shadow = {
      0,
      0,
      0
    },
    msg = db.GetMessage(237),
  })
end
local function r10_0(r0_2)
  -- line: [70, 77] id: 2
  local r1_2 = display.newRoundedRect(r0_2, 0, 0, r2_0, r3_0, 12)
  r1_2.strokeWidth = 2
  r1_2:setStrokeColor(255, 255, 255)
  r1_2:setFillColor(0, 0, 0, 192)
end
local function r11_0(r0_3)
  -- line: [81, 116] id: 3
  local function r1_3()
    -- line: [82, 86] id: 4
    if r0_3 ~= nil then
      r0_3()
    end
  end
  local r2_3 = _G.BingoManager.getBingoCardId()
  _G.BingoManager.isBingoEnabled(r2_3, function(r0_5)
    -- line: [90, 113] id: 5
    if r0_5 == false or r8_0 == false then
      r1_3()
      return 
    end
    local r1_5 = display.newGroup()
    r7_0:insert(r1_5)
    local r2_5 = _G.BingoManager.getUserClearedDataList()
    if r2_5 == nil or #r2_5 < 1 then
      r1_3()
      return 
    end
    local r3_5 = require("bingo.bingo_card").new({
      rootLayer = r1_5,
      bingoCardId = r2_3,
    })
    r3_5.show(true, true)
    r3_5.setMarkedByList(r2_5, function()
      -- line: [109, 112] id: 6
      _G.BingoManager.setUserClearedDataList()
      r1_3()
    end)
  end)
end
local function r12_0(r0_7)
  -- line: [118, 123] id: 7
  sound.PlaySE(2)
  r1_0.ViewHelp("witches", r0_7.param)
  r11_0()
  return true
end
local function r13_0(r0_8)
  -- line: [125, 132] id: 8
  local r1_8 = r0_8.param
  sound.PlaySE(1)
  r11_0(function()
    -- line: [128, 130] id: 9
    r1_8()
  end)
  return true
end
local function r14_0(r0_10, r1_10, r2_10, r3_10)
  -- line: [134, 172] id: 10
  local function r4_10(r0_11)
    -- line: [135, 135] id: 11
    return "data/stage/" .. r0_11 .. ".png"
  end
  local function r5_10(r0_12)
    -- line: [136, 136] id: 12
    return r4_10(r0_12 .. _G.UILanguage)
  end
  local function r6_10(r0_13)
    -- line: [137, 137] id: 13
    return "data/powerup/" .. r0_13 .. ".png"
  end
  local function r7_10(r0_14)
    -- line: [138, 138] id: 14
    return r6_10(r0_14 .. _G.UILanguage)
  end
  local r8_10 = display.newGroup()
  local r9_10 = display.newGroup()
  local r10_10 = display.newGroup()
  r8_10:insert(r9_10)
  r8_10:insert(r10_10)
  r10_0(r9_10)
  r9_0(r10_10, r1_10)
  r8_10:setReferencePoint(display.TopLeftReferencePoint)
  r8_10.x = r4_0[1]
  r8_10.y = r4_0[2]
  if r2_10 == nil then
    util.LoadBtn({
      rtImg = r8_10,
      fname = r5_10("back_"),
      x = (r2_0 - 192) / 2,
      y = r3_0 - 64 - 8,
      func = r12_0,
      param = r3_10,
    })
  else
    util.LoadBtn({
      rtImg = r8_10,
      fname = r7_10("ok_"),
      x = (r2_0 - 192) / 2,
      y = r3_0 - 64 - 8,
      func = r13_0,
      param = r2_10,
    })
  end
  r0_10:insert(r8_10)
  return r8_10
end
local function r15_0(r0_15, r1_15, r2_15, r3_15)
  -- line: [177, 182] id: 15
  db.SaveToServer2(_G.UserToken)
  return r14_0(r0_15, r1_15, r2_15, r3_15)
end
local function r16_0()
  -- line: [184, 186] id: 16
  r1_0.ViewHelp("witches")
end
local function r17_0(r0_17)
  -- line: [188, 196] id: 17
  local r1_17 = display.newGroup()
  local r2_17 = display.newRect(r1_17, 0, 0, 10, 10)
  r2_17:setFillColor(0, 0, 0)
  r2_17.alpha = 0.01
  r0_17:insert(r1_17)
  return r1_17
end
local function r19_0(r0_20, r1_20)
  -- line: [226, 245] id: 20
  r7_0 = r0_20
  local r2_20 = _G.UserID
  server.MakePassword(_G.UserToken, function(r0_21)
    -- line: [232, 244] id: 21
    if server.CheckError(r0_21) then
      server.NetworkError(35, r16_0)
    else
      local r1_21 = r0_0.decode(r0_21.response)
      if r1_21.status == 0 then
        r6_0 = r1_21.password
        return r14_0(r0_20, 158, r1_20)
      else
        server.ServerError(r1_21.status, r16_0)
      end
    end
  end)
end
return {
  Load = function(r0_18, r1_18)
    -- line: [198, 224] id: 18
    r7_0 = r0_18
    local r2_18 = _G.UserID
    local r3_18 = _G.UserToken
    if r3_18 == nil then
      server.NetworkError(35, r16_0)
      return r17_0(r0_18)
    end
    local r4_18 = nil
    server.GetPassword(r3_18, function(r0_19)
      -- line: [209, 221] id: 19
      if server.CheckError(r0_19) then
        server.NetworkError(35, r16_0)
      else
        local r1_19 = r0_0.decode(r0_19.response)
        if r1_19.status == 0 then
          r6_0 = r1_19.password
          return r15_0(r4_18, 157, nil, r1_18)
        else
          server.ServerError(r1_19.status, r16_0)
        end
      end
    end)
    r4_18 = r17_0(r0_18)
    return r4_18
  end,
  Update = r19_0,
  UpdateForContGame = function(r0_22, r1_22)
    -- line: [248, 254] id: 22
    r8_0 = false
    r19_0(r0_22, r1_22)
  end,
  View = function(r0_23, r1_23)
    -- line: [256, 275] id: 23
    r7_0 = r0_23
    local r2_23 = _G.UserID
    server.GetPassword(_G.UserToken, function(r0_24)
      -- line: [262, 274] id: 24
      if server.CheckError(r0_24) then
        server.NetworkError(35, r1_23)
      else
        local r1_24 = r0_0.decode(r0_24.response)
        if r1_24.status == 0 then
          r6_0 = r1_24.password
          return r15_0(r0_23, 157, r1_23)
        else
          server.ServerError(r1_24.status, r1_23)
        end
      end
    end)
  end,
}
