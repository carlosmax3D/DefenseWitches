-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("ad.myads")
local r1_0 = nil
local r26_0 = nil	-- notice: implicit variable refs by block#[2]
local r36_0 = nil	-- notice: implicit variable refs by block#[2]
if not _G.IsSimulator then
  r1_0 = require("plugin.google.iap.v3")
end
local r2_0 = require("json")
local r3_0 = nil
local r4_0 = nil
local r5_0 = "coin.dat"
local r6_0 = {}
local r7_0 = nil
local r8_0 = nil
local r9_0 = nil
local r10_0 = nil
local r11_0 = nil
local r12_0 = nil
local r13_0 = nil
local r14_0 = nil
local r15_0 = nil
local r16_0 = nil
local r17_0 = nil
local function r18_0(r0_1)
  -- line: [31, 31] id: 1
  return "data/ads" .. "/" .. r0_1 .. ".png"
end
local function r19_0(r0_2)
  -- line: [33, 33] id: 2
  return r18_0(r0_2 .. _G.UILanguage)
end
local function r20_0(r0_3)
  -- line: [35, 35] id: 3
  return "data/powerup/" .. r0_3 .. ".png"
end
local function r21_0(r0_4)
  -- line: [37, 45] id: 4
  local r2_4 = io.open(system.pathForFile(r5_0, system.DocumentsDirectory), "w")
  if r2_4 then
    r2_4:write(r0_4 .. "\n")
    io.close(r2_4)
  end
end
function r10_0()
  -- line: [47, 56] id: 5
  local r1_5 = io.open(system.pathForFile(r5_0, system.DocumentsDirectory), "r")
  local r2_5 = 0
  if r1_5 then
    r2_5 = tonumber(r1_5:read())
    io.close(r1_5)
  end
  return r2_5
end
function r12_0(r0_7)
  -- line: [64, 84] id: 7
  server.GetCoin(_G.UserToken, function(r0_8)
    -- line: [65, 81] id: 8
    if not r0_8.isError then
      local r1_8 = r2_0.decode(r0_8.response)
      if r1_8.status == 0 then
        local r2_8 = r1_8.coin
        if r0_7 then
          r16_0(r2_8)
        else
          r21_0(r2_8)
        end
      end
    end
  end)
end
function r16_0(r0_9)
  -- line: [86, 100] id: 9
  if r0_9 == nil then
    return 
  end
  local r1_9 = r10_0()
  if 0 < r1_9 and r1_9 < r0_9 and _G.UILanguage == "jp" then
    native.showAlert("DefenseWitches", "クリスタルが追加されました。", {
      "OK"
    }, callback)
  end
  r21_0(r0_9)
end
local function r22_0(r0_10)
  -- line: [103, 110] id: 10
  for r4_10, r5_10 in pairs(r6_0) do
    if r5_10.domain == r0_10 then
      return r5_10
    end
  end
  return nil
end
local function r23_0(r0_11)
  -- line: [113, 128] id: 11
  local r1_11 = r0_11.param
  if r1_11.loading then
    return true
  end
  util.setActivityIndicator(true)
  kpi.CreditAttempt()
  r9_0 = r1_11.domain
  r1_0.purchase(r9_0)
  return true
end
local function r24_0(r0_12, r1_12)
  -- line: [133, 176] id: 12
  util.setActivityIndicator(false)
  local r2_12 = false
  local r3_12 = -1
  if not r0_12.isError then
    r3_12 = r2_0.decode(r0_12.response).status
    if r3_12 == 0 or r3_12 == 6 then
      r12_0(true)
      r2_12 = true
    end
  end
  if r2_12 == false and r1_12 == 0 then
    native.showAlert("DefenseWitches", string.format(db.GetMessage(206), r3_12) .. "\n" .. db.GetMessage(207), {
      db.GetMessage(209)
    }, function(r0_13)
      -- line: [163, 172] id: 13
      if r0_13.action == "clicked" then
        local r1_13 = r0_13.index
        if r1_13 ~= 1 and r1_13 == 2 then
        end
      end
    end)
  end
  return r2_12
end
local function r25_0(r0_14)
  -- line: [179, 199] id: 14
  local r1_14 = r22_0(r0_14)
  local r2_14 = nil
  local r3_14 = nil
  if r1_14 then
    r2_14 = r1_14.localizedPrice
    r3_14 = r1_14.crystal
    if r2_14 == nil then
      r2_14 = db.GetStoreLocalizedPrice(r0_14)
      if r2_14 == nil then
        r2_14 = "\\0"
      end
    end
  else
    r2_14 = "not found localize price"
    r3_14 = 0
  end
  return r2_14, r3_14
end
function r26_0(r0_15)
  -- line: [202, 274] id: 15
  local r1_15 = r0_15.transaction
  local function r2_15(r0_16)
    -- line: [205, 221] id: 16
    local r2_16 = native.showAlert("Corona", r0_16, {
      "OK",
      "Learn More"
    }, function(r0_17)
      -- line: [207, 217] id: 17
      if r0_17.action == "clicked" then
        local r1_17 = r0_17.index
        if r1_17 ~= 1 and r1_17 == 2 then
          system.openURL("http://www.coronalabs.com")
        end
      end
    end)
  end
  local r4_15 = nil	-- notice: implicit variable refs by block#[7, 10]
  local r5_15 = nil	-- notice: implicit variable refs by block#[10]
  if r1_15.state == "purchased" then
    local r3_15 = r1_15.productIdentifier
    r4_15 = r1_15.receipt
    r5_15 = r1_15.signature
    local r6_15 = r22_0(r3_15)
    local r7_15, r8_15 = r25_0(r3_15)
    r1_0.consumePurchase({
      r3_15
    }, r26_0)
    local r9_15 = _G.UserToken
    server.BuyCoin(r9_15, r4_15, r5_15, function(r0_18, r1_18)
      -- line: [241, 250] id: 18
      if r24_0(r0_18, r1_18) == true then
        kpi.Credit(r8_15)
        _G.metrics._crystal_charge(r6_15, r1_15)
      else
        db.AddInvalidTransaction(_G.UserInquiryID, r9_15, r4_15, r5_15, r7_15)
      end
      r15_0()
    end)
    -- close: r3_15
  elseif r1_15.state == "restore" then
    r15_0()
  elseif r1_15.state == "cancelled" or r1_15.state == "failed" and r1_15.errorType == -1005 then
    r4_15 = 36
    server.NetworkError(r4_15)
    r15_0()
  elseif r1_15.state == "failed" then
    if r1_15.errorType == 7 then
      local r3_15 = r1_0.consumePurchase
      r4_15 = {}
      r5_15 = r9_0
      -- setlist for #4 failed
      r5_15 = r26_0
      r3_15(r4_15, r5_15)
    end
    server.NetworkError()
    r15_0()
  elseif r1_15.state == "consumed" then
  end
end
local function r27_0(r0_19, r1_19, r2_19)
  -- line: [276, 296] id: 19
  if r2_19 == nil then
    r2_19 = 28
  end
  local r3_19 = nil
  if type(r1_19) == "string" then
    r3_19 = r1_19
  else
    r3_19 = util.Num2Column(r1_19)
  end
  local r4_19 = display.newGroup()
  local r5_19 = nil
  display.newText(r4_19, r3_19, 1, 1, native.systemFontBold, r2_19):setFillColor(0, 0, 0)
  display.newText(r4_19, r3_19, 0, 0, native.systemFontBold, r2_19):setFillColor(255, 221, 51)
  if r0_19 ~= nil then
    r0_19:insert(r4_19)
  end
  return r4_19
end
local function r28_0(r0_20, r1_20)
  -- line: [298, 315] id: 20
  local r2_20 = nil	-- notice: implicit variable refs by block#[3]
  if r1_20 == nil then
    r2_20 = "Loading"
  else
    r2_20 = r1_20
  end
  local r3_20 = display.newGroup()
  local r4_20 = nil
  display.newText(r3_20, r2_20, 1, 1, native.systemFontBold, 28):setFillColor(0, 0, 0)
  display.newText(r3_20, r2_20, 0, 0, native.systemFontBold, 28):setFillColor(255, 255, 255)
  r0_20:insert(r3_20)
  return r3_20
end
local function r29_0()
  -- line: [317, 420] id: 21
  local function r0_21(r0_22)
    -- line: [318, 318] id: 22
    return "data/crystal/" .. r0_22 .. ".png"
  end
  local function r1_21(r0_23)
    -- line: [320, 320] id: 23
    return r0_21(r0_23 .. _G.UILanguage)
  end
  local function r2_21(r0_24)
    -- line: [322, 322] id: 24
    return "data/option/" .. r0_24 .. ".png"
  end
  local r3_21 = display.newGroup()
  local r4_21 = require("common.base_dialog")
  local r5_21 = r4_21.Create(r3_21, 960, 650, {
    direction = "down",
    color = {
      {
        118,
        71,
        119
      },
      {
        50,
        39,
        66
      }
    },
    alpha = 0.95,
  }, true, false)
  local r6_21 = display.newImage(r5_21, r1_21("addcrystal_title_"), 240, 40, true)
  local function r7_21(r0_25, r1_25, r2_25)
    -- line: [333, 337] id: 25
    for r6_25 = 96, 608, 256 do
      display.newImage(r0_25, r1_25, r6_25, r2_25, true)
    end
  end
  r7_21(r5_21, r2_21("option_line01"), 96)
  local r8_21 = nil
  local r9_21 = 128
  for r13_21, r14_21 in pairs(r6_0) do
    local r15_21 = #r6_0
    if r13_21 < r15_21 then
      r7_21(r5_21, r2_21("option_line02"), r9_21 + 48)
    end
    r8_21 = r27_0(r5_21, util.ConvertDisplayCrystal(r14_21.crystal))
    r8_21:setReferencePoint(display.TopRightReferencePoint)
    r8_21.x = 236
    r8_21.y = r9_21
    r8_21 = r28_0(r5_21)
    r8_21:setReferencePoint(display.TopRightReferencePoint)
    r8_21.x = 584
    r8_21.y = r9_21 - 10
    r14_21.spr = r8_21
    r14_21.loading = true
    display.newImage(r5_21, r0_21("crystal"), 95, r9_21 - 16, true)
    util.LoadBtn({
      rtImg = r5_21,
      fname = r1_21("purchase_"),
      x = 648,
      y = r9_21 - 16,
      func = r23_0,
      param = r14_21,
    })
    r9_21 = r9_21 + 64
  end
  r7_21(r5_21, r2_21("option_line01"), r9_21 + 88)
  r9_21 = r9_21 - 8
  local r10_21 = display.newGroup()
  r5_21:insert(r10_21)
  r10_21.x = 632
  r10_21.y = r9_21
  local r11_21 = util.LoadParts(r10_21, r20_0("pocket_crystal"), 0, 0)
  r8_21 = r27_0(r10_21, "Loading", 40)
  r8_21:setReferencePoint(display.TopRightReferencePoint)
  r8_21.x = 224
  r8_21.y = 20
  r7_0 = r8_21
  r4_21.CreateCloseButton(r5_21, r5_21.width - 49, 57, function(r0_26)
    -- line: [399, 402] id: 26
    r15_0()
    return true
  end)
  if r0_0.GetLastRes() then
    util.LoadBtn({
      rtImg = r5_21,
      fname = r19_0("offerwall_02_"),
      x = 96,
      y = r9_21,
      func = function(r0_27)
        -- line: [405, 408] id: 27
        wallAds.show()
        return true
      end,
    })
  end
  return r3_21
end
local function r30_0(r0_28)
  -- line: [422, 453] id: 28
  for r8_28, r9_28 in pairs(r0_28.products) do
    local r4_28 = r22_0(r9_28.productIdentifier)
    if r4_28 == nil then
      return 
    end
    if r4_0 == nil then
      return 
    end
    r4_28.title = r9_28.title
    r4_28.description = r9_28.description
    r4_28.price = r9_28.priceAmountMicros / 1000000
    r4_28.localizedPrice = r9_28.localizedPrice
    if r9_28.priceCurrencyCode then
      r4_28.priceLocale = string.sub(r9_28.priceCurrencyCode, -3)
    end
    assert(r4_28.spr)
    local r2_28 = r4_28.spr.x
    local r3_28 = r4_28.spr.y
    display.remove(r4_28.spr)
    local r1_28 = r28_0(r4_0, r4_28.localizedPrice)
    r1_28:setReferencePoint(display.TopRightReferencePoint)
    r1_28.x = r2_28
    r1_28.y = r3_28
    r4_28.spr = r1_28
    r4_28.loading = false
  end
  db.RestoreStoreData(r6_0)
end
local function r31_0()
  -- line: [455, 461] id: 29
  local r0_29 = {}
  for r4_29, r5_29 in pairs(r6_0) do
    table.insert(r0_29, r5_29.domain)
  end
  r1_0.loadProducts(r0_29, r30_0)
end
local function r32_0(r0_30, r1_30)
  -- line: [466, 486] id: 30
  local r2_30 = 0
  local r3_30 = 0
  local r4_30 = nil
  if r0_30 ~= nil then
    r2_30 = r0_30.x
    r3_30 = r0_30.y
    r4_30 = r0_30.parent
  end
  if r0_30 then
    display.remove(r0_30)
  end
  r0_30 = nil
  local r5_30 = r27_0(r4_30, util.ConvertDisplayCrystal(r1_30), 40)
  r5_30:setReferencePoint(display.TopRightReferencePoint)
  r5_30.x = r2_30
  r5_30.y = r3_30
  return r5_30
end
local function r33_0(r0_31)
  -- line: [488, 502] id: 31
  if r0_31.isError then
    server.NetworkError(35, r15_0)
  else
    local r1_31 = r2_0.decode(r0_31.response)
    if r1_31.status == 0 then
      local r2_31 = r1_31.coin
      r7_0 = r32_0(r7_0, r2_31)
      r16_0(r2_31)
    else
      server.ServerError(r1_31.status, r15_0)
    end
  end
end
local function r34_0(r0_32)
  -- line: [504, 514] id: 32
  if _G.UserToken == nil then
    server.NetworkError(35, r15_0)
    return 
  end
  util.setActivityIndicator(false)
  r4_0 = r29_0(r0_32)
  r0_32:insert(r4_0)
  r31_0()
  server.GetCoin(_G.UserToken, r33_0)
end
local function r35_0(r0_33)
  -- line: [516, 541] id: 33
  if _G.IsSimulator then
    r15_0()
    return 
  end
  r1_0.init(r26_0)
  if not r1_0.canMakePurchases then
    server.NetworkError(34, r15_0)
    return 
  end
  r6_0 = db.LoadAppData()
  r3_0 = util.MakeMat(r0_33)
  if _G.UserToken == nil then
    util.setActivityIndicator(true)
    if not server.Init({
      r34_0,
      r0_33
    }) then
      server.NetworkError(35, r15_0)
      return 
    end
  else
    r34_0(r0_33)
  end
end
function r36_0(r0_34, r1_34, r2_34, r3_34, r4_34)
  -- line: [545, 565] id: 34
  server.BuyCoin(r0_34, r1_34, r2_34, r3_34, r4_34, function(r0_35, r1_35)
    -- line: [546, 564] id: 35
    if r24_0(r0_35, r1_35) == true then
      db.DeleteInvalidTransaction(_G.UserInquiryID, r2_34)
      local r3_35, r4_35 = r25_0(r1_34.productId)
      kpi.Credit(r4_35)
    else
      r1_35 = r1_35 + 1
      if r1_35 > 10 then
        db.DeleteInvalidTransaction(_G.UserInquiryID, r2_34)
        server.NetworkError(210)
      else
        r36_0(r0_34, r1_34, r2_34, r3_34, r1_35)
      end
    end
  end)
end
local function r37_0()
  -- line: [570, 578] id: 36
  local r0_36 = db.LoadInvalidTransactionsData()
  if r0_36 == nil then
    return 
  end
  for r4_36, r5_36 in ipairs(r0_36) do
    r36_0(r5_36.token, r5_36.receipt, r5_36.signature, r5_36.price, 1)
  end
end
function r15_0()
  -- line: [595, 612] id: 38
  util.setActivityIndicator(false)
  if r3_0 then
    display.remove(r3_0)
    r3_0 = nil
  end
  if r4_0 then
    display.remove(r4_0)
    r4_0 = nil
  end
  r7_0 = nil
  if r8_0 then
    r8_0 = nil
    r8_0[1](r8_0[2])
  end
end
function r17_0(r0_39)
  -- line: [617, 698] id: 39
  local r1_39 = {}
  local r2_39 = true
  local r3_39 = 0
  local function r4_39(r0_40, r1_40)
    -- line: [625, 665] id: 40
    local function r2_40(r0_41)
      -- line: [627, 636] id: 41
      if r1_40 ~= nil then
        r1_40(r3_39)
      end
      if r0_39 then
        r0_39(r0_41)
      end
    end
    if r0_40.isError then
      if r2_39 then
        server.NetworkError(35, function()
          -- line: [641, 643] id: 42
          r2_40(nil)
        end)
      else
        r2_40(nil)
      end
    else
      local r3_40 = r2_0.decode(r0_40.response)
      if r3_40.status == 0 then
        r3_39 = r3_40.coin
        r2_40(r3_39)
      elseif r2_39 then
        server.ServerError(r3_40.status, function()
          -- line: [657, 659] id: 43
          r2_40(nil)
        end)
      else
        r2_40(nil)
      end
    end
  end
  function r1_39.update(r0_44, r1_44)
    -- line: [670, 685] id: 44
    if r1_44 == nil then
      r2_39 = true
    else
      r2_39 = r1_44
    end
    r3_39 = 0
    if r0_44 ~= nil then
      server.GetCoin(_G.UserToken, function(r0_45)
        -- line: [679, 681] id: 45
        r4_39(r0_45, r0_44)
      end)
    else
      server.GetCoin(_G.UserToken, r4_39)
    end
  end
  function r1_39.getPocketCrystal()
    -- line: [690, 692] id: 46
    return r3_39
  end
  r1_39.update()
  return r1_39
end
local r38_0 = r17_0()
local r39_0 = {
  new = function(r0_47, r1_47, r2_47)
    -- line: [707, 821] id: 47
    local r3_47 = {}
    local r4_47 = 0
    local r5_47 = nil
    local r6_47 = nil
    local function r7_47(r0_48)
      -- line: [720, 725] id: 48
      if r0_48 then
        r4_47 = r0_48
        r6_47 = r32_0(r6_47, r0_48)
      end
    end
    local r8_47 = nil
    function r3_47.update()
      -- line: [765, 770] id: 50
      r4_47 = 0
      if r8_47 ~= nil then
        r8_47.update(r7_47)
      end
    end
    function r3_47.visible(r0_51)
      -- line: [775, 779] id: 51
      if r5_47 then
        r5_47.isVisible = r0_51
      end
    end
    function r3_47.setPosition(r0_52, r1_52, r2_52)
      -- line: [784, 794] id: 52
      if r5_47 == nil then
        return 
      end
      if r2_52 ~= nil then
        r5_47:setReferencePoint(r2_52)
      end
      r5_47.x = r0_52
      r5_47.y = r1_52
    end
    function r3_47.getPocketCrystal()
      -- line: [799, 801] id: 53
      return r4_47
    end
    function r3_47.cleanup()
      -- line: [806, 815] id: 54
      if r6_47 then
        display.remove(r6_47)
        r6_47 = nil
      end
      if r5_47 then
        display.remove(r5_47)
        r5_47 = nil
      end
    end
    (function(r0_49, r1_49, r2_49)
      -- line: [732, 760] id: 49
      if r38_0 ~= nil then
        r8_47 = r38_0
      else
        r8_47 = r17_0()
      end
      r5_47 = display.newGroup()
      r5_47.parent = r0_49
      local r3_49 = util.LoadParts(r5_47, r20_0("pocket_crystal"), 0, 0)
      r5_47:setReferencePoint(display.TopLeftReferencePoint)
      r5_47.x = r1_49
      r5_47.y = r2_49
      local r4_49 = r27_0(r5_47, "Loading", 40)
      r4_49:setReferencePoint(display.TopRightReferencePoint)
      r4_49.x = r3_49.x + r3_49.width - 15
      r4_49.y = r3_49.y + 20
      r6_47 = r4_49
      r3_47.update()
      r5_47.isVisible = false
    end)(r0_47, r1_47, r2_47)
    return r3_47
  end,
}
local r40_0 = {
  LoadCoin = r10_0,
  LocalUpdateCoin = function(r0_6)
    -- line: [58, 62] id: 6
    r21_0(r10_0() + r0_6)
  end,
  UpdateCoin = r12_0,
  ShowCoinInfo = r16_0,
  Open = function(r0_37, r1_37)
    -- line: [580, 593] id: 37
    r15_0()
    r8_0 = r1_37
    if db.CountQueue() > 0 then
      server.NetworkError(33)
      r15_0()
    else
      r35_0(r0_37)
    end
    r37_0()
  end,
  Close = r15_0,
}
r40_0.ShowCoinInfo = r16_0
r40_0.PocketCrystalAccess = r17_0
function r40_0.GetPocketCrystal()
  -- line: [832, 834] id: 55
  return r38_0 or r17_0()
end
r40_0.possessingCrystal = r39_0
return r40_0
