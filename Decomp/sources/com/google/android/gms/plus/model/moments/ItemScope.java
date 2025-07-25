package com.google.android.gms.plus.model.moments;

import com.google.android.gms.common.data.Freezable;
import com.google.android.gms.internal.ib;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface ItemScope extends Freezable<ItemScope> {

    public static class Builder {
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
        private final Set<Integer> Eq = new HashSet();
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
        private String mName;
        private String sJ;
        private String uS;
        private double xw;
        private double xx;

        public ItemScope build() {
            return new ib(this.Eq, this.Er, this.Es, this.Et, this.Eu, this.Ev, this.Ew, this.Ex, this.Ey, this.Ez, this.EA, this.EB, this.EC, this.ED, this.EE, this.EF, this.EG, this.EH, this.EI, this.EJ, this.EK, this.EL, this.sJ, this.EM, this.EN, this.EO, this.EP, this.EQ, this.ER, this.ES, this.ET, this.uS, this.EU, this.EV, this.xw, this.EW, this.xx, this.mName, this.EX, this.EY, this.EZ, this.Fa, this.Fb, this.Fc, this.Fd, this.Fe, this.Ff, this.Fg, this.Fh, this.Fi, this.Fj, this.AI, this.iH, this.Fk, this.Fl);
        }

        public Builder setAbout(ItemScope itemScope) {
            this.Er = (ib) itemScope;
            this.Eq.add(2);
            return this;
        }

        public Builder setAdditionalName(List<String> list) {
            this.Es = list;
            this.Eq.add(3);
            return this;
        }

        public Builder setAddress(ItemScope itemScope) {
            this.Et = (ib) itemScope;
            this.Eq.add(4);
            return this;
        }

        public Builder setAddressCountry(String str) {
            this.Eu = str;
            this.Eq.add(5);
            return this;
        }

        public Builder setAddressLocality(String str) {
            this.Ev = str;
            this.Eq.add(6);
            return this;
        }

        public Builder setAddressRegion(String str) {
            this.Ew = str;
            this.Eq.add(7);
            return this;
        }

        public Builder setAssociated_media(List<ItemScope> list) {
            this.Ex = list;
            this.Eq.add(8);
            return this;
        }

        public Builder setAttendeeCount(int i) {
            this.Ey = i;
            this.Eq.add(9);
            return this;
        }

        public Builder setAttendees(List<ItemScope> list) {
            this.Ez = list;
            this.Eq.add(10);
            return this;
        }

        public Builder setAudio(ItemScope itemScope) {
            this.EA = (ib) itemScope;
            this.Eq.add(11);
            return this;
        }

        public Builder setAuthor(List<ItemScope> list) {
            this.EB = list;
            this.Eq.add(12);
            return this;
        }

        public Builder setBestRating(String str) {
            this.EC = str;
            this.Eq.add(13);
            return this;
        }

        public Builder setBirthDate(String str) {
            this.ED = str;
            this.Eq.add(14);
            return this;
        }

        public Builder setByArtist(ItemScope itemScope) {
            this.EE = (ib) itemScope;
            this.Eq.add(15);
            return this;
        }

        public Builder setCaption(String str) {
            this.EF = str;
            this.Eq.add(16);
            return this;
        }

        public Builder setContentSize(String str) {
            this.EG = str;
            this.Eq.add(17);
            return this;
        }

        public Builder setContentUrl(String str) {
            this.EH = str;
            this.Eq.add(18);
            return this;
        }

        public Builder setContributor(List<ItemScope> list) {
            this.EI = list;
            this.Eq.add(19);
            return this;
        }

        public Builder setDateCreated(String str) {
            this.EJ = str;
            this.Eq.add(20);
            return this;
        }

        public Builder setDateModified(String str) {
            this.EK = str;
            this.Eq.add(21);
            return this;
        }

        public Builder setDatePublished(String str) {
            this.EL = str;
            this.Eq.add(22);
            return this;
        }

        public Builder setDescription(String str) {
            this.sJ = str;
            this.Eq.add(23);
            return this;
        }

        public Builder setDuration(String str) {
            this.EM = str;
            this.Eq.add(24);
            return this;
        }

        public Builder setEmbedUrl(String str) {
            this.EN = str;
            this.Eq.add(25);
            return this;
        }

        public Builder setEndDate(String str) {
            this.EO = str;
            this.Eq.add(26);
            return this;
        }

        public Builder setFamilyName(String str) {
            this.EP = str;
            this.Eq.add(27);
            return this;
        }

        public Builder setGender(String str) {
            this.EQ = str;
            this.Eq.add(28);
            return this;
        }

        public Builder setGeo(ItemScope itemScope) {
            this.ER = (ib) itemScope;
            this.Eq.add(29);
            return this;
        }

        public Builder setGivenName(String str) {
            this.ES = str;
            this.Eq.add(30);
            return this;
        }

        public Builder setHeight(String str) {
            this.ET = str;
            this.Eq.add(31);
            return this;
        }

        public Builder setId(String str) {
            this.uS = str;
            this.Eq.add(32);
            return this;
        }

        public Builder setImage(String str) {
            this.EU = str;
            this.Eq.add(33);
            return this;
        }

        public Builder setInAlbum(ItemScope itemScope) {
            this.EV = (ib) itemScope;
            this.Eq.add(34);
            return this;
        }

        public Builder setLatitude(double d) {
            this.xw = d;
            this.Eq.add(36);
            return this;
        }

        public Builder setLocation(ItemScope itemScope) {
            this.EW = (ib) itemScope;
            this.Eq.add(37);
            return this;
        }

        public Builder setLongitude(double d) {
            this.xx = d;
            this.Eq.add(38);
            return this;
        }

        public Builder setName(String str) {
            this.mName = str;
            this.Eq.add(39);
            return this;
        }

        public Builder setPartOfTVSeries(ItemScope itemScope) {
            this.EX = (ib) itemScope;
            this.Eq.add(40);
            return this;
        }

        public Builder setPerformers(List<ItemScope> list) {
            this.EY = list;
            this.Eq.add(41);
            return this;
        }

        public Builder setPlayerType(String str) {
            this.EZ = str;
            this.Eq.add(42);
            return this;
        }

        public Builder setPostOfficeBoxNumber(String str) {
            this.Fa = str;
            this.Eq.add(43);
            return this;
        }

        public Builder setPostalCode(String str) {
            this.Fb = str;
            this.Eq.add(44);
            return this;
        }

        public Builder setRatingValue(String str) {
            this.Fc = str;
            this.Eq.add(45);
            return this;
        }

        public Builder setReviewRating(ItemScope itemScope) {
            this.Fd = (ib) itemScope;
            this.Eq.add(46);
            return this;
        }

        public Builder setStartDate(String str) {
            this.Fe = str;
            this.Eq.add(47);
            return this;
        }

        public Builder setStreetAddress(String str) {
            this.Ff = str;
            this.Eq.add(48);
            return this;
        }

        public Builder setText(String str) {
            this.Fg = str;
            this.Eq.add(49);
            return this;
        }

        public Builder setThumbnail(ItemScope itemScope) {
            this.Fh = (ib) itemScope;
            this.Eq.add(50);
            return this;
        }

        public Builder setThumbnailUrl(String str) {
            this.Fi = str;
            this.Eq.add(51);
            return this;
        }

        public Builder setTickerSymbol(String str) {
            this.Fj = str;
            this.Eq.add(52);
            return this;
        }

        public Builder setType(String str) {
            this.AI = str;
            this.Eq.add(53);
            return this;
        }

        public Builder setUrl(String str) {
            this.iH = str;
            this.Eq.add(54);
            return this;
        }

        public Builder setWidth(String str) {
            this.Fk = str;
            this.Eq.add(55);
            return this;
        }

        public Builder setWorstRating(String str) {
            this.Fl = str;
            this.Eq.add(56);
            return this;
        }
    }

    ItemScope getAbout();

    List<String> getAdditionalName();

    ItemScope getAddress();

    String getAddressCountry();

    String getAddressLocality();

    String getAddressRegion();

    List<ItemScope> getAssociated_media();

    int getAttendeeCount();

    List<ItemScope> getAttendees();

    ItemScope getAudio();

    List<ItemScope> getAuthor();

    String getBestRating();

    String getBirthDate();

    ItemScope getByArtist();

    String getCaption();

    String getContentSize();

    String getContentUrl();

    List<ItemScope> getContributor();

    String getDateCreated();

    String getDateModified();

    String getDatePublished();

    String getDescription();

    String getDuration();

    String getEmbedUrl();

    String getEndDate();

    String getFamilyName();

    String getGender();

    ItemScope getGeo();

    String getGivenName();

    String getHeight();

    String getId();

    String getImage();

    ItemScope getInAlbum();

    double getLatitude();

    ItemScope getLocation();

    double getLongitude();

    String getName();

    ItemScope getPartOfTVSeries();

    List<ItemScope> getPerformers();

    String getPlayerType();

    String getPostOfficeBoxNumber();

    String getPostalCode();

    String getRatingValue();

    ItemScope getReviewRating();

    String getStartDate();

    String getStreetAddress();

    String getText();

    ItemScope getThumbnail();

    String getThumbnailUrl();

    String getTickerSymbol();

    String getType();

    String getUrl();

    String getWidth();

    String getWorstRating();

    boolean hasAbout();

    boolean hasAdditionalName();

    boolean hasAddress();

    boolean hasAddressCountry();

    boolean hasAddressLocality();

    boolean hasAddressRegion();

    boolean hasAssociated_media();

    boolean hasAttendeeCount();

    boolean hasAttendees();

    boolean hasAudio();

    boolean hasAuthor();

    boolean hasBestRating();

    boolean hasBirthDate();

    boolean hasByArtist();

    boolean hasCaption();

    boolean hasContentSize();

    boolean hasContentUrl();

    boolean hasContributor();

    boolean hasDateCreated();

    boolean hasDateModified();

    boolean hasDatePublished();

    boolean hasDescription();

    boolean hasDuration();

    boolean hasEmbedUrl();

    boolean hasEndDate();

    boolean hasFamilyName();

    boolean hasGender();

    boolean hasGeo();

    boolean hasGivenName();

    boolean hasHeight();

    boolean hasId();

    boolean hasImage();

    boolean hasInAlbum();

    boolean hasLatitude();

    boolean hasLocation();

    boolean hasLongitude();

    boolean hasName();

    boolean hasPartOfTVSeries();

    boolean hasPerformers();

    boolean hasPlayerType();

    boolean hasPostOfficeBoxNumber();

    boolean hasPostalCode();

    boolean hasRatingValue();

    boolean hasReviewRating();

    boolean hasStartDate();

    boolean hasStreetAddress();

    boolean hasText();

    boolean hasThumbnail();

    boolean hasThumbnailUrl();

    boolean hasTickerSymbol();

    boolean hasType();

    boolean hasUrl();

    boolean hasWidth();

    boolean hasWorstRating();
}
