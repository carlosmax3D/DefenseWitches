-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("media")
local r1_0 = require("resource.char_define")
local function r2_0(r0_1)
  -- line: [8, 8] id: 1
  return "data/cutin/" .. r0_1 .. ".png"
end
local function r3_0(r0_2)
  -- line: [9, 9] id: 2
  return r2_0(r0_2 .. _G.UILanguage)
end
local r4_0 = {
  11,
  12,
  14,
  17,
  18,
  19
}
local r5_0 = nil
local r6_0 = nil
local r7_0 = nil
local r8_0 = nil
local r9_0 = nil
local function r10_0(r0_3)
  -- line: [22, 35] id: 3
  if r0_3 and r0_3.param then
    r0_0.playVideo("data/movie/" .. string.format("char_%2d.m4v", tonumber(r0_3.param)), false, movieClose)
  else
    DebugPrint("error on_short_movie_button")
    DebugPrint(r0_3)
  end
  return true
end
local function r11_0(r0_4)
  -- line: [40, 51] id: 4
  if r0_4 and r0_4.param then
    require("tool.handle_scheme").DirectForward(server.GetMovieUrl(tonumber(r0_4.param)))
  end
  return true
end
local function r12_0(r0_5)
  -- line: [56, 63] id: 5
  r5_0.isVisible = false
  r6_0.isVisible = true
  r7_0.isVisible = false
  r8_0.isVisible = true
  return true
end
local function r13_0(r0_6)
  -- line: [68, 75] id: 6
  r5_0.isVisible = true
  r6_0.isVisible = false
  r7_0.isVisible = true
  r8_0.isVisible = false
  return true
end
local function r14_0(r0_7, r1_7, r2_7)
  -- line: [80, 92] id: 7
  return util.LoadBtn({
    rtImg = r0_7,
    fname = r3_0("play_video_s_"),
    x = 700,
    y = 480,
    func = r10_0,
    param = r1_7,
    show = r2_7,
  })
end
local function r15_0(r0_8, r1_8, r2_8)
  -- line: [97, 109] id: 8
  return util.LoadBtn({
    rtImg = r0_8,
    fname = r3_0("btn_switching_"),
    x = 800,
    y = 5,
    func = r12_0,
    param = r1_8,
    show = r2_8,
  })
end
local function r16_0(r0_9, r1_9, r2_9)
  -- line: [114, 126] id: 9
  return util.LoadBtn({
    rtImg = r0_9,
    fname = r3_0("btn_switching_"),
    x = 800,
    y = 5,
    func = r13_0,
    param = r1_9,
    show = r2_9,
  })
end
local function r17_0(r0_10, r1_10, r2_10)
  -- line: [131, 143] id: 10
  return util.LoadBtn({
    rtImg = r0_10,
    fname = r3_0("play_video_l_"),
    x = 700,
    y = 560,
    func = r11_0,
    param = r1_10,
    show = r2_10,
  })
end
local function r18_0(r0_11, r1_11, r2_11)
  -- line: [148, 151] id: 11
  r17_0(r0_11, r1_11, r2_11)
end
local function r19_0(r0_12)
  -- line: [153, 290] id: 12
  local r1_12 = _G.UILanguage
  local function r2_12(r0_13)
    -- line: [155, 155] id: 13
    return "data/cutin/" .. r0_13 .. ".jpg"
  end
  local function r3_12(r0_14, r1_14)
    -- line: [156, 158] id: 14
    return string.format("data/cutin/%02d/%s.png", r0_14, r1_14)
  end
  local function r4_12(r0_15, r1_15)
    -- line: [159, 159] id: 15
    return r3_12(r0_15, r1_15 .. _G.UILanguage)
  end
  local function r5_12(r0_16)
    -- line: [160, 160] id: 16
    return r0_16 .. _G.UILanguage
  end
  local r6_12 = display.newGroup()
  util.MakeFrame(r6_12)
  local r7_12 = nil
  local r8_12 = nil
  local r9_12 = nil
  local r10_12 = nil
  local r11_12 = nil
  local r12_12 = nil
  util.LoadTileBG(r6_12, db.LoadTileData("cutin", "base"), "data/cutin")
  r10_12 = string.format("cutin%02d", r0_12)
  r11_12 = string.format("data/cutin/%02d", r0_12)
  local r13_12 = nil
  if r1_12 == "jp" then
    r13_12 = 200
  elseif r1_12 == "en" then
    r13_12 = 176
  end
  util.LoadParts(r6_12, r2_0("cutin_line"), 64, r13_12)
  util.LoadParts(r6_12, r2_0("cutin_line"), 64, 400)
  local r14_12 = {}
  local function r15_12(r0_17)
    -- line: [182, 224] id: 17
    sound.PlaySE(1)
    for r4_17, r5_17 in pairs(r14_12) do
      transit.Delete(r5_17)
    end
    r14_12 = {}
    events.DeleteNamespace("cutin")
    display.remove(r6_12)
    if hint.CheckHintRelease(_G.MapSelect, _G.StageSelect) then
      if hint.CheckHintReaded(_G.MapSelect, _G.StageSelect) then
        util.setActivityIndicator(true)
        util.ChangeScene({
          scene = "game",
          efx = "fade",
        })
      else
        local r3_17 = _G.ServerStatus.run2firsthint
        if _G.MapSelect == 1 and _G.StageSelect == 1 and r3_17 == 0 then
          util.setActivityIndicator(true)
          util.ChangeScene({
            scene = "game",
            efx = "fade",
          })
        else
          util.setActivityIndicator(false)
          util.ChangeScene({
            scene = "hint",
            efx = "fade",
            param = {
              no = 1,
              change_no = 1,
              wno = _G.MapSelect,
              sno = _G.StageSelect,
            },
          })
        end
      end
    else
      util.setActivityIndicator(true)
      util.ChangeScene({
        scene = "game",
        efx = "fade",
      })
    end
    return true
  end
  function r6_12.touch(r0_18, r1_18)
    -- line: [226, 246] id: 18
    if r1_18.phase == "ended" then
      local r2_18 = false
      for r6_18, r7_18 in pairs(r14_12) do
        r2_18 = true
        transit.Cancel(r7_18)
      end
      if r2_18 then
        r14_12 = {}
        r8_12.isVisible = true
        if r12_12 then
          r12_12.isVisible = true
        end
      else
        r15_12(nil)
      end
    end
    return true
  end
  r6_12:addEventListener("touch", r6_12)
  table.insert(r14_12, transit.Register(util.LoadTileParts(r6_12, _G.Width, 80, db.LoadTileData(r10_12, r5_12("name_")), r11_12), {
    time = 1000,
    x = 64,
    delay = 100,
    transition = easing.outExpo,
  }))
  if r1_12 == "jp" then
    r13_12 = 208
  elseif r1_12 == "en" then
    r13_12 = 184
  end
  table.insert(r14_12, transit.Register(util.LoadTileParts(r6_12, _G.Width, r13_12, db.LoadTileData(r10_12, r5_12("comment_")), r11_12), {
    time = 1000,
    x = 64,
    delay = 200,
    transition = easing.outExpo,
  }))
  table.insert(r14_12, transit.Register(util.LoadTileParts(r6_12, _G.Width, 408, db.LoadTileData(r10_12, "status_jp"), r11_12), {
    time = 1000,
    x = 64,
    delay = 300,
    transition = easing.outExpo,
    onComplete = function(r0_19)
      -- line: [269, 274] id: 19
      r8_12.isVisible = true
      if r12_12 then
        r12_12.isVisible = true
      end
    end,
  }))
  table.insert(r14_12, transit.Register(util.LoadTileParts(r6_12, -896, 0, db.LoadTileData(r10_12, "chara"), r11_12), {
    time = 1000,
    x = 240,
    transition = easing.outExpo,
  }))
  r8_12 = util.LoadBtn({
    rtImg = r6_12,
    fname = r2_0("cutin_button"),
    x = 368,
    y = 560,
    func = r15_12,
    show = false,
  })
  r12_12 = r17_0(r6_12, r0_12, false)
  return r6_12
end
local r21_0 = nil
local r22_0 = 30
local r23_0 = nil
local r24_0 = nil
local r25_0 = nil
local function r26_0(r0_22, r1_22)
  -- line: [431, 472] id: 22
  local r2_22 = 240
  if r0_22.sid == 19 then
    r2_22 = 150
  end
  local r3_22 = r1_22.phase
  if r3_22 == "began" then
    if r21_0 then
      transit.Delete(r21_0)
      r21_0 = nil
    end
    r0_22.sx = r1_22.x
    display.getCurrentStage():setFocus(r0_22)
  elseif r3_22 == "moved" then
    local r4_22 = r0_22.sx
    if r4_22 == nil then
      r4_22 = r1_22.x
      r0_22.sx = r4_22
    end
    r0_22.x = r2_22 + r1_22.x - r4_22
  elseif r3_22 == "ended" or r3_22 == "canceled" then
    if r25_0 ~= nil and 0 < r25_0 and _G.GameData.voice then
      local r4_22 = math.random(1, r25_0)
      sound.StopVoice(r22_0)
      local r5_22 = sound.GetCharVoiceFilename(r23_0, r4_22)
      if r5_22 ~= nil then
        sound.PlayVoice(r5_22, r22_0)
      end
    end
    if r21_0 then
      transit.Delete(r21_0)
    end
    r21_0 = transit.Register(r0_22, {
      time = 250,
      x = r2_22,
      transition = easing.inExpo,
    })
    display.getCurrentStage():setFocus(nil)
  end
end
local function r27_0(r0_23, r1_23, r2_23)
  -- line: [475, 522] id: 23
  local r3_23 = r1_0.Evo1CharId[r0_23]
  if r3_23 == nil then
    return true
  end
  if db.IsLockSummonData(_G.UserID, r3_23) == 1 then
    return true
  end
  local r4_23 = _G.UILanguage
  local function r5_23(r0_24)
    -- line: [486, 486] id: 24
    return "data/cutin/" .. r0_24 .. ".png"
  end
  local function r6_23(r0_25)
    -- line: [487, 487] id: 25
    return r0_25 .. _G.UILanguage
  end
  local r7_23 = nil
  local r8_23 = nil
  local r9_23 = nil
  r8_23 = string.format("cutin%02d", r3_23)
  r9_23 = string.format("data/cutin/%02d", r3_23)
  local r10_23 = nil
  if r4_23 == "jp" then
    r10_23 = 200
  elseif r4_23 == "en" then
    r10_23 = 176
  end
  util.LoadParts(r2_23, r5_23("cutin_line"), 64, r10_23)
  util.LoadParts(r2_23, r5_23("cutin_line"), 64, 400)
  r7_23 = db.LoadTileData(r8_23, "chara")
  local r11_23 = 240
  if r3_23 == 19 then
    r11_23 = 150
  end
  r6_0 = util.LoadTileParts(r2_23, r11_23, 0, r7_23, r9_23)
  r6_0.evo_sid = r3_23
  r6_0.touch = r26_0
  r21_0 = nil
  r6_0:addEventListener("touch", r6_0)
  r6_0.isVisible = false
  r7_0 = r15_0(r1_23, r3_23, true)
  r8_0 = r16_0(r1_23, r0_23, true)
  r7_0.isVisible = true
  r8_0.isVisible = false
end
return {
  new = function(r0_20)
    -- line: [292, 425] id: 20
    local r1_20 = _G.MapSelect
    local r2_20 = _G.StageSelect
    local r3_20 = _G.UserID
    local r4_20 = nil
    local r5_20 = nil
    if r1_20 == 1 and r2_20 == 1 then
      r4_20 = 1
    elseif r1_20 == 1 and r2_20 == 3 then
      r4_20 = 2
    elseif r1_20 == 1 and r2_20 == 6 then
      r4_20 = 3
    elseif r1_20 == 2 and r2_20 == 1 then
      r4_20 = 4
    elseif r1_20 == 2 and r2_20 == 6 then
      r4_20 = 5
    elseif r1_20 == 4 and r2_20 == 1 then
      r4_20 = 6
    elseif r1_20 == 5 and r2_20 == 1 then
      r4_20 = 7
    elseif r1_20 == 6 and r2_20 == 1 then
      r4_20 = 8
    elseif r1_20 == 6 and r2_20 == 6 then
      r4_20 = 13
    elseif r1_20 == 7 and r2_20 == 1 then
      r4_20 = 9
    elseif r1_20 == 8 and r2_20 == 1 then
      r4_20 = 10
    elseif r1_20 == 3 and r2_20 == 1 then
      r5_20 = 1
    elseif r1_20 == 3 and r2_20 == 5 then
      r5_20 = 2
    elseif r1_20 == 3 and r2_20 == 9 then
      r5_20 = 3
    elseif r1_20 == 4 and r2_20 == 5 then
      r5_20 = 4
    elseif r1_20 == 4 and r2_20 == 9 then
      r5_20 = 5
    elseif r1_20 == 5 and r2_20 == 5 then
      r5_20 = 6
    elseif r1_20 == 6 and r2_20 == 4 then
      r5_20 = 7
    elseif r1_20 == 7 and r2_20 == 3 then
      r5_20 = 8
    elseif r1_20 == 7 and r2_20 == 7 then
      r5_20 = 13
    elseif r1_20 == 8 and r2_20 == 3 then
      r5_20 = 9
    elseif r1_20 == 9 and r2_20 == 3 then
      r5_20 = 10
    end
    local r6_20 = nil
    local r7_20 = nil
    r6_20, r7_20 = db.LoadSummonData(r3_20)
    if #r6_20 < 1 then
      db.InitSummonData(r3_20)
      r6_20, r7_20 = db.LoadSummonData(r3_20)
    end
    if r4_20 and r6_20[r4_20] == 3 then
      db.UnlockSummonData(r3_20, r4_20)
      r6_20[r4_20] = 1
    end
    if r5_20 and db.GetL4SummonData(r3_20, r5_20) then
      db.UnlockL4SummonData(r3_20, r5_20)
    end
    _G.BingoManager.updateUserBingoData(_G.BingoManagerClass.MISSION_TYPE.UNLOCK_CHAR(), nil)
    local r8_20 = _G.IsSimulator
    if r8_20 and r4_20 then
      r8_20 = string.format("cutin%02d", r4_20)
      if db.LoadTileData(r8_20, "chara") == nil then
        DebugPrint("warning:!!!!!!!!!! cutin " .. r4_20 .. " is not found!!!!!!!!!!!")
        r4_20 = nil
      end
    end
    if r4_20 then
      events.SetNamespace("cutin")
      util.setActivityIndicator(false)
      return r19_0(r4_20)
    else
      Runtime:addEventListener("enterFrame", function(r0_21)
        -- line: [381, 417] id: 21
        if director:ChangingScene() then
          return 
        end
        if hint.CheckHintRelease(_G.MapSelect, _G.StageSelect) then
          if hint.CheckHintReaded(_G.MapSelect, _G.StageSelect) then
            Runtime:removeEventListener("enterFrame", r8_20)
            util.ChangeScene({
              scene = "game",
              efx = "fade",
            })
          elseif _G.MapSelect == 1 and _G.StageSelect == 2 and _G.ServerStatus.hint12 ~= 1 then
            Runtime:removeEventListener("enterFrame", r8_20)
            util.ChangeScene({
              scene = "game",
              efx = "fade",
            })
          else
            local r3_21 = {
              no = 1,
              change_no = 1,
              wno = _G.MapSelect,
              sno = _G.StageSelect,
            }
            Runtime:removeEventListener("enterFrame", r8_20)
            util.setActivityIndicator(false)
            require("scene.hint").new(r3_21)
          end
        else
          Runtime:removeEventListener("enterFrame", r8_20)
          util.ChangeScene({
            scene = "game",
            efx = "fade",
          })
        end
      end)
      local r9_20 = display.newGroup()
      display.newRect(r9_20, 0, 0, _G.Width, _G.Height):setFillColor(0, 0, 0)
      return r9_20
      -- close: r8_20
    end
  end,
  View = function(r0_26, r1_26, r2_26)
    -- line: [524, 646] id: 26
    sound.InitVoice()
    local r3_26 = _G.UILanguage
    local function r4_26(r0_27)
      -- line: [527, 527] id: 27
      return "data/cutin/" .. r0_27 .. ".png"
    end
    local function r5_26(r0_28)
      -- line: [528, 528] id: 28
      return r0_28 .. _G.UILanguage
    end
    local r6_26 = nil
    local r7_26 = nil
    local r8_26 = nil
    r7_26 = string.format("cutin%02d", r1_26)
    r8_26 = string.format("data/cutin/%02d", r1_26)
    local r9_26 = nil
    if r3_26 == "jp" then
      r9_26 = 185
    elseif r3_26 == "en" then
      r9_26 = 185
    end
    util.LoadParts(r0_26, r4_26("cutin_line"), 64, r9_26)
    util.LoadParts(r0_26, r4_26("cutin_line"), 64, 415)
    local r10_26 = display.newGroup()
    r0_26:insert(r10_26)
    r6_26 = db.LoadTileData(r7_26, "chara")
    local r11_26 = 240
    if r1_26 == 19 then
      r11_26 = 150
    end
    r5_0 = util.LoadTileParts(r10_26, r11_26, 0, r6_26, r8_26)
    r5_0.sid = r1_26
    r5_0.touch = r26_0
    r21_0 = nil
    r5_0:addEventListener("touch", r5_0)
    r6_26 = db.LoadTileData(r7_26, r5_26("name_"))
    if r6_26 then
      util.LoadTileParts(r10_26, 64, 70, r6_26, r8_26)
      if r3_26 == "jp" then
        r9_26 = 195
      elseif r3_26 == "en" then
        r9_26 = 195
      end
      util.LoadTileParts(r10_26, 64, r9_26, db.LoadTileData(r7_26, r5_26("comment_")), r8_26)
      util.LoadTileParts(r10_26, 64, 415, db.LoadTileData(r7_26, "status_jp"), r8_26)
      if r1_26 ~= 16 then
        r23_0 = sound.GetCharVoicePath(r1_26, _G.GameData.language)
        if r1_26 == 13 then
          r25_0 = 5
        elseif r1_26 == 11 then
          r25_0 = 6
        elseif r1_26 == 14 then
          r25_0 = 7
        else
          r25_0 = 5
        end
      else
        r23_0 = sound.GetCharBomVoicePath(r1_26, _G.GameData.language)
        r25_0 = 5
      end
    else
      local r12_26 = display.newText(r0_26, string.format("%d", r1_26), 0, 0, native.systemFontBold, 128)
      r12_26:setReferencePoint(display.CenterReferencePoint)
      r12_26.x = _G.Width / 2
      r12_26.y = _G.Height / 2
    end
    r27_0(r1_26, r0_26, r10_26)
    util.LoadBtn({
      rtImg = r0_26,
      fname = r4_26("cutin_button"),
      x = 368,
      y = 560,
      func = r2_26,
    })
    r18_0(r0_26, r1_26, true)
    r9_0 = display.newGroup()
    local r12_26 = util.MakeText4({
      rtImg = r9_0,
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
    local r13_26 = util.MakeText4({
      rtImg = r9_0,
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
      msg = db.GetMessage(r1_0.VoiceArtistName[r1_26]),
    })
    r12_26:setReferencePoint(display.TopLeftReferencePoint)
    r13_26:setReferencePoint(display.TopLeftReferencePoint)
    r12_26.x = 0
    r12_26.y = 0
    r13_26.x = r12_26.x + r12_26.width
    r13_26.y = 0
    r0_26:insert(r9_0)
    r9_0:setReferencePoint(display.BottomRightReferencePoint)
    r9_0.x = 920
    r9_0.y = 550
  end,
  ViewCleanup = function()
    -- line: [648, 665] id: 29
    display.remove(r6_0)
    if r9_0 then
      display.remove(r9_0)
      r9_0 = nil
    end
    if r23_0 then
      r23_0 = nil
    end
    if r25_0 then
      r25_0 = 0
    end
    sound.CleanupVoice()
    if r21_0 then
      transit.Delete(r21_0)
      r21_0 = nil
    end
  end,
}
