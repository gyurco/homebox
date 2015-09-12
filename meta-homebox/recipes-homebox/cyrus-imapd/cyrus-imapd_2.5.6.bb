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
RDEPENDS_${PN} += 'perl'

SRC_URI[md5sum] = "ba6f44b742f8b255e251bc056f4bc4a1"
SRC_URI[sha256sum] = "60f9be717c17497e7d69f468a8da3daf926c49edeac958bc769b4547f10103df"

inherit autotools-brokensep perlnative pkgconfig useradd systemd

EXTRA_OECONF="--enable-gssapi=no --enable-replication --enable-murder --enable-idled --with-cyrus-prefix=${sbindir} --with-service-path=${sbindir}"

do_configure() {
  aclocal -I cmulocal
  libtoolize --install
  automake --add-missing || autoreconf -v && automake --add-missing
  autoreconf -v
  oe_runconf
}

do_install_append() {
    mv ${D}/usr/man ${D}${mandir}
    install -d ${D}${nonarch_base_libdir}/systemd/system
    install -m 0644 ${WORKDIR}/cyrus-imapd.service ${D}${nonarch_base_libdir}/systemd/system

    mv ${D}${libdir}/perl-native/perl ${D}${libdir}
    rm -rf ${D}${libdir}/perl-native

    install -d ${D}${sysconfdir}/default
    echo 'CYRUSOPTIONS=""' > ${D}${sysconfdir}/default/cyrus-imapd

    install -m 0644 ${WORKDIR}/cyrus-imapd.imap-2.3.x-conf ${D}${sysconfdir}/imapd.conf
    install -m 0644 ${WORKDIR}/cyrus.conf ${D}${sysconfdir}/cyrus.conf
    install -d ${D}${sysconfdir}/pam.d
    install -m 0644 ${WORKDIR}/cyrus-imapd.pam-config ${D}${sysconfdir}/pam.d/imap
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

FILES_${PN}-dbg += "${libdir}/perl/5.22.0/auto/Cyrus/IMAP/.debug \
    ${libdir}/perl/5.22.0/auto/Cyrus/SIEVE/managesieve/.debug"

FILES_${PN} += "${libdir}/perl"

CONFFILES_${PN} = "\
  ${sysconfdir}/imapd.conf \
  ${sysconfdir}/cyrus.conf \
  ${sysconfdir}/pam.d/imap \
  "
