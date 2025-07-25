-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("tool.fbook")
local r1_0 = require("tool.twitter")
local r2_0 = require("logic.gamecenter")
local r3_0 = -40
local r4_0 = 64
local r5_0 = "data/complete"
local function r6_0(r0_1)
  -- line: [13, 13] id: 1
  return r5_0 .. "/" .. r0_1 .. ".png"
end
local function r7_0(r0_2)
  -- line: [14, 14] id: 2
  return "data/title/" .. r0_2 .. ".png"
end
local r8_0 = nil
local r9_0 = nil
local r10_0 = 0
local r11_0 = nil
local function r12_0(r0_3)
  -- line: [23, 29] id: 3
  sound.PlaySE(1)
  r0_0.Post(r0_3.param, "DefenseWitches", db.GetMessage(249), "", nil)
  return true
end
local function r13_0(r0_4)
  -- line: [31, 38] id: 4
  sound.PlaySE(1)
  r1_0.Post(r0_4.param, _G.UserID, db.GetMessage(249) .. " #dwitch", nil)
  return true
end
local function r14_0(r0_5)
  -- line: [40, 64] id: 5
  if r9_0 then
    for r4_5, r5_5 in pairs(r9_0) do
      r5_5.isVisible = true
    end
  end
  local r1_5 = _G.UserID
  local r2_5 = _G.UserToken
  if r1_5 == nil or r2_5 == nil then
    return 
  end
  local r3_5 = 36
  if db.SetGameCenterAchievement(r1_5, r3_5) then
    r2_0.UnlockAchievements(r3_5, 100)
    server.UnlockNormalAchievement(r2_5, 576, function(r0_6)
      -- line: [56, 61] id: 6
      if r0_6.isError then
        db.SetAchieveQueue("normal", 10, 10, 576)
      end
    end)
  end
end
local function r15_0()
  -- line: [66, 74] id: 7
  if r8_0 then
    for r3_7, r4_7 in pairs(r8_0) do
      if r4_7 then
        transit.Cancel(r4_7)
      end
    end
  end
  r10_0 = 1
  r14_0()
end
local function r16_0(r0_8, r1_8)
  -- line: [76, 89] id: 8
  if r1_8.phase == "ended" then
    if r10_0 == 0 then
      sound.PlaySE(1)
      r15_0()
    elseif r10_0 == 1 then
      sound.PlaySE(1)
      director:changeFxTime(1000)
      util.ChangeScene({
        prev = r11_0,
        scene = "endcut",
        efx = "fade",
      })
    end
  end
  return true
end
local function r17_0(r0_9, r1_9, r2_9)
  -- line: [91, 109] id: 9
  local r5_9 = r1_9 / r2_9
  if r5_9 <= 0.5 then
    r0_9.y = easing.inExpo(r5_9, 0.5, r3_0, 123 - r3_0)
  elseif r5_9 <= 0.75 then
    r0_9.y = easing.outExpo(r5_9 - 0.5, 0.25, 123, 123 - r4_0 - 123)
  elseif r5_9 < 1 then
    r0_9.y = easing.outExpo(r5_9 - 0.75, 0.25, 123 - r4_0, 123 - 123 - r4_0)
  else
    r0_9.y = 123
  end
end
local function r18_0(r0_10, r1_10, r2_10)
  -- line: [111, 131] id: 10
  local r5_10 = r1_10 / r2_10
  if r5_10 <= 0.5 then
    r0_10.x = easing.inExpo(r5_10, 0.5, _G.Width, 151 - _G.Width)
  elseif r5_10 <= 0.75 then
    r0_10.xScale = easing.outExpo(r5_10 - 0.5, 0.25, 1, -0.5)
  elseif r5_10 < 1 then
    r0_10.xScale = easing.outExpo(r5_10 - 0.75, 0.25, 0.5, 0.5)
  else
    r0_10.x = 151
    r0_10.xScale = 1
  end
end
function r11_0()
  -- line: [219, 227] id: 12
  if r8_0 then
    for r3_12, r4_12 in pairs(r8_0) do
      if r4_12 then
        transit.Delete(r4_12)
      end
    end
    r8_0 = nil
  end
  events.DeleteNamespace("complete")
end
return {
  new = function(r0_11)
    -- line: [133, 217] id: 11
    events.SetNamespace("complete")
    local r1_11 = display.newGroup()
    local r2_11 = util.MakeGroup(r1_11)
    local r3_11 = util.MakeGroup(r1_11)
    util.MakeFrame(r1_11)
    local r4_11 = nil
    local r5_11 = nil
    util.LoadTileBG(r2_11, db.LoadTileData("complete", "bg"), r5_0)
    r8_0 = {}
    local r6_11 = display.newGroup()
    util.LoadTileParts(r6_11, 44, 181, db.LoadTileData("complete", "shadow"), r5_0)
    util.LoadTileParts(r6_11, 90, 106, db.LoadTileData("complete", "chara01"), r5_0)
    util.LoadTileParts(r6_11, 0, 0, db.LoadTileData("complete", "chara02"), r5_0)
    r2_11:insert(r6_11)
    r6_11:setReferencePoint(display.TopLeftReferencePoint)
    r6_11.x = 17
    r6_11.y = 318
    r6_11.alpha = 0.001
    table.insert(r8_0, transit.Register(r6_11, {
      delay = 500,
      alpha = 1,
      time = 2000,
      transition = easing.linear,
    }))
    r4_11 = util.LoadTileParts(r2_11, 33, 0, db.LoadTileData("complete", "logo_deco"), r5_0)
    r4_11.alpha = 0.001
    table.insert(r8_0, transit.Register(r4_11, {
      delay = 3000,
      alpha = 1,
      time = 2000,
      transition = easing.linear,
    }))
    table.insert(r8_0, transit.Register(util.LoadTileParts(r2_11, 149, r3_0, db.LoadTileData("complete", "text01"), r5_0), {
      delay = 3000,
      time = 2000,
      transition = easing.linear,
      func = r17_0,
    }))
    r6_11 = display.newGroup()
    display.newRect(r6_11, 0, 0, 1320, 114):setFillColor(0, 0, 0, 1)
    util.LoadTileParts(r6_11, 660, 0, db.LoadTileData("complete", "logo"), r5_0)
    r2_11:insert(r6_11)
    r6_11:setReferencePoint(display.TopCenterReferencePoint)
    r6_11.x = _G.Width
    r6_11.y = 7
    table.insert(r8_0, transit.Register(r6_11, {
      delay = 3000,
      time = 2000,
      transition = easing.linear,
      func = r18_0,
    }))
    r4_11 = util.LoadTileParts(r2_11, 282, 588, db.LoadTileData("complete", "text02"), r5_0)
    r4_11:setReferencePoint(display.CenterReferencePoint)
    r4_11.alpha = 0.01
    table.insert(r8_0, transit.Register(r4_11, {
      delay = 5000,
      time = 1000,
      alpha = 1,
      transition = easing.outExpo,
      onComplete = r14_0,
    }))
    r9_0 = {}
    table.insert(r9_0, util.LoadBtn({
      rtImg = r3_11,
      fname = r7_0("title_facebook2"),
      x = 776,
      y = 208,
      func = r12_0,
      param = r3_11,
      show = false,
    }))
    table.insert(r9_0, util.LoadBtn({
      rtImg = r3_11,
      fname = r7_0("title_twitter2"),
      x = 840,
      y = 208,
      func = r13_0,
      param = r3_11,
      show = false,
    }))
    r10_0 = 0
    r2_11.touch = r16_0
    r2_11:addEventListener("touch")
    return r1_11
  end,
  Cleanup = r11_0,
}
