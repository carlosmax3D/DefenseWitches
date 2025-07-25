-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("sqlite3")
local r1_0 = require("json")
local r2_0 = require("crypto")
local r3_0 = require("db.BaseDB")
local function r4_0(r0_1, r1_1)
  -- line: [9, 19] id: 1
  for r6_1 in r0_1:nrows("SELECT sx, sy, ex, ey" .. ",skysx, skysy, skyex, skyey FROM pos" .. " WHERE flag=0 AND pid=1 LIMIT 1") do
    r1_1.start = {
      r6_1.sx,
      r6_1.sy
    }
    r1_1.goal = {
      r6_1.ex,
      r6_1.ey
    }
    r1_1.sstart = {
      r6_1.skysx,
      r6_1.skysy
    }
    r1_1.sgoal = {
      r6_1.skyex,
      r6_1.skyey
    }
  end
end
local function r5_0(r0_2, r1_2)
  -- line: [21, 28] id: 2
  local r3_2 = {}
  for r7_2 in r0_2:nrows("SELECT num, x, y FROM object WHERE flag=0 ORDER BY nr") do
    table.insert(r3_2, {
      r7_2.num,
      r7_2.x,
      r7_2.y
    })
  end
  r1_2.object = r3_2
end
local function r6_0(r0_3, r1_3)
  -- line: [30, 37] id: 3
  local r3_3 = {}
  for r7_3 in r0_3:nrows("SELECT num, x, y FROM front WHERE flag=0 ORDER BY nr") do
    table.insert(r3_3, {
      r7_3.num,
      r7_3.x,
      r7_3.y
    })
  end
  r1_3.front = r3_3
end
local function r7_0(r0_4, r1_4)
  -- line: [39, 46] id: 4
  local r3_4 = {}
  for r7_4 in r0_4:nrows("SELECT crash, hp, mp, score FROM info WHERE flag=0 ORDER BY nr") do
    table.insert(r3_4, {
      r7_4.crash,
      r7_4.hp,
      r7_4.mp,
      r7_4.score
    })
  end
  r1_4.info = r3_4
end
local function r8_0(r0_5, r1_5)
  -- line: [48, 55] id: 5
  local r3_5 = {}
  for r7_5 in r0_5:nrows("SELECT x, y, num FROM location WHERE flag=0") do
    table.insert(r3_5, {
      r7_5.x,
      r7_5.y,
      r7_5.num
    })
  end
  r1_5.map = r3_5
end
local function r9_0(r0_6, r1_6)
  -- line: [57, 75] id: 6
  r1_6.moving = {}
  local r3_6 = {}
  for r7_6 in r0_6:nrows("SELECT mx, my FROM ground WHERE flag=0 ORDER BY nr") do
    table.insert(r3_6, {
      r7_6.mx,
      r7_6.my
    })
  end
  table.insert(r3_6, {
    -1,
    -1
  })
  r1_6.moving[1] = r3_6
  local r4_6 = {}
  for r8_6 in r0_6:nrows("SELECT mx, my FROM sky WHERE flag=0 ORDER BY nr") do
    table.insert(r4_6, {
      r8_6.mx,
      r8_6.my
    })
  end
  table.insert(r4_6, {
    -1,
    -1
  })
  r1_6.moving[2] = r4_6
end
local function r10_0(r0_7, r1_7)
  -- line: [80, 83] id: 7
  return system.pathForFile(string.format("data/map/%02d/%03d.sqlite", r0_7, r1_7), system.ResourceDirectory)
end
local function r15_0()
  -- line: [185, 199] id: 12
  assert(_G.MapDB, debug.traceback())
  local r0_12 = r0_0.open(_G.MapDB)
  local r1_12 = {}
  r4_0(r0_12, r1_12)
  r5_0(r0_12, r1_12)
  r6_0(r0_12, r1_12)
  r7_0(r0_12, r1_12)
  r8_0(r0_12, r1_12)
  r9_0(r0_12, r1_12)
  r0_12:close()
  return r1_12
end
return {
  GetMapDbPath = r15_0,
  GetSpecialAchievementByMapAndStage = function(r0_8, r1_8)
    -- line: [86, 108] id: 8
    if r0_8 == nil or r1_8 == nil then
      DebugPrint("error GetSpecialAchievementByMapAndStage")
      return 
    end
    local r3_8 = r0_0.open(r10_0(r0_8, r1_8))
    local r5_8 = {}
    local r6_8 = nil
    local r7_8 = nil
    for r11_8 in r3_8:nrows("SELECT character, type FROM special WHERE flag=0" .. " ORDER BY character") do
      r6_8 = r11_8.character
      r7_8 = r11_8.type
      r5_8[r6_8] = r7_8
    end
    r3_8:close()
    return r5_8
  end,
  GetExMedalCondition = function(r0_9, r1_9)
    -- line: [113, 136] id: 9
    if r0_9 == nil or r1_9 == nil then
      DebugPrint("error GetExMedalCondition()")
      return nil
    end
    local r3_9 = r0_0.open(r10_0(r0_9, r1_9))
    local r5_9 = {}
    for r9_9 in r3_9:nrows("SELECT id, data FROM ex_condition WHERE flag=0 ORDER BY id ASC") do
      local r10_9 = r1_0.decode(r9_9.data)
      if r10_9 ~= nil then
        r10_9.exId = r9_9.id
        r5_9[r9_9.id] = r10_9
      end
    end
    r3_9:close()
    return r5_9
  end,
  GetWaveData = function()
    -- line: [142, 163] id: 10
    assert(_G.MapDB, debug.traceback())
    local r0_10 = r0_0.open(_G.MapDB)
    local r1_10 = {}
    local r3_10 = nil
    local r4_10 = nil
    for r8_10 in r0_10:nrows("SELECT wave, wait, enemy, nums, interval, bonus" .. " FROM pop WHERE flag=0 ORDER BY wave, nr") do
      r3_10 = r8_10.wave
      if r1_10[r3_10] == nil then
        r1_10[r3_10] = {}
      end
      table.insert(r1_10[r3_10], {
        r8_10.wait,
        r8_10.enemy,
        r8_10.nums,
        r8_10.interval,
        r8_10.bonus
      })
    end
    local r5_10 = {
      wave_nr = #r1_10,
      wave = r1_10,
    }
    r0_10:close()
    return r5_10
  end,
  GetSpecialAchievement = function()
    -- line: [167, 183] id: 11
    assert(_G.MapDB, debug.traceback())
    local r0_11 = r0_0.open(_G.MapDB)
    local r2_11 = {}
    local r3_11 = nil
    local r4_11 = nil
    for r8_11 in r0_11:nrows("SELECT character, type FROM special WHERE flag=0" .. " ORDER BY character") do
      r3_11 = r8_11.character
      r4_11 = r8_11.type
      r2_11[r3_11] = r4_11
    end
    r0_11:close()
    return r2_11
  end,
  GetMapData = r15_0,
}
