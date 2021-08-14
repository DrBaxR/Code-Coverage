package de.syngenio.demo7;

public class Delegator {

	private Delegate delegate;

	public Delegator(Delegate delegate) {
		this.delegate = delegate;
	}
	
	public void doSomethingComplex() {
		delegate.d();
		delegate.c();
		delegate.b();
		delegate.a();
	}
}
