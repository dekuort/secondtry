(ns secondtry.core
  (:require [next.jdbc :as jdbc]
            [next.jdbc.sql :as sql]
            [clj-time.core :as t]
            [clj-time.format :as f]
            [clj-time.coerce]
            [clojure.data.json :as json]
            [cheshire.core :as cc]))

(def db-spec {:dbtype "postgresql"
            :dbname "postgres"
            :host "127.0.0.1"
            :user "cljtest"
            :password "test1234" 
              })

(defn get-patient-list []
  (json/write-str (jdbc/execute! db-spec [(str "SELECT * FROM patient")])))


(defn add-patient [json-string]
  (let [json-list (cc/parse-string json-string true)]
   (sql/insert! db-spec :patient {
                                 :first_name (json-list :first_name)
                                 :mid_name (json-list :middle_name)
                                 :last_name (json-list :last_name)
                                 :sex (json-list :sex)
                                 :birthday (clj-time.coerce/to-sql-date (json-list :birth_day))
                                 :address (json-list :address)
                                 :insurance_number (json-list :insurance_number)})))

(defn delete-patient [json-string]
  (let [json-list (cc/parse-string json-string true)]
    (sql/delete! db-spec :patient{
                                  :insurance_number (json-list :insurance_number)})))