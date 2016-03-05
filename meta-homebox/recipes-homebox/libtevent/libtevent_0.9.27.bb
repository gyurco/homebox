SUMMARY = "Hierarchical, reference counted memory pool system with destructors"
HOMEPAGE = "http://tevent.samba.org"
SECTION = "libs"
LICENSE = "LGPLv3+"

DEPENDS += "libtalloc"
RDEPENDS_${PN} += "libtalloc"
RDEPENDS_python-tevent = "python"

SRC_URI = "http://samba.org/ftp/tevent/tevent-${PV}.tar.gz"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/LGPL-3.0;md5=bfccfe952269fff2b407dd11f2f3083b"

SRC_URI[md5sum] = "322d9bbf2e657fa0512b52e1c3c12982"
SRC_URI[sha256sum] = "fbc9ed76aa92f333629b61a1bfa616819704896885937a855e07d3bc0b65c913"

inherit waf-samba

S = "${WORKDIR}/tevent-${PV}"

EXTRA_OECONF += "--disable-rpath \
                 --bundled-libraries=NONE \
                 --builtin-libraries=replace \
                 --with-libiconv=${STAGING_DIR_HOST}${prefix}\
                 --without-gettext \
                "

PACKAGES += "python-tevent python-tevent-dbg"

FILES_python-tevent = "${libdir}/python${PYTHON_BASEVERSION}/site-packages/*"
FILES_python-tevent-dbg = "${libdir}/python${PYTHON_BASEVERSION}/site-packages/.debug"
