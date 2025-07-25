-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = {}
local r1_0 = nil
if system.getInfo("environment") ~= "simulator" then
  r1_0 = require("CoronaProvider.g2metrics.stargarage")
end
function r0_0.start()
  -- line: [9, 19] id: 1
  if system.getInfo("environment") ~= "simulator" then
    if system.getInfo("platformName") == "Android" then
      r1_0.setApplication(false)
    else
      r1_0.setGameId("07fe479aef0fe42a24bb5b438c83138d", false)
    end
  end
end
function r0_0.notification()
  -- line: [21, 31] id: 2
  if system.getInfo("environment") ~= "simulator" and system.getInfo("platformName") ~= "Android" then
    r1_0.registerUserNotificationSetting({
      badge = true,
      sound = true,
      alert = false,
    })
  end
end
function r0_0.trackUserRegist(r0_3, r1_3, r2_3, r3_3, r4_3)
  -- line: [33, 45] id: 3
  if system.getInfo("environment") ~= "simulator" and "number" == type(r0_3) and "string" == type(r1_3) and "number" == type(r2_3) and "number" == type(r3_3) and "string" == type(r4_3) then
    r1_0.trackUserRegist(r0_3, r1_3, r2_3, r3_3, r4_3)
  end
end
function r0_0.trackLevelUp(r0_4)
  -- line: [47, 55] id: 4
  if system.getInfo("environment") ~= "simulator" and ("number" == type(r0_4) or "string" == type(r0_4)) then
    r1_0.trackLevelUp(r0_4)
  end
end
function r0_0.trackCompleteTutorial()
  -- line: [57, 63] id: 5
  if system.getInfo("environment") ~= "simulator" then
    r1_0.trackCompleteTutorial()
  end
end
function r0_0.trackCharge(r0_6, r1_6, r2_6, r3_6)
  -- line: [65, 73] id: 6
  if system.getInfo("environment") ~= "simulator" and "string" == type(r0_6) and "number" == type(r1_6) and "string" == type(r2_6) and "number" == type(r3_6) then
    r1_0.trackCharge(r0_6, r1_6, r2_6, r3_6)
  end
end
function r0_0.trackStartMission(r0_7)
  -- line: [75, 83] id: 7
  if system.getInfo("environment") ~= "simulator" and type(r0_7) == "string" then
    r1_0.trackStartMission(r0_7)
  end
end
function r0_0.trackEndMission(r0_8)
  -- line: [85, 93] id: 8
  if system.getInfo("environment") ~= "simulator" and type(r0_8) == "string" then
    r1_0.trackEndMission(r0_8)
  end
end
function r0_0.trackFailMission(r0_9, r1_9)
  -- line: [95, 103] id: 9
  if system.getInfo("environment") ~= "simulator" and "string" == type(r0_9) and "string" == type(r1_9) then
    r1_0.trackFailMission(r0_9, r1_9)
  end
end
function r0_0.trackGetGameCurrencyAmount(r0_10, r1_10)
  -- line: [105, 113] id: 10
  if system.getInfo("environment") ~= "simulator" and "number" == type(r0_10) and "string" == type(r1_10) then
    r1_0.trackGetGameCurrencyAmount(r0_10, r1_10)
  end
end
function r0_0.trackUseGameCurrencyAmount(r0_11, r1_11)
  -- line: [115, 123] id: 11
  if system.getInfo("environment") ~= "simulator" and "number" == type(r0_11) and "string" == type(r1_11) then
    r1_0.trackUseGameCurrencyAmount(r0_11, r1_11)
  end
end
function r0_0.trackBuyItem(r0_12, r1_12, r2_12)
  -- line: [126, 134] id: 12
  if system.getInfo("environment") ~= "simulator" and "string" == type(r0_12) and "number" == type(r1_12) and "number" == type(r2_12) then
    r1_0.trackBuyItem(r0_12, r1_12, r2_12)
  end
end
function r0_0.trackUseItem(r0_13, r1_13)
  -- line: [137, 145] id: 13
  if system.getInfo("environment") ~= "simulator" and "string" == type(r0_13) and "number" == type(r1_13) then
    r1_0.trackUseItem(r0_13, r1_13)
  end
end
function r0_0.trackCustomEvent(r0_14, r1_14)
  -- line: [148, 156] id: 14
  if system.getInfo("environment") ~= "simulator" and "string" == type(r0_14) and "table" == type(r1_14) then
    r1_0.trackCustomEvent(r0_14, r1_14)
  end
end
return r0_0
