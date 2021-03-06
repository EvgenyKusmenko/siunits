/* (c) https://github.com/MontiCore/monticore */
package de.monticore.siunits.prettyprint;

import de.monticore.prettyprint.IndentPrinter;
import de.monticore.siunits._ast.*;
import de.monticore.siunits._visitor.SIUnitsVisitor;

public class SIUnitsPrettyPrinter implements SIUnitsVisitor {

    private SIUnitsVisitor realThis = this;

    // printer to use
    protected IndentPrinter printer;

    /**
     * Constructor
     * @param printer
     */
    public SIUnitsPrettyPrinter(IndentPrinter printer) {
        this.printer = printer;
    }

    /**
     * @return the printer
     */
    public IndentPrinter getPrinter() {
        return printer;
    }


    @Override
    public void traverse(ASTSIUnit node) {
        if (node.isPresentOne())
            printer.print("1");
        else if (node.isPresentNumerator())
            node.getNumerator().accept(getRealThis());
        else if (node.isPresentSIUnitPrimitive())
            node.getSIUnitPrimitive().accept(getRealThis());
        if (node.isPresentDenominator()) {
            printer.print("/");
            node.getDenominator().accept(getRealThis());
        }
    }

    @Override
    public void visit(ASTSIUnitWithPrefix node) {
        if (node.isPresentName())
            printer.print(node.getName());
        else if (node.isPresentNonNameUnit())
            printer.print(node.getNonNameUnit());
    }


    @Override
    public void visit(ASTSIUnitWithoutPrefix node) {
        if (node.isPresentName())
            printer.print(node.getName());
        else if (node.isPresentNonNameUnit())
            printer.print(node.getNonNameUnit());
    }

    @Override
    public void traverse(ASTSIUnitKindGroupWithExponent node) {
        int j = 0;
        for (int i = 0; i < node.getSIUnitGroupPrimitiveList().size(); i++) {
            node.getSIUnitGroupPrimitive(i).accept(getRealThis());
            if (j < node.getExponentList().size())
                printer.print("^" + node.getExponent(j++).getSource());
        }
    }
    /**
     * Prints a SiUnitDimensionless
     * @param node SiUnitDimensionless
     */
    @Override
    public void visit(ASTSIUnitDimensionless node) {
        if (node.isPresentUnit())
            printer.print(node.getUnit());
        else
            printer.print("°");
    }

    /**
     * Prints a Celsius or Fahrenheit Degree
     * @param node CelciusFahrenheit
     */
    @Override
    public void visit(ASTCelsiusFahrenheit node) {
        printer.print("°" + node.getUnit());
    }


    /**
     * This method prettyprints a given node from SIUnit grammar.
     *
     * @param node A node from SIUnit grammar.
     * @return String representation.
     */
    public static String prettyprint(ASTSIUnitsNode node) {
        SIUnitsPrettyPrinter pp = new SIUnitsPrettyPrinter(new IndentPrinter());
        node.accept(pp);
        return pp.getPrinter().getContent();
    }

    /**
     * @see SIUnitsVisitor#setRealThis(SIUnitsVisitor)
     */
    @Override
    public void setRealThis(SIUnitsVisitor realThis) {
        this.realThis = realThis;
    }

    /**
     * @see SIUnitsVisitor#getRealThis()
     */
    @Override
    public SIUnitsVisitor getRealThis() {
        return realThis;
    }
}
