(ns mshm.core
  (:require 
    [clj-http.client :as client]
    [clojure.data.json :as json]
    )
  (:gen-class))

(def app-id-here "nIEVDdnGHY7xgXcD5jdZ")
(def app-code-here "E6R9euusf00qbWu6Y-FWyg")
(def app-id-datalens "ve73EOpIZy3FLGqO4FnW")
(def app-code-datalens "LTs_gYLjWJTZHbCPcwo2iQ")

(defn weather-here
  [lat lon]
  (get-in 
    (json/read-str (get (client/get "https://weather.cit.api.here.com/weather/1.0/report.json" {:query-params 
      {
       "app_id" app-id-here 
       "app_code" app-code-here 
       "product" "observation" 
       "oneobservation" true
       "latitude" lat 
       "longitude" lon}
    } {:throw-entire-message? false}) :body) :key-fn keyword)
    [:observations :location 0 :observation 0]
    )
  )

(defn get-old-mycelia
  []
  (println "get old mycelia")
  [[44.9 13.8 0.87] [44.9 13.9 0.67]]
  )

(defn store-new-mycelia
  [mycelia]
  (println "saving new mycelia" mycelia)

  )
  
(defn calculate-env-score 
  [current low optima high]
  )

(defn calculate-new-rankness
  [old-rankness fungi weather]
  (println "......")
  (println weather)
  (println "......")

  (let [
        temperature-score (calculate-env-score
                            (get weather :temperature)
                            (get-in fungi [:environment :temperature :low])
                            (get-in fungi [:environment :temperature :optima])
                            (get-in fungi [:environment :temperature :high])
                            )
        humidity-score (calculate-env-score
                            (get weather :humidity)
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
  (-> (get-old-mycelia) perform-cycle store-new-mycelia)
  )

