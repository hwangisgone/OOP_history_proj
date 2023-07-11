package main;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.jsoup.nodes.Document;

import com.fasterxml.jackson.databind.ObjectMapper;

import crawldata.URLMaker;
import crawldata.wikibasis.WikiPage;

public class Multithreader {
	private ExecutorService executorService;

	public Multithreader() {
	}

	public void shutdown() {
        // Shutdown the ExecutorService
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(1, TimeUnit.MINUTES)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }

        System.out.println("Multithreading Ended.");
	}


    public <T, R> List<R> start(List<T> inputs, Function<T,R> task) {
		executorService = Executors.newFixedThreadPool(10);
    	System.out.println("Multithreading Started.");

    	List<R> responses = new ArrayList<>();

    	// Create a list to store Future objects representing the responses
        List<Future<R>> futures = new ArrayList<>();

        // Send GET requests concurrently
        for (T input : inputs) {
            Future<R> future = executorService.submit(() -> task.apply(input));
            futures.add(future);
        }

        // Process the responses when they become available
        for (Future<R> future : futures) {
            try {
            	R response = future.get();
            	if (response != null) {
            		responses.add(response);
            	}
			} catch (InterruptedException e) {
				System.err.println("Multithreading interrupted.");
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

        shutdown();

    	return responses;
    }
    
    public List<WikiPage> getDocumentsForPages(HttpClient client, List<WikiPage> manyPages) {
		executorService = Executors.newFixedThreadPool(10);
    	System.out.println("Multithreading Started.");

    	List<WikiPage> filtered = new ArrayList<>();     
     // Create a list to store Future objects representing the responses
        List<Future<HttpResponse<String>>> futures = new ArrayList<>();

        // Send GET requests concurrently
        for (WikiPage page : manyPages) {
            // Submit the request to the ExecutorService and store the Future object
            Future<HttpResponse<String>> future = executorService.submit(() -> {
	            HttpResponse<String> response = client.send(
	            		HttpRequest.newBuilder()
	                    .uri(URI.create(page.getUrl()))
	                    .GET().build(),
	                    HttpResponse.BodyHandlers.ofString()
	            );

	            return response;
            });

            futures.add(future);
        }

        int i = 0;
        // Process the responses when they become available
        for (Future<HttpResponse<String>> future : futures) {
            // Retrieve the response from the Future object
            HttpResponse<String> response;

			try {
				response = future.get();
	            // Process the response as needed
				System.out.println(manyPages.get(i).getTitle());
	            System.out.println("Response body: " + response.body());
	            
				if (manyPages.get(i).acceptResponse(response)) {
					filtered.add(manyPages.get(i));
				}

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
        }

        shutdown();
        
        return filtered;
    }

    public List<HttpResponse<String>> getResponseFromPages(HttpClient client, List<String> manyUrls) {
		executorService = Executors.newFixedThreadPool(10);
    	System.out.println("Multithreading Started.");

    	List<HttpResponse<String>> responses = new ArrayList<>();

    	// Create a list to store Future objects representing the responses
        List<Future<HttpResponse<String>>> futures = new ArrayList<>();

        // Send GET requests concurrently
        for (String url : manyUrls) {
            // Submit the request to the ExecutorService and store the Future object
            Future<HttpResponse<String>> future = executorService.submit(() -> {
	            HttpResponse<String> response = client.send(
	            		HttpRequest.newBuilder()
	                    .uri(URI.create(url))
	                    .GET().build(),
	                    HttpResponse.BodyHandlers.ofString()
	            );

	            return response;
            });

            futures.add(future);
        }

        int i = 0;
        // Process the responses when they become available
        for (Future<HttpResponse<String>> future : futures) {
            // Retrieve the response from the Future object
            HttpResponse<String> response;

			try {
				response = future.get();
	            // Process the response as needed
				System.out.println(manyUrls.get(i));
				i++;
	            System.out.println("Response code: " + response.statusCode());
	            System.out.println("Response body: " + response.body());
				responses.add(response);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

        shutdown();

    	return responses;
    }

	public static void main(String[] args) {
        // Define the URLs for the GET requests

        List<String> titles = new ArrayList<>();
        titles.add("Thể loại:Nhà Đinh");
        titles.add("Thể loại:Nhà Triệu");
        titles.add("Thể loại:Nhà Nguyễn");
        titles.add("Thể loại:Nhà Lê trung hưng");

        Multithreader multithreader = new Multithreader();
        HttpClient client = HttpClient.newHttpClient();
        multithreader.getResponseFromPages(client, URLMaker.getPageQueries(titles));
	}

}
