-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = _G.IAP_SERVER
local r1_0 = require("crypto")
local r2_0 = require("json")
local r3_0 = require("tool.multipart")
local r4_0 = require("server.server_status")
local r5_0 = require("server.server_movie")
local r6_0 = nil
local r7_0 = false
local r8_0 = {
  r0_0 .. "auth/init",
  r0_0 .. "auth/otp",
  r0_0 .. "auth/token",
  r0_0 .. "item/coin",
  r0_0 .. "store/commit",
  r0_0 .. "item/buy",
  r0_0 .. "game/save",
  r0_0 .. "game/load",
  r0_0 .. "util/timestamp",
  r0_0 .. "password/create",
  r0_0 .. "password/restore",
  r0_0 .. "password/delete",
  r0_0 .. "item/check",
  r0_0 .. "password/generate",
  r0_0 .. "invite/publish",
  r0_0 .. "invite/persons",
  r0_0 .. "invite/receive",
  r0_0 .. "achieve/list",
  r0_0 .. "achieve/unlock",
  r0_0 .. "password/retrieve",
  r0_0 .. "tweets/tweet_status",
  r0_0 .. "tweets/post_tweet"
}
local r9_0 = "loginbonus/"
local r10_0 = r0_0 .. r9_0 .. "stamp"
local r11_0 = r0_0 .. r9_0 .. "postlist"
local r12_0 = r0_0 .. r9_0 .. "getitem"
local r13_0 = r0_0 .. r9_0 .. "numpost"
local r14_0 = r0_0 .. r9_0 .. "list"
local r15_0 = r0_0 .. r9_0 .. "numitem"
local r16_0 = r0_0 .. r9_0 .. "useitem"
local r17_0 = "evo/"
local r18_0 = r0_0 .. r17_0 .. "create"
local r19_0 = r0_0 .. r17_0 .. "setexp"
local r20_0 = r0_0 .. r17_0 .. "getexp"
local r21_0 = r0_0 .. r17_0 .. "useorb"
local r22_0 = r0_0 .. r17_0 .. "recoveryorb"
local r23_0 = r0_0 .. r17_0 .. "getorb"
local r24_0 = r0_0 .. r17_0 .. "gettime"
local r25_0 = 300
local r26_0 = 10
local r27_0 = 1
local r28_0 = -1
local r29_0 = nil
local function r30_0(r0_1)
  -- line: [78, 105] id: 1
  local r2_1 = db.GetServerAccessTime({
    uid = _G.UserID,
    acs_id = r0_1,
  })
  local r3_1 = true
  if r2_1 and type(r2_1) == "table" then
    local r4_1 = os.time()
    local r5_1 = 0
    if type(r2_1.date) == "number" then
      r5_1 = r2_1.date
    end
    if r4_1 - r5_1 < r25_0 and r2_1.result == r27_0 then
      r3_1 = false
    end
  end
  return r3_1
end
local function r31_0(r0_2)
  -- line: [107, 112] id: 2
  r0_2 = string.gsub(r0_2, "([^0-9a-zA-Z])", function(r0_3)
    -- line: [108, 110] id: 3
    return string.format("%%%02X", string.byte(r0_3))
  end)
  return r0_2
end
local function r32_0(r0_4, r1_4, r2_4, r3_4)
  -- line: [121, 139] id: 4
  local r4_4 = {}
  for r8_4, r9_4 in pairs(r3_4) do
    table.insert(r4_4, string.format("%s=%s", r8_4, r31_0(r9_4)))
  end
  local r5_4 = table.concat(r4_4, "&")
  if r1_4 == "GET" then
    local r6_4 = r0_4 .. "?" .. r5_4
    if IsDebug then
      DebugPrint("GET:" .. r6_4)
    end
    network.request(r6_4, "GET", r2_4)
  else
    if IsDebug then
      DebugPrint("POST:" .. r0_4 .. "?" .. r5_4)
    end
    network.request(r0_4, "POST", r2_4, {
      body = r5_4,
    })
  end
end
local function r33_0(r0_5)
  -- line: [144, 159] id: 5
  local r1_5 = "INgzS1K7L25ACvE0rTCOpMFm3be5KHtw" .. _G.UserToken
  if r0_5 then
    for r5_5, r6_5 in pairs(r0_5) do
      local r7_5 = r6_5
      if type(r7_5) ~= "string" then
        r7_5 = tostring(r7_5)
      end
      r1_5 = r1_5 .. r7_5
    end
  end
  return r1_0.digest(r1_0.sha512, r1_5)
end
local function r34_0(r0_6)
  -- line: [161, 164] id: 6
  assert(r0_6, debug.traceback())
  return r2_0.decode(r0_6)
end
local function r35_0(r0_7, r1_7)
  -- line: [166, 174] id: 7
  if r0_7 == nil then
    r0_7 = db.GetMessage(37)
  end
  if type(r0_7) ~= "string" then
    r0_7 = db.GetMessage(r0_7)
  end
  if r1_7 ~= nil then
    native.showAlert("DefenseWitches", r0_7, {
      "OK"
    }, r1_7)
  else
    native.showAlert("DefenseWitches", r0_7, {
      "OK"
    })
  end
end
local function r36_0(r0_8, r1_8)
  -- line: [176, 192] id: 8
  -- error: decompile function#8: Condition failed: `def_pos <= last_end && last_end <= self.stmts.len()`
end
local function r37_0(r0_9)
  -- line: [206, 216] id: 9
  assert(r0_9, debug.traceback())
  local r1_9 = os.time()
  r32_0(r8_0[1], "POST", r0_9, {
    t = tostring(r1_9),
    h = r1_0.digest(r1_0.sha1, "NGateApp" .. tostring(r1_9)),
  })
end
local function r38_0(r0_10, r1_10)
  -- line: [227, 232] id: 10
  assert(r0_10, debug.traceback())
  assert(r1_10, debug.traceback())
  r32_0(r8_0[2], "POST", r1_10, {
    key = r0_10,
  })
end
local function r39_0(r0_11, r1_11, r2_11)
  -- line: [243, 252] id: 11
  assert(r0_11, debug.traceback())
  assert(r1_11, debug.traceback())
  assert(r2_11, debug.traceback())
  r32_0(r8_0[3], "GET", r2_11, {
    id = r0_11,
    otp = r1_11,
  })
end
local function r40_0(r0_12, r1_12, r2_12)
  -- line: [263, 276] id: 12
  assert(r0_12, debug.traceback())
  assert(r1_12, debug.traceback())
  assert(r2_12, debug.traceback())
  local r3_12 = r3_0.new()
  r3_12:addField("key", tostring(r0_12))
  r3_12:addFile("data", r1_12, "application/octet-stream", "savedata")
  network.request(r8_0[7], "POST", r2_12, {
    body = r3_12:getBody(),
    headers = r3_12:getHeaders(),
  })
end
local function r41_0(r0_13, r1_13, r2_13)
  -- line: [279, 291] id: 13
  assert(r0_13, debug.traceback())
  assert(r1_13, debug.traceback())
  local r3_13 = r3_0.new()
  r3_13:addField("key", tostring(r0_13))
  r3_13:add("field", "data", r1_13)
  network.request(r8_0[7], "POST", r2_13, {
    body = r3_13:getBody(),
    headers = r3_13:getHeaders(),
  })
end
local function r42_0(r0_14, r1_14, r2_14, r3_14)
  -- line: [301, 315] id: 14
  assert(r0_14, debug.traceback())
  assert(r1_14, debug.traceback())
  assert(r2_14, debug.traceback())
  if r3_14 == nil then
    r3_14 = system.DocumentsDirectory
  end
  network.download(string.format("%s?key=%s", r8_0[8], r31_0(r0_14)), "GET", r2_14, {
    timeout = 3,
  }, r1_14, r3_14)
end
local function r43_0(r0_15, r1_15)
  -- line: [325, 330] id: 15
  assert(r0_15, debug.traceback())
  assert(r1_15, debug.traceback())
  r32_0(r8_0[9], "GET", r1_15, {
    key = r0_15,
  })
end
local function r44_0(r0_16, r1_16, r2_16, r3_16)
  -- line: [344, 357] id: 16
  assert(r0_16, debug.traceback())
  assert(r1_16, debug.traceback())
  assert(r2_16, debug.traceback())
  assert(r3_16, debug.traceback())
  r32_0(r8_0[5], "POST", r3_16, {
    key = r0_16,
    signature = r2_16,
    ["receipt-data"] = r1_16,
  })
end
local function r45_0(r0_17, r1_17, r2_17, r3_17)
  -- line: [370, 392] id: 17
  assert(r0_17, debug.traceback())
  assert(r1_17, debug.traceback())
  assert(r2_17, debug.traceback())
  assert(r3_17, debug.traceback())
  local r4_17 = nil
  if type(r1_17) ~= "table" then
    r4_17 = {
      key = r0_17,
      item = r1_17,
      n = r2_17,
    }
  else
    r4_17 = {
      key = r0_17,
      item = table.concat(r1_17, ","),
      n = table.concat(r2_17, ","),
    }
  end
  r32_0(r8_0[6], "POST", r3_17, r4_17)
end
local function r46_0(r0_18, r1_18)
  -- line: [402, 426] id: 18
  if _G.NoServerCheck then
    r1_18({
      isError = false,
      response = r2_0.encode({
        status = 0,
        coin = 99999,
      }),
    })
    return 
  end
  if r0_18 == nil then
    return 
  end
  assert(r1_18, debug.traceback())
  local r2_18 = {
    key = r0_18,
  }
  if db.CountAchieveQueue() > 0 then
    db.DisposeAchieveQueue(r0_18, {
      r32_0,
      r8_0[4],
      "GET",
      r1_18,
      r2_18
    })
  else
    r32_0(r8_0[4], "GET", r1_18, r2_18)
  end
end
local function r47_0(r0_19)
  -- line: [428, 462] id: 19
  r7_0 = false
  if r0_19.isError then
    r35_0()
  else
    local r1_19 = r2_0.decode(r0_19.response)
    if r1_19 and r1_19.status == 0 then
      db.UpdateUserID(_G.UserID, r1_19.id, r1_19.key)
      _G.UserInquiryID = r1_19.id
      _G.UserToken = r1_19.key
      if _G.GameMode == _G.GameModeEvo then
        r29_0()
      end
      statslog.LogSend("id_create", {})
    elseif r1_19 then
      r35_0(string.format("Network error(%d)", r1_19.status))
    else
      r35_0()
    end
  end
  if r6_0 then
    --r6_0 = nil
    r6_0[1](r6_0[2])
  end
end
local function r48_0(r0_20, r1_20, r2_20)
  -- line: [467, 473] id: 20
  assert(r0_20, debug.traceback())
  assert(r1_20, debug.traceback())
  r32_0(r8_0[10], "GET", r2_20, {
    key = r0_20,
    password = r1_20,
  })
end
local function r49_0(r0_21, r1_21, r2_21)
  -- line: [475, 480] id: 21
  assert(r0_21, debug.traceback())
  r32_0(r8_0[11], "GET", r2_21, {
    id = r0_21,
    password = r1_21,
  })
end
local function r50_0(r0_22, r1_22, r2_22)
  -- line: [482, 487] id: 22
  assert(r0_22, debug.traceback())
  assert(r1_22, debug.traceback())
  r32_0(r8_0[12], "GET", r2_22, {
    key = r0_22,
    password = r1_22,
  })
end
local function r51_0(r0_23, r1_23)
  -- line: [489, 493] id: 23
  assert(r0_23, debug.traceback())
  r32_0(r8_0[13], "GET", r1_23, {
    key = r0_23,
  })
end
local function r52_0(r0_24, r1_24)
  -- line: [495, 499] id: 24
  assert(r0_24, debug.traceback())
  r32_0(r8_0[14], "GET", r1_24, {
    key = r0_24,
  })
end
local function r53_0(r0_25, r1_25)
  -- line: [501, 505] id: 25
  assert(r0_25, debug.traceback())
  r32_0(r8_0[20], "GET", r1_25, {
    key = r0_25,
  })
end
local function r54_0(r0_26, r1_26)
  -- line: [517, 521] id: 26
  r32_0(r8_0[15], "GET", r1_26, {
    key = r0_26,
  })
end
local function r55_0(r0_27, r1_27)
  -- line: [530, 534] id: 27
  assert(r0_27, debug.traceback())
  r32_0(r8_0[16], "GET", r1_27, {
    key = r0_27,
  })
end
local r56_0 = "feeldeadbeef"
local function r57_0(r0_28, r1_28)
  -- line: [537, 547] id: 28
  if r1_28 == nil then
    r1_28 = r1_0.sha512
  end
  local r2_28 = system.getInfo("deviceID")
  if _G.IsDebug then
    r2_28 = r2_28 .. r56_0 .. r0_28
  elseif not _G.IsSimulator then
  end
  return r1_0.digest(r1_28, r2_28)
end
local function r58_0(r0_29, r1_29, r2_29)
  -- line: [557, 567] id: 29
  assert(r0_29, debug.traceback())
  assert(r1_29, debug.traceback())
  r32_0(r8_0[17], "GET", r2_29, {
    key = r0_29,
    did = r57_0("DefenseWitchesInvite"),
    code = r1_29,
  })
end
local function r59_0(r0_30, r1_30, r2_30, r3_30, r4_30)
  -- line: [570, 598] id: 30
  assert(r4_30, debug.traceback())
  if r0_30 == nil then
    return r4_30({
      isError = true,
    })
  end
  assert(r0_30, debug.traceback())
  assert(r1_30 and type(r1_30) == "table", debug.traceback())
  local r5_30 = assert
  local r6_30 = r2_30
  if r6_30 then
    if r2_30 >= 1 then
      r6_30 = r2_30 <= 10
    else
      r6_30 = r2_30 >= 1
    end
  end
  r5_30(r6_30, debug.traceback())
  r5_30 = assert
  r6_30 = r3_30
  if r6_30 then
    if r3_30 >= 1 then
      r6_30 = r3_30 <= 10
    else
      r6_30 = r3_30 >= 1
    end
  end
  r5_30(r6_30, debug.traceback())
  r5_30 = 36 + (r2_30 - 1) * 50 + (r3_30 - 1) * 5
  r6_30 = {}
  for r10_30, r11_30 in pairs(r1_30) do
    if r11_30 then
      table.insert(r6_30, r5_30)
    end
    r5_30 = r5_30 + 1
  end
  r32_0(r8_0[19], "GET", r4_30, {
    key = r0_30,
    achieves = table.concat(r6_30, ","),
  })
end
local r60_0 = {
  26,
  536,
  546,
  556,
  566
}
function r29_0()
  -- line: [944, 966] id: 49
  if not _G.UserToken then
    return nil
  end
  r32_0(r18_0, "GET", function(r0_50)
    -- line: [948, 957] id: 50
    local r1_50 = r2_0.decode(r0_50.response)
    if r1_50.status == 0 then
      native.showAlert("DefenseWitches", db.GetMessage(35), {
        "OK"
      })
      r1_50.result = 0
    end
  end, {
    uid = _G.UserInquiryID,
    verify = r33_0(),
  })
end
return {
  JsonDecode = r34_0,
  NetworkError = r35_0,
  ServerError = r36_0,
  GetUserID = r37_0,
  OTPIssue = r38_0,
  OTP2Key = r39_0,
  SaveData = r40_0,
  SaveData2 = r41_0,
  LoadData = r42_0,
  GetTimeStamp = r43_0,
  BuyCoin = r44_0,
  GetCoin = r46_0,
  UseCoin = r45_0,
  CreatePassword = r48_0,
  RestoreToken = r49_0,
  DeletePassword = r50_0,
  GetItemList = r51_0,
  MakePassword = r52_0,
  GetPassword = r53_0,
  InvitePublish = r54_0,
  InvitePersons = r55_0,
  InviteReceive = r58_0,
  UnlockLocalAchievement = r59_0,
  UnlockWorldAchievement = function(r0_31, r1_31, r2_31, r3_31)
    -- line: [603, 632] id: 31
    assert(r3_31, debug.traceback())
    if r0_31 == nil then
      return r3_31({
        isError = true,
      })
    end
    assert(r0_31, debug.traceback())
    assert(r1_31 and type(r1_31) == "table", debug.traceback())
    local r4_31 = assert
    local r5_31 = r2_31
    if r5_31 then
      if r2_31 >= 1 then
        r5_31 = r2_31 <= 10
      else
        r5_31 = r2_31 >= 1
      end
    end
    r4_31(r5_31, debug.traceback())
    r4_31 = nil
    r5_31 = {}
    for r9_31, r10_31 in pairs(r1_31) do
      r4_31 = r60_0[r9_31] + r2_31 - 1
      if r10_31 >= 100 then
        table.insert(r5_31, r4_31)
      end
    end
    if #r5_31 > 0 then
      r32_0(r8_0[19], "GET", r3_31, {
        key = r0_31,
        achieves = table.concat(r5_31, ","),
      })
    else
      r3_31({
        isError = false,
        r2_0.encode({
          status = 7,
          achieves = {},
        })
      })
    end
  end,
  UnlockNormalAchievement = function(r0_32, r1_32, r2_32)
    -- line: [635, 647] id: 32
    assert(r2_32, debug.traceback())
    if r0_32 == nil then
      return r2_32({
        isError = true,
      })
    end
    assert(r0_32 and r1_32, debug.traceback())
    r32_0(r8_0[19], "GET", r2_32, {
      key = r0_32,
      achieves = r1_32,
    })
  end,
  LoadAchievementList = function(r0_33, r1_33)
    -- line: [649, 656] id: 33
    assert(r0_33, debug.traceback())
    assert(r1_33, debug.traceback())
    r32_0(r8_0[18], "GET", r1_33, {
      key = r0_33,
    })
  end,
  Init = function(r0_34)
    -- line: [659, 688] id: 34
    local r1_34 = db.GetUserID()
    if r1_34 then
      _G.UserID = r1_34.uid
      _G.UserInquiryID = r1_34.id
      _G.UserToken = r1_34.key
      if r0_34 then
        r0_34[1](r0_34[2])
      end
    else
      _G.UserID = db.InitUserID()
      _G.UserInquiryID = nil
      _G.UserToken = nil
      return false
    end
    return true
  end,
  Mailto = function(r0_35, r1_35)
    -- line: [690, 698] id: 35
    local r2_35 = {}
    for r6_35, r7_35 in pairs(r1_35) do
      table.insert(r2_35, string.format("%s=%s", r6_35, r31_0(r7_35)))
    end
    system.openURL("mailto:" .. r0_35 .. "?" .. table.concat(r2_35, "&"))
  end,
  GetInquiryID = function(r0_36, r1_36)
    -- line: [700, 720] id: 36
    if _G.UserInquiryID then
      if r0_36 then
        r0_36(r1_36)
      end
      return 
    end
    local r2_36 = db.GetUserID(_G.UserID)
    if r2_36.id then
      _G.UserID = r2_36.uid
      _G.UserInquiryID = r2_36.id
      _G.UserToken = r2_36.key
    elseif not r7_0 then
      r7_0 = true
      r6_0 = {
        r0_36,
        r1_36
      }
      r37_0(r47_0)
    end
  end,
  MakeUserID = function()
    -- line: [722, 729] id: 37
    if not r7_0 then
      r7_0 = true
      r37_0(r47_0)
    end
  end,
  CheckError = function(r0_38)
    -- line: [732, 746] id: 38
    if not r0_38.isError then
      return false
    end
    if r0_38.response then
      local r1_38 = r2_0.decode(r0_38.response)
      if r1_38 and r1_38.status then
        return r1_38.status ~= 0
      else
        return r1_38.status ~= 0
      end
    else
      return true
    end
  end,
  GetDID = r57_0,
  GetNotifyLoginBonus = function(r0_40, r1_40)
    -- line: [767, 801] id: 40
    if not _G.UserToken then
      return nil
    end
    if r30_0(r26_0) == false then
      DebugPrint("サーバアクセスタイム未満")
      return nil
    end
    local r3_40 = {
      uid = r0_40,
      verify = r33_0(),
    }
    local r4_40 = {
      uid = _G.UserID,
      acs_id = r26_0,
    }
    r32_0(r10_0, "GET", function(r0_41)
      -- line: [786, 796] id: 41
      local r1_41 = r27_0
      if r0_41.isError then
        r1_41 = r28_0
      end
      r4_40.result = r1_41
      db.SetServerAccessTime(r4_40)
      r1_40(r0_41)
    end, r3_40)
    db.SetServerAccessTime(r4_40)
  end,
  GetNotifyLoginBonusDbg = function(r0_39, r1_39)
    -- line: [751, 762] id: 39
    if not _G.UserToken then
      return nil
    end
    if not _G.IsDebug then
      return 
    end
    r32_0(r10_0, "GET", r1_39, {
      uid = r0_39,
      verify = r33_0(),
      dbg = 1,
    })
  end,
  GetKeepItemList = function(r0_42, r1_42)
    -- line: [806, 816] id: 42
    if not _G.UserToken then
      return nil
    end
    r32_0(r11_0, "GET", r1_42, {
      uid = r0_42,
      verify = r33_0(),
    })
  end,
  GetKeepItem = function(r0_43, r1_43, r2_43, r3_43)
    -- line: [821, 844] id: 43
    if not _G.UserToken then
      return nil
    end
    local r4_43 = {}
    local r5_43 = {
      r1_43
    }
    if r2_43 and 0 < #r2_43 then
      table.insert(r5_43, table.concat(r2_43, ""))
      r4_43.crystal = table.concat(r2_43, ",")
    end
    local r6_43 = r33_0(r5_43)
    r4_43.uid = r0_43
    r4_43.post_id = r1_43
    r4_43.verify = r6_43
    r32_0(r12_0, "GET", r3_43, r4_43)
  end,
  GetNotifyKeepItem = function(r0_44, r1_44)
    -- line: [849, 859] id: 44
    if not _G.UserToken then
      return nil
    end
    r32_0(r13_0, "GET", r1_44, {
      uid = r0_44,
      verify = r33_0(),
    })
  end,
  GetLoginBonusList = function(r0_45, r1_45)
    -- line: [864, 874] id: 45
    if not _G.UserToken then
      return nil
    end
    r32_0(r14_0, "GET", r1_45, {
      uid = r0_45,
      verify = r33_0(),
    })
  end,
  GetStockIemCount = function(r0_46, r1_46)
    -- line: [879, 889] id: 46
    if not _G.UserToken then
      return nil
    end
    r32_0(r15_0, "GET", r1_46, {
      uid = r0_46,
      verify = r33_0(),
    })
  end,
  UseStockItem = function(r0_47, r1_47, r2_47)
    -- line: [894, 905] id: 47
    if not _G.UserToken then
      return nil
    end
    r32_0(r16_0, "GET", r2_47, {
      uid = r0_47,
      item_id = r1_47,
      verify = r33_0({
        r1_47
      }),
    })
  end,
  PostTweet = function(r0_48, r1_48, r2_48)
    -- line: [918, 939] id: 48
    assert(r0_48, debug.traceback())
    assert(r1_48, debug.traceback())
    assert(r2_48, debug.traceback())
    local r4_48 = ({
      facebook = 1,
      twitter = 2,
    })[r1_48]
    if r4_48 == false then
      return 
    end
    local r5_48 = nil
    r32_0(r8_0[22], "GET", r2_48, {
      uid = r0_48,
      category = r4_48,
    })
  end,
  InitEvoData = r29_0,
  SaveExp = function(r0_51, r1_51)
    -- line: [971, 982] id: 51
    if not _G.UserToken then
      return nil
    end
    r32_0(r19_0, "GET", r1_51, {
      uid = _G.UserInquiryID,
      exp = r0_51,
      verify = r33_0(r0_51),
    })
  end,
  LoadExp = function(r0_52)
    -- line: [987, 997] id: 52
    if not _G.UserToken then
      return nil
    end
    r32_0(r20_0, "GET", r0_52, {
      uid = _G.UserInquiryID,
      verify = r33_0(),
    })
  end,
  SaveUseOrb = function(r0_53, r1_53)
    -- line: [1003, 1014] id: 53
    if not _G.UserToken then
      return nil
    end
    r32_0(r21_0, "GET", r1_53, {
      uid = _G.UserInquiryID,
      used = r0_53.usedOrb,
      verify = r33_0({
        r0_53.sid,
        r0_53.usedOrb
      }),
    })
  end,
  SaveRecoveryOrb = function(r0_54, r1_54)
    -- line: [1019, 1040] id: 54
    if not _G.UserToken or r0_54.type == nil then
      return nil
    end
    local r2_54 = r0_54.type
    local r3_54 = 0
    if r0_54.addMaxOrb ~= nil then
      r3_54 = r0_54.addMaxOrb
    end
    r32_0(r22_0, "GET", r1_54, {
      uid = _G.UserInquiryID,
      type = r0_54.type,
      add_max_orb = r3_54,
      verify = r33_0({
        r0_54.type,
        r0_54.addMaxOrb
      }),
    })
  end,
  LoadOrbInfo = function(r0_55)
    -- line: [1045, 1055] id: 55
    if not _G.UserToken then
      return nil
    end
    r32_0(r23_0, "GET", r0_55, {
      uid = _G.UserInquiryID,
      verify = r33_0(),
    })
  end,
  GetServerTime = function(r0_56)
    -- line: [1060, 1076] id: 56
    if not _G.UserToken then
      if r0_56 ~= nil then
        r0_56(nil)
      end
      return nil
    end
    r32_0(r24_0, "GET", r0_56, {
      uid = _G.UserInquiryID,
      verify = r33_0(),
    })
  end,
  UnlockExAchievement = function(r0_57, r1_57, r2_57, r3_57, r4_57)
    -- line: [1079, 1107] id: 57
    assert(r4_57, debug.traceback())
    if r0_57 == nil then
      return r4_57({
        isError = true,
      })
    end
    assert(r0_57, debug.traceback())
    assert(r1_57 and type(r1_57) == "table", debug.traceback())
    local r5_57 = assert
    local r6_57 = r2_57
    if r6_57 then
      if r2_57 >= 1 then
        r6_57 = r2_57 <= 10
      else
        r6_57 = r2_57 >= 1
      end
    end
    r5_57(r6_57, debug.traceback())
    r5_57 = assert
    r6_57 = r3_57
    if r6_57 then
      if r3_57 >= 1 then
        r6_57 = r3_57 <= 10
      else
        r6_57 = r3_57 >= 1	-- block#14 is visited secondly
      end
    end
    r5_57(r6_57, debug.traceback())
    r5_57 = 10000 + r2_57 * 100 + r3_57 * 10 + 1
    r6_57 = {}
    for r10_57, r11_57 in pairs(r1_57) do
      if r11_57 then
        table.insert(r6_57, r5_57)
      end
      r5_57 = r5_57 + 1
    end
    r32_0(r8_0[19], "GET", r4_57, {
      key = r0_57,
      achieves = table.concat(r6_57, ","),
    })
  end,
  GetStatus = r4_0.GetStatus,
  GetMovieUrl = r5_0.GetMovieUrl,
}
