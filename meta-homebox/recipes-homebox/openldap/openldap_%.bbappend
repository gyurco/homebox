
PACKAGECONFIG ??= "gnutls modules \
                   ldap meta monitor null passwd shell proxycache dnssrv bdb hdb \
"

do_configure() {
  cp ${STAGING_DATADIR_NATIVE}/libtool/build-aux/ltmain.sh ${S}/build
  rm -f ${S}/libtool
  aclocal
  libtoolize --force --copy
  gnu-configize
  autoconf
  LIBS=-ldb oe_runconf
}
