(ns aoc2024.day07
  (:require
   [aoc2024.aoc :as aoc]
   [clojure.math.combinatorics :as comb]))

(def example-input "190: 10 19
3267: 81 40 27
83: 17 5
156: 15 6
7290: 6 8 6 15
161011: 16 10 13
192: 17 8 14
21037: 9 7 18 13
292: 11 6 16 20")

(defn parse-data [input]
  (aoc/parse-lines input :ints))

(def example-data (parse-data example-input))
(def data (parse-data (aoc/read-input 7)))

(defn make-combs [n fns]
  (apply comb/cartesian-product (repeat n fns)))

(defn reduce-with-fns [nums fns]
  (reduce
   (fn [acc [num f]]
     (f acc num))
   (first nums)
   (map vector (rest nums) fns)))

(defn get-combs
  [seeds]
  (let [combs (make-combs (dec (count seeds)) [+ *])]
    (pmap #(reduce-with-fns seeds %) combs)))

(defn has-solution?
  [[target & seeds]]
  (let [combs (get-combs seeds)]
    (true? (some #(= target %) combs))))

(defn part-1
  [inputs]
  (->> inputs
       (filter has-solution?)
       (map first)
       (reduce +)))

(part-1 example-data)
(part-1 data)
