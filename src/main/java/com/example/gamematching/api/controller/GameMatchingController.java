package com.example.gamematching.api.controller;

/**
 * 게임 매칭 알고리즘 설계하기
 * 모든 게임 유저들은 자신의 실력을 공정하게 평가받기를 원한다. 대진운이 좋지 않아 자신보다 실력이 높은 상대만 만나 패배하고 등급이 낮게 책정되면 게임에 대한 불만이 쌓여 이탈로 이어질 수 있다.
 *
 * 당신은 매칭을 신청한 유저들이 너무 오래 기다리지 않도록 하면서, 모든 유저가 공정하게 등급을 받을 수 있도록 매칭을 성사시켜야 한다.
 *
 * 매칭 목표
 * 목표: 각 유저가 원래 갖고 있는 고유한 실력(응시자는 확인할 수 없음)을 기준으로 정렬한 결과와,
 * 부여된 등급을 기준으로 정렬된 결과가 최대한 유사하도록,
 * 유저 간 매칭을 성사시키고 결과에 따라 등급을 부여해야 한다.
 * 단, 매칭을 신청한 유저가 기다리는 시간이 적을수록 좋다.
 *
 * 매칭 제약 사항 설명
 * 유저 제약 사항
 * 초기에 모든 유저는 고유한 실력을 갖고 있으며, 실력은 1,000 이상 100,000 이하의 정수로 표현할 수 있다.
 * 모든 유저의 실력 분포는 평균이 40,000 표준편차가 20,000인 정규분포를 따른다.
 * 정확히 같은 실력 값을 가진 두 명 이상의 유저는 존재하지 않는다. 즉, 모든 유저의 실력 값은 중복되지 않는다.
 * 매칭을 신청한 유저는 매칭을 기다리는 대기열에 들어간다.
 * 모든 유저는 매칭 신청 후 매칭을 15분까지 기다린다. 즉, 15분을 초과하면 매칭 신청을 취소하여 대기열에서 제외된다.
 * 매칭을 취소한 유저가 곧바로 매칭을 신청할 수도 있다.
 * 각 유저는 고유 id를 갖고 있으며, id는 1 이상 유저 수 이하인 정수이다.
 *
 * 게임 제약 사항
 * 모든 게임은 1:1 게임이다.
 * 1. 게임은 항상 한 사람이 승자가 되고 다른 한 사람은 패자가 되며, 무승부는 없다.
 * 2. 게임이 중단되거나 어느 한쪽이 게임을 포기하는 경우는 없다.
 * 시나리오 1에서 모든 유저는 이기기 위해 최선을 다하며, 일부러 지는 경우는 없다.
 * 시나리오 2에서는 일부러 지는 플레이어(어뷰저)가 존재한다.
 * 게임에 걸리는 시간은 두 유저의 실력 차이가 가중치로 쓰이며, 실력 차이가 작을수록 오래 걸릴 확률이 높고 실력 차이가 클수록 짧게 걸릴 확률이 높다.
 * 게임 한 판에 걸리는 시간은 최소 3분, 최대 40분이다.
 * 걸리는 시간은 항상 분 단위로 표시된다.
 * 걸리는 시간은 40분 - (두 유저 간 고유 실력 차 / 99000 * 35) + e 으로 계산되며(소수점 이하는 버림), e의 값은 -5 이상 5 이하인 정수 중에서 무작위로 선택된 값이다. 걸리는 시간이 3분보다 작을 경우 3이 되며, 40보다 클 경우 40이 된다.
 * 게임의 승부는 두 유저의 실력이 가중치로 쓰이며, 실력이 높은 유저가 이길 확률이 높다.
 * 유저 A와 유저 B가 게임을 했을 때, 유저 A가 이길 확률은 (유저 A의 고유 실력) / (유저 A의 고유 실력 + 유저 B의 고유 실력)과 같다.
 *
 * 매칭 제약 사항
 * 매칭 대기열에 있는 유저가 중복해서 매칭을 신청하는 입력은 주어지지 않는다.
 * 게임을 하는 도중에 매칭을 신청하는 입력은 주어지지 않는다.
 * 게임이 종료된 시각과 매칭을 신청하는 시각(분)이 같을 수 있다.
 * 매칭에 성공하여 게임을 시작하면 대기열에서 제외된다.
 *
 * 시나리오
 * 본시험에는 시나리오 2개가 주어진다.
 *
 * 시나리오 1
 * 매칭을 1번 이상 신청하는 유저의 수: 30명
 *
 * 매칭 신청 빈도: 분당 신청 수 평균 1건
 *
 * 시나리오 2
 * 매칭을 1번 이상 신청하는 유저의 수: 900명
 * 매칭 신청 빈도: 분당 신청 수 평균 45건
 * [주의] 시나리오 2의 차이점 안내
 *
 * 시나리오 2에는 게임을 일부러 지려는 어뷰저가 존재한다. 어뷰저는 상대보다 높은 실력을 갖고 있음에도 일부러 지는 유저이다. 어뷰저는 자신보다 낮은 실력을 가진 상대에게만 80%의 확률로 지며, 게임에 걸리는 시간은 10분 이하이다. 나머지 20% 확률에서는 어뷰저가 승리하며, 이때 걸리는 시간은 일반 게임과 같은 방식으로 산출되며 10분을 초과할 수 있다.
 * 전체 유저 중 어뷰저의 비율은 5%이다. 게임에 걸리는 시간은 3 ~ 10 사이의 랜덤한 값이다. 자신보다 높은 실력을 가진 유저에게는 위와 같이 일부러 지려는 행동을 하지 않는다. 다시 말해, 어뷰저끼리 게임을 하게 된다면, 둘 중 높은 실력을 가진 어뷰저가 80% 확률로 진다.
 *
 * 점수
 * 점수는 시나리오별로 다음과 같이 계산된다.
 *
 * 구분	공식
 * 정확성 점수 1	((U - ∑(｜고유 실력 순위 - 부여된 등급 순위｜)) / U) * 100
 * 정확성 점수 2	((D - ∑(｜매칭된 두 유저의 실력 차｜ * 2) / 매칭 횟수) / D) * 100
 * 효율성 점수	((R - ∑(매칭을 신청한 유저가 기다린 시간)) / R) * 100
 * 총점	(정확성 점수 1 + 정확성 점수 2) * 1.2 + 효율성 점수 * 0.8
 * ※ U = 유저 수2 * 0.5
 * ※ D = 매칭된 두 유저의 실력 차이의 최댓값(=99,000)
 * ※ R = 대기 시간 합의 최댓값(=매칭 신청 수 * 15)
 *
 * 정확성 점수 1은 시뮬레이션이 끝난 뒤, 응시자가 유저에게 부여한 등급으로 정렬한 결과와 유저 고유 실력으로 정렬한 결과가 얼마나 유사한지를 평가한다. 두 결과가 유사할수록 점수가 높다. 유저 A, B, C의 고유 실력 순위가 각각 1, 2, 3위이고, 부여된 등급 순위가 각각 1, 3, 2위일 경우, 정확성 점수 1은 U = 32 * 0.5 = 4.5이므로, ((4.5 - (0 + 1 + 1)) / 4.5) * 100 로 계산된다.
 * 고유 실력 순위 = 유저가 처음부터 갖고 있는 고유한 실력 값을 기준으로 내림차순 정렬했을 때 순위를 의미한다. 가장 실력 값이 높은 유저가 1위이다.
 * 부여된 등급 순위 = 응시자가 조정한 유저의 grade를 기준으로 내림차순 정렬했을 때 순위를 의미한다. 가장 grade가 높은 유저가 1위가 된다. 만약 grade값이 같은 유저가 여러 명이라면, 중복 값들에 대해서 동일 순위로 표시하고, 중복 순위 다음 값에 대해서는 중복 개수만큼 떨어진 순위를 부여한다. 예를 들어 유저 4명의 grade값이 [100, 99, 99, 95] 라면 각각 [1위, 2위, 2위, 4위]가 된다.
 * 정확성 점수 2는 두 유저가 매칭될 때마다 얼마나 비슷한 실력의 유저끼리 매칭되었는지를 평가한다. 매칭된 두 유저의 실력 차이에 2를 곱한 뒤 평균을 구해 점수에서 차감한다. 즉, 실력이 비슷한 유저끼리 매칭될수록 점수가 높다.
 * 효율성 점수는 매칭 신청 후 매칭되기까지 걸린 시간의 합을 점수에서 차감한다. 즉, 유저가 기다린 시간이 짧을수록 점수가 높다.
 * 최종 점수는 (시나리오 1에서 획득한 총점) x (시나리오 1의 배점 가중치) + (시나리오 2에서 획득한 총점) x (시나리오 2의 배점 가중치)로 부여된다. 단, 배점 가중치는 지원자에게 공개하지 않는다.
 * 참고
 * 완료 후 작성한 코드를 업로드해야 한다. 여러 파일일 경우 zip으로 압축하여 업로드한다. 시험 시간 내에 코드를 제출하지 않으면 획득한 점수는 무시된다.
 * 각 시나리오의 총점은 모든 시도 중 최고 점수로 반영된다.
 * 총점은 시뮬레이션에서 카카오 게임 서버의 상태가 finished가 될 때 산출된다.
 * API REFERENCE
 * 540분 동안 시뮬레이션을 진행한다. 시뮬레이션은 분 단위로 진행되며, 매 분을 한 턴으로 가정한다. 예를 들어, 0분을 0턴, 1분을 1턴, ... , 540분을 540턴으로 가정한다. 0턴은 시뮬레이션을 시작하는 턴으로, 매칭을 신청하는 유저가 존재하지 않는다. 1턴부터 매칭 대기열이 활성화된다.
 *
 * 대기 중인 유저를 짝지어주는 Match API와, 대기 중인 유저들의 정보를 받을 수 있는 WaitingLine API, 이번 턴에 끝난 게임 결과 정보를 받을 수 있는 GameResult API가 주어진다. 게임 결과 정보를 참고하여 ChangeGrade API를 이용해 특정 유저의 등급을 수정할 수 있다. Match API는 대기열에서 대기 중인 유저들을 매칭시켜 줄 수 있으며, 동시에 다음 턴으로 넘어간다(1분 진행). 따라서 시뮬레이션의 진행을 위해서는 매칭시킬 유저가 없더라도 Match API를 이용해 아무도 매칭시키지 않음을 전달해야 한다.
 *
 * 1턴부터 540턴까지는 매칭을 신청할 수 있는 기간이다. 541턴 이후로는 새로운 매칭 신청이 발생하지 않는다. 540턴에 매칭을 신청한 유저의 경우 최대 555턴까지 매칭을 기다릴 수 있으며, 556턴이 되면 매칭을 취소한다. 만약 555턴에 매치가 성사되어 40분 동안 게임이 진행된다면 595턴에 게임 결과를 받아볼 수 있다. 따라서 595턴까지 GameResult API를 이용해 게임 결과를 확인해야 한다.
 *
 * 따라서 1턴~555턴까지는 대기열에 있는 유저들을 확인하는 WaitingLine API와 대기열의 유저를 매치시키는 Match API를 사용할 수 있다. 그 외 턴에서 WaitingLine API는 빈 배열만을 반환하며 Match API는 어떤 유저도 매칭시키지 못한다. 이번 턴에 끝난 게임 결과를 반환하는 GameResult API와, 유저의 등급을 수정할 수 있는 ChangeGrade API는 1턴~595턴 중 어느 턴에든 실행할 수 있다. 595턴이 끝나야 시뮬레이션이 종료되기 때문에, Match API를 이용해 매치시킬 유저가 없더라도 595턴까지 진행해야 한다. 마지막으로, 595턴에서 한번 더 Match API를 실행시키면 서버 상태가 finished가 되며, 어떤 API도 사용할 수 없다. 즉, 유저의 등급을 변화시키는 ChangeGrade API는 595턴이 끝나기 전에 실행해야 한다.
 *
 * HTTPS로 통신하며, 모든 정보는 json배열로 제공한다.
 *
 * 초당 10회가 넘는 API 호출은 서버가 응답하지 않을 수 있다.
 *
 * BASE URL: https://huqeyhi95c.execute-api.ap-northeast-2.amazonaws.com/prod
 *
 * Response Codes
 * API를 성공적으로 호출할 경우 200 코드를 반환하고, 그 외의 경우에는 아래의 코드를 반환한다.
 *
 * Response Code	Description
 * 200 OK	성공
 * 400 Bad Request	Parameter가 잘못됨 (범위, 값 등)
 * 401 Unauthorized	인증을 위한 Header가 잘못됨
 * 500 Internal Server Error	서버 에러, 채팅으로 문의 요망
 * Start API
 * 문제를 풀기 위한 key를 발급한다. Start API를 실행하면 파라미터로 전달한 문제 번호에 맞게 모든 유저에 대한 정보를 초기화한다.
 *
 * Request
 * POST /start
 * X-Auth-Token: {X-Auth-Token}
 * Content-Type: application/json
 * Header
 *
 * Name	Description
 * X-Auth-Token	문제에서 발급되는 응시자 식별 토큰 값
 * Parameter
 *
 * Name	Type	Description
 * problem	Integer	시나리오 번호 (1 <= problem <= 2)
 * Example
 *
 * curl -X POST {BASE_URL}/start \
 *      -H 'X-Auth-Token: {X_AUTH_TOKEN}' \
 *      -H 'Content-Type: application/json' \
 *      -d '{
 *          "problem": 1
 *      }'
 * Response
 * Key
 *
 * Key	Type	Description
 * auth_key	String	Start API 를 통해 발급받은 key, 이후 문제 풀이에 진행되는 모든 API에 이 key를 사용
 * problem	Integer	선택한 시나리오 번호
 * time	Integer	현재 게임 서버에서의 시각 (0부터 시작)
 * Example
 *
 * {
 *   "auth_key": "1fd74321-d314-4885-b5ae-3e72126e75cc",
 *   "problem": 1,
 *   "time": 0
 * }
 * WaitingLine API
 * 현재 대기열에서 매칭을 대기 중인 유저들의 정보를 반환한다.
 *
 * Request
 * GET /waiting_line
 * Authorization: {auth_key}
 * Content-Type: application/json
 * Header
 *
 * Name	Description
 * Authorization	Start API 에서 발급받은 auth_key
 * Example
 *
 * curl -X GET {BASE_URL}/waiting_line \
 *      -H 'Authorization: {AUTH_KEY}' \
 *      -H 'Content-Type: application/json'
 * Response
 * Key
 *
 * Key	Type	Description
 * waiting_line	Array of user_id	매칭을 기다리고 있는 각 유저의 ID, 기다리기 시작한 시각(턴)의 배열
 * Element
 *
 * Key	Type	Description
 * id	Integer	매칭을 기다리고 있는 유저의 ID
 * from	Integer	매칭 대기를 시작한 시각(턴)
 * Example
 *
 * {
 *   "waiting_line": [
 *     { "id": 1, "from": 3 },
 *     { "id": 2, "from": 14 },
 *     ...
 *   ]
 * }
 * GameResult API
 * 이번 턴에 게임이 끝난 유저들의 게임 결과를 반환한다.
 *
 * Request
 * GET /game_result
 * Authorization: {auth_key}
 * Content-Type: application/json
 * Header
 *
 * Name	Description
 * Authorization	Start API 에서 발급받은 auth_key
 * Example
 *
 * curl -X GET {BASE_URL}/game_result \
 *      -H 'Authorization: {AUTH_KEY}' \
 *      -H 'Content-Type: application/json'
 * Response
 * Key
 *
 * Key	Type	Description
 * game_result	Array of result	게임을 진행한 두 유저의 아이디 쌍, 게임의 승패, 게임에 걸린 시간을 담은 배열
 * Element
 *
 * Key	Type	Description
 * win	Integer	게임에서 이긴 유저의 아이디
 * lose	Integer	게임에서 진 유저의 아이디
 * taken	Integer	게임을 하는 데 걸린 시간
 * Example
 *
 * {
 *   "game_result": [
 *     {"win": 10, "lose": 2, "taken": 7 },
 *     {"win": 7, "lose": 12, "taken": 33 },
 *     ...
 *   ]
 * }
 * UserInfo API
 * 모든 유저들의 현재 등급을 반환한다.
 *
 * Request
 * GET /user_info
 * Authorization: {auth_key}
 * Content-Type: application/json
 * Header
 *
 * Name	Description
 * Authorization	Start API 에서 발급받은 auth_key
 * Example
 *
 * curl -X GET {BASE_URL}/user_info \
 *      -H 'Authorization: {AUTH_KEY}' \
 *      -H 'Content-Type: application/json'
 * Response
 * Key
 *
 * Key	Type	Description
 * user_info	Array of user_info	모든 유저의 아이디, 등급을 담은 배열
 * Element
 *
 * Key	Type	Description
 * id	Integer	유저의 아이디
 * grade	Integer	현재 유저의 등급
 * Example
 *
 * {
 *   "user_info": [
 *     { "id": 1, "grade": 2100 },
 *     { "id": 13, "grade": 1501 },
 *     ...
 *   ]
 * }
 * Match API
 * 대기열에서 매칭 대기 중인 두 유저를 매칭하여 게임을 시작하도록 한다. 매칭할 유저의 쌍을 배열에 담아 서버에 전달하면 서버에서는 다음과 같은 일이 진행된다.
 *
 * 카카오 게임 서버의 상태가 in_progress 로 변경된다.
 * 대기열에서 매칭된 두 유저의 아이디를 확인하여 대기열에서 삭제한다. 대기열에 없는 유저를 지목한 경우, 해당 유저가 속해 있는 쌍은 매칭되지 않고 무시된다. 하나의 pair 안에 같은 유저가 두 번 이상 등장하거나, 두 명의 유저가 아닌 한 명 또는 세 명 이상의 유저가 주어지는 경우에도 해당 쌍은 매칭되지 않고 무시된다.
 * 서버 내부에 기록된 두 유저의 고유 실력을 확인하여, 승패 여부와 게임에 걸릴 시간(t)을 정한다.
 * Match 요청이 성공하면 카카오 게임 서버의 상태가 ready 로 변경되며 시간이 1분 진행된다(다음 턴으로 넘어간다).
 * 만약 중복된 유저 쌍이 pairs에 담길 경우, 처음 등장한 쌍을 제외한 다른 모든 쌍은 무시된다.
 *
 * Request
 * PUT /match
 * Authorization: {auth_key}
 * Content-Type: application/json
 * Header
 *
 * Name	Description
 * Authorization	Start API 에서 발급받은 auth_key
 * Parameter
 *
 * Name	Type	Description
 * pairs	Array of user_id pair	대기 중인 유저 쌍들을 매칭시키고 시간을 1분 진행시킨다.
 * Element
 *
 * Type	Description
 * Array of Integer	매칭하고자 하는 두 유저의 아이디를 담은 길이가 2인 정수 배열
 * Example
 *
 * curl -X PUT {BASE_URL}/match \
 *      -H 'Authorization: {AUTH_KEY}' \
 *          -H 'Content-Type: application/json' \
 *      -d '{
 *        "pairs": [[1, 2], [9, 7], [11, 49]]
 *      }'
 * Response
 * Key
 *
 * Key	Type	Description
 * status	String	현재 카카오 게임 서버의 상태
 * time	Integer	현재 시각(턴) (요청 시각에서 1분 경과한 시각)
 * Example
 *
 * {
 *   "status": "ready",
 *   "time": 312
 * }
 * ChangeGrade API
 * 여러 유저의 등급을 수정할 수 있다. 등급의 범위는 0 이상 9,999 이하 정수이다. 서버에 저장된 등급은 매칭에 영향을 주지 않는다.
 *
 * Request
 * PUT /change_grade
 * Authorization: {auth_key}
 * Content-Type: application/json
 * Header
 *
 * Name	Description
 * Authorization	Start API 에서 발급받은 auth_key
 * Parameter
 *
 * Name	Type	Description
 * commands	Array of Command	여러 유저의 grade 값을 수정한다.
 * Element
 *
 * Key	Type	Description
 * id	Integer	유저의 아이디
 * grade	Integer	수정된 유저의 등급
 * Example
 *
 * curl -X PUT {BASE_URL}/change_grade \
 *      -H 'Authorization: {AUTH_KEY}' \
 *          -H 'Content-Type: application/json' \
 *      -d '{
 *        "commands": [
 *          { "id": 1, "grade": 1900 }
 *          ...
 *        ]
 *      }'
 * Response
 * Key
 *
 * Key	Type	Description
 * status	String	현재 카카오 게임 서버의 상태
 * Example
 *
 * {
 *   "status": "ready"
 * }
 * Score API
 * 시뮬레이션이 끝난 뒤 정확성 점수와 효율성 점수, 총점을 반환한다.
 *
 * Request
 * GET /score
 * Authorization: {auth_key}
 * Content-Type: application/json
 * Header
 *
 * Name	Description
 * Authorization	Start API 에서 발급받은 auth_key
 * Example
 *
 * curl -X GET {BASE_URL}/score \
 *      -H 'Authorization: {AUTH_KEY}' \
 *      -H 'Content-Type: application/json'
 * Response
 * Key
 *
 * Key	Type	Description
 * status	String	서버 상태
 * efficiency_score	Float	효율성 점수
 * accuracy_score1	Float	정확성 점수 1
 * accuracy_score2	Float	정확성 점수 2
 * score	Float	총점
 * Example
 *
 * {
 *   "status": "finished",
 *   "efficiency_score": 1.0,
 *   "accuracy_score1": 0.0,
 *   "accuracy_score2": 32.62,
 *   "score": 39.944
 */
public class GameMatchingController {
}
