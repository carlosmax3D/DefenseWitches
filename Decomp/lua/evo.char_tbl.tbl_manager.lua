-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = 1
local r1_0 = 2
local r2_0 = 200
local r3_0 = 300
local r4_0 = {
  "Ⅰ",
  "Ⅱ",
  "Ⅲ",
  "Ⅳ"
}
local r5_0 = {
  1,
  9,
  361
}
local r6_0 = {
  10,
  19,
  362
}
local r7_0 = {
  20,
  29,
  363
}
local r8_0 = {
  30,
  39,
  364
}
local r9_0 = {
  dtm = "text_evo_extension_",
  rdrt = "text_drop_",
  admp = "text_mp",
  rchp = "text_heal_hp_",
}
local r10_0 = {
  {
    dtm = 5000,
  },
  {
    dtm = 10000,
  },
  {
    dtm = 15000,
  },
  {
    dtm = 20000,
  },
  {
    dtm = 25000,
  },
  {
    dtm = 30000,
  },
  {
    dtm = nil,
  },
  {
    dtm = nil,
  },
  {
    dtm = nil,
  },
  {
    rdrt = 0.1,
  },
  {
    rdrt = 0.2,
  },
  {
    rdrt = 0.3,
  },
  {
    rdrt = 0.4,
  },
  {
    rdrt = 0.5,
  },
  {
    rdrt = 0.6,
  },
  {
    rdrt = nil,
  },
  {
    rdrt = nil,
  },
  {
    rdrt = nil,
  },
  {
    rdrt = nil,
  },
  {
    admp = 20,
  },
  {
    admp = 50,
  },
  {
    admp = 80,
  },
  {
    admp = 100,
  },
  {
    admp = nil,
  },
  {
    admp = nil,
  },
  {
    admp = nil,
  },
  {
    admp = nil,
  },
  {
    admp = nil,
  },
  {
    admp = nil,
  },
  {
    rchp = 1,
  },
  {
    rchp = 3,
  },
  {
    rchp = 6,
  },
  {
    rchp = 10,
  },
  {
    rchp = nil,
  },
  {
    rchp = nil,
  },
  {
    rchp = nil,
  },
  {
    rchp = nil,
  },
  {
    rchp = nil,
  },
  {
    rchp = nil,
  }
}
return {
  getRankTable = function(r0_1)
    -- line: [94, 142] id: 1
    local r1_1 = nil	-- notice: implicit variable refs by block#[44]
    if r0_1 == 1 then
      r1_1 = require("evo.char_tbl.tbl_c01_daisy").CreateTable()
    elseif r0_1 == 2 then
      r1_1 = require("evo.char_tbl.tbl_c02_becky").CreateTable()
    elseif r0_1 == 3 then
      r1_1 = require("evo.char_tbl.tbl_c03_chloe").CreateTable()
    elseif r0_1 == 4 then
      r1_1 = require("evo.char_tbl.tbl_c04_nicola").CreateTable()
    elseif r0_1 == 5 then
      r1_1 = require("evo.char_tbl.tbl_c05_chiara").CreateTable()
    elseif r0_1 == 6 then
      r1_1 = require("evo.char_tbl.tbl_c06_cecilia").CreateTable()
    elseif r0_1 == 7 then
      r1_1 = require("evo.char_tbl.tbl_c07_bianca").CreateTable()
    elseif r0_1 == 8 then
      r1_1 = require("evo.char_tbl.tbl_c08_lillian").CreateTable()
    elseif r0_1 == 9 then
      r1_1 = require("evo.char_tbl.tbl_c09_iris").CreateTable()
    elseif r0_1 == 10 then
      r1_1 = require("evo.char_tbl.tbl_c10_lyra").CreateTable()
    elseif r0_1 == 11 then
      r1_1 = require("evo.char_tbl.tbl_c11_tiana").CreateTable()
    elseif r0_1 == 12 then
      r1_1 = require("evo.char_tbl.tbl_c12_sarah").CreateTable()
    elseif r0_1 == 13 then
      r1_1 = require("evo.char_tbl.tbl_c13_luna").CreateTable()
    elseif r0_1 == 14 then
      r1_1 = require("evo.char_tbl.tbl_c14_lililala").CreateTable()
    elseif r0_1 == 17 then
      r1_1 = require("evo.char_tbl.tbl_c17_kala").CreateTable()
    elseif r0_1 == 18 then
      r1_1 = require("evo.char_tbl.tbl_c18_amber").CreateTable()
    elseif r0_1 == 19 then
      r1_1 = require("evo.char_tbl.tbl_c19_nina").CreateTable()
    elseif r0_1 == 20 then
      r1_1 = require("evo.char_tbl.tbl_c20_daisy").CreateTable()
    elseif r0_1 == 21 then
      r1_1 = require("evo.char_tbl.tbl_c21_jill").CreateTable()
    elseif r0_1 == 22 then
      r1_1 = require("evo.char_tbl.tbl_c22_yuiko").CreateTable()
    elseif r0_1 == 23 then
      r1_1 = require("evo.char_tbl.tbl_c23_bell").CreateTable()
    elseif r0_1 == 24 then
      r1_1 = require("evo.char_tbl.tbl_c24_yung").CreateTable()
    end
    return r1_1
  end,
  GetRankupSoundId = function(r0_2)
    -- line: [147, 154] id: 2
    if r0_2 ~= r0_0 and r0_2 ~= r1_0 then
      return nil
    end
    return r3_0 + r0_2
  end,
  new = function(r0_3)
    -- line: [159, 445] id: 3
    local r1_3 = {}
    local r2_3 = false
    local r3_3 = nil
    local r4_3 = nil
    local r5_3 = nil
    local r6_3 = nil
    local function r7_3(r0_4)
      -- line: [183, 183] id: 4
      return "data/evo/evo_combi/" .. r0_4 .. ".png"
    end
    local function r8_3(r0_5)
      -- line: [184, 184] id: 5
      return r7_3(r0_5 .. _G.UILanguage)
    end
    function r1_3.IsEvoChar()
      -- line: [219, 221] id: 7
      return r2_3
    end
    function r1_3.GetEvoData(r0_8)
      -- line: [228, 236] id: 8
      if r3_3 == nil or r0_8 < 1 or #r3_3 < r0_8 then
        return nil
      end
      return r3_3[r0_8]
    end
    function r1_3.GetEvoDataMax()
      -- line: [242, 247] id: 9
      if r3_3 == nil then
        return -1
      end
      return #r3_3
    end
    function r1_3.GetEvoLevelScale(r0_10)
      -- line: [254, 261] id: 10
      if r4_3 == nil or r0_10 < 1 or #r4_3 < r0_10 then
        return nil
      end
      return r4_3[r0_10]
    end
    function r1_3.GetCombiSkillData()
      -- line: [267, 269] id: 11
      return r5_3[1]
    end
    function r1_3.GetCombiSkillEffectData()
      -- line: [275, 309] id: 12
      if r5_3[1].typ < 1 or #r10_0 < r5_3[1].typ then
        return nil
      end
      local r0_12 = r10_0[r5_3[1].typ]
      if r0_12 == nil then
        return nil
      end
      local r1_12 = nil
      local r2_12 = nil
      local r3_12 = nil
      for r7_12, r8_12 in pairs(r0_12) do
        r3_12 = r7_12
        break
      end
      local r4_12 = r9_0[r3_12]
      if r4_12 == nil then
        return nil
      end
      local r5_12 = nil
      local r6_12 = string.len(r4_12)
      if string.sub(r4_12, r6_12, r6_12) == "_" then
        r5_12 = r8_3(r4_12)
      else
        r5_12 = r7_3(r4_12)
      end
      return {
        r5_12,
        r0_12
      }
    end
    function r1_3.GetRankData(r0_13)
      -- line: [316, 322] id: 13
      if r0_13 < 1 or #r6_3 < r0_13 then
        return nil
      end
      return r6_3[r0_13]
    end
    function r1_3.GetRankDataMax()
      -- line: [328, 330] id: 14
      return #r6_3
    end
    function r1_3.GetExp2Rank(r0_15)
      -- line: [337, 350] id: 15
      local r3_15 = 1
      for r7_15, r8_15 in pairs(r6_3) do
        if r8_15.exp ~= nil and r0_15 < r8_15.exp then
          break
        else
          r3_15 = r7_15
        end
      end
      return r3_15
    end
    function r1_3.GetRank2EffectString(r0_16)
      -- line: [357, 399] id: 16
      local r1_16 = r1_3.GetRankData(r0_16)
      if r1_16 == nil then
        return nil
      end
      local function r2_16(r0_17, r1_17)
        -- line: [364, 370] id: 17
        local r2_17 = db.GetMessage(r0_17)
        if r2_17 == nil then
          return nil
        end
        return string.format(r2_17, r1_17)
      end
      if r5_0[1] <= r1_16.efc and r1_16.efc <= r5_0[2] then
        if r1_16.evlv < 1 or #r4_0 < r1_16.evlv then
          return nil
        end
        return r2_16(r5_0[3], r4_0[r1_16.evlv])
      elseif r6_0[1] <= r1_16.efc and r1_16.efc <= r6_0[2] then
        return r2_16(r6_0[3], math.floor(r1_16.dtm * 0.001))
      elseif r7_0[1] <= r1_16.efc and r1_16.efc <= r7_0[2] then
        return r2_16(r7_0[3], math.floor(r1_16.drt * 100))
      elseif r8_0[1] <= r1_16.efc and r1_16.efc <= r8_0[2] then
        return r2_16(r8_0[3], r1_16.omx)
      end
      return nil
    end
    function r1_3.GetCharRank(r0_18)
      -- line: [406, 408] id: 18
      return db.LoadCharRank(_G.UserID, r0_18)
    end
    function r1_3.GetTotalOmx(r0_19)
      -- line: [415, 422] id: 19
      local r1_19 = 0
      for r5_19 = 1, r0_19, 1 do
        r1_19 = r1_19 + r6_3[r5_19].omx
      end
      return r1_19
    end
    function r1_3.GetEvoSoundId(r0_20)
      -- line: [427, 436] id: 20
      if r0_20.evo == nil or r0_20.evo.evoLevel == nil or r0_20.evo.evoLevel < 1 or r1_3.GetEvoDataMax(r0_20) < r0_20.evo.evoLevel then
        return nil
      end
      return r2_0 + r0_20.evo.evoLevel
    end
    (function()
      -- line: [189, 210] id: 6
      r2_3 = false
      if r0_3.isEvoChar ~= nil then
        r2_3 = r0_3.isEvoChar
      end
      r3_3 = nil
      if r0_3.evoData ~= nil then
        r3_3 = r0_3.evoData
      end
      r4_3 = nil
      if r0_3.evoLevelScale ~= nil then
        r4_3 = r0_3.evoLevelScale
      end
      r5_3 = nil
      if r0_3.combiSkillData ~= nil then
        r5_3 = r0_3.combiSkillData
      end
      r6_3 = nil
      if r0_3.rankData ~= nil then
        r6_3 = r0_3.rankData
      end
    end)()
    return r1_3
  end,
  RankupSoundTypeRankup = r0_0,
  RankupSoundTypeGetSkill = r1_0,
}
