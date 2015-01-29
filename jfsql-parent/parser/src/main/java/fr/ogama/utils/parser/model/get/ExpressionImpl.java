package fr.ogama.utils.parser.model.get;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import fr.ogama.utils.parser.JFSQLExecutionException;

public class ExpressionImpl implements Expression {

	String operator = null;
	Vector<Expression> operands = null;

	public ExpressionImpl(String operator) {
		this.operator = new String(operator);
	}

	
	public ExpressionImpl(String op, Expression o1) {
		operator = new String(op);
		addOperand(o1);
	}

	public ExpressionImpl(String operator, Expression o1, Expression o2) {
		this.operator = new String(operator);
		addOperand(o1);
		addOperand(o2);
	}

	/**
	 * Get this expression's operator.
	 * 
	 * @return the operator.
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * Set the operands list
	 * 
	 * @param v
	 *            A vector that contains all operands (ZExp objects).
	 */
	public void setOperands(Vector<Expression> v) {
		operands = v;
	}

	/**
	 * Get this expression's operands.
	 * 
	 * @return the operands (as a Vector of ZExp objects).
	 */
	public Vector<Expression> getOperands() {
		return operands;
	}

	/**
	 * Add an operand to the current expression.
	 * 
	 * @param o
	 *            The operand to add.
	 */
	public void addOperand(Expression o) {
		if (operands == null)
			operands = new Vector<Expression>();
		operands.addElement(o);
	}

	/**
	 * Get an operand according to its index (position).
	 * 
	 * @param pos
	 *            The operand index, starting at 0.
	 * @return The operand at the specified index, null if out of bounds.
	 */
	public Expression getOperand(int pos) {
		if (operands == null || pos >= operands.size())
			return null;
		return (Expression) operands.elementAt(pos);
	}

	/**
	 * Get the number of operands
	 * 
	 * @return The number of operands
	 */
	public int nbOperands() {
		if (operands == null)
			return 0;
		return operands.size();
	}


	public List<Comparable> execute(Map<String, Comparable> params) throws JFSQLExecutionException {
		throw new RuntimeException("Unimplemented");
	}
}
