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
                              `agree_push`	TINYINT(1)	    NULL	DEFAULT TRUE,
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

