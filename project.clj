(defproject secondtry "0.1.0-SNAPSHOT"
  :description "Test task for HealthSamurai"
  :url "https://github.com/dekuort"
  :min-lein-version "2.0.0"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [com.github.seancorfield/next.jdbc "1.2.780"]
                 [org.postgresql/postgresql "9.4-1201-jdbc41"]
                 [javax.servlet/servlet-api "2.5"]
                 [ring/ring-jetty-adapter "1.4.0"]
                 [compojure "1.7.0"]
                 [ring/ring-defaults "0.1.2"]
                 [hiccup "1.0.5"]
                 [org.clojure/data.json "2.4.0"]]
  
  :repl-options {:init-ns secondtry.core})
