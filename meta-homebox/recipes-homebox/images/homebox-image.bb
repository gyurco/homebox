DESCRIPTION = "Homebox"

inherit core-image

IMAGE_FEATURES += "package-management ssh-server-openssh"

KERNEL_FEATURES += "features/nfsd/nfsd-enable.scc"
KERNEL_FEATURES += "features/grsec/grsec.scc"

AUTO_SYSLINUXCFG = "1"
INITRD_IMAGE ?= "core-image-minimal-initramfs"
INITRD ?= "${DEPLOY_DIR_IMAGE}/${INITRD_IMAGE}-${MACHINE}.cpio.gz"
SYSLINUX_ROOT = "root=/dev/ram0"
SYSLINUX_TIMEOUT ?= "10"
SYSLINUX_LABELS = "boot"

do_bootimg[depends] += "${INITRD_IMAGE}:do_rootfs"
do_bootimg[depends] += "${PN}:do_rootfs"
do_bootdirectdisk_onepart[depends] += "${INITRD_IMAGE}:do_rootfs"
do_bootdirectdisk_onepart[depends] += "${PN}:do_rootfs"

inherit boot-directdisk-onepart
#If no need for a partitioned diskimage:
#inherit bootimg

IMAGE_FSTYPES = "squashfs-xz"
IMAGE_TYPEDEP_live = "squashfs-xz"
ROOTFS = "${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.squashfs-xz"

IMAGE_INSTALL = "\
    apache2 php-fpm php-fpm-apache2 \
    mdadm \
    rpm smartpm \
    acpid dmidecode usbutils \
    libav minidlna transmission transmission-client mpd alsa-utils \
    samba \
    curl xz \
    screen \
    ntp ntp-utils \
    tftp-hpa tftp-hpa-server \
    smartmontools hdparm sdparm \
    strace ldd lsof nmap iotop iperf tcpdump \
    wget rsync \
    lmsensors-sensors lmsensors-fancontrol lmsensors-pwmconfig lmsensors-isatools lmsensors-sensorsdetect \
    net-snmp-client net-snmp-mibs net-snmp-server net-snmp-server-snmpd net-snmp-server-snmptrapd \
    systemd-analyze \
    openvpn strongswan \
    ebtables bridge-utils cifs-utils \
    cryptsetup \
    cyrus-sasl-bin \
    lio-utils \
    python-json \
    postfix dovecot cyrus-imapd \
    packagegroup-core-boot \
    packagegroup-core-full-cmdline \
    ${CORE_IMAGE_EXTRA_INSTALL} \
    "
#    openldap openldap-slapd openldap-bin openldap-backends 

export IMAGE_BASENAME = "homebox"

NOISO="1"
