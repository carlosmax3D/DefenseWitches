-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = "ads.txt"
local r1_0 = "_tmpads"
local r2_0 = _G.VERSION_MY_AD_URLSTR
local r3_0 = true
local function r4_0(r0_1)
  -- line: [11, 24] id: 1
  DebugPrint("ADS:HttpGetListener")
  if r0_1.isError then
    DebugPrint("ADS:Network error - http GET failed")
  elseif r0_1.status == 200 then
    DebugPrint("ADS:HTTP GET OK. " .. r0_1.status)
    r3_0 = true
  else
    DebugPrint("ADS:HTTP GET NG. " .. r0_1.status)
  end
end
local function r6_0(r0_3)
  -- line: [34, 48] id: 3
  DebugPrint("ADS:http GET url=" .. r0_3)
  r3_0 = false
  network.download(r0_3, "GET", r4_0, {
    timeout = 3,
  }, r1_0, system.TemporaryDirectory)
end
return {
  InitADS = function()
    -- line: [50, 53] id: 4
    r6_0(r2_0 .. _G.Version .. "/" .. r0_0)
  end,
  GetLastRes = function()
    -- line: [26, 32] id: 2
    if _G.UILanguage == "en" then
      return r3_0
    else
      return r3_0
    end
  end,
}
