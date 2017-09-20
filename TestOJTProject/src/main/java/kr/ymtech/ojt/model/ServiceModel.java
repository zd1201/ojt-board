package kr.ymtech.ojt.model;
import open.commons.json.annotation.JSONField;
import open.commons.json.model.DefaultJSONModel;

/**
  * <a href="http://tools.ietf.org/html/rfc7159">JSON</a> source: <br>
  * <pre>
  * 
  * [CASE - 0]
  * 
  * {
  *   "id": "service-id-1",
  *   "name": "service-name-1",
  *   "imgIcon": "utm_img.png"
  * }
  * </pre>
  *
  * @since 2017. 04. 26.
 */
public  class ServiceModel extends DefaultJSONModel{

	private static final long serialVersionUID = 1L;

	@JSONField(name="id")
	private String id;

	@JSONField(name="imgIcon")
	private String imgIcon;

	@JSONField(name="name")
	private String name;
	
	@JSONField(name="descr")
	private String descr;
	
	@JSONField(name="resource")
	private String resource;
	
	public ServiceModel (){}
	
	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	/**
	 *
	 * @param id id to set.
	 *
	 * @since 2017. 04. 26.
	 */
	public void setId (String id) {
		this.id = id;
	}

	/**
	 *
	 * @param imgIcon imgIcon to set.
	 *
	 * @since 2017. 04. 26.
	 */
	public void setImgIcon (String imgIcon) {
		this.imgIcon = imgIcon;
	}

	/**
	 *
	 * @param name name to set.
	 *
	 * @since 2017. 04. 26.
	 */
	public void setName (String name) {
		this.name = name;
	}

	/**
	 *
	 * @return id
	 *
	 * @since 2017. 04. 26.
	 */
	public String getId () {
		return this.id;
	}

	/**
	 *
	 * @return imgIcon
	 *
	 * @since 2017. 04. 26.
	 */
	public String getImgIcon () {
		return this.imgIcon;
	}

	/**
	 *
	 * @return name
	 *
	 * @since 2017. 04. 26.
	 */
	public String getName () {
		return this.name;
	}


}