-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = "review.dat"
local r1_0 = "_reponse_file"
local r2_0 = 0
local r3_0 = false
local r4_0 = _G.REVIEW_SERVER_URL
local r5_0 = r4_0 .. "req"
local r6_0 = r4_0 .. "getcoin"
local r7_0 = nil
local r8_0 = nil
local r9_0 = nil
local r10_0 = nil
local r11_0 = nil
local function r12_0(r0_1)
  -- line: [21, 21] id: 1
  return "data/review/" .. r0_1 .. ".png"
end
local function r13_0(r0_2)
  -- line: [23, 23] id: 2
  return r12_0(r0_2 .. _G.UILanguage)
end
local function r14_0(r0_3)
  -- line: [25, 46] id: 3
  if r0_3.isError then
    DebugPrint("GETCOIN:Network error")
  elseif r0_3.status == 200 then
    DebugPrint("GETCOIN:Download OK. " .. r0_3.status)
    local r2_3 = io.open(system.pathForFile(r1_0, system.TemporaryDirectory), "r")
    if r2_3 then
      r2_0 = tonumber(r2_3:read())
      io.close(r2_3)
    else
      DebugPrint("GETCOIN:Download File NG.")
    end
  else
    DebugPrint("GETCOIN:Download NG. " .. r0_3.status)
  end
end
local function r15_0()
  -- line: [48, 70] id: 4
  local r0_4 = system.pathForFile(r0_0, system.DocumentsDirectory)
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
local function r16_0()
  -- line: [72, 85] id: 5
  local r0_5 = r15_0()
  if r0_5 ~= nil then
    local r1_5 = 1
    for r5_5 = 1, #r0_5, 1 do
      if r0_5[r1_5] == _G.Version then
        return false
      end
      r1_5 = r1_5 + 1
    end
  end
  return true
end
local function r17_0()
  -- line: [88, 110] id: 6
  if r3_0 == false then
    return 
  end
  if r16_0() == false then
    return 
  end
  local r0_6 = r6_0 .. "?uid=" .. _G.UserInquiryID .. "&ver=" .. _G.Version
  DebugPrint("url=" .. r0_6)
  network.download(r0_6, "GET", r14_0, {
    timeout = 3,
  }, r1_0, system.TemporaryDirectory)
end
local function r18_0()
  -- line: [112, 120] id: 7
  local r1_7 = io.open(system.pathForFile(r0_0, system.DocumentsDirectory), "a+")
  if r1_7 then
    r1_7:write(_G.Version .. "\n")
    io.close(r1_7)
  end
end
local function r19_0()
  -- line: [122, 124] id: 8
  return r2_0
end
local function r20_0()
  -- line: [126, 133] id: 9
  if r3_0 == false then
    return 
  end
  system.openURL(r5_0 .. "?uid=" .. _G.UserInquiryID .. "&ver=" .. _G.Version)
end
local function r21_0(r0_10, r1_10, r2_10)
  -- line: [135, 158] id: 10
  local r3_10 = 0
  local r4_10 = nil
  local r5_10 = {}
  local r6_10 = nil
  local r7_10 = r19_0()
  for r11_10, r12_10 in pairs(r1_10) do
    for r17_10, r18_10 in pairs(util.StringSplit(string.gsub(r12_10, "(\\n)", function(r0_11)
      -- line: [144, 144] id: 11
      return "\n"
    end), "\n")) do
      r18_10 = string.format(r18_10, util.ConvertDisplayCrystal(r7_10))
      r6_10 = display.newText(r18_10, 0, 0, native.SystemFont, r2_10)
      r4_10 = r6_10.width
      if r3_10 < r4_10 then
        r3_10 = r4_10
      end
      table.insert(r5_10, r6_10)
    end
  end
  return r3_10, r5_10
end
local function r22_0(r0_12, r1_12, r2_12, r3_12, r4_12)
  -- line: [160, 171] id: 12
  local r5_12 = nil	-- notice: implicit variable refs by block#[3]
  if type(r1_12) == "string" then
    r5_12 = display.newText(r0_12, r1_12, 0, 0, native.systemFont, r4_12)
  else
    r5_12 = r1_12
    r0_12:insert(r1_12)
  end
  r5_12.x = r2_12 / 2
  r5_12.y = r3_12 + r5_12.height / 2
  r5_12:setFillColor(255, 255, 255)
end
local function r23_0(r0_13, r1_13, r2_13)
  -- line: [173, 210] id: 13
  local r3_13 = display.newGroup()
  local r4_13 = db.GetMessage(r0_13)
  local r5_13 = {}
  for r9_13, r10_13 in pairs(r1_13) do
    if type(r10_13) == "string" then
      table.insert(r5_13, r10_13)
    else
      table.insert(r5_13, db.GetMessage(r10_13))
    end
  end
  local r6_13 = nil
  local r7_13 = nil
  r6_13, r7_13 = r21_0(r3_13, r5_13, 24)
  local r8_13 = table.maxn(r5_13)
  local r9_13 = 64 + r6_13 + 64
  local r10_13 = 128 + 40 * r8_13 + 62
  local r11_13 = -100
  util.LoadParts(r3_13, r12_0("valuation_plate"), 114 + r11_13, 110)
  local r12_13 = r3_13.width
  local r13_13 = r3_13.height
  r22_0(r3_13, r4_13, r12_13, 179, 37)
  for r17_13, r18_13 in pairs(r7_13) do
    r22_0(r3_13, r18_13, r12_13 + 25, 275 + 40 * (r17_13 - 1), 24)
  end
  util.LoadBtn({
    rtImg = r3_13,
    fname = r13_0("button_01_"),
    x = 156 + r11_13,
    y = 402,
    func = r2_13[1],
  })
  util.LoadBtn({
    rtImg = r3_13,
    fname = r13_0("button_02_"),
    x = 376 + r11_13,
    y = 402,
    func = r2_13[2],
  })
  util.LoadBtn({
    rtImg = r3_13,
    fname = r13_0("button_03_"),
    x = 596 + r11_13,
    y = 402,
    func = r2_13[3],
  })
  return r3_13
end
local function r24_0()
  -- line: [212, 228] id: 14
  if r9_0 then
    transit.Delete(r9_0)
    r9_0 = nil
  end
  if r8_0 then
    display.remove(r8_0)
    r8_0 = nil
  end
  if r7_0 then
    display.remove(r7_0)
    r7_0 = nil
  end
  if r10_0 then
    r10_0(r11_0)
  end
end
local function r25_0(r0_15, r1_15, r2_15, r3_15)
  -- line: [230, 239] id: 15
  local r4_15 = nil	-- notice: implicit variable refs by block#[0]
  r10_0 = r4_15
  r24_0()
  r8_0 = util.MakeMat(r0_15)
  r7_0 = r23_0(r1_15, r2_15, r3_15)
  r0_15:insert(r7_0)
  r7_0:setReferencePoint(display.CenterReferencePoint)
  r7_0.x = _G.Width / 2
  r4_15 = r7_0
  r4_15.y = _G.Height / 2
end
return {
  DlgClose = r24_0,
  Init = function()
    -- line: [310, 319] id: 20
    local r0_20 = _G.UserInquiryID ~= nil
    r2_0 = 0
    r17_0()
  end,
  ShowDialog = function(r0_16, r1_16)
    -- line: [241, 308] id: 16
    if r3_0 == false then
      return false
    end
    if r0_16 == 1 and r1_16 < 8 then
      return false
    end
    if r16_0() == false then
      return false
    end
    if r19_0() == 0 then
      return false
    end
    local function r3_16()
      -- line: [265, 270] id: 17
      r24_0()
      r18_0()
      r20_0()
    end
    local function r4_16()
      -- line: [272, 275] id: 18
      r24_0()
    end
    local function r5_16()
      -- line: [277, 281] id: 19
      r24_0()
      r18_0()
    end
    local r6_16 = display.newGroup()
    local r7_16 = util.MakeGroup(r6_16)
    util.MakeFrame(r6_16)
    r25_0(r7_16, 280, {
      284
    }, {
      r3_16,
      r4_16,
      r5_16
    })
    return true
  end,
}
