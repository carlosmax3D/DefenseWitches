-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
return {
  new = function(r0_1)
    -- line: [9, 130] id: 1
    local r1_1 = {}
    local function r2_1()
      -- line: [15, 15] id: 2
      return "dialogImage"
    end
    local r3_1 = require("common.base_dialog")
    local r4_1 = nil
    local r5_1 = nil
    local r6_1 = nil
    local r7_1 = nil
    local function r8_1(r0_3)
      -- line: [32, 32] id: 3
      return "data/" .. r0_3 .. ".png"
    end
    local function r9_1(r0_4)
      -- line: [33, 33] id: 4
      return r8_1(r0_4 .. _G.UILanguage)
    end
    local function r10_1(r0_5, r1_5, r2_5)
      -- line: [37, 48] id: 5
      if r1_5 == true then
        r3_1.SlideInUpEffect(r0_5, {
          x = r0_5.x,
          y = r0_5.y,
        }, {
          x = r0_5.x,
          y = r5_1.y,
        }, r2_5)
      else
        r3_1.SlideOutDownEffect(r0_5, {
          x = r0_5.x,
          y = r5_1.y * 2,
        }, function()
          -- line: [41, 46] id: 6
          r0_5.isVisible = false
          if r2_5 ~= nil then
            r2_5()
          end
        end)
      end
    end
    local function r11_1(r0_7, r1_7)
      -- line: [52, 63] id: 7
      if r6_1 ~= nil then
        r6_1()
      end
      r1_1.show(false, function()
        -- line: [57, 62] id: 8
        display.remove(r4_1[r2_1()])
        if r7_1 ~= nil then
          r7_1()
        end
      end)
    end
    local function r12_1(r0_9, r1_9, r2_9, r3_9)
      -- line: [67, 97] id: 9
      local r4_9 = r3_1.Create(r0_9, 620, 290, {
        direction = "down",
        color = {
          {
            102,
            170,
            204
          },
          {
            51,
            102,
            119
          }
        },
        alpha = 1,
      }, true, true)
      r4_9.y = _G.Height
      local r5_9 = display.newImage(r4_9, r8_1(r2_9), 0, 0)
      r5_9:setReferencePoint(display.CenterReferencePoint)
      r5_9.x = r4_9.width * 0.5 + (r4_9.width - r5_9.width) * 0.5 - 20
      r5_9.y = r4_9.height * 0.5
      local r6_9 = display.newGroup()
      local r7_9 = display.newImage(r6_9, "data/stage/ok_en.png", 0, 0)
      r6_9.touch = r11_1
      r6_9:addEventListener("touch", r6_9)
      r6_9:setReferencePoint(display.CenterReferencePoint)
      r6_9.x = r4_9.width * 0.5
      r6_9.y = r4_9.height - r6_9.height * 0.5 + 30
      r4_9:insert(r6_9)
      local r8_9 = util.MakeText4({
        rtImg = r4_9,
        size = 32,
        line = 22,
        align = "center",
        w = r4_9.width,
        h = r4_9.height,
        color = {
          255,
          255,
          255
        },
        shadow = {
          51,
          51,
          51
        },
        shadowBoldWidth = 2,
        font = native.systemFontBold,
        msg = r3_9,
      })
      r8_9:setReferencePoint(display.CenterReferencePoint)
      r8_9.x = r4_9.width * 0.5 - (r4_9.width - r8_9.width) * 0.5 + 32
      r8_9.y = r4_9.height * 0.5 - 20
      r0_9[r2_1()] = r4_9
      r4_9.x = r1_9.x
    end
    local function r13_1(r0_10)
      -- line: [101, 106] id: 10
      r4_1 = r0_10.parentLayer
      r5_1 = r0_10.centerPos
      r6_1 = r0_10.closeBeforeFunc
      r7_1 = r0_10.closeAfterFunc
    end
    function r1_1.show(r0_12, r1_12)
      -- line: [120, 122] id: 12
      r10_1(r4_1[r2_1()], r0_12, r1_12)
    end
    (function()
      -- line: [110, 113] id: 11
      r13_1(r0_1)
      r12_1(r4_1, r0_1.centerPos, r0_1.prizesImage, r0_1.message)
    end)()
    return r1_1
  end,
}
