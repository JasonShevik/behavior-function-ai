(ns evo-sim.core
  (:require [clojure.math :as math]
            [scicloj.ml.core :as ml]
            [scicloj.ml.metamorph :as mm]
            [scicloj.ml.dataset :refer [dataset add-column] :as ds]))




(defn make_entity
  ""
  [x y]
  {:location [x y]
   :brain ()})


(defn get_state
  ""
  [entity]
  ())


(defn get_behavior_func
  "Execute a pass of the entity's neural network to get the behavior function for the current state."
  [entity]
  ())





(defn mask_output
  "For each vector of terms in the term_list, make the last z terms 0, and return the new vector of vectors."
  [term_list z]
  (mapv #(concat (drop-last z %)
                 (repeat z 0))
        term_list))

(defn behavior_at_t
  "Solves the Fourier series' with the specified terms at time t and returns the behavior vector."
  [term_list t]
  ; f(t) = a_0 + sigma[n=1 to N]( a_n*cos((2*pi*n*t)/p) + b_n*sin((2*pi*n*t)/p))
  (let [p (/ (dec (count (first term_list))) 2)]
    (let [omega (/ (* 2 math/PI t) p)]
      (mapv #(let [terms %]
               (let [pairs (partition 2 (rest terms))]
                 (+ (get terms 0)
                    (reduce + (map-indexed (fn [n pair]
                                           (+ (* (first pair)  (math/sin (* omega n)))
                                              (* (second pair) (math/cos (* omega n)))))
                                           pairs)))))
            term_list))))

(defn simulate_entity
  "Simulate an entity for t steps with given precision for a specific state.
   Currently, this just returns a vector of behavior vectors for every t"
  [entity precision t]
  (let [masked_term_list (-> (get_behavior_func entity)
                             (mask_output precision))]
    (map #(behavior_at_t masked_term_list %) (range 0 t))))









(comment


  ;Need if entities is empty, do the repeat make_entity, otherwise don't?

  (let [entities (repeat make_entity 10)]
    (map #(simulate_entity % 6 3)
         entities))

  (if ()
    (repeat make_entity 10)
    ())



  )










;(println (mask_output [[1 2 3 4 5 6 7] [9 8 7 6 5 4 3] [1 3 5 7 9 2 4]] 3))
;(println (behavior_at_t [[1 2 3 4 5 6 7] [9 8 7 6 5 4 3]] 2))







