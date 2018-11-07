package Test.EllipticCurveTest;
import EllipticCurve.ModSquareRoot;

import java.math.BigInteger;

public class ModSquareRootTest {
        public static void main(String[] args) {
        BigInteger n = BigInteger.valueOf(1755);
        BigInteger p = BigInteger.valueOf(97);
        BigInteger sol = ModSquareRoot.solution(n, p);
        if (sol.equals(BigInteger.valueOf(-1))) {
            System.out.println("Solution does't exist");
        } else {
            System.out.println(p.subtract(sol));
        }

    }
}
