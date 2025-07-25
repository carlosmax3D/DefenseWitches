-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("ad.myads")
local r1_0 = "old"
local r2_0 = "new"
local r3_0 = nil
local r4_0 = nil
local r5_0 = nil
local r6_0 = nil
local r7_0 = nil
local r8_0 = {
  {
    239,
    "bgm",
    {
      nil,
      nil,
      "on"
    },
    {
      nil,
      nil,
      "off"
    },
    true
  },
  {
    240,
    "se",
    {
      nil,
      nil,
      "on"
    },
    {
      nil,
      nil,
      "off"
    },
    true
  },
  {
    241,
    "voice",
    {
      nil,
      nil,
      "on"
    },
    {
      nil,
      nil,
      "off"
    },
    true
  },
  {
    370,
    "voice_type",
    {
      nil,
      nil,
      "old"
    },
    {
      nil,
      nil,
      "new"
    },
    "new"
  },
  {
    242,
    "language",
    {
      nil,
      nil,
      "jp"
    },
    {
      nil,
      nil,
      "en"
    },
    "jp"
  },
  {
    243,
    "confirm",
    {
      nil,
      nil,
      "on"
    },
    {
      nil,
      nil,
      "off"
    },
    true
  },
  {
    244,
    "grid",
    {
      nil,
      nil,
      "on"
    },
    {
      nil,
      nil,
      "off"
    },
    true
  },
  {
    347,
    "local_notification",
    {
      nil,
      nil,
      "on"
    },
    {
      nil,
      nil,
      "off"
    },
    true
  }
}
local r9_0 = 88
local function r10_0(r0_1, r1_1)
  -- line: [28, 75] id: 1
  local r2_1 = r1_1.phase
  if r2_1 == "began" then
    r0_1.start_y = r1_1.y
    display.getCurrentStage():setFocus(r0_1)
  elseif r2_1 == "moved" then
    local r3_1 = r0_1.start_y
    if r3_1 == nil then
      return true
    end
    r0_1.start_y = r1_1.y
    local r6_1 = r0_1.end_y
    local r5_1 = r0_1.y + r1_1.y - r3_1
    if r9_0 < r5_1 then
      r5_1 = r9_0
    end
    if r5_1 < r6_1 then
      r5_1 = r6_1
    end
    r0_1.y = r5_1
    if r6_1 ~= 88 then
      if r5_0.tween then
        transit.Delete(r5_0.tween)
        r5_0.tween = nil
      end
      r5_0.bar.y = r5_0.moveRange * (r9_0 - r5_1) / (r9_0 - r6_1)
      local r12_1 = r5_0.rtImg
      r12_1.isVisible = true
      r12_1.alpha = 1
    end
  elseif r2_1 == "ended" or r2_1 == "cancelled" then
    if r0_1.end_y ~= r9_0 then
      r5_0.tween = transit.Register(r5_0.rtImg, {
        time = 500,
        alpha = 0.01,
        transition = easing.outExpo,
        onComplete = function(r0_2)
          -- line: [66, 69] id: 2
          r5_0.rtImg.isVisible = false
          r5_0.tween = nil
        end,
      })
    end
    display.getCurrentStage():setFocus(nil)
    r0_1.start_y = nil
  end
  return true
end
local function r11_0()
  -- line: [77, 113] id: 3
  for r6_3, r7_3 in pairs(r8_0) do
    local r0_3 = r7_3[5]
    local r1_3 = nil	-- notice: implicit variable refs by block#[12, 14, 17, 19]
    if type(r0_3) == "boolean" then
      r1_3 = r0_3
    elseif r7_3[2] == "voice_type" then
      r1_3 = r0_3 == r1_0
    else
      r1_3 = r0_3 == "jp"
    end
    if r7_3[3] ~= nil then
      local r2_3 = r7_3[3]
      if r2_3[1] ~= nil then
        r2_3[1].isVisible = r1_3
      end
      if r2_3[2] ~= nil then
        r2_3[2].isVisible = not r1_3
      end
    end
    if r7_3[4] ~= nil then
      local r2_3 = r7_3[4]
      if r2_3[1] ~= nil then
        r2_3[1].isVisible = not r1_3
      end
      if r2_3[2] ~= nil then
        r2_3[2].isVisible = r1_3
      end
    end
  end
end
local function r12_0(r0_4)
  -- line: [115, 141] id: 4
  sound.PlaySE(3)
  local r1_4 = r0_4.param
  local r2_4 = r1_4[5]
  if type(r2_4) == "boolean" then
    r1_4[5] = not r2_4
  elseif r1_4[2] == "voice_type" then
    if r2_4 == sound.OptionChangeTypeOld or r2_4 == r1_0 then
      r1_4[5] = r2_0
    else
      r1_4[5] = r1_0
    end
  elseif r2_4 == "jp" then
    r1_4[5] = "en"
  else
    r1_4[5] = "jp"
  end
  r11_0()
  return true
end
local function r13_0(r0_5)
  -- line: [144, 177] id: 5
  local r1_5 = _G.WidthDiff
  local r2_5 = _G.HeightDiff
  if r1_5 < 0 or r2_5 < 0 then
    local r3_5 = display.newGroup()
    local r4_5 = nil
    if r2_5 < 0 then
      local r6_5 = _G.Width
      local r7_5 = -r2_5
      display.newRect(r3_5, 0, r2_5, r6_5, r7_5):setFillColor(0, 0, 0)
      display.newRect(r3_5, 0, _G.Height, r6_5, r7_5):setFillColor(0, 0, 0)
    end
    if r1_5 < 0 and r1_5 ~= -176 then
      local r6_5 = r2_5
      local r7_5 = -r1_5
      local r8_5 = _G.Height + -r2_5
      display.newRect(r3_5, r1_5, r6_5, r7_5, r8_5):setFillColor(0, 0, 0)
      display.newRect(r3_5, _G.Width, r6_5, r7_5, r8_5):setFillColor(0, 0, 0)
    end
    r0_5:insert(r3_5)
    if r1_5 == -176 then
      return r3_5
    end
  end
end
return {
  Open = function(r0_6)
    -- line: [183, 391] id: 6
    local r1_6 = r0_6.mode
    local r2_6 = false
    if r0_0.GetLastRes() == false then
      r2_6 = true
    end
    local r3_6 = _G.Width
    local function r4_6(r0_7)
      -- line: [191, 191] id: 7
      return "data/option/" .. r0_7 .. ".png"
    end
    local function r5_6(r0_8)
      -- line: [192, 192] id: 8
      return r4_6(r0_8 .. _G.UILanguage)
    end
    local r6_6 = display.newGroup()
    local r7_6 = display.newGroup()
    local r8_6 = display.newGroup()
    local r9_6 = display.newGroup()
    local r10_6 = display.newGroup()
    r8_6:insert(r7_6)
    r6_6:insert(r8_6)
    r6_6:insert(r9_6)
    r6_6:insert(r10_6)
    r13_0(r6_6)
    local r11_6 = nil
    local r12_6 = nil
    if r1_6 and r2_6 then
      r11_6 = r4_6("bg_bottom02")
      r12_6 = 448
    else
      r11_6 = r4_6("bg_bottom01")
      r12_6 = 576
    end
    local r13_6 = nil
    for r17_6 = 0, r3_6 - 16, 16 do
      util.LoadParts(r9_6, r4_6("bg_top"), r17_6, 0)
      util.LoadParts(r9_6, r11_6, r17_6, r12_6)
      r13_6 = util.LoadParts(r6_6, r4_6("bg_back"), r17_6, 88)
      r6_6:insert(1, r13_6)
    end
    if r13_6 ~= nil then
      local r14_6 = display.newRect(r8_6, 0, 0, r3_6, r13_6.height)
      r14_6:setFillColor(1, 0, 0, 1)
      r14_6:toFront()
    end
    util.LoadParts(r9_6, r4_6("option_title"), 250, 40)
    if r1_6 and r2_6 then
      r12_6 = 470 or 598
    else
      goto label_129	-- block#14 is visited secondly
    end
    for r17_6 = 96, 608, 256 do
      util.LoadParts(r9_6, r4_6("option_line01"), r17_6, 88)
      util.LoadParts(r9_6, r4_6("option_line01"), r17_6, r12_6)
    end
    local r14_6 = nil
    local r15_6 = nil
    local r16_6 = nil
    local r17_6 = nil
    r12_6 = 16
    local r18_6 = nil	-- notice: implicit variable refs by block#[27, 46, 50]
    if r1_6 == false and r2_6 == false then
      function r18_6(r0_9)
        -- line: [246, 246] id: 9
        return "data/cdn/" .. r0_9 .. ".png"
      end
      local function r19_6(r0_10)
        -- line: [247, 247] id: 10
        return r18_6(r0_10 .. _G.UILanguage)
      end
      r14_6 = util.MakeText3({
        rtImg = r7_6,
        size = 24,
        color = {
          236,
          216,
          179
        },
        shadow = {
          0,
          0,
          0
        },
        msg = db.GetMessage(277),
      })
      r14_6:setReferencePoint(display.CenterLeftReferencePoint)
      r14_6.x = 96
      r14_6.y = r12_6 + 32
      r12_6 = r12_6 + 64
      for r23_6 = 96, 608, 256 do
        util.LoadParts(r7_6, r4_6("option_line02"), r23_6, r12_6)
        util.LoadParts(r7_6, r4_6("option_line02"), r23_6, r12_6)
        util.LoadParts(r7_6, r4_6("option_line02"), r23_6, r12_6)
      end
      if cdn.CheckFilelist() == true then
        util.LoadBtn({
          rtImg = r7_6,
          fname = r19_6("download_op_"),
          x = 626,
          y = 16,
          func = function(r0_11)
            -- line: [264, 269] id: 11
            if cdn.CheckFilelist() == true then
              util.ChangeScene({
                scene = "cdn",
                efx = "fade",
                param = {
                  next = "title",
                  back = "title",
                  scene = "option",
                },
              })
            end
          end,
        })
      else
        util.LoadParts(r7_6, r19_6("download_op_"), 626, 16).alpha = 0.5
      end
      -- close: r18_6
    end
    r18_6 = pairs
    for r21_6, r22_6 in r18_6(r8_0) do
      if r1_6 == false or r1_6 == true and r22_6[2] ~= "local_notification" then
        r14_6 = util.MakeText3({
          rtImg = r7_6,
          size = 24,
          color = {
            236,
            216,
            179
          },
          shadow = {
            0,
            0,
            0
          },
          msg = db.GetMessage(r22_6[1]),
        })
        r14_6:setReferencePoint(display.CenterLeftReferencePoint)
        r14_6.x = 96
        r14_6.y = r12_6 + 32
        r15_6 = r22_6[2]
        r16_6 = r22_6[3][3]
        r22_6[3][1] = util.LoadBtn({
          rtImg = r7_6,
          fname = r4_6(r16_6 .. "_active"),
          x = 648,
          y = r12_6,
          func = r12_0,
          param = r22_6,
        })
        r22_6[3][2] = util.LoadBtn({
          rtImg = r7_6,
          fname = r4_6(r16_6 .. "_nonactive"),
          x = 648,
          y = r12_6,
          func = r12_0,
          param = r22_6,
        })
        r16_6 = r22_6[4][3]
        r22_6[4][1] = util.LoadBtn({
          rtImg = r7_6,
          fname = r4_6(r16_6 .. "_active"),
          x = 760,
          y = r12_6,
          func = r12_0,
          param = r22_6,
        })
        r22_6[4][2] = util.LoadBtn({
          rtImg = r7_6,
          fname = r4_6(r16_6 .. "_nonactive"),
          x = 760,
          y = r12_6,
          func = r12_0,
          param = r22_6,
        })
        if r15_6 == "voice_type" then
          if tonumber(_G.GameData[r15_6]) == sound.OptionChangeTypeOld then
            r22_6[5] = r1_0
          else
            r22_6[5] = r2_0
          end
        else
          r22_6[5] = _G.GameData[r15_6]
        end
        if not r1_6 or r21_6 ~= #r8_0 - 1 then
          r17_6 = r21_6 == #r8_0
        else
          goto label_409	-- block#40 is visited secondly
        end
        r12_6 = r12_6 + 64
        if not r17_6 then
          for r26_6 = 96, 608, 256 do
            util.LoadParts(r7_6, r4_6("option_line02"), r26_6, r12_6)
            util.LoadParts(r7_6, r4_6("option_line02"), r26_6, r12_6)
            util.LoadParts(r7_6, r4_6("option_line02"), r26_6, r12_6)
          end
        end
      end
    end
    r8_6:setReferencePoint(display.TopLeftReferencePoint)
    r8_6.x = 0
    r8_6.y = 88
    r8_6.start_y = nil
    r8_6.end_y = 0
    r18_6 = r11_0
    r18_6()
    r18_6 = r10_0
    r8_6.touch = r18_6
    r8_6:addEventListener("touch")
    r18_6 = nil
    if r1_6 and r2_6 then
      r12_6 = 336
      r18_6 = 256
    else
      r12_6 = 464
      r18_6 = 385
    end
    r14_6 = display.newRect(r10_6, 8, 8, 8, r12_6)
    r14_6.strokeWidth = 1
    r14_6:setFillColor(0, 0, 0, 63.75)
    r14_6:setStrokeColor(0, 0, 0, 127.5)
    r14_6 = util.LoadParts(r10_6, r4_6("scrollbar_slider"), 0, 0)
    r10_6:setReferencePoint(display.TopLeftReferencePoint)
    r10_6.x = 872
    r10_6.y = 104
    r5_0 = {}
    r5_0.bar = r14_6
    r5_0.rtImg = r10_6
    r5_0.tween = nil
    r5_0.moveRange = r18_6
    r10_6.isVisible = false
    local r19_6 = r0_6.func
    util.LoadBtn({
      rtImg = r9_6,
      fname = r4_6("close"),
      x = 872,
      y = 0,
      func = r19_6.close,
    })
    if r1_6 then
      local r20_6 = r4_6("back_en")
      if _G.UILanguage == "jp" then
        r20_6 = r4_6("stageselect_jp")
      end
      util.LoadBtn({
        rtImg = r9_6,
        fname = r20_6,
        x = 648,
        y = 544,
        func = r19_6.back,
      })
    end
    function r6_6.touch(r0_12, r1_12)
      -- line: [368, 368] id: 12
      return true
    end
    r6_6:addEventListener("touch")
    if r0_6.rtImg then
      r0_6.rtImg:insert(r6_6)
    end
    r3_0 = r6_6
    r4_0 = r8_6
    if r1_6 then
      if r7_0 then
        transition.cancel(r7_0)
      end
      r7_0 = transition.to(_G.PanelRoot, {
        time = 500,
        y = _G.HeightDiff - _G.PanelRoot.height,
        transition = easing.outExpo,
      })
      r6_0 = char.AllPause(true)
    end
    r3_0.alpha = 0.01
    transition.to(r3_0, {
      time = 500,
      alpha = 1,
      transition = easing.outQuad,
    })
  end,
  Close = function(r0_13)
    -- line: [393, 439] id: 13
    if r0_13 == nil then
      r0_13 = true
    end
    if r0_13 then
      if r7_0 then
        transition.cancel(r7_0)
      end
      r7_0 = transition.to(_G.PanelRoot, {
        time = 500,
        y = 0,
        transition = easing.outExpo,
        onComplete = function()
          -- line: [401, 401] id: 14
          local r0_14 = nil	-- notice: implicit variable refs by block#[0]
          r7_0 = r0_14
        end,
      })
      char.AllPause(false, r6_0)
    end
    if r5_0.tween then
      transit.Delete(r5_0.tween)
      r5_0.tween = nil
    end
    local r1_13 = nil
    for r5_13, r6_13 in pairs(r8_0) do
      r1_13 = r6_13[2]
      if r1_13 == "voice_type" then
        if r6_13[5] == r1_0 then
          _G.GameData[r1_13] = sound.OptionChangeTypeOld
        else
          _G.GameData[r1_13] = sound.OptionChangeTypeNew
        end
      else
        _G.GameData[r1_13] = r6_13[5]
      end
    end
    if _G.GameData.local_notification == false then
      notification.RemoveNotification()
    else
      notification.RemoveNotification()
      notification.SetLocalNotification()
    end
    display.remove(r3_0)
    r3_0 = nil
  end,
}
