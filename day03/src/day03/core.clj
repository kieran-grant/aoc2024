(ns day03.core
  (:gen-class)
  (:require
   [clojure.string]))

(def regex #"mul\(([\d]+),([\d]+)\)")

(def input (slurp "input.txt"))

(def matches (map rest (re-seq regex input)))

(defn multiply-pairs [[x y]] (* (read-string x) (read-string y)))

(def solution1 (reduce + (map multiply-pairs matches)))

(def regex-dont-blocks #"(?x)don't\(\)[\s\S]*?(?=do\(\)|$)")

(def input-without-dont (clojure.string/replace input regex-dont-blocks ""))

(def matches-without-dont (map rest (re-seq regex input-without-dont)))

(def solution2 (reduce + (map multiply-pairs matches-without-dont)))

(defn -main
  "I don't do a whole lot ... yet."
  [& _args]
  (println "Part 1 solution: " solution1)
  (println "Part 2 solution: " solution2))
