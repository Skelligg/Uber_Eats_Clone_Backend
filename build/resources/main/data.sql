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
                                                                    ('11111111-1111-1111-1111-111111111111', 'https://example.com/bella1.jpg'),
                                                                    ('11111111-1111-1111-1111-111111111111', 'https://example.com/bella2.jpg'),
                                                                    ('22222222-2222-2222-2222-222222222222', 'https://example.com/sushi1.jpg');

-- Restaurant open days
INSERT INTO restaurant.restaurant_open_days (restaurant_id, day) VALUES
                                                                     ('11111111-1111-1111-1111-111111111111', 'Monday'),
                                                                     ('11111111-1111-1111-1111-111111111111', 'Tuesday'),
                                                                     ('22222222-2222-2222-2222-222222222222', 'Monday'),
                                                                     ('22222222-2222-2222-2222-222222222222', 'Wednesday');

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
          'Spaghetti Carbonara', 'Classic Italian pasta with bacon and cheese', 12.50, 'https://www.twopeasandtheirpod.com/wp-content/uploads/2023/01/Spaghetti-Carbonara168787.jpg', 'gluten', 'MAIN',
          'Spaghetti Carbonara REVISED', 'Classic Italian pasta with bacon and cheese', 15.00, 'https://www.twopeasandtheirpod.com/wp-content/uploads/2023/01/Spaghetti-Carbonara168787.jpg', 'gluten', 'MAIN',
          NULL, NULL
      ),
      (
          'aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa2',
          '11111111-1111-1111-1111-111111111111',
          'DRAFT',
          NULL, NULL, NULL, NULL, NULL, NULL,
          'Tiramisu', 'Coffee-flavored Italian dessert', 6.50, 'https://example.com/tiramisu.jpg', 'gluten, dairy', 'DESSERT',
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
          'Salmon Nigiri', 'Fresh salmon over rice', 4.50, 'https://example.com/nigiri.jpg', 'gluten-free', 'MAIN',
          NULL, NULL
      ),
      (
          'bbbbbbb2-bbbb-bbbb-bbbb-bbbbbbbbbbb2',
          '22222222-2222-2222-2222-222222222222',
          'PUBLISHED',
          'Miso Soup', 'Traditional Japanese soup', 3.00, 'https://example.com/miso.jpg', 'vegan', 'STARTER',
          NULL, NULL, NULL, NULL, NULL, NULL,
          NULL, NULL
      );

-- =====================================
-- Dummy data for ordering context (read model)
-- =====================================
SET search_path TO restaurant, ordering;

-- Restaurants
INSERT INTO ordering.restaurant_projection (
    id, owner_id, owner_name, name, street, number, postal_code, city, country, email_address, cuisine_type, min_prep_time, max_prep_time, opening_time, closing_time
) VALUES
      ('11111111-1111-1111-1111-111111111111', '11111111-1111-1111-1111-111111111112', 'Alice', 'Bella Cucina', 'Main Street', '12', '1000', 'Brussels', 'Belgium', 'alice@bella.com', 'ITALIAN', 15, 30, '11:00', '22:00'),
      ('22222222-2222-2222-2222-222222222222', '22222222-2222-2222-2222-222222222223', 'Bob', 'Sushi Palace', 'Market Street', '5', '2000', 'Antwerp', 'Belgium', 'bob@sushi.com', 'JAPANESE', 10, 25, '12:00', '21:00');

-- Pictures
INSERT INTO ordering.restaurant_projection_pictures (restaurant_projection_id, url) VALUES
                                                                                        ('11111111-1111-1111-1111-111111111111', 'https://example.com/bella1.jpg'),
                                                                                        ('22222222-2222-2222-2222-222222222222', 'https://example.com/sushi1.jpg');

-- Open days
INSERT INTO ordering.restaurant_projection_open_days (restaurant_projection_id, day) VALUES
                                                                                         ('11111111-1111-1111-1111-111111111111', 'Monday'),
                                                                                         ('11111111-1111-1111-1111-111111111111', 'Tuesday'),
                                                                                         ('22222222-2222-2222-2222-222222222222', 'Monday'),
                                                                                         ('22222222-2222-2222-2222-222222222222', 'Wednesday');

-- Food menus
INSERT INTO ordering.food_menu_projection (restaurant_id, average_menu_price) VALUES
                                                                                  ('11111111-1111-1111-1111-111111111111', 20.00),
                                                                                  ('22222222-2222-2222-2222-222222222222', 25.00);

-- Dishes
INSERT INTO ordering.dish_projection (
    dish_id, food_menu_id, name, description, price, picture_url, tags, dish_type, dish_state
) VALUES
      ('aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', '11111111-1111-1111-1111-111111111111', 'Spaghetti Carbonara', 'Classic Italian pasta with bacon and cheese', 12.50, 'https://example.com/carbonara.jpg', 'gluten', 'main', 'AVAILABLE'),
      ('bbbbbbb2-bbbb-bbbb-bbbb-bbbbbbbbbbb2', '22222222-2222-2222-2222-222222222222', 'Miso Soup', 'Traditional Japanese soup', 3.00, 'https://example.com/miso.jpg', 'vegan', 'starter', 'AVAILABLE');
