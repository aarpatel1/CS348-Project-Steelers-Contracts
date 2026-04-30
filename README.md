## Project Overview
Steelers Salary Cap Manager is a Spring Boot + Thymeleaf web app for CS348 Stage 2. It manages NFL player contracts and produces a filterable salary-cap report focused on Pittsburgh Steelers sample data.

Core Stage 2 features implemented:
- Relational database schema with PK/FK relationships (`teams`, `positions`, `players`, `contracts`)
- Full CRUD on `contracts` (create, edit, delete, list)
- Filter/report screen with team, position, age range, cap hit range, and contract status filters
- Dynamic dropdowns populated from database tables (teams, positions, players)
- Before/after report validation by changing contract values and refreshing report

## Database Design Summary
Tables:
- `teams(team_id PK, team_name, city, abbreviation)`
- `positions(position_id PK, position_name, position_group)`
- `players(player_id PK, team_id FK -> teams.team_id, position_id FK -> positions.position_id, first_name, last_name, age, jersey_number, active_status)`
- `contracts(contract_id PK, player_id FK -> players.player_id, start_year, end_year, base_salary, signing_bonus, cap_hit, guaranteed_money, contract_status)`

Why this design fits Stage 2:
- Clear entity separation and normalization
- Explicit FK constraints for referential integrity
- Supports reporting joins across player/team/position/contract data

## How To Run
From `steelers-contracts`:
1. `./mvnw spring-boot:run` (or `mvn spring-boot:run` on Windows PowerShell as `./mvnw.cmd spring-boot:run`)
2. Open [http://localhost:8080](http://localhost:8080)
3. Main pages:
   - Contracts CRUD: `/contracts`
   - Filtered report: `/reports`
   - H2 console (optional): `/h2-console`

Notes:
- App uses H2 in-memory DB for quick demo startup.
- Schema and sample Steelers data load automatically from `schema.sql` and `data.sql`.
- Config is MySQL-friendly (`MODE=MySQL`) so migration is straightforward.

## AI Usage
I used Cursor/AI to help generate code structure, SQL ideas, and UI scaffolding.
I reviewed, tested, and modified the output.
I understand and can explain the final code.

## Stage 3 Artifacts
- `STAGE3_NOTES.md` - SQL injection protection, indexes, and transaction/isolation rationale.
- `DEPLOYMENT.md` - extra credit deployment instructions and verification checklist.
- `DEMO_SCRIPT_STAGE3.md` - 5-10 minute demo script and final submission checklist.
