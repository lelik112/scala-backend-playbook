# Scala Backend Playbook

Tested, framework-conscious examples for Scala backend development.

This repository is not a snippet dump and not a shared domain library. Each example documents:

- the problem and invariant;
- a small implementation with neutral package names;
- boundary integration where it matters;
- executable tests;
- common failure modes;
- guidance on when to adapt the pattern instead of copying the type.

The examples may cover ZIO, Cats Effect, Circe, Doobie, and PostgreSQL. Framework-specific material lives in its own section; domain value objects remain independent where possible.

## Catalog

- [Validated half-open date range](docs/playbooks/validated-date-range.md)

## Run

```bash
sbt test
```

## Referencing from another project

Project documentation should link to the relevant playbook and, for normative use, pin the link to a commit. The consuming project still owns its domain names, contracts, and placement.

Example wording:

> Model the interval as a validated half-open range. Follow the DateRange pattern from `scala-backend-playbook`, adapted to this domain and pinned revision.

## Provenance

Examples are rewritten as neutral, standalone material. Do not copy private package names, company-specific utilities, internal contracts, or proprietary surrounding code into this repository.
