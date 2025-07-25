package com.tapjoy.mraid.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.tapjoy.mraid.controller.Abstract;
import com.tapjoy.mraid.listener.Player;
import com.tapjoy.mraid.util.MraidPlayer;
import com.tapjoy.mraid.util.Utils;
import com.tapjoy.mraid.view.MraidView;
import java.util.HashMap;
import java.util.Map;

public class ActionHandler extends Activity {
    private static final String TAG = "MRAID Action Handler";
    private HashMap<MraidView.Action, Object> actionData = new HashMap<>();
    private RelativeLayout layout;

    private void doAction(Bundle bundle) {
        String string = bundle.getString("action");
        if (string != null) {
            MraidView.Action valueOf = MraidView.Action.valueOf(string);
            switch (valueOf) {
                case PLAY_AUDIO:
                    initPlayer(bundle, valueOf).playAudio();
                    return;
                case PLAY_VIDEO:
                    initPlayer(bundle, valueOf).playVideo();
                    return;
                default:
                    return;
            }
        }
    }

    private void setPlayerListener(MraidPlayer mraidPlayer) {
        mraidPlayer.setListener(new Player() {
            public void onComplete() {
                ActionHandler.this.finish();
            }

            public void onError() {
                ActionHandler.this.finish();
            }

            public void onPrepared() {
            }
        });
    }

    /* access modifiers changed from: package-private */
    public MraidPlayer initPlayer(Bundle bundle, MraidView.Action action) {
        RelativeLayout.LayoutParams layoutParams;
        Abstract.PlayerProperties playerProperties = (Abstract.PlayerProperties) bundle.getParcelable(MraidView.PLAYER_PROPERTIES);
        Abstract.Dimensions dimensions = (Abstract.Dimensions) bundle.getParcelable(MraidView.DIMENSIONS);
        MraidPlayer mraidPlayer = new MraidPlayer(this);
        mraidPlayer.setPlayData(playerProperties, Utils.getData(MraidView.EXPAND_URL, bundle));
        if (playerProperties.inline || !playerProperties.startStyle.equals(Abstract.FULL_SCREEN)) {
            layoutParams = new RelativeLayout.LayoutParams(dimensions.width, dimensions.height);
            layoutParams.topMargin = dimensions.y;
            layoutParams.leftMargin = dimensions.x;
        } else {
            getWindow().setFlags(1024, 1024);
            getWindow().setFlags(ViewCompat.MEASURED_STATE_TOO_SMALL, ViewCompat.MEASURED_STATE_TOO_SMALL);
            layoutParams = new RelativeLayout.LayoutParams(-1, -1);
            layoutParams.addRule(13);
        }
        mraidPlayer.setLayoutParams(layoutParams);
        this.layout.addView(mraidPlayer);
        this.actionData.put(action, mraidPlayer);
        setPlayerListener(mraidPlayer);
        return mraidPlayer;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        Bundle extras = getIntent().getExtras();
        this.layout = new RelativeLayout(this);
        this.layout.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        this.layout.setBackgroundColor(-16777216);
        setContentView(this.layout);
        doAction(extras);
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        for (Map.Entry key : this.actionData.entrySet()) {
            switch ((MraidView.Action) key.getKey()) {
                case PLAY_AUDIO:
                case PLAY_VIDEO:
                    ((MraidPlayer) key.getValue()).releasePlayer();
                    break;
            }
        }
        super.onStop();
    }
}
