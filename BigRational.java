import java.math.*;

//immutable ratio of BigIntegers
//Operations between BigRationals yield reduced results to keep computation times small
public class BigRational {
    public static final BigRational ONE = new BigRational(BigInteger.ONE, BigInteger.ONE);
    public static final BigRational ZERO = new BigRational(BigInteger.ZERO, BigInteger.ONE);
    public static final BigRational TEN = new BigRational(BigInteger.TEN, BigInteger.ONE);

    public final BigInteger num;
    public final BigInteger denom;
    
    public BigRational(BigInteger num, BigInteger denom) throws ArithmeticException {
        if (denom.equals(BigInteger.ZERO)) throw new ArithmeticException("division by zero");
        this.num = num;
        this.denom = denom;
    }

    public BigRational add(BigRational r2) {
        return new BigRational((this.num).multiply(r2.denom).add((this.denom).multiply(r2.num)), (this.denom).multiply(r2.denom)).reduce();
    }

    public BigRational subtract(BigRational r2) {
        return new BigRational((this.num).multiply(r2.denom).subtract((this.denom).multiply(r2.num)), (this.denom).multiply(r2.denom)).reduce();
    }

    public BigRational multiply(BigRational r2) {
        return new BigRational(this.num.multiply(r2.num), this.denom.multiply(r2.denom)).reduce();
    }

    public BigRational multiply(BigInteger bigint) {
        return new BigRational(this.num.multiply(bigint), this.denom).reduce();
    }

    public BigRational divide(BigRational r2) {
        return new BigRational(this.num.multiply(r2.denom), this.denom.multiply(r2.num)).reduce();
    }

    public BigRational divide(BigInteger bigint) {
        return new BigRational(this.num, this.denom.multiply(bigint)).reduce();
    }

    public BigRational reduce() {
        BigInteger gcd = num.gcd(denom);
        return new BigRational(num.divide(gcd), denom.divide(gcd));        
    }

    public BigRational invert() {
        return new BigRational(this.denom, this.num).reduce();
    }

    public BigRational sqrt(int iterations) {
        BigRational br2 = new BigRational(new BigInteger("2"), BigInteger.ONE);
        BigRational x_n = this.divide(br2); //initial guess
        for (int i=0; i<iterations; i++) {
            x_n = x_n.subtract(((x_n.multiply(x_n)).subtract(this)).divide(br2.multiply(x_n)));
//System.out.println(x_n);
        }
        return x_n;
    }

    public boolean equals(BigRational r2) {
        if (num.multiply(r2.denom).equals(denom.multiply(r2.num))) return true;
        return false;
    }

    public String toString() {
        return num.toString() + " / " + denom.toString();        
    }

    public BigDecimal toBigDecimal(MathContext mc) {
        BigDecimal decnum = new BigDecimal(num);
        BigDecimal decdenom = new BigDecimal(denom);
        return decnum.divide(decdenom, mc);
    }
        
    //unit tests
    public static void main(String[] args) { 
        MathContext mc = MathContext.DECIMAL128;

        BigRational r1 = new BigRational(new BigInteger("5"), new BigInteger("6"));
        BigRational r2 = new BigRational(new BigInteger("5"), new BigInteger("6"));
        System.out.println(r1 + " . equals(" + r2 + ") = " + r1.equals(r2));

        BigRational r3 = new BigRational(new BigInteger("5"), new BigInteger("6"));
        BigRational r4 = new BigRational(new BigInteger("7"), new BigInteger("6"));
        System.out.println(r3 + " . equals(" + r4 + ") = " + r3.equals(r4));
        System.out.println();

        BigRational r5 = new BigRational(new BigInteger("5"), new BigInteger("6"));
        BigRational r6 = new BigRational(new BigInteger("7"), new BigInteger("6"));
        System.out.println(r5 + " . add(" + r6 + ") = " + r5.add(r6));
        System.out.println();

        BigRational r7 = new BigRational(new BigInteger("5"), new BigInteger("6"));
        BigRational r8 = new BigRational(new BigInteger("7"), new BigInteger("6"));
        System.out.println(r7 + " . subtract(" + r8 + ") = " + r7.subtract(r8));
        System.out.println();

        BigRational r9 = new BigRational(new BigInteger("20"), new BigInteger("12"));
        System.out.println(r9 + " . reduce() = " + r9.reduce());
        System.out.println();

        BigRational r10 = new BigRational(new BigInteger("5"), new BigInteger("6"));
        BigRational r11 = new BigRational(new BigInteger("5"), new BigInteger("6"));
        System.out.println(r10 + " . multiply(" + r11 + ") = " + r10.multiply(r11));
        System.out.println();

        BigRational r12 = new BigRational(new BigInteger("4"), new BigInteger("7"));
        BigRational r13 = new BigRational(new BigInteger("5"), new BigInteger("6"));
        System.out.println(r12 + " . divide(" + r13 + ") = " + r12.divide(r13));
        System.out.println();

        BigRational r14 = new BigRational(new BigInteger("4"), new BigInteger("7"));
        System.out.println(r14 + " . invert() = " + r14.invert());
        System.out.println();

        BigInteger b1 = new BigInteger("35");
        BigInteger b2 = new BigInteger("28");
        System.out.println(b1 + " . gcd(" + b2 + ") = " + b1.gcd(b2));
        System.out.println();

        BigRational r15 = new BigRational(new BigInteger("37"), new BigInteger("15"));
        System.out.println(r15 + " . toBigDecimal() = " + r15.toBigDecimal(mc));
        System.out.println();
        
        BigRational r17 = new BigRational(new BigInteger("25"), new BigInteger("16"));
        int iterations = 3;
        BigRational r18 = r17.sqrt(iterations);
        System.out.println(r17 + " . sqrt(" + iterations + ") = " + r18);
        System.out.println(r17 + " . sqrt(" + iterations + ") . toBigDecimal() = " + r18.toBigDecimal(mc));
        System.out.println();

        BigRational r19 = new BigRational(new BigInteger("7"), new BigInteger("4"));
        BigInteger r20 = new BigInteger("6");
        System.out.println(r19 + " . multiply(" + r20 + ") = " + r19.multiply(r20));
        System.out.println();

        BigRational r21 = new BigRational(new BigInteger("3"), new BigInteger("4"));
        BigInteger r22 = new BigInteger("6");
        System.out.println(r21 + " . divide(" + r22 + ") = " + r21.divide(r22));
        System.out.println();
    }

}
