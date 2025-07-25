package com.google.android.gms.games.multiplayer.turnbased;

import android.os.Bundle;
import com.google.android.gms.internal.eg;
import java.util.ArrayList;

public final class TurnBasedMatchConfig {
    private final String[] wG;
    private final Bundle wH;
    private final int wT;
    private final int wo;

    public static final class Builder {
        Bundle wH;
        ArrayList<String> wK;
        int wT;
        int wo;

        private Builder() {
            this.wo = -1;
            this.wK = new ArrayList<>();
            this.wH = null;
            this.wT = 2;
        }

        public Builder addInvitedPlayer(String str) {
            eg.f(str);
            this.wK.add(str);
            return this;
        }

        public Builder addInvitedPlayers(ArrayList<String> arrayList) {
            eg.f(arrayList);
            this.wK.addAll(arrayList);
            return this;
        }

        public TurnBasedMatchConfig build() {
            return new TurnBasedMatchConfig(this);
        }

        public Builder setAutoMatchCriteria(Bundle bundle) {
            this.wH = bundle;
            return this;
        }

        public Builder setMinPlayers(int i) {
            this.wT = i;
            return this;
        }

        public Builder setVariant(int i) {
            eg.b(i == -1 || i > 0, (Object) "Variant must be a positive integer or TurnBasedMatch.MATCH_VARIANT_ANY");
            this.wo = i;
            return this;
        }
    }

    private TurnBasedMatchConfig(Builder builder) {
        this.wo = builder.wo;
        this.wT = builder.wT;
        this.wH = builder.wH;
        this.wG = (String[]) builder.wK.toArray(new String[builder.wK.size()]);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Bundle createAutoMatchCriteria(int i, int i2, long j) {
        Bundle bundle = new Bundle();
        bundle.putInt("min_automatch_players", i);
        bundle.putInt("max_automatch_players", i2);
        bundle.putLong("exclusive_bit_mask", j);
        return bundle;
    }

    public Bundle getAutoMatchCriteria() {
        return this.wH;
    }

    public String[] getInvitedPlayerIds() {
        return this.wG;
    }

    public int getMinPlayers() {
        return this.wT;
    }

    public int getVariant() {
        return this.wo;
    }
}
