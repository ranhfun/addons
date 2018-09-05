package org.example.soup.icepush.pages;

import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.icepush.notify.GroupNotifier;

@Import(library = "register.js")
public class Register {

	@Persist
	@Property
	private GroupNotifier windowNotifier;
	
	void onActivate() {
		if (windowNotifier==null) {
			windowNotifier = new GroupNotifier();
		}
	}

	
}
