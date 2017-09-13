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

DEPENDS = "openssl cyrus-sasl util-linux jansson db zlib pcre net-snmp tcp-wrappers e2fsprogs icu"

SRC_URI[md5sum] = "c5d30b3dc91bc3949be9f43e1c5438fb"
SRC_URI[sha256sum] = "ed17b4321bbe54df2c9b8832e189f52de4561f51e199f7c620216e79910c73a0"

inherit autotools-brokensep pkgconfig useradd systemd

EXTRA_OECONF="--enable-gssapi=no \
              --enable-replication \
              --enable-murder \
              --enable-idled \
              --enable-autocreate \
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
