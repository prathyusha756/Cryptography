package Test.EllipticCurveTest;

import EllipticCurve.Point;

import java.math.BigInteger;

public class PointTest {
    public static void main(String[] args) {

        Boolean valid = Point.checkValidCurve();
        System.out.println(valid);

        Point p = Point.findY(new BigInteger("2"));
        System.out.println("Point p: " + p.x + " " + p.y);
        Point q = Point.findY(new BigInteger("12"));
        System.out.println("Point q: " + q.x + " " + q.y);

        Point r = p.pointAddition(q);
        System.out.println("Addition of p and q : " + r.x + " " + r.y);

        Point r1 = r.inverse();
        System.out.println("Mirror image of r: " + r1.x + "  " + r1.y);

        Point r2 = q.pointAddition(q);
        System.out.println("Point doubling: " + r2.x + " " + r2.y);

        Point r3 = p.pointMultiplication(new BigInteger("5"));
        Point r4 = p.pointMultiplication(new BigInteger("3"));
        Point r5 = p.pointMultiplication(new BigInteger("2"));
        Point r6 = r4.pointAddition(r5);
        System.out.println("Point multiplication is: " + r3.x + " " + r3.y + " " + r6.x + " " + r6.y);

    }

}
