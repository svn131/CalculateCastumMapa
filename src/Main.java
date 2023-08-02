import java.util.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        System.out.println("Введите пример");

        Scanner scn = new Scanner(System.in);
        String example = scn.nextLine();

        Main main = new Main();

        System.out.println(main.calculateMetod(example));
    }


    public String calculateMetod(String scn) {

//
//       String str[] = (scn.split(""));
//       String stringa = new String("stringatext");

        List<String> emptyList = new ArrayList<>(Arrays.asList(scn.split(" ")));

//        List<String> emptyList = new ArrayList<>(List.of((scn.split(""))));
//        ArrayList<String> exampleList = (ArrayList<String>) Arrays.asList(scn.split(""));


        String a = emptyList.get(0);
        String b = emptyList.get(2);

        String znak = emptyList.get(1);

        int sum = 0;
        boolean rim = false;
        String sumString;

        int intA;
        int intB;


        CustomHashMap<String, Integer> moyamapa = new CustomHashMap<>();

        CustomHashMap<String, String> finalMoyaMapa = new CustomHashMap<>();


        moyamapa.put("I", 1);
        moyamapa.put("II", 2);
        moyamapa.put("III", 3);
        moyamapa.put("IV", 4);
        moyamapa.put("V", 5);
        moyamapa.put("VI", 6);
        moyamapa.put("VII", 7);
        moyamapa.put("VIII", 8);
        moyamapa.put("IX", 9);
        moyamapa.put("X", 10);
        moyamapa.put("XX", 20);
        moyamapa.put("XXX", 30);
        moyamapa.put("XM", 40);
        moyamapa.put("M", 50);
        moyamapa.put("MX", 60);
        moyamapa.put("MXX", 70);
        moyamapa.put("MXXX", 80);
        moyamapa.put("XC", 90);


        finalMoyaMapa.put("1", "I");
        finalMoyaMapa.put("2", "II");
        finalMoyaMapa.put("3", "III");
        finalMoyaMapa.put("4", "IV");
        finalMoyaMapa.put("5", "V");
        finalMoyaMapa.put("6", "VI");
        finalMoyaMapa.put("7", "VII");
        finalMoyaMapa.put("8", "VIII");
        finalMoyaMapa.put("9", "IX");
        finalMoyaMapa.put("10", "X");
        finalMoyaMapa.put("20", "XX");
        finalMoyaMapa.put("30", "XXX");
        finalMoyaMapa.put("40", "XM");
        finalMoyaMapa.put("50", "M");
        finalMoyaMapa.put("60", "MX");
        finalMoyaMapa.put("70", "MXX");
        finalMoyaMapa.put("80", "MXXX");
        finalMoyaMapa.put("90", "XC");


        try {
            intA = Integer.parseInt(a);
            intB = Integer.parseInt(b);
        } catch (NumberFormatException e) {
//            throw new RuntimeException(e);
            System.out.println("Вот тут я спользовал трай кетч в целях напоминающих иф елсе");
            rim = true;
            intA = moyamapa.get(a);
            intB = moyamapa.get(b);
        }

        switch (znak) {
            case "+":
                sum = intA + intB;
                break;
            case "-":
                sum = intA - intB;
                break;
            case "*":
            case "x":
                sum = intA * intB;
                break;
            case "/":
            case ":":
                sum = intA / intB;
                break;
            default:
                throw new RuntimeException(); // проверить
        }


        sumString = Integer.toString(sum);

        if (rim) {
            String arrSum[] = sumString.split("");
            if (arrSum.length == 1) {
                sumString = finalMoyaMapa.get(sumString);
            } else {
                String desytki = arrSum[0] + "0";
                sumString = finalMoyaMapa.get(desytki) + finalMoyaMapa.get(arrSum[1]);

            }
        }


        return sumString;
    }
}
