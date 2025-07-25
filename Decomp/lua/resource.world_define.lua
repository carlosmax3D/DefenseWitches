-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = {
  DeepForest = 1,
  SteepValley = 2,
  CrystalCave = 3,
  ActiveVolcano = 4,
  IceTemple = 5,
  SmallIslands = 6,
  GhostTown = 7,
  HugeTreeHouse = 8,
  SkyIsland = 9,
  AncientTemple = 10,
}
local r1_0 = {
  "DeepForest",
  "SteepValley",
  "CrystalCave",
  "ActiveVolcano",
  "IceTemple",
  "SmallIslands",
  "GhostTown",
  "HugeTreeHouse",
  "SkyIsland",
  "AncientTemple"
}
local r2_0 = {
  DeepForest = 41,
  SteepValley = 42,
  CrystalCave = 43,
  ActiveVolcano = 44,
  IceTemple = 45,
  SmallIslands = 46,
  GhostTown = 47,
  HugeTreeHouse = 48,
  SkyIsland = 49,
  AncientTemple = 50,
}
return {
  WorldId = r0_0,
  WorldIdToKey = r1_0,
  WorldNameMessageId = r2_0,
  GetWorldName = function(r0_1)
    -- line: [63, 71] id: 1
    for r4_1, r5_1 in pairs(r0_0) do
      if r5_1 == r0_1 then
        return db.GetMessage(r2_0[r4_1])
      end
    end
    return ""
  end,
}
