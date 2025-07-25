-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("audio")
local r1_0 = nil
local r2_0 = 1
local r3_0 = -1
local function r5_0()
  -- line: [14, 27] id: 2
  if r1_0 == nil then
    return 
  end
  r0_0.stop(r2_0)
  r0_0.dispose(r1_0)
  r1_0 = nil
  r3_0 = -1
end
local function r7_0(r0_4)
  -- line: [37, 61] id: 4
  if not _G.GameData.bgm then
    return 
  end
  if r3_0 == r0_4 then
    return 
  end
  local r1_4 = nil
  if _G.IsWindows then
    r1_4 = string.format("data/windows/bgm%02d.mp3", r0_4)
  else
    r1_4 = string.format("data/bgm/%02d.ogg", r0_4)
  end
  if r1_0 then
    r5_0()
  end
  r1_0 = r0_0.loadStream(r1_4)
  r0_0.reserveChannels(r2_0)
  r0_0.play(r1_0, {
    channel = r2_0,
    loops = -1,
  })
  r0_0.setVolume(1, {
    channel = r2_0,
  })
  r3_0 = r0_4
end
local r8_0 = "bgm.bin"
local function r9_0()
  -- line: [69, 71] id: 5
  return system.pathForFile(r8_0, system.CachesDirectory)
end
local function r10_0()
  -- line: [73, 75] id: 6
  os.remove(r9_0())
end
return {
  IsPlaying = function()
    -- line: [12, 12] id: 1
    return r3_0
  end,
  Stop = r5_0,
  FadeOut = function(r0_3)
    -- line: [29, 34] id: 3
    if r1_0 == nil then
      return 
    end
    r0_0.fadeOut({
      channel = r2_0,
      time = r0_3,
    })
    r3_0 = -1
  end,
  Play = r7_0,
  Suspend = function()
    -- line: [77, 87] id: 7
    if r3_0 ~= -1 then
      local r1_7 = io.open(r9_0(), "wb")
      r1_7:write(r3_0)
      r1_7:close()
      r5_0()
    else
      r10_0()
    end
  end,
  Resume = function()
    -- line: [89, 97] id: 8
    local r1_8 = io.open(r9_0(), "rb")
    if r1_8 == nil then
      return 
    end
    local r2_8 = r1_8:read()
    r1_8:close()
    r7_0(tonumber(r2_8))
    r10_0()
  end,
}
