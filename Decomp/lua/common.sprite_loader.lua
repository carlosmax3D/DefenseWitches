-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
return {
  new = function(r0_1)
    -- line: [9, 243] id: 1
    local r1_1 = {
      sequenceNames = nil,
      frameDefines = nil,
    }
    local r2_1 = nil
    local r3_1 = nil
    local function r4_1(r0_2)
      -- line: [26, 31] id: 2
      if r0_2 == nil then
        return 0
      end
      return r0_2
    end
    local function r5_1()
      -- line: [36, 44] id: 3
      if r2_1 == nil or r2_1.LoadImageSheet == nil or r2_1.GetSpriteFrameInfo == nil or r2_1.IsContain == nil then
        return false
      end
      return true
    end
    local function r6_1(r0_4)
      -- line: [49, 67] id: 4
      if r0_4 == nil then
        return false
      end
      r2_1 = require(r0_4)
      if r5_1() == false then
        return false
      end
      r3_1 = r2_1.LoadImageSheet()
      r1_1.sequenceNames = r2_1.SequenceNames
      r1_1.frameDefines = r2_1.FrameDefines
      return true
    end
    local function r7_1(r0_5)
      -- line: [72, 80] id: 5
      if r5_1() == false or r3_1 == nil or r2_1.IsContain(r0_5) == false then
        return nil
      end
      return display.newSprite(r3_1, r2_1.GetSpriteFrameInfo(r0_5))
    end
    local function r8_1()
      -- line: [85, 101] id: 6
      if r1_1.sequenceNames ~= nil then
        r1_1.sequenceNames = nil
      end
      if r1_1.frameDefines ~= nil then
        r1_1.frameDefines = nil
      end
      if r3_1 ~= nil then
        r3_1 = nil
      end
      if r2_1 ~= nil then
        r2_1 = nil
      end
    end
    function r1_1.Load(r0_8)
      -- line: [120, 122] id: 8
      return r6_1(r0_8)
    end
    function r1_1.CreateSprite(r0_9, r1_9, r2_9, r3_9)
      -- line: [127, 141] id: 9
      if r0_9 == nil then
        return nil
      end
      local r4_9 = r7_1(r1_9)
      if r4_9 == nil then
        return nil
      end
      r0_9:insert(r4_9)
      r4_9.x = r2_9
      r4_9.y = r3_9
      return r4_9
    end
    function r1_1.CreateNumbers(r0_10, r1_10, r2_10, r3_10, r4_10, r5_10, r6_10)
      -- line: [146, 175] id: 10
      if r5_1() == false then
        return nil
      end
      local r7_10 = nil
      r3_10 = r4_1(r3_10)
      r4_10 = r4_1(r4_10)
      r5_10 = r4_1(r5_10)
      r6_10 = r4_1(r6_10)
      for r11_10 = 1, r2_10, 1 do
        local r12_10 = nil
        if r2_1.IsContain(r1_10) == true then
          r12_10 = r7_1(r1_10)
        end
        if r12_10 ~= nil then
          local r13_10 = r2_10 * (r12_10.width + r5_10)
          r12_10:setReferencePoint(display.TopLeftReferencePoint)
          r12_10.x = r3_10 + r13_10 - r11_10 * (r12_10.width + r5_10)
          r12_10.y = r4_10 + r6_10
          r0_10:insert(r11_10, r12_10)
        end
      end
      return r0_10
    end
    function r1_1.CreateImage(r0_11, r1_11, r2_11, r3_11, r4_11)
      -- line: [180, 187] id: 11
      if r5_1() == false or r2_1.IsContain(r1_11) == false then
        return nil
      end
      return display.newImage(r0_11, r3_1, r2_11, r3_11, r4_11)
    end
    function r1_1.CreateImageNumbers(r0_12, r1_12, r2_12, r3_12, r4_12, r5_12, r6_12, r7_12)
      -- line: [192, 227] id: 12
      if r5_1() == false then
        return nil
      end
      local r8_12 = nil
      local r9_12 = r3_12
      local r10_12 = nil
      if type(r3_12) == "number" then
        r10_12 = string.len(tostring(r3_12))
      else
        r10_12 = string.len(r3_12)
      end
      r4_12 = r4_1(r4_12)
      r5_12 = r4_1(r5_12)
      r6_12 = r4_1(r6_12)
      r7_12 = r4_1(r7_12)
      for r14_12 = 1, r10_12, 1 do
        r9_12 = math.floor(r9_12 * 0.1)
        local r16_12 = r1_1.CreateImage(r0_12, r1_12, r2_12 + r9_12 % 10, r4_12, r5_12)
        if r16_12 ~= nil then
          local r17_12 = r10_12 * (r16_12.width + r6_12)
          r16_12:setReferencePoint(display.TopLeftReferencePoint)
          r16_12.x = r4_12 + r17_12 - r14_12 * (r16_12.width + r6_12)
          r16_12.y = r5_12 + r7_12
        end
      end
      return r0_12
    end
    function r1_1.Clean()
      -- line: [232, 234] id: 13
      r8_1()
    end
    (function()
      -- line: [106, 112] id: 7
      r8_1()
      if r0_1.imageSheet ~= nil then
        r6_1(r0_1.imageSheet)
      end
    end)()
    return r1_1
  end,
}
