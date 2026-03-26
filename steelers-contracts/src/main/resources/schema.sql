CREATE TABLE teams (
    team_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    team_name VARCHAR(100) NOT NULL,
    city VARCHAR(100) NOT NULL,
    abbreviation VARCHAR(10) NOT NULL
);

CREATE TABLE positions (
    position_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    position_name VARCHAR(50) NOT NULL,
    position_group VARCHAR(50) NOT NULL
);

CREATE TABLE players (
    player_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    team_id BIGINT NOT NULL,
    position_id BIGINT NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    age INT NOT NULL,
    jersey_number INT,
    active_status BOOLEAN NOT NULL,
    CONSTRAINT fk_player_team FOREIGN KEY (team_id) REFERENCES teams(team_id),
    CONSTRAINT fk_player_position FOREIGN KEY (position_id) REFERENCES positions(position_id)
);

CREATE TABLE contracts (
    contract_id BIGINT PRIMARY KEY AUTO_INCREMENT,
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
    CONSTRAINT fk_contract_player FOREIGN KEY (player_id) REFERENCES players(player_id)
);
