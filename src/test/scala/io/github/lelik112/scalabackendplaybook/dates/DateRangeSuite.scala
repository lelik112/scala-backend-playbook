package io.github.lelik112.scalabackendplaybook.dates

import java.time.LocalDate

import io.circe.parser.decode
import io.circe.syntax._

final class DateRangeSuite extends munit.FunSuite {
  private val Day1 = LocalDate.parse("2030-01-01")
  private val Day2 = LocalDate.parse("2030-01-02")
  private val Day3 = LocalDate.parse("2030-01-03")
  private val Day4 = LocalDate.parse("2030-01-04")

  test("make rejects reversed bounds") {
    assert(DateRange.make(Day2, Day1).isLeft)
  }

  test("make rejects an empty range") {
    assert(DateRange.make(Day1, Day1).isLeft)
  }

  test("make accepts increasing bounds") {
    assert(DateRange.make(Day1, Day2).isRight)
  }

  test("includes uses half-open semantics") {
    val range = dateRange(Day1, Day3)

    assert(range.includes(Day1))
    assert(range.includes(Day2))
    assert(!range.includes(Day3))
  }

  test("adjacent ranges do not intersect") {
    val left = dateRange(Day1, Day2)
    val right = dateRange(Day2, Day3)

    assert(left.adjacent(right))
    assert(!left.intersects(right))
    assertEquals(left.intersection(right), None)
  }

  test("intersection keeps only the shared dates") {
    val left = dateRange(Day1, Day3)
    val right = dateRange(Day2, Day4)

    assertEquals(left.intersection(right), Some(dateRange(Day2, Day3)))
  }

  test("decoder uses the validated constructor") {
    val decoded = decode[DateRange]("""{"from":"2030-01-02","until":"2030-01-02"}""")

    assert(decoded.isLeft)
  }

  test("decoder rejects malformed dates") {
    val decoded = decode[DateRange]("""{"from":"not-a-date","until":"2030-01-02"}""")

    assert(decoded.isLeft)
  }

  test("encoder keeps an explicit flat contract") {
    val json = dateRange(Day1, Day3).asJson.noSpaces

    assertEquals(json, """{"from":"2030-01-01","until":"2030-01-03"}""")
  }

  private def dateRange(from: LocalDate, until: LocalDate): DateRange =
    DateRange.make(from, until).fold(error => fail(error.message), identity)
}
