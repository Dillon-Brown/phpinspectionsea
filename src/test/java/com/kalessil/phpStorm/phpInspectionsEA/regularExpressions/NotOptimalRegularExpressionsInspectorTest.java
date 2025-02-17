package com.kalessil.phpStorm.phpInspectionsEA.regularExpressions;

import com.kalessil.phpStorm.phpInspectionsEA.PhpCodeInsightFixtureTestCase;
import com.kalessil.phpStorm.phpInspectionsEA.inspectors.regularExpressions.NotOptimalRegularExpressionsInspector;

final public class NotOptimalRegularExpressionsInspectorTest extends PhpCodeInsightFixtureTestCase {
    public void testFindGreedyCharacterSets() {
        myFixture.enableInspections(new NotOptimalRegularExpressionsInspector());
        myFixture.configureByFile("testData/fixtures/regularExpressions/greedy-character-sets.php");
        myFixture.testHighlighting(true, false, true);
    }
    public void testFindShortCharacterClasses() {
        myFixture.enableInspections(new NotOptimalRegularExpressionsInspector());
        myFixture.configureByFile("testData/fixtures/regularExpressions/short-character-classes.php");
        myFixture.testHighlighting(true, false, true);
    }
    public void testFindBuggyNestedTagsDetection() {
        myFixture.enableInspections(new NotOptimalRegularExpressionsInspector());
        myFixture.configureByFile("testData/fixtures/regularExpressions/nested-tags.php");
        myFixture.testHighlighting(true, false, true);
    }
    public void testDelimiters() {
        myFixture.enableInspections(new NotOptimalRegularExpressionsInspector());
        myFixture.configureByFile("testData/fixtures/regularExpressions/delimeters.php");
        myFixture.testHighlighting(true, false, true);
    }
    public void testProblematicModifiers() {
        myFixture.enableInspections(new NotOptimalRegularExpressionsInspector());
        myFixture.configureByFile("testData/fixtures/regularExpressions/problematic-modifiers.php");
        myFixture.testHighlighting(true, false, true);
    }
    public void testNestedQuantifiers() {
        myFixture.enableInspections(new NotOptimalRegularExpressionsInspector());
        myFixture.configureByFile("testData/fixtures/regularExpressions/quantifier-compounds-quantifier.php");
        myFixture.testHighlighting(true, false, true);
    }
    public void testSenselessIgnoreCaseModifier() {
        myFixture.enableInspections(new NotOptimalRegularExpressionsInspector());
        myFixture.configureByFile("testData/fixtures/regularExpressions/senseless-i-modifier.php");
        myFixture.testHighlighting(true, false, true);
    }
    public void testSenselessDotAllModifier() {
        myFixture.enableInspections(new NotOptimalRegularExpressionsInspector());
        myFixture.configureByFile("testData/fixtures/regularExpressions/senseless-s-modifier.php");
        myFixture.testHighlighting(true, false, true);
    }
    public void testUnnecessaryCaseManipulation() {
        myFixture.enableInspections(new NotOptimalRegularExpressionsInspector());
        myFixture.configureByFile("testData/fixtures/regularExpressions/unnecessary-case-manipulation.php");
        myFixture.testHighlighting(true, false, true);
    }
    public void testMissingUnicodeModifier() {
        myFixture.enableInspections(new NotOptimalRegularExpressionsInspector());
        myFixture.configureByFile("testData/fixtures/regularExpressions/missing-u-modifier.php");
        myFixture.testHighlighting(true, false, true);
    }
    public void testPossibleCtypeUsages() {
        myFixture.enableInspections(new NotOptimalRegularExpressionsInspector());
        myFixture.configureByFile("testData/fixtures/regularExpressions/ctype-functions-usage.php");
        myFixture.testHighlighting(true, false, true);
    }
    public void testPossibleAlternativeRegexFunctionsUsages() {
        myFixture.enableInspections(new NotOptimalRegularExpressionsInspector());
        myFixture.configureByFile("testData/fixtures/regularExpressions/alternative-regex-functions-usage.php");
        myFixture.testHighlighting(true, false, true);
    }
    public void testPossiblePlainApiUsages() {
        myFixture.enableInspections(new NotOptimalRegularExpressionsInspector());
        myFixture.configureByFile("testData/fixtures/regularExpressions/plain-api-usage.php");
        myFixture.testHighlighting(true, false, true);

        myFixture.getAllQuickFixes().forEach(fix -> myFixture.launchAction(fix));
        myFixture.setTestDataPath(".");
        myFixture.checkResultByFile("testData/fixtures/regularExpressions/plain-api-usage.fixed.php");
    }
    public void testPregMatchPatterns() {
        myFixture.enableInspections(new NotOptimalRegularExpressionsInspector());
        myFixture.configureByFile("testData/fixtures/regularExpressions/preg-match.php");
        myFixture.testHighlighting(true, false, true);

        myFixture.getAllQuickFixes().forEach(fix -> myFixture.launchAction(fix));
        myFixture.setTestDataPath(".");
        myFixture.checkResultByFile("testData/fixtures/regularExpressions/preg-match.fixed.php");
    }
}
