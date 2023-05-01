
# makefile for Compiling Project source code

# Main class path
DES=/home/minh/School/Learning/20222/OOP/ProjectOOP20222/Class
# Used class path
USED=/home/minh/snap/java/lib/jsoup-1.15.4.jar
# class path
CLASS_PATH=$(DES):$(USED)
# classes
CLASS=data.crawl.Crawler


all: compile

compile:
	javac @Configure/option_compile.sh @Configure/source_file.sh

run:
	java -cp $(CLASS_PATH) $(CLASS)