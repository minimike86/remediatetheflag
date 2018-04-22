CREATE DATABASE IF NOT EXISTS dbRTF;
GRANT ALL ON dbRTF.* TO 'RTFGlobalDBUser'@'%';
FLUSH PRIVILEGES;
USE dbRTF;
CREATE TABLE `users` (
  `idUser` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(128) NOT NULL,
  `password` varchar(128) NOT NULL,
  `salt` varchar(128) NOT NULL,
  `email` varchar(128) NOT NULL,
  `role` int(11) NOT NULL,
  `firstName` varchar(128) NOT NULL,
  `lastName` varchar(128) NOT NULL,
  `score` int(11) NOT NULL,
  `status` varchar(255) NOT NULL,
  `countryId` int(11) DEFAULT NULL,
  `teamId` int(11) DEFAULT NULL,
  `instanceLimit` int(11) DEFAULT NULL,
  `emailVerified` bit(1) DEFAULT NULL,
  `forceChangePassword` bit(1) DEFAULT NULL,
  `joinDateTime` datetime NOT NULL,
  `defaultOrganizationId` int(11) DEFAULT NULL,
  PRIMARY KEY (`idUser`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=latin1;
LOCK TABLES `users` WRITE;
INSERT INTO `users` VALUES (1,'admin','58a23b7ea8cbd9cfd998538245818e05b583d26bc4fecb6a167d572607a91955ee3aa2bcd5f43eb838a37a30abb640b41f4670749d64d06d9cd57aa5a7bef04e','x6chJMlRu8er6ddV6GBSTQ==','admin@remediatetheflag.com',0,'rtf','admin',0,'ACTIVE',79,3,10,1,0,'2018-01-01 00:00:01',1);
UNLOCK TABLES;