-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
return {
  startFirstGame = function(r0_1)
    -- line: [11, 83] id: 1
    local r1_1 = _G.UserID
    local r2_1 = nil
    if r0_1 and r0_1.prevFunc then
      r2_1 = r0_1.prevFunc
    end
    hint.Init()
    save.DataClear()
    db.DeleteItemList(r1_1)
    db.InitMapInfo(r1_1)
    db.UnlockMap(r1_1, 1)
    db.InitStageInfo(r1_1, 1)
    db.UnlockStage(r1_1, 1, 1)
    _G.MapSelect = 1
    _G.StageSelect = 1
    if r0_1.invite == nil then
      _G.metrics.start_with_init(r1_1)
    else
      _G.metrics.start_with_invitation(r1_1)
    end
    server.GetStatus(function(r0_2)
      -- line: [41, 80] id: 2
      local r2_2 = _G.ServerStatus.run2firsthint
      local r3_2 = _G.ServerStatus.run2firsthelp
      local r4_2 = true
      local r5_2 = nil
      local r6_2 = nil
      local r7_2 = nil
      if _G.ServerStatus.run2firstcutin == 1 then
        r5_2 = "cutin"
      elseif r2_2 == 1 then
        r5_2 = "hint"
        r6_2 = {}
        r6_2.no = 1
        r6_2.change_no = 1
        r6_2.wno = _G.MapSelect
        r6_2.sno = _G.StageSelect
        r4_2 = false
      else
        r5_2 = "game"
      end
      local r8_2 = {
        scene = r5_2,
        efx = "fade",
        param = r6_2,
      }
      if r2_1 then
        r8_2.prev = r2_1
      end
      util.setActivityIndicator(r4_2)
      util.ChangeScene(r8_2)
    end, nil)
  end,
}
