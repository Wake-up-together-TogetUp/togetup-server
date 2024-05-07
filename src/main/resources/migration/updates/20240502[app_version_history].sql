create table app_version_history
(
    version       varchar(10) primary key             not null,
    app_store_url varchar(500)                        not null,
    created_at    timestamp default CURRENT_TIMESTAMP not null
);