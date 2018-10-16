package com.example.selfie.model;

public class ListingResponse_Model {
	private String id;
	private String image_url;
	private String video_url;
	private String isVideoVisible = "0";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public String getVideo_url() {
		return video_url;
	}

	public void setVideo_url(String video_url) {
		this.video_url = video_url;
	}

	public String getIsVideoVisible() {
		return isVideoVisible;
	}

	public void setIsVideoVisible(String isVideoVisible) {
		this.isVideoVisible = isVideoVisible;
	}
}