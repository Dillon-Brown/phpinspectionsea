package com.kalessil.phpStorm.phpInspectionsEA.inspectors.semanticalAnalysis.binaryOperations.strategy;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import com.jetbrains.php.lang.lexer.PhpTokenTypes;
import com.jetbrains.php.lang.psi.elements.*;
import com.kalessil.phpStorm.phpInspectionsEA.utils.ExpressionSemanticUtil;
import com.kalessil.phpStorm.phpInspectionsEA.utils.OpenapiTypesUtil;
import com.kalessil.phpStorm.phpInspectionsEA.utils.PhpLanguageUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/*
 * This file is part of the Php Inspections (EA Extended) package.
 *
 * (c) Vladimir Reznichenko <kalessil@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

final public class MultipleFalsyValuesCheckStrategy {
    private static final String messageAlwaysTrue  = "'%s' seems to be always true when reached.";
    private static final String messageAlwaysFalse = "'%s' seems to be always false when reached.";

    public static boolean apply(@NotNull BinaryExpression expression, @NotNull ProblemsHolder holder) {
        boolean result              = false;
        final IElementType operator = expression.getOperationType();
        if (operator != null && (operator == PhpTokenTypes.opAND || operator == PhpTokenTypes.opOR)) {
            /* false-positives: part of another condition */
            final PsiElement parent  = expression.getParent();
            final PsiElement context = parent instanceof ParenthesizedExpression ? parent.getParent() : parent;
            if (!(context instanceof BinaryExpression) || ((BinaryExpression) context).getOperationType() != operator) {
                final List<PsiElement> fragments = extractFragments(expression, operator);
                if (fragments.size() > 1) {

                }
                fragments.clear();
            }
        }
        return result;
    }

    private static boolean isFalsyValue(@NotNull PsiElement element) {
        if (element instanceof StringLiteralExpression) {
            return ((StringLiteralExpression) element).getContents().isEmpty();
        } else if (element instanceof ConstantReference) {
            return PhpLanguageUtil.isFalse(element);
        } else if (element instanceof ArrayCreationExpression) {
            return element.getChildren().length == 0;
        } else if (OpenapiTypesUtil.isNumber(element)) {
            return element.getText().equals("0");
        }
        return false;
    }

    @NotNull
    private static List<PsiElement> extractFragments(@NotNull BinaryExpression binary, @Nullable IElementType operator) {
        final List<PsiElement> result     = new ArrayList<>();
        final IElementType binaryOperator = binary.getOperationType();
        if (binaryOperator == operator) {
            Stream.of(binary.getLeftOperand(), binary.getRightOperand())
                    .filter(Objects::nonNull).map(ExpressionSemanticUtil::getExpressionTroughParenthesis)
                    .forEach(expression -> {
                        if (expression instanceof BinaryExpression) {
                            result.addAll(extractFragments((BinaryExpression) expression, operator));
                        }
                    });
        } else if (binaryOperator == PhpTokenTypes.opEQUAL || binaryOperator == PhpTokenTypes.opNOT_EQUAL) {
            final boolean isFalsyCheck = Stream.of(binary.getRightOperand(), binary.getLeftOperand())
                    .filter(Objects::nonNull).anyMatch(MultipleFalsyValuesCheckStrategy::isFalsyValue);
            if (isFalsyCheck) {
                result.add(binary);
            }
        }
        return result;
    }
}
