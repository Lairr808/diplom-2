package org.example;

import org.apache.commons.lang3.RandomStringUtils;

public class UserGenerator {
    public static User getDefault(){
        return new User("ninja@yandex.ru", "000000", "naruto");
    }

    public static User getNewData(){
        return new User(RandomStringUtils.randomAlphabetic(15),RandomStringUtils.randomAlphabetic(6), RandomStringUtils.randomAlphabetic(6));
    }

    public static User getEmailAndPasswordRandom(){
        return new User(RandomStringUtils.randomAlphabetic(15), RandomStringUtils.randomAlphabetic(6), "naruto");
    }

    public static User getPasswordNull(){
        return new User("ninja@yandex.ru", "", "naruto");
    }

}
