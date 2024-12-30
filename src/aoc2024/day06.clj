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

(defn guard-path
  ([grid]
   (guard-path grid (find-start grid) :north #{}))
  ([grid curr-pos dir visited]
   (let [new-visited (cons curr-pos visited)
         new-pos (move curr-pos dir)]
     (case (grid new-pos)
       nil (set new-visited)
       \# (recur grid curr-pos (turn-right dir) new-visited)
       (recur grid new-pos dir new-visited)))))

(defn part-1
  [grid]
  (->> grid guard-path count))

(part-1 example-data)
(part-1 data)

(defn is-loop?
  ([grid start]
   (is-loop? grid start :north #{}))
  ([grid curr-pos dir visited]
   (let [elem-pair [curr-pos dir]
         new-visited (conj visited elem-pair)
         new-pos (move curr-pos dir)
         next-step (grid new-pos)]
     (cond
       (visited elem-pair) 1
       (nil? next-step) 0
       (= \# next-step) (recur grid curr-pos (turn-right dir) new-visited)
       :else (recur grid new-pos dir new-visited)))))

(defn part-2
  ([grid]
   (let [start (find-start grid)]
     (part-2 grid (guard-path grid) start)))
  ([grid path start]
   (reduce + (pmap #(is-loop? (conj grid [% \#]) start) path))))

(part-2 example-data)
(part-2 data)

