-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = _G.VERSION_STATUS_URL
local r1_0 = nil
return {
  GetStatus = function(r0_1, r1_1)
    -- line: [5, 61] id: 1
    local function r2_1(r0_2)
      -- line: [6, 47] id: 2
      if not r0_2.isError and r0_2.status == 200 then
        local r2_2 = system.pathForFile("status.txt", system.TemporaryDirectory)
        local r3_2 = io.open(r2_2, "r")
        assert(r3_2, debug.traceback())
        local r4_2 = nil
        local r5_2 = nil
        local r6_2 = nil
        local r7_2 = {}
        for r11_2 in r3_2:lines() do
          r4_2 = util.StringSplit(r11_2, "=")
          if #r4_2 == 2 then
            r5_2 = string.lower(string.match(r4_2[1], "%s*(%w+)%s*"))
            r6_2 = string.match(r4_2[2], "%s*(%w+)%s*")
            if string.match(r6_2, "%d+") then
              r6_2 = tonumber(r6_2)
            end
            r7_2[r5_2] = r6_2
          end
        end
        r3_2:close()
        os.remove(r2_2)
        _G.ServerStatus = r7_2
      end
      if r1_0 then
        local r1_2 = r1_0[1]
        local r2_2 = r1_0[2]
        r1_0 = nil
        if r1_2 then
          r1_2(r2_2)
        end
      end
    end
    if r1_0 then
      r1_0 = {
        r0_1,
        r1_1
      }
    else
      r1_0 = {
        r0_1,
        r1_1
      }
      network.download(r0_0 .. _G.Version .. "/status.txt", "GET", r2_1, {
        timeout = 3,
      }, "status.txt", system.TemporaryDirectory)
    end
  end,
}
