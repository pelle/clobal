(ns clobal.core
  (:require clobal.data.countries
            clobal.data.regions))


(def countries clobal.data.countries/countries)
(def regions clobal.data.regions/regions)

(defn ->iso
  "Converts a string into an uppercase keyword as needed by the data"
  [code]
  (keyword (.toUpperCase (str code))))

(defn country
  "Returns a country map for a given ISO code

   (country :TO)

   > {:regions
   [{:slug \"eua\", :type :division, :code :TO-01, :name \"'Eua\"}
    {:slug \"haapai\", :type :division, :code :TO-02, :name \"Ha'apai\"}
    {:slug \"niuas\", :type :division, :code :TO-03, :name \"Niuas\"}
    {:slug \"tongatapu\",
     :type :division,
     :code :TO-04,
     :name \"Tongatapu\"}
    {:slug \"vavau\", :type :division, :code :TO-05, :name \"Vava'u\"}],
   :slug \"tonga\",
   :alpha_2_code :TO,
   :alpha_3_code :TON,
   :numeric_code 776,
   :currency :TOP
   :name \"Tonga\",
   :official_name \"Kingdom of Tonga\"}"
  [code]
  (let [code (->iso code) ]
    (assoc (code countries)
      :regions (regions code))))
