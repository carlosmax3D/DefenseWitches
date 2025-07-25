-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
module(..., package.seeall)
local function r0_0(r0_1)
  -- line: [9, 9] id: 1
  return "data/login_bonus/items/" .. r0_1 .. ".png"
end
local function r1_0(r0_2)
  -- line: [10, 10] id: 2
  return r0_0(r0_2 .. _G.UILanguage)
end
local r2_0 = 3
local r3_0 = 3
function getRewindMaxLimit()
  -- line: [21, 23] id: 3
  return r2_0
end
function getFlareMaxLimit()
  -- line: [28, 30] id: 4
  return r3_0
end
function getItem(r0_5)
  -- line: [35, 37] id: 5
  return _G.LoginItems[r0_5]
end
function getName(r0_6)
  -- line: [42, 55] id: 6
  return ({
    ["1"] = db.GetMessage(300),
    ["2"] = db.GetMessage(301),
    ["3"] = db.GetMessage(302),
    ["4"] = db.GetMessage(303),
    ["5"] = db.GetMessage(304),
    ["6"] = db.GetMessage(305),
    ["7"] = db.GetMessage(306),
    ["8"] = db.GetMessage(486),
    ["9"] = db.GetMessage(487),
  })[r0_6]
end
function getItemIcon(r0_7, r1_7, r2_7, r3_7, r4_7)
  -- line: [60, 120] id: 7
  local r5_7 = display.newGroup()
  r0_7:insert(r5_7)
  local r6_7 = getItem(r3_7)
  local r7_7 = util.LoadParts(r5_7, r0_0(r6_7.icon), r1_7, r2_7)
  DebugPrint(r7_7)
  local r8_7 = {
    "ticket_num_0",
    "ticket_num_1",
    "ticket_num_2",
    "ticket_num_3",
    "ticket_num_4",
    "ticket_num_5",
    "ticket_num_6",
    "ticket_num_7",
    "ticket_num_8",
    "ticket_num_9"
  }
  if tonumber(r3_7) == _G.LoginItems["1"].id then
    r4_7 = util.ConvertDisplayCrystal(tonumber(r4_7))
  end
  local r9_7 = nil
  if type(r4_7) ~= "string" then
    r9_7 = string.format("%d", r4_7)
  else
    r9_7 = r4_7
  end
  if r6_7.itemType == _G.ItemTypeUnit then
    return r7_7
  end
  local r10_7 = 2
  local r11_7 = r2_7 + 92
  local r12_7 = r1_7 + r7_7.width - r10_7
  for r16_7 = r9_7:len(), 1, -1 do
    local r18_7 = util.LoadParts(r5_7, r0_0(r8_7[tonumber(r9_7:sub(r16_7, r16_7)) + 1]), r12_7, r11_7)
    r12_7 = r12_7 - r18_7.width + r10_7
    r18_7.x = r12_7
  end
  if r6_7.itemType == _G.ItemTypeQuantity then
    local r13_7 = util.LoadParts(r5_7, r0_0("ticket_num_mark"), r12_7, r11_7)
    r13_7.x = r12_7 - r13_7.width + r10_7
  end
  return r5_7
end
