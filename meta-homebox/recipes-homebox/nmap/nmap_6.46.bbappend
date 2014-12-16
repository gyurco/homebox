RDEPENDS_${PN} += "python"

do_install_append() {
    sed -i 's:bin/python:usr/bin/python:' ${D}${bindir}/ndiff
}
