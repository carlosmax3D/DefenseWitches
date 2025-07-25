-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
require("global")
require("login.login_item")
_G.util = require("tool.util")
_G.DebugPrint = _G.util.Debug
_G.events = require("tool.events")
_G.anime = require("tool.anime")
_G.transit = require("tool.transit")
_G.preload = require("tool.preload")
_G.save = require("tool.save")
_G.metrics = require("tool.metrics")
_G.db = require("db")
_G.statslog = require("server.statslog")
_G.kpi = require("server.kpi")
_G.notification = require("tool.notification")
_G.sound = require("resource.sound")
_G.bgm = require("resource.bgm")
_G.server = require("server.server")
_G.cdn = require("scene.cdn")
_G.hint = require("scene.data.hint_data")
_G.dialog = require("ui.dialog")
_G.enemy = require("logic.enemy")
_G.char = require("char")
_G.game = require("game")
local r0_0 = require("logic.gamecenter")
local r1_0 = require("ad.myads")
local r2_0 = require("ad.adcropsWall")
local r3_0 = require("ad.adcropsIcon")
local r4_0 = require("ad.adfurikunIcon")
local r5_0 = require("ad.adgenerationIcon")
local r6_0 = require("server.bingoFlag")
local r7_0 = require("server.infofile")
local r8_0 = require("json")
local r9_0 = audio
display.setStatusBar(display.HiddenStatusBar)
math.randomseed(math.floor(os.clock() * 100))
if r9_0.supportsSessionProperty == true then
  r9_0.setSessionProperty(r9_0.MixMode, r9_0.AmbientMixMode)
end
local r10_0 = false
if r9_0.supportsSessionProperty == true and r9_0.getSessionProperty(r9_0.OtherAudioIsPlaying) ~= 0 then
  r10_0 = true
end
Runtime:addEventListener("memoryWarning", function(r0_1)
  -- line: [66, 76] id: 1
  local r1_1 = nil	-- notice: implicit variable refs by block#[3]
  if _G.UILanguage == "jp" then
    r1_1 = "ゲームの動作に必要となる充分なメモリが足りません。" .. "不要なアプリを終了して再度起動してください。"
  else
    r1_1 = "Insufficient memory to run the app." .. " Finish other apps and reload it."
  end
  native.showAlert("DefenseWitches", r1_1, {
    "OK"
  })
end)
system.setIdleTimer(false)
if _G.IsSimulator == true then
  _G.TitleAnimationSkip = true
end
local r12_0 = "info"
local r13_0 = "cont"
local r14_0 = "title"
_G.BingoManagerClass = require("bingo.bingo_manager")
_G.BingoManager = _G.BingoManagerClass.new({
  bingoCardId = 1,
})
_G.metrics.game_init()
local function r15_0(r0_2)
  -- line: [104, 151] id: 2
  local r1_2 = {
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
  local r2_2 = nil
  local r3_2 = nil
  local r4_2 = _G.UserID
  for r8_2, r9_2 in pairs(r0_2) do
    r2_2 = r9_2.itemid
    r3_2 = r1_2[r2_2]
    db.UnlockL4SummonData(r4_2, r3_2)
  end
  _G.BingoManager.updateUserBingoData(_G.BingoManagerClass.MISSION_TYPE.UNLOCK_CHAR(), nil)
  db.UpdateSprDaisy()
  db.UnlockUpgrade4()
  local r6_2 = db.GetUserID().key
  if r6_2 ~= nil and 0 < r6_2:len() then
    db.SaveToServer2(r6_2)
  end
  _G.IsResumingUnlockFlag = false
end
local function r16_0(r0_3)
  -- line: [154, 167] id: 3
  if server.CheckError(r0_3) then
    _G.IsResumingUnlockFlag = false
  else
    local r1_3 = r8_0.decode(r0_3.response)
    if r1_3 and r1_3.status == 0 then
      r15_0(r1_3.items)
    else
      _G.IsResumingUnlockFlag = false
    end
  end
end
Runtime:addEventListener("system", function(r0_4)
  -- line: [171, 230] id: 4
  local r1_4 = r0_4.type
  if _G.IsPlayingGame then
    if r1_4 == "applicationSuspend" then
      local r3_4 = package.loaded.save
      local r4_4 = package.loaded.bgm
      if package.loaded.game and r3_4 then
        r3_4.DataPush("start", 1)
        r3_4.DataSave()
      end
      if r4_4 then
        r4_4.Suspend()
      end
    elseif r1_4 == "applicationResume" then
      local r2_4 = package.loaded.game
      local r3_4 = package.loaded.save
      local r4_4 = package.loaded.bgm
      if r2_4 and r3_4 then
        save.DataResume()
      end
      if r4_4 then
        r4_4.Resume()
      end
      db.CheckDailyBonus(1000)
      if r2_4 ~= nil and r2_4.IsLoadPanel() == true then
        r2_4.RecoveryOrb()
      end
    end
  end
  if r1_4 == "applicationStart" then
    r0_0.Init()
    db.CheckDailyBonus(1000)
    notification.RemoveNotification()
    notification.SetLocalNotification()
    local r3_4 = db.GetUserID().key
    if r3_4 ~= nil and 0 < r3_4:len() then
      _G.IsResumingUnlockFlag = true
      server.GetItemList(r3_4, r16_0)
    end
    return true
  elseif r1_4 == "applicationResume" then
    notification.RemoveNotification()
    notification.SetLocalNotification()
    return true
  end
end)
events.Init()
db.Init()
db.InitData()
db.InitQueue()
db.InitResumeData()
db.InitInformation()
db.InitScore()
db.InitStore()
cdn.InitCDN()
r1_0.InitADS()
r2_0.InitADS()
r3_0.InitADS()
r4_0.InitADS()
r5_0.InitADS()
r6_0.Init()
r7_0.Init()
_G.UserID = nil
_G.UserInquiryID = nil
_G.UserToken = nil
server.Init()
ExpManager = require("evo.evo_exp_manager")
OrbManager = require("evo.evo_orb_manager")
local function r18_0()
  -- line: [265, 273] id: 5
  if ExpManager ~= nil then
    ExpManager.Init()
  end
  if OrbManager ~= nil then
    OrbManager.Init()
  end
end
local r19_0 = r12_0
_G.GameData = db.LoadOptionData(_G.UserID)
local r27_0 = nil	-- notice: implicit variable refs by block#[11]
if _G.GameData.uid == nil or db.GetStartup(1) then
  db.SetStartup(1, true)
  _G.GameData.uid = _G.UserID
  _G.GameData.bgm = true
  _G.GameData.se = true
  _G.GameData.confirm = true
  _G.GameData.grid = true
  _G.GameData.voice = true
  _G.GameData.voice_type = sound.OptionChangeTypeNew
  _G.GameData.language = _G.UILanguage
  _G.GameData.local_notification = true
  db.SaveOptionData(_G.GameData)
  r19_0 = r13_0
else
  r18_0()
end
statslog.LogSend("main", {})
sound.Init()
local r20_0 = require("director")
local r21_0 = display.newGroup()
local r22_0 = nil
local r23_0 = 0
local r24_0 = 0
(function()
  -- line: [314, 329] id: 6
  local suffix = tostring(math.floor(os.time() % 2) + 1)
  local r2_6 = display.newGroup()
  display.newImage(r2_6, "data/cont/bg_title_0" .. suffix .. ".jpg", 0, 0, true)
  display.newText(r2_6, "Loading ", _G.Width - 200, _G.Height - 50, 200, 50, native.systemFontBold, 24)
  r22_0 = {
    display.newText(r2_6, ".", _G.Width - 100, _G.Height - 50, 20, 50, native.systemFontBold, 24),
    display.newText(r2_6, ".", _G.Width - 90, _G.Height - 50, 20, 50, native.systemFontBold, 24),
    display.newText(r2_6, ".", _G.Width - 80, _G.Height - 50, 20, 50, native.systemFontBold, 24)
  }
  r2_6:insert(util.LoadLeftSideCharacterImage())
  r21_0:insert(r2_6)
  _G.LoadingImage = r2_6
end)()
local function r26_0(r0_7)
  -- line: [334, 345] id: 7
  r21_0:insert(r20_0.directorView)
  if _G.SKIP_MENU then
    _G.MapSelect = 1
    _G.StageSelect = 1
    util.ChangeScene({
      scene = "map",
    })
  else
    util.ChangeScene({
      scene = r19_0,
    })
  end
  return true
end
function r27_0(r0_8)
  -- line: [347, 379] id: 8
  if r22_0 ~= nil then
    r23_0 = (r23_0 + (r0_8.time - r24_0) / 1000) % (#r22_0 + 1)
    r24_0 = r0_8.time
    local r1_8 = 0
    for r5_8 = 1, #r22_0, 1 do
      if r22_0[r5_8] ~= nil then
        if r5_8 <= r23_0 then
          r22_0[r5_8].isVisible = true
        else
          r22_0[r5_8].isVisible = false
        end
      end
    end
  end
  if _G.IsResumingUnlockFlag == false then
    Runtime:removeEventListener("enterFrame", r27_0)
    if r19_0 == r12_0 then
      util.ReachableSwitch(function()
        -- line: [371, 373] id: 9
        r19_0 = r14_0
      end, function()
        -- line: [373, 374] id: 10
      end)
    end
    r26_0()
  end
end
Runtime:addEventListener("enterFrame", r27_0)
_G.notification.InitRemoteNotification()
