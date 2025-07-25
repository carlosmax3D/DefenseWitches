-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = _G.statslog or require("tool.statslog")
local r1_0 = require("logic.pay_item_data")
local r2_0 = _G.KPI_SERVER
local function r3_0(r0_1)
  -- line: [12, 18] id: 1
  if r0_1 == nil then
    return "null"
  end
  r0_1 = string.gsub(r0_1, "([^0-9a-zA-Z])", function(r0_2)
    -- line: [14, 16] id: 2
    return string.format("%%%02X", string.byte(r0_2))
  end)
  return r0_1
end
local function r4_0(r0_3)
  -- line: [20, 31] id: 3
  if _G.IsSimulator and r0_3.isError then
    DebugPrint("kpi post error")
  end
end
local function r5_0(r0_4)
  -- line: [40, 47] id: 4
  local r1_4 = {}
  for r5_4, r6_4 in pairs(r0_4) do
    table.insert(r1_4, string.format("%s=%s", r5_4, r3_0(r6_4)))
  end
  network.request(r2_0, "POST", r4_0, {
    body = table.concat(r1_4, "&"),
  })
end
local function r6_0(r0_5)
  -- line: [49, 55] id: 5
  local r1_5 = _G.UserInquiryID
  if r1_5 then
    r0_5.uid = r1_5
    r5_0(r0_5)
  end
end
local function r7_0(r0_6, r1_6)
  -- line: [57, 66] id: 6
  if r1_6 == nil then
    r1_6 = {}
  end
  r1_6.event = r0_6
  if _G.UserInquiryID then
    r6_0(r1_6)
  else
    server.GetInquiryID(r6_0, r1_6)
  end
end
return {
  Run = function()
    -- line: [68, 75] id: 7
    r7_0("run", {
      device = system.getInfo("model"),
      os = system.getInfo("platformVersion"),
      version = _G.Version,
      country = system.getPreference("ui", "language"),
    })
  end,
  Start = function(r0_8, r1_8)
    -- line: [77, 79] id: 8
    r7_0("start", {
      stage = r0_8 * 1000 + r1_8,
    })
  end,
  Clear = function(r0_9, r1_9, r2_9)
    -- line: [81, 83] id: 9
    r7_0("clear", {
      stage = r0_9 * 1000 + r1_9,
      score = r2_9,
    })
  end,
  Fail = function(r0_10, r1_10)
    -- line: [85, 87] id: 10
    r7_0("fail", {
      stage = r0_10 * 1000 + r1_10,
    })
  end,
  Spend = function(r0_11, r1_11, r2_11, r3_11, r4_11)
    -- line: [89, 130] id: 11
    local r5_11 = {}
    if _G.ExpManager then
      _G.ExpManager.LoadExp()
    end
    local r7_11 = r1_0.getItemData(r1_0.pay_item_data.BuyExp)
    local r8_11 = 0
    if r2_11 == 2102 then
      r8_11 = r7_11[3]
    elseif r2_11 == 2001 or r2_11 == 2002 or r2_11 == 2101 then
      if _G.OrbManager then
        r5_11.have_orb = _G.OrbManager.GetRemainOrb()
        r5_11.max_orb = _G.OrbManager.GetMaxOrb()
      end
      r5_11.hp = _G.HP
      r5_11.wave = _G.WaveNr
      r5_11.score = _G.Score
    end
    if _G.ExpManager then
      r5_11.have_exp = _G.ExpManager.GetExp() + r8_11
    end
    r5_11.item = r2_11
    r5_11.count = r3_11
    r5_11.coin = r4_11
    r0_0.LogSend("item", r5_11)
    if r0_11 == nil and r1_11 == nil then
      r7_0("spend", {
        item = r2_11,
        count = r3_11,
        debit = r4_11,
      })
    else
      r7_0("spend", {
        stage = r0_11 * 1000 + r1_11,
        item = r2_11,
        count = r3_11,
        debit = r4_11,
      })
    end
  end,
  Unlock = function(r0_12, r1_12)
    -- line: [132, 142] id: 12
    r0_0.LogSend("unlock", {
      item = r0_12,
      coin = r1_12,
    })
    r7_0("unlock", {
      item = r0_12,
      debit = r1_12,
    })
  end,
  Credit = function(r0_13)
    -- line: [144, 151] id: 13
    r0_0.LogSend("credit", {
      credit = r0_13,
    })
    r7_0("credit", {
      credit = r0_13,
    })
  end,
  Reward = function(r0_14, r1_14, r2_14)
    -- line: [153, 155] id: 14
    r7_0("reward", {
      campaign = r0_14,
      credit = r1_14,
    })
  end,
  CreditAttempt = function()
    -- line: [157, 159] id: 15
    r7_0("credit_attempt")
  end,
  Invitation = function(r0_16)
    -- line: [162, 164] id: 16
    r7_0("inviter", {
      count = r0_16,
      credit = _G.GetCrystal,
    })
  end,
  Invited = function()
    -- line: [167, 169] id: 17
    r7_0("invitee", {
      credit = _G.GetCrystal,
    })
  end,
  Achievement = function(r0_18)
    -- line: [172, 174] id: 18
    r7_0("achieve", {
      achievement = r0_18,
    })
  end,
}
