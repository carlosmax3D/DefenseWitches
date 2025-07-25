package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.util.TimeUtils;
import com.facebook.internal.FacebookRequestErrorClassification;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.threatmetrix.TrustDefenderMobile.TrustDefenderMobile;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import jp.co.voyagegroup.android.fluct.jar.util.FluctConstants;

public class ic implements Parcelable.Creator<ib> {
    static void a(ib ibVar, Parcel parcel, int i) {
        int o = b.o(parcel);
        Set<Integer> fa = ibVar.fa();
        if (fa.contains(1)) {
            b.c(parcel, 1, ibVar.getVersionCode());
        }
        if (fa.contains(2)) {
            b.a(parcel, 2, (Parcelable) ibVar.fb(), i, true);
        }
        if (fa.contains(3)) {
            b.a(parcel, 3, ibVar.getAdditionalName(), true);
        }
        if (fa.contains(4)) {
            b.a(parcel, 4, (Parcelable) ibVar.fc(), i, true);
        }
        if (fa.contains(5)) {
            b.a(parcel, 5, ibVar.getAddressCountry(), true);
        }
        if (fa.contains(6)) {
            b.a(parcel, 6, ibVar.getAddressLocality(), true);
        }
        if (fa.contains(7)) {
            b.a(parcel, 7, ibVar.getAddressRegion(), true);
        }
        if (fa.contains(8)) {
            b.b(parcel, 8, ibVar.fd(), true);
        }
        if (fa.contains(9)) {
            b.c(parcel, 9, ibVar.getAttendeeCount());
        }
        if (fa.contains(10)) {
            b.b(parcel, 10, ibVar.fe(), true);
        }
        if (fa.contains(11)) {
            b.a(parcel, 11, (Parcelable) ibVar.ff(), i, true);
        }
        if (fa.contains(12)) {
            b.b(parcel, 12, ibVar.fg(), true);
        }
        if (fa.contains(13)) {
            b.a(parcel, 13, ibVar.getBestRating(), true);
        }
        if (fa.contains(14)) {
            b.a(parcel, 14, ibVar.getBirthDate(), true);
        }
        if (fa.contains(15)) {
            b.a(parcel, 15, (Parcelable) ibVar.fh(), i, true);
        }
        if (fa.contains(17)) {
            b.a(parcel, 17, ibVar.getContentSize(), true);
        }
        if (fa.contains(16)) {
            b.a(parcel, 16, ibVar.getCaption(), true);
        }
        if (fa.contains(19)) {
            b.b(parcel, 19, ibVar.fi(), true);
        }
        if (fa.contains(18)) {
            b.a(parcel, 18, ibVar.getContentUrl(), true);
        }
        if (fa.contains(21)) {
            b.a(parcel, 21, ibVar.getDateModified(), true);
        }
        if (fa.contains(20)) {
            b.a(parcel, 20, ibVar.getDateCreated(), true);
        }
        if (fa.contains(23)) {
            b.a(parcel, 23, ibVar.getDescription(), true);
        }
        if (fa.contains(22)) {
            b.a(parcel, 22, ibVar.getDatePublished(), true);
        }
        if (fa.contains(25)) {
            b.a(parcel, 25, ibVar.getEmbedUrl(), true);
        }
        if (fa.contains(24)) {
            b.a(parcel, 24, ibVar.getDuration(), true);
        }
        if (fa.contains(27)) {
            b.a(parcel, 27, ibVar.getFamilyName(), true);
        }
        if (fa.contains(26)) {
            b.a(parcel, 26, ibVar.getEndDate(), true);
        }
        if (fa.contains(29)) {
            b.a(parcel, 29, (Parcelable) ibVar.fj(), i, true);
        }
        if (fa.contains(28)) {
            b.a(parcel, 28, ibVar.getGender(), true);
        }
        if (fa.contains(31)) {
            b.a(parcel, 31, ibVar.getHeight(), true);
        }
        if (fa.contains(30)) {
            b.a(parcel, 30, ibVar.getGivenName(), true);
        }
        if (fa.contains(34)) {
            b.a(parcel, 34, (Parcelable) ibVar.fk(), i, true);
        }
        if (fa.contains(32)) {
            b.a(parcel, 32, ibVar.getId(), true);
        }
        if (fa.contains(33)) {
            b.a(parcel, 33, ibVar.getImage(), true);
        }
        if (fa.contains(38)) {
            b.a(parcel, 38, ibVar.getLongitude());
        }
        if (fa.contains(39)) {
            b.a(parcel, 39, ibVar.getName(), true);
        }
        if (fa.contains(36)) {
            b.a(parcel, 36, ibVar.getLatitude());
        }
        if (fa.contains(37)) {
            b.a(parcel, 37, (Parcelable) ibVar.fl(), i, true);
        }
        if (fa.contains(42)) {
            b.a(parcel, 42, ibVar.getPlayerType(), true);
        }
        if (fa.contains(43)) {
            b.a(parcel, 43, ibVar.getPostOfficeBoxNumber(), true);
        }
        if (fa.contains(40)) {
            b.a(parcel, 40, (Parcelable) ibVar.fm(), i, true);
        }
        if (fa.contains(41)) {
            b.b(parcel, 41, ibVar.fn(), true);
        }
        if (fa.contains(46)) {
            b.a(parcel, 46, (Parcelable) ibVar.fo(), i, true);
        }
        if (fa.contains(47)) {
            b.a(parcel, 47, ibVar.getStartDate(), true);
        }
        if (fa.contains(44)) {
            b.a(parcel, 44, ibVar.getPostalCode(), true);
        }
        if (fa.contains(45)) {
            b.a(parcel, 45, ibVar.getRatingValue(), true);
        }
        if (fa.contains(51)) {
            b.a(parcel, 51, ibVar.getThumbnailUrl(), true);
        }
        if (fa.contains(50)) {
            b.a(parcel, 50, (Parcelable) ibVar.fp(), i, true);
        }
        if (fa.contains(49)) {
            b.a(parcel, 49, ibVar.getText(), true);
        }
        if (fa.contains(48)) {
            b.a(parcel, 48, ibVar.getStreetAddress(), true);
        }
        if (fa.contains(55)) {
            b.a(parcel, 55, ibVar.getWidth(), true);
        }
        if (fa.contains(54)) {
            b.a(parcel, 54, ibVar.getUrl(), true);
        }
        if (fa.contains(53)) {
            b.a(parcel, 53, ibVar.getType(), true);
        }
        if (fa.contains(52)) {
            b.a(parcel, 52, ibVar.getTickerSymbol(), true);
        }
        if (fa.contains(56)) {
            b.a(parcel, 56, ibVar.getWorstRating(), true);
        }
        b.D(parcel, o);
    }

    /* renamed from: as */
    public ib createFromParcel(Parcel parcel) {
        int n = a.n(parcel);
        HashSet hashSet = new HashSet();
        int i = 0;
        ib ibVar = null;
        ArrayList<String> arrayList = null;
        ib ibVar2 = null;
        String str = null;
        String str2 = null;
        String str3 = null;
        ArrayList arrayList2 = null;
        int i2 = 0;
        ArrayList arrayList3 = null;
        ib ibVar3 = null;
        ArrayList arrayList4 = null;
        String str4 = null;
        String str5 = null;
        ib ibVar4 = null;
        String str6 = null;
        String str7 = null;
        String str8 = null;
        ArrayList arrayList5 = null;
        String str9 = null;
        String str10 = null;
        String str11 = null;
        String str12 = null;
        String str13 = null;
        String str14 = null;
        String str15 = null;
        String str16 = null;
        String str17 = null;
        ib ibVar5 = null;
        String str18 = null;
        String str19 = null;
        String str20 = null;
        String str21 = null;
        ib ibVar6 = null;
        double d = 0.0d;
        ib ibVar7 = null;
        double d2 = 0.0d;
        String str22 = null;
        ib ibVar8 = null;
        ArrayList arrayList6 = null;
        String str23 = null;
        String str24 = null;
        String str25 = null;
        String str26 = null;
        ib ibVar9 = null;
        String str27 = null;
        String str28 = null;
        String str29 = null;
        ib ibVar10 = null;
        String str30 = null;
        String str31 = null;
        String str32 = null;
        String str33 = null;
        String str34 = null;
        String str35 = null;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i = a.g(parcel, m);
                    hashSet.add(1);
                    break;
                case 2:
                    hashSet.add(2);
                    ibVar = (ib) a.a(parcel, m, ib.CREATOR);
                    break;
                case 3:
                    arrayList = a.y(parcel, m);
                    hashSet.add(3);
                    break;
                case 4:
                    hashSet.add(4);
                    ibVar2 = (ib) a.a(parcel, m, ib.CREATOR);
                    break;
                case 5:
                    str = a.m(parcel, m);
                    hashSet.add(5);
                    break;
                case 6:
                    str2 = a.m(parcel, m);
                    hashSet.add(6);
                    break;
                case 7:
                    str3 = a.m(parcel, m);
                    hashSet.add(7);
                    break;
                case 8:
                    arrayList2 = a.c(parcel, m, ib.CREATOR);
                    hashSet.add(8);
                    break;
                case 9:
                    i2 = a.g(parcel, m);
                    hashSet.add(9);
                    break;
                case 10:
                    arrayList3 = a.c(parcel, m, ib.CREATOR);
                    hashSet.add(10);
                    break;
                case 11:
                    hashSet.add(11);
                    ibVar3 = (ib) a.a(parcel, m, ib.CREATOR);
                    break;
                case 12:
                    arrayList4 = a.c(parcel, m, ib.CREATOR);
                    hashSet.add(12);
                    break;
                case 13:
                    str4 = a.m(parcel, m);
                    hashSet.add(13);
                    break;
                case 14:
                    str5 = a.m(parcel, m);
                    hashSet.add(14);
                    break;
                case 15:
                    hashSet.add(15);
                    ibVar4 = (ib) a.a(parcel, m, ib.CREATOR);
                    break;
                case 16:
                    str6 = a.m(parcel, m);
                    hashSet.add(16);
                    break;
                case FacebookRequestErrorClassification.EC_USER_TOO_MANY_CALLS:
                    str7 = a.m(parcel, m);
                    hashSet.add(17);
                    break;
                case 18:
                    str8 = a.m(parcel, m);
                    hashSet.add(18);
                    break;
                case TimeUtils.HUNDRED_DAY_FIELD_LEN:
                    arrayList5 = a.c(parcel, m, ib.CREATOR);
                    hashSet.add(19);
                    break;
                case 20:
                    str9 = a.m(parcel, m);
                    hashSet.add(20);
                    break;
                case 21:
                    str10 = a.m(parcel, m);
                    hashSet.add(21);
                    break;
                case 22:
                    str11 = a.m(parcel, m);
                    hashSet.add(22);
                    break;
                case 23:
                    str12 = a.m(parcel, m);
                    hashSet.add(23);
                    break;
                case 24:
                    str13 = a.m(parcel, m);
                    hashSet.add(24);
                    break;
                case 25:
                    str14 = a.m(parcel, m);
                    hashSet.add(25);
                    break;
                case 26:
                    str15 = a.m(parcel, m);
                    hashSet.add(26);
                    break;
                case 27:
                    str16 = a.m(parcel, m);
                    hashSet.add(27);
                    break;
                case 28:
                    str17 = a.m(parcel, m);
                    hashSet.add(28);
                    break;
                case 29:
                    hashSet.add(29);
                    ibVar5 = (ib) a.a(parcel, m, ib.CREATOR);
                    break;
                case 30:
                    str18 = a.m(parcel, m);
                    hashSet.add(30);
                    break;
                case 31:
                    str19 = a.m(parcel, m);
                    hashSet.add(31);
                    break;
                case 32:
                    str20 = a.m(parcel, m);
                    hashSet.add(32);
                    break;
                case 33:
                    str21 = a.m(parcel, m);
                    hashSet.add(33);
                    break;
                case 34:
                    hashSet.add(34);
                    ibVar6 = (ib) a.a(parcel, m, ib.CREATOR);
                    break;
                case 36:
                    d = a.k(parcel, m);
                    hashSet.add(36);
                    break;
                case 37:
                    hashSet.add(37);
                    ibVar7 = (ib) a.a(parcel, m, ib.CREATOR);
                    break;
                case TrustDefenderMobile.THM_OPTION_WEBVIEW /*38*/:
                    d2 = a.k(parcel, m);
                    hashSet.add(38);
                    break;
                case 39:
                    str22 = a.m(parcel, m);
                    hashSet.add(39);
                    break;
                case FluctConstants.FRAME_SIZE /*40*/:
                    hashSet.add(40);
                    ibVar8 = (ib) a.a(parcel, m, ib.CREATOR);
                    break;
                case 41:
                    arrayList6 = a.c(parcel, m, ib.CREATOR);
                    hashSet.add(41);
                    break;
                case 42:
                    str23 = a.m(parcel, m);
                    hashSet.add(42);
                    break;
                case 43:
                    str24 = a.m(parcel, m);
                    hashSet.add(43);
                    break;
                case 44:
                    str25 = a.m(parcel, m);
                    hashSet.add(44);
                    break;
                case 45:
                    str26 = a.m(parcel, m);
                    hashSet.add(45);
                    break;
                case 46:
                    hashSet.add(46);
                    ibVar9 = (ib) a.a(parcel, m, ib.CREATOR);
                    break;
                case 47:
                    str27 = a.m(parcel, m);
                    hashSet.add(47);
                    break;
                case 48:
                    str28 = a.m(parcel, m);
                    hashSet.add(48);
                    break;
                case 49:
                    str29 = a.m(parcel, m);
                    hashSet.add(49);
                    break;
                case 50:
                    hashSet.add(50);
                    ibVar10 = (ib) a.a(parcel, m, ib.CREATOR);
                    break;
                case 51:
                    str30 = a.m(parcel, m);
                    hashSet.add(51);
                    break;
                case 52:
                    str31 = a.m(parcel, m);
                    hashSet.add(52);
                    break;
                case 53:
                    str32 = a.m(parcel, m);
                    hashSet.add(53);
                    break;
                case 54:
                    str33 = a.m(parcel, m);
                    hashSet.add(54);
                    break;
                case 55:
                    str34 = a.m(parcel, m);
                    hashSet.add(55);
                    break;
                case 56:
                    str35 = a.m(parcel, m);
                    hashSet.add(56);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new ib(hashSet, i, ibVar, arrayList, ibVar2, str, str2, str3, arrayList2, i2, arrayList3, ibVar3, arrayList4, str4, str5, ibVar4, str6, str7, str8, arrayList5, str9, str10, str11, str12, str13, str14, str15, str16, str17, ibVar5, str18, str19, str20, str21, ibVar6, d, ibVar7, d2, str22, ibVar8, arrayList6, str23, str24, str25, str26, ibVar9, str27, str28, str29, ibVar10, str30, str31, str32, str33, str34, str35);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: bk */
    public ib[] newArray(int i) {
        return new ib[i];
    }
}
