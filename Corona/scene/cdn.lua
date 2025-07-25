-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r47_0 = nil	-- notice: implicit variable refs by block#[0]
local r0_0 = 50
local r1_0 = 100
local r2_0 = r1_0 * 1024 * 1024
local r3_0 = 352
local r4_0 = nil
local r5_0 = nil
local r6_0 = 0
local r7_0 = nil
local r8_0 = nil
local r9_0 = nil
local r10_0 = nil
local r11_0 = nil
local r12_0 = nil
local r13_0 = nil
local r14_0 = nil
local r15_0 = nil
local r16_0 = nil
local r17_0 = nil
local r18_0 = nil
local r19_0 = nil
local r20_0 = require("anm.download_cha_anm_01")
local r21_0 = require("anm.download_cha_anm_02")
local r22_0 = require("anm.download_cha_anm_03")
local r23_0 = {
  Close = nil,
  DlgOpen = nil,
  Dlg2Open = nil,
  Dlg3Open = nil,
  DlgClose = nil,
}
local r24_0 = require("scene.data.cdn_data")
local function r25_0(r0_1)
  -- line: [39, 39] id: 1
  return "data/cdn/" .. r0_1 .. ".png"
end
local function r26_0(r0_2)
  -- line: [41, 41] id: 2
  return r25_0(r0_2 .. _G.UILanguage)
end
local function r27_0(r0_3)
  -- line: [43, 43] id: 3
  return "data/stage/" .. r0_3 .. ".png"
end
local function r28_0(r0_4)
  -- line: [45, 45] id: 4
  return r27_0(r0_4 .. _G.UILanguage)
end
local function r29_0(r0_5)
  -- line: [47, 47] id: 5
  return "data/dialog/" .. r0_5 .. ".png"
end
local function r30_0(r0_6)
  -- line: [49, 49] id: 6
  return r29_0(r0_6 .. _G.UILanguage)
end
local function r31_0(r0_7)
  -- line: [51, 51] id: 7
  return "data/option/" .. r0_7 .. ".png"
end
local function r32_0(r0_8)
  -- line: [53, 53] id: 8
  return r31_0(r0_8 .. _G.UILanguage)
end
local function r33_0(r0_9)
  -- line: [55, 55] id: 9
  return "data/game/confirm/" .. r0_9 .. "en.png"
end
local function r34_0(r0_10)
  -- line: [57, 57] id: 10
  return "data/game/confirm/" .. r0_10 .. _G.UILanguage .. ".png"
end
local function r35_0(r0_11)
  -- line: [59, 59] id: 11
  return "data/title/" .. r0_11 .. ".png"
end
local function r36_0(r0_12)
  -- line: [61, 61] id: 12
  return r35_0(r0_12 .. _G.UILanguage)
end
local function r37_0(r0_13)
  -- line: [63, 63] id: 13
  return "data/hint/" .. r0_13 .. ".png"
end
local function r38_0(r0_14)
  -- line: [65, 65] id: 14
  return r37_0(r0_14 .. _G.UILanguage)
end
local function r39_0(r0_15)
  -- line: [67, 67] id: 15
  return "data/option/" .. r0_15 .. ".png"
end
local function r40_0(r0_16, r1_16, r2_16, r3_16, r4_16)
  -- line: [69, 90] id: 16
  local r5_16 = display.newGroup()
  local r6_16 = nil
  local r7_16 = nil
  local r8_16 = 0
  for r12_16, r13_16 in pairs(r3_16) do
    r6_16 = string.format(db.GetMessage(r13_16), r4_16)
    display.newText(r5_16, r6_16, 1, r8_16 + 1, native.systemFontBold, 24):setFillColor(0, 0, 0)
    r7_16 = display.newText(r5_16, r6_16, 0, r8_16, native.systemFontBold, 24)
    r7_16:setFillColor(255, 255, 255)
    r8_16 = r8_16 + 36
  end
  r0_16:insert(r5_16)
  r5_16:setReferencePoint(display.TopLeftReferencePoint)
  r5_16.x = r1_16
  r5_16.y = r2_16
  r5_16.isVisible = true
  return r5_16
end
local function r41_0(r0_17, r1_17, r2_17)
  -- line: [92, 114] id: 17
  local r3_17 = 0
  local r4_17 = nil
  local r5_17 = {}
  local r6_17 = nil
  for r10_17, r11_17 in pairs(r1_17) do
    for r16_17, r17_17 in pairs(util.StringSplit(string.gsub(r11_17, "(\\n)", function(r0_18)
      -- line: [100, 100] id: 18
      return "\n"
    end), "\n")) do
      r6_17 = display.newText(r17_17, 0, 0, native.SystemFont, r2_17)
      r4_17 = r6_17.width
      if r3_17 < r4_17 then
        r3_17 = r4_17
      end
      table.insert(r5_17, r6_17)
    end
  end
  return r3_17, r5_17
end
local function r42_0(r0_19, r1_19, r2_19, r3_19, r4_19)
  -- line: [116, 127] id: 19
  local r5_19 = nil	-- notice: implicit variable refs by block#[3]
  if type(r1_19) == "string" then
    r5_19 = display.newText(r0_19, r1_19, 0, 0, native.systemFont, r4_19)
  else
    r5_19 = r1_19
    r0_19:insert(r1_19)
  end
  r5_19.x = r2_19 / 2
  r5_19.y = r3_19 + r5_19.height / 2
  r5_19:setFillColor(255, 255, 255)
end
local function r43_0(r0_20, r1_20, r2_20, r3_20)
  -- line: [129, 169] id: 20
  local r4_20 = display.newGroup()
  local r5_20 = db.GetMessage(r0_20)
  local r6_20 = {}
  for r10_20, r11_20 in pairs(r1_20) do
    if type(r11_20) == "string" then
      table.insert(r6_20, r11_20)
    else
      table.insert(r6_20, db.GetMessage(r11_20))
    end
  end
  local r7_20 = nil
  local r8_20 = nil
  r7_20, r8_20 = r41_0(r4_20, r6_20, 24)
  local r9_20 = table.maxn(r6_20)
  local r10_20 = 64 + r7_20 + 64
  local r11_20 = 128 + 40 * r9_20 + 62
  util.LoadParts(r4_20, r25_0("download_win"), 0, 0)
  local r12_20 = r4_20.width
  local r13_20 = r4_20.height
  r42_0(r4_20, r5_20, r12_20, 56, 32)
  for r17_20, r18_20 in pairs(r8_20) do
    r42_0(r4_20, r18_20, r12_20, 128 + 40 * (r17_20 - 1), 24)
  end
  local r14_20 = r12_20 / 2
  local r15_20 = r13_20 / 2 + 50
  if r3_20 == 1 then
    util.LoadBtn({
      rtImg = r4_20,
      fname = r26_0("download_"),
      x = r14_20 - 8 - 112,
      y = r15_20,
      func = r2_20[1],
      param = r2_20[3],
    })
    util.LoadBtn({
      rtImg = r4_20,
      fname = r39_0("close"),
      x = 566,
      y = -12,
      func = r2_20[2],
      param = r2_20[4],
    })
  elseif r3_20 == 2 or r3_20 == 3 then
    util.LoadBtn({
      rtImg = r4_20,
      fname = r27_0("ok_en"),
      x = r14_20 - 8 - 112,
      y = r15_20,
      func = r2_20[1],
      param = r2_20[3],
    })
  end
  return r4_20
end
local function r44_0()
  -- line: [172, 178] id: 21
  local r0_21 = -1
  if not _G.IsSimulator then
  end
  return r0_21
end
local function r45_0(r0_22, r1_22)
  -- line: [180, 194] id: 22
  local r2_22 = r0_22.params
  local r3_22 = r1_22.phase
  local r4_22 = r1_22.x - 70
  local r5_22 = r1_22.y - 80
  if r3_22 == "began" then
    r2_22.xy = {
      r4_22,
      r5_22
    }
    display.getCurrentStage():setFocus(r0_22)
  elseif r3_22 == "moved" then
    r2_22.xy = {
      r4_22,
      r5_22
    }
  elseif r3_22 == "ended" or r3_22 == "cancelled" then
    display.getCurrentStage():setFocus(nil)
  end
  return true
end
local function r46_0()
  -- line: [196, 223] id: 23
  r17_0 = anime.RegisterWithInterval(r20_0.GetData(), 70, 80, "data/download", 100)
  anime.Show(r17_0, true)
  anime.Loop(r17_0, true)
  local r0_23 = anime.GetSprite(r17_0)
  r0_23.touch = r45_0
  r0_23.params = r17_0
  r0_23:addEventListener("touch", r0_23)
  r18_0 = anime.RegisterWithInterval(r21_0.GetData(), 140, 400, "data/download", 100)
  anime.Show(r18_0, true)
  anime.Loop(r18_0, true)
  local r1_23 = anime.GetSprite(r18_0)
  r1_23.touch = r45_0
  r1_23.params = r18_0
  r1_23:addEventListener("touch", r1_23)
  r19_0 = anime.RegisterWithInterval(r22_0.GetData(), 640, 330, "data/download", 100)
  anime.Show(r19_0, true)
  anime.Loop(r19_0, true)
  local r2_23 = anime.GetSprite(r19_0)
  r2_23.touch = r45_0
  r2_23.params = r19_0
  r2_23:addEventListener("touch", r2_23)
end
function r47_0(r0_24)
  -- line: [225, 257] id: 24
  local r1_24 = false
  if r24_0.DownloadState() == r0_0 then
    local r2_24 = r24_0.GetDownloadEvent()
    local r3_24 = r24_0.GetDownloadQueue()
    if r2_24 == nil then
      if r15_0 ~= nil then
        r15_0.alpha = 0.5
      end
      r23_0.Dlg2Open(display.newGroup(), 279, {
        278
      }, {
        function()
          -- line: [237, 240] id: 25
          r23_0.DlgClose()
          util.ChangeScene({
            scene = r4_0,
            efx = "fade",
          })
        end
      })
      Runtime:removeEventListener("enterFrame", r47_0)
    elseif r3_24 then
      if r13_0 ~= nil then
        r13_0.text = string.format(r16_0, 100 - r2_24.nr / #r3_24 * 100)
        r14_0.text = string.format(r16_0, 100 - r2_24.nr / #r3_24 * 100)
      end
      if r12_0 ~= nil then
        r12_0.xScale = r3_0 / #r3_24 * r2_24.nr
      end
    end
  end
end
function r23_0.new(r0_26)
  -- line: [260, 367] id: 26
  if events.CheckNamespace("cdnview") == nil then
    events.SetNamespace("cdnview")
  end
  local r3_26 = display.newGroup()
  local r4_26 = util.MakeGroup(r3_26)
  util.MakeFrame(r3_26)
  r46_0()
  util.LoadParts(r4_26, r25_0("download_bg"), 0, 0)
  util.LoadParts(r4_26, r25_0("download_title"), 262, 40)
  local r5_26 = string.format(db.GetMessage(273), 0)
  display.newText(r4_26, r5_26, 202, 158, native.systemFontBold, 24):setFillColor(0, 0, 0)
  display.newText(r4_26, r5_26, 202, 158, native.systemFontBold, 24):setFillColor(255, 255, 255)
  r16_0 = db.GetMessage(274)
  r5_26 = string.format(r16_0, 0)
  r13_0 = display.newText(r4_26, r5_26, 420, 336, native.systemFontBold, 24)
  r13_0:setFillColor(0, 0, 0)
  r14_0 = display.newText(r4_26, r5_26, 420, 336, native.systemFontBold, 24)
  r14_0:setFillColor(255, 255, 255)
  local r7_26 = 80
  local r8_26 = 272
  r12_0 = util.LoadParts(r4_26, r25_0("dl_gauge_meter"), r7_26 + 47, r8_26 + 7)
  r12_0.xScale = 0
  util.LoadParts(r4_26, r25_0("dl_gauge_top"), r7_26, r8_26)
  util.LoadBtn({
    rtImg = r4_26,
    fname = r26_0("cancel_"),
    x = 678,
    y = 528,
    func = function(r0_27)
      -- line: [299, 307] id: 27
      Runtime:removeEventListener("enterFrame", r47_0)
      r23_0.Close()
      if r4_0 == nil then
        util.ChangeScene({
          scene = "title",
          efx = "fade",
        })
      else
        util.ChangeScene({
          scene = r5_0,
          efx = "fade",
        })
      end
    end,
  })
  r15_0 = display.newRect(r4_26, 0, 0, _G.Width, _G.Height)
  r15_0:setFillColor(140, 140, 140)
  r15_0.alpha = 0.5
  r3_26:insert(r4_26)
  r4_0 = r0_26.next
  r5_0 = r0_26.back
  if r0_26.val ~= nil then
    r6_0 = r0_26.val
  else
    r6_0 = nil
  end
  if not _G.IsSimulator and 0 <= r44_0() and r44_0() < r2_0 then
    r23_0.Dlg3Open(r4_26, 277, {
      string.format(db.GetMessage(348), r1_0)
    }, {
      function()
        -- line: [326, 334] id: 28
        r23_0.DlgClose()
        r23_0.Close()
        if r4_0 == nil then
          util.ChangeScene({
            scene = "title",
            efx = "fade",
          })
        else
          util.ChangeScene({
            scene = r5_0,
            efx = "fade",
          })
        end
      end
    })
  elseif r0_26.scene ~= "option" then
    r23_0.DlgOpen(r4_26, 277, {
      276
    }, {
      function()
        -- line: [339, 346] id: 29
        if r6_0 ~= nil then
          _G.MapSelect = r6_0
        end
        r23_0.DlgClose()
        r15_0.alpha = 0
        r24_0.DownloadRequest()
      end,
      function()
        -- line: [348, 356] id: 30
        r23_0.DlgClose()
        r23_0.Close()
        if r4_0 == nil then
          util.ChangeScene({
            scene = "title",
            efx = "fade",
          })
        else
          util.ChangeScene({
            scene = r5_0,
            efx = "fade",
          })
        end
      end
    })
  else
    r15_0.alpha = 0
    r24_0.DownloadRequest()
  end
  Runtime:addEventListener("enterFrame", r47_0)
  return r3_26
end
function r23_0.Close()
  -- line: [369, 409] id: 31
  if r13_0 then
    r13_0 = nil
  end
  if r14_0 then
    r14_0 = nil
  end
  if r15_0 then
    r15_0 = nil
  end
  if r9_0 then
    transit.Delete(r9_0)
    r9_0 = nil
  end
  if r8_0 then
    display.remove(r8_0)
    r8_0 = nil
  end
  if r7_0 then
    display.remove(r7_0)
    r7_0 = nil
  end
  if r10_0 then
    r10_0(r11_0)
  end
  r24_0.Close()
  if r17_0 then
    anime.Remove(r17_0)
  end
  if r18_0 then
    anime.Remove(r18_0)
  end
  if r19_0 then
    anime.Remove(r19_0)
  end
  events.DeleteNamespace("cdnview")
end
function r23_0.DlgClose()
  -- line: [412, 428] id: 32
  if r9_0 then
    transit.Delete(r9_0)
    r9_0 = nil
  end
  if r8_0 then
    display.remove(r8_0)
    r8_0 = nil
  end
  if r7_0 then
    display.remove(r7_0)
    r7_0 = nil
  end
  if r10_0 then
    r10_0(r11_0)
  end
end
function r23_0.DlgOpen(r0_33, r1_33, r2_33, r3_33)
  -- line: [430, 439] id: 33
  local r4_33 = nil	-- notice: implicit variable refs by block#[0]
  r10_0 = r4_33
  r23_0.DlgClose()
  r8_0 = util.MakeMat(r0_33)
  r7_0 = r43_0(r1_33, r2_33, r3_33, 1)
  r0_33:insert(r7_0)
  r7_0:setReferencePoint(display.CenterReferencePoint)
  r7_0.x = _G.Width / 2
  r4_33 = r7_0
  r4_33.y = _G.Height / 2
end
function r23_0.Dlg2Open(r0_34, r1_34, r2_34, r3_34)
  -- line: [441, 450] id: 34
  local r4_34 = nil	-- notice: implicit variable refs by block#[0]
  r10_0 = r4_34
  r23_0.DlgClose()
  r8_0 = util.MakeMat(r0_34)
  r7_0 = r43_0(r1_34, r2_34, r3_34, 2)
  r0_34:insert(r7_0)
  r7_0:setReferencePoint(display.CenterReferencePoint)
  r7_0.x = _G.Width / 2
  r4_34 = r7_0
  r4_34.y = _G.Height / 2
end
function r23_0.Dlg3Open(r0_35, r1_35, r2_35, r3_35)
  -- line: [452, 461] id: 35
  local r4_35 = nil	-- notice: implicit variable refs by block#[0]
  r10_0 = r4_35
  r23_0.DlgClose()
  r8_0 = util.MakeMat(r0_35)
  r7_0 = r43_0(r1_35, r2_35, r3_35, 3)
  r0_35:insert(r7_0)
  r7_0:setReferencePoint(display.CenterReferencePoint)
  r7_0.x = _G.Width / 2
  r4_35 = r7_0
  r4_35.y = _G.Height / 2
end
return setmetatable(r23_0, {
  __index = r24_0,
})
