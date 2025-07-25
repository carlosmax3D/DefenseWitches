-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
return {
  new = function(r0_1)
    -- line: [6, 46] id: 1
    local r6_1 = nil	-- notice: implicit variable refs by block#[0]
    local r1_1 = r0_1.map
    local r2_1 = r0_1.stage
    local r3_1 = r0_1.wave
    local r4_1 = db.LoadResume(_G.UserID, r1_1, r2_1, r3_1)
    local r5_1 = r0_1.save
    function r6_1(r0_2)
      -- line: [12, 36] id: 2
      if director:ChangingScene() then
        return 
      end
      Runtime:removeEventListener("enterFrame", r6_1)
      if r4_1 == false then
        util.setActivityIndicator(false)
        db.CleanupResume()
        require("tool.save").DataClear()
        native.showAlert("DefenseWitches", db.GetMessage(56), {
          "OK"
        })
        timer.performWithDelay(2000, function(r0_3)
          -- line: [24, 26] id: 3
          util.ChangeScene({
            scene = "title",
            efx = "fade",
          })
        end)
      else
        util.ChangeScene({
          scene = "game",
          param = {
            map = r1_1,
            stage = r2_1,
            wave = r3_1,
            data = r4_1,
            save = r5_1,
          },
          efx = "fade",
        })
      end
    end
    util.setActivityIndicator(true)
    Runtime:addEventListener("enterFrame", r6_1)
    local r7_1 = display.newGroup()
    display.newRect(r7_1, 0, 0, 960, 640):setFillColor(0, 0, 0)
    return r7_1
  end,
}
