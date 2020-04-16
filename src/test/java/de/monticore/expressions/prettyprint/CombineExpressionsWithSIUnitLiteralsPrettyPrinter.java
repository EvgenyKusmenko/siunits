// (c) https://github.com/MontiCore/monticore

package de.monticore.expressions.prettyprint;

import de.monticore.MCCommonLiteralsPrettyPrinter;
import de.monticore.expressions.combineexpressionswithliterals._visitor.CombineExpressionsWithLiteralsDelegatorVisitor;
import de.monticore.expressions.expressionsbasis._ast.ASTExpression;
import de.monticore.lang.literals.siunitliterals.prettyprint.SIUnitLiteralsPrettyPrinter;
import de.monticore.literals.mccommonliterals._ast.ASTSignedLiteral;
import de.monticore.literals.mcliteralsbasis._ast.ASTLiteral;
import de.monticore.prettyprint.IndentPrinter;
import de.monticore.types.check.IDerivePrettyPrinter;

public class CombineExpressionsWithSIUnitLiteralsPrettyPrinter extends CombineExpressionsWithLiteralsDelegatorVisitor implements IDerivePrettyPrinter {

  protected IndentPrinter printer;
  private CombineExpressionsWithLiteralsDelegatorVisitor realThis;

  public CombineExpressionsWithSIUnitLiteralsPrettyPrinter(IndentPrinter printer){
    this.printer = printer;
    realThis = this;

    setAssignmentExpressionsVisitor(new AssignmentExpressionsPrettyPrinter(printer));
    setCommonExpressionsVisitor(new CommonExpressionsPrettyPrinter(printer));
    setBitExpressionsVisitor(new BitExpressionsPrettyPrinter(printer));
    setExpressionsBasisVisitor(new ExpressionsBasisPrettyPrinter(printer));
    setSetExpressionsVisitor(new SetExpressionsPrettyPrinter(printer));
    setJavaClassExpressionsVisitor(new JavaClassExpressionsPrettyPrinter(printer));
    setMCCommonLiteralsVisitor(new MCCommonLiteralsPrettyPrinter(printer));
    setSIUnitLiteralsVisitor(new SIUnitLiteralsPrettyPrinter(printer));
  }

  @Override
  public String prettyprint(ASTExpression node) {
    this.printer.clearBuffer();
    node.accept(getRealThis());
    return this.printer.getContent();
  }

  @Override
  public String prettyprint(ASTLiteral node) {
    this.printer.clearBuffer();
    node.accept(getRealThis());
    return this.printer.getContent();
  }

  @Override
  public String prettyprint(ASTSignedLiteral node) {
    this.printer.clearBuffer();
    node.accept(getRealThis());
    return this.printer.getContent();
  }
}