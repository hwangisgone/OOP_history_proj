
# OOP History Project


## Outline Modules

1. Data package

	1. Data crawling

		> This is crawling data module, which searchs related link and extract data

	2. Database

		> This is database: include database interface and Json implementation of database interface

	3. Entity

		> This is module to modelize the object in database into Java class

2. Service package

	1. Search 

		> This is a module which provides search service


## Working Process

> For better understand the module, see the UML model in Specification directory

> Current work: Only tested on Historical character from wikipedia website. Working on search service

- Work done:
	
	- searching and extract Vietnamese Historical characters from wikipedia websites

	- The attributes of Historical character is declared at [file](./Data/DataConfigure/attributeHistoricalCharacter.json)

	- The data extracted is stored in [Data directory](./Data/Database/historical-character/)

	- Creating database interface, the design is [database interface](./Source/data/database/)

	- provide search service [service module](./Source/service/search/), design was stored in Specification directory. 


## Running Instruction

- All classes are stored in Class directory, to run or use any class, it's neccessary to include the below path in `-classpath` option:
	
	1. Class directory: where the classes stored
	2. Jsoup library
	3. Json library 

- The class contain main method, which project activities occur is: [Ground](./Source/Ground.java)
	

- Example
	
	> `$ java -classpath [...]/Class:[...]/jsoup-1.15.4.jar Ground`

