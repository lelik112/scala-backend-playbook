# TimeRange: source reference, not a consumer adaptation

Read [TimeRange.scala](TimeRange.scala) and [the supplied tests](TimeRangeTest.scala). The separate [characterization suite](TimeRangeCharacterizationTest.scala) was added during import.

## What this actually demonstrates

A non-empty half-open interval `[from, until)` represented with `ZonedDateTime`, compared through `Instant`. `make` returns `Either[String, TimeRange]`, checks increasing instants and identical ZoneId values, and `makeOption` delegates to it. The custom Circe decoder reads `from`/`to` then calls `make`; the encoder uses the same two wire fields. Keep the supplied String error contract here rather than replacing it with a new ADT.

The sealed abstract case class keeps the ordinary public construction boundary controlled: the Scala 2 compiler does not synthesize the normal case-class apply/copy for this abstract form; subclasses are restricted to its source file. Internal operations construct values directly where the implementation establishes valid bounds. Therefore the lesson is controlled construction and maintained invariants, not literally that every `new` must call `make`.

This is a domain value example. Reuse outside its original application does not make it a technical utils class. Framework-specific helpers remain visible: `duration` uses ZIO Duration; `makeUIO` deliberately turns invalidity into a defect. It is not the HTTP input-decoding path and is not a promise of universal failure-free execution.

## Publication changes

The source package was renamed to `examples.domain`; two unavailable private utility imports were removed. Omitted only the private regional-date conversions and overloads, their regional day rounding, and the positive-count month-start factory. Their private supporting types were not recreated. Remaining method bodies, signatures, wire names, equality, ordering and supplied test bodies are preserved. The now-redundant blank line from removing imports is intentionally harmless.

This is an explicitly reduced source excerpt, not a claim that the complete original application was imported. No LocalDate replacement or generic Range[A] has been introduced. The original private package identifiers are deliberately not listed here.

## Limits to notice before adapting

- `union` returns the enclosing interval even for disjoint arguments; it fills a gap. It is not a set union returning two intervals.
- `smallest` adds 1000 nanoseconds (one microsecond), despite the retained original comment saying millisecond. Neither has been silently changed.
- Equality and hashing compare start/end instants; presentation zones may differ between equal ranges, while each individual range requires matching endpoint zones.
- The first supplied test title says `<`, but its inputs exercise reversed bounds. The title and clock-based test bodies are retained as source material. Added tests use fixed instants.
- The supplied three tests are not complete coverage. Added characterization tests cover selected operations, codec boundaries and compile-time construction restrictions, not every java.time extreme or every ZIO runtime path.
- The decoder uses `DecodingFailure(_, Nil)` as supplied, not full cursor history. A consumer can improve diagnostic location deliberately.
- `smallest` and other java.time operations can fail at extreme representable values. No null-support or all-input totality guarantee is claimed.

## Run

`sbt test` with the repository build: Scala 2.13.18, Circe 0.14.10, ZIO 2.1.21 and ScalaTest 3.2.19. These are explicit build choices for this standalone excerpt, not a claim to reproduce its old application's entire dependency set.

## Transfer to another project

Borrow the construction boundary, invariant-preserving operations and decoder delegation. The consumer chooses calendar dates versus instants, placement, public names, error representation and effect integration. Do not copy regional helpers or defect-oriented constructors into an untrusted-input boundary by habit.

Link the consumer's relevant model/serialization document to this README at a pinned commit. Adapt in the consumer only when its task needs it.
