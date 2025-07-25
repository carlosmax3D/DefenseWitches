-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
return {
  new = function(r0_1)
    -- line: [3, 171] id: 1
    local r1_1 = {}
    local r2_1 = r0_1
    local r3_1 = nil
    local r4_1 = {}
    local r5_1 = nil
    local r6_1 = 1
    local r7_1 = false
    local r8_1 = 0
    local r9_1 = 0
    local r10_1 = 0
    local r11_1 = 200
    local r12_1 = 20
    local r13_1 = 30
    local r14_1 = 1000
    local r15_1 = 1
    local r16_1 = 0
    if r2_1.time ~= nil then
      r16_1 = r2_1.time
    end
    local r17_1 = {
      {
        r = 255,
        g = 0,
        b = 0,
      },
      {
        r = 255,
        g = 255,
        b = 0,
      },
      {
        r = 0,
        g = 255,
        b = 0,
      },
      {
        r = 0,
        g = 0,
        b = 255,
      },
      {
        r = 0,
        g = 255,
        b = 255,
      }
    }
    local function r18_1(r0_2)
      -- line: [41, 52] id: 2
      local r1_2 = math.floor(r0_2 / r13_1)
      if r0_2 % r13_1 > 0 then
        r1_2 = r1_2 + 1
      end
      if #r17_1 < r1_2 then
        r1_2 = #r17_1
      end
      return r1_2
    end
    local r19_1 = r18_1(r16_1)
    local r20_1 = r15_1
    local r21_1 = r15_1
    if r2_1.x ~= nil then
      r9_1 = r2_1.x
    end
    if r2_1.y ~= nil then
      r10_1 = r2_1.y
    end
    if r2_1.w ~= nil then
      r11_1 = r2_1.w
    end
    if r2_1.h ~= nil then
      r12_1 = r2_1.h
    end
    if r2_1.rtImg ~= nil then
      r3_1 = r2_1.rtImg
    else
      r3_1 = display.newGroup()
    end
    function r1_1.start()
      -- line: [81, 83] id: 3
      r7_1 = true
    end
    function r1_1.getParam()
      -- line: [85, 87] id: 4
      return r2_1
    end
    function r1_1.makeBar()
      -- line: [89, 103] id: 5
      r5_1 = display.newGroup()
      display.newRect(r5_1, r9_1, r10_1, r11_1, r12_1):setFillColor(graphics.newGradient({
        192
      }, {
        64
      }, "down"))
      display.newRect(r5_1, r9_1 + 2, r10_1 + 1, r11_1 - 4, r12_1 - 2):setFillColor(0)
      for r4_5 = 1, #r17_1, 1 do
        r4_1[r4_5] = display.newRect(r5_1, r9_1 + 2, r10_1 + 2, r11_1 - 4, r12_1 - 4)
        r4_1[r4_5]:setFillColor(r17_1[r4_5].r, r17_1[r4_5].g, r17_1[r4_5].b)
        if r19_1 < r4_5 then
          r4_1[r4_5].isVisible = false
        end
      end
      r3_1:insert(r5_1)
    end
    function r1_1.dispose()
      -- line: [105, 116] id: 6
      for r3_6 = 1, #r17_1, 1 do
        if r4_1[r3_6] ~= nil then
          r4_1[r3_6]:removeSelf()
          r4_1[r3_6] = nil
        end
      end
      if r5_1 ~= nil then
        r5_1:removeSelf()
        r5_1 = nil
      end
    end
    function r1_1.updateFunc(r0_7)
      -- line: [119, 150] id: 7
      local r1_7 = r16_1 - r0_7
      if r1_7 < 1 or r4_1 == nil then
        return 
      end
      if #r17_1 * r13_1 < r1_7 then
        r1_7 = #r17_1 * r13_1
      end
      local r2_7 = r18_1(r1_7)
      local r3_7 = math.abs((r1_7 - (r2_7 - 1) * r13_1)) / r13_1
      r4_1[r2_7]:setReferencePoint(display.TopLeftReferencePoint)
      r4_1[r2_7].xScale = r3_7
      if r2_7 < r8_1 then
        if r8_1 - r2_7 > 1 then
          local r4_7 = nil
          for r8_7 = r2_7 + 1, r8_1, 1 do
            r4_1[r8_7].isVisible = false
          end
        else
          r4_1[r8_1].isVisible = false
        end
      end
      r8_1 = r2_7
    end
    function r1_1.setTotalTime(r0_8)
      -- line: [153, 166] id: 8
      r16_1 = r0_8
      r19_1 = r18_1(r16_1)
      local r1_8 = nil
      for r5_8 = 1, r19_1, 1 do
        r4_1[r5_8]:setReferencePoint(display.TopLeftReferencePoint)
        r4_1[r5_8].xScale = 1
        r4_1[r5_8].isVisible = true
      end
      r1_1.updateFunc(0)
    end
    r1_1.makeBar()
    return r1_1
  end,
}
