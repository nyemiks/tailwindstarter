(ns tailwind.views
  (:require [tailwind.events :as events]
            [tailwind.db :as db]
             [re-frame.core :as rf]
             [tailwind.subs :as subs]
             [tailwind.events :as evt]
             [reagent.core :as r]
            ;
             ["react" :as react]
            )
  )

(defn public
  []
  [:div {:class "min-h-screen bg-gray-50 flex flex-col justify-center py-12 sm:px-6 lg:px-8"}
   [:div {:class "sm:mx-auto sm:w-full sm:max-w-md"}
    [:h2 {:class "mt-6 text-center text-3xl font-extrabold text-gray-900"} "Sign in to your account"]
    [:p {:class "mt-2 text-center text-sm text-gray-600 max-w"} "Or "
     [:a {:href "#" :class "font-medium text-pink-600 hover:text-pink-500"} "start your 14-day free trial"]]]
   [:div {:class "mt-8 sm:mx-auto sm:w-full sm:max-w-md"}
    [:div {:class "bg-white py-8 px-4 shadow sm:rounded-lg sm:px-10"}
     [:form {:class "space-y-6" :action "#" :method "POST"}
      [:div
       [:label {:for "email" :class "block text-sm font-medium text-gray-700"} "Email address"]
       [:div {:class "mt-1"}
        [:input {:id "email" :name "email" :type "email" :auto-complete "email" :required true :class "appearance-none block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm placeholder-gray-400 focus:outline-none focus:ring-pink-500 focus:border-pink-500 sm:text-sm"}]]]
      [:div
       [:label {:for "password" :class "block text-sm font-medium text-gray-700"} "Password"]
       [:div {:class "mt-1"}
        [:input {:id "password" :name "password" :type "password" :auto-complete "current-password" :required true :class "appearance-none block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm placeholder-gray-400 focus:outline-none focus:ring-pink-500 focus:border-pink-500 sm:text-sm"}]]]
      [:div {:class "flex items-center justify-between"}
       [:div {:class "flex items-center"}
        [:input {:id "remember_me" :name "remember_me" :type "checkbox" :class "h-4 w-4 text-pink-600 focus:ring-pink-500 border-gray-300 rounded"}]
        [:label {:for "remember_me" :class "ml-2 block text-sm text-gray-900"} "Remember me"]]
       [:div {:class "text-sm"}
        [:a {:href "#" :class "font-medium text-pink-600 hover:text-pink-500"} "Forgot your password?"]]]
      [:div
       [:button
        {:type "submit"
         :on-click #(events/login)
         :class "w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-pink-600 hover:bg-pink-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-pink-500"}
        "Sign in"]]]]]])

(defn authenticated
  []
  [:div {:class "h-screen flex overflow-hidden bg-white"}
   [:div {:class "hidden lg:flex lg:flex-shrink-0"}
    [:div {:class "flex flex-col w-64 border-r border-gray-200 pt-5 pb-4 bg-gray-100"}
     [:div {:class "flex items-center flex-shrink-0 px-6"}
      [:div {:class "text-center text-3xl font-bold text-gray-900"} "App"]]
     [:div {:class "h-0 flex-1 flex flex-col overflow-y-auto"}
      [:div {:class "px-3 mt-6 relative inline-block text-left"}
       [:div
        [:button {:type "button"
                  :on-click #(events/toggle-user-dropdown)
                  :class "group w-full bg-gray-100 rounded-md px-3.5 py-2 text-sm font-medium text-gray-700 hover:bg-gray-200 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-gray-100 focus:ring-pink-500"
                  :id "options-menu"
                  :aria-expanded "false"
                  :aria-haspopup "true"}
         [:span {:class "flex w-full justify-between items-center"}
          [:span {:class "flex min-w-0 items-center justify-between space-x-3"}
           [:img {:class "w-10 h-10 bg-gray-300 rounded-full flex-shrink-0" :src "https://images.unsplash.com/photo-1502685104226-ee32379fefbe?ixlib=rb-1.2.1&ixqx=4cZVjZZC0A&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=3&w=256&h=256&q=80" :alt ""}]
           [:span {:class "flex-1 min-w-0"}
            [:span {:class "text-gray-900 text-sm font-medium truncate"} "Jessy Schwarz"]
            [:br]
            [:span {:class "text-gray-500 text-sm truncate"} "@jessyschwarz"]]]
          [:svg {:class "flex-shrink-0 h-5 w-5 text-gray-400 group-hover:text-gray-500" :xmlns "http://www.w3.org/2000/svg" :viewBox "0 0 20 20" :fill "currentColor" :aria-hidden "true"}
           [:path {:fill-rule "evenodd" :d "M10 3a1 1 0 01.707.293l3 3a1 1 0 01-1.414 1.414L10 5.414 7.707 7.707a1 1 0 01-1.414-1.414l3-3A1 1 0 0110 3zm-3.707 9.293a1 1 0 011.414 0L10 14.586l2.293-2.293a1 1 0 011.414 1.414l-3 3a1 1 0 01-1.414 0l-3-3a1 1 0 010-1.414z" :clip-rule "evenodd"}]]]]]
       (when (:user-dropdown? @db/state)
         [:div {:class "z-10 mx-3 origin-top absolute right-0 left-0 mt-1 rounded-md shadow-lg bg-white ring-1 ring-black ring-opacity-5 divide-y divide-gray-200 focus:outline-none" :role "menu" :aria-orientation "vertical" :aria-labelledby "options-menu"}
          [:div {:class "py-1" :role "none"}
           [:a {:href "#" :class "block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 hover:text-gray-900" :role "menuitem"} "View profile"]
           [:a {:href "#" :class "block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 hover:text-gray-900" :role "menuitem"} "Settings"]
           [:a {:href "#" :class "block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 hover:text-gray-900" :role "menuitem"} "Notifications"]]
          [:div {:class "py-1" :role "none"}
           [:a {:href "#"
                :on-click #(events/logout)
                :class "block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 hover:text-gray-900" :role "menuitem"}
            "Logout"]]])]
      [:nav {:class "px-3 mt-6"}
       [:div {:class "space-y-1"}
        [:a {:href "#" :class "bg-gray-200 text-gray-900 group flex items-center px-2 py-2 text-sm font-medium rounded-md"}
         [:svg {:class "text-gray-500 mr-3 h-6 w-6" :xmlns "http://www.w3.org/2000/svg" :fill "none" :viewBox "0 0 24 24" :stroke "currentColor" :aria-hidden "true"}
          [:path {:stroke-linecap "round" :stroke-linejoin "round" :stroke-width "2" :d "M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6"}]] "Home"]
        [:a {:href "#" :class "text-gray-700 hover:text-gray-900 hover:bg-gray-50 group flex items-center px-2 py-2 text-sm font-medium rounded-md"}
         [:svg {:class "text-gray-400 group-hover:text-gray-500 mr-3 h-6 w-6" :xmlns "http://www.w3.org/2000/svg" :fill "none" :viewBox "0 0 24 24" :stroke "currentColor" :aria-hidden "true"}
          [:path {:stroke-linecap "round" :stroke-linejoin "round" :stroke-width "2" :d "M4 6h16M4 10h16M4 14h16M4 18h16"}]] "My tasks"]]
       [:div {:class "mt-8"}
        [:h3 {:class "px-3 text-xs font-semibold text-gray-500 uppercase tracking-wider" :id "teams-headline"} "Teams"]
        [:div {:class "mt-1 space-y-1" :role "group" :aria-labelledby "teams-headline"}
         [:a {:href "#" :class "group flex items-center px-3 py-2 text-sm font-medium text-gray-700 rounded-md hover:text-gray-900 hover:bg-gray-50"}
          [:span {:class "w-2.5 h-2.5 mr-4 bg-pink-500 rounded-full" :aria-hidden "true"}]
          [:span {:class "truncate"} "Engineering"]]
         [:a {:href "#" :class "group flex items-center px-3 py-2 text-sm font-medium text-gray-700 rounded-md hover:text-gray-900 hover:bg-gray-50"}
          [:span {:class "w-2.5 h-2.5 mr-4 bg-green-500 rounded-full" :aria-hidden "true"}]
          [:span {:class "truncate"} "Human Resources"]]]]]]]]
   [:div {:class "flex flex-col w-0 flex-1 overflow-hidden"}
    [:main {:class "flex-1 relative z-0 overflow-y-auto focus:outline-none" :tabIndex "0"}
     [:div {:class "px-4 mt-6 sm:px-6 lg:px-8"}
      [:h2 {:class "text-gray-500 text-xs font-medium uppercase tracking-wide"} "Hello world !!! "]]]]])




(defn counter-view []
  [:div.mt-6.px-6
   [:h2 "Counter"]
   [:div
    [:button.p-2.bg-blue-200 {
              :on-click #(rf/dispatch [::evt/decrement2])
    } 
     "-"
     ]
     [:button.p-2.bg-blue-100 {:disabled true}
      @(rf/subscribe [::subs/counter])
      ]
     [:button.p-2.bg-blue-200 {
                :on-click #(rf/dispatch [::evt/increment2])
               } 
     "+"
     ]
    ]
   ]
  )


(defn use-outside-click [ref callback]
  (.info js/console " -- inside use outside click -- ")
  (react/useEffect
   (fn []
     (let [handle-click (fn [e]
                          (if
                             (and ref (not (.contains (.-current ref) (.-target e)) ) )                                                  
                            (do 
                             ; (.info js/console "invoke callback")
                              (callback)
                              )
                            (.info js/console "clicked inside")
                            )
                          )
           ]
       (js/document.addEventListener "click" handle-click)
       (fn []
         (js/document.removeEventListener "click" handle-click)))))
  )


; using react use ref and use effect hooks  
(defn menu-view []
  (let  
       [
         ref (react/useRef)
         _   (.info js/console " ref: " ref)
        outside-comp (use-outside-click ref #(js/alert "You clicked outside"))
        ]
    (println " outside comp initialized: " outside-comp)
     [:div {:class "bg-gray-300"}
          [:div {:ref ref}
             [:h4 "This is a menu"]
             [:p "This is another content"]
           ]
          [:div "This is a content outside the menu"]
        ]
    )
  )


(defn drop-down []
  (let 
       [
          
           [open setOpen]  (react/useState false)
           ref (react/useRef)
           _   (.info js/console " ref: " ref)
           _   (use-outside-click ref (fn []
                                        (.info js/console "You clicked outside")
                                          (setOpen false)
                                        ))
       ]  
      [:div {
               :class "p-8"
               :ref ref
            }
        [:button {
                    :class "inline-flex items-center
                            py-2 px-6 bg-purple-600 hover:bg-opacity-95
                          text-white rounded-md shadow"                    
                    :on-click (fn [e]                               
                                 (setOpen (not open))
                                 (.info js/console " open: " open)
                              )
                  }
         "Toggle"
            [:svg {:xmlns "(link unavailable)"
       :class (str "h-4 w-4 ml-2 transition-all " (when open "rotate-90"))
       :fill "none"
       :viewBox "0 0 24 24"
       :strokeWidth 1.5
       :stroke "currentColor"}
 [:path {:strokeLinecap "round"
         :strokeLinejoin "round"
         :d "m8.25 4.5 7.5 7.5-7.5 7.5"}]]
         ]
         (when (= open true)
           [:ul {
                 ; :ref ref
                  :class "bg-white w-[160px] shadow-md py-1"
                 }
              [:li 
                 [:a {
                      :class "py-1 px-2 hover:bg-gray-200 flex items-center"
                      :href "#"
                      } 
                     

                  [:svg {:xmlns "(link unavailable)"
       :fill "none"
       :viewBox "0 0 24 24"
       :strokeWidth 1.5
       :stroke "currentColor"
       :class "h-4 w-4 mr-2"}
 [:path {:strokeLinecap "round"
         :strokeLinejoin "round"
         :d "M2.036 12.322a1.012 1.012 0 0 1 0-.639C3.423 7.51 7.36 4.5 12 4.5c4.638 0 8.573 3.007 9.963 7.178.07.207.07.431 0 .639C20.577 16.49 16.64 19.5 12 19.5c-4.638 0-8.573-3.007-9.963-7.178Z"}]
 [:path {:strokeLinecap "round"
         :strokeLinejoin "round"
         :d "M15 12a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z"}]]
                  
                  "Open"]
               ]
                [:li 
                  [:a {
                        :class "py-1 px-2 hover:bg-gray-200 flex items-center"
                       :href "#"
                       } 
                    [:svg {:xmlns "(link unavailable)"
                           :class="h-4 w-4 mr-2"
       :fill "none"
       :viewBox "0 0 24 24"
       :strokeWidth 1.5
       :stroke "currentColor"
       :class "h-4 w-4 mr-2"}
 [:path {:strokeLinecap "round"
         :strokeLinejoin "round"
         :d "m16.862 4.487 1.687-1.688a1.875 1.875 0 1 1 2.652 2.652L10.582 16.07a4.5 4.5 0 0 1-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 0 1 1.13-1.897l8.932-8.931Zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0 1 15.75 21H5.25A2.25 2.25 0 0 1 3 18.75V8.25A2.25 2.25 0 0 1 5.25 6H10"}]
                     ]
                   "Edit"
                   ]
                ]
                [:li 
                  [:a {
                        :class "py-1 px-2 hover:bg-gray-200 flex items-center"
                       :href "#"
                       } 
                    [:svg {:xmlns "(link unavailable)"
                           :class="h-4 w-4 mr-2"
       :fill "none"
       :viewBox "0 0 24 24"
       :strokeWidth 1.5
       :stroke "currentColor"
       :class "h-4 w-4 mr-2"}
 [:path {:strokeLinecap "round"
         :strokeLinejoin "round"
         :d "m14.74 9-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 0 1-2.244 2.077H8.084a2.25 2.25 0 0 1-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 0 0-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 0 1 3.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 0 0-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 0 0-7.5 0"}]]
                   "Delete"]
                ]               
            ]
           )
       ]
    )
  )