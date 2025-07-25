-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = {
  Egger = 1,
  Rabby = 2,
  Shellder = 3,
  Fley = 4,
  Eely = 5,
  Snowm = 6,
  Vampy = 7,
  Bc = 8,
  Dragony = 9,
  Pinky = 10,
  PinkyJr = 11,
  Cornet = 12,
  Ent = 13,
  Griffon = 14,
  Boulder = 15,
  FireGolem = 16,
  WhiteKnight = 17,
  CaptainT = 18,
  Golem = 19,
  Tornado = 20,
  Dragon = 21,
  Hecate = 22,
}
local r1_0 = {
  Egger = 415,
  Rabby = 416,
  Shellder = 417,
  Fley = 418,
  Eely = 419,
  Snowm = 420,
  Vampy = 421,
  Bc = 422,
  Dragony = 423,
  Pinky = 424,
  PinkyJr = 425,
  Ent = 426,
  Griffon = 427,
  Boulder = 428,
  FireGolem = 429,
  WhiteKnight = 430,
  CaptainT = 431,
  Golem = 432,
  Tornado = 433,
  Dragon = 434,
  Hecate = 435,
  Cornet = 436,
}
return {
  EnemyId = r0_0,
  EnemyNameMessageId = r1_0,
  VoiceArtistName = {
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    477,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1
  },
  GetEnemyName = function(r0_1)
    -- line: [98, 106] id: 1
    for r4_1, r5_1 in pairs(r0_0) do
      if r5_1 == r0_1 then
        return db.GetMessage(r1_0[r4_1])
      end
    end
    return ""
  end,
}
