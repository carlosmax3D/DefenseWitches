-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("tool.crystal")
local r1_0 = require("media")
local r2_0 = require("json")
local r3_0 = require("resource.char_define")
local function r4_0(r0_1)
  -- line: [13, 13] id: 1
  return "data/powerup/" .. r0_1 .. ".png"
end
local function r5_0(r0_2)
  -- line: [14, 14] id: 2
  return r4_0(r0_2 .. _G.UILanguage)
end
local function r6_0(r0_3)
  -- line: [15, 15] id: 3
  return "data/option/" .. r0_3 .. ".png"
end
local function r7_0(r0_4)
  -- line: [16, 16] id: 4
  return "data/shop/" .. r0_4 .. ".png"
end
local function r8_0(r0_5)
  -- line: [17, 17] id: 5
  return r7_0(r0_5 .. _G.UILanguage)
end
local function r9_0(r0_6)
  -- line: [18, 18] id: 6
  return "data/help/witches/" .. r0_6 .. ".png"
end
local r10_0 = nil
local r11_0 = nil
local r12_0 = nil
local r13_0 = nil
local r14_0 = nil
local r15_0 = nil
local r16_0 = nil
local r17_0 = nil
local r18_0 = r3_0.CharInformation
local r19_0 = nil
local r20_0 = nil
local r21_0 = nil
local function r22_0(r0_7, r1_7)
  -- line: [53, 68] id: 7
  if r11_0 then
    display.remove(r11_0)
    r11_0 = nil
  end
  r11_0 = util.MakeText3({
    rtImg = r0_7,
    size = 28,
    color = {
      184,
      163,
      191
    },
    shadow = {
      0,
      0,
      0
    },
    diff_x = 1,
    diff_y = 1,
    msg = db.GetMessage(222),
  })
  r11_0:setReferencePoint(display.CenterReferencePoint)
  r11_0.x = 480
  r11_0.y = 264
end
local function r23_0(r0_8, r1_8)
  -- line: [71, 110] id: 8
  if r0_8.focus == nil or r0_8.selected == nil or r0_8.buy_button == nil or r0_8.crystal_icon == nil or r0_8.crystal_num == nil then
    return 
  end
  if r1_8 == true then
    r21_0 = r0_8
  else
    r21_0 = nil
  end
  r0_8.focus = r1_8
  r0_8.selected.isVisible = r1_8
  if r0_8.buy_button then
    r0_8.buy_button.isVisible = r1_8
  end
  if r0_8.crystal_icon then
    r0_8.crystal_icon.isVisible = not r1_8
  end
  if r0_8.crystal_num then
    r0_8.crystal_num.isVisible = not r1_8
  end
  local r2_8 = r12_0[r0_8.id]
  if r2_8 then
    if r0_8.id == 11 or r0_8.id == 12 or r0_8.id == 14 or r0_8.id == 17 or r0_8.id == 18 then
      r2_8.isVisible = false
    else
      r2_8.isVisible = r1_8
    end
  end
end
local function r24_0(r0_9, r1_9)
  -- line: [112, 141] id: 9
  if r1_9.phase == "ended" then
    local r3_9 = r0_9.struct
    local r5_9 = r3_9.id
    if not r3_9.focus then
      local r6_9 = nil
      local r7_9 = nil
      for r11_9, r12_9 in pairs(r15_0) do
        r7_9 = r12_9.id == r5_9
        if r3_9.enable then
          r23_0(r12_9, false)
        end
      end
      if r3_9.enable then
        r23_0(r3_9, true)
      end
    elseif r3_9.enable then
      r23_0(r3_9, false)
    end
  end
  return true
end
local function r25_0()
  -- line: [143, 150] id: 10
  local r0_10 = r10_0.page_nr
  for r5_10 = 1, r10_0.page_max, 1 do
    r10_0.page[r5_10].isVisible = r5_10 == r0_10
    r10_0.pages[r5_10].isVisible = r5_10 == r0_10
  end
end
local function r26_0()
  -- line: [152, 178] id: 11
  local r0_11 = r10_0.page_max
  if r10_0.page ~= nil and type(r10_0.page) == "table" then
    for r4_11, r5_11 in pairs(r10_0.page) do
      r10_0.page[r4_11]:removeSelf()
    end
  end
  r10_0.page = {}
  local r1_11 = nil
  local r2_11 = r10_0.rtImg
  if r2_11 == nil then
    return 
  end
  for r6_11 = 1, r0_11, 1 do
    r1_11 = util.MakeText3({
      rtImg = r2_11,
      size = 25,
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
      diff_y = 1,
      msg = string.format("%02d/%02d", r6_11, r0_11),
    })
    r1_11.isVisible = false
    r1_11:setReferencePoint(display.CenterReferencePoint)
    r1_11.x = 482
    r1_11.y = 501
    r10_0.page[r6_11] = r1_11
  end
end
local function r27_0(r0_12)
  -- line: [180, 186] id: 12
  r1_0.playVideo("data/movie/" .. string.format("char_%2d.m4v", r0_12.param), false, movieClose)
  return true
end
local function r28_0(r0_13)
  -- line: [188, 197] id: 13
  if r0_13 then
    sound.PlaySE(2)
  end
  if r20_0 then
    util.ChangeScene({
      prev = Cleanup,
      scene = r20_0,
      efx = "overFromTop",
    })
  else
    util.ChangeScene({
      prev = Cleanup,
      scene = "title",
      efx = "overFromTop",
    })
  end
  return true
end
local function r29_0()
  -- line: [199, 206] id: 14
  for r4_14, r5_14 in pairs({
    "help",
    "help_pass"
  }) do
    if package.loaded[r5_14] then
      package.loaded[r5_14] = nil
    end
  end
end
local function r30_0()
  -- line: [208, 224] id: 15
  local r0_15 = display.newRect(r17_0, 0, 0, _G.Width, _G.Height)
  r0_15:setFillColor(0, 0, 0)
  r0_15.alpha = 0.01
  function r0_15.touch(r0_16, r1_16)
    -- line: [212, 212] id: 16
    return true
  end
  r0_15:addEventListener("touch")
  require("scene.parts.help_pass").View(r16_0, function()
    -- line: [215, 223] id: 17
    r29_0()
    r16_0:removeSelf()
    Close()
    new()
    return true
  end)
end
local function r31_0(r0_18, r1_18, r2_18)
  -- line: [226, 267] id: 18
  if _G.UserToken == nil then
    server.NetworkError(35, nil)
    return 
  end
  local r3_18 = _G.UserID
  if _G.IsSimulator then
    db.UnlockSummonData(r3_18, r0_18)
    db.UnlockL4SummonData(r3_18, r0_18)
    kpi.Unlock(r0_18, r2_18)
    DebugPrint("unlocked:" .. tostring(r0_18))
    _G.BingoManager.updateUserBingoData(_G.BingoManagerClass.MISSION_TYPE.UNLOCK_CHAR(), nil)
    r30_0()
  else
    util.setActivityIndicator(true)
    server.UseCoin(_G.UserToken, {
      r1_18
    }, {
      1
    }, function(r0_19)
      -- line: [247, 265] id: 19
      util.setActivityIndicator(false)
      if server.CheckError(r0_19) then
        server.NetworkError()
      else
        local r1_19 = r2_0.decode(r0_19.response)
        if r1_19.status == 0 then
          db.UnlockSummonData(r3_18, r0_18)
          db.UnlockL4SummonData(r3_18, r0_18)
          kpi.Unlock(r0_18, r2_18)
          r30_0()
          _G.BingoManager.updateUserBingoData(_G.BingoManagerClass.MISSION_TYPE.UNLOCK_CHAR(), nil)
        else
          server.ServerError(r1_19.status)
        end
      end
    end)
  end
end
local function r32_0()
  -- line: [269, 281] id: 20
  if r15_0 == nil then
    return 
  end
  local r0_20 = nil
  for r4_20, r5_20 in pairs(r15_0) do
    display.remove(r5_20.rtImg)
    r0_20 = r5_20.id
    if r12_0[r0_20] then
      display.remove(r12_0[r0_20])
      r12_0[r0_20] = nil
    end
  end
  r15_0 = nil
end
local function r33_0()
  -- line: [283, 290] id: 21
  if r10_0 and r10_0.rtImg then
    r10_0.rtImg.isVisible = true
  end
  util.setActivityIndicator(true)
  r32_0()
  RequestServerCoin()
end
local function r34_0(r0_22)
  -- line: [292, 309] id: 22
  if r0_22 == nil or r0_22.param == nil then
    return false
  end
  local r1_22 = r0_22.param
  if _G.IsSimulator then
    r33_0()
  else
    if r10_0 and r10_0.rtImg then
      r10_0.rtImg.isVisible = false
    end
    r0_0.Open(r1_22, {
      r33_0,
      nil
    })
  end
  return true
end
local function r35_0(r0_23)
  -- line: [311, 347] id: 23
  if r19_0 == nil or r0_23.param == nil or r0_23.param.sid == nil or r0_23.param.iid == nil then
    return 
  end
  sound.PlaySE(1)
  local r1_23 = r0_23.param.sid
  local r2_23 = r0_23.param.iid
  local r3_23 = r18_0[r1_23][3]
  dialog.Open(r19_0, 13, {
    string.format(db.GetMessage(14), util.ConvertDisplayCrystal(r3_23)),
    15
  }, {
    function(r0_24)
      -- line: [328, 339] id: 24
      sound.PlaySE(1)
      dialog.Close()
      if r3_23 <= r14_0 then
        r31_0(r1_23, r2_23, r3_23)
      else
        r34_0({
          param = r19_0,
        })
      end
      return true
    end,
    function(r0_25)
      -- line: [340, 345] id: 25
      sound.PlaySE(2)
      dialog.Close()
      return true
    end
  })
  return true
end
local function r36_0(r0_26, r1_26)
  -- line: [349, 413] id: 26
  local r2_26 = {}
  local r3_26 = display.newGroup()
  local r4_26 = nil
  local r5_26 = r18_0[r0_26]
  util.LoadParts(r3_26, r8_0(r5_26[2]), 0, 0)
  local r6_26 = display.newGroup()
  r2_26.crystal_icon = util.LoadParts(r6_26, r4_0("powerup_crystal1"), 0, 228)
  local r7_26 = {}
  local r8_26 = true
  if r5_26[3] <= r14_0 then
    r7_26 = {
      204,
      0,
      68
    }
  else
    r7_26 = {
      128,
      128,
      128
    }
  end
  r2_26.crystal_num = util.MakeText3({
    rtImg = r6_26,
    x = 32,
    y = 232,
    size = 28,
    color = r7_26,
    shadow = {
      0,
      0,
      0
    },
    diff_x = 1,
    diff_y = 1,
    msg = string.format("%d", util.ConvertDisplayCrystal(r5_26[3])),
  })
  r3_26:insert(r6_26)
  r6_26:setReferencePoint(display.CenterReferencePoint)
  r6_26.x = r3_26.width * 0.5
  r4_26 = util.LoadParts(r3_26, r7_0("item_select1"), 0, 0)
  r4_26.isVisible = false
  r2_26.selected = r4_26
  local r9_26 = util.LoadBtn({
    rtImg = r3_26,
    fname = r8_0("cha_buy_"),
    x = 0,
    y = 224,
    func = r35_0,
    param = {
      sid = r0_26,
      iid = r5_26[1],
    },
  })
  r9_26.x = r3_26.width * 0.5
  r9_26.isVisible = false
  r2_26.buy_button = r9_26
  r3_26.struct = r2_26
  r3_26.touch = r24_0
  r3_26:addEventListener("touch")
  if r1_26 then
    r2_26.movie = util.LoadBtn({
      rtImg = r3_26,
      fname = r8_0("movie_play_"),
      x = 3,
      y = 284,
      func = r27_0,
      param = r0_26,
    })
  end
  r2_26.rtImg = r3_26
  r2_26.id = r0_26
  r2_26.focus = false
  r2_26.is_checked = false
  r2_26.itemid = r5_26[1]
  r2_26.coin = r5_26[3]
  r2_26.enable = r8_26
  r2_26.msg_id = r5_26[4]
  return r2_26
end
local function r37_0(r0_27, r1_27, r2_27)
  -- line: [415, 442] id: 27
  if r2_27 == nil then
    r2_27 = false
  end
  local r3_27 = display.newGroup()
  local r4_27 = nil
  local r5_27 = nil
  local r6_27 = 0
  for r10_27, r11_27 in pairs(r1_27) do
    if r0_27[r11_27] == 3 then
      r5_27 = r36_0(r11_27, r2_27)
      r4_27 = r5_27.rtImg
      r3_27:insert(r4_27)
      r4_27:setReferencePoint(display.TopLeftReferencePoint)
      r4_27.x = r6_27
      r4_27.y = 0
      r6_27 = r6_27 + 192
      table.insert(r15_0, r5_27)
    end
  end
  if r10_0 == nil or r10_0.rtImg == nil or r6_27 == 0 then
    return nil
  end
  r10_0.rtImg:insert(r3_27)
  r3_27:setReferencePoint(display.TopCenterReferencePoint)
  r3_27.x = _G.Width * 0.5
  r3_27.y = 96
  r3_27.isVisible = false
  return r3_27
end
local function r38_0()
  -- line: [444, 468] id: 28
  local r4_28 = nil	-- notice: implicit variable refs by block#[4]
  local r5_28 = nil	-- notice: implicit variable refs by block#[4]
  local r6_28 = nil	-- notice: implicit variable refs by block#[4]
  if _G.UILanguage == "jp" then
    r6_28 = 386
    r4_28 = 24
    r5_28 = 36
  else
    r6_28 = 382
    r4_28 = 20
    r5_28 = 24
  end
  local r3_28 = r12_0.rtImg
  for r10_28, r11_28 in pairs(r15_0) do
    local r0_28 = r11_28.id
    local r2_28 = util.MakeText2({
      rtImg = r3_28,
      size = r4_28,
      line = r5_28,
      x = 16,
      y = r6_28,
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
      msg = db.GetMessage(r11_28.msg_id),
    })
    r2_28.isVisible = false
    r12_0[r0_28] = r2_28
  end
end
local r39_0 = {
  2,
  3,
  4,
  6,
  7,
  8,
  9,
  10,
  13
}
local function r40_0()
  -- line: [472, 521] id: 29
  local r0_29 = _G.UserID
  local r1_29, r2_29 = db.LoadSummonData(r0_29)
  if #r1_29 < 1 then
    db.InitSummonData(r0_29)
    r1_29, r2_29 = db.LoadSummonData(r0_29)
  end
  r15_0 = {}
  local r3_29 = nil
  local r4_29 = nil
  r4_29 = {}
  r3_29 = r37_0(r1_29, {
    11,
    12,
    14,
    17,
    18
  }, true)
  if r3_29 then
    table.insert(r4_29, r3_29)
  end
  local r5_29 = {}
  for r9_29, r10_29 in pairs(r39_0) do
    local r11_29 = r1_29[r10_29]
    if r11_29 == 3 then
      table.insert(r5_29, r10_29)
      r11_29 = #r5_29
      if r11_29 >= 5 then
        r11_29 = table.insert
        local r12_29 = r4_29
        r11_29(r12_29, r37_0(r1_29, {
          unpack(r5_29)
        }))
        r11_29 = {}
        r5_29 = r11_29
      end
    end
  end
  if #r5_29 > 0 then
    table.insert(r4_29, r37_0(r1_29, {
      unpack(r5_29)
    }))
  end
  if #r4_29 > 0 then
    r38_0()
    r11_0.isVisible = false
    r10_0.pages = r4_29
    r10_0.page_nr = 1
    r10_0.page_max = #r4_29
    r26_0()
    r25_0()
  elseif r10_0 then
    r10_0.next.isVisible = false
    r10_0.prev.isVisible = false
    r10_0.page_plate.isVisible = false
    r22_0(rtImg, db.GetMessage(222))
  end
end
local function r41_0(r0_30)
  -- line: [523, 548] id: 30
  assert(r13_0 and r13_0.rtImg, debug.traceback())
  local r1_30 = r13_0.rtImg
  if r13_0.spr then
    display.remove(r13_0.spr)
  end
  local r2_30 = nil
  if type(r0_30) == "string" then
    r2_30 = r0_30
  else
    r2_30 = util.Num2Column(r0_30)
  end
  local r3_30 = nil
  r3_30 = util.MakeText3({
    size = 40,
    color = {
      255,
      225,
      76
    },
    shadow = {
      0,
      0,
      0
    },
    diff_x = 1,
    diff_y = 1,
    msg = r2_30,
  })
  r1_30:insert(r3_30)
  r3_30:setReferencePoint(display.CenterRightReferencePoint)
  r3_30.x = 224
  r3_30.y = 576
  r13_0.spr = r3_30
end
local function r42_0(r0_31)
  -- line: [550, 567] id: 31
  util.setActivityIndicator(false)
  if r13_0 == nil then
    return 
  end
  if server.CheckError(r0_31) then
    server.NetworkError(35)
  else
    local r1_31 = r2_0.decode(r0_31.response)
    if r1_31.status == 0 then
      r41_0(util.ConvertDisplayCrystal(r1_31.coin))
      r14_0 = r1_31.coin
      r40_0()
      r0_0.ShowCoinInfo(r1_31.coin)
    else
      server.ServerError(r1_31.status)
    end
  end
end
local function r43_0(r0_32)
  -- line: [569, 590] id: 32
  for r4_32, r5_32 in pairs(r15_0) do
    local r6_32 = r5_32.id
    if r12_0[r6_32] then
      r12_0[r6_32].isVisible = false
    end
  end
  local r2_32 = r10_0.page_max
  local r1_32 = r10_0.page_nr + r0_32
  if r1_32 < 1 or r2_32 < r1_32 then
    sound.PlaySE(2)
    return 
  end
  sound.PlaySE(1)
  r10_0.page_nr = r1_32
  r25_0()
end
local function r44_0(r0_33)
  -- line: [592, 594] id: 33
  r43_0(-1)
end
local function r45_0(r0_34)
  -- line: [596, 598] id: 34
  r43_0(1)
end
function new(r0_35)
  -- line: [600, 706] id: 35
  local r1_35 = nil	-- notice: implicit variable refs by block#[0]
  r11_0 = r1_35
  r12_0 = nil
  r13_0 = nil
  r14_0 = nil
  r15_0 = nil
  r16_0 = nil
  r17_0 = nil
  r18_0 = r3_0.CharInformation
  r19_0 = nil
  r1_35 = nil
  r20_0 = r1_35
  if r0_35 ~= nil then
    r1_35 = r0_35.closeScene
    if r1_35 then
      r1_35 = r0_35.closeScene
      r20_0 = r1_35
    end
  end
  events.SetNamespace("shop")
  r1_35 = display.newGroup()
  local r2_35 = util.MakeGroup(r1_35)
  r17_0 = util.MakeGroup(r1_35)
  r16_0 = util.MakeGroup(r1_35)
  local r3_35 = util.MakeFrame(r1_35)
  local r4_35 = nil
  r2_35:addEventListener("touch", function(r0_36)
    -- line: [626, 632] id: 36
    if r0_36.phase == "ended" and r21_0 then
      r23_0(r21_0, false)
    end
  end)
  r19_0 = r1_35
  for r8_35 = 0, _G.Width - 16, 16 do
    util.LoadParts(r2_35, r7_0("shop_bg01"), r8_35, 0)
    util.LoadParts(r2_35, r7_0("shop_bg02"), r8_35, 128)
    util.LoadParts(r2_35, r7_0("shop_bg03"), r8_35, 256)
    util.LoadParts(r2_35, r7_0("shop_bg04"), r8_35, 384)
    util.LoadParts(r2_35, r7_0("shop_bg05"), r8_35, 512)
  end
  util.LoadParts(r2_35, r8_0("shop_title_"), 248, 36)
  for r8_35 = 0, 768, 256 do
    util.LoadParts(r2_35, r6_0("option_line01"), r8_35, 88)
    util.LoadParts(r2_35, r6_0("option_line02"), r8_35, 470)
    util.LoadParts(r2_35, r6_0("option_line01"), r8_35, 534)
  end
  r10_0 = {}
  r10_0.prev = util.LoadBtn({
    rtImg = r2_35,
    fname = r7_0("shop_page_button01"),
    x = 248,
    y = 479,
    func = r44_0,
  })
  r10_0.next = util.LoadBtn({
    rtImg = r2_35,
    fname = r7_0("shop_page_button02"),
    x = 520,
    y = 479,
    func = r45_0,
  })
  r10_0.page_plate = util.LoadParts(r2_35, r7_0("shop_page_plate"), 440, 487)
  r10_0.rtImg = r2_35
  r22_0(r2_35, "Loading...")
  r12_0 = {}
  r12_0.rtImg = r2_35
  util.LoadParts(r2_35, r9_0("pocket_crystal"), 0, 544)
  r13_0 = {}
  r13_0.rtImg = r2_35
  r13_0.spr = nil
  r41_0("Loading")
  util.LoadBtn({
    rtImg = r2_35,
    fname = r5_0("add_crystal_"),
    x = 720,
    y = 544,
    func = r34_0,
    param = r1_35,
  })
  util.LoadBtn({
    rtImg = r2_35,
    fname = r6_0("close"),
    x = 872,
    y = 0,
    func = r28_0,
  })
  if _G.UserToken == nil then
    server.NetworkError(35, r28_0)
  else
    r14_0 = 0
    util.setActivityIndicator(false)
    RequestServerCoin()
  end
  return r1_35
end
function RequestServerCoin()
  -- line: [709, 711] id: 37
  server.GetCoin(_G.UserToken, r42_0)
end
function Close()
  -- line: [713, 724] id: 38
  if r13_0 then
    if r13_0.spr then
      display.remove(r13_0.spr)
    end
    r13_0 = nil
  end
  if r10_0 and r10_0.rtImg then
    display.remove(r10_0.rtImg)
    r10_0.rtImg = nil
  end
end
function Cleanup()
  -- line: [726, 729] id: 39
  Close()
  events.DeleteNamespace("shop")
end
