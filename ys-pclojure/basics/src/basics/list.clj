(ns basics.list)
(println (list 1 2 3 4 5))
(println (list? *1))
(println (conj (list 1 2 3) 4))
(println (conj (list 1 2 3) 4 5))
(println (conj (conj (list 1 2 3) 4  ) 5))
(println (peek (list 1 2 3)))
(println (pop (list 1 2 3)))
(println '(1 2 3))
