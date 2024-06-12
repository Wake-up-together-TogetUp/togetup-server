ALTER TABLE mission_log
    ADD COLUMN alarm_id INT UNSIGNED after is_deleted;

ALTER TABLE mission_log
    ADD CONSTRAINT mission_log_alarm_id_fk
        FOREIGN KEY (alarm_id) REFERENCES alarm(id);