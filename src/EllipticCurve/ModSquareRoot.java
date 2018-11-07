package EllipticCurve;
/*Here i am implementing  Shanks Tonelli algorithm to find mod square root of a given number.
https://rosettacode.org/wiki/Tonelli-Shanks_algorithm#int
*/

import java.math.BigInteger;


public class ModSquareRoot {
    /*This is a pseudo code for Shanks-Tonelli algorithm.
  Input : p an odd prime, n is a integer.
    i) Check that n is indeed a square  : (n | p) must be ≡ 1
   ii) Find n^((p-1)/2) (mod p). It must be '1' or 'p-1'. If it is p-1 then modular square root does not exist.
  iii) Express 'p-1' as s*2^e , where s and e are positive integers.
   iv) Find value of 'q' such that q^((p-1)/2) (mod p) = p-1.
    v) Initialize 'x', 'b','g','r' as
         x = n^((s+1)/2) (first guess of square root)
         b = n^s
         g = q^s
         r = e
   vi) Loop
       1)If b=1 then output x, p-r.
       2) Else find, by repeated squaring least value of m such that 0<= m <= r-1 and b^(2^(m))= 1(mod p).
          Update x= x* g^(2^(r-m-1)), b= b*g^(2^(r-m)), g= g^(2^(r-m)), r=m.

* */

    public static BigInteger solution(BigInteger n, BigInteger p) {
        BigInteger a = p.subtract(BigInteger.ONE);
        BigInteger exponent = a.divide(BigInteger.valueOf(2));

        if (!n.modPow(exponent, p).equals(BigInteger.ONE)) {
            return BigInteger.valueOf(-1);
        }
        BigInteger s = a;
        BigInteger e = BigInteger.ZERO;
        while (s.and(BigInteger.ONE).equals(BigInteger.ZERO)) {
            e = e.add(BigInteger.ONE);
            s = s.shiftRight(1);
        }
        if (e.equals(BigInteger.ONE)) {
            BigInteger result = n.modPow(p.add(BigInteger.ONE).divide(BigInteger.valueOf(4)), p);
            return result;
        }
        BigInteger q = BigInteger.valueOf(2);
        while (!q.modPow(exponent, p).equals(p.subtract(BigInteger.ONE))) q = q.add(BigInteger.ONE);

        BigInteger x = n.modPow(s.add(BigInteger.ONE).divide(BigInteger.valueOf(2)), p); //x//
        BigInteger b = n.modPow(s, p);//b//
        BigInteger g = q.modPow(s, p);//g//
        BigInteger r = e;//r//
        while (true) {
            if (b.equals(BigInteger.ONE)) return x;
            BigInteger m = BigInteger.ZERO;
            BigInteger c = b;
            while (!c.equals(BigInteger.ONE) && m.compareTo(r.subtract(BigInteger.ONE)) < 0) {
                c = c.multiply(c).mod(p);
                m = m.add(BigInteger.ONE);
            }
            BigInteger d = g;//q^s=g//
            BigInteger f = r.subtract(m).subtract(BigInteger.ONE);
            while (f.compareTo(BigInteger.ZERO) > 0) {
                d = d.multiply(d).mod(p);
                f = f.subtract(BigInteger.ONE);
            }
            x = x.multiply(d).mod(p);
            g = d.multiply(d).mod(p);
            b = b.multiply(g).mod(p);
            r = m;
        }


    }


}
