-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = nil	-- notice: implicit variable refs by block#[0]
local function r1_0(r0_1)
  -- line: [16, 16] id: 1
  return "data/ticker/" .. r0_1 .. ".png"
end
return {
  SetTickerMsg = function(r0_2)
    -- line: [22, 64] id: 2
    if r0_0 ~= nil then
      display.remove(r0_0)
    end
    r0_0 = db.GetMessage(r0_2)
    r0_0 = util.MakeText3({
      rtImg = _G.TickerRoot,
      size = 25,
      color = {
        255,
        255,
        0
      },
      shadow = {
        54,
        63,
        76
      },
      diff_x = 1,
      diff_y = 2,
      msg = r0_0,
    })
    r0_0.x = 20
    r0_0.y = 580
    if _G.UILanguage == "jp" then
      if r0_2 == 403 then
        util.LoadParts(r0_0, r1_0("tap"), 0, -20)
      elseif r0_2 == 406 then
        util.LoadParts(r0_0, r1_0("play"), 0, -20)
      elseif r0_2 == 407 then
        util.LoadParts(r0_0, r1_0("mp"), 163, -10)
      elseif r0_2 == 408 then
        util.LoadParts(r0_0, r1_0("mp"), 40, -10)
        util.LoadParts(r0_0, r1_0("tap"), 280, -20)
      elseif r0_2 == 409 then
        util.LoadParts(r0_0, r1_0("mp"), 40, -10)
      end
    elseif _G.UILanguage == "en" then
      if r0_2 == 403 then
        util.LoadParts(r0_0, r1_0("tap"), 70, -20)
      elseif r0_2 == 406 then
        util.LoadParts(r0_0, r1_0("play"), 50, -20)
      elseif r0_2 == 407 then
        util.LoadParts(r0_0, r1_0("mp"), 330, -10)
      elseif r0_2 == 408 then
        util.LoadParts(r0_0, r1_0("mp"), 38, -10)
        util.LoadParts(r0_0, r1_0("tap"), 285, -20)
      elseif r0_2 == 409 then
        util.LoadParts(r0_0, r1_0("mp"), 90, -10)
      end
    end
  end,
}
