-- table : user_avatar_purchase_log 삭제
DROP TABLE user_avatar_purchase_log;

-- table : user_avatar 현재 아바타 정보를 위한 컬럼 추가
alter table user_avatar
    add is_active tinyint(1) default 0 not null after id;

alter table user_avatar
    add created_at TIMESTAMP default CURRENT_TIMESTAMP not null after is_active;

-- table : avatar 이미지 링크 삭제
alter table avatar
drop column avatar_img_link;