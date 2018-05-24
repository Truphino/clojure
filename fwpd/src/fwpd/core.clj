(ns fwpd.core)

(def filename "suspects.csv")

(def vamp-keys [:name :glitter-index])

(defn str->int
  [str]
  (Integer. str))

(def conversions {:name identity :glitter-index str->int})

(def validates {:name #((true)) :glitter-index #((true))})

(defn vali
  [key value]
  ((get validates key) value))

(defn convert
  [vamp-key value]
  ((get conversions vamp-key) value))

(defn parse
    "Convert a CSV into rows of columns"
    [string]
    (map #(clojure.string/split % #",")
                (clojure.string/split string #"\n")))

(defn mapify
    "Return a seq of maps like {:name \"Edward Cullen\" :glitter-index 10}"
    [rows]
    (map (fn [unmapped-row]
      (reduce (fn [row-map [vamp-key value]]
       (assoc row-map vamp-key (convert vamp-key value)))
       {}
       (map vector vamp-keys unmapped-row)))
    rows))

(defn glitter-filter
    [minimum-glitter records]
    (reduce (fn [name-list row]
           (conj name-list (:name row) ))
      '()
      (filter #(>= (:glitter-index %) minimum-glitter) records)))

(defn validate
  [key-to-validate record]
  (reduce )
)

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
