-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
return {
  new = function(r0_1)
    -- line: [9, 100] id: 1
    local r7_1 = nil	-- notice: implicit variable refs by block#[0]
    local r1_1 = {
      scale = 0,
      maxScale = 0,
      minScale = 0,
    }
    local r2_1 = false
    local r3_1 = 1
    local r4_1 = nil
    local r5_1 = false
    function r7_1()
      -- line: [48, 69] id: 3
      if r4_1 == nil then
        Runtime:removeEventListener("enterFrame", r7_1)
        return 
      end
      r4_1.xScale = r3_1
      r4_1.yScale = r3_1
      if r2_1 then
        if r1_1.maxScale < r3_1 then
          r2_1 = false
        else
          r3_1 = r3_1 + r1_1.scale
        end
      elseif r3_1 < r1_1.minScale then
        r2_1 = true
      else
        r3_1 = r3_1 - r1_1.scale
      end
    end
    function r1_1.Play(r0_4)
      -- line: [75, 82] id: 4
      if r5_1 == true then
        return 
      end
      if r0_4 == nil then
        return 
      end
      r4_1 = r0_4
      r3_1 = 1
      Runtime:addEventListener("enterFrame", r7_1)
      r5_1 = true
    end
    function r1_1.Stop()
      -- line: [88, 95] id: 5
      if r5_1 == false then
        return 
      end
      r5_1 = false
      r4_1.xScale = r1_1.minScale
      r4_1.yScale = r1_1.minScale
      Runtime:removeEventListener("enterFrame", r7_1)
    end
    (function()
      -- line: [26, 42] id: 2
      r1_1.scale = 0
      if r0_1.scale ~= nil and type(r0_1.scale) == "number" then
        r1_1.scale = r0_1.scale
      end
      r1_1.maxScale = 0
      if r0_1.maxScale ~= nil and type(r0_1.maxScale) == "number" then
        r1_1.maxScale = r0_1.maxScale
      end
      r1_1.minScale = 0
      if r0_1.minScale ~= nil and type(r0_1.minScale) == "number" then
        r1_1.minScale = r0_1.minScale
      end
    end)()
    return r1_1
  end,
}
