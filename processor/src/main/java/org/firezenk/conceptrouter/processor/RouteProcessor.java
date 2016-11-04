package org.firezenk.conceptrouter.processor;

import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableSet;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import org.firezenk.conceptrouter.processor.annotations.RoutableActivity;
import org.firezenk.conceptrouter.processor.annotations.RoutableView;
import org.firezenk.conceptrouter.processor.exceptions.NotEnoughParametersException;
import org.firezenk.conceptrouter.processor.exceptions.ParameterNotFoundException;
import org.firezenk.conceptrouter.processor.interfaces.Routable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;

import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

/**
 * Project: ConceptRouter
 *
 * Created by Jorge Garrido Oval on 04/11/2016.
 * Copyright © Jorge Garrido Oval 2016
 */
@AutoService(Processor.class)
public class RouteProcessor extends AbstractProcessor {

    private Filer filer;
    private Messager messager;

    @Override public synchronized void init(ProcessingEnvironment env){
        super.init(env);
        this.filer = env.getFiler();
        this.messager = env.getMessager();
    }

    @Override public Set<String> getSupportedAnnotationTypes() {
        return ImmutableSet.of(
                RoutableActivity.class.getCanonicalName(),
                RoutableView.class.getCanonicalName());
    }

    @Override public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
        for (Element element :  env.getElementsAnnotatedWith(RoutableActivity.class)) {
            JavaFile javaFile = this.generateRoute((TypeElement) element);
            try {
                javaFile.writeTo(filer);
            } catch (Exception e) {
                messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage());
                e.printStackTrace();
            }
        }

        for (Element element :  env.getElementsAnnotatedWith(RoutableView.class)) {
            JavaFile javaFile = this.generateRoute((TypeElement) element);
            try {
                javaFile.writeTo(filer);
            } catch (Exception e) {
                messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage());
                e.printStackTrace();
            }
        }

        return true;
    }

    private JavaFile generateRoute(TypeElement typeElement) {
        messager.printMessage(Diagnostic.Kind.NOTE, "Creating route...");

        final ArrayList<MethodSpec> methods = new ArrayList<>();

        methods.add(this.addRouteMethod(typeElement));

        final TypeSpec myClass = this.createRoute(typeElement, methods);

        messager.printMessage(Diagnostic.Kind.NOTE, "Save location: " +
                typeElement.getQualifiedName().toString().replace("."+typeElement.getSimpleName(), ""));
        return JavaFile.builder(
                typeElement.getQualifiedName().toString().replace("."+typeElement.getSimpleName(), ""),
                myClass).build();
    }

    private MethodSpec addRouteMethod(TypeElement typeElement) {
        messager.printMessage(Diagnostic.Kind.NOTE, "Generating route method");

        final List<TypeMirror> params = this.getParameters(typeElement.getAnnotation(RoutableActivity.class));
        final StringBuilder sb = new StringBuilder();

        sb.append("" +
                "  if (parameters.length < " + params.size() + ") {\n" +
                "      throw new NotEnoughParametersException(\"Need " + params.size() + " params\");\n" +
                "  }\n");

        int i = 0;
        for (TypeMirror tm : params) {
            sb.append("" +
                    "  if (parameters[" + i + "] == null || !(parameters[" + i + "] instanceof " + tm.toString() + ")) {\n" +
                    "      throw new ParameterNotFoundException(\"Need " + tm.toString() + "\");\n" +
                    "  }\n");
            ++i;
        }

        // TODO GENERATE START ACTIVITY OR ADD TO VIEW

        messager.printMessage(Diagnostic.Kind.NOTE, sb.toString());

        return MethodSpec.methodBuilder("route")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addException(ParameterNotFoundException.class)
                .addException(NotEnoughParametersException.class)
                .returns(void.class)
                .addParameter(Object.class, "context")
                .addParameter(Object[].class, "parameters")
                .addParameter(Object.class, "viewParent")
                .addCode(sb.toString())
                .build();
    }

    private TypeSpec createRoute(TypeElement typeElement, ArrayList<MethodSpec> methods) {
        messager.printMessage(Diagnostic.Kind.NOTE, "Saving route file...");
        return TypeSpec.classBuilder(typeElement.getSimpleName() + "Route")
                .addModifiers(Modifier.PUBLIC)
                .superclass(TypeName.get(typeElement.asType()))
                .addSuperinterface(Routable.class)
                .addMethods(methods)
                .build();
    }

    private List<TypeMirror> getParameters(RoutableActivity annotation) {
        try {
            annotation.extras();
        } catch (MirroredTypesException e) {
            return (List<TypeMirror>) e.getTypeMirrors();
        }
        return null;
    }
}
