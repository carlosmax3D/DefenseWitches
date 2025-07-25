-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("logic.char.CharStatus")
local r1_0 = nil
if _G.GameMode == _G.GameModeEvo then
  r1_0 = require("evo.char_tbl.tbl_c12_sarah").CreateTable()
end
local r2_0 = {
  "afx12_buff01_0_0",
  "afx12_buff01_1_0",
  "afx12_buff01_1_1",
  "afx12_buff01_1_2",
  "afx12_buff01_1_3",
  "afx12_buff02_1_0",
  "afx12_buff02_1_1",
  "afx12_buff04_0_0",
  "c12_sarah_0_0",
  "c12_sarah_1_0",
  "c12_sarah_1_1",
  "c12_sarah_1_2",
  "c12_sarah_1_3",
  "c12_sarah_1_4",
  "c12_sarah_1_5"
}
local r3_0 = false
local r4_0 = require("char.c12.c12_sarah")
local r5_0 = "data/game/sarah"
local r6_0 = {
  require("char.c12.afx12_buff01"),
  require("char.c12.afx12_buff04"),
  require("char.c12.afx12_buff03")
}
local r7_0 = {}
local function r8_0(r0_1)
  -- line: [46, 64] id: 1
  for r4_1, r5_1 in pairs(r7_0) do
    for r9_1, r10_1 in pairs(r5_1.buff_target) do
      r10_1.buff_power = 1
    end
  end
  local r1_1 = _G.UserStatus[12]
  for r5_1, r6_1 in pairs(r7_0) do
    local r8_1 = (r1_1.power[r6_1.level] + 100) / 100
    if r0_1 == r6_1 then
      r8_1 = 1
    end
    for r12_1, r13_1 in pairs(r6_1.buff_target) do
      r13_1.buff_power = r13_1.buff_power * r8_1
    end
  end
end
local function r9_0(r0_2, r1_2, r2_2, r3_2)
  -- line: [66, 69] id: 2
  r0_2.xScale = r0_2.xScale * r3_2
  r0_2.yScale = r0_2.yScale * r3_2
end
local function r10_0(r0_3, r1_3)
  -- line: [71, 132] id: 3
  local r2_3 = r1_3.x
  local r3_3 = r1_3.y
  local r7_3 = _G.UserStatus[12].range[r1_3.level][2] * (char.GetRangePowerup() + 100) / 100
  local r8_3 = r7_3 * r7_3
  local r10_3 = nil
  local r11_3 = nil
  for r15_3, r16_3 in pairs(r0_0.MyChar) do
    if r16_3 == r1_3 then
      r10_3 = 2
    else
      r10_3 = 3
    end
    local r17_3 = r16_3.x
    local r18_3 = r16_3.y
    if (r2_3 - r17_3) * (r2_3 - r17_3) + (r3_3 - r18_3) * (r3_3 - r18_3) < r8_3 then
      if r10_3 == 3 then
        table.insert(r1_3.buff_target, r16_3)
      end
      if r16_3.buff_anm == nil then
        r16_3.buff_type = r10_3
        r11_3 = anime.Register(r6_0[3].GetData(), r17_3, r18_3, r5_0)
        _G.BuffRoot:insert(anime.GetSprite(r11_3))
        anime.Loop(r11_3, true)
        anime.Show(r11_3, true)
        r16_3.buff_anm = r11_3
      else
        r11_3 = r16_3.buff_anm
        anime.Pause(r11_3, false)
        anime.Loop(r11_3, true)
        anime.Show(r11_3, true)
      end
    end
  end
  if r1_3.buff_anm2 == nil then
    r11_3 = anime.Register(r6_0[2].GetData(), r2_3, r3_3, r5_0)
    _G.BuffRoot:insert(anime.GetSprite(r11_3))
    anime.RegisterShowHook(r11_3, r9_0, r7_3 / 248)
    anime.Loop(r11_3, true)
    anime.Show(r11_3, true)
    r1_3.buff_anm2 = r11_3
  else
    r11_3 = r1_3.buff_anm2
    anime.RegisterShowHook(r11_3, r9_0, r7_3 / 248)
    anime.Pause(r11_3, false)
    anime.Loop(r11_3, true)
    anime.Show(r11_3, true)
  end
  r8_0()
end
local function r11_0(r0_4, r1_4, r2_4)
  -- line: [134, 152] id: 4
  assert(not r1_4.shooting, debug.traceback())
  r1_4.shooting = true
  if r1_4.buff_anm then
    anime.Show(r1_4.buff_anm, false)
  end
  if r1_4.buff_anm2 then
    anime.Show(r1_4.buff_anm2, false)
  end
  r1_4.buff_target = {}
  local r3_4 = r1_4.anime
  anime.RegisterTrigger(r3_4, r1_4.shot_frame_nr, r10_0, r1_4)
  anime.Pause(r3_4, false)
  anime.Show(r3_4, true)
  r1_4.shot_ev = nil
  return false
end
local function r12_0(r0_5)
  -- line: [154, 167] id: 5
  r8_0(r0_5)
  for r4_5, r5_5 in pairs(r0_5.buff_target) do
    if r5_5.type ~= 12 and r5_5.buff_power < 1.2 and r5_5.buff_anm then
      anime.Remove(r5_5.buff_anm)
      r5_5.buff_anm = nil
    end
  end
  local r1_5 = table.indexOf(r7_0, r0_5)
  if r1_5 then
    table.remove(r7_0, r1_5)
  end
end
return setmetatable({
  Load = function(r0_6)
    -- line: [170, 213] id: 6
    if not r3_0 then
      preload.Load(r2_0, r5_0)
      r3_0 = true
    end
    local r1_6 = {}
    local r2_6 = display.newGroup()
    local r3_6 = r0_6.x
    local r4_6 = r0_6.y
    local r5_6 = anime.Register(r4_0.GetData(), r3_6, r4_6, r5_0)
    local r6_6 = anime.GetSprite(r5_6)
    local r7_6 = display.newRect(_G.MyTgRoot, r3_6 - 40, r4_6 - 40, 80, 80)
    r7_6.alpha = 0.01
    r7_6.struct = r0_6
    r7_6.touch = r0_6.func.circle
    r7_6:addEventListener("touch", r7_6)
    r0_6.touch_area = r7_6
    r0_6.anime = r5_6
    r0_6.spr = r6_6
    function r0_6.func.load(r0_7, r1_7, r2_7, r3_7)
      -- line: [191, 191] id: 7
      local r4_7 = nil	-- notice: implicit variable refs by block#[0]
      return r4_7
    end
    r0_6.shot_frame_nr = 30
    r0_6.func.release = r12_0
    r0_6.func.range = r11_0
    function r0_6.func.pointing(r0_8, r1_8)
      -- line: [196, 196] id: 8
      return 1
    end
    function r0_6.func.shoot(r0_9, r1_9, r2_9)
      -- line: [197, 197] id: 9
      return true
    end
    function r0_6.func.check(r0_10, r1_10)
      -- line: [198, 198] id: 10
      return true
    end
    r0_6.buff_target = {}
    anime.RegisterFinish(r5_6, function(r0_11, r1_11)
      -- line: [202, 202] id: 11
      anime.SetTimer(r0_11, r0_11.stop)
    end, r0_6)
    table.insert(r7_0, r0_6)
    if _G.GameMode == _G.GameModeEvo then
      r0_6.func.rankTable = r1_0
    end
    return r0_6
  end,
  Reload = function(r0_13)
    -- line: [220, 249] id: 13
    local r1_13 = r0_13.x
    local r2_13 = r0_13.y
    local r3_13 = _G.UserStatus[12]
    local r4_13 = (char.GetRangePowerup() + 100) / 100
    local r5_13 = nil
    for r9_13, r10_13 in pairs(r7_0) do
      if r0_13 ~= r10_13 then
        local r12_13 = r3_13.range[r10_13.level][2] * r4_13
        local r14_13 = r10_13.x
        local r15_13 = r10_13.y
        if (r1_13 - r14_13) * (r1_13 - r14_13) + (r2_13 - r15_13) * (r2_13 - r15_13) < r12_13 * r12_13 then
          table.insert(r10_13.buff_target, r0_13)
          if r0_13.buff_type == 0 then
            r0_13.buff_type = 3
            r5_13 = anime.Register(r6_0[3].GetData(), r1_13, r2_13, r5_0)
            _G.BuffRoot:insert(anime.GetSprite(r5_13))
            anime.Loop(r5_13, true)
            anime.Show(r5_13, true)
            r0_13.buff_anm = r5_13
          end
        end
      end
    end
    r8_0()
  end,
  Cleanup = function()
    -- line: [215, 218] id: 12
    r3_0 = false
    r7_0 = {}
  end,
  custom_upgrade_ok = function(r0_14, r1_14)
    -- line: [251, 255] id: 14
    r0_14.shooting = false
    r0_14.func.range(nil, r0_14, -1)
    return false
  end,
}, {
  __index = require("char.Char"),
})
