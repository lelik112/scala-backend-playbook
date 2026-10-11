# Validated half-open date range

## Use when

A domain needs a non-empty interval of calendar dates with one agreed meaning:

- `from` is included;
- `until` is excluded;
- `from` must be before `until`.

The half-open form `[from, until)` makes adjacent ranges unambiguous. `[2026-10-11, 2026-10-12)` and `[2026-10-12, 2026-10-13)` touch but do not overlap.

## The pattern

1. Keep construction behind one total smart constructor.
2. Return an expected, typed error for invalid bounds.
3. Do not add a second `require` path that throws for the same expected invalidity.
4. Ensure ordinary callers cannot create or copy an invalid value around the constructor.
5. Make boundary decoders call the same constructor instead of reimplementing the invariant.
6. Keep technical corruption of persisted data distinct from invalid user input.

See the executable implementation in [`DateRange.scala`](../../src/main/scala/io/github/lelik112/scalabackendplaybook/dates/DateRange.scala) and its tests in [`DateRangeSuite.scala`](../../src/test/scala/io/github/lelik112/scalabackendplaybook/dates/DateRangeSuite.scala).

## Why this is not `Range[A]`

The reusable part is the construction and boundary pattern, not necessarily a universal generic type. Calendar dates, instants, money, revisions, and other ordered values often need different operations, error messages, persistence rules, and external contracts. Extract a generic abstraction only after real consumers share behavior, not merely because they all contain two ordered values.

## Placement

In a consuming service, the value object belongs with the domain that owns its meaning. It is not moved to a `utils` package merely because more than one method uses it. Generic Circe or Doobie integration helpers may belong in boundary-specific utility packages when they are genuinely shared.

The package in this repository is intentionally neutral. A project should adapt the name and package to its own model rather than importing this example as a foreign domain library.

## Boundary contract

The example uses a custom Circe encoder and decoder. The encoder makes the wire shape explicit; the decoder parses dates and delegates validity to `DateRange.make`.

Projects may choose different field names, such as `to` instead of `until`, but that is a deliberate API contract. Internal naming should still communicate whether the upper bound is inclusive or exclusive.

## Reject this shape

```scala
final case class DateRange private (from: LocalDate, until: LocalDate) {
  require(until.isAfter(from))
}

object DateRange {
  def make(from: LocalDate, until: LocalDate): Either[Error, DateRange] =
    Either.cond(until.isAfter(from), DateRange(from, until), InvalidBounds)
}
```

It duplicates one invariant in an expected-error channel and an exception channel. With an ordinary case class it can also leave construction paths, such as generated copying, that deserve explicit review.

## Minimum tests

- reversed bounds are rejected;
- equal bounds are rejected;
- increasing bounds are accepted;
- the lower bound is included;
- the upper bound is excluded;
- adjacent ranges do not intersect;
- actual overlap is computed correctly;
- malformed external dates fail decoding;
- valid syntax with invalid bounds also fails decoding;
- encoding preserves the selected wire contract.

## Referencing this playbook

When another repository adopts the pattern, link to a pinned commit rather than relying on a moving `main` branch:

> Use a validated half-open date range. Adapt the implementation and tests from the pinned `scala-backend-playbook` DateRange example; keep the resulting type in the owning domain.
