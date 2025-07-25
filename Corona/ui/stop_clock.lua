-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("tool.crystal")
local r1_0 = require("json")
local r2_0 = nil
local r3_0 = nil
local r4_0 = nil
local r5_0 = nil
local r6_0 = nil
local r7_0 = nil
local r8_0 = nil
local r9_0 = nil
local r10_0 = 1
local r11_0 = nil
local r12_0 = nil
local r13_0 = nil
local r14_0 = nil
local r15_0 = nil
local r16_0 = nil
local r17_0 = nil
local r18_0 = nil
local function r19_0(r0_1)
  -- line: [27, 27] id: 1
  return "data/stopclock/" .. r0_1 .. ".png"
end
local function r20_0(r0_2)
  -- line: [28, 28] id: 2
  return r19_0(r0_2 .. _G.UILanguage)
end
local function r21_0(r0_3)
  -- line: [29, 29] id: 3
  return "data/option/" .. r0_3 .. ".png"
end
local function r22_0(r0_4)
  -- line: [30, 30] id: 4
  return "data/help/" .. r0_4 .. ".png"
end
local function r23_0(r0_5)
  -- line: [31, 31] id: 5
  return "data/game/confirm/" .. r0_5 .. "en.png"
end
local function r24_0(r0_6)
  -- line: [32, 32] id: 6
  return "data/game/confirm/" .. r0_6 .. _G.UILanguage .. ".png"
end
local function r25_0(r0_7, r1_7)
  -- line: [34, 51] id: 7
  local r2_7 = nil	-- notice: implicit variable refs by block#[3]
  if type(r1_7) == "string" then
    r2_7 = r1_7
  else
    r2_7 = util.Num2Column(r1_7)
  end
  local r3_7 = display.newGroup()
  local r4_7 = nil
  display.newText(r3_7, r2_7, 1, 1, native.systemFontBold, 40):setFillColor(0, 0, 0)
  display.newText(r3_7, r2_7, 0, 0, native.systemFontBold, 40):setFillColor(255, 225, 76)
  r0_7:insert(r3_7)
  return r3_7
end
local r26_0 = {
  {
    155,
    300,
    {}
  }
}
local function r27_0(r0_8, r1_8, r2_8, r3_8)
  -- line: [57, 74] id: 8
  if r3_8 == nil then
    r3_8 = 28
  end
  local r4_8 = display.newGroup()
  local r5_8 = nil
  local r6_8 = nil
  if type(r1_8) == "string" then
    r6_8 = r1_8
  else
    r6_8 = util.Num2Column(r1_8)
  end
  display.newText(r4_8, r6_8, 1, 1, native.systemFontBold, r3_8):setFillColor(255, 255, 255)
  display.newText(r4_8, r6_8, 0, 0, native.systemFontBold, r3_8):setFillColor(r2_8[1], r2_8[2], r2_8[3])
  r0_8:insert(r4_8)
  return r4_8
end
local function r28_0(r0_9, r1_9, r2_9, r3_9, r4_9)
  -- line: [76, 82] id: 9
  r0_9:setReferencePoint(display.TopLeftReferencePoint)
  r0_9.x = r1_9 + (r3_9 - r0_9.width) / 2
  r0_9.y = r2_9 + (r4_9 - r0_9.height) / 2
end
local function r29_0(r0_10)
  -- line: [84, 96] id: 10
  local r1_10 = {
    rtImg = rt,
  }
  local r2_10 = r26_0[r0_10]
  r1_10.id = r2_10[1]
  r1_10.crystal = r2_10[2]
  r1_10.msgid = r2_10[3]
  r1_10.disable = false
  r1_10.enable = false
  return r1_10
end
local function r30_0(r0_11, r1_11)
  -- line: [98, 122] id: 11
  local r2_11 = display.newGroup()
  local r3_11 = nil
  local r4_11 = nil
  local r5_11 = 0
  for r9_11, r10_11 in pairs(r1_11) do
    if type(r10_11) == "number" then
      r3_11 = db.GetMessage(r10_11)
    else
      r3_11 = r10_11
    end
    display.newText(r2_11, r3_11, 1, r5_11 + 1, native.systemFontBold, 24):setFillColor(0, 0, 0)
    r4_11 = display.newText(r2_11, r3_11, 0, r5_11, native.systemFontBold, 24)
    r4_11:setFillColor(255, 255, 255)
    r5_11 = r5_11 + 36
  end
  r0_11:insert(r2_11)
  r2_11:setReferencePoint(display.TopLeftReferencePoint)
  r2_11.x = 256
  r2_11.y = 315
  r2_11.isVisible = false
  return r2_11
end
local function r31_0(r0_12)
  -- line: [124, 131] id: 12
  local r1_12 = r0_12.param
  sound.PlaySE(2)
  r17_0()
  r1_12[2]()
  return true
end
local function r32_0()
  -- line: [133, 142] id: 13
  local r0_13 = 0
  local r1_13 = {}
  for r5_13, r6_13 in pairs(r4_0) do
    r0_13 = r0_13 + r6_13.crystal
    table.insert(r1_13, r6_13.id)
  end
  return r0_13, r1_13
end
local function r33_0(r0_14)
  -- line: [145, 152] id: 14
  for r4_14, r5_14 in pairs(r4_0) do
    if r5_14.id == r0_14 then
      return r5_14.crystal
    end
  end
  return 0
end
local function r34_0(r0_15, r1_15, r2_15, r3_15)
  -- line: [154, 220] id: 15
  local r4_15 = false
  if _G.UserToken == nil then
    server.NetworkError(35, r16_0)
    return 
  end
  local r5_15 = nil
  local r6_15 = table.maxn(r2_15)
  local r7_15 = nil
  if r6_15 > 1 then
    r7_15 = r2_15
    r5_15 = {}
    for r11_15 = 1, r6_15, 1 do
      table.insert(r5_15, 1)
    end
  else
    r7_15 = r2_15[1]
    r5_15 = 1
  end
  if _G.IsSimulator then
    db.UseItem(_G.UserID, _G.WaveNr, r2_15)
    local r8_15 = nil
    for r12_15, r13_15 in pairs(r2_15) do
      r8_15 = r33_0(r13_15)
      kpi.Spend(_G.MapSelect, _G.StageSelect, r13_15, 1, r8_15)
      _G.metrics.crystal_stop_clock(_G.MapSelect, _G.StageSelect, r8_15)
    end
    r0_15[1](r2_15)
  else
    util.setActivityIndicator(true)
    server.UseCoin(_G.UserToken, r7_15, r5_15, function(r0_16)
      -- line: [186, 218] id: 16
      util.setActivityIndicator(false)
      if server.CheckError(r0_16) then
        server.NetworkError()
        sound.PlaySE(2)
        dialog.Close()
        r18_0(r3_15, r0_15)
      else
        local r1_16 = r1_0.decode(r0_16.response)
        if r1_16.status == 0 then
          db.UseItem(_G.UserID, _G.WaveNr, r2_15)
          local r2_16 = nil
          for r6_16, r7_16 in pairs(r2_15) do
            r2_16 = r33_0(r7_16)
            kpi.Spend(_G.MapSelect, _G.StageSelect, r7_16, 1, r2_16)
            _G.metrics.crystal_stop_clock(_G.MapSelect, _G.StageSelect, r2_16)
          end
          r4_15 = true
        else
          server.ServerError(r1_16.status)
        end
        if r4_15 then
          r0_15[1](r2_15)
        else
          sound.PlaySE(2)
          dialog.Close()
          r18_0(r3_15, r0_15)
        end
      end
    end)
  end
end
local function r35_0(r0_17)
  -- line: [222, 280] id: 17
  local r1_17, r2_17 = r32_0()
  local r3_17 = r3_0.parent
  local r4_17 = r0_17.param
  sound.PlaySE(1)
  r3_0.alpha = 0.4
  if r15_0 == nil then
    server.NetworkError(35)
    dialog.Close()
    r18_0(r3_17, r4_17)
    return 
  end
  if r15_0 < r1_17 then
    server.NetworkError(31)
    dialog.Close()
    r18_0(r3_17, r4_17)
    return 
  end
  dialog.Open(r3_17, 13, {
    string.format(db.GetMessage(14), util.ConvertDisplayCrystal(r1_17)),
    15
  }, {
    function(r0_18)
      -- line: [256, 271] id: 18
      if r8_0 then
        transition.cancel(r8_0)
      end
      r8_0 = transition.to(_G.PanelRoot, {
        time = 500,
        y = _G.HeightDiff - _G.PanelRoot.height,
        transition = easing.outExpo,
      })
      sound.PlaySE(1)
      dialog.Close()
      r17_0()
      r34_0(r4_17, r1_17, r2_17, r3_17)
      return true
    end,
    function(r0_19)
      -- line: [272, 278] id: 19
      sound.PlaySE(2)
      dialog.Close()
      r18_0(r3_17, r4_17)
      return true
    end
  })
  return true
end
local function r36_0(r0_20)
  -- line: [282, 286] id: 20
  r18_0(r0_20[1], r0_20[2])
end
local function r37_0(r0_21)
  -- line: [288, 294] id: 21
  local r1_21 = r0_21.param
  local r2_21 = r3_0.parent
  r17_0()
  r0_0.Open(r2_21, {
    r36_0,
    {
      r2_21,
      r1_21
    }
  })
  return true
end
local function r38_0(r0_22)
  -- line: [296, 337] id: 22
  local r1_22 = display.newGroup()
  local r2_22 = util.MakeGroup(r1_22)
  local r3_22 = util.MakeGroup(r1_22)
  local r4_22 = util.MakeGroup(r1_22)
  util.LoadPartsCenter(r2_22, r19_0("window_kala"), 480, 350)
  r13_0 = util.LoadBtn({
    rtImg = r2_22,
    fname = r24_0("confirm_01"),
    x = 360,
    y = 440,
    func = r35_0,
    param = r0_22,
  })
  util.LoadBtn({
    rtImg = r2_22,
    fname = r24_0("confirm_02"),
    x = 490,
    y = 440,
    func = r31_0,
    param = r0_22,
  })
  util.LoadBtn({
    rtImg = r2_22,
    fname = r20_0("add_crystal_"),
    x = 458,
    y = 390,
    func = r37_0,
    param = r0_22,
  })
  util.LoadParts(r2_22, r19_0("pocket_crystal"), 220, 390)
  local r5_22 = r25_0(r2_22, "Loading")
  r5_22:setReferencePoint(display.TopRightReferencePoint)
  r5_22.x = 445
  r5_22.y = 396
  r6_0 = r5_22
  r4_0 = {}
  table.insert(r4_0, r29_0(1))
  r12_0 = r30_0(r4_22, {
    289,
    string.format(db.GetMessage(290), util.ConvertDisplayCrystal(r26_0[1][2]))
  })
  r12_0.isVisible = true
  return r1_22
end
local function r39_0(r0_23)
  -- line: [340, 363] id: 23
  local r1_23 = db.UsingItemList(_G.UserID)
  local r2_23 = {}
  for r6_23, r7_23 in pairs(r1_23) do
    r2_23[r7_23[1]] = true
  end
  for r6_23, r7_23 in pairs(r4_0) do
    local r10_23 = true
    if r2_23[r7_23.id] == nil then
      local r9_23 = false
    end
    if r10_23 then
      if r7_23.crystal <= r0_23 then
        r7_23.ok = true
      else
        r7_23.ok = false
      end
    end
  end
end
local function r40_0(r0_24)
  -- line: [365, 394] id: 24
  if r6_0 == nil then
    return 
  end
  if server.CheckError(r0_24) then
    server.NetworkError(35, nil)
    return 
  else
    local r1_24 = r1_0.decode(r0_24.response)
    if r1_24.status == 0 then
      local r2_24 = r1_24.coin
      r15_0 = r2_24
      local r3_24 = r6_0.x
      local r4_24 = r6_0.y
      local r5_24 = r6_0.parent
      if r6_0 then
        display.remove(r6_0)
      end
      r6_0 = nil
      local r6_24 = r25_0(r5_24, util.ConvertDisplayCrystal(r2_24))
      r6_24:setReferencePoint(display.TopRightReferencePoint)
      r6_24.x = r3_24
      r6_24.y = r4_24
      r6_0 = r6_24
      r39_0(r2_24)
      r0_0.ShowCoinInfo(r2_24)
    else
      server.ServerError(r1_24.status, nil)
    end
  end
end
local function r41_0()
  -- line: [396, 398] id: 25
  server.GetCoin(_G.UserToken, r40_0)
end
local function r42_0(r0_26)
  -- line: [400, 413] id: 26
  if db.CountQueue() > 0 then
    if r0_26.count >= 60 then
      util.setActivityIndicator(false)
      r17_0()
    end
    return 
  end
  timer.cancel(r0_26.source)
  util.setActivityIndicator(false)
  r41_0()
end
local function r43_0(r0_27)
  -- line: [415, 439] id: 27
  local r1_27 = nil	-- notice: implicit variable refs by block#[0]
  r11_0 = r1_27
  r1_27 = r0_27[1]
  local r2_27 = r0_27[2]
  util.setActivityIndicator(false)
  r2_0 = util.MakeMat(r1_27)
  r3_0 = r38_0(r2_27)
  r1_27:insert(r3_0)
  if db.CountQueue() <= 0 then
    r41_0()
  end
end
function r18_0(r0_28, r1_28)
  -- line: [445, 455] id: 28
  r14_0 = r1_28[2]
  r17_0()
  if _G.UserToken == nil then
    server.NetworkError(35, r16_0)
  else
    r43_0({
      r0_28,
      r1_28
    })
  end
end
function r17_0()
  -- line: [457, 487] id: 29
  if r9_0 then
    if r9_0.tween then
      transition.cancel(r9_0.tween)
    end
    if r9_0.ev then
      events.Delete(r9_0.ev)
    end
    if r9_0.vev then
      events.Delete(r9_0.vev)
    end
    r9_0 = nil
  end
  if r3_0 then
    display.remove(r3_0)
    r3_0 = nil
  end
  if r2_0 then
    display.remove(r2_0)
    r2_0 = nil
  end
  r6_0 = nil
  r5_0 = nil
end
function r16_0()
  -- line: [496, 499] id: 32
  r14_0()
  r17_0()
end
return {
  Init = function()
    -- line: [489, 490] id: 30
  end,
  Cleanup = function()
    -- line: [492, 494] id: 31
    r17_0()
  end,
  Open = r18_0,
  Close = r17_0,
  ErrorClose = r16_0,
}
