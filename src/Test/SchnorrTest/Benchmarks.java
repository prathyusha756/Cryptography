package Test.SchnorrTest;

import Schnorr.PreSigObj;
import Schnorr.SchnorrSig;

import java.util.LinkedList;
import java.util.List;

public class Benchmarks {
    public static void main(String[] args) throws Exception {
        SchnorrSig obj = new SchnorrSig();
        obj.keyGeneration();

        List<PreSigObj> linkedList = new LinkedList<>();
        List<PreSigObj> linkedList1 = new LinkedList<>();
        for (int i = 0; i < 500; i++) {
            linkedList.add(obj.offLineCalculation());
        }
        String m = new String("hello world");
        long startTime = System.nanoTime();
        for (int i = 0; i < 500; i++) {
            linkedList1.add(obj.signature(linkedList.get(i), m));
        }
        long endTime = System.nanoTime();
        System.out.println("Total time to sign 500 messages " + (endTime - startTime));

        int check = 0;
        long startTime1 = System.nanoTime();
        for (int i = 0; i < 500; i++) {
            boolean output = obj.verification(linkedList1.get(i));
            if (!output) {
                check = 1;
                System.out.println("not valid signature" + i);
            }
        }
        long endTime1 = System.nanoTime();

        if (check == 0) {
            System.out.println("All signatures are valid");
        }

        long startTime2 = System.nanoTime();
        boolean batchVerificationOut = obj.batchVerification(linkedList1);
        long endTime2 = System.nanoTime();
        if (batchVerificationOut) {
            System.out.println("All signatures are valid using batch verification");
        } else System.out.println("not valid ");

        System.out.println("Total time to sign 500 messages " + (endTime - startTime));
        System.out.println("Total time to verify 500 signatures " + (endTime1 - startTime1));
        System.out.println("Total time to batch verify 500 signature " + (endTime2 - startTime2));
    }
}
