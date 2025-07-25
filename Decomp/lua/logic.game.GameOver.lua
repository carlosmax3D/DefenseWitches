-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r6_0 = nil	-- notice: implicit variable refs by block#[0]
local r0_0 = require("logic.game.GameStatus")
local r1_0 = require("logic.game.BaseGame")
local r2_0 = require("logic.game.GameRewind")
local r3_0 = require("logic.pay_item_data")
local r4_0 = require("ui.rewind")
local r5_0 = require("tool.trial")
function r6_0()
  -- line: [8, 200] id: 1
  statslog.LogSend("game_over", {
    mp = _G.MP,
    wave = _G.WaveNr,
  })
  _G.metrics.stage_game_over(_G.MapSelect, _G.StageSelect, _G.WaveNr, _G.MP)
  r1_0.setMpObject()
  anime.Remove(r0_0.orbButtonEffect)
  local function r1_1(r0_2)
    -- line: [20, 22] id: 2
    return true
  end
  local r2_1 = nil
  local r3_1 = nil
  local function r4_1(r0_3)
    -- line: [28, 37] id: 3
    sound.PlaySE(2)
    require("scene.hint").new({
      no = 2,
      change_no = 0,
      wno = _G.MapSelect,
      sno = _G.StageSelect,
    })
  end
  local function r5_1(r0_4)
    -- line: [39, 41] id: 4
    sound.PlaySE(2)
  end
  local function r6_1(r0_5)
    -- line: [43, 54] id: 5
    sound.PlaySE(2)
    dialog.Close()
    r1_0.PossessingCrystalVisible(false)
    save.DataClear()
    util.ChangeScene({
      prev = r1_0.Cleanup,
      scene = "restart",
    })
    return true
  end
  local function r7_1(r0_6)
    -- line: [56, 63] id: 6
    sound.PlaySE(2)
    dialog.Close()
    r6_1(r0_6)
    return true
  end
  local function r8_1(r0_7)
    -- line: [65, 92] id: 7
    sound.PlaySE(2)
    dialog.Close()
    r1_0.PossessingCrystalVisible(false)
    save.DataClear()
    save.DataInit()
    save.DataPush("resume", {
      map = _G.MapSelect,
      stage = _G.StageSelect,
      wave = 1,
    })
    save.DataPush("start", {
      flag = 1,
      PopEnemyNum = 0,
      DropTreasureboxEnemy = 0,
    })
    save.DataSave()
    util.ChangeScene({
      prev = r1_0.Cleanup,
      scene = "title",
      efx = "fade",
    })
    return true
  end
  local function r9_1(r0_8)
    -- line: [94, 97] id: 8
    r8_1()
    return true
  end
  local function r10_1(r0_9)
    -- line: [99, 108] id: 9
    sound.PlaySE(2)
    dialog.Close()
    util.ChangeScene({
      prev = r1_0.Cleanup,
      scene = "map",
      efx = "fade",
    })
    return true
  end
  local function r11_1(r0_10)
    -- line: [110, 124] id: 10
    sound.PlaySE(2)
    dialog.Close()
    r1_0.PossessingCrystalVisible(false)
    if _G.WaveNr == 1 then
      r6_1()
    else
      r4_0.Open(_G.DialogRoot, {
        r2_0.rewind_ok_func,
        r3_1
      })
    end
    return true
  end
  local function r12_1()
    -- line: [127, 134] id: 11
    r1_0.showMagicRestartConfirm({
      onCancel = function()
        -- line: [129, 131] id: 12
        r6_0()
      end,
    })
  end
  local function r13_1()
    -- line: [137, 141] id: 13
    sound.PlaySE(2)
    dialog.Close()
    r12_1()
  end
  function r3_1()
    -- line: [143, 160] id: 14
    local r1_14 = r3_0.getItemData(r3_0.pay_item_data.SetItem01)[2]
    local r2_14 = r1_0.is_magic()
    if hint.CheckHintRelease(_G.MapSelect, _G.StageSelect) then
      dialog.GameOver(_G.DialogRoot, r1_14, r2_14, {
        r1_1,
        r4_1,
        r10_1,
        r11_1,
        r7_1,
        r13_1
      })
    else
      dialog.GameOver(_G.DialogRoot, r1_14, r2_14, {
        r1_1,
        r5_1,
        r10_1,
        r11_1,
        r7_1,
        r13_1
      })
    end
    r1_0.PossessingCrystalVisible(true)
  end
  function r2_1(r0_15)
    -- line: [162, 167] id: 15
    sound.PlaySE(2)
    dialog.Close()
    r3_1()
    return true
  end
  local r14_1 = _G.MapSelect
  local r15_1 = _G.StageSelect
  local r16_1 = _G.UserID
  kpi.Fail(r14_1, r15_1)
  if r0_0.PowerupMark then
    if r0_0.PowerupMark.tween then
      transit.Delete(r0_0.PowerupMark.tween)
    end
    if r0_0.PowerupMark.spr then
      display.remove(r0_0.PowerupMark.spr)
    end
  end
  r0_0.PowerupMark = nil
  local r17_1 = nil
  local r18_1 = nil
  if r14_1 == 1 and r15_1 == 1 and _G.LoginGameCenter and not db.GetGameCenterAchievement(r16_1, 22) then
    r1_0.CheckTotalAchievement(22, 100, true)
  end
  r1_0.CheckTotalAchievement(23, math.floor(db.CountAchievement(r16_1, 23) * 100 / 30), true)
  r3_1()
  _G.IsPlayingGame = false
end
return {
  game_over = r6_0,
  trial_game_over_func = function()
    -- line: [203, 238] id: 16
    sound.PlaySE(2)
    dialog.Close()
    if r5_0.CountUp(_G.MapSelect, _G.StageSelect) then
      local r1_16, r2_16 = r5_0.GetPowerUpItem()
      if r1_16 == 0 then
        save.DataClear()
        util.ChangeScene({
          prev = r1_0.Cleanup,
          scene = "restart",
        })
      else
        r5_0.Open({
          rtImg = _G.TrialRoot,
          func = {
            close = function(r0_17)
              -- line: [217, 222] id: 17
              sound.PlaySE(2)
              r5_0.Close()
              save.DataClear()
              util.ChangeScene({
                prev = r1_0.Cleanup,
                scene = "restart",
              })
            end,
            try = function(r0_18)
              -- line: [223, 229] id: 18
              sound.PlaySE(2)
              r5_0.Close()
              r5_0.StartTrial()
              save.DataClear()
              util.ChangeScene({
                prev = r1_0.Cleanup,
                scene = "restart",
              })
            end,
          },
        })
      end
    else
      save.DataClear()
      util.ChangeScene({
        prev = r1_0.Cleanup,
        scene = "restart",
      })
    end
  end,
}
