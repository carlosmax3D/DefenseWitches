-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
return {
  new = function(r0_1)
    -- line: [8, 137] id: 1
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
    local function r5_1()
      -- line: [89, 109] id: 3
      if r3_1 == nil then
        Runtime:removeEventListener("enterFrame", finger_vertical)
        return 
      end
      r1_1.phase = r1_1.phase + r1_1.phase_speed
      local r0_3 = math.sin(r1_1.phase * math.pi / 180)
      local r2_3 = r1_1.px + r1_1.dy
      r3_1.x = r1_1.px
      r3_1.y = r0_3 * r1_1.amp + r1_1.py
    end
    function r1_1.play(r0_4)
      -- line: [114, 120] id: 4
      if r2_1 == true then
        return 
      end
      if r0_4 == nil then
        return 
      end
      r3_1 = r0_4
      Runtime:addEventListener("enterFrame", r5_1)
      r2_1 = true
    end
    function r1_1.stop()
      -- line: [125, 131] id: 5
      if r2_1 == false then
        return 
      end
      Runtime:removeEventListener("enterFrame", r5_1)
      r2_1 = false
      if r3_1 == nil then
        return 
      end
      display.remove(r3_1)
    end
    (function()
      -- line: [28, 84] id: 2
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
