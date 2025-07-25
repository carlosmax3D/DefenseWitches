-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("tool.crystal")
local r1_0 = require("json")
local r2_0 = require("scene.help")
local r3_0 = require("resource.char_define")
local r4_0 = "data/help/witches/"
local function r5_0(r0_1)
  -- line: [12, 12] id: 1
  return r4_0 .. r0_1 .. ".png"
end
local function r6_0(r0_2)
  -- line: [13, 13] id: 2
  return r5_0(r0_2 .. _G.UILanguage)
end
local function r7_0(r0_3)
  -- line: [14, 14] id: 3
  return "data/stage/" .. r0_3 .. ".png"
end
local function r8_0(r0_4)
  -- line: [15, 15] id: 4
  return r7_0(r0_4 .. _G.UILanguage)
end
local r9_0 = nil
local r10_0 = nil
local r11_0 = nil
local r12_0 = nil
local r13_0 = nil
local r14_0 = nil
local r15_0 = nil
local r16_0 = {
  {
    1,
    1
  },
  {
    1,
    3
  },
  {
    1,
    6
  },
  {
    2,
    1
  },
  {
    2,
    6
  },
  {
    4,
    1
  },
  {
    5,
    1
  },
  {
    6,
    1
  },
  {
    7,
    1
  },
  {
    8,
    1
  },
  {
    0,
    0
  },
  {
    0,
    0
  },
  {
    6,
    6
  },
  {
    0,
    0
  },
  {
    1,
    1
  },
  {
    0,
    0
  },
  {
    0,
    0
  },
  {
    0,
    0
  },
  {
    0,
    0
  },
  {
    0,
    0
  },
  {
    0,
    0
  },
  {
    0,
    0
  },
  {
    0,
    0
  },
  {
    0,
    0
  }
}
local r17_0 = {
  {
    -1,
    -1
  },
  {
    25,
    800
  },
  {
    26,
    800
  },
  {
    27,
    800
  },
  {
    28,
    800
  },
  {
    29,
    4400
  },
  {
    30,
    4400
  },
  {
    31,
    4400
  },
  {
    32,
    4400
  },
  {
    33,
    4400
  },
  {
    34,
    8000
  },
  {
    35,
    8000
  },
  {
    36,
    4400
  },
  {
    37,
    8000
  },
  {
    -1,
    -1
  },
  {
    -1,
    -1
  },
  {
    154,
    8000
  },
  {
    157,
    8000
  },
  {
    159,
    8000
  },
  {
    162,
    8000
  },
  {
    162,
    8000
  },
  {
    164,
    8000
  },
  {
    166,
    8000
  },
  {
    168,
    9000
  }
}
local r18_0 = {
  db.GetMessage(154),
  db.GetMessage(155),
  db.GetMessage(156)
}
local function r19_0(r0_5, r1_5)
  -- line: [65, 82] id: 5
  local r2_5 = nil	-- notice: implicit variable refs by block#[3]
  if type(r1_5) == "string" then
    r2_5 = r1_5
  else
    r2_5 = util.Num2Column(r1_5)
  end
  local r3_5 = display.newGroup()
  local r4_5 = nil
  display.newText(r3_5, r2_5, 1, 1, native.systemFontBold, 40):setFillColor(0, 0, 0)
  display.newText(r3_5, r2_5, 0, 0, native.systemFontBold, 40):setFillColor(255, 225, 76)
  r0_5:insert(r3_5)
  return r3_5
end
local function r20_0(r0_6)
  -- line: [84, 110] id: 6
  if r14_0 == nil then
    return 
  end
  if server.CheckError(r0_6) then
    server.NetworkError(35)
  else
    local r1_6 = r1_0.decode(r0_6.response)
    if r1_6.status == 0 then
      local r2_6 = r1_6.coin
      local r3_6 = r14_0.x
      local r4_6 = r14_0.y
      local r5_6 = r14_0.parent
      if r14_0 then
        display.remove(r14_0)
      end
      r14_0 = nil
      local r6_6 = r19_0(r5_6, util.ConvertDisplayCrystal(r2_6))
      r6_6:setReferencePoint(display.TopRightReferencePoint)
      r6_6.x = r3_6
      r6_6.y = r4_6
      r14_0 = r6_6
      r0_0.ShowCoinInfo(r2_6)
    else
      server.ServerError(r1_6.status)
    end
  end
end
local function r21_0()
  -- line: [112, 128] id: 7
  if r14_0 then
    local r0_7 = r14_0.x
    local r1_7 = r14_0.y
    local r2_7 = r14_0.parent
    if r14_0 then
      display.remove(r14_0)
    end
    r14_0 = nil
    local r3_7 = r19_0(r2_7, "Loading")
    r3_7:setReferencePoint(display.TopRightReferencePoint)
    r3_7.x = r0_7
    r3_7.y = r1_7
    r14_0 = r3_7
    server.GetCoin(_G.UserToken, r20_0)
  end
end
local function r22_0(r0_8, r1_8, r2_8)
  -- line: [130, 182] id: 8
  if _G.UserToken == nil then
    server.NetworkError(35, nil)
    return 
  end
  local r3_8 = _G.UserID
  if _G.IsSimulator then
    db.UnlockSummonData(r3_8, r0_8)
    db.UnlockL4SummonData(r3_8, r0_8)
    kpi.Unlock(r0_8, r1_8)
    _G.metrics.crystal_buy_character(r0_8, r1_8)
    if r0_8 == r3_0.CharId.Jill then
      r0_8 = r0_8 - 1
    end
    if r0_8 == r3_0.CharId.Yuiko then
      r0_8 = r0_8 - 2
    end
    r2_0.ViewHelp("pass", r0_8)
    _G.BingoManager.updateUserBingoData(_G.BingoManagerClass.MISSION_TYPE.UNLOCK_CHAR(), nil)
  else
    util.setActivityIndicator(true)
    server.UseCoin(_G.UserToken, r2_8, 1, function(r0_9)
      -- line: [154, 180] id: 9
      util.setActivityIndicator(false)
      if server.CheckError(r0_9) then
        server.NetworkError()
      else
        local r1_9 = r1_0.decode(r0_9.response)
        if r1_9.status == 0 then
          db.UnlockSummonData(r3_8, r0_8)
          db.UnlockL4SummonData(r3_8, r0_8)
          kpi.Unlock(r0_8, r1_8)
          _G.metrics.crystal_buy_character(r0_8, r1_8)
          if r0_8 == r3_0.CharId.Jill then
            r0_8 = r0_8 - 1
          end
          if r0_8 == r3_0.CharId.Yuiko then
            r0_8 = r0_8 - 2
          end
          r2_0.ViewHelp("pass", r0_8)
          _G.BingoManager.updateUserBingoData(_G.BingoManagerClass.MISSION_TYPE.UNLOCK_CHAR(), nil)
        else
          server.ServerError(r1_9.status)
        end
      end
    end)
  end
end
local function r23_0(r0_10)
  -- line: [184, 205] id: 10
  sound.PlaySE(1)
  local r1_10 = r17_0[r0_10][1]
  local r2_10 = r17_0[r0_10][2]
  dialog.Open(r15_0, 13, {
    string.format(db.GetMessage(14), util.ConvertDisplayCrystal(r2_10)),
    15
  }, {
    function(r0_11)
      -- line: [189, 194] id: 11
      sound.PlaySE(1)
      dialog.Close()
      r22_0(r0_10, r2_10, r1_10)
      return true
    end,
    function(r0_12)
      -- line: [195, 199] id: 12
      sound.PlaySE(2)
      dialog.Close()
      return true
    end
  })
end
local function r24_0(r0_13)
  -- line: [207, 212] id: 13
  sound.PlaySE(1)
  r14_0.isVisible = false
  r0_0.Open(r15_0, {
    r21_0,
    nil
  })
  return true
end
local function r25_0(r0_14, r1_14)
  -- line: [214, 232] id: 14
  local r2_14 = display.newGroup()
  util.LoadParts(r2_14, r5_0("witches_crystal"), 0, 0)
  local r3_14 = nil
  r3_14 = util.MakeText(30, {
    255,
    225,
    76
  }, {
    0,
    0,
    0
  }, tostring(r1_14))
  r2_14:insert(r3_14)
  r3_14:setReferencePoint(display.TopLeftReferencePoint)
  r3_14.x = 28
  r3_14.y = 1
  r0_14:insert(r2_14)
  r2_14:setReferencePoint(display.CenterReferencePoint)
  r2_14.x = 80
  r2_14.y = 80
  return r2_14
end
local function r26_0(r0_15, r1_15, r2_15)
  -- line: [234, 258] id: 15
  local r4_15 = r17_0[r1_15][2]
  if r17_0[r1_15][1] == -1 and r2_15 == 3 then
    r2_15 = -1
  end
  if r2_15 == -1 and r1_15 ~= 16 then
    util.MakeCenterText(r0_15, 18, {
      16,
      72,
      128,
      20
    }, {
      242,
      230,
      242
    }, {
      0,
      0,
      0
    }, "Coming soon!")
  elseif r2_15 == 3 then
    r25_0(r0_15, util.ConvertDisplayCrystal(r4_15))
    util.MakeCenterText(r0_15, 20, {
      16,
      100,
      128,
      20
    }, {
      255,
      234,
      0
    }, {
      0,
      0,
      0
    }, r18_0[1])
    util.MakeCenterText(r0_15, 20, {
      16,
      120,
      128,
      20
    }, {
      255,
      170,
      0
    }, {
      0,
      0,
      0
    }, r18_0[2])
  end
end
local function r27_0(r0_16)
  -- line: [260, 269] id: 16
  local r1_16 = r16_0[r0_16]
  local r2_16 = r1_16[1]
  local r3_16 = r1_16[2]
  if r2_16 == 0 and r3_16 == 0 then
    return r18_0[3]
  else
    return string.format("World %d-%d", r2_16, r3_16)
  end
end
local function r28_0(r0_17, r1_17)
  -- line: [271, 338] id: 17
  local r4_17 = display.newGroup()
  if r1_17 then
    local r2_17 = db.GetMessage(81)
    util.MakeCenterText(r4_17, 16, {
      0,
      286,
      160,
      18
    }, {
      64,
      48,
      25
    }, {
      255,
      246,
      229
    }, r2_17)
    if r0_17 == 16 then
      r2_17 = db.GetMessage(269)
    elseif r0_17 == 17 then
      r2_17 = db.GetMessage(288)
    elseif r0_17 == 18 then
      r2_17 = db.GetMessage(346)
    elseif r0_17 == 19 then
      r2_17 = db.GetMessage(352)
    elseif r0_17 == 21 then
      r2_17 = db.GetMessage(481)
    elseif r0_17 == 22 then
      r2_17 = db.GetMessage(485)
    elseif r0_17 == 23 then
      r2_17 = db.GetMessage(497)
    elseif r0_17 == 24 then
      r2_17 = db.GetMessage(501)
    else
      r2_17 = db.GetMessage(140 + r0_17 - 1)
    end
    util.MakeCenterText(r4_17, 16, {
      0,
      304,
      160,
      18
    }, {
      99,
      85,
      71
    }, {
      255,
      246,
      229
    }, r2_17)
  else
    util.MakeCenterText(r4_17, 16, {
      0,
      277,
      160,
      18
    }, {
      64,
      48,
      25
    }, {
      255,
      246,
      229
    }, db.GetMessage(82))
    local r2_17 = r27_0(r0_17)
    util.MakeCenterText(r4_17, 16, {
      0,
      295,
      160,
      18
    }, {
      166,
      50,
      50
    }, {
      255,
      246,
      229
    }, r2_17)
    if r0_17 == 17 then
      r2_17 = db.GetMessage(288)
    elseif r0_17 == 18 then
      r2_17 = db.GetMessage(346)
    elseif r0_17 == 19 then
      r2_17 = db.GetMessage(352)
    elseif r0_17 == 21 then
      r2_17 = db.GetMessage(481)
    elseif r0_17 == 22 then
      r2_17 = db.GetMessage(485)
    elseif r0_17 == 23 then
      r2_17 = db.GetMessage(497)
    elseif r0_17 == 24 then
      r2_17 = db.GetMessage(501)
    else
      r2_17 = db.GetMessage(140 + r0_17 - 1)
    end
    util.MakeCenterText(r4_17, 16, {
      0,
      313,
      160,
      18
    }, {
      99,
      85,
      71
    }, {
      255,
      246,
      229
    }, r2_17)
  end
  return r4_17
end
local function r29_0(r0_18, r1_18, r2_18)
  -- line: [340, 371] id: 18
  local r3_18 = r0_18
  if r3_18 == 16 then
    r1_18 = true
  end
  local r4_18 = display.newGroup()
  local r5_18 = nil
  local r6_18 = nil
  r6_18 = r3_0.GetCharName(r0_18)
  util.LoadParts(r4_18, r5_0("witches_name_plate"), 0, 0)
  util.MakeCenterText(r4_18, 20, {
    16,
    16,
    128,
    26
  }, {
    26,
    19,
    10
  }, {
    255,
    246,
    229
  }, r6_18)
  if r1_18 then
    r6_18 = string.format("witches_chara_unlock%02d", r3_18)
  else
    r6_18 = string.format("witches_chara_locked%02d", r3_18)
  end
  util.LoadParts(r4_18, r5_0(r6_18), 0, 48)
  r26_0(r4_18, r0_18, r2_18)
  r4_18:insert(r28_0(r0_18, r1_18))
  util.LoadParts(r4_18, r5_0("witches_thumb_bottom"), 0, 336)
  return r4_18
end
local function r30_0()
  -- line: [373, 378] id: 19
  for r3_19, r4_19 in pairs(r12_0) do
    r4_19[1].isVisible = r3_19 == r13_0
    r4_19[2].isVisible = r3_19 ~= r13_0
  end
end
local function r31_0(r0_20)
  -- line: [380, 384] id: 20
  sound.PlaySE(2)
  r2_0.ViewHelp("index")
  return true
end
local function r32_0(r0_21)
  -- line: [386, 401] id: 21
  local r1_21 = r13_0 + r0_21
  if r1_21 < 1 or 5 < r1_21 then
    sound.PlaySE(2)
    return 
  end
  sound.PlaySE(1)
  r13_0 = r1_21
  r10_0.tween = transition.to(r10_0, {
    time = 500,
    x = (r13_0 - 1) * -_G.Width,
    transition = easing.outExpo,
    onComplete = function(r0_22)
      -- line: [398, 398] id: 22
      r30_0()
    end,
  })
  return true
end
local function r33_0(r0_23)
  -- line: [403, 405] id: 23
  return r32_0(-1)
end
local function r34_0(r0_24)
  -- line: [407, 409] id: 24
  return r32_0(1)
end
local function r35_0(r0_25, r1_25)
  -- line: [411, 424] id: 25
  for r6_25, r7_25 in pairs(r11_0) do
    local r2_25 = r7_25.stageBounds
    if r2_25.xMin <= r0_25 and r0_25 <= r2_25.xMax and r2_25.yMin <= r1_25 and r1_25 <= r2_25.yMax then
      if r7_25.disable then
        return nil
      else
        return r7_25
      end
    end
  end
end
local function r36_0()
  -- line: [426, 431] id: 26
  sound.PlaySE(1)
  util.ChangeScene({
    prev = r9_0,
    scene = "shop.shop_view",
    efx = "overFromBottom",
    param = {
      closeScene = "help",
    },
  })
end
local function r37_0(r0_27, r1_27)
  -- line: [433, 507] id: 27
  local r2_27 = r1_27.phase
  if r2_27 == "began" then
    r0_27.start_pos = r1_27.x
    r0_27.prev_pos = r1_27.x
    r0_27.delta = 0
    r0_27.velocity = 0
    if r0_27.tween then
      transition.cancel(r0_27.tween)
      r0_27.tween = nil
    end
    r0_27.select_btn = r35_0(r1_27.x, r1_27.y)
    if r0_27.select_btn == nil then
      r0_27.start_id = nil
    else
      r0_27.start_id = r0_27.select_btn.id
    end
    events.Disable(r0_27.scroll_ev, true)
    r0_27.prev_time = 0
    r0_27.prev_x = 0
    events.Disable(r0_27.track_ev, false)
    display.getCurrentStage():setFocus(r0_27)
    r0_27.isFocus = true
  elseif r0_27.isFocus then
    if r2_27 == "moved" then
      local r3_27 = -_G.Width * 4
      r0_27.delta = r1_27.x - r0_27.prev_pos
      r0_27.prev_pos = r1_27.x
      if 0 < r0_27.x or r0_27.x < r3_27 then
        r0_27.x = r0_27.x + r0_27.delta / 3
      else
        r0_27.x = r0_27.x + r0_27.delta
      end
    elseif r2_27 == "ended" or r2_27 == "canceled" then
      r0_27.last_time = r1_27.time
      events.Disable(r0_27.scroll_ev, false)
      events.Disable(r0_27.track_ev, true)
      display.getCurrentStage():setFocus(nil)
      r0_27.isFocus = false
      local r3_27 = r0_27.delta
      if -1 <= r3_27 and r3_27 <= 1 then
        local r4_27 = r35_0(r1_27.x, r1_27.y)
        local r5_27 = nil
        local r6_27 = nil
        if r4_27 then
          r5_27 = r4_27.id
          r6_27 = r4_27.buy
        else
          r5_27 = nil
          r6_27 = false
        end
        if r5_27 and r5_27 == r0_27.start_id then
          sound.PlaySE(1)
          if r6_27 then
            if r5_27 ~= r3_0.CharId.Yung then
              r23_0(r5_27)
            else
              r36_0()
            end
          else
            r2_0.ViewHelp("chara", r5_27)
          end
        end
      end
    end
  end
  return true
end
local function r38_0(r0_28)
  -- line: [509, 525] id: 28
  r0_28.velocity = 0
  events.Disable(r0_28.scroll_ev, true)
  local r2_28 = math.round(-r0_28.x / _G.Width)
  if r2_28 < 0 then
    r2_28 = 0
  end
  if r2_28 > 4 then
    r2_28 = 4
  end
  local r3_28 = r2_28 * -_G.Width
  r13_0 = r2_28 + 1
  if r0_28.tween then
    transition.cancel(r0_28.tween)
  end
  r0_28.tween = transition.to(r0_28, {
    time = 500,
    x = r3_28,
    transition = easing.outExpo,
    onComplete = function(r0_29)
      -- line: [524, 524] id: 29
      r30_0()
    end,
  })
end
local function r39_0(r0_30, r1_30, r2_30)
  -- line: [527, 551] id: 30
  local r4_30 = 0.9
  local r5_30 = r0_30.ev.time - r1_30.last_time
  r1_30.last_time = r1_30.last_time + r5_30
  if r1_30.x == nil then
    r1_30.x = 0
  end
  if math.abs(r1_30.velocity) < 0.1 then
    r38_0(r1_30)
  end
  r1_30.velocity = r1_30.velocity * r4_30
  r1_30.x = math.floor(r1_30.x + r1_30.velocity * r5_30)
  local r6_30 = -_G.Width * 2
  if r1_30.x > 0 then
    r38_0(r1_30)
  elseif r1_30.x < r6_30 and r6_30 < 0 then
    r38_0(r1_30)
  elseif r1_30.x < r6_30 then
    r38_0(r1_30)
  end
  return true
end
local function r40_0(r0_31, r1_31, r2_31)
  -- line: [553, 569] id: 31
  local r4_31 = r0_31.ev.time - r1_31.prev_time
  r1_31.prev_time = r1_31.prev_time + r4_31
  if r1_31.prev_x then
    if r4_31 > 0 then
      r1_31.velocity = (r1_31.x - r1_31.prev_x) / r4_31 * 0.5
    else
      r1_31.velocity = 0
    end
  end
  r1_31.prev_x = r1_31.x
  return true
end
function r9_0()
  -- line: [716, 733] id: 35
  if r15_0 then
    display.remove(r15_0)
    r15_0 = nil
  end
  if r10_0 then
    local r0_35 = nil
    r0_35 = r10_0.scroll_ev
    if r0_35 then
      events.Delete(r0_35)
    end
    r0_35 = r10_0.track_ev
    if r0_35 then
      events.Delete(r0_35)
    end
    r10_0 = nil
  end
  if r14_0 then
    display.remove(r14_0)
    r14_0 = nil
  end
end
return {
  Load = function(r0_32, r1_32)
    -- line: [571, 714] id: 32
    local function r2_32(r0_33)
      -- line: [572, 572] id: 33
      return "data/powerup/" .. r0_33 .. ".png"
    end
    local function r3_32(r0_34)
      -- line: [573, 573] id: 34
      return r2_32(r0_34 .. _G.UILanguage)
    end
    local r4_32 = _G.UserID
    local r5_32, r6_32 = db.LoadSummonData(r4_32)
    if #r5_32 < 1 then
      db.InitSummonData(r4_32)
      r5_32, r6_32 = db.LoadSummonData(r4_32)
    end
    local r7_32 = display.newGroup()
    local r8_32 = nil
    util.LoadParts(r7_32, r6_0("witches_title_"), 192, 16)
    local r9_32 = 80
    local r10_32 = nil
    r10_0 = display.newGroup()
    r10_0.top = 80
    r10_0.left = 0
    r10_0.right = _G.Width * 3
    r10_0.bottom = 424
    r10_0.x = 0
    r10_0.y = 80
    r7_32:insert(r10_0)
    for r14_32 = 0, _G.Width * 2, _G.Width do
      for r18_32 = r14_32 + 236, r14_32 + 716, 160 do
        util.LoadParts(r10_0, r5_0("witches_line"), r18_32, 4)
      end
    end
    r8_32 = display.newRect(r10_0, 0, 0, _G.Width * 3, 344)
    r8_32:setFillColor(0, 0, 0)
    r8_32.alpha = 0.01
    r11_0 = {}
    local r11_32 = 0
    for r15_32 = 1, 24, 1 do
      if r15_32 ~= 15 and r15_32 ~= 20 then
        r11_32 = r11_32 + 1
        local r16_32 = r5_32[r15_32]
        r8_32 = r29_0(r15_32, r16_32 ~= 3, r5_32[r15_32])
        r8_32:setReferencePoint(display.TopLeftReferencePoint)
        r8_32.x = r9_32
        r8_32.y = 0
        r10_0:insert(r8_32)
        r9_32 = r9_32 + 160
        if r11_32 == 5 or r11_32 == 10 or r11_32 == 15 or r11_32 == 20 then
          r9_32 = r9_32 + 160
        end
        if r15_32 ~= 1 and r15_32 ~= 16 then
          r16_32 = r17_0[r15_32][1] == -1
        else
          goto label_150	-- block#21 is visited secondly
        end
        r8_32.disable = r16_32
        r8_32.buy = r5_32[r15_32] == 3
        if r15_32 == 16 then
          r8_32.buy = false
        end
        r8_32.id = r15_32
        table.insert(r11_0, r8_32)
      end
    end
    r10_0.scroll_ev = events.Register(r39_0, r10_0, 0, true)
    r10_0.track_ev = events.Register(r40_0, r10_0, 0, true)
    r10_0.touch = r37_0
    r10_0.tween = nil
    r10_0:addEventListener("touch", r10_0)
    util.LoadParts(r7_32, r5_0("witches_line2"), 84, 476)
    util.LoadParts(r7_32, r5_0("witches_line2"), 84, 548)
    r12_0 = {}
    for r15_32 = 380, 604, 56 do
      local r16_32 = display.newGroup()
      local r17_32 = {}
      r8_32 = util.LoadParts(r16_32, r5_0("witches_page_active"), 0, 0)
      r8_32.isVisible = false
      table.insert(r17_32, r8_32)
      r8_32 = util.LoadParts(r16_32, r5_0("witches_page_nonactive"), 0, 0)
      r8_32.isVisible = false
      table.insert(r17_32, r8_32)
      r16_32:setReferencePoint(display.TopLeftReferencePoint)
      r16_32.x = r15_32
      r16_32.y = 440
      r7_32:insert(r16_32)
      table.insert(r12_0, r17_32)
    end
    util.LoadBtn({
      rtImg = r7_32,
      fname = r3_32("add_crystal_"),
      x = 80,
      y = 484,
      func = r24_0,
      param = r7_32,
    })
    util.LoadParts(r7_32, r5_0("pocket_crystal"), 640, 480)
    r8_32 = r19_0(r7_32, "Loading")
    r8_32:setReferencePoint(display.TopRightReferencePoint)
    r8_32.x = 856
    r8_32.y = 492
    r14_0 = r8_32
    server.GetCoin(_G.UserToken, r20_0)
    util.LoadBtn({
      rtImg = r7_32,
      fname = r8_0("back_"),
      x = 256,
      y = 560,
      func = r31_0,
    })
    util.LoadBtn({
      rtImg = r7_32,
      fname = r7_0("scrl_previous"),
      x = 448,
      y = 560,
      func = r33_0,
    })
    util.LoadBtn({
      rtImg = r7_32,
      fname = r7_0("scrl_next"),
      x = 576,
      y = 560,
      func = r34_0,
    })
    r13_0 = 1
    if r1_32 then
      if r3_0.CharId.Cecilia <= r1_32 and r1_32 <= r3_0.CharId.Lyra then
        r13_0 = 2
      elseif r3_0.CharId.Tiana <= r1_32 and r1_32 <= r3_0.CharId.Flare then
        r13_0 = 3
      elseif r3_0.CharId.Kala <= r1_32 and r1_32 <= r3_0.CharId.Yuiko then
        r13_0 = 4
      elseif r3_0.CharId.Bell <= r1_32 then
        r13_0 = 5
      end
    end
    r30_0()
    if r13_0 ~= 1 then
      r10_0.x = (r13_0 - 1) * -_G.Width
    end
    r15_0 = display.newGroup()
    r7_32:insert(r15_0)
    r0_32:insert(r7_32)
    return r7_32
  end,
  Cleanup = r9_0,
}
