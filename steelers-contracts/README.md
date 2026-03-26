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

## Suggested 5-10 Minute Demo Script
1. **Open app and describe architecture (1 min)**
   - Show `/contracts` page.
   - Say: "This app uses Spring Boot MVC + service/repository layers + relational schema with FK relationships."

2. **Show database design in code (1-2 min)**
   - Open `src/main/resources/schema.sql` and point out PK/FK constraints.
   - Open entity classes (`Team`, `Position`, `Player`, `Contract`) and mention `@ManyToOne` relationships.

3. **Demonstrate CRUD on contracts (2-3 min)**
   - Click **Create Contract**.
   - Highlight player dropdown is DB-driven.
   - Save a new contract.
   - Edit one contract (change cap hit/status).
   - Delete one contract.
   - Say: "This satisfies insert/update/delete on one table (`contracts`)."

4. **Demonstrate report filtering (2 min)**
   - Go to `/reports`.
   - Set filters (Team = Steelers, Position = Linebacker, cap hit range).
   - Apply filters and explain report columns including years remaining.

5. **Show before/after report evidence (1 min)**
   - Keep report open and note a specific cap hit/status.
   - Go edit that contract from `/contracts`.
   - Return to report, reapply filter, show updated value.
   - Say: "This is the report before and after data changes."

6. **Show dynamic dropdown source code (1 min)**
   - Open `ContractController` and `ReportController`.
   - Show model attributes for players/teams/positions from services/repositories.
   - Say: "Dropdowns are loaded from DB tables, not hard-coded."
