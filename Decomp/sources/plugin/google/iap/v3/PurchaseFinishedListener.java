package plugin.google.iap.v3;

import com.ansca.corona.CoronaRuntimeTaskDispatcher;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import plugin.google.iap.v3.util.IabHelper;
import plugin.google.iap.v3.util.IabResult;
import plugin.google.iap.v3.util.Purchase;

public class PurchaseFinishedListener implements IabHelper.OnIabPurchaseFinishedListener {
    private static CopyOnWriteArrayList<IabHelper.OnIabPurchaseFinishedListener> sListeners = new CopyOnWriteArrayList<>();
    private CoronaRuntimeTaskDispatcher fDispatcher;
    private int fListener;

    public PurchaseFinishedListener(CoronaRuntimeTaskDispatcher coronaRuntimeTaskDispatcher, int i) {
        this.fDispatcher = coronaRuntimeTaskDispatcher;
        this.fListener = i;
    }

    public static void addListener(IabHelper.OnIabPurchaseFinishedListener onIabPurchaseFinishedListener) {
        sListeners.add(onIabPurchaseFinishedListener);
    }

    public static void removeListener(IabHelper.OnIabPurchaseFinishedListener onIabPurchaseFinishedListener) {
        sListeners.remove(onIabPurchaseFinishedListener);
    }

    public void onIabPurchaseFinished(IabResult iabResult, Purchase purchase) {
        Iterator<IabHelper.OnIabPurchaseFinishedListener> it = sListeners.iterator();
        while (it.hasNext()) {
            it.next().onIabPurchaseFinished(iabResult, purchase);
        }
        this.fDispatcher.send(new StoreTransactionRuntimeTask(purchase, iabResult, this.fListener));
    }
}
