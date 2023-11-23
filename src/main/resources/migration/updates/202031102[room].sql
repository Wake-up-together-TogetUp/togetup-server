--table : invitation_code 길이 10으로 수정
alter table room
    add invitation_code varchar(10) null;