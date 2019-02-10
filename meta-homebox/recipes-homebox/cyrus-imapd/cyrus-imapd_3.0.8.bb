DESCRIPTION = "Cyrus-imapd"
LICENSE = "CMU"
LIC_FILES_CHKSUM = "file://COPYING;md5=012dbb3a606ccc09f32836aa337dc2fb"
PR="r1"

SRC_URI = "http://www.cyrusimap.org/releases/${BPN}-${PV}.tar.gz \
   file://cyrus-imapd.service \
   file://cyrus-imapd.imap-2.3.x-conf \
   file://cyrus-imapd.pam-config \
   file://cyrus.conf \
   "

DEPENDS = "bison-native openssl cyrus-sasl util-linux jansson db zlib pcre net-snmp tcp-wrappers e2fsprogs icu"

SRC_URI[md5sum] = "7dc5cf7987d146c6df608146087e0c75"
SRC_URI[sha256sum] = "76586dbf2e237b380effb63568faf42a058b3dd3a1ff48032559ae460185d5ab"

inherit autotools-brokensep pkgconfig useradd systemd

EXTRA_OECONF="--enable-gssapi=no \
              --enable-replication \
              --enable-murder \
              --enable-idled \
              --enable-autocreate \
              --with-cyrus-user=cyrus-imap \
              --with-cyrus-group=cyrus-imap \
              --with-mmap=shared \
              --without-perl \
              --bindir=${bindir} \
              --sbindir=${sbindir} \
              --libexecdir=${libdir}/${BPN} \
              --sysconfdir=${sysconfdir} \
             "

do_install_append() {
    install -d ${D}${systemd_unitdir}/system
    sed 's|%LIBEXECDIR%|${libdir}/${BPN}|' ${WORKDIR}/cyrus-imapd.service > ${D}${systemd_unitdir}/system/cyrus-imapd.service

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
GROUPADD_PARAM_${PN} = "--system cyrus-imap"
USERADD_PARAM_${PN} = "--home ${localstatedir}/lib/imap --create-home \
                       --gid cyrus-imap \
                       --shell ${base_bindir}/false \
                       --system \
                       cyrus-imap"

#FILES_${PN}-doc += "/usr/man"

CONFFILES_${PN} = "\
  ${sysconfdir}/imapd.conf \
  ${sysconfdir}/cyrus.conf \
  ${sysconfdir}/pam.d/imap \
  "
