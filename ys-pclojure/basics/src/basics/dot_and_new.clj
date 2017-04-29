(ns basics.dot-and-new)
(println "==>basics.dot_and_new")
(println "==>dot")
(println (. Math PI))
(println (. Math abs -3))
(println (. "foo" toUpperCase))

(println "==>nodot")
(println (Math/PI))
(println (Math/abs -3))
(println (.toUpperCase "foo" ))

(println "==>new")
(println (new Integer 42))
(println (Integer. 42))

