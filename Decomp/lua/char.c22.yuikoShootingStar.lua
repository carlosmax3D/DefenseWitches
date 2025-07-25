-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("logic.char.CharStatus")
local r1_0 = require("common.sprite_loader").new({
  imageSheet = "common.sprites.sprite_twinkle01",
})
local r2_0 = nil
local r3_0 = nil
local function r4_0(r0_1, r1_1, r2_1)
  -- line: [18, 46] id: 1
  r1_1.passTime = r1_1.passTime + r2_1
  if r1_1.passTime < r1_1.waitTime then
    return true
  end
  r1_1.ms = r1_1.ms + r2_1
  local r4_1 = util.CalcQuadraticBezPoint({
    x = r1_1.sx,
    y = r1_1.sy,
  }, {
    x = r1_1.cpoint.x,
    y = r1_1.cpoint.y,
  }, {
    x = r2_0,
    y = r3_0,
  }, r1_1.ms / 2000)
  r1_1.spr.x = r4_1.x
  r1_1.spr.y = r4_1.y
  if r1_1.ms >= 2000 then
    display.remove(r1_1.spr)
    events.Delete(r1_1.ev)
    r1_1.ev = nil
    if r1_1.endListener ~= nil then
      r1_1.endListener()
    end
    return false
  end
  return true
end
return {
  setStarAnime = function(r0_2, r1_2, r2_2, r3_2)
    -- line: [51, 93] id: 2
    local r4_2 = r0_0.YuikoStruct
    if r4_2 == nil or r4_2.x == nil or r4_2.y == nil then
      r3_2()
      return 
    end
    local r5_2 = r4_2.x
    local r6_2 = r4_2.y
    r2_0 = r0_2
    r3_0 = r1_2
    local r7_2 = display.newGroup()
    r1_0.CreateSprite(r7_2, r1_0.sequenceNames.Purple, 0, 0):play()
    r7_2.x = r5_2
    r7_2.y = r6_2
    r7_2.xScale = 1.5
    r7_2.yScale = 1.5
    r7_2.isVisible = true
    _G.MissleRoot:insert(r7_2)
    local r10_2 = {
      sx = r5_2,
      sy = r6_2,
      cpoint = {
        x = math.random() * (r2_0 - r5_2) + r5_2,
        y = math.random() * (r3_0 - r6_2) + r6_2,
      },
      spr = r7_2,
      ms = 0,
      passTime = 0,
      waitTime = r2_2,
      endListener = r3_2,
      ev = nil,
    }
    r10_2.ev = events.Register(r4_0, r10_2, 0.5)
  end,
}
