package org.goranjovic.scon.util.proxy;

public interface WrapperProxy {
	
	void putOriginal(Object original);
	
	Object retrieveOriginal();

}
