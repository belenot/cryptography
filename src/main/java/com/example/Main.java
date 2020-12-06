package com.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws Exception {
        List<BigInteger> primes = DSA.sieveOfEratosthenes(30).stream().map( v -> BigInteger.valueOf(v)).collect(Collectors.toList());
        System.out.println(primes);
        System.out.println("Enter message");
        String content = new BufferedReader(new InputStreamReader(System.in)).readLine();
        DSA dsa = new DSA(content.getBytes());
        BigInteger p = new BigInteger("13232376895198612407547930718267435757728527029623408872245156039757713029036368719146452186041204237350521785240337048752071462798273003935646236777459223");
        System.out.println("p: " + p);
        BigInteger q = new BigInteger("857393771208094202104259627990318636601332086981");
        System.out.println("q: " + q);
        BigInteger g = new BigInteger("5421644057436475141609648488325705128047428394380474376834667300766108262613900542681289080713724597310673074119355136085795982097390670890367185141189796");
        System.out.println("g: " + g);
        BigInteger x = new BigInteger("792647853324835944125296675259316105451780620466");
        System.out.println("x: " + x);
        BigInteger y = new BigInteger("10783827985936883407800478884376885258012329124816552994400318669417122279843086645137200743427232531167766104260606805303022314906254403593803159583034340");
        System.out.println("y: " + y);
        BigInteger[] rs = dsa.engineSign(x, p, q ,g);
        BigInteger r = rs[0];
        System.out.println("r: " + r);
        BigInteger s = rs[1];
        System.out.println("s: " + s);

        System.out.println("Verify: " + dsa.engineVerify(y, r, s, p, q, g));
    }
}
