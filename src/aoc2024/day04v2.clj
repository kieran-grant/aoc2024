(ns aoc2024.day04v2
  (:gen-class)
  (:require
   [aoc2024.aoc :as aoc]))

(def example "MMMSXXMASM
MSAMXMSMSA
AMXSXMAAMM
MSAMASMSMX
XMASAMXAMM
XXAMMXXAMA
SMSMSASXSS
SAXAMASAAA
MAMMMXMMMM
MXMXAXMASX")

(defn parse-data [data]
  (-> data
      aoc/parse-lines
      aoc/grid->point-map))

(def example-grid (parse-data example))

(def grid (parse-data (aoc/read-input 4)))

(def letters (map-indexed vector "XMAS"))

(defn part-1
  [grid]
  (count (for
          [[[x y] c] grid
           :when (= \X c)
           dx [-1 0 1]
           dy [-1 0 1]
           :when (every? (fn [[i l]] (= l (grid [(+ x (* i dx))
                                                 (+ y (* i dy))]))) letters)] 1)))

(def solution1 (part-1 example-grid))
