-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("json")
local r1_0 = require("scene.help")
local r2_0 = "data/help/index/"
local r3_0 = "defensewitches@stargarage.jp"
local r4_0 = false
local function r5_0(r0_1)
  -- line: [14, 14] id: 1
  return r2_0 .. r0_1 .. ".png"
end
local function r6_0(r0_2)
  -- line: [15, 15] id: 2
  return r5_0(r0_2 .. _G.UILanguage)
end
local function r7_0(r0_3)
  -- line: [16, 16] id: 3
  return "data/option/" .. r0_3 .. ".png"
end
local function r8_0(r0_4)
  -- line: [17, 17] id: 4
  return r7_0(r0_4 .. _G.UILanguage)
end
local function r15_0(r0_11)
  -- line: [58, 75] id: 11
  if _G.UserInquiryID then
    native.showAlert("DefenseWitches", db.GetMessage(221), {
      "OK",
      "Cancel"
    }, function(r0_12)
      -- line: [63, 69] id: 12
      if r0_12.action == "clicked" and r0_12.index == 1 then
        r1_0.CallAddr()
      end
    end)
  else
    native.showAlert("DefenseWitches", db.GetMessage(84), {
      "OK"
    })
  end
  return true
end
local function r16_0(r0_13)
  -- line: [77, 80] id: 13
  r1_0.QuitHelp()
  return true
end
local function r17_0(r0_14, r1_14, r2_14, r3_14, r4_14, r5_14)
  -- line: [82, 108] id: 14
  local r6_14 = display.newGroup()
  local r7_14 = nil
  local r8_14 = nil
  local r9_14 = nil
  local r10_14 = nil
  local r11_14 = nil
  r8_14 = util.LoadParts(r6_14, r5_0("index_mail"), 0, 0).width
  local r12_14 = util.MakeText(r1_14, r3_14, r4_14, r5_14)
  r12_14:setReferencePoint(display.TopLeftReferencePoint)
  r12_14.x = r8_14 + 8
  r12_14.y = (r2_14[4] - r12_14.height) / 2
  r7_14 = display.newLine(r12_14, 0, r1_14, r12_14.width, r1_14)
  r7_14:setColor(r3_14[1], r3_14[2], r3_14[3])
  r7_14.strokeWidth = 1
  r6_14:insert(r12_14)
  r6_14:setReferencePoint(display.CenterReferencePoint)
  r6_14.x = r2_14[1] + r2_14[3] / 2
  r6_14.y = r2_14[2] + r2_14[4] / 2
  r0_14:insert(r6_14)
  return r6_14
end
local r18_0 = {
  function(r0_5)
    -- line: [19, 23] id: 5
    sound.PlaySE(1)
    r1_0.ViewHelp("story")
    return true
  end,
  function(r0_6)
    -- line: [25, 29] id: 6
    sound.PlaySE(1)
    r1_0.ViewHelp("howto")
    return true
  end,
  function(r0_7)
    -- line: [31, 35] id: 7
    sound.PlaySE(1)
    r1_0.ViewHelp("crystal")
    return true
  end,
  function(r0_8)
    -- line: [37, 41] id: 8
    sound.PlaySE(1)
    r1_0.ViewHelp("witches")
    return true
  end,
  function(r0_9)
    -- line: [43, 47] id: 9
    sound.PlaySE(1)
    r1_0.ViewHelp("enemy")
    return true
  end,
  function()
    -- line: [52, 56] id: 10
    sound.PlaySE(1)
    r1_0.ViewHelp("credit")
    return true
  end
}
local r19_0 = nil
local function r20_0(r0_15, r1_15, r2_15)
  -- line: [115, 152] id: 15
  if not r4_0 then
    return 
  end
  local r3_15 = nil
  local r4_15 = db.GetMessage(83)
  if r1_15 then
    r3_15 = "ID:" .. r1_15
  else
    r3_15 = "ID:" .. r4_15
  end
  r3_15 = r3_15 .. "\u{3000}"
  if r2_15 then
    r3_15 = r3_15 .. "PASS:" .. r2_15
  else
    r3_15 = r3_15 .. "PASS:" .. r4_15
  end
  if r19_0 then
    if r19_0 then
      display.remove(r19_0)
    end
    r19_0 = nil
  end
  local r5_15 = nil
  r5_15 = util.MakeText(28, {
    153,
    0,
    0
  }, {
    242,
    234,
    218
  }, r3_15)
  if r0_15 ~= nil then
    r0_15:insert(r5_15)
  end
  r5_15:setReferencePoint(display.CenterReferencePoint)
  r5_15.x = 480
  r5_15.y = 424
  r19_0 = r5_15
end
return {
  Load = function(r0_16, r1_16)
    -- line: [154, 238] id: 16
    local r2_16 = display.newGroup()
    local r3_16 = nil
    util.LoadParts(r2_16, r6_0("index_title_"), 192, 16)
    local r4_16 = 120
    local r5_16 = 88
    local r6_16 = nil
    for r10_16, r11_16 in pairs(r18_0) do
      r6_16 = string.format("index_button%02d_", r10_16)
      util.LoadBtn({
        rtImg = r2_16,
        fname = r6_0(r6_16),
        x = r4_16,
        y = r5_16,
        func = r11_16,
      })
      r4_16 = r4_16 + 240
      if r4_16 > 600 then
        r4_16 = 120
        r5_16 = r5_16 + 144
      end
    end
    local r7_16 = _G.UserID
    local r8_16 = _G.UserInquiryID
    local r9_16 = nil
    local r10_16 = nil
    if r8_16 then
      r8_16 = tostring(math.floor(_G.UserInquiryID))
    end
    r19_0 = nil
    r4_0 = true
    r20_0(r2_16, r8_16, r9_16)
    if _G.UserInquiryID ~= nil then
      if not r8_16 then
        r4_0 = true
        server.GetInquiryID(function(r0_17)
          -- line: [191, 197] id: 17
          r8_16 = _G.UserInquiryID
          if r8_16 then
            r8_16 = tostring(math.floor(_G.UserInquiryID))
            r20_0(r2_16, r8_16, r9_16)
          end
        end, nil)
      end
      server.GetPassword(_G.UserToken, function(r0_18)
        -- line: [200, 212] id: 18
        if server.CheckError(r0_18) then
          server.NetworkError(35)
        else
          local r1_18 = r0_0.decode(r0_18.response)
          if r1_18.status == 0 then
            r9_16 = r1_18.password
            r20_0(r2_16, r8_16, r9_16)
          else
            server.ServerError(r1_18.status)
          end
        end
      end)
    end
    util.SetBtnFunction(r17_0(r2_16, 24, {
      128,
      452,
      704,
      32
    }, {
      38,
      76,
      153
    }, {
      242,
      234,
      218
    }, r3_0), r15_0)
    r3_16 = util.MakeText(20, {
      38,
      29,
      15
    }, {
      242,
      234,
      218
    }, db.GetMessage(159), 704, 48)
    r2_16:insert(r3_16)
    r3_16.x = 128
    r3_16.y = 496
    util.LoadTileParts(r2_16, 120, 384, db.LoadTileData("help", "line"), "data/help")
    util.LoadBtn({
      rtImg = r2_16,
      fname = r8_0("quit_"),
      x = 368,
      y = 560,
      func = r16_0,
    })
    r0_16:insert(r2_16)
    return r2_16
  end,
  Cleanup = function()
    -- line: [240, 242] id: 19
    r4_0 = false
  end,
}
