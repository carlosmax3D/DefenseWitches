package android.support.v4.widget;

import android.view.animation.Interpolator;
import twitter4j.MediaEntity;

final class BakedBezierInterpolator implements Interpolator {
    private static final BakedBezierInterpolator INSTANCE = new BakedBezierInterpolator();
    private static final float STEP_SIZE = (1.0f / ((float) (VALUES.length - 1)));
    private static final float[] VALUES;

    static {
        float[] fArr = new float[MediaEntity.Size.CROP];
        // fill-array-data instruction
        fArr[0] = 0;
        fArr[1] = 961656599;
        fArr[2] = 980151802;
        fArr[3] = 989399404;
        fArr[4] = 996929018;
        fArr[5] = 1002525898;
        fArr[6] = 1007478532;
        fArr[7] = 1011021880;
        fArr[8] = 1015061833;
        fArr[9] = 1017853562;
        fArr[10] = 1020430542;
        fArr[11] = 1023638346;
        fArr[12] = 1025544238;
        fArr[13] = 1027611191;
        fArr[14] = 1030215015;
        fArr[15] = 1032214859;
        fArr[16] = 1033718098;
        fArr[17] = 1035315288;
        fArr[18] = 1037274867;
        fArr[19] = 1039328399;
        fArr[20] = 1040851770;
        fArr[21] = 1041992620;
        fArr[22] = 1043180447;
        fArr[23] = 1044737373;
        fArr[24] = 1046032574;
        fArr[25] = 1047703585;
        fArr[26] = 1049002141;
        fArr[27] = 1049891334;
        fArr[28] = 1050619465;
        fArr[29] = 1051548923;
        fArr[30] = 1052491802;
        fArr[31] = 1053448104;
        fArr[32] = 1054417827;
        fArr[33] = 1055199645;
        fArr[34] = 1056179434;
        fArr[35] = 1056964608;
        fArr[36] = 1057357195;
        fArr[37] = 1057749782;
        fArr[38] = 1058140691;
        fArr[39] = 1058529922;
        fArr[40] = 1058867144;
        fArr[41] = 1059201011;
        fArr[42] = 1059533200;
        fArr[43] = 1059813379;
        fArr[44] = 1060137180;
        fArr[45] = 1060410648;
        fArr[46] = 1060679084;
        fArr[47] = 1060900543;
        fArr[48] = 1061162267;
        fArr[49] = 1061377016;
        fArr[50] = 1061588409;
        fArr[51] = 1061794768;
        fArr[52] = 1061997773;
        fArr[53] = 1062177289;
        fArr[54] = 1062353450;
        fArr[55] = 1062544710;
        fArr[56] = 1062694027;
        fArr[57] = 1062841667;
        fArr[58] = 1062984273;
        fArr[59] = 1063125202;
        fArr[60] = 1063262775;
        fArr[61] = 1063395315;
        fArr[62] = 1063526177;
        fArr[63] = 1063636907;
        fArr[64] = 1063744281;
        fArr[65] = 1063865077;
        fArr[66] = 1063952318;
        fArr[67] = 1064064726;
        fArr[68] = 1064146934;
        fArr[69] = 1064225787;
        fArr[70] = 1064316384;
        fArr[71] = 1064403626;
        fArr[72] = 1064474090;
        fArr[73] = 1064544554;
        fArr[74] = 1064609985;
        fArr[75] = 1064675416;
        fArr[76] = 1064735814;
        fArr[77] = 1064786146;
        fArr[78] = 1064841511;
        fArr[79] = 1064886809;
        fArr[80] = 1064938819;
        fArr[81] = 1064979084;
        fArr[82] = 1065026060;
        fArr[83] = 1065061292;
        fArr[84] = 1065096525;
        fArr[85] = 1065128401;
        fArr[86] = 1065158600;
        fArr[87] = 1065187122;
        fArr[88] = 1065213965;
        fArr[89] = 1065237453;
        fArr[90] = 1065259264;
        fArr[91] = 1065277719;
        fArr[92] = 1065292818;
        fArr[93] = 1065307918;
        fArr[94] = 1065321339;
        fArr[95] = 1065329728;
        fArr[96] = 1065339794;
        fArr[97] = 1065344827;
        fArr[98] = 1065349861;
        fArr[99] = 1065353216;
        fArr[100] = 1065353216;
        VALUES = fArr;
    }

    private BakedBezierInterpolator() {
    }

    public static final BakedBezierInterpolator getInstance() {
        return INSTANCE;
    }

    public float getInterpolation(float f) {
        if (f >= 1.0f) {
            return 1.0f;
        }
        if (f <= 0.0f) {
            return 0.0f;
        }
        int min = Math.min((int) (((float) (VALUES.length - 1)) * f), VALUES.length - 2);
        return VALUES[min] + ((VALUES[min + 1] - VALUES[min]) * ((f - (((float) min) * STEP_SIZE)) / STEP_SIZE));
    }
}
