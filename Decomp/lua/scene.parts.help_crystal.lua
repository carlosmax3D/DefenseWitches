-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("scene.help")
local r1_0 = "data/help/crystal"
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
  -- line: [14, 14] id: 6
  return "data/help/howto/" .. r0_6 .. ".png"
end
local function r8_0(r0_7)
  -- line: [16, 20] id: 7
  sound.PlaySE(2)
  r0_0.ViewHelp("index")
  return true
end
local function r9_0(r0_8)
  -- line: [22, 26] id: 8
  return string.gsub(db.GetMessage(r0_8), "(\\n)", function(r0_9)
    -- line: [24, 24] id: 9
    return "\n"
  end)
end
local r10_0 = {
  {
    nil,
    251
  },
  {
    253,
    254
  },
  {
    255,
    256
  },
  {
    257,
    258
  },
  {
    259,
    260
  }
}
local function r11_0(r0_10)
  -- line: [37, 131] id: 10
  local r1_10 = display.newGroup()
  local r2_10 = nil
  local r3_10 = nil
  local r4_10 = nil
  local r5_10 = nil
  local r6_10 = nil
  local r7_10 = nil
  if r0_10 == 1 then
    r3_10 = db.GetMessage(250)
  else
    r3_10 = string.format(db.GetMessage(252), r0_10 - 1)
  end
  util.LoadParts(r1_10, r7_0("title_plate"), 80, 80)
  r2_10 = util.MakeText3({
    rtImg = r1_10,
    size = 24,
    color = {
      64,
      48,
      25
    },
    shadow = {
      255,
      246,
      229
    },
    diff_x = 1,
    diff_y = 2,
    msg = r3_10,
  })
  r2_10:setReferencePoint(display.CenterReferencePoint)
  r2_10.x = 480
  r2_10.y = 112
  r1_10:insert(r2_10)
  if r0_10 == 1 then
    util.MakeText3({
      rtImg = r1_10,
      size = 20,
      x = 96,
      y = 160,
      width = 424,
      height = 144,
      color = {
        64,
        48,
        25
      },
      shadow = {
        255,
        246,
        229
      },
      diff_x = 1,
      diff_y = 2,
      msg = r9_0(251),
    })
    for r11_10 = 1, 4, 1 do
      util.LoadParts(r1_10, r7_0(string.format("number%02d", r11_10)), 92, 324 + (r11_10 - 1) * 40)
      util.MakeText3({
        rtImg = r1_10,
        size = 22,
        x = 128,
        y = 328 + (r11_10 - 1) * 40,
        color = {
          64,
          48,
          25
        },
        shadow = {
          255,
          246,
          229
        },
        diff_x = 1,
        diff_y = 2,
        msg = db.GetMessage(261 + r11_10 - 1),
      })
    end
  else
    util.LoadParts(r1_10, r7_0("number00"), 92, 156)
    util.MakeText3({
      rtImg = r1_10,
      size = 22,
      x = 128,
      y = 160,
      color = {
        64,
        48,
        25
      },
      shadow = {
        255,
        246,
        229
      },
      diff_x = 1,
      diff_y = 2,
      msg = db.GetMessage(r10_0[r0_10][1]),
    })
    r3_10 = r9_0(r10_0[r0_10][2])
    if r0_10 == 5 then
      r3_10 = string.format(r3_10, _G.RewindCrystal)
    end
    util.MakeText3({
      rtImg = r1_10,
      size = 20,
      x = 128,
      y = 200,
      width = 392,
      height = 288,
      color = {
        64,
        48,
        25
      },
      shadow = {
        255,
        246,
        229
      },
      diff_x = 1,
      diff_y = 2,
      msg = r3_10,
    })
  end
  if r0_10 == 1 then
    util.LoadParts(r1_10, r3_0("crystal_image01a_"), 528, 152)
    util.LoadParts(r1_10, r3_0("crystal_image01b_"), 528, 228)
  else
    util.LoadParts(r1_10, r3_0(string.format("crystal_image%02d_", r0_10)), 528, 152)
  end
  r4_10 = 1
  for r11_10 = 352, 576, 56 do
    if r4_10 == r0_10 then
      r7_10 = r6_0("witches_page_active")
    else
      r7_10 = r6_0("witches_page_nonactive")
    end
    util.LoadParts(r1_10, r7_10, r11_10, 516)
    r4_10 = r4_10 + 1
  end
  util.LoadBtn({
    rtImg = r1_10,
    fname = r5_0("back_"),
    x = 256,
    y = 560,
    func = r8_0,
  })
  local function r8_10(r0_11)
    -- line: [113, 119] id: 11
    local r1_11 = r0_11.param
    local r2_11 = assert
    local r3_11 = nil	-- notice: implicit variable refs by block#[4]
    if r1_11 >= 1 then
      r3_11 = r1_11 <= 5
    else
      goto label_6	-- block#2 is visited secondly
    end
    r2_11(r3_11)
    sound.PlaySE(1)
    r0_0.ViewHelp("crystal", r1_11)
    return true
  end
  if r0_10 > 1 then
    util.LoadBtn({
      rtImg = r1_10,
      fname = r4_0("scrl_previous"),
      x = 448,
      y = 560,
      func = r8_10,
      param = r0_10 - 1,
    })
  end
  if r0_10 < 5 then
    util.LoadBtn({
      rtImg = r1_10,
      fname = r4_0("scrl_next"),
      x = 576,
      y = 560,
      func = r8_10,
      param = r0_10 + 1,
    })
  end
  return r1_10
end
return {
  Load = function(r0_12, r1_12)
    -- line: [133, 147] id: 12
    local r2_12 = display.newGroup()
    util.LoadParts(r2_12, r3_0("crystal_title_"), 192, 16)
    local r3_12 = 1
    if r1_12 then
      r3_12 = r1_12
    end
    r2_12:insert(r11_0(r3_12))
    r0_12:insert(r2_12)
    return r2_12
  end,
  Cleanup = function()
    -- line: [149, 150] id: 13
  end,
}
