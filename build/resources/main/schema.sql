-- Drop tables if they exist (start fresh)
DROP TABLE IF EXISTS restaurant_pictures CASCADE;
DROP TABLE IF EXISTS restaurant_projection_pictures CASCADE;
DROP TABLE IF EXISTS restaurant_projection CASCADE;
DROP TABLE IF EXISTS restaurant CASCADE;

-- ==============================
-- Restaurant Table (write model)
-- ==============================
CREATE TABLE restaurant (
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

-- Pictures for Restaurant
CREATE TABLE restaurant_pictures (
                                     id UUID NOT NULL,
                                     url VARCHAR(2048),
                                     CONSTRAINT fk_restaurant
                                         FOREIGN KEY(id)
                                             REFERENCES restaurant(id)
                                             ON DELETE CASCADE
);

-- ==============================
-- Restaurant Projection Table (read model)
-- ==============================
CREATE TABLE restaurant_projection (
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

-- Pictures for Restaurant Projection
CREATE TABLE restaurant_projection_pictures (
                                                restaurant_projection_id UUID NOT NULL,
                                                url VARCHAR(2048),
                                                CONSTRAINT fk_restaurant_projection
                                                    FOREIGN KEY(restaurant_projection_id)
                                                        REFERENCES restaurant_projection(id)
                                                        ON DELETE CASCADE
);
