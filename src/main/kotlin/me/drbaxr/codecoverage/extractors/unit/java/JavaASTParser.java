package me.drbaxr.codecoverage.extractors.unit.java;

import kotlin.Pair;
import org.eclipse.jdt.core.dom.*;

import java.util.ArrayList;
import java.util.List;

public class JavaASTParser {

    private static CompilationUnit currentCompilationUnit;

    public static List<Pair<MethodDeclaration, Integer>> parse(String sourceCode) {
        ASTParser parser = ASTParser.newParser(AST.JLS3);
        parser.setSource(sourceCode.toCharArray());
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        currentCompilationUnit = (CompilationUnit) parser.createAST(null);

        MethodVisitor visitor = new MethodVisitor();
        currentCompilationUnit.accept(visitor);

        return visitor.getMethods();
    }

    private static class MethodVisitor extends ASTVisitor {

        private final List<Pair<MethodDeclaration, Integer>> methods = new ArrayList<>(); // methodDeclaration, startLine

        @Override
        public boolean visit(MethodDeclaration node) {
            Pair<MethodDeclaration, Integer> entity = new Pair(
                node,
                currentCompilationUnit.getLineNumber(node.getStartPosition())
            );
            methods.add(entity);

            return super.visit(node);
        }

        public List<Pair<MethodDeclaration, Integer>> getMethods() {
            return methods;
        }
    }

}
