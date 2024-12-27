(ns aoc2024.day04
  (:gen-class)
  (:require
   [clojure.string :as string]))

(defn transpose [strings]
  (apply map str (map seq strings)))

(defn reverse-rows [matrix]
  (mapv #(vec (reverse %)) matrix))

(defn diagonals [matrix]
  (let [rows (count matrix)
        cols (count (first matrix))]
    (concat
      ;; Diagonals starting from the first column
     (for [i (range rows)]
       (loop [r i, c 0, diag []]
         (if (and (< r rows) (< c cols))
           (recur (inc r) (inc c) (conj diag (nth (nth matrix r) c)))
           (apply str diag))))

      ;; Diagonals starting from the top row (except the first element)
     (for [j (range 1 cols)]
       (loop [r 0, c j, diag []]
         (if (and (< r rows) (< c cols))
           (recur (inc r) (inc c) (conj diag (nth (nth matrix r) c)))
           (apply str diag)))))))

(defn forward-diagonals [matrix]
  (diagonals matrix))

(defn backwards-diagonals [matrix]
  (diagonals (reverse-rows matrix)))

(def input ((comp string/split-lines slurp) "inputs/04.txt"))

(defn count-xmas-occurances
  [string]
  (->> string
       (re-seq #"XMAS")
       count))

(defn count-samx-occurances
  [string]
  (->> string
       (re-seq #"SAMX")
       count))

(defn count-all
  [strings]
  (reduce + (map #(+ (count-xmas-occurances %) (count-samx-occurances %)) strings)))

(def orientations
  (concat input ; horizontal
          (transpose input) ; vertial
          (forward-diagonals input) ; forward diagonals
          (backwards-diagonals input))); backwards diagonas

(defn -main
  "I don't do a whole lot ... yet."
  [& _args]
  (println orientations))
