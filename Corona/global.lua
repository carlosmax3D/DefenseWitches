-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
_G.Width = display.contentWidth
_G.Height = display.contentHeight
_G.WidthDiff = display.screenOriginX
_G.HeightDiff = display.screenOriginY
_G.MapSelect = -1
_G.StageSelect = -1
_G.MaxMap = 10
_G.MaxStage = 10
_G.MainFPS = 30
_G.RewindCrystal = 20
_G.VsyncTime = 1000 / _G.MainFPS
_G.GooglePlayURL = {
  jp = "https://play.google.com/store/apps/details?id=jp.newgate.game.android.dw",
  en = "https://play.google.com/store/apps/details?id=jp.newgate.game.android.dw",
}
_G.GameData = nil
_G.IsPlayingGame = false
_G.NewInformation = true
_G.SavingToServer = false
_G.GetCrystal = 500
_G.UnlockStageCrystal = 1500
_G.Version = "1.2.3"
_G.CDN_VERSION = "1.2.0"
_G.ServerStatus = {}
_G.VersionInfo = "Defense Witches for Android ver" .. _G.Version
_G.Copyright = "© 2012-2014 Newgate Inc. All rights reserved."
_G.NoUpdate = false
_G.LoginGameCenter = false
_G.IsResumingUnlockFlag = false
_G.IsSimulator = "simulator" == system.getInfo("environment")
_G.IsWindows = system.getInfo("platformName") == "Win"
_G.IsiPhone = string.sub(system.getInfo("model"), 0, 6) == "iPhone"
_G.IsAndroid = system.getInfo("platformName") == "Android"
_G.IsiPhone5 = _G.WidthDiff == -176
_G.SystemLanguage = system.getPreference("ui", "language")
local r0_0 = _G
local lang = _G.SystemLanguage
local r1_0 = "en"

if lang == "ja" or lang == "ja-JP" or lang == "1041" or lang == "日本語" then
  r1_0 = "jp"
end

_G.UILanguage = r1_0
_G.GameModeNormal = 1
_G.GameModeEvo = 2
_G.GameMode = _G.GameModeEvo
_G.GuideSpeedButton = true
_G.GuideOrbButton = true
_G.IsSuperMP = false
_G.IsQuickGameOver = false
_G.IsQuickGameClear = false
_G.TestSpeed = 2
_G.NoServerCheck = false
_G.IsDebug = false
_G.IsResDebug = false
_G.IsReleaseAllFlag = _G.IsDebug
if _G.IsReleaseAllFlag then
  _G.IsReleaseAllWorld = false
  _G.IsReleaseAllStage = false
  _G.IsReleaseAllCharacter = false
  _G.IsReleaseAllEvoLevel = false
end
_G.DefaultHp = 20
_G.HpMaxLimit = 99
_G.MpMaxLimit = 99999
_G.Release = true
_G.IsProduction = true
_G.IsDev = false
_G.IsLocal = false
_G.VIEW_FPS = false
_G.SKIP_MENU = _G.IsSimulator
if _G.Release then
  _G.IsSuperMP = false
  _G.IsQuickGameOver = false
  _G.IsQuickGameClear = false
  _G.TestSpeed = 2
  _G.NoServerCheck = false
  _G.IsDebug = false
  _G.IsResDebug = false
  _G.IsSimulator = false
  _G.IsReleaseAllWorld = false
  _G.IsReleaseAllStage = false
  _G.IsReleaseAllCharacter = false
  _G.IsReleaseAllEvoLevel = false
  _G.IsProduction = true
  _G.IsDev = false
  _G.IsLocal = false
  _G.VIEW_FPS = false
  _G.SKIP_MENU = false
end
_G.IAP_SERVER = "https://dw-android-iap.appspot.com/"
if _G.IsDev then
  _G.IAP_SERVER = "https://dw-android-iap-dev.appspot.com/"
elseif _G.IsLocal then
  _G.IAP_SERVER = "http://127.0.0.1:9080/"
end
_G.REVIEW_SERVER_URL = "https://dw-android-iap.appspot.com/review/"
if _G.IsDev then
  _G.REVIEW_SERVER_URL = "https://dw-android-iap-dev.appspot.com/review/"
elseif _G.IsLocal then
  _G.REVIEW_SERVER_URL = "http://127.0.0.1:9080/review"
end
_G.KPI_SERVER = "https://dw-android-stats.appspot.com/event"
if _G.IsDev then
  _G.KPI_SERVER = "https://dw-android-stats-dev.appspot.com/event"
elseif _G.IsLocal then
  _G.KPI_SERVER = "http://127.0.0.1:15080/event"
end
_G.STATS_SERVER = "https://dw-android-stats.appspot.com/log/send?"
if _G.IsDev then
  _G.STATS_SERVER = "https://dw-android-stats-dev.appspot.com/log/send?"
elseif _G.IsLocal then
  _G.STATS_SERVER = "http://127.0.0.1:15080/log/send?"
end
_G.MOVIE_URL = "https://dw-android-stats.appspot.com/movie/show"
if _G.IsDev then
  _G.MOVIE_URL = "https://dw-android-stats-dev.appspot.com/movie/show"
elseif _G.IsLocal then
  _G.MOVIE_URL = "http://127.0.0.1:15080/movie/show"
end
_G.REACHABLE_URL = "https://dw-android-stats.appspot.com/static/status.txt"
if _G.IsDev then
  _G.REACHABLE_URL = "https://dw-android-stats-dev.appspot.com/static/status.txt"
elseif _G.IsLocal then
  _G.REACHABLE_URL = "http://127.0.0.1:15080/static/status.txt"
end
_G.CDN_VERSION_URLSTR = "https://dw-android-cdn.appspot.com/dwdata/"
if _G.IsDev then
  _G.CDN_VERSION_URLSTR = "https://dw-android-cdn-dev.appspot.com/dwdata/"
elseif _G.IsLocal then
  _G.CDN_VERSION_URLSTR = "http://127.0.0.1:12080/dwdata/"
end
_G.VERSION_MY_AD_URLSTR = "https://dw-android-info.appspot.com/dwdata/"
if _G.IsDev then
  _G.VERSION_MY_AD_URLSTR = "https://dw-android-info-dev.appspot.com/dwdata/"
elseif _G.IsLocal then
  _G.VERSION_MY_AD_URLSTR = "http://127.0.0.1:14080/dwdata/"
end
_G.VERSION_STATUS_URL = "https://dw-android-info.appspot.com/dwdata/"
if _G.IsDev then
  _G.VERSION_STATUS_URL = "https://dw-android-info-dev.appspot.com/dwdata/"
elseif _G.IsLocal then
  _G.VERSION_STATUS_URL = "http://127.0.0.1:14080/dwdata/"
end
_G.INFO_SERVER_URL = "https://dw-android-info.appspot.com/dwdata/static/"
if _G.IsDev then
  _G.INFO_SERVER_URL = "https://dw-android-info-dev.appspot.com/dwdata/static/"
elseif _G.IsLocal then
  _G.INFO_SERVER_URL = "http://127.0.0.1:14080/dwdata/static/"
end
