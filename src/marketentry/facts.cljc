(ns marketentry.facts
  "Per-jurisdiction public-procurement market-entry regulatory catalog
  -- the G2-style spec-basis table the Market-Entry Compliance Governor
  checks every `:jurisdiction/assess` proposal against ('did the advisor
  cite an OFFICIAL public source for this jurisdiction's requirements,
  or did it invent one?').

  Liberia's real market-entry surface (curl/WebFetch-verified
  2026-07-22/23; where a page could not be reached, or turned out to be
  client-side-rendered with no readable content, that is stated
  explicitly rather than silently omitted). Liberia is NOT an OHADA
  member (unlike most of its Francophone West African neighbors) -- it
  is a common-law jurisdiction whose own statutes (Associations Law,
  Investment Act) are cited directly below, not a supranational Uniform
  Act.

  - **Public procurement** is regulated by the Public Procurement and
    Concessions Commission (PPCC, `ppcc.gov.lr`, fetched directly --
    ordinary server-rendered HTML with full content). PPCC's own
    'Legal Documents' publications page (`ppcc.gov.lr/publications/
    document-type/legal-documents`, fetched directly) lists, and this
    iteration downloaded and read the actual PDF text of, THREE
    generations of the governing Act, all hosted on PPCC's own site:
    'An Act Creating the Public Procurement and Concessions Commission'
    (own text: 'APPROVED: SEPTEMBER 8, 2005'), the 'Amendment and
    Restatement of the Public Procurement and Concessions Act, 2005'
    (2010), and -- current as of this iteration -- the 'Amendment and
    Restatement of the Public Procurement and Concessions Act, 2010'
    (own text: 'APPROVED: JANUARY 12, 2026', printed January 23, 2026;
    filename on PPCC's own site literally names it '...of the PPC of
    Act 2026'). This 2026 Act's own Section 32(1) states the bidder
    qualification evidence this catalog's `:required-evidence` models
    directly: '(a) Professional and technical qualifications; (b)
    Registration with the Commission's Vendor Register...(j)
    Verification by the internal revenue authority of payment of taxes
    and social security contributions when due.' The Act's own Section
    46(2) states bidding-VALUE 'Thresholds' are 'establish[ed] by
    Regulations promulgated by the Commission' -- this iteration did
    not independently fetch those Regulations, so no numeric bidding
    threshold is modeled here (an honest, delegated-to-regulation gap,
    the same discipline this family's CAF ministerial-arrêté-delegated
    Marché réservé value threshold and Bhutan's unread Debarment Rules
    duration clause already established).
  - **Business/company registration** is handled by the Liberia
    Business Registry (LBR -- an autonomous agency, NOT to be confused
    with this repo's own ISO3 code). This iteration independently
    confirmed LBR's role from the National Investment Commission's own
    'Starting a Business' page (`nic.gov.lr/pages/starting-a-business/`,
    fetched directly): 'The Liberia Business Registry (LBR) serves as a
    one-stop shop for business registration in Liberia. Any entity
    intending to commence business activities in the country must first
    be registered with the LBR... Detailed information regarding fees
    and procedures is available on the official Liberia Business
    Registry website at www.lbr.gov.lr.' HOWEVER: `www.lbr.gov.lr`
    (both http and https) timed out on connection for this iteration
    (`curl -v` showed TCP connect timeout, not a DNS or TLS failure) --
    an honestly-flagged ACCESS gap, not a claim the site does not exist.
    The related public portal `liberiabusinessregistry.com` IS
    reachable but returned only a client-side-rendered React SPA shell
    (title 'LBR - Business Registry System', body
    '<noscript>You need to enable JavaScript to run this app.</noscript>')
    to plain `curl` -- the SAME kind of rendering gap this family's GMB
    catalog documented for GPPA's own site (`gppa.gm`). NIC's own page
    (still fetched directly, independent of either LBR domain) names
    the concrete required documents this catalog's `:required-evidence`
    models: 'Registration Form (RF-001), Articles of Incorporation, and
    copies of identification documents attached to the Empowered Person
    Form (Form A) or Registered Agent Form (Form B)... the
    Incorporator(s) Form (E), Shares and Shareholder(s) Form (Form F),
    and the Tax Authority Information Form (Form Q)', a company-name
    reservation 'valid for a period of 120 days and costs approximately
    USD $15.00', and post-registration coordination with 'the Ministry
    of Finance and Development Planning to obtain the entrepreneur's Tax
    Identification Number (TIN) and clearance through the Business
    Permit System (BPS)'. NIC's own page separately links a 'Download
    Business Registration Document' PDF
    (`nic.gov.lr/documents/liberia-business-registration-guide.pdf`) --
    this iteration fetched that URL directly and it returned HTTP 404, a
    second, independently-confirmed honest ACCESS gap on NIC's own site
    (the document NIC's own page references is not actually present at
    the URL NIC's own page gives for it).
  - **LBR is NOT Liberia's only corporate registry** -- this iteration
    deliberately checked for conflation given Liberia's history as a
    flag-of-convenience shipping registry, the same non-conflating
    discipline this family's GMB (Companies Department vs. Registrar
    General's Department) and CAF/Benin (two-body ARMP splits) catalogs
    already established. The Liberian International Ship & Corporate
    Registry (LISCR, `liscr.com`, fetched directly, own text: 'The
    Liberian Registry ... Largest Flag Worldwide') separately operates
    a non-resident/offshore 'Corporate Registry' division (own nav:
    'Corporation | Shelf Companies | Limited Liability Company (LLC) |
    Partnership & Limited Partnership | Private Foundation | Foreign
    Maritime Entity (FME)') under contract with the Government of
    Liberia. This iteration downloaded LISCR's own consolidated
    'Business Corporation Act' PDF
    (`media.liscr.com/.../bca consolidated- published april 6, 2020.pdf`)
    and confirmed by chapter/section structure (Ch.1 General Provisions,
    Ch.3 Service of Process/Registered Agent, Ch.4 Formation of
    Corporations, Ch.5 Corporate Finance, Ch.6 Directors and Management
    ... matching Title 5's own 'PART I. BUSINESS CORPORATIONS' table of
    contents exactly) that LISCR's 'BCA' is a republished excerpt of the
    SAME Title 5 Associations Law cited below for `statute.facts`, NOT a
    separate statute -- LISCR is a different ADMINISTRATOR (for
    non-resident entities not operating a business within Liberia,
    including 'Foreign Maritime Entities', reflecting the
    flag-of-convenience role) of provisions within the same law the
    domestic LBR administers for entities actually operating in Liberia.
    This catalog models ONLY the domestic LBR market-entry path; an
    engagement seeking solely a LISCR offshore/ship registration is
    OUT OF SCOPE here.
  - **Tax/TIN registration** is administered by the Liberia Revenue
    Authority (LRA, `lra.gov.lr` / `revenue.lra.gov.lr`, fetched
    directly). LRA's own homepage advertises a 'Get a TIN Online' tool
    directly. LRA's own 'Domestic Tax Laws & Regulations' page
    (`revenue.lra.gov.lr/domestic-tax-laws-regulations/`, fetched
    directly) lists, by its own title, the 'Liberia Consolidated Revenue
    Code As Amended - 2020' as the current tax code. LRA's own 'Legal'
    page (`revenue.lra.gov.lr/legal/`, fetched directly) names its own
    legislative framework as 'the Act Creating the LRA, the Liberia
    Revenue Codes, and other relevant financial regulations' but does
    NOT state the Act Creating the LRA's own year/number on that page --
    this iteration found this Act's EXISTENCE and its role only, and
    does not invent a year/number for it (an honest gap, the same
    discipline GMB's catalog used for the Gambia's 'Single Window
    Business Registration Act', title-only, year unconfirmed). The
    2000-vintage base code is independently corroborated by a
    SEPARATE official source: the Investment Act of 2010's own Section
    5 (own text, see below), 'An enterprise shall be entitled to such
    fiscal incentives as are applicable under the Revenue Code of
    Liberia of 2000 or amendments thereto' -- confirming the Revenue
    Code's 2000 origin from a source that was not copied from LRA's own
    page.
  - `investment-act-spec-basis` grounds this vertical's FLAGSHIP check
    (see `marketentry.governor` / `marketentry.registry`) -- a genuinely
    Liberia-specific mechanism this iteration found directly in the
    National Investment Commission's (NIC, `nic.gov.lr`) own 'Legal &
    Regulatory Resources' page (`nic.gov.lr/pages/resources/`, fetched
    directly): 'Investment Act of 2010 -- This fundamental statutory
    instrument provides the complete legal bedrock for establishing
    business operations, capital regulations, and investor protections
    in Liberia.' This iteration downloaded the ACTUAL PDF NIC's own page
    links ('Download Investment Act 2010') and read its primary text
    directly: 'THE INVESTMENT ACT OF 2010 -- REPEALING AND REPLACING THE
    INVESTMENT INCENTIVES ACT OF 1973 ... This Act shall be known as the
    Investment Act of 2010.' Its own SCHEDULE ('Limited Restrictions to
    Foreign Ownership of Enterprises') states, in full and directly (no
    delegated number to guess), a TWO-PART foreign-ownership gate this
    catalog models completely: (1) fifteen named business activities
    'reserved exclusively for Liberians' (sand supply, block making,
    peddling, travel agencies, retail sale of rice and cement, ice
    making, tire repair shops, auto repair shops with investments of
    less than US$50,000, shoe repair shops, retail sale of timber and
    planks, gas stations, video clubs, taxi operation, importation/sale
    of second-hand or used clothing, distribution of locally
    manufactured products, importation/sale of used cars except
    authorized dealerships) -- a categorical bar no capital amount
    cures (except the auto-repair-shop item's own stated $50,000
    carve-out, which this catalog also models exactly as stated); and
    (2) a second list of twelve activities (stone/granite production,
    ice cream manufacturing, commercial printing, advertising agencies,
    cinemas, poultry production, water purification/bottling excluding
    sachet water, entertainment centers not hotel-connected, animal/
    poultry feed sale, heavy duty truck operation, bakeries,
    pharmaceutical sales) where the Act's own text states foreign
    investment IS permitted, but ONLY if the engagement's own declared
    capital clears a threshold that is CONDITIONAL on its own declared
    ownership structure: 'where such of the listed enterprises is owned
    exclusively by non-Liberians, the total Capital invested shall not
    be less than US$500,000; and, where such of the listed enterprises
    is owned by non-Liberians in partnership with Liberians and the
    aggregate shareholding of the Liberians is at least 25%, the total
    Capital invested shall not be less than US$300,000.' This iteration
    independently cross-confirmed the SAME Act from a wholly separate
    official source: the Investment Act's own Section 6.4 names 'the
    Act of September 8, 2005 establishing the Public Procurement and
    Concessions Commission' -- the EXACT date this iteration
    independently found on PPCC's own site (see above), corroborating
    both citations from two unrelated official domains neither copied
    from the other.
  - This iteration also looked for a Gambia-style flat minimum-
    investment incentive certificate (the GIEPA SIC shape). NIC's own
    'Government Support' page (`nic.gov.lr/pages/gov-support/`, fetched
    directly) DOES publish a tiered incentive schedule (Agribusiness
    incentives from US$50,000+; General Tax Incentives for 'approved
    investments between $500,000 -- $9 million'; Concession Agreements
    above US$10 million) and its own 'Sources' list at the foot of that
    page names 'Tax Amendment Act, 2016' as the source -- but this
    iteration did NOT independently fetch the Tax Amendment Act, 2016's
    own primary text (only NIC's own paraphrase of it), so this
    incentive-tier schedule is NOT modeled as a governor check here,
    only mentioned in this docstring as an honestly-flagged
    lower-confidence adjacent mechanism this iteration chose NOT to
    build a flagship check on, unlike the Investment Act of 2010
    Schedule above (which this iteration read as PRIMARY text directly).

  Coverage is reported HONESTLY (see `coverage`): a jurisdiction not in
  this table has NO spec-basis, full stop -- the advisor must not
  fabricate one, and the governor holds if it tries.")

(def catalog
  "iso3 -> requirement map. `:required-evidence` mirrors the generic
  intake/portal-registration/filing evidence set; `:legal-basis` /
  `:owner-authority` / `:provenance` are the G2 citation the governor
  requires before any `:jurisdiction/assess` proposal can commit. LBR
  deliberately carries NO `:rep-owner-authority` -- this iteration did
  not find a Liberia-specific representative/director exclusion-
  extension provision in any primary text it was actually able to
  read (an honest gap, not a claim none exists). `:investment-act-*`
  grounds this vertical's flagship governor check (`foreign-ownership-
  eligible?`/`foreign-ownership-ineligible-claim?` in
  `marketentry.registry`)."
  {"LBR" {:name "Liberia"
          :owner-authority "Public Procurement and Concessions Commission (PPCC) -- established by 'An Act Creating the Public Procurement and Concessions Commission', own text: 'APPROVED: SEPTEMBER 8, 2005' (ppcc.gov.lr, own hosted PDF, fetched directly)"
          :legal-basis "Amendment and Restatement of the Public Procurement and Concessions Act, 2010 (own text: 'APPROVED: JANUARY 12, 2026'; PPCC's own site names the file '...of the PPC of Act 2026'; this Act is itself the third generation of a lineage this iteration confirmed on PPCC's own Legal Documents page -- the original 2005 Act, its 2010 Amendment and Restatement, and this 2026 Amendment and Restatement, all three hosted and fetched directly from ppcc.gov.lr)"
          :national-spec "Business/company registration: Liberia Business Registry (LBR, an autonomous agency -- NOT this repo's own ISO3 code), per the National Investment Commission's own text: 'The Liberia Business Registry (LBR) serves as a one-stop shop for business registration in Liberia. Any entity intending to commence business activities in the country must first be registered with the LBR' (nic.gov.lr/pages/starting-a-business/, own text). Registration under the Associations Law, Title 5 of the Liberian Code of Laws Revised (Approved May 19, 1976; Effective January 3, 1977 -- own primary text, fetched directly, see statute.facts). Tax/TIN registration: Liberia Revenue Authority (LRA), 'Liberia Consolidated Revenue Code As Amended - 2020' per LRA's own Domestic Tax Laws & Regulations page (own text, fetched directly), rooted in the 'Revenue Code of Liberia of 2000' independently named by the Investment Act of 2010's own Section 5"
          :provenance "https://ppcc.gov.lr/publications/document-type/legal-documents ; https://nic.gov.lr/pages/starting-a-business/ ; https://revenue.lra.gov.lr/domestic-tax-laws-regulations/ ; https://revenue.lra.gov.lr/legal/"
          :required-evidence ["Business Registration Certificate (Liberia Business Registry -- Registration Form RF-001, Articles of Incorporation, Empowered Person Form (Form A) or Registered Agent Form (Form B), per nic.gov.lr's own 'Starting a Business' page, fetched directly)"
                              "Incorporator(s) Form (E) and Shares and Shareholder(s) Form (Form F), where the business structure requires them (Liberia Business Registry, per nic.gov.lr's own text)"
                              "Tax Authority Information Form (Form Q) and TIN Card / Tax Identification Number record (coordinated with the Ministry of Finance and Development Planning and the Liberia Revenue Authority, per nic.gov.lr's own text)"
                              "Company-name reservation confirmation (valid 120 days, approximately USD $15.00, per nic.gov.lr's own text)"
                              "PPCC Vendor Register registration (Section 32(1)(b) of the 2026 Act, PPCC's own text: 'Registration with the Commission's Vendor Register' -- a qualification criterion for participating in procurement proceedings at all)"
                              "Verification by the Liberia Revenue Authority of payment of taxes and social security contributions when due (Section 32(1)(j) of the 2026 Act, PPCC's own text)"
                              "Investment Act of 2010 foreign-ownership eligibility confirmation record, when the engagement declares :foreign-company? true"]
          :corporate-number-owner-authority "Liberia Revenue Authority (LRA)"
          :corporate-number-legal-basis "LRA's own 'Domestic Tax Laws & Regulations' page (revenue.lra.gov.lr/domestic-tax-laws-regulations/, fetched directly) names 'Liberia Consolidated Revenue Code As Amended - 2020' as the current tax code; LRA's own 'Legal' page (revenue.lra.gov.lr/legal/, fetched directly) names its legislative framework as 'the Act Creating the LRA, the Liberia Revenue Codes' but does not itself state the Act Creating the LRA's own year/number -- an honest gap, not invented here"
          :corporate-number-provenance "https://revenue.lra.gov.lr/domestic-tax-laws-regulations/ ; https://revenue.lra.gov.lr/legal/"
          :investment-act-owner-authority "National Investment Commission (NIC)"
          :investment-act-legal-basis "The Investment Act of 2010 (own text, fetched directly: 'THE INVESTMENT ACT OF 2010 -- REPEALING AND REPLACING THE INVESTMENT INCENTIVES ACT OF 1973 ... This Act shall be known as the Investment Act of 2010.'), own SCHEDULE 'Limited Restrictions to Foreign Ownership of Enterprises': (1) fifteen activities reserved exclusively for Liberians (a categorical bar, except the auto-repair-shop item's own stated <US$50,000 carve-out); (2) a second list of twelve activities where foreign investment is permitted only if capital invested clears a threshold conditional on ownership structure -- 'where such of the listed enterprises is owned exclusively by non-Liberians, the total Capital invested shall not be less than US$500,000; and, where such of the listed enterprises is owned by non-Liberians in partnership with Liberians and the aggregate shareholding of the Liberians is at least 25%, the total Capital invested shall not be less than US$300,000.' Independently cross-confirmed via the SAME Act's own Section 6.4, which names 'the Act of September 8, 2005 establishing the Public Procurement and Concessions Commission' -- the exact date this iteration separately found on PPCC's own site"
          :investment-act-criteria {:reserved-for-liberians #{:sand-supply :block-making :peddling :travel-agency
                                                                :retail-rice-and-cement :ice-making :tire-repair-shop
                                                                :shoe-repair-shop :retail-timber-and-planks :gas-station
                                                                :video-club :taxi-operation :used-clothing-import-or-sale
                                                                :distribution-of-locally-manufactured-products
                                                                :used-car-import-or-sale}
                                     :auto-repair-shop-reserved-below-usd 50000
                                     :schedule-two-sectors #{:stone-and-granite-production :ice-cream-manufacturing
                                                              :commercial-printing :advertising-agency :cinema
                                                              :poultry-production :water-purification-or-bottling-plant
                                                              :entertainment-center-not-hotel-connected
                                                              :animal-and-poultry-feed-sale :heavy-duty-truck-operation
                                                              :bakery :pharmaceutical-sale}
                                     :min-capital-usd-wholly-foreign 500000
                                     :min-capital-usd-liberian-partnership-25pct-plus 300000
                                     :liberian-partnership-min-share-pct 25}
          :investment-act-provenance "https://nic.gov.lr/pages/resources/ (own 'Legal & Regulatory Resources' page, links directly to the Investment Act of 2010 PDF)"}
   "USA" {:name "United States"
          :owner-authority "U.S. General Services Administration (GSA) / SAM.gov"
          :legal-basis "Federal Acquisition Regulation (FAR); System for Award Management"
          :national-spec "SAM.gov entity registration + NAICS self-certification"
          :provenance "https://sam.gov/"
          :required-evidence ["EIN record"
                              "SAM.gov registration record"
                              "State business registration record"
                              "Authorized-representative record"]}
   "DEU" {:name "Germany"
          :owner-authority "Beschaffungsamt des BMI / e-Vergabe platforms"
          :legal-basis "Gesetz gegen Wettbewerbsbeschränkungen (GWB) / VgV"
          :national-spec "e-Vergabe supplier registration under EU procurement directives"
          :provenance "https://www.evergabe-online.de/"
          :required-evidence ["Handelsregister extract"
                              "e-Vergabe registration record"
                              "USt-IdNr record"
                              "Authorized-representative record"]}})

(defn spec-basis
  "The jurisdiction's requirement map, or nil -- nil means NO spec-basis,
  and the governor must hold any proposal that tries to assess or file
  on it."
  [iso3]
  (get catalog iso3))

(defn coverage
  "Honest coverage report: how many of the requested jurisdictions actually
  have a spec-basis entry. Never report a missing jurisdiction as covered."
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-lbr R0: " (count catalog)
                 " jurisdictions seeded with an official spec-basis. "
                 "This is a starting catalog for market-entry navigation, "
                 "not a survey of all ~194 jurisdictions -- extend "
                 "`marketentry.facts/catalog`, never fabricate a "
                 "jurisdiction's requirements.")})))

(defn required-evidence-satisfied?
  "Does `submitted` (a set/coll of evidence keywords or strings) satisfy
  every evidence item listed for `iso3`? Missing spec-basis -> never
  satisfied."
  [iso3 submitted]
  (when-let [{:keys [required-evidence]} (spec-basis iso3)]
    (let [need (count required-evidence)
          have (count (filter (set submitted) required-evidence))]
      (= need have))))

(defn evidence-checklist [iso3]
  (:required-evidence (spec-basis iso3) []))

(defn rep-spec-basis
  "The jurisdiction's representative-related requirement map, or nil when
  this catalog has no such regime. For LBR this is deliberately nil --
  this iteration did not find a Liberia-specific representative/director
  exclusion-extension provision in any primary text it was actually able
  to read."
  [iso3]
  (when-let [sb (spec-basis iso3)]
    (when (:rep-owner-authority sb)
      (select-keys sb [:rep-owner-authority :rep-legal-basis :rep-provenance]))))

(defn corporate-number-spec-basis
  "The jurisdiction's corporate-number / tax-id regime, or nil."
  [iso3]
  (when-let [sb (spec-basis iso3)]
    (when (:corporate-number-owner-authority sb)
      (select-keys sb [:corporate-number-owner-authority
                       :corporate-number-legal-basis
                       :corporate-number-provenance]))))

(defn investment-act-spec-basis
  "The jurisdiction's Investment Act foreign-ownership-restriction
  regime, or nil. For LBR this is real and current -- the flagship
  check this vertical adds is grounded here (Investment Act of 2010,
  Schedule: Limited Restrictions to Foreign Ownership of Enterprises)."
  [iso3]
  (when-let [sb (spec-basis iso3)]
    (when (:investment-act-owner-authority sb)
      (select-keys sb [:investment-act-owner-authority
                       :investment-act-legal-basis
                       :investment-act-criteria
                       :investment-act-provenance]))))
