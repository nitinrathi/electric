(ns user.demo-0-entrypoint
  (:require [hyperfiddle.photon :as p]
            [hyperfiddle.photon-dom :as dom]
            [hyperfiddle.photon-ui :as ui]
            [user.demo-1-healthcheck :as healthcheck]
            [user.demo-2-system-properties :as system-properties]
            [user.demo-3-webview :as webview]
            [user.demo-4-counter :as counter]
            [user.demo-5-button :as button]
            [user.demo-6-todos-basic :as todos-basic]
            )
  (:import (hyperfiddle.photon Pending)
           (missionary Cancelled))
  #?(:cljs (:require-macros user.demo-0-entrypoint)))      ; forces shadow hot reload to also reload JVM at the same time

(p/defn App []
  (dom/div
   (dom/h1 (dom/text "Photon Demos"))
   (dom/p (dom/text "Pick a demo. Also take a look at the source in (src-docs/user/demo-{1,2,3…}….cljc)."))
   (let [default-demo {:value 1, :text "1 - Healthcheck"}
         choice       (:value (ui/select {:value   default-demo
                                          :options [default-demo
                                                    {:value 2, :text "2 - System Properties"}
                                                    {:value 3, :text "3 - Webview"}
                                                    {:value 4, :text "4 - Counter"}
                                                    {:value 5, :text "5 - Button"}
                                                    {:value 6, :text "6 - Basic Todo"}]}))]
     (dom/div {:style {:max-width  "90vw"
                       :overflow-x :auto}}
              (case choice
                1 (healthcheck/App.)
                2 (system-properties/App.)
                3 (webview/App.)
                4 (counter/App.)
                5 (button/App.)
                6 (todos-basic/App.))))))

(def main #?(:cljs (p/client (p/main
                              (try
                                (binding [dom/node (dom/by-id "root")]
                                  (App.))
                                (catch Pending _)
                                (catch Cancelled _))))))

(comment
  #?(:clj (user/browser-main! `main))
  )

