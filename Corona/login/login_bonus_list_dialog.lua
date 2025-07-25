-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r16_0 = nil	-- notice: implicit variable refs by block#[0]
module(..., package.seeall)
local r0_0 = require("json")
require("login.login_bonus_item_data")
require("login.base_login_bonus_dialog")
require("login.login_ok_only_popup_dialog")
local r1_0 = {}
local r2_0 = 1
local r3_0 = nil
local r4_0 = {}
local r5_0 = 220
local r6_0 = 260
local r7_0 = 300
function r1_0.LB(r0_1)
  -- line: [26, 26] id: 1
  return "data/login_bonus/list/" .. r0_1 .. ".png"
end
function r1_0.LB_L(r0_2)
  -- line: [27, 27] id: 2
  return r1_0.LB(r0_2 .. _G.UILanguage)
end
function r1_0.O(r0_3)
  -- line: [29, 29] id: 3
  return "data/option/" .. r0_3 .. ".png"
end
function r1_0.O_L(r0_4)
  -- line: [30, 30] id: 4
  return r1_0.O(r0_4 .. _G.UILanguage)
end
function r1_0.S(r0_5)
  -- line: [32, 32] id: 5
  return "data/stage/" .. r0_5 .. ".png"
end
function r1_0.S_L(r0_6)
  -- line: [33, 33] id: 6
  return S(r0_6 .. _G.UILanguage)
end
local function r8_0()
  -- line: [38, 41] id: 7
  assert(r1_0, debug.traceback())
  return getmetatable(r1_0).__index
end
local function r9_0()
  -- line: [46, 58] id: 8
  if r3_0 then
    r3_0.clean()
    display.remove(r3_0)
  end
  if r4_0 then
    r4_0 = nil
  end
  r8_0().close()
end
local function r10_0(r0_9, r1_9, r2_9)
  -- line: [63, 108] id: 9
  local r4_9 = tonumber(r0_9)
  local r3_9 = nil	-- notice: implicit variable refs by block#[18]
  if r4_9 == _G.LoginItems["1"].id then
    r3_9 = string.format(db.GetMessage(327), util.ConvertDisplayCrystal(r1_9))
  elseif r4_9 == _G.LoginItems["2"].id then
    r3_9 = string.format(db.GetMessage(328), r1_9)
  elseif r4_9 == _G.LoginItems["3"].id then
    r3_9 = string.format(db.GetMessage(329), r1_9)
  elseif r4_9 == _G.LoginItems["4"].id then
    r3_9 = string.format(db.GetMessage(323), util.ConvertDisplayCrystal(r7_0))
  elseif r4_9 == _G.LoginItems["5"].id then
    r3_9 = string.format(db.GetMessage(324), util.ConvertDisplayCrystal(r7_0))
  elseif r4_9 == _G.LoginItems["6"].id then
    r3_9 = string.format(db.GetMessage(325), util.ConvertDisplayCrystal(r7_0))
  elseif r4_9 == _G.LoginItems["7"].id then
    r3_9 = string.format(db.GetMessage(326), util.ConvertDisplayCrystal(r7_0))
  elseif r4_9 == _G.LoginItems["8"].id then
    r3_9 = string.format(db.GetMessage(492), r1_9)
  elseif r4_9 == _G.LoginItems["9"].id then
    r3_9 = string.format(db.GetMessage(493), r1_9)
  end
  login.login_ok_only_popup_dialog.new({
    titleLogoPath = r1_0.LB_L("list_info_title_"),
    itemId = r0_9,
    quantity = r1_9,
    message = r3_9,
    onOkButton = r2_9,
  }).show()
end
local function r11_0()
  -- line: [113, 116] id: 10
  sound.PlaySE(2)
  r9_0()
end
local function r12_0()
  -- line: [121, 124] id: 11
  sound.PlaySE(1)
  r3_0.moveHome()
end
local function r13_0()
  -- line: [129, 132] id: 12
  sound.PlaySE(1)
  r3_0.movePrevious()
end
local function r14_0()
  -- line: [137, 140] id: 13
  sound.PlaySE(1)
  r3_0.moveNext()
end
local function r15_0()
  -- line: [145, 148] id: 14
  sound.PlaySE(1)
  r3_0.moveEnd()
end
function r16_0(r0_15)
  -- line: [153, 174] id: 15
  local r1_15 = r0_15.phase
  if r1_15 == "began" then
    local r2_15 = r0_15.target
    r2_15.call = r16_0
    local r3_15 = r3_0.setButton(r2_15)
  end
  if r1_15 == "ended" then
    local r2_15 = r0_15.target
    local r3_15 = _G.LoginItems[r2_15.itemId]
    DebugPrint(r2_15.itemId)
    DebugPrint(r2_15.quantity)
    r10_0(r2_15.itemId, r2_15.quantity)
  end
  return false
end
local function r17_0(r0_16)
  -- line: [179, 241] id: 16
  local r1_16 = r0_16.col
  if r1_16.index == nil then
    return 
  end
  local r2_16 = r4_0[r1_16.index]
  if r2_16 == nil then
    return 
  end
  local r3_16 = r5_0 * 0.5
  display.newRect(r1_16, 0, 0, r5_0, r6_0):setFillColor(0, 0, 0, 0)
  local r5_16 = util.LoadParts(r1_16, r1_0.LB("list_ticket_plate"), 0, 0)
  r5_16:setReferencePoint(display.CenterReferencePoint)
  r5_16.x = r3_16
  if r2_16.id and r2_16.quantity then
    local r6_16 = login.login_bonus_item_data.getItemIcon(r1_16, r5_16.x, r5_16.y, r2_16.id, r2_16.quantity)
    r6_16.x = r6_16.x - r6_16.width * 0.5
    r6_16.y = r6_16.y - r6_16.height * 0.5
    r6_16.itemId = r2_16.id
    r6_16.quantity = r2_16.quantity
    r6_16:addEventListener("touch", r16_0)
  end
  if r2_16.acquired == r2_0 then
    local r6_16 = util.LoadParts(r1_16, r1_0.LB("stamp"), 0, 0)
    r6_16:setReferencePoint(display.CenterReferencePoint)
    r6_16.x = r5_16.x
    r6_16.y = r5_16.y
  end
  local r6_16 = util.LoadParts(r1_16, r1_0.LB("list_text_plate"), 0, 0)
  r6_16:setReferencePoint(display.CenterReferencePoint)
  r6_16.x = r3_16
  r6_16.y = r5_16.height + 40
  local r7_16 = nil
  if r2_16.acquired == r2_0 then
    r7_16 = db.GetMessage(312)
  elseif r2_16.day then
    r7_16 = string.format(db.GetMessage(313), r2_16.day)
  end
  local r8_16 = display.newText(r1_16, r7_16, 0, 0, native.systemFont, 28)
  r8_16:setReferencePoint(display.CenterReferencePoint)
  r8_16.x = r3_16
  r8_16.y = r6_16.y - 1
end
local function r18_0(r0_17)
  -- line: [246, 333] id: 17
  r0_17:init()
  local r1_17 = _G.Width * 0.5
  local r2_17 = _G.Height * 0.5
  r0_17.loadGround(r1_0.LB("list_bg"))
  local r3_17 = r0_17.getDialogObj()
  r3_17:setReferencePoint(display.CenterReferencePoint)
  r3_17.x = r1_17
  r3_17.y = r2_17
  local r4_17 = util.LoadParts(r3_17, r1_0.LB_L("list_titile_"), 0, 40)
  r4_17:setReferencePoint(display.CenterReferencePoint)
  r4_17.x = r1_17
  local r6_17 = display.newText(r3_17, db.GetMessage(319), r1_17, 156, native.systemFontBold, 28)
  r6_17:setReferencePoint(display.CenterReferencePoint)
  r6_17.x = r1_17
  r6_17.y = 156
  r3_0 = require("common.gridView").new({
    grp = display.newGroup(),
    top = 210,
    left = r1_17 - r5_0 * 0.5,
    width = r5_0,
    height = r6_0,
    contentHeight = r6_0,
    onRender = r17_0,
  })
  r3_17:insert(r3_0.getScrollStage())
  local r8_17 = util.LoadBtn({
    rtImg = r3_17,
    fname = r1_0.S("scrl_home"),
    x = r1_17 - 370,
    y = 510,
    func = r12_0,
  })
  local r9_17 = util.LoadBtn({
    rtImg = r3_17,
    fname = r1_0.S("scrl_previous"),
    x = r1_17 - 210,
    y = 510,
    func = r13_0,
  })
  local r10_17 = util.LoadBtn({
    rtImg = r3_17,
    fname = r1_0.S("scrl_next"),
    x = r1_17 + 210,
    y = 510,
    func = r14_0,
  })
  r10_17.x = r10_17.x - r10_17.width
  local r11_17 = util.LoadBtn({
    rtImg = r3_17,
    fname = r1_0.S("scrl_end"),
    x = r1_17 + 370,
    y = 510,
    func = r15_0,
  })
  r11_17.x = r11_17.x - r11_17.width
  local r12_17 = util.LoadBtn({
    rtImg = r3_17,
    fname = r1_0.O("close"),
    x = 870,
    y = 7,
    func = r11_0,
  })
  local r13_17 = nil
  r13_17 = util.MakeFrame(r3_17, false)
end
local function r19_0(r0_18)
  -- line: [338, 395] id: 18
  if r0_18 == nil then
    return 
  end
  local r1_18 = nil
  local r2_18 = nil
  local r3_18 = nil
  local r4_18 = 0
  local r5_18 = 0
  local r6_18 = {}
  for r10_18, r11_18 in pairs(r0_18) do
    table.insert(r6_18, tonumber(r10_18))
  end
  table.sort(r6_18)
  for r10_18 = 1, #r6_18, 1 do
    r2_18 = r0_18[tostring(r6_18[r10_18])]
    local r11_18 = nil
    local r12_18 = nil
    local r13_18 = nil
    local r14_18 = nil
    local r15_18 = nil
    if r2_18 and type(r2_18) == "table" and 5 <= #r2_18 then
      r11_18 = r2_18[1]
      r12_18 = r2_18[2]
      r13_18 = r2_18[3]
      r14_18 = r2_18[4]
      r15_18 = r2_18[5]
      if r11_18 ~= r2_0 then
        r4_18 = r4_18 + 1
        if r5_18 == 0 then
          r5_18 = r10_18
        end
      end
    end
    if r11_18 ~= nil and r12_18 ~= nil and r13_18 ~= nil and r14_18 ~= nil and r15_18 ~= nil then
      r1_0.addItem(r4_18, r12_18, r13_18, r14_18, r15_18, r11_18)
    end
  end
  r3_0.moveAtNoAnimation(r5_18 - 1)
end
local function r20_0(r0_19)
  -- line: [400, 429] id: 19
  if r0_19.isError then
    native.showAlert("DefenseWitches", db.GetMessage(35), {
      "OK"
    })
    r9_0()
    return 
  end
  DebugPrint("receiveLoginBonusList")
  DebugPrint(r0_19.response)
  local r1_19 = r0_0.decode(r0_19.response)
  if r1_19 ~= nil then
    if r1_19.status == 1 and type(r1_19.result) == "table" then
      r19_0(r1_19.result)
    else
      native.showAlert("DefenseWitches", db.GetMessage(35), {
        "OK"
      })
      r9_0()
    end
  else
    native.showAlert("DefenseWitches", db.GetMessage(35), {
      "OK"
    })
    r9_0()
  end
end
function new(r0_20)
  -- line: [434, 443] id: 20
  local r1_20 = login.base_login_bonus_dialog.base_login_bonus_dialog.new()
  setmetatable(r1_0, {
    __index = r1_20,
  })
  r4_0 = {}
  r18_0(r1_20)
  return r1_0
end
function r1_0.addItem(r0_21, r1_21, r2_21, r3_21, r4_21, r5_21)
  -- line: [448, 468] id: 21
  if type(r1_21) ~= "string" then
    r1_21 = tostring(r1_21)
  end
  local r6_21 = login.login_bonus_item_data.getItem(r1_21)
  local r7_21 = #r4_0 + 1
  r4_0[r7_21] = {}
  r4_0[r7_21].day = r0_21
  r4_0[r7_21].id = r1_21
  r4_0[r7_21].quantity = r2_21
  r4_0[r7_21].acquired = r5_21
  r4_0[r7_21].timeHour = r3_21
  r4_0[r7_21].timeMinutes = r4_21
  local r8_21 = r3_0.add
  local r9_21 = {
    isCategory = false,
    rowHeight = 146,
  }
  r9_21.rowColor = {
    default = {
      255,
      128,
      128,
      0
    },
  }
  r8_21(r9_21)
end
function r1_0.show()
  -- line: [473, 477] id: 22
  r8_0().show()
  server.GetLoginBonusList(_G.UserInquiryID, r20_0)
end
