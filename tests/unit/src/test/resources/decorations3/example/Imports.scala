package example

import util.{Failure => NotGood}
import math.{floor => _, _}

class Imports {
  // rename reference
  NotGood/*[Nothing]*/(null)
  max(1, 2)
}