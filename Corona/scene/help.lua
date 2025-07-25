-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
module(..., package.seeall)
local r0_0 = nil
local r1_0 = nil
local r2_0 = nil
local r3_0 = nil
local r4_0 = nil
local r5_0 = nil
local function r6_0(r0_1)
  -- line: [12, 15] id: 1
  util.LoadTileBG(r0_1, db.LoadTileData("help", "base"), "data/help")
end
function MakeTextParts(r0_2, r1_2, r2_2, r3_2, r4_2)
  -- line: [18, 35] id: 2
  local r5_2 = display.newGroup()
  local r6_2 = nil
  local r7_2 = nil
  local r8_2 = nil
  r6_2 = util.LoadParts(r5_2, r0_2, 0, 0)
  r7_2 = r6_2.width
  r8_2 = r6_2.height
  if type(r4_2) == "number" then
    r4_2 = db.GetMessage(r4_2)
  end
  r6_2 = util.MakeText(r1_2, r2_2, r3_2, r4_2)
  r6_2:setReferencePoint(display.CenterReferencePoint)
  r6_2.x = r7_2 / 2
  r6_2.y = r8_2 / 2
  r5_2:insert(r6_2)
  return r5_2
end
function CallAddr()
  -- line: [37, 48] id: 3
  local r0_3 = require("common.device_info")
  sound.PlaySE(1)
  server.Mailto("defensewitches@stargarage.jp", {
    subject = "DefenseWitches Support（iOS）",
    body = "\r\nSupport ID: " .. tostring(math.floor(_G.UserInquiryID)) .. "\r\nVersion : " .. _G.Version .. "\r\niOS : " .. system.getInfo("platformName") .. system.getInfo("platformVersion") .. "\r\n" .. db.GetMessage(369) .. " : " .. r0_3.GetDeveiceName(system.getInfo("architectureInfo")),
  })
end
function QuitHelp()
  -- line: [50, 60] id: 4
  sound.PlaySE(1)
  if r2_0 then
    if package.loaded[r2_0].Cleanup then
      package.loaded[r2_0].Cleanup()
    end
    package.loaded[r2_0] = nil
    r2_0 = nil
  end
  util.ChangeScene({
    prev = Cleanup,
    scene = "menu",
    efx = "overFromTop",
  })
end
function ViewHelp(r0_5, r1_5)
  -- line: [62, 131] id: 5
  if r0_5 ~= "index" and cdn.CheckFilelist() == true then
    util.ChangeScene({
      scene = "cdn",
      efx = "fade",
      param = {
        next = "help",
        back = "help",
        scene = "help",
      },
    })
    return 
  end
  r3_0 = r1_0
  local r2_5 = "scene.parts.help_" .. r0_5
  if package.loaded[r2_5] then
    r1_0 = package.loaded[r2_5].Load(r4_0, r1_5)
    if r3_0 then
      display.remove(r3_0)
      r3_0 = nil
    end
  else
    r1_0 = require(r2_5).Load(r4_0, r1_5)
  end
  if r3_0 == nil then
    r2_0 = r2_5
    return 
  end
  r1_0.isVisible = false
  r1_0.alpha = 0
  local r3_5 = nil
  local r4_5 = nil
  r5_0.isVisible = true
  r4_5 = transit.Register(r3_0, {
    alpha = 0,
    time = 500,
    transition = easing.inExpo,
  })
  r3_5 = transit.Register(r1_0, {
    alpha = 1,
    time = 500,
    transition = easing.inExpo,
    onComplete = function(r0_6)
      -- line: [94, 116] id: 6
      if r2_0 then
        if package.loaded[r2_0].Cleanup then
          package.loaded[r2_0].Cleanup()
        end
        package.loaded[r2_0] = nil
      end
      r2_0 = r2_5
      display.remove(r3_0)
      r3_0 = nil
      r5_0.isVisible = false
      if r2_0 and package.loaded[r2_0].Complete then
        package.loaded[r2_0].Complete()
      end
      if r3_5 then
        transit.Delete(r3_5)
        r3_5 = nil
      end
      if r4_5 then
        transit.Delete(r4_5)
        r4_5 = nil
      end
    end,
  })
end
function new(r0_7)
  -- line: [133, 163] id: 7
  local r1_7 = "index"
  if r0_7 and r0_7.start then
    r1_7 = r0_7.start
  end
  events.SetNamespace("help")
  local r2_7 = display.newGroup()
  local r3_7 = util.MakeGroup(r2_7)
  r4_0 = util.MakeGroup(r2_7)
  r5_0 = util.MakeGroup(r2_7)
  util.MakeFrame(r2_7)
  local r4_7 = display.newRect(r5_0, 0, 0, _G.Width, _G.Height)
  r4_7:setFillColor(0, 0, 0)
  r4_7.alpha = 0.01
  r5_0.isVisible = false
  r5_0.touch = function(r0_8, r1_8)
    -- line: [148, 148] id: 8
    return true
  end
  r5_0:addEventListener("touch", r5_0)
  r6_0(r3_7)
  r2_0 = nil
  r1_0 = nil
  r3_0 = nil
  ViewHelp(r1_7)
  db.SaveToServer2(_G.UserToken)
  return r2_7
end
function GetInitRoot()
  -- line: [165, 172] id: 9
  events.SetNamespace("help")
  local r0_9 = display.newGroup()
  local r1_9 = util.MakeGroup(r0_9)
  r4_0 = util.MakeGroup(r0_9)
  r6_0(r1_9)
  return r0_9
end
function Cleanup()
  -- line: [174, 176] id: 10
  events.DeleteNamespace("help")
end
