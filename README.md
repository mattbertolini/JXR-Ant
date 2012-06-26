# JXR Ant Task

Version 1.0.0<br/>
Written by Matt Bertolini

## License

The JXR Ant Task is licensed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).

## Usage

Download the library from Maven Central and make sure it is available to ant in some way (e.g. On Ant's classpath or in a `<path>`).

```xml
    <dependency>
        <groupId>com.mattbertolini</groupId>
        <artifactId>jxr-ant</artifactId>
        <version>1.0.0</version>
    </dependency>
```

In your ant file, load the task.

```xml
    <taskdef resource="com/mattbertolini/jxr/ant/antlib.xml"/>
```

## Parameters

<table>
  <tr>
    <th>Attribute</th>
    <th>Description</th>
    <th>Availability</th>
    <th>Required</th>
  </tr>
  <tr>
    <td>bottom</td>
    <td>The text or HTML to place at the footter of the generated files.</td>
    <td>all</td>
    <td>No.</td>
  <tr>
    <td>destdir</td>
    <td>The destination directory where the generated files are saved.</td>
    <td>all</td>
    <td>Yes</td>
  </tr>
  <tr>
    <td>doctitle</td>
    <td>The document title to give the generated files.</td>
    <td>all</td>
    <td>No. Defaults to ${project.name} Xref Documentation</td>
  </tr>
  <tr>
    <td>inputencoding</td>
    <td>The character set to use when reading the input/source files.</td>
    <td>all</td>
    <td>No. Defaults to JVM file.encoding system property.</td>
  </tr>
  <tr>
    <td>javadocdir</td>
    <td>The directory where the equivalient javadoc files are located. Adding this attribute will link the javadoc with
    the xref files.</td>
    <td>all</td>
    <td>No</td>
  </tr>
  <tr>
    <td>outputencoding</td>
    <td>The character set to use when writing the generated files.</td>
    <td>all</td>
    <td>No. Defaults to JVM file.encoding system property.</td>
  </tr>
  <tr>
    <td>sourcepath</td>
    <td>The directory where the source files are located.</td>
    <td>all</td>
    <td>Yes unless a nested &lt;sourcepath&gt; is given.</td>
  </tr>
  <tr>
    <td>stylesheet</td>
    <td>The file path to the custom stylesheet to use when generating the documentation.</td>
    <td>all</td>
    <td>No</td>
  </tr>
  <tr>
    <td>templatedir</td>
    <td>The directory where the custom templates are located.</td>
    <td>all</td>
    <td>No</td>
  </tr>
  <tr>
    <td>windowtitle</td>
    <td>The window title to give the generated HTML files.</td>
    <td>all</td>
    <td>No. Defaults to ${project.name} Xref Documentation.</td>
  </tr>
</table>

## Parameters defined as nested elements

### bottom

Same as the bottom attribute. Nested element allows for easier defining of large blocks of text or HTML.

### doctitle

Same as the doctitle attribute.

### Sourcepath

A Path. This element allows for multiple source paths to be combined and generated into the final generated
documentation. All path elements must be directories. No individual files can be listed in the path. If defined, the
sourcepath attribute is not needed.

### windowtitle

The same as the window title attribute.

## Examples

Here is a basic uasage example:

```xml
    <jxr destdir="dest/docs/jxr" sourcepath="src"/>
```

Here is an example using the `<sourcepath>` nested element instead of the attribute:

```xml
    <jxr destdir="dest/docs/jxr/">
        <sourcepath>
            <pathelement location="src"/>
        </sourcepath>
    </jxr>
```

Here is an example with an HTML footer defined in the `<bottom>` nested element:

```xml
    <jxr destdir="dest/docs/jxr" sourcepath="src">
        <bottom><![CDATA[<p>Fotter text here</p>]]></bottom>
    </jxr>
```

Here is an example with some classes excluded using a `<patternset>` element:

```xml
    <jxr destdir="dest/docs/jxr" sourcepath="src">
        <patternset>
            <excludes name="**/example/subpackage/**"/>
        </patternset>
    </jxr>
```

## Bugs

If you find any bugs, pleasae file them in the issue tracker above.