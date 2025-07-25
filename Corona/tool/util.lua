local L0_1, L1_1, L2_1, L3_1, L4_1, L5_1, L6_1, L7_1, L8_1, L9_1, L10_1, L11_1, L12_1, L13_1, L14_1, L15_1, L16_1, L17_1, L18_1, L19_1, L20_1, L21_1, L22_1, L23_1, L24_1, L25_1, L26_1, L27_1, L28_1, L29_1, L30_1, L31_1, L32_1, L33_1, L34_1, L35_1, L36_1, L37_1, L38_1, L39_1, L40_1, L41_1, L42_1, L43_1, L44_1, L45_1, L46_1, L47_1, L48_1, L49_1, L50_1, L51_1, L52_1, L53_1, L54_1, L55_1, L56_1, L57_1
L0_1 = require
L1_1 = "ad.myads"
L0_1 = L0_1(L1_1)
L1_1 = require
L2_1 = "ad.adcropsWall"
L1_1 = L1_1(L2_1)
L2_1 = require
L3_1 = "ad.adcropsIcon"
L2_1 = L2_1(L3_1)
L3_1 = require
L4_1 = "ad.adfurikunIcon"
L3_1 = L3_1(L4_1)
L4_1 = require
L5_1 = "ad.adgenerationIcon"
L4_1 = L4_1(L5_1)
L5_1 = require
L6_1 = "server.bingoFlag"
L5_1 = L5_1(L6_1)
L6_1 = require
L7_1 = "json"
L6_1 = L6_1(L7_1)
L7_1 = print
L8_1 = string
L9_1 = display
L10_1 = tostring
L11_1 = type
L12_1 = pairs
L13_1 = table
L14_1 = tonumber
L15_1 = debug
L16_1 = assert

function L17_1(A0_2, A1_2)
  local L2_2, L3_2, L4_2, L5_2, L6_2, L7_2, L8_2, L9_2, L10_2, L11_2
  L2_2 = {}
  L3_2 = "(.-)"
  L4_2 = A1_2
  L3_2 = L3_2 .. L4_2
  L4_2 = 1
  L6_2 = A0_2
  L5_2 = A0_2.find
  L7_2 = L3_2
  L8_2 = 1
  L5_2, L6_2, L7_2 = L5_2(L6_2, L7_2, L8_2)
  while L5_2 do
    if L5_2 ~= 1 or L7_2 ~= "" then
      L8_2 = L13_1
      L8_2 = L8_2.insert
      L9_2 = L2_2
      L10_2 = L7_2
      L8_2(L9_2, L10_2)
    end
    L4_2 = L6_2 + 1
    L9_2 = A0_2
    L8_2 = A0_2.find
    L10_2 = L3_2
    L11_2 = L4_2
    L8_2, L9_2, L10_2 = L8_2(L9_2, L10_2, L11_2)
    L7_2 = L10_2
    L6_2 = L9_2
    L5_2 = L8_2
  end
  L8_2 = #A0_2
  if L4_2 <= L8_2 then
    L9_2 = A0_2
    L8_2 = A0_2.sub
    L10_2 = L4_2
    L8_2 = L8_2(L9_2, L10_2)
    L7_2 = L8_2
    L8_2 = L13_1
    L8_2 = L8_2.insert
    L9_2 = L2_2
    L10_2 = L7_2
    L8_2(L9_2, L10_2)
  end
  return L2_2
end

function L18_1(A0_2, A1_2)
  local L2_2, L3_2, L4_2, L5_2, L6_2, L7_2, L8_2, L9_2, L10_2, L11_2, L12_2
  L2_2 = false
  L3_2 = A0_2.disable
  if L3_2 then
    return L2_2
  end
  L3_2 = A1_2.phase
  L4_2 = A0_2
  L5_2 = L9_1
  L5_2 = L5_2.getCurrentStage
  L5_2 = L5_2()
  if L3_2 == "began" then
    L6_2 = A0_2.stageBounds
    A0_2.rect = L6_2
    L6_2 = A0_2.on_scale
    A0_2.xScale = L6_2
    L6_2 = A0_2.on_scale
    A0_2.yScale = L6_2
    if L5_2 then
      L7_2 = L5_2
      L6_2 = L5_2.setFocus
      L8_2 = A0_2
      L6_2(L7_2, L8_2)
    end
    A0_2.is_foucs = true
    L2_2 = true
  else
    L6_2 = A0_2.is_foucs
    if L6_2 then
      L6_2 = A1_2.x
      L7_2 = A1_2.y
      L8_2 = A0_2.rect
      L9_2 = L8_2.xMin
      L9_2 = L6_2 >= L9_2
      if L3_2 == "moved" and not L9_2 then
        L3_2 = "cancelled"
      end
      if L3_2 == "ended" or L3_2 == "cancelled" then
        A0_2.xScale = 1
        A0_2.yScale = 1
        A0_2.is_foucs = false
        if L5_2 then
          L11_2 = L5_2
          L10_2 = L5_2.setFocus
          L12_2 = nil
          L10_2(L11_2, L12_2)
        end
        if L3_2 == "ended" and L9_2 then
          L10_2 = A0_2.onRelease
          if L10_2 then
            L10_2 = A0_2.onRelease
            L11_2 = A0_2
            L10_2 = L10_2(L11_2)
            L2_2 = L10_2
          end
        end
      end
    end
  end
  return L2_2
end

function L19_1(A0_2, A1_2, A2_2)
  local L3_2, L4_2, L5_2, L6_2, L7_2, L8_2, L9_2, L10_2, L11_2, L12_2, L13_2, L14_2, L15_2
  L3_2 = L11_1
  L4_2 = A0_2
  L3_2 = L3_2(L4_2)
  if L3_2 ~= "table" then
    if not A0_2 then
      A0_2 = ""
    end
    L3_2 = L7_1
    L4_2 = "@@@@"
    L5_2 = A0_2
    L4_2 = L4_2 .. L5_2
    L3_2(L4_2)
    return
  end
  if not A2_2 then
    L3_2 = {}
    A2_2 = L3_2
  end
  if not A1_2 then
    A1_2 = ""
  end
  L3_2 = "@@@@"
  L4_2 = A1_2
  A1_2 = L3_2 .. L4_2
  L3_2 = nil
  L4_2 = L12_1
  L5_2 = A0_2
  L4_2, L5_2, L6_2 = L4_2(L5_2)
  for L7_2, L8_2 in L4_2, L5_2, L6_2 do
    L9_2 = L11_1
    L10_2 = L8_2
    L9_2 = L9_2(L10_2)
    if L9_2 == "table" then
      L9_2 = A2_2[L8_2]
      if not L9_2 then
        if not L3_2 then
          L9_2 = A1_2
          L10_2 = L8_1
          L10_2 = L10_2.rep
          L11_2 = " "
          L12_2 = L8_1
          L12_2 = L12_2.len
          L13_2 = L10_1
          L14_2 = L7_2
          L13_2, L14_2, L15_2 = L13_2(L14_2)
          L12_2 = L12_2(L13_2, L14_2, L15_2)
          L12_2 = L12_2 + 2
          L10_2 = L10_2(L11_2, L12_2)
          L3_2 = L9_2 .. L10_2
        end
        A2_2[L8_2] = true
        L9_2 = L7_1
        L10_2 = A1_2
        L11_2 = "["
        L12_2 = L10_1
        L13_2 = L7_2
        L12_2 = L12_2(L13_2)
        L13_2 = "] => Table {"
        L10_2 = L10_2 .. L11_2 .. L12_2 .. L13_2
        L9_2(L10_2)
        L9_2 = L7_1
        L10_2 = L3_2
        L11_2 = "{"
        L10_2 = L10_2 .. L11_2
        L9_2(L10_2)
        L9_2 = L19_1
        L10_2 = L8_2
        L11_2 = L3_2
        L12_2 = L8_1
        L12_2 = L12_2.rep
        L13_2 = " "
        L14_2 = 2
        L12_2 = L12_2(L13_2, L14_2)
        L11_2 = L11_2 .. L12_2
        L12_2 = A2_2
        L9_2(L10_2, L11_2, L12_2)
        L9_2 = L7_1
        L10_2 = L3_2
        L11_2 = "}"
        L10_2 = L10_2 .. L11_2
        L9_2(L10_2)
    end
    else
      L9_2 = L7_1
      L10_2 = A1_2
      L11_2 = "["
      L12_2 = L10_1
      L13_2 = L7_2
      L12_2 = L12_2(L13_2)
      L13_2 = "] => "
      L14_2 = L10_1
      L15_2 = L8_2
      L14_2 = L14_2(L15_2)
      L15_2 = ""
      L10_2 = L10_2 .. L11_2 .. L12_2 .. L13_2 .. L14_2 .. L15_2
      L9_2(L10_2)
    end
  end
end

function L20_1(A0_2, A1_2, A2_2)
  local L3_2, L4_2, L5_2, L6_2, L7_2, L8_2, L9_2, L10_2, L11_2, L12_2, L13_2
  if A2_2 == nil then
    A2_2 = ""
  end
  L3_2 = L11_1
  L4_2 = A0_2
  L3_2 = L3_2(L4_2)
  if L3_2 ~= "table" then
    L3_2 = A2_2
    L4_2 = "<"
    L5_2 = L10_1
    L6_2 = A0_2
    L5_2 = L5_2(L6_2)
    L6_2 = ">"
    L3_2 = L3_2 .. L4_2 .. L5_2 .. L6_2
    return L3_2
  end
  if not A1_2 then
    L3_2 = {}
    A1_2 = L3_2
  end
  L3_2 = L12_1
  L4_2 = A0_2
  L3_2, L4_2, L5_2 = L3_2(L4_2)
  for L6_2, L7_2 in L3_2, L4_2, L5_2 do
    L8_2 = L11_1
    L9_2 = L7_2
    L8_2 = L8_2(L9_2)
    if L8_2 == "table" then
      L8_2 = A1_2[L7_2]
      if not L8_2 then
        A1_2[L7_2] = true
        L8_2 = A2_2
        L9_2 = "{"
        A2_2 = L8_2 .. L9_2
        L8_2 = A2_2
        L9_2 = L20_1
        L10_2 = L7_2
        L11_2 = A1_2
        L12_2 = A2_2
        L9_2 = L9_2(L10_2, L11_2, L12_2)
        A2_2 = L8_2 .. L9_2
        L8_2 = A2_2
        L9_2 = "}"
        A2_2 = L8_2 .. L9_2
    end
    else
      L8_2 = A2_2
      L9_2 = "["
      L10_2 = L10_1
      L11_2 = L6_2
      L10_2 = L10_2(L11_2)
      L11_2 = "=>"
      L12_2 = L10_1
      L13_2 = L7_2
      L12_2 = L12_2(L13_2)
      L13_2 = "]"
      A2_2 = L8_2 .. L9_2 .. L10_2 .. L11_2 .. L12_2 .. L13_2
    end
  end
  return A2_2
end

function L21_1(A0_2, A1_2)
  local L2_2, L3_2, L4_2, L5_2, L6_2, L7_2, L8_2

  if A0_2 ~= nil then
    L2_2 = L11_1
    L3_2 = A0_2
    L2_2 = L2_2(L3_2)
    if L2_2 == "table" and A1_2 ~= nil then
      -- Proceed
    else
      L2_2 = false
      return L2_2
    end
  else
    L2_2 = false
    return L2_2
  end

  L2_2 = A0_2[A1_2]
  if L2_2 ~= nil then
    L2_2 = true
    return L2_2
  end

  L2_2 = L12_1
  L3_2 = A0_2
  L2_2, L3_2, L4_2 = L2_2(L3_2)

  for L5_2, L6_2 in L2_2, L3_2, L4_2 do
    L7_2 = L11_1
    L8_2 = L6_2
    L7_2 = L7_2(L8_2)

    if L7_2 == "number" or L7_2 == "string" or L7_2 == "boolean" then
      if A1_2 == L6_2 then
        L7_2 = true
        return L7_2
      end
    elseif L7_2 == "function" then
      local ok, result = pcall(L6_2)
      if ok and A1_2 == result then
        L7_2 = true
        return L7_2
      end
    end
  end

  L2_2 = false
  return L2_2
end

function L22_1(A0_2)
  local L1_2, L2_2, L3_2, L4_2, L5_2
  L1_2 = false
  L2_2 = system
  L2_2 = L2_2.pathForFile
  L3_2 = A0_2
  L4_2 = system
  L4_2 = L4_2.ResourceDirectory
  L2_2 = L2_2(L3_2, L4_2)
  if L2_2 == nil then
    return L1_2
  end
  L3_2 = io
  L3_2 = L3_2.open
  L4_2 = L2_2
  L5_2 = "r"
  L3_2 = L3_2(L4_2, L5_2)
  if L3_2 then
    L4_2 = io
    L4_2 = L4_2.close
    L5_2 = L3_2
    L4_2(L5_2)
    L1_2 = true
  end
  return L1_2
end

function L23_1(A0_2)
  local L1_2, L2_2, L3_2, L4_2
  if not A0_2 then
    A0_2 = ""
  end
  L1_2 = L8_1
  L1_2 = L1_2.gsub
  L2_2 = A0_2
  L3_2 = "(.*/)(.*)"
  L4_2 = "%1"
  L1_2 = L1_2(L2_2, L3_2, L4_2)
  return L1_2
end

function L24_1(A0_2)
  local L1_2, L2_2, L3_2, L4_2, L5_2, L6_2, L7_2, L8_2, L9_2
  L1_2 = ""
  L2_2 = ipairs
  L3_2 = L17_1
  L4_2 = A0_2
  L5_2 = "/"
  L3_2, L4_2, L5_2, L6_2, L7_2, L8_2, L9_2 = L3_2(L4_2, L5_2)
  L2_2, L3_2, L4_2 = L2_2(L3_2, L4_2, L5_2, L6_2, L7_2, L8_2, L9_2)
  for L5_2, L6_2 in L2_2, L3_2, L4_2 do
    L7_2 = L1_2
    L8_2 = "/"
    L9_2 = L6_2
    L1_2 = L7_2 .. L8_2 .. L9_2
    L7_2 = lfs
    L7_2 = L7_2.chdir
    L8_2 = L1_2
    L7_2 = L7_2(L8_2)
    if not L7_2 then
      L7_2 = lfs
      L7_2 = L7_2.mkdir
      L8_2 = L1_2
      L7_2(L8_2)
    end
  end
end

function L25_1(A0_2)
  local L1_2, L2_2, L3_2, L4_2
  L1_2 = L9_1
  L1_2 = L1_2.newGroup
  L1_2 = L1_2()
  L3_2 = A0_2
  L2_2 = A0_2.insert
  L4_2 = L1_2
  L2_2(L3_2, L4_2)
  return L1_2
end

function L26_1(A0_2)
  local L1_2, L2_2, L3_2, L4_2, L5_2, L6_2, L7_2
  L1_2 = A0_2.group
  if not L1_2 then
    return nil
  end

  L2_2 = A0_2.cx
  L3_2 = A0_2.cy

  if L2_2 and L3_2 then
    L1_2.x = L2_2
    L1_2.y = L3_2
  else
    L4_2 = L1_2.width or 0
    L5_2 = L1_2.height or 0
    L6_2 = (A0_2.x or 0) + L4_2 * 0.5
    L1_2.x = L6_2
    L6_2 = (A0_2.y or 0) + L5_2 * 0.5
    L1_2.y = L6_2
  end

  L4_2 = A0_2.func
  L1_2.onRelease = L4_2

  L4_2 = A0_2.param
  L1_2.param = L4_2

  L1_2.is_foucs = false

  -- Only set scale if explicitly provided
  if A0_2.scale ~= nil then
    L1_2.on_scale = A0_2.scale
  end

  L4_2 = L18_1
  L1_2.touch = L4_2

  -- Handle visibility fallback to true
  if A0_2.show == nil then
    L1_2.isVisible = true
  else
    L1_2.isVisible = A0_2.show
  end

  -- Optional disable field
  L1_2.disable = A0_2.disable

  -- Add touch listener
  L5_2 = L1_2
  L4_2 = L1_2.addEventListener
  L6_2 = "touch"
  L7_2 = L1_2
  L4_2(L5_2, L6_2, L7_2)

  return L1_2
end


function L27_1(A0_2)
  local L1_2, L2_2, L3_2, L4_2, L5_2, L6_2
  L1_2 = L16_1
  L2_2 = A0_2.rtImg
  L3_2 = L15_1
  L3_2 = L3_2.traceback
  L3_2, L4_2, L5_2, L6_2 = L3_2()
  L1_2(L2_2, L3_2, L4_2, L5_2, L6_2)
  L1_2 = cdn
  L1_2 = L1_2.NewImage
  L2_2 = A0_2.rtImg
  L3_2 = A0_2.fname
  L4_2 = 0
  L5_2 = 0
  L6_2 = true
  L1_2 = L1_2(L2_2, L3_2, L4_2, L5_2, L6_2)
  A0_2.group = L1_2
  L1_2 = L16_1
  L2_2 = A0_2.group
  L3_2 = L8_1
  L3_2 = L3_2.format
  L4_2 = "not found %s"
  L5_2 = A0_2.fname
  L3_2, L4_2, L5_2, L6_2 = L3_2(L4_2, L5_2)
  L1_2(L2_2, L3_2, L4_2, L5_2, L6_2)
  L1_2 = L26_1
  L2_2 = A0_2
  return L1_2(L2_2)
end

function L28_1(A0_2, A1_2, A2_2, A3_2, A4_2, A5_2)
  local L6_2, L7_2, L8_2, L9_2

  A0_2.onRelease = A1_2
  A0_2.param = A2_2
  A0_2.is_foucs = false

  -- Only assign on_scale if A3_2 is not nil
  if A3_2 ~= nil then
    A0_2.on_scale = A3_2
  end

  -- Handle visibility fallback to true
  if A4_2 == nil then
    L6_2 = true
  else
    L6_2 = A4_2
  end
  A0_2.isVisible = L6_2

  -- Assign disable flag (could be nil or boolean)
  A0_2.disable = A5_2

  -- Set touch handler and listener
  L6_2 = L18_1
  A0_2.touch = L6_2

  L7_2 = A0_2
  L6_2 = A0_2.addEventListener
  L8_2 = "touch"
  L9_2 = A0_2
  L6_2(L7_2, L8_2, L9_2)
end


function L29_1(A0_2, A1_2)
  local L2_2, L3_2, L4_2, L5_2, L6_2, L7_2
  L2_2 = cdn
  L2_2 = L2_2.NewImage
  L3_2 = A0_2
  L4_2 = A1_2
  L5_2 = nil
  L6_2 = nil
  L7_2 = true
  L2_2 = L2_2(L3_2, L4_2, L5_2, L6_2, L7_2)
  return L2_2
end

function L30_1(A0_2, A1_2, A2_2)
  local L3_2, L4_2, L5_2, L6_2, L7_2, L8_2, L9_2, L10_2, L11_2, L12_2, L13_2, L14_2, L15_2, L16_2, L17_2, L18_2, L19_2, L20_2
  L3_2 = L16_1
  L4_2 = A1_2
  L5_2 = L15_1
  L5_2 = L5_2.traceback
  L5_2, L6_2, L7_2, L8_2, L9_2, L10_2, L11_2, L12_2, L13_2, L14_2, L15_2, L16_2, L17_2, L18_2, L19_2, L20_2 = L5_2()
  L3_2(L4_2, L5_2, L6_2, L7_2, L8_2, L9_2, L10_2, L11_2, L12_2, L13_2, L14_2, L15_2, L16_2, L17_2, L18_2, L19_2, L20_2)
  L3_2 = nil
  if A2_2 then
    L4_2 = A2_2
    L5_2 = "/"
    L3_2 = L4_2 .. L5_2
  else
    L3_2 = ""
  end
  L4_2 = L16_1
  L5_2 = A1_2[1]
  L6_2 = L15_1
  L6_2 = L6_2.traceback
  L6_2, L7_2, L8_2, L9_2, L10_2, L11_2, L12_2, L13_2, L14_2, L15_2, L16_2, L17_2, L18_2, L19_2, L20_2 = L6_2()
  L4_2(L5_2, L6_2, L7_2, L8_2, L9_2, L10_2, L11_2, L12_2, L13_2, L14_2, L15_2, L16_2, L17_2, L18_2, L19_2, L20_2)
  L4_2 = L3_2
  L5_2 = A1_2[1]
  L3_2 = L4_2 .. L5_2
  L4_2 = L9_1
  L4_2 = L4_2.newGroup
  L4_2 = L4_2()
  L5_2 = A1_2[2]
  L4_2.width = L5_2
  L5_2 = A1_2[3]
  L4_2.height = L5_2
  L5_2 = A1_2[4]
  L6_2 = A1_2[5]
  L6_2 = L6_2 - 1
  L7_2 = A1_2[6]
  L8_2 = nil
  L9_2 = nil
  L10_2 = L12_1
  L11_2 = L7_2
  L10_2, L11_2, L12_2 = L10_2(L11_2)
  for L13_2, L14_2 in L10_2, L11_2, L12_2 do
    L15_2 = L8_1
    L15_2 = L15_2.format
    L16_2 = "%s/%08d.%s"
    L17_2 = L3_2
    L18_2 = L13_2 - 1
    L19_2 = L5_2
    L15_2 = L15_2(L16_2, L17_2, L18_2, L19_2)
    L8_2 = L15_2
    L15_2 = cdn
    L15_2 = L15_2.NewImage
    L16_2 = L4_2
    L17_2 = L8_2
    L18_2 = nil
    L19_2 = nil
    L20_2 = nil
    L15_2 = L15_2(L16_2, L17_2, L18_2, L19_2, L20_2)
    L9_2 = L15_2
    L16_2 = L9_2
    L15_2 = L9_2.setReferencePoint
    L17_2 = L9_1
    L17_2 = L17_2.TopLeftReferencePoint
    L15_2(L16_2, L17_2)
    L15_2 = L14_2[1]
    L9_2.x = L15_2
    L15_2 = L14_2[2]
    L9_2.y = L15_2
  end
  if A0_2 then
    L11_2 = A0_2
    L10_2 = A0_2.insert
    L12_2 = L4_2
    L10_2(L11_2, L12_2)
  end
  return L4_2
end

function L31_1(A0_2, A1_2, A2_2)
  local L3_2, L4_2, L5_2, L6_2
  L3_2 = L16_1
  L4_2 = A1_2
  L5_2 = L15_1
  L5_2 = L5_2.traceback
  L5_2, L6_2 = L5_2()
  L3_2(L4_2, L5_2, L6_2)
  L3_2 = L30_1
  L4_2 = A0_2
  L5_2 = A1_2
  L6_2 = A2_2
  L3_2 = L3_2(L4_2, L5_2, L6_2)
  L5_2 = L3_2
  L4_2 = L3_2.setReferencePoint
  L6_2 = L9_1
  L6_2 = L6_2.CenterReferencePoint
  L4_2(L5_2, L6_2)
  L4_2 = _G
  L4_2 = L4_2.Width
  L4_2 = L4_2 / 2
  L3_2.x = L4_2
  L4_2 = _G
  L4_2 = L4_2.Height
  L4_2 = L4_2 / 2
  L3_2.y = L4_2
  return L3_2
end

function L32_1(A0_2, A1_2, A2_2, A3_2, A4_2)
  local L5_2, L6_2, L7_2, L8_2
  L5_2 = L16_1
  L6_2 = A3_2
  L7_2 = L15_1
  L7_2 = L7_2.traceback
  L7_2, L8_2 = L7_2()
  L5_2(L6_2, L7_2, L8_2)
  L5_2 = L30_1
  L6_2 = A0_2
  L7_2 = A3_2
  L8_2 = A4_2
  L5_2 = L5_2(L6_2, L7_2, L8_2)
  L7_2 = L5_2
  L6_2 = L5_2.setReferencePoint
  L8_2 = L9_1
  L8_2 = L8_2.TopLeftReferencePoint
  L6_2(L7_2, L8_2)
  L5_2.x = A1_2
  L5_2.y = A2_2
  return L5_2
end

function L33_1(A0_2, A1_2, A2_2, A3_2)
  local L4_2, L5_2, L6_2, L7_2, L8_2, L9_2
  L4_2 = cdn
  L4_2 = L4_2.NewImage
  L5_2 = A0_2
  L6_2 = A1_2
  L7_2 = nil
  L8_2 = nil
  L9_2 = true
  L4_2 = L4_2(L5_2, L6_2, L7_2, L8_2, L9_2)
  if L4_2 == nil then
    L5_2 = DebugPrint
    L6_2 = "image load err:"
    L7_2 = A1_2
    L6_2 = L6_2 .. L7_2
    L5_2(L6_2)
    L5_2 = nil
    return L5_2
  end
  L5_2 = L16_1
  L6_2 = L4_2
  L7_2 = L15_1
  L7_2 = L7_2.traceback
  L7_2, L8_2, L9_2 = L7_2()
  L5_2(L6_2, L7_2, L8_2, L9_2)
  L6_2 = L4_2
  L5_2 = L4_2.setReferencePoint
  L7_2 = L9_1
  L7_2 = L7_2.TopLeftReferencePoint
  L5_2(L6_2, L7_2)
  L4_2.x = A2_2
  L4_2.y = A3_2
  return L4_2
end

function L34_1(A0_2, A1_2, A2_2, A3_2)
  local L4_2, L5_2, L6_2, L7_2
  L4_2 = L9_1
  L4_2 = L4_2.newImage
  L5_2 = A0_2
  L6_2 = A1_2
  L7_2 = true
  L4_2 = L4_2(L5_2, L6_2, L7_2)
  L5_2 = L16_1
  L6_2 = L4_2
  L7_2 = L15_1
  L7_2 = L7_2.traceback
  L7_2 = L7_2()
  L5_2(L6_2, L7_2)
  L4_2.x = A2_2
  L4_2.y = A3_2
  return L4_2
end

function L35_1(A0_2, A1_2)
  local L2_2, L3_2, L4_2, L5_2, L6_2, L7_2, L8_2
  L3_2 = L9_1
  L3_2 = L3_2.newRect
  L4_2 = A0_2
  L5_2 = -200
  L6_2 = -200
  L7_2 = _G
  L7_2 = L7_2.Width
  L7_2 = L7_2 + 400
  L8_2 = _G
  L8_2 = L8_2.Height
  L8_2 = L8_2 + 400
  L3_2 = L3_2(L4_2, L5_2, L6_2, L7_2, L8_2)
  L2_2 = L3_2
  L4_2 = L2_2
  L3_2 = L2_2.setFillColor
  L5_2 = 0
  L6_2 = 0
  L7_2 = 0
  L3_2(L4_2, L5_2, L6_2, L7_2)
  L2_2.alpha = 0.01
  if A1_2 then
    L2_2.touch = A1_2
  else
    function L3_2(A0_3, A1_3)
      local L2_3
      
      L2_3 = true
      return L2_3
    end
    
    L2_2.touch = L3_2
  end
  L4_2 = L2_2
  L3_2 = L2_2.addEventListener
  L5_2 = "touch"
  L6_2 = L2_2
  L3_2(L4_2, L5_2, L6_2)
  return L2_2
end

function L36_1(A0_2)
  local L1_2, L2_2, L3_2, L4_2, L5_2, L6_2, L7_2, L8_2, L9_2
  L1_2 = {}
  L2_2 = nil
  while 0 < A0_2 do
    L2_2 = A0_2 % 1000
    L3_2 = A0_2 - L2_2
    A0_2 = L3_2 / 1000
    if 0 < A0_2 then
      L3_2 = L13_1
      L3_2 = L3_2.insert
      L4_2 = L1_2
      L5_2 = L8_1
      L5_2 = L5_2.format
      L6_2 = "%03d"
      L7_2 = L2_2
      L5_2, L6_2, L7_2, L8_2, L9_2 = L5_2(L6_2, L7_2)
      L3_2(L4_2, L5_2, L6_2, L7_2, L8_2, L9_2)
      L3_2 = L13_1
      L3_2 = L3_2.insert
      L4_2 = L1_2
      L5_2 = ","
      L3_2(L4_2, L5_2)
    else
      L3_2 = L13_1
      L3_2 = L3_2.insert
      L4_2 = L1_2
      L5_2 = L10_1
      L6_2 = L2_2
      L5_2, L6_2, L7_2, L8_2, L9_2 = L5_2(L6_2)
      L3_2(L4_2, L5_2, L6_2, L7_2, L8_2, L9_2)
    end
  end
  L3_2 = L13_1
  L3_2 = L3_2.maxn
  L4_2 = L1_2
  L3_2 = L3_2(L4_2)
  L2_2 = L3_2
  if L2_2 == 0 then
    L3_2 = L13_1
    L3_2 = L3_2.insert
    L4_2 = L1_2
    L5_2 = "0"
    L3_2(L4_2, L5_2)
    L2_2 = 1
  end
  L3_2 = ""
  L4_2 = L2_2
  L5_2 = 1
  L6_2 = -1
  for L7_2 = L4_2, L5_2, L6_2 do
    L8_2 = L3_2
    L9_2 = L1_2[L7_2]
    L3_2 = L8_2 .. L9_2
  end
  return L3_2
end

function L37_1(A0_2, A1_2, A2_2, A3_2, A4_2, A5_2)
  local L6_2, L7_2, L8_2, L9_2, L10_2, L11_2, L12_2, L13_2, L14_2, L15_2
  L6_2 = A4_2 - A2_2
  L7_2 = A5_2 - A3_2
  if L6_2 == 0 and L7_2 == 0 then
    L8_2 = A2_2 - A0_2
    L9_2 = A2_2 - A0_2
    L8_2 = L8_2 * L9_2
    L9_2 = A3_2 - A1_2
    L10_2 = A3_2 - A1_2
    L9_2 = L9_2 * L10_2
    L8_2 = L8_2 + L9_2
    return L8_2
  end
  L8_2 = L6_2 * L6_2
  L9_2 = L7_2 * L7_2
  L8_2 = L8_2 + L9_2
  L9_2 = A2_2 - A0_2
  L9_2 = L6_2 * L9_2
  L10_2 = A3_2 - A1_2
  L10_2 = L7_2 * L10_2
  L9_2 = L9_2 + L10_2
  L10_2 = -L9_2
  L10_2 = L10_2 / L8_2
  if L10_2 < 0 then
    L10_2 = 0
  end
  if 1 < L10_2 then
    L10_2 = 1
  end
  L11_2 = L6_2 * L10_2
  L11_2 = A2_2 + L11_2
  L12_2 = L7_2 * L10_2
  L12_2 = A3_2 + L12_2
  L13_2 = A0_2 - L11_2
  L14_2 = A0_2 - L11_2
  L13_2 = L13_2 * L14_2
  L14_2 = A1_2 - L12_2
  L15_2 = A1_2 - L12_2
  L14_2 = L14_2 * L15_2
  L13_2 = L13_2 + L14_2
  return L13_2
end

function L38_1(A0_2, A1_2)
  local L2_2, L3_2, L4_2, L5_2, L6_2, L7_2, L8_2
  L2_2 = true
  L3_2 = io
  L3_2 = L3_2.open
  L4_2 = A0_2
  L5_2 = "rb"
  L3_2 = L3_2(L4_2, L5_2)
  if L3_2 == nil then
    L4_2 = false
    return L4_2
  end
  L4_2 = io
  L4_2 = L4_2.open
  L5_2 = A1_2
  L6_2 = "wb"
  L4_2 = L4_2(L5_2, L6_2)
  if L4_2 == nil then
    L6_2 = L3_2
    L5_2 = L3_2.close
    L5_2(L6_2)
    L5_2 = false
    return L5_2
  end
  if not L4_2 then
    L5_2 = L7_1
    L6_2 = "filebackup error"
    L5_2(L6_2)
    L2_2 = false
  else
    L6_2 = L3_2
    L5_2 = L3_2.read
    L7_2 = "*a"
    L5_2 = L5_2(L6_2, L7_2)
    if not L5_2 then
      L6_2 = L7_1
      L7_2 = "read error!"
      L6_2(L7_2)
      L2_2 = false
    else
      L7_2 = L4_2
      L6_2 = L4_2.write
      L8_2 = L5_2
      L6_2 = L6_2(L7_2, L8_2)
      if not L6_2 then
        L6_2 = L7_1
        L7_2 = "write error!"
        L6_2(L7_2)
        L2_2 = false
      end
    end
  end
  L6_2 = L3_2
  L5_2 = L3_2.close
  L5_2(L6_2)
  L6_2 = L4_2
  L5_2 = L4_2.close
  L5_2(L6_2)
  return L2_2
end

function L39_1(A0_2)
  local L1_2, L2_2, L3_2, L4_2, L5_2, L6_2, L7_2, L8_2, L9_2, L10_2, L11_2, L12_2, L13_2, L14_2, L15_2, L16_2, L17_2, L18_2, L19_2, L20_2
  L1_2 = L16_1
  L2_2 = A0_2 or L2_2
  if A0_2 then
    L2_2 = L11_1
    L3_2 = A0_2
    L2_2 = L2_2(L3_2)
    L2_2 = L2_2 == "table"
  end
  L3_2 = L15_1
  L3_2 = L3_2.traceback
  L3_2, L4_2, L5_2, L6_2, L7_2, L8_2, L9_2, L10_2, L11_2, L12_2, L13_2, L14_2, L15_2, L16_2, L17_2, L18_2, L19_2, L20_2 = L3_2()
  L1_2(L2_2, L3_2, L4_2, L5_2, L6_2, L7_2, L8_2, L9_2, L10_2, L11_2, L12_2, L13_2, L14_2, L15_2, L16_2, L17_2, L18_2, L19_2, L20_2)
  L1_2 = A0_2.size
  L2_2 = A0_2.color
  L3_2 = A0_2.shadow
  L4_2 = A0_2.msg
  L5_2 = A0_2.width
  L6_2 = A0_2.height
  L7_2 = A0_2.diff_x
  L8_2 = A0_2.diff_y
  if L7_2 == nil then
    L7_2 = 1
  end
  if L8_2 == nil then
    L8_2 = 1
  end
  L9_2 = L16_1
  L10_2 = L1_2
  L11_2 = L15_1
  L11_2 = L11_2.traceback
  L11_2, L12_2, L13_2, L14_2, L15_2, L16_2, L17_2, L18_2, L19_2, L20_2 = L11_2()
  L9_2(L10_2, L11_2, L12_2, L13_2, L14_2, L15_2, L16_2, L17_2, L18_2, L19_2, L20_2)
  L9_2 = L16_1
  L10_2 = L2_2
  L11_2 = L15_1
  L11_2 = L11_2.traceback
  L11_2, L12_2, L13_2, L14_2, L15_2, L16_2, L17_2, L18_2, L19_2, L20_2 = L11_2()
  L9_2(L10_2, L11_2, L12_2, L13_2, L14_2, L15_2, L16_2, L17_2, L18_2, L19_2, L20_2)
  L9_2 = L16_1
  L10_2 = L4_2
  L11_2 = L15_1
  L11_2 = L11_2.traceback
  L11_2, L12_2, L13_2, L14_2, L15_2, L16_2, L17_2, L18_2, L19_2, L20_2 = L11_2()
  L9_2(L10_2, L11_2, L12_2, L13_2, L14_2, L15_2, L16_2, L17_2, L18_2, L19_2, L20_2)
  L9_2 = nil
  L10_2 = A0_2.font
  if L10_2 then
    L9_2 = A0_2.font
  else
    L10_2 = native
    L9_2 = L10_2.systemFontBold
  end
  L10_2 = L9_1
  L10_2 = L10_2.newGroup
  L10_2 = L10_2()
  L11_2 = nil
  if L5_2 ~= nil and L6_2 ~= nil then
    if L3_2 then
      L12_2 = L9_1
      L12_2 = L12_2.newText
      L13_2 = L10_2
      L14_2 = L4_2
      L15_2 = L7_2
      L16_2 = L8_2
      L17_2 = L5_2
      L18_2 = L6_2
      L19_2 = L9_2
      L20_2 = L1_2
      L12_2 = L12_2(L13_2, L14_2, L15_2, L16_2, L17_2, L18_2, L19_2, L20_2)
      L11_2 = L12_2
      L13_2 = L11_2
      L12_2 = L11_2.setFillColor
      L14_2 = L3_2[1]
      L15_2 = L3_2[2]
      L16_2 = L3_2[3]
      L12_2(L13_2, L14_2, L15_2, L16_2)
    end
    L12_2 = L9_1
    L12_2 = L12_2.newText
    L13_2 = L10_2
    L14_2 = L4_2
    L15_2 = 0
    L16_2 = 0
    L17_2 = L5_2
    L18_2 = L6_2
    L19_2 = L9_2
    L20_2 = L1_2
    L12_2 = L12_2(L13_2, L14_2, L15_2, L16_2, L17_2, L18_2, L19_2, L20_2)
    L11_2 = L12_2
    L13_2 = L11_2
    L12_2 = L11_2.setFillColor
    L14_2 = L2_2[1]
    L15_2 = L2_2[2]
    L16_2 = L2_2[3]
    L12_2(L13_2, L14_2, L15_2, L16_2)
  else
    if L3_2 then
      L12_2 = L9_1
      L12_2 = L12_2.newText
      L13_2 = L10_2
      L14_2 = L4_2
      L15_2 = L7_2
      L16_2 = L8_2
      L17_2 = L9_2
      L18_2 = L1_2
      L12_2 = L12_2(L13_2, L14_2, L15_2, L16_2, L17_2, L18_2)
      L11_2 = L12_2
      L13_2 = L11_2
      L12_2 = L11_2.setFillColor
      L14_2 = L3_2[1]
      L15_2 = L3_2[2]
      L16_2 = L3_2[3]
      L12_2(L13_2, L14_2, L15_2, L16_2)
    end
    L12_2 = L9_1
    L12_2 = L12_2.newText
    L13_2 = L10_2
    L14_2 = L4_2
    L15_2 = 0
    L16_2 = 0
    L17_2 = L9_2
    L18_2 = L1_2
    L12_2 = L12_2(L13_2, L14_2, L15_2, L16_2, L17_2, L18_2)
    L11_2 = L12_2
    L13_2 = L11_2
    L12_2 = L11_2.setFillColor
    L14_2 = L2_2[1]
    L15_2 = L2_2[2]
    L16_2 = L2_2[3]
    L12_2(L13_2, L14_2, L15_2, L16_2)
  end
  L12_2 = A0_2.rtImg
  if L12_2 then
    L14_2 = L12_2
    L13_2 = L12_2.insert
    L15_2 = L10_2
    L13_2(L14_2, L15_2)
  end
  L13_2 = A0_2.x
  L14_2 = A0_2.y
  if L13_2 and L14_2 then
    L16_2 = L10_2
    L15_2 = L10_2.setReferencePoint
    L17_2 = L9_1
    L17_2 = L17_2.TopLeftReferencePoint
    L15_2(L16_2, L17_2)
    L10_2.x = L13_2
    L10_2.y = L14_2
  end
  return L10_2
end

function L40_1(A0_2)
  local L1_2, L2_2, L3_2, L4_2, L5_2, L6_2, L7_2, L8_2, L9_2, L10_2, L11_2, L12_2, L13_2, L14_2, L15_2, L16_2, L17_2, L18_2
  L1_2 = A0_2.msg
  L2_2 = L8_1
  L2_2 = L2_2.gsub
  L3_2 = L1_2
  L4_2 = "(\\n)"
  
  function L5_2(A0_3)
    local L1_3
    L1_3 = "\n"
    return L1_3
  end
  
  L2_2 = L2_2(L3_2, L4_2, L5_2)
  L1_2 = L2_2
  L2_2 = L17_1
  L3_2 = L1_2
  L4_2 = "\n"
  L2_2 = L2_2(L3_2, L4_2)
  L1_2 = L2_2
  L2_2 = A0_2.w
  L3_2 = A0_2.h
  L4_2 = A0_2.color
  L5_2 = A0_2.shadow
  L6_2 = A0_2.size
  L7_2 = A0_2.line
  
  function L8_2(A0_3, A1_3)
    local L2_3, L3_3, L4_3, L5_3, L6_3, L7_3, L8_3, L9_3, L10_3, L11_3, L12_3, L13_3, L14_3, L15_3, L16_3, L17_3, L18_3, L19_3
    L2_3 = 0
    L3_3 = nil
    L4_3 = L12_1
    L5_3 = L1_2
    L4_3, L5_3, L6_3 = L4_3(L5_3)
    for L7_3, L8_3 in L4_3, L5_3, L6_3 do
      L9_3 = 1
      L10_3 = A1_3
      L11_3 = 1
      for L12_3 = L9_3, L10_3, L11_3 do
        L13_3 = L9_1
        L13_3 = L13_3.newText
        L14_3 = A0_3
        L15_3 = L8_3
        L16_3 = 0
        L17_3 = 0
        L18_3 = A0_2
        L18_3 = L18_3.font
        L19_3 = A0_2
        L19_3 = L19_3.size
        L13_3 = L13_3(L14_3, L15_3, L16_3, L17_3, L18_3, L19_3)
        L3_3 = L13_3
        L13_3 = A0_2
        L13_3 = L13_3.align
        if L13_3 == "left" then
          L14_3 = L3_3
          L13_3 = L3_3.setReferencePoint
          L15_3 = L9_1
          L15_3 = L15_3.TopLeftReferencePoint
          L13_3(L14_3, L15_3)
          L3_3.x = 0
          L3_3.y = L2_3
        else
          L14_3 = L3_3
          L13_3 = L3_3.setReferencePoint
          L15_3 = L9_1
          L15_3 = L15_3.CenterReferencePoint
          L13_3(L14_3, L15_3)
          L13_3 = L2_2
          L13_3 = L13_3 / 2
          L3_3.x = L13_3
          L13_3 = L7_2
          L13_3 = L13_3 / 2
          L13_3 = L2_3 + L13_3
          L3_3.y = L13_3
        end
      end
      L9_3 = L7_2
      L2_3 = L2_3 + L9_3
    end
  end
  
  function L9_2(A0_3, A1_3)
    local L2_3, L3_3, L4_3, L5_3, L6_3, L7_3, L8_3, L9_3, L10_3, L11_3
    L2_3 = 1
    L3_3 = A0_3.numChildren
    L4_3 = 1
    for L5_3 = L2_3, L3_3, L4_3 do
      L6_3 = A0_3[L5_3]
      L8_3 = L6_3
      L7_3 = L6_3.setFillColor
      L9_3 = A1_3[1]
      L10_3 = A1_3[2]
      L11_3 = A1_3[3]
      L7_3(L8_3, L9_3, L10_3, L11_3)
    end
  end
  
  L10_2 = L9_1
  L10_2 = L10_2.newGroup
  L10_2 = L10_2()
  L11_2 = A0_2.rtImg
  L12_2 = L11_2
  L11_2 = L11_2.insert
  L13_2 = L10_2
  L11_2(L12_2, L13_2)
  L11_2 = A0_2.shadow
  if L11_2 ~= nil then
    L11_2 = L11_1
    L12_2 = A0_2.shadow
    L11_2 = L11_2(L12_2)
    if L11_2 == "table" then
      L11_2 = L9_1
      L11_2 = L11_2.newGroup
      L11_2 = L11_2()
      L13_2 = L10_2
      L12_2 = L10_2.insert
      L14_2 = L11_2
      L12_2(L13_2, L14_2)
      L12_2 = L8_2
      L13_2 = L11_2
      L14_2 = 8
      L12_2(L13_2, L14_2)
      L12_2 = L9_2
      L13_2 = L11_2
      L14_2 = A0_2.shadow
      L12_2(L13_2, L14_2)
      L12_2 = 1
      L13_2 = L11_2.numChildren
      L14_2 = 8
      for L15_2 = L12_2, L13_2, L14_2 do
        L16_2 = L11_2[L15_2]
        L17_2 = L11_2[L15_2]
        L17_2 = L17_2.x
        L18_2 = A0_2.shadowBoldWidth
        L17_2 = L17_2 - L18_2
        L16_2.x = L17_2
        L16_2 = L11_2[L15_2]
        L17_2 = L11_2[L15_2]
        L17_2 = L17_2.y
        L18_2 = A0_2.shadowBoldWidth
        L17_2 = L17_2 - L18_2
        L16_2.y = L17_2
        L16_2 = L15_2 + 1
        L16_2 = L11_2[L16_2]
        L17_2 = L15_2 + 1
        L17_2 = L11_2[L17_2]
        L17_2 = L17_2.x
        L18_2 = A0_2.shadowBoldWidth
        L17_2 = L17_2 + L18_2
        L16_2.x = L17_2
        L16_2 = L15_2 + 1
        L16_2 = L11_2[L16_2]
        L17_2 = L15_2 + 1
        L17_2 = L11_2[L17_2]
        L17_2 = L17_2.y
        L18_2 = A0_2.shadowBoldWidth
        L17_2 = L17_2 + L18_2
        L16_2.y = L17_2
        L16_2 = L15_2 + 2
        L16_2 = L11_2[L16_2]
        L17_2 = L15_2 + 2
        L17_2 = L11_2[L17_2]
        L17_2 = L17_2.x
        L18_2 = A0_2.shadowBoldWidth
        L17_2 = L17_2 + L18_2
        L16_2.x = L17_2
        L16_2 = L15_2 + 2
        L16_2 = L11_2[L16_2]
        L17_2 = L15_2 + 2
        L17_2 = L11_2[L17_2]
        L17_2 = L17_2.y
        L18_2 = A0_2.shadowBoldWidth
        L17_2 = L17_2 - L18_2
        L16_2.y = L17_2
        L16_2 = L15_2 + 3
        L16_2 = L11_2[L16_2]
        L17_2 = L15_2 + 3
        L17_2 = L11_2[L17_2]
        L17_2 = L17_2.x
        L18_2 = A0_2.shadowBoldWidth
        L17_2 = L17_2 - L18_2
        L16_2.x = L17_2
        L16_2 = L15_2 + 3
        L16_2 = L11_2[L16_2]
        L17_2 = L15_2 + 3
        L17_2 = L11_2[L17_2]
        L17_2 = L17_2.y
        L18_2 = A0_2.shadowBoldWidth
        L17_2 = L17_2 + L18_2
        L16_2.y = L17_2
        L16_2 = L15_2 + 4
        L16_2 = L11_2[L16_2]
        L17_2 = L15_2 + 4
        L17_2 = L11_2[L17_2]
        L17_2 = L17_2.y
        L18_2 = A0_2.shadowBoldWidth
        L17_2 = L17_2 - L18_2
        L17_2 = L17_2 - 1
        L16_2.y = L17_2
        L16_2 = L15_2 + 5
        L16_2 = L11_2[L16_2]
        L17_2 = L15_2 + 5
        L17_2 = L11_2[L17_2]
        L17_2 = L17_2.y
        L18_2 = A0_2.shadowBoldWidth
        L17_2 = L17_2 + L18_2
        L17_2 = L17_2 + 1
        L16_2.y = L17_2
        L16_2 = L15_2 + 6
        L16_2 = L11_2[L16_2]
        L17_2 = L15_2 + 6
        L17_2 = L11_2[L17_2]
        L17_2 = L17_2.x
        L18_2 = A0_2.shadowBoldWidth
        L17_2 = L17_2 - L18_2
        L17_2 = L17_2 - 1
        L16_2.x = L17_2
        L16_2 = L15_2 + 7
        L16_2 = L11_2[L16_2]
        L17_2 = L15_2 + 7
        L17_2 = L11_2[L17_2]
        L17_2 = L17_2.x
        L18_2 = A0_2.shadowBoldWidth
        L17_2 = L17_2 + L18_2
        L17_2 = L17_2 + 1
        L16_2.x = L17_2
      end
    end
  end
  L11_2 = L9_1
  L11_2 = L11_2.newGroup
  L11_2 = L11_2()
  L13_2 = L10_2
  L12_2 = L10_2.insert
  L14_2 = L11_2
  L12_2(L13_2, L14_2)
  L12_2 = L8_2
  L13_2 = L11_2
  L14_2 = 1
  L12_2(L13_2, L14_2)
  L12_2 = L9_2
  L13_2 = L11_2
  L14_2 = A0_2.color
  L12_2(L13_2, L14_2)
  L12_2 = A0_2.x
  if L12_2 ~= nil then
    L12_2 = A0_2.y
    if L12_2 ~= nil then
      L12_2 = A0_2.x
      L13_2 = A0_2.y
      if L2_2 and L3_2 then
        L15_2 = L10_2
        L14_2 = L10_2.setReferencePoint
        L16_2 = L9_1
        L16_2 = L16_2.CenterReferencePoint
        L14_2(L15_2, L16_2)
        L14_2 = L2_2 / 2
        L14_2 = L12_2 + L14_2
        L10_2.x = L14_2
        L14_2 = L3_2 / 2
        L14_2 = L13_2 + L14_2
        L10_2.y = L14_2
      else
        L15_2 = L10_2
        L14_2 = L10_2.setReferencePoint
        L16_2 = L9_1
        L16_2 = L16_2.TopLeftReferencePoint
        L14_2(L15_2, L16_2)
        L10_2.x = L12_2
        L10_2.y = L13_2
      end
    end
  end
  return L10_2
end

function L41_1(A0_2, A1_2, A2_2, A3_2, A4_2, A5_2)
  local L6_2, L7_2
  L6_2 = L39_1
  L7_2 = {}
  L7_2.size = A0_2
  L7_2.color = A1_2
  L7_2.shadow = A2_2
  L7_2.msg = A3_2
  L7_2.width = A4_2
  L7_2.height = A5_2
  return L6_2(L7_2)
end

function L42_1(A0_2)
  local L1_2, L2_2, L3_2, L4_2, L5_2, L6_2, L7_2, L8_2, L9_2, L10_2, L11_2, L12_2, L13_2, L14_2, L15_2, L16_2, L17_2, L18_2, L19_2, L20_2
  L1_2 = A0_2.msg
  L2_2 = L8_1
  L2_2 = L2_2.gsub
  L3_2 = L1_2
  L4_2 = "(\\n)"
  
  function L5_2(A0_3)
    local L1_3
    L1_3 = "\n"
    return L1_3
  end
  
  L2_2 = L2_2(L3_2, L4_2, L5_2)
  L1_2 = L2_2
  L2_2 = L17_1
  L3_2 = L1_2
  L4_2 = "\n"
  L2_2 = L2_2(L3_2, L4_2)
  L1_2 = L2_2
  L2_2 = A0_2.w
  L3_2 = A0_2.h
  L4_2 = A0_2.color
  L5_2 = A0_2.shadow
  L6_2 = A0_2.size
  L7_2 = A0_2.line
  L8_2 = 0
  L9_2 = L9_1
  L9_2 = L9_2.newGroup
  L9_2 = L9_2()
  L10_2 = nil
  L11_2 = L12_1
  L12_2 = L1_2
  L11_2, L12_2, L13_2 = L11_2(L12_2)
  for L14_2, L15_2 in L11_2, L12_2, L13_2 do
    L16_2 = L41_1
    L17_2 = L6_2
    L18_2 = L4_2
    L19_2 = L5_2
    L20_2 = L15_2
    L16_2 = L16_2(L17_2, L18_2, L19_2, L20_2)
    L10_2 = L16_2
    L17_2 = L9_2
    L16_2 = L9_2.insert
    L18_2 = L10_2
    L16_2(L17_2, L18_2)
    if L2_2 and L3_2 then
      L17_2 = L10_2
      L16_2 = L10_2.setReferencePoint
      L18_2 = L9_1
      L18_2 = L18_2.CenterReferencePoint
      L16_2(L17_2, L18_2)
      L16_2 = L2_2 / 2
      L10_2.x = L16_2
      L16_2 = L7_2 / 2
      L16_2 = L8_2 + L16_2
      L10_2.y = L16_2
    else
      L17_2 = L10_2
      L16_2 = L10_2.setReferencePoint
      L18_2 = L9_1
      L18_2 = L18_2.TopLeftReferencePoint
      L16_2(L17_2, L18_2)
      L10_2.x = 0
      L10_2.y = L8_2
    end
    L8_2 = L8_2 + L7_2
  end
  L11_2 = A0_2.rtImg
  L12_2 = A0_2.x
  L13_2 = A0_2.y
  L15_2 = L11_2
  L14_2 = L11_2.insert
  L16_2 = L9_2
  L14_2(L15_2, L16_2)
  if L2_2 and L3_2 then
    L15_2 = L9_2
    L14_2 = L9_2.setReferencePoint
    L16_2 = L9_1
    L16_2 = L16_2.CenterReferencePoint
    L14_2(L15_2, L16_2)
    L14_2 = L2_2 / 2
    L14_2 = L12_2 + L14_2
    L9_2.x = L14_2
    L14_2 = L3_2 / 2
    L14_2 = L13_2 + L14_2
    L9_2.y = L14_2
  else
    L15_2 = L9_2
    L14_2 = L9_2.setReferencePoint
    L16_2 = L9_1
    L16_2 = L16_2.TopLeftReferencePoint
    L14_2(L15_2, L16_2)
    L9_2.x = L12_2
    L9_2.y = L13_2
  end
  return L9_2
end

function L43_1(A0_2, A1_2, A2_2, A3_2, A4_2, A5_2)
  local L6_2, L7_2, L8_2, L9_2, L10_2
  L6_2 = L41_1
  L7_2 = A1_2
  L8_2 = A3_2
  L9_2 = A4_2
  L10_2 = A5_2
  L6_2 = L6_2(L7_2, L8_2, L9_2, L10_2)
  L8_2 = L6_2
  L7_2 = L6_2.setReferencePoint
  L9_2 = L9_1
  L9_2 = L9_2.CenterReferencePoint
  L7_2(L8_2, L9_2)
  L7_2 = A2_2[1]
  L8_2 = A2_2[3]
  L8_2 = L8_2 / 2
  L7_2 = L7_2 + L8_2
  L6_2.x = L7_2
  L7_2 = A2_2[2]
  L8_2 = A2_2[4]
  L8_2 = L8_2 / 2
  L7_2 = L7_2 + L8_2
  L6_2.y = L7_2
  L8_2 = A0_2
  L7_2 = A0_2.insert
  L9_2 = L6_2
  L7_2(L8_2, L9_2)
  return L6_2
end

function L44_1()
  local L0_2, L1_2, L2_2, L3_2, L4_2, L5_2, L6_2, L7_2
  L0_2 = L9_1
  L0_2 = L0_2.newGroup
  L0_2 = L0_2()
  L1_2 = nil
  L2_2 = L33_1
  L3_2 = L0_2
  L4_2 = "data/side/side_cornet.png"
  L5_2 = 0
  L6_2 = 0
  L2_2(L3_2, L4_2, L5_2, L6_2)
  L2_2 = L39_1
  L3_2 = {}
  L3_2.rtImg = L0_2
  L3_2.size = 16
  L4_2 = {}
  L5_2 = 255
  L6_2 = 255
  L7_2 = 255
  L4_2[1] = L5_2
  L4_2[2] = L6_2
  L4_2[3] = L7_2
  L3_2.color = L4_2
  L4_2 = {}
  L5_2 = 0
  L6_2 = 0
  L7_2 = 0
  L4_2[1] = L5_2
  L4_2[2] = L6_2
  L4_2[3] = L7_2
  L3_2.shadow = L4_2
  L3_2.diff_x = 1
  L3_2.diff_y = 2
  L4_2 = "Version "
  L5_2 = _G
  L5_2 = L5_2.Version
  L4_2 = L4_2 .. L5_2
  L3_2.msg = L4_2
  L2_2 = L2_2(L3_2)
  L1_2 = L2_2
  L3_2 = L1_2
  L2_2 = L1_2.setReferencePoint
  L4_2 = L9_1
  L4_2 = L4_2.CenterReferencePoint
  L2_2(L3_2, L4_2)
  L1_2.x = 88
  L1_2.y = 620
  L3_2 = L0_2
  L2_2 = L0_2.setReferencePoint
  L4_2 = L9_1
  L4_2 = L4_2.TopLeftReferencePoint
  L2_2(L3_2, L4_2)
  L2_2 = _G
  L2_2 = L2_2.WidthDiff
  L0_2.x = L2_2
  L0_2.y = 0
  return L0_2
end

function L45_1(A0_2, A1_2, A2_2)
  local L3_2, L4_2, L5_2, L6_2, L7_2, L8_2, L9_2, L10_2, L11_2, L12_2, L13_2, L14_2, L15_2, L16_2
  if A1_2 == nil then
    A1_2 = true
  end
  L3_2 = _G
  L3_2 = L3_2.WidthDiff
  L4_2 = _G
  L4_2 = L4_2.HeightDiff
  L5_2 = L9_1
  L5_2 = L5_2.newGroup
  L5_2 = L5_2()
  if L3_2 < 0 or L4_2 < 0 then
    L6_2 = nil
    if L4_2 < 0 then
      L7_2 = L4_2
      L8_2 = _G
      L8_2 = L8_2.Width
      L9_2 = -L4_2
      L10_2 = L9_1
      L10_2 = L10_2.newRect
      L11_2 = L5_2
      L12_2 = 0
      L13_2 = L7_2
      L14_2 = L8_2
      L15_2 = L9_2
      L10_2 = L10_2(L11_2, L12_2, L13_2, L14_2, L15_2)
      L6_2 = L10_2
      L11_2 = L6_2
      L10_2 = L6_2.setFillColor
      L12_2 = 0
      L13_2 = 0
      L14_2 = 0
      L10_2(L11_2, L12_2, L13_2, L14_2)
      L10_2 = L9_1
      L10_2 = L10_2.newRect
      L11_2 = L5_2
      L12_2 = 0
      L13_2 = _G
      L13_2 = L13_2.Height
      L14_2 = L8_2
      L15_2 = L9_2
      L10_2 = L10_2(L11_2, L12_2, L13_2, L14_2, L15_2)
      L6_2 = L10_2
      L11_2 = L6_2
      L10_2 = L6_2.setFillColor
      L12_2 = 0
      L13_2 = 0
      L14_2 = 0
      L10_2(L11_2, L12_2, L13_2, L14_2)
    end
    if L3_2 < 0 and L3_2 ~= -176 and L3_2 ~= -177 then
      L7_2 = L3_2
      L8_2 = L4_2
      L9_2 = -L3_2
      L10_2 = _G
      L10_2 = L10_2.Height
      L11_2 = -L4_2
      L10_2 = L10_2 + L11_2
      L11_2 = L9_1
      L11_2 = L11_2.newRect
      L12_2 = L5_2
      L13_2 = L7_2
      L14_2 = L8_2
      L15_2 = L9_2
      L16_2 = L10_2
      L11_2 = L11_2(L12_2, L13_2, L14_2, L15_2, L16_2)
      L6_2 = L11_2
      L12_2 = L6_2
      L11_2 = L6_2.setFillColor
      L13_2 = 0
      L14_2 = 0
      L15_2 = 0
      L11_2(L12_2, L13_2, L14_2, L15_2)
      L11_2 = L9_1
      L11_2 = L11_2.newRect
      L12_2 = L5_2
      L13_2 = _G
      L13_2 = L13_2.Width
      L14_2 = L8_2
      L15_2 = L9_2
      L16_2 = L10_2
      L11_2 = L11_2(L12_2, L13_2, L14_2, L15_2, L16_2)
      L6_2 = L11_2
      L12_2 = L6_2
      L11_2 = L6_2.setFillColor
      L13_2 = 0
      L14_2 = 0
      L15_2 = 0
      L11_2(L12_2, L13_2, L14_2, L15_2)
    end
    
    function L7_2(A0_3)
      local L1_3, L2_3
      L1_3 = A2_2
      if L1_3 ~= nil then
        L1_3 = A2_2
        L2_3 = A0_3
        L1_3(L2_3)
      end
    end
    
    function L8_2()
      local L0_3, L1_3, L2_3, L3_3, L4_3, L5_3, L6_3, L7_3
      L0_3 = L9_1
      L0_3 = L0_3.newGroup
      L0_3 = L0_3()
      L1_3 = nil
      L2_3 = L33_1
      L3_3 = L0_3
      L4_3 = "data/side/side_cornet.png"
      L5_3 = 0
      L6_3 = 0
      L2_3(L3_3, L4_3, L5_3, L6_3)
      L2_3 = L39_1
      L3_3 = {}
      L3_3.rtImg = L0_3
      L3_3.size = 16
      L4_3 = {}
      L5_3 = 255
      L6_3 = 255
      L7_3 = 255
      L4_3[1] = L5_3
      L4_3[2] = L6_3
      L4_3[3] = L7_3
      L3_3.color = L4_3
      L4_3 = {}
      L5_3 = 0
      L6_3 = 0
      L7_3 = 0
      L4_3[1] = L5_3
      L4_3[2] = L6_3
      L4_3[3] = L7_3
      L3_3.shadow = L4_3
      L3_3.diff_x = 1
      L3_3.diff_y = 2
      L4_3 = "Version "
      L5_3 = _G
      L5_3 = L5_3.Version
      L4_3 = L4_3 .. L5_3
      L3_3.msg = L4_3
      L2_3 = L2_3(L3_3)
      L1_3 = L2_3
      L3_3 = L1_3
      L2_3 = L1_3.setReferencePoint
      L4_3 = L9_1
      L4_3 = L4_3.CenterReferencePoint
      L2_3(L3_3, L4_3)
      L1_3.x = 88
      L1_3.y = 620
      L3_3 = L0_3
      L2_3 = L0_3.setReferencePoint
      L4_3 = L9_1
      L4_3 = L4_3.TopLeftReferencePoint
      L2_3(L3_3, L4_3)
      L2_3 = L5_2
      L3_3 = L2_3
      L2_3 = L2_3.insert
      L4_3 = L0_3
      L2_3(L3_3, L4_3)
      L2_3 = L3_2
      L0_3.x = L2_3
      L0_3.y = 0
    end
    
    if L3_2 <= -176 then
      L9_2 = L9_1
      L9_2 = L9_2.newGroup
      L9_2 = L9_2()
      L10_2 = L33_1
      L11_2 = L9_2
      L12_2 = "data/side/side_none.png"
      L13_2 = 0
      L14_2 = 0
      L10_2(L11_2, L12_2, L13_2, L14_2)
      L11_2 = L9_2
      L10_2 = L9_2.setReferencePoint
      L12_2 = L9_1
      L12_2 = L12_2.TopRightReferencePoint
      L10_2(L11_2, L12_2)
      L11_2 = L5_2
      L10_2 = L5_2.insert
      L12_2 = L9_2
      L10_2(L11_2, L12_2)
      L9_2.x = 0
      L9_2.y = 0
      L10_2 = _G
      L10_2 = L10_2.IsSimulator
      if L10_2 == true then
        L10_2 = L7_2
        L11_2 = 0
        L10_2(L11_2)
      else
        L10_2 = L0_1
        L10_2 = L10_2.GetLastRes
        L10_2 = L10_2()
        if L10_2 and A1_2 == true then
          L10_2 = 0
          L11_2 = 0
          L12_2 = L3_1
          L12_2 = L12_2.GetLastRes
          L12_2 = L12_2()
          if L12_2 then
            L10_2 = 1
          end
          L12_2 = L4_1
          L12_2 = L12_2.GetLastRes
          L12_2 = L12_2()
          if L12_2 then
            L11_2 = 1
          end
          if L10_2 == 0 and L11_2 == 0 then
            L12_2 = L7_2
            L13_2 = 0
            L12_2(L13_2)
          else
            L12_2 = iconAds
            L12_2 = L12_2.show
            L12_2()
            L12_2 = L7_2
            L13_2 = 1
            L12_2(L13_2)
          end
        else
          L10_2 = iconAds
          L10_2 = L10_2.hide
          L10_2()
          L10_2 = L7_2
          L11_2 = 0
          L10_2(L11_2)
        end
      end
    else
      L9_2 = L7_2
      L10_2 = 0
      L9_2(L10_2)
    end
    L10_2 = A0_2
    L9_2 = A0_2.insert
    L11_2 = L5_2
    L9_2(L10_2, L11_2)
    if L3_2 <= -176 then
      return L5_2
    end
  end
  L6_2 = nil
  return L6_2
end

function L46_1(A0_2)
  local L1_2, L2_2, L3_2, L4_2, L5_2, L6_2, L7_2, L8_2, L9_2, L10_2, L11_2
  L1_2 = A0_2.prev
  L2_2 = A0_2.param
  L3_2 = A0_2.scene
  L4_2 = _G
  L4_2 = L4_2.IsSimulator
  if L4_2 then
    L4_2 = L7_1
    L5_2 = "###change scene "
    L6_2 = L3_2
    L5_2 = L5_2 .. L6_2
    L4_2(L5_2)
  end
  if L3_2 == "cdn" or L3_2 == "complete" or L3_2 == "cont" or L3_2 == "cont_game" or L3_2 == "cutin" or L3_2 == "endcut" or L3_2 == "force_update_view" or L3_2 == "help" or L3_2 == "hint" or L3_2 == "info" or L3_2 == "invite" or L3_2 == "invite_game" or L3_2 == "map" or L3_2 == "menu" or L3_2 == "restart" or L3_2 == "resume" or L3_2 == "shop" or L3_2 == "stage" or L3_2 == "title" then
    L4_2 = "scene."
    L5_2 = L3_2
    L3_2 = L4_2 .. L5_2
  end
  L4_2 = A0_2.efx
  if L4_2 == nil then
    L4_2 = ""
  end
  if L1_2 then
    L5_2 = L11_1
    L6_2 = L1_2
    L5_2 = L5_2(L6_2)
    if L5_2 == "table" then
      L5_2 = L12_1
      L6_2 = L1_2
      L5_2, L6_2, L7_2 = L5_2(L6_2)
      for L8_2, L9_2 in L5_2, L6_2, L7_2 do
        L10_2 = L9_2
        L10_2()
      end
    else
      L5_2 = L1_2
      L5_2()
    end
  end
  L5_2 = nil
  L6_2 = nil
  if L5_2 == L3_2 or L6_2 == L3_2 then
    L7_2 = A0_2.prev
    if L7_2 then
      L7_2 = A0_2.prev
      L7_2()
    end
    L7_2 = require
    L8_2 = L3_2
    L7_2 = L7_2(L8_2)
    L7_2 = L7_2.new
    L8_2 = L2_2
    L7_2(L8_2)
  elseif L2_2 then
    L7_2 = director
    L8_2 = L7_2
    L7_2 = L7_2.changeScene
    L9_2 = L2_2
    L10_2 = L3_2
    L11_2 = L4_2
    L7_2(L8_2, L9_2, L10_2, L11_2)
  else
    L7_2 = director
    L8_2 = L7_2
    L7_2 = L7_2.changeScene
    L9_2 = L3_2
    L10_2 = L4_2
    L7_2(L8_2, L9_2, L10_2)
  end
end

function L47_1(A0_2)
  local L1_2, L2_2, L3_2, L4_2, L5_2, L6_2, L7_2, L8_2, L9_2, L10_2, L11_2
  L1_2 = L17_1
  L2_2 = A0_2
  L3_2 = "[.]"
  L1_2 = L1_2(L2_2, L3_2)
  L2_2 = {}
  L3_2 = nil
  L4_2 = L12_1
  L5_2 = L1_2
  L4_2, L5_2, L6_2 = L4_2(L5_2)
  for L7_2, L8_2 in L4_2, L5_2, L6_2 do
    L9_2 = L14_1
    L10_2 = L8_2
    L9_2 = L9_2(L10_2)
    L3_2 = L9_2
    if L3_2 ~= nil then
      L9_2 = L13_1
      L9_2 = L9_2.insert
      L10_2 = L2_2
      L11_2 = L3_2
      L9_2(L10_2, L11_2)
    else
      L9_2 = L13_1
      L9_2 = L9_2.insert
      L10_2 = L2_2
      L11_2 = L8_2
      L9_2(L10_2, L11_2)
    end
  end
  return L2_2
end

function L48_1(A0_2)
  local L1_2, L2_2
  L1_2 = math
  L1_2 = L1_2.ceil
  L2_2 = A0_2 * 0.1
  return L1_2(L2_2)
end

function L49_1(A0_2, A1_2, A2_2, A3_2)
  local L4_2, L5_2, L6_2, L7_2
  L4_2 = {}
  L4_2.x = 0
  L4_2.y = 0
  L5_2 = 1 - A3_2
  L6_2 = 1 - A3_2
  L5_2 = L5_2 * L6_2
  L6_2 = L4_2.x
  L7_2 = A0_2.x
  L7_2 = L5_2 * L7_2
  L6_2 = L6_2 + L7_2
  L4_2.x = L6_2
  L6_2 = L4_2.y
  L7_2 = A0_2.y
  L7_2 = L5_2 * L7_2
  L6_2 = L6_2 + L7_2
  L4_2.y = L6_2
  L6_2 = 2 * A3_2
  L7_2 = 1 - A3_2
  L5_2 = L6_2 * L7_2
  L6_2 = L4_2.x
  L7_2 = A1_2.x
  L7_2 = L5_2 * L7_2
  L6_2 = L6_2 + L7_2
  L4_2.x = L6_2
  L6_2 = L4_2.y
  L7_2 = A1_2.y
  L7_2 = L5_2 * L7_2
  L6_2 = L6_2 + L7_2
  L4_2.y = L6_2
  L5_2 = A3_2 * A3_2
  L6_2 = L4_2.x
  L7_2 = A2_2.x
  L7_2 = L5_2 * L7_2
  L6_2 = L6_2 + L7_2
  L4_2.x = L6_2
  L6_2 = L4_2.y
  L7_2 = A2_2.y
  L7_2 = L5_2 * L7_2
  L6_2 = L6_2 + L7_2
  L4_2.y = L6_2
  return L4_2
end

function L50_1(A0_2, A1_2)
  local L2_2, L3_2
  L2_2 = L5_1
  L2_2 = L2_2.GetLastRes
  L2_2 = L2_2()
  if L2_2 then
    if A0_2 ~= nil then
      L2_2 = A0_2
      L3_2 = true
      L2_2(L3_2)
    end
    L2_2 = true
    return L2_2
  end
  if A0_2 ~= nil then
    L2_2 = A0_2
    L3_2 = false
    L2_2(L3_2)
  end
  L2_2 = false
  return L2_2
end

function L51_1(A0_2, A1_2, A2_2)
  local L3_2, L4_2, L5_2, L6_2, L7_2, L8_2, L9_2
  L3_2 = _G
  L3_2 = L3_2.REACHABLE_URL
  
  function L4_2(A0_3)
    local L1_3, L2_3
    L1_3 = A0_3.isError
    if L1_3 then
      L1_3 = A2_2
      L2_3 = A0_3
      L1_3(L2_3)
    else
      L1_3 = A1_2
      L2_3 = A0_3
      L1_3(L2_3)
    end
  end
  
  L5_2 = network
  L5_2 = L5_2.request
  L6_2 = L3_2
  L7_2 = "GET"
  L8_2 = L4_2
  L9_2 = {}
  L5_2(L6_2, L7_2, L8_2, L9_2)
end

function L52_1(A0_2, A1_2)
  local L2_2, L3_2, L4_2, L5_2
  L2_2 = L51_1
  L3_2 = "www.google.com"
  L4_2 = A0_2
  L5_2 = A1_2
  L2_2(L3_2, L4_2, L5_2)
end

function L53_1(A0_2)
  local L1_2, L2_2, L3_2, L4_2, L5_2, L6_2, L7_2, L8_2
  L1_2 = L7_1
  L2_2 = "file list:"
  L1_2(L2_2)
  L1_2 = lfs
  L1_2 = L1_2.dir
  L2_2 = A0_2
  L1_2, L2_2, L3_2 = L1_2(L2_2)
  for L4_2 in L1_2, L2_2, L3_2 do
    if L4_2 ~= "." and L4_2 ~= ".." then
      L5_2 = lfs
      L5_2 = L5_2.attributes
      L6_2 = L4_2
      L7_2 = "mode"
      L5_2 = L5_2(L6_2, L7_2)
      if L5_2 == "file" then
        L5_2 = L7_1
        L6_2 = "found file, "
        L7_2 = L4_2
        L6_2 = L6_2 .. L7_2
        L5_2(L6_2)
      else
        L5_2 = lfs
        L5_2 = L5_2.attributes
        L6_2 = L4_2
        L7_2 = "mode"
        L5_2 = L5_2(L6_2, L7_2)
        if L5_2 == "directory" then
          L5_2 = L7_1
          L6_2 = "found dir, "
          L7_2 = L4_2
          L6_2 = L6_2 .. L7_2
          L7_2 = " containing:"
          L5_2(L6_2, L7_2)
          L5_2 = util
          L5_2 = L5_2.filelist
          L6_2 = A0_2
          L7_2 = "/"
          L8_2 = L4_2
          L6_2 = L6_2 .. L7_2 .. L8_2
          L5_2(L6_2)
        end
      end
    end
  end
end

function L54_1(A0_2)
  local L1_2, L2_2
  L1_2 = _G
  L1_2 = L1_2.IsDebug
  if L1_2 then
    L1_2 = L19_1
    L2_2 = A0_2
    L1_2(L2_2)
  end
end

function L55_1(A0_2)
  local L1_2, L2_2
  L1_2 = _G
  L1_2 = L1_2.IsResDebug
  if L1_2 then
    L1_2 = L19_1
    L2_2 = A0_2
    L1_2(L2_2)
  end
end

function L56_1(A0_2)
  local L1_2, L2_2
  L1_2 = _G
  L1_2 = L1_2.IsAndroid
  if not L1_2 then
    L1_2 = native
    L1_2 = L1_2.setActivityIndicator
    L2_2 = A0_2
    L1_2(L2_2)
  end
end

L57_1 = {}
L57_1.print_r = L19_1
L57_1.print_r2 = L20_1
L57_1.IsContainedTable = L21_1
L57_1.IsExistFile = L22_1
L57_1.Dirname = L23_1
L57_1.Mkdir = L24_1
L57_1.MakeGroup = L25_1
L57_1.MakeFrame = L45_1
L57_1.LoadBtnGroup = L26_1
L57_1.LoadBtn = L27_1
L57_1.SetBtnFunction = L28_1
L57_1.LoadBG = L29_1
L57_1.LoadTile = L30_1
L57_1.LoadTileBG = L31_1
L57_1.LoadTileParts = L32_1
L57_1.LoadParts = L33_1
L57_1.LoadPartsCenter = L34_1
L57_1.MakeMat = L35_1
L57_1.Num2Column = L36_1
L57_1.GetDistance = L37_1
L57_1.CopyFile = L38_1
L57_1.StringSplit = L17_1
L57_1.MakeText = L41_1
L57_1.MakeCenterText = L43_1
L57_1.MakeText2 = L42_1
L57_1.MakeText3 = L39_1
L57_1.MakeText4 = L40_1
L57_1.ChangeScene = L46_1
L57_1.SplitVersion = L47_1
L57_1.ConvertDisplayCrystal = L48_1
L57_1.CalcQuadraticBezPoint = L49_1
L57_1.IsBingoEnabled = L50_1
L57_1.ReachableSwitchCustom = L51_1
L57_1.ReachableSwitch = L52_1
L57_1.Filelist = L53_1
L57_1.Debug = L54_1
L57_1.ResDebug = L55_1
L57_1.LoadLeftSideCharacterImage = L44_1
L57_1.setActivityIndicator = L56_1
return L57_1
