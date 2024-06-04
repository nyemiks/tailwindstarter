(ns tailwind.events
  (:require
   [tailwind.db :as db]
    [re-frame.core :as rf]
            )
  )

(defn login
  []
  (swap! db/state assoc :auth? true))

(defn logout
  []
  (swap! db/state assoc :auth? false))

(defn toggle-user-dropdown
  []
  (let [dropdown (:user-dropdown? @db/state)]
    (swap! db/state assoc :user-dropdown? (not dropdown))))



(rf/reg-event-db
 ::initialize-db
 (fn [_ _]
   (.info js/console "db initialized ! ")
   db/default-db
   )
 )



(rf/reg-event-db
 ::increment2
 (fn [db _]
   (update-in db [:counter] (fnil inc 0))
   )
 )


(rf/reg-event-db
 ::decrement2
 (fn [db _]
   (update-in db [:counter] (fnil dec 0))
   )
 )
