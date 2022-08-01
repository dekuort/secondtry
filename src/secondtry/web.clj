(ns secondtry.web
  (:require [compojure.core :refer [defroutes GET POST]]
            [compojure.route :as route]
            [ring.adapter.jetty :as ring]
            [hiccup.page :as page]
            [clojure.data.json :as json]
            [secondtry.core :as stryc]
            [clojure.pprint]
            [cheshire.core :as cc]))

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

(defroutes app
  (GET "/" [] (index))
  (GET "/api/patients" []
    {:status 200
     :headers {"Content-Type" "application/json"}
     :body (stryc/get-patient-list)})
  (POST "/api/add-patient" request (stryc/add-patient (slurp (:body request))))
  (route/not-found (nf)))

(defn -main []
  (ring/run-jetty #'app {:port 8081 :join? false}))