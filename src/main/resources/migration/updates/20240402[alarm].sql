alter table alarm
    add is_deleted tinyint(1) default 0 not null;