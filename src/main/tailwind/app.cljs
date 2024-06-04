(ns tailwind.app
  (:require [reagent.dom :as dom]
            [tailwind.views :as views]
            [tailwind.db :as db]))

(defn app
  []
  (comment 
    
    (if (:auth? @db/state)
    [views/authenticated]
    [views/public]
    )

    )
   ; [views/counter-view]
    ; [views/menu-comp]
     ; [views/desktop-header]
  
     ;[:f> views/menu-view]
      [:f> views/drop-down]
  
  )

;; start is called by init and after code reloading finishes
(defn ^:dev/after-load start []
  (dom/render [app]
    (.getElementById js/document "app")))

(defn init []
  ;; init is called ONCE when the page loads
  ;; this is called in the index.html and must be exported
  ;; so it is available even in :advanced release builds
  (js/console.log "init")
  (start))

;; this is called before any code is reloaded
(defn ^:dev/before-load stop []
  (js/console.log "stop"))
