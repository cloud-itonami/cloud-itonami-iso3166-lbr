(ns culture.facts
  "Country-level regional-culture catalog for Liberia (LBR) -- national
  dishes, protected products, beverages, crafts, festivals and heritage
  sites, per ADR-2607171400 addendum 2 (cloud-itonami-municipality-
  culture-catalog Wave 1, in com-junkawasaki/root). Sibling namespace to
  `marketentry.facts` / `statute.facts` (ADR-2607141700); city-level
  counterparts live in the cloud-itonami-municipality-* repos.

  Catalog is keyed by UPPERCASE ISO3 (mirrors `statute.facts`); entries
  carry no :culture/municipality (that attribute is city-level only).

  Every entry cites a source URL that was actually fetched and read on
  :culture/retrieved-at -- never fabricated. Summaries state only what the
  cited source confirms. An item not in this table has NO spec-basis, full
  stop; extend `catalog`, do not invent an id/url.")

(def catalog
  "iso3 -> vector of culture entries."
  {"LBR"
   [{:culture/id "lbr.dish.dumboy"
     :culture/name "Dumboy"
     :culture/country "LBR"
     :culture/kind :dish
     :culture/summary "Pounded starch preparation made from boiled cassava, one of the staple dishes of Liberian cuisine alongside fufu."
     :culture/url "https://en.wikipedia.org/wiki/Liberian_cuisine"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "lbr.dish.cassava-leaf"
     :culture/name "Cassava leaf"
     :culture/name-local "gbassajama"
     :culture/country "LBR"
     :culture/kind :dish
     :culture/summary "Liberian dish made from ground cassava leaves, braised and tenderized in a broth and mixed with red palm oil stock."
     :culture/url "https://en.wikipedia.org/wiki/Liberian_cuisine"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "lbr.dish.palm-butter"
     :culture/name "Palm butter"
     :culture/country "LBR"
     :culture/kind :dish
     :culture/summary "One of the popular Liberian stews, locally referred to as 'soups', eaten with staples such as rice or fufu."
     :culture/url "https://en.wikipedia.org/wiki/Liberian_cuisine"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "lbr.dish.pepper-soup"
     :culture/name "Pepper soup"
     :culture/country "LBR"
     :culture/kind :dish
     :culture/summary "Spicy stew popular in Liberia, eaten with fufu or rice."
     :culture/url "https://en.wikipedia.org/wiki/Liberian_cuisine"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "lbr.beverage.palm-wine"
     :culture/name "Palm wine"
     :culture/country "LBR"
     :culture/kind :beverage
     :culture/summary "Traditional palm wine made from fermenting palm-tree sap is popular in Liberia, drunk as is, used as a yeast substitute in bread, or used as vinegar."
     :culture/url "https://en.wikipedia.org/wiki/Liberian_cuisine"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "lbr.product.liberian-coffee"
     :culture/name "Liberian coffee (Coffea liberica)"
     :culture/country "LBR"
     :culture/kind :product
     :culture/summary "Coffee species commonly known as Liberian coffee, native to western and central Africa from Liberia to Uganda and Angola; its scientific name references Liberia."
     :culture/url "https://en.wikipedia.org/wiki/Coffea_liberica"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "lbr.craft.sande-masks"
     :culture/name "Sande society masks"
     :culture/country "LBR"
     :culture/kind :craft
     :culture/summary "Carved wooden helmet masks (sowo/bundu) of the Sande women's initiation society of Liberia, Sierra Leone, Guinea and Ivory Coast -- the region's most numerous and important wood masks, worn by women."
     :culture/url "https://en.wikipedia.org/wiki/Sande_society"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "lbr.festival.independence-day"
     :culture/name "Independence Day (26 July)"
     :culture/country "LBR"
     :culture/kind :festival
     :culture/summary "The anniversary of the adoption of Liberia's 1847 Declaration of Independence and accompanying Constitution is celebrated as Independence Day in Liberia."
     :culture/url "https://en.wikipedia.org/wiki/Liberian_Declaration_of_Independence"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "lbr.heritage.providence-island"
     :culture/name "Providence Island"
     :culture/country "LBR"
     :culture/kind :heritage
     :culture/summary "Island at Monrovia, originally Dozoa Island, that was the site of the first successful settlement of American freedmen by the American Colonization Society in Liberia."
     :culture/url "https://en.wikipedia.org/wiki/Providence_Island,_Liberia"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}]})

(defn spec-basis [iso3] (get catalog iso3))

(defn coverage
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-lbr culture catalog "
                 "(ADR-2607171400 addendum 2, Wave 1): " (count (get catalog "LBR"))
                 " LBR entries, each with a fetched-and-read citation. "
                 "Extend `culture.facts/catalog`, never fabricate an id/url.")})))

(defn by-kind [iso3 kind]
  (filterv #(= (:culture/kind %) kind) (spec-basis iso3)))
