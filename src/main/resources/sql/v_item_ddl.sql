CREATE TABLE v_list
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    created_date    datetime              NULL,
    v_category_name VARCHAR(100)          NULL,
    v_category_code INT                   NULL,
    v_detail        VARCHAR(1000)         NULL,
    case_tag        VARCHAR(10)           NULL,
    respond_tag     VARCHAR(10)           NULL,
    ordering        INT                   NULL,
    annual_table_id BIGINT                NULL,
    CONSTRAINT pk_v_list PRIMARY KEY (id)
);

CREATE TABLE v_item_detail_group
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    v_group_grade INT                   NULL,
    v_group_name  VARCHAR(255)          NULL,
    v_group_code  INT                   NULL,
    ordering      INT                   NULL,
    v_item_id     BIGINT                NULL,
    CONSTRAINT pk_v_item_detail_group PRIMARY KEY (id)
);

ALTER TABLE v_item_detail_group
    ADD CONSTRAINT FK_V_ITEM_DETAIL_GROUP_ON_V_ITEM FOREIGN KEY (v_item_id) REFERENCES v_list (id);


CREATE TABLE v_item_detail
(
    id                     BIGINT AUTO_INCREMENT NOT NULL,
    v_detail               VARCHAR(1000)         NULL,
    v_item_detail_group_id BIGINT                NULL,
    CONSTRAINT pk_v_item_detail PRIMARY KEY (id)
);

ALTER TABLE v_item_detail
    ADD CONSTRAINT FK_V_ITEM_DETAIL_ON_V_ITEM_DETAIL_GROUP FOREIGN KEY (v_item_detail_group_id) REFERENCES v_item_detail_group (id);

create table v_detail_respond
(
    v_item_detail_id bigint       not null,
    respond          varchar(255) null,
    constraint FKki9dt75mq9f9qng2jjhe42vga
        foreign key (v_item_detail_id) references v_item_detail (id)
);