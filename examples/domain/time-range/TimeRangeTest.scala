package examples.domain

import java.time.Instant
import java.time.temporal.ChronoUnit.SECONDS

import org.scalatest.OptionValues
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

final class TimeRangeTest extends AnyFlatSpec with Matchers with OptionValues {

  "TimeRange" must "forbid creating values when from < until" in {
    assert(TimeRange.make(Instant.now(), Instant.now().minus(1, SECONDS)).isLeft)
  }

  it must "forbid creating values when from = until" in {
    val now = Instant.now()
    assert(TimeRange.make(now, now).isLeft)
  }

  it must "allow creating values when from < until" in {
    assert(TimeRange.make(Instant.now().minus(1, SECONDS), Instant.now()).isRight)
  }
}
