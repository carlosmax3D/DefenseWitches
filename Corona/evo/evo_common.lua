-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local function r2_0(r0_3)
  -- line: [65, 88] id: 3
  if r0_3 == nil or r0_3.rtImg == nil or r0_3.spriteType == nil or r0_3.place == nil then
    return 
  end
  local r1_3 = 0
  local r2_3 = 0
  local r3_3 = 0
  local r4_3 = 0
  if r0_3.x ~= nil then
    r1_3 = r0_3.x
  end
  if r0_3.y ~= nil then
    r2_3 = r0_3.y
  end
  if r0_3.offsetX ~= nil then
    r3_3 = r0_3.offsetX
  end
  if r0_3.offsetY ~= nil then
    r4_3 = r0_3.offsetY
  end
  SpriteLdr.CreateNumbers(r0_3.rtImg, r0_3.spriteType, r0_3.place, r1_3, r2_3, r3_3, r4_3)
end
return {
  create_exp_frame = function(r0_1, r1_1, r2_1, r3_1)
    -- line: [9, 23] id: 1
    local r5_1 = r1_1
    local r7_1 = r3_1 - 40 / 1
    local r8_1 = require("common.sprite_loader").new({
      imageSheet = "common.sprites.sprite_parts01",
    })
    r8_1.CreateImage(r0_1, r8_1.sequenceNames.ExpFrame, r8_1.frameDefines.ExpFrameLeft, r5_1, r2_1)
    r5_1 = r5_1 + 20
    local r9_1 = r8_1.CreateImage(r0_1, r8_1.sequenceNames.ExpFrame, r8_1.frameDefines.ExpFrameCenter, r5_1, r2_1)
    r9_1.x = r5_1 + r7_1 * 0.5
    r9_1.width = r7_1
    r8_1.CreateImage(r0_1, r8_1.sequenceNames.ExpFrame, r8_1.frameDefines.ExpFrameRight, r5_1 + r7_1, r2_1)
  end,
  show_num_images = function(r0_2)
    -- line: [30, 53] id: 2
    if r0_2 == nil or r0_2.rtImg == nil or r0_2.rtImg ~= nil and r0_2.rtImg.numChildren < 1 or r0_2.num == nil then
      return 
    end
    local r1_2 = nil
    local r3_2 = math.floor(r0_2.num)
    for r7_2 = 1, r0_2.rtImg.numChildren, 1 do
      local r8_2 = r0_2.rtImg[r7_2]
      if r8_2 ~= nil then
        local r9_2 = r3_2 % 10
        r3_2 = math.floor(r3_2 * 0.1)
        r8_2:setFrame(r9_2 + 1)
        local r10_2 = nil	-- notice: implicit variable refs by block#[13]
        if 0 >= r9_2 and 0 >= r3_2 then
          r10_2 = r7_2 == 1
        else
          r10_2 = 0 >= r9_2 and 0 >= r3_2	-- block#12 is visited secondly
        end
        r8_2.isVisible = r10_2
      end
    end
  end,
}
