# clobal

A clojure library with world information based on the Debian iso-codes library.

This is absolute early work and is not meant for public consumption just yet.

The goal is to import and convert all the locales in iso-codes and make them available to [Tower](https://github.com/ptaoussanis/tower) for localization purposes.

Right now it's just basic information that I personally need to sketch out a new project.

## Usage

Add the following to your project.clj

```clojure
[clobal "0.2.0"]
```

Use library:

```clojure
(use 'clobal.core)
```

```clojure
(country "to")
{:regions
   [{:slug "eua", :type :division, :code :TO-01, :name "'Eua"}
    {:slug "haapai", :type :division, :code :TO-02, :name "Ha'apai"}
    {:slug "niuas", :type :division, :code :TO-03, :name "Niuas"}
    {:slug "tongatapu",
     :type :division,
     :code :TO-04,
     :name "Tongatapu"}
    {:slug "vavau", :type :division, :code :TO-05, :name "Vava'u"}],
   :slug "tonga",
   :alpha_2_code :TO,
   :alpha_3_code :TON,
   :currency :TOP,
   :numeric_code 776,
   :name "Tonga",
   :official_name "Kingdom of Tonga"}

```

## License

Copyright © 2014 Pelle Braendgaard

Country data is converted from [Debian pkg iso-codes](http://pkg-isocodes.alioth.debian.org/), however their data is based on [ISO 3166](http://www.iso.org/iso/country_codes).

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
