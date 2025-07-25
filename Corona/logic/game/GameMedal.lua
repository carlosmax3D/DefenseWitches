-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("logic.game.GameStatus")
local r1_0 = require("logic.char.CharStatus")
local r2_0 = require("logic.game.BaseGame")
return {
  initMedal = function()
    -- line: [6, 137] id: 1
    r0_0.MedalObject.MedalManager = r0_0.MedalClass.MedalManager.new({
      mapId = _G.MapSelect,
      stageId = _G.StageSelect,
    })
    local function r0_1(r0_2)
      -- line: [10, 28] id: 2
      if r0_2 == nil then
        return 
      end
      if r0_0.MedalObject.ExMedalDisplay ~= nil then
        local r1_2 = nil
        for r5_2 = 1, #r0_2, 1 do
          if util.IsContainedTable(r0_2[r5_2], "exId") == true then
            if r0_0.MedalObject.MedalManager.IsAcquireMedal(r0_2[r5_2].exId, r0_2[r5_2].ex_type) == true then
              r0_0.MedalObject.ExMedalDisplay.EnableMedal(r0_2[r5_2].exId, true)
            else
              r0_0.MedalObject.ExMedalDisplay.EnableMedal(r0_2[r5_2].exId, false)
            end
          end
        end
      end
    end
    r0_0.MedalObject.MedalFuncs = {
      UpdateScore = function(r0_3)
        -- line: [33, 45] id: 3
        if r0_0.MedalObject.MedalManager ~= nil and r0_0.MedalObject.MedalManager.UpdateAcquireMedal(r0_0.MedalClass.MedalManager.ExType.Score, {
          value = r0_3,
        }) == true then
          r0_1(r0_0.MedalObject.MedalManager.GetCondition(r0_0.MedalClass.MedalManager.ExType.Score))
        end
      end,
      UpdateUseOrb = function(r0_4)
        -- line: [47, 59] id: 4
        if r0_0.MedalObject.MedalManager ~= nil and r0_0.MedalObject.MedalManager.UpdateAcquireMedal(r0_0.MedalClass.MedalManager.ExType.UseOrb, {
          value = r0_4,
        }) == true then
          r0_1(r0_0.MedalObject.MedalManager.GetCondition(r0_0.MedalClass.MedalManager.ExType.UseOrb))
        end
      end,
      UpdateTreasurebox = function(r0_5, r1_5)
        -- line: [61, 73] id: 5
        if r0_0.MedalObject.MedalManager ~= nil and r0_0.MedalObject.MedalManager.UpdateAcquireMedal(r0_5, {
          value = r1_5,
        }) == true then
          r0_1(r0_0.MedalObject.MedalManager.GetCondition(r0_5))
        end
      end,
      UpdateHpxx = function(r0_6)
        -- line: [75, 93] id: 6
        if r0_0.MedalObject.MedalManager ~= nil then
          if r0_0.MedalObject.MedalManager.UpdateAcquireMedal(r0_0.MedalClass.MedalManager.ExType.Hpxx, {
            value = r0_6,
          }) == true then
            r0_1(r0_0.MedalObject.MedalManager.GetCondition(r0_0.MedalClass.MedalManager.ExType.Hpxx))
          else
            r0_1(r0_0.MedalObject.MedalManager.GetCondition(r0_0.MedalClass.MedalManager.ExType.Hpxx))
          end
        end
      end,
      UpdateLevelxx = function(r0_7, r1_7)
        -- line: [95, 107] id: 7
        if r0_0.MedalObject.MedalManager ~= nil and r0_0.MedalObject.MedalManager.UpdateAcquireMedal(r0_0.MedalClass.MedalManager.ExType.Levelxx, {
          char_id = r0_7,
          value = r1_7,
        }) == true then
          r0_1(r0_0.MedalObject.MedalManager.GetCondition(r0_0.MedalClass.MedalManager.ExType.Levelxx))
        end
      end,
      UpdateUnitAndEvoxx = function(r0_8, r1_8)
        -- line: [109, 121] id: 8
        if r0_0.MedalObject.MedalManager ~= nil and r0_0.MedalObject.MedalManager.UpdateAcquireMedal(r0_0.MedalClass.MedalManager.ExType.UnitAndEvoxx, {
          char_id = r0_8,
          value = r1_8,
        }) == true then
          r0_1(r0_0.MedalObject.MedalManager.GetCondition(r0_0.MedalClass.MedalManager.ExType.UnitAndEvoxx))
        end
      end,
      UpdateTwoUnitAndEvoxx = function(r0_9, r1_9)
        -- line: [123, 135] id: 9
        if r0_0.MedalObject.MedalManager ~= nil and r0_0.MedalObject.MedalManager.UpdateAcquireMedal(r0_0.MedalClass.MedalManager.ExType.TwoUnitAndEvoxx, {
          char_id = r0_9,
          value = r1_9,
        }) == true then
          r0_1(r0_0.MedalObject.MedalManager.GetCondition(r0_0.MedalClass.MedalManager.ExType.TwoUnitAndEvoxx))
        end
      end,
    }
  end,
}
