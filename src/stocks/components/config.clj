(ns stocks.components.config
  (:require [environ.core :refer [env]]
            [com.stuartsierra.component :as component]))

(defrecord Config []
  ;; Implement the Lifecycle protocol
  component/Lifecycle

  (start [component]
    (println ";; Start config")
    (let [jetty {:port  (or (some-> (env :port) Integer/parseInt) 5000)
                 :join? false}]
      (assoc component :jetty jetty)))

  (stop [component]
    (println ";; Stopping config")
    (dissoc component :jetty)))

(defn new-config []
  (map->Config {}))
