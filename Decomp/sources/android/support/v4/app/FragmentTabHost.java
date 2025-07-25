package android.support.v4.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import java.util.ArrayList;

public class FragmentTabHost extends TabHost implements TabHost.OnTabChangeListener {
    private boolean mAttached;
    private int mContainerId;
    private Context mContext;
    private FragmentManager mFragmentManager;
    private TabInfo mLastTab;
    private TabHost.OnTabChangeListener mOnTabChangeListener;
    private FrameLayout mRealTabContent;
    private final ArrayList<TabInfo> mTabs = new ArrayList<>();

    static class DummyTabFactory implements TabHost.TabContentFactory {
        private final Context mContext;

        public DummyTabFactory(Context context) {
            this.mContext = context;
        }

        public View createTabContent(String str) {
            View view = new View(this.mContext);
            view.setMinimumWidth(0);
            view.setMinimumHeight(0);
            return view;
        }
    }

    static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        String curTab;

        private SavedState(Parcel parcel) {
            super(parcel);
            this.curTab = parcel.readString();
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            return "FragmentTabHost.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " curTab=" + this.curTab + "}";
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeString(this.curTab);
        }
    }

    static final class TabInfo {
        /* access modifiers changed from: private */
        public final Bundle args;
        /* access modifiers changed from: private */
        public final Class<?> clss;
        /* access modifiers changed from: private */
        public Fragment fragment;
        /* access modifiers changed from: private */
        public final String tag;

        TabInfo(String str, Class<?> cls, Bundle bundle) {
            this.tag = str;
            this.clss = cls;
            this.args = bundle;
        }
    }

    public FragmentTabHost(Context context) {
        super(context, (AttributeSet) null);
        initFragmentTabHost(context, (AttributeSet) null);
    }

    public FragmentTabHost(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initFragmentTabHost(context, attributeSet);
    }

    private FragmentTransaction doTabChanged(String str, FragmentTransaction fragmentTransaction) {
        TabInfo tabInfo = null;
        for (int i = 0; i < this.mTabs.size(); i++) {
            TabInfo tabInfo2 = this.mTabs.get(i);
            if (tabInfo2.tag.equals(str)) {
                tabInfo = tabInfo2;
            }
        }
        if (tabInfo == null) {
            throw new IllegalStateException("No tab known for tag " + str);
        }
        if (this.mLastTab != tabInfo) {
            if (fragmentTransaction == null) {
                fragmentTransaction = this.mFragmentManager.beginTransaction();
            }
            if (!(this.mLastTab == null || this.mLastTab.fragment == null)) {
                fragmentTransaction.detach(this.mLastTab.fragment);
            }
            if (tabInfo != null) {
                if (tabInfo.fragment == null) {
                    Fragment unused = tabInfo.fragment = Fragment.instantiate(this.mContext, tabInfo.clss.getName(), tabInfo.args);
                    fragmentTransaction.add(this.mContainerId, tabInfo.fragment, tabInfo.tag);
                } else {
                    fragmentTransaction.attach(tabInfo.fragment);
                }
            }
            this.mLastTab = tabInfo;
        }
        return fragmentTransaction;
    }

    private void ensureContent() {
        if (this.mRealTabContent == null) {
            this.mRealTabContent = (FrameLayout) findViewById(this.mContainerId);
            if (this.mRealTabContent == null) {
                throw new IllegalStateException("No tab content FrameLayout found for id " + this.mContainerId);
            }
        }
    }

    private void ensureHierarchy(Context context) {
        if (findViewById(16908307) == null) {
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(1);
            addView(linearLayout, new FrameLayout.LayoutParams(-1, -1));
            TabWidget tabWidget = new TabWidget(context);
            tabWidget.setId(16908307);
            tabWidget.setOrientation(0);
            linearLayout.addView(tabWidget, new LinearLayout.LayoutParams(-1, -2, 0.0f));
            FrameLayout frameLayout = new FrameLayout(context);
            frameLayout.setId(16908305);
            linearLayout.addView(frameLayout, new LinearLayout.LayoutParams(0, 0, 0.0f));
            FrameLayout frameLayout2 = new FrameLayout(context);
            this.mRealTabContent = frameLayout2;
            this.mRealTabContent.setId(this.mContainerId);
            linearLayout.addView(frameLayout2, new LinearLayout.LayoutParams(-1, 0, 1.0f));
        }
    }

    private void initFragmentTabHost(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, new int[]{16842995}, 0, 0);
        this.mContainerId = obtainStyledAttributes.getResourceId(0, 0);
        obtainStyledAttributes.recycle();
        super.setOnTabChangedListener(this);
    }

    public void addTab(TabHost.TabSpec tabSpec, Class<?> cls, Bundle bundle) {
        tabSpec.setContent(new DummyTabFactory(this.mContext));
        String tag = tabSpec.getTag();
        TabInfo tabInfo = new TabInfo(tag, cls, bundle);
        if (this.mAttached) {
            Fragment unused = tabInfo.fragment = this.mFragmentManager.findFragmentByTag(tag);
            if (tabInfo.fragment != null && !tabInfo.fragment.isDetached()) {
                FragmentTransaction beginTransaction = this.mFragmentManager.beginTransaction();
                beginTransaction.detach(tabInfo.fragment);
                beginTransaction.commit();
            }
        }
        this.mTabs.add(tabInfo);
        addTab(tabSpec);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        String currentTabTag = getCurrentTabTag();
        FragmentTransaction fragmentTransaction = null;
        for (int i = 0; i < this.mTabs.size(); i++) {
            TabInfo tabInfo = this.mTabs.get(i);
            Fragment unused = tabInfo.fragment = this.mFragmentManager.findFragmentByTag(tabInfo.tag);
            if (tabInfo.fragment != null && !tabInfo.fragment.isDetached()) {
                if (tabInfo.tag.equals(currentTabTag)) {
                    this.mLastTab = tabInfo;
                } else {
                    if (fragmentTransaction == null) {
                        fragmentTransaction = this.mFragmentManager.beginTransaction();
                    }
                    fragmentTransaction.detach(tabInfo.fragment);
                }
            }
        }
        this.mAttached = true;
        FragmentTransaction doTabChanged = doTabChanged(currentTabTag, fragmentTransaction);
        if (doTabChanged != null) {
            doTabChanged.commit();
            this.mFragmentManager.executePendingTransactions();
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mAttached = false;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setCurrentTabByTag(savedState.curTab);
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.curTab = getCurrentTabTag();
        return savedState;
    }

    public void onTabChanged(String str) {
        FragmentTransaction doTabChanged;
        if (this.mAttached && (doTabChanged = doTabChanged(str, (FragmentTransaction) null)) != null) {
            doTabChanged.commit();
        }
        if (this.mOnTabChangeListener != null) {
            this.mOnTabChangeListener.onTabChanged(str);
        }
    }

    public void setOnTabChangedListener(TabHost.OnTabChangeListener onTabChangeListener) {
        this.mOnTabChangeListener = onTabChangeListener;
    }

    @Deprecated
    public void setup() {
        throw new IllegalStateException("Must call setup() that takes a Context and FragmentManager");
    }

    public void setup(Context context, FragmentManager fragmentManager) {
        ensureHierarchy(context);
        super.setup();
        this.mContext = context;
        this.mFragmentManager = fragmentManager;
        ensureContent();
    }

    public void setup(Context context, FragmentManager fragmentManager, int i) {
        ensureHierarchy(context);
        super.setup();
        this.mContext = context;
        this.mFragmentManager = fragmentManager;
        this.mContainerId = i;
        ensureContent();
        this.mRealTabContent.setId(i);
        if (getId() == -1) {
            setId(16908306);
        }
    }
}
