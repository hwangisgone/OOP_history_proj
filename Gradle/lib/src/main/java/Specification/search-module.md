
Search service
===


> Purpose: to let user search in the database by inputting key-word


1. Problem
	
	- Context: After read from database, the result is a list of Entity, which contains list of attributes

	- Problem:

		1. There are many fields of entity can be used to seach for, example: id, name

		2. There are many algorithm for searching:
			- longest common subsequence
			- equal string

2. Solution
	
> By divide the searching process into 2 activities, each activities are represented as an interface; so that the change of search field and searching algorithms can be flexible made without changing the searcher

- The 2 activities are
	
	1. SearchFieldGetter.getSearchField(Entity): return a string
		- SearchFieldGetter is an interface to get the search field from given Entity

	2. SearchStrategy.search()
		- SearchStrategy is an interface perform searching 

- ![search-module-image](./images/search-module.png)