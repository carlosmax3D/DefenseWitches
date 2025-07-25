-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = {
  require("enemy.anm.akuma_walk"),
  require("enemy.anm.akuma_ouch")
}
local r1_0 = 2000
local r2_0 = nil
local r3_0 = 1
local function r4_0(r0_1)
  -- line: [12, 22] id: 1
  if not _G.GameData.voice then
    return 
  end
  local r2_1 = sound.GetCharVoiceFilename(sound.GetCharVoicePath(90, _G.GameData.language), r0_1)
  if r2_0 then
    sound.StopVoice(r2_0, 31)
  end
  r2_0 = sound.PlayVoice(r2_1, 31)
end
local function r5_0(r0_2, r1_2, r2_2)
  -- line: [24, 62] id: 2
  local r3_2 = r1_2.ms + r2_2
  if r1_0 <= r3_2 then
    r3_2 = r1_0
    if game ~= nil and game.IsNotPauseTypeNone() then
      return true
    end
  end
  r1_2.ms = r3_2
  local r4_2 = r3_2 / r1_0
  anime.Move(r1_2.anm, r1_2.sx + r1_2.lx * r4_2, r1_2.sy + r1_2.ly * r4_2 - 120 * math.sin(math.pi * r4_2), {
    angle = 270 * r4_2,
    scale = 1 + r4_2 / 2,
  })
  if r1_0 <= r3_2 then
    r1_2.func(r1_2.struct)
    r1_2.ev = nil
    r1_2 = nil
    if not db.GetGameCenterAchievement(_G.UserID, 16) then
      game.CheckTotalAchievement(16, db.CountAchievement(_G.UserID, 16) * 2)
    end
    game.SetCornetFlag(2)
    return false
  end
  return true
end
local function r6_0(r0_3, r1_3)
  -- line: [64, 102] id: 3
  local r2_3 = r1_3.sx
  local r3_3 = r1_3.sy
  local r4_3 = r1_3.burst_anm
  anime.RegisterShowHook(r4_3, function(r0_4, r1_4, r2_4, r3_4)
    -- line: [69, 72] id: 4
    r0_4.xScale = r0_4.xScale * 2
    r0_4.yScale = r0_4.yScale * 2
  end, nil)
  anime.Show(r4_3, true)
  r4_0(r3_0 + 6)
  r3_0 = r3_0 + 1
  if r3_0 > 3 then
    r3_0 = 3
  end
  local r5_3 = nil
  local r6_3 = nil
  if r2_3 < _G.Width / 2 then
    r5_3 = _G.Width + 112 + math.random(50, 150)
  else
    r5_3 = -112 - math.random(50, 150)
  end
  r6_3 = r3_3
  local r9_3 = {
    sx = r2_3,
    sy = r3_3,
    ex = r5_3,
    ey = r6_3,
    lx = r5_3 - r2_3,
    ly = r6_3 - r3_3,
    anm = r1_3.damage_anm,
    spr = anime.GetSprite(r1_3.damage_anm),
    func = r1_3.func.destructor,
    struct = r1_3,
    ms = 0,
    ev = nil,
  }
  r1_3.sort_sprite.x = 0
  r1_3.sort_sprite.y = 0
  r1_3.sort_z = 640960
  anime.Move(r9_3.anm, r2_3, r3_3)
  r9_3.ev = events.Register(r5_0, r9_3, 1)
  return false
end
local function r7_0(r0_5)
  -- line: [104, 118] id: 5
  local r1_5 = r0_5.sx
  local r2_5 = r0_5.sy
  local r3_5 = r0_5.anm
  anime.Pause(r3_5, true)
  anime.Show(r3_5, false)
  r3_5 = r0_5.damage_anm
  anime.Move(r3_5, 0, 0)
  anime.Pause(r3_5, true)
  anime.Show(r3_5, true)
  events.Register(r6_0, r0_5, 333.3333333333333)
end
local function r8_0(r0_6, r1_6)
  -- line: [121, 133] id: 6
  local r2_6 = r1_6.damage_anm
  anime.Pause(r2_6, true)
  anime.Show(r2_6, false)
  local r3_6 = r1_6.anm
  anime.Pause(r3_6, false)
  anime.Show(r3_6, true)
  r1_6.cantmove = false
end
local function r9_0(r0_7)
  -- line: [136, 160] id: 7
  local r1_7 = r0_7.damage_level
  if r0_7.hitpoint <= r0_7.maxhtpt * (3 - r1_7) / 4 then
    r1_7 = r1_7 + 1
    r0_7.damage_level = r1_7
    r4_0(r1_7 + 3)
    anime.Pause(r0_7.anm, true)
    anime.Show(r0_7.anm, false)
    local r4_7 = r0_7.sx
    local r5_7 = r0_7.sy
    local r6_7 = r0_7.damage_anm
    anime.Move(r6_7, 0, 0)
    anime.Pause(r6_7, false)
    anime.Show(r6_7, true)
    r0_7.cantmove = true
  end
end
local function r10_0(r0_8, r1_8)
  -- line: [162, 167] id: 8
  if r1_8.burst_anm then
    anime.Remove(r1_8.burst_anm)
    r1_8.burst_anm = nil
  end
end
local function r11_0(r0_9)
  -- line: [169, 205] id: 9
  local r1_9 = r0_9.sx
  local r2_9 = r0_9.sy
  local r3_9 = display.newGroup()
  local r4_9 = anime.Register(r0_0[1].GetData(), 0, 0, "data/game/enemy12")
  local r5_9 = anime.GetSprite(r4_9)
  anime.Loop(r4_9, true)
  anime.Show(r4_9, true)
  r3_9:insert(r5_9)
  r0_9.anm = r4_9
  r0_9.sprite = r5_9
  r0_9.attack = {
    true,
    false
  }
  r0_9.func.damage = r9_0
  r0_9.damage_level = 0
  r0_9.func.burst = r7_0
  r0_9.func.burst_finish = r10_0
  r0_9.sort_sprite = r3_9
  r0_9.sort_z = r1_9 + r2_9 * 1000
  r4_9 = anime.Register(r0_0[2].GetData(), 0, 0, "data/game/enemy12")
  anime.RegisterFinish(r4_9, r8_0, r0_9)
  r0_9.damage_anm = r4_9
  r0_9.htptpos[2] = -112
  r0_9.targetpos[2] = -140
  r3_9:insert(anime.GetSprite(r4_9))
  r0_9.stone_damage = 5
  r4_0(r3_0)
  r0_9.no_snipe = true
  game.SetCornetFlag(1)
end
local r12_0 = {
  "akuma_ouch_1_0",
  "akuma_ouch_2_0",
  "akuma_walk_0_0",
  "akuma_walk_1_0",
  "akuma_walk_1_1",
  "akuma_walk_1_2",
  "akuma_walk_2_0",
  "akuma_walk_2_1",
  "akuma_walk_2_2",
  "akuma_walk_2_3",
  "akuma_walk_3_0",
  "akuma_walk_3_1",
  "akuma_walk_3_2",
  "akuma_walk_3_7",
  "akuma_walk_4_0",
  "akuma_walk_4_1",
  "akuma_walk_4_2",
  "akuma_walk_4_3",
  "akuma_walk_5_0",
  "akuma_walk_5_1",
  "akuma_walk_5_2",
  "akuma_walk_5_7"
}
return {
  Pop = r11_0,
  PreLoad = function()
    -- line: [233, 235] id: 10
    preload.Load(r12_0, "data/game/enemy12")
  end,
}
