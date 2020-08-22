(ns stocks.web
  (:require [stocks.system :as s])
  (:gen-class))

(defn run-dev [& [custom-port]]
  (s/start! {:app {:wrap-reload? true}}))

(defn -main [& [custom-port]]
  (s/start!))
