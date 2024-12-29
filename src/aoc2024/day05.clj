(ns aoc2024.day05
  (:require [aoc2024.aoc :as aoc]))

(def example "47|53
97|13
97|61
97|47
75|29
61|13
75|53
29|13
97|29
53|29
61|53
97|53
61|29
47|13
75|47
97|75
47|61
75|61
47|29
75|13
53|13

75,47,61,53,29
97,61,53,29,13
75,29,13
75,97,47,61,53
61,13,29
97,13,75,29,47")

(defn parse-data [input]
  (aoc/parse-paragraphs input :ints))

(def example-data (parse-data example))

(def data (parse-data (aoc/read-input 5)))

(defn valid-line?
  [rules, line]
  (every? rules (partition 2 1 line)))

(defn middle-page
  [line]
  (line (quot (count line) 2)))

(defn part-1
  [[rules updates]]
  (let [rule-set (set rules)]
    (->> updates
         (filter (partial valid-line? rule-set))
         (map middle-page)
         (reduce +))))

(part-1 example-data)
(part-1 data)

(defn sort-line
  [rules line]
  (->> line
       (sort (fn [a, b] (if (rules [a b]) -1 1)))
       vec))

(defn part-2
  [[rules updates]]
  (let [rule-set (set rules)]
    (->> updates
         (remove (partial valid-line? rule-set))
         (map (partial sort-line rule-set))
         (map middle-page)
         (reduce +))))

(part-2 example-data)
(part-2 data)
