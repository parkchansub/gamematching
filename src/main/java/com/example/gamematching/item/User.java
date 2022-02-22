package com.example.gamematching.item;


/***
 * 유저 제약 사항
 * 초기에 모든 유저는 고유한 실력을 갖고 있으며, 실력은 1,000 이상 100,000 이하의 정수로 표현할 수 있다.
 * 모든 유저의 실력 분포는 평균이 40,000 표준편차가 20,000인 정규분포를 따른다.
 * 정확히 같은 실력 값을 가진 두 명 이상의 유저는 존재하지 않는다. 즉, 모든 유저의 실력 값은 중복되지 않는다.
 * 매칭을 신청한 유저는 매칭을 기다리는 대기열에 들어간다.
 * 모든 유저는 매칭 신청 후 매칭을 15분까지 기다린다. 즉, 15분을 초과하면 매칭 신청을 취소하여 대기열에서 제외된다.
 * 매칭을 취소한 유저가 곧바로 매칭을 신청할 수도 있다.
 * 각 유저는 고유 id를 갖고 있으며, id는 1 이상 유저 수 이하인 정수이다.
 */


public class User {
    private int id;

    /* 1000 이상 100,000 이하(유일한 값) */
    private int iter;

    private int matchingTime;

    public int getIter() {
        return iter;
    }
}
