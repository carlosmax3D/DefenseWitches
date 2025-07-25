-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
module(..., package.seeall)
local r0_0 = "adcropsIcon.txt"
local r1_0 = "_tmp_adcrops_icon"
local r2_0 = _G.VERSION_MY_AD_URLSTR
local r3_0 = true
local function r4_0(r0_1)
  -- line: [13, 26] id: 1
  DebugPrint("ADS adcropsIcon:HttpGetListener")
  if r0_1.isError then
    DebugPrint("ADS adcropsIcon:Network error - http GET failed")
  elseif r0_1.status == 200 then
    DebugPrint("ADS adcropsIcon:HTTP GET OK. " .. r0_1.status)
    r3_0 = true
  else
    DebugPrint("ADS adcropsIcon:HTTP GET NG. " .. r0_1.status)
  end
end
function GetLastRes()
  -- line: [28, 35] id: 2
  if _G.UILanguage == "en" then
    return r3_0
  else
    return r3_0
  end
end
function HttpGet(r0_3)
  -- line: [37, 52] id: 3
  DebugPrint("ADS:http GET url=" .. r0_3)
  r3_0 = false
  network.download(r0_3, "GET", r4_0, {
    timeout = 3,
  }, r1_0, system.TemporaryDirectory)
end
function InitADS()
  -- line: [54, 57] id: 4
  HttpGet(r2_0 .. _G.Version .. "/" .. r0_0)
end
