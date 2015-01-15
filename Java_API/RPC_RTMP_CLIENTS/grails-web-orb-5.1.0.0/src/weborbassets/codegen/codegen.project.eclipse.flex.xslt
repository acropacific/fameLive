<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:codegen="xalan://weborb.data.management.codegen.XsltExtention">
	
	<xsl:output method="xml" indent="yes" omit-xml-declaration="yes" />
	
	<xsl:template name="codegen.project.eclipse.flex.actionscript.properties.applications" />
	<xsl:template name="codegen.project.eclipse.flex.actionscript.properties.mainApplicationPath" />
	<xsl:template name="codegen.project.eclipse.flex.name" />
  <xsl:template name="libs.folder.additional.content"/>
	
	<xsl:template name="codegen.project.eclipse.flex">
		<file name=".project" type="xml">
			<projectDescription>
				<name>
					<xsl:call-template name="codegen.project.eclipse.flex.name" />
				</name>
				<comment></comment>
				<projects>
				</projects>
				<buildSpec>
					<buildCommand>
						<name>com.adobe.flexbuilder.project.flexbuilder</name>
						<arguments>
						</arguments>
					</buildCommand>
				</buildSpec>
				<natures>
					<nature>com.adobe.flexbuilder.project.flexnature</nature>
					<nature>com.adobe.flexbuilder.project.actionscriptnature</nature>
				</natures>
				<linkedResources>
					<link>
						<name>bin-debug</name>
						<type>2</type>
						<location>
							<xsl:value-of select="//runtime/@path" />/<xsl:call-template name="codegen.project.eclipse.flex.name" />
						</location>
					</link>
				</linkedResources>
			</projectDescription>
		</file>
	</xsl:template>

	<xsl:template name="codegen.project.eclipse.flex.properties">
		<file name=".flexProperties">
			<flexProperties enableServiceManager="false"
				flexServerFeatures="4" flexServerType="2" serverContextRoot=""
				serverRoot="{//runtime/@path}" serverRootURL="{//runtime/@serverRootURL}"
				toolCompile="true" useServerFlexSDK="false" version="2">
			</flexProperties>
		</file>
	</xsl:template>

	<xsl:template name="codegen.project.eclipse.flex.actionscript.properties">
    <xsl:variable name="servicesConfigFilename">
      <xsl:choose>
        <xsl:when test="codegen:hasRTMP()">weborb-services-config.xml</xsl:when>
        <xsl:otherwise>services-config.xml</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
        <folder name="libs">
          <file path="{concat(//runtime/@path, '/weborbassets/wdm/weborb.swc')}" hideContent="true"/>
          <xsl:call-template name="libs.folder.additional.content" />
        </folder>
        <xsl:if test="codegen:isCloudMode()">
            <folder name="configs">
                <file path="{concat(//runtime/@path, '/WEB-INF/flex/', $servicesConfigFilename)}"/>
                <file path="{concat(//runtime/@path, '/WEB-INF/flex/remoting-config.xml')}"/>
                <file path="{concat(//runtime/@path, '/WEB-INF/flex/messaging-config.xml')}"/>
            </folder>
        </xsl:if>
		<file name=".actionScriptProperties">
			<actionScriptProperties version="3">
				<xsl:attribute name="mainApplicationPath"><xsl:call-template name="codegen.project.eclipse.flex.actionscript.properties.mainApplicationPath" /></xsl:attribute>
                <xsl:variable name="weborb-services-config">
                  <xsl:choose>
                     <xsl:when test="codegen:isCloudMode()">configs/<xsl:value-of select="$servicesConfigFilename"/></xsl:when>
                     <xsl:otherwise><xsl:value-of select="//runtime/@path"/>/WEB-INF/flex/<xsl:value-of select="$servicesConfigFilename"/></xsl:otherwise>
                  </xsl:choose>
                </xsl:variable>
                <compiler
					copyDependentFiles="true" enableModuleDebug="false"
					generateAccessible="false" htmlExpressInstall="true" htmlGenerate="true"
					htmlHistoryManagement="false" htmlPlayerVersion="9.0.0"
					htmlPlayerVersionCheck="true"
					outputFolderPath="bin-debug"
					sourceFolderPath="src" strict="true" useApolloConfig="false"
					verifyDigests="true" warn="true">
                    <xsl:attribute name="additionalCompilerArguments">-services &quot;<xsl:value-of select="$weborb-services-config"/>&quot; -locale en_US</xsl:attribute>
					<xsl:attribute name="outputFolderLocation"><xsl:value-of select="//runtime/@path" />/<xsl:call-template name="codegen.project.eclipse.flex.name" /></xsl:attribute>
					<xsl:attribute name="rootURL"><xsl:value-of select="//runtime/@serverRootURL" />/<xsl:call-template name="codegen.project.eclipse.flex.name" /></xsl:attribute>

					<compilerSourcePath />
					<libraryPath defaultLinkType="1">
						<libraryPathEntry kind="4" path="">
							<excludedEntries>
								<libraryPathEntry kind="3" linkType="1"
									path="${PROJECT_FRAMEWORKS}/libs/qtp.swc" useDefaultLinkType="false" />
								<libraryPathEntry kind="3" linkType="1"
									path="${PROJECT_FRAMEWORKS}/libs/automation.swc"
									useDefaultLinkType="false" />
								<libraryPathEntry kind="3" linkType="1"
									path="${PROJECT_FRAMEWORKS}/libs/automation_dmv.swc"
									useDefaultLinkType="false" />
								<libraryPathEntry kind="3" linkType="1"
									path="${PROJECT_FRAMEWORKS}/libs/automation_agent.swc"
									useDefaultLinkType="false" />
							</excludedEntries>
						</libraryPathEntry>
						<!-- <libraryPathEntry kind="3" linkType="1"
							path="libs/weborb.swc"
							useDefaultLinkType="false" />-->
							
							<libraryPathEntry kind="1" linkType="1" path="libs" />
            
            <!-- codegenformat id="0" name="Flex Remoting/AS3" -->
            <!-- <xsl:if test="//runtime/@codeFormatType = 0">
              <libraryPathEntry kind="1" linkType="1" path="libs" />
            </xsl:if>-->

            <!-- codegenformat id="3" name="Mate" -->
            <!-- <xsl:if test="//runtime/@codeFormatType = 3">
              <libraryPathEntry kind="1" linkType="1" path="libs" />
            </xsl:if>-->

            <!-- codegenformat id="4" name="Swiz" -->
            <!-- <xsl:if test="//runtime/@codeFormatType = 4">
              <libraryPathEntry kind="1" linkType="1" path="libs" />
            </xsl:if>-->

            <!-- codegenformat id="5" name="Cairngorm Framework" -->
            <!-- <xsl:if test="//runtime/@codeFormatType = 5">
              <libraryPathEntry kind="1" linkType="1" path="libs" />
              <libraryPathEntry kind="3" linkType="1" path="libs/Cairngorm.swc" useDefaultLinkType="false" />
            </xsl:if>-->

            <!-- codegenformat id="8" name="PureMVC/AS3-Standard" -->
            <!-- <xsl:if test="//runtime/@codeFormatType = 8">
              <libraryPathEntry kind="1" linkType="1" path="libs" />
            </xsl:if>-->

            <!-- codegenformat id="10" name="Flex Hibernate/AS3" -->
            <!-- <xsl:if test="//runtime/@codeFormatType = 10">
              <libraryPathEntry kind="1" linkType="1" path="libs" />
            </xsl:if>-->

            <!-- codegenformat id="12" name="Robotlegs" -->
            <!-- <xsl:if test="//runtime/@codeFormatType = 12">
              <libraryPathEntry kind="1" linkType="1" path="libs" />
            </xsl:if>-->

					</libraryPath>
					<sourceAttachmentPath />
				</compiler>
				<applications>
					<!-- application path="{//service/@name}.mxml" /-->
					<xsl:call-template name="codegen.project.eclipse.flex.actionscript.properties.applications" />
				</applications>
				<modules />
				<buildCSSFiles />
			</actionScriptProperties>
		</file>
	</xsl:template>
		
</xsl:stylesheet>