package com.filippov_vs_2019;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class RomanNumeral extends Numeral {


    private static int[] numbers = {
            1000, 900, 500, 400, 100, 90,
            50, 40, 10, 9, 5, 4, 1
    };

    private static String[] letters = {
            "M", "CM", "D", "CD", "C", "XC",
            "L", "XL", "X", "IX", "V", "IV", "I"
    };


    private static String createRomanString(String roman) {

        // Constructor.  Creates the Roman number with the given representation.
        // For example, RomanNumeral("xvii") is 17.  If the parameter is not a
        // legal Roman numeral, a NumberFormatException is thrown.  Both upper and
        // lower case letters are allowed.

        String inputRoman = roman;

        if (roman.length() == 0)
            throw new NumberFormatException("An empty string does not define a Roman numeral.");

        // for (int i = 0; i < roman.toLowerCase().length(); i++) {
        //     char x = roman.charAt(i);
        //     if (!RomanNumeral.letters.)
        //
        // }

        roman = roman.toUpperCase();  // Convert to upper case letters.

        int i = 0;       // A position in the string, roman;
        int arabic = 0;  // Arabic numeral equivalent of the part of the string that has
        //    been converted so far.

        while (i < roman.length()) {

            char letter = roman.charAt(i);        // Letter at current position in string.
            int number = letterToNumber(letter);  // Numerical equivalent of letter.

            if (number < 0)
                throw new NumberFormatException(
                        "Illegal character \"" + letter + "\" in roman numeral : " + inputRoman);

            i++;  // Move on to next position in the string

            if (i == roman.length()) {
                // There is no letter in the string following the one we have just processed.
                // So just add the number corresponding to the single letter to arabic.
                arabic += number;
            }
            else {
                // Look at the next letter in the string.  If it has a larger Roman numeral
                // equivalent than number, then the two letters are counted together as
                // a Roman numeral with value (nextNumber - number).
                int nextNumber = letterToNumber(roman.charAt(i));
                if (nextNumber > number) {
                    // Combine the two letters to get one value, and move on to next position in string.
                    arabic += (nextNumber - number);
                    i++;
                }
                else {
                    // Don't combine the letters.  Just add the value of the one letter onto the number.
                    arabic += number;
                }
            }

        }  // end while


        if (arabic > 3999)
            throw new NumberFormatException("Roman numeral must have value 3999 or less.");

        return String.valueOf(arabic);
    } // end constructor

    public RomanNumeral(String rom) {
        super(createRomanString(rom));
    }

    private static int letterToNumber(char letter) {
        // Find the integer value of letter considered as a Roman numeral.  Return
        // -1 if letter is not a legal Roman numeral.  The letter must be upper case.
        switch (letter) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                return -1;
        }
    }


    public static String toString(int N) {
        // N represents the part of num that still has
        //   to be converted to Roman numeral representation.
        // Return the standard representation of this Roman numeral.
        if (N <= 0) {
            throw new NumberFormatException("Roman values must be > 0" + " -but " + N + " is <= 0");
        }
        String roman = "";  // The roman numeral.
        for (int i = 0; i < numbers.length; i++) {
            while (N >= numbers[i]) {
                roman += letters[i];
                N -= numbers[i];
            }
        }
        return roman;
    }


    public String binaryOperation(int secondOperand, String op) {
        String stringResult = super.binaryOperation(secondOperand, op);
        double res = Double.parseDouble(stringResult);
        if ( res <= 0 || res % 1 != 0 ) {
            throw new NumberFormatException("roman nambers result mast be >= 0 and maust have no fractional part");
        }
        return RomanNumeral.toString((int)res);

    }


} // end class RomanNumeral


class Numeral {
    private final int num;

    static private final String errorStr = "Value of number must must be an integer between 1 and 10.";


    public static boolean isLegalNumeral(String inputStr) {
        int arabic;
        try {
            arabic = Integer.parseInt(inputStr);
            if (Double.valueOf(arabic) != Double.parseDouble(inputStr)) {
                return false;
            }
        }
        catch (NumberFormatException e) {
            return false;
        }
        return true;

    }

    public Numeral(String inputStr) {
        int arabic = 0;
        if (!isLegalNumeral(inputStr)) {
            throw new NumberFormatException(errorStr);

        }
        num = Integer.parseInt(inputStr);
    }

    public int toInt() {
        return num;
    }

    public String binaryOperation(int secondOperand, String op) {
        double res = 0;

        double op1 = this.toInt();
        if (op1 < 1 || op1 > 10 || secondOperand < 1 || secondOperand > 10) {
            throw new NumberFormatException(errorStr);
        }
        double op2 = secondOperand;

        if (op.equals("+")) {
            res = op1 + op2;
        }
        else if (op.equals("-")) {
            res = op1 - op2;
        }
        else if (op.equals("/")) {
            res = op1 / op2;
        }
        else if (op.equals("*")) {
            res = op1 * op2;
        }
        else {
            throw new NumberFormatException("operator must be '+' or '-'  or '/' or '*'");

        }

        return String.valueOf(res);

    }
}


class JavaCalcRoman {

    public static String result(String inputString) {
        String name = inputString.trim();
        String[] splited = name.split("\\s+");

        if (splited.length != 3) {
            throw new NumberFormatException("input line must contain 3 operands splitted by spaces!");
        }


        String resultStr;

        try {
            Numeral n1 = new Numeral(splited[0]);
            Numeral n2 = new Numeral(splited[2]);
            resultStr = n1.binaryOperation(n2.toInt(), splited[1]);
        }
        catch (NumberFormatException e) {
            RomanNumeral n1 = new RomanNumeral(splited[0]);
            RomanNumeral n2 = new RomanNumeral(splited[2]);
            resultStr = n1.binaryOperation(n2.toInt(), splited[1]);

        }
        System.out.println(resultStr);
        return resultStr;

    }

    public static void main(String[] args) throws IOException {

        while (true) {
            System.out.println("Please enter operation  1 + 2  or 1 / 3  etc : ");

            //Enter data using BufferReader
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(System.in));

            JavaCalcRoman.result(reader.readLine());



        }

    }
}
