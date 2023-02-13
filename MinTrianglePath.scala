import scala.annotation.tailrec
import scala.io.StdIn.readLine

object MinTrianglePath {

  type Error = String

  private def readNextLineOfInts(): Either[Error, Vector[Int]] = {
    try {
      val line = readLine()
      if (line == null) Right(Vector())
      else Right(line.split(" ").map(_.toInt).toVector)
    } catch {
      case e: NumberFormatException => Left(s"Could not parse into a number: ${e.getMessage}")
    }
  }

  @tailrec
  private def pathsFromStdIn(acc: Map[Vector[Int], Int] = Map.empty, lineNumber: Int = 1): Either[Error, List[List[Int]]] = {
    readNextLineOfInts() match {
      case Right(IndexedSeq()) => Right(acc.keys.map(_.toList).toList)
      case Right(line) =>
        if (line.length != lineNumber)
          Left(s"$lineNumber integers expected but ${line.length} found: $line")
        else {
          if (lineNumber == 1)
            pathsFromStdIn(Map(line -> 0), lineNumber + 1)
          else {
            val appended = acc.flatMap { case (path, idx) => Map((path :+ line(idx)) -> idx, (path :+ line(idx + 1)) -> (idx + 1))}
            val filteredForShortest = appended
              .groupBy{ case (_, idx) => idx}
              .map { case (_, pathWithLastNumIdx) => pathWithLastNumIdx.minBy{ case (path, _) => path.sum}}
            pathsFromStdIn(filteredForShortest, lineNumber + 1)
          }
        }
      case Left(e) => Left(e)
    }
  }

  def main(args: Array[String]): Unit = {
    pathsFromStdIn() match {
      case Left(error) => println(error)
      case Right(paths) =>
        val shortestPath = paths.minBy(_.sum)
        println(s"Minimal path is ${shortestPath.mkString(" + ")} = ${shortestPath.sum}")
    }
  }
}
