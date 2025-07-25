-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r20_0 = nil	-- notice: implicit variable refs by block#[0]
local r19_0 = nil	-- notice: implicit variable refs by block#[0]
local r0_0 = require("ad.myads")
local r1_0 = require("ad.adcropsWall")
local r2_0 = require("ad.adcropsIcon")
local r3_0 = require("ad.adfurikunIcon")
local r4_0 = require("ad.adgenerationIcon")
local r5_0 = require("server.bingoFlag")
local r6_0 = require("json")
local r7_0 = print
local r8_0 = string
local r9_0 = display
local r10_0 = tostring
local r11_0 = type
local r12_0 = pairs
local r13_0 = table
local r14_0 = tonumber
local r15_0 = debug
local r16_0 = assert
local function r17_0(r0_1, r1_1)
  -- line: [24, 42] id: 1
  local r2_1 = {}
  local r3_1 = "(.-)" .. r1_1
  local r4_1 = 1
  local r5_1, r6_1, r7_1 = r0_1:find(r3_1, 1)
  while r5_1 do
    if r5_1 ~= 1 or r7_1 ~= "" then
      r13_0.insert(r2_1, r7_1)
    end
    r4_1 = r6_1 + 1
    r5_1, r6_1, r7_1 = r0_1:find(r3_1, r4_1)
  end
  if r4_1 <= #r0_1 then
    r13_0.insert(r2_1, r0_1:sub(r4_1))
  end
  return r2_1
end
local function r18_0(r0_2, r1_2)
  -- line: [44, 78] id: 2
  local r2_2 = false
  if r0_2.disable then
    return r2_2
  end
  local r3_2 = r1_2.phase
  local r4_2 = r0_2
  local r5_2 = r9_0.getCurrentStage()
  if r3_2 == "began" then
    r0_2.rect = r0_2.stageBounds
    r0_2.xScale = r0_2.on_scale
    r0_2.yScale = r0_2.on_scale
    if r5_2 then
      r5_2:setFocus(r0_2)
    end
    r0_2.is_foucs = true
    r2_2 = true
  elseif r0_2.is_foucs then
    local r6_2 = r1_2.x
    local r7_2 = r1_2.y
    local r8_2 = r0_2.rect
    local r9_2 = r8_2.xMin
    if r9_2 <= r6_2 then
      r9_2 = r8_2.xMax
      if r6_2 <= r9_2 then
        r9_2 = r8_2.yMin
        if r9_2 <= r7_2 then
          r9_2 = r7_2 <= r8_2.yMax
        end
      end
    else
      goto label_44	-- block#11 is visited secondly
    end
    if r3_2 == "moved" and not r9_2 then
      r3_2 = "cancelled"
    end
    if r3_2 == "ended" or r3_2 == "cancelled" then
      r0_2.xScale = 1
      r0_2.yScale = 1
      r0_2.is_foucs = false
      if r5_2 then
        r5_2:setFocus(nil)
      end
      if r3_2 == "ended" and r9_2 and r0_2.onRelease then
        r2_2 = r0_2.onRelease(r0_2)
      end
    end
  end
  return r2_2
end
function r19_0(r0_3, r1_3, r2_3)
  -- line: [80, 102] id: 3
  if r11_0(r0_3) ~= "table" then
    if not r0_3 then
      r0_3 = ""
    end
    r7_0("@@@@" .. r0_3)
    return 
  end
  if not r2_3 then
    r2_3 = {}
  end
  if not r1_3 then
    r1_3 = ""
  end
  r1_3 = "@@@@" .. r1_3
  local r3_3 = nil
  for r7_3, r8_3 in r12_0(r0_3) do
    if r11_0(r8_3) == "table" and not r2_3[r8_3] then
      if not r3_3 then
        r3_3 = r1_3 .. r8_0.rep(" ", r8_0.len(r10_0(r7_3)) + 2)
      end
      r2_3[r8_3] = true
      r7_0(r1_3 .. "[" .. r10_0(r7_3) .. "] => Table {")
      r7_0(r3_3 .. "{")
      r19_0(r8_3, r3_3 .. r8_0.rep(" ", 2), r2_3)
      r7_0(r3_3 .. "}")
    else
      r7_0(r1_3 .. "[" .. r10_0(r7_3) .. "] => " .. r10_0(r8_3) .. "")
    end
  end
end
function r20_0(r0_4, r1_4, r2_4)
  -- line: [104, 121] id: 4
  if r2_4 == nil then
    r2_4 = ""
  end
  if r11_0(r0_4) ~= "table" then
    return r2_4 .. "<" .. r10_0(r0_4) .. ">"
  end
  if not r1_4 then
    r1_4 = {}
  end
  for r6_4, r7_4 in r12_0(r0_4) do
    if r11_0(r7_4) == "table" and not r1_4[r7_4] then
      r1_4[r7_4] = true
      r2_4 = r2_4 .. "{"
      r2_4 = r2_4 .. r20_0(r7_4, r1_4, r2_4)
      r2_4 = r2_4 .. "}"
    else
      r2_4 = r2_4 .. "[" .. r10_0(r6_4) .. "=>" .. r10_0(r7_4) .. "]"
    end
  end
  return r2_4
end
local function r26_0(r0_10)
  -- line: [202, 224] id: 10
  -- notice: unreachable block#7
  local r1_10 = r0_10.group
  local r2_10 = r0_10.cx
  local r3_10 = r0_10.cy
  if r2_10 and r3_10 then
    r1_10.x = r2_10
    r1_10.y = r3_10
  else
    r1_10.x = r0_10.x + r1_10.width * 0.5
    r1_10.y = r0_10.y + r1_10.height * 0.5
  end
  r1_10.onRelease = r0_10.func
  r1_10.param = r0_10.param
  r1_10.is_foucs = false
  local r4_10 = r0_10.scale
  if r4_10 == nil then
    r4_10 = 0.9
  else
    r4_10 = false
  end
  r1_10.on_scale = r4_10
  r1_10.touch = r18_0
  r4_10 = r0_10.show
  if r4_10 == nil then
    r4_10 = true or r0_10.show
  else
    goto label_41	-- block#10 is visited secondly
  end
  r1_10.isVisible = r4_10
  if r0_10.disable == nil then
  end
  r1_10.disable = r0_10.disable
  r1_10:addEventListener("touch", r1_10)
  return r1_10
end
local function r30_0(r0_14, r1_14, r2_14)
  -- line: [258, 286] id: 14
  r16_0(r1_14, r15_0.traceback())
  local r3_14 = nil
  if r2_14 then
    r3_14 = r2_14 .. "/"
  else
    r3_14 = ""
  end
  r16_0(r1_14[1], r15_0.traceback())
  r3_14 = r3_14 .. r1_14[1]
  local r4_14 = r9_0.newGroup()
  r4_14.width = r1_14[2]
  r4_14.height = r1_14[3]
  local r5_14 = r1_14[4]
  local r6_14 = r1_14[5] - 1
  local r8_14 = nil
  local r9_14 = nil
  for r13_14, r14_14 in r12_0(r1_14[6]) do
    r8_14 = r8_0.format("%s/%08d.%s", r3_14, r13_14 - 1, r5_14)
    r9_14 = cdn.NewImage(r4_14, r8_14, nil, nil, nil)
    r9_14:setReferencePoint(r9_0.TopLeftReferencePoint)
    r9_14.x = r14_14[1]
    r9_14.y = r14_14[2]
  end
  if r0_14 then
    r0_14:insert(r4_14)
  end
  return r4_14
end
local function r33_0(r0_17, r1_17, r2_17, r3_17)
  -- line: [309, 321] id: 17
  local r4_17 = cdn.NewImage(r0_17, r1_17, nil, nil, true)
  if r4_17 == nil then
    DebugPrint("image load err:" .. r1_17)
    return nil
  end
  r16_0(r4_17, r15_0.traceback())
  r4_17:setReferencePoint(r9_0.TopLeftReferencePoint)
  r4_17.x = r2_17
  r4_17.y = r3_17
  return r4_17
end
local function r39_0(r0_24)
  -- line: [431, 484] id: 24
  r16_0(r0_24 and r11_0(r0_24) == "table", r15_0.traceback())
  local r1_24 = r0_24.size
  local r2_24 = r0_24.color
  local r3_24 = r0_24.shadow
  local r4_24 = r0_24.msg
  local r5_24 = r0_24.width
  local r6_24 = r0_24.height
  local r7_24 = r0_24.diff_x
  local r8_24 = r0_24.diff_y
  if r7_24 == nil then
    r7_24 = 1
  end
  if r8_24 == nil then
    r8_24 = 1
  end
  r16_0(r1_24, r15_0.traceback())
  r16_0(r2_24, r15_0.traceback())
  r16_0(r4_24, r15_0.traceback())
  local r9_24 = nil
  if r0_24.font then
    r9_24 = r0_24.font
  else
    r9_24 = native.systemFontBold
  end
  local r10_24 = r9_0.newGroup()
  local r11_24 = nil
  if r5_24 ~= nil and r6_24 ~= nil then
    if r3_24 then
      r9_0.newText(r10_24, r4_24, r7_24, r8_24, r5_24, r6_24, r9_24, r1_24):setFillColor(r3_24[1], r3_24[2], r3_24[3])
    end
    r9_0.newText(r10_24, r4_24, 0, 0, r5_24, r6_24, r9_24, r1_24):setFillColor(r2_24[1], r2_24[2], r2_24[3])
  else
    if r3_24 then
      r9_0.newText(r10_24, r4_24, r7_24, r8_24, r9_24, r1_24):setFillColor(r3_24[1], r3_24[2], r3_24[3])
    end
    r9_0.newText(r10_24, r4_24, 0, 0, r9_24, r1_24):setFillColor(r2_24[1], r2_24[2], r2_24[3])
  end
  local r12_24 = r0_24.rtImg
  if r12_24 then
    r12_24:insert(r10_24)
  end
  local r13_24 = r0_24.x
  local r14_24 = r0_24.y
  if r13_24 and r14_24 then
    r10_24:setReferencePoint(r9_0.TopLeftReferencePoint)
    r10_24.x = r13_24
    r10_24.y = r14_24
  end
  return r10_24
end
local function r41_0(r0_29, r1_29, r2_29, r3_29, r4_29, r5_29)
  -- line: [579, 588] id: 29
  return r39_0({
    size = r0_29,
    color = r1_29,
    shadow = r2_29,
    msg = r3_29,
    width = r4_29,
    height = r5_29,
  })
end
local function r51_0(r0_42, r1_42, r2_42)
  -- line: [931, 943] id: 42
  network.request(_G.REACHABLE_URL, "GET", function(r0_43)
    -- line: [934, 940] id: 43
    if r0_43.isError then
      r2_42(r0_43)
    else
      r1_42(r0_43)
    end
  end, {})
end
return {
  print_r = r19_0,
  print_r2 = r20_0,
  IsContainedTable = function(r0_5, r1_5)
    -- line: [126, 157] id: 5
    if r0_5 == nil or r11_0(r0_5) ~= "table" or r1_5 == nil then
      return false
    end
    if r0_5[r1_5] ~= nil then
      return true
    end
    for r5_5, r6_5 in r12_0(r0_5) do
      if (r11_0(r6_5) == "number" or r11_0(r6_5) == "string" or r11_0(r6_5) == "boolean") and r1_5 == r6_5 then
        return true
      elseif r11_0(r6_5) ~= "table" and r1_5 == r6_5() then
        return true
      end
    end
    return false
  end,
  IsExistFile = function(r0_6)
    -- line: [160, 173] id: 6
    local r1_6 = false
    local r2_6 = system.pathForFile(r0_6, system.ResourceDirectory)
    if r2_6 == nil then
      return r1_6
    end
    local r3_6 = io.open(r2_6, "r")
    if r3_6 then
      io.close(r3_6)
      r1_6 = true
    end
    return r1_6
  end,
  Dirname = function(r0_7)
    -- line: [175, 179] id: 7
    if not r0_7 then
      r0_7 = ""
    end
    return r8_0.gsub(r0_7, "(.*/)(.*)", "%1")
  end,
  Mkdir = function(r0_8)
    -- line: [181, 189] id: 8
    local r1_8 = ""
    for r5_8, r6_8 in ipairs(r17_0(r0_8, "/")) do
      r1_8 = r1_8 .. "/" .. r6_8
      if not lfs.chdir(r1_8) then
        lfs.mkdir(r1_8)
      end
    end
  end,
  MakeGroup = function(r0_9)
    -- line: [193, 197] id: 9
    local r1_9 = r9_0.newGroup()
    r0_9:insert(r1_9)
    return r1_9
  end,
  MakeFrame = function(r0_34, r1_34, r2_34)
    -- line: [690, 808] id: 34
    if r1_34 == nil then
      r1_34 = true
    end
    local r3_34 = _G.WidthDiff
    local r4_34 = _G.HeightDiff
    local r5_34 = r9_0.newGroup()
    if r3_34 < 0 or r4_34 < 0 then
      local r6_34 = nil
      if r4_34 < 0 then
        local r8_34 = _G.Width
        local r9_34 = -r4_34
        r9_0.newRect(r5_34, 0, r4_34, r8_34, r9_34):setFillColor(0, 0, 0)
        r9_0.newRect(r5_34, 0, _G.Height, r8_34, r9_34):setFillColor(0, 0, 0)
      end
      if r3_34 < 0 and r3_34 ~= -176 and r3_34 ~= -177 then
        local r8_34 = r4_34
        local r9_34 = -r3_34
        local r10_34 = _G.Height + -r4_34
        r9_0.newRect(r5_34, r3_34, r8_34, r9_34, r10_34):setFillColor(0, 0, 0)
        r9_0.newRect(r5_34, _G.Width, r8_34, r9_34, r10_34):setFillColor(0, 0, 0)
      end
      local function r7_34(r0_35)
        -- line: [723, 727] id: 35
        if r2_34 ~= nil then
          r2_34(r0_35)
        end
      end
      local function r8_34()
        -- line: [730, 750] id: 36
        local r0_36 = r9_0.newGroup()
        local r1_36 = nil
        r33_0(r0_36, "data/side/side_cornet.png", 0, 0)
        r1_36 = r39_0({
          rtImg = r0_36,
          size = 16,
          color = {
            255,
            255,
            255
          },
          shadow = {
            0,
            0,
            0
          },
          diff_x = 1,
          diff_y = 2,
          msg = "Version " .. _G.Version,
        })
        r1_36:setReferencePoint(r9_0.CenterReferencePoint)
        r1_36.x = 88
        r1_36.y = 620
        r0_36:setReferencePoint(r9_0.TopLeftReferencePoint)
        r5_34:insert(r0_36)
        r0_36.x = r3_34
        r0_36.y = 0
      end
      if r3_34 <= -176 then
        local r9_34 = r9_0.newGroup()
        r33_0(r9_34, "data/side/side_none.png", 0, 0)
        r9_34:setReferencePoint(r9_0.TopRightReferencePoint)
        r5_34:insert(r9_34)
        r9_34.x = 0
        r9_34.y = 0
        if _G.IsSimulator == true then
          r7_34(0)
        elseif r0_0.GetLastRes() and r1_34 == true then
          local r10_34 = 0
          local r11_34 = 0
          if r3_0.GetLastRes() then
            r10_34 = 1
          end
          if r4_0.GetLastRes() then
            r11_34 = 1
          end
          if r10_34 == 0 and r11_34 == 0 then
            r7_34(0)
          else
            iconAds.show()
            r7_34(1)
          end
        else
          iconAds.hide()
          r7_34(0)
        end
      else
        r7_34(0)
      end
      r0_34:insert(r5_34)
      if r3_34 <= -176 then
        return r5_34
      end
    end
    return nil
  end,
  LoadBtnGroup = r26_0,
  LoadBtn = function(r0_11)
    -- line: [226, 233] id: 11
    r16_0(r0_11.rtImg, r15_0.traceback())
    r0_11.group = cdn.NewImage(r0_11.rtImg, r0_11.fname, 0, 0, true)
    r16_0(r0_11.group, r8_0.format("not found %s", r0_11.fname))
    return r26_0(r0_11)
  end,
  SetBtnFunction = function(r0_12, r1_12, r2_12, r3_12, r4_12, r5_12)
    -- line: [235, 244] id: 12
    -- notice: unreachable block#3
    r0_12.onRelease = r1_12
    r0_12.param = r2_12
    r0_12.is_foucs = false
    local r6_12 = nil	-- notice: implicit variable refs by block#[4, 7]
    if r3_12 == nil then
      r6_12 = 0.9
    else
      r6_12 = false
    end
    r0_12.on_scale = r6_12
    if r4_12 == nil then
      r6_12 = true
      if not r6_12 then
        ::label_15::
        r6_12 = r4_12
      end
    else
      goto label_15	-- block#6 is visited secondly
    end
    r0_12.isVisible = r6_12
    if r5_12 == nil then
    end
    r0_12.disable = r5_12
    r0_12.touch = r18_0
    r0_12:addEventListener("touch", r0_12)
  end,
  LoadBG = function(r0_13, r1_13)
    -- line: [249, 253] id: 13
    return cdn.NewImage(r0_13, r1_13, nil, nil, true)
  end,
  LoadTile = r30_0,
  LoadTileBG = function(r0_15, r1_15, r2_15)
    -- line: [288, 295] id: 15
    r16_0(r1_15, r15_0.traceback())
    local r3_15 = r30_0(r0_15, r1_15, r2_15)
    r3_15:setReferencePoint(r9_0.CenterReferencePoint)
    r3_15.x = _G.Width / 2
    r3_15.y = _G.Height / 2
    return r3_15
  end,
  LoadTileParts = function(r0_16, r1_16, r2_16, r3_16, r4_16)
    -- line: [297, 304] id: 16
    r16_0(r3_16, r15_0.traceback())
    local r5_16 = r30_0(r0_16, r3_16, r4_16)
    r5_16:setReferencePoint(r9_0.TopLeftReferencePoint)
    r5_16.x = r1_16
    r5_16.y = r2_16
    return r5_16
  end,
  LoadParts = r33_0,
  LoadPartsCenter = function(r0_18, r1_18, r2_18, r3_18)
    -- line: [326, 333] id: 18
    local r4_18 = r9_0.newImage(r0_18, r1_18, true)
    r16_0(r4_18, r15_0.traceback())
    r4_18.x = r2_18
    r4_18.y = r3_18
    return r4_18
  end,
  MakeMat = function(r0_19, r1_19)
    -- line: [336, 349] id: 19
    local r2_19 = r9_0.newRect(r0_19, -200, -200, _G.Width + 400, _G.Height + 400)
    r2_19:setFillColor(0, 0, 0)
    r2_19.alpha = 0.01
    if r1_19 then
      r2_19.touch = r1_19
    else
      function r2_19.touch(r0_20, r1_20)
        -- line: [345, 345] id: 20
        return true
      end
    end
    r2_19:addEventListener("touch", r2_19)
    return r2_19
  end,
  Num2Column = function(r0_21)
    -- line: [351, 375] id: 21
    local r1_21 = {}
    local r2_21 = nil
    while 0 < r0_21 do
      r2_21 = r0_21 % 1000
      r0_21 = (r0_21 - r2_21) / 1000
      if r0_21 > 0 then
        r13_0.insert(r1_21, r8_0.format("%03d", r2_21))
        r13_0.insert(r1_21, ",")
      else
        r13_0.insert(r1_21, r10_0(r2_21))
      end
    end
    r2_21 = r13_0.maxn(r1_21)
    if r2_21 == 0 then
      r13_0.insert(r1_21, "0")
      r2_21 = 1
    end
    local r3_21 = ""
    for r7_21 = r2_21, 1, -1 do
      r3_21 = r3_21 .. r1_21[r7_21]
    end
    return r3_21
  end,
  GetDistance = function(r0_22, r1_22, r2_22, r3_22, r4_22, r5_22)
    -- line: [377, 393] id: 22
    local r6_22 = r4_22 - r2_22
    local r7_22 = r5_22 - r3_22
    if r6_22 == 0 and r7_22 == 0 then
      return (r2_22 - r0_22) * (r2_22 - r0_22) + (r3_22 - r1_22) * (r3_22 - r1_22)
    end
    local r10_22 = -(r6_22 * (r2_22 - r0_22) + r7_22 * (r3_22 - r1_22)) / (r6_22 * r6_22 + r7_22 * r7_22)
    if r10_22 < 0 then
      r10_22 = 0
    end
    if r10_22 > 1 then
      r10_22 = 1
    end
    local r11_22 = r2_22 + r6_22 * r10_22
    local r12_22 = r3_22 + r7_22 * r10_22
    return (r0_22 - r11_22) * (r0_22 - r11_22) + (r1_22 - r12_22) * (r1_22 - r12_22)
  end,
  CopyFile = function(r0_23, r1_23)
    -- line: [395, 427] id: 23
    local r2_23 = true
    local r3_23 = io.open(r0_23, "rb")
    if r3_23 == nil then
      return false
    end
    local r4_23 = io.open(r1_23, "wb")
    if r4_23 == nil then
      r3_23:close()
      return false
    end
    if not r4_23 then
      r7_0("filebackup error")
      r2_23 = false
    else
      local r5_23 = r3_23:read("*a")
      if not r5_23 then
        r7_0("read error!")
        r2_23 = false
      elseif not r4_23:write(r5_23) then
        r7_0("write error!")
        r2_23 = false
      end
    end
    r3_23:close()
    r4_23:close()
    return r2_23
  end,
  StringSplit = r17_0,
  MakeText = r41_0,
  MakeCenterText = function(r0_32, r1_32, r2_32, r3_32, r4_32, r5_32)
    -- line: [655, 665] id: 32
    local r6_32 = r41_0(r1_32, r3_32, r4_32, r5_32)
    r6_32:setReferencePoint(r9_0.CenterReferencePoint)
    r6_32.x = r2_32[1] + r2_32[3] / 2
    r6_32.y = r2_32[2] + r2_32[4] / 2
    r0_32:insert(r6_32)
    return r6_32
  end,
  MakeText2 = function(r0_30)
    -- line: [606, 653] id: 30
    local r1_30 = r17_0(r8_0.gsub(r0_30.msg, "(\\n)", function(r0_31)
      -- line: [609, 609] id: 31
      return "\n"
    end), "\n")
    local r2_30 = r0_30.w
    local r3_30 = r0_30.h
    local r4_30 = r0_30.color
    local r5_30 = r0_30.shadow
    local r6_30 = r0_30.size
    local r7_30 = r0_30.line
    local r8_30 = 0
    local r9_30 = r9_0.newGroup()
    local r10_30 = nil
    for r14_30, r15_30 in r12_0(r1_30) do
      r10_30 = r41_0(r6_30, r4_30, r5_30, r15_30)
      r9_30:insert(r10_30)
      if r2_30 and r3_30 then
        r10_30:setReferencePoint(r9_0.CenterReferencePoint)
        r10_30.x = r2_30 / 2
        r10_30.y = r8_30 + r7_30 / 2
      else
        r10_30:setReferencePoint(r9_0.TopLeftReferencePoint)
        r10_30.x = 0
        r10_30.y = r8_30
      end
      r8_30 = r8_30 + r7_30
    end
    local r12_30 = r0_30.x
    local r13_30 = r0_30.y
    r0_30.rtImg:insert(r9_30)
    if r2_30 and r3_30 then
      r9_30:setReferencePoint(r9_0.CenterReferencePoint)
      r9_30.x = r12_30 + r2_30 / 2
      r9_30.y = r13_30 + r3_30 / 2
    else
      r9_30:setReferencePoint(r9_0.TopLeftReferencePoint)
      r9_30.x = r12_30
      r9_30.y = r13_30
    end
    return r9_30
  end,
  MakeText3 = r39_0,
  MakeText4 = function(r0_25)
    -- line: [488, 577] id: 25
    local r1_25 = r17_0(r8_0.gsub(r0_25.msg, "(\\n)", function(r0_26)
      -- line: [491, 491] id: 26
      return "\n"
    end), "\n")
    local r2_25 = r0_25.w
    local r3_25 = r0_25.h
    local r4_25 = r0_25.color
    local r5_25 = r0_25.shadow
    local r6_25 = r0_25.size
    local r7_25 = r0_25.line
    local function r8_25(r0_27, r1_27)
      -- line: [502, 522] id: 27
      local r2_27 = 0
      local r3_27 = nil
      for r7_27, r8_27 in r12_0(r1_25) do
        for r12_27 = 1, r1_27, 1 do
          r3_27 = r9_0.newText(r0_27, r8_27, 0, 0, r0_25.font, r0_25.size)
          if r0_25.align == "left" then
            r3_27:setReferencePoint(r9_0.TopLeftReferencePoint)
            r3_27.x = 0
            r3_27.y = r2_27
          else
            r3_27:setReferencePoint(r9_0.CenterReferencePoint)
            r3_27.x = r2_25 / 2
            r3_27.y = r2_27 + r7_25 / 2
          end
        end
        r2_27 = r2_27 + r7_25
      end
    end
    local function r9_25(r0_28, r1_28)
      -- line: [524, 529] id: 28
      for r5_28 = 1, r0_28.numChildren, 1 do
        r0_28[r5_28]:setFillColor(r1_28[1], r1_28[2], r1_28[3])
      end
    end
    local r10_25 = r9_0.newGroup()
    r0_25.rtImg:insert(r10_25)
    if r0_25.shadow ~= nil and r11_0(r0_25.shadow) == "table" then
      local r11_25 = r9_0.newGroup()
      r10_25:insert(r11_25)
      r8_25(r11_25, 8)
      r9_25(r11_25, r0_25.shadow)
      for r15_25 = 1, r11_25.numChildren, 8 do
        r11_25[r15_25].x = r11_25[r15_25].x - r0_25.shadowBoldWidth
        r11_25[r15_25].y = r11_25[r15_25].y - r0_25.shadowBoldWidth
        r11_25[r15_25 + 1].x = r11_25[r15_25 + 1].x + r0_25.shadowBoldWidth
        r11_25[r15_25 + 1].y = r11_25[r15_25 + 1].y + r0_25.shadowBoldWidth
        r11_25[r15_25 + 2].x = r11_25[r15_25 + 2].x + r0_25.shadowBoldWidth
        r11_25[r15_25 + 2].y = r11_25[r15_25 + 2].y - r0_25.shadowBoldWidth
        r11_25[r15_25 + 3].x = r11_25[r15_25 + 3].x - r0_25.shadowBoldWidth
        r11_25[r15_25 + 3].y = r11_25[r15_25 + 3].y + r0_25.shadowBoldWidth
        r11_25[r15_25 + 4].y = r11_25[r15_25 + 4].y - r0_25.shadowBoldWidth - 1
        r11_25[r15_25 + 5].y = r11_25[r15_25 + 5].y + r0_25.shadowBoldWidth + 1
        r11_25[r15_25 + 6].x = r11_25[r15_25 + 6].x - r0_25.shadowBoldWidth - 1
        r11_25[r15_25 + 7].x = r11_25[r15_25 + 7].x + r0_25.shadowBoldWidth + 1
      end
    end
    local r11_25 = r9_0.newGroup()
    r10_25:insert(r11_25)
    r8_25(r11_25, 1)
    r9_25(r11_25, r0_25.color)
    if r0_25.x ~= nil and r0_25.y ~= nil then
      local r12_25 = r0_25.x
      local r13_25 = r0_25.y
      if r2_25 and r3_25 then
        r10_25:setReferencePoint(r9_0.CenterReferencePoint)
        r10_25.x = r12_25 + r2_25 / 2
        r10_25.y = r13_25 + r3_25 / 2
      else
        r10_25:setReferencePoint(r9_0.TopLeftReferencePoint)
        r10_25.x = r12_25
        r10_25.y = r13_25
      end
    end
    return r10_25
  end,
  ChangeScene = function(r0_37)
    -- line: [813, 872] id: 37
    local r1_37 = r0_37.prev
    local r2_37 = r0_37.param
    local r3_37 = r0_37.scene
    if _G.IsSimulator then
      r7_0("###change scene " .. r3_37)
    end
    if r3_37 == "cdn" or r3_37 == "complete" or r3_37 == "cont" or r3_37 == "cont_game" or r3_37 == "cutin" or r3_37 == "endcut" or r3_37 == "force_update_view" or r3_37 == "help" or r3_37 == "hint" or r3_37 == "info" or r3_37 == "invite" or r3_37 == "invite_game" or r3_37 == "map" or r3_37 == "menu" or r3_37 == "restart" or r3_37 == "resume" or r3_37 == "shop" or r3_37 == "stage" or r3_37 == "title" then
      r3_37 = "scene." .. r3_37
    end
    local r4_37 = r0_37.efx
    if r4_37 == nil then
      r4_37 = ""
    end
    if r1_37 then
      if r11_0(r1_37) == "table" then
        for r8_37, r9_37 in r12_0(r1_37) do
          r9_37()
        end
      else
        r1_37()
      end
    end
    local r6_37 = nil
    if nil == r3_37 or r6_37 == r3_37 then
      if r0_37.prev then
        r0_37.prev()
      end
      require(r3_37).new(r2_37)
    elseif r2_37 then
      director:changeScene(r2_37, r3_37, r4_37)
    else
      director:changeScene(r3_37, r4_37)
    end
  end,
  SplitVersion = function(r0_38)
    -- line: [874, 887] id: 38
    local r1_38 = r17_0(r0_38, "[.]")
    local r2_38 = {}
    local r3_38 = nil
    for r7_38, r8_38 in r12_0(r1_38) do
      r3_38 = r14_0(r8_38)
      if r3_38 ~= nil then
        r13_0.insert(r2_38, r3_38)
      else
        r13_0.insert(r2_38, r8_38)
      end
    end
    return r2_38
  end,
  ConvertDisplayCrystal = function(r0_39)
    -- line: [892, 894] id: 39
    return math.ceil(r0_39 * 0.1)
  end,
  CalcQuadraticBezPoint = function(r0_40, r1_40, r2_40, r3_40)
    -- line: [899, 915] id: 40
    local r4_40 = {
      x = 0,
      y = 0,
    }
    local r5_40 = (1 - r3_40) * (1 - r3_40)
    r4_40.x = r4_40.x + r5_40 * r0_40.x
    r4_40.y = r4_40.y + r5_40 * r0_40.y
    r5_40 = 2 * r3_40 * (1 - r3_40)
    r4_40.x = r4_40.x + r5_40 * r1_40.x
    r4_40.y = r4_40.y + r5_40 * r1_40.y
    r5_40 = r3_40 * r3_40
    r4_40.x = r4_40.x + r5_40 * r2_40.x
    r4_40.y = r4_40.y + r5_40 * r2_40.y
    return r4_40
  end,
  IsBingoEnabled = function(r0_41, r1_41)
    -- line: [917, 928] id: 41
    if r5_0.GetLastRes() then
      if r0_41 ~= nil then
        r0_41(true)
      end
      return true
    end
    if r0_41 ~= nil then
      r0_41(false)
    end
    return false
  end,
  ReachableSwitchCustom = r51_0,
  ReachableSwitch = function(r0_44, r1_44)
    -- line: [945, 947] id: 44
    r51_0("www.google.com", r0_44, r1_44)
  end,
  Filelist = function(r0_45)
    -- line: [949, 961] id: 45
    r7_0("file list:")
    for r4_45 in lfs.dir(r0_45) do
      if r4_45 ~= "." and r4_45 ~= ".." then
        if lfs.attributes(r4_45, "mode") == "file" then
          r7_0("found file, " .. r4_45)
        elseif lfs.attributes(r4_45, "mode") == "directory" then
          r7_0("found dir, " .. r4_45, " containing:")
          util.filelist(r0_45 .. "/" .. r4_45)
        end
      end
    end
  end,
  Debug = function(r0_46)
    -- line: [963, 967] id: 46
    if _G.IsDebug then
      r19_0(r0_46)
    end
  end,
  ResDebug = function(r0_47)
    -- line: [969, 973] id: 47
    if _G.IsResDebug then
      r19_0(r0_47)
    end
  end,
  LoadLeftSideCharacterImage = function()
    -- line: [667, 687] id: 33
    local r0_33 = r9_0.newGroup()
    local r1_33 = nil
    r33_0(r0_33, "data/side/side_cornet.png", 0, 0)
    r1_33 = r39_0({
      rtImg = r0_33,
      size = 16,
      color = {
        255,
        255,
        255
      },
      shadow = {
        0,
        0,
        0
      },
      diff_x = 1,
      diff_y = 2,
      msg = "Version " .. _G.Version,
    })
    r1_33:setReferencePoint(r9_0.CenterReferencePoint)
    r1_33.x = 88
    r1_33.y = 620
    r0_33:setReferencePoint(r9_0.TopLeftReferencePoint)
    r0_33.x = _G.WidthDiff
    r0_33.y = 0
    return r0_33
  end,
  setActivityIndicator = function(r0_48)
    -- line: [975, 979] id: 48
    if not _G.IsAndroid then
      native.setActivityIndicator(r0_48)
    end
  end,
}
