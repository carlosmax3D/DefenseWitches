-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = {
  CharDef = require("resource.char_define"),
  target_efx = require("efx.target_mark"),
  snipe_target_efx = require("efx.snipe_target_mark"),
  guide_efx = require("efx.guide_mark"),
  SummonAnime = {
    require("efx.fx_summons_base"),
    require("efx.fx_summon_overlay")
  },
  TutorialManager = require("tool.tutorial_manager"),
  TblManager = require("evo.char_tbl.tbl_manager"),
  SpriteNumber01 = require("common.sprite_loader").new({
    imageSheet = "common.sprites.sprite_number01",
  }),
  SpriteNumber02 = require("common.sprite_loader").new({
    imageSheet = "common.sprites.sprite_number02",
  }),
  SpriteNumber03 = require("common.sprite_loader").new({
    imageSheet = "common.sprites.sprite_number03",
  }),
  EvoChar = nil,
}
if _G.GameMode == _G.GameModeEvo then
  r0_0.EvoChar = require("evo.evo_char")
end
r0_0.Classes = {
  UnitListParts = require("game.unit_list_parts"),
}
r0_0.Objects = {
  UnitListParts = nil,
}
r0_0.MAX_USER = #r0_0.CharDef.UnitPanelOrder
r0_0.RECOVERY_TIME = 20000
local r1_0 = require("logic.pay_item_data")
r0_0.ItemList = {
  id1 = r1_0.pay_item_data.CharItemDaisy,
  id2 = r1_0.pay_item_data.CharItemBecky,
  id3 = r1_0.pay_item_data.CharItemChloe,
  id4 = r1_0.pay_item_data.CharItemNicola,
  id5 = r1_0.pay_item_data.CharItemChiara,
  id6 = r1_0.pay_item_data.CharItemCecilia,
  id7 = r1_0.pay_item_data.CharItemBianca,
  id8 = r1_0.pay_item_data.CharItemLillian,
  id9 = r1_0.pay_item_data.CharItemIris,
  id10 = r1_0.pay_item_data.CharItemLyra,
  id11 = r1_0.pay_item_data.CharItemTiana,
  id12 = r1_0.pay_item_data.CharItemSarah,
  id13 = r1_0.pay_item_data.CharItemLuna,
  id14 = r1_0.pay_item_data.CharItemLiliLala,
  id15 = r1_0.pay_item_data.CharItem15,
  id16 = 0,
  id17 = r1_0.pay_item_data.CharItemKala,
  id18 = r1_0.pay_item_data.CharItemAmber,
  id19 = r1_0.pay_item_data.CharItemNina,
  id20 = r1_0.pay_item_data.CharItemDaisyA,
  id21 = r1_0.pay_item_data.CharItemJill,
  id22 = r1_0.pay_item_data.CharItemYuiko,
  id23 = r1_0.pay_item_data.CharItemBell,
  id24 = r1_0.pay_item_data.CharItemYung,
}
r0_0.CircleTransition = nil
r0_0.CircleUpgradeTransition = nil
r0_0.CircleSpr = nil
r0_0.CircleUpgradeSpr = nil
r0_0.SummonTransition = nil
r0_0.SummonSpr = nil
r0_0.SummonPos = nil
r0_0.SummonPos2 = nil
r0_0.SummonGroupSpr = nil
r0_0.SummonCrystalGroupSpr = nil
r0_0.LunaOnlyOne = nil
r0_0.AmberOnlyOne = nil
r0_0.SummonStatus = nil
r0_0.SummonCrystalStatus = nil
r0_0.SummonChar = nil
r0_0.MyChar = nil
r0_0.TargetSpr = nil
r0_0.SelectTarget = nil
r0_0.BadMarkSprite = nil
r0_0.BadMarkTransition = nil
r0_0.SummonConfirm = nil
r0_0.ReleaseConfirm = nil
r0_0.UpgradeBtn = nil
r0_0.UpgradeBtnNum = nil
r0_0.ReleaseBtnNum = nil
r0_0.UpgradeMonitoring = nil
r0_0.SummonMonitoring = nil
r0_0.AttackSpeed = 0
r0_0.RangePower = 0
r0_0.Level4Lock = nil
r0_0.FlowerCount = 0
r0_0.LiliLalaPop = nil
r0_0.GuideSprite = nil
r0_0.GuideCircle = nil
r0_0.CharaUID = 1
r0_0.Cooldown = nil
r0_0.ShowUnitPanelFlag = false
r0_0.CrystalSpriteGrp = nil
r0_0.CrystalSprite = nil
r0_0.SnipeImg = nil
r0_0.SnipeChargeGauge = nil
r0_0.SummonSelectPlateSpr = nil
r0_0.SummonSelectCrystalSpr = nil
r0_0.SummonSelectMpSpr = nil
r0_0.EvoSummonSelectCrystalSpr = nil
r0_0.EvoSummonSelectMpSpr = nil
r0_0.PanelTrantision = nil
r0_0.userCoin = 0
r0_0.kala_crystal_cost = 200
r0_0.touch_time = 0
r0_0.touch_pos = {}
r0_0.LiliLalaVect = {
  {
    0,
    -1
  },
  {
    0,
    1
  },
  {
    -1,
    0
  },
  {
    1,
    0
  }
}
r0_0.DisableScoreTocketAutoFlag = false
r0_0.UseCrystalMode = false
r0_0.ComingSoonLabel = nil
r0_0.DisableButtonColor = {
  164,
  128,
  64,
  1
}
r0_0.KalaOnlyOne = nil
r0_0.YuikoOnlyOne = nil
r0_0.SnipeTargetSpr = nil
r0_0.SummonPlateGroup = nil
r0_0.IsUseCrystal = false
r0_0.KalaPos = {}
r0_0.KalaStruct = nil
r0_0.AmberStruct = nil
r0_0.YuikoStruct = nil
r0_0.IsSnipeMode = false
r0_0.SnipemodeWindowImg = nil
r0_0.SnipeSelectTarget = nil
if r0_0.EvoChar ~= nil then
  r0_0.UseOrbModeReset = r0_0.EvoChar.UseOrbModeReset
  r0_0.UseOrbModeCharSelect = r0_0.EvoChar.UseOrbModeCharSelect
  r0_0.UseOrbModeEvoLevelSelect = r0_0.EvoChar.UseOrbModeEvoLevelSelect
  r0_0.UseOrbModePlayStatusPlay = r0_0.EvoChar.UseOrbModePlayStatusPlay
  r0_0.UseOrbModePlayStatusPause = r0_0.EvoChar.UseOrbModePlayStatusPause
end
return r0_0
