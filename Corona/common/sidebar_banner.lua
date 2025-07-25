-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = nil	-- notice: implicit variable refs by block#[0]
local function r1_0(r0_1, r1_1, r2_1)
  -- line: [11, 47] id: 1
  if game ~= nil and game.IsNotPauseTypeNone() then
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
local function r2_0(r0_2, r1_2, r2_2)
  -- line: [52, 79] id: 2
  local r3_2 = display.newGroup()
  if r1_2 > 3 then
    if cdn.CheckFilelist() then
      util.LoadParts(r3_2, string.format("data/side/side_cornet.png", r1_2), 0, 0)
    else
      util.LoadParts(r3_2, string.format("data/side/side_chara%02d.png", r1_2), 0, 0)
    end
  else
    util.LoadParts(r3_2, string.format("data/side/side_chara%02d.png", r1_2), 0, 0)
  end
  if r2_2 ~= nil then
    util.LoadParts(r3_2, string.format("data/side/side_chara_lv%d.png", r2_2), 0, 544)
  end
  r0_2:insert(r3_2)
  r3_2:setReferencePoint(display.TopLeftReferencePoint)
  r3_2.x = -176
  r3_2.y = 0
  r3_2.isVisible = false
  return r3_2
end
local function r3_0(r0_3, r1_3, r2_3, r3_3, r4_3)
  -- line: [84, 161] id: 3
  if r0_3 == nil or r1_3 == nil then
    return 
  end
  local r5_3 = display.newRect(r0_3, 0, 0, 176, _G.Height)
  r5_3:setFillColor(0, 0, 0)
  r5_3:setReferencePoint(display.TopLeftReferencePoint)
  r5_3.x = -176
  r5_3.y = 0
  r0_0 = {}
  r0_0.spr = {}
  r0_0.ev = nil
  for r9_3, r10_3 in pairs(r1_3) do
    r5_3 = nil
    if r10_3 == 1 then
      if r3_3 then
        r5_3 = r2_0(r0_3, r9_3, 3)
      else
        r5_3 = r2_0(r0_3, r9_3)
      end
    elseif r10_3 == 2 then
      if r3_3 then
        r5_3 = r2_0(r0_3, r9_3, 4)
      else
        r5_3 = r2_0(r0_3, r9_3)
      end
    elseif r2_3[r9_3] ~= 3 and r4_3 then
      if r3_3 then
        r5_3 = r2_0(r0_3, r9_3, 4)
      else
        r5_3 = r2_0(r0_3, r9_3)
      end
    end
    if r5_3 then
      table.insert(r0_0.spr, r5_3)
    end
  end
  if r2_3[11] ~= 3 then
    if r3_3 then
      table.insert(r0_0.spr, r2_0(r0_3, 11, 4))
    else
      table.insert(r0_0.spr, r2_0(r0_3, 11))
    end
  end
  if r2_3[12] ~= 3 then
    if r3_3 then
      table.insert(r0_0.spr, r2_0(r0_3, 12, 4))
    else
      table.insert(r0_0.spr, r2_0(r0_3, 12))
    end
  end
  if r2_3[14] ~= 3 then
    if r3_3 then
      table.insert(r0_0.spr, r2_0(r0_3, 14, 4))
    else
      table.insert(r0_0.spr, r2_0(r0_3, 14))
    end
  end
  r0_0.ms = 0
  r0_0.es = 4000
  r0_0.ev = events.Register(r1_0, r0_0, 1)
  events.SetSkipFrame(r0_0.ev, true)
  r0_0.nr = 1
  r0_0.max = #r0_0.spr
  r0_0.tween = nil
  if r0_0.max > 0 then
    r0_0.spr[1].isVisible = true
  end
end
return {
  new = function(r0_4)
    -- line: [166, 212] id: 4
    local r3_4 = true
    local r4_4 = false
    local r5_4 = nil
    if _G.WidthDiff == 0 then
      return 
    end
    local r1_4 = nil	-- notice: implicit variable refs by block#[15]
    if r0_4.rtImg then
      r1_4 = r0_4.rtImg
    else
      r1_4 = display.newGroup()
    end
    local r2_4 = nil	-- notice: implicit variable refs by block#[15]
    if r0_4.achievement then
      r2_4 = r0_4.achievement
    else
      _G.MapDB = system.pathForFile(string.format("data/map/%02d/%03d.sqlite", _G.MaxMap, _G.MaxStage), system.ResourceDirectory)
      r2_4 = db.GetSpecialAchievement()
    end
    if r0_4.summonState then
      r5_4 = r0_4.summonState
    else
      local r7_4 = nil
      r5_4, r7_4 = db.LoadSummonData(_G.UserID)
    end
    if r0_4.showLevel ~= nil then
      r3_4 = r0_4.showLevel
    end
    if r0_4.showUnlock ~= nil then
      r4_4 = r0_4.showUnlock
    end
    events.SetNamespace("game")
    r3_0(r1_4, r2_4, r5_4, r3_4, r4_4)
  end,
  Cleanup = function()
    -- line: [217, 229] id: 5
    if r0_0 then
      if r0_0.ev then
        events.Delete(r0_0.ev)
        r0_0.ev = nil
      end
      if r0_0.tween then
        transition.cancel(r0_0.tween)
        r0_0.tween = nil
      end
      r0_0 = nil
    end
  end,
}
