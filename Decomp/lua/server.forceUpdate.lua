-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = "forceUpdate.txt"
local r1_0 = "_tmpForceUpdate"
local r2_0 = _G.VERSION_FORCEUPDATE_URLSTR
local r3_0 = nil
local function r4_0(r0_1)
  -- line: [11, 24] id: 1
  r3_0 = false
  if r0_1.isError then
    DebugPrint("ForceUpdate:Network error - http GET failed")
  elseif r0_1.status == 200 then
    r3_0 = true
  else
    DebugPrint("ForceUpdate:HTTP GET NG. " .. r0_1.status)
  end
end
local function r6_0(r0_3)
  -- line: [34, 47] id: 3
  local r1_3 = nil	-- notice: implicit variable refs by block#[0]
  r3_0 = r1_3
  r1_3 = {
    timeout = 3,
  }
  network.download(r0_3, "GET", r4_0, r1_3, r1_0, system.TemporaryDirectory)
end
return {
  GetLastRes = function()
    -- line: [26, 32] id: 2
    if _G.UILanguage == "en" then
      return r3_0
    else
      return r3_0
    end
  end,
  HttpGet = r6_0,
  Init = function()
    -- line: [49, 52] id: 4
    r6_0(r2_0 .. _G.Version .. "/" .. r0_0)
  end,
}
