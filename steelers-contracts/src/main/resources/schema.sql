-- MySQL 8 + H2(MySQL mode) compatible, idempotent schema setup.
-- Spring runs this on startup when SPRING_SQL_INIT_MODE=always.

-- Drop in child-to-parent order so FK constraints don't block teardown.
DROP TABLE IF EXISTS contracts;
DROP TABLE IF EXISTS players;
DROP TABLE IF EXISTS positions;
DROP TABLE IF EXISTS teams;

CREATE TABLE IF NOT EXISTS teams (
    team_id BIGINT NOT NULL AUTO_INCREMENT,
    team_name VARCHAR(100) NOT NULL,
    city VARCHAR(100) NOT NULL,
    abbreviation VARCHAR(10) NOT NULL,
    PRIMARY KEY (team_id)
);

CREATE TABLE IF NOT EXISTS positions (
    position_id BIGINT NOT NULL AUTO_INCREMENT,
    position_name VARCHAR(50) NOT NULL,
    position_group VARCHAR(50) NOT NULL,
    PRIMARY KEY (position_id)
);

CREATE TABLE IF NOT EXISTS players (
    player_id BIGINT NOT NULL AUTO_INCREMENT,
    team_id BIGINT NOT NULL,
    position_id BIGINT NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    age INT NOT NULL,
    jersey_number INT,
    active_status BOOLEAN NOT NULL,
    PRIMARY KEY (player_id),
    CONSTRAINT fk_player_team FOREIGN KEY (team_id) REFERENCES teams(team_id),
    CONSTRAINT fk_player_position FOREIGN KEY (position_id) REFERENCES positions(position_id)
);

CREATE TABLE IF NOT EXISTS contracts (
    contract_id BIGINT NOT NULL AUTO_INCREMENT,
    player_id BIGINT NOT NULL,
    start_year INT NOT NULL,
    end_year INT NOT NULL,
    -- Increased precision so contract demo edits can't fail due to DB column overflow.
    -- Still MySQL-friendly, since DECIMAL(M,2) maps directly across engines.
    base_salary DECIMAL(20, 2) NOT NULL,
    signing_bonus DECIMAL(20, 2) NOT NULL,
    cap_hit DECIMAL(20, 2) NOT NULL,
    guaranteed_money DECIMAL(20, 2) NOT NULL,
    contract_status VARCHAR(25) NOT NULL,
    PRIMARY KEY (contract_id),
    CONSTRAINT fk_contract_player FOREIGN KEY (player_id) REFERENCES players(player_id)
);

-- Stage 3 indexes: accelerate report filters, joins, dropdowns, and CRUD lookups.
CREATE INDEX idx_players_team_id ON players(team_id);
CREATE INDEX idx_players_position_id ON players(position_id);
CREATE INDEX idx_players_last_first_name ON players(last_name, first_name);
CREATE INDEX idx_players_age ON players(age);

CREATE INDEX idx_contracts_player_id ON contracts(player_id);
CREATE INDEX idx_contracts_status ON contracts(contract_status);
CREATE INDEX idx_contracts_cap_hit ON contracts(cap_hit);
CREATE INDEX idx_contracts_player_years ON contracts(player_id, start_year, end_year);
