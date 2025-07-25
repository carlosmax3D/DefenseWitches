-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = _G.STATS_SERVER
local function r1_0(r0_1)
  -- line: [10, 16] id: 1
  if r0_1 == nil then
    return "null"
  end
  r0_1 = string.gsub(r0_1, "([^0-9a-zA-Z])", function(r0_2)
    -- line: [12, 14] id: 2
    return string.format("%%%02X", string.byte(r0_2))
  end)
  return r0_1
end
local function r2_0(r0_3)
  -- line: [18, 29] id: 3
  if _G.IsSimulator then
    if r0_3.isError then
      DebugPrint("statslog post error")
    else
      DebugPrint("statslog post ok")
    end
  end
end
local function r3_0(r0_4, r1_4, r2_4)
  -- line: [31, 36] id: 4
  if r0_4 ~= "" then
    r0_4 = r0_4 .. ","
  end
  return r0_4 .. "\"" .. r1_4 .. "\"" .. ":" .. "\"" .. r2_4 .. "\""
end
local function r4_0(r0_5, r1_5, r2_5)
  -- line: [38, 69] id: 5
  local r3_5 = {}
  table.insert(r3_5, string.format("%s=%s", "uid", r1_0(r0_5)))
  table.insert(r3_5, string.format("%s=%s", "action", r1_0(r1_5)))
  local r4_5 = ""
  if _G.SystemLanguage then
    r4_5 = r3_0(r4_5, "lang", _G.SystemLanguage)
  end
  if _G.StageSelect and 0 < _G.StageSelect and _G.MapSelect and 0 < _G.MapSelect then
    r4_5 = r3_0(r4_5, "now_stage", _G.MapSelect * 1000 + _G.StageSelect)
  end
  local r5_5 = db.GetUnlockMaxStage()
  if r5_5 > 0 then
    r4_5 = r3_0(r4_5, "max_stage", r5_5)
  end
  r4_5 = r3_0(r4_5, "device_version", require("common.device_info").GetDeveiceName(system.getInfo("architectureInfo")))
  for r10_5, r11_5 in pairs(r2_5) do
    r4_5 = r3_0(r4_5, r10_5, r11_5)
  end
  table.insert(r3_5, string.format("%s=%s", "data", r1_0("{" .. r4_5 .. "}")))
  network.request(r0_0 .. table.concat(r3_5, "&"), "GET", r2_0)
end
return {
  LogSend = function(r0_6, r1_6)
    -- line: [71, 81] id: 6
    if r0_6 == nil then
      return 
    end
    if r1_6 == nil then
      return 
    end
    local r2_6 = nil
    if _G.UserInquiryID then
      r2_6 = _G.UserInquiryID
    else
      return 
    end
    r4_0(r2_6, r0_6, r1_6)
  end,
}
