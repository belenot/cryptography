package com.example;

import java.math.BigInteger;
//import java.security.Random;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Arrays;

public class DSA {

    byte[] content;

    private static final int BLINDING_BITS = 7;
    private static final BigInteger BLINDING_CONSTANT = BigInteger.valueOf(1 << BLINDING_BITS);

    public DSA(byte[] content) {
        this.content = content;
    }

    public BigInteger[] engineSign(BigInteger x, BigInteger p, BigInteger q, BigInteger g) {
        BigInteger k = generateK(q);
        BigInteger r = generateR(p, q, g, k);
        BigInteger s = generateS(x, q, r, k);
        return new BigInteger[]{r, s};
    }

    public boolean engineVerify(BigInteger y, BigInteger r, BigInteger s, BigInteger p, BigInteger q, BigInteger g) {
        BigInteger w = generateW(q, s);
        BigInteger v = generateV(y, p, q, g, w, r);
        return v.equals(r);
    }

    private BigInteger generateK(BigInteger q) {
        byte[] kValue = new byte[(q.bitLength() + 7)/8 + 8];
        Random random = new Random();
        random.nextBytes(kValue);
        return new BigInteger(1, kValue)
                .mod(q.subtract(BigInteger.ONE))
                .add(BigInteger.ONE);
    }

    private BigInteger generateR(BigInteger p, BigInteger q, BigInteger g,
                                 BigInteger k) {
        Random random = new Random();
        BigInteger blindingValue = new BigInteger(BLINDING_BITS, random);
        blindingValue = blindingValue.add(BLINDING_CONSTANT);
        k = k.add(q.multiply(blindingValue));
        BigInteger temp = g.modPow(k, p);
        return temp.mod(q);
    }

    private BigInteger generateS(BigInteger x, BigInteger q,
                                 BigInteger r, BigInteger k) {
        byte[] s2 = content;
        int nBytes = q.bitLength()/8;
        if (nBytes < s2.length) {
            s2 = Arrays.copyOfRange(s2, 0, nBytes);
        }
        BigInteger z = new BigInteger(1, s2);
        BigInteger k1 = k.modInverse(q);
        return x.multiply(r).add(z).multiply(k1).mod(q);
    }

    private BigInteger generateW(BigInteger q, BigInteger s) {
        return s.modInverse(q);
    }

    private BigInteger generateV(BigInteger y, BigInteger p, BigInteger q, BigInteger g, BigInteger w, BigInteger r) {
        byte[] s2 = content;
        int nBytes = q.bitLength()/8;
        if (nBytes < s2.length) {
            s2 = Arrays.copyOfRange(s2, 0, nBytes);
        }
        BigInteger z = new BigInteger(1, s2);
        BigInteger u1 = z.multiply(w).mod(q);
        BigInteger u2 = (r.multiply(w)).mod(q);

        BigInteger t1 = g.modPow(u1,p);
        BigInteger t2 = y.modPow(u2,p);
        BigInteger t3 = t1.multiply(t2);
        BigInteger t5 = t3.mod(p);
        return t5.mod(q);
    }


    public static List<Integer> sieveOfEratosthenes(int n) {
        boolean prime[] = new boolean[n + 1];
        Arrays.fill(prime, true);
        for (int p = 2; p * p <= n; p++) {
            if (prime[p]) {
                for (int i = p * 2; i <= n; i += p) {
                    prime[i] = false;
                }
            }
        }
        List<Integer> primeNumbers = new LinkedList<>();
        for (int i = 2; i <= n; i++) {
            if (prime[i]) {
                primeNumbers.add(i);
            }
        }
        return primeNumbers;
    }
}
