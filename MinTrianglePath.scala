import scala.annotation.{nowarn, tailrec}
import scala.io.StdIn.readLine

trait Node

case class Branch(left: Node, right: Node, value: Int) extends Node

case class Leaf(value: Int) extends Node

object MinTrianglePath {

  private def readNextLineOfInts(): Option[List[Int]] = {
    Option(readLine()).map(_.split(" ").map(_.toInt).toList)
  }

  /**
   * Reads all non-null lines from standard input
   *
   * @param acc The acc
   * @return Each line of the number triangle. The lines are in reverse order.
   */
  @tailrec
  private def readAllLines(acc: List[List[Int]] = Nil): List[List[Int]] = {
    readNextLineOfInts() match {
      case Some(line) => readAllLines(line :: acc)
      case None => acc
    }
  }

  private def readInputIntoNode(): Option[Node] = {
    @tailrec
    def loop(otherLines: List[List[Int]], currentNodes: List[Node]): Option[Node] = {
      if (currentNodes.length == (otherLines.head.length + 1)) {
        val newNodes = otherLines.foldLeft(currentNodes)((acc, ints) => {
          val zipped = ints.zip(acc.sliding(2))
          @nowarn("msg=not.*?exhaustive")
          val result = zipped.map {
            case (currentVal, left :: right :: Nil) => Branch(left, right, currentVal)
          }
          result
        })

        newNodes match {
          case root :: Nil => Some(root)
          case _ => loop(otherLines.tail, newNodes)
        }
      } else None
    }

    readAllLines() match {
      case (root :: Nil) :: Nil => Some(Leaf(root))
      case _ :: Nil => None
      case Nil => None
      case head :: tail => loop(tail, head.map(Leaf.apply))
    }
  }


  def main(args: Array[String]): Unit = {
    println(readInputIntoNode())
  }
}
