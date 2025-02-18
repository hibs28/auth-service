CREATE TABLE address (
    address_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    street_address VARCHAR(255) NOT NULL,
    city VARCHAR(255) UNIQUE NOT NULL,
    postal_code VARCHAR(255) NOT NULL,
    country VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE address ADD FOREIGN KEY (user_id) REFERENCES users;