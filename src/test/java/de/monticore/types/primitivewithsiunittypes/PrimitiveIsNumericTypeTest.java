package de.monticore.types.primitivewithsiunittypes;

import de.monticore.testsijava.testsijava._parser.TestSIJavaParser;
import de.monticore.types.primitivewithsiunittypes._ast.ASTPrimitiveWithSIUnitType;
import de.monticore.types.primitivewithsiunittypes._ast.ASTPrimitiveWithSIUnitTypesNode;
import de.monticore.types.primitivewithsiunittypes._cocos.PrimitiveIsNumericType;
import de.monticore.types.primitivewithsiunittypes._cocos.PrimitiveWithSIUnitTypesCoCoChecker;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.LogStub;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.Optional;

public class PrimitiveIsNumericTypeTest {

    @Before
    public void init() {
        LogStub.init();
        Log.enableFailQuick(false);
    }

    @Test
    public void testPositive() throws IOException {
        String[] toTest = {
                "<byte m>",
                "<short m>",
                "<int m>",
                "<long m>",
                "<char m>",
                "<float m>",
                "<double m>"
        };
        for (String s : toTest)
            test(s, false);
    }

    @Test
    public void testNegative() throws IOException {
        test("<boolean m>", true);
    }

    private void test(String s, boolean errorExpected) throws IOException {
        ASTPrimitiveWithSIUnitType ast = parse(s);
        PrimitiveWithSIUnitTypesCoCoChecker checker = new PrimitiveWithSIUnitTypesCoCoChecker();
        checker.addCoCo(new PrimitiveIsNumericType());
        checker.checkAll((ASTPrimitiveWithSIUnitTypesNode) ast);

        if (errorExpected)
            assert (Log.getFindings().size() > 0);
        else
            assert (Log.getFindings().size() == 0);
    }

    private ASTPrimitiveWithSIUnitType parse(String s) throws IOException {
        TestSIJavaParser parser = new TestSIJavaParser();
        Optional<ASTPrimitiveWithSIUnitType> ast = parser.parsePrimitiveWithSIUnitType(new StringReader(s));
        assert (ast.isPresent());
        return ast.get();
    }
}
