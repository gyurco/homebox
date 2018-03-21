DESCRIPTION = "Homebox"

inherit core-image

DEPENDS += "squashfs-tools-native"

IMAGE_FEATURES += "package-management ssh-server-openssh"

AUTO_SYSLINUXCFG = "1"
INITRD_IMAGE_VM ?= "core-image-minimal-initramfs"
SYSLINUX_ROOT = "root=/dev/ram0"
SYSLINUX_TIMEOUT ?= "10"
SYSLINUX_LABELS = "boot"
BOOTDD_EXTRA_SPACE = "262144"

inherit boot-directdisk-onepart
#If no need for a partitioned diskimage:
#inherit image-vm

IMAGE_FSTYPES = "squashfs-xz"
IMAGE_TYPEDEP_vm = "squashfs_xz"
VM_ROOTFS_TYPE = "squashfs_xz"
LIVE_ROOTFS_TYPE = "squashfs_xz"

CONFIGFS_FILE = "${FILE_DIRNAME}/configfs.ext4.gz"
ROOTFS = "${IMGDEPLOYDIR}/${IMAGE_LINK_NAME}.squashfs-xz"

ROOTFS_POSTPROCESS_COMMAND += " disable_autostarts; default_network; "

disable_autostarts () {
    rm -f ${IMAGE_ROOTFS}/etc/systemd/system/multi-user.target.wants/cyrus-imapd.service;
    rm -f ${IMAGE_ROOTFS}/etc/systemd/system/multi-user.target.wants/libvirtd.service;
    rm -f ${IMAGE_ROOTFS}/etc/systemd/system/multi-user.target.wants/libvirt-guests.service;
    rm -f ${IMAGE_ROOTFS}/etc/systemd/system/multi-user.target.wants/minidlna.service;
    rm -f ${IMAGE_ROOTFS}/etc/systemd/system/multi-user.target.wants/nfs-mountd.service;
    rm -f ${IMAGE_ROOTFS}/etc/systemd/system/multi-user.target.wants/nfs-server.service;
    rm -f ${IMAGE_ROOTFS}/etc/systemd/system/multi-user.target.wants/nfs-statd.service;
    rm -f ${IMAGE_ROOTFS}/etc/systemd/system/multi-user.target.wants/nginx.service;
    rm -f ${IMAGE_ROOTFS}/etc/systemd/system/multi-user.target.wants/nmb.service;
    rm -f ${IMAGE_ROOTFS}/etc/systemd/system/multi-user.target.wants/smb.service;
    rm -f ${IMAGE_ROOTFS}/etc/systemd/system/multi-user.target.wants/openvswitch.service;
    rm -f ${IMAGE_ROOTFS}/etc/systemd/system/multi-user.target.wants/strongswan.service;
    rm -f ${IMAGE_ROOTFS}/etc/systemd/system/multi-user.target.wants/transmission-daemon.service;
    rm -f ${IMAGE_ROOTFS}/etc/systemd/system/multi-user.target.wants/xinetd.service;
    rm -f ${IMAGE_ROOTFS}/etc/systemd/system/sysinit.target.wants/systemd-timesyncd.service;
}

default_network () {
    echo "[Match]\nName=*\n\n[Network]\nDHCP=ipv4\n" > ${IMAGE_ROOTFS}/etc/systemd/network/01-default.network
}

IMAGE_INSTALL = "\
    grub-efi \
    apache2 php-cli php-fpm php-fpm-apache2 \
    v86d \
    squid \
    nginx \
    mariadb-server postgresql \
    mdadm \
    rpm \
    acpid dmidecode usbutils \
    ffmpeg minidlna transmission transmission-client mpd alsa-utils \
    samba samba-ad-dc winbind krb5-user python-unixadmin python-difflib python-threading \
    curl xz \
    screen \
    ntp ntp-utils \
    fuse-exfat ntfs-3g-ntfsprogs exfat-utils \
    e2fsprogs e2fsprogs-mke2fs e2fsprogs-tune2fs e2fsprogs-resize2fs xfsprogs btrfs-tools \
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
    qemu libvirt libvirt-libvirtd libvirt-virsh lxc \
    openvswitch-switch openvswitch-pki \
    kernel-modules \
    packagegroup-core-boot \
    packagegroup-core-full-cmdline \
    ${CORE_IMAGE_EXTRA_INSTALL} \
    "

export IMAGE_BASENAME = "homebox"

NOISO="1"
