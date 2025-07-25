-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("ui.sin").new({
  px = 0,
  py = 0,
  dx = 1,
  dy = 1,
  phase = 0,
  phase_speed = 7,
  amp = 5,
})
local r1_0 = nil
local r2_0 = nil
local r3_0 = nil
local r4_0 = nil
local r5_0 = nil
local r6_0 = nil
local function r7_0(r0_1)
  -- line: [30, 32] id: 1
  return "data/cont/" .. r0_1 .. ".png"
end
local function r8_0(r0_2)
  -- line: [34, 36] id: 2
  return r7_0(r0_2 .. _G.UILanguage)
end
local function r9_0(r0_3)
  -- line: [38, 40] id: 3
  return "data/cont/" .. r0_3 .. ".jpg"
end
local function r10_0(r0_4)
  -- line: [42, 44] id: 4
  return "data/cont/story/" .. r0_4 .. ".png"
end
local function r11_0(r0_5)
  -- line: [46, 48] id: 5
  return r10_0(r0_5 .. _G.UILanguage)
end
local function r12_0(r0_6)
  -- line: [50, 52] id: 6
  return "data/cont/story/" .. r0_6 .. ".jpg"
end
local function r13_0(r0_7)
  -- line: [54, 56] id: 7
  return r12_0(r0_7 .. _G.UILanguage)
end
local function r14_0(r0_8)
  -- line: [58, 60] id: 8
  return "data/title/" .. r0_8 .. ".png"
end
local function r15_0(r0_9)
  -- line: [62, 64] id: 9
  return r14_0(r0_9 .. _G.UILanguage)
end
local function r16_0(r0_10)
  -- line: [69, 77] id: 10
  sound.PlaySE(1)
  r0_0.stop()
  require("logic.run_to_first").startFirstGame({
    prevFunc = r6_0,
  })
  return true
end
local function r17_0(r0_11)
  -- line: [82, 104] id: 11
  util.MakeMat(r0_11)
  local r1_11 = _G.Width * 0.5
  local r2_11 = 530
  local r5_11 = display.newGroup()
  local r6_11 = display.newRect(r5_11, 0, 0, 380, 82)
  r6_11:setReferencePoint(display.CenterReferencePoint)
  r6_11.x = r1_11
  r6_11.y = r2_11
  r6_11:setFillColor(255, 255, 255)
  r6_11.alpha = 0.01
  function r5_11.touch(r0_12, r1_12)
    -- line: [98, 102] id: 12
    if r1_12.phase == "ended" then
      r16_0()
    end
  end
  r5_11:addEventListener("touch", r5_11)
end
local function r18_0(r0_13)
  -- line: [109, 114] id: 13
  sound.PlaySE(1)
  r0_0.stop()
  util.ChangeScene({
    prev = r6_0,
    scene = "invite_game",
  })
  return true
end
local function r19_0()
  -- line: [119, 127] id: 14
  local r0_14 = _G.ServerStatus.invitation
  if r0_14 == nil or r0_14 == 0 then
    return 
  end
  util.LoadBtn({
    rtImg = r4_0,
    fname = r8_0("top_menu01"),
    x = 756,
    y = 562,
    func = r18_0,
  })
end
local function r20_0(r0_15)
  -- line: [132, 137] id: 15
  sound.PlaySE(1)
  r0_0.stop()
  util.ChangeScene({
    prev = r6_0,
    scene = "cont_game",
  })
  return true
end
local function r21_0()
  -- line: [142, 146] id: 16
  util.LoadBtn({
    rtImg = r4_0,
    fname = r8_0("top_menu03"),
    x = 12,
    y = 562,
    func = r20_0,
  })
end
local function r22_0(r0_17, r1_17)
  -- line: [151, 192] id: 17
  local r2_17 = r1_17.phase
  local r3_17 = r0_17.y
  local r4_17 = r0_17
  if r2_17 == "began" then
    if r3_0 then
      transit.Delete(r3_0)
      r3_0 = nil
    end
    r4_17.start_y = r1_17.y
    display.getCurrentStage():setFocus(r0_17)
    if r4_17.y <= 88 then
      r4_17.is_scroll_up = true
    end
  elseif r2_17 == "moved" then
    local r5_17 = r4_17.start_y
    if r5_17 == nil then
      r5_17 = 0
    end
    local r7_17 = r3_17
    r3_17 = r3_17 + r1_17.y - r5_17
    if r4_17.is_scroll_up and r3_17 > 88 then
      r3_17 = r7_17
    elseif r3_17 > 504 then
      r3_17 = r7_17
    end
    if r3_17 < r2_0 then
      r3_17 = r2_0
    end
    r4_17.y = r3_17
    r4_17.start_y = r1_17.y
    if r3_17 <= 88 then
      r4_17.is_scroll_up = true
    end
  elseif r2_17 == "ended" or r2_17 == "cancelled" then
    local r6_17 = r1_0 * (r3_17 - r2_0) / 1950
    if r6_17 > 0 then
      if r3_0 then
        transit.Delete(r3_0)
      end
      r3_0 = transit.Register(r4_17, {
        time = r6_17,
        transition = easing.liner,
        y = r2_0,
      })
    end
    display.getCurrentStage():setFocus(nil)
  end
  return true
end
local function r23_0()
  -- line: [197, 217] id: 18
  local r0_18 = "data/title"
  util.LoadTileBG(r4_0, db.LoadTileData("title", "bg"), r0_18)
  util.LoadTileParts(r4_0, 136, 0, db.LoadTileData("title", "logo"), r0_18)
  local r3_18 = anime.RegisterWithInterval(require("anm.cont_banner").GetData(), 470, 390, "data/cont/banner", 100)
  anime.Show(r3_18, true)
  anime.Loop(r3_18, true)
  local r4_18 = anime.GetSprite(r3_18)
  r4_18.touch = r16_0
  r4_18:addEventListener("touch", r4_18)
  util.LoadParts(r4_18, r8_0("top_text_start_"), 340, 245)
  r4_0:insert(r4_18)
  r0_0.play(r4_18)
  util.MakeFrame(r4_0)
end
local function r24_0()
  -- line: [222, 263] id: 19
  util.LoadTileBG(r4_0, db.LoadTileData("story", "bg"), "data/cont/story")
  r5_0 = display.newGroup()
  r5_0.left = 88
  r5_0.top = 504
  r5_0.right = 872
  r5_0.bottom = 1896
  r5_0.x = 88
  r5_0.y = 504
  r4_0:insert(r5_0)
  util.LoadParts(r5_0, r13_0("main_"), 0, 0):setMask(cdn.NewMask(r13_0("mask_")))
  local r1_19 = display.newRect(r5_0, 0, 0, 784, 1392)
  r1_19:setFillColor(0, 0, 0)
  r1_19.alpha = 0.01
  util.LoadParts(r4_0, r10_0("mask_top"), 88, 0)
  util.LoadParts(r4_0, r10_0("mask_bottom"), 88, 520)
  r1_0 = 60000
  r2_0 = -840
  r3_0 = transit.Register(r5_0, {
    time = r1_0,
    transition = easing.liner,
    y = r2_0,
  })
  r5_0.touch = r22_0
  r5_0:addEventListener("touch", r5_0)
  util.LoadBtn({
    rtImg = r4_0,
    fname = r15_0("title_menu02"),
    x = 380,
    y = 550,
    func = r16_0,
  })
  util.LoadParts(r4_0, r10_0("title"), 430, 20)
  util.MakeFrame(r4_0)
end
local function r25_0()
  -- line: [268, 308] id: 20
  util.LoadTileBG(r4_0, db.LoadTileData("story", "bg"), "data/cont/story")
  r5_0 = display.newGroup()
  r5_0.left = 88
  r5_0.top = 504
  r5_0.right = 872
  r5_0.bottom = 1896
  r5_0.x = 88
  r5_0.y = 504
  r4_0:insert(r5_0)
  util.LoadParts(r5_0, r13_0("main2_"), 0, 0):setMask(cdn.NewMask(r13_0("mask2_")))
  local r1_20 = display.newRect(r5_0, 0, 0, 784, 1950)
  r1_20:setFillColor(0, 0, 0)
  r1_20.alpha = 0.01
  util.LoadParts(r4_0, r10_0("mask_top"), 88, 0)
  util.LoadParts(r4_0, r10_0("mask_bottom"), 88, 520)
  r1_0 = 60000
  r2_0 = -1400
  r3_0 = transit.Register(r5_0, {
    time = r1_0,
    transition = easing.liner,
    y = r2_0,
  })
  r5_0.touch = r22_0
  r5_0:addEventListener("touch", r5_0)
  util.LoadBtn({
    rtImg = r4_0,
    fname = r15_0("title_menu02"),
    x = 380,
    y = 550,
    func = r16_0,
  })
  util.LoadParts(r4_0, r10_0("title"), 430, 20)
  util.MakeFrame(r4_0)
end
local function r26_0()
  -- line: [313, 322] id: 21
  util.LoadParts(r4_0, r9_0("bg_title_02"), 0, 0)
  local r0_21 = util.MakeGroup(r4_0)
  util.LoadParts(r0_21, r7_0("button_tapstart"), 300, 500)
  r0_0.play(r0_21)
  r17_0(r4_0)
  util.MakeFrame(r4_0)
end
local function r27_0()
  -- line: [327, 336] id: 22
  util.LoadParts(r4_0, r9_0("bg_title_01"), 0, 0)
  local r0_22 = util.MakeGroup(r4_0)
  util.LoadParts(r0_22, r7_0("button_tapstart"), 300, 500)
  r0_0.play(r0_22)
  r17_0(r4_0)
  util.MakeFrame(r4_0)
end
local function r28_0()
  -- line: [341, 366] id: 23
  local r0_23 = _G.ServerStatus.contpattern
  if r0_23 == nil then
    r27_0()
    return 
  end
  if r0_23 == 1 then
    r23_0()
  elseif r0_23 == 2 then
    r24_0()
  elseif r0_23 == 3 then
    r25_0()
  elseif r0_23 == 4 then
    r26_0()
  else
    r27_0()
  end
end
function r6_0()
  -- line: [399, 401] id: 26
  events.DeleteNamespace("topmenu")
end
return {
  new = function(r0_24)
    -- line: [374, 397] id: 24
    events.SetNamespace("topmenu")
    r4_0 = display.newGroup()
    server.GetStatus(function(r0_25)
      -- line: [379, 392] id: 25
      if _G.LoadingImage ~= nil then
        display.remove(_G.LoadingImage)
        _G.LoadingImage = nil
      end
      r28_0()
      r21_0()
      r19_0()
    end, nil)
    return r4_0
  end,
  Cleanup = r6_0,
}
