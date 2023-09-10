CREATE TABLE `user` (
                        `id`	                INT UNSIGNED    NOT NULL    AUTO_INCREMENT PRIMARY KEY,
                        `name`     	            VARCHAR(10)	    NULL,
                        `login_type`            VARCHAR(20)	    NOT NULL,
                        `social_id`	            VARCHAR(80)	    NOT NULL,
                        `agree_push`	        TINYINT(1)	    NOT NULL	DEFAULT TRUE,
                        `created_at`	        TIMESTAMP	    NOT NULL	DEFAULT current_timestamp,
                        `updated_at`	        TIMESTAMP	    NULL        DEFAULT current_timestamp ON UPDATE current_timestamp,
                        `is_deleted`	        TINYINT(1)	    NOT NULL    DEFAULT FALSE
);

CREATE TABLE `mission` (
                           `id`	        INT UNSIGNED        NOT NULL    AUTO_INCREMENT PRIMARY KEY,
                           `name`	    VARCHAR(20)	        NOT NULL,
                           `object`	    VARCHAR(20)	        NOT NULL,
                           `created_at`	TIMESTAMP	        NOT NULL	DEFAULT current_timestamp,
                           `updated_at`	TIMESTAMP	        NOT NULL    DEFAULT current_timestamp ON UPDATE current_timestamp,
                           `is_active`	TINYINT(1)	    NOT NULL	DEFAULT TRUE
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
                              FOREIGN KEY (room_id)         REFERENCES room (id),
                              FOREIGN KEY (user_id)         REFERENCES user(id)

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

CREATE TABLE `alarm` (
                         `id`	            INT UNSIGNED	NOT NULL	AUTO_INCREMENT  PRIMARY KEY,
                         `name`	            VARCHAR(20)	    NOT NULL,
                         `icon`	            VARCHAR(30)	    NOT NULL    DEFAULT '⏰',
                         `snooze_interval`	TINYINT	        NOT NULL	DEFAULT 5,
                         `snooze_cnt`	    TINYINT	        NOT NULL	DEFAULT 3,
                         `alarm_time`	    TIME	        NOT NULL,
                         `monday`	        TINYINT(1)	    NOT NULL	DEFAULT FALSE,
                         `tuesday`	        TINYINT(1)	    NOT NULL	DEFAULT FALSE,
                         `wednesday`	    TINYINT(1)	    NOT NULL	DEFAULT FALSE,
                         `thursday`	        TINYINT(1)	    NOT NULL	DEFAULT FALSE,
                         `friday`	        TINYINT(1)	    NOT NULL	DEFAULT FALSE,
                         `saturday`	        TINYINT(1)	    NOT NULL	DEFAULT FALSE,
                         `sunday`	        TINYINT(1)	    NOT NULL	DEFAULT FALSE,
                         `is_vibrate`	    TINYINT(1)	    NOT NULL	DEFAULT TRUE,
                         `is_activated`	    TINYINT(1)	    NOT NULL	DEFAULT TRUE,
                         `user_id`	        INT UNSIGNED	NOT NULL,
                         `mission_id`	    INT UNSIGNED	NULL,
                         `room_id`	        INT UNSIGNED	NULL,
                         FOREIGN KEY (user_id) REFERENCES user(id),
                         FOREIGN KEY (room_id) REFERENCES room(id),
                         FOREIGN KEY (mission_id) REFERENCES mission(id)
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

