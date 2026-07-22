(ns marketentry.registry-test
  (:require [clojure.test :refer [deftest is testing]]
            [marketentry.registry :as registry]))

(deftest engagement-fee-recompute
  (let [e {:base-fee 500000 :monthly-rate 30000 :monitoring-months 12 :claimed-fee 860000.0}]
    (is (== 860000.0 (registry/compute-engagement-fee e)))
    (is (true? (registry/engagement-fee-matches-claim? e))))
  (let [bad {:base-fee 500000 :monthly-rate 30000 :monitoring-months 12 :claimed-fee 999000.0}]
    (is (false? (registry/engagement-fee-matches-claim? bad)))))

(deftest register-draft-and-submit
  (let [d (registry/register-draft "eng-1" "LBR" 0)
        s (registry/register-submit "eng-1" "LBR" 0)]
    (is (= "LBR-DFT-000000" (get d "draft_number")))
    (is (= "LBR-SUB-000000" (get s "submit_number")))
    (is (nil? (get-in d ["certificate" "proof"])))
    (is (= "draft-unsigned" (get-in s ["certificate" "status"])))))

(deftest register-requires-ids
  (is (thrown? Exception (registry/register-draft "" "LBR" 0)))
  (is (thrown? Exception (registry/register-submit "eng-1" "" 0))))

(deftest reserved-sector-is-categorical-bar
  (testing "a Schedule-One activity is reserved for Liberians regardless of capital amount"
    (is (true? (registry/reserved-sector? {:business-activity :gas-station :capital-invested-usd 10000000.0})))
    (is (true? (registry/reserved-sector? {:business-activity :taxi-operation :capital-invested-usd 0.0})))))

(deftest auto-repair-shop-is-conditionally-reserved
  (testing "the Schedule's own text makes auto repair shops reserved ONLY below US$50,000"
    (is (true? (registry/reserved-sector? {:business-activity :auto-repair-shop :capital-invested-usd 40000.0})))
    (is (false? (registry/reserved-sector? {:business-activity :auto-repair-shop :capital-invested-usd 50000.0})))
    (is (false? (registry/reserved-sector? {:business-activity :auto-repair-shop :capital-invested-usd 200000.0})))))

(deftest foreign-ownership-eligible-wholly-foreign-threshold
  (testing "a wholly foreign-owned Schedule-Two engagement needs the HIGHER US$500,000 threshold"
    (is (true? (registry/foreign-ownership-eligible? {:business-activity :cinema :liberian-partner-share-pct 0 :capital-invested-usd 500000.0})))
    (is (true? (registry/foreign-ownership-eligible? {:business-activity :cinema :liberian-partner-share-pct nil :capital-invested-usd 600000.0})))
    (is (false? (registry/foreign-ownership-eligible? {:business-activity :cinema :liberian-partner-share-pct 0 :capital-invested-usd 499999.0})))))

(deftest foreign-ownership-eligible-liberian-partnership-threshold
  (testing "a >=25% Liberian-partnership Schedule-Two engagement clears at the LOWER US$300,000 threshold -- the same capital amount that fails the wholly-foreign bar clears the partnership one"
    (is (true? (registry/foreign-ownership-eligible? {:business-activity :bakery :liberian-partner-share-pct 25 :capital-invested-usd 300000.0})))
    (is (false? (registry/foreign-ownership-eligible? {:business-activity :bakery :liberian-partner-share-pct 25 :capital-invested-usd 299999.0})))
    (is (false? (registry/foreign-ownership-eligible? {:business-activity :bakery :liberian-partner-share-pct 10 :capital-invested-usd 1000000.0}))
        "a partnership share below the Schedule's own 25% floor is not a recognized tier at all -- fails closed")))

(deftest foreign-ownership-eligible-unrestricted-sector-defaults-open
  (testing "a business activity in neither Schedule list is not restricted at all"
    (is (true? (registry/foreign-ownership-eligible? {:business-activity :software-development-services :capital-invested-usd 0.0})))))

(deftest foreign-ownership-ineligible-claim-is-entity-scope-gated
  (testing "a domestic engagement is never flagged, even in a reserved sector"
    (is (false? (registry/foreign-ownership-ineligible-claim? {:foreign-company? false :business-activity :gas-station :capital-invested-usd 0.0}))))
  (testing "a foreign engagement in a reserved sector is flagged regardless of capital"
    (is (true? (registry/foreign-ownership-ineligible-claim? {:foreign-company? true :business-activity :gas-station :capital-invested-usd 10000000.0}))))
  (testing "a foreign engagement in a Schedule-Two sector below its own tier's threshold is flagged"
    (is (true? (registry/foreign-ownership-ineligible-claim? {:foreign-company? true :business-activity :cinema :liberian-partner-share-pct 0 :capital-invested-usd 50000.0}))))
  (testing "a foreign engagement that DOES clear its own tier's threshold is not flagged"
    (is (false? (registry/foreign-ownership-ineligible-claim? {:foreign-company? true :business-activity :cinema :liberian-partner-share-pct 0 :capital-invested-usd 600000.0})))
    (is (false? (registry/foreign-ownership-ineligible-claim? {:foreign-company? true :business-activity :software-development-services :capital-invested-usd 0.0})))))
