(ns com.iraqidata.tasks.day-one)


;; 1. Write a function that takes one argument as input and prints that
;; argument.

(defn print_basic [p]
  (println p))

(print_basic "Hello")

;; Correct, one full point.

;; 2. Write a function that adds `1` to a number only if the input is odd,
;; otherwise return `:error`.

;; Tip, use the `odd?` function to check if a number is odd.

(defn add_to_odd [n]
  (if (odd? n) (+ n 1) :error))

(add_to_odd 3)
(add_to_odd 2)

;; Correct, one full point.

;; 3. Write a function that takes 3 arguments, `name`, `year-of-birth`, and
;; `current-year`. and returns a map with the following keys: `name`, `age`.

;; Example run
;; (function-name "Ali" 2001 2024) => {:name "Ali", :age 23}

(defn get_age [name year-of-birth current-year]
  {:name name :age (- current-year year-of-birth)})

(get_age "Adham" 2001 2024)

;; Correct, one full point.

;; 4. Write a function that takes the output of the above function and returns
;; `true` if the person is allowed to vote (assume the voting age is 18).

;; Example run
;; (function-name {:name "Ali", :age 23}) => true
;; (function-name "Ali" 2001 2024) => true
;; (function-name {:name "Abbas", :age 17}) => false

(defn is_vote_allowed [demographic]
  (if (> (get demographic :age) 18) true false))

(is_vote_allowed {:name "Ali", :age 23})
(is_vote_allowed (get_age "Ali" 2001 2024))
(is_vote_allowed {:name "Abbas", :age 17})

;; Correct, one full point.

;; OPTIONAL FOR BONUS POINTS

;; 5. Modify the function from number 3 to not need the `current-year`.
;; Example run
;; (function-name "Ali" 2001) => {:name "Ali", :age 23}
;; If ran in 2025
;; (function-name "Ali" 2001) => {:name "Ali", :age 24}


;; Very dirty solution, sorry!

;; old solution
;; (defn get_age_smart [name year-of-birth]
;;   {:name name :age (- (Integer. (subs  (.toString (new java.util.Date)) 24)) year-of-birth)})


;; Correct, 2.5 points, half a point deducted for the hacks :D

;; Checkout the solutions by Yasmin and Ahmed Shanshal for a cleaner approach

;; Final score: 6.5 points.


;; new solution


(defn get_age_smart [name year-of-birth]
  {:name name :age (- (.getValue (java.time.Year/now)) year-of-birth)})
 
(get_age_smart "Ali" 2001)
