
# Algorithms


1. Search link
> This is algorithm for searching links given a seed url

- Modeling the links data as a tree: the seed url is the root, all links that are available on the seed url page is its children

- To travel all the links that are available on the seed url page or its successors, we travel by level from 0 (the root) to higher levels(successors). The algorithm has some constraints:

	> Allow to specify how many level from level 0 for searching

	> Breadth-first then go deeper

	> Prevent duplicated links

## ALgorithms descriptions: In order to allow breadth-first, using a queue to store the links
	
	```
	root = urlSeed
	ENQUEUE(root)
	WHILE queue is not empty
		e = DEQUEUE
		PROCESS(e)
		IF goesNext is true:	
			ENQUEUE(e.listChildren)
	```

- By using queue, breadth-first traveling is done

- Using `goesNext` condition to specify the travel levels

- To prevent dupicated links, using HashMap to stored the links in `PROCESS` procedure


*Note: to prevent the amount of links too large to search, can add one more contrains of number links found*