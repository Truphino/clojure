(ns clojure-noob.core
  (:gen-class))

(defn inc100
  "Inc 100"
  [number]
  (+ number 100)
)

(defn dec_maker
  "Make dec"
  [dec]
  #(- % dec)
)

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (if true
    "I am a dwarf"
    "I'm not a dwarf")
)
