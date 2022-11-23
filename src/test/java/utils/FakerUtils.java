package utils;

import com.github.javafaker.Faker;

public class FakerUtils {

    public static String generateSummary() {
        Faker faker = new Faker();
        return "Issue " + DateUtils.getCurrentDate() + " " + faker.regexify("[A-Za-z0-9 ,_-]{10}");
    }

    public static String generateDescription() {
        Faker faker = new Faker();
        return "This is description created on" + DateUtils.getCurrentDateTime();
    }
}
