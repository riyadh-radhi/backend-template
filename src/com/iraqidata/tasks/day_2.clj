(ns com.iraqidata.tasks.day-2
  (:require
   [clojure.string :as str]
   scicloj.clay.v2.api
   [tablecloth.api :as tc]
   tech.v3.datatype.casting))

(def ds (tc/dataset "./resources/data/flights.csv"
                    {:key-fn #(keyword (str/replace (name %) "_" "-"))}))

;; 1. How many flights were there in total?
;;;;;;;;;;;;;;;;;;
(-> ds
    tc/row-count)

;; Each row represents a flight, 
;; Total number of flights = Number of rows = 336776 
;;;;;;;;;;;;;;;;;;

;; 2. How many unique carriers were there in total?
;;;;;;;;;;;;;;;;;;
(-> ds
    (tc/unique-by  :carrier)
    (tc/row-count))

;; 16 unique carriers
;;;;;;;;;;;;;;;;;;

;; 3. How many unique airports were there in total?
;;;;;;;;;;;;;;;;;;
(-> ds 
    (tc/pivot->longer  [:origin :dest])
    (tc/unique-by :$value)
    (tc/row-count))

;; 107 unique airports
;;;;;;;;;;;;;;;;;;

;; 4. What is the average arrival delay for each month?
;;;;;;;;;;;;;;;;;;
(-> ds
    (tc/group-by :month)
    (tc/mean :arr-delay))

;;;;;;;;;;;;;;;;;;

;; Optional: Use the `airlines` dataset to get the name of the carrier with the
;; highest average distance.

(def airlines
  (tc/dataset "./resources/data/airlines.csv"
              {:key-fn keyword}))
;;;;;;;;;;;;;;;;;;
(-> ds
    (tc/select-columns [:carrier :distance])
    (tc/inner-join airlines :carrier)
    (tc/group-by :name)
    (tc/mean :distance)
    (tc/order-by "summary" :desc)
    (tc/head 1))

;; Hawaiian Airlines Inc.
;;;;;;;;;;;;;;;;;;