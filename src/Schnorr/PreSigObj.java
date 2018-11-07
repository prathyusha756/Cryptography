package Schnorr;

import EllipticCurve.Point;

import java.math.BigInteger;

public class PreSigObj {
    Point R;
    BigInteger k, s;
    String message;

    public PreSigObj(Point R, BigInteger K) {
        this.R = R;
        this.k = K;
    }

    public PreSigObj(Point R, BigInteger S, String m) {
        this.R = R;
        this.s = S;
        this.message = m;

    }

}
