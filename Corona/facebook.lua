local Library = require "CoronaLibrary"

-- Create stub library for simulator
local lib = Library:new{ name='plugin.facebook.v4', publisherId='com.coronalabs' }

-- Default implementations
local function defaultFunction()
	print( "WARNING: The '" .. lib.name .. "' library is not available in the Corona Simulator." )
end

local function defaultFunctionReturnNil()
	defaultFunction()
	return nil
end

local function defaultFunctionReturnFalse()
	defaultFunction()
	return false
end

local function defaultFunctionReturnTrue()
	defaultFunction()
	return true
end

lib.login = defaultFunction
lib.logout = defaultFunction
lib.request = defaultFunction
lib.showDialog = defaultFunction
lib.publishInstall = defaultFunction
lib.accessDenied = defaultFunctionReturnTrue

-- New Facebook-v4 APIs
lib.getCurrentAccessToken = defaultFunctionReturnNil
lib.isActive = defaultFunctionReturnTrue
lib.setFBConnectListener= defaultFunction
lib.isFacebookAppEnabled = defaultFunctionReturnFalse

-- Introduced in Android Beta v1 and deprecated later
lib.currentAccessToken = defaultFunctionReturnNil

-- Return an instance
return lib
