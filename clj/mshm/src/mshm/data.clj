(ns mshm.data
  (:require 
    [clojurewerkz.welle.core    :as wc]
    [clojurewerkz.welle.buckets :as wb]
    [clojurewerkz.welle.kv      :as kv]
    [clojure.data.json          :as json])
  (:import com.basho.riak.client.http.util.Constants)
 )

(defn create-bucket
  [bucket-name]
  (wb/create (wc/connect) bucket-name)
)

(defn get-data
  [bucket key]
  (let [
    {:keys [result]} (kv/fetch (wc/connect) bucket key)
    [value]             result]
  (get value :value)
  )
)

(defn set-data
  [bucket key value]
  (kv/store  (wc/connect) bucket key value {:content-type "application/clojure"})
)

(defn init-mshm
  []
  (create-bucket "mycelia-gen-1")
  (set-data "mycelia-gen-1" "boletus-titanus" [[44.9 13.8 0.87] [44.9 13.9 0.67]])
  (create-bucket "mshm-config")
  (set-data "mshm-config" "generation" 1)
)

(defn get-old-mycelia
  []
  (println "get old mycelia")
  (let [last-generation (get-data "mshm-config" "generation")]
    (get-data (str "mycelia-gen-" last-generation) "boletus-titanus"))
)

(defn set-new-mycelia
  [mycelia]
  (println "saving new mycelia" mycelia)
  (let [new-generation (+ 1 (get-data "mshm-config" "generation"))
    new-bucket (str "mycelia-gen-" new-generation)
  ]
    (create-bucket  new-bucket)
    (set-data new-bucket "boletus-titanus" mycelia)
    (set-data "mshm-config" "generation" new-generation)
  )

  )


