(ns tailwind.subs
  (:require [re-frame.core :as rf])
  )




(rf/reg-sub
 ::counter
 (fn [db _]
   (get-in db [:counter] 0)
   )
 )