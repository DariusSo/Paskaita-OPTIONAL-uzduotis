package org.example;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.apache.commons.lang3.ArrayUtils;

public class Main {
    public static String filePath = "C:\\Users\\Darius\\IdeaProjects\\Paskaita-19-uzduotys\\src\\main\\java\\org\\example\\Duom1.txt";
    public static String filePath2 = "C:\\Users\\Darius\\IdeaProjects\\Paskaita-19-uzduotys\\src\\main\\java\\org\\example\\Rez1.txt";

    public static String moketinaSuma;
    public static String klientoPinigai;

    public static int[] litai = {5, 2, 1};
    public static int[] lituMonetos = {0, 0, 0};
    public static int[] centai = {50, 20, 10, 5, 2, 1};
    public static int[] centuMonetos = {0, 0, 0, 0, 0, 0};
    public static int visosMonetos = 0;
    public static String[] rasytiIFaila;
    

    public static void main(String[] args) throws IOException {
        int c = 0;
        rasytiIFaila = new String[klientuSkaicius() + 2];
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] l = line.split(" ");
            if (line.length() == 1){ // Praleidzia pirma eilute
                continue;
            }

            String s = String.valueOf(graza(l)); //Priskiria grazos kieki
            String[] grazosMasyvas = s.split("\\.");

            int monetuSkaicius = kiekCentoMonetu(grazosMasyvas) + kiekLitoMonetu(grazosMasyvas); // Bendras klientui grazintu monetu skaicius
            rasytiIFaila[c] = String.valueOf(monetuSkaicius); // Monetu skaicius pridedamas i masyva is kurio irasys i faila
            c++;

            visosMonetos = visosMonetos + monetuSkaicius; // Skaiciuojamas bendras grazinamas monetu skaicius
        }
        rasytiIFaila[c] = String.valueOf(visosMonetos);
        rasytiIFaila[c + 1] = monetuKiekis(lituMonetos, centuMonetos);
        fileReader.close();
        bufferedReader.close();
        irasytiIFaila(rasytiIFaila);
    }




    public static String monetuKiekis(int[] lituMonetos, int[] centuMonetos){ // Grazina daugiausia panaudotu monetu ir ju kieki
        int didziausiasLitas = 0;
        int didziausiasCentas = 0;

        for (int i = 0; i < lituMonetos.length; i++){
            if (didziausiasLitas < lituMonetos[i]){
                didziausiasLitas = lituMonetos[i];
            }
        }

        for (int i = 0; i < centuMonetos.length; i++){
            if (didziausiasCentas < centuMonetos[i]){
                didziausiasCentas = centuMonetos[i];
            }
        }
        if (didziausiasLitas > didziausiasCentas){
            return litai[ArrayUtils.indexOf(lituMonetos, didziausiasLitas)] + " Lt " + didziausiasLitas;
        }else{
            return centai[ArrayUtils.indexOf(centuMonetos, didziausiasLitas)] + " ct " + didziausiasCentas;
        }
    }
    public static int kiekCentoMonetu(String[] grazosMasyvas){ // Cento monetos klientui
        int i = 0;
        int monetuSkaicius = 0;
        while(Integer.parseInt(grazosMasyvas[1]) != 0){
            if (Integer.parseInt(grazosMasyvas[1]) - centai[i] < 0){
                i++;
            }else{
                grazosMasyvas[1] = String.valueOf(Integer.parseInt(grazosMasyvas[1]) - centai[i]);
                monetuSkaicius++;
                centuMonetos[i]++; // Skaiciuoja masyve, kiek kokiu monetu ideda
            }
        }
        return monetuSkaicius;
    }
    public static int kiekLitoMonetu(String[] grazosMasyvas){ // Lito monetos klientui
        int j = 0;
        int monetuSkaicius = 0;
        while(Integer.parseInt(grazosMasyvas[0]) != 0){
            if (Integer.parseInt(grazosMasyvas[0]) - litai[j] < 0){
                j++;
            }else{
                grazosMasyvas[0] = String.valueOf(Integer.parseInt(grazosMasyvas[0]) - litai[j]);
                monetuSkaicius++;
                lituMonetos[j]++; //Skaiciuoja masyve, kiek kokiu monetu ideda
            }
        }
        return monetuSkaicius;
    }
    public static BigDecimal graza(String[] l){
        if (l[1].length() == 1){
            moketinaSuma = l[0] + ".0" + l[1];
        }else{
            moketinaSuma = l[0] + "." + l[1];
        }
        if (l[3].length() == 1){
            klientoPinigai = l[2] + ".0" + l[3];
        }else{
            klientoPinigai = l[2] + "." + l[3];
        }
        BigDecimal graza = new BigDecimal(Double.parseDouble(klientoPinigai) - Double.parseDouble(moketinaSuma));
        graza = graza.setScale(2, RoundingMode.HALF_UP);
        return graza;
    }
    public static int klientuSkaicius() throws IOException {
        FileReader fileReader1 = new FileReader(filePath);
        BufferedReader bufferedReader1 = new BufferedReader(fileReader1);

        String line1;
        String klientuSkaicius = null;
        while ((line1 = bufferedReader1.readLine()) != null) {
            String[] l1 = line1.split(" ");
            if (line1.length() == 1) {
                klientuSkaicius = l1[0];
            }
        }
        bufferedReader1.close();
        fileReader1.close();
        return Integer.parseInt(klientuSkaicius);
    }
    public static void irasytiIFaila(String[] rasytiIFaila) throws IOException {
        FileWriter fileWriter = new FileWriter(filePath2, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        for (int i = 0; i < rasytiIFaila.length; i++){
            bufferedWriter.write(rasytiIFaila[i]);
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
        fileWriter.close();
    }
}