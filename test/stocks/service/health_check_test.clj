(ns stocks.service.health-check-test
  (:require [clojure.test :refer :all]
            [stocks.web :refer :all]
            [ring.mock.request :as mock]))

#_(deftest health-check
  (let [{status :status headers :headers body :body} (app (mock/request :get "/health-check"))
        message (re-find #"It's working!" body)]
    (are [x y] (= x y)
      status 200
      headers {"Content-Type" "application/octet-stream"}
      message "It's working!")))

