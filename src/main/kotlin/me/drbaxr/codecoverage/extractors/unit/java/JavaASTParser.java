package me.drbaxr.codecoverage.extractors.unit.java;

import kotlin.Pair;
import org.eclipse.jdt.core.dom.*;

import java.util.ArrayList;
import java.util.List;

public class JavaASTParser {

    private static CompilationUnit currentCompilationUnit;

    public static List<Pair<MethodDeclaration, Integer>> parse(String sourceCode) {
        ASTParser parser = ASTParser.newParser(AST.JLS16);
        parser.setSource(sourceCode.toCharArray());
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        currentCompilationUnit = (CompilationUnit) parser.createAST(null);

        ClassMethodVisitor visitor = new ClassMethodVisitor();
        currentCompilationUnit.accept(visitor);

        return visitor.getMethods();
    }

    private static class ClassMethodVisitor extends ASTVisitor {

        private final List<Pair<MethodDeclaration, Integer>> methods = new ArrayList<>(); // methodDeclaration, startLine

        @Override
        public boolean visit(MethodDeclaration node) {
            if (!hasAbstractModifier(node)) {

                ASTNode parent = node.getParent();
                if (parent instanceof TypeDeclaration) {
                    TypeDeclaration classNode = (TypeDeclaration) parent;

                    if (!classNode.isInterface() && !hasAbstractModifier(classNode)) {
                        methods.add(new Pair(
                            node,
                            currentCompilationUnit.getLineNumber(node.getStartPosition())
                        ));
                    }
                }

            }

            return super.visit(node);
        }

        private boolean hasAbstractModifier(BodyDeclaration node) {
            return Modifier.isAbstract(node.getModifiers());
        }

        public List<Pair<MethodDeclaration, Integer>> getMethods() {
            return methods;
        }
    }

}
