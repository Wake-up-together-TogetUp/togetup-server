alter table alarm
drop column snooze_interval;

alter table alarm
drop column snooze_cnt;

alter table alarm
drop column is_snooze_activated;