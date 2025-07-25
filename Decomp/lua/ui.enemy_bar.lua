-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
return {
  new = function(r0_1)
    -- line: [4, 66] id: 1
    local r1_1 = {}
    local r2_1 = 490
    local r3_1 = 7
    local r4_1 = {
      {
        r = 255,
        g = 0,
        b = 204,
      },
      {
        r = 51,
        g = 255,
        b = 255,
      },
      {
        r = 255,
        g = 0,
        b = 204,
      },
      {
        r = 255,
        g = 0,
        b = 204,
      },
      {
        r = 255,
        g = 0,
        b = 204,
      },
      {
        r = 255,
        g = 0,
        b = 204,
      },
      {
        r = 255,
        g = 0,
        b = 204,
      },
      {
        r = 255,
        g = 0,
        b = 204,
      },
      {
        r = 255,
        g = 0,
        b = 204,
      },
      {
        r = 255,
        g = 255,
        b = 0,
      }
    }
    local r5_1 = display.newGroup()
    local r6_1 = nil
    local r7_1 = nil
    function r1_1.MakeBaseBar()
      -- line: [36, 48] id: 2
      r6_1 = display.newGroup()
      local r0_2 = display.newRect(r6_1, 180, 0, r2_1, r3_1)
      r0_2:setFillColor(0)
      r0_2.alpha = 0.5
      r7_1 = display.newRect(r6_1, 180, 0, r2_1, r3_1)
      r7_1:setFillColor(r4_1[_G.MapSelect].r, r4_1[_G.MapSelect].g, r4_1[_G.MapSelect].b)
    end
    function r1_1.UpdateGauge()
      -- line: [53, 63] id: 3
      if _G.EnemyCnt == 0 then
        r7_1.isVisible = false
      else
        local r0_3 = _G.EnemyCnt / _G.EnemyTotalCnt
        r7_1:setReferencePoint(display.TopLeftReferencePoint)
        r7_1.xScale = r0_3
      end
    end
    return r1_1
  end,
}
