package com.google.android.gms.games.multiplayer.turnbased;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.multiplayer.InvitationBuffer;
import com.google.android.gms.internal.gf;

public final class LoadMatchesResponse {
    private final InvitationBuffer wO;
    private final TurnBasedMatchBuffer wP;
    private final TurnBasedMatchBuffer wQ;
    private final TurnBasedMatchBuffer wR;

    public LoadMatchesResponse(Bundle bundle) {
        DataHolder a = a(bundle, 0);
        if (a != null) {
            this.wO = new InvitationBuffer(a);
        } else {
            this.wO = null;
        }
        DataHolder a2 = a(bundle, 1);
        if (a2 != null) {
            this.wP = new TurnBasedMatchBuffer(a2);
        } else {
            this.wP = null;
        }
        DataHolder a3 = a(bundle, 2);
        if (a3 != null) {
            this.wQ = new TurnBasedMatchBuffer(a3);
        } else {
            this.wQ = null;
        }
        DataHolder a4 = a(bundle, 3);
        if (a4 != null) {
            this.wR = new TurnBasedMatchBuffer(a4);
        } else {
            this.wR = null;
        }
    }

    private static DataHolder a(Bundle bundle, int i) {
        String aG = gf.aG(i);
        if (!bundle.containsKey(aG)) {
            return null;
        }
        return (DataHolder) bundle.getParcelable(aG);
    }

    public void close() {
        if (this.wO != null) {
            this.wO.close();
        }
        if (this.wP != null) {
            this.wP.close();
        }
        if (this.wQ != null) {
            this.wQ.close();
        }
        if (this.wR != null) {
            this.wR.close();
        }
    }

    public TurnBasedMatchBuffer getCompletedMatches() {
        return this.wR;
    }

    public InvitationBuffer getInvitations() {
        return this.wO;
    }

    public TurnBasedMatchBuffer getMyTurnMatches() {
        return this.wP;
    }

    public TurnBasedMatchBuffer getTheirTurnMatches() {
        return this.wQ;
    }
}
