(ns secondtry.web
  (:require [compojure.core :refer [defroutes GET POST]]
            [compojure.route :as route]
            [ring.adapter.jetty :as ring]
            [hiccup.page :as page]
            [clojure.data.json :as json]
            [secondtry.core :as stryc]
            [clojure.pprint]
            [hiccup.table :as ht]
            [cheshire.core :as cc]))

(defn map-tag [tag xs]
  (map (fn [x] [tag x]) xs))

(defn index [] 
   (let [patient-list (cc/parse-string (stryc/get-patient-list) true)]
    (page/html5 {:status 200}
              [:head
               [:title "Hello World"]]
              [:body 
               (ht/to-table1d patient-list [:first_name "First Name" 
                                             :mid_name "Mid Name" 
                                             :last_name "Last Name" 
                                             :sex "Sex" 
                                             :birthday "Birthday" 
                                             :address "Address" 
                                             :insurance_number "Insurance"])
               [:div {:id "content"} "Hello World"]])))

(defn nf []
  (page/html5
   [:head
    [:title  "Hello World"]]
   [:body
    [:h1 "Page not found"]]))

(defn json-response [data & [status]]
  {:status (or status 200)
   :headers {"Content-Type" "application/json"}
   :body (json/write-str data)})

(defn create-table []
  
  )

(defroutes app
  (GET "/" [] (index))
  (GET "/api/patients" []
    {:status 200
     :headers {"Content-Type" "application/json"}
     :body (stryc/get-patient-list)})
  (POST "/api/add-patient" request (stryc/add-patient (slurp (:body request))))
  (POST "/api/delete-patient" request (stryc/delete-patient (slurp (:body request)))) 
  (route/not-found (nf)))

(defn -main []
  (ring/run-jetty #'app {:port 8081 :join? false}))