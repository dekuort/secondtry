(ns secondtry.core
  (:require [next.jdbc :as jdbc]
            [clojure.data.json :as json]))

(def db-spec {:dbtype "postgresql"
            :dbname "postgres"
            :host "127.0.0.1"
            :user "cljtest"
            :password "test1234" 
              })

(defn get-patient-list
  "I don't do a whole lot."
  []
  (json/write-str (jdbc/execute! db-spec [(str "SELECT * FROM patient")])))


(defn add-patient [f-name m-name l-name sex b-day adddr ins-number]
  (jdbc/execute! db-spec [(str "insert into patient values ('"
                          f-name"',
                          '"m-name"',
                          '"l-name"',
                          '"sex"',
                          '"b-day"',
                          '"adddr"',"
                          ins-number")")]))