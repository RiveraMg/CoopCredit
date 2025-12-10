-- =====================================================
-- CoopCredit - Comprehensive Credit Application System
-- V2: Additional constraints and relationships
-- =====================================================

-- Add constraint to validate that membership date is not in the future
ALTER TABLE members
    ADD CONSTRAINT chk_membership_date_not_future
        CHECK (membership_date <= CURRENT_DATE);

-- Add constraint to validate that application date is not in the future
ALTER TABLE credit_applications
    ADD CONSTRAINT chk_application_date_not_future
        CHECK (application_date <= CURRENT_TIMESTAMP);

-- Add constraint to validate that term is between 6 and 120 months
ALTER TABLE credit_applications
    ADD CONSTRAINT chk_term_valid_range
        CHECK (term_months >= 6 AND term_months <= 120);

-- Add constraint to validate that rate does not exceed 50%
ALTER TABLE credit_applications
    ADD CONSTRAINT chk_maximum_rate
        CHECK (proposed_rate <= 50.0);

-- Function to automatically update the updated_at field
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
RETURN NEW;
END;
$$ language 'plpgsql';

-- Triggers to automatically update updated_at
CREATE TRIGGER update_users_updated_at
    BEFORE UPDATE ON users
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_members_updated_at
    BEFORE UPDATE ON members
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_applications_updated_at
    BEFORE UPDATE ON credit_applications
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

-- View for frequent queries
CREATE OR REPLACE VIEW v_complete_applications AS
SELECT
    ca.id AS application_id,
    ca.requested_amount,
    ca.term_months,
    ca.proposed_rate,
    ca.application_date,
    ca.status AS application_status,
    m.id AS member_id,
    m.document,
    m.name,
    m.salary,
    m.status AS member_status,
    re.score,
    re.risk_level,
    re.approved,
    re.rejection_reason,
    re.evaluation_date
FROM credit_applications ca
         INNER JOIN members m ON ca.member_id = m.id
         LEFT JOIN risk_evaluations re ON ca.id = re.application_id;

COMMENT ON VIEW v_complete_applications IS 'View with complete application information';