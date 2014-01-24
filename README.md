backspring
==========

# Description

A Java-based backup tool

# Installation

- For Ubuntu, a script has been provided in the backspring/misc directory to install backsrping as a service.

  $ cd backspring
  $ chmod +x misc/*
  $ sudo misc/install
  $ sudo service backspring start

  Run sudo service backspring to see all options.

# Notes

- If using rsync strategy, make sure you have read access to all files you want backed up to avoid rsync errors.
