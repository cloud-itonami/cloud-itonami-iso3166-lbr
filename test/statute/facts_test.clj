(ns statute.facts-test
  (:require [clojure.string :as str]
            [clojure.test :refer [deftest is]]
            [statute.facts :as facts]))

(deftest lbr-has-spec-basis
  (let [sb (facts/spec-basis "LBR")]
    (is (= 4 (count sb)))
    (is (every? #(str/starts-with? (:statute/url %) "https://") sb))
    (is (every? :statute/law-number sb))))

(deftest unknown-jurisdiction-has-no-spec-basis
  (is (nil? (facts/spec-basis "ATL")))
  (is (nil? (facts/spec-basis "ZZZ"))))

(deftest coverage-is-honest
  (let [c (facts/coverage ["LBR" "JPN" "ATL"])]
    (is (= 3 (:requested c)))
    (is (= 1 (:covered c)))
    (is (= ["ATL" "JPN"] (:missing-jurisdictions c)))))

(deftest by-topic-filters
  (is (= ["lbr.decent-work-act-2015"]
         (mapv :statute/id (facts/by-topic "LBR" :labor))))
  (is (= ["lbr.consolidated-revenue-code-2020"]
         (mapv :statute/id (facts/by-topic "LBR" :tax))))
  (is (= ["lbr.associations-law-title5"]
         (mapv :statute/id (facts/by-topic "LBR" :corporate-governance))))
  (is (= ["lbr.investment-act-2010"]
         (mapv :statute/id (facts/by-topic "LBR" :investment))))
  (is (empty? (facts/by-topic "ATL" :labor))))
