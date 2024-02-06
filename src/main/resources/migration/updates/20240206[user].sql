alter table user
    change experience exp_point int unsigned default '0' null;

alter table user
    change point coin int unsigned default '0' null;