-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("json")
local r1_0 = require("ad.myads")
local r2_0 = _G.MaxStage
local r3_0 = _G.MaxMap
local r4_0 = _G.UnlockStageCrystal
local r5_0 = {}
local r6_0 = nil
local r7_0 = nil
local r8_0 = nil
local r9_0 = nil
local r10_0 = nil
local r11_0 = nil
local r12_0 = nil
local r13_0 = nil
local r14_0 = _G.MapSelect
local r16_0 = require("resource.char_define").CharInformation
local r17_0 = nil
local function r18_0(r0_1)
  -- line: [31, 31] id: 1
  return "data/map/" .. r0_1 .. ".png"
end
local function r19_0(r0_2)
  -- line: [32, 32] id: 2
  return r18_0(r0_2 .. _G.UILanguage)
end
local function r20_0(r0_3)
  -- line: [33, 33] id: 3
  return "data/stage/" .. r0_3 .. ".png"
end
local function r21_0(r0_4)
  -- line: [34, 34] id: 4
  return "data/shop/" .. r0_4 .. ".png"
end
local function r22_0(r0_5)
  -- line: [35, 35] id: 5
  return r21_0(r0_5 .. _G.UILanguage)
end
local function r23_0(r0_6)
  -- line: [36, 36] id: 6
  return "data/ads" .. "/" .. r0_6 .. ".png"
end
local function r24_0(r0_7)
  -- line: [37, 37] id: 7
  return r23_0(r0_7 .. _G.UILanguage)
end
local function r25_0(r0_8)
  -- line: [38, 40] id: 8
  return "data/map/interface/" .. r0_8 .. ".png"
end
local function r26_0(r0_9)
  -- line: [41, 43] id: 9
  return r25_0(r0_9 .. _G.UILanguage)
end
local r27_0 = nil
local r28_0 = require("anm.char_banner")
local function r29_0(r0_10)
  -- line: [48, 60] id: 10
  if 1 < r0_10 and cdn.CheckFilelist() == true then
    util.ChangeScene({
      scene = "cdn",
      efx = "fade",
      param = {
        next = "stage",
        back = "map",
        scene = "map",
        val = r0_10,
      },
    })
    return 
  end
  _G.MapSelect = r0_10
  util.setActivityIndicator(true)
  util.ChangeScene({
    prev = r17_0,
    scene = "stage",
    efx = "moveFromRight",
  })
end
local function r30_0(r0_11)
  -- line: [62, 133] id: 11
  if _G.UserToken == nil then
    server.NetworkError(35)
    return 
  end
  local r1_11 = _G.UserID
  local r2_11 = r0_11.cid
  local r3_11 = r0_11.world
  local r4_11 = 1
  local r5_11 = r0_11.crystal
  local r6_11 = assert
  local r7_11 = nil	-- notice: implicit variable refs by block#[6]
  if r3_11 == 1 then
    r7_11 = r4_11 ~= 1
  else
    r7_11 = r3_11 == 1  -- block#5 is visited secondly
  end
  r6_11(r7_11, debug.traceback())
  r6_11 = 38 + r4_11 - 1 + (r3_11 - 1) * 10
  r7_11 = {}
  local r8_11 = {}
  local r9_11 = nil
  table.insert(r7_11, r6_11)
  table.insert(r8_11, 1)
  if r2_11 and r0_11.itemid ~= 0 then
    table.insert(r7_11, r0_11.itemid)
    table.insert(r8_11, 1)
    r9_11 = r16_0[r2_11][3]
  end
  local r10_11 = _G.UserID
  if _G.IsSimulator then
    db.UnlockMap(r10_11, r3_11)
    db.UnlockStage(r10_11, r3_11, r4_11)
    kpi.Unlock(r6_11, r4_0)
    _G.metrics.crystal_unlock_stage(r3_11, r4_11, r4_0)
    if r2_11 then
      db.UnlockSummonData(r10_11, r2_11)
      db.UnlockL4SummonData(r10_11, r2_11)
      kpi.Unlock(r2_11, r9_11)
      _G.BingoManager.updateUserBingoData(_G.BingoManagerClass.MISSION_TYPE.UNLOCK_CHAR(), nil)
    end
    r29_0(r3_11)
  else
    util.setActivityIndicator(true)
    server.UseCoin(_G.UserToken, r7_11, r8_11, function(r0_12)
      -- line: [107, 131] id: 12
      util.setActivityIndicator(false)
      if server.CheckError(r0_12) then
        server.NetworkError()
      else
        local r1_12 = server.JsonDecode(r0_12.response)
        if r1_12.status == 0 then
          db.UnlockMap(r10_11, r3_11)
          db.UnlockStage(r10_11, r3_11, r4_11)
          kpi.Unlock(r6_11, r4_0)
          _G.metrics.crystal_unlock_stage(r3_11, r4_11, r4_0)
          if r2_11 then
            db.UnlockSummonData(r10_11, r2_11)
            db.UnlockL4SummonData(r10_11, r2_11)
            kpi.Unlock(r2_11, r9_11)
            _G.BingoManager.updateUserBingoData(_G.BingoManagerClass.MISSION_TYPE.UNLOCK_CHAR(), nil)
          end
          r29_0(r3_11)
        else
          server.ServerError(r1_12.status)
        end
      end
    end)
  end
end
local function r31_0(r0_13)
  -- line: [135, 156] id: 13
  assert(r0_13, debug.traceback())
  dialog.Open(r0_13.rtImg, 13, {
    string.format(db.GetMessage(14), util.ConvertDisplayCrystal(r0_13.crystal)),
    15
  }, {
    function(r0_14)
      -- line: [143, 149] id: 14
      sound.PlaySE(1)
      dialog.Close()
      r30_0(r0_13)
      return true
    end,
    function(r0_15)
      -- line: [150, 155] id: 15
      sound.PlaySE(2)
      dialog.Close()
      return true
    end
  })
end
local function r32_0(r0_16, r1_16, r2_16)
  -- line: [158, 199] id: 16
  local r3_16 = display.newGroup()
  local r4_16 = display.newGroup()
  r3_16:insert(r4_16)
  local r5_16 = display.newGroup()
  r3_16:insert(r5_16)
  local r6_16 = nil
  local r7_16 = nil
  r6_16 = util.MakeText3({
    rtImg = r5_16,
    size = 24,
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
    msg = db.GetMessage(190 + r0_16 - 1),
  })
  r6_16:setReferencePoint(display.TopCenterReferencePoint)
  r6_16.x = 192
  r6_16.y = 16
  r7_16 = r6_16.height
  r6_16 = util.MakeText3({
    rtImg = r5_16,
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
    msg = db.GetMessage(195 + r0_16 - 1),
  })
  r6_16:setReferencePoint(display.TopLeftReferencePoint)
  r6_16.x = 16
  r6_16.y = 16 + r7_16 + 16
  r6_16 = display.newRoundedRect(r4_16, 0, 0, 384, r5_16.height + 16 + 16, 12)
  r6_16.strokeWidth = 2
  r6_16:setFillColor(54, 64, 89, 229.5)
  r6_16:setStrokeColor(242, 230, 194)
  assert(r13_0)
  r13_0:insert(r3_16)
  r3_16:setReferencePoint(display.BottomCenterReferencePoint)
  r3_16.x = r1_16
  r3_16.y = r2_16
  return r3_16
end
local function r33_0(r0_17, r1_17)
  -- line: [201, 222] id: 17
  local r2_17 = r1_17.phase
  local r3_17 = r0_17.id
  if r2_17 == "began" then
    if r12_0 then
      display.remove(r12_0)
      r12_0 = nil
    end
    local r4_17 = r0_17.contentBounds
    r12_0 = r32_0(r3_17, (r4_17.xMax + r4_17.xMin) / 2, r4_17.yMin - 24)
    display.getCurrentStage():setFocus(r0_17)
  elseif r2_17 == "ended" or r2_17 == "cancelled" then
    if r12_0 then
      display.remove(r12_0)
      r12_0 = nil
    end
    display.getCurrentStage():setFocus(nil)
  end
  return true
end
local function r34_0(r0_18, r1_18, r2_18)
  -- line: [224, 231] id: 18
  local r3_18 = db.LoadAchievement(r0_18, r1_18, r2_18)
  for r7_18 = 1, r2_0, 1 do
    if r3_18[r7_18] ~= true then
      return false
    end
  end
  return true
end
local function r35_0(r0_19)
  -- line: [233, 240] id: 19
  if r0_19 == nil then
    return 0
  end
  local r1_19 = 0
  for r5_19, r6_19 in pairs(r0_19) do
    r1_19 = r1_19 + r6_19
  end
  return r1_19
end
local function r36_0(r0_20)
  -- line: [243, 256] id: 20
  local r1_20 = _G.UserID
  r11_0[r0_20] = {
    advance = r35_0(db.GetScore(r1_20, r0_20, 0)),
    standard = r35_0(db.GetScore(r1_20, r0_20, 1)),
    special = r34_0(r1_20, r0_20, 1),
    gold = r34_0(r1_20, r0_20, 2),
    silver = r34_0(r1_20, r0_20, 3),
    bronze = r34_0(r1_20, r0_20, 4),
    hp = r34_0(r1_20, r0_20, 5),
  }
end
local function r37_0(r0_21, r1_21)
  -- line: [259, 278] id: 21
  local r2_21 = display.newGroup()
  local r3_21 = util.MakeText3({
    rtImg = r2_21,
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
    msg = r0_21,
  })
  r3_21:setReferencePoint(display.CenterLeftReferencePoint)
  r3_21.x = 0
  r3_21.y = 10
  local r4_21 = r3_21.width
  r3_21 = util.MakeText3({
    rtImg = r2_21,
    size = 26,
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
    msg = string.format("%09d", r1_21),
  })
  r3_21:setReferencePoint(display.CenterLeftReferencePoint)
  r3_21.x = r4_21
  r3_21.y = 10
  return r2_21
end
local function r38_0(r0_22)
  -- line: [281, 401] id: 22
  local r1_22 = assert
  local r2_22 = r0_22
  if r2_22 then
    if r0_22 >= 1 then
      r2_22 = r0_22 <= 10
    else
      r2_22 = r0_22 >= 1	-- block#3 is visited secondly
    end
  end
  r1_22(r2_22, debug.traceback())
  if r11_0[r0_22] == nil then
    r36_0(r0_22)
  end
  r1_22 = r11_0[r0_22]
  assert(r1_22)
  r2_22 = nil
  local r3_22 = nil
  if r8_0 then
    r8_0.isVisible = false
  end
  if r1_22.score then
    r2_22 = r1_22.score
  else
    r2_22 = display.newGroup()
    r3_22 = r37_0("STANDARD : ", r1_22.standard)
    r2_22:insert(r3_22)
    r3_22:setReferencePoint(display.CenterReferencePoint)
    r3_22.x = 148
    r3_22.y = 48
    r3_22 = r37_0("ADVANCED : ", r1_22.advance)
    r2_22:insert(r3_22)
    r3_22:setReferencePoint(display.CenterReferencePoint)
    r3_22.x = 412
    r3_22.y = 48
    r1_22.score = r2_22
    r7_0:insert(r2_22)
  end
  r8_0 = r2_22
  r2_22.isVisible = true
  if r10_0 then
    r10_0.isVisible = false
  end
  if r1_22.achieve then
    r2_22 = r1_22.achieve
  else
    r2_22 = display.newGroup()
    if r1_22.special then
      r3_22 = display.newImage(r2_22, r18_0("master01_special"), 0, 0)
    else
      r3_22 = display.newImage(r2_22, r18_0("master01_special_dis"), 0, 0)
    end
    r3_22:setReferencePoint(display.TopLeftReferencePoint)
    r3_22.x = 120
    r3_22.y = 28
    r3_22.id = 1
    r3_22.touch = r33_0
    r3_22:addEventListener("touch")
    if r1_22.gold then
      r3_22 = display.newImage(r2_22, r18_0("master02_gold"), 0, 0)
    else
      r3_22 = display.newImage(r2_22, r18_0("master02_gold_dis"), 0, 0)
    end
    r3_22:setReferencePoint(display.TopLeftReferencePoint)
    r3_22.x = 184
    r3_22.y = 28
    r3_22.id = 2
    r3_22.touch = r33_0
    r3_22:addEventListener("touch")
    if r1_22.silver then
      r3_22 = display.newImage(r2_22, r18_0("master03_silver"), 0, 0)
    else
      r3_22 = display.newImage(r2_22, r18_0("master03_silver_dis"), 0, 0)
    end
    r3_22:setReferencePoint(display.TopLeftReferencePoint)
    r3_22.x = 248
    r3_22.y = 28
    r3_22.id = 3
    r3_22.touch = r33_0
    r3_22:addEventListener("touch")
    if r1_22.bronze then
      r3_22 = display.newImage(r2_22, r18_0("master04_bronze"), 0, 0)
    else
      r3_22 = display.newImage(r2_22, r18_0("master04_bronze_dis"), 0, 0)
    end
    r3_22:setReferencePoint(display.TopLeftReferencePoint)
    r3_22.x = 312
    r3_22.y = 28
    r3_22.id = 4
    r3_22.touch = r33_0
    r3_22:addEventListener("touch")
    if r1_22.hp then
      r3_22 = display.newImage(r2_22, r18_0("master05_hp1"), 0, 0)
    else
      r3_22 = display.newImage(r2_22, r18_0("master05_hp1_dis"), 0, 0)
    end
    r3_22:setReferencePoint(display.TopLeftReferencePoint)
    r3_22.x = 376
    r3_22.y = 28
    r3_22.id = 5
    r3_22.touch = r33_0
    r3_22:addEventListener("touch")
    r1_22.achieve = r2_22
    r9_0:insert(r2_22)
  end
  r10_0 = r2_22
  r2_22.isVisible = true
  for r7_22, r8_22 in pairs(r5_0) do
    if r8_22.icons then
      r8_22.icons.isVisible = r7_22 ~= r0_22
    end
  end
end
local function r39_0(r0_23, r1_23, r2_23)
  -- line: [403, 422] id: 23
  if r2_23 == nil then
    r2_23 = easing.linear
  end
  if r8_0 then
    r8_0.isVisible = false
    r8_0 = nil
  end
  if r10_0 then
    r10_0.isVisible = false
    r10_0 = nil
  end
  if r6_0.tween then
    transition.cancel(r6_0.tween)
  end
  r6_0.tween = transition.to(r6_0, {
    time = r0_23,
    x = 320 - 320 * (r1_23 - 1),
    transition = r2_23,
    onComplete = function(r0_24)
      -- line: [421, 421] id: 24
      r38_0(r1_23)
    end,
  })
end
local function r40_0(r0_25, r1_25)
  -- line: [427, 608] id: 25
  local r2_25 = display.newGroup()
  local r3_25 = r1_25.top or 0
  local r4_25 = r1_25.bottom or _G.Height
  local r5_25 = r1_25.left or 0
  local r6_25 = r1_25.right or _G.Width
  local r7_25 = r6_25 - r5_25
  local r8_25 = display.newRect(r2_25, 0, 0, r7_25, r4_25 - r3_25)
  r8_25:setFillColor(0, 0, 0)
  r8_25.alpha = 0.01
  r0_25:insert(r2_25)
  r2_25:setReferencePoint(display.TopLeftReferencePoint)
  r2_25.x = r5_25
  r2_25.y = r3_25
  r2_25.left = r5_25
  r2_25.top = r3_25
  r2_25.right = r6_25
  r2_25.bottom = r4_25
  local function r9_25(r0_26, r1_26)
    -- line: [453, 467] id: 26
    for r6_26, r7_26 in pairs(r5_0) do
      local r2_26 = r7_26.stageBounds
      if r2_26.xMin <= r0_26 and r0_26 <= r2_26.xMax and r2_26.yMin <= r1_26 and r1_26 <= r2_26.yMax then
        if r7_26.disable then
          return nil
        else
          return r7_26.id, r7_26
        end
      end
    end
    return nil
  end
  local function r11_25(r0_28)
    -- line: [533, 560] id: 28
    r0_28.velocity = 0
    events.Disable(r0_28.ev, true)
    local r1_28 = nil
    local r2_28 = nil
    local r3_28 = nil
    local r4_28 = nil
    local r5_28 = r3_0
    for r9_28, r10_28 in pairs(r5_0) do
      if r9_28 <= r5_28 then
        r1_28 = r0_28.x + r10_28.x + r10_28.width / 2
        r4_28 = _G.Width / 2 - r1_28
        if r2_28 == nil or math.abs(r4_28) < math.abs(r2_28) then
          r2_28 = r4_28
          r3_28 = r10_28
        end
      end
    end
    if r3_28 then
      r14_0 = r3_28.id
      r39_0(math.abs(r2_28), r3_28.id)
    else
      r14_0 = nil
      DebugPrint("not found btn: error")
    end
  end
  r2_25.ev = events.Register(function(r0_29, r1_29, r2_29)
    -- line: [562, 585] id: 29
    local r4_29 = 0.9
    local r5_29 = r0_29.ev.time - r1_29.lastTime
    r1_29.lastTime = r1_29.lastTime + r5_29
    if math.abs(r1_29.velocity) < 0.1 then
      r11_25(r1_29)
    end
    r1_29.velocity = r1_29.velocity * r4_29
    r1_29.x = math.floor(r1_29.x + r1_29.velocity * r5_29)
    local r7_29 = _G.Width - r1_29.width
    if r1_29.x > 320 then
      r11_25(r1_29)
    elseif r1_29.x < r7_29 and r7_29 < 0 then
      r11_25(r1_29)
    elseif r1_29.x < r7_29 then
      r11_25(r1_29)
    end
    return true
  end, r2_25, 0, true)
  r2_25.vev = events.Register(function(r0_30, r1_30, r2_30)
    -- line: [587, 600] id: 30
    local r4_30 = r0_30.ev.time - r1_30.prevTime
    r1_30.prevTime = r1_30.prevTime + r4_30
    if r1_30.prevX then
      if r4_30 > 0 then
        r1_30.velocity = (r1_30.x - r1_30.prevX) / r4_30
      else
        r1_30.velocity = 0
      end
    end
    r1_30.prevX = r1_30.x
  end, r2_25, 0, true)
  function r2_25.touch(r0_27, r1_27)
    -- line: [469, 531] id: 27
    local r2_27 = r1_27.phase
    if r2_27 == "began" then
      r0_27.startPos = r1_27.x
      r0_27.prevPos = r1_27.x
      r0_27.delta = 0
      r0_27.velocity = 0
      if r0_27.tween then
        transition.cancel(r0_27.tween)
      end
      r0_27.start_btn, r0_27.select_btn = r9_25(r1_27.x, r1_27.y)
      events.Disable(r0_27.ev, true)
      r0_27.prevTime = 0
      r0_27.prevX = 0
      events.Disable(r0_27.vev, false)
      display.getCurrentStage():setFocus(r0_27)
      r0_27.isFocus = true
    elseif r0_27.isFocus then
      if r2_27 == "moved" then
        local r3_27 = _G.Width - r7_25
        r0_27.delta = r1_27.x - r0_27.prevPos
        r0_27.prevPos = r1_27.x
        if r0_27.left < r0_27.x or r0_27.x < r3_27 then
          r0_27.x = r0_27.x + r0_27.delta / 2
        else
          r0_27.x = r0_27.x + r0_27.delta
        end
        if r8_0 then
          r8_0.isVisible = false
          r8_0 = nil
        end
        if r10_0 then
          r10_0.isVisible = false
          r10_0 = nil
        end
      elseif r2_27 == "ended" or r2_27 == "cancelled" then
        r0_27.lastTime = r1_27.time
        events.Disable(r0_27.ev, false)
        events.Disable(r0_27.vev, true)
        display.getCurrentStage():setFocus(nil)
        r0_27.isFocus = false
        local r3_27 = r0_27.delta
        if -1 <= r3_27 and r3_27 <= 1 then
          local r4_27, r5_27 = r9_25(r1_27.x, r1_27.y)
          if r4_27 and r4_27 == r0_27.start_btn then
            sound.PlaySE(1)
            if r11_0[r4_27].unlock then
              r31_0(r11_0[r4_27].unlock)
            else
              r29_0(r4_27)
            end
          end
        end
      end
    end
    return true
  end
  r2_25:addEventListener("touch", r2_25)
  return r2_25
end
local function r41_0()
  -- line: [611, 620] id: 31
  local r0_31 = _G.UserID
  local r1_31 = db.GetMapInfo(r0_31)
  if #r1_31 < 1 then
    db.InitMapInfo(r0_31)
    db.UnlockMap(r0_31, 1)
    r1_31 = db.GetMapInfo(r0_31)
  end
  return r1_31
end
local function r42_0(r0_32, r1_32)
  -- line: [622, 684] id: 32
  if r11_0[r1_32] == nil then
    r36_0(r1_32)
  end
  local r2_32 = r11_0[r1_32]
  assert(r2_32)
  local r3_32 = nil
  local r4_32 = display.newGroup()
  if r2_32.special then
    r3_32 = display.newImage(r4_32, r18_0("master01_special"), 0, 0)
  else
    r3_32 = display.newImage(r4_32, r18_0("master01_special_dis"), 0, 0)
  end
  r3_32:setReferencePoint(display.TopLeftReferencePoint)
  r3_32.x = 80
  r3_32.y = 164
  r3_32:scale(0.5, 0.5)
  if r2_32.gold then
    r3_32 = display.newImage(r4_32, r18_0("master02_gold"), 0, 0)
  else
    r3_32 = display.newImage(r4_32, r18_0("master02_gold_dis"), 0, 0)
  end
  r3_32:setReferencePoint(display.TopLeftReferencePoint)
  r3_32.x = 112
  r3_32.y = 164
  r3_32:scale(0.5, 0.5)
  if r2_32.silver then
    r3_32 = display.newImage(r4_32, r18_0("master03_silver"), 0, 0)
  else
    r3_32 = display.newImage(r4_32, r18_0("master03_silver_dis"), 0, 0)
  end
  r3_32:setReferencePoint(display.TopLeftReferencePoint)
  r3_32.x = 144
  r3_32.y = 164
  r3_32:scale(0.5, 0.5)
  if r2_32.bronze then
    r3_32 = display.newImage(r4_32, r18_0("master04_bronze"), 0, 0)
  else
    r3_32 = display.newImage(r4_32, r18_0("master04_bronze_dis"), 0, 0)
  end
  r3_32:setReferencePoint(display.TopLeftReferencePoint)
  r3_32.x = 176
  r3_32.y = 164
  r3_32:scale(0.5, 0.5)
  if r2_32.hp then
    r3_32 = display.newImage(r4_32, r18_0("master05_hp1"), 0, 0)
  else
    r3_32 = display.newImage(r4_32, r18_0("master05_hp1_dis"), 0, 0)
  end
  r3_32:setReferencePoint(display.TopLeftReferencePoint)
  r3_32.x = 208
  r3_32.y = 164
  r3_32:scale(0.5, 0.5)
  r0_32:insert(r4_32)
  return r4_32
end
local function r43_0(r0_33)
  -- line: [686, 691] id: 33
  if r0_33 == 1 then
    return false
  end
  return db.GetStageInfo(_G.UserID, r0_33 - 1)[10] == 0
end
local r44_0 = "data/help/witches/witches_crystal.png"
local r45_0 = "data/game/upgrade/upgrade03.png"
local function r46_0(r0_34, r1_34)
  -- line: [695, 825] id: 34
  local r8_34, r9_34 = db.LoadSummonData(_G.UserID)
  local r2_34 = r4_0
  local r5_34 = {
    itemid = nil,
  }
  local r3_34 = nil
  local r7_34 = nil
  local r4_34 = false
  for r13_34, r14_34 in pairs(r16_0) do
    if r14_34[5] == r1_34 and r14_34[6] == 1 then
      r7_34 = r13_34
      r3_34 = r13_34
      r4_34 = false
      if r8_34[r13_34] ~= 3 then
        r3_34 = nil
      else
        r2_34 = r2_34 + r14_34[3]
      end
      r5_34.itemid = r14_34[1]
      break
    elseif r14_34[7] == r1_34 and r14_34[8] == 1 then
      r7_34 = r13_34
      r3_34 = r13_34
      r4_34 = true
      r5_34.itemid = r14_34[1]
      break
    end
  end
  local r10_34 = 0
  local r11_34 = display.newGroup()
  local r6_34 = util.LoadParts(r11_34, r44_0, 0, 0)
  r6_34:setReferencePoint(display.CenterLeftReferencePoint)
  r6_34.x = 0
  r6_34.y = 16
  r10_34 = r6_34.width
  r6_34 = util.MakeText3({
    rtImg = r11_34,
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
    msg = tostring(util.ConvertDisplayCrystal(r2_34)),
  })
  r6_34:setReferencePoint(display.CenterLeftReferencePoint)
  r6_34.x = r10_34
  r6_34.y = 16
  r0_34:insert(r11_34)
  r11_34:setReferencePoint(display.TopCenterReferencePoint)
  r11_34.x = 160
  r11_34.y = 160
  if r7_34 then
    util.LoadParts(r0_34, r18_0(string.format("thumbnail_chara%02d", r7_34)), 210, 80)
    if r4_34 then
      util.LoadParts(r0_34, r45_0, 252, 64)
    end
  end
  r10_34 = 0
  r11_34 = display.newGroup()
  local r12_34 = string.format("WORLD %d-1", r1_34)
  if r3_34 then
    r12_34 = r12_34 .. string.format(" & %s", db.GetMessage(59 + r3_34))
    if r4_34 then
      r12_34 = r12_34 .. "Lv4"
    end
  end
  r6_34 = util.MakeText3({
    rtImg = r11_34,
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
    msg = "\"" .. r12_34 .. "\"",
  })
  r6_34:setReferencePoint(display.CenterLeftReferencePoint)
  r6_34.x = 0
  r6_34.y = 52
  r10_34 = r10_34 + r6_34.width
  r6_34 = util.LoadParts(r11_34, r44_0, 0, 0)
  r6_34:setReferencePoint(display.CenterLeftReferencePoint)
  r6_34.x = r10_34
  r6_34.y = 52
  r10_34 = r10_34 + r6_34.width
  r6_34 = util.MakeText3({
    rtImg = r11_34,
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
    msg = tostring(util.ConvertDisplayCrystal(r2_34)),
  })
  r6_34:setReferencePoint(display.CenterLeftReferencePoint)
  r6_34.x = r10_34
  r6_34.y = 52
  r10_34 = r10_34 + r6_34.width
  r6_34 = util.MakeText3({
    rtImg = r11_34,
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
  r6_34:setReferencePoint(display.CenterLeftReferencePoint)
  r6_34.x = r10_34
  r6_34.y = 52
  r7_0:insert(r11_34)
  r11_34:setReferencePoint(display.CenterReferencePoint)
  r11_34.x = 280
  r11_34.y = 48
  r11_34.isVisible = false
  r11_0[r1_34] = {}
  r11_0[r1_34].score = r11_34
  r5_34.crystal = r2_34
  r5_34.cid = r3_34
  r5_34.l4 = r4_34
  r5_34.world = r1_34
  r5_34.stage = 1
  r5_34.unlock = true
  r11_0[r1_34].unlock = r5_34
  r6_34 = util.MakeText3({
    rtImg = r9_0,
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
  r6_34.isVisible = false
  r6_34:setReferencePoint(display.CenterReferencePoint)
  r6_34.x = 280
  r6_34.y = 56
  r11_0[r1_34].achieve = r6_34
end
local function r47_0(r0_35, r1_35)
  -- line: [827, 879] id: 35
  local r2_35 = {}
  local r3_35 = nil
  local r4_35 = nil
  local r5_35 = false
  for r9_35, r10_35 in pairs(r16_0) do
    if r10_35[5] == r1_35 and r10_35[6] == 1 then
      r4_35 = r9_35
      r5_35 = false
      break
    elseif r10_35[7] == r1_35 and r10_35[8] == 1 then
      r4_35 = r9_35
      r5_35 = true
      break
    end
  end
  if r4_35 then
    util.LoadParts(r0_35, r18_0(string.format("thumbnail_chara%02d", r4_35)), 210, 80)
    if r5_35 then
      util.LoadParts(r0_35, r45_0, 252, 64)
    end
  end
  r3_35 = util.MakeText3({
    rtImg = r7_0,
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
    msg = string.format("WORLD %d-1", r1_35),
  })
  r3_35.isVisible = false
  r3_35:setReferencePoint(display.CenterLeftReferencePoint)
  r3_35.x = 80
  r3_35.y = 48
  r2_35.score = r3_35
  r3_35 = util.MakeText3({
    rtImg = r9_0,
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
    msg = db.GetMessage(248),
  })
  r3_35.isVisible = false
  r3_35:setReferencePoint(display.CenterReferencePoint)
  r3_35.x = 280
  r3_35.y = 56
  r2_35.achieve = r3_35
  r11_0[r1_35] = r2_35
  r11_0[r1_35].unlock = {
    unlock = false,
  }
end
local function r48_0(r0_36)
  -- line: [881, 888] id: 36
  sound.PlaySE(1)
  util.ChangeScene({
    prev = r17_0,
    scene = "shop.shop_view",
    efx = "overFromBottom",
    param = {
      closeScene = "map",
    },
  })
end
local function r49_0(r0_37)
  -- line: [893, 919] id: 37
  if r0_37.isError then
    return 
  end
  local r1_37 = r0_37.response
  if r1_37 == nil then
    return 
  end
  local r2_37 = r0_0.decode(r1_37)
  if r2_37.status == 1 then
    if type(r2_37.result) == "table" then
      local r3_37 = require("login.login_bonus_popup_dialog").new(r2_37.result)
      if r3_37 and r3_37.isLoginBonusPopup == false then
        r3_37.show()
      end
    end
    if r2_37.is_next ~= nil and r2_37.is_next == 1 then
      notification.SetLoginBonusNotification()
    end
  end
end
local function r50_0()
  -- line: [924, 930] id: 38
end
function r17_0()
  -- line: [1185, 1200] id: 49
  if r6_0 then
    if r6_0.tween then
      transition.cancel(r6_0.tween)
    end
    if r6_0.ev then
      events.Delete(r6_0.ev)
    end
    if r6_0.vev then
      events.Delete(r6_0.vev)
    end
    r6_0 = nil
  end
  events.DeleteNamespace("map")
end
return {
  new = function(r0_39)
    -- line: [932, 1183] id: 39
    events.SetNamespace("map")
    local r1_39 = display.newGroup()
    local r2_39 = util.MakeGroup(r1_39)
    util.MakeFrame(r1_39)
    r7_0 = display.newGroup()
    r9_0 = display.newGroup()
    util.LoadTileBG(r2_39, db.LoadTileData("title", "bg"), "data/title")
    util.LoadParts(r2_39, r18_0("world_select_title"), 192, -16)
    local r4_39 = _G.MapSelect
    if r4_39 < 1 or r3_0 < r4_39 then
      r4_39 = 1
    end
    r6_0 = r40_0(r2_39, {
      left = 0,
      top = 0,
      right = 3200,
      bottom = 560,
    })
    util.LoadParts(r7_0, r18_0("plate_score"), 0, 0)
    r8_0 = nil
    r7_0:setReferencePoint(display.TopLeftReferencePoint)
    r7_0.x = 330
    r7_0.y = 132
    r2_39:insert(r7_0)
    util.LoadParts(r9_0, r18_0("plate_achievemet"), 0, 0)
    r10_0 = nil
    r9_0:setReferencePoint(display.TopLeftReferencePoint)
    r9_0.x = 200
    r9_0.y = 444
    r2_39:insert(r9_0)
    local r5_39 = 0
    local r6_39 = r41_0()
    local r7_39 = nil
    local r8_39 = nil
    local r9_39 = true
    local r10_39 = false
    r5_0 = {}
    r11_0 = {}
    for r14_39 = 1, r3_0, 1 do
      r10_39 = false
      r7_39 = display.newGroup()
      if r6_39[r14_39] == 0 then
        r8_39 = r19_0(string.format("map_thumbnail%02d_", r14_39))
      else
        r8_39 = r18_0(string.format("map_thumbnail%02d_locked", r14_39))
        if r9_39 and r43_0(r14_39) then
          r8_39 = r18_0(string.format("map_thumbnail%02d_purchase", r14_39))
          r10_39 = true
        end
        r9_39 = false
      end
      display.newImage(r7_39, r8_39, true)
      if r6_39[r14_39] ~= 0 then
        if r10_39 then
          r46_0(r7_39, r14_39)
          r11_0[r14_39].unlock.rtImg = r2_39
        else
          r47_0(r7_39, r14_39)
        end
      end
      r6_0:insert(r7_39)
      r7_39:setReferencePoint(display.TopLeftReferencePoint)
      r7_39.x = r5_39
      r7_39.y = 228
      r7_39.id = r14_39
      r7_39.unlock = r6_39[r14_39] == 0
      r7_39.disable = true
      if r7_39.unlock then
        r7_39.icons = r42_0(r7_39, r14_39)
        r7_39.disable = false
      end
      if r10_39 then
        r7_39.disable = false
      end
      table.insert(r5_0, r7_39)
      r5_39 = r5_39 + 320
    end
    local function r11_39(r0_40)
      -- line: [1019, 1025] id: 40
      bgm.FadeOut(500)
      sound.PlaySE(2)
      util.ChangeScene({
        prev = r17_0,
        scene = "title",
        efx = "moveFromLeft",
      })
      return true
    end
    local function r12_39(r0_41)
      -- line: [1028, 1034] id: 41
      bgm.FadeOut(500)
      sound.PlaySE(2)
      util.ChangeScene({
        prev = r17_0,
        scene = "menu",
        efx = "moveFromLeft",
        param = {
          back = "map",
        },
      })
      return true
    end
    local function r13_39(r0_42)
      -- line: [1036, 1042] id: 42
      sound.PlaySE(1)
      r6_0.velocity = 0
      r39_0(500, 1, easing.outQuad)
      r14_0 = 1
      return true
    end
    local function r14_39(r0_43)
      -- line: [1044, 1051] id: 43
      sound.PlaySE(1)
      r6_0.velocity = 0
      r39_0(500, r3_0, easing.outQuad)
      r14_0 = r3_0
      return true
    end
    local function r15_39(r0_44)
      -- line: [1053, 1078] id: 44
      sound.PlaySE(1)
      if r6_0.tween then
        transition.cancel(r6_0.tween)
      end
      r6_0.velocity = 0
      local r1_44 = nil
      local r2_44 = nil
      local r3_44 = nil
      local r4_44 = nil
      for r8_44, r9_44 in pairs(r5_0) do
        r1_44 = r6_0.x + r9_44.x + r9_44.width / 2
        r2_44 = _G.Width / 2 - r1_44
        if r3_44 == nil or math.abs(r2_44) < math.abs(r3_44) then
          r3_44 = r2_44
          r4_44 = r9_44
        end
      end
      local r5_44 = r4_44.id + r0_44
      if 1 <= r5_44 and r5_44 <= r3_0 then
        r39_0(500, r5_44, easing.outQuad)
        r14_0 = r5_44
      end
      return true
    end
    local function r16_39(r0_45)
      -- line: [1081, 1083] id: 45
      return r15_39(-1)
    end
    local function r17_39(r0_46)
      -- line: [1085, 1087] id: 46
      return r15_39(1)
    end
    local function r18_39(r0_47)
      -- line: [1089, 1102] id: 47
      if r14_0 == nil or r14_0 <= 0 then
        r14_0 = 1
      end
      if r5_0[r14_0] ~= nil and r5_0[r14_0].disable == false then
        sound.PlaySE(1)
        if r11_0[r14_0].unlock then
          r31_0(r11_0[r14_0].unlock)
        else
          r29_0(r14_0)
        end
      end
    end
    local r19_39 = r18_0("quit_en")
    if _G.UILanguage == "jp" then
      r19_39 = r18_0("quit2_jp")
    end
    util.LoadBtn({
      rtImg = r2_39,
      fname = r19_39,
      x = 24,
      y = 564,
      func = r11_39,
    })
    util.LoadBtn({
      rtImg = r2_39,
      fname = r25_0("btn_menu"),
      x = 713,
      y = 559,
      func = r12_39,
    })
    util.LoadBtn({
      rtImg = r2_39,
      fname = r20_0("scrl_previous"),
      x = 24,
      y = 470,
      func = r16_39,
    })
    util.LoadBtn({
      rtImg = r2_39,
      fname = r20_0("scrl_next"),
      x = 808,
      y = 470,
      func = r17_39,
    })
    util.LoadBtn({
      rtImg = r2_39,
      fname = r19_0("ok_"),
      x = 360,
      y = 548,
      func = r18_39,
    })
    if r1_0.GetLastRes() then
      util.LoadBtn({
        rtImg = r2_39,
        fname = r24_0("offerwall_01_"),
        x = 15,
        y = 20,
        func = function(r0_48)
          -- line: [1141, 1146] id: 48
          if not _G.IsSimulator then
            wallAds.show()
          end
          return true
        end,
      })
    end
    r27_0 = anime.RegisterWithInterval(r28_0.GetData(), 15, 120, "data/shop/banner", 100)
    anime.Show(r27_0, true)
    anime.Loop(r27_0, true)
    local r21_39 = anime.GetSprite(r27_0)
    r21_39.touch = r48_0
    r21_39:addEventListener("touch", r21_39)
    util.LoadParts(r21_39, r22_0("banner_select_text_"), 15, 200)
    r2_39:insert(r21_39)
    r13_0 = display.newGroup()
    r2_39:insert(r13_0)
    r12_0 = nil
    r6_0.x = 320 - 320 * (r4_39 - 1)
    r38_0(r4_39)
    util.setActivityIndicator(false)
    bgm.Play(3)
    return r1_39
  end,
  Cleanup = r17_0,
}
