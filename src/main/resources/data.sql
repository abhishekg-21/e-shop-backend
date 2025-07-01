-- data.sql

-- IMPORTANT: No DROP TABLE or CREATE TABLE statements here.
-- Hibernate's ddl-auto=update handles schema management.
-- This file is solely for inserting initial data.


-- 4. INSERT INITIAL DATA (Users, Categories, Products - Roles handled by DataLoader)

-- USERS
-- Admin User: email: admin@eshop.com, password: password (BCrypt hash)
-- This user will be created first. The DataLoader ensures ROLE_ADMIN is there for assignment.
INSERT INTO users (id, first_name, last_name, phone_number, email, password, enabled) VALUES
('a0b1c2d3-e4f5-6a7b-8c9d-0e1f2a3b4c5d', 'Admin', 'User', '123-456-7890', 'admin@eshop.com',
'$2a$10$w2fgHHNAZ07w6Y376SpSheizQeXAb9t85bj.BYc3kbACSp4a7.XBi', TRUE)
ON CONFLICT (id) DO NOTHING;

INSERT INTO users (id, first_name, last_name, phone_number, email, password, enabled) VALUES
('f0e1d2c3-b4a5-6f7e-8d9c-0b1a2f3e4d5c', 'Regular', 'User', '987-654-3210', 'user@eshop.com',
'$2a$10$w2fgHHNAZ07w6Y376SpSheizQeXAb9t85bj.BYc3kbACSp4a7.XBi', TRUE)
ON CONFLICT (id) DO NOTHING;


INSERT INTO users_roles (user_id, role_id) VALUES 
('a0b1c2d3-e4f5-6a7b-8c9d-0e1f2a3b4c5d', (SELECT id FROM roles WHERE name = 'ROLE_ADMIN'))
ON CONFLICT (user_id, role_id) DO NOTHING;

INSERT INTO users_roles (user_id, role_id) VALUES 
('a0b1c2d3-e4f5-6a7b-8c9d-0e1f2a3b4c5d', (SELECT id FROM roles WHERE name = 'ROLE_USER'))
ON CONFLICT (user_id, role_id) DO NOTHING;

INSERT INTO users_roles (user_id, role_id) VALUES 
('f0e1d2c3-b4a5-6f7e-8d9c-0b1a2f3e4d5c', (SELECT id FROM roles WHERE name = 'ROLE_USER'))
ON CONFLICT (user_id, role_id) DO NOTHING;



INSERT INTO categories (id, name) VALUES
('cat-elec-1234-abcd-5678-efgh-9012ijklmn', 'Electronics'),
('cat-book-5678-abcd-9012-efgh-3456ijklmn', 'Books'),
('cat-appr-9012-abcd-3456-efgh-7890ijklmn', 'Apparel'),
('cat-home-1122-abcd-3344-efgh-5566ijklmn', 'Home & Kitchen')
ON CONFLICT (id) DO NOTHING;

INSERT INTO products (id, name, description, price, stock_quantity, image_url, category_id) VALUES
('prod-a1b2c3d4-e5f6-7a8b-9c0d-1e2f3a4b5c6d', 'Wireless Headphones', 'High-fidelity wireless headphones with noise-cancelling. Up to 30 hours battery life.', 199.99, 50, 'https://placehold.co/400x300/007bff/FFFFFF?text=Headphones', (SELECT id FROM categories WHERE name = 'Electronics')),
('prod-b1c2d3e4-f5a6-7b8c-9d0e-1f2a3b4c5d6e', 'The Great Adventure', 'A thrilling fantasy novel about a hero''s journey to save the world.', 25.00, 120, 'https://placehold.co/400x300/28a745/FFFFFF?text=Book+Cover', (SELECT id FROM categories WHERE name = 'Books')),
('prod-c1d2e3f4-a5b6-7c8d-9e0f-1a2b3c4d5e6f', 'Casual T-Shirt', 'Comfortable 100% cotton casual t-shirt, perfect for everyday wear. Available in multiple sizes.', 15.99, 200, 'https://placehold.co/400x300/6c757d/FFFFFF?text=T-Shirt', (SELECT id FROM categories WHERE name = 'Apparel')),
('prod-d1e2f3a4-b5c6-7d8e-9f0a-1b2c3d4e5f6a', 'Smart LED TV 55"', 'Stunning 4K Ultra HD Smart TV with vibrant colors and advanced smart features. Built-in streaming.', 799.99, 15, 'https://placehold.co/400x300/dc3545/FFFFFF?text=Smart+TV', (SELECT id FROM categories WHERE name = 'Electronics')),
('prod-e1f2a3b4-c5d6-7e8f-9a0b-1c2d3e4f5a6b', 'Python Programming Guide', 'An comprehensive guide to Python programming, from basics to advanced topics. Includes exercises.', 45.50, 80, 'https://placehold.co/400x300/17a2b8/FFFFFF?text=Python+Book', (SELECT id FROM categories WHERE name = 'Books')),
('prod-f1a2b3c4-d5e6-7f8a-9b0c-1d2e3f4a5b6c', 'Denim Jeans', 'Classic fit denim jeans, durable and stylish for any occasion. Various washes available.', 59.99, 100, 'https://placehold.co/400x300/ffc107/FFFFFF?text=Denim+Jeans', (SELECT id FROM categories WHERE name = 'Apparel')),
('prod-g1a2b3c4-d5e6-7f8a-9b0c-1d2e3f4a5b6d', 'Coffee Maker', 'Programmable coffee maker with a 12-cup capacity. Brews fresh coffee quickly.', 49.99, 75, 'https://placehold.co/400x300/20c997/FFFFFF?text=Coffee+Maker', (SELECT id FROM categories WHERE name = 'Home & Kitchen')),
('prod-h1a2b3c4-d5e6-7f8a-9b0c-1d2e3f4a5b6e', 'Blender', 'High-speed blender perfect for smoothies, soups, and more. Easy to clean.', 75.00, 60, 'https://placehold.co/400x300/6610f2/FFFFFF?text=Blender', (SELECT id FROM categories WHERE name = 'Home & Kitchen')),
('prod-i1a2b3c4-d5e6-7f8a-9b0c-1d2e3f4a5b6f', 'Gaming Laptop', 'Powerful gaming laptop with latest GPU, 16GB RAM, and high refresh rate display.', 1499.99, 10, 'https://placehold.co/400x300/0d6efd/FFFFFF?text=Gaming+Laptop', (SELECT id FROM categories WHERE name = 'Electronics')),
('prod-j1a2b3c4-d5e6-7f8a-9b0c-1d2e3f4a5b6g', 'Smartphone', 'Next-gen smartphone with advanced camera, long-lasting battery, and edge-to-edge display.', 899.99, 30, 'https://placehold.co/400x300/6f42c1/FFFFFF?text=Smartphone', (SELECT id FROM categories WHERE name = 'Electronics'))
ON CONFLICT (id) DO NOTHING;
