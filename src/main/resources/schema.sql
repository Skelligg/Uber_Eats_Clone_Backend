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
                                       owner_name VARCHAR(255) NOT NULL,
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

CREATE TABLE restaurant.dish (
                                 id UUID PRIMARY KEY,
                                 food_menu_id UUID NOT NULL,
                                 name VARCHAR(255) NOT NULL,
                                 description TEXT,
                                 price NUMERIC(10, 2) NOT NULL,
                                 picture_url VARCHAR(2048),
                                 tags TEXT,
                                 dish_type VARCHAR(50),
                                 state VARCHAR(50) NOT NULL,
                                 scheduled_publish_time TIMESTAMP,
                                 scheduled_to_become_state VARCHAR(50),
                                 CONSTRAINT fk_dish_food_menu
                                     FOREIGN KEY (food_menu_id)
                                         REFERENCES restaurant.food_menu(restaurant_id)
                                         ON DELETE CASCADE
);




-- ==============================
-- Ordering Context (read model)
-- ==============================
CREATE TABLE ordering.restaurant_projection (
     id UUID PRIMARY KEY,
     owner_id UUID NOT NULL,
     owner_name VARCHAR(255) NOT NULL,
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


CREATE TABLE ordering.dish_projection (
                                          dish_id UUID PRIMARY KEY,
                                          food_menu_id UUID NOT NULL,
                                          name VARCHAR(255) NOT NULL,
                                          description TEXT,
                                          price NUMERIC(10, 2) NOT NULL,
                                          picture_url VARCHAR(2048),
                                          tags TEXT,
                                          dish_type VARCHAR(50),
                                          dish_state VARCHAR(50) NOT NULL,

                                          CONSTRAINT fk_dish_projection_food_menu
                                              FOREIGN KEY (food_menu_id)
                                                  REFERENCES ordering.food_menu_projection(restaurant_id)
                                                  ON DELETE CASCADE
);

-- Orders
CREATE TABLE ordering.orders (
                                 id UUID PRIMARY KEY,
                                 restaurant_id UUID NOT NULL,
                                 customer_name VARCHAR(255) NOT NULL,
                                 customer_email VARCHAR(255) NOT NULL,

    -- Delivery address (flattened)
                                 street VARCHAR(255),
                                 number VARCHAR(50),
                                 postal_code VARCHAR(50),
                                 city VARCHAR(100),
                                 country VARCHAR(100),

                                 total_price NUMERIC(10,2) NOT NULL,
                                 status VARCHAR(50),
                                 rejection_reason TEXT,

                                 placed_at TIMESTAMP,
                                 accepted_at TIMESTAMP,
                                 rejected_at TIMESTAMP,
                                 ready_at TIMESTAMP,
                                 picked_up_at TIMESTAMP,
                                 delivered_at TIMESTAMP,

                                 estimated_delivery_minutes INTEGER
);

-- Order lines (ElementCollection)
CREATE TABLE ordering.order_lines (
                                      order_id UUID NOT NULL,
                                      dish_id UUID NOT NULL,
                                      dish_name VARCHAR(255) NOT NULL,
                                      quantity INTEGER NOT NULL,
                                      unit_price NUMERIC(10,2) NOT NULL,
                                      line_price NUMERIC(10,2) NOT NULL,

                                      CONSTRAINT fk_order_lines_order
                                          FOREIGN KEY (order_id)
                                              REFERENCES ordering.orders(id)
                                              ON DELETE CASCADE
);

-- Courier locations (ElementCollection)
CREATE TABLE ordering.courier_locations (
                                            order_id UUID NOT NULL,
                                            timestamp TIMESTAMP,
                                            latitude DOUBLE PRECISION,
                                            longitude DOUBLE PRECISION,

                                            CONSTRAINT fk_courier_locations_order
                                                FOREIGN KEY (order_id)
                                                    REFERENCES ordering.orders(id)
                                                    ON DELETE CASCADE
);

