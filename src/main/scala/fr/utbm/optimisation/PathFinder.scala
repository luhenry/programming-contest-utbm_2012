package fr.utbm.optimisation

import fr.utbm.utils.Map
import fr.utbm.utils.Route
import fr.utbm.utils.Square

class PathFinder(start: Square, end: Square, maxRunningTime: Long) {

  var startTime = 0L
  var result: Route = null
  var timeout = false

  def run: Route = {
    this.startTime = System.currentTimeMillis()
    this.result = this.moveTo(this.start, 0, List(this.start))

    System.out.println(String.format("PathFinder from %s to %s, result.cost: %s, max running time: %s, timeout: %s", this.start.id.toString, this.end.id.toString, this.result.cost.toString, this.maxRunningTime.toString, this.timeout.toString));

    this.result
  }

  def moveTo(current: Square, cost: Int, path: List[Square]): Route = {
    if (this.result != null && this.result.cost <= cost) {
      new Route(-1, null)
    } else if (this.result != null && System.currentTimeMillis() > this.startTime + this.maxRunningTime) {
      this.timeout = true
      
      new Route(-1, null)
    } else if (current.equals(end)) {
      this.result = new Route(cost, path)
      this.result
    } else {
      val points = current.neighbors
        .filter(s => !path.contains(s))
        .sortBy(s => s.manhattanDistanceTo(this.end))

      val paths = points
        .map(s => moveTo(s, cost + s.cost, path :+ s))
        .filter(p => p.cost >= 0)

      if (paths.isEmpty)
        new Route(-1, null)
      else
        paths.minBy(p => p.cost)
    }
  }
}