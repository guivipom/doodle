CREATE TABLE time_slots (
                            id         BIGSERIAL       PRIMARY KEY,
                            user_id    BIGINT          NOT NULL REFERENCES users(id),
                            start_time TIMESTAMP       NOT NULL,
                            end_time   TIMESTAMP       NOT NULL,
                            status     VARCHAR(10)     NOT NULL DEFAULT 'FREE',
                            created_at TIMESTAMP       NOT NULL DEFAULT now(),
                            CONSTRAINT chk_end_after_start CHECK (end_time > start_time)
);

CREATE INDEX idx_time_slots_user_id ON time_slots(user_id);
CREATE INDEX idx_time_slots_user_status ON time_slots(user_id, status);

