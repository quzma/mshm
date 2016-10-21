(defproject mshm "0.1.0-SNAPSHOT"
  :description "Mushroom simulator"
  :url "http://ivo.kuzmanovic.com.hr"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"][clj-http "3.1.0"][org.clojure/data.json "0.2.6"]]
  :main ^:skip-aot mshm.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
