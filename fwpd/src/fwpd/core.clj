(ns fwpd.core)

(def filename "suspects.csv")

(def vamp-keys [:name :glitter-index])

(defn str->int
  [str]
  (Integer. str))

(def conversions {:name identity :glitter-index str->int})

(def validations {:name string? :glitter-index string?})

(defn vali
  [validation-map key value]
  (if (contains? validation-map key)
    ((get validation-map key) value)
    false))

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

(defn vali-all-present
  [validation-map record]
  (reduce-kv (fn [result key value]
               (and result (contains? record key)))
             true
             validation-map))

(defn vali-only
  [validation-map record]
  (reduce-kv (fn [result key value]
      (and result (vali validation-map key value)))
      true
      record))

(defn validate
  [validation-map record]
  (and
    (vali-all-present validation-map record)
    (vali-only validation-map record)))

(defn convert-suspect
  [new-suspect]
  (reduce-kv (fn [mapped-suspect key value]
        (assoc mapped-suspect key (convert key value)))
        {}
        new-suspect))

(defn append
  [suspect-list new-suspect]
  (if (validate validations new-suspect )
    (conj suspect-list (convert-suspect new-suspect))
    suspect-list)
  )

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
