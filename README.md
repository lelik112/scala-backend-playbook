# Scala Backend Playbook

A reference collection of working Scala examples supplied by the repository owner. Read an example, understand its decisions and limitations, then adapt it in the consuming project. This is not another backend, a shared domain dependency, or a library of prewritten answers for one application.

## Catalog

| Need | Reference | Stack |
| --- | --- | --- |
| Validated temporal value, boundary codecs, interval operations | [TimeRange](examples/domain/time-range/README.md) | Scala 2.13, java.time, ZIO, Circe, ScalaTest |

## How to use

Read the example README, implementation and tests together. Keep accepted requirements of the consuming project above example-specific choices. A reference demonstrates an approach; it does not make every original detail universally correct. Link from the project's relevant engineering document to a pinned commit here. Do not copy the reference into every instruction file.

For example: when designing a validated temporal value, study TimeRange's construction boundary and decoder. Decide in the consuming project whether its values are calendar dates, zoned date-times or instants. Do not adapt the reference in advance to a hypothetical consumer.

## Adding a reference

Preserve the supplied structure, types, operations and tests as far as publication permits. Remove private package names and unavailable private dependencies; document every substantive omission or change. Keep added characterization tests separate from the supplied tests. Do not silently repair a known limitation while claiming a faithful import.

Use [the reference template](docs/reference-template.md). Add only examples that exist, not empty directories for potential frameworks. ZIO and Cats Effect examples can coexist without converting one ecosystem to the other.

## Verification

`sbt test` compiles the runnable TimeRange excerpt and runs the supplied tests plus separate characterization tests. No source formatter rewrites imported examples. CI runs for code/build changes, does not publish packages, and uploads no test artifacts.

The previous LocalDate DateRange adaptation has been removed from the current tree because it was not a faithful reference import. History is preserved, not rewritten.

## Publication boundary

Never include private package names, company-specific surrounding code, credentials, internal URLs or private project discussions. A public repository is not permission to copy material whose publication has not been authorized. No license grant is inferred merely from repository visibility.
