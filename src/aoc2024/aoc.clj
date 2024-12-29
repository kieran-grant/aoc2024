(ns aoc2024.aoc
  (:gen-class)
  (:require
   [clojure.string :as str]
   [clojure.data.int-map :as i]))

(defn read-input
  [file]
  (let [name (if (int? file)
               (format "%02d" file)
               file)]
    (slurp (str "inputs/" name ".txt"))))

(defn integers
  [s & {:keys [negative?]
        :or {negative? true}}]
  (mapv parse-long
        (re-seq (if negative? #"-?\d+" #"\d+") s)))

(defn string->digits [s]
  (->> (str/split s #"")
       (map parse-long)
       (filterv some?)))

(defn parse-line [line & [parse-fn word-sep]]
  (let [f (case parse-fn
            :int    parse-long
            :ints   integers
            :digits string->digits
            :chars  vec
            :words  #(str/split % (or word-sep #" "))
            nil     identity
            parse-fn)]
    (f line)))

(defn parse-lines
  [s & [parse-fn {:keys [word-sep nl-sep]}]]
  (mapv #(parse-line % parse-fn word-sep)
        (str/split s (or nl-sep #"\n"))))

(defn parse-paragraphs
  [input & [parse-fn word-sep]]
  (mapv #(parse-lines % parse-fn {:word-sep word-sep})
        (parse-lines input nil {:nl-sep #"\n\n"})))

(defn grid->point-map
  ([v] (grid->point-map v identity nil))
  ([v pred] (grid->point-map v pred nil))
  ([v pred mult]
   (into (if mult (i/int-map) {})
         (for [[^long y line] (map-indexed vector v)
               [^long x char] (map-indexed vector line)
               :when (pred char)]
           (if mult
             [(+ (* y ^long mult) x) char]
             [[x y] char])))))
