-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r38_0 = nil	-- notice: implicit variable refs by block#[0]
local r37_0 = nil	-- notice: implicit variable refs by block#[0]
module(..., package.seeall)
DebugPrint("-- login_bonus_item_receive_dialog --")
local r0_0 = require("json")
require("login.login_bonus_item_data")
require("login.base_login_bonus_dialog")
require("login.login_ok_only_popup_dialog")
local r1_0 = {}
local r2_0 = nil
local r3_0 = {}
local r4_0 = nil
local r5_0 = nil
local r6_0 = false
local r7_0 = false
local r8_0 = nil
local r9_0 = 0
local r10_0 = 0
local r11_0 = require("login.loginbonus_cha_anm_01")
local r12_0 = require("login.loginbonus_cha_anm_02")
local r13_0 = require("login.loginbonus_cha_anm_03")
local r14_0 = nil
local r15_0 = nil
local r16_0 = nil
local r17_0 = 1
local r18_0 = 300
local r19_0 = 26
local r20_0 = "jin06"
local r21_0 = nil
local r22_0 = 300
local r23_0 = false
function r1_0.LB(r0_1)
  -- line: [55, 55] id: 1
  return "data/login_bonus/receive/" .. r0_1 .. ".png"
end
function r1_0.LB_L(r0_2)
  -- line: [56, 56] id: 2
  return r1_0.LB(r0_2 .. _G.UILanguage)
end
function r1_0.LBL(r0_3)
  -- line: [58, 58] id: 3
  return "data/login_bonus/list/" .. r0_3 .. ".png"
end
function r1_0.LBL_L(r0_4)
  -- line: [59, 59] id: 4
  return r1_0.LBL(r0_4 .. _G.UILanguage)
end
function r1_0.O(r0_5)
  -- line: [62, 62] id: 5
  return "data/option/" .. r0_5 .. ".png"
end
function r1_0.O_L(r0_6)
  -- line: [63, 63] id: 6
  return r1_0.O(r0_6 .. _G.UILanguage)
end
local function r24_0()
  -- line: [68, 71] id: 7
  assert(r1_0, debug.traceback())
  return getmetatable(r1_0).__index
end
local function r25_0(r0_8, r1_8)
  -- line: [76, 79] id: 8
  return string.format(db.GetMessage(299), r0_8, r1_8)
end
local function r26_0()
  -- line: [84, 111] id: 9
  if r2_0 then
    r2_0.clean()
    r2_0.removeAll()
    display.remove(r2_0)
  end
  if r3_0 then
    r3_0 = nil
  end
  if r8_0 then
    display.remove(r8_0)
    r8_0 = nil
  end
  if r14_0 then
    anime.Remove(r14_0)
  end
  if r15_0 then
    anime.Remove(r15_0)
  end
  if r16_0 then
    anime.Remove(r16_0)
  end
  r24_0().close()
end
local function r27_0(r0_10, r1_10, r2_10, r3_10, r4_10, r5_10, r6_10)
  -- line: [116, 127] id: 10
  login.login_ok_only_popup_dialog.new({
    titleLogoPath = r1_10,
    itemId = r0_10,
    quantity = r2_10,
    message = r3_10,
    fontSize = r4_10,
    lineHeight = r5_10,
    onOkButton = r6_10,
  }).show()
end
local function r28_0()
  -- line: [132, 139] id: 11
  sound.PlaySE(1)
  r26_0()
  require("login.login_bonus_list_dialog").new().show()
  return true
end
local function r29_0(r0_12)
  -- line: [144, 149] id: 12
  sound.PlaySE(1)
  dialog.Close()
  return true
end
local function r30_0()
  -- line: [154, 159] id: 13
  if r8_0 then
    r8_0:removeEventListener("touch")
    r8_0.alpha = 0.8
  end
end
local function r31_0(r0_14)
  -- line: [164, 168] id: 14
  local r3_14 = dialog.OpenOkButtonDialog(r24_0().getDialogObj(), r0_14, {
    r29_0
  })
end
local function r32_0()
  -- line: [173, 242] id: 15
  if r21_0 == nil or type(r21_0) ~= "table" or r23_0 == true then
    return 
  end
  DebugPrint("~~ show_notice_dialog ~~")
  DebugPrint(r21_0)
  r23_0 = true
  local function r0_15(r0_16, r1_16)
    -- line: [186, 199] id: 16
    local r2_16 = nil	-- notice: implicit variable refs by block#[3]
    if _G.UILanguage == "en" then
      r2_16 = string.format(db.GetMessage(345), r1_16, string.format(db.GetMessage(r0_16), util.ConvertDisplayCrystal(r18_0)))
    else
      r2_16 = string.format(db.GetMessage(345), r1_16, util.ConvertDisplayCrystal(r18_0))
    end
    return r2_16
  end
  local r1_15 = 0
  if r21_0.quantity then
    r1_15 = r21_0.quantity
  end
  local r2_15 = nil
  local r3_15 = 0
  if r21_0.itemId then
    r3_15 = r21_0.itemId
  end
  local r4_15 = 0
  if r21_0.dayCount then
    r4_15 = r21_0.dayCount
  end
  if r3_15 == _G.LoginItems["1"].id then
    r2_15 = string.format(db.GetMessage(344), r4_15, util.ConvertDisplayCrystal(r1_15))
  elseif r3_15 == _G.LoginItems["4"].id then
    r2_15 = r0_15(323, r4_15)
  elseif r3_15 == _G.LoginItems["5"].id then
    r2_15 = r0_15(324, r4_15)
  elseif r3_15 == _G.LoginItems["6"].id then
    r2_15 = r0_15(325, r4_15)
  elseif r3_15 == _G.LoginItems["7"].id then
    r2_15 = r0_15(326, r4_15)
  elseif r3_15 == _G.LoginItems["8"].id then
    r2_15 = string.format(db.GetMessage(488), r4_15, r1_15)
  elseif r3_15 == _G.LoginItems["9"].id then
    r2_15 = string.format(db.GetMessage(489), r4_15, r1_15)
  end
  r27_0(r3_15, r1_0.LBL_L("list_info_title_"), r1_15, r2_15, 24, 28)
end
local function r33_0(r0_17, r1_17, r2_17)
  -- line: [247, 278] id: 17
  local r4_17 = tonumber(r0_17)
  local r3_17 = nil	-- notice: implicit variable refs by block#[14]
  if r4_17 == _G.LoginItems["1"].id then
    r3_17 = string.format(db.GetMessage(311), util.ConvertDisplayCrystal(r1_17))
  elseif r4_17 == _G.LoginItems["4"].id then
    r3_17 = db.GetMessage(307)
  elseif r4_17 == _G.LoginItems["5"].id then
    r3_17 = db.GetMessage(308)
  elseif r4_17 == _G.LoginItems["6"].id then
    r3_17 = db.GetMessage(309)
  elseif r4_17 == _G.LoginItems["7"].id then
    r3_17 = db.GetMessage(310)
  elseif r4_17 == _G.LoginItems["8"].id then
    r3_17 = string.format(db.GetMessage(490), r1_17)
  elseif r4_17 == _G.LoginItems["9"].id then
    r3_17 = string.format(db.GetMessage(491), r1_17)
  end
  sound.PlaySound(r20_0)
  r27_0(r0_17, r1_0.LB("login_bonus_title_received"), r1_17, r3_17, nil, nil, r2_17)
end
local function r34_0(r0_18)
  -- line: [283, 327] id: 18
  local r1_18 = true
  if r0_18.status == 1 then
    local r2_18 = r0_18.result
    if type(r2_18) == "table" then
      local r3_18 = nil
      local r4_18 = 0
      for r8_18, r9_18 in pairs(r2_18) do
        local r10_18 = r8_18
        if type(r10_18) ~= "number" then
          r10_18 = tonumber(r10_18)
        end
        if r10_18 == 2 then
          r3_18 = db.GetMessage(321)
          r4_18 = login.login_bonus_item_data.getFlareMaxLimit()
        end
        if r10_18 == 3 then
          r3_18 = db.GetMessage(320)
          r4_18 = login.login_bonus_item_data.getRewindMaxLimit()
          break
        else
          break
        end
      end
      DebugPrint(r3_18, r4_18)
      if r3_18 then
        r31_0({
          string.format(r3_18, r4_18)
        })
      end
      r1_18 = false
    end
  else
    native.showAlert("DefenseWitches", db.GetMessage(332), {
      "OK"
    })
    r1_18 = false
  end
  return r1_18
end
local function r35_0(r0_19)
  -- line: [332, 384] id: 19
  local r1_19 = 0
  local r2_19 = _G.LoginItems[r0_19.id].itemType
  if r2_19 ~= _G.ItemTypeCrystal then
    if r2_19 == _G.ItemTypeExp then
      ExpManager.AddExp(r0_19.quantity)
      ExpManager.SaveExp()
      db.SaveToServer2(_G.UserToken)
    elseif r2_19 == _G.ItemTypeMaxOrb then
      OrbManager.AddMaxOrb(r0_19.quantity)
      db.SaveToServer2(_G.UserToken)
    elseif r2_19 == _G.ItemTypeQuantity then
      if r0_19.id == "2" then
        r1_19 = r10_0 + r0_19.quantity
        r10_0 = r1_19
        r1_0.setFlare(r1_19)
      elseif r0_19.id == "3" then
        r1_19 = r9_0 + r0_19.quantity
        r9_0 = r1_19
        r1_0.setRewind(r1_19)
      end
      db.SetInventoryItem({
        {
          uid = _G.UserInquiryID,
          itemid = r0_19.id,
          quantity = r1_19,
        }
      })
      db.SaveToServer2(_G.UserToken)
    elseif r2_19 == _G.ItemTypeUnit then
      local r3_19 = _G.LoginItems[r0_19.id].charId
      local r4_19, r5_19 = db.LoadSummonData(_G.UserID)
      if #r4_19 < 1 then
        db.InitSummonData(_G.UserID)
      end
      db.UnlockSummonData(_G.UserID, r3_19)
      _G.BingoManager.updateUserBingoData(_G.BingoManagerClass.MISSION_TYPE.UNLOCK_CHAR(), nil)
      db.SaveToServer2(_G.UserToken)
    end
  end
end
local function r36_0(r0_20, r1_20, r2_20)
  -- line: [389, 454] id: 20
  if tostring(r0_20.id) == "2" and login.login_bonus_item_data.getFlareMaxLimit() < r10_0 + r0_20.quantity then
    local r5_20 = {
      status = 1,
      result = {},
    }
    r5_20.result["2"] = login.login_bonus_item_data.getFlareMaxLimit()
    local r3_20 = r34_0(r5_20)
    if r2_20 then
      r2_20(r3_20)
    end
    return 
  elseif tostring(r0_20.id) == "3" and login.login_bonus_item_data.getRewindMaxLimit() < r9_0 + r0_20.quantity then
    local r5_20 = {
      status = 1,
      result = {},
    }
    r5_20.result["3"] = login.login_bonus_item_data.getRewindMaxLimit()
    local r3_20 = r34_0(r5_20)
    if r2_20 then
      r2_20(r3_20)
    end
    return 
  end
  server.GetKeepItem(_G.UserInquiryID, r0_20.postId, r1_20, function(r0_21)
    -- line: [425, 453] id: 21
    local r1_21 = true
    if r0_21.isError then
      native.showAlert("DefenseWitches", db.GetMessage(330), {
        "OK"
      })
      r1_21 = false
    end
    DebugPrint(r0_21.response)
    local r2_21 = r0_0.decode(r0_21.response)
    if r2_21 ~= nil then
      r1_21 = r34_0(r2_21)
    else
      native.showAlert("DefenseWitches", db.GetMessage(332), {
        "OK"
      })
      r1_21 = false
    end
    if r2_20 then
      r2_20(r1_21)
    end
  end)
end
function r37_0(r0_22)
  -- line: [459, 528] id: 22
  if r0_22 == false then
    r7_0 = false
    return 
  end
  local r1_22 = r2_0.getRows()
  if r1_22 == nil or #r1_22 < 1 then
    return 
  end
  local r2_22 = r1_22[1]
  local r3_22 = r3_0[r2_22.index]
  r35_0(r3_22)
  local function r4_22()
    -- line: [481, 507] id: 23
    r2_0.remove(r2_22, {
      onComplete = function()
        -- line: [483, 505] id: 24
        r1_22 = r2_0.getRows()
        if r1_22 == nil then
          r30_0()
          r7_0 = false
          return 
        end
        if #r1_22 < 1 then
          r30_0()
          r7_0 = false
          r32_0()
        else
          r2_22 = r1_22[1]
          r3_22 = r3_0[r2_22.index]
          r36_0(r3_22, crystalList, r37_0)
        end
      end,
    })
  end
  if _G.LoginItems[r3_22.id].itemType ~= _G.ItemTypeQuantity then
    r33_0(r3_22.id, r3_22.quantity, function()
      -- line: [511, 519] id: 25
      transition.to(r2_22, {
        time = 100,
        alpha = 0,
        onComplete = r4_22,
      })
    end)
  else
    transition.to(r2_22, {
      time = 100,
      alpha = 0,
      onComplete = r4_22,
    })
  end
end
function r38_0()
  -- line: [533, 564] id: 26
  if r6_0 or r7_0 then
    return true
  end
  r8_0:removeEventListener("touch", r38_0)
  r7_0 = true
  sound.PlaySE(1)
  local r0_26 = r2_0.getRows()
  if #r0_26 < 1 then
    return true
  end
  local r2_26 = r3_0[r0_26[1].index]
  local r3_26 = nil
  if r2_26.changedCrystal then
    r3_26 = {}
    table.insert(r3_26, r2_26.postId)
  end
  r36_0(r2_26, r3_26, r37_0)
  return true
end
local function r39_0(r0_27)
  -- line: [569, 643] id: 27
  if r6_0 or r7_0 then
    return true
  end
  r6_0 = true
  sound.PlaySE(1)
  local r1_27 = r0_27.param
  if r1_27.index == nil or r1_27.index < 1 or #r3_0 < r1_27.index then
    return 
  end
  local r2_27 = r3_0[r1_27.index]
  if r2_27.postId then
    local r3_27 = nil
    if r2_27.changedCrystal then
      r3_27 = {}
      table.insert(r3_27, r2_27.postId)
    end
    r36_0(r2_27, r3_27, function(r0_28)
      -- line: [599, 639] id: 28
      if r0_28 == false then
        r6_0 = false
        return 
      end
      r35_0(r2_27)
      if _G.LoginItems[r2_27.id].itemType ~= _G.ItemTypeQuantity then
        r33_0(r2_27.id, r2_27.quantity, function()
          -- line: [613, 616] id: 29
          r32_0()
        end)
      else
        r32_0()
      end
      transition.to(r1_27, {
        time = 100,
        alpha = 0,
        onComplete = function()
          -- line: [626, 637] id: 30
          r2_0.remove(r1_27, {
            onComplete = function()
              -- line: [628, 635] id: 31
              if #r2_0.getRows() < 1 then
                r30_0()
              end
              r6_0 = false
            end,
          })
        end,
      })
    end)
  end
  return true
end
local function r40_0()
  -- line: [648, 662] id: 32
  sound.PlaySE(2)
  local r0_32 = r2_0.getRows()
  DebugPrint(#r0_32)
  if menu and #r0_32 < 1 then
    menu.removeItemMark()
  end
  r26_0()
  return true
end
local function r41_0(r0_33)
  -- line: [667, 720] id: 33
  local r1_33 = r0_33.row
  if #r3_0 < r1_33.index then
    return 
  end
  local r2_33 = r3_0[r1_33.index]
  if r2_33 == nil or r2_33.id == nil or r2_33.name == nil or r2_33.quantity == nil then
    return 
  end
  display.newRect(r1_33, 0, 0, 630, 147):setFillColor(0, 0, 0, 0)
  if r1_33.index > 1 then
    local r4_33 = util.LoadParts(r1_33, r1_0.LB("scroll_plate_line"), 0, 0)
    r4_33:setReferencePoint(display.CenterReferencePoint)
    r4_33.x = r1_33.width * 0.5
    r4_33[r2_0.getSeparateKey()] = true
  end
  local r4_33 = login.login_bonus_item_data.getItemIcon(r1_33, 36, 10, r2_33.id, r2_33.quantity)
  local r5_33 = display.newText(r1_33, r2_33.name, 0, 0, native.systemFont, 26)
  r5_33:setReferencePoint(display.TopLeftReferencePoint)
  r5_33:setFillColor(255, 255, 128)
  r5_33.x = 190
  r5_33.y = 40
  if login.login_bonus_item_data.getItem(r2_33.id).itemType ~= _G.ItemTypeUnit then
    local r8_33 = display.newText(r1_33, r25_0(r2_33.timeHour, r2_33.timeMinutes), 190, 80, native.systemFont, 24)
  end
  local r7_33 = util.LoadBtn({
    rtImg = r1_33,
    fname = r1_0.LB_L("receive2_"),
    x = 452,
    y = 25,
    param = r1_33,
    func = r39_0,
  })
end
local function r42_0(r0_34)
  -- line: [725, 826] id: 34
  r0_34:init()
  if not events.CheckNamespace("menuview") then
    events.SetNamespace("menuview")
  end
  r0_34.loadGround(r1_0.LB("login_bonus_bg"))
  local r1_34 = r0_34.getDialogObj()
  r1_34:setReferencePoint(display.TopLeftReferencePoint)
  r1_34.x = _G.Width * 0.5 - r1_34.width * 0.5
  r1_34.y = _G.Height * 0.5 - r1_34.height * 0.5
  local r2_34 = graphics.newMask(r1_0.LB("scroll_plate_mask"))
  local r3_34 = display.newGroup()
  local r4_34 = util.LoadParts(r3_34, r1_0.LB("scroll_plate"), 0, 0)
  r3_34:setReferencePoint(display.TopLeftReferencePoint)
  r3_34.x = 107
  r3_34.y = 103
  r3_34:setMask(r2_34)
  r3_34.maskX = r3_34.width * 0.5
  r3_34.maskY = r3_34.height * 0.5
  r1_34:insert(1, r3_34)
  local r6_34 = require("common.listView").new({
    grp = display.newGroup(),
    top = 0,
    left = 0,
    width = r4_34.width,
    height = r4_34.height,
    contentWidth = r4_34.width,
    onRender = r41_0,
  })
  r3_34:insert(r6_34.getScrollStage())
  r2_0 = r6_34
  local r7_34 = util.LoadParts(r1_34, r1_0.LB_L("bonus_get_title_"), 0, 40)
  r7_34.x = r1_34.width * 0.5 - r7_34.width * 0.5
  r15_0 = anime.RegisterWithInterval(r12_0.GetData(), 50, 550, "data/login_bonus/receive", 100)
  anime.Show(r15_0, true)
  anime.Loop(r15_0, true)
  r1_34:insert(anime.GetSprite(r15_0))
  r14_0 = anime.RegisterWithInterval(r11_0.GetData(), 798, 415, "data/login_bonus/receive", 100)
  anime.Show(r14_0, true)
  anime.Loop(r14_0, true)
  r1_34:insert(anime.GetSprite(r14_0))
  r16_0 = anime.RegisterWithInterval(r13_0.GetData(), 904, 415, "data/login_bonus/receive", 100)
  anime.Show(r16_0, true)
  anime.Loop(r16_0, true)
  r1_34:insert(anime.GetSprite(r16_0))
  local r11_34 = util.LoadParts(r1_34, r1_0.LB("ticket_rewind_s"), 770, 110)
  local r12_34 = util.LoadParts(r1_34, r1_0.LB("ticket_flare_s"), 770, 165)
  r4_0 = display.newText(r1_34, string.format("%d/%d", 0, login.login_bonus_item_data.getRewindMaxLimit()), 850, 120, native.systemFont, r19_0)
  r4_0:setReferencePoint(display.TopLeftReferencePoint)
  r5_0 = display.newText(r1_34, string.format("%d/%d", 0, login.login_bonus_item_data.getFlareMaxLimit()), 850, 175, native.systemFont, r19_0)
  r5_0:setReferencePoint(display.TopLeftReferencePoint)
  local r14_34 = util.LoadBtn({
    rtImg = r1_34,
    fname = r1_0.LB_L("bonus_list_"),
    x = 766,
    y = 244,
    func = r28_0,
  })
  r8_0 = util.LoadBtn({
    rtImg = r1_34,
    fname = r1_0.LB_L("all_receive_"),
    x = 766,
    y = 477,
  })
  r8_0.alpha = 0.8
  local r15_34 = util.LoadBtn({
    rtImg = r1_34,
    fname = r1_0.O("close"),
    x = 870,
    y = 7,
    func = r40_0,
  })
end
local function r43_0(r0_35)
  -- line: [831, 882] id: 35
  if r0_35 == nil then
    return 
  end
  for r4_35, r5_35 in pairs(r0_35) do
    local r6_35 = nil
    local r7_35 = nil
    local r8_35 = nil
    local r9_35 = nil
    local r10_35 = nil
    local r11_35 = false
    if r4_35 and type(r4_35) == "string" then
      r6_35 = r4_35
    end
    if r5_35 and type(r5_35) == "table" and 4 <= #r5_35 then
      r7_35 = r5_35[1]
      r8_35 = r5_35[2]
      r9_35 = r5_35[3]
      r10_35 = r5_35[4]
    end
    local r12_35 = _G.LoginItems[tostring(r7_35)]
    if r12_35.itemType == _G.ItemTypeUnit and db.GetIsLockSummonData(_G.UserID, r12_35.charId) == 0 then
      r7_35 = r17_0
      r8_35 = r18_0
      r11_35 = true
    end
    if r7_35 ~= nil and r8_35 ~= nil and r9_35 ~= nil and r10_35 ~= nil then
      r1_0.addItem(r6_35, r7_35, r8_35, r9_35, r10_35, r11_35)
    end
  end
  if #r2_0.getRows() > 0 then
    r8_0:addEventListener("touch", r38_0)
    r8_0.alpha = 1
  end
end
local function r44_0(r0_36)
  -- line: [887, 917] id: 36
  for r6_36, r7_36 in pairs(r0_36) do
    local r8_36 = r6_36
    if type(r8_36) ~= "string" then
      r8_36 = tostring(r8_36)
    end
    local r9_36 = r7_36
    if type(r9_36) ~= "number" then
      r9_36 = tonumber(r9_36)
    end
    if _G.LoginItems[r8_36].itemType == _G.ItemTypeQuantity then
      if r8_36 == "2" then
        r10_0 = r9_36
        r1_0.setFlare(r10_0)
      end
      if r8_36 == "3" then
        r9_0 = r9_36
        r1_0.setRewind(r9_0)
      end
    end
  end
end
local function r45_0(r0_37)
  -- line: [922, 951] id: 37
  if r0_37.isError then
    native.showAlert("DefenseWitches", db.GetMessage(330), {
      "OK"
    })
    r26_0()
    return 
  end
  DebugPrint("--> " .. r0_37.response)
  local r1_37 = r0_0.decode(r0_37.response)
  if r1_37 ~= nil then
    if r1_37.status == 1 and type(r1_37.result) == "table" then
      r44_0(r1_37.result)
    elseif r1_37.status == 0 then
      native.showAlert("DefenseWitches", db.GetMessage(331), {
        "OK"
      })
      r26_0()
    end
  else
    native.showAlert("DefenseWitches", db.GetMessage(331), {
      "OK"
    })
    r26_0()
  end
end
local function r46_0(r0_38)
  -- line: [956, 986] id: 38
  if r0_38.isError then
    native.showAlert("DefenseWitches", db.GetMessage(330), {
      "OK"
    })
    r26_0()
    return 
  end
  DebugPrint("==> " .. r0_38.response)
  local r1_38 = r0_0.decode(r0_38.response)
  if r1_38 ~= nil then
    if r1_38.status == 1 and type(r1_38.result) == "table" then
      r43_0(r1_38.result)
    elseif r1_38.status == 1 then
      r30_0()
    elseif r1_38.status == 0 then
      native.showAlert("DefenseWitches", db.GetMessage(331), {
        "OK"
      })
      r26_0()
    end
  else
    native.showAlert("DefenseWitches", db.GetMessage(331), {
      "OK"
    })
    r26_0()
  end
end
local function r47_0(r0_39)
  -- line: [991, 1046] id: 39
  if r0_39 == nil then
    return 
  end
  local r1_39 = nil
  local r2_39 = nil
  local r3_39 = {}
  for r7_39, r8_39 in pairs(r0_39) do
    table.insert(r3_39, tonumber(r7_39))
  end
  table.sort(r3_39)
  local r4_39 = 0
  local r5_39 = 0
  local r6_39 = 0
  local r7_39 = false
  for r11_39, r12_39 in pairs(r3_39) do
    DebugPrint(tostring(r12_39) .. " : " .. tostring(r0_39[tostring(r12_39)][1]) .. " : " .. tostring(r0_39[tostring(r12_39)][2]))
    local r13_39 = tonumber(r0_39[tostring(r12_39)][1])
    r5_39 = tonumber(r0_39[tostring(r12_39)][2])
    if r13_39 == 0 then
      r4_39 = r4_39 + 1
    end
    if r13_39 == 0 and (r5_39 == _G.LoginItems["1"].id and r22_0 <= tonumber(r0_39[tostring(r12_39)][3]) or r5_39 == _G.LoginItems["4"].id or r5_39 == _G.LoginItems["5"].id or r5_39 == _G.LoginItems["6"].id or r5_39 == _G.LoginItems["7"].id or r5_39 == _G.LoginItems["8"].id or r5_39 == _G.LoginItems["9"].id) then
      r7_39 = true
      r6_39 = tonumber(r0_39[tostring(r12_39)][3])
      break
    end
  end
  if r7_39 then
    r21_0 = {
      dayCount = r4_39,
      itemId = r5_39,
      quantity = r6_39,
    }
  end
end
local function r48_0(r0_40)
  -- line: [1051, 1080] id: 40
  if r0_40.isError then
    native.showAlert("DefenseWitches", db.GetMessage(35), {
      "OK"
    })
    r26_0()
    return 
  end
  DebugPrint("receiveLoginBonusList")
  DebugPrint(r0_40.response)
  local r1_40 = r0_0.decode(r0_40.response)
  if r1_40 ~= nil then
    if r1_40.status == 1 and type(r1_40.result) == "table" then
      r47_0(r1_40.result)
    else
      native.showAlert("DefenseWitches", db.GetMessage(35), {
        "OK"
      })
      r26_0()
    end
  else
    native.showAlert("DefenseWitches", db.GetMessage(35), {
      "OK"
    })
    r26_0()
  end
end
function new()
  -- line: [1085, 1113] id: 41
  local r0_41 = login.base_login_bonus_dialog.base_login_bonus_dialog.new()
  r6_0 = false
  r7_0 = false
  r3_0 = {}
  r21_0 = nil
  r23_0 = false
  r10_0 = db.GetInventoryItem(_G.UserInquiryID, _G.LoginItems["2"].id)
  r9_0 = db.GetInventoryItem(_G.UserInquiryID, _G.LoginItems["3"].id)
  setmetatable(r1_0, {
    __index = r0_41,
  })
  r42_0(r0_41)
  r1_0.setFlare(r10_0)
  r1_0.setRewind(r9_0)
  return r1_0
end
function r1_0.addItem(r0_42, r1_42, r2_42, r3_42, r4_42, r5_42)
  -- line: [1118, 1139] id: 42
  if type(r1_42) ~= "string" then
    r1_42 = tostring(r1_42)
  end
  local r6_42 = login.login_bonus_item_data.getItem(r1_42)
  local r7_42 = #r3_0 + 1
  r3_0[r7_42] = {}
  r3_0[r7_42].postId = r0_42
  r3_0[r7_42].id = r1_42
  r3_0[r7_42].quantity = r2_42
  r3_0[r7_42].name = login.login_bonus_item_data.getName(r1_42)
  r3_0[r7_42].timeHour = r3_42
  r3_0[r7_42].timeMinutes = r4_42
  r3_0[r7_42].changedCrystal = r5_42
  local r8_42 = r2_0.add
  local r9_42 = {
    isCategory = false,
    rowHeight = 146,
  }
  r9_42.rowColor = {
    default = {
      255,
      128,
      128,
      0
    },
  }
  r8_42(r9_42)
end
function r1_0.setRewind(r0_43)
  -- line: [1144, 1157] id: 43
  local r1_43 = r4_0.x
  local r2_43 = r4_0.y
  local r3_43 = r0_43
  if type(r0_43) ~= "number" then
    r3_43 = tonumber(r0_43)
  end
  r4_0.text = string.format("%d/%d", r0_43, login.login_bonus_item_data.getRewindMaxLimit())
  r4_0:setReferencePoint(display.TopLeftReferencePoint)
  r4_0.x = r1_43
  r4_0.y = r2_43
end
function r1_0.setFlare(r0_44)
  -- line: [1162, 1175] id: 44
  local r1_44 = r5_0.x
  local r2_44 = r5_0.y
  local r3_44 = r0_44
  if type(r0_44) == "string" then
    r3_44 = tonumber(r0_44)
  end
  r5_0.text = string.format("%d/%d", r0_44, login.login_bonus_item_data.getFlareMaxLimit())
  r5_0:setReferencePoint(display.TopLeftReferencePoint)
  r5_0.x = r1_44
  r5_0.y = r2_44
end
function r1_0.show()
  -- line: [1180, 1186] id: 45
  r24_0().show()
  server.GetLoginBonusList(_G.UserInquiryID, r48_0)
  server.GetKeepItemList(_G.UserInquiryID, r46_0)
end
