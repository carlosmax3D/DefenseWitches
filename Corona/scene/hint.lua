-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("tool.tutorial_manager")
local r1_0 = _G.VersionInfo
local r2_0 = _G.Copyright
local r3_0 = false
local r4_0 = nil
local r5_0 = {
  iPhone = "iphone",
  iPad = "ipad",
  ["iPhone Simulator"] = "iphone",
  ["iPad Simulator"] = "ipad",
}
local function r6_0(r0_1)
  -- line: [19, 19] id: 1
  return "data/info/" .. r0_1 .. ".png"
end
local function r7_0(r0_2)
  -- line: [21, 21] id: 2
  return "data/option/" .. r0_2 .. ".png"
end
local function r8_0(r0_3)
  -- line: [23, 23] id: 3
  return "data/hint/" .. r0_3 .. ".png"
end
local function r9_0(r0_4)
  -- line: [25, 25] id: 4
  return r8_0(r0_4 .. _G.UILanguage)
end
local function r10_0()
  -- line: [27, 33] id: 5
  local r0_5 = system.getInfo("model")
  if r0_5 == nil then
    r0_5 = "iPhone"
  end
  local r1_5 = r5_0[r0_5]
  if r1_5 == nil then
    r1_5 = r5_0.iPhone
  end
  return r1_5
end
local function r11_0(r0_6, r1_6)
  -- line: [36, 48] id: 6
  if r3_0 then
    native.cancelWebPopup()
    r3_0 = false
  end
  native.showWebPopup(0, 88, 960, 552, r0_6, {
    baseUrl = r1_6,
    hasBackground = false,
    autoCancel = false,
  })
  r3_0 = true
end
local function r12_0()
  -- line: [50, 62] id: 7
  r0_0.CloseBtnTutorial(false)
  if r3_0 then
    native.cancelWebPopup()
    r3_0 = false
  end
  if r4_0 then
    display.remove(r4_0)
    r4_0 = nil
  end
end
local function r13_0(r0_8)
  -- line: [64, 67] id: 8
  r12_0()
  util.ChangeScene({
    scene = "title",
    efx = "fade",
  })
end
local function r14_0(r0_9)
  -- line: [69, 72] id: 9
  r12_0()
  util.ChangeScene({
    scene = "game",
    efx = "fade",
  })
end
local function r15_0(r0_10)
  -- line: [74, 76] id: 10
  r12_0()
end
local function r16_0(r0_11, r1_11)
  -- line: [78, 104] id: 11
  local r2_11 = util.MakeGroup(r0_11)
  util.MakeFrame(r0_11)
  util.Debug(r1_11)
  local r3_11 = r13_0
  if r1_11.change_no == 0 then
    r3_11 = r15_0
  elseif r1_11.change_no == 1 then
    r3_11 = r14_0
  else
    r3_11 = r15_0
  end
  util.LoadParts(r2_11, r9_0("hint_header_"), 0, 0)
  util.LoadBtn({
    rtImg = r2_11,
    fname = r7_0("close"),
    x = 872,
    y = 0,
    func = r3_11,
  })
  r0_0.CloseBtnTutorial(true, r2_11)
  r0_11:insert(r2_11)
  r4_0 = r2_11
  return r0_11
end
local r17_0 = {}
local r18_0 = require("scene.data.hint_data")
function r17_0.new(r0_12)
  -- line: [110, 156] id: 12
  if _G.MapSelect > 1 and cdn.CheckFilelist({
    "hint"
  }) == true then
    return util.ChangeScene({
      scene = "cdn",
      efx = "fade",
      param = {
        next = "stage",
        back = "stage",
      },
    })
  end
  local r1_12 = display.newGroup()
  r16_0(r1_12, r0_12)
  r3_0 = false
  local r2_12 = r10_0()
  local r3_12 = r0_12.wno
  local r4_12 = r0_12.sno
  if r3_12 < 10 then
    r3_12 = "0" .. tostring(r3_12)
  else
    r3_12 = tostring(r3_12)
  end
  if r4_12 < 10 then
    r4_12 = "0" .. tostring(r4_12)
  else
    r4_12 = tostring(r4_12)
  end
  local r5_12 = "data/hint/" .. r3_12 .. "/html/" .. _G.UILanguage .. "/gallary" .. r4_12 .. ".html"
  local r6_12 = system.ResourceDirectory
  if _G.IsAndroid then
    r6_12 = "file:///android_asset/"
  end
  if tonumber(r3_12) >= 2 then
    r5_12 = "data/hint/" .. r3_12 .. "/html/" .. _G.UILanguage .. "/gallary" .. r4_12 .. ".html"
    r6_12 = system.DocumentsDirectory
  end
  r11_0(r5_12, r6_12)
  r18_0.SetHintReaded(_G.MapSelect, _G.StageSelect)
  return r1_12
end
function r17_0.Close()
  -- line: [158, 160] id: 13
  r12_0()
end
return r17_0
