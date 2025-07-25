-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
(function(...)
  -- line: [0, 0] id: 1
  ratio = display.pixelHeight / display.pixelWidth
  local r0_1 = {}
  local r1_1 = {
    graphicsCompatibility = 1,
    width = 640,
    height = 960,
    scale = "letterbox",
    xAlign = "center",
  }
  local r2_1 = ratio
  if r2_1 <= 1.72 then
    r2_1 = "center" or "bottom"
  else
    goto label_19	-- block#2 is visited secondly
  end
  r1_1.yAlign = r2_1
  r1_1.fps = 30
  r1_1.antialias = true
  r1_1.audioPlayFrequency = 22050
  r0_1.content = r1_1
  r0_1.notification = {
    google = {
      projectNumber = "942290441806",
    },
  }
  r0_1.license = {
    google = {
      key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlBH+fACKjujlZAI02EUNIC0ET6jHtV6FiaNvJ9Ej2+PZa9eh3tPVFo8VOZPI0uKFe0LG3aLV2ZMqkWQfDy9Wl4TYypULaJnwazXmdH5+4bcx6/4EkASeTl9qrD8Aic1krg30eZgtVt6Cmbp2exHMOY52g9FNnQDZ7kLvuXHlB6lylalqeQrhrYg9jtWSAXZnW0Q7QYZQsKnXg71+chlTt8Cm6ywah/94lOSn7YbeFhjf2g5PDkQgTwgm0rT2Ld/llE7xovDKh7QfRlhtnRCgv7iKVWYF8f/d3/FxB06quVnlrGIxkDCUSkZK8FaPTuFhxsHCa9qGuDjELZJT4D5DEwIDAQAB",
    },
  }
  application = r0_1
end)()
(function(...)
  -- line: [0, 0] id: 2
  if not application or type(application) ~= "table" then
    application = {}
  end
  application.metadata = {
    appName = "android",
    appVersion = "1.0",
    userId = "345",
    subscription = "enterprise",
    mode = "distribution",
  }
end)()
