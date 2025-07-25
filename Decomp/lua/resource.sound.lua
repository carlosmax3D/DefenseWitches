-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("audio")
local r1_0 = 1
local r3_0 = "v100"
local r4_0 = "v400"
local r5_0 = "bom"
local r6_0 = "data/sound"
local r7_0 = "%02d.ogg"
local r8_0 = nil
local r9_0 = nil
local function r10_0(r0_1, r1_1)
  -- line: [23, 42] id: 1
  local r2_1 = nil	-- notice: implicit variable refs by block#[7, 8]
  local r3_1 = nil	-- notice: implicit variable refs by block#[7, 8]
  if _G.IsSimulator and _G.IsWindows then
    r2_1 = "data/windows"
    r3_1 = "mp3"
  else
    r2_1 = "data/sound"
    if r1_1 then
      r3_1 = r1_1
    else
      r3_1 = "ogg"
    end
  end
  if type(r0_1) == "string" then
    return string.format("%s/%s.%s", r2_1, r0_1, r3_1)
  else
    return string.format("%s/se%02d.%s", r2_1, r0_1, r3_1)
  end
end
local function r11_0(r0_2, r1_2, r2_2)
  -- line: [47, 60] id: 2
  if r0_2 == nil or r1_2 == nil or r2_2 == nil then
    return nil
  end
  if r0_2 == r1_0 then
    return string.format("%s/%s/%02d/%s/%s/", r6_0, r3_0, r1_2, r5_0, r2_2)
  else
    return string.format("%s/%s/%02d/%s/%s/", r6_0, r4_0, r1_2, r5_0, r2_2)
  end
end
local function r12_0(r0_3, r1_3, r2_3)
  -- line: [65, 78] id: 3
  if r0_3 == nil or r1_3 == nil or r2_3 == nil then
    return nil
  end
  if r0_3 == r1_0 then
    return string.format("%s/%s/%02d/%s/", r6_0, r3_0, r1_3, r2_3)
  else
    return string.format("%s/%s/%02d/%s/", r6_0, r4_0, r1_3, r2_3)
  end
end
local function r13_0(r0_4, r1_4)
  -- line: [83, 95] id: 4
  if r0_4 == nil or r1_4 == nil then
    return nil
  end
  if not _G.IsSimulator or not _G.IsWindows then
    return r0_4 .. string.format(r7_0, r1_4)
  end
end
local r23_0 = nil
local function r25_0(r0_16)
  -- line: [230, 237] id: 16
  local r1_16 = r23_0[r0_16]
  r0_0.stop(r0_16)
  if r1_16 then
    r0_0.dispose(r1_16)
    r23_0[r0_16] = nil
  end
end
local function r27_0(r0_18, r1_18)
  -- line: [245, 258] id: 18
  if r1_18 > 0 then
    r25_0(r1_18)
  end
  local r2_18 = r0_0.loadStream(r0_18)
  if r2_18 == nil then
    DebugPrint(r0_18)
    DebugPrint("not found voice : " .. tostring(r0_18))
    return 
  end
  DebugPrint("found voice : " .. tostring(r0_18))
  r0_0.play(r2_18, {
    channel = r1_18,
  })
  r23_0[r1_18] = r2_18
end
local r29_0 = nil
local r30_0 = nil
local function r31_0()
  -- line: [272, 278] id: 20
  if r29_0 == nil then
    return 
  end
  r0_0.stop(r30_0)
  r0_0.dispose(r29_0)
  r30_0 = nil
  r29_0 = nil
end
local function r33_0(r0_22, r1_22)
  -- line: [290, 292] id: 22
  return r12_0(_G.GameData.voice_type, r0_22, r1_22)
end
local function r34_0(r0_23, r1_23)
  -- line: [297, 299] id: 23
  return r13_0(r0_23, r1_23)
end
return {
  Init = function()
    -- line: [98, 103] id: 5
    r8_0 = {}
    for r3_5 = 1, 3, 1 do
      r8_0[r3_5] = r0_0.loadSound(r10_0(r3_5))
    end
  end,
  Cleanup = function()
    -- line: [106, 107] id: 6
  end,
  GameSELoad = function()
    -- line: [110, 121] id: 7
    for r3_7 = 4, 19, 1 do
      r8_0[r3_7] = r0_0.loadSound(r10_0(r3_7))
    end
    for r3_7 = 21, 31, 1 do
      r8_0[r3_7] = r0_0.loadSound(r10_0(r3_7))
    end
    r8_0[52] = r0_0.loadSound(r10_0(52))
    r8_0[53] = r0_0.loadSound(r10_0(53))
  end,
  GameSECleanup = function()
    -- line: [123, 144] id: 8
    r0_0.stop()
    for r3_8 = 4, 18, 1 do
      if r8_0[r3_8] then
        r0_0.dispose(r8_0[r3_8])
      end
      r8_0[r3_8] = nil
    end
    for r3_8 = 21, 27, 1 do
      if r8_0[r3_8] then
        r0_0.dispose(r8_0[r3_8])
      end
      r8_0[r3_8] = nil
    end
    for r3_8 = 52, 53, 1 do
      if r8_0[r3_8] then
        r0_0.dispose(r8_0[r3_8])
      end
      r8_0[r3_8] = nil
    end
  end,
  PlaySE = function(r0_9, r1_9, r2_9)
    -- line: [146, 167] id: 9
    if r1_9 == nil then
      if r0_9 == 1 then
        r1_9 = 23
      elseif r0_9 == 2 then
        r1_9 = 24
      elseif r0_9 == 3 then
        r1_9 = 25
      else
        r1_9 = 0
      end
    end
    if r2_9 == nil then
      r2_9 = false
    end
    if not _G.GameData.se then
      return r1_9
    end
    if 0 < r1_9 and r0_0.isChannelPlaying(r1_9) then
      r0_0.stop(r1_9)
    end
    return r0_0.play(r8_0[r0_9], {
      channel = r1_9,
    })
  end,
  LoopSE = function(r0_10, r1_10)
    -- line: [169, 182] id: 10
    if not _G.GameData.se then
      return r1_10
    end
    assert(r8_0[r0_10], debug.traceback())
    if r1_10 then
      if r0_0.isChannelPlaying(r1_10) then
        return nil
      else
        return r0_0.play(r8_0[r0_10], {
          channel = r1_10,
          loops = -1,
        })
      end
    else
      return r0_0.play(r8_0[r0_10], {
        loops = -1,
      })
    end
  end,
  StopSE = function(r0_11)
    -- line: [184, 186] id: 11
    r0_0.stop(r0_11)
  end,
  PlaySound = function(r0_13, r1_13, r2_13)
    -- line: [198, 223] id: 13
    if not _G.GameData.se then
      if r1_13 then
        return r1_13()
      end
      return 
    end
    local r3_13 = r0_0.loadSound(r10_0(r0_13, "ogg"))
    local r4_13 = nil	-- notice: implicit variable refs by block#[7, 8]
    if r2_13 then
      r4_13 = r0_0.play(r3_13)
      r9_0 = timer.performWithDelay(r2_13, function(r0_14)
        -- line: [210, 215] id: 14
        r0_0.stop(r4_13)
        if r1_13 then
          r1_13()
        end
      end)
      -- close: r4_13
    elseif r1_13 then
      r4_13 = r0_0
      r4_13 = r4_13.play
      r4_13(r3_13, {
        onComplete = r1_13,
      })
    else
      r4_13 = r0_0
      r4_13 = r4_13.play
      r4_13(r3_13)
    end
  end,
  InitVoice = function()
    -- line: [226, 228] id: 15
    r23_0 = {}
  end,
  CleanupVoice = function()
    -- line: [239, 243] id: 17
    for r3_17, r4_17 in pairs(r23_0) do
      r25_0(r3_17)
    end
  end,
  PlayVoice = r27_0,
  StopVoice = r25_0,
  LoopSEStream = function(r0_19, r1_19)
    -- line: [262, 267] id: 19
    if not _G.GameData.se then
      return 
    end
    local r2_19 = r0_0.loadSound(r10_0(r0_19))
    r0_0.play(r2_19, {
      channel = r1_19,
      loops = -1,
    })
    return r2_19
  end,
  StopTitleSound = r31_0,
  PlayTitleSound = function(r0_24)
    -- line: [301, 323] id: 24
    local r1_24 = assert
    local r2_24 = nil	-- notice: implicit variable refs by block#[4]
    if r0_24 ~= 1 then
      r2_24 = r0_24 == 2
    else
      goto label_6	-- block#3 is visited secondly
    end
    r1_24(r2_24)
    r31_0()
    if not _G.GameData.voice then
      return 
    end
    r1_24 = nil
    r2_24 = nil
    r29_0 = r0_0.loadStream(r34_0(r33_0(99, _G.GameData.language), r0_24))
    r30_0 = r0_0.play(r29_0)
  end,
  GetCharBomVoicePath = function(r0_21, r1_21)
    -- line: [283, 285] id: 21
    return r11_0(_G.GameData.voice_type, r0_21, r1_21)
  end,
  GetCharVoicePath = r33_0,
  GetCharVoiceFilename = r34_0,
  cancelCallbackAtPlaySound = function()
    -- line: [191, 196] id: 12
    if r9_0 then
      timer.cancel(r9_0)
      r9_0 = nil
    end
  end,
  OptionChangeTypeOld = r1_0,
  OptionChangeTypeNew = 2,
  play_sound = function(r0_25, r1_25)
    -- line: [326, 335] id: 25
    if not _G.GameData.voice then
      return 
    end
    if not r0_25 then
      return 
    end
    if r0_25.sound_path == nil then
      return 
    end
    local r2_25 = r34_0(r0_25.sound_path, r1_25)
    local r3_25 = r0_25.sound_channel
    r25_0(r3_25)
    r27_0(r2_25, r3_25)
  end,
}
