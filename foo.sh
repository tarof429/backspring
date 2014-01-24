#!/bin/sh

mvn clean install
tar xzf target/backspring-1.0.0-SNAPSHOT-distribution.tar.gz -C target
cp $HOME/backspring-1.0.0-SNAPSHOT/conf/config.yml target/backspring-1.0.0-SNAPSHOT/conf/config.yml
chmod +x target/backspring-1.0.0-SNAPSHOT/misc/*
cd target/backspring-1.0.0-SNAPSHOT
sudo sh ./misc/install
sudo service backspring console