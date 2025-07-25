-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
return {
  new = function(r0_1)
    -- line: [15, 53] id: 1
    statslog.LogSend("restart", {
      wave = _G.WaveNr,
      hp = _G.HP,
      mp = _G.MP,
    })
    local r2_1 = nil
    function r2_1(r0_2)
      -- line: [24, 44] id: 2
      if director:ChangingScene() then
        return 
      end
      local r1_2 = {
        restart = true,
      }
      if r0_1 and r0_1.magic then
        r1_2.magic = true
        _G.metrics.stage_restart("magic", _G.MapSelect, _G.StageSelect, _G.WaveNr, _G.MP)
      else
        _G.metrics.stage_restart("nomarl", _G.MapSelect, _G.StageSelect, _G.WaveNr, _G.MP)
      end
      Runtime:removeEventListener("enterFrame", r2_1)
      util.ChangeScene({
        scene = "game",
        param = r1_2,
        efx = "fade",
      })
    end
    util.setActivityIndicator(true)
    Runtime:addEventListener("enterFrame", r2_1)
    local r3_1 = display.newGroup()
    display.newRect(r3_1, 0, 0, 960, 640):setFillColor(0, 0, 0)
    return r3_1
  end,
}
