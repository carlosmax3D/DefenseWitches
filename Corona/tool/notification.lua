-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("plugin.notifications")
local r1_0 = nil
local function r2_0(r0_1)
  -- line: [9, 11] id: 1
  return os.time(os.date("!*t", r0_1))
end
local function r3_0(r0_2, r1_2, r2_2)
  -- line: [17, 34] id: 2
  local r3_2 = r0_2.dayAfter
  if r3_2 ~= nil then
    r3_2 = r0_2.dayAfter
  else
    r3_2 = 0
  end
  local r4_2 = r0_2.hour
  if r4_2 ~= nil then
    r4_2 = r0_2.hour
  else
    r4_2 = "00"
  end
  local r5_2 = r0_2.min
  if r5_2 ~= nil then
    r5_2 = r0_2.min
  else
    r5_2 = "00"
  end
  local r6_2 = r0_2.sec
  if r6_2 ~= nil then
    r6_2 = r0_2.sec
  else
    r6_2 = "00"
  end
  local r7_2 = {
    year = os.date("%Y", os.time() + r3_2 * 24 * 60 * 60),
    month = os.date("%m", os.time() + r3_2 * 24 * 60 * 60),
    day = os.date("%d", os.time() + r3_2 * 24 * 60 * 60),
    hour = r4_2,
    min = r5_2,
    sec = r6_2,
  }
  if r2_0(os.time()) < os.time(r7_2) then
    r1_0 = r0_0.scheduleNotification(r7_2, {
      alert = r1_2,
      badge = r2_2,
    })
  end
end
return {
  RemoveNotification = function()
    -- line: [37, 43] id: 3
    native.setProperty("applicationIconBadgeNumber", 0)
    if r1_0 ~= nil then
      r0_0.cancelNotification(r1_0)
    end
  end,
  SetLoginBonusNotification = function()
    -- line: [46, 76] id: 4
    if _G.GameData.local_notification ~= nil and _G.GameData.local_notification == false then
      return 
    end
    local r0_4 = {}
    local r1_4 = db.GetMessage(342)
    if _G.IsDebug then
      local r2_4 = 300
      r0_4 = {
        dayAfter = 0,
        hour = os.date("%H", r2_0(os.time() + r2_4)),
        min = os.date("%M", r2_0(os.time() + r2_4)),
        sec = os.date("%S", r2_0(os.time() + r2_4)),
      }
    else
      r0_4 = {
        dayAfter = 1,
        hour = 0,
        min = 0,
        sec = 0,
      }
    end
    r3_0(r0_4, r1_4, 1, false)
  end,
  SetLocalNotification = function()
    -- line: [79, 110] id: 5
    if _G.GameData and _G.GameData.local_notification ~= nil and _G.GameData.local_notification == false then
      return 
    end
    local r0_5 = {}
    local r1_5 = db.GetMessage(343)
    if _G.IsDebug then
      local r2_5 = 600
      r0_5 = {
        dayAfter = 0,
        hour = os.date("%H", r2_0(os.time() + r2_5)),
        min = os.date("%M", r2_0(os.time() + r2_5)),
        sec = os.date("%S", r2_0(os.time() + r2_5)),
      }
    else
      r0_5 = {
        dayAfter = 3,
        hour = 0,
        min = 0,
        sec = 0,
      }
    end
    r3_0(r0_5, r1_5, 1, false)
  end,
  InitRemoteNotification = function(r0_6)
    -- line: [112, 150] id: 6
    local r1_6 = "http://push-iaps.n-gate.jp/android?"
--    if nil and not nil then
--      go to label_3	-- block#1 is visited secondly
--    end
    Runtime:addEventListener("notification", function(r0_7)
      -- line: [119, 147] id: 7
      local function r1_7(r0_8)
        -- line: [120, 124] id: 8
        if r0_8.isError then
        end
      end
      local function r2_7(r0_9)
        -- line: [126, 138] id: 9
        local r1_9 = 0
        if _G.UserInquiryID then
          r1_9 = _G.UserInquiryID
        end
        network.request(r1_6 .. "itoken=" .. r0_9 .. "&lang=" .. _G.SystemLanguage .. "&uid=" .. r1_9, "POST", r1_7, {
          headers = {},
          body = "",
        })
      end
      if r0_7.type == "remoteRegistration" and r0_7.token ~= nil then
        r2_7(r0_7.token)
      elseif r0_7.type == "remote" then
      end
    end)
  end,
  InitOneSigalNotification = function()
    -- line: [152, 171] id: 10
    local function r0_10(r0_11, r1_11, r2_11)
      -- line: [155, 166] id: 11
      if r1_11 then
        if r1_11.discount then
          native.showAlert("Discount!", r0_11, {
            "OK"
          })
        elseif r1_11.actionSelected then
          native.showAlert("Button Pressed!", "ButtonID:" .. r1_11.actionSelected, {
            "OK"
          })
        end
      else
        native.showAlert("OneSignal Message", r0_11, {
          "OK"
        })
      end
    end
    local r1_10 = require("plugin.OneSignal")
    r1_10.SetLogLevel(5, 5)
    r1_10.Init("d7003c2f-423c-4b13-834f-a4ed6c6f5f12", "", r0_10)
  end,
}
