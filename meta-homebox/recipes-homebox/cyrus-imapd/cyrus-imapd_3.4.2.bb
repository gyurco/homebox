DESCRIPTION = "Cyrus-imapd"
LICENSE = "CMU"
LIC_FILES_CHKSUM = "file://COPYING;md5=c1754b012ddda8fb8369391bfb72d9c0"
PR="r1"

SRC_URI = "https://github.com/cyrusimap/${BPN}/releases/download/${BPN}-${PV}/${BPN}-${PV}.tar.gz \
   file://cyrus-imapd.service \
   file://cyrus-imapd.imap-2.3.x-conf \
   file://cyrus-imapd.pam-config \
   file://cyrus.conf \
   "

DEPENDS = "bison-native openssl cyrus-sasl util-linux jansson db zlib pcre net-snmp tcp-wrappers e2fsprogs icu"

SRC_URI[sha256sum] = "08b225b5be70a1a2b054169ab09d8a4977524e4681fe0fd9a6a4f843496152c5"

inherit autotools-brokensep pkgconfig useradd systemd

EXTRA_OECONF="--enable-gssapi=no \
              --enable-replication \
              --enable-murder \
              --enable-idled \
              --enable-autocreate \
              --with-cyrus-user=cyrus-imap \
              --with-mmap=shared \
              --without-perl \
              --bindir=${bindir} \
              --sbindir=${sbindir} \
              --libexecdir=${libdir}/${BPN} \
              --sysconfdir=${sysconfdir} \
             "

do:install:append() {
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
GROUPADD_PARAM:${PN} = "--system cyrus-imap"
USERADD_PARAM:${PN} = "--home ${localstatedir}/lib/imap --create-home \
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
