-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
module(..., package.seeall)
local r0_0 = "bingo.txt"
local r1_0 = "_tmp_bingo"
local r2_0 = _G.VERSION_MY_AD_URLSTR
local r3_0 = true
local function r4_0(r0_1)
  -- line: [9, 22] id: 1
  DebugPrint("Bingo:HttpGetListener")
  if r0_1.isError then
    DebugPrint("Bingo:Network error - http GET failed")
  elseif r0_1.status == 200 then
    DebugPrint("Bingo:HTTP GET OK. " .. r0_1.status)
    r3_0 = true
  else
    DebugPrint("Bingo:HTTP GET NG. " .. r0_1.status)
  end
end
function GetLastRes()
  -- line: [24, 31] id: 2
  if _G.UILanguage == "en" then
    return r3_0
  else
    return r3_0
  end
end
function HttpGet(r0_3)
  -- line: [33, 48] id: 3
  DebugPrint("Bingo:http GET url=" .. r0_3)
  r3_0 = false
  network.download(r0_3, "GET", r4_0, {
    timeout = 3,
  }, r1_0, system.TemporaryDirectory)
end
function Init()
  -- line: [50, 53] id: 4
  HttpGet(r2_0 .. _G.Version .. "/" .. r0_0)
end
