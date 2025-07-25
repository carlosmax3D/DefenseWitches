package com.google.android.gms.internal;

import android.os.Parcel;
import android.support.v4.util.TimeUtils;
import com.facebook.internal.FacebookRequestErrorClassification;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.es;
import com.google.android.gms.plus.PlusShare;
import com.google.android.gms.plus.model.moments.ItemScope;
import com.threatmetrix.TrustDefenderMobile.TrustDefenderMobile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import jp.co.voyagegroup.android.fluct.jar.util.FluctConstants;

public final class ib extends es implements SafeParcelable, ItemScope {
    public static final ic CREATOR = new ic();
    private static final HashMap<String, es.a<?, ?>> Ep = new HashMap<>();
    private String AI;
    private ib EA;
    private List<ib> EB;
    private String EC;
    private String ED;
    private ib EE;
    private String EF;
    private String EG;
    private String EH;
    private List<ib> EI;
    private String EJ;
    private String EK;
    private String EL;
    private String EM;
    private String EN;
    private String EO;
    private String EP;
    private String EQ;
    private ib ER;
    private String ES;
    private String ET;
    private String EU;
    private ib EV;
    private ib EW;
    private ib EX;
    private List<ib> EY;
    private String EZ;
    private final Set<Integer> Eq;
    private ib Er;
    private List<String> Es;
    private ib Et;
    private String Eu;
    private String Ev;
    private String Ew;
    private List<ib> Ex;
    private int Ey;
    private List<ib> Ez;
    private String Fa;
    private String Fb;
    private String Fc;
    private ib Fd;
    private String Fe;
    private String Ff;
    private String Fg;
    private ib Fh;
    private String Fi;
    private String Fj;
    private String Fk;
    private String Fl;
    private String iH;
    private final int kg;
    private String mName;
    private String sJ;
    private String uS;
    private double xw;
    private double xx;

    static {
        Ep.put("about", es.a.a("about", 2, ib.class));
        Ep.put("additionalName", es.a.h("additionalName", 3));
        Ep.put("address", es.a.a("address", 4, ib.class));
        Ep.put("addressCountry", es.a.g("addressCountry", 5));
        Ep.put("addressLocality", es.a.g("addressLocality", 6));
        Ep.put("addressRegion", es.a.g("addressRegion", 7));
        Ep.put("associated_media", es.a.b("associated_media", 8, ib.class));
        Ep.put("attendeeCount", es.a.d("attendeeCount", 9));
        Ep.put("attendees", es.a.b("attendees", 10, ib.class));
        Ep.put("audio", es.a.a("audio", 11, ib.class));
        Ep.put("author", es.a.b("author", 12, ib.class));
        Ep.put("bestRating", es.a.g("bestRating", 13));
        Ep.put("birthDate", es.a.g("birthDate", 14));
        Ep.put("byArtist", es.a.a("byArtist", 15, ib.class));
        Ep.put("caption", es.a.g("caption", 16));
        Ep.put("contentSize", es.a.g("contentSize", 17));
        Ep.put("contentUrl", es.a.g("contentUrl", 18));
        Ep.put("contributor", es.a.b("contributor", 19, ib.class));
        Ep.put("dateCreated", es.a.g("dateCreated", 20));
        Ep.put("dateModified", es.a.g("dateModified", 21));
        Ep.put("datePublished", es.a.g("datePublished", 22));
        Ep.put("description", es.a.g("description", 23));
        Ep.put("duration", es.a.g("duration", 24));
        Ep.put("embedUrl", es.a.g("embedUrl", 25));
        Ep.put("endDate", es.a.g("endDate", 26));
        Ep.put("familyName", es.a.g("familyName", 27));
        Ep.put("gender", es.a.g("gender", 28));
        Ep.put("geo", es.a.a("geo", 29, ib.class));
        Ep.put("givenName", es.a.g("givenName", 30));
        Ep.put(FluctConstants.XML_NODE_HEIGHT, es.a.g(FluctConstants.XML_NODE_HEIGHT, 31));
        Ep.put(ShareConstants.WEB_DIALOG_PARAM_ID, es.a.g(ShareConstants.WEB_DIALOG_PARAM_ID, 32));
        Ep.put("image", es.a.g("image", 33));
        Ep.put("inAlbum", es.a.a("inAlbum", 34, ib.class));
        Ep.put("latitude", es.a.e("latitude", 36));
        Ep.put("location", es.a.a("location", 37, ib.class));
        Ep.put("longitude", es.a.e("longitude", 38));
        Ep.put("name", es.a.g("name", 39));
        Ep.put("partOfTVSeries", es.a.a("partOfTVSeries", 40, ib.class));
        Ep.put("performers", es.a.b("performers", 41, ib.class));
        Ep.put("playerType", es.a.g("playerType", 42));
        Ep.put("postOfficeBoxNumber", es.a.g("postOfficeBoxNumber", 43));
        Ep.put("postalCode", es.a.g("postalCode", 44));
        Ep.put("ratingValue", es.a.g("ratingValue", 45));
        Ep.put("reviewRating", es.a.a("reviewRating", 46, ib.class));
        Ep.put("startDate", es.a.g("startDate", 47));
        Ep.put("streetAddress", es.a.g("streetAddress", 48));
        Ep.put("text", es.a.g("text", 49));
        Ep.put("thumbnail", es.a.a("thumbnail", 50, ib.class));
        Ep.put(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_THUMBNAIL_URL, es.a.g(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_THUMBNAIL_URL, 51));
        Ep.put("tickerSymbol", es.a.g("tickerSymbol", 52));
        Ep.put("type", es.a.g("type", 53));
        Ep.put("url", es.a.g("url", 54));
        Ep.put(FluctConstants.XML_NODE_WIDTH, es.a.g(FluctConstants.XML_NODE_WIDTH, 55));
        Ep.put("worstRating", es.a.g("worstRating", 56));
    }

    public ib() {
        this.kg = 1;
        this.Eq = new HashSet();
    }

    ib(Set<Integer> set, int i, ib ibVar, List<String> list, ib ibVar2, String str, String str2, String str3, List<ib> list2, int i2, List<ib> list3, ib ibVar3, List<ib> list4, String str4, String str5, ib ibVar4, String str6, String str7, String str8, List<ib> list5, String str9, String str10, String str11, String str12, String str13, String str14, String str15, String str16, String str17, ib ibVar5, String str18, String str19, String str20, String str21, ib ibVar6, double d, ib ibVar7, double d2, String str22, ib ibVar8, List<ib> list6, String str23, String str24, String str25, String str26, ib ibVar9, String str27, String str28, String str29, ib ibVar10, String str30, String str31, String str32, String str33, String str34, String str35) {
        this.Eq = set;
        this.kg = i;
        this.Er = ibVar;
        this.Es = list;
        this.Et = ibVar2;
        this.Eu = str;
        this.Ev = str2;
        this.Ew = str3;
        this.Ex = list2;
        this.Ey = i2;
        this.Ez = list3;
        this.EA = ibVar3;
        this.EB = list4;
        this.EC = str4;
        this.ED = str5;
        this.EE = ibVar4;
        this.EF = str6;
        this.EG = str7;
        this.EH = str8;
        this.EI = list5;
        this.EJ = str9;
        this.EK = str10;
        this.EL = str11;
        this.sJ = str12;
        this.EM = str13;
        this.EN = str14;
        this.EO = str15;
        this.EP = str16;
        this.EQ = str17;
        this.ER = ibVar5;
        this.ES = str18;
        this.ET = str19;
        this.uS = str20;
        this.EU = str21;
        this.EV = ibVar6;
        this.xw = d;
        this.EW = ibVar7;
        this.xx = d2;
        this.mName = str22;
        this.EX = ibVar8;
        this.EY = list6;
        this.EZ = str23;
        this.Fa = str24;
        this.Fb = str25;
        this.Fc = str26;
        this.Fd = ibVar9;
        this.Fe = str27;
        this.Ff = str28;
        this.Fg = str29;
        this.Fh = ibVar10;
        this.Fi = str30;
        this.Fj = str31;
        this.AI = str32;
        this.iH = str33;
        this.Fk = str34;
        this.Fl = str35;
    }

    public ib(Set<Integer> set, ib ibVar, List<String> list, ib ibVar2, String str, String str2, String str3, List<ib> list2, int i, List<ib> list3, ib ibVar3, List<ib> list4, String str4, String str5, ib ibVar4, String str6, String str7, String str8, List<ib> list5, String str9, String str10, String str11, String str12, String str13, String str14, String str15, String str16, String str17, ib ibVar5, String str18, String str19, String str20, String str21, ib ibVar6, double d, ib ibVar7, double d2, String str22, ib ibVar8, List<ib> list6, String str23, String str24, String str25, String str26, ib ibVar9, String str27, String str28, String str29, ib ibVar10, String str30, String str31, String str32, String str33, String str34, String str35) {
        this.Eq = set;
        this.kg = 1;
        this.Er = ibVar;
        this.Es = list;
        this.Et = ibVar2;
        this.Eu = str;
        this.Ev = str2;
        this.Ew = str3;
        this.Ex = list2;
        this.Ey = i;
        this.Ez = list3;
        this.EA = ibVar3;
        this.EB = list4;
        this.EC = str4;
        this.ED = str5;
        this.EE = ibVar4;
        this.EF = str6;
        this.EG = str7;
        this.EH = str8;
        this.EI = list5;
        this.EJ = str9;
        this.EK = str10;
        this.EL = str11;
        this.sJ = str12;
        this.EM = str13;
        this.EN = str14;
        this.EO = str15;
        this.EP = str16;
        this.EQ = str17;
        this.ER = ibVar5;
        this.ES = str18;
        this.ET = str19;
        this.uS = str20;
        this.EU = str21;
        this.EV = ibVar6;
        this.xw = d;
        this.EW = ibVar7;
        this.xx = d2;
        this.mName = str22;
        this.EX = ibVar8;
        this.EY = list6;
        this.EZ = str23;
        this.Fa = str24;
        this.Fb = str25;
        this.Fc = str26;
        this.Fd = ibVar9;
        this.Fe = str27;
        this.Ff = str28;
        this.Fg = str29;
        this.Fh = ibVar10;
        this.Fi = str30;
        this.Fj = str31;
        this.AI = str32;
        this.iH = str33;
        this.Fk = str34;
        this.Fl = str35;
    }

    /* access modifiers changed from: protected */
    public Object V(String str) {
        return null;
    }

    /* access modifiers changed from: protected */
    public boolean W(String str) {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean a(es.a aVar) {
        return this.Eq.contains(Integer.valueOf(aVar.cq()));
    }

    /* access modifiers changed from: protected */
    public Object b(es.a aVar) {
        switch (aVar.cq()) {
            case 2:
                return this.Er;
            case 3:
                return this.Es;
            case 4:
                return this.Et;
            case 5:
                return this.Eu;
            case 6:
                return this.Ev;
            case 7:
                return this.Ew;
            case 8:
                return this.Ex;
            case 9:
                return Integer.valueOf(this.Ey);
            case 10:
                return this.Ez;
            case 11:
                return this.EA;
            case 12:
                return this.EB;
            case 13:
                return this.EC;
            case 14:
                return this.ED;
            case 15:
                return this.EE;
            case 16:
                return this.EF;
            case FacebookRequestErrorClassification.EC_USER_TOO_MANY_CALLS:
                return this.EG;
            case 18:
                return this.EH;
            case TimeUtils.HUNDRED_DAY_FIELD_LEN:
                return this.EI;
            case 20:
                return this.EJ;
            case 21:
                return this.EK;
            case 22:
                return this.EL;
            case 23:
                return this.sJ;
            case 24:
                return this.EM;
            case 25:
                return this.EN;
            case 26:
                return this.EO;
            case 27:
                return this.EP;
            case 28:
                return this.EQ;
            case 29:
                return this.ER;
            case 30:
                return this.ES;
            case 31:
                return this.ET;
            case 32:
                return this.uS;
            case 33:
                return this.EU;
            case 34:
                return this.EV;
            case 36:
                return Double.valueOf(this.xw);
            case 37:
                return this.EW;
            case TrustDefenderMobile.THM_OPTION_WEBVIEW /*38*/:
                return Double.valueOf(this.xx);
            case 39:
                return this.mName;
            case FluctConstants.FRAME_SIZE /*40*/:
                return this.EX;
            case 41:
                return this.EY;
            case 42:
                return this.EZ;
            case 43:
                return this.Fa;
            case 44:
                return this.Fb;
            case 45:
                return this.Fc;
            case 46:
                return this.Fd;
            case 47:
                return this.Fe;
            case 48:
                return this.Ff;
            case 49:
                return this.Fg;
            case 50:
                return this.Fh;
            case 51:
                return this.Fi;
            case 52:
                return this.Fj;
            case 53:
                return this.AI;
            case 54:
                return this.iH;
            case 55:
                return this.Fk;
            case 56:
                return this.Fl;
            default:
                throw new IllegalStateException("Unknown safe parcelable id=" + aVar.cq());
        }
    }

    public HashMap<String, es.a<?, ?>> cj() {
        return Ep;
    }

    public int describeContents() {
        ic icVar = CREATOR;
        return 0;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ib)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        ib ibVar = (ib) obj;
        for (es.a next : Ep.values()) {
            if (a(next)) {
                if (!ibVar.a(next)) {
                    return false;
                }
                if (!b(next).equals(ibVar.b(next))) {
                    return false;
                }
            } else if (ibVar.a(next)) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public Set<Integer> fa() {
        return this.Eq;
    }

    /* access modifiers changed from: package-private */
    public ib fb() {
        return this.Er;
    }

    /* access modifiers changed from: package-private */
    public ib fc() {
        return this.Et;
    }

    /* access modifiers changed from: package-private */
    public List<ib> fd() {
        return this.Ex;
    }

    /* access modifiers changed from: package-private */
    public List<ib> fe() {
        return this.Ez;
    }

    /* access modifiers changed from: package-private */
    public ib ff() {
        return this.EA;
    }

    /* access modifiers changed from: package-private */
    public List<ib> fg() {
        return this.EB;
    }

    /* access modifiers changed from: package-private */
    public ib fh() {
        return this.EE;
    }

    /* access modifiers changed from: package-private */
    public List<ib> fi() {
        return this.EI;
    }

    /* access modifiers changed from: package-private */
    public ib fj() {
        return this.ER;
    }

    /* access modifiers changed from: package-private */
    public ib fk() {
        return this.EV;
    }

    /* access modifiers changed from: package-private */
    public ib fl() {
        return this.EW;
    }

    /* access modifiers changed from: package-private */
    public ib fm() {
        return this.EX;
    }

    /* access modifiers changed from: package-private */
    public List<ib> fn() {
        return this.EY;
    }

    /* access modifiers changed from: package-private */
    public ib fo() {
        return this.Fd;
    }

    /* access modifiers changed from: package-private */
    public ib fp() {
        return this.Fh;
    }

    /* renamed from: fq */
    public ib freeze() {
        return this;
    }

    public ItemScope getAbout() {
        return this.Er;
    }

    public List<String> getAdditionalName() {
        return this.Es;
    }

    public ItemScope getAddress() {
        return this.Et;
    }

    public String getAddressCountry() {
        return this.Eu;
    }

    public String getAddressLocality() {
        return this.Ev;
    }

    public String getAddressRegion() {
        return this.Ew;
    }

    public List<ItemScope> getAssociated_media() {
        return (ArrayList) this.Ex;
    }

    public int getAttendeeCount() {
        return this.Ey;
    }

    public List<ItemScope> getAttendees() {
        return (ArrayList) this.Ez;
    }

    public ItemScope getAudio() {
        return this.EA;
    }

    public List<ItemScope> getAuthor() {
        return (ArrayList) this.EB;
    }

    public String getBestRating() {
        return this.EC;
    }

    public String getBirthDate() {
        return this.ED;
    }

    public ItemScope getByArtist() {
        return this.EE;
    }

    public String getCaption() {
        return this.EF;
    }

    public String getContentSize() {
        return this.EG;
    }

    public String getContentUrl() {
        return this.EH;
    }

    public List<ItemScope> getContributor() {
        return (ArrayList) this.EI;
    }

    public String getDateCreated() {
        return this.EJ;
    }

    public String getDateModified() {
        return this.EK;
    }

    public String getDatePublished() {
        return this.EL;
    }

    public String getDescription() {
        return this.sJ;
    }

    public String getDuration() {
        return this.EM;
    }

    public String getEmbedUrl() {
        return this.EN;
    }

    public String getEndDate() {
        return this.EO;
    }

    public String getFamilyName() {
        return this.EP;
    }

    public String getGender() {
        return this.EQ;
    }

    public ItemScope getGeo() {
        return this.ER;
    }

    public String getGivenName() {
        return this.ES;
    }

    public String getHeight() {
        return this.ET;
    }

    public String getId() {
        return this.uS;
    }

    public String getImage() {
        return this.EU;
    }

    public ItemScope getInAlbum() {
        return this.EV;
    }

    public double getLatitude() {
        return this.xw;
    }

    public ItemScope getLocation() {
        return this.EW;
    }

    public double getLongitude() {
        return this.xx;
    }

    public String getName() {
        return this.mName;
    }

    public ItemScope getPartOfTVSeries() {
        return this.EX;
    }

    public List<ItemScope> getPerformers() {
        return (ArrayList) this.EY;
    }

    public String getPlayerType() {
        return this.EZ;
    }

    public String getPostOfficeBoxNumber() {
        return this.Fa;
    }

    public String getPostalCode() {
        return this.Fb;
    }

    public String getRatingValue() {
        return this.Fc;
    }

    public ItemScope getReviewRating() {
        return this.Fd;
    }

    public String getStartDate() {
        return this.Fe;
    }

    public String getStreetAddress() {
        return this.Ff;
    }

    public String getText() {
        return this.Fg;
    }

    public ItemScope getThumbnail() {
        return this.Fh;
    }

    public String getThumbnailUrl() {
        return this.Fi;
    }

    public String getTickerSymbol() {
        return this.Fj;
    }

    public String getType() {
        return this.AI;
    }

    public String getUrl() {
        return this.iH;
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.kg;
    }

    public String getWidth() {
        return this.Fk;
    }

    public String getWorstRating() {
        return this.Fl;
    }

    public boolean hasAbout() {
        return this.Eq.contains(2);
    }

    public boolean hasAdditionalName() {
        return this.Eq.contains(3);
    }

    public boolean hasAddress() {
        return this.Eq.contains(4);
    }

    public boolean hasAddressCountry() {
        return this.Eq.contains(5);
    }

    public boolean hasAddressLocality() {
        return this.Eq.contains(6);
    }

    public boolean hasAddressRegion() {
        return this.Eq.contains(7);
    }

    public boolean hasAssociated_media() {
        return this.Eq.contains(8);
    }

    public boolean hasAttendeeCount() {
        return this.Eq.contains(9);
    }

    public boolean hasAttendees() {
        return this.Eq.contains(10);
    }

    public boolean hasAudio() {
        return this.Eq.contains(11);
    }

    public boolean hasAuthor() {
        return this.Eq.contains(12);
    }

    public boolean hasBestRating() {
        return this.Eq.contains(13);
    }

    public boolean hasBirthDate() {
        return this.Eq.contains(14);
    }

    public boolean hasByArtist() {
        return this.Eq.contains(15);
    }

    public boolean hasCaption() {
        return this.Eq.contains(16);
    }

    public boolean hasContentSize() {
        return this.Eq.contains(17);
    }

    public boolean hasContentUrl() {
        return this.Eq.contains(18);
    }

    public boolean hasContributor() {
        return this.Eq.contains(19);
    }

    public boolean hasDateCreated() {
        return this.Eq.contains(20);
    }

    public boolean hasDateModified() {
        return this.Eq.contains(21);
    }

    public boolean hasDatePublished() {
        return this.Eq.contains(22);
    }

    public boolean hasDescription() {
        return this.Eq.contains(23);
    }

    public boolean hasDuration() {
        return this.Eq.contains(24);
    }

    public boolean hasEmbedUrl() {
        return this.Eq.contains(25);
    }

    public boolean hasEndDate() {
        return this.Eq.contains(26);
    }

    public boolean hasFamilyName() {
        return this.Eq.contains(27);
    }

    public boolean hasGender() {
        return this.Eq.contains(28);
    }

    public boolean hasGeo() {
        return this.Eq.contains(29);
    }

    public boolean hasGivenName() {
        return this.Eq.contains(30);
    }

    public boolean hasHeight() {
        return this.Eq.contains(31);
    }

    public boolean hasId() {
        return this.Eq.contains(32);
    }

    public boolean hasImage() {
        return this.Eq.contains(33);
    }

    public boolean hasInAlbum() {
        return this.Eq.contains(34);
    }

    public boolean hasLatitude() {
        return this.Eq.contains(36);
    }

    public boolean hasLocation() {
        return this.Eq.contains(37);
    }

    public boolean hasLongitude() {
        return this.Eq.contains(38);
    }

    public boolean hasName() {
        return this.Eq.contains(39);
    }

    public boolean hasPartOfTVSeries() {
        return this.Eq.contains(40);
    }

    public boolean hasPerformers() {
        return this.Eq.contains(41);
    }

    public boolean hasPlayerType() {
        return this.Eq.contains(42);
    }

    public boolean hasPostOfficeBoxNumber() {
        return this.Eq.contains(43);
    }

    public boolean hasPostalCode() {
        return this.Eq.contains(44);
    }

    public boolean hasRatingValue() {
        return this.Eq.contains(45);
    }

    public boolean hasReviewRating() {
        return this.Eq.contains(46);
    }

    public boolean hasStartDate() {
        return this.Eq.contains(47);
    }

    public boolean hasStreetAddress() {
        return this.Eq.contains(48);
    }

    public boolean hasText() {
        return this.Eq.contains(49);
    }

    public boolean hasThumbnail() {
        return this.Eq.contains(50);
    }

    public boolean hasThumbnailUrl() {
        return this.Eq.contains(51);
    }

    public boolean hasTickerSymbol() {
        return this.Eq.contains(52);
    }

    public boolean hasType() {
        return this.Eq.contains(53);
    }

    public boolean hasUrl() {
        return this.Eq.contains(54);
    }

    public boolean hasWidth() {
        return this.Eq.contains(55);
    }

    public boolean hasWorstRating() {
        return this.Eq.contains(56);
    }

    public int hashCode() {
        int i = 0;
        Iterator<es.a<?, ?>> it = Ep.values().iterator();
        while (true) {
            int i2 = i;
            if (!it.hasNext()) {
                return i2;
            }
            es.a next = it.next();
            if (a(next)) {
                i = b(next).hashCode() + i2 + next.cq();
            } else {
                i = i2;
            }
        }
    }

    public boolean isDataValid() {
        return true;
    }

    public void writeToParcel(Parcel parcel, int i) {
        ic icVar = CREATOR;
        ic.a(this, parcel, i);
    }
}
