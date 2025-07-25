-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = _G.dialog or require("ui.dialog")
local r1_0 = require("tool.crystal")
local r2_0 = require("json")
local r3_0 = nil
local r4_0 = nil
local r5_0 = nil
local r6_0 = nil
local r7_0 = nil
local r8_0 = nil
local r9_0 = nil
local r10_0 = nil
local r11_0 = _G.RewindCrystal
local r12_0 = nil
local r13_0 = nil
local r14_0 = nil
local r15_0 = 1
local r16_0 = false
local r17_0 = 0
local function r18_0(r0_1)
  -- line: [28, 28] id: 1
  return "data/game/rewind/" .. r0_1 .. ".png"
end
local function r19_0(r0_2)
  -- line: [29, 29] id: 2
  return r18_0(r0_2 .. _G.UILanguage)
end
local function r20_0(r0_3)
  -- line: [30, 30] id: 3
  return "data/powerup/" .. r0_3 .. ".png"
end
local function r21_0(r0_4)
  -- line: [31, 31] id: 4
  return r20_0(r0_4 .. _G.UILanguage)
end
local function r22_0(r0_5)
  -- line: [32, 32] id: 5
  return "data/option/" .. r0_5 .. ".png"
end
local function r23_0(r0_6)
  -- line: [33, 33] id: 6
  return "data/login_bonus/items/" .. r0_6 .. ".png"
end
local function r24_0(r0_7)
  -- line: [34, 34] id: 7
  return "data/login_bonus/receive/" .. r0_7 .. ".png"
end
local function r25_0(r0_8, r1_8)
  -- line: [36, 53] id: 8
  local r2_8 = nil	-- notice: implicit variable refs by block#[3]
  if type(r1_8) == "string" then
    r2_8 = r1_8
  else
    r2_8 = util.Num2Column(r1_8)
  end
  local r3_8 = display.newGroup()
  local r4_8 = nil
  display.newText(r3_8, r2_8, 1, 1, native.systemFontBold, 40):setFillColor(0, 0, 0)
  display.newText(r3_8, r2_8, 0, 0, native.systemFontBold, 40):setFillColor(255, 225, 76)
  r0_8:insert(r3_8)
  return r3_8
end
local function r26_0(r0_9, r1_9, r2_9)
  -- line: [55, 57] id: 9
  util.LoadParts(r0_9, r18_0(string.format("wave_number%02d", r1_9)), r2_9, 0)
end
local function r27_0(r0_10, r1_10, r2_10)
  -- line: [59, 72] id: 10
  local r3_10 = r1_10 % 10
  local r5_10 = display.newGroup()
  r26_0(r5_10, (r1_10 - r3_10) / 10, 0)
  r26_0(r5_10, r3_10, 28)
  r0_10:insert(r5_10)
  r5_10:setReferencePoint(display.TopLeftReferencePoint)
  r5_10.x = r2_10
  r5_10.y = 228
  return r5_10
end
local function r28_0(r0_11)
  -- line: [74, 84] id: 11
  r9_0 = {}
  local r1_11 = 452
  for r5_11 = r8_0, 2, -1 do
    r9_0[r5_11] = r27_0(r0_11, r5_11, r1_11)
    r9_0[r5_11].isVisible = false
    r1_11 = r1_11 - 80
  end
end
local function r29_0()
  -- line: [87, 96] id: 12
  for r3_12, r4_12 in pairs(r9_0) do
    local r5_12 = r4_12.x
    if r5_12 < 368 or 564 < r5_12 then
      r4_12.isVisible = false
    else
      r4_12.isVisible = true
    end
  end
end
local function r30_0(r0_13, r1_13)
  -- line: [98, 146] id: 13
  if r10_0 then
    display.remove(r10_0)
    r10_0 = nil
  end
  local r2_13 = display.newGroup()
  local r3_13 = tostring(util.ConvertDisplayCrystal(r1_13))
  local r4_13 = nil
  if r16_0 then
    util.LoadParts(r2_13, r24_0("ticket_rewind_s"), -35, 0)
    r4_13 = util.MakeText3({
      rtImg = r2_13,
      x = 40,
      y = 5,
      size = 28,
      color = {
        255,
        255,
        255
      },
      shadow = {
        0,
        0,
        0
      },
      diff_x = 1,
      diff_y = 2,
      msg = "×1",
    })
  else
    util.LoadParts(r2_13, r20_0("powerup_crystal1"), 0, 0)
    r4_13 = display.newText(r2_13, r3_13, 1, 1, native.systemFontBold, 40)
    r4_13:setReferencePoint(display.TopLeftReferencePoint)
    r4_13.x = 33
    r4_13.y = 1
    r4_13:setFillColor(255, 255, 255)
    r4_13 = display.newText(r2_13, r3_13, 0, 0, native.systemFontBold, 40)
    r4_13:setReferencePoint(display.TopLeftReferencePoint)
    r4_13.x = 32
    r4_13.y = 0
    r4_13:setFillColor(204, 0, 68)
  end
  r2_13:setReferencePoint(display.CenterReferencePoint)
  r2_13.x = 480
  r2_13.y = 320
  r0_13:insert(r2_13)
  r10_0 = r2_13
end
local function r31_0()
  -- line: [149, 153] id: 14
  r30_0(r10_0.parent, (r8_0 - r7_0 + 1) * r11_0)
end
local function r32_0(r0_15, r1_15, r2_15)
  -- line: [155, 201] id: 15
  local r4_15 = r1_15.ed
  local r3_15 = r1_15.st + r2_15
  if r4_15 < r3_15 then
    r3_15 = r4_15
  end
  r1_15.st = r3_15
  local r5_15 = nil
  local r6_15 = nil
  local r7_15 = r1_15.lx
  local r8_15 = nil
  if r3_15 < r4_15 then
    for r12_15, r13_15 in pairs(r9_0) do
      r13_15.x = easing.linear(r3_15, r4_15, r1_15.sx[r12_15], r7_15)
    end
    r8_15 = true
  else
    for r12_15, r13_15 in pairs(r9_0) do
      r13_15.x = r1_15.sx[r12_15] + r7_15
    end
    r13_0 = nil
    r7_0 = r1_15.nwave
    r31_0()
    r8_15 = false
  end
  for r12_15, r13_15 in pairs(r9_0) do
    if r13_15.x < 372 or 532 < r13_15.x then
      r13_15.isVisible = false
    else
      r13_15.isVisible = true
    end
  end
  if 2 <= r15_0 and r15_0 <= _G.WaveNr and r7_0 ~= r15_0 then
    r14_0[3].isVisible = true
    r14_0[4].isVisible = false
  else
    r14_0[3].isVisible = false
    r14_0[4].isVisible = true
  end
  return r8_15
end
local function r33_0(r0_16)
  -- line: [203, 229] id: 16
  local r1_16 = r7_0 + r0_16
  if r1_16 < 2 or r8_0 < r1_16 then
    sound.PlaySE(2)
    return 
  end
  if r13_0 then
    return 
  end
  sound.PlaySE(1)
  local r2_16 = {
    sx = {},
    st = 0,
    ed = 200,
    nwave = r1_16,
  }
  for r6_16, r7_16 in pairs(r9_0) do
    r2_16.sx[r6_16] = r7_16.x
  end
  r2_16.lx = -80 * r0_16
  r13_0 = events.Register(r32_0, r2_16, 1)
end
local function r34_0(r0_17)
  -- line: [231, 234] id: 17
  r33_0(-1)
  return true
end
local function r35_0(r0_18)
  -- line: [236, 239] id: 18
  r33_0(1)
  return true
end
local function r36_0(r0_19)
  -- line: [241, 247] id: 19
  r33_0((r7_0 - r0_19.param) * -1)
  return true
end
local function r37_0(r0_20)
  -- line: [249, 255] id: 20
  local r1_20 = r0_20.param
  sound.PlaySE(2)
  r4_0()
  if r1_20 then
    r1_20()
  end
  return true
end
local function r38_0(r0_21, r1_21)
  -- line: [257, 272] id: 21
  r0_21[1](r1_21)
  db.SetInventoryItem({
    {
      uid = _G.UserInquiryID,
      itemid = _G.LoginItems["3"].id,
      quantity = db.GetInventoryItem(_G.UserInquiryID, _G.LoginItems["3"].id) - 1,
    }
  })
  _G.metrics.ticket_use_rewind(_G.MapSelect, _G.StageSelect)
end
local function r39_0(r0_22, r1_22)
  -- line: [274, 302] id: 22
  if _G.UserToken == nil then
    server.NetworkError(35, nil)
    return 
  end
  local r2_22 = r8_0 - r7_0 + 1
  if _G.IsSimulator then
    r0_22[1](r1_22)
    _G.metrics.crystal_rewind_in_stage(_G.MapSelect, _G.StageSelect, r11_0 * 10)
  else
    util.setActivityIndicator(true)
    server.UseCoin(_G.UserToken, 12, r2_22, function(r0_23)
      -- line: [285, 300] id: 23
      util.setActivityIndicator(false)
      if server.CheckError(r0_23) then
        server.NetworkError(35)
      else
        local r1_23 = r2_0.decode(r0_23.response)
        if r1_23.status == 0 then
          kpi.Spend(_G.MapSelect, _G.StageSelect, 12, r2_22, r11_0 * 10)
          r0_22[1](r1_22)
          _G.metrics.crystal_rewind_in_stage(_G.MapSelect, _G.StageSelect, r11_0 * 10)
        else
          server.ServerError(r1_23.status)
          r0_22[2]()
        end
      end
    end)
  end
end
local function r40_0(r0_24)
  -- line: [304, 329] id: 24
  local r1_24 = r6_0.parent
  local r2_24 = r0_24.param
  r4_0()
  sound.PlaySE(1)
  r0_0.Open(r1_24, 291, {
    db.GetMessage(293),
    db.GetMessage(294)
  }, {
    function(r0_25)
      -- line: [314, 320] id: 25
      sound.PlaySE(1)
      r0_0.Close()
      r38_0(r2_24, r7_0)
      return true
    end,
    function(r0_26)
      -- line: [321, 327] id: 26
      sound.PlaySE(2)
      r0_0.Close()
      r3_0(r1_24, r2_24)
      return true
    end
  })
  return true
end
local function r41_0(r0_27)
  -- line: [331, 357] id: 27
  local r1_27 = r6_0.parent
  local r2_27 = r0_27.param
  r4_0()
  sound.PlaySE(1)
  r0_0.Open(r1_27, 13, {
    string.format(db.GetMessage(14), util.ConvertDisplayCrystal((r8_0 - r7_0 + 1) * r11_0)),
    15
  }, {
    function(r0_28)
      -- line: [342, 348] id: 28
      sound.PlaySE(1)
      r0_0.Close()
      r39_0(r2_27, r7_0)
      return true
    end,
    function(r0_29)
      -- line: [349, 355] id: 29
      sound.PlaySE(2)
      r0_0.Close()
      r3_0(r1_27, r2_27)
      return true
    end
  })
  return true
end
local function r42_0(r0_30)
  -- line: [359, 363] id: 30
  r3_0(r0_30[1], r0_30[2])
end
local function r43_0(r0_31)
  -- line: [365, 371] id: 31
  local r1_31 = r0_31.param
  local r2_31 = r6_0.parent
  r4_0()
  r1_0.Open(r2_31, {
    r42_0,
    {
      r2_31,
      r1_31
    }
  })
  return true
end
local function r44_0(r0_32)
  -- line: [373, 509] id: 32
  local r1_32 = display.newGroup()
  for r5_32 = 0, 960, 16 do
    util.LoadParts(r1_32, r18_0("rewind_window01"), r5_32, 72)
    util.LoadParts(r1_32, r18_0("rewind_window02"), r5_32, 200)
    util.LoadParts(r1_32, r18_0("rewind_window03"), r5_32, 328)
    util.LoadParts(r1_32, r18_0("rewind_window04"), r5_32, 456)
  end
  util.LoadParts(r1_32, r19_0("rewind_title_"), 288, 112)
  for r5_32 = 224, 480, 256 do
    util.LoadParts(r1_32, r22_0("option_line01"), r5_32, 168)
    util.LoadParts(r1_32, r22_0("option_line02"), r5_32, 360)
    util.LoadParts(r1_32, r22_0("option_line01"), r5_32, 456)
  end
  local r2_32 = display.newGroup()
  r1_32:insert(r2_32)
  local r3_32 = display.newGroup()
  r1_32:insert(r3_32)
  local r4_32 = display.newGroup()
  r1_32:insert(r4_32)
  util.LoadParts(r2_32, r18_0("rewind_plate"), 360, 176)
  util.LoadParts(r4_32, r18_0("number_mask"), 368, 216)
  util.LoadBtn({
    rtImg = r1_32,
    fname = r18_0("rewind"),
    x = 272,
    y = 216,
    func = r34_0,
  })
  util.LoadBtn({
    rtImg = r1_32,
    fname = r18_0("forward"),
    x = 600,
    y = 216,
    func = r35_0,
  })
  r28_0(r3_32)
  r29_0()
  r30_0(r1_32, r11_0)
  r14_0 = {}
  util.LoadBtn({
    rtImg = r1_32,
    fname = r22_0("close"),
    x = 872,
    y = 40,
    func = r37_0,
    param = r0_32[2],
  })
  r15_0 = db.LoadResumeRewind(_G.UserID, _G.MapSelect, _G.StageSelect)
  r14_0[3] = util.LoadBtn({
    rtImg = r1_32,
    fname = r19_0("rewind_damage_"),
    x = 216,
    y = 464,
    func = r36_0,
    param = r15_0,
  })
  r14_0[3].isVisible = false
  r14_0[4] = util.LoadParts(r1_32, r19_0("rewind_damage_disable_"), 216, 464)
  r14_0[4].isVisible = false
  if 2 <= r15_0 and r15_0 < _G.WaveNr then
    r14_0[3].isVisible = true
  else
    r14_0[4].isVisible = true
  end
  if r16_0 then
    r14_0[1] = util.LoadBtn({
      rtImg = r1_32,
      fname = r21_0("ok_"),
      x = 504,
      y = 464,
      func = r40_0,
      param = r0_32,
    })
    local r5_32 = 330
    local r6_32 = 390
    local r8_32 = util.MakeText3({
      rtImg = r1_32,
      x = r5_32,
      y = r6_32,
      size = 28,
      color = {
        255,
        255,
        255
      },
      shadow = {
        0,
        0,
        0
      },
      diff_x = 1,
      diff_y = 2,
      msg = db.GetMessage(292),
    })
    local r9_32 = util.LoadParts(r1_32, r24_0("ticket_rewind_s"), r5_32 + r8_32.width, r6_32 - 7)
    local r10_32 = {
      "ticket_num_0",
      "ticket_num_1",
      "ticket_num_2",
      "ticket_num_3",
      "ticket_num_4",
      "ticket_num_5",
      "ticket_num_6",
      "ticket_num_7",
      "ticket_num_8",
      "ticket_num_9"
    }
    local r11_32 = nil
    if type(r17_0) ~= "string" then
      r11_32 = string.format("%d", r17_0)
    else
      r11_32 = r17_0
    end
    local r12_32 = 2
    local r13_32 = r5_32 + r8_32.width + 185
    local r14_32 = r6_32 + 10
    local r15_32 = util.LoadParts(r1_32, r23_0("ticket_num_mark"), r13_32 - 95, r14_32 - 7)
    for r19_32 = r11_32:len(), 1, -1 do
      local r21_32 = util.LoadParts(r1_32, r23_0(r10_32[tonumber(r11_32:sub(r19_32, r19_32)) + 1]), r13_32 + r15_32.width, r14_32 - 7)
      r21_32.x = r13_32 - r21_32.width + r12_32
    end
  else
    r14_0[1] = util.LoadBtn({
      rtImg = r1_32,
      fname = r21_0("ok_"),
      x = 504,
      y = 464,
      func = r41_0,
      param = r0_32,
    })
    r14_0[2] = util.LoadBtn({
      rtImg = r1_32,
      fname = r21_0("add_crystal_"),
      x = 504,
      y = 378,
      func = r43_0,
      param = r0_32,
    })
    util.LoadParts(r1_32, r20_0("pocket_crystal"), 216, 368)
    local r5_32 = r25_0(r1_32, "Loading")
    r5_32:setReferencePoint(display.TopRightReferencePoint)
    r5_32.x = 440
    r5_32.y = 388
    r12_0 = r5_32
  end
  return r1_32
end
local function r45_0(r0_33)
  -- line: [511, 538] id: 33
  if server.CheckError(r0_33) then
    server.NetworkError(35, r4_0)
  else
    local r1_33 = r2_0.decode(r0_33.response)
    if r1_33.status == 0 then
      local r2_33 = r1_33.coin
      if r12_0 then
        local r3_33 = r12_0.x
        local r4_33 = r12_0.y
        local r5_33 = r12_0.parent
        if r12_0 then
          display.remove(r12_0)
        end
        r12_0 = nil
        local r6_33 = r25_0(r5_33, util.ConvertDisplayCrystal(r2_33))
        r6_33:setReferencePoint(display.TopRightReferencePoint)
        r6_33.x = r3_33
        r6_33.y = r4_33
        r12_0 = r6_33
        r1_0.ShowCoinInfo(r2_33)
      end
    else
      server.ServerError(r1_33.status, r4_0)
    end
  end
end
local function r46_0()
  -- line: [540, 542] id: 34
  server.GetCoin(_G.UserToken, r45_0)
end
local function r47_0(r0_35)
  -- line: [544, 557] id: 35
  if db.CountQueue() > 0 then
    if r0_35.count >= 60 then
      util.setActivityIndicator(false)
      r4_0()
    end
    return 
  end
  timer.cancel(r0_35.source)
  util.setActivityIndicator(false)
  r46_0()
end
local function r48_0(r0_36)
  -- line: [559, 578] id: 36
  if _G.UserToken == nil then
    server.NetworkError(35, r4_0)
    return 
  end
  local r1_36 = r0_36[1]
  local r2_36 = r0_36[2]
  util.setActivityIndicator(false)
  r5_0 = util.MakeMat(r1_36)
  r6_0 = r44_0(r2_36)
  r1_36:insert(r6_0)
  if db.CountQueue() > 0 then
    util.setActivityIndicator(true)
    timer.performWithDelay(500, r47_0, 60)
  else
    r46_0()
  end
end
function r3_0(r0_37, r1_37)
  -- line: [584, 613] id: 37
  r16_0 = false
  r17_0 = 0
  local r2_37 = db.GetInventoryItem(_G.UserInquiryID, _G.LoginItems["3"].id)
  if r2_37 > 0 then
    r16_0 = true
    r17_0 = r2_37
  end
  r4_0()
  r7_0 = _G.WaveNr
  r8_0 = r7_0
  if _G.UserToken == nil then
    if r1_37[2] then
      r1_37[2]()
    end
    server.NetworkError(35, r4_0)
    return false
  else
    r48_0({
      r0_37,
      r1_37
    })
  end
end
function r4_0()
  -- line: [615, 626] id: 38
  if r6_0 then
    display.remove(r6_0)
    r6_0 = nil
  end
  if r5_0 then
    display.remove(r5_0)
    r5_0 = nil
  end
  r12_0 = nil
  r10_0 = nil
end
return {
  Open = r3_0,
  Close = r4_0,
  Init = function()
    -- line: [628, 630] id: 39
  end,
  Cleanup = function()
    -- line: [632, 634] id: 40
    r4_0()
  end,
}
