-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = "ntv:"
local function r1_0(r0_1)
  -- line: [12, 14] id: 1
  system.openURL(r0_1)
end
return {
  DirectForward = r1_0,
  ParseScheme = function(r0_2)
    -- line: [19, 27] id: 2
    local r1_2, r2_2 = string.find(r0_2, r0_0)
    local r3_2 = string.sub(r0_2, 1, r2_2)
    if r3_2 and r3_2 == r0_0 then
      r1_0(string.sub(r0_2, r2_2 + 1))
    end
  end,
}
