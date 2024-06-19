(defproject caged "0.1.0"
  :description "Clojure wrapper for the Jagged Age encryption library."
  :url "https://gihub.com/mjholub/caged"
  :license {:name "Apache-2.0"
            :url "https://spdx.org/licenses/Apache-2.0.html"}
  :deploy-branches ["main"]
  :dependencies
  [[org.clojure/clojure "1.11.1"]
   [com.exceptionfactory.jagged/jagged-api "0.3.2"]
   [com.exceptionfactory.jagged/jagged-bech32 "0.3.2"]]
  :coverage
  {:plugins [[lein-cloverage "1.2.2"]]
   :dependencies [[org.clojure/tools.logging "1.2.4"]]}

  :repl-options {:init-ns caged.core})


