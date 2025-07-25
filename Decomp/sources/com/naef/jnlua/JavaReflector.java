package com.naef.jnlua;

public interface JavaReflector {

    public enum Metamethod {
        INDEX,
        NEWINDEX,
        LEN,
        EQ,
        LT,
        LE,
        UNM,
        ADD,
        SUB,
        MUL,
        DIV,
        MOD,
        POW,
        CONCAT,
        CALL,
        TOSTRING,
        JAVAFIELDS,
        JAVAMETHODS,
        JAVAPROPERTIES;

        public String getMetamethodName() {
            return "__" + toString().toLowerCase();
        }
    }

    JavaFunction getMetamethod(Metamethod metamethod);
}
