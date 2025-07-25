-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = _G.DebugPrint
local r1_0 = require("tool.g2metrics")
local r2_0 = require("plugin.gameanalytics")
r2_0.isDebug = false
r2_0.submitSystemInfo = false
return {
  game_init = function()
    -- line: [11, 19] id: 1
    r0_0("metrics.game_init")
    r1_0.start()
    r2_0.init({
      game_key = "f4d54bf24e95e2213b77d90176b96903",
      secret_key = "a128da1412a10d234928732837f988d0d3d98653",
      build_name = _G.Version,
    })
  end,
  start_with_init = function(r0_2)
    -- line: [21, 24] id: 2
    r0_0("metrics.start_with_init")
    r2_0.newEvent("user", {
      type = "init",
    })
  end,
  start_with_recovery = function(r0_3, r1_3, r2_3)
    -- line: [26, 29] id: 3
    r0_0("metrics.start_with_recovery")
    r2_0.newEvent("user", {
      type = "recovery",
    })
  end,
  start_with_invitation = function(r0_4)
    -- line: [31, 34] id: 4
    r0_0("metrics.start_with_invitation")
    r2_0.newEvent("user", {
      type = "invitation",
    })
  end,
  cdn_download_start = function()
    -- line: [36, 39] id: 5
    r0_0("metrics.cdn_download_start")
    r2_0.newEvent("design", {
      event_id = "cdn_download_start",
    })
  end,
  cdn_download_complie = function()
    -- line: [41, 44] id: 6
    r0_0("metrics.cdn_download_complie")
    r2_0.newEvent("design", {
      event_id = "cdn_download_complie",
    })
  end,
  click_invite_twtter = function()
    -- line: [46, 49] id: 7
    r0_0("metrics.click_invite_twtter")
    r2_0.newEvent("design", {
      event_id = "click_invite_twtter",
    })
  end,
  click_invite_fb = function()
    -- line: [51, 54] id: 8
    r0_0("metrics.click_invite_fb")
    r2_0.newEvent("design", {
      event_id = "click_invite_fb",
    })
  end,
  click_invite_mail = function()
    -- line: [56, 59] id: 9
    r0_0("metrics.click_invite_mail")
    r2_0.newEvent("design", {
      event_id = "click_invite_mail",
    })
  end,
  click_clear_twtter = function()
    -- line: [61, 64] id: 10
    r0_0("metrics.click_clear_twtter")
    r2_0.newEvent("design", {
      event_id = "click_clear_twtter",
    })
  end,
  click_clear_fb = function()
    -- line: [66, 69] id: 11
    r0_0("metrics.click_clear_fb")
    r2_0.newEvent("design", {
      event_id = "click_clear_fb",
    })
  end,
  stage_start = function(r0_12, r1_12)
    -- line: [72, 76] id: 12
    r0_0("metrics.stage_start")
    r1_0.trackStartMission(tostring(tonumber(r0_12) * 1000 + tonumber(r1_12)))
  end,
  stage_clear = function(r0_13, r1_13)
    -- line: [78, 82] id: 13
    r0_0("metrics.stage_clear")
    r1_0.trackEndMission(tostring(tonumber(r0_13) * 1000 + tonumber(r1_13)))
  end,
  stage_game_over = function(r0_14, r1_14, r2_14, r3_14)
    -- line: [84, 88] id: 14
    r0_0("metrics.stage_game_over")
    r1_0.trackFailMission(tostring(tonumber(r0_14) * 1000 + tonumber(r1_14)), tostring(r2_14))
  end,
  stage_restart = function(r0_15, r1_15, r2_15, r3_15, r4_15)
    -- line: [91, 94] id: 15
    r0_0("metrics.stage_restart")
    r0_0({
      r0_15,
      r1_15,
      r2_15,
      r3_15,
      r4_15
    })
  end,
  stage_clear_ad_tap = function(r0_16, r1_16, r2_16)
    -- line: [96, 99] id: 16
    r0_0("metrics.stage_clear_ad_tap")
    r0_0({
      r0_16,
      r1_16,
      r2_16
    })
  end,
  character_level_up = function(r0_17, r1_17)
    -- line: [102, 105] id: 17
    r0_0("metrics.character_level_up")
    r0_0({
      r0_17,
      r1_17
    })
  end,
  bingo_clear = function()
    -- line: [107, 109] id: 18
    r0_0("metrics.bingo_clear")
  end,
  ticket_use_flare = function()
    -- line: [112, 114] id: 19
    r0_0("metrics.ticket_use_flare")
  end,
  ticket_use_rewind = function()
    -- line: [116, 118] id: 20
    r0_0("metrics.ticket_use_rewind")
  end,
  _ticket_get_flare = function(r0_21)
    -- line: [120, 123] id: 21
    r0_0("metrics._ticket_get_flare")
    r0_0({
      r0_21
    })
  end,
  _ticket_get_rewind = function(r0_22)
    -- line: [125, 128] id: 22
    r0_0("metrics._ticket_get_rewind")
    r0_0({
      r0_22
    })
  end,
  _crystal_charge = function(r0_23, r1_23)
    -- line: [131, 140] id: 23
    r0_0("metrics._crystal_charge")
    local r4_23 = r0_23.jpy
    local r5_23 = r0_23.price
    local r6_23 = r0_23.priceLocale
    r1_0.trackCharge(r0_23.title, r5_23, r6_23, r0_23.crystal)
    r2_0.newEvent("business", {
      event_id = "_crystal_charge",
      amount = r5_23,
      currency = r6_23,
    })
  end,
  _crystal_bonus = function(r0_24)
    -- line: [142, 145] id: 24
    r0_0("metrics._crystal_bonus")
    r1_0.trackGetGameCurrencyAmount(r0_24, "game_clear")
  end,
  crystal_buy_character = function(r0_25, r1_25)
    -- line: [148, 152] id: 25
    r0_0("metrics.crystal_buy_character")
    r1_0.trackUseGameCurrencyAmount(r1_25, "Sink" .. r0_25)
    r2_0.newEvent("design", {
      event_id = "Sink" .. r0_25,
      amount = r1_25,
    })
  end,
  crystal_unlock_stage = function(r0_26, r1_26, r2_26)
    -- line: [154, 159] id: 26
    r0_0("metrics.crystal_unlock_stage")
    local r3_26 = tostring(tonumber(r0_26) * 1000 + tonumber(r1_26))
    r1_0.trackUseGameCurrencyAmount(r2_26, "Sink" .. r3_26)
    r2_0.newEvent("design", {
      event_id = "Sink" .. r3_26,
      amount = r2_26,
    })
  end,
  crystal_orb_upgrade = function(r0_27)
    -- line: [161, 169] id: 27
    r0_0("metrics.crystal_orb_upgrade")
    for r4_27, r5_27 in pairs(r0_27) do
      local r6_27 = r5_27[1]
      local r7_27 = r5_27[3]
      r1_0.trackBuyItem("Sink" .. r6_27, 1, r7_27)
      r2_0.newEvent("design", {
        event_id = "Sink" .. r6_27,
        amount = r7_27,
      })
    end
  end,
  crystal_buy_evo = function(r0_28)
    -- line: [171, 179] id: 28
    r0_0("metrics.crystal_buy_evo")
    for r4_28, r5_28 in pairs(r0_28) do
      local r6_28 = r5_28[1]
      local r7_28 = r5_28[3]
      r1_0.trackBuyItem("Sink" .. r6_28, 1, r7_28)
      r2_0.newEvent("design", {
        event_id = "Sink" .. r6_28,
        amount = r7_28,
      })
    end
  end,
  crystal_set_character_in_stage = function(r0_29, r1_29, r2_29, r3_29)
    -- line: [181, 185] id: 29
    r0_0("metrics.crystal_set_character_in_stage")
    r1_0.trackBuyItem("Sink" .. r2_29, 1, r3_29)
    r2_0.newEvent("design", {
      event_id = "Sink" .. r2_29,
      amount = r3_29,
    })
  end,
  crystal_character_upgrade_in_stage = function(r0_30, r1_30, r2_30)
    -- line: [187, 195] id: 30
    r0_0("metrics.crystal_character_upgrade_in_stage")
    for r6_30, r7_30 in pairs(r2_30) do
      local r8_30 = r7_30[1]
      local r9_30 = r7_30[3]
      r1_0.trackBuyItem("Sink" .. r8_30, 1, r9_30)
      r2_0.newEvent("design", {
        event_id = "Sink" .. r8_30,
        amount = r9_30,
      })
    end
  end,
  crystal_powerup_in_stage = function(r0_31, r1_31, r2_31)
    -- line: [197, 205] id: 31
    r0_0("metrics.crystal_powerup_in_stage")
    for r6_31, r7_31 in pairs(r2_31) do
      local r8_31 = r7_31[1]
      local r9_31 = r7_31[3]
      r1_0.trackBuyItem("Sink" .. r8_31, 1, r9_31)
      r2_0.newEvent("design", {
        event_id = "Sink" .. r8_31,
        amount = r9_31,
      })
    end
  end,
  crystal_rewind_in_stage = function(r0_32, r1_32, r2_32)
    -- line: [207, 211] id: 32
    r0_0("metrics.crystal_rewind_in_stage")
    r1_0.trackUseGameCurrencyAmount(r2_32, "Sink" .. "rewind")
    r2_0.newEvent("design", {
      event_id = "Sink" .. "rewind",
      amount = r2_32,
    })
  end,
  crystal_flare_in_stage = function(r0_33, r1_33, r2_33)
    -- line: [213, 217] id: 33
    r0_0("metrics.crystal_flare_in_stage")
    r1_0.trackUseGameCurrencyAmount(r2_33, "Sink" .. "flare")
    r2_0.newEvent("design", {
      event_id = "Sink" .. "flare",
      amount = r2_33,
    })
  end,
  crystal_hp_recory_in_stage = function(r0_34, r1_34, r2_34)
    -- line: [219, 227] id: 34
    r0_0("metrics.crystal_hp_recory_in_stage")
    for r6_34, r7_34 in pairs(r2_34) do
      local r8_34 = r7_34[1]
      local r9_34 = r7_34[3]
      r1_0.trackBuyItem("Sink" .. r8_34, 1, r9_34)
      r2_0.newEvent("design", {
        event_id = "Sink" .. r8_34,
        amount = r9_34,
      })
    end
  end,
  crystal_magic_powerup_in_stage = function(r0_35, r1_35, r2_35)
    -- line: [229, 237] id: 35
    r0_0("metrics.crystal_magic_powerup_in_stage")
    for r6_35, r7_35 in pairs(r2_35) do
      local r8_35 = r7_35[1]
      local r9_35 = r7_35[3]
      r1_0.trackBuyItem("Sink" .. r8_35, 1, r9_35)
      r2_0.newEvent("design", {
        event_id = "Sink" .. r8_35,
        amount = r9_35,
      })
    end
  end,
  crystal_magic_restart = function(r0_36, r1_36, r2_36)
    -- line: [239, 247] id: 36
    r0_0("metrics.crystal_magic_restart")
    for r6_36, r7_36 in pairs(r2_36) do
      local r8_36 = r7_36[1]
      local r9_36 = r7_36[3]
      r1_0.trackBuyItem("Sink" .. r8_36, 1, r9_36)
      r2_0.newEvent("design", {
        event_id = "Sink" .. r8_36,
        amount = r9_36,
      })
    end
  end,
  crystal_stop_clock = function(r0_37, r1_37, r2_37)
    -- line: [249, 253] id: 37
    r0_0("metrics.crystal_stop_clock")
    r1_0.trackUseGameCurrencyAmount(r2_37, "Sink" .. "clock")
    r2_0.newEvent("design", {
      event_id = "Sink" .. "clock",
      amount = r2_37,
    })
  end,
}
