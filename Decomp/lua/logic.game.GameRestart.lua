-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("logic.game.GameStatus")
local r1_0 = require("logic.game.BaseGame")
local r2_0 = require("logic.pay_item_data")
local r3_0 = nil
function r3_0(r0_1)
  -- line: [11, 72] id: 1
  local function r1_1(r0_2)
    -- line: [12, 12] id: 2
    return "data/game/confirm/" .. r0_2 .. "en.png"
  end
  local function r2_1(r0_3)
    -- line: [14, 14] id: 3
    return "data/game/confirm/" .. r0_3 .. _G.UILanguage .. ".png"
  end
  local function r3_1(r0_4)
    -- line: [16, 16] id: 4
    return "data/dialog/" .. r0_4 .. ".png"
  end
  local function r4_1(r0_5)
    -- line: [18, 18] id: 5
    return r3_1(r0_5 .. _G.UILanguage)
  end
  local r5_1 = db.GetMessage(4)
  local r6_1 = db.GetMessage(9)
  local r7_1 = {}
  local r8_1 = true
  local r9_1 = "restart_powerup_"
  local r10_1 = {
    255,
    255,
    68
  }
  if r1_0.is_magic() then
    r8_1 = false
    r9_1 = "restart_powerup_disable_"
    r10_1 = {
      153,
      153,
      153
    }
  end
  local r13_1 = r2_0.getItemData(r2_0.pay_item_data.SetItem01)[2]
  r7_1[1] = {
    posX = 40,
    image = r4_1(r9_1),
    enable = r8_1,
    inText = tostring(util.ConvertDisplayCrystal(r13_1)),
    inTextPosX = 90,
    inTextPosY = 53,
    inTextColor = r10_1,
    func = function()
      -- line: [47, 54] id: 6
      r1_0.showMagicRestartConfirm({
        onCancel = function()
          -- line: [49, 51] id: 7
          r3_0(r0_1)
        end,
      })
    end,
  }
  r7_1[2] = {
    posX = 290,
    image = r1_1("confirm_01"),
    func = r0_1.onOk,
  }
  r7_1[3] = {
    posX = 420,
    image = r2_1("confirm_02"),
    func = r0_1.onCancel,
  }
  dialog.OpenRestartDialog(_G.DialogRoot, r5_1, {
    r6_1
  }, r13_1, r7_1)
  r1_0.PossessingCrystalVisible(true)
end
return {
  showRestartDialog = r3_0,
}
