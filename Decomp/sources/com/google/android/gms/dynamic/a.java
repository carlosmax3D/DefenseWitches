package com.google.android.gms.dynamic;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.dynamic.LifecycleDelegate;
import java.util.Iterator;
import java.util.LinkedList;

public abstract class a<T extends LifecycleDelegate> {
    /* access modifiers changed from: private */
    public T ss;
    /* access modifiers changed from: private */
    public Bundle st;
    /* access modifiers changed from: private */
    public LinkedList<C0007a> su;
    private final d<T> sv = new d<T>() {
        public void a(T t) {
            LifecycleDelegate unused = a.this.ss = t;
            Iterator it = a.this.su.iterator();
            while (it.hasNext()) {
                ((C0007a) it.next()).b(a.this.ss);
            }
            a.this.su.clear();
            Bundle unused2 = a.this.st = null;
        }
    };

    /* renamed from: com.google.android.gms.dynamic.a$a  reason: collision with other inner class name */
    private interface C0007a {
        void b(LifecycleDelegate lifecycleDelegate);

        int getState();
    }

    private void a(Bundle bundle, C0007a aVar) {
        if (this.ss != null) {
            aVar.b(this.ss);
            return;
        }
        if (this.su == null) {
            this.su = new LinkedList<>();
        }
        this.su.add(aVar);
        if (bundle != null) {
            if (this.st == null) {
                this.st = (Bundle) bundle.clone();
            } else {
                this.st.putAll(bundle);
            }
        }
        a(this.sv);
    }

    private void ay(int i) {
        while (!this.su.isEmpty() && this.su.getLast().getState() >= i) {
            this.su.removeLast();
        }
    }

    public void a(FrameLayout frameLayout) {
        final Context context = frameLayout.getContext();
        final int isGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        String b = GooglePlayServicesUtil.b(context, isGooglePlayServicesAvailable, -1);
        String b2 = GooglePlayServicesUtil.b(context, isGooglePlayServicesAvailable);
        LinearLayout linearLayout = new LinearLayout(frameLayout.getContext());
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
        frameLayout.addView(linearLayout);
        TextView textView = new TextView(frameLayout.getContext());
        textView.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
        textView.setText(b);
        linearLayout.addView(textView);
        if (b2 != null) {
            Button button = new Button(context);
            button.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
            button.setText(b2);
            linearLayout.addView(button);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    context.startActivity(GooglePlayServicesUtil.a(context, isGooglePlayServicesAvailable, -1));
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public abstract void a(d<T> dVar);

    public T cZ() {
        return this.ss;
    }

    public void onCreate(final Bundle bundle) {
        a(bundle, (C0007a) new C0007a() {
            public void b(LifecycleDelegate lifecycleDelegate) {
                a.this.ss.onCreate(bundle);
            }

            public int getState() {
                return 1;
            }
        });
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        final FrameLayout frameLayout = new FrameLayout(layoutInflater.getContext());
        final LayoutInflater layoutInflater2 = layoutInflater;
        final ViewGroup viewGroup2 = viewGroup;
        final Bundle bundle2 = bundle;
        a(bundle, (C0007a) new C0007a() {
            public void b(LifecycleDelegate lifecycleDelegate) {
                frameLayout.removeAllViews();
                frameLayout.addView(a.this.ss.onCreateView(layoutInflater2, viewGroup2, bundle2));
            }

            public int getState() {
                return 2;
            }
        });
        if (this.ss == null) {
            a(frameLayout);
        }
        return frameLayout;
    }

    public void onDestroy() {
        if (this.ss != null) {
            this.ss.onDestroy();
        } else {
            ay(1);
        }
    }

    public void onDestroyView() {
        if (this.ss != null) {
            this.ss.onDestroyView();
        } else {
            ay(2);
        }
    }

    public void onInflate(final Activity activity, final Bundle bundle, final Bundle bundle2) {
        a(bundle2, (C0007a) new C0007a() {
            public void b(LifecycleDelegate lifecycleDelegate) {
                a.this.ss.onInflate(activity, bundle, bundle2);
            }

            public int getState() {
                return 0;
            }
        });
    }

    public void onLowMemory() {
        if (this.ss != null) {
            this.ss.onLowMemory();
        }
    }

    public void onPause() {
        if (this.ss != null) {
            this.ss.onPause();
        } else {
            ay(3);
        }
    }

    public void onResume() {
        a((Bundle) null, (C0007a) new C0007a() {
            public void b(LifecycleDelegate lifecycleDelegate) {
                a.this.ss.onResume();
            }

            public int getState() {
                return 3;
            }
        });
    }

    public void onSaveInstanceState(Bundle bundle) {
        if (this.ss != null) {
            this.ss.onSaveInstanceState(bundle);
        } else if (this.st != null) {
            bundle.putAll(this.st);
        }
    }
}
