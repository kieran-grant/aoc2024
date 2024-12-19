(ns day03.core
  (:gen-class))

(def regex #"mul\(([\d]+),([\d]+)\)")

(def input (slurp "input.txt"))

(def matches (map rest (re-seq regex input)))

(defn multiply-pairs [[x y]] (* (read-string x) (read-string y)))

(def solution1 (reduce + (map multiply-pairs matches)))

(defn -main
  "I don't do a whole lot ... yet."
  [& _args]
  (println "Part 1 solution: " solution1))
