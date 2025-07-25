-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("crypto")
local r1_0 = require("mime")
local r2_0 = require("socket.http")
local r3_0 = require("ltn12")
local function r4_0(r0_1)
  -- line: [26, 30] id: 1
  return r0_1:gsub("[^-%._~a-zA-Z0-9]", function(r0_2)
    -- line: [27, 29] id: 2
    return string.format("%%%02x", r0_2:byte()):upper()
  end)
end
local function r5_0(r0_3, r1_3, r2_3)
  -- line: [32, 35] id: 3
  if not r2_3 then
    r2_3 = false
  end
  return r0_0.hmac(r0_0.sha1, r0_3, r1_3, r2_3)
end
local function r6_0()
  -- line: [37, 42] id: 4
  return string.sub(r0_0.hmac(r0_0.sha1, tostring(math.random()) .. "feeldead" .. tostring(os.time()), "deadbeef"), -32)
end
local function r7_0()
  -- line: [44, 46] id: 5
  return tostring(os.time())
end
local function r8_0(r0_6, r1_6)
  -- line: [48, 62] id: 6
  if r1_6 then
    network.request(r0_6, "GET", r1_6, nil)
  else
    local r2_6 = nil
    local r3_6 = nil
    local r4_6 = nil
    local r5_6 = {}
    r2_6, r3_6, r4_6 = r2_0.request({
      url = r0_6,
      sink = r3_0.sink.table(r5_6),
    })
    return table.concat(r5_6, ""), r2_6, r3_6, r4_6
  end
end
local function r9_0(r0_7, r1_7, r2_7)
  -- line: [64, 91] id: 7
  if r2_7 then
    network.request(r0_7, "POST", r2_7, {
      header = {
        ["Content-Type"] = "application/x-www-form-urlencoded",
        ["Content-Length"] = string.len(r1_7),
      },
      body = r1_7,
    })
  else
    local r3_7 = nil
    local r4_7 = nil
    local r5_7 = nil
    local r6_7 = {}
    r3_7, r4_7, r5_7 = r2_0.request({
      url = r0_7,
      method = "POST",
      headers = {
        ["Content-Type"] = "application/x-www-form-urlencoded",
        ["Content-Length"] = string.len(r1_7),
      },
      source = r3_0.source.string(r1_7),
      sink = r3_0.sink.table(r6_7),
    })
    return table.concat(r6_7, ""), r3_7, r4_7, r5_7
  end
end
local function r10_0(r0_8, r1_8, r2_8, r3_8)
  -- line: [93, 144] id: 8
  local r4_8 = assert
  local r5_8 = nil	-- notice: implicit variable refs by block#[4]
  if r1_8 ~= "GET" then
    r5_8 = r1_8 == "POST"
  else
    r5_8 = r1_8 == "GET"
  end
  r4_8(r5_8, "method must be either POST or GET")
  assert(r3_8, "consumer_secret required")
  assert(r0_8, "url required")
  r4_8 = r2_8.oauth_token_secret or ""
  r2_8.oauth_token_secret = nil
  r5_8 = {}
  for r9_8, r10_8 in pairs(r2_8) do
    table.insert(r5_8, {
      key = r4_0(r9_8),
      val = r4_0(r10_8),
    })
  end
  table.sort(r5_8, function(r0_9, r1_9)
    -- line: [115, 123] id: 9
    if r0_9.key < r1_9.key then
      return true
    elseif r1_9.key < r0_9.key then
      return false
    else
      return r0_9.val < r1_9.val
    end
  end)
  local r6_8 = {}
  for r10_8, r11_8 in pairs(r5_8) do
    table.insert(r6_8, r11_8.key .. "=" .. r11_8.val)
  end
  local r7_8 = table.concat(r6_8, "&")
  local r12_8 = r7_8 .. "&oauth_signature=" .. r4_0(r1_0.b64(r5_0(r1_8 .. "&" .. r4_0(r0_8) .. "&" .. r4_0(r7_8), r4_0(r3_8) .. "&" .. r4_0(r4_8), true)))
  if r1_8 == "GET" then
    return r0_8 .. "?" .. r12_8
  else
    return r12_8
  end
end
local function r11_0(r0_10, r1_10, r2_10)
  -- line: [146, 176] id: 10
  assert(r0_10.authorize_url, "authorize_url option must be set")
  assert(r0_10.request_token_url, "request_token_url option must be set")
  assert(r0_10.consumer_key, "consumer_key option must be set")
  assert(r0_10.consumer_secret, "consumer_secret option must be set")
  assert(r0_10.token_ready_url, "token_ready_url option must be set")
  local r3_10 = assert
  local r4_10 = nil	-- notice: implicit variable refs by block#[4]
  if r1_10 ~= "GET" then
    r4_10 = r1_10 == "POST"
  else
    r4_10 = r1_10 == "GET"
  end
  r3_10(r4_10, "method must be either POST or GET")
  if r2_10 then
    r3_10 = assert
    r4_10 = type(r2_10) == "function"
    r3_10(r4_10, "callback must be function")
  end
  r3_10 = r0_10.authorize_url
  r4_10 = {
    oauth_consumer_key = r0_10.consumer_key,
    oauth_timestamp = r7_0(),
    oauth_version = "1.0",
    oauth_nonce = r6_0(),
    oauth_callback = r0_10.token_ready_url,
    oauth_signature_method = "HMAC-SHA1",
  }
  local r5_10 = r10_0(r0_10.request_token_url, r1_10, r4_10, r0_10.consumer_secret)
  if r1_10 == "GET" then
    return r8_0(r5_10, r2_10)
  else
    return r9_0(r0_10.request_token_url, r5_10, r2_10)
  end
end
local function r12_0(r0_11, r1_11, r2_11)
  -- line: [179, 212] id: 11
  assert(r2_11.consumer_key, "consumer_key option must be set")
  local r3_11 = r2_11.consumer_key
  local r4_11 = r2_11.consumer_secret
  return {
    call = function(r0_12, r1_12, r2_12, r3_12)
      -- line: [184, 209] id: 12
      local r4_12 = {
        oauth_consumer_key = r3_11,
        oauth_timestamp = r7_0(),
        oauth_version = "1.0",
        oauth_nonce = r6_0(),
        oauth_token = r0_11,
        oauth_token_secret = r1_11,
        oauth_signature_method = "HMAC-SHA1",
      }
      for r8_12, r9_12 in pairs(r2_12) do
        r4_12[r8_12] = r9_12
      end
      if r1_12 == "POST" then
        r4_12 = r10_0(r0_12, "POST", r4_12, r4_11)
        return r9_0(r0_12, r4_12, r3_12)
      elseif r1_12 == "GET" then
        r0_12 = r10_0(r0_12, "GET", r4_12, r4_11)
        return r8_0(r0_12, r3_12)
      end
    end,
  }
end
local function r13_0(r0_13, r1_13)
  -- line: [215, 239] id: 13
  local r2_13 = r1_13:match("oauth_token=([^&]+)")
  local r3_13 = r1_13:match("oauth_token_secret=([^&]+)")
  return {
    getAuthorizeUrl = function()
      -- line: [220, 225] id: 14
      if r2_13 and r3_13 then
        return r0_13.authorize_url .. "?" .. "oauth_token=" .. r2_13 .. "&" .. "oauth_callback=" .. r0_13.token_ready_url
      end
      return nil
    end,
    getToken = function()
      -- line: [226, 231] id: 15
      if r2_13 then
        return r2_13
      end
      return nil
    end,
    getTokenSecret = function()
      -- line: [232, 237] id: 16
      if r3_13 then
        return r3_13
      end
      return nil
    end,
  }
end
local function r14_0(r0_17, r1_17)
  -- line: [241, 281] id: 17
  local r2_17 = r1_17:match("oauth_token=([^&]+)")
  local r3_17 = r1_17:match("oauth_token_secret=([^&]+)")
  local r4_17 = r0_17.consumer_key
  return {
    getAccess = function()
      -- line: [247, 255] id: 18
      if r2_17 and r3_17 then
        return r12_0(r2_17, r3_17, {
          consumer_key = r4_17,
        })
      end
      return nil
    end,
    getToken = function()
      -- line: [256, 261] id: 19
      if r2_17 then
        return r2_17
      end
      return nil
    end,
    getTokenSecret = function()
      -- line: [262, 267] id: 20
      if r3_17 then
        return r3_17
      end
      return nil
    end,
    getUserId = function()
      -- line: [268, 273] id: 21
      if r1_17 then
        return r1_17:match("user_id=([^&]+)")
      end
      return nil
    end,
    getScreenName = function()
      -- line: [274, 279] id: 22
      if r1_17 then
        return r1_17:match("screen_name=([^&]+)")
      end
      return nil
    end,
  }
end
local function r15_0(r0_23, r1_23, r2_23, r3_23, r4_23, r5_23)
  -- line: [283, 315] id: 23
  assert(r3_23.consumer_key, "consumer_key option must be set")
  assert(r3_23.consumer_secret, "consumer_secret option must be set")
  assert(r0_23, "oauth_token option must be set")
  assert(r2_23, "oauth_token_secret must be set")
  assert(r3_23.access_token_url, "access_token_url option must be set")
  local r6_23 = assert
  local r7_23 = nil	-- notice: implicit variable refs by block#[4]
  if r4_23 ~= "GET" then
    r7_23 = r4_23 == "POST"
  else
    r7_23 = r4_23 == "GET"
  end
  r6_23(r7_23, "method must be either POST or GET")
  if r5_23 then
    r6_23 = assert
    r7_23 = type(r5_23) == "function"
    r6_23(r7_23, "callback must be function")
  end
  r7_23 = {
    oauth_consumer_key = r3_23.consumer_key,
    oauth_timestamp = r7_0(),
    oauth_version = "1.0",
    oauth_nonce = r6_0(),
    oauth_token = r3_23.oauth_token,
    oauth_token_secret = r3_23.oauth_token_secret,
    oauth_verifier = r1_23,
    oauth_signature_method = "HMAC-SHA1",
  }
  local r8_23 = r10_0(r3_23.access_token_url, r4_23, r7_23, r3_23.consumer_secret)
  if r4_23 == "GET" then
    return r8_0(result, r5_23)
  else
    return r9_0(r3_23.request_token_url, result, r5_23)
  end
end
local function r16_0(r0_24, r1_24, r2_24, r3_24, r4_24)
  -- line: [317, 346] id: 24
  assert(r3_24.consumer_key, "consumer_key option must be set")
  assert(r3_24.consumer_secret, "consumer_secret option must be set")
  assert(r3_24.access_token_url, "access_token_url option must be set")
  local r5_24 = assert
  local r6_24 = nil	-- notice: implicit variable refs by block#[4]
  if r2_24 ~= "GET" then
    r6_24 = r2_24 == "POST"
  else
    r6_24 = r2_24 == "GET"
  end
  r5_24(r6_24, "method must be either POST or GET")
  assert(r0_24, "username must be set")
  assert(r1_24, "password must be set")
  if r4_24 then
    r5_24 = assert
    r6_24 = type(r4_24) == "function"
    r5_24(r6_24, "callback must be function")
  end
  r6_24 = {
    oauth_consumer_key = r3_24.consumer_key,
    oauth_timestamp = r7_0(),
    oauth_version = "1.0",
    oauth_nonce = r6_0(),
    oauth_signature_method = "HMAC-SHA1",
    x_auth_username = r0_24,
    x_auth_password = r1_24,
    x_auth_mode = "client_auth",
  }
  r9_0(r3_24.access_token_url, r10_0(r3_24.access_token_url, r2_24, r6_24, r3_24.consumer_secret), r4_24)
end
local function r17_0(r0_25, r1_25, r2_25, r3_25)
  -- line: [348, 376] id: 25
  assert(r2_25.consumer_key, "consumer_key option must be set")
  assert(r2_25.consumer_secret, "consumer_secret option must be set")
  assert(r2_25.oauth_token, "oauth_token option must be set")
  assert(r2_25.oauth_token_secret, "oauth_token_secret must be set")
  assert(r2_25.access_token_url, "access_token_url option must be set")
  local r4_25 = assert
  local r5_25 = nil	-- notice: implicit variable refs by block#[4]
  if r1_25 ~= "GET" then
    r5_25 = r1_25 == "POST"
  else
    r5_25 = r1_25 == "GET"
  end
  r4_25(r5_25, "method must be either POST or GET")
  assert(r0_25, "pin must be given")
  if r3_25 then
    r4_25 = assert
    r5_25 = type(r3_25) == "function"
    r4_25(r5_25, "callback must be function")
  end
  r5_25 = {
    oauth_consumer_key = r2_25.consumer_key,
    oauth_timestamp = r7_0(),
    oauth_version = "1.0",
    oauth_nonce = r6_0(),
    oauth_token = r2_25.oauth_token,
    oauth_token_secret = r2_25.oauth_token_secret,
    oauth_verifier = tostring(r0_25),
    oauth_signature_method = "HMAC-SHA1",
  }
  r9_0(r2_25.access_token_url, r10_0(r2_25.access_token_url, r1_25, r5_25, r2_25.consumer_secret), r3_25)
end
return {
  newConsumer = function(r0_26)
    -- line: [379, 457] id: 26
    assert(r0_26.request_token_url, "request_token_url option must be set")
    assert(r0_26.authorize_url, "authorize_url option must be set")
    assert(r0_26.access_token_url, "access_token_url option must be set")
    assert(r0_26.consumer_key, "consumer_key option must be set")
    assert(r0_26.consumer_secret, "consumer_secret option must be set")
    assert(r0_26.token_ready_url, "token_ready_url option must be set")
    local r1_26 = r0_26.consumer_key
    local r2_26 = r0_26.consumer_secret
    local r3_26 = r0_26.authorize_url
    local r4_26 = r0_26.request_token_url
    local r5_26 = r0_26.access_token_url
    local r6_26 = r0_26.token_ready_url
    return {
      getRequestToken = function(r0_27, r1_27)
        -- line: [394, 403] id: 27
        return r11_0({
          consumer_key = r1_26,
          consumer_secret = r2_26,
          request_token_url = r4_26,
          authorize_url = r3_26,
          token_ready_url = r6_26,
        }, r0_27, r1_27)
      end,
      getAccessToken = function(r0_28, r1_28, r2_28, r3_28, r4_28)
        -- line: [405, 412] id: 28
        return r15_0(r0_28, r1_28, r2_28, {
          consumer_key = r1_26,
          consumer_secret = r2_26,
          access_token_url = r5_26,
        }, r3_28, r4_28)
      end,
      getAccessTokenByPin = function(r0_31, r1_31, r2_31, r3_31)
        -- line: [422, 431] id: 31
        return r17_0(r0_31, r1_31, {
          consumer_key = r2_31.consumer_key,
          consumer_secret = r2_31.consumer_secret,
          access_token_url = r2_31.access_token_url,
          oauth_token = r2_31.oauth_token,
          oauth_token_secret = r2_31.oauth_token_secret,
        }, r3_31)
      end,
      getAccessTokenUsingXAuth = function(r0_32, r1_32, r2_32, r3_32)
        -- line: [433, 440] id: 32
        return r16_0(r0_32, r1_32, r2_32, {
          consumer_key = r1_26,
          consumer_secret = r2_26,
          access_token_url = r5_26,
        }, r3_32)
      end,
      getAccess = function(r0_33, r1_33)
        -- line: [442, 446] id: 33
        return r12_0(r0_33, r1_33, {
          consumer_key = r1_26,
          consumer_secret = r2_26,
        })
      end,
      parseRequestTokenResponse = function(r0_29)
        -- line: [414, 416] id: 29
        return r13_0(r0_26, r0_29)
      end,
      parseAccessTokenResponse = function(r0_30)
        -- line: [418, 420] id: 30
        return r14_0(r0_26, r0_30)
      end,
    }
  end,
}
