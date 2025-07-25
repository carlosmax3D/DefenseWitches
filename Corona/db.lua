-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require
local r1_0 = pairs
local r2_0 = {}
for r20_0, r21_0 in r1_0({
  r0_0("db.doc.AppStoreData"),
  r0_0("db.doc.GameData"),
  r0_0("db.doc.InfoData"),
  r0_0("db.doc.LeaderboardsData"),
  r0_0("db.doc.MsgData"),
  r0_0("db.doc.QueueData"),
  r0_0("db.doc.ResumeData"),
  r0_0("db.res.AdData"),
  r0_0("db.res.GcData"),
  r0_0("db.res.MapData"),
  r0_0("db.res.RecAppStoreData"),
  r0_0("db.res.TitleData"),
  r0_0("db.res.UserData"),
  r0_0("db.DownloadDB")
}) do
  local r22_0 = r1_0
  local r23_0 = r21_0
  for r25_0, r26_0 in r22_0(r23_0) do
    r2_0[r25_0] = r26_0
  end
end
return r2_0
