/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.bouncycastle.srcgen;

import static com.google.currysrc.api.process.Rules.createMandatoryRule;
import static com.google.currysrc.api.process.Rules.createOptionalRule;

import com.google.currysrc.Main;
import com.google.currysrc.api.RuleSet;
import com.google.currysrc.api.input.DirectoryInputFileGenerator;
import com.google.currysrc.api.input.InputFileGenerator;
import com.google.currysrc.api.output.BasicOutputSourceFileGenerator;
import com.google.currysrc.api.output.OutputSourceFileGenerator;
import com.google.currysrc.api.process.Rule;
import com.google.currysrc.api.process.ast.BodyDeclarationLocators;
import com.google.currysrc.api.process.ast.TypeLocator;
import com.google.currysrc.processors.AddMarkerAnnotation;
import com.google.currysrc.processors.HidePublicClasses;
import com.google.currysrc.processors.InsertHeader;
import com.google.currysrc.processors.ModifyQualifiedNames;
import com.google.currysrc.processors.ModifyStringLiterals;
import com.google.currysrc.processors.RenamePackage;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.formatter.DefaultCodeFormatterConstants;

/**
 * Generates bouncycastle sources in the com.android.org.bouncycastle package.
 */
public class BouncyCastleTransform {
    static final String ORIGINAL_PACKAGE = "org.bouncycastle";
    static final String ANDROID_PACKAGE = "com.android.org.bouncycastle";

    /**
     * Usage:
     * java BouncyCastleTransform {source dir} {target dir}
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
          throw new IllegalArgumentException(
              "Usage: " + BouncyCastleTransform.class.getCanonicalName()
                  + " <source-dir> <target-dir> <core-platform-api-file>");
        }
        String sourceDir = args[0];
        String targetDir = args[1];
        Path corePlatformApiFile = Paths.get(args[2]);

        Map<String, String> options = JavaCore.getOptions();
        options.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_8);
        options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_8);
        options.put(JavaCore.COMPILER_DOC_COMMENT_SUPPORT, JavaCore.ENABLED);
        options.put(DefaultCodeFormatterConstants.FORMATTER_TAB_CHAR, JavaCore.SPACE);
        options.put(DefaultCodeFormatterConstants.FORMATTER_TAB_SIZE, "4");

        new Main(false /* debug */)
            .setJdtOptions(options)
            .execute(new TransformRuleSet(sourceDir, targetDir, corePlatformApiFile));
    }

    static class TransformRuleSet implements RuleSet {
        private final String sourceDir;
        private final String targetDir;
        private final Path corePlatformApiFile;

        TransformRuleSet(String sourceDir, String targetDir, Path corePlatformApiFile) {
            this.sourceDir = sourceDir;
            this.targetDir = targetDir;
            this.corePlatformApiFile = corePlatformApiFile;
        }

        @Override
        public InputFileGenerator getInputFileGenerator() {
            return new DirectoryInputFileGenerator(new File(sourceDir));
        }

        @Override
        public List<Rule> getRuleList(File ignored) {
            return Arrays.asList(
                    // Doc change: Insert a warning about the source code being generated.
                    createMandatoryRule(
                            new InsertHeader("/* GENERATED SOURCE. DO NOT MODIFY. */\n")),
                    // AST change: Change the package of each CompilationUnit
                    createMandatoryRule(new RenamePackage(ORIGINAL_PACKAGE, ANDROID_PACKAGE)),
                    // AST change: Change all qualified names in code and javadoc.
                    createOptionalRule(new ModifyQualifiedNames(ORIGINAL_PACKAGE, ANDROID_PACKAGE)),
                    // AST change: Change all string literals containing package names in code.
                    createOptionalRule(new ModifyStringLiterals(ORIGINAL_PACKAGE, ANDROID_PACKAGE)),
                    // Doc change: Insert @hide on all public classes.
                    createHidePublicClassesRule(),
                    // AST change: Add CorePlatformApi to specified classes and members
                    createOptionalRule(new AddMarkerAnnotation("libcore.api.CorePlatformApi",
                        BodyDeclarationLocators.readBodyDeclarationLocators(corePlatformApiFile)))
                    );
        }

        private static Rule createHidePublicClassesRule() {
            List<TypeLocator> publicApiClassesWhitelist = Collections.emptyList();
            return createOptionalRule(new HidePublicClasses(publicApiClassesWhitelist,
                    "This class is not part of the Android public SDK API"));
        }

        @Override
        public OutputSourceFileGenerator getOutputSourceFileGenerator() {
            File outputDir = new File(targetDir);
            return new BasicOutputSourceFileGenerator(outputDir);
        }
    }

    private BouncyCastleTransform() {
    }
}
