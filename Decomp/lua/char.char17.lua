-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("logic.char.CharStatus")
local r1_0 = require("logic.game.GameStatus")
local r2_0 = nil
if _G.GameMode == _G.GameModeEvo then
  r2_0 = require("evo.char_tbl.tbl_c17_kala").CreateTable()
end
local r3_0 = {
  "c17_kala01_0_0",
  "c17_kala01_0_1",
  "c17_kala01_0_2",
  "c17_kala01_0_3",
  "c17_kala02_0_0",
  "c17_kala02_0_1",
  "c17_kala02_0_2",
  "c17_kala02_0_3",
  "c17_kala02_0_4",
  "c17_kala02_0_5",
  "c17_kala02_0_6",
  "c17_kala02_0_7",
  "c17_kala02_0_8",
  "c17_kala03_0_0",
  "c17_kala03_0_1",
  "c17_kala03_0_2",
  "c17_kala03_0_3",
  "c17_kala03_0_4",
  "c17_kala03_0_5",
  "c17_kala03_0_6"
}
local r4_0 = false
local r5_0 = nil
local r6_0 = nil
local r7_0 = require("char.c17.c17_kala01")
local r8_0 = require("char.c17.c17_kala02")
local r9_0 = require("char.c17.c17_kala03")
local r10_0 = "data/game/kala"
local function r11_0(r0_1, r1_1, r2_1)
  -- line: [52, 107] id: 1
  local r3_1 = display.newGroup()
  local r4_1 = {}
  local r5_1 = nil
  local r6_1 = nil
  local r7_1 = nil
  local r8_1 = nil
  local r9_1 = nil
  local r10_1 = nil
  local r11_1 = nil
  for r15_1, r16_1 in pairs(r0_1) do
    r6_1 = {}
    r9_1 = r16_1.name
    r7_1 = r10_0 .. "/" .. r16_1.name
    r8_1 = display.newImage(r7_1)
    r8_1:setReferencePoint(display.CenterReferencePoint)
    r10_1 = r16_1.pos
    r8_1.x = r1_1
    r8_1.y = r2_1
    r8_1.isVisible = false
    r3_1:insert(r8_1)
    r6_1.spr = r8_1
    r6_1.start = r16_1.start
    r6_1.stop = r16_1.stop
    r6_1.scale = r16_1.scale
    r6_1.alpha = r16_1.alpha
    r6_1.angle = r16_1.angle
    r6_1.pos = r10_1
    table.insert(r4_1, r6_1)
    if r5_1 == nil or r5_1 < r16_1.stop then
      r5_1 = r16_1.stop
    end
  end
  local r12_1 = {
    sprite = r3_1,
    timer = 0,
    stop = r5_1,
    show = false,
    layers = r4_1,
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
      r1_1,
      r2_1
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
  anime.RegisterCallback(r12_1)
  return r12_1
end
local function r12_0(r0_2)
  -- line: [109, 111] id: 2
  r0_0.KalaStruct = nil
end
local function r13_0(r0_3)
  -- line: [114, 122] id: 3
  local r1_3 = {
    timer = anime.GetTimer(r0_3.anime),
    ms = r0_3.rigidity_ms,
    es = r0_3.rigidity_es,
    flag = r0_3.rigidity_ev ~= nil,
  }
  return r1_3
end
local function r14_0(r0_4, r1_4)
  -- line: [125, 139] id: 4
  if r1_4 == nil then
    return 
  end
  local r2_4 = nil
  local r3_4 = nil
  local r4_4 = nil
  local r5_4 = nil
  r2_4 = r1_4.timer
  r3_4 = r1_4.ms
  r4_4 = r1_4.es
  r5_4 = r1_4.flag
  local r7_4 = r0_4.level
  anime.SetTimer(r0_4.anime, r2_4)
  r0_4.resume_timer = r2_4
end
local function r15_0(r0_5, r1_5)
  -- line: [143, 203] id: 5
  if not r4_0 then
    preload.Load(r3_0, r10_0)
    r4_0 = true
  end
  local r2_5 = r0_5.x
  local r3_5 = r0_5.y
  local r4_5 = nil
  if r1_5 == nil then
    r1_5 = 1
  end
  if r1_5 == 1 then
    r4_5 = r11_0(r7_0.GetData(), r2_5, r3_5)
    r4_5.loop = true
  elseif r1_5 == 2 then
    r4_5 = r11_0(r8_0.GetData(), r2_5, r3_5)
  else
    r4_5 = r11_0(r9_0.GetData(), r2_5, r3_5)
  end
  r5_0 = anime.GetSprite(r4_5)
  r6_0 = display.newRect(_G.MyTgRoot, r2_5 - 40, r3_5 - 40, 80, 80)
  r6_0.alpha = 0.01
  r6_0.struct = r0_5
  r6_0.touch = r0_5.func.circle
  r6_0:addEventListener("touch", r6_0)
  r0_5.touch_area = r6_0
  r0_5.anime = r4_5
  r0_5.spr = r5_0
  function r0_5.func.range(r0_6, r1_6, r2_6)
    -- line: [172, 189] id: 6
    if game ~= nil and game.IsNotPauseTypeNone() then
      return true
    end
    if r1_6.shooting then
      return true
    end
    r1_6.shooting = true
    local r3_6 = r1_6.anime
    anime.Pause(r3_6, false)
    local r4_6 = r1_6.resume_timer
    if r4_6 then
      anime.Show(r3_6, true, {
        timer = r4_6,
      })
      r1_6.resume_timer = nil
    else
      anime.Show(r3_6, true)
    end
    return true
  end
  r0_5.func.release = r12_0
  r0_5.func.get_resume_data = r13_0
  r0_5.func.set_first = r14_0
  r0_5.resume_timer = nil
  if _G.GameMode == _G.GameModeEvo then
    r0_5.func.rankTable = r2_0
  end
  return r0_5
end
local r18_0 = require("char.Char")
local function r19_0()
  -- line: [240, 249] id: 9
  if r0_0.SummonPlateGroup then
    r18_0.close_plate()
  end
  game.StopClock()
  return true
end
local function r20_0(r0_10)
  -- line: [251, 259] id: 10
  if r0_10 < 319 then
    r0_0.UpgradeBtn[10].x = 590
    r0_0.UpgradeBtn[10].y = 85
  else
    r0_0.UpgradeBtn[10].x = 590
    r0_0.UpgradeBtn[10].y = 410
  end
end
return setmetatable({
  Load = r15_0,
  Reload = function(r0_7, r1_7)
    -- line: [205, 231] id: 7
    display.remove(r5_0)
    display.remove(r6_0)
    r15_0(r0_7, r1_7)
    local r2_7 = r0_7.x
    local r3_7 = r0_7.y
    anime.Show(r0_7.anime, false)
    anime.SetTimer(r0_7.anime, 0)
    anime.Pause(r0_7.anime, false)
    if _G.GameMode == _G.GameModeEvo and r0_7.evoLevel ~= nil and 0 < r0_7.evoLevel then
      anime.Show(r0_7.anime, true, {
        scale = 1.7,
      })
    else
      anime.Show(r0_7.anime, true)
    end
    r0_0.KalaStruct = r0_7
    local r4_7 = display.newGroup()
    r4_7:insert(r0_7.spr)
    r0_7.sort_sprite = r4_7
    r0_7.sort_z = r2_7 + r3_7 * 1000
    _G.MyRoot:insert(r0_7.sort_sprite)
  end,
  Cleanup = function()
    -- line: [233, 235] id: 8
    r4_0 = false
  end,
  coustom_skill_touch_func = function(r0_11)
    -- line: [262, 265] id: 11
    r19_0()
    return true
  end,
  coustom_circle_touch_func = function(r0_12, r1_12, r2_12)
    -- line: [267, 277] id: 12
    r0_0.CircleTransition = transition.to(r0_0.CircleSpr, {
      time = 0,
      rotation = 0,
      transition = easing.outQuad,
      onComplete = r18_0.circle_rolling,
    })
    r0_0.CircleTransition.isVisible = false
    return true
  end,
  custom_popup_upgrade_btn = function(r0_13)
    -- line: [279, 285] id: 13
    if game ~= nil and game.GetPauseType() ~= r1_0.PauseTypeFirstPause then
      r20_0(r0_13.y)
      r0_0.UpgradeBtn[10].isVisible = true
      r0_0.UpgradeBtn[10].struct = r0_13
    end
  end,
  custom_make_upgrade_btn = function()
    -- line: [287, 304] id: 14
    local function r1_14(r0_15)
      -- line: [289, 289] id: 15
      return "data/game/upgrade/" .. r0_15 .. ".png"
    end
    local r0_14 = display.newGroup()
    local r3_14 = r19_0
    display.newImage(r0_14, (function(r0_16)
      -- line: [290, 290] id: 16
      return r1_14(r0_16 .. _G.UILanguage)
    end)("stoptheclock_"), 0, 0, true)
    r0_14:setReferencePoint(display.CenterReferencePoint)
    r0_0.UpgradeBtn[10] = util.LoadBtnGroup({
      group = r0_14,
      x = 452,
      y = 176,
      func = r3_14,
      show = false,
    })
    _G.UpgradeRoot:insert(r0_14)
  end,
  custom_release_ok = function(r0_17)
    -- line: [306, 309] id: 17
    r0_0.KalaOnlyOne = false
    return false
  end,
  custom_crystal_cost = function(r0_18)
    -- line: [311, 313] id: 18
    return r0_0.kala_crystal_cost
  end,
  custom_crystal_status = function(r0_19, r1_19, r2_19)
    -- line: [315, 321] id: 19
    if r0_0.KalaOnlyOne or r0_0.SummonStatus[r2_19] == 1 then
      r0_0.SummonCrystalStatus[r2_19] = 4
    else
      return r18_0.custom_crystal_status(r0_19, r1_19, r2_19)
    end
  end,
  custom_summon_status = function(r0_20, r1_20, r2_20, r3_20)
    -- line: [323, 329] id: 20
    if r0_0.KalaOnlyOne then
      r0_0.SummonStatus[r2_20] = 4
    else
      return r18_0.custom_summon_status(r0_20, r1_20, r2_20, r3_20)
    end
  end,
}, {
  __index = r18_0,
})
