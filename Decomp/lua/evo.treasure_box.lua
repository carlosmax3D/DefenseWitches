-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("common.bound")
local r1_0 = require("evo.treasure_box_manager")
local r2_0 = require("common.sprite_loader").new({
  imageSheet = "common.sprites.sprite_twinkle01",
})
local r3_0 = 645
local r4_0 = 20
local function r5_0(r0_1, r1_1, r2_1)
  -- line: [18, 25] id: 1
  local r3_1 = anime.GetTimer(r0_1.anm)
  if 3 <= r3_1 and r3_1 <= 11 or 16 <= r3_1 and r3_1 <= 25 then
    return {
      r1_1,
      r2_1
    }
  else
    return {
      0,
      0
    }
  end
end
local function r6_0(r0_2, r1_2, r2_2)
  -- line: [31, 73] id: 2
  if r1_2.bound.isOneBoundFinish() == true then
    if r1_2.bound ~= nil then
      r1_2.bound.hideImage()
      r1_2.spr.isVisible = true
    end
    if r1_2.bound.isFrameCountFinish() == true then
      r1_2.ms = r1_2.ms + r2_2
      local r4_2 = util.CalcQuadraticBezPoint({
        x = r1_2.sx,
        y = r1_2.sy,
      }, {
        x = r1_2.cpoint.x,
        y = r1_2.cpoint.y,
      }, {
        x = r3_0,
        y = r4_0,
      }, r1_2.ms / 2000)
      r1_2.spr.x = r4_2.x
      r1_2.spr.y = r4_2.y
      if r1_2.ms >= 2000 then
        display.remove(r1_2.spr)
        events.Delete(r1_2.ev)
        r1_2.ev = nil
        if r1_2.endListener ~= nil then
          r1_2.endListener()
        end
        return false
      end
    end
  else
    r1_2.sx = r1_2.bound.x + 12
    r1_2.sy = r1_2.bound.y + 16
    r1_2.spr.x = r1_2.bound.x + 12
    r1_2.spr.y = r1_2.bound.y + 16
  end
  return true
end
local function r7_0(r0_3, r1_3, r2_3, r3_3)
  -- line: [78, 116] id: 3
  local r4_3 = r1_3.x - r0_3.x
  local r5_3 = r1_3.y - r0_3.y
  local r6_3 = math.sqrt(r4_3 * r4_3 + r5_3 * r5_3)
  if r6_3 > 0 then
    r6_3 = 1 / r6_3
  end
  local r7_3 = -(r5_3 * r6_3)
  local r8_3 = r4_3 * r6_3
  local r9_3 = -r7_3 * r3_3.x
  local r10_3 = -r8_3 * r3_3.y
  local r12_3 = -(r7_3 * r2_3.x + r8_3 * r2_3.y + -(r0_3.x * r7_3 + r0_3.y * r8_3)) / (r7_3 * r9_3 + r8_3 * r10_3)
  if 0 < r12_3 and r12_3 <= 1 then
    local r13_3 = r2_3.x + r12_3 * r9_3
    local r14_3 = r2_3.y + r12_3 * r10_3
    if (r13_3 - r0_3.x) * (r13_3 - r1_3.x) + (r14_3 - r0_3.y) * (r14_3 - r1_3.y) <= 0 then
      return true
    end
  end
  return false
end
local function r8_0(r0_4, r1_4, r2_4, r3_4, r4_4, r5_4)
  -- line: [121, 213] id: 4
  local r6_4 = {
    r2_0.sequenceNames.White,
    r2_0.sequenceNames.Silver,
    r2_0.sequenceNames.Gold,
    r2_0.sequenceNames.Red
  }
  local r7_4 = nil
  if r5_4 then
    r7_4 = 4
  else
    r7_4 = r3_4 + 1
    if r7_4 < 1 or 3 < r7_4 then
      r7_4 = 3
    end
  end
  local r8_4 = display.newGroup()
  r2_0.CreateSprite(r8_4, r6_4[r7_4], 0, 0):play()
  r8_4.x = r1_4
  r8_4.y = r2_4
  r8_4.xScale = 1.5
  r8_4.yScale = 1.5
  r8_4.isVisible = false
  _G.MissleRoot:insert(r8_4)
  local r10_4 = {
    x = math.random() * (r3_0 - r1_4) + r1_4,
    y = math.random() * (r4_0 - r2_4) + r2_4,
  }
  local r11_4 = display.newGroup()
  r11_4.x = r1_4
  r11_4.y = r2_4
  local r14_4 = util.LoadParts(r11_4, (function(r0_5)
    -- line: [188, 188] id: 5
    return "data/map/interface/" .. r0_5 .. ".png"
  end)(r1_0.GetTreasureboxFilename(r3_4)), 0, 0)
  local r15_4 = r0_0.new(r11_4)
  r15_4.setVelecity(2.5, -12.8)
  r15_4.setElastic(0.5)
  r15_4.setFrameEnd(28)
  r15_4.setOneBoundEnd()
  if r0_4 == -1 then
    r15_4.setDirectionLeft()
  end
  _G.MissleRoot:insert(r11_4)
  local r16_4 = {
    sx = r1_4,
    sy = r2_4,
    cpoint = r10_4,
    spr = r8_4,
    ms = 0,
    endListener = r4_4,
    ev = nil,
    bound = r15_4,
  }
  r16_4.ev = events.Register(r6_0, r16_4, 0.5)
end
return {
  Pop = function(r0_6, r1_6, r2_6, r3_6, r4_6, r5_6)
    -- line: [218, 220] id: 6
    r8_0(r0_6, r1_6, r2_6, r3_6, r4_6, r5_6)
  end,
}
