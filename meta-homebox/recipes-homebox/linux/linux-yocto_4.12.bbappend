FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

KERNEL_FEATURES += "features/nfsd/nfsd-enable.scc"
KERNEL_FEATURES += "features/netfilter/netfilter.scc"
KERNEL_FEATURES += "features/net_sched/net_sched.scc"
KERNEL_FEATURES += "features/overlayfs/overlayfs.scc"

COMPATIBLE_MACHINE_genericx86-64 = "genericx86-64"
KBRANCH_genericx86-64  = "standard/base"
KMACHINE_genericx86-64 ?= "common-pc-64"

###
SRC_URI += "file://raid.cfg"
SRC_URI += "file://sensors.cfg"
SRC_URI += "file://squashfs.cfg"
SRC_URI += "file://lio.cfg"
SRC_URI += "file://iostat.cfg"
SRC_URI += "file://snd.cfg"
SRC_URI += "file://bluetooth.cfg"
