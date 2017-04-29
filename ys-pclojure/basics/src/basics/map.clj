(ns basics.map)
(def the-map {:a 1 :b 2 :c 3})
(println "the-map" the-map)
(println "hash-map" (hash-map :a 1 :b 2 :c 3))
(println "(the-map :b)" (the-map :b))
(println "(:b the-map)" (:b the-map))
(println "(:z the-map)" (:z the-map))                       ;no default value
(println "(:z the-map)" (:z the-map 26))                    ; with default value
(println "(def updated-map (assoc the-map :d 4))" (def updated-map (assoc the-map :d 4)))
(println "(def updated-map (assoc the-map :d 4))" updated-map)
(println "(dissoc updated-map :a)" (dissoc updated-map :a))