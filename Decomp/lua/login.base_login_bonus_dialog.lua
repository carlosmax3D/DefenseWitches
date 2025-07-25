-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
module(..., package.seeall)
base_login_bonus_dialog = {}
function base_login_bonus_dialog.new()
  -- line: [13, 194] id: 1
  local r0_1 = {}
  local r1_1 = nil
  local r2_1 = nil
  local r3_1 = nil
  local r4_1 = nil
  local r5_1 = 26
  local r6_1 = 32
  function r0_1.parseMessage(r0_2, r1_2, r2_2)
    -- line: [30, 52] id: 2
    local r3_2 = 0
    local r4_2 = {}
    local r5_2 = nil
    for r9_2, r10_2 in pairs(r1_2) do
      for r15_2, r16_2 in pairs(util.StringSplit(string.gsub(r10_2, "(\\n)", function(r0_3)
        -- line: [38, 38] id: 3
        return "\n"
      end), "\n")) do
        r5_2 = display.newText(r16_2, 0, 0, native.SystemFont, r2_2)
        if r3_2 < r5_2.width then
          r3_2 = r5_2.width
        end
        table.insert(r4_2, r5_2)
      end
    end
    return r3_2, r4_2
  end
  local function r7_1(r0_4, r1_4, r2_4, r3_4, r4_4)
    -- line: [57, 69] id: 4
    local r5_4 = nil	-- notice: implicit variable refs by block#[3]
    if type(r4_4) == "string" then
      r5_4 = display.newText(r0_4, r4_4, 0, 0, native.systemFont, r3_4)
    else
      r5_4 = r4_4
      r0_4:insert(r4_4)
    end
    r5_4:setReferencePoint(display.TopLeftReferencePoint)
    r5_4.x = r1_4
    r5_4.y = r2_4
    r5_4:setFillColor(255, 255, 255)
  end
  function r0_1.setMessage(r0_5, r1_5, r2_5, r3_5, r4_5, r5_5)
    -- line: [74, 103] id: 5
    local r6_5 = {}
    for r10_5, r11_5 in pairs(r3_5) do
      if type(r11_5) == "string" then
        table.insert(r6_5, r11_5)
      else
        table.insert(r6_5, db.GetMessage(r11_5))
      end
    end
    local r7_5 = nil
    local r8_5 = nil
    if r4_5 ~= nil then
      r7_5, r8_5 = r0_1.parseMessage(r0_5, r6_5, r4_5)
    else
      r7_5, r8_5 = r0_1.parseMessage(r0_5, r6_5, r5_1)
    end
    local r9_5 = table.maxn(r6_5)
    local r10_5 = nil
    if r5_5 ~= nil then
      r10_5 = r5_5
    else
      r10_5 = r6_1
    end
    for r14_5, r15_5 in pairs(r8_5) do
      r7_1(r0_5, r1_5, r2_5 + r10_5 * (r14_5 - 1), r5_1, r15_5)
    end
  end
  function r0_1.loadGround(r0_6)
    -- line: [105, 124] id: 6
    r1_1 = display.newGroup()
    r1_1.isVisible = false
    local r1_6 = util.MakeGroup(r1_1)
    r3_1 = display.newRect(r1_1, 0, 0, _G.Width, _G.Height)
    r3_1:setFillColor(0, 0, 0, 124)
    r3_1.isVisible = false
    r2_1 = display.newGroup()
    util.LoadParts(r2_1, r0_6, 0, 0)
    r4_1 = util.MakeMat(r1_6)
    r1_1:insert(r3_1)
    r1_1:insert(r2_1)
  end
  function r0_1.getDialogObj()
    -- line: [129, 131] id: 7
    return r2_1
  end
  function r0_1.close()
    -- line: [133, 147] id: 8
    r1_1.isVisible = false
    if r4_1 then
      display.remove(r4_1)
      r4_1 = nil
    end
    if r2_1 then
      display.remove(r2_1)
      r2_1 = nil
    end
    if r1_1 then
      display.remove(r1_1)
      r1_1 = nil
    end
  end
  function r0_1.show()
    -- line: [152, 168] id: 9
    if r1_1 == nil then
      return 
    end
    r1_1.isVisible = true
    r3_1.alpha = 0
    r3_1.isVisible = true
    transition.to(r3_1, {
      time = 200,
      alpha = 1,
      onComplete = function()
        -- line: [164, 166] id: 10
        DebugPrint("super:show()")
      end,
    })
  end
  function r0_1.hide()
    -- line: [173, 182] id: 11
    r3_1.alpha = 1
    transition.to(r3_1, {
      time = 300,
      alpha = 0,
      onComplete = function()
        -- line: [178, 180] id: 12
        r1_1.isVisible = false
      end,
    })
  end
  function r0_1.init()
    -- line: [187, 191] id: 13
    r1_1 = {}
    r2_1 = {}
    r4_1 = {}
  end
  return r0_1
end
