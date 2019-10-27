package com.gmail.xapchenko2000;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // считываем ОТ, алфавит
        String openText = readFile("C:/Users/xapch/Desktop/labaTwoCrypt/key and openText/openText.txt");
        String russianAlph = readFile("C:/Users/xapch/Desktop/russianAlph.txt");
        //чистим ОТ и алфавит
        String clearText = clearWithOutSpaces(openText);
        String clearAlph = clearWithOutSpaces(russianAlph);

        System.out.println("Your text: ");
        System.out.println(openText);

        Scanner sc = new Scanner(System.in);

        System.out.println("Hello, choose the length of your key: 2, 3, 4, 5, 15. ");
        System.out.println("To find the key of cipher text press 1.");
        System.out.println("To divide your cipher text on Yi press 9");
        System.out.println("To decrypt text press 100");
        byte userChoice = sc.nextByte();
        float resultIndex;


        switch (userChoice) {
            case 2:
                System.out.println("Your choice is 2.");
                String key2 = readFile("C:/Users/xapch/Desktop/labaTwoCrypt/key and openText/key2.txt");
                String clearKey2 = clearWithOutSpaces(key2);
                System.out.println("Your key: ");
                System.out.println(clearKey2);
                System.out.println("Your cipher text: ");
                //System.out.println(alph(clearAlph, clearText, clearKey2));
                resultIndex = conformityIndex(openText);
                System.out.println("Index for key=2 ---> " + resultIndex);
                break;
            case 3:
                System.out.println("Your choice is 3.");
                String key3 = readFile("C:/Users/xapch/Desktop/labaTwoCrypt/key and openText/key3.txt");
                String clearKey3 = clearWithOutSpaces(key3);
                System.out.println("Your key: ");
                System.out.println(clearKey3);
                System.out.println("Your cipher text: ");
                //System.out.println(alph(clearAlph, clearText, clearKey3));
                resultIndex = conformityIndex(encrypt(clearAlph, clearText, clearKey3));
                System.out.println("Index for key=3 ---> " + resultIndex);
                break;
            case 4:
                System.out.println("Your choice is 4.");
                String key4 = readFile("C:/Users/xapch/Desktop/labaTwoCrypt/key and openText/key4.txt");
                String clearKey4 = clearWithOutSpaces(key4);
                System.out.println("Your key: ");
                System.out.println(clearKey4);
                System.out.println("Your cipher text: ");
                // System.out.println(alph(clearAlph, clearText, clearKey4));
                resultIndex = conformityIndex(encrypt(clearAlph, clearText, clearKey4));
                System.out.println("Index for key=4 ---> " + resultIndex);
                break;
            case 5:
                System.out.println("Your choice is 5.");
                String key5 = readFile("C:/Users/xapch/Desktop/labaTwoCrypt/key and openText/key5.txt");
                String clearKey5 = clearWithOutSpaces(key5);
                System.out.println("Your key: ");
                System.out.println(clearKey5);
                System.out.println("Your cipher text: ");
                //System.out.println(alph(clearAlph, clearText, clearKey5));
                resultIndex = conformityIndex(encrypt(clearAlph, clearText, clearKey5));
                System.out.println("Index for key=5 ---> " + resultIndex);
                System.out.println();

                break;
            case 15:
                System.out.println("Your choice is 15.");
                String key15 = readFile("C:/Users/xapch/Desktop/labaTwoCrypt/key and openText/key15.txt");
                String clearKey15 = clearWithOutSpaces(key15);
                System.out.println("Your key: ");
                System.out.println(clearKey15);
                System.out.println("Your cipher text: ");
                // System.out.println(alph(clearAlph, clearText, clearKey15));
                resultIndex = conformityIndex(encrypt(clearAlph, clearText, clearKey15));
                System.out.println("Index for key=15 ---> " + resultIndex);
                break;
            case 1:
                String cipherText = readFile("C:/Users/xapch/Desktop/labaTwoCrypt/key and openText/cipherText.txt");
                for (int i = 2; i <= 30; i++) {
                    String str = chooseYi(cipherText, i); // выбираем каждый второй символ из ШТ и считаем ИС
                    resultIndex = conformityIndex(str);
                    System.out.println("Index for k = " + i + "  ---> " + resultIndex);
                    // Длина ключа равна 14, так как ИС при ключе 14 ближе всего к ИС русского языка
                    //Index for k =14  ---> 0.05285719
                }

                break;
            case 9:
                String cipherTextOne = readFile("C:/Users/xapch/Desktop/labaTwoCrypt/key and openText/cipherText.txt");
                System.out.println(cipherTextOne);
                for (int i = 0; i <= 14; i++) {
                    String str = chooseYi(cipherTextOne, i);
                    System.out.println(str);
                    findMostpopular(str);
                    System.out.println("=================================================================");
                }


                break;
            case 100:
                String cipherTextLast = readFile("C:/Users/xapch/Desktop/labaTwoCrypt/key and openText/cipherText.txt");
                String cClear = clearWithOutSpaces(cipherTextLast);
                System.out.println(cipherTextLast);
                String keyReal = readFile("C:/Users/xapch/Desktop/labaTwoCrypt/key and openText/foundKey.txt");
                String clearKey = clearWithOutSpaces(keyReal);
                System.out.println("Your key: ");
                System.out.println(clearKey);
                System.out.println("Your open text: ");
                System.out.println(decrypt(clearAlph, cClear, clearKey));

                break;
            default:
                System.out.println("Sorry, you entered wrong number");
                break;

        }

        System.out.println();
        printAlph(clearAlph);
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

    public static String clearWithOutSpaces(String str) {

        String newStr = str.toLowerCase();
        String clear = newStr.replaceAll("[^a-я]|[a-z,]", "");
        return clear;
    }

    public static String encrypt(String alphabet, String userText, String key) {

        char[] alphabetArray = alphabet.toCharArray();
        char[] userTextArray = userText.toCharArray();
        char[] keyArray = key.toCharArray();
        int result;
        int r;
        String cipherText = "";


        for (int i = 0; i < userTextArray.length - 1; i++) {

            r = i % (keyArray.length);
            result = ((keyArray[r] + userTextArray[i]) % 32);
            cipherText += alphabetArray[result];
        }
        return cipherText;
    }

    public static String decrypt(String alphabet, String userText, String key) {

        char[] alphabetArray = alphabet.toCharArray();
        char[] userTextArray = userText.toCharArray();
        char[] keyArray = key.toCharArray();
        int result;
        int r;
        String openText = "";


        for (int i = 0; i < userTextArray.length - 1; i++) {

            r = i % (keyArray.length);
            result = ((userTextArray[i] - keyArray[r]) % 32);
            if (result < 0) result += 32;
            openText += alphabetArray[result];
        }
        return openText;
    }


    public static void printAlph(String alphabet) {
        char[] alphabetArray = alphabet.toCharArray();

        for (int i = 0; i < alphabetArray.length; i++) {
            System.out.println(alphabetArray[i] + " " + i);
        }

    }

    public static float conformityIndex(String cipherText) {

        char[] charArray = cipherText.toCharArray();
        float allLatters = cipherText.length();
        int count = 0;
        float coefficient;
        char ch = charArray[count];
        Map<Character, Float> charCounter = new HashMap<Character, Float>();
        for (int i = 0; i < cipherText.length() - 1; i++) {
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
            frequancy = charCounter.get(key);
            //System.out.format(key + "-" + "%.0f%n", frequancy);
            result += ((charCounter.get(key) * (charCounter.get(key) - 1)));

        }
        coefficient = 1 / ((allLatters) * (allLatters - 1));
        //System.out.println("Result ---> " + (result) * coefficient);


        return result * coefficient;
    }

    public static String chooseYi(String cipherText, int period) {


        char[] arrayCipher = cipherText.toCharArray();
        String stringOfPeriod = "";

        for (int i = period; i < arrayCipher.length - 1;i+=period) {
            stringOfPeriod += arrayCipher[i];
        }

        return stringOfPeriod;

    }


    public static void findMostpopular(String cipherText) {

        char[] charArray = cipherText.toCharArray();

        int count = 0;

        char ch = charArray[count];
        Map<Character, Float> charCounter = new HashMap<Character, Float>();
        for (int i = 0; i < cipherText.length() - 1; i++) {
            ch = charArray[i];


            if (charCounter.containsKey(ch)) {
                charCounter.put(ch, charCounter.get(ch) + 1);
            } else {
                charCounter.put(ch, (float) 1);
            }
        }
        float max = 0;
        for (Character key : charCounter.keySet()) {
            max = charCounter.get(key) > max ? charCounter.get(key) : max;
            //System.out.println(key + "-" + charCounter.get(key));
            System.out.println(key + "max is " + max);
        }

    }
}
