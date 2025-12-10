-- V3: Initial test data

-- Insert Roles
INSERT INTO roles (name, description) VALUES
                                          ('ADMIN', 'Administrator with full system access'),
                                          ('ANALYST', 'Analyst who evaluates credit applications'),
                                          ('MEMBER', 'Member who can apply for credits');

-- Insert test Users
-- Password: "password123" encrypted with BCrypt
-- You can generate passwords at: https://bcrypt-generator.com/
INSERT INTO users (username, password, email, enabled) VALUES
                                                           ('admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'admin@coopcredit.com', true),
                                                           ('analyst', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'analyst1@coopcredit.com', true),
                                                           ('lanamember', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'member1@coopcredit.com', true),
                                                           ('member', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'member2@coopcredit.com', true);

-- Assign roles to users
INSERT INTO user_roles (user_id, role_id) VALUES
                                              (1, 1), -- admin has ROLE_ADMIN
                                              (2, 2), -- analyst1 has ROLE_ANALYST
                                              (3, 3), -- member1 has ROLE_MEMBER
                                              (4, 3); -- member2 has ROLE_MEMBER

-- Insert test Members
INSERT INTO members (document, name, salary, membership_date, status, user_id) VALUES
                                                                                   ('1017654321', 'Juan Perez Garcia', 3500000.00, '2023-01-15', 'ACTIVE', 3),
                                                                                   ('1098765432', 'Maria Lopez Rodriguez', 4200000.00, '2023-03-20', 'ACTIVE', 4),
                                                                                   ('1234567890', 'Carlos Martinez Sanchez', 2800000.00, '2024-06-10', 'ACTIVE', NULL),
                                                                                   ('9876543210', 'Ana Gomez Torres', 5000000.00, '2022-11-05', 'INACTIVE', NULL);

-- Insert test Credit Applications
INSERT INTO credit_applications (member_id, requested_amount, term_months, proposed_rate, application_date, status) VALUES
                                                                                                                        (1, 5000000.00, 36, 12.5, '2024-12-01 10:30:00', 'PENDING'),
                                                                                                                        (2, 8000000.00, 48, 14.0, '2024-12-05 14:15:00', 'PENDING'),
                                                                                                                        (3, 3000000.00, 24, 10.0, '2024-12-08 09:00:00', 'APPROVED');

-- Insert a sample Risk Evaluation
INSERT INTO risk_evaluations
(application_id, score, risk_level, debt_to_income_ratio, meets_seniority_requirement, meets_maximum_amount, approved, rejection_reason, details, evaluation_date)
VALUES
    (3, 720, 'LOW', 28.50, true, true, true, NULL, 'Successful evaluation. Excellent credit history.', '2024-12-08 10:00:00');

-- Update the evaluated application status
UPDATE credit_applications SET status = 'APPROVED' WHERE id = 3;

-- Data verification
SELECT 'Initial data inserted successfully' AS status;
SELECT COUNT(*) AS total_users FROM users;
SELECT COUNT(*) AS total_roles FROM roles;
SELECT COUNT(*) AS total_members FROM members;
SELECT COUNT(*) AS total_applications FROM credit_applications;
SELECT COUNT(*) AS total_evaluations FROM risk_evaluations;
