-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("logic.char.CharStatus")
local r1_0 = nil
if _G.GameMode == _G.GameModeEvo then
  r1_0 = require("evo.char_tbl.tbl_c13_luna").CreateTable()
end
local r2_0 = {
  "c13_luna01_0_0",
  "c13_luna01_1_0",
  "c13_luna01_2_0",
  "c13_luna01_2_1",
  "c13_luna01_2_2",
  "c13_luna01_2_3",
  "c13_luna01_2_31",
  "c13_luna01_2_4",
  "c13_luna01_2_5",
  "c13_luna01_2_6",
  "c13_luna01_2_7",
  "c13_luna01_2_8",
  "c13_luna01_2_9",
  "c13_luna01_3_0",
  "c13_luna01_3_1",
  "c13_luna01_3_10",
  "c13_luna01_3_11",
  "c13_luna01_3_12",
  "c13_luna01_3_13",
  "c13_luna01_3_14",
  "c13_luna01_3_15",
  "c13_luna01_3_16",
  "c13_luna01_3_17",
  "c13_luna01_3_18",
  "c13_luna01_3_19",
  "c13_luna01_3_2",
  "c13_luna01_3_20",
  "c13_luna01_3_21",
  "c13_luna01_3_22",
  "c13_luna01_3_23",
  "c13_luna01_3_24",
  "c13_luna01_3_25",
  "c13_luna01_3_26",
  "c13_luna01_3_27",
  "c13_luna01_3_28",
  "c13_luna01_3_29",
  "c13_luna01_3_3",
  "c13_luna01_3_4",
  "c13_luna01_3_5",
  "c13_luna01_3_6",
  "c13_luna01_3_7",
  "c13_luna01_3_8",
  "c13_luna01_3_9"
}
local r3_0 = false
local r4_0 = require("char.c13.c13_luna01")
local r5_0 = "data/game/luna"
local r6_0 = _G.CharaParam[13][1]
local function r7_0(r0_1, r1_1, r2_1)
  -- line: [70, 88] id: 1
  if game ~= nil and game.IsNotPauseTypeNone() then
    return true
  end
  local r3_1 = r1_1
  local r4_1 = r3_1.rigidity_ms + r2_1
  if r4_1 < r3_1.rigidity_es then
    r3_1.rigidity_ms = r4_1
    return true
  end
  r3_1.rigidity_ev = nil
  r3_1.shooting = false
  return false
end
local function r8_0(r0_2, r1_2)
  -- line: [91, 106] id: 2
  local r2_2 = r1_2.level
  game.AddMP(r1_2.power[r2_2], r1_2.x, r1_2.y, 1.2)
  anime.Pause(r0_2, true)
  anime.SetTimer(r0_2, 0)
  r1_2.rigidity_ms = 0
  r1_2.rigidity_es = r6_0[r2_2]
  r1_2.rigidity_ev = events.Register(r7_0, r1_2, 1)
end
local function r9_0(r0_3, r1_3)
  -- line: [109, 111] id: 3
  return 1
end
local function r10_0(r0_4, r1_4, r2_4, r3_4)
  -- line: [114, 186] id: 4
  local r4_4 = display.newGroup()
  local r5_4 = {}
  local r6_4 = nil
  local r7_4 = nil
  local r8_4 = nil
  local r9_4 = nil
  local r10_4 = nil
  local r11_4 = nil
  local r12_4 = nil
  for r16_4, r17_4 in pairs(r0_4) do
    r7_4 = {}
    r10_4 = r17_4.name
    r8_4 = r3_4 .. "/" .. r17_4.name
    if string.find(r10_4, "c13_luna01_3_") then
      r9_4 = display.newGroup()
      r9_4.top = 0
      r9_4.left = 0
      r9_4.right = 68
      r9_4.bottom = 32
      display.newImage(r9_4, r8_4, 0, 0)
      r9_4:setReferencePoint(display.CenterRightReferencePoint)
      r11_4 = {}
      for r21_4, r22_4 in pairs(r17_4.pos) do
        table.insert(r11_4, {
          r22_4[1] + 36,
          r22_4[2]
        })
      end
    else
      r9_4 = display.newImage(r8_4)
      r9_4:setReferencePoint(display.CenterReferencePoint)
      r11_4 = r17_4.pos
    end
    r9_4.x = r1_4
    r9_4.y = r2_4
    r9_4.isVisible = false
    r4_4:insert(r9_4)
    r7_4.spr = r9_4
    r7_4.start = r17_4.start
    r7_4.stop = r17_4.stop
    r7_4.scale = r17_4.scale
    r7_4.alpha = r17_4.alpha
    r7_4.angle = r17_4.angle
    r7_4.pos = r11_4
    table.insert(r5_4, r7_4)
    if r6_4 == nil or r6_4 < r17_4.stop then
      r6_4 = r17_4.stop
    end
  end
  local r13_4 = {
    sprite = r4_4,
    timer = 0,
    stop = r6_4,
    show = false,
    layers = r5_4,
    pause = false,
    trigger = nil,
    trigger_param = nil,
    finish = nil,
    finish_param = nil,
    show_hook = nil,
    show_hook_param = nil,
    show_hook2 = nil,
    show_hook2_param = nil,
    xy = {
      r1_4,
      r2_4
    },
    offset_x_y = {
      0,
      0
    },
    image_scale_x_y = {
      1,
      1
    },
    scale = 1,
    name = "SpriteAnimation",
    playing = false,
    loop = false,
  }
  anime.RegisterCallback(r13_4)
  return r13_4
end
local function r11_0(r0_5)
  -- line: [188, 193] id: 5
  if r0_5.rigidity_ev then
    events.Delete(r0_5.rigidity_ev)
    r0_5.rigidity_ev = nil
  end
end
local function r12_0(r0_6)
  -- line: [196, 204] id: 6
  local r1_6 = {
    timer = anime.GetTimer(r0_6.anime),
    ms = r0_6.rigidity_ms,
    es = r0_6.rigidity_es,
    flag = r0_6.rigidity_ev ~= nil,
  }
  return r1_6
end
local function r13_0(r0_7, r1_7)
  -- line: [207, 232] id: 7
  if r1_7 == nil then
    return 
  end
  local r2_7 = nil
  local r3_7 = nil
  local r4_7 = nil
  local r5_7 = nil
  r2_7 = r1_7.timer
  r3_7 = r1_7.ms
  r4_7 = r1_7.es
  local r6_7 = r0_7.anime
  local r7_7 = r0_7.level
  if r1_7.flag then
    anime.Pause(r6_7, true)
    anime.SetTimer(r6_7, 0)
    r0_7.rigidity_ms = r3_7
    assert(r4_7 == r6_0[r7_7], debug.traceback())
    r0_7.rigidity_es = r4_7
    r0_7.rigidity_ev = events.Register(r7_0, r0_7, 1)
  else
    anime.SetTimer(r6_7, r2_7)
    r0_7.resume_timer = r2_7
  end
end
local function r14_0(r0_8, r1_8, r2_8, r3_8)
  -- line: [235, 237] id: 8
  r3_8.sort_z = r0_8.x + r0_8.y * 1000
end
local function r15_0(r0_9, r1_9)
  -- line: [239, 278] id: 9
  for r13_9, r14_9 in pairs(r0_9.anime.layers) do
    local r9_9 = r4_0.GetData()[r13_9]
    if r9_9 == nil then
      return 
    end
    local r5_9 = r14_9.spr.x
    local r6_9 = r14_9.spr.y
    local r7_9 = r14_9.spr.isVisible
    local r8_9 = r1_9 .. "/luna/" .. r9_9.name
    if r14_9.spr.numChildren ~= nil and 0 < r14_9.spr.numChildren then
      for r18_9 = 1, r14_9.spr.numChildren, 1 do
        display.remove(r14_9.spr[r18_9])
        r14_9.spr[r18_9] = nil
      end
      display.newImage(r14_9.spr, r8_9, 0, 0)
    else
      display.remove(r14_9.spr)
      r14_9.spr = nil
      r14_9.spr = display.newImage(r8_9)
      r14_9.spr:setReferencePoint(display.CenterReferencePoint)
      r14_9.spr.x = r5_9
      r14_9.spr.y = r6_9
      r14_9.spr.isVisible = r7_9
      r0_9.anime.sprite:insert(r14_9.spr)
    end
  end
  return r0_9
end
local r18_0 = require("char.Char")
return setmetatable({
  ChangeSprite = r15_0,
  Load = function(r0_10)
    -- line: [281, 339] id: 10
    if not r3_0 then
      preload.Load(r2_0, r5_0)
      r3_0 = true
    end
    local r1_10 = r0_10.x
    local r2_10 = r0_10.y
    local r3_10 = r10_0(r4_0.GetData(), r1_10, r2_10, r5_0)
    local r4_10 = anime.GetSprite(r3_10)
    anime.RegisterShowHook(r3_10, r14_0, r0_10)
    local r5_10 = display.newRect(_G.MyTgRoot, r1_10 - 40, r2_10 - 40, 80, 80)
    r5_10.alpha = 0.01
    r5_10.struct = r0_10
    r5_10.touch = r0_10.func.circle
    r5_10:addEventListener("touch", r5_10)
    r0_10.touch_area = r5_10
    r0_10.anime = r3_10
    r0_10.spr = r4_10
    function r0_10.func.range(r0_11, r1_11, r2_11)
      -- line: [302, 320] id: 11
      if game ~= nil and game.IsNotPauseTypeNone() then
        return true
      end
      if r1_11.shooting then
        return true
      end
      r1_11.shooting = true
      local r3_11 = r1_11.anime
      anime.Pause(r3_11, false)
      local r4_11 = r1_11.resume_timer
      if r4_11 then
        anime.Show(r3_11, true, {
          timer = r4_11,
        })
        r1_11.resume_timer = nil
      else
        anime.Show(r3_11, true)
      end
      anime.RegisterTrigger(r3_11, 149, r8_0, r1_11)
      return true
    end
    r0_10.func.pointing = r9_0
    r0_10.func.release = r11_0
    r0_10.func.get_resume_data = r12_0
    r0_10.func.set_first = r13_0
    r0_10.rigidity_ms = 0
    r0_10.rigidity_es = 0
    r0_10.rigidity_ev = nil
    r0_10.resume_timer = nil
    if _G.GameMode == _G.GameModeEvo then
      r0_10.func.changeSprite = r15_0
      r0_10.func.rankTable = r1_0
    end
    return r0_10
  end,
  Cleanup = function()
    -- line: [341, 343] id: 12
    r3_0 = false
  end,
  custom_release_ok = function(r0_13)
    -- line: [346, 349] id: 13
    r0_0.LunaOnlyOne = false
    return false
  end,
  custom_crystal_status = function(r0_14, r1_14, r2_14)
    -- line: [351, 357] id: 14
    if r0_0.LunaOnlyOne then
      r0_0.SummonCrystalStatus[r2_14] = 4
    else
      return r18_0.custom_crystal_status(r0_14, r1_14, r2_14)
    end
  end,
  custom_summon_status = function(r0_15, r1_15, r2_15, r3_15)
    -- line: [359, 365] id: 15
    if r0_0.LunaOnlyOne then
      r0_0.SummonStatus[r2_15] = 4
    else
      return r18_0.custom_summon_status(r0_15, r1_15, r2_15, r3_15)
    end
  end,
  CustomFirstSummon = function(r0_16, r1_16, r2_16, r3_16, r4_16, r5_16)
    -- line: [367, 387] id: 16
    r0_0.SummonPos = {
      r1_16,
      r2_16
    }
    r18_0.summon_touch({
      id = r0_16,
      fast_summon = true,
      level = r3_16,
      no_skip_summon = r4_16,
    }, {
      phase = "ended",
    })
    local r8_16 = nil
    for r12_16, r13_16 in pairs(r0_0.MyChar) do
      r8_16 = r13_16.map_xy
      if r8_16[1] == r1_16 and r8_16[2] == r2_16 then
        r13_16.func.set_first(r13_16, r5_16[5])
        break
      end
    end
  end,
}, {
  __index = r18_0,
})
