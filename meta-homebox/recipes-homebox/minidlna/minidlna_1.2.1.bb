SRCREV = "v1_2_1"
LIC_FILES_CHKSUM = "file://LICENCE.miniupnpd;md5=b0dabf9d8e0f871554e309d62ead8d2b"

DESCRIPTION = "MiniDLNA (aka ReadyDLNA) is server software with the aim of \
being fully compliant with DLNA/UPnP-AV clients."
LICENSE = "GPL-2.0|BSD"
DEPENDS = "ffmpeg flac libav jpeg sqlite3 libexif libogg libid3tag libvorbis"

# because it depends on libav which has commercial flag
LICENSE_FLAGS = "commercial"

inherit gettext autotools-brokensep update-rc.d systemd

SRC_URI = "git://git.code.sf.net/p/minidlna/git;branch=master;module=git \
           file://minidlna-daemon.init.d \
           file://minidlna-systemd.service "

S = "${WORKDIR}/git"

# This remove "--exclude=autopoint" option from autoreconf argument to avoid
# configure.ac:30: error: required file './ABOUT-NLS' not found
EXTRA_AUTORECONF = ""

do:configure:prepend() {
    sed -i 's/0.18/0.19/' configure.ac
}

do:install:append(){
    install -d ${D}${sysconfdir}
    install -m 0755 minidlna.conf ${D}${sysconfdir}

# Systemd script
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/minidlna-systemd.service ${D}${systemd_unitdir}/system/minidlna.service

# Sysvinit script
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/minidlna-daemon.init.d ${D}${sysconfdir}/init.d/minidlna
}

SYSTEMD_SERVICE:${PN} = "minidlna.service"

INITSCRIPT_NAME = "minidlna"
INITSCRIPT_PARAMS = "defaults 90"
