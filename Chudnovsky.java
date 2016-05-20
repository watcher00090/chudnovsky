import java.math.*;
import java.io.*;

public class Chudnovsky extends Object {

    public static BigDecimal pi_chudnovsky_bigdecimal(int N, MathContext mc, int iterations) {
        BigDecimal bminus1 = new BigDecimal("-1");
        BigDecimal b1 = new BigDecimal("1");
        BigDecimal b3 = new BigDecimal("3");
        BigDecimal b6 = new BigDecimal("6");
        BigDecimal b12= new BigDecimal("12");
        BigDecimal b53360 = new BigDecimal("53360");
        BigDecimal b640320 = new BigDecimal("640320");
        BigDecimal b13591409 = new BigDecimal("13591409");
        BigDecimal b545140134 = new BigDecimal("545140134");
        BigDecimal bsum = new BigDecimal("0");
        BigDecimal bsqrt640320 = sqrt(b640320, mc, iterations);

        for (int k=0; k<N; k++) {
            BigDecimal bk = new BigDecimal(""+k);

            BigDecimal bnum = (bminus1.pow(k))
                  .multiply(factorial(b6.multiply(bk)))  
                  .multiply(b13591409.add(b545140134.multiply(bk)));

            BigDecimal bdenom = (factorial(bk).pow(3))
                    .multiply((factorial(b3.multiply(bk))))
                    .multiply(b640320.pow(3*k));

            BigDecimal bratio = bnum.divide(bdenom, mc);
            bsum = bsum.add(bratio);
System.out.println((k+1) + " terms summed");
        }
        return b53360.multiply(bsqrt640320).divide(bsum, mc);
    }

    public static BigDecimal pi_chudnovsky_bigrational(int N, MathContext mc, int iterations) {
        BigInteger bminus1 = new BigInteger("-1");
        BigInteger b1 = new BigInteger("1");
        BigInteger b3 = new BigInteger("3");
        BigInteger b6 = new BigInteger("6");
        BigInteger b12= new BigInteger("12");
        BigInteger b13591409 = new BigInteger("13591409");
        BigInteger b545140134 = new BigInteger("545140134");
        BigInteger bi640320 = new BigInteger("640320");
        BigRational brsum = new BigRational(new BigInteger("0"), new BigInteger("1"));
        BigDecimal bd640320 = new BigDecimal("640320");
        BigDecimal bsqrt640320 = sqrt(bd640320, mc, iterations);
        BigDecimal b53360 = new BigDecimal("53360");

        for (int k=0; k<N; k++) {
            BigInteger bk = new BigInteger(""+k);

            BigInteger bnum = (bminus1.pow(k))
                  .multiply(factorial(b6.multiply(bk)))  
                  .multiply(b13591409.add(b545140134.multiply(bk)));

            BigInteger bdenom = (factorial(bk).pow(3))
                    .multiply((factorial(b3.multiply(bk))))
                    .multiply(bi640320.pow(3*k));

            BigRational bratio = new BigRational(bnum, bdenom);
            brsum = brsum.add(bratio);
System.out.println((k+1) + " terms summed");
        }
        return bsqrt640320.multiply(b53360).divide(brsum.toBigDecimal(mc), mc);
    }

    public static BigDecimal factorial(BigDecimal bigdecimal) {
        BigDecimal result = new BigDecimal("1");
        int n = bigdecimal.intValue();
        while (n > 0) {
            result = result.multiply(new BigDecimal(""+n));
            n--;
        }
        return result;
    }

    public static BigInteger factorial(BigInteger bigint) {
        BigInteger result = new BigInteger("1");
        int n = bigint.intValue();
        while (n > 0) {
            result = result.multiply(new BigInteger(""+n));
            n--;
        }
        return result;
    } 

    //approximates f(x) = x^2 - x_n = 0;
    public static BigDecimal sqrt(BigDecimal bigdecimal, MathContext mc, int iterations) {
        BigDecimal b2 = new BigDecimal("2");
        BigDecimal x_n = bigdecimal.divide(b2); //initial guess
        for (int i=0; i<iterations; i++) {
            x_n = x_n.subtract(((x_n.multiply(x_n)).subtract(bigdecimal)).divide(b2.multiply(x_n), mc));
//System.out.println(x_n);
//System.out.println();
        }
        return x_n;
    }

    public static int digits_of_pi_correct(BigDecimal pi_approx) throws IOException {
        String pi_approx_string = pi_approx.toString();
//System.out.println(pi_approx_string);
//System.out.println("length="+pi_approx_string.length());
        int count = 0; //number of matching characters
        int index = 0; 
        BufferedReader inputstream = null;
        try {
            inputstream = new BufferedReader(new FileReader("pi-billion.txt"));
            int c;
            while ((c=inputstream.read()) != -1) {
                if (index == pi_approx_string.length()) return count - 2;
//System.out.println("c="+c);
//System.out.println("index="+index);
//System.out.println("charAt(index)="+pi_approx_string.charAt(index));
                if (c != pi_approx_string.charAt(index)) return count - 2;
                count++;
                index++;
            }
        } finally {
            inputstream.close();    
        }
        return -1; //error code
    }

    public static void main(String[] args) throws IOException { 
        int terms = Integer.parseInt(args[0]);
        MathContext mc = new MathContext(5000, RoundingMode.HALF_UP);
        int iterations = Integer.parseInt(args[1]);

        long t1 = System.currentTimeMillis();
        BigDecimal res = pi_chudnovsky_bigdecimal(terms, mc, iterations);
        long t2 = System.currentTimeMillis();
        System.out.println(res);
        System.out.println(digits_of_pi_correct(res) + " correct digits.");
        System.out.println("Computation time in milliseconds = " + (t2 - t1));
        System.out.println();

        long t3 = System.currentTimeMillis();
        BigDecimal res2 = pi_chudnovsky_bigrational(terms, mc, iterations);
        long t4 = System.currentTimeMillis();
        System.out.println(res2);
        System.out.println(digits_of_pi_correct(res) + " correct digits.");
        System.out.println("Computation time in milliseconds = " + (t4 - t3));
        System.out.println();
    }

}
