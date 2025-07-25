package jp.stargarage.g2metrics;

import android.content.DialogInterface;

public interface IPushDialogCallback {
    void onClickNegative(DialogInterface dialogInterface);

    void onClickPositive(DialogInterface dialogInterface);
}
