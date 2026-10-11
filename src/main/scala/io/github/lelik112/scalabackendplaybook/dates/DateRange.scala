package io.github.lelik112.scalabackendplaybook.dates

import java.time.LocalDate
import scala.util.Try

import io.circe.{Decoder, DecodingFailure, Encoder, HCursor, Json}

/** A non-empty half-open date interval: `from` is included, `until` is excluded. */
sealed abstract case class DateRange private (from: LocalDate, until: LocalDate) {
  def includes(date: LocalDate): Boolean =
    !date.isBefore(from) && date.isBefore(until)

  def contains(other: DateRange): Boolean =
    !other.from.isBefore(from) && !other.until.isAfter(until)

  def adjacent(other: DateRange): Boolean =
    until == other.from || other.until == from

  def intersects(other: DateRange): Boolean =
    from.isBefore(other.until) && other.from.isBefore(until)

  def intersection(other: DateRange): Option[DateRange] = {
    val intersectionFrom  = if (from.isAfter(other.from)) from else other.from
    val intersectionUntil = if (until.isBefore(other.until)) until else other.until

    DateRange.makeOption(intersectionFrom, intersectionUntil)
  }
}

object DateRange {
  private val JsonFrom  = "from"
  private val JsonUntil = "until"

  sealed trait Error extends Product with Serializable {
    def message: String
  }

  object Error {
    final case class InvalidBounds(from: LocalDate, until: LocalDate) extends Error {
      override val message: String = s"from must be before until: from=$from, until=$until"
    }
  }

  def make(from: LocalDate, until: LocalDate): Either[Error, DateRange] =
    Either.cond(
      until.isAfter(from),
      new DateRange(from, until) {},
      Error.InvalidBounds(from, until)
    )

  def makeOption(from: LocalDate, until: LocalDate): Option[DateRange] =
    make(from, until).toOption

  implicit val encoder: Encoder[DateRange] = Encoder.instance { range =>
    Json.obj(
      JsonFrom  -> Json.fromString(range.from.toString),
      JsonUntil -> Json.fromString(range.until.toString)
    )
  }

  implicit val decoder: Decoder[DateRange] = Decoder.instance { cursor =>
    for {
      from  <- date(cursor, JsonFrom)
      until <- date(cursor, JsonUntil)
      range <- make(from, until).left.map(error => DecodingFailure(error.message, cursor.history))
    } yield range
  }

  private def date(cursor: HCursor, field: String): Decoder.Result[LocalDate] =
    cursor.get[String](field).flatMap { raw =>
      Try(LocalDate.parse(raw)).toEither.left.map { _ =>
        DecodingFailure(s"$field must be an ISO-8601 date (YYYY-MM-DD)", cursor.downField(field).history)
      }
    }
}
