#!/usr/bin/env bash

sudo -s
sudo apt-get update
sudo apt-get install build-essential
sudo apt-get install vim
sudo apt-get install libpng-dev libjpeg-dev
sh-keygen -t rsa
sudo apt-get install git
curl -sL https://deb.nodesource.com/setup_0.12 | sudo bash -
sudo apt-get install nodejs
sudo npm install -g --upgrade npm
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
sudo apt-get install oracle-java8-installer
sudo apt-get install oracle-java8-set-default
sudo apt-get install maven
sudo npm install -g grunt-cli
sudo npm install -g karma-cli
sudo npm install -g bower
sudo npm install -g yo
sudo npm install -g node-sass
sudo npm install -g generator-jhipster

git clone git@github.com:leadscorp/step.git
cd step/
npm install
bower install
grunt clean build
mvn compile
mvn spring-boot:run
