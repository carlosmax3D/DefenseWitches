package android.support.v4.view.accessibility;

import android.view.accessibility.AccessibilityNodeInfo;
import java.util.List;

class AccessibilityNodeInfoCompatApi21 {

    static class CollectionItemInfo {
        CollectionItemInfo() {
        }

        public static boolean isSelected(Object obj) {
            return ((AccessibilityNodeInfo.CollectionItemInfo) obj).isSelected();
        }
    }

    AccessibilityNodeInfoCompatApi21() {
    }

    static void addAction(Object obj, Object obj2) {
        ((AccessibilityNodeInfo) obj).addAction((AccessibilityNodeInfo.AccessibilityAction) obj2);
    }

    static int getAccessibilityActionId(Object obj) {
        return ((AccessibilityNodeInfo.AccessibilityAction) obj).getId();
    }

    static CharSequence getAccessibilityActionLabel(Object obj) {
        return ((AccessibilityNodeInfo.AccessibilityAction) obj).getLabel();
    }

    static List<Object> getActionList(Object obj) {
        return ((AccessibilityNodeInfo) obj).getActionList();
    }

    static Object newAccessibilityAction(int i, CharSequence charSequence) {
        return new AccessibilityNodeInfo.AccessibilityAction(i, charSequence);
    }

    public static Object obtainCollectionInfo(int i, int i2, boolean z, int i3) {
        return AccessibilityNodeInfo.CollectionInfo.obtain(i, i2, z, i3);
    }

    public static Object obtainCollectionItemInfo(int i, int i2, int i3, int i4, boolean z, boolean z2) {
        return AccessibilityNodeInfo.CollectionItemInfo.obtain(i, i2, i3, i4, z, z2);
    }
}
