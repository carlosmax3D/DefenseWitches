-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("tool.trial")
local r1_0 = require("json")
local r2_0 = _G.IsiPhone
local r3_0 = nil
local r4_0 = nil
local r5_0 = nil
local r6_0 = nil
local r7_0 = nil
local r8_0 = nil
local r9_0 = nil
local r10_0 = nil
local r11_0 = nil
local function r12_0(r0_1)
  -- line: [20, 20] id: 1
  return "data/twitter/" .. r0_1 .. ".png"
end
local function r13_0(r0_2)
  -- line: [21, 21] id: 2
  return "data/cont/" .. r0_2 .. ".png"
end
local function r14_0(r0_3)
  -- line: [22, 22] id: 3
  return r13_0(r0_3 .. _G.UILanguage)
end
local function r15_0()
  -- line: [24, 37] id: 4
  if r9_0 then
    display.remove(r9_0)
    r9_0 = nil
  end
  if r4_0 then
    r4_0:removeSelf()
    r4_0 = nil
  end
  if r3_0 then
    display.remove(r3_0)
    r3_0 = nil
  end
end
local function r16_0(r0_5, r1_5, r2_5)
  -- line: [39, 68] id: 5
  local r3_5 = display.newGroup()
  local r4_5 = nil
  local r5_5 = display.newText(r0_5, 0, 0, native.systemFontBold, 24)
  r5_5:setFillColor(255, 255, 255)
  local r6_5 = 0
  r6_5 = r6_5 + util.LoadParts(r3_5, r12_0(("button_" .. r1_5 .. "_" .. r2_5)), r6_5, 0).width
  local r7_5 = math.ceil(r5_5.width / 16)
  local r8_5 = r12_0("button_" .. r1_5 .. "_center")
  for r12_5 = r6_5, r6_5 + r7_5 * 16 - 16, 16 do
    util.LoadParts(r3_5, r8_5, r12_5, 0)
  end
  util.LoadParts(r3_5, r12_0("button_" .. r1_5 .. "_right"), r6_5 + r7_5 * 16, 0)
  r3_5:insert(r5_5)
  r5_5:setReferencePoint(display.TopLeftReferencePoint)
  r5_5.x = (r3_5.width - r5_5.width) / 2
  r5_5.y = (88 - r5_5.height) / 2
  return r3_5
end
local function r17_0(r0_6, r1_6)
  -- line: [70, 102] id: 6
  local r2_6 = r1_6.phase
  local r3_6 = r0_6[1]
  local r4_6 = r0_6[2]
  local r5_6 = r0_6.onRelease
  if r2_6 == "began" then
    r3_6.isVisible = false
    r4_6.isVisible = true
    display.getCurrentStage():setFocus(r0_6)
    r0_6.is_focus = true
  elseif r0_6.is_focus then
    local r6_6 = r1_6.x
    local r7_6 = r1_6.y
    local r8_6 = r0_6.stageBounds
    local r9_6 = r8_6.xMin
    if r9_6 <= r6_6 then
      r9_6 = r8_6.xMax
      if r6_6 <= r9_6 then
        r9_6 = r8_6.yMin
        if r9_6 <= r7_6 then
          r9_6 = r7_6 <= r8_6.yMax
        end
      end
    else
      r9_6 = r9_6 <= r6_6	-- block#7 is visited secondly
    end
    if r2_6 == "moved" and not r9_6 then
      r2_6 = "cancelled"
    end
    if r2_6 == "ended" or r2_6 == "canceled" then
      r0_6.is_focus = false
      r3_6.isVisible = true
      r4_6.isVisible = false
      display.getCurrentStage():setFocus(nil)
      if r2_6 == "ended" and r9_6 and r5_6 then
        r5_6(r0_6)
      end
    end
  end
  return true
end
local function r18_0(r0_7, r1_7, r2_7)
  -- line: [104, 123] id: 7
  local r3_7 = r0_7[1]
  local r4_7 = r0_7[2]
  local r5_7 = display.newGroup()
  local r6_7 = nil
  r5_7:insert(r16_0(r3_7, "normal", r2_7))
  r6_7 = r16_0(r3_7, "push", r2_7)
  r6_7.isVisible = false
  r5_7:insert(r6_7)
  r5_7:setReferencePoint(display.TopLeftReferencePoint)
  r5_7.x = r1_7
  r5_7.onRelease = r4_7
  r5_7.touch = r17_0
  r5_7:addEventListener("touch", r5_7)
  return r5_7
end
local function r19_0(r0_8)
  -- line: [125, 128] id: 8
  r15_0()
  return true
end
local function r20_0(r0_9)
  -- line: [130, 199] id: 9
  r9_0 = display.newGroup()
  util.MakeMat(r9_0)
  local function r1_9(r0_10)
    -- line: [134, 134] id: 10
    return "data/twitter/" .. r0_10 .. ".png"
  end
  local r2_9 = {
    "OK",
    r0_9.func
  }
  local r3_9 = {
    "Cancel",
    r19_0
  }
  local r4_9 = display.newGroup()
  local r5_9 = nil
  local r6_9 = nil
  r5_9 = r1_9("bar_loop")
  for r10_9 = 0, _G.Width, 16 do
    util.LoadParts(r4_9, r5_9, r10_9, 0)
  end
  local r7_9 = nil
  local r8_9 = nil
  local r9_9 = 0
  local r10_9 = nil
  r6_9 = r18_0(r3_9, 0, "left2")
  r4_9:insert(r6_9)
  r9_9 = r9_9 + r6_9.width
  r6_9 = display.newText(r4_9, r0_9.text, 0, 0, native.systemFontBold, 24)
  r6_9:setFillColor(255, 255, 255)
  r4_9:insert(r6_9)
  r6_9:setReferencePoint(display.TopLeftReferencePoint)
  r6_9.x = r9_9
  r6_9.y = (88 - r6_9.height) / 2
  r7_9 = r9_9 + r6_9.width
  r6_9 = r18_0(r2_9, _G.Width, "left")
  r4_9:insert(r6_9)
  r8_9 = _G.Width - r6_9.width
  r6_9.x = r8_9
  if not _G.IsWindows then
    r7_9 = r7_9 + 8
    r6_9 = native.newTextField(r7_9, 26, r8_9 - 8 - r7_9, 36)
    local r12_9 = 24
    if r2_0 then
      r12_9 = r12_9 * 0.5
    end
    r6_9.font = native.newFont(native.systemFont, r12_9)
    r6_9:setTextColor(0, 0, 0)
    if r0_9.get_func then
      local r13_9 = r0_9.get_func()
      if r13_9 then
        r6_9.text = r13_9
      end
    end
    r4_9:insert(r6_9)
    native.setKeyboardFocus(r6_9)
    r4_0 = r6_9
  end
  if r3_0 then
    display.remove(r3_0)
  end
  r3_0 = r4_9
end
local function r21_0(r0_11, r1_11)
  -- line: [201, 205] id: 11
  if r1_11.phase == "ended" then
    r20_0(r0_11.params)
  end
end
local function r22_0(r0_12)
  -- line: [207, 245] id: 12
  local r1_12 = display.newGroup()
  local r2_12 = display.newText(r1_12, r0_12.text, 0, 0, native.systemFontBold, 40)
  r2_12:setFillColor(51, 51, 51)
  r2_12:setReferencePoint(display.CenterLeftReferencePoint)
  r2_12.x = 0
  r2_12.y = 34
  local r3_12 = r2_12.width + 12
  local r4_12 = display.newGroup()
  r2_12 = util.LoadParts(r4_12, r13_0("input_box"), 0, 0)
  r2_12.touch = r21_0
  r2_12.params = r0_12
  r2_12:addEventListener("touch", r2_12)
  local r5_12 = r2_12.height
  r2_12 = display.newText(r4_12, "", 16, 0, native.systemFontBold, 40)
  r2_12:setReferencePoint(display.TopLeftReferencePoint)
  r2_12.y = r5_12 / 2 - r2_12.contentHeight / 2
  r2_12:setFillColor(51, 51, 51)
  r1_12:insert(r4_12)
  r4_12:setReferencePoint(display.TopLeftReferencePoint)
  r4_12.x = r3_12
  r1_12:setReferencePoint(display.TopLeftReferencePoint)
  r1_12.x = r0_12.x
  r1_12.y = r0_12.y
  r0_12.rtImg:insert(r1_12)
  return r2_12, r1_12
end
local function r23_0(r0_13)
  -- line: [247, 268] id: 13
  local r1_13 = nil	-- notice: implicit variable refs by block#[4, 5]
  if r4_0 then
    r1_13 = r4_0.text
  elseif _G.IsWindows then
    r1_13 = "1039869"
  end
  r5_0 = r1_13
  if r7_0 then
    local r2_13 = r7_0.x
    local r3_13 = r7_0.y
    local r4_13 = r7_0.parent
    display.remove(r7_0)
    r7_0 = util.MakeText(40, {
      0,
      0,
      0
    }, {
      255,
      255,
      255
    }, r1_13)
    r4_13:insert(r7_0)
    r7_0:setReferencePoint(display.TopLeftReferencePoint)
    r7_0.x = r2_13
    r7_0.y = r3_13
  end
  r15_0()
end
local function r24_0(r0_14)
  -- line: [270, 291] id: 14
  local r1_14 = nil	-- notice: implicit variable refs by block#[4, 5]
  if r4_0 then
    r1_14 = r4_0.text
  elseif _G.IsWindows then
    r1_14 = "123456"
  end
  r6_0 = r1_14
  if r8_0 then
    local r2_14 = r8_0.x
    local r3_14 = r8_0.y
    local r4_14 = r8_0.parent
    display.remove(r8_0)
    r8_0 = util.MakeText(40, {
      0,
      0,
      0
    }, {
      255,
      255,
      255
    }, r1_14)
    r4_14:insert(r8_0)
    r8_0:setReferencePoint(display.TopLeftReferencePoint)
    r8_0.x = r2_14
    r8_0.y = r3_14
  end
  r15_0()
end
local function r25_0(r0_15)
  -- line: [293, 298] id: 15
  r15_0()
  sound.PlaySE(2)
  util.ChangeScene({
    scene = "cont",
  })
  return true
end
local function r26_0()
  -- line: [300, 320] id: 16
  for r4_16, r5_16 in pairs({
    "help",
    "help_pass"
  }) do
    if package.loaded[r5_16] then
      package.loaded[r5_16] = nil
    end
  end
  if r10_0 then
    display.remove(r10_0)
    r10_0 = nil
  end
  r0_0.TrialDisableInit()
  util.ReachableSwitch(function()
    -- line: [313, 315] id: 17
    util.ChangeScene({
      scene = "info",
    })
  end, function()
    -- line: [315, 317] id: 18
    util.ChangeScene({
      scene = "title",
    })
  end)
  return true
end
local function r27_0(r0_19)
  -- line: [325, 369] id: 19
  local r1_19 = {
    ["25"] = 2,
    ["26"] = 3,
    ["27"] = 4,
    ["28"] = 5,
    ["29"] = 6,
    ["30"] = 7,
    ["31"] = 8,
    ["32"] = 9,
    ["33"] = 10,
    ["34"] = 11,
    ["35"] = 12,
    ["36"] = 13,
    ["37"] = 14,
    ["154"] = 17,
    ["157"] = 18,
    ["159"] = 19,
    ["162"] = 21,
    ["164"] = 22,
    ["166"] = 23,
  }
  local r2_19 = nil
  local r3_19 = nil
  local r4_19 = _G.UserID
  for r8_19, r9_19 in pairs(r0_19) do
    r2_19 = r9_19.itemid
    r3_19 = r1_19[r2_19]
    db.UnlockL4SummonData(r4_19, r3_19)
  end
  r15_0()
  if r11_0 then
    display.remove(r11_0)
    r11_0 = nil
  end
  _G.BingoManager.updateUserBingoData(_G.BingoManagerClass.MISSION_TYPE.UNLOCK_CHAR(), nil)
  db.SaveToServer2(_G.UserToken)
  require("scene.parts.help_pass").UpdateForContGame(r10_0, r26_0)
end
local function r28_0(r0_20)
  -- line: [371, 383] id: 20
  util.setActivityIndicator(false)
  if server.CheckError(r0_20) then
    server.NetworkError(35)
  else
    local r1_20 = r1_0.decode(r0_20.response)
    if r1_20.status == 0 then
      r27_0(r1_20.items)
    else
      server.ServerError(r1_20.status)
    end
  end
end
local function r29_0(r0_21)
  -- line: [385, 402] id: 21
  local r4_21 = {}
  for r8_21, r9_21 in pairs(r0_21) do
    if 36 <= r9_21 and r9_21 <= 535 then
      r9_21 = r9_21 - 36
      local r1_21 = math.floor(r9_21 / 50)
      local r2_21 = math.floor((r9_21 - r1_21 * 50) / 5)
      table.insert(r4_21, {
        map = r1_21 + 1,
        stage = r2_21 + 1,
        type = r9_21 - r1_21 * 50 - r2_21 * 5 + 1,
      })
    end
  end
  db.SetAchievements(_G.UserID, r4_21)
end
local function r30_0(r0_22)
  -- line: [404, 419] id: 22
  util.setActivityIndicator(false)
  if server.CheckError(r0_22) then
    server.NetworkError(35)
  else
    local r1_22 = r1_0.decode(r0_22.response)
    if r1_22.status == 0 then
      r29_0(r1_22.achieves)
    else
      server.ServerError(r1_22.status)
    end
    util.setActivityIndicator(true)
    server.GetItemList(_G.UserToken, r28_0)
  end
end
local function r31_0(r0_23)
  -- line: [421, 496] id: 23
  util.setActivityIndicator(false)
  if r0_23.isError then
    server.NetworkError(35)
  else
    local r1_23 = r0_23.status
    if r1_23 == 200 or r1_23 == 404 then
      local r2_23 = false
      if r1_23 == 200 then
        local r3_23 = nil
        if db.GetSaveDataType() == 1 then
          db.Init()
          db.InitData()
          db.InitQueue()
          db.InitResumeData()
          db.InitInformation()
          db.InitScore()
          db.InitStore()
          util.setActivityIndicator(true)
          r3_23 = db.LoadData2()
          _G.GameData = db.LoadOptionData(_G.UserID)
          ExpManager.Init()
          OrbManager.Init()
          util.setActivityIndicator(false)
          if not r3_23 then
            server.ServerError(2)
            return 
          end
          r3_23 = db.GetUserID()
          _G.UserID = r3_23.uid
          assert(r3_23.id == _G.UserInquiryID)
          _G.UserInquiryID = r3_23.id
          assert(r3_23.key == _G.UserToken)
          _G.UserToken = r3_23.key
          r2_23 = true
        else
          db.MoveDownloadFile()
          r3_23 = db.GetUserID()
          _G.UserID = r3_23.uid
          assert(r3_23.id == _G.UserInquiryID)
          _G.UserInquiryID = r3_23.id
          assert(r3_23.key == _G.UserToken)
          _G.UserToken = r3_23.key
          db.Init()
          db.InitData()
          db.InitQueue()
          db.InitResumeData()
          db.InitInformation()
          db.InitScore()
          db.InitStore()
        end
      else
        db.DeleteDownloadFile()
      end
      if r2_23 then
        util.setActivityIndicator(true)
        server.LoadAchievementList(_G.UserToken, r30_0)
      else
        util.setActivityIndicator(true)
        server.GetItemList(_G.UserToken, r28_0)
      end
    elseif r1_23 == 403 then
      server.ServerError(2)
    else
      server.ServerError(r1_23)
    end
  end
end
local function r32_0(r0_24)
  -- line: [498, 519] id: 24
  util.setActivityIndicator(false)
  if server.CheckError(r0_24) then
    server.NetworkError(35)
  else
    local r1_24 = r1_0.decode(r0_24.response)
    if r1_24.status == 0 then
      local r2_24 = r1_24.key
      db.UpdateUserID(_G.UserID, r1_24.id, r1_24.key)
      _G.UserInquiryID = tonumber(r5_0)
      _G.UserToken = r2_24
      db.InitSummonData(_G.UserID)
      util.setActivityIndicator(true)
      db.ServerToLoad(r2_24, r31_0)
      _G.metrics.start_with_recovery(_G.UserID, _G.UserInquiryID, r2_24)
    else
      server.ServerError(r1_24.status)
    end
  end
end
local function r33_0(r0_25)
  -- line: [521, 538] id: 25
  local r1_25 = r5_0
  local r2_25 = r6_0
  if _G.IsSimulator then
    DebugPrint("authorize " .. r1_25 .. "/" .. r2_25)
  end
  local r3_25 = true
  if r1_25 == nil or r1_25:len() < 1 then
    r3_25 = false
  end
  if r2_25 == nil or r2_25:len() < 1 then
    r3_25 = false
  end
  if not r3_25 then
    sound.PlaySE(2)
    return 
  end
  r15_0()
  sound.PlaySE(1)
  util.setActivityIndicator(true)
  server.RestoreToken(r1_25, r2_25, r32_0)
  return true
end
return {
  Close = r15_0,
  new = function(r0_26)
    -- line: [540, 601] id: 26
    local r1_26 = display.newGroup()
    r11_0 = display.newGroup()
    r10_0 = display.newGroup()
    r1_26:insert(r11_0)
    r1_26:insert(r10_0)
    util.LoadBG(r11_0, r13_0("base"))
    local r2_26 = nil
    local r3_26 = nil
    r3_26 = display.newText(r11_0, db.GetMessage(161), 0, 0, 864, 72, native.systemFontBold, 30)
    r3_26:setFillColor(51, 51, 51)
    r3_26.x = 480
    r3_26.y = 236
    r5_0 = nil
    r6_0 = nil
    r7_0 = nil
    r8_0 = nil
    local r4_26 = nil
    local r5_26 = nil
    r7_0, r4_26 = r22_0({
      rtImg = r11_0,
      text = "ID",
      x = 0,
      y = 290,
      func = r23_0,
      get_func = function()
        -- line: [569, 571] id: 27
        return r5_0
      end,
    })
    r8_0, r5_26 = r22_0({
      rtImg = r11_0,
      text = "Password",
      x = 0,
      y = 378,
      func = r24_0,
      get_func = function()
        -- line: [579, 581] id: 28
        return r6_0
      end,
    })
    r5_26:setReferencePoint(display.TopCenterReferencePoint)
    r5_26.x = _G.Width / 2
    local r6_26 = r5_26.contentBounds.xMax
    r4_26:setReferencePoint(display.TopRightReferencePoint)
    r4_26.x = r6_26
    util.LoadBtn({
      rtImg = r11_0,
      fname = r14_0("button01"),
      x = 216,
      y = 468,
      func = r25_0,
    })
    util.LoadBtn({
      rtImg = r11_0,
      fname = r14_0("button02"),
      x = 548,
      y = 468,
      func = r33_0,
    })
    util.MakeFrame(r1_26)
    return r1_26
  end,
}
