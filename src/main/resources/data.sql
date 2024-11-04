INSERT INTO design_records (record_id, design_staff_id, customer_id, drawing_file, customer_feedback, creation_date, update_date, engineer_notes)
VALUES
    (1, 101, 201, 'floor_plan_v1.dwg', 'Looks great, but please adjust the dimensions of room 2.', '2024-10-01', '2024-10-10', 'Room 2 dimensions updated as per feedback'),
    (2, 102, 202, 'house_blueprint_v2.dwg', 'Satisfied with the design.', '2024-10-05', NULL, 'Design approved by customer'),
    (3, 103, 203, 'office_layout_v3.dwg', 'Add more space for conference rooms.', '2024-10-12', '2024-10-15', 'Additional space allocated for conference rooms'),
    (4, 101, 204, 'garden_design_v1.dwg', NULL, '2024-10-20', NULL, 'Initial draft sent for review'),
    (5, 102, 205, 'warehouse_layout_v1.dwg', 'Please add more loading docks.', '2024-10-25', '2024-10-28', 'Added extra loading docks as requested');

INSERT INTO users (user_id, username, email, phone, password, role_id, created_date, otp_code, dob, status)
VALUES
    (101, 'jdoe', 'jdoe@example.com', '123-456-7890', 'password123', 1, '2024-01-15', '123456', '1990-02-20', 'active'),
    (102, 'asmith', 'asmith@example.com', '234-567-8901', 'password123', 2, '2024-02-10', '654321', '1985-08-12', 'active'),
    (103, 'bwilliams', 'bwilliams@example.com', '345-678-9012', 'password123', 3, '2024-03-05', NULL, '1992-11-05', 'inactive'),
    (104, 'cdavis', 'cdavis@example.com', '456-789-0123', 'password123', 3, '2024-04-12', '789012', '1988-05-19', 'active'),
    (105, 'emiller', 'emiller@example.com', '567-890-1234', 'password123', 3, '2024-05-08', NULL, '1995-09-23', 'active');

INSERT INTO roles (role_id, name)
VALUES
    (1, 'Designer'),
    (2, 'Manager'),
    (3, 'User');

INSERT INTO ratings (rating_id, service_id, user_id, rating, feedback)
VALUES
    (1, 1001, 101, 5, 'Excellent service, very satisfied!'),
    (2, 1002, 102, 4, 'Good service, but could be improved.'),
    (3, 1003, 103, 3, 'Average experience, room for improvement.'),
    (4, 1004, 104, 5, 'Exceptional experience! Highly recommend.'),
    (5, 1005, 105, 2, 'Not satisfied, service was below expectations.');

INSERT INTO quotations (quotation_id, quotation_number, user_id, order_id, area_size, location, design_details, material_cost, labor_cost, transportation_cost, total_cost, payment_method, quotation_date, status, expiration_date)
VALUES
    (1, 'QTN-2024-001', 101, 1001, 150.5, 'Downtown', 'Modern interior design with open space', 5000.0, 2000.0, 300.0, 7300.0, 'Credit Card', '2024-10-01', 'Pending', '2024-12-01'),
    (2, 'QTN-2024-002', 102, 1002, 200.0, 'Suburbs', 'Classic style with traditional materials', 7000.0, 2500.0, 400.0, 9900.0, 'Bank Transfer', '2024-10-10', 'Approved', '2024-12-10'),
    (3, 'QTN-2024-003', 103, 1003, 180.75, 'City Center', 'Industrial design with exposed brick', 5500.0, 2200.0, 350.0, 8050.0, 'Cash', '2024-10-15', 'Pending', '2024-12-15'),
    (4, 'QTN-2024-004', 104, 1004, 250.0, 'Coastal', 'Beach-inspired design with natural materials', 8000.0, 3000.0, 500.0, 11500.0, 'Credit Card', '2024-10-20', 'Rejected', '2024-12-20'),
    (5, 'QTN-2024-005', 105, 1005, 120.0, 'Mountain View', 'Rustic cabin style', 4500.0, 1800.0, 250.0, 6550.0, 'Bank Transfer', '2024-10-25', 'Approved', '2024-12-25');

INSERT INTO orders (order_id, title, order_number, phone, design_details, user_id, address, service_type, start_date, end_date, status)
VALUES
    (1001, 'Living Room Renovation', 'ORD-2024-001', '123-456-7890', 'Modern layout with new lighting fixtures', 101, '123 Main St, Downtown', 1, '2024-09-01', '2024-09-15', 1),
    (1002, 'Office Redesign', 'ORD-2024-002', '234-567-8901', 'Open space with adjustable workstations', 102, '456 Business Rd, Suburbs', 2, '2024-09-05', '2024-09-20', 2),
    (1003, 'Kitchen Upgrade', 'ORD-2024-003', '345-678-9012', 'Install new cabinets and countertops', 103, '789 Pine St, City Center', 1, '2024-09-10', '2024-09-25', 1),
    (1004, 'Outdoor Patio Design', 'ORD-2024-004', '456-789-0123', 'Beach-inspired outdoor patio', 104, '101 Ocean Dr, Coastal', 3, '2024-09-15', '2024-09-30', 3),
    (1005, 'Basement Conversion', 'ORD-2024-005', '567-890-1234', 'Create home theater space', 105, '202 Mountain View Rd, Highlands', 1, '2024-09-20', '2024-10-05', 2);

INSERT INTO maintence (title, price, number_order, service_type, user_id, construction_staff, start_date, end_date, content)
VALUES
    ('AC Repair', '150', 'ORD-2024-001', 'Repair', 101, 'John Doe', '2024-09-05', '2024-09-06', 'Repair air conditioning unit in living room'),
    ('Plumbing Fix', '200', 'ORD-2024-002', 'Plumbing', 102, 'Jane Smith', '2024-09-07', '2024-09-08', 'Fix leaking pipes in the kitchen'),
    ('Electrical Work', '300', 'ORD-2024-003', 'Electrical', 103, 'Mike Johnson', '2024-09-10', '2024-09-11', 'Rewire office lighting system'),
    ('Roof Inspection', '100', 'ORD-2024-004', 'Inspection', 104, 'Sara Lee', '2024-09-12', '2024-09-12', 'Inspect roof for damages'),
    ('Garden Maintenance', '180', 'ORD-2024-005', 'Maintenance', 105, 'Tom Brown', '2024-09-15', '2024-09-16', 'Trim bushes and mow lawn');
