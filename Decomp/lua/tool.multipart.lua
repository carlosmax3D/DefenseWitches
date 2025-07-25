-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = require("crypto")
local r1_0 = require("ltn12")
local r2_0 = require("mime")
local r3_0 = {}
local r4_0 = {
  __index = r3_0,
}
function r3_0.new()
  -- line: [48, 60] id: 1
  local r1_1 = {
    isClass = true,
    boundary = "MPFD-" .. r0_0.digest(r0_0.sha1, "MultipartFormData" .. tostring(object) .. tostring(os.time()) .. tostring(os.clock()), false),
    headers = {},
    elements = {},
  }
  r1_1.headers["MIME-Version"] = "1.0"
  return setmetatable(r1_1, r4_0)
end
function r3_0.getBody(r0_2)
  -- line: [62, 130] id: 2
  local r1_2 = {}
  table.insert(r1_2, r1_0.source.chain(r1_0.source.string("\n\n"), r2_0.normalize()))
  for r5_2 = 1, #r0_2.elements, 1 do
    local r6_2 = r0_2.elements[r5_2]
    if r6_2 then
      if r6_2.intent == "field" then
        table.insert(r1_2, r1_0.source.chain(r1_0.source.string(table.concat({
          "--" .. r0_2.boundary .. "\n",
          "content-disposition: form-data; name=\"",
          r6_2.name,
          "\"\n\n",
          r6_2.value,
          "\n"
        })), r2_0.normalize()))
      elseif r6_2.intent == "file" then
        local r8_2 = table.concat({
          "--" .. r0_2.boundary .. "\n",
          "content-disposition: form-data; name=\"",
          r6_2.name,
          "\"; filename=\"",
          r6_2.filename,
          "\"\n",
          "Content-Type: ",
          r6_2.mimetype,
          "\n",
          "Content-Transfer-Encoding: ",
          r6_2.encoding,
          "\n\n"
        })
        local r9_2 = io.open(r6_2.path, "rb")
        assert(r9_2)
        table.insert(r1_2, r1_0.source.cat(r1_0.source.chain(r1_0.source.string(r8_2), r2_0.normalize()), r1_0.source.chain(r1_0.source.file(r9_2), r1_0.filter.chain(r2_0.encode(r6_2.encoding), r2_0.wrap())), r1_0.source.chain(r1_0.source.string("\n"), r2_0.normalize())))
      end
    end
  end
  table.insert(r1_2, r1_0.source.chain(r1_0.source.string("\n--" .. r0_2.boundary .. "--\n"), r2_0.normalize()))
  local r2_2 = r1_0.source.empty()
  for r6_2 = 1, #r1_2, 1 do
    r2_2 = r1_0.source.cat(r2_2, r1_2[r6_2])
  end
  local r3_2, r4_2 = r1_0.sink.table()
  r1_0.pump.all(r2_2, r3_2)
  local r5_2 = table.concat(r4_2)
  r0_2.headers["Content-Type"] = "multipart/form-data; boundary=" .. r0_2.boundary
  r0_2.headers["Content-Length"] = string.len(r5_2)
  return r5_2
end
function r3_0.getHeaders(r0_3)
  -- line: [132, 136] id: 3
  assert(r0_3.headers["Content-Type"])
  assert(r0_3.headers["Content-Length"])
  return r0_3.headers
end
function r3_0.addHeader(r0_4, r1_4, r2_4)
  -- line: [138, 140] id: 4
  r0_4.headers[r1_4] = r2_4
end
function r3_0.setBoundry(r0_5, r1_5)
  -- line: [142, 144] id: 5
  r0_5.boundary = r1_5
end
function r3_0.addField(r0_6, r1_6, r2_6)
  -- line: [146, 148] id: 6
  r0_6:add("field", r1_6, r2_6)
end
function r3_0.addFile(r0_7, r1_7, r2_7, r3_7, r4_7)
  -- line: [150, 162] id: 7
  r0_7:addElement({
    intent = "file",
    name = r1_7,
    path = r2_7,
    mimetype = r3_7,
    filename = r4_7,
    encoding = "base64",
  })
end
function r3_0.add(r0_8, r1_8, r2_8, r3_8)
  -- line: [164, 167] id: 8
  r0_8:addElement({
    intent = r1_8,
    name = r2_8,
    value = r3_8,
  })
end
function r3_0.addElement(r0_9, r1_9)
  -- line: [169, 171] id: 9
  table.insert(r0_9.elements, r1_9)
end
function r3_0.toString(r0_10)
  -- line: [173, 175] id: 10
  return "MultipartFormData [elementCount:" .. tostring(#r0_10.elements) .. ", headerCount:" .. tostring(#r0_10.headers) .. "]"
end
return r3_0
