-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("sqlite3")
local r1_0 = require("db.BaseDB")
local r2_0 = "data/db.sqlite"
local function r3_0()
  -- line: [6, 8] id: 1
  return system.pathForFile(r2_0, system.ResourceDirectory)
end
local function r6_0(r0_4)
  -- line: [84, 159] id: 4
  local r1_4 = {}
  local r2_4 = nil
  local r4_4 = nil
  local r5_4 = nil
  for r9_4 in r0_4:nrows("SELECT uid, ground, sky FROM user WHERE flag=0") do
    r4_4 = r9_4.ground == 1
    r5_4 = r9_4.sky == 1
    r1_4[r9_4.uid] = {
      attack = {
        r4_4,
        r5_4
      },
      cost = {},
      power = {},
      speed = {},
      range = {},
      sell = {},
    }
  end
  r2_4 = r0_4:prepare("SELECT level, value FROM cost WHERE uid=? AND flag=0")
  for r9_4, r10_4 in pairs(r1_4) do
    r2_4:reset()
    r2_4:bind_values(r9_4)
    for r14_4 in r2_4:nrows() do
      r10_4.cost[r14_4.level] = r14_4.value
    end
  end
  r2_4:finalize()
  r2_4 = r0_4:prepare("SELECT level, value FROM power WHERE uid=? AND flag=0")
  for r9_4, r10_4 in pairs(r1_4) do
    r2_4:reset()
    r2_4:bind_values(r9_4)
    for r14_4 in r2_4:nrows() do
      r10_4.power[r14_4.level] = r14_4.value
    end
  end
  r2_4:finalize()
  r2_4 = r0_4:prepare("SELECT level, value FROM speed WHERE uid=? AND flag=0")
  for r9_4, r10_4 in pairs(r1_4) do
    r2_4:reset()
    r2_4:bind_values(r9_4)
    for r14_4 in r2_4:nrows() do
      r10_4.speed[r14_4.level] = r14_4.value
    end
  end
  r2_4:finalize()
  r2_4 = r0_4:prepare("SELECT level, min_range, max_range FROM range" .. " WHERE uid=? AND flag=0")
  for r9_4, r10_4 in pairs(r1_4) do
    r2_4:reset()
    r2_4:bind_values(r9_4)
    for r14_4 in r2_4:nrows() do
      r10_4.range[r14_4.level] = {
        r14_4.min_range,
        r14_4.max_range
      }
    end
  end
  r2_4:finalize()
  r2_4 = r0_4:prepare("SELECT level, value FROM sell WHERE uid=? AND flag=0")
  for r9_4, r10_4 in pairs(r1_4) do
    r2_4:reset()
    r2_4:bind_values(r9_4)
    for r14_4 in r2_4:nrows() do
      r10_4.sell[r14_4.level] = r14_4.value
    end
  end
  r2_4:finalize()
  return r1_4
end
local function r7_0(r0_5)
  -- line: [161, 178] id: 5
  local r1_5 = {}
  for r6_5 in r0_5:nrows("SELECT eid, attr, speed, hp, mp, score, stone_damage FROM enemy" .. " WHERE flag=0 ORDER BY eid") do
    r1_5[r6_5.eid] = {
      attr = r6_5.attr,
      speed = r6_5.speed,
      hp = r6_5.hp,
      mp = r6_5.mp,
      score = r6_5.score,
      stone_damage = r6_5.stone_damage,
    }
  end
  return r1_5
end
local function r8_0(r0_6)
  -- line: [180, 207] id: 6
  local r2_6 = nil
  local r3_6 = nil
  local r4_6 = nil
  local r5_6 = nil
  local r6_6 = {}
  for r10_6 in r0_6:nrows("SELECT cnum, pnum, level, param FROM param" .. " ORDER BY cnum, pnum, level") do
    r2_6 = r10_6.cnum
    r3_6 = r10_6.pnum
    r4_6 = r10_6.level
    r5_6 = r10_6.param
    if r6_6[r2_6] == nil then
      r6_6[r2_6] = {}
    end
    if r6_6[r2_6][r3_6] == nil then
      r6_6[r2_6][r3_6] = {}
    end
    if r6_6[r2_6][r3_6][r4_6] == nil then
      r6_6[r2_6][r3_6][r4_6] = {}
    end
    r6_6[r2_6][r3_6][r4_6] = r5_6
  end
  return r6_6
end
local function r9_0(r0_7)
  -- line: [209, 216] id: 7
  local r2_7 = nil
  for r6_7 in r0_7:nrows("SELECT ratio, firstmp FROM information WHERE id=1 AND flag=0") do
    r2_7 = r6_7
  end
  return r2_7
end
return {
  LoadEvoCombiSkillTalkData = function(r0_2)
    -- line: [17, 63] id: 2
    if r0_2 == nil or r0_2.sid01 == nil or r0_2.sid02 == nil then
      return 
    end
    local r2_2 = r0_0.open(r3_0())
    local r3_2 = r2_2:prepare("SELECT own_sid, priority, msg_id FROM talk" .. " WHERE (own_sid = :own_sid AND tgt_sid = :tgt_sid OR own_sid = :tgt_sid AND tgt_sid = :own_sid) AND priority > 0" .. " ORDER BY priority ASC")
    r3_2:bind_names({
      own_sid = r0_2.sid01,
      tgt_sid = r0_2.sid02,
    })
    local r4_2 = nil
    local r5_2 = nil
    local r6_2 = {}
    for r10_2 in r3_2:nrows() do
      table.insert(r6_2, {
        sid = r10_2.own_sid,
        priority = r10_2.priority,
        msg_id = r10_2.msg_id,
      })
    end
    r3_2:finalize()
    r2_2:close()
    local r7_2 = {}
    for r11_2, r12_2 in pairs(r6_2) do
      table.insert(r7_2, {
        sid = r12_2.sid,
        priority = r12_2.priority,
        msg = r1_0.GetTalkMessage(r12_2.msg_id),
      })
    end
    return r7_2
  end,
  GetSummonCost = function(r0_3, r1_3)
    -- line: [66, 81] id: 3
    local r3_3 = r0_0.open(r3_0())
    local r4_3 = nil
    r4_3 = r3_3:prepare("select * from cost WHERE uid=? and level=?")
    r4_3:bind_values(r0_3, r1_3)
    local r5_3 = 0
    for r9_3 in r4_3:nrows() do
      r5_3 = r9_3.value
    end
    r4_3:finalize()
    r3_3:close()
    return r5_3
  end,
  StatusInit = function()
    -- line: [218, 234] id: 8
    local r1_8 = r0_0.open(r3_0())
    _G.UserStatus = r6_0(r1_8)
    _G.CharaParam = r8_0(r1_8)
    r1_8:close()
    assert(_G.MapDB, debug.traceback())
    r1_8 = r0_0.open(_G.MapDB)
    _G.EnemyStatus = r7_0(r1_8)
    local r2_8 = r9_0(r1_8)
    assert(r2_8, debug.traceback())
    _G.EnemyScaleBase = r2_8.ratio / 100
    _G.MP = r2_8.firstmp
    r1_8:close()
  end,
}
