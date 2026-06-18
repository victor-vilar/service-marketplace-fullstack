CREATE TABLE tb_users
(
    id                         UUID PRIMARY KEY,
    name                       VARCHAR(150) NOT NULL,
    password                   VARCHAR(255)  NOT NULL,
    email                      VARCHAR(254)  NOT NULL UNIQUE,
    phone_number               VARCHAR(20),
    is_account_non_expired     BOOLEAN DEFAULT TRUE,
    is_account_non_locked      BOOLEAN DEFAULT TRUE,
    is_credentials_non_expired BOOLEAN DEFAULT TRUE,
    is_enabled                 BOOLEAN DEFAULT TRUE
);

CREATE TABLE tb_user_authorities
(
    user_id UUID NOT NULL,
    authority VARCHAR(50),
    CONSTRAINT user_fk FOREIGN KEY (user_id) REFERENCES tb_users(id)
);


CREATE TABLE tb_categories
(
    id   UUID PRIMARY KEY,
    name VARCHAR(150) NOT NULL UNIQUE
);

CREATE TABLE tb_jobs
(
    id          UUID PRIMARY KEY,
    title       VARCHAR(150)   NOT NULL,
    description VARCHAR(400)   NOT NULL,
    price       DECIMAL(10, 2) NOT NULL,
    active      BOOLEAN DEFAULT TRUE,
    category_id UUID           NOT NULL,
    provider_id UUID           NOT NULL,
    CONSTRAINT category_fk FOREIGN KEY (category_id) REFERENCES tb_categories (id) ON DELETE SET NULL,
    CONSTRAINT provider_fk FOREIGN KEY (provider_id) REFERENCES tb_users(id)

);

CREATE TABLE tb_orders
(
    id            UUID PRIMARY KEY,
    creation_date DATE           NOT NULL,
    order_status  VARCHAR(50)    NOT NULL,
    total_amount  DECIMAL(10, 2) NOT NULL,
    observation   VARCHAR(500),
    customer_id   UUID           NOT NULL,
    job_id        UUID           NOT NULL,
    CONSTRAINT customer_fk FOREIGN KEY (customer_id) REFERENCES tb_users (id),
    CONSTRAINT job_fk FOREIGN KEY (job_id) REFERENCES tb_jobs (id)
);

CREATE TABLE tb_payments
(
    id             UUID PRIMARY KEY,
    payment_status VARCHAR(50) NOT NULL,
    payment_method VARCHAR(50) NOT NULL,
    order_id       UUID        NOT NULL,
    CONSTRAINT order_fk FOREIGN KEY (order_id) REFERENCES tb_orders (id)

);

CREATE TABLE tb_reviews
(
    id         UUID     PRIMARY KEY ,
    created_at Date        NOT NULL,
    rating     int         NOT NULL,
    commentary VARCHAR(300),
    reviewer   VARCHAR(50) NOT NULL,
    order_id   UUID        NOT NULL,
    CONSTRAINT order_fk_for_reviews FOREIGN KEY (order_id) REFERENCES tb_orders (id)


);