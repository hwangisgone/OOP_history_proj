
# OOP History Project

## Data module

> This is a branch for data crawling and processing, the outcome will be a database


## Working Process

> Still on developing phase, developers want to use classes of this branch, should follow the below instruction

> For better understand the module, see the UML model in Specification directory


## Running Instruction

- All classes are stored in Class directory, to run or use any class, it's neccessary to include the below path in `-classpath` option:
	
	1. Class directory: where the classes stored
	2. Jsoup library
	3. Json library 


- Example
	
	> `$ java -classpath [...]/Class:[...]/jsoup-1.15.4.jar data.crawl.Crawler`

## Description working sofar

- Working on searching and extract Vietnamese Historical characters from wikipedia websites

- The attributes of Historical character is declared at [file](./Data/DataConfigure/attributeHistoricalCharacter.json)

- The data extracted is stored in [Data directory](./Data/Database/historical-character/)
