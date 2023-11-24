(window.webpackJsonp=window.webpackJsonp||[]).push([[33],{329:function(e,t,s){"use strict";s.r(t);var a=s(4),r=Object(a.a)({},(function(){var e=this,t=e._self._c;return t("ContentSlotsDistributor",{attrs:{"slot-key":e.$parent.slotKey}},[t("h1",{attrs:{id:"adr-008-security-roles"}},[t("a",{staticClass:"header-anchor",attrs:{href:"#adr-008-security-roles"}},[e._v("#")]),e._v(" ADR-008 security-roles")]),e._v(" "),t("h2",{attrs:{id:"status"}},[t("a",{staticClass:"header-anchor",attrs:{href:"#status"}},[e._v("#")]),e._v(" Status")]),e._v(" "),t("adr-status",{attrs:{status:"accepted"}}),e._v(" "),t("h2",{attrs:{id:"context"}},[t("a",{staticClass:"header-anchor",attrs:{href:"#context"}},[e._v("#")]),e._v(" Context")]),e._v(" "),t("p",[e._v("We need to restrict access to certain features of the application so users can only use and see what is meant for them.")]),e._v(" "),t("p",[e._v("Permission-based access would require a non-open source component, and this product should be open source in all aspects.")]),e._v(" "),t("h2",{attrs:{id:"decision"}},[t("a",{staticClass:"header-anchor",attrs:{href:"#decision"}},[e._v("#")]),e._v(" Decision")]),e._v(" "),t("p",[e._v("We are using a role-based security model in favor of a permission-based model.")]),e._v(" "),t("h3",{attrs:{id:"todo"}},[t("a",{staticClass:"header-anchor",attrs:{href:"#todo"}},[e._v("#")]),e._v(" Todo")]),e._v(" "),t("ul",[t("li",[e._v("All backend rest controller methods need the "),t("code",[e._v("@PreAuthorized")]),e._v("-Annotation for role binding")]),e._v(" "),t("li",[e._v("All frontend components that should be secured should have the custom v-security directive")]),e._v(" "),t("li",[e._v("Roles for personas have to be defined and implemented in the keycloak")])]),e._v(" "),t("h2",{attrs:{id:"consequences"}},[t("a",{staticClass:"header-anchor",attrs:{href:"#consequences"}},[e._v("#")]),e._v(" Consequences")]),e._v(" "),t("p",[e._v("Access restrictions have to be considered for each feature.\nBy using role-based access management, the authorization is less complicated,\nthan it would be with permission-based access management, as the granularity is lower and therefore easier to maintain.")])],1)}),[],!1,null,null,null);t.default=r.exports}}]);