package jp.tjkapp.adfurikunsdk;

import android.annotation.SuppressLint;
import com.facebook.share.internal.ShareConstants;
import com.tapjoy.TapjoyConstants;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jp.stargarage.g2metrics.BuildConfig;
import jp.tjkapp.adfurikunsdk.API_Controller2;
import jp.tjkapp.adfurikunsdk.ApiAccessUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
class API_ScaleOut extends API_Base {
    private static final String GETINFO_SCALEOUT_PARAM_BADV = "badv";
    private static final String GETINFO_SCALEOUT_PARAM_BCAT = "bcat";
    private static final String GETINFO_SCALEOUT_PARAM_CAT = "cat";
    private static final String GETINFO_SCALEOUT_PARAM_H = "h";
    private static final String GETINFO_SCALEOUT_PARAM_W = "w";
    private static final String SCALEOUT_HTML1 = "<html><head><base target=\"_blank\"><meta http-equiv='Content-Type' content='text/html; charset=utf-8' /><style>img{width:320px;height: auto;}@media screen and (min-width: 640px) and orientation: portrait){img{width:640px;height:auto;}}@media screen and (min-width:768px) and (orientation:landscape){img{width:640px;height: auto;}}</style></head><body style='text-align:center;padding:0;margin:0;overflow:hidden;'><div style=\"text-align:center;\"><a href=\"";
    private static final String SCALEOUT_HTML11 = "<html><head><base target=\"_blank\"><meta http-equiv='Content-Type' content='text/html; charset=utf-8' /></head><body style='text-align:center;padding:0;margin:0;overflow:hidden;'><div style=\"text-align:center;\"><a href=\"";
    private static final String SCALEOUT_HTML12 = "\"><img src=\"";
    private static final String SCALEOUT_HTML13 = "\" width=\"100%%\"></a></div></body></html>";
    private static final String SCALEOUT_HTML2 = "\"><img src=\"";
    private static final String SCALEOUT_HTML3 = "\"></a></div></body></html>";
    private static final String SCALEOUT_URL = "http://adfuri.socdm.com/rtb/bid?id=17873&posall=RTB&proto=adfuri";
    private String mAppID;
    String[] priceList = {"7K9SQD3AY_E", "qsixOrkO17g", "FhYAhdTtaOE", "lr38f-Lmhkw", "fWBIA5GM-HI", "iAOJzDz0qKY", "0XAcHhRTp34", "IdRf48jiwk8", "ipQ4grf_Z44", "1d-lrwltHsk", "85eP6DtF42Q", "6O_ujsWWliU", "DakcIgUUsrg", "J3Jwv9VZUT4", "NCc-TF6uixY", "vxU9p8Yla7Q", "Z1Knr8P9zQk", "RIkhib-JYCQ", "q2_1QjwTEIc", "KG8N0y5gDdk", "VThOfyMr6W4", "fc6cphGuqf8", "PEJz1iuygwk", "4CBSFxf0jxI", "ic5RageRZD8", "h6Unj4XbLco", "3FS3YKshNLc", "rZYtBh3T3ys", "qC6OVBSL1yY", "GbU-Bu_q7EI", "s5iclzmuGwY", "V4cOai-EEpA", "BunMWFXK0A8", "WC99hV0hiRM", "U7Vc18-1Ioo", "J4tALTFH074", "ewJUwNqPSo0", "ZGT2IvDDod0", "E_SO5YaRiFs", "2yMrmA5NodM", "InsWj-O4QEg", "uiFdEzfXCqs", "jPzPmAlbJ6Q", "J-OuP1TEshw", "y9fr_KTgN4M", "2MvuOGwlfFw", "6FPty54aMyk", "u2zxSCEosmg", "KeYjpJQaLj4", "lNQh-7I5O7Q", "YTF3CttiXpY", "7Msl61U6CZ8", "4RvJSbAlUsM", "45LLdyqGsuM", "W2OO28SLjno", "KS_tZW4TVVw", "cbU6xvJjs88", "1bZKanbaxMY", "I0A9hZFEYPk", "xXIkwdpqXTo", "LB9oElYCHAE", "0hvW8jy5ATU", "mI7rNdpANKg", "3phpZaBvoiI", "Hudof5vQxvI", "FltcWua6TiI", "BwDnQMz1BFY", "xlj0oK0pnFk", "YUpswX-DJiE", "qq66Sc3_F3Q", "WIrJapgz1r8", "UXxuKOBRYpw", "um2eAz3A7-c", "QqmiV_OpRh4", "mcVXjxNAhr8", "3Wgytq2h1T0", "9SPBQ-c8lxE", "Oq4ecNU3UA0", "w75_WQCjWWg", "ZK0zftgedVE", "q1B3MOqS7gs", "CSycaamzB1w", "04TyKOtvQ9A", "hFZw10r0wVY", "F7HhKCcnMCo", "IqKxRsAml-M", "JH9J4tAJBWs", "NeWC0iMKae0", "-0vzZzeImQ4", "uJCFtufVwJ4", "l_velmsf1sI", "Gxw1o5GfRpA", "p5_45aqYRo4", "zrmTHHwdKs0", "ZbxKwN9nICE", "Zjgg35SONJY", "Lwa2Q1bNTi0", "W5lIPUy_ciY", "y1_aIRMRJ88", "goa8H-Ubl1s", "Z0ajcSK1PdE", "Vz8LveXYUVQ", "r3aLD0PlMEk", "zFS7RY5Pn4A", "NyQT8vhj9cQ", "-AFifTLcuPo", "9BUndvqGnSw", "-SSaSYDAfCA", "I6xFHtDDaSM", "RW8FXBoRcRs", "9k8bKJFKwhM", "0VZRk1rvRhQ", "xgU6yYQJ7As", "75QwkTYSdyw", "eF5r3-FcDC8", "bI4PtdlK9Ss", "KN5-Rmv7UVE", "Q5cqTOKD7XI", "bsoShsqj5PM", "AhnegPMkACI", "JfkOEucJurU", "q5kZqXJ9wA0", "R1kPzVKlpAY", "IFub52G9O8Y", "EsWA-Fki8FE", "C5LKzR4TLEQ", "cAivq_iX46I", "O77UlAvX-YY", "AbtmfDgXg6s", "2W7CyJh2xzM", "j6-Bfovgic4", "Scix6xPZnh4", "WPfNmtVeVRY", "DGDaGVH0Nwo", "MHAZghdJ9CI", "iXhxebCYrpQ", "QiNkd-lL62g", "KXs8Ec19FyY", "04aHcZOShtc", "uq6arg-CQMk", "6hRHBaqfnp0", "TBF9QFL0GWQ", "TjdKWRt2Dak", "JBxBAf9QwJM", "NqpmA2K90xg", "our6TR2BH_A", "RV-qFXEGDKQ", "2Qnl_xjig6I", "tAZNSBr4NFY", "rDiCr5rqGBA", "3WhPwERQsRo", "CU2rS956ZEg", "-W9R_xbBLbM", "Djhs5awQAuc", "qO28qv-VHCE", "7vKXUJesnKg", "01z4nbXj3jM", "psmYMea0jI4", "qARtEYpk9kw", "_cNtRYcf8vQ", "Bbg5OdGzUjI", "qkOEpPydLps", "mPn6eunk7oA", "5_jbwYihlOE", "7bwf6w_Pk5g", "EPVnvtRsDEc", "sKCOfzGRYmE", "2PlAg7vpjzY", "BuLIOlQ5Phs", "ig6KMMeF4Hc", "TBlVzZlGiBA", "p3ByiVdItrQ", "MUGSYZCJMZY", "Nk8QxfjwynI", "Js2K22_Q1wk", "yhhNfNm9U9k", "WvB6w8LWxSg", "Lyy5Vce7m04", "wR-vKC-NQVY", "Ur79hQ4EXVA", "glp_izLMNFI", "vUjbZtxWlos", "8-nQpZoZqi4", "rYu-lmk-UKQ", "5GgGsKZ4gKE", "98ZpRwSXxzY", "HHI99no30pQ", "5i25lTOtVxU", "v3b8rtLyyb0", "qANhAUNbLjw", "Pooq0-Jx7c8", "3_A4vZbLSDQ", "jL3o8m_VD0k", "t_wgUzul98s", "2f2YUPHCD48", "yQZquaO5BSA", "MmF5AWxbpAU", "zXUaIjKVlCY", "v6cYTYgd5Ew", "mZ2tFn8-Mfs", "VlBnyzeH7kA", "vIqG-ApkxiQ", "iC8KzxcyaWM", "6LTJQAyGu0M", "0AAUDvrvy80", "sja_YD7OccA", "THiVSoikaSM", "sZNvo4tQ-1s", "KLgN-U-YtRk", "2IyfhVy0aeA", "Cg4RpwAOCgw", "oun04L-Y384", "zPHNIxsKtCc", "hqKzSv9bSfw", "aJIrUi0rYgk", "UxUqrYWBswI", "kMdtttiTNMs", "_sw2fBcuuGc", "8XeT7lL8hPs", "cqq6DibDmTQ", "jL1Ah4ZZKY4", "CFuuLFTRWzU", "ytDq0BfyFBw", "0mnKpO-WY7Q", "xpLobXUWM4g", "W-zyWi_49Cc", "6j2z4HbAqvg", "Mno17sSGzFY", "8VHAOGU9nAc", "MND9MhoY8vM", "EjqLxJUzezM", "NDazh7C0m08", "bn_5snKIHyo", "yGbpJBSrm3M", "URtGbPzuVFA", "1dlhBSpvA3Q", "0wFMpde7zhg", "QfazezUc6c8", "mpYASD0zETM", "-ulBuAYW7Hs", "Si0SdXPWUTw", "KHBVISeFokM", "rV5IJHS-GUA", "K-qQ4HjRPoU", "M0yP1baJjVk", "__5lu3h1xz4", "4Ew-xMJHp3g", "4Dv2yBfRgEo", "F9HlXy_8tvw", "0wwh1TomczQ", "z20Ujw9m8Ec", "Hb7Q03RDMXc", "eDbuhbkPBhU", "xw-VejxBkkA", "tRlOGsA8NlA", "VQjvNYxbFtg", "cSusMqJSoyk", "kDaJq2V0CUY", "VFB_33xbfbw", "MBfboumqdLk", "7-JTFm9i5kg", "KVhV5oxuzVE", "Hyp_RjArXOY", "D28iYko5-lA", "2jJsaKW0P3k", "Kuuzo-p6zgE", "6qGWUI7ICFE", "DvxmPwjA28c", "JzFJHs49xkI", "dzyP9NL8Lxg", "lHv8A_2Zy78", "_mOGgjbOGgY", "F5nNZn1mJWg", "MLf7FzzVqPw", "GNZGfPjvTmc", "XL_9N9pXCT4", "J-VEJeNl9jc", "YOewWmvrLzs", "gDUMMjnNnUg", "SbWb-qqpod8", "KPC1hm4ENEg", "-pXoO0yDF_A", "54rfezZKgOk", "nmVJW10Rqqs", "68mbrwXz5a8", "zF2bXYcTsi4", "vSGHu67k34U", "ADpma3zDim4", "2tz5nYZMiAU", "vrXb5RslsdQ", "5oNS5ooFvtY", "8eeLdR1lNEU", "vZx8EfkkwWE", "6Nj2X_FEZfc", "rXDF4-RhlgI", "HMmc1phb3tQ", "KDIR_dv78qE", "QgNMmF5PZAM", "QahFoKiBt3o", "Zkuux5VVXKk", "K8LYOmX6i9E", "Wb1uzscSZ5Y", "PTLkbXeyIHU", "qsHgIliOI3Y", "hnG0nQif2mw", "dYbrYGMxKZg", "mfR5D2DvZDc", "Apdv11S_k3E", "EPeEV1IBRwQ", "oJzCC9Uarks", "sWM7645lIUY", "ceS9s8Gf5_8", "H0BoM-iWR-Y", "qOVlVh2eRf8", "RaNZqkSkKXQ", "tITC_86CvME", "vaoUb5bkGLc", "KAxdByoKL8U", "fm_gr5kKJLQ", "TM-yg0axykE", "SZNWlzupNoI", "Yx_W5ikIlsA", "9svL8fyt9FQ", "5GaXyyfd98g", "jNtZUUBvfDU", "eLspqF6S6qU", "kLHSbnjLiP8", "VqcVVHrCXMc", "9alLg7TcwQI", "3fPIn6o82Wo", "y6nEpWXamzs", "2mW-z13JHRk", "Jg0XWxxY-Qo", "iZb26Du79v0", "zEvk5NgQex0", "5nh5lJoQ_Vw", "UjZ_Tn2R4Qo", "BNAn0SU15bQ", "U-oVH_drpVw", "ljtNetJjSIg", "HawfbmyTeRk", "4TGeO9KrBU4", "yaTMJ1k8Ans", "9UT1V88sRIU", "ubQL-iBZ5R8", "SpHeR_dwbuk", "tuOpH_st0r8", "VdbsUV3gCJo", "50KPjB1RrUA", "YWkwnUbKga8", "2sw75W-HPas", "OE0BDT02byc", "FiccOOFEHyM", "PQWNd1JVuEs", "KdAfBIiXIew", "LMhxnUoHKkw", "SXlfzJe5pwQ", "HYTUiTSbCv8", "klzUhY0GBvg", "dMVgeHvrctg", "FS1MbzLOi_Q", "TFtwQnnGcgY", "ZoOqOmUSUO4", "rIxqL_2wOc4", "XImsXtaM7SA", "Bj3oUupDcvo", "EUjSY5Nm85I", "W49VYJpio_A", "7d1ItsOUF_Y", "CCxoqAZLcl8", "I5XBQS7AAGw", "6lu73HqdqM8", "Y5HfgMUoIB4", "-jw43r8bzHw", "sHmWdr4CxAo", "UK0j3G1HZB4", "XfRJdoGsgPs", "9whcEVscC-w", "DPNlZk3tX_U", "stl7UO5fMWQ", "u43Y0a3-m_Q", "pR7OEEG1ex8", "My9MMXzlh_s", "yX5V7twLKyU", "dPCYav4SrB0", "5SaMmu38d-o", "Pv69kqqHYXA", "b13sTVVylMc", "fuVlbIlBJf8", "L0Zf_1cqhnw", "guY2H772OO0", "3WIGW0EzvUk", "p9OO6_uXoOY", "6uShgTYQCKk", "0CQmutr3xBo", "02Fg0c0bUb4", "Gig4FgJS_Hk", "DyD99mDz2AU", "kuUdX8Qb728", "ixaS23EZpfI", "VYNyGu3D0ng", "DTKKiv0iWyg", "8TK6yguoy_I", "Rhkk1ccHp1A", "3EBkkeqbMac", "CeTRqItSlvc", "y49jRsM2Myw", "4PBvHtRpAGw", "cp3VM6s3u-s", "5QYRrSKo_6A", "TBKInv3_37w", "xN2VMe1-HGY", "RbWwjKG6iaI", "hJdB9Ig5A4w", "S62BUO8p5y4", "iEiw2q1Qk64", "h2KH-cUWWS8", "4iYbsU5qJGw", "uJuQo1vyYOc", "DM9uRx1Njn0", "gMBU0Foytmc", "epVp31pmFqs", "1Y7z5su9c24", "fKwbRcdSWRs", "Pg7JLiF2L_4", "z8Uwlk-cUP0", "KLj1fOd1uZ4", "uuQuWxWfoUE", "GbAJuSq3uy4", "S39H9YdAf9o", "GPZwnURJwOM", "NfWyURjkxo8", "6e6GFifJ840", "RgvmnuEZT3I", "YT6_TaQ8YDI", "XX50kLxzVug", "CxynclVO4Fc", "7kBCW-V6SSE", "8U_Yimtch5U", "d3Gnz94jjKg", "EjjgPrfTN2M", "R8_i2pFJNO8", "SfW1j5Ye4aQ", "R1DwSD6cPIg", "zm-4WHgLeS4", "-0tQQl2N8Mc", "Tgtze7bWZdU", "-q5D5izEYnU", "vt-l4yKsw4s", "He6oqAh6RoM", "4QQp8Ic0awU", "tA5GjeaXrAM", "cRP6dU3ESgQ", "pu9zovDzMas", "-mQ6WZUyaBc", "Kts9P0A14rQ", "L_xmfcIuZH0", "Dn4w8asIfMA", "3pEr7U8LxD4", "56L1R6W-5XQ", "zD4c6lu_1EQ", "Iux9nLk5c6Q", "pfDvgdavNvs", "aXXMA_PPzms", "P-fEj0Jukgw", "-VFYoh1VB_A", "taS_bEhOKOA", "i5r9pyzubdg", "E4-KHM2oZ6k", "2sCJPGxL3fs", "sa_XnCoKEqU", "Jr-f8Jr2sJ0", "0l5yo4uL2ys", "ww8Bv_0ACZ4", "UDMfiuEMSBQ", "mQnfZEiQ_JA", "r9P8Yfcjuxc", "tz_EknC_AYo", "ajYZjLG1Luc", "GV71VVckRIA", "U5ei8ih8qRU", "gIK6HV_75TA", "8JlQ9E2au_4", "HhFvSdZMz7g", "ZtIX__gMhlw", "JDrz6WyCBBY", "THH5NUHPTTc", "3KZbbfKV67U", "t1xfRagDYFw", "28Pk2Ho97rc", "90tpR2uhpZQ", "ufpDCifdYfI", "dqnyTGOoBqU", "OgSL6JkZI9M", "XI5Wm6CGeV4", "AIG7h094tEs", "zWowLjUgDQo", "Or2KOAw8rgs", "rFvHK4xcXOI", "l0x9IKEOVC8", "Egn3VSe0L0g", "asXGKN-y5e8", "Nr8EmUwbMAA", "-ho_xjYSdnc", "Lhw7zyQvG5I", "fSY_LN0HKkA", "qDgLw_1jglg", "pVCoFIFuxeI", "tySU8Qdo2ew", "k83pJvWnBzw", "qgkPJukOm48", "rnIUqGQb-wM", "XyVmIKU84Us", "jwAkUz-1Qww", "2ytM3H0AOck", "Bnvt1hxUJrw", "1Tkd18qEwEU", "dMkClENb_3w", "_DXwVIIfK2g", "4hRe_dZigh8", "ZAIXEVlKHyY", "zPpiSloZ1Cc", "HMozHkwwYUY", "8AWEwpkp-Cw", "2ltJmgDstn4", "_O4QbBRPCaU", "fn8mM9Yom60", "U3SkSKEh7K0", "kefACDNkREg", "0Us2R_F0p4I", "Yuz6QUjx5Lg", "bPRj_VRZlME", "4Pqyy1sSsUk", "y0UxwJ2TokM", "fq57DWLtanc", "UtdAYPOGODI", "IoeQ5OpJUyM", "H_BtsvkV5jI", "Ws0j0Dvp9Lw", "vV-RdMDFyY0", "Sk1lfd8KcDw", "7zB5mzIpbwU", "ylT7-8ibQPo", "dKQs0hljfao", "PBtgde-oD3A", "O9fXWH7oWns", "wDoBTrb-n8o", "V9q4s6MVwN8", "K6_zQi7UcQk", "xYdjTvSmNJM", "mEeTPxMBMKg", "bSKgTMjGtlA", "eAgAAcznb7E", "G2ZISFze-qc", "_k32mwS07LQ", "3p7giSgY1TQ", "X5NrZK5N7Fw", "oMtowvmUaBA", "bUG42Up7APc", "B0Nqzn6l3nM", "mRzRb5pQO7Q", "-Zly-oMYfh4", "9DTO0Rnmmq4", "3K74WaFqQBs", "pOhHRXduVbY", "UnZ9yqomBQE", "4Im-dRlW-4Y", "qekwZ0MDjnc", "_w6egFE-t10", "vhjfF7ubgVs", "_w8qJBfqlmE", "ahWSedjxiJc", "ST0sGmAnlOg", "Wa1ch145mxY", "EUnTUo3ERmA", "JQn45Ai9Tiw", "k7EDmvuiBZo", "eieWdakcMsk", "7zyrRDVNzYY", "cj1AO0F7xuM", "IVLfybj80RI", "ACMp_fhlQPA", "d5uAOd8TmZ0", "JW6Fuq6LgF4", "BEk5vBOTwwc", "C9r-rTQ3FrQ", "vH7gjH84ZBM", "uICi1c8DW6A", "GRCgLhFXnv8", "d7SkoRdb6Gg", "S8lCqYPCFMg", "0Tx3sVhi0As", "CBsoOe_BsMs", "1VU9DOgmUHU", "sGmUQscEY1k", "sijwTqoAH2U", "ZkwQU-m9gNM", "zHpxXP2_t58", "e77mzbEIUfA", "tFdk8JAZQlc", "zUc4nwn8lVM", "nCxwpPesYMA", "r-oqv2bqS2o", "g2IYEF-CWsI", "JVeFxBWfiTk", "UYGHXmUcN5E", "0WPLwC3Hxm0", "DJNJ59erY8M", "0sDFyly6qi0", "1BnzXoNZ1hM", "a612vEvNsXQ", "CFpovL_6Bts", "NtOam8KNA4Q", "NKe7AUtU37k", "ZV7sB6JQ4Ps", "VEo_y3HDE1E", "QKPeVHQ_0p4", "LPIVnTre1pY", "GapGJbGAKug", "HaAbAgcNg6g", "GMctktjG2tY", "x71DS-lnuHw", "uIDXE8gqP1I", "lxUVytC5tlk", "AnSh1HKvEpg", "QGDs6E826DM", "Rml_DmOB5o8", "nzwiH2MicTk", "qXczapGkFao", "c6mod3h1XLY", "k325zCotoM8", "47TXahjpRHE", "AqU4mYlK0og", "JkRc-rH5s5s", "PGOyyYkZjnc", "SrPqYoLj4Vw", "HchmJXKWtaw", "Aor401kVAd4", "AAahn7XIxKA", "zunMfbwU_-k", "KwPV4f6bqc8", "p9iPrx4OfR4", "9B-s1P3Pk1g", "qQWwDtxpb-o", "dz5uYmlhhik", "UAlCGDGG5pw", "gW-DlFDrL7A", "Pv1FVy6_epU", "sisO6SSel6w", "b_R9d5kCyBE", "KqIy-THQTQ0", "c9oOpHgcbeg", "X394AjfNoNc", "alsdz2QmLho", "lw9tKWMzJA8", "tRGLkRzUvSc", "6oI_Vgjo_Fs", "-yn44g4sGxc", "KvAUGr36Lsc", "qeySRme-NZI", "AXZZFF4x8AE", "foskJur7Nao", "k78OMMDouEk", "EBqZs0ETpH0", "3fSKptZLMek", "Csee8mYqK8I", "o4KV7fbnZAA", "VTrnqwG37x0", "jJ0Fbt5y7ZM", "UeHihVcHff0", "6kn2kONaNIA", "xewaZ-ZU1bs", "TKToIJ0pDZg", "Dr-HW74qVgI", "r-wWiqeOccA", "GS89bM62dqA", "OIeS22yaqe0", "wyOP74tfUdI", "wobbhVtf7y8", "OZKu8p9tQRs", "PaJNXaqmqcw", "XVTQFoFWDB4", "lePbu96Gl3U", "SmTLBoI4p6U", "PepMHtwBH_w", "RSAo5aQxw_Y", "GttepVXauRs", "Cf0B24bEleY", "1NSNJ1hzzAo", "nfWMYwFXRtw", "AEQaW8fzvTo", "04XKYV1R_T4", "1N1jdIbfAJc", "cPzS24RBpBY", "H_xTQ5tT9aU", "_iuezYTMHQQ", "IGBbK9nnLlk", "WpegS4wyyNE", "iARUqqVwibE", "H-iEAUXRwxY", "kwY-_LUWIWI", "AFZplSzAauo", "V0NfSIFxDNY", "xyJm8DXOJfM", "jI-p6bHpqrA", "W4LHi9Fhpos", "3V3z37aajYA", "Xadvu_fE-aA", "r-oNWd-EAbw", "s1SYx1Hx3Wg", "saCIN2j_sCc", "0qX8cWx1LVQ", "iE1NPysRdBc", "Z4imOBEebzA", "gMdd-h3Gtmk", "7CPpA2l7RHM", "FS0HoaZsb-4", "uLTW09ttPZM", "kx72QVBz5yM", "feXWAMeJXUg", "74ke-vRUzBo", "VtsHDO70mn8", "pomxxAUCOe4", "VO_dTkmq33g", "eAJFTymC5H0", "KodQt8tjBBE", "sQ6WHEkTXqk", "euVJ-MdesCw", "PdgjJ_8tU5o", "rUhYd9Rht-M", "GPwC5PPOZfQ", "l6N8AL6PunE", "LP_ddjRxybw", "TGVg8M3hFZw", "yWi8vdQYH28", "e1E0KVeYvRE", "yHOB0XTQSfw", "4xpccm4qhZk", "dqHEst3X6dY", "mJGFz_ZJDH4", "pE27oY1qt6s", "AHpF_fkY4zI", "Kb_HTXs2oOE", "jI-V_xqMwZw", "ZA6mf_l_QMo", "13_WisynKys", "suVFfpk89cY", "F_fKb2QmmTE", "aEee3PZFpNM", "Mdm_FJ1zIyk", "nLJbolTBn6o", "pF92wSIFT5E", "SMt4nHumKfY", "yLWAHGZB4To", "CzwaY0UJV08", "JnH---gslm0", "IoblPayWP7c", "jWwBotnG8dQ", "vrQsgilLIgM", "dToKb3qdAdQ", "lOqusiho4fg", "uYVY4rcfLwQ", "XGt24QeOB10", "87QOoBaTVxk", "-cKUXT_nVbc", "V9-q2nQSKPQ", "PX4zNZseNoc", "dPzjAKXBz_s", "yYaw-g9hU2s", "HEBicHcj3P4", "NR9oHJ4LA50", "jnE2bGkF2SM", "ScMgxfoA7ao", "eeFvFhC9hZI", "AHAFfcnN_mY", "D-dHW1blgEA", "2UOc1KV77OA", "2htdQbkXxwk", "UtxaJhAnel0", "UqkEOnuteWs", "SnHzk866AnI", "709I6c8BaVg", "6d-Nma6H8IQ", "Q5Go_MhJPPw", "7PUMnjMyX2k", "NX8Nh7g--vE", "N7n1toQE678", "PhItNtcEXjk", "OfsuTfROyJI", "1aZyV1ThHjc", "7JygV-OKEbA", "I3vZZeFVDfc", "AWAWPwg8Tq0", "EVpY5DE5Cac", "-xKtWvwHp64", "CE16NEXEmRU", "6c83yisSAL4", "mHY8BhjN6hQ", "ZlkYrZVnRCg", "iYGIRX59aA0", "pzvH-zQma7s", "g7PVO4czjDU", "26QV5V7OpQw", "Y-kmJ3kTcm4", "s5uvyD8lxh0", "co1tXanXR6s", "tQT3XneAvBQ", "VxxEcNZrHX0", "apVIq130_dc", "IcNm0ARUdhk", "rF8SPOAI8CI", "1a0DDyAt5g0", "jEdZxSbJBQo", "TtUs8GT391U", "10nD_btXpak", "UagG8579VmM", "lBlPjuoshrk", "QpBKEE15baw", "NI6muhADMLI", "3mw1DeAbsSA", "Hn9XS8mUapk", "SF2_lKHW7Fc", "VmudbHCc-es", "VViXySRdZm0", "IgCypELJ-vs", "X0NUCcDtykQ", "s-jfKtICnAI", "RGK6na3pdro", "DfmjUba42hk", "PMdvgsAoadk", "kEpRdJFsJaI", "unXY_t5mbwk", "BOzAYbUazGg", "Z6T8M7PQFGc", "uI7vMNccd44", "kCZSXNqU03w", "0IDvQfgpxjM", "Hz4CP2Dzoz4", "F-5MRrQNNYc", "TKNuFY8s6fM", "bpLyUp6TObo", "ECqe3C7JXnQ", "iqrzHqIaNa8", "ygbeWbB97is", "5cNNXEbp-ac", "2fKVqYgDa7s", "3lR8sYQghhA", "SJJO3I8rtdc", "utISa6L9M_0", "V1dfCjZKHC4", "tSA8PkHpuTs", "KqabqhcStug", "QAy8Orx5urk", "iPraVqkhsD0", "XGw5FRLEsZg", "ZRhgzx3YV5k", "kjaiTKhLU8Y", "N4vZOJJyUkA", "D5Kh8LY7D-o", "vPT0ksAifjg", "8et2L4EIR30", "99-em0Z-g6g", "WgSj-kJtNXo", "8_UOwYM9Eqs", "ikVa8_niPsc", "d5zjz3ZbrBE", "fmd42OqBCUk", "Cob5x1mPe3g", "vOJuLutxSuA", "zvpvKqzn4qM", "gD-2BCaeYwg", "d49kAfWaaW0", "9A4b1AjA4WY", "DzeX6HlPVik", "N3-mcb1J9rs", "W3QNGld0N7s", "KoAU-H6DyCA", "iwxfbT3NF-c", "w9lWRyrAWdA", "QUxc1Ipu8Zc", "TbwfT5HOVlI", "K5dHAENYzb8", "WhCteYXcWn0", "qzkAt31Hnxo", "1bHa-cv5ZFc", "dJaCk731uo4", "5NGBAsbUKcI", "Lito5QEhO2s", "H95fZVY1-Fg", "iAZA4svywcg", "foKXmZaLNYo", "NY4ekUnCLjE", "nltSnLYxW5E", "beAHwV6gzkw", "A-hYpPkoUF0", "CU1a5KBDYrA", "eX7brjGsuDw", "WLNlQBsV2PM", "eIFROEXGVmk", "h2EaH95bTBM", "3zbOGuEOBjQ", "nEWSYpgYPH4", "q67jgUolFg8", "lHNP0dCB5vQ", "rdRCr2z8sn0", "0JhWjbxSIYo", "MiT0CPpr4kY", "750gTR4kCsk", "icmxCZARpco", "ntkYxBtnkus", "IR8OiArXU4g", "bgsRCob5T_s", "2has3tdO2-4", "-bTf2khs8nk", "7Q5ryHlX7IY", "cfmPdXBPDno", "60H0fhZSSZg", "NVtJrReI_Dk", "eXM_Y0K7s50", "9gp1ZQWlOKg", "kRf04rT5J6w", "N5mbm_ojL2c", "lxo1XjInOXU", "-GsscNdymTw", "eMwNO6o-_Gc", "Te5n390H5uQ", "flcScxIMMbI", "JxUDurxChc4", "18wfamYcjcc", "Cu_oIamtGus", "LvCxa95W6Xg", "rOK_Pb044Vo", "JP6JLY4qvw8", "zZDfxOQbq7Y", "tg02_e87lOE", "HPRKqZYgA-0", "CcegEOvmWhc", "sUa9Fhv58uk", "ucUY5zU4L4A", "uElE1k5IBvM", "a6JlifEql-A", "hFyO02qroVY", "YX_UctGogxs", "CYblEQiJ2WU", "a4sHk0u9Sss", "QI_tGmRox2U", "baI5lNjAP8c", "OZdTx5tGBXI", "nDZHck0SeL4", "jmGmIqdNHs0", "s8em-rZRRow", "kzYXEYIXLj0", "HxUekYOuirU", "npbSHZwtCZM", "8_7gxX_DzQk", "1PSRqEaHNnE", "18Z7gAMZ77Y", "tRdzyBxAXeU", "S-f3z1BPY-w", "3TqZ-GDp_RM", "gP1FzqnLnzY", "W_ZwbYrJgug", "B0SUlwWjpAM", "TdeWlXpeRRY", "fJao_M6QQEI", "31PrfW5zZ4w", "Ryv5DXLDngg", "HLbvsmnzqIo", "Kbh2ZNq8KJk", "_odIW0gsK8Q", "9uC1r2eR6p4", "UycAb6eYT4Y", "dzHNM7f-vMY", "TpUdJY1fOVs", "CrnxQPdfxUg", "IFB07PBxOuM", "TCskeoseG28", "ev7PCZiudzA", "jDfGwtcJviQ", "dLUMjB_Ot28", "S8kBf0paCn8", "iFtYPIKkHK8", "JjvMxF-YlRU", "_5iv7_SL9Jc", "Zry7rJPV56M", "zTsPeqsxZKg", "Xt9S8lC0Cac", "P1HOaBKN_WI", "hZKZSv09me0", "mZZAjbnlKPs", "xbBA83dSPuM", "CEy7tg6w0vI", "mPvVztBYOEs", "YL4_IvyQpYQ", "fGCG825j9y4", "j4lV18I_JAI", "0WSuvwU6psA", "IN9T9yiu3mo", "4I5aKhHZJdY", "ClcWQEvlmBQ", 
    "5qia3eSUulw", "nYrAnBkQ7W4", "A8OBRccd0xA", "7W4wLYzA_pc", "s2-NhBmldAE", "5jb3-iaQmLo", "aWwHvEDX3eM", "VpZpFUQqm8w", "OE8Ric9PAEY", "JNc9DFoN6mo", "on2CjccSrbk", "KXYcibirzsM", "snYXlrVIdbE", "Yto-SKrneqA", "gdaPEe69580", "uzAABLO6YL4", "YvPZE4q3Pr0", "GVDCTjhbgbQ", "SrQ9uBtWDNs", "XwbxQimhRns", "lJLVM8njlFU", "BsZLBqzIwOM", "LLokuiq5MPw", "IvQEw4SJSFA", "cn-7MovF7Jk", "Gm8qZI464EA", "C2dXDeEV6EA", "lbETZ_nF7bo", "jVcO0vymVdw", "n99n40QCHhQ", "WW8t2gvh_14", "eXN8UU3qxOY", "k1w2hEd6Iuc", "npPTIj1d_EM", "i9QyaEtbHos", "G2hHKMBquvs", "pHwAvTFVbxo", "I4AWdQyAg50", "skCIuxd8SZU", "oLjsue7oZe8", "M1HF_t3JGbI", "J7j3FGu5zXM", "ilfTObEBigg", "65Nmrol3MfM", "_ce1XgremzY", "kYsV6LXEVHw", "8UmbWghnbIU", "BVAEWKA9rKI", "kdWSJxJ3Aic", "SNyTx7HNTiQ", "1lUsKZmAL0I", "83-dR18WfgQ", "QVIaPGC5TH8", "Cny73X_O0k4", "cwamPOXUYiw", "wZzC-2cmxY0", "tEqLPA1GFRI", "g8C3flHVjAQ", "06NVynvX2Ug", "7Royjr4slcs", "iNkILO8y9dE", "kgjaT2fmcYo", "qx0m5wwuy-I", "EfMm0aqZ5cM", "oq4ZFrhsZZ4", "zdbIbXVLX8E", "a24apmYQW0E", "VT2peRq73SU", "gJesca8ScyE", "E2rVAKlrlxM", "PIa4t0FxcEE", "mkitNAK3z20", "QKWaJMhTVRQ", "HPX5MNI0ARk", "hPW15DSlv-M", "KKOW1w88MZ8", "zWDGq9blwx0", "utAwIGgH1yM", "yphKIGEe_9I", "41DNDsz1wK8", "aZ_IlwAmigA", "P1CxMowiuaI", "K_xjO0Iwr1k", "_UC5G-O6KK4", "Lk9ufl0xNaE", "yddcLK9Vyho", "QhQadrJsOUI", "Jay6vNEgBfA", "2rbsHOTuwtA", "xNJoDO_4l9s", "Og3M6DMPlnY", "VvefcAmJEy4", "PFTV3rj9MgQ", "c0Z69Pkkvw4", "F5kaHlosGSw", "aUgdiXkpxBQ", "OnODIURhoPI", "dRUyqZTJKc4", "IUjfvrccCzo", "TueK8WP3Wss", "dCXnEGzEskc", "fsSdOg455G8", "ClydVkXeqIc", "VDNoIEqqlGc", "qkCGE79u9EI", "3mk_L72mccU", "gQo9-Tkwc0w", "FbS8WOMJ0SI", "I1HGocPD654", "cQwDyLH43SY", "KaP8yRZOZZE", "3y1y-fpUHeM", "2ubBnQ5cnIU", "dEEZqQfyt5A", "Cf3c11Dywq0", "Edg5_92JwA0", "to0O3kzDT9Q", "XgwQMe0dt8w", "BEPgXyHNwEY", "Mg7wtkuYyHU", "jjbpGnT2c9c", "-OREP4HHOMY", "cEdmU8xKXVQ", "lN1_njJ1EE8", "wQIvR4naniA", "GfQRtkLJb4E", "Zt4pMXn97TE", "ieMOix1IjwU", "Wd7UA6JWlCU", "ogQ3u_JYHkU", "J0v_IVnYQBU", "t76y5nyHG_4", "1bejU4klvT4", "oK0x-9RaUlU", "wR5TDpC6acU", "CS_vRAqHyzY", "n-32DoVOsqk", "Kws5Wf5tYHU", "Hfl6W_G5bvc", "8-cxjvNjZBY", "4na07qgJiSs", "01_92SIpuiU", "vgivydJAI1Y", "7GT_Uif-mQ0", "S9JwOBfX4Gw", "N_TZWyR4nPU", "xyGxUdtsRRc", "pfuNnByxA9w", "Dxl45dIhkRo", "lmr-RlaLhZE", "hWMSZvdUVQE", "RSXG2DQtKss", "5c9Jrnk4xFk", "u_AUQ-Pv01M", "ubUFGxgpq1U", "Kf4_H62DKcY", "ZmsMPUXY85A", "TjRzh3mCxBc", "4Fj-oGYqExI", "AfClFbJl8-4", "HGYOASiMTmQ", "3BcPJOlbMSc", "_fxLIdgda5Y", "VVHetC6CmP4", "bi_0t7RXDk4", "9P8mRMA2R2A", "yw8IMuB9bc8", "etfnElePwlk", "2m1iDT_TxxY", "ovOOVZmhjpI", "ZTFshSSjmjI", "4RxYGZ44miQ", "0a6uD7JP_1w", "ajoYJ6VS8Mg", "L7Cp7aQ_xz4", "HIUD03o10nQ", "xrjhYf44Fik", "9rURvjao000", "NzqGDwbjv9g", "xEsXc4iBl8o", "MAgaWM6zTs0", "iI9XGE42-Ew", "Xj18ruQhdSg", "7ViWDIhcX7s", "SYHkaKnCwkc", "85TcFIq439o", "-BQW3PCJuLs", "6yL_nwdhzk4", "vqmA7vj24S0", "XwWZ9K2lH7Y", "1uk8GZyFNMs", "h91Uw945QC4", "oQdx3PcFFIk", "783SRv4RpTg", "b3loZ79bAcw", "l2z899yAuHU", "vo6UD4deKcE", "ArkCO4H-aiE", "PEIRBF8tpB4", "VQcOgSEJ3YY", "j3doiyKSC0M", "MHgXD-P0Gs8", "BTYsGeIZS9M", "49zFXVwjmrg", "6xQR6RKABmY", "40kiXe9ss2k", "SXKKUgZJQAI", "RM7yeYUQmmg", "CrDldnCFo_c", "TU1ZCi8eQf0", "1OvcdYsihiQ", "X5gajO3dgQ4", "7iW2chAnVpg", "2IZV11EWywU", "4tj4R2wT97Q", "TLWSOQBnq0w", "k2scfm5KDPM", "jqs8S16hIW8", "fs8aSnc2URE", "85Qwm2tBrrk", "RJz_XKElFWA", "94xpvvQkYKc", "SmK6h2u3s38", "4EEPJKqXRt4", "ZZyfLgjrOME", "Xjr-ydrjlzo", "chWCM9PN3GE", "3aj16WDC6Ww", "Uqg4mIefoC8", "uJsk70bQsdc", "GjZIN2BMyC4", "-z1KcrnoU8s", "d2C0CaMTa6g", "RIKHnYtf3-s", "ZRqCIDylVTY", "NMc65c5jXA4", "kGBkPZ4c8Jg", "nIwgCAWNEaY", "GvrSFfRa60w", "3bSN5O59oPA", "5MFoWBUAYJk", "0DpYBBjTdvM", "cD_R1FoHsr0", "Q2DbCAEcKeE", "ncBz92xpFpE", "p24fufhXXgM", "O6UoY1qgFGw", "3jq-ZlZnDv0", "JtJsVhJ2zRQ", "Z5koNl0jurQ", "emy8xmMvOwg", "ivLxpHksXTY", "qjB8fTWSbTk", "hHhnVi1DyI4", "48iPLaXL5Gs", "Qeg_aHs3JXA", "OZavINjCU20", "_UM2ZXl8xo0", "a3TD1yrwGRs", "_0pAexrqd2w", "DaV33FbbT-Y", "w5feWRWCzQg", "Yt6bhK5MA4w", "D_-514_8uWA", "G5d8nOK4YMY", "Q5v00mM-FPs", "yP88cVOOF7k", "mBQ_owznhhA", "hvIabC2D83M", "e19ReBQM_9Y", "q5hmCKwEEFY", "yWdkGyfk5tY", "yDv2MqneJ0Q", "MqbQxZYZnNU", "AJHNKmzRG4U", "Ve2R2GrZgPo", "7B_7dYGYMLc", "-Rj_eHN2oOM", "nGEPe7lAoUY", "WHVOkgWgZXM", "r-jEqRQ0_rw", "EM8bry7wFt0", "8-yJPp8iCqg", "IwqfeXertF4", "tWaQhZIu-Qc", "5xTzVgvrhEU", "Jb7bLI88DEA", "BY4RFiZoXfk", "9v0gOwnWwkU", "Mv8tDtkHbgc", "9fmDFk_Y1yY", "iZ3UxsvSvR4", "YOWgJ5tr3ug", "Ba70P0B_ciI", "vM4ffGDTccE", "eKJlnny-EwQ", "GNoQjqt3QI8", "w9fdr-KuffM", "SI6xga9jxr0", "8PjW7tYuCSQ", "Rxx1yhr12n8", "YC9ttxJcIdY", "Dw4GXaRfyqg", "OwP0TVKF5DE", "ycX6hLhqpGo", "Olu14X-yL1k", "Nzw93qoWXfw", "_2MRf3Amk5k", "nnWtq3owyPw", "0yHDr5mHbSA", "Iag_FUdeSbE", "T0B-qXyJGNg", "-q11JdAHMok", "EjjhR3m3jSI", "f2NoFMbq-rM", "8xaDgT-BUTE", "S8BqIKW9I1w", "IG-R95gGifk", "W37RE26rtmc", "X7I0CHSSuzw", "Golj3Aj84X0", "H96x22g8j8I", "xgO6hgCxQ_Q", "PZxcna7_vqc", "F2qM6XtzrzA", "IDh3bDJz5Ac", "Pj9x6G0UwkU", "CJXicOPA47E", "-GP4PeecHh4", "Mp7_4Hlbe2Y", "I9_uOiLN4NM", "JnPVUHV9c_w", "2PynoZ-2OOI", "ppOOtU9Ms8o", "DfwjVKr1CK4", "0JDfnUa-iRg", "0eLZ7X3VJhI", "WbvHAlOoXQ8", "Ws9y2iDz8l4", "O9Kp1_8lcL0", "e3OfY-XGemE", "f55Hqpfl36g", "5pdsZorvu3E", "C_mp-5rsCDc", "2f9pz7iYVUg", "qGzeec_tV94", "jqgPB-EFiCY", "oL0xf3_IbMI", "6ERXQU6A0xM", "vjIsxjL56jo", "W6g5h8b8NUw", "TwT0whIYg9w", "215g1FTJ8OQ", "dggfK15Nvek", "G-KNc1svRQA", "7Qb2rX32pZE", "-s7ndhGzIls", "W0-0M27VMas", "FBgttpFMePY", "372CEehOgYA", "2MoH64EQdRY", "v-ax3R2JieA", "CIYZiPy0mTk", "ybprRZ6t148", "fsi7cz9sUMI", "-asCO0ZKamk", "Ery9mq6dJRc", "dKVMO1OzQVE", "h68vKBZ4u88", "8V9ocrN9qMc", "yy6D-OUhxTw", "pRkqaF1VqBI", "Q_HnVkdGpTU", "Pd_I1NCUn04", "ry8CpdFidxU", "xNT3AYuKL_U", "Z8SI0oGmkvc", "wzsZohi6oXo", "tUWGq90JEzQ", "D7CqOS13tbU", "vAI1jxMp7ok", "qva0hA4QKqI", "6UWFk86SQ9M", "y7FB-MxiMHE", "NEIoFgq8B00", "00b-NXDMBpU", "vKzkT5g5U_E", "vPhlKcD3ha8", "NIMapZ4otm8", "x1MnvjELNUE", "D33oObXn6Xo", "_JCWud08x68", "Ap_GDpIOmVs", "q56bKkTY250", "wiFcfdI3iXI", "_wh1PyfhIpI", "P6ljOuqpKFI", "74a6ZsK8BB0", "EwWq9lWDQfE", "2jSKiMtl2us", "SmNYdreD8Dk", "pZIGyJqQCoQ", "f-iWiPiA7zk", "I4CtRIytiW8", "FFAaw78R5hY", "5HL-rPtvUBw", "QSDzTk_O-0o", "x4hvDRQFucQ", "cZc04Mebdhg", "4Aw4j85ZXWc", "_FwLAzZlEAo", "FN1m3OfNlWI", "mgP3OfX8rrI", "AeUNXbsCoYA", "CAsd18BDyWE", "kg4EFmTL6p0", "FZzzRjhKQP0", "IS9VXMg5S70", "wS_H4obszm0", "LMwsoHQjQ9Q", "-09UGQD4Uvk", "0SZjKr99oQ8", "RKMB3OAdw38", "HYLfLCiGfD8", "7O-p5BrOfrg", "GwQwDYkzIEE", "XfFP9dWEmmE", "plEm6iJJFl0", "AkG-acXxLZA", "YQwyZpwEbeE", "ShDWRGIw7sg", "H0lsreY3qWM", "b3iVnSF7ACQ", "AGQAeL34b64", "bMrbfR5lQoU", "r-FDF1rmGos", "-NHH63gSC8U", "_NzqIp6Io2s", "BkX7B0gtR6w", "KtE7XHflSKw", "B4EdvkwXOOg", "JGB4B-yjwT4", "sNLwTJLOmkA", "Nrv0ZJwNROg", "Y9GVvm49n28", "wUZt3nBttaI", "5zNRNsB7z6w", "_KBRpumbFtw", "DKiHMNBh-qY", "KfxhiMp1N9Y", "4kf8qGf-UFw", "nGXAvi_DCUc", "S6RXs-tDwqI", "zIIuz9EpVV8", "_aEOFT4YeMI", "_piCW249r2c", "4Fveaz38LKs", "L7IjwY_RYuE", "xIpv-x1BI60", "o-x4mwIC6dg", "0lfSCHnSY20", "wkszvT6W730", "aScFq1pOJ9E", "_BfK0bASaOw", "cwxyKkJarTU", "7lcBMUh4sJg", "BCQr88UFQRo", "L__1HfePIb4", "RrBt-N95QcY", "KhLhC5owid4", "MZ3A2ae1jXQ", "y0z8obT-lzA", "L2GCoH91UOU", "xo7JfggwWoE", "O5JYYi5FH48", "ZXfqDmmGvd4", "yxdFYsuEV5k", "7rEVEsCBxRM", "cCE_w7P4--0", "3B7pmAR6bFg", "0XMiDmVyG6k", "8RspWZZpxpE", "ilR0cqJ55Dw", "ezzvk-DFOBI", "LfwtOFvPDy8", "LjYvx4CzBhk", "jKOKfjnGiyM", "V0TB7QGb0ZI", "rBOVNxXes5Q", "ygtZo-ZemR0", "sAMfre5dx1g", "jF8P3phGcHY", "0-IKro3eWyQ", "HiksbaHMMck", "JT-rzc0nbrE", "ypF-1Slmmx8", "NtwsCZ6zDys", "obwgAytAPH8", "AxeJ0DiSoT8", "6-UI0g-2ZkU", "zYX3iODGI8I", "dPmWRGaYHZo", "fFBeBb36Wx0", "NVLzwBuvRIQ", "9bzo0_Vx43w", "5nBVVgdgH0A", "TlMb9nIeFYU", "XO95Z0OUij8", "1-iW02GzSC4", "R4Q3Oy1TayI", "9Vb4s0e5QKM", "BU22PIc53QI", "0rFyO1Xn5vc", "0XV9TqCvXdI", "Du5lrcYUtns", "Wqhht0t3NhA", "e7fHQz1r0CY", "9sFTW2hlnHA", "8IVf2NtP0Gc", "1MI24SaneLI", "1xPNSXlq9ws", "GM1RH0VgteE", "j02SnoCpajo", "KjwRjKqlZuI", "cJuCXUZye4M", "PTFMSJqVHsM", "fO-8Qqq6D-E", "c3bela-yyoE", "IrWsK6K4UwQ", "eUjUHJwbY68", "8eFcWV3_9zw", "fvc35uOlDcA", "-jBb86VLQvQ", "VxG9au73OZo", "oVn1ZgHzE_s", "7qkJFx8yheI", "I9WemD_H0_4", "7i2sxTyX56s", "ViWq6GFiic8", "DyUHxblt9JM", "IGNtZ6DAKdk", "y2pgiwCsjWo", "jOWu16ZS0MU", "gbFEc856SE8", "jaahL-1eyjI", "xbdiKheYzzk", "MMi9zYrFtj8", "T39jouWCY8E", "p1oAVK7fK_E", "xbSn6h1Jxdo", "ah4_sRwNIUE", "GqKVAniM7Ac", "dZUgUIwTEDc", "IvEXNiMzCf0", "l5N0ASKcu1U", "Jv43GALBL2c", "Zq3nF9e4F-s", "P8NHjA6zgIg", "5anKLmN_i_k", "SF8MneB9OVo", "zt_p_5OnSM0", "I4YNQPENe1g", "5mAv915fhmE", "lCfB8VKbftU", "hfDQVOgds6g", "ry20d3808cw", "HqyZqHmELC0", "DRXTQUnL-ms", "HoMPm5WSQe8", "VYqxmq9-Vx0", "S_a6DRzK5w0", "2knXiK1mFXM", "hXdXor8-m0I", "nj6mm-nEKGg", "Jwmb6WtVe2Q", "GhLZgv2JVQA", "1yZBfkzJByI", "CzRb69Gc1XE", "W77N7_pUe-Q", "Lcl7_2Z_Fl8", "Pjd59OWqXGw", "Mav8gQ_Ugls", "C5E32B2MWCI", "X2A0p7eesQ0", "MCjVzIdYIq8", "CP0K4Xz9MQI", "6qf8UCJqx3Y", "luiJwLbDgW0", "iHnVnhsvWKA", "Ex5W4fQaJNE", "tpWR4Gtcpqc", "fmGr7wrNzos", "h2VYvOhOFZM", "wR8fE1xINmY", "eSMGhD2dCOI", "w6FK0MrsP90", "pE9SBEZ-9ks", "mxZYE_GVYHI", "QbS2PIMZI7I", "vNn9XBsH3mQ", "43egsg1ivr4", "0hbjL-GwQ_g", "maOnuPYvEx0", "Lb7y15pq-rg", "nX1T_fG-89c", "k6CRv4jx3yc", "c6XjQjTRDrw", "s-asXx8wYAI", "jDhmcfQWtAw", "Hak9P-EK6oI", "aNCfTb6SVlo", "nGuJchvXESw", "TaPKJBnRN1A", "LITcD-hqHJg", "yttBWEeIuvU", "gholRyVOEtE", "vnbGCbq1mdk", "shBJVdGguIs", "fR2pTr7v-fQ", "3bJix74gA9w", "qSwQJe9WupU", "SSYxlJsCewA", "rjsN1xoHb_c", "w9p4sqR-TFE", "4fUcoyKY93Q", "rvb07ioeyPQ", "dn3pFyvYVEM", "VnN0UyZ3bTo", "YwzOPHd7e7o", "732L2NJOedI", "9v0RWguLN8I", "V4LxtNA_kjs", "dKgPpEHnUvk", "GdIZa2AeDCI", "6tu1PiPoio0", "oP6KslazRF8", "OJLvEnP1KBM", "xcbtriOg91A", "kM0tNGNO8Y0", "yLv9sWHdJVE", "ynJIqpiAv9U", "XvWYGKoqozA", "2h4JLK1lTdE", "sFXGgIfBtDs", "17ykBjIGXcU", "PZ6wKyCxgrU", "HbHeuZ9VyXQ", "dlnOZjyJvsg", "SmjB46VcE-U", "KQbE42nuPZE", "qc19g9Zb1bo", "U6khH4JMY7c", "iR51vLbsAyk", "LXXdr2T-on4", "Zi3NVMtqofY", "kDiB34jqlL4", "1BxL8nr-WgE", "JiARPOyQ_Jg", "uy_8gHNWmxA", "2coVFIRid_k", "s0WcMPOLRik", "WcUgBBKTxdY", "aU89B2eGB1k", "1GhN7pZ-Wvs", "u8n6qduGa0s", "esub10-0FHQ", "PBG_noSSWCU", "dw2WFgLTcd4", "VAzJZi8G8w8", "QSZc5jVKeWo", "wArFysO1L3c", "PQ6CLI_a_WQ", "zwru7aW9sHY", "_D9kj9sM2m0", "7uCz-3gTuQw", "X-GzSEEhVgA", "sEJ4TqqX-rs", "zeNiMpYGqkQ", "BXTSOGr2b_E", "_KUvvElDy3c", "i1vz6U8c9qs", "eJhONscLjTY", "UZ_4p_zHfoc", "nsQ22t_CsKg", "nGdyNgXU56c", "8ukEBw2g3RQ", "B4rIQ4hxb6k", "ZF98YYIztGA", "M3yYOHYKCyM", "UuSZx4hdzOI", "2gX7Gk8OBAo", "xnp0dqA78JM", "-p1GqbFunS8", "WqL-1_4G4Tk", "sGx2QIfRRD0", "BZhq8FIFj0Y", "u82C8D-3QwU", "HGruXVaTdhg", "q-9yW9NdB_g", "RKdRVH1eB7M", "4X1zHEPL-AU", "FA2T7gHMlVQ", "D3DA-2VXIws", "E84Gt0q1prE", "51q6zJx0kgs", "Lc--nRbKE00", "xO0KurU_gqM", "gS5CCA5F-SE", "vpSV0X6xOXc", "NkdaXj7125g", "QfmaAAahID4", "PcnNjrAm0W0", "8Xk8fQ5-V1c", "iUogolDiJXw", "Xo99RC1Lbhw", "xOvEeWy1Fms", "kvSQSbdKPrg", "NtffpMOsNcc", "pz4Hs84aa-Y", "GW5a5Ymajdc", "BGRNDnqqpSM", "1CcCB87qKbM", "beRu3dZJZa4", "eI0DYwaEX8A", "BLAZx-mezaQ", "GZLSASd0P8w", "aBdjQic8SMQ", "elD8v_g3RIg", "oItEmLEDalc", "oS0C0oFRLno", "P0r4KT8DCsw", "Nugx5aQ7aSI", "ryCltBia4pY", "qpMeL711Bgo", "-53h_nj1Odk", "_O9UCAblkws", "7ka1Fe8ZmAw", "DDi2RXnYIFw", "5Eb419rlM14", "-pLscO4NxQE", "aw3dtqDP6v8", "N72JYghSxds", "RKVlK8EEnHg", "Rjq2yGbwKVc", "0u0wlBVfvIA", "A1bhu09G4_E", "BKttaM7j69Q", "Ivaq8bpoEHo", "YR4OgCvy9Qc", "WwDeY61YJT8", "x-siuG-k6ZM", "3dnIgmN8DTc", "ig2Gy1bZB7s", "H0R2tiHwZ0o", "3qgI_GeOx6k", "hrERCbNvYto", "JwzrFj4oXY4", "3hln65zbaBE", "07OATbtm8eQ", "QtPOn7WfTtQ", "Eq2xJZ8Qwss", "REpz6nAIILw", "dECESJ9MhpE", "Fl-W_nbILjo", "mpSeXARm290", "nxpaKeloy0c", "MHs3gV3UpRM", "1Cgzf51aAhE", "8_lL4lX9Atk", "lX-5aHL7AHs", "UxFsJ2nUu40", "KLygmghlrUw", "f5LB2zljb7Q", "4gwKvKfKueo", "fViHLaRxV2s", "roQyAhG1RR4", "ikgjGNWlEKU", "zTB6sGwP72Y", "0x-ne0hXUw4", "SuFkKHfXQkY", "PIHrUPw4IJM", "B2kJTuXW46g", "uWhU0aA2Ybo", "EmKtAKActek", "KHxo9PvdpWo", "EhpbKhoIIK0", "Q66lu490BJI", "gRF0FL7m734", "6mUafsp8N30", "7aY23inPDeY", "RPH4kHsHhXI", "zWOEB0isnMU", "Wwo_jkB8DpU", "es39cV3s8nA", "SzLajxquLiI", "AT1ggR8jSTY", "A3ghMKNRH9k", "QQyRQogQ40g", "dZUnNvRuxhc", "lgnxxFJA7vg", "8K6Mw2RPeas", "9dF4zYr5kQU", "dxSXXm_76pM", "hM9bPmLPK2w", "wyxdeAYF_tM", "e9c2v9lNbv4", "wqSgmsSY-mE", "-xiMyag5HhE", "QUg2XaQfeLw", "o2aZFaBUH9U", "hWKhTP7tQ04", "grfqFXJEf3E", "afdHClZvqgk", "FzH4lROIF9A", "f4SBX0TB7_I", "lhAwHuNa64Q", "sW-tYccuCww", "4xtlbun4jJg", "GJyn9daCeLM", "k4uGKmnzEgk", "lkntXS33BWQ", "NOAI_YhEwyI", "QDvYFwr2Vgo", "aHllFe14fOc", "Sxplle-aIA4", "DN6PUgy9M2c", "bbgD94CFoAg", "RhtbmSYK7qY", "FW8RpR52DvY", "Q5rmykA1doM", "wzAQwzbDrkk", "OLoeC5X1MVU", "pb1vAaQMt0Y", "33Qf04E8YeY", "LNW6YwxMp5E", "0fPj_GXImTg", "2LBVjbLbS4o", "0GPYWwULh1o", "CEPQ6zcQqKo", "6-92pserrAo", "fY3NronsAlE", "-lEn13PDZOM", "Hs7qvrjctDY", "00unyUl99_s", "3mndHnAJv1U", "-H4pDTm5_nw", "cYgFvvmpAlw", "naXUKPHmsm4", "roV_6h6ltx8", "CuB2CXNOuJA", "AzA4kIzdw_A", "JeF2nhkZLhs", "ncjMqCbe0X4", "b5qG98TtAPI", "6UwsiV-bj_Y", "hBJJw9Ia6Fk", "ZFHhzAcAWoI", "Q4UGXGqIeSI", "z4DO_HUWJ38", "pqp_l5yyod0", "NErvUxvTNIo", "D82jSvcyYsE", "yTRynEpKp8o", "nFCoDeswvSA", "8SyTgTNJS1s", "Bho46MtYk68", "pz9CUDsRXvU", "CPbc7RtTh40", "iwidYxMTljQ", "u-4aaolgLvg", "yhSq5DkhBRs", "0znTNtzRE4k", "LCFcQuu3t1A", "YLMBsrVhfd8", "cusNz_DyUu4", "35CyIw7TOC0", "2hRT1QS3smw", "wop-AIH00g0", "1689KgzRWz8", "FpC3ynC9Ujo", "hlcYJQJEYrE", "4TgM1lIUFwI", "-O3syUxQS0U", "3X1pawzSluo", "U_nJwdn8emI", "5LFrsxfH_eg", "qzmodB8mSmA", "fDCEFMUc1A4", "hf82J5d41_Y", "geZn4A4W7Hs", "Y42yERh29vU", "tuvUMyiWw2Q", "z2dHX1WZDL4", "_WIYtX587p8", "CEvJiRtSv10", "RPeyUZVDBDo", "ZAk4mqx01oU", "py_HTRnbX0A", "rT-dWkzhTfs", "GVfR0MqHJZU", "USrqtbV3Yus", "kftGoDaxb9o", "omggDZjMw58", "JdHwFPBo9Rw", "-262csKVh_U", "KGxtboAVzaQ", "-P_MDJxGiLc", "XuEgHBpPEvw", "WUGvzSa42Tk", "aDPahLugy7o", "SbJIiOdaZL4", "pIM0apuEP9c", "4NZMUj5RxoM", "MQKZQVq17PQ", "_rwFHdlo0ew", "5jHNApPKqio", "pMusIBekILk", "MtsuYz6hEx8", "xF_JxWT-8Vc", "NrWle9Uajak", "u8m9Yg-O91Y", "Ax_3ahiIjWQ", "bhIlnvseTDE", "nqMuqzgQkcA", "x-V12QpNzKc", "YCEPIMtAC3Y", "elTo4GR0oF8", "pSbPOF8y8kU", "tbHcb6nc0LQ", "7r7JZXrxQTo", "gWxZWfkGmQM", "ED6tP9Je6Nc", "81_VmIoLVJc", "JoccK6moEGE", "bEz3coe0kao", "vWLBCdER9NQ", "Jum56uvQjMI", "yI8PkZLBwZs", "1uKEWU95zok", "Sc792Rc3p0o", "chd0K9HpL_s", "UKx9qQEy9q4", "neD0mQsKNCM", "XwnYKDa84xs", "cioePZpLjJs", "yusKa6a6U4s", "nNRnPGdBPcc", "Z4N9_0gplqg", "Ama-Cycka50", "_ersoEcC3fI", "bsW-gClXgpI", "veRvM3JtUKE", "1M5LVktHPyc", "LLNHUvAILvU", "1l_KUAHCOdg", "yHwLC226Ios", "hWWlE2YKZoM", "SYpyDB2yOJE", "Mzep-wZZOqU", "viLaGjqDOO4", "JQl0Zm9-0dc", "ZXTQnOUSNsA", "tIDfnTkt7mA", "vhNvKU5eI-4", "aefNf_2u20M", "KSTskXrrd6s", "qcMkKx-Glbs", "Je3oPWGie6Y", "vlzVllkLyPw", "QpvrkFQUrlU", "6a10G13nhgQ", "fH6wo05vNik", "PvDrcloXmTk", "p1uebBfEy6Q", "Qt4nGKQyC6A", "RbwwY13i69w", "O2el6Tq8ICg", "yFs-_yaAWE4", "zlqGRCq5IF4", "orJV6ks1Xgc", "T9LTD3J-g4c", "awTYrUcWYfw", "Ao52o03xnw8", "QdpP9QZe5Uk", "nsAEG2pLNEI", "hrGdou7lxnM", "sf7Ml2Zs59g", "GH6jC4pAJuI", "s9rZ0A-xla0", "QQeug-5TuC0", "3zHrH0_bIvc", "xXJkl9Kv2z8", "IjsBbXcWIUo", "g9y4O7Rgo_c", "wLnuH0g9ZSw", "s4CAOMCl0FY", "Mr21fxhhitc", "y9M5HZu4IhQ", "Vjc6nUu2rC4", "f33Owa7b36E", 
    "cDGK68x-5JU"};

    API_ScaleOut() {
    }

    private String createScaleOutJson(String str, String str2, String str3, String str4, String str5, String[] strArr, String[] strArr2, String str6, String str7, String str8, LogUtil logUtil, String str9) throws JSONException, NumberFormatException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(ShareConstants.WEB_DIALOG_PARAM_ID, str);
            jSONObject.put("at", 2);
            jSONObject.put("tmax", 1000);
            JSONArray jSONArray = new JSONArray();
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("impid", str2);
            jSONObject2.put(GETINFO_SCALEOUT_PARAM_W, str3);
            jSONObject2.put(GETINFO_SCALEOUT_PARAM_H, str4);
            jSONObject2.put("pos", 1);
            jSONArray.put(jSONObject2);
            jSONObject.put("imp", jSONArray);
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("aid", this.mAppID);
            if (str5 != null && str5.length() > 0) {
                JSONArray jSONArray2 = new JSONArray();
                jSONArray2.put(str5);
                jSONObject3.put(GETINFO_SCALEOUT_PARAM_CAT, jSONArray2);
            }
            jSONObject3.put("domain", str9);
            jSONObject.put("app", jSONObject3);
            JSONObject jSONObject4 = new JSONObject();
            jSONObject4.put("ip", str6);
            jSONObject4.put("ua", str7);
            jSONObject4.put("language", Locale.getDefault().toString());
            if (str8 != null && str8.length() > 0) {
                jSONObject4.put("ifa", str8);
            }
            jSONObject.put(TapjoyConstants.TJC_NOTIFICATION_DEVICE_PREFIX, jSONObject4);
            JSONObject jSONObject5 = new JSONObject();
            if (strArr2 != null) {
                JSONArray jSONArray3 = new JSONArray();
                int length = strArr2.length;
                for (String str10 : strArr2) {
                    putBcat(jSONArray3, str10);
                }
                if (length > 0) {
                    jSONObject5.put(GETINFO_SCALEOUT_PARAM_BCAT, jSONArray3);
                }
            }
            if (strArr != null) {
                JSONArray jSONArray4 = new JSONArray();
                int length2 = strArr.length;
                for (int i = 0; i < length2; i++) {
                    if (strArr[i].length() > 0) {
                        JSONObject jSONObject6 = new JSONObject();
                        jSONObject6.put("domain", strArr[i]);
                        jSONArray4.put(jSONObject6);
                    }
                }
                if (length2 > 0) {
                    jSONObject5.put(GETINFO_SCALEOUT_PARAM_BADV, jSONArray4);
                }
            }
            if (jSONObject5.length() > 0) {
                jSONObject.put("restrictions", jSONObject5);
            }
            return jSONObject.toString();
        } catch (JSONException e) {
            logUtil.debug_e(Constants.TAG_NAME, "JSONException");
            logUtil.debug_e(Constants.TAG_NAME, e);
            return BuildConfig.FLAVOR;
        }
    }

    private String getPriceCode(String str, String str2, LogUtil logUtil) {
        return ApiAccessUtil.getPriceCode(AdNetworkKey.SCALEOUT, str, logUtil, str2).message;
    }

    private String matchString(String str, String str2) {
        Matcher matcher = Pattern.compile(str).matcher(str2);
        return matcher.find() ? matcher.group(0) : BuildConfig.FLAVOR;
    }

    @SuppressLint({"DefaultLocale"})
    private void putBcat(JSONArray jSONArray, String str) throws JSONException, NumberFormatException {
        if (str.length() > 0) {
            try {
                String lowerCase = matchString("[A-Za-z]{1,}", str).toLowerCase(Locale.ENGLISH);
                int i = Integer.parseInt(matchString("[0-9]{1,}", str));
                JSONObject jSONObject = new JSONObject();
                jSONObject.put(lowerCase, i);
                jSONArray.put(jSONObject);
            } catch (Exception e) {
            }
        }
    }

    private void setResultParam(API_Controller2.API_ResultParam aPI_ResultParam, String str, String str2, int i, LogUtil logUtil) {
        String priceCode;
        String valueFromJSON = getValueFromJSON(str, "seatbid");
        if (valueFromJSON == null || valueFromJSON.length() <= 0) {
            return;
        }
        try {
            JSONArray jSONArray = new JSONArray(valueFromJSON);
            if (jSONArray.length() > 0) {
                JSONArray jSONArray2 = new JSONArray(getValueFromJSON(jSONArray.get(0).toString(), "bid"));
                if (jSONArray2.length() > 0) {
                    String string = jSONArray2.get(0).toString();
                    String valueFromJSON2 = getValueFromJSON(string, TapjoyConstants.TJC_EVENT_IAP_PRICE);
                    String valueFromJSON3 = getValueFromJSON(string, "imgurl");
                    String valueFromJSON4 = getValueFromJSON(string, "impurl");
                    String valueFromJSON5 = getValueFromJSON(string, "clickurl");
                    if (valueFromJSON2 == null || valueFromJSON2.length() <= 0) {
                        return;
                    }
                    double d = Double.parseDouble(valueFromJSON2) * 0.9d;
                    if (((int) d) < d || ((int) d) > 2000 || ((int) d) < 0) {
                        priceCode = getPriceCode(Double.toString(d), str2, logUtil);
                    } else {
                        try {
                            priceCode = this.priceList[(int) d];
                        } catch (Exception e) {
                            priceCode = getPriceCode(Double.toString(d), str2, logUtil);
                        }
                    }
                    if (priceCode == null || priceCode.length() <= 0) {
                        return;
                    }
                    try {
                        String strReplace = valueFromJSON4.replace("${SSP_WINNING_PRICE}", priceCode);
                        StringBuilder sb = new StringBuilder();
                        if (i == 9) {
                            sb.append(SCALEOUT_HTML11);
                            sb.append(valueFromJSON5);
                            sb.append("\"><img src=\"");
                            sb.append(valueFromJSON3);
                            sb.append(SCALEOUT_HTML13);
                        } else {
                            sb.append(SCALEOUT_HTML1);
                            sb.append(valueFromJSON5);
                            sb.append("\"><img src=\"");
                            sb.append(valueFromJSON3);
                            sb.append(SCALEOUT_HTML3);
                        }
                        aPI_ResultParam.html = sb.toString();
                        aPI_ResultParam.imp_price = Double.toString(d / 1000.0d);
                        aPI_ResultParam.imp_url = strReplace;
                    } catch (Exception e2) {
                        logUtil.debug_e(Constants.TAG_NAME, "Exception");
                        logUtil.debug_e(Constants.TAG_NAME, e2);
                    }
                }
            }
        } catch (Exception e3) {
        }
    }

    @Override // jp.tjkapp.adfurikunsdk.API_Base
    public void getContent(API_Controller2.API_ResultParam aPI_ResultParam, String str, String str2, String str3, API_Controller2.API_CpntrolParam aPI_CpntrolParam, LogUtil logUtil, int i) throws Exception {
        if (AdInfo.getAdType(i) == 3) {
            aPI_ResultParam.err = -2;
            return;
        }
        if (aPI_CpntrolParam.idfa == null || aPI_CpntrolParam.idfa.length() <= 0) {
            aPI_ResultParam.err = -7;
            return;
        }
        if (str3.length() == 0) {
            aPI_ResultParam.err = -7;
            return;
        }
        if (aPI_CpntrolParam.useragent.length() == 0) {
            aPI_ResultParam.err = -7;
            return;
        }
        if (aPI_CpntrolParam.pkg_name.length() == 0) {
            aPI_ResultParam.err = -7;
            return;
        }
        if (aPI_CpntrolParam.ipua.f3207ip.length() == 0) {
            aPI_ResultParam.err = -7;
            return;
        }
        this.mAppID = str;
        String strCreateUniqueID = ApiAccessUtil.createUniqueID(aPI_CpntrolParam.idfa);
        String strCreateUniqueID2 = ApiAccessUtil.createUniqueID(aPI_CpntrolParam.idfa);
        if (strCreateUniqueID.length() <= 0 || strCreateUniqueID2.length() <= 0) {
            aPI_ResultParam.err = -7;
            return;
        }
        String valueFromJSON = getValueFromJSON(str3, GETINFO_SCALEOUT_PARAM_W);
        if (valueFromJSON.length() == 0) {
            aPI_ResultParam.err = -7;
            return;
        }
        String valueFromJSON2 = getValueFromJSON(str3, GETINFO_SCALEOUT_PARAM_H);
        if (valueFromJSON2.length() == 0) {
            aPI_ResultParam.err = -7;
            return;
        }
        String valueFromJSON3 = getValueFromJSON(str3, GETINFO_SCALEOUT_PARAM_CAT);
        String[] strArr = null;
        String valueFromJSON4 = getValueFromJSON(str3, GETINFO_SCALEOUT_PARAM_BADV);
        if (valueFromJSON4 != null && valueFromJSON4.length() > 0) {
            try {
                JSONArray jSONArray = new JSONArray(valueFromJSON4);
                int length = jSONArray.length();
                strArr = new String[length];
                for (int i2 = 0; i2 < length; i2++) {
                    strArr[i2] = (String) jSONArray.get(i2);
                }
            } catch (Exception e) {
            }
        }
        String[] strArr2 = null;
        String valueFromJSON5 = getValueFromJSON(str3, GETINFO_SCALEOUT_PARAM_BCAT);
        if (valueFromJSON5 != null && valueFromJSON5.length() > 0) {
            try {
                JSONArray jSONArray2 = new JSONArray(valueFromJSON5);
                int length2 = jSONArray2.length();
                strArr2 = new String[length2];
                for (int i3 = 0; i3 < length2; i3++) {
                    strArr2[i3] = (String) jSONArray2.get(i3);
                }
            } catch (Exception e2) {
            }
        }
        ApiAccessUtil.WebAPIResult webAPIResultCallWebAPI = ApiAccessUtil.callWebAPI(SCALEOUT_URL, logUtil, aPI_CpntrolParam.useragent, createScaleOutJson(strCreateUniqueID, strCreateUniqueID2, valueFromJSON, valueFromJSON2, valueFromJSON3, strArr, strArr2, aPI_CpntrolParam.ipua.f3207ip, aPI_CpntrolParam.useragent, aPI_CpntrolParam.idfa, logUtil, aPI_CpntrolParam.pkg_name), aPI_CpntrolParam.ipua.f3207ip, false);
        if (webAPIResultCallWebAPI.return_code == 200) {
            if (webAPIResultCallWebAPI.message.length() > 0) {
                setResultParam(aPI_ResultParam, webAPIResultCallWebAPI.message.trim(), aPI_CpntrolParam.useragent, i, logUtil);
            } else {
                aPI_ResultParam.err = -7;
            }
        } else if (webAPIResultCallWebAPI.return_code == 204) {
            aPI_ResultParam.err = -4;
        }
        if (aPI_ResultParam.html.length() > 0 || aPI_ResultParam.err == -4) {
            return;
        }
        aPI_ResultParam.err = -7;
    }
}
