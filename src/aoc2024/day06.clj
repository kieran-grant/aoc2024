(ns aoc2024.day06
  (:require [aoc2024.aoc :as aoc]))

(def example-input "....#.....
.........#
..........
..#.......
.......#..
..........
.#..^.....
........#.
#.........
......#...")

(defn parse-data [data]
  (-> data
      aoc/parse-lines
      aoc/grid->point-map))

(def example-data (parse-data example-input))

(def data (parse-data (aoc/read-input 6)))

(defn find-start
  [grid]
  (first (for [[k v] grid :when (= v \^)] k)))

(defn transpose
  "Transposes a sequence of pairs into two sequences."
  [pairs]
  (apply map vector pairs))

(defn get-bounds
  [grid]
  (let [[xs ys] (transpose (keys grid))]
    [(apply max xs) (apply max ys)]))

(def dirs {:north [0, -1]
           :east [1, 0]
           :south [0, 1]
           :west [-1, 0]})

(find-start example-data)
(get-bounds example-data)

(defn move [curr dir]
  (map + curr (dirs dir)))

(move [4,6] :north)

