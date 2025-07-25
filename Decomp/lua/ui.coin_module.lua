-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("tool.trial")
local r1_0 = require("tool.crystal")
local r2_0 = require("json")
local r3_0 = require("common.base_dialog")
return {
  new = function(r0_1)
    -- line: [17, 361] id: 1
    local r14_1 = nil	-- notice: implicit variable refs by block#[0]
    local r1_1 = {}
    local r2_1 = 13
    local r3_1 = 14
    local r4_1 = 15
    local r5_1 = nil
    local r6_1 = 0
    local r7_1 = nil
    local r8_1 = nil
    local r9_1 = false
    local function r10_1(r0_2)
      -- line: [55, 65] id: 2
      local r1_2 = r1_0.GetPocketCrystal()
      if r1_2 then
        if r0_2 then
          r1_2.update(r0_2, true)
        else
          r6_1 = r1_2.getPocketCrystal()
        end
      end
    end
    local function r11_1(r0_3)
      -- line: [71, 76] id: 3
      r7_1 = display.newGroup()
      r8_1 = r1_0.possessingCrystal.new(r7_1, r0_3.posX, r0_3.posY)
      r8_1.visible(false)
    end
    local function r12_1()
      -- line: [81, 90] id: 4
      if r7_1 == nil then
        return 
      end
      r8_1.cleanup()
      r7_1.isVisible = true
      display.remove(r7_1)
      r7_1 = nil
    end
    local function r13_1(r0_5, r1_5)
      -- line: [96, 184] id: 5
      if _G.UserToken == nil then
        server.NetworkError(35, nil)
        return 
      end
      local function r2_5(r0_6)
        -- line: [103, 107] id: 6
        if r1_5 ~= nil then
          r1_5(r0_6)
        end
      end
      local r3_5 = nil
      local r4_5 = nil
      local r5_5 = nil
      local r6_5 = nil
      if #r5_1 > 1 then
        r5_5 = {}
        r6_5 = {}
        for r10_5, r11_5 in pairs(r5_1) do
          table.insert(r5_5, r11_5[1])
          table.insert(r6_5, r11_5[2])
        end
      else
        r5_5 = {
          r5_1[1][1]
        }
        r6_5 = {
          r5_1[1][2]
        }
      end
      r0_0.CountUpItem(_G.MapSelect, _G.StageSelect, r5_5)
      if _G.IsSimulator then
        db.UseItem(_G.UserID, _G.WaveNr, r5_5)
        local r7_5 = nil
        for r11_5, r12_5 in pairs(r5_1) do
          kpi.Spend(_G.MapSelect, _G.StageSelect, r12_5[1], r12_5[2], r12_5[3])
        end
        if r9_1 == true then
          r3_0.FadeOutMask(r0_5, 100)
        end
        r12_1()
        r2_5(r5_1)
      else
        util.setActivityIndicator(true)
        server.UseCoin(_G.UserToken, r5_5, r6_5, function(r0_7)
          -- line: [145, 182] id: 7
          util.setActivityIndicator(false)
          r12_1()
          if server.CheckError(r0_7) then
            server.NetworkError()
          else
            local r1_7 = r2_0.decode(r0_7.response)
            if r1_7.status == 0 then
              db.UseItem(_G.UserID, _G.WaveNr, r5_5)
              for r5_7, r6_7 in pairs(r5_1) do
                kpi.Spend(_G.MapSelect, _G.StageSelect, r6_7[1], r6_7[2], r6_7[3])
              end
              if r9_1 == true then
                r3_0.FadeOutMask(r0_5, 100)
              end
              r2_5(r5_1)
            else
              if game ~= nil then
                game.UpdatePocketCrystal()
              end
              server.ServerError(r1_7.status)
            end
          end
        end)
      end
    end
    function r14_1(r0_8, r1_8, r2_8, r3_8, r4_8)
      -- line: [189, 281] id: 8
      local function r5_8(r0_9)
        -- line: [192, 198] id: 9
        r10_1(function(r0_10)
          -- line: [193, 197] id: 10
          r6_1 = r0_10
          r14_1(r0_8, r1_8, r2_8, r3_8, r4_8)
        end)
      end
      dialog.Close()
      if r9_1 == true then
        r3_0.FadeInMask(r0_8, {
          0,
          0,
          0,
          0.3
        }, 200)
      end
      if r8_1 ~= nil then
        r8_1.visible(true)
      end
      local r6_8 = nil
      local r7_8 = nil
      local r8_8 = 0
      for r12_8, r13_8 in pairs(r5_1) do
        r8_8 = r8_8 + r13_8[2] * r13_8[3]
      end
      local r10_8 = {
        string.format(db.GetMessage(r3_1), util.ConvertDisplayCrystal(r8_8)),
        db.GetMessage(r4_1)
      }
      if r2_8 ~= nil then
        table.insert(r10_8, r2_8)
      end
      local r11_8 = nil
      if r1_8 ~= nil then
        r11_8 = r1_8
      else
        r11_8 = r2_1
      end
      dialog.OpenCrystal(r0_8, r11_8, r10_8, {
        function()
          -- line: [241, 260] id: 11
          sound.PlaySE(1)
          dialog.Close()
          if r6_1 < r8_8 then
            dialog.Close()
            if r9_1 == true then
              r3_0.FadeOutMask(r0_8, 100)
            end
            r1_0.Open(r0_8, {
              r5_8,
              nil
            })
          else
            r13_1(r0_8, r3_8)
          end
          return true
        end,
        function()
          -- line: [261, 279] id: 12
          sound.PlaySE(2)
          dialog.Close()
          if r9_1 == true then
            r3_0.FadeOutMask(r0_8, 100)
          end
          r12_1()
          if r4_8 ~= nil then
            r4_8(r5_1)
          end
          return true
        end
      })
    end
    local function r15_1(r0_13, r1_13, r2_13, r3_13, r4_13)
      -- line: [286, 293] id: 13
      util.setActivityIndicator(true)
      r10_1(function(r0_14)
        -- line: [288, 292] id: 14
        r6_1 = r0_14
        util.setActivityIndicator(false)
        r14_1(r0_13, r1_13, r2_13, r3_13, r4_13)
      end)
    end
    function r1_1.Open(r0_17, r1_17, r2_17, r3_17)
      -- line: [338, 340] id: 17
      r15_1(r0_17, nil, r1_17, r2_17, r3_17)
    end
    function r1_1.OpenWithTitle(r0_18, r1_18, r2_18, r3_18, r4_18)
      -- line: [350, 352] id: 18
      r15_1(r0_18, r1_18, r2_18, r3_18, r4_18)
    end
    (function()
      -- line: [298, 326] id: 15
      local r0_15 = nil	-- notice: implicit variable refs by block#[0]
      r5_1 = r0_15
      r0_15 = r0_1.useItemList
      if r0_15 then
        r0_15 = r0_1.useItemList
        r5_1 = r0_15
      end
      r7_1 = nil
      r0_15 = r0_1.displayPocketCoin
      if r0_15 ~= nil then
        r0_15 = type(r0_1.displayPocketCoin)
        if r0_15 == "table" then
          r11_1(r0_1.displayPocketCoin)
          r8_1.visible(true)
          util.setActivityIndicator(true)
          r10_1(function(r0_16)
            -- line: [312, 315] id: 16
            r6_1 = r0_16
            util.setActivityIndicator(false)
          end)
        end
      end
      r9_1 = false
      r0_15 = r0_1.displayMask
      if r0_15 ~= nil then
        r0_15 = r0_1.displayMask
        if r0_15 == true then
          r0_15 = r0_1.displayMask
          r9_1 = r0_15
        end
      end
    end)()
    return r1_1
  end,
}
