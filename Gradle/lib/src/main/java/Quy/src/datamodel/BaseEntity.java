package Quy.src.datamodel;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;

import Quy.src.constant.Constant;

public class BaseEntity {

	protected String id;
	protected String name;
	protected String description;
	protected Map<String, String> additionalInfo;
	protected List<String> relatedEntityIds;
	protected String rootURL;

	public BaseEntity() {
		this.id = UUID.randomUUID().toString();
		this.additionalInfo = new HashMap<>();
		this.relatedEntityIds = new LinkedList<>();
		this.description = "Chưa có thông tin";
	}
	public BaseEntity(String name, Map<String, String> additionalInfo) {
		this();

		this.name = name;
		this.additionalInfo= additionalInfo;
	}
	public BaseEntity(String name, Map<String, String> additionalInfo, String description ) {
		this();

		this.description = description;
		this.name = name;
		this.additionalInfo= additionalInfo;
	}

	public JSONObject toJSONObject() {
		JSONObject jEntity = new JSONObject();
		jEntity.put(Constant.ENTITY_ID, this.getId());
		jEntity.put(Constant.ENTITY_NAME, this.getName());
		jEntity.put(Constant.ENTITY_DESCRIPTION, this.getDescription());
		jEntity.put(Constant.ENTITY_ADDITIONAL_INFO, this.getAdditionalInfo());
		jEntity.put(Constant.ENTITY_RELATED_ENTITY_IDS, this.getRelatedEntityIds());
		jEntity.put(Constant.ENTITY_TYPE, this.getType());
		jEntity.put(Constant.ENTITY_ROOT_URL, this.getRootURL());
		return jEntity;
	}
	public boolean isContainInEntity(String text)
	{
		return this.getName().contains(text) || this.getAdditionalInfo().values().toString().contains(text) || this.getDescription().contains(text);
	}

	public void addRelatedEntity(String entityId) {
		if(!this.relatedEntityIds.contains(entityId))
			this.relatedEntityIds.add(entityId);
	}

	//getter and setter
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Map<String, String> getAdditionalInfo() {
		return additionalInfo;
	}
	public void setAdditionalInfo(Map<String, String> additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	public List<String> getRelatedEntityIds() {
		return relatedEntityIds;
	}
	public void setRelatedEntityIds(List<String> relatedEntityIds) {
		this.relatedEntityIds = relatedEntityIds;
	}
	public String getRootURL() {
		return rootURL;
	}
	public void setRootURL(String rootURL) {
		this.rootURL = rootURL;
	}
	public String getType() {
		return Constant.BASE_ENTITY;
	}
	public String getDesJsonPath()
	{
		return Constant.JSON_PATH_DES;
	}
}

