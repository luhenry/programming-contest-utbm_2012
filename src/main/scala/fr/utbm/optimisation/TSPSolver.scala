package fr.utbm.optimisation

import scala.Array.canBuildFrom
import fr.utbm.utils.Town
import fr.utbm.utils.Map
import fr.utbm.utils.Route
import fr.utbm.utils.Solution

class AStarTSPSolver(start: Town, map: Map, routes: List[Route], maxRunningTime: Long) {

  val towns = map.towns
  val costs = routes.map(r => ((r.path.head, r.path.last), r.cost)).toMap

  var startTime = 0L
  var result: Solution = null

  val mutex = new Object()

  def run: Solution = {
    this.startTime = System.currentTimeMillis()
    this.result = this.moveTo(this.start, 0, List(this.start))

    System.out.println(String.format("AStarTSPSolver from %s, result.cost: %s, max running time: %s", this.start.toString, this.result.cost.toString, this.maxRunningTime.toString))

    this.result
  }

  def moveTo(town: Town, cost: Int, path: List[Town]): Solution = {
    if (this.result != null && (this.result.cost <= cost || System.currentTimeMillis() > this.startTime + this.maxRunningTime)) {
      new Solution(-1, null)
    } else if (path.size == (this.towns).size) {
      this.result = new Solution(cost, path)
      this.result
    } else {
      val towns = this.towns
        .filter(t => !path.contains(t))
        .sortBy(t => costs.get((town, t)).get)

      val solutions = towns
        .map(t => moveTo(t, cost + costs.get((town, t)).get, path :+ t))
        .filter(s => s.cost >= 0)

      if (solutions.isEmpty)
        new Solution(-1, null)
      else
        solutions.minBy(s => s.cost)
    }
  }
}
