-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
return {
  new = function(r0_1)
    -- line: [5, 157] id: 1
    local r1_1 = {
      x = 0,
      y = 0,
      frameCount = 0,
      frameEnd = 100,
      state = 0,
      image = nil,
      velocity = {},
      gravity = {},
      friction = {},
      direction = 1,
      groundY = 0,
      groundLevel = 0,
      oneBoundEndFlg = false,
      enterFrame = function()
        -- line: [29, 61] id: 2
        r1_1.frameCount = r1_1.frameCount + 1
        if r1_1.state == 1 then
          r1_1.x = r1_1.x + (r1_1.velocity.x + r1_1.gravity.x / 2) * r1_1.direction
          r1_1.y = r1_1.y + r1_1.velocity.y + r1_1.gravity.y / 2
          r1_1.velocity.x = r1_1.velocity.x + r1_1.friction.x
          r1_1.velocity.y = r1_1.velocity.y + r1_1.gravity.y
          if r1_1.groundY + r1_1.groundLevel < r1_1.y then
            r1_1.y = r1_1.groundY + r1_1.groundLevel
            r1_1.velocity.y = (r1_1.velocity.y - r1_1.elastic) * -1
            if r1_1.oneBoundEndFlg then
              r1_1.state = 2
              r1_1.frameCount = 0
            end
          end
        elseif r1_1.state == 1 and r1_1.frameEnd <= r1_1.frameCount then
          r1_1.state = 3
        end
        if r1_1.frameEnd <= r1_1.frameCount then
          r1_1.state = 3
        end
        if r1_1.image ~= nil then
          r1_1.image.x = r1_1.x
          r1_1.image.y = r1_1.y
        end
      end,
      init = function(r0_3)
        -- line: [63, 95] id: 3
        if r0_3 == nil then
          return nil
        end
        r1_1.image = r0_3
        r1_1.velocity = {
          x = 5,
          y = -19.2,
        }
        r1_1.gravity = {
          x = 0,
          y = 0.9800000000000001,
        }
        r1_1.friction = {
          x = 0,
          y = 0,
        }
        r1_1.elastic = 6
        r1_1.x = r1_1.image.x
        r1_1.y = r1_1.image.y
        r1_1.groundY = r1_1.y
        r1_1.state = 1
        Runtime:addEventListener("enterFrame", r1_1.enterFrame)
      end,
      dispose = function()
        -- line: [97, 103] id: 4
        if r1_1.image ~= nil then
          Runtime:removeEventListener("enterFrame", r1_1.enterFrame)
          r1_1.image:removeSelf()
          r1_1.image = nil
        end
      end,
      hideImage = function()
        -- line: [105, 107] id: 5
        r1_1.image.isVisible = false
      end,
      setVelecity = function(r0_6, r1_6)
        -- line: [109, 112] id: 6
        r1_1.velocity.x = r0_6
        r1_1.velocity.y = r1_6
      end,
      setElastic = function(r0_7)
        -- line: [114, 116] id: 7
        r1_1.elastic = r0_7
      end,
      setGroundLevel = function(r0_8)
        -- line: [118, 120] id: 8
        r1_1.groundLevel = r0_8
      end,
      setDirectionRight = function()
        -- line: [122, 124] id: 9
        r1_1.direction = 1
      end,
      setOneBoundEnd = function()
        -- line: [126, 128] id: 10
        r1_1.oneBoundEndFlg = true
      end,
      setDirectionLeft = function()
        -- line: [130, 132] id: 11
        r1_1.direction = -1
      end,
      isOneBoundFinish = function()
        -- line: [134, 140] id: 12
        local r0_12 = 2 <= r1_1.state
      end,
      isFrameCountFinish = function()
        -- line: [142, 148] id: 13
        local r0_13 = 3 <= r1_1.state
      end,
      setFrameEnd = function(r0_14)
        -- line: [150, 152] id: 14
        r1_1.frameEnd = r0_14
      end,
    }
    r1_1.init(r0_1)
    return r1_1
  end,
}
