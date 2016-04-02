DESCRIPTION = "Transmission"
LICENSE = "GPLv2 & GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=0dd9fcdc1416ff123c41c785192a1895"

PR="r1"

SRC_URI = "https://transmission.cachefly.net/${BPN}-${PV}.tar.xz \
   file://transmission-daemon.service"

DEPENDS = "libevent openssl curl libtool intltool-native"

SRC_URI[md5sum] = "3fce404a436e3cd7fde80fb6ed61c264"
SRC_URI[sha256sum] = "3a8d045c306ad9acb7bf81126939b9594553a388482efa0ec1bfb67b22acd35f"

inherit autotools-brokensep gettext useradd systemd

#PACKAGECONFIG = "${@base_contains('DISTRO_FEATURES', 'x11', 'gtk', '', d)}"

#PACKAGECONFIG[gtk] = " --with-gtk,--without-gtk,gtk+3,"

# Configure aborts with:
# config.status: error: po/Makefile.in.in was not created by intltoolize.
do_configure_prepend() {
    sed -i /AM_GLIB_GNU_GETTEXT/d ${S}/configure.ac
    cd ${S} && intltoolize --copy --force --automake
}

do_install_append() {
    install -d ${D}${nonarch_base_libdir}/systemd/system
    install -m 0644 ${WORKDIR}/transmission-daemon.service ${D}${nonarch_base_libdir}/systemd/system

    install -d ${D}${sysconfdir}/default
    echo 'TRANSMISSION_EXTRA_PARAMS=""' > ${D}${sysconfdir}/default/transmission
}

SYSTEMD_SERVICE_${PN} = "transmission-daemon.service"

PACKAGES += "${PN}-gui ${PN}-client"

FILES_${PN}-client = " \
    ${bindir}/transmission-remote \
    ${bindir}/transmission-cli \
    ${bindir}/transmission-create \
    ${bindir}/transmission-show \
    ${bindir}/transmission-edit"

FILES_${PN}-gui += "\
    ${bindir}/transmission-gtk \
     ${datadir}/icons \
     ${datadir}/applications \
     ${datadir}/pixmaps"

FILES_${PN} = "\
    ${bindir}/transmission-daemon \
    ${datadir}/transmission \
    ${sysconfdir}"

USERADD_PACKAGES = "${PN}"
GROUPADD_PARAM_${PN} = "--system transmission"
USERADD_PARAM_${PN} = "--home ${localstatedir}/lib/transmission-daemon --create-home \
                       --gid transmission \
                       --shell ${base_bindir}/false \
                       --system \
                       transmission"
