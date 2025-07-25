local Library = require "CoronaLibrary"

-- Create library
local lib = Library:new{ name='plugin.google.iap.v3', publisherId='com.coronalabs' }

-------------------------------------------------------------------------------
-- BEGIN (Insert your implementation starting here)
-------------------------------------------------------------------------------

lib.init = function()
	print( "WARNING: This library is not available on this platform")
end

lib.finishTransaction = function()
	print( "WARNING: This library is not available on this platform")
end

lib.loadProducts = function()
	print( "WARNING: This library is not available on this platform")
end

lib.purchase = function()
	print( "WARNING: This library is not available on this platform")
end

lib.restore = function()
	print( "WARNING: This library is not available on this platform")
end

lib.consumePurchase = function()
	print( "WARNING: This library is not available on this platform")
end

lib.canLoadProducts = false

lib.canMakePurchases = false

lib.isActive = false

return lib
