-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("logic.char.CharStatus")
local r1_0 = require("logic.game.GameStatus")
local r2_0 = nil
if _G.GameMode == _G.GameModeEvo then
  r2_0 = require("evo.char_tbl.tbl_c11_tiana").CreateTable()
end
local r3_0 = {
  "afx11_explosion_0_0",
  "afx11_explosion_0_1",
  "afx11_explosion_1_0",
  "afx11_explosion_2_0",
  "c11_tiana_0_0",
  "c11_tiana_0_1",
  "c11_tiana_1_0",
  "c11_tiana_1_1",
  "c11_tiana_1_2",
  "c11_tiana_1_3",
  "c11_tiana_1_4",
  "c11_tiana_1_5",
  "c11_tiana_1_6",
  "c11_tiana_1_7",
  "c11_tiana_1_8",
  "c11_tiana_2_0",
  "c11_tiana_2_1",
  "c11_tiana_2_2"
}
local r4_0 = false
local r5_0 = require("char.c11.c11_tiana")
local r6_0 = require("char.c11.afx11_explosion")
local r7_0 = _G.CharaParam[11][1]
local r8_0 = _G.CharaParam[11][2]
local r9_0 = _G.CharaParam[11][3][1] / 100
local function r10_0(r0_1, r1_1)
  -- line: [52, 81] id: 1
  local r3_1 = r1_1.level
  local r5_1 = _G.UserStatus[r1_1.type].range[r3_1][2] * (char.GetRangePowerup() + 100) / 100
  local r6_1 = r5_1 * r5_1
  local r7_1 = r1_1.x
  local r8_1 = r1_1.y
  local r10_1 = r7_0[r3_1]
  local r11_1 = r8_0[r3_1]
  local r9_1 = r1_1.power[r3_1] * r1_1.buff_power
  local r13_1 = nil
  local r14_1 = nil
  local r15_1 = nil
  for r19_1, r20_1 in pairs(_G.Enemys) do
    r13_1 = r20_1.sx + r20_1.sight[1]
    r14_1 = r20_1.sy + r20_1.sight[2]
    r15_1 = (r13_1 - r7_1) * (r13_1 - r7_1) + (r14_1 - r8_1) * (r14_1 - r8_1)
    if r15_1 < r6_1 then
      if r20_1.attr == 1 or r20_1.attr == 4 then
        r20_1.func.hit(r20_1, r9_1 * r9_0)
      else
        r20_1.func.hit(r20_1, r9_1)
      end
      r20_1.func.burn(r20_1, r10_1, r11_1)
    end
  end
end
local function r11_0(r0_2, r1_2)
  -- line: [84, 86] id: 2
  anime.Remove(r0_2)
end
local function r12_0(r0_3, r1_3, r2_3, r3_3)
  -- line: [89, 95] id: 3
  local r4_3 = r3_3.range_scale
  if r4_3 > 1 then
    r0_3.xScale = r0_3.xScale * r4_3
    r0_3.yScale = r0_3.yScale * r4_3
  end
end
local function r13_0(r0_4, r1_4)
  -- line: [98, 121] id: 4
  sound.PlaySE(17, 12)
  local r3_4 = _G.UserStatus[r1_4.type]
  r1_4.range_scale = r3_4.range[r1_4.level][2] / r3_4.range[1][2]
  local r6_4 = anime.GetPos(r0_4)
  local r9_4 = anime.Register(r6_0.GetData(), r6_4[1], r6_4[2] - 40, "data/game/tiana")
  anime.RegisterTrigger(r9_4, 8, r10_0, r1_4)
  anime.RegisterFinish(r9_4, r11_0, r1_4)
  anime.RegisterShowHook(r9_4, r12_0, r1_4)
  anime.Pause(r9_4, false)
  anime.Show(r9_4, true)
  _G.ExplosionRoot:insert(anime.GetSprite(r9_4))
end
local r16_0 = require("char.Char")
local function r17_0(r0_11, r1_11, r2_11)
  -- line: [172, 196] id: 11
  if game ~= nil and game.IsNotPauseTypeNone() and game.GetPauseType() ~= r1_0.PauseTypeStopClock then
    return true
  end
  local r3_11 = r1_11.recovery_left - r2_11
  if r3_11 < 0 then
    r3_11 = 0
  end
  r1_11.recovery_left = r3_11
  local r4_11 = nil
  if r3_11 <= 0 then
    r1_11.recovery_ev = nil
    r4_11 = false
  else
    r4_11 = true
  end
  local r5_11 = r1_11.recovery_watch
  if r5_11 then
    r5_11(r1_11)
  end
  return r4_11
end
local function r18_0(r0_12)
  -- line: [198, 219] id: 12
  r16_0.not_show_upgrade_btn()
  local r1_12 = r0_12.struct
  r1_12.func.sound(r1_12, 6)
  r1_12.vect = 1
  r1_12.shooting = true
  r1_12.not_select = true
  local r2_12 = r1_12.anime
  anime.Pause(r2_12, false)
  anime.Show(r2_12, true)
  anime.RegisterTrigger(r2_12, r1_12.shot_frame_nr, r1_12.func.shoot, r1_12)
  anime.RegisterFinish(r2_12, function(r0_13, r1_13)
    -- line: [212, 218] id: 13
    anime.SetTimer(r0_13, 0)
    r1_12.recovery_left = r0_0.RECOVERY_TIME
    r1_12.recovery_ev = events.Register(r17_0, r1_12, 1)
    r1_12.not_select = false
    r1_12.shooting = false
  end, r1_12)
end
local function r19_0(r0_14)
  -- line: [221, 233] id: 14
  if r0_14 < 319 then
    r0_0.UpgradeBtn[6].x = 460
    r0_0.UpgradeBtn[6].y = 65
    r0_0.UpgradeBtn[8].x = 460
    r0_0.UpgradeBtn[8].y = 65
  else
    r0_0.UpgradeBtn[6].x = 460
    r0_0.UpgradeBtn[6].y = 410
    r0_0.UpgradeBtn[8].x = 460
    r0_0.UpgradeBtn[8].y = 410
  end
end
local function r20_0(r0_15)
  -- line: [235, 256] id: 15
  if r0_0.Cooldown == nil then
    return 
  end
  local r1_15 = r0_0.Cooldown.rtImg
  local r2_15 = r0_0.Cooldown.rtBar
  local r3_15 = r0_0.Cooldown.spr
  local r4_15 = r0_15.recovery_left
  if r3_15 then
    display.remove(r3_15)
  end
  r3_15 = display.newRect(r2_15, 16, 8, 208 * (r0_0.RECOVERY_TIME - r4_15) / r0_0.RECOVERY_TIME, 8)
  r3_15:setFillColor(255, 127, 0)
  r0_0.Cooldown.spr = r3_15
  r0_0.Cooldown.rtImg.x = r0_0.UpgradeBtn[6].x - 120
  r0_0.Cooldown.rtImg.y = r0_0.UpgradeBtn[6].y + 40
  r1_15.isVisible = true
end
local function r21_0(r0_16)
  -- line: [259, 277] id: 16
  r20_0(r0_16)
  if r0_16.recovery_left <= 0 then
    r0_0.UpgradeBtn[6].isVisible = true
    r0_0.UpgradeBtn[6].struct = r0_16
    r0_0.UpgradeBtn[8].isVisible = false
    if r0_0.Cooldown then
      local r2_16 = nil
      r2_16 = r0_0.Cooldown.rtImg
      if r2_16 then
        r2_16.isVisible = false
      end
      r2_16 = r0_0.Cooldown.spr
      if r2_16 then
        display.remove(r2_16)
      end
      r0_0.Cooldown.spr = nil
    end
    r0_16.recovery_watch = nil
  end
end
local function r22_0(r0_17)
  -- line: [279, 304] id: 17
  local function r1_17(r0_18)
    -- line: [280, 280] id: 18
    return "data/game/upgrade/" .. r0_18 .. ".png"
  end
  local r2_17 = display.newGroup()
  local r3_17 = display.newGroup()
  local r4_17 = display.newGroup()
  local r5_17 = display.newGroup()
  r2_17:insert(r3_17)
  r2_17:insert(r4_17)
  r2_17:insert(r5_17)
  util.LoadParts(r3_17, r1_17("cooldown_base"), 0, 0)
  util.LoadParts(r5_17, r1_17("cooldown_frame"), 0, 0)
  r2_17.isVisible = false
  r0_17:insert(r2_17)
  r2_17:setReferencePoint(display.TopLeftReferencePoint)
  r2_17.x = 360
  r2_17.y = 280
  r0_0.Cooldown = {}
  r0_0.Cooldown.rtImg = r2_17
  r0_0.Cooldown.rtBar = r4_17
  r0_0.Cooldown.spr = nil
end
return setmetatable({
  Load = function(r0_5)
    -- line: [124, 164] id: 5
    if not r4_0 then
      preload.Load(r3_0, "data/game/tiana")
      r4_0 = true
    end
    local r1_5 = {}
    local r2_5 = display.newGroup()
    local r3_5 = r0_5.x
    local r4_5 = r0_5.y
    local r5_5 = anime.Register(r5_0.GetData(), r3_5, r4_5, "data/game/tiana")
    local r6_5 = anime.GetSprite(r5_5)
    local r7_5 = display.newRect(_G.MyTgRoot, r3_5 - 40, r4_5 - 40, 80, 80)
    r7_5.alpha = 0.01
    r7_5.struct = r0_5
    r7_5.touch = r0_5.func.circle
    r7_5:addEventListener("touch", r7_5)
    r0_5.touch_area = r7_5
    r0_5.anime = r5_5
    r0_5.spr = r6_5
    function r0_5.func.load(r0_6, r1_6, r2_6, r3_6)
      -- line: [145, 145] id: 6
      local r4_6 = nil	-- notice: implicit variable refs by block#[0]
      return r4_6
    end
    r0_5.shot_frame_nr = 21
    r0_5.recovery_time = 0
    r0_5.recovery_left = 0
    r0_5.recovery_ev = nil
    r0_5.recovery_watch = nil
    function r0_5.func.range(r0_7, r1_7, r2_7)
      -- line: [152, 152] id: 7
      return true
    end
    function r0_5.func.pointing(r0_8, r1_8)
      -- line: [153, 153] id: 8
      return 1
    end
    r0_5.func.shoot = r13_0
    function r0_5.func.check(r0_9, r1_9)
      -- line: [155, 155] id: 9
      return true
    end
    if _G.GameMode == _G.GameModeEvo then
      r0_5.func.rankTable = r2_0
    end
    return r0_5
  end,
  Cleanup = function()
    -- line: [166, 168] id: 10
    r4_0 = false
  end,
  coustom_skill_touch_func = function(r0_19)
    -- line: [307, 316] id: 19
    local r1_19 = r0_19.struct
    if r1_19.recovery_left <= 0 and r1_19.shooting == false then
      r16_0.hide_upgrade_circle(true, true)
      r18_0(r0_19)
      return true
    end
  end,
  custom_popup_upgrade_btn = function(r0_20)
    -- line: [318, 331] id: 20
    r19_0(r0_20.y)
    if r0_20.recovery_left <= 0 then
      r0_0.UpgradeBtn[6].isVisible = true
      r0_0.UpgradeBtn[6].struct = r0_20
    else
      r0_0.UpgradeBtn[8].isVisible = true
      r0_0.UpgradeBtn[8].struct = r0_20
      r0_20.recovery_watch = r21_0
      r20_0(r0_20)
      r0_0.Cooldown.watch = r0_20
    end
  end,
  custom_make_upgrade_btn = function()
    -- line: [333, 362] id: 21
    local function r1_21(r0_22)
      -- line: [335, 335] id: 22
      return "data/game/upgrade/" .. r0_22 .. ".png"
    end
    local function r2_21(r0_23)
      -- line: [336, 336] id: 23
      return r1_21(r0_23 .. _G.UILanguage)
    end
    local r0_21 = display.newGroup()
    display.newImage(r0_21, r2_21("explosion_"), 0, 0, true)
    r0_21:setReferencePoint(display.CenterReferencePoint)
    r0_0.UpgradeBtn[6] = util.LoadBtnGroup({
      group = r0_21,
      x = 352,
      y = 176,
      func = r18_0,
      show = false,
    })
    _G.UpgradeRoot:insert(r0_21)
    r0_21 = display.newGroup()
    display.newImage(r0_21, r2_21("explosion_disable_"), 0, 0, true)
    r0_21:setReferencePoint(display.CenterReferencePoint)
    r0_21.x = 352 + r0_21.width * 0.5
    r0_21.y = 176 + r0_21.height * 0.5
    r0_21.isVisible = false
    function r0_21.touch(r0_24, r1_24)
      -- line: [356, 356] id: 24
      return true
    end
    r0_0.UpgradeBtn[8] = r0_21
    _G.UpgradeRoot:insert(r0_21)
    r0_0.Cooldown = nil
    r22_0(_G.UpgradeRoot)
  end,
}, {
  __index = r16_0,
})
