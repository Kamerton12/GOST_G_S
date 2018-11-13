import java.math.BigInteger;

public class Main {

    public static void main(String[] args)
    {
        BigInteger p = BigInteger.valueOf(107);//Prime
        BigInteger q = BigInteger.valueOf(53);//Prime, is divisor of p-1
        BigInteger a = getA(q, p);// a^q mod p == 1
        BigInteger x = BigInteger.valueOf(14);//Less, then q; Secret key
        BigInteger y = a.modPow(x, p);// p q a y; Open key

        BigInteger hash = BigInteger.valueOf(837156);

        //coding
        BigInteger[] DS;
        {
            BigInteger k = BigInteger.valueOf(27);//Less, then q
            BigInteger r = a.modPow(k, p).mod(q);
            BigInteger ourHash = hash;
            if(hash.mod(q).compareTo(BigInteger.ZERO) == 0)
                ourHash = BigInteger.ONE;
            BigInteger s = x.multiply(r).add(k.multiply(ourHash)).mod(q);
            DS = new BigInteger[]{r, s};
        }
        //decoding
        BigInteger r = DS[0];
        BigInteger s = DS[1];

        BigInteger v = hash.modPow(q.subtract(BigInteger.valueOf(2)), q);
        BigInteger z1 = s.multiply(v).mod(q);
        BigInteger z2 = q.subtract(r).multiply(v).mod(q);
        BigInteger u = a.modPow(z1,p).multiply(y.modPow(z2, p)).mod(p).mod(q);
        if(u.compareTo(r) == 0)
            System.out.println("Signature Correct");
    }



    public static BigInteger getA(BigInteger d, BigInteger m)
    {
        BigInteger[][] E = {{BigInteger.ONE,BigInteger.ZERO},{BigInteger.ZERO,BigInteger.ONE}};
        while(true)
        {
            BigInteger r = m.mod(d);
            if(r.compareTo(BigInteger.ZERO) == 0)
            {
                return E[1][1];
            }
            BigInteger q = m.divide(d);
            BigInteger[][] mult = {{BigInteger.ZERO,BigInteger.ONE},{BigInteger.ONE, q.negate()}};
            E = matrMult(E, mult);
            m = d;
            d = r;
        }
    }

    public static BigInteger[][] matrMult(BigInteger[][] a, BigInteger[][] b)
    {
        BigInteger leftUp = a[0][0].multiply(b[0][0]).add(a[0][1].multiply(b[1][0]));
        BigInteger rightUp = a[0][0].multiply(b[0][1]).add(a[0][1].multiply(b[1][1]));
        BigInteger leftDown = a[1][0].multiply(b[0][0]).add(a[1][1].multiply(b[1][0]));
        BigInteger rightDown = a[1][0].multiply(b[0][1]).add(a[1][1].multiply(b[1][1]));
        return new BigInteger[][]{{leftUp, rightUp},{leftDown, rightDown}};
    }


}
