/* (c) https://github.com/MontiCore/monticore */

package de.monticore.lang.testsijava.testsijava.visitor;

import de.monticore.lang.testsijava.testsijava._visitor.TestSIJavaVisitor;

public class TestSIJavaBasicVisitor implements TestSIJavaVisitor {

    private TestSIJavaVisitor realThis;

    @Override
    public void setRealThis(TestSIJavaVisitor realThis) {
        this.realThis = realThis;
    }

    @Override
    public TestSIJavaVisitor getRealThis() {
        return realThis;
    }

    public TestSIJavaBasicVisitor() {
        realThis = this;
    }
}
