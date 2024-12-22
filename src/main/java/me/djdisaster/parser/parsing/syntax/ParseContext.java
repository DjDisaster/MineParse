package me.djdisaster.parser.parsing.syntax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParseContext {

	public ParseContext() {}



	private List<SimpleSyntax> fileSyntaxes = new ArrayList<>();
	private List<SimpleSyntax> sectionSyntaxes = new ArrayList<>();
	public List<SimpleSyntax> getSyntaxes() {
		List<SimpleSyntax> copy = new ArrayList<>(fileSyntaxes);
		copy.addAll(sectionSyntaxes);
		return copy;
	}

	public void addFileSyntax(List<SimpleSyntax> syntax) {
		fileSyntaxes.addAll(syntax);
	}

	public void addSectionSyntax(List<SimpleSyntax> syntax) {
		sectionSyntaxes.addAll(syntax);
	}

	public void clearFileSyntax() {
		fileSyntaxes.clear();
	}

	public void clearSectionSyntax() {
		sectionSyntaxes.clear();
	}






	private List<SimpleExpression> sectionExpressions = new ArrayList<>();
	private List<SimpleExpression> fileExpressions = new ArrayList<>();
	public List<SimpleExpression> getExpressions() {
		List<SimpleExpression> copy = new ArrayList<>(fileExpressions);
		copy.addAll(sectionExpressions);
		return copy;
	}

	public void addFileExpression(List<SimpleExpression> syntax) {
		fileExpressions.addAll(syntax);
	}

	public void addSectionExpression(List<SimpleExpression> syntax) {
		sectionExpressions.addAll(syntax);
	}

	public void clearFileExpressions() {
		fileExpressions.clear();
	}

	public void clearSectionExpressions() {
		sectionExpressions.clear();
	}

}
