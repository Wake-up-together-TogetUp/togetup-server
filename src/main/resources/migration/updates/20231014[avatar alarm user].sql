-- avatar에 unlock_level(해금 레벨) 컬럼 추가, phase 컬럼 삭제
alter table avatar
    add unlock_level int unsigned not null after price;

alter table avatar
drop column phase;

-- user에 level, experience, point 컬럼 추가
alter table user
    add level int unsigned default 1 not null after agree_push;

alter table user
    add experience int unsigned default 0 not null after level;

alter table user
    add point int unsigned default 0 not null after experience;

-- mission_log에 mission_id 삭제
alter table mission_log
drop foreign key mission_log_ibfk_3;

alter table mission_log
drop column mission_id;

-- alarm에 user_id, mission_id nullable 속성 수정
alter table alarm
    modify user_id int unsigned null;

alter table alarm
    modify mission_id int unsigned not null;