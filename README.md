backspring
==========

# Description

A Java-based backup tool. The name comes from using Spring for DI.

# Installation

- Unpack the backspring core to your home directory (no GUI support yet)

  $ mkdir ~/backspring
  $ tar xzf backspring-core/target/backspring-core-<version>-dist.tar.gz -C ~/backspring

- To install backspring as a service on systems using System V or traditional init:

  $ cd ~/backspring
  $ chmod +x misc/*
  $ sudo misc/install
  $ sudo service backspring start

  Run sudo service backspring to see all options.

For system using systemd, see the example below:

  $ sudo cp misc/systemd/backspring.service /etc/systemd/system
  $ sudo systemctl enable backspring.service
  $ ls -l /etc/systemd/system/multi-user.target.wants/
  $ sudo systemctl daemon-reload
  $ sudo systemctl restart backspring.service
  $ sudo systemctl is-enabled backspring.service
  $ sudo systemctl status backspring.service

# Configuration

  See conf/config.yml. Restart backspring service once done.

# Running

  To run immediately:

  $ ./bin/backspring console

  To run in the background:

  $ ./bin/backspring start

  To run as a service

  $ sudo service start

# Notes

- If using rsync strategy, make sure you have read access to all files you want backed up to avoid rsync errors.

- See https://askubuntu.com/questions/676007/how-do-i-make-my-systemd-service-run-via-specific-user-and-start-on-boot