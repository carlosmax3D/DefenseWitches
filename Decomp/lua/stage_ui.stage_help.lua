-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("common.base_dialog")
local r1_0 = 10
local r2_0 = "help_titile_evo%02d_"
local r3_0 = "help_evo_panel%02d_"
local r4_0 = nil
local r5_0 = nil
local r6_0 = nil
local r7_0 = nil
local r8_0 = nil
local r9_0 = nil
local r10_0 = nil
local r11_0 = nil
local r12_0 = nil
local function r13_0(r0_1)
  -- line: [42, 42] id: 1
  return "data/game/help/" .. r0_1 .. ".png"
end
local function r14_0(r0_2)
  -- line: [43, 43] id: 2
  return r13_0(r0_2 .. _G.UILanguage)
end
local function r15_0(r0_3)
  -- line: [44, 44] id: 3
  return "data/stage/" .. r0_3 .. ".png"
end
local function r16_0(r0_4)
  -- line: [45, 45] id: 4
  return r15_0(r0_4 .. _G.UILanguage)
end
local function r17_0(r0_5)
  -- line: [46, 46] id: 5
  return "data/help/witches/" .. r0_5 .. ".png"
end
local function r18_0(r0_6, r1_6)
  -- line: [51, 57] id: 6
  if r0_6 ~= nil and r0_6[r1_6] ~= nil then
    return true
  end
  return false
end
local function r19_0()
  -- line: [62, 71] id: 7
  if r12_0 ~= nil then
    r12_0 = nil
  end
  if r4_0 ~= nil then
    display.remove(r4_0)
    r4_0 = nil
  end
end
local function r20_0(r0_8)
  -- line: [77, 84] id: 8
  if r18_0(r0_8, "titleObj") == false then
    return 
  end
  display.remove(r0_8.titleObj)
  r0_8.titleObj = nil
end
local function r21_0(r0_9, r1_9)
  -- line: [90, 101] id: 9
  r20_0(r0_9)
  r0_9.titleObj = util.LoadParts(r0_9, r14_0(string.format(r2_0, r1_9)), 0, 0)
  r0_9:setReferencePoint(display.CenterReferencePoint)
  r0_9.x = _G.Width * 0.5
  r0_9.y = 47
end
local function r22_0(r0_10)
  -- line: [107, 114] id: 10
  if r18_0(r0_10, "contentsObj") == false then
    return 
  end
  display.remove(r0_10.contentsObj)
  r0_10.contentsObj = nil
end
local function r23_0(r0_11, r1_11)
  -- line: [120, 130] id: 11
  r22_0(r0_11)
  r0_11.contentsObj = util.LoadParts(r0_11, r14_0(string.format(r3_0, r1_11)), 0, 0)
  r0_11:setReferencePoint(display.CenterReferencePoint)
  r0_11.x = _G.Width * 0.5
  r0_11.y = _G.Height * 0.5 - 30
end
local function r24_0()
  -- line: [135, 138] id: 12
  r4_0.isVisible = true
  r0_0.SlideInLeftEffect(r4_0, r5_0)
end
local function r25_0()
  -- line: [143, 153] id: 13
  r0_0.SlideOutRightEffect(r4_0, r5_0, function()
    -- line: [144, 152] id: 14
    if r12_0 ~= nil then
      r12_0()
    end
    r19_0()
  end)
end
local function r26_0()
  -- line: [158, 161] id: 15
  sound.PlaySE(2)
  r25_0()
end
local function r27_0(r0_16)
  -- line: [166, 193] id: 16
  if r0_16 < 1 or r1_0 < r0_16 then
    return 
  end
  r21_0(r6_0, r0_16)
  r23_0(r7_0, r0_16)
  local r1_16 = r9_0[r0_16]
  local r2_16 = 2
  local r3_16 = nil
  for r7_16 = 1, #r9_0, 1 do
    if r7_16 ~= r0_16 then
      r8_0.mark[r2_16].x = r9_0[r7_16]
      r2_16 = r2_16 + 1
    end
  end
  r8_0.mark[1].x = r1_16
  r8_0.current = r0_16
end
local function r28_0(r0_17, r1_17)
  -- line: [198, 206] id: 17
  if r1_17 == true then
    r0_17.disable = false
    r0_17:setFillColor(255, 255, 255)
  else
    r0_17.disable = true
    r0_17:setFillColor(128, 128, 128)
  end
end
local function r29_0(r0_18)
  -- line: [211, 227] id: 18
  sound.PlaySE(1)
  local r1_18 = r8_0.current + r0_18.param
  r28_0(r11_0, true)
  r28_0(r10_0, true)
  if r1_18 <= 1 then
    r1_18 = 1
    r28_0(r11_0, false)
  end
  if r1_0 <= r1_18 then
    r1_18 = r1_0
    r28_0(r10_0, false)
  end
  r27_0(r1_18)
end
local function r30_0()
  -- line: [232, 289] id: 19
  r4_0 = display.newGroup()
  display.newRect(r4_0, 0, 0, _G.Width, _G.Height).alpha = 0
  r4_0:addEventListener("touch", function()
    -- line: [241, 243] id: 20
    return true
  end)
  r5_0 = display.newGroup()
  r4_0:insert(r5_0)
  util.LoadTileBG(r5_0, db.LoadTileData("help", "base"), "data/help")
  r6_0 = display.newGroup()
  display.newRect(r6_0, 0, 0, 1, 1)
  r5_0:insert(r6_0)
  r7_0 = display.newGroup()
  display.newRect(r7_0, 0, 0, 1, 1)
  r5_0:insert(r7_0)
  r8_0 = display.newGroup()
  r8_0.mark = {}
  table.insert(r8_0.mark, util.LoadParts(r8_0, r17_0("witches_page_active"), 0, 0))
  table.insert(r9_0, r8_0.mark[1].x)
  for r6_19 = 2, r1_0, 1 do
    table.insert(r8_0.mark, util.LoadParts(r8_0, r17_0("witches_page_nonactive"), r8_0.mark[r6_19 - 1].x + r8_0.mark[r6_19 - 1].width, 0))
    table.insert(r9_0, r8_0.mark[r6_19].x)
  end
  r8_0.current = 1
  r5_0:insert(r8_0)
  r8_0:setReferencePoint(display.CenterReferencePoint)
  r8_0.x = _G.Width * 0.5
  r8_0.y = 540
  util.LoadBtn({
    rtImg = r5_0,
    fname = r14_0("button_help_close_"),
    x = 260,
    y = 565,
    func = r26_0,
    param = nil,
  })
  r11_0 = util.LoadBtn({
    rtImg = r5_0,
    fname = r15_0("scrl_previous"),
    x = 455,
    y = 562,
    func = r29_0,
    param = -1,
  })
  r10_0 = util.LoadBtn({
    rtImg = r5_0,
    fname = r15_0("scrl_next"),
    x = 580,
    y = 562,
    func = r29_0,
    param = 1,
  })
end
local function r31_0()
  -- line: [294, 310] id: 21
  local r0_21 = nil	-- notice: implicit variable refs by block#[0]
  r4_0 = r0_21
  r5_0 = nil
  r6_0 = nil
  r7_0 = nil
  r8_0 = nil
  r0_21 = {}
  r9_0 = r0_21
  r10_0 = nil
  r0_21 = nil
  r11_0 = r0_21
end
return {
  Init = function()
    -- line: [318, 327] id: 22
    r31_0()
    r30_0()
    r27_0(1)
    r28_0(r11_0, false)
  end,
  Open = function(r0_23, r1_23)
    -- line: [332, 357] id: 23
    local r2_23 = nil	-- notice: implicit variable refs by block#[0]
    r12_0 = r2_23
    if r1_23 ~= nil then
      r12_0 = r1_23
    end
    r24_0()
    if r0_23 ~= nil then
      r28_0(r11_0, true)
      r28_0(r10_0, true)
      if r0_23 <= 1 then
        r0_23 = 1
        r28_0(r11_0, false)
      else
        r2_23 = r1_0
        if r2_23 <= r0_23 then
          r0_23 = r1_0
          r28_0(r10_0, false)
        end
      end
      r27_0(r0_23)
    end
  end,
}
