(ns mshm.here
  (:require
    [clj-http.client            :as client]
    [clojure.data.json          :as json])
 )

(def app-id-weather "nIEVDdnGHY7xgXcD5jdZ")
(def app-code-weather "E6R9euusf00qbWu6Y-FWyg")
(def app-id-datalens "ve73EOpIZy3FLGqO4FnW")
(def app-code-datalens "LTs_gYLjWJTZHbCPcwo2iQ")

(defn weather-here
  [lat lon]
  (get-in
    (json/read-str (get (client/get "https://weather.cit.api.here.com/weather/1.0/report.json" {:query-params
      {
       "app_id"         app-id-weather
       "app_code"       app-code-weather
       "product"        "observation"
       "oneobservation" true
       "latitude"       lat
       "longitude"      lon}
    } {:throw-entire-message? false}) :body) :key-fn keyword)
    [:observations :location 0 :observation 0]
    )
  )