DESCRIPTION = "MiniDLNA (aka ReadyDLNA) is server software with the aim of \
being fully compliant with DLNA/UPnP-AV clients."
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://COPYING;md5=b1a795ac1a06805cf8fd74920bc46b5c"

DEPENDS = "flac libav jpeg sqlite3 libexif libogg libvorbis libid3tag"

SRC_URI = "${SOURCEFORGE_MIRROR}/project/${BPN}/${BPN}/${PV}/${BPN}-${PV}.tar.gz \
    file://minidlna.service \
"

SRC_URI[md5sum] = "67c9e91285bc3801fd91a5d26ea775d7"
SRC_URI[sha256sum] = "9814c04a2c506a0dd942c4218d30c07dedf90dabffbdef2d308a3f9f23545314"

inherit autotools-brokensep gettext systemd

SYSTEMD_SERVICE_${PN} = "minidlna.service"
SYSTEMD_AUTO_ENABLE = "disable"

do_install_append() {
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/minidlna.service ${D}${systemd_unitdir}/system/
}
