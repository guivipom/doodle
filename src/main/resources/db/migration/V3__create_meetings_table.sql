CREATE TABLE meetings (
    id          BIGSERIAL    PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    description TEXT,
    slot_id     BIGINT       NOT NULL UNIQUE REFERENCES time_slots(id),
    organizer_id BIGINT      NOT NULL REFERENCES users(id),
    created_at  TIMESTAMP    NOT NULL DEFAULT now()
);

CREATE TABLE meeting_participants (
    meeting_id BIGINT NOT NULL REFERENCES meetings(id) ON DELETE CASCADE,
    user_id    BIGINT NOT NULL REFERENCES users(id),
    PRIMARY KEY (meeting_id, user_id)
);
