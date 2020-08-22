(ns stocks.system
  (:require [stocks.components.config :refer [new-config]]
            [stocks.components.server :refer [new-server]]
            [com.stuartsierra.component :as component]))

(defn prod-system [app]
  (component/system-map
   :config (new-config)
   :server (component/using
             (new-server app)
             [:config])))

(def _prod-system (atom {:system nil}))

(defn start-prod! [app]
  (swap! _prod-system assoc :system
         (component/start-system (prod-system app))))

(defn stop-prod! []
  (swap! _prod-system (fn [system-map]
                        (component/stop-system system-map))))
