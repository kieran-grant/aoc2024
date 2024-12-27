(ns aoc2024.day02
  (:gen-class)
  (:require
   [clojure.string :as string]))

(def filename "input.txt")

(defn parse [filename]
  (->> filename
       slurp
       (string/split-lines) ;; split by lines
       (map (fn [line]
              (->> (string/split line #"[\t ]")
                   (map #(read-string %)))))))

(def contents (parse filename))

(defn zip-with
  [fn list-a list-b]
  (map fn list-a list-b))

(defn safely-increasing?
  "Checks whether the given list is monotonically increasing within the bound"
  [item]
  (and (pos? item) (<= item 3)))

(defn safely-decreasing?
  "Checks whether the given list is monotonically decreasing within the bound"
  [item]
  (and (neg? item) (>= item -3)))

(defn safe?
  [list]
  (let [zipped (zip-with - list (rest list))]
    (and (> (count zipped) 0)
         (or (every? safely-increasing? zipped)
             (every? safely-decreasing? zipped)))))

(defn remove-each [lst]
  (map #(concat (take % lst) (drop (inc %) lst))
       (range (count lst))))

(defn kind-of-safe?
  [list]
  (some safe? (remove-each list)))

(def solution1 (count (filter safe? contents)))

(def solution2 (count (filter #(or (safe? %) (kind-of-safe? %)) contents)))

(defn -main
  [& _args]
  (println "Part 1 solution:" solution1)
  (println "Part 2 solution:" solution2)) ;; Use contents in -main if needed
