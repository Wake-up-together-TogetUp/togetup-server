package com.wakeUpTogetUp.togetUp.api.avatar.util;

import com.wakeUpTogetUp.togetUp.api.avatar.application.RandomFoodProvider;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class RandomFoodProviderImpl implements RandomFoodProvider {
    private static final List<String> FOODS = List.of(
            "피자", "햄버거", "스시", "파스타", "샐러드", "김치찌개", "비빔밥", "초밥", "스테이크", "라면");

    private static final Random RANDOM = new Random();

    @Override
    public String getRandomFoodWord() {
        return FOODS.get(RANDOM.nextInt(FOODS.size()));
    }
}
