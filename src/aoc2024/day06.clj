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

(def dirs {:north [0, -1]
           :east [1, 0]
           :south [0, 1]
           :west [-1, 0]})

(defn turn-right
  [dir]
  (case dir
    :north :east
    :east :south
    :south :west
    :west :north))

(defn move [curr dir]
  (map + curr (dirs dir)))

(defn part-1
  ([grid]
   (part-1 grid (find-start grid) :north #{}))
  ([grid curr-pos dir visited]
   (let [new-visited (cons curr-pos visited)
         new-pos (move curr-pos dir)]
     (case (grid new-pos)
       nil (count (set new-visited))
       \# (recur grid curr-pos (turn-right dir) new-visited)
       (recur grid new-pos dir new-visited)))))

(part-1 data)
