# E2E Testing Setup Guide — Spring PetClinic

## Overview

This guide describes how to use **Spring PetClinic** on GitHub for E2E testing and reporting, with a clear path from cloning the app to running tests and publishing Allure reports via CI/CD.

**Stack:** Selenium + Selenide + TestNG + Allure + CI/CD (GitHub Actions) + Docker.

---

## Step 1 — Clone the repository

1. Fork or clone **Spring PetClinic** from GitHub:
   - **Repo:** https://github.com/spring-projects/spring-petclinic  
   - If you use your own GitHub repo, clone that instead.

2. Clone and enter the project:
   ```bash
   git clone https://github.com/spring-projects/spring-petclinic.git
   cd spring-petclinic
   ```

3. (Optional) Run the app with Docker to confirm it works:
   ```bash
   docker run -p 8080:8080 springcommunity/spring-petclinic
   ```
   - Open **http://localhost:8080** and check the PetClinic welcome page.

---

## Step 2 — Write and configure E2E tests

1. **Separate test project (recommended)**  
   Keep E2E tests in their own repo/module (e.g. your current `E2E_SELENIUM` project) that points at PetClinic’s URL. This keeps the app repo clean and lets you run tests against any deployed instance (local, Docker, or CI).

2. **Configure the stack**  
   In your test project, configure:
   - **Selenium** — browser automation (often pulled in via Selenide).
   - **Selenide** — concise API and built-in waits; set `baseUrl` to PetClinic (e.g. `http://localhost:8080` or the CI URL).
   - **TestNG** — test lifecycle, suites, and parallel execution.
   - **Allure** — reporting (Surefire + Allure Maven/Gradle plugins, Allure–TestNG adapter).

3. **What to implement**  
   - Base URL / environment config (e.g. local vs CI).
   - Page objects or helpers for PetClinic flows (e.g. home, find owners, add pet, veterinarians).
   - Tests that cover main user journeys; use Allure annotations (`@Step`, `@Description`, `@Severity`) for clear reports.
   - Optional: run the app via Docker in the same setup so “run app + run tests” is one command.

4. **Running and viewing reports locally**  
   - Run tests with your build tool (e.g. `mvn clean test`).
   - Generate and open Allure report (e.g. `mvn allure:serve` or `allure serve target/surefire-reports`).

---

## Step 3 — CI/CD on GitHub (how you’d handle it)

Goal: **Anyone can see how you run E2E tests and publish Allure reports** in GitHub.

### 3.1 Where the workflow runs

- **In the test project repo** (e.g. `E2E_SELENIUM`): workflow runs tests and publishes Allure.
- **Optionally:** trigger on changes in the PetClinic repo (e.g. via `workflow_dispatch` or by cloning PetClinic in the job). For simplicity, the test repo can assume PetClinic is already running (e.g. in Docker in the same job).

### 3.2 High-level flow

1. **Checkout** the test repo (and optionally the app repo if you build/run it in CI).
2. **Set up Java** (e.g. JDK 17) and optionally Maven/Gradle cache.
3. **Start the application** (e.g. `docker run springcommunity/spring-petclinic` or `docker-compose up -d`) so tests have a URL to hit.
4. **Run tests** (e.g. `mvn clean test`) with browser in headless mode (e.g. `selenide.browser=chrome` and Chrome headless, or use Selenium Docker images if you prefer).
5. **Generate Allure report** from Surefire/source results (e.g. `mvn allure:report` or `allure generate`).
6. **Publish the report** so it’s visible to everyone:
   - **Option A — Allure Report artifact:** upload the generated Allure report directory as a workflow artifact; add a short note in the README on how to download and open it (e.g. `allure open`).
   - **Option B — GitHub Pages:** publish the static Allure report to a `gh-pages` branch or `github.io` so the report has a stable URL (e.g. `https://<user>.github.io/<repo>/allure-report`).
   - **Option C — Allure Report server:** if you use a server (e.g. Allure Report Docker image), deploy the report there and link the URL in the README.

### 3.3 What to put in the repo so “anyone can see” the approach

- **`.github/workflows/e2e.yml`** (or similar name):  
  - One workflow that: builds the test project, starts PetClinic (e.g. via Docker), runs tests, generates Allure, then either uploads the report as an artifact or publishes it to GitHub Pages (or both).
- **README section “CI/CD and reports”** that explains:
  - The workflow runs on push/PR (and optionally on schedule or `workflow_dispatch`).
  - How to download the Allure artifact from the Actions run (or the link to the published report).
  - That E2E stack is Selenium + Selenide + TestNG + Allure, with Docker used to run PetClinic (and optionally browsers).

This way, the workflow file and README fully describe how you handle CI/CD and reporting.

### 3.4 Docker in CI

- Use **Docker** to run PetClinic in the same job as the tests (no need to install Java/Spring in the runner for the app).
- For browsers: either use **Chrome/Firefox headless** on the GitHub runner, or use **Selenium Grid in Docker** (e.g. `selenium/standalone-chrome`) and point your tests at the grid. Both approaches are valid; document the one you choose in the README.

---

## Summary

| Step | Action |
|------|--------|
| **1** | Clone (or fork) Spring PetClinic; optionally run it with Docker to verify. |
| **2** | In your own test project: configure Selenium, Selenide, TestNG, Allure; write E2E tests against PetClinic; run locally and generate Allure reports. |
| **3** | In the test repo: add a GitHub Actions workflow that starts PetClinic (Docker), runs tests, generates Allure, and publishes the report (artifact and/or GitHub Pages). Document the flow and report location in the README. |

---

## Useful resources

- [Spring PetClinic](https://github.com/spring-projects/spring-petclinic)
- [Selenide](https://selenide.org/)
- [TestNG](https://testng.org/doc/)
- [Allure](https://docs.qameta.io/allure/)
- [GitHub Actions](https://docs.github.com/en/actions)
- [Docker](https://docs.docker.com/)
