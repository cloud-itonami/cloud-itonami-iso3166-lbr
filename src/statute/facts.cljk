(ns statute.facts
  "General-law compliance catalog for Liberia (LBR) -- extends this
  repo's existing `marketentry.facts` (public-procurement market-entry
  only, narrow scope) with a second, orthogonal catalog of statutes a
  company operating in this jurisdiction must generally track for
  compliance. Mirrors cloud-itonami-iso3166-jpn/-deu/-bgr/-aze/-alb/
  -arm/-atg/-ben/-btn/-bwa/-caf/-est/-gmb's `statute.facts`
  (ADR-2607141700, cloud-itonami-compliance-fact-federation).

  Every entry cites an OFFICIAL government-hosted URL -- never
  fabricated.

  - Labour law: the Decent Work Act, 2015. This iteration found a copy
    hosted on the Civil Service Agency's own domain
    (`csa.gov.lr/wp-content/uploads/2024/11/Decent-Work-Act-2015.pdf`,
    fetched directly) and downloaded and read its actual PDF text
    directly (a genuine embedded text layer, `pdftotext` returned full
    readable text). The document's own text reads: 'REPUBLIC OF LIBERIA
    ... AN ACT TO REPEAL TITLE 18 OF THE EXECUTIVE LAW, LABOUR PRACTICES
    LAW AND TO ESTABLISH IN LIEU THEREOF THE DECENT WORK ACT, 2015 ...
    APPROVED JUNE 26, 2015'. This iteration independently corroborated
    the year and existence of this Act from a SEPARATE official source:
    the Ministry of Labour's own 'Library & Documentation' page
    (`mol.gov.lr/library-documentation/`, fetched directly, independent
    of the CSA-hosted PDF) lists among the Library's own holdings
    'Decent Work Act of Liberia of 2015' by name.
  - Tax law: the Liberia Consolidated Revenue Code. The Liberia Revenue
    Authority's own 'Domestic Tax Laws & Regulations' page
    (`revenue.lra.gov.lr/domestic-tax-laws-regulations/`, fetched
    directly) names, by its own section heading, the 'Liberia
    Consolidated Revenue Code As Amended - 2020' as the current tax
    code. This iteration independently corroborated the code's earlier,
    2000 origin from a SEPARATE official source that was not copied
    from LRA's own page: the Investment Act of 2010's own Section 5
    (own text, fetched directly from `nic.gov.lr`): 'An enterprise shall
    be entitled to such fiscal incentives as are applicable under the
    Revenue Code of Liberia of 2000 or amendments thereto', and Section
    15.1: 'the transitional rules enacted by the Liberia Revenue Code of
    2000 as amended.' This iteration did not independently fetch the
    2020 Consolidated Code's own primary statutory text (only LRA's own
    title citation for it plus the Investment Act's own corroboration
    of its 2000 origin), so exact section numbers are not claimed here.
  - Company/commercial-entity law: the Associations Law, Title 5 of the
    Liberian Code of Laws Revised. This iteration found a copy hosted on
    the Ministry of Commerce and Industry's own domain
    (`moci.gov.lr/sites/default/files/documents/Associations%20Law.pdf`,
    fetched directly) and downloaded and read its actual PDF text
    directly. The document's own text reads: 'Associations Law - Title 5
    - Liberian Code of Laws Revised ... Approved: May 19, 1976 ...
    Effective: January 3, 1977', with 'PART I. BUSINESS CORPORATIONS'
    (Chapters 1-14, including Chapter 12 'Foreign Corporations' and
    Chapter 13 'Foreign Maritime Entities'), 'PART II. NOT-FOR-PROFIT
    CORPORATIONS', 'PART III. PARTNERSHIPS' and 'PART IV. OTHER FORMS OF
    ASSOCIATIONS'. This iteration independently corroborated this exact
    citation from a SEPARATE official source: the Investment Act of
    2010's own Section 4 (own text, fetched directly from `nic.gov.lr`,
    an unrelated agency's site): 'A person who intends to establish an
    enterprise shall incorporate and/or register a business organization
    in accordance with the Associations Law or such other laws as are
    relevant to the establishment of the enterprise.' This iteration
    ALSO independently confirmed, by directly comparing chapter/section
    structure against a separately-hosted consolidated PDF
    (`media.liscr.com`, the Liberian International Ship & Corporate
    Registry's own 'Business Corporation Act' publication), that
    LISCR's own 'BCA' republishes this SAME Title 5 statute's own PART I
    for the non-resident/offshore corporate registry LISCR administers
    -- NOT a separate law. This catalog cites the underlying Title 5
    statute once, under its own primary title, rather than duplicating
    it under LISCR's informal short name.
  - Investment law: the Investment Act of 2010. This iteration
    downloaded the Investment Act of 2010's own PDF directly (linked
    from the National Investment Commission's own 'Legal & Regulatory
    Resources' page, `nic.gov.lr/pages/resources/`, fetched directly)
    and read its actual text: 'THE INVESTMENT ACT OF 2010 -- REPEALING
    AND REPLACING THE INVESTMENT INCENTIVES ACT OF 1973 ... This Act
    shall be known as the Investment Act of 2010.' See
    `marketentry.facts` for this Act's own Schedule, which grounds this
    repo's flagship governor check.

  A law not in this table has NO spec-basis, full stop; extend
  `catalog`, do not invent an id/url.")

(def catalog
  "iso3 -> vector of statute entries. `:statute/url` + `:statute/law-number`
  are the citation the governor requires before any compliance-fact
  proposal referencing this law can commit."
  {"LBR"
   [{:statute/id "lbr.decent-work-act-2015"
     :statute/title "Decent Work Act, 2015"
     :statute/jurisdiction "LBR"
     :statute/kind :law
     :statute/law-number "Decent Work Act, 2015 (own text: 'AN ACT TO REPEAL TITLE 18 OF THE EXECUTIVE LAW, LABOUR PRACTICES LAW AND TO ESTABLISH IN LIEU THEREOF THE DECENT WORK ACT, 2015'; own text: 'APPROVED JUNE 26, 2015'. This iteration independently confirmed this is a real, machine-readable document hosted by the Civil Service Agency (CSA), and independently corroborated the year from a SEPARATE official source, the Ministry of Labour's own Library & Documentation page, which lists 'Decent Work Act of Liberia of 2015' by name among its own holdings)"
     :statute/url "https://csa.gov.lr/wp-content/uploads/2024/11/Decent-Work-Act-2015.pdf"
     :statute/url-provenance :official-csa-gov-lr
     :statute/enacted-date "2015-06-26"
     :statute/retrieved-at "2026-07-23"
     :statute/topic #{:labor}}
    {:statute/id "lbr.consolidated-revenue-code-2020"
     :statute/title "Liberia Consolidated Revenue Code As Amended"
     :statute/jurisdiction "LBR"
     :statute/kind :law
     :statute/law-number "Liberia Consolidated Revenue Code As Amended - 2020 -- title confirmed directly from the Liberia Revenue Authority's own 'Domestic Tax Laws & Regulations' page (revenue.lra.gov.lr/domestic-tax-laws-regulations/); the code's earlier 2000 origin independently corroborated from a SEPARATE official source, the Investment Act of 2010's own Section 5 and Section 15.1 (nic.gov.lr), which name 'the Revenue Code of Liberia of 2000 or amendments thereto' in an unrelated context (fiscal-incentive eligibility and transitional rules). This iteration did not independently fetch the 2020 Consolidated Code's own primary statutory text, only LRA's own title citation plus the Investment Act's own corroboration of its 2000 origin"
     :statute/url "https://revenue.lra.gov.lr/domestic-tax-laws-regulations/"
     :statute/url-provenance :official-lra-gov-lr
     :statute/enacted-date "2020"
     :statute/retrieved-at "2026-07-23"
     :statute/topic #{:tax}}
    {:statute/id "lbr.associations-law-title5"
     :statute/title "Associations Law, Title 5 of the Liberian Code of Laws Revised"
     :statute/jurisdiction "LBR"
     :statute/kind :law
     :statute/law-number "Associations Law - Title 5 - Liberian Code of Laws Revised (own text: 'Approved: May 19, 1976 ... Effective: January 3, 1977'). Independently corroborated by the Investment Act of 2010's own Section 4 (nic.gov.lr, a separate official source), which names 'the Associations Law' as the statute a person establishing an enterprise must incorporate/register under. This iteration also confirmed by direct chapter/section comparison that LISCR's own consolidated 'Business Corporation Act' publication (media.liscr.com) republishes this SAME Title 5 statute's own Part I for the non-resident/offshore corporate registry LISCR administers -- not a separate law"
     :statute/url "https://moci.gov.lr/sites/default/files/documents/Associations%20Law.pdf"
     :statute/url-provenance :official-moci-gov-lr
     :statute/enacted-date "1976-05-19"
     :statute/retrieved-at "2026-07-23"
     :statute/topic #{:corporate-governance}}
    {:statute/id "lbr.investment-act-2010"
     :statute/title "Investment Act of 2010"
     :statute/jurisdiction "LBR"
     :statute/kind :law
     :statute/law-number "Investment Act of 2010 (own text: 'THE INVESTMENT ACT OF 2010 -- REPEALING AND REPLACING THE INVESTMENT INCENTIVES ACT OF 1973 ... This Act shall be known as the Investment Act of 2010.'). Linked directly from the National Investment Commission's own 'Legal & Regulatory Resources' page. See marketentry.facts for this Act's own Schedule, which grounds this repo's flagship governor check"
     :statute/url "https://nic.gov.lr/pages/resources/"
     :statute/url-provenance :official-nic-gov-lr
     :statute/enacted-date "2010"
     :statute/retrieved-at "2026-07-23"
     :statute/topic #{:investment}}]})

(defn spec-basis
  "The jurisdiction's statute vector, or nil -- nil means NO spec-basis
  for that jurisdiction yet."
  [iso3]
  (get catalog iso3))

(defn coverage
  "Honest coverage report, same shape/discipline as `marketentry.facts/coverage`:
  never report a missing jurisdiction as covered."
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-lbr statute.facts Wave 0 (ADR-2607141700): "
                 (count (get catalog "LBR")) " LBR statute(s) seeded with an "
                 "official citation. Extend `statute.facts/catalog`, "
                 "never fabricate a law-id or URL.")})))

(defn by-topic
  "Statutes for `iso3` tagged with `topic` (e.g. :labor, :tax)."
  [iso3 topic]
  (filterv #(contains? (:statute/topic %) topic) (spec-basis iso3)))
