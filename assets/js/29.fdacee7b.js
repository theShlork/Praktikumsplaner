(window.webpackJsonp=window.webpackJsonp||[]).push([[29],{324:function(e,t,a){"use strict";a.r(t);var r=a(4),s=Object(r.a)({},(function(){var e=this,t=e._self._c;return t("ContentSlotsDistributor",{attrs:{"slot-key":e.$parent.slotKey}},[t("h1",{attrs:{id:"adr-004-reduce-mapstructmapper-tests-to-a-minimum"}},[t("a",{staticClass:"header-anchor",attrs:{href:"#adr-004-reduce-mapstructmapper-tests-to-a-minimum"}},[e._v("#")]),e._v(" ADR-004 Reduce mapstructmapper tests to a minimum")]),e._v(" "),t("h2",{attrs:{id:"status"}},[t("a",{staticClass:"header-anchor",attrs:{href:"#status"}},[e._v("#")]),e._v(" Status")]),e._v(" "),t("adr-status",{attrs:{status:"accepted"}}),e._v(" "),t("h2",{attrs:{id:"context"}},[t("a",{staticClass:"header-anchor",attrs:{href:"#context"}},[e._v("#")]),e._v(" Context")]),e._v(" "),t("p",[e._v("Testing is all about showing the functionality of our application. We rely on the fact that the\nframeworks and libraries we use during development are sufficiently tested. Therefore, we can focus on testing our\nlogic while writing tests, especially unit tests.")]),e._v(" "),t("h2",{attrs:{id:"decision"}},[t("a",{staticClass:"header-anchor",attrs:{href:"#decision"}},[e._v("#")]),e._v(" Decision")]),e._v(" "),t("p",[e._v("Mappers are only tested if we define explicit mapping logic.\nThis explicit mapping logic is present, for example,\nwhen we define default implementations in interfaces or map fields with different names via the "),t("code",[e._v("@mapping")]),e._v(" annotation or define an expression for the mapping.")]),e._v(" "),t("p",[e._v("Via the configuration of the mapstructprocessor for the "),t("code",[e._v("maven-compile-plugin")]),e._v(",\nit is defined that all fields in the target object are considered for mapping.")]),e._v(" "),t("p",[e._v("If a field is not part of the mapping declaration, an error should occur.")]),e._v(" "),t("h3",{attrs:{id:"todo"}},[t("a",{staticClass:"header-anchor",attrs:{href:"#todo"}},[e._v("#")]),e._v(" Todo")]),e._v(" "),t("ul",[t("li",[e._v("Add compiler argument at "),t("code",[e._v("maven-compile-plugin")]),e._v(": "),t("code",[e._v("-Amapstruct.unmappedTargetPolicy=ERROR")])]),e._v(" "),t("li",[e._v("Ignore target properties when mapping if they are not fillable by the source object:\n"),t("code",[e._v('@Mapping(target="ignorableProp", ignore=true)')])])]),e._v(" "),t("h2",{attrs:{id:"consequences"}},[t("a",{staticClass:"header-anchor",attrs:{href:"#consequences"}},[e._v("#")]),e._v(" Consequences")]),e._v(" "),t("p",[e._v("There are fewer tests to write, but we rely more on Mapstruct to work correctly.\nMore knowledge about Mapstruct is required, since assumptions are less checked.")])],1)}),[],!1,null,null,null);t.default=s.exports}}]);