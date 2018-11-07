package EllipticCurve;

import java.math.BigInteger;
import java.lang.Math;

/*Here we are taking SPEC256k1 curve */
public class Point {
    public BigInteger x, y;
    static BigInteger a = new BigInteger("0");
    static BigInteger b = new BigInteger("7");
    static BigInteger p = new BigInteger("115792089237316195423570985008687907853269984665640564039457584007908834671663");
    static BigInteger order = new BigInteger("115792089237316195423570985008687907852837564279074904382605163141518161494337");

    public Point(BigInteger x, BigInteger y) {
        this.x = x;
        this.y = y;

    }

    public Point() {

    }

    public static BigInteger getOrder() {
        return order;
    }

    /*check whether the given curve is valid by calculating 4*a^3+27*b^2 and it should not be equal to zero.
    Indicates that there are no repeated roots*/
    public static boolean checkValidCurve() {
        BigInteger s = (a.pow(3).multiply(new BigInteger("4"))).add(b.pow(2).multiply(new BigInteger("27")));
        if (s.equals(new BigInteger("0"))) {
            return false;
        }
        return true;
    }

    /*Here our curve is in the form  y^2=x^3+ax+b. Given X-coordinate of a point then find Y-coordinate.*/
    public static Point findY(BigInteger X) {

        BigInteger y1 = X.pow(3).add(a.multiply(X)).add(b);
        BigInteger Y = ModSquareRoot.solution(y1, p);
        if (Y.equals(BigInteger.valueOf(-1))) {
            System.out.println("Solution does not exist");
            return new Point(BigInteger.ZERO, BigInteger.ZERO);
        } else {
            return new Point(X, p.subtract(Y));
        }

    }


    /*This method adds two points. Where slope m = (y2-y1)/(x2-x1); new point r = (x3,y3),
    x3 = m^2-(x1+x2), y3 = -m(x3-x1)-y1 */
    public Point pointAddition(Point q) {
        if (this.x.equals(q.x) && this.y.equals(q.y)) {
            if (this.y.equals(new BigInteger("0"))) {
                return new Point(new BigInteger("0"), new BigInteger("0"));

            }
            return pointDoubling();
        }
        if (this.x.equals(q.x)) {
            return new Point(new BigInteger("0"), new BigInteger("0"));
        }
        if (q.x.equals(BigInteger.ZERO) && q.y.equals(BigInteger.ZERO)) {
            return this;
        }
        if (this.x.equals(BigInteger.ZERO) && this.y.equals(BigInteger.ZERO)) {
            return q;
        }

        BigInteger m = (((q.y).subtract(this.y)).multiply(((q.x).subtract(this.x)).modInverse(p))).mod(p);
        BigInteger x3 = ((m.pow(2)).subtract((this.x).add(q.x))).mod(p);
        BigInteger y3 = (((m.multiply(x3.subtract(this.x))).multiply(new BigInteger("-1"))).subtract(this.y)).mod(p);
        Point r = new Point(x3, y3);
        return r;

    }

    /*Mirror image of a point with respect to x-axis. For ex: p=(1,2), mirror image of p is (1,-2)  */
    public Point inverse() {
        BigInteger y2 = ((this.y).multiply(new BigInteger("-1")));
        return new Point(this.x, y2);
    }

    /* For pointDoubling slope m = (3*x1^2 + a)/2y1, new point r=(x3,y3),
    x3 = m^2-(x1+x2), y3 = -m(x3-x1)-y1  */
    public Point pointDoubling() {
        BigInteger numerator = (this.x.pow(2).multiply(new BigInteger("3"))).add(a);
        BigInteger denominator = this.y.multiply(new BigInteger("2"));
        BigInteger m = (numerator.multiply(denominator.modInverse(p))).mod(p);
        BigInteger x3 = ((m.pow(2)).subtract((this.x).add(this.x))).mod(p);
        BigInteger y3 = (((m.multiply(x3.subtract(this.x))).multiply(new BigInteger("-1"))).subtract(this.y)).mod(p);
        Point r = new Point(x3, y3);
        return r;


    }

    /*PointMultiplication is the scalar multiplication of the given point. Let say p = (2,3), then find out
    [2]p, [3]p etc.
    We are using double and add algorithm for point multiplication.
    For more details you can visit https://sefiks.com/2016/03/27/double-and-add-method/
    * */
    public Point pointMultiplication(BigInteger x) {
        Point r = this;

        String toBinary = x.toString(2);
        //System.out.println("Binary representation (" + toBinary + ")2\n");

        int q = 1, p = 1;
        int a = 0, b = 0;

        for (int i = 1; i < toBinary.length(); i++) {
            int currentBit = Integer.parseInt(toBinary.substring(i, i + 1));
            //System.out.print(q + "P+" + q + "P=");
            q = q + q;
            a = q;
            //System.out.print(a + "P\t");
            r = r.pointAddition(r);
            //System.out.println(r.x+" "+r.y);
            if (r.y.equals(new BigInteger("0"))) {
                if (2 * (i + 1) == x.intValue())
                    return new Point(new BigInteger("0"), new BigInteger("0"));
            }

            if (currentBit == 1) {

                // System.out.print(q + "P+" + p + "P=");
                q = q + p;
                b = q;
                //System.out.print(b + "P\t");
//                System.out.println(r.x+" "+r.y);
//                System.out.println(this.x+" "+this.y);
                r = this.pointAddition(r);
                //System.out.println(r.x+" "+r.y);
                if (r.y.equals(new BigInteger("0"))) {
                    if (2 * (i + 1) == x.intValue())
                        return new Point(new BigInteger("0"), new BigInteger("0"));
                }

            }
            b = 0;
            //System.out.println("");
        }


        return r;

    }

}
