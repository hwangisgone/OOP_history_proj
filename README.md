
# OOP History Project


## Outline Modules

1. Data crawling

	> This is crawling data module, which searchs related link and extract data

2. Database

	> This is database: include database interface and Json implementation of database interface

3. Entity

	> This is module to modelize the object in database into Java class


## Working Process

> For better understand the module, see the UML model in Specification directory

> Current work: Only tested on Historical character from wikipedia website. Working on loading data from database and modelize the data into java objects for historical character.

- Working on searching and extract Vietnamese Historical characters from wikipedia websites

- The attributes of Historical character is declared at [file](./Data/DataConfigure/attributeHistoricalCharacter.json)

- The data extracted is stored in [Data directory](./Data/Database/historical-character/)

- Creating database interface, the design is [database interface](./Source/data/database/)


## Running Instruction

- All classes are stored in Class directory, to run or use any class, it's neccessary to include the below path in `-classpath` option:
	
	1. Class directory: where the classes stored
	2. Jsoup library
	3. Json library 


- Example
	
	> `$ java -classpath [...]/Class:[...]/jsoup-1.15.4.jar data.DataGround`

