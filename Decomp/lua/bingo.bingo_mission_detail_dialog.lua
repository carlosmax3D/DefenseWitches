-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
return {
  new = function(r0_1)
    -- line: [9, 196] id: 1
    local r11_1 = nil	-- notice: implicit variable refs by block#[0]
    local r1_1 = {}
    local function r2_1()
      -- line: [15, 15] id: 2
      return "dialogBackground"
    end
    local function r3_1()
      -- line: [16, 16] id: 3
      return "dialogTouchLayer"
    end
    local function r4_1()
      -- line: [17, 17] id: 4
      return "dialogLabelGroup"
    end
    local r5_1 = require("common.base_dialog")
    local r6_1 = nil
    local r7_1 = nil
    local r8_1 = nil
    local function r9_1(r0_5, r1_5, r2_5)
      -- line: [33, 45] id: 5
      if r1_5 == true then
        r0_5.isVisible = true
        r5_1.SlideInUpEffect(r0_5[r2_1()], {
          x = r0_5[r2_1()].x,
          y = r0_5[r2_1()].y,
        }, {
          x = r0_5[r2_1()].x,
          y = r7_1.y,
        }, r2_5)
      else
        r5_1.SlideOutDownEffect(r0_5[r2_1()], {
          x = r0_5[r2_1()].x,
          y = r7_1.y * 2,
        }, function()
          -- line: [38, 43] id: 6
          r0_5.isVisible = false
          if r2_5 ~= nil then
            r2_5()
          end
        end)
      end
    end
    local function r10_1()
      -- line: [49, 57] id: 7
      r9_1(r6_1, false, function()
        -- line: [50, 56] id: 8
        display.remove(r6_1[r4_1()])
        r6_1[r4_1()] = nil
        if r8_1 ~= nil then
          r8_1()
        end
      end)
    end
    function r11_1(r0_9)
      -- line: [61, 67] id: 9
      r6_1[r2_1()]:removeEventListener("touch", r11_1)
      r10_1()
      return true
    end
    local function r12_1(r0_10)
      -- line: [71, 72] id: 10
    end
    local function r13_1(r0_11, r1_11, r2_11)
      -- line: [76, 113] id: 11
      if r6_1[r4_1()] ~= nil then
        display.remove(r6_1[r4_1()])
        r6_1[r4_1()] = nil
      end
      local r3_11 = display.newGroup()
      r0_11[r2_1()]:insert(r3_11)
      r0_11[r4_1()] = r3_11
      local r4_11 = string.gsub(_G.BingoManager.getMissionMessage(r1_11, r2_11), "\\n", " ")
      local r5_11 = _G.BingoManager.getMissionDetailMessage(r2_11)
      local r6_11 = display.newGroup()
      local r7_11 = display.newGroup()
      r3_11:insert(r6_11)
      r3_11:insert(r7_11)
      local r8_11 = util.MakeText4({
        rtImg = r6_11,
        size = 32,
        line = 22,
        align = "center",
        w = 450,
        h = 32,
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
        shadowBoldWidth = 1,
        font = native.systemFontBold,
        msg = r4_11,
      })
      local r9_11 = util.MakeText2({
        rtImg = r7_11,
        size = 24,
        line = 32,
        x = 0,
        y = 0,
        w = 784,
        h = 64,
        color = {
          255,
          255,
          255
        },
        shadow = {
          0,
          0,
          0
        },
        msg = r5_11,
      })
      r6_11:setReferencePoint(display.CenterReferencePoint)
      r7_11:setReferencePoint(display.CenterReferencePoint)
      r6_11.x = r0_11[r2_1()].width * 0.5
      r6_11.y = 70
      r7_11.x = r0_11[r2_1()].width * 0.5
      r7_11.y = r0_11[r2_1()].height * 0.5
    end
    local function r14_1(r0_12)
      -- line: [117, 144] id: 12
      r0_12.isVisible = false
      local r1_12 = r5_1.Create(r0_12, 640, 250, {
        direction = "down",
        color = {
          {
            0,
            102,
            153
          },
          {
            0,
            51,
            68
          }
        },
        alpha = 1,
      }, true, true)
      r1_12.y = _G.Height
      r0_12[r2_1()] = r1_12
      r7_1 = {
        x = _G.Width * 0.5,
        y = _G.Height * 0.5,
      }
      local r2_12 = display.newGroup()
      display.newRect(r2_12, 0, 0, _G.Width, _G.Height).alpha = 0.01
      r2_12:addEventListener("touch", r11_1)
      r0_12:insert(r2_12)
      r0_12[r3_1()] = r2_12
    end
    local function r15_1(r0_13, r1_13)
      -- line: [148, 150] id: 13
      r13_1(r6_1, r0_13, r1_13)
    end
    local function r16_1(r0_14)
      -- line: [154, 160] id: 14
      if r0_14 == nil then
        return 
      end
      r6_1 = r0_14.rootLayer
    end
    function r1_1.show(r0_16, r1_16)
      -- line: [179, 182] id: 16
      r8_1 = r1_16
      r9_1(r6_1, r0_16, nil)
    end
    function r1_1.setMissionType(r0_17, r1_17)
      -- line: [186, 188] id: 17
      r15_1(r0_17, r1_17)
    end
    (function()
      -- line: [164, 172] id: 15
      r16_1(r0_1)
      if r6_1 == nil then
        r6_1 = display.newGroup()
      end
      r14_1(r6_1)
    end)()
    return r1_1
  end,
}
