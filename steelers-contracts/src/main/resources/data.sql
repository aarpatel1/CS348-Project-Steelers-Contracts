INSERT INTO teams (team_name, city, abbreviation) VALUES
('Pittsburgh Steelers', 'Pittsburgh', 'PIT'),
('Cincinnati Bengals', 'Cincinnati', 'CIN');

INSERT INTO positions (position_name, position_group) VALUES
('Quarterback', 'Offense'),
('Running Back', 'Offense'),
('Wide Receiver', 'Offense'),
('Linebacker', 'Defense'),
('Defensive End', 'Defense'),
('Safety', 'Defense'),
('Defensive Lineman', 'Defense');

INSERT INTO players (team_id, position_id, first_name, last_name, age, jersey_number, active_status) VALUES
(1, 1, 'Aaron', 'Rodgers', 42, 8, TRUE),
(1, 3, 'DK', 'Metcalf', 28, 4, TRUE),
(1, 2, 'Jaylen', 'Warren', 27, 30, TRUE),
(1, 4, 'T.J.', 'Watt', 31, 90, TRUE),
(1, 7, 'Cameron', 'Heyward', 36, 97, TRUE),
(2, 1, 'Joe', 'Burrow', 29, 9, TRUE);

INSERT INTO contracts (player_id, start_year, end_year, base_salary, signing_bonus, cap_hit, guaranteed_money, contract_status) VALUES
(1, 2025, 2026, 13650000.00, 0.00, 14150000.00, 10000000.00, 'ACTIVE'),
(2, 2025, 2029, 103500000.00, 30000000.00, 149444444.00, 62000000.00, 'ACTIVE'),
(3, 2025, 2027, 8750000.00, 5950000.00, 17250000.00, 11765000.00, 'ACTIVE'),
(4, 2025, 2028, 89050000.00, 49368895.00, 153418695.00, 108000000.00, 'ACTIVE'),
(5, 2026, 2027, 9300000.00, 19850000.00, 37150000.00, 16250000.00, 'ACTIVE'),
(6, 2023, 2029, 173014000.00, 45055507.00, 315104525.00, 146510000.00, 'ACTIVE');
