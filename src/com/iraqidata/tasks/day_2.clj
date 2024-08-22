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
;; => 336776

;; Each row represents a flight,
;; Total number of flights = Number of rows = 336776
;;;;;;;;;;;;;;;;;;

;; 2. How many unique carriers were there in total?
;;;;;;;;;;;;;;;;;;
(-> ds
    (tc/unique-by  :carrier)
    (tc/row-count))
;; => 16

;; 16 unique carriers
;;;;;;;;;;;;;;;;;;

;; 3. How many unique airports were there in total?
;;;;;;;;;;;;;;;;;;
(-> ds
    (tc/pivot->longer  [:origin :dest])
    (tc/unique-by :$value)
    (tc/row-count))
;; => 107

;; 107 unique airports
;;;;;;;;;;;;;;;;;;

;; 4. What is the average arrival delay for each month?
;;;;;;;;;;;;;;;;;;
(-> ds
    (tc/group-by :month)
    (tc/mean :arr-delay))
;; => _unnamed [12 2]:
;;    | :$group-name |     summary |
;;    |-------------:|------------:|
;;    |            1 |  6.12997197 |
;;    |           10 | -0.16706269 |
;;    |           11 |  0.46134737 |
;;    |           12 | 14.87035529 |
;;    |            2 |  5.61301936 |
;;    |            3 |  5.80757652 |
;;    |            4 | 11.17606298 |
;;    |            5 |  3.52150882 |
;;    |            6 | 16.48132964 |
;;    |            7 | 16.71130668 |
;;    |            8 |  6.04065239 |
;;    |            9 | -4.01836357 |

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
;; => _unnamed [1 2]:
;;    |           :$group-name | summary |
;;    |------------------------|--------:|
;;    | Hawaiian Airlines Inc. |  4983.0 |

;; Hawaiian Airlines Inc.
;;;;;;;;;;;;;;;;;;

;; Full mark. 7 points.
