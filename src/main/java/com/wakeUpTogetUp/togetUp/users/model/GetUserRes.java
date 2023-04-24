package com.wakeUpTogetUp.togetUp.users.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetUserRes {
    private String userNickname;
    private String profileImageLink;
    private int cntFollower;
    private int cntFollowing;
    private int totalScrapbook;
    private int totalCoupon;
    private int totalPoint;
    private int totalOrder;
    private int totalLike;
    private int totalReview;
}