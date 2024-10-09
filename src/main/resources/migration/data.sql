SET NAMES utf8mb4;

-- 미션
INSERT INTO mission (id, name, created_at, is_active)
VALUES (1, 'Authentication Shots', '2023-09-04 20:21:08', 1);
INSERT INTO mission (id, name, created_at, is_active)
VALUES (2, 'object recognition', '2023-08-25 16:51:30', 1);
INSERT INTO mission (id, name, created_at, is_active)
VALUES (3, 'expression recognition', '2023-09-09 22:29:21', 1);

-- 미션 오브젝트
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (1, 'person', '사람', '👤', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (2, 'bicycle', '자전거', '🚲', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (3, 'car', '자동차', '🚗', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (4, 'motorcycle', '오토바이', '🏍️', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (5, 'airplane', '비행기', '✈️', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (6, 'bus', '버스', '🚌', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (7, 'train', '기차', '🚃', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (8, 'boat', '보트', '🛥️', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (9, 'traffic light', '교통 신호등', '🚥', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (10, 'bird', '새', '🦜', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (11, 'cat', '고양이', '🐈', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (12, 'dog', '개', '🐕', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (13, 'horse', '말', '🐴', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (14, 'backpack', '책가방', '🎒', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (15, 'umbrella', '우산', '☂️', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (16, 'handbag', '핸드백', '👜', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (17, 'tie', '넥타이', '👔', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (18, 'suitcase', '캐리어', '🧳', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (19, 'frisbee', '프리스비', '🥏', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (20, 'skis', '스키', '🎿', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (21, 'surfboard', '서핑보드', '🏄🏻‍♀️', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (22, 'snowboard', '스노보드', '🏂', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (23, 'ball', '스포츠 볼', '⚽️', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (24, 'baseball bat', '야구 방망이', '🏏', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (25, 'baseball glove', '야구 글러브', '⚾', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (26, 'skateboard', '스케이트보드', '🛹', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (27, 'tennis racket', '테니스 라켓', '🏸', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (28, 'bottle', '병', '🍾', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (29, 'wine glass', '와인 글라스', '🍷', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (30, 'cup', '컵', '☕️', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (31, 'fork', '포크', '🍴', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (32, 'knife', '나이프', '🔪', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (33, 'spoon', '숟가락', '🥄', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (34, 'bowl', '그릇', '🥣', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (35, 'tableware', '식기류', '🥛', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (36, 'kitchen utensil', '주방용품', '🍳', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (37, 'banana', '바나나', '🍌', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (38, 'apple', '사과', '🍎', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (39, 'sandwich', '샌드위치', '🥪', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (40, 'orange', '오렌지', '🍊', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (41, 'broccoli', '브로콜리', '🥦', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (42, 'carrot', '당근', '🥕', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (43, 'pizza', '피자', '🍕', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (44, 'donut', '도넛', '🍩', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (45, 'cake', '케이크', '🍰', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (46, 'chair', '의자', '🪑', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (47, 'desk', '책상', '📚', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (48, 'couch', '소파', '🛋️', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (49, 'pen', '필기구', '✒', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (50, 'potted plant', '화분', '🏺', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (51, 'bed', '침대', '🛏️', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (52, 'television', '텔레비전', '📺', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (53, 'laptop', '노트북', '💻', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (54, 'mouse', '마우스', '🖱️', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (55, 'keyboard', '키보드', '⌨️', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (56, 'cell phone', '휴대폰', '📱', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (57, 'oven', '오븐', '🥐', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (58, 'toaster', '토스터', '🍞', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (59, 'refrigerator', '냉장고', '🧊', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (60, 'book', '책', '📚', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (61, 'clock', '시계', '⏰', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (62, 'vase', '꽃병', '🏺', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (63, 'scissors', '가위', '✂️', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (64, 'teddy bear', '테디 베어', '🧸', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (65, 'toothbrush', '칫솔', '💨', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (66, 'joy', '기쁜 표정', '😄', 1, 3);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (67, 'angry', '화난 표정', '😠', 1, 3);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (68, 'sorrow', '슬픈 표정', '😢', 1, 3);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id)
VALUES (69, 'surprise', '놀란 표정', '😲', 1, 3);


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
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('안녕, 내가 니 선임이야~', 'DEFAULT', 1);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('신입 어서오공~', 'NONE', 1);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('야야 그래가지구\\n언제 레벨 업할래!', 'NONE', 1);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('밥은 꼭 챙겨 먹어랑~', 'NONE', 1);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('미션 10회 실시!', 'NONE', 1);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('삐약... 졸리당', 'NONE', 1);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('병아리가 가장 잘 먹는 약은?\\n......삐약!', 'NONE', 1);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('드디어 막내 탈출이당!', 'NONE', 1);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('오~ 웬일?', 'NONE', 1);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('너 생각보다 잘하는뎅?', 'NONE', 1);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('막내야 물 좀 떠와랑~', 'NONE', 1);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('야야 참아\\n그래도 내가 니 선배양~', 'NONE', 1);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('야야 밥은 내가 쏜당~', 'NONE', 1);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('나랑 같이 우주탐험 하지 않을래?', 'DEFAULT', 2);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('우주는 엄청 넓고 어둡구나…\\n저 너머에는 뭐가 있을까?', 'NEW_MOON', 2);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('달이 꼭 노릇노릇한 빵 같은걸!\\n우리도 든든하게 먹고 힘내자~', 'HALF_MOON', 2);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('오늘은 달이 밝네~\\n왠지 미션을 잘 끝낼 수\\n있을 것 같아!', 'FULL_MOON', 2);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('반짝반짝 작은별~\\n보석처럼 예쁘다…', 'NONE', 2);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('저기 은하수가 보여!\\n가서 헤엄치고 싶다~', 'NONE', 2);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('내 우주선?\\n직접 만든거야! 멋있지?', 'NONE', 2);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('안녕!\\n친구와 미션을 함께하니\\n더 의욕이 생긴다~', 'NONE', 2);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('우주에서 별을 찾는 미션이\\n있는데, 같이 할래?', 'NONE', 2);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('우주 모험은 언제나 즐거워!', 'NONE', 2);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('사랑을 나누어 드릴게요...', 'DEFAULT', 3);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('누군가에게 내 마음을 전해줄 수 있을까?\\n장미도, 케이크도 다 준비했어요!', 'NONE', 3);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('저의 볼이 분홍색이 될 때까지 당신을 생각했답니다.', 'NONE', 3);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('이 꽃들과 달콤한 케이크,\\n누구에게 줄까 하고 고민 중이에요.', 'NONE', 3);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('눈이 반짝반짝 빛나요,\\n누군가에게 이 딸기 케이크를 나눠줄 생각을 하니까요!', 'NONE', 3);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('사랑을 전하는 걸음걸음이 춤추듯 가벼워요,\\n꽃다발과 케이크를 들고서요.', 'NONE', 3);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('수줍고 달콤한,\\n이 작은 토끼 친구가 마음을 담은 선물을 가지고 있어요.', 'NONE', 3);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('저는 사랑을 전하는 메신저예요.', 'NONE', 3);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('제 발걸음에 봄이 왔어요,\\n평범한 하루를 사랑의 축제로 바꾸기 위한 임무를 가지고요.', 'NONE', 3);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('이렇게 사랑스러운 토끼 앞에서는\\n꽃들도 수줍어할 거예요.', 'NONE', 3);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('내 크고 분홍색 마음은\\n케이크만큼이나 사랑으로 가득 차 있어요.', 'NONE', 3);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('당신은 간단한 것에서\\n기쁨을 찾는 법을 아세요?', 'NONE', 3);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('사랑스러운 고민에 빠져,\\n준비한 마음을 정말 줘도 될지 생각 중이에요.', 'NONE', 3);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('꽃다발의 리본을 예쁘게 묶었어요,\\n친구들에게 전할 사랑처럼요.', 'NONE', 3);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('먹는게 세상에서 제일 좋아~', 'DEFAULT', 4);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('실은 나, 얼마나 배가 고픈지로\\n지금 시간을 알 수 있어~\\n꼬르륵~ 현재 %d시 %d분 이야,,', 'TIME', 4);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('나 지금 대나무 냄새 느껴졌어,,\\n혹시 숨기고있는거 아니지!\\n같이 먹자구~~ ', 'NONE', 4);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('요즘 매일같이 닭가슴살을 먹고 있는데에.. 이제 한동안은 쳐다도 보기 싫어! ', 'NONE', 4);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('저녁밥 먹고 디저트로 뭘 먹을지 정하질 못하겠어~ 앗, 그래! 둘 다 먹으면 되겠다아!', 'NONE', 4);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('내가 좋아하는 대나무에 둘러싸여서 느긋하게 시간을 보내고 싶다아～', 'NONE', 4);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('한 입 정도는 괜찮잖아～', 'NONE', 4);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('나 오늘부터 다이어트 시작이다… ', 'NONE', 4);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('이번 여름,,, 달라진 모습 기대해줘', 'NONE', 4);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('냉장고안에 있는 초콜릿,, 내꺼다 건들 ㄴ', 'NONE', 4);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('오늘 저녁은 %s 어때~~???', 'FOOD', 4);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('내일 점심으론\\n가볍게 %s 먹을까??', 'FOOD', 4);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('치느님 영접할때가 되었어,,,\\n치느님이 날 부르고 있어,,!', 'NONE', 4);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('맛없는걸로 배 채우는건\\n바보같은 짓이야,,,\\n한끼 한끼가 소중하다구,,', 'NONE', 4);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('대나무는 윗 부분보다는,,\\n아랫 부분이야,,,', 'NONE', 4);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('우웩,,\\n나뭇잎은,, 먹기싫어요,,,', 'NONE', 4);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('나무 올라타는건 힘들어,,,\\n하지만 운동은 해야하니까', 'NONE', 4);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('요즘 나 김밥 안에 오이는 먹을수 있짜나 이제 나도 어른일지도,,,', 'NONE', 4);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('커피는 너무 써,,, 초코라떼 먹어야지~~~', 'NONE', 4);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('저와 비오는 날을 함께 해요!', 'DEFAULT', 5);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('비가 오는 날은 집에서 쉬는 게 최고죠. 우리 함께 뒹굴뒹굴해요!', 'NONE', 5);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('젖은 발바닥, 신경 쓰지 마세요. 저도 비 오는 날엔 자주 그래요!', 'NONE', 5);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('비가 와서 산책이 걱정되나요? 제가 용기를 드릴게요!', 'NONE', 5);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('우리 같이 빗소리를 들으며 잠시 휴식을 취해요. 어떨까요?', 'NONE', 5);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('비가 오면 세상이 깨끗해지는 것 같아요. 마음도 같이 씻겨나가는 기분이죠.', 'NONE', 5);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('빗방울 소리 듣는 걸 좋아하세요? 저도 좋아해요, 마음이 편해져요.', 'NONE', 5);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('비 내리는 창밖을 같이 바라볼까요? 저에게는 최고의 시간이에요.', 'NONE', 5);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('비가 오면, 저는 항상 창가에 앉아 빗방울을 세곤 해요. 당신도 같이 해보실래요?', 'NONE', 5);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('비가 내리는 소리, 들어보셨나요? 저는 그 소리가 좋아요. 마음을 진정시켜주거든요.', 'NONE', 5);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('비가 오는 날은 집 안에서 코를 박고 자는 것도 좋지만, 가끔은 밖의 세상도 탐험해야죠!', 'NONE', 5);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('널 철학의 세계로 인도하지...', 'DEFAULT', 6);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('삶이 있는 한 희망은 있다.', 'NONE', 6);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('언제나 현재에 집중할수\\n있다면 행복할것이다.', 'NONE', 6);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('신은 용기 있는 자를\\n결코 버리지 않는다.', 'NONE', 6);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('행복한 삶을 살기 위해\\n필요한 것은 거의 없다.', 'NONE', 6);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('절대 어제를 후회하지 마라.\\n인생은 오늘의 나 안에 있고\\n내일은 스스로 만드는 것이다', 'NONE', 6);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('한번의 실패와 영원한 실패를\\n혼동하지 마라.', 'NONE', 6);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('피할수 없으면 즐겨라.', 'NONE', 6);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('오랫동안 꿈을 그리는 사람은\\n마침내 그 꿈을 닮아 간다.', 'NONE', 6);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('평생 살 것처럼 꿈을 꾸어라.\\n그리고 내일 죽을 것처럼 오늘을 살아라.', 'NONE', 6);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('고통이 남기고 간 뒤를 보라!\\n고난이 지나면 반드시 기쁨이 스며든다.', 'NONE', 6);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('돈이란 바닷물과도 같다.\\n그것은 마시면 마실수록 목이 말라진다.', 'NONE', 6);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('사막이 아름다운 것은\\n어딘가에 샘이 숨겨져 있기 때문이다.', 'NONE', 6);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('내 비장의 무기는 아직 손안에 있다.\\n그것은 희망이다.', 'NONE', 6);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('겨울이 오면 봄이 멀지 않으리.', 'NONE', 6);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('인생을 다시 산다면 다음번에는\\n더 많은 실수를 저지르리라.', 'NONE', 6);
INSERT INTO avatar_speech (speech, `condition`, avatar_id)
VALUES ('인생에 뜻을 세우는데 있어 늦은 때라곤 없다.', 'NONE', 6);


-- 앱 버전 히스토리
INSERT INTO app_version_history (version, app_store_url, created_at)
VALUES ('1.0.0', 'https://apps.apple.com/kr/app/togetup/id6477523543', '2024-05-02 13:28:03');
INSERT INTO app_version_history (version, app_store_url, created_at)
VALUES ('1.0.1', 'https://apps.apple.com/kr/app/togetup/id6477523543', '2024-05-02 13:28:03');
