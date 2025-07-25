-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("scene.help")
local r1_0 = "data/help/enemies"
local r2_0 = 30
local function r3_0(r0_1)
  -- line: [10, 10] id: 1
  return r1_0 .. "/" .. r0_1 .. ".png"
end
local function r4_0(r0_2)
  -- line: [11, 11] id: 2
  return r3_0(r0_2 .. _G.UILanguage)
end
local function r5_0(r0_3)
  -- line: [12, 12] id: 3
  return "data/stage/" .. r0_3 .. ".png"
end
local function r6_0(r0_4)
  -- line: [13, 13] id: 4
  return r5_0(r0_4 .. _G.UILanguage)
end
local function r7_0(r0_5)
  -- line: [14, 14] id: 5
  return "data/help/witches/" .. r0_5 .. ".png"
end
local r8_0 = nil
local r9_0 = nil
local r10_0 = 1
local r11_0 = nil
local r12_0 = nil
local r13_0 = nil
local function r14_0(r0_6)
  -- line: [22, 26] id: 6
  sound.PlaySE(2)
  r0_0.ViewHelp("index")
  return true
end
local function r15_0()
  -- line: [28, 50] id: 7
  if r9_0 == nil or r8_0 == nil then
    return 
  end
  assert(#r8_0 == #r9_0)
  local r0_7 = nil
  for r4_7, r5_7 in pairs(r8_0) do
    r5_7.isVisible = r4_7 == r10_0
  end
  if r10_0 == 1 then
    for r4_7, r5_7 in pairs(r9_0) do
      r5_7[1].isVisible = false
      r5_7[2].isVisible = false
    end
  else
    for r4_7, r5_7 in pairs(r9_0) do
      r0_7 = r4_7 == r10_0
      r5_7[1].isVisible = not r0_7
      r5_7[2].isVisible = r0_7
    end
  end
end
local function r16_0(r0_8)
  -- line: [52, 65] id: 8
  local r1_8 = r10_0 + r0_8
  local r2_8 = #r9_0
  if r1_8 < 1 or r2_8 < r1_8 then
    sound.PlaySE(2)
    return false
  end
  sound.PlaySE(1)
  r10_0 = r1_8
  r15_0()
  return true
end
local function r17_0(r0_9)
  -- line: [67, 70] id: 9
  r16_0(-1)
  return true
end
local function r18_0(r0_10)
  -- line: [72, 75] id: 10
  r16_0(1)
  return true
end
local function r19_0(r0_11, r1_11)
  -- line: [77, 108] id: 11
  local r2_11 = r1_11.phase
  if r2_11 == "began" then
    if r11_0 then
      transit.Delete(r11_0)
      r11_0 = nil
    end
    r0_11.sx = r1_11.x
    display.getCurrentStage():setFocus(r0_11)
  elseif r2_11 == "moved" then
    local r3_11 = r0_11.sx
    if r3_11 == nil then
      r3_11 = r1_11.x
      r0_11.sx = r3_11
    end
    r0_11.x = 240 + r1_11.x - r3_11
  elseif r2_11 == "ended" or r2_11 == "canceled" then
    if _G.GameData.voice then
      local r3_11 = math.random(1, 9)
      sound.StopVoice(r2_0)
      sound.PlayVoice(string.format("%s/%02d.%s", r12_0, r3_11, r13_0), r2_0)
    end
    if r11_0 then
      transit.Delete(r11_0)
    end
    r11_0 = transit.Register(r0_11, {
      time = 250,
      x = 240,
      transition = easing.inExpo,
    })
    display.getCurrentStage():setFocus(nil)
  end
end
local function r20_0()
  -- line: [110, 164] id: 12
  local r0_12 = display.newGroup()
  local r1_12 = require("resource.enemy_define")
  local r2_12 = _G.GameData.language
  if _G.IsSimulator and _G.IsWindows then
    r12_0 = string.format("data/windows/90/%s", r2_12)
    r13_0 = "mp3"
  else
    r12_0 = string.format("data/sound/90/%s", r2_12)
    r13_0 = "aac"
  end
  util.LoadParts(r0_12, r4_0("cornet_name_"), 64, 80)
  local r3_12 = nil
  if _G.UILanguage == "jp" then
    r3_12 = 200
  else
    r3_12 = 176
  end
  util.LoadParts(r0_12, r4_0("cornet_comment_"), 64, r3_12)
  r11_0 = nil
  local r5_12 = util.LoadTileParts(r0_12, 240, 0, db.LoadTileData("help", "cornet"), r1_0)
  r5_12.touch = r19_0
  r5_12:addEventListener("touch")
  local r6_12 = display.newGroup()
  local r7_12 = util.MakeText4({
    rtImg = r6_12,
    size = 20,
    line = 22,
    align = "left",
    w = 200,
    h = 24,
    color = {
      255,
      255,
      255
    },
    shadow = {
      51,
      51,
      51
    },
    shadowBoldWidth = 1,
    font = native.systemFontBold,
    msg = "Voice :",
  })
  local r8_12 = util.MakeText4({
    rtImg = r6_12,
    size = 20,
    line = 22,
    align = "left",
    w = 200,
    h = 24,
    color = {
      255,
      255,
      255
    },
    shadow = {
      51,
      51,
      51
    },
    shadowBoldWidth = 1,
    font = native.systemFontBold,
    msg = db.GetMessage(r1_12.VoiceArtistName[r1_12.EnemyId.Cornet]),
  })
  r7_12:setReferencePoint(display.TopLeftReferencePoint)
  r8_12:setReferencePoint(display.TopLeftReferencePoint)
  r7_12.x = 0
  r7_12.y = 0
  r8_12.x = r7_12.x + r7_12.width
  r8_12.y = 0
  r0_12:insert(r6_12)
  r6_12:setReferencePoint(display.BottomRightReferencePoint)
  r6_12.x = 920
  r6_12.y = 550
  return r0_12
end
local function r21_0(r0_13, r1_13, r2_13)
  -- line: [166, 169] id: 13
  util.LoadParts(r0_13, r4_0(string.format("enemies_zako%02d_", r2_13)), r1_13, 88)
end
local function r22_0(r0_14)
  -- line: [171, 185] id: 14
  local r1_14 = (r0_14 - 2) * 3 + 1
  local r2_14 = display.newGroup()
  if r1_14 < 10 then
    r21_0(r2_14, 88, r1_14)
    r21_0(r2_14, 352, r1_14 + 1)
    r21_0(r2_14, 616, r1_14 + 2)
  else
    r21_0(r2_14, 220, r1_14)
    r21_0(r2_14, 484, r1_14 + 1)
  end
  return r2_14
end
local function r23_0(r0_15)
  -- line: [187, 197] id: 15
  local r1_15 = (r0_15 - 6) * 2 + 1
  local r2_15 = display.newGroup()
  util.LoadParts(r2_15, r4_0(string.format("enemies_boss%02d_", r1_15)), 88, 88)
  util.LoadParts(r2_15, r4_0(string.format("enemies_boss%02d_", r1_15 + 1)), 488, 88)
  return r2_15
end
local function r24_0()
  -- line: [199, 243] id: 16
  local r0_16 = display.newGroup()
  local r1_16 = nil
  local r2_16 = nil
  util.LoadParts(r0_16, r4_0("enemies_title_"), 192, 16)
  r8_0 = {}
  for r6_16 = 1, 10, 1 do
    if r6_16 == 1 then
      r1_16 = r20_0()
    elseif 2 <= r6_16 and r6_16 <= 5 then
      r1_16 = r22_0(r6_16)
    else
      r1_16 = r23_0(r6_16)
    end
    r0_16:insert(r1_16)
    r1_16.isVisible = false
    r8_0[#r8_0 + 1] = r1_16
  end
  r9_0 = {}
  for r6_16 = 212, 716, 56 do
    r2_16 = {}
    r1_16 = util.LoadParts(r0_16, r7_0("witches_page_nonactive"), r6_16, 516)
    r1_16.isVisible = false
    r2_16[#r2_16 + 1] = r1_16
    r1_16 = util.LoadParts(r0_16, r7_0("witches_page_active"), r6_16, 516)
    r1_16.isVisible = false
    r2_16[#r2_16 + 1] = r1_16
    r9_0[#r9_0 + 1] = r2_16
  end
  r15_0()
  util.LoadBtn({
    rtImg = r0_16,
    fname = r6_0("back_"),
    x = 256,
    y = 560,
    func = r14_0,
  })
  util.LoadBtn({
    rtImg = r0_16,
    fname = r5_0("scrl_previous"),
    x = 448,
    y = 560,
    func = r17_0,
  })
  util.LoadBtn({
    rtImg = r0_16,
    fname = r5_0("scrl_next"),
    x = 576,
    y = 560,
    func = r18_0,
  })
  return r0_16
end
return {
  Load = function(r0_17, r1_17)
    -- line: [245, 250] id: 17
    sound.InitVoice()
    local r2_17 = r24_0()
    r0_17:insert(r2_17)
    return r2_17
  end,
  Cleanup = function()
    -- line: [252, 258] id: 18
    sound.CleanupVoice()
    if r11_0 then
      transit.Delete(r11_0)
      r11_0 = nil
    end
  end,
}
