SUMMARY = "Hierarchical, reference counted memory pool system with destructors"
HOMEPAGE = "http://talloc.samba.org"
SECTION = "libs"
LICENSE = "LGPL-3.0+ & GPL-3.0+"

SRC_URI = "http://samba.org/ftp/${BPN}/${BPN}-${PV}.tar.gz"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/LGPL-3.0;md5=bfccfe952269fff2b407dd11f2f3083b \
                    file://${COREBASE}/meta/files/common-licenses/GPL-3.0;md5=c79ff39f19dfec6d293b95dea7b07891"

SRC_URI[md5sum] = "3e285de2228ae67ff0a0f5cec658f627"
SRC_URI[sha256sum] = "7aa5f75b22d4ef9c737b25515f2a2837ddc13014ff4ac6e58dd9e311f41f2cb0"

inherit waf-samba

EXTRA_OECONF += "--disable-rpath \
                 --disable-rpath-install \
                 --bundled-libraries=NONE \
                 --builtin-libraries=replace \
                 --disable-silent-rules \
                 --with-libiconv=${STAGING_DIR_HOST}${prefix}\
                "

PACKAGES += "libtalloc libtalloc-dbg libtalloc-dev pytalloc pytalloc-dbg pytalloc-dev"

#ALLOW_EMPTY_${PN} = "1"
FILES_${PN} = ""
FILES_${PN}-dev = ""
FILES_${PN}-dbg = ""

FILES_libtalloc = "${libdir}/libtalloc.so.* \
                  "
FILES_libtalloc-dbg = "/usr/src/debug/ \
                   ${libdir}/.debug/libtalloc.so.*"
FILES_libtalloc-dev = "${includedir}/ \
                   ${libdir}/libtalloc.so \
                   ${libdir}/pkgconfig/"

FILES_pytalloc = "${libdir}/python${PYTHON_BASEVERSION}/site-packages/* \
                  ${libdir}/libpytalloc-util.so.* \
                 "
FILES_pytalloc-dbg = "${libdir}/python${PYTHON_BASEVERSION}/site-packages/.debug \
                      ${libdir}/.debug/libpytalloc-util.so.*"
FILES_pytalloc-dev = "${libdir}/libpytalloc-util.so"
RDEPENDS_pytalloc = "python"
