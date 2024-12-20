create table if not exists app_version_history
(
    version       varchar(10)                         not null
    primary key,
    app_store_url varchar(500)                        not null,
    created_at    timestamp default CURRENT_TIMESTAMP not null
    );

create table if not exists avatar
(
    id           int unsigned auto_increment
    primary key,
    theme        varchar(20)                         not null,
    theme_kr     varchar(20)                         not null,
    unlock_level int unsigned                        not null,
    created_at   timestamp default CURRENT_TIMESTAMP not null
    );

create table if not exists avatar_speech
(
    id          int unsigned auto_increment
    primary key,
    speech      varchar(60)                not null,
    `condition` varchar(20) default 'NONE' not null,
    avatar_id   int unsigned               null,
    constraint avatar_speech_avatar_id_fk
    foreign key (avatar_id) references avatar (id)
    );

create table if not exists mission
(
    id         int unsigned auto_increment
    primary key,
    name       varchar(30)                          not null,
    created_at timestamp  default CURRENT_TIMESTAMP not null,
    is_active  tinyint(1) default 1                 not null
    );

create table if not exists mission_object
(
    id         int unsigned auto_increment
    primary key,
    name       varchar(20)          not null,
    kr         varchar(20)          not null,
    icon       varchar(30)          not null,
    is_active  tinyint(1) default 1 not null,
    mission_id int unsigned         not null,
    constraint mission_object_mission_id_fk
    foreign key (mission_id) references mission (id)
    );

create table if not exists notification
(
    id         int unsigned auto_increment
    primary key,
    title      varchar(20)                          null,
    body       varchar(30)                          null,
    created_at timestamp  default CURRENT_TIMESTAMP not null,
    data_map   text                                 null,
    user_id    int unsigned                         not null,
    is_read    tinyint(1) default 0                 not null
    );

create table if not exists room
(
    id              int unsigned auto_increment
    primary key,
    name            varchar(10)                          not null,
    intro           varchar(30)                          null,
    topic           varchar(100)                         null,
    invitation_code varchar(10)                          null,
    created_at      timestamp  default CURRENT_TIMESTAMP not null,
    updated_at      timestamp  default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    is_deleted      tinyint(1) default 0                 not null
    );

create table if not exists user
(
    id         int unsigned auto_increment
    primary key,
    name       varchar(10)                            null,
    login_type varchar(20)                            not null,
    social_id  varchar(80)                            not null,
    agree_push tinyint(1)   default 1                 not null,
    level      int unsigned default '1'               null,
    exp_point  int unsigned default '0'               null,
    created_at timestamp    default CURRENT_TIMESTAMP not null,
    updated_at timestamp    default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    is_deleted tinyint(1)   default 0                 not null,
    email      varchar(320)                           null
    );

create table if not exists alarm
(
    id                  int unsigned auto_increment
    primary key,
    name                varchar(20)          not null,
    snooze_interval     tinyint    default 5 not null,
    snooze_cnt          tinyint    default 3 not null,
    alarm_time          time                 not null,
    monday              tinyint(1) default 0 not null,
    tuesday             tinyint(1) default 0 not null,
    wednesday           tinyint(1) default 0 not null,
    thursday            tinyint(1) default 0 not null,
    friday              tinyint(1) default 0 not null,
    saturday            tinyint(1) default 0 not null,
    sunday              tinyint(1) default 0 not null,
    is_snooze_activated tinyint(1) default 1 not null,
    is_vibrate          tinyint(1) default 1 not null,
    is_activated        tinyint(1) default 1 not null,
    user_id             int unsigned         null,
    mission_id          int unsigned         not null,
    mission_object_id   int unsigned         null,
    room_id             int unsigned         null,
    is_deleted          tinyint(1) default 0 not null,
    constraint alarm_ibfk_1
    foreign key (user_id) references user (id),
    constraint alarm_ibfk_2
    foreign key (room_id) references room (id),
    constraint alarm_ibfk_3
    foreign key (mission_id) references mission (id),
    constraint alarm_mission_object_id_fk
    foreign key (mission_object_id) references mission_object (id)
    );

create table if not exists fcm_token
(
    id         int unsigned auto_increment
    primary key,
    value      varchar(200)                        not null,
    user_id    int unsigned                        not null,
    updated_at timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    constraint fcm_token_ibfk_1
    foreign key (user_id) references user (id)
    );

create index user_id
    on fcm_token (user_id);

create table if not exists mission_log
(
    id               int unsigned auto_increment
    primary key,
    alarm_name       varchar(20)                         not null,
    mission_pic_link text                                not null,
    created_at       timestamp default CURRENT_TIMESTAMP not null,
    is_deleted       tinyint(1)                          null,
    alarm_id         int unsigned                        null,
    user_id          int unsigned                        not null,
    room_id          int unsigned                        null,
    constraint mission_log_alarm_id_fk
    foreign key (alarm_id) references alarm (id),
    constraint mission_log_ibfk_1
    foreign key (user_id) references user (id),
    constraint mission_log_ibfk_2
    foreign key (room_id) references room (id)
    );

create index room_id
    on mission_log (room_id);

create index user_id
    on mission_log (user_id);

create table if not exists room_user
(
    id         int unsigned auto_increment
    primary key,
    created_at timestamp  default CURRENT_TIMESTAMP not null,
    room_id    int unsigned                         null,
    user_id    int unsigned                         not null,
    agree_push tinyint(1) default 1                 null,
    constraint room_user_ibfk_1
    foreign key (room_id) references room (id),
    constraint room_user_ibfk_2
    foreign key (user_id) references user (id)
    );

create index room_id
    on room_user (room_id);

create index user_id
    on room_user (user_id);

create table if not exists user_avatar
(
    id         int unsigned auto_increment
    primary key,
    is_active  tinyint(1) default 0                 not null,
    created_at timestamp  default CURRENT_TIMESTAMP not null,
    user_id    int unsigned                         not null,
    avatar_id  int unsigned                         not null,
    constraint user_avatar_ibfk_1
    foreign key (user_id) references user (id),
    constraint user_avatar_ibfk_2
    foreign key (avatar_id) references avatar (id)
    );

create index avatar_id
    on user_avatar (avatar_id);

create index user_id
    on user_avatar (user_id);

