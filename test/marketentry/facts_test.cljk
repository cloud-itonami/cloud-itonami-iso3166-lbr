(ns marketentry.facts-test
  (:require [clojure.test :refer [deftest is testing]]
            [marketentry.facts :as facts]))

(deftest lbr-has-spec-basis
  (let [sb (facts/spec-basis "LBR")]
    (is (some? sb))
    (is (string? (:provenance sb)))
    (is (seq (:required-evidence sb)))
    (is (some? (facts/corporate-number-spec-basis "LBR")))
    (is (some? (facts/investment-act-spec-basis "LBR")))))

(deftest lbr-rep-spec-basis-is-honestly-absent
  (testing "no Liberia-specific representative/director exclusion-extension provision could be independently confirmed this iteration -- deliberately not claimed"
    (is (nil? (facts/rep-spec-basis "LBR")))))

(deftest unknown-jurisdiction-has-no-spec-basis
  (is (nil? (facts/spec-basis "ATL")))
  (is (nil? (facts/spec-basis "ZZZ"))))

(deftest required-evidence-satisfied
  (let [sb (facts/spec-basis "LBR")
        all (:required-evidence sb)]
    (is (true? (facts/required-evidence-satisfied? "LBR" all)))
    (is (not (facts/required-evidence-satisfied? "LBR" (take 1 all))))
    (is (nil? (facts/required-evidence-satisfied? "ATL" all)))))

(deftest coverage-is-honest
  (let [c (facts/coverage ["LBR" "USA" "ATL"])]
    (is (= 3 (:requested c)))
    (is (= 2 (:covered c)))
    (is (= ["ATL"] (:missing-jurisdictions c)))))

(deftest investment-act-spec-basis-criteria
  (let [ia (facts/investment-act-spec-basis "LBR")]
    (is (= 500000 (get-in ia [:investment-act-criteria :min-capital-usd-wholly-foreign])))
    (is (= 300000 (get-in ia [:investment-act-criteria :min-capital-usd-liberian-partnership-25pct-plus])))
    (is (contains? (get-in ia [:investment-act-criteria :reserved-for-liberians]) :gas-station))
    (is (contains? (get-in ia [:investment-act-criteria :schedule-two-sectors]) :cinema))))
