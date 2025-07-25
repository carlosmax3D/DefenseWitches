package plugin.google.iap.v3;

import com.ansca.corona.CoronaLua;
import com.ansca.corona.CoronaLuaEvent;
import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.facebook.internal.AnalyticsEvents;
import com.naef.jnlua.LuaState;
import com.tapjoy.TJAdUnitConstants;
import plugin.google.iap.v3.util.IabResult;
import plugin.google.iap.v3.util.Purchase;

public class StoreTransactionRuntimeTask implements CoronaRuntimeTask {
    private int fListener;
    private Purchase fPurchase;
    private IabResult fResult;

    public StoreTransactionRuntimeTask(Purchase purchase, IabResult iabResult, int i) {
        this.fPurchase = purchase;
        this.fListener = i;
        this.fResult = iabResult;
    }

    public void executeUsing(CoronaRuntime coronaRuntime) {
        String str;
        if (this.fListener != -1) {
            LuaState luaState = coronaRuntime.getLuaState();
            try {
                CoronaLua.newEvent(luaState, "storeTransaction");
                luaState.newTable();
                if (this.fResult.isFailure()) {
                    luaState.pushBoolean(true);
                    luaState.setField(-2, CoronaLuaEvent.ISERROR_KEY);
                    luaState.pushNumber((double) this.fResult.getResponse());
                    luaState.setField(-2, CoronaLuaEvent.ERRORTYPE_KEY);
                    luaState.pushString(this.fResult.getMessage());
                    luaState.setField(-2, "errorString");
                    str = "failed";
                } else {
                    luaState.pushString(this.fPurchase.getItemType());
                    luaState.setField(-2, "type");
                    luaState.pushString(this.fPurchase.getOrderId());
                    luaState.setField(-2, TJAdUnitConstants.String.IDENTIFIER);
                    luaState.pushString(this.fPurchase.getPackageName());
                    luaState.setField(-2, "packageName");
                    luaState.pushString(this.fPurchase.getSku());
                    luaState.setField(-2, "productIdentifier");
                    luaState.pushNumber((double) this.fPurchase.getPurchaseTime());
                    luaState.setField(-2, "date");
                    switch (this.fPurchase.getPurchaseState()) {
                        case Purchased:
                            str = "purchased";
                            break;
                        case Cancelled:
                            str = AnalyticsEvents.PARAMETER_SHARE_OUTCOME_CANCELLED;
                            break;
                        case Refunded:
                            str = "refunded";
                            break;
                        case Consumed:
                            str = "consumed";
                            break;
                        default:
                            str = "unknown";
                            break;
                    }
                    luaState.pushString(this.fPurchase.getToken());
                    luaState.setField(-2, TJAdUnitConstants.String.EVENT_TOKEN);
                    luaState.pushString(this.fPurchase.getOriginalJson());
                    luaState.setField(-2, "originalJson");
                    luaState.pushString(this.fPurchase.getOriginalJson());
                    luaState.setField(-2, "receipt");
                    luaState.pushString(this.fPurchase.getSignature());
                    luaState.setField(-2, "signature");
                }
                luaState.pushString(str);
                luaState.setField(-2, "state");
                luaState.setField(-2, "transaction");
                CoronaLua.dispatchEvent(luaState, this.fListener, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
