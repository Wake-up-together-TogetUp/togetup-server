create table avatar_speech
(
    id          int unsigned auto_increment primary key,
    speech      varchar(60)                   not null,
    `condition` varchar(20) default 'DEFAULT' not null,
    avatar_id   int unsigned null,
    constraint avatar_speech_avatar_id_fk
        foreign key (avatar_id) references avatar (id)
);