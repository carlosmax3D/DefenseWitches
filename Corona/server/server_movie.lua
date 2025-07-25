-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = _G.MOVIE_URL
return {
  GetMovieUrl = function(r0_1)
    -- line: [7, 14] id: 1
    local r1_1 = r0_0
    if r0_1 ~= nil then
      r1_1 = r1_1 .. "?lang=" .. _G.UILanguage .. "&cid=" .. tostring(r0_1)
    end
    return r1_1
  end,
}
