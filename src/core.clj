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

(defn calculate_omega
  "Calculates omega, (2*pi*t)/p, which appears in the trig functions of the Fourier series terms."
  [t p]
  (/ (* 2 math/PI t) p))

(defn evaluate_fourier
  "Solves the Fourier series with the specified terms for omega (which includes t) and return the result."
  [terms omega]
  ; f(t) = a_0 + sigma[n=1 to N]( a_n*cos(omega*n) + b_n*sin(omega*n)), omega = (2*pi*t)/p
  (let [pairs (partition 2 (rest terms))]
    (+ (get terms 0)
       (reduce + (map-indexed (fn [n pair]
                                (+ (* (first pair)  (math/sin (* omega n)))
                                   (* (second pair) (math/cos (* omega n)))))
                              pairs)))))

(defn evaluate_fourier_vector
  ""
  [term_vector t p]
  (let [omega (calculate_omega t p)]
    (mapv #(evaluate_fourier % omega)
          term_vector)))

(defn smooth_fourier
  ""
  [terms b t]
  (if (zero? b)
    (evaluate_fourier terms t)
    ()))




; I think my current approach is wrong.
; I want to work with equations and stop trying to solve at t so quickly.
; I should be passing the result of mask_output to smooth_func
(defn simulate_entity
  "Simulate an entity for t steps with given precision for a specific state.
   Currently, this just returns a vector of behavior vectors for every t"
  [entity precision t b]
  (let [masked_term_list (-> (get_behavior_func entity)
                             (mask_output precision))]
    (map #(kernel_smooth evaluate_fourier_vector [masked_term_list %] b)
         (range 0 t))))









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







