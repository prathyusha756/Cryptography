package Test.EllipticCurveTest;
import EllipticCurve.Point;
import java.math.BigInteger;
import EllipticCurve.PointOrder;

public class PointOrderTest {
        public static void main(String[] args){
        Point P = Point.findY(new BigInteger("2"));
        BigInteger N = BigInteger.valueOf(42);
        BigInteger r = PointOrder.findPointOrder(N,P);
        System.out.println("order of point "+r);
    }

}
