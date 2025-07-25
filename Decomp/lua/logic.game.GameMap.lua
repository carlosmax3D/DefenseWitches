-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r6_0 = nil	-- notice: implicit variable refs by block#[0]
local r0_0 = require("logic.game.GameStatus")
local r1_0 = require("logic.char.CharStatus")
local r2_0 = require("logic.game.BaseGame")
local r3_0 = require("logic.game.GameRewind")
local r4_0 = nil
local function r5_0(r0_1, r1_1, r2_1)
  -- line: [8, 20] id: 1
  if r1_1.htptspr then
    if r1_1.htptms > 2000 then
      display.remove(r1_1.htptspr)
      r1_1.htptspr = nil
      r1_1.move = nil
      return false
    else
      r1_1.htptms = r1_1.htptms + r2_1
    end
  end
  return true
end
function r6_0(r0_2)
  -- line: [24, 42] id: 2
  r4_0 = transition.to(r0_2, {
    time = 2000,
    delay = 1000,
    alpha = 0.01,
    transition = easing.linear,
    onComplete = function(r0_3)
      -- line: [31, 39] id: 3
      r4_0 = transition.to(r0_3, {
        time = 500,
        delay = 1000,
        alpha = 1,
        transition = easing.linear,
        onComplete = r6_0,
      })
    end,
  })
  return true
end
local function r7_0()
  -- line: [44, 51] id: 4
  local r0_4 = display.newGroup()
  local r1_4 = anime.Register(r0_0.AuraFx.GetData(), 40, 40, "data/game/aura")
  r0_4:insert(anime.GetSprite(r1_4))
  anime.Loop(r1_4, true)
  return r0_4, r1_4
end
local function r8_0(r0_5)
  -- line: [54, 59] id: 5
  anime.Show(r0_5.burst_anm, true)
  r2_0.CheckTotalAchievement(20, db.CountAchievement(_G.UserID, 20))
end
local function r9_0(r0_6, r1_6, r2_6)
  -- line: [62, 67] id: 6
  if not r0_6.move then
    r0_6.move = events.Register(r5_0, r0_6, 1)
  end
  enemy.BurnObject(r0_6, r1_6, r2_6)
end
local function r10_0(r0_7, r1_7)
  -- line: [70, 90] id: 7
  if r1_7.phase == "ended" then
    if not r0_7.struct.move then
      r0_7.struct.move = events.Register(r5_0, r0_7.struct, 1, false)
    end
    if r1_0.IsSnipeMode then
      char.SetSnipeTarget(r0_7.struct)
    else
      char.SetNextTarget(r0_7.struct)
    end
    char.ClearAllCircle()
    char.SnipeModeOff()
    return true
  else
    return false
  end
end
local function r11_0()
  -- line: [93, 117] id: 8
  local r0_8 = {}
  local r1_8 = _G.EnemyStart[1] * 80 + 40
  local r2_8 = _G.EnemyStart[2] * 80 + 40
  local r3_8 = nil
  local r4_8 = nil
  local r5_8 = nil
  local r6_8 = nil
  for r10_8, r11_8 in pairs(_G.EnemyRoute) do
    if r11_8[1] == -1 and r11_8[2] == -1 then
      break
    else
      r3_8 = r1_8 + r11_8[1]
      r4_8 = r2_8 + r11_8[2]
      while r1_8 ~= r3_8 or r2_8 ~= r4_8 do
        local r12_8 = table.insert
        r12_8(r0_8, {
          r1_8,
          r2_8,
          r3_8,
          r4_8,
          r10_8
        })
        r5_8 = r3_8 - r1_8
        r6_8 = r4_8 - r2_8
        if r5_8 ~= 0 then
          if r5_8 > 0 then
            r12_8 = 1
            r5_8 = r12_8 or -1
          else
            goto label_52	-- block#10 is visited secondly
          end
        end
        if r6_8 ~= 0 then
          if r6_8 > 0 then
            r12_8 = 1
            r6_8 = r12_8 or -1
          else
            goto label_60	-- block#14 is visited secondly
          end
        end
        r1_8 = r1_8 + r5_8
        r2_8 = r2_8 + r6_8
      end
    end
  end
  return r0_8
end
local function r12_0(r0_9)
  -- line: [119, 178] id: 9
  local r1_9 = r0_9[1] * 80 + 40
  local r2_9 = r0_9[2] * 80 + 40
  r0_0.Goal.x = r1_9 - 60
  r0_0.Goal.y = r2_9 + 15
  r0_0.GoalSprite = {}
  local r3_9 = nil
  local r4_9 = nil
  local r5_9 = nil
  local r6_9 = nil
  r6_9 = true
  for r10_9 = 1, 4, 1 do
    r3_9 = string.format("anm.stone_seal%02d", r10_9)
    r4_9 = anime.Register(require(r3_9).GetData(), r1_9, r2_9, "data/game")
    _G.MyRoot:insert(anime.GetSprite(r4_9))
    table.insert(r0_0.GoalSprite, r4_9)
    anime.Loop(r4_9, true)
    if r6_9 then
      anime.Pause(r4_9, false)
      anime.Show(r4_9, true)
      r6_9 = false
    else
      anime.Pause(r4_9, true)
      anime.Show(r4_9, false)
    end
    package.loaded[r3_9] = nil
  end
  r0_0.GoalSpriteZ = r1_9 + (r2_9 - 41) * 1000
  r0_0.GoalSpriteIndex = 1
  r0_0.rewind_anime = anime.Register(r0_0.rewindAnm.GetData(), r1_9 - 40, r2_9 - 50, "data/game/rewind/" .. _G.UILanguage)
  local r7_9 = anime.GetSprite(r0_0.rewind_anime)
  _G.FrontRoot:insert(r7_9)
  r0_0.rectSpr = display.newRect(_G.FrontRoot, r1_9 - 40, r2_9 - 37, 80, 80)
  r0_0.rectSpr:setFillColor(140, 140, 140)
  r0_0.rectSpr.touch = r3_0.rewind_func
  r0_0.rectSpr:addEventListener("touch", r0_0.rectSpr)
  r0_0.rectSpr.alpha = 0.01
  _G.FrontRoot:insert(r0_0.rectSpr)
  r4_0 = transition.to(r7_9, {
    transition = easing.outQuad,
    onComplete = r6_0,
  })
  if r0_0.PerfectFlag then
    anime.Show(r0_0.rewind_anime, false)
    r0_0.rectSpr.isVisible = false
  else
    anime.Show(r0_0.rewind_anime, true)
    r0_0.rectSpr.isVisible = true
  end
  anime.Loop(r0_0.rewind_anime, true)
end
return {
  loadmap = function(r0_10, r1_10)
    -- line: [180, 491] id: 10
    local function r2_10(r0_11)
      -- line: [181, 183] id: 11
      return string.format("data/map/%02d/%s.png", _G.MapSelect, r0_11)
    end
    local function r3_10(r0_12)
      -- line: [185, 187] id: 12
      return string.format("data/map/%02d/%s.jpg", _G.MapSelect, r0_12)
    end
    local r4_10 = display.newGroup()
    if true then
      util.LoadTileBG(r4_10, db.LoadTileData(string.format("world%02d", _G.MapSelect), string.format("bg%03d", _G.StageSelect - 1)), string.format("data/map/%02d", _G.MapSelect))
    end
    local r6_10 = db.GetMapData(_G.MapSelect, _G.StageSelect)
    local r7_10 = {}
    for r11_10, r12_10 in pairs(r6_10.info) do
      if r12_10[1] > 0 then
        r7_10[r11_10] = {
          hp = r12_10[2],
          mp = r12_10[3],
          score = r12_10[4],
        }
      end
    end
    _G.CrashObject = {}
    local r8_10 = nil
    local r9_10 = nil
    local r10_10 = nil
    local r11_10 = nil
    local r12_10 = nil
    local r13_10 = {}
    local r14_10 = nil
    for r18_10, r19_10 in pairs(r6_10.object) do
      r14_10 = 65536 + r18_10
      r8_10 = r19_10[1]
      r9_10 = r19_10[2]
      r10_10 = r19_10[3]
      r12_10 = r2_10(string.format("obj%03d", r8_10 - 1))
      if r7_10[r8_10] then
        r11_10 = cdn.NewImageNG(r12_10, 0, 0, true)
        r11_10.id = r18_10
        local r20_10 = r11_10.width / 2
        local r21_10 = r11_10.height / 2
        local r22_10 = r9_10 + r20_10
        local r23_10 = r10_10 + r21_10
        local r24_10 = {
          uid = r14_10,
          type = -1,
          sx = r22_10,
          sy = r23_10,
          ex = r22_10,
          ey = r23_10,
          sight = {
            0,
            0
          },
          ptdiff = {
            0,
            0
          },
          move = nil,
          living = true,
          func = {
            hit = enemy.HitObject,
            stan = enemy.StanObject,
            burn = r9_0,
            freeze = enemy.FreezeObject,
            damage = nil,
            burst = r8_0,
            move = nil,
            burst_finish = enemy.GetBurstFuncFinish(),
            destructor = nil,
          },
          hit_flag = false,
          hit_anm = nil,
          burst_anm = nil,
          maxhtpt = r7_10[r8_10].hp,
          hitpoint = r7_10[r8_10].hp,
          htptms = 0,
          htptspr = nil,
          attack = {
            true,
            false
          },
          addmp = r7_10[r8_10].mp,
          target_spr = nil,
          htptpos = {
            16,
            -16
          },
          targetpos = {
            0,
            -80
          },
          aura = nil,
          crash_obj = r11_10,
          hard = true,
          score = r7_10[r8_10].score,
          root = _G.MyRoot,
          slip_root = nil,
          hit_root = nil,
          hp_root = nil,
          aura_root = nil,
          stan_flag = false,
          stan_time = 0,
          stan_efx = nil,
          stan_se = nil,
        }
        r24_10.burst_anm = nil
        r24_10.burn_efx = nil
        r24_10.burn_ev = nil
        r24_10.burn_damage = 0
        r24_10.burn_time = 0
        r24_10.burn_cnt = 0
        r24_10.burn_se = nil
        r24_10.freeze_efx = nil
        r24_10.freeze_time = 0
        r24_10.freeze_se = nil
        r24_10.move_vect = 1
        r24_10.map_x = math.floor(r9_10 / 80) + 1
        r24_10.map_y = math.floor(r10_10 / 80) + 1
        r24_10.stone_damage = 0
        r24_10.shadow = nil
        r24_10.flying = false
        r24_10.dive_anm = nil
        r24_10.sort_sprite = nil
        r24_10.sort_z = nil
        r24_10.not_select = false
        r24_10.lyra = {
          {
            0,
            0,
            0,
            0,
            0
          },
          {
            0,
            0,
            0,
            0,
            0
          }
        }
        r11_10.struct = r24_10
        r24_10 = r11_10.struct
        if r0_10 then
          local r25_10 = r0_10[r14_10]
          local r26_10 = false
          if r25_10 then
            local r27_10 = r25_10.hp
            if r27_10 > 0 then
              r24_10.hitpoint = r27_10
            else
              r26_10 = true
            end
          else
            r26_10 = true
          end
          if r26_10 then
            if r24_10.aura then
              anime.Remove(r24_10.aura)
            end
            display.remove(r11_10)
            r11_10 = nil
          end
        end
        if r11_10 then
          r11_10.touch = r10_0
          r11_10:addEventListener("touch", r11_10)
          table.insert(_G.CrashObject, r24_10)
          local r25_10 = nil
          local r26_10 = nil
          local r27_10 = nil
          r26_10 = display.newGroup()
          r11_10:setReferencePoint(display.CenterReferencePoint)
          r11_10.x = r20_10
          r11_10.y = r21_10
          r26_10:insert(r11_10)
          r24_10.sort_sprite = r26_10
          r25_10, r27_10 = r7_0()
          r26_10:insert(r25_10)
          r24_10.aura = r27_10
          r24_10.aura_root = r25_10
          r25_10 = display.newGroup()
          r26_10:insert(r25_10)
          r24_10.slip_root = r25_10
          r25_10 = display.newGroup()
          r26_10:insert(r25_10)
          r24_10.hit_root = r25_10
          r25_10 = display.newGroup()
          r26_10:insert(r25_10)
          r24_10.hp_root = r25_10
          r26_10:setReferencePoint(display.CenterReferencePoint)
          r26_10.x = r22_10
          r26_10.y = r23_10
          r24_10.root:insert(r26_10)
          r24_10.sort_z = r22_10 + r23_10 * 1000
          anime.Show(r24_10.aura, true)
        end
      else
        r11_10 = cdn.NewImage(_G.RouteRoot, r12_10, nil, nil, true)
        r11_10:setReferencePoint(display.TopLeftReferencePoint)
        r11_10.x = r9_10
        r11_10.y = r10_10
        table.insert(r13_10, {
          r9_10,
          r10_10
        })
      end
    end
    for r18_10, r19_10 in pairs(r6_10.front) do
      r9_10 = r19_10[2]
      r10_10 = r19_10[3]
      r12_10 = r2_10(string.format("fnt%03d", r19_10[1]))
      r11_10 = cdn.NewImage(_G.RouteRoot, r12_10, nil, nil, true)
      r11_10:setReferencePoint(display.TopLeftReferencePoint)
      r11_10.x = r9_10
      r11_10.y = r10_10
    end
    local r15_10 = nil
    local r16_10 = nil
    if r6_10.goal[1] == -1 then
      r12_0(r6_10.sgoal)
      r15_10 = r6_10.sgoal[1] + 1
      r16_10 = r6_10.sgoal[2] + 1
    else
      r12_0(r6_10.goal)
      r15_10 = r6_10.goal[1] + 1
      r16_10 = r6_10.goal[2] + 1
    end
    _G.MapLocation = {}
    for r20_10 = 1, r0_0.MapHeight, 1 do
      local r21_10 = {}
      for r25_10 = 1, r0_0.MapWidth, 1 do
        table.insert(r21_10, 0)
      end
      table.insert(_G.MapLocation, r21_10)
    end
    local r17_10 = {}
    if r1_10 then
      for r21_10, r22_10 in pairs(r6_10.map) do
        local r23_10 = r22_10[1]
        local r24_10 = r22_10[2]
        local r25_10 = r22_10[3]
        if r25_10 < 1 then
          _G.MapLocation[r24_10][r23_10] = r25_10
        end
      end
      for r21_10, r22_10 in pairs(r1_10) do
        local r23_10 = r22_10.xy
        local r24_10 = r23_10[1]
        local r25_10 = r23_10[2]
        local r26_10 = r22_10.id
        local r27_10 = r22_10.level
        _G.MapLocation[r25_10][r24_10] = r26_10
        if r26_10 == 100 then
          table.insert(r17_10, {
            r26_10,
            r24_10,
            r25_10,
            r27_10,
            r22_10.hp,
            r22_10.maxhp
          })
        elseif r26_10 == 15 then
          table.insert(r17_10, {
            r26_10,
            r24_10,
            r25_10,
            r27_10,
            r22_10.hp,
            r22_10.ms
          })
        elseif r26_10 == 13 then
          table.insert(r17_10, {
            r26_10,
            r24_10,
            r25_10,
            r27_10,
            r22_10.params
          })
        else
          table.insert(r17_10, {
            r26_10,
            r24_10,
            r25_10,
            r27_10
          })
        end
      end
    else
      for r21_10, r22_10 in pairs(r6_10.map) do
        local r23_10 = r22_10[1]
        local r24_10 = r22_10[2]
        local r25_10 = r22_10[3]
        _G.MapLocation[r24_10][r23_10] = r25_10
        if r25_10 >= 1 then
          table.insert(r17_10, {
            r25_10,
            r23_10,
            r24_10,
            nil
          })
        end
      end
    end
    _G.EnemyStart = {
      unpack(r6_10.start)
    }
    _G.SkyStart = {
      unpack(r6_10.sstart)
    }
    _G.EnemyRoute = {
      unpack(r6_10.moving[1])
    }
    _G.SkyRoute = {
      unpack(r6_10.moving[2])
    }
    _G.RoadRoute = r11_0()
    for r21_10, r22_10 in pairs(r17_10) do
      char.FirstSummon(r22_10)
    end
    for r21_10, r22_10 in pairs(r13_10) do
      _G.MapLocation[math.floor(r22_10[2] / 80) + 1][math.floor(r22_10[1] / 80) + 1] = 256
    end
    _G.MapLocation[r16_10][r15_10] = 257
    for r21_10, r22_10 in pairs(_G.CrashObject) do
      _G.MapLocation[r22_10.map_y][r22_10.map_x] = r22_10.uid
    end
    r2_0.MakeGrid(true)
    return r4_10
  end,
}
