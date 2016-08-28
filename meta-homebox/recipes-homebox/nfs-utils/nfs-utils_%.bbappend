FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

do_install_append () {
	install -m 0644 ${WORKDIR}/nfs-server.service ${D}${systemd_unitdir}/system/
	install -m 0644 ${WORKDIR}/nfs-mountd.service ${D}${systemd_unitdir}/system/
	sed -i -e 's,@SBINDIR@,${sbindir},g' \
		-e 's,@SYSCONFDIR@,${sysconfdir},g' \
		${D}${systemd_unitdir}/system/*.service
}