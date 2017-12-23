# Polish legal docs parser
OOP class assignment. Parser which turns text file with legal document (following Act of rules for legislative techniques in Poland) into object form.

Intensively uses functional features of Java 8 such as streams, lambdas, parallelism, method references.

## Partition levels

Division (Dział) >
Chapter (Rozdział) >
Section (Sekcja) >
Article (Artykuł) >
Paragraph (Ustęp) >
Point (Punkt) >
Letter (Litera)

## Arguments
First (and the only expected) non-option arguement is assumed to be a filename.

`-m` / `--mode` accepts `["table", table_of_contents", "show"]`. If empty, defaults to show. If you provide a mode, the only other accepted option is `--division`.

`-d` / `--division` number of division. Upon providing `--division` (and not `--mode` before), the only other working parameter with this one is `--chapter`.

`-c` / `--chapter` chapter. Goes alone.

`-a` / `--article` to show article's content.
 
`-P` / `--paragraph` requires article.

`-p` / `--point` requires article and paragraph.

`-l` / `--letter` requires article, paragraph and point.

`-f` / `--articles-from` beginning of articles range.

`-t` / `--articles-to` end of articles range. Inclusive.

The order of options *does not* matter.

## Example of usage
Start with command:
```bash
java -jar lpars.jar ...
```
choose file (`konstytucja.txt` and `uokik.txt` are provided in `/assets`):
```
... assets/uokik.txt ...
```
then provide parameters (each line below is separate example):
```
--mode table --division III
--articles-from 29 --articles-to 32
--article 111 --paragraph 3 --point 1 --letter c
```

**NOTE:**
Due to questionable consistence of the documents, make sure you provide numbers in exactly the same format as in the source document (roman/arabic numerals).
## Running
In order to quickly produce `*.jar` archive, type the following (requires Gradle installed):
```bash
gradle clean
gradle artifact
cp ./build/libs/lpars.jar . # Copy jar out of the build directory.
java -jar lpars.jar ...args
```
The `.idea` directory is also provided, allowing (hopefully) to easily run tests and build application. The builds are stored in `/out`. Also include artifact (i.e. jar) generation to `/out/artifacts`.
