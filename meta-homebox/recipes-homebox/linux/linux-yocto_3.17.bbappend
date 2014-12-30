FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

KERNEL_FEATURES += "features/nfsd/nfsd-enable.scc"
KERNEL_FEATURES += "features/grsec/grsec.scc"

COMPATIBLE_MACHINE_genericx86-64 = "genericx86-64"
KBRANCH_genericx86-64  = "standard/common-pc-64/base"
KMACHINE_genericx86-64 ?= "common-pc-64"

###
SRC_URI += "file://raid.cfg"
SRC_URI += "file://sensors.cfg"
SRC_URI += "file://squashfs.cfg"
SRC_URI += "file://lio.cfg"
SRC_URI += "file://iostat.cfg"
SRC_URI += "file://snd.cfg"
