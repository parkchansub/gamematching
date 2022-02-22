package com.example.gamematching.item;

import java.util.Random;

/**
 * 게임 제약 사항
 *  모든 게임은 1:1 게임이다.
 *  1. 게임은 항상 한 사람이 승자가 되고 다른 한 사람은 패자가 되며, 무승부는 없다.
 *  2. 게임이 중단되거나 어느 한쪽이 게임을 포기하는 경우는 없다.
 *  3. 시나리오 1에서 모든 유저는 이기기 위해 최선을 다하며, 일부러 지는 경우는 없다.
 *  4. 시나리오 2에서는 일부러 지는 플레이어(어뷰저)가 존재한다.
 *  5. 게임에 걸리는 시간은 두 유저의 실력 차이가 가중치로 쓰이며, 실력 차이가 작을수록 오래 걸릴 확률이 높고 실력 차이가 클수록 짧게 걸릴 확률이 높다.
 *  6. 게임 한 판에 걸리는 시간은 최소 3분, 최대 40분이다. 걸리는 시간은 항상 분 단위로 표시된다.
 *
 *  7. 걸리는 시간은  = 40분 - (두 유저 간 고유 실력 차 / 99000 * 35) + e 으로 계산되며(소수점 이하는 버림),
 *      e의 값은 -5 이상 5 이하인 정수 중에서 무작위로 선택된 값이다.
 *  걸리는 시간이 3분보다 작을 경우 3이 되며, 40보다 클 경우 40이 된다.
 *
 *  8. 게임의 승부는 두 유저의 실력이 가중치로 쓰이며, 실력이 높은 유저가 이길 확률이 높다.
 *  9. 유저 A와 유저 B가 게임을 했을 때, 유저 A가 이길 확률은 (유저 A의 고유 실력) / (유저 A의 고유 실력 + 유저 B의 고유 실력)과 같다.
 */
public class Game {

    /* 3분 ~ 40 분 */
    private int playTime;

    int getWinRate(User user1, User user2){
        return (user1.getIter()) / (user1.getIter() + user2.getIter());
    }


    void play(User user1, User user2){
        int winRate = getWinRate(user1, user2);

        //new Random().nextInt(100) < winRate
    }

    private void calculatePlayTime(User user1, User user2) {

        double e = Math.random() * (5 + 5 + 1) - 5;
        this.playTime = (int) (40 - (Math.abs(user1.getIter() - user2.getIter()) / 99000 * 35) +e);

        if(playTime>40){
            this.playTime = 40;
        }

        if(playTime<3){
            this.playTime = 3;
        }

    }





}
