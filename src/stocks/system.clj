(ns stocks.system
  (:require [stocks.components.config :refer [new-config]]
            [stocks.components.server :refer [new-server]]
            [stocks.components.app :refer [new-app]]
            [stocks.service.routes :refer [app-routes]]
            [com.stuartsierra.component :as component]))

(defn new-routes []
  {:routes app-routes})

(defn system [extra-configs]
  (component/system-map
   :config (new-config extra-configs)
   :routes (new-routes)
   :app (component/using (new-app) [:routes :config])
   :server (component/using (new-server) [:config :app])))

(def _system (atom {:system nil}))

(defn start!
  ([] (start! {}))
  ([extra-configs]
   (swap! _system assoc :system
          (component/start-system (system extra-configs)))))

(defn stop! []
  (swap! _system (fn [system-map]
                   (component/stop-system system-map))))
