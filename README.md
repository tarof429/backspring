backspring
==========

# Description

A Java-based backup tool. The name comes from using Spring for DI.

# Installation

- Unpack the backspring core to your home directory (no GUI support yet)

  $ mkdir ~/backspring
  $ tar xzf backspring-core/target/backspring-core-<version>-dist.tar.gz -C ~/backspring

- For Ubuntu, a script has been provided in the backspring/misc directory to install backsrping as a service.

  $ cd ~/backspring
  $ chmod +x misc/*
  $ sudo misc/install
  $ sudo service backspring start

  Run sudo service backspring to see all options.

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
