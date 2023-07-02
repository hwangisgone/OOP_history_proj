
# Package


## Package Organization

> The problem will be divided into 3 packages base on their activities

1. data
	> Provide classes/interfaces related to data
	
	1. data.crawl
		- Used for crawling data from the web-link
		1. data.crawl.character
			- to crawl data for historical vietnamese character

	2. data.database
		- providing method to store/load data from dataset(such as json file)

	3. entity
		- modeling the entities crawled
	

2. exception
	> Provide exception to be handle (avoid the program crashing)
	
	1. exception.data
		- provide exception-classes for data 

3. service
	> Provides services for user
	
	1. service.search
		- searching for entities by name/id/...