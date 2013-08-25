<@compress single_line=true>
<#escape x as x?html>
<!DOCTYPE html>
<html lang="ja">
<head>
	<title>${indexName!"Local Cloud"}</title>
</head>
<body>


<a href="/tree">/</a><#--
--><#if (path?has_content)>
<#assign hierarchy = path?substring(1, path?length - 1)?split("/")>
<#list hierarchy as layer><#--
--><#if (layer != hierarchy?last)><#--
--><a href="/tree/<#list 0..layer_index as i>${hierarchy[i]}</#list>">${layer}</a>/<#--
--><#else><#--
-->${layer}<#--
--></#if><#--
--></#list>
</#if>


<#if fileInformationList?has_content>
<#if (path?length > 1)><#assign root = path?substring(0, path?length - 1)></#if>

<ul>
<#list fileInformationList as content>
<li>
	<#switch content.contentType!>
	<#case "DIRECTORY">
		<a href="${root!}/${content.name}/">${content.name}</a>
		<#break>
	<#case "AUDIO">
		<audio src="/file/${root!}${root?has_content?string("/", "")}${content.name}">非対応の音声形式です。</audio>
		<#break>
	<#default>
		<p>${content.name}</p>
	</#switch>
</li>
</#list>
</ul>
<#else>
<p>空のディレクトリ</p>
</#if>
</body>
</html>
</#escape>
</@compress>