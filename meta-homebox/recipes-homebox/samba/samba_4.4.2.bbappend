FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
           file://10-use-only-libsystemd.patch \
          "
