(ns refactoring-exercises.video-store.customer
  (:require [refactoring-exercises.video-store.movie :as movie]))

(defn create [name]
  {:type :customer :name name})

(defn add-rental [customer rental]
  (update-in customer [:rentals] (fnil conj []) rental))

(defn amount-for [rental]
  (condp = (:price-code (:movie rental))
    movie/REGULAR (+ 2 (if (< 2 (:days rental))
                         (* 1.5 (- (:days rental) 2))
                         0))
    movie/NEW_RELEASE (* 3 (:days rental))
    movie/CHILDRENS (+ 1.5 (if (< 3 (:days rental))
                             (* 1.5 (- (:days rental) 3))
                             0))))

(defn frequent-points-for [rental]
  (if (and (< 1 (:days rental))
           (= movie/NEW_RELEASE
              (:price-code (:movie rental))))
    2
    1))

(defn format-line-item-for [rental amount]
  (str "\t" (:title (:movie rental)) "\t" (double amount) "\n"))

(defn summary-for [customer]
  (reduce (fn [[line-items amount points]
               rental]
            (let [this-amount (amount-for rental)]
              [(str line-items (format-line-item-for rental this-amount))
               (+ amount this-amount)
               (+ points (frequent-points-for rental))
               ]))
          ["" 0.0 0]
          (:rentals customer)))

(defn statement-body [customer]
  (zipmap
    [:body-text :total-amount :frequent-renter-points]
    (summary-for customer)))

(defn statement-header [customer]
  (format "Rental Record for %s\n" (:name customer)))

(defn statement-footer [amount points]
  (format "You owed %.1f\nYou earned %d frequent renter points\n"
          amount
          points))

(defn statement [customer]
  (let [{:keys [body-text total-amount frequent-renter-points]}
        (statement-body customer)]
    (str (statement-header customer)
         body-text
         (statement-footer total-amount frequent-renter-points))))
