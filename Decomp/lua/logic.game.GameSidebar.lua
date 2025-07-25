-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("logic.game.GameStatus")
local r1_0 = require("logic.char.CharStatus")
local r2_0 = require("logic.game.BaseGame")
local r3_0 = require("game.pause_manager")
local function r4_0(r0_1, r1_1, r2_1)
  -- line: [7, 35] id: 1
  if r3_0.GetPauseType() ~= r0_0.PauseTypeNone then
    return true
  end
  local r3_1 = r1_1.ms + r2_1
  r1_1.ms = r3_1
  if r3_1 < r1_1.es then
    return true
  end
  r1_1.ms = 0
  local r4_1 = nil
  local r5_1 = nil
  local r6_1 = r1_1.nr
  r4_1 = r6_1
  r6_1 = r6_1 + 1
  if r1_1.max < r6_1 then
    r6_1 = 1
  end
  r1_1.nr = r6_1
  if r6_1 == r4_1 then
    return true
  end
  if r1_1.tween then
    transition.cancel(r1_1.tween)
    r1_1.tween = nil
  end
  r1_1.tween = transition.dissolve(r1_1.spr[r4_1], r1_1.spr[r6_1], 1000)
  r1_1.es = 5000
  return true
end
local function r5_0(r0_2, r1_2)
  -- line: [37, 68] id: 2
  if r0_0.rtSide == nil or r0_0.rtSide.rtImg == nil then
    return 
  end
  local r2_2 = nil
  local r3_2 = nil
  r2_2 = display.newGroup()
  if r0_2 > 3 then
    if cdn.CheckFilelist() then
      util.LoadParts(r2_2, string.format("data/side/side_chara%02d.png", math.random(3)), 0, 0)
    else
      util.LoadParts(r2_2, string.format("data/side/side_chara%02d.png", r0_2), 0, 0)
    end
  else
    util.LoadParts(r2_2, string.format("data/side/side_chara%02d.png", r0_2), 0, 0)
  end
  if r1_2 ~= nil then
    util.LoadParts(r2_2, string.format("data/side/side_chara_lv%d.png", r1_2), 0, 544)
  end
  r0_0.rtSide.rtImg:insert(r2_2)
  r2_2:setReferencePoint(display.TopLeftReferencePoint)
  r2_2.x = -176
  r2_2.y = 0
  r2_2.isVisible = false
  return r2_2
end
return {
  make_sidebar = function()
    -- line: [70, 125] id: 3
    if r0_0.rtSide == nil or r0_0.rtSide.rtImg == nil or r0_0.rtSide.adsIconShowState == -1 or r0_0.rtSide.adsIconShowState == 1 then
      return 
    end
    local r0_3 = nil
    r0_3 = display.newRect(r0_0.rtSide.rtImg, 0, 0, 176, _G.Height)
    r0_3:setFillColor(0, 0, 0)
    r0_3:setReferencePoint(display.TopLeftReferencePoint)
    r0_3.x = -176
    r0_3.y = 0
    r0_0.SideBar = {}
    r0_0.SideBar.spr = {}
    r0_0.SideBar.ev = nil
    if r0_0.SpecialAchievement and type(r0_0.SpecialAchievement) == "table" then
      for r4_3, r5_3 in pairs(r0_0.SpecialAchievement) do
        r0_3 = nil
        if r5_3 == 1 then
          r0_3 = r5_0(r4_3, 3)
        elseif r5_3 == 2 then
          r0_3 = r5_0(r4_3, 4)
        end
        if r0_3 then
          table.insert(r0_0.SideBar.spr, r0_3)
        end
      end
    end
    if char.GetSummonPurchase(11) ~= 3 then
      table.insert(r0_0.SideBar.spr, r5_0(11, 4))
    end
    if char.GetSummonPurchase(12) ~= 3 then
      table.insert(r0_0.SideBar.spr, r5_0(12, 4))
    end
    if char.GetSummonPurchase(14) ~= 3 then
      table.insert(r0_0.SideBar.spr, r5_0(14, 4))
    end
    r0_0.SideBar.ms = 0
    r0_0.SideBar.es = 4000
    r0_0.SideBar.ev = events.Register(r4_0, r0_0.SideBar, 1)
    events.SetSkipFrame(r0_0.SideBar.ev, true)
    r0_0.SideBar.nr = 1
    r0_0.SideBar.max = #r0_0.SideBar.spr
    r0_0.SideBar.tween = nil
    if r0_0.SideBar.max > 0 then
      r0_0.SideBar.spr[1].isVisible = true
    end
  end,
}
