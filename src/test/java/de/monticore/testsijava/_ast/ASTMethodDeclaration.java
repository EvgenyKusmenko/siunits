package de.monticore.testsijava._ast;

public class ASTMethodDeclaration extends ASTMethodDeclarationTOP {

    public String getName() {
        return getMethodSignature().getName();
    }
}
