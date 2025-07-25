-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("tool.crystal")
local r1_0 = require("ui.review")
local r2_0 = require("ad.myads")
local r3_0 = require("json")
local r4_0 = require("logic.pay_item_data")
local r5_0 = require("resource.char_define")
local r6_0 = nil
if _G.GameMode == _G.GameModeEvo then
  r6_0 = require("common.sprite_loader").new({
    imageSheet = "common.sprites.sprite_number01",
  })
end
local r7_0 = require("evo.evo_common")
local r8_0 = {
  MedalConditionDialog = require("game.medal_condition_dialog"),
}
local r9_0 = true
local r10_0 = _G.MaxStage
local r11_0 = _G.UnlockStageCrystal
local r12_0 = {}
local r13_0 = nil
local r14_0 = nil
local r15_0 = nil
local r16_0 = nil
local r17_0 = r5_0.CharInformation
local r18_0 = _G.StageSelect
local r19_0 = nil
local r20_0 = require("anm.char_banner")
local r21_0 = {
  MedalCondition = nil,
  Hint = nil,
}
local r22_0 = nil
local r23_0 = nil
local r24_0 = nil
local r25_0 = nil
local function r26_0(r0_1)
  -- line: [63, 63] id: 1
  return "data/stage/" .. r0_1 .. ".png"
end
local function r27_0(r0_2)
  -- line: [64, 64] id: 2
  return r26_0(r0_2 .. _G.UILanguage)
end
local function r28_0(r0_3)
  -- line: [65, 65] id: 3
  return "data/map/" .. r0_3 .. ".png"
end
local function r29_0(r0_4)
  -- line: [66, 66] id: 4
  return "data/hint/" .. r0_4 .. ".png"
end
local function r30_0(r0_5)
  -- line: [67, 67] id: 5
  return r29_0(r0_5 .. _G.UILanguage)
end
local function r31_0(r0_6)
  -- line: [68, 68] id: 6
  return "data/ads" .. "/" .. r0_6 .. ".png"
end
local function r32_0(r0_7)
  -- line: [69, 69] id: 7
  return r31_0(r0_7 .. _G.UILanguage)
end
local function r33_0(r0_8)
  -- line: [70, 70] id: 8
  return "data/shop/" .. r0_8 .. ".png"
end
local function r34_0(r0_9)
  -- line: [71, 71] id: 9
  return r33_0(r0_9 .. _G.UILanguage)
end
local function r35_0(r0_10)
  -- line: [72, 72] id: 10
  return "data/game/upgrade/" .. r0_10 .. ".png"
end
local function r36_0(r0_11)
  -- line: [73, 73] id: 11
  return "data/bingo/" .. r0_11 .. ".png"
end
local function r37_0(r0_12)
  -- line: [74, 74] id: 12
  return r36_0(r0_12 .. _G.UILanguage)
end
local function r38_0(r0_13)
  -- line: [76, 81] id: 13
  bgm.FadeOut(500)
  _G.StageSelect = r0_13
  util.setActivityIndicator(true)
  util.ChangeScene({
    prev = r25_0,
    scene = "cutin",
  })
end
local function r39_0(r0_14)
  -- line: [83, 88] id: 14
  sound.PlaySE(1)
  util.ChangeScene({
    prev = r25_0,
    scene = "shop.shop_view",
    efx = "overFromBottom",
    param = {
      closeScene = "stage",
    },
  })
end
local function r40_0(r0_15)
  -- line: [90, 159] id: 15
  if _G.UserToken == nil then
    server.NetworkError(35)
    return 
  end
  local r1_15 = _G.UserID
  local r2_15 = r0_15.cid
  local r3_15 = r0_15.world
  local r4_15 = r0_15.stage
  local r5_15 = r0_15.crystal
  local r6_15 = assert
  local r7_15 = nil	-- notice: implicit variable refs by block#[6]
  if r3_15 == 1 then
    r7_15 = r4_15 ~= 1
  else
    r7_15 = r3_15 == 1	-- block#5 is visited secondly
  end
  r6_15(r7_15, debug.traceback())
  r6_15 = 38 + r4_15 - 1 + (r3_15 - 1) * 10
  r7_15 = {}
  local r8_15 = {}
  local r9_15 = nil
  table.insert(r7_15, r6_15)
  table.insert(r8_15, 1)
  if r2_15 then
    table.insert(r7_15, r0_15.itemid)
    table.insert(r8_15, 1)
    r9_15 = r17_0[r2_15][3]
  end
  local r10_15 = _G.UserID
  if _G.IsSimulator then
    db.UnlockStage(r10_15, r3_15, r4_15)
    kpi.Unlock(r6_15, r11_0)
    _G.metrics.crystal_unlock_stage(r3_15, r4_15, r11_0)
    if r2_15 then
      db.UnlockSummonData(r10_15, r2_15)
      db.UnlockL4SummonData(r10_15, r2_15)
      kpi.Unlock(r2_15, r9_15)
      _G.BingoManager.updateUserBingoData(_G.BingoManagerClass.MISSION_TYPE.UNLOCK_CHAR(), nil)
    end
    r38_0(r4_15)
  else
    util.setActivityIndicator(true)
    server.UseCoin(_G.UserToken, r7_15, r8_15, function(r0_16)
      -- line: [134, 157] id: 16
      util.setActivityIndicator(false)
      if server.CheckError(r0_16) then
        server.NetworkError()
      else
        local r1_16 = server.JsonDecode(r0_16.response)
        if r1_16.status == 0 then
          db.UnlockStage(r10_15, r3_15, r4_15)
          kpi.Unlock(r6_15, r11_0)
          _G.metrics.crystal_unlock_stage(r3_15, r4_15, r11_0)
          if r2_15 then
            db.UnlockSummonData(r10_15, r2_15)
            db.UnlockL4SummonData(r10_15, r2_15)
            kpi.Unlock(r2_15, r9_15)
            _G.BingoManager.updateUserBingoData(_G.BingoManagerClass.MISSION_TYPE.UNLOCK_CHAR(), nil)
          end
          r38_0(r4_15)
        else
          server.ServerError(r1_16.status)
        end
      end
    end)
  end
end
local function r41_0(r0_17)
  -- line: [161, 182] id: 17
  assert(r0_17, debug.traceback())
  dialog.Open(r0_17.rtImg, 13, {
    string.format(db.GetMessage(14), util.ConvertDisplayCrystal(r0_17.crystal)),
    15
  }, {
    function(r0_18)
      -- line: [169, 175] id: 18
      sound.PlaySE(1)
      dialog.Close()
      r40_0(r0_17)
      return true
    end,
    function(r0_19)
      -- line: [176, 181] id: 19
      sound.PlaySE(2)
      dialog.Close()
      return true
    end
  })
end
local function r42_0(r0_20)
  -- line: [185, 227] id: 20
  local r4_20 = 1
  r21_0 = display.newGroup()
  r21_0.MedalCondition = util.LoadBtn({
    rtImg = r21_0,
    fname = r27_0("button_medal_"),
    x = 0,
    y = 0,
    func = function(r0_21)
      -- line: [190, 199] id: 21
      if util.IsContainedTable(r0_21, "param") == false then
        return 
      end
      sound.PlaySE(2)
      r8_0.MedalConditionDialog.new({
        mapId = _G.MapSelect,
        stageId = r0_21.param.stage,
      }).Show(true)
    end,
  })
  r21_0.Hint = util.LoadBtn({
    rtImg = r21_0,
    fname = r30_0("hint2_"),
    x = r21_0.MedalCondition.width + 30,
    y = 0,
    func = function(r0_22)
      -- line: [202, 216] id: 22
      if util.IsContainedTable(r0_22, "param") == false then
        return 
      end
      sound.PlaySE(2)
      require("scene.hint").new({
        no = 3,
        change_no = 0,
        wno = _G.MapSelect,
        sno = r0_22.param.stage,
      })
    end,
  })
  r0_20:insert(r21_0)
  r21_0:setReferencePoint(display.CenterReferencePoint)
  r21_0.x = r0_20.width * 0.5 + 2
  r21_0.y = r0_20.height * 0.5 + 5
  r21_0.isVisible = false
end
local function r43_0(r0_23, r1_23, r2_23)
  -- line: [230, 254] id: 23
  if r21_0.MedalCondition == nil or r21_0.Hint == nil or r0_23 < 1 or 10 < r0_23 or r1_23 < 1 or r10_0 < r1_23 then
    return 
  end
  r21_0.isVisible = r2_23
  if hint.CheckHintRelease(r0_23, r1_23) == false then
    r21_0.Hint.disable = true
    r21_0.Hint:setFillColor(190)
    r21_0.Hint.alpha = 0.7
  else
    r21_0.Hint.disable = false
    r21_0.Hint:setFillColor(255)
    r21_0.Hint.alpha = 1
  end
  r21_0.MedalCondition.param = {
    stage = r1_23,
  }
  r21_0.Hint.param = {
    stage = r1_23,
  }
end
local function r44_0(r0_24)
  -- line: [256, 273] id: 24
  local r1_24 = assert
  local r2_24 = r0_24
  if r2_24 then
    if r0_24 >= 1 then
      r2_24 = r0_24 <= 10
    else
      r2_24 = r0_24 >= 1	-- block#3 is visited secondly
    end
  end
  r1_24(r2_24, debug.traceback())
  r1_24 = r12_0[r0_24]
  for r5_24, r6_24 in pairs(r14_0) do
    r6_24.isVisible = r5_24 == r0_24
  end
  for r5_24, r6_24 in pairs(r15_0) do
    r6_24.isVisible = r5_24 == r0_24
  end
  if r1_24.unlock == nil and r1_24.disable == false then
    r43_0(_G.MapSelect, r0_24, true)
  end
end
local function r45_0(r0_25, r1_25, r2_25)
  -- line: [275, 298] id: 25
  if r2_25 == nil then
    r2_25 = easing.linear
  end
  r43_0(_G.MapSelect, r1_25, false)
  for r6_25, r7_25 in pairs(r14_0) do
    r7_25.isVisible = false
  end
  for r6_25, r7_25 in pairs(r15_0) do
    r7_25.isVisible = false
  end
  if r13_0.tween then
    transition.cancel(r13_0.tween)
  end
  r13_0.tween = transition.to(r13_0, {
    time = r0_25,
    x = 320 + -320 * r1_25 + 320,
    transition = r2_25,
    onComplete = function(r0_26)
      -- line: [297, 297] id: 26
      r44_0(r1_25)
    end,
  })
end
local function r46_0(r0_27)
  -- line: [301, 310] id: 27
  local r1_27 = _G.UserID
  local r2_27 = db.GetStageInfo(r1_27, r0_27)
  if #r2_27 < 1 then
    db.InitStageInfo(r1_27, r0_27)
    db.UnlockStage(r1_27, r0_27, 1)
    r2_27 = db.GetStageInfo(r1_27, r0_27)
  end
  return r2_27
end
local function r47_0(r0_28)
  -- line: [313, 315] id: 28
  return db.LoadStageClearCountForMap(r0_28)
end
local function r48_0(r0_29, r1_29)
  -- line: [318, 503] id: 29
  local r2_29 = display.newGroup()
  local r3_29 = r1_29.top or 0
  local r4_29 = r1_29.bottom or _G.Height
  local r5_29 = r1_29.left or 0
  local r6_29 = r1_29.right or _G.Width
  local r7_29 = r6_29 - r5_29
  local r8_29 = display.newRect(r2_29, 0, 0, r7_29, r4_29 - r3_29)
  r8_29:setFillColor(0, 0, 0)
  r8_29.alpha = 0.01
  r0_29:insert(r2_29)
  r2_29:setReferencePoint(display.TopLeftReferencePoint)
  r2_29.x = r5_29
  r2_29.y = r3_29
  r2_29.left = r5_29
  r2_29.top = r3_29
  r2_29.right = r6_29
  r2_29.bottom = r4_29
  local function r9_29(r0_30, r1_30)
    -- line: [344, 358] id: 30
    for r6_30, r7_30 in pairs(r12_0) do
      local r2_30 = r7_30.stageBounds
      if r2_30.xMin <= r0_30 and r0_30 <= r2_30.xMax and r2_30.yMin <= r1_30 and r1_30 <= r2_30.yMax then
        if r7_30.disable then
          return nil
        else
          return r7_30.id, r7_30
        end
      end
    end
    return nil
  end
  local function r11_29(r0_32)
    -- line: [430, 454] id: 32
    r0_32.velocity = 0
    events.Disable(r0_32.ev, true)
    local r1_32 = nil
    local r2_32 = nil
    local r3_32 = nil
    local r4_32 = nil
    for r8_32, r9_32 in pairs(r12_0) do
      r1_32 = r0_32.x + r9_32.x + r9_32.width / 2
      r4_32 = _G.Width / 2 - r1_32
      if r2_32 == nil or math.abs(r4_32) < math.abs(r2_32) then
        r2_32 = r4_32
        r3_32 = r9_32
      end
    end
    if r3_32 then
      r18_0 = r3_32.id
      r45_0(math.abs(r2_32), r3_32.id)
    else
      r18_0 = nil
      DebugPrint("not found btn: error")
    end
  end
  r2_29.ev = events.Register(function(r0_33, r1_33, r2_33)
    -- line: [456, 480] id: 33
    local r4_33 = 0.9
    local r5_33 = r0_33.ev.time - r1_33.lastTime
    r1_33.lastTime = r1_33.lastTime + r5_33
    if math.abs(r1_33.velocity) < 0.1 then
      r11_29(r1_33)
    end
    r1_33.velocity = r1_33.velocity * r4_33
    r1_33.x = math.floor(r1_33.x + r1_33.velocity * r5_33)
    local r7_33 = _G.Width - r1_33.width
    if r1_33.left < r1_33.x then
      r11_29(r1_33)
    elseif r1_33.x < r7_33 and r7_33 < 0 then
      r11_29(r1_33)
    elseif r1_33.x < r7_33 then
      r11_29(r1_33)
    end
    return true
  end, r2_29, 0, true)
  r2_29.vev = events.Register(function(r0_34, r1_34, r2_34)
    -- line: [482, 495] id: 34
    local r4_34 = r0_34.ev.time - r1_34.prevTime
    r1_34.prevTime = r1_34.prevTime + r4_34
    if r1_34.prevX then
      if r4_34 > 0 then
        r1_34.velocity = (r1_34.x - r1_34.prevX) / r4_34
      else
        r1_34.velocity = 0
      end
    end
    r1_34.prevX = r1_34.x
  end, r2_29, 0, true)
  function r2_29.touch(r0_31, r1_31)
    -- line: [360, 428] id: 31
    local r2_31 = r1_31.phase
    if r2_31 == "began" then
      r0_31.startPos = r1_31.x
      r0_31.prevPos = r1_31.x
      r0_31.delta = 0
      r0_31.velocity = 0
      if r0_31.tween then
        transition.cancel(r0_31.tween)
      end
      r0_31.start_btn, r0_31.select_btn = r9_29(r1_31.x, r1_31.y)
      events.Disable(r0_31.ev, true)
      r0_31.prevTime = 0
      r0_31.prevX = 0
      events.Disable(r0_31.vev, false)
      if r18_0 ~= nil then
        r43_0(_G.MapSelect, r18_0, false)
      else
        r43_0(_G.MapSelect, 1, false)
      end
      display.getCurrentStage():setFocus(r0_31)
      r0_31.isFocus = true
    elseif r0_31.isFocus then
      if r2_31 == "moved" then
        local r3_31 = _G.Width - r7_29
        r0_31.delta = r1_31.x - r0_31.prevPos
        r0_31.prevPos = r1_31.x
        if r0_31.left < r0_31.x or r0_31.x < r3_31 then
          r0_31.x = r0_31.x + r0_31.delta / 2
        else
          r0_31.x = r0_31.x + r0_31.delta
        end
        for r7_31, r8_31 in pairs(r14_0) do
          r8_31.isVisible = false
        end
        for r7_31, r8_31 in pairs(r15_0) do
          r8_31.isVisible = false
        end
      elseif r2_31 == "ended" or r2_31 == "cancelled" then
        r0_31.lastTime = r1_31.time
        events.Disable(r0_31.ev, false)
        events.Disable(r0_31.vev, true)
        display.getCurrentStage():setFocus(nil)
        r0_31.isFocus = false
        local r3_31 = r0_31.delta
        if -1 <= r3_31 and r3_31 <= 1 then
          local r4_31, r5_31 = r9_29(r1_31.x, r1_31.y)
          if r4_31 and r4_31 == r0_31.start_btn then
            if r5_31.unlock then
              r41_0(r5_31.unlock)
            else
              sound.PlaySE(1)
              r38_0(r4_31)
            end
          end
        end
      end
    end
    return true
  end
  r2_29:addEventListener("touch", r2_29)
  return r2_29
end
local function r49_0(r0_35, r1_35)
  -- line: [505, 513] id: 35
  local r2_35 = r26_0(string.format("ws%02dbg", r1_35))
  for r6_35 = 0, _G.Height, 512 do
    for r10_35 = 0, _G.Width, 512 do
      util.LoadParts(r0_35, r2_35, r10_35, r6_35)
    end
  end
end
local function r50_0(r0_36)
  -- line: [515, 524] id: 36
  local r2_36 = cdn.NewImage(r0_36, r26_0("shadow"), 0, 0, true)
  r2_36:setReferencePoint(display.CenterReferencePoint)
  r2_36.x = 480
  r2_36.y = 368
  r2_36:scale(15, 1.5)
  r2_36.alpha = 0.75
end
local function r51_0(r0_37, r1_37)
  -- line: [526, 543] id: 37
  local r2_37 = display.newGroup()
  local r3_37 = util.MakeText3({
    rtImg = r2_37,
    size = 12,
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
    diff_x = 1,
    diff_y = 2,
    msg = r0_37,
  })
  local r4_37 = util.MakeText3({
    rtImg = r2_37,
    size = 12,
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
    diff_x = 1,
    diff_y = 2,
    msg = ":",
  })
  r4_37:setReferencePoint(display.TopLeftReferencePoint)
  r4_37.x = 30
  local r5_37 = util.MakeText3({
    rtImg = r2_37,
    size = 12,
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
    diff_x = 1,
    diff_y = 2,
    msg = string.format("%d", r1_37),
  })
  r5_37:setReferencePoint(display.TopRightReferencePoint)
  r5_37.x = 110
  return r2_37
end
local function r52_0(r0_38)
  -- line: [545, 576] id: 38
  local r1_38 = _G.UserID
  local r2_38 = db.GetScore(r1_38, r0_38, 0)
  if r2_38 == nil then
    r2_38 = {}
  end
  local r3_38 = db.GetScore(r1_38, r0_38, 1)
  if r3_38 == nil then
    r3_38 = {}
  end
  r14_0 = {}
  local r4_38 = nil
  local r5_38 = nil
  local r6_38 = nil
  for r10_38 = 1, r10_0, 1 do
    r6_38 = display.newGroup()
    r4_38 = r3_38[r10_38]
    if r4_38 == nil then
      r4_38 = 0
    end
    r5_38 = r51_0("STD", r4_38)
    r6_38:insert(r5_38)
    r5_38:setReferencePoint(display.TopLeftReferencePoint)
    r5_38.x = 0
    r5_38.y = 0
    r4_38 = r2_38[r10_38]
    if r4_38 == nil then
      r4_38 = 0
    end
    r5_38 = r51_0("ADV", r4_38)
    r6_38:insert(r5_38)
    r5_38:setReferencePoint(display.TopLeftReferencePoint)
    r5_38.x = 0
    r5_38.y = 13
    r6_38.isVisible = false
    r14_0[r10_38] = r6_38
  end
end
local function r53_0(r0_39, r1_39)
  -- line: [579, 590] id: 39
  if r1_39 == nil or r1_39 < 1 or #r14_0 < r1_39 then
    return 
  end
  local r2_39 = r14_0[r1_39]
  r2_39:setReferencePoint(display.TopCenterReferencePoint)
  r2_39.x = r0_39.width * 0.5
  r2_39.y = r0_39.y + 50
  r0_39:insert(r2_39)
end
local function r54_0(r0_40, r1_40)
  -- line: [592, 621] id: 40
  _G.MapDB = system.pathForFile(string.format("data/map/%02d/%03d.sqlite", _G.MapSelect, r1_40), system.ResourceDirectory)
  local r2_40 = db.GetSpecialAchievement()
  local r3_40 = db.GetMessage(205) .. " : "
  local r4_40 = {}
  for r8_40, r9_40 in pairs(r2_40) do
    if r9_40 == 1 then
      table.insert(r4_40, db.GetMessage(60 + r8_40 - 1) .. "L1-3")
    elseif r9_40 == 2 then
      table.insert(r4_40, db.GetMessage(60 + r8_40 - 1) .. "L1-4")
    end
  end
  if #r4_40 > 0 then
    r3_40 = r3_40 .. table.concat(r4_40, " | ")
  end
  local r5_40 = display.newGroup()
  local r6_40 = util.MakeText3({
    rtImg = r5_40,
    size = 16,
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
    diff_x = 1,
    diff_y = 2,
    msg = r3_40,
    width = 352,
    height = 0,
  })
  r0_40:insert(r5_40)
  return r5_40
end
local r55_0 = {
  "normal01_special",
  "normal02_gold",
  "normal03_silver",
  "normal04_bronze",
  "normal05_hp1",
  "normal06_ex1",
  "normal07_ex2",
  "normal08_ex3"
}
local function r56_0(r0_41, r1_41, r2_41, r3_41)
  -- line: [634, 666] id: 41
  local r4_41 = _G.UserID
  r15_0 = {}
  local r5_41 = nil
  local r6_41 = nil
  local r7_41 = nil
  local r8_41 = 1
  for r12_41 = 1, r10_0, 1 do
    r8_41 = r12_41
    r5_41 = display.newGroup()
    r7_41 = 0
    for r16_41 = 1, #r3_41, 1 do
      local r17_41 = r3_41[r16_41][r12_41]
      if r17_41 == nil then
        r17_41 = false
      end
      if r17_41 then
        r6_41 = cdn.NewImage(r5_41, r26_0(r55_0[r16_41]), 0, 0, nil)
      else
        r6_41 = cdn.NewImage(r5_41, r26_0(r55_0[r16_41] .. "_dis"), 0, 0, nil)
      end
      r6_41:setReferencePoint(display.TopLeftReferencePoint)
      r6_41.x = r7_41
      r6_41.y = 0
      r7_41 = r7_41 + 64
    end
    r0_41:insert(r5_41)
    r5_41.isVisible = false
    r5_41:setReferencePoint(display.CenterReferencePoint)
    r5_41.x = r0_41.width * 0.5
    r5_41.y = r0_41.height * 0.5 - 1
    r15_0[r12_41] = r5_41
  end
end
local r57_0 = "data/help/witches/witches_crystal.png"
local r58_0 = "data/game/upgrade/upgrade03.png"
local function r59_0(r0_42, r1_42, r2_42)
  -- line: [670, 781] id: 42
  local r9_42, r10_42 = db.LoadSummonData(_G.UserID)
  local r3_42 = r11_0
  local r6_42 = {
    itemid = nil,
  }
  local r4_42 = nil
  local r8_42 = nil
  local r5_42 = false
  for r14_42, r15_42 in pairs(r17_0) do
    if r15_42[5] == r1_42 and r15_42[6] == r2_42 then
      r8_42 = r14_42
      r4_42 = r14_42
      r5_42 = false
      if r9_42[r14_42] ~= 3 then
        r4_42 = nil
      else
        r3_42 = r3_42 + r15_42[3]
      end
      r6_42.itemid = r15_42[1]
      break
    elseif r15_42[7] == r1_42 and r15_42[8] == r2_42 then
      r8_42 = r14_42
      r4_42 = r14_42
      r5_42 = true
      r6_42.itemid = r15_42[1]
      break
    end
  end
  local r11_42 = 0
  local r12_42 = display.newGroup()
  local r7_42 = util.LoadParts(r12_42, r57_0, 0, 0)
  r7_42:setReferencePoint(display.CenterLeftReferencePoint)
  r7_42.x = 0
  r7_42.y = 16
  r11_42 = r7_42.width
  r7_42 = util.MakeText3({
    rtImg = r12_42,
    size = 30,
    color = {
      255,
      225,
      76
    },
    shadow = {
      0,
      0,
      0
    },
    diff_x = 1,
    diff_y = 2,
    msg = tostring(util.ConvertDisplayCrystal(r3_42)),
  })
  r7_42:setReferencePoint(display.CenterLeftReferencePoint)
  r7_42.x = r11_42
  r7_42.y = 16
  r0_42:insert(r12_42)
  r12_42:setReferencePoint(display.TopCenterReferencePoint)
  r12_42.x = 160
  r12_42.y = 160
  if r8_42 then
    util.LoadParts(r0_42, r28_0(string.format("thumbnail_chara%02d", r8_42)), 210, 80)
    if r5_42 then
      util.LoadParts(r0_42, r58_0, 252, 64)
    end
  end
  r11_42 = 0
  r12_42 = display.newGroup()
  local r13_42 = string.format("STAGE%d", r2_42)
  if r4_42 then
    r13_42 = r13_42 .. string.format(" & %s", db.GetMessage(59 + r4_42))
    if r5_42 then
      r13_42 = r13_42 .. "Lv4"
    end
  end
  r7_42 = util.MakeText3({
    rtImg = r12_42,
    size = 21,
    color = {
      255,
      255,
      51
    },
    shadow = {
      0,
      0,
      0
    },
    diff_x = 1,
    diff_y = 2,
    msg = "\"" .. r13_42 .. "\"",
  })
  r7_42:setReferencePoint(display.CenterLeftReferencePoint)
  r7_42.x = 0
  r7_42.y = 52
  r11_42 = r11_42 + r7_42.width
  r7_42 = util.LoadParts(r12_42, r57_0, 0, 0)
  r7_42:setReferencePoint(display.CenterLeftReferencePoint)
  r7_42.x = r11_42
  r7_42.y = 52
  r11_42 = r11_42 + r7_42.width
  r7_42 = util.MakeText3({
    rtImg = r12_42,
    size = 21,
    color = {
      255,
      225,
      76
    },
    shadow = {
      0,
      0,
      0
    },
    diff_x = 1,
    diff_y = 2,
    msg = tostring(util.ConvertDisplayCrystal(r3_42)),
  })
  r7_42:setReferencePoint(display.CenterLeftReferencePoint)
  r7_42.x = r11_42
  r7_42.y = 52
  r11_42 = r11_42 + r7_42.width
  r7_42 = util.MakeText3({
    rtImg = r12_42,
    size = 21,
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
    diff_x = 1,
    diff_y = 2,
    msg = db.GetMessage(245),
  })
  r7_42:setReferencePoint(display.CenterLeftReferencePoint)
  r7_42.x = r11_42
  r7_42.y = 52
  r6_42.crystal = r3_42
  r6_42.msg = r12_42
  r6_42.cid = r4_42
  r6_42.l4 = r5_42
  r6_42.world = r1_42
  r6_42.stage = r2_42
  return r6_42
end
local function r60_0(r0_43, r1_43, r2_43)
  -- line: [784, 808] id: 43
  local r3_43 = nil
  local r4_43 = false
  for r8_43, r9_43 in pairs(r17_0) do
    if r9_43[5] == r1_43 and r9_43[6] == r2_43 then
      r3_43 = r8_43
      r4_43 = false
      break
    elseif r9_43[7] == r1_43 and r9_43[8] == r2_43 then
      r3_43 = r8_43
      r4_43 = true
      break
    end
  end
  if r3_43 then
    util.LoadParts(r0_43, r28_0(string.format("thumbnail_chara%02d", r3_43)), 210, 80)
    if r4_43 then
      util.LoadParts(r0_43, r58_0, 252, 64)
    end
  end
end
local function r61_0(r0_44)
  -- line: [810, 818] id: 44
  sound.PlaySE(1)
  util.ChangeScene({
    prev = r25_0,
    scene = "shop.shop_view",
    efx = "overFromBottom",
    param = {
      closeScene = "stage",
    },
  })
end
local function r62_0(r0_45, r1_45, r2_45, r3_45, r4_45)
  -- line: [821, 847] id: 45
  local r5_45 = display.newGroup()
  local r6_45 = r1_45
  local r7_45 = 16
  local r8_45 = string.len(tostring(r1_45)) * r7_45
  repeat
    local r9_45 = r6_45 % 10
    r6_45 = math.floor(r6_45 * 0.1)
    if r2_45 == "a" then
      r6_0.CreateImage(r5_45, r6_0.sequenceNames.MpNumberA, r6_0.frameDefines.MpNumberAStart + r9_45, r8_45, 0)
    else
      r6_0.CreateImage(r5_45, r6_0.sequenceNames.MpNumberB, r6_0.frameDefines.MpNumberBStart + r9_45, r8_45, 0)
    end
    r8_45 = r8_45 - r7_45
  until r6_45 <= 0
  r5_45:setReferencePoint(display.TopLeftReferencePoint)
  r5_45.x = r3_45
  r5_45.y = r4_45
  r5_45.isVisible = true
  r0_45:insert(r5_45)
  return r5_45
end
local function r63_0(r0_46)
  -- line: [852, 880] id: 46
  if r22_0 == nil or r23_0 == nil or OrbManager == nil then
    DebugPrint("オーブ情報領域未設定")
    return 
  end
  r7_0.show_num_images({
    rtImg = r22_0,
    num = OrbManager.GetRemainOrb(),
  })
  r7_0.show_num_images({
    rtImg = r23_0,
    num = OrbManager.GetMaxOrb(),
  })
  if r24_0 ~= nil and r24_0.timeString ~= nil then
    display.remove(r24_0.timeString)
    r24_0.timeString = nil
  end
  if r24_0 ~= nil then
    local r1_46 = util.MakeText3({
      rtImg = r24_0,
      size = 16,
      color = {
        255,
        255,
        255
      },
      shadow = {
        170,
        170,
        170
      },
      diff_x = 0,
      diff_y = 0,
      msg = r0_46,
    })
    r24_0:insert(r1_46)
    r24_0.timeString = r1_46
  end
end
local function r64_0()
  -- line: [885, 892] id: 47
  OrbManager.ShowOrbMaxUpDialog(function()
    -- line: [886, 891] id: 48
    OrbManager.GetFormatFullRecoveryTime(function(r0_49)
      -- line: [888, 890] id: 49
      r63_0(r0_49)
    end)
  end)
end
local function r65_0(r0_50)
  -- line: [897, 928] id: 50
  if r0_50.isError then
    return 
  end
  local r1_50 = r0_50.response
  if r1_50 == nil then
    return 
  end
  local r2_50 = r3_0.decode(r1_50)
  if r2_50.status == 1 then
    if type(r2_50.result) == "table" then
      local r3_50 = require("login.login_bonus_popup_dialog").new(r2_50.result)
      if r3_50 and r3_50.isLoginBonusPopup == false then
        r3_50.show()
      end
    end
    if r2_50.is_next ~= nil and r2_50.is_next == 1 then
      notification.SetLoginBonusNotification()
    end
  elseif r2_50.status == 2 then
    db.SaveLoginBonusReciveData({
      uid = _G.UserInquiryID,
      result = 1,
    })
  end
end
local function r66_0()
  -- line: [933, 939] id: 51
end
local function r67_0(r0_52)
  -- line: [943, 945] id: 52
  util.ChangeScene({
    scene = "bingo.bingo_card_viewer",
    efx = "fade",
    param = {
      back = "stage",
    },
  })
end
function r25_0()
  -- line: [1392, 1412] id: 68
  if r24_0 ~= nil then
    display.remove(r24_0)
    r24_0 = nil
  end
  if r13_0 then
    if r13_0.tween then
      transition.cancel(r13_0.tween)
    end
    if r13_0.ev then
      events.Delete(r13_0.ev)
    end
    if r13_0.vev then
      events.Delete(r13_0.vev)
    end
    r13_0 = nil
  end
  events.DeleteNamespace("stage")
end
return {
  new = function(r0_53)
    -- line: [947, 1390] id: 53
    events.SetNamespace("stage")
    if r18_0 == nil or r18_0 < 1 or r10_0 < r18_0 then
      r18_0 = 1
    end
    hint.Init()
    r1_0.Init()
    local function r1_53(r0_54, r1_54, r2_54, r3_54)
      -- line: [962, 969] id: 54
      local r4_54 = cdn.NewImage(r0_54, r26_0(string.format("stage_number%02d", r3_54)), 0, 0, true)
      r4_54:setReferencePoint(display.TopLeftReferencePoint)
      r4_54.x = r1_54
      r4_54.y = r2_54
    end
    local function r2_53(r0_55, r1_55, r2_55, r3_55)
      -- line: [971, 977] id: 55
      r1_53(r0_55, r2_55, r3_55, math.floor(r1_55 / 100))
      r2_55 = r2_55 + 18
      r1_53(r0_55, r2_55, r3_55, math.floor(r1_55 / 10) % 10)
      r2_55 = r2_55 + 18
      r1_53(r0_55, r2_55, r3_55, r1_55 % 10)
    end
    local r3_53 = display.newGroup()
    local r4_53 = util.MakeGroup(r3_53)
    local r5_53 = util.MakeGroup(r3_53)
    local r6_53 = util.MakeGroup(r3_53)
    local r7_53 = util.MakeGroup(r3_53)
    util.MakeFrame(r3_53, true, function(r0_56)
      -- line: [986, 993] id: 56
      if r0_56 == 0 then
        util.LoadParts(r7_53, string.format("data/side/side_world%02d.png", _G.MapSelect), -176, 0)
      else
        util.LoadParts(r7_53, string.format("data/side/side_world%02d.png", _G.MapSelect), -176, 0)
      end
    end)
    local r9_53 = _G.MapSelect
    r49_0(r4_53, r9_53)
    util.LoadParts(r4_53, r26_0("stage_select_logo"), 192, -16)
    util.LoadParts(r4_53, r27_0(string.format("map_title%02d", r9_53)), 280, 84)
    local r10_53 = nil
    local r11_53 = {}
    local r12_53 = nil
    for r16_53 = 1, #r55_0, 1 do
      r12_53 = db.LoadAchievement(_G.UserID, r9_53, r16_53)
      if r12_53 == nil then
        r12_53 = {}
      end
      r11_53[r16_53] = r12_53
    end
    r10_53 = display.newGroup()
    util.LoadParts(r10_53, r28_0("plate_score"), 0, 0)
    r56_0(r10_53, r9_53, r5_53, r11_53)
    r4_53:insert(r10_53)
    r10_53.x = 330
    r10_53.y = 132
    r52_0(r9_53)
    r50_0(r4_53)
    r12_0 = {}
    local r13_53 = r48_0(r4_53, {
      left = 320,
      top = 0,
      right = 320 + 320 * r10_0,
      bottom = _G.Height,
    })
    r13_0 = r13_53
    function r13_53(r0_57)
      -- line: [1030, 1139] id: 57
      Runtime:removeEventListener("enterFrame", r13_53)
      local r1_57 = r46_0(r9_53)
      local r2_57 = r47_0(r9_53)
      local r3_57 = 0
      local r4_57 = 228
      local r5_57 = nil
      local r6_57 = true
      local r7_57 = 0
      local r8_57 = nil
      for r12_57 = 1, r10_0, 1 do
        r8_57 = nil
        local r13_57 = display.newGroup()
        cdn.NewImage(r13_57, string.format("data/map/%02d/thumb%03d.jpg", r9_53, r12_57 - 1), 16, 16, true)
        if r1_57[r12_57] == 0 then
          cdn.NewImage(r13_57, r26_0("stage_thumbnail_frame"), 0, 0, true)
          cdn.NewImage(r13_57, r26_0("stage_number_base"), 88, 18, true)
          r2_53(r13_57, r12_57, 170, 18)
          r53_0(r13_57, r12_57)
        else
          local r14_57 = nil
          local r15_57 = nil
          if r6_57 then
            cdn.NewImage(r13_57, r26_0("stage_thumbnail_purchase"), 0, 0, true)
            r8_57 = r59_0(r13_57, r9_53, r12_57)
            r8_57.rtImg = r6_53
            r14_57 = r8_57.msg
            r14_57.x = 360
            r15_57 = util.MakeText3({
              rtImg = r4_53,
              size = 18,
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
              width = 512,
              height = 48,
              diff_x = 1,
              diff_y = 2,
              msg = db.GetMessage(246),
            })
          else
            cdn.NewImage(r13_57, r26_0("stage_thumbnail_locked"), 0, 0, true)
            r60_0(r13_57, r9_53, r12_57)
            r14_57 = util.MakeText3({
              rtImg = r4_53,
              size = 21,
              color = {
                255,
                255,
                51
              },
              shadow = {
                0,
                0,
                0
              },
              diff_x = 1,
              diff_y = 2,
              msg = string.format("STAGE%d", r12_57),
            })
            r14_57.x = 440
            r15_57 = util.MakeText3({
              rtImg = r4_53,
              size = 18,
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
              diff_x = 1,
              diff_y = 2,
              msg = db.GetMessage(247),
            })
          end
          if r14_0[r12_57] then
            display.remove(r14_0[r12_57])
          end
          r4_53:insert(r14_57)
          r14_57:setReferencePoint(display.CenterLeftReferencePoint)
          r14_57.y = 180
          r14_0[r12_57] = r14_57
          if r15_0[r12_57] then
            display.remove(r15_0[r12_57])
          end
          r4_53:insert(r15_57)
          r15_57:setReferencePoint(display.CenterReferencePoint)
          r15_57.x = 480
          r15_57.y = 505
          r15_0[r12_57] = r15_57
        end
        r13_57:setReferencePoint(display.TopLeftReferencePoint)
        r13_57.x = r3_57
        r13_57.y = r4_57
        r13_57.id = r12_57
        r13_57.disable = true
        r13_57.unlock = nil
        if r1_57[r12_57] == 0 then
          r13_57.disable = false
        elseif r6_57 then
          r6_57 = false
          r13_57.disable = false
          r13_57.unlock = r8_57
        end
        if r2_57 ~= nil and r2_57[r12_57].clearCount ~= nil and 0 < r2_57[r12_57].clearCount then
          local r14_57 = util.LoadParts(r13_57, r26_0("icon_exp_get"), 0, 0)
          r14_57:setReferencePoint(display.BottomRightReferencePoint)
          r14_57.x = r13_57.width - 20
          r14_57.y = r13_57.height - 25
        end
        table.insert(r12_0, r13_57)
        r13_0:insert(r13_57)
        r3_57 = r3_57 + 320
      end
      util.setActivityIndicator(false)
      if 1 < _G.StageSelect and _G.StageSelect <= r10_0 then
        r45_0(500, _G.StageSelect, easing.outQuad)
      else
        r44_0(1)
      end
    end
    r10_53 = display.newGroup()
    util.LoadParts(r10_53, r28_0("plate_achievemet"), 0, 0)
    r42_0(r10_53, r9_53)
    r4_53:insert(r10_53)
    r10_53.x = 200
    r10_53.y = 444
    local function r14_53(r0_58)
      -- line: [1150, 1157] id: 58
      bgm.FadeOut(500)
      sound.PlaySE(2)
      _G.StageSelect = 1
      util.ChangeScene({
        prev = r25_0,
        scene = "title",
        efx = "moveFromLeft",
      })
      return false
    end
    local function r16_53(r0_60)
      -- line: [1168, 1174] id: 60
      sound.PlaySE(1)
      r13_0.velocity = 0
      r45_0(500, 1, easing.outQuad)
      r18_0 = 1
      return true
    end
    local function r17_53(r0_61)
      -- line: [1176, 1182] id: 61
      sound.PlaySE(1)
      r13_0.velocity = 0
      r45_0(500, r10_0, easing.outQuad)
      r18_0 = r10_0
      return true
    end
    local function r18_53(r0_62)
      -- line: [1184, 1211] id: 62
      sound.PlaySE(1)
      if r13_0.tween then
        transition.cancel(r13_0.tween)
      end
      r13_0.velocity = 0
      local r1_62 = nil
      local r2_62 = nil
      local r3_62 = nil
      local r4_62 = nil
      for r8_62, r9_62 in pairs(r12_0) do
        r1_62 = r13_0.x + r9_62.x + r9_62.width / 2
        r2_62 = _G.Width / 2 - r1_62
        if r3_62 == nil or math.abs(r2_62) < math.abs(r3_62) then
          r3_62 = r2_62
          r4_62 = r9_62
        end
      end
      local r5_62 = r4_62.id + r0_62
      if 1 <= r5_62 and r5_62 <= r10_0 then
        r45_0(500, r5_62, easing.outQuad)
        r18_0 = r5_62
      end
      return true
    end
    local function r19_53(r0_63)
      -- line: [1213, 1215] id: 63
      return r18_53(-1)
    end
    local function r20_53(r0_64)
      -- line: [1217, 1219] id: 64
      return r18_53(1)
    end
    local function r21_53(r0_65)
      -- line: [1221, 1234] id: 65
      if r18_0 == nil or r18_0 <= 0 then
        r18_0 = 1
      end
      if r12_0[r18_0] ~= nil and r12_0[r18_0].disable == false then
        if r12_0[r18_0].unlock then
          r41_0(r12_0[r18_0].unlock)
        else
          sound.PlaySE(1)
          r38_0(r18_0)
        end
      end
    end
    util.LoadBtn({
      rtImg = r4_53,
      fname = r27_0("back_"),
      x = 24,
      y = 564,
      func = function(r0_59)
        -- line: [1159, 1166] id: 59
        sound.PlaySE(2)
        util.setActivityIndicator(true)
        _G.StageSelect = 1
        util.ChangeScene({
          prev = r25_0,
          scene = "map",
          efx = "moveFromLeft",
        })
        return false
      end,
    })
    util.LoadBtn({
      rtImg = r4_53,
      fname = r26_0("scrl_previous"),
      x = 24,
      y = 470,
      func = r19_53,
    })
    util.LoadBtn({
      rtImg = r4_53,
      fname = r26_0("scrl_next"),
      x = 808,
      y = 470,
      func = r20_53,
    })
    util.LoadBtn({
      rtImg = r4_53,
      fname = r27_0("ok_"),
      x = 360,
      y = 548,
      func = r21_53,
    })
    if r9_0 == true then
      local r22_53 = display.newGroup()
      display.newImage(r22_53, r37_0("banner_bingo_"), 0, 0)
      r22_53:setReferencePoint(display.TopLeftReferencePoint)
      util.LoadBtnGroup({
        group = r22_53,
        cx = 15,
        cy = 120,
        func = r67_0,
        show = true,
      })
      r22_53:setReferencePoint(display.CenterReferencePoint)
      r4_53:insert(r22_53)
    else
      r19_0 = anime.RegisterWithInterval(r20_0.GetData(), 15, 120, "data/shop/banner", 100)
      anime.Show(r19_0, true)
      anime.Loop(r19_0, true)
      local r22_53 = anime.GetSprite(r19_0)
      r22_53.touch = r61_0
      r22_53:addEventListener("touch", r22_53)
      util.LoadParts(r22_53, r34_0("banner_select_text_"), 15, 200)
      r4_53:insert(r22_53)
    end
    if r2_0.GetLastRes() then
      util.LoadBtn({
        rtImg = r4_53,
        fname = r32_0("offerwall_01_"),
        x = 15,
        y = 20,
        func = function(r0_66)
          -- line: [1290, 1295] id: 66
          if not _G.IsSimulator then
            wallAds.show()
          end
          return true
        end,
      })
    end
    if _G.GameMode == _G.GameModeEvo then
      local r22_53 = display.newGroup()
      util.LoadParts(r22_53, r26_0("plate_orb"), 0, 0)
      local r23_53 = display.newGroup()
      util.LoadParts(r23_53, r35_0("icon_orb"), 0, 0)
      r22_0 = display.newGroup()
      r6_0.CreateNumbers(r22_0, r6_0.sequenceNames.Score, 3, 0, 0, -2)
      r22_0.x = 30
      r22_0.y = 1
      r6_0.CreateImage(r23_53, r6_0.sequenceNames.Score, r6_0.frameDefines.ScoreSlash, 66, 1)
      r23_0 = display.newGroup()
      r6_0.CreateNumbers(r23_0, r6_0.sequenceNames.Score, 3, 0, 0, -2)
      r23_0.x = 79
      r23_0.y = 1
      r23_53:insert(r22_0)
      r23_53:insert(r23_0)
      r23_53:setReferencePoint(display.TopLeftReferencePoint)
      r23_53.x = 10
      r23_53.y = 10
      r22_53:insert(r23_53)
      local r24_53 = 0
      local r25_53 = 0
      local r26_53 = 0
      local r27_53 = 0
      if _G.UILanguage == "en" then
        r24_53 = 15
        r25_53 = 55
        r26_53 = 15
        r27_53 = 36
      else
        r24_53 = 15
        r25_53 = 36
        r26_53 = 40
        r27_53 = 55
      end
      local r28_53 = util.MakeText3({
        rtImg = r22_53,
        size = 16,
        color = {
          255,
          255,
          255
        },
        shadow = {
          170,
          170,
          170
        },
        diff_x = 0,
        diff_y = 0,
        msg = db.GetMessage(357),
      })
      r28_53:setReferencePoint(display.TopLeftReferencePoint)
      r28_53.x = r24_53
      r28_53.y = r25_53
      r24_0 = display.newGroup()
      display.newRect(r24_0, 0, 0, 80, 15).alpha = 0
      r22_53:insert(r24_0)
      r24_0:setReferencePoint(display.TopLeftReferencePoint)
      r24_0.x = r26_53
      r24_0.y = r27_53
      OrbManager.GetFormatFullRecoveryTime(function(r0_67)
        -- line: [1358, 1360] id: 67
        r63_0(r0_67)
      end)
      local r29_53 = r4_0.getItemData(r4_0.pay_item_data.OrbMaxUp)
      local r30_53 = display.newGroup()
      display.newImage(r30_53, r26_0("add_orb"), 0, 0, true)
      r30_53:setReferencePoint(display.TopLeftReferencePoint)
      util.LoadBtnGroup({
        group = r30_53,
        cx = 140,
        cy = 4,
        func = r64_0,
        show = true,
      })
      util.LoadParts(r30_53, r35_0("icon_crystal"), 42, 38)
      r62_0(r30_53, util.ConvertDisplayCrystal(r29_53[2]), "a", 70, 42)
      r30_53:setReferencePoint(display.CenterReferencePoint)
      r22_53:insert(r30_53)
      r4_53:insert(r22_53)
      r22_53:setReferencePoint(display.TopLeftReferencePoint)
      r22_53.x = 630
      r22_53.y = 550
    end
    bgm.Play(3)
    Runtime:addEventListener("enterFrame", r13_53)
    r0_0.UpdateCoin(true)
    return r3_53
  end,
  Cleanup = r25_0,
}
