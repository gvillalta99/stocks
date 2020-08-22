(ns stocks.web
  (:require [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [ring.middleware.json :refer [wrap-json-response]]
            [stocks.service.routes :refer [app-routes]]
            [stocks.system :as s])
  (:gen-class))

(def app
  (-> app-routes
      (wrap-json-response)
      (wrap-content-type)
      (wrap-defaults api-defaults)))

(defn run-dev [& [custom-port]]
  (s/start! (wrap-reload app)))

(defn -main [& [custom-port]]
  (s/start! app))
