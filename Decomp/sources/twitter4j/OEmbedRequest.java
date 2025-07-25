package twitter4j;

import com.facebook.share.internal.ShareConstants;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class OEmbedRequest implements Serializable {
    private static final long serialVersionUID = 7454130135274547901L;
    private Align align = Align.NONE;
    private boolean hideMedia = true;
    private boolean hideThread = true;
    private String lang;
    private int maxWidth;
    private boolean omitScript = false;
    private String[] related = new String[0];
    private final long statusId;
    private final String url;

    public enum Align {
        LEFT,
        CENTER,
        RIGHT,
        NONE
    }

    public OEmbedRequest(long j, String str) {
        this.statusId = j;
        this.url = str;
    }

    private void appendParameter(String str, long j, List<HttpParameter> list) {
        if (0 <= j) {
            list.add(new HttpParameter(str, String.valueOf(j)));
        }
    }

    private void appendParameter(String str, String str2, List<HttpParameter> list) {
        if (str2 != null) {
            list.add(new HttpParameter(str, str2));
        }
    }

    public OEmbedRequest HideMedia(boolean z) {
        this.hideMedia = z;
        return this;
    }

    public OEmbedRequest HideThread(boolean z) {
        this.hideThread = z;
        return this;
    }

    public OEmbedRequest MaxWidth(int i) {
        this.maxWidth = i;
        return this;
    }

    public OEmbedRequest align(Align align2) {
        this.align = align2;
        return this;
    }

    /* access modifiers changed from: package-private */
    public HttpParameter[] asHttpParameterArray() {
        ArrayList arrayList = new ArrayList(12);
        appendParameter(ShareConstants.WEB_DIALOG_PARAM_ID, this.statusId, (List<HttpParameter>) arrayList);
        appendParameter("url", this.url, (List<HttpParameter>) arrayList);
        appendParameter("maxwidth", (long) this.maxWidth, (List<HttpParameter>) arrayList);
        arrayList.add(new HttpParameter("hide_media", this.hideMedia));
        arrayList.add(new HttpParameter("hide_thread", this.hideThread));
        arrayList.add(new HttpParameter("omit_script", this.omitScript));
        arrayList.add(new HttpParameter("align", this.align.name().toLowerCase()));
        if (this.related.length > 0) {
            appendParameter("related", StringUtil.join(this.related), (List<HttpParameter>) arrayList);
        }
        appendParameter("lang", this.lang, (List<HttpParameter>) arrayList);
        return (HttpParameter[]) arrayList.toArray(new HttpParameter[arrayList.size()]);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        OEmbedRequest oEmbedRequest = (OEmbedRequest) obj;
        if (this.hideMedia != oEmbedRequest.hideMedia) {
            return false;
        }
        if (this.hideThread != oEmbedRequest.hideThread) {
            return false;
        }
        if (this.maxWidth != oEmbedRequest.maxWidth) {
            return false;
        }
        if (this.omitScript != oEmbedRequest.omitScript) {
            return false;
        }
        if (this.statusId != oEmbedRequest.statusId) {
            return false;
        }
        if (this.align != oEmbedRequest.align) {
            return false;
        }
        if (this.lang == null ? oEmbedRequest.lang != null : !this.lang.equals(oEmbedRequest.lang)) {
            return false;
        }
        if (!Arrays.equals(this.related, oEmbedRequest.related)) {
            return false;
        }
        if (this.url != null) {
            if (this.url.equals(oEmbedRequest.url)) {
                return true;
            }
        } else if (oEmbedRequest.url == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = 1;
        int i2 = 0;
        int hashCode = ((((((((((int) (this.statusId ^ (this.statusId >>> 32))) * 31) + (this.url != null ? this.url.hashCode() : 0)) * 31) + this.maxWidth) * 31) + (this.hideMedia ? 1 : 0)) * 31) + (this.hideThread ? 1 : 0)) * 31;
        if (!this.omitScript) {
            i = 0;
        }
        int hashCode2 = (((((hashCode + i) * 31) + (this.align != null ? this.align.hashCode() : 0)) * 31) + (this.related != null ? Arrays.hashCode(this.related) : 0)) * 31;
        if (this.lang != null) {
            i2 = this.lang.hashCode();
        }
        return hashCode2 + i2;
    }

    public OEmbedRequest lang(String str) {
        this.lang = str;
        return this;
    }

    public OEmbedRequest omitScript(boolean z) {
        this.omitScript = z;
        return this;
    }

    public OEmbedRequest related(String[] strArr) {
        this.related = strArr;
        return this;
    }

    public void setAlign(Align align2) {
        this.align = align2;
    }

    public void setHideMedia(boolean z) {
        this.hideMedia = z;
    }

    public void setHideThread(boolean z) {
        this.hideThread = z;
    }

    public void setLang(String str) {
        this.lang = str;
    }

    public void setMaxWidth(int i) {
        this.maxWidth = i;
    }

    public void setOmitScript(boolean z) {
        this.omitScript = z;
    }

    public void setRelated(String[] strArr) {
        this.related = strArr;
    }

    public String toString() {
        return "OEmbedRequest{statusId=" + this.statusId + ", url='" + this.url + '\'' + ", maxWidth=" + this.maxWidth + ", hideMedia=" + this.hideMedia + ", hideThread=" + this.hideThread + ", omitScript=" + this.omitScript + ", align=" + this.align + ", related=" + (this.related == null ? null : Arrays.asList(this.related)) + ", lang='" + this.lang + '\'' + '}';
    }
}
