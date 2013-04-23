package fr.utbm.utils

class Route(val cost: Int, val path: List[Square]) {
  override def toString = String.format("Route(cost: %s, path: %s)", this.cost.toString, this.path.toString)
}