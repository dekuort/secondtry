(ns secondtry.web
  (:require [compojure.core :refer [defroutes GET POST]]
            [compojure.route :as route]
            [ring.adapter.jetty :as ring]
            [hiccup.page :as page]
            [clojure.data.json :as json]
            [secondtry.core :as stryc]
            [clojure.pprint]
            [hiccup.table :as ht]
            [hiccup.form :as form]
            [cheshire.core :as cc]
            [ring.util.anti-forgery :as anti-forgery]))

(defn map-tag [tag xs]
  (map (fn [x] [tag x]) xs))

(defn index [] 
   (let [patient-list (cc/parse-string (stryc/get-patient-list) true)]
    (page/html5 {:status 200}
              [:head
               [:title "HealthSamurai Test Task"]]
              [:body 
               (ht/to-table1d patient-list [:first_name "First Name" 
                                             :mid_name "Mid Name" 
                                             :last_name "Last Name" 
                                             :sex "Sex" 
                                             :birthday "Birthday" 
                                             :address "Address" 
                                             :insurance_number "Insurance"]
                              {:table-attrs {:class "patients-table"}
                               :th-attrs (fn [label-key _] {:class (name label-key)})})
               [:br]
              [:div {:id "add-patient-form"}
               (form/form-to [:post "/api/test"]
                             (anti-forgery/anti-forgery-field)
                             (form/label "add-patient" "Fill a form for add patient information")
                             [:br]
                             "First name: "
                             (form/text-field "first-name")
                             [:br]
                             "Midle name: "
                             (form/text-field "middle-name")
                             [:br]
                             "Last name: "
                             (form/text-field "last-name")
                             [:br]
                             "Sex: "
                             (form/text-field "sex")
                             [:br]
                             "Birthday"
                             (form/text-field "birthday")
                             [:br]
                             "Address: "
                             (form/text-field "address")
                             [:br]
                             "Insurance number: "
                             (form/text-field "insurance-number")
                             [:br]
                             (form/submit-button "Add" )
                             )]
               ])))

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
 (POST "/api/test" request (slurp (:body request))) 
  (route/not-found (nf)))

(defn -main []
  (ring/run-jetty #'app {:port 8081 :join? false}))