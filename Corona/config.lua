local L0_1

function L0_1(...)
  local L0_2, L1_2, L2_2
  L0_2 = display
  L0_2 = L0_2.pixelHeight
  L1_2 = display
  L1_2 = L1_2.pixelWidth
  L0_2 = L0_2 / L1_2
  ratio = L0_2
  L0_2 = {}
  L1_2 = {}
  L1_2.graphicsCompatibility = 1
  L1_2.width = 640
  L1_2.height = 960
  L1_2.scale = "letterbox"
  L1_2.xAlign = "center"
  L2_2 = ratio
  if L2_2 <= 1.72 then
    L2_2 = "center"
  else
    L2_2 = "bottom"
  end
  L1_2.yAlign = L2_2
  L1_2.fps = 30
  L1_2.antialias = true
  L1_2.audioPlayFrequency = 22050
  L0_2.content = L1_2

  L1_2 = {}
  L2_2 = {}
  L2_2.projectNumber = "942290441806"
  L1_2.google = L2_2
  L0_2.notification = L1_2

  L1_2 = {}
  L2_2 = {}
  L2_2.key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlBH+fACKjujlZAI02EUNIC0ET6jHtV6FiaNvJ9Ej2+PZa9eh3tPVFo8VOZPI0uKFe0LG3aLV2ZMqkWQfDy9Wl4TYypULaJnwazXmdH5+4bcx6/4EkASeTl9qrD8Aic1krg30eZgtVt6Cmbp2exHMOY52g9FNnQDZ7kLvuXHlB6lylalqeQrhrYg9jtWSAXZnW0Q7QYZQsKnXg71+chlTt8Cm6ywah/94lOSn7YbeFhjf2g5PDkQgTwgm0rT2Ld/llE7xovDKh7QfRlhtnRCgv7iKVWYF8f/d3/FxB06quVnlrGIxkDCUSkZK8FaPTuFhxsHCa9qGuDjELZJT4D5DEwIDAQAB"
  L1_2.google = L2_2
  L0_2.license = L1_2

  application = L0_2
end

L0_1()

local function L0_1_metadata(...)
  local L0_2, L1_2
  L0_2 = application
  if not (type(L0_2) == "table") then
    L0_2 = {}
    application = L0_2
  end
  L0_2 = application
  L1_2 = {}
  L1_2.appName = "android"
  L1_2.appVersion = "1.0"
  L1_2.userId = "345"
  L1_2.subscription = "enterprise"
  L1_2.mode = "distribution"
  L0_2.metadata = L1_2
end

L0_1_metadata()
