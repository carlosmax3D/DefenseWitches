-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
module(..., package.seeall)
require("login.login_bonus_item_data")
require("login.base_login_bonus_dialog")
local r0_0 = {}
local r1_0 = nil
function r0_0.MP(r0_1)
  -- line: [13, 13] id: 1
  return "data/map/" .. r0_1 .. ".png"
end
function r0_0.LBI(r0_2)
  -- line: [14, 14] id: 2
  return "data/login_bonus/info/" .. r0_2 .. ".png"
end
local function r2_0()
  -- line: [19, 22] id: 3
  assert(r0_0, debug.traceback())
  return getmetatable(r0_0).__index
end
local function r3_0()
  -- line: [27, 30] id: 4
  r2_0().close()
end
local function r4_0(r0_5)
  -- line: [35, 41] id: 5
  sound.PlaySE(1)
  r3_0()
  if r1_0 then
    r1_0()
  end
end
local function r5_0(r0_6, r1_6)
  -- line: [46, 73] id: 6
  r0_6:init()
  local r2_6 = _G.Width * 0.5
  local r3_6 = _G.Height * 0.5
  r0_6.loadGround(r0_0.LBI("login_bonus_plate"))
  local r4_6 = r0_6.getDialogObj()
  r4_6:setReferencePoint(display.CenterReferencePoint)
  r4_6.x = r2_6
  r4_6.y = r3_6
  if r1_6 then
    local r5_6 = util.LoadParts(r4_6, r1_6, 0, 40)
    r5_6.x = r4_6.width * 0.5 - r5_6.width * 0.5
  end
  local r5_6 = util.LoadBtn({
    rtImg = r4_6,
    fname = r0_0.MP("ok_en"),
    x = r4_6.width * 0.5,
    y = 360,
    func = r4_0,
  })
  r5_6.x = r5_6.x - r5_6.width * 0.5
end
local function r6_0(r0_7)
  -- line: [78, 117] id: 7
  if r0_7 == nil or type(r0_7) ~= "table" or r0_7.itemId == nil or r0_7.quantity == nil or r0_7.message == nil then
    return 
  end
  local r1_7 = nil
  local r2_7 = nil
  local r3_7 = nil
  local r4_7 = nil
  local r5_7 = nil
  r1_7 = r0_7.itemId
  if type(r1_7) ~= "string" then
    r1_7 = tostring(r1_7)
  end
  if r0_7.fontSize ~= nil then
    r4_7 = r0_7.fontSize
  end
  if r0_7.lineHeight ~= nil then
    r5_7 = r0_7.lineHeight
  end
  r2_7 = r0_7.quantity
  r3_7 = r0_7.message
  if r1_7 == nil or r2_7 == nil or r3_7 == nil then
    return 
  end
  local r6_7 = {
    r3_7
  }
  r0_0.setItemIcon(r1_7, r2_7)
  r0_0.setMessage(r6_7, r4_7, r5_7)
end
function new(r0_8)
  -- line: [122, 168] id: 8
  local r1_8 = login.base_login_bonus_dialog.base_login_bonus_dialog.new()
  setmetatable(r0_0, {
    __index = r1_8,
  })
  r1_0 = nil
  local r2_8 = nil
  if r0_8.titleLogoPath then
    r2_8 = r0_8.titleLogoPath
  end
  if r0_8.onOkButton then
    r1_0 = r0_8.onOkButton
  end
  r0_0.show = function()
    -- line: [140, 160] id: 9
    r1_8.show()
    local r0_9 = r1_8.getDialogObj()
    r0_9:scale(0, 0)
    transition.to(r0_9, {
      time = 70,
      xScale = 1,
      yScale = 1,
    })
  end
  r5_0(r1_8, r2_8)
  r6_0(r0_8)
  return r0_0
end
function r0_0.setItemIcon(r0_10, r1_10)
  -- line: [173, 176] id: 10
  login.login_bonus_item_data.getItemIcon(r0_0.getDialogObj(), 55, 130, r0_10, r1_10)
end
function r0_0.setMessage(r0_11, r1_11, r2_11)
  -- line: [181, 186] id: 11
  r2_0().setMessage(r0_0.getDialogObj(), 212, 125, r0_11, r1_11, r2_11)
end
