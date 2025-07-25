-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("logic.game.GameStatus")
local r1_0 = require("logic.game.BaseGame")
local r2_0 = require("logic.game.GamePause")
local r3_0 = require("evo.treasure_box_manager")
local function r5_0()
  -- line: [66, 85] id: 2
  local r0_2 = {}
  local r1_2 = nil
  if char.GetSummonPurchase(11) ~= 3 then
    r0_2.tiana = true
  end
  if char.GetSummonPurchase(12) ~= 3 then
    r0_2.sarah = true
  end
  r1_2 = enemy.GetAttackPower()
  if r1_2 > 0 then
    r0_2.damage = r1_2
  end
  r1_2 = char.GetSpeedPowerup()
  if r1_2 > 0 then
    r0_2.speed = r1_2
  end
  r1_2 = char.GetRangePowerup()
  if r1_2 > 0 then
    r0_2.range = r1_2
  end
  return r0_2
end
local function r6_0()
  -- line: [88, 99] id: 3
  local r0_3 = {}
  for r4_3, r5_3 in pairs(_G.CrashObject) do
    table.insert(r0_3, {
      index = r5_3.uid,
      x = r5_3.map_x,
      y = r5_3.map_y,
      hp = r5_3.hitpoint,
    })
  end
  return r0_3
end
return {
  resume_game = function(r0_1)
    -- line: [7, 63] id: 1
    events.ResumeInit()
    local r1_1 = nil	-- notice: implicit variable refs by block#[29, 31]
    for r5_1, r6_1 in pairs(r0_1) do
      local r7_1 = r6_1.time
      local r8_1 = r6_1.command
      local r9_1 = nil
      if r8_1 == "pop" then
        r9_1 = char.ResumePop
      elseif r8_1 == "upgrade" then
        r9_1 = char.ResumeUpgrade
      elseif r8_1 == "release" then
        r9_1 = char.ResumeRelease
      elseif r8_1 == "targetselect" then
        r9_1 = char.ResumeTargetSet
      elseif r8_1 == "targetcancel" then
        r9_1 = char.ResumeTargetCancel
      elseif r8_1 == "powerup" then
        r9_1 = enemy.AttackPowerup
      elseif r8_1 == "speedup" then
        r9_1 = char.SpeedPowerup
      elseif r8_1 == "rangeup" then
        r9_1 = char.RangePowerup
      elseif r8_1 == "addmp" then
        r9_1 = game.AddMPResume
      elseif r8_1 == "addhp" then
        r9_1 = game.AddHPResume
      elseif r8_1 == "unlock" then
        r9_1 = char.SummonPurchase
      elseif r8_1 == "lililala" then
        r9_1 = char.SummonTwins
      elseif r8_1 == "start" then
        r1_1 = r7_1
        break
      end
      events.ResumeSet(r7_1, r9_1, r6_1.data)
    end
    if r1_1 == nil then
      DebugPrint("save data error")
      return 
    end
    local r2_1 = _G.GameData.se
    _G.GameData.se = false
    local r3_1 = _G.GameData.bgm
    _G.GameData.bgm = false
    r2_0.pause_func(false, false)
    events.ResumeStart(r1_1)
    r2_0.pause_func(true, false)
    _G.GameData.se = r2_1
    _G.GameData.bgm = r3_1
  end,
  make_resume_data = function()
    -- line: [102, 122] id: 4
    local r0_4 = {
      score = _G.Score,
      hp = _G.HP,
      mp = _G.MP,
      perfect = r0_0.PerfectFlag,
      scoretype = r0_0.ScoreType,
      purchase_hpmp = {
        r0_0.PurchaseHP,
        r0_0.PurchaseMP
      },
      release_flag = r0_0.ReleaseFlag,
      char = char.MakeResumeData(),
      purchase = r5_0(),
      crash_object = r6_0(),
      special = r0_0.SpecialAchievementFlag,
      powerupmark = r0_0.PowerupMark ~= nil,
      treasurebox = r3_0.GetTreasureboxData(),
      use_orb_count = OrbManager.GetUsedOrbCount(),
      mdlex_cd = r0_0.MedalObject.MedalManager.EncodeAcquire(),
    }
    db.SaveResume(_G.UserID, _G.MapSelect, _G.StageSelect, _G.WaveNr, r0_4)
  end,
}
