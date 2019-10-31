CREATE SEQUENCE purchase_orders_id_seq;

create table purchase_orders(
    id bigint NOT NULL DEFAULT nextval('purchase_orders_id_seq') not null primary key,
    product text not null,
    quantity int,
    buyer text not null,
    segments jsonb not null,
    created_at timestamptz not null default NOW(),
    modified_at timestamptz not null default NOW()
);