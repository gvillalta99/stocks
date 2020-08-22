(require '[clojure.test :refer [run-all-tests]])
(require '[clojure.tools.namespace.repl :refer [refresh]])
(require '[kaocha.watch])
(use 'kaocha.repl)
(println "repl tools loaded")

(def _current-state (atom nil))

(defn start-watcher []
  (if (:state @_current-state)
    (println "Watcher already started")
    (swap! _current-state (fn [_old]
                            (let [[state finish-fn] (kaocha.watch/run (kaocha.repl/config))]
                              {:state state
                               :finish-fn finish-fn})))))

(defn stop-watcher []
  (if (:finish-fn @_current-state)
    (swap! _current-state (fn [{finish-fn :finish-fn}]
                            (do
                              (finish-fn)
                              nil)))
    (println "Watcher not running")))
