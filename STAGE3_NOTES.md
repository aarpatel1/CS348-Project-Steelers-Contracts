# Stage 3 Notes

## Phase 1 Audit Summary

- **Stack:** Java 17, Spring Boot, Spring MVC, Thymeleaf, Spring Data JPA, Maven.
- **Database system:** H2 (in-memory) for local runtime, configured in MySQL mode; MySQL driver already included for deployment.
- **ORM/query style:** Spring Data JPA repositories + JPQL queries with bound parameters.
- **DB connection:** `application.properties` now reads `SPRING_DATASOURCE_*` env vars first, then falls back to local H2 defaults.
- **Current features from Stage 2 preserved:**
  - Contract CRUD (`/contracts`, create/edit/delete).
  - Report/filter page (`/reports`) with filters for team, position, contract status, age range, cap-hit range.
  - Dynamic dropdowns for players, teams, positions, statuses populated from DB.

## SQL Injection Protection

- All user input in forms and filters is passed through Spring Data JPA/JPA bindings, not SQL string concatenation.
- Stage 3 report filtering now runs in the database via parameterized JPQL (`ContractRepository.findFilteredContracts(...)`) instead of in-memory list filtering.
- Contract create/update/delete use repository save/delete methods, which use prepared statements under the hood.
- Representative note in `ContractService`: JPA binds each filter as typed params, preventing SQL injection from malicious form input.

## Indexes Added

Indexes were added in `schema.sql`:

1. `idx_players_team_id` on `players(team_id)`
   - Supports report filters and joins from contracts -> players -> team.
2. `idx_players_position_id` on `players(position_id)`
   - Supports report position filter and player-position joins.
3. `idx_players_last_first_name` on `players(last_name, first_name)`
   - Supports player dropdown sorting (`findAllByOrderByLastNameAscFirstNameAsc`).
4. `idx_players_age` on `players(age)`
   - Supports report age range filters.
5. `idx_contracts_player_id` on `contracts(player_id)`
   - Supports contract-player joins and player-specific lookups.
6. `idx_contracts_status` on `contracts(contract_status)`
   - Supports contract status filter in reports.
7. `idx_contracts_cap_hit` on `contracts(cap_hit)`
   - Supports cap-hit range filtering.
8. `idx_contracts_player_years` on `contracts(player_id, start_year, end_year)`
   - Supports overlapping-year validation query in transactional create/update.

## Transactions and Isolation

- Transaction added around contract create/update/delete in `ContractService` with `@Transactional(isolation = Isolation.READ_COMMITTED)`.
- For create/update, the service now:
  1. acquires a row lock on player (`PESSIMISTIC_WRITE` query),
  2. checks for overlapping contract years,
  3. writes contract changes,
  all in one transaction.
- **Why this matters:** without a transaction, two concurrent users could both pass overlap checks and insert conflicting contract-year data for the same player.
- **Why `READ_COMMITTED`:** practical default for web apps; avoids dirty reads and keeps concurrency reasonable for this project while the explicit player row lock protects the critical section.

## Overlap Bug Fix (Back-to-Back Contracts)

- **Previous bug:** overlap query used inclusive-style logic:
  - `existing.start_year <= new_end_year`
  - `existing.end_year >= new_start_year`
- With that logic, `2025-2026` and `2026-2027` were incorrectly treated as overlapping because both touched year `2026`.
- **Fix applied:** switched to interval overlap logic with end-year treated as exclusive for detection:
  - `existing.start_year < new_end_year`
  - `existing.end_year > new_start_year`
- **Why this is correct:** it allows adjacent contracts (end boundary equals next start boundary) while still rejecting true overlap.
- Validation now enforces `start_year < end_year` so interval comparisons remain well-defined.
- Update flow still excludes the current contract id, so editing a contract without changing years does not conflict with itself.

## AI Usage

- **Tools used:** ChatGPT and Cursor.
- **AI-assisted tasks:** implementation planning, debugging, SQL/index suggestions, transaction/concurrency design, deployment steps, and writing project docs/demo script.
- **Verification performed by student:** reviewed generated code manually, tested CRUD and report behavior, verified user inputs are parameterized through JPA, and confirmed deployment flow for live app/database operation.
