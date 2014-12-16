FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
	   file://saslauthd \
	   file://saslauthd.service \
	   "

inherit systemd

SYSTEMD_SERVICE_${PN} = "saslauthd.service"

do_install_append() {
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/saslauthd.service ${D}${systemd_unitdir}/system/

    install -d ${D}${sysconfdir}/default
    install -m 0644 ${WORKDIR}/saslauthd ${D}${sysconfdir}/default/saslauthd
}


FILES_${PN}-bin       += "${systemd_unitdir}/system/saslauthd.service \
                          ${sysconfdir}/default/saslauthd"
