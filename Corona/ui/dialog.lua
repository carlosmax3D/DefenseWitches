-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("media")
local r1_0 = require("json")
local r2_0 = require("resource.char_define")
local r3_0 = require("ad.myads")
local r4_0 = require("tool.crystal")
local r5_0 = nil
local r6_0 = nil
local r7_0 = nil
local r8_0 = nil
local r9_0 = nil
local r10_0 = nil
local r11_0 = nil
local r12_0 = nil
local r13_0 = nil
local r14_0 = nil
local r15_0 = nil
local r16_0 = nil
local r17_0 = {
  "dialog_line",
  "dialog_window01",
  "dialog_window02",
  "dialog_window03",
  "dialog_window04",
  "dialog_window05",
  "dialog_window06",
  "dialog_window07",
  "dialog_window08",
  "dialog_window09"
}
local function r18_0(r0_1)
  -- line: [39, 39] id: 1
  return "data/dialog/" .. r0_1 .. ".png"
end
local function r19_0(r0_2)
  -- line: [41, 41] id: 2
  return r18_0(r0_2 .. _G.UILanguage)
end
local function r20_0(r0_3)
  -- line: [43, 43] id: 3
  return "data/option/" .. r0_3 .. ".png"
end
local function r21_0(r0_4)
  -- line: [45, 45] id: 4
  return r20_0(r0_4 .. _G.UILanguage)
end
local function r22_0(r0_5)
  -- line: [47, 47] id: 5
  return "data/game/confirm/" .. r0_5 .. "en.png"
end
local function r23_0(r0_6)
  -- line: [49, 49] id: 6
  return "data/game/confirm/" .. r0_6 .. _G.UILanguage .. ".png"
end
local function r24_0(r0_7)
  -- line: [51, 51] id: 7
  return "data/game/confirm/" .. r0_7 .. ".png"
end
local function r25_0(r0_8)
  -- line: [53, 53] id: 8
  return "data/title/" .. r0_8 .. ".png"
end
local function r26_0(r0_9)
  -- line: [55, 55] id: 9
  return r25_0(r0_9 .. _G.UILanguage)
end
local function r27_0(r0_10)
  -- line: [57, 57] id: 10
  return "data/hint/" .. r0_10 .. ".png"
end
local function r28_0(r0_11)
  -- line: [59, 59] id: 11
  return r27_0(r0_11 .. _G.UILanguage)
end
local function r29_0(r0_12)
  -- line: [61, 61] id: 12
  return "data/evo/rankup/" .. r0_12 .. ".png"
end
local function r30_0(r0_13)
  -- line: [63, 63] id: 13
  return r29_0(r0_13 .. _G.UILanguage)
end
local function r31_0(r0_14)
  -- line: [65, 65] id: 14
  return "data/ads" .. "/" .. r0_14 .. ".png"
end
local function r32_0(r0_15)
  -- line: [67, 67] id: 15
  return r31_0(r0_15 .. _G.UILanguage)
end
local function r33_0(r0_16)
  -- line: [69, 71] id: 16
  return "data/map/interface/" .. r0_16 .. ".png"
end
local function r34_0(r0_17)
  -- line: [73, 75] id: 17
  return r33_0(r0_17 .. _G.UILanguage)
end
local function r35_0(r0_18, r1_18, r2_18)
  -- line: [81, 103] id: 18
  local r3_18 = 0
  local r4_18 = {}
  local r5_18 = nil
  for r9_18, r10_18 in pairs(r1_18) do
    for r15_18, r16_18 in pairs(util.StringSplit(string.gsub(r10_18, "(\\n)", function(r0_19)
      -- line: [89, 89] id: 19
      return "\n"
    end), "\n")) do
      r5_18 = display.newText(r16_18, 0, 0, native.SystemFont, r2_18)
      if r3_18 < r5_18.width then
        r3_18 = r5_18.width
      end
      table.insert(r4_18, r5_18)
    end
  end
  return r3_18, r4_18
end
local function r36_0(r0_20, r1_20)
  -- line: [105, 119] id: 20
  local r2_20 = 0
  local r3_20 = nil
  local r4_20 = {}
  local r5_20 = nil
  for r9_20, r10_20 in pairs(r0_20) do
    r5_20 = display.newText(r10_20, 0, 0, native.SystemFont, r1_20)
    r3_20 = r5_20.width
    if r2_20 < r3_20 then
      r2_20 = r3_20
    end
    table.insert(r4_20, r5_20)
  end
  return r2_20, r4_20
end
local function r37_0(r0_21, r1_21, r2_21, r3_21, r4_21)
  -- line: [121, 134] id: 21
  local r5_21 = nil	-- notice: implicit variable refs by block#[3]
  if type(r1_21) == "string" then
    r5_21 = display.newText(r0_21, r1_21, 0, 0, native.systemFont, r4_21)
  else
    r5_21 = r1_21
    r0_21:insert(r1_21)
  end
  r5_21.x = r2_21 / 2
  r5_21.y = r3_21 + r5_21.height / 2
  r5_21:setFillColor(255, 255, 255)
  return r5_21
end
local function r38_0(r0_22, r1_22, r2_22, r3_22, r4_22)
  -- line: [139, 185] id: 22
  local r5_22 = math.ceil((r1_22 - 118) / 64)
  local r6_22 = math.ceil((r2_22 - 56 - 62) / 64)
  local r7_22 = r5_22 * 64 + 59
  local r8_22 = r6_22 * 64 + 56
  for r12_22 = 56, r8_22 - 63, 64 do
    for r16_22 = 59, r7_22 - 63, 64 do
      util.LoadParts(r0_22, r18_0("dialog_window05"), r16_22, r12_22)
    end
  end
  if r3_22 then
    for r12_22 = 59, r7_22 - 63, 64 do
      util.LoadParts(r0_22, r18_0("dialog_window02"), r12_22, 0)
    end
  end
  if r4_22 then
    for r12_22 = 56, r8_22 - 63, 64 do
      util.LoadParts(r0_22, r18_0("dialog_window04"), 2, r12_22)
      util.LoadParts(r0_22, r18_0("dialog_window06"), r7_22, r12_22)
    end
  end
  if r3_22 then
    for r12_22 = 59, r7_22 - 63, 64 do
      util.LoadParts(r0_22, r18_0("dialog_window08"), r12_22, r8_22)
    end
  end
  if r3_22 and r4_22 then
    util.LoadParts(r0_22, r18_0("dialog_window01"), 0, 0)
    util.LoadParts(r0_22, r18_0("dialog_window03"), r7_22, 0)
    util.LoadParts(r0_22, r18_0("dialog_window07"), 0, r8_22)
    util.LoadParts(r0_22, r18_0("dialog_window09"), r7_22, r8_22)
  end
end
local function r39_0(r0_23)
  -- line: [187, 194] id: 23
  if r0_23 ~= nil then
    local r1_23 = r1_0.decode(r0_23.response)
    if r1_23.status == 0 then
      r12_0 = r1_23.coin
    end
  end
end
local function r40_0()
  -- line: [196, 198] id: 24
  server.GetCoin(_G.UserToken, r39_0)
end
local function r41_0(r0_25)
  -- line: [203, 207] id: 25
  r4_0.Open(display.newGroup(), r0_25)
  return true
end
local function r42_0(r0_26, r1_26, r2_26)
  -- line: [209, 258] id: 26
  local r3_26 = display.newGroup()
  local r4_26 = db.GetMessage(r0_26)
  local r5_26 = {}
  for r9_26, r10_26 in pairs(r1_26) do
    if type(r10_26) == "string" then
      table.insert(r5_26, r10_26)
    else
      table.insert(r5_26, db.GetMessage(r10_26))
    end
  end
  local r6_26 = nil
  local r7_26 = nil
  r6_26, r7_26 = r36_0(r5_26, 24)
  r38_0(r3_26, 64 + r6_26 + 64, 128 + 40 * table.maxn(r5_26) + 62, true, true)
  local r11_26 = r3_26.width
  local r12_26 = r3_26.height
  r37_0(r3_26, r4_26, r11_26, 56, 32)
  for r16_26, r17_26 in pairs(r7_26) do
    r37_0(r3_26, r17_26, r11_26, 128 + 40 * (r16_26 - 1), 24)
  end
  local r13_26 = r11_26 / 2
  local r14_26 = r12_26 - 56 - 10
  util.LoadBtn({
    rtImg = r3_26,
    fname = r22_0("confirm_01"),
    x = r13_26 - 8 - 112,
    y = r14_26,
    func = r2_26[1],
    param = r2_26[3],
  })
  util.LoadBtn({
    rtImg = r3_26,
    fname = r23_0("confirm_02"),
    x = r13_26 + 8,
    y = r14_26,
    func = r2_26[2],
    param = r2_26[4],
  })
  return r3_26
end
local function r43_0(r0_27, r1_27, r2_27)
  -- line: [261, 318] id: 27
  local r3_27 = display.newGroup()
  local r4_27 = db.GetMessage(r0_27)
  local r5_27 = {}
  for r9_27, r10_27 in pairs(r1_27) do
    if type(r10_27) == "string" then
      table.insert(r5_27, r10_27)
    else
      table.insert(r5_27, db.GetMessage(r10_27))
    end
  end
  local r6_27 = nil
  local r7_27 = nil
  r6_27, r7_27 = r36_0(r5_27, 24)
  r38_0(r3_27, 64 + r6_27 + 64, 128 + 40 * table.maxn(r5_27) + 62, true, true)
  local r11_27 = r3_27.width
  local r12_27 = r3_27.height
  local r13_27 = r37_0(r3_27, r4_27, r11_27, 56, 32)
  local r14_27 = util.LoadParts(r3_27, "data/crystal/crystal.png", 0, 0)
  local r15_27 = util.LoadParts(r3_27, "data/crystal/crystal.png", 0, 0)
  r14_27.x = r13_27.x - r14_27.width - r13_27.width * 0.5
  r14_27.y = r13_27.y - r13_27.height * 0.5 - 12
  r15_27.x = r13_27.x + r13_27.width * 0.5
  r15_27.y = r13_27.y - r13_27.height * 0.5 - 12
  for r19_27, r20_27 in pairs(r7_27) do
    r37_0(r3_27, r20_27, r11_27, 128 + 40 * (r19_27 - 1), 24)
  end
  local r16_27 = r11_27 / 2
  local r17_27 = r12_27 - 56 - 10
  util.LoadBtn({
    rtImg = r3_27,
    fname = r22_0("confirm_01"),
    x = r16_27 - 8 - 112,
    y = r17_27,
    func = r2_27[1],
    param = r2_27[3],
  })
  util.LoadBtn({
    rtImg = r3_27,
    fname = r23_0("confirm_02"),
    x = r16_27 + 8,
    y = r17_27,
    func = r2_27[2],
    param = r2_27[4],
  })
  return r3_27
end
local function r44_0(r0_28, r1_28)
  -- line: [323, 391] id: 28
  local r2_28 = display.newGroup()
  local r3_28 = {}
  for r7_28, r8_28 in pairs(r0_28) do
    if type(r8_28) == "string" then
      table.insert(r3_28, r8_28)
    else
      table.insert(r3_28, db.GetMessage(r8_28))
    end
  end
  local r4_28 = nil
  local r5_28 = nil
  local r6_28 = 40
  r4_28, r5_28 = r35_0(r2_28, r3_28, 24)
  if #r5_28 > 3 then
    for r10_28, r11_28 in pairs(r5_28) do
      display.remove(r11_28)
    end
    r6_28 = 24
    r4_28, r5_28 = r35_0(r2_28, r3_28, 20)
  end
  local r7_28 = table.maxn(r3_28)
  local r10_28 = math.ceil((64 + r4_28 + 64 - 118) / 64)
  local r11_28 = math.ceil((128 + 40 * r7_28 + 62 - 56 - 62) / 64)
  local r12_28 = r10_28 * 64 + 59
  local r13_28 = r11_28 * 64 + 56
  local r14_28 = nil
  util.LoadParts(r2_28, r18_0("dialog_window01"), 0, 0)
  for r18_28 = 59, r12_28 - 63, 64 do
    util.LoadParts(r2_28, r18_0("dialog_window02"), r18_28, 0)
  end
  util.LoadParts(r2_28, r18_0("dialog_window03"), r12_28, 0)
  for r18_28 = 56, r13_28 - 63, 64 do
    util.LoadParts(r2_28, r18_0("dialog_window04"), 2, r18_28)
    for r22_28 = 59, r12_28 - 63, 64 do
      util.LoadParts(r2_28, r18_0("dialog_window05"), r22_28, r18_28)
    end
    util.LoadParts(r2_28, r18_0("dialog_window06"), r12_28, r18_28)
  end
  util.LoadParts(r2_28, r18_0("dialog_window07"), 0, r13_28)
  for r18_28 = 59, r12_28 - 63, 64 do
    util.LoadParts(r2_28, r18_0("dialog_window08"), r18_28, r13_28)
  end
  util.LoadParts(r2_28, r18_0("dialog_window09"), r12_28, r13_28)
  local r15_28 = r2_28.width
  local r16_28 = r2_28.height
  for r20_28, r21_28 in pairs(r5_28) do
    r37_0(r2_28, r21_28, r15_28, 60 + r6_28 * (r20_28 - 1), 24)
  end
  local r17_28 = r15_28 / 2
  local r19_28 = util.LoadBtn({
    rtImg = r2_28,
    fname = r24_0("button_ok"),
    x = r17_28,
    y = r16_28 - 56 - 10,
    func = r1_28[1],
    param = r1_28[3],
  })
  r19_28:setReferencePoint(display.CenterReferencePoint)
  r19_28.x = r17_28
  return r2_28
end
local function r45_0(r0_29, r1_29, r2_29)
  -- line: [396, 478] id: 29
  local r3_29 = display.newGroup()
  local r4_29 = db.GetMessage(r0_29)
  local r5_29 = {}
  for r9_29, r10_29 in pairs(r1_29) do
    if type(r10_29) == "table" then
      local r11_29 = nil
      if type(r10_29) == "string" then
        r11_29 = r10_29[1]
      else
        r11_29 = db.GetMessage(r10_29[1])
      end
      table.remove(r10_29, 1)
      table.insert(r5_29, string.format(r11_29, unpack(r10_29)))
    elseif type(r10_29) == "string" then
      table.insert(r5_29, r10_29)
    else
      table.insert(r5_29, db.GetMessage(r10_29))
    end
  end
  local r6_29 = nil
  local r7_29 = nil
  local r8_29 = 40
  r6_29, r7_29 = r35_0(r3_29, r5_29, 24)
  if #r7_29 > 3 then
    for r12_29, r13_29 in pairs(r7_29) do
      display.remove(r13_29)
    end
    r8_29 = 24
    r6_29, r7_29 = r35_0(r3_29, r5_29, 20)
  end
  local r9_29 = table.maxn(r5_29)
  local r12_29 = math.ceil((64 + r6_29 + 64 + 100 - 118) / 64)
  local r13_29 = math.ceil((128 + 40 * r9_29 + 62 + 50 - 56 - 62) / 64)
  local r14_29 = r12_29 * 64 + 59
  local r15_29 = r13_29 * 64 + 56
  local r16_29 = nil
  util.LoadParts(r3_29, r18_0("dialog_window01"), 0, 0)
  for r20_29 = 59, r14_29 - 63, 64 do
    util.LoadParts(r3_29, r18_0("dialog_window02"), r20_29, 0)
  end
  util.LoadParts(r3_29, r18_0("dialog_window03"), r14_29, 0)
  for r20_29 = 56, r15_29 - 63, 64 do
    util.LoadParts(r3_29, r18_0("dialog_window04"), 2, r20_29)
    for r24_29 = 59, r14_29 - 63, 64 do
      util.LoadParts(r3_29, r18_0("dialog_window05"), r24_29, r20_29)
    end
    util.LoadParts(r3_29, r18_0("dialog_window06"), r14_29, r20_29)
  end
  util.LoadParts(r3_29, r18_0("dialog_window07"), 0, r15_29)
  for r20_29 = 59, r14_29 - 63, 64 do
    util.LoadParts(r3_29, r18_0("dialog_window08"), r20_29, r15_29)
  end
  util.LoadParts(r3_29, r18_0("dialog_window09"), r14_29, r15_29)
  local r17_29 = r3_29.width
  local r18_29 = r3_29.height
  r37_0(r3_29, r4_29, r17_29, 56, 32)
  for r22_29, r23_29 in pairs(r7_29) do
    r37_0(r3_29, r23_29, r17_29, 128 + r8_29 * (r22_29 - 1), 24)
  end
  local r19_29 = r17_29 / 2
  local r21_29 = util.LoadBtn({
    rtImg = r3_29,
    fname = r24_0("button_ok"),
    x = r19_29,
    y = r18_29 - 56 - 10,
    func = r2_29[1],
    param = r2_29[3],
  })
  r21_29:setReferencePoint(display.CenterReferencePoint)
  r21_29.x = r19_29
  return r3_29
end
local function r46_0(r0_30, r1_30, r2_30, r3_30, r4_30)
  -- line: [483, 638] id: 30
  local r5_30 = display.newGroup()
  local r6_30 = {}
  for r10_30, r11_30 in pairs(r1_30) do
    if type(r11_30) == "string" then
      table.insert(r6_30, r11_30)
    else
      table.insert(r6_30, db.GetMessage(r11_30))
    end
  end
  local r7_30 = nil
  local r8_30 = nil
  local r9_30 = 40
  r7_30, r8_30 = r36_0(r6_30, 24)
  if #r8_30 > 3 then
    for r13_30, r14_30 in pairs(r8_30) do
      display.remove(r14_30)
    end
    r9_30 = 24
    r7_30, r8_30 = r35_0(r5_30, r6_30, 20)
  end
  local r10_30 = table.maxn(r6_30)
  local r11_30 = 64 + r7_30 + 64
  local r12_30 = 128 + 40 * r10_30 + 62
  if r3_30 and r11_30 < r3_30 then
    r11_30 = r3_30
  end
  if r4_30 and r12_30 < r4_30 then
    r12_30 = r4_30
  end
  local r13_30 = math.ceil((r11_30 - 118) / 64)
  local r14_30 = math.ceil((r12_30 - 56 - 62) / 64)
  local r15_30 = r13_30 * 64 + 59
  local r16_30 = r14_30 * 64 + 56
  r38_0(r5_30, r11_30, r12_30, true, true)
  local r17_30 = r5_30.width
  local r18_30 = r5_30.height
  local r19_30 = 56
  if r0_30 then
    for r23_30 = 59, r15_30 - 63, 64 do
      util.LoadParts(r5_30, r18_0("dialog_line"), r23_30, 104)
    end
    r37_0(r5_30, r0_30, r17_30, r19_30, 32)
    r19_30 = 128
  end
  for r23_30, r24_30 in pairs(r8_30) do
    r37_0(r5_30, r24_30, r17_30, r19_30 + r9_30 * (r23_30 - 1), 24)
  end
  local r20_30 = r17_30 / #r2_30
  local r21_30 = r18_30 - 56 - 10
  for r25_30, r26_30 in pairs(r2_30) do
    local r27_30 = r26_30.image
    local r28_30 = r26_30.func
    local r29_30 = r26_30.param
    local r30_30 = r20_30 * (r25_30 - 1)
    if r26_30.posX then
      r30_30 = r26_30.posX
    end
    local r31_30 = r21_30
    if r26_30.posY then
      r31_30 = r26_30.posY
    end
    DebugPrint(r25_30 .. "::" .. r30_30)
    local r32_30 = {
      rtImg = r5_30,
      fname = r27_30,
      x = r30_30,
      y = r31_30,
      param = r29_30,
    }
    if r28_30 then
      r32_30.func = r28_30
    end
    local r33_30 = util.LoadBtn(r32_30)
    if r26_30.enable ~= nil and r26_30.enable == false then
      r33_30:removeEventListener("touch")
    end
    if r26_30.inText then
      local r34_30 = r30_30
      local r35_30 = r31_30
      local r36_30 = {
        255,
        255,
        68
      }
      if r26_30.inTextPosX then
        r34_30 = r34_30 + r26_30.inTextPosX
      end
      if r26_30.inTextPosY then
        r35_30 = r35_30 + r26_30.inTextPosY
      end
      if r26_30.inTextColor then
        r36_30 = r26_30.inTextColor
      end
      local r37_30 = util.MakeText3({
        rtImg = r5_30,
        x = r34_30,
        y = r35_30,
        size = 28,
        color = r36_30,
        shadow = {
          0,
          0,
          0
        },
        diff_x = 1,
        diff_y = 1,
        msg = r26_30.inText,
      })
      local r38_30 = r33_30.touch
      function r33_30.touch(r0_31, r1_31)
        -- line: [613, 633] id: 31
        local r2_31 = r33_30.stageBounds
        if r1_31.phase == "began" then
          r37_30.xScale = 0.9
          r37_30.yScale = 0.9
        elseif r1_31.phase == "moved" and (r1_31.x < r2_31.xMin or r2_31.xMax < r1_31.x or r1_31.y < r2_31.yMin or r2_31.yMax < r1_31.y) then
          r37_30.xScale = 1
          r37_30.yScale = 1
        elseif r1_31.phase == "ended" or r1_31.phase == "cancelled" then
          r37_30.xScale = 1
          r37_30.yScale = 1
        end
        r38_30(r0_31, r1_31)
        return true
      end
      -- close: r34_30
    end
    -- close: r27_30
  end
  return r5_30
end
local function r47_0(r0_32)
  -- line: [643, 683] id: 32
  local r1_32 = display.newGroup()
  local r2_32 = 0
  local r3_32 = db.GetMessage(286)
  local r4_32 = nil
  local r5_32 = nil
  if r0_32 then
    if r0_32.need_crystal then
      r2_32 = r0_32.need_crystal
    end
    if r0_32.onOk then
      r4_32 = r0_32.onOk
    end
    if r0_32.onCancel then
      r5_32 = r0_32.onCancel
    end
  end
  dialog.Open(r1_32, 13, {
    string.format(r3_32, util.ConvertDisplayCrystal(r2_32)),
    287
  }, {
    function(r0_33)
      -- line: [664, 672] id: 33
      r5_0()
      sound.PlaySE(1)
      if r4_32 then
        r4_32(r0_33)
      end
      return true
    end,
    function(r0_34)
      -- line: [673, 681] id: 34
      r5_0()
      sound.PlaySE(2)
      if r5_32 then
        r5_32(r0_34)
      end
      return true
    end
  })
end
local function r48_0(r0_35, r1_35, r2_35)
  -- line: [687, 714] id: 35
  local r3_35 = display.newGroup()
  r0_35 = r0_35 + 59 + 59
  r1_35 = r1_35 + 56 + 62
  r38_0(r3_35, r0_35, r1_35, true, false)
  local r5_35 = r3_35.height
  local r6_35 = r3_35.width / 2
  local r7_35 = -10
  util.LoadBtn({
    rtImg = r3_35,
    fname = r22_0("confirm_01"),
    x = r6_35 - 8 - 112,
    y = r7_35,
    func = r2_35[1],
  })
  util.LoadBtn({
    rtImg = r3_35,
    fname = r23_0("confirm_02"),
    x = r6_35 + 8,
    y = r7_35,
    func = r2_35[2],
  })
  return r3_35
end
local function r49_0(r0_36, r1_36, r2_36, r3_36)
  -- line: [716, 871] id: 36
  local r4_36 = display.newGroup()
  r38_0(r4_36, 1024, 352, true, false)
  util.LoadParts(r4_36, r19_0("failed_title_"), 480, 48)
  util.LoadParts(r4_36, r20_0("option_line01"), 324, 96)
  util.LoadParts(r4_36, r20_0("option_line01"), 500, 96)
  if r3_0.GetLastRes() then
    util.LoadBtn({
      rtImg = r4_36,
      fname = r32_0("offerwall_02_"),
      x = 85,
      y = 40,
      func = function(r0_37)
        -- line: [729, 734] id: 37
        if not _G.IsSimulator then
          wallAds.show()
        end
        return true
      end,
    })
  end
  local r6_36 = util.LoadBtn({
    rtImg = r4_36,
    fname = r34_0("stage_chara_"),
    x = 75,
    y = 99,
    func = function(r0_38)
      -- line: [745, 750] id: 38
      sound.PlaySE(1)
      util.ChangeScene({
        prev = game.Cleanup,
        scene = "shop.shop_view",
        efx = "overFromBottom",
        param = {
          closeScene = "title",
        },
      })
    end,
  })
  if hint.CheckHintRelease(_G.MapSelect, _G.StageSelect) then
    util.LoadBtn({
      rtImg = r4_36,
      fname = r34_0("btn_manga_"),
      x = 309,
      y = 144,
      func = r3_36[2],
    })
  else
    util.LoadParts(r4_36, r34_0("btn_manga_"), 309, 144).alpha = 0.5
  end
  util.LoadBtn({
    rtImg = r4_36,
    fname = r34_0("btn_world_select_"),
    x = 309,
    y = 228,
    func = r3_36[3],
  })
  util.LoadBtn({
    rtImg = r4_36,
    fname = r34_0("btn_rewind_"),
    x = 542,
    y = 228,
    func = r3_36[4],
  })
  util.LoadBtn({
    rtImg = r4_36,
    fname = r34_0("btn_restart_"),
    x = 542,
    y = 144,
    func = r3_36[5],
  })
  local function r8_36(r0_39)
    -- line: [791, 812] id: 39
    if r12_0 < r1_36 then
      r47_0({
        need_crystal = r1_36,
        onOk = function(r0_40)
          -- line: [797, 799] id: 40
          r41_0(r7_0(r0_36, r1_36, r2_36, r3_36))
        end,
        onCancel = function(r0_41)
          -- line: [800, 802] id: 41
          r7_0(r0_36, r1_36, r2_36, r3_36)
        end,
      })
    else
      r3_36[6]()
    end
    return true
  end
  local r9_36 = {
    204,
    0,
    68
  }
  local r10_36 = "magic_restart_"
  if r2_36 then
    r10_36 = "magic_restart_disable_"
    r9_36 = {
      68,
      68,
      68
    }
    r8_36 = nil
  end
  local r11_36 = util.LoadBtn({
    rtImg = r4_36,
    fname = r19_0(r10_36),
    x = 783,
    y = 99,
    func = r8_36,
  })
  local r12_36 = util.MakeText3({
    rtImg = r4_36,
    x = 880,
    y = 280,
    size = 28,
    color = r9_36,
    shadow = {
      0,
      0,
      0
    },
    diff_x = 1,
    diff_y = 1,
    msg = string.format("%d", util.ConvertDisplayCrystal(r1_36)),
  })
  r12_36:setReferencePoint(display.TopCenterReferencePoint)
  if r2_36 == false then
    local r13_36 = r11_36.touch
    function r11_36.touch(r0_42, r1_42)
      -- line: [845, 865] id: 42
      local r2_42 = r11_36.stageBounds
      if r1_42.phase == "began" then
        r12_36.xScale = 0.9
        r12_36.yScale = 0.9
      elseif r1_42.phase == "moved" and (r1_42.x < r2_42.xMin or r2_42.xMax < r1_42.x or r1_42.y < r2_42.yMin or r2_42.yMax < r1_42.y) then
        r12_36.xScale = 1
        r12_36.yScale = 1
      elseif r1_42.phase == "ended" or r1_42.phase == "cancelled" then
        r12_36.xScale = 1
        r12_36.yScale = 1
      end
      r13_36(r0_42, r1_42)
      return true
    end
    -- close: r13_36
  else
    r11_36:removeEventListener("touch")
  end
  return r4_36
end
local function r50_0(r0_43, r1_43)
  -- line: [873, 900] id: 43
  if r8_0 == nil then
    return true
  end
  local r2_43 = r1_43.phase
  if r2_43 == "began" then
    if r14_0 then
      transit.Delete(r14_0)
      r14_0 = nil
    end
    sound.PlaySE(2)
    r14_0 = transit.Register(r8_0, {
      time = 500,
      transition = easing.outExpo,
      alpha = 0,
    })
  elseif r2_43 == "ended" or r2_43 == "canceled" then
    if r14_0 then
      transit.Delete(r14_0)
      r14_0 = nil
    end
    r14_0 = transit.Register(r8_0, {
      time = 500,
      transition = easing.outExpo,
      alpha = 1,
    })
  end
  return true
end
local function r51_0()
  -- line: [904, 918] id: 44
  local r0_44 = display.newGroup()
  local r1_44 = display.newGroup()
  local r2_44 = display.newRect(r0_44, 0, 0, _G.Width, _G.Height)
  r2_44:setFillColor(0, 0, 0)
  r2_44.alpha = 0.01
  function r2_44.touch(r0_45, r1_45)
    -- line: [911, 911] id: 45
    return true
  end
  r2_44:addEventListener("touch")
  require("scene.parts.help_pass").View(r1_44, function()
    -- line: [914, 917] id: 46
    r0_44:removeSelf()
    r1_44:removeSelf()
  end)
end
local function r52_0(r0_47, r1_47)
  -- line: [920, 975] id: 47
  if _G.UserToken == nil then
    server.NetworkError(35, nil)
    return 
  end
  local r2_47 = _G.UserID
  if _G.IsSimulator then
    if r1_47 ~= nil then
      db.UnlockSummonData(r2_47, r0_47)
      db.UnlockL4SummonData(r2_47, r0_47)
      kpi.Unlock(r0_47, r1_47[3])
      _G.metrics.crystal_buy_character(r0_47, r1_47[3])
      _G.BingoManager.updateUserBingoData(_G.BingoManagerClass.MISSION_TYPE.UNLOCK_CHAR(), nil)
      if r13_0 then
        r13_0:removeSelf()
      end
    end
    r51_0()
  else
    util.setActivityIndicator(true)
    local r3_47 = {}
    local r4_47 = {}
    if r1_47 ~= nil then
      table.insert(r3_47, r1_47[1])
      table.insert(r4_47, 1)
    end
    server.UseCoin(_G.UserToken, r3_47, r4_47, function(r0_48)
      -- line: [951, 973] id: 48
      util.setActivityIndicator(false)
      if server.CheckError(r0_48) then
        server.NetworkError()
      else
        local r1_48 = r1_0.decode(r0_48.response)
        if r1_48.status == 0 then
          db.UnlockSummonData(r2_47, r0_47)
          db.UnlockL4SummonData(r2_47, r0_47)
          kpi.Unlock(r0_47, r1_47[3])
          _G.metrics.crystal_buy_character(r0_47, r1_47[3])
          r51_0()
          _G.BingoManager.updateUserBingoData(_G.BingoManagerClass.MISSION_TYPE.UNLOCK_CHAR(), nil)
          if r13_0 then
            r13_0:removeSelf()
          end
        else
          server.ServerError(r1_48.status)
        end
      end
    end)
  end
end
local function r53_0()
  -- line: [977, 982] id: 49
  sound.PlaySE(1)
  util.ChangeScene({
    prev = game.Cleanup,
    scene = "shop.shop_view",
    efx = "overFromBottom",
    param = {
      closeScene = "map",
    },
  })
end
local function r54_0(r0_50)
  -- line: [984, 1039] id: 50
  local r1_50 = r0_50.param.detail
  local r2_50 = r0_50.param.id
  local r3_50 = r1_50[3]
  _G.metrics.stage_clear_ad_tap(_G.MapSelect, _G.StageSelect, r2_50)
  if r3_50 <= r12_0 then
    r6_0(display.newGroup(), 13, {
      string.format(db.GetMessage(14), util.ConvertDisplayCrystal(r3_50)),
      15
    }, {
      function(r0_51)
        -- line: [995, 1001] id: 51
        r5_0()
        sound.PlaySE(1)
        r52_0(r2_50, r1_50)
        return true
      end,
      function(r0_52)
        -- line: [1002, 1007] id: 52
        r5_0()
        sound.PlaySE(2)
        return true
      end
    })
  else
    r47_0({
      need_crystal = r1_50[3],
      onOk = function()
        -- line: [1012, 1014] id: 53
        r41_0({
          r40_0
        })
      end,
    })
  end
end
local function r55_0(r0_54)
  -- line: [1041, 1044] id: 54
  r0_0.playVideo(string.format("data/movie/char_%02d.m4v", r0_54), false, nonFunc)
end
local function r56_0(r0_55)
  -- line: [1046, 1050] id: 55
  require("tool.handle_scheme").DirectForward(server.GetMovieUrl(r0_55))
end
local function r57_0(r0_56)
  -- line: [1053, 1056] id: 56
  r55_0(r0_56.param)
end
local function r58_0(r0_57)
  -- line: [1058, 1058] id: 57
  return "data/shop/" .. r0_57 .. ".png"
end
local function r59_0(r0_58)
  -- line: [1060, 1060] id: 58
  return r58_0(r0_58 .. _G.UILanguage)
end
local function r60_0(r0_59)
  -- line: [1063, 1100] id: 59
  r13_0 = display.newGroup()
  local r2_59 = {}
  local r3_59 = nil
  local r4_59 = r2_0.CharInformation[r0_59]
  util.LoadParts(r13_0, r58_0("sale_banner_frame"), 40, 170)
  local r5_59 = string.format("sale_banner_%02d_", r0_59)
  local r6_59 = r54_0
  if r0_59 == r2_0.CharId.Yung then
    r6_59 = r53_0
  end
  util.LoadBtn({
    rtImg = r13_0,
    fname = r59_0(r5_59),
    x = 60,
    y = 180,
    func = r6_59,
    param = {
      detail = r4_59,
      id = r0_59,
    },
  })
  util.LoadBtn({
    rtImg = r13_0,
    fname = r59_0("movie_play_"),
    x = 100,
    y = 480,
    func = r57_0,
    param = r0_59,
  })
  r2_59.rtImg = r13_0
  r2_59.id = r0_59
  r2_59.itemid = r4_59[1]
  r2_59.coin = r4_59[3]
  return r2_59
end
local function r61_0(r0_60)
  -- line: [1103, 1147] id: 60
  local function r1_60(r0_61)
    -- line: [1107, 1112] id: 61
    sound.PlaySE(1)
    if not _G.IsSimulator then
    end
  end
  local function r2_60(r0_62)
    -- line: [1117, 1123] id: 62
    sound.PlaySE(1)
    if not _G.IsSimulator then
      wallAds.show()
    end
  end
  r13_0 = display.newGroup()
  if r0_60 == "careward" then
    util.LoadBtn({
      rtImg = r13_0,
      fname = r58_0("result_offerwall_02"),
      x = 60,
      y = 180,
      func = r1_60,
    })
  elseif r0_60 == "tapjoy" then
    util.LoadBtn({
      rtImg = r13_0,
      fname = r59_0("result_offerwall_03_"),
      x = 60,
      y = 180,
      func = r2_60,
    })
  end
  return r13_0
end
local function r62_0(r0_63)
  -- line: [1150, 1173] id: 63
  local function r1_63(r0_64)
    -- line: [1154, 1157] id: 64
    sound.PlaySE(1)
    util.ChangeScene({
      prev = game.Cleanup,
      scene = "invite",
      efx = "crossfade",
    })
  end
  r13_0 = display.newGroup()
  if r0_63 == "invite_friends" then
    util.LoadBtn({
      rtImg = r13_0,
      fname = r59_0("result_invite_"),
      x = 60,
      y = 180,
      func = r1_63,
    })
  end
  return r13_0
end
local function r63_0(r0_65, r1_65)
  -- line: [1175, 1375] id: 65
  r40_0()
  local r2_65 = display.newGroup()
  for r6_65 = 0, 960, 16 do
    util.LoadParts(r2_65, r18_0("clear_window01"), r6_65, 0)
    util.LoadParts(r2_65, r18_0("clear_window02"), r6_65, 128)
    util.LoadParts(r2_65, r18_0("clear_window03"), r6_65, 256)
    util.LoadParts(r2_65, r18_0("clear_window04"), r6_65, 384)
    util.LoadParts(r2_65, r18_0("clear_window05"), r6_65, 512)
  end
  util.LoadParts(r2_65, r18_0("clear_title"), 216, 40)
  for r6_65 = 96, 608, 256 do
    util.LoadParts(r2_65, r20_0("option_line01"), r6_65, 112)
  end
  for r6_65 = 352, 608, 256 do
    util.LoadParts(r2_65, r20_0("option_line01"), r6_65, 472)
  end
  for r6_65 = 352, 608, 256 do
    for r10_65 = 167, 407, 48 do
      util.LoadParts(r2_65, r20_0("option_line02"), r6_65, r10_65)
    end
  end
  local r3_65 = {}
  table.insert(r3_65, util.LoadBtn({
    rtImg = r2_65,
    fname = r34_0("btn_world_select3_"),
    x = 88,
    y = 544,
    func = r1_65[1],
    disable = true,
  }))
  table.insert(r3_65, util.LoadBtn({
    rtImg = r2_65,
    fname = r34_0("btn_stage_select_"),
    x = 648,
    y = 544,
    func = r1_65[2],
    disable = true,
  }))
  table.insert(r3_65, util.LoadBtn({
    rtImg = r2_65,
    fname = r25_0("title_facebook"),
    x = 336,
    y = 480,
    func = r1_65[4],
    disable = true,
  }))
  table.insert(r3_65, util.LoadBtn({
    rtImg = r2_65,
    fname = r25_0("title_twitter"),
    x = 488,
    y = 480,
    func = r1_65[5],
    disable = true,
  }))
  if r3_0.GetLastRes() then
    table.insert(r3_65, util.LoadBtn({
      rtImg = r2_65,
      fname = r32_0("offerwall_02_"),
      x = 630,
      y = 485,
      func = function(r0_66)
        -- line: [1246, 1251] id: 66
        if not _G.IsSimulator then
          wallAds.show()
        end
        return true
      end,
      disable = true,
    }))
  end
  util.MakeText3({
    rtImg = r2_65,
    x = 76,
    y = 118,
    size = 36,
    color = {
      255,
      234,
      127
    },
    shadow = {
      54,
      63,
      76
    },
    diff_x = 1,
    diff_y = 2,
    msg = string.format("STAGE %02d-%02d", _G.MapSelect, _G.StageSelect),
  })
  local function r7_65()
    -- line: [1288, 1320] id: 67
    if not _G.IsSimulator then
      interstitialAds.showInterstitialAds()
    end
    local r0_67 = db.LoadAdsProbabilityTable()
    if r0_67 ~= nil then
      local r1_67 = 0
      for r5_67, r6_67 in ipairs(r0_67) do
        r1_67 = r1_67 + r6_67.weight
      end
      if r1_67 > 0 then
        math.randomseed(os.time())
        local r2_67 = math.random(1, r1_67)
        local r3_67 = 0
        for r7_67, r8_67 in ipairs(r0_67) do
          r3_67 = r3_67 + r8_67.weight
          if r2_67 <= r3_67 then
            if r8_67.type == "character" then
              r2_65:insert(r60_0(tonumber(r8_67.tid)).rtImg)
              break
            elseif r8_67.type == "ads" then
              r2_65:insert(r61_0(r8_67.tid))
              break
            elseif r8_67.type == "local" then
              r2_65:insert(r62_0(r8_67.tid))
              break
            else
              break
            end
          end
        end
      end
    end
  end
  if r3_0.GetLastRes() then
    if _G.ServerStatus.invitation == 1 then
      r7_65()
    else
      server.GetStatus(function(r0_68)
        -- line: [1327, 1331] id: 68
        if _G.ServerStatus.invitation == 1 then
          r7_65()
        end
      end, nil)
    end
  end
  local r8_65 = {}
  local r9_65 = {}
  for r13_65, r14_65 in pairs(r0_65) do
    r9_65 = {
      msg = nil,
      spr = nil,
      score = r14_65,
      now = 0,
      ms = 0,
    }
    table.insert(r8_65, r9_65)
  end
  if not _G.IsSimulator then
  end
  local r10_65 = {
    rtImg = r2_65,
    struct = r8_65,
    index = 1,
    btn = r3_65,
    ev = nil,
    skip = false,
    sound = nil,
    total = {
      msg = nil,
      spr = nil,
      score = 0,
    },
    y = 128,
  }
  r2_65.struct = r10_65
  r2_65.touch = r1_65[7]
  r2_65:addEventListener("touch", r2_65)
  r10_65.ev = events.Register(r1_65[6], r10_65, 1, true)
  return r10_65
end
local function r64_0(r0_69)
  -- line: [1378, 1382] id: 69
  sound.PlaySE(2)
  r5_0()
  return true
end
local function r65_0(r0_70, r1_70)
  -- line: [1384, 1463] id: 70
  assert(r1_70 and type(r1_70) == "table", debug.traceback())
  local r2_70 = assert
  local r3_70 = r1_70[1]
  if r3_70 then
    r3_70 = type
    r3_70 = r3_70(r1_70[1] == "function")
  end
  r2_70(r3_70, debug.traceback())
  r2_70 = assert
  r3_70 = r1_70[2]
  if r3_70 then
    r3_70 = type
    r3_70 = r3_70(r1_70[2] == "function")
  end
  r2_70(r3_70, debug.traceback())
  r2_70 = display.newGroup()
  r3_70 = nil
  local r4_70 = nil
  util.LoadParts(r2_70, r18_0("dialog_window01"), 0, 0)
  r3_70 = 59
  for r8_70 = 1, 8, 1 do
    util.LoadParts(r2_70, r18_0("dialog_window02"), r3_70, 0)
    r3_70 = r3_70 + 64
  end
  util.LoadParts(r2_70, r18_0("dialog_window03"), r3_70, 0)
  r4_70 = 56
  for r8_70 = 1, 3, 1 do
    util.LoadParts(r2_70, r18_0("dialog_window04"), 2, r4_70)
    r3_70 = 59
    for r12_70 = 1, 8, 1 do
      util.LoadParts(r2_70, r18_0("dialog_window05"), r3_70, r4_70)
      r3_70 = r3_70 + 64
    end
    util.LoadParts(r2_70, r18_0("dialog_window06"), r3_70, r4_70)
    r4_70 = r4_70 + 64
  end
  util.LoadParts(r2_70, r18_0("dialog_window07"), 0, r4_70)
  r3_70 = 59
  for r8_70 = 1, 8, 1 do
    util.LoadParts(r2_70, r18_0("dialog_window08"), r3_70, r4_70)
    r3_70 = r3_70 + 64
  end
  util.LoadParts(r2_70, r18_0("dialog_window09"), r3_70, r4_70)
  r3_70 = 59
  for r8_70 = 1, 8, 1 do
    util.LoadParts(r2_70, r18_0("dialog_line"), r3_70, 158)
    r3_70 = r3_70 + 64
  end
  local r5_70 = display.newImage(r2_70, r26_0("gamecenter_"), true)
  r5_70:setReferencePoint(display.CenterReferencePoint)
  r5_70.x = 315
  r5_70.y = 91
  util.LoadBtn({
    rtImg = r2_70,
    fname = r26_0("leaderboards_"),
    cx = 204,
    cy = 218,
    func = r1_70[1],
  })
  util.LoadBtn({
    rtImg = r2_70,
    fname = r26_0("achievements_"),
    cx = 436,
    cy = 218,
    func = r1_70[2],
  })
  util.LoadBtn({
    rtImg = r2_70,
    fname = r20_0("close"),
    cx = 598,
    cy = 24,
    func = r64_0,
  })
  r0_70:insert(r2_70)
  r2_70:setReferencePoint(display.CenterReferencePoint)
  r2_70.x = _G.Width / 2
  r2_70.y = _G.Height / 2
  return r2_70
end
function r6_0(r0_71, r1_71, r2_71, r3_71)
  -- line: [1465, 1474] id: 71
  local r4_71 = nil	-- notice: implicit variable refs by block#[0]
  r15_0 = r4_71
  r5_0()
  r9_0 = util.MakeMat(r0_71)
  r8_0 = r42_0(r1_71, r2_71, r3_71)
  r0_71:insert(r8_0)
  r8_0:setReferencePoint(display.CenterReferencePoint)
  r8_0.x = _G.Width / 2
  r4_71 = r8_0
  r4_71.y = _G.Height / 2
end
function r5_0()
  -- line: [1476, 1492] id: 72
  if r14_0 then
    transit.Delete(r14_0)
    r14_0 = nil
  end
  if r9_0 then
    display.remove(r9_0)
    r9_0 = nil
  end
  if r8_0 then
    display.remove(r8_0)
    r8_0 = nil
  end
  if r15_0 then
    r15_0(r16_0)
  end
end
function r7_0(r0_73, r1_73, r2_73, r3_73)
  -- line: [1494, 1506] id: 73
  r40_0()
  r15_0 = nil
  r5_0()
  r9_0 = util.MakeMat(r0_73, r50_0)
  r8_0 = r49_0(r0_73, r1_73, r2_73, r3_73)
  r0_73:insert(r8_0)
  r8_0:setReferencePoint(display.TopLeftReferencePoint)
  r8_0.x = 0
  r8_0.y = _G.Height * 0.5 - r8_0.height * 0.5
end
local r70_0 = nil
function r70_0(r0_78, r1_78, r2_78, r3_78, r4_78)
  -- line: [1565, 1597] id: 78
  r40_0()
  local r5_78 = r4_78[1].func
  r4_78[1].func = function()
    -- line: [1569, 1587] id: 79
    if r12_0 < r3_78 then
      r47_0({
        need_crystal = r3_78,
        onOk = function(r0_80)
          -- line: [1575, 1577] id: 80
          r41_0(r70_0(r0_78, r1_78, r2_78, r3_78, r4_78))
        end,
        onCancel = function(r0_81)
          -- line: [1578, 1580] id: 81
          r70_0(r0_78, r1_78, r2_78, r3_78, r4_78)
        end,
      })
    else
      r5_78()
    end
  end
  r15_0 = nil
  r5_0()
  r9_0 = util.MakeMat(r0_78)
  r8_0 = r46_0(r1_78, r2_78, r4_78, 566, 0)
  r0_78:insert(r8_0)
  r8_0:setReferencePoint(display.CenterReferencePoint)
  r8_0.x = _G.Width / 2
  r8_0.y = _G.Height / 2
end
return {
  Open = r6_0,
  Close = r5_0,
  GameOver = r7_0,
  OpenCrystal = function(r0_74, r1_74, r2_74, r3_74)
    -- line: [1508, 1517] id: 74
    local r4_74 = nil	-- notice: implicit variable refs by block#[0]
    r15_0 = r4_74
    r5_0()
    r9_0 = util.MakeMat(r0_74)
    r8_0 = r43_0(r1_74, r2_74, r3_74)
    r0_74:insert(r8_0)
    r8_0:setReferencePoint(display.CenterReferencePoint)
    r8_0.x = _G.Width / 2
    r4_74 = r8_0
    r4_74.y = _G.Height / 2
  end,
  OpenOkButtonDialog = function(r0_75, r1_75, r2_75)
    -- line: [1522, 1531] id: 75
    local r3_75 = nil	-- notice: implicit variable refs by block#[0]
    r15_0 = r3_75
    r5_0()
    r9_0 = util.MakeMat(r0_75)
    r8_0 = r44_0(r1_75, r2_75)
    r0_75:insert(r8_0)
    r8_0:setReferencePoint(display.CenterReferencePoint)
    r8_0.x = _G.Width / 2
    r3_75 = r8_0
    r3_75.y = _G.Height / 2
  end,
  OpenDialog = function(r0_76, r1_76, r2_76, r3_76)
    -- line: [1536, 1545] id: 76
    local r4_76 = nil	-- notice: implicit variable refs by block#[0]
    r15_0 = r4_76
    r5_0()
    r9_0 = util.MakeMat(r0_76)
    r8_0 = r46_0(r1_76, r2_76, r3_76)
    r0_76:insert(r8_0)
    r8_0:setReferencePoint(display.CenterReferencePoint)
    r8_0.x = _G.Width / 2
    r4_76 = r8_0
    r4_76.y = _G.Height / 2
  end,
  OpenRestartDialog = r70_0,
  GetDialog = function()
    -- line: [1602, 1604] id: 82
    return r8_0
  end,
  SetCloseFunc = function(r0_83, r1_83)
    -- line: [1606, 1609] id: 83
    r15_0 = r0_83
    r16_0 = r1_83
  end,
  GameClear = function(r0_84, r1_84, r2_84)
    -- line: [1611, 1624] id: 84
    local r3_84 = nil	-- notice: implicit variable refs by block#[0]
    r15_0 = r3_84
    r5_0()
    r11_0 = util.MakeMat(r0_84)
    r3_84 = r63_0(r1_84, r2_84)
    r10_0 = r3_84.rtImg
    r0_84:insert(r10_0)
    r10_0:setReferencePoint(display.TopLeftReferencePoint)
    r10_0.x = 0
    r10_0.y = 0
    return r3_84
  end,
  OpenPackDialog = function(r0_77, r1_77, r2_77, r3_77)
    -- line: [1550, 1559] id: 77
    local r4_77 = nil	-- notice: implicit variable refs by block#[0]
    r15_0 = r4_77
    r5_0()
    r9_0 = util.MakeMat(r0_77)
    r8_0 = r45_0(r1_77, r2_77, r3_77)
    r0_77:insert(r8_0)
    r8_0:setReferencePoint(display.CenterReferencePoint)
    r8_0.x = _G.Width / 2
    r4_77 = r8_0
    r4_77.y = _G.Height / 2
  end,
  Make = function(r0_85, r1_85, r2_85, r3_85)
    -- line: [1626, 1637] id: 85
    local r4_85 = nil	-- notice: implicit variable refs by block#[0]
    r15_0 = r4_85
    r5_0()
    r9_0 = util.MakeMat(r0_85)
    r8_0 = r48_0(r1_85, r2_85, r3_85)
    r0_85:insert(r8_0)
    r8_0:setReferencePoint(display.CenterReferencePoint)
    r8_0.x = _G.Width / 2
    r8_0.y = _G.Height / 2
    r4_85 = r8_0
    return r4_85
  end,
  OpenGC = function(r0_86, r1_86)
    -- line: [1639, 1644] id: 86
    local r2_86 = nil	-- notice: implicit variable refs by block#[0]
    r15_0 = r2_86
    r5_0()
    r9_0 = util.MakeMat(r0_86)
    r2_86 = r65_0(r0_86, r1_86)
    r8_0 = r2_86
  end,
  Init = function()
    -- line: [1646, 1649] id: 87
    local r0_87 = nil	-- notice: implicit variable refs by block#[0]
    r15_0 = r0_87
    preload.Load(r17_0, "data/dialog")
  end,
  Cleanup = function()
    -- line: [1651, 1653] id: 88
    r5_0()
  end,
}
