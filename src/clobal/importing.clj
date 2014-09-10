(ns clobal.importing
  (:require [clojure.xml :as xml]
            [clojure.zip :as zip]
            [slugger.core :as slug]))




(defn convert-xml
  "This is used to convert the raw xml file from iso-codes"
  [file]
  (:content
   (first
    (zip/xml-zip
     (xml/parse (java.io.ByteArrayInputStream. (.getBytes (slurp file))))))))


(defn flat-xml [xml]
  (map :attrs xml))


(defn assoc-regions
  [m c]
  (assoc m (keyword (:code (:attrs c)))
         (vec (flatten
               (map
                (fn [r]
                  (let [type (keyword (slug/->slug (:type (:attrs r))))]
                    (map #(assoc % :type type
                                 :slug (slug/->slug (:name %))
                                 :code (keyword (:code %)))
                         (flat-xml (:content r)))
                    ))
                (:content c))))))

(defn convert-iso3166-2
  ([] (convert-iso3166-2 "../iso-codes/iso_3166_2/iso_3166_2.xml"))
  ([file]
     (reduce assoc-regions {} (convert-xml file))))

(defn convert-iso3166
  ([] (convert-iso3166 "../iso-codes/iso_3166/iso_3166.xml"))
  ([file]
     (map #(assoc % :numeric_code (Integer/parseInt (:numeric_code %))
                    :slug (slug/->slug (:name %))                  
                    :alpha_2_code (keyword (:alpha_2_code %))
                    :alpha_3_code (keyword (:alpha_3_code %)))
          (remove :date_withdrawn (flat-xml (convert-xml file))))))

(defn combine-iso3166
  []
  (let [countries (convert-iso3166)
        regions   (convert-iso3166-2)]
    (map #(assoc % :regions (regions (:alpha_2_code %))) countries)))

(defn print-var
  [w name data]
  (.write w ";; Autogenerated\n")
  (clojure.pprint/pprint (seq [`def (symbol name) data]) w)
  (.write w "\n"))



(defn print->ns [name f]
  (with-open [w (clojure.java.io/writer (str "src/clobal/data/" name ".clj"))]
    (.write w (str "(ns clobal.data." name ")\n\n"))
    (f w name)
    
    
    ))

(defn country-ns
  []
  (print->ns "countries"
             (fn [w name]
               (let [data (combine-iso3166)]
                 ;; (print-var w name (vec data))
                 (print-var w "countries" (reduce #(assoc % (:alpha_2_code %2) %2) {} data))
                 ;; (print-var w "slug->countries" (group-by :slug data))
                 ))))

