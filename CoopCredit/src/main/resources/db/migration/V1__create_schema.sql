-- V1: Base schema creation

-- Users table (for authentication)
CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       enabled BOOLEAN DEFAULT TRUE,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Roles table
CREATE TABLE roles (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(50) NOT NULL UNIQUE,
                       description VARCHAR(255),
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- User-Role junction table (Many-to-Many)
CREATE TABLE user_roles (
                            user_id BIGINT NOT NULL,
                            role_id BIGINT NOT NULL,
                            PRIMARY KEY (user_id, role_id),
                            CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                            CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- Members table (afiliados)
CREATE TABLE members (
                         id BIGSERIAL PRIMARY KEY,
                         document VARCHAR(20) NOT NULL UNIQUE,
                         name VARCHAR(100) NOT NULL,
                         salary DECIMAL(15, 2) NOT NULL CHECK (salary > 0),
                         membership_date DATE NOT NULL,
                         status VARCHAR(20) NOT NULL CHECK (status IN ('ACTIVE', 'INACTIVE')),
                         user_id BIGINT,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         CONSTRAINT fk_member_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL
);

-- Credit applications table
CREATE TABLE credit_applications (
                                     id BIGSERIAL PRIMARY KEY,
                                     member_id BIGINT NOT NULL,
                                     requested_amount DECIMAL(15, 2) NOT NULL CHECK (requested_amount > 0),
                                     term_months INTEGER NOT NULL CHECK (term_months > 0),
                                     proposed_rate DECIMAL(5, 2) NOT NULL CHECK (proposed_rate >= 0),
                                     application_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                     status VARCHAR(20) NOT NULL CHECK (status IN ('PENDING', 'APPROVED', 'REJECTED')),
                                     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     CONSTRAINT fk_application_member FOREIGN KEY (member_id) REFERENCES members(id) ON DELETE CASCADE
);

-- Risk evaluation table
CREATE TABLE risk_evaluations (
                                  id BIGSERIAL PRIMARY KEY,
                                  application_id BIGINT NOT NULL UNIQUE,
                                  score INTEGER NOT NULL CHECK (score >= 300 AND score <= 950),
                                  risk_level VARCHAR(20) NOT NULL CHECK (risk_level IN ('HIGH', 'MEDIUM', 'LOW')),
                                  debt_to_income_ratio DECIMAL(5, 2),
                                  meets_seniority_requirement BOOLEAN NOT NULL,
                                  meets_maximum_amount BOOLEAN NOT NULL,
                                  approved BOOLEAN NOT NULL,
                                  rejection_reason TEXT,
                                  details TEXT,
                                  evaluation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                  CONSTRAINT fk_evaluation_application FOREIGN KEY (application_id) REFERENCES credit_applications(id) ON DELETE CASCADE
);

-- Indexes for performance improvement
CREATE INDEX idx_members_document ON members(document);
CREATE INDEX idx_members_status ON members(status);
CREATE INDEX idx_members_user_id ON members(user_id);
CREATE INDEX idx_applications_member_id ON credit_applications(member_id);
CREATE INDEX idx_applications_status ON credit_applications(status);
CREATE INDEX idx_applications_date ON credit_applications(application_date);
CREATE INDEX idx_evaluations_application_id ON risk_evaluations(application_id);
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_email ON users(email);

-- Table comments
COMMENT ON TABLE users IS 'System users for authentication';
COMMENT ON TABLE roles IS 'System roles (ROLE_MEMBER, ROLE_ANALYST, ROLE_ADMIN)';
COMMENT ON TABLE members IS 'CoopCredit cooperative members';
COMMENT ON TABLE credit_applications IS 'Credit applications submitted by members';
COMMENT ON TABLE risk_evaluations IS 'Risk evaluations of credit applications';