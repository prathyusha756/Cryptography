package EllipticCurve;

import java.math.BigInteger;
/* PointOrder class finds the order of subgroup*/

public class PointOrder {

    public static BigInteger findPointOrder(BigInteger N, Point P) {
        BigInteger result = findDivisors(N, P);

        return result;

    }

    /* Find divisors of N */
    public static BigInteger findDivisors(BigInteger N, Point P) {

        BigInteger result = BigInteger.ZERO;

        for (BigInteger i = BigInteger.ONE; i.compareTo(N) <= 0; i = i.add(BigInteger.ONE)) {

            if (N.mod(i).equals(BigInteger.ZERO)) {
                BigInteger order = findOrder(i, P);
                if (!order.equals(BigInteger.ZERO)) {
                    result = order;
                    break;
                }

            }
        }
        return result;
    }

    /* For every divisor n of N, calculate np. The smallest n such that np=0 is the order of subgroup */
    public static BigInteger findOrder(BigInteger i, Point P) {
        Point Q;
        if (i.equals(BigInteger.ONE)) {
            Q = P;
        } else {
            Q = P.pointMultiplication(i);
        }
        if (Q.x.equals(BigInteger.ZERO) && Q.y.equals(BigInteger.ZERO)) {
            return i;
        } else return BigInteger.ZERO;


    }
}

