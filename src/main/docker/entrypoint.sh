#!/bin/bash

set -e

# assuming the container is launched with /intellij-inspect/idea-project-ro bound to the host directory thag contains the IDEA project
# we create an overlay above /intellij-inspect/idea-project-ro so that we can modify some files in the
# container without affecting the files on the host
mount -t overlay overlay -o lowerdir=/intellij-inspect/idea-project-ro,upperdir=/intellij-inspect/idea-project-tmprw,workdir=/intellij-inspect/idea-project-overlay-workdir /intellij-inspect/idea-project-tmprw

/idea-cli-inspector/ideainspect.groovy -i /idea-IC --resultdir /intellij-inspect/target/inspection-results $*
