package me.drbaxr.codecoverage.parsers;

import org.eclipse.jdt.core.dom.*;

import java.util.ArrayList;
import java.util.List;

public class JavaASTMethodInvocationParser {

    // sourcepathEntries MUST be absolute paths
    public static List<MethodInvocation> parse(String[] sourcepathEntries, String unitName, String sourceCode) {
        ASTParser parser = ASTParser.newParser(AST.JLS16);
        parser.setSource(sourceCode.toCharArray());
        parser.setKind(ASTParser.K_COMPILATION_UNIT);

        parser.setResolveBindings(true);
        parser.setBindingsRecovery(true); // IMPORTANT: required if specified sourcepaths and classpaths are incomplete, otherwise no bindings will be generated
        parser.setUnitName(unitName);
        parser.setEnvironment(null, sourcepathEntries, null, true);

        CompilationUnit currentCompilationUnit = (CompilationUnit) parser.createAST(null);

        MethodInvocationVisitor visitor = new MethodInvocationVisitor();
        currentCompilationUnit.accept(visitor);

        return visitor.getInvocations();
    }

    private static class MethodInvocationVisitor extends ASTVisitor {

        private final List<MethodInvocation> invocations = new ArrayList<>();

        @Override
        public boolean visit(MethodInvocation node) {
            invocations.add(node);

            return super.visit(node);
        }

        public List<MethodInvocation> getInvocations() {
            return invocations;
        }
    }

}
