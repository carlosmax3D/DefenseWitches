package plugin.google.iap.v3;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.ansca.corona.CoronaActivity;
import com.ansca.corona.CoronaEnvironment;
import com.ansca.corona.CoronaLua;
import com.ansca.corona.CoronaRuntimeTaskDispatcher;
import com.ansca.corona.purchasing.StoreName;
import com.ansca.corona.purchasing.StoreServices;
import com.naef.jnlua.JavaFunction;
import com.naef.jnlua.LuaState;
import com.naef.jnlua.LuaType;
import com.naef.jnlua.NamedJavaFunction;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import jp.stargarage.g2metrics.BuildConfig;
import plugin.google.iap.v3.util.IabHelper;
import plugin.google.iap.v3.util.IabResult;
import plugin.google.iap.v3.util.Inventory;
import plugin.google.iap.v3.util.Purchase;

public class LuaLoader implements JavaFunction {
    /* access modifiers changed from: private */
    public static IabHelper sHelper;
    /* access modifiers changed from: private */
    public CoronaRuntimeTaskDispatcher fDispatcher;
    /* access modifiers changed from: private */
    public IabHelper fHelper;
    /* access modifiers changed from: private */
    public int fLibRef;
    /* access modifiers changed from: private */
    public int fListener;
    /* access modifiers changed from: private */
    public boolean fSetupSuccessful;

    private class ConsumePurchaseWrapper implements NamedJavaFunction {
        private ConsumePurchaseWrapper() {
        }

        public String getName() {
            return "consumePurchase";
        }

        public int invoke(LuaState luaState) {
            return LuaLoader.this.consumePurchase(luaState);
        }
    }

    private class FinishTransactionWrapper implements NamedJavaFunction {
        private FinishTransactionWrapper() {
        }

        public String getName() {
            return "finishTransaction";
        }

        public int invoke(LuaState luaState) {
            return 0;
        }
    }

    private class InitWrapper implements NamedJavaFunction {
        private InitWrapper() {
        }

        public String getName() {
            return "init";
        }

        public int invoke(LuaState luaState) {
            return LuaLoader.this.init(luaState);
        }
    }

    private class LoadProductsWrapper implements NamedJavaFunction {
        private LoadProductsWrapper() {
        }

        public String getName() {
            return "loadProducts";
        }

        public int invoke(LuaState luaState) {
            return LuaLoader.this.loadProducts(luaState);
        }
    }

    private class PurchaseSubscriptionWrapper implements NamedJavaFunction {
        private PurchaseSubscriptionWrapper() {
        }

        public String getName() {
            return "purchaseSubscription";
        }

        public int invoke(LuaState luaState) {
            return LuaLoader.this.purchaseSubscription(luaState);
        }
    }

    private class PurchaseWrapper implements NamedJavaFunction {
        private PurchaseWrapper() {
        }

        public String getName() {
            return "purchase";
        }

        public int invoke(LuaState luaState) {
            return LuaLoader.this.purchase(luaState);
        }
    }

    private class RestoreWrapper implements NamedJavaFunction {
        private RestoreWrapper() {
        }

        public String getName() {
            return "restore";
        }

        public int invoke(LuaState luaState) {
            return LuaLoader.this.restore(luaState);
        }
    }

    public LuaLoader() {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            throw new IllegalArgumentException("Activity cannot be null.");
        }
    }

    /* access modifiers changed from: private */
    public int consumePurchase(LuaState luaState) {
        if (!initSuccessful()) {
            Log.w("Corona", "Please call init before trying to consume products.");
        } else {
            final HashSet hashSet = new HashSet();
            if (luaState.type(1) == LuaType.TABLE) {
                int length = luaState.length(1);
                for (int i = 1; i <= length; i++) {
                    luaState.rawGet(1, i);
                    if (luaState.type(-1) == LuaType.STRING) {
                        hashSet.add(luaState.toString(-1));
                    } else {
                        hashSet.add(BuildConfig.FLAVOR);
                    }
                    luaState.pop(1);
                }
            } else {
                String str = BuildConfig.FLAVOR;
                if (luaState.type(1) == LuaType.STRING) {
                    str = luaState.toString(1);
                }
                hashSet.add(str);
            }
            this.fHelper.queryInventoryAsync(true, (Set<String>) null, (Set<String>) null, new IabHelper.QueryInventoryFinishedListener() {
                public void onQueryInventoryFinished(IabResult iabResult, Inventory inventory) {
                    if (iabResult.isFailure() || inventory == null) {
                        LuaLoader.this.fDispatcher.send(new StoreTransactionRuntimeTask((Purchase) null, iabResult, LuaLoader.this.fListener));
                        return;
                    }
                    ArrayList arrayList = new ArrayList(hashSet.size());
                    Iterator it = hashSet.iterator();
                    while (it.hasNext()) {
                        String str = (String) it.next();
                        if (inventory.hasPurchase(str)) {
                            arrayList.add(inventory.getPurchase(str));
                        }
                    }
                    LuaLoader.this.fHelper.consumeAsync((List<Purchase>) arrayList, (IabHelper.OnConsumeMultiFinishedListener) new IabHelper.OnConsumeMultiFinishedListener() {
                        public void onConsumeMultiFinished(List<Purchase> list, List<IabResult> list2) {
                            Iterator<Purchase> it = list.iterator();
                            Iterator<IabResult> it2 = list2.iterator();
                            while (it.hasNext() && it2.hasNext()) {
                                Purchase next = it.next();
                                next.setPurchaseState(Purchase.State.Consumed);
                                LuaLoader.this.fDispatcher.send(new StoreTransactionRuntimeTask(next, it2.next(), LuaLoader.this.fListener));
                            }
                        }
                    });
                }
            });
        }
        return 0;
    }

    /* access modifiers changed from: private */
    public int init(LuaState luaState) {
        int i = 1;
        String str = BuildConfig.FLAVOR;
        luaState.getGlobal("require");
        luaState.pushString("config");
        luaState.call(1, -1);
        luaState.getGlobal("application");
        if (luaState.type(-1) == LuaType.TABLE) {
            luaState.getField(-1, "license");
            if (luaState.type(-1) == LuaType.TABLE) {
                luaState.getField(-1, StoreName.GOOGLE);
                if (luaState.type(-1) == LuaType.TABLE) {
                    luaState.getField(-1, "key");
                    if (luaState.type(-1) == LuaType.STRING) {
                        str = luaState.toString(-1);
                    }
                    luaState.pop(1);
                }
                luaState.pop(1);
            }
            luaState.pop(1);
        }
        luaState.pop(1);
        if (luaState.type(1) == LuaType.STRING) {
            i = 1 + 1;
        }
        this.fListener = -1;
        if (CoronaLua.isListener(luaState, i, "storeTransaction")) {
            this.fListener = CoronaLua.newRef(luaState, i);
        }
        final LuaState luaState2 = luaState;
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        Context applicationContext = CoronaEnvironment.getApplicationContext();
        if (str.length() <= 0 || applicationContext == null) {
            if (str.length() < 1) {
                Log.w("Corona", "No license key was supplied to config.lua.");
            }
            if (applicationContext == null) {
                Log.w("Corona", "Context was null.");
            }
            atomicBoolean.set(true);
        } else {
            this.fHelper = new IabHelper(applicationContext, str);
            this.fHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
                public void onIabSetupFinished(IabResult iabResult) {
                    if (!luaState2.isOpen()) {
                        atomicBoolean.set(true);
                        return;
                    }
                    boolean unused = LuaLoader.this.fSetupSuccessful = iabResult.isSuccess();
                    if (LuaLoader.this.fSetupSuccessful) {
                        IabHelper unused2 = LuaLoader.sHelper = LuaLoader.this.fHelper;
                    }
                    luaState2.rawGet(LuaState.REGISTRYINDEX, LuaLoader.this.fLibRef);
                    luaState2.pushBoolean(iabResult.isSuccess());
                    luaState2.setField(-2, "isActive");
                    luaState2.pushBoolean(LuaLoader.this.fHelper.subscriptionsSupported());
                    luaState2.setField(-2, "canPurchaseSubscriptions");
                    luaState2.pop(1);
                    atomicBoolean.set(true);
                }
            });
        }
        do {
        } while (!atomicBoolean.get());
        return 0;
    }

    private boolean initSuccessful() {
        return this.fHelper != null && this.fSetupSuccessful;
    }

    /* access modifiers changed from: private */
    public int loadProducts(LuaState luaState) {
        final int i = -1;
        if (!initSuccessful()) {
            Log.w("Corona", "Please call init before trying to load products.");
        } else {
            int i2 = 2;
            int length = luaState.length(1);
            final HashSet hashSet = new HashSet(length);
            for (int i3 = 1; i3 <= length; i3++) {
                luaState.rawGet(1, i3);
                if (luaState.type(-1) == LuaType.STRING) {
                    hashSet.add(luaState.toString(-1));
                } else {
                    hashSet.add(BuildConfig.FLAVOR);
                }
                luaState.pop(1);
            }
            HashSet hashSet2 = null;
            if (!CoronaLua.isListener(luaState, 2, "productList")) {
                int length2 = luaState.length(2);
                hashSet2 = new HashSet(length2);
                for (int i4 = 1; i4 <= length2; i4++) {
                    luaState.rawGet(2, i4);
                    if (luaState.type(-1) == LuaType.STRING) {
                        hashSet2.add(luaState.toString(-1));
                    } else {
                        hashSet2.add(BuildConfig.FLAVOR);
                    }
                    luaState.pop(1);
                }
                i2 = 2 + 1;
            }
            final HashSet hashSet3 = hashSet2;
            if (CoronaLua.isListener(luaState, i2, "productList")) {
                i = CoronaLua.newRef(luaState, i2);
            }
            this.fHelper.queryInventoryAsync(true, hashSet, hashSet3, new IabHelper.QueryInventoryFinishedListener() {
                public void onQueryInventoryFinished(IabResult iabResult, Inventory inventory) {
                    LuaLoader.this.fDispatcher.send(new ProductListRuntimeTask(inventory, hashSet, hashSet3, iabResult, i));
                }
            });
        }
        return 0;
    }

    /* access modifiers changed from: private */
    public int purchase(LuaState luaState) {
        if (!initSuccessful()) {
            Log.w("Corona", "Please call init before trying to purchase products.");
        } else {
            CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
            if (coronaActivity == null) {
                Log.w("Corona", "The Corona Activity was null.");
            } else {
                int registerActivityResultHandler = coronaActivity.registerActivityResultHandler(new CoronaActivity.OnActivityResultHandler() {
                    public void onHandleActivityResult(CoronaActivity coronaActivity, int i, int i2, Intent intent) {
                        coronaActivity.unregisterActivityResultHandler(this);
                        if (LuaLoader.this.fHelper != null) {
                            LuaLoader.this.fHelper.handleActivityResult(i, i2, intent);
                        }
                    }
                });
                String str = BuildConfig.FLAVOR;
                if (luaState.type(1) == LuaType.STRING) {
                    str = luaState.toString(1);
                }
                luaState.pop(1);
                this.fHelper.launchPurchaseFlow(coronaActivity, str, registerActivityResultHandler, new PurchaseFinishedListener(this.fDispatcher, this.fListener));
            }
        }
        return 0;
    }

    /* access modifiers changed from: private */
    public int purchaseSubscription(LuaState luaState) {
        if (!initSuccessful()) {
            Log.w("Corona", "Please call init before trying to purchase products.");
        } else {
            CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
            if (coronaActivity == null) {
                Log.w("Corona", "The Corona Activity was null.");
            } else {
                int registerActivityResultHandler = coronaActivity.registerActivityResultHandler(new CoronaActivity.OnActivityResultHandler() {
                    public void onHandleActivityResult(CoronaActivity coronaActivity, int i, int i2, Intent intent) {
                        coronaActivity.unregisterActivityResultHandler(this);
                        if (LuaLoader.this.fHelper != null) {
                            LuaLoader.this.fHelper.handleActivityResult(i, i2, intent);
                        }
                    }
                });
                String str = BuildConfig.FLAVOR;
                if (luaState.type(1) == LuaType.STRING) {
                    str = luaState.toString(1);
                }
                luaState.pop(1);
                this.fHelper.launchSubscriptionPurchaseFlow(coronaActivity, str, registerActivityResultHandler, new PurchaseFinishedListener(this.fDispatcher, this.fListener));
            }
        }
        return 0;
    }

    public static void queryInventoryAsync(HashSet<String> hashSet, IabHelper.QueryInventoryFinishedListener queryInventoryFinishedListener) {
        if (sHelper != null) {
            sHelper.queryInventoryAsync(true, hashSet, (Set<String>) null, queryInventoryFinishedListener);
        }
    }

    /* access modifiers changed from: private */
    public int restore(LuaState luaState) {
        if (!initSuccessful()) {
            Log.w("Corona", "Please call init before trying to restore products.");
        } else {
            this.fHelper.queryInventoryAsync(false, (Set<String>) null, (Set<String>) null, new IabHelper.QueryInventoryFinishedListener() {
                public void onQueryInventoryFinished(IabResult iabResult, Inventory inventory) {
                    for (Purchase storeTransactionRuntimeTask : inventory.getAllPurchases()) {
                        LuaLoader.this.fDispatcher.send(new StoreTransactionRuntimeTask(storeTransactionRuntimeTask, iabResult, LuaLoader.this.fListener));
                    }
                }
            });
        }
        return 0;
    }

    public int invoke(LuaState luaState) {
        this.fDispatcher = new CoronaRuntimeTaskDispatcher(luaState);
        this.fSetupSuccessful = false;
        luaState.register(luaState.toString(1), new NamedJavaFunction[]{new InitWrapper(), new LoadProductsWrapper(), new PurchaseWrapper(), new ConsumePurchaseWrapper(), new PurchaseSubscriptionWrapper(), new FinishTransactionWrapper(), new RestoreWrapper()});
        luaState.pushValue(-1);
        this.fLibRef = luaState.ref(LuaState.REGISTRYINDEX);
        luaState.pushBoolean(true);
        luaState.setField(-2, "canLoadProducts");
        luaState.pushBoolean(true);
        luaState.setField(-2, "canMakePurchases");
        luaState.pushBoolean(false);
        luaState.setField(-2, "isActive");
        luaState.pushBoolean(true);
        luaState.setField(-2, "canPurchaseSubscriptions");
        luaState.pushString(StoreServices.getTargetedAppStoreName());
        luaState.setField(-2, "target");
        return 1;
    }
}
