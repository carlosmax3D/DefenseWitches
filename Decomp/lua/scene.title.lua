-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("ui.option")
local r1_0 = require("tool.twitter")
local r2_0 = require("tool.fbook")
local r3_0 = require("tool.crystal")
local r4_0 = require("tool.trial")
local r5_0 = require("ad.myads")
local r6_0 = require("anm.anm_start_arrow")
local r7_0 = require("common.already_read_manager")
local r8_0 = require("json")
local r9_0 = nil
kpi.Run()
local r10_0 = nil
local r11_0 = require("anm.title_banner")
local r12_0 = nil
local r13_0 = nil
local function r14_0(r0_1)
  -- line: [32, 32] id: 1
  return "data/ads" .. "/" .. r0_1 .. ".png"
end
local function r15_0(r0_2)
  -- line: [33, 33] id: 2
  return r14_0(r0_2 .. _G.UILanguage)
end
local function r16_0(r0_3)
  -- line: [35, 35] id: 3
  return "data/title/banner" .. "/" .. r0_3 .. ".png"
end
local function r17_0(r0_4)
  -- line: [36, 36] id: 4
  return r16_0(r0_4 .. _G.UILanguage)
end
local r18_0 = 0
local r19_0 = nil
local function r20_0(r0_5)
  -- line: [41, 45] id: 5
  sound.PlaySE(1)
  r3_0.Open(r0_5.param)
end
local function r21_0(r0_6)
  -- line: [47, 84] id: 6
  if r0_6.isError then
    return 
  end
  local r1_6 = r0_6.response
  if r1_6 == nil then
    return 
  end
  local r2_6 = r8_0.decode(r1_6)
  if r2_6.status == 1 then
    r18_0 = r2_6.result
    _G.BingoManager.isBingoEnabled(_G.BingoManager.getBingoCardId(), function(r0_7)
      -- line: [61, 78] id: 7
      if r0_7 == false then
        return 
      end
      if r7_0.getState(r7_0.EVENT_ID.BINGO_CARD_01()) == r7_0.STATE.UNREAD() then
        r19_0.isVisible = true
      elseif r19_0 ~= nil then
        if r18_0 > 0 then
          r19_0.isVisible = true
        else
          r19_0.isVisible = false
        end
      end
    end)
  else
    r18_0 = 0
  end
end
local function r22_0()
  -- line: [86, 90] id: 8
  if _G.UserInquiryID ~= nil and _G.UserToken ~= nil then
    server.GetNotifyKeepItem(_G.UserInquiryID, r21_0)
  end
end
local function r23_0()
  -- line: [92, 94] id: 9
  system.openURL("https://m.facebook.com/DefenseWitches")
end
local function r24_0()
  -- line: [98, 102] id: 10
  if system.openURL("line://shop/detail/1006007") == false then
    system.openURL("https://store.line.me/stickershop/product/1006007/")
  end
end
local function r25_0()
  -- line: [105, 111] id: 11
  if _G.UILanguage == "jp" then
    system.openURL("https://play.google.com/store/apps/details?id=jp.stargarage.games.dw2048&hl=jp")
  else
    system.openURL("https://play.google.com/store/apps/details?id=jp.stargarage.games.dw2048&hl=en")
  end
end
local function r26_0(r0_12)
  -- line: [113, 119] id: 12
  sound.PlaySE(1)
  r2_0.Post(r0_12.param, "DefenseWitches", db.GetMessage(39) .. "\n" .. db.GetMessage(40), "", nil)
end
local function r27_0(r0_13)
  -- line: [121, 125] id: 13
  local r1_13 = r0_13.param
  sound.PlaySE(1)
  r2_0.Like(r1_13)
end
local function r28_0(r0_14)
  -- line: [127, 134] id: 14
  sound.PlaySE(1)
  r1_0.Post(r0_14.param, _G.UserID, db.GetMessage(39) .. db.GetMessage(40) .. " #dwitch", nil)
end
local function r29_0()
  -- line: [137, 161] id: 15
  local r0_15 = save.DataLoad()
  if r0_15 == nil then
    return nil
  end
  save.DataClear()
  local r1_15 = nil
  for r5_15, r6_15 in pairs(r0_15) do
    if r6_15.command == "resume" then
      r1_15 = {
        map = r6_15.data.map,
        stage = r6_15.data.stage,
        wave = r6_15.data.wave,
        saveData = r0_15,
      }
      table.remove(r0_15, r5_15)
      break
    end
  end
  return r1_15
end
local function r30_0(r0_16)
  -- line: [163, 185] id: 16
  if r0_16 == nil then
    return false
  end
  if 1 < r0_16.map and cdn.CheckFilelist() == true then
    util.ChangeScene({
      scene = "cdn",
      efx = "fade",
      param = {
        next = "stage",
        back = "title",
        scene = "title",
        val = r0_16.map,
      },
    })
    return true
  end
  local r1_16 = {
    map = r0_16.map,
    stage = r0_16.stage,
    wave = r0_16.wave,
    save = r0_16.saveData,
  }
  sound.PlaySE(1)
  util.ChangeScene({
    prev = r9_0,
    param = r1_16,
    scene = "resume",
    efx = "fade",
  })
  return true
end
local function r31_0(r0_17)
  -- line: [187, 201] id: 17
  for r4_17, r5_17 in pairs(r0_17.saveData) do
    if util.IsContainedTable(r5_17.data, "DropTreasureboxEnemy") and util.IsContainedTable(r5_17.data, "PopEnemyNum") then
      enemy.DropTreasureboxEnemy = r5_17.data.DropTreasureboxEnemy
      enemy.PopEnemyNum = r5_17.data.PopEnemyNum
    end
  end
  if not r30_0(r0_17) then
    sound.PlaySE(2)
  end
end
local function r32_0(r0_18)
  -- line: [203, 245] id: 18
  local r1_18 = r0_18.param[2]
  local r2_18 = r0_18.param[1]
  local r3_18 = r29_0()
  if r1_18 == true and r3_18 ~= nil and db.IsInvalidResume(_G.UserID, r3_18.map, r3_18.stage) == true then
    r1_18 = false
  end
  local function r4_18(r0_19)
    -- line: [218, 223] id: 19
    sound.PlaySE(2)
    dialog.Close()
    r31_0(r3_18)
    return true
  end
  local function r5_18(r0_20)
    -- line: [225, 236] id: 20
    sound.PlaySE(1)
    if r4_0.CheckTrialDisable() == false then
      r4_0.InitNowTrial()
    end
    bgm.FadeOut(500)
    save.DataClear()
    util.setActivityIndicator(true)
    util.ChangeScene({
      prev = r9_0,
      scene = "map",
      efx = "moveFromRight",
    })
    return true
  end
  if r1_18 == true then
    sound.PlaySE(1)
    dialog.Open(r2_18, 316, {
      317,
      318
    }, {
      r4_18,
      r5_18
    })
  else
    r5_18()
  end
end
local r33_0 = "data/title"
local function r34_0(r0_21)
  -- line: [248, 248] id: 21
  return r33_0 .. "/" .. r0_21 .. ".png"
end
local function r35_0(r0_22)
  -- line: [249, 249] id: 22
  return r34_0(r0_22 .. _G.UILanguage)
end
local function r36_0(r0_23)
  -- line: [250, 250] id: 23
  return "data/menu/" .. r0_23 .. ".png"
end
local function r37_0(r0_24)
  -- line: [251, 251] id: 24
  return r36_0(r0_24 .. _G.UILanguage)
end
local function r38_0(r0_25)
  -- line: [254, 258] id: 25
  sound.PlaySE(1)
  util.ChangeScene({
    prev = r9_0,
    scene = "menu",
    efx = "crossfade",
  })
  return true
end
local function r39_0(r0_26)
  -- line: [260, 264] id: 26
  sound.PlaySE(1)
  util.ChangeScene({
    prev = r9_0,
    scene = "menu",
    efx = "crossfade",
  })
  return true
end
local function r40_0(r0_27)
  -- line: [270, 306] id: 27
  if r0_27.isError then
    return 
  end
  if r0_27.response == nil then
    return 
  end
  local r2_27 = r8_0.decode(r0_27.response)
  if r2_27.status == 1 then
    if type(r2_27.result) == "table" then
      local r3_27 = require("login.login_bonus_popup_dialog").new(r2_27.result)
      if r3_27 and r3_27.isLoginBonusPopup == false then
        r3_27.show()
      end
    end
    if r2_27.is_next ~= nil and r2_27.is_next == 1 then
      notification.SetLoginBonusNotification()
    end
    db.SaveLoginBonusReciveData({
      uid = _G.UserInquiryID,
      result = 0,
    })
  elseif r2_27.status == 2 then
    db.SaveLoginBonusReciveData({
      uid = _G.UserInquiryID,
      result = 1,
    })
  end
end
local function r41_0()
  -- line: [311, 316] id: 28
  if _G.UserInquiryID ~= nil and _G.UserToken ~= nil then
    server.GetNotifyLoginBonus(_G.UserInquiryID, r40_0)
  end
end
local function r42_0()
  -- line: [318, 324] id: 29
  if _G.UserInquiryID ~= nil and _G.UserToken ~= nil then
    server.GetNotifyLoginBonusDbg(_G.UserInquiryID, r40_0)
  end
end
local function r43_0(r0_30)
  -- line: [327, 344] id: 30
  local r1_30 = display.newGroup()
  util.LoadParts(r1_30, r34_0("title_info_bg"), 0, 0)
  if _G.IsSimulator or _G.IsDebug then
    util.LoadBtn({
      rtImg = r1_30,
      fname = r34_0("title_info"),
      x = 24,
      y = 144,
      func = r42_0,
    })
  end
  r1_30:setReferencePoint(display.TopLeftReferencePoint)
  r1_30.x = 832
  r1_30.y = 0
  r0_30:insert(r1_30)
  return r1_30
end
local function r44_0(r0_31)
  -- line: [346, 349] id: 31
  wallAds.show()
  return true
end
local r45_0 = false
local function r47_0()
  -- line: [357, 362] id: 33
  r45_0 = true
end
local function r48_0(r0_34)
  -- line: [364, 366] id: 34
  r45_0 = false
end
local function r49_0(r0_35, r1_35)
  -- line: [369, 375] id: 35
  if r1_35.phase == "ended" then
    sound.PlaySE(1)
    util.ChangeScene({
      prev = r9_0,
      scene = "shop.shop_view",
      efx = "crossfade",
    })
  end
  return true
end
local function r50_0(r0_36)
  -- line: [377, 380] id: 36
  sound.PlaySE(1)
  util.ChangeScene({
    prev = r9_0,
    scene = "invite",
    efx = "crossfade",
  })
end
local function r51_0(r0_37, r1_37, r2_37)
  -- line: [382, 387] id: 37
  r1_37.flag = true
  sound.PlayTitleSound(r1_37.id)
  r1_37.ev = nil
  return false
end
local function r52_0(r0_38, r1_38)
  -- line: [389, 402] id: 38
  if r1_38.phase == "began" and r0_38.charId then
    if not _G.GameData.voice then
      return 
    end
    local r4_38 = string.format("data/sound/%02d/%s", r0_38.charId, _G.GameData.language) .. "/0" .. math.random(1, 5) .. ".aac"
    sound.StopVoice(5)
    sound.PlayVoice(r4_38, 5)
  end
end
local function r53_0(r0_39, r1_39)
  -- line: [404, 435] id: 39
  local r2_39 = r1_39.phase
  if r2_39 == "began" then
    if r0_39.ev then
      events.Delete(r0_39.ev)
      r0_39.ev = nil
    end
    if not r0_39.flag then
      r0_39.ev = events.Register(r51_0, r0_39, 2000)
    end
    display.getCurrentStage():setFocus(r0_39)
    r0_39.is_focus = true
  elseif r0_39.is_focus then
    local r3_39 = r1_39.x
    local r4_39 = r1_39.y
    local r5_39 = r0_39.stageBounds
    local r6_39 = r5_39.xMin
    if r6_39 <= r3_39 then
      r6_39 = r5_39.xMax
      if r3_39 <= r6_39 then
        r6_39 = r5_39.yMin
        if r6_39 <= r4_39 then
          r6_39 = r4_39 <= r5_39.yMax
        end
      end
    else
      goto label_47	-- block#11 is visited secondly
    end
    if r2_39 == "moved" and not r6_39 then
      r2_39 = "cancelled"
    end
    if r2_39 == "ended" or r2_39 == "canceled" then
      r0_39.is_focus = false
      if r0_39.ev then
        events.Delete(r0_39.ev)
        r0_39.ev = nil
      end
      display.getCurrentStage():setFocus(nil)
    end
  end
  return true
end
local function r54_0(r0_40)
  -- line: [440, 477] id: 40
  if r0_40.isError then
    return 
  end
  if r0_40.response == nil then
    return 
  end
  local r2_40 = r8_0.decode(r0_40.response)
  if r2_40.status == 1 then
    if type(r2_40.result) == "table" then
      local r3_40 = require("login.login_bonus_popup_dialog").new(r2_40.result)
      if r3_40 and r3_40.isLoginBonusPopup == false then
        r3_40.show()
      end
    end
    if r2_40.is_next ~= nil and r2_40.is_next == 1 then
      notification.SetLoginBonusNotification()
    end
    db.SaveLoginBonusReciveData({
      uid = _G.UserInquiryID,
      result = 0,
    })
  elseif r2_40.status == 2 then
    db.SaveLoginBonusReciveData({
      uid = _G.UserInquiryID,
      result = 1,
    })
  end
end
local function r55_0()
  -- line: [482, 545] id: 41
  local function r0_41(r0_42)
    -- line: [486, 512] id: 42
    for r6_42, r7_42 in pairs(r0_42) do
      local r8_42 = r6_42
      if type(r8_42) ~= "string" then
        r8_42 = tostring(r8_42)
      end
      local r9_42 = r7_42
      if type(r9_42) ~= "number" then
        r9_42 = tonumber(r9_42)
      end
      if _G.LoginItems[r8_42].itemType == _G.ItemTypeQuantity then
        db.SetInventoryItem({
          {
            uid = _G.UserInquiryID,
            itemid = r8_42,
            quantity = r9_42,
          }
        })
      end
    end
  end
  server.GetStockIemCount(_G.UserInquiryID, function(r0_43)
    -- line: [517, 542] id: 43
    if r0_43.isError then
      native.showAlert("DefenseWitches", db.GetMessage(330), {
        "OK"
      })
      close()
      return 
    end
    local r1_43 = r8_0.decode(r0_43.response)
    if r1_43 ~= nil then
      if r1_43.status == 1 and type(r1_43.result) == "table" then
        r0_41(r1_43.result)
      elseif r1_43.status == 0 then
        native.showAlert("DefenseWitches", db.GetMessage(331), {
          "OK"
        })
        close()
      end
    else
      native.showAlert("DefenseWitches", db.GetMessage(331), {
        "OK"
      })
      close()
    end
  end)
end
function r9_0()
  -- line: [834, 851] id: 50
  if nil and not nil then
    goto label_5	-- block#1 is visited secondly
  end
  sound.StopTitleSound()
  if r12_0 then
    transit.Delete(r12_0)
    r12_0 = nil
  end
  r45_0 = false
  events.DeleteNamespace("title")
end
return {
  new = function(r0_44)
    -- line: [547, 832] id: 44
    events.SetNamespace("title")
    sound.InitVoice()
    local r1_44 = save.FileExist()
    local r2_44 = display.newGroup()
    local r3_44 = util.MakeGroup(r2_44)
    local r4_44 = util.MakeGroup(r2_44)
    local r5_44 = util.MakeGroup(r2_44)
    local r6_44 = util.MakeGroup(r2_44)
    local r7_44 = util.MakeGroup(r2_44)
    local r8_44 = util.MakeGroup(r2_44)
    local r9_44 = util.MakeGroup(r2_44)
    util.MakeFrame(r2_44)
    hint.Init()
    local r10_44 = nil
    db.SetStartup(1, false)
    util.LoadTileBG(r3_44, db.LoadTileData("title", "bg"), r33_0)
    local r11_44 = util.LoadTileParts(r7_44, -8, -24, db.LoadTileData("title", "logo"), r33_0)
    r11_44.isVisible = false
    r11_44.id = 2
    r11_44.ev = nil
    r11_44.flag = false
    r11_44.touch = r53_0
    r11_44:addEventListener("touch")
    local r12_44 = util.LoadParts(r7_44, r34_0("title_newgate"), 686, 22)
    r12_44.isVisible = false
    r12_44.id = 1
    r12_44.ev = nil
    r12_44.flag = false
    local r13_44 = {
      [3] = util.LoadTileParts(r4_44, 960, 56, db.LoadTileData("title", "c03"), r33_0),
    }
    r13_44[3].isVisible = false
    r13_44[2] = util.LoadTileParts(r4_44, -577, 0, db.LoadTileData("title", "c02"), r33_0)
    r13_44[2].isVisible = false
    r13_44[1] = util.LoadTileParts(r4_44, 180, 117, db.LoadTileData("title", "c01"), r33_0)
    r13_44[1].isVisible = false
    util.LoadParts(r5_44, r34_0("button_bg"), 0, 640).isVisible = false
    local r15_44 = {}
    r15_44[5] = util.LoadBtn({
      rtImg = r7_44,
      fname = r35_0("title_start_"),
      x = -232,
      y = 254,
      func = r32_0,
      param = {
        r8_44,
        r1_44
      },
      show = false,
    })
    r15_44[6] = util.LoadBtn({
      rtImg = r7_44,
      fname = r35_0("title_menu_"),
      x = -232,
      y = 414,
      func = r38_0,
      param = r8_44,
      show = false,
    })
    local r16_44 = nil
    local r17_44 = nil
    if r5_0.GetLastRes() then
      r16_44 = 536
      r17_44 = 479
    else
      r16_44 = 704
      r17_44 = 479
    end
    r10_0 = anime.RegisterWithInterval(r11_0.GetData(), 698, r17_44, "data/title/banner", 40)
    anime.Loop(r10_0, true)
    local r18_44 = anime.GetSprite(r10_0)
    r18_44.touch = r49_0
    r18_44:addEventListener("touch", r18_44)
    util.LoadParts(r18_44, r17_0("text_banner_title_"), 698, r17_44 + 105)
    r7_44:insert(r18_44)
    r15_44[10] = r43_0(r7_44)
    r15_44[10].isVisible = false
    if r5_0.GetLastRes() then
      r15_44[14] = util.LoadBtn({
        rtImg = r7_44,
        fname = r35_0("crystal_"),
        x = 320,
        y = 510,
        func = r44_0,
        param = r7_44,
      })
      if math.random(10) > 5 then
        r15_44[15] = util.LoadBtn({
          rtImg = r7_44,
          fname = r35_0("line_stamp_"),
          x = 5,
          y = 510,
          func = r24_0,
          param = r7_44,
        })
      else
        r15_44[15] = util.LoadBtn({
          rtImg = r7_44,
          fname = r35_0("2048_"),
          x = 5,
          y = 510,
          func = r25_0,
          param = r7_44,
        })
      end
      r15_44[14].isVisible = false
      r15_44[15].isVisible = false
    end
    local r19_44 = display.newImage(r6_44, r34_0("circle_addcrystal"), true)
    r19_44:setReferencePoint(display.CenterReferencePoint)
    r19_44.x = 1088
    r19_44.y = 768
    r19_44.isVisible = false
    r19_44.transit = nil
    function r19_44.touch(r0_45, r1_45)
      -- line: [664, 689] id: 45
      local r2_45 = r1_45.phase
      local r3_45 = r1_45.x
      local r4_45 = r1_45.y
      if r2_45 == "began" then
        if r0_45.transit then
          transit.Delete(r0_45.transit)
          r0_45.transit = nil
        end
        r19_44.x = r3_45
        r19_44.y = r4_45
        display.getCurrentStage():setFocus(r0_45)
      elseif r2_45 == "moved" then
        r19_44.x = r3_45
        r19_44.y = r4_45
      elseif r2_45 == "ended" or r2_45 == "cancelled" then
        if r0_45.transit then
          transit.Delete(r0_45.transit)
          r0_45.transit = nil
        end
        r0_45.transit = transit.Register(r0_45, {
          time = 1000,
          x = 896,
          y = 640,
        })
        display.getCurrentStage():setFocus(nil)
      end
      return true
    end
    r19_44:addEventListener("touch", r19_44)
    local function r20_44(r0_46)
      -- line: [693, 821] id: 46
      local r1_46 = {}
      local r2_46 = nil
      local function r3_46(r0_47)
        -- line: [697, 744] id: 47
        for r4_47, r5_47 in pairs(r1_46) do
          transit.Delete(r5_47)
        end
        r1_46 = {}
        r2_46.isVisible = false
        r13_0 = {}
        r19_44.rotation = 0
        r12_0 = transit.Register(r19_44, {
          time = 5000,
          rotation = 360,
          transition = easing.linear,
          loop = true,
        })
        r15_44[10].isVisible = true
        anime.Show(r10_0, true)
        if r5_0.GetLastRes() and r15_44 ~= nil and r15_44[14] ~= nil and r15_44[15] ~= nil then
          r15_44[14].isVisible = true
          r15_44[15].isVisible = true
        end
        bgm.Play(1)
        r3_0.UpdateCoin(true)
        r41_0()
        r18_0 = 0
        r19_0 = util.LoadParts(r7_44, r36_0("mark_info"), 20, 410)
        r19_0.isVisible = false
        r22_0()
      end
      local r4_46 = nil
      r4_46 = r13_44[3]
      r4_46.x = 636
      r4_46.alpha = 0.01
      r1_46[3] = transit.Register(r4_46, {
        time = 1000,
        transition = easing.outExpo,
        x = 236,
        alpha = 1,
      })
      r4_46 = r13_44[2]
      r4_46.x = 126
      r4_46.alpha = 0.01
      r1_46[2] = transit.Register(r4_46, {
        delay = 300,
        time = 1000,
        transition = easing.outExpo,
        x = 514,
        alpha = 1,
      })
      r4_46 = r13_44[1]
      r4_46:setReferencePoint(display.CenterReferencePoint)
      r4_46.x = _G.Width / 2
      r4_46.y = _G.Height / 2
      r4_46.xScale = 2
      r4_46.yScale = 2
      r4_46.alpha = 0.01
      r4_46.isVisible = false
      r1_46[1] = transit.Register(r4_46, {
        delay = 800,
        time = 1000,
        transition = easing.outExpo,
        xScale = 1,
        yScale = 1,
        alpha = 1,
        x = 518,
        y = 379,
      })
      r11_44.alpha = 0.001
      r1_46[4] = transit.Register(r11_44, {
        delay = 1500,
        time = 500,
        transition = easing.inExpo,
        alpha = 1,
      })
      r1_46[6] = transit.Register(r15_44[5], {
        delay = 2100,
        time = 250,
        transition = easing.inExpo,
        x = 152,
        onComplete = function()
          -- line: [779, 785] id: 48
          local r0_48 = anime.RegisterWithInterval(r6_0.GetData(), 149, 303, "data/title", 50)
          r7_44:insert(anime.GetSprite(r0_48))
          anime.Show(r0_48, true)
          anime.Loop(r0_48, true)
        end,
      })
      r1_46[7] = transit.Register(r15_44[6], {
        delay = 2200,
        time = 250,
        transition = easing.inExpo,
        x = 123,
      })
      if r15_44[12] then
        r1_46[17] = transit.Register(r15_44[12], {
          delay = 2650,
          time = 250,
          transition = easing.inExpo,
          y = 600,
        })
      end
      r1_46[15] = transit.Register(r12_44, {
        delay = 2650,
        time = 250,
        transition = easing.inExpo,
        y = 22,
      })
      r1_46[16] = transit.Register(r19_44, {
        delay = 2900,
        time = 250,
        transition = easing.inExpo,
        x = 896,
        y = 640,
        onComplete = r3_46,
      })
      r2_46 = display.newRect(r9_44, 0, 0, _G.Width, _G.Height)
      r2_46:setFillColor(0, 0, 0)
      r2_46.alpha = 0.01
      function r2_46.touch(r0_49, r1_49)
        -- line: [806, 811] id: 49
        for r5_49, r6_49 in pairs(r1_46) do
          transit.Cancel(r6_49)
        end
        return true
      end
      r2_46:addEventListener("touch", r2_46)
      if _G.TitleAnimationSkip ~= nil and _G.TitleAnimationSkip == true then
        for r8_46, r9_46 in pairs(r1_46) do
          transit.Cancel(r9_46)
        end
      end
    end
    if _G.TitleAnimationSkip ~= nil and _G.TitleAnimationSkip == true then
      r20_44()
    else
      timer.performWithDelay(500, r20_44)
    end
    return r2_44
  end,
  Cleanup = r9_0,
  isInfoUpdate = function()
    -- line: [353, 355] id: 32
    return r45_0
  end,
}
