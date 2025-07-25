local Library = require "CoronaLibrary"

-- Create stub library for simulator
local lib = Library:new{ name='plugin.notifications', publisherId='com.coronalabs' }

-- Default implementations
local function defaultFunction()
	print( "WARNING: The '" .. lib.name .. "' library is not available on this platform." )
end

lib.scheduleNotification = defaultFunction
lib.registerForPushNotifications = defaultFunction
lib.cancelNotification = defaultFunction

-- Return an instance
return lib
