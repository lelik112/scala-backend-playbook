# Reference entry template

## What this demonstrates
State the engineering problem, not the consuming project's feature.

## Read
Link the supplied implementation and supplied tests. List stack/version assumptions and the verification command.

## Source behavior
List invariants, creation paths, error channels, effects and relevant edge cases actually present in the source.

## Publication changes
List renamed packages, omitted private adapters, replaced dependencies and any deliberate semantic change. Prefer an explicit excerpt to inventing a missing private helper. Never publish private package identifiers in this ledger.

## Limits, not hidden fixes
Record surprising behavior, mismatched comments, missing tests and assumptions. Distinguish original tests from added characterization tests. Do not claim universal correctness.

## How to adapt
State which decisions transfer, which are consumer-owned and when not to reuse the example. Link from the relevant context-level document in the consuming project. Keep the implementation here once, not copied into every guide.
