-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("tool.crystal")
local r1_0 = require("common.sidebar_banner")
local r2_0 = require("shop.shop_summon_plate")
local r3_0 = require("resource.char_define")
local r4_0 = require("json")
local r5_0 = nil
local r6_0 = nil
local r7_0 = nil
local r8_0 = nil
local r9_0 = {}
local r10_0 = nil
local r11_0 = nil
local r12_0 = nil
local r13_0 = {}
local function r14_0(r0_1)
  -- line: [25, 25] id: 1
  return "data/option/" .. r0_1 .. ".png"
end
local function r15_0(r0_2, r1_2)
  -- line: [27, 27] id: 2
  return string.format("data/map/%s/%s.png", r0_2, r1_2)
end
local function r16_0(r0_3)
  -- line: [29, 29] id: 3
  return "data/shop/" .. r0_3 .. ".png"
end
local function r17_0(r0_4)
  -- line: [31, 31] id: 4
  return r16_0(r0_4 .. _G.UILanguage)
end
local function r18_0(r0_5)
  -- line: [33, 33] id: 5
  return "data/help/witches/" .. r0_5 .. ".png"
end
local function r19_0(r0_6)
  -- line: [35, 35] id: 6
  return "data/powerup/" .. r0_6 .. ".png"
end
local function r20_0(r0_7)
  -- line: [37, 37] id: 7
  return r19_0(r0_7 .. _G.UILanguage)
end
local function r21_0(r0_8)
  -- line: [39, 42] id: 8
  ExpManager.AddExp(r0_8)
  ExpManager.SaveExp()
end
local function r22_0(r0_9)
  -- line: [44, 46] id: 9
  OrbManager.AddMaxOrb(r0_9)
end
local function r23_0(r0_10)
  -- line: [48, 77] id: 10
  assert(r7_0 and r7_0.rtImg, debug.traceback())
  local r1_10 = r7_0.rtImg
  if r7_0.spr then
    display.remove(r7_0.spr)
  end
  local r2_10 = nil
  if type(r0_10) == "string" then
    r2_10 = r0_10
  else
    r2_10 = util.Num2Column(r0_10)
  end
  local r3_10 = nil
  r3_10 = util.MakeText3({
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
    msg = r2_10,
  })
  r1_10:insert(r3_10)
  r3_10:setReferencePoint(display.CenterRightReferencePoint)
  r3_10.x = 224
  r3_10.y = 576
  r7_0.spr = r3_10
end
local function r24_0()
  -- line: [79, 86] id: 11
  for r4_11, r5_11 in pairs({
    "scene.parts.help_pass"
  }) do
    if package.loaded[r5_11] then
      package.loaded[r5_11] = nil
    end
  end
end
local function r25_0()
  -- line: [88, 106] id: 12
  local r1_12 = display.newRect(display.newGroup(), 0, 0, _G.Width, _G.Height)
  r1_12:setFillColor(0, 0, 0)
  r1_12.alpha = 0.8
  function r1_12.touch(r0_13, r1_13)
    -- line: [93, 93] id: 13
    return true
  end
  r1_12:addEventListener("touch")
  local r2_12 = display.newGroup()
  require("scene.parts.help_pass").View(r2_12, function()
    -- line: [97, 105] id: 14
    r24_0()
    r2_12:removeSelf()
    r11_0()
    r12_0()
    return true
  end)
end
local function r26_0(r0_15, r1_15, r2_15)
  -- line: [108, 114] id: 15
  db.UnlockSummonData(r0_15, r1_15)
  db.UnlockL4SummonData(r0_15, r1_15)
  kpi.Unlock(r1_15, r2_15)
  _G.metrics.crystal_buy_character(r1_15, r2_15)
  DebugPrint("unlocked:" .. tostring(r1_15))
end
local function r27_0(r0_16, r1_16)
  -- line: [116, 131] id: 16
  local r2_16 = display.newGroup()
  r2_16:toFront()
  dialog.OpenPackDialog(r2_16, 502, {
    {
      503,
      db.GetMessage(498),
      1
    },
    {
      503,
      db.GetMessage(504),
      r0_16[3]
    },
    {
      503,
      db.GetMessage(505),
      r0_16[2]
    }
  }, {
    function(r0_17)
      -- line: [124, 129] id: 17
      sound.PlaySE(1)
      dialog.Close()
      r1_16()
      return true
    end
  })
end
local function r28_0(r0_18)
  -- line: [133, 136] id: 18
  r21_0(r0_18[2])
  r22_0(r0_18[3])
end
local function r29_0(r0_19, r1_19, r2_19)
  -- line: [138, 181] id: 19
  if _G.UserToken == nil then
    server.NetworkError(35, nil)
    return 
  end
  local r3_19 = _G.UserID
  if _G.IsSimulator then
    r26_0(r3_19, r0_19, r2_19)
    if r0_19 == r3_0.CharId.Yung then
      local r4_19 = r3_0.GetPackItem(r0_19)
      r28_0(r4_19)
      r27_0(r4_19, function()
        -- line: [150, 150] id: 20
        r25_0()
      end)
    else
      r25_0()
    end
    _G.BingoManager.updateUserBingoData(_G.BingoManagerClass.MISSION_TYPE.UNLOCK_CHAR(), nil)
  else
    util.setActivityIndicator(true)
    server.UseCoin(_G.UserToken, {
      r1_19
    }, {
      1
    }, function(r0_21)
      -- line: [158, 179] id: 21
      util.setActivityIndicator(false)
      if server.CheckError(r0_21) then
        server.NetworkError()
      else
        local r1_21 = r4_0.decode(r0_21.response)
        if r1_21.status == 0 then
          r26_0(r3_19, r0_19, r2_19)
          if r0_19 == r3_0.CharId.Yung then
            local r2_21 = r3_0.GetPackItem(r0_19)
            r28_0(r2_21)
            r27_0(r2_21, function()
              -- line: [169, 169] id: 22
              r25_0()
            end)
          else
            r25_0()
          end
          _G.BingoManager.updateUserBingoData(_G.BingoManagerClass.MISSION_TYPE.UNLOCK_CHAR(), nil)
        else
          server.ServerError(r1_21.status)
        end
      end
    end)
  end
end
local function r30_0(r0_23)
  -- line: [183, 198] id: 23
  util.setActivityIndicator(false)
  if r7_0 == nil then
    return 
  end
  if server.CheckError(r0_23) then
    server.NetworkError(35)
  else
    local r1_23 = r4_0.decode(r0_23.response)
    if r1_23.status == 0 then
      r23_0(util.ConvertDisplayCrystal(r1_23.coin))
      r6_0 = r1_23.coin
      r0_0.ShowCoinInfo(r1_23.coin)
    else
      server.ServerError(r1_23.status)
    end
  end
end
local function r31_0()
  -- line: [201, 203] id: 24
  server.GetCoin(_G.UserToken, r30_0)
end
local function r32_0()
  -- line: [208, 213] id: 25
  r1_0.Cleanup()
  if r5_0 then
    r5_0.Cleanup()
  end
end
local function r33_0()
  -- line: [215, 218] id: 26
  util.setActivityIndicator(true)
  r31_0()
end
local function r34_0(r0_27)
  -- line: [220, 234] id: 27
  if r0_27 == nil or r0_27.param == nil then
    return false
  end
  local r1_27 = r0_27.param
  if _G.IsSimulator then
    r33_0()
  else
    r0_0.Open(r1_27, {
      r33_0,
      nil
    })
  end
  return true
end
local function r35_0(r0_28)
  -- line: [239, 246] id: 28
  r32_0()
  if r10_0 then
    util.ChangeScene({
      prev = r11_0,
      scene = r10_0,
      efx = "crossfade",
    })
  else
    util.ChangeScene({
      prev = r11_0,
      scene = "title",
      efx = "crossfade",
    })
  end
end
local function r36_0()
  -- line: [248, 259] id: 29
  if phase ~= "index" and cdn.CheckFilelist() == true then
    util.ChangeScene({
      scene = "cdn",
      efx = "fade",
      param = {
        next = "shop.shop_view",
        back = "shop.shop_view",
        scene = "shop.shop_view",
      },
    })
    return 
  end
  require("scene.parts.help_chara").Load(require("scene.help").GetInitRoot(), r8_0, true)
end
local function r37_0(r0_30, r1_30)
  -- line: [262, 275] id: 30
  if r0_30 == true and r1_30 then
    r8_0 = r1_30
  end
  if r9_0[1] and r9_0[2] then
    if r0_30 == true then
      r9_0[1].isVisible = true
      r9_0[2].isVisible = false
    else
      r9_0[1].isVisible = false
      r9_0[2].isVisible = true
    end
  end
end
local function r38_0(r0_31)
  -- line: [280, 357] id: 31
  for r5_31 = 0, _G.Width - 16, 16 do
    util.LoadParts(r0_31, r16_0("shop_bg01"), r5_31, 0)
    util.LoadParts(r0_31, r16_0("shop_bg02"), r5_31, 128)
    util.LoadParts(r0_31, r16_0("shop_bg03"), r5_31, 256)
    util.LoadParts(r0_31, r16_0("shop_bg04"), r5_31, 384)
    util.LoadParts(r0_31, r16_0("shop_bg05"), r5_31, 512)
  end
  util.LoadParts(r0_31, r17_0("shop_title_"), 248, 36)
  for r5_31 = 0, 768, 256 do
    util.LoadParts(r0_31, r14_0("option_line01"), r5_31, 88)
    util.LoadParts(r0_31, r14_0("option_line02"), r5_31, 464)
    util.LoadParts(r0_31, r14_0("option_line01"), r5_31, 534)
  end
  local r2_31 = util.LoadBtn({
    rtImg = r0_31,
    fname = r14_0("close"),
    x = 0,
    y = 0,
    func = r35_0,
  })
  r2_31.x = _G.Width - r2_31.width * 0.5 - 5
  r2_31.y = r2_31.height * 0.5 + 5
  util.LoadParts(r0_31, r18_0("pocket_crystal"), 0, 544)
  r7_0 = {}
  r7_0.rtImg = r0_31
  r7_0.spr = nil
  r23_0("Loading")
  r9_0[1] = util.LoadBtn({
    rtImg = r0_31,
    fname = r17_0("detail_"),
    x = 722,
    y = 470,
    func = r36_0,
    param = r0_31,
  })
  r9_0[2] = util.LoadBtn({
    rtImg = r0_31,
    fname = r17_0("detail_disable_"),
    x = 722,
    y = 470,
    func = r34_0,
    param = r0_31,
  })
  r37_0(false)
  util.LoadBtn({
    rtImg = r0_31,
    fname = r20_0("add_crystal_"),
    x = 720,
    y = 544,
    func = r34_0,
    param = r0_31,
  })
  if _G.UserToken == nil then
    server.NetworkError(35)
  else
    r6_0 = 0
    util.setActivityIndicator(false)
    r31_0()
  end
end
local function r39_0(r0_32, r1_32, r2_32)
  -- line: [362, 370] id: 32
  r1_0.new({
    rtImg = r0_32,
    achievement = r1_32,
    summonState = r2_32,
    showLevel = false,
    showUnlock = true,
  })
end
function r12_0(r0_33)
  -- line: [375, 404] id: 33
  local r1_33 = nil	-- notice: implicit variable refs by block#[0]
  r8_0 = r1_33
  r1_33 = display.newGroup()
  r10_0 = nil
  if r0_33 ~= nil and r0_33.closeScene then
    r10_0 = r0_33.closeScene
  end
  r38_0(r1_33)
  local r2_33 = nil
  r2_33 = util.MakeFrame(r1_33)
  r5_0 = r2_0.new({
    shop_view = r13_0,
    rtImg = r1_33,
    posX = 66,
    posY = 100,
  })
  return r1_33
end
function r11_0()
  -- line: [451, 453] id: 37
  r32_0()
end
r13_0 = {
  new = r12_0,
  BuyChara = function(r0_34)
    -- line: [406, 445] id: 34
    if r0_34.param == nil or r0_34.param.img == nil or r0_34.param.sid == nil then
      return 
    end
    sound.PlaySE(1)
    local r1_34 = r0_34.param.img
    local r2_34 = r0_34.param.sid
    local r3_34 = r3_0.CharInformation[r2_34][1]
    local r4_34 = r3_0.CharInformation[r2_34][3]
    local r5_34 = string.format(db.GetMessage(14), util.ConvertDisplayCrystal(r4_34))
    local r6_34 = display.newGroup()
    r6_34:toFront()
    dialog.Open(r6_34, 13, {
      r5_34,
      15
    }, {
      function(r0_35)
        -- line: [425, 436] id: 35
        sound.PlaySE(1)
        dialog.Close()
        if r4_34 <= r6_0 then
          r29_0(r2_34, r3_34, r4_34)
        else
          r34_0({
            param = r6_34,
          })
        end
        return true
      end,
      function(r0_36)
        -- line: [437, 442] id: 36
        sound.PlaySE(2)
        dialog.Close()
        return true
      end
    })
    return true
  end,
  SetDetailBtn = r37_0,
  Cleanup = r11_0,
}
return r13_0
