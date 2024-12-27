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
          [[[x y] c] grid ; bind coordinates / char from grid at each iteration
           :when (= \X c) ; where the current character == X
           dx [-1 0 1]    ; loop over dx es
           dy [-1 0 1]    ; loop over dy es
                          ; e.g. (dx, dy) = (0,0) (1,0) (0,1) (1,1) ...
           :when
           (every?        ; where full word is satisfied
            (fn [[i l]] (= l (grid [(+ x (* i dx))
                                    (+ y (* i dy))])))
            letters)] ; work over (idx, char) pairs in the word "XMAS"
           1))) ; mark valid configuration as '1'

(def solution1 (part-1 example-grid))

(defn part-2
  [grid]
  (let [diags #{(vec "MAS") (vec "SAM")}
        deltas [-1 0 1]]
    (count
     (for [[x y] (keys grid)
           :when (and
                  ;; calling hashset like a function returns the element if it exists
                  ;; or nil otherwise
                  (diags (for [d deltas] (grid [(+ x d) (+ y d)])))
                  (diags (for [d deltas] (grid [(- x d) (+ y d)]))))]
       1))))

(def solution2 (part-2 grid))
