package com.wakeUpTogetUp.togetUp.api.avatar.util;

import com.wakeUpTogetUp.togetUp.api.avatar.application.RandomFoodProvider;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class RandomFoodProviderImpl implements RandomFoodProvider {
    private static final List<String> FOODS = List.of(
            "피자", "햄버거", "스시", "파스타", "샐러드", "김치찌개", "비빔밥", "초밥", "스테이크",
            "라면", "냉면", "갈비", "삼겹살", "떡볶이", "칼국수", "보쌈", "닭갈비", "감자탕", "카레", "돈까스",
            "곱창", "치킨", "해물파전", "타코", "딤섬", "똠얌꿍", "팟타이", "케밥", "라멘", "우동", "타코야키",
            "스키야키", "훠궈", "마라탕", "꿔바로우", "짜장면", "탕수육", "양꼬치", "깐풍기", "대나무");

    private static final Random RANDOM = new Random();

    @Override
    public String getRandomFoodWord() {
        return FOODS.get(RANDOM.nextInt(FOODS.size()));
    }
}
