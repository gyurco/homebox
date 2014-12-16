DESCRIPTION = "Transmission"
LICENSE = "GPLv2 & GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=a1923fe8f8ff37c33665716af0ec84f1"

PR="r1"

SRC_URI = "https://transmission.cachefly.net/${BPN}-${PV}.tar.xz \
   file://transmission-daemon.service"

DEPENDS = "libevent openssl curl libtool intltool-native"

SRC_URI[md5sum] = "411aec1c418c14f6765710d89743ae42"
SRC_URI[sha256sum] = "a9fc1936b4ee414acc732ada04e84339d6755cd0d097bcbd11ba2cfc540db9eb"

inherit autotools-brokensep gettext useradd systemd

PACKAGECONFIG = "${@base_contains('DISTRO_FEATURES', 'x11', 'gtk', '', d)}"

PACKAGECONFIG[gtk] = " --with-gtk,--without-gtk,gtk+,"

# Configure aborts with:
# config.status: error: po/Makefile.in.in was not created by intltoolize.
do_configure_prepend() {
    sed -i /AM_GLIB_GNU_GETTEXT/d ${S}/configure.ac
    pushd ${S}
    intltoolize --copy --force --automake
    popd
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
