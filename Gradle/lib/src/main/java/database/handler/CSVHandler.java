package database.handler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

import entity.Entity;

public class CSVHandler<T extends Entity> {
	private ObjectMapper mapper;
	private Class<T> targetClass;

	public CSVHandler(Class<T> targetClass) {
		mapper = new ObjectMapper();
		AnnotationIntrospector introspector = new JacksonAnnotationIntrospector();

        mapper.getDeserializationConfig().with(introspector);
        mapper.getSerializationConfig().with(introspector);

        this.targetClass = targetClass;
	}

	public void write(File fileJson, List<T> resultList) {
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(fileJson, resultList);
		} catch (StreamWriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabindException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<T> load(File fileJson) {
		JavaType type = mapper.getTypeFactory().
	            constructCollectionType(
	                ArrayList.class,
	                targetClass);

		List<T> resultList = new ArrayList<>();

		try {
			resultList = mapper.readValue(fileJson, type);
		} catch (StreamReadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabindException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultList;
	}
}
