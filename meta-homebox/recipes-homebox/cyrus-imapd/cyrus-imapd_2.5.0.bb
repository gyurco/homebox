DESCRIPTION = "Cyrus-imapd"
LICENSE = "CMU"
LIC_FILES_CHKSUM = "file://COPYRIGHT;md5=b24dd5815bd69137c774dd6b5e5250f4"
PR="r2"

SRC_URI = "http://www.cyrusimap.org/releases/${BPN}-${PV}.tar.gz \
   file://cyrus-imapd.service \
   file://cyrus-imapd-2.5-ctl_mboxlist-mbtype.patch \
   file://cyrus-imapd.imap-2.3.x-conf \
   file://cyrus-imapd.pam-config \
   file://cyrus.conf \
   "

DEPENDS = "openssl cyrus-sasl util-linux jansson db zlib pcre net-snmp tcp-wrappers e2fsprogs"
RDEPENDS_${PN} += 'perl'

SRC_URI[md5sum] = "e699ab5d8b0a8255ac05b024578e9bdc"
SRC_URI[sha256sum] = "7706bf80758debce681ae96ee9eac8be181e1066773075cf340727b306fe0543"

inherit autotools-brokensep perlnative pkgconfig useradd systemd

EXTRA_OECONF="--enable-gssapi=no --enable-replication --enable-murder --enable-idled --with-cyrus-prefix=${sbindir} --with-service-path=${sbindir}"


do_configure() {
#  cp ${STAGING_DATADIR_NATIVE}/libtool/config/ltmain.sh ${S}/build
#  rm -f ${S}/libtool
#  aclocal
#  libtoolize --force --copy
#  gnu-configize
#  autoconf
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

FILES_${PN}-dbg += "${libdir}/perl/5.20.0/auto/Cyrus/IMAP/.debug \
    ${libdir}/perl/5.20.0/auto/Cyrus/SIEVE/managesieve/.debug"

FILES_${PN} += "${libdir}/perl"

CONFFILES_${PN} = "\
  ${sysconfdir}/imapd.conf \
  ${sysconfdir}/cyrus.conf \
  ${sysconfdir}/pam.d/imap \
  "
