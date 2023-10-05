
-- 미션
INSERT INTO mission (id, name, created_at, is_active) VALUES (1, 'Authentication Shots', '2023-09-04 20:21:08', 1);
INSERT INTO mission (id, name, created_at, is_active) VALUES (2, 'object recognition', '2023-08-25 16:51:30', 1);
INSERT INTO mission (id, name, created_at, is_active) VALUES (3, 'expression recognition', '2023-09-09 22:29:21', 1);

-- 미션 오브젝트
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (1, 'person', '사람', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (2, 'bicycle', '자전거', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (3, 'car', '자동차', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (4, 'motorcycle', '오토바이', '?️ ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (5, 'airplane', '비행기', '✈️ ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (6, 'bus', '버스', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (7, 'train', '기차', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (8, 'boat', '보트', '?️ ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (9, 'traffic light', '교통 신호등', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (10, 'bird', '새', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (11, 'cat', '고양이', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (12, 'dog', '개', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (13, 'horse', '말', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (14, 'backpack', '책가방', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (15, 'umbrella', '우산', '☂️ ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (16, 'handbag', '핸드백', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (17, 'tie', '넥타이', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (18, 'suitcase', '캐리어', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (19, 'frisbee', '프리스비', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (20, 'skis', '스키', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (21, 'surfboard', '서핑보드', '??‍♀️', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (22, 'snowboard', '스노보드', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (23, 'sports ball', '스포츠 볼', '⚽️', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (24, 'baseball bat', '야구 방망이', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (25, 'baseball glove', '야구 글러브', '⚾', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (26, 'skateboard', '스케이트보드', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (27, 'tennis racket', '테니스 라켓', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (28, 'bottle', '병', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (29, 'wine glass', '와인 글라스', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (30, 'cup', '컵', '☕️', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (31, 'fork', '포크', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (32, 'knife', '나이프', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (33, 'spoon', '숟가락', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (34, 'bowl', '그릇', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (35, 'banana', '바나나', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (36, 'apple', '사과', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (37, 'sandwich', '샌드위치', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (38, 'orange', '오렌지', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (39, 'broccoli', '브로콜리', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (40, 'carrot', '당근', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (41, 'hot dog', '핫도그', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (42, 'pizza', '피자', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (43, 'donut', '도넛', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (44, 'cake', '케이크', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (45, 'chair', '의자', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (46, 'couch', '소파', '?️', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (47, 'potted plant', '화분', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (48, 'bed', '침대', '?️ ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (49, 'tv', '텔레비전', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (50, 'laptop', '노트북', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (51, 'mouse', '마우스', '?️ ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (52, 'keyboard', '키보드', '⌨️', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (53, 'cell phone', '휴대폰', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (54, 'oven', '오븐', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (55, 'toaster', '토스터', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (56, 'refrigerator', '냉장고', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (57, 'book', '책', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (58, 'clock', '시계', '⏰', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (59, 'vase', '꽃병', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (60, 'scissors', '가위', '✂️ ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (61, 'teddy bear', '테디 베어', '? ', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (62, 'hair drier', '헤어 드라이어', '?‍♀️', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (63, 'toothbrush', '칫솔', '?', 1, 2);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (64, 'angry', '화난 표정', '?', 1, 3);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (65, 'disgust', '역겨워하는 표정', '?', 1, 3);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (66, 'fear', '분노 표정', '?', 1, 3);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (67, 'laugh', '웃는 표정', '?', 1, 3);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (68, 'neutral', '평온한 표정', '?', 1, 3);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (69, 'sad', '슬픈 표정', '?', 1, 3);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (70, 'suprise', '놀란 표정', '?', 1, 3);
INSERT INTO mission_object (id, name, kr, icon, is_active, mission_id) VALUES (71, 'smile', '미소짓는 표정', '?', 1, 3);

-- user email 추가
alter table user
    add email varchar(30) null;

-- room_user에 방장 여부 추가
alter table room_user
    add is_host tinyint(1) default 0 null;

-- room_user에 푸쉬 알림 여부 추가
alter table room_user
    add agree_push tinyint(1) default 1 not null;

