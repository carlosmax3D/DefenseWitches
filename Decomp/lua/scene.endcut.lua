-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = "data/endcut"
local function r1_0(r0_1)
  -- line: [7, 7] id: 1
  return r0_0 .. "/" .. r0_1 .. ".png"
end
local r2_0 = nil
local r3_0 = nil
local r4_0 = nil
local function r5_0(r0_2, r1_2)
  -- line: [15, 24] id: 2
  if r1_2.phase == "ended" then
    sound.PlaySE(1)
    r3_0 = transit.Register(r0_2, {
      time = 5000,
      alpha = 0.01,
      onComplete = function(r0_3)
        -- line: [20, 22] id: 3
        util.ChangeScene({
          prev = r4_0,
          scene = "title",
        })
      end,
    })
  end
end
local function r6_0(r0_4, r1_4, r2_4)
  -- line: [26, 56] id: 4
  local r3_4 = r1_4.delay
  if r3_4 > 0 then
    r3_4 = r3_4 - r2_4
    if r3_4 < 0 then
      r3_4 = 0
    end
    r1_4.delay = r3_4
    return true
  end
  local r4_4 = r1_4.ms + r2_4
  local r5_4 = r1_4.ed
  local r6_4 = r1_4.sx
  local r8_4 = r1_4.ex - r6_4
  local r9_4 = r1_4.spr
  if r5_4 < r4_4 then
    r4_4 = r5_4
  end
  r1_4.ms = r4_4
  r9_4.maskX = easing.linear(r4_4, r5_4, r6_4, r8_4)
  if r5_4 <= r4_4 then
    display.remove(r9_4)
    r2_0 = nil
    return false
  end
  return true
end
function r4_0()
  -- line: [86, 100] id: 6
  director:changeFxTime(250)
  if r2_0 then
    events.Delete(r2_0)
    r2_0 = nil
  end
  if r3_0 then
    transit.Delete(r3_0)
    r3_0 = nil
  end
  events.DeleteNamespace("endcut")
end
return {
  new = function(r0_5)
    -- line: [58, 84] id: 5
    events.SetNamespace("endcut")
    local r1_5 = display.newGroup()
    local r2_5 = util.MakeGroup(r1_5)
    util.MakeFrame(r1_5)
    local r3_5 = nil
    local r4_5 = nil
    util.LoadTileBG(r2_5, db.LoadTileData("endcut", "body"), r0_0)
    local r5_5 = -200
    local r6_5 = nil
    local r7_5 = nil
    r3_5 = util.LoadParts(r2_5, r1_0("text"), 456, 8)
    r3_5:setMask(graphics.newMask(r1_0("mask")))
    r3_5.maskX = r5_5
    r2_0 = events.Register(r6_0, {
      spr = r3_5,
      sx = r5_5,
      ex = 300,
      delay = 2200,
      ms = 0,
      ed = 6000,
    }, 1)
    r2_5.touch = r5_0
    r2_5:addEventListener("touch")
    return r1_5
  end,
  Cleanup = r4_0,
}
