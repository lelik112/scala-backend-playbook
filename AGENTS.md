# Reference corpus instructions

This repository preserves supplied examples, not pre-adapted implementations for a consuming product.

1. Read the root catalog, then the selected example's README, source and tests.
2. Preserve the example's actual behavior. Anonymize private names and explicitly list omitted unavailable dependencies.
3. Do not turn a ZIO sample into Cats Effect or a zoned time range into a date-only range here merely for a future consumer. Adapt in that consumer.
4. Separate source facts, known limitations and new commentary. Added tests must be distinguishable from supplied tests.
5. New generic abstractions, dependency publishing and additional frameworks require a concrete need. Do not introduce them for catalog organization.
6. Run the documented checks for changes that affect runnable examples. Report exact results, skips and limitations. Do not count a reference's historic tests as a fresh run.
7. Keep corporate/private packages, credentials, internal links and unrelated private material out of the entire public commit.
