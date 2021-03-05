(ns mshm.here
  (:require
    [clj-http.client            :as client]
    [clojure.data.json          :as json])
 )

(def app-id-weather "xxx")
(def app-code-weather "yyyy")
(def app-id-datalens "zzzz")
(def app-code-datalens "qqqq")

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
