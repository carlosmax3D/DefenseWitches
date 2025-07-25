-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("scene.help")
local r1_0 = nil
local r2_0 = 30000
local r3_0 = 1700
local r4_0 = 1242
local function r5_0(r0_1, r1_1)
  -- line: [12, 49] id: 1
  local r2_1 = r1_1.phase
  local r3_1 = r0_1.y
  if r2_1 == "began" then
    if r1_0 then
      transit.Delete(r1_0)
      r1_0 = nil
    end
    r0_1.start_y = r1_1.y
    display.getCurrentStage():setFocus(r0_1)
    if r0_1.y <= 96 then
      r0_1.is_scroll_up = true
    end
  elseif r2_1 == "moved" then
    local r4_1 = r0_1.start_y
    if r4_1 == nil then
      r4_1 = 0
    end
    local r6_1 = r3_1
    r3_1 = r3_1 + r1_1.y - r4_1
    if r0_1.is_scroll_up and r3_1 > 96 then
      r3_1 = r6_1
    elseif r3_1 > 608 then
      r3_1 = r6_1
    end
    if r3_1 < -r4_0 then
      r3_1 = -r4_0
    end
    r0_1.y = r3_1
    r0_1.start_y = r1_1.y
    if r3_1 <= 96 then
      r0_1.is_scroll_up = true
    end
  elseif r2_1 == "ended" or r2_1 == "cancelled" then
    local r5_1 = r2_0 * (r3_1 - -r4_0) / r3_0
    if r5_1 > 0 then
      r1_0 = transit.Register(r0_1, {
        time = r5_1,
        transition = easing.liner,
        y = -r4_0,
      })
    end
    display.getCurrentStage():setFocus(nil)
  end
end
local function r6_0(r0_2)
  -- line: [51, 55] id: 2
  sound.PlaySE(2)
  r0_0.ViewHelp("index")
  return true
end
local function r7_0(r0_3)
  -- line: [57, 91] id: 3
  local function r1_3(r0_4)
    -- line: [58, 58] id: 4
    return "data/credit/" .. r0_4 .. ".png"
  end
  local function r2_3(r0_5)
    -- line: [59, 59] id: 5
    return r1_3(r0_5 .. _G.UILanguage)
  end
  local function r3_3(r0_6)
    -- line: [60, 60] id: 6
    return "data/option/" .. r0_6 .. ".png"
  end
  local function r4_3(r0_7)
    -- line: [61, 61] id: 7
    return "data/stage/" .. r0_7 .. ".png"
  end
  local function r5_3(r0_8)
    -- line: [62, 62] id: 8
    return r4_3(r0_8 .. _G.UILanguage)
  end
  local r6_3 = display.newGroup()
  local r7_3 = util.MakeGroup(r6_3)
  local r8_3 = util.MakeGroup(r6_3)
  local r9_3 = util.MakeGroup(r6_3)
  local r10_3 = util.MakeGroup(r6_3)
  util.MakeFrame(r6_3)
  util.LoadBG(r7_3, r1_3("credits_base"))
  util.LoadParts(r9_3, r1_3("credits_mask_top"), 96, 0)
  util.LoadParts(r9_3, r1_3("credits_mask_bottom"), 96, 542)
  local r12_3 = util.LoadBtn({
    rtImg = r6_3,
    fname = r5_3("back_"),
    x = _G.Width * 0.5,
    y = 560,
    func = r6_0,
  })
  r12_3.x = r12_3.x - r12_3.width * 0.5
  local r13_3 = nil
  r13_3 = util.LoadParts(r8_3, r2_3("staffroll_"), 96, 608)
  r13_3.is_scroll_up = false
  r1_0 = transit.Register(r13_3, {
    time = r2_0,
    transition = easing.liner,
    y = -r4_0,
  })
  r13_3.touch = r5_0
  r13_3:addEventListener("touch", r13_3)
  return r6_3
end
return {
  new = r7_0,
  Load = function(r0_10, r1_10)
    -- line: [104, 110] id: 10
    local r2_10 = r7_0(r1_10)
    r0_10:insert(r2_10)
    return r2_10
  end,
  Cleanup = function()
    -- line: [93, 99] id: 9
    if r1_0 then
      transit.Delete(r1_0)
      r1_0 = nil
    end
    events.DeleteNamespace("credit")
  end,
}
