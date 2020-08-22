(ns stocks.components.server
  (:require [com.stuartsierra.component :as component]
            [ring.adapter.jetty :as jetty]))

(defrecord Server [config]
  component/Lifecycle

  (start [{config :config
           app    :app
           :as    this}]
    (let [{{port  :port
            join? :join?} :jetty} config
          server                  (jetty/run-jetty app {:port port :join? join?})]
      (println (str ";; Start server on port " port))
      (assoc this :server server)))

  (stop [this]
    (println ";; Stopping server")
    (do
      (some-> (:server this)
              (.stop))
      (dissoc this :server))))

(defn new-server [app]
  (some->> app
           (assoc {} :app)
           (map->Server)))
