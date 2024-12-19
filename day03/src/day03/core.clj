(ns day03.core
  (:gen-class)
  (:require [clojure.string :as str]))

(def input (slurp "input.txt"))

(defn get-mult-ops [input]
  (->> input
       (re-seq #"mul\(([\d]+),([\d]+)\)")
       (map rest)))

(defn calculate-sum [matches]
  (->> matches
       (map (fn [[x y]] (* (read-string x) (read-string y))))
       (reduce +)))

(defn get-total-from-dump [dump]
  (-> dump
      (get-mult-ops)
      (calculate-sum)))

(def solution1
  (get-total-from-dump input))

(def solution2
  (-> input
      (str/replace #"don't\(\)[\s\S]*?(?=do\(\)|$)" "") ; remove don't() blocks
      (get-total-from-dump)))

(defn -main [& _args]
  (println "Part 1 solution:" solution1)
  (println "Part 2 solution:" solution2))
