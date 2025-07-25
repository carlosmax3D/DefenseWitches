-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
module(..., package.seeall)
DebugPrint("-- logion_bonus_popup_dialog --")
require("login.login_bonus_item_data")
require("login.base_login_bonus_dialog")
local r0_0 = {
  isLoginBonusPopup = false,
  LB = function(r0_1)
    -- line: [19, 19] id: 1
    return "data/login_bonus/info/" .. r0_1 .. ".png"
  end,
  LB_L = function(r0_2)
    -- line: [20, 20] id: 2
    return r0_0.LB(r0_2 .. _G.UILanguage)
  end,
  O = function(r0_3)
    -- line: [22, 22] id: 3
    return "data/option/" .. r0_3 .. ".png"
  end,
  O_L = function(r0_4)
    -- line: [23, 23] id: 4
    return r0_0.O(r0_4 .. _G.UILanguage)
  end,
}
local r1_0 = "1"
local r2_0 = 300
local function r3_0()
  -- line: [31, 34] id: 5
  assert(r0_0, debug.traceback())
  return getmetatable(r0_0).__index
end
local function r4_0()
  -- line: [39, 43] id: 6
  r0_0.isLoginBonusPopup = false
  r3_0().close()
end
local function r5_0()
  -- line: [48, 53] id: 7
  sound.PlaySE(1)
  r4_0()
  require("login.login_bonus_item_receive_dialog").new().show()
end
local function r6_0()
  -- line: [58, 61] id: 8
  sound.PlaySE(2)
  r4_0()
end
local function r7_0(r0_9)
  -- line: [66, 100] id: 9
  r0_9:init()
  r0_9.loadGround(r0_0.LB("login_bonus_plate"))
  local r1_9 = r0_9.getDialogObj()
  r1_9:setReferencePoint(display.TopLeftReferencePoint)
  r1_9.x = _G.Width * 0.5 - r1_9.width * 0.5
  r1_9.y = _G.Height * 0.5 - r1_9.height * 0.5
  local r2_9 = util.LoadParts(r1_9, r0_0.LB_L("login_bonus_title_"), 0, 40)
  r2_9.x = r1_9.width * 0.5 - r2_9.width * 0.5
  local r3_9 = util.LoadBtn({
    rtImg = r1_9,
    fname = r0_0.LB_L("receive_"),
    x = r1_9.width * 0.5,
    y = 360,
    func = r5_0,
  })
  r3_9.x = r3_9.x - r3_9.width * 0.5
  local r4_9 = util.LoadBtn({
    rtImg = r1_9,
    fname = r0_0.O("close"),
    x = 0,
    y = -14,
    func = r6_0,
  })
  r4_9:setReferencePoint(display.TopLeftReferencePoint)
  r4_9.x = r1_9.width - r4_9.width + 15
  r4_9:setReferencePoint(display.CenterReferencePoint)
end
local function r8_0(r0_10)
  -- line: [105, 147] id: 10
  if r0_10 == nil or type(r0_10) ~= "table" or #r0_10 < 4 then
    return 
  end
  local r1_10 = nil
  local r2_10 = nil
  local r3_10 = nil
  local r4_10 = nil
  local r5_10 = nil
  r1_10 = r0_10[1]
  if type(r1_10) ~= "string" then
    r1_10 = tostring(r1_10)
  end
  r2_10 = r0_10[2]
  r3_10 = r0_10[3]
  r4_10 = r0_10[4]
  if r1_10 == nil or r2_10 == nil then
    return 
  end
  local r7_10 = {
    db.GetMessage(297)
  }
  local r8_10 = _G.LoginItems[tostring(r1_10)]
  if r8_10.itemType == _G.ItemTypeUnit and db.GetIsLockSummonData(_G.UserID, r8_10.charId) == 0 then
    r1_10 = r1_0
    r2_10 = r2_0
  end
  r0_0.setItemIcon(r1_10, r2_10)
  r0_0.setMessage(r7_10)
  r0_0.setReceiveTime(r3_10, r4_10)
end
function new(r0_11)
  -- line: [152, 197] id: 11
  if r0_0.isLoginBonusPopup == true then
    return nil
  end
  local r1_11 = login.base_login_bonus_dialog.base_login_bonus_dialog.new()
  setmetatable(r0_0, {
    __index = r1_11,
  })
  r7_0(r1_11)
  r0_0.show = function()
    -- line: [167, 189] id: 12
    r0_0.isLoginBonusPopup = true
    r1_11.show()
    local r0_12 = r1_11.getDialogObj()
    r0_12:scale(0, 0)
    r0_12:setReferencePoint(display.CenterReferencePoint)
    transition.to(r0_12, {
      time = 70,
      xScale = 1,
      yScale = 1,
    })
  end
  r8_0(r0_11)
  return r0_0
end
function r0_0.setItemIcon(r0_13, r1_13)
  -- line: [202, 205] id: 13
  login.login_bonus_item_data.getItemIcon(r0_0.getDialogObj(), 55, 130, r0_13, r1_13)
end
function r0_0.setMessage(r0_14)
  -- line: [210, 215] id: 14
  r3_0().setMessage(r0_0.getDialogObj(), 212, 125, r0_14)
end
function r0_0.setReceiveTime(r0_15, r1_15)
  -- line: [220, 241] id: 15
  local r2_15 = r0_0.getDialogObj()
  local r3_15 = r3_0()
  local r4_15 = nil
  if type(r0_15) == "string" then
    r0_15 = tonumber(r0_15)
  end
  if type(r1_15) == "string" then
    r1_15 = tonumber(r1_15)
  end
  if r0_15 >= 1 then
    r4_15 = string.format(db.GetMessage(295), r0_15)
  else
    r4_15 = string.format(db.GetMessage(296), r1_15)
  end
  r3_15.setMessage(r2_15, 100, 300, {
    r4_15
  })
end
