import scala.annotation.tailrec
import scala.io.StdIn.readLine

object MinTrianglePath {

  type Error = String

  private final case class Path(nodes: Vector[Int]) {
    val sum: Int = nodes.sum
  }

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
  private def pathsFromStdIn(acc: Map[Path, Int] = Map.empty, lineNumber: Int = 1): Either[Error, List[Path]] = {
    readNextLineOfInts() match {
      case Right(IndexedSeq()) => Right(acc.keys.toList)
      case Right(line) =>
        if (line.length != lineNumber)
          Left(s"$lineNumber integers expected but ${line.length} found: $line")
        else {
          if (lineNumber == 1)
            pathsFromStdIn(Map(Path(line) -> 0), lineNumber + 1)
          else {
            val appended = acc.flatMap { case (path, idx) => Map(path.copy(path.nodes :+ line(idx)) -> idx, path.copy(path.nodes :+ line(idx + 1)) -> (idx + 1)) }
            val filteredForShortest = appended
              .groupBy { case (_, idx) => idx }
              .map { case (_, pathWithLastNumIdx) => pathWithLastNumIdx.minBy { case (path, _) => path.sum } }
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
        println(s"Minimal path is ${shortestPath.nodes.mkString(" + ")} = ${shortestPath.sum}")
    }
  }
}
