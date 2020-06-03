package de.monticore.types.check;

import de.monticore.lang.siunits.utility.UnitPrettyPrinter;
import de.monticore.lang.types.siunittypes4computing._ast.ASTSIUnitType4Computing;
import de.monticore.lang.types.siunittypes4computing._ast.ASTSIUnitType4ComputingInt;
import de.monticore.lang.types.siunittypes4computing._visitor.SIUnitTypes4ComputingVisitor;
import de.monticore.lang.types.siunittypes4math._ast.ASTSIUnitType4Math;
import de.se_rwth.commons.logging.Log;

import static de.monticore.types.check.TypeCheck.*;

/**
 * Visitor for Derivation of SymType from SIUnitTypes4Computing
 * i.e. for
 * types/SIUnitTypes4Computing.mc4
 */
public class SynthesizeSymTypeFromSIUnitTypes4Computing extends SynthesizeSymTypeFromMCBasicTypes
        implements SIUnitTypes4ComputingVisitor {
    /**
     * Using the visitor functionality to calculate the SymType Expression
     */

    // ----------------------------------------------------------  realThis start
    // setRealThis, getRealThis are necessary to make the visitor compositional
    //
    // (the Vistors are then composed using theRealThis Pattern)
    //
    SIUnitTypes4ComputingVisitor realThis = this;

    @Override
    public void setRealThis(SIUnitTypes4ComputingVisitor realThis) {
        this.realThis = realThis;
    }

    @Override
    public SIUnitTypes4ComputingVisitor getRealThis() {
        return realThis;
    }

    @Override
    public void endVisit(ASTSIUnitType4Math node) {
        typeCheckResult.setLast(SIUnitSymTypeExpressionFactory.createSIUnit(UnitPrettyPrinter.printUnit(node.getSIUnit()), getScope(node.getEnclosingScope())));
    }

    @Override
    public void traverse(ASTSIUnitType4Computing node) {
        traverseSIUnitType4Computing(node);
    }

    protected void traverseSIUnitType4Computing(ASTSIUnitType4ComputingInt node) {
        SymTypeExpression numericType = null;
        SymTypeExpression siunitType = null;

        node.getMCPrimitiveType().accept(getRealThis());
        if (!typeCheckResult.isPresentLast() || !isNumericType(typeCheckResult.getLast())) {
            Log.error("0xA0495");
        }
        numericType = typeCheckResult.getLast();
        typeCheckResult.reset();
        node.getSIUnitType4Math().accept(getRealThis());
        if (!typeCheckResult.isPresentLast()){
            Log.error("0xA0496");
        }
        siunitType = typeCheckResult.getLast();
        typeCheckResult.setLast(SIUnitSymTypeExpressionFactory.createNumericWithSIUnitType(numericType, siunitType, getScope(node.getEnclosingScope())));
    }

    /**
     * test if the expression is of numeric type (double, float, long, int, char, short, byte)
     */
    private boolean isNumericType(SymTypeExpression type) {
        return (isDouble(type) || isFloat(type) ||
                isLong(type) || isInt(type) ||
                isChar(type) || isShort(type) ||
                isByte(type));
    }
}