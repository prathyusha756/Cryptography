package Schnorr;
import EllipticCurve.Point;
import EllipticCurve.PointOrder;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/*Implements Elliptical curve based Schnorr Signature*/
public class SchnorrSig {
    Point P,Q;
    BigInteger a, pointOrder;
    /* step 1)Choose a random point p on elliptical curve
      *     2)Choose a random integer 'a' from range[1, r],
      *       where r is the order of point p.
      *     3) calculate Q=[a]p
      *     4) Output: public key Pk:(p,[a]p) , secret key Sk=(a, Pk) */

    public  void keyGeneration() {

        Point P = Point.findY(new BigInteger("79BE667EF9DCBBAC55A06295CE870B07029BFCDB2DCE28D959F2815B16F81798",16));
        Point Q =new Point(BigInteger.ZERO,BigInteger.ZERO);
        BigInteger curveOrder = Point.getOrder();

        //BigInteger r = PointOrder.findPointOrder(curveOrder,P);
        BigInteger r= curveOrder;
        System.out.println("order of point "+r);
        BigInteger b=BigInteger.ZERO;

        if((!P.y.equals(BigInteger.ZERO)) && (!P.y.equals(BigInteger.ZERO))) {

            Random random = new Random();

            do {
                b = new BigInteger(r.bitLength(), random);
            } while (b.compareTo(r) >0);

            System.out.println("random value a: " + b);

            Q = P.pointMultiplication(b);
        }
        this.P=P;
        this.Q=Q;
        this.a=b;
        this.pointOrder=r;

    }
   /* Calculating Point R :
    * 1)Choose a random integer 'k' from range[1, r],where r is the order of point p.
      2) Calculate Point R= [k]p */
    public PreSigObj offLineCalculation (){

        Random random = new Random();
        BigInteger k;
        do {
            k = new BigInteger(pointOrder.bitLength(), random);
        } while (k.compareTo(pointOrder) >0 );
        //System.out.println("random value k: " + k);

        Point R = P.pointMultiplication(k);
        return new PreSigObj(R, k);

    }
    /* set e = H(message || R).
       set s = k+ae (mod r).
       output the signature sigma=(R,s)
    * */

    public PreSigObj signature (PreSigObj F ,String message) throws Exception{
        String m=message;
        Point R= F.R;
        String rx = R.x.toString();
        String ry = R.y.toString();
        String concatenation = m.concat(rx).concat(ry);
        //System.out.println(concatenation);
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(concatenation.getBytes());
        BigInteger e = new BigInteger(1,hash).mod(pointOrder);
        //System.out.println("e value is sig:"+e);
        BigInteger s=(F.k.add(a.multiply(e))).mod(pointOrder);
        //System.out.println("s value :"+s);

        return new PreSigObj(R, s, m);

    }
    /* Compute e = H(message || R)
    * If R+[e]Q = [s]p then accept, otherwise reject the message*/

    public boolean verification(PreSigObj L) throws Exception{
        //calculate e=H(M||r)//
        String m = L.message;
        Point R = L.R;
        String rx = R.x.toString();
        String ry = R.y.toString();
        String concatenation = m.concat(rx).concat(ry);
        //System.out.println(concatenation);
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(concatenation.getBytes());
        BigInteger e = new BigInteger(1,hash).mod(pointOrder);
        //System.out.println("e value is ver:"+e);

        //Check R+[e]Q=[s]P //
        Point etimesQ=Q.pointMultiplication(e);

        Point leftSide=R.pointAddition(etimesQ);

        Point rightSide=P.pointMultiplication(L.s);

        if(leftSide.x.equals(rightSide.x) && leftSide.y.equals(leftSide.y)){
            return true;
        }
        else return false;

    }
/*In batch verification we have given list of signatures. In order to validate all signatures at once
* i)Get s value from each signature in the list and add them to get 'S'
* ii)Calculate  e = H(message || R) from each signature and sum to get 'E'
* iii)Get point R from each signature and add to get Rs
 *iv) Finally calculate, if Rs+[E]Q = [S]p then output true else output false.*/
    public boolean batchVerification(List<PreSigObj> sigList) throws Exception{
        String m;
        Point  R;
        PreSigObj sig;
        Point Rs = new Point(BigInteger.ZERO,BigInteger.ZERO);
        BigInteger S = BigInteger.ZERO;
        BigInteger E = BigInteger.ZERO;

        for(int i=0;i<sigList.size();i++){
            sig = sigList.get(i);
            m = sig.message;
            R = sig.R;
            S = S.add(sig.s).mod(pointOrder);
            Rs = Rs.pointAddition(R);
            String rx = R.x.toString();
            String ry = R.y.toString();
            String concatenation = m.concat(rx).concat(ry);
           // System.out.println(concatenation);
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(concatenation.getBytes());
            BigInteger e = new BigInteger(1,hash).mod(pointOrder);
            E = E.add(e).mod(pointOrder);
        }
        Point EtimesQ = Q.pointMultiplication(E);

        Point leftSide = Rs.pointAddition(EtimesQ);

        Point rightSide = P.pointMultiplication(S);

        if(leftSide.x.equals(rightSide.x) && leftSide.y.equals(leftSide.y)){
            return true;
        }
        else return false;

    }

}
