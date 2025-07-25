-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
return {
  new = function(r0_1)
    -- line: [9, 178] id: 1
    local r9_1 = nil	-- notice: implicit variable refs by block#[0]
    local r8_1 = nil	-- notice: implicit variable refs by block#[0]
    local r1_1 = {
      px = 0,
      py = 0,
      dy = 0,
      phase = 0,
      phase_speed = 0,
      amp = 0,
      direction = 0,
      scale = nil,
      rotation = nil,
    }
    local r2_1 = false
    local r3_1 = nil
    local r4_1 = 0
    local r5_1 = 1
    local function r6_1(r0_2)
      -- line: [28, 28] id: 2
      return "data/tutorial/" .. r0_2 .. ".png"
    end
    function r8_1()
      -- line: [94, 114] id: 4
      if r3_1 == nil then
        Runtime:removeEventListener("enterFrame", r8_1)
        return 
      end
      r1_1.phase = r1_1.phase + r1_1.phase_speed
      local r0_4 = math.sin(r1_1.phase * math.pi / 180)
      local r2_4 = r1_1.py + r1_1.dy
      r3_1.x = r0_4 * r1_1.amp + r1_1.px
      r3_1.y = r1_1.py
    end
    function r9_1()
      -- line: [119, 139] id: 5
      if r3_1 == nil then
        Runtime:removeEventListener("enterFrame", r9_1)
        return 
      end
      r1_1.phase = r1_1.phase + r1_1.phase_speed
      local r0_5 = math.sin(r1_1.phase * math.pi / 180)
      local r2_5 = r1_1.px + r1_1.dy
      r3_1.x = r1_1.px
      r3_1.y = r0_5 * r1_1.amp + r1_1.py
    end
    function r1_1.play(r0_6)
      -- line: [144, 157] id: 6
      if r2_1 == true then
        return 
      end
      if r0_6 == nil then
        return 
      end
      r3_1 = util.LoadParts(r0_6, r6_1("hand"), 0, 0)
      if r1_1.direction == r4_1 then
        Runtime:addEventListener("enterFrame", r8_1)
      else
        r3_1.xScale = -1
        r3_1.rotation = r1_1.rotation
        Runtime:addEventListener("enterFrame", r9_1)
      end
      r2_1 = true
    end
    function r1_1.stop()
      -- line: [162, 172] id: 7
      if r2_1 == false then
        return 
      end
      if r1_1.direction == r4_1 then
        Runtime:removeEventListener("enterFrame", r8_1)
      else
        Runtime:removeEventListener("enterFrame", r9_1)
      end
      r2_1 = false
      if r3_1 == nil then
        return 
      end
      display.remove(r3_1)
    end
    (function()
      -- line: [33, 89] id: 3
      r1_1.px = 0
      if r0_1.px ~= nil and type(r0_1.px) == "number" then
        r1_1.px = r0_1.px
      end
      r1_1.py = 0
      if r0_1.py ~= nil and type(r0_1.py) == "number" then
        r1_1.py = r0_1.py
      end
      r1_1.dy = 0
      if r0_1.dy ~= nil and type(r0_1.dy) == "number" then
        r1_1.dy = r0_1.dy
      end
      r1_1.phase = 0
      if r0_1.phase ~= nil and type(r0_1.phase) == "number" then
        r1_1.phase = r0_1.phase
      end
      r1_1.phase_speed = 0
      if r0_1.phase_speed ~= nil and type(r0_1.phase_speed) == "number" then
        r1_1.phase_speed = r0_1.phase_speed
      end
      r1_1.amp = 0
      if r0_1.amp ~= nil and type(r0_1.amp) == "number" then
        r1_1.amp = r0_1.amp
      end
      r1_1.direction = 0
      if r0_1.direction ~= nil and type(r0_1.direction) == "number" and r0_1.direction == 1 then
        r1_1.direction = r0_1.direction
      end
      r1_1.scale = -1
      if r0_1.scale ~= nil and type(r0_1.scale) == "number" then
        r1_1.scale = r0_1.scale
      end
      r1_1.rotation = 90
      if r0_1.rotation ~= nil and type(r0_1.rotation) == "number" then
        r1_1.rotation = r0_1.rotation
      end
    end)()
    return r1_1
  end,
}
