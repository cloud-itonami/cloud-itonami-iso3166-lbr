# cloud-itonami-iso3166-lbr

**LBR**: Liberia.

- PPCC (Public Procurement and Concessions Commission) / Amendment and
  Restatement of the Public Procurement and Concessions Act, 2010
  (approved January 12, 2026) public-procurement compliance
- Liberia Business Registry (LBR -- an autonomous agency, not to be
  confused with this repo's own ISO3 code) business/company
  registration under the Associations Law, Title 5 of the Liberian
  Code of Laws Revised + Liberia Revenue Authority (LRA) TIN
  registration
- Investment Act of 2010 (National Investment Commission) Schedule
  ('Limited Restrictions to Foreign Ownership of Enterprises')
  reserved-sector + ownership-percentage-scaled capital-threshold gate

AGPL-3.0-or-later.

## Market-entry / statute catalogs

Governed public-sector market-entry compliance actor, same architecture
as every `cloud-itonami-iso3166-*` sibling in this fleet:

- `src/marketentry/{facts,governor,phase,sim,operation,registry,store,
  marketentryllm}.cljc` -- the actor. `facts.cljc` cites the Public
  Procurement and Concessions Commission (PPCC, three confirmed
  generations of its own Act: 2005, 2010, and the current January 2026
  Amendment and Restatement), the Liberia Business Registry (LBR,
  registration under the Associations Law, Title 5) and the Liberia
  Revenue Authority (LRA, Taxpayer Identification Number registration,
  Liberia Consolidated Revenue Code As Amended - 2020). `governor.cljc`'s
  flagship check independently recomputes whether an engagement
  declaring `:foreign-company? true` is eligible under the Investment
  Act of 2010's own Schedule -- a two-part gate combining a categorical
  reserved-sector exclusion list (sixteen activities reserved
  exclusively for Liberians, with the Schedule's own internal
  US$50,000 carve-out for auto repair shops) with a SEPARATE list of
  twelve activities where foreign capital is admitted only above a
  threshold conditional on the engagement's own declared ownership
  structure (US$500,000 wholly foreign-owned vs. US$300,000 in a
  >=25%-Liberian partnership) -- a check shape genuinely different
  from every other iso3166 sibling's (see the namespace docstrings for
  the full research trail and honestly-narrowed scope, including facts
  this iteration could NOT verify, such as the exact citation for the
  Act Creating the LRA and a Liberia-specific representative/director
  exclusion-extension provision).
- `src/statute/facts.cljc` -- general-law catalog: the Decent Work Act,
  2015 (labor, Civil Service Agency-hosted, independently corroborated
  by the Ministry of Labour's own site), the Liberia Consolidated
  Revenue Code As Amended - 2020 (tax, Liberia Revenue Authority),
  the Associations Law, Title 5 of the Liberian Code of Laws Revised
  (company/commercial-entity law, Approved May 19, 1976, Ministry of
  Commerce and Industry-hosted, independently corroborated by the
  Investment Act of 2010's own Section 4), and the Investment Act of
  2010 itself (investment law, National Investment Commission).

Every citation is curl/WebFetch-verified against an official source
(ppcc.gov.lr, nic.gov.lr, revenue.lra.gov.lr, csa.gov.lr, mol.gov.lr,
moci.gov.lr); the Liberia Business Registry's own portal domain
(`www.lbr.gov.lr`, named directly by NIC's own site) timed out on
connection for this iteration, and the related public portal
`liberiabusinessregistry.com` returned only a client-side-rendered
React SPA shell -- an honestly-flagged ACCESS gap, not a claim of
non-existence, see `marketentry.facts`'s docstring. This iteration also
independently confirmed, by directly comparing chapter/section
structure, that the Liberian International Ship & Corporate Registry's
(LISCR) own 'Business Corporation Act' publication republishes the SAME
Title 5 Associations Law statute for the non-resident/offshore
corporate registry LISCR administers (reflecting Liberia's history as
a flag-of-convenience shipping registry) -- a DIFFERENT administrator
of the same law, not a separate law, and explicitly OUT OF SCOPE for
this catalog's domestic-market-entry focus. See `marketentry.facts`'s
docstring for the full non-conflation discussion.

## Culture catalog

Alongside the market-entry / statute catalogs, this repo carries a
**country-level regional-culture catalog** (ADR-2607171400 addendum 2,
`cloud-itonami-municipality-culture-catalog` Wave 1, in
`com-junkawasaki/root`) — national dishes, protected products, beverages,
crafts, festivals and heritage sites for Liberia:

- `src/culture/facts.cljc` — the catalog, source of truth (keyed by
  uppercase ISO3, mirroring `statute.facts`).
- `schema/culture.edn` — DataScript schema.
- `data/culture-tx.edn` — derived DataScript tx-data (regenerated from
  the catalog, never hand-edited).

City-level counterparts live in the `cloud-itonami-municipality-*` repos.
Same provenance discipline as the compliance catalogs: every entry cites a
source URL that was actually fetched and read on `:culture/retrieved-at`;
summaries state only what the cited source confirms. An item not in
`culture.facts/catalog` has no spec-basis — never fabricate one.
