homebox
=======

A small USB-drive based Linux based on OpenEmbedded.
It is designed to be very small, to be put on a USB drive.
Features a readonly squashfs image, and an overlay for 
storing the changes. Frequently written files are stored
in tmpfs RAM disk.
Use case: home server

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
