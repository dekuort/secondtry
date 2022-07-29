(ns secondtry.web
  (:require [compojure.core :refer [defroutes GET POST]]
            [compojure.route :as route]
            [ring.adapter.jetty :as ring]
            [hiccup.page :as page]
            [clojure.data.json :as json]
            [secondtry.core :as stryc]))

(defn index []
  (page/html5 {:status 200}
   [:head
    [:title "Hello World"]]
   [:body
    [:div {:id "content"} "Hello World"]]))

(defn nf []
  (page/html5
   [:head
    [:title "Hello World"]]
   [:body
    [:h1 "Page not found"]]))

(defn json-response [data & [status]] 
  {:status (or status 200)
   :headers {"Content-Type" "application/json"}
   :body (json/write-str data)})

(defroutes routes
  (GET "/" [] (index))
  (GET "/api/patients" [] 
    {:status 200
     :headers {"Content-Type" "application/json"}
     :body (stryc/get-patient-list)})
  (POST "/api/add-patient" [fname, mname, lname, sex, bday, addr, insnum] 
    {:status 200
     :headers {"Content-Type" "text/plain; charset=utf-8"}
     :body(stryc/add-patient fname mname lname sex bday addr insnum)})
  (route/not-found (nf)))


(defn -main []
  (ring/run-jetty #'routes {:port 8081 :join? false}))