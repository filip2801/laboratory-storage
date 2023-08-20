create table location
(
    location_id uuid         not null primary key,
    name        varchar(255) not null,
    parent_id   uuid,
    path        jsonb        not null,
    type        varchar(255) not null
);

create table sample_placement
(
    sample_id   uuid         not null primary key,
    employee_id uuid         not null,
    location_id uuid         not null,
    updated_at  timestamp(6) not null
);

create table sample_placement_history
(
    sample_id   uuid         not null,
    updated_at  timestamp(6) not null,
    employee_id uuid         not null,
    location_id uuid         not null,
    primary key (sample_id, updated_at)
);
