(ns day02.core
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

(defn zip-self-with
  [fn list]
  (map fn list (rest list)))

(defn safely-increasing?
  "Checks whether the given list is monotonically increasing within the bound"
  [list]
  (every? #(and (pos? %) (<= % 3)) (zip-self-with - list)))

(defn safely-decreasing?
  "Checks whether the given list is monotonically decreasing within the bound"
  [list]
  (every? #(and (neg? %) (>= % -3)) (zip-self-with - list)))

(defn safe?
  [list]
  (and (> (count list) 1)
       (or (safely-increasing? list) (safely-decreasing? list))))

(def safe-reports (filter safe? contents))

(def solution1 (count safe-reports))

(defn -main
  "I don't do a whole lot ... yet."
  [& _args]
  (println "Part 1 solution:" solution1)) ;; Use contents in -main if needed
