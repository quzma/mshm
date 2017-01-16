(defproject mshm "0.1.0-SNAPSHOT"
  :description "Mushroom simulator"
  :url "http://ivo.kuzmanovic.com.hr"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
    [clj-http "3.1.0"]
    [com.novemberain/welle "3.0.0"]
    [org.clojure/clojure "1.8.0"]
    [org.clojure/data.json "0.2.6"]
    [org.clojure/math.numeric-tower "0.0.4"]
    ]
  :main ^:skip-aot mshm.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
