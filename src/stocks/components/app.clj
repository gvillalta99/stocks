(ns stocks.components.app
  (:require [com.stuartsierra.component :as c]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [ring.middleware.json :refer [wrap-json-response]]))

(defrecord App []
  c/Lifecycle
  (start [{routes :routes
           :as    this}]
    (let [wrap-reload? (some-> this :config :app :wrap-reload?)
          reload       (if wrap-reload? wrap-reload identity)
          wrap-app-fn  (fn [handler]
                        (fn [request]
                          (handler (assoc request :app this))))]
      (println ";; Start app")
      (assoc this :app
             (some-> (:routes routes)
                     (wrap-json-response)
                     (wrap-content-type)
                     (wrap-defaults api-defaults)
                     (wrap-app-fn)
                     (reload)))))

  (stop [this]
    (println ";; Stopping app")
    (dissoc this :app)))

(defn new-app []
  (map->App {}))
