INSERT INTO room (id, name, intro, topic, invitation_code, created_at, updated_at, is_deleted) VALUES (1, 'test room1', 'test room 1 입니다', 'kdfja39r83', 'dfjasdlf', '2023-09-19 11:01:11', '2023-09-19 11:02:24', 0);
INSERT INTO room (id, name, intro, topic, invitation_code, created_at, updated_at, is_deleted) VALUES (2, 'test room2', 'test room 2 입니다', 'adsfadf', 'asdfade', '2023-09-19 11:03:15', '2023-09-19 11:03:15', 0);
INSERT INTO room (id, name, intro, topic, invitation_code, created_at, updated_at, is_deleted) VALUES (3, 'test room3', 'test room 3입니다', 'egaaegg', 'egageg', '2023-09-19 11:03:45', '2023-09-19 11:03:48', 0);


INSERT INTO alarm (id, name, icon, snooze_interval, snooze_cnt, alarm_time, monday, tuesday, wednesday, thursday, friday, saturday, sunday, is_snooze_activated, is_vibrate, is_activated, user_id, mission_id, mission_object_id, room_id) VALUES (1, 'room 1알람', '⏰', 5, 3, '11:10:23', 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1);
INSERT INTO alarm (id, name, icon, snooze_interval, snooze_cnt, alarm_time, monday, tuesday, wednesday, thursday, friday, saturday, sunday, is_snooze_activated, is_vibrate, is_activated, user_id, mission_id, mission_object_id, room_id) VALUES (2, 'room 2알람', '⏰', 5, 3, '12:10:23', 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 2);
INSERT INTO alarm (id, name, icon, snooze_interval, snooze_cnt, alarm_time, monday, tuesday, wednesday, thursday, friday, saturday, sunday, is_snooze_activated, is_vibrate, is_activated, user_id, mission_id, mission_object_id, room_id) VALUES (3, 'room 3알람', '⏰', 5, 3, '10:10:23', 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 3);


INSERT INTO room_user (id, created_at, room_id, user_id) VALUES (1, '2023-09-16 11:08:38', 1, 1);
INSERT INTO room_user (id, created_at, room_id, user_id) VALUES (2, '2023-09-17 11:08:38', 1, 2);
INSERT INTO room_user (id, created_at, room_id, user_id) VALUES (3, '2023-09-18 11:08:38', 2, 1);
INSERT INTO room_user (id, created_at, room_id, user_id) VALUES (4, '2023-09-19 11:08:38', 2, 2);
INSERT INTO room_user (id, created_at, room_id, user_id) VALUES (5, '2023-09-19 11:08:38', 3, 1);
