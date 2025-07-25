-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local function r0_0(r0_1, r1_1, r2_1)
  -- line: [5, 36] id: 1
  r2_1 = math.floor(r2_1)
  local r3_1 = r0_1.xy[1]
  local r4_1 = r0_1.xy[2]
  local r5_1 = r1_1.spr
  local r6_1 = r1_1.scale[r2_1]
  local r7_1 = r1_1.pos[r2_1][1]
  local r8_1 = r1_1.pos[r2_1][2]
  r5_1.xScale = r6_1[1] * r0_1.image_scale_x_y[1]
  r5_1.yScale = r6_1[2] * r0_1.image_scale_x_y[2]
  r5_1.alpha = r1_1.alpha[r2_1]
  if r1_1.angle[r2_1] ~= 0 then
    local r9_1 = 360 - r1_1.angle[r2_1]
    r5_1.rotation = r9_1 - math.floor(r9_1 / 360) * 360
  else
    r5_1.rotation = 0
  end
  if r0_1.show_hook2 then
    r0_1.show_hook2(r5_1, r2_1, r0_1, r1_1, r0_1.show_hook2_param)
  else
    r5_1.x = r3_1 + r7_1 + r0_1.offset_x_y[1]
    r5_1.y = r4_1 + r8_1 + r0_1.offset_x_y[2]
  end
  if r0_1.show_hook then
    r0_1.show_hook(r5_1, r2_1, r0_1, r0_1.show_hook_param)
  end
  if r5_1.isVisible == false then
    r5_1.isVisible = true
  end
end
local function r1_0(r0_2, r1_2, r2_2)
  -- line: [39, 130] id: 2
  local r3_2 = not _G.NoUpdate
  if not r1_2.show then
    if r1_2.name == "PackAnimation" then
      r1_2.pack[r1_2.vect].sprite.isVisible = false
    else
      r1_2.sprite.isVisible = false
    end
    return true
  end
  if r1_2.debug then
    r1_2.debug(r1_2, r2_2)
  end
  local r4_2 = nil
  local r5_2 = nil
  if r1_2.name == "PackAnimation" then
    r4_2 = r1_2.vect
    r5_2 = r1_2.pack[r4_2].layers
  else
    r4_2 = 0
    r5_2 = r1_2.layers
  end
  local r6_2 = math.floor(r1_2.timer)
  local r7_2 = r1_2.xy[1]
  local r8_2 = r1_2.xy[2]
  local r9_2 = false
  if r0_2.update_frame and r3_2 then
    for r13_2, r14_2 in pairs(r5_2) do
      local r15_2 = r14_2.start
      local r16_2 = r14_2.stop
      local r17_2 = r14_2.spr
      if r15_2 <= r6_2 and r6_2 <= r16_2 then
        r0_0(r1_2, r14_2, r6_2 - r15_2 + 1)
        r9_2 = true
      else
        r17_2.isVisible = false
      end
    end
    if r1_2.show_hook3 then
      r1_2.show_hook3(r1_2, r1_2.show_hook3_param)
    end
  end
  r6_2 = r1_2.timer
  if not r1_2.pause then
    if r6_2 < r1_2.stop then
      r6_2 = r6_2 + r2_2 * r1_2.scale
      if r1_2.stop < r6_2 then
        r6_2 = r1_2.stop
      end
    else
      r6_2 = r6_2 + 1
    end
  end
  r1_2.timer = r6_2
  if r1_2.trigger then
    local r10_2 = r1_2.trigger
    if r10_2[1] <= r6_2 then
      r1_2.trigger = nil
      r10_2[2](r1_2, r1_2.trigger_param)
    end
  end
  if r1_2.trigger2 then
    local r10_2 = r1_2.trigger2
    if r10_2[1] <= r6_2 then
      r1_2.trigger2 = nil
      r10_2[2](r1_2, r1_2.trigger_param2)
    end
  end
  if r9_2 then
    if r1_2.name == "PackAnimation" then
      r1_2.pack[r4_2].sprite.isVisible = true
    else
      r1_2.sprite.isVisible = true
    end
  end
  if r1_2.stop < r6_2 then
    r1_2.timer = 0
    if not r1_2.loop then
      r1_2.pause = true
      r1_2.playing = false
      if r1_2.finish then
        r1_2.finish(r1_2, r1_2.finish_param)
      end
    end
  end
  return true
end
local function r2_0(r0_3, r1_3, r2_3)
  -- line: [134, 141] id: 3
  r0_3.xy = {
    r1_3,
    r2_3
  }
  for r8_3, r9_3 in pairs(r0_3.layers) do
    r9_3.spr.x = r1_3
    r9_3.spr.y = r2_3
  end
end
local function r3_0(r0_4, r1_4, r2_4)
  -- line: [144, 150] id: 4
  r0_4.xy = {
    r1_4,
    r2_4
  }
  for r8_4, r9_4 in pairs(r0_4.pack) do
    r2_0(r9_4, r1_4, r2_4)
  end
end
local function r4_0(r0_5, r1_5, r2_5, r3_5, r4_5)
  -- line: [153, 211] id: 5
  local r5_5 = display.newGroup()
  local r6_5 = {}
  local r7_5 = nil
  local r8_5 = nil
  local r9_5 = nil
  local r10_5 = nil
  for r14_5, r15_5 in pairs(r0_5) do
    r8_5 = {}
    r9_5 = r3_5 .. "/" .. r15_5.name
    r10_5 = display.newImage(r9_5)
    r10_5:setReferencePoint(display.CenterReferencePoint)
    r10_5.x = r1_5
    r10_5.y = r2_5
    r10_5.isVisible = false
    r5_5:insert(r10_5)
    r8_5.spr = r10_5
    r8_5.start = r15_5.start
    r8_5.stop = r15_5.stop
    r8_5.scale = r15_5.scale
    r8_5.alpha = r15_5.alpha
    r8_5.angle = r15_5.angle
    r8_5.pos = r15_5.pos
    table.insert(r6_5, r8_5)
    if r7_5 == nil or r7_5 < r15_5.stop then
      r7_5 = r15_5.stop
    end
  end
  local r11_5 = {
    sprite = r5_5,
    timer = 0,
    stop = r7_5,
    show = false,
    layers = r6_5,
    pause = false,
    trigger = nil,
    trigger_param = nil,
    trigger2 = nil,
    trigger2_param = nil,
    finish = nil,
    finish_param = nil,
    show_hook = nil,
    show_hook_param = nil,
    show_hook2 = nil,
    show_hook2_param = nil,
    show_hook3 = nil,
    show_hook3_param = nil,
    xy = {
      r1_5,
      r2_5
    },
    offset_x_y = {
      0,
      0
    },
    image_scale_x_y = {
      1,
      1
    },
    scale = 1,
    name = "SpriteAnimation",
    playing = false,
    loop = false,
  }
  r11_5.ev = events.Register(r1_0, r11_5, r4_5, false)
  return r11_5
end
return {
  RegisterWithInterval = r4_0,
  Register = function(r0_6, r1_6, r2_6, r3_6)
    -- line: [213, 215] id: 6
    return r4_0(r0_6, r1_6, r2_6, r3_6, 0)
  end,
  RegisterCallback = function(r0_7)
    -- line: [218, 220] id: 7
    r0_7.ev = events.Register(r1_0, r0_7, 0)
  end,
  ReplaceSprite = function(r0_8, r1_8, r2_8)
    -- line: [230, 258] id: 8
    for r13_8, r14_8 in pairs(r0_8.layers) do
      if type(r14_8) == "table" and r14_8.spr ~= nil then
        local r6_8 = r2_8[r13_8]
        if r6_8 ~= nil then
          local r7_8 = r14_8.spr.x
          local r8_8 = r14_8.spr.y
          local r9_8 = r14_8.spr.isVisible
          display.remove(r14_8.spr)
          r14_8.spr = nil
          r14_8.spr = display.newImage(r1_8 .. "/" .. r6_8.name)
          r14_8.spr:setReferencePoint(display.CenterReferencePoint)
          r14_8.spr.x = r7_8
          r14_8.spr.y = r8_8
          r14_8.spr.isVisible = r9_8
          r0_8.sprite:insert(r14_8.spr)
        end
      end
    end
  end,
  Remove = function(r0_9)
    -- line: [260, 278] id: 9
    assert(r0_9, debug.traceback())
    if r0_9.name == "PackAnimation" then
      for r4_9 = 1, r0_9.nr, 1 do
        for r8_9, r9_9 in pairs(r0_9.pack[r4_9].layers) do
          r9_9.spr.isVisible = false
          display.remove(r9_9.spr)
        end
        display.remove(r0_9.pack[r4_9].sprite)
      end
    else
      for r4_9, r5_9 in pairs(r0_9.layers) do
        r5_9.spr.isVisible = false
        display.remove(r5_9.spr)
      end
      display.remove(r0_9.sprite)
    end
    events.Delete(r0_9.ev)
  end,
  Init = function()
    -- line: [280, 281] id: 10
  end,
  Cleanup = function()
    -- line: [283, 284] id: 11
  end,
  Show = function(r0_12, r1_12, r2_12)
    -- line: [290, 368] id: 12
    assert(r0_12, debug.traceback())
    local function r3_12(r0_13)
      -- line: [293, 304] id: 13
      if r0_13 == nil or r2_12 == nil then
        return 
      end
      for r4_13, r5_13 in pairs(r2_12) do
        if r4_13 == "timer" then
          r0_13.timer = r5_13
        end
      end
    end
    if r0_12.name == "PackAnimation" then
      r0_12.show = r1_12
      local r4_12 = math.floor(r0_12.timer)
      for r8_12 = 1, r0_12.nr, 1 do
        if r8_12 == r0_12.vect then
          if r1_12 then
            local r9_12 = nil
            local r10_12 = nil
            for r14_12, r15_12 in pairs(r0_12.pack[r8_12].layers) do
              r9_12 = r15_12.start
              r10_12 = r15_12.stop
              if r9_12 <= r4_12 and r4_12 <= r10_12 then
                r0_0(r0_12, r15_12, r4_12 - r9_12 + 1)
              else
                r15_12.spr.isVisible = false
              end
            end
          end
          r0_12.pack[r8_12].sprite.isVisible = r1_12
          r3_12(r0_12.pack[r8_12].sprite)
        else
          for r12_12, r13_12 in pairs(r0_12.pack[r8_12].layers) do
            r13_12.spr.isVisible = false
          end
          r0_12.pack[r8_12].sprite.isVisible = false
          r3_12(r0_12.pack[r8_12].sprite)
        end
      end
    else
      r0_12.timer = 0
      r0_12.show = r1_12
      r0_12.sprite.isVisible = r1_12
      if r1_12 then
        local r4_12 = nil
        local r5_12 = nil
        local r6_12 = math.floor(r0_12.timer)
        for r10_12, r11_12 in pairs(r0_12.layers) do
          r4_12 = r11_12.start
          r5_12 = r11_12.stop
          if r4_12 <= r6_12 and r6_12 <= r5_12 then
            r0_0(r0_12, r11_12, r6_12 - r4_12 + 1)
          else
            r11_12.spr.isVisible = false
          end
        end
        r3_12(r0_12.sprite)
      else
        for r7_12, r8_12 in pairs(r0_12.layers) do
          r8_12.spr.isVisible = false
        end
      end
    end
    r0_12.playing = r1_12
    if r2_12 then
      for r7_12, r8_12 in pairs(r2_12) do
        if r7_12 == "timer" then
          r0_12.timer = r8_12
        elseif r7_12 == "scale" then
          r0_12.scale = r8_12
        end
      end
    end
  end,
  GetTimer = function(r0_14)
    -- line: [370, 373] id: 14
    assert(r0_14, debug.traceback())
    return r0_14.timer
  end,
  GetSprite = function(r0_15)
    -- line: [375, 378] id: 15
    assert(r0_15, debug.traceback())
    return r0_15.sprite
  end,
  SetSprite = function(r0_16)
    -- line: [380, 383] id: 16
    r0_16.sprite = r0_16
  end,
  Pause = function(r0_17, r1_17)
    -- line: [385, 390] id: 17
    assert(r0_17, debug.traceback())
    r0_17.pause = r1_17
    return r0_17.pause
  end,
  GetPos = function(r0_18)
    -- line: [392, 395] id: 18
    assert(r0_18, debug.traceback())
    return r0_18.xy
  end,
  SetPos = function(r0_19, r1_19, r2_19)
    -- line: [398, 405] id: 19
    if r0_19.name == "SpriteAnimation" then
      r2_0(r0_19, r1_19, r2_19)
    else
      r3_0(r0_19, r1_19, r2_19)
    end
  end,
  GetOffset = function(r0_20)
    -- line: [408, 410] id: 20
    return r0_20.offset_x_y
  end,
  SetOffset = function(r0_21, r1_21, r2_21)
    -- line: [413, 415] id: 21
    r0_21.offset_x_y = {
      r1_21,
      r2_21
    }
  end,
  SetImageScale = function(r0_22, r1_22, r2_22)
    -- line: [418, 420] id: 22
    r0_22.image_scale_x_y = {
      r1_22,
      r2_22
    }
  end,
  GetImageScale = function(r0_23)
    -- line: [423, 425] id: 23
    return r0_23.image_scale_x_y
  end,
  GetPause = function(r0_24)
    -- line: [427, 430] id: 24
    assert(r0_24, debug.traceback())
    return r0_24.pause
  end,
  RegisterTrigger = function(r0_25, r1_25, r2_25, r3_25)
    -- line: [432, 436] id: 25
    assert(r0_25, debug.traceback())
    r0_25.trigger = {
      r1_25,
      r2_25
    }
    r0_25.trigger_param = r3_25
  end,
  RegisterTrigger2 = function(r0_26, r1_26, r2_26, r3_26)
    -- line: [438, 442] id: 26
    assert(r0_26, debug.traceback())
    r0_26.trigger2 = {
      r1_26,
      r2_26
    }
    r0_26.trigger_param2 = r3_26
  end,
  RegisterFinish = function(r0_27, r1_27, r2_27)
    -- line: [444, 448] id: 27
    assert(r0_27, debug.traceback())
    r0_27.finish = r1_27
    r0_27.finish_param = r2_27
  end,
  RegisterShowHook = function(r0_28, r1_28, r2_28)
    -- line: [450, 454] id: 28
    assert(r0_28, debug.traceback())
    r0_28.show_hook = r1_28
    r0_28.show_hook_param = r2_28
  end,
  RegisterShowHook2 = function(r0_29, r1_29, r2_29)
    -- line: [456, 460] id: 29
    assert(r0_29, debug.traceback())
    r0_29.show_hook2 = r1_29
    r0_29.show_hook2_param = r2_29
  end,
  RegisterShowHook3 = function(r0_30, r1_30, r2_30)
    -- line: [462, 466] id: 30
    assert(r0_30, debug.traceback())
    r0_30.show_hook3 = r1_30
    r0_30.show_hook3_param = r2_30
  end,
  Move = function(r0_31, r1_31, r2_31, r3_31)
    -- line: [468, 493] id: 31
    assert(r0_31, debug.traceback())
    r0_31.xy = {
      r1_31,
      r2_31
    }
    local r4_31 = math.floor(r0_31.timer)
    local r5_31 = nil
    local r6_31 = nil
    local r7_31 = nil
    for r11_31, r12_31 in pairs(r0_31.layers) do
      r5_31 = r12_31.start
      r6_31 = r12_31.stop
      r7_31 = r12_31.spr
      if r5_31 <= r4_31 and r4_31 <= r6_31 then
        r0_0(r0_31, r12_31, r4_31 - r5_31 + 1)
        if r3_31 then
          if r3_31.angle then
            r7_31.rotation = r3_31.angle
          end
          if r3_31.scale then
            r7_31.xScale = r3_31.scale
            r7_31.yScale = r3_31.scale
          end
        end
      else
        r7_31.isVisible = false
      end
    end
  end,
  IsPause = function(r0_32)
    -- line: [495, 497] id: 32
    return r0_32.pause
  end,
  IsShow = function(r0_33)
    -- line: [499, 501] id: 33
    return r0_33.show
  end,
  IsPlaying = function(r0_34)
    -- line: [503, 505] id: 34
    return r0_34.playing
  end,
  Pack = function(...)
    -- line: [508, 545] id: 35
    local r1_35 = {
      pack = {
        ...
      },
      nr = select("#", ...),
      vect = 1,
      name = "PackAnimation",
    }
    local r2_35 = r1_35.pack[1]
    r1_35.timer = 0
    r1_35.stop = r2_35.stop
    r1_35.show = false
    r1_35.pause = false
    r1_35.tirgger = nil
    r1_35.trigger_param = nil
    r1_35.finish = nil
    r1_35.finish_param = nil
    r1_35.xy = r2_35.xy
    r1_35.offset_x_y = r2_35.offset_x_y
    r1_35.image_scale_x_y = r2_35.image_scale_x_y
    r1_35.scale = r2_35.scale
    r1_35.playing = false
    r1_35.show_hook = nil
    r1_35.show_hook_param = nil
    r1_35.show_hook2 = nil
    r1_35.show_hook2_param = nil
    r1_35.loop = r2_35.loop
    for r6_35, r7_35 in pairs(r1_35.pack) do
      events.Delete(r7_35.ev)
    end
    r1_35.ev = events.Register(r1_0, r1_35, 0, false)
    return r1_35
  end,
  ChangeVect = function(r0_36, r1_36)
    -- line: [547, 581] id: 36
    assert(r0_36, debug.traceback())
    local r2_36 = assert
    local r3_36 = r0_36.name == "PackAnimation"
    r2_36(r3_36, debug.traceback())
    r2_36 = assert
    if r1_36 >= 1 then
      r3_36 = r1_36 <= r0_36.nr
    else
      goto label_22	-- block#5 is visited secondly
    end
    r2_36(r3_36)
    if r0_36.vect == r1_36 then
      return 
    end
    r0_36.vect = r1_36
    r3_36 = r0_36.show
    local r4_36 = math.floor(r0_36.timer)
    for r8_36 = 1, r0_36.nr, 1 do
      if r8_36 == r1_36 then
        if r3_36 then
          local r9_36 = nil
          local r10_36 = nil
          for r14_36, r15_36 in pairs(r0_36.pack[r8_36].layers) do
            r9_36 = r15_36.start
            r10_36 = r15_36.stop
            if r9_36 <= r4_36 and r4_36 <= r10_36 then
              r0_0(r0_36, r15_36, r4_36 - r9_36 + 1)
            else
              r15_36.spr.isVisible = false
            end
          end
        end
        r0_36.pack[r8_36].sprite.isVisible = r3_36
      else
        for r12_36, r13_36 in pairs(r0_36.pack[r8_36].layers) do
          r13_36.spr.isVisible = false
        end
        r0_36.pack[r8_36].sprite.isVisible = false
      end
    end
  end,
  SetTimer = function(r0_37, r1_37)
    -- line: [583, 586] id: 37
    assert(r0_37, debug.traceback())
    r0_37.timer = r1_37
  end,
  Loop = function(r0_38, r1_38)
    -- line: [588, 592] id: 38
    r0_38.loop = r1_38
    return r0_38.loop
  end,
}
