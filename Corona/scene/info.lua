-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = _G.VersionInfo
local r1_0 = _G.Copyright
local r2_0 = false
local r3_0 = nil
local r4_0 = nil
local r5_0 = false
local r6_0 = nil
local r7_0 = "app-info-and_"
local r8_0 = "info.html"
local r9_0 = "http://dw.n-gate.jp/"
local function r10_0(r0_1)
  -- line: [40, 48] id: 1
  require("tool.handle_scheme").ParseScheme(r0_1.url)
  return true
end
local function r11_0(r0_2, r1_2)
  -- line: [50, 64] id: 2
  if r2_0 then
    native.cancelWebPopup()
    r2_0 = false
  end
  local r2_2 = {
    baseUrl = r1_2,
    urlRequest = r10_0,
  }
  offsetY = 90 / display.contentScaleY
  defendseWebview.webview(0 - _G.WidthDiff / display.contentScaleX, offsetY, display.contentWidth / display.contentScaleX, display.contentHeight / display.contentScaleY, r1_2 .. r0_2, true)
  r2_0 = true
end
local function r12_0(r0_3)
  -- line: [66, 73] id: 3
  r6_0()
  if r4_0 == nil then
    util.ChangeScene({
      scene = "title",
      efx = "fade",
    })
  else
    util.ChangeScene({
      scene = r4_0,
      efx = "fade",
    })
  end
end
local function r13_0(r0_4)
  -- line: [75, 75] id: 4
  return "data/info/" .. r0_4 .. ".png"
end
local function r14_0(r0_5)
  -- line: [76, 76] id: 5
  return "data/option/" .. r0_5 .. ".png"
end
local function r15_0(r0_6)
  -- line: [78, 88] id: 6
  local r1_6 = display.newGroup()
  util.LoadParts(r1_6, r13_0("info_header"), 0, 0)
  util.LoadBtn({
    rtImg = r1_6,
    fname = r14_0("close"),
    x = 872,
    y = 0,
    func = r12_0,
  })
  r0_6:insert(r1_6)
  return r1_6
end
local function r16_0(r0_7, r1_7, r2_7, r3_7, r4_7, r5_7)
  -- line: [90, 103] id: 7
  local r6_7 = nil	-- notice: implicit variable refs by block#[3]
  if r5_7 then
    r6_7 = display.newText(r0_7, r2_7, 0, 0, native.systemFontBold, r1_7)
  else
    r6_7 = display.newText(r0_7, r2_7, 0, 0, native.systemFont, r1_7)
  end
  r6_7:setFillColor(255, 255, 255)
  r6_7:setReferencePoint(display.TopLeftReferencePoint)
  r6_7.x = r3_7
  r6_7.y = r4_7
  return r6_7
end
local function r17_0(r0_8)
  -- line: [105, 116] id: 8
  local r1_8 = display.newGroup()
  util.LoadParts(r1_8, r13_0("info_footer"), 0, 528)
  r16_0(r1_8, 24, r0_0, 120, 544, true)
  r16_0(r1_8, 16, r1_0, 120, 584, false)
  r0_8:insert(r1_8)
  return r1_8
end
function r6_0()
  -- line: [152, 163] id: 10
  if r3_0 then
    if r3_0.ev then
      events.Delete(r3_0.ev)
    end
    r3_0 = nil
  end
  defendseWebview.closeWebView()
  events.DeleteNamespace("information")
end
return {
  new = function(r0_9)
    -- line: [119, 150] id: 9
    if _G.LoadingImage ~= nil then
      display.remove(_G.LoadingImage)
      _G.LoadingImage = nil
    end
    events.SetNamespace("information")
    local r1_9 = display.newGroup()
    util.MakeFrame(r1_9)
    if r0_9 ~= nil then
      r4_0 = r0_9.back
    end
    r15_0(r1_9)
    r2_0 = false
    r11_0(r7_0 .. _G.UILanguage .. "/", r9_0)
    return r1_9
  end,
  Close = r6_0,
}
