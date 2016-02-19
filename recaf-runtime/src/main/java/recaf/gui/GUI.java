package recaf.gui;

import recaf.core.cps.EvalJavaStmt;
import recaf.core.cps.ED;
import recaf.core.cps.SD;

public abstract class GUI extends EvalJavaStmt<Void> {
	private int idCount = 0;
	
	protected String nextId() {
		return "id" + idCount++;
	}
	
	public Void Method(SD<Void> body) {
		return typePreserving(body);
	}
	
	public abstract SD<Void> Tag(ED<String> t, SD<Void> body);

	public abstract SD<Void> Button(ED<String> label, SD<Void> body);

	public abstract SD<Void> Echo(ED<String> exp);	
}
