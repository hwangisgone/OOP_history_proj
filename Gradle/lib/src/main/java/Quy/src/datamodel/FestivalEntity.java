package Quy.src.datamodel;
import java.util.Map;

import Quy.src.constant.Constant;

public class FestivalEntity extends BaseEntity {

	public static String JSON_PATH_FESTIVAL = Constant.JSON_PATH_DYNASTY;

	public FestivalEntity() {
		super();
	}
	public FestivalEntity(String name, Map<String, String> additionalInfo, String description ) {
		super(name, additionalInfo, description);
	}
	public FestivalEntity(String name, Map<String, String> additionalInfo, String description, String url ) {
		super(name, additionalInfo, description);
		setRootURL(url);
	}
	@Override
	public String getType() {
		return Constant.FESTIVAL_ENTITY;
	}

	public String getDesJsonPath()
	{
		return Constant.JSON_PATH_FESTIVAL;
	}
}
