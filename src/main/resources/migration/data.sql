-- 미션
INSERT INTO mission (id, name, created_at, is_active)
VALUES (1, 'Authentication Shots', '2023-09-04 20:21:08', 1);
INSERT INTO mission (id, name, created_at, is_active)
VALUES (2, 'object recognition', '2023-08-25 16:51:30', 1);
INSERT INTO mission (id, name, created_at, is_active)
VALUES (3, 'expression recognition', '2023-09-09 22:29:21', 1);

-- 미션 오브젝트
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (1, 'person', '사람', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (2, 'bicycle', '자전거', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (3, 'car', '자동차', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (4, 'motorcycle', '오토바이', '?️ ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (5, 'airplane', '비행기', '✈️ ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (6, 'bus', '버스', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (7, 'train', '기차', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (8, 'boat', '보트', '?️ ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (9, 'traffic light', '교통 신호등', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (10, 'bird', '새', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (11, 'cat', '고양이', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (12, 'dog', '개', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (13, 'horse', '말', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (14, 'backpack', '책가방', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (15, 'umbrella', '우산', '☂️ ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (16, 'handbag', '핸드백', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (17, 'tie', '넥타이', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (18, 'suitcase', '캐리어', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (19, 'frisbee', '프리스비', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (20, 'skis', '스키', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (21, 'surfboard', '서핑보드', '??‍♀️', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (22, 'snowboard', '스노보드', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (23, 'sports ball', '스포츠 볼', '⚽️', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (24, 'baseball bat', '야구 방망이', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (25, 'baseball glove', '야구 글러브', '⚾', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (26, 'skateboard', '스케이트보드', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (27, 'tennis racket', '테니스 라켓', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (28, 'bottle', '병', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (29, 'wine glass', '와인 글라스', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (30, 'cup', '컵', '☕️', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (31, 'fork', '포크', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (32, 'knife', '나이프', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (33, 'spoon', '숟가락', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (34, 'bowl', '그릇', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (35, 'banana', '바나나', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (36, 'apple', '사과', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (37, 'sandwich', '샌드위치', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (38, 'orange', '오렌지', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (39, 'broccoli', '브로콜리', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (40, 'carrot', '당근', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (41, 'hot dog', '핫도그', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (42, 'pizza', '피자', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (43, 'donut', '도넛', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (44, 'cake', '케이크', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (45, 'chair', '의자', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (46, 'couch', '소파', '?️', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (47, 'potted plant', '화분', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (48, 'bed', '침대', '?️ ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (49, 'tv', '텔레비전', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (50, 'laptop', '노트북', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (51, 'mouse', '마우스', '?️ ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (52, 'keyboard', '키보드', '⌨️', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (53, 'cell phone', '휴대폰', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (54, 'oven', '오븐', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (55, 'toaster', '토스터', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (56, 'refrigerator', '냉장고', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (57, 'book', '책', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (58, 'clock', '시계', '⏰', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (59, 'vase', '꽃병', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (60, 'scissors', '가위', '✂️ ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (61, 'teddy bear', '테디 베어', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (62, 'hair drier', '헤어 드라이어', '?‍♀️', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (63, 'toothbrush', '칫솔', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (64, 'joy', '기쁜 표정', '?', 1, 3);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (65, 'angry', '화난 표정', '?', 1, 3);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (66, 'sorrow', '슬픈 표정', '?', 1, 3);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (67, 'surprise', '놀란 표정', '?', 1, 3);


-- 아바타
INSERT INTO avatar (id, theme, theme_kr, unlock_level, created_at)
VALUES (1, 'SENIOR_CHICK', '선임 병아리', 1, '2023-10-07 20:35:51');
INSERT INTO avatar (id, theme, theme_kr, unlock_level, created_at)
VALUES (2, 'ASTRONAUT_BEAR', '우주비행사 곰돌이', 15, '2023-10-21 03:24:06');
INSERT INTO avatar (id, theme, theme_kr, unlock_level, created_at)
VALUES (3, 'LOVELY_BUNNY', '러블리 토끼', 30, '2023-10-21 03:24:06');
INSERT INTO avatar (id, theme, theme_kr, unlock_level, created_at)
VALUES (4, 'GLUTTON_PANDA', '먹보 판다', 45, '2024-02-19 23:20:00');
INSERT INTO avatar (id, theme, theme_kr, unlock_level, created_at)
VALUES (5, 'RAINY_DAY_PUPPY', '비오는 날 강아지', 60, '2024-02-19 23:21:09');
INSERT INTO avatar (id, theme, theme_kr, unlock_level, created_at)
VALUES (6, 'PHILOSOPHER_RACCOON', '철학자 너구리', 75, '2024-02-19 23:22:52');


-- 아바타 대사
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (1, '신입 어서오공~', 'DEFAULT', 1);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (2, '야야 그래가지구\\n언제 레벨 업할래!', 'DEFAULT', 1);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (3, '밥은 꼭 챙겨 먹어랑~', 'DEFAULT', 1);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (4, '미션 10회 실시!', 'DEFAULT', 1);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (5, '삐약... 졸리당', 'DEFAULT', 1);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (6, '병아리가 가장 잘 먹는 약은?\\n......삐약!', 'DEFAULT', 1);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (7, '드디어 막내 탈출이당!', 'DEFAULT', 1);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (8, '오~ 웬일?', 'DEFAULT', 1);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (9, '너 생각보다 잘하는뎅?', 'DEFAULT', 1);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (10, '막내야 물 좀 떠와랑~', 'DEFAULT', 1);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (11, '야야 참아\\n그래도 내가 니 선배양~', 'DEFAULT', 1);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (12, '야야 밥은 내가 쏜당~', 'DEFAULT', 1);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (13, '우주는 엄청 넓고 어둡구나…\\n저 너머에는 뭐가 있을까?', 'DEFAULT', 2);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (14, '달이 꼭 노릇노릇한 빵 같은걸!\\n우리도 든든하게 먹고 힘내자~', 'DEFAULT', 2);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (15, '오늘은 달이 밝네~\\n왠지 미션을 잘 끝낼 수 있을 것 같아!', 'DEFAULT', 2);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (16, '모든 미션 완료!\\n다음에는 뭘 할지 기대되는걸?', 'DEFAULT', 2);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (17, '반짝반짝 작은별~\\n보석처럼 예쁘다…', 'DEFAULT', 2);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (18, '저기 은하수가 보여!\\n가서 헤엄치고 싶다~', 'DEFAULT', 2);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (19, '내 우주선?\\n직접 만든거야! 멋있지?', 'DEFAULT', 2);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (20, '안녕!\\n친구와 미션을 함께하니 더 의욕이 생긴다~', 'DEFAULT', 2);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (21, '우주에서 별을 찾는 미션이 있는데,\\n같이 할래?', 'DEFAULT', 2);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (22, '우주 모험은 언제나 즐거워!', 'DEFAULT', 2);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (30, '누군가에게 내 마음을 전해줄 수 있을까?\\n장미도, 케이크도 다 준비했어요!', 'DEFAULT', 3);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (31, '저의 볼이 분홍색이 될 때까지 당신을 생각했답니다.', 'DEFAULT', 3);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (32, '이 꽃들과 달콤한 케이크,\\n누구에게 줄까 하고 고민 중이에요.', 'DEFAULT', 3);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (33, '눈이 반짝반짝 빛나요,\\n누군가에게 이 딸기 케이크를 나눠줄 생각을 하니까요!', 'DEFAULT', 3);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (34, '사랑을 전하는 걸음걸음이 춤추듯 가벼워요,\\n꽃다발과 케이크를 들고서요.', 'DEFAULT', 3);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (35, '수줍고 달콤한,\\n이 작은 토끼 친구가 마음을 담은 선물을 가지고 있어요.', 'DEFAULT', 3);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (36, '저는 사랑을 전하는 메신저예요.', 'DEFAULT', 3);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (37, '제 발걸음에 봄이 왔어요,\\n평범한 하루를 사랑의 축제로 바꾸기 위한 임무를 가지고요.', 'DEFAULT', 3);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (38, '이렇게 사랑스러운 토끼 앞에서는\\n꽃들도 수줍어할 거예요.', 'DEFAULT', 3);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (39, '내 크고 분홍색 마음은\\n케이크만큼이나 사랑으로 가득 차 있어요.', 'DEFAULT', 3);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (40, '당신은 간단한 것에서\\n기쁨을 찾는 법을 아세요?', 'DEFAULT', 3);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (41, '사랑스러운 고민에 빠져,\\n준비한 마음을 정말 줘도 될지 생각 중이에요.', 'DEFAULT', 3);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (42, '꽃다발의 리본을 예쁘게 묶었어요,\\n친구들에게 전할 사랑처럼요.', 'DEFAULT', 3);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (43, '실은 나, 얼마나 배가 고픈지로 \\n지금 몇 시인지 알 수 있어~\\n꼬르륵~ 현재 %d시야,,', 'DEFAULT', 4);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (44, '나 지금 대나무 냄새 느껴졌어,,\\n혹시 숨기고있는거 아니지!\\n같이 먹자구~~ ', 'DEFAULT', 4);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (45, '요즘 매일같이 닭가슴살을 먹고 있는데에.. 이제 한동안은 쳐다도 보기 싫어! ', 'DEFAULT', 4);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (46, '저녁밥 먹고 디저트로 뭘 먹을지 정하질 못하겠어~ 앗, 그래! 둘 다 먹으면 되겠다아!', 'DEFAULT', 4);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (47, '내가 좋아하는 대나무에 둘러싸여서 느긋하게 시간을 보내고 싶다아～', 'DEFAULT', 4);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (48, '한 입 정도는 괜찮잖아～', 'DEFAULT', 4);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (49, '나 오늘부터 다이어트 시작이다… ', 'DEFAULT', 4);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (50, '이번 여름,,, 달라진 모습 기대해줘', 'DEFAULT', 4);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (51, '냉장고안에 있는 초콜릿,, 내꺼다 건들 ㄴ', 'DEFAULT', 4);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (52, '오늘 저녁은 %s 어때~~???', 'DEFAULT', 4);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (53, '점심으로 가볍게 샌드위치먹을까??', 'DEFAULT', 4);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (54, '치느님 영접할때가 되었어,, 치느님이 날 부르고 있어,,,,', 'DEFAULT', 4);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (55, '맛없는걸로 배 채우는건 바보같은 짓이야,,, 한끼 한끼가 소중하다구,,', 'DEFAULT', 4);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (56, '대나무는 윗 부분보다는,, 아랫 부분이야,,,', 'DEFAULT', 4);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (57, '우웩,, 나뭇잎은,, 먹기싫어요,,,', 'DEFAULT', 4);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (58, '나무 올라타는건 힘들어,,, 하지만 운동은 해야하니까', 'DEFAULT', 4);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (59, '요즘 나 김밥 안에 오이는 먹을수 있짜나 이제 나도 어른일지도,,,', 'DEFAULT', 4);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (60, '커피는 너무 써,,, 초코라떼 먹어야지~~~', 'DEFAULT', 4);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (61, '비가 오는 날은 집에서 쉬는 게 최고죠. 우리 함께 뒹굴뒹굴해요!', 'DEFAULT', 5);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (62, '젖은 발바닥, 신경 쓰지 마세요. 저도 비 오는 날엔 자주 그래요!', 'DEFAULT', 5);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (63, '비가 와서 산책이 걱정되나요? 제가 용기를 드릴게요!', 'DEFAULT', 5);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (64, '우리 같이 빗소리를 들으며 잠시 휴식을 취해요. 어떨까요?', 'DEFAULT', 5);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (65, '비가 오면 세상이 깨끗해지는 것 같아요. 마음도 같이 씻겨나가는 기분이죠.', 'DEFAULT', 5);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (66, '빗방울 소리 듣는 걸 좋아하세요? 저도 좋아해요, 마음이 편해져요.', 'DEFAULT', 5);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (67, '비 내리는 창밖을 같이 바라볼까요? 저에게는 최고의 시간이에요.', 'DEFAULT', 5);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (68, '비가 오면, 저는 항상 창가에 앉아 빗방울을 세곤 해요. 당신도 같이 해보실래요?', 'DEFAULT', 5);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (69, '비가 내리는 소리, 들어보셨나요? 저는 그 소리가 좋아요. 마음을 진정시켜주거든요.', 'DEFAULT', 5);
INSERT INTO avatar_speech (id, speech, `condition`, avatar_id) VALUES (70, '비가 오는 날은 집 안에서 코를 박고 자는 것도 좋지만, 가끔은 밖의 세상도 탐험해야죠!', 'DEFAULT', 5);

-- 앱 버전 히스토리
INSERT INTO app_version_history (version, app_store_url, created_at) VALUES ('1.0.0', '미정', '2024-05-02 13:28:03');