-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
return {
  new = function()
    -- line: [9, 95] id: 1
    local r0_1 = {
      cardId = 1,
    }
    r0_1.prizes_data = {
      all_clear = {
        prizes_type = 1,
        image = "game/plate/status_cha20",
        char_id = 20,
        spss_item_master_id = 147,
      },
    }
    r0_1.mission_data = {
      {
        mission_type = 101,
        spss_item_master_id = 138,
      },
      {
        mission_type = 102,
        spss_item_master_id = 139,
      },
      {
        mission_type = 1,
        enemy_id = 12,
        value = 3,
        spss_item_master_id = 140,
      },
      {
        mission_type = 105,
        spss_item_master_id = 141,
      },
      {
        mission_type = 103,
        spss_item_master_id = 142,
      },
      {
        mission_type = 2,
        value = 1,
        spss_item_master_id = 143,
      },
      {
        mission_type = 3,
        char_id = 5,
        spss_item_master_id = 144,
      },
      {
        mission_type = 104,
        spss_item_master_id = 145,
      },
      {
        mission_type = 4,
        char_id = 1,
        value = 50,
        spss_item_master_id = 146,
      }
    }
    return r0_1
  end,
}
