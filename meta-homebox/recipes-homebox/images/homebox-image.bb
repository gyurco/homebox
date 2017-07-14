DESCRIPTION = "Homebox"

inherit core-image

DEPENDS += "squashfs-tools-native"

IMAGE_FEATURES += "package-management ssh-server-openssh"

KERNEL_FEATURES += "features/nfsd/nfsd-enable.scc"
KERNEL_FEATURES += "features/grsec/grsec.scc"

AUTO_SYSLINUXCFG = "1"
INITRD_IMAGE_VM ?= "core-image-minimal-initramfs"
SYSLINUX_ROOT = "root=/dev/ram0"
SYSLINUX_TIMEOUT ?= "10"
SYSLINUX_LABELS = "boot"

do_bootimg[depends] = "${PN}:do_image_squashfs_xz"
do_bootdirectdisk_onepart[depends] += "${PN}:do_image_squashfs_xz"
do_bootdirectdisk_onepart[depends] += "${MLPREFIX}grub-efi:do_deploy"

inherit boot-directdisk-onepart
#If no need for a partitioned diskimage:
#inherit image-vm

IMAGE_FSTYPES = "squashfs-xz"
IMAGE_TYPEDEP_vm = "squashfs_xz"
VM_ROOTFS_TYPE = "squashfs_xz"

CONFIGFS_FILE = "${FILE_DIRNAME}/configfs.ext4.gz"
ROOTFS = "${IMGDEPLOYDIR}/${IMAGE_LINK_NAME}.squashfs-xz"

IMAGE_INSTALL = "\
    grub-efi \
    apache2 php-cli php-fpm php-fpm-apache2 \
    squid \
    nginx \
    mdadm \
    rpm \
    acpid dmidecode usbutils \
    ffmpeg minidlna transmission transmission-client mpd alsa-utils \
    samba \
    curl xz \
    screen \
    ntp ntp-utils \
    fuse-exfat ntfs-3g-ntfsprogs exfat-utils \
    tftp-hpa tftp-hpa-server \
    smartmontools hdparm sdparm \
    strace ldd lsof iotop iperf3 tcpdump \
    wget rsync \
    lmsensors-sensors lmsensors-fancontrol lmsensors-pwmconfig lmsensors-isatools lmsensors-sensorsdetect \
    net-snmp-client net-snmp-mibs net-snmp-server net-snmp-server-snmpd net-snmp-server-snmptrapd \
    systemd-analyze \
    openvpn strongswan \
    ebtables bridge-utils cifs-utils vlan inetutils \
    cryptsetup \
    cyrus-sasl-bin \
    lio-utils \
    python-json \
    postfix cyrus-imapd \
    bluez5 bluez5-noinst-tools bluez5-obex \
    openldap-bin openldap-slapd openldap-overlay-proxycache openldap-dev \
    openldap-backend-dnssrv openldap-backend-ldap openldap-backend-meta \
    openldap-backend-monitor openldap-backend-null openldap-backend-passwd \
    qemu libvirt libvirt-libvirtd libvirt-virsh \
    openvswitch-switch openvswitch-pki \
    kernel-modules \
    packagegroup-core-boot \
    packagegroup-core-full-cmdline \
    ${CORE_IMAGE_EXTRA_INSTALL} \
    "

export IMAGE_BASENAME = "homebox"

NOISO="1"
