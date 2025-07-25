-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("logic.char.CharStatus")
local r1_0 = require("logic.game.GameStatus")
local r2_0 = require("char.c22.yuikoShootingStar")
local r3_0 = nil
if _G.GameMode == _G.GameModeEvo then
  r3_0 = require("evo.char_tbl.tbl_c22_yuiko").CreateTable()
end
local r4_0 = {
  "c22_yuiko01_0_0",
  "c22_yuiko01_0_1",
  "c22_yuiko01_0_2",
  "c22_yuiko01_0_3",
  "c22_yuiko01_0_4",
  "c22_yuiko01_0_5",
  "c22_yuiko01_0_6",
  "c22_yuiko01_0_7",
  "c22_yuiko01_0_8",
  "c22_yuiko01_0_9",
  "c22_yuiko01_0_10",
  "c22_yuiko01_0_15",
  "c22_yuiko01_0_18",
  "c22_yuiko02_0_0",
  "c22_yuiko02_0_1",
  "c22_yuiko02_0_2",
  "c22_yuiko02_0_3",
  "c22_yuiko02_0_4",
  "c22_yuiko02_0_5",
  "c22_yuiko02_0_6",
  "c22_yuiko02_0_7",
  "c22_yuiko02_0_8",
  "c22_yuiko02_0_9",
  "c22_yuiko02_0_10",
  "c22_yuiko02_0_15",
  "c22_yuiko02_0_16",
  "c22_yuiko02_0_19",
  "c22_yuiko02_0_20",
  "c22_yuiko02_0_21",
  "c22_yuiko02_0_22",
  "c22_yuiko03_0_0",
  "c22_yuiko03_0_1",
  "c22_yuiko03_0_2",
  "c22_yuiko03_0_3",
  "c22_yuiko03_0_4",
  "c22_yuiko03_0_5",
  "c22_yuiko03_0_6",
  "c22_yuiko03_0_7",
  "c22_yuiko03_0_8",
  "c22_yuiko03_0_9",
  "c22_yuiko03_0_10",
  "c22_yuiko03_0_15",
  "c22_yuiko03_0_16",
  "c22_yuiko03_0_19",
  "c22_yuiko03_0_20",
  "c22_yuiko03_0_21",
  "c22_yuiko03_0_22"
}
local r5_0 = false
local r6_0 = nil
local r7_0 = nil
local r8_0 = 1000
local r9_0 = require("char.c22.c22_yuiko01")
local r10_0 = require("char.c22.c22_yuiko02")
local r11_0 = require("char.c22.c22_yuiko03")
local r12_0 = "data/game/yuiko"
local r13_0 = _G.CharaParam[22][1]
local function r14_0(r0_1, r1_1, r2_1)
  -- line: [87, 106] id: 1
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
local function r15_0(r0_2, r1_2)
  -- line: [109, 118] id: 2
  local r2_2 = r1_2.level
  anime.Pause(r0_2, true)
  anime.SetTimer(r0_2, 0)
  r1_2.rigidity_ms = 0
  r1_2.rigidity_es = r13_0[r2_2]
  r1_2.rigidity_ev = events.Register(r14_0, r1_2, 1)
end
local function r16_0(r0_3, r1_3)
  -- line: [121, 123] id: 3
  return 1
end
local function r17_0(r0_4, r1_4, r2_4)
  -- line: [126, 181] id: 4
  local r3_4 = display.newGroup()
  local r4_4 = {}
  local r5_4 = nil
  local r6_4 = nil
  local r7_4 = nil
  local r8_4 = nil
  local r9_4 = nil
  local r10_4 = nil
  local r11_4 = nil
  for r15_4, r16_4 in pairs(r0_4) do
    r6_4 = {}
    r9_4 = r16_4.name
    r7_4 = r12_0 .. "/" .. r16_4.name
    r8_4 = display.newImage(r7_4)
    r8_4:setReferencePoint(display.CenterReferencePoint)
    r10_4 = r16_4.pos
    r8_4.x = r1_4
    r8_4.y = r2_4
    r8_4.isVisible = false
    r3_4:insert(r8_4)
    r6_4.spr = r8_4
    r6_4.start = r16_4.start
    r6_4.stop = r16_4.stop
    r6_4.scale = r16_4.scale
    r6_4.alpha = r16_4.alpha
    r6_4.angle = r16_4.angle
    r6_4.pos = r10_4
    table.insert(r4_4, r6_4)
    if r5_4 == nil or r5_4 < r16_4.stop then
      r5_4 = r16_4.stop
    end
  end
  local r12_4 = {
    sprite = r3_4,
    timer = 0,
    stop = r5_4,
    show = false,
    layers = r4_4,
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
  anime.RegisterCallback(r12_4)
  return r12_4
end
local function r18_0(r0_5)
  -- line: [183, 188] id: 5
  if r0_5.rigidity_ev then
    events.Delete(r0_5.rigidity_ev)
    r0_5.rigidity_ev = nil
  end
end
local function r19_0(r0_6)
  -- line: [191, 199] id: 6
  local r1_6 = {
    timer = anime.GetTimer(r0_6.anime),
    ms = r0_6.rigidity_ms,
    es = r0_6.rigidity_es,
    flag = r0_6.rigidity_ev ~= nil,
  }
  return r1_6
end
local function r20_0(r0_7, r1_7)
  -- line: [202, 227] id: 7
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
    assert(r4_7 == r13_0[r7_7], debug.traceback())
    r0_7.rigidity_es = r4_7
    r0_7.rigidity_ev = events.Register(r14_0, r0_7, 1)
  else
    anime.SetTimer(r6_7, r2_7)
    r0_7.resume_timer = r2_7
  end
end
local function r21_0(r0_8, r1_8, r2_8, r3_8)
  -- line: [230, 233] id: 8
  r3_8.sort_z = r8_0
end
local r22_0 = nil
local function r23_0(r0_9, r1_9)
  -- line: [237, 259] id: 9
  display.remove(r6_0)
  display.remove(r7_0)
  r22_0(r0_9, r1_9)
  local r2_9 = r0_9.x
  local r3_9 = r0_9.y
  anime.SetTimer(r0_9.anime, 0)
  anime.Pause(r0_9.anime, true)
  anime.Show(r0_9.anime, true)
  r0_9.rigidity_ev = nil
  r0_9.shooting = false
  local r4_9 = display.newGroup()
  r4_9:insert(r0_9.spr)
  r0_9.sort_sprite = r4_9
  r0_9.sort_z = r8_0
  _G.MyRoot:insert(r0_9.sort_sprite)
  return r0_9
end
function r22_0(r0_10, r1_10)
  -- line: [262, 329] id: 10
  if not r5_0 then
    preload.Load(r4_0, r12_0)
    r5_0 = true
  end
  local r2_10 = r0_10.x
  local r3_10 = r0_10.y
  local r4_10 = nil
  if r1_10 == nil then
    r1_10 = 0
  end
  if r1_10 == 0 then
    r4_10 = r17_0(r9_0.GetData(), r2_10, r3_10)
  elseif r1_10 == 1 then
    r4_10 = r17_0(r10_0.GetData(), r2_10, r3_10)
  else
    r4_10 = r17_0(r11_0.GetData(), r2_10, r3_10)
  end
  r6_0 = anime.GetSprite(r4_10)
  anime.RegisterShowHook(r4_10, r21_0, r0_10)
  r7_0 = display.newRect(_G.MyTgRoot, r2_10 - 40, r3_10 - 40, 80, 80)
  r7_0.alpha = 0.01
  r7_0.struct = r0_10
  r7_0.touch = r0_10.func.circle
  r7_0:addEventListener("touch", r7_0)
  r0_10.touch_area = r7_0
  r0_10.anime = r4_10
  r0_10.spr = r6_0
  function r0_10.func.range(r0_11, r1_11, r2_11)
    -- line: [291, 310] id: 11
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
    anime.RegisterFinish(r3_11, r15_0, r1_11)
    return true
  end
  r0_10.func.pointing = r16_0
  r0_10.func.release = r18_0
  r0_10.func.get_resume_data = r19_0
  r0_10.func.set_first = r20_0
  r0_10.rigidity_ms = 0
  r0_10.rigidity_es = 0
  r0_10.rigidity_ev = nil
  r0_10.resume_timer = nil
  if _G.GameMode == _G.GameModeEvo then
    r0_10.func.changeSprite = r23_0
    r0_10.func.rankTable = r3_0
  end
  return r0_10
end
local r25_0 = require("char.Char")
local function r26_0(r0_13)
  -- line: [343, 388] id: 13
  r1_0.yuikoEvoLevel = r0_13
  if r1_0.yuikoEvoLevel > 0 then
    r2_0.setStarAnime(r1_0.Goal.x + 58, r1_0.Goal.y + 30, 4000, function()
      -- line: [350, 376] id: 14
      if r1_0.yuikoEvoLevel > 0 then
        if r1_0.stoneHealEffect ~= nil then
          anime.Pause(r1_0.stoneHealEffect, true)
          anime.Show(r1_0.stoneHealEffect, false)
          anime.Remove(r1_0.stoneHealEffect)
          r1_0.stoneHealEffect = nil
        end
        local r0_14 = r1_0.stoneHealAnime.GetData()
        local r3_14 = anime.Register(r1_0.stoneHealAnime.GetData(), r1_0.Goal.x + 58, r1_0.Goal.y + 30, "data/game")
        anime.Show(r3_14, true)
        anime.Loop(r3_14, true)
        r1_0.stoneHealEffect = r3_14
        _G.FrontRoot:insert(anime.GetSprite(r3_14))
      end
      local r0_14 = _G.DefaultHp + r1_0.PurchaseHP
      if _G.HpMaxLimit < r0_14 then
        r0_14 = _G.HpMaxLimit
      end
      game.SetHp(r0_14)
      game.ViewPanel()
    end)
  elseif r1_0.stoneHealEffect ~= nil then
    anime.Pause(r1_0.stoneHealEffect, true)
    anime.Show(r1_0.stoneHealEffect, false)
    anime.Remove(r1_0.stoneHealEffect)
    r1_0.stoneHealEffect = nil
  end
end
return setmetatable({
  ChangeSprite = r23_0,
  Load = r22_0,
  Cleanup = function()
    -- line: [331, 333] id: 12
    r5_0 = false
  end,
  custom_release_ok = function(r0_17)
    -- line: [407, 417] id: 17
    if r1_0.yuikoEvoLevel > 0 then
      r26_0(0)
    end
    r0_0.YuikoOnlyOne = false
    r0_0.YuikoStruct = nil
    return false
  end,
  custom_crystal_status = function(r0_18, r1_18, r2_18)
    -- line: [419, 425] id: 18
    if r0_0.YuikoOnlyOne then
      r0_0.SummonCrystalStatus[r2_18] = 4
    else
      return r25_0.custom_crystal_status(r0_18, r1_18, r2_18)
    end
  end,
  custom_summon_status = function(r0_19, r1_19, r2_19, r3_19)
    -- line: [427, 433] id: 19
    if r0_0.YuikoOnlyOne then
      r0_0.SummonStatus[r2_19] = 4
    else
      return r25_0.custom_summon_status(r0_19, r1_19, r2_19, r3_19)
    end
  end,
  setYuikoEvoLevel = r26_0,
  setStarAnime = function()
    -- line: [390, 403] id: 15
    r2_0.setStarAnime(r1_0.Goal.x + 58, r1_0.Goal.y + 30, 1000, function()
      -- line: [393, 400] id: 16
      local r0_16 = _G.DefaultHp + r1_0.PurchaseHP
      if _G.HpMaxLimit < r0_16 then
        r0_16 = _G.HpMaxLimit
      end
      game.SetHp(r0_16)
      game.ViewPanel()
    end)
  end,
}, {
  __index = r25_0,
})
