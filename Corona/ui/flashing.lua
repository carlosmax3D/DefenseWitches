-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
return {
  new = function(r0_1)
    -- line: [10, 90] id: 1
    local r12_1 = nil	-- notice: implicit variable refs by block#[0]
    local r1_1 = {
      speed = 0.1,
    }
    local r2_1 = nil
    local r3_1 = nil
    local r4_1 = nil
    local r5_1 = false
    local r6_1 = false
    local r7_1 = 0
    local r8_1 = 0
    local r9_1 = 0
    local r10_1 = 0
    function r12_1()
      -- line: [43, 61] id: 3
      if r2_1 == nil then
        Runtime:removeEventListener("enterFrame", r12_1)
        return 
      end
      r8_1 = r7_1 / 30
      r10_1 = r8_1 - r9_1
      if r1_1.speed < r10_1 then
        if r5_1 then
          r4_1.isVisible = false
          r5_1 = false
        else
          r4_1.isVisible = true
          r5_1 = true
        end
        r9_1 = r8_1
      end
      r7_1 = r7_1 + 1
    end
    function r1_1.Play(r0_4, r1_4)
      -- line: [66, 75] id: 4
      if r6_1 == true then
        return 
      end
      if r0_4 == nil then
        return 
      end
      if r1_4 == nil then
        return 
      end
      r2_1 = r0_4
      r4_1 = r1_4
      Runtime:addEventListener("enterFrame", r12_1)
      r6_1 = true
    end
    function r1_1.Stop()
      -- line: [80, 85] id: 5
      if r6_1 == false then
        return 
      end
      r6_1 = false
      r4_1.isVisible = false
      Runtime:removeEventListener("enterFrame", r12_1)
    end
    (function()
      -- line: [31, 37] id: 2
      r1_1.speed = 0
      if r0_1.speed ~= nil and type(r0_1.speed) == "number" then
        r1_1.speed = r0_1.speed
      end
    end)()
    return r1_1
  end,
}
