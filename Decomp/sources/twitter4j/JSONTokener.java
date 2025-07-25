package twitter4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import jp.stargarage.g2metrics.BuildConfig;

public class JSONTokener {
    private int character;
    private boolean eof;
    private int index;
    private int line;
    private char previous;
    private final Reader reader;
    private boolean usePrevious;

    public JSONTokener(InputStream inputStream) throws JSONException {
        this((Reader) new InputStreamReader(inputStream));
    }

    public JSONTokener(Reader reader2) {
        this.reader = !reader2.markSupported() ? new BufferedReader(reader2) : reader2;
        this.eof = false;
        this.usePrevious = false;
        this.previous = 0;
        this.index = 0;
        this.character = 1;
        this.line = 1;
    }

    public JSONTokener(String str) {
        this((Reader) new StringReader(str));
    }

    public void back() throws JSONException {
        if (this.usePrevious || this.index <= 0) {
            throw new JSONException("Stepping back two steps is not supported");
        }
        this.index--;
        this.character--;
        this.usePrevious = true;
        this.eof = false;
    }

    public boolean end() {
        return this.eof && !this.usePrevious;
    }

    public boolean more() throws JSONException {
        next();
        if (end()) {
            return false;
        }
        back();
        return true;
    }

    public char next() throws JSONException {
        int read;
        int i = 0;
        if (this.usePrevious) {
            this.usePrevious = false;
            read = this.previous;
        } else {
            try {
                read = this.reader.read();
                if (read <= 0) {
                    this.eof = true;
                    read = 0;
                }
            } catch (IOException e) {
                throw new JSONException((Throwable) e);
            }
        }
        this.index++;
        if (this.previous == 13) {
            this.line++;
            if (read != 10) {
                i = 1;
            }
            this.character = i;
        } else if (read == 10) {
            this.line++;
            this.character = 0;
        } else {
            this.character++;
        }
        this.previous = (char) read;
        return this.previous;
    }

    public char next(char c) throws JSONException {
        char next = next();
        if (next == c) {
            return next;
        }
        throw syntaxError("Expected '" + c + "' and instead saw '" + next + "'");
    }

    public String next(int i) throws JSONException {
        if (i == 0) {
            return BuildConfig.FLAVOR;
        }
        char[] cArr = new char[i];
        for (int i2 = 0; i2 < i; i2++) {
            cArr[i2] = next();
            if (end()) {
                throw syntaxError("Substring bounds error");
            }
        }
        return new String(cArr);
    }

    /*  JADX ERROR: StackOverflow in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: 
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    public char nextClean() throws twitter4j.JSONException {
        /*
            r2 = this;
        L_0x0000:
            char r0 = r2.next()
            if (r0 == 0) goto L_0x000a
            r1 = 32
            if (r0 <= r1) goto L_0x0000
        L_0x000a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: twitter4j.JSONTokener.nextClean():char");
    }

    public String nextString(char c) throws JSONException {
        StringBuilder sb = new StringBuilder();
        while (true) {
            char next = next();
            switch (next) {
                case 0:
                case 10:
                case 13:
                    throw syntaxError("Unterminated string");
                case '\\':
                    char next2 = next();
                    switch (next2) {
                        case '\"':
                        case '\'':
                        case '/':
                        case '\\':
                            sb.append(next2);
                            break;
                        case 'b':
                            sb.append(8);
                            break;
                        case 'f':
                            sb.append(12);
                            break;
                        case 'n':
                            sb.append(10);
                            break;
                        case 'r':
                            sb.append(13);
                            break;
                        case 't':
                            sb.append(9);
                            break;
                        case 'u':
                            sb.append((char) Integer.parseInt(next(4), 16));
                            break;
                        default:
                            throw syntaxError("Illegal escape.");
                    }
                default:
                    if (next != c) {
                        sb.append(next);
                        break;
                    } else {
                        return sb.toString();
                    }
            }
        }
    }

    public Object nextValue() throws JSONException {
        char nextClean = nextClean();
        switch (nextClean) {
            case '\"':
            case '\'':
                return nextString(nextClean);
            case '[':
                back();
                return new JSONArray(this);
            case '{':
                back();
                return new JSONObject(this);
            default:
                StringBuilder sb = new StringBuilder();
                while (nextClean >= ' ' && ",:]}/\\\"[{;=#".indexOf(nextClean) < 0) {
                    sb.append(nextClean);
                    nextClean = next();
                }
                back();
                String trim = sb.toString().trim();
                if (!trim.equals(BuildConfig.FLAVOR)) {
                    return JSONObject.stringToValue(trim);
                }
                throw syntaxError("Missing value");
        }
    }

    public JSONException syntaxError(String str) {
        return new JSONException(str + toString());
    }

    public String toString() {
        return " at " + this.index + " [character " + this.character + " line " + this.line + "]";
    }
}
