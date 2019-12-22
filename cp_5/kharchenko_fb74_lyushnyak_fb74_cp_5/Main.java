package com.gmail.xapchenko2000;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        rsa(generateKeyPair());
    }


    public static boolean testMillerRabin(BigInteger p) {
        int k = 15;
        BigInteger s = howManyTimesDiv2(p);
        BigInteger t = (p.subtract(BigInteger.valueOf(1)).divide(BigInteger.valueOf(2).pow(s.intValue())));
        BigInteger one = new BigInteger("1");
        
        for (int i = 0; i < k; i++) {
            BigInteger a = randBigInteger(p.subtract(BigInteger.valueOf(2)));
            BigInteger x = a.modPow(t, p);

            if (x.equals(one) || x.equals(p.subtract(BigInteger.valueOf(1)))) continue;

            for (int j = 0; j < s.intValue() - 1; j++) {

                x = x.modPow(BigInteger.valueOf(2), p);
                if (x.equals(one)) return false;
                if (x.equals(p.subtract(BigInteger.valueOf(1)))) break;

            }
            return false;
        }
        return true;
    }

    public static BigInteger pow(BigInteger base, BigInteger exponent) {
        BigInteger result = BigInteger.ONE;
        while (exponent.signum() > 0) {
            if (exponent.testBit(0)) result = result.multiply(base);
            base = base.multiply(base);
            exponent = exponent.shiftRight(1);
        }
        return result;
    }

    public static BigInteger randBigInteger(BigInteger upperLimit) {
        BigInteger randomNumber;
        BigInteger zero = new BigInteger("0");
        Random random = new Random();
        do {
            randomNumber = new BigInteger(upperLimit.bitLength(), random);
        } while (randomNumber.compareTo(upperLimit) >= 0);

        return randomNumber;


    }

    public static BigInteger howManyTimesDiv2(BigInteger p) {
        BigInteger counter = BigInteger.valueOf(0);
        BigInteger one = new BigInteger("1");
        while (!p.mod(BigInteger.valueOf(2)).equals(one)) {
            counter = counter.add(BigInteger.valueOf(1));
            p = p.divide(BigInteger.valueOf(2));
        }
        return counter;
    }


    public static ArrayList<BigInteger> generateKeyPair() {
        ArrayList<BigInteger> keyPair = new ArrayList<>();
//        ArrayList<BigInteger> badReturn = new ArrayList<>();
//
//        BigInteger upperLimit = BigInteger.valueOf(2).pow(128);
//        for (int i = 0; i < 4; i++) {
//            BigInteger randBigInteger;
//            do {
//                randBigInteger = randBigInteger(upperLimit);
//                System.out.println("Candidate for key = " + i + " ---> " + randBigInteger);
//            } while (!testMillerRabin(randBigInteger));
//            keyPair.add(i, randBigInteger);
//        }
//        BigInteger p = keyPair.get(0);
//        BigInteger q = keyPair.get(1);
//        BigInteger p1 = keyPair.get(2);
//        BigInteger q1 = keyPair.get(3);
//
//        if (p.multiply(q).compareTo(p1.multiply(q1)) == -1) {
//            return keyPair;
//        } else {
//            generateKeyPair();
//        }
        //my pq and p1q1
        BigInteger p = new BigInteger("43399655120072260970935313291528674073");//real
        BigInteger q = new BigInteger("130580507916984957504958663382739097567");
        BigInteger p1 = new BigInteger("53331100945154617591518012107103607177");
        BigInteger q1 = new BigInteger("295430518263560483288730000329718905483");
        keyPair.add(0, p);
        keyPair.add(1, q);
        keyPair.add(2, p1);
        keyPair.add(3, q1);
        return keyPair;
    }

    public static void rsa(ArrayList<BigInteger> listOfAandB) {
        //User A
        System.out.println("Key ->");
        listOfAandB.forEach(x -> System.out.println(x));
        System.out.println("============================");
        BigInteger n = listOfAandB.get(0).multiply(listOfAandB.get(1));
        BigInteger oyler = (listOfAandB.get(0).subtract(BigInteger.valueOf(1))).multiply(listOfAandB.get(1).subtract(BigInteger.valueOf(1)));
        //BigInteger e = findE(oyler);// open key (n, e)
        BigInteger e = new BigInteger("10001",16);
        BigInteger d = e.modInverse(oyler);//A private key
        BigInteger m = new BigInteger("999");//Our message
        BigInteger sign = sign(m, d, n);//Sign A user

        BigInteger c = findC(m, e, n);

        BigInteger v = verify(sign, e, n);

        BigInteger cDecrypt = decrypt(c, d, n);
        //User B
        BigInteger n1 = listOfAandB.get(2).multiply(listOfAandB.get(3));
        BigInteger oyler1 = (listOfAandB.get(2).subtract(BigInteger.valueOf(1))).multiply(listOfAandB.get(3).subtract(BigInteger.valueOf(1)));
        BigInteger e1 = findE(oyler1);// open key (n, e)
        BigInteger d1 = e1.modInverse(oyler1);//A private key

        //Send key
        ArrayList<BigInteger> send = sendKey(m, e1, n, n1, d);

        ArrayList<BigInteger> receive = receiveKey(send, d1, n1);

        Boolean isCorrect = userACheckTheMessageOfUserB(receive, e, n);

        System.out.println("User A Generate public key pair");
        System.out.println("n = " +n.toString(16).toUpperCase());
        System.out.println("fN = " + oyler);
        System.out.println("e = " + e.toString(16));
        System.out.println("User A generate private key");
        System.out.println("d = " + d.toString(16));
        System.out.println("User A create message and encrypt it");
        System.out.println("Message = " + m.toString(2));
        System.out.println("We encrypt the message");
        System.out.println("Encrypt message = " + c);
        System.out.println("c decrypt = " + cDecrypt);
        System.out.println("If we verify it, we can see the same message");
        System.out.println("v = " + v);
        System.out.println("sign = " + sign.toString(16));
        System.out.println("User B Generate public key pair");
        System.out.println("n1 = " + n1);
        System.out.println("e1 = " + e1);
        System.out.println("User B generate private key");
        System.out.println("d1 = " + d1 + "\n");
        System.out.println("User A send message to B the pair (k1 , S1)");
        System.out.println(sendKey(m, e1, n, n1, d).toString());
        System.out.println("User B receive and send to A the pair (k, S)");
        System.out.println(receiveKey(send, d1, n1).toString());
        System.out.println("If all ok you can see true -> " + isCorrect);
        BigInteger m1 = new BigInteger("999");//Our message
        BigInteger N = new BigInteger("E61F01081BA833758DA9377ED6FBA0359D23B5154888BE7DC7C9648E704E1D7", 16);
        BigInteger n11 = listOfAandB.get(0).multiply(listOfAandB.get(1));
        System.out.println("=================="+N);
        System.out.println("=================="+n11);
        BigInteger E = new BigInteger("10001");
        System.out.println("n = " +E.toString(16).toUpperCase());


        photo(d, n);
    }


    public static BigInteger findE(BigInteger oyler) {
        BigInteger e;
        for (int i = 0; i < 101; i++) {
            e = randBigInteger(oyler);
            if (e.gcd(oyler).compareTo(BigInteger.valueOf(1)) == 0) return e;
        }
        return BigInteger.valueOf(-1);
    }

    public static BigInteger findC(BigInteger m, BigInteger e, BigInteger n) {
        return m.modPow(e, n);
    }

    public static BigInteger decrypt(BigInteger c, BigInteger d, BigInteger n) {
        return c.modPow(d, n);
    }

    public static BigInteger sign(BigInteger m, BigInteger d, BigInteger n) {
        return m.modPow(d, n);
    }

    public static BigInteger verify(BigInteger sign, BigInteger e, BigInteger n) {
        return sign.modPow(e, n);

    }

    public static ArrayList<BigInteger> sendKey(BigInteger k, BigInteger e1, BigInteger n, BigInteger n1, BigInteger d) {

        //A make message (k1, S1)
        ArrayList<BigInteger> userAMessage = new ArrayList<>();
        BigInteger s = k.modPow(d, n);

        BigInteger k1 = k.modPow(e1, n1);
        BigInteger s1 = s.modPow(e1, n1);

        userAMessage.add(0, k1);
        userAMessage.add(1, s1);

        return userAMessage;

    }

    public static ArrayList<BigInteger> receiveKey(ArrayList<BigInteger> sendKey, BigInteger d1, BigInteger n1) {
        //B make message (k, S)
        ArrayList<BigInteger> userBMessage = new ArrayList<>();

        BigInteger k = sendKey.get(0).modPow(d1, n1);
        BigInteger s = sendKey.get(1).modPow(d1, n1);

        userBMessage.add(0, k);
        userBMessage.add(1, s);

        return userBMessage;
    }

    public static boolean userACheckTheMessageOfUserB(ArrayList<BigInteger> userBMessage, BigInteger e, BigInteger n) {
        //k = s^e mod n
        BigInteger k = userBMessage.get(0);
        BigInteger s = userBMessage.get(1);

        return k.equals(s.modPow(e, n));
    }

    public static void photo(BigInteger d, BigInteger n) {
        BigInteger m = new BigInteger("9994");//Our message
        BigInteger N = new BigInteger("82B29B7215D97AA73F7D4867DB873CC3ECEFE28FFD39AD5C2EA87F2D6F256CD8CECF8CA7D957C5EEFADDB31C886BDDCBA144B8AD6BBFBF1C7204F17799C09227", 16);//from site
        BigInteger E = new BigInteger("10001", 16);//from site

        BigInteger s = m.modPow(d, n);

        BigInteger k1 = m.modPow(E, N);
        BigInteger s1 = s.modPow(E, N);
        System.out.println("n  - > " + n);//my n = p*q
        System.out.println("n1 - > " + N + "\n");

        System.out.println("Input for site: ");
        System.out.println("k1 " + k1.toString(16));
        System.out.println("s1 " + s1.toString(16));
        System.out.println("n  - > " + n.toString(16));

    }
}
