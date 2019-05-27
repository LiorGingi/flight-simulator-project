package interpreter;

import java.util.Observable;
import java.util.Observer;

import commands.ConnectCommand;

public class Property extends Observable implements Observer {
	Double value;

	public Property(Double value) {
		this.value = value;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
		setChanged();
		notifyObservers();
		if (MyInterpreter.getBindingTable().isBind(this)) {
			String name = MyInterpreter.getBindingTable().getVarName(this);
			try {
				ConnectCommand.updateSimulator(name, this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		Property prop = (Property) o;
		if (value == null || !value.equals(prop.getValue()))
			this.setValue(prop.getValue());
	}

	public void bind(Property prop) {
		addObserver(prop);
	}

	public void bindBi(Property p) {
		if (this != p) {
			this.addObserver(p);
			p.addObserver(this);
		}
	}
}
