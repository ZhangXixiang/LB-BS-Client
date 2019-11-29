package com.lb.bs.client.util;

import com.google.common.base.Predicate;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.MethodParameterScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * program: LB-BS-Client
 * author: bsworld.xie
 * create: 2019-11-28 17:45
 * description:
 */
public class ReflectionUtil {

    /**
     * 通过扫描，获取反射对象
     */
    public static Reflections getReflection(List<String> packNameList) {

        //
        // filter
        //todo
        FilterBuilder filterBuilder = new FilterBuilder().includePackage("com.lb.bs.client");
        for (String packName : packNameList) {
            filterBuilder = filterBuilder.includePackage(packName);
        }

        Predicate<String> filter = filterBuilder;

        //
        // urls
        //
        Collection<URL> urlTotals = new ArrayList<URL>();
        for (String packName : packNameList) {
            Set<URL> urls = ClasspathHelper.forPackage(packName);
            urlTotals.addAll(urls);
        }

        //
        Reflections reflections = new Reflections(new ConfigurationBuilder().filterInputsBy(filter)
                .setScanners(new SubTypesScanner().filterResultsBy(filter), new TypeAnnotationsScanner().filterResultsBy(filter), new FieldAnnotationsScanner()
                                .filterResultsBy(filter),
                        new MethodAnnotationsScanner()
                                .filterResultsBy(filter),
                        new MethodParameterScanner()).setUrls(urlTotals));

        return reflections;
    }
}
