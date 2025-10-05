-- Drop schemas if they exist
DROP SCHEMA IF EXISTS restaurant CASCADE;
DROP SCHEMA IF EXISTS ordering CASCADE;

-- Create schemas
CREATE SCHEMA restaurant;
CREATE SCHEMA ordering;

-- ==============================
-- Restaurant Context (write model)
-- ==============================
CREATE TABLE restaurant.restaurant (
                                       id UUID PRIMARY KEY,
                                       owner_id VARCHAR(255) NOT NULL,
                                       name VARCHAR(255) NOT NULL,
                                       street VARCHAR(255),
                                       number VARCHAR(50),
                                       postal_code VARCHAR(50),
                                       city VARCHAR(100),
                                       country VARCHAR(100),
                                       email_address VARCHAR(255),
                                       cuisine_type VARCHAR(50),
                                       min_prep_time INTEGER NOT NULL,
                                       max_prep_time INTEGER NOT NULL,
                                       opening_time TIME,
                                       closing_time TIME
);

CREATE TABLE restaurant.restaurant_pictures (
     restaurant_id UUID NOT NULL,
     url VARCHAR(2048),
     CONSTRAINT fk_restaurant_pictures
         FOREIGN KEY (restaurant_id)
             REFERENCES restaurant.restaurant(id)
             ON DELETE CASCADE
);

CREATE TABLE restaurant.restaurant_open_days (
    restaurant_id UUID NOT NULL,
    day VARCHAR(15),
    CONSTRAINT fk_restaurant_open_days
        FOREIGN KEY (restaurant_id)
            REFERENCES restaurant.restaurant(id)
            ON DELETE CASCADE
);

CREATE TABLE restaurant.food_menu (
                                      restaurant_id UUID PRIMARY KEY ,          -- 1:1 relationship with restaurant
                                      average_menu_price NUMERIC(10,2) NOT NULL,   -- Average price of published dishes

                                      CONSTRAINT fk_food_menu_restaurant
                                          FOREIGN KEY (restaurant_id)
                                              REFERENCES restaurant.restaurant(id)
                                              ON DELETE CASCADE
);


-- ==============================
-- Ordering Context (read model)
-- ==============================
CREATE TABLE ordering.restaurant_projection (
     id UUID PRIMARY KEY,
     owner_id UUID NOT NULL,
     name VARCHAR(255) NOT NULL,
     street VARCHAR(255),
     number VARCHAR(50),
     postal_code VARCHAR(50),
     city VARCHAR(100),
     country VARCHAR(100),
     email_address VARCHAR(255),
     cuisine_type VARCHAR(50),
     min_prep_time INTEGER NOT NULL,
     max_prep_time INTEGER NOT NULL,
     opening_time TIME,
     closing_time TIME
);

CREATE TABLE ordering.restaurant_projection_pictures (
              restaurant_projection_id UUID NOT NULL,
              url VARCHAR(2048),
              CONSTRAINT fk_restaurant_projection_pictures
                  FOREIGN KEY (restaurant_projection_id)
                      REFERENCES ordering.restaurant_projection(id)
                      ON DELETE CASCADE
);

CREATE TABLE ordering.restaurant_projection_open_days (
               restaurant_projection_id UUID NOT NULL,
               day VARCHAR(15),
               CONSTRAINT fk_restaurant_projection_open_days
                   FOREIGN KEY (restaurant_projection_id)
                       REFERENCES ordering.restaurant_projection(id)
                       ON DELETE CASCADE
);

CREATE TABLE ordering.food_menu_projection (
                                               restaurant_id UUID PRIMARY KEY ,
                                               average_menu_price NUMERIC(10,2) NOT NULL,

                                               CONSTRAINT fk_food_menu_projection_restaurant
                                                   FOREIGN KEY (restaurant_id)
                                                       REFERENCES ordering.restaurant_projection(id)
                                                       ON DELETE CASCADE
);

