-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("scene.help")
local r1_0 = "data/help/story"
local r2_0 = 60000
local r3_0 = nil
local r4_0 = nil
local function r5_0(r0_1)
  -- line: [12, 12] id: 1
  return r1_0 .. "/" .. r0_1 .. ".png"
end
local function r6_0(r0_2)
  -- line: [13, 13] id: 2
  return r5_0(r0_2 .. _G.UILanguage)
end
local function r7_0(r0_3)
  -- line: [14, 14] id: 3
  return r1_0 .. "/" .. r0_3 .. ".jpg"
end
local function r8_0(r0_4)
  -- line: [15, 15] id: 4
  return r7_0(r0_4 .. _G.UILanguage)
end
local function r9_0(r0_5)
  -- line: [16, 16] id: 5
  return "data/stage/" .. r0_5 .. ".png"
end
local function r10_0(r0_6)
  -- line: [17, 17] id: 6
  return r9_0(r0_6 .. _G.UILanguage)
end
local function r11_0(r0_7, r1_7)
  -- line: [19, 60] id: 7
  local r2_7 = r1_7.phase
  local r3_7 = r0_7.y
  local r4_7 = r0_7
  if r2_7 == "began" then
    if r3_0 then
      transit.Delete(r3_0)
      r3_0 = nil
    end
    r4_7.start_y = r1_7.y
    display.getCurrentStage():setFocus(r0_7)
    if r4_7.y <= 88 then
      r4_7.is_scroll_up = true
    end
  elseif r2_7 == "moved" then
    local r5_7 = r4_7.start_y
    if r5_7 == nil then
      r5_7 = 0
    end
    local r7_7 = r3_7
    r3_7 = r3_7 + r1_7.y - r5_7
    if r4_7.is_scroll_up and r3_7 > 88 then
      r3_7 = r7_7
    elseif r3_7 > 504 then
      r3_7 = r7_7
    end
    if r3_7 < -840 then
      r3_7 = -840
    end
    r4_7.y = r3_7
    r4_7.start_y = r1_7.y
    if r3_7 <= 88 then
      r4_7.is_scroll_up = true
    end
  elseif r2_7 == "ended" or r2_7 == "cancelled" then
    local r6_7 = r2_0 * (r3_7 - -840) / 1392
    if r6_7 > 0 then
      if r3_0 then
        transit.Delete(r3_0)
      end
      r3_0 = transit.Register(r4_7, {
        time = r6_7,
        transition = easing.liner,
        y = -840,
      })
    end
    display.getCurrentStage():setFocus(nil)
  end
  return true
end
local function r12_0(r0_8)
  -- line: [62, 66] id: 8
  sound.PlaySE(2)
  r0_0.ViewHelp("index")
  return true
end
return {
  Load = function(r0_9, r1_9)
    -- line: [68, 114] id: 9
    local r2_9 = nil	-- notice: implicit variable refs by block#[0]
    r3_0 = r2_9
    r2_9 = display.newGroup()
    local r3_9 = nil
    local r4_9 = nil
    util.LoadTileBG(r2_9, db.LoadTileData("story", "bg"), r1_0)
    r4_0 = display.newGroup()
    r4_0.left = 88
    r4_0.top = 504
    r4_0.right = 872
    r4_0.bottom = 1896
    r4_0.x = 88
    r4_0.y = 504
    r2_9:insert(r4_0)
    util.LoadParts(r4_0, r8_0("main_"), 0, 0):setMask(cdn.NewMask(r8_0("mask_")))
    r3_9 = display.newRect(r4_0, 0, 0, 784, 1392)
    r3_9:setFillColor(0, 0, 0)
    r3_9.alpha = 0.01
    util.LoadParts(r2_9, r5_0("mask_top"), 88, 0)
    util.LoadParts(r2_9, r5_0("mask_bottom"), 88, 520)
    util.LoadParts(r2_9, r6_0("title_"), 192, 16)
    util.LoadBtn({
      rtImg = r2_9,
      fname = r10_0("back_"),
      x = 384,
      y = 560,
      func = r12_0,
    })
    r3_0 = transit.Register(r4_0, {
      time = r2_0,
      transition = easing.liner,
      y = -840,
    })
    r4_0.touch = r11_0
    r4_0:addEventListener("touch", r4_0)
    r0_9:insert(r2_9)
    return r2_9
  end,
  Cleanup = function()
    -- line: [116, 121] id: 10
    if r3_0 then
      transit.Delete(r3_0)
      r3_0 = nil
    end
  end,
}
