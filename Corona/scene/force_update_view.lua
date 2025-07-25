-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local function r0_0(r0_1)
  -- line: [10, 97] id: 1
  local r1_1 = {}
  local function r2_1()
    -- line: [17, 17] id: 2
    return "bg_title_01"
  end
  local r3_1 = require("common.base_dialog")
  local r4_1 = nil
  local r5_1 = nil
  local function r6_1(r0_3)
    -- line: [32, 32] id: 3
    return "data/option/" .. r0_3 .. ".png"
  end
  local function r7_1(r0_4)
    -- line: [33, 33] id: 4
    return "data/cont/" .. r0_4 .. ".jpg"
  end
  local function r8_1(r0_5)
    -- line: [37, 58] id: 5
    local r1_5 = display.newGroup()
    r0_5:insert(r1_5)
    r0_5[r2_1()] = r1_5
    util.LoadParts(r0_5, r7_1("bg_title_01"), 0, 0)
    local r2_5 = nil
    r2_5 = util.MakeFrame(r0_5)
    dialog.OpenOkButtonDialog(r0_5, {
      486
    }, {
      function(r0_6)
        -- line: [51, 56] id: 6
        if not IsSimulator then
          native.showPopup("appStore", {
            iOSAppId = "507366983",
          })
        end
      end
    })
  end
  local function r9_1(r0_7)
    -- line: [62, 74] id: 7
    if r0_7 == nil then
      return 
    end
    if r0_7.parentLayer ~= nil then
      r4_1 = r0_7.parentLayer
    end
    if r0_7.back ~= nil then
      r5_1 = r0_7.back
    end
  end
  (function()
    -- line: [78, 86] id: 8
    r9_1(r0_1)
    if r4_1 == nil then
      r4_1 = display.newGroup()
    end
    r8_1(r4_1)
  end)()
  return r1_1
end
return {
  new = function(r0_9)
    -- line: [102, 108] id: 9
    local r1_9 = display.newGroup()
    local r2_9 = util.MakeGroup(r1_9)
    local r3_9 = r0_0({
      parentLayer = r1_9,
      back = r0_9.back,
    })
    return r2_9
  end,
  Cleanup = function()
    -- line: [113, 114] id: 10
  end,
}
