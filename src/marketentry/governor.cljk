(ns marketentry.governor
  "Market-Entry Compliance Governor -- the independent compliance layer
  that earns the MarketEntry-LLM the right to commit. The LLM has no
  notion of Liberian procurement or investment law, whether a claimed
  engagement fee actually equals base + months x rate, whether the
  engagement's own declared business activity and ownership structure
  actually qualify it for foreign investment under the Investment Act
  of 2010's own Schedule when it declares itself `:foreign-company?
  true`, whether a Liberia Revenue Authority (LRA) Taxpayer
  Identification Number (TIN) record has been verified for a filing
  that requires it, or when a draft stops being a draft and becomes a
  real-world ppcc.gov.lr/lbr.gov.lr submission, so this MUST be a
  separate system able to *reject* a proposal and fall back to HOLD.

  `:itonami.blueprint/governor` is `:market-entry-compliance-governor`
  (shared family keyword on blueprints).

  This blueprint's own text (docs/business-model.md Trust Controls:
  'any actual portal registration or filing submission requires
  Market-Entry Compliance Governor clearance and always escalates to
  human sign-off'; 'a false or fabricated regulatory-requirement claim
  is a HARD hold') names exactly the checks below.

  Six checks, in priority order, ALL HARD violations: a human
  approver CANNOT override them. The confidence/actuation gate is
  SOFT: it asks a human to look (low confidence / actuation), and the
  human may approve -- but see `marketentry.phase`: for `:stake
  :actuation/draft-filing`/`:actuation/submit-filing` NO phase ever
  allows auto-commit either. Two independent layers agree that
  actuation is always a human call.

    1. Spec-basis                  -- did the jurisdiction proposal cite
                                       an OFFICIAL source
                                       (`marketentry.facts`), or invent
                                       one?
    2. Evidence incomplete         -- for `:filing/draft`/
                                       `:filing/submit`, has the
                                       jurisdiction actually been
                                       assessed with a full evidence
                                       checklist on file?
    3. Foreign-ownership sector
       ineligible                   -- for `:filing/submit`, when the
                                       engagement declares
                                       `:foreign-company? true`,
                                       INDEPENDENTLY recompute whether
                                       the engagement's own declared
                                       business activity, ownership
                                       structure and capital invested
                                       actually satisfy the Investment
                                       Act of 2010's own Schedule
                                       ('Limited Restrictions to
                                       Foreign Ownership of
                                       Enterprises'), and HARD-hold if
                                       not. FLAGSHIP genuinely new
                                       check for the iso3166 family
                                       (grep-verified absent as a
                                       governor check function name
                                       fleet-wide at build time) -- a
                                       TWO-SCHEDULE COMPOUND GATE
                                       combining a categorical
                                       sector-exclusion list with a
                                       SEPARATE, independently-
                                       triggered ownership-percentage-
                                       scaled capital threshold, a
                                       check SHAPE genuinely different
                                       from every prior sibling's (see
                                       `marketentry.registry`
                                       docstring for the full
                                       comparison).
    4. Engagement fee mismatch     -- for `:filing/submit`,
                                       INDEPENDENTLY recompute whether
                                       the engagement's own `:claimed-
                                       fee` equals `base-fee +
                                       monthly-rate x monitoring-
                                       months` -- honest reapplication
                                       of the ground-truth-recompute
                                       discipline sibling actors use.
    5. TIN record unverified        -- for `:filing/submit`, when the
                                       engagement declares
                                       `:requires-tin-record? true`,
                                       INDEPENDENTLY check
                                       `:tin-record-verified?`.
                                       CONDITIONAL on the engagement's
                                       own ground truth. Grounded in
                                       the Liberia Revenue Authority
                                       (LRA)'s Taxpayer Identification
                                       Number (TIN) requirement (see
                                       `marketentry.facts`).
    6. Confidence floor / actuation
       gate                          -- LLM confidence below threshold,
                                       OR the op is `:filing/draft`/
                                       `:filing/submit` (REAL acts)
                                       -> escalate.

  Two more guards, double-draft/double-submit prevention, are enforced
  off dedicated `:drafted?`/`:submitted?` facts (never a `:status`
  value)."
  (:require [marketentry.facts :as facts]
            [marketentry.registry :as registry]
            [marketentry.store :as store]))

(def confidence-floor 0.6)

(def high-stakes
  "Stakes grave enough to always require a human, even when clean.
  Drafting a real portal package and submitting a real portal
  registration are the two real-world actuation events this actor
  performs."
  #{:actuation/draft-filing :actuation/submit-filing})

;; ----------------------------- checks -----------------------------

(defn- spec-basis-violations
  "A `:jurisdiction/assess` (or `:filing/draft`/`:filing/submit`)
  proposal with no spec-basis citation is a HARD violation -- never
  invent a jurisdiction's market-entry requirements."
  [{:keys [op]} proposal]
  (when (contains? #{:jurisdiction/assess :filing/draft :filing/submit} op)
    (let [value (:value proposal)]
      (when (or (empty? (:cites proposal))
                (and (contains? value :spec-basis) (nil? (:spec-basis value))))
        [{:rule :no-spec-basis
          :detail "公式spec-basisの引用が無い提案は法域要件として扱えない"}]))))

(defn- evidence-incomplete-violations
  "For `:filing/draft`/`:filing/submit`, the jurisdiction's required
  registration evidence must actually be satisfied."
  [{:keys [op subject]} st]
  (when (contains? #{:filing/draft :filing/submit} op)
    (let [e (store/engagement st subject)
          assessment (store/assessment-of st subject)]
      (when-not (and assessment
                     (facts/required-evidence-satisfied?
                      (:jurisdiction e) (:checklist assessment)))
        [{:rule :evidence-incomplete
          :detail "法域の必要書類(事業登録証明書/TINカード/PPCCベンダー登録/Investment Act適格確認等)が充足していない状態での提案"}]))))

(defn- foreign-ownership-ineligible-violations
  "For `:filing/submit`, INDEPENDENTLY recompute whether the
  engagement's own declared business activity, ownership structure and
  capital invested satisfy the Investment Act of 2010's own Schedule --
  the flagship check this vertical adds. HARD-hold when the engagement
  declares `:foreign-company? true` for a Schedule-restricted business
  activity it is not independently eligible for."
  [{:keys [op subject]} st]
  (when (= op :filing/submit)
    (let [e (store/engagement st subject)]
      (when (registry/foreign-ownership-ineligible-claim? e)
        [{:rule :foreign-ownership-ineligible
          :detail (str subject " は外国企業として業種 " (:business-activity e)
                      " への参入を宣言しているが、独立再計算(Investment Act of 2010 Schedule: "
                      "留保業種リスト、または所有構造別の最低投資額基準 -- 完全外資"
                      "US$500,000/リベリア人パートナー出資比率25%以上でUS$300,000)"
                      "による適格性を満たさない")}]))))

(defn- engagement-fee-mismatch-violations
  "For `:filing/submit`, INDEPENDENTLY recompute whether the
  engagement's own claimed fee equals base + months x rate."
  [{:keys [op subject]} st]
  (when (= op :filing/submit)
    (let [e (store/engagement st subject)]
      (when-not (registry/engagement-fee-matches-claim? e)
        [{:rule :engagement-fee-mismatch
          :detail (str subject " の申告手数料(" (:claimed-fee e)
                      ")が独立再計算値(" (registry/compute-engagement-fee e) ")と一致しない")}]))))

(defn- tin-record-unverified-violations
  "For `:filing/submit`, when the engagement declares
  `:requires-tin-record? true`, INDEPENDENTLY check
  `:tin-record-verified?` -- CONDITIONAL on the engagement's own
  ground truth. Grounded in the Liberia Revenue Authority (LRA)'s
  Taxpayer Identification Number (TIN)."
  [{:keys [op subject]} st]
  (when (= op :filing/submit)
    (let [e (store/engagement st subject)]
      (when (and (true? (:requires-tin-record? e))
                 (not (true? (:tin-record-verified? e))))
        [{:rule :tin-record-unverified
          :detail (str subject " はLRA(Liberia Revenue Authority)発行のTIN記録確認を要するが未確認 -- 提出提案は進められない")}]))))

(defn- already-drafted-violations
  "For `:filing/draft`, refuses to draft the SAME engagement twice."
  [{:keys [op subject]} st]
  (when (= op :filing/draft)
    (when (store/engagement-already-drafted? st subject)
      [{:rule :already-drafted
        :detail (str subject " は既にドラフト済み")}])))

(defn- already-submitted-violations
  "For `:filing/submit`, refuses to submit the SAME engagement twice."
  [{:keys [op subject]} st]
  (when (= op :filing/submit)
    (when (store/engagement-already-submitted? st subject)
      [{:rule :already-submitted
        :detail (str subject " は既に提出済み")}])))

(defn check
  "Censors a MarketEntry-LLM proposal against the governor rules.
  Returns {:ok? bool :violations [..] :confidence c :escalate? bool
  :high-stakes? bool :hard? bool}."
  [request _context proposal st]
  (let [hard (into []
                   (concat (spec-basis-violations request proposal)
                           (evidence-incomplete-violations request st)
                           (foreign-ownership-ineligible-violations request st)
                           (engagement-fee-mismatch-violations request st)
                           (tin-record-unverified-violations request st)
                           (already-drafted-violations request st)
                           (already-submitted-violations request st)))
        conf (:confidence proposal 0.0)
        low? (< conf confidence-floor)
        stakes? (boolean (high-stakes (:stake proposal)))
        hard? (boolean (seq hard))]
    {:ok?          (and (not hard?) (not low?) (not stakes?))
     :violations   hard
     :confidence   conf
     :hard?        hard?
     :escalate?    (and (not hard?) (or low? stakes?))
     :high-stakes? stakes?}))

(defn hold-fact
  "The audit fact written when a proposal is rejected (HOLD)."
  [request context verdict]
  {:t          :governor-hold
   :op         (:op request)
   :actor      (:actor-id context)
   :subject    (:subject request)
   :disposition :hold
   :basis      (mapv :rule (:violations verdict))
   :violations (:violations verdict)
   :confidence (:confidence verdict)})
