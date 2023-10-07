CREATE TABLE `user` (
                        `id`	                INT UNSIGNED    NOT NULL    AUTO_INCREMENT PRIMARY KEY,
                        `name`     	            VARCHAR(10)	    NULL,
                        `email`     	        VARCHAR(30)	    NULL,
                        `login_type`            VARCHAR(20)	    NOT NULL,
                        `social_id`	            VARCHAR(80)	    NOT NULL,
                        `agree_push`	        TINYINT(1)	    NOT NULL	DEFAULT TRUE,
                        `created_at`	        TIMESTAMP	    NOT NULL	DEFAULT current_timestamp,
                        `updated_at`	        TIMESTAMP	    NULL        DEFAULT current_timestamp ON UPDATE current_timestamp,
                        `is_deleted`	        TINYINT(1)	    NOT NULL    DEFAULT FALSE
);

create table mission
(
    id         int unsigned auto_increment
        primary key,
    name       varchar(30)                          not null,
    created_at timestamp  default CURRENT_TIMESTAMP not null,
    is_active  tinyint(1) default 1                 not null
);

CREATE TABLE room (
                         `id`	                        INT UNSIGNED    NOT NULL    AUTO_INCREMENT  PRIMARY KEY,
                         `name`	                        VARCHAR(10)	    NOT NULL,
                         `intro`	                    VARCHAR(30)	    NULL,
                         `topic`                        VARCHAR(100)    NULL,
                         `invitation_code`              VARCHAR(100)    NULL,
                         `created_at`	                TIMESTAMP	    NOT NULL	DEFAULT current_timestamp,
                         `updated_at`	                TIMESTAMP	    NOT NULL	DEFAULT current_timestamp ON UPDATE current_timestamp,
                         `latest_completion_time`       TIMESTAMP       ,
                         `is_deleted`	                TINYINT(1)	    NOT NULL    DEFAULT FALSE
);

CREATE TABLE `room_user` (
                              `id`	        INT UNSIGNED	NOT NULL	AUTO_INCREMENT  PRIMARY KEY,
                              `created_at`	TIMESTAMP	    NOT NULL	DEFAULT current_timestamp,
                              `room_id`	    INT UNSIGNED	NULL,
                              `user_id`	    INT UNSIGNED	NOT NULL,
                              `is_host`	    TINYINT(1)	    NULL	    DEFAULT FALSE,
                              `agree_push`	TINYINT(1)	    NOT NULL	DEFAULT TRUE,
                              FOREIGN KEY (room_id)         REFERENCES room (id),
                              FOREIGN KEY (user_id)         REFERENCES user(id)

);

create table mission_object
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

CREATE TABLE `mission_log` (
                               `id`	                    INT UNSIGNED	NOT NULL    AUTO_INCREMENT  PRIMARY KEY,
                               `alarm_name`	            VARCHAR(20)	    NOT NULL,
                               `mission_pic_link` 	    TEXT	        NOT NULL,
                               `created_at`	            TIMESTAMP	    NOT NULL	DEFAULT current_timestamp,
                               `is_deleted`	            TINYINT(1)	    NULL,
                               `user_id`	            INT UNSIGNED	NOT NULL,
                               `room_id` 	            INT UNSIGNED	NULL,
                               `mission_id`	            INT UNSIGNED	NOT NULL,
                               FOREIGN KEY (user_id) REFERENCES user(id),
                               FOREIGN KEY (room_id) REFERENCES room(id),
                               FOREIGN KEY (mission_id) REFERENCES mission(id)
);

CREATE TABLE `notification` (
                                `id`	            INT UNSIGNED	    NOT NULL	AUTO_INCREMENT  PRIMARY KEY,
                                `title`	            VARCHAR(20)	        NULL,
                                `content`	        VARCHAR(30)	        NULL,
                                `created_at`	    TIMESTAMP	        NOT NULL    DEFAULT current_timestamp,
                                `room_id`	        INT UNSIGNED	    NULL,
                                `fcm_token_id`	INT UNSIGNED	    NULL,
                                FOREIGN KEY (room_id) REFERENCES room(id)

);

create table alarm
(
    id                  int unsigned auto_increment
        primary key,
    name                varchar(20)             not null,
    icon                varchar(30) default '⏰' not null,
    snooze_interval     tinyint     default 5   not null,
    snooze_cnt          tinyint     default 3   not null,
    alarm_time          time                    not null,
    monday              tinyint(1create table avatar
        (
        id              int unsigned auto_increment
        primary key,
        theme           varchar(20)                         not null,
        phase           varchar(20)                         not null,
        avatar_img_link text                                not null,
        price           int unsigned                        not null,
        unlock_level    int unsigned                        not null,
        created_at      timestamp default CURRENT_TIMESTAMP not null
        );

create table mission
(
    id         int unsigned auto_increment
        primary key,
    name       varchar(30)                          not null,
    created_at timestamp  default CURRENT_TIMESTAMP not null,
    is_active  tinyint(1) default 1                 not null
);

create table mission_object
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

create table room
(
    id                     int unsigned auto_increment
        primary key,
    name                   varchar(10)                          not null,
    intro                  varchar(30)                          null,
    topic                  varchar(100)                         null,
    invitation_code        varchar(100)                         null,
    created_at             timestamp  default CURRENT_TIMESTAMP not null,
    updated_at             timestamp  default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    latest_completion_time timestamp                            null,
    is_deleted             tinyint(1) default 0                 not null
);

create table notification
(
    id           int unsigned auto_increment
        primary key,
    title        varchar(20)                         null,
    content      varchar(30)                         null,
    created_at   timestamp default CURRENT_TIMESTAMP not null,
    room_id      int unsigned                        null,
    fcm_token_id int unsigned                        null,
    constraint notification_ibfk_1
        foreign key (room_id) references room (id)
);

create index room_id
    on notification (room_id);

create table user
(
    id         int unsigned auto_increment
        primary key,
    name       varchar(10)                            null,
    email      varchar(30)                            null,
    login_type varchar(20)                            not null,
    social_id  varchar(80)                            not null,
    agree_push tinyint(1)   default 1                 not null,
    level      int unsigned default '1'               not null,
    experience int unsigned default '0'               not null,
    point      int unsigned default '0'               not null,
    created_at timestamp    default CURRENT_TIMESTAMP not null,
    updated_at timestamp    default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    is_deleted tinyint(1)   default 0                 not null
);

create table alarm
(
    id                  int unsigned auto_increment
        primary key,
    name                varchar(20)             not null,
    icon                varchar(30) default '⏰' not null,
    snooze_interval     tinyint     default 5   not null,
    snooze_cnt          tinyint     default 3   not null,
    alarm_time          time                    not null,
    monday              tinyint(1)  default 0   not null,
    tuesday             tinyint(1)  default 0   not null,
    wednesday           tinyint(1)  default 0   not null,
    thursday            tinyint(1)  default 0   not null,
    friday              tinyint(1)  default 0   not null,
    saturday            tinyint(1)  default 0   not null,
    sunday              tinyint(1)  default 0   not null,
    is_snooze_activated tinyint(1)  default 1   not null,
    is_vibrate          tinyint(1)  default 1   not null,
    is_activated        tinyint(1)  default 1   not null,
    user_id             int unsigned            not null,
    mission_id          int unsigned            null,
    mission_object_id   int unsigned            null,
    room_id             int unsigned            null,
    constraint alarm_ibfk_1
        foreign key (user_id) references user (id),
    constraint alarm_ibfk_2
        foreign key (room_id) references room (id),
    constraint alarm_ibfk_3
        foreign key (mission_id) references mission (id),
    constraint alarm_mission_object_id_fk
        foreign key (mission_object_id) references mission_object (id)
);

create table fcm_token
(
    id         int unsigned auto_increment
        primary key,
    value      varchar(80)                         not null,
    user_id    int unsigned                        not null,
    updated_at timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    constraint fcm_token_ibfk_1
        foreign key (user_id) references user (id)
);

create index user_id
    on fcm_token (user_id);

create table mission_log
(
    id               int unsigned auto_increment
        primary key,
    alarm_name       varchar(20)                         not null,
    mission_pic_link text                                not null,
    created_at       timestamp default CURRENT_TIMESTAMP not null,
    is_deleted       tinyint(1)                          null,
    user_id          int unsigned                        not null,
    room_id          int unsigned                        null,
    constraint mission_log_ibfk_1
        foreign key (user_id) references user (id),
    constraint mission_log_ibfk_2
        foreign key (room_id) references room (id)
);

create index room_id
    on mission_log (room_id);

create index user_id
    on mission_log (user_id);

create table room_user
(
    id         int unsigned auto_increment
        primary key,
    created_at timestamp  default CURRENT_TIMESTAMP not null,
    room_id    int unsigned                         null,
    user_id    int unsigned                         not null,
    is_host    tinyint(1) default 0                 null,
    agree_push tinyint(1) default 1                 not null,
    constraint room_user_ibfk_1
        foreign key (room_id) references room (id),
    constraint room_user_ibfk_2
        foreign key (user_id) references user (id)
);

create index room_id
    on room_user (room_id);

create index user_id
    on room_user (user_id);

create table user_avatar
(
    id        int unsigned auto_increment
        primary key,
    user_id   int unsigned not null,
    avatar_id int unsigned not null,
    constraint user_avatar_ibfk_1
        foreign key (user_id) references user (id),
    constraint user_avatar_ibfk_2
        foreign key (avatar_id) references avatar (id)
);

create index avatar_id
    on user_avatar (avatar_id);

create index user_id
    on user_avatar (user_id);

create table user_avatar_purchase_log
(
    id         int unsigned auto_increment
        primary key,
    created_at timestamp default CURRENT_TIMESTAMP not null,
    user_id    int unsigned                        not null,
    avatar_id  int unsigned                        not null,
    constraint user_avatar_purchase_log_ibfk_1
        foreign key (user_id) references user (id),
    constraint user_avatar_purchase_log_ibfk_2
        foreign key (avatar_id) references avatar (id)
);

create index avatar_id
    on user_avatar_purchase_log (avatar_id);

create index user_id
    on user_avatar_purchase_log (user_id);

)  default 0   not null,
    tuesday             tinyint(1)  default 0   not null,
    wednesday           tinyint(1)  default 0   not null,
    thursday            tinyint(1)  default 0   not null,
    friday              tinyint(1)  default 0   not null,
    saturday            tinyint(1)  default 0   not null,
    sunday              tinyint(1)  default 0   not null,
    is_snooze_activated tinyint(1)  default 1   not null,
    is_vibrate          tinyint(1)  default 1   not null,
    is_activated        tinyint(1)  default 1   not null,
    user_id             int unsigned            not null,
    mission_id          int unsigned            null,
    mission_object_id   int unsigned            null,
    room_id             int unsigned            null,
    constraint alarm_ibfk_1
        foreign key (user_id) references user (id),
    constraint alarm_ibfk_2
        foreign key (room_id) references room (id),
    constraint alarm_ibfk_3
        foreign key (mission_id) references mission (id),
    constraint alarm_mission_object_id_fk
        foreign key (mission_object_id) references mission_object (id)
);

CREATE TABLE `avatar` (
                          `id`	            INT UNSIGNED	NOT NULL	AUTO_INCREMENT  PRIMARY KEY,
                          `theme`	        VARCHAR(20)	    NOT NULL,
                          `phase`	        VARCHAR(20) 	NOT NULL,
                          `avatar_img_link`	TEXT	        NOT NULL,
                          `price`	        INT UNSIGNED	NOT NULL,
                          `created_at`	TIMESTAMP	    NOT NULL    DEFAULT current_timestamp
);

CREATE TABLE `user_avatar_purchase_log` (
                                   `id`	        INT UNSIGNED	NOT NULL	AUTO_INCREMENT  PRIMARY KEY,
                                   `created_at`	TIMESTAMP	    NOT NULL    DEFAULT current_timestamp,
                                   `user_id`	INT UNSIGNED	NOT NULL,
                                   `avatar_id`	INT UNSIGNED	NOT NULL,
                                   FOREIGN KEY (user_id) REFERENCES user(id),
                                   FOREIGN KEY (avatar_id) REFERENCES avatar(id)


);

CREATE TABLE `user_avatar` (
                               `id`	        INT UNSIGNED	NOT NULL	AUTO_INCREMENT  PRIMARY KEY,
                               `user_id`	INT UNSIGNED	NOT NULL,
                               `avatar_id`	INT UNSIGNED	NOT NULL,
                               FOREIGN KEY (user_id) REFERENCES user(id),
                               FOREIGN KEY (avatar_id) REFERENCES avatar(id)


);

CREATE TABLE `fcm_token` (
                                `id`	        INT UNSIGNED	    NOT NULL	AUTO_INCREMENT  PRIMARY KEY,
                                `value`	        VARCHAR(80)	        NOT NULL,
                                `user_id`	    INT UNSIGNED	    NOT NULL,
                                `updated_at`	        TIMESTAMP	NOT NULL    DEFAULT current_timestamp ON UPDATE current_timestamp,
                                FOREIGN KEY (user_id) REFERENCES user(id)
);

