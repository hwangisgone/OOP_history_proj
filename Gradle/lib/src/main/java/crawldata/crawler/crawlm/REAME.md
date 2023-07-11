
# Historical Character module

## Running instruction
- step 1: set up
	- go to Gradle/lib folder
	- In build.gradle file, specify the `mainClass` to run the class that you want, example:
		
		`mainClass = 'Source.data.DataGround'`


- step 2: build classes
	- at Gradle/lib folder
	- Using command `gradle build` : to compile the java files

- step 3: run class
	- at Gradle/lib folder
	- using command `gradle run`: to run the class, which specified by variable `mainClass`

## Demo functionalities

1. Crawling data
- Using class Source.data.DataGround to crawl the character data, and using static method: crawl with specific arguments:
	1. no: a string, represent file name
	2. urlSeed: url link, where to start searching
	3. size: the number of links to search
- Stored location
	1. list of urls is stored in relative path: `src/main/resources/character_crawl/url/historical-character/`
	2. fild json of crawled data is stored at: `src/main/resources/character_crawl/historical-character/`

2. Demonstrate loading and modeling character data
- At class Source.Ground, using static method `testLoad` with specific json path of dataset:
	`Ground.testLoad("src/main/resources/character_crawl/historical-character/hc#2.json")`

- The Ground class will print out list of character entities in the dataset

- To know how Ground get data, see `src/main/java/Source/Ground.java`

- To know how about attributes of Character class, see `src/main/java/entity/Character.java`


## Main Dataset
- The main dataset stored at `src/main/resources/character_data.json`