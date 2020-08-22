(ns stocks.system
  (:require [stocks.components.config :refer [new-config]]
            [stocks.components.server :refer [new-server]]
            [com.stuartsierra.component :as component]))

(defn system [app]
  (component/system-map
   :config (new-config)
   :server (component/using
             (new-server app)
             [:config])))

(def _system (atom {:system nil}))

(defn start! [app]
  (swap! _system assoc :system
         (component/start-system (system app))))

(defn stop! []
  (swap! _system (fn [system-map]
                        (component/stop-system system-map))))
