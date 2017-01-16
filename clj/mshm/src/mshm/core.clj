(ns mshm.core
  (:require 
    [clojure.math.numeric-tower :as math]
    )
  (:gen-class))

(use 'mshm.data)
(use 'mshm.here)


(defn calculate-env-score
  [current low optima high]
  (cond
    (< (math/abs (- optima current)) 1) 1000
    (and (> current low) (< current optima)) (* (/ (- optima low) (- optima current)) 10)
    (and (< current high) (> current optima)) (* (/ (- high optima) (- current optima)) 10)
    :else 0.1)
  )

(defn calculate-new-rankness
  [old-rankness fungi weather]
  (println "......")

  (let [
        temperature-score (calculate-env-score
                            (bigdec (get weather :temperature))
                            (get-in fungi [:environment :temperature :low])
                            (get-in fungi [:environment :temperature :optima])
                            (get-in fungi [:environment :temperature :high])
                            )
        humidity-score (calculate-env-score
                            (bigdec (get weather :humidity))
                            (get-in fungi [:environment :humidity :low])
                            (get-in fungi [:environment :humidity :optima])
                            (get-in fungi [:environment :humidity :high])
                            )]
      (* old-rankness temperature-score humidity-score)
    )
  )

(defn perform-cycle
  [mycelia]
    (let [this-fungi {
       :name "Boletus Titanus"
       :environment {
        :temperature {:low 10 :optima 14 :high 19} 
        :humidity {:low 70 :optima 77 :high 95}}
       :traits {
        :fecundity 0.80
        :resilience 0.55
       }
       }]
    (map (fn 
           [mycelium] 
           [(get mycelium 0) (get mycelium 1) (calculate-new-rankness
              (get mycelium 2) 
              this-fungi 
              (weather-here (get mycelium 0) (get mycelium 1)))]
           ) 
         mycelia) 
      )
  )

(defn -main
  [& args]
  (println ".: mshm :.")
  (-> (get-old-mycelia) perform-cycle set-new-mycelia)
  )

