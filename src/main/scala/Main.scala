import fr.utbm.optimisation.PathFinder
import fr.utbm.optimisation.AStarTSPSolver
//import fr.utbm.optimisation.TabuSearchTSPSolver
import fr.utbm.utils.Environment
import fr.utbm.utils.Route

object Main extends App {

  override def main(args: Array[String]) = {
    val parallelism = Math.max(Runtime.getRuntime().availableProcessors(), 3)

    val pathFinderTime = 4
    val tspSolverTime = 1

    val input =
      if (args.size >= 1)
        args(0)
      else
        throw new IllegalArgumentException("the parameter input must be set")
    
    
    val map = Environment.read(input)
    val towns = map.towns

    val combinations =
      for {
        i <- 0 until towns.size
        j <- (i + 1) until towns.size
      } yield (towns(i), towns(j))

    val start = System.currentTimeMillis()

    val routes =
      combinations.par
        .map(
          combination => {
            val route = new PathFinder(
              combination._1,
              combination._2,
              (60.0 * pathFinderTime * (parallelism + 1) / combinations.size * 1000).toLong
            ).run
            
            List(route, new Route(route.cost, route.path.reverse))
          })
        .flatten

    val solution =
      towns.par
        .map(t => new AStarTSPSolver(t, map, routes.toList, (60.0 * tspSolverTime * parallelism / towns.size * 1000).toLong).run)
        .minBy(s => s.cost)

    System.out.println("Solution: " + solution)

    val end = System.currentTimeMillis()

    Environment.write(routes.toList, solution)

    System.out.println("Time: " + ((end - start) / 1000) + "s")
  }
}