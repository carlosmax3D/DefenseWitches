-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
module(..., package.seeall)
MedalTestIndex = {
  Sp = 1,
  P = 2,
  S = 3,
  B = 4,
  Hp1 = 5,
  Ex3 = 6,
  Ex2 = 7,
  Ex1 = 8,
  Disable = 9,
}
function new(r0_1)
  -- line: [23, 168] id: 1
  local r1_1 = require("common.sprite_loader").new({
    imageSheet = "test.sprites.sprite_test_medal01",
  })
  local r2_1 = {}
  local r3_1 = nil
  local function r4_1(r0_2, r1_2)
    -- line: [43, 63] id: 2
    if r1_2 == MedalTestIndex.Sp then
      return r1_1.CreateImage(r0_2, r1_1.sequenceNames.Medals, r1_1.frameDefines.Sp, 0, 0)
    elseif r1_2 == MedalTestIndex.P then
      return r1_1.CreateImage(r0_2, r1_1.sequenceNames.Medals, r1_1.frameDefines.P, 0, 0)
    elseif r1_2 == MedalTestIndex.S then
      return r1_1.CreateImage(r0_2, r1_1.sequenceNames.Medals, r1_1.frameDefines.S, 0, 0)
    elseif r1_2 == MedalTestIndex.B then
      return r1_1.CreateImage(r0_2, r1_1.sequenceNames.Medals, r1_1.frameDefines.B, 0, 0)
    elseif r1_2 == MedalTestIndex.Hp1 then
      return r1_1.CreateImage(r0_2, r1_1.sequenceNames.Medals, r1_1.frameDefines.Hp1, 0, 0)
    elseif r1_2 == MedalTestIndex.Ex3 then
      return r1_1.CreateImage(r0_2, r1_1.sequenceNames.Medals, r1_1.frameDefines.Ex3, 0, 0)
    elseif r1_2 == MedalTestIndex.Ex2 then
      return r1_1.CreateImage(r0_2, r1_1.sequenceNames.Medals, r1_1.frameDefines.Ex2, 0, 0)
    elseif r1_2 == MedalTestIndex.Ex1 then
      return r1_1.CreateImage(r0_2, r1_1.sequenceNames.Medals, r1_1.frameDefines.Ex1, 0, 0)
    else
      return r1_1.CreateImage(r0_2, r1_1.sequenceNames.Medals, r1_1.frameDefines.Disable, 0, 0)
    end
  end
  local function r5_1(r0_3)
    -- line: [66, 125] id: 3
    if r0_3 == nil or r1_1 == nil then
      return 
    end
    r3_1 = display.newGroup()
    local r1_3 = display.newGroup()
    local r2_3 = display.newGroup()
    local r3_3 = display.newGroup()
    local r4_3 = display.newGroup()
    local r5_3 = display.newGroup()
    local r6_3 = display.newGroup()
    local r7_3 = display.newGroup()
    local r8_3 = display.newGroup()
    local r9_3 = r4_1(r1_3, MedalTestIndex.Sp)
    local r10_3 = r4_1(r2_3, MedalTestIndex.P)
    local r11_3 = r4_1(r3_3, MedalTestIndex.S)
    local r12_3 = r4_1(r4_3, MedalTestIndex.B)
    local r13_3 = r4_1(r5_3, MedalTestIndex.Hp1)
    local r14_3 = r4_1(r6_3, MedalTestIndex.Ex3)
    local r15_3 = r4_1(r7_3, MedalTestIndex.Ex2)
    local r16_3 = r4_1(r8_3, MedalTestIndex.Ex1)
    r3_1:insert(MedalTestIndex.Sp, r1_3)
    r3_1:insert(MedalTestIndex.P, r2_3)
    r3_1:insert(MedalTestIndex.S, r3_3)
    r3_1:insert(MedalTestIndex.B, r4_3)
    r3_1:insert(MedalTestIndex.Hp1, r5_3)
    r3_1:insert(MedalTestIndex.Ex3, r6_3)
    r3_1:insert(MedalTestIndex.Ex2, r7_3)
    r3_1:insert(MedalTestIndex.Ex1, r8_3)
    r0_3:insert(r3_1)
    r1_3:setReferencePoint(display.TopLeftReferencePoint)
    r2_3:setReferencePoint(display.TopLeftReferencePoint)
    r3_3:setReferencePoint(display.TopLeftReferencePoint)
    r4_3:setReferencePoint(display.TopLeftReferencePoint)
    r5_3:setReferencePoint(display.TopLeftReferencePoint)
    r6_3:setReferencePoint(display.TopLeftReferencePoint)
    r7_3:setReferencePoint(display.TopLeftReferencePoint)
    r8_3:setReferencePoint(display.TopLeftReferencePoint)
    r1_3.x = 0
    r1_3.y = 0
    r2_3.x = 18
    r2_3.y = 0
    r3_3.x = 36
    r3_3.y = 0
    r4_3.x = 54
    r4_3.y = 0
    r5_3.x = 72
    r5_3.y = 0
    r6_3.x = 0
    r6_3.y = 18
    r7_3.x = 18
    r7_3.y = 18
    r8_3.x = 36
    r8_3.y = 18
    r3_1:setReferencePoint(display.TopLeftReferencePoint)
    r3_1.x = 330
    r3_1.y = 35
  end
  local function r6_1(r0_4, r1_4)
    -- line: [128, 149] id: 4
    if r3_1 == nil or r0_4 < 1 or MedalTestIndex.Disable < r0_4 or r1_4 == nil then
      return 
    end
    if r3_1[r0_4][1] ~= nil then
      display.remove(r3_1[r0_4][1])
      r3_1[r0_4][1] = nil
    end
    r3_1[r0_4]:insert(display.newGroup())
    if r1_4 == true then
      r4_1(r3_1[r0_4][1], r0_4)
    else
      r4_1(r3_1[r0_4][1], MedalTestIndex.Disable)
    end
  end
  function r2_1.CreateMedal(r0_5)
    -- line: [155, 157] id: 5
    r5_1(r0_5)
  end
  function r2_1.EnableMedal(r0_6, r1_6)
    -- line: [160, 162] id: 6
    r6_1(r0_6, r1_6)
  end
  return r2_1
end
