-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("logic.game.GameStatus")
local r1_0 = require("logic.char.CharStatus")
local r2_0 = require("logic.game.BaseGame")
local r3_0 = require("tool.tutorial_manager")
return {
  ViewTicketCount = function()
    -- line: [8, 93] id: 1
    if _G.UserInquiryID == nil or r3_0.IsGameStartTutorial ~= false or r3_0.IsSummonTutorial ~= false or r3_0.IsUpgradeTutorial ~= false or r3_0.IsOrbTutorial ~= false then
      return 
    end
    local function r0_1(r0_2)
      -- line: [18, 18] id: 2
      return "data/stage/" .. r0_2 .. ".png"
    end
    local function r1_1(r0_3)
      -- line: [20, 20] id: 3
      return r0_1(r0_3 .. _G.UILanguage)
    end
    local r2_1 = db.GetInventoryItem(_G.UserInquiryID, _G.LoginItems["3"].id)
    local r3_1 = db.GetInventoryItem(_G.UserInquiryID, _G.LoginItems["2"].id)
    if not r2_1 then
      r2_1 = 0
    end
    if not r3_1 then
      r3_1 = 0
    end
    local function r4_1(r0_4, r1_4, r2_4, r3_4)
      -- line: [32, 49] id: 4
      local r4_4 = 12
      local r5_4 = r1_4
      local r6_4 = r2_4 + string.len(tostring(r1_4)) * r4_4
      if r5_4 < 1 then
        r0_0.SpriteNumber01.CreateImage(r0_4, r0_0.SpriteNumber01.sequenceNames.Score, r0_0.SpriteNumber01.frameDefines.ScoreStart, r6_4, r3_4)
        return 
      end
      while 0 < r5_4 do
        r5_4 = math.floor(r5_4 * 0.1)
        r0_0.SpriteNumber01.CreateImage(r0_4, r0_0.SpriteNumber01.sequenceNames.Score, r0_0.SpriteNumber01.frameDefines.ScoreStart + r5_4 % 10, r6_4, r3_4)
        r6_4 = r6_4 - r4_4
      end
    end
    local function r5_1(r0_5)
      -- line: [52, 58] id: 5
      sound.PlaySE(2)
      r0_0.MedalClass.MedalConditionDialog.new({
        mapId = _G.MapSelect,
        stageId = _G.StageSelect,
      }).Show(true)
      return true
    end
    if r0_0.TicketDisplayGroup ~= nil then
    end
    r0_0.TicketDisplayGroup = display.newGroup()
    local r6_1 = display.newRect(r0_0.TicketDisplayGroup, 0, 0, 390, 65)
    r6_1:setFillColor(0, 0, 0)
    r6_1.alpha = 0.5
    local r7_1 = graphics.newGradient({
      0,
      0,
      0,
      255
    }, {
      0,
      0,
      0,
      0
    }, "left")
    local r8_1 = display.newRect(r0_0.TicketDisplayGroup, 390, 0, 80, 65)
    r8_1:setFillColor(r7_1)
    r8_1.alpha = 0.5
    util.LoadParts(r0_0.TicketDisplayGroup, "data/login_bonus/receive/ticket_rewind_s.png", 0, 5)
    r0_0.SpriteNumber01.CreateImage(r0_0.TicketDisplayGroup, r0_0.SpriteNumber01.sequenceNames.Score, r0_0.SpriteNumber01.frameDefines.ScoreCross, 40, 35)
    r4_1(r0_0.TicketDisplayGroup, r2_1, 42, 35)
    util.LoadParts(r0_0.TicketDisplayGroup, "data/login_bonus/receive/ticket_flare_s.png", 80, 5)
    r0_0.SpriteNumber01.CreateImage(r0_0.TicketDisplayGroup, r0_0.SpriteNumber01.sequenceNames.Score, r0_0.SpriteNumber01.frameDefines.ScoreCross, 120, 35)
    r4_1(r0_0.TicketDisplayGroup, r3_1, 122, 35)
    local r9_1 = util.LoadBtn({
      rtImg = r0_0.TicketDisplayGroup,
      fname = r1_1("button_medal_"),
      x = 180,
      y = 0,
      func = r5_1,
    })
    _G.TickerRoot:insert(r0_0.TicketDisplayGroup)
    r0_0.TicketDisplayGroup.x = 0
    r0_0.TicketDisplayGroup.y = _G.Height - r0_0.TicketDisplayGroup.height
  end,
}
