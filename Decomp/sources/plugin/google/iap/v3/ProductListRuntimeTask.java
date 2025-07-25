package plugin.google.iap.v3;

import com.ansca.corona.CoronaLua;
import com.ansca.corona.CoronaLuaEvent;
import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.naef.jnlua.LuaState;
import java.util.HashSet;
import java.util.Iterator;
import plugin.google.iap.v3.util.IabResult;
import plugin.google.iap.v3.util.Inventory;
import plugin.google.iap.v3.util.SkuDetails;

public class ProductListRuntimeTask implements CoronaRuntimeTask {
    private Inventory fInventory;
    private int fListener;
    private HashSet<String> fManagedProducts;
    private IabResult fResult;
    private HashSet<String> fSubscriptionProducts;

    public ProductListRuntimeTask(Inventory inventory, HashSet<String> hashSet, HashSet<String> hashSet2, IabResult iabResult, int i) {
        this.fInventory = inventory;
        this.fListener = i;
        this.fResult = iabResult;
        this.fManagedProducts = hashSet;
        this.fSubscriptionProducts = hashSet2;
    }

    public void executeUsing(CoronaRuntime coronaRuntime) {
        int i;
        if (this.fListener != -1) {
            LuaState luaState = coronaRuntime.getLuaState();
            try {
                CoronaLua.newEvent(luaState, "productList");
                if (this.fResult.isFailure()) {
                    luaState.pushBoolean(true);
                    luaState.setField(-2, CoronaLuaEvent.ISERROR_KEY);
                    luaState.pushNumber((double) this.fResult.getResponse());
                    luaState.setField(-2, CoronaLuaEvent.ERRORTYPE_KEY);
                    luaState.pushString(this.fResult.getMessage());
                    luaState.setField(-2, "errorString");
                } else {
                    luaState.newTable();
                    int i2 = 1;
                    for (SkuDetails next : this.fInventory.getAllSkuDetails()) {
                        luaState.newTable();
                        next.pushToLua(luaState, -2);
                        luaState.rawSet(-2, i);
                        i2 = i + 1;
                        if (this.fManagedProducts != null) {
                            this.fManagedProducts.remove(next.getSku());
                        }
                        if (this.fSubscriptionProducts != null) {
                            this.fSubscriptionProducts.remove(next.getSku());
                        }
                    }
                    luaState.setField(-2, "products");
                    if (this.fManagedProducts != null) {
                        Iterator<String> it = this.fManagedProducts.iterator();
                        luaState.newTable();
                        i = 1;
                        while (it.hasNext()) {
                            luaState.pushString(it.next());
                            luaState.rawSet(-2, i);
                            i++;
                        }
                    }
                    if (this.fSubscriptionProducts != null) {
                        Iterator<String> it2 = this.fSubscriptionProducts.iterator();
                        while (it2.hasNext()) {
                            luaState.pushString(it2.next());
                            luaState.rawSet(-2, i);
                            i++;
                        }
                    }
                    luaState.setField(-2, "invalidProducts");
                }
                CoronaLua.dispatchEvent(luaState, this.fListener, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            CoronaLua.deleteRef(luaState, this.fListener);
        }
    }
}
