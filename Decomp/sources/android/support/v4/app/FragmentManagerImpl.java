package android.support.v4.app;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.BackStackRecord;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.DebugUtils;
import android.support.v4.util.LogWriter;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: FragmentManager */
final class FragmentManagerImpl extends FragmentManager implements LayoutInflater.Factory {
    static final Interpolator ACCELERATE_CUBIC = new AccelerateInterpolator(1.5f);
    static final Interpolator ACCELERATE_QUINT = new AccelerateInterpolator(2.5f);
    static final int ANIM_DUR = 220;
    public static final int ANIM_STYLE_CLOSE_ENTER = 3;
    public static final int ANIM_STYLE_CLOSE_EXIT = 4;
    public static final int ANIM_STYLE_FADE_ENTER = 5;
    public static final int ANIM_STYLE_FADE_EXIT = 6;
    public static final int ANIM_STYLE_OPEN_ENTER = 1;
    public static final int ANIM_STYLE_OPEN_EXIT = 2;
    static boolean DEBUG = false;
    static final Interpolator DECELERATE_CUBIC = new DecelerateInterpolator(1.5f);
    static final Interpolator DECELERATE_QUINT = new DecelerateInterpolator(2.5f);
    static final boolean HONEYCOMB;
    static final String TAG = "FragmentManager";
    static final String TARGET_REQUEST_CODE_STATE_TAG = "android:target_req_state";
    static final String TARGET_STATE_TAG = "android:target_state";
    static final String USER_VISIBLE_HINT_TAG = "android:user_visible_hint";
    static final String VIEW_STATE_TAG = "android:view_state";
    ArrayList<Fragment> mActive;
    FragmentActivity mActivity;
    ArrayList<Fragment> mAdded;
    ArrayList<Integer> mAvailBackStackIndices;
    ArrayList<Integer> mAvailIndices;
    ArrayList<BackStackRecord> mBackStack;
    ArrayList<FragmentManager.OnBackStackChangedListener> mBackStackChangeListeners;
    ArrayList<BackStackRecord> mBackStackIndices;
    FragmentContainer mContainer;
    ArrayList<Fragment> mCreatedMenus;
    int mCurState = 0;
    boolean mDestroyed;
    Runnable mExecCommit = new Runnable() {
        public void run() {
            FragmentManagerImpl.this.execPendingActions();
        }
    };
    boolean mExecutingActions;
    boolean mHavePendingDeferredStart;
    boolean mNeedMenuInvalidate;
    String mNoTransactionsBecause;
    Fragment mParent;
    ArrayList<Runnable> mPendingActions;
    SparseArray<Parcelable> mStateArray = null;
    Bundle mStateBundle = null;
    boolean mStateSaved;
    Runnable[] mTmpActions;

    /* compiled from: FragmentManager */
    static class FragmentTag {
        public static final int[] Fragment = {16842755, 16842960, 16842961};
        public static final int Fragment_id = 1;
        public static final int Fragment_name = 0;
        public static final int Fragment_tag = 2;

        FragmentTag() {
        }
    }

    static {
        boolean z = false;
        if (Build.VERSION.SDK_INT >= 11) {
            z = true;
        }
        HONEYCOMB = z;
    }

    FragmentManagerImpl() {
    }

    private void checkStateLoss() {
        if (this.mStateSaved) {
            throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
        } else if (this.mNoTransactionsBecause != null) {
            throw new IllegalStateException("Can not perform this action inside of " + this.mNoTransactionsBecause);
        }
    }

    static Animation makeFadeAnimation(Context context, float f, float f2) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(f, f2);
        alphaAnimation.setInterpolator(DECELERATE_CUBIC);
        alphaAnimation.setDuration(220);
        return alphaAnimation;
    }

    static Animation makeOpenCloseAnimation(Context context, float f, float f2, float f3, float f4) {
        AnimationSet animationSet = new AnimationSet(false);
        ScaleAnimation scaleAnimation = new ScaleAnimation(f, f2, f, f2, 1, 0.5f, 1, 0.5f);
        scaleAnimation.setInterpolator(DECELERATE_QUINT);
        scaleAnimation.setDuration(220);
        animationSet.addAnimation(scaleAnimation);
        AlphaAnimation alphaAnimation = new AlphaAnimation(f3, f4);
        alphaAnimation.setInterpolator(DECELERATE_CUBIC);
        alphaAnimation.setDuration(220);
        animationSet.addAnimation(alphaAnimation);
        return animationSet;
    }

    public static int reverseTransit(int i) {
        switch (i) {
            case FragmentTransaction.TRANSIT_FRAGMENT_OPEN:
                return FragmentTransaction.TRANSIT_FRAGMENT_CLOSE;
            case FragmentTransaction.TRANSIT_FRAGMENT_FADE:
                return FragmentTransaction.TRANSIT_FRAGMENT_FADE;
            case FragmentTransaction.TRANSIT_FRAGMENT_CLOSE:
                return FragmentTransaction.TRANSIT_FRAGMENT_OPEN;
            default:
                return 0;
        }
    }

    private void throwException(RuntimeException runtimeException) {
        Log.e(TAG, runtimeException.getMessage());
        Log.e(TAG, "Activity state:");
        PrintWriter printWriter = new PrintWriter(new LogWriter(TAG));
        if (this.mActivity != null) {
            try {
                this.mActivity.dump("  ", (FileDescriptor) null, printWriter, new String[0]);
            } catch (Exception e) {
                Log.e(TAG, "Failed dumping state", e);
            }
        } else {
            try {
                dump("  ", (FileDescriptor) null, printWriter, new String[0]);
            } catch (Exception e2) {
                Log.e(TAG, "Failed dumping state", e2);
            }
        }
        throw runtimeException;
    }

    public static int transitToStyleIndex(int i, boolean z) {
        switch (i) {
            case FragmentTransaction.TRANSIT_FRAGMENT_OPEN:
                return z ? 1 : 2;
            case FragmentTransaction.TRANSIT_FRAGMENT_FADE:
                return z ? 5 : 6;
            case FragmentTransaction.TRANSIT_FRAGMENT_CLOSE:
                return z ? 3 : 4;
            default:
                return -1;
        }
    }

    /* access modifiers changed from: package-private */
    public void addBackStackState(BackStackRecord backStackRecord) {
        if (this.mBackStack == null) {
            this.mBackStack = new ArrayList<>();
        }
        this.mBackStack.add(backStackRecord);
        reportBackStackChanged();
    }

    public void addFragment(Fragment fragment, boolean z) {
        if (this.mAdded == null) {
            this.mAdded = new ArrayList<>();
        }
        if (DEBUG) {
            Log.v(TAG, "add: " + fragment);
        }
        makeActive(fragment);
        if (fragment.mDetached) {
            return;
        }
        if (this.mAdded.contains(fragment)) {
            throw new IllegalStateException("Fragment already added: " + fragment);
        }
        this.mAdded.add(fragment);
        fragment.mAdded = true;
        fragment.mRemoving = false;
        if (fragment.mHasMenu && fragment.mMenuVisible) {
            this.mNeedMenuInvalidate = true;
        }
        if (z) {
            moveToState(fragment);
        }
    }

    public void addOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener onBackStackChangedListener) {
        if (this.mBackStackChangeListeners == null) {
            this.mBackStackChangeListeners = new ArrayList<>();
        }
        this.mBackStackChangeListeners.add(onBackStackChangedListener);
    }

    public int allocBackStackIndex(BackStackRecord backStackRecord) {
        synchronized (this) {
            if (this.mAvailBackStackIndices == null || this.mAvailBackStackIndices.size() <= 0) {
                if (this.mBackStackIndices == null) {
                    this.mBackStackIndices = new ArrayList<>();
                }
                int size = this.mBackStackIndices.size();
                if (DEBUG) {
                    Log.v(TAG, "Setting back stack index " + size + " to " + backStackRecord);
                }
                this.mBackStackIndices.add(backStackRecord);
                return size;
            }
            int intValue = this.mAvailBackStackIndices.remove(this.mAvailBackStackIndices.size() - 1).intValue();
            if (DEBUG) {
                Log.v(TAG, "Adding back stack index " + intValue + " with " + backStackRecord);
            }
            this.mBackStackIndices.set(intValue, backStackRecord);
            return intValue;
        }
    }

    public void attachActivity(FragmentActivity fragmentActivity, FragmentContainer fragmentContainer, Fragment fragment) {
        if (this.mActivity != null) {
            throw new IllegalStateException("Already attached");
        }
        this.mActivity = fragmentActivity;
        this.mContainer = fragmentContainer;
        this.mParent = fragment;
    }

    public void attachFragment(Fragment fragment, int i, int i2) {
        if (DEBUG) {
            Log.v(TAG, "attach: " + fragment);
        }
        if (fragment.mDetached) {
            fragment.mDetached = false;
            if (!fragment.mAdded) {
                if (this.mAdded == null) {
                    this.mAdded = new ArrayList<>();
                }
                if (this.mAdded.contains(fragment)) {
                    throw new IllegalStateException("Fragment already added: " + fragment);
                }
                if (DEBUG) {
                    Log.v(TAG, "add from attach: " + fragment);
                }
                this.mAdded.add(fragment);
                fragment.mAdded = true;
                if (fragment.mHasMenu && fragment.mMenuVisible) {
                    this.mNeedMenuInvalidate = true;
                }
                moveToState(fragment, this.mCurState, i, i2, false);
            }
        }
    }

    public FragmentTransaction beginTransaction() {
        return new BackStackRecord(this);
    }

    public void detachFragment(Fragment fragment, int i, int i2) {
        if (DEBUG) {
            Log.v(TAG, "detach: " + fragment);
        }
        if (!fragment.mDetached) {
            fragment.mDetached = true;
            if (fragment.mAdded) {
                if (this.mAdded != null) {
                    if (DEBUG) {
                        Log.v(TAG, "remove from detach: " + fragment);
                    }
                    this.mAdded.remove(fragment);
                }
                if (fragment.mHasMenu && fragment.mMenuVisible) {
                    this.mNeedMenuInvalidate = true;
                }
                fragment.mAdded = false;
                moveToState(fragment, 1, i, i2, false);
            }
        }
    }

    public void dispatchActivityCreated() {
        this.mStateSaved = false;
        moveToState(2, false);
    }

    public void dispatchConfigurationChanged(Configuration configuration) {
        if (this.mAdded != null) {
            for (int i = 0; i < this.mAdded.size(); i++) {
                Fragment fragment = this.mAdded.get(i);
                if (fragment != null) {
                    fragment.performConfigurationChanged(configuration);
                }
            }
        }
    }

    public boolean dispatchContextItemSelected(MenuItem menuItem) {
        if (this.mAdded != null) {
            for (int i = 0; i < this.mAdded.size(); i++) {
                Fragment fragment = this.mAdded.get(i);
                if (fragment != null && fragment.performContextItemSelected(menuItem)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void dispatchCreate() {
        this.mStateSaved = false;
        moveToState(1, false);
    }

    public boolean dispatchCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        boolean z = false;
        ArrayList<Fragment> arrayList = null;
        if (this.mAdded != null) {
            for (int i = 0; i < this.mAdded.size(); i++) {
                Fragment fragment = this.mAdded.get(i);
                if (fragment != null && fragment.performCreateOptionsMenu(menu, menuInflater)) {
                    z = true;
                    if (arrayList == null) {
                        arrayList = new ArrayList<>();
                    }
                    arrayList.add(fragment);
                }
            }
        }
        if (this.mCreatedMenus != null) {
            for (int i2 = 0; i2 < this.mCreatedMenus.size(); i2++) {
                Fragment fragment2 = this.mCreatedMenus.get(i2);
                if (arrayList == null || !arrayList.contains(fragment2)) {
                    fragment2.onDestroyOptionsMenu();
                }
            }
        }
        this.mCreatedMenus = arrayList;
        return z;
    }

    public void dispatchDestroy() {
        this.mDestroyed = true;
        execPendingActions();
        moveToState(0, false);
        this.mActivity = null;
        this.mContainer = null;
        this.mParent = null;
    }

    public void dispatchDestroyView() {
        moveToState(1, false);
    }

    public void dispatchLowMemory() {
        if (this.mAdded != null) {
            for (int i = 0; i < this.mAdded.size(); i++) {
                Fragment fragment = this.mAdded.get(i);
                if (fragment != null) {
                    fragment.performLowMemory();
                }
            }
        }
    }

    public boolean dispatchOptionsItemSelected(MenuItem menuItem) {
        if (this.mAdded != null) {
            for (int i = 0; i < this.mAdded.size(); i++) {
                Fragment fragment = this.mAdded.get(i);
                if (fragment != null && fragment.performOptionsItemSelected(menuItem)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void dispatchOptionsMenuClosed(Menu menu) {
        if (this.mAdded != null) {
            for (int i = 0; i < this.mAdded.size(); i++) {
                Fragment fragment = this.mAdded.get(i);
                if (fragment != null) {
                    fragment.performOptionsMenuClosed(menu);
                }
            }
        }
    }

    public void dispatchPause() {
        moveToState(4, false);
    }

    public boolean dispatchPrepareOptionsMenu(Menu menu) {
        boolean z = false;
        if (this.mAdded != null) {
            for (int i = 0; i < this.mAdded.size(); i++) {
                Fragment fragment = this.mAdded.get(i);
                if (fragment != null && fragment.performPrepareOptionsMenu(menu)) {
                    z = true;
                }
            }
        }
        return z;
    }

    public void dispatchReallyStop() {
        moveToState(2, false);
    }

    public void dispatchResume() {
        this.mStateSaved = false;
        moveToState(5, false);
    }

    public void dispatchStart() {
        this.mStateSaved = false;
        moveToState(4, false);
    }

    public void dispatchStop() {
        this.mStateSaved = true;
        moveToState(3, false);
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        int size;
        int size2;
        int size3;
        int size4;
        int size5;
        int size6;
        String str2 = str + "    ";
        if (this.mActive != null && (size6 = this.mActive.size()) > 0) {
            printWriter.print(str);
            printWriter.print("Active Fragments in ");
            printWriter.print(Integer.toHexString(System.identityHashCode(this)));
            printWriter.println(":");
            for (int i = 0; i < size6; i++) {
                Fragment fragment = this.mActive.get(i);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i);
                printWriter.print(": ");
                printWriter.println(fragment);
                if (fragment != null) {
                    fragment.dump(str2, fileDescriptor, printWriter, strArr);
                }
            }
        }
        if (this.mAdded != null && (size5 = this.mAdded.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Added Fragments:");
            for (int i2 = 0; i2 < size5; i2++) {
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i2);
                printWriter.print(": ");
                printWriter.println(this.mAdded.get(i2).toString());
            }
        }
        if (this.mCreatedMenus != null && (size4 = this.mCreatedMenus.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Fragments Created Menus:");
            for (int i3 = 0; i3 < size4; i3++) {
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i3);
                printWriter.print(": ");
                printWriter.println(this.mCreatedMenus.get(i3).toString());
            }
        }
        if (this.mBackStack != null && (size3 = this.mBackStack.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Back Stack:");
            for (int i4 = 0; i4 < size3; i4++) {
                BackStackRecord backStackRecord = this.mBackStack.get(i4);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i4);
                printWriter.print(": ");
                printWriter.println(backStackRecord.toString());
                backStackRecord.dump(str2, fileDescriptor, printWriter, strArr);
            }
        }
        synchronized (this) {
            if (this.mBackStackIndices != null && (size2 = this.mBackStackIndices.size()) > 0) {
                printWriter.print(str);
                printWriter.println("Back Stack Indices:");
                for (int i5 = 0; i5 < size2; i5++) {
                    printWriter.print(str);
                    printWriter.print("  #");
                    printWriter.print(i5);
                    printWriter.print(": ");
                    printWriter.println(this.mBackStackIndices.get(i5));
                }
            }
            if (this.mAvailBackStackIndices != null && this.mAvailBackStackIndices.size() > 0) {
                printWriter.print(str);
                printWriter.print("mAvailBackStackIndices: ");
                printWriter.println(Arrays.toString(this.mAvailBackStackIndices.toArray()));
            }
        }
        if (this.mPendingActions != null && (size = this.mPendingActions.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Pending Actions:");
            for (int i6 = 0; i6 < size; i6++) {
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i6);
                printWriter.print(": ");
                printWriter.println(this.mPendingActions.get(i6));
            }
        }
        printWriter.print(str);
        printWriter.println("FragmentManager misc state:");
        printWriter.print(str);
        printWriter.print("  mActivity=");
        printWriter.println(this.mActivity);
        printWriter.print(str);
        printWriter.print("  mContainer=");
        printWriter.println(this.mContainer);
        if (this.mParent != null) {
            printWriter.print(str);
            printWriter.print("  mParent=");
            printWriter.println(this.mParent);
        }
        printWriter.print(str);
        printWriter.print("  mCurState=");
        printWriter.print(this.mCurState);
        printWriter.print(" mStateSaved=");
        printWriter.print(this.mStateSaved);
        printWriter.print(" mDestroyed=");
        printWriter.println(this.mDestroyed);
        if (this.mNeedMenuInvalidate) {
            printWriter.print(str);
            printWriter.print("  mNeedMenuInvalidate=");
            printWriter.println(this.mNeedMenuInvalidate);
        }
        if (this.mNoTransactionsBecause != null) {
            printWriter.print(str);
            printWriter.print("  mNoTransactionsBecause=");
            printWriter.println(this.mNoTransactionsBecause);
        }
        if (this.mAvailIndices != null && this.mAvailIndices.size() > 0) {
            printWriter.print(str);
            printWriter.print("  mAvailIndices: ");
            printWriter.println(Arrays.toString(this.mAvailIndices.toArray()));
        }
    }

    public void enqueueAction(Runnable runnable, boolean z) {
        if (!z) {
            checkStateLoss();
        }
        synchronized (this) {
            if (this.mDestroyed || this.mActivity == null) {
                throw new IllegalStateException("Activity has been destroyed");
            }
            if (this.mPendingActions == null) {
                this.mPendingActions = new ArrayList<>();
            }
            this.mPendingActions.add(runnable);
            if (this.mPendingActions.size() == 1) {
                this.mActivity.mHandler.removeCallbacks(this.mExecCommit);
                this.mActivity.mHandler.post(this.mExecCommit);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0081, code lost:
        r8.mExecutingActions = true;
        r2 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0085, code lost:
        if (r2 >= r4) goto L_0x0099;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0087, code lost:
        r8.mTmpActions[r2].run();
        r8.mTmpActions[r2] = null;
        r2 = r2 + 1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean execPendingActions() {
        /*
            r8 = this;
            r7 = 0
            boolean r5 = r8.mExecutingActions
            if (r5 == 0) goto L_0x000d
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "Recursive entry to executePendingTransactions"
            r5.<init>(r6)
            throw r5
        L_0x000d:
            android.os.Looper r5 = android.os.Looper.myLooper()
            android.support.v4.app.FragmentActivity r6 = r8.mActivity
            android.os.Handler r6 = r6.mHandler
            android.os.Looper r6 = r6.getLooper()
            if (r5 == r6) goto L_0x0023
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "Must be called from main thread of process"
            r5.<init>(r6)
            throw r5
        L_0x0023:
            r0 = 0
        L_0x0024:
            monitor-enter(r8)
            java.util.ArrayList<java.lang.Runnable> r5 = r8.mPendingActions     // Catch:{ all -> 0x0096 }
            if (r5 == 0) goto L_0x0031
            java.util.ArrayList<java.lang.Runnable> r5 = r8.mPendingActions     // Catch:{ all -> 0x0096 }
            int r5 = r5.size()     // Catch:{ all -> 0x0096 }
            if (r5 != 0) goto L_0x0058
        L_0x0031:
            monitor-exit(r8)     // Catch:{ all -> 0x0096 }
            boolean r5 = r8.mHavePendingDeferredStart
            if (r5 == 0) goto L_0x00a4
            r3 = 0
            r2 = 0
        L_0x0038:
            java.util.ArrayList<android.support.v4.app.Fragment> r5 = r8.mActive
            int r5 = r5.size()
            if (r2 >= r5) goto L_0x009d
            java.util.ArrayList<android.support.v4.app.Fragment> r5 = r8.mActive
            java.lang.Object r1 = r5.get(r2)
            android.support.v4.app.Fragment r1 = (android.support.v4.app.Fragment) r1
            if (r1 == 0) goto L_0x0055
            android.support.v4.app.LoaderManagerImpl r5 = r1.mLoaderManager
            if (r5 == 0) goto L_0x0055
            android.support.v4.app.LoaderManagerImpl r5 = r1.mLoaderManager
            boolean r5 = r5.hasRunningLoaders()
            r3 = r3 | r5
        L_0x0055:
            int r2 = r2 + 1
            goto L_0x0038
        L_0x0058:
            java.util.ArrayList<java.lang.Runnable> r5 = r8.mPendingActions     // Catch:{ all -> 0x0096 }
            int r4 = r5.size()     // Catch:{ all -> 0x0096 }
            java.lang.Runnable[] r5 = r8.mTmpActions     // Catch:{ all -> 0x0096 }
            if (r5 == 0) goto L_0x0067
            java.lang.Runnable[] r5 = r8.mTmpActions     // Catch:{ all -> 0x0096 }
            int r5 = r5.length     // Catch:{ all -> 0x0096 }
            if (r5 >= r4) goto L_0x006b
        L_0x0067:
            java.lang.Runnable[] r5 = new java.lang.Runnable[r4]     // Catch:{ all -> 0x0096 }
            r8.mTmpActions = r5     // Catch:{ all -> 0x0096 }
        L_0x006b:
            java.util.ArrayList<java.lang.Runnable> r5 = r8.mPendingActions     // Catch:{ all -> 0x0096 }
            java.lang.Runnable[] r6 = r8.mTmpActions     // Catch:{ all -> 0x0096 }
            r5.toArray(r6)     // Catch:{ all -> 0x0096 }
            java.util.ArrayList<java.lang.Runnable> r5 = r8.mPendingActions     // Catch:{ all -> 0x0096 }
            r5.clear()     // Catch:{ all -> 0x0096 }
            android.support.v4.app.FragmentActivity r5 = r8.mActivity     // Catch:{ all -> 0x0096 }
            android.os.Handler r5 = r5.mHandler     // Catch:{ all -> 0x0096 }
            java.lang.Runnable r6 = r8.mExecCommit     // Catch:{ all -> 0x0096 }
            r5.removeCallbacks(r6)     // Catch:{ all -> 0x0096 }
            monitor-exit(r8)     // Catch:{ all -> 0x0096 }
            r5 = 1
            r8.mExecutingActions = r5
            r2 = 0
        L_0x0085:
            if (r2 >= r4) goto L_0x0099
            java.lang.Runnable[] r5 = r8.mTmpActions
            r5 = r5[r2]
            r5.run()
            java.lang.Runnable[] r5 = r8.mTmpActions
            r6 = 0
            r5[r2] = r6
            int r2 = r2 + 1
            goto L_0x0085
        L_0x0096:
            r5 = move-exception
            monitor-exit(r8)     // Catch:{ all -> 0x0096 }
            throw r5
        L_0x0099:
            r8.mExecutingActions = r7
            r0 = 1
            goto L_0x0024
        L_0x009d:
            if (r3 != 0) goto L_0x00a4
            r8.mHavePendingDeferredStart = r7
            r8.startPendingDeferredFragments()
        L_0x00a4:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.FragmentManagerImpl.execPendingActions():boolean");
    }

    public boolean executePendingTransactions() {
        return execPendingActions();
    }

    public Fragment findFragmentById(int i) {
        if (this.mAdded != null) {
            for (int size = this.mAdded.size() - 1; size >= 0; size--) {
                Fragment fragment = this.mAdded.get(size);
                if (fragment != null && fragment.mFragmentId == i) {
                    return fragment;
                }
            }
        }
        if (this.mActive != null) {
            for (int size2 = this.mActive.size() - 1; size2 >= 0; size2--) {
                Fragment fragment2 = this.mActive.get(size2);
                if (fragment2 != null && fragment2.mFragmentId == i) {
                    return fragment2;
                }
            }
        }
        return null;
    }

    public Fragment findFragmentByTag(String str) {
        if (!(this.mAdded == null || str == null)) {
            for (int size = this.mAdded.size() - 1; size >= 0; size--) {
                Fragment fragment = this.mAdded.get(size);
                if (fragment != null && str.equals(fragment.mTag)) {
                    return fragment;
                }
            }
        }
        if (!(this.mActive == null || str == null)) {
            for (int size2 = this.mActive.size() - 1; size2 >= 0; size2--) {
                Fragment fragment2 = this.mActive.get(size2);
                if (fragment2 != null && str.equals(fragment2.mTag)) {
                    return fragment2;
                }
            }
        }
        return null;
    }

    public Fragment findFragmentByWho(String str) {
        Fragment findFragmentByWho;
        if (!(this.mActive == null || str == null)) {
            for (int size = this.mActive.size() - 1; size >= 0; size--) {
                Fragment fragment = this.mActive.get(size);
                if (fragment != null && (findFragmentByWho = fragment.findFragmentByWho(str)) != null) {
                    return findFragmentByWho;
                }
            }
        }
        return null;
    }

    public void freeBackStackIndex(int i) {
        synchronized (this) {
            this.mBackStackIndices.set(i, (Object) null);
            if (this.mAvailBackStackIndices == null) {
                this.mAvailBackStackIndices = new ArrayList<>();
            }
            if (DEBUG) {
                Log.v(TAG, "Freeing back stack index " + i);
            }
            this.mAvailBackStackIndices.add(Integer.valueOf(i));
        }
    }

    public FragmentManager.BackStackEntry getBackStackEntryAt(int i) {
        return this.mBackStack.get(i);
    }

    public int getBackStackEntryCount() {
        if (this.mBackStack != null) {
            return this.mBackStack.size();
        }
        return 0;
    }

    public Fragment getFragment(Bundle bundle, String str) {
        int i = bundle.getInt(str, -1);
        if (i == -1) {
            return null;
        }
        if (i >= this.mActive.size()) {
            throwException(new IllegalStateException("Fragment no longer exists for key " + str + ": index " + i));
        }
        Fragment fragment = this.mActive.get(i);
        if (fragment != null) {
            return fragment;
        }
        throwException(new IllegalStateException("Fragment no longer exists for key " + str + ": index " + i));
        return fragment;
    }

    public List<Fragment> getFragments() {
        return this.mActive;
    }

    /* access modifiers changed from: package-private */
    public LayoutInflater.Factory getLayoutInflaterFactory() {
        return this;
    }

    public void hideFragment(Fragment fragment, int i, int i2) {
        if (DEBUG) {
            Log.v(TAG, "hide: " + fragment);
        }
        if (!fragment.mHidden) {
            fragment.mHidden = true;
            if (fragment.mView != null) {
                Animation loadAnimation = loadAnimation(fragment, i, false, i2);
                if (loadAnimation != null) {
                    fragment.mView.startAnimation(loadAnimation);
                }
                fragment.mView.setVisibility(8);
            }
            if (fragment.mAdded && fragment.mHasMenu && fragment.mMenuVisible) {
                this.mNeedMenuInvalidate = true;
            }
            fragment.onHiddenChanged(true);
        }
    }

    public boolean isDestroyed() {
        return this.mDestroyed;
    }

    /* access modifiers changed from: package-private */
    public Animation loadAnimation(Fragment fragment, int i, boolean z, int i2) {
        Animation loadAnimation;
        Animation onCreateAnimation = fragment.onCreateAnimation(i, z, fragment.mNextAnim);
        if (onCreateAnimation != null) {
            return onCreateAnimation;
        }
        if (fragment.mNextAnim != 0 && (loadAnimation = AnimationUtils.loadAnimation(this.mActivity, fragment.mNextAnim)) != null) {
            return loadAnimation;
        }
        if (i == 0) {
            return null;
        }
        int transitToStyleIndex = transitToStyleIndex(i, z);
        if (transitToStyleIndex < 0) {
            return null;
        }
        switch (transitToStyleIndex) {
            case 1:
                return makeOpenCloseAnimation(this.mActivity, 1.125f, 1.0f, 0.0f, 1.0f);
            case 2:
                return makeOpenCloseAnimation(this.mActivity, 1.0f, 0.975f, 1.0f, 0.0f);
            case 3:
                return makeOpenCloseAnimation(this.mActivity, 0.975f, 1.0f, 0.0f, 1.0f);
            case 4:
                return makeOpenCloseAnimation(this.mActivity, 1.0f, 1.075f, 1.0f, 0.0f);
            case 5:
                return makeFadeAnimation(this.mActivity, 0.0f, 1.0f);
            case 6:
                return makeFadeAnimation(this.mActivity, 1.0f, 0.0f);
            default:
                if (i2 == 0 && this.mActivity.getWindow() != null) {
                    i2 = this.mActivity.getWindow().getAttributes().windowAnimations;
                }
                return i2 == 0 ? null : null;
        }
    }

    /* access modifiers changed from: package-private */
    public void makeActive(Fragment fragment) {
        if (fragment.mIndex < 0) {
            if (this.mAvailIndices == null || this.mAvailIndices.size() <= 0) {
                if (this.mActive == null) {
                    this.mActive = new ArrayList<>();
                }
                fragment.setIndex(this.mActive.size(), this.mParent);
                this.mActive.add(fragment);
            } else {
                fragment.setIndex(this.mAvailIndices.remove(this.mAvailIndices.size() - 1).intValue(), this.mParent);
                this.mActive.set(fragment.mIndex, fragment);
            }
            if (DEBUG) {
                Log.v(TAG, "Allocated fragment index " + fragment);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void makeInactive(Fragment fragment) {
        if (fragment.mIndex >= 0) {
            if (DEBUG) {
                Log.v(TAG, "Freeing fragment index " + fragment);
            }
            this.mActive.set(fragment.mIndex, (Object) null);
            if (this.mAvailIndices == null) {
                this.mAvailIndices = new ArrayList<>();
            }
            this.mAvailIndices.add(Integer.valueOf(fragment.mIndex));
            this.mActivity.invalidateSupportFragment(fragment.mWho);
            fragment.initState();
        }
    }

    /* access modifiers changed from: package-private */
    public void moveToState(int i, int i2, int i3, boolean z) {
        if (this.mActivity == null && i != 0) {
            throw new IllegalStateException("No activity");
        } else if (z || this.mCurState != i) {
            this.mCurState = i;
            if (this.mActive != null) {
                boolean z2 = false;
                for (int i4 = 0; i4 < this.mActive.size(); i4++) {
                    Fragment fragment = this.mActive.get(i4);
                    if (fragment != null) {
                        moveToState(fragment, i, i2, i3, false);
                        if (fragment.mLoaderManager != null) {
                            z2 |= fragment.mLoaderManager.hasRunningLoaders();
                        }
                    }
                }
                if (!z2) {
                    startPendingDeferredFragments();
                }
                if (this.mNeedMenuInvalidate && this.mActivity != null && this.mCurState == 5) {
                    this.mActivity.supportInvalidateOptionsMenu();
                    this.mNeedMenuInvalidate = false;
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void moveToState(int i, boolean z) {
        moveToState(i, 0, 0, z);
    }

    /* access modifiers changed from: package-private */
    public void moveToState(Fragment fragment) {
        moveToState(fragment, this.mCurState, 0, 0, false);
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:100:0x020b, code lost:
        r11.mSavedFragmentState = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x020f, code lost:
        if (r12 <= 3) goto L_0x0230;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x0213, code lost:
        if (DEBUG == false) goto L_0x022d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:0x0215, code lost:
        android.util.Log.v(TAG, "moveto STARTED: " + r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:106:0x022d, code lost:
        r11.performStart();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:108:0x0231, code lost:
        if (r12 <= 4) goto L_0x0046;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:0x0235, code lost:
        if (DEBUG == false) goto L_0x024f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:111:0x0237, code lost:
        android.util.Log.v(TAG, "moveto RESUMED: " + r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:112:0x024f, code lost:
        r11.mResumed = true;
        r11.performResume();
        r11.mSavedFragmentState = null;
        r11.mSavedViewState = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:115:0x026c, code lost:
        r11.mView = android.support.v4.app.NoSaveStateFrameLayout.wrap(r11.mView);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:116:0x0276, code lost:
        r11.mInnerView = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:122:0x0286, code lost:
        if (r12 >= 1) goto L_0x0046;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:124:0x028a, code lost:
        if (r10.mDestroyed == false) goto L_0x0298;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:126:0x028e, code lost:
        if (r11.mAnimatingAway == null) goto L_0x0298;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:127:0x0290, code lost:
        r9 = r11.mAnimatingAway;
        r11.mAnimatingAway = null;
        r9.clearAnimation();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:129:0x029a, code lost:
        if (r11.mAnimatingAway == null) goto L_0x037d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:130:0x029c, code lost:
        r11.mStateAfterAnimating = r12;
        r12 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:138:0x02c7, code lost:
        if (r12 >= 4) goto L_0x02e8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:140:0x02cb, code lost:
        if (DEBUG == false) goto L_0x02e5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:141:0x02cd, code lost:
        android.util.Log.v(TAG, "movefrom STARTED: " + r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:142:0x02e5, code lost:
        r11.performStop();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:144:0x02e9, code lost:
        if (r12 >= 3) goto L_0x030a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:146:0x02ed, code lost:
        if (DEBUG == false) goto L_0x0307;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:147:0x02ef, code lost:
        android.util.Log.v(TAG, "movefrom STOPPED: " + r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:148:0x0307, code lost:
        r11.performReallyStop();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:150:0x030b, code lost:
        if (r12 >= 2) goto L_0x0285;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:152:0x030f, code lost:
        if (DEBUG == false) goto L_0x0329;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:153:0x0311, code lost:
        android.util.Log.v(TAG, "movefrom ACTIVITY_CREATED: " + r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:155:0x032b, code lost:
        if (r11.mView == null) goto L_0x033c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:157:0x0333, code lost:
        if (r10.mActivity.isFinishing() != false) goto L_0x033c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:159:0x0337, code lost:
        if (r11.mSavedViewState != null) goto L_0x033c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:160:0x0339, code lost:
        saveFragmentViewState(r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:161:0x033c, code lost:
        r11.performDestroyView();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:162:0x0341, code lost:
        if (r11.mView == null) goto L_0x0372;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:164:0x0345, code lost:
        if (r11.mContainer == null) goto L_0x0372;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:165:0x0347, code lost:
        r6 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:166:0x034a, code lost:
        if (r10.mCurState <= 0) goto L_0x0355;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:168:0x034e, code lost:
        if (r10.mDestroyed != false) goto L_0x0355;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:169:0x0350, code lost:
        r6 = loadAnimation(r11, r13, false, r14);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:170:0x0355, code lost:
        if (r6 == null) goto L_0x036b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:171:0x0357, code lost:
        r8 = r11;
        r11.mAnimatingAway = r11.mView;
        r11.mStateAfterAnimating = r12;
        r6.setAnimationListener(new android.support.v4.app.FragmentManagerImpl.AnonymousClass5(r10));
        r11.mView.startAnimation(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:172:0x036b, code lost:
        r11.mContainer.removeView(r11.mView);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:173:0x0372, code lost:
        r11.mContainer = null;
        r11.mView = null;
        r11.mInnerView = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:175:0x037f, code lost:
        if (DEBUG == false) goto L_0x0399;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:176:0x0381, code lost:
        android.util.Log.v(TAG, "movefrom CREATED: " + r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:178:0x039b, code lost:
        if (r11.mRetaining != false) goto L_0x03a0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:179:0x039d, code lost:
        r11.performDestroy();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:180:0x03a0, code lost:
        r11.mCalled = false;
        r11.onDetach();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:181:0x03a8, code lost:
        if (r11.mCalled != false) goto L_0x03c9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:183:0x03c8, code lost:
        throw new android.support.v4.app.SuperNotCalledException("Fragment " + r11 + " did not call through to super.onDetach()");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:184:0x03c9, code lost:
        if (r15 != false) goto L_0x0046;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:186:0x03cd, code lost:
        if (r11.mRetaining != false) goto L_0x03d4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:187:0x03cf, code lost:
        makeInactive(r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:188:0x03d4, code lost:
        r11.mActivity = null;
        r11.mParentFragment = null;
        r11.mFragmentManager = null;
        r11.mChildFragmentManager = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x013e, code lost:
        if (r12 <= 1) goto L_0x020e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x0142, code lost:
        if (DEBUG == false) goto L_0x015c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x0144, code lost:
        android.util.Log.v(TAG, "moveto ACTIVITY_CREATED: " + r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x015e, code lost:
        if (r11.mFromLayout != false) goto L_0x01fd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x0160, code lost:
        r7 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x0163, code lost:
        if (r11.mContainerId == 0) goto L_0x01b4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x0165, code lost:
        r7 = (android.view.ViewGroup) r10.mContainer.findViewById(r11.mContainerId);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x016f, code lost:
        if (r7 != null) goto L_0x01b4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x0173, code lost:
        if (r11.mRestored != false) goto L_0x01b4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x0175, code lost:
        throwException(new java.lang.IllegalArgumentException("No view found for id 0x" + java.lang.Integer.toHexString(r11.mContainerId) + " (" + r11.getResources().getResourceName(r11.mContainerId) + ") for fragment " + r11));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x01b4, code lost:
        r11.mContainer = r7;
        r11.mView = r11.performCreateView(r11.getLayoutInflater(r11.mSavedFragmentState), r7, r11.mSavedFragmentState);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x01c6, code lost:
        if (r11.mView == null) goto L_0x0276;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x01c8, code lost:
        r11.mInnerView = r11.mView;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x01d0, code lost:
        if (android.os.Build.VERSION.SDK_INT < 11) goto L_0x026c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x01d2, code lost:
        android.support.v4.view.ViewCompat.setSaveFromParentEnabled(r11.mView, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x01d8, code lost:
        if (r7 == null) goto L_0x01eb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x01da, code lost:
        r6 = loadAnimation(r11, r13, true, r14);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x01df, code lost:
        if (r6 == null) goto L_0x01e6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x01e1, code lost:
        r11.mView.startAnimation(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x01e6, code lost:
        r7.addView(r11.mView);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x01ed, code lost:
        if (r11.mHidden == false) goto L_0x01f6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x01ef, code lost:
        r11.mView.setVisibility(8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x01f6, code lost:
        r11.onViewCreated(r11.mView, r11.mSavedFragmentState);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x01fd, code lost:
        r11.performActivityCreated(r11.mSavedFragmentState);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x0204, code lost:
        if (r11.mView == null) goto L_0x020b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x0206, code lost:
        r11.restoreViewState(r11.mSavedFragmentState);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void moveToState(android.support.v4.app.Fragment r11, int r12, int r13, int r14, boolean r15) {
        /*
            r10 = this;
            boolean r0 = r11.mAdded
            if (r0 == 0) goto L_0x0008
            boolean r0 = r11.mDetached
            if (r0 == 0) goto L_0x000c
        L_0x0008:
            r0 = 1
            if (r12 <= r0) goto L_0x000c
            r12 = 1
        L_0x000c:
            boolean r0 = r11.mRemoving
            if (r0 == 0) goto L_0x0016
            int r0 = r11.mState
            if (r12 <= r0) goto L_0x0016
            int r12 = r11.mState
        L_0x0016:
            boolean r0 = r11.mDeferStart
            if (r0 == 0) goto L_0x0023
            int r0 = r11.mState
            r1 = 4
            if (r0 >= r1) goto L_0x0023
            r0 = 3
            if (r12 <= r0) goto L_0x0023
            r12 = 3
        L_0x0023:
            int r0 = r11.mState
            if (r0 >= r12) goto L_0x027a
            boolean r0 = r11.mFromLayout
            if (r0 == 0) goto L_0x0030
            boolean r0 = r11.mInLayout
            if (r0 != 0) goto L_0x0030
        L_0x002f:
            return
        L_0x0030:
            android.view.View r0 = r11.mAnimatingAway
            if (r0 == 0) goto L_0x0041
            r0 = 0
            r11.mAnimatingAway = r0
            int r2 = r11.mStateAfterAnimating
            r3 = 0
            r4 = 0
            r5 = 1
            r0 = r10
            r1 = r11
            r0.moveToState(r1, r2, r3, r4, r5)
        L_0x0041:
            int r0 = r11.mState
            switch(r0) {
                case 0: goto L_0x0049;
                case 1: goto L_0x013d;
                case 2: goto L_0x020e;
                case 3: goto L_0x020e;
                case 4: goto L_0x0230;
                default: goto L_0x0046;
            }
        L_0x0046:
            r11.mState = r12
            goto L_0x002f
        L_0x0049:
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x0065
            java.lang.String r0 = "FragmentManager"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "moveto CREATED: "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r11)
            java.lang.String r1 = r1.toString()
            android.util.Log.v(r0, r1)
        L_0x0065:
            android.os.Bundle r0 = r11.mSavedFragmentState
            if (r0 == 0) goto L_0x00ad
            android.os.Bundle r0 = r11.mSavedFragmentState
            android.support.v4.app.FragmentActivity r1 = r10.mActivity
            java.lang.ClassLoader r1 = r1.getClassLoader()
            r0.setClassLoader(r1)
            android.os.Bundle r0 = r11.mSavedFragmentState
            java.lang.String r1 = "android:view_state"
            android.util.SparseArray r0 = r0.getSparseParcelableArray(r1)
            r11.mSavedViewState = r0
            android.os.Bundle r0 = r11.mSavedFragmentState
            java.lang.String r1 = "android:target_state"
            android.support.v4.app.Fragment r0 = r10.getFragment(r0, r1)
            r11.mTarget = r0
            android.support.v4.app.Fragment r0 = r11.mTarget
            if (r0 == 0) goto L_0x0097
            android.os.Bundle r0 = r11.mSavedFragmentState
            java.lang.String r1 = "android:target_req_state"
            r2 = 0
            int r0 = r0.getInt(r1, r2)
            r11.mTargetRequestCode = r0
        L_0x0097:
            android.os.Bundle r0 = r11.mSavedFragmentState
            java.lang.String r1 = "android:user_visible_hint"
            r2 = 1
            boolean r0 = r0.getBoolean(r1, r2)
            r11.mUserVisibleHint = r0
            boolean r0 = r11.mUserVisibleHint
            if (r0 != 0) goto L_0x00ad
            r0 = 1
            r11.mDeferStart = r0
            r0 = 3
            if (r12 <= r0) goto L_0x00ad
            r12 = 3
        L_0x00ad:
            android.support.v4.app.FragmentActivity r0 = r10.mActivity
            r11.mActivity = r0
            android.support.v4.app.Fragment r0 = r10.mParent
            r11.mParentFragment = r0
            android.support.v4.app.Fragment r0 = r10.mParent
            if (r0 == 0) goto L_0x00ea
            android.support.v4.app.Fragment r0 = r10.mParent
            android.support.v4.app.FragmentManagerImpl r0 = r0.mChildFragmentManager
        L_0x00bd:
            r11.mFragmentManager = r0
            r0 = 0
            r11.mCalled = r0
            android.support.v4.app.FragmentActivity r0 = r10.mActivity
            r11.onAttach(r0)
            boolean r0 = r11.mCalled
            if (r0 != 0) goto L_0x00ef
            android.support.v4.app.SuperNotCalledException r0 = new android.support.v4.app.SuperNotCalledException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Fragment "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r11)
            java.lang.String r2 = " did not call through to super.onAttach()"
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x00ea:
            android.support.v4.app.FragmentActivity r0 = r10.mActivity
            android.support.v4.app.FragmentManagerImpl r0 = r0.mFragments
            goto L_0x00bd
        L_0x00ef:
            android.support.v4.app.Fragment r0 = r11.mParentFragment
            if (r0 != 0) goto L_0x00f8
            android.support.v4.app.FragmentActivity r0 = r10.mActivity
            r0.onAttachFragment(r11)
        L_0x00f8:
            boolean r0 = r11.mRetaining
            if (r0 != 0) goto L_0x0101
            android.os.Bundle r0 = r11.mSavedFragmentState
            r11.performCreate(r0)
        L_0x0101:
            r0 = 0
            r11.mRetaining = r0
            boolean r0 = r11.mFromLayout
            if (r0 == 0) goto L_0x013d
            android.os.Bundle r0 = r11.mSavedFragmentState
            android.view.LayoutInflater r0 = r11.getLayoutInflater(r0)
            r1 = 0
            android.os.Bundle r2 = r11.mSavedFragmentState
            android.view.View r0 = r11.performCreateView(r0, r1, r2)
            r11.mView = r0
            android.view.View r0 = r11.mView
            if (r0 == 0) goto L_0x0267
            android.view.View r0 = r11.mView
            r11.mInnerView = r0
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 11
            if (r0 < r1) goto L_0x025d
            android.view.View r0 = r11.mView
            r1 = 0
            android.support.v4.view.ViewCompat.setSaveFromParentEnabled(r0, r1)
        L_0x012b:
            boolean r0 = r11.mHidden
            if (r0 == 0) goto L_0x0136
            android.view.View r0 = r11.mView
            r1 = 8
            r0.setVisibility(r1)
        L_0x0136:
            android.view.View r0 = r11.mView
            android.os.Bundle r1 = r11.mSavedFragmentState
            r11.onViewCreated(r0, r1)
        L_0x013d:
            r0 = 1
            if (r12 <= r0) goto L_0x020e
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x015c
            java.lang.String r0 = "FragmentManager"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "moveto ACTIVITY_CREATED: "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r11)
            java.lang.String r1 = r1.toString()
            android.util.Log.v(r0, r1)
        L_0x015c:
            boolean r0 = r11.mFromLayout
            if (r0 != 0) goto L_0x01fd
            r7 = 0
            int r0 = r11.mContainerId
            if (r0 == 0) goto L_0x01b4
            android.support.v4.app.FragmentContainer r0 = r10.mContainer
            int r1 = r11.mContainerId
            android.view.View r7 = r0.findViewById(r1)
            android.view.ViewGroup r7 = (android.view.ViewGroup) r7
            if (r7 != 0) goto L_0x01b4
            boolean r0 = r11.mRestored
            if (r0 != 0) goto L_0x01b4
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "No view found for id 0x"
            java.lang.StringBuilder r1 = r1.append(r2)
            int r2 = r11.mContainerId
            java.lang.String r2 = java.lang.Integer.toHexString(r2)
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = " ("
            java.lang.StringBuilder r1 = r1.append(r2)
            android.content.res.Resources r2 = r11.getResources()
            int r3 = r11.mContainerId
            java.lang.String r2 = r2.getResourceName(r3)
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = ") for fragment "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r11)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            r10.throwException(r0)
        L_0x01b4:
            r11.mContainer = r7
            android.os.Bundle r0 = r11.mSavedFragmentState
            android.view.LayoutInflater r0 = r11.getLayoutInflater(r0)
            android.os.Bundle r1 = r11.mSavedFragmentState
            android.view.View r0 = r11.performCreateView(r0, r7, r1)
            r11.mView = r0
            android.view.View r0 = r11.mView
            if (r0 == 0) goto L_0x0276
            android.view.View r0 = r11.mView
            r11.mInnerView = r0
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 11
            if (r0 < r1) goto L_0x026c
            android.view.View r0 = r11.mView
            r1 = 0
            android.support.v4.view.ViewCompat.setSaveFromParentEnabled(r0, r1)
        L_0x01d8:
            if (r7 == 0) goto L_0x01eb
            r0 = 1
            android.view.animation.Animation r6 = r10.loadAnimation(r11, r13, r0, r14)
            if (r6 == 0) goto L_0x01e6
            android.view.View r0 = r11.mView
            r0.startAnimation(r6)
        L_0x01e6:
            android.view.View r0 = r11.mView
            r7.addView(r0)
        L_0x01eb:
            boolean r0 = r11.mHidden
            if (r0 == 0) goto L_0x01f6
            android.view.View r0 = r11.mView
            r1 = 8
            r0.setVisibility(r1)
        L_0x01f6:
            android.view.View r0 = r11.mView
            android.os.Bundle r1 = r11.mSavedFragmentState
            r11.onViewCreated(r0, r1)
        L_0x01fd:
            android.os.Bundle r0 = r11.mSavedFragmentState
            r11.performActivityCreated(r0)
            android.view.View r0 = r11.mView
            if (r0 == 0) goto L_0x020b
            android.os.Bundle r0 = r11.mSavedFragmentState
            r11.restoreViewState(r0)
        L_0x020b:
            r0 = 0
            r11.mSavedFragmentState = r0
        L_0x020e:
            r0 = 3
            if (r12 <= r0) goto L_0x0230
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x022d
            java.lang.String r0 = "FragmentManager"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "moveto STARTED: "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r11)
            java.lang.String r1 = r1.toString()
            android.util.Log.v(r0, r1)
        L_0x022d:
            r11.performStart()
        L_0x0230:
            r0 = 4
            if (r12 <= r0) goto L_0x0046
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x024f
            java.lang.String r0 = "FragmentManager"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "moveto RESUMED: "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r11)
            java.lang.String r1 = r1.toString()
            android.util.Log.v(r0, r1)
        L_0x024f:
            r0 = 1
            r11.mResumed = r0
            r11.performResume()
            r0 = 0
            r11.mSavedFragmentState = r0
            r0 = 0
            r11.mSavedViewState = r0
            goto L_0x0046
        L_0x025d:
            android.view.View r0 = r11.mView
            android.view.ViewGroup r0 = android.support.v4.app.NoSaveStateFrameLayout.wrap(r0)
            r11.mView = r0
            goto L_0x012b
        L_0x0267:
            r0 = 0
            r11.mInnerView = r0
            goto L_0x013d
        L_0x026c:
            android.view.View r0 = r11.mView
            android.view.ViewGroup r0 = android.support.v4.app.NoSaveStateFrameLayout.wrap(r0)
            r11.mView = r0
            goto L_0x01d8
        L_0x0276:
            r0 = 0
            r11.mInnerView = r0
            goto L_0x01fd
        L_0x027a:
            int r0 = r11.mState
            if (r0 <= r12) goto L_0x0046
            int r0 = r11.mState
            switch(r0) {
                case 1: goto L_0x0285;
                case 2: goto L_0x030a;
                case 3: goto L_0x02e8;
                case 4: goto L_0x02c6;
                case 5: goto L_0x02a1;
                default: goto L_0x0283;
            }
        L_0x0283:
            goto L_0x0046
        L_0x0285:
            r0 = 1
            if (r12 >= r0) goto L_0x0046
            boolean r0 = r10.mDestroyed
            if (r0 == 0) goto L_0x0298
            android.view.View r0 = r11.mAnimatingAway
            if (r0 == 0) goto L_0x0298
            android.view.View r9 = r11.mAnimatingAway
            r0 = 0
            r11.mAnimatingAway = r0
            r9.clearAnimation()
        L_0x0298:
            android.view.View r0 = r11.mAnimatingAway
            if (r0 == 0) goto L_0x037d
            r11.mStateAfterAnimating = r12
            r12 = 1
            goto L_0x0046
        L_0x02a1:
            r0 = 5
            if (r12 >= r0) goto L_0x02c6
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x02c0
            java.lang.String r0 = "FragmentManager"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "movefrom RESUMED: "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r11)
            java.lang.String r1 = r1.toString()
            android.util.Log.v(r0, r1)
        L_0x02c0:
            r11.performPause()
            r0 = 0
            r11.mResumed = r0
        L_0x02c6:
            r0 = 4
            if (r12 >= r0) goto L_0x02e8
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x02e5
            java.lang.String r0 = "FragmentManager"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "movefrom STARTED: "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r11)
            java.lang.String r1 = r1.toString()
            android.util.Log.v(r0, r1)
        L_0x02e5:
            r11.performStop()
        L_0x02e8:
            r0 = 3
            if (r12 >= r0) goto L_0x030a
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x0307
            java.lang.String r0 = "FragmentManager"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "movefrom STOPPED: "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r11)
            java.lang.String r1 = r1.toString()
            android.util.Log.v(r0, r1)
        L_0x0307:
            r11.performReallyStop()
        L_0x030a:
            r0 = 2
            if (r12 >= r0) goto L_0x0285
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x0329
            java.lang.String r0 = "FragmentManager"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "movefrom ACTIVITY_CREATED: "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r11)
            java.lang.String r1 = r1.toString()
            android.util.Log.v(r0, r1)
        L_0x0329:
            android.view.View r0 = r11.mView
            if (r0 == 0) goto L_0x033c
            android.support.v4.app.FragmentActivity r0 = r10.mActivity
            boolean r0 = r0.isFinishing()
            if (r0 != 0) goto L_0x033c
            android.util.SparseArray<android.os.Parcelable> r0 = r11.mSavedViewState
            if (r0 != 0) goto L_0x033c
            r10.saveFragmentViewState(r11)
        L_0x033c:
            r11.performDestroyView()
            android.view.View r0 = r11.mView
            if (r0 == 0) goto L_0x0372
            android.view.ViewGroup r0 = r11.mContainer
            if (r0 == 0) goto L_0x0372
            r6 = 0
            int r0 = r10.mCurState
            if (r0 <= 0) goto L_0x0355
            boolean r0 = r10.mDestroyed
            if (r0 != 0) goto L_0x0355
            r0 = 0
            android.view.animation.Animation r6 = r10.loadAnimation(r11, r13, r0, r14)
        L_0x0355:
            if (r6 == 0) goto L_0x036b
            r8 = r11
            android.view.View r0 = r11.mView
            r11.mAnimatingAway = r0
            r11.mStateAfterAnimating = r12
            android.support.v4.app.FragmentManagerImpl$5 r0 = new android.support.v4.app.FragmentManagerImpl$5
            r0.<init>(r8)
            r6.setAnimationListener(r0)
            android.view.View r0 = r11.mView
            r0.startAnimation(r6)
        L_0x036b:
            android.view.ViewGroup r0 = r11.mContainer
            android.view.View r1 = r11.mView
            r0.removeView(r1)
        L_0x0372:
            r0 = 0
            r11.mContainer = r0
            r0 = 0
            r11.mView = r0
            r0 = 0
            r11.mInnerView = r0
            goto L_0x0285
        L_0x037d:
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x0399
            java.lang.String r0 = "FragmentManager"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "movefrom CREATED: "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r11)
            java.lang.String r1 = r1.toString()
            android.util.Log.v(r0, r1)
        L_0x0399:
            boolean r0 = r11.mRetaining
            if (r0 != 0) goto L_0x03a0
            r11.performDestroy()
        L_0x03a0:
            r0 = 0
            r11.mCalled = r0
            r11.onDetach()
            boolean r0 = r11.mCalled
            if (r0 != 0) goto L_0x03c9
            android.support.v4.app.SuperNotCalledException r0 = new android.support.v4.app.SuperNotCalledException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Fragment "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r11)
            java.lang.String r2 = " did not call through to super.onDetach()"
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x03c9:
            if (r15 != 0) goto L_0x0046
            boolean r0 = r11.mRetaining
            if (r0 != 0) goto L_0x03d4
            r10.makeInactive(r11)
            goto L_0x0046
        L_0x03d4:
            r0 = 0
            r11.mActivity = r0
            r0 = 0
            r11.mParentFragment = r0
            r0 = 0
            r11.mFragmentManager = r0
            r0 = 0
            r11.mChildFragmentManager = r0
            goto L_0x0046
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.FragmentManagerImpl.moveToState(android.support.v4.app.Fragment, int, int, int, boolean):void");
    }

    public void noteStateNotSaved() {
        this.mStateSaved = false;
    }

    public View onCreateView(String str, Context context, AttributeSet attributeSet) {
        if (!"fragment".equals(str)) {
            return null;
        }
        String attributeValue = attributeSet.getAttributeValue((String) null, "class");
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, FragmentTag.Fragment);
        if (attributeValue == null) {
            attributeValue = obtainStyledAttributes.getString(0);
        }
        int resourceId = obtainStyledAttributes.getResourceId(1, -1);
        String string = obtainStyledAttributes.getString(2);
        obtainStyledAttributes.recycle();
        if (!Fragment.isSupportFragmentClass(this.mActivity, attributeValue)) {
            return null;
        }
        View view = null;
        int id = view != null ? view.getId() : 0;
        if (id == -1 && resourceId == -1 && string == null) {
            throw new IllegalArgumentException(attributeSet.getPositionDescription() + ": Must specify unique android:id, android:tag, or have a parent with an id for " + attributeValue);
        }
        Fragment findFragmentById = resourceId != -1 ? findFragmentById(resourceId) : null;
        if (findFragmentById == null && string != null) {
            findFragmentById = findFragmentByTag(string);
        }
        if (findFragmentById == null && id != -1) {
            findFragmentById = findFragmentById(id);
        }
        if (DEBUG) {
            Log.v(TAG, "onCreateView: id=0x" + Integer.toHexString(resourceId) + " fname=" + attributeValue + " existing=" + findFragmentById);
        }
        if (findFragmentById == null) {
            findFragmentById = Fragment.instantiate(context, attributeValue);
            findFragmentById.mFromLayout = true;
            findFragmentById.mFragmentId = resourceId != 0 ? resourceId : id;
            findFragmentById.mContainerId = id;
            findFragmentById.mTag = string;
            findFragmentById.mInLayout = true;
            findFragmentById.mFragmentManager = this;
            findFragmentById.onInflate(this.mActivity, attributeSet, findFragmentById.mSavedFragmentState);
            addFragment(findFragmentById, true);
        } else if (findFragmentById.mInLayout) {
            throw new IllegalArgumentException(attributeSet.getPositionDescription() + ": Duplicate id 0x" + Integer.toHexString(resourceId) + ", tag " + string + ", or parent id 0x" + Integer.toHexString(id) + " with another fragment for " + attributeValue);
        } else {
            findFragmentById.mInLayout = true;
            if (!findFragmentById.mRetaining) {
                findFragmentById.onInflate(this.mActivity, attributeSet, findFragmentById.mSavedFragmentState);
            }
        }
        if (this.mCurState >= 1 || !findFragmentById.mFromLayout) {
            moveToState(findFragmentById);
        } else {
            moveToState(findFragmentById, 1, 0, 0, false);
        }
        if (findFragmentById.mView == null) {
            throw new IllegalStateException("Fragment " + attributeValue + " did not create a view.");
        }
        if (resourceId != 0) {
            findFragmentById.mView.setId(resourceId);
        }
        if (findFragmentById.mView.getTag() == null) {
            findFragmentById.mView.setTag(string);
        }
        return findFragmentById.mView;
    }

    public void performPendingDeferredStart(Fragment fragment) {
        if (!fragment.mDeferStart) {
            return;
        }
        if (this.mExecutingActions) {
            this.mHavePendingDeferredStart = true;
            return;
        }
        fragment.mDeferStart = false;
        moveToState(fragment, this.mCurState, 0, 0, false);
    }

    public void popBackStack() {
        enqueueAction(new Runnable() {
            public void run() {
                FragmentManagerImpl.this.popBackStackState(FragmentManagerImpl.this.mActivity.mHandler, (String) null, -1, 0);
            }
        }, false);
    }

    public void popBackStack(final int i, final int i2) {
        if (i < 0) {
            throw new IllegalArgumentException("Bad id: " + i);
        }
        enqueueAction(new Runnable() {
            public void run() {
                FragmentManagerImpl.this.popBackStackState(FragmentManagerImpl.this.mActivity.mHandler, (String) null, i, i2);
            }
        }, false);
    }

    public void popBackStack(final String str, final int i) {
        enqueueAction(new Runnable() {
            public void run() {
                FragmentManagerImpl.this.popBackStackState(FragmentManagerImpl.this.mActivity.mHandler, str, -1, i);
            }
        }, false);
    }

    public boolean popBackStackImmediate() {
        checkStateLoss();
        executePendingTransactions();
        return popBackStackState(this.mActivity.mHandler, (String) null, -1, 0);
    }

    public boolean popBackStackImmediate(int i, int i2) {
        checkStateLoss();
        executePendingTransactions();
        if (i >= 0) {
            return popBackStackState(this.mActivity.mHandler, (String) null, i, i2);
        }
        throw new IllegalArgumentException("Bad id: " + i);
    }

    public boolean popBackStackImmediate(String str, int i) {
        checkStateLoss();
        executePendingTransactions();
        return popBackStackState(this.mActivity.mHandler, str, -1, i);
    }

    /* access modifiers changed from: package-private */
    public boolean popBackStackState(Handler handler, String str, int i, int i2) {
        if (this.mBackStack == null) {
            return false;
        }
        if (str == null && i < 0 && (i2 & 1) == 0) {
            int size = this.mBackStack.size() - 1;
            if (size < 0) {
                return false;
            }
            BackStackRecord remove = this.mBackStack.remove(size);
            SparseArray sparseArray = new SparseArray();
            SparseArray sparseArray2 = new SparseArray();
            remove.calculateBackFragments(sparseArray, sparseArray2);
            remove.popFromBackStack(true, (BackStackRecord.TransitionState) null, sparseArray, sparseArray2);
            reportBackStackChanged();
        } else {
            int i3 = -1;
            if (str != null || i >= 0) {
                int size2 = this.mBackStack.size() - 1;
                while (i3 >= 0) {
                    BackStackRecord backStackRecord = this.mBackStack.get(i3);
                    if ((str != null && str.equals(backStackRecord.getName())) || (i >= 0 && i == backStackRecord.mIndex)) {
                        break;
                    }
                    size2 = i3 - 1;
                }
                if (i3 < 0) {
                    return false;
                }
                if ((i2 & 1) != 0) {
                    i3--;
                    while (i3 >= 0) {
                        BackStackRecord backStackRecord2 = this.mBackStack.get(i3);
                        if ((str == null || !str.equals(backStackRecord2.getName())) && (i < 0 || i != backStackRecord2.mIndex)) {
                            break;
                        }
                        i3--;
                    }
                }
            }
            if (i3 == this.mBackStack.size() - 1) {
                return false;
            }
            ArrayList arrayList = new ArrayList();
            for (int size3 = this.mBackStack.size() - 1; size3 > i3; size3--) {
                arrayList.add(this.mBackStack.remove(size3));
            }
            int size4 = arrayList.size() - 1;
            SparseArray sparseArray3 = new SparseArray();
            SparseArray sparseArray4 = new SparseArray();
            for (int i4 = 0; i4 <= size4; i4++) {
                ((BackStackRecord) arrayList.get(i4)).calculateBackFragments(sparseArray3, sparseArray4);
            }
            BackStackRecord.TransitionState transitionState = null;
            int i5 = 0;
            while (i5 <= size4) {
                if (DEBUG) {
                    Log.v(TAG, "Popping back stack state: " + arrayList.get(i5));
                }
                transitionState = ((BackStackRecord) arrayList.get(i5)).popFromBackStack(i5 == size4, transitionState, sparseArray3, sparseArray4);
                i5++;
            }
            reportBackStackChanged();
        }
        return true;
    }

    public void putFragment(Bundle bundle, String str, Fragment fragment) {
        if (fragment.mIndex < 0) {
            throwException(new IllegalStateException("Fragment " + fragment + " is not currently in the FragmentManager"));
        }
        bundle.putInt(str, fragment.mIndex);
    }

    public void removeFragment(Fragment fragment, int i, int i2) {
        if (DEBUG) {
            Log.v(TAG, "remove: " + fragment + " nesting=" + fragment.mBackStackNesting);
        }
        boolean z = !fragment.isInBackStack();
        if (!fragment.mDetached || z) {
            if (this.mAdded != null) {
                this.mAdded.remove(fragment);
            }
            if (fragment.mHasMenu && fragment.mMenuVisible) {
                this.mNeedMenuInvalidate = true;
            }
            fragment.mAdded = false;
            fragment.mRemoving = true;
            moveToState(fragment, z ? 0 : 1, i, i2, false);
        }
    }

    public void removeOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener onBackStackChangedListener) {
        if (this.mBackStackChangeListeners != null) {
            this.mBackStackChangeListeners.remove(onBackStackChangedListener);
        }
    }

    /* access modifiers changed from: package-private */
    public void reportBackStackChanged() {
        if (this.mBackStackChangeListeners != null) {
            for (int i = 0; i < this.mBackStackChangeListeners.size(); i++) {
                this.mBackStackChangeListeners.get(i).onBackStackChanged();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void restoreAllState(Parcelable parcelable, ArrayList<Fragment> arrayList) {
        if (parcelable != null) {
            FragmentManagerState fragmentManagerState = (FragmentManagerState) parcelable;
            if (fragmentManagerState.mActive != null) {
                if (arrayList != null) {
                    for (int i = 0; i < arrayList.size(); i++) {
                        Fragment fragment = arrayList.get(i);
                        if (DEBUG) {
                            Log.v(TAG, "restoreAllState: re-attaching retained " + fragment);
                        }
                        FragmentState fragmentState = fragmentManagerState.mActive[fragment.mIndex];
                        fragmentState.mInstance = fragment;
                        fragment.mSavedViewState = null;
                        fragment.mBackStackNesting = 0;
                        fragment.mInLayout = false;
                        fragment.mAdded = false;
                        fragment.mTarget = null;
                        if (fragmentState.mSavedFragmentState != null) {
                            fragmentState.mSavedFragmentState.setClassLoader(this.mActivity.getClassLoader());
                            fragment.mSavedViewState = fragmentState.mSavedFragmentState.getSparseParcelableArray(VIEW_STATE_TAG);
                            fragment.mSavedFragmentState = fragmentState.mSavedFragmentState;
                        }
                    }
                }
                this.mActive = new ArrayList<>(fragmentManagerState.mActive.length);
                if (this.mAvailIndices != null) {
                    this.mAvailIndices.clear();
                }
                for (int i2 = 0; i2 < fragmentManagerState.mActive.length; i2++) {
                    FragmentState fragmentState2 = fragmentManagerState.mActive[i2];
                    if (fragmentState2 != null) {
                        Fragment instantiate = fragmentState2.instantiate(this.mActivity, this.mParent);
                        if (DEBUG) {
                            Log.v(TAG, "restoreAllState: active #" + i2 + ": " + instantiate);
                        }
                        this.mActive.add(instantiate);
                        fragmentState2.mInstance = null;
                    } else {
                        this.mActive.add((Object) null);
                        if (this.mAvailIndices == null) {
                            this.mAvailIndices = new ArrayList<>();
                        }
                        if (DEBUG) {
                            Log.v(TAG, "restoreAllState: avail #" + i2);
                        }
                        this.mAvailIndices.add(Integer.valueOf(i2));
                    }
                }
                if (arrayList != null) {
                    for (int i3 = 0; i3 < arrayList.size(); i3++) {
                        Fragment fragment2 = arrayList.get(i3);
                        if (fragment2.mTargetIndex >= 0) {
                            if (fragment2.mTargetIndex < this.mActive.size()) {
                                fragment2.mTarget = this.mActive.get(fragment2.mTargetIndex);
                            } else {
                                Log.w(TAG, "Re-attaching retained fragment " + fragment2 + " target no longer exists: " + fragment2.mTargetIndex);
                                fragment2.mTarget = null;
                            }
                        }
                    }
                }
                if (fragmentManagerState.mAdded != null) {
                    this.mAdded = new ArrayList<>(fragmentManagerState.mAdded.length);
                    for (int i4 = 0; i4 < fragmentManagerState.mAdded.length; i4++) {
                        Fragment fragment3 = this.mActive.get(fragmentManagerState.mAdded[i4]);
                        if (fragment3 == null) {
                            throwException(new IllegalStateException("No instantiated fragment for index #" + fragmentManagerState.mAdded[i4]));
                        }
                        fragment3.mAdded = true;
                        if (DEBUG) {
                            Log.v(TAG, "restoreAllState: added #" + i4 + ": " + fragment3);
                        }
                        if (this.mAdded.contains(fragment3)) {
                            throw new IllegalStateException("Already added!");
                        }
                        this.mAdded.add(fragment3);
                    }
                } else {
                    this.mAdded = null;
                }
                if (fragmentManagerState.mBackStack != null) {
                    this.mBackStack = new ArrayList<>(fragmentManagerState.mBackStack.length);
                    for (int i5 = 0; i5 < fragmentManagerState.mBackStack.length; i5++) {
                        BackStackRecord instantiate2 = fragmentManagerState.mBackStack[i5].instantiate(this);
                        if (DEBUG) {
                            Log.v(TAG, "restoreAllState: back stack #" + i5 + " (index " + instantiate2.mIndex + "): " + instantiate2);
                            instantiate2.dump("  ", new PrintWriter(new LogWriter(TAG)), false);
                        }
                        this.mBackStack.add(instantiate2);
                        if (instantiate2.mIndex >= 0) {
                            setBackStackIndex(instantiate2.mIndex, instantiate2);
                        }
                    }
                    return;
                }
                this.mBackStack = null;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public ArrayList<Fragment> retainNonConfig() {
        ArrayList<Fragment> arrayList = null;
        if (this.mActive != null) {
            for (int i = 0; i < this.mActive.size(); i++) {
                Fragment fragment = this.mActive.get(i);
                if (fragment != null && fragment.mRetainInstance) {
                    if (arrayList == null) {
                        arrayList = new ArrayList<>();
                    }
                    arrayList.add(fragment);
                    fragment.mRetaining = true;
                    fragment.mTargetIndex = fragment.mTarget != null ? fragment.mTarget.mIndex : -1;
                    if (DEBUG) {
                        Log.v(TAG, "retainNonConfig: keeping retained " + fragment);
                    }
                }
            }
        }
        return arrayList;
    }

    /* access modifiers changed from: package-private */
    public Parcelable saveAllState() {
        int size;
        int size2;
        execPendingActions();
        if (HONEYCOMB) {
            this.mStateSaved = true;
        }
        if (this.mActive == null || this.mActive.size() <= 0) {
            return null;
        }
        int size3 = this.mActive.size();
        FragmentState[] fragmentStateArr = new FragmentState[size3];
        boolean z = false;
        for (int i = 0; i < size3; i++) {
            Fragment fragment = this.mActive.get(i);
            if (fragment != null) {
                if (fragment.mIndex < 0) {
                    throwException(new IllegalStateException("Failure saving state: active " + fragment + " has cleared index: " + fragment.mIndex));
                }
                z = true;
                FragmentState fragmentState = new FragmentState(fragment);
                fragmentStateArr[i] = fragmentState;
                if (fragment.mState <= 0 || fragmentState.mSavedFragmentState != null) {
                    fragmentState.mSavedFragmentState = fragment.mSavedFragmentState;
                } else {
                    fragmentState.mSavedFragmentState = saveFragmentBasicState(fragment);
                    if (fragment.mTarget != null) {
                        if (fragment.mTarget.mIndex < 0) {
                            throwException(new IllegalStateException("Failure saving state: " + fragment + " has target not in fragment manager: " + fragment.mTarget));
                        }
                        if (fragmentState.mSavedFragmentState == null) {
                            fragmentState.mSavedFragmentState = new Bundle();
                        }
                        putFragment(fragmentState.mSavedFragmentState, TARGET_STATE_TAG, fragment.mTarget);
                        if (fragment.mTargetRequestCode != 0) {
                            fragmentState.mSavedFragmentState.putInt(TARGET_REQUEST_CODE_STATE_TAG, fragment.mTargetRequestCode);
                        }
                    }
                }
                if (DEBUG) {
                    Log.v(TAG, "Saved state of " + fragment + ": " + fragmentState.mSavedFragmentState);
                }
            }
        }
        if (z) {
            int[] iArr = null;
            BackStackState[] backStackStateArr = null;
            if (this.mAdded != null && (size2 = this.mAdded.size()) > 0) {
                iArr = new int[size2];
                for (int i2 = 0; i2 < size2; i2++) {
                    iArr[i2] = this.mAdded.get(i2).mIndex;
                    if (iArr[i2] < 0) {
                        throwException(new IllegalStateException("Failure saving state: active " + this.mAdded.get(i2) + " has cleared index: " + iArr[i2]));
                    }
                    if (DEBUG) {
                        Log.v(TAG, "saveAllState: adding fragment #" + i2 + ": " + this.mAdded.get(i2));
                    }
                }
            }
            if (this.mBackStack != null && (size = this.mBackStack.size()) > 0) {
                backStackStateArr = new BackStackState[size];
                for (int i3 = 0; i3 < size; i3++) {
                    backStackStateArr[i3] = new BackStackState(this, this.mBackStack.get(i3));
                    if (DEBUG) {
                        Log.v(TAG, "saveAllState: adding back stack #" + i3 + ": " + this.mBackStack.get(i3));
                    }
                }
            }
            FragmentManagerState fragmentManagerState = new FragmentManagerState();
            fragmentManagerState.mActive = fragmentStateArr;
            fragmentManagerState.mAdded = iArr;
            fragmentManagerState.mBackStack = backStackStateArr;
            return fragmentManagerState;
        } else if (!DEBUG) {
            return null;
        } else {
            Log.v(TAG, "saveAllState: no fragments!");
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public Bundle saveFragmentBasicState(Fragment fragment) {
        Bundle bundle = null;
        if (this.mStateBundle == null) {
            this.mStateBundle = new Bundle();
        }
        fragment.performSaveInstanceState(this.mStateBundle);
        if (!this.mStateBundle.isEmpty()) {
            bundle = this.mStateBundle;
            this.mStateBundle = null;
        }
        if (fragment.mView != null) {
            saveFragmentViewState(fragment);
        }
        if (fragment.mSavedViewState != null) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putSparseParcelableArray(VIEW_STATE_TAG, fragment.mSavedViewState);
        }
        if (!fragment.mUserVisibleHint) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putBoolean(USER_VISIBLE_HINT_TAG, fragment.mUserVisibleHint);
        }
        return bundle;
    }

    public Fragment.SavedState saveFragmentInstanceState(Fragment fragment) {
        Bundle saveFragmentBasicState;
        if (fragment.mIndex < 0) {
            throwException(new IllegalStateException("Fragment " + fragment + " is not currently in the FragmentManager"));
        }
        if (fragment.mState <= 0 || (saveFragmentBasicState = saveFragmentBasicState(fragment)) == null) {
            return null;
        }
        return new Fragment.SavedState(saveFragmentBasicState);
    }

    /* access modifiers changed from: package-private */
    public void saveFragmentViewState(Fragment fragment) {
        if (fragment.mInnerView != null) {
            if (this.mStateArray == null) {
                this.mStateArray = new SparseArray<>();
            } else {
                this.mStateArray.clear();
            }
            fragment.mInnerView.saveHierarchyState(this.mStateArray);
            if (this.mStateArray.size() > 0) {
                fragment.mSavedViewState = this.mStateArray;
                this.mStateArray = null;
            }
        }
    }

    public void setBackStackIndex(int i, BackStackRecord backStackRecord) {
        synchronized (this) {
            if (this.mBackStackIndices == null) {
                this.mBackStackIndices = new ArrayList<>();
            }
            int size = this.mBackStackIndices.size();
            if (i < size) {
                if (DEBUG) {
                    Log.v(TAG, "Setting back stack index " + i + " to " + backStackRecord);
                }
                this.mBackStackIndices.set(i, backStackRecord);
            } else {
                while (size < i) {
                    this.mBackStackIndices.add((Object) null);
                    if (this.mAvailBackStackIndices == null) {
                        this.mAvailBackStackIndices = new ArrayList<>();
                    }
                    if (DEBUG) {
                        Log.v(TAG, "Adding available back stack index " + size);
                    }
                    this.mAvailBackStackIndices.add(Integer.valueOf(size));
                    size++;
                }
                if (DEBUG) {
                    Log.v(TAG, "Adding back stack index " + i + " with " + backStackRecord);
                }
                this.mBackStackIndices.add(backStackRecord);
            }
        }
    }

    public void showFragment(Fragment fragment, int i, int i2) {
        if (DEBUG) {
            Log.v(TAG, "show: " + fragment);
        }
        if (fragment.mHidden) {
            fragment.mHidden = false;
            if (fragment.mView != null) {
                Animation loadAnimation = loadAnimation(fragment, i, true, i2);
                if (loadAnimation != null) {
                    fragment.mView.startAnimation(loadAnimation);
                }
                fragment.mView.setVisibility(0);
            }
            if (fragment.mAdded && fragment.mHasMenu && fragment.mMenuVisible) {
                this.mNeedMenuInvalidate = true;
            }
            fragment.onHiddenChanged(false);
        }
    }

    /* access modifiers changed from: package-private */
    public void startPendingDeferredFragments() {
        if (this.mActive != null) {
            for (int i = 0; i < this.mActive.size(); i++) {
                Fragment fragment = this.mActive.get(i);
                if (fragment != null) {
                    performPendingDeferredStart(fragment);
                }
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("FragmentManager{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" in ");
        if (this.mParent != null) {
            DebugUtils.buildShortClassTag(this.mParent, sb);
        } else {
            DebugUtils.buildShortClassTag(this.mActivity, sb);
        }
        sb.append("}}");
        return sb.toString();
    }
}
