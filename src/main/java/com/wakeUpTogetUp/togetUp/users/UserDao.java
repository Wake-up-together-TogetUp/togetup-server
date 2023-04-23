package com.wakeUpTogetUp.togetUp.users;

import org.springframework.stereotype.Repository;
import lombok.Data;

@Repository
@Data
public class UserDao {
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
