package com.example.authentification.Utils;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static boolean isUsernameValid(String string) {
        if (!string.isEmpty()) {
            capitalize(string);
            String regex = "(?:[`~!@#$%^&*()+=\\[{\\]}|\\\\'<,>?/\";:\\n])";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(string);
            return matcher.find();
        }
        return true;
    }

    public static boolean isNameStringsValid(String string) {
        if (!string.isEmpty()) {
            capitalize(string);
            String regex = "(?:[`~!@#$%^&*()+=\\[{\\]}|\\\\'<,.>?/\";:\\d\\n])";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(string);
            return matcher.find();
        }
        return true;
    }

    public static boolean isEmailValid(String string) {
        String regex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);

        return !matcher.find();
    }

    public static boolean isPasswordValid(String string) {
        String regex = "((?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_=+-{}\\[\\]])(?=.*[A-Z]).{8,})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);

        return !matcher.find();
    }

    //club, tournament name, city, address have same regex
    public static boolean isClubValid(String string) {
        if (!string.isEmpty()) {
            capitalize(string);
            String regex = "(?:[`~!@#$%^&*()+=\\[{\\]}|\\\\'<>?\";:\\n])";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(string);
            return matcher.find();
        }
        return true;
    }

    public static int calculateAge(Date birthdate) {
        Calendar birth = Calendar.getInstance();
        birth.setTime(birthdate);
        Calendar today = Calendar.getInstance();
        int yearDifference = today.get(Calendar.YEAR)
                - birth.get(Calendar.YEAR);
        if (today.get(Calendar.MONTH) < birth.get(Calendar.MONTH)) {
            yearDifference--;
        } else {
            if (today.get(Calendar.MONTH) == birth.get(Calendar.MONTH)
                    && today.get(Calendar.DAY_OF_MONTH) < birth
                    .get(Calendar.DAY_OF_MONTH)) {
                yearDifference--;
            }
        }
        if(yearDifference < 0){
            yearDifference = 0;
        }
        return yearDifference;
    }
}
