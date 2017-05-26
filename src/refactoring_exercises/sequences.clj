(ns refactoring-exercises.sequences)

(defn factorial [n]
  (reduce *' 1 (range 1 (inc n))))

(def first-letters (partial map first))

(defn- next-character [c]
  (-> c int inc char))

(defn- geq [bound c]
  (>= (int bound) (int c)))

(defn character-range [first-inclusive last-inclusive]
  (take-while
    (partial geq last-inclusive)
    (iterate next-character first-inclusive)))
