-- =====================================
-- Dummy data for restaurant context
-- =====================================

-- Restaurants
INSERT INTO restaurant.restaurant (
    id, owner_id, owner_name, name, street, number, postal_code, city, country, email_address, cuisine_type, min_prep_time, max_prep_time, opening_time, closing_time
) VALUES
      ('11111111-1111-1111-1111-111111111111', '3a779f4d-4e13-4322-8169-8fce41a4e108', 'Alice', 'Bella Cucina', 'Jezusstraat', '32', '2000', 'Antwerp', 'Belgium', 'alice@bella.com', 'ITALIAN', 15, 30, '11:00', '22:00'),
      ('22222222-2222-2222-2222-222222222222', '22222222-2222-2222-2222-222222222223', 'Bob', 'Sushi Palace', 'Market Street', '5', '2000', 'Antwerp', 'Belgium', 'bob@sushi.com', 'JAPANESE', 10, 25, '12:00', '21:00');

-- Restaurant pictures
INSERT INTO restaurant.restaurant_pictures (restaurant_id, url) VALUES
                                                                    ('11111111-1111-1111-1111-111111111111', 'https://www.fodors.com/wp-content/uploads/2018/03/Italy-Michelin-Restaurants-Osteria-Francescana-1.jpg'),
                                                                    ('22222222-2222-2222-2222-222222222222', 'https://example.com/sushi1.jpg');

-- Restaurant open days
INSERT INTO restaurant.restaurant_open_days (restaurant_id, day) VALUES
                                                                     ('11111111-1111-1111-1111-111111111111', 'MONDAY'),
                                                                     ('11111111-1111-1111-1111-111111111111', 'TUESDAY'),
                                                                     ('22222222-2222-2222-2222-222222222222', 'MONDAY'),
                                                                     ('22222222-2222-2222-2222-222222222222', 'WEDNESDAY');

-- Food menus (1:1)
INSERT INTO restaurant.food_menu (restaurant_id, average_menu_price) VALUES
                                                                         ('11111111-1111-1111-1111-111111111111', 20.00),
                                                                         ('22222222-2222-2222-2222-222222222222', 25.00);

-- Bella Cucina: 1 published, 1 draft
INSERT INTO restaurant.dish (
    id, food_menu_id, state,
    published_name, published_description, published_price, published_picture_url, published_tags, published_dish_type,
    draft_name, draft_description, draft_price, draft_picture_url, draft_tags, draft_dish_type,
    scheduled_publish_time, scheduled_to_become_state
) VALUES
      (
          'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1',
          '11111111-1111-1111-1111-111111111111',
          'PUBLISHED',
          'Margherita Pizza', 'Classic Italian Pizza with tomato sauce and cheese', 14.50, 'https://www.tasteofhome.com/wp-content/uploads/2024/03/Margherita-Pizza-_EXPS_TOHVP24_275515_MF_02_28_1.jpg', 'gluten,lactose', 'MAIN',
          NULL, NULL, NULL, NULL, NULL, NULL,
          NULL, NULL
      ),
      (
          'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa2',
          '11111111-1111-1111-1111-111111111111',
          'PUBLISHED',
          'Spaghetti Carbonara', 'Classic Italian pasta with bacon and cheese', 12.50, 'https://www.twopeasandtheirpod.com/wp-content/uploads/2023/01/Spaghetti-Carbonara168787.jpg', 'gluten,meat', 'MAIN',
          'Spaghetti Carbonara REVISED', 'Classic Italian pasta with bacon and cheese', 15.00, 'https://www.twopeasandtheirpod.com/wp-content/uploads/2023/01/Spaghetti-Carbonara168787.jpg', 'gluten', 'MAIN',
          NULL, NULL
      ),
      (
          'aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa3',
          '11111111-1111-1111-1111-111111111111',
          'DRAFT',
          NULL, NULL, NULL, NULL, NULL, NULL,
          'Tiramisu', 'Coffee-flavored Italian dessert', 6.50, 'https://upload.wikimedia.org/wikipedia/commons/5/58/Tiramisu_-_Raffaele_Diomede.jpg', 'gluten,lactose', 'DESSERT',
          NULL, NULL
      );

-- Sushi Palace: 1 draft, 1 published
INSERT INTO restaurant.dish (
    id, food_menu_id, state,
    published_name, published_description, published_price, published_picture_url, published_tags, published_dish_type,
    draft_name, draft_description, draft_price, draft_picture_url, draft_tags, draft_dish_type,
    scheduled_publish_time, scheduled_to_become_state
) VALUES
      (
          'bbbbbbb1-bbbb-bbbb-bbbb-bbbbbbbbbbb1',
          '22222222-2222-2222-2222-222222222222',
          'DRAFT',
          NULL, NULL, NULL, NULL, NULL, NULL,
          'Salmon Nigiri', 'Fresh salmon over rice', 4.50, 'https://example.com/nigiri.jpg', 'soy', 'MAIN',
          NULL, NULL
      ),
      (
          'bbbbbbb2-bbbb-bbbb-bbbb-bbbbbbbbbbb2',
          '22222222-2222-2222-2222-222222222222',
          'PUBLISHED',
          'Miso Soup', 'Traditional Japanese soup', 3.00, 'https://example.com/miso.jpg', 'soy', 'STARTER',
          NULL, NULL, NULL, NULL, NULL, NULL,
          NULL, NULL
      );

-- =====================================
-- Dummy data for ordering context (read model)
-- =====================================
SET search_path TO restaurant, ordering;

-- Restaurants
INSERT INTO ordering.restaurant_projection (
    id, name, street, number, postal_code, city, country, email_address, cuisine_type, min_prep_time, max_prep_time, opening_time, closing_time
) VALUES
      ('11111111-1111-1111-1111-111111111111', 'Bella Cucina', 'Main Street', '12', '1000', 'Brussels', 'Belgium', 'alice@bella.com', 'ITALIAN', 15, 30, '00:00', '23:50'),
      ('22222222-2222-2222-2222-222222222222', 'Sushi Palace', 'Market Street', '5', '2000', 'Antwerp', 'Belgium', 'bob@sushi.com', 'JAPANESE', 10, 25, '12:00', '21:00');

-- Pictures
INSERT INTO ordering.restaurant_projection_pictures (restaurant_projection_id, url) VALUES
                                                                                        ('11111111-1111-1111-1111-111111111111', 'https://www.fodors.com/wp-content/uploads/2018/03/Italy-Michelin-Restaurants-Osteria-Francescana-1.jpg'),
                                                                                        ('22222222-2222-2222-2222-222222222222', 'https://res.cloudinary.com/dyiffrkzh/image/upload/v1720438999/bbj/hvdkkymmh3kbgikbotk2.jpg');

-- Open days
INSERT INTO ordering.restaurant_projection_open_days (restaurant_projection_id, day) VALUES
                                                                                         ('11111111-1111-1111-1111-111111111111', 'MONDAY'),
                                                                                         ('11111111-1111-1111-1111-111111111111', 'FRIDAY'),
                                                                                         ('22222222-2222-2222-2222-222222222222', 'MONDAY'),
                                                                                         ('22222222-2222-2222-2222-222222222222', 'WEDNESDAY');

-- Food menus
INSERT INTO ordering.food_menu_projection (restaurant_id, average_menu_price) VALUES
                                                                                  ('11111111-1111-1111-1111-111111111111', 20.00),
                                                                                  ('22222222-2222-2222-2222-222222222222', 25.00);

-- Dishes
INSERT INTO ordering.dish_projection (
    dish_id, food_menu_id, name, description, price, picture_url, tags, dish_type, dish_state
) VALUES
      ('aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa2', '11111111-1111-1111-1111-111111111111', 'Spaghetti Carbonara', 'Classic Italian pasta with bacon and cheese', 12.50, 'https://www.twopeasandtheirpod.com/wp-content/uploads/2023/01/Spaghetti-Carbonara168787.jpg', 'gluten,meat', 'MAIN', 'AVAILABLE'),
      ('aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', '11111111-1111-1111-1111-111111111111', 'Margherita Pizza', 'Classic Italian Pizza with tomato sauce and cheese', 14.50, 'https://www.tasteofhome.com/wp-content/uploads/2024/03/Margherita-Pizza-_EXPS_TOHVP24_275515_MF_02_28_1.jpg', 'gluten,lactose', 'STARTER', 'AVAILABLE'),
      ('bbbbbbb2-bbbb-bbbb-bbbb-bbbbbbbbbbb2', '22222222-2222-2222-2222-222222222222', 'Miso Soup', 'Traditional Japanese soup', 3.00, 'https://example.com/miso.jpg', 'soy', 'STARTER', 'AVAILABLE');


-- =====================================
-- Additional Restaurants
-- =====================================

-- Restaurants
INSERT INTO restaurant.restaurant (
    id, owner_id, owner_name, name, street, number, postal_code, city, country, email_address, cuisine_type, min_prep_time, max_prep_time, opening_time, closing_time
) VALUES
      ('33333333-3333-3333-3333-333333333333', '33333333-3333-3333-3333-333333333334', 'Clara', 'Five Guys', 'De Keyserlei', '11', '2018', 'Antwerp', 'Belgium', 'clara@burgerhub.com', 'AMERICAN', 10, 20, '10:00', '23:59'),
      ('44444444-4444-4444-4444-444444444444', '44444444-4444-4444-4444-444444444445', 'David', 'Greek Delight', 'Park Avenue', '45', '1000', 'Antwerp', 'Belgium', 'david@greendelight.com', 'GREEK', 5, 15, '09:00', '23:59');

-- Restaurant pictures
INSERT INTO restaurant.restaurant_pictures (restaurant_id, url) VALUES
                                                                    ('33333333-3333-3333-3333-333333333333', 'https://a.mktgcdn.com/p/AOeT9ChFFMHyGMC8Vm8JgxrAP2OXgVt-5kj-MnlW2_c/2000x1298.jpg'),
                                                                    ('44444444-4444-4444-4444-444444444444', 'https://example.com/greendelight1.jpg');

-- Restaurant open days
INSERT INTO restaurant.restaurant_open_days (restaurant_id, day) VALUES
                                                                     ('33333333-3333-3333-3333-333333333333', 'MONDAY'),
                                                                     ('33333333-3333-3333-3333-333333333333', 'FRIDAY'),
                                                                     ('33333333-3333-3333-3333-333333333333', 'THURSDAY'),
                                                                     ('44444444-4444-4444-4444-444444444444', 'TUESDAY'),
                                                                     ('44444444-4444-4444-4444-444444444444', 'WEDNESDAY'),
                                                                     ('44444444-4444-4444-4444-444444444444', 'THURSDAY');

-- Food menus
INSERT INTO restaurant.food_menu (restaurant_id, average_menu_price) VALUES
                                                                         ('33333333-3333-3333-3333-333333333333', 15.00),
                                                                         ('44444444-4444-4444-4444-444444444444', 12.50);

-- Dishes for Burger Hub
INSERT INTO restaurant.dish (
    id, food_menu_id, state,
    published_name, published_description, published_price, published_picture_url, published_tags, published_dish_type,
    draft_name, draft_description, draft_price, draft_picture_url, draft_tags, draft_dish_type,
    scheduled_publish_time, scheduled_to_become_state
) VALUES
      (
          'ccccccc1-cccc-cccc-cccc-ccccccccccc1',
          '33333333-3333-3333-3333-333333333333',
          'PUBLISHED',
          'Cheeseburger', 'Juicy beef burger with cheddar cheese, lettuce, and tomato', 11.50, 'https://www.braums.com/wp-content/uploads/2018/04/Cheeseburgers-1.jpg', 'gluten,meat', 'MAIN',
          NULL, NULL, NULL, NULL, NULL, NULL,
          NULL, NULL
      ),
      (
          'ccccccc2-cccc-cccc-cccc-ccccccccccc2',
          '33333333-3333-3333-3333-333333333333',
          'DRAFT',
          NULL, NULL, NULL, NULL, NULL, NULL,
          'Bacon Burger', 'Beef burger topped with crispy bacon', 13.00, 'https://example.com/baconburger.jpg', 'gluten,meat', 'MAIN',
          NULL, NULL
      );

-- Dishes for Green Delight
INSERT INTO restaurant.dish (
    id, food_menu_id, state,
    published_name, published_description, published_price, published_picture_url, published_tags, published_dish_type,
    draft_name, draft_description, draft_price, draft_picture_url, draft_tags, draft_dish_type,
    scheduled_publish_time, scheduled_to_become_state
) VALUES
      (
          'ddddddd1-dddd-dddd-dddd-ddddddddddd1',
          '44444444-4444-4444-4444-444444444444',
          'PUBLISHED',
          'Vegan Salad Bowl', 'Fresh seasonal vegetables with quinoa and avocado', 10.00, 'https://example.com/vegan_salad.jpg', 'vegan,gluten-free', 'MAIN',
          NULL, NULL, NULL, NULL, NULL, NULL,
          NULL, NULL
      ),
      (
          'ddddddd2-dddd-dddd-dddd-ddddddddddd2',
          '44444444-4444-4444-4444-444444444444',
          'DRAFT',
          NULL, NULL, NULL, NULL, NULL, NULL,
          'Smoothie Bowl', 'Berry smoothie topped with granola and seeds', 8.50, 'https://example.com/smoothie_bowl.jpg', 'vegan,gluten', 'DESSERT',
          NULL, NULL
      );

-- =====================================
-- Ordering context projections
-- =====================================

-- Restaurants
INSERT INTO ordering.restaurant_projection (
    id, name, street, number, postal_code, city, country, email_address, cuisine_type, min_prep_time, max_prep_time, opening_time, closing_time
) VALUES
      ('33333333-3333-3333-3333-333333333333', 'Five Guys', 'De Keyserlei', '11', '2018', 'Antwerp', 'Belgium', 'clara@burgerhub.com', 'AMERICAN', 10, 20, '00:00', '23:59'),
      ('44444444-4444-4444-4444-444444444444', 'Greek Delight', 'Park Avenue', '45', '1000', 'Brussels', 'Belgium', 'david@greendelight.com', 'GREEK', 5, 15, '09:00', '22:59');

-- Pictures
INSERT INTO ordering.restaurant_projection_pictures (restaurant_projection_id, url) VALUES
                                                                                        ('33333333-3333-3333-3333-333333333333', 'https://a.mktgcdn.com/p/AOeT9ChFFMHyGMC8Vm8JgxrAP2OXgVt-5kj-MnlW2_c/2000x1298.jpg'),
                                                                                        ('44444444-4444-4444-4444-444444444444', 'https://offloadmedia.feverup.com/secretmiami.com/wp-content/uploads/2023/04/13054033/meraki-1024x683.jpg');

-- Open days
INSERT INTO ordering.restaurant_projection_open_days (restaurant_projection_id, day) VALUES
                                                                                         ('33333333-3333-3333-3333-333333333333', 'MONDAY'),
                                                                                         ('33333333-3333-3333-3333-333333333333', 'FRIDAY'),
                                                                                         ('33333333-3333-3333-3333-333333333333', 'FRIDAY'),
                                                                                         ('44444444-4444-4444-4444-444444444444', 'TUESDAY'),
                                                                                         ('44444444-4444-4444-4444-444444444444', 'WEDNESDAY'),
                                                                                         ('44444444-4444-4444-4444-444444444444', 'THURSDAY');

-- Food menus
INSERT INTO ordering.food_menu_projection (restaurant_id, average_menu_price) VALUES
                                                                                  ('33333333-3333-3333-3333-333333333333', 11.50),
                                                                                  ('44444444-4444-4444-4444-444444444444', 10.00);

-- Dishes
INSERT INTO ordering.dish_projection (
    dish_id, food_menu_id, name, description, price, picture_url, tags, dish_type, dish_state
) VALUES
      ('ccccccc1-cccc-cccc-cccc-ccccccccccc1', '33333333-3333-3333-3333-333333333333', 'Cheeseburger', 'Juicy beef burger with cheddar cheese, lettuce, and tomato', 11.50, 'https://www.braums.com/wp-content/uploads/2018/04/Cheeseburgers-1.jpg', 'gluten,meat', 'MAIN', 'AVAILABLE'),
      ('ddddddd1-dddd-dddd-dddd-ddddddddddd1', '44444444-4444-4444-4444-444444444444', 'Vegan Salad Bowl', 'Fresh seasonal vegetables with quinoa and avocado', 10.00, 'https://offloadmedia.feverup.com/secretmiami.com/wp-content/uploads/2023/04/13054033/meraki-1024x683.jpg', 'vegan,gluten-free', 'MAIN', 'AVAILABLE');

-- =====================================
-- Orders for Bella Cucina
-- =====================================
SET search_path TO restaurant;

-- ===== Write model (restaurant context) =====

-- Rejected order
INSERT INTO restaurant.order_projections (
    order_id, restaurant_id, street, number, postal_code, city, country,
    total_price, placed_at, status, rejection_reason, accepted_at, ready_at, rejected_at, picked_up_at, delivered_at
) VALUES (
             'c1a11111-aaaa-aaaa-aaaa-aaaaaaaaaaa1',
             '11111111-1111-1111-1111-111111111111',
             'Rue de la Loi', '25', '1000', 'Brussels', 'Belgium',
             27.00,
             '2025-10-25 18:45:00',
             'REJECTED',
             'Restaurant was closing soon and could not accept the order.',
             NULL,
             NULL,
             '2025-10-25 18:50:00',
             NULL,
             NULL
         );

-- Delivered order
INSERT INTO restaurant.order_projections (
    order_id, restaurant_id, street, number, postal_code, city, country,
    total_price, placed_at, status, rejection_reason, accepted_at, ready_at, rejected_at, picked_up_at, delivered_at
) VALUES (
             'c2b22222-bbbb-bbbb-bbbb-bbbbbbbbbbb2',
             '11111111-1111-1111-1111-111111111111',
             'Main Street', '42', '2000', 'Antwerp', 'Belgium',
             33.50,
             '2025-10-24 19:10:00',
             'READY',
             NULL,
             '2025-10-24 19:12:00',
             '2025-10-24 19:40:00',
             NULL,
             '2025-10-24 19:50:00',
             '2025-10-24 20:15:00'
         );

-- Order lines (write model)
INSERT INTO restaurant.order_projection_lines (order_id, dish_id, dish_name, quantity, unit_price, line_price) VALUES
                                                                                                                   ('c1a11111-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 'Margherita Pizza', 1, 14.50, 14.50),
                                                                                                                   ('c1a11111-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa2', 'Spaghetti Carbonara', 1, 12.50, 12.50),

                                                                                                                   ('c2b22222-bbbb-bbbb-bbbb-bbbbbbbbbbb2', 'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa2', 'Spaghetti Carbonara', 2, 12.50, 25.00),
                                                                                                                   ('c2b22222-bbbb-bbbb-bbbb-bbbbbbbbbbb2', 'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 'Margherita Pizza', 1, 14.50, 14.50);

-- =====================================
-- Ordering Context (read model)
-- =====================================
SET search_path TO ordering;

-- Rejected order
INSERT INTO ordering.orders (
    id, restaurant_id, customer_name, customer_email,
    street, number, postal_code, city, country,
    total_price, status, rejection_reason,
    placed_at, accepted_at, rejected_at, ready_at, picked_up_at, delivered_at,
    estimated_delivery_minutes, payment_session_id
) VALUES (
             'c1a11111-aaaa-aaaa-aaaa-aaaaaaaaaaa1',
             '11111111-1111-1111-1111-111111111111',
             'Emma Dupont', 'emma.dupont@example.com',
             'Rue de la Loi', '25', '1000', 'Brussels', 'Belgium',
             27.00,
             'REJECTED',
             'Restaurant was closing soon and could not accept the order.',
             '2025-10-25 18:45:00',
             NULL,
             '2025-10-25 18:50:00',
             NULL,
             NULL,
             NULL,
             10,
             'sess_reject_bella_1'
         );

-- Delivered order
INSERT INTO ordering.orders (
    id, restaurant_id, customer_name, customer_email,
    street, number, postal_code, city, country,
    total_price, status, rejection_reason,
    placed_at, accepted_at, rejected_at, ready_at, picked_up_at, delivered_at,
    estimated_delivery_minutes, payment_session_id
) VALUES (
             'c2b22222-bbbb-bbbb-bbbb-bbbbbbbbbbb2',
             '11111111-1111-1111-1111-111111111111',
             'Lucas Janssens', 'lucas.janssens@example.com',
             'Main Street', '42', '2000', 'Antwerp', 'Belgium',
             33.50,
             'READY',
             NULL,
             '2025-10-24 19:10:00',
             '2025-10-24 19:12:00',
             NULL,
             '2025-10-24 19:40:00',
             '2025-10-24 19:50:00',
             '2025-10-24 20:15:00',
             35,
             'sess_delivered_bella_1'
         );

-- Order lines (read model)
INSERT INTO ordering.order_lines (order_id, dish_id, dish_name, quantity, unit_price, line_price) VALUES
                                                                                                      ('c1a11111-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 'Margherita Pizza', 1, 14.50, 14.50),
                                                                                                      ('c1a11111-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa2', 'Spaghetti Carbonara', 1, 12.50, 12.50),

                                                                                                      ('c2b22222-bbbb-bbbb-bbbb-bbbbbbbbbbb2', 'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa2', 'Spaghetti Carbonara', 2, 12.50, 25.00),
                                                                                                      ('c2b22222-bbbb-bbbb-bbbb-bbbbbbbbbbb2', 'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 'Margherita Pizza', 1, 14.50, 14.50);

-- Optional: courier tracking for delivered order
INSERT INTO ordering.courier_locations (order_id, timestamp, latitude, longitude) VALUES
                                                                                      ('c2b22222-bbbb-bbbb-bbbb-bbbbbbbbbbb2', '2025-10-24 19:55:00', 51.2194, 4.4025),
                                                                                      ('c2b22222-bbbb-bbbb-bbbb-bbbbbbbbbbb2', '2025-10-24 20:05:00', 51.2208, 4.4101),
                                                                                      ('c2b22222-bbbb-bbbb-bbbb-bbbbbbbbbbb2', '2025-10-24 20:12:00', 51.2213, 4.4177);