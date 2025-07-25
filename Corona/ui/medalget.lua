-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("tool.crystal")
local r1_0 = require("game.medal_manager")
local r2_0 = {
  "normal01_special",
  "normal02_gold",
  "normal03_silver",
  "normal04_bronze",
  "normal05_hp1",
  "normal06_ex1",
  "normal07_ex2",
  "normal08_ex3"
}
local r3_0 = nil
local r4_0 = nil
local r5_0 = nil
local r6_0 = nil
local r7_0 = nil
local r8_0 = nil
local function r9_0(r0_1)
  -- line: [27, 27] id: 1
  return "data/medalget/" .. r0_1 .. ".png"
end
local function r10_0(r0_2)
  -- line: [28, 28] id: 2
  return r9_0(r0_2 .. _G.UILanguage)
end
local function r11_0(r0_3)
  -- line: [29, 29] id: 3
  return "data/stage/" .. r0_3 .. ".png"
end
local function r12_0(r0_4)
  -- line: [30, 30] id: 4
  return "data/powerup/" .. r0_4 .. ".png"
end
local function r13_0(r0_5)
  -- line: [31, 31] id: 5
  return "data/medal_info/" .. r0_5 .. ".png"
end
local function r14_0()
  -- line: [33, 55] id: 6
  if r6_0 then
    transit.Delete(r6_0)
    r6_0 = nil
  end
  if r3_0 ~= nil then
    display.remove(r3_0)
    r3_0 = nil
  end
  if r5_0 then
    display.remove(r5_0)
    r5_0 = nil
  end
  if r4_0 then
    display.remove(r4_0)
    r4_0 = nil
  end
  if r7_0 then
    r7_0(r8_0)
  end
end
local function r15_0()
  -- line: [57, 60] id: 7
  r14_0()
  return true
end
local function r16_0(r0_8, r1_8)
  -- line: [62, 215] id: 8
  local r2_8 = display.newGroup()
  local r3_8 = util.LoadParts(r2_8, r9_0("medalget_plate"), 0, 0)
  local r4_8 = util.LoadParts(r2_8, r10_0("medalget_title_"), 0, 0)
  local r5_8 = r3_8.width * 0.5
  r4_8:setReferencePoint(display.TopCenterReferencePoint)
  r4_8.x = r5_8
  r4_8.y = r3_8.y + 50
  local r6_8 = display.newGroup()
  local r7_8 = 0
  local r8_8 = 0
  local r9_8 = 0
  local r10_8 = 0
  for r14_8, r15_8 in pairs(r1_8) do
    if r15_8 ~= nil and util.IsContainedTable(r15_8, "acquireFlag") == true and util.IsContainedTable(r15_8, "reward") == true and util.IsContainedTable(r15_8.reward, "type") == true and util.IsContainedTable(r15_8.reward, "value") == true and r15_8.acquireFlag == true and r14_8 <= #r2_0 then
      local r16_8 = display.newGroup()
      local r18_8 = util.LoadParts(r16_8, r11_0(r2_0[r14_8]), 0, 0)
      local r19_8 = 0
      local r20_8 = nil
      if r15_8.reward.type == r1_0.RewardType.Crystal then
        r20_8 = util.LoadParts(r16_8, r12_0("powerup_crystal1"), 0, 0)
        r19_8 = util.ConvertDisplayCrystal(r15_8.reward.value)
        r9_8 = r9_8 + r15_8.reward.value
      elseif r15_8.reward.type == r1_0.RewardType.Exp then
        r20_8 = util.LoadParts(r16_8, r13_0("medalinfo_icon_exp"), r18_8.x + r18_8.width, 0)
        r19_8 = r15_8.reward.value
        r10_8 = r10_8 + r15_8.reward.value
      end
      local r21_8 = r18_8.height * 0.5
      r20_8:setReferencePoint(display.CenterLeftReferencePoint)
      r20_8.x = r18_8.x + r18_8.width + 20
      r20_8.y = r21_8
      local r22_8 = util.MakeText3({
        rtImg = r16_8,
        size = 24,
        color = {
          255,
          238,
          204
        },
        shadow = {
          54,
          63,
          76
        },
        diff_x = 1,
        diff_y = 2,
        msg = string.format("+%d", r19_8),
      })
      r22_8:setReferencePoint(display.CenterLeftReferencePoint)
      r22_8.x = r20_8.x + r20_8.width
      r22_8.y = r21_8
      r6_8:insert(r16_8)
      r16_8.isVisible = true
      r16_8:setReferencePoint(display.TopLeftReferencePoint)
      r16_8.x = r7_8
      r16_8.y = r8_8
      r8_8 = r8_8 + 64
    end
    if r14_8 % 5 == 0 then
      r7_8 = r7_8 + 210
      r8_8 = 0
    end
  end
  local r11_8 = display.newGroup()
  local r12_8 = 390
  if r9_8 > 0 then
    r0_0.LocalUpdateCoin(r9_8)
    _G.metrics._crystal_bonus(r9_8)
    local r13_8 = display.newGroup()
    local r14_8 = util.LoadParts(r13_8, r12_0("powerup_crystal1"), 0, 0)
    local r15_8 = util.MakeText3({
      rtImg = r13_8,
      size = 24,
      color = {
        255,
        238,
        204
      },
      shadow = {
        54,
        63,
        76
      },
      diff_x = 1,
      diff_y = 2,
      msg = string.format("+%d", util.ConvertDisplayCrystal(r9_8)),
    })
    r15_8:setReferencePoint(display.CenterLeftReferencePoint)
    r15_8.x = r14_8.x + r14_8.width
    r15_8.y = r14_8.height * 0.5
    r11_8:insert(r13_8)
    r13_8:setReferencePoint(display.CenterLeftReferencePoint)
    r13_8.x = 0
    r13_8.y = r12_8
  end
  if r10_8 > 0 then
    ExpManager.AddExp(r10_8)
    ExpManager.SaveExp(nil)
    local r13_8 = display.newGroup()
    local r14_8 = util.LoadParts(r13_8, r13_0("medalinfo_icon_exp"), 0, 0)
    local r15_8 = util.MakeText3({
      rtImg = r13_8,
      size = 24,
      color = {
        255,
        238,
        204
      },
      shadow = {
        54,
        63,
        76
      },
      diff_x = 1,
      diff_y = 2,
      msg = string.format("+%d", r10_8),
    })
    r15_8:setReferencePoint(display.CenterLeftReferencePoint)
    r15_8.x = r14_8.x + r14_8.width
    r15_8.y = r14_8.height * 0.5
    r11_8:insert(r13_8)
    r13_8:setReferencePoint(display.CenterLeftReferencePoint)
    r13_8.y = r12_8
    if r11_8.numChildren > 1 then
      r13_8.x = r11_8[1].x + r11_8[1].width + 20
    end
  end
  r2_8:insert(r6_8)
  r2_8:insert(r11_8)
  r6_8:setReferencePoint(display.TopCenterReferencePoint)
  r6_8.x = r5_8
  r6_8.y = 95
  if r11_8.numChildren > 1 then
    r11_8:setReferencePoint(display.TopLeftReferencePoint)
    r11_8.x = 210
  else
    r11_8:setReferencePoint(display.TopCenterReferencePoint)
    r11_8.x = r5_8
  end
  r11_8.y = r3_8.height - 180
  local r13_8 = util.LoadBtn({
    rtImg = r2_8,
    fname = r9_0("ok"),
    x = 0,
    y = 0,
    func = r15_0,
  })
  r13_8:setReferencePoint(display.TopCenterReferencePoint)
  r13_8.x = r5_8
  r13_8.y = r3_8.height - r13_8.height
  r0_8:insert(r2_8)
  r2_8:setReferencePoint(display.CenterReferencePoint)
  r2_8.x = _G.Width * 0.5
  r2_8.y = _G.Height * 0.5
  return r2_8
end
local function r17_0(r0_9, r1_9)
  -- line: [217, 236] id: 9
  sound.PlaySE(28)
  r7_0 = nil
  if r1_9 then
    r7_0 = r1_9
  end
  r15_0()
  r3_0 = display.newGroup()
  display.newRect(r3_0, 0, 0, _G.Width, _G.Height).alpha = 0.01
  r3_0.touch = function()
    -- line: [230, 232] id: 10
    return true
  end
  r3_0:addEventListener("touch")
  r16_0(r3_0, r0_9)
end
return {
  ShowDialog = function(r0_11, r1_11)
    -- line: [238, 240] id: 11
    r17_0(r0_11, r1_11)
  end,
}
