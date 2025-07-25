-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("scene.help")
local r1_0 = "data/help/howto"
local function r2_0(r0_1)
  -- line: [9, 9] id: 1
  return r1_0 .. "/" .. r0_1 .. ".png"
end
local function r3_0(r0_2)
  -- line: [10, 10] id: 2
  return r2_0(r0_2 .. _G.UILanguage)
end
local function r4_0(r0_3)
  -- line: [11, 11] id: 3
  return "data/stage/" .. r0_3 .. ".png"
end
local function r5_0(r0_4)
  -- line: [12, 12] id: 4
  return r4_0(r0_4 .. _G.UILanguage)
end
local function r6_0(r0_5)
  -- line: [13, 13] id: 5
  return "data/help/witches/" .. r0_5 .. ".png"
end
local function r7_0(r0_6)
  -- line: [15, 19] id: 6
  sound.PlaySE(2)
  r0_0.ViewHelp("index")
  return true
end
local function r8_0(r0_7)
  -- line: [21, 25] id: 7
  sound.PlaySE(1)
  r0_0.ViewHelp("howto", nil)
  return true
end
local r9_0 = {
  {
    135,
    124
  },
  {
    125,
    126
  },
  {
    127,
    128
  },
  {
    129,
    130
  },
  {
    131,
    132
  },
  {
    133,
    134
  }
}
local function r10_0(r0_8)
  -- line: [32, 111] id: 8
  local r1_8 = display.newGroup()
  local r2_8 = nil
  local r3_8 = nil
  local r4_8 = nil
  local r5_8 = nil
  r2_8 = r0_0.MakeTextParts(r2_0("title_plate"), 24, {
    26,
    19,
    10
  }, {
    255,
    246,
    229
  }, db.GetMessage(88) .. string.format(" (%d)", r0_8))
  r2_8:setReferencePoint(display.TopLeftReferencePoint)
  r2_8.x = 80
  r2_8.y = 80
  r1_8:insert(r2_8)
  r4_8 = r9_0[r0_8]
  if r0_8 == 1 then
    util.LoadParts(r1_8, r2_0("number00"), 92, 156)
  else
    util.LoadParts(r1_8, r2_0(string.format("number%02d", r0_8 - 1)), 92, 156)
  end
  r2_8 = util.MakeText(22, {
    26,
    19,
    10
  }, {
    255,
    246,
    229
  }, db.GetMessage(r4_8[1]))
  r1_8:insert(r2_8)
  r2_8.x = 128
  r2_8.y = 160
  r2_8 = util.MakeText(20, {
    26,
    19,
    10
  }, {
    255,
    246,
    229
  }, db.GetMessage(r4_8[2]), 384, 288)
  r1_8:insert(r2_8)
  r2_8.x = 128
  r2_8.y = 200
  if 1 <= r0_8 and r0_8 <= 4 then
    util.LoadParts(r1_8, r3_0("control_image01"), 528, 152)
  elseif r0_8 == 5 then
    util.LoadParts(r1_8, r2_0("control_image02"), 528, 152)
  elseif r0_8 == 6 then
    util.LoadParts(r1_8, r2_0("control_image03"), 528, 152)
  end
  local r6_8 = 1
  for r10_8 = 324, 604, 56 do
    if r6_8 == r0_8 then
      r5_8 = r6_0("witches_page_active")
    else
      r5_8 = r6_0("witches_page_nonactive")
    end
    util.LoadParts(r1_8, r5_8, r10_8, 516)
    r6_8 = r6_8 + 1
  end
  util.LoadBtn({
    rtImg = r1_8,
    fname = r5_0("back_"),
    x = 256,
    y = 560,
    func = r8_0,
  })
  local function r7_8(r0_9)
    -- line: [93, 99] id: 9
    local r1_9 = r0_9.param
    local r2_9 = assert
    local r3_9 = nil	-- notice: implicit variable refs by block#[4]
    if r1_9 >= 1 then
      r3_9 = r1_9 <= 6
    else
      goto label_6	-- block#2 is visited secondly
    end
    r2_9(r3_9)
    sound.PlaySE(1)
    r2_9 = r0_0.ViewHelp
    r3_9 = "howto"
    r2_9(r3_9, {
      r10_0,
      r1_9
    })
    return true
  end
  if r0_8 > 1 then
    util.LoadBtn({
      rtImg = r1_8,
      fname = r4_0("scrl_previous"),
      x = 448,
      y = 560,
      func = r7_8,
      param = r0_8 - 1,
    })
  end
  if r0_8 < 6 then
    util.LoadBtn({
      rtImg = r1_8,
      fname = r4_0("scrl_next"),
      x = 576,
      y = 560,
      func = r7_8,
      param = r0_8 + 1,
    })
  end
  return r1_8
end
local r11_0 = {
  {
    120,
    121
  },
  {
    122,
    123
  }
}
local function r12_0(r0_10)
  -- line: [117, 190] id: 10
  local r1_10 = display.newGroup()
  local r2_10 = nil
  local r3_10 = nil
  local r4_10 = nil
  local r5_10 = nil
  r2_10 = r0_0.MakeTextParts(r2_0("title_plate"), 24, {
    26,
    19,
    10
  }, {
    255,
    246,
    229
  }, db.GetMessage(87) .. string.format(" (%d)", r0_10))
  r2_10:setReferencePoint(display.TopLeftReferencePoint)
  r2_10.x = 80
  r2_10.y = 80
  r1_10:insert(r2_10)
  r4_10 = r11_0[r0_10]
  util.LoadParts(r1_10, r2_0(string.format("number%02d", r0_10)), 92, 156)
  r2_10 = util.MakeText(22, {
    26,
    19,
    10
  }, {
    255,
    246,
    229
  }, db.GetMessage(r4_10[1]))
  r1_10:insert(r2_10)
  r2_10.x = 128
  r2_10.y = 160
  r2_10 = util.MakeText(20, {
    26,
    19,
    10
  }, {
    255,
    246,
    229
  }, db.GetMessage(r4_10[2]), 384, 288)
  r1_10:insert(r2_10)
  r2_10.x = 128
  r2_10.y = 200
  if r0_10 == 1 then
    util.LoadParts(r1_10, r2_0("summon_image01"), 528, 152)
  else
    util.LoadParts(r1_10, r3_0("summon_image02_"), 528, 152)
  end
  local r6_10 = 1
  for r10_10 = 436, 492, 56 do
    if r6_10 == r0_10 then
      r5_10 = r6_0("witches_page_active")
    else
      r5_10 = r6_0("witches_page_nonactive")
    end
    util.LoadParts(r1_10, r5_10, r10_10, 516)
    r6_10 = r6_10 + 1
  end
  util.LoadBtn({
    rtImg = r1_10,
    fname = r5_0("back_"),
    x = 256,
    y = 560,
    func = r8_0,
  })
  local function r7_10(r0_11)
    -- line: [172, 178] id: 11
    local r1_11 = r0_11.param
    local r2_11 = assert
    local r3_11 = nil	-- notice: implicit variable refs by block#[4]
    if r1_11 >= 1 then
      r3_11 = r1_11 <= 2
    else
      goto label_6	-- block#2 is visited secondly
    end
    r2_11(r3_11)
    sound.PlaySE(1)
    r2_11 = r0_0.ViewHelp
    r3_11 = "howto"
    r2_11(r3_11, {
      r12_0,
      r1_11
    })
    return true
  end
  if r0_10 > 1 then
    util.LoadBtn({
      rtImg = r1_10,
      fname = r4_0("scrl_previous"),
      x = 448,
      y = 560,
      func = r7_10,
      param = r0_10 - 1,
    })
  end
  if r0_10 < 2 then
    util.LoadBtn({
      rtImg = r1_10,
      fname = r4_0("scrl_next"),
      x = 576,
      y = 560,
      func = r7_10,
      param = r0_10 + 1,
    })
  end
  return r1_10
end
local r13_0 = {
  {
    113,
    114
  },
  {
    115,
    116
  },
  {
    117,
    118
  }
}
local function r14_0(r0_12)
  -- line: [196, 266] id: 12
  local r1_12 = display.newGroup()
  local r2_12 = nil
  local r3_12 = nil
  local r4_12 = nil
  local r5_12 = nil
  r2_12 = r0_0.MakeTextParts(r2_0("title_plate"), 24, {
    26,
    19,
    10
  }, {
    255,
    246,
    229
  }, db.GetMessage(86) .. string.format(" (%d)", r0_12))
  r2_12:setReferencePoint(display.TopLeftReferencePoint)
  r2_12.x = 80
  r2_12.y = 80
  r1_12:insert(r2_12)
  r4_12 = r13_0[r0_12]
  util.LoadParts(r1_12, r2_0(string.format("number%02d", r0_12)), 92, 156)
  r2_12 = util.MakeText(22, {
    26,
    19,
    10
  }, {
    255,
    246,
    229
  }, db.GetMessage(r4_12[1]))
  r1_12:insert(r2_12)
  r2_12.x = 128
  r2_12.y = 160
  r2_12 = util.MakeText(20, {
    26,
    19,
    10
  }, {
    255,
    246,
    229
  }, db.GetMessage(r4_12[2]), 384, 288)
  r1_12:insert(r2_12)
  r2_12.x = 128
  r2_12.y = 200
  util.LoadParts(r1_12, r2_0(string.format("playfield_image%02d", r0_12)), 528, 152)
  local r6_12 = 1
  for r10_12 = 408, 520, 56 do
    if r6_12 == r0_12 then
      r5_12 = r6_0("witches_page_active")
    else
      r5_12 = r6_0("witches_page_nonactive")
    end
    util.LoadParts(r1_12, r5_12, r10_12, 516)
    r6_12 = r6_12 + 1
  end
  util.LoadBtn({
    rtImg = r1_12,
    fname = r5_0("back_"),
    x = 256,
    y = 560,
    func = r8_0,
  })
  local function r7_12(r0_13)
    -- line: [248, 254] id: 13
    local r1_13 = r0_13.param
    local r2_13 = assert
    local r3_13 = nil	-- notice: implicit variable refs by block#[4]
    if r1_13 >= 1 then
      r3_13 = r1_13 <= 3
    else
      goto label_6	-- block#2 is visited secondly
    end
    r2_13(r3_13)
    sound.PlaySE(1)
    r2_13 = r0_0.ViewHelp
    r3_13 = "howto"
    r2_13(r3_13, {
      r14_0,
      r1_13
    })
    return true
  end
  if r0_12 > 1 then
    util.LoadBtn({
      rtImg = r1_12,
      fname = r4_0("scrl_previous"),
      x = 448,
      y = 560,
      func = r7_12,
      param = r0_12 - 1,
    })
  end
  if r0_12 < 3 then
    util.LoadBtn({
      rtImg = r1_12,
      fname = r4_0("scrl_next"),
      x = 576,
      y = 560,
      func = r7_12,
      param = r0_12 + 1,
    })
  end
  return r1_12
end
local r15_0 = {
  {
    {
      89,
      90,
      91
    },
    {
      92,
      93,
      94
    }
  },
  {
    {
      98,
      99,
      100
    },
    {
      95,
      96,
      97
    }
  },
  {
    {
      101,
      102,
      103
    },
    {
      104,
      105,
      106
    }
  },
  {
    {
      371,
      372,
      373,
      374
    },
    {
      375,
      376,
      377,
      378
    }
  },
  {
    {
      379,
      380,
      381,
      382,
      383,
      384,
      387,
      385,
      386
    }
  }
}
local function r16_0(r0_14)
  -- line: [277, 395] id: 14
  local r1_14 = display.newGroup()
  local r2_14 = nil
  local r3_14 = nil
  local r4_14 = nil
  local r5_14 = nil
  local r6_14 = nil
  local r7_14 = nil
  r2_14 = r0_0.MakeTextParts(r2_0("title_plate"), 24, {
    26,
    19,
    10
  }, {
    255,
    246,
    229
  }, db.GetMessage(85) .. string.format(" (%d)", r0_14))
  r2_14:setReferencePoint(display.TopLeftReferencePoint)
  r2_14.x = 80
  r2_14.y = 80
  r1_14:insert(r2_14)
  r4_14 = r0_14 * 2 - 1
  local r8_14 = 156
  for r12_14 = 1, #r15_0[r0_14], 1 do
    r5_14 = r15_0[r0_14][r12_14]
    if r5_14 ~= nil then
      util.LoadParts(r1_14, r2_0(string.format("number%02d", r4_14)), 92, r8_14)
      for r16_14 = 1, #r5_14, 1 do
        if r5_14[r16_14] ~= nil then
          local r17_14 = 0
          local r18_14 = 22
          if r16_14 == 1 then
            r17_14 = r8_14 + 2
          else
            r17_14 = r8_14 + r16_14 * 22
            r18_14 = 20
          end
          local r20_14 = util.MakeText(r18_14, {
            26,
            19,
            10
          }, {
            255,
            246,
            229
          }, db.GetMessage(r5_14[r16_14]))
          r1_14:insert(r20_14)
          r20_14.x = 128
          r20_14.y = r17_14
        end
      end
      r4_14 = r4_14 + 1
      r8_14 = r8_14 + 120
    end
  end
  util.LoadParts(r1_14, r3_0("menubar_image_"), 88, 400)
  r4_14 = 1
  local r9_14 = 56
  local r10_14 = #r15_0 - 1
  local r11_14 = 352
  for r15_14 = 1, #r15_0, 1 do
    if r4_14 == r0_14 then
      r7_14 = r6_0("witches_page_active")
    else
      r7_14 = r6_0("witches_page_nonactive")
    end
    util.LoadParts(r1_14, r7_14, r11_14, 516)
    r11_14 = r11_14 + r9_14
    r4_14 = r4_14 + 1
  end
  util.LoadBtn({
    rtImg = r1_14,
    fname = r5_0("back_"),
    x = 256,
    y = 560,
    func = r8_0,
  })
  local function r12_14(r0_15)
    -- line: [377, 383] id: 15
    local r1_15 = r0_15.param
    local r2_15 = assert
    local r3_15 = nil	-- notice: implicit variable refs by block#[4]
    if r1_15 >= 1 then
      r3_15 = r1_15 <= #r15_0
    else
      goto label_8	-- block#2 is visited secondly
    end
    r2_15(r3_15)
    sound.PlaySE(1)
    r2_15 = r0_0.ViewHelp
    r3_15 = "howto"
    r2_15(r3_15, {
      r16_0,
      r1_15
    })
    return true
  end
  if r0_14 > 1 then
    util.LoadBtn({
      rtImg = r1_14,
      fname = r4_0("scrl_previous"),
      x = 448,
      y = 560,
      func = r12_14,
      param = r0_14 - 1,
    })
  end
  if r0_14 < #r15_0 then
    util.LoadBtn({
      rtImg = r1_14,
      fname = r4_0("scrl_next"),
      x = 576,
      y = 560,
      func = r12_14,
      param = r0_14 + 1,
    })
  end
  return r1_14
end
local r17_0 = {
  {
    85,
    r16_0
  },
  {
    86,
    r14_0
  },
  {
    87,
    r12_0
  },
  {
    88,
    r10_0
  }
}
local function r18_0(r0_16)
  -- line: [404, 408] id: 16
  sound.PlaySE(1)
  r0_0.ViewHelp("howto", {
    r0_16.param,
    1
  })
  return true
end
local function r19_0()
  -- line: [410, 430] id: 17
  local r0_17 = display.newGroup()
  local r1_17 = nil
  local r2_17 = nil
  local r3_17 = nil
  local r4_17 = 1
  for r8_17 = 160, 400, 80 do
    r3_17 = r17_0[r4_17]
    r1_17 = r0_0.MakeTextParts(r2_0(string.format("top_menu%02d", r4_17)), 24, {
      26,
      19,
      10
    }, {
      255,
      246,
      229
    }, r3_17[1])
    r1_17:setReferencePoint(display.CenterReferencePoint)
    util.LoadBtnGroup({
      group = r1_17,
      x = 312,
      y = r8_17,
      func = r18_0,
      param = r3_17[2],
    })
    r0_17:insert(r1_17)
    r4_17 = r4_17 + 1
  end
  util.LoadBtn({
    rtImg = r0_17,
    fname = r5_0("back_"),
    x = 384,
    y = 560,
    func = r7_0,
  })
  return r0_17
end
return {
  Load = function(r0_18, r1_18)
    -- line: [432, 448] id: 18
    local r2_18 = display.newGroup()
    util.LoadParts(r2_18, r3_0("title_"), 192, 16)
    if r1_18 == nil then
      r2_18:insert(r19_0())
    else
      r2_18:insert(r1_18[1](r1_18[2]))
    end
    r0_18:insert(r2_18)
    return r2_18
  end,
  Cleanup = function()
    -- line: [450, 451] id: 19
  end,
}
