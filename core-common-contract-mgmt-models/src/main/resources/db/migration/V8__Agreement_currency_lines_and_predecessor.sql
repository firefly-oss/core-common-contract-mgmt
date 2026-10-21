-- Agreement currency, more than one product, and a predecessor link for amendment or renewal.

CREATE TYPE contract_link_kind_enum AS ENUM (
    'AMENDMENT',
    'RENEWAL'
);

CREATE CAST (varchar AS contract_link_kind_enum) WITH INOUT AS IMPLICIT;

ALTER TABLE contract
    ADD COLUMN currency_code VARCHAR(3),
    ADD COLUMN predecessor_contract_id UUID,
    ADD COLUMN link_kind contract_link_kind_enum;

ALTER TABLE contract
    ADD CONSTRAINT fk_contract_predecessor
        FOREIGN KEY (predecessor_contract_id) REFERENCES contract (contract_id);

ALTER TABLE contract
    ADD CONSTRAINT chk_contract_currency
        CHECK (currency_code IS NULL OR currency_code ~ '^[A-Z]{3}$');

ALTER TABLE contract
    ADD CONSTRAINT chk_contract_not_self_predecessor
        CHECK (predecessor_contract_id IS NULL OR predecessor_contract_id <> contract_id);

ALTER TABLE contract
    ADD CONSTRAINT chk_contract_predecessor_link
        CHECK (
            (predecessor_contract_id IS NULL AND link_kind IS NULL)
            OR (predecessor_contract_id IS NOT NULL AND link_kind IS NOT NULL)
        );

CREATE INDEX idx_contract_predecessor ON contract (predecessor_contract_id);

COMMENT ON COLUMN contract.currency_code IS 'ISO 4217 currency of the agreement.';
COMMENT ON COLUMN contract.predecessor_contract_id IS 'Contract this agreement amends or renews.';
COMMENT ON COLUMN contract.link_kind IS 'AMENDMENT or RENEWAL when a predecessor is set.';

CREATE TABLE contract_line (
    contract_line_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    contract_id UUID NOT NULL,
    product_id UUID NOT NULL,
    line_role VARCHAR(50),
    quantity NUMERIC(18, 4),
    effective_from TIMESTAMP,
    effective_to TIMESTAMP,
    is_primary BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_contract_line_contract
        FOREIGN KEY (contract_id) REFERENCES contract (contract_id) ON DELETE CASCADE,
    CONSTRAINT chk_contract_line_quantity
        CHECK (quantity IS NULL OR quantity >= 0),
    CONSTRAINT chk_contract_line_dates
        CHECK (effective_to IS NULL OR effective_from IS NULL OR effective_to >= effective_from)
);

CREATE UNIQUE INDEX uq_contract_line_one_primary
    ON contract_line (contract_id) WHERE is_primary;
CREATE INDEX idx_contract_line_contract ON contract_line (contract_id);
CREATE INDEX idx_contract_line_product ON contract_line (product_id);

COMMENT ON TABLE contract_line IS
    'Products on an agreement. contract.product_id mirrors the primary line, or the first line when none is marked primary.';
COMMENT ON COLUMN contract_line.line_role IS 'Optional role of this product on the agreement.';

INSERT INTO contract_line (contract_id, product_id, is_primary)
SELECT contract_id, product_id, TRUE
FROM contract
WHERE product_id IS NOT NULL;
