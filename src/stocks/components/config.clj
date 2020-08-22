(ns stocks.components.config
  (:require [environ.core :refer [env]]
            [com.stuartsierra.component :as c]))

(defrecord Config []
  ;; Implement the Lifecycle protocol
  c/Lifecycle

  (start [this]
    (println ";; Start config")
    (let [jetty {:port  (or (some-> (env :port) Integer/parseInt) 5000)
                 :join? false}]
      (assoc this :jetty jetty)))

  (stop [this]
    (println ";; Stopping config")
    (dissoc this :jetty)))

(defn new-config
  ([]
   (new-config {}))
  ([extra-configs]
   (map->Config extra-configs)))
