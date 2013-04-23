package fr.utbm.utils

class Map(val size: Int, val squares: Array[Square]) {

  def apply(x: Int, y: Int): Square = {
    squares.apply(x + this.size * y)
  }

  def update(x: Int, y: Int, square: Square) = {
    squares.update(x + this.size * y, square)
  }

  lazy val towns = squares.filter(s => s match {
    case Town(_, _, _, _) => true
    case _                => false
  }).map(s => s.asInstanceOf[Town]).sortBy(t => t.id)
}

abstract class Square(x: Int, y: Int, map: Map)
    extends Point2D(x, y) {

  val id = -1
  val cost = 0

  lazy val top: Square = if (y <= 0) null else map(x, y - 1)
  lazy val right: Square = if (x >= map.size - 1) null else map(x + 1, y)
  lazy val bottom: Square = if (y >= map.size - 1) null else map(x, y + 1)
  lazy val left: Square = if (x <= 0) null else map(x - 1, y)

  lazy val neighbors = List(top, right, bottom, left).filter(_ != null)
}

case class Normal(_x: Int, _y: Int, map: Map, override val cost: Int)
    extends Square(_x, _y, map) {

  override def toString = String.format("Normal(%s, %s, cost: %s)", this.x.toString, this.y.toString, this.cost.toString)
}
case class Town(_x: Int, _y: Int, map: Map, override val id: Int)
    extends Square(_x, _y, map) {

  override def toString = String.format("Town(%s, %s, id: %s)", this.x.toString, this.y.toString, this.id.toString)
  
  override def equals(obj: Any) = {
    if (obj.isInstanceOf[Town]) {
      obj.asInstanceOf[Town].id == this.id
    } else {
      super.equals(obj)
    }
  }
}