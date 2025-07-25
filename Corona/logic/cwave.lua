-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("common.sprite_loader").new({
  imageSheet = "common.sprites.sprite_number02",
})
local r1_0 = require("common.sprite_loader").new({
  imageSheet = "common.sprites.sprite_string01",
})
local r2_0 = "data/game/wave"
local r3_0 = nil
local r4_0 = nil
local r5_0 = nil
local r6_0 = nil
local r7_0 = nil
local r8_0 = nil
local r9_0 = nil
local r10_0 = nil
local r11_0 = nil
local function r12_0(r0_1)
  -- line: [20, 20] id: 1
  return r2_0 .. "/" .. r0_1 .. ".png"
end
local function r13_0(r0_2)
  -- line: [22, 32] id: 2
  local r1_2 = display.newGroup()
  util.LoadTileParts(r1_2, 0, 0, db.LoadTileData("cwave", "bg"), r2_0)
  r0_2:insert(r1_2)
  return r1_2
end
local function r14_0(r0_3)
  -- line: [34, 49] id: 3
  local r1_3 = display.newGroup()
  local r2_3 = math.ceil(math.log10(r0_3 + 1))
  local r3_3 = 24 * (r2_3 - 1)
  local r4_3 = nil
  local r5_3 = nil
  local r6_3 = r0_3
  for r10_3 = 1, r2_3, 1 do
    r6_3 = r6_3 * 0.1
    r0_0.CreateImage(r1_3, r0_0.sequenceNames.World, r0_0.frameDefines.WorldStart + r6_3 % 10, r3_3, 0)
    r3_3 = r3_3 - 24
  end
  return r1_3
end
local function r15_0(r0_4, r1_4, r2_4)
  -- line: [51, 75] id: 4
  local r3_4 = display.newGroup()
  local r5_4 = 0
  r1_0.CreateImage(r3_4, r1_0.sequenceNames.World, r1_0.frameDefines.World, r5_4, 0)
  r5_4 = r5_4 + 128
  local r4_4 = r14_0(r1_4)
  r3_4:insert(r4_4)
  r4_4:setReferencePoint(display.TopLeftReferencePoint)
  r4_4.x = r5_4
  r5_4 = r5_4 + 24
  if r1_4 >= 10 then
    r5_4 = r5_4 + 24
  end
  r0_0.CreateImage(r3_4, r0_0.sequenceNames.World, r0_0.frameDefines.WorldHyphen, r5_4, 0)
  r5_4 = r5_4 + 24
  r4_4 = r14_0(r2_4)
  r3_4:insert(r4_4)
  r4_4:setReferencePoint(display.TopLeftReferencePoint)
  r4_4.x = r5_4
  r0_4:insert(r3_4)
  return r3_4
end
local function r16_0(r0_5, r1_5)
  -- line: [77, 79] id: 5
  r0_0.CreateNumbers(r0_5, r0_0.sequenceNames.Wave, 2, r1_5, 0)
end
local function r17_0(r0_6)
  -- line: [81, 95] id: 6
  local r1_6 = display.newGroup()
  local r3_6 = 0
  r1_0.CreateImage(r1_6, r1_0.sequenceNames.Wave, r1_0.frameDefines.Wave, 0, 4)
  r7_0 = display.newGroup()
  r16_0(r7_0, r3_6 + 152)
  r1_6:insert(r7_0)
  r0_6:insert(r1_6)
  return r1_6
end
local function r18_0(r0_7)
  -- line: [97, 107] id: 7
  local r1_7 = display.newGroup()
  r1_0.CreateImage(r1_7, r1_0.sequenceNames.Final, r1_0.frameDefines.Final, 0, 0)
  r1_0.CreateImage(r1_7, r1_0.sequenceNames.Wave, r1_0.frameDefines.Wave, 176, 0)
  r0_7:insert(r1_7)
  return r1_7
end
local function r20_0()
  -- line: [135, 160] id: 9
  if r3_0 and r3_0.tween then
    transit.Delete(r3_0.tween)
    r3_0.tween = nil
  end
  if r4_0 and r4_0.tween then
    transit.Delete(r4_0.tween)
    r4_0.tween = nil
  end
  if r5_0 and r5_0.tween then
    transit.Delete(r5_0.tween)
    r5_0.tween = nil
  end
  if r6_0 and r6_0.tween then
    transit.Delete(r6_0.tween)
    r6_0.tween = nil
  end
  if r8_0 and r8_0.tween then
    transit.Delete(r8_0.tween)
    r8_0.tween = nil
  end
  if r11_0 then
    transit.Delete(r11_0)
    r11_0 = nil
  end
end
local function r22_0(r0_11, r1_11)
  -- line: [190, 195] id: 11
  r1_11 = math.floor(r1_11)
  for r5_11 = 0, 9, 1 do
    r0_11[r5_11].isVisible = r5_11 == r1_11
  end
end
local function r23_0(r0_12, r1_12, r2_12, r3_12)
  -- line: [210, 225] id: 12
  local r4_12 = r0_12 / r1_12
  local r5_12 = nil
  if r4_12 < 0.16666666666666666 or 0.8333333333333334 <= r4_12 then
    r5_12 = 0
  elseif 0.16666666666666666 <= r4_12 and r4_12 < 0.3333333333333333 then
    r5_12 = easing.inOutExpo(6 * r4_12 - 1, 1, 0, 1)
  elseif 0.3333333333333333 <= r4_12 and r4_12 < 0.6666666666666666 then
    r5_12 = 1
  elseif 0.6666666666666666 <= r4_12 and r4_12 < 0.8333333333333334 then
    r5_12 = easing.inOutExpo(6 * r4_12 - 4, 1, 1, -1)
  end
  return r5_12
end
local function r24_0(r0_13)
  -- line: [227, 230] id: 13
  local r1_13 = r0_13 - 1
  return r1_13 * r1_13 * r1_13 + 1
end
local function r25_0(r0_14)
  -- line: [232, 234] id: 14
  return r0_14 * r0_14 * r0_14
end
local function r26_0(r0_15)
  -- line: [236, 242] id: 15
  if r0_15 < 0.5 then
    return 0.5 * r24_0(r0_15 * 2)
  else
    return 0.5 * r25_0((r0_15 - 0.5) * 2) + 0.5
  end
end
local function r27_0(r0_16, r1_16, r2_16, r3_16)
  -- line: [244, 246] id: 16
  return r2_16 + r3_16 * r26_0(r0_16 / r1_16)
end
local function r28_0(r0_17, r1_17, r2_17, r3_17)
  -- line: [255, 272] id: 17
  local r4_17 = r0_17 / r1_17
  local r5_17 = nil
  if r4_17 < 0.25 then
    r5_17 = -160
  elseif r4_17 >= 0.75 then
    r5_17 = 1120
  elseif 0.25 <= r4_17 and r4_17 < 0.3333333333333333 then
    r5_17 = easing.inOutExpo(12 * r4_17 - 3, 1, -160, 610)
  elseif 0.3333333333333333 <= r4_17 and r4_17 < 0.6666666666666666 then
    r5_17 = r27_0(r0_17, r1_17, 450, 60)
  elseif 0.6666666666666666 <= r0_17 and r4_17 < 0.75 then
    r5_17 = easing.inOutExpo(12 * r4_17 - 8, 1, 510, 610)
  end
  return r5_17
end
local function r29_0(r0_18, r1_18, r2_18, r3_18)
  -- line: [277, 292] id: 18
  local r4_18 = r0_18 / r1_18
  local r5_18 = nil
  if r4_18 < 0.25 then
    r5_18 = 1120
  elseif r4_18 >= 0.75 then
    r5_18 = -160
  elseif 0.25 <= r4_18 and r4_18 < 0.3333333333333333 then
    r5_18 = easing.inOutExpo(12 * r4_18 - 3, 1, 1120, -610)
  elseif 0.3333333333333333 <= r4_18 and r4_18 < 0.6666666666666666 then
    r5_18 = r27_0(r0_18, r1_18, 510, -60)
  elseif 0.6666666666666666 <= r4_18 and r4_18 < 0.75 then
    r5_18 = easing.inOutExpo(12 * r4_18 - 8, 1, 450, -610)
  end
  return r5_18
end
local function r30_0(r0_19)
  -- line: [294, 296] id: 19
  r0_19.sprite.isVisible = false
end
local r31_0 = 2000
local function r33_0(r0_21, r1_21, r2_21, r3_21)
  -- line: [362, 375] id: 21
  local r4_21 = r0_21 / r1_21
  local r5_21 = nil
  if r4_21 < 0.16666666666666666 then
    r5_21 = 0
  elseif 0.16666666666666666 <= r4_21 and r4_21 < 0.3333333333333333 then
    r5_21 = easing.inOutExpo(6 * r4_21 - 1, 1, 0, 1)
  elseif 0.3333333333333333 <= r4_21 and r4_21 < 0.8333333333333334 then
    r5_21 = 1
  elseif r4_21 >= 0.8333333333333334 then
    r5_21 = easing.inOutExpo(6 * r4_21 - 5, 1, 1, -1)
  end
  return r5_21
end
local function r34_0(r0_22, r1_22, r2_22, r3_22)
  -- line: [377, 389] id: 22
  local r4_22 = r0_22 / r1_22
  local r5_22 = nil
  if r4_22 < 0.16666666666666666 then
    r5_22 = 0
  elseif 0.16666666666666666 <= r4_22 and r4_22 < 0.3333333333333333 then
    r5_22 = easing.inOutExpo(6 * r4_22 - 1, 1, 0, 1)
  elseif r4_22 >= 0.3333333333333333 then
    r5_22 = 1
  end
  return r5_22
end
local function r35_0(r0_23)
  -- line: [391, 399] id: 23
  if r8_0 then
    if r8_0.tween then
      transit.Delete(r8_0.tween)
    end
    display.remove(r8_0)
    r8_0 = nil
  end
end
local function r38_0(r0_26, r1_26)
  -- line: [452, 475] id: 26
  if r8_0 and r8_0.tween then
    transit.Delete(r8_0.tween)
    r8_0.tween = nil
  end
  anime.SetTimer(r0_26, r0_26.stop)
  timer.performWithDelay(1500, function(r0_27)
    -- line: [459, 474] id: 27
    r11_0 = transit.Register(r10_0, {
      time = 500,
      alpha = 0,
      transition = easing.linear,
      onComplete = function(r0_28)
        -- line: [464, 472] id: 28
        if r9_0 then
          anime.Remove(r9_0)
          r9_0 = nil
        end
        transit.Delete(r11_0)
        r11_0 = nil
        r1_26({})
      end,
    })
  end)
end
return {
  Init = function(r0_8, r1_8, r2_8)
    -- line: [109, 133] id: 8
    local r3_8 = nil	-- notice: implicit variable refs by block#[0]
    r7_0 = r3_8
    r3_0 = r13_0(r0_8)
    r4_0 = r15_0(r0_8, r1_8, r2_8)
    r5_0 = r17_0(r0_8)
    r6_0 = r18_0(r0_8)
    r3_0.isVisible = false
    r3_0:setReferencePoint(display.TopLeftReferencePoint)
    r3_0.x = 168
    r3_0.y = 264
    r4_0.isVisible = false
    r4_0:setReferencePoint(display.CenterReferencePoint)
    r4_0.x = 364
    r4_0.y = 284
    r5_0.isVisible = false
    r5_0:setReferencePoint(display.CenterReferencePoint)
    r5_0.x = 360
    r5_0.y = 332
    r6_0.isVisible = false
    r6_0:setReferencePoint(display.CenterReferencePoint)
    r6_0.x = 316
    r3_8 = r6_0
    r3_8.y = 336
  end,
  Cleanup = function()
    -- line: [162, 188] id: 10
    r20_0()
    if r3_0 then
      display.remove(r3_0)
      r3_0 = nil
    end
    if r4_0 then
      display.remove(r4_0)
      r4_0 = nil
    end
    if r5_0 then
      display.remove(r5_0)
      r5_0 = nil
    end
    if r6_0 then
      display.remove(r6_0)
      r6_0 = nil
    end
    if r8_0 then
      display.remove(r8_0)
      r8_0 = nil
    end
    if r7_0 ~= nil then
      r7_0 = nil
    end
  end,
  Start = function(r0_20, r1_20)
    -- line: [300, 360] id: 20
    if r0_20 == 1 then
      statslog.LogSend("game_start", {})
    end
    local r2_20 = _G.SpeedType
    local r3_20 = r31_0
    if r2_20 and r2_20 == 2 then
      r3_20 = r3_20 * 2
    elseif r2_20 and r2_20 == 3 then
      r3_20 = r3_20 * 3
    end
    r20_0()
    if r3_0 == nil then
      return 
    end
    r3_0.alpha = 0
    r3_0.tween = transit.Register(r3_0, {
      time = r3_20,
      alpha = 1,
      transition = r23_0,
      onComplete = r30_0,
    })
    r3_0.tween.value.alpha[3] = 0
    r4_0.x = -160
    r4_0.tween = transit.Register(r4_0, {
      time = r3_20,
      x = 1120,
      transition = r28_0,
      onComplete = r30_0,
    })
    if r0_20 < r1_20 then
      r7_0[1]:setFrame(r0_20 % 10 + 1)
      r7_0[2]:setFrame(r0_20 / 10 + 1)
      r5_0.x = 1120
      r5_0.tween = transit.Register(r5_0, {
        time = r3_20,
        x = -160,
        transition = r29_0,
        onComplete = r30_0,
      })
    else
      r6_0.x = 1120
      r6_0.tween = transit.Register(r6_0, {
        time = r3_20,
        x = -160,
        transition = r29_0,
        onComplete = r30_0,
      })
    end
  end,
  GameEnd = function(r0_24, r1_24, r2_24, r3_24)
    -- line: [401, 443] id: 24
    if r3_24 == nil then
      r3_24 = false
    end
    local r4_24 = _G.SpeedType
    if r4_24 and r4_24 == 2 then
      r2_24 = r2_24 * 2
    elseif r4_24 and r4_24 == 3 then
      r2_24 = r2_24 * 3
    end
    local r5_24 = nil
    if r1_24 then
      r5_24 = "gameover"
    else
      r5_24 = "stageclear"
    end
    local r6_24 = display.newGroup()
    r8_0 = util.LoadTileParts(r6_24, 168, 264, db.LoadTileData("cwave", r5_24), r2_0)
    r8_0.alpha = 0
    if r3_24 then
      r8_0.tween = transit.Register(r8_0, {
        time = r2_24,
        alpha = 1,
        transition = r34_0,
      })
      r9_0 = anime.Register(require("anm.perfect").GetData(), 480, 376, r2_0)
      r6_24:insert(anime.GetSprite(r9_0))
    else
      r8_0.tween = transit.Register(r8_0, {
        time = r2_24,
        alpha = 1,
        transition = r33_0,
        onComplete = r35_0,
      })
      r8_0.tween.value.alpha[3] = 0
      r9_0 = nil
    end
    r0_24:insert(r6_24)
    r10_0 = r6_24
  end,
  removeGameOver = function()
    -- line: [448, 450] id: 25
    r35_0()
  end,
  ViewPerfect = function(r0_29)
    -- line: [477, 482] id: 29
    anime.RegisterFinish(r9_0, r38_0, r0_29)
    anime.Pause(r9_0, false)
    anime.Show(r9_0, true)
    sound.PlaySE(5, 21, true)
  end,
}
