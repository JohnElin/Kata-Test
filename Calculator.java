import java.io.IOException;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Map;

public class Calculator {

    public static void main(String[] args)  {
         String st = consoleGet();
         String result = "";
         int err = 0;

         try {
             result = calc(st);
         }
         catch (IOException nfc) {
             err = 1;
         }
        if(err !=1) {
            System.out.println("Output:");
           System.out.println(result);
        }

    }
    public static String consoleGet(){
        System.out.println("Input:");
        Scanner in = new Scanner(System.in);
        return in.nextLine();

    }

    public static String romeOut(Integer i, Map s){
        String out = "";
        int iLo = (i%10);
        int iHi = i - iLo;
        if( iHi>0 & iLo>0) {
            out = s.get(iHi).toString()+s.get(iLo).toString();
        }
        else if ( iHi>0 & iLo==0) {
            out = s.get(iHi).toString();
        }
        else if (iLo>=0) {
            out = s.get(iLo).toString();
        }
        return out;
    }
    public static String calc(String st) throws IOException {
        String result = "";
        int operandes = 0; // Кол-во операторов в принятой строке
        char operand = '?'; // Операнд арифм. операции
        int lSide = 0, rSide = 0; //lSide и rSide обозначают систему счисл. левой и правой части выражения, где 0 не определена, 1 арабская, 3 римская
        int lSVal = 0, rSVal = 0;

        Map<Integer, String> s = new TreeMap<>();
        //  s.put(0, "null");
        s.put(1, "I");
        s.put(2, "II");
        s.put(3, "III");
        s.put(4, "IV");
        s.put(5, "V");
        s.put(6, "VI");
        s.put(7, "VII");
        s.put(8, "VIII");
        s.put(9, "IX");
        s.put(10, "X");
        s.put(20, "XX");
        s.put(30, "XXX");
        s.put(40, "XL");
        s.put(50, "L");
        s.put(60, "LX");
        s.put(70, "LXX");
        s.put(80, "LXXX");
        s.put(90, "XC");
        s.put(100, "C");
        //Подсчет количества операторов и определение оператора
        char[] a = st.toCharArray();
        for (char ilt : a) {
            switch (ilt) {
                case '*','/','+','-' -> {
                    operand = ilt;
                    ++operandes;
                }

            }
        }
        // Выброс Exception если кол-во операторов != 1
        if (operandes > 1) {
                System.out.println("Output:");
                System.out.println("throws Exception //т.к. формат математической операции не удовлетворяет заданию - два положительных операнда и один оператор (+, -, /, *)");
                throw new IOException();
        } else if (operandes == 0) {
                System.out.println("Output:");
                System.out.println("throws Exception //т.к. строка не является математической операцией");
                throw new IOException();
        }
        st = st.toUpperCase();
        String[] stSplit = st.split("["+operand+"]");
        if(stSplit.length<2){ // Проверяем наличие правой части выражения
            System.out.println("Output:");
            System.out.println("throws Exception //т.к. строка не является математической операцией");
            throw new IOException();
        }
        stSplit[0] = stSplit[0].strip(); // Удаляем лишние пробелы в левой и правой части
        stSplit[1] = stSplit[1].strip();
        try {
            lSVal = Integer.parseInt(stSplit[0]);
            lSide = 1;
        }
        catch (NumberFormatException ignored) {

        }
        try {
            rSVal = Integer.parseInt(stSplit[1]);
            rSide = 1;
        }
        catch (NumberFormatException ignored) {

        }
        if (lSide == 0) { // Если в левой части выражения не найдены арабские цифры
            for (Map.Entry<Integer, String> entry : s.entrySet()) {
                if (entry.getValue().equals(stSplit[0])) {  // Проверяем наличие римских цифр от 1 до 10
                    lSide = 3;
                    lSVal = entry.getKey();
                }
            }
        }
        if (rSide == 0) {  // Если в правой части выражения не найдены арабские цифры
            for (Map.Entry<Integer, String> entry1 : s.entrySet()) {
                if (entry1.getValue().equals(stSplit[1])) { // Проверяем наличие римских цифр от 1 до 10
                    rSide = 3;
                    rSVal = entry1.getKey();
                }
            }
        }
        if ((rSide+lSide)==2&&(rSVal<1||lSVal<1||rSVal>10||lSVal>10)) { // Проверка арабских цифр на соответствие условиям
                System.out.println("Output:");
                System.out.println("throws Exception //т.к на вход допустимы только целые числа от 1 до 10");
                throw new IOException();
        }
        if((rSide+lSide)==0) { // Ни справа, ни слева не обнаружены арабские или римские цифры
                System.out.println("Output:");
                System.out.println("throws Exception //т.к. формат математической операции не удовлетворяет заданию - два положительных операнда и один оператор (+, -, /, *)");
                throw new IOException();
        }

        if((rSide+lSide)==4) { // С одной стороны арабская цифра, с другой римская
                System.out.println("Output:");
                System.out.println("throws Exception //т.к. используются одновременно разные системы счисления");
                throw new IOException();
        }
        if((rSide+lSide)==3) { // Только слева или справа обнаружена римская цифра, но не обнаружена арабская
                System.out.println("Output:");
                System.out.println("throws Exception //т.к. допустимые числа в римской системе счисления не обнаружены");
                throw new IOException();
        }
        if((rSide+lSide)==6&&(rSVal>10||lSVal>10)) { // Проверка на величину римских цифр
            System.out.println("Output:");
            System.out.println("throws Exception //т.к на вход допустимы только числа от I до X");
            throw new IOException();
        }



        switch (operand) {  // Выбор арифметической операции
            case ('*') -> {
                if (lSide == 1) result = String.valueOf(lSVal * rSVal);
                else result = romeOut(lSVal * rSVal, s);
            }
            case ('/') -> {
                if (lSide == 1) result = String.valueOf(lSVal / rSVal);
                else {
                    if (lSVal / rSVal < 1) {
                            System.out.println("Output:");
                            System.out.println("throws Exception //т.к. в римском счислении не используются числа менее 1");
                            throw new IOException();
                    } else result = romeOut(lSVal / rSVal, s);
                }
            }
            case ('+') -> {
                if (lSide == 1) result = String.valueOf(lSVal + rSVal);
                else result = romeOut(lSVal + rSVal, s);
            }
            case ('-') -> {
                if (lSide == 1) result = String.valueOf(lSVal - rSVal);
                else if (lSVal - rSVal < 1) {
                        System.out.println("throws Exception //т.к. в римской системе нет отрицательных чисел");
                        throw new IOException();
                } else result = romeOut(lSVal - rSVal, s);
            }
        }

        return result;
    }

}


