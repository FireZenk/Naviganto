package org.firezenk.naviganto.processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import org.firezenk.naviganto.annotations.RoutableActivity;
import org.firezenk.naviganto.annotations.RoutableView;
import org.firezenk.naviganto.processor.exceptions.NotEnoughParametersException;
import org.firezenk.naviganto.processor.exceptions.ParameterNotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Generated;
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
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

/**
 * Project: Naviganto
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
        final Set<String> set = new HashSet<>();
        set.add(RoutableActivity.class.getCanonicalName());
        set.add(RoutableView.class.getCanonicalName());
        return Collections.unmodifiableSet(set);
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

        boolean isActivity;
        int forResult;
        List<TypeMirror> params;

        if (isActivity = (typeElement.getAnnotation(RoutableActivity.class) != null)) {
            forResult = this.getForResult(typeElement.getAnnotation(RoutableActivity.class));
            params = this.getParameters(typeElement.getAnnotation(RoutableActivity.class));
        } else {
            forResult = this.getForResult(typeElement.getAnnotation(RoutableView.class));
            params = this.getParameters(typeElement.getAnnotation(RoutableView.class));
        }

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

        sb.append("\n");

        if (isActivity) {
            sb.append("  final android.content.Intent intent = new android.content.Intent((android.content.Context) context, " + typeElement.getSimpleName() + ".class);\n");

            if (params.size() > 0) {
                sb.append("  android.os.Bundle bundle = new android.os.Bundle();\n\n");
                sb.append(this.parametersToBundle(params));
                sb.append("  intent.putExtras(bundle);\n");
            }

            sb.append("" +
                    "  if (context instanceof android.app.Activity) {\n" +
                    "      ((android.app.Activity) context).startActivityForResult(intent, "+ forResult +");\n" +
                    "  } else if (context instanceof android.content.Context) {\n" +
                    "      ((android.content.Context) context).startActivity(intent);\n" +
                    "  }\n");
        } else {
            sb.append("" +
                    "  if (viewParent == null || !(viewParent instanceof android.view.ViewGroup)) {\n" +
                    "      throw new ParameterNotFoundException(\"Need a view parent or is not a ViewGroup\");\n" +
                    "  }\n");

            if (params.size() > 0) {
                sb.append("" +
                        "  ((android.view.ViewGroup) viewParent).removeAllViews();\n" +
                        "  ((android.view.ViewGroup) viewParent).addView(" + typeElement.getSimpleName() + ".newInstance((android.content.Context) context " + this.parametersToString(params) + "));\n");
            } else {
                sb.append("" +
                        "  ((android.view.ViewGroup) viewParent).removeAllViews();\n" +
                        "  ((android.view.ViewGroup) viewParent).addView(" + typeElement.getSimpleName() + ".newInstance((android.content.Context) context));\n");
            }
        }

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

    private String parametersToString(List<TypeMirror> params) {
        final StringBuilder sb = new StringBuilder();

        int i = 0;
        for (TypeMirror tm : params) {
            sb.append(", (" + tm.toString() + ") parameters[" + i + "]");
            ++i;
        }

        return sb.toString();
    }

    private String parametersToBundle(List<TypeMirror> params) {
        final StringBuilder sb = new StringBuilder();

        int i = 0;
        for (TypeMirror tm : params) {

            final String extra = "parameters[" + i + "]);\n";
            final String casting = "(" + tm.toString() + ") ";

            switch (tm.toString()) {
                case "java.lang.Boolean":
                    sb.append("  bundle.putBoolean(\"bool" + i + "\", ");
                    sb.append(casting);
                    sb.append(extra);
                    break;
                case "java.lang.Integer":
                    sb.append("  bundle.putInt(\"int" + i + "\", ");
                    sb.append(casting);
                    sb.append(extra);
                    break;
                case "java.lang.Character":
                    sb.append("  bundle.putChar(\"char" + i + "\", ");
                    sb.append(casting);
                    sb.append(extra);
                    break;
                case "java.lang.Float":
                    sb.append("  bundle.putFloat(\"float" + i + "\", ");
                    sb.append(casting);
                    sb.append(extra);
                    break;
                case "java.lang.Double":
                    sb.append("  bundle.putDouble(\"double" + i + "\", ");
                    sb.append(casting);
                    sb.append(extra);
                    break;
                case "java.lang.String":
                    sb.append("  bundle.putString(\"string" + i + "\", ");
                    sb.append(casting);
                    sb.append(extra);
                    break;
                default:
                    sb.append("  bundle.putParcelable(\"parcelable" + i + "\", (android.os.Parcelable) " + extra);
                    break;
            }

            sb.append("\n");
            ++i;
        }

        return sb.toString();
    }

    private TypeSpec createRoute(TypeElement typeElement, ArrayList<MethodSpec> methods) {
        messager.printMessage(Diagnostic.Kind.NOTE, "Saving route file...");
        return TypeSpec.classBuilder(typeElement.getSimpleName() + "Route")
                .addAnnotation(AnnotationSpec.builder(Generated.class)
                        .addMember("value", "$S", this.getClass().getName())
                        .build())
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(org.firezenk.naviganto.processor.interfaces.Routable.class)
                .addMethods(methods)
                .build();
    }

    @SuppressWarnings("unchecked") private List<TypeMirror> getParameters(RoutableActivity annotation) {
        try {
            annotation.params(); // TODO get forResult
        } catch (MirroredTypesException e) {
            return (List<TypeMirror>) e.getTypeMirrors();
        }
        return null;
    }

    @SuppressWarnings("unchecked") private List<TypeMirror> getParameters(RoutableView annotation) {
        try {
            annotation.params(); // TODO get forResult
        } catch (MirroredTypesException e) {
            return (List<TypeMirror>) e.getTypeMirrors();
        }
        return null;
    }

    private int getForResult(RoutableActivity annotation) {
        return annotation.forResult();
    }

    private int getForResult(RoutableView annotation) {
        return annotation.forResult();
    }
}
