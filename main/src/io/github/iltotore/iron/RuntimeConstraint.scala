package io.github.iltotore.iron

final class RuntimeConstraint[A, T](_test: A => Boolean, val message: String):
  inline def test(value: A): Boolean = _test(value)

object RuntimeConstraint:

  inline def derived[A, C](using inline c: Constraint[A, C]): RuntimeConstraint[A, A :| C] =
    new RuntimeConstraint[A, A :| C](c.test(_), c.message)

  final class AutoDerived[A, T](val inner: RuntimeConstraint[A, T])
  object AutoDerived:
    inline given derived[A, C](using inline c: Constraint[A, C]): AutoDerived[A, A :| C] =
      new AutoDerived[A, A :| C](RuntimeConstraint.derived[A, C])
