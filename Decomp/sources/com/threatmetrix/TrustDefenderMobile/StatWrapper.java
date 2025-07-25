package com.threatmetrix.TrustDefenderMobile;

import android.os.StatFs;
import java.lang.reflect.Method;

class StatWrapper extends WrapperHelper {
    private static final String TAG = StatWrapper.class.getName();
    private static final Method m_getAvailableBlocks = getMethod(StatFs.class, "getAvailableBlocks", new Class[0]);
    private static final Method m_getAvailableBlocksLong = getMethod(StatFs.class, "getAvailableBlocksLong", new Class[0]);
    private static final Method m_getBlockCount = getMethod(StatFs.class, "getBlockCount", new Class[0]);
    private static final Method m_getBlockCountLong = getMethod(StatFs.class, "getBlockCountLong", new Class[0]);
    private static final Method m_getBlockSize = getMethod(StatFs.class, "getBlockSize", new Class[0]);
    private static final Method m_getBlockSizeLong = getMethod(StatFs.class, "getBlockSizeLong", new Class[0]);
    private final StatFs m_stat;

    public StatWrapper(String str) {
        this.m_stat = new StatFs(str);
    }

    public final long getAvailableBlocks() {
        Integer num;
        Long l;
        if (m_getAvailableBlocksLong != null && (l = (Long) invoke(this.m_stat, m_getAvailableBlocksLong, new Object[0])) != null) {
            return l.longValue();
        }
        if (m_getAvailableBlocks == null || (num = (Integer) invoke(this.m_stat, m_getAvailableBlocks, new Object[0])) == null) {
            return 0;
        }
        return (long) num.intValue();
    }

    public final long getBlockCount() {
        Integer num;
        Long l;
        if (m_getBlockCountLong != null && (l = (Long) invoke(this.m_stat, m_getBlockCountLong, new Object[0])) != null) {
            return l.longValue();
        }
        if (m_getBlockCount == null || (num = (Integer) invoke(this.m_stat, m_getBlockCount, new Object[0])) == null) {
            return 0;
        }
        return (long) num.intValue();
    }

    public final long getBlockSize() {
        Integer num;
        Long l;
        if (m_getBlockSizeLong != null && (l = (Long) invoke(this.m_stat, m_getBlockSizeLong, new Object[0])) != null) {
            return l.longValue();
        }
        if (m_getBlockSize == null || (num = (Integer) invoke(this.m_stat, m_getBlockSize, new Object[0])) == null) {
            return 0;
        }
        return (long) num.intValue();
    }
}
