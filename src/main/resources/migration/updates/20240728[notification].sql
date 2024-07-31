alter table notification
    add data_map text null;

alter table notification
drop foreign key notification_ibfk_1;

alter table notification
drop column room_id;

alter table notification
drop column fcm_token_id;

alter table notification
    add user_id int unsigned not null;

alter table notification
    add is_read boolean null;

alter table notification
    change content body varchar(30) null;

alter table notification
    alter column is_read set default 0;

alter table notification
    modify is_read tinyint(1) default 0 not null;







