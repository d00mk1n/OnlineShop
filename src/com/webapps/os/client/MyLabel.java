package com.webapps.os.client;

import com.google.gwt.user.client.ui.Label;

public class MyLabel extends Label {

	MyLabel (String text, int lenght, int width) {
		this.setText(text);
		this.setPixelSize(lenght, width);
	}
	
	MyLabel (String text) {
		this.setText(text);
		this.setPixelSize(100, 15);
	}
}
