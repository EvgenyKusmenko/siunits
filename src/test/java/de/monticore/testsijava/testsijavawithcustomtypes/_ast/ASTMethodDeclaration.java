package de.monticore.testsijava.testsijavawithcustomtypes._ast;


public class ASTMethodDeclaration extends ASTMethodDeclarationTOP {

    public String getName() {
        return getMethodSignature().getName();
    }
}
