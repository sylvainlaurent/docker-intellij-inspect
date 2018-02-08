#!/bin/bash

set -e

# assuming the container is launched with /home/ijinspector/idea-project bound to the host directory that contains the IDEA project
# we create an overlay above /home/ijinspector/idea-project so that we can modify some files in the
# container without affecting the files on the host
echo "Trying to create an overlay /home/ijinspector/idea-project-tmprw above /home/ijinspector/idea-project"
MOUNT_FAILED=0
mount -t overlay overlay -o lowerdir=/home/ijinspector/idea-project,upperdir=/home/ijinspector/idea-project-tmprw,workdir=/home/ijinspector/idea-project-overlay-workdir /home/ijinspector/idea-project-tmprw \
   || MOUNT_FAILED=1

if [ $MOUNT_FAILED = 1 ]; then
    echo "Mount failed, the container is probably not run as --privileged, reverting to symbolic link"
    cd /home/ijinspector \
    && rm -rf /home/ijinspector/idea-project-tmprw \
    && ln -s /home/ijinspector/idea-project /home/ijinspector/idea-project-tmprw
fi

#set working dir and change user to drop privileges
cd /home/ijinspector/idea-project-tmprw \
  && gosu ijinspector /home/ijinspector/groovy/bin/groovy /home/ijinspector/idea-cli-inspector/ideainspect.groovy -i /home/ijinspector/idea-IC --resultdir /home/ijinspector/intellij-inspect/target/inspection-results $*
