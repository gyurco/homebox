FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://minidlna-systemd.service"

do_install_append() {
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/minidlna-systemd.service ${D}${systemd_unitdir}/system/minidlna.service
}
