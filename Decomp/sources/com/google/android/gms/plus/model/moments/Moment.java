package com.google.android.gms.plus.model.moments;

import com.google.android.gms.common.data.Freezable;
import com.google.android.gms.internal.ib;
import com.google.android.gms.internal.id;
import java.util.HashSet;
import java.util.Set;

public interface Moment extends Freezable<Moment> {

    public static class Builder {
        private String AI;
        private final Set<Integer> Eq = new HashSet();
        private String Fe;
        private ib Fm;
        private ib Fn;
        private String uS;

        public Moment build() {
            return new id(this.Eq, this.uS, this.Fm, this.Fe, this.Fn, this.AI);
        }

        public Builder setId(String str) {
            this.uS = str;
            this.Eq.add(2);
            return this;
        }

        public Builder setResult(ItemScope itemScope) {
            this.Fm = (ib) itemScope;
            this.Eq.add(4);
            return this;
        }

        public Builder setStartDate(String str) {
            this.Fe = str;
            this.Eq.add(5);
            return this;
        }

        public Builder setTarget(ItemScope itemScope) {
            this.Fn = (ib) itemScope;
            this.Eq.add(6);
            return this;
        }

        public Builder setType(String str) {
            this.AI = str;
            this.Eq.add(7);
            return this;
        }
    }

    String getId();

    ItemScope getResult();

    String getStartDate();

    ItemScope getTarget();

    String getType();

    boolean hasId();

    boolean hasResult();

    boolean hasStartDate();

    boolean hasTarget();

    boolean hasType();
}
