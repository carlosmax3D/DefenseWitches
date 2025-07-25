package com.google.android.gms.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.R;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.internal.du;
import com.google.android.gms.internal.dz;
import com.google.android.gms.internal.fg;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

public final class GooglePlayServicesUtil {
    public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = 4242000;
    public static final String GOOGLE_PLAY_STORE_PACKAGE = "com.android.vending";
    static final byte[][] mD = {K("0\u0004C0\u0003+ \u0003\u0002\u0001\u0002\u0002\t\u0000ÂàFdJ00\r\u0006\t*H÷\r\u0001\u0001\u0004\u0005\u00000t1\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00130\u0011\u0006\u0003U\u0004\b\u0013\nCalifornia1\u00160\u0014\u0006\u0003U\u0004\u0007\u0013\rMountain View1\u00140\u0012\u0006\u0003U\u0004\n\u0013\u000bGoogle Inc.1\u00100\u000e\u0006\u0003U\u0004\u000b\u0013\u0007Android1\u00100\u000e\u0006\u0003U\u0004\u0003\u0013\u0007Android0\u001e\u0017\r080821231334Z\u0017\r360107231334Z0t1\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00130\u0011\u0006\u0003U\u0004\b\u0013\nCalifornia1\u00160\u0014\u0006\u0003U\u0004\u0007\u0013\rMountain View1\u00140\u0012\u0006\u0003U\u0004\n\u0013\u000bGoogle Inc.1\u00100\u000e\u0006\u0003U\u0004\u000b\u0013\u0007Android1\u00100\u000e\u0006\u0003U\u0004\u0003\u0013\u0007Android0\u0001 0\r\u0006\t*H÷\r\u0001\u0001\u0001\u0005\u0000\u0003\u0001\r\u00000\u0001\b\u0002\u0001\u0001\u0000«V.\u0000Ø;¢\b®\no\u0012N)Ú\u0011ò«VÐXâÌ©\u0013\u0003é·TÓrö@§\u001b\u001dË\u0013\tgbNFV§wj\u0019=²å¿·$©\u001ew\u0018\u000ejG¤;3Ù`w\u00181EÌß{.XftÉáV[\u001fLjYU¿òQ¦=«ùÅ\\'\"\"Rèuäø\u0015Jd_qhÀ±¿Æ\u0012ê¿xWi»4ªyÜ~.¢vL®\u0007ØÁqT×î_d¥\u001aD¦\u0002ÂI\u0005AWÜ\u0002Í_\\\u000eUûï\u0019ûã'ð±Q\u0016Å o\u0019ÑõÄÛÂÖ¹?hÌ)yÇ\u000e\u0018«k;ÕÛU*\u000e;LßXûíÁº5à\u0003Á´±\rÒD¨î$ÿý38r«R!^Ú°ü\r\u000b\u0014[j¡y\u0002\u0001\u0003£Ù0Ö0\u001d\u0006\u0003U\u001d\u000e\u0004\u0016\u0004\u0014Ç}Â!\u0017V%Óßkãä×¥0¦\u0006\u0003U\u001d#\u00040\u0014Ç}Â!\u0017V%Óßkãä×¥¡x¤v0t1\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00130\u0011\u0006\u0003U\u0004\b\u0013\nCalifornia1\u00160\u0014\u0006\u0003U\u0004\u0007\u0013\rMountain View1\u00140\u0012\u0006\u0003U\u0004\n\u0013\u000bGoogle Inc.1\u00100\u000e\u0006\u0003U\u0004\u000b\u0013\u0007Android1\u00100\u000e\u0006\u0003U\u0004\u0003\u0013\u0007Android\t\u0000ÂàFdJ00\f\u0006\u0003U\u001d\u0013\u0004\u00050\u0003\u0001\u0001ÿ0\r\u0006\t*H÷\r\u0001\u0001\u0004\u0005\u0000\u0003\u0001\u0001\u0000mÒRÎï0,6\nªÎÏòÌ©\u0004»]z\u0016aø®F²B\u0004ÐÿJhÇí\u001aS\u001eÄYZb<æ\u0007c±g)zzãW\u0012Ä\u0007ò\bðË\u0010)\u0012M{\u0010b\u0019ÀÊ>³ù­_¸qï&âñmDÈÙ l²ð\u0005»?âËD~s\u0010v­E³?`\tê\u0019Áaæ&Aª'\u001dýR(ÅÅ]ÛE'XÖaöÌ\fÌ·5.BLÄ6\\R52÷2Q7Y<JãAôÛAíÚ\r\u000b\u0010q§Ä@ðþ \u001c¶'ÊgCiÐ½/Ù\u0011ÿ\u0006Í¿,ú\u0010Ü\u000f:ãWbHÇïÆLqD\u0017B÷\u0005ÉÞW:õ[9\r×ý¹A1]_u0\u0011&ÿb\u0014\u0010Ài0"), K("0\u0004¨0\u0003 \u0003\u0002\u0001\u0002\u0002\t\u0000Õ¸l}ÓNõ0\r\u0006\t*H÷\r\u0001\u0001\u0004\u0005\u000001\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00130\u0011\u0006\u0003U\u0004\b\u0013\nCalifornia1\u00160\u0014\u0006\u0003U\u0004\u0007\u0013\rMountain View1\u00100\u000e\u0006\u0003U\u0004\n\u0013\u0007Android1\u00100\u000e\u0006\u0003U\u0004\u000b\u0013\u0007Android1\u00100\u000e\u0006\u0003U\u0004\u0003\u0013\u0007Android1\"0 \u0006\t*H÷\r\u0001\t\u0001\u0016\u0013android@android.com0\u001e\u0017\r080415233656Z\u0017\r350901233656Z01\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00130\u0011\u0006\u0003U\u0004\b\u0013\nCalifornia1\u00160\u0014\u0006\u0003U\u0004\u0007\u0013\rMountain View1\u00100\u000e\u0006\u0003U\u0004\n\u0013\u0007Android1\u00100\u000e\u0006\u0003U\u0004\u000b\u0013\u0007Android1\u00100\u000e\u0006\u0003U\u0004\u0003\u0013\u0007Android1\"0 \u0006\t*H÷\r\u0001\t\u0001\u0016\u0013android@android.com0\u0001 0\r\u0006\t*H÷\r\u0001\u0001\u0001\u0005\u0000\u0003\u0001\r\u00000\u0001\b\u0002\u0001\u0001\u0000ÖÎ.\b\n¿â1MÑ³ÏÓ\u0018\\´=3ú\ftá½¶ÑÛ\u0013ö,\\9ßVøF=e¾ÀóÊBk\u0007Å¨íZ9ÁgçkÉ¹'K\u000b\"\u0000\u0019©)\u0015årÅm*0\u001b£oÅü\u0011:ÖËt5¡m#«}úîáeäß\u001f\n½§\nQlN\u0005\u0011Ê|\fU\u0017[ÃuùHÅj®\b¤O¦¤Ý}¿,\n5\"­\u0006¸Ì\u0018^±Uyîøm\b\u000b\u001daÀù¯±ÂëÑ\u0007êE«Ûh£Ç^TÇlSÔ\u000b\u0012\u001dç»Ó\u000eb\f\u0018áªaÛ¼Ý<d_/UóÔÃuì@p©?qQØ6pÁj\u001a¾^òÑ\u0018á¸®ó)ðf¿láD¬èm\u001c\u001b\u000f\u0002\u0001\u0003£ü0ù0\u001d\u0006\u0003U\u001d\u000e\u0004\u0016\u0004\u0014\u001cÅ¾LC<a:\u0015°L¼\u0003òOà²0É\u0006\u0003U\u001d#\u0004Á0¾\u0014\u001cÅ¾LC<a:\u0015°L¼\u0003òOà²¡¤01\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00130\u0011\u0006\u0003U\u0004\b\u0013\nCalifornia1\u00160\u0014\u0006\u0003U\u0004\u0007\u0013\rMountain View1\u00100\u000e\u0006\u0003U\u0004\n\u0013\u0007Android1\u00100\u000e\u0006\u0003U\u0004\u000b\u0013\u0007Android1\u00100\u000e\u0006\u0003U\u0004\u0003\u0013\u0007Android1\"0 \u0006\t*H÷\r\u0001\t\u0001\u0016\u0013android@android.com\t\u0000Õ¸l}ÓNõ0\f\u0006\u0003U\u001d\u0013\u0004\u00050\u0003\u0001\u0001ÿ0\r\u0006\t*H÷\r\u0001\u0001\u0004\u0005\u0000\u0003\u0001\u0001\u0000\u0019Ó\fñ\u0005ûx?L\r}Ò##=@zÏÎ\u0000\b\u001d[×ÆéÖí k\u000e\u0011 \u0006Al¢D\u0013ÒkJ àõ$ÊÒ»\\nL¡\u0001j\u0015n¡ì]ÉZ^:\u0001\u00006ôHÕ\u0010¿.\u001eag:;åm¯\u000bw±Â)ãÂUãèL]#ïº\tËñ; +NZ\"É2cHJ#Òü)ú\u00199u3¯Øª\u0016\u000fBÂÐ\u0016>fCéÁ/ Á33[Àÿk\"ÞÑ­DB)¥9©Nï­«ÐeÎÒK>QåÝ{fx{ï\u0012þû¤Ä#ûOøÌIL\u0002ðõ\u0005\u0016\u0012ÿe)9>FêÅ»!òwÁQª_*¦'Ñè§\n¶\u00035iÞ;¿ÿ|©Ú>\u0012Cö\u000b")};
    static final byte[][] mE = {K("0\u0002R0\u0001»\u0002\u0004I4~0\r\u0006\t*H÷\r\u0001\u0001\u0004\u0005\u00000p1\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u000b0\t\u0006\u0003U\u0004\b\u0013\u0002CA1\u00160\u0014\u0006\u0003U\u0004\u0007\u0013\rMountain View1\u00140\u0012\u0006\u0003U\u0004\n\u0013\u000bGoogle, Inc1\u00140\u0012\u0006\u0003U\u0004\u000b\u0013\u000bGoogle, Inc1\u00100\u000e\u0006\u0003U\u0004\u0003\u0013\u0007Unknown0\u001e\u0017\r081202020758Z\u0017\r360419020758Z0p1\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u000b0\t\u0006\u0003U\u0004\b\u0013\u0002CA1\u00160\u0014\u0006\u0003U\u0004\u0007\u0013\rMountain View1\u00140\u0012\u0006\u0003U\u0004\n\u0013\u000bGoogle, Inc1\u00140\u0012\u0006\u0003U\u0004\u000b\u0013\u000bGoogle, Inc1\u00100\u000e\u0006\u0003U\u0004\u0003\u0013\u0007Unknown00\r\u0006\t*H÷\r\u0001\u0001\u0001\u0005\u0000\u0003\u00000\u0002\u0000H\u0003\u0019ù±G&8N\u0004SÑ\u000b¿Ç{%\u0004¤± |LlDº¼\u0000­Æa\u000f¦¶«-¨\u000e3òîñk&£ö¸[úÊû¾³ôÉO~\"§àë§\\í=Ò)úseô\u0015\u0016AZ©Áa}ÕÎ\u0019ºè »Øü\u0017©´½&@Q!ªÛwÞ´\u0000\u00138\u0014\u0018.Å\"üX\r\u0002\u0003\u0001\u0000\u00010\r\u0006\t*H÷\r\u0001\u0001\u0004\u0005\u0000\u0003\u0000@fÖ1ÚCÝÐaÒ&às¹Ä¹øµä¾<¾P\u001eß\u001co©YÀÎ`\\OÒ¬m\u001cÎÞ Glº±èò :ÿw\u0017­e-Ì\u0007\bÑ!m¨DWY&IàéÓÄ»Lõ¡±ÔüA¼¹XOdæ_A\r\u0005)ý[h\u0014\u001d\nÑÛ\u0011Ë*\r÷ê\f±-³¤"), K("0\u0004¨0\u0003 \u0003\u0002\u0001\u0002\u0002\t\u0000~OòÖµÞ0\r\u0006\t*H÷\r\u0001\u0001\u0005\u0005\u000001\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00130\u0011\u0006\u0003U\u0004\b\u0013\nCalifornia1\u00160\u0014\u0006\u0003U\u0004\u0007\u0013\rMountain View1\u00100\u000e\u0006\u0003U\u0004\n\u0013\u0007Android1\u00100\u000e\u0006\u0003U\u0004\u000b\u0013\u0007Android1\u00100\u000e\u0006\u0003U\u0004\u0003\u0013\u0007Android1\"0 \u0006\t*H÷\r\u0001\t\u0001\u0016\u0013android@android.com0\u001e\u0017\r100120010135Z\u0017\r370607010135Z01\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00130\u0011\u0006\u0003U\u0004\b\u0013\nCalifornia1\u00160\u0014\u0006\u0003U\u0004\u0007\u0013\rMountain View1\u00100\u000e\u0006\u0003U\u0004\n\u0013\u0007Android1\u00100\u000e\u0006\u0003U\u0004\u000b\u0013\u0007Android1\u00100\u000e\u0006\u0003U\u0004\u0003\u0013\u0007Android1\"0 \u0006\t*H÷\r\u0001\t\u0001\u0016\u0013android@android.com0\u0001 0\r\u0006\t*H÷\r\u0001\u0001\u0001\u0005\u0000\u0003\u0001\r\u00000\u0001\b\u0002\u0001\u0001\u0000Ø(q|6Ñ\u0017\u000fÔM\n{\u000f\u0007\u0011&è[¿ß3°4`\u0000ZÌûe¥Û ²Cß`±¿\u0006ß\u001d\\\n3âÑcõ\u0013ß\u001d\"SAê<3y\"è\\\u0002ì4ÎÙL¸\u0007#¦#ÿK¯û´åïæw;>¢¾¸¼²\u0002gÏçQ\u001f.ù«uþ\u001e)Ï¼M\b:\u001f\u0012R\u0000wsò\u0016[i{\u0000£ Á:Ì0ò!cÁn=J²\u00146LEÀC\u00142p9ñÚ\t`ñ³ü\u0018¶V\u0010Æ\"_Ç\u0010+|o\u0013¤]$ãàÅNgã[g\b'\u0013ÒÖðWÝ4WÑÄþÝì:O?#\u0005\u0019§\n(64¬5£J½¡}Z\n\tûø\u0006\u000b\u0003j'x`cú\f7¹çò¡\u000ev¼w\u0002\u0001\u0003£ü0ù0\u001d\u0006\u0003U\u001d\u000e\u0004\u0016\u0004\u0014µÇù\u0012ox\r:ûÊess?õ\"k\u001770É\u0006\u0003U\u001d#\u0004Á0¾\u0014µÇù\u0012ox\r:ûÊess?õ\"k\u00177¡¤01\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00130\u0011\u0006\u0003U\u0004\b\u0013\nCalifornia1\u00160\u0014\u0006\u0003U\u0004\u0007\u0013\rMountain View1\u00100\u000e\u0006\u0003U\u0004\n\u0013\u0007Android1\u00100\u000e\u0006\u0003U\u0004\u000b\u0013\u0007Android1\u00100\u000e\u0006\u0003U\u0004\u0003\u0013\u0007Android1\"0 \u0006\t*H÷\r\u0001\t\u0001\u0016\u0013android@android.com\t\u0000~OòÖµÞ0\f\u0006\u0003U\u001d\u0013\u0004\u00050\u0003\u0001\u0001ÿ0\r\u0006\t*H÷\r\u0001\u0001\u0005\u0005\u0000\u0003\u0001\u0001\u0000L>§e}&æ»×\u0011\f\u0019ß\u001f¡\t}3\u000fiÞ¿ÊÛF£~å³\u000f»4{\u001cuU¼»<T\u0014F_y*\u0002ÐÛå¦Ga³yG«kÿ°ºÆ¢Á Íøbøw©g\rýo\u0006.@nÎ\u0018\u0006\f`Iü6'\u0011qåoË¡Ræ\u0005ÎÎY\u001fÄô©+3ºØ\u0019mwoU·Ð\u001aÏ1Ý×\fì·xv\u0006e\u0010ùI¥RJ11³ËeAÏ5B\u000e¼ÄR%Y?Bfi\u0005rfbO³ÏÛR\u0017\u001d\u0011\u001cn\u0003F\u0016øQ!\u0018Ð¢¦\u0013×ðÍ\u0011ÛÕ#ZT¥JÂQçÒ,Dj?î\u0014\u0012\u0010éDGK@c\u0007»&+OkÓU\u001csQÿ¢`[\u0005â$×\u0015Øzö")};
    static final byte[][] mF = {K("0\u0002§0\u0002e \u0003\u0002\u0001\u0002\u0002\u0004P\u0005|B0\u000b\u0006\u0007*HÎ8\u0004\u0003\u0005\u0000071\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00100\u000e\u0006\u0003U\u0004\n\u0013\u0007Android1\u00160\u0014\u0006\u0003U\u0004\u0003\u0013\rAndroid Debug0\u001e\u0017\r120717145250Z\u0017\r220715145250Z071\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00100\u000e\u0006\u0003U\u0004\n\u0013\u0007Android1\u00160\u0014\u0006\u0003U\u0004\u0003\u0013\rAndroid Debug0\u0001·0\u0001,\u0006\u0007*HÎ8\u0004\u00010\u0001\u001f\u0002\u0000ýS\u001du\u0012)RßJ.ìäçö\u0011·R<ïD\u0000Ã\u001e?¶Q&iE]@\"QûY=Xú¿Åõº0öËUl×;\u001d4oòf`·kP¥¤è\u0004{\u0010\"ÂO»©×þ·Æ\u001bø;WçÆ¨¦\u0015\u000f\u0004ûöÓÅ\u001eÃ\u00025T\u0013Z\u00162öuó®+a×*ïò\"\u0003\u0019ÑH\u0001Ç\u0002\u0015\u0000`P\u0015#\u000bÌ²¹¢ë\u000bðX\u001cõ\u0002\u0000÷á Ö=ÞË¼«\\6¸W¹y¯»ú:êùWL\u000b=\u0007gQYWºÔYOæq\u0007\u0010´I\u0016q#èL(\u0016\u0013·Ï\t2È¦á<\u0016zT|(à£®\u001e+³¦un£\u000bú!5bñûbz\u0001$;Ì¤ñ¾¨Q¨ßáZå\u0006f^{U%d\u0001L;þÏI*\u0003\u0000\u0002jÑ\u001b×ÕfÒzô9À.Ah¬ýE´¾¼{\u001cwTi?\rB¤üá\u00108BO¦Ñ0RNïöñ78c/¦7)þMF ¸feîðA\u00179\u0001\u0003[\u001cj£\u0018\u0018\r0:¨ÌY#àjo«úuh<E;²\u0007w|òýçÏ±\u001408\u0014ª\u001d÷´=[\"+W\u0006´0\u000b\u0006\u0007*HÎ8\u0004\u0003\u0005\u0000\u0003/\u00000,\u0002\u0014\tÒÑ°G\u0002)µ¾Ò&aÑ\u0012òpÅæ\u001d\u0002\u0014gP\u0002\u0006§Pºx®Ç\u0017O\u0016\u0004ê¢÷")};
    static final byte[][] mG = {K("0\u0004L0\u00034 \u0003\u0002\u0001\u0002\u0002\t\u0000¨Í\u0017É=¥Ù0\r\u0006\t*H÷\r\u0001\u0001\u0005\u0005\u00000w1\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00130\u0011\u0006\u0003U\u0004\b\u0013\nCalifornia1\u00160\u0014\u0006\u0003U\u0004\u0007\u0013\rMountain View1\u00140\u0012\u0006\u0003U\u0004\n\u0013\u000bGoogle Inc.1\u00100\u000e\u0006\u0003U\u0004\u000b\u0013\u0007Android1\u00130\u0011\u0006\u0003U\u0004\u0003\u0013\nGoogle NFC0\u001e\u0017\r110324010653Z\u0017\r380809010653Z0w1\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00130\u0011\u0006\u0003U\u0004\b\u0013\nCalifornia1\u00160\u0014\u0006\u0003U\u0004\u0007\u0013\rMountain View1\u00140\u0012\u0006\u0003U\u0004\n\u0013\u000bGoogle Inc.1\u00100\u000e\u0006\u0003U\u0004\u000b\u0013\u0007Android1\u00130\u0011\u0006\u0003U\u0004\u0003\u0013\nGoogle NFC0\u0001 0\r\u0006\t*H÷\r\u0001\u0001\u0001\u0005\u0000\u0003\u0001\r\u00000\u0001\b\u0002\u0001\u0001\u0000Ã\u000f­Ù´\tj,XjZ5kú\u0002iXøÿ\f]úõI&ØpÞè!¥>\u001f[\u0017\u000fÉbE£É§ËE'\u0005;ã^4óÒK\"ì\fRn&teàhuêb\u001fù@ã4[ I\u0007ÌTt:ÍªÎeV_HºtÍA!ÍÈvß5\"ºÛ\t\\ Ù4Åj>\\9>åðà/àb\u001f\u001f5¨$%,o¦¶3§hk>Ha-\u0006©ÏoI¿ñ\u001d](þ\u0014¬WbCÝ)êý¹\rã&5\u0013©\u0005¬¯ ~Fu\nZ·¿w&/G°?Z<nm{Q4?iÇ÷%÷\u000bÌ\u001bJÕ%\u000bpZæè>â®7þW\u0001¼½²oîýÿö\u000fj[ßµ¶G\u0002\u0001\u0003£Ü0Ù0\u001d\u0006\u0003U\u001d\u000e\u0004\u0016\u0004\u0014\u001cÎÎ\u000eêMÁ\u0012\u001fÇQ_\r\n\fràÉm0©\u0006\u0003U\u001d#\u0004¡0\u0014\u001cÎÎ\u000eêMÁ\u0012\u001fÇQ_\r\n\fràÉm¡{¤y0w1\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00130\u0011\u0006\u0003U\u0004\b\u0013\nCalifornia1\u00160\u0014\u0006\u0003U\u0004\u0007\u0013\rMountain View1\u00140\u0012\u0006\u0003U\u0004\n\u0013\u000bGoogle Inc.1\u00100\u000e\u0006\u0003U\u0004\u000b\u0013\u0007Android1\u00130\u0011\u0006\u0003U\u0004\u0003\u0013\nGoogle NFC\t\u0000¨Í\u0017É=¥Ù0\f\u0006\u0003U\u001d\u0013\u0004\u00050\u0003\u0001\u0001ÿ0\r\u0006\t*H÷\r\u0001\u0001\u0005\u0005\u0000\u0003\u0001\u0001\u0000¤pÇ(áÓ\u001b\u0006Ù¯jçhµe\u0004lWkCrI1×]L¡\f2\u0015 Ó<Ïí*¦Tb#Lù¶ù\u0010ÌgkËÖÀgcWO»x3\u0012uÜ\\óº©\u0018×\u0005\u001fû¢­èó\u0003ÍèÙæ\u0004\u001fÛ|*I²\"ÆÿB+ñUi¸^îí°J£\bsÛæKtøòÂöÄ\u0001$ª¨Ñx\r\u0018Q+T\nÝ(³éX\u0019q¤\u0017\rØhÏ_1äG\u0012²Â;µ\u00107×ï¦å½³^,ëk°\"cl\u0017¥j¼zP%\u000bÒí{1UZ\u0018E.\u00172\u001a\rRö?t-tÿyXj\\»¯q¨KÏtC\u0010éé'Y\u0000¢=Ð\u0006`\f\"8Ù\u000b/³rßÛºu½."), K("0\u0004L0\u00034 \u0003\u0002\u0001\u0002\u0002\t\u0000Þv\u0004\u001dvPÀ0\r\u0006\t*H÷\r\u0001\u0001\u0005\u0005\u00000w1\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00130\u0011\u0006\u0003U\u0004\b\u0013\nCalifornia1\u00160\u0014\u0006\u0003U\u0004\u0007\u0013\rMountain View1\u00140\u0012\u0006\u0003U\u0004\n\u0013\u000bGoogle Inc.1\u00100\u000e\u0006\u0003U\u0004\u000b\u0013\u0007Android1\u00130\u0011\u0006\u0003U\u0004\u0003\u0013\nGoogle NFC0\u001e\u0017\r110324010324Z\u0017\r380809010324Z0w1\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00130\u0011\u0006\u0003U\u0004\b\u0013\nCalifornia1\u00160\u0014\u0006\u0003U\u0004\u0007\u0013\rMountain View1\u00140\u0012\u0006\u0003U\u0004\n\u0013\u000bGoogle Inc.1\u00100\u000e\u0006\u0003U\u0004\u000b\u0013\u0007Android1\u00130\u0011\u0006\u0003U\u0004\u0003\u0013\nGoogle NFC0\u0001 0\r\u0006\t*H÷\r\u0001\u0001\u0001\u0005\u0000\u0003\u0001\r\u00000\u0001\b\u0002\u0001\u0001\u0000æÿ=ïé*¡\rqë\u000f¦@À6·âCîíh¦¤v=Ç¥*1u|ÚÆ\u001få\u0010»sÇ\u0016ä\u0000\u0001\u0004&[4ÎÎôÄ+ñá7Ð¨vð(\"»Áù½Õ×\u0013²ö©5£yÒË©ÉoÒÐx|\u0011ñë\u0019T\b¦ r³Klú\ná'gé\u0000u0\u0016i¡\u001cïFÎ÷Ç\u0004mÞ1û`(M\u0012\n°çÞ\u001dc?\u0007h}FQ\u0013ÿýÆ¼ |©\u0004¸¾\u001d ª{NuoC`d¾\\®<hè»yBÍõ\u0016\u0007É0¢üÚe[uÐuº­\u0006ç9½\u000b¢\u001f@BÂÀ¨ZZ°ÐgÆÃìI! B¬c§å;Tle´`´ãæâ>\u001fwÏçöÞtK\u001ae\u0002\u0001\u0003£Ü0Ù0\u001d\u0006\u0003U\u001d\u000e\u0004\u0016\u0004\u0014¢èd°]\b\\4Û\n\u0000P\u0011zì0©\u0006\u0003U\u001d#\u0004¡0\u0014¢èd°]\b\\4Û\n\u0000P\u0011zì¡{¤y0w1\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00130\u0011\u0006\u0003U\u0004\b\u0013\nCalifornia1\u00160\u0014\u0006\u0003U\u0004\u0007\u0013\rMountain View1\u00140\u0012\u0006\u0003U\u0004\n\u0013\u000bGoogle Inc.1\u00100\u000e\u0006\u0003U\u0004\u000b\u0013\u0007Android1\u00130\u0011\u0006\u0003U\u0004\u0003\u0013\nGoogle NFC\t\u0000Þv\u0004\u001dvPÀ0\f\u0006\u0003U\u001d\u0013\u0004\u00050\u0003\u0001\u0001ÿ0\r\u0006\t*H÷\r\u0001\u0001\u0005\u0005\u0000\u0003\u0001\u0001\u00007q\fè|<Rê0ÆébÙKM_\u0012Â]&\u0015AýµU]\u0012Îó¸1,?]ö¨ªàL¹³\u0005ä'ý\u001d-\u0019áÒxñ<R\u000f\u0018!\u0002cØÔ½6QHØØº&Ø¹¿\tõý>»\u000e£ÂðÉ7o\u001e\u001fÊvó¦¤\u0005B\b\u001bu*z·Vé«DÚA«ÈáèøÂu§CûsæPqW\fËkzÝ!¹ÆäVá,\"=\\\u0007JßUö«Ú&-dê\nEîÍ´\u0012~uÇSÃÿ0ËÆxµ\u001cR\u0014rñ}¢\n\rÆ'J¢F44Á©¶\u0014ßi}õÊ\u0001ç¢\\}³û\u0005]eV\u0004°\u001d8«ºW³¡p>ÂçJÓ4")};
    private static final byte[][] mH = a(mD, mE, mF, mG);
    private static final byte[][] mI = {mD[0], mE[0], mG[0]};
    static final byte[][] mJ = {K("0\u0002_0\u0001È \u0003\u0002\u0001\u0002\u0002\u0004K\u0019±0\r\u0006\t*H÷\r\u0001\u0001\u0005\u0005\u00000t1\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00130\u0011\u0006\u0003U\u0004\b\u0013\nCalifornia1\u00160\u0014\u0006\u0003U\u0004\u0007\u0013\rMountain View1\u00150\u0013\u0006\u0003U\u0004\n\u0013\fGoogle, Inc.1\u00100\u000e\u0006\u0003U\u0004\u000b\u0013\u0007Unknown1\u000f0\r\u0006\u0003U\u0004\u0003\u0013\u0006Bazaar0\u001e\u0017\r091205010429Z\u0017\r370422010429Z0t1\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00130\u0011\u0006\u0003U\u0004\b\u0013\nCalifornia1\u00160\u0014\u0006\u0003U\u0004\u0007\u0013\rMountain View1\u00150\u0013\u0006\u0003U\u0004\n\u0013\fGoogle, Inc.1\u00100\u000e\u0006\u0003U\u0004\u000b\u0013\u0007Unknown1\u000f0\r\u0006\u0003U\u0004\u0003\u0013\u0006Bazaar00\r\u0006\t*H÷\r\u0001\u0001\u0001\u0005\u0000\u0003\u00000\u0002\u0000©\bÞã54w\tÝK%\u001ez)¨G7k.\\º[[Õ\u0004>\u0001\u0002\u0019\u0014\u0013ú3ªåD;\u0003SJÎJ­oP\u0012IÙv£~LÌá×\u001b§¾>ugMµñ\u0007Zsp\u0001FH§Îp<-Ç4\u0005\u0012¯l[(«ÕOd\u0011È1¢P\u000f(\u0002ÑlæÑopªü¢eA{9\u0002\u0003\u0001\u0000\u00010\r\u0006\t*H÷\r\u0001\u0001\u0005\u0005\u0000\u0003\u0000IÆóAG\u0001#b:'Oéá7=u1Ì\rüé§jæûp[@L½\u001bÁ\u0016«\u0018»\u0011Ãx¿´ólÁLì\u001d,ÅQj\u000eÎÔ\u0007NµhPd\u0000¯øÜÈïT\u0004\u0012\u0002ýïñýàó#\u0010rýÌÞJ6àÆÃù¸³ª\rh<:¿Ú·»2é¾^6\u0019ºÝ:Ì\u0003j­µ¦\u0019¯P")};
    public static boolean mK = false;
    public static boolean mL = false;
    static boolean mM = false;
    private static int mN = -1;
    private static final Object mO = new Object();

    private GooglePlayServicesUtil() {
    }

    private static byte[] K(String str) {
        try {
            return str.getBytes("ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    public static Dialog a(int i, Activity activity, int i2, DialogInterface.OnCancelListener onCancelListener, int i3) {
        AlertDialog.Builder message = new AlertDialog.Builder(activity).setMessage(b(activity, i, i3));
        if (onCancelListener != null) {
            message.setOnCancelListener(onCancelListener);
        }
        du duVar = new du(activity, a(activity, i, i3), i2);
        String b = b(activity, i);
        if (b != null) {
            message.setPositiveButton(b, duVar);
        }
        switch (i) {
            case 0:
                return null;
            case 1:
                return message.setTitle(R.string.common_google_play_services_install_title).create();
            case 2:
                return message.setTitle(R.string.common_google_play_services_update_title).create();
            case 3:
                return message.setTitle(R.string.common_google_play_services_enable_title).create();
            case 4:
            case 6:
                return message.create();
            case 5:
                Log.e("GooglePlayServicesUtil", "An invalid account was specified when connecting. Please provide a valid account.");
                return message.setTitle(R.string.common_google_play_services_invalid_account_title).create();
            case 7:
                Log.e("GooglePlayServicesUtil", "Network error occurred. Please retry request later.");
                return message.setTitle(R.string.common_google_play_services_network_error_title).create();
            case 8:
                Log.e("GooglePlayServicesUtil", "Internal error occurred. Please see logs for detailed information");
                return message.create();
            case 9:
                Log.e("GooglePlayServicesUtil", "Google Play services is invalid. Cannot recover.");
                return message.setTitle(R.string.common_google_play_services_unsupported_title).create();
            case 10:
                Log.e("GooglePlayServicesUtil", "Developer error occurred. Please see logs for detailed information");
                return message.create();
            case 11:
                Log.e("GooglePlayServicesUtil", "The application is not licensed to the user.");
                return message.create();
            case 12:
                Log.e("GooglePlayServicesUtil", "The date of the device is not valid.");
                return message.setTitle(R.string.common_google_play_services_unsupported_title).create();
            default:
                Log.e("GooglePlayServicesUtil", "Unexpected error code " + i);
                return message.create();
        }
    }

    public static Intent a(Context context, int i, int i2) {
        switch (i) {
            case 1:
            case 2:
                return y(i2) ? o(context) ? dz.T(GOOGLE_PLAY_SERVICES_PACKAGE) : dz.S("com.google.android.apps.bazaar") : dz.S(GOOGLE_PLAY_SERVICES_PACKAGE);
            case 3:
                return dz.Q(GOOGLE_PLAY_SERVICES_PACKAGE);
            case 12:
                return dz.bX();
            default:
                return null;
        }
    }

    public static boolean a(Resources resources) {
        if (resources == null) {
            return false;
        }
        return (fg.cD() && ((resources.getConfiguration().screenLayout & 15) > 3)) || b(resources);
    }

    private static byte[] a(PackageInfo packageInfo, byte[]... bArr) {
        try {
            CertificateFactory instance = CertificateFactory.getInstance("X509");
            if (packageInfo.signatures.length != 1) {
                Log.w("GooglePlayServicesUtil", "Package has more than one signature.");
                return null;
            }
            try {
                try {
                    ((X509Certificate) instance.generateCertificate(new ByteArrayInputStream(packageInfo.signatures[0].toByteArray()))).checkValidity();
                    byte[] byteArray = packageInfo.signatures[0].toByteArray();
                    for (byte[] bArr2 : bArr) {
                        if (Arrays.equals(bArr2, byteArray)) {
                            return bArr2;
                        }
                    }
                    if (Log.isLoggable("GooglePlayServicesUtil", 2)) {
                        Log.v("GooglePlayServicesUtil", "Signature not valid.  Found: \n" + Base64.encodeToString(byteArray, 0));
                    }
                    return null;
                } catch (CertificateExpiredException e) {
                    Log.w("GooglePlayServicesUtil", "Certificate has expired.");
                    return null;
                } catch (CertificateNotYetValidException e2) {
                    Log.w("GooglePlayServicesUtil", "Certificate is not yet valid.");
                    return null;
                }
            } catch (CertificateException e3) {
                Log.w("GooglePlayServicesUtil", "Could not generate certificate.");
                return null;
            }
        } catch (CertificateException e4) {
            Log.w("GooglePlayServicesUtil", "Could not get certificate instance.");
            return null;
        }
    }

    private static byte[][] a(byte[][]... bArr) {
        int i = 0;
        for (byte[][] length : bArr) {
            i += length.length;
        }
        byte[][] bArr2 = new byte[i][];
        int length2 = bArr.length;
        int i2 = 0;
        int i3 = 0;
        while (i2 < length2) {
            byte[][] bArr3 = bArr[i2];
            int i4 = i3;
            int i5 = 0;
            while (i5 < bArr3.length) {
                bArr2[i4] = bArr3[i5];
                i5++;
                i4++;
            }
            i2++;
            i3 = i4;
        }
        return bArr2;
    }

    public static String b(Context context, int i) {
        Resources resources = context.getResources();
        switch (i) {
            case 1:
                return resources.getString(R.string.common_google_play_services_install_button);
            case 2:
                return resources.getString(R.string.common_google_play_services_update_button);
            case 3:
                return resources.getString(R.string.common_google_play_services_enable_button);
            default:
                return resources.getString(17039370);
        }
    }

    public static String b(Context context, int i, int i2) {
        Resources resources = context.getResources();
        switch (i) {
            case 1:
                String string = a(context.getResources()) ? resources.getString(R.string.common_google_play_services_install_text_tablet) : resources.getString(R.string.common_google_play_services_install_text_phone);
                return y(i2) ? string + " (via Bazaar)" : string;
            case 2:
                String string2 = resources.getString(R.string.common_google_play_services_update_text);
                return y(i2) ? string2 + " (via Bazaar)" : string2;
            case 3:
                return resources.getString(R.string.common_google_play_services_enable_text);
            case 5:
                return resources.getString(R.string.common_google_play_services_invalid_account_text);
            case 7:
                return resources.getString(R.string.common_google_play_services_network_error_text);
            case 9:
                return resources.getString(R.string.common_google_play_services_unsupported_text);
            case 12:
                return resources.getString(R.string.common_google_play_services_unsupported_date_text);
            default:
                return resources.getString(R.string.common_google_play_services_unknown_issue);
        }
    }

    private static boolean b(Resources resources) {
        Configuration configuration = resources.getConfiguration();
        return fg.cF() && (configuration.screenLayout & 15) <= 3 && configuration.smallestScreenWidthDp >= 600;
    }

    public static boolean bi() {
        return mK ? mL : "user".equals(Build.TYPE);
    }

    public static Dialog getErrorDialog(int i, Activity activity, int i2) {
        return a(i, activity, i2, (DialogInterface.OnCancelListener) null, -1);
    }

    public static Dialog getErrorDialog(int i, Activity activity, int i2, DialogInterface.OnCancelListener onCancelListener) {
        return a(i, activity, i2, onCancelListener, -1);
    }

    public static PendingIntent getErrorPendingIntent(int i, Context context, int i2) {
        Intent a = a(context, i, -1);
        if (a == null) {
            return null;
        }
        return PendingIntent.getActivity(context, i2, a, DriveFile.MODE_READ_ONLY);
    }

    public static String getErrorString(int i) {
        switch (i) {
            case 0:
                return "SUCCESS";
            case 1:
                return "SERVICE_MISSING";
            case 2:
                return "SERVICE_VERSION_UPDATE_REQUIRED";
            case 3:
                return "SERVICE_DISABLED";
            case 4:
                return "SIGN_IN_REQUIRED";
            case 5:
                return "INVALID_ACCOUNT";
            case 6:
                return "RESOLUTION_REQUIRED";
            case 7:
                return "NETWORK_ERROR";
            case 8:
                return "INTERNAL_ERROR";
            case 9:
                return "SERVICE_INVALID";
            case 10:
                return "DEVELOPER_ERROR";
            case 11:
                return "LICENSE_CHECK_FAILED";
            case 12:
                return "DATE_INVALID";
            default:
                return "UNKNOWN_ERROR_CODE";
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getOpenSourceSoftwareLicenseInfo(android.content.Context r4) {
        /*
            r1 = 0
            android.net.Uri$Builder r0 = new android.net.Uri$Builder
            r0.<init>()
            java.lang.String r2 = "android.resource"
            android.net.Uri$Builder r0 = r0.scheme(r2)
            java.lang.String r2 = "com.google.android.gms"
            android.net.Uri$Builder r0 = r0.authority(r2)
            java.lang.String r2 = "raw"
            android.net.Uri$Builder r0 = r0.appendPath(r2)
            java.lang.String r2 = "oss_notice"
            android.net.Uri$Builder r0 = r0.appendPath(r2)
            android.net.Uri r0 = r0.build()
            android.content.ContentResolver r2 = r4.getContentResolver()     // Catch:{ Exception -> 0x004e }
            java.io.InputStream r2 = r2.openInputStream(r0)     // Catch:{ Exception -> 0x004e }
            java.util.Scanner r0 = new java.util.Scanner     // Catch:{ NoSuchElementException -> 0x003f, all -> 0x0047 }
            r0.<init>(r2)     // Catch:{ NoSuchElementException -> 0x003f, all -> 0x0047 }
            java.lang.String r3 = "\\A"
            java.util.Scanner r0 = r0.useDelimiter(r3)     // Catch:{ NoSuchElementException -> 0x003f, all -> 0x0047 }
            java.lang.String r0 = r0.next()     // Catch:{ NoSuchElementException -> 0x003f, all -> 0x0047 }
            if (r2 == 0) goto L_0x003e
            r2.close()     // Catch:{ Exception -> 0x004e }
        L_0x003e:
            return r0
        L_0x003f:
            r0 = move-exception
            if (r2 == 0) goto L_0x0045
            r2.close()     // Catch:{ Exception -> 0x004e }
        L_0x0045:
            r0 = r1
            goto L_0x003e
        L_0x0047:
            r0 = move-exception
            if (r2 == 0) goto L_0x004d
            r2.close()     // Catch:{ Exception -> 0x004e }
        L_0x004d:
            throw r0     // Catch:{ Exception -> 0x004e }
        L_0x004e:
            r0 = move-exception
            r0 = r1
            goto L_0x003e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.GooglePlayServicesUtil.getOpenSourceSoftwareLicenseInfo(android.content.Context):java.lang.String");
    }

    public static Context getRemoteContext(Context context) {
        try {
            return context.createPackageContext(GOOGLE_PLAY_SERVICES_PACKAGE, 3);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public static Resources getRemoteResource(Context context) {
        try {
            return context.getPackageManager().getResourcesForApplication(GOOGLE_PLAY_SERVICES_PACKAGE);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public static int isGooglePlayServicesAvailable(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            context.getResources().getString(R.string.common_google_play_services_unknown_issue);
        } catch (Throwable th) {
            Log.e("GooglePlayServicesUtil", "The Google Play services resources were not found. Check your project configuration to ensure that the resources are included.");
        }
        if (System.currentTimeMillis() < 1227312000288L) {
            return 12;
        }
        n(context);
        try {
            byte[] a = a(packageManager.getPackageInfo("com.android.vending", 64), mD);
            if (a == null) {
                Log.w("GooglePlayServicesUtil", "Google Play Store signature invalid.");
                return 9;
            }
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(GOOGLE_PLAY_SERVICES_PACKAGE, 64);
                if (a(packageInfo, a) == null) {
                    Log.w("GooglePlayServicesUtil", "Google Play services signature invalid.");
                    return 9;
                } else if (packageInfo.versionCode < 4242000) {
                    Log.w("GooglePlayServicesUtil", "Google Play services out of date.  Requires 4242000 but found " + packageInfo.versionCode);
                    return 2;
                } else {
                    try {
                        return !packageManager.getApplicationInfo(GOOGLE_PLAY_SERVICES_PACKAGE, 0).enabled ? 3 : 0;
                    } catch (PackageManager.NameNotFoundException e) {
                        Log.wtf("GooglePlayServicesUtil", "Google Play services missing when getting application info.");
                        e.printStackTrace();
                        return 1;
                    }
                }
            } catch (PackageManager.NameNotFoundException e2) {
                Log.w("GooglePlayServicesUtil", "Google Play services is missing.");
                return 1;
            }
        } catch (PackageManager.NameNotFoundException e3) {
            Log.w("GooglePlayServicesUtil", "Google Play Store is missing.");
            return 9;
        }
    }

    public static boolean isUserRecoverableError(int i) {
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 9:
            case 12:
                return true;
            default:
                return false;
        }
    }

    public static void m(Context context) throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException {
        int isGooglePlayServicesAvailable = isGooglePlayServicesAvailable(context);
        if (isGooglePlayServicesAvailable != 0) {
            Intent a = a(context, isGooglePlayServicesAvailable, -1);
            Log.e("GooglePlayServicesUtil", "GooglePlayServices not available due to error " + isGooglePlayServicesAvailable);
            if (a == null) {
                throw new GooglePlayServicesNotAvailableException(isGooglePlayServicesAvailable);
            }
            throw new GooglePlayServicesRepairableException(isGooglePlayServicesAvailable, "Google Play Services not available", a);
        }
    }

    private static void n(Context context) {
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
        } catch (PackageManager.NameNotFoundException e) {
            Log.wtf("GooglePlayServicesUtil", "This should never happen.", e);
        }
        Bundle bundle = applicationInfo.metaData;
        if (bundle != null) {
            int i = bundle.getInt("com.google.android.gms.version");
            if (i != 4242000) {
                throw new IllegalStateException("The meta-data tag in your app's AndroidManifest.xml does not have the right value.  Expected 4242000 but found " + i + ".  You must have the" + " following declaration within the <application> element: " + "    <meta-data android:name=\"" + "com.google.android.gms.version" + "\" android:value=\"@integer/google_play_services_version\" />");
            }
            return;
        }
        throw new IllegalStateException("A required meta-data tag in your app's AndroidManifest.xml does not exist.  You must have the following declaration within the <application> element:     <meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />");
    }

    private static boolean o(Context context) {
        if (mK) {
            return mM;
        }
        try {
            return a(context.getPackageManager().getPackageInfo("com.google.android.apps.bazaar", 64), mJ) != null;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    static boolean y(int i) {
        switch (z(i)) {
            case 0:
                return !bi();
            case 1:
                return true;
            case 2:
                return false;
            default:
                return false;
        }
    }

    private static int z(int i) {
        if (i == -1) {
            return 2;
        }
        return i;
    }
}
