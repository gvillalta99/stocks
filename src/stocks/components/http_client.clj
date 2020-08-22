(ns stocks.components.http-client
  (:require [org.httpkit.client :as http-client]
            [com.stuartsierra.component :as c]))


(defrecord HttpClient
    c/Lifecycle
  (start [this]
    (assoc this :http-client ))
  (stop [this]))

(defn new-http-client [])
