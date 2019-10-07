package com.gmail.xapchenko2000;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        String justString = readFile("C:/Users/xapch/Desktop/n.txt");


        String withSpaces = clearWithSpaces(justString);
        String withOutSpaces = clearWithOutSpaces(justString);
        int allWith = allLatters(withSpaces);
        int allWithOut = allLatters(withOutSpaces);
        int all = allLatters(justString);


        System.out.println("Энтропия в монограме с пробелом и без: ");

        monogram(withSpaces, allWith);
//        float rOne = (float) ((1 - (monogram(withSpaces, allWith)) / (Math.log(33) / Math.log(2))));
//        System.out.println("Redundancy --->  " + rOne + "\n");
//

        monogram(withOutSpaces, allWithOut);
//        float rTwo = (float) ((1 - (monogram(withOutSpaces, allWithOut)) / (Math.log(33) / Math.log(2))));
//        System.out.println("Redundancy --->  " + rTwo + "\n");

//
//        System.out.println("Энтропия в накладывающайся биграме с пробелом и без: ");
        bigram(withSpaces, allWith);
//        float rThree = (float) ((1 - (bigram(withSpaces, allWith)) / (Math.log(33) / Math.log(2))));
//        System.out.println("Redundancy --->  " + rThree + "\n");

        bigram(withOutSpaces, allWithOut);
//        float rFour = (float) ((1 - (bigram(withOutSpaces, allWithOut)) / (Math.log(33) / Math.log(2))));
//        System.out.println("Redundancy --->  " + rFour + "\n");
        ;
//        System.out.println("Энтропия в биграме с пробелом и без: ");
        bigramNakl(withSpaces, allWith);
//        float rFive = (float) ((1 - (bigramNakl(withSpaces, allWith)) / (Math.log(33) / Math.log(2))));
//        System.out.println("Redundancy --->  " + rFive + "\n");
        ;
        bigramNakl(withOutSpaces, allWithOut);
//        float rSix = (float) ((1 - (bigramNakl(withOutSpaces, allWithOut)) / (Math.log(33) / Math.log(2))));
//        System.out.println("Redundancy --->  " + rSix + "\n");

    }


    public static String readFile(String path) {
        String str = "";

        try {
            FileInputStream file = new FileInputStream(path);
            DataInputStream dis = new DataInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(dis));
            String Contents = "";


            while ((Contents = br.readLine()) != null) {
                str += Contents;
            }

        } catch (IOException e1) {
            System.out.println(e1);
        }


        return str;
    }

    public static int allLatters(String str) {

        int quantity = str.length();
        return quantity;
    }

    public static String clearWithSpaces(String str) {

        String newStr = str.toLowerCase();
        String clear = newStr.replaceAll("[^a-я ]|[a-z, ,»,«,,?!.,°]", "");
        return clear;
    }

    public static String clearWithOutSpaces(String str) {

        String newStr = str.toLowerCase();
        String clear = newStr.replaceAll("[^a-я]|[a-z, , ,,»,«,,?!.,°]", "");
        return clear;
    }


    public static float monogram(String clear, int allLatters) {

        char[] charArray = clear.toCharArray();
        int count = 0;
        char ch = charArray[count];
        Map<Character, Float> charCounter = new HashMap<Character, Float>();
        for (int i = 0; i < clear.length() - 1; i++) {
            ch = charArray[i];


            if (charCounter.containsKey(ch)) {
                charCounter.put(ch, charCounter.get(ch) + 1);
            } else {
                charCounter.put(ch, (float) 1);
            }
        }
        float result = 0;
        float frequancy;
        for (Character key : charCounter.keySet()) {
            frequancy = charCounter.get(key) / allLatters;
            System.out.format(key + "-" + "%.3f%n",frequancy);
            result += ((charCounter.get(key) / allLatters) * ((Math.log(charCounter.get(key) / allLatters)) / Math.log(2.0)));

        }

        System.out.println("Result ---> " + (-result));


        return 0;
    }


    public static float bigram(String clear, int allLatters) {

        char[] charArray = clear.toCharArray();
        Map<String, Float> biCounter = new HashMap<String, Float>();
        for (int i = 0; i < clear.length() - 1; i++) {

            char s = charArray[i];
            char sOne = charArray[i + 1];

            StringBuilder sb = new StringBuilder().append(s).append(sOne);
            String bi = sb.toString();


            if (biCounter.containsKey(bi)) {
                biCounter.put(bi, biCounter.get(bi) + 1);
            } else {
                biCounter.put(bi, (float) 1);
            }
        }
        float result = 0;
        float frequancy;
        for (String key : biCounter.keySet()) {
            frequancy = biCounter.get(key) / allLatters;
            System.out.format(key + "-" + "%.4f%n",frequancy);
            result += (((biCounter.get(key) / allLatters) * ((Math.log(biCounter.get(key) / allLatters)) / Math.log(2.0))));

        }

        System.out.println("Result ---> " + (-result) / 2);


        return 0;
    }


    public static float bigramNakl(String clear, int allLatters) {

        char[] charArray = clear.toCharArray();
        Map<String, Float> biCounter = new HashMap<String, Float>();
        for (int i = 0; i < clear.length() - 1; ++i) {

            char s = charArray[i];
            char sOne = charArray[i + 1];

            String bi = String.valueOf(s) + sOne;


            if (biCounter.containsKey(bi)) {
                biCounter.put(bi, biCounter.get(bi) + 1);

            } else {
                biCounter.put(bi, (float) 1.0);
                i++;
            }


        }
        float result = 0;
        float frequancy;
        for (String key : biCounter.keySet()) {
            frequancy = biCounter.get(key) / allLatters;
            System.out.format(key + "-" + "%.4f%n",frequancy);
            result += (((biCounter.get(key) / allLatters) * ((Math.log(biCounter.get(key) / allLatters)) / Math.log(2.0))));

        }

        System.out.println("Result ---> " + (-result)/2);


        return 0;
    }

}