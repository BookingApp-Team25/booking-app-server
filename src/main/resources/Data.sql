INSERT INTO users (id, username, password, blocked) VALUES
('123e4567-e89b-12d3-a456-426614174001', 'Saste', 'password1', false),
('123e4567-e89b-12d3-a456-426614174002', 'MirSalve', 'password2', false),
('123e4567-e89b-12d3-a456-426614174003', 'Vosa', 'password3', false);

INSERT INTO guest (id) VALUES
('123e4567-e89b-12d3-a456-426614174001'),
('123e4567-e89b-12d3-a456-426614174002');

INSERT INTO hosts (id) VALUES
('123e4567-e89b-12d3-a456-426614174003');

--------------------------------------------
INSERT INTO accommodation_reserved_dates (id)
VALUES
('323e4567-e89b-12d3-a456-426614174001'),
('423e4567-e89b-12d3-a456-426614174001'),
('523e4567-e89b-12d3-a456-426614174001');

INSERT INTO location (id, street_number, street, city, country)
VALUES
('423e4567-e89b-12d3-a456-426614174001', '123', 'Main Street', 'Cityville', 'Countryland'),
('523e4567-e89b-12d3-a456-426614174001', '456', 'Oak Avenue', 'Townsville', 'Countryland'),
('623e4567-e89b-12d3-a456-426614174001', '789', 'Mountain Road', 'Hilltop', 'Countryland');

INSERT INTO accommodation_pricelist (id, daily_price, summer_price, weekend_price, winter_price, currency)
VALUES
('523e4567-e89b-12d3-a456-426614174001', 100.0, 120.0, 110.0, 90.0, 'USD'),
('623e4567-e89b-12d3-a456-426614174001', 150.0, 180.0, 160.0, 130.0, 'EUR'),
('723e4567-e89b-12d3-a456-426614174001', 200.0, 240.0, 210.0, 180.0, 'GBP');

-- Accommodation 1
INSERT INTO accommodation (id, name, description, location_id, min_guests, max_guests, type, availability_id, pricelist_id, price, days_before, policy, on_hold_status, average_rating)
VALUES
    ('123e4567-e89b-12d3-a456-426614174001', 'Cozy Studio', 'A small and comfortable studio in the city center', '423e4567-e89b-12d3-a456-426614174001', 1, 2, 'Studio', '323e4567-e89b-12d3-a456-426614174001', '523e4567-e89b-12d3-a456-426614174001', 80.0, 30, 'Manually', 'APPROVED', 0.0);

-- Accommodation 2
INSERT INTO accommodation (id, name, description, location_id, min_guests, max_guests, type, availability_id, pricelist_id, price, days_before, policy, on_hold_status, average_rating)
VALUES
    ('223e4567-e89b-12d3-a456-426614174001', 'Spacious Apartment', 'A large apartment with a beautiful view', '523e4567-e89b-12d3-a456-426614174001', 2, 4, 'Apartment', '423e4567-e89b-12d3-a456-426614174001', '623e4567-e89b-12d3-a456-426614174001', 120.0, 45, 'Auto', 'WAITING_FOR_CREATE_APPROVAL', 0.0);

-- Accommodation 3
INSERT INTO accommodation (id, name, description, location_id, min_guests, max_guests, type, availability_id, pricelist_id, price, days_before, policy, on_hold_status, average_rating)
VALUES
    ('323e4567-e89b-12d3-a456-426614174001', 'Mountain Retreat', 'A peaceful retreat in the mountains', '623e4567-e89b-12d3-a456-426614174001', 2, 6, 'House', '523e4567-e89b-12d3-a456-426614174001', '723e4567-e89b-12d3-a456-426614174001', 180.0, 60, 'Manually', 'WAITING_FOR_EDIT_APPROVAL', 0.0);

---------------------------------------------
-- DatePeriod 1,2,3
INSERT INTO date_period (id, start_date, end_date)
VALUES
    ('423e4567-e89b-12d3-a456-426614174001', '2023-01-01', '2023-01-10'),
    ('523e4567-e89b-12d3-a456-426614174001', '2023-02-15', '2023-02-20'),
    ('623e4567-e89b-12d3-a456-426614174001', '2023-03-10', '2023-03-20');


-- Reservation 1
INSERT INTO reservation (id, guest_id, host_id, accommodation_id, reservation_status, reserved_date_id)
VALUES
    ('423e4567-e89b-12d3-a456-426614174001', '823e4567-e89b-12d3-a456-426614174001', '923e4567-e89b-12d3-a456-426614174001', '123e4567-e89b-12d3-a456-426614174001', 'PENDING', '423e4567-e89b-12d3-a456-426614174001');

-- Reservation 2
INSERT INTO reservation (id, guest_id, host_id, accommodation_id, reservation_status, reserved_date_id)
VALUES
    ('523e4567-e89b-12d3-a456-426614174001', '723e4567-e89b-12d3-a456-426614174001', '823e4567-e89b-12d3-a456-426614174001', '223e4567-e89b-12d3-a456-426614174001', 'APPROVED', '523e4567-e89b-12d3-a456-426614174001');

-- Reservation 3
INSERT INTO reservation (id, guest_id, host_id, accommodation_id, reservation_status, reserved_date_id)
VALUES
    ('623e4567-e89b-12d3-a456-426614174001', '523e4567-e89b-12d3-a456-426614174001', '623e4567-e89b-12d3-a456-426614174001', '323e4567-e89b-12d3-a456-426614174001', 'REJECTED', '623e4567-e89b-12d3-a456-426614174001');
