-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("logic.game.GameStatus")
local r1_0 = require("logic.game.BaseGame")
local r2_0 = require("logic.game.GameOver")
local r3_0 = require("logic.game.GameRewind")
local r4_0 = require("logic.game.GamePowerup")
local r5_0 = require("ui.option")
local r6_0 = require("ui.rewind")
local r7_0 = require("ui.powerup")
local r8_0 = require("tool.trial")
local r9_0 = require("game.pause_manager")
return {
  option_func = function()
    -- line: [14, 174] id: 1
    if r0_0.GameOver then
      return true
    end
    if r0_0.GameClear then
      return true
    end
    if r9_0.GetPauseType() == r0_0.PauseTypeStopClockMenu or r9_0.GetPauseType() == r0_0.PauseTypeStopClock then
      return true
    end
    sound.PlaySE(2)
    char.ClearTwinsGuide()
    char.ClearAllCircle()
    anime.Show(r0_0.orbButtonEffect, false)
    local function r0_1(r0_2)
      -- line: [35, 44] id: 2
      sound.PlaySE(2)
      dialog.Close()
      r1_0.PossessingCrystalVisible(false)
      return true
    end
    local function r1_1(r0_3)
      -- line: [46, 58] id: 3
      bgm.FadeOut(500)
      sound.PlaySE(2)
      dialog.Close()
      r1_0.PossessingCrystalVisible(false)
      save.DataClear()
      util.ChangeScene({
        prev = r1_0.Cleanup,
        scene = "stage",
        efx = "fade",
      })
      return true
    end
    local function r2_1(r0_4)
      -- line: [60, 71] id: 4
      bgm.FadeOut(500)
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
    local function r3_1(r0_5)
      -- line: [73, 89] id: 5
      bgm.FadeOut(500)
      sound.PlaySE(2)
      dialog.Close()
      r1_0.PossessingCrystalVisible(false)
      save.DataPush("start", {
        flag = 1,
        PopEnemyNum = enemy.PopEnemyNum,
        DropTreasureboxEnemy = enemy.DropTreasureboxEnemy,
      })
      save.DataSave()
      util.ChangeScene({
        prev = r1_0.Cleanup,
        scene = "title",
        efx = "fade",
      })
      return true
    end
    local r4_1 = _G.GameData.language
    local r5_1 = _G.GameData.voice_type
    r5_0.Open({
      rtImg = _G.OptionRoot,
      mode = true,
      func = {
        close = function(r0_6)
          -- line: [97, 116] id: 6
          sound.PlaySE(2)
          r5_0.Close()
          db.SaveOptionData(_G.GameData)
          if _G.GameData.bgm then
            bgm.Play(2)
          else
            bgm.Stop()
          end
          if _G.GameData.language ~= r4_1 or _G.GameData.language ~= r5_1 then
            char.UpdateVoicePath()
          end
          anime.Show(r0_0.orbButtonEffect, true)
        end,
        back = function(r0_7)
          -- line: [117, 124] id: 7
          sound.PlaySE(2)
          r5_0.Close()
          local r1_7 = {
            8,
            6
          }
          if db.CountItemList(_G.UserID) > 0 then
            r1_7 = {
              8,
              10
            }
          end
          dialog.Open(_G.DialogRoot, 3, r1_7, {
            r1_1,
            r0_1
          })
        end,
        quit = function(r0_8)
          -- line: [125, 130] id: 8
          sound.PlaySE(2)
          r5_0.Close()
          dialog.Open(_G.DialogRoot, 2, {
            7
          }, {
            r3_1,
            r0_1
          })
        end,
        powerup = function(r0_9)
          -- line: [131, 144] id: 9
          local function r1_9(r0_10)
            -- line: [132, 135] id: 10
            r4_0.process_powerup(r0_10)
            return true
          end
          local function r2_9()
            -- line: [137, 139] id: 11
            return true
          end
          sound.PlaySE(1)
          r5_0.Close()
          r7_0.Open(_G.DialogRoot, {
            r1_9,
            r2_9
          })
        end,
        restart = function(r0_12)
          -- line: [145, 159] id: 12
          sound.PlaySE(2)
          r5_0.Close()
          if r8_0.GetItemCount(_G.MapSelect, _G.StageSelect) == 0 then
            if r8_0.CheckTrialDisable() == false then
              r8_0.SetTrialType(1)
              r1_0.showRestartDialog({
                onOk = r2_0.trial_game_over_func,
                onCancel = r0_1,
              })
            else
              r1_0.showRestartDialog({
                onOk = r2_1,
                onCancel = r0_1,
              })
            end
          else
            r1_0.showRestartDialog({
              onOk = r2_1,
              onCancel = r0_1,
            })
          end
        end,
        rewind = function(r0_13)
          -- line: [160, 169] id: 13
          bgm.Stop()
          sound.PlaySE(2)
          r5_0.Close()
          if _G.WaveNr == 1 then
            r2_1()
          else
            r6_0.Open(_G.DialogRoot, {
              r3_0.rewind_ok_func,
              nil
            })
          end
        end,
      },
    })
    return true
  end,
  open_rewind_func = function()
    -- line: [176, 179] id: 14
    sound.PlaySE(2)
    r6_0.Open(_G.DialogRoot, {
      r3_0.rewind_ok_func,
      nil
    })
  end,
  open_powerup_func = function()
    -- line: [181, 193] id: 15
    local function r0_15(r0_16)
      -- line: [182, 185] id: 16
      r4_0.process_powerup(r0_16)
      return true
    end
    local function r1_15()
      -- line: [187, 189] id: 17
      return true
    end
    sound.PlaySE(1)
    r7_0.Open(_G.DialogRoot, {
      r0_15,
      r1_15
    })
  end,
}
