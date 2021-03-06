/* (c) https://github.com/MontiCore/monticore */
package de.monticore.types.check;

import de.monticore.expressions.combineexpressionswithsiunitliterals._parser.CombineExpressionsWithSIUnitLiteralsParser;
import de.monticore.expressions.expressionsbasis._ast.ASTExpression;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.LogStub;
import org.junit.Before;
import org.junit.BeforeClass;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.*;

public abstract class DeriveSymTypeAbstractTest {

    @BeforeClass
    public static void setup() {
        LogStub.init();         // replace log by a sideffect free variant
        // LogStub.initPlusLog();  // for manual testing purpose only
        Log.enableFailQuick(false);
    }

    @Before
    public void setupForEach() {
        LogStub.init();         // replace log by a sideffect free variant
    }

    private TypeCheck tc;

    protected void setTypeCheck(TypeCheck tc) {
        this.tc = tc;
    }

    protected abstract void setupTypeCheck();


    // Parser used for convenience:
    // (may be any other Parser that understands CommonExpressions)
    private CombineExpressionsWithSIUnitLiteralsParser p = new CombineExpressionsWithSIUnitLiteralsParser();

    protected ASTExpression parseExpression(String expression) throws IOException {
        Optional<ASTExpression> astExpression = p.parse_StringExpression(expression);
        assertTrue(astExpression.isPresent());
        return astExpression.get();
    }

    private FlatExpressionScopeSetterAbs flatExpressionScopeSetter;

    protected void setFlatExpressionScopeSetter(FlatExpressionScopeSetterAbs flatExpressionScopeSetter) {
        this.flatExpressionScopeSetter = flatExpressionScopeSetter;
    }

    protected void check(String expression, String expectedType) throws IOException {
        setupTypeCheck();
        ASTExpression astex = parseExpression(expression);
        if (flatExpressionScopeSetter != null)
            astex.accept(flatExpressionScopeSetter);

        assertEquals(expectedType, tc.typeOf(astex).print());
    }

    protected void checkError(String expression, String expectedError) throws IOException {
        setupTypeCheck();
        ASTExpression astex = parseExpression(expression);
        if (flatExpressionScopeSetter != null)
            astex.accept(flatExpressionScopeSetter);

        Log.getFindings().clear();
        try {
            tc.typeOf(astex);
        } catch(RuntimeException e) {
            assertEquals(expectedError, getFirstErrorCode());
            return;
        }
        fail();
    }

    private String getFirstErrorCode() {
        if (Log.getFindings().size() > 0) {
            String firstFinding = Log.getFindings().get(0).getMsg();
            return firstFinding.split(" ")[0];
        }
        return "";
    }

}
