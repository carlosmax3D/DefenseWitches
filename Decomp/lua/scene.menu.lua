-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("ui.option")
local r1_0 = require("scene.title")
local r2_0 = require("logic.gamecenter")
local r3_0 = require("scene.invite")
local r4_0 = require("json")
local r5_0 = "menuview"
local r6_0 = {}
local r7_0 = {}
local r8_0 = 0
local r9_0 = false
local r10_0 = nil
local r11_0 = nil
local r12_0 = require("common.already_read_manager")
local r13_0 = require("anm.download_cha_anm_01")
local r14_0 = require("anm.download_cha_anm_02")
local r15_0 = require("anm.download_cha_anm_04")
local r16_0 = nil
local r17_0 = nil
local r18_0 = nil
local function r19_0(r0_1)
  -- line: [36, 36] id: 1
  return "data/menu/" .. r0_1 .. ".png"
end
local function r20_0(r0_2)
  -- line: [37, 37] id: 2
  return r19_0(r0_2 .. _G.UILanguage)
end
local function r21_0(r0_3)
  -- line: [38, 38] id: 3
  return "data/option/" .. r0_3 .. ".png"
end
local function r22_0(r0_4)
  -- line: [39, 39] id: 4
  return r21_0(r0_4 .. _G.UILanguage)
end
local function r23_0(r0_5)
  -- line: [44, 63] id: 5
  if r0_5.isError then
    return 
  end
  local r1_5 = r4_0.decode(r0_5.response)
  if r1_5.status == 1 then
    r8_0 = r1_5.result
    if r7_0[2] ~= nil then
      if r8_0 > 0 then
        r7_0[2].isVisible = true
      else
        r7_0[2].isVisible = false
      end
    end
  else
    r8_0 = 0
  end
end
local function r24_0()
  -- line: [65, 69] id: 6
  if _G.UserInquiryID ~= nil and _G.UserToken ~= nil then
    server.GetNotifyKeepItem(_G.UserInquiryID, r23_0)
  end
end
local function r26_0()
  -- line: [80, 94] id: 8
  r17_0 = anime.RegisterWithInterval(r14_0.GetData(), 506, 460, "data/download", 100)
  anime.Show(r17_0, true)
  anime.Loop(r17_0, true)
  r16_0 = anime.RegisterWithInterval(r13_0.GetData(), 644, 460, "data/download", 100)
  anime.Show(r16_0, true)
  anime.Loop(r16_0, true)
  r18_0 = anime.RegisterWithInterval(r15_0.GetData(), 855, 545, "data/download", 100)
  anime.Show(r18_0, true)
  anime.Loop(r18_0, true)
end
local function r27_0()
  -- line: [96, 108] id: 9
  r9_0 = true
  if r16_0 then
    anime.Remove(r16_0)
  end
  if r17_0 then
    anime.Remove(r17_0)
  end
  if r18_0 then
    anime.Remove(r18_0)
  end
end
local function r28_0()
  -- line: [110, 126] id: 10
  for r3_10 = 1, #r6_0, 1 do
    if r6_0[r3_10] then
      r6_0[r3_10]:removeSelf()
    end
  end
  for r3_10 = 1, #r7_0, 1 do
    if r7_0[r3_10] then
      r7_0[r3_10]:removeSelf()
    end
  end
  r27_0()
  events.DeleteNamespace(r5_0)
end
local function r29_0(r0_11)
  -- line: [130, 133] id: 11
  sound.PlaySE(1)
  util.ChangeScene({
    prev = r28_0,
    scene = "invite",
    param = {
      back = "menu",
    },
    efx = "fade",
  })
end
local function r30_0(r0_12)
  -- line: [137, 189] id: 12
  if r9_0 == true or r0_12.rtImg == nil then
    return 
  end
  if not r10_0 then
    return 
  end
  if not r11_0 then
    return 
  end
  local r1_12 = r0_12.rtImg
  if _G.ServerStatus.invitation == 1 then
    server.InvitePersons(_G.UserToken, function(r0_13)
      -- line: [150, 183] id: 13
      if r9_0 == true or r0_12.rtImg == nil then
        return 
      end
      if not r11_0 then
        return 
      end
      local r1_13 = 0
      local r2_13 = r4_0.decode(r0_13.response)
      if r2_13.persons ~= nil then
        r1_13 = r3_0.GetMaxPersons() - r2_13.persons
      end
      if r1_13 == 0 then
        return 
      end
      local r3_13 = display.newGroup()
      display.newImage(r3_13, r20_0("menu_button_invite_"), 0, 0, true)
      r3_13:setReferencePoint(display.TopLeftReferencePoint)
      local r4_13 = display.newGroup()
      r3_13:insert(r4_13)
      r4_13.x = 230
      r4_13.y = 56
      local r5_13 = display.newRoundedRect(r4_13, 0, 0, 70, 26, 13)
      r5_13:setFillColor(0)
      r5_13.alpha = 0.4
      local r7_13 = display.newText(r4_13, string.format(db.GetMessage(402), r1_13), 0, 0, native.systemFont, 17)
      r7_13:setReferencePoint(display.CenterReferencePoint)
      r7_13.x = r4_13.width * 0.5
      util.LoadBtnGroup({
        group = r3_13,
        cx = r0_12.x,
        cy = 484,
        func = r29_0,
      })
      r3_13:setReferencePoint(display.CenterReferencePoint)
      r1_12:insert(r3_13)
    end)
  end
  r10_0 = false
end
return {
  removeItemMark = function()
    -- line: [71, 75] id: 7
    if r7_0 and r7_0 then
      r7_0[2].isVisible = false
    end
  end,
  Close = r28_0,
  new = function(r0_14)
    -- line: [191, 372] id: 14
    r8_0 = 0
    local r1_14 = db.GetLoginBonusRecive(_G.UserInquiryID)
    events.SetNamespace(r5_0)
    local r2_14 = display.newGroup()
    local r3_14 = util.MakeGroup(r2_14)
    util.MakeFrame(r2_14)
    local r4_14 = util.MakeGroup(r2_14)
    r6_0[1] = util.LoadParts(r3_14, r19_0("menu_bg"), 0, 0)
    r6_0[2] = util.LoadParts(r3_14, r20_0("menu_title_"), 289, 42)
    r6_0[3] = util.LoadBtn({
      rtImg = r3_14,
      fname = r21_0("close"),
      x = 872,
      y = 0,
      func = function(r0_15)
        -- line: [211, 220] id: 15
        sound.PlaySE(2)
        r28_0()
        if r0_14 and r0_14.back == "map" then
          util.ChangeScene({
            scene = "map",
            efx = "fade",
          })
        else
          util.ChangeScene({
            scene = "title",
            efx = "fade",
          })
        end
        return true
      end,
    })
    local r6_14 = 126
    local r7_14 = 510
    r6_0[4] = util.LoadBtn({
      rtImg = r3_14,
      fname = r20_0("menu_button_info_"),
      x = r6_14,
      y = 124,
      func = function(r0_16)
        -- line: [227, 236] id: 16
        util.ReachableSwitch(function()
          -- line: [229, 233] id: 17
          r27_0()
          sound.PlaySE(1)
          util.ChangeScene({
            scene = "info",
            efx = "fade",
            param = {
              back = "menu",
            },
          })
        end, function()
          -- line: [233, 234] id: 18
        end)
        return true
      end,
    })
    local function r9_14(r0_19)
      -- line: [240, 252] id: 19
      sound.PlaySE(1)
      if _G.UserInquiryID ~= nil and _G.UserToken ~= nil then
        require("login.login_bonus_item_receive_dialog").new().show()
      else
        native.showAlert("DefenseWitches", db.GetMessage(35), {
          "OK"
        })
      end
      return true
    end
    local r10_14 = r6_0
    local r11_14 = util.LoadBtn
    local r12_14 = {
      rtImg = r3_14,
      fname = r20_0("menu_button_receive_"),
      x = r7_14,
    }
    local r13_14 = util.IsBingoEnabled()
    if r13_14 then
      r13_14 = 364 or 244
    else
      goto label_102	-- block#2 is visited secondly
    end
    r12_14.y = r13_14
    r12_14.func = r9_14
    r10_14[5] = r11_14(r12_14)
    r6_0[8] = util.LoadBtn({
      rtImg = r3_14,
      fname = r20_0("menu_button_help_"),
      x = r6_14,
      y = 244,
      func = function(r0_20)
        -- line: [256, 261] id: 20
        r27_0()
        sound.PlaySE(1)
        util.ChangeScene({
          scene = "help",
          efx = "fade",
        })
        return true
      end,
    })
    r6_0[9] = util.LoadBtn({
      rtImg = r3_14,
      fname = r20_0("menu_button_levelup_"),
      x = r6_14,
      y = 364,
      func = function(r0_21)
        -- line: [265, 270] id: 21
        r27_0()
        sound.PlaySE(1)
        util.ChangeScene({
          scene = "evo.evo_view",
          efx = "fade",
          param = {
            back = "menu",
          },
        })
        return true
      end,
    })
    function r12_14(r0_22)
      -- line: [274, 286] id: 22
      sound.PlaySE(2)
      r0_0.Close(false)
      db.SaveOptionData(_G.GameData)
      r26_0()
      if _G.GameData.bgm then
        bgm.Play(1)
      else
        bgm.Stop()
      end
    end
    r6_0[10] = util.LoadBtn({
      rtImg = r3_14,
      fname = r20_0("menu_button_option_"),
      x = r7_14,
      y = 124,
      func = function(r0_23)
        -- line: [288, 300] id: 23
        r27_0()
        sound.PlaySE(1)
        r0_0.Open({
          rtImg = r0_23.param,
          mode = false,
          func = {
            close = r12_14,
            help = nil,
          },
        })
      end,
      show = true,
      param = r4_14,
    })
    if util.IsBingoEnabled() then
      r6_0[11] = util.LoadBtn({
        rtImg = r3_14,
        fname = r20_0("menu_bingo_"),
        x = r7_14,
        y = 244,
        func = function(r0_24)
          -- line: [305, 310] id: 24
          r27_0()
          sound.PlaySE(1)
          util.ChangeScene({
            scene = "bingo.bingo_card_viewer",
            efx = "fade",
            param = {
              back = "menu",
            },
          })
          return true
        end,
        show = true,
      })
    end
    r9_0 = false
    r10_0 = true
    r11_0 = true
    server.GetStatus(r30_0, {
      rtImg = r3_14,
      x = r6_14,
    })
    local r14_14 = db.GetInviteCode(_G.UserID)
    if r14_14 == nil or r14_14 == "" then
      server.InvitePublish(_G.UserToken, function(r0_25)
        -- line: [323, 333] id: 25
        if not server.CheckError(r0_25) then
          local r1_25 = r4_0.decode(r0_25.response)
          if r1_25.status == 0 then
            r14_14 = r1_25.code
            db.SetInviteCode(_G.UserID, r14_14)
          end
        end
      end)
    end
    r26_0()
    r7_0[1] = util.LoadParts(r3_14, r19_0("mark_info"), r6_14 + 326 - 22.5 - 8, 109.5)
    if r1_0.isInfoUpdate() then
      r7_0[1].isVisible = true
    else
      r7_0[1].isVisible = false
    end
    r7_0[2] = util.LoadParts(r3_14, r19_0("mark_info"), r7_14 + 326 - 22.5 - 8, 349.5)
    if r8_0 > 0 then
      r7_0[2].isVisible = true
    else
      r7_0[2].isVisible = false
    end
    r7_0[3] = util.LoadParts(r3_14, r19_0("mark_info"), r7_14 + 326 - 22.5 - 8, 349.5)
    _G.BingoManager.isBingoEnabled(_G.BingoManager.getBingoCardId(), function(r0_26)
      -- line: [354, 365] id: 26
      if r0_26 == false then
        return 
      end
      if r12_0.getState(r12_0.EVENT_ID.BINGO_CARD_01()) == r12_0.STATE.UNREAD() then
        r7_0[3].isVisible = true
      else
        r7_0[3].isVisible = false
      end
    end)
    r24_0()
    return r2_14
  end,
}
