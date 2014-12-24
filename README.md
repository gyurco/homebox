homebox
=======

A small Linux system based on OpenEmbedded.
It is designed to be very small (the image size is less than 150 MB), 
aimed to be put on a USB drive.
Features a readonly squashfs image, and an overlay for 
storing the changes. Frequently written files are stored
in tmpfs RAM disk to minimize flash drive wearing.
Main use case: home server

Included packages are:
- systemd
- systemd-networkd (for network config)
- transmission
- minidlna
- mdadm
- lio-utils (iscsi)
- apache
- php
- cyrus-imapd
- dovecot
- postfix
- samba
- nfsd
- openvpn
- and a lot more...

Build
=====

```
. ./oe-init-build-env
bitbake homebox-image
```

Install
=======

```
dd if=tmp-glibc/deploy/images/genericx86-64/homebox-genericx86-64.hdddirect of=/dev/sdX
```
Warning! Existing data in sdX will be destroyed!
The above command will create a boot partition on the drive (replace X in sdX with an appropriate USB/hard disc). To finish the installation, one has to create a config filesystem, too, which will store the writeable part of the system.
```
mkfs -t ext4 /dev/sdXN
mount /dev/sdXN /mnt
touch /mnt/homebox
umount /dev/sdXN
```
The above commands will create an ext4 filesystem in a pre-created partition (with fdisk for example), and put an empty file, called homebox on it. The init script will know that this FS should be used for storing the configuration data.
