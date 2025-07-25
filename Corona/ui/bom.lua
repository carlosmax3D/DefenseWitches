-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = _G.dialog or require("ui.dialog")
local r1_0 = require("tool.crystal")
local r2_0 = require("json")
local r3_0 = require("logic.pay_item_data")
local r4_0 = nil
local r5_0 = nil
local r6_0 = nil
local r7_0 = nil
local r8_0 = nil
local r9_0 = nil
local r10_0 = nil
local r11_0 = nil
local r12_0 = nil
local r13_0 = nil
local r14_0 = nil
local r15_0 = 1
local r16_0 = nil
local r17_0 = nil
local r18_0 = nil
local r19_0 = nil
local r20_0 = nil
local r21_0 = false
local r22_0 = 0
local function r23_0(r0_1)
  -- line: [34, 34] id: 1
  return "data/bom/" .. r0_1 .. ".png"
end
local function r24_0(r0_2)
  -- line: [36, 36] id: 2
  return r23_0(r0_2 .. _G.UILanguage)
end
local function r25_0(r0_3)
  -- line: [38, 38] id: 3
  return "data/option/" .. r0_3 .. ".png"
end
local function r26_0(r0_4)
  -- line: [40, 40] id: 4
  return "data/help/" .. r0_4 .. ".png"
end
local function r27_0(r0_5)
  -- line: [42, 42] id: 5
  return "data/game/confirm/" .. r0_5 .. "en.png"
end
local function r28_0(r0_6)
  -- line: [44, 44] id: 6
  return "data/game/confirm/" .. r0_6 .. _G.UILanguage .. ".png"
end
local function r29_0(r0_7)
  -- line: [46, 46] id: 7
  return "data/login_bonus/items/" .. r0_7 .. ".png"
end
local function r30_0(r0_8)
  -- line: [48, 48] id: 8
  return "data/login_bonus/receive/" .. r0_8 .. ".png"
end
local function r31_0(r0_9, r1_9)
  -- line: [50, 67] id: 9
  local r2_9 = nil	-- notice: implicit variable refs by block#[3]
  if type(r1_9) == "string" then
    r2_9 = r1_9
  else
    r2_9 = util.Num2Column(r1_9)
  end
  local r3_9 = display.newGroup()
  local r4_9 = nil
  display.newText(r3_9, r2_9, 1, 1, native.systemFontBold, 40):setFillColor(0, 0, 0)
  display.newText(r3_9, r2_9, 0, 0, native.systemFontBold, 40):setFillColor(255, 225, 76)
  r0_9:insert(r3_9)
  return r3_9
end
local function r32_0(r0_10, r1_10, r2_10, r3_10)
  -- line: [69, 86] id: 10
  if r3_10 == nil then
    r3_10 = 28
  end
  local r4_10 = display.newGroup()
  local r5_10 = nil
  local r6_10 = nil
  if type(r1_10) == "string" then
    r6_10 = r1_10
  else
    r6_10 = util.Num2Column(r1_10)
  end
  display.newText(r4_10, r6_10, 1, 1, native.systemFontBold, r3_10):setFillColor(255, 255, 255)
  display.newText(r4_10, r6_10, 0, 0, native.systemFontBold, r3_10):setFillColor(r2_10[1], r2_10[2], r2_10[3])
  r0_10:insert(r4_10)
  return r4_10
end
local function r33_0(r0_11, r1_11, r2_11, r3_11, r4_11)
  -- line: [88, 94] id: 11
  r0_11:setReferencePoint(display.TopLeftReferencePoint)
  r0_11.x = r1_11 + (r3_11 - r0_11.width) / 2
  r0_11.y = r2_11 + (r4_11 - r0_11.height) / 2
end
local function r34_0(r0_12)
  -- line: [96, 108] id: 12
  local r1_12 = {
    rtImg = rt,
  }
  local r2_12 = r3_0.getItemData(r3_0.pay_item_data.CharItemFlareNew)
  r1_12.id = r3_0.pay_item_data.CharItemFlareNew
  r1_12.crystal = r2_12[2]
  r1_12.msgid = r2_12[3]
  r1_12.disable = false
  r1_12.enable = false
  return r1_12
end
local function r35_0(r0_13, r1_13)
  -- line: [110, 134] id: 13
  local r2_13 = display.newGroup()
  local r3_13 = nil
  local r4_13 = nil
  local r5_13 = 0
  for r9_13, r10_13 in pairs(r1_13) do
    if type(r10_13) == "number" then
      r3_13 = db.GetMessage(r10_13)
    else
      r3_13 = r10_13
    end
    display.newText(r2_13, r3_13, 1, r5_13 + 1, native.systemFontBold, 24):setFillColor(0, 0, 0)
    r4_13 = display.newText(r2_13, r3_13, 0, r5_13, native.systemFontBold, 24)
    r4_13:setFillColor(255, 255, 255)
    r5_13 = r5_13 + 36
  end
  r0_13:insert(r2_13)
  r2_13:setReferencePoint(display.TopLeftReferencePoint)
  r2_13.x = 296
  r2_13.y = 295
  r2_13.isVisible = false
  return r2_13
end
local function r36_0(r0_14)
  -- line: [136, 143] id: 14
  local r1_14 = r0_14.param
  sound.PlaySE(2)
  r5_0()
  r1_14[2]()
  return true
end
local function r37_0()
  -- line: [145, 154] id: 15
  local r0_15 = 0
  local r1_15 = {}
  for r5_15, r6_15 in pairs(r9_0) do
    r0_15 = r0_15 + r6_15.crystal
    table.insert(r1_15, r6_15.id)
  end
  return r0_15, r1_15
end
local function r38_0(r0_16)
  -- line: [157, 164] id: 16
  for r4_16, r5_16 in pairs(r9_0) do
    if r5_16.id == r0_16 then
      return r5_16.crystal
    end
  end
  return 0
end
local function r39_0(r0_17, r1_17)
  -- line: [166, 181] id: 17
  r0_17[1]()
  db.SetInventoryItem({
    {
      uid = _G.UserInquiryID,
      itemid = _G.LoginItems["2"].id,
      quantity = db.GetInventoryItem(_G.UserInquiryID, _G.LoginItems["2"].id) - 1,
    }
  })
  _G.metrics.ticket_use_flare(_G.MapSelect, _G.StageSelect)
end
local function r40_0(r0_18, r1_18, r2_18, r3_18)
  -- line: [183, 249] id: 18
  local r4_18 = false
  if _G.UserToken == nil then
    server.NetworkError(35, r6_0)
    return 
  end
  local r5_18 = nil
  local r6_18 = table.maxn(r2_18)
  local r7_18 = nil
  if r6_18 > 1 then
    r7_18 = r2_18
    r5_18 = {}
    for r11_18 = 1, r6_18, 1 do
      table.insert(r5_18, 1)
    end
  else
    r7_18 = r2_18[1]
    r5_18 = 1
  end
  if _G.IsSimulator then
    db.UseItem(_G.UserID, _G.WaveNr, r2_18)
    local r8_18 = nil
    for r12_18, r13_18 in pairs(r2_18) do
      r8_18 = r38_0(r13_18)
      kpi.Spend(_G.MapSelect, _G.StageSelect, r13_18, 1, r8_18)
      _G.metrics.crystal_flare_in_stage(_G.MapSelect, _G.StageSelect, r8_18)
    end
    r0_18[1](r2_18)
  else
    util.setActivityIndicator(true)
    server.UseCoin(_G.UserToken, r7_18, r5_18, function(r0_19)
      -- line: [215, 247] id: 19
      util.setActivityIndicator(false)
      if server.CheckError(r0_19) then
        server.NetworkError()
        sound.PlaySE(2)
        r0_0.Close()
        r4_0(r3_18, r0_18)
      else
        local r1_19 = r2_0.decode(r0_19.response)
        if r1_19.status == 0 then
          db.UseItem(_G.UserID, _G.WaveNr, r2_18)
          local r2_19 = nil
          for r6_19, r7_19 in pairs(r2_18) do
            r2_19 = r38_0(r7_19)
            kpi.Spend(_G.MapSelect, _G.StageSelect, r7_19, 1, r2_19)
            _G.metrics.crystal_flare_in_stage(_G.MapSelect, _G.StageSelect, r2_19)
          end
          r4_18 = true
        else
          server.ServerError(r1_19.status)
        end
        if r4_18 then
          r0_18[1](r2_18)
        else
          sound.PlaySE(2)
          r0_0.Close()
          r4_0(r3_18, r0_18)
        end
      end
    end)
  end
end
local function r41_0(r0_20)
  -- line: [251, 286] id: 20
  local r1_20 = r8_0.parent
  local r2_20 = r0_20.param
  sound.PlaySE(1)
  r8_0.alpha = 0.4
  r0_0.Open(r1_20, 291, {
    314,
    315
  }, {
    function(r0_21)
      -- line: [261, 276] id: 21
      if r13_0 then
        transition.cancel(r13_0)
      end
      r13_0 = transition.to(_G.PanelRoot, {
        time = 500,
        y = _G.HeightDiff - _G.PanelRoot.height,
        transition = easing.outExpo,
      })
      sound.PlaySE(1)
      r0_0.Close()
      r5_0()
      r39_0(r2_20, r1_20)
      return true
    end,
    function(r0_22)
      -- line: [277, 283] id: 22
      sound.PlaySE(2)
      r0_0.Close()
      r4_0(r1_20, r2_20)
      return true
    end
  })
  return true
end
local function r42_0(r0_23)
  -- line: [288, 346] id: 23
  local r1_23, r2_23 = r37_0()
  local r3_23 = r8_0.parent
  local r4_23 = r0_23.param
  sound.PlaySE(1)
  r8_0.alpha = 0.4
  if r20_0 == nil then
    server.NetworkError(35)
    r0_0.Close()
    r4_0(r3_23, r4_23)
    return 
  end
  if r20_0 < r1_23 then
    server.NetworkError(31)
    r0_0.Close()
    r4_0(r3_23, r4_23)
    return 
  end
  r0_0.Open(r3_23, 13, {
    string.format(db.GetMessage(14), util.ConvertDisplayCrystal(r1_23)),
    15
  }, {
    function(r0_24)
      -- line: [322, 336] id: 24
      if r13_0 then
        transition.cancel(r13_0)
      end
      r13_0 = transition.to(_G.PanelRoot, {
        time = 500,
        y = _G.HeightDiff - _G.PanelRoot.height,
        transition = easing.outExpo,
      })
      sound.PlaySE(1)
      r0_0.Close()
      r5_0()
      r40_0(r4_23, r1_23, r2_23, r3_23)
      return true
    end,
    function(r0_25)
      -- line: [337, 343] id: 25
      sound.PlaySE(2)
      r0_0.Close()
      r4_0(r3_23, r4_23)
      return true
    end
  })
  return true
end
local function r43_0(r0_26)
  -- line: [348, 352] id: 26
  r4_0(r0_26[1], r0_26[2])
end
local function r44_0(r0_27)
  -- line: [354, 360] id: 27
  local r1_27 = r0_27.param
  local r2_27 = r8_0.parent
  r5_0()
  r1_0.Open(r2_27, {
    r43_0,
    {
      r2_27,
      r1_27
    }
  })
  return true
end
local function r45_0(r0_28)
  -- line: [362, 510] id: 28
  local r1_28 = display.newGroup()
  local r2_28 = util.MakeGroup(r1_28)
  local r3_28 = util.MakeGroup(r1_28)
  local r4_28 = util.MakeGroup(r1_28)
  util.LoadPartsCenter(r2_28, r23_0("plate_bom"), 480, 350)
  util.LoadParts(r2_28, r24_0("bom_title_"), 296, 170)
  util.LoadParts(r2_28, r26_0("witches/witches_chara_unlock16"), 90, 200)
  util.LoadParts(r2_28, r23_0("plate_bom_line"), 280, 260)
  if r21_0 then
    r18_0 = util.LoadBtn({
      rtImg = r2_28,
      fname = r28_0("confirm_01"),
      x = 360,
      y = 490,
      func = r41_0,
      param = r0_28,
    })
    util.LoadBtn({
      rtImg = r2_28,
      fname = r28_0("confirm_02"),
      x = 490,
      y = 490,
      func = r36_0,
      param = r0_28,
    })
    local r5_28 = 330
    local r6_28 = 440
    local r8_28 = util.MakeText3({
      rtImg = r2_28,
      x = r5_28,
      y = r6_28,
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
    local r9_28 = util.LoadParts(r2_28, r30_0("ticket_flare_s"), r5_28 + r8_28.width, r6_28 - 7)
    local r10_28 = {
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
    local r11_28 = nil
    if type(r22_0) ~= "string" then
      r11_28 = string.format("%d", r22_0)
    else
      r11_28 = r22_0
    end
    local r12_28 = 2
    local r13_28 = r5_28 + r8_28.width + 185
    local r14_28 = r6_28 + 10
    local r15_28 = util.LoadParts(r2_28, r29_0("ticket_num_mark"), r13_28 - 95, r14_28 - 7)
    for r19_28 = r11_28:len(), 1, -1 do
      local r21_28 = util.LoadParts(r2_28, r29_0(r10_28[tonumber(r11_28:sub(r19_28, r19_28)) + 1]), r13_28 + r15_28.width, r14_28 - 7)
      r21_28.x = r13_28 - r21_28.width + r12_28
    end
  else
    r18_0 = util.LoadBtn({
      rtImg = r2_28,
      fname = r28_0("confirm_01"),
      x = 360,
      y = 490,
      func = r42_0,
      param = r0_28,
    })
    util.LoadBtn({
      rtImg = r2_28,
      fname = r28_0("confirm_02"),
      x = 490,
      y = 490,
      func = r36_0,
      param = r0_28,
    })
    util.LoadBtn({
      rtImg = r2_28,
      fname = r24_0("add_crystal_"),
      x = 590,
      y = 436,
      func = r44_0,
      param = r0_28,
    })
    util.LoadParts(r2_28, r23_0("pocket_crystal"), 90, 436)
    local r5_28 = r31_0(r2_28, "Loading")
    r5_28:setReferencePoint(display.TopRightReferencePoint)
    r5_28.x = 315
    r5_28.y = 442
    r11_0 = r5_28
  end
  r9_0 = {}
  table.insert(r9_0, r34_0(1))
  if r21_0 then
    r17_0 = r35_0(r4_28, {
      270,
      271,
      322
    })
  else
    r17_0 = r35_0(r4_28, {
      270,
      271,
      string.format(db.GetMessage(272), util.ConvertDisplayCrystal(r3_0.getItemData(r3_0.pay_item_data.CharItemFlareNew)[2]))
    })
  end
  r17_0.isVisible = true
  return r1_28
end
local function r46_0(r0_29)
  -- line: [513, 536] id: 29
  local r1_29 = db.UsingItemList(_G.UserID)
  local r2_29 = {}
  for r6_29, r7_29 in pairs(r1_29) do
    r2_29[r7_29[1]] = true
  end
  for r6_29, r7_29 in pairs(r9_0) do
    local r10_29 = true
    if r2_29[r7_29.id] == nil then
      local r9_29 = false
    end
    if r10_29 then
      if r7_29.crystal <= r0_29 then
        r7_29.ok = true
      else
        r7_29.ok = false
      end
    end
  end
end
local function r47_0(r0_30)
  -- line: [538, 567] id: 30
  if r11_0 == nil then
    return 
  end
  if server.CheckError(r0_30) then
    server.NetworkError(35, nil)
    return 
  else
    local r1_30 = r2_0.decode(r0_30.response)
    if r1_30.status == 0 then
      local r2_30 = r1_30.coin
      r20_0 = r2_30
      local r3_30 = r11_0.x
      local r4_30 = r11_0.y
      local r5_30 = r11_0.parent
      if r11_0 then
        display.remove(r11_0)
      end
      r11_0 = nil
      local r6_30 = r31_0(r5_30, util.ConvertDisplayCrystal(r2_30))
      r6_30:setReferencePoint(display.TopRightReferencePoint)
      r6_30.x = r3_30
      r6_30.y = r4_30
      r11_0 = r6_30
      r46_0(r2_30)
      r1_0.ShowCoinInfo(r2_30)
    else
      server.ServerError(r1_30.status, nil)
    end
  end
end
local function r48_0()
  -- line: [569, 571] id: 31
  server.GetCoin(_G.UserToken, r47_0)
end
local function r49_0(r0_32)
  -- line: [573, 586] id: 32
  if db.CountQueue() > 0 then
    if r0_32.count >= 60 then
      util.setActivityIndicator(false)
      r5_0()
    end
    return 
  end
  timer.cancel(r0_32.source)
  util.setActivityIndicator(false)
  r48_0()
end
local function r50_0(r0_33)
  -- line: [588, 605] id: 33
  local r1_33 = nil	-- notice: implicit variable refs by block#[0]
  r16_0 = r1_33
  r1_33 = r0_33[1]
  local r2_33 = r0_33[2]
  util.setActivityIndicator(false)
  r7_0 = util.MakeMat(r1_33)
  r8_0 = r45_0(r2_33)
  r1_33:insert(r8_0)
  if db.CountQueue() <= 0 then
    r48_0()
  end
  r12_0 = char.AllPause(true)
end
function r4_0(r0_34, r1_34)
  -- line: [612, 632] id: 34
  r21_0 = false
  r22_0 = 0
  local r2_34 = db.GetInventoryItem(_G.UserInquiryID, _G.LoginItems["2"].id)
  if r2_34 > 0 then
    r21_0 = true
    r22_0 = r2_34
  end
  r19_0 = r1_34[2]
  r5_0()
  if _G.UserToken == nil then
    server.NetworkError(35, r6_0)
  else
    r50_0({
      r0_34,
      r1_34
    })
  end
end
function r5_0()
  -- line: [634, 660] id: 35
  if r14_0 then
    if r14_0.tween then
      transition.cancel(r14_0.tween)
    end
    if r14_0.ev then
      events.Delete(r14_0.ev)
    end
    if r14_0.vev then
      events.Delete(r14_0.vev)
    end
    r14_0 = nil
  end
  if r8_0 then
    display.remove(r8_0)
    r8_0 = nil
  end
  if r7_0 then
    display.remove(r7_0)
    r7_0 = nil
  end
  r11_0 = nil
  r10_0 = nil
  char.AllPause(true)
end
function r6_0()
  -- line: [669, 672] id: 38
  r19_0()
  r5_0()
end
return {
  Open = r4_0,
  Close = r5_0,
  Init = function()
    -- line: [662, 663] id: 36
  end,
  Cleanup = function()
    -- line: [665, 667] id: 37
    r5_0()
  end,
  ErrorClose = r6_0,
}
