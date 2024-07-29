package com.mall.generator.utils;

import cn.hutool.core.date.DateUtil;
import com.mall.common.base.exception.GlobalException;
import com.mall.generator.domain.entity.ColumnEntity;
import com.mall.generator.domain.entity.ColumnSchema;
import com.mall.generator.domain.entity.TableEntity;
import com.mall.generator.domain.entity.TableSchema;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器   工具类
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月19日 下午11:40:24
 */
public class GenUtils {

    public static List<String> getTemplates() {
        return Arrays.asList(
                "template/Entity.java.vm",
                "template/Dao.xml.vm",
                "template/menu.sql.vm",
                "template/Service.java.vm",
                "template/ServiceImpl.java.vm",
                "template/Controller.java.vm",
                "template/Dao.java.vm",
                "template/index.vue.vm",
                "template/add-or-update.vue.vm"
        );
    }

    public static void generatorCode(TableSchema tableInfo, List<ColumnSchema> columnInfo, ZipOutputStream zip) {
        Configuration config = getConfig();
        TableEntity tableEntity = buildTableEntity(tableInfo, columnInfo, config);

        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);

        String mainPath = Optional.ofNullable(config.getString("mainPath")).orElse("io.renren");

        Map<String, Object> contextMap = buildContextMap(tableEntity, mainPath, config);
        VelocityContext context = new VelocityContext(contextMap);

        List<String> templates = getTemplates();
        for (String template : templates) {
            renderTemplate(template, tableEntity, context, config, zip);
        }
    }

    private static TableEntity buildTableEntity(TableSchema tableInfo, List<ColumnSchema> columnInfo, Configuration config) {
        TableEntity tableEntity = new TableEntity();
        tableEntity.setTableName(tableInfo.getTableName());
        tableEntity.setComments(tableInfo.getTableComment());

        String className = tableToJava(tableEntity.getTableName(), config.getStringArray("tablePrefix"));
        tableEntity.setPascalCaseClassName(className);
        tableEntity.setCamelCaseClassName(StringUtils.uncapitalize(className));

        boolean hasBigDecimal = false;
        boolean hasList = false;
        List<ColumnEntity> columnsList = new ArrayList<>();

        for (ColumnSchema columnSchema : columnInfo) {
            ColumnEntity columnEntity = buildColumnEntity(columnSchema, config);
            columnsList.add(columnEntity);

            if (!hasBigDecimal && "BigDecimal".equals(columnEntity.getAttrType())) {
                hasBigDecimal = true;
            }
            if (!hasList && "array".equals(columnEntity.getExtra())) {
                hasList = true;
            }
            if ("PRI".equalsIgnoreCase(columnSchema.getColumnKey()) && tableEntity.getPk() == null) {
                tableEntity.setPk(columnEntity);
            }
        }
        tableEntity.setColumns(columnsList);
        tableEntity.setHasBigDecimal(hasBigDecimal);
        tableEntity.setHasList(hasList);

        return tableEntity;
    }

    private static ColumnEntity buildColumnEntity(ColumnSchema columnSchema, Configuration config) {
        ColumnEntity columnEntity = new ColumnEntity();
        columnEntity.setColumnName(columnSchema.getColumnName());
        columnEntity.setDataType(columnSchema.getDataType());
        columnEntity.setComments(columnSchema.getColumnComment());
        columnEntity.setExtra(columnSchema.getExtra());

        String attrName = columnToJava(columnEntity.getColumnName());
        columnEntity.setAttrName(attrName);
        columnEntity.setAttrname(StringUtils.uncapitalize(attrName));

        String attrType = config.getString(columnEntity.getDataType(), columnToJava(columnEntity.getDataType()));
        columnEntity.setAttrType(attrType);

        return columnEntity;
    }

    private static Map<String, Object> buildContextMap(TableEntity tableEntity, String mainPath, Configuration config) {
        Map<String, Object> map = new HashMap<>();
        map.put("tableName", tableEntity.getTableName());
        map.put("comments", tableEntity.getComments());
        map.put("pk", tableEntity.getPk());
        map.put("className", tableEntity.getPascalCaseClassName());
        map.put("classname", tableEntity.getCamelCaseClassName());
        map.put("pathName", tableEntity.getCamelCaseClassName().toLowerCase());
        map.put("columns", tableEntity.getColumns());
        map.put("hasBigDecimal", tableEntity.isHasBigDecimal());
        map.put("hasList", tableEntity.isHasList());
        map.put("mainPath", mainPath);
        map.put("package", config.getString("package"));
        map.put("moduleName", config.getString("moduleName"));
        map.put("author", config.getString("author"));
        map.put("email", config.getString("email"));
        map.put("datetime", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));

        return map;
    }

    private static void renderTemplate(String template, TableEntity tableEntity, VelocityContext context, Configuration config, ZipOutputStream zip) {
        try (StringWriter sw = new StringWriter()) {
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, sw);
            zip.putNextEntry(new ZipEntry(Objects.requireNonNull(getFileName(template, tableEntity.getPascalCaseClassName(), config.getString("package"), config.getString("moduleName")))));
            IOUtils.write(sw.toString(), zip, "UTF-8");
            zip.closeEntry();
        } catch (IOException e) {
            throw new GlobalException("渲染模板失败，表名：" + tableEntity.getTableName());
        }
    }

    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }

    public static String tableToJava(String tableName, String[] tablePrefixArray) {
        if (ArrayUtils.isNotEmpty(tablePrefixArray)) {
            for (String tablePrefix : tablePrefixArray) {
                if (tableName.startsWith(tablePrefix)) {
                    tableName = tableName.replaceFirst(tablePrefix, "");
                }
            }
        }
        return columnToJava(tableName);
    }

    public static Configuration getConfig() {
        try {
            return new PropertiesConfiguration("generator.properties");
        } catch (ConfigurationException e) {
            throw new GlobalException("获取配置文件失败");
        }
    }

    public static String getFileName(String template, String className, String packageName, String moduleName) {
        String packagePath = "main" + File.separator + "java" + File.separator;
        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator + moduleName + File.separator;
        }
        if (template.contains("Entity.java.vm") || template.contains("MongoEntity.java.vm")) {
            return packagePath + "entity" + File.separator + className + ".java";
        }
        if (template.contains("Dao.java.vm")) {
            return packagePath + "mapper" + File.separator + className + "Mapper.java";
        }
        if (template.contains("Service.java.vm")) {
            return packagePath + "service" + File.separator + "I" + className + "Service.java";
        }
        if (template.contains("ServiceImpl.java.vm")) {
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }
        if (template.contains("Controller.java.vm")) {
            return packagePath + "controller" + File.separator + className + "Controller.java";
        }
        if (template.contains("Dao.xml.vm")) {
            return packagePath + "mapper" + File.separator + "xml" + File.separator + className + "Mapper.xml";
        }
        if (template.contains("menu.sql.vm")) {
            return className.toLowerCase() + "_menu.sql";
        }
        if (template.contains("index.vue.vm") || template.contains("add-or-update.vue.vm")) {
            return "main" + File.separator + "resources" + File.separator + "src" + File.separator + "views" + File.separator + "modules" + File.separator + moduleName + File.separator + className.toLowerCase() + (template.contains("index.vue.vm") ? ".vue" : "-add-or-update.vue");
        }

        return null;
    }

}
