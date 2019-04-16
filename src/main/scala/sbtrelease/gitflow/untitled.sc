class Rational(x: Int, y: Int) {

  private def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)
  private val g = gcd(x, y)

  lazy val numer: Int = x / g
  lazy val denom: Int = y / g
}


val compareRationals: (Rational, Rational) => Int = (a, b) => a.denom.compareTo(b.denom)
}


implicit val rationalOrder: Ordering[Rational] =
  new Ordering[Rational] {
    def compare(x: Rational, y: Rational): Int = compareRationals(x, y)
  }

def insertionSort[T](xs: List[T])(ord: Ordering[T]): List[T] = {
  def insert(y: T, ys: List[T]): List[T] =
    ys match {
      case List() => y :: List()
      case z :: zs =>
        if (ord.lt(y,z)) y :: z :: zs
        else z :: insert(y, zs)
    }

  xs match {
    case List() => List()
    case y :: ys => insert(y, insertionSort(ys)(ord))
  }
}

val half = new Rational(1, 2)
val third = new Rational(1, 3)
val fourth = new Rational(1, 4)
val rationals = List(third, half, fourth)
//insertionSort(rationals) shouldBe List(fourth, third, half)