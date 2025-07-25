-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
return {
  new = function(r0_1)
    -- line: [31, 1090] id: 1
    local r1_1 = {}
    local r2_1 = {
      BG_IMAGE = function()
        -- line: [39, 39] id: 2
        return "window_bingo"
      end,
      SQUARES_IMAGE = function()
        -- line: [40, 40] id: 3
        return "img_bingo_panel"
      end,
      STAMP_IMAGE = function()
        -- line: [41, 41] id: 4
        return "img_bingo_panel_stamp"
      end,
    }
    local r3_1 = {
      COL = function()
        -- line: [45, 45] id: 5
        return 3
      end,
      ROW = function()
        -- line: [46, 46] id: 6
        return 3
      end,
    }
    local function r4_1()
      -- line: [49, 49] id: 7
      return "bingoCardBackground"
    end
    local function r5_1()
      -- line: [50, 50] id: 8
      return "bingoCardBackgroundGroup"
    end
    local function r6_1()
      -- line: [51, 51] id: 9
      return "bingoMissionDetailGroup"
    end
    local function r7_1()
      -- line: [52, 52] id: 10
      return "bingoCardMat"
    end
    local function r8_1()
      -- line: [53, 53] id: 11
      return "bingoCardCloseRect"
    end
    local function r9_1()
      -- line: [55, 55] id: 12
      return "backgroundImage"
    end
    local function r10_1()
      -- line: [56, 56] id: 13
      return "markedImage"
    end
    local function r11_1()
      -- line: [57, 57] id: 14
      return "stampImage"
    end
    local function r12_1()
      -- line: [58, 58] id: 15
      return "label"
    end
    local function r13_1()
      -- line: [59, 59] id: 16
      return "markedFlag"
    end
    local function r14_1()
      -- line: [60, 60] id: 17
      return "whiteMask"
    end
    local function r15_1()
      -- line: [62, 62] id: 18
      return "flashLine"
    end
    local function r16_1()
      -- line: [63, 63] id: 19
      return "flashCrash"
    end
    local function r17_1()
      -- line: [65, 65] id: 20
      return "completeImage"
    end
    local function r18_1()
      -- line: [67, 67] id: 21
      return "pixieDust"
    end
    local r19_1 = {
      NONE = function()
        -- line: [70, 70] id: 22
        return 0
      end,
      PHASE01 = function()
        -- line: [71, 71] id: 23
        return 1
      end,
      PHASE02 = function()
        -- line: [72, 72] id: 24
        return 2
      end,
      PHASE03 = function()
        -- line: [73, 73] id: 25
        return 3
      end,
      PHASE04 = function()
        -- line: [74, 74] id: 26
        return 4
      end,
      ENDED = function()
        -- line: [75, 75] id: 27
        return -1
      end,
    }
    local r20_1 = {
      NONE = function()
        -- line: [78, 78] id: 28
        return 0
      end,
      PHASE01 = function()
        -- line: [79, 79] id: 29
        return 1
      end,
      PHASE02 = function()
        -- line: [80, 80] id: 30
        return 2
      end,
      PHASE03 = function()
        -- line: [81, 81] id: 31
        return 3
      end,
      PHASE04 = function()
        -- line: [82, 82] id: 32
        return 4
      end,
      PHASE05 = function()
        -- line: [83, 83] id: 33
        return 5
      end,
      PHASE06 = function()
        -- line: [84, 84] id: 34
        return 6
      end,
      ENDED = function()
        -- line: [85, 85] id: 35
        return -1
      end,
    }
    local r21_1 = require("common.base_dialog")
    local r22_1 = require("resource.char_define")
    local r23_1 = _G.sound
    local r49_1 = nil	-- notice: implicit variable refs by block#[2]
    local r62_1 = nil	-- notice: implicit variable refs by block#[2]
    if not r23_1 then
      r23_1 = require("resource.sound")
    end
    local r24_1 = nil
    local r25_1 = nil
    local r26_1 = 0
    local r27_1 = false
    local r28_1 = nil
    local r29_1 = 0
    local r30_1 = false
    local r31_1 = nil
    local r32_1 = nil
    local r33_1 = nil
    local r34_1 = nil
    local r35_1 = nil
    local r36_1 = nil
    local r37_1 = nil
    local function r38_1(r0_36)
      -- line: [114, 114] id: 36
      return "data/bingo/" .. r0_36 .. ".png"
    end
    local function r39_1(r0_37)
      -- line: [115, 115] id: 37
      return r38_1(r0_37 .. _G.UILanguage)
    end
    local function r40_1(r0_38)
      -- line: [119, 155] id: 38
      local r1_38 = require("common.particles.prism").new
      local r2_38 = {
        parentLayer = r0_38,
        position = {
          x = 0,
          y = 0,
        },
        range = {
          width = 0,
          height = 0,
        },
        color = {
          255,
          255,
          255
        },
        size = {
          min = 10,
          max = 100,
        },
        lifeLimit = {
          min = 10,
          max = 200,
        },
        alphaRamge = {
          min = 0,
          max = 1,
        },
        generateVolume = 30,
      }
      r2_38.imageSheet = {
        fileName = "data/sprites/prism002.png",
        options = {
          width = 32,
          height = 32,
          numFrames = 1,
          sheetContentWidth = 32,
          sheetContentHeight = 32,
        },
      }
      return r1_38(r2_38)
    end
    local function r41_1(r0_39, r1_39)
      -- line: [159, 216] id: 39
      local r2_39 = require("common.particles.pixieDust")
      local r4_39 = {}
      r4_39.particleImage = {
        file = "data/sprites/ptc_star_list.png",
        options = {
          width = 64,
          height = 64,
          numFrames = 4,
          sheetContentWidth = 256,
          sheetContentHeight = 64,
        },
      }
      r4_39.generateParticle = {
        type = r2_39.GENERATE_SPRITE_TYPE.IMAGE(),
        frameNum = r1_39,
      }
      r4_39.parentObj = r0_39
      r4_39.baseX = 0
      r4_39.baseY = 0
      r4_39.startRadius = 60
      r4_39.flowX = 0
      r4_39.flowY = 0
      r4_39.particleMax = 2
      r4_39.sizeStart = 20
      r4_39.sizeEnd = 50
      r4_39.moveXStart = -60
      r4_39.moveXEnd = 60
      r4_39.moveYStart = -60
      r4_39.moveYEnd = 60
      r4_39.accelerateXStart = -100
      r4_39.accelerateXEnd = 100
      r4_39.accelerateYStart = -100
      r4_39.accelerateYEnd = 100
      r4_39.lifetimeStart = 10
      r4_39.lifetimeEnd = 12
      r4_39.alphaStepStart = 1
      r4_39.alphaStepEnd = 5
      r4_39.twistSpeedStart = 1
      r4_39.twistSpeedEnd = 10
      r4_39.twistRadiusStart = 1
      r4_39.twistRadiusEnd = 5
      r4_39.twistAngleStart = 0
      r4_39.twistAngleEnd = 359
      r4_39.twistAngleStepStart = 1
      r4_39.twistAngleStepEnd = 10
      return r2_39.new(r4_39)
    end
    local function r42_1(r0_40)
      -- line: [220, 264] id: 40
      r1_1.show(false, true, function()
        -- line: [221, 263] id: 41
        if r36_1 ~= nil then
          r36_1.clean()
          r36_1 = nil
        end
        if r24_1 ~= nil then
          display.remove(r24_1)
        end
        if r34_1 ~= nil and type(r34_1) == "table" then
          for r3_41, r4_41 in pairs(r34_1) do
            transition.cancel(r4_41)
          end
          r34_1 = nil
        else
          transition.cancel(r34_1)
          r34_1 = nil
        end
        if r35_1 ~= nil then
          timer.cancel(r35_1)
          r35_1 = nil
        end
        if r28_1 ~= nil then
          if type(r28_1) == "table" then
            for r3_41, r4_41 in pairs(r28_1) do
              r4_41.stop()
              r4_41.clean()
            end
          else
            r28_1.clean()
          end
          r28_1 = nil
        end
        if r33_1 ~= nil then
          Runtime:removeEventListener("enterFrame", r33_1)
        end
        if r0_40 ~= nil then
          r0_40()
        end
      end)
    end
    local function r43_1(r0_42)
      -- line: [271, 326] id: 42
      if r0_42 == nil then
        DebugPrint("マスグループが存在しません")
        return 
      end
      local r1_42 = true
      r30_1 = r1_42
      function r1_42(r0_43)
        -- line: [279, 323] id: 43
        if #r0_42 < r0_43 then
          r30_1 = false
          r29_1 = r20_1.PHASE02()
          return 
        end
        local r1_43 = r0_42[r0_43]
        local function r2_43(r0_44)
          -- line: [289, 301] id: 44
          r34_1 = transition.to(r0_44, {
            time = 100,
            alpha = 0,
            xScale = 1.5,
            yScale = 1.5,
            onComplete = function()
              -- line: [295, 299] id: 45
              local r0_45 = nil	-- notice: implicit variable refs by block#[0]
              r34_1 = r0_45
              display.remove(r0_44)
              r1_42(r0_43 + 1)
            end,
          })
        end
        local r3_43 = display.newImage(r1_43[r10_1()].parent, r38_1("img_bingo_panel_flash"), 0, 0)
        r1_43[r14_1()] = r3_43
        r1_43:insert(r1_43[r11_1()])
        r3_43.isVisible = false
        r3_43.alpha = 0
        r3_43.xScale = 0.6
        r3_43.yScale = 0.6
        r3_43.isVisible = true
        r34_1 = transition.to(r3_43, {
          time = 150,
          alpha = 1,
          xScale = 1,
          yScale = 1,
          onComplete = function()
            -- line: [318, 321] id: 46
            local r0_46 = nil	-- notice: implicit variable refs by block#[0]
            r34_1 = r0_46
            r2_43(r3_43)
          end,
        })
      end
      r1_42(1)
    end
    local function r44_1(r0_47)
      -- line: [330, 392] id: 47
      r30_1 = true
      local r1_47 = display.newGroup()
      local r2_47 = display.newImage(r1_47, r38_1("eff_flash_line"), 0, 0)
      r1_47:setReferencePoint(display.CenterReferencePoint)
      r1_47.x = r0_47[r4_1()].x
      r1_47.y = r0_47[r4_1()].y
      r1_47.alpha = 0
      r1_47.xScale = 1
      r1_47.yScale = 1
      local r3_47 = display.newGroup()
      local r4_47 = display.newImage(r3_47, r38_1("eff_flash_crash"), 0, 0)
      r3_47:setReferencePoint(display.CenterReferencePoint)
      r3_47.x = r0_47[r4_1()].x
      r3_47.y = r0_47[r4_1()].y
      r3_47.alpha = 0
      r3_47.xScale = 0
      r3_47.yScale = 0
      r0_47:insert(1, r1_47)
      r0_47:insert(r3_47)
      r0_47[r15_1()] = r1_47
      r0_47[r16_1()] = r3_47
      r34_1 = {}
      local r5_47 = transition.to(r1_47, {
        time = 600,
        alpha = 1,
        xScale = 10,
        yScale = 10,
        rotation = 10,
        onComplete = function()
          -- line: [364, 374] id: 48
          for r3_48, r4_48 in pairs(r34_1) do
            transition.cancel(r4_48)
          end
          r3_47.alpha = 1
          r34_1 = nil
          r30_1 = false
          r29_1 = r20_1.PHASE03()
        end,
      })
      local r6_47 = transition.to(r3_47, {
        delay = 200,
        time = 400,
        alpha = 1,
        xScale = 10,
        yScale = 10,
        rotation = -30,
        onComplete = function()
          -- line: [385, 387] id: 49
          r34_1[2] = nil
        end,
      })
      r34_1[1] = r5_47
      r34_1[2] = r6_47
    end
    local function r45_1(r0_50)
      -- line: [396, 418] id: 50
      r30_1 = true
      local r1_50 = display.newGroup()
      local r2_50 = display.newImage(r1_50, r38_1("txt_complate"), 0, 0)
      r0_50:insert(r1_50)
      r1_50.alpha = 0
      r1_50:setReferencePoint(display.CenterReferencePoint)
      r1_50.x = -r1_50.width * 0.5
      r1_50.y = r0_50[r4_1()].height * 0.5
      r0_50[r17_1()] = r1_50
      r34_1 = transition.to(r1_50, {
        time = 200,
        alpha = 1,
        x = r0_50[r4_1()].width * 0.5,
        onComplete = function()
          -- line: [412, 416] id: 51
          local r0_51 = nil	-- notice: implicit variable refs by block#[0]
          r34_1 = r0_51
          r30_1 = false
          r0_51 = r20_1.PHASE04()
          r29_1 = r0_51
        end,
      })
    end
    local function r46_1(r0_52)
      -- line: [422, 444] id: 52
      r30_1 = true
      local r1_52 = display.newImage(r0_52, r38_1("txt_complate"), 0, 0)
      r1_52.alpha = 1
      r1_52:setReferencePoint(display.CenterReferencePoint)
      r1_52.x = r0_52[r4_1()].width * 0.5
      r1_52.y = r0_52[r4_1()].height * 0.5
      r34_1 = transition.to(r1_52, {
        time = 300,
        xScale = 5,
        yScale = 5,
        alpha = 0,
        transition = easing.outQuad,
        onComplete = function()
          -- line: [437, 442] id: 53
          display.remove(r1_52)
          r34_1 = nil
          r30_1 = false
          r29_1 = r20_1.PHASE05()
        end,
      })
    end
    local function r47_1(r0_54)
      -- line: [448, 531] id: 54
      r30_1 = true
      local r1_54 = r40_1(r0_54[r17_1()])
      r1_54.position.x = r0_54[r17_1()].width * 0.5
      r1_54.position.y = r0_54[r17_1()].height * 0.5
      r1_54.range.width = r0_54[r17_1()].width
      r1_54.range.height = r0_54[r17_1()].height
      local r2_54 = 0
      local r3_54 = r0_54[r4_1()].width
      local r4_54 = r0_54[r4_1()].height
      local r5_54 = display.newGroup()
      r0_54:insert(r5_54)
      r0_54:insert(r0_54[r17_1()])
      r0_54[r18_1()] = r5_54
      r36_1 = r41_1(r5_54, 1)
      r36_1.lifetimeStart = 400
      r36_1.lifetimeEnd = 500
      r36_1.play()
      local r6_54 = timer.performWithDelay(100, function()
        -- line: [471, 480] id: 55
        r1_54.play()
        if r36_1.isPlay() == true then
          r36_1.stop()
        end
        r36_1.baseX = math.random(r3_54)
        r36_1.baseY = math.random(r4_54)
        r36_1.generateParticle.frameNum = math.floor(math.random(4))
        r36_1.play()
      end, -1)
      r34_1 = {}
      local r7_54 = transition.to(r0_54[r15_1()], {
        time = 300,
        alpha = 0,
        xScale = 15,
        yScale = 15,
        rotation = 10,
        onComplete = function()
          -- line: [491, 511] id: 56
          for r3_56, r4_56 in pairs(r34_1) do
            transition.cancel(r4_56)
          end
          r34_1 = nil
          display.remove(r0_54[r15_1()])
          if r0_54[r16_1()] ~= nil then
            display.remove(r0_54[r16_1()])
            r0_54[r16_1()] = nil
          end
          r35_1 = timer.performWithDelay(3000, function()
            -- line: [504, 510] id: 57
            timer.cancel(r6_54)
            timer.cancel(r35_1)
            r35_1 = nil
            r30_1 = false
            r29_1 = r20_1.PHASE06()
          end)
        end,
      })
      local r8_54 = transition.to(r0_54[r16_1()], {
        delay = 10,
        time = 200,
        alpha = 0,
        xScale = 15,
        yScale = 15,
        onComplete = function()
          -- line: [520, 526] id: 58
          r34_1[2] = nil
          if r0_54[r16_1()] ~= nil then
            display.remove(r0_54[r16_1()])
            r0_54[r16_1()] = nil
          end
        end,
      })
      r34_1[1] = r7_54
      r34_1[2] = r8_54
    end
    local function r48_1(r0_59, r1_59)
      -- line: [535, 570] id: 59
      r30_1 = true
      local r2_59 = r31_1
      local r3_59 = r2_59.charName .. db.GetMessage(439)
      r23_1.PlaySound("jin06", func, 3000)
      require("bingo.bingo_prizes_dialog").new({
        parentLayer = r0_59,
        centerPos = {
          x = _G.Width * 0.5,
          y = _G.Height * 0.5,
        },
        prizesImage = r2_59.image,
        message = r3_59,
        closeBeforeFunc = function()
          -- line: [549, 555] id: 60
          if r36_1 ~= nil then
            r36_1.stop()
            r36_1.clean()
            r36_1 = nil
          end
        end,
        closeAfterFunc = function()
          -- line: [556, 560] id: 61
          if r1_59 ~= nil then
            r1_59()
          end
        end,
      }).show(true, function()
        -- line: [563, 569] id: 62
        if r36_1 ~= nil then
          r36_1.stop()
        end
        r30_1 = false
        r29_1 = r20_1.ENDED()
      end)
    end
    function r49_1(r0_63, r1_63)
      -- line: [574, 606] id: 63
      local function r2_63(r0_64)
        -- line: [575, 601] id: 64
        if r30_1 == false then
          if r29_1 == r20_1.PHASE01() then
            r43_1(r0_63)
          elseif r29_1 == r20_1.PHASE02() then
            r44_1(r24_1[r5_1()])
          elseif r29_1 == r20_1.PHASE03() then
            r45_1(r24_1[r5_1()])
          elseif r29_1 == r20_1.PHASE04() then
            r46_1(r24_1[r5_1()])
          elseif r29_1 == r20_1.PHASE05() then
            r47_1(r24_1[r5_1()])
          elseif r29_1 == r20_1.PHASE06() then
            r48_1(r24_1, r1_63)
          elseif r29_1 == r20_1.ENDED() then
            Runtime:removeEventListener("enterFrame", r49_1)
            r29_1 = r20_1.NONE()
            r33_1 = nil
          end
        end
      end
      r29_1 = r20_1.PHASE01()
      Runtime:addEventListener("enterFrame", r2_63)
      r33_1 = r2_63
    end
    local function r50_1()
      -- line: [613, 622] id: 65
      for r3_65, r4_65 in pairs(r25_1) do
        if r4_65[r13_1()] == nil or r4_65[r13_1()] == false then
          return false
        end
      end
      return true
    end
    local function r51_1(r0_66)
      -- line: [626, 630] id: 66
      _G.BingoManager.setPrizesData()
      r49_1(r25_1, r0_66)
    end
    local function r52_1(r0_67)
      -- line: [637, 663] id: 67
      if r0_67 == nil then
        DebugPrint("スタンプ画像が存在しません")
        return 
      end
      r27_1 = true
      r0_67.alpha = 0
      r0_67.xScale = 1.5
      r0_67.yScale = 1.5
      r0_67.isVisible = true
      r34_1 = transition.to(r0_67, {
        delay = 1000,
        time = 600,
        xScale = 1.8,
        yScale = 1.8,
        alpha = 1,
        transition = easing.outQuad,
        onComplete = function(r0_68)
          -- line: [657, 661] id: 68
          local r1_68 = nil	-- notice: implicit variable refs by block#[0]
          r34_1 = r1_68
          r27_1 = false
          r1_68 = r19_1.PHASE02()
          r26_1 = r1_68
        end,
      })
    end
    local function r53_1(r0_69)
      -- line: [667, 695] id: 69
      if r0_69 == nil then
        DebugPrint("スタンプ画像が存在しません")
        return 
      end
      r27_1 = true
      r0_69.xScale = 1.5
      r0_69.yScale = 1.5
      r34_1 = transition.to(r0_69, {
        time = 100,
        xScale = 0.8,
        yScale = 0.8,
        transition = easing.outQuad,
        onComplete = function()
          -- line: [683, 693] id: 70
          local r0_70 = nil	-- notice: implicit variable refs by block#[0]
          r34_1 = r0_70
          r28_1 = r41_1(r0_69.parent, 2)
          r28_1.baseX = r0_69.width * 0.5 - 10
          r28_1.baseY = r0_69.height * 0.5 - 10
          r28_1.play()
          r27_1 = false
          r0_70 = r19_1.PHASE03()
          r26_1 = r0_70
        end,
      })
    end
    local function r54_1(r0_71)
      -- line: [699, 724] id: 71
      if r0_71 == nil then
        DebugPrint("スタンプ画像が存在しません")
        return 
      end
      r27_1 = true
      r0_71.xScale = 0.8
      r0_71.yScale = 0.8
      r0_71.isVisible = true
      r34_1 = transition.to(r0_71, {
        time = 300,
        xScale = 1,
        yScale = 1,
        transition = easing.inQuad,
        onComplete = function()
          -- line: [716, 722] id: 72
          local r0_72 = nil	-- notice: implicit variable refs by block#[0]
          r34_1 = r0_72
          r28_1.stop()
          r27_1 = false
          r0_72 = r19_1.PHASE04()
          r26_1 = r0_72
        end,
      })
    end
    local function r55_1(r0_73)
      -- line: [728, 750] id: 73
      if r0_73 == nil then
        DebugPrint("マーク画像が存在しません")
        return 
      end
      r27_1 = true
      r0_73.alpha = 0
      r0_73.isVisible = true
      r34_1 = transition.to(r0_73, {
        time = 100,
        alpha = 1,
        onComplete = function()
          -- line: [742, 748] id: 74
          r28_1.clean()
          r28_1 = nil
          r34_1 = nil
          r27_1 = false
          r26_1 = r19_1.ENDED()
        end,
      })
    end
    local function r56_1(r0_75, r1_75)
      -- line: [754, 791] id: 75
      local r2_75 = nil	-- notice: implicit variable refs by block#[0]
      function r2_75(r0_76)
        -- line: [755, 786] id: 76
        if r27_1 == false then
          if r26_1 == r19_1.PHASE01() then
            r52_1(r0_75[r11_1()])
          elseif r26_1 == r19_1.PHASE02() then
            r53_1(r0_75[r11_1()])
          elseif r26_1 == r19_1.PHASE03() then
            r54_1(r0_75[r11_1()])
          elseif r26_1 == r19_1.PHASE04() then
            r55_1(r0_75[r10_1()])
          elseif r26_1 == r19_1.ENDED() then
            Runtime:removeEventListener("enterFrame", r2_75)
            r26_1 = r19_1.NONE()
            r33_1 = nil
            if r50_1() == true then
              r51_1(r1_75)
            elseif r1_75 ~= nil then
              r1_75()
            end
          end
        end
      end
      r26_1 = r19_1.PHASE01()
      Runtime:addEventListener("enterFrame", r2_75)
      r33_1 = r2_75
    end
    local function r57_1(r0_77)
      -- line: [798, 810] id: 77
      if r0_77 == true then
        for r4_77, r5_77 in pairs(r25_1) do
          r5_77:addEventListener("touch")
        end
      else
        for r4_77, r5_77 in pairs(r25_1) do
          r5_77:removeEventListener("touch")
        end
      end
    end
    local function r58_1(r0_78)
      -- line: [814, 821] id: 78
      r57_1(false)
      r32_1.setMissionType(r37_1, r0_78.missionType)
      r32_1.show(true, function()
        -- line: [818, 820] id: 79
        r57_1(true)
      end)
    end
    local function r59_1(r0_80, r1_80)
      -- line: [825, 829] id: 80
      if r1_80.phase == "ended" then
        r58_1(r0_80)
      end
    end
    local function r60_1(r0_81, r1_81)
      -- line: [833, 846] id: 81
      local r2_81 = display.newRect(r0_81, 3, 3, r0_81.width - 4, r0_81.height - 6)
      r2_81:setFillColor(0, 0, 0, 102)
      r2_81.isVisible = false
      local r3_81 = display.newImage(r0_81, r38_1(r1_81), 0, 0)
      r3_81.isVisible = false
      r3_81:setReferencePoint(display.CenterReferencePoint)
      r3_81.x = r2_81.width * 0.5
      r3_81.y = r2_81.height * 0.5
      r3_81.width = r2_81.width
      r3_81.height = r2_81.height
      return r2_81, r3_81
    end
    local function r61_1(r0_82, r1_82, r2_82)
      -- line: [850, 863] id: 82
      if r0_82 < 1 or #r25_1 < r0_82 then
        DebugPrint("インデックス不正")
        return 
      end
      local r3_82 = r25_1[r0_82]
      r3_82[r10_1()], r3_82[r11_1()] = r60_1(r3_82, r1_82)
      r3_82[r13_1()] = true
      r3_82.parent:insert(r3_82)
      r56_1(r3_82, r2_82)
    end
    function r62_1(r0_83, r1_83, r2_83)
      -- line: [867, 900] id: 83
      local r3_83 = #r0_83
      if r3_83 < r1_83 then
        return 
      end
      function r3_83(r0_84)
        -- line: [873, 882] id: 84
        if r0_84.phase == "ended" then
          r24_1[r8_1()]:removeEventListener("touch", r3_83)
          display.remove(r24_1[r8_1()])
          r42_1(r2_83)
        end
        return true
      end
      local r4_83 = nil
      if r1_83 < #r0_83 then
        function r4_83()
          -- line: [886, 888] id: 85
          r62_1(r0_83, r1_83 + 1, r2_83)
        end
      elseif #r0_83 <= r1_83 then
        function r4_83()
          -- line: [890, 896] id: 86
          local r0_86 = display.newRect(r24_1, 0, 0, _G.Width, _G.Height)
          r0_86.alpha = 0.01
          r0_86:addEventListener("touch", r3_83)
          r24_1[r8_1()] = r0_86
        end
      end
      r61_1(r0_83[r1_83].missionNo, "img_bingo_panel_stamp", r4_83)
    end
    local function r63_1(r0_87, r1_87)
      -- line: [904, 906] id: 87
      return string.format("%02d/%s", r0_87, r1_87)
    end
    local function r64_1(r0_88, r1_88, r2_88, r3_88)
      -- line: [910, 972] id: 88
      local r4_88 = 0
      local r5_88 = 0
      local r6_88 = display.newGroup()
      r25_1 = {}
      for r10_88, r11_88 in pairs(r2_88) do
        local r12_88 = display.newGroup()
        local r13_88 = display.newImage(r12_88, r38_1(r63_1(r1_88, r2_1.SQUARES_IMAGE())), 0, 0)
        local r14_88 = display.newGroup()
        r12_88:insert(r14_88)
        local r15_88 = util.MakeText4({
          rtImg = r14_88,
          size = 19,
          line = 22,
          align = "center",
          w = r13_88.width,
          h = r13_88.height,
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
          msg = r3_88[r10_88].message,
        })
        local r16_88 = nil
        local r17_88 = nil
        local r18_88 = false
        if r11_88.marked == true then
          r16_88, r17_88 = r60_1(r12_88, r2_1.STAMP_IMAGE())
          r16_88.isVisible = true
          r17_88.isVisible = true
          r18_88 = true
        end
        r14_88:setReferencePoint(display.CenterReferencePoint)
        r14_88.x = r13_88.width * 0.5
        r14_88.y = r13_88.height * 0.5
        r12_88[r9_1()] = r13_88
        r12_88[r12_1()] = r14_88
        r12_88[r10_1()] = r16_88
        r12_88[r11_1()] = r17_88
        r12_88[r13_1()] = r18_88
        r6_88:insert(r12_88)
        r12_88:setReferencePoint(display.TopLeftReferencePoint)
        r12_88.x = r4_88 * r13_88.width
        r12_88.y = r5_88 * r13_88.height
        r12_88.missionType = r3_88[r10_88].missionType
        function r12_88.touch(r0_89, r1_89)
          -- line: [955, 957] id: 89
          r59_1(r0_89, r1_89)
        end
        table.insert(r25_1, r12_88)
        r4_88 = r4_88 + 1
        if r3_1.COL() <= r4_88 then
          r4_88 = 0
          r5_88 = r5_88 + 1
        end
      end
      r0_88:insert(r6_88)
      r6_88:setReferencePoint(display.CenterReferencePoint)
      r6_88.x = r0_88.width * 0.5
      r6_88.y = r0_88.height * 0.5 + 35
    end
    local function r65_1(r0_90, r1_90, r2_90, r3_90)
      -- line: [976, 1000] id: 90
      local r4_90 = display.newGroup()
      local r5_90 = util.MakeMat(r4_90)
      r0_90:insert(r4_90)
      local r6_90 = display.newGroup()
      local r7_90 = display.newImage(r6_90, r38_1(r63_1(r1_90, r2_1.BG_IMAGE())), 0, 0)
      r0_90:insert(r6_90)
      r6_90[r4_1()] = r7_90
      r0_90[r5_1()] = r6_90
      r0_90[r7_1()] = r4_90
      r64_1(r0_90[r5_1()], r1_90, r2_90, r3_90)
      r0_90[r5_1()]:setReferencePoint(display.CenterReferencePoint)
      r0_90[r5_1()].x = _G.Width * 0.5
      r0_90[r5_1()].y = _G.Height + r0_90[r5_1()].height
      local r8_90 = display.newGroup()
      r0_90:insert(r8_90)
      r0_90[r6_1()] = r8_90
      r32_1 = require("bingo.bingo_mission_detail_dialog").new({
        rootLayer = r8_90,
      })
    end
    local function r66_1(r0_91, r1_91, r2_91)
      -- line: [1004, 1019] id: 91
      if r1_91 == true then
        r21_1.FadeInMask(r24_1, {
          0,
          0,
          0,
          0.5
        }, 100, function()
          -- line: [1006, 1008] id: 92
          r21_1.SlideInUpEffect(r0_91, {
            x = r0_91.x,
            y = r0_91.y,
          }, {
            x = r0_91.x,
            y = _G.Height * 0.5,
          }, r2_91)
        end)
      else
        r21_1.SlideOutDownEffect(r0_91, {
          x = r0_91.x,
          y = _G.Height + r0_91.height,
        }, function()
          -- line: [1010, 1017] id: 93
          r0_91.isVisible = false
          r21_1.FadeOutMask(r24_1, 100, function()
            -- line: [1012, 1016] id: 94
            if r2_91 ~= nil then
              r2_91()
            end
          end)
        end)
      end
    end
    local function r67_1(r0_95, r1_95, r2_95)
      -- line: [1023, 1033] id: 95
      if r1_95 == true then
        r21_1.SlideInUpEffect(r0_95, {
          x = r0_95.x,
          y = r0_95.y,
        }, {
          x = r0_95.x,
          y = _G.Height * 0.5,
        }, r2_95)
      else
        r21_1.SlideOutDownEffect(r0_95, {
          x = r0_95.x,
          y = _G.Height + r0_95.height,
        }, function()
          -- line: [1027, 1031] id: 96
          if r2_95 ~= nil then
            r2_95()
          end
        end)
      end
    end
    local function r68_1(r0_97)
      -- line: [1038, 1041] id: 97
      r24_1 = r0_97.rootLayer
      r37_1 = r0_1.bingoCardId
    end
    function r1_1.show(r0_99, r1_99, r2_99)
      -- line: [1063, 1076] id: 99
      if r1_99 ~= nil and r1_99 == true then
        r57_1(false)
        r66_1(r24_1[r5_1()], r0_99, r2_99)
      else
        r57_1(true)
        r24_1[r7_1()].isVisible = false
        r67_1(r24_1[r5_1()], r0_99, r2_99)
      end
    end
    function r1_1.setMarkedByList(r0_100, r1_100)
      -- line: [1080, 1082] id: 100
      r62_1(r0_100, 1, r1_100)
    end
    (function()
      -- line: [1045, 1056] id: 98
      r68_1(r0_1)
      if r24_1 == nil then
        r24_1 = display.newGroup()
      end
      r31_1 = _G.BingoManager.getPrizesData(r0_1.bingoCardId)
      r65_1(r24_1, r0_1.bingoCardId, _G.BingoManager.getUserBingoData(r0_1.bingoCardId), _G.BingoManager.getBingoCardData(r0_1.bingoCardId))
    end)()
    return r1_1
  end,
}
