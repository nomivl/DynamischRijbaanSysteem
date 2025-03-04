package com.dynamischrijbaansysteem.utils;


import javafx.scene.Group;
import javafx.scene.shape.SVGPath;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SVGLoader {
    public static Group loadSVG(String filePath) {
        Group group = new Group();
        try {
            String svgContent = new String(Files.readAllBytes((Paths.get(filePath))));
            Pattern pattern = Pattern.compile("\\sd=\"([^\"]+)\"");
            Matcher matcher = pattern.matcher(svgContent);

            while(matcher.find()) {
                String pathData = matcher.group(1);
                SVGPath svgPath = new SVGPath();
                svgPath.setContent(pathData);
                //svgPath.setStyle("-fx-fill: black;");
                group.getChildren().add(svgPath);
            }

            return group;

        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
