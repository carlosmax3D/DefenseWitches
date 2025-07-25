-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local function r0_0(r0_1)
  -- line: [9, 137] id: 1
  local r1_1 = {}
  local function r2_1()
    -- line: [16, 16] id: 2
    return "viewerBackground"
  end
  local function r3_1()
    -- line: [17, 17] id: 3
    return "bingoCardListGroup"
  end
  local function r4_1()
    -- line: [19, 19] id: 4
    return "currentBingoCard"
  end
  local r5_1 = require("common.base_dialog")
  local r6_1 = nil
  local r7_1 = nil
  local function r8_1(r0_5)
    -- line: [34, 34] id: 5
    return "data/option/" .. r0_5 .. ".png"
  end
  local function r9_1(r0_6, r1_6)
    -- line: [38, 51] id: 6
    _G.BingoManager.isBingoEnabled(r1_6, function(r0_7)
      -- line: [39, 49] id: 7
      if r0_7 == false then
        return 
      end
      require("bingo.bingo_card").new({
        rootLayer = r0_6,
        bingoCardId = r1_6,
      }).show(true, false)
    end)
  end
  local function r10_1(r0_8)
    -- line: [55, 58] id: 8
    sound.PlaySE(2)
    util.ChangeScene({
      scene = r7_1,
      efx = "fade",
    })
  end
  local function r11_1(r0_9)
    -- line: [62, 66] id: 9
    local r1_9 = require("stage_ui.stage_help")
    r1_9.Init()
    r1_9.Open(10)
  end
  local function r12_1(r0_10, r1_10)
    -- line: [70, 98] id: 10
    local r2_10 = display.newGroup()
    local r3_10 = display.newGroup()
    local r4_10 = display.newGroup()
    r0_10:insert(r2_10)
    r0_10:insert(r3_10)
    r0_10:insert(r4_10)
    r0_10[r2_1()] = r2_10
    r0_10[r4_1()] = r3_10
    r0_10[r3_1()] = r4_10
    local r5_10 = r5_1.Create(r0_10[r2_1()], _G.Width, _G.Height + 10, {
      direction = "down",
      color = {
        {
          0,
          153,
          119
        },
        {
          0,
          51,
          68
        }
      },
      alpha = 1,
    }, true, false)
    r9_1(r0_10[r4_1()], r1_10)
    local r6_10 = r5_1.CreateCloseButton(r0_10[r2_1()], _G.Width - 42, 42, r10_1)
    local r7_10 = util.LoadBtn({
      rtImg = r0_10[r2_1()],
      fname = r8_1("help"),
      cx = _G.Width - 150,
      cy = 42,
      func = r11_1,
    })
    local r8_10 = nil
    r8_10 = util.MakeFrame(r0_10)
  end
  local function r13_1(r0_11)
    -- line: [102, 114] id: 11
    if r0_11 == nil then
      return 
    end
    if r0_11.parentLayer ~= nil then
      r6_1 = r0_11.parentLayer
    end
    if r0_11.back ~= nil then
      r7_1 = r0_11.back
    end
  end
  (function()
    -- line: [118, 126] id: 12
    r13_1(r0_1)
    if r6_1 == nil then
      r6_1 = display.newGroup()
    end
    r12_1(r6_1, _G.BingoManager.getBingoCardId())
  end)()
  return r1_1
end
return {
  new = function(r0_13)
    -- line: [142, 153] id: 13
    local r1_13 = require("common.already_read_manager")
    r1_13.setState(r1_13.EVENT_ID.BINGO_CARD_01(), r1_13.STATE.ALREADY_READ())
    local r2_13 = display.newGroup()
    local r3_13 = util.MakeGroup(r2_13)
    local r4_13 = r0_0({
      parentLayer = r2_13,
      back = r0_13.back,
    })
    return r3_13
  end,
  Cleanup = function()
    -- line: [158, 159] id: 14
  end,
}
