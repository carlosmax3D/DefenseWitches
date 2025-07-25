-- filename: 
-- version: lua51
-- line: [0, 0] id: 0
local r0_0 = {
  iPad11 = "iPad1 ",
  iPad21 = "iPad2 Wi‑Fi",
  iPad22 = "iPad2 GSM",
  iPad23 = "iPad2 CDMA",
  iPad24 = "iPad2 Wi‑Fi Rev A",
  iPad25 = "iPad mini Wi‑Fi",
  iPad26 = "iPad mini GSM",
  iPad27 = "iPad mini GSM+CDMA",
  iPad31 = "iPad3 Wi‑Fi",
  iPad32 = "iPad3 GSM+CDMA",
  iPad33 = "iPad3 GSM",
  iPad34 = "iPad4 Wi‑Fi",
  iPad35 = "iPad4 GSM",
  iPad36 = "iPad4 GSM+CDMA",
  iPad41 = "iPad Air Wi‑Fi",
  iPad42 = "iPad Air Cellular",
  iPad44 = "iPad mini2 Wi‑Fi",
  iPad45 = "iPad mini2 Cellular",
  iPad46 = "iPad mini2 A1491",
  iPad47 = "iPad mini3 A1599",
  iPad48 = "iPad mini3 A1600",
  iPad49 = "iPad mini3 A1601",
  iPad53 = "iPad Air2 A1566",
  iPad54 = "iPad Air2 A1567",
  iPhone21 = "iPhone3GS",
  iPhone31 = "iPhone4 GSM",
  iPhone32 = "iPhone4 GSM Rev A",
  iPhone33 = "iPhone4 CDMA",
  iPhone41 = "iPhone4S GSM+CDMA",
  iPhone51 = "iPhone5 GSM",
  iPhone52 = "iPhone5 GSM+CDMA",
  iPhone53 = "iPhone5c GSM",
  iPhone54 = "iPhone5c Global",
  iPhone61 = "iPhone5s GSM",
  iPhone62 = "iPhone5s Global",
  iPhone71 = "iPhone6 Plus",
  iPhone72 = "iPhone6",
  iPod21 = "iPod touch2",
  iPod31 = "iPod touch3",
  iPod41 = "iPod touch4",
  iPod51 = "iPod touch5",
}
return {
  GetDeveiceName = function(r0_1)
    -- line: [46, 55] id: 1
    r0_1 = string.gsub(r0_1, ",", "")
    local r1_1 = r0_0[r0_1]
    if r1_1 == nil then
      return r0_1
    else
      return r1_1
    end
  end,
}
