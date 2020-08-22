(ns stocks.components.server
  (:require [com.stuartsierra.component :as c]
            [ring.adapter.jetty :as jetty]))

(defrecord Server [config]
  c/Lifecycle

  (start [{config :config
           app    :app
           :as    this}]
    (let [{{port  :port
            join? :join?} :jetty} config
          server                  (jetty/run-jetty (:app app) {:port port :join? join?})]
      (println (str ";; Start server on port " port))
      (assoc this :server server)))

  (stop [this]
    (println ";; Stopping server")
    (do
      (some-> (:server this)
              (.stop))
      (dissoc this :server))))

(defn new-server
  ([] (map->Server {}))
  ([app]
   (some->> app
            (assoc {} :app)
            (map->Server))))
