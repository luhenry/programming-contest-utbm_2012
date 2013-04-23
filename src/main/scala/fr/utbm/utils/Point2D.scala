package fr.utbm.utils

class Point2D(val x: Int, val y: Int) {
  
  def manhattanDistanceTo(that: Point2D) = {
    Math.abs(this.x - that.x) + Math.abs(this.y - that.y)
  }
  
  override def hashCode: Int = {
	var	bits = java.lang.Double.doubleToLongBits(this.x)
    bits ^= java.lang.Double.doubleToLongBits(this.y) * 31
    
    ((bits) ^ (bits >> 32)).toInt
  }
  
  override def equals(obj: Any): Boolean = {
    if (obj.isInstanceOf[Point2D]) {
      val p2d: Point2D = obj.asInstanceOf[Point2D]
      
      (this.x == p2d.x) && (this.y == p2d.y)
    } else {
      super.equals(obj)
    }
  }
  
  override def toString: String = {
    String.format("Point2D(%s, %s)", this.x.toString, this.y.toString)
  }
}