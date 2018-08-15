rem this script installed 3rd party jar files in the local repository.
call mvn install:install-file -Dfile=jasperreports-4.1.2.jar -DgroupId=org-xmlactions -DartifactId=jasperreports -Dversion=4.1.2 -Dpackaging=jar
