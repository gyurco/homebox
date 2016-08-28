DESCRIPTION = "Cyrus-imapd"
LICENSE = "CMU"
LIC_FILES_CHKSUM = "file://COPYING;md5=b24dd5815bd69137c774dd6b5e5250f4"
PR="r1"

SRC_URI = "http://www.cyrusimap.org/releases/${BPN}-${PV}.tar.gz \
   file://cyrus-imapd.service \
   file://cyrus-imapd.imap-2.3.x-conf \
   file://cyrus-imapd.pam-config \
   file://cyrus.conf \
   "

DEPENDS = "openssl cyrus-sasl util-linux jansson db zlib pcre net-snmp tcp-wrappers e2fsprogs"

SRC_URI[md5sum] = "05221f5ce606a4db82e27e2d55a699db"
SRC_URI[sha256sum] = "8e1bb6cc373bf318b5895b299c8344eaecbfb83db4566182f5d57f2707bce23d"

inherit autotools-brokensep pkgconfig useradd systemd

EXTRA_OECONF="--enable-gssapi=no --enable-replication --enable-murder --enable-idled --with-cyrus-prefix=${sbindir} --with-service-path=${sbindir} --without-perl"

do_install_append() {
    install -d ${D}${nonarch_base_libdir}/systemd/system
    install -m 0644 ${WORKDIR}/cyrus-imapd.service ${D}${nonarch_base_libdir}/systemd/system

    install -d ${D}${sysconfdir}/default
    echo 'CYRUSOPTIONS=""' > ${D}${sysconfdir}/default/cyrus-imapd

    install -m 0644 ${WORKDIR}/cyrus-imapd.imap-2.3.x-conf ${D}${sysconfdir}/imapd.conf
    install -m 0644 ${WORKDIR}/cyrus.conf ${D}${sysconfdir}/cyrus.conf
    install -d ${D}${sysconfdir}/pam.d
    install -m 0644 ${WORKDIR}/cyrus-imapd.pam-config ${D}${sysconfdir}/pam.d/imap
}

do_install() {
    oe_runmake 'DESTDIR=${D}' install
}

SYSTEMD_SERVICE_${PN} = "cyrus-imapd.service"

USERADD_PACKAGES = "${PN}"
GROUPADD_PARAM_${PN} = "--system cyrus"
USERADD_PARAM_${PN} = "--home ${localstatedir}/lib/imap --create-home \
                       --gid cyrus \
                       --shell ${base_bindir}/false \
                       --system \
                       cyrus"

#FILES_${PN}-doc += "/usr/man"

CONFFILES_${PN} = "\
  ${sysconfdir}/imapd.conf \
  ${sysconfdir}/cyrus.conf \
  ${sysconfdir}/pam.d/imap \
  "