package org.example;

import org.apache.commons.lang3.RandomStringUtils;

public class OrderGenerator {

    public static Order getDefaultIngredients(){
        return new Order("61c0c5a71d1f82001bdaaa6d");
    }

    public static Order getNullIngredients(){
        return new Order(null);
    }

    public static Order getHashRandom(){
        return new Order(RandomStringUtils.randomAlphabetic(24));
    }
}
