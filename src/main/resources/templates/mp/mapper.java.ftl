package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
* <p>
* ${table.comment!} Mapper 接口
* </p>
*
* @author ${author}
* @since ${date}
*/
@Mapper
@Repository
<#if kotlin>
    interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {
/**
  *  查询表${table.name}所有信息
  */
 List<${entity}> findAll${entity}();

<#list table.fields as field>
<#if field.keyFlag>
 /**
  *  根据主键${field.propertyName}查询表${table.name}信息
  *  @param ${field.propertyName}
  */
 ${entity} find${entity}By${field.propertyName}(@Param("${field.propertyName}") ${field.propertyType} ${field.propertyName});
</#if>
</#list>

/**
*  根据条件查询表${table.name}信息
*  @param ${table.entityPath}
*/
List<${entity}> find${entity}ByCondition(${entity} ${table.entityPath});

<#list table.fields as field>
<#if field.keyFlag>
 /**
  *  根据主键${field.propertyName}查询表${table.name}信息
  *  @param ${field.propertyName}
  */
 Integer delete${entity}By${field.propertyName}(@Param("${field.propertyName}") ${field.propertyType} ${field.propertyName});
</#if>
</#list>

<#list table.fields as field>
<#if field.keyFlag>
 /**
  *  根据主键${field.propertyName}更新表${table.name}信息
  *  @param ${table.entityPath}
  */
 Integer update${entity}By${field.propertyName}(${entity} ${table.entityPath});
</#if>
</#list>

<#list table.fields as field>
<#if field.keyFlag>
 /**
  *  新增表${table.name}信息
  *  @param ${table.entityPath}
  */
 Integer add${entity}(${entity} ${table.entityPath});
</#if>
</#list>

}
</#if>
