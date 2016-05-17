import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class Chudnovsky extends Object {

    public static BigDecimal pi_chudnovsky(int N, int precision, int iterations) {
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
        BigDecimal bsqrt640320 = sqrt(b640320, precision, iterations);
        MathContext bmathcontext = new MathContext(precision, RoundingMode.HALF_UP);

        for (int k=0; k<N; k++) {
            BigDecimal bk = new BigDecimal(""+k);

            BigDecimal bnum = (bminus1.pow(k))
                  .multiply(factorial(b6.multiply(bk)))  
                  .multiply(b13591409.add(b545140134.multiply(bk)));

            BigDecimal bdenom = (factorial(bk).pow(3))
                    .multiply((factorial(b3.multiply(bk))))
                    .multiply(b640320.pow(3*k));

            BigDecimal bratio = bnum.divide(bdenom, bmathcontext);
            bsum = bsum.add(bratio);
//System.out.println("k="+k);
//System.out.println("bratio="+bratio);
//System.out.println("bsum="+bsum);
//System.out.println(b53360.multiply(bsqrt640320).divide(bsum, bmathcontext));
//System.out.println();
        }
//System.out.println();
        return b53360.multiply(bsqrt640320).divide(bsum, bmathcontext);
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

    //approximates f(x) = x^2 - x_n = 0;
    public static BigDecimal sqrt(BigDecimal bigdecimal, int precision, int iterations) {
        BigDecimal b2 = new BigDecimal("2");
        MathContext bmathcontext = new MathContext(precision, RoundingMode.HALF_UP);
        BigDecimal x_n = bigdecimal.divide(b2); //initial guess
        for (int i=0; i<iterations; i++) {
            x_n = x_n.subtract(((x_n.multiply(x_n)).subtract(bigdecimal)).divide(b2.multiply(x_n), bmathcontext));
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
        MathContext bmathcontext = new MathContext(5000, RoundingMode.HALF_UP);
        BigDecimal b5000123 = new BigDecimal(5000123);
        BigDecimal b125000123 = new BigDecimal(125000123);
        BigDecimal b3 = new BigDecimal(3);
        BigDecimal b4 = new BigDecimal(4);
        BigDecimal b53360 = new BigDecimal(53360);
        BigDecimal b640320 = new BigDecimal(640320);
        BigDecimal bpiapprox1 = new BigDecimal(args[0]);
           
        //System.out.println(sqrt(b3, 10, 50));
        //System.out.println(b3);
        //System.out.println(sqrt(b640320, 25, 50));
        //System.out.println(b640320);
        BigDecimal res = pi_chudnovsky(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        System.out.println(res);
        System.out.println(digits_of_pi_correct(res) + " correct digits.");
        System.out.println();
    }

}
