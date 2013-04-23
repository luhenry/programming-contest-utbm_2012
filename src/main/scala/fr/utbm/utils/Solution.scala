package fr.utbm.utils

class Solution(val cost: Int, val towns: List[Town]) {
  override def toString = String.format("Solution(cost: %s, towns: %s)", this.cost.toString, this.towns.toString)
}