(ns day01.core
  (:gen-class)
  (:require
   [clojure.string :as string]))

(def filename "input.txt")
(def input (slurp filename))

(defn transpose [m]
  (apply mapv vector m))

(defn sorted-ints
  [strings]
  (sort (map read-string strings)))

(def vectors
  (->> input
       (#(string/split % #"\n")) ;; split by lines
       (map #(string/split % #"[\t ]+")) ;; split each line by whitespace
       (transpose)
       (map sorted-ints))) ;; transform into lists of sorted ints

(def solution1
  (reduce + (map (comp abs -) (first vectors) (second vectors))))

(defn similarity-score
  [x other-list]
  (* x (or ((frequencies (vec other-list)) x) 0)))

(def solution2
  (reduce + (map #(similarity-score % (second vectors)) (first vectors))))

(defn -main
  [& _args]
  (println (str "Part 1 solution: " solution1))
  (println (str "Part 2 solution: " solution2)))
