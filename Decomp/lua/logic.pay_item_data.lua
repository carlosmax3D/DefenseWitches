-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("sqlite3")
local r2_0 = require("resource.char_define").CharTotal
local r3_0 = {
  15,
  16
}
local function r4_0()
  -- line: [12, 14] id: 1
  return system.pathForFile("data/db.sqlite", system.ResourceDirectory)
end
local r5_0 = {
  UnlockAttackUnit01 = 1,
  UnlockBufUnit01 = 2,
  Attck20Up = 17,
  Attack40Up = 18,
  Speed10Up = 19,
  Speed20Up = 20,
  Range10Up = 21,
  Range20Up = 22,
  MpPlus150 = 23,
  MpPlus350 = 24,
  HpPlus5 = 15,
  HpPlus10 = 16,
  SetItem01 = 38,
  CharItemFlareOld = 138,
  CharItemFlareNew = 200,
  CharItemDaisy = 139,
  CharItemBecky = 140,
  CharItemChloe = 141,
  CharItemNicola = 142,
  CharItemChiara = 143,
  CharItemCecilia = 144,
  CharItemBianca = 145,
  CharItemLillian = 146,
  CharItemIris = 147,
  CharItemLyra = 148,
  CharItemTiana = 149,
  CharItemSarah = 150,
  CharItemLuna = 151,
  CharItemLiliLala = 152,
  CharItem15 = 153,
  CharItemKala = 156,
  CharItemAmber = 158,
  CharItemNina = 160,
  CharItemDaisyA = 161,
  CharItemJill = 163,
  CharItemYuiko = 165,
  CharItemBell = 167,
  CharItemYung = 169,
  CharCostDaisyLv02 = 1012,
  CharCostDaisyLv03 = 1013,
  CharCostDaisyLv04 = 1014,
  CharCostBeckyLv02 = 1022,
  CharCostBeckyLv03 = 1023,
  CharCostBeckyLv04 = 1024,
  CharCostChloeLv02 = 1032,
  CharCostChloeLv03 = 1033,
  CharCostChloeLv04 = 1034,
  CharCostNicolaLv02 = 1042,
  CharCostNicolaLv03 = 1043,
  CharCostNicolaLv04 = 1044,
  CharCostChiaraLv02 = 1052,
  CharCostChiaraLv03 = 1053,
  CharCostChiaraLv04 = 1054,
  CharCostCeciliaLv02 = 1062,
  CharCostCeciliaLv03 = 1063,
  CharCostCeciliaLv04 = 1064,
  CharCostBiancaLv02 = 1072,
  CharCostBiancaLv03 = 1073,
  CharCostBiancaLv04 = 1074,
  CharCostLillianLv02 = 1082,
  CharCostLillianLv03 = 1083,
  CharCostLillianLv04 = 1084,
  CharCostIrisLv02 = 1092,
  CharCostIrisLv03 = 1093,
  CharCostIrisLv04 = 1094,
  CharCostLyraLv02 = 1102,
  CharCostLyraLv03 = 1103,
  CharCostLyraLv04 = 1104,
  CharCostTianaLv02 = 1112,
  CharCostTianaLv03 = 1113,
  CharCostTianaLv04 = 1114,
  CharCostSarahLv02 = 1122,
  CharCostSarahLv03 = 1123,
  CharCostSarahLv04 = 1124,
  CharCostLunaLv02 = 1132,
  CharCostLunaLv03 = 1133,
  CharCostLunaLv04 = 1134,
  CharCostLiliLalaLv02 = 1142,
  CharCostLiliLalaLv03 = 1143,
  CharCostLiliLalaLv04 = 1144,
  CharCostKalaLv02 = 1172,
  CharCostKalaLv03 = 1173,
  CharCostKalaLv04 = 1174,
  CharCostAmberLv02 = 1182,
  CharCostAmberLv03 = 1183,
  CharCostAmberLv04 = 1184,
  CharCostNinaLv02 = 1192,
  CharCostNinaLv03 = 1193,
  CharCostNinaLv04 = 1194,
  CharCostDaisyALv02 = 1202,
  CharCostDaisyALv03 = 1203,
  CharCostDaisyALv04 = 1204,
  CharCostJillLv02 = 1212,
  CharCostJillLv03 = 1213,
  CharCostJillLv04 = 1214,
  CharCostYuikoLv02 = 1222,
  CharCostYuikoLv03 = 1223,
  CharCostYuikoLv04 = 1224,
  CharCostBellLv02 = 1232,
  CharCostBellLv03 = 1232,
  CharCostBellLv04 = 1234,
  CharCostYungLv02 = 1242,
  CharCostYungLv03 = 1242,
  CharCostYungLv04 = 1244,
  OrbFullRecovery = 2001,
  OrbHalfRecovery = 2002,
  OrbMaxUp = 2101,
  BuyExp = 2102,
  HpFullRecovery = 2201,
}
local r6_0 = {
  {
    r5_0.UnlockAttackUnit01,
    180,
    {
      16,
      17
    },
    0,
    {
      summon = 10,
    }
  },
  {
    r5_0.UnlockBufUnit01,
    240,
    {
      18,
      17
    },
    0,
    {
      attack = 10,
    }
  },
  {
    r5_0.Attck20Up,
    300,
    {
      19,
      20
    },
    1,
    {
      attack = 20,
    }
  },
  {
    r5_0.Attack40Up,
    500,
    {
      19,
      20
    },
    1,
    {
      attack = 40,
    }
  },
  {
    r5_0.Speed10Up,
    240,
    {
      21,
      22
    },
    1,
    {
      speed = 10,
    }
  },
  {
    r5_0.Speed20Up,
    400,
    {
      21,
      22
    },
    1,
    {
      speed = 20,
    }
  },
  {
    r5_0.Range10Up,
    180,
    {
      23,
      24
    },
    1,
    {
      range = 10,
    }
  },
  {
    r5_0.Range20Up,
    300,
    {
      23,
      24
    },
    1,
    {
      range = 20,
    }
  },
  {
    r5_0.MpPlus150,
    200,
    {
      25
    },
    1,
    {
      mp = 150,
    }
  },
  {
    r5_0.MpPlus350,
    400,
    {
      25
    },
    1,
    {
      mp = 350,
    }
  },
  {
    r5_0.HpPlus5,
    260,
    {
      27
    },
    1,
    {
      hp = 5,
    }
  },
  {
    r5_0.HpPlus10,
    470,
    {
      27
    },
    1,
    {
      hp = 10,
    }
  },
  {
    r5_0.SetItem01,
    970,
    {
      188,
      189
    },
    1,
    {
      attack = 60,
      speed = 30,
      range = 30,
    }
  },
  {
    r5_0.CharItemFlareOld,
    200,
    {}
  },
  {
    r5_0.CharItemFlareNew,
    400,
    {}
  },
  {
    r5_0.CharItemDaisy,
    160
  },
  {
    r5_0.CharItemBecky,
    200
  },
  {
    r5_0.CharItemChloe,
    240
  },
  {
    r5_0.CharItemNicola,
    220
  },
  {
    r5_0.CharItemChiara,
    180
  },
  {
    r5_0.CharItemCecilia,
    480
  },
  {
    r5_0.CharItemBianca,
    600
  },
  {
    r5_0.CharItemLillian,
    720
  },
  {
    r5_0.CharItemIris,
    660
  },
  {
    r5_0.CharItemLyra,
    540
  },
  {
    r5_0.CharItemTiana,
    400
  },
  {
    r5_0.CharItemSarah,
    400
  },
  {
    r5_0.CharItemLuna,
    480
  },
  {
    r5_0.CharItemLiliLala,
    360
  },
  {
    r5_0.CharItem15,
    80
  },
  {
    r5_0.CharItemKala,
    200
  },
  {
    r5_0.CharItemAmber,
    240
  },
  {
    r5_0.CharItemNina,
    320
  },
  {
    r5_0.CharItemDaisyA,
    180
  },
  {
    r5_0.CharItemJill,
    80
  },
  {
    r5_0.CharItemYuiko,
    220
  },
  {
    r5_0.CharItemBell,
    120
  },
  {
    r5_0.CharItemYung,
    120
  },
  {
    r5_0.OrbFullRecovery,
    800
  },
  {
    r5_0.OrbHalfRecovery,
    500
  },
  {
    r5_0.OrbMaxUp,
    3500,
    3
  },
  {
    r5_0.BuyExp,
    5000,
    10000
  },
  {
    r5_0.HpFullRecovery,
    40
  }
}
function r5_0.new()
  -- line: [445, 449] id: 6
  return {}
end
return {
  pay_item_data = r5_0,
  Init = function()
    -- line: [239, 397] id: 2
    local r1_2 = r0_0.open(r4_0())
    local r2_2 = {
      {
        r5_0.CharCostDaisyLv02,
        r5_0.CharCostDaisyLv03,
        r5_0.CharCostDaisyLv04
      },
      {
        r5_0.CharCostBeckyLv02,
        r5_0.CharCostBeckyLv03,
        r5_0.CharCostBeckyLv04
      },
      {
        r5_0.CharCostChloeLv02,
        r5_0.CharCostChloeLv03,
        r5_0.CharCostChloeLv04
      },
      {
        r5_0.CharCostNicolaLv02,
        r5_0.CharCostNicolaLv03,
        r5_0.CharCostNicolaLv04
      },
      {
        r5_0.CharCostChiaraLv02,
        r5_0.CharCostChiaraLv03,
        r5_0.CharCostChiaraLv04
      },
      {
        r5_0.CharCostCeciliaLv02,
        r5_0.CharCostCeciliaLv03,
        r5_0.CharCostCeciliaLv04
      },
      {
        r5_0.CharCostBiancaLv02,
        r5_0.CharCostBiancaLv03,
        r5_0.CharCostBiancaLv04
      },
      {
        r5_0.CharCostLillianLv02,
        r5_0.CharCostLillianLv03,
        r5_0.CharCostLillianLv04
      },
      {
        r5_0.CharCostIrisLv02,
        r5_0.CharCostIrisLv03,
        r5_0.CharCostIrisLv04
      },
      {
        r5_0.CharCostLyraLv02,
        r5_0.CharCostLyraLv03,
        r5_0.CharCostLyraLv04
      },
      {
        r5_0.CharCostTianaLv02,
        r5_0.CharCostTianaLv03,
        r5_0.CharCostTianaLv04
      },
      {
        r5_0.CharCostSarahLv02,
        r5_0.CharCostSarahLv03,
        r5_0.CharCostSarahLv04
      },
      {
        r5_0.CharCostLunaLv02,
        r5_0.CharCostLunaLv03,
        r5_0.CharCostLunaLv04
      },
      {
        r5_0.CharCostLiliLalaLv02,
        r5_0.CharCostLiliLalaLv03,
        r5_0.CharCostLiliLalaLv04
      },
      {
        r5_0.CharCostKalaLv02,
        r5_0.CharCostKalaLv03,
        r5_0.CharCostKalaLv04
      },
      {
        r5_0.CharCostAmberLv02,
        r5_0.CharCostAmberLv03,
        r5_0.CharCostAmberLv04
      },
      {
        r5_0.CharCostNinaLv02,
        r5_0.CharCostNinaLv03,
        r5_0.CharCostNinaLv04
      },
      {
        r5_0.CharCostDaisyALv02,
        r5_0.CharCostDaisyALv03,
        r5_0.CharCostDaisyALv04
      },
      {
        r5_0.CharCostJillLv02,
        r5_0.CharCostJillLv03,
        r5_0.CharCostJillLv04
      },
      {
        r5_0.CharCostYuikoLv02,
        r5_0.CharCostYUikoLv03,
        r5_0.CharCostYuikoLv04
      },
      {
        r5_0.CharCostBellLv02,
        r5_0.CharCostBellLv03,
        r5_0.CharCostBellLv04
      },
      {
        r5_0.CharCostYungLv02,
        r5_0.CharCostYungLv03,
        r5_0.CharCostYungLv04
      }
    }
    local r3_2 = r1_2:prepare("SELECT value FROM cost WHERE uid=? AND level=? AND flag=0")
    for r7_2, r8_2 in pairs(r2_2) do
      local r9_2 = 2
      local r10_2 = pairs
      local r11_2 = r8_2
      for r13_2, r14_2 in r10_2(r11_2) do
        local r15_2 = math.floor((r14_2 - 1000) / 10)
        r3_2:reset()
        r3_2:bind_values(r15_2, r9_2)
        for r19_2 in r3_2:nrows() do
          local r20_2 = table.insert
          local r21_2 = r6_0
          local r22_2 = {
            r14_2,
            r19_2.value
          }
          r20_2(r21_2, r22_2)
        end
        r9_2 = r9_2 + 1
      end
    end
    r3_2:finalize()
    r1_2:close()
  end,
  getItemData = function(r0_3)
    -- line: [402, 415] id: 3
    local r3_3 = nil	-- notice: implicit variable refs by block#[4]
    for r7_3, r8_3 in pairs(r6_0) do
      if r8_3[1] == r0_3 then
        r3_3 = r8_3
        break
      end
    end
    return r3_3
  end,
  getAllItemData = function()
    -- line: [420, 422] id: 4
    return r6_0
  end,
  GetCharCrystalLevelup = function(r0_5, r1_5)
    -- line: [427, 437] id: 5
    if r0_5 < 1 or r2_0 < r0_5 or table.indexOf(r3_0, r0_5) ~= nil or r1_5 < 2 or 4 < r1_5 then
      return 0
    end
    return 1000 + tonumber(r0_5) * 10 + r1_5
  end,
}
