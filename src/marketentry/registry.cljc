(ns marketentry.registry
  "Pure-function market-entry filing-draft + filing-submit record
  construction -- an append-only market-entry book-of-record draft.

  Like every sibling actor's registry, there is no single international
  reference-number standard for a public-procurement market-entry
  filing -- every jurisdiction assigns its own format. This namespace
  does NOT invent one; it builds a jurisdiction-scoped sequence number
  and validates the record's required fields, the same honest,
  non-fabricating discipline `marketentry.facts` uses.

  `engagement-fee-matches-claim?` is an HONEST reapplication of the
  SAME ground-truth-recompute DISCIPLINE sibling actors use (verify a
  claimed monetary total against the entity's own recorded quantity x
  unit fields), reapplied to a market-entry engagement fee line.

  `foreign-ownership-eligible?` / `foreign-ownership-ineligible-claim?`
  are the SAME discipline applied to a genuinely Liberia-specific
  mechanism: the Investment Act of 2010's own SCHEDULE ('Limited
  Restrictions to Foreign Ownership of Enterprises', own text, fetched
  directly, see `marketentry.facts`) -- a TWO-PART gate:

    1. Sixteen business activities the Schedule's own text reserves
       'exclusively for Liberians' -- a CATEGORICAL bar no capital
       amount cures, EXCEPT the Schedule's own auto-repair-shop item,
       which is itself conditional: 'Auto repair shops with investments
       of less than $US50,000' are reserved; at or above that amount
       they are not.
    2. A second list of twelve activities where the Schedule's own text
       states foreign investment IS permitted, but only if the
       engagement's own declared capital clears a threshold that is
       ITSELF CONDITIONAL on the engagement's own declared ownership
       structure -- wholly foreign-owned needs >= US$500,000; foreign
       investment in partnership with Liberians holding an aggregate
       >= 25% needs only >= US$300,000.

  This is a GENUINELY DIFFERENT check SHAPE than every prior iso3166
  sibling this repo mirrors: Bulgaria's ЗОП Art. 54(5) de-minimis is a
  PERCENTAGE-OF-TURNOVER eligibility formula, Albania's Neni 76(2)(c)
  carve-out is a SINGLE FLAT-CONSTANT threshold (the same number for
  every entity), Azerbaijan's/Armenia's flagship checks are BOOLEAN
  registry-membership reads, Antigua and Barbuda's vendor-class check
  is a THREE-TIER eligibility-threshold classification, Benin's MPME
  mechanism is a BID-EVALUATION PRICE ADJUSTMENT (not an eligibility
  gate at all), Bhutan's FDI Negative List is a CATEGORICAL
  SECTOR-EXCLUSION allow-list gate (a SINGLE list, entity-scope-gated
  on foreign-equity percentage), Botswana's citizen/resident-preference
  check is an ORDERED-TIER CLASSIFICATION, CAF's Marché réservé
  mechanism is a MULTI-CRITERION INCLUSION-ELIGIBILITY test (an OR of
  THREE workforce-composition percentage thresholds and a legal-form
  set-membership test), Estonia's digital-signing-method check tests
  the VALIDITY OF THE FILING'S OWN EXECUTION INSTRUMENT (a procedural
  axis, not the bidder's business substance at all), and the Gambia's
  SIC mechanism is a FLAT INVESTMENT-AMOUNT THRESHOLD whose OWN VALUE
  is selected by the engagement's declared investor-origin attribute.
  Liberia's Investment Act Schedule is none of these: it is a
  TWO-SCHEDULE COMPOUND GATE combining (a) a categorical sector-
  exclusion list (like Bhutan's, but with its own internal
  capital-conditional carve-out on ONE item) with (b) a SEPARATE,
  independent sector list whose admission threshold's OWN VALUE
  is conditional not on a simple declared domestic/foreign origin
  (Gambia's shape) but on a CONTINUOUS aggregate Liberian-ownership
  PERCENTAGE bucketed into two named tiers (wholly foreign vs. >=25%
  Liberian partnership) -- the first in this family where a single
  mechanism combines an absolute categorical bar with a SEPARATE,
  independently-triggered ownership-percentage-scaled capital
  threshold, rather than applying one test shape uniformly across all
  covered sectors.

  This namespace is pure data + pure functions -- no I/O, no network
  call to any real procurement or investment portal. It builds the
  RECORD an operator would keep, not the act of submitting a portal
  registration itself (that is `marketentry.operation`'s `:filing/
  submit`, always human-gated -- see README Actuation)."
  (:require [clojure.string :as str]))

(defn- unsigned-certificate
  "Every certificate this actor produces is UNSIGNED -- signature is
  the market-entry operator's act, not this actor's."
  [kind subject record-id]
  {"@context" ["https://www.w3.org/ns/credentials/v2"]
   "type" ["VerifiableCredential" kind]
   "credentialSubject" {"id" subject "record" record-id}
   "proof" nil
   "issued_by_registry" false
   "status" "draft-unsigned"})

(defn- zero-pad [n w]
  (let [s (str n)]
    (str (apply str (repeat (max 0 (- w (count s))) "0")) s)))

(defn compute-engagement-fee
  "The ground-truth engagement fee for `engagement`'s own `:base-fee`
  and `:monitoring-months` x `:monthly-rate` -- a single flat
  base + months x rate calculation, not a full pricing engine."
  [{:keys [base-fee monthly-rate monitoring-months]}]
  (+ (double base-fee)
     (* (double monthly-rate) (double monitoring-months))))

(defn engagement-fee-matches-claim?
  "Does `engagement`'s own `:claimed-fee` equal the independently
  recomputed `compute-engagement-fee`?"
  [{:keys [claimed-fee] :as engagement}]
  (== (double claimed-fee) (compute-engagement-fee engagement)))

(def reserved-for-liberians
  "Investment Act of 2010, Schedule (own text, fetched directly
  2026-07-23): fifteen business activities reserved exclusively for
  Liberians with NO capital-amount exception. The Schedule's sixteenth
  reserved item, auto repair shops, is handled separately by
  `auto-repair-shop-reserved?` because the Schedule's own text makes
  IT conditional on a stated capital amount ('...with investments of
  less than $US50,000')."
  #{:sand-supply :block-making :peddling :travel-agency
    :retail-rice-and-cement :ice-making :tire-repair-shop
    :shoe-repair-shop :retail-timber-and-planks :gas-station
    :video-club :taxi-operation :used-clothing-import-or-sale
    :distribution-of-locally-manufactured-products
    :used-car-import-or-sale})

(def auto-repair-shop-reserved-below-usd
  "Investment Act of 2010, Schedule (own text): 'Auto repair shops with
  investments of less than $US50,000' are reserved for Liberians; at or
  above that amount the Schedule does not reserve them."
  50000)

(defn auto-repair-shop-reserved?
  "Is an `:auto-repair-shop` engagement's own declared capital below the
  Schedule's own stated $US50,000 threshold (and therefore reserved)?"
  [{:keys [business-activity capital-invested-usd]}]
  (boolean
   (and (= business-activity :auto-repair-shop)
        (some? capital-invested-usd)
        (< (double capital-invested-usd) (double auto-repair-shop-reserved-below-usd)))))

(defn reserved-sector?
  "Is `engagement`'s own declared `:business-activity` categorically
  reserved for Liberians under the Schedule (with no capital amount
  able to cure it), independently recomputed from the engagement's own
  declared fields?"
  [{:keys [business-activity] :as engagement}]
  (boolean
   (or (contains? reserved-for-liberians business-activity)
       (auto-repair-shop-reserved? engagement))))

(def schedule-two-sectors
  "Investment Act of 2010, Schedule (own text, fetched directly
  2026-07-23): twelve business activities where foreign investment IS
  permitted, but only above a capital threshold conditional on the
  engagement's own declared ownership structure -- see
  `schedule-two-capital-threshold-usd`."
  #{:stone-and-granite-production :ice-cream-manufacturing
    :commercial-printing :advertising-agency :cinema
    :poultry-production :water-purification-or-bottling-plant
    :entertainment-center-not-hotel-connected
    :animal-and-poultry-feed-sale :heavy-duty-truck-operation
    :bakery :pharmaceutical-sale})

(def liberian-partnership-min-share-pct
  "Investment Act of 2010, Schedule (own text): the minimum aggregate
  Liberian shareholding percentage a partnership needs to qualify for
  the LOWER of the two Schedule Two capital thresholds."
  25)

(defn- ownership-tier
  "Independently recompute which Schedule Two ownership tier
  `engagement`'s own declared `:liberian-partner-share-pct` falls into,
  or nil if it matches neither of the Schedule's own two named tiers
  (wholly foreign-owned, or a Liberian-partnership share meeting the
  Schedule's own 25% floor) -- fails closed on unrecognized input, the
  same discipline this family's GMB catalog uses for an unrecognized
  `:investor-origin`."
  [{:keys [liberian-partner-share-pct]}]
  (cond
    (or (nil? liberian-partner-share-pct) (zero? (double liberian-partner-share-pct)))
    :wholly-foreign-owned

    (>= (double liberian-partner-share-pct) (double liberian-partnership-min-share-pct))
    :liberian-partnership-25pct-plus

    :else nil))

(def schedule-two-capital-threshold-usd
  "Investment Act of 2010, Schedule (own text, fetched directly
  2026-07-23): 'where such of the listed enterprises is owned
  exclusively by non-Liberians, the total Capital invested shall not be
  less than US$500,000; and, where such of the listed enterprises is
  owned by non-Liberians in partnership with Liberians and the
  aggregate shareholding of the Liberians is at least 25%, the total
  Capital invested shall not be less than US$300,000.'"
  {:wholly-foreign-owned 500000
   :liberian-partnership-25pct-plus 300000})

(defn schedule-two-capital-threshold-met?
  "For a `schedule-two-sectors` engagement, INDEPENDENTLY recompute
  whether the engagement's own declared `:capital-invested-usd` clears
  the Schedule's own threshold for its own declared ownership tier. A
  business activity NOT in `schedule-two-sectors`, or an ownership tier
  the Schedule's own text does not name, simply fails (does not throw)."
  [{:keys [business-activity capital-invested-usd] :as engagement}]
  (boolean
   (when (contains? schedule-two-sectors business-activity)
     (when-let [threshold (get schedule-two-capital-threshold-usd (ownership-tier engagement))]
       (and (some? capital-invested-usd)
            (>= (double capital-invested-usd) (double threshold)))))))

(defn foreign-ownership-eligible?
  "The ground-truth Investment Act of 2010 Schedule eligibility for a
  foreign-company `engagement`'s own declared `:business-activity`,
  `:liberian-partner-share-pct` and `:capital-invested-usd`. A business
  activity in neither Schedule list is simply not restricted by the
  Schedule at all (eligible by default -- the Schedule's own text does
  not cover it)."
  [{:keys [business-activity] :as engagement}]
  (boolean
   (cond
     (reserved-sector? engagement) false
     (contains? schedule-two-sectors business-activity) (schedule-two-capital-threshold-met? engagement)
     :else true)))

(defn foreign-ownership-ineligible-claim?
  "Does `engagement` declare `:foreign-company? true` for a
  `:business-activity` the Investment Act of 2010's own Schedule
  restricts, while the INDEPENDENTLY recomputed
  `foreign-ownership-eligible?` is false? A wholly-domestic engagement
  (`:foreign-company?` false/nil) is never flagged by this check --
  entity-scope-gated, the same discipline this family's BTN catalog
  uses for its own foreign-equity-gated FDI Negative List check."
  [{:keys [foreign-company?] :as engagement}]
  (boolean (and foreign-company? (not (foreign-ownership-eligible? engagement)))))

(defn register-draft
  "Validate + construct the FILING-DRAFT registration DRAFT -- the
  market-entry operator's own act of preparing a portal registration
  package. Pure function -- does not touch any real procurement
  portal."
  [engagement-id jurisdiction sequence]
  (when-not (and engagement-id (not= engagement-id ""))
    (throw (ex-info "draft: engagement_id required" {})))
  (when-not (and jurisdiction (not= jurisdiction ""))
    (throw (ex-info "draft: jurisdiction required" {})))
  (when (< sequence 0)
    (throw (ex-info "draft: sequence must be >= 0" {})))
  (let [draft-number (str (str/upper-case jurisdiction) "-DFT-" (zero-pad sequence 6))
        record {"record_id" draft-number
                "kind" "filing-draft"
                "engagement_id" engagement-id
                "jurisdiction" jurisdiction
                "immutable" true}]
    {"record" record "draft_number" draft-number
     "certificate" (unsigned-certificate "FilingDraft" draft-number draft-number)}))

(defn register-submit
  "Validate + construct the FILING-SUBMIT registration DRAFT -- the
  market-entry operator's own act of actually submitting a portal
  registration (always human-gated upstream)."
  [engagement-id jurisdiction sequence]
  (when-not (and engagement-id (not= engagement-id ""))
    (throw (ex-info "submit: engagement_id required" {})))
  (when-not (and jurisdiction (not= jurisdiction ""))
    (throw (ex-info "submit: jurisdiction required" {})))
  (when (< sequence 0)
    (throw (ex-info "submit: sequence must be >= 0" {})))
  (let [submit-number (str (str/upper-case jurisdiction) "-SUB-" (zero-pad sequence 6))
        record {"record_id" submit-number
                "kind" "filing-submit"
                "engagement_id" engagement-id
                "jurisdiction" jurisdiction
                "immutable" true}]
    {"record" record "submit_number" submit-number
     "certificate" (unsigned-certificate "FilingSubmit" submit-number submit-number)}))

(defn append [history result]
  (conj (vec history) (get result "record")))
