(window.webpackJsonp=window.webpackJsonp||[]).push([[37],{333:function(e,t,s){"use strict";s.r(t);var r=s(4),a=Object(r.a)({},(function(){var e=this,t=e._self._c;return t("ContentSlotsDistributor",{attrs:{"slot-key":e.$parent.slotKey}},[t("h1",{attrs:{id:"security-concept"}},[t("a",{staticClass:"header-anchor",attrs:{href:"#security-concept"}},[e._v("#")]),e._v(" Security concept")]),e._v(" "),t("p",[e._v("For the security concept we identified the "),t("a",{attrs:{href:"#personas"}},[e._v("personas")]),e._v(" listed below.\nThen we determined which persona is allowed to use which feature.\nThe results of this process are listed under "),t("a",{attrs:{href:"#features"}},[e._v("features")])]),e._v(" "),t("h2",{attrs:{id:"personas"}},[t("a",{staticClass:"header-anchor",attrs:{href:"#personas"}},[e._v("#")]),e._v(" Personas")]),e._v(" "),t("ul",[t("li",[t("RouterLink",{attrs:{to:"/glossary.html#ortliche-ausbildungsleitung"}},[e._v("örtliche Ausbildungsleitung")])],1),e._v(" "),t("li",[t("RouterLink",{attrs:{to:"/glossary.html#ortliche-ausbilderin"}},[e._v("örtliche Ausbilderin")])],1)]),e._v(" "),t("h2",{attrs:{id:"features"}},[t("a",{staticClass:"header-anchor",attrs:{href:"#features"}},[e._v("#")]),e._v(" Features")]),e._v(" "),t("ul",[t("li",[t("RouterLink",{attrs:{to:"/features/ExcelImport.html"}},[e._v("Excel-Import")]),e._v(" (örtliche Ausbildungsleitung)")],1),e._v(" "),t("li",[t("RouterLink",{attrs:{to:"/features/meldezeitraum.html"}},[e._v("Meldezeitraum")]),e._v(" (örtliche Ausbildungsleitung)")],1),e._v(" "),t("li",[t("RouterLink",{attrs:{to:"/features/MeldungOertlAusbilder.html"}},[e._v("Meldung örtl. Ausbilder")]),e._v(" (örtliche Ausbilderin)")],1)]),e._v(" "),t("h2",{attrs:{id:"technical-implementation"}},[t("a",{staticClass:"header-anchor",attrs:{href:"#technical-implementation"}},[e._v("#")]),e._v(" Technical Implementation")]),e._v(" "),t("p",[e._v("On the frontend side the security features are controlled by the "),t("code",[e._v("VITE_APP_SECURITY")]),e._v(" in the "),t("code",[e._v(".env")]),e._v(" files.\nBy default security features are enabled in "),t("code",[e._v("production")]),e._v(" and disabled in "),t("code",[e._v("development")]),e._v(".")]),e._v(" "),t("p",[e._v("On the frontend side we have a "),t("code",[e._v("v-security")]),e._v(" directive. This directive has two modifiers "),t("code",[e._v("allow")]),e._v(" and "),t("code",[e._v("restrict")]),e._v(".\n"),t("code",[e._v("allow")]),e._v(" is a whitelisting mechanism so, the role that is used as a parameter with "),t("code",[e._v("allow")]),e._v(" can see the content displayed underneath the tag.\n"),t("code",[e._v("restrict")]),e._v(" is a blacklisting mechanism, so the role that is used as a parameter with "),t("code",[e._v("restrct")]),e._v(" can not see the content displayed underneath the tag.\nThe reason for black- and whitelisting is, that there is the persona "),t("RouterLink",{attrs:{to:"/glossary.html#ortliche-ausbilderin"}},[e._v("Ausbilder")]),e._v(" which is applicable to every person that is not a "),t("RouterLink",{attrs:{to:"/glossary.html#nwk"}},[e._v("NWK")]),e._v(".\nIt would be unreasonable to give this role to every employee that is not a "),t("RouterLink",{attrs:{to:"/glossary.html#nwk"}},[e._v("NWK")]),e._v(" so we decided not to create a role for this, but instead\nuse this black- and whitelisting, depending on the other roles.")],1)])}),[],!1,null,null,null);t.default=a.exports}}]);